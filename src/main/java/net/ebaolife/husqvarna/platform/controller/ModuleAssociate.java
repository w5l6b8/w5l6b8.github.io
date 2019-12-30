package net.ebaolife.husqvarna.platform.controller;


import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovDataobjectassociate;
import net.ebaolife.husqvarna.platform.service.DataObjectService;
import net.ebaolife.husqvarna.platform.service.ModuleAssociateService;
import ognl.OgnlException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

@Controller
@RequestMapping("/platform/scheme/associate")

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
public class ModuleAssociate {

	@Resource
	private ModuleAssociateService moduleAssociateService;

	@Autowired
	private DataObjectService dataobjectService;

	@RequestMapping("/savesetting")
	public @ResponseBody
	ActionResult saveSetting(String associateid, String savestr) {

		try {
			dataobjectService.saveOrUpdate(FovDataobjectassociate.class.getSimpleName(), savestr, null, "edit");
		} catch (ClassNotFoundException | IllegalAccessException | InvocationTargetException | OgnlException e) {
			e.printStackTrace();
		}

		return new ActionResult();
	}

	@RequestMapping("/addsubmodule")
	public @ResponseBody ActionResult addSubModule(String objectid, String fieldahead, String title, Integer orderno,
			Boolean subobjectnavigate, Boolean subobjectsouthregion, Boolean subobjecteastregion, String region) {
		return moduleAssociateService.AddSubModule(objectid, fieldahead, title, orderno, subobjectnavigate,
				subobjectsouthregion, subobjecteastregion, region);
	}

	@RequestMapping("/addform")
	public @ResponseBody ActionResult addForm(String objectid, String formschemeid, String title, Integer orderno,
			Boolean usedfornew, Boolean usedforedit, String region) {
		return moduleAssociateService.AddForm(objectid, formschemeid, title, orderno,
				usedfornew == null ? false : usedfornew, usedforedit == null ? false : usedforedit, region);
	}

	@RequestMapping("/adduserdefine")
	public @ResponseBody ActionResult addUserDefine(String objectid, String name, String region) {
		return moduleAssociateService.addUserDefine(objectid, name, region);
	}

	@RequestMapping("/addattachment")
	public @ResponseBody ActionResult addAttachment(String objectid, String region) {
		return moduleAssociateService.addAttachment(objectid, region);
	}

	@RequestMapping("/remove")
	public @ResponseBody ActionResult remove(String detailid) {
		return moduleAssociateService.removeAssociatedetail(detailid);
	}

}
