package net.ebaolife.husqvarna.framework.utils;

import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.core.dataobject.ModuleOnlyHierarchyGenerate;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;

import java.util.*;

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
public class DataObjectUtils {

	public static final String SQLPARAMETER = "sqlParameter";

	private static Map<String, FDataobject> dataobjects = null;
	private static Map<String, FDataobjectfield> dataobjectfields = new HashMap<String, FDataobjectfield>();
	private static Map<String, Set<FDataobjectfield>> dataobjectManyToOneFields = null;

	private static Map<String, BaseModule> baseModules = new HashMap<String, BaseModule>();

	private static boolean usedcache = false;

	public static void resetDataobjectCachedData() {
		setDataobjects(null);
		setDataobjectManyToOneFields(null);
		dataobjectfields.clear();
		baseModules.clear();
	}

	public static BaseModule getBaseModule(String objectname) {
		if (usedcache) {
			if (!baseModules.containsKey(objectname)) {
				baseModules.put(objectname, ModuleOnlyHierarchyGenerate.genModuleHierarchy(getDataObject(objectname)));
			}
			return baseModules.get(objectname);
		} else {
			return ModuleOnlyHierarchyGenerate.genModuleHierarchy(getDataObject(objectname));
		}
	}

	public synchronized static FDataobject getDataObject(String objectname) {
		if (usedcache) {
			FDataobject result = getDataobjects().get(objectname);
			if (result == null) {
				for (String key : getDataobjects().keySet()) {
					if (getDataobjects().get(key).getObjectid().equals(objectname)
							|| getDataobjects().get(key).getTablename().equals(objectname)) {
						result = getDataobjects().get(key);
						break;
					}
				}
			}
			return result;
		} else {
			FDataobject result = Local.getDao().findById(FDataobject.class, objectname);
			if (result == null)
				return Local.getDao().findByPropertyFirst(FDataobject.class, "objectname", objectname);
			else
				return result;
		}
	}

	public static Set<FDataobjectfield> getDataObjectManyToOneField(String objectname) {
		if (usedcache) {
			return getDataobjectManyToOneFields().containsKey(objectname)
					? getDataobjectManyToOneFields().get(objectname) : new HashSet<FDataobjectfield>();
		} else {
			List<FDataobjectfield> fields = Local.getDao().findByProperty(FDataobjectfield.class, "fieldtype",
					objectname);
			Set<FDataobjectfield> result = new HashSet<FDataobjectfield>();
			for (FDataobjectfield field : fields) {
				result.add(field);
			}
			return result;
		}
	}

	public static Map<String, FDataobject> getDataobjects() {
		if (dataobjects == null) {
			dataobjectfields.clear();
			List<FDataobject> dos = Local.getDao().findAll(FDataobject.class);
			dataobjects = new HashMap<String, FDataobject>();
			dataobjectManyToOneFields = new HashMap<String, Set<FDataobjectfield>>();

			for (FDataobject object : dos) {
				dataobjects.put(object.getObjectname(), object);
				object.getFDataobjectdefaultorders().size();
				object.getFDataobjectconditions().size();
				object.getFDataobjectfieldconstraints().size();
				for (FDataobjectfield field : object.getFDataobjectfields()) {
					dataobjectfields.put(field.getFieldid(), field);
					if (field._isManyToOne() || field._isOneToOne()) {
						if (!dataobjectManyToOneFields.containsKey(field.getFieldtype()))
							dataobjectManyToOneFields.put(field.getFieldtype(), new HashSet<FDataobjectfield>());
						dataobjectManyToOneFields.get(field.getFieldtype()).add(field);
					}
				}
			}
		}
		return dataobjects;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, Object> getSqlParameter() {
		Map<String, Object> result = (Map<String, Object>) Local.getRequest()
				.getAttribute(DataObjectUtils.SQLPARAMETER);
		if (result == null) {
			result = new HashMap<String, Object>();
			Local.getRequest().setAttribute(DataObjectUtils.SQLPARAMETER, result);
		}
		return result;
	}

	public static void setDataobjects(Map<String, FDataobject> dataobjects) {
		DataObjectUtils.dataobjects = dataobjects;
	}

	public static Map<String, Set<FDataobjectfield>> getDataobjectManyToOneFields() {
		if (dataobjectManyToOneFields == null)
			getDataobjects();
		return dataobjectManyToOneFields;
	}

	public static void setDataobjectManyToOneFields(Map<String, Set<FDataobjectfield>> dataobjectManyToOneFields) {
		DataObjectUtils.dataobjectManyToOneFields = dataobjectManyToOneFields;
	}

	public static Map<String, FDataobjectfield> getDataobjectfields() {
		if (dataobjectfields.size() == 0) {
			getDataobjects();
		}
		return dataobjectfields;
	}

	public static void setDataobjectfields(Map<String, FDataobjectfield> dataobjectfields) {
		DataObjectUtils.dataobjectfields = dataobjectfields;
	}

}
