package net.ebaolife.husqvarna.framework.bean;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("serial")
public class TreeNodeRecord implements Serializable {

	
	private String text; 
	private Boolean leaf; 
	private Boolean expanded; 
	private String icon; 
	private String tooltip; 
	private List<TreeNodeRecord> children; 

	
	private String moduleName; 
	private String tableAsName; 

	private String parentId;
	private String fieldname; 
	private String fieldtitle; 
	private String fieldvalue; 
	private String equalsMethod; 
	private String nativeValue; 
	private Integer count; 
	private Boolean isCodeLevel; 
	private Integer tag; 
	private String aggregationType; 

	public TreeNodeRecord() {

	}

	public TreeNodeRecord(String moduleName, String tableAsName, String text, String fieldname,
			String fieldvalue, String equalsMethod, Boolean isCodeLevel) {
		super();
		this.moduleName = moduleName;
		this.tableAsName = tableAsName;
		if (text != null) {
			if (text.equals("true"))
				text = "已选中";
			if (text.equals("false"))
				text = "未选中";
		}
		this.leaf = true;
		this.expanded = true;
		this.text = text;
		this.fieldname = fieldname;
		this.fieldvalue = fieldvalue;
		this.equalsMethod = equalsMethod;
		this.nativeValue = this.fieldvalue;
		this.isCodeLevel = isCodeLevel;
		this.tag = -1;
	}

	public String getText() {
		
		
		
		
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getIcon() {
		if (icon != null)
			return "images/module/" + icon + ".png";
		else
			return null;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getFieldtitle() {
		return fieldtitle;
	}

	public void setFieldtitle(String fieldtitle) {
		this.fieldtitle = fieldtitle;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldvalue() {
		return fieldvalue;
	}

	public void setFieldvalue(String fieldvalue) {
		this.fieldvalue = fieldvalue;
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public String getEqualsMethod() {
		return equalsMethod;
	}

	public void setEqualsMethod(String equalsMethod) {
		this.equalsMethod = equalsMethod;
	}

	public Boolean getLeaf() {
		return leaf == null ? false : leaf;
	}

	public void setLeaf(Boolean leaf) {
		this.leaf = leaf;
	}

	public String getNativeValue() {
		return nativeValue;
	}

	public void setNativeValue(String nativeValue) {
		this.nativeValue = nativeValue;
	}

	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getTableAsName() {
		return tableAsName;
	}

	public void setTableAsName(String tableAsName) {
		this.tableAsName = tableAsName;
	}

	public Integer getTag() {
		return tag;
	}

	public void setTag(Integer tag) {
		this.tag = tag;
	}

	public Integer getCount() {
		return count == null ? 0 : count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public Boolean getIsCodeLevel() {
		return isCodeLevel;
	}

	public void setIsCodeLevel(Boolean isCodeLevel) {
		this.isCodeLevel = isCodeLevel;
	}

	public List<TreeNodeRecord> getChildren() {
		if (children == null)
			children = new ArrayList<TreeNodeRecord>();
		return children;
	}

	public void setChildren(List<TreeNodeRecord> children) {
		this.children = children;
	}

	public Boolean hasChildren() {
		return (children != null && children.size() > 0);
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public String getAggregationType() {
		return aggregationType;
	}

	public void setAggregationType(String aggregationType) {
		this.aggregationType = aggregationType;
	}

}
