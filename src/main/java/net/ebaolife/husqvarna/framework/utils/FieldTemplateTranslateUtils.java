package net.ebaolife.husqvarna.framework.utils;

import ognl.Ognl;
import ognl.OgnlException;

import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Map;

public class FieldTemplateTranslateUtils {

	public static final String NOSUCHPROPERTY = "_NOSUCHPROPERTY_";

	public static Object getValue(String express, Object record, Map<String, Object> recordMap) {
		express = express.replace("{", "").replace("}", "");
		Object result = null;
		boolean found = true;
		try {
			result = Ognl.getValue(express, record);
		} catch (OgnlException e) {
			found = false;
		}
		if (!found) {
			if (recordMap.containsKey(express))
				result = recordMap.get(express);
			else
				result = NOSUCHPROPERTY;
		}
		return result;
	}

	public static String getStringValue(String express, Object record, Map<String, Object> recordMap) {
		express = express.replace("{", "").replace("}", "");
		String[] parts = express.split("::");
		Object result = getValue(parts[0], record, recordMap);
		if (result == null)
			return "";

		if (parts.length == 2) {
			if (result instanceof Date || result instanceof java.sql.Date || result instanceof Timestamp) {
				Date _result = (Date) result;
				result = DateUtils.format(_result, parts[1]);
			} else if (result instanceof Number) {
				if ("大写".equals(parts[1])) {
					result = TypeChange.moneyFormatToUpper(((Number) result).doubleValue());
				} else {
					DecimalFormat df = (DecimalFormat) NumberFormat.getInstance();
					df.applyPattern(parts[1]);
					result = df.format(result);
				}
			}
		}
		return result.toString();
	}

}
