package net.ebaolife.husqvarna.platform.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.bean.TableBean;
import net.ebaolife.husqvarna.framework.bean.TableFieldBean;
import net.ebaolife.husqvarna.framework.bean.TreeValueText;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.SqlMapperAdapter;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectbasefuncion;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectgroup;
import net.ebaolife.husqvarna.framework.dao.entity.module.*;
import net.ebaolife.husqvarna.framework.dao.entity.system.FCompany;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.utils.FUserview;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.*;
import net.ebaolife.husqvarna.framework.utils.*;
import org.hibernate.Session;
import org.hibernate.query.NativeQuery;
import org.springframework.stereotype.Service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
public class DatabaseService extends SqlMapperAdapter {
	public JSONArray getSchemas() throws SQLException {

		Connection conn = dao.getConnection();
		String defaultschema = conn.getCatalog();
		JSONArray result = new JSONArray();
		JSONObject object = new JSONObject();
		object.put("text", "默认数据库");
		result.add(object);

		for (String string : sf.getSchemas(dao)) {
			if (string.equalsIgnoreCase(defaultschema))
				continue;
			object = new JSONObject();
			object.put("text", string);
			result.add(object);
		}
		return result;
	}

	public TreeValueText getNotImportTableAndViews(String schema) throws SQLException {

		List<TreeValueText> tablelist = new ArrayList<TreeValueText>();
		List<TreeValueText> viewlist = new ArrayList<TreeValueText>();

		String defaultSchema = dao.getConnection().getCatalog();
		schema = (schema == null || schema.length() == 0) ? defaultSchema : schema;
		boolean isdefault = schema.equals(defaultSchema);

		List<String> tables = sf.getTables(dao, schema);
		List<String> views = sf.getViews(dao, schema);

		List<FDataobject> dataobjects = dao.findByProperty(FDataobject.class, "schemaname", isdefault ? null : schema);
		for (String table : tables) {
			boolean found = false;
			for (FDataobject object : dataobjects) {
				if (object.getTablename().equalsIgnoreCase(table)) {
					found = true;
					break;
				}
			}
			if (!found) {
				tablelist.add(new TreeValueText(table, table, null, null, true));
			}
		}
		if (views != null)
			for (String view : views) {
				boolean found = false;
				for (FDataobject object : dataobjects)
					if (object.getTablename().equalsIgnoreCase(view)) {
						found = true;
						break;
					}
				if (!found) {
					viewlist.add(new TreeValueText(view, view, null, null, true));
				}
			}
		TreeValueText table = new TreeValueText("table", "table", true, null, false);
		table.setChildren(tablelist);
		TreeValueText view = new TreeValueText("view", "view", true, null, false);
		view.setChildren(viewlist);
		TreeValueText result = new TreeValueText("allobject", "所有对象", true, null, false);
		if (!tablelist.isEmpty())
			result.getChildren().add(table);
		if (!viewlist.isEmpty())
			result.getChildren().add(view);
		return result;
	}

	public List<TableFieldBean> getFields(String schema, String tablename) {
		if (tablename == null || tablename.length() == 0)
			return new ArrayList<TableFieldBean>();
		TableBean table = sf.getTable(dao, tablename, schema);
		for (TableFieldBean field : table.getFields()) {
			if (field.getFieldrelation() != null && field.getFieldrelation().equalsIgnoreCase("manytoone")) {
				String jointable = field.getJointable();
				FDataobject joinobject = dao.findByPropertyFirst(FDataobject.class, "schemaname", schema,
						"lower(tablename)", jointable);
				if (joinobject == null && !jointable.equalsIgnoreCase(tablename))
					field.setBy5("<span style=\"color:red;\">关联表还没有加入，请先加入该表</span>");
			}
		}
		return table.getFields();
	}

	public String getNaviateName(String objectname) {
		Random random = new Random();
		String s = "";
		s += (char) ('a' + random.nextInt(26));
		for (int i = 0; i < 3; i++) {
			int r = random.nextInt(26 + 10);
			char c;
			if (r >= 26)
				c = (char) ('0' + (r - 26));
			else
				c = (char) ('a' + r);
			s = s + c;
		}
		if (dao.findByPropertyFirst(FDataobject.class, "nativename", s) != null)
			return getNaviateName("");
		return s;
	}

