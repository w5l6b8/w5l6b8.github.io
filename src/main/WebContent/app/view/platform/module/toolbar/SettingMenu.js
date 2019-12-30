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

Ext.define('app.view.platform.module.toolbar.SettingMenu', {
	extend : 'Ext.menu.Menu',

	alias : 'widget.toolbarsettingmenu',

	initComponent : function() {

		this.items = [ {
			xtype : 'form',
			layout : {
				type : 'table',
				columns : 2,
				tdAttrs : {
					style : {
						'padding' : '3px 3px 3px 3px',
						'border-color' : 'gray'
					}
				},
				tableAttrs : {
					border : 1,
					width : '100%',
					style : {
						'border-collapse' : 'collapse',
						'border-color' : 'gray'
					}
				}
			},
			width : 400,
			title : '工具条参数设置',
			iconCls : 'x-fa fa-list-alt',
			items : [ {
				xtype : 'label',
				text : '工具条位置：',
				tdAttrs : {
					width : 150,
					align : 'right'
				}
			}, {
				xtype : 'segmentedbutton',
				bind : {
					value : '{module.toolbar.dock}'
				},
				defaultUI : 'default',
				items : [ {
					text : '顶部',
					value : 'top'
				}, {
					text : '左边',
					value : 'left'
				}, {
					text : '底部',
					value : 'bottom'
				}, {
					text : '右边',
					value : 'right'
				} ]
			}, {
				xtype : 'label',
				text : '按钮大小：',
				tdAttrs : {
					width : 150,
					align : 'right'
				}
			}, {
				xtype : 'segmentedbutton',
				bind : {
					value : '{module.toolbar.buttonScale}'
				},
				defaultUI : 'default',
				items : [ {
					text : '标准',
					value : 'small'
				}, {
					text : '较大',
					value : 'medium'
				}, {
					text : '最大',
					value : 'large'
				} ]
			}, {
				xtype : 'label',
				text : '顶部和底部方案：',
				tdAttrs : {
					width : 150,
					align : 'right'
				}
			}, {
				xtype : 'segmentedbutton',
				bind : {
					value : '{module.toolbar.topbottomMode}'
				},
				defaultUI : 'default',
				items : [ {
					text : '标准',
					value : 'normal'
				}, {
					text : '紧凑',
					value : 'compact'
				} ]
			}, {
				xtype : 'label',
				text : '左边和右边方案：',
				tdAttrs : {
					width : 150,
					align : 'right'
				}
			}, {
				xtype : 'segmentedbutton',
				bind : {
					value : '{module.toolbar.leftrightMode}'
				},
				defaultUI : 'default',
				items : [ {
					text : '标准',
					value : 'normal'
				}, {
					text : '紧凑',
					value : 'compact'
				} ]
			}, {
				xtype : 'label',
				text : '左边和右边箭头位置：',
				tdAttrs : {
					width : 150,
					align : 'right'
				}
			}, {
				xtype : 'segmentedbutton',
				bind : {
					value : '{module.toolbar.leftrightArrowAlign}'
				},
				defaultUI : 'default',
				items : [ {
					text : '下方',
					value : 'bottom'
				}, {
					text : '右边',
					value : 'right'
				} ]
			}, {
				xtype : 'fieldcontainer',
				colspan : 2,
				items : [ {
					xtype : 'checkbox',
					margin : '0 0 0 10',
					boxLabel : '应用到我所有模块的工具条'
				}, {
					xtype : 'checkbox',
					margin : '0 0 0 10',
					boxLabel : '已设置好，以后不要再出现设置菜单'
				} ]
			} ],

			buttons : [ {
				text : '保存到服务器'
			} ]

		} ];

		this.callParent();
	}

})