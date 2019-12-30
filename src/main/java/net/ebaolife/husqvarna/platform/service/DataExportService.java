package net.ebaolife.husqvarna.platform.service;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.bean.GridParams;
import net.ebaolife.husqvarna.framework.bean.GroupParameter;
import net.ebaolife.husqvarna.framework.bean.SortParameter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserDefineFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserNavigateFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.filter.UserParentFilter;
import net.ebaolife.husqvarna.framework.core.dataobject.grid.ExcelColumn;
import net.ebaolife.husqvarna.framework.core.dataobject.util.ExcelExport;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.attachment.FDataobjectattachment;
import net.ebaolife.husqvarna.framework.dao.entity.datainorout.FRecordexcelscheme;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridsortscheme;
import net.ebaolife.husqvarna.framework.utils.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.*;

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
public class DataExportService {

	@Autowired
	private DataObjectService dataObjectService;

	@Autowired
	private DaoImpl dao;

	@Autowired
	private AttachmentService attachmentService;

	public OutputStream generateExcelorPDF(String moduleName, GridParams pg, List<ExcelColumn> exportColumns,
										   List<SortParameter> sort, GroupParameter group, List<UserDefineFilter> query, List<UserDefineFilter> filter,
										   List<UserNavigateFilter> navigates, List<UserParentFilter> userParentFilters,
										   List<UserDefineFilter> conditions, FovGridsortscheme sortscheme, JSONObject sqlparam, boolean colorless,
										   boolean usemonetary, boolean sumless) {

		ExcelColumn[] cs = new ExcelColumn[exportColumns.size()];

		for (int i = 0; i < exportColumns.size(); i++)
			cs[i] = exportColumns.get(i);
		JSONObject rowCountJson = new JSONObject();
		rowCountJson.put("rowCount", 0);

		ExcelColumn.setColRowSize(cs, 0, 0, rowCountJson);

		ExcelColumn.setAllLastRow(cs, rowCountJson.getIntValue("rowCount"));

		List<ExcelColumn> dataIndexColumns = new ArrayList<ExcelColumn>();
		ExcelColumn.genAllDataIndexColumns(cs, dataIndexColumns);

		List<ExcelColumn> allColumns = new ArrayList<ExcelColumn>();
		ExcelColumn.genAllColumns(cs, allColumns);

		Integer rowCount = rowCountJson.getIntValue("rowCount") + 1;
		Integer colCount = dataIndexColumns.size();

		List<?> resultList = dataObjectService.fetchDataInner(moduleName, pg, null, group, sort, query, filter,
				navigates, userParentFilters, sortscheme, sqlparam).getData();
		FDataobject module = DataObjectUtils.getDataObject(moduleName);
		return new ExcelExport(colorless, usemonetary, sumless).GenExcel(module, conditions, resultList, group, null,
				rowCount, colCount, allColumns, dataIndexColumns);

	}

	public void exportExcelScheme(String objectid, String schemeid, String recordid) throws Exception {
		FRecordexcelscheme scheme = dao.findById(FRecordexcelscheme.class, schemeid);
		FDataobject dataobject = scheme.getFDataobject();
		FDataobject excelSchemeobject = DataObjectUtils.getDataObject(FRecordexcelscheme.class.getSimpleName());

		FDataobjectattachment attachment = dao.findByPropertyFirst(FDataobjectattachment.class, "objectid",
				excelSchemeobject.getObjectid(), "idvalue", schemeid);
		if (attachment == null) {
			CommonFunction.downloadFileError(Local.getResponse(), "没有找到当前方案的附件文件!");
			return;
		}

		FDataobject recordObject = DataObjectUtils.getDataObject(objectid);
		Map<String, Object> recordMap = dataObjectService.getObjectRecordMap(objectid, recordid);
		String recordtitle = recordMap.get(dataobject.getNamefield()).toString();
		Class<?> objectClass = Class.forName(recordObject.getClassname());
		Object record = dao.findById(objectClass, recordid);

		String filename = recordtitle + "--" + attachment.getFilename();

		InputStream inputStream = attachmentService.getOriginalFileStream(attachment);

		if (attachment.getSuffixname().equalsIgnoreCase("xlsx")) {

			XSSFWorkbook excelDocument = ExcelUtils.createNewExcel(inputStream);
			ExcelUtils.replace(excelDocument, record, recordMap);
			OutputStream fopts = new ByteArrayOutputStream();
			excelDocument.write(fopts);
			CommonFunction.download(fopts, filename, Local.getResponse());
		} else {

			XWPFDocument wordDocument = WordUtils.createNewWord(inputStream);
			Set<String> templateWord = WordUtils.getAllTemplateWord(wordDocument);
			Map<String, Object> param = new HashMap<String, Object>();
			for (String key : templateWord) {
				String value = FieldTemplateTranslateUtils.getStringValue(key, record, recordMap);
				if (value.equals(FieldTemplateTranslateUtils.NOSUCHPROPERTY))
					continue;
				param.put(key, value);
			}
			WordUtils.replace(wordDocument, param);
			OutputStream fopts = new ByteArrayOutputStream();
			wordDocument.write(fopts);
			CommonFunction.download(fopts, filename, Local.getResponse());
		}
	}

}
