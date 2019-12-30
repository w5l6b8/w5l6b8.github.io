/**
 * manytoone选择combobx 10 只能下拉选择 20 可以录入关键字选择 30 可以直接录入编码，或者用关键字进行选择，如 01 男， 02
 * 女，可以直接录入 01,就会选择男，可以用于快速录入 在没有录入选择值的时候，空格键会自动展开录入项， 04,05 和 02,03相似，
 * 只是是remote
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



Ext.define('app.view.platform.module.form.field.ManyToOneComboBox', {
	extend : 'Ext.form.field.ComboBox',
	alias : 'widget.manytoonecombobox',
	minChars : 2,
	displayField : 'text',
	valueField : 'value',
	queryMode : 'local',
	triggerAction : 'all',
	queryParam : 'query',
	editable : true,
	anyMatch : true, // 录入的关键字可以是在任何位置
	forceSelection : true, // 必须是下拉菜单里有的
	triggers : {
		comment : {
			cls : 'x-fa fa-comment-o',
			weight : 1,
			handler : function(){
				var me = this;
				if (!me.getValue()) EU.toastWarn('模块还没有选择值！');
				else modules.getModuleInfo(me.fieldtype).showDisplayWindow(me.getValue());
			}
		}

	},
	enableKeyEvents : true, // 如果是空格键，并且值是空，那么就弹出选择框
	listeners : {
		keypress : function(field, e, eOpts){
			if (field.readOnly == false) if (e.getKey() == e.SPACE) {
				if (field.editable == false || !field.getValue()) {
					e.preventDefault();
					field.expand()
				}
			}
		}
	},

	config : {
		fieldtype : null,
		fieldDefine : null
	},

	initComponent : function(){
		var me = this;
		delete me.maxLength;
		var cobject = modules.getModuleInfo(me.fieldtype).fDataobject;
		// 设置fieldlabel
		var icon = '';
		if (cobject.iconcls) icon = '<span class="' + cobject.iconcls + '"/>'
		else if (cobject.iconurl) icon = '<img src="' + cobject.iconurl + '" />';
		var fl = me.fieldDefine ? me.fieldDefine.fieldtitle : (me.fieldLabel || cobject.title);
		me.fieldLabel = '<span class="gridheadicon" >' + (icon ? icon + ' ' : '') + fl + '</span>';
		if (!me.allowBlank) me.fieldLabel += "<font color='red'>✻</font>";
		// 选择方式
		var mode = cobject.selectedmode;
		if (mode === '01' || !mode) me.editable = false;
		if (mode === '04' || mode === '05') {
			me.queryMode = 'remote'
			// me.pageWidth = 10, //加了这个就需要处理分页，现在还没有，查询过后，加入所有的
		} else me.allowInputValue = mode === '03' // 录入的关键字可以和主键进行比较
		me.store = {
			fields : ['value', 'text'],
			idProperty : 'value',
			autoLoad : true,
			proxy : {
				type : 'ajax',
				extraParams : {
					moduleName : me.fieldtype
				},
				url : 'platform/dataobject/fetchcombodata.do',
				reader : 'json'
			}
		};
		this.callParent(arguments);
	},

	findRecordByDisplay : function(value){
		var result = this.store.byText.get(value), ret = false;
		if (result) {
			ret = result[0] || result;
		} else {
			if (this.allowInputValue) {
				// 如果设置了allowInputValue，那么录入的值会和主键进行比较
				var me = this;
				Ext.each(me.store.data._source.items, function(record){
					if (record.get(me.valueField) === value) {
						ret = record;
						return false;
					}
				});
			}
		}
		return ret;
	}

})
