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

Ext.define('app.view.platform.module.attachment.AttachmentUtils', {
	  alternateClassName : 'AttachmentUtils',

	  requires : ['app.view.platform.module.attachment.Module'],

	  statics : {

		  showInCenterRegion : function(parentFilter){
			  var me = this,
				  isCreate = parentFilter.moduleName !== me.lastModuleName;
			  me.lastModuleName = parentFilter.moduleName;
			  app.viewport.down('maincenter').fireEvent('showattachment', me.getAttachmentTabPanel(parentFilter, isCreate),
			    isCreate
			  );
		  },

		  getAttachmentTabPanel : function(parentFilter, isCreate){
			  var me = this,
				  showgrid = parentFilter.showGrid;
			  delete parentFilter.showGrid;
			  if (isCreate && me.attachmentTabPanel) {
				  me.attachmentTabPanel.destroy();
				  delete me.attachmentTabPanel;
			  };
			  if (!me.attachmentTabPanel) {
				  me.attachmentTabPanel = Ext.create('app.view.platform.module.attachment.Module', {
					    parentFilter : parentFilter,
					    showgrid : showgrid
				    })
			  } else {
				  me.attachmentTabPanel.setParentFilter(parentFilter);
			  }
			  me.attachmentTabPanel.down('tabpanel#moduletabpanel').setActiveTab(showgrid ? 1 : 0);
			  return me.attachmentTabPanel;
		  },

		  downloadall : function(objectid, recordid){
			  window.location.href = 'platform/attachment/downloadall.do?moduleName=' + objectid + '&idkey=' + recordid;
		  }

	  }
  })
