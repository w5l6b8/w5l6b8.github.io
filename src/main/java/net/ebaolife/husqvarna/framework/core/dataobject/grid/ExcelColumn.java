package net.ebaolife.husqvarna.framework.core.dataobject.grid;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import java.util.List;

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
public class ExcelColumn {
	private String gridFieldId;
	private String text;
	private String dataIndex;
	private Boolean hidden;
	private boolean ismonetary;

	private int firstCol;
	private int lastCol;
	private int firstRow;
	private int lastRow;

	private ExcelColumn[] items;

	public ExcelColumn() {

	}

	public static int setColRowSize(ExcelColumn[] items, int col, int row, JSONObject rowCount) {
		int colCount = 0;
		for (ExcelColumn column : items) {
			if (column.items != null) {
				column.setFirstRow(row); 
				column.setLastRow(row);
				column.setFirstCol(col + colCount);
				int subColumn = setColRowSize(column.items, col + colCount, row + 1, rowCount);
				colCount += subColumn;
				column.setLastCol(col + colCount - 1);
			} else {
				
				column.setFirstCol(col + colCount);
				column.setLastCol(col + colCount);
				column.setFirstRow(row); 
				colCount++;
			}
		}

		if (rowCount.getIntValue("rowCount") < row)
			rowCount.put("rowCount", row);
		return colCount;
	}






	public static void setAllLastRow(ExcelColumn[] items, int lastRow) {
		for (ExcelColumn column : items) {
			if (column.items == null)
				column.setLastRow(lastRow);
			else
				setAllLastRow(column.getItems(), lastRow);
		}
	}

	public static void genAllDataIndexColumns(ExcelColumn[] items, List<ExcelColumn> dataIndexColumns) {
		for (ExcelColumn column : items) {
			if (column.items == null)
				dataIndexColumns.add(column);
			else
				genAllDataIndexColumns(column.getItems(), dataIndexColumns);
		}
	}

	public static void genAllColumns(ExcelColumn[] items, List<ExcelColumn> dataIndexColumns) {
		for (ExcelColumn column : items) {
			dataIndexColumns.add(column);
			if (column.items != null)
				genAllColumns(column.getItems(), dataIndexColumns);
		}
	}






	public static List<ExcelColumn> changeToExportColumn(String str) {
		if (str != null && str.length() > 1) {
			return JSON.parseArray(str, ExcelColumn.class);
		} else
			return null;
	}

	@Override
	public String toString() {
		String result = "text=" + text + " " + firstCol + "-" + lastCol + "--" + firstRow + "-" + lastRow + ";\r\n";
		if (items != null)
			for (ExcelColumn column : items)
				result += column.toString();
		return result;
	}

	public String getGridFieldId() {
		return gridFieldId;
	}

	public void setGridFieldId(String gridFieldId) {
		this.gridFieldId = gridFieldId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDataIndex() {
		return dataIndex;
	}

	public void setDataIndex(String dataIndex) {
		this.dataIndex = dataIndex;
	}

	public Boolean getHidden() {
		return hidden == null ? false : hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	public int getFirstCol() {
		return firstCol;
	}

	public void setFirstCol(int firstCol) {
		this.firstCol = firstCol;
	}

	public int getLastCol() {
		return lastCol;
	}

	public void setLastCol(int lastCol) {
		this.lastCol = lastCol;
	}

	public int getFirstRow() {
		return firstRow;
	}

	public void setFirstRow(int firstRow) {
		this.firstRow = firstRow;
	}

	public int getLastRow() {
		return lastRow;
	}

	public void setLastRow(int lastRow) {
		this.lastRow = lastRow;
	}

	public ExcelColumn[] getItems() {
		return items;
	}

	public void setItems(ExcelColumn[] items) {
		this.items = items;
	}

  public boolean isIsmonetary() {
    return ismonetary;
  }

  public void setIsmonetary(boolean ismonetary) {
    this.ismonetary = ismonetary;
  }

}
