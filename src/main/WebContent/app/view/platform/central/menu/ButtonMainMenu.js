/**
 * 显示在顶部的按钮菜单，可以切换至标准菜单，菜单树
 * 
 * 
 * @author jiangfeng
 * 
 * www.jhopesoft.com
 * 
 * jfok1972@qq.com
 * 
 * 2017-06-01
 * 
 */
Ext.define('app.view.platform.central.menu.ButtonMainMenu', {
	extend : 'expand.ux.ButtonTransparent',
	alias : 'widget.buttonmainmenu',

	text : '菜单',
	iconCls : 'x-fa fa-list',

	initComponent : function() {
		this.menu = this.up('appcentral').getViewModel().getMenus();
		this.callParent(arguments);
	}
});