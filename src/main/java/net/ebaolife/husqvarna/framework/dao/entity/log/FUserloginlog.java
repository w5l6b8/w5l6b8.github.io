package net.ebaolife.husqvarna.framework.dao.entity.log;

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
@Table(name = "f_userloginlog")
public class FUserloginlog implements java.io.Serializable {

	private String logid;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FUser FUser;
	private Date logindate;
	private Date logoutdate;
	private String logintype;
	private String logouttype;
	private String ipaddress;
	private String remark;

	public FUserloginlog() {
	}

	public FUserloginlog(String logid, FUser FUser) {
		this.logid = logid;
		this.FUser = FUser;
	}

	public FUserloginlog(String logid, FUser FUser, Date logindate, Date logoutdate, String logintype,
			String logouttype, String ipaddress, String remark) {
		this.logid = logid;
		this.FUser = FUser;
		this.logindate = logindate;
		this.logoutdate = logoutdate;
		this.logintype = logintype;
		this.logouttype = logouttype;
		this.ipaddress = ipaddress;
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
	@JoinColumn(name = "userid", nullable = false)
	public FUser getFUser() {
		return this.FUser;
	}

	public void setFUser(FUser FUser) {
		this.FUser = FUser;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logindate", length = 19)
	public Date getLogindate() {
		return this.logindate;
	}

	public void setLogindate(Date logindate) {
		this.logindate = logindate;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "logoutdate", length = 19)
	public Date getLogoutdate() {
		return this.logoutdate;
	}

	public void setLogoutdate(Date logoutdate) {
		this.logoutdate = logoutdate;
	}

	@Column(name = "logintype", length = 20)
	public String getLogintype() {
		return this.logintype;
	}

	public void setLogintype(String logintype) {
		this.logintype = logintype;
	}

	@Column(name = "logouttype", length = 20)
	public String getLogouttype() {
		return this.logouttype;
	}

	public void setLogouttype(String logouttype) {
		this.logouttype = logouttype;
	}

	@Column(name = "ipaddress", length = 50)
	public String getIpaddress() {
		return this.ipaddress;
	}

	public void setIpaddress(String ipaddress) {
		this.ipaddress = ipaddress;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
