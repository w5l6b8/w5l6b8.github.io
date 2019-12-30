Ext.define('app.Application', {
	  extend : 'Ext.app.Application',

	  name : 'app',

	  requires : ['app.utils.Loader', 'app.utils.Config', 'app.utils.CommonUtils', 'app.utils.storage.localStorage',
	      'app.utils.storage.sessionStorage', 'app.utils.Loader', 'app.utils.ExtUtils', 'app.utils.ProjectUtils',
	      'app.view.platform.frame.system.Function', 'app.view.platform.frame.system.Systemrequires',
	      'app.view.platform.module.attachment.AttachmentUtils', 'expand.ux.FormPanel', 'expand.ux.GridIndex',
	      'expand.ux.BtnGridQuery', 'expand.ux.TreeFilterField', 'expand.ux.TreePanelEdit',
	      'expand.ux.field.SelectField', 'expand.ux.field.UploadField', 'expand.ux.iconcls.Field',
	      'expand.ux.field.MoneyField', 'expand.ux.field.InlineImageField', 'expand.ux.field.DateTime',
	      'expand.widget.ColorColumn', 'expand.widget.ViewColumn', 'expand.widget.RadioColumn',
	      'expand.plugin.PageRequest', 'expand.overrides.form.Basic', 'expand.overrides.util.LocalStorage',
	      'expand.overrides.grid.column.Number', 'expand.overrides.form.field.ComboBox',
	      'expand.overrides.grid.filters.Filters', 'expand.overrides.grid.filters.filter.Date',
	      'expand.overrides.grid.filters.filter.String', 'expand.overrides.grid.filters.filter.Number',
	      'expand.overrides.grid.filters.filter.List', 'expand.overrides.form.trigger.Trigger',
	      'expand.overrides.data.Model', 'expand.overrides.selection.Model', 'expand.overrides.grid.header.Container',
	      'expand.overrides.grid.feature.Grouping'],

	  launch : function(){
		  Ext.QuickTips.init();
		  delete Ext.tip.Tip.prototype.minWidth;
		  Ext.Date.defaultFormat = 'Y-m-d';

		  Ext.get("loading").remove();
	  },

	  onAppUpdate : function(){
		  PU.onAppUpdate();
	  }
  });
