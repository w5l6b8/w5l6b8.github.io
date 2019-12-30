package net.ebaolife.husqvarna.framework.utils;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.FieldAggregationType;
import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.core.dataobject.ModuleHierarchyGenerate;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;

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
public class DataObjectFieldUtils {

	public static String getAdditionFieldname(FDataobjectfield FDataobjectfield, String fieldahead, String aggregate,
											  FDataobjectcondition condition, String objectname, boolean addToAdditionField) {

		String fieldname = null;
		if (fieldahead != null && fieldahead.length() > 0 && FDataobjectfield != null) {
			if (aggregate == null || aggregate.equals("normal")) {

				fieldname = fieldahead + "." + FDataobjectfield.getFieldname();
			} else {

				fieldname = aggregate + "." + fieldahead.replaceFirst(ModuleHierarchyGenerate.CHILDSEPARATOR,
						"." + FDataobjectfield.getFieldname() + ModuleHierarchyGenerate.CHILDSEPARATOR);
				if (condition != null)
					fieldname = fieldname + "|" + condition.getConditionid();
			}

			if (addToAdditionField) {
				FDataobject dataobject = DataObjectUtils.getDataObject(objectname);
				dataobject.addAdditionField(fieldname, FDataobjectfield, fieldahead, aggregate, condition);
			}
		}
		return fieldname;
	}

	public static String getDefaulttitle(FDataobjectfield FDataobjectfield, String fieldahead, String aggregate,
			FDataobjectcondition condition, String objectname) {
		if (fieldahead != null && fieldahead.length() > 0 && FDataobjectfield != null) {
			BaseModule baseModule = DataObjectUtils.getBaseModule(objectname);
			if (aggregate == null || aggregate.length() == 0 || aggregate.equals("normal"))
				return baseModule.getAllParents().get(fieldahead)._getNamePath() + "--"
						+ FDataobjectfield.getFieldtitle();
			else {
				String result = baseModule.getAllChilds().get(fieldahead).getNamePath() + "--"
						+ FDataobjectfield.getFieldtitle() + "--"
						+ FieldAggregationType.AGGREGATION.get(FieldAggregationType.valueOf(aggregate.toUpperCase()));
				if (condition != null)
					result = result + " (" + condition.getTitle() + ")";
				return result;
			}

		} else
			return null;
	}

	public static String getPCModuletitle(String objectname, String fieldahead) {
		if (fieldahead != null && fieldahead.length() > 0 && objectname != null) {
			BaseModule baseModule = DataObjectUtils.getBaseModule(objectname);
			if (fieldahead.indexOf(".with.") > 0)
				return baseModule.getAllChilds().get(fieldahead).getNamePath();
			else
				return baseModule.getAllParents().get(fieldahead)._getNamePath();
		} else
			return null;
	}

	public static String getItemId(FDataobjectfield FDataobjectfield, String fieldahead, String aggregate,
			FDataobjectcondition condition) {
		if (fieldahead == null) {
			return FDataobjectfield.getFieldid();
		} else {
			if (aggregate == null)
				return fieldahead + "|" + FDataobjectfield.getFieldid();
			else if (condition == null)
				return fieldahead + "|" + FDataobjectfield.getFieldid() + "|" + aggregate;
			else
				return fieldahead + "|" + FDataobjectfield.getFieldid() + "|" + aggregate + "|"
						+ condition.getConditionid();
		}
	}

	public static String getTitle(FDataobjectfield FDataobjectfield, String fieldahead, String aggregate,
			FDataobjectcondition condition, FDataobject baseModule) {
		if (fieldahead == null) {
			return FDataobjectfield.getFieldtitle();
		} else {
			return getDefaulttitle(FDataobjectfield, fieldahead, aggregate, condition, baseModule.getObjectname());
		}
	}

	public static String getFieldname(FDataobjectfield FDataobjectfield, String fieldahead, String aggregate,
			FDataobjectcondition condition, FDataobject baseModule) {
		if (fieldahead == null) {
			return FDataobjectfield.getFieldname();
		} else {
			return getAdditionFieldname(FDataobjectfield, fieldahead, aggregate, condition, baseModule.getObjectname(),
					false);
		}
	}

	public static JSONObject getFieldnameJson(FDataobjectfield FDataobjectfield, String fieldahead, String aggregate,
			FDataobjectcondition condition, FDataobject baseModule) {
		JSONObject object = new JSONObject();
		if (FDataobjectfield._isManyToOne() || FDataobjectfield._isOneToOne()) {
			FDataobject pobject = DataObjectUtils.getDataObject(FDataobjectfield.getFieldtype());
			object.put("fieldname", pobject._getPrimaryKeyField().getFieldname());
			object.put("objectname", pobject.getObjectname());
			object.put("fieldahead", (fieldahead != null ? fieldahead + "." : "") + FDataobjectfield.getFieldtype());
		} else {
			object.put("fieldname", FDataobjectfield.getFieldname());
			object.put("objectname", FDataobjectfield.getFDataobject().getObjectname());
			if (fieldahead != null) {
				object.put("fieldahead", fieldahead);
				if (aggregate != null)
					object.put("aggregate", aggregate);
				if (condition != null)
					object.put("subconditionid", condition.getConditionid());
			}
		}
		return object;
	}

}
