Ext.define('app.view.platform.module.form.BaseWindow', {
	extend : 'Ext.window.Window',
	xtype : "baseWindow",
	requires : [
	    'app.view.platform.module.form.widget.SchemeSegmented',
	    'app.view.platform.module.form.panel.BaseForm',
	    'app.view.platform.module.form.panel.NewForm',
	    'app.view.platform.module.form.panel.EditForm',
	    'app.view.platform.module.form.panel.DisplayForm',
	    'app.view.platform.module.form.panel.ApproveForm'],
	maximizable : true,
	resizable : false,
	closeAction : 'close',
	bodyPadding : '1px 1px',
	shadowOffset : 30,
	layout : 'fit',

	config : {
		moduleinfo : undefined,
		/** 必要参数 模块名称 */
		modulecode : undefined,
		/** 必要参数 当前form的操作类型 display,new,edit */
		operatetype : undefined,
		/** 变量 当前表对象 */
		fDataobject : undefined,
		/** 变量 当前类型下全部的表单方案 */
		formSchemes : [],
		/** 变量 展示的表单方案 */
		formScheme : undefined
	},
	/** 变量 业务表单对象 */
	formPanel : undefined,

	/**
	 * 设置一些基本的window属性
	 * @param {} config
	 */
	setBasicConfig : function(config){
		var me = this, formScheme = config.formScheme, fDataobject = config.fDataobject;
		var w = me.width = formScheme.width;
		var h = me.height = formScheme.height;
		var maxwidth = PU.getWidth();
		var maxheight = PU.getHeight();
		if (w == -1) me.width = PU.getWidth();
		if (h == -1) me.height = PU.getHeight();
		if (maxwidth < this.width) this.width = maxwidth;
		if (maxheight < this.height) this.height = maxheight;
		if (!me.height || me.height == 0) delete me.height;
		this.icon = fDataobject.iconurl;
		if (fDataobject.iconcls) {
			this.iconCls = fDataobject.iconcls;
		}
		this.modulecode = config.modulecode;
		this.tools = [{
			xtype : 'formschemesegmented'
		}, {
			type : 'gear'
		}, {
			type : 'print',
			tooltip : '打印当前窗口的内容'
		}, {
			type : 'help'
		}, {
			type : 'collapse',
			tooltip : '当前记录导出至Excel'
		}];
		if (fDataobject.hasattachment) {
			this.tools.push({
				type : 'search',
				tooltip : '显示附件'
			})
		}
	},

	/**
	 * 切换Form方案
	 * @param {} formScheme 方案ID或方案对象
	 */
	formSchemeChange : function(formScheme){
		if (Ext.isString(formScheme)) formScheme = this.config.moduleinfo.getFormScheme(formScheme);
		this.config.formScheme = formScheme;
		var w = formScheme.width;
		var h = formScheme.height;
		var maxwidth = PU.getWidth();
		var maxheight = PU.getHeight();
		if (w == -1) w = PU.getWidth();
		if (h == -1) h = PU.getHeight();
		if (maxwidth < w) w = maxwidth;
		if (maxheight < h) h = maxheight;
		this.setWidth(w);
		this.setHeight(h == 0 || h == null ? null : h);
		this.formPanel = this.createForm(this.config);
		Ext.suspendLayouts();
		this.removeAll();
		this.add(this.formPanel);
		Ext.resumeLayouts(true);
		this.show()
	},

	/**
	 * 创建Form表单对象
	 * @param {} config
	 * @return {}
	 */
	createForm : function(config){
		var operatetype = config.operatetype || 'new', view = undefined, customform = config.formScheme.customform;
		if (operatetype == 'display') {
			view = 'app.view.platform.module.form.panel.DisplayForm';
		} else if (operatetype == 'new') {
			this.modal = true;
			view = 'app.view.platform.module.form.panel.NewForm';
		} else if (operatetype == 'edit') {
			this.modal = true;
			view = 'app.view.platform.module.form.panel.EditForm';
		} else if (operatetype == 'approve') {
			this.modal = true;
			view = 'app.view.platform.module.form.panel.ApproveForm';
		}
		if (customform) {
			var newPanel = null;
			try {
				newPanel = Ext.create(customform, config);
			} catch (ex) {
				Ext.Logger.error(ex);
			}
			if (newPanel && (newPanel instanceof newForm && operatetype == 'new') || (newPanel instanceof editForm && operatetype == 'edit') || (newPanel instanceof displayForm && operatetype == 'display') || (newPanel instanceof approveForm && operatetype == 'approve')) {
				this.formPanel = newPanel;
			} else {
				this.formPanel = Ext.create(view, config);
			}
		} else {
			this.formPanel = Ext.create(view, config);
		}
		return this.formPanel;
	},

	/**
	 * 显示window窗口
	 * @param {} obj
	 */
	show : function(obj,copyed){
		if (this.config.formScheme) {
		}
		if (this.formPanel) {
			this.data = obj = (obj || this.data);
			this.formPanel.setReadOnly(false);
			this.formPanel.beforeShow();
			this.superclass.superclass.show.call(this);
			this.formPanel.initData(obj,copyed);
			this.formPanel.afterShow();
		} else {
			this.callParent(arguments);
		}
	},

	/**
	 * 关闭window窗口
	 */
	close : function(){
		if (this.formPanel) {
			this.formPanel.getForm().reset();;
			this.formPanel.getForm().clearInvalid();
		}
		this.callParent(arguments);
	}
});
