package net.ebaolife.husqvarna.framework.dao.entity.system;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@DynamicUpdate
@Table(name = "f_systemcfg")
@SuppressWarnings("serial")

public class FSystemcfg implements java.io.Serializable {

	

	private String id;
	private String companyid;
	private String userid;
	private String type;
	private String value;
	private Integer orderno;
	private String creater;
	private Timestamp createdate;
	private String lastmodifier;
	private Timestamp lastmodifydate;

	public FSystemcfg() {
	}

	public FSystemcfg(String companyid, String userid, String type, String value, Integer orderno, String creater,
			Timestamp createdate) {
		this.companyid = companyid;
		this.userid = userid;
		this.type = type;
		this.value = value;
		this.orderno = orderno;
		this.creater = creater;
		this.createdate = createdate;
	}

	public FSystemcfg(String companyid, String userid, String type, String value, Integer orderno, String creater,
			Timestamp createdate, String lastmodifier, Timestamp lastmodifydate) {
		this.companyid = companyid;
		this.userid = userid;
		this.type = type;
		this.value = value;
		this.orderno = orderno;
		this.creater = creater;
		this.createdate = createdate;
		this.lastmodifier = lastmodifier;
		this.lastmodifydate = lastmodifydate;
	}

	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "id", unique = true, nullable = false, length = 40)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "companyid", nullable = false, length = 40)

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	@Column(name = "userid", nullable = false, length = 40)

	public String getUserid() {
		return this.userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	@Column(name = "type", nullable = false, length = 2)

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	@Column(name = "value", nullable = false, length = 65535)

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Column(name = "orderno", nullable = false)

	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "creater", nullable = false, length = 40)

	public String getCreater() {
		return this.creater;
	}

	public void setCreater(String creater) {
		this.creater = creater;
	}

	@Column(name = "createdate", nullable = false, length = 19)

	public Timestamp getCreatedate() {
		return this.createdate;
	}

	public void setCreatedate(Timestamp createdate) {
		this.createdate = createdate;
	}

	@Column(name = "lastmodifier", length = 40)

	public String getLastmodifier() {
		return this.lastmodifier;
	}

	public void setLastmodifier(String lastmodifier) {
		this.lastmodifier = lastmodifier;
	}

	@Column(name = "lastmodifydate", length = 19)

	public Timestamp getLastmodifydate() {
		return this.lastmodifydate;
	}

	public void setLastmodifydate(Timestamp lastmodifydate) {
		this.lastmodifydate = lastmodifydate;
	}

}