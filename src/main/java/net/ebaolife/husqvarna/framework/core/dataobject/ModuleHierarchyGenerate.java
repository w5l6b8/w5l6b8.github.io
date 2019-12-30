package net.ebaolife.husqvarna.framework.core.dataobject;

import net.ebaolife.husqvarna.framework.bean._ModuleAdditionField;
import net.ebaolife.husqvarna.framework.core.dataobject.condition.ConditionService;
import net.ebaolife.husqvarna.framework.core.dataobject.field.AdditionParentModuleField;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.sqlgenerate.FieldGenerate;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectdefaultorder;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Set;

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
public class ModuleHierarchyGenerate {

	private static final Log log = LogFactory.getLog(ModuleHierarchyGenerate.class);

	public static final String CHILDSEPARATOR = ".with";

	public BaseModule genModuleHierarchy(FDataobject module, GenerateParam param, ModuleHierarchyCreateMode mode,
										 BaseModule pbm) {

		log.debug("基准模块：" + module.getTitle() + " 的关联关系结构开始生成......");
		long s = new Date().getTime();
		BaseModule baseModule = new BaseModule();
		baseModule.setModule(module);
		baseModule.setpModule(pbm);
		if (pbm == null)
			baseModule.setAsName("this_");
		else
			baseModule.setAsName(pbm.getAsName() + "_1");

		int pcount = 1;

		if (param != null && param.getUserParentFilters() != null) {
			for (UserParentFilter filter : param.getUserParentFilters()) {
				if (filter.getModuleName().equals(module.getObjectname()) && filter.getFieldahead() == null) {
					if (baseModule.getUserParentFilters() == null)
						baseModule.setUserParentFilters(new ArrayList<UserParentFilter>());
					baseModule.getUserParentFilters().add(filter);
				}
			}
		}

		if (mode == ModuleHierarchyCreateMode.normal) {
			if (param != null && param.getUserDefineFilters() != null) {
				for (UserDefineFilter udf : param.getUserDefineFilters()) {
					if (udf.getProperty().indexOf(".") == -1) {
						if (baseModule.getUserDefineFilters() == null)
							baseModule.setUserDefineFilters(new ArrayList<UserDefineFilter>());
						FDataobjectfield field = baseModule.getModule()._getModuleFieldByFieldName(udf.getProperty());

						udf.setField(field);
						baseModule.getUserDefineFilters().add(udf);
					}
				}
			}

			if (param != null && param.getQuerys() != null && param.getQuerys().size() > 0) {
				for (UserDefineFilter udf : param.getQuerys()) {
					if (udf.getProperty().indexOf(".") == -1) {
						if (baseModule.getQuerys() == null)
							baseModule.setQuerys(new ArrayList<UserDefineFilter>());
						FDataobjectfield field = baseModule.getModule()._getModuleFieldByFieldName(udf.getProperty());

						udf.setField(field);
						baseModule.getQuerys().add(udf);
					}
				}
			}

			if (param != null && param.getUserNavigateFilters() != null) {
				for (UserNavigateFilter filter : param.getUserNavigateFilters()) {
					if (filter.getModuleName().equals(module.getObjectname()) && filter.getFieldahead() == null) {
						if (baseModule.getUserNavigateFilters() == null)
							baseModule.setUserNavigateFilters(new ArrayList<UserNavigateFilter>());
						baseModule.getUserNavigateFilters().add(filter);
					}
				}
			}
		}

		for (FDataobjectfield field : module.getFDataobjectfields())
			if (field._isManyToOne() || field._isOneToOne()) {
				FDataobject pmodule = DataObjectUtils.getDataObject(field.getFieldtype());

				String parentPath = pmodule.getObjectname();
				baseModule.getParents().put(field.getFieldname(),
						genModuleParentHierarchy(mode, null, baseModule, baseModule, field, pmodule, pcount++, 1,
								field.getFieldtitle(), parentPath, module.getObjectname(), param));
			}

		for (String p : baseModule.getParents().keySet()) {
			baseModule.getParents().get(p).setIsDirectParent(true);
		}

		baseModule.calcParentModuleOnlyone();
	    // 如果不是聚合的目标模块，那么组织加入所有的子模块,如果是子模块也要加入子模块了
	    if (mode == ModuleHierarchyCreateMode.normal || mode == ModuleHierarchyCreateMode.navigate_pfield_countSubModule
	        || mode == ModuleHierarchyCreateMode.aggregate) {
	      Set<FDataobjectfield> manytooneFields = DataObjectUtils.getDataObjectManyToOneField(module.getObjectname());
	      if (manytooneFields != null) {
	        for (FDataobjectfield field : manytooneFields) {
	          String childPath = field.getFDataobject().getObjectname() + CHILDSEPARATOR + "." + field.getFieldname();
	          baseModule.getChilds().put(childPath,
	              genModuleChildHierarchy(mode, baseModule, baseModule, field, field.getFDataobject(), 1,
	                  field.getFDataobject().getTitle() + "(" + field.getFieldtitle() + ")", childPath, param));

	        }
	      }
	    }
		if (mode == ModuleHierarchyCreateMode.normal) {
			baseModule.setAllFieldsNameAndSql(FieldGenerate.generateFields(baseModule, null));

		}

		if (param != null && param.getConditionString() != null)
			param.setConditionSql(ConditionService.addConditionInfo(baseModule, param.getConditionString()));
		log.debug("模块：" + module.getTitle() + " 的关联关系结构已生成,用时 " + (new Date().getTime() - s) + "毫秒。");
		return baseModule;

	}

