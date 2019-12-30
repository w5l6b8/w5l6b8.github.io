Ext.define('expand.ux.TreePanelEdit', {
    extend: 'Ext.tree.Panel',
    alternateClassName:'ux.TreepanelEdit',
    xtype: 'treepaneledit',
    
    idfield:'id',
    textfield:'text',
    parentidfield : 'parentid',
    saveorupdateurl : '', //新增提交地址
    moveorderurl:'',	  //移动提交地址
    deleteurl: '',		  //删除提交地址
    tools : [
		{type : 'expand',tooltip : '展开全部树节点',handler : function() {this.up("treepanel").expandAll();}}, 
		{type : 'collapse',tooltip : '收缩全部树节点',handler : function(event, toolEl, panelHeader) {this.up("treepanel").collapseAll();}}, 
	 	{type : 'refresh',tooltip : '刷新菜单树',handler : function(event, toolEl, panelHeader) {this.up("treepanel").getStore().reload();}}
	],
    initComponent : function() {
    	var me = this;
    	Ext.apply(me,{
    		viewConfig :  {
		    	scope:this,
		        plugins: {
		            ptype: 'treeviewdragdrop',
		            allowLeafInserts:true
		        },
		        listeners:{
		        	drop: function(node, data, overModel, dropPosition, eOpts){
				    	var parentNode = dropPosition=='append'? overModel:overModel.parentNode;
						me.saveMoveOrder(parentNode.childNodes);
					}
		        }
		    },
		    listeners : {
				containercontextmenu:function(tree , e , eOpts){
					e.stopEvent();
					var menu = this.createMenu();
					menu.setItemsDisplay(true,0);
					menu.showAt(e.getXY());
				},
				rowcontextmenu : function(tree, record, tr, rowIndex, e, eOpts){
					e.stopEvent();
					var menu = this.createMenu();
					menu.setItemsDisplay(false);
					menu.showAt(e.getXY());
				}
			}
    	});
    	me.callParent();
    },
	
	/**
	 * 创建右键菜单
	 * @return {}
	 */
    createMenu:function(){
    	var me = this;
        return me.menu || (me.menu = Ext.create('Ext.menu.Menu', {
		    items: [
		    	{text: '插入根节点',iconCls:'x-fa fa-cogs',scope:me,handler:me.addRootNode},
		    	{text: '插入同级节点',iconCls:'x-fa fa-cogs',scope:me,handler:me.addPeerNode},
		        {text: '插入子节点',iconCls:'x-fa fa-cogs',scope:me,handler:me.addChildNode},
	            {text: '编辑节点',iconCls:'x-fa fa-pencil-square-o',scope:me,handler:me.updateNode},
	            {text: '删除节点',iconCls:'x-fa fa-trash',scope:me,handler:me.deleteNode}
	         ]
		}));
    },
    
    /**
     * 添加根节点数据
     */
    addRootNode:function(){
       var me = this,
       	   rootNode = me.getStore().getRootNode(),
       	   win = me.createFormPanel();
       win.show({parenttext:"Root"},function(result){
       		result.leaf = true;
       		me.reloadNode(rootNode,result.id);
       		win.hide();
       });
    },

    /**
     * 添加同级节点数据
     */
    addPeerNode:function(){
    	var me = this,
    		currentNode = me.getSelectRecord(),
    		parentNode = currentNode.parentNode,
    		params = {parentid:parentNode.id,parenttext:parentNode.data.text},
       		win = me.createFormPanel();
        win.show(params,function(result){
       		result.leaf = true;
       		me.reloadNode(parentNode,result.id);
       		win.hide();
        });
    },
    
    /**
     * 添加子级节点数据
     */
    addChildNode:function(){
        var me = this,
    		currentNode = me.getSelectRecord(),
    		params = {parentid:currentNode.id,parenttext:currentNode.data.text},
       		win = me.createFormPanel();
        win.show(params,function(result){
       		result.leaf = true;
       		me.reloadNode(currentNode,result.id);
       		win.hide();
        });
    },
    
    /**
     * 修改节点
     */
    updateNode:function(){
    	var me = this,
    		currentNode = me.getSelectRecord(),
    		parentNode = currentNode.parentNode,
    		params = {id:currentNode.id,text:currentNode.data.text,parentid:parentNode.id,parenttext:parentNode.data.text},
       		win = me.createFormPanel();
        win.show(params,function(result){
       		currentNode.set("text",result.text);
       		win.hide();
        });
    },
    
    /**
     * 删除节点
     */
    deleteNode:function(){
    	var me = this,
    		store = me.getStore(),
    		record = me.getSelectRecord();
    	var params = {};
    	params[me.idfield] = record.id;
        EU.RS({url:me.deleteurl,scope:me,msg:false,params:params,callback:function(result){
        	store.remove(record);
        }});
    },
    
    saveMoveOrder:function(childNodes){
    	var me = this,list = [];
    	Ext.each(childNodes,function(rec){
    		var params = {};
    		params[me.idfield] = rec.id;
    		params[me.parentidfield] = rec.data.parentId;
    		list.push(params);
    	});
		EU.RS({url:me.moveorderurl,scope:this,msg:false,params:{list:list}});
    },
    
    /**
     * 获取选择的节点
     * @return {}
     */
    getSelectRecord: function(){
        var selecteds = this.getSelection();
    	if(selecteds.length==0)return null;
    	return selecteds[0];
    },
    
    /**
     * 创建Form表单维护
     * @param {} params
     * @return {}
     */
    createFormPanel:function(params){
    	var me = this;
    	if(!me.win){
	    	var formPanel = Ext.create("ux.form.Panel",{
	    	 	items:[
	    	 		{xtype:"textfield",fieldLabel:'上级目录',name: 'parenttext',readOnly:true,width: '100%'},
		        	{xtype:"textfield",fieldLabel:'组名称',name : 'text',allowBlank : false,width : '100%'}
		        ],
	    	 	buttons:[
			    	{text:'提交',scope:me,handler:function(){
			    		me.onFormSubmit(formPanel);
			    	}},
			    	{text:'关闭',scope:this,handler:function(){
			    		if(formPanel.isAllDirty()){
			    			EU.showMsg({title:'保存修改',message:"当前记录已经被修改过，需要保存吗?",option:1,scope:this,callback:function(btn, text){
			    				btn == 'yes' ? me.onFormSubmit(formPanel) : me.win.hide();
			    			}});
			    		}else{
			    			me.win.hide();
			    		}
			    	}}
			    ]
	    	});
	    	me.win = Ext.create('Ext.window.Window',{title: '数据操作',width:300,layout: 'fit',items:formPanel});
	    	me.win.show = function(params,callback){
	    		formPanel.setValues(params);
	    		formPanel.params = params;
	    		formPanel.callback = callback;
	    		me.win.superclass.show.call(this);
	    	}
	    	me.win.hide = function(){
	    		formPanel.setValues({text:"",parenttext:""});
    			formPanel.reset();
	    		me.win.superclass.hide.call(this);
	    	}
    	}
    	return me.win;
    },
    
    /**
     * From提交数据
     * @param {} formPanel
     */
    onFormSubmit:function(formPanel){
    	var me = this;
    	if(!formPanel.isValid())return;
    	var params = Ext.apply(formPanel.params,formPanel.getValues());
    	if(Ext.isEmpty(me.saveorupdateurl)){CU.log("saveorupdateurl不能为空");return;}
    	if(Ext.isObject(params)){
    		params[me.idfield] = params.id;
    		params[me.textfield] = params.text;
    		params[me.parentidfield] = params.parentid;
    		if(params.parentid=='00' || Ext.isEmpty(params.parentid)){
    			delete params[me.parentidfield];
    		}
    	}
    	EU.RS({url:me.saveorupdateurl,scope:this,params:params,callback:function(result){
    		  formPanel.updateDirty();
    		  EU.toastInfo(params[me.idfield]?"新增成功":"修改成功");
    		  if(Ext.isFunction(formPanel.callback)){
    		  	 if(Ext.isObject(result)){
    		  	 	result.id = result[me.idfield];
    		  	 	result.text = result[me.textfield];
    		  	 }
    		  	 Ext.callback(formPanel.callback,me,[result]);
    		  }
    	}});
    }
});