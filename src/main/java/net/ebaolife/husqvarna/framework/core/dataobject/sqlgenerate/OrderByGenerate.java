package net.ebaolife.husqvarna.framework.core.dataobject.sqlgenerate;


import net.ebaolife.husqvarna.framework.bean.SortParameter;
import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.core.dataobject.ParentModule;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectdefaultorder;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;

import java.util.ArrayList;
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
public class OrderByGenerate {

	public static List<String> generateOrderby(BaseModule baseModule, List<SortParameter> sortParameters) {
		List<String> orders = new ArrayList<String>();
		if (sortParameters != null && sortParameters.size() > 0) {

			for (SortParameter sp : sortParameters)
				orders.add(getAOrder(baseModule, sp.getProperty(), sp.getDirection()));
		} else if (baseModule.getModule().getFDataobjectdefaultorders() != null
				&& baseModule.getModule().getFDataobjectdefaultorders().size() > 0) {

			for (FDataobjectdefaultorder moduleDefaultOrder : baseModule.getModule().getFDataobjectdefaultorders()) {
				String dir = moduleDefaultOrder.getDirection();
				if (dir == null)
					dir = "asc";
				if (moduleDefaultOrder.getFieldahead() == null || moduleDefaultOrder.getFieldahead().length() == 0)
					orders.add(moduleDefaultOrder.getFDataobjectfield()._getSelectName(baseModule.getAsName()) + " "
							+ dir);
				else {
					ParentModule pm = baseModule.getAllParents().get(moduleDefaultOrder.getFieldahead());
					if (pm != null)
						orders.add(moduleDefaultOrder.getFDataobjectfield()._getSelectName(pm.getAsName()) + " " + dir);
				}
			}
		}
		return orders;
	}

	public static String getAOrder(String[] orders) {
		if (orders.length == 0)
			return null;
		else if (orders.length == 1)
			return orders[0];
		else
			return orders[0] + " " + orders[1];

	}

	private static String getAOrder(BaseModule baseModule, String fieldName, String dir) {

		return baseModule.getShortScaleFormScale(fieldName) + " " + dir;

	}

	@SuppressWarnings("unused")
	private static String getAOrder_(BaseModule baseModule, String fieldName, String dir) {
		if (dir == null)
			dir = "asc";
		if (fieldName.lastIndexOf(".") > 0) {
			if (fieldName.indexOf(".with.") > 0) {

				return fieldName + " " + dir;
			} else {
				String fieldahead = fieldName.substring(0, fieldName.lastIndexOf("."));
				ParentModule pm = baseModule.getAllParents().get(fieldahead);
				FDataobjectfield field = pm.getModule()
						._getModuleFieldByFieldName(fieldName.substring(fieldName.lastIndexOf(".") + 1));
				return field._getSelectName(pm.getAsName()) + " " + dir;
			}
		} else {
			FDataobjectfield field = baseModule.getModule()._getModuleFieldByFieldName(fieldName);
			return field._getSelectName(baseModule.getAsName()) + " " + dir;
		}
	}
}
