package zixin;

import java.io.File;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonParser.Feature;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;

public class TestMongoDB {
	public static Mongo mongo = null;// Mongo对象
	public static DB database = null;// MongonDB的数据库对象
	public static DBCollection collection = null;// 类似于关系数据库的表

	@Before
	public void setup() throws Exception {
		mongo = new Mongo();
		connectDB();
	}

	@After
	public void teardown() {

	}

	public void connectDB() throws Exception {
		database = mongo.getDB("test");
		// 获取一个teacher集合没有新建一个
		collection = database.getCollection("user");
	}

	@Test
	public void newUser() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(new File("c:\\user.json"),
				new TypeReference<Map<String, Object>>() {
				});
		System.out.println(map);
		// Map map = new LinkedHashMap();
		BasicDBObject baseObject = new BasicDBObject(map);
		collection.insert(baseObject);
	}

	@Test
	public void saveUserAll() throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		Map<String, Object> map = mapper.readValue(new File("c:\\user.json"),
				new TypeReference<Map<String, Object>>() {
				});
		System.out.println(map);
		// Map map = new LinkedHashMap();
		BasicDBObject baseObject = new BasicDBObject();
		;
		baseObject.putAll(map);
		collection.save(baseObject);
	}

	@Test
	public void updateUser() throws Exception {
		updateObject("{'_id':'13585715388'}", "{'m':'sdsdsdsd'}");
	}

	// 更新问题
	@Test
	public void updateUserWenti() throws Exception {
		// updateObject("{'_id':'13585715388'}","{'w':[{'a1':3},{'a2':2},{'a3':1}]}");
		appendObject("{'_id':'13585715388'}", "{'w':{'a1':3,'a2':2,'a3':1}}");
		// updateObject("{'_id':'13585715388'}","{'w':{'a4':4}}");
		/*
		 * Map map = new HashMap(); Map map1 = new HashMap(); map1.put("a1", 1);
		 * map1.put("a2", 2); map1.put("a3", 3); map.put("w", map1);
		 * //map.put("w.a2", "1"); System.out.println(map); BasicDBObject
		 * queryObject = new BasicDBObject(); queryObject.put("_id",
		 * "13585715388");
		 * 
		 * BasicDBObject updateObject = new BasicDBObject();
		 * updateObject.putAll(map); DBObject updateSetValue =new
		 * BasicDBObject("$set",updateObject);
		 * 
		 * collection.update(queryObject,updateSetValue);
		 */
	}

	public void updateObject(String sQuery, String sUpdate) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		Map<String, Object> mapQuery = mapper.readValue(sQuery,
				new TypeReference<Map<String, Object>>() {
				});
		Map<String, Object> mapUpdate = mapper.readValue(sUpdate,
				new TypeReference<Map<String, Object>>() {
				});
		System.out.println(mapQuery);
		System.out.println(mapUpdate);
		BasicDBObject queryObject = new BasicDBObject();
		queryObject.putAll(mapQuery);
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.putAll(mapUpdate);
		DBObject updateSetValue = new BasicDBObject("$set", updateObject);
		collection.update(queryObject, updateSetValue);
	}

	public void appendObject(String sQuery, String sUpdate) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		Map<String, Object> mapQuery = mapper.readValue(sQuery,
				new TypeReference<Map<String, Object>>() {
				});
		Map<String, Object> mapUpdate = mapper.readValue(sUpdate,
				new TypeReference<Map<String, Object>>() {
				});
		System.out.println(mapQuery);
		System.out.println(mapUpdate);
		BasicDBObject queryObject = new BasicDBObject();
		queryObject.putAll(mapQuery);
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.putAll(mapUpdate);
		DBObject updateSetValue = new BasicDBObject("$addToSet", updateObject);
		collection.update(queryObject, updateSetValue);
	}

	// 更新借款实际还款
	@Test
	public void updateUserJie() throws Exception {
		updateObject("{'_id':'13585715388'}", "{'m':'sdsdsdsd'}");
		/*
		 * Map map = new HashMap(); map.put("m", "斯堪斯卡1111"); //Map map = new
		 * LinkedHashMap(); //BasicDBObject baseObject = (BasicDBObject)
		 * collection.findOne("13585715388"); //System.out.println(baseObject);
		 * //baseObject.putAll(map); BasicDBObject queryObject = new
		 * BasicDBObject(); queryObject.put("_id", "13585715388");
		 * queryObject.put("c", "13585715388");
		 * 
		 * BasicDBObject updateObject = new BasicDBObject();
		 * updateObject.putAll(map); DBObject updateSetValue =new
		 * BasicDBObject("$set",updateObject);
		 * 
		 * collection.update(queryObject,updateSetValue);
		 */
	}
}
