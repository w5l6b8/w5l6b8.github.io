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


Ext.define('app.view.platform.frame.system.userlimit.AllLimitWindow', {
	  extend : 'Ext.window.Window',

	  requires : ['app.view.platform.frame.system.userlimit.AllLimitPanel'],

	  height : '80%',
	  width : 400,
	  modal : true,
	  maximizable : true,
	  shadow : 'frame',
	  shadowOffset : 10,
	  layout : 'fit',
	  iconCls : 'x-fa fa-gears',

	  title_ : '用户所有权限',

	  initComponent : function(){
		  var me = this;
		  if (me.userid) {
			  me.title = me.title_ + ' 『' + me.username + '』';
			  me.items = [{
				      xtype : 'useralllimitdisplaypanel',
				      roleid : me.userid,
				      rolename : me.username,
				      record : me.record,
				      header : false
			      }]
		  } else {
			  me.title = me.title_ + ' 『' + me.record.get('username') + '』';
			  me.items = [{
				      xtype : 'useralllimitdisplaypanel',
				      roleid : me.record.get('userid'),
				      rolename : me.record.get('username'),
				      record : me.record,
				      header : false
			      }]
		  }
		  me.callParent();
	  }

  })