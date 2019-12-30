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

Ext.define('app.view.platform.module.attachment.quickupload.FileField', {
	  extend : 'Ext.form.field.File',
	  alias : 'widget.attachmentquickuploadfilefield',

	  executeSelect : function(){
		  var f = document.getElementById(this.id);
		  var inputs = f.getElementsByTagName('input'), fileInput;
		  for (var i = 0; i < inputs.length; i++) {
			  if (inputs[i].type == 'file') {
				  inputs[i].click();
				  break;
			  }
		  }
	  },

	  listeners : {
		  change : function(filefield, value){
			  var inputs = document.getElementById(filefield.id).getElementsByTagName('input'), fileInput;
			  for (var i = 0; i < inputs.length; i++)
				  if (inputs[i].type == 'file') {
					  fileInput = inputs[i].files;
					  if (fileInput.length > 0) {
						  filefield.up('attachmentquickuploadgrid').fireEvent('fileselect', fileInput[0])
					  }
					  break;
				  }
		  }
	  }

  })