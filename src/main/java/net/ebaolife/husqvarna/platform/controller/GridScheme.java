package net.ebaolife.husqvarna.platform.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.platform.service.GridSchemeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/platform/scheme/grid")

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
public class GridScheme {

	@Resource
	private GridSchemeService gridSchemeService;

	@RequestMapping("/updatedetails")
	public @ResponseBody
	ActionResult updateGridSchemeColumns(HttpServletRequest request, String dataObjectId,
										 String gridSchemeId, String schemeDefine, String gridSchemeName, Boolean mydefault, Boolean shareowner,
										 Boolean shareall) {

		return gridSchemeService.updateGridSchemeColumns(request, dataObjectId, gridSchemeId, gridSchemeName,
				schemeDefine, mydefault, shareowner, shareall);
	}

	@RequestMapping("/getdetailsforedit")
	public @ResponseBody JSONObject getGridSchemeColumnsForEdit(HttpServletRequest request, String gridSchemeId) {
		return gridSchemeService.getGridSchemeColumnsForEdit(request, gridSchemeId);
	}

	@RequestMapping("/getdetailsfordisplay")
	public @ResponseBody JSONArray getGridSchemeColumnsForDisplay(HttpServletRequest request, String gridSchemeId) {
		return gridSchemeService.getGridSchemeColumnsForDisplay(request, gridSchemeId);
	}

	@RequestMapping("/deletescheme")
	public @ResponseBody ActionResult deleteGridScheme(HttpServletRequest request, String schemeid) {
		return gridSchemeService.deleteGridScheme(request, schemeid);
	}

	@RequestMapping("/schemesaveas")
	public @ResponseBody ActionResult gridSchemeSaveas(HttpServletRequest request, String schemeid, String schemename) {
		return gridSchemeService.gridSchemeSaveas(request, schemeid, schemename);
	}

	@RequestMapping("/checknamevalidate")
	public @ResponseBody ActionResult checkNameValidate(String name, String id) {
		return gridSchemeService.checkNameValidate(name, id);
	}

}
