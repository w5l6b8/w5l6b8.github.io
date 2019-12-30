package net.ebaolife.husqvarna.framework.dao.entity.dictionary;

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
@Table(name = "f_numbergroupdetail", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "numbergroupid", "orderno" }),
		@UniqueConstraint(columnNames = { "numbergroupid", "title" }) })
public class FNumbergroupdetail implements java.io.Serializable {

	private String numbergroupdetailid;
	private FNumbergroup FNumbergroup;
	private Integer orderno;
	private String title;
	private String condition1;
	private String condition2;
	private String remark;

	public FNumbergroupdetail() {
	}

	public String genExpression(String fieldname) {
		String express1 = genAExpression(fieldname, condition1);
		String express2 = genAExpression(fieldname, condition2);
		if (express1 == null)
			return express2;
		else if (express2 == null)
			return express1;
		else
			return express1 + " and " + express2;

	}

	private String genAExpression(String fieldname, String express) {
		if (express != null && express.length() > 0) {
			if (express.indexOf("this") >= 0)
				return express.replaceAll("express", fieldname);
			else
				return fieldname + " " + express;
		} else
			return null;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "numbergroupdetailid", unique = true, nullable = false, length = 40)
	public String getNumbergroupdetailid() {
		return this.numbergroupdetailid;
	}

	public void setNumbergroupdetailid(String numbergroupdetailid) {
		this.numbergroupdetailid = numbergroupdetailid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "numbergroupid", nullable = false)
	public FNumbergroup getFNumbergroup() {
		return this.FNumbergroup;
	}

	public void setFNumbergroup(FNumbergroup FNumbergroup) {
		this.FNumbergroup = FNumbergroup;
	}

	@Column(name = "orderno", nullable = false)
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

	@Column(name = "condition1", length = 200)
	public String getCondition1() {
		return this.condition1;
	}

	public void setCondition1(String condition1) {
		this.condition1 = condition1;
	}

	@Column(name = "condition2", length = 200)
	public String getCondition2() {
		return this.condition2;
	}

	public void setCondition2(String condition2) {
		this.condition2 = condition2;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
