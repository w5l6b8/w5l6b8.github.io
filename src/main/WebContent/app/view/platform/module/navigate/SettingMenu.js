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


Ext.define('app.view.platform.module.navigate.SettingMenu', {
	    extend : 'Ext.menu.Menu',
	    alias : 'widget.navigatesettingmenu',
	    requires : ['app.view.platform.module.navigate.SettingMenuController'],
	    controller : 'navigatesettingmenucontroller',
	    floating : true,

	    initComponent : function(){

		    var me = this;

		    me.items = [me.navigate.moduleInfo.fDataobject.navigatedesign ? {
			    text : '设计新的导航方案',
			    iconCls : 'x-fa fa-plus',
          handler : 'createNavigateScheme'
		    } : null, me.navigate.moduleInfo.fDataobject.navigatedesign ? '-' : null, {
			    text : '取消所有选择的导航',
			    handler : 'clearAllFilter'
		    }, {
			    text : '刷新所有导航记录',
			    handler : 'refreshAll'
		    }, (me.navigate.moduleInfo.getNavigateSchemeCount() > 1 ? {
			    xtype : 'menucheckitem',
			    itemId : 'allselected',
			    text : '选中的导航条件都有效',
			    listeners : {
				    checkchange : 'allSelectedCheckChange'
			    }
		    } : null), '-', {
			    text : '导航显示方式',
			    menu : [{
				    xtype : 'segmentedbutton',
				    bind : {
					    value : '{navigateMode}'
				    },
				    defaultUI : 'default',
				    items : [{
					    text : '以Tab形式显示',
					    value : 'tab'
				    }, {
					    text : '以层叠形式显示',
					    value : 'acce'
				    }]
			    }]
		    }, {
			    text : '导航显示位置',
			    menu : [{
				    xtype : 'segmentedbutton',
				    bind : {
					    value : '{region}'
				    },
				    defaultUI : 'default',
				    items : [{
					    text : '左边',
					    value : 'west'
				    }, {
					    text : '右边',
					    value : 'east'
				    }]
			    }]
		    }, {
			    text : '标签页设置',
			    iconCls : 'x-fa fa-retweet',
			    menu : [{
				    text : '位置',
				    menu : [{
					    xtype : 'segmentedbutton',
					    bind : {
						    value : '{tabPosition}'
					    },
					    defaultUI : 'default',
					    items : [{
						    text : '上边',
						    value : 'top'
					    }, {
						    text : '左边',
						    value : 'left'
					    }, {
						    text : '下面',
						    value : 'bottom'
					    }, {
						    text : '右边',
						    value : 'right'
					    }]
				    }]
			    }, {
				    text : '文字旋转',
				    menu : [{
					    xtype : 'segmentedbutton',
					    bind : {
						    value : '{tabRotation}'
					    },
					    defaultUI : 'default',
					    items : [{
						    text : '默认',
						    value : 'default'
					    }, {
						    text : '不旋转',
						    value : 0
					    }, {
						    text : '旋转90度',
						    value : 1
					    }, {
						    text : '旋转270度',
						    value : 2
					    }]
				    }]
			    }]
		    }];
		    me.callParent(arguments);
	    }
    });