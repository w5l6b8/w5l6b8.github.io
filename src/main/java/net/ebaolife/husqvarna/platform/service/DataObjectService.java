package net.ebaolife.husqvarna.platform.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import net.ebaolife.husqvarna.framework.bean.*;
import net.ebaolife.husqvarna.framework.core.dataobject.ModuleDataDAO;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.grid.GridColumn;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.TreeModuleDataDAO;
import net.ebaolife.husqvarna.framework.core.dataobjectquery.generate.SqlGenerate;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.Dao;
import net.ebaolife.husqvarna.framework.dao.SqlMapperAdapter;
import net.ebaolife.husqvarna.framework.dao.entity.attachment.FDataobjectattachment;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectview;
import net.ebaolife.husqvarna.framework.dao.entity.log.FUseroperatelog;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridsortscheme;
import net.ebaolife.husqvarna.framework.exception.DataDeleteException;
import net.ebaolife.husqvarna.framework.utils.*;
import net.ebaolife.husqvarna.platform.logic.define.LogicInterface;
import ognl.Ognl;
import ognl.OgnlException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

@Service
@SuppressWarnings("unchecked")

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
public class DataObjectService extends SqlMapperAdapter {

	@Resource
	private ModuleDataDAO moduleDataDAO;

	@Resource
	private TreeModuleDataDAO treeModuleDataDAO;

	@Resource
	private CodeLevelDataobjectService codeLevelDataobjectService;

	public PageInfo<Map<String, Object>> fetchDataInner(String moduleName, GridParams pg, List<GridColumn> columns,
														GroupParameter group, List<SortParameter> sorts, List<UserDefineFilter> querys,
														List<UserDefineFilter> userDefineFilters, List<UserNavigateFilter> userNavigateFilters,
														List<UserParentFilter> userParentFilters, FovGridsortscheme sortscheme, JSONObject sqlparam) {
		PageInfo<Map<String, Object>> info = new PageInfo<Map<String, Object>>(pg.getStart(), pg.getLimit());
		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		if (module == null)
			return info;

		SqlGenerate generate = new SqlGenerate();
		generate.setDataobject(module);
		generate.setSqlparam(sqlparam);
		generate.setUserDefineFilters(userDefineFilters);
		generate.setSearchFieldQuerys(querys);
		generate.setUserNavigateFilters(userNavigateFilters);
		generate.setUserParentFilters(userParentFilters);

		generate.setGridsortscheme(sortscheme);
		generate.setSortParameters(sorts);
		generate.setGroup(group);

		UserDefineFilter queryFilter = null;
		if (userDefineFilters != null) {
			for (UserDefineFilter filter : userDefineFilters) {
				if (filter.getProperty().equals("_query_")) {
					queryFilter = filter;
					break;
				}
			}
		}
		if (queryFilter != null)
			generate.getUserDefineFilters().remove(queryFilter);
		generate.pretreatment();

		String sql = generate.generateSelect();
		Dao dao = Local.getDao();
		String[] fields = generate.getFieldNames();
		if (!pg.isPaging())
			pg.setLimit(Integer.MAX_VALUE);
		int total = dao.selectSQLCount(generate.generateSelectCount());
		return total == 0 ? info
				: dao.executeSQLQueryPage(sql, fields, pg.getStart(), pg.getLimit(), total, new Object[] {});
	}

	public JSONObject fetchTreeDataInner(String moduleName, GridParams pg, List<GridColumn> columns,
			List<SortParameter> sort, List<UserDefineFilter> query, List<UserDefineFilter> filter,
			List<UserNavigateFilter> navigates, List<UserParentFilter> userParentFilters, FDataobjectview viewscheme) {
		return treeModuleDataDAO.getTreeModuleData(moduleName, filter, navigates, userParentFilters, sort);

	}

	public ResultBean fetchInfo(String objectname, String id) {
		return new ResultBean(true, getObjectRecordMap(objectname, id));
	}

	public Map<String, Object> getObjectRecordMap(String objectname, String id) {
		FDataobject dataObject = DataObjectUtils.getDataObject(objectname);
		SqlGenerate generate = new SqlGenerate();
		generate.setDataobject(dataObject);
		generate.setIdvalue(id);
		String sql = generate.pretreatment().generateSelect();
		String[] fields = generate.getFieldNames();
		Map<String, Object> map = dao.executeSQLQueryFirst(sql, fields, new Object[] {});
		return map;
	}

