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

Ext.define('app.view.platform.module.associate.TabPanelController', {
	  extend : 'Ext.app.ViewController',
	  alias : 'controller.associatetabpanelcontroller',

	  requires : ['app.view.platform.module.associate.SubModuleOptionWindow',
	      'app.view.platform.module.associate.SubFormOptionWindow'],

	  saveAssociateSetting : function(button){
		  var me = this,
			  form = button.up('form');
		  form.getForm().submit({
			    url : 'platform/scheme/associate/savesetting.do',
			    params : {
				    savestr : Ext.encode(form.getValues())
			    },
			    callback : function(options, success, response){
				    EU.toastWarn("模块关联区域设置保存成功!");
				    Ext.apply(me.getView().associateInfo, form.getValues());
			    }
		    });
	  },

	  onSettingButtonClick : function(button){
		  var me = this,
			  view = me.getView(),
			  menu = me.lookupReference('settingmenu');
		  menu.showBy(button);

	  },

	  onAddUserDefineButtonClick : function(){
		  var me = this,
			  view = me.getView();

		  Ext.Msg.prompt("自定义控件名称", "录入类或xtype名称", function(btn, text){
			    if (btn = 'ok') {
				    EU.RS({
					      url : 'platform/scheme/associate/adduserdefine.do',
					      params : {
						      name : text,
						      region : view.region,
						      objectid : view.moduleInfo.fDataobject.objectid
					      },
					      target : view,
					      callback : function(result){
						      if (result.success) {
							      param = {
								      associatedetailid : result.tag,
								      xtype : text
							      }
							      view.fireEvent('userAddUserDefine', param);
						      }
					      }
				      })
			    }
		    }, this)

	  },

	  onAddChildModuleButtonClick : function(){
		  var view = this.getView(),
			  window = Ext.widget('setmoduleoptionwindow', {
				    moduleName : view.objectName,
				    objectid : view.moduleInfo.fDataobject.objectid,
				    region : view.region,
				    target : view
			    });
		  window.show();
	  },

	  onAddFormButtonClick : function(){
		  var view = this.getView(),
			  window = Ext.widget('setformoptionwindow', {
				    moduleName : view.objectName,
				    objectid : view.moduleInfo.fDataobject.objectid,
				    region : view.region,
				    target : view
			    });
		  window.show();
	  },

	  onAddAttachmentButtonClick : function(){
		  var me = this,
			  view = me.getView(),
			  params = {
				  objectid : view.moduleInfo.fDataobject.objectid,
				  region : view.region
			  },
			  apanel = view.down('panel#gridattachment');

		  if (apanel) view.setActiveTab(apanel)
		  else EU.RS({
			    url : 'platform/scheme/associate/addattachment.do',
			    target : view,
			    params : params,
			    callback : function(result){
				    if (result.success) {
					    params.associatedetailid = result.tag;
					    view.fireEvent('useraddattachment', params);
				    }
			    }
		    })
	  },

	  userAddAttachment : function(detail){
		  var view = this.getView(),
			  r = null,
			  gridselection = view.up('modulepanel').getModuleGrid().getSelectionModel().getSelection();
		  if (gridselection.length > 0) r = gridselection[0];
		  detail.subModuleActivated = true; // 新增的要设置这个，因为第一个加入的不会触发active事件
		  var attachment =
		      view.add(view.getAttachment(detail, (r == null ? 'null' : r.getIdValue()), (r == null ? '未定义'
		            : r.getTitleTpl())));
		  view.setActiveTab(attachment);
		  attachment.down('attachmentmodule').subModuleActivated = true;
	  },


	  userAddUserDefine : function(detail){
		  var me = this,
			  view = me.getView(),
			  userXtype = view.getXtype(detail);
		  view.setActiveTab(view.add(userXtype));
		  userXtype.deactivated = false;
		  if (me.lastrecord) {
			  userXtype.fireEvent('parentfilterchange', {
				    fieldvalue : me.lastrecord.getIdValue(),
				    text : me.lastrecord.getNameValue()
			    })
		  }
	  },

	  userAddSubModule : function(detail){
		  var view = this.getView(),
			  r = null,
			  gridselection = view.up('modulepanel').getModuleGrid().getSelectionModel().getSelection();
		  if (gridselection.length > 0) r = gridselection[0];
		  detail.subModuleActivated = true; // 新增的要设置这个，因为第一个加入的不会触发active事件
		  view.setActiveTab(view.add(view.getSubobject(detail, (r == null ? undefined : r.getIdValue()), (r == null
		        ? undefined : r.getTitleTpl()))));
	  },

	  userAddForm : function(detail){
		  var view = this.getView(),
			  form = view.getForm(detail);
		  form.down('form').initData(view.up('modulepanel').getModuleGrid());
		  view.setActiveTab(view.add(form));
	  },

	  onRemoveUserAssociate : function(tabpanel, removed){
		  if (removed.associatedetailid) {
			  EU.RS({
				    url : 'platform/scheme/associate/remove.do',
				    async : false,
				    params : {
					    detailid : removed.associatedetailid
				    },
				    callback : function(result){
					    if (result.success) {
						    EU.toastInfo('已将关联区域『' + removed.title + '』删除!');
					    }
				    }
			    })
		  }
	  },

	  onTabPanelExpand : function(){
		  var me = this,
			  view = me.getView();
		  if (me.cached == true) {
			  me.onGridSelectionChange(me.lastrecord);
			  me.cached = false;
		  }
		  // if (view.getActiveTab()) view.getActiveTab().updateLayout(); //
		  // 折叠后，pf改变了，grid没有刷新
	  },

	  onGridSelectionChange : function(record){
		  var me = this,
			  view = me.getView();
		  me.lastrecord = record;
		  if (view.collapsed) {
			  me.cached = true;
		  } else {
			  me.cached = false;
			  Ext.each(view.query('panel[parentModule=' + view.objectName + ']'), function(panel){
				    if (panel.xtype == 'attachmentmodule') {
					    panel.fireEvent('attachmentparentfilterchange', record ? {
						      record : record
					      } : {
						      record : null
					      });
				    } else if (panel.xtype == 'gridassociateform') {
					    panel.down('form').initData(view.up('modulepanel').getModuleGrid());
				    } else {
					    panel.fireEvent('parentfilterchange', record ? {
						      fieldvalue : record.getIdValue(),
						      text : record.getNameValue()
					      } : {
						      fieldvalue : 'null',
						      text : '未选中'
					      });
				    }
			    })
		  }
	  }

  })
