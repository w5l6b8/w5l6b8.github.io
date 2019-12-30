package net.ebaolife.husqvarna.framework.dao.entity.groupquery;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
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
@Table(name = "f_groupquerygroup")
public class FGroupquerygroup implements java.io.Serializable {

	private String groupid;
	private FGroupquerygroup FGroupquerygroup;
	private String title;
	private Integer orderno;
	private Boolean isdisable;
	private String iconurl;
	private String iconcls;
	private Boolean[] iconfile;
	private String remark;
	private Set<FGroupquery> FGroupqueries = new HashSet<FGroupquery>(0);
	private Set<FGroupquerygroup> FGroupquerygroups = new HashSet<FGroupquerygroup>(0);

	public FGroupquerygroup() {
	}

	public FGroupquerygroup(String groupid, String title) {
		this.groupid = groupid;
		this.title = title;
	}

	public FGroupquerygroup(String groupid, FGroupquerygroup FGroupquerygroup, String title, Integer orderno,
			Boolean isdisable, String iconurl, String iconcls, Boolean[] iconfile, String remark,
			Set<FGroupquery> FGroupqueries, Set<FGroupquerygroup> FGroupquerygroups) {
		this.groupid = groupid;
		this.FGroupquerygroup = FGroupquerygroup;
		this.title = title;
		this.orderno = orderno;
		this.isdisable = isdisable;
		this.iconurl = iconurl;
		this.iconcls = iconcls;
		this.iconfile = iconfile;
		this.remark = remark;
		this.FGroupqueries = FGroupqueries;
		this.FGroupquerygroups = FGroupquerygroups;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "groupid", unique = true, nullable = false, length = 40)
	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public FGroupquerygroup getFGroupquerygroup() {
		return this.FGroupquerygroup;
	}

	public void setFGroupquerygroup(FGroupquerygroup FGroupquerygroup) {
		this.FGroupquerygroup = FGroupquerygroup;
	}

	@Column(name = "title", nullable = false, length = 50)
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

	@Column(name = "isdisable")
	public Boolean getIsdisable() {
		return this.isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	@Column(name = "iconurl", length = 200)
	public String getIconurl() {
		return this.iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	@Column(name = "iconcls", length = 50)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "iconfile")
	public Boolean[] getIconfile() {
		return this.iconfile;
	}

	public void setIconfile(Boolean[] iconfile) {
		this.iconfile = iconfile;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FGroupquerygroup")
	public Set<FGroupquery> getFGroupqueries() {
		return this.FGroupqueries;
	}

	public void setFGroupqueries(Set<FGroupquery> FGroupqueries) {
		this.FGroupqueries = FGroupqueries;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FGroupquerygroup")
	public Set<FGroupquerygroup> getFGroupquerygroups() {
		return this.FGroupquerygroups;
	}

	public void setFGroupquerygroups(Set<FGroupquerygroup> FGroupquerygroups) {
		this.FGroupquerygroups = FGroupquerygroups;
	}

}
