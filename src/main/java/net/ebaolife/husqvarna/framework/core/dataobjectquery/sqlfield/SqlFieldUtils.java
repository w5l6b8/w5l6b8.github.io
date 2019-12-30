package net.ebaolife.husqvarna.framework.core.dataobjectquery.sqlfield;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.core.dataobject.ParentModule;
import net.ebaolife.husqvarna.framework.core.dataobject.field.AttachmentFieldGenerate;
import net.ebaolife.husqvarna.framework.core.dataobject.field.DictionaryFieldGenerate;
import net.ebaolife.husqvarna.framework.core.dataobject.field.ManyToManyField;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.filter.JsonToConditionField;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.attachment.FDataobjectattachment;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FAdditionfield;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovFormscheme;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridscheme;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
import net.ebaolife.husqvarna.framework.utils.MD5;
import net.ebaolife.husqvarna.framework.utils.ObjectFunctionUtils;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
public class SqlFieldUtils {

	public static Set<SqlField> addIdField(BaseModule baseModule, Set<SqlField> result) {
		if (result == null)
			result = new LinkedHashSet<SqlField>();
		addBaseField(baseModule, baseModule.getModule()._getPrimaryKeyField(), result, false, null, null);
		return result;
	}

	public static Set<SqlField> addNameField(BaseModule baseModule, Set<SqlField> result) {
		if (result == null)
			result = new LinkedHashSet<SqlField>();
		addBaseField(baseModule, baseModule.getModule()._getNameField(), result, false, null, null);
		return result;
	}

	public static Set<SqlField> addPidField(BaseModule baseModule, Set<SqlField> result) {
		if (result == null)
			result = new LinkedHashSet<SqlField>();
		addBaseField(baseModule, baseModule.getModule()._getPidField(), result, false, null, null);
		return result;
	}

	public static Set<SqlField> addAllBaseField(BaseModule baseModule, Set<SqlField> result) {
		if (result == null)
			result = new LinkedHashSet<SqlField>();
		for (FDataobjectfield field : baseModule.getModule().getFDataobjectfields()) {
			addBaseField(baseModule, field, result, false, null, null);
		}
		return result;
	}

	public static Set<SqlField> addBaseField(BaseModule baseModule, FDataobjectfield field, Set<SqlField> result,
			boolean isFilter, String condition, String asName) {
		if (field.getIsdisable() == null || !field.getIsdisable()) {
			if (field._isBaseField()) {
				result.add(new SqlField(asName == null ? field.getFieldname() : asName,
						addConditionToFieldSql(field._getSelectName(baseModule.getAsName()), condition)));
				if (field.getFDictionary() != null) {
					result.add(new SqlField((asName == null ? field.getFieldname() : asName) + FDictionary.NAMEENDS,
							DictionaryFieldGenerate.getDictionaryTextField(field._getSelectName(baseModule.getAsName()),
									field.getFDictionary())));
				}
			} else if (field._isManyToMany()) {
				result.add(new SqlField(field.getFieldname(), ManyToManyField._getSelectName(baseModule, field)));
			} else if (field._isManyToOne() || field._isOneToOne()) {
				ParentModule pm = baseModule.getParents().get(field.getFieldname());

				result.add(new SqlField(pm.getPrimarykeyField().getAsName(), pm.getPrimarykeyField().getFieldsql()));
				result.add(new SqlField(pm.getNameField().getAsName(), pm.getNameField().getFieldsql()));
			}
		}
		return result;
	}

	public static String genAsName(String fieldname) {
		return ("f_" + MD5.MD5Encode(fieldname)).substring(0, 30);
	}

	public static String generateUserDefineField(BaseModule baseModule, ParentModule pmodule, FDataobjectfield field,
			boolean isFilter) {
		FAdditionfield additionField = Local.getDao().findById(FAdditionfield.class,
				field.getFAdditionfield().getAdditionfieldid());
		return generateSqlFormJsonFieldString(baseModule, pmodule, additionField._getConditionExpression(), isFilter);
	}

	public static String addConditionToFieldSql(String sql, String condition) {
		if (condition == null || condition.length() == 0)
			return sql;
		else {
			StringBuffer resultBuffer = new StringBuffer();
			String s = "\\d+.\\d+|\\w+";
			Pattern patternthis = Pattern.compile(s);
			Matcher matcherthis = patternthis.matcher(condition);
			while (matcherthis.find()) {
				if (matcherthis.group().equals("this")) {
					matcherthis.appendReplacement(resultBuffer, sql);
				}
			}
			matcherthis.appendTail(resultBuffer);
			return resultBuffer.toString();
		}
	}

