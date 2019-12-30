package net.ebaolife.husqvarna.framework.dao.entity.datainorout;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
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
@Table(name = "f_recordprintscheme", uniqueConstraints = @UniqueConstraint(columnNames = { "objectid", "title" }))
public class FRecordprintscheme implements java.io.Serializable {

	private String schemeid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject FDataobject;
	private String title;
	private Integer orderno;
	private Boolean issystem;
	private Boolean issub;
	private String iconurl;
	private String iconcls;
	private Boolean isdisable;
	private String othersetting;
	private Set<FRecordprintschemegroup> FRecordprintschemegroups = new HashSet<FRecordprintschemegroup>(0);

	public FRecordprintscheme() {
	}

	public FRecordprintscheme(String schemeid, FDataobject FDataobject, String title) {
		this.schemeid = schemeid;
		this.FDataobject = FDataobject;
		this.title = title;
	}

	public FRecordprintscheme(String schemeid, FDataobject FDataobject, String title, Integer orderno, Boolean issystem,
			Boolean issub, String iconurl, String iconcls, Boolean isdisable, String othersetting,
			Set<FRecordprintschemegroup> FRecordprintschemegroups) {
		this.schemeid = schemeid;
		this.FDataobject = FDataobject;
		this.title = title;
		this.orderno = orderno;
		this.issystem = issystem;
		this.issub = issub;
		this.iconurl = iconurl;
		this.iconcls = iconcls;
		this.isdisable = isdisable;
		this.othersetting = othersetting;
		this.FRecordprintschemegroups = FRecordprintschemegroups;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "schemeid", unique = true, nullable = false, length = 40)
	public String getSchemeid() {
		return this.schemeid;
	}

	public void setSchemeid(String schemeid) {
		this.schemeid = schemeid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objectid", nullable = false)
	public FDataobject getFDataobject() {
		return this.FDataobject;
	}

	public void setFDataobject(FDataobject FDataobject) {
		this.FDataobject = FDataobject;
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

	@Column(name = "issystem")
	public Boolean getIssystem() {
		return this.issystem;
	}

	public void setIssystem(Boolean issystem) {
		this.issystem = issystem;
	}

	@Column(name = "issub")
	public Boolean getIssub() {
		return this.issub;
	}

	public void setIssub(Boolean issub) {
		this.issub = issub;
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

	@Column(name = "isdisable")
	public Boolean getIsdisable() {
		return this.isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	@Column(name = "othersetting", length = 200)
	public String getOthersetting() {
		return this.othersetting;
	}

	public void setOthersetting(String othersetting) {
		this.othersetting = othersetting;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FRecordprintscheme")
	public Set<FRecordprintschemegroup> getFRecordprintschemegroups() {
		return this.FRecordprintschemegroups;
	}

	public void setFRecordprintschemegroups(Set<FRecordprintschemegroup> FRecordprintschemegroups) {
		this.FRecordprintschemegroups = FRecordprintschemegroups;
	}

}
