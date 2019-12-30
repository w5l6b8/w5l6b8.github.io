package net.ebaolife.husqvarna.framework.core.dataobjectquery.generate;


import net.ebaolife.husqvarna.framework.bean.SortParameter;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.filter.FilterUtils;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.filter.JsonToConditionField;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.sqlfield.SqlField;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.sqlfield.SqlFieldUtils;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectdefaultorder;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridsortschemedetail;
import net.ebaolife.husqvarna.framework.utils.CommonUtils;

import java.util.ArrayList;
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
public class OrderGenerate {

	public static List<String> generateOrder(SqlGenerate sqlGenerate) {
		List<String> result = new ArrayList<String>();

		if (sqlGenerate.getGroup() != null && sqlGenerate.getGroup().getProperty() != null
				&& sqlGenerate.getGroup().getProperty().length() > 0) {
			SqlField field = SqlFieldUtils.getSqlFieldFromFields(sqlGenerate.getSelectfields(),
					sqlGenerate.getGroup().getProperty());
			if (field != null) {
				result.add(generateSortSql(field.getScale(), sqlGenerate.getGroup().getDirection()));
			} else
				result.add(generateSortSql(getSortSqlFromFieldname(sqlGenerate, sqlGenerate.getGroup().getProperty()),
						sqlGenerate.getGroup().getDirection()));
		}

		if (sqlGenerate.getGridsortscheme() != null) {
			for (FovGridsortschemedetail detail : sqlGenerate.getGridsortscheme().getDetails()) {
				Set<SqlField> fields = SqlFieldUtils.getSqlFieldFromParentChildField(sqlGenerate.getBaseModule(),
						detail, null, false);
				SqlField sqlfieldname = ((SqlField) fields.toArray()[0]);

				SqlField field = SqlFieldUtils.getSqlFieldFromFields(sqlGenerate.getSelectfields(),
						sqlfieldname.getFieldname());
				if (field != null) {

					result.add(generateSortSql(field.getScale(), detail.getDirection()));
				} else
					result.add(generateSortSql(sqlfieldname.getSqlstatment(), detail.getDirection()));
			}
		} else if (sqlGenerate.getSortParameters() != null) {

			for (SortParameter parameter : sqlGenerate.getSortParameters()) {
				SqlField field = SqlFieldUtils.getSqlFieldFromFields(sqlGenerate.getSelectfields(),
						parameter.getProperty());
				if (field != null) {
					result.add(generateSortSql(field.getScale(), parameter.getDirection()));
				} else
					result.add(generateSortSql(getSortSqlFromFieldname(sqlGenerate, parameter.getProperty()),
							parameter.getDirection()));
			}
		} else {

			Set<FDataobjectdefaultorder> defaultorders = sqlGenerate.getBaseModule().getModule()
					.getFDataobjectdefaultorders();
			if (defaultorders != null && defaultorders.size() > 0) {
				for (FDataobjectdefaultorder detail : defaultorders) {
					Set<SqlField> fields = SqlFieldUtils.getSqlFieldFromParentChildField(sqlGenerate.getBaseModule(),
							detail, null, false);
					SqlField sqlfieldname = ((SqlField) fields.toArray()[0]);

					SqlField field = SqlFieldUtils.getSqlFieldFromFields(sqlGenerate.getSelectfields(),
							sqlfieldname.getFieldname());
					if (field != null) {

						result.add(generateSortSql(field.getScale(), detail.getDirection()));
					} else
						result.add(generateSortSql(sqlfieldname.getSqlstatment(), detail.getDirection()));
				}
			} else {
				String orderfield = sqlGenerate.getBaseModule().getModule().getOrderfield();
				if (!CommonUtils.isEmpty(orderfield)) {
					result.add(orderfield + " asc");
				}
			}
		}
		return result.size() > 0 ? result : null;
	}

	private static String getSortSqlFromFieldname(SqlGenerate sqlGenerate, String fieldname) {
		JsonToConditionField conditionField = new JsonToConditionField();

		FilterUtils.updateFieldNameToField(sqlGenerate.getBaseModule(), conditionField, fieldname);
		Set<SqlField> fields = SqlFieldUtils.getSqlFieldFromParentChildField(sqlGenerate.getBaseModule(),
				conditionField, null, false);
		SqlField sqlfield = (SqlField) fields.toArray()[0];
		return sqlfield.getSqlstatment();
	}

	private static String generateSortSql(String fieldname, String direction) {
		return fieldname + " " + (direction == null || direction.length() == 0 ? "asc" : direction);
	}

}
