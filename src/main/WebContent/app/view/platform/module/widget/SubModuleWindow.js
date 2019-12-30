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


Ext.define('app.view.platform.module.widget.SubModuleWindow', {

	  extend : 'Ext.window.Window',
	  alias : 'widget.submodulewindow',

	  layout : 'fit',
	  maximizable : true,
	  height : 600,
	  width : 800,
	  shadowOffset : 20,
	  //modal : true,
	  bodyPadding : 1,
	  pModuleName : null,
	  pModuleTitle : null,
	  pId : null,
	  pName : null,
	  fieldahead : null,

	  initComponent : function(){
		  var me = this;
		  var module = modules.getModuleInfo(me.childModuleName);
		  if (!me.param) me.param = {};
		  me.param.inWindow = true;
		  var m = module.getNewPanelWithParentFilter(me.pModuleName, me.fieldahead, me.pId, me.pName, me.param);
		  m.border = false;
		  m.frame = false;
		  var object = module.fDataobject;
		  me.icon = object.icon;
		  me.iconCls = object.iconcls;
		  me.title = object.title;

		  delete m.icon;
		  delete m.title;
		  delete m.closable;

		  me.items = [m];

		  me.callParent(arguments);
	  }

  })
