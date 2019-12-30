package net.ebaolife.husqvarna.framework.utils;

import net.ebaolife.husqvarna.framework.bean.HierarchyIDPID;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.generate.SqlGenerate;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import org.hibernate.query.NativeQuery;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HierarchyIDPIDUtils {

	public static final String HIERARCHYIDPID = "HierarchyIDPID_";
	public static final String UNDEFINED = "_undefined_";

	public static String _getIDPIDExpression(int level, String fieldexpression, List<HierarchyIDPID> hierarchyidpids) {

		List<HierarchyIDPID> levelHierarchyIDPIDs = new ArrayList<HierarchyIDPID>();

		for (HierarchyIDPID record : hierarchyidpids) {
			if (record.getLevel() + 1 == level)
				levelHierarchyIDPIDs.add(record);
		}
		List<HierarchyIDPID> keyvalues = new ArrayList<HierarchyIDPID>();
		for (HierarchyIDPID record : levelHierarchyIDPIDs) {
			keyvalues.add(record);
		}
		return genCaseWhen(fieldexpression, keyvalues);
	}

	private static String genCaseWhen(String fieldexpression, List<HierarchyIDPID> keyvalues) {
		HierarchyIDPID record = keyvalues.get(0);

		keyvalues.remove(0);

		return " case when " + fieldexpression + getIn(record.getAllChildrenId(false)) + " then "
				+ OperateUtils.translateValue(record.getId()) + " else "
				+ (keyvalues.size() > 0 ? genCaseWhen(fieldexpression, keyvalues) : "'" + UNDEFINED + "'") + " end ";

	}

	private static String getIn(String[] values) {

		return OperateUtils.valueChangeToInString(String.join(",", values));

	}

	public static int getIDPIDMaxLevel(FDataobject dataobject) {
		int result = 0;
		for (HierarchyIDPID record : getHierarchyIDPIDFromRequest(dataobject)) {
			if (result < record.getLevel())
				result = record.getLevel();
		}
		return result + 1;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, HierarchyIDPID> getHierarchyIDPIDMapsFromRequest(FDataobject dataobject) {
		getHierarchyIDPIDFromRequest(dataobject);
		return ((Map<String, HierarchyIDPID>) Local.getRequest()
				.getAttribute(HIERARCHYIDPID + dataobject.getObjectname() + "_idmap"));
	}

	@SuppressWarnings("unchecked")
	public static List<HierarchyIDPID> getHierarchyIDPIDFromRequest(FDataobject dataobject) {
		if (Local.getRequest().getAttribute(HIERARCHYIDPID + dataobject.getObjectname()) == null) {
			List<HierarchyIDPID> idpids = getHierarchyIDPID_(dataobject);
			Map<String, HierarchyIDPID> idmap = new HashMap<String, HierarchyIDPID>();
			for (HierarchyIDPID record : idpids)
				idmap.put(record.getId(), record);
			Local.getRequest().setAttribute(HIERARCHYIDPID + dataobject.getObjectname(), idpids);
			Local.getRequest().setAttribute(HIERARCHYIDPID + dataobject.getObjectname() + "_idmap", idmap);
		}
		return ((List<HierarchyIDPID>) Local.getRequest().getAttribute(HIERARCHYIDPID + dataobject.getObjectname()));
	}

	@SuppressWarnings("unchecked")
	public static boolean hasChildren(String objectname, String id) {
		if (id == null)
			return false;
		Map<String, HierarchyIDPID> idmap = (Map<String, HierarchyIDPID>) Local.getRequest()
				.getAttribute(HIERARCHYIDPID + objectname + "_idmap");
		HierarchyIDPID record = idmap.get(id);
		if (record != null) {
			return record.getChildren() != null && record.getChildren().size() > 0;
		} else
			return false;
	}

	public static List<HierarchyIDPID> getHierarchyIDPID_(FDataobject object) {
		SqlGenerate generate = new SqlGenerate(object);
		generate.onlyAddIdnameFields();
		generate.pretreatment();
		String sql = generate.generateSelect();
		NativeQuery<?> query = Local.getDao().getCurrentSession().createNativeQuery(sql);

		Map<String, Object> param = DataObjectUtils.getSqlParameter();
		if (param != null) {
			for (String key : param.keySet()) {
				if (sql.indexOf(":" + key) != -1)
					query.setParameter(key, param.get(key));
			}
		}
		List<HierarchyIDPID> hierarchyResult = new ArrayList<HierarchyIDPID>();
		List<HierarchyIDPID> result = new ArrayList<HierarchyIDPID>();

		Map<String, List<HierarchyIDPID>> maps = new HashMap<String, List<HierarchyIDPID>>();
		List<?> queryresult = query.getResultList();
		for (int i = 0; i < queryresult.size(); i++) {
			Object[] datas = (Object[]) queryresult.get(i);
			HierarchyIDPID record = new HierarchyIDPID(datas[0] != null ? datas[0].toString() : null,
					datas[1] != null ? datas[1].toString() : null, datas[2] != null ? datas[2].toString() : null);
			result.add(record);
			if (record.getPid() == null) {
				hierarchyResult.add(record);
			} else {
				if (!maps.containsKey(record.getPid()))
					maps.put(record.getPid(), new ArrayList<HierarchyIDPID>());
				maps.get(record.getPid()).add(record);
			}
		}
		for (HierarchyIDPID record : hierarchyResult) {
			adjustChildren(record, maps);
		}

		return result;
	}

	public static void adjustChildren(HierarchyIDPID record, Map<String, List<HierarchyIDPID>> maps) {
		List<HierarchyIDPID> children = maps.get(record.getId());
		if (children != null) {
			record.setChildren(children);
			for (HierarchyIDPID child : children) {
				child.setParent(record);
				child.setLevel(record.getLevel() + 1);
				adjustChildren(child, maps);
			}
		}
	}

}
