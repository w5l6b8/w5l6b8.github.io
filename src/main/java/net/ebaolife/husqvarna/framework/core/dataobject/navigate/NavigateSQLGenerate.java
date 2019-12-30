package net.ebaolife.husqvarna.framework.core.dataobject.navigate;


import net.ebaolife.husqvarna.framework.core.dataobject.ParentModule;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.column.ColumnField;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.generate.SqlGenerate;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridnavigateschemedetail;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
public class NavigateSQLGenerate {

	public static String KEYASNAME = "key_";
	public static String NAMEASNAME = "name_";

	private SqlGenerate baseModule;
	private Map<String, String> fields;

	private FovGridnavigateschemedetail navigateSchemeDetail;

	public NavigateSQLGenerate(SqlGenerate baseModule) {
		super();
		this.baseModule = baseModule;

	}

	public NavigateSQLGenerate(SqlGenerate baseModule, FovGridnavigateschemedetail navigateSchemeDetail) {
		super();
		this.baseModule = baseModule;
		this.navigateSchemeDetail = navigateSchemeDetail;
	}

	public String generageNavigateSql() {
		fields = new LinkedHashMap<String, String>();
		List<String> groups = new ArrayList<String>();
		List<String> ascordesc = new ArrayList<String>();

		List<ColumnField> columnFields = new ArrayList<ColumnField>();
		FovGridnavigateschemedetail schemeDetail = navigateSchemeDetail;

		String aheadPath = schemeDetail._getFactAheadPath();

		String key = "key_1";
		String name = "name_1";
		if (schemeDetail.getFDataobjectfield()._isBaseField()) {
			ColumnField field = new ColumnField();
			field.setRemark(key);
			field.setFDataobjectfield(schemeDetail.getFDataobjectfield());
			field.setCondition(schemeDetail._getCondition());
			field.setFieldahead(aheadPath);
			fields.put(key, key);
			groups.add(key);
			ascordesc.add(schemeDetail.getReverseorder() ? " desc" : "");
			if (field.getFDataobjectfield().getFDictionary() != null) {
				fields.put(name, key + FDictionary.NAMEENDS);
				groups.add(key + FDictionary.NAMEENDS);
				ascordesc.add("");
			}
			columnFields.add(field);
		} else {
			ParentModule pm = baseModule.getBaseModule().getAllParents().get(aheadPath);
			fields.put(key, key);
			groups.add(key);
			ascordesc.add(schemeDetail.getReverseorder() ? " desc" : "");
			ColumnField field = new ColumnField();
			field.setRemark(key);
			field.setFDataobjectfield(pm.getModule()._getPrimaryKeyField());
			field.setFieldahead(aheadPath);
			columnFields.add(field);

			fields.put(name, name);
			groups.add(name);
			ascordesc.add("");
			field = new ColumnField();
			field.setRemark(name);
			field.setFDataobjectfield(pm.getModule()._getNameField());
			field.setFieldahead(aheadPath);
			columnFields.add(field);
		}
		fields.put("count_", "count(*)");
		baseModule.getSelectfields().clear();
		baseModule.setColumnFields(columnFields);

		List<String> sqls = new ArrayList<String>();
		sqls.add("select ");
		boolean first = true;
		for (String scale : fields.keySet()) {
			sqls.add((first ? "" : ",") + fields.get(scale) + " as " + scale);
			first = false;
		}
		sqls.add(" from ( ");
		sqls.add(baseModule.pretreatment().generateSelect());
		sqls.add(" ) t_");
		sqls.add(" group by ");
		first = true;
		for (String group : groups) {
			sqls.add((first ? "" : ",") + group);
			first = false;
		}
		sqls.add(" order by ");
		first = true;
		int i = 0;
		for (String group : groups) {
			sqls.add((first ? "" : ",") + group + ascordesc.get(i++));
			first = false;
		}
		StringBuilder sb = new StringBuilder();
		for (String s : sqls) {
			sb.append(s);
		}
		return sb.toString();
	}

	public String generageNavigateSqlWithAllParentField() {

		fields = new LinkedHashMap<String, String>();
		fields.put("count_", null);
		List<String> groups = new ArrayList<String>();
		List<String> ascordesc = new ArrayList<String>();

		FovGridnavigateschemedetail schemeDetail = navigateSchemeDetail;
		FDataobjectfield moduleField = schemeDetail.getFDataobjectfield();
		String key = "key_1";
		String name = "name_1";

		List<ColumnField> columnFields = baseModule.getColumnFields();

		if (moduleField._isBaseField()) {

			ColumnField field = new ColumnField();
			field.setRemark(key);
			field.setFDataobjectfield(schemeDetail.getFDataobjectfield());
			field.setCondition(schemeDetail._getCondition());
			fields.put(key, key);
			groups.add(key);
			ascordesc.add(schemeDetail.getReverseorder() ? " desc" : "");

			if (field.getFDataobjectfield().getFDictionary() != null) {
				fields.put(name, key + FDictionary.NAMEENDS);
				groups.add(key + FDictionary.NAMEENDS);
				ascordesc.add("");
			}
			columnFields.add(field);

		} else {

			fields.put(key, key);
			groups.add(key);
			ascordesc.add(schemeDetail.getReverseorder() ? " desc" : "");
			ColumnField field = new ColumnField();
			field.setRemark(key);
			field.setFDataobjectfield(baseModule.getBaseModule().getModule()._getPrimaryKeyField());
			columnFields.add(field);

			fields.put(name, name);
			groups.add(name);
			ascordesc.add("");
			field = new ColumnField();
			field.setRemark(name);
			field.setFDataobjectfield(baseModule.getBaseModule().getModule()._getNameField());
			columnFields.add(field);

		}

		List<String> sqls = new ArrayList<String>();

		sqls.add(baseModule.pretreatment().generateSelect());
		sqls.add(" order by ");
		boolean first = true;
		int i = 0;
		for (String group : groups) {
			sqls.add((first ? "" : ",") + group + ascordesc.get(i++));
			first = false;
		}
		StringBuilder sb = new StringBuilder();
		for (String s : sqls) {
			sb.append(s);
		}
		return sb.toString();
	}

	public SqlGenerate getBaseModule() {
		return baseModule;
	}

	public void setBaseModule(SqlGenerate baseModule) {
		this.baseModule = baseModule;
	}

	public Map<String, String> getFields() {
		return fields;
	}

	public void setFields(Map<String, String> fields) {
		this.fields = fields;
	}

	public FovGridnavigateschemedetail getNavigateSchemeDetail() {
		return navigateSchemeDetail;
	}

	public void setNavigateSchemeDetail(FovGridnavigateschemedetail navigateSchemeDetail) {
		this.navigateSchemeDetail = navigateSchemeDetail;
	}

}
