package net.ebaolife.husqvarna.framework.dao.entity.viewsetting;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@DynamicUpdate
@Table(name = "fov_formschemedetail")

public class FovFormschemedetail implements java.io.Serializable, ParentChildField {

	private static final long serialVersionUID = 8628775961544343142L;
	private String detailid;
	private FovFormscheme fovFormscheme;
	private FDataobject FDataobjectBySubobjectid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private FovFormschemedetail fovFormschemedetail;
	private FDataobjectcondition FDataobjectconditionBySubconditionid;
	private Integer orderno;
	private String xtype;
	private String region;
	private String layout;
	private String widths;
	private Integer cols;
	private Integer rowss;
	private Integer rowspan;
	private Integer colspan;
	private Boolean separatelabel;
	private Boolean collapsible;
	private Boolean collapsed;
	private String title;
	private String aggregate;
	private String fieldahead;
	private Integer width;
	private Integer height;
	private Boolean hiddenlabel;
	private Boolean isendrow;
	private String othersetting;
	private String remark;
	private List<FovFormschemedetail> details = new ArrayList<FovFormschemedetail>(0);

	private String subobjectid;
	private String fieldid;

	public FovFormschemedetail() {
	}

	public FovFormschemedetail(Integer orderno) {
		this.orderno = orderno;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "detailid", unique = true, nullable = false, length = 40)

	public String getDetailid() {
		return this.detailid;
	}

	public void setDetailid(String detailid) {
		this.detailid = detailid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "formschemeid")

	public FovFormscheme getFovFormscheme() {
		return this.fovFormscheme;
	}

	public void setFovFormscheme(FovFormscheme fovFormscheme) {
		this.fovFormscheme = fovFormscheme;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subobjectid")
	public FDataobject getFDataobjectBySubobjectid() {
		return this.FDataobjectBySubobjectid;
	}

	public void setFDataobjectBySubobjectid(FDataobject fDataobjectBySubobjectid) {
		this.FDataobjectBySubobjectid = fDataobjectBySubobjectid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fieldid")

	public FDataobjectfield getFDataobjectfield() {
		return this.FDataobjectfield;
	}

	public void setFDataobjectfield(FDataobjectfield FDataobjectfield) {
		this.FDataobjectfield = FDataobjectfield;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")

	public FovFormschemedetail getFovFormschemedetail() {
		return this.fovFormschemedetail;
	}

	public void setFovFormschemedetail(FovFormschemedetail fovFormschemedetail) {
		this.fovFormschemedetail = fovFormschemedetail;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subconditionid")
	public FDataobjectcondition getFDataobjectconditionBySubconditionid() {
		return this.FDataobjectconditionBySubconditionid;
	}

	public void setFDataobjectconditionBySubconditionid(FDataobjectcondition FDataobjectconditionBySubconditionid) {
		this.FDataobjectconditionBySubconditionid = FDataobjectconditionBySubconditionid;
	}

	@Column(name = "orderno", nullable = false)

	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "xtype", length = 50)

	public String getXtype() {
		return this.xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	@Column(name = "region", length = 20)
	public String getRegion() {
		return this.region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Column(name = "layout", length = 50)

	public String getLayout() {
		return this.layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Column(name = "cols")

	public Integer getCols() {
		return this.cols;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}

	@Column(name = "widths", length = 200)

	public String getWidths() {
		return this.widths;
	}

	public void setWidths(String widths) {
		this.widths = widths;
	}

	@Column(name = "separatelabel")

	public Boolean getSeparatelabel() {
		return this.separatelabel;
	}

	public void setSeparatelabel(Boolean separatelabel) {
		this.separatelabel = separatelabel;
	}

	@Column(name = "collapsible")

	public Boolean getCollapsible() {
		return this.collapsible;
	}

	public void setCollapsible(Boolean collapsible) {
		this.collapsible = collapsible;
	}

	@Column(name = "collapsed")

	public Boolean getCollapsed() {
		return this.collapsed;
	}

	public void setCollapsed(Boolean collapsed) {
		this.collapsed = collapsed;
	}

	@Column(name = "title", length = 200)

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "aggregate", length = 20)

	public String getAggregate() {
		return this.aggregate;
	}

	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}

	@Column(name = "fieldahead", length = 200)

	public String getFieldahead() {
		return this.fieldahead;
	}

	public void setFieldahead(String fieldahead) {
		this.fieldahead = fieldahead;
	}

	@Column(name = "width")

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name = "height")

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "rowss")

	public Integer getRowss() {
		return this.rowss;
	}

	public void setRowss(Integer rowss) {
		this.rowss = rowss;
	}

	@Column(name = "hiddenlabel")

	public Boolean getHiddenlabel() {
		return this.hiddenlabel;
	}

	public void setHiddenlabel(Boolean hiddenlabel) {
		this.hiddenlabel = hiddenlabel;
	}

	@Column(name = "isendrow")

	public Boolean getIsendrow() {
		return this.isendrow;
	}

	public void setIsendrow(Boolean isendrow) {
		this.isendrow = isendrow;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fovFormschemedetail")
	@OrderBy("orderno")
	public List<FovFormschemedetail> getDetails() {
		return this.details;
	}

	public void setDetails(List<FovFormschemedetail> details) {
		this.details = details;
	}

	@Column(updatable = false, insertable = false)
	public String getFieldid() {
		return fieldid;
	}

	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
	}

	@Column(updatable = false, insertable = false)
	public String getSubobjectid() {
		return subobjectid;
	}

	public void setSubobjectid(String subobjectid) {
		this.subobjectid = subobjectid;
	}

	@Column(name = "rowspan")
	public Integer getRowspan() {
		return rowspan;
	}

	public void setRowspan(Integer rowspan) {
		this.rowspan = rowspan;
	}

	@Column(name = "colspan")
	public Integer getColspan() {
		return colspan;
	}

	public void setColspan(Integer colspan) {
		this.colspan = colspan;
	}

	@Override
	@Transient
	public String getCondition() {

		return null;
	}

	@Override
	public void setCondition(String value) {

	}

}
