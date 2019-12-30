package net.ebaolife.husqvarna.platform.service;

import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.utils.CommonUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service

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
public class SystemBaseCodeService {

	@Resource
	private DaoImpl dao;

	public List<Map<String, Object>> getViewList(String viewname) {
		return getViewList(viewname, null, null, null, null);
	}

	public List<Map<String, Object>> getViewList(String viewname, String ids) {
		return getViewList(viewname, ids, null, null, null);
	}

	public List<Map<String, Object>> getViewList(String viewname, String ids, String idfield, String textfield,
			String orderbyfield) {
		String id = CommonUtils.isEmpty(idfield) ? "id" : idfield;
		String text = CommonUtils.isEmpty(textfield) ? "text" : textfield;
		String sql = "select a.codecode as " + id + "," + " a.codename as " + text + ", "
				+ " a.codevalue,a.id as 'key' " + " from F_Basecode a "
				+ " inner join  F_Basecodetype b on a.codetype = b.codetype and (b.companyid is null or b.companyid = '"
				+ Local.getCompanyid() + "') " + " where b.viewname='" + viewname + "' and a.isvalid = '1'";
		if (!CommonUtils.isEmpty(ids)) {
			sql += " and a.codeid in ('" + ids.replace(",", "','") + "')";
		}
		if (!CommonUtils.isEmpty(orderbyfield)) {
			sql += "order by a." + orderbyfield + " ";
		} else {
			sql += "order by -(-a.codecode)";
		}
		return dao.executeSQLQuery(sql);
	}

	public Map<String, String> getColumnMapping(String viewname) {
		List<Map<String, Object>> list = getViewList(viewname);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> r = list.get(i);
			map.put(((String) r.get("id")).trim(), (String) r.get("text"));
		}
		return map;
	}

	public Map<String, String> getColumnMapping(String viewname, String keyfield, String namefield) {
		List<Map<String, Object>> list = getViewList(viewname);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> r = list.get(i);
			map.put(((String) r.get(keyfield)).trim(), (String) r.get(namefield));
		}
		return map;
	}

	public Map<String, String> getColumnMapping(String tablename, String keyfield, String namefield, String cdt) {
		String sql = " select " + keyfield + " as K," + namefield + " as N from " + tablename + " where 1=1 ";
		if (!CommonUtils.isEmpty(cdt))
			sql += " and " + cdt;
		List<Map<String, Object>> list = dao.executeSQLQuery(sql);
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> r = list.get(i);
			map.put(((String) r.get("K")).trim(), (String) r.get("N"));
		}
		return map;
	}

}
