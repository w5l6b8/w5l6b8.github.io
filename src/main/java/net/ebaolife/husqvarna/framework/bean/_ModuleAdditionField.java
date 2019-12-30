package net.ebaolife.husqvarna.framework.bean;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;

import java.io.Serializable;

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
public class _ModuleAdditionField implements Serializable, ParentChildField {

	private static final long serialVersionUID = -8392747854820467524L;

	private Integer additionfieldId;

	private String objectid;

	private String fieldid;

	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;

	private String fieldahead;

	private String aggregate;

	private String titleahead;

	private String targetModuleName;

	private String title;

	private String fieldname;

	private String fieldtype;

	private FDataobjectcondition FDataobjectconditionBySubconditionid;

	private Boolean allowsummary;

	private String remark;

	public _ModuleAdditionField() {

	}

	public FieldAggregationType getAggregationType() {
		if (aggregate == null)
			return FieldAggregationType.NORMAL;

		for (FieldAggregationType type : FieldAggregationType.values())
			if (aggregate.equals(type.getValue()))
				return type;
		return null;
	}

	public Integer getAdditionfieldId() {
		return additionfieldId;
	}

	public void setAdditionfieldId(Integer additionfieldId) {
		this.additionfieldId = additionfieldId;
	}

	public String getObjectid() {
		return objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}

	public String getFieldid() {
		return fieldid;
	}

	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
	}

	public FDataobjectfield getFDataobjectfield() {
		return FDataobjectfield;
	}

	public void setFDataobjectfield(FDataobjectfield fDataobjectfield) {
		FDataobjectfield = fDataobjectfield;
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

	public String getTitleahead() {
		return titleahead;
	}

	public void setTitleahead(String titleahead) {
		this.titleahead = titleahead;
	}

	public String getTargetModuleName() {
		return targetModuleName;
	}

	public void setTargetModuleName(String targetModuleName) {
		this.targetModuleName = targetModuleName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	public Boolean getAllowsummary() {
		return allowsummary;
	}

	public void setAllowsummary(Boolean allowsummary) {
		this.allowsummary = allowsummary;
	}

	public FDataobjectcondition getFDataobjectconditionBySubconditionid() {
		return FDataobjectconditionBySubconditionid;
	}

	public void setFDataobjectconditionBySubconditionid(FDataobjectcondition fDataobjectconditionBySubconditionid) {
		FDataobjectconditionBySubconditionid = fDataobjectconditionBySubconditionid;
	}

	@Override
	public String getCondition() {
		return null;
	}

	@Override
	public void setCondition(String value) {

	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
