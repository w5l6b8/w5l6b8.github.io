package net.ebaolife.husqvarna.framework.dao.entity.dictionary;

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
@Table(name = "f_objectfieldproperty", uniqueConstraints = @UniqueConstraint(columnNames = "propertyname"))
public class FObjectfieldproperty implements java.io.Serializable {

	private String propertyid;
	private String propertyname;
	private Boolean caninput;
	private Boolean canmult;
	private String pfieldtype;
	private String value;
	private String remark;
	private Set<FDataobjectfield> FDataobjectfields = new HashSet<FDataobjectfield>(0);

	public FObjectfieldproperty() {
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "propertyid", unique = true, nullable = false, length = 40)
	public String getPropertyid() {
		return this.propertyid;
	}

	public void setPropertyid(String propertyid) {
		this.propertyid = propertyid;
	}

	@Column(name = "propertyname", unique = true, nullable = false, length = 50)
	public String getPropertyname() {
		return this.propertyname;
	}

	public void setPropertyname(String propertyname) {
		this.propertyname = propertyname;
	}

	@Column(name = "caninput")
	public Boolean getCaninput() {
		return this.caninput;
	}

	public void setCaninput(Boolean caninput) {
		this.caninput = caninput;
	}

	@Column(name = "canmult")
	public Boolean getCanmult() {
		return this.canmult;
	}

	public void setCanmult(Boolean canmult) {
		this.canmult = canmult;
	}

	@Column(name = "pfieldtype", length = 10)
	public String getPfieldtype() {
		return this.pfieldtype;
	}

	public void setPfieldtype(String pfieldtype) {
		this.pfieldtype = pfieldtype;
	}

	@Column(name = "value", nullable = false, length = 4000)
	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FObjectfieldproperty")
	public Set<FDataobjectfield> getFDataobjectfields() {
		return this.FDataobjectfields;
	}

	public void setFDataobjectfields(Set<FDataobjectfield> FDataobjectfields) {
		this.FDataobjectfields = FDataobjectfields;
	}

}
