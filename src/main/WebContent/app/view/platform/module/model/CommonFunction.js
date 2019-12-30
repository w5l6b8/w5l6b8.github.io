/**
 * model 的某些可操作的判断，也可以加入用户自定义的函数在此处
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


Ext.define('app.view.platform.module.model.CommonFunction', {
	extend : 'Ext.Mixin',

	/**
	 * 返回一个名称是name的字段的定义，这可能是directionary 或者 manytoone 的字段，根据id,name的字段名来找
	 * @param {} name
	 * @return {}
	 */
	getField : function(name){
		var result = null;
		Ext.each(this.fields, function(field){
			if (field.name == name) {
				result = field;
				return false;
			}
		})
		return result;
	},

	getTitleTpl : function(){
		if (!this.titleTemplate) {
			if (this.titletpl) this.titleTemplate = new Ext.Template(this.titletpl);
			else this.titleTemplate = new Ext.Template('{' + this.namefield + '}');
		}
		return this.titleTemplate.apply(this.getData());
	},

	// 取得当前modal的主键值
	getIdValue : function(){
		return this.get(this.idProperty);
	},

	// 设置当前modal的主键值
	setIdValue : function(v){
		return this.set(this.idProperty,v);
	},

	// 取得当前modal记录的名字字段值
	getNameValue : function(){
		if (this.namefield) return this.get(this.namefield);
		else return null;
	},

	// 此条记录是否可以修改
	canEdit : function(){
		//if (this.module.tf_hasAuditing && this.get('tf_auditinged')) return false;
		//if (this.module.tf_hasApprove && this.get('tf_shNowCount') > 0) return false;

		// 如果设置了需要去后台判断是否允许编译的加在这里

		return true;
	},

	canInsert : function(){

		// 如果设置了需要去后台判断是否允许编译的加在这里

	},

	// 此条记录是否可以进行操作
	canOperate : function(){
		if (this.module.tf_hasAuditing && this.get('tf_auditinged')) return false;
		return true;
	},

	// 此条记录是否可以删除
	canDelete : function(){
		if (this.module.tf_hasAuditing && this.get('tf_auditinged')) return {
			canDelete : false,
			message : '『' + this.getTitleTpl() + '』已进行过审核，不允许进行删除操作!'
		};
		if (this.module.tf_hasApprove && this.get('tf_shNowCount') > 0) return {
			canDelete : false,
			message : '『' + this.getTitleTpl() + '』正在审批或已经审批完成,不允许进行删除操作!'
		};

		// 如果设置了需要去后台判断是否允许删除的加在这里

		return true;
	}

});
