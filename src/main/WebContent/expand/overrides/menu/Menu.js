Ext.define('expand.overrides.menu.Menu', {
    override :'Ext.menu.Menu',
    
    /**
     * 指定下标节点显示
     * @param {} display true=显示,false=隐藏
     * @param {} indexs 下标数组
     */
    setItemsDisplay:function(display,indexs){
    	var items = this.items.items;
    	indexs = Ext.isArray(indexs)?indexs:[indexs];
    	Ext.each(items,function(item){
    		item.setHidden(display);
    	});
    	Ext.each(indexs,function(index){
    		if(Ext.isNumber(index)){
    			items[index].setHidden(!display);
    		}else if(Ext.isObject(index)){
    			if(Ext.isNumber(index.index) && Ext.isBoolean(index.display)){
    			   items[index.index].setHidden(!index.display);
    			}
    		}
    	})
    }
});