	public static String generateSqlFormJsonFieldString(BaseModule baseModule, ParentModule pmodule, String fieldString,
			boolean isFilter) {
		String regex = "\\{[^}]*\\}";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(fieldString);
		StringBuffer resultBuffer = new StringBuffer();
		String preAhead = pmodule == null ? "" : pmodule.getFieldahead() + ".";
		while (matcher.find()) {
			JsonToConditionField f = JSONObject.parseObject(matcher.group(), JsonToConditionField.class);
			f._setfDataobjectfield();
			if (f.getAggregate() == null || f.getAggregate().length() == 0) {
				if (f.getFieldahead() != null && f.getFieldahead().length() > 0) {
					ParentModule pm = baseModule.getAllParents().get(preAhead + f.getFieldahead());
					matcher.appendReplacement(resultBuffer, formatFieldWithCondition(f.getCondition(),
							f.getFDataobjectfield()._getSelectName(pm.getAsName())));
				} else {
					matcher.appendReplacement(resultBuffer,
							formatFieldWithCondition(f.getCondition(), f.getFDataobjectfield()
									._getSelectName(pmodule == null ? baseModule.getAsName() : pmodule.getAsName())));
				}
			}
		}
		matcher.appendTail(resultBuffer);
		return resultBuffer.toString();

	}

	public static String formatFieldWithCondition(String condition, String field) {
		if (condition == null)
			return field;
		StringBuffer resultBuffer = new StringBuffer();
		String s = "\\d+.\\d+|\\w+";
		Pattern patternthis = Pattern.compile(s);
		Matcher matcherthis = patternthis.matcher(condition);
		while (matcherthis.find()) {
			if (matcherthis.group().equals("this")) {
				matcherthis.appendReplacement(resultBuffer, field);
			}
		}
		matcherthis.appendTail(resultBuffer);
		return resultBuffer.toString();
	}

	public static Set<SqlField> addParentField(BaseModule baseModule, FDataobjectfield field, String fieldahead,
			Set<SqlField> result, boolean isFilter, String condition, String asName) {
		if (field.getIsdisable() == null || !field.getIsdisable()) {

			ParentModule pm = baseModule.getAllParents().get(fieldahead);
			if (field._isBaseField()) {
				result.add(new SqlField(asName == null ? fieldahead + "." + field.getFieldname() : asName,
						addConditionToFieldSql(field._getSelectName(pm.getAsName()), condition)));
				if (field.getFDictionary() != null) {
					result.add(new SqlField(fieldahead + "." + field.getFieldname() + FDictionary.NAMEENDS,
							DictionaryFieldGenerate.getDictionaryTextField(field._getSelectName(pm.getAsName()),
									field.getFDictionary())));
				}
			} else if (field._isManyToOne() || field._isOneToOne()) {
				ParentModule pmpm = pm.getParents().get(field.getFieldname());
				if (pmpm == null)
					throw new RuntimeException(
							"模块：" + pm.getModule().getTitle() + " 的父模块路径：" + field.getFieldname() + " 没有找到。");
				result.add(
						new SqlField(pmpm.getPrimarykeyField().getAsName(), pmpm.getPrimarykeyField().getFieldsql()));
				result.add(new SqlField(pmpm.getNameField().getAsName(), pmpm.getNameField().getFieldsql()));
			}
		}
		return result;
	}

	public static Set<SqlField> addAttachmentField(BaseModule baseModule, Set<SqlField> result) {
		if (ObjectFunctionUtils.allowQueryAttachment(baseModule.getModule())) {
			result.add(new SqlField(FDataobjectattachment.COUNT, AttachmentFieldGenerate.getCountField(baseModule)));
			result.add(
					new SqlField(FDataobjectattachment.TOOLTIP, AttachmentFieldGenerate.getTooltipField(baseModule)));
		}
		return result;
	}

	public static Set<SqlField> addUserDefinedField(BaseModule baseModule, List<?> fields, Set<SqlField> result) {
		if (result == null)
			result = new LinkedHashSet<SqlField>();
		for (Object _field : fields) {
			ParentChildField field = (ParentChildField) _field;
			getSqlFieldFromParentChildField(baseModule, field, result, false);
		}
		return result;
	}

	public static Set<SqlField> getSqlFieldFromParentChildField(BaseModule baseModule, ParentChildField field,
			Set<SqlField> result, boolean isFilter) {
		if (result == null)
			result = new LinkedHashSet<SqlField>();
		if (field.getFieldahead() == null || field.getFieldahead().length() == 0) {
			addBaseField(baseModule, field.getFDataobjectfield(), result, isFilter, field.getCondition(),
					field.getRemark());
		} else if (field.getAggregate() == null || field.getAggregate().length() == 0) {
			addParentField(baseModule, field.getFDataobjectfield(), field.getFieldahead(), result, isFilter,
					field.getCondition(), field.getRemark());
		}
		return result;
	}

	public static Set<SqlField> addGridSchemeField(BaseModule baseModule, FovGridscheme scheme, Set<SqlField> result) {
		return addUserDefinedField(baseModule, scheme._getFields(), result);
	}

	public static Set<SqlField> addFormSchemeField(BaseModule baseModule, FovFormscheme scheme, Set<SqlField> result) {
		return addUserDefinedField(baseModule, scheme._getFields(), result);
	}

	public static SqlField getSqlFieldFromFields(Set<SqlField> fields, String fieldname) {
		for (SqlField f : fields) {
			if (f.getFieldname().equals(fieldname))
				return f;
		}
		return null;
	}

}