	public ParentModule genModuleParentHierarchy(ModuleHierarchyCreateMode mode, UserSession userSession,
			BaseModule baseModule, Object sonModule, FDataobjectfield field, FDataobject pModule, int pcount, int level,
			String fullname, String parentPath, String proviousPath, GenerateParam param) {

		String proviousAs;
		String proviousAheadField;
		if (sonModule instanceof ParentModule) {
			proviousAs = ((ParentModule) sonModule).getAsName();
			proviousAheadField = ((ParentModule) sonModule).getFieldahead();
		} else {
			proviousAs = ((BaseModule) sonModule).getAsName();
			proviousAheadField = "";
		}

		ParentModule pm = new ParentModule();
		pm.setSonModuleHierarchy(sonModule);
		pm.setModule(pModule);
		pm.setModuleField(field);
		pm.setLevel(level);
		pm.setModulePath(parentPath);
		pm.setOnlyonename(field.getFieldtitle());
		pm.setNamePath(fullname);
		pm.setFieldahead(proviousAheadField + (proviousAheadField.length() == 0 ? "" : ".") + field.getFieldname());

		pm.setAsName(proviousAs + "_" + level + "" + pcount);

		baseModule.getAllParents().put(pm.getFieldahead(), pm);

		pm.setLeftoutterjoin(String.format(" left outer join %s %s on %s = %s", pModule._getTablename(), pm.getAsName(),
				pModule._getPrimaryKeyField()._getSelectName(pm.getAsName()),
				field.getJoincolumnname() == null ? pModule._getPrimaryKeyField()._getSelectName(proviousAs)
						: proviousAs + "." + field.getJoincolumnname()));

		pm.setPrimarykeyField(new AdditionParentModuleField(pm.getFieldahead() + "." + pModule.getPrimarykey(),

				pModule._getPrimaryKeyField()._getSelectName(pm.getAsName())));
		pm.setNameField(new AdditionParentModuleField(pm.getFieldahead() + "." + pModule.getNamefield(),
				pModule._getNameField()._getSelectName(pm.getAsName())));

		log.debug("  加入父模块：" + pm.getNamePath() + "--" + pm.getFieldahead());

		if (param != null && param.getUserParentFilters() != null) {
			for (UserParentFilter filter : param.getUserParentFilters()) {
				if (filter.getModuleName().equals(pm.getModule().getObjectname())
						&& filter.getFieldahead().equals(pm.getFieldahead())) {
					if (pm.getUserParentFilters() == null)
						pm.setUserParentFilters(new ArrayList<UserParentFilter>());
					pm.getUserParentFilters().add(filter);
				}
			}
		}

		if (mode == ModuleHierarchyCreateMode.normal) {
			if (param != null && param.getUserDefineFilters() != null) {
				for (UserDefineFilter udf : param.getUserDefineFilters()) {
					if (udf.getProperty().lastIndexOf(".") > 0) {
						String fieldahead = udf.getProperty().substring(0, udf.getProperty().lastIndexOf("."));
						if (pm.getFieldahead().equals(fieldahead)) {
							if (pm.getUserDefineFilters() == null)
								pm.setUserDefineFilters(new ArrayList<UserDefineFilter>());

							FDataobjectfield udffield = pModule._getModuleFieldByFieldName(
									udf.getProperty().substring(udf.getProperty().lastIndexOf(".") + 1));
							udf.setField(udffield);
							pm.getUserDefineFilters().add(udf);
						}
					}
				}
			}

			if (param != null && param.getQuerys() != null && param.getQuerys().size() > 0) {
				for (UserDefineFilter udf : param.getQuerys()) {
					if (udf.getProperty().lastIndexOf(".") > 0) {
						String fieldahead = udf.getProperty().substring(0, udf.getProperty().lastIndexOf("."));
						if (pm.getFieldahead().equals(fieldahead)) {
							if (pm.getQuerys() == null)
								pm.setQuerys(new ArrayList<UserDefineFilter>());
							FDataobjectfield udffield = pModule._getModuleFieldByFieldName(
									udf.getProperty().substring(udf.getProperty().lastIndexOf(".") + 1));
							udf.setField(udffield);
							pm.getQuerys().add(udf);
						}
					}
				}
			}

			if (param != null && param.getUserNavigateFilters() != null) {
				for (UserNavigateFilter filter : param.getUserNavigateFilters()) {
					if (filter.getModuleName().equals(pm.getModule().getObjectname())
							&& filter.getFieldahead().equals(pm.getFieldahead())) {
						if (pm.getUserNavigateFilters() == null)
							pm.setUserNavigateFilters(new ArrayList<UserNavigateFilter>());
						pm.getUserNavigateFilters().add(filter);
					}
				}
			}
		}

		if (mode == ModuleHierarchyCreateMode.normal) {
			for (_ModuleAdditionField af : baseModule.getModule().getAdditionFields()) {

				if (af.getFieldahead() != null) {
					if (af.getFieldahead().equals(pm.getFieldahead())) {

						FDataobjectfield f = pm.getModule()
								._getModuleFieldByFieldId(af.getFDataobjectfield().getFieldid());

						AdditionParentModuleField pmf = new AdditionParentModuleField();
						pmf.setModuleField(f);
						pmf.setModuleAdditionField(af);
						pmf.setTitle((af.getTitleahead() == null ? fullname : af.getTitleahead()) + "--"
								+ f.getFieldtitle());
						pmf.setAsName(pm.getFieldahead() + "." + f.getFieldname());

						pmf.setFieldsql(f._getSelectName(pm.getAsName()));
						if (pm.getAdditionFields() == null)
							pm.setAdditionFields(new HashMap<String, AdditionParentModuleField>());
						pm.getAdditionFields().put(pmf.getAsName(), pmf);
						log.debug("    附加字段:" + f.getFieldtitle() + ":" + pmf.getFieldsql() + ":" + pmf.getAsName());
					} else {

						if (pm.getFieldahead()
								.equals(af.getFieldahead() + "." + af.getFDataobjectfield().getFieldname())) {
							pm.setInsertIdAndNameFields(true);

						}
					}
				}
			}

			if (param != null)
				if (param.getSortParameters() == null || param.getSortParameters().size() == 0) {
					if (baseModule.getModule().getFDataobjectdefaultorders() != null
							&& (baseModule.getModule().getFDataobjectdefaultorders().size() > 0)) {
						for (FDataobjectdefaultorder moduleDefaultOrder : baseModule.getModule()
								.getFDataobjectdefaultorders()) {
							if (moduleDefaultOrder.getFieldahead() != null
									&& moduleDefaultOrder.getFieldahead().equals(pm.getFieldahead())) {
								break;
							}
						}
					}
				}

		}

		pcount = 1;
		for (

		FDataobjectfield mfield : pModule.getFDataobjectfields())
			if (mfield._isManyToOne() || mfield._isOneToOne()) {
				FDataobject pmodule = DataObjectUtils.getDataObject(mfield.getFieldtype());

				String parentPath1 = parentPath + "--" + pmodule.getObjectname();

				pm.getParents().put(pm.getFieldahead() + "." + mfield.getFieldname(),
						genModuleParentHierarchy(mode, userSession, baseModule, pm, mfield, pmodule, pcount++,
								level + 1, fullname + "--" + mfield.getFieldtitle(), parentPath1, parentPath, param));
			}
		return pm;
	}

