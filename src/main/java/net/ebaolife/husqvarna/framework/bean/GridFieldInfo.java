package net.ebaolife.husqvarna.framework.bean;

import java.io.Serializable;

@SuppressWarnings("serial")
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
public class GridFieldInfo implements Serializable {

	public static final String GRIDFIELDINFO = "gridfieldinfo";

	private String groupName;
	private String fieldId;
	private String fieldname;
	private String fieldtype;
	private String valueFieldname;
	private String title;
	private Boolean allowSubTotal;
	private Boolean twoRows;
	private String unitText;
	private boolean ismonetary;

	public GridFieldInfo() {

	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getFieldId() {
		return fieldId;
	}

	public void setFieldId(String fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldname() {
		return fieldname;
	}

	public String getFactFieldname() {
		if (valueFieldname != null)
			return valueFieldname;
		else
			return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	public String getValueFieldname() {
		return valueFieldname;
	}

	public void setValueFieldname(String valueFieldname) {
		this.valueFieldname = valueFieldname;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitleAndUnitTextWithOutGroupName() {
		String result;
		if (unitText == null || unitText.length() == 0)
			result = title.replaceAll("--", "");
		else
			result = title.replaceAll("--", "") + "\n(" + unitText + ")";
		if (groupName != null && result != null) {
			if (result.indexOf(groupName) == 0)
				result = result.substring(groupName.length());
			if (result.length() == 0)
				result = groupName;
		}
		return result;
	}

	public String getTitleAndUnitText() {
		if (unitText == null || unitText.length() == 0)
			return title.replaceAll("--", "");
		else
			return title.replaceAll("--", "") + "\n(" + unitText + ")";
	}

	public Boolean getAllowSubTotal() {
		return allowSubTotal == null ? false : allowSubTotal;
	}

	public void setAllowSubTotal(Boolean allowSubTotal) {
		this.allowSubTotal = allowSubTotal;
	}

	public Boolean getTwoRows() {
		return twoRows;
	}

	public void setTwoRows(Boolean twoRows) {
		this.twoRows = twoRows;
	}

	public String getUnitText() {
		return unitText;
	}

	public void setUnitText(String unitText) {
		this.unitText = unitText;
	}

	public boolean isIsmonetary() {
		return ismonetary;
	}

	public void setIsmonetary(boolean ismonetary) {
		this.ismonetary = ismonetary;
	}

}
