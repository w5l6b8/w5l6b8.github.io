package net.ebaolife.husqvarna.framework.core.dataobject.sqlgenerate;


import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.core.dataobject.ChildModule;
import net.ebaolife.husqvarna.framework.core.dataobject.GenerateParam;
import net.ebaolife.husqvarna.framework.core.dataobject.ParentModule;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
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
public class WhereGenerate {

	public static List<String> generateWhere(BaseModule baseModule, GenerateParam generateParam) {
		List<String> wheres = new ArrayList<String>();

		if (generateParam != null && generateParam.getKeyValue() != null) {
			wheres.add(baseModule.getAsName() + "." + baseModule.getModule()._getPrimaryKeyField()._getSelectName(null)
					+ " = '" + generateParam.getKeyValue() + "'");
		}

		if (baseModule.getUserDefineFilters() != null) {
			for (UserDefineFilter filter : baseModule.getUserDefineFilters()) {
				wheres.add(filter.getSqlWhere(baseModule.getAsName(), baseModule));
			}
		}

		if (baseModule.getUserNavigateFilters() != null) {
			for (UserNavigateFilter filter : baseModule.getUserNavigateFilters()) {
				wheres.add(filter.getSqlWhere(baseModule.getAsName()));
			}
		}

		if (baseModule.getUserParentFilters() != null) {
			for (UserParentFilter filter : baseModule.getUserParentFilters()) {
				wheres.add(filter.getSqlWhere(baseModule.getAsName()));
			}
		}

		if (generateParam == null || generateParam.isHasParent() == false) {

			for (String pmkey : baseModule.getParents().keySet()) {
				ParentModule pm = baseModule.getParents().get(pmkey);
				addParentToWheres(pm, wheres);
			}

			for (String cmkey : baseModule.getChilds().keySet()) {
				ChildModule cm = baseModule.getChilds().get(cmkey);
				addChildToWheres(cm, wheres);
			}
		}

		List<String> qs = new ArrayList<String>();
		if (baseModule.getQuerys() != null) {
			for (UserDefineFilter filter : baseModule.getQuerys())
				qs.add(filter.getSqlWhere(baseModule.getAsName(), baseModule));
		}
		for (String pmkey : baseModule.getParents().keySet()) {
			ParentModule pm = baseModule.getParents().get(pmkey);
			if (pm.getQuerys() != null) {
				for (UserDefineFilter filter : pm.getQuerys())
					qs.add(filter.getSqlWhere(pm.getAsName(), baseModule));
			}
		}

		for (String cmkey : baseModule.getChilds().keySet()) {
			ChildModule cm = baseModule.getChilds().get(cmkey);
			if (cm.getQuerys() != null) {
				for (UserDefineFilter filter : cm.getQuerys())
					qs.add(filter.getChildSqlWhere());
			}
		}

		if (qs.size() > 0) {
			StringBuffer sb = new StringBuffer("(");
			for (int i = 0; i < qs.size(); i++) {
				sb.append(qs.get(i));
				if (i != qs.size() - 1)
					sb.append(" or ");
			}
			sb.append(")");
			wheres.add(sb.toString());
		}

		if (generateParam != null && generateParam.getConditionSql() != null) {
			wheres.add(generateParam.getConditionSql());
		}

		List<String> results = new ArrayList<String>();
		if (wheres.size() > 0) {
			results.add(" where ");
			for (String s : wheres) {
				results.add(s);
				results.add(" and ");
			}
			results.remove(results.size() - 1);
		}
		return results;
	}

	private static void addChildToWheres(ChildModule childModule, List<String> wheres) {
		if (childModule == null)
			return;
		if (childModule.getUserDefineFilters() != null) {
			for (UserDefineFilter filter : childModule.getUserDefineFilters()) {
				wheres.add(filter.getChildSqlWhere());
			}
		}
		for (String cmkey : childModule.getChilds().keySet()) {
			addChildToWheres(childModule.getChilds().get(cmkey), wheres);
		}

	}

	private static void addParentToWheres(ParentModule pmodule, List<String> wheres) {

		if (pmodule.getUserDefineFilters() != null) {
			for (UserDefineFilter filter : pmodule.getUserDefineFilters()) {
				wheres.add(filter.getSqlWhere(pmodule.getAsName(), null));
			}
		}

		if (pmodule.getUserNavigateFilters() != null) {
			for (UserNavigateFilter filter : pmodule.getUserNavigateFilters()) {
				wheres.add(filter.getSqlWhere(pmodule.getAsName()));
			}
		}

		if (pmodule.getUserParentFilters() != null) {
			for (UserParentFilter filter : pmodule.getUserParentFilters()) {
				wheres.add(filter.getSqlWhere(pmodule.getAsName()));
			}
		}

		for (String pmkey : pmodule.getParents().keySet()) {
			addParentToWheres(pmodule.getParents().get(pmkey), wheres);
		}

	}

	public static List<String> generateAgreegateWhere(BaseModule baseModule, String aheadField,
			GenerateParam generateParam) {

		List<String> wheres = new ArrayList<String>();

		for (String pmkey : baseModule.getParents().keySet()) {
			ParentModule pm = baseModule.getParents().get(pmkey);
			addAggregateParentToWheres(pm, wheres, aheadField, baseModule);
		}

		if (generateParam != null && generateParam.getConditionSql() != null) {
			wheres.add(generateParam.getConditionSql());
		}

		return wheres;
	}

	private static void addAggregateParentToWheres(ParentModule pmodule, List<String> wheres, String aheadField,
			BaseModule baseModule) {

		if (aheadField.equals(pmodule.getFieldahead())) {
			FDataobjectfield field = pmodule.getModuleField();
			String sonTableAs = null;
			if (pmodule.getSonModuleHierarchy() instanceof BaseModule)
				sonTableAs = ((BaseModule) pmodule.getSonModuleHierarchy()).getAsName();
			else
				sonTableAs = ((ParentModule) pmodule.getSonModuleHierarchy()).getAsName();

			String s = String.format(" %s.%s = %s.%s", sonTableAs,
					field.getJoincolumnname() == null ? pmodule.getModule().getPrimarykey() : field.getJoincolumnname(),
					baseModule.getpModule().getAsName(), pmodule.getModule().getPrimarykey());
			wheres.add(s);
		}

		for (String pmkey : pmodule.getParents().keySet()) {
			addAggregateParentToWheres(pmodule.getParents().get(pmkey), wheres, aheadField, baseModule);
		}

	}

}
