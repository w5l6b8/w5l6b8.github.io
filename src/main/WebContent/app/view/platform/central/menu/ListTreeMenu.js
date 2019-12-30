
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
Ext.define('app.view.platform.central.menu.ListTreeMenu', {
  
	  extend : 'Ext.list.Tree',

    requires : ['app.view.platform.central.menu.ListTreeMenuStore'],
    
	  reference : 'navigationTreeList',
	  itemId : 'navigationTreeList',
	  ui : 'navigation',
	  store :  Ext.create('app.view.platform.central.menu.ListTreeMenuStore'),
	  width : 250,
	  expanderFirst : false,
	  expanderOnly : false,
    singleExpand : true

    
  })