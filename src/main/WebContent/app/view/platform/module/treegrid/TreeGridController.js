/**
 * 
 * @author jiangfeng
 * 
 * www.jhopesoft.com
 * 
 * jfok1972@qq.com
 * 
 * 2017-06-01
 * 
 */

Ext.define('app.view.platform.module.treegrid.TreeGridController', {
	extend : 'app.view.platform.module.grid.GridController',
	alias : 'controller.moduletreegrid',

	onNodeBeforeDrop : function(node, data, overModel, dropPosition, dropHandlers) {

		var grid = this.getView();
		var sourceRecord = data.records[0]; // 当前拖动的记录
		dropHandlers.wait = true;
		if (dropPosition != 'append'
				&& sourceRecord.get(grid.moduleInfo.fDataobject.parentkey) === overModel
						.get(grid.moduleInfo.fDataobject.parentkey)) {
			dropHandlers.processDrop();
		} else {
			var pnode = overModel;
			// before,after
			if (dropPosition != 'append')
				pnode = pnode.parentNode;
			var pid = pnode.isRoot() ? null : pnode.getIdValue();

			EU.RS({
				url : 'platform/dataobject/updateparentkey.do',
				async : false,
				params : {
					objectname : grid.moduleInfo.fDataobject.objectname,
					id : sourceRecord.getIdValue(),
					parentkey : pid
				},
				callback : function(result) {
					if (result.success) {
						dropHandlers.processDrop();
						sourceRecord.set(grid.moduleInfo.fDataobject.parentkey, pid);
						sourceRecord.commit();
					} else
						dropHandlers.cancelDrop();
				}
			})

		}
	},

	onNodeDrop : function(node, data, overModel, dropPosition, dropHandlers) {

	}

})