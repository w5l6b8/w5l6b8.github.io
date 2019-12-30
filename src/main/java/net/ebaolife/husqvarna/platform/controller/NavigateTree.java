package net.ebaolife.husqvarna.platform.controller;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.core.dataobject.navigate.NavigateGenerateService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/platform/navigatetree")

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
public class NavigateTree {

	@Resource
	private NavigateGenerateService navigateGenerateService;

	@RequestMapping(value = "/fetchnavigatedata", method = RequestMethod.GET)
	public @ResponseBody JSONObject getTreeRecords(String moduleName, String navigateschemeid,
			Boolean isContainNullRecord, Boolean cascading, String parentFilter, HttpServletRequest request) {
		try {
			return navigateGenerateService.genNavigateTree(moduleName, navigateschemeid, parentFilter, cascading,
					isContainNullRecord);
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
	}

}
