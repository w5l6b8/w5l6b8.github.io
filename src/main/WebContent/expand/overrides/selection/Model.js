Ext.define('expand.overrides.selection.Model', {
	uses : [ 'Ext.selection.Model' ],
	override : 'Ext.selection.Model',
	onStoreRefresh : function() {
		this.updateSelectedInstances(this.selected);
		if (this.view && this.view.xtype == 'tableview') {
			if (Ext.tempDisableAutoSelectRecord != true)
				if (this.getStore().count() > 0 && this.selected.length == 0) {
					if (app && app.viewport
							&& app.viewport.getViewModel().get('_autoselectrecord')) {
						switch (app.viewport.getViewModel().get('_autoselectrecord')) {
						case 'everyload':
							this.select(this.getStore().first());
							break;
						case 'onlyone':
							if (this.getStore().count() == 1) {
								this.select(this.getStore().first());
							}
							break;
						}
					}
				}
		}
	}
})