	@SuppressWarnings("rawtypes")
	public ResultBean getRecordNewDefault(String objectname, String parentfilter, String navigates) throws Exception {
		ResultBean result = new ResultBean();
		FDataobject dataObject = DataObjectUtils.getDataObject(objectname);
		objectname = dataObject.getObjectname();
		List<UserParentFilter> userParentFilters = UserParentFilter.changeToParentFilters(parentfilter, objectname);
		List<UserNavigateFilter> navigateFilters = UserNavigateFilter.changeToNavigateFilters(navigates);
		Map<String, Object> map = new HashMap<String, Object>();
		setDefaultData("default", map);
		Object logic = Local.getBean(dataObject.getObjectname() + "Logic");
		if (logic != null && logic instanceof LogicInterface) {
			Map<String, Object> defaults = ((LogicInterface) logic).getNewDefultValue(userParentFilters,
					navigateFilters);
			if (defaults != null)
				map.putAll(defaults);
			result.setData(map);
		}
		return result;
	}

	public ResultBean saveOrUpdate(String objectname, String inserted, String oldid, String opertype)
			throws ClassNotFoundException, OgnlException, IllegalAccessException, InvocationTargetException {
		MapBean params = new MapBean();
		FDataobject dataObject = DataObjectUtils.getDataObject(objectname);
		objectname = dataObject.getObjectname();
		ResultBean result = new ResultBean();
		inserted = adjustNullParentId(inserted);
		Object logic = Local.getBean(dataObject.getObjectname() + "Logic");
		Class<?> clazz = Class.forName(dataObject.getClassname());
		{
			params.add("inserted", inserted).add("oldid", oldid).add("opertype", opertype);
			inserted = String.valueOf(params.get("inserted"));
		}
		Object bean = JSON.parseObject(inserted, clazz, new KeyExtraProcessor());
		if (opertype.equals("new")) {
			setDefaultData(opertype, bean);

			if (dataObject._isCodeLevel()) {
				codeLevelDataobjectService.addCodeLevelModuleKey(dataObject,
						Ognl.getValue(dataObject._getPrimaryKeyField().getFieldname(), bean).toString(), clazz);
			}

			DataobjectFieldConstraintUtils.moduleFieldConstraintValid(dataObject, bean);

			if (logic != null && logic instanceof LogicInterface)
				((LogicInterface<Object>) logic).beforeInsert(bean);
			dao.save(bean);
			if (logic != null && logic instanceof LogicInterface)
				((LogicInterface<Object>) logic).afterInsert(bean);

			Serializable id = dao.getIdentifier(bean);
			result = fetchInfo(objectname, id.toString());
			saveOperateLog(dataObject, id.toString(), getRecordNameValue(dataObject, bean), "new", inserted);
		} else if (opertype.equals("edit")) {
			Map<String, Object> map = JSON.parseObject(inserted);
			Serializable keyName = dao.getIdentifierPropertyName(clazz);
			Serializable keyValue = dao.getIdentifier(bean);
			if (!CommonUtils.isEmpty(oldid)) {
				if (dataObject._isCodeLevel()) {
					codeLevelDataobjectService.replaceCodeLevelModuleKey(dataObject, oldid.toString(),
							keyValue.toString(), clazz);
				}
				String upsql = "update " + clazz.getSimpleName() + " set " + keyName + " = ? where " + keyName + " = ?";
				dao.executeUpdate(upsql, keyValue, oldid);

			}
			map.remove(keyName);
			String[] upfield = map.keySet().toArray(new String[] {});

			for (int i = 0; i < upfield.length; i++) {
				boolean isentity = false;
				if (upfield[i].indexOf('.') > 0) {
					isentity = true;
					upfield[i] = upfield[i].substring(0, upfield[i].indexOf('.'));
				}
				if (upfield[i].indexOf("_") != -1) {
					upfield[i] = CommonUtils.underlineToCamelhump(upfield[i]);
					if (isentity) {
						upfield[i] = CommonUtils.firstCharacterUpperCase(upfield[i]);
					}
				}
			}
			Object oldentitybean = dao.findById(clazz, keyValue);
			dao.evict(oldentitybean);
			Object entitybean = dao.findById(clazz, keyValue);
			setDefaultData(opertype, entitybean);
			if (upfield.length > 0)
				BeanUtils.copyProperties(entitybean, bean, upfield);

			DataobjectFieldConstraintUtils.moduleFieldConstraintValid(dataObject, entitybean);

			if (logic != null && logic instanceof LogicInterface)
				((LogicInterface<Object>) logic).beforeUpdate("edit", entitybean, oldentitybean);
			dao.update(entitybean);
			if (logic != null && logic instanceof LogicInterface)
				((LogicInterface<Object>) logic).afterUpdate("edit", entitybean, oldentitybean);

			result = fetchInfo(objectname, keyValue.toString());
			saveOperateLog(dataObject, keyValue.toString(), getRecordNameValue(dataObject, entitybean), "edit",
					inserted);
		}
		return result;
	}

