package net.ebaolife.husqvarna.platform.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridscheme;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridschemecolumn;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
public class GridSchemeService {

	@Resource
	private DaoImpl dao;

	@Resource
	private ModuleService moduleService;

	@Resource
	private UserFavouriteService userFavouriteService;

	@Resource
	private ModuleHierarchyService moduleHierarchyService;

	@Transactional(propagation = Propagation.REQUIRED)
	public ActionResult updateGridSchemeColumns(HttpServletRequest request, String dataObjectId, String gridSchemeId,
												String gridSchemeName, String schemeDefine, Boolean mydefault, Boolean shareowner, Boolean shareall) {
		FovGridscheme gridScheme;

		if (gridSchemeId != null && gridSchemeId.length() > 1) {
			gridScheme = dao.findById(FovGridscheme.class, gridSchemeId);
			gridScheme.setSchemename(gridSchemeName);
			gridScheme.setIsshare(shareall);
			gridScheme.setIsshareowner(shareowner);
			dao.saveOrUpdate(gridScheme);
			for (FovGridschemecolumn detail : dao.findByProperty(FovGridschemecolumn.class, "gridschemeid",
					gridSchemeId)) {
				dao.delete(detail);
			}

		} else {
			gridScheme = new FovGridscheme();
			gridScheme.setSchemename(gridSchemeName);
			gridScheme.setFDataobject(dao.findById(FDataobject.class, dataObjectId));
			gridScheme.setIsshareowner(shareowner);
			gridScheme.setIsshare(shareall);
			gridScheme.setFUser(dao.findById(FUser.class, Local.getUserid()));
			dao.save(gridScheme);
		}
		JSONObject object = JSONObject.parseObject("{ children :" + schemeDefine + "}");
		JSONArray arrays = (JSONArray) object.get("children");
		saveNewColumns(gridScheme, arrays, null);

		if (mydefault)
			userFavouriteService.setDefaultGridScheme(gridScheme.getGridschemeid());

		ActionResult result = new ActionResult();
		result.setTag(gridScheme.getGridschemeid());
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public JSONArray getGridSchemeColumnsForDisplay(HttpServletRequest request, String gridSchemeId) {

		FovGridscheme gridScheme = dao.findById(FovGridscheme.class, gridSchemeId);
		if (gridScheme == null)
			return new JSONArray();

		FDataobject baseModule = DataObjectUtils.getDataObject(gridScheme.getFDataobject().getObjectname());
		List<FovGridschemecolumn> columns = dao.findByProperty(FovGridschemecolumn.class, "gridschemeid", gridSchemeId);

		return genGridSchemeColumnForDisplay(columns, baseModule);
	}

	public JSONArray genGridSchemeColumnForDisplay(List<FovGridschemecolumn> columns, FDataobject baseModule) {
		JSONArray result = new JSONArray();
		for (FovGridschemecolumn column : columns) {
			JSONObject object = new JSONObject();
			object.put("tf_columnid", column.getColumnid());
			object.put("tf_title", column.getTitle());
			object.put("tf_locked", column.getLocked());
			object.put("tf_hidden", column.getHidden());
			object.put("tf_otherSetting", column.getOthersetting());
			object.put("tf_remark", column.getRemark());
			if (column.getFDataobjectfield() == null) {

				object.put("children", genGridSchemeColumnForDisplay(column.getColumns(), baseModule));
			} else {
				object.put("tf_autosizetimes", column.getAutosizetimes());
				object.put("tf_flex", column.getFlex());

				object.put("tf_width", column.getColumnwidth());

				if (column.getFieldahead() == null) {

					object.put("tf_fieldname", column.getFDataobjectfield().getFieldname());

				} else if (column.getAggregate() == null) {

					object.put("tf_fieldname",
							column.getFieldahead() + "." + column.getFDataobjectfield().getFieldname());
				} else {

					object.put("tf_fieldname", column.getAggregate() + "." + column.getFieldahead()
							.replaceFirst("\\.with\\.", "." + column.getFDataobjectfield().getFieldname() + ".with."));
				}
			}
			result.add(object);
		}
		return result;
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public JSONObject getGridSchemeColumnsForEdit(HttpServletRequest request, String gridSchemeId) {
		FovGridscheme gridScheme = dao.findById(FovGridscheme.class, gridSchemeId);
		JSONObject object = new JSONObject();
		if (gridScheme == null) {
			object.put("children", new JSONArray());
			return object;
		}
		FDataobject baseModule = DataObjectUtils.getDataObject(gridScheme.getFDataobject().getObjectname());
		List<FovGridschemecolumn> columns = dao.findByProperty(FovGridschemecolumn.class, "gridschemeid", gridSchemeId);

		object.put("moduleTitle", gridScheme.getFDataobject().getTitle());
		object.put("schemename", gridScheme.getSchemename());
		object.put("children", genGridSchemeColumnForEdit(columns, baseModule));
		return object;
	}

	public JSONArray genGridSchemeColumnForEdit(List<FovGridschemecolumn> columns, FDataobject baseModule) {
		JSONArray result = new JSONArray();
		for (FovGridschemecolumn column : columns) {
			JSONObject object = new JSONObject();
			object.put("tf_title", column.getTitle());
			object.put("tf_locked", column.getLocked());
			object.put("tf_hidden", column.getHidden());
			object.put("tf_otherSetting", column.getOthersetting());
			object.put("tf_remark", column.getRemark());
			if (column.getFDataobjectfield() == null) {

				object.put("text", column.getTitle());
				object.put("itemId", column.getTitle());
				object.put("expanded", true);
				object.put("leaf", false);
				object.put("children", genGridSchemeColumnForEdit(column.getColumns(), baseModule));
			} else {

				object.put("tf_autosizetimes", column.getAutosizetimes());
				object.put("tf_flex", column.getFlex());
				object.put("tf_width", column.getColumnwidth());
				object.put("leaf", true);
				object.put("cls", column.getFDataobjectfield()._getFieldCss());

				object.put("itemId", ParentChildField.generateFieldString(column));
				object.put("text", ParentChildField.generateFieldText(column, baseModule));

				if (column.getFDataobjectfield()._isManyToOne() || column.getFDataobjectfield()._isOneToOne()) {
					FDataobject m = DataObjectUtils.getDataObject(column.getFDataobjectfield().getFieldtype());
					object.put("iconCls", m.getIconcls());
					object.put("icon", m.getIconurl());
				}

			}

			result.add(object);
		}
		return result;
	}

	private void saveNewColumns(FovGridscheme gridScheme, JSONArray arrays, FovGridschemecolumn p) {
		for (int i = 0; i < arrays.size(); i++) {

			JSONObject columnObject = arrays.getJSONObject(i);
			FovGridschemecolumn column = new FovGridschemecolumn();
			column.setFovGridschemecolumn(p);
			column.setFovGridscheme(gridScheme);
			if (columnObject.containsKey("tf_title"))
				column.setTitle(columnObject.getString("tf_title"));
			if (columnObject.containsKey("tf_locked"))
				column.setLocked(columnObject.getBoolean("tf_locked"));
			if (columnObject.containsKey("tf_hidden"))
				column.setHidden(columnObject.getBoolean("tf_hidden"));
			if (columnObject.containsKey("tf_otherSetting"))
				column.setOthersetting(columnObject.getString("tf_otherSetting"));
			if (columnObject.containsKey("tf_remark"))
				column.setRemark(columnObject.getString("tf_remark"));
			column.setOrderno((i + 1) * 10);
			if (columnObject.containsKey("children")) {
				dao.save(column);
				saveNewColumns(null, (JSONArray) columnObject.get("children"), column);
			} else {
				ParentChildField.updateToField(column, columnObject.getString("tf_itemId"));
				if (columnObject.containsKey("tf_width"))
					column.setColumnwidth(columnObject.getInteger("tf_width"));
				if (columnObject.containsKey("tf_autosizetimes"))
					column.setAutosizetimes(columnObject.getInteger("tf_autosizetimes"));
				if (columnObject.containsKey("tf_flex"))
					column.setFlex(columnObject.getInteger("tf_flex"));
				dao.save(column);
			}
		}

	}

	public ActionResult deleteGridScheme(HttpServletRequest request, String schemeid) {

		dao.executeSQLUpdate("delete from fov_gridschemecolumn where gridschemeid=?", schemeid);
		dao.executeSQLUpdate("delete from fov_gridscheme where gridschemeid=?", schemeid);

		return new ActionResult();
	}

	public ActionResult checkNameValidate(String name, String id) {
		FUser user = dao.findById(FUser.class, Local.getUserid());
		ActionResult result = new ActionResult();
		for (FovGridscheme scheme : user.getFovGridschemes())
			if ((id == null || !id.equals(scheme.getGridschemeid())) && scheme.getSchemename().equals(name)) {
				result.setSuccess(false);
				break;
			}
		return result;
	}

	public ActionResult gridSchemeSaveas(HttpServletRequest request, String schemeid, String schemename) {
		FovGridscheme scheme = dao.findById(FovGridscheme.class, schemeid);
		FovGridscheme saveas = new FovGridscheme();
		saveas.setFDataobject(scheme.getFDataobject());
		saveas.setFUser(dao.findById(FUser.class, Local.getUserid()));
		saveas.setSchemename(schemename);
		dao.save(saveas);
		for (FovGridschemecolumn c : scheme.getColumns()) {
			FovGridschemecolumn newcolumn = new FovGridschemecolumn(c.getFDataobjectfield(),
					c.getFDataobjectconditionBySubconditionid(), c.getOrderno(), c.getTitle(), c.getFieldahead(),
					c.getAggregate(), c.getColumnwidth(), c.getAutosizetimes(), c.getFlex(), c.getHidden(),
					c.getLocked(), c.getOthersetting(), c.getRemark());
			newcolumn.setFovGridscheme(saveas);
			dao.save(newcolumn);
			if (c.getColumns() != null && c.getColumns().size() > 0)
				copyGridSchemeColumn(c, newcolumn);
		}
		ActionResult result = new ActionResult();
		result.setTag(saveas.getGridschemeid());
		return result;
	}

	private void copyGridSchemeColumn(FovGridschemecolumn column, FovGridschemecolumn pcolumn) {
		for (FovGridschemecolumn c : column.getColumns()) {
			FovGridschemecolumn newcolumn = new FovGridschemecolumn(c.getFDataobjectfield(),
					c.getFDataobjectconditionBySubconditionid(), c.getOrderno(), c.getTitle(), c.getFieldahead(),
					c.getAggregate(), c.getColumnwidth(), c.getAutosizetimes(), c.getFlex(), c.getHidden(),
					c.getLocked(), c.getOthersetting(), c.getRemark());
			newcolumn.setFovGridschemecolumn(pcolumn);
			dao.save(newcolumn);
			if (c.getColumns() != null && c.getColumns().size() > 0)
				copyGridSchemeColumn(c, newcolumn);
		}

	}

}
