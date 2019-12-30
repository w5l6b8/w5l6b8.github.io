/**
 * Grid 的一些操作的控制，包括grid中的金额字段的金额单位，是否自动调整列宽，是否自动选中
 * 
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
Ext.define('app.view.platform.central.controller.GridController', {
	extend : 'Ext.Mixin',

	requires : ['app.utils.Monetary'],

	init : function() {
		var vm = this.getView().getViewModel();
		vm.bind('{monetary}', 'onMonetaryChange', this);
		vm.bind('{autoColumnMode}', 'onAutoColumnModeChange', this);
		vm.bind('{autoselectrecord}', 'onAutoSelectRecordChange', this);
		vm.bind('{monetaryposition}', 'onMoneraryPositionChange', this);
		//		

	},

	// grid列自动刷新方式
	onAutoColumnModeChange : function(value) {
		if (this.getView().down('settingmenu').getMenu().isVisible()) {
			EU
					.toastInfo('列表列宽自动适应方式：'
									+ (value == 'firstload'
											? '首次加载'
											: value == 'everyload'
													? '每次加载'
													: '禁止自动调整'), {
								align : 'tl'
							});
		}
	},

	onAutoSelectRecordChange : function(value) {
		if (this.getView().down('settingmenu').getMenu().isVisible()) {
			EU.toastInfo('自动选中记录方式：'
							+ (value == 'everyload'
									? '每次加载'
									: value == 'onlyone' ? '单条选中' : '不自动选择'), {
						align : 'tl'
					});
		}
	},

	onMoneraryPositionChange : function(value) {

		if (this.getView().down('settingmenu').getMenu().isVisible())
			EU.toastInfo('数值单位显示位置：显示在'
							+ (value == 'behindnumber' ? '数值后' : '列头上'), {
						align : 'tl'
					});
		Ext.monetaryPosition = value;
		Ext.monetaryText = Ext.monetaryPosition === 'behindnumber' ? ' '
				+ Ext.monetary.monetaryText : ''; // 设置当前的全局的金额单位
		Ext.each(this.getView().query('modulegrid'), function(grid) {
			if (grid.rendered) {
				grid.getView().refresh();
				Ext.Array.forEach(grid.columnManager.getColumns(), function(
						column) {
					// 如果可以改变大小，并且是金额字段，则在改变了金额单位以后，自动调整一下列宽
					if (column.fieldDefine && column.fieldDefine.ismonetary) {
						column
								.setText(app.view.platform.module.grid.ColumnsFactory
										.getTextAndUnit(column.fieldDefine,column.gridField));
						column.autoSize();
					}
				});
			}
		});
	},

	// 金额单位修改过后执行
	onMonetaryChange : function(value) {
		if (this.getView().down('settingmenu').getMenu().isVisible())
			EU.toastInfo('列表数值单位：'
							+ app.utils.Monetary.getMonetary(value).unittext, {
						align : 'tl'
					});

		var m = app.utils.Monetary.getMonetary(value);
		Ext.monetary = m;
		Ext.monetaryText = Ext.monetaryPosition === 'behindnumber' ? ' '
				+ Ext.monetary.monetaryText : ''; // 设置当前的全局的金额单位
		Ext.monetaryUnit = m.monetaryUnit;
		Ext.each(this.getView().query('modulegrid'), function(grid) {
			if (grid.rendered) {
				grid.getView().refresh();
				Ext.Array.forEach(grid.columnManager.getColumns(), function(
						column) {
					// 如果可以改变大小，并且是金额字段，则在改变了金额单位以后，自动调整一下列宽
					if (column.fieldDefine && column.fieldDefine.ismonetary) {
						if (Ext.monetaryPosition === 'columntitle') {
							column
									.setText(app.view.platform.module.grid.ColumnsFactory
											.getTextAndUnit(column.fieldDefine,column.gridField));
						}
						column.autoSize();
					}
				});
			}
		});
	}
});
