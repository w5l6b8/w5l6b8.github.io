package net.ebaolife.husqvarna.framework.core.dataobject.field;

import net.ebaolife.husqvarna.framework.bean._ModuleAdditionField;
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
public class AdditionParentModuleField {

	private String asName;
	private String fieldsql;
	private FDataobjectfield moduleField;
	private _ModuleAdditionField moduleAdditionField;

	private String title;
	private String additionSetting;

	public AdditionParentModuleField() {

	}

	public AdditionParentModuleField(String asName, String fieldsql) {
		super();
		this.asName = asName;
		this.setFieldsql(fieldsql);
	}

	public String getAsName() {
		return asName;
	}

	public void setAsName(String asName) {
		this.asName = asName;
	}

	public FDataobjectfield getModuleField() {
		return moduleField;
	}

	public void setModuleField(FDataobjectfield moduleField) {
		this.moduleField = moduleField;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getAdditionSetting() {
		return additionSetting;
	}

	public void setAdditionSetting(String additionSetting) {
		this.additionSetting = additionSetting;
	}

	public String getFieldsql() {
		return fieldsql;
	}

	public void setFieldsql(String fieldsql) {
		this.fieldsql = fieldsql;
	}

	public _ModuleAdditionField getModuleAdditionField() {
		return moduleAdditionField;
	}

	public void setModuleAdditionField(_ModuleAdditionField moduleAdditionField) {
		this.moduleAdditionField = moduleAdditionField;
	}

}
