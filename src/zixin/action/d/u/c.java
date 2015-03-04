package zixin.action.d.u;

import java.util.HashMap;
import java.util.Map;

import zixin.action.Action;

//用户新增
public class c extends Action {

	public Map action(String sType1, String sType2, Map map) throws Exception {
		Map returnMap = new HashMap();
		returnMap.put("i", 1);
		// returnMap.put("g", "q");
		Map nMap = new HashMap();
		nMap.put("x", "交易不失时机手机世界成功");
		nMap.put("y", "交易不失时机手机世界成功");
		returnMap.put("n", nMap);
		return returnMap;
	}

}
