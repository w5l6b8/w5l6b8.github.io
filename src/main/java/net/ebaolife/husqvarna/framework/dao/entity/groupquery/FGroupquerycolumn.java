package net.ebaolife.husqvarna.framework.dao.entity.groupquery;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
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
@Table(name = "f_groupquerycolumn")
public class FGroupquerycolumn implements java.io.Serializable, ParentChildField {

	private String columnid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private FDataobjectcondition FDataobjectconditionBySubconditionid;
	private FGroupquery FGroupquery;
	private FGroupquerycolumn FGroupquerycolumn;
	private int orderno;
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
	private List<FGroupquerycolumn> FGroupquerycolumns;

	public FGroupquerycolumn() {
	}

	public FGroupquerycolumn(String columnid, int orderno) {
		this.columnid = columnid;
		this.orderno = orderno;
	}

	public FGroupquerycolumn(String columnid, FDataobjectcondition FDataobjectconditionBySubconditionid,
			FDataobjectfield FDataobjectfield, FGroupquery FGroupquery, FGroupquerycolumn FGroupquerycolumn,
			int orderno, String title, String fieldahead, String aggregate, Integer columnwidth, Integer autosizetimes,
			Integer flex, Boolean hidden, Boolean locked, String othersetting, String remark,
			List<FGroupquerycolumn> FGroupquerycolumns) {
		this.columnid = columnid;
		this.FDataobjectconditionBySubconditionid = FDataobjectconditionBySubconditionid;
		this.FDataobjectfield = FDataobjectfield;
		this.FGroupquery = FGroupquery;
		this.FGroupquerycolumn = FGroupquerycolumn;
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
		this.FGroupquerycolumns = FGroupquerycolumns;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "columnid", unique = true, nullable = false, length = 40)
	public String getColumnid() {
		return this.columnid;
	}

	public void setColumnid(String columnid) {
		this.columnid = columnid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "subconditionid")
	public FDataobjectcondition getFDataobjectconditionBySubconditionid() {
		return this.FDataobjectconditionBySubconditionid;
	}

	public void setFDataobjectconditionBySubconditionid(FDataobjectcondition FDataobjectconditionBySubconditionid) {
		this.FDataobjectconditionBySubconditionid = FDataobjectconditionBySubconditionid;
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
	@JoinColumn(name = "queryid")
	public FGroupquery getFGroupquery() {
		return this.FGroupquery;
	}

	public void setFGroupquery(FGroupquery FGroupquery) {
		this.FGroupquery = FGroupquery;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public FGroupquerycolumn getFGroupquerycolumn() {
		return this.FGroupquerycolumn;
	}

	public void setFGroupquerycolumn(FGroupquerycolumn FGroupquerycolumn) {
		this.FGroupquerycolumn = FGroupquerycolumn;
	}

	@Column(name = "orderno", nullable = false)
	public int getOrderno() {
		return this.orderno;
	}

	public void setOrderno(int orderno) {
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FGroupquerycolumn")
	@OrderBy("orderno")
	public List<FGroupquerycolumn> getFGroupquerycolumns() {
		return this.FGroupquerycolumns;
	}

	public void setFGroupquerycolumns(List<FGroupquerycolumn> FGroupquerycolumns) {
		this.FGroupquerycolumns = FGroupquerycolumns;
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
