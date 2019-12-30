package net.ebaolife.husqvarna.platform.controller;

import net.ebaolife.husqvarna.platform.service.SystemBaseCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/platform/basecode")
public class SystemBaseCode {
	
	@Autowired
	private SystemBaseCodeService service;
	
	@RequestMapping(value = "/getviewlist")
	@ResponseBody
	public List<Map<String,Object>> getViewList(String viewname,String ids,String idfield,String textfield,String orderbyfield){
		return service.getViewList(viewname,ids,idfield,textfield,orderbyfield);
	}
	
}
