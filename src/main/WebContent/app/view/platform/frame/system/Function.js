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

Ext.define('app.view.platform.frame.system.Function',
		{
			alternateClassName : 'systemFunction',

			statics : {

				createOneToManyField : function(param) {
					var record = param.record;
					if (record.get('fieldrelation').toLowerCase() != 'manytoone') {
						EU.toastWarn('请选择一个多对一(manytoone)的字段！');
						return;
					}
					var mess = '模块『' + record.get('fieldtitle') + '』中建立『' + record.get('FDataobject.title') + '』的一对多关系';
					Ext.MessageBox.confirm('确定', '确定要在' + mess + '吗?', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								method : 'POST',
								url : 'platform/dataobjectfield/createonetomanyfield.do',
								params : {
									// fieldtype : record.get('fieldtype'),
									// linkedobjectid : record.get('FDataobject.objectid'),
									fieldid : record.get('fieldid')
								},
								success : function(response) {
									var result = Ext.decode(response.responseText, true);
									if (result.success) {
										Ext.Msg.show({
											title : '操作成功',
											message : mess + '已成功!',
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.INFO
										})
									} else {
										Ext.Msg.show({
											title : '操作失败',
											message : mess + '失败!<br/><br/>原因：' + result.msg,
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.INFO
										})
									}
								},
								failure : function(response) {
									Ext.Msg.alert('错误', mess + '失败!');
								}
							})
						}
					})

				},

				createManyToManyField : function(param) {
					var records = param.records, r1 = records[0], r2 = records[1];
					if (r1.get('FDataobject.objectid') != r2.get('FDataobject.objectid')) {
						EU.toastWarn('请选择相同模块下的二个字段！');
						return;
					}
					if (r1.get('fieldrelation').toLowerCase() != 'manytoone'
							|| r2.get('fieldrelation').toLowerCase() != 'manytoone') {
						EU.toastWarn('请选择二个多对一(manytoone)的字段！');
						return;
					}
					var mess = '模块『' + r1.get('fieldtitle') + '』和『' + r2.get('fieldtitle') + '』之间建立多对多关系';
					Ext.MessageBox.confirm('确定', '确定要在' + mess + '吗?', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								method : 'POST',
								url : 'platform/dataobjectfield/createmanytomanyfield.do',
								params : {
									fieldtype1 : r1.get('fieldtype'),
									fieldtype2 : r2.get('fieldtype'),
									linkedobjectid : r1.get('FDataobject.objectid')
								},
								success : function(response) {
									var result = Ext.decode(response.responseText, true);
									if (result.success) {
										Ext.Msg.show({
											title : '操作成功',
											message : mess + '已成功!',
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.INFO
										})
									} else {
										Ext.Msg.show({
											title : '操作失败',
											message : mess + '失败!<br/><br/>原因：' + result.msg,
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.INFO
										})
									}
								},
								failure : function(response) {
									Ext.Msg.alert('错误', mess + '失败!');
								}
							})
						}
					})
				},

				resetPassword : function(param) {
					var record = param.record;
					var mess = '用户『' + record.getTitleTpl() + '』的密码';
					Ext.MessageBox.confirm('确定重置', '确定要重置' + mess + '吗?', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								method : 'POST',
								url : 'platform/systemframe/resetpassword.do',
								params : {
									userid : record.get('userid')
								},
								success : function(response) {
									Ext.Msg.show({
										title : '密码重置成功',
										message : mess + '已重置为123456，请通知其尽快修改!',
										buttons : Ext.Msg.OK,
										icon : Ext.Msg.INFO
									})
								},
								failure : function(response) {
									Ext.Msg.alert('错误', mess + '重置失败!');
								}
							})
						}
					})
				},

				updateAdditionFuntionToCModule : function(param) {
					var record = param.record;
					var mess = '将『' + record.getTitleTpl() + '』更新到所有公司的模块功能里';
					Ext.MessageBox.confirm('更新附加功能', '确定要' + mess + '吗?', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								method : 'POST',
								url : 'platform/userrole/updateadditionfunctiontocmodule.do',
								params : {
									functionid : record.getIdValue()
								},
								success : function(response) {
									var result = Ext.decode(response.responseText, true);
									if (result.success) {
										Ext.Msg.show({
											title : '操作成功',
											message : mess + '成功!<br/><br/>更新的公司有：' + result.msg,
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.INFO
										});
									} else {
										Ext.Msg.show({
											title : '操作失败',
											message : mess + '创建失败!<br/><br/>' + '原因：' + result.msg,
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.ERROR
										})
									}
								},
								failure : function(response) {
									Ext.Msg.alert('错误', mess + '失败!');
								}
							})
						}
					})
				},

				createUserView : function(param) {
					var record = param.record;
					var mess = '创建视图『' + record.getTitleTpl() + '』';
					Ext.MessageBox.confirm('创建视图', '确定要' + mess + '吗?', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								method : 'POST',
								url : 'platform/database/createuserview.do',
								params : {
									viewid : record.getIdValue()
								},
								success : function(response) {
									var result = Ext.decode(response.responseText, true);
									if (result.success) {
										Ext.Msg.show({
											title : '操作成功',
											message : mess + '成功!',
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.INFO
										});
									} else {
										Ext.Msg.show({
											title : '操作失败',
											message : mess + '创建失败!<br/><br/>' + '原因：' + result.msg,
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.ERROR
										})
									}
									param.grid.getStore().reload();
								},
								failure : function(response) {
									Ext.Msg.alert('错误', mess + '失败!');
								}
							})
						}
					})
				},

				dropUserView : function(param) {
					var record = param.record;
					var mess = '删除视图『' + record.getTitleTpl() + '』';
					Ext.MessageBox.confirm('删除视图', '确定要' + mess + '吗?', function(btn) {
						if (btn == 'yes') {
							Ext.Ajax.request({
								method : 'POST',
								url : 'platform/database/dropuserview.do',
								params : {
									viewid : record.getIdValue()
								},
								success : function(response) {
									var result = Ext.decode(response.responseText, true);
									if (result.success) {
										Ext.Msg.show({
											title : '操作成功',
											message : mess + '成功!',
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.INFO
										});
									} else {
										Ext.Msg.show({
											title : '操作失败',
											message : mess + '删除失败!<br/><br/>' + '原因：' + result.msg,
											buttons : Ext.Msg.OK,
											icon : Ext.Msg.ERROR
										})
									}
									param.grid.getStore().reload();
								},
								failure : function(response) {
									Ext.Msg.alert('错误', mess + '失败!');
								}
							})
						}
					})
				}
			}
		})
