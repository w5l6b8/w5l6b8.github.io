package net.ebaolife.husqvarna.framework.core.dataobject;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.*;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.grid.GridColumn;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Repository
public class ModuleDataDAO {
	private static final Log log = LogFactory.getLog(ModuleDataDAO.class);

	public static final String CHILDREN = "children";
	public static final Integer PARENTWITHCODELEVEL = 1;
	public static final Integer PARENTWITHPARENTID = 2;
	//@Resource
	private DaoImpl dao;

	@SuppressWarnings("unchecked")
	public JSONObject getTreeModuleData(String moduleName, List<UserDefineFilter> userDefineFilters,
										List<UserNavigateFilter> userNavigateFilters, List<UserParentFilter> userParentFilters,
										List<SortParameter> sortParameters) {

		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		GenerateParam generateParam = new GenerateParam();

		UserDefineFilter pidFilter = new UserDefineFilter();
		pidFilter.setProperty(module.getParentkey());
		pidFilter.setOperator("is null");
		pidFilter.setValue(null);

		if (userDefineFilters == null)
			userDefineFilters = new ArrayList<UserDefineFilter>();
		userDefineFilters.add(0, pidFilter);

		generateParam.setUserDefineFilters(userDefineFilters);
		generateParam.setSortParameters(sortParameters);
		generateParam.setUserNavigateFilters(userNavigateFilters);
		generateParam.setUserParentFilters(userParentFilters);

		BaseModule baseModule = new ModuleHierarchyGenerate().genModuleHierarchy(module, generateParam,
				ModuleHierarchyCreateMode.normal, null);

		SQLGenerate generator = new SQLGenerate(baseModule, generateParam);
		JSONArray jsonArray = getData(generator, -1, -1);
		baseModule.setUserNavigateFilters(null);
		baseModule.setUserParentFilters(null);
		generateParam.setHasParent(true);
		for (int i = 0; i < jsonArray.size(); i++) {
			Map<String, Object> object = (Map<String, Object>) jsonArray.get(i);
			String id = object.get(module.getPrimarykey()).toString();
			JSONArray childs = getTreeModuleDataWithPid(module, baseModule, generateParam, id);
			if (childs.size() == 0) {
				object.put("leaf", true);
			} else {
				object.put("leaf", false);
				object.put("expanded", true);
				object.put(CHILDREN, childs);
			}
		}
		JSONObject result = new JSONObject();
		result.put("text", "root");
		System.out.println(jsonArray.toJSONString());
		result.put(CHILDREN, jsonArray);
		return result;

	}

	@SuppressWarnings("unchecked")
	public JSONArray getTreeModuleDataWithPid(FDataobject module, BaseModule baseModule, GenerateParam generateParam,
			String id) {

		UserDefineFilter pidFilter = baseModule.getUserDefineFilters().get(0);
		pidFilter.setOperator("=");
		pidFilter.setValue(id);

		SQLGenerate generator = new SQLGenerate(baseModule, generateParam);
		JSONArray jsonArray = getData(generator, -1, -1);
		for (int i = 0; i < jsonArray.size(); i++) {
			Map<String, Object> object = (Map<String, Object>) jsonArray.get(i);
			String _id = object.get(module.getPrimarykey()).toString();
			JSONArray childs = getTreeModuleDataWithPid(module, baseModule, generateParam, _id);
			if (childs.size() == 0) {
				object.put("leaf", true);
			} else {
				object.put("leaf", false);
				object.put(CHILDREN, childs);
			}
		}
		return jsonArray;
	}

