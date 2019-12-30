package net.ebaolife.husqvarna.framework.dao.entity.dataobject;

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
@Table(name = "f_dataobjectgroup")

public class FDataobjectgroup implements java.io.Serializable {

	private String objectgroupid;
	private FDataobjectgroup FDataobjectgroup;
	private String groupname;
	private Integer orderno;
	private String levelid;
	private Set<FDataobject> FDataobjects = new HashSet<FDataobject>(0);
	private Set<FDataobjectgroup> FDataobjectgroups = new HashSet<FDataobjectgroup>(0);

	public FDataobjectgroup() {
	}

	public FDataobjectgroup(String groupname, Integer orderno, FDataobjectgroup FDataobjectgroup) {
		this.groupname = groupname;
		this.orderno = orderno;
		this.FDataobjectgroup = FDataobjectgroup;
	}

	public FDataobjectgroup(String objectgroupid, FDataobjectgroup FDataobjectgroup, String groupname, Integer orderno,
			String levelid, Set<FDataobject> FDataobjects, Set<FDataobjectgroup> FDataobjectgroups) {
		this.objectgroupid = objectgroupid;
		this.FDataobjectgroup = FDataobjectgroup;
		this.groupname = groupname;
		this.orderno = orderno;
		this.levelid = levelid;
		this.FDataobjects = FDataobjects;
		this.FDataobjectgroups = FDataobjectgroups;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "objectgroupid", unique = true, nullable = false, length = 40)
	public String getObjectgroupid() {
		return this.objectgroupid;
	}

	public void setObjectgroupid(String objectgroupid) {
		this.objectgroupid = objectgroupid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public FDataobjectgroup getFDataobjectgroup() {
		return this.FDataobjectgroup;
	}

	public void setFDataobjectgroup(FDataobjectgroup FDataobjectgroup) {
		this.FDataobjectgroup = FDataobjectgroup;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobjectgroup")
	public Set<FDataobject> getFDataobjects() {
		return this.FDataobjects;
	}

	public void setFDataobjects(Set<FDataobject> FDataobjects) {
		this.FDataobjects = FDataobjects;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobjectgroup")
	public Set<FDataobjectgroup> getFDataobjectgroups() {
		return this.FDataobjectgroups;
	}

	public void setFDataobjectgroups(Set<FDataobjectgroup> FDataobjectgroups) {
		this.FDataobjectgroups = FDataobjectgroups;
	}

}