package net.ebaolife.husqvarna.framework.bean;

import java.io.Serializable;
import java.math.BigInteger;

public class TableFieldBean implements Serializable {
	private static final long serialVersionUID = 1L;
	private String fieldname;
	private String fieldtitle;
	private String fieldtype;
	private BigInteger fieldlen;
	private String fieldrelation;
	private String jointable;
	private String joincolumnname;
	private Boolean isrequired;
	private String comments;

	private Integer dataprecision;
	private Integer datascale;

	private String by1;

	private String by2;

	private String by3;

	private String by4;

	private String by5;

	public String getFieldname() {
		return fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public String getFieldtitle() {
		return fieldtitle;
	}

	public void setFieldtitle(String fieldtitle) {
		this.fieldtitle = fieldtitle;
	}

	public String getFieldtype() {
		return fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	public BigInteger getFieldlen() {
		return fieldlen;
	}

	public void setFieldlen(BigInteger fieldlen) {
		this.fieldlen = fieldlen;
	}

	public String getFieldrelation() {
		return fieldrelation;
	}

	public void setFieldrelation(String fieldrelation) {
		this.fieldrelation = fieldrelation;
	}

	public String getJointable() {
		return jointable;
	}

	public void setJointable(String jointable) {
		this.jointable = jointable;
	}

	public String getJoincolumnname() {
		return joincolumnname;
	}

	public void setJoincolumnname(String joincolumnname) {
		this.joincolumnname = joincolumnname;
	}

	public Boolean getIsrequired() {
		return this.isrequired;
	}

	public void setIsrequired(Boolean isrequired) {
		this.isrequired = isrequired;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public String getBy1() {
		return by1;
	}

	public void setBy1(String by1) {
		this.by1 = by1;
	}

	public String getBy2() {
		return by2;
	}

	public void setBy2(String by2) {
		this.by2 = by2;
	}

	public String getBy3() {
		return by3;
	}

	public void setBy3(String by3) {
		this.by3 = by3;
	}

	public String getBy4() {
		return by4;
	}

	public void setBy4(String by4) {
		this.by4 = by4;
	}

	public String getBy5() {
		return by5;
	}

	public void setBy5(String by5) {
		this.by5 = by5;
	}

	public Integer getDataprecision() {
		return dataprecision;
	}

	public void setDataprecision(Integer dataprecision) {
		this.dataprecision = dataprecision;
	}

	public Integer getDatascale() {
		return datascale;
	}

	public void setDatascale(Integer datascale) {
		this.datascale = datascale;
	}

}
