package net.ebaolife.husqvarna.framework.core.dataobjectquery.sqlfield;
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
public class SqlField {

	private int orderno;
	private String sqlstatment;
	private String fieldname;
	private String scale;
	private String remark;
	private boolean isgroup;

	public SqlField() {

	}

	public SqlField(String fieldname, String sqlstatment) {
		super();
		this.fieldname = fieldname;
		this.scale = fieldname;
		this.sqlstatment = sqlstatment;
	}

	@Override
	public String toString() {
		return "SqlField [fieldname=" + fieldname + ", sqlstatment=" + sqlstatment + "]";
	}

	public String getSqlstatment() {
		return sqlstatment;
	}

	public void setSqlstatment(String sqlstatment) {
		this.sqlstatment = sqlstatment;
	}

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getScale() {
		return scale;
	}

	public void setScale(String scale) {
		this.scale = scale;
	}

	public int getOrderno() {
		return orderno;
	}

	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fieldname == null) ? 0 : fieldname.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SqlField other = (SqlField) obj;
		if (fieldname == null) {
			if (other.fieldname != null)
				return false;
		} else if (!fieldname.equals(other.fieldname))
			return false;
		return true;
	}

	public boolean isIsgroup() {
		return isgroup;
	}

	public void setIsgroup(boolean isgroup) {
		this.isgroup = isgroup;
	}

}
