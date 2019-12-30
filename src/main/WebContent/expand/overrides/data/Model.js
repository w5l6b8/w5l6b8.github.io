Ext.define('expand.overrides.data.Model', {
	override : 'Ext.data.Model',

	loadRecord : function(record){
		var me = this;
		if (Ext.isEmpty(record)) return;
		me.beginEdit();
		if (record instanceof Ext.data.Model) {
			me.set(record.getData());
		} else {
			me.set(record);
		}
		me.endEdit();
		me.commit();
		return this;
	},

	updateRecord : function(record){
		if (Ext.isEmpty(record)) return;
		var me = this,
			fields = me.tablefields || me.fields,
			values = {};
		if (record instanceof Ext.data.Model) {
			var datas = record.getData();
			Ext.each(fields, function(field){
				if (datas.hasOwnProperty(field.name)) {
					if (record.get(field.name) != me.get(field.name)) {
						values[field.name] = record.get(field.name);
					}
				}
			});
		}else if(Ext.isObject(record)){
			var datas = me.getData();
			Ext.each(fields, function(field){
				if (record.hasOwnProperty(field.name)) {
					if (record[field.name] != datas[field.name]) {
						values[field.name] = record[field.name];
					}
				}
			});
		}
		me.beginEdit();
		me.set(values);
		me.endEdit();
		me.commit();
		return me;
	}
});
