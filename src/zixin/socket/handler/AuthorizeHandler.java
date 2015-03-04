/**
 * Copyright 2012 Nikita Koksharov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package zixin.socket.handler;

import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandler.Sharable;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.QueryStringDecoder;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zixin.socket.Configuration;
import zixin.socket.Disconnectable;
import zixin.socket.DisconnectableHub;
import zixin.socket.HandshakeData;
import zixin.socket.SocketIOClient;
import zixin.socket.Transport;
import zixin.socket.ack.AckManager;
import zixin.socket.namespace.Namespace;
import zixin.socket.namespace.NamespacesHub;
import zixin.socket.protocol.AuthPacket;
import zixin.socket.protocol.Packet;
import zixin.socket.protocol.PacketType;
import zixin.socket.scheduler.CancelableScheduler;
import zixin.socket.scheduler.SchedulerKey;
import zixin.socket.scheduler.SchedulerKey.Type;
import zixin.socket.store.StoreFactory;
import zixin.socket.store.pubsub.ConnectMessage;
import zixin.socket.store.pubsub.PubSubStore;

@Sharable
public class AuthorizeHandler extends ChannelInboundHandlerAdapter implements Disconnectable {

    private final Logger log = LoggerFactory.getLogger(getClass());

    private final CancelableScheduler disconnectScheduler;

    private final String connectPath;
    private final Configuration configuration;
    private final NamespacesHub namespacesHub;
    private final StoreFactory storeFactory;
    private final DisconnectableHub disconnectable;
    private final AckManager ackManager;
    private final ClientsBox clientsBox;

    public AuthorizeHandler(String connectPath, CancelableScheduler scheduler, Configuration configuration, NamespacesHub namespacesHub, StoreFactory storeFactory,
            DisconnectableHub disconnectable, AckManager ackManager, ClientsBox clientsBox) {
        super();
        this.connectPath = connectPath;
        this.configuration = configuration;
        this.disconnectScheduler = scheduler;
        this.namespacesHub = namespacesHub;
        this.storeFactory = storeFactory;
        this.disconnectable = disconnectable;
        this.ackManager = ackManager;
        this.clientsBox = clientsBox;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            FullHttpRequest req = (FullHttpRequest) msg;
            Channel channel = ctx.channel();
            QueryStringDecoder queryDecoder = new QueryStringDecoder(req.getUri());

            if (!configuration.isAllowCustomRequests()
                    && !queryDecoder.path().startsWith(connectPath)) {
                HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.BAD_REQUEST);
                channel.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
                req.release();
                log.warn("Blocked wrong request! url: {}, ip: {}", queryDecoder.path(), channel.remoteAddress());
                return;
            }

            List<String> sid = queryDecoder.parameters().get("sid");
            if (queryDecoder.path().equals(connectPath)
                    && sid == null) {
                String origin = req.headers().get(HttpHeaders.Names.ORIGIN);
                if (!authorize(ctx, channel, origin, queryDecoder.parameters(), req)) {
                    req.release();
                    return;
                }
                // forward message to polling or websocket handler to bind channel
            }
        }
        ctx.fireChannelRead(msg);
    }

    private boolean authorize(ChannelHandlerContext ctx, Channel channel, String origin, Map<String, List<String>> params, FullHttpRequest req)
            throws IOException {
        Map<String, List<String>> headers = new HashMap<String, List<String>>(req.headers().names().size());
        for (String name : req.headers().names()) {
            List<String> values = req.headers().getAll(name);
            headers.put(name, values);
        }

        HandshakeData data = new HandshakeData(headers, params,
                                                (InetSocketAddress)channel.remoteAddress(),
                                                    req.getUri(), origin != null && !origin.equalsIgnoreCase("null"));

        boolean result = false;
        try {
            result = configuration.getAuthorizationListener().isAuthorized(data);
        } catch (Exception e) {
            log.error("Authorization error", e);
        }

        if (!result) {
            HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
            channel.writeAndFlush(res)
                    .addListener(ChannelFutureListener.CLOSE);
            log.debug("Handshake unauthorized, query params: {} headers: {}", params, headers);
            return false;
        }

        // TODO try to get sessionId from cookie
        UUID sessionId = UUID.randomUUID();

        List<String> transportValue = params.get("transport");
        if (transportValue == null) {
            log.warn("Got no transports for request {}", req.getUri());

            HttpResponse res = new DefaultHttpResponse(HTTP_1_1, HttpResponseStatus.UNAUTHORIZED);
            channel.writeAndFlush(res).addListener(ChannelFutureListener.CLOSE);
            return false;
        }

        Transport transport = Transport.byName(transportValue.get(0));
        ClientHead client = new ClientHead(sessionId, ackManager, disconnectable, storeFactory, data, clientsBox, transport, disconnectScheduler, configuration);
        channel.attr(ClientHead.CLIENT).set(client);
        clientsBox.addClient(client);


        String[] transports = {};
        if (configuration.getTransports().contains(Transport.WEBSOCKET)) {
            transports = new String[] {"websocket"};
        }

        AuthPacket authPacket = new AuthPacket(sessionId, transports, configuration.getPingInterval(),
                                                                            configuration.getPingTimeout());
        Packet packet = new Packet(PacketType.OPEN);
        packet.setData(authPacket);
        client.send(packet);

        client.schedulePingTimeout();
        log.debug("Handshake authorized for sessionId: {}, query params: {} headers: {}", sessionId, params, headers);
        return true;
    }

    public void connect(UUID sessionId) {
        SchedulerKey key = new SchedulerKey(Type.PING_TIMEOUT, sessionId);
        disconnectScheduler.cancel(key);
    }

    public void connect(ClientHead client) {
        Namespace ns = namespacesHub.get(Namespace.DEFAULT_NAME);

        if (!client.getNamespaces().contains(ns)) {
//            connect(client.getSessionId());

            Packet packet = new Packet(PacketType.MESSAGE);
            packet.setSubType(PacketType.CONNECT);
            client.send(packet);

            configuration.getStoreFactory().pubSubStore().publish(PubSubStore.CONNECT, new ConnectMessage(client.getSessionId()));

            SocketIOClient nsClient = client.addNamespaceClient(ns);
            ns.onConnect(nsClient);
        }
    }

    @Override
    public void onDisconnect(ClientHead client) {
        clientsBox.removeClient(client.getSessionId());
    }

}
