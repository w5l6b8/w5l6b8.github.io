
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
Ext.define('app.view.platform.central.widget.FavoriteButton', {

	extend : 'expand.ux.ButtonTransparent',
	alias : 'widget.favoritebutton',
	text : '收藏',

	iconCls : 'x-fa fa-star',

	listeners : {

		render : function(button) {
			// 可以使Grid中选中的记录拖到到此按钮上来进行删除

			button.dropZone = new Ext.dd.DropZone(button.getEl(), {
				// 此处的ddGroup需要与Grid中设置的一致
				ddGroup : 'DD_Province',// + button.module.tf_moduleName,

				// 这个函数没弄明白是啥意思,没有还不行
				getTargetFromEvent : function(e) {
					return e.getTarget('');
				},

				// On entry into a target node, highlight that node.
				onNodeEnter : function(target, dd, e, data) {
					// Ext.fly(target).addCls('hospital-target-hover');
				},

				// 用户拖动选中的记录经过了此按钮
				onNodeOver : function(target, dd, e, data) {
					return Ext.dd.DropZone.prototype.dropAllowed;
				},
				// 用户放开了鼠标键，删除记录
				onNodeDrop : function(target, dd, e, data) {
					var record = data.records[0];
					var s = '已将 ' + record.module.tf_title
							+ ' 的记录『<span style="color:green;">' + record.getNameValue()
							+ '</span>』加入收藏夹。';
					EU.toastInfo(s);

				}
			})
		}
	}

})
