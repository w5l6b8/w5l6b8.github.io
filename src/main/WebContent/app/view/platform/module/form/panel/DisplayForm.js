Ext.define('app.view.platform.module.form.panel.DisplayForm', {
	extend : 'app.view.platform.module.form.panel.BaseForm',
	alternateClassName : 'displayForm',
	alias : 'widget.displayform',

	initComponent : function(){
		var me = this;
		me.config.operatetype = 'display';
		me.config.formtypetext = '显示';
		me.buttons_ = ['->', {
			text : '上一条',
			itemId : 'displayprior',
			iconCls : 'x-fa fa-chevron-left',
			id : me.getId() + "editprior",
			scope : me,
			hidden : true,
			handler : me.selectPriorRecord
		}, {
			text : '下一条',
			itemId : 'displaynext',
			iconCls : 'x-fa fa-chevron-right',
			id : me.getId() + "editnext",
			scope : me,
			hidden : true,
			handler : me.selectNextRecord
		}];
		me.callParent(arguments);
	}
})
