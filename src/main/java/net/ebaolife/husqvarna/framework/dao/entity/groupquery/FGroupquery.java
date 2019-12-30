package net.ebaolife.husqvarna.framework.dao.entity.groupquery;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.system.FPersonnel;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
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
@Table(name = "f_groupquery")
public class FGroupquery implements java.io.Serializable {

	private String queryid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject FDataobject;
	private FGroupquerygroup FGroupquerygroup;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FPersonnel FPersonnel;
	private String title;
	private Integer orderno;
	private String qtype;
	private Boolean isdisable;
	private Boolean isshowtotal;
	private Boolean isbrowsemode;
	private Boolean istreegrid;
	private Boolean isdisplaydetailinsubtotal;
	private String remark;
	private Boolean isdisablesubtotal;
	private Boolean isdisablechart;
	private String iconurl;
	private String iconcls;
	private Boolean[] iconfile;
	private String userid;
	private Boolean isshare;
	private Boolean isshareowner;
	private Set<FGroupquerychart> FGroupquerycharts = new HashSet<FGroupquerychart>(0);
	private Set<FGroupqueryorder> FGroupqueryorders = new HashSet<FGroupqueryorder>(0);
	private Set<FGroupquerygroupinfo> FGroupquerygroupinfos = new HashSet<FGroupquerygroupinfo>(0);
	private List<FGroupquerycolumn> FGroupquerycolumns;

	public FGroupquery() {
	}

	public FGroupquery(String queryid, FDataobject FDataobject, FGroupquerygroup FGroupquerygroup, String title,
			String userid) {
		this.queryid = queryid;
		this.FDataobject = FDataobject;
		this.FGroupquerygroup = FGroupquerygroup;
		this.title = title;
		this.userid = userid;
	}

	public FGroupquery(String queryid, FDataobject FDataobject, FGroupquerygroup FGroupquerygroup,
			FPersonnel FPersonnel, String title, Integer orderno, String qtype, Boolean isdisable, Boolean isshowtotal,
			Boolean isbrowsemode, Boolean istreegrid, Boolean isdisplaydetailinsubtotal, String remark,
			Boolean isdisablesubtotal, Boolean isdisablechart, String iconurl, String iconcls, Boolean[] iconfile,
			String userid, Boolean isshare, Boolean isshareowner, Set<FGroupquerychart> FGroupquerycharts,
			Set<FGroupqueryorder> FGroupqueryorders, Set<FGroupquerygroupinfo> FGroupquerygroupinfos,
			List<FGroupquerycolumn> FGroupquerycolumns) {
		this.queryid = queryid;
		this.FDataobject = FDataobject;
		this.FGroupquerygroup = FGroupquerygroup;
		this.FPersonnel = FPersonnel;
		this.title = title;
		this.orderno = orderno;
		this.qtype = qtype;
		this.isdisable = isdisable;
		this.isshowtotal = isshowtotal;
		this.isbrowsemode = isbrowsemode;
		this.istreegrid = istreegrid;
		this.isdisplaydetailinsubtotal = isdisplaydetailinsubtotal;
		this.remark = remark;
		this.isdisablesubtotal = isdisablesubtotal;
		this.isdisablechart = isdisablechart;
		this.iconurl = iconurl;
		this.iconcls = iconcls;
		this.iconfile = iconfile;
		this.userid = userid;
		this.isshare = isshare;
		this.isshareowner = isshareowner;
		this.FGroupquerycharts = FGroupquerycharts;
		this.FGroupqueryorders = FGroupqueryorders;
		this.FGroupquerygroupinfos = FGroupquerygroupinfos;
		this.FGroupquerycolumns = FGroupquerycolumns;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "queryid", unique = true, nullable = false, length = 40)
	public String getQueryid() {
		return this.queryid;
	}

