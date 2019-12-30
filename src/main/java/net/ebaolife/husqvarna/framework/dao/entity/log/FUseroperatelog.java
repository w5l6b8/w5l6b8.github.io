package net.ebaolife.husqvarna.framework.dao.entity.log;


import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

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
@Table(name = "f_useroperatelog")
public class FUseroperatelog implements java.io.Serializable {

	private String logid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject FDataobject;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FUser FUser;
	private Date odate;
	private String ipaddress;
	private String dotype;
	private String idvalue;
	private String namevalue;
	private Byte isfile;
	private byte[] filedata;
	private String ocontent;
	private String remark;

	public FUseroperatelog() {
	}

	public FUseroperatelog(String logid, FDataobject FDataobject, FUser FUser, Date odate, String dotype) {
		this.logid = logid;
		this.FDataobject = FDataobject;
		this.FUser = FUser;
		this.odate = odate;
		this.dotype = dotype;
	}

	public FUseroperatelog(String logid, FDataobject FDataobject, FUser FUser, Date odate, String ipaddress,
			String dotype, String idvalue, String namevalue, Byte isfile, byte[] filedata, String ocontent,
			String remark) {
		this.logid = logid;
		this.FDataobject = FDataobject;
		this.FUser = FUser;
		this.odate = odate;
		this.ipaddress = ipaddress;
		this.dotype = dotype;
		this.idvalue = idvalue;
		this.namevalue = namevalue;
		this.isfile = isfile;
		this.filedata = filedata;
		this.ocontent = ocontent;
		this.remark = remark;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "logid", unique = true, nullable = false, length = 40)
	public String getLogid() {
		return this.logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
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
	@JoinColumn(name = "userid", nullable = false)
	public FUser getFUser() {
		return this.FUser;
	}

	public void setFUser(FUser FUser) {
		this.FUser = FUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "odate", nullable = false, length = 19)
	public Date getOdate() {
		return this.odate;
	}

	public void setOdate(Date odate) {
		this.odate = odate;
	}

	@Column(name = "ipaddress", length = 50)
	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	@Column(name = "dotype", nullable = false, length = 40)
	public String getDotype() {
		return this.dotype;
	}

	public void setDotype(String dotype) {
		this.dotype = dotype;
	}

	@Column(name = "idvalue", length = 40)
	public String getIdvalue() {
		return this.idvalue;
	}

	public void setIdvalue(String idvalue) {
		this.idvalue = idvalue;
	}

	@Column(name = "namevalue", length = 200)
	public String getNamevalue() {
		return this.namevalue;
	}

	public void setNamevalue(String namevalue) {
		this.namevalue = namevalue;
	}

	@Column(name = "isfile")
	public Byte getIsfile() {
		return this.isfile;
	}

	public void setIsfile(Byte isfile) {
		this.isfile = isfile;
	}

	@Column(name = "filedata")
	public byte[] getFiledata() {
		return this.filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

	@Column(name = "ocontent", length = 4000)
	public String getOcontent() {
		return this.ocontent;
	}

	public void setOcontent(String ocontent) {
		this.ocontent = ocontent;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
