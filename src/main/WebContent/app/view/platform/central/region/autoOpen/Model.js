
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
Ext.define('app.view.platform.central.region.autoOpen.Model', {
	  fields : ['id', 'type', 'objectid', 'focused'],
	  extend : 'Ext.data.Model',
	  proxy : {
		  type : 'localstorage',
		  id : '_autoOpenModules_'
	  }
  });