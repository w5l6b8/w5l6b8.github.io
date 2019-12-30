package net.ebaolife.husqvarna.framework.bean;

import java.util.List;

public class TableBean implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	private String tablename;

	private String comment;

	private String classname;

	private String primarykey;

	private String parantkey;

	private String namefield;

	private List<TableFieldBean> fields;

	public String getTablename() {
		return tablename;
	}

	public String getClassname() {
		return classname;
	}

	public void setClassname(String classname) {
		this.classname = classname;
	}

	public String getPrimarykey() {
		return primarykey;
	}

	public void setPrimarykey(String primarykey) {
		this.primarykey = primarykey;
	}

	public String getParantkey() {
		return parantkey;
	}

	public void setParantkey(String parantkey) {
		this.parantkey = parantkey;
	}

	public String getNamefield() {
		return namefield;
	}

	public void setNamefield(String namefield) {
		this.namefield = namefield;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public List<TableFieldBean> getFields() {
		return fields;
	}

	public void setFields(List<TableFieldBean> fields) {
		this.fields = fields;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

}
