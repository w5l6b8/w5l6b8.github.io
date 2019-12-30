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

Ext.define('app.view.platform.module.toolbar.widget.New', {
	  extend : 'Ext.button.Split',
	  alias : 'widget.newbutton',

	  text : '新增',
	  iconCls : 'x-fa fa-plus',
	  itemId : 'new',
	  menu : {},
	  handler : 'onNewButtonClick',

	  listeners : {
		  // click : 'addRecord', // 这里不要用handler，而要用click,因为下面要发送click事件
		  // 删除按钮在渲染后加入可以Drop的功能
		  render : function(button){
			  // 可以使Grid中选中的记录拖到到此按钮上来进行复制新增
			  button.dropZone = new Ext.dd.DropZone(button.getEl(), {
				    // 此处的ddGroup需要与Grid中设置的一致
				    ddGroup : 'DD_' + button.up('tablepanel').objectName,
				    getTargetFromEvent : function(e){
					    return e.getTarget('');
				    },
				    // 用户拖动选中的记录经过了此按钮
				    onNodeOver : function(target, dd, e, data){
					    return Ext.dd.DropZone.prototype.dropAllowed;
				    },
				    // 用户放开了鼠标键，删除记录
				    onNodeDrop : function(target, dd, e, data){
					    var b = button.menu.down('#newwithcopy');
					    b.fireEvent('click', b);
				    }
			    })
		  }
	  },

	  initComponent : function(){

		  if (!this.showtext) {
			  delete this.text;
			  this.tooltip = '新增记录';
		  }

		  var items = [{
			      text : '复制新增',
			      tooltip : '新增时先将当前选中的记录值复制到新记录中',
			      itemId : 'newwithcopy',
			      listeners : {
				      click : 'onNewWithCopyButtonClick'
			      },
			      iconCls : 'x-fa fa-files-o'
		      }, '-', {
			      text : '新增向导',
			      disabled : true
		      }];

		  // 是否有单条记录导入新增
		  if (this.module) {
			  var adds = this.module.moduleExcelRecordAdds;
			  if (adds && adds.length > 0) {
				  items.push('-');
				  items.push({
					    text : '上传Excel表单条新增',
					    tooltip : '根据指定的excel表添好数据后，上传新增一条记录',
					    itemId : 'uploadinsertexcelrecord',
					    methodId : adds[0].tf_id,
					    remark : adds[0].tf_remark
				    })
			  }

			  if (this.module && this.module.tf_allowInsertExcel) {
				  items.push('-');
				  items.push({
					    text : '上传Excel表批量新增',
					    tooltip : '根据下载的Excel表中的要求添加数据后，上传批量新增数据',
					    itemId : 'uploadinsertexcel'
				    })
			  }
		  }
		  this.menu.items = items;
		  this.callParent(arguments);
	  }

  })
