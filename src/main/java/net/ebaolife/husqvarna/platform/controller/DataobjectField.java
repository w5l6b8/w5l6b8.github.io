package net.ebaolife.husqvarna.platform.controller;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.platform.service.DataobjectFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.persistence.PersistenceException;

@Controller
@RequestMapping("/platform/dataobjectfield")

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
public class DataobjectField {

	@Autowired
	private DataobjectFieldService service;

	@RequestMapping("/getexpression")
	public @ResponseBody JSONObject getDataFilterRoleLimit(String additionfieldid) {
		return service.getUserDefineFieldExpression(additionfieldid);
	}

	@RequestMapping("/updateexpression")
	public @ResponseBody
	ActionResult updateExpression(String additionfieldid, String expression) {

		return service.updateUserDefineFieldExpression(additionfieldid, expression);
	}

	@RequestMapping(value = "/createonetomanyfield", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult createOneToManyField(String fieldid) {
		ActionResult result = null;
		try {
			result = service.createOneToManyField(fieldid);
		} catch (PersistenceException e) {
			result = new ActionResult(false,
					e.getCause().getCause() != null ? e.getCause().getCause().getMessage() : e.getCause().getMessage());
		} catch (Exception e) {
			result = new ActionResult(false, e.getCause().getMessage());
		}
		return result;
	}

	@RequestMapping(value = "/createmanytomanyfield", method = RequestMethod.POST)
	@ResponseBody
	public ActionResult createManyToManyField(String fieldtype1, String fieldtype2, String linkedobjectid) {
		ActionResult result = null;
		try {
			result = service.createManyToManyField(fieldtype1, fieldtype2, linkedobjectid, null);
		} catch (PersistenceException e) {
			result = new ActionResult(false,
					e.getCause().getCause() != null ? e.getCause().getCause().getMessage() : e.getCause().getMessage());
		} catch (Exception e) {
			result = new ActionResult(false, e.getCause().getMessage());
		}
		return result;
	}

}
