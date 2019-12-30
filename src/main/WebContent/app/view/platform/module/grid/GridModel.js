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

Ext.define('app.view.platform.module.grid.GridModel', {

	  extend : 'Ext.app.ViewModel',

	  alias : 'viewmodel.modulegrid',

	  data : {
      
		  module : {
			  toolbar : {
				  dock : 'top', // toolbar的位置,left,right,top,bottom
				  buttonScale : 'small', // 按钮的大小，small,medium,large
				  topbottomMode : 'normal', // toolbar在上面和下面时的显示模式，normal,compact
				  leftrightMode : 'compact', // toolbar在左边和右面时的显示模式，normal,compact
				  leftrightArrowAlign : 'bottom'
				  // toolbar在左边和右面时splitButton按钮的显示位置，right,bottom
			  }
		  }
	  },

	  constructor : function(param){
		  this.callParent(arguments);
	  }

  })
