package net.ebaolife.husqvarna.framework.core.dataobject.sqlgenerate;


import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.core.dataobject.GenerateParam;
import net.ebaolife.husqvarna.framework.core.dataobject.ParentModule;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.grid.GridColumn;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary;

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
public class GridQueryGenerate {

	public static List<UserDefineFilter> generateQueryFilter(BaseModule baseModule, GenerateParam generateParam,
															 String queryValue) {
		List<UserDefineFilter> result = new ArrayList<UserDefineFilter>();
		List<GridColumn> gridColumns = generateParam.getGridColumns();

		for (GridColumn column : gridColumns) {

			if (column.getDataIndex() != null && column.getDataIndex().indexOf(".") == -1) {
				boolean found = false;
				for (FDataobjectfield field : baseModule.getModule().getFDataobjectfields())
					if (column.getDataIndex().equals(field.getFieldname())) {
						found = true;
						if (field.canLikeOperate()) {
							UserDefineFilter filter = new UserDefineFilter();
							filter.setProperty(column.getDataIndex());
							filter.setOperator("like");
							filter.setValue(queryValue);
							filter.setField(field);
							filter.setTableAsName(baseModule.getAsName());
							addPropertyToResult(result, filter);
							break;
						}
					}
				if (!found) {
					UserDefineFilter filter = new UserDefineFilter();
					filter.setProperty(column.getDataIndex());
					filter.setOperator("like");
					filter.setValue(queryValue);
					filter.setTableAsName(baseModule.getAsName());
					addPropertyToResult(result, filter);
				}
			}
		}

		for (String pkey : baseModule.getParents().keySet()) {
			ParentModule pm = baseModule.getParents().get(pkey);
			FDataobject pModule = pm.getModule();
			for (GridColumn column : gridColumns) {
				if (column.getDataIndex() != null && column.getDataIndex().lastIndexOf(".") > 0) {
					String fieldahead = column.getDataIndex().substring(0, column.getDataIndex().lastIndexOf("."));
					if (pm.getFieldahead().equals(fieldahead)) {
						String fn = column.getDataIndex().substring(column.getDataIndex().lastIndexOf(".") + 1);
						if (fn.endsWith(FDictionary.NAMEENDS))
							fn = fn.replace(FDictionary.NAMEENDS, "");
						FDataobjectfield field = pModule._getModuleFieldByFieldName(fn);
						if (field.canLikeOperate()) {
							UserDefineFilter filter = new UserDefineFilter();
							filter.setProperty(column.getDataIndex());
							filter.setOperator("like");
							filter.setValue(queryValue);
							filter.setField(column.getDataIndex().endsWith(FDictionary.NAMEENDS) ? null : field);
							filter.setTableAsName(pm.getAsName());
							addPropertyToResult(result, filter);
						}
					}
				}
			}
		}
		if (result.size() > 0)
			return result;
		else
			return null;
	}

	public static void addPropertyToResult(List<UserDefineFilter> result, UserDefineFilter filter) {
		for (UserDefineFilter f : result) {
			if (f.getProperty().equals(filter.getProperty()))
				return;
		}
		result.add(filter);
	}

}
