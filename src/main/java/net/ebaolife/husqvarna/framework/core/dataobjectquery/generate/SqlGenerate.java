package net.ebaolife.husqvarna.framework.core.dataobjectquery.generate;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.GroupParameter;
import net.ebaolife.husqvarna.framework.bean.SortParameter;
import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.ModuleHierarchyGenerate;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.column.ColumnField;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.sqlfield.SqlField;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.sqlfield.SqlFieldUtils;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovFormscheme;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridscheme;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridsortscheme;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

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
public class SqlGenerate {

	private static final String CR = "\r\n";
	private static final String TAB = "\t";

	private FDataobject dataobject;
	private BaseModule baseModule;
	private JSONObject sqlparam;
	private String idvalue;

	private boolean distinct = false;

	private boolean addIdField = false;
	private boolean addNameField = false;
	private boolean addPidField = false;
	private boolean addBaseField = true;
	private boolean addAllGridScheme = true;
	private boolean addAllFormScheme = true;
	private FovGridscheme gridscheme;
	private FovFormscheme formscheme;
	private List<ColumnField> columnFields;

	private List<UserParentFilter> userParentFilters;
	private List<UserNavigateFilter> userNavigateFilters;
	private List<UserDefineFilter> userDefineFilters;
	private List<UserDefineFilter> searchFieldQuerys;

	private GroupParameter group;

	private List<SortParameter> sortParameters;
	private FovGridsortscheme gridsortscheme;

	private Set<SqlField> selectfields = new LinkedHashSet<SqlField>();
	private List<String> wheres;
	private List<String> orders;

	public SqlGenerate() {
	}

	public SqlGenerate(FDataobject dataobject) {
		super();
		this.dataobject = dataobject;
		this.baseModule = ModuleHierarchyGenerate.genModuleHierarchy(dataobject, "t_");
	}

	public SqlGenerate pretreatment() {

		if (baseModule == null)
			baseModule = ModuleHierarchyGenerate.genModuleHierarchy(dataobject, "t_");

		if (addIdField)
			SqlFieldUtils.addIdField(baseModule, selectfields);
		if (addPidField)
			SqlFieldUtils.addPidField(baseModule, selectfields);
		if (addNameField)
			SqlFieldUtils.addNameField(baseModule, selectfields);

		if (addBaseField) {

			SqlFieldUtils.addAttachmentField(baseModule, selectfields);
			SqlFieldUtils.addAllBaseField(baseModule, selectfields);
		}

		if (gridscheme != null)
			SqlFieldUtils.addGridSchemeField(baseModule, gridscheme, selectfields);
		if (addAllGridScheme) {
			List<FovGridscheme> gridschemes = Local.getDao().findByProperty(FovGridscheme.class, "objectid",
					dataobject.getObjectid());
			for (FovGridscheme scheme : gridschemes)
				SqlFieldUtils.addGridSchemeField(baseModule, scheme, selectfields);
		}

		if (formscheme != null)
			SqlFieldUtils.addFormSchemeField(baseModule, formscheme, selectfields);
		if (addAllFormScheme) {
			List<FovFormscheme> formschemes = Local.getDao().findByProperty(FovFormscheme.class, "objectid",
					dataobject.getObjectid());
			for (FovFormscheme scheme : formschemes)
				SqlFieldUtils.addFormSchemeField(baseModule, scheme, selectfields);
		}

		if (columnFields != null)
			SqlFieldUtils.addUserDefinedField(baseModule, columnFields, selectfields);

		FieldGenerate.adjustScale(baseModule, selectfields);

		wheres = WhereGenerate.generateWhere(this);

		orders = OrderGenerate.generateOrder(this);
		return this;
	}

	public String generateSelect() {
		StringBuilder sql = new StringBuilder("select " + CR);
		if (distinct)
			sql.append(" distinct ");
		List<String> fields = FieldGenerate.generateSelectFields(baseModule, selectfields);
		for (String field : fields) {
			sql.append(TAB + field);
			sql.append((field == fields.get(fields.size() - 1) ? "" : ",") + CR);
		}
		sql.append(" from " + CR);
		for (String from : FromGenerate.generateFrom(this, baseModule, false)) {
			sql.append(TAB + from + CR);
		}
		if (wheres.size() > 0) {
			sql.append(" where " + CR);
			for (String where : wheres) {
				sql.append(TAB + "(" + where + ")");
				sql.append((where == wheres.get(wheres.size() - 1) ? "" : " and ") + CR);
			}
		}
		if (orders != null && orders.size() > 0) {
			sql.append(" order by " + CR);
			for (String order : orders) {
				sql.append(TAB + order);
				sql.append((order == orders.get(orders.size() - 1) ? "" : " , ") + CR);
			}
		}

		System.out.println(sql.toString());
		return sql.toString();

	}

