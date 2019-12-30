Ext.define('app.view.platform.frame.SystemFrameController', {
    extend: 'Ext.app.ViewController',
    
    alias:'controller.systemframe',
    
    init:function(){
		var theme = local.get("theme") || cfg.theme;
		var view = this.getView();
		view.removeAll(true);
		EU.RSView("v_theme",function(result){
			var xtype = "";
			Ext.each(result,function(rec){
				if(rec.id == theme){
					xtype = rec.codevalue;
					return;
				}else if(xtype==""){
					xtype = rec.codevalue;
				}
			})
			if(!Ext.isEmpty(xtype)){
				cfg.theme = theme;
				var panel = Ext.create(xtype,{view:view});
				if(panel instanceof Ext.panel.Panel){
					view.add(panel);
				}
			}
		});
    }
});