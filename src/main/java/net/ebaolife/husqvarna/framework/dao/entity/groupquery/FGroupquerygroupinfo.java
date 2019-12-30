package net.ebaolife.husqvarna.framework.dao.entity.groupquery;

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
@Table(name = "f_groupquerygroupinfo")
public class FGroupquerygroupinfo implements java.io.Serializable {

	private String querygroupid;
	private FDataobjectcangroupinfo FDataobjectcangroupinfo;
	private FGroupquery FGroupquery;
	private String title;
	private Integer orderno;
	private Boolean reverseorder;
	private Boolean collapsed;
	private String leveltype;
	private String remark;

	public FGroupquerygroupinfo() {
	}

	public FGroupquerygroupinfo(String querygroupid, FDataobjectcangroupinfo FDataobjectcangroupinfo,
			FGroupquery FGroupquery) {
		this.querygroupid = querygroupid;
		this.FDataobjectcangroupinfo = FDataobjectcangroupinfo;
		this.FGroupquery = FGroupquery;
	}

	public FGroupquerygroupinfo(String querygroupid, FDataobjectcangroupinfo FDataobjectcangroupinfo,
			FGroupquery FGroupquery, String title, Integer orderno, Boolean reverseorder, Boolean collapsed,
			String leveltype, String remark) {
		this.querygroupid = querygroupid;
		this.FDataobjectcangroupinfo = FDataobjectcangroupinfo;
		this.FGroupquery = FGroupquery;
		this.title = title;
		this.orderno = orderno;
		this.reverseorder = reverseorder;
		this.collapsed = collapsed;
		this.leveltype = leveltype;
		this.remark = remark;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "querygroupid", unique = true, nullable = false, length = 40)
	public String getQuerygroupid() {
		return this.querygroupid;
	}

	public void setQuerygroupid(String querygroupid) {
		this.querygroupid = querygroupid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dataobjectgroupid", nullable = false)
	public FDataobjectcangroupinfo getFDataobjectcangroupinfo() {
		return this.FDataobjectcangroupinfo;
	}

	public void setFDataobjectcangroupinfo(FDataobjectcangroupinfo FDataobjectcangroupinfo) {
		this.FDataobjectcangroupinfo = FDataobjectcangroupinfo;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "queryid", nullable = false)
	public FGroupquery getFGroupquery() {
		return this.FGroupquery;
	}

	public void setFGroupquery(FGroupquery FGroupquery) {
		this.FGroupquery = FGroupquery;
	}

	@Column(name = "title", length = 50)
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

}
