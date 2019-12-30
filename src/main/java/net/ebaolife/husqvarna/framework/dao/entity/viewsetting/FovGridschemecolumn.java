package net.ebaolife.husqvarna.framework.dao.entity.viewsetting;


import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
import net.ebaolife.husqvarna.framework.utils.DataObjectFieldUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
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

@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name = "fov_gridschemecolumn")

public class FovGridschemecolumn implements java.io.Serializable, ParentChildField {

	private String columnid;
	private FovGridscheme fovGridscheme;
	private FovGridschemecolumn fovGridschemecolumn;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private FDataobjectcondition FDataobjectconditionBySubconditionid;
	private Integer orderno;
	private String title;
	private String fieldahead;
	private String aggregate;
	private Integer columnwidth;
	private Integer autosizetimes;
	private Integer flex;
	private Boolean hidden;
	private Boolean locked;
	private String othersetting;
	private String remark;
	private List<FovGridschemecolumn> columns;

	private String fieldid;

	public FovGridschemecolumn() {
	}

	public FovGridschemecolumn(FDataobjectfield field, FDataobjectcondition FDataobjectconditionBySubconditionid,
			Integer orderno, String title, String fieldahead, String aggregate, Integer columnwidth,
			Integer autosizetimes, Integer flex, Boolean hidden, Boolean locked, String othersetting, String remark) {
		super();
		this.FDataobjectfield = field;
		this.FDataobjectconditionBySubconditionid = FDataobjectconditionBySubconditionid;
		this.orderno = orderno;
		this.title = title;
		this.fieldahead = fieldahead;
		this.aggregate = aggregate;
		this.columnwidth = columnwidth;
		this.autosizetimes = autosizetimes;
		this.flex = flex;
		this.hidden = hidden;
		this.locked = locked;
		this.othersetting = othersetting;
		this.remark = remark;
	}

	@Transient
	public String getAdditionFieldname() {
		return DataObjectFieldUtils.getAdditionFieldname(FDataobjectfield, fieldahead, aggregate,
				FDataobjectconditionBySubconditionid, _getFDataobject(this).getObjectname(), true);
	}

	@Transient
	public String getAdditionObjectname() {
		if (fieldahead != null && fieldahead.length() > 0 && FDataobjectfield != null)
			return FDataobjectfield.getFDataobject().getObjectname();
		else
			return null;
	}

	@Transient
	public String getDefaulttitle() {
		return DataObjectFieldUtils.getDefaulttitle(FDataobjectfield, fieldahead, aggregate,
				FDataobjectconditionBySubconditionid, _getFDataobject(this).getObjectname());
	}

	private FDataobject _getFDataobject(FovGridschemecolumn column) {
		if (column.fovGridscheme == null)
			return _getFDataobject(column.getFovGridschemecolumn());
		else
			return column.getFovGridscheme().getFDataobject();
	}

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	@Column(name = "columnid", unique = true, nullable = false, length = 40)

	public String getColumnid() {
		return this.columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gridschemeid")

	public FovGridscheme getFovGridscheme() {
		return this.fovGridscheme;
	}

	public void setFovGridscheme(FovGridscheme fovGridscheme) {
		this.fovGridscheme = fovGridscheme;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")

	public FovGridschemecolumn getFovGridschemecolumn() {
		return this.fovGridschemecolumn;
	}

	public void setFovGridschemecolumn(FovGridschemecolumn fovGridschemecolumn) {
		this.fovGridschemecolumn = fovGridschemecolumn;
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

	@Column(name = "title", length = 200)

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

	@Column(name = "aggregate", length = 20)

	public String getAggregate() {
		return this.aggregate;
	}

	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}

	@Column(name = "columnwidth")

	public Integer getColumnwidth() {
		return this.columnwidth;
	}

	public void setColumnwidth(Integer columnwidth) {
		this.columnwidth = columnwidth;
	}

	@Column(name = "autosizetimes")

	public Integer getAutosizetimes() {
		return this.autosizetimes;
	}

	public void setAutosizetimes(Integer autosizetimes) {
		this.autosizetimes = autosizetimes;
	}

	@Column(name = "flex")

	public Integer getFlex() {
		return this.flex;
	}

	public void setFlex(Integer flex) {
		this.flex = flex;
	}

	@Column(name = "hidden")

	public Boolean getHidden() {
		return this.hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	@Column(name = "locked")

	public Boolean getLocked() {
		return this.locked;
	}

	public void setLocked(Boolean locked) {
		this.locked = locked;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fovGridschemecolumn")

	public List<FovGridschemecolumn> getColumns() {
		return this.columns == null || this.columns.size() == 0 ? null : this.columns;
	}

	public void setColumns(List<FovGridschemecolumn> fovGridschemecolumns) {
		this.columns = fovGridschemecolumns;
	}

	@Column(updatable = false, insertable = false)
	public String getFieldid() {
		return fieldid;
	}

	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
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
