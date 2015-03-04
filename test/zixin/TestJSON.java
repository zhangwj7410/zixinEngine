package zixin;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.infinispan.client.hotrod.configuration.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import zixin.util.CommonUtil;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TestJSON {
	public Configuration configuration;
	public RemoteCacheManager manager;
	public RemoteCache<Object, Object> cache;
	public ObjectMapper mapper = new ObjectMapper();

	@Before
	public void setup() throws Exception {
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
	}

	@After
	public void teardown() {

	}

	@Test
	public void newJSON() throws Exception {  
	     JsonNode root = mapper.readTree("{'_id':'','s':'','z':'1','f':'1','x':{'m':'','p':'','y':'','t':'1'},'l':[{'i':'1'}],'l_':''}");
	     JsonNode root1 = mapper.readTree("{'_id':'111','l':[{'i':'2'}]}");
	    CommonUtil.merge(root, root1);
		System.out.println(mapper.writeValueAsString(root));
	}

}
