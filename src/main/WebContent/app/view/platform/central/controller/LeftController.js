/**
 * 
 * 用来管理left界面菜单样式的转换等功能
 * 
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
Ext.define('app.view.platform.central.controller.LeftController', {
	extend : 'Ext.Mixin',

	init : function(){
		
	},
	
	expandTreeMenu : function() {
		this.lookupReference('mainmenutree').expandAll();
	},

	collapseTreeMenu : function() {
		this.lookupReference('mainmenutree').collapseAll();
	},

	setAccordionMenu : function(tool) {		
		var panel = this.lookupReference('mainmenuregion');
		panel.down('mainmenuaccordion').show();
		panel.down('mainmenutree').hide();
		tool.hide();
		tool.nextSibling().show();
		
		var expandtool = this.lookupReference('expandtool');
		expandtool.hide();
		expandtool.nextSibling().hide();
		
	},

	setTreeMenu : function(tool) {
		var panel = this.lookupReference('mainmenuregion');
		panel.down('mainmenutree').show();
		panel.down('mainmenuaccordion').hide();
		tool.hide();
		tool.previousSibling().show();
		
		var expandtool = this.lookupReference('expandtool');
		expandtool.show();
		expandtool.nextSibling().show();
	}

});
