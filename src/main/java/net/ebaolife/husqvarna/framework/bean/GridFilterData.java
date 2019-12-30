package net.ebaolife.husqvarna.framework.bean;

import net.ebaolife.husqvarna.framework.core.dataobject.filter.SqlModuleFilter;

import java.io.Serializable;
import java.util.*;

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
public class GridFilterData implements Serializable {

	private String searchText;

	private String[] gridColumnNames;

	private List<GridFieldInfo> gridFieldInfos;

	private String listGridSchemaName;

	private Map<String, TreeNodeRecord> navigateTreeSelected = new HashMap<String, TreeNodeRecord>();

	private SqlModuleFilter parentModuleFilter;

	private Map<String, String> eachFieldFilter = new HashMap<String, String>();

	private String groupFieldName;

	public GridFilterData() {

	}

	@Override
	public String toString() {
		return "GridFilterData [searchText=" + searchText + ", gridColumnNames=" + gridColumnNames
				+ ", navigateTreeSelected=" + navigateTreeSelected + ", parentModuleFilter=" + parentModuleFilter
				+ ", eachFieldFilter=" + eachFieldFilter + ", groupFieldName=" + groupFieldName + "]";
	}

	public String getNavigateTreeSelectedAllTitle() {
		String result = "";
		if (navigateTreeSelected.size() != 0) {
			Iterator<String> key = navigateTreeSelected.keySet().iterator();
			while (key.hasNext()) {
				String string = (String) key.next();
				TreeNodeRecord record = navigateTreeSelected.get(string);
				result = result + " 『" + record.getText() + "』";
			}
		}
		return result;

	}

	public List<String> getTreeFilterTextList() {
		List<String> result = new ArrayList<String>();
		if (navigateTreeSelected.size() != 0) {
			Iterator<String> key = navigateTreeSelected.keySet().iterator();
			while (key.hasNext()) {
				String string = (String) key.next();
				TreeNodeRecord record = navigateTreeSelected.get(string);
				result.add(record.getModuleName() + ":" + record.getText());
			}
		}
		return result;

	}

	public TreeNodeRecord getSelectedTreeNodeRecordWithModuleName(String moduleName) {
		if (navigateTreeSelected.size() != 0) {
			Iterator<String> key = navigateTreeSelected.keySet().iterator();
			while (key.hasNext()) {
				String string = (String) key.next();
				TreeNodeRecord record = navigateTreeSelected.get(string);
				if (record.getModuleName().equals(moduleName))
					return record;
			}
		}
		return null;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}

	public String[] getGridColumnNames() {
		return gridColumnNames;
	}

	public void setGridColumnNames(String[] gridColumnNames) {
		this.gridColumnNames = gridColumnNames;
	}

	public Map<String, TreeNodeRecord> getNavigateTreeSelected() {
		return navigateTreeSelected;
	}

	public void setNavigateTreeSelected(Map<String, TreeNodeRecord> navigateTreeSelected) {
		this.navigateTreeSelected = navigateTreeSelected;
	}

	public SqlModuleFilter getParentModuleFilter() {
		return parentModuleFilter;
	}

	public void setParentModuleFilter(SqlModuleFilter parentModuleFilter) {
		this.parentModuleFilter = parentModuleFilter;
	}

	public Map<String, String> getEachFieldFilter() {
		return eachFieldFilter;
	}

	public void setEachFieldFilter(Map<String, String> eachFieldFilter) {
		this.eachFieldFilter = eachFieldFilter;
	}

	public String getGroupFieldName() {
		return groupFieldName;
	}

	public void setGroupFieldName(String groupFieldName) {
		this.groupFieldName = groupFieldName;
	}

	public String getListGridSchemaName() {
		return listGridSchemaName;
	}

	public void setListGridSchemaName(String listGridSchemaName) {
		this.listGridSchemaName = listGridSchemaName;
	}

	public List<GridFieldInfo> getGridFieldInfos() {
		return gridFieldInfos;
	}

	public void setGridFieldInfos(List<GridFieldInfo> gridFieldInfos) {
		this.gridFieldInfos = gridFieldInfos;
	}

}
