package net.ebaolife.husqvarna.framework.core.dataobject.filter;


import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;

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
public class UserGlobalFilter implements Serializable {

	private static final long serialVersionUID = 1L;

	private FDataobject baseModule;

	private String fieldahead;

	private FDataobjectfield moduleField;

	private String aggregate;

	private FDataobject filterModule;

	private String recordIds;

	private String userDefineOperator;

	private String userDefineValue;

	private String userDefineCondition;

	public UserGlobalFilter() {

	}

	public void setManyToOneFieldToAheadPath() {
		if (moduleField != null) {
			if (moduleField._isManyToOne() || moduleField._isOneToOne()) {
				if (fieldahead == null)
					fieldahead = moduleField.getFieldname();
				else
					fieldahead = fieldahead + "." + moduleField.getFieldname();
				moduleField = null;
			}
		}
	}

	public String getSqlWhere(FDataobject module, String filterModuleAs) {

		String keyfieldname;
		if (moduleField == null) {
			keyfieldname = module._getPrimaryKeyField()._getSelectName(filterModuleAs);
		} else
			keyfieldname = moduleField._getSelectName(filterModuleAs);

		String result = "";

		if (recordIds != null && recordIds.length() > 0) {
			String[] keys = recordIds.split(",");
			if (module._isCodeLevel())
				for (String s : keys)
					result = result + (result == "" ? "" : " or ") + keyfieldname + " like '" + s.replaceAll("'", "")
							+ "%' ";
			else {
				for (String s : keys)
					result = result + (result == "" ? "" : " , ") + "'" + s.replaceAll("'", "") + "'";
				result = keyfieldname + " in (" + result + ")";
			}
		} else if (userDefineOperator != null) {
			if (userDefineValue != null)
				result = keyfieldname + " " + userDefineOperator + " " + userDefineValue;
			else
				result = keyfieldname + " " + userDefineOperator;
		} else if (userDefineCondition != null) {
			result = userDefineCondition.replaceAll("_this_", keyfieldname);
		}

		return " (" + result + ") ";
	}

	public FDataobject getBaseModule() {
		return baseModule;
	}

	public void setBaseModule(FDataobject baseModule) {
		this.baseModule = baseModule;
	}

	public String getFieldahead() {
		return fieldahead;
	}

	public void setFieldahead(String fieldahead) {
		this.fieldahead = fieldahead;
	}

	public FDataobject getFilterModule() {
		if (filterModule == null) {
			if (fieldahead == null)
				filterModule = baseModule;
			else {
				String paths[] = fieldahead.split("\\.");
				FDataobject m = baseModule;
				for (String path : paths) {
					m = DataObjectUtils.getDataObject(m._getModuleFieldByFieldName(path).getFieldtype());
				}
				filterModule = m;
			}
		}
		return filterModule;
	}

	public void setFilterModule(FDataobject filterModule) {
		this.filterModule = filterModule;
	}

	public String getAggregate() {
		return aggregate;
	}

	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}

	public String getUserDefineOperator() {
		return userDefineOperator;
	}

	public void setUserDefineOperator(String userDefineOperator) {
		this.userDefineOperator = userDefineOperator;
	}

	public String getUserDefineValue() {
		return userDefineValue;
	}

	public void setUserDefineValue(String userDefineValue) {
		this.userDefineValue = userDefineValue;
	}

	public String getUserDefineCondition() {
		return userDefineCondition;
	}

	public void setUserDefineCondition(String userDefineCondition) {
		this.userDefineCondition = userDefineCondition;
	}

	public FDataobjectfield getModuleField() {
		return moduleField;
	}

	public void setModuleField(FDataobjectfield moduleField) {
		this.moduleField = moduleField;
	}

	public String getRecordIds() {
		return recordIds;
	}

	public void setRecordIds(String recordIds) {
		this.recordIds = recordIds;
	}

}
