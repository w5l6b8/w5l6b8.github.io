/**
 * 最近打开过的模块
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
Ext.define('app.view.platform.central.widget.OpenRecentTree', {
	extend : 'Ext.tree.Panel',

	alias : 'widget.openrecenttree',

	openRecentCount : 10,

	store : Ext.create('Ext.data.TreeStore', {
		root : {
			text : '最近打开过的模块',
			expanded : true
		}
	}),

	rootVisible : true,

	lines : false,

	initComponent : function() {
		var me = this;

		this.callParent(arguments);
	},


	/**
	 * 打开了一个模块以后，将其加入到OpenRecent的第一个,并更新各个菜单里的数据
	 */
	addItem : function(item) {
		this.getStore().getRoot().insertChild(0, item);
		this.localStore.add({
			text : item.text
		});
		this.localStore.sync();
	}

})
