package net.ebaolife.husqvarna.framework.dao.entity.groupquery;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
import net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction;
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
@Table(name = "f_groupqueryorder", uniqueConstraints = @UniqueConstraint(columnNames = "orderno"))
public class FGroupqueryorder implements java.io.Serializable {

	private String defaultorderid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private net.ebaolife.husqvarna.framework.dao.entity.utils.FFunction FFunction;
	private FGroupquery FGroupquery;
	private Integer orderno;
	private String fielddescription;
	private String fieldahead;
	private String aggregate;
	private String fieldfunction;
	private String direction;

	public FGroupqueryorder() {
	}

	public FGroupqueryorder(String defaultorderid, FDataobjectfield FDataobjectfield, Integer orderno,
			String fielddescription) {
		this.defaultorderid = defaultorderid;
		this.FDataobjectfield = FDataobjectfield;
		this.orderno = orderno;
		this.fielddescription = fielddescription;
	}

	public FGroupqueryorder(String defaultorderid, FDataobjectfield FDataobjectfield, FFunction FFunction,
			FGroupquery FGroupquery, Integer orderno, String fielddescription, String fieldahead, String aggregate,
			String fieldfunction, String direction) {
		this.defaultorderid = defaultorderid;
		this.FDataobjectfield = FDataobjectfield;
		this.FFunction = FFunction;
		this.FGroupquery = FGroupquery;
		this.orderno = orderno;
		this.fielddescription = fielddescription;
		this.fieldahead = fieldahead;
		this.aggregate = aggregate;
		this.fieldfunction = fieldfunction;
		this.direction = direction;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "defaultorderid", unique = true, nullable = false, length = 40)
	public String getDefaultorderid() {
		return this.defaultorderid;
	}

	public void setDefaultorderid(String defaultorderid) {
		this.defaultorderid = defaultorderid;
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
	@JoinColumn(name = "queryid")
	public FGroupquery getFGroupquery() {
		return this.FGroupquery;
	}

	public void setFGroupquery(FGroupquery FGroupquery) {
		this.FGroupquery = FGroupquery;
	}

	@Column(name = "orderno", unique = true, nullable = false)
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "fielddescription", nullable = false, length = 200)
	public String getFielddescription() {
		return this.fielddescription;
	}

	public void setFielddescription(String fielddescription) {
		this.fielddescription = fielddescription;
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

}
