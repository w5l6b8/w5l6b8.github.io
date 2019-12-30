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


Ext.define('app.view.platform.module.toolbar.widget.SchemeSegmented', {
	extend : 'Ext.button.Segmented',
	alias : 'widget.gridschemesegmented',

	firstDonotChange : true, // 刚创建的时候，不要发送 change事件。
	listeners : {
		change : function(button, value) {
			if (this.firstDonotChange)
				this.firstDonotChange = false;
			else
				this.grid.gridSchemeChange(value);
		}
	},

	initComponent : function() {

		this.grid = this.up('tablepanel');
		this.items = [];
		var schemes = this.grid.moduleInfo.fDataobject.gridSchemes;
		this.c = 1;
		this.addToItems(schemes.system, '系统方案');
		this.addToItems(schemes.owner, '我的方案');
		this.addToItems(schemes.othershare, '别人分享的');
		delete this.c;
		this.value = this.grid.moduleInfo.getGridDefaultScheme().gridschemeid;
		this.callParent(arguments);
	},

	deleteScheme : function(schemeid) {
		this.setValue(this.items.getAt(0).value);
		EU.toastInfo('已选中列表方案『' + this.items.getAt(0).tooltip + '』')
		this.remove(this.down('[value=' + schemeid + ']'));
		for (var i = 0; i < this.items.getCount(); i++)
			this.items.getAt(i).setText('' + (i + 1));
	},

	addSchemeAndSelect : function(scheme, type) {
		this.add({
					text : '' + (this.items.getCount() + 1),
					tooltip : type + '： ' + scheme.schemename,
					value : scheme.gridschemeid,
					style : 'padding-left : 0px;padding-right: 0px;'
				})
		this.setValue(scheme.gridschemeid);
		EU.toastInfo('已选中列表方案『' + scheme.schemename + '』')

	},

	updateSchemeAndSelect : function(scheme, type) {
		this.down('[value=' + scheme.gridschemeid + ']').setTooltip(type + '： '
				+ scheme.schemename);
		this.grid.gridSchemeChange(scheme.gridschemeid);
	},

	addToItems : function(schemes, type) {
		if (schemes)
			Ext.each(schemes, function(scheme) {
						this.items.push({
									text : '' + this.c++,
									tooltip : type + '： ' + scheme.schemename,
									value : scheme.gridschemeid,
									style : 'padding-left : 0px;padding-right: 0px;'
								})
					}, this)
	}

});