
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
Ext.define('app.view.platform.central.menu.FormSettingForm', {
	extend : 'Ext.form.Panel',
	alias : 'widget.formsettingform',
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
	title : '表单参数设置',
	iconCls : 'x-fa fa-list-alt',
	items : [ {
		xtype : 'label',
		text : '新增窗口必填字段：',
		tdAttrs : {
			width : 150,
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{validbeforenew}'
		},
		defaultUI : 'default',
		items : [ {
			text : '标注红✻号',
			value : 'mark*'
		}, {
			text : '标注在录入框',
			value : 'markfield'
		} ]
	}, {
		xtype : 'label',
		text : '错误信息显示位置：',
		tdAttrs : {
			width : 150,
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{msgTarget}'
		},
		defaultUI : 'default',
		items : [ {
			text : '弹出式',
			value : 'qtip'
		}, {
			text : '字段后',
			value : 'side'
		}, {
			text : '字段下',
			value : 'under'
		}, {
			text : '不提示',
			value : 'none'

		} ]
	}, {
		xtype : 'label',
		text : '字段提示信息：',
		tdAttrs : {
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{fieldtooltip}'
		},
		defaultUI : 'default',
		items : [ {
			text : '显示',
			tooltip : '显示字段所设置的提示内容。',
			value : 'on'
		}, {
			text : '不显示',
			tooltip : '不显示字段所设置的提示内容。',
			value : 'off'
		} ]
	},

	{
		xtype : 'label',
		text : '显示父级按钮：',
		tdAttrs : {
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{displayparentbutton}'
		},
		defaultUI : 'default',
		items : [ {
			text : '显示',
			tooltip : '在父模块字段后面加一个按钮，可以显示父模块的信息。',
			value : 'on'
		}, {
			text : '隐藏',
			value : 'off'
		} ]
	},

	{
		xtype : 'label',
		text : '新增保存后：',
		tdAttrs : {
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{afternewsave}'
		},
		defaultUI : 'default',
		items : [ {
			text : '默认',
			value : 'default'
		}, {
			text : '继续新增',
			value : 'new'
		}, {
			text : '复制新增',
			value : 'newwithcopy'
		} ]
	}, {
		xtype : 'label',
		text : '修改保存后：',
		tdAttrs : {
			align : 'right'
		}
	}, {
		xtype : 'segmentedbutton',
		bind : {
			value : '{aftereditsave}'
		},
		defaultUI : 'default',
		items : [ {
			text : '默认',
			value : 'default'
		}, {
			text : '修改下一条',
			value : 'next'
		}, {
			text : '关闭窗口',
			value : 'close'
		} ]
	}

	]

})