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

Ext.define('app.view.platform.module.userFilter.BaseFilter', {
			extend : 'Ext.container.Container',
			alias : 'widget.basefilter',
			requires : ['app.utils.UserFilterUtils'],

			layout : 'hbox',
			config : {
				userfilter : undefined,
				filter : undefined
			},

			clearFilter : function() {
				var f = this.down('field#value');
				if (f)
					f.setValue(null);
				this.setFilter(null);
			},

			/**
			 * 给每一个字段加一个name,这样就可以保存在model中，reset的时候可以重置isdirty
			 */
			getName : function() {
				this.nameobject.orderno++;
				return 'name' + this.nameobject.orderno;
			}

		})