package net.ebaolife.husqvarna.framework.dao.entity.groupquery;

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
@Table(name = "f_groupquerychart")
public class FGroupquerychart implements java.io.Serializable {

	private String querychartid;
	private FGroupquery FGroupquery;
	private String title;
	private Integer orderno;
	private String charttype;
	private String colorscheme;
	private String categoryfield;
	private String numericfields;
	private Boolean isshowdetailnumber;
	private Boolean isshowtips;
	private Boolean isanimate;
	private Boolean isgridline;
	private String othersetting;
	private String remark;

	public FGroupquerychart() {
	}

	public FGroupquerychart(String querychartid) {
		this.querychartid = querychartid;
	}

	public FGroupquerychart(String querychartid, FGroupquery FGroupquery, String title, Integer orderno,
			String charttype, String colorscheme, String categoryfield, String numericfields,
			Boolean isshowdetailnumber, Boolean isshowtips, Boolean isanimate, Boolean isgridline, String othersetting,
			String remark) {
		this.querychartid = querychartid;
		this.FGroupquery = FGroupquery;
		this.title = title;
		this.orderno = orderno;
		this.charttype = charttype;
		this.colorscheme = colorscheme;
		this.categoryfield = categoryfield;
		this.numericfields = numericfields;
		this.isshowdetailnumber = isshowdetailnumber;
		this.isshowtips = isshowtips;
		this.isanimate = isanimate;
		this.isgridline = isgridline;
		this.othersetting = othersetting;
		this.remark = remark;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "querychartid", unique = true, nullable = false, length = 40)
	public String getQuerychartid() {
		return this.querychartid;
	}

	public void setQuerychartid(String querychartid) {
		this.querychartid = querychartid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "queryid")
	public FGroupquery getFGroupquery() {
		return this.FGroupquery;
	}

	public void setFGroupquery(FGroupquery FGroupquery) {
		this.FGroupquery = FGroupquery;
	}

	@Column(name = "title", length = 50)
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

	@Column(name = "charttype", length = 50)
	public String getCharttype() {
		return this.charttype;
	}

	public void setCharttype(String charttype) {
		this.charttype = charttype;
	}

	@Column(name = "colorscheme", length = 40)
	public String getColorscheme() {
		return this.colorscheme;
	}

	public void setColorscheme(String colorscheme) {
		this.colorscheme = colorscheme;
	}

	@Column(name = "categoryfield", length = 50)
	public String getCategoryfield() {
		return this.categoryfield;
	}

	public void setCategoryfield(String categoryfield) {
		this.categoryfield = categoryfield;
	}

	@Column(name = "numericfields", length = 200)
	public String getNumericfields() {
		return this.numericfields;
	}

	public void setNumericfields(String numericfields) {
		this.numericfields = numericfields;
	}

	@Column(name = "isshowdetailnumber")
	public Boolean getIsshowdetailnumber() {
		return this.isshowdetailnumber;
	}

	public void setIsshowdetailnumber(Boolean isshowdetailnumber) {
		this.isshowdetailnumber = isshowdetailnumber;
	}

	@Column(name = "isshowtips")
	public Boolean getIsshowtips() {
		return this.isshowtips;
	}

	public void setIsshowtips(Boolean isshowtips) {
		this.isshowtips = isshowtips;
	}

	@Column(name = "isanimate")
	public Boolean getIsanimate() {
		return this.isanimate;
	}

	public void setIsanimate(Boolean isanimate) {
		this.isanimate = isanimate;
	}

	@Column(name = "isgridline")
	public Boolean getIsgridline() {
		return this.isgridline;
	}

	public void setIsgridline(Boolean isgridline) {
		this.isgridline = isgridline;
	}

	@Column(name = "othersetting", length = 200)
	public String getOthersetting() {
		return this.othersetting;
	}

	public void setOthersetting(String othersetting) {
		this.othersetting = othersetting;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
