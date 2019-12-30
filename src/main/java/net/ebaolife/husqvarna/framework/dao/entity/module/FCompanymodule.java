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

@Entity
@DynamicUpdate
@Table(name = "f_companymodule")

public class FCompanymodule implements java.io.Serializable {

	private static final long serialVersionUID = 9092994044671984533L;
	private String cmoduleid;
	private FCompany FCompany;
	private FCompanymodulegroup FCompanymodulegroup;
	private FModule FModule;
	private Integer orderno;
	private Set<FCompanymenu> FCompanymenus = new HashSet<FCompanymenu>(0);
	private Set<FModulefunction> FModulefunctions = new HashSet<FModulefunction>(0);

	public FCompanymodule() {
	}

	public FCompanymodule(String cmoduleid, FCompany FCompany, FCompanymodulegroup FCompanymodulegroup,
			FModule FModule) {
		this.cmoduleid = cmoduleid;
		this.FCompany = FCompany;
		this.FCompanymodulegroup = FCompanymodulegroup;
		this.FModule = FModule;
	}

	public FCompanymodule(String cmoduleid, FCompany FCompany, FCompanymodulegroup FCompanymodulegroup, FModule FModule,
			Set<FCompanymenu> FCompanymenus, Set<FModulefunction> FModulefunctions) {
		this.cmoduleid = cmoduleid;
		this.FCompany = FCompany;
		this.FCompanymodulegroup = FCompanymodulegroup;
		this.FModule = FModule;
		this.FCompanymenus = FCompanymenus;
		this.FModulefunctions = FModulefunctions;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "cmoduleid", unique = true, nullable = false, length = 40)
	public String getCmoduleid() {
		return this.cmoduleid;
	}

	public void setCmoduleid(String cmoduleid) {
		this.cmoduleid = cmoduleid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyid", nullable = false)
	public FCompany getFCompany() {
		return this.FCompany;
	}

	public void setFCompany(net.ebaolife.husqvarna.framework.dao.entity.system.FCompany FCompany) {
		this.FCompany = FCompany;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cmodulegroupid", nullable = false)
	public FCompanymodulegroup getFCompanymodulegroup() {
		return this.FCompanymodulegroup;
	}

	public void setFCompanymodulegroup(FCompanymodulegroup FCompanymodulegroup) {
		this.FCompanymodulegroup = FCompanymodulegroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "moduleid", nullable = false)
	public FModule getFModule() {
		return this.FModule;
	}

	public void setFModule(FModule FModule) {
		this.FModule = FModule;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FCompanymodule")
	public Set<FCompanymenu> getFCompanymenus() {
		return this.FCompanymenus;
	}

	public void setFCompanymenus(Set<FCompanymenu> FCompanymenus) {
		this.FCompanymenus = FCompanymenus;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FCompanymodule")
	@OrderBy("orderno")
	public Set<FModulefunction> getFModulefunctions() {
		return this.FModulefunctions;
	}

	public void setFModulefunctions(Set<FModulefunction> FModulefunctions) {
		this.FModulefunctions = FModulefunctions;
	}

	public Integer getOrderno() {
		return orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

}