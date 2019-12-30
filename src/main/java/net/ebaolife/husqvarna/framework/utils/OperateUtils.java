package net.ebaolife.husqvarna.framework.utils;

import net.ebaolife.husqvarna.framework.core.dataobject.filter.DateSectionFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OperateUtils {

	public static String getCondition(String name, String operator, String value) {

		if (operator == null) {
			if (value == null)
				return name;
			else
				return name + " " + value;
		}
		if (value != null) {
			value = value.replaceAll("'", "");
			if (value.equalsIgnoreCase("true"))
				value = "1";
			if (value.equalsIgnoreCase("false"))
				value = "0";
			if (value.equalsIgnoreCase("null"))
				operator = "is null";
		}
		if (operator != null)
			operator = operator.toLowerCase();

		if (operator.equals("eq") || operator.equals("==") || operator.equals("="))
			return name + " = " + translateValue(value);
		else if (operator.equals("gt"))
			return name + " > " + translateValue(value);
		else if (operator.equals("ge"))
			return name + " >= " + translateValue(value);
		else if (operator.equals("lt"))
			return name + " < " + translateValue(value);
		else if (operator.equals("le"))
			return name + " <= " + translateValue(value);
		else if (operator.equals("ne"))
			return name + " <> " + translateValue(value);

		else if (operator.equals("is"))
			return name + " is " + value + "";
		else if (operator.equals("is not"))
			return name + " is not " + value + "";
		else if (operator.equals("is null"))
			return name + " is null ";
		else if (operator.equals("is not null"))
			return name + " is not null";

		else if (operator.equals("in"))
			return name + valueChangeToInString(value);
		else if (operator.equals("not in"))
			return name + "( not " + valueChangeToInString(value) + ")";

		else if (operator.equals("like"))
			return name + " like " + translateValue("%" + value + "%");
		else if (operator.equals("not like"))
			return name + "( not like " + translateValue(value) + ")";

		else if (operator.equals("between"))
			return name + valueChangeToBetweenString(value);
		else if (operator.equals("not between"))
			return name + "( not " + valueChangeToBetweenString(value) + ")";

		else if (operator.equals("startwith"))
			return name + " like " + translateValue(value + "%");
		else if (operator.equals("not startwith"))
			return name + "( not like " + translateValue(value + "%") + ")";
		else if (operator.equals("regexp"))
			return name + " regexp " + translateValue(value);

		else if (operator.equals("yyyy"))
			return "year(" + name + ") = " + translateValue(value);
		else if (operator.equals("yyyy-mm"))
			return getYYYYMMFilter(name, value);
		else if (operator.equals("yyyy-mm-dd"))
			return getYYYYMMDDFilter(name, value);

		else if (DateSectionFilter.isDataSectionFilter(operator))
			return new DateSectionFilter(name, operator, value).getWhereSql();
		else
			return name + " " + operator + " '" + value + "'";

	}

	public static String translateValue(String value) {
		Map<String, Object> params = DataObjectUtils.getSqlParameter();
		if (params != null) {
			String key = "jxy_" + MD5.MD5Encode(value);
			params.put(key, value);
			return ":" + key + " ";
		} else {
			return " '" + value + "' ";
		}

	}

	public static String valueChangeToInString(String value) {
		Map<String, Object> params = DataObjectUtils.getSqlParameter();
		if (params != null) {
			String values[] = value.split(",");
			String key = "jxy_in" + MD5.MD5Encode(value);
			List<String> pvalues = new ArrayList<String>();
			for (String s : values) {
				pvalues.add(s);
			}
			params.put(key, pvalues);
			return " in :" + key + " ";
		} else {
			String values[] = value.split(",");
			StringBuilder sb = new StringBuilder("");
			for (int i = 0; i < values.length; i++) {
				sb.append("'" + values[i] + "'");
				if (i != values.length - 1)
					sb.append(",");
			}
			return " in (" + sb.toString() + ") ";
		}
	}

	public static String valueChangeToBetweenString(String value) {
		String v[] = value.split(",");
		if (v.length < 2) {
			int pos = -1;
			for (int i = 0; i <= 9; i++) {
				int p = value.indexOf(i + "-");
				if (p >= 0) {
					pos = p;
				}
			}
			if (pos > -1) {
				v = new String[2];
				v[0] = value.substring(0, pos + 1);
				v[1] = value.substring(pos + 2);
			}
		}
		if (v.length < 2)
			return " between '" + v[0] + "' and '" + v[0] + "'";
		else
			return " between '" + v[0] + "' and '" + v[1] + "'";
	}

	public static String getYYYYMMFilter(String name, String value) {
		String values[] = value.split("-");
		return String.format("(year(%s) = %s and month(%s) = %s)", name, values[0], name, values[1]);
	}

	public static String getYYYYMMDDFilter(String name, String value) {
		String values[] = value.split("-");
		return String.format("(year(%s) = %s and month(%s) = %s and day(%s) = %s)", name, values[0], name, values[1],
				name, values[2]);
	}
}
