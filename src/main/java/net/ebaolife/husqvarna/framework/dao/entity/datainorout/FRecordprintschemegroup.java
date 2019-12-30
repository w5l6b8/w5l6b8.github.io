package net.ebaolife.husqvarna.framework.dao.entity.datainorout;

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
@Table(name = "f_recordprintschemegroup")
public class FRecordprintschemegroup implements java.io.Serializable {

	private String groupid;
	private FRecordprintscheme FRecordprintscheme;
	private int orderno;
	private String title;
	private Integer gwidth;
	private Integer gcols;
	private String gwidths;
	private Integer borderwidth;
	private String cellpadding;
	private String cssstyle;
	private Boolean isdisable;
	private String othersetting;
	private Set<FRecordprintschemegroupcell> FRecordprintschemegroupcells = new HashSet<FRecordprintschemegroupcell>(0);

	public FRecordprintschemegroup() {
	}

	public FRecordprintschemegroup(String groupid, FRecordprintscheme FRecordprintscheme, int orderno, String title) {
		this.groupid = groupid;
		this.FRecordprintscheme = FRecordprintscheme;
		this.orderno = orderno;
		this.title = title;
	}

	public FRecordprintschemegroup(String groupid, FRecordprintscheme FRecordprintscheme, int orderno, String title,
			Integer gwidth, Integer gcols, String gwidths, Integer borderwidth, String cellpadding, String cssstyle,
			Boolean isdisable, String othersetting, Set<FRecordprintschemegroupcell> FRecordprintschemegroupcells) {
		this.groupid = groupid;
		this.FRecordprintscheme = FRecordprintscheme;
		this.orderno = orderno;
		this.title = title;
		this.gwidth = gwidth;
		this.gcols = gcols;
		this.gwidths = gwidths;
		this.borderwidth = borderwidth;
		this.cellpadding = cellpadding;
		this.cssstyle = cssstyle;
		this.isdisable = isdisable;
		this.othersetting = othersetting;
		this.FRecordprintschemegroupcells = FRecordprintschemegroupcells;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "groupid", unique = true, nullable = false, length = 40)
	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schemeid", nullable = false)
	public FRecordprintscheme getFRecordprintscheme() {
		return this.FRecordprintscheme;
	}

	public void setFRecordprintscheme(FRecordprintscheme FRecordprintscheme) {
		this.FRecordprintscheme = FRecordprintscheme;
	}

	@Column(name = "orderno", nullable = false)
	public int getOrderno() {
		return this.orderno;
	}

	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "gwidth")
	public Integer getGwidth() {
		return this.gwidth;
	}

	public void setGwidth(Integer gwidth) {
		this.gwidth = gwidth;
	}

	@Column(name = "gcols")
	public Integer getGcols() {
		return this.gcols;
	}

	public void setGcols(Integer gcols) {
		this.gcols = gcols;
	}

	@Column(name = "gwidths", length = 200)
	public String getGwidths() {
		return this.gwidths;
	}

	public void setGwidths(String gwidths) {
		this.gwidths = gwidths;
	}

	@Column(name = "borderwidth")
	public Integer getBorderwidth() {
		return this.borderwidth;
	}

	public void setBorderwidth(Integer borderwidth) {
		this.borderwidth = borderwidth;
	}

	@Column(name = "cellpadding", length = 50)
	public String getCellpadding() {
		return this.cellpadding;
	}

	public void setCellpadding(String cellpadding) {
		this.cellpadding = cellpadding;
	}

	@Column(name = "cssstyle", length = 50)
	public String getCssstyle() {
		return this.cssstyle;
	}

	public void setCssstyle(String cssstyle) {
		this.cssstyle = cssstyle;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FRecordprintschemegroup")
	public Set<FRecordprintschemegroupcell> getFRecordprintschemegroupcells() {
		return this.FRecordprintschemegroupcells;
	}

	public void setFRecordprintschemegroupcells(Set<FRecordprintschemegroupcell> FRecordprintschemegroupcells) {
		this.FRecordprintschemegroupcells = FRecordprintschemegroupcells;
	}

}
