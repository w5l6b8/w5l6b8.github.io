package net.ebaolife.husqvarna.framework.dao.entity.limit;

import net.ebaolife.husqvarna.framework.dao.entity.system.FCompany;
import net.ebaolife.husqvarna.framework.dao.entity.usershare.FGridschemeshare;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name = "f_role", uniqueConstraints = { @UniqueConstraint(columnNames = { "companyid", "rolecode" }),
		@UniqueConstraint(columnNames = { "companyid", "rolename" }) })

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
public class FRole implements java.io.Serializable {

	private String roleid;
	private String rolecode;
	private String rolename;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FCompany FCompany;
	private Boolean isvalid;
	private String creater;
	private Timestamp createdate;
	private String lastmodifier;
	private Timestamp lastmodifydate;
	private Set<FGridschemeshare> FGridschemeshares = new HashSet<FGridschemeshare>(0);
	private Set<FRolefieldlimit> FRolefieldlimits = new HashSet<FRolefieldlimit>(0);
	private Set<FUserrole> FUserroles = new HashSet<FUserrole>(0);
	private Set<FRolefunctionlimit> FRolefunctionlimits = new HashSet<FRolefunctionlimit>(0);

	public FRole() {
	}

	public FRole(String rolecode, String rolename, FCompany FCompany, Boolean isvalid, String creater,
			Timestamp createdate, String lastmodifier, Timestamp lastmodifydate,
			Set<FGridschemeshare> FGridschemeshares, Set<FRolefieldlimit> FRolefieldlimits, Set<FUserrole> FUserroles,
			Set<FRolefunctionlimit> FRolefunctionlimits) {
		this.rolecode = rolecode;
		this.rolename = rolename;
		this.FCompany = FCompany;
		this.isvalid = isvalid;
		this.creater = creater;
		this.createdate = createdate;
		this.lastmodifier = lastmodifier;
		this.lastmodifydate = lastmodifydate;
		this.FGridschemeshares = FGridschemeshares;
		this.FRolefieldlimits = FRolefieldlimits;
		this.FUserroles = FUserroles;
		this.FRolefunctionlimits = FRolefunctionlimits;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "roleid", unique = true, nullable = false, length = 40)

	public String getRoleid() {
		return this.roleid;
	}

	public void setRoleid(String roleid) {
		this.roleid = roleid;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "companyid", nullable = false)
	public FCompany getFCompany() {
		return this.FCompany;
	}

	public void setFCompany(FCompany FCompany) {
		this.FCompany = FCompany;
	}

	@Column(name = "isvalid", nullable = false)

	public Boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FRole")

	public Set<FGridschemeshare> getFGridschemeshares() {
		return this.FGridschemeshares;
	}

	public void setFGridschemeshares(Set<FGridschemeshare> FGridschemeshares) {
		this.FGridschemeshares = FGridschemeshares;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FRole")

	public Set<FRolefieldlimit> getFRolefieldlimits() {
		return this.FRolefieldlimits;
	}

	public void setFRolefieldlimits(Set<FRolefieldlimit> FRolefieldlimits) {
		this.FRolefieldlimits = FRolefieldlimits;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FRole")

	public Set<FUserrole> getFUserroles() {
		return this.FUserroles;
	}

	public void setFUserroles(Set<FUserrole> FUserroles) {
		this.FUserroles = FUserroles;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FRole")

	public Set<FRolefunctionlimit> getFRolefunctionlimits() {
		return this.FRolefunctionlimits;
	}

	public void setFRolefunctionlimits(Set<FRolefunctionlimit> FRolefunctionlimits) {
		this.FRolefunctionlimits = FRolefunctionlimits;
	}

}
