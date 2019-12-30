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

Ext.define('app.view.platform.module.toolbar.Toolbar', {

	extend : 'Ext.toolbar.Toolbar',

	alias : 'widget.moduletoolbar',

	requires : [ 'app.view.platform.module.toolbar.widget.New', 'app.view.platform.module.toolbar.widget.SearchField',
			'app.view.platform.module.toolbar.ToolbarController', 'app.view.platform.module.toolbar.SettingButton',
			'app.view.platform.module.toolbar.widget.Attachment', 'app.view.platform.module.toolbar.widget.Delete',
			'app.view.platform.module.toolbar.widget.Export' ],

	controller : 'gridtoolbar',
	reference : 'gridtoolbar',
	weight : 4,

	border : false,
	frame : false,
	listeners : {
		resize : 'onToolbarResize',
		selectionchange : 'onSelectionChange'
	},

	isDockTopBottom : function() {
		return this.dock == 'top' || this.dock == 'bottom';
	},

	rebuildButtons : function() {
		this.removeAll(true);
		this.add(this.getButtons());
	},

	getButtons : function() {
		var me = this, obj = me.moduleInfo.fDataobject, baseFunctions = obj.baseFunctions, setting = me.grid.getViewModel()
				.get('module.toolbar'), showtext = (me.isDockTopBottom() && setting.topbottomMode == 'normal')
				|| ((!me.isDockTopBottom()) && setting.leftrightMode == 'normal'), arrowAlign = me.isDockTopBottom() ? 'right'
				: setting.leftrightArrowAlign;
		me.actions = {
			'display' : {
				text : showtext ? '显示' : null,
				tooltip : showtext ? null : '显示选中的记录',
				iconCls : 'x-fa fa-file-text-o',
				itemId : 'display',
				disabled : true,
				bind : {
					scale : '{module.toolbar.buttonScale}'
				},
				handler : 'onDisplayButtonClick'
			},
			'edit' : {
				text : showtext ? '修改' : null,
				iconCls : 'x-fa fa-pencil-square-o',
				itemId : 'edit',
				disabled : true,
				bind : {
					scale : '{module.toolbar.buttonScale}'
				},
				handler : 'onEditButtonClick'
			},
			'add' : {
				xtype : 'newbutton',
				showtext : showtext,
				arrowAlign : arrowAlign,
				bind : {
					scale : '{module.toolbar.buttonScale}'
				}
			},
			'addattachment' : {
				xtype : 'button',
				text : '上传',
				iconCls : 'x-fa fa-cloud-upload',
				handler : 'onUploadParentAttachment',
				showtext : showtext,
				arrowAlign : arrowAlign,
				bind : {
					scale : '{module.toolbar.buttonScale}'
				}
			},
			'delete' : {
				text : showtext ? '删除' : null,
				iconCls : 'x-fa fa-trash-o',
				xtype : 'deletebutton',
				itemId : 'delete',
				disabled : true,
				listeners : {
					click : 'onDeleteRecordButtonClick'
				},
				bind : {
					scale : '{module.toolbar.buttonScale}'
				}
			},
			'filter' : {
				xtype : obj.filterdesign || obj.conditiondesign ? 'button' : 'button',
				iconCls : 'x-fa fa-filter',
				tooltip : '自定义筛选条件',
				enableToggle : true,
				arrowAlign : arrowAlign,
				bind : {
					scale : '{module.toolbar.buttonScale}'
				},
				listeners : {
					toggle : 'onFilterButtonToggle'
				}
			}
		};

		var items = [];

		items.push('@display');
		if (obj.objectname == 'FDataobjectattachment' && me.up('modulepanel').parentFilter) {
			var targetobj = modules.getModuleInfo(me.up('modulepanel').parentFilter.moduleName).fDataobject;
			var targetBaseFunctions = targetobj.baseFunctions;
			if (targetBaseFunctions['attachmentadd'])
				items.push('@addattachment');
			if (targetBaseFunctions['attachmentedit'])
				items.push('@edit');
			if (targetBaseFunctions['attachmentdelete'])
				items.push('@delete');
		} else {
			if (obj.hasinsert && baseFunctions['new'])
				items.push('@add');
			if (obj.hasedit && baseFunctions['edit'])
				items.push('@edit');
			if (obj.hasdelete && baseFunctions['delete'])
				items.push('@delete');
		}

		if (obj.hasattachment && obj.baseFunctions.attachmentquery) {
			items.push({
				xtype : 'attachmentbutton',
				arrowAlign : arrowAlign,
				bind : {
					scale : '{module.toolbar.buttonScale}'
				}
			})
		}

		me.addAdditionFunctions(items, showtext);
		items.push({
			xtype : 'modulegridexportbutton',
			moduleInfo : me.moduleInfo,
			arrowAlign : arrowAlign,
			bind : {
				scale : '{module.toolbar.buttonScale}'
			}
		});

		if (!obj.istreemodel)
			items.push('@filter');

		if (!obj.istreemodel) {
			if (me.isDockTopBottom())
				items.push({
					xtype : 'gridsearchfield'
				})
		} else {
			if (me.isDockTopBottom())
				items.push({
					xtype : 'treesearchfield',
					emptyText : '查找',
					width : 120,
					labelWidth : 0,
					treePanel : me.grid,
					searchField : me.grid.moduleInfo.fDataobject.namefield
				})
		}
		items.push('->');

		var modulepanel = me.grid.modulePanel;
		if (modulepanel.getEnableNavigate())
			items.push({
				bind : {
					scale : '{module.toolbar.buttonScale}'
				},
				xtype : 'button',
				// icon : 'images/button/centerwest.png',
				iconCls : 'x-fa fa-location-arrow',
				enableToggle : true,
				pressed : !modulepanel.getCollapseNavigate(),
				itemId : 'regionnavigate',
				listeners : {
					toggle : 'onRegionNavigateToggle'
				}
			});

		if (modulepanel.getEnableSouth())
			items.push({
				bind : {
					scale : '{module.toolbar.buttonScale}'
				},
				xtype : 'button',
				icon : 'images/button/centersouth.png',
				enableToggle : true,
				pressed : !modulepanel.getCollapseSouth(),
				itemId : 'regionsouth',
				listeners : {
					toggle : 'onRegionSouthToggle'
				}
			});
		if (modulepanel.getEnableEast())
			items.push({
				bind : {
					scale : '{module.toolbar.buttonScale}'
				},
				xtype : 'button',
				icon : 'images/button/centereast.png',
				enableToggle : true,
				pressed : !modulepanel.getCollapseEast(),
				itemId : 'regioneast',
				listeners : {
					toggle : 'onRegionEastToggle'
				}
			});

		items.push({
			xtype : 'toolbarsettingbutton',
			bind : {
				scale : '{module.toolbar.buttonScale}'
			}
		})

		return items;
	},

	addAdditionFunctions : function(items, showtext) {
		var me = this, moduleinfo = this.grid.moduleInfo.fDataobject, functions = moduleinfo.additionFunctions, menus = [];

		if (Ext.isArray(functions)) {
			Ext.each(functions, function(f) {
				var button = {
					text : showtext ? f.title : null,
					additionfunction : true,
					iconCls : f.iconcls,
					tooltip : f.fdescription,
					itemId : f.fcode,
					minselectrecordnum : f.minselectrecordnum,
					maxselectrecordnum : f.maxselectrecordnum,
					orderno : f.orderno,
					windowclass : f.windowclass,
					functionname : f.functionname,
					fxtype : f.xtype,
					menuname : f.menuname
				};
				if (f.othersetting)
					CU.applyOtherSetting(button, f.othersetting);
				items.push(button);
			})
		}

	},

	initComponent : function() {
		var me = this;
		if (me.isDockTopBottom())
			me.layout = {
				overflowHandler : 'scroller'
			};
		me.items = [];
		me.callParent();
	}

})