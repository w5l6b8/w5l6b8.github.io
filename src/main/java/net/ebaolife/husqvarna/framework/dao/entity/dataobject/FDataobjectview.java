package net.ebaolife.husqvarna.framework.dao.entity.dataobject;


import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
@Entity
@DynamicUpdate
@Table(name = "f_dataobjectview", uniqueConstraints = @UniqueConstraint(columnNames = { "objectid", "title",
		"userid" }))
public class FDataobjectview implements java.io.Serializable {
	private static final long serialVersionUID = -7762313646296565956L;
	private String viewschemeid;
	private FDataobject FDataobject;
	private FUser FUser;
	private String title;
	private Integer orderno;
	private String operator;
	private Boolean isshare;
	private Boolean isshareowner;
	private String remark;
	private Set<FDataobjectviewdetail> details = new HashSet<FDataobjectviewdetail>(0);

	public FDataobjectview() {
	}

	public FDataobjectview(String viewid, String title, Integer orderno) {
		this.viewschemeid = viewid;
		this.title = title;
		this.orderno = orderno;
	}

	public FDataobjectview(String viewid, FDataobject FDataobject, FUser FUser, String title, Integer orderno,
			String operator, Boolean isshare, Boolean isshareowner, String remark,
			Set<FDataobjectviewdetail> FDataobjectviewdetails) {
		this.viewschemeid = viewid;
		this.FDataobject = FDataobject;
		this.FUser = FUser;
		this.title = title;
		this.orderno = orderno;
		this.operator = operator;
		this.isshare = isshare;
		this.isshareowner = isshareowner;
		this.remark = remark;
		this.details = FDataobjectviewdetails;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "viewschemeid", unique = true, nullable = false, length = 40)
	public String getViewschemeid() {
		return viewschemeid;
	}

	public String _getConditionText() {
		List<String> texts = new ArrayList<String>();
		for (FDataobjectviewdetail detail : details) {
			texts.add(detail.getFDataobjectcondition()._getConditionText());
		}
		StringBuffer sb = new StringBuffer();
		for (Integer i = 0; i < texts.size(); i++) {
			sb.append(texts.get(i));
			if (i != texts.size() - 1)
				sb.append(" " + (operator == null ? "and" : operator) + " ");
		}
		return " ( " + sb.toString() + " ) ";
	}

	public String _getConditionExpression() {
		List<String> texts = new ArrayList<String>();
		for (FDataobjectviewdetail detail : details) {
			texts.add(detail.getFDataobjectcondition()._getConditionExpression());
		}
		StringBuffer sb = new StringBuffer();
		for (Integer i = 0; i < texts.size(); i++) {
			sb.append(texts.get(i));
			if (i != texts.size() - 1)
				sb.append(" " + (operator == null ? "and" : operator) + " ");
		}
		return " ( " + sb.toString() + " ) ";
	}

	public void setViewschemeid(String viewschemeid) {
		this.viewschemeid = viewschemeid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objectid")
	public FDataobject getFDataobject() {
		return this.FDataobject;
	}

	public void setFDataobject(FDataobject FDataobject) {
		this.FDataobject = FDataobject;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public FUser getFUser() {
		return this.FUser;
	}

	public void setFUser(FUser FUser) {
		this.FUser = FUser;
	}

	@Column(name = "title", nullable = false, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "orderno", nullable = false)
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "operator", length = 20)
	public String getOperator() {
		return this.operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	@Column(name = "isshare")
	public Boolean getIsshare() {
		return this.isshare;
	}

	public void setIsshare(Boolean isshare) {
		this.isshare = isshare;
	}

	@Column(name = "isshareowner")
	public Boolean getIsshareowner() {
		return this.isshareowner;
	}

	public void setIsshareowner(Boolean isshareowner) {
		this.isshareowner = isshareowner;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobjectview")
	@OrderBy("orderno")
	public Set<FDataobjectviewdetail> getDetails() {
		return this.details;
	}

	public void setDetails(Set<FDataobjectviewdetail> details) {
		this.details = details;
	}

}
