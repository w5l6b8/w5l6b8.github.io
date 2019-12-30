package net.ebaolife.husqvarna.framework.core.dataobject;

import net.ebaolife.husqvarna.framework.core.dataobject.field.AdditionParentModuleField;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

@SuppressWarnings("serial")
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
public class ParentModule extends AbstractModule implements Serializable {

	private FDataobjectfield moduleField;
	private Integer level;
	private Boolean isDirectParent = false;
	private boolean donotAddUserDataFilter = false;
	private boolean onlyonethisdataobject = true;
	private String onlyonename;
	private String namePath;

	private String modulePath;

	private String fieldahead;

	private AdditionParentModuleField primarykeyField;
	private AdditionParentModuleField nameField;
	private Map<String, AdditionParentModuleField> additionFields;
	private List<AdditionParentModuleField> tempField;

	private String leftoutterjoin;

	private Map<String, ParentModule> parents = new java.util.HashMap<String, ParentModule>();

	private boolean insertIdAndNameFields = false;

	private Object sonModuleHierarchy;

	public ParentModule() {

	}

	public FDataobjectfield getModuleField() {
		return moduleField;
	}

	public void setModuleField(FDataobjectfield moduleField) {
		this.moduleField = moduleField;
	}

	public String getModulePath() {
		return modulePath;
	}

	public void setModulePath(String modulePath) {
		this.modulePath = modulePath;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String _getNamePath() {
		if (onlyonethisdataobject)
			return onlyonename;
		else
			return namePath;
	}

	public String getNamePath() {
		return namePath;
	}

	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	public Map<String, ParentModule> getParents() {
		return parents;
	}

	public void setParents(Map<String, ParentModule> parents) {
		this.parents = parents;
	}

	public String getLeftoutterjoin() {
		return leftoutterjoin;
	}

	public void setLeftoutterjoin(String leftoutterjoin) {
		this.leftoutterjoin = leftoutterjoin;
	}

	public Object getSonModuleHierarchy() {
		return sonModuleHierarchy;
	}

	public void setSonModuleHierarchy(Object sonModuleHierarchy) {
		this.sonModuleHierarchy = sonModuleHierarchy;
	}

	public String getFieldahead() {
		return fieldahead;
	}

	public void setFieldahead(String fieldahead) {
		this.fieldahead = fieldahead;
	}

	public AdditionParentModuleField getPrimarykeyField() {
		return primarykeyField;
	}

	public void setPrimarykeyField(AdditionParentModuleField primarykeyField) {
		this.primarykeyField = primarykeyField;
	}

	public AdditionParentModuleField getNameField() {
		return nameField;
	}

	public void setNameField(AdditionParentModuleField nameField) {
		this.nameField = nameField;
	}

	public List<AdditionParentModuleField> getTempField() {
		return tempField;
	}

	public void setTempField(List<AdditionParentModuleField> tempField) {
		this.tempField = tempField;
	}

	public Map<String, AdditionParentModuleField> getAdditionFields() {
		return additionFields;
	}

	public void setAdditionFields(Map<String, AdditionParentModuleField> additionFields) {
		this.additionFields = additionFields;
	}

	public Boolean getIsDirectParent() {
		return isDirectParent;
	}

	public void setIsDirectParent(Boolean isDirectParent) {
		this.isDirectParent = isDirectParent;
	}

	public boolean isInsertIdAndNameFields() {
		return insertIdAndNameFields;
	}

	public void setInsertIdAndNameFields(boolean insertIdAndNameFields) {
		this.insertIdAndNameFields = insertIdAndNameFields;
	}

	public boolean isDonotAddUserDataFilter() {
		return donotAddUserDataFilter;
	}

	public void setDonotAddUserDataFilter(boolean donotAddUserDataFilter) {
		this.donotAddUserDataFilter = donotAddUserDataFilter;

		if (this.getParents() != null) {
			for (ParentModule p : this.getParents().values())
				p.setDonotAddUserDataFilter(donotAddUserDataFilter);
		}

	}

	public boolean isOnlyonethisdataobject() {
		return onlyonethisdataobject;
	}

	public void setOnlyonethisdataobject(boolean onlyonethisdataobject) {
		this.onlyonethisdataobject = onlyonethisdataobject;
	}

	public String getOnlyonename() {
		return onlyonename;
	}

	public void setOnlyonename(String onlyonename) {
		this.onlyonename = onlyonename;
	}

}
