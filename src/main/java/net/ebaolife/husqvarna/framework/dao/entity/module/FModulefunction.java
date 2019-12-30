package net.ebaolife.husqvarna.framework.dao.entity.module;


import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectadditionfuncion;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectbasefuncion;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FRolefunctionlimit;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FUserfunctionlimit;
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
@Table(name = "f_modulefunction")

public class FModulefunction implements java.io.Serializable {

	private String functionid;
	private FCompanymodule FCompanymodule;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectadditionfuncion FDataobjectadditionfuncion;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectbasefuncion FDataobjectbasefuncion;
	private String functionname;
	private String functiontitle;
	private String description;
	private Boolean isvalid;
	private Integer orderno;
	private Set<FUserfunctionlimit> FUserfunctionlimits = new HashSet<FUserfunctionlimit>(0);
	private Set<FRolefunctionlimit> FRolefunctionlimits = new HashSet<FRolefunctionlimit>(0);

	public FModulefunction() {
	}

	public FModulefunction(String functionid) {
		this.functionid = functionid;
	}

	public FModulefunction(FCompanymodule FCompanymodule, String functionname, String functiontitle, Boolean isvalid) {
		this.FCompanymodule = FCompanymodule;
		this.functionname = functionname;
		this.functiontitle = functiontitle;
		this.isvalid = isvalid;
	}

	public FModulefunction(FCompanymodule FCompanymodule, String functionname, String functiontitle, String description,
			Boolean isvalid, Integer orderno, Set<FUserfunctionlimit> FUserfunctionlimits,
			Set<FRolefunctionlimit> FRolefunctionlimits) {
		this.FCompanymodule = FCompanymodule;
		this.functionname = functionname;
		this.functiontitle = functiontitle;
		this.description = description;
		this.isvalid = isvalid;
		this.orderno = orderno;
		this.FUserfunctionlimits = FUserfunctionlimits;
		this.FRolefunctionlimits = FRolefunctionlimits;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "functionid", unique = true, nullable = false, length = 40)

	public String getFunctionid() {
		return this.functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "cmoduleid", nullable = false)

	public FCompanymodule getFCompanymodule() {
		return this.FCompanymodule;
	}

	public void setFCompanymodule(FCompanymodule FCompanymodule) {
		this.FCompanymodule = FCompanymodule;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "additionfunctionid")
	public FDataobjectadditionfuncion getFDataobjectadditionfuncion() {
		return this.FDataobjectadditionfuncion;
	}

	public void setFDataobjectadditionfuncion(FDataobjectadditionfuncion FDataobjectadditionfuncion) {
		this.FDataobjectadditionfuncion = FDataobjectadditionfuncion;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "basefunctionid")
	public FDataobjectbasefuncion getFDataobjectbasefuncion() {
		return this.FDataobjectbasefuncion;
	}

	public void setFDataobjectbasefuncion(FDataobjectbasefuncion FDataobjectbasefuncion) {
		this.FDataobjectbasefuncion = FDataobjectbasefuncion;
	}

	@Column(name = "functionname", length = 50)

	public String getFunctionname() {
		return this.functionname;
	}

	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}

	@Column(name = "functiontitle", length = 50)

	public String getFunctiontitle() {
		return this.functiontitle;
	}

	public void setFunctiontitle(String functiontitle) {
		this.functiontitle = functiontitle;
	}

	@Column(name = "description", length = 200)

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Column(name = "isvalid", nullable = false)

	public Boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	@Column(name = "orderno")

	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FModulefunction")

	public Set<FUserfunctionlimit> getFUserfunctionlimits() {
		return this.FUserfunctionlimits;
	}

	public void setFUserfunctionlimits(Set<FUserfunctionlimit> FUserfunctionlimits) {
		this.FUserfunctionlimits = FUserfunctionlimits;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FModulefunction")

	public Set<FRolefunctionlimit> getFRolefunctionlimits() {
		return this.FRolefunctionlimits;
	}

	public void setFRolefunctionlimits(Set<FRolefunctionlimit> FRolefunctionlimits) {
		this.FRolefunctionlimits = FRolefunctionlimits;
	}

}
