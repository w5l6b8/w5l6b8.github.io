package net.ebaolife.husqvarna.platform.controller;


import net.ebaolife.husqvarna.framework.bean.ValueText;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary;
import net.ebaolife.husqvarna.platform.service.DictionaryService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/dictionary")

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
public class DictionaryController {

	@Resource
	private DictionaryService dictionaryService;

	@RequestMapping(value = "/getdictionary")
	public @ResponseBody
	FDictionary getDictionary(String id, Boolean adddetails) {
		return dictionaryService.getDictionary(id, adddetails != null && adddetails);
	}

	@RequestMapping(value = "/getDictionaryComboData")
	public @ResponseBody List<ValueText> getDictionaryComboData(String dictionaryId, Boolean idIncludeText,
																HttpServletRequest request) {

		return dictionaryService.getDictionaryComboData(dictionaryId, idIncludeText == null ? false : idIncludeText,
				request);
	}

}
