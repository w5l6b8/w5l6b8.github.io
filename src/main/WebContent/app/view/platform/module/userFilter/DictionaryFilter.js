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

Ext.define('app.view.platform.module.userFilter.DictionaryFilter', {
	extend : 'app.view.platform.module.userFilter.BaseFilter',
	alias : 'widget.userdictionaryfilter',

	initComponent : function() {
		var me = this;
		me.items = [{
					xtype : 'displayfield',
					fieldLabel : me.fieldtitle,
					width : me.labelWidth,
					labelWidth : me.labelWidth - 2,
					labelAlign : 'right'
				}, {
					xtype : 'tagfield',
					itemId : 'value',
					filterPickList : true,
					name : me.getName(),
					queryMode : 'local',
					triggerAction : 'all',
					anyMatch : true, // 录入的关键字可以是在任何位置
					publishes : 'value',
					flex : 1,
					displayField : 'text',
					valueField : 'value',
					listeners : {
						change : function(field) {
							var result = null;
							if (field.getValue().length > 0) {
								me.setFilter({
											property : me.fieldname,
											operator : 'in',
											value : field.getValue().join(','),
											text : field.getRawValue(),
											title : me.fieldtitle
										})
							} else
								me.setFilter(null);
							me.up('moduleuserfilter').executeFilterForChange();
						}
					},
					store : Ext.create('Ext.data.Store', {
						fields : ['value', 'text'],
						autoLoad : true,
						proxy : {
							type : 'ajax',
							extraParams : {
								dictionaryId : me.userfilter.fDictionaryid
							},
							url : 'dictionary/getDictionaryComboData.do',
							reader : {
								type : 'json'
							}
						}
					})
				}];
		me.callParent(arguments);
	}

})