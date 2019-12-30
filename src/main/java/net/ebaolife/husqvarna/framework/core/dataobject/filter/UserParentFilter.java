package net.ebaolife.husqvarna.framework.core.dataobject.filter;

import com.alibaba.fastjson.JSON;
import net.ebaolife.husqvarna.framework.dao.entity.attachment.FDataobjectattachment;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;

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
public class UserParentFilter extends UserNavigateFilter {

	private static final long serialVersionUID = -4604317461593860035L;

	public static List<UserParentFilter> changeToParentFilters(String parentFilter, String moduleName) {
		if (parentFilter != null && parentFilter.length() > 1) {
			UserParentFilter pFilter = null;
			if (parentFilter != null && parentFilter.length() > 1) {
				pFilter = JSON.parseObject(parentFilter, UserParentFilter.class);
			}
			List<UserParentFilter> userParentFilters = null;
			if (pFilter != null) {
				userParentFilters = new ArrayList<UserParentFilter>();
				if (moduleName.equals(FDataobjectattachment.class.getSimpleName())) {

					UserParentFilter moduleFilter = new UserParentFilter();
					moduleFilter.setFieldName("FDataobject");
					FDataobject attachment = DataObjectUtils.getDataObject(FDataobjectattachment.class.getSimpleName());
					moduleFilter.setModuleField(attachment._getModuleFieldByFieldName(moduleFilter.getFieldName()));
					FDataobject module = DataObjectUtils.getDataObject(pFilter.getModuleName());
					moduleFilter.setOperator("=");
					moduleFilter.setFieldvalue(module.getObjectid());
					userParentFilters.add(moduleFilter);

					UserParentFilter keyFilter = new UserParentFilter();
					keyFilter.setFieldName("idvalue");
					keyFilter.setModuleField(attachment._getModuleFieldByFieldName("idvalue"));
					keyFilter.setOperator("=");
					keyFilter.setFieldvalue(pFilter.getFieldvalue());
					userParentFilters.add(keyFilter);
				} else {
					userParentFilters.add(pFilter);
					pFilter.setModuleField(
							pFilter.getFilterModule()._getModuleFieldByFieldName(pFilter.getFieldName()));
					pFilter.setManyToOneFieldToAheadPath();
				}
			}
			return userParentFilters;
		} else
			return null;
	}

}
