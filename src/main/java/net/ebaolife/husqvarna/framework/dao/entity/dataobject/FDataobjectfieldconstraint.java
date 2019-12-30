package net.ebaolife.husqvarna.framework.dao.entity.dataobject;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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

@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name = "f_dataobjectfieldconstraint", uniqueConstraints = @UniqueConstraint(columnNames = { "objectid",
		"title" }))
public class FDataobjectfieldconstraint implements java.io.Serializable {

	private String constraintid;
	private FDataobject FDataobject;
	private Integer orderno;
	private String title;
	private String ctype;
	private String clevel;
	private Boolean isdisable;
	private String cexpression;
	private String errormessage;
	private String errormessagefield;
	private String remark;

	public FDataobjectfieldconstraint() {
	}

	public FDataobjectfieldconstraint(String constraintid, FDataobject FDataobject, String title, String cexpression,
			String errormessage) {
		this.constraintid = constraintid;
		this.FDataobject = FDataobject;
		this.title = title;
		this.cexpression = cexpression;
		this.errormessage = errormessage;
	}

	public FDataobjectfieldconstraint(String constraintid, FDataobject FDataobject, Integer orderno, String title,
			String ctype, String clevel, Boolean isdisable, String cexpression, String errormessage,
			String errormessagefield, String remark) {
		this.constraintid = constraintid;
		this.FDataobject = FDataobject;
		this.orderno = orderno;
		this.title = title;
		this.ctype = ctype;
		this.clevel = clevel;
		this.isdisable = isdisable;
		this.cexpression = cexpression;
		this.errormessage = errormessage;
		this.errormessagefield = errormessagefield;
		this.remark = remark;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "constraintid", unique = true, nullable = false, length = 40)
	public String getConstraintid() {
		return this.constraintid;
	}

	public void setConstraintid(String constraintid) {
		this.constraintid = constraintid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objectid", nullable = false)
	public FDataobject getFDataobject() {
		return this.FDataobject;
	}

	public void setFDataobject(FDataobject FDataobject) {
		this.FDataobject = FDataobject;
	}

	@Column(name = "orderno")
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "ctype", length = 50)
	public String getCtype() {
		return this.ctype;
	}

	public void setCtype(String ctype) {
		this.ctype = ctype;
	}

	@Column(name = "clevel", length = 20)
	public String getClevel() {
		return this.clevel;
	}

	public void setClevel(String clevel) {
		this.clevel = clevel;
	}

	@Column(name = "isdisable")
	public Boolean getIsdisable() {
		return this.isdisable == null ? false : this.isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	@Column(name = "cexpression", nullable = false, length = 4000)
	public String getCexpression() {
		return this.cexpression;
	}

	public void setCexpression(String cexpression) {
		this.cexpression = cexpression;
	}

	@Column(name = "errormessage", nullable = false, length = 200)
	public String getErrormessage() {
		return this.errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	@Column(name = "errormessagefield", length = 50)
	public String getErrormessagefield() {
		return this.errormessagefield;
	}

	public void setErrormessagefield(String errormessagefield) {
		this.errormessagefield = errormessagefield;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
