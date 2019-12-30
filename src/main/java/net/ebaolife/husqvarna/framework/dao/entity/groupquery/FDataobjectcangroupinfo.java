package net.ebaolife.husqvarna.framework.dao.entity.groupquery;


import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FNumbergroup;
import net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction;
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
@Table(name = "f_dataobjectcangroupinfo")
public class FDataobjectcangroupinfo implements java.io.Serializable {

	private String dataobjectgroupid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject FDataobject;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition FDataobjectcondition;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private FFunction FFunction;
	private net.ebaolife.husqvarna.framework.dao.entity.dictionary.FNumbergroup FNumbergroup;
	private String title;
	private Integer orderno;
	private String fieldahead;
	private String aggregate;
	private String fieldfunction;
	private Boolean reverseorder;
	private Boolean collapsed;
	private String leveltype;
	private String remark;
	private Set<FGroupquerygroupinfo> FGroupquerygroupinfos = new HashSet<FGroupquerygroupinfo>(0);

	public FDataobjectcangroupinfo() {
	}

	public FDataobjectcangroupinfo(String dataobjectgroupid, FDataobject FDataobject, FDataobjectfield FDataobjectfield,
			String title) {
		this.dataobjectgroupid = dataobjectgroupid;
		this.FDataobject = FDataobject;
		this.FDataobjectfield = FDataobjectfield;
		this.title = title;
	}

	public FDataobjectcangroupinfo(String dataobjectgroupid, FDataobject FDataobject,
			FDataobjectcondition FDataobjectcondition, FDataobjectfield FDataobjectfield, net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction FFunction,
			FNumbergroup FNumbergroup, String title, Integer orderno, String fieldahead, String aggregate,
			String fieldfunction, Boolean reverseorder, Boolean collapsed, String leveltype, String remark,
			Set<FGroupquerygroupinfo> FGroupquerygroupinfos) {
		this.dataobjectgroupid = dataobjectgroupid;
		this.FDataobject = FDataobject;
		this.FDataobjectcondition = FDataobjectcondition;
		this.FDataobjectfield = FDataobjectfield;
		this.FFunction = FFunction;
		this.FNumbergroup = FNumbergroup;
		this.title = title;
		this.orderno = orderno;
		this.fieldahead = fieldahead;
		this.aggregate = aggregate;
		this.fieldfunction = fieldfunction;
		this.reverseorder = reverseorder;
		this.collapsed = collapsed;
		this.leveltype = leveltype;
		this.remark = remark;
		this.FGroupquerygroupinfos = FGroupquerygroupinfos;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "dataobjectgroupid", unique = true, nullable = false, length = 40)
	public String getDataobjectgroupid() {
		return this.dataobjectgroupid;
	}

	public void setDataobjectgroupid(String dataobjectgroupid) {
		this.dataobjectgroupid = dataobjectgroupid;
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
	@JoinColumn(name = "subconditionid")
	public FDataobjectcondition getFDataobjectcondition() {
		return this.FDataobjectcondition;
	}

	public void setFDataobjectcondition(FDataobjectcondition FDataobjectcondition) {
		this.FDataobjectcondition = FDataobjectcondition;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fieldid", nullable = false)
	public FDataobjectfield getFDataobjectfield() {
		return this.FDataobjectfield;
	}

	public void setFDataobjectfield(FDataobjectfield FDataobjectfield) {
		this.FDataobjectfield = FDataobjectfield;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public FFunction getFFunction() {
		return this.FFunction;
	}

	public void setFFunction(FFunction FFunction) {
		this.FFunction = FFunction;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "numbergroupid")
	public FNumbergroup getFNumbergroup() {
		return this.FNumbergroup;
	}

	public void setFNumbergroup(FNumbergroup FNumbergroup) {
		this.FNumbergroup = FNumbergroup;
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

	@Column(name = "fieldahead", length = 200)
	public String getFieldahead() {
		return this.fieldahead;
	}

	public void setFieldahead(String fieldahead) {
		this.fieldahead = fieldahead;
	}

	@Column(name = "aggregate", length = 10)
	public String getAggregate() {
		return this.aggregate;
	}

	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}

	@Column(name = "fieldfunction", length = 200)
	public String getFieldfunction() {
		return this.fieldfunction;
	}

	public void setFieldfunction(String fieldfunction) {
		this.fieldfunction = fieldfunction;
	}

	@Column(name = "reverseorder")
	public Boolean getReverseorder() {
		return this.reverseorder;
	}

	public void setReverseorder(Boolean reverseorder) {
		this.reverseorder = reverseorder;
	}

	@Column(name = "collapsed")
	public Boolean getCollapsed() {
		return this.collapsed;
	}

	public void setCollapsed(Boolean collapsed) {
		this.collapsed = collapsed;
	}

	@Column(name = "leveltype", length = 20)
	public String getLeveltype() {
		return this.leveltype;
	}

	public void setLeveltype(String leveltype) {
		this.leveltype = leveltype;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobjectcangroupinfo")
	public Set<FGroupquerygroupinfo> getFGroupquerygroupinfos() {
		return this.FGroupquerygroupinfos;
	}

	public void setFGroupquerygroupinfos(Set<FGroupquerygroupinfo> FGroupquerygroupinfos) {
		this.FGroupquerygroupinfos = FGroupquerygroupinfos;
	}

}
