/**
 * 在模块的列表中显示父模块的，显示父模块的name值，加个链接，一点即可显示 该像模块的 display window
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



Ext.define('app.view.platform.module.grid.column.ManyToOne', {
	  extend : 'Ext.grid.column.Column',
	  alias : 'widget.manytoonefieldcolumn',

	  initComponent : function(){
		  var me = this,
			  icon = '',
			  iconCls = '';
		  if (me.pmodule.fDataobject.iconurl) icon = '<img src="' + me.pmodule.fDataobject.iconurl + '"/>';
		  if (me.pmodule.fDataobject.iconcls) iconCls = ' ' + me.pmodule.fDataobject.iconcls;
		  me.text =
		      '<span class="gridheadicon ' + iconCls + '" >' + icon
		          + (me.gridField.title || me.fieldDefine.fieldtitle).replace(new RegExp('--', 'gm'), '<br/>') + '</span>';
		  this.callParent();
	  },

	  renderer : function(val, metaData, model, row, col, store, gridview){
		  if (val) {
			  var result = '<span class="gridNameField">' + val + '</span>';
			  return result;
		  }
	  },

	  processEvent : function(type, view, cell, recordIndex, cellIndex, e, record, row){
		  var me = this;
		  if (type === 'click') {
			  if (e.getTarget().className === 'gridNameField') {
				  var id = record.get(me.manytooneIdName);
				  if (id) modules.getModuleInfo(me.moduleName).showDisplayWindow(id);
			  }
		  }
	  }
  })
