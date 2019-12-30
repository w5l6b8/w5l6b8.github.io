Ext.define('app.view.platform.module.form.FormWindow', {
	extend : 'app.view.platform.module.form.BaseWindow',
	alias : 'widget.formwindow',
	
	initComponent : function(){
		var me = this;
		var moduleinfo = this.config.moduleinfo = modules.getModuleInfo(me.config.modulecode);
		var operatetype = me.config.operatetype || 'new';
		var fDataobject = me.config.fDataobject = moduleinfo.fDataobject;
		var formSchemes = me.config.formSchemes = moduleinfo.getAllFormSchemeFromType(operatetype);
		if (!formSchemes) {
			Ext.apply(this, {width : 400,height : 300,title : "没有窗口信息",html : "没有表单方案信息。"});
			this.callParent(arguments);
			return;
		}
		me.config.formScheme = formSchemes[0];
		this.setBasicConfig(this.config);
		this.items = this.createForm(this.config);
		this.callParent(arguments);
	}
});