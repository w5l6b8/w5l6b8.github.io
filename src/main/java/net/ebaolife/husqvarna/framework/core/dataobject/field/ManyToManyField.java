package net.ebaolife.husqvarna.framework.core.dataobject.field;

import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;

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
public class ManyToManyField {

	public static String _getSelectName(BaseModule module, FDataobjectfield moduleField) {
		String fieldtype = moduleField.getFieldtype();
		fieldtype = fieldtype.substring(fieldtype.indexOf('<') + 1, fieldtype.indexOf('>'));
		FDataobject otherSideModule = DataObjectUtils.getDataObject(fieldtype);
		FDataobject joinTableModule = DataObjectUtils.getDataObject(moduleField.getJointable());
		String sql = " (select GROUP_CONCAT( otherSide.%s,',',otherSide.%s ,',', joinTable.%s  SEPARATOR '|||') "
				+ " from %s joinTable inner join %s otherSide on joinTable.%s = otherSide.%s "
				+ "where joinTable.%s = %s.%s )";
		if (otherSideModule == null || joinTableModule == null)
			return "' ' ";
		else
			return String.format(sql, otherSideModule.getPrimarykey(), otherSideModule.getNamefield(),
					joinTableModule.getPrimarykey(), joinTableModule.getTablename(), otherSideModule.getTablename(),
					otherSideModule.getPrimarykey(), otherSideModule.getPrimarykey(),
					module.getModule().getPrimarykey(), module.getAsName(), module.getModule().getPrimarykey());

	}
}
