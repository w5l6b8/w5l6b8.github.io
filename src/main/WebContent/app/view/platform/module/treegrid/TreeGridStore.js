/**
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


Ext.define('app.view.platform.module.treegrid.TreeGridStore', {
	  extend : 'Ext.data.TreeStore',

	  modulePanel : null,
	  autoLoad : false,
	  autoSync : false,
	  remoteSort : false,
	  remoteFilter : false,
	  filterer : 'bottomup',
	  config : {
		  extraParams : {},
		  navigates : [],
		  userFilters : [],
		  // 导航属性选中的情况
		  sortScheme : null
	  },
	  proxy : {
		  type : 'rest',
		  appendId : false,
		  batchActions : true,
		  extraParams : {
			  moduleName : undefined
		  },
		  api : {
			  read : 'platform/dataobject/fetchtreedata.do',
			  update : 'platform/dataobject/update.do',
			  create : 'platform/dataobject/create.do',
			  destroy : 'platform/dataobject/remove.do'
		  },
		  actionMethods : {
			  create : 'POST',
			  read : 'POST',
			  update : 'POST',
			  destroy : 'POST'
		  },
		  reader : {
			  type : 'json'
		  },
		  writer : {
			  type : 'json',
			  writeRecordId : true,
			  writeAllFields : false
			  // 没有修改过的字段不加入到update的json中去
		  }
	  },
	  constructor : function(param){
		  var me = this;
		  me.extraParams = {};
		  me.navigates = [];
		  me.userFilters = [];
		  // 有创建时加进来的导航约束
		  if (param.modulePanel.param) {
			  var dnv = param.modulePanel.param.defaultNavigateValues;
			  me.setDefaultNavigates(dnv);
		  }
		  me.callParent(arguments);
	  },

	  listeners : {
		  beforeload : function(store){
			  if (!(store.grid && store.grid.rendered)) { return false; }
			  store.proxy.extraParams.moduleName = store.module.fDataobject.objectname;
			  if (store.getSorters().getCount() > 0) store.sortScheme = null;
			  if (store.sortScheme) {
				  store.proxy.extraParams.sortschemeid = store.sortScheme.sortschemeid;
			  } else delete store.proxy.extraParams.sortschemeid;
			  if (store.parentFilter) {
				  if (store.parentFilter.fieldvalue) {
					  store.proxy.extraParams.parentFilter = Ext.encode(store.parentFilter);
				  } else {
					  // 把 store 里的数据清空, 好象没有好办法，只能去后台调用一次ajax
					  // store.removeAll(false);
					  // store.setData([]); 都不彻底
					  return false;
				  }
			  } else delete store.proxy.extraParams.parentFilter;
			  for (var i in store.extraParams)
				  store.proxy.extraParams[i] = store.extraParams[i];
			  {
				  var _query_ = false;
				  store.getFilters().each(function(filter){
					    if (filter.getProperty() == '_query_') {
						    _query_ = filter;
						    return false;
					    }
				    })
				  if (_query_) {
					  store.proxy.extraParams.query = Ext.encode(store.grid.getQueryFilters(_query_));
				  } else delete store.proxy.extraParams.query;
			  }
			  store.lastExtraParams = {};
			  Ext.apply(store.lastExtraParams, store.proxy.extraParams);
		  },

		  load : function(store){
			  for (var i in store.extraParams)
				  delete store.proxy.extraParams[i];
		  }

	  },


	  resetSort : function(){
		  var me = this;
		  me.getSorters().removeAll();
		  me.sortScheme = null;
		  me.load();
	  },

	  setSortScheme : function(scheme){
		  var me = this;
		  me.sortScheme = scheme;
		  me.getSorters().removeAll();
		  me.load();
	  },

	  /**
		 * 设置用户自定义的条件
		 * @param {} filters
		 */
	  setUserFilters : function(filters){
		  var me = this;
		  if (Ext.isArray(filters) && filters.length > 0) {
			  me.userFilters = filters;
			  me.extraParams.userfilter = Ext.encode(filters);
		  } else {
			  me.userFilters = [];
			  delete me.extraParams.userfilter;
		  }
		  me.loadPage(1);
		  me.fireEvent('filterchange', this);
	  },

	  /**
		 * 设置指定的 navigates，是程序里指定的，不是用户单击navigates 产生的
		 * @param {} array
		 */
	  setDefaultNavigates : function(dnv){
		  var me = this;
		  me.navigates = [];
		  delete me.extraParams.navigates;
		  if (dnv && dnv.length > 0) {
			  Ext.each(dnv, function(nv){
				    for (var n in nv) {
					    me.navigates.push(nv[n]);
				    }
			    });
			  if (me.navigates.length > 0) Ext.apply(me.extraParams, {
				    navigates : Ext.encode(me.navigates)
			    });
		  }
	  },

	  /**
		 * 当导航条件修改过了，将数组赋给proxy 刷新数据
		 */
	  setNavigates : function(array){
		  var me = this;
		  me.navigates = array;
		  if (me.navigates.length > 0) Ext.apply(me.extraParams, {
			    navigates : Ext.encode(me.navigates)
		    });
		  else delete me.extraParams.navigates;
		  if (me.buffered) me.data.clear();
		  me.loadPage(1);
		  me.fireEvent('filterchange', this);
	  },

	  getFilterCount : function(){
		  var me = this;
		  var count = me.getFilters().length;
		  count += me.getNavigates().length;
		  count += me.getUserFilters().length;
		  return count;
	  }
  });
