/**
 * 用来控制单个模块的总体布局 一般情况下包括：导航条和列表区域二个部分。可以根据配置加入其他的 布局可以预置一些 1.导航和列表区域的标准布局；
 * 2.导航、列表、明细的三列式布局； 3.导航、列表、附件显示布局；
 */
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


Ext.define('app.view.platform.module.Module', {

	  extend : 'Ext.panel.Panel',

	  alias : 'widget.modulepanel',

	  requires : ['app.view.platform.module.ModuleUtil', 'app.view.platform.module.ModuleModel',
	      'app.view.platform.module.ModuleController', 'app.view.platform.module.navigate.Navigate',
	      'app.view.platform.module.toolbar.Toolbar', 'app.view.platform.module.grid.Grid',
	      'app.view.platform.module.grid.GridStore', 'app.view.platform.module.model.GridModelFactory',
	      'app.view.platform.module.associate.Panel', 'app.view.platform.module.associate.TabPanel',
	      'app.view.platform.module.treegrid.TreeGrid', 'app.view.platform.module.treegrid.TreeGridStore'],

	  layout : 'border',

	  viewModel : 'module',
	  controller : 'module',
	  centerRegionNest : false, // 是否在center region 中加入一个panel,在附件的时候要用到。
	  parentFilter : null,
	  config : {
		  isSubModule : false, // 是不是放在一个grid中作为子模块。
		  subModuleActivated : false, // 当前模块作为grid的子模块，是否处于激活状态，如果是的话才会对pf的事件响应，否则记录pf改变，到激活的时候再刷新数据

		  collapseNavigate : false, // 是否最小化导航栏
		  enableNavigate : true, // 是否允许有导航栏

		  collapseSouth : true,
		  enableSouth : undefined,

		  collapseEast : true,
		  enableEast : undefined,

		  inWindow : false,

		  gridType : 'normal' // 当前grid的用处，normal 正常 ,window,onetomanygrid ,select,

	  },
	  getParentFilter : function(){
		  return this.parentFilter;
	  },

	  setSubModuleActivated : function(activated){
		  var me = this;
		  if (me.isSubModule) {
			  me.subModuleActivated = activated;
			  if (activated) {
				  if (me.bufferParentFilter) {
					  me.bufferParentFilter = false;
					  me.setParentFilter(me.parentFilter);
				  }
			  }
		  }
	  },

	  setParentFilter : function(value){
		  var me = this;
		  me.parentFilter = value;
		  if (me.isSubModule) {
			  if (!me.subModuleActivated) {
				  me.bufferParentFilter = true;
				  me.store.loadRecords([]);
				  return;
			  }
		  }
		  me.getModuleGrid().setParentFilter(value);
		  if (me.getModuleNavigate()) me.getModuleNavigate().setParentFilter(value);
	  },

	  listeners : {
		  boxready : 'onBoxReady',
		  parentfilterchange : 'onParentFilterChange',
		  navigatetoggle : 'onNavigateToggle',
		  regionsouthtoggle : 'onRegionSouthToggle',
		  regioneasttoggle : 'onRegionEastToggle'
	  },

	  initComponent : function(){
		  var me = this;
		  if (Ext.isObject(me.param)) Ext.apply(me, me.param)
		  me.moduleInfo = modules.getModuleInfo(me.moduleId);
		  me.objectName = me.moduleInfo.fDataobject.objectname;
		  me.model = me.moduleInfo.model;
		  me.istreemodel = me.moduleInfo.fDataobject.istreemodel;
		  me.store =
		      Ext.create('app.view.platform.module.' + (me.istreemodel ? 'treegrid.TreeGridStore' : 'grid.GridStore'), {
			        module : me.moduleInfo,
			        modulePanel : me,
			        model : me.model
		        });
		  me.store.getProxy().extraParams.moduleName = me.moduleInfo.fDataobject.objectname;

		  if (me.parentFilter) me.store.parentFilter = me.parentFilter;
		  me.enableNavigate =
		      !me.istreemodel && me.enableNavigate
		          && (me.moduleInfo.fDataobject.navigatedesign || me.moduleInfo.getNavigateSchemeCount() > 0);
		  me.collapseNavigate = me.moduleInfo.getNavigateSchemeCount() == 0 || me.collapseNavigate;
		  me.defaults = {
			  moduleInfo : me.moduleInfo,
			  objectName : me.objectName,
			  modulePanel : me,
			  parentFilter : me.parentFilter
		  };
		  var center = {
			  xtype : me.istreemodel ? 'moduletreegrid' : 'modulegrid',
			  store : me.store,
			  region : 'center',
			  modulePanel : me,
			  inWindow : me.inWindow
		  };
		  me.items = [me.centerRegionNest ? {
			  xtype : 'panel',
			  region : 'center',
			  layout : 'fit',
			  items : [Ext.apply(center, me.defaults)]
		  } : center];
		  if (me.enableNavigate && !me.istreemodel) me.items.push({
			    xtype : 'modulenavigate',
			    region : 'west',
			    width : 258,
			    weight : 200,
			    split : true,
			    collapsed : me.collapseNavigate,
			    collapsible : true,
			    collapseMode : 'mini',
			    listeners : {
				    expand : 'onNavigateExpand',
				    collapse : 'onNavigateCollapse'
			    }
		    });

		  if (me.enableEast !== false) { // 传进来的参数为false
			  var assoc = me.moduleInfo.getEastAssociateInfo();
			  if (assoc) {
				  me.enableEast = !assoc.isdisable;
				  if (me.enableEast) {
					  me.collapseEast = assoc.iscollapsed || assoc.ishidden;
					  me.items.push({
						    associateInfo : assoc,
						    xtype : 'moduleassociatetabpanel',
						    allowDesign : !assoc.isdisabledesign,
						    region : 'east',
						    width : parseInt(assoc.worh) || 350,
						    weight : assoc.weight || 100,
						    split : true,
						    collapsible : true,
						    collapsed : me.collapseEast,
						    defaultHidden : assoc.ishidden,
						    collapseMode : 'mini',
						    listeners : {
							    expand : 'onEastRegionExpand',
							    collapse : 'onEastRegionCollapse'
						    }
					    });
				  }
			  } else me.enableEast = false;
		  }

		  if (me.enableSouth !== false) { // 传进来的参数为false
			  var assoc = me.moduleInfo.getSouthAssociateInfo();
			  if (assoc) {
				  me.enableSouth = !assoc.isdisable;
				  if (me.enableSouth) {
					  me.collapseSouth = assoc.iscollapsed || assoc.ishidden;
					  me.items.push({
						    associateInfo : assoc,
						    xtype : 'moduleassociatetabpanel',
						    allowDesign : !assoc.isdisabledesign,
						    region : 'south',
						    height : parseInt(assoc.worh) || 300,
						    weight : assoc.weight || 110,
						    split : true,
						    collapsible : true,
						    collapseMode : 'mini',
						    collapsed : me.collapseSouth,
						    defaultHidden : assoc.ishidden,
						    listeners : {
							    expand : 'onSouthRegionExpand',
							    collapse : 'onSouthRegionCollapse'
						    }
					    })
				  }
			  } else me.enableSouth = false;
		  }
		  me.callParent();
	  },

	  getModuleGrid : function(){
		  var me = this;
		  if (me.istreemodel) return me.down('moduletreegrid[objectName=' + this.objectName + ']')
		  else return me.down('modulegrid[objectName=' + this.objectName + ']')
	  },
	  getModuleNavigate : function(){
		  return this.down(' > modulenavigate[objectName=' + this.objectName + ']')
	  },
	  getSouthRegion : function(){
		  return this.down(' > moduleassociatetabpanel[region=south]')
	  },
	  getEastRegion : function(){
		  return this.down(' > moduleassociatetabpanel[region=east]')
	  }

  })
