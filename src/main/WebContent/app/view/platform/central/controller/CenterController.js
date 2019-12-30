/**
 * 用来管理center界面里面所有的模块，查询等的tab的创建和维护
 * 
 * @author jiangfeng
 * 
 * www.jhopesoft.com
 * 
 * jfok1972@qq.com
 * 
 * 2017-06-01
 * 
 */
Ext
		.define(
				'app.view.platform.central.controller.CenterController',
				{
					extend : 'Ext.Mixin',

					requires : [ 'app.view.platform.module.Module', 'app.view.platform.central.region.autoOpen.Model' ],

					init : function() {
					},

					addModuleToCenter : function(menuitem) {

						var maincenter = this.getView().down('maincenter');
						if (menuitem.menuType === 'module') {
							this.addModuleToMainRegion(menuitem);
						}
					},

					/**
					 * 将标准模块加入tabpanel中了，如果已经有了，就转至该tab页 itemId:module_(moduleName)
					 */
					addModuleToMainRegion : function(menuitem, donotActive) {
						var moduleName = menuitem.moduleName;
						var menuid = menuitem.menuid;
						var view = this.getView().down('maincenter');
						var tabItemId = 'module_' + menuid; // tabPanel中的itemId
						var tab = view.down('> panel#' + tabItemId);// 查找当前主区域中是否已经加入了此模块了
						if (!tab) {
							var tabPanel = null;
							// type : 01=外部xtype，03=实体对象
							if (menuitem.type == '01') {
								tabPanel = Ext.getCmp(tabItemId);
								if (!tabPanel) {
									tabPanel = Ext.create(menuitem.url, {
										id : tabItemId,
										autoDestroy : true,
										title : menuitem.text,
										closable : true
									});
								}
							} else if (menuitem.type == '03') {
								tabPanel = modules.getModuleInfo(moduleName).getModulePanel(tabItemId);
							}
							if (!tabPanel)
								return;
							if (!Ext.isEmpty(menuitem.glyph))
								tabPanel.glyph = menuitem.glyph;
							if (!Ext.isEmpty(menuitem.iconCls))
								tabPanel.iconCls = menuitem.iconCls;
							tab = view.add(tabPanel);
						}
						if (!donotActive)
							view.setActiveTab(tab);
					},

					addParentFilterModule : function(p) {
						var childinfo = modules.getModuleInfo(p.childModuleName), view = this.getView().down('maincenter'), modulepanel = childinfo
								.getPanelWithParentFilter(p.parentModuleName, p.fieldahead, p.pid, p.ptitle, p.param);
						var tab = view.down('panel#' + modulepanel.itemId);
						if (!tab)
							tab = view.add(modulepanel);
						view.setActiveTab(tab);
					},

					addAttachmentToMainRegion : function(attachmentpanel, isCreate) {
						var view = this.getView().down('maincenter'), tabItemId = '_attachment_tab', tab = view.down('panel#'
								+ tabItemId);
						if (tab) {
							if (isCreate) {
								tab.removeAll(true);
								tab.add(attachmentpanel);
							}
						} else {
							tab = view.add({
								xtype : 'panel',
								layout : 'fit',
								iconCls : 'x-fa fa-paperclip',
								items : [ attachmentpanel ],
								closable : true,
								itemId : tabItemId
							});
						}
						tab.setTitle(attachmentpanel.parentFilter.fieldtitle + ':' + attachmentpanel.parentFilter.text);
						view.setActiveTab(tab);
					},


					onTabAdd : function(panel, component, index, eOpts) {
						// 如果当前已经打开了最大的tab数，则删除最前面的一个
						if (panel.items.length > this.getView().getViewModel().get('maxOpenTab')) {
							panel.remove(1);
						}
					},

					getAutoOpenStore : function() {
						return Ext.create('Ext.data.Store', {
							model : 'app.view.platform.central.region.autoOpen.Model'
						});
					},

					// 设置模块是否自动打开，单击后操作
					moduleAutoOpenMenuClick : function(tool) {
						var me = this, tab = tool.ownerCt.tabPanel, store = me.getAutoOpenStore();
						store.load();
						if (tool.checked) {
							if (!me.isModuleInAutoOpenStore(store, tab.type, tab.objectid)) {
								store.add({
									type : tab.type,
									objectid : tab.objectid
								})
								store.sync();
							}
						} else {
							store.each(function(record) {
								if (record.get('type') == tab.type && record.get('objectid') == tab.objectid) {
									store.remove(record);
									store.sync();
									return false;
								}
							})
						}
						store.destroy();
					},

					isModuleInAutoOpenStore : function(store, type, objectid) {
						var result = null;
						store.each(function(record) {
							if (record.get('type') == type && record.get('objectid') == objectid) {
								result = record;
								return false;
							}
						})
						return result;
					},

					// 模块打开时自动定位到
					moduleAutoOpenAndSelectedMenuClick : function(tool) {
						var me = this, tab = tool.ownerCt.tabPanel, store = me.getAutoOpenStore();
						store.load();
						var record = me.isModuleInAutoOpenStore(store, tab.type, tab.objectid);
						if (tool.checked) {
							store.each(function(record) {
								if (record.get('focused') == true) {
									record.set('focused', null);
								}
							})
							if (!record) {
								record = store.add({
									type : tab.type,
									objectid : tab.objectid
								})[0];
							}
							record.set('focused', true);
							store.sync();
						} else {
							if (record) {
								record.set('focused', null);
								store.sync();
							}
						}
						store.destroy();
					},

					/**
					 * 加入自动打开的模块，这里要用延迟加载，不然有些参数还没有初始化好
					 */
					centerAfterRender : function(panel) {

						var bar = panel.tabBar;
						bar.add([ {
							reorderable : false,
							xtype : 'component',
							flex : 1
						}, {
							xtype : 'buttontransparent',
							reorderable : false,
							tooltip : '隐藏顶部和底部区域',
							iconCls : 'x-fa fa-expand',
							handler : function(button) {
								app.viewport.down('maintop').hide();
								app.viewport.down('mainbottom').hide();
								button.hide();
								button.nextSibling().show();
							}
						}, {
							xtype : 'buttontransparent',
							hidden : true,
							tooltip : '显示顶部和底部区域',
							reorderable : false,
							iconCls : 'x-fa fa-compress',
							handler : function(button) {
								app.viewport.down('maintop').show();
								app.viewport.down('mainbottom').show();
								button.hide();
								button.previousSibling().show();
							}
						} ]);

						Ext.Function.defer(function() {
							this.showAutoOpenModules();

						}, 200, this);
					},

					/**
					 * 打开需要自动打开的模块在center 区域
					 */
					showAutoOpenModules : function() {
						var me = this, store = me.getAutoOpenStore();
						store.load();
						vm = me.getView().getViewModel();
						store.each(function(record) {
							var menuitem = vm.getMenuFromObjectid(record.get('objectid'));
							if (menuitem) {
								me.addModuleToMainRegion(menuitem, record.get('focused') != true)
							}
						})
					},

					// 判断模块是否是自动打开的
					isModuleAutoOpen : function(tabPanel) {
						var me = this, store = me.getAutoOpenStore();
						store.load();
						var found = false;
						store.each(function(record) {
							if (record.get('type') == tabPanel.type && record.get('objectid') == tabPanel.objectid) {
								found = true;
								return false;
							}
						})
						store.destroy();
						return found;
					},

					// 打开时定位到
					isModuleAutoOpenAndSelected : function(tabPanel) {
						var me = this, store = me.getAutoOpenStore();
						store.load();
						var found = false;
						store.each(function(record) {
							if (record.get('type') == tabPanel.type && record.get('objectid') == tabPanel.objectid
									&& record.get('focused')) {
								found = true;
								return false;
							}
						})
						store.destroy();
						return found;

					},

					onMaxtabChange : function(field, newValue, oldValue) {
						var me = this;
						me.getViewModel().set('maxOpenTab', newValue);
					}

				});
