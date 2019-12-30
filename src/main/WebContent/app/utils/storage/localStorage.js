/**
 * 项目永久存储信息
 */
Ext.define('app.utils.storage.localStorage', {
	alternateClassName : 'local', // 设置别名是为了方便调用，这样直接cfg.xxx就能获取到变量。
	statics : {
		/**
		 * 设置系统缓存数据
		 * @param {} key
		 * @param {} obj
		 */
		set : function(key, obj){
			if (Ext.isEmpty(key)) return;
			localStorage.setItem(key, CU.toString(obj));
		},

		/**
		 * 获取系统缓存数据
		 * @param {} key
		 * @param {} defaultValue
		 * @return {}
		 */
		get : function(key, defaultValue){
			if (Ext.isEmpty(key)) return null;
			return CU.toObject(localStorage.getItem(key)) || defaultValue;
		},

		remove : function(key){
			localStorage.removeItem(key);
		},

		clean : function(){
			localStorage.clear();
		}
	}
});