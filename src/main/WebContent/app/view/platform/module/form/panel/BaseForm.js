Ext.define('app.view.platform.module.form.panel.BaseForm', {
	extend : 'Ext.form.Panel',
	alternateClassName : 'baseForm',
	alias : 'widget.baseform',
	requires : [ 'app.view.platform.module.form.utils.FormUtils' ],
	bodyPadding : '1px 1px',
	trackResetOnLoad : true,
	autoScroll : true,
	bodyBorder : false,
	showclosebtn : true,
	referenceHolder : true,
	viewModel : true,
	/** 外部传入 */
	config : {
		/** 必要参数 模块名称 */
		modulecode : undefined,
		/** 必要参数 当前form的操作类型 display,new,edit,approve */
		operatetype : undefined,
		/** 当前模块信息 */
		moduleinfo : undefined,
		/** 变量 当前表对象 */
		fDataobject : undefined,
		/** 变量 当前form的中文名称(显示，修改，新增)等 */
		formtypetext : undefined,
		/** 变量 当前类型下全部的表单方案 */
		formSchemes : [],
		/** 变量 展示的表单方案 */
		formScheme : undefined
	},
	/** 是否来源grid */
	isSourceGrid : false,
	/** grid对象 */
	gridPanel : undefined,
	/** 当前选中的grid或外部传递的模型对象 */
	dataModel : undefined,
	/** 根据数据ID查询 */
	dataid : undefined,
	/** 子窗口 */
	subobjects : [],

	initComponent : function() {
		var me = this, config = me.config, syscfg = app.viewport.getViewModel().data;
		me.subobjects = [];
		me.config.moduleinfo = modules.getModuleInfo(me.config.modulecode);
		this.buttons_ = this.buttons_ || [ "->" ];
		this.buttons_.push({
			text : '关闭',
			itemId : 'close',
			hidden : !me.showclosebtn,
			iconCls : 'x-fa fa-close',
			handler : function(button) {
				me.up("window").close();
			}
		});
		this.buttons_.push('->');
		var bp = config.formScheme.buttonsposition;
		this.dockedItems = [ {
			xtype : 'toolbar',
			hidden : bp === 'hidden',
			dock : Ext.isEmpty(bp, true) || bp === 'hidden' ? 'bottom' : bp,
			ui : 'footer',
			defaults : {},
			items : this.buttons_
		} ];
		Ext.apply(this, {
			layout : config.formScheme.layout,
			items : this.renderView(config.formScheme.details, syscfg)
		})
		switch (config.formScheme.layout) {
		case 'table': {
			Ext.apply(this, {
				layout : {
					type : 'table',
					columns : config.formScheme.cols || 1,
					tableAttrs : {
						style : {
							width : config.formScheme.widths || '100%'
						}
					}
				}
			});
			break;
		}
		}
		if (config.formScheme.othersetting) {
			Ext.apply(this, Ext.decode(config.formScheme.othersetting));
		}
		me.callParent(arguments);
	},

	/**
	 * 初始化数据
	 * 
	 * @param {}
	 *          obj
	 */
	initData : function(obj) {
		var me = this;
		this.isSourceGrid = false;
		if (obj instanceof Ext.panel.Table) { // 传递grid对象，并且显示选择的数据
			me.isSourceGrid = true;
			me.gridPanel = obj;
			var models = obj.getSelection();
			if (models.length > 0) {
				me.dataModel = models[0];
				me.setFormData(me.dataModel);
			}
			this.down('button[id=' + this.getId() + 'editprior' + ']').show()
			this.down('button[id=' + this.getId() + 'editnext' + ']').show()
		} else if (obj instanceof Ext.data.Model) { // 外部直接传递对象
			me.setFormData(me.dataModel = obj);
		} else if (Ext.isString(obj)) { // 外部传递id
			me.setRecordId(obj);
		}
	},

	/**
	 * 不是grid中调用的显示某条记录的信息，可能是其他模块点击namefields来调用的
	 * 
	 * @param {}
	 *          id
	 */
	setRecordId : function(id) {
		var me = this;
		var param = {};
		param[me.config.moduleinfo.fDataobject.primarykey] = id;
		var dataRecord = Ext.create(me.config.moduleinfo.model, param);
		var myMask = new Ext.LoadMask({
			msg : '正在访问服务器, 请稍候...',
			target : me.up("panel"),
			removeMask : true
		});
		myMask.show();
		dataRecord.load({
			callback : function(record, operation, success) {
				myMask.hide();
				if (!success) {
					var result = Ext.decode(operation.getResponse().responseText);
					me.showErrorMsg(result);
					return;
				}
				if (me.config.moduleinfo.fDataobject.istreemodel) {
					var treedata = record.data.data;
					delete record.data;
					record.data = treedata;
					me.setFormData(record);
				}
				me.setFormData(record);
			}
		});
	},

	/**
	 * 设置数据
	 * 
	 * @param {}
	 *          model
	 */
	setFormData : function(model) {
		var me = this;
		Ext.log("form表单初始值：");
		// Ext.log(model);
		if (model) {
			this.getForm().loadRecord(model);
			this.getForm().clearInvalid();
			this.setWindowTitle(model.getTitleTpl());
		} else {
			model = {};
			this.getForm()._record = null;
			var record = Ext.create(me.config.moduleinfo.model, {});
			this.getForm().loadRecord(record);
			this.getForm().clearInvalid();
			this.setWindowTitle(record.getTitleTpl());
			var params = {
				objectname : me.config.fDataobject.objectname
			};
			var values = {};
			if (me.isSourceGrid) {
				params.parentfilter = Ext.encode(me.gridPanel.parentFilter);
				params.navigates = Ext.encode(me.gridPanel.getStore().navigates);
				// 是否是codelevel样式的树形表，是的话，如果有当前选中记录，那么就把新增的id 改为当前id +1
				if (me.config.fDataobject.codelevel) {
					if (me.gridPanel.getSelectionModel().getSelection().length > 0) {
						var selected = me.gridPanel.getSelectionModel().getSelection()[0];
						var pkey = me.config.moduleinfo.model.idProperty;
						if (selected.get(pkey)) {
							values[pkey] = CU.getNextId(selected.get(pkey));
						}
					}
				}
			}
			var fields = this.getForm().getFields();
			Ext.each(fields.items, function(field) {
				if (field.defaultvalue) {
					if (field.defaultvalue == 'now') {
						values[field.name] = new Date();
					} else {
						values[field.name] = field.defaultvalue;
					}
				}
			});

			var navigates = me.gridPanel.getStore().navigates;
			if (Ext.isArray(navigates)) {
				Ext.each(navigates, function(n) {
					values[(n.fieldahead ? n.fieldahead + '.' : '') + n.fieldName] = n.fieldvalue
				})
			}
			if (me.gridPanel.parentFilter) {
				var n = me.gridPanel.parentFilter;
				values[(n.fieldahead ? n.fieldahead + '.' : '') + n.fieldName] = n.fieldvalue
			}
			Ext.log("默认值:");
			Ext.log(values);
			me.getForm().setValues(values);
			EU.RS({
				url : "platform/dataobject/getnewdefault.do",
				params : params,
				target : me,
				callback : function(result) {
					if (result.success == false) {
						EU.toastError(result.message);
						return;
					}
					me.getForm().setValues(result.data);
				}
			});
		}
		Ext.each(me.subobjects, function(panel) {
			if (model.getIdValue) {
				panel.fireEvent('parentfilterchange', {
					fieldvalue : model.getIdValue(),
					text : model.getNameValue()
				})
			} else {
				panel.fireEvent('parentfilterchange', {
					fieldvalue : null,
					text : '未定义'
				})
			}
		});
	},

	/**
	 * 新增数据
	 */
	saveNew : function(button) {
		var me = this, grid = me.gridPanel;
		if (!this.getForm().isValid())
			return;
		var myMask = new Ext.LoadMask({
			msg : "正在保存结果，请稍候......",
			target : me
		});
		myMask.show();
		var model = Ext.create(me.config.moduleinfo.model, me.getForm().getValues());
		model.getProxy().extraParams.opertype = 'new';
		model.phantom = true; // 服务器上还没有此条记录
		model.save({
			callback : function(record, operation, success) {
				myMask.hide();
				var result = Ext.decode(operation.getResponse().responseText);
				if (!result.success) {
					me.showErrorMsg(result);
					return;
				}
				// 修改来源的Model数据
				record.updateRecord(result.data);
				if (me.isSourceGrid) {
					if (grid instanceof Ext.tree.Panel) {
						record.set('leaf', true);
						if (grid.getSelectionModel().getSelection().length > 0) {
							var selected = grid.getSelectionModel().getSelection()[0];
							var pkey = grid.moduleInfo.fDataobject.parentkey;
							if (pkey) {
								EU.RS({
									url : 'platform/dataobject/updateparentkey.do',
									async : false,
									params : {
										objectname : grid.moduleInfo.fDataobject.objectname,
										id : record.getIdValue(),
										parentkey : selected.get(pkey)
									},
									callback : function(result) {
										if (result.success) {
											record.set(pkey, selected.get(pkey));
											record.commit();
											var pnode = selected.parentNode;
											pnode.insertChild(pnode.indexOf(selected) + 1, record);
										} else {
											EU.toastWarn('更改父键时出错:' + result.msg + '<br/>新加入的记录放在最顶层！');
											grid.getRootNode().appendChild(record);
										}
									}
								})
							} else {
								// 放在最近的codelevel下面,和当前选中的是同一个父节点
								var codelevel = grid.moduleInfo.fDataobject.codelevel;
								var pid = CU.getParentId(codelevel, record.getIdValue());
								if (CU.getParentId(codelevel, selected.getIdValue()) == pid) {
									var pnode = selected.parentNode;
									pnode.insertChild(pnode.indexOf(selected) + 1, record);
								} else {
									if (pid) {
										var pnode = grid.getStore().getNodeById(pid);
										if (pnode)
											pnode.insertChild(pnode.indexOf(selected) + 1, record);
										else
											grid.getRootNode().appendChild(record);
									} else
										// 放在根路径之下
										grid.getRootNode().appendChild(record);
								}
							}
						} else {
							grid.getRootNode().appendChild(record);
						}
					} else {
						grid.getStore().loadData([ record ], true);
						grid.getStore().totalCount++;
					}
					grid.getSelectionModel().select(record, false, button.suppressEvent); // 要不要执行selectionchange事件,true不执行
					// 滚动到当前新增的记录
					Ext.fly(grid.getView().getNode(record)).scrollIntoView(grid.getView().getEl(), null, true, true)
				}
				// 设置window的title名称
				me.setWindowTitle(record.getTitleTpl());
				// 修改表单数据，返回后台的数据重新赋予表单
				me.setFormData(record);
				me.setReadOnly(true);
				var text = me.config.moduleinfo.modulename + ":『<font color='red'>" + model.getTitleTpl() + '</font>』';
				EU.toastInfo(text + "已被成功添加！");
			}
		});
	},

	/**
	 * 修改数据 只提交修改后的数据
	 */
	saveEdit : function() {
		var me = this, form = this.getForm();
		if (!form.isValid())
			return;
		var myMask = new Ext.LoadMask({
			msg : "正在保存结果，请稍候......",
			target : me
		});
		myMask.show();
		var model = Ext.create(me.config.moduleinfo.model, form.getRecord().getData());
		var fields = form.getFields().items;
		var mfields = model.getFields();
		var key = model.idProperty;
		var oldid = model.getIdValue();
		for (var i = 0; i < fields.length; i++) {
			var field = fields[i];
			Ext.each(mfields, function(f) {
				if (f.name == field.name) {
					found = true;
					return;
				}
			})
			if (!found || model.get(field.name) == field.getValue())
				continue;
			if (field.readOnly || field.xtype == 'fileuploadfield'
					|| (field.xtype == 'imagefield' && (model.get(field.name) == null && field.getValue() == '')))
				continue;
			if (field.xtype == "datetimefield") {
				model.set(field.name, field.getSubmitValue());
			} else {
				model.set(field.name, field.getValue());
			}
		}
		var id = me.getForm().getValues()[key];
		if (!Ext.isEmpty(id) && oldid != id) {
			model.getProxy().extraParams.oldid = oldid;
		}
		model.getProxy().extraParams.opertype = 'edit';
		model.phantom = false; // 服务器上还没有此条记录
		model.save({
			callback : function(record, operation, success) {
				myMask.hide();
				var result = Ext.decode(operation.getResponse().responseText);
				if (!result.success) {
					me.showErrorMsg(result);
					return;
				}
				var returnModel = Ext.create(me.config.moduleinfo.model, result.data);
				// 如果dataModel不为null且更新
				if (me.dataModel)
					me.dataModel.updateRecord(returnModel);
				// 修改表单数据，返回后台的数据重新赋予表单
				me.setFormData(returnModel);
				var text = me.config.moduleinfo.modulename + ":『<font color='red'>" + model.getTitleTpl() + '</font>』';
				EU.toastInfo(text + "修改成功！");
			}
		});
	},

	/**
	 * 删除数据
	 */
	remove : function() {

	},

	/**
	 * 如果数据来源grid，上一条数据
	 */
	selectPriorRecord : function() {
		if (this.isSourceGrid) {
			this.gridPanel.selectPriorRecord();
		}
	},

	/**
	 * 如果数据来源grid，下一条数据
	 */
	selectNextRecord : function() {
		if (this.isSourceGrid) {
			this.gridPanel.selectNextRecord();
		}
	},

	/**
	 * 显示前执行
	 */
	beforeShow : function() {
	},

	/**
	 * 显示后执行
	 */
	afterShow : function() {
		this.focusFirstField();
	},

	/**
	 * 设置window窗口的Title内容
	 * 
	 * @param {}
	 *          titletpl
	 */
	setWindowTitle : function(titletpl) {
		var window = this.up('window[modulecode=' + this.config.modulecode + ']');
		if (window) {
			titletpl = (Ext.isEmpty(titletpl) || titletpl == "null") ? "" : ' 〖<em>' + titletpl + '</em>〗';
			var title = this.config.formtypetext + ' ' + this.fDataobject.title + titletpl;
			window.setTitle(title);
		}
	},

	/**
	 * 渲染界面
	 * 
	 * @param {}
	 *          details
	 * @return {}
	 */
	renderView : function(details, syscfg) {
		var me = this, items = [];
		Ext.each(details, function(rec) {
			if (!rec.fieldid) {
				var panel = FormUtils.getPanel(me.config.moduleinfo, objectfield, rec, me.config.operatetype, syscfg);
				if (!Ext.isEmpty(rec.details)) {
					panel.add(me.renderView(rec.details, syscfg));
				}
				if (rec.subobjectid || rec.xtype == 'attachment')
					me.subobjects.push(panel);
				if (panel)
					items.push(panel);
			} else {
				var moduleinfo = me.config.moduleinfo;
				if (rec.fieldahead) {
					moduleinfo = modules.getModuleInfo(rec.fieldahead);
				}
				var objectfield = moduleinfo.getFieldDefine(rec.fieldid);
				var field = FormUtils.getField(me.config.moduleinfo, objectfield, rec, me.config.operatetype, syscfg);
				if (field)
					items.push(field);
			}
		});
		return items;
	},

	/**
	 * 第一个组件获取焦点
	 */
	focusFirstField : function() {
		this.getForm().getFields().each(function(field) {
			if (field.xtype.indexOf('hidden') == -1 && !field.readOnly) {
				field.focus();
				return false;
			}
		});
	},

	/**
	 * 获取提交后返回的错误信息
	 * 
	 * @param {}
	 *          result
	 * @return {}
	 */
	showErrorMsg : function(result) {
		var errorMessage = '', form = this.getForm();
		if (result.message)
			errorMessage = result.message + '</br>';
		// if (result.errorcode == 500) {
		if (result.data) {
			var errors = result.data;
			for ( var fieldname in errors) {
				var fs = fieldname;
				var field = form.findField(fieldname);
				if (field) {
					if (field.fieldLabel_) {
						fs = field.fieldLabel_;
					} else {
						var fd = this.config.moduleinfo.getFieldDefineWithName(fieldname);
						if (fd)
							fs = fd.fieldtitle;
					}
				}
				errorMessage = errorMessage + (fs && fs != 'null' ? fs + " : " : '') + errors[fieldname] + '</br>';
			}
			form.markInvalid(result.data);
			EU.showMsg({
				title : '提示消息',
				msg : errorMessage,
				icon : Ext.Msg.ERROR
			});
		} else {
			EU.showMsg({
				title : '提示消息',
				msg : result.message,
				icon : Ext.Msg.ERROR
			});
		}
	}
});
