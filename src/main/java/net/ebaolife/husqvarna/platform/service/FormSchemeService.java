package net.ebaolife.husqvarna.platform.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.bean.ValueText;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovFormscheme;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovFormschemedetail;
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
public class FormSchemeService {

	@Resource
	private DaoImpl dao;

	@Resource
	private ModuleService moduleService;

	@Resource
	private UserFavouriteService userFavouriteService;

	@Resource
	private ModuleHierarchyService moduleHierarchyService;

	@Transactional(propagation = Propagation.REQUIRED)
	public ActionResult updateFormSchemeDetails(HttpServletRequest request, String dataObjectId, String formSchemeId,
												String formSchemeName, String schemeDefine, Boolean mydefault, Boolean shareowner, Boolean shareall) {
		FovFormscheme formScheme = dao.findById(FovFormscheme.class, formSchemeId);

		for (FovFormschemedetail detail : dao.findByProperty(FovFormschemedetail.class, "formschemeid", formSchemeId)) {
			dao.delete(detail);
		}

		JSONObject object = JSONObject.parseObject("{ children :" + schemeDefine + "}");
		JSONArray arrays = (JSONArray) object.get("children");
		saveNewDetails(formScheme, arrays, null);

		ActionResult result = new ActionResult();
		result.setTag(formScheme.getFormschemeid());
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public JSONObject getFormSchemeDetails(HttpServletRequest request, String formSchemeId) {
		FovFormscheme formScheme = dao.findById(FovFormscheme.class, formSchemeId);
		JSONObject object = new JSONObject();
		if (formScheme == null) {
			object.put("children", new JSONArray());
			return object;
		}
		FDataobject baseModule = DataObjectUtils.getDataObject(formScheme.getFDataobject().getObjectname());
		List<FovFormschemedetail> Details = dao.findByProperty(FovFormschemedetail.class, "formschemeid", formSchemeId);
		object.put("children", genFormSchemedetail(Details, baseModule));
		return object;
	}

	public JSONArray genFormSchemedetail(List<FovFormschemedetail> Details, FDataobject baseModule) {
		JSONArray result = new JSONArray();
		for (FovFormschemedetail detail : Details) {
			JSONObject object = new JSONObject();
			object.put("title", detail.getTitle());
			object.put("rowspan", detail.getRowspan());
			object.put("colspan", detail.getColspan());
			object.put("othersetting", detail.getOthersetting());
			object.put("remark", detail.getRemark());
			object.put("xtype", detail.getXtype());
			object.put("region", detail.getRegion());
			object.put("layout", detail.getLayout());
			object.put("widths", detail.getWidths());
			object.put("rows", detail.getRowss());
			object.put("cols", detail.getCols());

			object.put("separatelabel", detail.getSeparatelabel());
			object.put("hiddenlabel", detail.getHiddenlabel());
			object.put("collapsible", detail.getCollapsible());
			object.put("collapsed", detail.getCollapsed());
			object.put("width", detail.getWidth());
			object.put("height", detail.getHeight());
			object.put("isendrow", detail.getIsendrow());
			object.put("leaf", true);
			if (detail.getFDataobjectBySubobjectid() != null) {

				FDataobject cobject = detail.getFDataobjectBySubobjectid();
				object.put("cls", "manytomanycolor");
				object.put("iconCls", cobject.getIconcls());
				object.put("icon", cobject.getIconurl());
				object.put("text",
						DataObjectFieldUtils.getPCModuletitle(baseModule.getObjectname(), detail.getFieldahead()));
				object.put("subdataobjecttitle",
						DataObjectFieldUtils.getPCModuletitle(baseModule.getObjectname(), detail.getFieldahead()));
				object.put("fieldahead", detail.getFieldahead());
			} else if (detail.getFDataobjectfield() != null) {

				object.put("cls", detail.getFDataobjectfield()._getFieldCss());
				object.put("itemId",
						DataObjectFieldUtils.getItemId(detail.getFDataobjectfield(), detail.getFieldahead(),
								detail.getAggregate(), detail.getFDataobjectconditionBySubconditionid()));
				object.put("text", DataObjectFieldUtils.getTitle(detail.getFDataobjectfield(), detail.getFieldahead(),
						detail.getAggregate(), null, baseModule));
				if (detail.getFDataobjectfield()._isManyToOne() || detail.getFDataobjectfield()._isOneToOne()) {
					FDataobject m = DataObjectUtils.getDataObject(detail.getFDataobjectfield().getFieldtype());
					object.put("iconCls", m.getIconcls());
					object.put("icon", m.getIconurl());
				}
			} else {

				object.put("text", detail.getTitle());
				JSONArray children = genFormSchemedetail(detail.getDetails(), baseModule);
				if (children.size() > 0) {
					object.put("expanded", true);
					object.put("leaf", false);
					object.put("children", children);
				}
			}
			result.add(object);
		}
		return result;
	}

	private void saveNewDetails(FovFormscheme FormScheme, JSONArray arrays, FovFormschemedetail p) {
		for (int i = 0; i < arrays.size(); i++) {
			JSONObject detailObject = arrays.getJSONObject(i);
			FovFormschemedetail detail = new FovFormschemedetail();
			detail.setFovFormschemedetail(p);
			detail.setFovFormscheme(FormScheme);
			if (detailObject.containsKey("title"))
				detail.setTitle(detailObject.getString("title"));
			if (detailObject.containsKey("rowspan"))
				detail.setRowspan(detailObject.getInteger("rowspan"));
			if (detailObject.containsKey("colspan"))
				detail.setColspan(detailObject.getInteger("colspan"));
			if (detailObject.containsKey("othersetting"))
				detail.setOthersetting(detailObject.getString("othersetting"));
			if (detailObject.containsKey("remark"))
				detail.setRemark(detailObject.getString("remark"));
			detail.setOrderno((i + 1) * 10);
			if (detailObject.containsKey("rows"))
				detail.setRowss(detailObject.getInteger("rows"));
			if (detailObject.containsKey("cols"))
				detail.setCols(detailObject.getInteger("cols"));
			if (detailObject.containsKey("widths"))
				detail.setWidths(detailObject.getString("widths"));
			if (detailObject.containsKey("xtype"))
				detail.setXtype(detailObject.getString("xtype"));

			if (detailObject.containsKey("region"))
				detail.setRegion(detailObject.getString("region"));
			if (detailObject.containsKey("layout"))
				detail.setLayout(detailObject.getString("layout"));
			if (detailObject.containsKey("separatelabel"))
				detail.setSeparatelabel(detailObject.getBoolean("separatelabel"));
			if (detailObject.containsKey("hiddenlabel"))
				detail.setHiddenlabel(detailObject.getBoolean("hiddenlabel"));
			if (detailObject.containsKey("collapsible"))
				detail.setCollapsible(detailObject.getBoolean("collapsible"));
			if (detailObject.containsKey("collapsed"))
				detail.setCollapsed(detailObject.getBoolean("collapsed"));
			if (detailObject.containsKey("isendrow"))
				detail.setIsendrow(detailObject.getBoolean("isendrow"));
			if (detailObject.containsKey("width"))
				detail.setWidth(detailObject.getInteger("width"));
			if (detailObject.containsKey("height"))
				detail.setHeight(detailObject.getInteger("height"));

			if (detailObject.containsKey("fieldahead")) {
				String fieldahead = detailObject.getString("fieldahead");
				String subname = fieldahead.substring(0, fieldahead.indexOf('.'));
				detail.setFDataobjectBySubobjectid(DataObjectUtils.getDataObject(subname));
				detail.setFieldahead(fieldahead);
				dao.save(detail);
			} else if (detailObject.containsKey("itemId")) {
				ParentChildField.updateToField(detail, detailObject.getString("itemId"));
				dao.save(detail);
			} else if (detailObject.containsKey("children")) {
				dao.save(detail);
				saveNewDetails(null, (JSONArray) detailObject.get("children"), detail);
			} else
				dao.save(detail);
		}
	}

	public ActionResult deleteFormScheme(HttpServletRequest request, String schemeid) {
		dao.executeSQLUpdate("delete from fov_formschemedetail where formschemeid=?", schemeid);
		dao.executeSQLUpdate("delete from fov_formscheme where formschemeid=?", schemeid);
		return new ActionResult();
	}

	public ActionResult checkNameValidate(String name, String id) {
		FUser user = dao.findById(FUser.class, Local.getUserid());
		ActionResult result = new ActionResult();
		for (FovFormscheme scheme : user.getFovFormschemes())
			if ((id == null || !id.equals(scheme.getFormschemeid())) && scheme.getSchemename().equals(name)) {
				result.setSuccess(false);
				break;
			}
		return result;
	}

	public List<ValueText> getObjectSchemename(String objectid) {
		List<ValueText> result = new ArrayList<ValueText>();
		FDataobject dataobject = dao.findById(FDataobject.class, objectid);
		for (FovFormscheme scheme : dataobject.getFovFormschemes()) {
			result.add(new ValueText(scheme.getFormschemeid(), scheme.getSchemename()));
		}
		return result;
	}

}
