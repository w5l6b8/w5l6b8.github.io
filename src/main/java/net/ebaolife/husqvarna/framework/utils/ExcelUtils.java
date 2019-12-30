package net.ebaolife.husqvarna.framework.utils;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import java.util.Set;

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
public class ExcelUtils {

	public static XSSFWorkbook createNewExcel(InputStream inputStream) throws IOException {
		XSSFWorkbook xls = new XSSFWorkbook(inputStream);
		return xls;
	}

	public static XSSFWorkbook createNewExcel(String template) throws Exception {
		OPCPackage pack = POIXMLDocument.openPackage(template);
		XSSFWorkbook doc = new XSSFWorkbook(pack);
		return doc;
	}

	public static void replace(XSSFWorkbook excelDocument, Object record, Map<String, Object> recordMap) {
		Sheet sheet = excelDocument.getSheetAt(0);
		int rowNum = sheet.getLastRowNum();
		for (int row = 0; row < rowNum + 1; row++) {
			XSSFRow arow = (XSSFRow) sheet.getRow(row);

			for (int col = 0; col < arow.getLastCellNum(); col++) {
				Cell cell = arow.getCell(col);
				if (cell == null)
					continue;
				int celltype = cell.getCellType();
				switch (celltype) {

				case Cell.CELL_TYPE_NUMERIC:

					break;
				case Cell.CELL_TYPE_STRING:
					String strValue = cell.getStringCellValue();
					Set<String> templateWord = CommonUtils.getAllTemplateWord(strValue);
					if (templateWord.size() > 0) {
						for (String key : templateWord) {
							String value = FieldTemplateTranslateUtils.getStringValue(key, record, recordMap);
							if (value.equals(FieldTemplateTranslateUtils.NOSUCHPROPERTY))
								continue;
							strValue = strValue.replace(key, value);
						}
					}

					cell.setCellValue(strValue);

					break;

				case Cell.CELL_TYPE_FORMULA:

					break;

				case Cell.CELL_TYPE_BLANK:

					break;

				case Cell.CELL_TYPE_BOOLEAN:

					break;
				}

			}

		}

	}

}
