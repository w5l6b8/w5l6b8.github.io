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

Ext.define('app.view.platform.module.treegrid.toolbar.Toolbar', {

	  extend : 'Ext.toolbar.Toolbar',
	  alias : 'widget.moduletreetoolbar',

	  requires : ['app.view.platform.module.treegrid.toolbar.ToolbarController'],

	  controller : 'gridtreetoolbar',
	  reference : 'gridtreetoolbar',
	  weight : 5,

	  initComponent : function(){
		  var me = this;

		  me.items = [{
			      iconCls : 'x-tool-collapse',
			      tooltip : '全部折叠',
            handler : 'onCollapseButtonClick'
		      }, {
			      iconCls : 'x-tool-expand',
			      tooltip : '展开一级',
            handler : 'onExpandALevelButtonClick'
		      }, {
			      iconCls : 'x-fa fa-sort-numeric-asc',
			      tooltip : '将选中节点同级按当前顺序重新排列',
            handler : 'onUpdateOrderButtonClick'
		      }, {
			      tooltip : '设置当前节点为非叶节点',
			      itemId : 'setnotleaf',
			      iconCls : 'x-fa fa-folder-open-o',
			      handler : 'onSetNotLeafButtonClick'
		      }]

		  me.callParent();

	  }

  })