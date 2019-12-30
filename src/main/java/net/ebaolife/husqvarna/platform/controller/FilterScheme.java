package net.ebaolife.husqvarna.platform.controller;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.platform.service.FilterSchemeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/platform/scheme/filter")

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
public class FilterScheme {

	@Resource
	private FilterSchemeService filterSchemeService;

	@RequestMapping("/updatedetails")
	public @ResponseBody
	ActionResult updateFilterSchemeDetails(HttpServletRequest request, String dataObjectId,
										   String filterSchemeId, String schemeDefine, String filterSchemeName, Boolean mydefault, Boolean shareowner,
										   Boolean shareall) {

		return filterSchemeService.updateFilterSchemeDetails(request, dataObjectId, filterSchemeId, filterSchemeName,
				schemeDefine, mydefault, shareowner, shareall);
	}

	@RequestMapping("/getdetails")
	public @ResponseBody JSONObject getFilterSchemeDetails(HttpServletRequest request, String filterSchemeId) {
		return filterSchemeService.getFilterSchemeDetails(request, filterSchemeId);
	}

	@RequestMapping("/deletescheme")
	public @ResponseBody ActionResult deleteFilterScheme(HttpServletRequest request, String schemeid) {
		return filterSchemeService.deleteFilterScheme(request, schemeid);
	}

	@RequestMapping("/schemesaveas")
	public @ResponseBody ActionResult filterSchemeSaveas(HttpServletRequest request, String schemeid,
			String schemename) {
		return filterSchemeService.filterSchemeSaveas(request, schemeid, schemename);
	}

	@RequestMapping("/checknamevalidate")
	public @ResponseBody ActionResult checkNameValidate(String name, String id) {
		return filterSchemeService.checkNameValidate(name, id);
	}

}
