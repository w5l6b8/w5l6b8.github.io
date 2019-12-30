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

Ext.define('app.view.platform.frame.system.userlimit.AllLimitPanel', {
	  extend : 'app.view.platform.frame.system.rolelimit.Panel',
	  alias : 'widget.useralllimitdisplaypanel',

	  title : '用户所有权限',
	  storeUrl : 'platform/userrole/getuseralllimit.do',
	  updateUrl : '',

	  tbar : [{
		      iconCls : 'x-tool-expand',
		      tooltip : '展开一级',
		      handler : 'expandALevel'
	      }, {
		      iconCls : 'x-tool-collapse',
		      tooltip : '全部折叠',
		      handler : 'collapseAll'
	      }]

  })