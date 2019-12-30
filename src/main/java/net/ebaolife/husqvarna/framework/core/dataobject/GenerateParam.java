package net.ebaolife.husqvarna.framework.core.dataobject;

import net.ebaolife.husqvarna.framework.bean.SortParameter;
import net.ebaolife.husqvarna.framework.bean._ModuleAdditionField;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.grid.GridColumn;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectview;

import java.util.List;

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
public class GenerateParam {
	private String keyValue;

	private FDataobjectview viewscheme;

	private String query;

	private List<UserDefineFilter> querys;

	private List<UserDefineFilter> userDefineFilters;

	private List<UserNavigateFilter> userNavigateFilters;

	private List<UserParentFilter> userParentFilters;

	private List<SortParameter> sortParameters;

	private List<GridColumn> gridColumns;

	private _ModuleAdditionField countAggregateField;

	private String conditionString;
	private String conditionSql;
	private boolean hasParent;

	public GenerateParam() {

	}

	public String getKeyValue() {
		return keyValue;
	}

	public void setKeyValue(String keyValue) {
		this.keyValue = keyValue;
	}

	public List<UserDefineFilter> getUserDefineFilters() {
		return userDefineFilters;
	}

	public void setUserDefineFilters(List<UserDefineFilter> userDefineFilters) {
		this.userDefineFilters = userDefineFilters;
	}

	public List<SortParameter> getSortParameters() {
		return sortParameters;
	}

	public void setSortParameters(List<SortParameter> sortParameters) {
		this.sortParameters = sortParameters;
	}

	public List<UserNavigateFilter> getUserNavigateFilters() {
		return userNavigateFilters;
	}

	public void setUserNavigateFilters(List<UserNavigateFilter> userNavigateFilters) {
		this.userNavigateFilters = userNavigateFilters;
	}

	public _ModuleAdditionField getCountAggregateField() {
		return countAggregateField;
	}

	public void setCountAggregateField(_ModuleAdditionField countAggregateField) {
		this.countAggregateField = countAggregateField;
	}

	public List<UserParentFilter> getUserParentFilters() {
		return userParentFilters;
	}

	public void setUserParentFilters(List<UserParentFilter> userParentFilters) {
		this.userParentFilters = userParentFilters;
	}

	public List<GridColumn> getGridColumns() {
		return gridColumns;
	}

	public void setGridColumns(List<GridColumn> gridColumns) {
		this.gridColumns = gridColumns;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<UserDefineFilter> getQuerys() {
		return querys;
	}

	public void setQuerys(List<UserDefineFilter> querys) {
		this.querys = querys;
	}

	public FDataobjectview getViewscheme() {
		return viewscheme;
	}

	public void setViewscheme(FDataobjectview viewscheme) {
		this.viewscheme = viewscheme;
		if (viewscheme != null)
			this.conditionString = viewscheme._getConditionExpression();
	}

	public String getConditionString() {
		return conditionString;
	}

	public void setConditionString(String conditionString) {
		this.conditionString = conditionString;
	}

	public String getConditionSql() {
		return conditionSql;
	}

	public void setConditionSql(String conditionSql) {
		this.conditionSql = conditionSql;
	}

	public boolean isHasParent() {
		return hasParent;
	}

	public void setHasParent(boolean hasParent) {
		this.hasParent = hasParent;
	}

}
