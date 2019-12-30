Ext.define('app.view.platform.frame.default.MainController', {
    extend: 'Ext.app.ViewController',
    
    alias:'controller.defaultThemeController',
    
    init: function(){
    	var b_style = this.lookupReference('b_style');
    	var b_theme = this.lookupReference('b_theme');
    	EU.RSView("v_theme",function(result){
    		Ext.each(result,function(rec){
    			rec.code = rec.id;
    			delete rec.id;
    			rec.handler = function(btn){
    				local.set("theme",btn.code);
    				PU.onAppUpdate();
    			}
    		})
       		b_theme.setMenu(result);
    	});
    	EU.RSView("v_style",function(result){
    		Ext.each(result,function(rec){
    			delete rec.id;
    			var code=rec.text;
    			rec.text=rec.custom1;
    			rec.handler = function(btn){
    				localStorage.setItem("style",code);
    				PU.onAppUpdate();
    			}
    		})    	
    		b_style.setMenu(result);
    	});
    },
    
    onMenuTreeItemClick:  function(tree, record, item, index, e, eOpts) {
     	PU.openTabModule(record.data);
    },
    
    onFavoritesContextmenu:function(tree, record, item, index, e, eOpts){
    	var me = this,menu = me.menu;
    	if (Ext.isEmpty(menu)) {
    	 	me.menu = menu = Ext.create('Ext.menu.Menu', {
    	 		items: [
			        {text: '取消收藏',iconCls:'x-fa fa-bitbucket',handler:function(btn,e){
				    	var record = me.menu.record;
			        	EU.RScfgDel(record.data.themecfgid,function(result){
				    		delete Ext.SystemFavorites[record.data.id];
				    		tree.store.remove(record);
				    	});
			        }}
			     ]
    	 	});
    	}
    	me.menu.record = record;
    	menu.showAt(e.getXY());
    },
    
    //颜色显示
    treeNavNodeRenderer: function(value, metaData, record, rowIndex, colIndex, store, view) {
        return view.rendererRegExp ? value.replace(view.rendererRegExp, '<span style="color:red;font-weight:bold">$1</span>') : value;
    },
    
    onUserclick:function(btn){
    	var user = cfg.sub;
    	var params = {userid:user.userid,personnelid:user.personnelid};
    	PU.openModule({title:"用户信息维护",xtype:"userEdit",width:750,height:700,params:params,scope:this,animateTarget:btn});
    },
    
    onMessage:function(btn){
    	var x = PU.getWidth()-250-50;
    	this.msgWin.setPosition(x,50);
    	this.msgWin.show();
    },
    
    onLogout:function(btn){
    	var me = this;
    	PU.onLogout(btn,function(){
	    	if(me.msgWin)me.msgWin.sysclose();
    	});
    }
});