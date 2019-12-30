package net.ebaolife.husqvarna.framework.dao.entity.viewsetting;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction;
import net.ebaolife.husqvarna.framework.dao.entityinterface.ParentChildField;
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
@Table(name = "fov_gridsortschemedetail")
public class FovGridsortschemedetail implements java.io.Serializable, ParentChildField {

	private String schemedetailid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition FDataobjectcondition;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction FFunction;
	private FovGridsortscheme fovGridsortscheme;
	private int orderno;
	private String fieldahead;
	private String aggregate;
	private String fieldfunction;
	private String direction;

	public FovGridsortschemedetail() {
	}

	public FovGridsortschemedetail(String schemedetailid, FDataobjectfield FDataobjectfield,
			FovGridsortscheme fovGridsortscheme, int orderno) {
		this.schemedetailid = schemedetailid;
		this.FDataobjectfield = FDataobjectfield;
		this.fovGridsortscheme = fovGridsortscheme;
		this.orderno = orderno;
	}

	public FovGridsortschemedetail(String schemedetailid, FDataobjectcondition FDataobjectcondition,
			FDataobjectfield FDataobjectfield, FFunction FFunction, FovGridsortscheme fovGridsortscheme, int orderno,
			String fieldahead, String aggregate, String fieldfunction, String direction) {
		this.schemedetailid = schemedetailid;
		this.FDataobjectcondition = FDataobjectcondition;
		this.FDataobjectfield = FDataobjectfield;
		this.FFunction = FFunction;
		this.fovGridsortscheme = fovGridsortscheme;
		this.orderno = orderno;
		this.fieldahead = fieldahead;
		this.aggregate = aggregate;
		this.fieldfunction = fieldfunction;
		this.direction = direction;
	}

	@Id
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@GeneratedValue(generator = "generator")
	@Column(name = "schemedetailid", unique = true, nullable = false, length = 40)
	public String getSchemedetailid() {
		return this.schemedetailid;
	}

	public void setSchemedetailid(String schemedetailid) {
		this.schemedetailid = schemedetailid;
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
	@JoinColumn(name = "schemeid", nullable = false)
	public FovGridsortscheme getFovGridsortscheme() {
		return this.fovGridsortscheme;
	}

	public void setFovGridsortscheme(FovGridsortscheme fovGridsortscheme) {
		this.fovGridsortscheme = fovGridsortscheme;
	}

	@Column(name = "orderno", nullable = false)
	public int getOrderno() {
		return this.orderno;
	}

	public void setOrderno(int orderno) {
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

	@Column(name = "direction", length = 10)
	public String getDirection() {
		return this.direction;
	}

	public void setDirection(String direction) {
		this.direction = direction;
	}

	@Override
	@Transient
	public FDataobjectcondition getFDataobjectconditionBySubconditionid() {
		return null;
	}

	@Override
	public void setFDataobjectconditionBySubconditionid(FDataobjectcondition value) {
	}

	@Override
	@Transient
	public String getCondition() {
		return null;
	}

	@Override
	public void setCondition(String value) {

	}

	@Override
	@Transient
	public String getRemark() {
		return null;
	}

	@Override
	public void setRemark(String value) {
	}

}
