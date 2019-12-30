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


Ext.define('app.view.platform.module.navigate.NavigateTreeStore', {
	  extend : 'Ext.data.TreeStore',

	  autoLoad : true,

	  allowAppend : true, // 在load过后不允许拖动进来的grid append;

	  rootVisible : true,
	  remoteFilter : false,
	  filterer : 'bottomup',
	  listeners : {

		  beforeinsert : function(store, node){
			  // 当grid的记录拖动进来以后，不执行增加操作
			  return this.allowAppend;
		  },

		  beforeappend : function(store, node){
			  // 当grid的记录拖动进来以后，不执行增加操作
			  return this.allowAppend;
		  },

		  beforeload : function(store){
			  // 允许load的数据和root append 操作
			  this.allowAppend = true;
		  },

		  load : function(store, records, successful){
			  var istree = app.viewport.getViewModel().get('navigateMode') == 'tree';
			  if (records) Ext.each(records, function(record){
				    this.addCountToItemText(record, istree);
			    }, this)
			  this.allowAppend = false;
		  }
	  },

	  /**
		 * 如果一个item下面有记录，就将此数字加到text中显示出来
		 */
	  addCountToItemText : function(node, istree){
		  // 把没有图标的module 的在导航中值的图标删掉
		  // var m = app.modules.getModuleInfo(node.raw.moduleName);
		  // if (!(m && m.iconURL))
		  // node.data.icon = null;
		  var text = node.get('text');
		  if (node.get('fieldvalue')) if (node.get('isBaseField') === false && node.get('fieldvalue') != '_null_') text +=
		      '<span class="navigateDetailIcon x-fa fa-info-circle"> </span>';
		  if (istree && node.get('count')) text += '<span class="navigateTreeItem">(' + node.get('count') + ')</span>'

		  node.set('text', text);
		  for (var i in node.childNodes)
			  this.addCountToItemText(node.childNodes[i], istree);
	  }

  });