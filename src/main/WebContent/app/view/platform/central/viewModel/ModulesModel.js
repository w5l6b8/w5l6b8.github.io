/**
 * 总体模块控制的类，用来根据moduleName去加载模块
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
Ext.define('app.view.platform.central.viewModel.ModulesModel', {
	extend : 'Ext.Mixin',

	requires : ['app.view.platform.module.ModuleInfo'],

	init : function(){
	},

	/**
	 * 根据模块的名称，或者模块的编号，或者模块的asName(_t0000)来取得模块的定义
	 * @param {} moduleName
	 * @return {}
	 */
	getModuleInfo : function(moduleName){

		var me = this;
		var result = this.get('modules').get(moduleName);
		if (!result) {
			this.get('modules').each(function(module){
				  if (module.tableAsName == moduleName) {
					  result = module;
					  return false;
				  }
			  });
			if (!result) {
				// 如果还没有加载进来，那么去后台把module的信息加载进来
				Ext.Ajax.request({
					  url : 'moduleinfo/moduleinfo.do?moduleName=' + moduleName,
					  async : false, // 同步
					  success : function(response){
						  var text = response.responseText;
						  if (!text) {
							  alert('模块:' + moduleName + '取得失败！');
						  } else {
							  var moduleinfo = Ext.decode(response.responseText, true);
							  result = new Ext.create('app.view.platform.module.ModuleInfo', moduleinfo);
							  me.get('modules').add(moduleinfo.tf_moduleName, result);
						  }
					  }
				  });
			}
		}
		return result;
	},

	showModuleRecord : function(moduleName, id){
		this.getModule(moduleName).showRecord(id);
	},

	// 取得一个module的控制模块
	getModule : function(moduleName){
		return this.getModuleInfo(moduleName);
	},

	// 刷新某个模块的数据，如果该模块存在于页面上
	refreshModuleGrid : function(moduleNames){
		if (moduleNames) {
			var ms = moduleNames.split(','),
				me = this;
			Ext.each(ms, function(moduleName){
				var result = me.get('modules').get(moduleName);
				if (result) {
					if (result.modulePanel) result.modulePanel.getModuleGrid().refreshWithSilent();
					if (result.modulePanelWithParent) result.modulePanelWithParent.getModuleGrid().refreshWithSilent();
					if (result.modulePanelWithFilter) result.modulePanelWithFilter.getModuleGrid().refreshWithSilent();
					if (result.newModulePanelWithParent && result.newModulePanelWithParent.getModuleGrid()) result.newModulePanelWithParent.getModuleGrid().refreshWithSilent();
				}
			}
			);
		}
	}
}
);
