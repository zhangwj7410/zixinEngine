package zixin.socket.server;

import java.io.InputStream;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import zixin.action.Action;
import zixin.action.ActionObject;
import zixin.socket.AckRequest;
import zixin.socket.Configuration;
import zixin.socket.SocketIOClient;
import zixin.socket.SocketIOServer;
import zixin.socket.listener.DataListener;
import zixin.socket.quartz.SchedulerTemplate;
import zixin.socket.store.MemoryStore;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

public class SocketSSLServer {
	public static Logger logger = LoggerFactory.getLogger(SocketSSLServer.class);
	public static SchedulerTemplate scheduler;
	public static ObjectMapper mapper = new ObjectMapper();
	public static MemoryStore memoryStore = new MemoryStore();
	public static SocketIOServer server = null;

	public static void loadScheduler() throws Exception {
		scheduler = new SchedulerTemplate();
		scheduler.start();
		logger.info("loadScheduler succuess");
	}

	public static void main(String[] args) throws InterruptedException {
		try {
			Configuration config = new Configuration();
			config.setHostname("localhost");
	        config.setPort(10443);
	        config.setKeyStorePassword("test1234");
	        InputStream stream = SocketSSLServer.class.getResourceAsStream("/keystore.jks");
	        config.setKeyStore(stream);
			server = new SocketIOServer(config);

			mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
			
			server.addEventListener("c", ActionObject.class, new DataListener<ActionObject>() {
				@Override
				public void onData(SocketIOClient client, ActionObject data, AckRequest ackRequest) {
					try {
						// broadcast messages to all clients
						System.out.println(data.getN());
						String sReturn = "0";
						try {
							Map<String, Object> map = mapper.readValue(data.getN(),
									new TypeReference<Map<String, Object>>() {
									});
							String sID = data.getI();
							String sService = (String) map.get("f");
							String sD = (String) map.get("d");
							String sY = (String) map.get("y");
							String sE = (String) map.get("e");
							String sS = (String) map.get("s");
							System.out.println("sService=" + sService);
							switch (sService) {
							case "t":
								try {
									Action action = ((Action) Class.forName("zixin.action.t." + sD + ".Action" + sY)
											.newInstance());
									Map returnMap = action.action(sE, sS, map);
									sReturn = mapper.writeValueAsString(returnMap);
									System.out.println("sReturn="+sReturn);

									if ("q".equals(returnMap.get("g"))) {
										server.getBroadcastOperations().sendEvent("c", sReturn);
										System.out.println("guangbo="+sReturn);
									}
									action = null;
									returnMap = null;
								} catch (Exception e) {
									logger.error(e.toString());
									sReturn = "{'i':0,n:{'x':'交易不存在'}}";
								}
								break;
							}
							map = null;
						} catch (Exception e) {
							logger.error(e.toString());
							sReturn = "{'i':0,n:{'x':'数据格式不正确'}}";
						}
						//data.setMessage("111");
						client.sendEvent("c", sReturn);
						sReturn = null;
					} catch (Exception e) {
						logger.error(e.toString());
					}
				}
			});

			server.start();
			//Thread.sleep(Integer.MAX_VALUE);
			//Thread.sleep(1000);
			loadScheduler();
			//server.stop();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
