Ext.define('app.view.platform.module.form.panel.EditForm', {
	extend : 'app.view.platform.module.form.panel.BaseForm',
	alternateClassName : 'editForm',
	alias : 'widget.editform',

	initComponent : function(){
		var me = this;
		me.config.operatetype = 'edit';
		me.config.formtypetext = '修改';

		me.buttons_ = ['->', {
			text : '保存',
			disabled : true,
			iconCls : 'x-fa fa-floppy-o',
			id : me.getId() + "saveedit",
			scope : me,
			handler : me.saveEdit
		}, ' ', {
			text : '上一条',
			iconCls : 'x-fa fa-chevron-left',
			scope : me,
			hidden : true,
			id : me.getId() + "editprior",
			handler : me.selectPriorRecord
		}, {
			text : '下一条',
			iconCls : 'x-fa fa-chevron-right',
			scope : me,
			hidden : true,
			id : me.getId() + "editnext",
			handler : me.selectNextRecord
		}];
		me.callParent(arguments);
	},

	listeners : {
		'dirtychange' : function(form, dirty, eOpts){
			var id = form.owner.getId();
			form.owner.down('button[id=' + id + 'saveedit' + ']')[dirty ? 'enable' : 'disable']()
			form.owner.down('button[id=' + id + 'editprior' + ']')[!dirty ? 'enable' : 'disable']()
			form.owner.down('button[id=' + id + 'editnext' + ']')[!dirty ? 'enable' : 'disable']()
		}
	}
})