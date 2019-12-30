/**
 * 系统首页的字义
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

Ext.define('app.view.platform.central.region.HomePage', {
	extend : 'Ext.panel.Panel',
	alias : 'widget.homepage',
	layout : 'border',

	requires : [ 'app.view.platform.central.widget.OpenRecentTree' ],

	title : '首页',
	iconCls : 'x-fa fa-home',

	frame : false,
	border : false,
	items : [ {
		title : '相关事项',
		region : 'west',
		collapsible : true,
		collapsed : true,
		split : true,
		width : 300,
		layout : 'accordion',
		header : {},
		items : [ {
			title : '待办事项'
		}, {
			xtype : 'openrecenttree',
			reference : 'openrecenttree',
			title : '最近访问过的模块'
		}, {
			title : '最近修改过的数据'
		} ]
	}, {
		region : 'center',
		layout : 'fit',
		items : [ {
			xtype : 'tabpanel',
			bodyPadding : '1 0 0 0',
			items : []

		} ]
	} ]

});