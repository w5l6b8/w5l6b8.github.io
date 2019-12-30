/**
 * 显示在顶部的按钮菜单，可以切换至标准菜单，菜单树
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

Ext.define('app.view.platform.central.menu.SettingMenu', {
	extend : 'expand.ux.ButtonTransparent',
	alias : 'widget.settingmenu',

	requires : [ 'Ext.menu.Separator', 'app.view.platform.central.menu.GridSettingForm',
			'app.view.platform.central.menu.FormSettingForm' ],
	uses : [ 'app.utils.Monetary' ],
	text : '设置',
	iconCls : 'x-fa fa-cog fa-spin',
	tooltip : '系统偏好设置',
	autoRender : false,

	initComponent : function() {
		this.menu = [];
		this.menu.push({
			text : '菜单样式',
			menu : [ {
				xtype : 'segmentedbutton',
				bind : {
					value : '{menuType}' // 在外界改变了菜单样式之后，这里会得到反应
				},
				items : [ {
					text : '标准菜单',
					value : 'toolbar'
				}, {
					text : '树形菜单',
					value : 'tree'
				}, {
					text : '按钮菜单',
					value : 'button'
				} ]
			} ]
		}, {

			text : '整体布局结构',
			menu : [ {
				xtype : 'segmentedbutton',
				bind : {
					value : '{borderType}'
				},
				items : [ {
					text : '默认方式',
					value : 'normal'
				}, {
					text : '紧凑方式',
					value : 'tree',
					tooltip : 'grid和form将以更紧凑的方式来进行布局，用于某些分辨率太小的场合。'
				} ]
			} ]

		}, {
			text : '主标签页设置',
			iconCls : 'x-fa fa-retweet',
			menu : [ {
				text : '位置',
				menu : [ {
					xtype : 'segmentedbutton',
					bind : {
						value : '{centerTabPosition}'
					},
					defaultUI : 'default',
					items : [ {
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
					} ]
				} ]
			}, {
				text : '文字旋转',
				menu : [ {
					xtype : 'segmentedbutton',
					bind : {
						value : '{centerTabRotation}'
					},
					defaultUI : 'default',
					items : [ {
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
					} ]
				} ]
			} ]
		}, {
			text : '列表设置',
			iconCls : 'x-fa fa-list',
			menu : [ {
				xtype : 'gridsettingform'
			} ]
		}, {
			text : '表单设置',
			iconCls : 'x-fa fa-list-alt',
			menu : [ {
				xtype : 'formsettingform'
			} ]
		}, {
			text : '导航设置',
			menu : [ {
				text : '显示模式',
				menu : [ {
					xtype : 'segmentedbutton',
					bind : {
						value : '{navigateMode}'
					},
					defaultUI : 'default',
					items : [ {
						text : '单列导航树',
						value : 'tree'
					}, {
						text : '双列导航树',
						value : 'treegrid'
					} ]
				} ]
			} ]
		}, '-', {
			text : '全部设为默认值',
			handler : 'onResetConfigClick'
		});
		this.callParent();
	}
});