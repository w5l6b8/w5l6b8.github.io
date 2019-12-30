package net.ebaolife.husqvarna.framework.dao.entity.dataobject;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

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
@Table(name = "f_additionfield", uniqueConstraints = @UniqueConstraint(columnNames = { "objectid", "title" }))
public class FAdditionfield implements java.io.Serializable {

	private String additionfieldid;
	private FDataobject FDataobject;
	private String title;
	private Integer orderno;
	private String creater;
	private Date createdate;
	private String lastmodifier;
	private Date lastmodifydate;
	private Set<FAdditionfieldexpression> expressions = new HashSet<FAdditionfieldexpression>(0);
	private Set<FDataobjectfield> FDataobjectfields = new HashSet<FDataobjectfield>(0);

	public FAdditionfield() {
	}

	public String _getConditionText() {
		List<String> conditions = new ArrayList<String>();
		for (FAdditionfieldexpression detail : getExpressions()) {
			conditions.add(detail._getConditionText(FDataobject, true));
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
		for (FAdditionfieldexpression detail : getExpressions()) {
			conditions.add(detail._getConditionText(FDataobject, false));
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
	@Column(name = "additionfieldid", unique = true, nullable = false, length = 40)
	public String getAdditionfieldid() {
		return this.additionfieldid;
	}

	public void setAdditionfieldid(String additionfieldid) {
		this.additionfieldid = additionfieldid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objectid", nullable = false)
	public FDataobject getFDataobject() {
		return this.FDataobject;
	}

	public void setFDataobject(FDataobject FDataobject) {
		this.FDataobject = FDataobject;
	}

	@Column(name = "title", nullable = false, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "orderno")
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "creater", nullable = false, length = 40)
	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createdate", nullable = false, length = 19)
	public Date getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Date createdate) {
		this.createdate = createdate;
	}

	@Column(name = "lastmodifier", length = 40)
	public String getLastmodifier() {
		return this.lastmodifier;
	}

	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "lastmodifydate", length = 19)
	public Date getLastmodifydate() {
		return this.lastmodifydate;
	}

	public void setLastmodifydate(Date lastmodifydate) {
		this.lastmodifydate = lastmodifydate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FAdditionfield")
	@OrderBy("orderno")
	public Set<FAdditionfieldexpression> getExpressions() {
		return this.expressions;
	}

	public void setExpressions(Set<FAdditionfieldexpression> FAdditionfieldexpressions) {
		this.expressions = FAdditionfieldexpressions;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FAdditionfield")
	public Set<FDataobjectfield> getFDataobjectfields() {
		return this.FDataobjectfields;
	}

	public void setFDataobjectfields(Set<FDataobjectfield> FDataobjectfields) {
		this.FDataobjectfields = FDataobjectfields;
	}

}
