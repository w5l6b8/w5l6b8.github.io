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

Ext.define('app.view.platform.design.selectField.SelectFieldViewModel', {
	extend : 'Ext.app.ViewModel',
	alias : 'viewmodel.selectafield',

	data : {
		_moduleName : '',
		selectedModuleDescription : '',
		isChild : false,
		selectedFieldTitle : '',
		selectedFieldTreeItem : null
	},

	formulas : {
		moduleName : {
			get : function(){
				return this.get('_moduleName');
			},
			
			set : function(value){
				this.set('_moduleName' , value);
				this.getView().down('modulehierarchytree').setModuleName(value);
			}
		}
	} ,
	
	setModuleName : function (value){
		this.set('_moduleName' , value);
		this.getView().down('modulehierarchytree').ApplyModuleName(value);
	},
	
	reset : function(){
		this.set('_moduleName' , null);
		this.set('selectedModuleDescription' , null);
		this.set('isChild', null);
		this.set('selectedFieldTitle' , null);
		this.set('selectedFieldTreeItem' , null);
	}
})