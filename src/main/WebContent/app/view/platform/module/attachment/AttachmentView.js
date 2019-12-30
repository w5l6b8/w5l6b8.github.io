/**
 * 附件预览
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


Ext.define('app.view.platform.module.attachment.AttachmentView', {
	  extend : 'Ext.view.View',
	  alias : 'widget.attachmentview',
	  baseCls : 'images-view',
	  border : 1,
	  tpl : new Ext.XTemplate('<tpl for=".">', '<div class="thumb-wrap">',
	    '<div class="thumb"><span class="imagecontainer"><img src="', '<tpl if="previewdata">',
	    'data:image/png;base64,{previewdata}', '<tpl else>', 'images/attachment/no.png', '</tpl>',
	    '" data-qtip="{title}<br/>' + '<tpl if="filename"> {filename}', '<tpl else>&nbsp;', '</tpl>', '"/></span></div>',
	    '<span class="title">{title}</span>', '</div>', '</tpl>', '<div class="x-clear"></div>'
	  ),

	  listeners : {
		  selectionchange : 'onViewSelectionChange',
		  itemdblclick : 'onViewSelectionDblClick',
      afterrender : 'onViewSelectionAfterRender'
	  },

	  trackOver : true,
	  overItemCls : 'x-item-over',
	  itemSelector : 'div.thumb-wrap',
	  autoScroll : true,

	  initComponent : function(){
		  var me = this;
		  me.callParent(arguments);
	  }

  });
