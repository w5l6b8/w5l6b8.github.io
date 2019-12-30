package net.ebaolife.husqvarna.framework.dao.entity.dataobject;

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
@Table(name = "f_dataobjectdefaultorder", uniqueConstraints = @UniqueConstraint(columnNames = { "objectid",
		"orderno" }))
public class FDataobjectdefaultorder implements java.io.Serializable, ParentChildField {

	private String defaultorderid;
	private FDataobject FDataobject;
	private FDataobjectfield FDataobjectfield;
	private net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction FFunction;
	private Integer orderno;
	private String fieldahead;
	private String aggregate;
	private String fieldfunction;
	private String direction;

	public FDataobjectdefaultorder() {
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "defaultorderid", unique = true, nullable = false, length = 40)
	public String getDefaultorderid() {
		return this.defaultorderid;
	}

	public void setDefaultorderid(String defaultorderid) {
		this.defaultorderid = defaultorderid;
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

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid")
	public FFunction getFFunction() {
		return this.FFunction;
	}

	public void setFFunction(FFunction FFunction) {
		this.FFunction = FFunction;
	}

	@Column(name = "orderno", nullable = false)
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

	@Column(name = "aggregate", length = 20)
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
