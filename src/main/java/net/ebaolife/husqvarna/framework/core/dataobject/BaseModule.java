package net.ebaolife.husqvarna.framework.core.dataobject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("serial")
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
public class BaseModule extends AbstractModule implements Serializable {

	private Map<String, ParentModule> parents = new HashMap<String, ParentModule>();
	private Map<String, ChildModule> childs = new HashMap<String, ChildModule>();

	private Map<String, ParentModule> allParents = new HashMap<String, ParentModule>();
	private Map<String, ChildModule> allChilds = new HashMap<String, ChildModule>();

	private Map<String, BaseModule> childFieldBaseModules;

	private Map<String, String> allFieldsNameAndSql;

	public BaseModule pModule;

	public BaseModule() {
		this.childFieldBaseModules = new HashMap<String, BaseModule>();
	}

	public void calcParentModuleOnlyone() {

		for (String key : getAllParents().keySet()) {

			if (getAllParents().get(key).isOnlyonethisdataobject()) {
				String pmobjectname = getAllParents().get(key).getModule().getObjectname();
				int count = 0;
				for (String key1 : getAllParents().keySet()) {
					if (pmobjectname.equals(getAllParents().get(key1).getModule().getObjectname())) {
						count++;
						if (count == 2) {
							getAllParents().get(key1).setOnlyonethisdataobject(false);
							break;
						}
					}
				}
				if (count == 2)
					getAllParents().get(key).setOnlyonethisdataobject(false);
			}
		}

	}

	public Map<String, ParentModule> getParents() {
		return parents;
	}

	public void setParents(Map<String, ParentModule> parents) {
		this.parents = parents;
	}

	public Map<String, ChildModule> getChilds() {
		return childs;
	}

	public void setChilds(Map<String, ChildModule> childs) {
		this.childs = childs;
	}

	public Map<String, ParentModule> getAllParents() {
		return allParents;
	}

	public void setAllParents(Map<String, ParentModule> allParents) {
		this.allParents = allParents;
	}

	public Map<String, ChildModule> getAllChilds() {
		return allChilds;
	}

	public void setAllChilds(Map<String, ChildModule> allChilds) {
		this.allChilds = allChilds;
	}

	public Map<String, BaseModule> getChildFieldBaseModules() {
		return childFieldBaseModules;
	}

	public void setChildFieldBaseModules(Map<String, BaseModule> childFieldBaseModules) {
		this.childFieldBaseModules = childFieldBaseModules;
	}

	public Map<String, String> getAllFieldsNameAndSql() {
		return allFieldsNameAndSql;
	}

	public void setAllFieldsNameAndSql(Map<String, String> allFieldsNameAndSql) {
		this.allFieldsNameAndSql = allFieldsNameAndSql;
	}

	public BaseModule getpModule() {
		return pModule;
	}

	public void setpModule(BaseModule pModule) {
		this.pModule = pModule;
	}

}
