package net.ebaolife.husqvarna.platform.controller;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.platform.service.NavigateSchemeService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/platform/scheme/navigate")

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
public class NavigateScheme {

	@Resource
	private NavigateSchemeService navigateSchemeService;

	@RequestMapping("/updatedetails")
	public @ResponseBody
	ActionResult updateNavigateSchemeDetails(String dataObjectId, String navigateschemeid,
											 String navigateSchemeName, String iconCls, Boolean cascading, Boolean allowNullRecordButton,
											 Boolean isContainNullRecord, Boolean mydefault, Boolean shareowner, Boolean shareall, String schemeDefine) {

		return navigateSchemeService.updateNavigateSchemeDetails(dataObjectId, navigateschemeid, navigateSchemeName,
				iconCls, cascading, allowNullRecordButton, isContainNullRecord, mydefault, shareowner, shareall,
				schemeDefine);
	}

	@RequestMapping("/getdetails")
	public @ResponseBody JSONObject getNavigateSchemeDetails(HttpServletRequest request, String navigateschemeid) {
		return navigateSchemeService.getNavigateSchemeDetails(request, navigateschemeid);
	}

	@RequestMapping("/deletescheme")
	public @ResponseBody ActionResult deleteNavigateScheme(HttpServletRequest request, String schemeid) {
		return navigateSchemeService.deleteNavigateScheme(request, schemeid);
	}

	@RequestMapping("/schemesaveas")
	public @ResponseBody ActionResult navigateSchemeSaveas(HttpServletRequest request, String schemeid,
			String schemename) {
		return navigateSchemeService.navigateSchemeSaveas(request, schemeid, schemename);
	}

	@RequestMapping("/checknamevalidate")
	public @ResponseBody ActionResult checkNameValidate(String name, String id) {
		return navigateSchemeService.checkNameValidate(name, id);
	}

}
