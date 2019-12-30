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

Ext.define('app.view.platform.module.ModuleController', {
	  extend : 'Ext.app.ViewController',
	  alias : 'controller.module',

	  init : function(){
	  },

	  /**
		 * 在当前module的父模块限定条件改变了之后执行。
		 * @param {} pf pf中需要 fieldvalue : '01', // 父模块的记录id text : "北京市", // 父模块的标题
		 */
	  onParentFilterChange : function(parentfilter){
		  var me = this.getView();
		  if (me.getParentFilter() != null) {
			  Ext.applyIf(parentfilter, me.getParentFilter());
		  }
		  me.setParentFilter(parentfilter);
	  },

	  onNavigateExpand : function(){
		  this.getView().getModuleGrid().down('moduletoolbar').down('button#regionnavigate').setPressed(true);
	  },

	  onNavigateCollapse : function(){
		  this.getView().getModuleGrid().down('moduletoolbar').down('button#regionnavigate').setPressed(false);
	  },

	  onSouthRegionExpand : function(){
		  this.getView().getModuleGrid().down('moduletoolbar').down('button#regionsouth').setPressed(true);
	  },
	  onSouthRegionCollapse : function(){
		  this.getView().getModuleGrid().down('moduletoolbar').down('button#regionsouth').setPressed(false);
	  },
	  onEastRegionExpand : function(){
		  this.getView().getModuleGrid().down('moduletoolbar').down('button#regioneast').setPressed(true);
	  },
	  onEastRegionCollapse : function(){
		  this.getView().getModuleGrid().down('moduletoolbar').down('button#regioneast').setPressed(false);
	  },
	  onNavigateToggle : function(toggled){
		  this.toggleWidget(this.getView().getModuleNavigate(), toggled);
	  },

	  onRegionSouthToggle : function(toggled){
		  this.toggleWidget(this.getView().getSouthRegion(), toggled);
	  },

	  onRegionEastToggle : function(toggled){
		  this.toggleWidget(this.getView().getEastRegion(), toggled);
	  },

	  toggleWidget : function(widget, toggled){
		  var sp = Ext.getCmp(widget.getId() + '-splitter');
		  var b = sp.collapseDirection == 'bottom';
		  if (toggled) {
			  if (widget.getCollapsed()) {
				  widget.expand();
				  if (b) sp.setHeight(sp._height);
				  else sp.setWidth(sp._width);
				  sp.setStyle('visibility', 'visible');
				  sp.show();
			  }
		  } else {
			  if (!widget.getCollapsed()) {
				  widget.collapse();
				  if (b) {
					  if (!sp._height) sp._height = sp.getHeight();
					  sp.setHeight(0);
				  } else {
					  if (!sp._width) sp._width = sp.getWidth();
					  sp.setWidth(0);
				  }
				  sp.setStyle('visibility', 'hidden');
				  sp.hide();
			  }
		  }
	  },

	  onBoxReady : function(modulepanel){
		  var s = this.getView().getSouthRegion();
		  if (s && s.defaultHidden) {
			  this.hiddenSplitter(Ext.getCmp(s.getId() + '-splitter'));
		  }
		  s = this.getView().getEastRegion();
		  if (s && s.defaultHidden) {
			  this.hiddenSplitter(Ext.getCmp(s.getId() + '-splitter'));
		  }
	  },

	  hiddenSplitter : function(sp){
		  if (sp) {
			  var b = sp.collapseDirection == 'bottom';
			  if (b) {
				  if (!sp._height) sp._height = sp.getHeight();
				  sp.setHeight(0);
			  } else {
				  if (!sp._width) sp._width = sp.getWidth();
				  sp.setWidth(0);
			  }
			  sp.setStyle('visibility', 'hidden');
			  sp.hide();
		  }
	  },

	  onModuleItemAdded : function(modulepanel, component){
		  if (component.xtype == 'bordersplitter') {
			  var linkedregion = Ext.getCmp(component.getId().replace('-splitter', ''));
			  if (linkedregion && linkedregion.defaultHidden) {
				  this.toggleWidget(linkedregion, true);
			  }
		  } else if (component.defaultHidden) {
			  var sp = Ext.getCmp(component.getId() + '-splitter');
			  if (sp) {
				  sp.on('render', function(sp){
					    var b = sp.collapseDirection == 'bottom';
					    if (b) {
						    if (!sp._height) sp._height = sp.getHeight();
						    sp.setHeight(0);
					    } else {
						    if (!sp._width) sp._width = sp.getWidth();
						    sp.setWidth(0);
					    }
					    sp.setStyle('visibility', 'hidden');
					    sp.hide();
				    })
			  }
		  }
	  }

  })