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
package zixin.socket.store;

import java.util.Map;
import java.util.UUID;

import zixin.socket.Disconnectable;
import zixin.socket.handler.AuthorizeHandler;
import zixin.socket.namespace.NamespacesHub;
import zixin.socket.protocol.JsonSupport;
import zixin.socket.store.pubsub.PubSubStore;

/**
 *
 * Creates a client Store and PubSubStore
 *
 */
public interface StoreFactory extends Disconnectable {

    PubSubStore pubSubStore();

    <K, V> Map<K, V> createMap(String name);

    Store createStore(UUID sessionId);

    void init(NamespacesHub namespacesHub, AuthorizeHandler authorizeHandler, JsonSupport jsonSupport);

    void shutdown();

}
