package net.ebaolife.husqvarna.framework.core.dataobject.sqlgenerate;


import net.ebaolife.husqvarna.framework.core.dataobject.BaseModule;
import net.ebaolife.husqvarna.framework.core.dataobject.ParentModule;

import java.util.ArrayList;
import java.util.List;

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
public class FromGenerate {

	public static List<String> generateFrom(BaseModule baseModule, boolean isCount) {
		List<String> froms = new ArrayList<String>();
		froms.add(" from ");

		froms.add(baseModule.getModule()._getTablename() + " " + baseModule.getAsName());
		if (isCount)
			for (String pmkey : baseModule.getParents().keySet()) {
				ParentModule pm = baseModule.getParents().get(pmkey);
				addParentToFroms(pm, froms, isCount);
			}
		else
			for (String pmkey : baseModule.getParents().keySet()) {
				ParentModule pm = baseModule.getParents().get(pmkey);
				addParentToFroms(pm, froms, isCount);
			}
		return froms;
	}

	private static void addParentToFroms(ParentModule pmodule, List<String> froms, boolean isCount) {

		if (isCount) {
			froms.add(pmodule.getLeftoutterjoin());
			for (String pmkey : pmodule.getParents().keySet())
				addParentToFroms(pmodule.getParents().get(pmkey), froms, isCount);

		} else {
			froms.add(pmodule.getLeftoutterjoin());
			for (String pmkey : pmodule.getParents().keySet())
				addParentToFroms(pmodule.getParents().get(pmkey), froms, isCount);
		}
	}

	public List<String> generateAggregateFrom1(BaseModule baseModule, String aheadField) {

		List<String> froms = new ArrayList<String>();

		froms.add(baseModule.getModule()._getTablename() + " " + baseModule.getAsName());

		for (String pmkey : baseModule.getParents().keySet()) {
			ParentModule pm = baseModule.getParents().get(pmkey);
			addAggregateParentToFroms(pm, froms, aheadField);

		}
		return froms;
	}

	private void addAggregateParentToFroms(ParentModule pmodule, List<String> froms, String aheadField) {

		if (aheadField.startsWith(pmodule.getFieldahead())) {

			if (aheadField.equals(pmodule.getFieldahead())) {

			} else {
				froms.add(pmodule.getLeftoutterjoin());
				for (String pmkey : pmodule.getParents().keySet())
					addAggregateParentToFroms(pmodule.getParents().get(pmkey), froms, aheadField);
			}
		} else {
			froms.add(pmodule.getLeftoutterjoin());
			for (String pmkey : pmodule.getParents().keySet())
				addAggregateParentToFroms(pmodule.getParents().get(pmkey), froms, aheadField);
		}
	}

}
