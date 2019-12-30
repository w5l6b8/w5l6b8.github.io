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

Ext.define('app.view.platform.module.toolbar.widget.Export', {
	  extend : 'Ext.button.Split',
	  alias : 'widget.modulegridexportbutton',

	  config : {
		  moduleInfo : undefined
	  },

	  icon : 'images/button/excel.png',
	  // iconCls : 'x-fa fa-file-excel-o',
	  handler : 'onExportExeclButtonClick',

	  initComponent : function(){
		  var me = this;
		  me.menu = [{
			      text : '列表导出至excel文档',
			      icon : 'images/button/excel.png',
			      handler : 'onExportExeclButtonClick'
		      }, {
			      text : '当前页记录导出至excel文档',
			      icon : 'images/button/excel.png',
			      handler : 'onExportCurrentPageExeclButtonClick'
		      }, '-', {
			      text : '列表导出至pdf文档',
			      icon : 'images/button/pdf.png',
			      handler : 'onExportPdfButtonClick'
		      }, {
			      text : '当前页记录导出至pdf文档',
			      icon : 'images/button/pdf.png',
			      handler : 'onExportCurrentPagePdfButtonClick'
		      }];
		  if (me.moduleInfo.fDataobject.excelschemes) {
			  me.menu.push('-');
			  Ext.each(me.moduleInfo.fDataobject.excelschemes, function(scheme){
				    me.menu.push({
					      text : scheme.title,
					      schemeid : scheme.schemeid,
					      iconCls : scheme.iconcls,
					      icon : scheme.iconurl,
                handler : 'onExcelSchemeItemClick'
				      })
			    })
		  }
		  me.menu.push('-');
		  me.menu.push({
			    text : '列表导出设置',
			    menu : [{
				        xtype : 'checkbox',
				        boxLabel : '无背景色',
				        reference : 'colorless'
			        }, {
				        xtype : 'checkbox',
				        boxLabel : '以万元为单位',
				        reference : 'usemonetary'
			        }, {
				        xtype : 'checkbox',
				        boxLabel : '无总计',
				        reference : 'sumless'
			        }]
		    });

		  me.callParent(arguments);
	  }
  })
