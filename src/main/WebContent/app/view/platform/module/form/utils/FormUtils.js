Ext.define('app.view.platform.module.form.utils.FormUtils', {
	  alternateClassName : 'FormUtils', // 设置别名
	  requires : ['app.view.platform.module.form.field.DictionaryComboBox',
	      'app.view.platform.module.form.field.ManyToOneText', 'app.view.platform.module.form.field.ManyToOneComboBox',
	      'app.view.platform.module.form.field.ManyToOneTreePicker', 'app.utils.DictionaryUtils'],
	  statics : {

		  /**
			 * 获取Panel信息
			 * @param {} moduleinfo 当前模型的定义类
			 * @param {} objectfield 对象字段信息
			 * @param {} formfield 表单字段信息
			 * @param {} operatetype 操作类型 new、eidt、display
			 * @return {}
			 */
		  getPanel : function(moduleinfo, objectfield, formfield, operatetype, syscfg){

			  var xtype = formfield.xtype || 'fieldset',
				  layout = formfield.layout || 'table',
				  fDataobject = moduleinfo.fDataobject,
				  config = {};
			  if (formfield.layout) config.layout = formfield.layout;
			  if (formfield.collapsible) config.collapsible = formfield.collapsible;
			  if (formfield.collapsed) config.collapsed = formfield.collapsed;
			  if (formfield.region) config.region = formfield.region;
			  if (formfield.height) config.height = formfield.height;
			  if (formfield.colspan) config.colspan = formfield.colspan;
			  if (formfield.rowspan) config.rowspan = formfield.rowspan;
			  if (formfield.title) config.title = formfield.title;

			  switch (xtype) {
				  case 'fieldset' :
				  case 'Ext.form.FieldSet' : {
					  Ext.apply(config, {
						    border : true,
						    margin : 5
					    });
					  break;
				  }
			  }
			  switch (layout) {
				  case 'table' : {
					  Ext.apply(config, {
						    titleCollapse : true,
						    layout : {
							    type : 'table',
							    columns : formfield.cols || 1,
							    tableAttrs : {
								    style : {
									    width : '100%'
								    }
							    },
							    tdAttrs : {
								    align : 'center',
								    valign : 'middle',
								    widths : formfield.widths
							    }
						    }
					    });
					  break;
				  }
			  }
			  if (formfield.othersetting) {
				  Ext.apply(config, CU.toObject('{' + formfield.othersetting + '}'));
			  }
			  if (formfield.subobjectid) {
				  config.moduleId = formfield.subobjectid;
				  config.operatetype = operatetype;
				  config.parentFilter = {
					  moduleName : fDataobject.objectname, // 父模块的名称
					  fieldahead : fDataobject.fieldahead || fDataobject.objectname, // 路径
					  fieldName : fDataobject.primarykey, // 父模块的限定字段,父模块主键
					  fieldtitle : moduleinfo.modulename, // 父模块的标题
					  operator : "=",
					  fieldvalue : undefined, // 父模块的记录id
					  isCodeLevel : false
					    // 层级
				    }
				  /** 是否允许有导航栏 */
				  config.enableNavigate = false;
          delete config.layout;
          delete config.titleCollapse;
				  return Ext.create('app.view.platform.module.Module', config);
			  } else if (xtype == 'attachment') {
				  config.parentFilter = {
					  moduleName : fDataobject.objectid,
					  fieldtitle : fDataobject.title,
					  operator : "=",
					  fieldvalue : null,
					  text : '未设置'
				  }
				  config.height = 500;
				  config.showgrid = false;
          delete config.layout;
          delete config.titleCollapse;
				  return Ext.create('app.view.platform.module.attachment.Module', config)
			  } else {
				  if (xtype.indexOf('.') > 0) {
					  return Ext.create(xtype, config);
				  } else {
					  return Ext.widget(xtype, config);
				  }
			  }
		  },

		  /**
			 * 获取字段
			 * @param {} fDataobject 实体对象
			 * @param {} objectfield 字段对象
			 *          需要考虑的字段：(数据库字段=fieldname、长度=fieldlen、小数位数=digitslen、禁用=isdisable、隐藏=ishidden、可新增=allownew、
			 *          可修改=allowedit、新增选中=newneedselected，必填=isrequired、最大值=maxval、最小值=minval、正则验正表达式=regexvalue、
			 *          js验证代码=jsvalue、计量单位=unittext、缺省值=defaultvalue、数据字典属性=dictionaryid、tooltiptpl=提示信息定义、
			 *          类型=vtype、字段iconcls=iconcls、form字段设置=formfieldset)
			 * @param {} formfield 表单字段对象
			 *          需要考虑的字段：(字段名称=Title、宽度=width、高度=height、隐藏label=hiddenlabel、合并行数=rowspan、合并列数=colspan)
			 * @param {} operatetype 表单类型
			 */
		  getField : function(moduleinfo, objectfield, formfield, operatetype, syscfg){
			  if (!objectfield || objectfield.isdisable) return null;
			  var field = {
				  xtype : "textfield",
				  fieldLabel : formfield.title || objectfield.fieldtitle,
				  name : objectfield.fieldname,
				  colspan : formfield.colspan || 1,
				  rowspan : formfield.rowspan || 1,
				  hidden : objectfield.ishidden,
				  labelAlign : formfield.labelalign || 'right'
			  }
			  field.allowBlank = !CU.getBoolean(objectfield.isrequired);
			  if (field.allowBlank == false) {
				  if (syscfg.validbeforenew == 'markfield') {
					  field.emptyText = "必填";
				  } else {
					  field.fieldLabel += "<font color='red'>✻</font>";
				  }
			  }
			  field.hideLabel = CU.getBoolean(formfield.hiddenlabel);
			  if (objectfield.ishidden) {
				  field.xtype = "hiddenfield";
				  if (objectfield.manyToOneInfo) {
					  field.name = objectfield.keyField;
					  field.moduleName = objectfield.fieldtype;
				  }
				  return field;
			  }
			  if (operatetype == 'display' || operatetype == 'approve' || (operatetype == 'new' && !objectfield.allownew)
			      || (operatetype == 'edit' && !objectfield.allowedit) || objectfield.isdisable) {
				  field.readOnly = true;
				  field.allowBlank = true;
			  }
			  field.width = formfield.width || "100%";
			  if (formfield.height) {
				  field.height = formfield.height;
			  }
			  field.unittext = objectfield.unittext;
			  Ext.apply(field, this.getFieldxtype(objectfield, syscfg));
			  if (field.readOnly && (field.xtype == 'manytoonecombobox' || field.xtype == 'manytoonetreepicker')) {
				  field.xtype = 'manytoonetext';
				  field.idfieldname = objectfield.manyToOneInfo.keyField;
				  field.name = objectfield.manyToOneInfo.nameField;
			  }
			  if (objectfield.formfieldset) {
				  Ext.apply(field, CU.toObject('{' + objectfield.formfieldset + '}'));
			  }
			  return field;
		  },

		  getFieldxtype : function(objectfield, syscfg){
			  var field = {};
			  if (objectfield.fDictionaryid) {
				  Ext.apply(field, {
					    xtype : 'dictionarycombobox',
					    objectfield : objectfield
				    });
			  } else if (objectfield.fieldrelation) {
				  Ext.apply(field, {
					    xtype : objectfield.manyToOneInfo.parentKey || objectfield.manyToOneInfo.codeLevel
					        ? 'manytoonetreepicker' : 'manytoonecombobox',
					    name : objectfield.manyToOneInfo.keyField,
					    reference : objectfield.joincolumnname,
					    fieldDefine : objectfield,
					    fieldtype : objectfield.fieldtype,
					    displayparentbutton : syscfg.displayparentbutton
				    });
				  if (objectfield.remark) {
					  field.bind = {
						  filters : {
							  property : 'text',// objectfield.remark,
							  value : '{' + objectfield.remark + '.value}'
						  }
					  }
				  }
			  } else {
				  switch (objectfield.fieldtype) {
					  case 'Date' : {
						  Ext.apply(field, {
							    format : 'Y-m-d',
							    xtype : 'datefield',
							    submitFormat : 'Y-m-d',
							    enableKeyEvents : true
						    });
						  break;
					  }
					  case 'Timestamp' :
					  case 'DateTime' : {
						  Ext.apply(field, {
							    format : 'Y-m-d H:i:s',
							    submitFormat : 'Y-m-d H:i:s',
							    xtype : 'datetimefield'
						    });
						  break;
					  }
					  case 'Boolean' : {
						  field = {
							  xtype : 'checkboxfield',
							  inputValue : 'true'
						  };
						  break;
					  }
					  case 'Integer' : {
						  Ext.apply(field, {
							    fieldStyle : "text-align:right",
							    xtype : 'numberfield',
							    enableKeyEvents : true
						    });
						  break;
					  }
					  case 'Double' : {
						  Ext.apply(field, {
							    hideTrigger : true,
							    xtype : 'moneyfield'
						    });
						  break;
					  }
					  case 'Float' : {
						  Ext.apply(field, {
							    hideTrigger : true,
							    xtype : 'moneyfield'
						    });
						  break;
					  }
					  case 'Percent' : {
						  Ext.apply(field, {
							    xtype : 'moneyfield',
							    percent : true
						    });
						  break;
					  }
					  case 'String' : {
						  Ext.apply(field, {
							    enforceMaxLength : true,
							    xtype : 'textfield',
							    enableKeyEvents : true
						    });
						  break;
					  }
					  case 'Image' : {
						  Ext.apply(field, {
							    xtype : 'inlineimagefield'
						    });
						  break;
					  }
					  default : {
						  Ext.log(objectfield.fieldtype + '类型没有找到,字段：' + objectfield.fieldname);
					  }
				  }
			  }
			  // 最大值
			  if (objectfield.maxval) {
				  field.maxValue = objectfield.maxval;
			  }
			  // 最小值
			  if (objectfield.minval) {
				  field.minValue = objectfield.minval;
			  }
			  // 最大长度
			  if (objectfield.fieldlen) {
				  field.maxLength = objectfield.fieldlen;
			  }
			  // 缺省值
			  if (objectfield.defaultvalue) {
				  field.defaultvalue = objectfield.defaultvalue;
			  }
			  // 小数位数
			  if (objectfield.digitslen) {
				  field.decimalPrecision = objectfield.digitslen;
			  }
			  // 正则表达式验证
			  if (objectfield.regexvalue && objectfield.regextext) {
				  field.regex = new RegExp(objectfield.regexvalue, "g");
				  field.regexText = objectfield.regextext;
			  }
			  // 验证方式
			  if (objectfield.vtype) {
				  field.vtype = objectfield.vtype;
			  }
			  // 提示
			  if (objectfield.tooltiptpl && syscfg.fieldtooltip != 'off') {
				  Ext.apply(field, {
					    listeners : {
						    render : function(field){
							    Ext.QuickTips.init();
							    Ext.QuickTips.register({
								      target : field.el,
								      text : objectfield.tooltiptpl
							      })
						    }
					    }
				    });
			  }
			  return field;
		  }
	  }
  });
