package net.ebaolife.husqvarna.framework.dao.entity.dictionary;

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
@SuppressWarnings("serial")

@DynamicUpdate
@Table(name = "f_dictionarygroup", uniqueConstraints = { @UniqueConstraint(columnNames = "groupcode"),
		@UniqueConstraint(columnNames = "groupname") })
public class FDictionarygroup implements java.io.Serializable {

	private String groupid;
	private String groupcode;
	private String groupname;
	private Boolean issystem;
	private String iconcls;
	private String remark;
	private Set<FDictionary> FDictionaries = new HashSet<FDictionary>(0);

	public FDictionarygroup() {
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "groupid", unique = true, nullable = false, length = 40)
	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	@Column(name = "groupcode", unique = true, nullable = false, length = 20)
	public String getGroupcode() {
		return this.groupcode;
	}

	public void setGroupcode(String groupcode) {
		this.groupcode = groupcode;
	}

	@Column(name = "groupname", unique = true, nullable = false, length = 50)
	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Column(name = "issystem")
	public Boolean getIssystem() {
		return this.issystem;
	}

	public void setIssystem(Boolean issystem) {
		this.issystem = issystem;
	}

	@Column(name = "iconcls", length = 50)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDictionarygroup")
	public Set<FDictionary> getFDictionaries() {
		return this.FDictionaries;
	}

	public void setFDictionaries(Set<FDictionary> FDictionaries) {
		this.FDictionaries = FDictionaries;
	}

}
