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

Ext.define('app.view.platform.module.toolbar.SettingButton', {
	extend : 'Ext.button.Button',
	alias : 'widget.toolbarsettingbutton',
	requires : [ 'app.view.platform.module.toolbar.SettingMenu' ],
	tooltip : '设置或改变工具条的位置',
	itemId : 'settingbutton',
	iconCls : 'x-fa fa-gear',
	defaultIconCls : 'x-fa fa-gear',
	listeners : {
		click : function(button) {
			var toolbar = button.up('toolbar');
			var d = button.c;
			if (!d)
				return;
			if (d == 'center') {
				toolbar.up('tablepanel').down('toolbarsettingmenu').showBy(button);
			} else {
				var dock = d == 'e' ? 'right' : d == 'n' ? 'top' : d == 'w' ? 'left'
						: 'bottom';
				button.setIconCls(button.defaultIconCls);
				toolbar.up('tablepanel').getViewModel().set('module.toolbar.dock',
						dock);
			}
		},

		render : function(button) {
			button.getEl().on(
					'mousemove',
					function(event) {
						var np = button.getPosition(false), w = button.getWidth();
						//可能是长方形的按钮，需要计算出长宽的比例因子
						var wdivh = 1.0 * w / button.getHeight();
						var numX = event.pageX - np[0] - w / 2;
						var numY = (0 - (event.pageY - np[1] - button.getHeight() / 2))
								* wdivh;
						// 这个编辑器里面函数里加 /4 就不能自动排js了
						var c = button.getCursorFromXY(numX, numY, w * 0.25);
						button.c = c;
						if (c == 'center') {
							button.setIconCls('x-fa fa-reorder');
							button.getEl().setStyle({
								cursor : 'pointer'
							})
						} else {
							button.getEl().setStyle({
								cursor : c + '-resize'
							});
							button.setIconCls('x-fa fa-arrow-circle-'
									+ (c == 'e' ? 'right' : c == 'n' ? 'up' : c == 'w' ? 'left'
											: 'down'))
						}
					})

		},

		mouseout : function(button) {
			button.setIconCls(button.defaultIconCls);
		}
	},

	// 取得当前鼠标在这个按钮上面的位置，分为 上下左右，中心五个位置
	getCursorFromXY : function(numX, numY, w) {
		var cursor = 'center';
		// 如果是在中心位置，那么点击就出现一个菜单
		if (Math.abs(numX) >= w || Math.abs(numY) >= w) {
			if (numY > 0) {
				if (numX < 0) {
					if (numX + numY > 0)
						cursor = 'n';
					else
						cursor = 'w';
				} else {
					if (numX - numY > 0)
						cursor = 'e';
					else
						cursor = 'n';
				}
			} else {
				if (numX < 0) {
					if (numX - numY > 0)
						cursor = 's';
					else
						cursor = 'w';
				} else {
					if (numX + numY > 0)
						cursor = 'e';
					else
						cursor = 's';
				}
			}
		}
		return cursor;
	}

});