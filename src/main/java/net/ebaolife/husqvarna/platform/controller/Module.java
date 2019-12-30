package net.ebaolife.husqvarna.platform.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.core.annotation.SystemLogs;
import net.ebaolife.husqvarna.framework.dao.entity.module.FModule;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovFilterscheme;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridscheme;
import net.ebaolife.husqvarna.platform.service.ModuleHierarchyService;
import net.ebaolife.husqvarna.platform.service.ModuleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Set;

@Controller
@RequestMapping("/platform/module")
public class Module {

	@Resource
	private ModuleService service;

	@Resource
	private ModuleHierarchyService moduleHierarchyService;

	@SystemLogs("获取系统模块数据")
	@RequestMapping(value = "/getmoduleinfo")
	@ResponseBody
	public FModule getModuleInfo(String moduleid) {
		return service.getModuleInfo(moduleid);
	}

	@SystemLogs("获取模块的一个列表方案")
	@RequestMapping("/getgridscheme")
	@ResponseBody
	public FovGridscheme getGridScheme(String schemeid) {
		return service.getGridScheme(schemeid);
	}

	@SystemLogs("获取模块的一个排序方案")
	@RequestMapping("/getsortscheme")
	@ResponseBody
	public JSONObject getSortScheme(String schemeid) {
		return service.getSortScheme(schemeid);
	}

	@SystemLogs("获取模块的一个自定义筛选方案")
	@RequestMapping("/getfilterscheme")
	@ResponseBody
	public FovFilterscheme getFilterScheme(String schemeid) {
		return service.getFilterScheme(schemeid);
	}

	@SystemLogs("重新获取系统列表方案")
	@RequestMapping(value = "/getmodulegridsinfo")
	@ResponseBody
	public Map<String, Set<FovGridscheme>> getModuleGridSchemeInfo(String moduleid) {
		return service.getGridSchemes(moduleid);
	}

	@RequestMapping("/getModuleFields")
	@ResponseBody
	public JSONArray getModuleFieldsForSelect(String moduleName, Boolean isChildModule, String modulePath,
			String withoutcheck, HttpServletRequest request) {
		return moduleHierarchyService.getModuleFieldsForSelect(moduleName, isChildModule, modulePath,
				withoutcheck == null || withoutcheck.length() == 0);

	}

	@RequestMapping("/getModuleHierarchyTree")
	@ResponseBody
	public JSONObject getModuleHierarchyTree(String moduleName, HttpServletRequest request, Boolean onlyParentModule,
			Boolean onlyChildModule) {
		return moduleHierarchyService.getModuleHierarchTree(moduleName,
				onlyParentModule == null ? false : onlyParentModule, onlyChildModule == null ? false : onlyChildModule,
				request);
	}
}
