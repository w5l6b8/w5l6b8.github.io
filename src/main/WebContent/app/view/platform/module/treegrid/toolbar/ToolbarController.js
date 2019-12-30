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

Ext.define('app.view.platform.module.treegrid.toolbar.ToolbarController', {
	  extend : 'Ext.app.ViewController',
	  alias : 'controller.gridtreetoolbar',

	  onUpdateOrderButtonClick : function(){
		  var grid = this.getView().grid;
		  if (!grid.moduleInfo.fDataobject.orderfield) {
			  EU.toastWarn('当前实体对象没有设置顺序号字段!');
			  return;
		  }
		  var selected = grid.getFirstSelectedRecord();
		  if (selected) {

			  Ext.MessageBox.confirm('确定更新顺序号', '确定要将选中节点同级按当前顺序重新排列吗?', function(btn){
				    if (btn == 'yes') {
					    var pnode = selected.parentNode;
					    var childs = [];
					    pnode.eachChild(function(node){
						      childs.push(node.getIdValue())
					      });
					    EU.RS({
						      url : 'platform/dataobject/updateorderno.do',
						      params : {
							      moduleName : grid.objectName,
							      ids : childs.join(',')
						      },
						      callback : function(result){
							      if (result.success) {
								      var i = 0;
								      pnode.eachChild(function(node){
									        node.set(result.tag, result.msg[i++].text);
									        node.commit();
								        })
								      EU.toastInfo('将选中节点同级按当前顺序重新排列已完成！')
							      } else {
								      EU.toastWarn(result.msg);
							      }
						      }
					      })
				    }
			    })
		  }
	  },

	  onSetNotLeafButtonClick : function(){

		  var grid = this.getView().grid;
		  var selected = grid.getFirstSelectedRecord();
		  if (selected && selected.get('leaf') == true) {
			  selected.set('children', []);
			  selected.set('leaf', false);
		  }

	  },

	  onCollapseButtonClick : function(){
		  this.getView().grid.collapseAll();
		  this.getView().grid.setLevel(1);
	  },

	  onExpandALevelButtonClick : function(){
		  this.getView().grid.expandToNextLevel();
	  }
  });