	public String generateSelectCount() {
		StringBuilder sql = new StringBuilder("select count(*)" + CR);
		sql.append(" from " + CR);
		for (String from : FromGenerate.generateFrom(this, baseModule, true)) {
			sql.append(TAB + from + CR);
		}
		if (wheres.size() > 0) {
			sql.append(" where " + CR);
			for (String where : wheres) {
				sql.append(TAB + "(" + where + ")");
				sql.append((where == wheres.get(wheres.size() - 1) ? "" : " and ") + CR);
			}
		}
		System.out.println(sql.toString());
		return sql.toString();
	}

	public String[] getFieldNames() {
		String[] result = new String[selectfields.size()];
		int i = 0;
		for (SqlField field : selectfields) {
			result[i++] = field.getFieldname();
		}
		return result;
	}

	public String[] getFieldScales() {
		String[] result = new String[selectfields.size()];
		int i = 0;
		for (SqlField field : selectfields) {
			result[i++] = field.getScale();
		}
		return result;
	}

	public FDataobject getDataobject() {
		return dataobject;
	}

	public void setDataobject(FDataobject dataobject) {
		this.dataobject = dataobject;
	}

	public BaseModule getBaseModule() {
		return baseModule;
	}

	public void setBaseModule(BaseModule baseModule) {
		this.baseModule = baseModule;
	}

	public Set<SqlField> getSelectfields() {
		return selectfields;
	}

	public void setSelectfields(Set<SqlField> selectfields) {
		this.selectfields = selectfields;
	}

	public List<UserDefineFilter> getUserDefineFilters() {
		return userDefineFilters;
	}

	public void setUserDefineFilters(List<UserDefineFilter> userDefineFilters) {
		this.userDefineFilters = userDefineFilters;
	}

	public List<UserParentFilter> getUserParentFilters() {
		return userParentFilters;
	}

	public void setUserParentFilters(List<UserParentFilter> userParentFilters) {
		this.userParentFilters = userParentFilters;
	}

	public List<UserDefineFilter> getSearchFieldQuerys() {
		return searchFieldQuerys;
	}

	public void setSearchFieldQuerys(List<UserDefineFilter> searchFieldQuerys) {
		this.searchFieldQuerys = searchFieldQuerys;
	}

	public String getIdvalue() {
		return idvalue;
	}

	public void setIdvalue(String idvalue) {
		this.idvalue = idvalue;
	}

	public boolean isAddBaseField() {
		return addBaseField;
	}

	public void setAddBaseField(boolean addBaseField) {
		this.addBaseField = addBaseField;
	}

	public boolean isAddAllGridScheme() {
		return addAllGridScheme;
	}

	public void setAddAllGridScheme(boolean addAllGridScheme) {
		this.addAllGridScheme = addAllGridScheme;
	}

	public boolean isAddAllFormScheme() {
		return addAllFormScheme;
	}

	public void setAddAllFormScheme(boolean addAllFormScheme) {
		this.addAllFormScheme = addAllFormScheme;
	}

	public List<UserNavigateFilter> getUserNavigateFilters() {
		return userNavigateFilters;
	}

	public void setUserNavigateFilters(List<UserNavigateFilter> userNavigateFilters) {
		this.userNavigateFilters = userNavigateFilters;
	}

	public List<SortParameter> getSortParameters() {
		return sortParameters;
	}

	public void setSortParameters(List<SortParameter> sortParameters) {
		this.sortParameters = sortParameters;
	}

	public List<String> getOrders() {
		return orders;
	}

	public void setOrders(List<String> orders) {
		this.orders = orders;
	}

	public FovGridsortscheme getGridsortscheme() {
		return gridsortscheme;
	}

	public void setGridsortscheme(FovGridsortscheme gridsortscheme) {
		this.gridsortscheme = gridsortscheme;
	}

	public List<ColumnField> getColumnFields() {
		return columnFields;
	}

	public void setColumnFields(List<ColumnField> columnFields) {
		this.columnFields = columnFields;
	}

	public boolean isAddIdField() {
		return addIdField;
	}

	public void setAddIdField(boolean addIdField) {
		this.addIdField = addIdField;
	}

	public boolean isAddNameField() {
		return addNameField;
	}

	public void setAddNameField(boolean addNameField) {
		this.addNameField = addNameField;
	}

	public boolean isDistinct() {
		return distinct;
	}

	public void setDistinct(boolean distinct) {
		this.distinct = distinct;
	}

	public JSONObject getSqlparam() {
		return sqlparam;
	}

	public void setSqlparam(JSONObject sqlparam) {
		this.sqlparam = sqlparam;
	}

	public boolean isAddPidField() {
		return addPidField;
	}

	public void setAddPidField(boolean addPidField) {
		this.addPidField = addPidField;
	}

	public void onlyAddIdnameFields() {
		addIdField = true;
		addNameField = true;
		addPidField = true;
		addBaseField = false;
		addAllGridScheme = false;
		addAllFormScheme = false;
	}

	public void addAllFields() {
		addIdField = false;
		addNameField = false;
		addPidField = false;
		addBaseField = true;
		addAllGridScheme = true;
		addAllFormScheme = true;
	}

	public GroupParameter getGroup() {
		return group;
	}

	public void setGroup(GroupParameter group) {
		this.group = group;
	}
}
