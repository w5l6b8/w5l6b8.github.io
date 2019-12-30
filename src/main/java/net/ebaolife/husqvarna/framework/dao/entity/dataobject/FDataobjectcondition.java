package net.ebaolife.husqvarna.framework.dao.entity.dataobject;

import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.LinkedHashSet;
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
@Table(name = "f_dataobjectcondition")
public class FDataobjectcondition implements java.io.Serializable {

	private static final long serialVersionUID = 4863745299591709034L;
	private String conditionid;
	private FDataobject FDataobject;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FUser FUser;
	private String title;
	private Integer orderno;
	private Boolean isshare;
	private Boolean isshareowner;
	private String remark;
	private Set<FDataobjectconditiondetail> FDataobjectconditiondetailsForSubconditionid = new LinkedHashSet<FDataobjectconditiondetail>(
			0);
	private Set<FDataobjectconditiondetail> details = new LinkedHashSet<FDataobjectconditiondetail>(0);

	public FDataobjectcondition() {
	}

	public FDataobjectcondition(String conditionid) {
		this.conditionid = conditionid;

	}

	public String _getConditionText() {
		List<String> conditions = new ArrayList<String>();
		for (FDataobjectconditiondetail detail : getDetails()) {
			conditions.add(detail._getConditionText(true));
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < conditions.size(); i++) {
			sb.append(" ( " + conditions.get(i) + " ) ");
			if (i != conditions.size() - 1)
				sb.append(" and ");
		}
		return "(" + sb.toString() + ")";
	}

	public String _getConditionExpression() {
		List<String> conditions = new ArrayList<String>();
		for (FDataobjectconditiondetail detail : getDetails()) {
			conditions.add(detail._getConditionText(false));
		}
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < conditions.size(); i++) {
			sb.append(" ( " + conditions.get(i) + " ) ");
			if (i != conditions.size() - 1)
				sb.append(" and ");
		}
		return "(" + sb.toString() + ")";
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "conditionid", unique = true, nullable = false, length = 40)
	public String getConditionid() {
		return this.conditionid;
	}

	public void setConditionid(String conditionid) {
		this.conditionid = conditionid;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobjectconditionBySubconditionid")
	@OrderBy("orderno")
	public Set<FDataobjectconditiondetail> getFDataobjectconditiondetailsForSubconditionid() {
		return this.FDataobjectconditiondetailsForSubconditionid;
	}

	public void setFDataobjectconditiondetailsForSubconditionid(
			Set<FDataobjectconditiondetail> FDataobjectconditiondetailsForSubconditionid) {
		this.FDataobjectconditiondetailsForSubconditionid = FDataobjectconditiondetailsForSubconditionid;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobjectcondition")
	@OrderBy("orderno")
	public Set<FDataobjectconditiondetail> getDetails() {
		return this.details;
	}

	public void setDetails(Set<FDataobjectconditiondetail> details) {
		this.details = details;
	}

}
