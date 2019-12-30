/**
 * 自定义的grid 的页 导航条，可以上一面，下一页的，主要问题是 classic 等几个css ,所有按钮的宽度都没有了
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


Ext.define('app.view.platform.module.paging.Paging', {
	  extend : 'Ext.toolbar.Paging',
	  alias : 'widget.ownpagingtoolbar',
	  xtype : 'modulepagingtoolbar',

	  requires : ['app.view.platform.module.paging.sortScheme.SortButton',
	      'app.view.platform.module.toolbar.widget.SchemeSegmented', 'app.view.platform.module.paging.PageSizeCombo',
	      'app.view.platform.module.toolbar.widget.gridScheme.MenuButton',
	      'app.view.platform.module.paging.FilterDetailButton', 'app.view.platform.module.paging.SummaryToggleButton'],

	  padding : '2px 5px 2px 5px',

	  prependButtons : true,
	  store : this.store,
	  dock : 'bottom',
	  displayInfo : true,

	  initComponent : function(){
		  var me = this;
		  me.items = [];
		  var o = me.moduleInfo.fDataobject;
		  if (o.griddesign || o.gridshare || me.moduleInfo.getGridSchemeCount() > 1) {
			  me.items.push({
				    xtype : 'gridschememenubutton',
				    objectName : me.objectName
			    })
			  me.items.push({
				    xtype : 'container',
				    layout : {
					    type : 'hbox'
				    },
				    items : [{
					        xtype : 'gridschemesegmented',
					        objectName : me.objectName
				        }]
			    })
		  }
		  me.items.push({
			    tooltip : '自动调整列宽',
			    itemId : 'autocolumnwidth',
			    iconCls : 'x-fa fa-text-width',
			    handler : function(button){
				    button.up('tablepanel').autoSizeAllColumn();
			    }
		    })
		  me.items.push({
			    xtype : 'gridsortbutton',
			    target : me.target,
			    moduleInfo : me.moduleInfo
		    })
		  me.items.push({
			    xtype : 'gridfilterdetailbutton'
		    })
		  if (!o.istreemodel && me.moduleInfo.hasSummaryField()) {
			  me.items.push({
				    xtype : 'summarytogglebutton'
			    })
			  me.style = 'border-top-width: 1px !important;';
		  }
		  if (!o.istreemodel) me.items.push({
			    xtype : 'pagesizecombo',
			    triggers : {
				    clear : false
			    }
		    })
		  me.callParent(arguments);
		  if (o.istreemodel) {
			  var first = me.down('#first');
			  while (first.nextSibling().itemId !== 'refresh') {
				  first.hide();
				  first = first.nextSibling();
			  }
		  }
	  }
  });