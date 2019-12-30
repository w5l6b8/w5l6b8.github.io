package net.ebaolife.husqvarna.framework.dao.entity.datainorout;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

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
@Table(name = "f_recordprintschemegroupcell")
public class FRecordprintschemegroupcell implements java.io.Serializable {

	private String cellid;
	private FRecordprintschemegroup FRecordprintschemegroup;
	private int orderno;
	private String title;
	private String printtext;
	private Integer cheight;
	private Integer cwidth;
	private Integer colspan;
	private Integer rowspan;
	private String halign;
	private String valign;
	private String cssstyle;
	private Boolean isdisable;
	private String othersetting;

	public FRecordprintschemegroupcell() {
	}

	public FRecordprintschemegroupcell(String cellid, FRecordprintschemegroup FRecordprintschemegroup, int orderno,
			String title) {
		this.cellid = cellid;
		this.FRecordprintschemegroup = FRecordprintschemegroup;
		this.orderno = orderno;
		this.title = title;
	}

	public FRecordprintschemegroupcell(String cellid, FRecordprintschemegroup FRecordprintschemegroup, int orderno,
			String title, String printtext, Integer cheight, Integer cwidth, Integer colspan, Integer rowspan,
			String halign, String valign, String cssstyle, Boolean isdisable, String othersetting) {
		this.cellid = cellid;
		this.FRecordprintschemegroup = FRecordprintschemegroup;
		this.orderno = orderno;
		this.title = title;
		this.printtext = printtext;
		this.cheight = cheight;
		this.cwidth = cwidth;
		this.colspan = colspan;
		this.rowspan = rowspan;
		this.halign = halign;
		this.valign = valign;
		this.cssstyle = cssstyle;
		this.isdisable = isdisable;
		this.othersetting = othersetting;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "cellid", unique = true, nullable = false, length = 40)
	public String getCellid() {
		return this.cellid;
	}

	public void setCellid(String cellid) {
		this.cellid = cellid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupid", nullable = false)
	public FRecordprintschemegroup getFRecordprintschemegroup() {
		return this.FRecordprintschemegroup;
	}

	public void setFRecordprintschemegroup(FRecordprintschemegroup FRecordprintschemegroup) {
		this.FRecordprintschemegroup = FRecordprintschemegroup;
	}

	@Column(name = "orderno", nullable = false)
	public int getOrderno() {
		return this.orderno;
	}

	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}

	@Column(name = "title", nullable = false, length = 200)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "printtext", length = 200)
	public String getPrinttext() {
		return this.printtext;
	}

	public void setPrinttext(String printtext) {
		this.printtext = printtext;
	}

	@Column(name = "cheight")
	public Integer getCheight() {
		return this.cheight;
	}

	public void setCheight(Integer cheight) {
		this.cheight = cheight;
	}

	@Column(name = "cwidth")
	public Integer getCwidth() {
		return this.cwidth;
	}

	public void setCwidth(Integer cwidth) {
		this.cwidth = cwidth;
	}

	@Column(name = "colspan")
	public Integer getColspan() {
		return this.colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	@Column(name = "rowspan")
	public Integer getRowspan() {
		return this.rowspan;
	}

	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}

	@Column(name = "halign", length = 20)
	public String getHalign() {
		return this.halign;
	}

	public void setHalign(String halign) {
		this.halign = halign;
	}

	@Column(name = "valign", length = 20)
	public String getValign() {
		return this.valign;
	}

	public void setValign(String valign) {
		this.valign = valign;
	}

	@Column(name = "cssstyle", length = 100)
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

}
