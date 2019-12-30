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

Ext.define('app.view.platform.module.grid.GridController', {
	  extend : 'Ext.app.ViewController',
	  alias : 'controller.modulegrid',

	  init : function(){

		  var vm = this.getViewModel();
		  vm.bind('{module.toolbar.dock}', 'onToolbarDockChange', this);
		  vm.bind('{module.toolbar.topbottomMode}', 'onToolbarTBModeChange', this);
		  vm.bind('{module.toolbar.leftrightMode}', 'onToolbarLRModeChange', this);
		  vm.bind('{module.toolbar.leftrightArrowAlign}', 'onToolbarLRModeChange', this);
	  },

	  /**
		 * 新增了一个列表方案后，定义到该方案
		 * @param {} scheme
		 */
	  newGridSchemeCreated : function(scheme){
		  var view = this.getView();
		  var oname = view.moduleInfo.fDataobject.objectname;
		  view.moduleInfo.addOwnerGridScheme(scheme);
		  var menubutton = view.down('gridschememenubutton[objectName=' + oname + ']');
		  menubutton.fireEvent('newSchemeCreated', scheme);

		  var segmentbutton = view.down('gridschemesegmented[objectName=' + oname + ']');
		  if (segmentbutton) segmentbutton.addSchemeAndSelect(scheme, '我的方案');

	  },

	  gridSchemeModified : function(scheme){
		  var view = this.getView();
		  var oname = view.moduleInfo.fDataobject.objectname;
		  view.moduleInfo.updateOwnerGridScheme(scheme);

		  var menubutton = view.down('gridschememenubutton[objectName=' + oname + ']');
		  menubutton.fireEvent('schemeModified', scheme);

		  var segmentbutton = view.down('gridschemesegmented[objectName=' + oname + ']');

		  if (segmentbutton) segmentbutton.updateSchemeAndSelect(scheme, '我的方案');

	  },

	  onSelectionChange : function(model, selected){
		  var grid = this.getView(),
			  moduleinfo = grid.moduleInfo;
		  modulepanel = grid.up('modulepanel');
		  if (grid.silent) { // 如果是沉默的，form的 subgrid
			  // 中修改过数据以后产生的事件，不需要刷新数据
			  return;
		  }

		  // 如果显示的窗口正在显示，则更新
		  if (moduleinfo.displayWindow && !moduleinfo.getDisplayWindow().isHidden()) moduleinfo.showDisplayWindow(grid);

		  if (moduleinfo.editWindow && !moduleinfo.getEditWindow().isHidden()) moduleinfo.showEditWindow(grid);

		  /*
			 * grid.updateRecordDetail(selected); grid.module.updateActiveForm(); //
			 * if (modulepanel.editWindow && !modulepanel.editWindow.isHidden())
			 * modulepanel.editWindow.form.initForm();
			 */
		  grid.updateTitle();
		  var firstrecord = null;
		  if (selected.length > 0) firstrecord = selected[0];
		  Ext.each(grid.modulePanel.query('moduleassociatetabpanel[objectName=' + grid.objectName + ']'), function(panel){
			    panel.fireEvent('selectionchange', firstrecord);
		    })
		  var toolbar = grid.down('moduletoolbar[objectName=' + grid.objectName + ']');
		  if (toolbar) toolbar.fireEvent('selectionchange', selected);
	  },

	  afterGridRender : function(grid){
		  // if (grid.parentFilter) {
		  // if (grid.parentFilter.fieldvalue) grid.getStore().load();
		  // }
		  grid.getStore().load();
		  grid.updateTitle();
		  var menu = grid.headerCt.getMenu();
		  menu.on('beforeshow', this.columnMenuShow);
		  var subitems = [{
			      text : '默认值'
		      }, '-'];
		  subitems = subitems.concat(app.utils.Monetary.getMonetaryMenu({
			    handler : this.onMonetaryChange
		    }));
		  menu.add(['-', {
			      text : '数值单位',
			      itemId : 'monetary',
			      menu : subitems
		      }]);
	  },

	  onMonetaryChange : function(menu){

	  },

	  columnMenuShow : function(menu){
		  var h = menu.activeHeader;
		  var m = menu.down('#monetary');
		  if (m) {
			  if (h.fieldDefine && h.fieldDefine.ismonetary) {
				  m.show();
				  m.previousNode().show();
			  } else {
				  m.hide();
				  m.previousNode().hide();
			  }
		  }
	  },


	  onToolbarTBModeChange : function(){
		  var toolbar = this.lookupReference('gridtoolbar');
		  if (toolbar && toolbar.isDockTopBottom()) toolbar.rebuildButtons();
	  },
	  onToolbarLRModeChange : function(){
		  var toolbar = this.lookupReference('gridtoolbar');
		  if (toolbar && !toolbar.isDockTopBottom()) toolbar.rebuildButtons();
	  },
	  /**
		 * 当toolbar的dock值变动过后，执行此函数
		 */
	  onToolbarDockChange : function(value){
		  var toolbar = this.lookupReference('gridtoolbar');
		  if (!toolbar) return;
		  var grid = toolbar.grid;
		  if (toolbar && value && toolbar.dock != value) {
			  var dock = toolbar.dock;
			  if (value != dock) {
				  if (((value == 'top' || value == 'bottom') && (dock == 'top' || dock == 'bottom'))
				      || ((value == 'left' || value == 'right') && (dock == 'left' || dock == 'right'))) {
					  toolbar.setDock(value)
				  } else {
					  // toolbar 横竖变换后。
					  var ownerCt = toolbar.ownerCt;
					  ownerCt.remove(toolbar, true);
					  ownerCt.addDocked({
						    xtype : 'moduletoolbar',
						    moduleInfo : grid.moduleInfo,
						    objectName : grid.objectName,
						    grid : grid,
						    dock : value
					    })[0].rebuildButtons();
				  }
			  }
		  }
	  }
  })