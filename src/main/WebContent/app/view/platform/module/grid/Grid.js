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

Ext.define('app.view.platform.module.grid.Grid', {

	  extend : 'Ext.grid.Panel',
	  alias : 'widget.modulegrid',

	  requires : ['app.view.platform.module.grid.GridModel', 'app.view.platform.module.grid.GridController',
	      'app.view.platform.module.grid.GridFunction', 'app.view.platform.module.toolbar.Toolbar',
	      'Ext.grid.filters.Filters', 'Ext.grid.feature.GroupingSummary', 'app.view.platform.module.toolbar.SettingMenu',
	      'app.view.platform.module.userFilter.UserFilter', 'app.view.platform.module.paging.Paging',
	      'app.view.platform.module.grid.ColumnsFactory', 'app.view.platform.module.sqlparam.Form'],

	  viewModel : 'modulegrid',
	  controller : 'modulegrid',

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

	  // layout : 'border', //locked 字段可以隐藏的设置
	  // split : true,
	  // collapseMode : 'mini',
	  // collapsible : true,
	  initComponent : function(){

		  var me = this,
			  dataobj = me.moduleInfo.fDataobject;
		  // me.title = me.moduleInfo.modulename;
		  me.currentGridScheme = me.moduleInfo.getGridDefaultScheme();
		  me.currentFilterScheme = me.moduleInfo.getFilterDefaultScheme();
		  me.columns = ColumnsFactory.getColumns(me.moduleInfo, me.currentGridScheme);

		  // 行选择模式，一共有四种，是选择框和行选择的各二种，单选和复选
		  var selModel = app.viewport.getViewModel().get('selModel').split('-');
		  me.selModel = {
			  selType : selModel[0],
			  mode : selModel[1]
		  };

		  if (me.moduleInfo.hasSummaryField()) {
			  me.features = [
          {
				      ftype : 'summary',
              dock: 'bottom'
			      },
              {
               ftype: 'groupingsummary'
            }]
		  }

		  me.plugins = ['gridfilters'];
		  me.store.grid = me;
		  me.store.on('load', me.onStoreLoad, me);

		  Ext.apply(me.viewConfig, {
			  plugins : [{
				  ptype : 'gridviewdragdrop',
				  // dragText : this.id,
				  ddGroup : 'DD_' + me.objectName,
				  enableDrop : true
				    // his.module.tf_orderField
				    // 设为false，不允许在本grid中拖动，如果设置一个顺序字段，那么就可以互换顺序，
			    }]
			  // listeners : {
			  // 这是拖动了一条记录到另一条记录，换了位置以后的drop , 可以保存记录的顺序号了
			  // drop : function(node, data, overModel, dropPosition){
			  // data.view.up('modulegrid').getGridSettingMenu().down('#saverecordorder'
			  // ).setDisabled(false);
			  // }
			  // }
		  }
		  )

		  if (me.inWindow) {
			  delete me.tools;
			  me.header = false;
		  }

		  me.dockedItems = [dataobj.hassqlparam ? {
			      xtype : 'modulesqlform',
			      moduleInfo : me.moduleInfo,
			      dock : 'top'
		      } : null, {
			      xtype : 'moduletoolbar',
			      dock : me.getViewModel().get('module').toolbar.dock,
			      moduleInfo : me.moduleInfo,
			      objectName : me.objectName,
			      grid : me
		      }, {
			      xtype : 'modulepagingtoolbar',
			      padding : '2px 5px 2px 5px',
			      store : me.store,
			      moduleInfo : me.moduleInfo,
			      objectName : me.objectName,
			      target : me,
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
	  }

  })
