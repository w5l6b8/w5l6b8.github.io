package net.ebaolife.husqvarna.framework.utils;

import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectadditionfuncion;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectbasefuncion;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectFunctionUtils {

	private static final String baseFunctionSql = "SELECT DISTINCT bf.fcode FROM f_modulefunction mf "
			+ "        INNER JOIN f_rolefunctionlimit rfl ON rfl.functionid = mf.functionid "
			+ "        INNER JOIN f_role role_ ON role_.roleid = rfl.roleid "
			+ "        INNER JOIN f_userrole ur ON ur.roleid = role_.roleid "
			+ "        INNER JOIN f_companymodule cm ON cm.cmoduleid = mf.cmoduleid "
			+ "        INNER JOIN f_module m ON m.moduleid = cm.moduleid "
			+ "        INNER JOIN f_dataobject do_ ON do_.objectid = m.objectid "
			+ "        INNER JOIN f_dataobjectbasefuncion bf ON bf.basefunctionid = mf.basefunctionid "
			+ "WHERE role_.isvalid = 1 AND mf.isvalid = 1 AND bf.basefunctionid IS NOT NULL "
			+ "        AND ur.userid = ? AND do_.objectid = ? " + "UNION  " + "SELECT DISTINCT bf.fcode "
			+ "FROM f_modulefunction mf "
			+ "        INNER JOIN f_userfunctionlimit rfl ON rfl.functionid = mf.functionid "
			+ "        INNER JOIN f_companymodule cm ON cm.cmoduleid = mf.cmoduleid "
			+ "        INNER JOIN f_module m ON m.moduleid = cm.moduleid "
			+ "        INNER JOIN f_dataobject do_ ON do_.objectid = m.objectid "
			+ "        INNER JOIN f_dataobjectbasefuncion bf ON bf.basefunctionid = mf.basefunctionid "
			+ "WHERE mf.isvalid = 1 AND bf.basefunctionid IS NOT NULL AND rfl.userid = ? AND do_.objectid = ? ";

	private static String additionFunctionSql = "SELECT DISTINCT mf.additionfunctionid FROM f_modulefunction mf "
			+ "        INNER JOIN f_rolefunctionlimit rfl ON rfl.functionid = mf.functionid "
			+ "        INNER JOIN f_role role_ ON role_.roleid = rfl.roleid "
			+ "        INNER JOIN f_userrole ur ON ur.roleid = role_.roleid "
			+ "        INNER JOIN f_companymodule cm ON cm.cmoduleid = mf.cmoduleid "
			+ "        INNER JOIN f_module m ON m.moduleid = cm.moduleid "
			+ "        INNER JOIN f_dataobject do_ ON do_.objectid = m.objectid "
			+ "WHERE role_.isvalid = 1 AND mf.isvalid = 1 and mf.additionfunctionid is not null "
			+ "        AND ur.userid = ? AND do_.objectid = ? " + "UNION SELECT DISTINCT mf.additionfunctionid "
			+ "FROM f_modulefunction mf "
			+ "        INNER JOIN f_userfunctionlimit rfl ON rfl.functionid = mf.functionid "
			+ "        INNER JOIN f_companymodule cm ON cm.cmoduleid = mf.cmoduleid "
			+ "        INNER JOIN f_module m ON m.moduleid = cm.moduleid "
			+ "        INNER JOIN f_dataobject do_ ON do_.objectid = m.objectid "
			+ "WHERE mf.isvalid = 1 and mf.additionfunctionid is not null "
			+ "        AND rfl.userid = ? AND do_.objectid = ?";

	private static String BASEFUNCTIONS = "_BASEFUNCTIONS_";

	public static Map<String, Boolean> getBaseFunctions(String objectid) {
		List<Map<String, Object>> fcodes = Local.getDao().executeSQLQuery(baseFunctionSql, Local.getUserid(), objectid,
				Local.getUserid(), objectid);
		Map<String, Boolean> result = new HashMap<String, Boolean>();
		for (Map<String, Object> fcode : fcodes)
			result.put(fcode.get("fcode").toString(), true);
		return result;
	}

	public static List<FDataobjectadditionfuncion> getAdditionFunctions(FDataobject object) {
		List<Map<String, Object>> additionfunctionids = Local.getDao().executeSQLQuery(additionFunctionSql,
				Local.getUserid(), object.getObjectid(), Local.getUserid(), object.getObjectid());
		List<FDataobjectadditionfuncion> result = new ArrayList<FDataobjectadditionfuncion>();
		for (FDataobjectadditionfuncion af : object.getFDataobjectadditionfuncions()) {
			if (af.getIsdisable() != null && af.getIsdisable())
				continue;
			for (Map<String, Object> additionfunctionid : additionfunctionids)
				if (af.getAdditionfunctionid().equals(additionfunctionid.get("additionfunctionid"))) {
					result.add(af);
					break;
				}
		}
		return result;
	}

	public static boolean allowQuery(FDataobject dataobject) {
		return allowBaseFunction(dataobject, FDataobjectbasefuncion.QUERY);
	}

	public static boolean allowNew(FDataobject dataobject) {
		return allowBaseFunction(dataobject, FDataobjectbasefuncion.NEW);
	}

	public static boolean allowEdit(FDataobject dataobject) {
		return allowBaseFunction(dataobject, FDataobjectbasefuncion.EDIT);
	}

	public static boolean allowDelete(FDataobject dataobject) {
		return allowBaseFunction(dataobject, FDataobjectbasefuncion.DELETE);
	}

	@SuppressWarnings("unchecked")
	public static boolean allowBaseFunction(FDataobject dataobject, String action) {
		Object baseobject = Local.getRequest().getAttribute(BASEFUNCTIONS);
		Map<String, Boolean> baseFunctions;
		if (baseobject != null) {
			baseFunctions = (Map<String, Boolean>) baseobject;
		} else {
			baseFunctions = getBaseFunctions(dataobject.getObjectid());
			Local.getRequest().setAttribute(BASEFUNCTIONS, baseFunctions);
		}
		boolean result = false;
		if (action == FDataobjectbasefuncion.QUERY)
			result = true;
		else if (action == FDataobjectbasefuncion.NEW)
			result = dataobject.getHasinsert() != null && dataobject.getHasinsert()
					&& baseFunctions.get(FDataobjectbasefuncion.QUERY) && baseFunctions.get(action);
		else if (action == FDataobjectbasefuncion.EDIT)
			result = dataobject.getHasedit() != null && dataobject.getHasedit()
					&& baseFunctions.get(FDataobjectbasefuncion.QUERY) && baseFunctions.get(action);
		else if (action == FDataobjectbasefuncion.DELETE)
			result = dataobject.getHasdelete() != null && dataobject.getHasdelete()
					&& baseFunctions.get(FDataobjectbasefuncion.QUERY) && baseFunctions.get(action);
		return result && dataobject.getHasbrowse() != null && dataobject.getHasbrowse()
				&& dataobject.getHasenable() != null && dataobject.getHasenable();

	}

	public static boolean allowQueryAttachment(FDataobject dataobject) {
		return allowAttachmentFunction(dataobject, FDataobjectbasefuncion.ATTACHMENTQUERY);
	}

	public static boolean allowAddAttachment(FDataobject dataobject) {
		return allowAttachmentFunction(dataobject, FDataobjectbasefuncion.ATTACHMENTADD);
	}

	public static boolean allowEditAttachment(FDataobject dataobject) {
		return allowAttachmentFunction(dataobject, FDataobjectbasefuncion.ATTACHMENTEDIT);
	}

	public static boolean allowDELETEAttachment(FDataobject dataobject) {
		return allowAttachmentFunction(dataobject, FDataobjectbasefuncion.ATTACHMENTDELETE);
	}

	@SuppressWarnings("unchecked")
	public static boolean allowAttachmentFunction(FDataobject dataobject, String action) {
		Object baseobject = Local.getRequest().getAttribute(BASEFUNCTIONS);
		Map<String, Boolean> baseFunctions;
		if (baseobject != null) {
			baseFunctions = (Map<String, Boolean>) baseobject;
		} else {
			baseFunctions = getBaseFunctions(dataobject.getObjectid());
			Local.getRequest().setAttribute(BASEFUNCTIONS, baseFunctions);
		}
		return dataobject.getHasattachment() != null && dataobject.getHasattachment()
				&& baseFunctions.get(FDataobjectbasefuncion.ATTACHMENTQUERY) && baseFunctions.get(action);
	}

}
