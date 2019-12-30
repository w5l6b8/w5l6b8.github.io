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


Ext.define('app.view.platform.module.ModuleInfoBase', {

	requires_______ : ['app.module.window.BaseWindow', 'app.module.window.AttachmentWindow', 'app.module.ModulePanel',
	    'app.view.treeModule.ModuleTreePanel'],

	modulePanel : null,
	modulePanelWithParent : null,
	modulePanelWithFilter : null,
	modulePanelToApprove : null,
	modulePanelToAuditing : null,
	modulePanelToPayout : null,

	newModulePanelWithParent : null,

	parentModuleName : null,

	tf_moduleId : null,
	tf_moduleName : null,
	tf_title : null,

	displayWindow : null, // 用于显示一条记录的一个窗口，可以有二种方式对其控制，
	newWindow : null,
	editWindow : null,
	model : null,
	auditingWindow : null,
	approveWindow : null,
	paymentWindow : null,

	// 如果grid中的记录变掉了，那么如果有打开的窗口，就将当前的记录更新
	updateActiveForm : function(){
		// 如果修改的窗口正在显示，则更新
		if (this.editWindow && !this.editWindow.isHidden()) this.editWindow.form.initForm();
		if (this.auditingWindow && !this.auditingWindow.isHidden()) this.auditingWindow.form.initForm();
		if (this.approveWindow && !this.approveWindow.isHidden()) this.approveWindow.form.initForm();

	},

	showRecord : function(id){
		var window = this.getDisplayWindow();
		window.show();
		window.form.setRecordId(id);
	},

	getRecordWithId : function(id){
		var param = {};
		param[this.module.model.idProperty] = id;
		var dataRecord = Ext.create(this.module.model, param);
		dataRecord.load({
			  async : false,
			  success : function(record, operation, success){
				  me.setData(dataRecord);
			  }
		  });

	},

	getDisplayWindow : function(){
		if (!this.displayWindow) {
			this.displayWindow = Ext.widget('basewindow', {
				  module : this,
				  formtype : 'display'
			  });
		}
		return this.displayWindow;
	},

	// temporary 表示不是在
	// 一个临时新增窗口，比如在模块中直接新增附件,临时的窗口是全局modal的，而其他的都是 modal
	// modulepanel
	getNewWindow : function(temporary){
		if (temporary) {
			if (!this.newWindow) this.newWindow = Ext.widget('basewindow', {
				module : this,
				formtype : 'new'
				// closeAction : 'destroy'
			}
			);
			return this.newWindow;
		} else return Ext.widget('basewindow', {
			  module : this,
			  formtype : 'new'
		  });

	},

	/**
	 * 每一个modulepanel有自己的editwindow
	 */
	getEditWindow : function(temporary){
		if (temporary) {
			if (!this.editWindow) this.editWindow = Ext.widget('basewindow', {
				module : this,
				formtype : 'edit'
				// closeAction : 'destroy'
			}
			);
			return this.editWindow;
		} else return Ext.widget('basewindow', {
			  module : this,
			  formtype : 'edit'
		  });
	},

	getAuditingWindow : function(){
		if (!this.auditingWindow) {
			this.auditingWindow = Ext.widget('basewindow', {
				  module : this,
				  formtype : 'auditing'
			  });
		}
		return this.auditingWindow;
	},

	getApproveWindow : function(){
		if (!this.approveWindow) {
			this.approveWindow = Ext.widget('basewindow', {
				  module : this,
				  formtype : 'approve'
			  });
		}
		return this.approveWindow;
	},

	getPaymentWindow : function(){
		// if (!this.paymentWindow) {
		this.paymentWindow = Ext.widget('basewindow', {
			  module : this,
			  formtype : 'payment'
		  });
		// }
		return this.paymentWindow;
	},

	getPanelWithParentModule : function(itemId, parentModuleName, pid, ptitle, param){

		if (!this.canBrowseThisModule()) return null;

		var pm = modules.getModuleInfo(parentModuleName);
		var pf = {
			moduleId : pm.tf_moduleId,
			moduleName : parentModuleName,
			fieldahead : param.fieldahead,
			fieldName : pm.tf_primaryKey,
			fieldtitle : pm.tf_title,
			operator : pid == null || pid == 'null' || pid == '_null_' ? ' is null' : '=',
			fieldvalue : pid == null ? 'null' : pid,
			text : ptitle,
			isCodeLevel : pm.codeLevel
		};
		// 如果是附件模块，那么因为权限不同，要重新生成一个，如果是不同的模块，那么就重新生成

		if (this.tf_moduleName == '_Attachment' && this.modulePanelWithParent) {
			if (this.parentModuleName != parentModuleName) {
				Ext.destroy(this.modulePanelWithParent);
				this.modulePanelWithParent = null;
			}
		}
		this.parentModuleName = parentModuleName;

		var title = this.tf_title + '[' + ptitle + ']';
		if (!this.modulePanelWithParent) {
			this.modulePanelWithParent = Ext.widget('panel', {
				  moduleName : this.tf_moduleName,
				  parentModuleName : parentModuleName,
				  itemId : itemId,
				  title : title,
				  closable : true,
				  icon : this.iconURL,
				  layout : 'border',
				  items : [Ext.create('app.module.ModulePanel', {
					    region : 'center',
					    module : this,
					    param : param,
					    parentFilter : pf
				    })]
			  });
		} else {
			// this.modulePanelWithParent.setTitle(title);
			this.modulePanelWithParent.down('modulepanel').setParentFilter(pf);
			// 如果没有重新建立，那么把param 重新改一下，里面的 addDefault 会改变
			this.modulePanelWithParent.down('modulepanel').param = param;
		}

		// 如果是附件，那么判断是显示第一个tab还是第2个
		if (this.tf_moduleName == '_Attachment') if (param && param.showAdditionView == true) this.modulePanelWithParent.down('attachmenttabpanel').setActiveTab(1);
		else this.modulePanelWithParent.down('attachmenttabpanel').setActiveTab(0);

		return this.modulePanelWithParent;
	},

	// 在form菜单中要加入子模块的grid 时，产生的一个
	getNewPanelWithParentModuleForm : function(parentModuleName, pid, ptitle, param){
		if (!this.canBrowseThisModule()) return null;
		var pm = modules.getModuleInfo(parentModuleName);
		var pf = {
			// moduleId : pm.tf_moduleId,
			moduleName : parentModuleName,
			fieldahead : param.fieldahead,

			// tableAsName : pm.tableAsName,
			fieldName : pm.tf_primaryKey,
			fieldtitle : pm.tf_title,
			fieldvalue : pid == null ? 'null' : pid,
			operator : pid == null || pid == 'null' || pid == '_null_' ? ' is null' : '=',
			text : ptitle,
			isCodeLevel : pm.codeLevel
		};
		return Ext.create('app.module.ModulePanel', {
			  region : 'center',
			  module : this,
			  param : param,
			  parentFilter : pf
		  });
	},

	getNewPanelWithParentModule : function(itemId, parentModuleName, pid, ptitle, param){

		if (!this.canBrowseThisModule()) return null;

		var pm = modules.getModuleInfo(parentModuleName);
		var pf = {

			moduleName : parentModuleName,
			fieldahead : param.fieldahead,
			fieldName : pm.tf_primaryKey,
			fieldtitle : pm.tf_title,
			operator : pid == null || pid == 'null' || pid == '_null_' ? ' is null' : '=',
			fieldvalue : pid == null ? 'null' : pid,
			text : ptitle,
			isCodeLevel : pm.codeLevel
		};

		var title = this.tf_title + '[' + ptitle + ']';

		this.newModulePanelWithParent = Ext.widget('panel', {
			  moduleName : this.tf_moduleName,
			  parentModuleName : parentModuleName,
			  itemId : itemId,
			  title : title,
			  closable : true,
			  icon : this.iconURL,
			  layout : 'border',
			  items : [Ext.create('app.module.ModulePanel', {
				    region : 'center',
				    module : this,
				    param : param,
				    parentFilter : pf
			    })]
		  });

		return this.newModulePanelWithParent;
	},

	// 根据一个父筛选，来创建grid,没有考虑attachment的情况
	getPanelWithParentFilter : function(itemId, parentFilter){
		if (!this.canBrowseThisModule()) return null;
		var title = this.tf_title + '[' + parentFilter.ptitle + ']';
		if (!this.modulePanelWithFilter) {
			this.modulePanelWithFilter = Ext.widget('panel', {
				  moduleName : this.tf_moduleName,
				  itemId : itemId,
				  title : title,
				  closable : true,
				  icon : this.iconURL,
				  layout : 'border',
				  items : [Ext.create('app.module.ModulePanel', {
					    region : 'center',
					    module : this,
					    parentFilter : parentFilter
				    })]
			  });
		} else {
			this.modulePanelWithFilter.down('modulepanel').setParentFilter(parentFilter);
		}
		return this.modulePanelWithFilter;
	},

	// 取得可以支付的模块的grid
	getModulePanelToPayout : function(itemId){
		if (!this.canBrowseThisModule()) return null;
		var pf = {
			moduleId : this.tf_moduleId,
			moduleName : this.tf_moduleName,
			tableAsName : this.tableAsName,
			primarykey : 'tf_payoutStatus',
			fieldtitle : '支付',
			equalsValue : '可支付',
			equalsMethod : null,
			text : '我可以支付的',
			isCodeLevel : false
		};

		if (!this.modulePanelToPayout) {
			this.modulePanelToPayout = Ext.widget('panel', {
				  moduleName : this.tf_moduleName,
				  itemId : itemId,
				  title : this.tf_title,
				  icon : this.iconURL,
				  closable : true,
				  layout : 'border',
				  items : [Ext.create('app.module.ModulePanel', {
					    region : 'center',
					    gridType : 'normal',
					    module : this,
					    parentFilter : pf

				    })]
			  });
		}
		return this.modulePanelToPayout;
	},

	// 取得可以审核的模块的grid
	getModulePanelToAuditing : function(itemId){
		if (!this.canBrowseThisModule()) return null;
		var pf = {
			moduleId : this.tf_moduleId,
			moduleName : this.tf_moduleName,
			tableAsName : this.tableAsName,
			primarykey : 'tf_auditinged',
			fieldtitle : '审核',
			equalsValue : '0',
			equalsMethod : null,
			text : '我可以审核的',
			isCodeLevel : false
		};

		if (!this.modulePanelToAuditing) {
			this.modulePanelToAuditing = Ext.widget('panel', {
				  moduleName : this.tf_moduleName,
				  itemId : itemId,
				  title : this.tf_title,
				  icon : this.iconURL,
				  closable : true,
				  layout : 'border',
				  items : [Ext.create('app.module.ModulePanel', {
					    region : 'center',
					    gridType : 'auditing',
					    module : this,
					    parentFilter : pf

				    })]
			  });
		}
		return this.modulePanelToAuditing;
	},

	// 取得可以审批的模块的grid
	getModulePanelToApprove : function(itemId){
		if (!this.canBrowseThisModule()) return null;
		var pf = {
			moduleId : this.tf_moduleId,
			moduleName : this.tf_moduleName,
			tableAsName : this.tableAsName,
			primarykey : 'approvetype',
			fieldtitle : '审批类型',
			equalsValue : '我可以审批的',
			equalsMethod : null,
			text : '我可以审批的',
			isCodeLevel : false
		};

		if (!this.modulePanelToApprove) {
			this.modulePanelToApprove = Ext.widget('panel', {
				  moduleName : this.tf_moduleName,
				  itemId : itemId,
				  title : this.tf_title,
				  icon : this.iconURL,
				  closable : true,
				  layout : 'border',
				  items : [Ext.create('app.module.ModulePanel', {
					    region : 'center',
					    gridType : 'approve',
					    module : this,
					    parentFilter : pf

				    })]
			  });
		}
		return this.modulePanelToApprove;
	},

	// 生成一个modulePanel,如果已经生成了，那么就返回原实例
	getModulePanel : function(itemId){
		if (!this.canBrowseThisModule()) return null;

		if (!this.modulePanel) {
			if (this.tf_parentKey || this.tf_isTreeModel) {
				this.modulePanel = Ext.widget('panel', {
					  moduleName : this.tf_moduleName,
					  itemId : itemId,
					  title : this.tf_title,
					  icon : this.iconURL,
					  closable : true,
					  layout : 'border',
					  items : [Ext.create('app.view.treeModule.ModuleTreePanel', {
						    region : 'center',
						    gridType : 'normal',
						    module : this
					    })]
				  });
			} else {
				this.modulePanel = Ext.widget('panel', {
					  moduleName : this.tf_moduleName,
					  itemId : itemId,
					  title : this.tf_title,
					  icon : this.iconURL,
					  closable : true,
					  layout : 'border',
					  items : [Ext.create('app.module.ModulePanel', {
						    region : 'center',
						    gridType : 'normal',
						    module : this
					    })]
				  });
			}
		}
		return this.modulePanel;
	},

	// 是否此模块有序号字段 tf_order ，如果有的话，可以拖放改变记录顺序
	// canOrderRecord : function() {
	// for (var i in this.tf_fields)
	// if (this.tf_fields[i].tf_fieldName == 'tf_orderId')
	// return true;
	// return false;
	// },

	canBrowseThisModule : function(){
		if (!(this.tf_userRole && this.tf_userRole.tf_allowBrowse)) {
			EU.toastWarn('当前操作员没有查看模块『' + this.tf_title + '』的权限');
			return false;
		} else return true;
	},

	/**
	 * 根据字段ＩＤ号取得字段的定义,如果没找到返回null
	 */
	getFieldDefine : function(fieldId){
		for (var i in this.tf_fields) {
			if (this.tf_fields[i].tf_fieldId == fieldId) return this.tf_fields[i];
		}
		return null;
	},

	/**
	 * 根据字段fieldname号取得字段的定义,如果没找到返回null
	 */
	getFieldDefineWithName : function(fieldName){
		for (var i in this.tf_fields) {
			if (this.tf_fields[i].tf_fieldName == fieldName) return this.tf_fields[i];
		}
		return null;
	},

	/**
	 * 根据字段ＩＤ号取得附加字段,如果没找到返回null
	 */
	getAdditionFieldDefine : function(fieldId){
		for (var i in this.tf_moduleAdditionFields) {
			if (this.tf_moduleAdditionFields[i].tf_fieldId == fieldId) {
				var fd = this.tf_moduleAdditionFields[i];
				if (!fd.originFieldDefine) {
					var submodule = modules.getModuleInfo(fd.targetModuleName);
					if (submodule) var originfd = submodule.getFieldDefine(fd.tf_fieldId);
					if (originfd) {
						fd.originFieldDefine = originfd;
						fd.ismonetary = originfd.ismonetary;
						fd.tf_unittext = originfd.tf_unittext;
						fd.s = originfd.s;
					}
				}
				return fd;
			}
		}
		return null;
	},

	/**
	 * 根据字段ＩＤ号取得gridScheme的定义,如果没找到返回null
	 */

	getGridScheme : function(schemeId){
		for (var i in this.tf_gridSchemes) {
			if (this.tf_gridSchemes[i].tf_schemeOrder == schemeId) return this.tf_gridSchemes[i];
		}
		return null;
	}

}
);