	public DataFetchResponseInfo getModuleData(String moduleName, Integer start, Integer limit,
											   List<UserDefineFilter> userDefineFilters, List<UserNavigateFilter> userNavigateFilters,
											   List<UserParentFilter> userParentFilters, List<SortParameter> sortParameters, List<GridColumn> gridColumns,
											   HttpServletRequest request) {

		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		GenerateParam generateParam = new GenerateParam();

		generateParam.setUserDefineFilters(userDefineFilters);
		generateParam.setSortParameters(sortParameters);
		generateParam.setUserNavigateFilters(userNavigateFilters);
		generateParam.setUserParentFilters(userParentFilters);
		generateParam.setGridColumns(gridColumns);

		UserDefineFilter queryFilter = null;
		if (generateParam.getUserDefineFilters() != null) {
			for (UserDefineFilter filter : generateParam.getUserDefineFilters())
				if (filter.getProperty().equals("_query_")) {
					generateParam.setQuery(filter.getValue());
					queryFilter = filter;
					break;
				}
		}
		if (queryFilter != null)
			generateParam.getUserDefineFilters().remove(queryFilter);

		BaseModule baseModule = new ModuleHierarchyGenerate().genModuleHierarchy(module, generateParam,
				ModuleHierarchyCreateMode.normal, null);

		SQLGenerate generator = new SQLGenerate(baseModule, generateParam);

		Integer totalRow = getRecordCount(generator);
		log.debug("统计计录个数:" + totalRow);

		Integer startRow = start;
		Integer endRow = start + limit - 1;
		endRow = Math.min(endRow, totalRow - 1);
		JSONArray jsonArray;
		if (totalRow > 0)
			jsonArray = getData(generator, startRow, endRow);
		else
			jsonArray = new JSONArray();
		DataFetchResponseInfo response = new DataFetchResponseInfo();
		response.setStartRow(startRow);
		response.setEndRow(endRow);
		response.setTotalRows(totalRow);
		response.setMatchingObjects(jsonArray);
		return response;

	}

	public Integer getRecordCount(SQLGenerate generator) {
		String sql = generator.generateCountSql();
		@SuppressWarnings("rawtypes")
		Query query = dao.getCurrentSession().createNativeQuery(sql);
		return ((BigInteger) query.getSingleResult()).intValue();
	}

	public JSONArray getRecords(String moduleName, List<UserDefineFilter> userDefineFilters,
			List<UserNavigateFilter> userNavigateFilters) {
		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		GenerateParam generateParam = new GenerateParam();
		generateParam.setUserDefineFilters(userDefineFilters);
		generateParam.setUserNavigateFilters(userNavigateFilters);
		BaseModule baseModule = new ModuleHierarchyGenerate().genModuleHierarchy(module, generateParam,
				ModuleHierarchyCreateMode.normal, null);
		SQLGenerate generator = new SQLGenerate(baseModule, generateParam);
		return getData(generator.generateSql(), generator.getFields().keySet(), -1, -1);
	}

	public JSONArray getRecords(String moduleName, UserDefineFilter userDefineFilter) {
		List<UserDefineFilter> userDefineFilters = new ArrayList<UserDefineFilter>();
		userDefineFilters.add(userDefineFilter);
		return getRecords(moduleName, userDefineFilters, null);
	}

	@SuppressWarnings("unchecked")
	public List<ValueText> getRecordWithIdAndName(String moduleName, List<UserDefineFilter> userDefineFilters,
												  List<UserNavigateFilter> userNavigateFilters) {
		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		GenerateParam generateParam = new GenerateParam();
		generateParam.setUserDefineFilters(userDefineFilters);
		generateParam.setUserNavigateFilters(userNavigateFilters);
		BaseModule baseModule = new ModuleHierarchyGenerate().genModuleHierarchy(module, generateParam,
				ModuleHierarchyCreateMode.normal, null);
		SQLGenerate generator = new SQLGenerate(baseModule, generateParam);
		String sql = generator.generateIdAndNameSql();
		JSONArray jsonArray = getData(sql, generator.getFields().keySet(), -1, -1);
		List<ValueText> results = new ArrayList<ValueText>();
		for (int i = 0; i < jsonArray.size(); i++) {
			ValueText vt = new ValueText();
			vt.setValue(((Map<String, Object>) jsonArray.get(i)).get(SQLGenerate.KEYASNAME).toString());
			vt.setText(((Map<String, Object>) jsonArray.get(i)).get(SQLGenerate.NAMEASNAME).toString());
			results.add(vt);
		}
		return results;
	}

	public List<TreeValueText> getRecordWithTreeData(String moduleName, Boolean allowParentValue,
													 List<UserDefineFilter> userDefineFilters, List<UserNavigateFilter> userNavigateFilters) {

		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		List<TreeValueText> results = new ArrayList<TreeValueText>();
		if (module._isIdPidLevel()) {

			JSONObject object = getTreeModuleData(moduleName, userDefineFilters, userNavigateFilters, null, null);
			addToTreeValueText(results, (JSONArray) object.get(CHILDREN), module.getPrimarykey(), module.getNamefield(),
					allowParentValue);
		} else {
			List<ValueText> vts = getRecordWithIdAndName(moduleName, userDefineFilters, userNavigateFilters);
			for (ValueText vt : vts) {
				results.add(new TreeValueText(vt.getValue(), vt.getText()));
			}
			List<TreeValueText> deleted = new ArrayList<TreeValueText>();
			for (int i = results.size() - 1; i > 0; i--) {

				TreeValueText record = results.get(i);
				for (int j = i - 1; j >= 0; j--) {
					TreeValueText p = results.get(j);
					if (record.getValue().startsWith(p.getValue())) {
						p.getChildren().add(0, record);
						p.setExpanded(true);
						p.setDisabled(!allowParentValue && true);
						p.setLeaf(false);
						deleted.add(record);
						break;
					}
				}
			}
			results.removeAll(deleted);
		}
		return results;

	}