	public void setQueryid(String queryid) {
		this.queryid = queryid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objectid", nullable = false)
	public FDataobject getFDataobject() {
		return this.FDataobject;
	}

	public void setFDataobject(FDataobject FDataobject) {
		this.FDataobject = FDataobject;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupid", nullable = false)
	public FGroupquerygroup getFGroupquerygroup() {
		return this.FGroupquerygroup;
	}

	public void setFGroupquerygroup(FGroupquerygroup FGroupquerygroup) {
		this.FGroupquerygroup = FGroupquerygroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personnelid")
	public FPersonnel getFPersonnel() {
		return this.FPersonnel;
	}

	public void setFPersonnel(FPersonnel FPersonnel) {
		this.FPersonnel = FPersonnel;
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

	@Column(name = "qtype", length = 20)
	public String getQtype() {
		return this.qtype;
	}

	public void setQtype(String qtype) {
		this.qtype = qtype;
	}

	@Column(name = "isdisable")
	public Boolean getIsdisable() {
		return this.isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	@Column(name = "isshowtotal")
	public Boolean getIsshowtotal() {
		return this.isshowtotal;
	}

	public void setIsshowtotal(Boolean isshowtotal) {
		this.isshowtotal = isshowtotal;
	}

	@Column(name = "isbrowsemode")
	public Boolean getIsbrowsemode() {
		return this.isbrowsemode;
	}

	public void setIsbrowsemode(Boolean isbrowsemode) {
		this.isbrowsemode = isbrowsemode;
	}

	@Column(name = "istreegrid")
	public Boolean getIstreegrid() {
		return this.istreegrid;
	}

	public void setIstreegrid(Boolean istreegrid) {
		this.istreegrid = istreegrid;
	}

	@Column(name = "isdisplaydetailinsubtotal")
	public Boolean getIsdisplaydetailinsubtotal() {
		return this.isdisplaydetailinsubtotal;
	}

	public void setIsdisplaydetailinsubtotal(Boolean isdisplaydetailinsubtotal) {
		this.isdisplaydetailinsubtotal = isdisplaydetailinsubtotal;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "isdisablesubtotal")
	public Boolean getIsdisablesubtotal() {
		return this.isdisablesubtotal;
	}

	public void setIsdisablesubtotal(Boolean isdisablesubtotal) {
		this.isdisablesubtotal = isdisablesubtotal;
	}

	@Column(name = "isdisablechart")
	public Boolean getIsdisablechart() {
		return this.isdisablechart;
	}

	public void setIsdisablechart(Boolean isdisablechart) {
		this.isdisablechart = isdisablechart;
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

	@Column(name = "userid", nullable = false, length = 40)
	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "isshare")
	public Boolean getIsshare() {
		return this.isshare;
	}

	public void setIsshare(Boolean isshare) {
		this.isshare = isshare;
	}

	@Column(name = "isshareowner")
	public Boolean getIsshareowner() {
		return this.isshareowner;
	}

	public void setIsshareowner(Boolean isshareowner) {
		this.isshareowner = isshareowner;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FGroupquery")
	public Set<FGroupquerychart> getFGroupquerycharts() {
		return this.FGroupquerycharts;
	}

	public void setFGroupquerycharts(Set<FGroupquerychart> FGroupquerycharts) {
		this.FGroupquerycharts = FGroupquerycharts;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FGroupquery")
	@OrderBy("orderno")
	public Set<FGroupqueryorder> getFGroupqueryorders() {
		return this.FGroupqueryorders;
	}

	public void setFGroupqueryorders(Set<FGroupqueryorder> FGroupqueryorders) {
		this.FGroupqueryorders = FGroupqueryorders;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FGroupquery")
	public Set<FGroupquerygroupinfo> getFGroupquerygroupinfos() {
		return this.FGroupquerygroupinfos;
	}

	public void setFGroupquerygroupinfos(Set<FGroupquerygroupinfo> FGroupquerygroupinfos) {
		this.FGroupquerygroupinfos = FGroupquerygroupinfos;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FGroupquery")
	@OrderBy("orderno")
	public List<FGroupquerycolumn> getFGroupquerycolumns() {
		return this.FGroupquerycolumns;
	}

	public void setFGroupquerycolumns(List<FGroupquerycolumn> FGroupquerycolumns) {
		this.FGroupquerycolumns = FGroupquerycolumns;
	}

}
