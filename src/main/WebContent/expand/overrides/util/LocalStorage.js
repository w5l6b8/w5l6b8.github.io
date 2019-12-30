Ext.define('expand.overrides.util.LocalStorage', {
	uses : [ 'Ext.util.LocalStorage' ],
	override : 'Ext.util.LocalStorage',

	getItem : function(key, defaultValue) {
		var k = this.prefix + key;
		var value = this._store.getItem(k);
		if (defaultValue)
			return value ? value : defaultValue
		else
			return value;
	}
})