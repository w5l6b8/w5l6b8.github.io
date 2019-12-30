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

Ext.define('app.view.platform.module.form.panel.ApproveForm', {
	extend : 'app.view.platform.module.form.panel.BaseForm',
	alternateClassName : 'approveForm',
	alias : 'widget.approveform',
	initComponent : function(){
		var me = this;
		me.config.operatetype = 'approve';
		me.config.formtypetext = '业务表单'
		me.callParent(arguments);
	}
})