package net.ebaolife.husqvarna.framework.dao.entity.datamining;


import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
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
@Table(name = "f_dataminingexpandgroup")
public class FDataminingexpandgroup implements java.io.Serializable {

	private String expandgroupid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject FDataobject;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private int orderno;
	private String title;
	private String fieldahead;
	private String leveltype;
	private String iconcls;
	private Boolean ontoolbar;
	private Integer contextmenuorderno;
	private String tooltip;
	private Integer expandmaxrow;
	private Integer expandmaxcol;
	private Boolean disablecolumngroup;
	private Boolean disablerowgroup;
	private Boolean isnumbergroup;
	private Boolean isdategroup;
	private Boolean disabled;
	private String othersetting;
	private String remark;

	public FDataminingexpandgroup() {
	}

	public FDataminingexpandgroup(FDataobject FDataobject, FDataobjectfield FDataobjectfield, int orderno, String title,
			String fieldahead, String leveltype) {
		this.FDataobject = FDataobject;
		this.FDataobjectfield = FDataobjectfield;
		this.orderno = orderno;
		this.title = title;
		this.fieldahead = fieldahead;
		this.leveltype = leveltype;
	}

	public FDataminingexpandgroup(String expandgroupid, FDataobject FDataobject, FDataobjectfield FDataobjectfield,
			int orderno, String title, String fieldahead, String iconcls, Boolean ontoolbar, String tooltip,
			Integer expandmaxrow, Integer expandmaxcol, Boolean disablecolumngroup, Boolean disablerowgroup,
			Boolean isnumbergroup, Boolean isdategroup, String othersetting, String remark) {
		this.expandgroupid = expandgroupid;
		this.FDataobject = FDataobject;
		this.FDataobjectfield = FDataobjectfield;
		this.orderno = orderno;
		this.title = title;
		this.fieldahead = fieldahead;
		this.iconcls = iconcls;
		this.ontoolbar = ontoolbar;
		this.tooltip = tooltip;
		this.expandmaxrow = expandmaxrow;
		this.expandmaxcol = expandmaxcol;
		this.disablecolumngroup = disablecolumngroup;
		this.disablerowgroup = disablerowgroup;
		this.isnumbergroup = isnumbergroup;
		this.isdategroup = isdategroup;
		this.othersetting = othersetting;
		this.remark = remark;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "expandgroupid", unique = true, nullable = false, length = 40)
	public String getExpandgroupid() {
		return this.expandgroupid;
	}

	public void setExpandgroupid(String expandgroupid) {
		this.expandgroupid = expandgroupid;
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
	@JoinColumn(name = "fieldid", nullable = false)
	public FDataobjectfield getFDataobjectfield() {
		return this.FDataobjectfield;
	}

	public void setFDataobjectfield(FDataobjectfield FDataobjectfield) {
		this.FDataobjectfield = FDataobjectfield;
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

	@Column(name = "fieldahead", length = 200)
	public String getFieldahead() {
		return this.fieldahead;
	}

	public void setFieldahead(String fieldahead) {
		this.fieldahead = fieldahead;
	}

	@Column(name = "iconcls", length = 50)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "ontoolbar")
	public Boolean getOntoolbar() {
		return this.ontoolbar;
	}

	public void setOntoolbar(Boolean ontoolbar) {
		this.ontoolbar = ontoolbar;
	}

	@Column(name = "tooltip", length = 50)
	public String getTooltip() {
		return this.tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	@Column(name = "expandmaxrow")
	public Integer getExpandmaxrow() {
		return this.expandmaxrow;
	}

	public void setExpandmaxrow(Integer expandmaxrow) {
		this.expandmaxrow = expandmaxrow;
	}

	@Column(name = "expandmaxcol")
	public Integer getExpandmaxcol() {
		return this.expandmaxcol;
	}

	public void setExpandmaxcol(Integer expandmaxcol) {
		this.expandmaxcol = expandmaxcol;
	}

	@Column(name = "disablecolumngroup")
	public Boolean getDisablecolumngroup() {
		return this.disablecolumngroup;
	}

	public void setDisablecolumngroup(Boolean disablecolumngroup) {
		this.disablecolumngroup = disablecolumngroup;
	}

	@Column(name = "disablerowgroup")
	public Boolean getDisablerowgroup() {
		return this.disablerowgroup;
	}

	public void setDisablerowgroup(Boolean disablerowgroup) {
		this.disablerowgroup = disablerowgroup;
	}

	@Column(name = "isnumbergroup")
	public Boolean getIsnumbergroup() {
		return this.isnumbergroup;
	}

	public void setIsnumbergroup(Boolean isnumbergroup) {
		this.isnumbergroup = isnumbergroup;
	}

	@Column(name = "isdategroup")
	public Boolean getIsdategroup() {
		return this.isdategroup;
	}

	public void setIsdategroup(Boolean isdategroup) {
		this.isdategroup = isdategroup;
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

	public Boolean getDisabled() {
		return disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	public Integer getContextmenuorderno() {
		return contextmenuorderno;
	}

	public void setContextmenuorderno(Integer contextmenuorderno) {
		this.contextmenuorderno = contextmenuorderno;
	}

	public String getLeveltype() {
		return leveltype;
	}

	public void setLeveltype(String leveltype) {
		this.leveltype = leveltype;
	}

}
