package net.ebaolife.husqvarna.framework.core.dataobject.sqlgenerate;

import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.core.dataobject.ParentModule;
import net.ebaolife.husqvarna.framework.core.dataobject.UserSession;
import net.ebaolife.husqvarna.framework.core.dataobject.field.AdditionParentModuleField;
import net.ebaolife.husqvarna.framework.core.dataobject.field.DictionaryFieldGenerate;
import net.ebaolife.husqvarna.framework.core.dataobject.field.ManyToManyField;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary;

import java.util.LinkedHashMap;
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
public class FieldGenerate {

	public static Map<String, String> generateFields(BaseModule baseModule, UserSession userSession) {
		Map<String, String> result = new LinkedHashMap<String, String>();

		for (FDataobjectfield field : baseModule.getModule().getFDataobjectfields()) {

			if (field.getIsdisable() == null || !field.getIsdisable()) {

				if ((userSession == null || !userSession.isFieldHidden(field.getFieldid()))) {
					if (field._isBaseField()) {

						result.put(field.getFieldname(), field._getSelectName(baseModule.getAsName()));
						if (field.getFDictionary() != null) {
							result.put(field.getFieldname() + FDictionary.NAMEENDS,
									DictionaryFieldGenerate.getDictionaryTextField(
											field._getSelectName(baseModule.getAsName()), field.getFDictionary()));
						}
					} else if (field._isManyToMany())
						result.put(field.getFieldname(), ManyToManyField._getSelectName(baseModule, field));
				}
			}
		}

		for (String pkey : baseModule.getParents().keySet()) {
			ParentModule pm = baseModule.getParents().get(pkey);

			result.put(pm.getPrimarykeyField().getAsName(), pm.getPrimarykeyField().getFieldsql());

			result.put(pm.getNameField().getAsName(), pm.getNameField().getFieldsql());

			if (pm.getAdditionFields() != null) {
				for (String pad : pm.getAdditionFields().keySet()) {
					AdditionParentModuleField f = pm.getAdditionFields().get(pad);
					if (f.getModuleField()._isBaseField())
						result.put(pad, f.getFieldsql());
					if (f.getModuleField().getFDictionary() != null) {
						result.put(pad + FDictionary.NAMEENDS,
								DictionaryFieldGenerate.getDictionaryTextField(
										f.getModuleField()._getSelectName(pm.getAsName()),
										f.getModuleField().getFDictionary()));
					}
				}
			}
		}

		for (String pkey : baseModule.getParents().keySet()) {
			ParentModule pm = baseModule.getParents().get(pkey);
			addGrandParentFields(pm, result);
		}

		baseModule.setAllFieldsNameAndSql(result);
		return result;

	}

	private static void addGrandParentFields(ParentModule pm, Map<String, String> result) {
		for (String pkey : pm.getParents().keySet()) {
			ParentModule gpm = pm.getParents().get(pkey);

			if (gpm.getAdditionFields() != null)
				for (String pad : gpm.getAdditionFields().keySet()) {
					AdditionParentModuleField f = gpm.getAdditionFields().get(pad);
					if (f.getModuleField()._isBaseField())
						result.put(pad, f.getFieldsql());
					if (f.getModuleField().getFDictionary() != null) {
						result.put(pad + FDictionary.NAMEENDS,
								DictionaryFieldGenerate.getDictionaryTextField(
										f.getModuleField()._getSelectName(gpm.getAsName()),
										f.getModuleField().getFDictionary()));
					}
				}

			if (gpm.getAdditionFields() != null || gpm.isInsertIdAndNameFields()) {

				if (!result.containsKey(gpm.getPrimarykeyField().getAsName()))
					result.put(gpm.getPrimarykeyField().getAsName(), gpm.getPrimarykeyField().getFieldsql());

				if (!result.containsKey(gpm.getNameField().getAsName()))
					result.put(gpm.getNameField().getAsName(), gpm.getNameField().getFieldsql());
			}

			addGrandParentFields(gpm, result);
		}
	}

}
