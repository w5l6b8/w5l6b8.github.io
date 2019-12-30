package net.ebaolife.husqvarna.framework.dao.entity.limit;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.system.FCompany;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
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
@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name = "f_datafilterrole")

public class FDatafilterrole implements java.io.Serializable {

	private String roleid;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FCompany FCompany;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject FDataobject;
	private String rolecode;
	private String rolename;
	private String rolegroup;
	private boolean isvalid;
	private Integer orderno;
	private String creater;
	private Timestamp createdate;
	private String lastmodifier;
	private Timestamp lastmodifydate;
	private Set<FDatafilterrolelimit> limits = new LinkedHashSet<FDatafilterrolelimit>(0);
	private Set<FUserdatafilterrole> FUserdatafilterroles = new LinkedHashSet<FUserdatafilterrole>(0);

	public FDatafilterrole() {
	}

	public String _getConditionText() {
		List<String> conditions = new ArrayList<String>();
		for (FDatafilterrolelimit detail : getLimits()) {
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
		for (FDatafilterrolelimit detail : getLimits()) {
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
	@Column(name = "roleid", unique = true, nullable = false, length = 40)
	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyid", nullable = false)
	public FCompany getFCompany() {
		return this.FCompany;
	}

	public void setFCompany(FCompany FCompany) {
		this.FCompany = FCompany;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objectid", nullable = false)
	public FDataobject getFDataobject() {
		return this.FDataobject;
	}

	public void setFDataobject(FDataobject FDataobject) {
		this.FDataobject = FDataobject;
	}

	@Column(name = "rolecode", nullable = false, length = 40)
	public String getRolecode() {
		return this.rolecode;
	}

	public void setRolecode(String rolecode) {
		this.rolecode = rolecode;
	}

	@Column(name = "rolename", nullable = false, length = 40)
	public String getRolename() {
		return this.rolename;
	}

	public void setRolename(String rolename) {
		this.rolename = rolename;
	}

	@Column(name = "rolegroup", length = 40)

	public String getRolegroup() {
		return rolegroup;
	}

	public void setRolegroup(String rolegroup) {
		this.rolegroup = rolegroup;
	}

	@Column(name = "isvalid", nullable = false)
	public boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(boolean isvalid) {
		this.isvalid = isvalid;
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

	@Column(name = "createdate", nullable = false, length = 19)
	public Timestamp getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	@Column(name = "lastmodifier", length = 40)
	public String getLastmodifier() {
		return this.lastmodifier;
	}

	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}

	@Column(name = "lastmodifydate", length = 19)
	public Timestamp getLastmodifydate() {
		return this.lastmodifydate;
	}

	public void setLastmodifydate(Timestamp lastmodifydate) {
		this.lastmodifydate = lastmodifydate;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDatafilterrole")
	@OrderBy("orderno")
	public Set<FDatafilterrolelimit> getLimits() {
		return this.limits;
	}

	public void setLimits(Set<FDatafilterrolelimit> limits) {
		this.limits = limits;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDatafilterrole")
	public Set<FUserdatafilterrole> getFUserdatafilterroles() {
		return this.FUserdatafilterroles;
	}

	public void setFUserdatafilterroles(Set<FUserdatafilterrole> FUserdatafilterroles) {
		this.FUserdatafilterroles = FUserdatafilterroles;
	}

}
