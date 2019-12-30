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

Ext.define('app.view.platform.module.toolbar.ToolbarController', {
	  extend : 'Ext.app.ViewController',
	  alias : 'controller.gridtoolbar',

	  changeLinkedItem : ['display', 'edit', 'delete'],

	  init : function(){

	  },

	  control : {

		  'button[additionfunction=true]' : {
			  click : function(button){
				  var grid = this.getView().grid, records, record,
					  config = {
						  grid : grid,
						  moduleInfo : grid.moduleInfo,
						  objectName : grid.moduleInfo.fDataobject.objectname
					  };
				  // 是否有选中记录的要求
				  if (button.minselectrecordnum >= 1 && button.maxselectrecordnum >= 1) {
					  records = grid.getSelectedRecord(button.minselectrecordnum, button.maxselectrecordnum);
					  if (records) {
						  config.records = records;
						  config.record = records[0];
					  }
				  }
				  if (button.windowclass) {
					  Ext.create(button.windowclass, config).show();
				  } else if (button.functionname) {
					  var fn = button.functionname;
					  if (fn.indexOf('.') == -1) { // 系统函数，在'app.view.platform.frame.system.Function'里面
						  systemFunction[button.functionname](config);
					  }
				  }
			  }
		  },
		  'menuitem' : {
			  click : function(menuitem){
			  }
		  }
	  },


	  onSelectionChange : function(selected){
		  var me = this,
			  view = me.getView(),
			  action = selected.length ? 'enable' : 'disable';
		  Ext.each(me.changeLinkedItem, function(item){
			    var ib = view.down('button#' + item);
			    if (ib) ib[action]();
		    });

	  },

	  onFilterButtonToggle : function(filterButton, pressed){
		  var modulegrid = filterButton.up('tablepanel');
		  if (pressed) {
			  if (!modulegrid.userfilter) {
				  modulegrid.selectUserFilter();
			  }
			  modulegrid.userfilter.show();
		  } else {
			  modulegrid.userfilter.hide();
		  }
	  },

	  onDisplayButtonClick : function(button){
		  var grid = button.up('tablepanel');
		  if (grid.getFirstSelectedRecord()) grid.moduleInfo.showDisplayWindow(grid);
	  },

	  onNewButtonClick : function(button){
		  var grid = button.up('tablepanel');
		  grid.moduleInfo.showNewWindow(grid);
	  },

	  onNewWithCopyButtonClick : function(button){
		  var me = this,
			  grid = me.getView().grid;
		  var selected = grid.getFirstSelectedRecord();
		  if (selected) {
			  grid.moduleInfo.showNewWindow(grid, selected);
		  }
	  },

	  onEditButtonClick : function(button){
		  var grid = button.up('tablepanel');
		  if (grid.getFirstSelectedRecord()) grid.moduleInfo.showEditWindow(grid);
	  },

	  onToolbarResize : function(toolbar, width, height, oldWidth, oldHeight, eOpts){
		  if (this.getView().isDockTopBottom()) this.calcToAdjustBox();
	  },

	  calcToAdjustBox : function(){
		  var view = this.getView();
		  var tbfill = view.down('tbfill');
		  if (!tbfill) return;
		  if (tbfill.getBox(false, true).width == 0) {
			  var item = last = view.items.last();
			  while (item && last.getBox(false, true).right + 3 > view.getWidth()) {
				  if (item.isXType('button') && item.text && (item.icon || item.iconCls)) {
					  item._text = item.text;
					  if (!item._width) item._width = item.getWidth();
					  item.setText(null);
				  }
				  item = item.previousSibling();
			  }
		  } else {
			  var item = view.items.first();
			  while (item) {
				  if (item.isXType('button') && item._text) {
					  if (tbfill.getWidth() > (item._width - item.getWidth())) {
						  item.setText(item._text);
						  delete item._text;
					  } else break;
				  }
				  item = item.nextSibling();
			  }
		  }
	  },

	  onRegionNavigateToggle : function(button, toggled){
		  this.getView().grid.modulePanel.fireEvent('navigatetoggle', toggled);
	  },

	  onRegionSouthToggle : function(button, toggled){
		  this.getView().grid.modulePanel.fireEvent('regionsouthtoggle', toggled);
	  },

	  onRegionEastToggle : function(button, toggled){
		  this.getView().grid.modulePanel.fireEvent('regioneasttoggle', toggled);
	  },

	  onUploadParentAttachment : function(){
		  var me = this,
			  grid = me.getView().grid;
		  var pf = grid.parentFilter;
		  Ext.widget('attachmentquickuploadwindow', {
			    objectid : pf.moduleName,
			    objecttitle : pf.fieldtitle,
			    keyid : pf.fieldvalue,
			    keytitle : pf.text,
			    callback : function(){
				    grid.getStore().reload();
			    },
			    callbackscope : me
		    }).show();
	  },

	  onUploadAttachment : function(button){
		  var me = this,
			  grid = me.getView().grid;
		  var selected = grid.getFirstSelectedRecord();
		  if (selected) {
			  Ext.widget('attachmentquickuploadwindow', {
				    objectid : selected.module.fDataobject.objectid,
				    objecttitle : selected.module.fDataobject.title,
				    keyid : selected.getIdValue(),
				    keytitle : selected.getTitleTpl(),
				    callback : function(){
					    grid.refreshRecord(selected);
				    },
				    callbackscope : me
			    }).show();
		  }
	  },

	  onPreviewAttachment : function(button){
		  var me = this,
			  grid = me.getView().grid;
		  var selected = grid.getFirstSelectedRecord();
		  if (selected) {
			  AttachmentUtils.showInCenterRegion({
				    moduleName : selected.module.fDataobject.objectid,
				    fieldtitle : selected.module.fDataobject.title,
				    operator : "=",
				    fieldvalue : selected.get(selected.idProperty),
				    text : selected.getTitleTpl()
			    })
		  }
	  },

	  onDisplayAttachment : function(button){
		  var me = this,
			  grid = me.getView().grid;
		  var selected = grid.getFirstSelectedRecord();
		  if (selected) {
			  AttachmentUtils.showInCenterRegion({
				    moduleName : selected.module.fDataobject.objectid,
				    fieldtitle : selected.module.fDataobject.title,
				    operator : "=",
				    fieldvalue : selected.get(selected.idProperty),
				    text : selected.getTitleTpl(),
				    showGrid : true
			    })
		  }
	  },

	  onDownloadAllAttachment : function(button){
		  var me = this,
			  grid = me.getView().grid;
		  var selected = grid.getFirstSelectedRecord();
		  if (selected) {
			  AttachmentUtils.downloadall(selected.module.fDataobject.objectid, selected.getIdValue())
		  }
	  },

	  // 下载方案，excel 和 word
	  onExcelSchemeItemClick : function(menuitem){
		  var me = this,
			  grid = me.getView().grid;
		  var selected = grid.getFirstSelectedRecord();
		  if (selected) {
			  window.location.href =
			      'platform/dataobjectexport/exportexcelscheme.do?schemeid=' + menuitem.schemeid + '&objectid='
			          + me.getView().moduleInfo.fDataobject.objectid + '&recordid=' + selected.getIdValue();
		  }
	  },

	  onExportExeclButtonClick : function(){
		  var me = this;
		  me.exportExcel({});
	  },

	  onExportCurrentPageExeclButtonClick : function(){
		  var me = this;
		  me.exportExcel({
			    thispage : true
		    });
	  },
	  onExportPdfButtonClick : function(){
		  var me = this;
		  me.exportExcel({
			    toPdf : true
		    });
	  },

	  onExportCurrentPagePdfButtonClick : function(){
		  var me = this;
		  me.exportExcel({
			    thispage : true,
			    toPdf : true
		    });
	  },

	  onPrintExeclButtonClick : function(){
		  var me = this;
		  me.exportExcel({
			    toPdf : true
		    });
	  },

	  exportExcel : function(parameters){
		  var me = this,
			  view = me.getView(),
			  grid = view.grid,
			  store = grid.getStore(),
			  lastOptions = store.lastOptions,
			  params = {};
		  if (parameters.toPdf) {
			  params.topdf = true;
		  }
		  Ext.apply(params, {
			    colorless : me.lookupReference('colorless').getValue(),
			    usemonetary : me.lookupReference('usemonetary').getValue(),
			    sumless : me.lookupReference('sumless').getValue()
		    })
		  Ext.apply(params, store.lastExtraParams);
		  if (lastOptions.filters) {
			  var filters = [];
			  Ext.each(lastOptions.filters, function(filter){
				    filters.push(filter.serialize())
			    })
			  params.filter = Ext.encode(filters);
		  }
		  if (lastOptions.sorters) {
			  var sorters = [];
			  Ext.each(lastOptions.sorters, function(sorter){
				    sorters.push(sorter.serialize())
			    })
			  params.sort = Ext.encode(sorters);
		  }
		  if (lastOptions.grouper) {
			  params.group = Ext.encode({
				    property : lastOptions.grouper.config.property,
				    direction : lastOptions.grouper.config.direction
			    })
		  }

		  params.columns = Ext.encode(view.grid.getExportGridColumns());
		  var conditions = [];

		  if (parameters.thispage) {
			  params.page = store.currentPage;
			  params.start = (store.currentPage - 1) * store.pageSize;
			  params.limit = store.pageSize;
			  conditions.push({
				    property : '第',
				    operator : '' + store.currentPage,
				    value : '页'
			    })
		  } else {
			  params.page = 1;
			  params.start = 0;
			  params.limit = 65000
		  }

		  if (grid.parentFilter) conditions.push({
			    property : grid.parentFilter.fieldtitle + ":",
			    operator : '',
			    value : (grid.parentFilter.text ? grid.parentFilter.text : '未选中')
		    })

		  Ext.each(grid.getNavigateTexts(), function(navigate){
			    conditions.push(navigate)
		    })
		  Ext.each(grid.getFilterTexts(), function(filter){
			    conditions.push(filter);
		    });
		  params.conditions = Ext.encode(conditions);

		  var children = [];
		  for (var i in params) {
			  children.push({
				    tag : 'input',
				    type : 'hidden',
				    name : i,
				    value : Ext.isString(params[i]) ? params[i].replace(new RegExp('"', 'gm'), "'") : params[i]
			    })
		  }
		  var form = Ext.DomHelper.append(document.body, {
			    tag : 'form',
			    method : 'post',
			    action : 'platform/dataobjectexport/exporttoexcel.do',
			    children : children
		    });
		  document.body.appendChild(form);
		  form.submit();
		  document.body.removeChild(form);

	  },

	  /**
		 * 删除grid的当前选中的记录
		 */
	  onDeleteRecordButtonClick : function(button){
		  var grid = this.getView().grid;
		  if (grid.getSelectionModel().getSelection().length > 1) {
			  this.deleteRecords(button);
			  return;
		  }
		  var selected = grid.getFirstSelectedRecord('delete');
		  if (selected) {
			  var canDelete = selected.canDelete();
			  if (typeof canDelete == 'object') {
				  Ext.toastWarn(canDelete.message);
				  return false;
			  }
			  var text = grid.moduleInfo.modulename + ":『" + selected.getTitleTpl() + '』';
			  Ext.MessageBox.confirm('确定删除', '确定要删除当前选中的' + text + '吗?', function(btn){
				    if (btn == 'yes') {
					    var deleted = Ext.create(grid.store.model, selected.data);
					    deleted.erase({
						      success : function(proxy, operation){
							      var result = Ext.decode(operation.getResponse().responseText);
							      if (result.resultCode == 0) {
								      EU.toastInfo(text + ' 已被成功删除！');
								      grid.refreshAll();
							      } else
							      // 删除失败
							      Ext.MessageBox.show({
								        title : '记录删除失败',
								        msg : text + '删除失败<br/><br/>' + result.message,
								        buttons : Ext.MessageBox.OK,
								        animateTarget : button.id,
								        icon : Ext.MessageBox.ERROR
							        });
						      }
					      });
				    }
			    });
		  }
	  },

	  /**
		 * 删除grid的当前选中的多条记录，
		 */
	  deleteRecords : function(button){
		  var grid = this.getView().grid;
		  var selection = grid.getSelectionModel().getSelection();
		  var errormessage = [];

		  Ext.each(selection, function(model){
			    var canDelete = model.canDelete();
			    if (typeof canDelete == 'object') {
				    errormessage.push(canDelete.message);
			    }
		    });
		  if (errormessage.length != 0) {
			  var s =
			      Ext.String.format('以下 {0} 条不能删除，请重新选择后再删除。<br/>{1}', errormessage.length, '<ol><li>'
			            + errormessage.join('</li><li>') + '</li></ol>');
			  EU.toastWarn(s);
			  return false;
		  }
		  var text = '<ol><li>' + grid.getSelectionTitleTpl().join('</li><li>') + '</li></ol>';
		  Ext.MessageBox.confirm('确定删除', Ext.String.format(
		      '确定要删除' + grid.moduleInfo.modulename + '当前选中的 {0} 条记录吗?<br/>{1}', selection.length, text
		    ), function(btn){
			    if (btn == 'yes') {
				    EU.RS({
					      url : 'platform/dataobject/removerecords.do',
					      params : {
						      modulename : grid.moduleInfo.fDataobject.objectname,
						      ids : grid.getSelectionIds().join(","),
						      titles : grid.getSelectionTitleTpl().join("~~")
					      },
					      callback : function(info){
						      if (info.resultCode == 0) {
							      EU.toastInfo(text + ' 已成功被删除。');
						      } else {
							      // 删除失败
							      Ext.MessageBox.show({
								        title : '删除结果',
								        msg : (info.okMessageList.length > 0 ? ('已被删除记录：<br/>' + '<ol><li>'
								            + info.okMessageList.join('</li><li>') + '</li></ol><br/>') : '')
								            + '删除失败记录：<br/>' + '<ol><li>' + info.errorMessageList.join('</li><li>') + '</li></ol>',
								        buttons : Ext.MessageBox.OK,
								        animateTarget : button.id,
								        icon : Ext.MessageBox.ERROR
							        });
						      }
						      // 有记录被删除，才刷新
						      if (info.okMessageList.length > 0) grid.refreshAll();
					      }
				      })
			    }
		    })
	  }

  });
