package net.ebaolife.husqvarna.platform.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.*;
import net.ebaolife.husqvarna.framework.core.annotation.SystemLogs;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.grid.GridColumn;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.SqlMapperAdapter;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectview;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridsortscheme;
import net.ebaolife.husqvarna.framework.exception.DataDeleteException;
import net.ebaolife.husqvarna.framework.exception.DataUpdateException;
import net.ebaolife.husqvarna.framework.interceptor.transcoding.RequestList;
import net.ebaolife.husqvarna.framework.utils.CommonUtils;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import net.ebaolife.husqvarna.framework.utils.FileUtils;
import net.ebaolife.husqvarna.framework.utils.ProjectUtils;
import net.ebaolife.husqvarna.platform.service.DataObjectService;
import ognl.OgnlException;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/platform/dataobject")

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
public class DataObject extends SqlMapperAdapter {

	@Autowired
	private DataObjectService service;

	@SystemLogs("列表信息查询")
	@RequestMapping(value = "/fetchdata")
	@ResponseBody
	public PageInfo<Map<String, Object>> fetchData(String moduleName, GridParams pg,
												   @RequestList(clazz = SortParameter.class) List<SortParameter> sort, String group,
												   @RequestList(clazz = UserDefineFilter.class) List<UserDefineFilter> filter,
												   @RequestList(clazz = UserDefineFilter.class) List<UserDefineFilter> userfilter,
												   @RequestList(clazz = UserDefineFilter.class) List<UserDefineFilter> query,
												   @RequestList(clazz = GridColumn.class) List<GridColumn> columns,
												   @RequestList(clazz = UserNavigateFilter.class) List<UserNavigateFilter> navigates, String parentFilter,
												   String sortschemeid, String sqlparamstr) {
		long s = new Date().getTime();
		if (filter == null)
			filter = new ArrayList<UserDefineFilter>();
		if (!CommonUtils.isEmpty(userfilter))
			filter.addAll(userfilter);
		List<UserParentFilter> userParentFilters = UserParentFilter.changeToParentFilters(parentFilter, moduleName);
		FovGridsortscheme sortscheme = null;
		if (sortschemeid != null && sortschemeid.length() > 0)
			sortscheme = Local.getDao().findById(FovGridsortscheme.class, sortschemeid);
		JSONObject sqlparam = null;
		if (sqlparamstr != null && sqlparamstr.length() > 0)
			sqlparam = JSONObject.parseObject(sqlparamstr);
		PageInfo<Map<String, Object>> result = null;
		try {
			result = service.fetchDataInner(moduleName, pg, columns, GroupParameter.changeToGroupParameter(group), sort,
					query, filter, navigates, userParentFilters, sortscheme, sqlparam);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		result.setSpendtime(new Date().getTime() - s);
		return result;
	}

	@RequestMapping(value = "/fetchtreedata")
	public @ResponseBody JSONObject fetchTreeData(String moduleName, GridParams pg,
			@RequestList(clazz = SortParameter.class) List<SortParameter> sort,
			@RequestList(clazz = UserDefineFilter.class) List<UserDefineFilter> filter,
			@RequestList(clazz = UserDefineFilter.class) List<UserDefineFilter> userfilter,
			@RequestList(clazz = UserDefineFilter.class) List<UserDefineFilter> query,
			@RequestList(clazz = GridColumn.class) List<GridColumn> columns,
			@RequestList(clazz = UserNavigateFilter.class) List<UserNavigateFilter> navigates,

			String parentFilter, String viewschemeid, String node) {
		if (filter == null)
			filter = new ArrayList<UserDefineFilter>();
		if (!CommonUtils.isEmpty(userfilter))
			filter.addAll(userfilter);

		List<UserParentFilter> userParentFilters = UserParentFilter.changeToParentFilters(parentFilter, moduleName);
		FDataobjectview viewscheme = null;
		if (viewschemeid != null && viewschemeid.length() > 0)
			viewscheme = Local.getDao().findById(FDataobjectview.class, viewschemeid);
		JSONObject result = null;
		try {
			result = service.fetchTreeDataInner(moduleName, pg, columns, sort, query, filter, navigates,
					userParentFilters, viewscheme);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		return result;
	}

	@RequestMapping(value = "/fetchcombodata")
	@ResponseBody
	public List<ValueText> fetchModuleComboData(String moduleName, String query) {
		return service.fetchModuleComboData(moduleName, query);
	}

	@RequestMapping(value = "/fetchpickertreedata", method = RequestMethod.GET)
	public @ResponseBody List<TreeValueText> getModuleTreeData(String moduleName,
			Boolean allowParentValue) {
		return service.getModuleWithTreeData(moduleName, allowParentValue == null ? false : allowParentValue, null);
	}

	@SystemLogs("查询模块数据")
	@RequestMapping(value = "/fetchinfo")
	@ResponseBody
	public ResultBean fetchInfo(String objectname, String id) {
		return service.fetchInfo(objectname, id);
	}

	@RequestMapping(value = "/getnewdefault")
	@ResponseBody
	public ResultBean getRecordNewDefault(String objectname, String parentfilter, String navigates) {
		ResultBean result = new ResultBean();
		try {
			result = service.getRecordNewDefault(objectname, parentfilter, navigates);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@SystemLogs("新增或修改模块数据")
	@RequestMapping(value = "/saveorupdate")
	@ResponseBody
	public ResultBean saveOrUpdate(String objectname, String oldid, String opertype) {
		if (opertype == null) {
			opertype = "edit";
		}
		ResultBean result = new ResultBean();
		try {
			StringBuffer sb = new StringBuffer();

			FileUtils.copy(Local.getRequest().getInputStream(), sb);
			result = service.saveOrUpdate(objectname, sb.toString(), oldid, opertype);
		} catch (DataUpdateException e) {
			result.setSuccess(false);
			result.setData(e.getErrorMessage());
		} catch (ConstraintViolationException e) {
			FDataobject dataObject = DataObjectUtils.getDataObject(objectname);
			result = ProjectUtils.getErrorMassage(e, dataObject, dao, sf);
		} catch (PersistenceException e) {
			FDataobject dataObject = DataObjectUtils.getDataObject(objectname);
			result = ProjectUtils.getErrorMassage(e, dataObject, dao, sf);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setMessage(e.getMessage());
		}
		return result;
	}

	@SystemLogs("删除数据")
	@RequestMapping(value = "/remove")
	@ResponseBody
	public DataDeleteResponseInfo remove(String objectname, @RequestBody String removed) {
		JSONObject object = JSON.parseObject(removed);
		String id = null;
		for (String idfieldname : object.keySet()) {
			id = object.getString(idfieldname);
			break;
		}
		return RemoveWithId(objectname, id);
	}

	public DataDeleteResponseInfo RemoveWithId(String objectname, String id) {
		DataDeleteResponseInfo result = new DataDeleteResponseInfo();
		FDataobject object = DataObjectUtils.getDataObject(objectname);
		try {
			result = service.remove(objectname, id);
		} catch (PersistenceException e) {
			e.printStackTrace();
			result.setResultCode(-1);
			String message = e.getCause().getCause() != null ? e.getCause().getCause().getMessage()
					: e.getCause().getMessage();
			String FKTablename = sf.getFKConstraintTableName(dao, object.getTablename(), message , object.getSchemaname());
			if (FKTablename != null) {
				String sql = " select title as name from f_dataobject where tablename = ? ";
				Name name = dao.executeSQLQueryFirst(sql, Name.class, FKTablename);
				result.getErrorMessageList()
						.add("与本记录相关联的『" + (name == null ? FKTablename : name.getName()) + "』数据没有全部清空");
			} else
				result.getErrorMessageList().add("错误信息:『" + message);
		} catch (DataDeleteException e) {
			e.printStackTrace();
			result.setResultCode(-1);
			result.getErrorMessageList().addAll(e.getErrorMessage());
		} catch (Exception e) {
			e.printStackTrace();
			result.setResultCode(-1);
			result.getErrorMessageList().add(e.getCause().getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/removerecords")
	public @ResponseBody DataDeleteResponseInfo removeRecords(String modulename, String ids, String titles,
			HttpServletRequest request) {
		DataDeleteResponseInfo result = null;
		String[] idarray = ids.split(",");
		String[] titlearray = titles.split("~~");
		result = new DataDeleteResponseInfo();
		for (int i = 0; i < idarray.length; i++) {
			DataDeleteResponseInfo recordDeleteResult = RemoveWithId(modulename, idarray[i]);
			if (recordDeleteResult.getResultCode() == 0)
				result.getOkMessageList().add(titlearray[i]);
			else {
				if (recordDeleteResult.getErrorMessageList().size() > 0)
					result.getErrorMessageList()
							.add("『" + titlearray[i] + "』" + recordDeleteResult.getErrorMessageList().get(0));
				else
					result.getErrorMessageList().add("『" + titlearray[i] + "』" + "未知错误！");
			}
		}
		result.setResultCode(result.getErrorMessageList().size());
		return result;
	}

	@RequestMapping("/getmanytomanydetail")
	public @ResponseBody List<TreeNodeRecordChecked> genManyToManyDetail(HttpServletRequest request, String moduleName,
			String id, String manyToManyModuleName, String linkModuleName) {
		return service.getManyToManyDetail(request, moduleName, id, manyToManyModuleName, linkModuleName);

	}

	@RequestMapping("/setmanytomanydetail")
	public @ResponseBody ActionResult setManyToManyDetail(String moduleName, String id, String manyToManyModuleName,
			String linkModuleName, String selected)
			throws ClassNotFoundException, OgnlException, IllegalAccessException, InvocationTargetException {
		return service.setManyToManyDetail(moduleName, id, manyToManyModuleName, linkModuleName, selected.split(","));

	}

	@RequestMapping("/updateorderno")
	public @ResponseBody ActionResult UpdateOrderno(String moduleName, String ids) {
		ActionResult result = null;
		try {
			result = service.UpdateOrderno(moduleName, ids.split(","));
		} catch (Exception e) {
			e.printStackTrace();
			result = new ActionResult(false, e.getMessage());
		}
		return result;
	}

	@RequestMapping("/updateparentkey")
	public @ResponseBody ActionResult UpdateParentkey(String objectname, String id, String parentkey) {
		ActionResult result = null;
		try {
			result = service.UpdateParentkey(objectname, id,
					parentkey == null || parentkey.length() == 0 ? null : parentkey);
		} catch (Exception e) {
			e.printStackTrace();
			result = new ActionResult(false, e.getMessage());
		}
		return result;
	}

}
