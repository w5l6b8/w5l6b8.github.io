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

Ext.define('app.view.platform.module.attachment.quickupload.Window', {
	  extend : 'Ext.window.Window',
	  alias : 'widget.attachmentquickuploadwindow',

	  requires : ['app.view.platform.module.attachment.quickupload.Grid'],

	  width : 800,
	  height : 500,
	  modal : true,
	  layout : 'fit',
	  iconCls : 'x-fa fa-cloud-upload',

	  listeners : {
		  close : function(window){
			  if (window.callback) {
				  Ext.callback(window.callback, window.callbackscope);
			  }
		  },

		  show : function(window){
			  if (window.objectid && window.keyid) {
			  } else {
				  EU.toastWarn('当前记录尚未保存，请先保存后再上传附件!');
				  window.hide();
			  }
		  }

	  },

	  initComponent : function(){
		  var me = this;

		  me.title = me.objecttitle + '『' + me.keytitle + '』附件上传'

		  // var attachment = modules.getModuleInfo('fDataobjectattachment');
		  me.items = [{
			      xtype : 'attachmentquickuploadgrid',
			      files : me.files,
			      param : {
				      objectid : me.objectid,
				      objecttitle : me.objecttitle,
				      keyid : me.keyid,
				      keytitle : me.keytitle
			      }
		      }]
		  me.callParent(arguments);
	  }

  })