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

Ext.define('app.view.platform.module.treegrid.TreeGrid', {

	  extend : 'Ext.tree.Panel',
	  alias : 'widget.moduletreegrid',

	  requires : ['app.view.platform.module.grid.GridModel', 'app.view.platform.module.treegrid.TreeGridController',
	      'app.view.platform.module.grid.GridFunction', 'app.view.platform.module.toolbar.Toolbar',
	      'Ext.grid.filters.Filters', 'app.view.platform.module.toolbar.SettingMenu',
	      'app.view.platform.module.userFilter.UserFilter', 'app.view.platform.module.paging.Paging',
	      'app.view.platform.module.grid.ColumnsFactory', 'app.view.platform.module.treegrid.toolbar.Toolbar'],

	  viewModel : 'modulegrid',
	  controller : 'moduletreegrid',

	  mixins : {
		  gridFunction : 'app.view.platform.module.grid.GridFunction'
	  },

	  viewConfig : {
		  enableTextSelection : false,
		  loadMask : true,
		  stripeRows : true
	  },

	  columnLines : true,
	  tools : [{
		      iconCls : 'x-fa fa-close'
	      }, {

		      iconCls : 'x-fa fa-paperclip'

	      }],

	  config : {
		  level : 1,
		  maxlevel : 5
	  },

	  listeners : {
		  newGridSchemeCreated : 'newGridSchemeCreated',
		  gridSchemeModified : 'gridSchemeModified',
		  afterrender : 'afterGridRender',
		  selectionchange : 'onSelectionChange',
		  userfilterchange : function(filters){
			  this.getStore().setUserFilters(filters);
		  }
	  },
	  header : {
		  listeners : {
			  menushow : 'columnMenuShow'
		  }
	  },

    root : {
      children : []
    },
    
	  initComponent : function(){

		  var me = this;
		  // me.title = me.moduleInfo.modulename;
		  me.currentGridScheme = me.moduleInfo.getGridDefaultScheme();
		  me.currentFilterScheme = me.moduleInfo.getFilterDefaultScheme();
		  me.columns = app.view.platform.module.grid.ColumnsFactory.getColumns(me.moduleInfo, me.currentGridScheme);
		  // 行选择模式，一共有四种，是选择框和行选择的各二种，单选和复选
		  var selModel = app.viewport.getViewModel().get('selModel').split('-');
		  me.selModel = {
			  selType : selModel[0],
			  mode : selModel[1]
		  };

		  me.plugins = ['gridfilters'];
		  me.store.grid = me;
		  me.store.on('load', me.onStoreLoad, me);

		  Ext.apply(me.viewConfig, {
			    plugins : {
				    ptype : 'treeviewdragdrop',
				    ddGroup : 'DD_' + me.objectName,
				    enableDrop : true,
				    containerScroll : true
			    },
			    listeners : {
				    beforedrop : 'onNodeBeforeDrop',
				    drop : 'onNodeDrop'
			    }
		    });

		  if (me.inWindow) {
			  delete me.tools;
			  me.header = false;
		  }

		  me.dockedItems = [{
			      xtype : 'moduletoolbar',
			      dock : me.getViewModel().get('module').toolbar.dock,
			      moduleInfo : me.moduleInfo,
			      objectName : me.objectName,
			      grid : me
		      }, {
			      xtype : 'moduletreetoolbar',
			      dock : 'left',
			      moduleInfo : me.moduleInfo,
			      objectName : me.objectName,
			      grid : me
		      }, {
			      xtype : 'modulepagingtoolbar',
			      padding : '2px 5px 2px 5px',
			      store : me.store,
			      target : me,
			      moduleInfo : me.moduleInfo,
			      objectName : me.objectName,
			      dock : 'bottom'
		      }, {
			      xtype : 'button', // 创建这个是为了生成一个context menu 可以
			      // bind 数据，这是一个隐藏的按钮
			      hidden : true,
			      menu : {
				      xtype : 'toolbarsettingmenu'
			      }
		      }]
		  me.callParent();
	  },

	  onStoreLoad : function(){
		  var me = this;
		  // 自动适应列宽,有三种选择方式
		  switch (app.viewport.getViewModel().get('autoColumnMode')) {
			  case 'firstload' :
				  if (me.firstload == undefined) {
					  me.autoSizeAllColumn();
					  // 如果没有记录，那么下次有记录的时候再刷新一次
					  if (me.getStore().count() > 0) me.firstload = false;
				  }
				  break;
			  case 'everyload' :
				  me.autoSizeAllColumn();
		  }
	  },

	  /**
		 * 展开至下一级
		 */
	  expandToNextLevel : function(){
		  if (this.level < this.maxlevel) this.expandToLevel(this.getRootNode(), this.level);
		  this.level += 1;
		  if (this.level >= this.maxlevel) this.level = 1;
	  },

	  /**
		 * 展开至指定级数
		 */
	  expandToLevel : function(node, tolevel){
		  if (node.getDepth() <= tolevel) node.expand();
		  for (var i in node.childNodes)
			  this.expandToLevel(node.childNodes[i], tolevel);
	  }

  })
