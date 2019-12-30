package net.ebaolife.husqvarna.platform.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FNumbergroup;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridnavigatescheme;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridnavigateschemedetail;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
import net.ebaolife.husqvarna.framework.utils.DataObjectFieldUtils;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
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
public class NavigateSchemeService {

	@Resource
	private DaoImpl dao;

	@Resource
	private ModuleService moduleService;

	@Resource
	private UserFavouriteService userFavouriteService;

	@Resource
	private ModuleHierarchyService moduleHierarchyService;

	@Transactional(propagation = Propagation.REQUIRED)
	public ActionResult updateNavigateSchemeDetails(String dataObjectId, String navigateschemeid,
													String navigateSchemeName, String iconCls, Boolean cascading, Boolean allowNullRecordButton,
													Boolean isContainNullRecord, Boolean mydefault, Boolean shareowner, Boolean shareall, String schemeDefine) {
		FovGridnavigatescheme navigateScheme;

		if (navigateschemeid != null && navigateschemeid.length() > 1) {
			navigateScheme = dao.findById(FovGridnavigatescheme.class, navigateschemeid);
			navigateScheme.setTitle(navigateSchemeName);
			navigateScheme.setIconcls(iconCls);
			navigateScheme.setCascading(cascading);
			navigateScheme.setAllownullrecordbuttton(allowNullRecordButton);
			navigateScheme.setIscontainnullrecord(isContainNullRecord);
			navigateScheme.setIsshare(shareall);
			navigateScheme.setIsshareowner(shareowner);
			dao.saveOrUpdate(navigateScheme);
			dao.executeSQLUpdate("delete from fov_gridnavigateschemedetail where schemeid=?", navigateschemeid);
		} else {
			navigateScheme = new FovGridnavigatescheme();
			navigateScheme.setTitle(navigateSchemeName);
			FDataobject d = dao.findById(FDataobject.class, dataObjectId);
			navigateScheme.setFDataobject(d);
			int orderno = 10;
			if (d.getFovGridnavigateschemes() != null && d.getFovGridnavigateschemes().size() > 0) {
				List<FovGridnavigatescheme> schemes = new ArrayList<FovGridnavigatescheme>(
						d.getFovGridnavigateschemes());
				orderno = schemes.get(schemes.size() - 1).getOrderno() + 10;
			}
			navigateScheme.setIconcls(iconCls);
			navigateScheme.setCascading(cascading);
			navigateScheme.setAllownullrecordbuttton(allowNullRecordButton);
			navigateScheme.setIscontainnullrecord(isContainNullRecord);
			navigateScheme.setIsshareowner(shareowner);
			navigateScheme.setIsshare(shareall);
			navigateScheme.setOrderno(orderno);
			navigateScheme.setFUser(dao.findById(FUser.class, Local.getUserid()));
			navigateScheme.setEnabled(true);
			dao.save(navigateScheme);
		}
		JSONObject object = JSONObject.parseObject("{ children :" + schemeDefine + "}");
		JSONArray arrays = (JSONArray) object.get("children");
		saveNewDetails(navigateScheme, arrays, null);

		if (mydefault)
			userFavouriteService.setDefaultNavigateScheme(navigateScheme.getSchemeid());

		ActionResult result = new ActionResult();
		result.setTag(navigateScheme.genJson());
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public JSONObject getNavigateSchemeDetails(HttpServletRequest request, String navigateSchemeId) {
		FovGridnavigatescheme navigateScheme = dao.findById(FovGridnavigatescheme.class, navigateSchemeId);
		JSONObject object = new JSONObject();
		if (navigateScheme == null) {
			object.put("children", new JSONArray());
		} else {
			FDataobject baseModule = DataObjectUtils.getDataObject(navigateScheme.getFDataobject().getObjectname());
			object.put("leaf", false);
			object.put("children", genNavigateSchemedetail(navigateScheme.getDetails(), baseModule));
		}
		return object;
	}

	public JSONArray genNavigateSchemedetail(Set<FovGridnavigateschemedetail> Details, FDataobject baseModule) {
		JSONArray result = new JSONArray();
		for (FovGridnavigateschemedetail detail : Details) {
			JSONObject object = new JSONObject();
			object.put("title", detail.getTitle());
			object.put("functionid", detail.getFFunction() != null ? detail.getFFunction().getFunctionid() : null);
			object.put("numbergroupid",
					detail.getFNumbergroup() != null ? detail.getFNumbergroup().getNumbergroupid() : null);
			object.put("fieldfunction", detail.getFieldfunction());
			object.put("addparentfilter", detail.getAddparentfilter());
			object.put("reverseorder", detail.getReverseorder());
			object.put("collapsed", detail.getCollapsed());
			object.put("addcodelevel", detail.getAddcodelevel());
			object.put("iconcls", detail.getIconcls());
			object.put("cls", detail.getCls());
			object.put("remark", detail.getRemark());
			object.put("leaf", true);
			object.put("itemId", DataObjectFieldUtils.getItemId(detail.getFDataobjectfield(), detail.getFieldahead(),
					detail.getAggregate(), null));
			object.put("text", DataObjectFieldUtils.getTitle(detail.getFDataobjectfield(), detail.getFieldahead(),
					detail.getAggregate(), null, baseModule));
			result.add(object);
		}
		return result;
	}

	private void saveNewDetails(FovGridnavigatescheme navigateScheme, JSONArray arrays, FovGridnavigateschemedetail p) {
		for (int i = 0; i < arrays.size(); i++) {
			JSONObject detailObject = arrays.getJSONObject(i);
			FovGridnavigateschemedetail detail = new FovGridnavigateschemedetail();
			detail.setFovGridnavigatescheme(navigateScheme);
			detail.setOrderno((i + 1) * 10);
			if (detailObject.containsKey("title"))
				detail.setTitle(detailObject.getString("title"));
			if (detailObject.containsKey("fieldfunction"))
				detail.setFieldfunction(detailObject.getString("fieldfunction"));
			if (detailObject.containsKey("addparentfilter"))
				detail.setAddparentfilter(detailObject.getBoolean("addparentfilter"));
			if (detailObject.containsKey("reverseorder"))
				detail.setReverseorder(detailObject.getBoolean("reverseorder"));
			if (detailObject.containsKey("collapsed"))
				detail.setCollapsed(detailObject.getBoolean("collapsed"));
			if (detailObject.containsKey("addcodelevel"))
				detail.setAddcodelevel(detailObject.getBoolean("addcodelevel"));

			if (detailObject.containsKey("iconcls"))
				detail.setIconcls(detailObject.getString("iconcls"));
			if (detailObject.containsKey("cls"))
				detail.setCls(detailObject.getString("cls"));
			if (detailObject.containsKey("remark"))
				detail.setRemark(detailObject.getString("remark"));
			if (detailObject.containsKey("functionid"))
				detail.setFFunction(dao.findById(FFunction.class, detailObject.getString("functionid")));
			if (detailObject.containsKey("numbergroupid"))
				detail.setFNumbergroup(dao.findById(FNumbergroup.class, detailObject.getString("numbergroupid")));
			ParentChildField.updateToField(detail, detailObject.getString("itemId"));
			dao.save(detail);
			navigateScheme.getDetails().add(detail);
		}
	}

	public ActionResult deleteNavigateScheme(HttpServletRequest request, String schemeid) {
		dao.executeSQLUpdate("delete from fov_gridnavigateschemedetail where schemeid=?", schemeid);
		dao.executeSQLUpdate("delete from fov_gridnavigatescheme where schemeid=?", schemeid);
		return new ActionResult();
	}

	public ActionResult checkNameValidate(String name, String id) {
		FUser user = dao.findById(FUser.class, Local.getUserid());
		ActionResult result = new ActionResult();
		for (FovGridnavigatescheme scheme : user.getFovGridnavigateschemes())
			if ((id == null || !id.equals(scheme.getSchemeid())) && scheme.getTitle().equals(name)) {
				result.setSuccess(false);
				break;
			}
		return result;
	}

	public ActionResult navigateSchemeSaveas(HttpServletRequest request, String schemeid, String schemename) {
		FovGridnavigatescheme scheme = dao.findById(FovGridnavigatescheme.class, schemeid);
		FovGridnavigatescheme saveas = new FovGridnavigatescheme();
		int orderno = 10;
		FDataobject d = scheme.getFDataobject();
		if (d.getFovGridnavigateschemes() != null && d.getFovGridnavigateschemes().size() > 0) {
			List<FovGridnavigatescheme> schemes = new ArrayList<FovGridnavigatescheme>(d.getFovGridnavigateschemes());
			orderno = schemes.get(schemes.size() - 1).getOrderno() + 10;
		}
		saveas.setOrderno(orderno);
		saveas.setFDataobject(scheme.getFDataobject());
		saveas.setFUser(dao.findById(FUser.class, Local.getUserid()));
		saveas.setTitle(schemename);
		saveas.setIconcls(scheme.getIconcls());
		saveas.setCascading(scheme.getCascading());
		saveas.setAllownullrecordbuttton(scheme.getAllownullrecordbuttton());
		saveas.setIscontainnullrecord(scheme.getIscontainnullrecord());
		saveas.setEnabled(true);

		dao.save(saveas);
		for (FovGridnavigateschemedetail c : scheme.getDetails()) {
			FovGridnavigateschemedetail newdetail = new FovGridnavigateschemedetail(saveas, c.getFDataobjectfield(),
					c.getFDataobjectconditionBySubconditionid(), c.getFFunction(), c.getFNumbergroup(), c.getOrderno(),
					c.getTitle(), c.getFielddescription(), c.getFieldahead(), c.getAggregate(), c.getFieldfunction(),
					c.getNtype(), c.getAddparentfilter(), c.getReverseorder(), c.getCollapsed(), c.getAddcodelevel(),
					c.getIconcls(), c.getCls(), c.getRemark());
			dao.save(newdetail);
		}
		ActionResult result = new ActionResult();
		result.setTag(saveas.genJson());
		return result;
	}
}
