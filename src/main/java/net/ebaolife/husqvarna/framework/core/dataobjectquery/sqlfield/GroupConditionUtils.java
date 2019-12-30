package net.ebaolife.husqvarna.framework.core.dataobjectquery.sqlfield;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import net.ebaolife.husqvarna.framework.utils.MD5;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author jiangfeng
 * 
 *         www.jhopesoft.com
 * 
 *         jfok1972@qq.com
 * 
 *         2017-06-01
 * 
 */
public class GroupConditionUtils {

	public static List<UserDefineFilter> changeGroupConditionTo(String groupcondition) {
		List<UserDefineFilter> result = new ArrayList<UserDefineFilter>();
		String[] conditions = groupcondition.split("\\|\\|\\|");
		for (String s : conditions) {
			result.add(changeAGroupConditionTo(s));
		}
		return result;
	}

	public static List<UserDefineFilter> changeGroupConditionTo(List<String> groupcondition) {
		List<UserDefineFilter> result = new ArrayList<UserDefineFilter>();
		for (String s : groupcondition) {
			result.add(changeAGroupConditionTo(s));
		}
		return result;
	}

	public static UserDefineFilter changeAGroupConditionTo(String s) {
		int pos = s.indexOf('=');
		String fieldahead = null;
		String field = s.substring(0, pos);
		String value = s.substring(pos + 1);
		String[] part = field.split("\\|");
		if (part.length > 1) {
			fieldahead = part[0];
			field = part[1];
		}

		String leveltype = null;
		String[] spart = field.split("-");
		if (spart.length == 2) {
			field = spart[0];
			leveltype = spart[1];
		}
		FDataobjectfield objectfield = Local.getDao().findById(FDataobjectfield.class, field);
		if (objectfield._isManyToOne() || objectfield._isOneToOne()) {
			fieldahead = (fieldahead == null ? "" : fieldahead + ".") + objectfield.getFieldname();
			FDataobject dataobject = Local.getDao().findById(FDataobject.class, objectfield.getFieldtype());
			objectfield = dataobject._getNameField();
		}
		UserDefineFilter filter = new UserDefineFilter();
		filter.setProperty((fieldahead == null ? "" : fieldahead + ".") + objectfield.getFieldname());

		if (leveltype != null && leveltype.length() == 1)
			filter.setCondition(objectfield.getFDataobject()._getLevelExpression(Integer.parseInt(leveltype), "this"));
		filter.setOperator("eq");
		if (value.indexOf(',') != -1) {
			filter.setOperator("in");
		}
		filter.setValue(value);
		return filter;
	}

	public static String changeAGroupField(String field) {
		String fieldahead = null;
		String[] part = field.split("\\|");
		if (part.length > 1) {
			fieldahead = part[0];
			field = part[1];
		}

		FDataobjectfield objectfield = Local.getDao().findById(FDataobjectfield.class, field);
		if (objectfield._isManyToOne() || objectfield._isOneToOne()) {
			fieldahead = (fieldahead == null ? "" : fieldahead + ".") + objectfield.getFieldname();
			FDataobject dataobject = Local.getDao().findById(FDataobject.class, objectfield.getFieldtype());
			objectfield = dataobject._getNameField();
		}
		return (fieldahead == null ? "" : fieldahead + ".") + objectfield.getFieldname();
	}

	public static String getCodeLevelCondition(String field, int level) {
		String[] part = field.split("\\|");
		if (part.length > 1) {
			field = part[1];
		}
		FDataobjectfield objectfield = Local.getDao().findById(FDataobjectfield.class, field);
		return objectfield.getFDataobject()._getLevelExpression(level, "this");
	}

	public static String changeGroupConditionToJson(String groupcondition) {
		StringBuilder result = new StringBuilder("(");
		String[] conditions = groupcondition.split("\\|\\|\\|");
		for (int i = 0; i < conditions.length; i++) {
			String s = conditions[i];
			result.append("(" + changeAGroupConditionToJson(s) + ")");
			if (i != conditions.length - 1)
				result.append(" and ");
		}
		result.append(")");
		return result.toString();
	}

	public static String changeAGroupConditionToJson(String s) {
		JSONObject object = new JSONObject();
		String fieldahead = null;
		int pos = s.indexOf('=');
		String field = null;
		String value = null;
		if (pos != -1) {
			field = s.substring(0, pos);
			value = s.substring(pos + 1);
		} else
			field = s;
		String[] part = field.split("\\|");
		if (part.length > 1) {
			fieldahead = part[0];
			field = part[1];
		}

		String leveltype = null;
		String[] spart = field.split("-");
		if (spart.length == 2) {
			field = spart[0];
			leveltype = spart[1];
		}

		FDataobjectfield objectfield = Local.getDao().findById(FDataobjectfield.class, field);
		if (objectfield._isManyToOne() || objectfield._isOneToOne()) {
			fieldahead = (fieldahead == null ? "" : fieldahead + ".") + objectfield.getFieldname();
			FDataobject dataobject = Local.getDao().findById(FDataobject.class, objectfield.getFieldtype());
			objectfield = dataobject._getNameField();
			object.put("fieldname", objectfield.getFieldname());
			object.put("objectname", dataobject.getObjectname());
			object.put("fieldahead", fieldahead);
		} else {
			object.put("fieldname", objectfield.getFieldname());
			object.put("objectname", objectfield.getFDataobject().getObjectname());
			object.put("fieldahead", fieldahead);

			if (leveltype != null && leveltype.length() > 0) {

				object.put("condition",
						objectfield.getFDataobject()._getLevelExpression(Integer.parseInt(leveltype), "this"));
			}

		}
		if (pos != -1)
			return object.toJSONString() + genEqOrInStr(value, leveltype);
		else
			return object.toJSONString();
	}

	public static FDataobjectfield getGroupField(String s) {
		int pos = s.indexOf('=');
		String field = null;
		if (pos != -1) {
			field = s.substring(0, pos);
		} else
			field = s;
		String[] part = field.split("\\|");
		if (part.length > 1) {
			field = part[1];
		}
		return Local.getDao().findById(FDataobjectfield.class, field);
	}

	private static String genEqOrInStr(String value, String leveltype) {
		Map<String, Object> param = DataObjectUtils.getSqlParameter();
		if (param == null) {
			value = value.replaceAll("'", "");
			if (value.indexOf(',') != -1) {
				StringBuilder sb = new StringBuilder(" in (");
				String[] values = value.split(",");
				for (int i = 0; i < values.length; i++) {
					sb.append("'" + values[i] + "'" + (i == values.length - 1 ? "" : ","));
				}
				sb.append(")");
				return sb.toString();
			} else
				return " = '" + value + "'";
		} else {
			value = value.replaceAll("'", "");
			String key = "jxy_" + MD5.MD5Encode(value);
			if (value.indexOf(',') != -1) {
				List<String> pvalues = new ArrayList<String>();
				for (String s : value.split(",")) {
					pvalues.add(s);
				}
				param.put(key, pvalues);
				return " in :" + key + " ";
			} else {
				param.put(key, value);
				return " = :" + key + " ";
			}
		}
	}

}
