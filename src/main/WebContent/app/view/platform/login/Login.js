Ext.define('app.view.platform.login.Login', {
	extend : 'Ext.panel.Panel',
	alternateClassName : 'login',
	xtype : 'login',
	requires : ['app.view.platform.login.LoginController', 'app.view.platform.central.Central'],
	controller : 'userlogin',
	layout : {
		type : 'fit'
	},
	referenceHolder : true,
	initComponent : function(){

		var me = this;
		me.items = [{
			bodyStyle : "background-image:url('resources/images/bg.gif');",
			layout : {
				type : 'vbox',
				align : 'center',
				pack : 'center'
			},
			items : [{
				    xtype : 'container',
				    flex : 3
			    }, {
				    xtype : 'label',
				    style : 'text-align :center;letter-spacing:2mm;text-shadow: 3px 3px 3px rgba(42,42,42,0.75);font-size:32px;',
				    html : cfg.systeminfo.systemname,
				    width : '100%'
			    }, {
				    xtype : 'container',
				    flex : 3
			    }, {
				    xtype : 'form',

				    bodyStyle : "background:#f8f8f8;",

				    shadow : 'frame',
				    shadowOffset : 20,
				    reference : 'p_form',
				    width : 415,
				    // height : 300,
				    bodyPadding : '20 20',
				    layout : {
					    type : 'vbox'
				    },
				    defaults : {
					    margin : '5 0',
					    width : '100%'
				    },
				    items : [{
					        xtype : 'label',
					        text : '登录到您的帐户'
				        }, {
					        xtype : 'textfield',
					        name : 'usercode',
					        height : 55,
					        hideLabel : true,
					        allowBlank : false,
					        cls : 'auth-textbox',
					        emptyText : '用户名称',
					        triggers : {
						        clear : false,
						        user : {
							        cls : 'x-fa fa-user login-textfield-triggers'
						        }
					        },
					        listeners : {
						        specialkey : 'onUserNameEnterKey',
						        render : 'onFieldRender'
					        }
				        }, {
					        xtype : 'textfield',
					        reference : 't_password',
					        name : 'password',
					        height : 55,
					        hideLabel : true,
					        emptyText : '用户密码',
					        inputType : 'password',
					        allowBlank : false,
					        triggers : {
						        clear : false,
						        user : {
							        cls : 'x-fa fa-lock login-textfield-triggers'
						        }
					        },
					        listeners : {
						        specialkey : 'onPasswordEnterKey',
						        render : 'onFieldRender'
					        }
				        }, {
					        xtype : 'container',
					        layout : 'hbox',
					        items : [{
						            xtype : 'checkboxfield',
						            name : 'keepusername',
						            flex : 1,
						            cls : 'form-panel-font-color rememberMeCheckbox',
						            height : 30,
						            checked : true,
						            inputValue : '1',
						            uncheckedValue : "0",
						            boxLabel : '记住用户名'
					            }, {
						            xtype : 'checkboxfield',
						            name : 'keeppassword',
						            flex : 1,
						            cls : 'form-panel-font-color rememberMeCheckbox',
						            height : 30,
						            inputValue : '1',
						            uncheckedValue : "0",
						            boxLabel : '记住密码'
					            }]
				        }, {
					        xtype : 'button',
					        name : 'loginButton',
					        scale : 'large',
					        height : 50,
					        iconAlign : 'right',
					        iconCls : 'x-fa fa-sign-in',
					        text : '登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;',
					        action : 'login',
					        listeners : {
						        click : 'onLoginButton'
					        }
				        }, {
					        xtype : 'label',
					        html : "<a href='#'>我把密码给忘了</a>",
					        listeners : {
						        render : function(label){
							        label.getEl().on('click', function(){
								          EU.toastInfo(cfg.systeminfo.forgetpassword);
							          })
						        }
					        }
				        }]
			    }, {
				    xtype : 'container',
				    flex : 6
			    }, {

				    xtype : 'label',
				    style : 'text-align : center;font-size: 1.2em;',
				    html : '用户单位:' + cfg.company.companyname + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;服务单位:'
				        + cfg.company.servicedepartment + '&nbsp;&nbsp;' + cfg.company.servicemen + '&nbsp;&nbsp;'
				        + cfg.company.servicetelnumber + '<br/><br/>©' + cfg.systeminfo.copyrightinfo
				        + '&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;(Ver:' + cfg.systeminfo.systemversion + ')',
				    width : '100%'

			    }, {
				    xtype : 'container',
				    flex : 2
			    }]
		}];
		me.callParent(arguments);
	}
}
);