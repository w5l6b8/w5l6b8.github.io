package net.ebaolife.husqvarna.framework.dao.entity.dataobject;

import net.ebaolife.husqvarna.framework.dao.entity.module.FModulefunction;
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
@Table(name = "f_dataobjectadditionfuncion", uniqueConstraints = {
		@UniqueConstraint(columnNames = { "objectid", "title" }),
		@UniqueConstraint(columnNames = { "objectid", "fcode" }) })
public class FDataobjectadditionfuncion implements java.io.Serializable {

	private String additionfunctionid;
	private FDataobject FDataobject;
	private String title;
	private String fcode;
	private String fdescription;
	private Integer orderno;
	private Boolean isdisable;
	private String iconcls;
	private String icon;
	private byte[] iconfile;
	private Integer minselectrecordnum;
	private Integer maxselectrecordnum;
	private String menuname;
	private String menusetting;
	private String othersetting;
	private String windowclass;
	private String functionname;
	private String xtype;
	private Boolean clickvalidate;
	private String ftype;
	private String remark;
	private Set<FModulefunction> FModulefunctions = new HashSet<FModulefunction>(0);

	public FDataobjectadditionfuncion() {
	}

	public FDataobjectadditionfuncion(String additionfunctionid, FDataobject FDataobject, String title, String fcode) {
		this.additionfunctionid = additionfunctionid;
		this.FDataobject = FDataobject;
		this.title = title;
		this.fcode = fcode;
	}

	public FDataobjectadditionfuncion(FDataobject FDataobject, String title, String fcode, String fdescription,
			Integer orderno, Boolean isdisable, String iconcls, String icon, byte[] iconfile,
			Integer minselectrecordnum, Integer maxselectrecordnum, String menuname, String menusetting,
			String othersetting, String windowclass, String functionname, String xtype, Boolean clickvalidate,
			String ftype, String remark, Set<FModulefunction> FModulefunctions) {
		this.FDataobject = FDataobject;
		this.title = title;
		this.fcode = fcode;
		this.fdescription = fdescription;
		this.orderno = orderno;
		this.isdisable = isdisable;
		this.iconcls = iconcls;
		this.icon = icon;
		this.iconfile = iconfile;
		this.minselectrecordnum = minselectrecordnum;
		this.maxselectrecordnum = maxselectrecordnum;
		this.menuname = menuname;
		this.menusetting = menusetting;
		this.othersetting = othersetting;
		this.windowclass = windowclass;
		this.functionname = functionname;
		this.xtype = xtype;
		this.clickvalidate = clickvalidate;
		this.ftype = ftype;
		this.remark = remark;
		this.FModulefunctions = FModulefunctions;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "additionfunctionid", unique = true, nullable = false, length = 40)
	public String getAdditionfunctionid() {
		return this.additionfunctionid;
	}

	public void setAdditionfunctionid(String additionfunctionid) {
		this.additionfunctionid = additionfunctionid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objectid", nullable = false)
	public FDataobject getFDataobject() {
		return this.FDataobject;
	}

	public void setFDataobject(FDataobject FDataobject) {
		this.FDataobject = FDataobject;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "fcode", nullable = false, length = 50)
	public String getFcode() {
		return this.fcode;
	}

	public void setFcode(String fcode) {
		this.fcode = fcode;
	}

	@Column(name = "fdescription", length = 200)
	public String getFdescription() {
		return this.fdescription;
	}

	public void setFdescription(String fdescription) {
		this.fdescription = fdescription;
	}

	@Column(name = "orderno")
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "isdisable")
	public Boolean getIsdisable() {
		return this.isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	@Column(name = "iconcls", length = 200)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "icon", length = 200)
	public String getIcon() {
		return this.icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Column(name = "iconfile")
	public byte[] getIconfile() {
		return this.iconfile;
	}

	public void setIconfile(byte[] iconfile) {
		this.iconfile = iconfile;
	}

	@Column(name = "minselectrecordnum")
	public Integer getMinselectrecordnum() {
		return this.minselectrecordnum;
	}

	public void setMinselectrecordnum(Integer minselectrecordnum) {
		this.minselectrecordnum = minselectrecordnum;
	}

	@Column(name = "maxselectrecordnum")
	public Integer getMaxselectrecordnum() {
		return this.maxselectrecordnum;
	}

	public void setMaxselectrecordnum(Integer maxselectrecordnum) {
		this.maxselectrecordnum = maxselectrecordnum;
	}

	@Column(name = "menuname", length = 50)
	public String getMenuname() {
		return this.menuname;
	}

	public void setMenuname(String menuname) {
		this.menuname = menuname;
	}

	@Column(name = "menusetting", length = 200)
	public String getMenusetting() {
		return this.menusetting;
	}

	public void setMenusetting(String menusetting) {
		this.menusetting = menusetting;
	}

	@Column(name = "othersetting", length = 200)
	public String getOthersetting() {
		return this.othersetting;
	}

	public void setOthersetting(String othersetting) {
		this.othersetting = othersetting;
	}

	@Column(name = "windowclass", length = 200)
	public String getWindowclass() {
		return this.windowclass;
	}

	public void setWindowclass(String windowclass) {
		this.windowclass = windowclass;
	}

	@Column(name = "functionname", length = 200)
	public String getFunctionname() {
		return this.functionname;
	}

	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}

	@Column(name = "xtype", length = 200)
	public String getXtype() {
		return this.xtype;
	}

	public void setXtype(String xtype) {
		this.xtype = xtype;
	}

	@Column(name = "clickvalidate")
	public Boolean getClickvalidate() {
		return this.clickvalidate;
	}

	public void setClickvalidate(Boolean clickvalidate) {
		this.clickvalidate = clickvalidate;
	}

	@Column(name = "ftype", length = 50)
	public String getFtype() {
		return this.ftype;
	}

	public void setFtype(String ftype) {
		this.ftype = ftype;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataobjectadditionfuncion")
	public Set<FModulefunction> getFModulefunctions() {
		return this.FModulefunctions;
	}

	public void setFModulefunctions(Set<FModulefunction> FModulefunctions) {
		this.FModulefunctions = FModulefunctions;
	}

}
