package net.ebaolife.husqvarna.framework.core.dataobject;

import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
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
public class ChildModule implements Serializable {

	private FDataobject module;
	private FDataobjectfield moduleField;

	private String asName;
	private Integer level;

	private String namePath;

	private String modulePath;

	private String fieldahead;

	private List<UserDefineFilter> querys;

	private List<UserDefineFilter> userDefineFilters;

	private Map<String, ChildModule> childs = new java.util.HashMap<String, ChildModule>();

	private Object parentModuleHierarchy;

	public ChildModule() {

	}

	public FDataobject getModule() {
		return module;
	}

	public void setModule(FDataobject module) {
		this.module = module;
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

	public String getAsName() {
		return asName;
	}

	public void setAsName(String asName) {
		this.asName = asName;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public String getNamePath() {
		return namePath;
	}

	public void setNamePath(String namePath) {
		this.namePath = namePath;
	}

	public Map<String, ChildModule> getChilds() {
		return childs;
	}

	public void setChilds(Map<String, ChildModule> childs) {
		this.childs = childs;
	}

	public Object getParentModuleHierarchy() {
		return parentModuleHierarchy;
	}

	public void setParentModuleHierarchy(Object parentModuleHierarchy) {
		this.parentModuleHierarchy = parentModuleHierarchy;
	}

	public String getFieldahead() {
		return fieldahead;
	}

	public void setFieldahead(String fieldahead) {
		this.fieldahead = fieldahead;
	}

	public List<UserDefineFilter> getUserDefineFilters() {
		return userDefineFilters;
	}

	public void setUserDefineFilters(List<UserDefineFilter> userDefineFilters) {
		this.userDefineFilters = userDefineFilters;
	}

	public List<UserDefineFilter> getQuerys() {
		return querys;
	}

	public void setQuerys(List<UserDefineFilter> querys) {
		this.querys = querys;
	}

}
