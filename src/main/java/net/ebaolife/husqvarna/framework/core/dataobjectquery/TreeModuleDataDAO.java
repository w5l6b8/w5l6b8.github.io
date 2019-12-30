package net.ebaolife.husqvarna.framework.core.dataobjectquery;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.SortParameter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.generate.SqlGenerate;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
@Repository
public class TreeModuleDataDAO{

	public static final String CHILDREN = "children";

	//@Autowired
	private DaoImpl dao;

	@SuppressWarnings("unchecked")
	public JSONObject getTreeModuleData(String moduleName, List<UserDefineFilter> userDefineFilters,
										List<UserNavigateFilter> userNavigateFilters, List<UserParentFilter> userParentFilters,
										List<SortParameter> sortParameters) {
		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		SqlGenerate generate = new SqlGenerate();
		generate.setDataobject(module);
		generate.addAllFields();
		generate.setUserDefineFilters(userDefineFilters);
		generate.setSortParameters(sortParameters);
		generate.setUserNavigateFilters(userNavigateFilters);
		generate.setUserParentFilters(userParentFilters);
		generate.pretreatment();
		List<?> result = getTreeData(generate.generateSelect(), generate.getFieldNames());
		String keyfieldname = module._getPrimaryKeyField().getFieldname();
		if (module._isCodeLevel()) {
			Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
			for (Object object : result) {
				Map<String, Object> dataMap = (Map<String, Object>) object;
				maps.put(dataMap.get(keyfieldname).toString(), dataMap);
			}
			String[] levelstrs = module.getCodelevel().split(",");
			int[] levellen = new int[levelstrs.length];
			for (int i = 0; i < levelstrs.length; i++) {
				levellen[i] = Integer.parseInt(levelstrs[i]);
				if (i > 0)
					levellen[i] += levellen[i - 1];
			}
			for (int k = result.size() - 1; k >= 0; k--) {
				Map<String, Object> dataMap = (Map<String, Object>) result.get(k);
				String key = dataMap.get(keyfieldname).toString();
				int keylen = key.length();
				if (keylen == levellen[0])
					continue;
				for (int i = 1; i < levellen.length; i++) {
					if (levellen[i] == keylen) {
						Map<String, Object> parent = maps.get(key.substring(0, levellen[i - 1]));
						parent.put("leaf", false);
						parent.put("expanded", true);
						if (!parent.containsKey(CHILDREN))
							parent.put(CHILDREN, new ArrayList<Map<String, Object>>());
						((List<Map<String, Object>>) parent.get(CHILDREN)).add(0, dataMap);
						result.remove(k);
						break;
					}
				}
			}
		} else {

			String parentkeyname = module._getParentKeyField().getFieldname();
			Map<String, Map<String, Object>> maps = new HashMap<String, Map<String, Object>>();
			for (Object object : result) {
				Map<String, Object> dataMap = (Map<String, Object>) object;
				maps.put(dataMap.get(keyfieldname).toString(), dataMap);
			}
			for (int k = result.size() - 1; k >= 0; k--) {
				Map<String, Object> dataMap = (Map<String, Object>) result.get(k);
				String parentkey = dataMap.get(parentkeyname) != null ? dataMap.get(parentkeyname).toString() : null;
				if (parentkey != null) {
					Map<String, Object> parent = maps.get(parentkey);
					parent.put("leaf", false);
					parent.put("expanded", true);
					if (!parent.containsKey(CHILDREN))
						parent.put(CHILDREN, new ArrayList<Map<String, Object>>());
					((List<Map<String, Object>>) parent.get(CHILDREN)).add(0, dataMap);
					result.remove(k);
				}
			}
		}
		JSONObject jsonobject = new JSONObject();
		jsonobject.put("text", "root");
		jsonobject.put(CHILDREN, result);
		return jsonobject;
	}

	private List<?> getTreeData(String sql, String[] scales) {
		NativeQuery<?> query = dao.getCurrentSession().createNativeQuery(sql);

		Map<String, Object> param = DataObjectUtils.getSqlParameter();
		if (param != null) {
			for (String key : param.keySet()) {
				if (sql.indexOf(":" + key) != -1)
					query.setParameter(key, param.get(key));
			}
		}
		List<?> result = query.getResultList();
		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		for (int i = 0; i < result.size(); i++) {
			Object obj = result.get(i);
			Map<String, Object> dataMap = new HashMap<String, Object>();
			if (obj instanceof Object[]) {
				Object[] datas = (Object[]) obj;
				for (int j = 0; j < scales.length; j++) {
					if (scales[j] != null)
						dataMap.put(scales[j], j < datas.length ? datas[j] : null);
				}
			} else {
				dataMap.put(scales[0], obj);
			}
			dataMap.put("leaf", true);
			dataList.add(dataMap);
		}
		return dataList;
	}

}
