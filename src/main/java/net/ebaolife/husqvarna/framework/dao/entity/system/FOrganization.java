package net.ebaolife.husqvarna.framework.dao.entity.system;

import net.ebaolife.husqvarna.framework.dao.entity.usershare.FGridschemeshare;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Entity
@DynamicUpdate
@Table(name = "f_organization", uniqueConstraints = @UniqueConstraint(columnNames = {
		"companyid", "orgcode" }) )
@SuppressWarnings("serial")

public class FOrganization implements java.io.Serializable {

	

	private String orgid;
	private FCompany FCompany;
	private FOrganization FOrganization;
	private String orgcode;
	private String orgname;
	private String levelid;
	private Boolean isvalid;
	private Integer orderno;
	private String creater;
	private Timestamp createdate;
	private String lastmodifier;
	private Timestamp lastmodifydate;
	private Set<FPersonnel> FPersonnels = new HashSet<FPersonnel>(0);
	private Set<FOrganization> FOrganizations = new HashSet<FOrganization>(0);
	private Set<FGridschemeshare> FGridschemeshares = new HashSet<FGridschemeshare>(0);

	public FOrganization() {
	}

	public FOrganization(FCompany FCompany, String orgcode, String orgname, String levelid, Boolean isvalid,
			String creater, Timestamp createdate) {
		this.FCompany = FCompany;
		this.orgcode = orgcode;
		this.orgname = orgname;
		this.levelid = levelid;
		this.isvalid = isvalid;
		this.creater = creater;
		this.createdate = createdate;
	}

	public FOrganization(FCompany FCompany, FOrganization FOrganization, String orgcode, String orgname, String levelid,
			Boolean isvalid, Integer orderno, String creater, Timestamp createdate, String lastmodifier,
			Timestamp lastmodifydate, Set<FPersonnel> FPersonnels, Set<FOrganization> FOrganizations,
			Set<FGridschemeshare> FGridschemeshares) {
		this.FCompany = FCompany;
		this.FOrganization = FOrganization;
		this.orgcode = orgcode;
		this.orgname = orgname;
		this.levelid = levelid;
		this.isvalid = isvalid;
		this.orderno = orderno;
		this.creater = creater;
		this.createdate = createdate;
		this.lastmodifier = lastmodifier;
		this.lastmodifydate = lastmodifydate;
		this.FPersonnels = FPersonnels;
		this.FOrganizations = FOrganizations;
		this.FGridschemeshares = FGridschemeshares;
	}

	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "orgid", unique = true, nullable = false, length = 40)

	public String getOrgid() {
		return this.orgid;
	}

	public void setOrgid(String orgid) {
		this.orgid = orgid;
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
	@JoinColumn(name = "parentid")

	public FOrganization getFOrganization() {
		return this.FOrganization;
	}

	public void setFOrganization(FOrganization FOrganization) {
		this.FOrganization = FOrganization;
	}

	@Column(name = "orgcode", nullable = false, length = 40)

	public String getOrgcode() {
		return this.orgcode;
	}

	public void setOrgcode(String orgcode) {
		this.orgcode = orgcode;
	}

	@Column(name = "orgname", nullable = false, length = 100)

	public String getOrgname() {
		return this.orgname;
	}

	public void setOrgname(String orgname) {
		this.orgname = orgname;
	}

	@Column(name = "levelid", nullable = false, length = 100)

	public String getLevelid() {
		return this.levelid;
	}

	public void setLevelid(String levelid) {
		this.levelid = levelid;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOrganization")

	public Set<FPersonnel> getFPersonnels() {
		return this.FPersonnels;
	}

	public void setFPersonnels(Set<FPersonnel> FPersonnels) {
		this.FPersonnels = FPersonnels;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOrganization")

	public Set<FOrganization> getFOrganizations() {
		return this.FOrganizations;
	}

	public void setFOrganizations(Set<FOrganization> FOrganizations) {
		this.FOrganizations = FOrganizations;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FOrganization")

	public Set<FGridschemeshare> getFGridschemeshares() {
		return this.FGridschemeshares;
	}

	public void setFGridschemeshares(Set<FGridschemeshare> FGridschemeshares) {
		this.FGridschemeshares = FGridschemeshares;
	}

}