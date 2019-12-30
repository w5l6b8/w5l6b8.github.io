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

Ext.define('app.view.platform.frame.system.userlimit.SetPanel', {
	  extend : 'app.view.platform.frame.system.rolelimit.Panel',
	  alias : 'widget.userlimitsettingpanel',

	  title : '用户权限设置',
	  storeUrl : 'platform/userrole/getuserlimit.do',
	  updateUrl : 'platform/userrole/saveuserlimit.do'
    
  })