	private String adjustNullParentId(String inserted) {
		JSONObject object = JSONObject.parseObject(inserted);
		List<String> keys = new ArrayList<String>();
		for (String name : object.keySet()) {
			if (name.indexOf('.') != -1 && object.get(name) == null) {
				keys.add(name);
			}
		}
		for (String name : keys) {
			String[] sep = name.split("\\.");
			object.put(sep[0], object.get(name));
			object.remove(name);
		}
		return JSONObject.toJSONString(object, SerializerFeature.WriteMapNullValue);
	}

	public String getRecordNameValue(FDataobject module, Object record) {
		String result = "";
		try {
			result = (module.getNamefield() != null && module.getNamefield().length() > 0)
					? Ognl.getValue(module.getNamefield(), record).toString() : "未定义";
		} catch (Exception e) {
		}
		return result;
	}

	private void setDefaultData(String type, Object bean) {
		try {
			UserBean userbean = Local.getUserBean();
			if (userbean == null)
				return;
			if ((bean instanceof Map) && ("default".equals(type))) {
				Map<String, Object> map = (Map<String, Object>) bean;
				map.put("creater", userbean.getUserid());
				map.put("createdate", DateUtils.getTimestamp());
				map.put("companyid", Local.getCompanyid());
				map.put("FCompany.companyid", Local.getCompanyid());
				map.put("FCompany.companyname", Local.getUserBean().getCompanyname());
			} else {
				Field[] fields = bean.getClass().getDeclaredFields();
				for (int i = 0; i < fields.length; i++) {
					Field field = fields[i];
					field.setAccessible(true);
					String propertyName = field.getName();
					Class<?> propertyType = field.getType();
					Object obj = field.get(bean);
					if ("new".equals(type)) {
						if (!CommonUtils.isEmpty(obj))
							continue;
						if ("creater".equals(propertyName) && propertyType.equals(String.class)) {
							field.set(bean, userbean.getUserid());
						} else if ("createdate".equals(propertyName) && Date.class.isAssignableFrom(propertyType)) {
							field.set(bean, DateUtils.getTimestamp());
						} else if ("companyid".equals(propertyName) && propertyType.equals(String.class)) {
							field.set(bean, Local.getCompanyid());
						}
					}
					if ("lastmodifier".equals(propertyName) && propertyType.equals(String.class)) {
						field.set(bean, userbean.getUserid());
					} else if ("lastmodifydate".equals(propertyName) && Date.class.isAssignableFrom(propertyType)) {
						field.set(bean, DateUtils.getTimestamp());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DataDeleteResponseInfo remove(String objectname, String removed) {

		DataDeleteResponseInfo result = new DataDeleteResponseInfo();

		FDataobject dataObject = DataObjectUtils.getDataObject(objectname);

		List<FDataobjectattachment> attachments = dao.findByProperty(FDataobjectattachment.class, "objectid",
				dataObject.getObjectid(), "idvalue", removed);
		if (attachments.size() > 0) {
			throw new DataDeleteException("本记录有附件信息，请先删除所有附件！");
		}

		if (dataObject.getCodelevel() != null && dataObject.getCodelevel().length() > 0) {
			codeLevelDataobjectService.deleteCodeLevelModuleKey(dataObject, removed);
		}

		Class<?> beanClass = null;
		if (dataObject.getClassname() == null) {
			throw new DataDeleteException(dataObject.getTitle() + "的实体bean名称没有设置！");
		}
		try {
			beanClass = Class.forName(dataObject.getClassname());
		} catch (ClassNotFoundException e) {
			throw new DataDeleteException(dataObject.getTitle() + "的实体bean没有找到！");
		}

		Object record = dao.findById(beanClass, removed);

		Object logic = Local.getBean(dataObject.getObjectname() + "Logic");
		if (logic != null && logic instanceof LogicInterface)
			((LogicInterface<Object>) logic).beforeDelete(record);
		dao.delete(record);
		if (logic != null && logic instanceof LogicInterface)
			((LogicInterface<Object>) logic).afterDelete(record);

		saveOperateLog(dataObject, removed, getRecordNameValue(dataObject, record), "delete", null);
		result.setResultCode(0);
		return result;

	}

	public List<ValueText> fetchModuleComboData(String moduleName, String query) {
		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		List<UserDefineFilter> userDefineFilters = null;
		List<ValueText> keys = null;
		if (query != null && query.length() > 0) {
			userDefineFilters = new ArrayList<UserDefineFilter>();
			UserDefineFilter keyfilter = new UserDefineFilter();
			keyfilter.setProperty(module._getPrimaryKeyField().getFieldname());
			keyfilter.setOperator("=");
			keyfilter.setValue(query);
			userDefineFilters.add(keyfilter);

			keys = moduleDataDAO.getRecordWithIdAndName(moduleName, userDefineFilters, null);

			userDefineFilters.clear();
			UserDefineFilter namelikefilter = new UserDefineFilter();
			namelikefilter.setProperty(module._getNameField().getFieldname());
			namelikefilter.setOperator("like");
			namelikefilter.setValue(query);
			userDefineFilters.add(namelikefilter);

		}

		List<ValueText> names = moduleDataDAO.getRecordWithIdAndName(moduleName, userDefineFilters, null);
		if (keys != null && keys.size() > 0) {

			for (int i = names.size() - 1; i >= 0; i++) {
				if (names.get(i).getValue().equals(keys.get(0).getValue())) {
					names.remove(i);
					break;
				}
			}
			names.add(0, keys.get(0));
		}
		return names;
	}

	public List<TreeValueText> getModuleWithTreeData(String moduleName, boolean allowParentValue, Object object) {
		return moduleDataDAO.getRecordWithTreeData(moduleName, allowParentValue, null, null);
	}

	public List<TreeNodeRecordChecked> getManyToManyDetail(HttpServletRequest request, String moduleName, String id,
			String manyToManyModuleName, String linkModuleName) {
		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		FDataobject manyToManyModule = DataObjectUtils.getDataObject(manyToManyModuleName);
		FDataobject linkedModule = DataObjectUtils.getDataObject(linkModuleName);
		List<TreeNodeRecord> result = new ArrayList<TreeNodeRecord>();

		List<ValueText> allTreeItems = moduleDataDAO.getRecordWithIdAndName(manyToManyModuleName, null, null);
		for (ValueText vt : allTreeItems) {
			TreeNodeRecordChecked record = new TreeNodeRecordChecked();
			record.setFieldvalue(vt.getValue());
			record.setText(vt.getText());
			record.setLeaf(true);
			result.add(record);
		}
		String fn = null;
		for (FDataobjectfield field : linkedModule.getFDataobjectfields()) {
			if (field._isManyToOne() && field.getFieldtype().equals(moduleName)) {
				fn = field.getFieldname();
				break;
			}
		}
		UserDefineFilter filter = new UserDefineFilter();
		filter.setProperty(fn + "." + module.getPrimarykey());
		filter.setOperator("eq");
		filter.setValue(id);
		JSONArray dataArray = moduleDataDAO.getRecords(linkModuleName, filter);
		String manytomanyfn = null;
		for (FDataobjectfield field : linkedModule.getFDataobjectfields()) {
			if (field._isManyToOne() && field.getFieldtype().equals(manyToManyModuleName)) {
				manytomanyfn = field.getFieldname();
				break;
			}
		}

		for (int i = 0; i < dataArray.size(); i++) {
			String manytomanyid = dataArray.getJSONObject(i)
					.getString(manytomanyfn + "." + manyToManyModule.getPrimarykey());
			for (TreeNodeRecord record : result) {
				if (record.getFieldvalue().equals(manytomanyid))
					((TreeNodeRecordChecked) record).setChecked(true);
			}
		}
		List<TreeNodeRecordChecked> root = new ArrayList<TreeNodeRecordChecked>();
		TreeNodeRecordChecked rootrecord = new TreeNodeRecordChecked();
		rootrecord.setText(manyToManyModule.getTitle());
		rootrecord.setChildren(result);
		rootrecord.setExpanded(true);
		root.add(rootrecord);
		return root;
	}

	public ActionResult setManyToManyDetail(String moduleName, String id, String manyToManyModuleName,
			String linkModuleName, String[] selected)
			throws ClassNotFoundException, OgnlException, IllegalAccessException, InvocationTargetException {

		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		FDataobject manyToManyModule = DataObjectUtils.getDataObject(manyToManyModuleName);
		FDataobject linkedModule = DataObjectUtils.getDataObject(linkModuleName);

		String fn = null;
		for (FDataobjectfield field : linkedModule.getFDataobjectfields()) {
			if (field._isManyToOne() && field.getFieldtype().equals(moduleName)) {
				fn = field.getFieldname();
				break;
			}
		}
		UserDefineFilter filter = new UserDefineFilter();
		filter.setProperty(fn + "." + module.getPrimarykey());
		filter.setOperator("eq");
		filter.setValue(id);
		JSONArray dataArray = moduleDataDAO.getRecords(linkModuleName, filter);

		String manytomanyfn = null;
		for (FDataobjectfield field : linkedModule.getFDataobjectfields()) {
			if (field._isManyToOne() && field.getFieldtype().equals(manyToManyModuleName)) {
				manytomanyfn = field.getFieldname();
				break;
			}
		}

		for (int i = 0; i < dataArray.size(); i++) {
			String manytomanyid = dataArray.getJSONObject(i)
					.getString(manytomanyfn + "." + manyToManyModule.getPrimarykey());
			boolean isfound = false;
			for (String selectedid : selected) {
				if (manytomanyid.equals(selectedid)) {
					isfound = true;
					break;
				}
			}
			if (!isfound) {

				remove(linkModuleName, dataArray.getJSONObject(i).getString(linkedModule.getPrimarykey()));
			}
		}

		for (String selectedid : selected) {
			if (selectedid.length() > 0) {
				boolean isfound = false;
				for (int i = 0; i < dataArray.size(); i++) {
					String manytomanyid = dataArray.getJSONObject(i)
							.getString(manytomanyfn + "." + manyToManyModule.getPrimarykey());
					if (manytomanyid.equals(selectedid)) {
						isfound = true;
						break;
					}
				}
				if (!isfound) {
					JSONObject object = new JSONObject();
					object.put(manytomanyfn + "." + manyToManyModule.getPrimarykey(), selectedid);
					object.put(fn + "." + module.getPrimarykey(), id);

					saveOrUpdate(linkModuleName, object.toString(), null, "new");
				}
			}
		}
		ActionResult result = new ActionResult();
		return result;
	}

	public ActionResult UpdateOrderno(String moduleName, String[] ids) throws ClassNotFoundException, OgnlException {
		FDataobject dataObject = DataObjectUtils.getDataObject(moduleName);
		Class<?> clazz = Class.forName(dataObject.getClassname());
		int recordno = 10;
		List<ValueText> valuetexts = new ArrayList<ValueText>();
		for (String id : ids) {
			Object bean = dao.findById(clazz, id);
			Ognl.setValue(dataObject.getOrderfield(), bean, recordno);
			valuetexts.add(new ValueText(id, recordno + ""));
			recordno += 10;
		}
		ActionResult result = new ActionResult();
		result.setMsg(valuetexts);
		result.setTag(dataObject.getOrderfield());
		return result;
	}

	public ActionResult UpdateParentkey(String objectname, String id, String parentkey) throws ClassNotFoundException {
		FDataobject dataObject = DataObjectUtils.getDataObject(objectname);
		Class<?> clazz = Class.forName(dataObject.getClassname());
		clazz.getClass();
		String upsql = "update " + dataObject._getTablename() + " this_ set " + dataObject.getParentkey()
				+ " = ? where " + dataObject._getPrimaryKeyField()._getSelectName("this_") + " = ?";
		dao.executeSQLUpdate(upsql, new Object[] { parentkey, id });
		return new ActionResult();
	}

	public FUseroperatelog saveOperateLog(FDataobject dataobject, String id, String name, String doword,
										  String remark) {

		if (doword != null) {
			if (doword.equals("new") || doword.equals("add"))
				doword = "新增";
			if (doword.equals("edit"))
				doword = "修改";
			if (doword.equals("delete"))
				doword = "删除";
			else if (doword.equals("auditing"))
				doword = "审核";
			else if (doword.equals("cancelauditing"))
				doword = "取消审核";
			else if (doword.equals("approve"))
				doword = "审批";
			else if (doword.equals("cancelapprove"))
				doword = "取消审批";
		}
		FUseroperatelog operateLog = new FUseroperatelog();
		operateLog.setOdate(new Date());
		operateLog.setDotype(doword);
		operateLog.setIpaddress(CommonFunction.getIpAddr(Local.getRequest()));
		operateLog.setFDataobject(dataobject);
		operateLog.setIdvalue(id);
		if (name != null && name.length() > 200)
			name = name.substring(0, 200);
		operateLog.setNamevalue(name);
		if (remark != null && remark.length() > 2000)
			remark = remark.substring(0, 2000);
		operateLog.setOcontent(remark);
		operateLog.setFUser(dao.findById(FUser.class, Local.getUserid()));
		dao.save(operateLog);
		return operateLog;
	}

}
