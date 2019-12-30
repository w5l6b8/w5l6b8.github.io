
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
Ext.define('app.view.platform.central.menu.ListTreeMenuStore', {
	  extend : 'Ext.data.TreeStore',

	  storeId : 'NavigationTree',

	  fields : [{
		      name : 'text'
	      }],

	  root : {
		  expanded : true,
      
      children : app.viewport.getViewModel().getTreeMenus(),
      
		  children1 : [{
			      text : 'Dashboard',
			      iconCls : 'x-fa fa-desktop',
			      rowCls : 'nav-tree-badge nav-tree-badge-new',
			      viewType : 'admindashboard',
			      routeId : 'dashboard', // routeId defaults to viewType
			      leaf : true
		      }, {
			      text : 'Email',
			      iconCls : 'x-fa fa-send',
			      rowCls : 'nav-tree-badge nav-tree-badge-hot',
			      viewType : 'email',
			      leaf : false,
                      children : [{
                  text : 'Blank Page',
                  iconCls : 'x-fa fa-file-o',
                  viewType : 'pageblank',
                  leaf : false,

                  children : [{
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }, {
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }, {
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }]

                },

                {
                  text : '404 Error',
                  iconCls : 'x-fa fa-exclamation-triangle',
                  viewType : 'page404',
                  leaf : false,

                  children : [{
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }, {
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }, {
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }]
                }, {
                  text : '500 Error',
                  iconCls : 'x-fa fa-times-circle',
                  viewType : 'page500',
                  leaf : true
                }, {
                  text : 'Lock Screen',
                  iconCls : 'x-fa fa-lock',
                  viewType : 'lockscreen',
                  leaf : true
                },

                {
                  text : 'Login',
                  iconCls : 'x-fa fa-check',
                  viewType : 'login',
                  leaf : true
                }, {
                  text : 'Register',
                  iconCls : 'x-fa fa-pencil-square-o',
                  viewType : 'register',
                  leaf : true
                }, {
                  text : 'Password Reset',
                  iconCls : 'x-fa fa-lightbulb-o',
                  viewType : 'passwordreset',
                  leaf : true
                }]
		      }, {
			      text : 'Profile',
			      iconCls : 'x-fa fa-user',
			      viewType : 'profile',
			      leaf : false,
                      children : [{
                  text : 'Blank Page',
                  iconCls : 'x-fa fa-file-o',
                  viewType : 'pageblank',
                  leaf : false,

                  children : [{
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }, {
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }, {
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }]

                },

                {
                  text : '404 Error',
                  iconCls : 'x-fa fa-exclamation-triangle',
                  viewType : 'page404',
                  leaf : false,

                  children : [{
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }, {
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }, {
                        text : 'Blank Page',
                        iconCls : 'x-fa fa-file-o',
                        viewType : 'pageblank',
                        leaf : true
                      }]
                }, {
                  text : '500 Error',
                  iconCls : 'x-fa fa-times-circle',
                  viewType : 'page500',
                  leaf : true
                }, {
                  text : 'Lock Screen',
                  iconCls : 'x-fa fa-lock',
                  viewType : 'lockscreen',
                  leaf : true
                },

                {
                  text : 'Login',
                  iconCls : 'x-fa fa-check',
                  viewType : 'login',
                  leaf : true
                }, {
                  text : 'Register',
                  iconCls : 'x-fa fa-pencil-square-o',
                  viewType : 'register',
                  leaf : true
                }, {
                  text : 'Password Reset',
                  iconCls : 'x-fa fa-lightbulb-o',
                  viewType : 'passwordreset',
                  leaf : true
                }]
		      }, {
			      text : 'Search results',
			      iconCls : 'x-fa fa-search',
			      viewType : 'searchresults',
			      leaf : true
		      }, {
			      text : 'FAQ',
			      iconCls : 'x-fa fa-question',
			      viewType : 'faq',
			      leaf : true
		      }, {
			      text : 'Pages',
			      iconCls : 'x-fa fa-leanpub',
			      expanded : false,
			      selectable : false,
			      // routeId: 'pages-parent',
			      // id: 'pages-parent',

			      children : [{
				          text : 'Blank Page',
				          iconCls : 'x-fa fa-file-o',
				          viewType : 'pageblank',
				          leaf : false,

				          children : [{
					              text : 'Blank Page',
					              iconCls : 'x-fa fa-file-o',
					              viewType : 'pageblank',
					              leaf : true
				              }, {
					              text : 'Blank Page',
					              iconCls : 'x-fa fa-file-o',
					              viewType : 'pageblank',
					              leaf : true
				              }, {
					              text : 'Blank Page',
					              iconCls : 'x-fa fa-file-o',
					              viewType : 'pageblank',
					              leaf : true
				              }]

			          },

			          {
				          text : '404 Error',
				          iconCls : 'x-fa fa-exclamation-triangle',
				          viewType : 'page404',
				          leaf : false,

				          children : [{
					              text : 'Blank Page',
					              iconCls : 'x-fa fa-file-o',
					              viewType : 'pageblank',
					              leaf : true
				              }, {
					              text : 'Blank Page',
					              iconCls : 'x-fa fa-file-o',
					              viewType : 'pageblank',
					              leaf : true
				              }, {
					              text : 'Blank Page',
					              iconCls : 'x-fa fa-file-o',
					              viewType : 'pageblank',
					              leaf : true
				              }]
			          }, {
				          text : '500 Error',
				          iconCls : 'x-fa fa-times-circle',
				          viewType : 'page500',
				          leaf : true
			          }, {
				          text : 'Lock Screen',
				          iconCls : 'x-fa fa-lock',
				          viewType : 'lockscreen',
				          leaf : true
			          },

			          {
				          text : 'Login',
				          iconCls : 'x-fa fa-check',
				          viewType : 'login',
				          leaf : true
			          }, {
				          text : 'Register',
				          iconCls : 'x-fa fa-pencil-square-o',
				          viewType : 'register',
				          leaf : true
			          }, {
				          text : 'Password Reset',
				          iconCls : 'x-fa fa-lightbulb-o',
				          viewType : 'passwordreset',
				          leaf : true
			          }]
		      }, {
			      text : 'Widgets',
			      iconCls : 'x-fa fa-flask',
			      viewType : 'widgets',
			      leaf : true
		      }, {
			      text : 'Forms',
			      iconCls : 'x-fa fa-edit',
			      viewType : 'forms',
			      leaf : true
		      }, {
			      text : 'Charts',
			      iconCls : 'x-fa fa-pie-chart',
			      viewType : 'charts',
			      leaf : true
		      }]
	  }
  });
