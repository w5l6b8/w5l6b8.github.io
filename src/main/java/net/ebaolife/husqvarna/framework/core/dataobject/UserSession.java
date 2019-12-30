package net.ebaolife.husqvarna.framework.core.dataobject;

import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserGlobalFilter;

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
public class UserSession {

	public boolean isFieldHidden(String fieldId) {
		return false;
	}

	public List<UserGlobalFilter> getUserGlobalFilters() {
		
		return null;
	}
}
