package net.ebaolife.husqvarna.framework.dao;

import net.ebaolife.husqvarna.framework.core.jdbc.JdbcAdapterFactory;
import net.ebaolife.husqvarna.framework.core.jdbc.support.mysql.MySQL5SqlFunction;
import net.ebaolife.husqvarna.framework.utils.CommonUtils;
import net.ebaolife.husqvarna.framework.utils.ProjectUtils;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

public abstract class SqlMapperAdapter {

	@Resource
	public Dao dao;

	public MySQL5SqlFunction sf;

	public SqlMapperAdapter() {
		sf = JdbcAdapterFactory.getJdbcAdapter(ProjectUtils.getInitParameter("jdbc.dbType"));
	}

	public double selectMax(String tablename, String field) {
		return selectMax(tablename, field, null);
	}

	public double selectMax(String tablename, String field, String where) {
		Object obj = selectMaxValue(tablename, field, where);
		if (obj == null)
			return 0;
		String str = obj.toString().trim();
		return str.length() > 0 ? Double.parseDouble(str) : 0;
	}

	public String getLevelid(String tablename, String field, String where, String pwhere) {
		Object obj = selectMaxValue(tablename, field, where);
		if (obj == null) {
			obj = selectMaxValue(tablename, field, pwhere);
			return obj.toString() + "01";
		}
		String levelid = obj.toString();
		int value = Integer.valueOf(levelid.substring(levelid.length() - 2, levelid.length())) + 1;
		levelid = levelid.substring(0, levelid.length() - 2) + ((value < 10) ? "0" + value : value);
		return levelid;
	}

	public Object selectMaxValue(String tablename, String field, String where) {
		String sql = "select max(" + field + ") F from " + tablename + " where 1=1 ";
		if (where != null && !("").equals(where))
			sql += " and " + where;
		List<Map<String, Object>> list = dao.executeSQLQuery(sql);
		return list.size() == 0 ? null : list.get(0).get("F");
	}

	public void updateLevel(String tablename, String levelfield, String idfield, String parentfield, String v,
			String orderfield, int length) {
		String levelid = v == null ? "" : (String) selectMaxValue(tablename, levelfield, idfield + " = '" + v + "'");
		if (levelid == null)
			return;
		String sql = "select * from " + tablename + " where "
				+ (v == null ? parentfield + " is null " : (parentfield + " = '" + v + "' "));
		if (!CommonUtils.isEmpty(orderfield))
			sql += " order by " + orderfield;
		List<Map<String, Object>> list = dao.executeSQLQuery(sql);
		if (list.size() == 0)
			return;
		String upSql = "update " + tablename + " set " + levelfield + " = ? where " + idfield + " = ?";
		for (int i = 0; i < list.size(); i++) {
			Map<String, Object> map = list.get(i);
			String code = CommonUtils.lpad(length, i + 1);
			String id = String.valueOf(map.get(idfield));
			dao.executeSQLUpdate(upSql, new Object[] { levelid + code, id });
			updateLevel(tablename, levelfield, idfield, parentfield, id, orderfield, length);
		}
	}
}
