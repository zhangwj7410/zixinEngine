package zixin;

import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.infinispan.client.hotrod.configuration.ConfigurationBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestCache {
	public Configuration configuration;
	public RemoteCacheManager manager;
	public RemoteCache<Object, Object> cache;

	@Before
	public void setup() throws Exception {
		connectCache();
	}

	@After
	public void teardown() {

	}

	public void connectCache() throws Exception {
		configuration = new ConfigurationBuilder()
				.addServers("127.0.0.1:11222").build();
		manager = new RemoteCacheManager(configuration);
		cache = manager.getCache();
	}

	@Test
	public void newCache() throws Exception {
		cache.put("key", "value");  
		assertEquals(1, cache.size());  
		assertTrue(cache.containsKey("key"));  
		cache.remove("key");  
		assertTrue(cache.isEmpty());  
		System.out.println(cache);
		cache.putIfAbsent("key", "newValue");  
		assertTrue(cache.get("key").equals("newValue"));  
		cache.put("key", "value", 5, TimeUnit.SECONDS);  
		assertTrue(cache.containsKey("key"));  
		Thread.sleep(10000);  
		assertFalse(cache.containsKey("key"));  
	}

}
