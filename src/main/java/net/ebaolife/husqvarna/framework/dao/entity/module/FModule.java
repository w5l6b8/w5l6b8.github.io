package net.ebaolife.husqvarna.framework.dao.entity.module;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
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
@Table(name = "f_module", uniqueConstraints = @UniqueConstraint(columnNames = "modulecode"))

public class FModule implements java.io.Serializable {

	private String moduleid;
	private FDataobject FDataobject;
	private FModulegroup FModulegroup;
	private String modulecode;
	private String modulename;
	private String moduletype;
	private String modulesource;
	private Integer orderno;
	private Boolean isvalid;
	private String creater;
	private Timestamp createdate;
	private String lastmodifier;
	private Timestamp lastmodifydate;
	private Set<FCompanymodule> FCompanymodules = new HashSet<FCompanymodule>(0);

	public FModule() {
	}

	public FModule(FModulegroup FModulegroup, String modulecode, String modulename, Boolean isvalid, String creater,
			Timestamp createdate) {
		this.FModulegroup = FModulegroup;
		this.modulecode = modulecode;
		this.modulename = modulename;
		this.isvalid = isvalid;
		this.creater = creater;
		this.createdate = createdate;
	}

	public FModule(FDataobject FDataobject, FModulegroup FModulegroup, String modulecode, String modulename,
			String moduletype, String modulesource, Integer orderno, Boolean isvalid, String creater,
			Timestamp createdate, String lastmodifier, Timestamp lastmodifydate, Set<FCompanymodule> FCompanymodules) {
		this.FDataobject = FDataobject;
		this.FModulegroup = FModulegroup;
		this.modulecode = modulecode;
		this.modulename = modulename;
		this.moduletype = moduletype;
		this.modulesource = modulesource;
		this.orderno = orderno;
		this.isvalid = isvalid;
		this.creater = creater;
		this.createdate = createdate;
		this.lastmodifier = lastmodifier;
		this.lastmodifydate = lastmodifydate;
		this.FCompanymodules = FCompanymodules;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "moduleid", unique = true, nullable = false, length = 40)

	public String getModuleid() {
		return this.moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
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
	@JoinColumn(name = "modulegroupid", nullable = false)

	public FModulegroup getFModulegroup() {
		return this.FModulegroup;
	}

	public void setFModulegroup(FModulegroup FModulegroup) {
		this.FModulegroup = FModulegroup;
	}

	@Column(name = "modulecode", unique = true, nullable = false, length = 50)

	public String getModulecode() {
		return this.modulecode;
	}

	public void setModulecode(String modulecode) {
		this.modulecode = modulecode;
	}

	@Column(name = "modulename", nullable = false, length = 50)

	public String getModulename() {
		return this.modulename;
	}

	public void setModulename(String modulename) {
		this.modulename = modulename;
	}

	@Column(name = "moduletype", length = 2)

	public String getModuletype() {
		return this.moduletype;
	}

	public void setModuletype(String moduletype) {
		this.moduletype = moduletype;
	}

	@Column(name = "modulesource", length = 300)

	public String getModulesource() {
		return this.modulesource;
	}

	public void setModulesource(String modulesource) {
		this.modulesource = modulesource;
	}

	@Column(name = "orderno")

	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FModule")

	public Set<FCompanymodule> getFCompanymodules() {
		return this.FCompanymodules;
	}

	public void setFCompanymodules(Set<FCompanymodule> FCompanymodules) {
		this.FCompanymodules = FCompanymodules;
	}

}
