Ext.define('app.view.platform.module.form.panel.NewForm', {
	  extend : 'app.view.platform.module.form.panel.BaseForm',
	  alternateClassName : 'newForm',
	  alias : 'widget.newform',

	  initComponent : function(){
		  var me = this;
		  me.config.operatetype = 'new';
		  me.config.formtypetext = '新增';

		  me.buttons_ = ['->', {
			      text : '保存',
			      iconCls : 'x-fa fa-floppy-o',
			      id : me.getId() + "savenew",
			      scope : me,
			      disabled : true,
			      handler : me.saveNew
		      }, ' ', {
			      text : '继续新增',
			      xtype : 'splitbutton',
			      iconCls : 'x-fa fa-plus',
			      id : me.getId() + "newnext",
			      disabled : true,
			      scope : me,
			      handler : function(button){
				      me.setReadOnly(false);
				      me.setFormData(null);
			      },
			      menu : [{
				          text : '复制新增',
				          iconCls : 'x-fa fa-newspaper-o',
				          scope : me,
				          handler : function(button){
					          me.setReadOnly(false);
					          var k = me.getForm().findField(me.config.fDataobject.primarykey);
					          if (k) {
						          k.setValue(null);
						          k.clearInvalid();
					          }
					          var id = me.getId();
					          me.down('button[id=' + id + 'savenew' + ']').setDisabled(false);
					          me.down('button[id=' + id + 'newnext' + ']').setDisabled(true);
				          }
			          }]
		      }];
		  me.callParent(arguments);
	  },

	  /**
		 * 初始化数据
		 * @param {} obj
		 */
	  initData : function(obj, copyed){
		  var me = this;
		  this.isSourceGrid = false;
		  if (obj instanceof Ext.panel.Table) { // 传递grid对象，并且显示选择的数据
			  me.isSourceGrid = true;
			  me.gridPanel = obj;
			  me.dataModel = null;
			  if (copyed) {
			  	var c = {};
			  	Ext.apply(c,copyed.data);
				  me.dataModel = Ext.create(me.config.moduleinfo.model, c);
				  me.dataModel.set(me.config.moduleinfo.model.idProperty, null);
			  }
			  me.setFormData(me.dataModel);
		  } else if (obj instanceof Ext.data.Model) { // 外部直接传递Model对象
			  me.dataModel = obj;
			  me.setFormData(obj);
		  } else if (Ext.isObject(obj)) { // 外部直接传递对象
			  me.dataModel = Ext.create(me.config.moduleinfo.model, obj);
			  me.setFormData(me.dataModel);
		  } else {
			  me.setFormData(null);
		  }
		  var id = me.getId();
		  me.down('button[id=' + id + 'savenew' + ']').setDisabled(false);
		  me.down('button[id=' + id + 'newnext' + ']').setDisabled(true);
	  },

	  listeners : {
		  'dirtychange' : function(form, dirty, eOpts){
			  var id = form.owner.getId();
			  form.owner.down('button[id=' + id + 'savenew' + ']')[dirty ? 'enable' : 'disable']()
			  form.owner.down('button[id=' + id + 'newnext' + ']')[!dirty ? 'enable' : 'disable']()
		  }
	  }
  })