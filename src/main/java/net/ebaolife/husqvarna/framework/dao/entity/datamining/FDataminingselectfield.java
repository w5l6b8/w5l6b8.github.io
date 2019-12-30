package net.ebaolife.husqvarna.framework.dao.entity.datamining;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
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
@Table(name = "f_dataminingselectfield")
public class FDataminingselectfield implements java.io.Serializable {

	private String selectfieldid;
	private FDataminingscheme FDataminingscheme;
	private FDataminingselectfield FDataminingselectfield;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition FDataobjectcondition;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private String aggregate;
	private String title;
	private int orderno;
	private Boolean onlytotal;
	private String othersetting;
	private String remark;
	private Set<FDataminingselectfield> FDataminingselectfields = new HashSet<FDataminingselectfield>(0);

	public FDataminingselectfield() {
	}

	public FDataminingselectfield(String selectfieldid, String title, int orderno) {
		this.selectfieldid = selectfieldid;
		this.title = title;
		this.orderno = orderno;
	}

	public FDataminingselectfield(String selectfieldid, FDataminingscheme FDataminingscheme,
			FDataminingselectfield FDataminingselectfield, FDataobjectcondition FDataobjectcondition,
			FDataobjectfield FDataobjectfield, String aggregate, String title, int orderno, Boolean onlytotal,
			String othersetting, String remark, Set<FDataminingselectfield> FDataminingselectfields) {
		this.selectfieldid = selectfieldid;
		this.FDataminingscheme = FDataminingscheme;
		this.FDataminingselectfield = FDataminingselectfield;
		this.FDataobjectcondition = FDataobjectcondition;
		this.FDataobjectfield = FDataobjectfield;
		this.aggregate = aggregate;
		this.title = title;
		this.orderno = orderno;
		this.onlytotal = onlytotal;
		this.othersetting = othersetting;
		this.remark = remark;
		this.FDataminingselectfields = FDataminingselectfields;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "selectfieldid", unique = true, nullable = false, length = 40)
	public String getSelectfieldid() {
		return this.selectfieldid;
	}

	public void setSelectfieldid(String selectfieldid) {
		this.selectfieldid = selectfieldid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schemeid")
	public FDataminingscheme getFDataminingscheme() {
		return this.FDataminingscheme;
	}

	public void setFDataminingscheme(FDataminingscheme FDataminingscheme) {
		this.FDataminingscheme = FDataminingscheme;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public FDataminingselectfield getFDataminingselectfield() {
		return this.FDataminingselectfield;
	}

	public void setFDataminingselectfield(FDataminingselectfield FDataminingselectfield) {
		this.FDataminingselectfield = FDataminingselectfield;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conditionid")
	public FDataobjectcondition getFDataobjectcondition() {
		return this.FDataobjectcondition;
	}

	public void setFDataobjectcondition(FDataobjectcondition FDataobjectcondition) {
		this.FDataobjectcondition = FDataobjectcondition;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "fieldid")
	public FDataobjectfield getFDataobjectfield() {
		return this.FDataobjectfield;
	}

	public void setFDataobjectfield(FDataobjectfield FDataobjectfield) {
		this.FDataobjectfield = FDataobjectfield;
	}

	@Column(name = "aggregate", length = 20)
	public String getAggregate() {
		return this.aggregate;
	}

	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "orderno", nullable = false)
	public int getOrderno() {
		return this.orderno;
	}

	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}

	@Column(name = "onlytotal")
	public Boolean getOnlytotal() {
		return this.onlytotal;
	}

	public void setOnlytotal(Boolean onlytotal) {
		this.onlytotal = onlytotal;
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataminingselectfield")
	@OrderBy("orderno")
	public Set<FDataminingselectfield> getFDataminingselectfields() {
		return this.FDataminingselectfields;
	}

	public void setFDataminingselectfields(Set<FDataminingselectfield> FDataminingselectfields) {
		this.FDataminingselectfields = FDataminingselectfields;
	}

}