	public List<String> getAllChildKeys(String moduleName, String pid) {
		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		GenerateParam generateParam = new GenerateParam();
		UserDefineFilter pidFilter = new UserDefineFilter();
		pidFilter.setProperty(module.getParentkey());
		List<UserDefineFilter> userDefineFilters = new ArrayList<UserDefineFilter>();
		userDefineFilters.add(0, pidFilter);
		generateParam.setUserDefineFilters(userDefineFilters);
		BaseModule baseModule = new ModuleHierarchyGenerate().genModuleHierarchy(module, generateParam,
				ModuleHierarchyCreateMode.normal, null);
		JSONArray array = getTreeModuleDataWithPid(module, baseModule, generateParam, pid);
		List<String> result = new ArrayList<String>();
		addchildtoarray(result, array, module.getPrimarykey());
		return result;
	}

	@SuppressWarnings("unchecked")
	private void addchildtoarray(List<String> result, JSONArray array, String key) {
		for (int i = 0; i < array.size(); i++) {
			Map<String, Object> object = (Map<String, Object>) array.get(i);
			result.add(object.get(key).toString());
			if (object.containsKey(CHILDREN)) {
				addchildtoarray(result, (JSONArray) object.get(CHILDREN), key);
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void addToTreeValueText(List<TreeValueText> results, JSONArray array, String primarykey, String namefield,
			Boolean allowParentValue) {
		for (int i = 0; i < array.size(); i++) {
			Map<String, Object> object = (Map<String, Object>) array.get(i);
			TreeValueText valuetext = new TreeValueText(object.get(primarykey).toString(),
					object.get(namefield).toString());
			if (object.containsKey(CHILDREN)) {
				valuetext.setParenttype(PARENTWITHPARENTID);
				valuetext.setChildren(new ArrayList<TreeValueText>());
				valuetext.setExpanded(true);
				valuetext.setLeaf(false);
				valuetext.setDisabled(!allowParentValue && true);
				addToTreeValueText(valuetext.getChildren(), (JSONArray) object.get(CHILDREN), primarykey, namefield,
						allowParentValue);
			}
			results.add(valuetext);
		}

	}

	public JSONObject getModuleRecord(String moduleName, String keyValue, HttpServletRequest request) {
		FDataobject module = DataObjectUtils.getDataObject(moduleName);

		GenerateParam param = new GenerateParam();
		param.setKeyValue(keyValue);

		BaseModule baseModule = new ModuleHierarchyGenerate().genModuleHierarchy(module, param,
				ModuleHierarchyCreateMode.normal, null);

		SQLGenerate generator = new SQLGenerate(baseModule, param);

		JSONArray jsonArray = getData(generator, -1, 0);
		if (jsonArray.size() > 0)
			return jsonArray.getJSONObject(0);
		else
			return null;
	}

	public JSONArray getData(SQLGenerate generator, Integer startRow, Integer endRow) {
		String sql = generator.generateSql();
		JSONArray resultArray = new JSONArray();
		String[] fields = generator.getFields().keySet().toArray(new String[] {});
		if (startRow != -1) {
			PageInfo<Map<String, Object>> info = dao.executeSQLQueryPage(sql, fields, startRow, endRow - startRow + 1);
			resultArray.addAll(info.getData());
		} else {
			resultArray.addAll(dao.executeSQLQuery(sql, fields, new Object[] {}));
		}
		return resultArray;
	}

	public JSONArray getData(String sql, Set<String> scales, Integer startRow, Integer endRow) {
		JSONArray resultArray = new JSONArray();
		String[] fields = scales.toArray(new String[] {});
		if (startRow != -1) {
			PageInfo<Map<String, Object>> info = dao.executeSQLQueryPage(sql, fields, startRow, endRow - startRow + 1);
			resultArray.addAll(info.getData());
		} else {
			resultArray.addAll(dao.executeSQLQuery(sql, fields, new Object[] {}));
		}
		return resultArray;
	}

}
