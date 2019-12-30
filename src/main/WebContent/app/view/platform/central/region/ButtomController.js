
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
Ext.define('app.view.platform.central.region.ButtomController', {
	  extend : 'Ext.app.ViewController',

	  alias : 'controller.buttom',

	  init : function(){
		  Ext.apply(Ext.form.field.VTypes, {
			    password : function(val, field){
				    if (field.initialPassField) {
					    var pwd = field.up('form').down('#' + field.initialPassField);
					    return (val == pwd.getValue());
				    }
				    return true;
			    },
			    passwordText : '确认新密码与新密码不匹配!'
		    });
	  },

	  logout : function(button){
		  button.up('appcentral').getController().logout();
	  },

	  // 显示当前用户单位和服务单位情况
	  onUserDwmcClick : function(button){
		  modules.getModuleInfo('FCompany').showDisplayWindow(cfg.company.companyid);
	  },

	  // 显示当前用户所在的部门情况
	  onUserDepartmentClick : function(){
		  modules.getModuleInfo('FOrganization').showDisplayWindow(this.getViewModel().get('userInfo.departmentid'));
	  },

	  // 显示当前用户的情况
	  onUserInfoClick : function(){
		  modules.getModuleInfo('FPersonnel').showDisplayWindow(this.getViewModel().get('userInfo.personnelid'));
	  },

	  // 当前用户的权限设置
	  onUserRolesClick : function(){

		  var win = Ext.widget('window', {
			    height : '70%',
			    width : 350,
			    layout : 'fit',
			    modal : true,
			    title : '角色设置『用户：' + app.viewport.getViewModel().get('userInfo.tf_userName') + '』',
			    items : [{
				        xtype : 'treepanel',
				        rootVisible : false,
				        buttonAlign : 'center',
				        buttons : [{
					            text : '关闭',
					            icon : 'images/button/return.png',
					            scope : this,
					            handler : function(button){
						            button.up('window').hide();
					            }
				            }],
				        store : new Ext.data.TreeStore({
					          autoLoad : true,
					          proxy : {
						          type : 'ajax',
						          url : 'user/getuserroles.do',
						          extraParams : {
							          userId : app.viewport.getViewModel().get('userInfo.tf_userId')
						          }
					          }
				          })
			        }]

		    });
		  win.down('treepanel').getView().onCheckChange = Ext.emptyFn;
		  win.show();

	  },

	  // 当前用户权限明细
	  onUserPopedomClick : function(){
		  var me = this;
		  var win = Ext.create('app.view.platform.frame.system.userlimit.AllLimitWindow', {
			    userid : me.getViewModel().get('userInfo.userid'),
			    username : me.getViewModel().get('userInfo.username')
		    });
		  win.show();

	  },

	  // 我的登录日志
	  onLoginInfoClick : function(){
		  this.showModuleWithName('FUserloginlog');
	  },

	  // 我的操作日志
	  onOperateInfoClick : function(){
		  this.showModuleWithName('FUseroperatelog');
	  },

	  // 修改密码
	  onChangePasswordClick : function(){

		  var win = Ext.widget('window', {
			    title : '修改登录密码',
			    iconCls : 'x-fa fa-user-secret',
			    width : 300,
			    modal : true,
			    layout : {
				    type : 'vbox',
				    pack : 'start',
				    align : 'stretch'
			    },
			    items : [{
				        xtype : 'panel',
				        bodyPadding : '10 0',
				        layout : 'center',
				        items : [{
					            layout : {
						            type : 'hbox',
						            pack : 'start',
						            align : 'middle'
					            },
					            items : [{
						                xtype : 'label',
						                text : app.viewport.getViewModel().get('userInfo.username'),
						                margin : '0 20 0 0'
					                }, {
						                xtype : 'userfavicon'
					                }]
				            }]
			        }, {
				        xtype : 'form',
				        fieldDefaults : {
					        labelAlign : 'right',
					        labelWidth : 80,
					        msgTarget : 'side',
					        autoFitErrors : false
				        },
				        buttonAlign : 'center',
				        buttons : [{
					            text : '确定',
					            formBind : true,
					            iconCls : 'x-fa fa-save',
					            handler : function(button){
						            var form = button.up('form');
						            if (form.isValid()) {
							            Ext.Ajax.request({
								              url : 'platform/systemframe/changepassword.do',
								              params : {
									              oldPassword : form.down('[name=oldpass]').getValue(),
									              newPassword : form.down('[name=newpass]').getValue()
								              },
								              success : function(response){
									              var result = Ext.decode(response.responseText, true);
									              if (result.success) {
										              EU.toastInfo('密码修改已保存成功!');
										              button.up('window').hide();
									              } else {
										              form.down('[name=oldpass]').markInvalid('原密码输入错误!');
										              EU.toastWarn('原密码输入错误!');
									              }
								              },
								              failure : function(response){
									              window.alert('修改密修保存失败!');
								              }
							              });
						            }
					            }
				            }, {
					            text : '关闭',
					            iconCls : 'x-fa fa-close',
					            handler : function(button){
						            button.up('window').hide();
					            }
				            }],
				        defaultType : 'textfield',
				        items : [{
					            xtype : 'fieldset',
                      margin : 5,
					            padding : 5,
					            layout : 'form',
					            defaults : {
						            inputType : 'password',
						            maxLength : 10,
						            enforceMaxLength : true,
						            allowBlank : false,
						            xtype : 'textfield'
					            },
					            items : [{
						                fieldLabel : '原密码',
						                name : 'oldpass'
					                }, {
						                fieldLabel : '新密码',
						                name : 'newpass',
						                itemId : 'newpass'
					                }, {
						                fieldLabel : '确认新密码',
						                initialPassField : 'newpass',
						                vtype : 'password'
					                }]
				            }]
			        }]
		    });

		  win.show();
		  win.down('field').focus();

	  },
	  // 注销
	  onLogoutClick : function(){
		  app.viewport.getController().logout();
	  },

	  onMainMenuClick : function(menuitem){
		  app.viewport.getController().onMainMenuClick(menuitem);
	  },

	  onQQClick : function(){
		  var obj = document.getElementById("__qq__");
		  obj.target = "_blank";
		  obj.href =
		      'http://wpa.qq.com/msgrd?V=1&Uin=' + app.viewport.getViewModel().get('userInfo.tf_serviceQQ')
		          + '&Site=http://wpa.qq.com&Menu=yes';
		  obj.click();

	  },

	  onEmailClick : function(){
		  var vm = app.viewport.getViewModel();
		  var link =
		      "mailto:" + vm.get('company.serviceemail') + "?subject=" + vm.get('company.companyname') + ' 的 '
		          + vm.get('userInfo.personnelname') + " 关于 " + vm.get('systeminfo.systemname') + " 的咨询";
		  window.location.href = link;
	  },

	  // 显示我的登录日志和操作日志
	  showModuleWithName : function(moduleName){
		  app.viewport.getController().addParentFilterModule({
			    childModuleName : moduleName,
			    parentModuleName : 'FUser',
			    fieldahead : 'FUser',
			    pid : app.viewport.getViewModel().get('userInfo.userid'),
			    ptitle : app.viewport.getViewModel().get('userInfo.username')
		    });
	  }

  });