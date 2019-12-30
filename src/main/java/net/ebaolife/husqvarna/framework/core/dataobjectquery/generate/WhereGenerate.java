package net.ebaolife.husqvarna.framework.core.dataobjectquery.generate;


import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.filter.FilterUtils;

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

	public static List<String> generateWhere(SqlGenerate generate) {
		List<String> result = new ArrayList<String>();
		if (generate.getUserParentFilters() != null) {
			for (UserParentFilter pf : generate.getUserParentFilters()) {
				UserDefineFilter filter = new UserDefineFilter();
				filter.setProperty((pf.getFieldahead() == null ? "" : pf.getFieldahead() + ".") + pf.getFieldName());
				filter.setOperator(pf.getOperator());
				filter.setValue(pf.getFieldvalue());
				result.add(FilterUtils.getConditionSqlAndSetFilter(generate.getBaseModule(), filter));
			}
		}

		if (generate.getUserNavigateFilters() != null) {
			for (UserNavigateFilter nf : generate.getUserNavigateFilters()) {
				UserDefineFilter filter = new UserDefineFilter();
				filter.setProperty((nf.getFieldahead() == null ? "" : nf.getFieldahead() + ".") + nf.getFieldName());
				String fieldnameExpression = FilterUtils.getConditionSqlAndSetFilter(generate.getBaseModule(), filter);
				result.add(nf.getSqlWhereWithFieldName(fieldnameExpression));
			}
		}

		if (generate.getUserDefineFilters() != null) {
			for (UserDefineFilter filter : generate.getUserDefineFilters()) {
				result.add(FilterUtils.getConditionSqlAndSetFilter(generate.getBaseModule(), filter));
			}
		}

		if (generate.getSearchFieldQuerys() != null) {
			StringBuilder searchquerystr = new StringBuilder();
			List<String> searchqueryList = new ArrayList<String>();
			for (UserDefineFilter filter : generate.getSearchFieldQuerys()) {
				searchqueryList.add(FilterUtils.getConditionSqlAndSetFilter(generate.getBaseModule(), filter));
			}
			for (int j = 0; j < searchqueryList.size(); j++) {
				searchquerystr.append(" ( " + searchqueryList.get(j) + " ) ");
				if (j != searchqueryList.size() - 1)
					searchquerystr.append(" or ");
			}
			result.add(searchquerystr.toString());
		}

		if (generate.getIdvalue() != null) {
			UserDefineFilter filter = new UserDefineFilter();
			filter.setProperty(generate.getDataobject()._getPrimaryKeyField().getFieldname());
			filter.setOperator("eq");
			filter.setValue(generate.getIdvalue());
			result.add(FilterUtils.getConditionSqlAndSetFilter(generate.getBaseModule(), filter));
		}

		return result;
	}

}
