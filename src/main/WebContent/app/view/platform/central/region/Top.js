/**
 * 系统主页的顶部区域，主要放置系统名称，菜单，和一些快捷按钮
 * 
 * @author jiangfeng
 * 
 * www.jhopesoft.com
 * 
 * jfok1972@qq.com
 * 
 * 2017-06-01
 * 
 */
Ext.define('app.view.platform.central.region.Top', {

	extend : 'Ext.toolbar.Toolbar',

	alias : 'widget.maintop', // 定义了这个组件的xtype类型为maintop

	requires : [ 'expand.ux.ButtonTransparent', 'app.view.platform.central.menu.ButtonMainMenu',
			'app.view.platform.central.menu.SettingMenu', 'Ext.toolbar.Spacer', 'Ext.toolbar.Fill', 'Ext.toolbar.Separator',
			'Ext.Img', 'Ext.form.Label', 'app.view.platform.central.widget.FavoriteButton', 'expand.ux.UserFavicon',
			'app.view.platform.central.region.ButtomController' ],

	defaults : {
		xtype : 'buttontransparent'
	},
	controller : 'buttom',
	style : 'border-bottom:0px solid #f0f0f0;' + 'background-color:#f0f0f0;padding-right:30px;',
	// border : '0 0 1 0',

	height : 50,// 42,

	listeners : {
		render : function(toolbar) {
			toolbar.updateLayout();
		}
	},

	initComponent : function() {
		this.items = [ {
			width : 24,
			height : 24,
			xtype : 'image',
			src : 'images/system/favicon24.png'
		}, {
			xtype : 'label',
			// 系统名称
			bind : {
				text : '{systeminfo.systemname}'
			},
			style : 'font-size:20px;color:blank;'
		}, {
			xtype : 'label',
			style : 'color:grey;',
			// 版本号,MVVM的用法，用bind注入，
			bind : {
				// 传统的用法，这个用法会在显示界面的时候，把数据显示好，用上面的方法在显示界面后，
				// 再进行变更，会看得出延时的过程
				// text :
				// '('+this.up('appcentral').getViewModel().get('systeminfo.tf_systemVersion')
				// +')'
				text : '(Ver:{systeminfo.systemversion})'
			}
		}, '->', {
			xtype : 'buttonmainmenu',
			hidden : true,
			bind : {
				hidden : '{!isButtonMenu}'
			}
		}, ' ', ' ', {
			text : '首页',
			iconCls : 'x-fa fa-home',
			handler : 'onHomePageButtonClick'
		}, {
			xtype : 'favoritebutton',
			reference : 'favoritebutton'
		}, {
			xtype : 'settingmenu'
		}
//		, {
//			text : '帮助',
//			iconCls : 'x-fa fa-question',
//			handler : function(button) {
//
//			}
//		}, {
//			text : '关于',
//			iconCls : 'x-fa fa-exclamation-circle',
//			handler : function() {
//			}
//		}, '->', '->', {
//			text : '搜索',
//			iconCls : 'x-fa fa-search',
//			handler : function() {
//			}
//		}
		,
		{
			cls : 'delete-focus-bg',
			iconCls : 'x-fa fa-bell',
			tooltip : '消息提醒',
			reference : 'b_tips',
			handler : 'onMessage'
		}, ' ', {
			bind : {
				text : '{userInfo.username}',
				hidden : '{!userInfo.username}'
			},
			menu : [ {
				text : '我的信息',
				handler : 'onUserInfoClick',
				iconCls : 'x-fa fa-info-circle'
			}, {
				text : '我的角色设置',
				handler : 'onUserRolesClick',
				iconCls : 'x-fa fa-users'
			}, {
				text : '我的操作权限',
				handler : 'onUserPopedomClick',
				iconCls : 'x-fa fa-bell-o'
			}, '-', {
				text : '我的登录日志',
				handler : 'onLoginInfoClick'
			}, {
				text : '我的操作日志',
				handler : 'onOperateInfoClick'
			}, '-', {
				text : '修改登录密码',
				handler : 'onChangePasswordClick',
				iconCls : 'x-fa fa-user-secret'
			}, '-', {
				text : '注销登录',
				handler : 'onLogoutClick',
				iconCls : 'x-fa fa-sign-out'
			} ]
		}, {
			xtype : 'userfavicon'
		}, ' ', {
			tooltip : '注销',
			iconCls : 'x-fa fa-sign-out',
			handler : 'logout'
		} ];
		this.callParent(arguments);
	}
});