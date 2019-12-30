
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
Ext.define('app.view.platform.central.menu.GridSettingForm', {
	extend : 'Ext.form.Panel',
	alias : 'widget.gridsettingform',
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
	title : '列表参数设置',
	iconCls : 'x-fa fa-list',
	items : [ {
		xtype : 'label',
		text : '数值单位：',
		tdAttrs : {
			width : 150,
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{monetary}'
		},
		defaultUI : 'default',
		items : app.utils.Monetary.getMonetaryMenu()
	},

	{
		xtype : 'label',
		text : '数值单位位置：',
		tdAttrs : {
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{monetaryposition}'
		},
		defaultUI : 'default',
		items : [ {
			text : '显示在数值后',
			tooltip : '数值单位信息显示在每个数据的后面',
			value : 'behindnumber'
		}, {
			text : '显示在列头上',
			tooltip : '数值单位信息显示在每个列的名称后面',
			value : 'columntitle'
		} ]
	},

	{
		xtype : 'label',
		text : '列宽自动调整：',
		tdAttrs : {
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{autoColumnMode}'
		},
		defaultUI : 'default',
		items : [ {
			text : '首次加载',
			tooltip : '第一次加载数据后列宽自动调整',
			value : 'firstload'
		}, {
			text : '每次加载',
			tooltip : '每一次加载到数据后列宽都自动调整(低效率)',
			value : 'everyload'
		}, {
			text : '禁止自动调整',
			value : 'disable'
		} ]
	},

	{
		xtype : 'label',
		text : '自动选中记录：',
		tdAttrs : {
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{autoselectrecord}'
		},
		defaultUI : 'default',
		items : [ {
			text : '每次加载',
			tooltip : '每次加载到数据的时候都选中第一条记录',
			value : 'everyload'
		}, {
			text : '单条选中',
			tooltip : '加载的数据只有一条时选中',
			value : 'onlyone'
		}, {
			text : '不自动选择',
			value : 'disable'
		} ]
	},

	{
		xtype : 'label',
		text : '鼠标双击记录操作：',
		tdAttrs : {
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{rowdblclick}'
		},
		defaultUI : 'default',
		items : [ {
			text : '显示',
			value : 'display',
			tooltip : '显示当前记录'
		}, {
			text : '修改',
			value : 'edit',
			tooltip : '修改当前记录'
		}, {
			text : '明细',
			value : 'showdetail',
			tooltip : '显示当前记录明细'
		}, {
			text : '复制',
			value : 'copycelltext',
			tooltip : '复制当前单元格的内容到剪切板'
		} ]
	},

	{
		xtype : 'label',
		text : '选择记录方式：',
		tdAttrs : {
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{selModel}'
		},
		defaultUI : 'default',
		items : [ {
			text : '多选框',
			value : 'checkboxmodel-MULTI'
		}, {
			text : '单选框',
			value : 'checkboxmodel-SINGLE'
		}, {
			text : '行多选',
			value : 'rowmodel-MULTI'
		}, {
			text : '行单选',
			value : 'rowmodel-SINGLE'
		} ]
	}

	]

})