Ext.define('expand.overrides.form.field.Text', {
	  override : 'Ext.form.field.Text',

	  requires : ['expand.trigger.Clear'],

	  constructor : function(config){
		  var me = this;
		  // if (config.allowBlank == false && !Ext.isEmpty(config.fieldLabel)) {
		  // config.fieldLabel_ = config.fieldLabel;
		  // config.fieldLabel += "<font color='red'>*</font>";
		  // }
		  if (!config.triggers) {
			  config.triggers = {};
		  }
		  if (config.triggers.clear == false) {
			  delete config.triggers.clear;
		  } else if ((!(config.allowBlank == false && me instanceof Ext.form.field.ComboBox))
		      && !(me instanceof Ext.form.field.Number) && !config.hideClearTrigger) {
			  if (!config.triggers.clear) // 如果没设置
			  config.triggers.clear = {
				  type : 'clear',
				  weight : -1,
				  hideWhenMouseOut : true
			  };
		  }
		  me.callParent(arguments);
	  },

	  initComponent : function(){
		  var me = this;
		  if (me.unittext) me.on('render', me.onUnitTextRender);
		  me.callParent(arguments);
	  },

	  onUnitTextRender : function(field){

		  var realLength = 0,
			  len = field.unittext.length,
			  charCode = -1;
		  for (var i = 0; i < len; i++) {
			  charCode = field.unittext.charCodeAt(i);
			  if (charCode >= 0 && charCode <= 128) realLength += 1;
			  else realLength += 2;
		  }

		  var width = field.unitWidth || realLength * 8 + 2;
		  field.bodyEl.el.dom.style = "width:100%;border-right:" + width + "px solid transparent;";
		  var span = document.createElement("span");
		  span.style = "width:" + width + "px;float:right;padding-top:3px;padding-left:5px;" + field.unitStyle;
		  span.appendChild(document.createTextNode(field.unittext));
		  field.el.dom.appendChild(span);
	  }

  });
