package net.ebaolife.husqvarna.framework.dao.entity.module;

import net.ebaolife.husqvarna.framework.dao.entity.system.FCompany;
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
@Table(name = "f_companymodulegroup")

public class FCompanymodulegroup implements java.io.Serializable {

	private String cmodulegroupid;
	private FCompany FCompany;
	private FCompanymodulegroup FCompanymodulegroup;
	private String groupname;
	private Integer orderno;
	private String levelid;
	private Set<FCompanymodulegroup> FCompanymodulegroups = new HashSet<FCompanymodulegroup>(0);
	private Set<FCompanymodule> FCompanymodules = new HashSet<FCompanymodule>(0);

	public FCompanymodulegroup() {
	}

	public FCompanymodulegroup(FCompany FCompany, String groupname, Integer orderno,
			FCompanymodulegroup FCompanymodulegroup) {
		this.FCompany = FCompany;
		this.groupname = groupname;
		this.orderno = orderno;
		this.FCompanymodulegroup = FCompanymodulegroup;
	}

	public FCompanymodulegroup(String cmodulegroupid, FCompany FCompany, FCompanymodulegroup FCompanymodulegroup,
			String groupname, Integer orderno, String levelid, Set<FCompanymodulegroup> FCompanymodulegroups,
			Set<FCompanymodule> FCompanymodules) {
		this.cmodulegroupid = cmodulegroupid;
		this.FCompany = FCompany;
		this.FCompanymodulegroup = FCompanymodulegroup;
		this.groupname = groupname;
		this.orderno = orderno;
		this.levelid = levelid;
		this.FCompanymodulegroups = FCompanymodulegroups;
		this.FCompanymodules = FCompanymodules;
	}

	@Id

	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "cmodulegroupid", unique = true, nullable = false, length = 40)
	public String getCmodulegroupid() {
		return this.cmodulegroupid;
	}

	public void setCmodulegroupid(String cmodulegroupid) {
		this.cmodulegroupid = cmodulegroupid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyid", nullable = false)
	public net.ebaolife.husqvarna.framework.dao.entity.system.FCompany getFCompany() {
		return this.FCompany;
	}

	public void setFCompany(FCompany FCompany) {
		this.FCompany = FCompany;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public FCompanymodulegroup getFCompanymodulegroup() {
		return this.FCompanymodulegroup;
	}

	public void setFCompanymodulegroup(FCompanymodulegroup FCompanymodulegroup) {
		this.FCompanymodulegroup = FCompanymodulegroup;
	}

	@Column(name = "groupname", nullable = false, length = 100)
	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Column(name = "orderno", nullable = false)
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "levelid", length = 20)
	public String getLevelid() {
		return this.levelid;
	}

	public void setLevelid(String levelid) {
		this.levelid = levelid;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FCompanymodulegroup")
	@OrderBy("orderno")
	public Set<FCompanymodulegroup> getFCompanymodulegroups() {
		return this.FCompanymodulegroups;
	}

	public void setFCompanymodulegroups(Set<FCompanymodulegroup> FCompanymodulegroups) {
		this.FCompanymodulegroups = FCompanymodulegroups;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FCompanymodulegroup")
	@OrderBy("orderno")
	public Set<FCompanymodule> getFCompanymodules() {
		return this.FCompanymodules;
	}

	public void setFCompanymodules(Set<FCompanymodule> FCompanymodules) {
		this.FCompanymodules = FCompanymodules;
	}

}
