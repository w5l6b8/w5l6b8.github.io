/**
 * 主控框架的viewModel

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
Ext.define('app.view.platform.central.CentralModel', {
	  extend : 'Ext.app.ViewModel',

	  alias : 'viewmodel.central',
	  requires : ['Ext.util.Cookies'],
	  mixins : {
		  menuModel : 'app.view.platform.central.viewModel.MenuModel',
		  modulesModel : 'app.view.platform.central.viewModel.ModulesModel'
	  },

	  constructor : function(){
		  var me = this;
		  me.mixins.menuModel.init.call(me);
		  me.mixins.modulesModel.init.call(me);
		  // 这个是暂时用的，extjs4 里面有好多的 app.modules 的引用，这里先赋值给它
		  app.modules = me;

		  // 这一句是关键，如果没有的话，this还没有初始化完成,下面的Ext.apply(me.data,....)这句就会出错
		  me.callParent(arguments);

		  me.localStore = new Ext.util.LocalStorage({
			    id : CU.getContextPath() + '/systemSetting'
		    });

		  // 'default' or int 0,1,2
		  var centerTabRotation = me.localStore.getItem('centerTabRotation', 'default');
		  if (centerTabRotation !== 'default') {
			  centerTabRotation = parseInt(centerTabRotation);
		  }
		  me.set({
			    _menuType : me.localStore.getItem('menuType', 'toolbar'),
			    _borderType : me.localStore.getItem('borderType', 'normal'),

			    _centerTabPosition : me.localStore.getItem('centerTabPosition', 'top'),
			    _centerTabRotation : centerTabRotation,
			    _monetary : me.localStore.getItem('monetary', 'tenthousand'),
			    _monetaryposition : me.localStore.getItem('monetaryposition', 'behindnumber'),
			    _autoColumnMode : me.localStore.getItem('autoColumnMode', 'firstload'),
			    _autoselectrecord : me.localStore.getItem('autoselectrecord', 'everyload'),
			    _selModel : me.localStore.getItem('selModel', 'checkboxmodel-MULTI'),
			    _rowdblclick : me.localStore.getItem('rowdblclick', 'display'),

			    _navigateMode : me.localStore.getItem('navigateMode', 'tree'),

			    _validbeforenew : me.localStore.getItem('validbeforenew', 'mark*'),
			    _msgTarget : me.localStore.getItem('msgTarget', 'qtip'),

			    _fieldtooltip : me.localStore.getItem('fieldtooltip', 'on'),

			    _displayparentbutton : me.localStore.getItem('displayparentbutton', 'off'),

			    _afternewsave : me.localStore.getItem('afternewsave', 'default'),
			    _aftereditsave : me.localStore.getItem('aftereditsave', 'default'),
			    _maxOpenTab : me.localStore.getItem('maxOpenTab', 8)

		    });
		  me.notify();
		  // 同步调用取得系统参数
      me.data.company = cfg.company;
      me.data.systeminfo = cfg.systeminfo;
		  Ext.apply(me.data.userInfo, cfg.sub);
		  EU.RS({
			    url : 'platform/systemframe/getmenutree.do',
			    async : false, // 同步
			    callback : function(menu){
				    me.data.menus = menu;
				    me.data.leafmenus = [];
				    me.getLeafMenus(me.data.menus, me.data.leafmenus);
			    }
		    })
	  },

	  getLeafMenus : function(menus, leafmenus){
		  var me = this;
		  Ext.each(menus, function(menu){
			    if (menu.children && menu.children.length > 0) {
				    me.getLeafMenus(menu.children, leafmenus);
			    } else {
				    leafmenus.push(menu);
			    }
		    })
	  },

	  // 根据objectid 来取得menuitem
	  getMenuFromObjectid : function(objectid){
		  var me = this,
			  menuitem = null;
		  Ext.each(me.data.leafmenus, function(menu){
			    if (menu.objectid == objectid) {
				    menuitem = menu;
				    return false;
			    }
		    })
		  return menuitem;
	  },

	  data : {

		  _menuType : 'toolbar', // 菜单的位置，'button' , 'toolbar' , 'tree'
		  _borderType : 'normal',

		  _centerTabPosition : 'top', // 'top' , 'left' , 'bottom', 'right'
		  _centerTabRotation : 'default', // 'default' , 0 , 1 , 2

		  _monetary : 'tenthousand', // 数值或金额的显示单位，默认万元
		  _monetaryposition : 'behindnumber', // 金额单位放置位置
		  _autoColumnMode : 'firstload', // 列宽自动调整,'firstload','everyload','disable'
		  _autoselectrecord : 'everyload', // 加载数据后是否自动选择一条，'everyload','onlyone','disable'
		  _navigateMode : 'tree', // 导航显示模式，tree, treegrid

		  _validbeforenew : 'mark*',
		  _msgTarget : 'qtip',
		  _fieldtooltip : 'on',
		  _displayparentbutton : 'off',

		  _afternewsave : 'default',
		  _aftereditsave : 'default',

		  _rowdblclick : 'display',
		  _selModel : 'checkboxmodel-MULTI',
		  pageSize : 20,

		  _maxOpenTab : 8, // 主tabPanel中最多打开的tab页面数

		  // 存放所有的模块的定义信息，管理由 moudlesController 进行管理
		  modules : new Ext.util.MixedCollection(),
		  // 存放所有的查询分组的panel,在关闭了以后，下次打开，不重新生成，在这里取得
		  reportGroups : new Ext.util.MixedCollection(),

		  userInfo : {}

	  },

	  /**
		 * 把所有的设置设为初始值
		 */
	  resetConfig : function(){
		  var me = this;
		  me.set('menuType', 'toolbar');
		  me.set('borderType', 'normal');

		  me.set('centerTabPosition', 'top');
		  me.set('centerTabRotation', 'default');

		  me.set('monetary', 'tenthousand');
		  me.set('monetaryposition', 'behindnumber');
		  me.set('autoColumnMode', 'firstload');
		  me.set('autoselectrecord', 'everyload');
		  me.set('rowdblclick', 'display');
		  me.set('selModel', 'checkboxmodel-MULTI');

		  me.set('validbeforenew', 'mark*');
		  me.set('msgTarget', 'qtip');

		  me.set('fieldtooltip', 'on');

		  me.set('displayparentbutton', 'off');

		  me.set('afternewsave', 'default');
		  me.set('aftereditsave', 'default');

		  me.set('maxOpenTab', 8);

	  },

	  formulas : {

		  menuType : {
			  get : function(get){
				  return get('_menuType');
			  },
			  set : function(value){
				  this.set({
					    _menuType : value
				    });
				  this.localStore.setItem('menuType', value);
			  }
		  },
		  // 布局方式，标准，紧凑
		  borderType : {
			  get : function(get){
				  return get('_borderType');
			  },
			  set : function(value){
				  this.set({
					    _borderType : value
				    });
				  this.localStore.setItem('borderType', value);
			  }
		  },
		  // 当菜单方式选择的按钮按下后，这里的formulas会改变，然后会影响相应的bind的数据
		  isButtonMenu : function(get){
			  return get('_menuType') == 'button';
		  },
		  isToolbarMenu : function(get){
			  return get('_menuType') == 'toolbar';
		  },
		  isTreeMenu : function(get){
			  return get('_menuType') == 'tree';
		  },

		  maxOpenTab : {
			  get : function(get){
				  return get('_maxOpenTab');
			  },
			  set : function(value){
				  this.set({
					    _maxOpenTab : value
				    });
				  this.localStore.setItem('maxOpenTab', value);
			  }
		  },

		  centerTabPosition : {
			  get : function(get){
				  return get('_centerTabPosition');
			  },
			  set : function(value){
				  this.set({
					    _centerTabPosition : value
				    });
				  this.localStore.setItem('centerTabPosition', value);
			  }
		  },
		  centerTabRotation : {
			  get : function(get){
				  return get('_centerTabRotation');
			  },
			  set : function(value){
				  this.set({
					    _centerTabRotation : value
				    });
				  this.localStore.setItem('centerTabRotation', value);
			  }
		  },

		  monetary : {
			  get : function(get){
				  return get('_monetary');
			  },
			  set : function(value){
				  this.set({
					    _monetary : value
				    });
				  this.localStore.setItem('monetary', value);
			  }
		  },

		  monetaryposition : {
			  get : function(get){
				  return get('_monetaryposition');
			  },
			  set : function(value){
				  this.set({
					    _monetaryposition : value
				    });
				  this.localStore.setItem('monetaryposition', value);
			  }
		  },

		  autoColumnMode : {
			  get : function(get){
				  return get('_autoColumnMode');
			  },
			  set : function(value){
				  this.set({
					    _autoColumnMode : value
				    });
				  this.localStore.setItem('autoColumnMode', value);
			  }
		  },

		  autoselectrecord : {
			  get : function(get){
				  return get('_autoselectrecord');
			  },
			  set : function(value){
				  this.set({
					    _autoselectrecord : value
				    });
				  this.localStore.setItem('autoselectrecord', value);
			  }
		  },

		  rowdblclick : {
			  get : function(get){
				  return get('_rowdblclick');
			  },
			  set : function(value){
				  this.set({
					    _rowdblclick : value
				    });
				  this.localStore.setItem('rowdblclick', value);
			  }
		  },

		  selModel : {
			  get : function(get){
				  return get('_selModel');
			  },
			  set : function(value){
				  this.set({
					    _selModel : value
				    });
				  this.localStore.setItem('selModel', value);
			  }
		  },

		  navigateMode : {
			  get : function(get){
				  return get('_navigateMode');
			  },
			  set : function(value){
				  this.set({
					    _navigateMode : value
				    });
				  this.localStore.setItem('navigateMode', value);
			  }
		  },

		  validbeforenew : {
			  get : function(get){
				  return get('_validbeforenew');
			  },
			  set : function(value){
				  this.set({
					    _validbeforenew : value
				    });
				  this.localStore.setItem('validbeforenew', value);
			  }
		  },

		  msgTarget : {
			  get : function(get){
				  return get('_msgTarget');
			  },
			  set : function(value){
				  this.set({
					    _msgTarget : value
				    });
				  this.localStore.setItem('msgTarget', value);
			  }
		  },

		  fieldtooltip : {
			  get : function(get){
				  return get('_fieldtooltip');
			  },
			  set : function(value){
				  this.set({
					    _fieldtooltip : value
				    });
				  this.localStore.setItem('fieldtooltip', value);
			  }
		  },

		  displayparentbutton : {
			  get : function(get){
				  return get('_displayparentbutton');
			  },
			  set : function(value){
				  this.set({
					    _displayparentbutton : value
				    });
				  this.localStore.setItem('displayparentbutton', value);
			  }
		  },

		  afternewsave : {
			  get : function(get){
				  return get('_afternewsave');
			  },
			  set : function(value){
				  this.set({
					    _afternewsave : value
				    });
				  this.localStore.setItem('afternewsave', value);
			  }
		  },

		  aftereditsave : {
			  get : function(get){
				  return get('_aftereditsave');
			  },
			  set : function(value){
				  this.set({
					    _aftereditsave : value
				    });
				  this.localStore.setItem('aftereditsave', value);
			  }
		  }
	  }
  });
