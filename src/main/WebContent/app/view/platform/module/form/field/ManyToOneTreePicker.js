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

Ext.define('app.view.platform.module.form.field.ManyToOneTreePicker', {
	  extend : 'expand.ux.field.DataTreePicker',
	  alias : 'widget.manytoonetreepicker',

	  displayField : 'text',
	  valueField : 'value',
	  forceSelection : true,
	  triggers : {
		  comment : {
			  cls : 'x-fa fa-comment-o',
			  weight : 1,
			  handler : function(){
				  var me = this;
				  if (!me.getValue()) EU.toastWarn('模块还没有选择值！');
				  else modules.getModuleInfo(me.fieldtype).showDisplayWindow(me.getValue());
			  }
		  }

	  },
	  enableKeyEvents : true, // 如果是空格键，并且值是空，那么就弹出选择框
	  listeners : {
		  keypress : function(field, e, eOpts){
			  if (field.readOnly == false) if (e.getKey() == e.SPACE) {
				  if (field.editable == false || !field.getValue()) {
					  e.preventDefault();
					  field.expand()
				  }
			  }
		  }
	  },

	  config : {
		  fieldtype : null,
		  fieldDefine : null
	  },

	  initComponent : function(){
		  var me = this;
		  delete me.maxLength;
		  var cobject = modules.getModuleInfo(me.fieldtype).fDataobject;
		  // 设置fieldlabel
		  var icon = '';
		  if (cobject.iconcls) icon = '<span class="' + cobject.iconcls + '"/>'
		  else if (cobject.iconurl) icon = '<img src="' + cobject.iconurl + '" />';
		  var fl = me.fieldDefine ? me.fieldDefine.fieldtitle : (me.fieldLabel || cobject.title);
		  me.fieldLabel = '<span class="gridheadicon" >' + (icon ? icon + ' ' : '') + fl + '</span>';
		  me.store = Ext.create('Ext.data.TreeStore', {
			    autoLoad : true,
			    root : {},
			    fields : ['value', 'text', {
				        name : 'disabled',
				        type : 'bool',
				        defaultValue : false
			        }],
			    proxy : {
				    type : 'ajax',
				    url : 'platform/dataobject/fetchpickertreedata.do',
				    extraParams : {
					    moduleName : me.fieldtype,
					    allowParentValue : me.fieldDefine.allowparentvalue
				    }
			    },
			    moduleName : me.fieldtype,
			    allowParentValue : me.fieldDefine.allowparentvalue
		    });
		  this.callParent(arguments);
	  }
  })