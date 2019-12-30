package net.ebaolife.husqvarna.platform.service;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.SqlMapperAdapter;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.*;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionarydetail;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FObjectfieldproperty;
import net.ebaolife.husqvarna.framework.dao.entity.module.FModule;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.*;
import net.ebaolife.husqvarna.framework.utils.CommonUtils;
import net.ebaolife.husqvarna.framework.utils.PropertyPreFilters;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service

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
public class ModuleService extends SqlMapperAdapter {

	public static final PropertyPreFilters filters = new PropertyPreFilters();

	static {
		filters.addFilter(FModule.class).addExcludes("fModulegroup", "fCompanymodules", "fModuletabellimits",
				"createdate", "creater");
		filters.addFilter(FDataobject.class).addExcludes("fDataobjectgroup", "fDatafilterrolelimits",
				"fModuletabellimits", "fovFormschemedetails", "fModules", "creater", "createdate", "lastmodifier",
				"lastmodifydate", "fovGridschemes", "fovFilterschemes", "fovGridnavigateschemes",
				"fDataobjectconditions", "fDataobjectviews", "fDataobjectfieldconstraints", "fDataobjectdefaultorders",
				"sqlstatement", "fDataminingschemes", "fDataminingexpandgroups", "fovGridsortschemes", "classname",
				"tablename", "schemaname", "datasourcename", "sqlstatement", "fDataobjectadditionfuncions",
				"fRecordexcelschemes");
		filters.addFilter(FDataobjectfield.class).addExcludes("fDataobject", "fRolefieldlimits",
				"fDatafilterrolelimits", "fUserfieldlimits", "fovGridschemecolumns", "fovFormschemedetails", "creater",
				"createdate", "lastmodifier", "lastmodifydate", "fDictionary", "fAdditionfield");
		filters.addFilter(FovGridscheme.class).addExcludes("fUser", "fDataobject", "fGridschemeshares",
				"fovUserdefaultgridschemes");
		filters.addFilter(FovGridschemecolumn.class).addExcludes("fovGridscheme", "fovGridschemecolumn",
				"fDataobjectfield", "fDataobjectconditionBySubconditionid");

		filters.addFilter(FovFilterscheme.class).addExcludes("fUser", "fDataobject", "fFilterschemeshares",
				"fovUserdefaultfilterschemes");
		filters.addFilter(FovFilterschemedetail.class).addExcludes("fovFilterscheme", "fovFilterschemedetail",
				"fDataobjectfield");

		filters.addFilter(FovFormscheme.class).addExcludes("fDataobject", "fUser", "fDataobjectBySubobjectid");
		filters.addFilter(FovFormschemedetail.class).addExcludes("fovFormscheme", "fovFormschemedetail", "fDataobject",
				"fDataobjectfield");
		filters.addFilter(FDataobjectview.class).addExcludes("details", "fDataobject", "fUser");
		filters.addFilter(FDictionary.class).addExcludes("fDictionarygroup", "fDataobjectfields", "fDictionarydetails");
		filters.addFilter(FDictionarydetail.class).addExcludes("fDictionary");
		filters.addFilter(FDataobjectadditionfuncion.class).addExcludes("fDataobject", "fModulefunctions");
		filters.addFilter(FovDataobjectassociate.class).addExcludes("fDataobject", "fovDataobjectassociatedetails");
		filters.addFilter(FovChartscheme.class).addExcludes("fDataobject", "FUser");
		filters.addFilter(FObjectfieldproperty.class).addExcludes("fDataobjectfields");
		filters.addFilter(FovGridsortscheme.class).addExcludes("fDataobject", "fUser", "details");
		filters.addFilter(FDataobjectsqlparam.class).addExcludes("fDataobject", "FDataobjectByParamobjectid",
				"FDictionary");

	}

	public FModule getModule(String moduleid) {
		if (CommonUtils.isEmpty(moduleid))
			return null;
		FModule module = dao.findById(FModule.class, moduleid);
		if (module != null)
			return module;
		List<FDataobject> list = dao.executeQuery(
				"from FDataobject where (lower(objectid) = ? or lower(objectname) =?)",
				new Object[] { moduleid.toLowerCase(), moduleid.toLowerCase() });
		if (list.size() == 0)
			return null;
		FDataobject object = list.get(0);
		if (object.getFModules() == null)
			return null;
		for (FModule m : object.getFModules()) {
			module = m;
			break;
		}
		return module;
	}

	public FModule getModuleInfo(String moduleid) {
		FModule module = getModule(moduleid);
		if (module == null)
			return null;
		Local.writeJsonToHttpFilters(filters);
		return module;
	}

	public Map<String, Set<FovGridscheme>> getGridSchemes(String moduleid) {
		FModule module = getModule(moduleid);
		if (module == null)
			return null;
		Local.writeJsonToHttpFilters(filters);
		return module.getFDataobject().getGridSchemes();
	}

	public FovGridscheme getGridScheme(String schemeid) {
		Local.writeJsonToHttpFilters(filters);
		return dao.findById(FovGridscheme.class, schemeid);
	}

	public FovFilterscheme getFilterScheme(String schemeid) {
		Local.writeJsonToHttpFilters(filters);
		return dao.findById(FovFilterscheme.class, schemeid);
	}

	public JSONObject getSortScheme(String schemeid) {
		FovGridsortscheme scheme = dao.findById(FovGridsortscheme.class, schemeid);
		return scheme._genJson();
	}

}
