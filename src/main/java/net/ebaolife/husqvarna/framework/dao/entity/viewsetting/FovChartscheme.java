package net.ebaolife.husqvarna.framework.dao.entity.viewsetting;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
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
@Table(name = "fov_chartscheme", uniqueConstraints = @UniqueConstraint(columnNames = { "objectid", "userid", "title" }))
public class FovChartscheme implements java.io.Serializable {

	private String chartschemeid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject FDataobject;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FUser FUser;
	private Integer orderno;
	private String title;
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

	public FovChartscheme() {
	}

	public FovChartscheme(String chartschemeid) {
		this.chartschemeid = chartschemeid;
	}

	public FovChartscheme(String chartschemeid, FDataobject FDataobject, FUser FUser, Integer orderno, String title,
			String charttype, String colorscheme, String categoryfield, String numericfields,
			Boolean isshowdetailnumber, Boolean isshowtips, Boolean isanimate, Boolean isgridline, String othersetting,
			String remark) {
		this.chartschemeid = chartschemeid;
		this.FDataobject = FDataobject;
		this.FUser = FUser;
		this.orderno = orderno;
		this.title = title;
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
	@Column(name = "chartschemeid", unique = true, nullable = false, length = 40)
	public String getChartschemeid() {
		return this.chartschemeid;
	}

	public void setChartschemeid(String chartschemeid) {
		this.chartschemeid = chartschemeid;
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
	@JoinColumn(name = "userid")
	public FUser getFUser() {
		return this.FUser;
	}

	public void setFUser(FUser FUser) {
		this.FUser = FUser;
	}

	@Column(name = "orderno")
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "title", length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
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
