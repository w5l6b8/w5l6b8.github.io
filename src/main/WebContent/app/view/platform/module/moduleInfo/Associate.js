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

Ext.define('app.view.platform.module.moduleInfo.Associate', {

	  extend : 'Ext.Mixin',

	  getEastAssociateInfo : function(){
		  return this.getAssociateInfo('east');
	  },
	  getSouthAssociateInfo : function(){
		  return this.getAssociateInfo('south');
	  },
	  getWestAssociateInfo : function(){
		  return this.getAssociateInfo('west');
	  },

	  getAssociateInfo : function(region){
		  var me = this,
			  assoc = me.fDataobject.fovDataobjectassociates;
		  if (Ext.isArray(assoc)) {
			  for (var i = 0; i < assoc.length; i++)
				  if (assoc[i].region == region) return assoc[i];
		  } else return null;
	  }

  })