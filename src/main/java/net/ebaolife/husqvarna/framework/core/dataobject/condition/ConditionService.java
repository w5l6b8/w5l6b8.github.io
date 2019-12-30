package net.ebaolife.husqvarna.framework.core.dataobject.condition;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.core.dataobject.ParentModule;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class ConditionService {

	public static String addConditionInfo(BaseModule baseModule, String conditionString) {
		String regex = "\\{[^}]*\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(conditionString);
		StringBuffer resultBuffer = new StringBuffer();
		while (matcher.find()) {
			Conditionfield f = JSONObject.parseObject(matcher.group(), Conditionfield.class);
			f._setfDataobjectfield();
			if (f.getAggregate() == null) {
				if (f.getFieldahead() != null && f.getFieldahead().length() > 0) {
					ParentModule pm = baseModule.getAllParents().get(f.getFieldahead());
					matcher.appendReplacement(resultBuffer, f.getFDataobjectfield()._getSelectName(pm.getAsName()));
				} else {
					matcher.appendReplacement(resultBuffer,
							f.getFDataobjectfield()._getSelectName(baseModule.getAsName()));
				}
			}
		}
		matcher.appendTail(resultBuffer);
		return resultBuffer.toString();
	}

	public static String getFieldaheadFromFieldName(String fieldname) {
		if (fieldname.indexOf(".with.") > 0) {
			String f = fieldname.substring(fieldname.indexOf(".") + 1);
			f = f.substring(0, f.indexOf(".")) + f.substring(f.indexOf(".with."));
			return f;
		} else
			return null;
	}

}

@SuppressWarnings("serial")
class Conditionfield implements Serializable, ParentChildField {
	private String objectname;
	private String fieldname;
	private FDataobjectfield FDataobjectfield;
	private String fieldahead;
	private String aggregate;
	private String subconditionid;
	private FDataobjectcondition FDataobjectconditionBySubconditionid;
	private String condition;
	private String remark;

	public Conditionfield() {

	}

	@Override
	public String toString() {
		return "Conditionfield [objectname=" + objectname + ", fieldname=" + fieldname + ", fieldahead=" + fieldahead
				+ ", aggregate=" + aggregate + "]";
	}

	public String getObjectname() {
		return objectname;
	}

	public void setObjectname(String objectname) {
		this.objectname = objectname;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldahead() {
		return fieldahead;
	}

	public void setFieldahead(String fieldahead) {
		this.fieldahead = fieldahead;
	}

	public String getAggregate() {
		return aggregate;
	}

	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}

	public net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield getFDataobjectfield() {
		return FDataobjectfield;
	}

	public void setFDataobjectfield(FDataobjectfield fDataobjectfield) {
		FDataobjectfield = fDataobjectfield;
	}

	public void _setfDataobjectfield() {
		FDataobject dataobject = DataObjectUtils.getDataObject(objectname);
		this.setFDataobjectfield(dataobject._getModuleFieldByFieldName(fieldname));
	}

	public String getSubconditionid() {
		return subconditionid;
	}

	public void setSubconditionid(String subconditionid) {
		this.subconditionid = subconditionid;
	}

	public FDataobjectcondition getFDataobjectconditionBySubconditionid() {
		return FDataobjectconditionBySubconditionid;
	}

	public void setFDataobjectconditionBySubconditionid(FDataobjectcondition fDataobjectconditionBySubconditionid) {
		FDataobjectconditionBySubconditionid = fDataobjectconditionBySubconditionid;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	@Override
	public String getRemark() {
		return remark;
	}

	@Override
	public void setRemark(String value) {
		this.remark = value;
	}

}
