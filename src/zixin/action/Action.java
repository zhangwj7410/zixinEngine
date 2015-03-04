package zixin.action;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class Action {
	protected Logger logger = LoggerFactory.getLogger(getClass());

	public Map action(String sType1, String sType2, Map map) throws Exception {
		return null;
	}

}