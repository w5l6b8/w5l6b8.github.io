package net.ebaolife.husqvarna.framework.bean;


import net.ebaolife.husqvarna.framework.core.dataobject.filter.SqlModuleFilter;

import java.io.Serializable;
import java.util.List;

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
public class DataFetchRequestInfo implements Serializable {

	private String moduleId;
	private String moduleName;
	private Integer startRow;
	private Integer endRow;
	private List<SortParameter> sorts;
	private Boolean isExport;

	private List<SqlModuleFilter> navigateFilters;

	public DataFetchRequestInfo() {
		super();
	}

	public DataFetchRequestInfo(String moduleId, String moduleName, Integer startRow, Integer endRow) {
		super();
		this.moduleId = moduleId;
		this.moduleName = moduleName;
		this.startRow = startRow;
		this.endRow = endRow;

	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

	public String getModuleId() {
		return moduleId;
	}

	public void setModuleId(String moduleId) {
		this.moduleId = moduleId;
	}

	public Boolean getIsExport() {
		return isExport == null ? false : isExport;
	}

	public void setIsExport(Boolean isExport) {
		this.isExport = isExport;
	}

	public List<SortParameter> getSorts() {
		return sorts;
	}

	public void setSorts(List<SortParameter> sorts) {
		this.sorts = sorts;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public List<SqlModuleFilter> getNavigateFilters() {
		return navigateFilters;
	}

	public void setNavigateFilters(List<SqlModuleFilter> navigateFilters) {
		this.navigateFilters = navigateFilters;
	}

}
