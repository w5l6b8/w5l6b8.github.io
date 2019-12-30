package net.ebaolife.husqvarna.framework.bean;
public class TableExtInfoBean{ 
	private String tablename;
	private String columnname;
	private String tablename_cn;
	private String columnname_cn;
	private String comments;
	private Integer orderno;
	
	
	public TableExtInfoBean() {
	}
	
	public TableExtInfoBean(String tablename, String tablename_cn, String columnname, String columnname_cn, Integer orderno) {
		this.tablename = tablename;
		this.columnname = columnname;
		this.tablename_cn = tablename_cn;
		this.columnname_cn = columnname_cn;
		this.orderno = orderno;
	}
	
	
	public String getTablename() {
		return tablename;
	}
	public void setTablename(String tablename) {
		this.tablename = tablename;
	}
	public String getColumnname() {
		return columnname;
	}
	public void setColumnname(String columnname) {
		this.columnname = columnname;
	}
	public String getTablename_cn() {
		return tablename_cn;
	}
	public void setTablename_cn(String tablenameCn) {
		tablename_cn = tablenameCn;
	}
	public String getColumnname_cn() {
		return columnname_cn;
	}
	public void setColumnname_cn(String columnnameCn) {
		columnname_cn = columnnameCn;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	
	
	
	

}
