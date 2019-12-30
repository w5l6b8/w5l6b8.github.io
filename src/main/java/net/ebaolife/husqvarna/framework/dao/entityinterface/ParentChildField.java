package net.ebaolife.husqvarna.framework.dao.entityinterface;

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

import net.ebaolife.husqvarna.framework.bean.FieldAggregationType;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.platform.service.ModuleHierarchyService;

public interface ParentChildField {

	public String getFieldahead();

	public void setFieldahead(String value);

	public String getAggregate();

	public void setAggregate(String value);

	public FDataobjectfield getFDataobjectfield();

	public void setFDataobjectfield(FDataobjectfield value);

	public FDataobjectcondition getFDataobjectconditionBySubconditionid();

	public void setFDataobjectconditionBySubconditionid(FDataobjectcondition value);

	public String getCondition();

	public void setCondition(String value);

	public String getRemark();

	public void setRemark(String value);

	static public void updateToField(ParentChildField field, String string) {
		if (string == null || string.length() == 0)
			return;
		String fielditemid = string;
		String fieldid;
		String[] fieldsSeparate = fielditemid.split("\\|");
		if (fieldsSeparate.length == 1) {
			fieldid = fieldsSeparate[0];
		} else if (fieldsSeparate.length == 2) {
			fieldid = fieldsSeparate[1];
			field.setFieldahead(fieldsSeparate[0]);
		} else {
			fieldid = fieldsSeparate[1];
			field.setFieldahead(fieldsSeparate[0]);
			field.setAggregate(fieldsSeparate[2]);
			if (fieldsSeparate.length == 4) {
				field.setFDataobjectconditionBySubconditionid(new FDataobjectcondition(fieldsSeparate[3]));
			}
		}
		FDataobjectfield mf = new FDataobjectfield(fieldid);
		field.setFDataobjectfield(mf);
	}

	static public String generateFieldName(ParentChildField field) {
		if (field.getFieldahead() == null) {
			return field.getFDataobjectfield().getFieldname();
		} else if (field.getAggregate() == null) {
			return field.getFieldahead() + "." + field.getFDataobjectfield().getFieldname();
		} else {
			String part[] = field.getFieldahead().split(".with.");
			String result = field.getAggregate() + "." + part[0] + "." + field.getFDataobjectfield().getFieldname()
					+ ".with." + part[1];
			if (field.getFDataobjectconditionBySubconditionid() != null) {
				result = result + "|" + field.getFDataobjectconditionBySubconditionid().getConditionid();
			}
			return result;
		}
	}

	static public String generateFieldString(ParentChildField field) {
		if (field.getFieldahead() == null) {
			return field.getFDataobjectfield().getFieldid();
		} else if (field.getAggregate() == null) {
			return field.getFieldahead() + "|" + field.getFDataobjectfield().getFieldid();
		} else {
			String result = field.getFieldahead() + "|" + field.getFDataobjectfield().getFieldid() + "|"
					+ field.getAggregate();
			if (field.getFDataobjectconditionBySubconditionid() != null) {
				result = result + "|" + field.getFDataobjectconditionBySubconditionid().getConditionid();
			}
			return result;
		}
	}

	static public String generateFieldText(ParentChildField field, FDataobject baseModule) {
		if (field.getFieldahead() == null) {
			return field.getFDataobjectfield().getFieldtitle();
		} else if (field.getAggregate() == null) {
			return ModuleHierarchyService.getParentModuleFullName(baseModule, field.getFieldahead()) + "--"
					+ field.getFDataobjectfield().getFieldtitle();
		} else {
			String result = ModuleHierarchyService.getChildModuleFullName(baseModule, field.getFieldahead()) + "--"
					+ field.getFDataobjectfield().getFieldtitle() + "--" + FieldAggregationType.AGGREGATION
							.get(FieldAggregationType.valueOf(field.getAggregate().toUpperCase()));

			if (field.getFDataobjectconditionBySubconditionid() != null) {
				result = result + "(" + field.getFDataobjectconditionBySubconditionid().getTitle() + ")";
			}
			return result;
		}
	}
}
