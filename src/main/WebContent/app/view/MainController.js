Ext.define('app.view.MainController', {
	  extend : 'Ext.app.ViewController',

	  alias : 'controller.viewmain',
	  requires : ['app.utils.Monetary'],

	  routes : {
		  ':xtype' : 'handleRoute'// 执行路由
	  },

	  handleRoute : function(xtype){
		  var home = null;
		  try {
			  home = Ext.create(xtype);
		  } catch (e) {
			  console.log(e);
			  home = Ext.create(cfg.xtypeLogin);
		  }
		  Ext.FramePanel.removeAll(true);
		  Ext.FramePanel.add(home);
	  },

	  init : function(){
		  this.initCfg();
		  Ext.Panel = this;
		  Ext.Viewport = this.getView();
		  Ext.FramePanel = Ext.Viewport.items.items[0]; // 主窗口面板
		  Ext.lockPanel = Ext.Viewport.items.items[1]; // 锁屏面板
		  var islogin = local.get("isLogin") || false;
		  if (CU.getBoolean(islogin) == true) {
			  Ext.util.History.setHash("");// 移除浏览器指定的路由对象
			  EU.RS({
				    url : "login/getuserbean.do",
				    msg : false,
				    scope : this,
				    callback : function(result){
					    cfg.sub = result;
					    this.redirectTo(cfg.xtypeFrame);
				    }
			    });
		  } else {
			  this.redirectTo(cfg.xtypeLogin);
		  }
	  },

	  initCfg : function(){
		  var me = this;
		  Ext.Ajax.on('requestexception', function(conn, response, options){
			    if (response.status == "999") {
				    local.remove("isLogin");
				    var wins = PU.wins;
				    for (var key in wins)
					    wins[key].close();
				    if (Ext.msgWin) Ext.msgWin.sysclose();
				    if (cfg.sub == null || Ext.isEmpty(cfg.sub.usercode)) {
					    me.redirectTo(cfg.xtypeLogin);
				    } else {
					    Ext.Viewport.getLayout().setActiveItem(1);
				    }
			    } else if (response.status == "998") {
				    Ext.Msg.show({
					      title : '警告',
					      message : '没有访问权限！',
					      buttons : Ext.Msg.CANCEL,
					      icon : Ext.Msg.WARNING
				      });
				    return false;
			    }
		    });
		  // 获取系统配置
		  EU.RS({
			    url : "login/getsysteminfo.do",
			    params : {
				    companyid : cfg.companyid
			    },
			    async : false,
			    callback : function(result){
				    document.title = result.systeminfo.systemname;
				    Ext.apply(cfg, result);
			    }
		    });
	  }
  });