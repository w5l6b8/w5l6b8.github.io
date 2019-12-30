package net.ebaolife.husqvarna.framework.dao.entity.utils;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectconditiondetail;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridnavigateschemedetail;
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

@Entity
@DynamicUpdate
@Table(name = "f_function", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class FFunction implements java.io.Serializable {

	private static final long serialVersionUID = -8653468231440451819L;
	private String functionid;
	private String groupname;
	private Integer orderno;
	private String title;
	private String expression;
	private String mysqlexpression;
	private String sqlserverexpression;
	private String oracleexpression;
	private Integer paramcount;
	private String returntype;
	private String remark;
	private Set<FDataobjectconditiondetail> FDataobjectconditiondetails = new HashSet<FDataobjectconditiondetail>(0);
	private Set<FovGridnavigateschemedetail> fovGridnavigateschemedetails = new HashSet<FovGridnavigateschemedetail>(0);

	public FFunction(String functionid) {
		this.functionid = functionid;
	}

	public FFunction() {
	}

	public FFunction(String functionid, String groupname, Integer orderno, String title, String expression,
			String returntype) {
		this.functionid = functionid;
		this.groupname = groupname;
		this.orderno = orderno;
		this.title = title;
		this.expression = expression;
		this.returntype = returntype;
	}

	public FFunction(String functionid, String groupname, Integer orderno, String title, String expression,
			String mysqlexpression, String sqlserverexpression, String oracleexpression, Integer paramcount,
			String returntype, String remark, Set<FDataobjectconditiondetail> FDataobjectconditiondetails,
			Set<FovGridnavigateschemedetail> fovGridnavigateschemedetails) {
		this.functionid = functionid;
		this.groupname = groupname;
		this.orderno = orderno;
		this.title = title;
		this.expression = expression;
		this.mysqlexpression = mysqlexpression;
		this.sqlserverexpression = sqlserverexpression;
		this.oracleexpression = oracleexpression;
		this.paramcount = paramcount;
		this.returntype = returntype;
		this.remark = remark;
		this.FDataobjectconditiondetails = FDataobjectconditiondetails;
		this.fovGridnavigateschemedetails = fovGridnavigateschemedetails;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "functionid", unique = true, nullable = false, length = 40)
	public String getFunctionid() {
		return this.functionid;
	}

	public void setFunctionid(String functionid) {
		this.functionid = functionid;
	}

	@Column(name = "groupname", nullable = false, length = 50)
	public String getGroupname() {
		return this.groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	@Column(name = "orderno", nullable = false)
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "title", unique = true, nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "expression", nullable = false, length = 200)
	public String getExpression() {
		return this.expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}

	@Column(name = "mysqlexpression", length = 200)
	public String getMysqlexpression() {
		return this.mysqlexpression;
	}

	public void setMysqlexpression(String mysqlexpression) {
		this.mysqlexpression = mysqlexpression;
	}

	@Column(name = "sqlserverexpression", length = 200)
	public String getSqlserverexpression() {
		return this.sqlserverexpression;
	}

	public void setSqlserverexpression(String sqlserverexpression) {
		this.sqlserverexpression = sqlserverexpression;
	}

	@Column(name = "oracleexpression", length = 200)
	public String getOracleexpression() {
		return this.oracleexpression;
	}

	public void setOracleexpression(String oracleexpression) {
		this.oracleexpression = oracleexpression;
	}

	@Column(name = "paramcount")
	public Integer getParamcount() {
		return this.paramcount;
	}

	public void setParamcount(Integer paramcount) {
		this.paramcount = paramcount;
	}

	@Column(name = "returntype", nullable = false, length = 200)
	public String getReturntype() {
		return this.returntype;
	}

	public void setReturntype(String returntype) {
		this.returntype = returntype;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FFunction")
	public Set<FDataobjectconditiondetail> getFDataobjectconditiondetails() {
		return this.FDataobjectconditiondetails;
	}

	public void setFDataobjectconditiondetails(Set<FDataobjectconditiondetail> FDataobjectconditiondetails) {
		this.FDataobjectconditiondetails = FDataobjectconditiondetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FFunction")
	public Set<FovGridnavigateschemedetail> getFovGridnavigateschemedetails() {
		return this.fovGridnavigateschemedetails;
	}

	public void setFovGridnavigateschemedetails(Set<FovGridnavigateschemedetail> fovGridnavigateschemedetails) {
		this.fovGridnavigateschemedetails = fovGridnavigateschemedetails;
	}

}
