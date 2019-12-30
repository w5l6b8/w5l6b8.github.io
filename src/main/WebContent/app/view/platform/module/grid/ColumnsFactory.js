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


Ext.define('app.view.platform.module.grid.ColumnsFactory', {

	  alternateClassName : 'ColumnsFactory',

	  requires : ['Ext.grid.column.Check', 'app.view.platform.module.grid.column.RecordIcon',
	      'app.view.platform.module.grid.column.Image', 'app.view.platform.module.grid.column.ChildCount',
	      'app.view.platform.module.grid.column.ManyToOne', 'app.view.platform.module.grid.column.ChildAggregate',
	      'app.view.platform.module.grid.column.NameField', 'expand.ux.grid.filters.filter.Dictionary',
	      'app.utils.DictionaryUtils', 'app.view.platform.module.grid.column.ManyToMany'
	  // ,'app.view.platform.module.grid.column.OneToMany'

	  ],

	  statics : {
		  /**
			 * 根据module的定义和schemeOrderId返回此列表方案的多层表头定义
			 */
		  getColumns : function(module, scheme){
			  var columns = this.getLockedLeftColumns(module);
			  var result = columns.concat(this.getGroupAndFieldColumns(module, scheme.columns));
			  if (module.fDataobject.istreemodel) result[0].xtype = 'treecolumn';
			  return result;
		  },

		  getGroupAndFieldColumns : function(module, columns){
			  var result = [];
			  for (var i in columns) {
				  var column = columns[i];
				  if (column.columns || !column.fieldid) { // 没有fieldid就当作合并表头
					  if (!column.title) column.title = '';
					  var group = {
						  gridFieldId : column.columnid,
						  text : column.title.replace(new RegExp('--', 'gm'), '<br/>'),
						  hidden : column.hidden,
						  menuText : column.title.replace(new RegExp('--', 'gm'), ''),
						  columns : this.getGroupAndFieldColumns(module, column.columns)
					  }
					  if (column.locked) group.locked = true;
					  CU.applyOtherSetting(group, column.othersetting);
					  result.push(group);
				  } else {
					  if (column.fieldahead) { // 附加字段
						  if (column.aggregate) {
							  var field = module.addChildAdditionField(column);
							  result.push(this.getColumn(column, field, module));
						  } else {
							  var field = module.addParentAdditionField(column);
							  result.push(this.getColumn(column, field, module));
						  }
					  } else {
						  var field = module.getFieldDefine(column.fieldid);
						  if (!field) {
							  Ext.log(Ext.encode(column) + '未找到字段的定义，可能是分组下的字段全被删光了');
							  continue;
						  }
						  if (field.ishidden) continue;
						  result.push(this.getColumn(column, field, module));
					  }
				  }
			  }
			  return result;
		  },

		  /**
			 * 根据groupField,fieldDefine的定义，生成一个column的定义
			 */
		  getColumn : function(gridField, fieldDefine, module){
			  // 要分成三种情况来行成列了。基本字段,manytoone，onetomany字段，
			  var field = {
				  filter : {},
				  maxWidth : 600,
				  fieldDefine : fieldDefine,
				  gridField : gridField,
				  gridFieldId : gridField.columnid, // 加上这个属性，用于在列改变了宽度过后，传到后台
				  sortable : true,
				  text : this.getTextAndUnit(fieldDefine, gridField),
				  menuText : this.getMenuText(fieldDefine, gridField),
				  dataIndex : fieldDefine.fieldname,
				  hidden : gridField.hidden,
				  groupable : !!fieldDefine.allowgroup
			  }
			  if (gridField.locked) field.locked = true;
			  if (fieldDefine.fDictionaryid) {
				  field.groupable = true;
				  field.dataIndex += '_dictname'; // 原来的字段名是id 的名称
				  field.fDictionary = DictionaryUtils.getDictionary(fieldDefine.fDictionaryid);
				  if (!field.fDictionary.disablecolumnlist) field.filter = {
					  type : 'dictionary',
					  single : !!field.fDictionary.columnsingle, // 是否可以选择多个，true只能选择一个
					  dictionaryid : field.fDictionary.dictionaryid
				  }
			  }

			  if (fieldDefine.tooltiptpl) {
				  field.tooltiptpl = fieldDefine.tooltiptpl; // 显示在字段值上的tooltip的tpl值
				  field.tooltipXTemplate = new Ext.XTemplate(fieldDefine.tooltiptpl);
			  }

			  this.setFieldxtype(field, fieldDefine.fieldtype.toLowerCase());

			  if (module.fDataobject.namefield == fieldDefine.fieldname) {
				  field.xtype = 'namefieldcolumn';
				  Ext.apply(field, {
					    summaryType : 'count',
					    summaryRenderer : function(value){
						    return Ext.String.format('小计({0}条记录)', value);
					    }
				    });
				  delete field.renderer;
			  }

			  if (field.dataIndex == 'iconcls') field.renderer = Ext.util.Format.iconClsRenderer;

			  // 如果是可以改变显示单位的数值，可以选择万，千，百万，亿
			  if (fieldDefine.ismonetary) {
				  field.renderer = Ext.util.Format.monetaryRenderer;
			  }

			  if (fieldDefine.allowsummary) {
				  field.summaryType = 'sum';
				  field.summaryRenderer = field.renderer;
			  }

			  if (gridField.columnwidth > 0) {
				  field.width = field.columnwidth = gridField.columnwidth;
			  }
			  if (gridField.flex) {
				  field.flex = gridField.flex;
				  delete field.maxWidth;
			  }
			  if (gridField.autosizetimes) field.autosizetimes = gridField.autosizetimes;

			  if (fieldDefine.isManyToOne || fieldDefine.isOneToOne) {
				  field.dataIndex = fieldDefine.manyToOneInfo.nameField;
				  var pmodule = modules.getModuleInfo(fieldDefine.fieldtype);
				  Ext.apply(field, {
					    groupable : true,
					    xtype : 'manytoonefieldcolumn',
					    pmodule : pmodule,
					    manytooneIdName : fieldDefine.manyToOneInfo.keyField,
					    moduleName : fieldDefine.fieldtype
				    });
				  delete field.renderer;
			  }

			  if (fieldDefine.isOneToMany) {
				  field.xtype = 'onetomanycolumn';
				  var ft = fieldDefine.fieldtype;
				  ft = ft.substring(ft.indexOf('<') + 1, ft.indexOf('>'));
				  field.childModuleName = ft;
				  field.fieldahead = fieldDefine.fieldahead;
				  var cmodule = modules.getModuleInfo(ft);
				  if (cmodule && cmodule.iconcls) field.moduleIconCls = cmodule.iconcls;
				  delete field.renderer;
			  }
			  // 如果一个字段是一个附加字段，这个字段正好是父模块的父模块的一个namefileds字段，那么也要加成单击可以显示的功能
			  if (fieldDefine.isAdditionField && fieldDefine.fieldname === fieldDefine.namefield) {
				  var pmodule = app.modules.getModuleInfo(fieldDefine.tf_moduleName);
				  var icon = '',
					  iconCls = '';
				  if (pmodule && pmodule.iconURL) icon = '<img src="' + pmodule.iconURL + '" />';
				  if (pmodule.tf_iconCls) iconCls = ' ' + pmodule.tf_iconCls;
				  Ext.apply(field, {
					    xtype : 'manytoonefieldcolumn',
					    text : '<span class="gridheadicon' + iconCls + '" >' + icon
					        + fieldDefine.tf_title.replace(new RegExp('--', 'gm'), '<br/>') + '</span>',
					    manytooneIdName : fieldDefine.keyField,
					    moduleName : fieldDefine.tf_moduleName
				    });
				  delete field.renderer;
			  }
			  if (fieldDefine.isManyToMany) {
				  field.xtype = 'manytomanycolumn';
				  delete field.renderer;
			  }
			  CU.applyOtherSetting(field, fieldDefine.gridcolumnset);
			  CU.applyOtherSetting(field, gridField.othersetting);
			  return field;
		  },

		  getLockedLeftColumns : function(module){
			  var columns = [];
			  // 是否有附件，有附件则加入附件按钮
			  if (module.fDataobject.hasattachment && module.fDataobject.baseFunctions.attachmentquery) columns.push({
				    // locked : true,
				    xtype : 'attachmentnumbercolumn'
			    });

			  // 是否模块具有审核功能
			  if (module.tf_hasAuditing) {
				  columns.push({
					    locked : true,
					    xtype : 'auditingactioncolumn'
				    });
			  }
			  // 是否模块具有审批功能
			  if (module.tf_hasApprove) {
				  columns.push({
					    locked : true,
					    xtype : 'approveactioncolumn'
				    });
			  }

			  // 是否模块具有支付功能
			  if (module.tf_hasPayment) {
				  columns.push({
					    locked : true,
					    xtype : 'payoutactioncolumn'
				    });
			  }
			  // 如果是附件模块，加一个可以预览的列
			  if (module.tf_moduleName == '_Attachment') {
				  columns.push({
					    dataIndex : 'tf_attachmentId',
					    text : '预览',
					    align : 'center',
					    menuDisabled : true,
					    sortable : true,
					    width : 56,
					    resizable : false,
					    renderer : function(val, rd, model){
						    if (model.get('tf_filename')) return '<img height="16" width="16" src="attachment/preview.do?id='
						        + model.get('tf_attachmentId') + '" />';
						    else return '<img height="16" width="16" src="" />';
					    }
				    });
			  }
			  // 如果模块有记录icon,则加入记录字段icon列
			  if (module.tf_hasRecordIcon) {
				  columns.push({
					    xtype : 'recordiconcolumn'
				    })
			  }
			  return columns;
		  },

		  // 看看分组名称是不是 下面column 的开头，如果是开头的话，并且columntitle 后面有内容，就把
		  // 相同的部分截掉
		  canReduceTitle : function(group, field){
			  if (field.text.indexOf(group.text) == 0) {
				  field.text =
				      field.text.slice(group.text.length).replace('(', '').replace(')', '').replace('（', '').replace('）', '');
				  if (field.text.indexOf("<br/>") == 0) field.text = field.text.slice(5);
			  }
		  },

		  getTextAndUnit : function(fieldDefine, gridField){
			  var result = gridField.title || fieldDefine.fieldtitle;
			  result = result.replace(new RegExp('--', 'gm'), '<br/>');// title中间有--表示换行

			  result = result.replace('小计', '<span style="color : green;">小计</span>');

			  var unittext = Ext.monetary.unittext === '个' ? '' : Ext.monetary.unittext;

			  if (fieldDefine.ismonetary && Ext.monetaryPosition === 'columntitle') {// 可能选择金额单位千,
				  // 万,百万, 亿
				  if (fieldDefine.unittext || unittext) result +=
				      '<br/><span style="color:green;">(' + unittext + (fieldDefine.unittext ? fieldDefine.unittext : '')
				          + ')</span>';
			  } else {
				  if (fieldDefine.unittext) result += '<br/><span style="color:green;">(' + fieldDefine.unittext + ')</span>';
			  }
			  return result;
		  },

		  getMenuText : function(fieldDefine, gridField){
			  var result = gridField.title || fieldDefine.fieldtitle;
			  if (fieldDefine.unittext) result += '(' + fieldDefine.unittext + ')';
			  return result.replace(new RegExp('--', 'gm'), '');
		  },

		  setFieldxtype : function(field, fieldtype){
			  switch (fieldtype) {
				  case 'image' :
					  Ext.apply(field, {
						    xtype : 'imagecolumn',
						    align : 'center',
						    width : 100
					    })
					  break;
				  case 'date' :
					  Ext.apply(field, {
						    xtype : 'datecolumn',
						    align : 'center',
						    width : 90,
						    renderer : Ext.util.Format.dateRenderer
					    })
					  break;

				  case 'datetime' :
				  case 'timestamp' :
					  Ext.apply(field, {
						    xtype : 'datecolumn',
						    align : 'center',
						    width : 130,
						    renderer : Ext.util.Format.datetimeRenderer
					    })
					  break;

				  case 'boolean' :
					  field.xtype = 'checkcolumn';
					  field.stopSelection = false;
					  field.listeners = {
						  beforecheckchange : function(){
							  return false; // 不允许在grid中直接修改值
						  }
					  }
					  break;
				  case 'integer' :
					  Ext.apply(field, {
						    align : 'right',
						    xtype : 'numbercolumn',
						    tdCls : 'intcolor',
						    format : '#',
						    renderer : Ext.util.Format.intRenderer,
						    filter : 'number'
					    })
					  break;
				  case 'money' :
				  case 'double' :
				  case 'float' :
					  Ext.apply(field, {
						    align : 'right',
						    xtype : 'numbercolumn',
						    width : 110,
						    renderer : Ext.util.Format.floatRenderer,
						    filter : 'number'
					    })
					  break;
				  case 'percent' :
					  Ext.apply(field, {
						    align : 'center',
						    xtype : 'widgetcolumn',
						    filter : 'number',
						    width : 110,
						    widget : {
							    xtype : 'progressbarwidget',
							    animate : true,
							    textTpl : ['{percent:number("0")}%']
						    }
					    })
					  break;
				  case 'string' :
					  field.renderer = Ext.util.Format.defaultRenderer
					  break;
				  default :
					  field.renderer = Ext.util.Format.defaultRenderer
			  }
		  }
	  }
  });
