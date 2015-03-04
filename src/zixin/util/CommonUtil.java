package zixin.util;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class CommonUtil {
	public static Logger logger = LoggerFactory.getLogger(CommonUtil.class);

	public static Map cloneMap(Map map) {
		Map o = new HashMap();
		if (map != null) {
			java.util.Iterator it = map.entrySet().iterator();
			while (it.hasNext()) {
				java.util.Map.Entry<String, Object> entry = (java.util.Map.Entry<String, Object>) it
						.next();
				o.put(entry.getKey(), "" + entry.getValue());
			}
		}
		return o;
	}

	public static boolean isContain(String s1, String s2) {
		// System.out.println(s1+"="+s2);
		if (s1 == null || s2 == null) {
			return false;
		} else {
			String[] a1 = StringUtils.splitPreserveAllTokens(s1, ",");
			String[] a2 = StringUtils.splitPreserveAllTokens(s2, ",");

			boolean bContain = false;
			for (int i = 0; i < a1.length; i++) {
				for (int j = 0; j < a2.length; j++) {
					if (a1[i].equals(a2[j])) {
						return true;
					}
				}
			}
			return bContain;
		}
	}

	public boolean ifContain(String s1, String s2) {
		// System.out.println(s1+"="+s2);
		if (s1 == null || s2 == null) {
			return false;
		} else {
			String[] a1 = StringUtils.splitPreserveAllTokens(s1, ",");
			String[] a2 = StringUtils.splitPreserveAllTokens(s2, ",");

			boolean bContain = false;
			for (int i = 0; i < a1.length; i++) {
				for (int j = 0; j < a2.length; j++) {
					if (a1[i].equals(a2[j])) {
						return true;
					}
				}
			}
			return bContain;
		}
	}

	public static String getBetweenString(String s, String s1, String s2) {
		return getBeforeString(getAfterString(s, s1), s2);
	}

	public static String getBeforeString(String s, String s1) {
		if (isNull(s)) {
			return "";
		} else {
			int i = s.indexOf(s1);
			if (i > -1) {
				return s.substring(0, i).toString().trim();
			} else {
				return "";
			}
		}
	}

	public static String getAfterString(String s, String s1) {
		if (isNull(s)) {
			return "";
		} else {
			int i = s.indexOf(s1);
			if (i > -1) {
				return s.substring(i + 1).toString().trim();
			} else {
				return "";
			}
		}
	}

	public static String getBeforeString1(String s, String s1) {
		if (isNull(s)) {
			return "";
		} else {
			int i = s.indexOf(s1);
			if (i > -1) {
				return s.substring(0, i).toString().trim();
			} else {
				return s;
			}
		}
	}

	public static String getAfterString1(String s, String s1) {
		if (isNull(s)) {
			return "";
		} else {
			int i = s.indexOf(s1);
			if (i > -1) {
				return s.substring(i + 1).toString().trim();
			} else {
				return s;
			}
		}
	}

	public static String getNotNullString(Object o) {
		if (isNull(o)) {
			return "";
		} else {
			return o.toString();
		}
	}

	public static String getNotNullString(String o) {
		if (isNull(o)) {
			return "";
		} else {
			return o;
		}
	}

	public static boolean isNull(Object o) {
		if (o == null || o.equals("") || StringUtils.isBlank("" + o)
				|| o.equals("null")) {
			return true;
		} else {
			return false;
		}
	}
	
	public static JsonNode merge(JsonNode mainNode, JsonNode updateNode) {

	    Iterator<String> fieldNames = updateNode.fieldNames();
	    while (fieldNames.hasNext()) {

	        String fieldName = fieldNames.next();
	        JsonNode jsonNode = mainNode.get(fieldName);
	        // if field exists and is an embedded object
	        if (jsonNode != null && jsonNode.isObject()) {
	            merge(jsonNode, updateNode.get(fieldName));
	        }
	        else {
	            if (mainNode instanceof ObjectNode) {
	                // Overwrite field
	                JsonNode value = updateNode.get(fieldName);
	                ((ObjectNode) mainNode).put(fieldName, value);
	            }
	        }

	    }

	    return mainNode;
	}
}
