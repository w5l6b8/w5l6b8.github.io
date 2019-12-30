package net.ebaolife.husqvarna.framework.core.jdbc.support.mysql;

import net.ebaolife.husqvarna.framework.bean.Name;
import net.ebaolife.husqvarna.framework.bean.TableBean;
import net.ebaolife.husqvarna.framework.bean.TableFieldBean;
import net.ebaolife.husqvarna.framework.dao.Dao;
import net.ebaolife.husqvarna.framework.utils.CommonUtils;
import net.ebaolife.husqvarna.framework.utils.ProjectUtils;
import org.hibernate.metamodel.internal.EntityTypeImpl;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MySQL5SqlFunction {

	private static MySQL5SqlFunction conver;

	private MySQL5SqlFunction() {
	}

	public static MySQL5SqlFunction getInstance() {
		if (conver == null)
			buildConver();
		return conver;
	}

	private synchronized static void buildConver() {
		if (conver != null)
			return;
		conver = new MySQL5SqlFunction();
	}

	public TableBean getTable(Dao dao, String tablename, String schema) {
		TableBean bean = null;
		try {
			Connection conn = dao.getConnection();
			schema = (schema == null || schema.length() == 0) ? conn.getCatalog() : schema;
			String sql = "select a.table_name as tablename, a.table_comment as comment from information_schema.tables a where a.TABLE_SCHEMA = ? and a.TABLE_NAME = ?";
			bean = dao.executeSQLQueryFirst(sql, TableBean.class, schema, tablename);
			if (bean == null)
				return null;
			EntityTypeImpl<?> entity = ProjectUtils.getEntityMap(dao)
					.get(CommonUtils.underlineToCamelhump(tablename).toLowerCase());
			if (entity != null)
				bean.setClassname(entity.getTypeName());
			sql = "select " + "		a.column_name as fieldname, " + "		a.data_type as fieldtype, "
					+ "		ifnull(a.character_maximum_length, 0 ) as fieldlen, "
					+ "		case when a.is_nullable = 'NO' then true else false end as isrequired, "
					+ "		a.column_comment as comments, " + "		b.referenced_table_name as jointable, "
					+ "		b.referenced_column_name as joincolumnname, " + " 		a.column_key as by1 " + " from "
					+ " information_schema.columns a "
					+ " left join information_schema.key_column_usage b on a.table_schema = b.table_schema and a.table_name = b.table_name and"
					+ " a.column_name = b.column_name and b.referenced_table_name is not null"
					+ " left join F_SystemInfo c on c.companyid = '00' "
					+ " where a.table_schema = ? and a.table_name = ? order by a.ordinal_position ";
			List<TableFieldBean> fields = dao.executeSQLQuery(sql, TableFieldBean.class, schema, tablename);
			for (TableFieldBean field : fields) {
				if ("PRI".equals(field.getBy1())) {
					bean.setPrimarykey(field.getFieldname());
				}
				if (!CommonUtils.isEmpty(field.getJointable())) {
					field.setFieldrelation("manytoone");
				}
			}
			bean.setFields(fields);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}

	public List<String> getSchemas(Dao dao) {
		List<String> result = new ArrayList<String>();
		String sql = "select schema_name as name from information_schema.schemata a where"
				+ " a.schema_name not in('information_schema','performance_schema','sys','mysql') ";
		for (Name name : dao.executeSQLQuery(sql, Name.class))
			result.add(name.getName());
		return result;
	}

	public List<String> getTables(Dao dao, String schema) {
		List<String> result = new ArrayList<String>();
		String sql = "select table_name as name from information_schema.tables"
				+ " where table_type='BASE TABLE' and table_schema = ? ";
		for (Name name : dao.executeSQLQuery(sql, Name.class, schema))
			result.add(name.getName());
		return result;
	}

	public List<String> getViews(Dao dao, String schema) {
		List<String> result = new ArrayList<String>();
		String sql = "select table_name as name from information_schema.tables where table_type='VIEW' and table_schema = ? ";
		for (Name name : dao.executeSQLQuery(sql, Name.class, schema))
			result.add(name.getName());
		return result;
	}

	public String getFKConstraintTableName(Dao dao, String tablename, String message, String schema) {
		String fkname = CommonUtils.getConstraintName(message, "FK_");
		Connection conn = dao.getConnection();
		try {
			schema = (schema == null || schema.length() == 0) ? conn.getCatalog() : schema;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		if (fkname != null) {
			String sql = " select table_name as name from information_schema.key_column_usage "
					+ "where constraint_name= ? and table_schema = ? ";
			Name name = dao.executeSQLQueryFirst(sql, Name.class, fkname , schema);

			return name.getName();
		} else
			return null;
	}

	public Map<String, Map<String, Object>> getAllKeyInfo(Dao dao, String schema) {
		Map<String, Map<String, Object>> resultMap = new HashMap<String, Map<String, Object>>();
		Connection conn = dao.getConnection();
		try {
			schema = (schema == null || schema.length() == 0) ? conn.getCatalog() : schema;
			String sql = "select DISTINCT * from ( " + " SELECT  "
					+ "     a.TABLE_SCHEMA,a.TABLE_NAME,a.COLUMN_NAME,a.INDEX_NAME " + " FROM "
					+ "     information_schema.statistics a " + " UNION all " + " SELECT  "
					+ "     a.TABLE_SCHEMA,a.TABLE_NAME,a.COLUMN_NAME,a.CONSTRAINT_NAME as INDEX_NAME " + " FROM "
					+ "     INFORMATION_SCHEMA.KEY_COLUMN_USAGE a  " + " ) m  " + " where m.TABLE_SCHEMA ='" + schema
					+ "'";

			List<Map<String, Object>> dataList = dao.executeSQLQuery(sql);
			for (int i = 0; i < dataList.size(); i++) {
				Map<String, Object> map = dataList.get(i);
				String table_name = ((String) map.get("TABLE_NAME")).toLowerCase();
				String column_name = (String) map.get("COLUMN_NAME");
				String index_name = (String) map.get("INDEX_NAME");
				if (!resultMap.containsKey(table_name)) {
					resultMap.put(table_name, new HashMap<String, Object>());
				}
				String v = (String) resultMap.get(table_name).get(index_name);
				if (!CommonUtils.isEmpty(v)) {
					resultMap.get(table_name).put(index_name, v + "," + column_name);
				} else {
					resultMap.get(table_name).put(index_name, column_name);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return resultMap;
	}
}
