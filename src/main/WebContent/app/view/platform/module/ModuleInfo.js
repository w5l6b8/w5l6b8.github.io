/**
 * 模块的定义类，里面包括了所有的定义，以及各种函数
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


Ext.define('app.view.platform.module.ModuleInfo', {

	mixins : [ 'app.view.platform.module.moduleInfo.GridScheme', 'app.view.platform.module.moduleInfo.FilterScheme',
			'app.view.platform.module.moduleInfo.NavigateScheme', 'app.view.platform.module.moduleInfo.Associate',
			'app.view.platform.module.moduleInfo.SortScheme' ],

	config : {
		displayWindow : null,
		editWindow : null

	},

	constructor : function(moduleinfo) {
		var me = this;
		Ext.apply(me, moduleinfo);
		var obj = me.fDataobject;
		if (obj.iconurl) {
			obj.iconcls = obj.objectname + "_iconcss";
			Ext.util.CSS.createStyleSheet('.' + obj.iconcls + '{background:url(' + obj.iconurl
					+ ') no-repeat left center;padding-left:16px;background-size:16px 16px;}');
			delete obj.iconurl;
		}
		if (obj.iconfile) {
			obj.iconcls = obj.objectname + "_iconcss";
			Ext.util.CSS.createStyleSheet('.' + obj.iconcls + '{background:url(data:image/png;base64,' + obj.iconfile
					+ ') no-repeat left center;padding-left:16px;background-size:16px 16px;}');
			delete obj.iconfile;
		}

		// 生成此模块的数据model

		if (me.parentKey || me.istreemodel) {
			me.model = app.view.platform.module.model.GridModelFactory.getModelByModule(this);
		} else {
			me.model = app.view.platform.module.model.GridModelFactory.getModelByModule(this);
		}
	},

	// 取得用于展开分组的所有字段
	getExpandGroupFields : function() {
	},

	hasSummaryField : function() {
		var me = this, has = false;
		Ext.each(me.fDataobject.fDataobjectfields, function(field) {
			if (field.allowsummary) {
				has = true;
				return false;
			}
		})
		return has;
	},

	showDisplayWindow : function(target) {
		var me = this;
		if (target instanceof Ext.grid.Panel) {
			me.getDisplayWindow().show(target);
		} else {
			me.getDisplayWindow().show(target); // 可能是传进去一个主键
		}
	},

	getDisplayWindow : function() {
		var me = this;
		if (!me.displayWindow) {
			var cfg = {
				config : {
					modulecode : me.moduleid,
					operatetype : 'display'
				}
			}
			me.displayWindow = Ext.create("app.view.platform.module.form.FormWindow", cfg);
		}
		return me.displayWindow;
	},

	showNewWindow : function(target, copyed) {
		var me = this;
		if (target instanceof Ext.panel.Table) {
			me.getNewWindow().show(target, copyed);
		} else {
			me.getNewWindow().show(target, copyed); // 可能是传进去一个主键
		}
	},

	getNewWindow : function() {
		var me = this;
		if (!me.newWindow) {
			var cfg = {
				config : {
					modulecode : me.moduleid,
					operatetype : 'new'
				}
			}
			me.newWindow = Ext.create("app.view.platform.module.form.FormWindow", cfg);
		}
		return me.newWindow;
	},

	showEditWindow : function(target) {
		var me = this;
		if (target instanceof Ext.grid.Panel) {
			me.getEditWindow().show(target);
		} else {
			me.getEditWindow().show(target); // 可能是传进去一个主键
		}
	},

	getEditWindow : function() {
		var me = this;
		if (!me.editWindow) {
			var cfg = {
				config : {
					modulecode : me.moduleid,
					operatetype : 'edit'
				}
			}
			me.editWindow = Ext.create("app.view.platform.module.form.FormWindow", cfg);
		}
		return me.editWindow;
	},

	// 生成一个modulePanel,如果已经生成了，那么就返回原实例
	getModulePanel : function(itemId) {

		// if (!this.canBrowseThisModule()) return null;
		var me = this;
		if (!me.modulePanel) {
			me.modulePanel = Ext.widget('panel', {
				canAutoOpen : true,
				type : 'dataobject',
				objectid : me.fDataobject.objectid,
				moduleName : me.fDataobject.objectname,
				itemId : itemId,
				title : me.fDataobject.shortname || me.fDataobject.title,
				// icon : me.fDataobject.iconurl,
				iconCls : me.fDataobject.iconcls,
				closable : true,
				layout : 'border',
				items : [ {
					xtype : 'modulepanel',
					region : 'center',
					gridType : 'normal',
					moduleId : me.fDataobject.objectname
				} ]
			});
		}
		return me.modulePanel;
	},

	getNewPanelWithParentFilter : function(parentModuleName, fieldahead, pid, ptitle, param) {
		var pm = modules.getModuleInfo(parentModuleName);
		var pf = {
			moduleName : parentModuleName,
			fieldahead : fieldahead,
			fieldName : pm.fDataobject.primarykey,
			fieldtitle : pm.fDataobject.title,
			operator : '=',
			fieldvalue : pid,
			text : ptitle,
			isCodeLevel : pm.codeLevel
		};
		// 如果是附件模块，那么因为权限不同，要重新生成一个，如果是不同的模块，那么就重新生成『主表:name21』
		var title = this.fDataobject.title + '『' + ptitle + '』';

		return Ext.widget('panel', {
			moduleName : this.fDataobject.objectname,
			itemId : 'Tab_' + this.fDataobject.objectname + "_witharent",
			title : title,
			closable : true,
			// icon : this.iconURL,
			layout : 'fit',
			items : [ Ext.create('app.view.platform.module.Module', {
				moduleId : this.moduleid,
				param : param,
				parentFilter : pf
			}) ]
		});

	},

	getPanelWithParentFilter : function(parentModuleName, fieldahead, pid, ptitle, param) {

		// if (!this.canBrowseThisModule()) return null;
		if (!this.modulePanelWithParent) {
			this.modulePanelWithParent = this.getNewPanelWithParentFilter(parentModuleName, fieldahead, pid, ptitle, param);
		} else {
			this.modulePanelWithParent.down('modulepanel').fireEvent('parentfilterchange', {
				fieldvalue : pid,
				text : ptitle
			});
			this.modulePanelWithParent.setTitle(this.fDataobject.title + '『' + ptitle + '』');
		}
		return this.modulePanelWithParent;
	},

	modulecode : undefined, // 模块代码，全系统唯一
	moduleid : undefined, // 模块id号，uuid
	modulename : undefined, // 模块名称描述
	moduletype : undefined, // 模块类型

	fDataobject : undefined, // 表模块数据结构
	fModuletabellimits : undefined, // 关联模块数据定义

	/**
	 * @param {}
	 *          columnfield 包括附加字段的columnfield或者formfield或者其他类型, columnfield中需要有：
	 *          additionFieldname:"UProvince.name" additionObjectname:"UProvince"
	 *          columnid:"8a808182587fb88601587fc15c440002"
	 *          defaulttitle:"省份--name" fieldahead:"UProvince"
	 *          fieldid:"402881ee581ed4f801581ed5ac410004"
	 */
	addParentAdditionField : function(columnfield) {
		var me = this;
		var additionModuleInfo = modules.getModuleInfo(columnfield.additionObjectname);
		var additionField = additionModuleInfo.getFieldDefine(columnfield.fieldid);
		var field = {
			fieldname : columnfield.additionFieldname,
			fieldtitle : columnfield.defaulttitle,
			fieldid : columnfield.fieldid
		};
		if (additionField.isManyToOne)
			field.manyToOneInfo = {};
		Ext.applyIf(field, additionField);
		if (additionField.isManyToOne) {
			Ext.applyIf(field.manyToOneInfo, additionField.manyToOneInfo);
			field.manyToOneInfo.keyField = field.fieldname + '.' + field.manyToOneInfo.keyOrginalField.fieldname;
			field.manyToOneInfo.nameField = field.fieldname + '.' + field.manyToOneInfo.nameOrginalField.fieldname;
		}

		if (!me.getFieldDefineWithName(field.fieldname)) {
			me.fDataobject.fDataobjectfields.push(field);
			// 在model中加入字段
			var modelFields = app.view.platform.module.model.GridModelFactory.getField(field);
			for ( var i in modelFields)
				me.model.addFields([ modelFields[i] ])
		}
		return field;
	},

	addChildAdditionField : function(columnfield) {
		var me = this;
		var additionModuleInfo = modules.getModuleInfo(columnfield.additionObjectname);
		var additionField = additionModuleInfo.getFieldDefine(columnfield.fieldid);
		var field = {
			fieldname : columnfield.additionFieldname,
			fieldtitle : columnfield.defaulttitle,
			fieldid : columnfield.fieldid,
			aggregate : columnfield.aggregate
		}
		Ext.applyIf(field, additionField);
		if (field.aggregate == 'count') {
			field.fieldtype = 'Integer';
			field.ismonetary = false;
		}
		if (!me.getFieldDefineWithName(field.fieldname)) {
			me.fDataobject.fDataobjectfields.push(field);
			var modelFields = app.view.platform.module.model.GridModelFactory.getField(field);
			for ( var i in modelFields)
				me.model.addFields([ modelFields[i] ])
		}
		return field;
	},

	getFormScheme : function(schemeid) {
		var fovFormschemes = this.fDataobject.fovFormschemes;
		var result;
		Ext.each(fovFormschemes, function(rec) {
			if (rec.formschemeid == schemeid) {
				result = rec;
			}
		});
		return result;
	},

	getFormSchemeFromType : function(type) {
		var fovFormschemes = this.fDataobject.fovFormschemes;
		if ((!fovFormschemes) || fovFormschemes.length == 0)
			return null;
		var result;
		Ext.each(fovFormschemes, function(rec) {
			if (rec.operatetype == type) {
				result = rec;
			}
		});
		if (result)
			result = fovFormschemes[0];
		return result;
	},

	getAllFormSchemeFromType : function(type) {
		var fovFormschemes = this.fDataobject.fovFormschemes;
		if ((!fovFormschemes) || fovFormschemes.length == 0)
			return null;
		var result = [], temp = undefined;
		Ext.each(fovFormschemes, function(rec) {
			if (!temp && rec.operatetype != 'approve') {
				temp = rec;
			}
			if (rec.operatetype == type) {
				result.push(rec);
			}
		});
		if (result.length == 0 && temp)
			result.push(temp);
		return result.length == 0 ? null : result;
	},

	/**
	 * 根据字段ＩＤ号取得字段的定义,如果没找到返回null
	 */
	getFieldDefine : function(fieldId) {
		for ( var i in this.fDataobject.fDataobjectfields) {
			if (this.fDataobject.fDataobjectfields[i].fieldid == fieldId)
				return this.fDataobject.fDataobjectfields[i];
		}
		return null;
	},

	/**
	 * 根据字段fieldname号取得字段的定义,如果没找到返回null
	 */
	getFieldDefineWithName : function(fieldName) {
		for ( var i in this.fDataobject.fDataobjectfields) {
			if (this.fDataobject.fDataobjectfields[i].fieldname == fieldName)
				return this.fDataobject.fDataobjectfields[i];
		}
		return null;
	}

});
