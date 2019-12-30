/**
 * manytoone 的只读时候使用
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



Ext.define('app.view.platform.module.form.field.ManyToOneText', {
	extend : 'Ext.form.field.Text',
	alias : 'widget.manytoonetext',

	config : {
		fieldtype : null,
		idfieldname : null
	},
	
	triggers : {
		comment : {
			cls : 'x-fa fa-comment-o',
			weight : 1,
			hideOnReadOnly : false,
			handlerOnReadOnly : true,
			handler : function(){
				var me = this;
				if (!me.getValue()) EU.toastWarn('模块还没有选择值！');
				else modules.getModuleInfo(me.fieldtype).showDisplayWindow(me.getHiddenValue());
			}
		}
	},

	onRender : function(ct, position){
		var me = this;
		me.hiddenField = new Ext.form.field.Hidden({
			name : me.idfieldname,
			readOnly:true
		});
		me.up('form').add(me.hiddenField);
		me.superclass.onRender.call(this, ct, position);
		this.setEditable(false);
	},

	initComponent :function(){
		delete this.maxLength;
		this.callParent();
	},
	
	getHiddenValue : function(){
		return this.hiddenField.getValue();
	},

	setValue : function(value){
		this.superclass.setValue.call(this, value);
		if (Ext.isEmpty(value) && this.hiddenField) {
			this.hiddenField.setValue("");
		}
	}
})