	public ChildModule genModuleChildHierarchy(ModuleHierarchyCreateMode mode, BaseModule baseModule,
			Object parentModule, FDataobjectfield field, FDataobject pModule, int level, String fullname,
			String childPath, GenerateParam param) {

		if (level == 10)
			return null;
		ChildModule cm = new ChildModule();
		cm.setParentModuleHierarchy(parentModule);
		cm.setModule(pModule);
		cm.setModuleField(field);
		cm.setLevel(level);
		cm.setModulePath(childPath);
		cm.setFieldahead(childPath);

		cm.setNamePath(fullname);
		baseModule.getAllChilds().put(cm.getFieldahead(), cm);
		for (FDataobjectfield f : DataObjectUtils.getDataObjectManyToOneField(pModule.getObjectname())) {
			String fieldType = f.getFieldtype();
			if (fieldType.equals(pModule.getObjectname())) {
				String childPath1 = f.getFDataobject().getObjectname() + CHILDSEPARATOR + "."
						+ childPath.replaceFirst(pModule.getObjectname() + CHILDSEPARATOR, f.getFieldname());
				String fullname1 = f.getFDataobject().getTitle() + "("
						+ fullname.replaceFirst(pModule.getTitle() + "\\(", f.getFieldtitle() + "--");
				cm.getChilds().put(childPath1, genModuleChildHierarchy(mode, baseModule, cm, f, f.getFDataobject(),
						level + 1, fullname1, childPath1, param));
			}

		}
		return cm;
	}

}