	public ActionResult importTableOrView(String schema, String tablename, String title1, String namefield,
										  boolean addtoadmin, boolean addtomenu, FCompanymenu pmenu)
			throws IllegalAccessException, InvocationTargetException {
		tablename = tablename.toLowerCase();

		System.out.println(schema + "--" + tablename);
		if (CommonUtils.isEmpty(tablename))
			return null;

		schema = schema == null || schema.length() == 0 ? null : schema;
		TableBean tablebean = sf.getTable(dao, tablename, schema);

		FDataobject object = new FDataobject();
		BeanUtils.copyProperties(object, tablebean);

		if (object.getClassname() != null && object.getClassname().length() > 0)
			object.setObjectname(object.getClassname().substring(object.getClassname().lastIndexOf('.') + 1));
		else
			object.setObjectname(CommonUtils.firstCharacterUpperCase(CommonUtils.underlineToCamelhump(tablename)));

		object.setNativename(getNaviateName(object.getObjectname() + object.getObjectname() + object.getObjectname()));

		String objecttitle = title1.toLowerCase();
		if (objecttitle.equals(tablename)) {
			if (tablebean.getComment() != null && tablebean.getComment().length() > 0) {
				objecttitle = tablebean.getComment();
				if (objecttitle.indexOf('[') >= 0)
					objecttitle = objecttitle.substring(0, objecttitle.indexOf('['));
			}
		}
		object.setTitle(objecttitle);
		object.setSchemaname(schema);
		object.setNamefield(namefield);
		object.setHasenable(true);
		object.setHasbrowse(true);
		object.setHasinsert(true);
		object.setHasedit(true);
		object.setHasdelete(true);
		String userid;
		if (Local.getUserBean() == null)
			userid = dao.findByPropertyFirst(FUser.class, "usercode", "admin").getUserid();
		else
			userid = Local.getUserid();

		object.setCreater(userid);
		object.setCreatedate(DateUtils.getTimestamp());

		object.setGriddesign(true);
		object.setGridshare(false);
		object.setViewdesign(false);
		object.setViewshare(false);
		object.setFilterdesign(true);
		object.setFiltershare(false);
		object.setConditiondesign(true);
		object.setConditionshare(true);
		object.setNavigatedesign(true);
		object.setNavigateshare(true);
		object.setSortdesign(true);
		object.setSortdesign(false);

		if (pmenu != null)
			object.setFDataobjectgroup(
					dao.findByPropertyFirst(FDataobjectgroup.class, "groupname", pmenu.getMenuname()));

		if (object.getFDataobjectgroup() == null) {
			FDataobjectgroup group;
			if (schema != null) {
				group = dao.findByPropertyFirst(FDataobjectgroup.class, "groupname", schema + "的模块");
				if (group == null) {
					group = new FDataobjectgroup(schema + "的模块", 1, null);
				}
			} else {
				group = dao.findByPropertyFirst(FDataobjectgroup.class, "groupname", "新加入的模块");
				if (group == null) {
					group = new FDataobjectgroup("新加入的模块", 1, null);
				}
			}
			group.setOrderno((int) selectMax("F_DataObjectGroup", "orderno") + 1);
			dao.save(group);
			object.setFDataobjectgroup(group);
		}
		object.setOrderno((int) selectMax("F_Dataobject", "orderno",
				"objectgroupid = '" + object.getFDataobjectgroup().getObjectgroupid() + "'") + 1);

		System.out.println(object.getNativename());

		dao.save(object);

		FModule module = new FModule();
		module.setCreater(userid);
		module.setCreatedate(DateUtils.getTimestamp());
		module.setFDataobject(object);
		module.setModulecode(object.getObjectname());
		module.setModuletype("03");
		module.setIsvalid(true);
		module.setModulename(objecttitle);
		if (pmenu != null)
			module.setFModulegroup(dao.findByPropertyFirst(FModulegroup.class, "groupname", pmenu.getMenuname()));

		if (module.getFModulegroup() == null) {
			FModulegroup group;
			if (schema != null) {
				group = dao.findByPropertyFirst(FModulegroup.class, "groupname", schema + "的模块");
				if (group == null) {
					group = new FModulegroup(schema + "的模块", 100);
					dao.save(group);
				}
			} else {
				group = dao.findByPropertyFirst(FModulegroup.class, "groupname", "新加入的模块");
				if (group == null) {
					group = new FModulegroup("新加入的模块", 100);
					dao.save(group);
				}
			}
			group.setOrderno((int) selectMax("f_modulegroup", "orderno") + 1);
			module.setFModulegroup(group);
		}
		module.setOrderno((int) selectMax("F_Module", "orderno",
				"ModuleGroupID = '" + module.getFModulegroup().getModulegroupid() + "'") + 1);
		dao.save(module);

		FCompanymodulegroup companymodulegroup = null;
		if (pmenu != null)
			companymodulegroup = dao.findByPropertyFirst(FCompanymodulegroup.class, "groupname", pmenu.getMenuname());
		if (companymodulegroup == null) {

			companymodulegroup = dao.findByPropertyFirst(FCompanymodulegroup.class, "companyid", "00", "groupname",
					"新加入的模块组");
			if (companymodulegroup == null) {
				companymodulegroup = new FCompanymodulegroup();
				companymodulegroup.setFCompany(dao.findById(FCompany.class, "00"));
				companymodulegroup.setGroupname("新加入的模块组");
				companymodulegroup.setOrderno(100);
				companymodulegroup.setOrderno((int) selectMax("f_companymodulegroup", "orderno") + 1);
				dao.save(companymodulegroup);
			}
		}

		FCompanymodule companymodule = new FCompanymodule();
		companymodule.setFCompany(dao.findById(FCompany.class, "00"));
		companymodule.setFCompanymodulegroup(companymodulegroup);
		companymodule.setFModule(module);
		dao.save(companymodule);

		if (pmenu == null) {
			if (schema != null) {
				pmenu = dao.findByPropertyFirst(FCompanymenu.class, "companyid", "00", "menuname", schema + "的模块");
				if (pmenu == null) {
					pmenu = new FCompanymenu(dao.findById(FCompany.class, "00"), schema + "的模块", 0, userid,
							DateUtils.getTimestamp());
				}
			} else {
				pmenu = dao.findByPropertyFirst(FCompanymenu.class, "companyid", "00", "menuname", "新加入的模块组");
				if (pmenu == null) {
					pmenu = new FCompanymenu(dao.findById(FCompany.class, "00"), "新加入的模块组", 0, userid,
							DateUtils.getTimestamp());
				}
			}
			String where = CommonUtils.isEmpty(pmenu.getFCompanymenu()) ? ""
					: "ParentID = '" + pmenu.getFCompanymenu().getMenuid() + "'";
			pmenu.setOrderno((int) selectMax("f_companymenu", "orderno", where) + 1);
			dao.save(pmenu);
		}

		FCompanymenu menu = new FCompanymenu(dao.findById(FCompany.class, "00"), objecttitle, 0, userid,
				DateUtils.getTimestamp());
		menu.setFCompanymenu(pmenu);
		menu.setFCompanymodule(companymodule);
		menu.setIsdisplay(true);

		String where = CommonUtils.isEmpty(menu.getFCompanymenu()) ? ""
				: "ParentID = '" + menu.getFCompanymenu().getMenuid() + "'";
		menu.setOrderno((int) selectMax("f_companymenu", "orderno", where) + 1);
		dao.save(menu);

		List<FDataobjectbasefuncion> list = dao.findAll(FDataobjectbasefuncion.class);
		for (int i = 0; i < list.size(); i++) {
			FModulefunction mf = new FModulefunction();
			mf.setFCompanymodule(companymodule);
			mf.setFDataobjectbasefuncion(list.get(i));
			mf.setIsvalid(true);
			mf.setOrderno(i + 1);
			dao.save(mf);
		}

		FovDataobjectassociate assoc = new FovDataobjectassociate();
		assoc.setFDataobject(object);
		assoc.setRegion("east");
		assoc.setWorh("350");
		assoc.setIshidden(true);
		assoc.setIscollapsed(true);
		assoc.setIsdisabledesign(false);
		dao.save(assoc);

		assoc = new FovDataobjectassociate();
		assoc.setFDataobject(object);
		assoc.setRegion("south");
		assoc.setWorh("350");
		assoc.setIshidden(true);
		assoc.setIscollapsed(true);
		assoc.setIsdisabledesign(false);
		dao.save(assoc);

		FovGridscheme gridscheme = new FovGridscheme();
		gridscheme.setFDataobject(object);
		gridscheme.setSchemename("默认方案");
		gridscheme.setOrderno(1);
		gridscheme.setFUser(dao.findByPropertyFirst(FUser.class, "usercode", "admin"));
		dao.save(gridscheme);

		FovFormscheme formscheme = new FovFormscheme();
		formscheme.setFDataobject(object);
		formscheme.setSchemename("默认方案");
		formscheme.setOrderno(1);
		formscheme.setLayout("form");
		int cols = 1;
		formscheme.setHeight(0);
		if (tablebean.getFields().size() > 50) {
			formscheme.setCols(4);
			cols = 4;
			formscheme.setWidth(-1);
		} else if (tablebean.getFields().size() > 30) {
			formscheme.setCols(3);
			cols = 3;
			formscheme.setWidth(900);
		} else if (tablebean.getFields().size() > 10) {
			formscheme.setCols(2);
			cols = 2;
			formscheme.setWidth(800);
		} else {
			formscheme.setCols(1);
			cols = 1;
			formscheme.setWidth(500);
		}
		dao.save(formscheme);
		String group = "默认组";

		FovFormschemedetail fieldset = null;
		FovGridschemecolumn parentcolumn = null;
		int fieldsetorder = 10;
		for (int i = 0; i < tablebean.getFields().size(); i++) {

			TableFieldBean field = tablebean.getFields().get(i);
			boolean found = false;

			for (int j = 0; j < i; j++) {
				if (tablebean.getFields().get(j).getFieldname().equals(field.getFieldname())) {
					found = true;
					break;
				}
			}
			if (found)
				continue;

			FDataobjectfield objectfield = new FDataobjectfield();

			BeanUtils.copyProperties(objectfield, field);

			if (field.getComments() != null && field.getComments().length() > 0)
				objectfield.setFieldtitle(field.getComments());
			else
				objectfield.setFieldtitle(objectfield.getFieldname());
			if (objectfield.getFieldtitle().indexOf('|') > 0) {
				String[] a = objectfield.getFieldtitle().split("\\|");
				objectfield.setFieldtitle(a[0]);
				group = a[1];
			}
			objectfield.setFieldgroup(group);

			if (objectfield.getFieldrelation() != null) {
				if (objectfield.getFieldtitle().toLowerCase().endsWith("id"))
					objectfield.setFieldtitle(
							objectfield.getFieldtitle().substring(0, objectfield.getFieldtitle().length() - 2));
				FDataobject joinedobject = dao.findByPropertyFirst(FDataobject.class, "lower(tablename)",
						objectfield.getJointable(), "schemaname", schema);
				if (joinedobject == null)
					System.out.println("object:" + tablename + "--的关联模块" + objectfield.getJointable() + "未导入！");

				if (joinedobject.getTablename().equals(object.getTablename())) {
					object.setParentkey(objectfield.getFieldname());
					object.setIstreemodel(true);
					objectfield.setFieldrelation(null);
					objectfield.setJointable(null);
					objectfield.setJoincolumnname(null);

				} else {

					FDataobjectfield onetomanyfield = new FDataobjectfield();
					onetomanyfield.setFDataobject(joinedobject);
					onetomanyfield.setIsdisable(true);
					onetomanyfield.setCreatedate(DateUtils.getTimestamp());
					onetomanyfield.setCreater(userid);
					onetomanyfield.setFieldrelation("onetomany");
					onetomanyfield.setJointable(object.getTablename());
					onetomanyfield.setJoincolumnname(objectfield.getFieldname());
					onetomanyfield.setFieldtitle(objectfield.getFieldtitle() + "的" + object.getTitle());
					onetomanyfield.setFieldlen(0);
					onetomanyfield.setFieldtype("Set<" + object.getObjectname() + ">");
					onetomanyfield.setFieldgroup("默认组");

					objectfield.setFieldtype(joinedobject.getObjectname());
					if (joinedobject.getPrimarykey().equals(objectfield.getFieldname())) {
						objectfield.setFieldname(joinedobject.getObjectname());
						onetomanyfield.setFieldname(object.getObjectname() + "s");
					} else {

						String n = joinedobject.getObjectname();
						n = n + "By" + objectfield.getFieldname().substring(0, 1).toUpperCase()
								+ objectfield.getFieldname().substring(1);
						objectfield.setJoincolumnname(objectfield.getFieldname());
						objectfield.setFieldname(n);
						onetomanyfield.setFieldname(
								object.getObjectname() + "By" + objectfield.getFieldname().substring(0, 1).toUpperCase()
										+ objectfield.getFieldname().substring(1) + "s");
					}
					onetomanyfield.setFieldahead(object.getObjectname() + ".with." + objectfield.getFieldname());
					System.out.println(onetomanyfield.getFieldtitle() + "    " + onetomanyfield.getFieldname());

					dao.evict(onetomanyfield);

				}
			}

			objectfield.setFDataobject(object);
			objectfield.setCreater(userid);
			objectfield.setFieldtype(DBFieldType.valueOf(objectfield.getFieldtype()));

			if (DBFieldType.isNumber(objectfield.getFieldtype())) {
				objectfield.setAllowaggregate(true);
				objectfield.setFieldlen(0);
			}
			if (objectfield.getFieldtype().toLowerCase().equals("double")) {
				objectfield.setIsmonetary(true);
			}

			objectfield.setCreatedate(DateUtils.getTimestamp());
			objectfield.setOrderno(i + 1);
			objectfield.setAllownew(true);
			objectfield.setAllowedit(true);
			System.out.println(objectfield.getFieldtitle() + "    " + objectfield.getFieldname());
			String fn = objectfield.getFieldname();
			if (fn.equals("creater") || fn.equals("createdate") || fn.equals("lastmodifier")
					|| fn.equals("lastmodifydate")) {
				objectfield.setAllowedit(false);
				objectfield.setAllownew(false);
			}

			dao.save(objectfield);
			if (fn.equals(object.getPrimarykey()) || fn.equals(object.getParentkey()))
				;
			else {
				if (fn.equals("creater") || fn.equals("createdate") || fn.equals("lastmodifier")
						|| fn.equals("lastmodifydate"))
					;
				else {

					FovGridschemecolumn column = new FovGridschemecolumn();
					column.setOrderno(i + 1);
					column.setFDataobjectfield(objectfield);
					if (group.equals("默认组")) {
						column.setFovGridscheme(gridscheme);
					} else {
						if (parentcolumn == null || !parentcolumn.getTitle().equals(group)) {
							parentcolumn = new FovGridschemecolumn();
							parentcolumn.setTitle(group);
							parentcolumn.setOrderno(i + 1);
							parentcolumn.setFovGridscheme(gridscheme);
							dao.save(parentcolumn);
						}
						column.setFovGridschemecolumn(parentcolumn);
					}
					dao.save(column);
				}

				if (fieldset == null || !fieldset.getTitle().equals(group)) {
					fieldset = new FovFormschemedetail(fieldsetorder);
					fieldsetorder += 10;
					fieldset.setFovFormscheme(formscheme);
					fieldset.setXtype("fieldset");
					fieldset.setTitle(group);
					fieldset.setCols(cols);
					dao.save(fieldset);
				}

				FovFormschemedetail formcolumn = new FovFormschemedetail();
				formcolumn.setFovFormschemedetail(fieldset);
				formcolumn.setOrderno(i + 1);
				formcolumn.setFDataobjectfield(objectfield);
				dao.save(formcolumn);

			}
		}
		DataObjectUtils.resetDataobjectCachedData();
		return new ActionResult();
	}

	public ActionResult dropUserView(String viewid) {
		Session session = dao.getSessionFactory().getCurrentSession();
		FUserview view = dao.findById(FUserview.class, viewid);
		ActionResult result = new ActionResult();
		try {
			NativeQuery<?> query = session.createNativeQuery("drop view " + view.getViewname());
			query.executeUpdate();
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMsg(e.getCause().getMessage());
		}
		view.setIscreated(false);
		dao.update(view);
		return result;
	}

	public ActionResult createUserView(String viewid) {
		ActionResult result = new ActionResult();
		Session session = dao.getSessionFactory().getCurrentSession();
		FUserview view = dao.findById(FUserview.class, viewid);
		if (view.isIscreated()) {
			try {
				NativeQuery<?> query = session.createNativeQuery("drop view " + view.getViewname());
				query.executeUpdate();
			} catch (Exception e) {
			}
		}
		view.setIscreated(true);
		try {
			NativeQuery<?> query = session
					.createNativeQuery("create  view " + view.getViewname() + " as " + view.getSqlstatment());
			query.executeUpdate();
		} catch (Exception e) {
			view.setIscreated(false);
			result.setSuccess(false);
			result.setMsg(e.getCause().getMessage());
		}
		dao.update(view);
		return result;
	}

}
