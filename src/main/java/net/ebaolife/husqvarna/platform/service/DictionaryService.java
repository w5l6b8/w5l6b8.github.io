package net.ebaolife.husqvarna.platform.service;


import net.ebaolife.husqvarna.framework.bean.ValueText;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionarydetail;
import net.ebaolife.husqvarna.framework.utils.PropertyPreFilters;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service

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
public class DictionaryService {

	@Resource
	private DaoImpl systemBaseDAO;

	@Transactional(readOnly = true)
	public List<ValueText> getDictionaryComboData(String dictionaryId, Boolean idIncludeText,
												  HttpServletRequest request) {
		List<ValueText> result = new ArrayList<ValueText>();
		FDictionary dictionary = systemBaseDAO.findById(FDictionary.class, dictionaryId);
		for (FDictionarydetail detail : dictionary.getFDictionarydetails()) {
			String value = dictionary.getIslinkedkey() ? detail.getDdetailid()
					: (dictionary.getIslinkedorderno() ? detail.getOrderno()
							: (dictionary.getIslinkedcode() ? detail.getVcode() : detail.getTitle()));

			String text = (dictionary.getIsdisplayorderno() ? detail.getOrderno() + "-" : "")
					+ (dictionary.getIsdisplaycode() ? detail.getVcode() + "-" : "") + detail.getTitle();
			ValueText vt = new ValueText(idIncludeText ? value + "|" + text : value, text);
			result.add(vt);
		}
		return result;
	}

	public FDictionary getDictionary(String id, Boolean adddetails) {
		PropertyPreFilters filters = new PropertyPreFilters();
		filters.addFilter(FDictionary.class).addExcludes("fDictionarygroup", "fDataobjectfields");
		if (!adddetails)
			filters.addFilter(FDictionary.class).addExcludes("fDictionarydetails");
		Local.writeJsonToHttpFilters(filters);
		FDictionary dictionary = systemBaseDAO.findById(FDictionary.class, id);
		if (dictionary == null)
			dictionary = systemBaseDAO.findByPropertyFirst(FDictionary.class, "dcode", id);
		return dictionary;
	}

}
