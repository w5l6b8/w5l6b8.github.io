package net.ebaolife.husqvarna.framework.dao.entity.dataobject;


import net.ebaolife.husqvarna.framework.bean.FieldType;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary;
import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FObjectfieldproperty;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FDatafilterrolelimit;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FRolefieldlimit;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FUserfieldlimit;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovFormschemedetail;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridschemecolumn;
import net.ebaolife.husqvarna.framework.utils.DataObjectUtils;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
@Table(name = "f_dataobjectfield", uniqueConstraints = { @UniqueConstraint(columnNames = { "objectid", "fieldname" }),
		@UniqueConstraint(columnNames = { "objectid", "fieldtitle" }),
		@UniqueConstraint(columnNames = { "objectid", "nativename" }) })

public class FDataobjectfield implements java.io.Serializable {

	private String fieldid;
	private FDataobject FDataobject;
	private net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary FDictionary;
	private net.ebaolife.husqvarna.framework.dao.entity.dictionary.FObjectfieldproperty FObjectfieldproperty;
	private FAdditionfield FAdditionfield;

	private String fieldname;
	private String nativename;
	private String fieldtitle;
	private String fielddbname;
	private Integer orderno;
	private String fieldtype;
	private String fieldahead;
	private int fieldlen;
	private Integer digitslen;
	private String fieldgroup;
	private String fieldrelation;
	private String jointable;
	private String joincolumnname;
	private String fieldformula;
	private Boolean isdisable;
	private Boolean ishidden;
	private Boolean allownew;
	private Boolean allowedit;
	private Boolean allowgroup;
	private Boolean allowsummary;
	private Boolean allowaggregate;
	private Boolean shownavigatortree;
	private Boolean newneedselected;
	private Boolean ischartcategory;
	private Boolean ischartnumeric;
	private Boolean haveattachment;
	private Boolean ismonetary;
	private Boolean isrequired;
	private Long maxval;
	private Long minval;
	private String monetarytype;
	private String regexvalue;
	private String regextext;
	private String vtype;
	private String jsvalue;
	private String unittext;
	private String defaultvalue;
	private String propertyvalue;
	private String divisor;
	private String denominator;
	private String tooltiptpl;
	private String othersetting;
	private String iconcls;
	private String modelset;
	private String gridcolumnset;
	private String formfieldset;
	private String reportset;
	private String remark;
	private String creater;
	private Timestamp createdate;
	private String lastmodifier;
	private Timestamp lastmodifydate;
	private Set<FRolefieldlimit> FRolefieldlimits = new HashSet<FRolefieldlimit>(0);
	private Set<FDatafilterrolelimit> FDatafilterrolelimits = new HashSet<FDatafilterrolelimit>(0);
	private Set<FovGridschemecolumn> fovGridschemecolumns = new HashSet<FovGridschemecolumn>(0);
	private Set<FovFormschemedetail> fovFormschemedetails = new HashSet<FovFormschemedetail>(0);
	private Set<FUserfieldlimit> FUserfieldlimits = new HashSet<FUserfieldlimit>(0);

	public FDataobjectfield() {
	}

	public FDataobjectfield(String fieldid) {
		this.fieldid = fieldid;
	}

	public String _getFieldCss() {
		if (_isManyToOne() || _isOneToOne())
			return "manytomanycolor";
		else if (_isDateField())
			return "datecolor";
		else if (this.fieldtype.equalsIgnoreCase("Boolean"))
			return "booleancolor";
		else if (_isNumberField())
			return "numbercolor";
		else
			return null;
	}

	@Transient
	public Boolean getIsDateField() {
		return _isDateField() ? true : null;
	}

	public Boolean _isDateField() {
		return fieldtype != null && (fieldtype.equalsIgnoreCase(FieldType.Date.toString())
				|| fieldtype.equalsIgnoreCase(FieldType.DateTime.toString())
				|| fieldtype.equalsIgnoreCase(FieldType.Timestamp.toString()));
	}

	@Transient
	public Boolean getIsNumberField() {
		return _isNumberField() ? true : null;
	}

	public Boolean _isPercentField() {
		return fieldtype.equalsIgnoreCase(FieldType.Percent.toString());

	}

	public Boolean _isNumberField() {
		return fieldtype != null && (fieldtype.equalsIgnoreCase(FieldType.Integer.toString())
				|| fieldtype.equalsIgnoreCase(FieldType.Double.toString())
				|| fieldtype.equalsIgnoreCase(FieldType.Float.toString())
				|| fieldtype.equalsIgnoreCase(FieldType.Percent.toString())
				|| fieldtype.equalsIgnoreCase(FieldType.Money.toString()));
	}

	@Transient
	public Boolean getIsBooleanField() {
		return _isBooleanField() ? true : null;
	}

	public Boolean _isBooleanField() {
		return fieldtype != null && fieldtype.equalsIgnoreCase(FieldType.Boolean.toString());

	}

	public String _getSelectName(String tableAsName) {
		if (tableAsName == null)
			tableAsName = "";
		else
			tableAsName = tableAsName + ".";
		if (fielddbname != null && fielddbname.length() > 0) {
			return tableAsName + fielddbname;
		} else if (fieldformula != null && fieldformula.length() > 0)
			return "(" + fieldformula.replaceAll("   ", " " + tableAsName) + ") ";
		else
			return tableAsName + fieldname;
	}

	public static final String MANYTOONE = "ManyToOne";
	public static final String ONETOONE = "OneToOne";
	public static final String ONETOMANY = "OneToMany";
	public static final String MANYTOMANY = "ManyToMany";

	@Transient
	public Map<String, Object> getManyToOneInfo() {
		if (!_isManyToOne())
			return null;
		Map<String, Object> result = new HashMap<String, Object>();
		FDataobject manytooneObject = DataObjectUtils.getDataObject(getFieldtype());
		if (manytooneObject == null)
			throw new RuntimeException("没有找到类型是：" + getFieldtype() + "的模块！");
		result.put("keyField", this.getFieldname() + "." + manytooneObject.getPrimarykey());
		result.put("keyOrginalField", manytooneObject._getPrimaryKeyField());
		result.put("nameField", this.getFieldname() + "." + manytooneObject.getNamefield());
		result.put("nameOrginalField", manytooneObject._getNameField());
		result.put("parentKey", manytooneObject.getParentkey());
		result.put("codeLevel", manytooneObject.getCodelevel());
		return result;
	}

	public boolean canLikeOperate() {
		String type = this.getFieldtype().toLowerCase();
		return type.equals("string");
	}

	@Transient
	public Boolean getIsBaseField() {
		return _isBaseField() ? true : null;
	}

	public Boolean _isBaseField() {
		return (!(_isManyToOne() || _isOneToOne() || _isOneToMany() || _isManyToMany()));
	}

	@Transient
	public Boolean getIsManyToOne() {
		return _isManyToOne() ? true : null;

	}

	public Boolean _isManyToOne() {
		return fieldrelation == null ? false : fieldrelation.equalsIgnoreCase(MANYTOONE);
	}

	@Transient
	public Boolean getIsOneToOne() {
		return _isOneToOne() ? true : null;

	}

	public Boolean _isOneToOne() {
		return fieldrelation == null ? false : fieldrelation.equalsIgnoreCase(ONETOONE);

	}

	@Transient
	public Boolean getIsOneToMany() {
		return _isOneToMany() ? true : null;

	}

	public Boolean _isOneToMany() {
		return fieldrelation == null ? false : fieldrelation.equalsIgnoreCase(ONETOMANY);
	}

	@Transient
	public Boolean getIsManyToMany() {
		return _isManyToMany() ? true : null;
	}

	public Boolean _isManyToMany() {
		return fieldrelation == null ? false : fieldrelation.equalsIgnoreCase(MANYTOMANY);
	}

	public Boolean _hasDivisior_Denominator() {
		return (divisor != null) && (denominator != null) && (divisor.length() > 0) && (denominator.length() > 0);
	}

	public String genSubModuleFieldahead(FDataobject module) throws Exception {

		String aa = this.getFieldtype();

		String subModuleName = aa.substring(aa.indexOf('<') + 1, aa.indexOf('>'));
		FDataobject subModule = DataObjectUtils.getDataObject(subModuleName);
		if (subModule == null)
			throw new Exception("在" + module.getObjectname() + "中没有找到OneToMany的" + aa + "模块名称！");

		FDataobjectfield subField = null;
		for (FDataobjectfield subFd : subModule.getFDataobjectfields()) {
			if (subFd._isManyToOne() && subFd.getFieldtype().equals(module.getObjectname())) {
				if ((subFd.getJoincolumnname() == null || subFd.getJoincolumnname().length() == 0)
						|| subFd.getJoincolumnname().equals(this.getJoincolumnname())) {

					subField = subFd;
					break;
				}
			}
		}
		if (subField == null)
			throw new Exception("在" + module.getObjectname() + "的OneToMany字段" + aa + "中没找找到其子模块中的ManyToOne字段！");
		return subModule.getObjectname() + ".with." + subField.getFieldname();

	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "fieldid", unique = true, nullable = false, length = 40)

	public String getFieldid() {
		return this.fieldid;
	}

	public void setFieldid(String fieldid) {
		this.fieldid = fieldid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "objectid", nullable = false)

	public FDataobject getFDataobject() {
		return this.FDataobject;
	}

	public void setFDataobject(FDataobject FDataobject) {
		this.FDataobject = FDataobject;
	}

	@Transient
	public String getFDictionaryid() {
		return getFDictionary() != null ? getFDictionary().getDictionaryid() : null;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "dictionaryid")
	public FDictionary getFDictionary() {
		return this.FDictionary;
	}

	public void setFDictionary(FDictionary FDictionary) {
		this.FDictionary = FDictionary;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "propertyid")
	public FObjectfieldproperty getFObjectfieldproperty() {
		return this.FObjectfieldproperty;
	}

	public void setFObjectfieldproperty(FObjectfieldproperty FObjectfieldproperty) {
		this.FObjectfieldproperty = FObjectfieldproperty;
	}

	@Column(name = "fieldname", nullable = false, length = 200)

	public String getFieldname() {
		return this.fieldname;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	@Column(name = "nativename", length = 2)
	public String getNativename() {
		return this.nativename;
	}

	public void setNativename(String nativename) {
		this.nativename = nativename;
	}

	@Column(name = "fieldtitle", nullable = false, length = 200)

	public String getFieldtitle() {
		return this.fieldtitle;
	}

	public void setFieldtitle(String fieldtitle) {
		this.fieldtitle = fieldtitle;
	}

	@Column(name = "fielddbname", length = 60)

	public String getFielddbname() {
		return this.fielddbname;
	}

	public void setFielddbname(String fielddbname) {
		this.fielddbname = fielddbname;
	}

	@Column(name = "orderno")

	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "fieldtype", nullable = false, length = 40)

	public String getFieldtype() {
		return this.fieldtype;
	}

	public void setFieldtype(String fieldtype) {
		this.fieldtype = fieldtype;
	}

	@Column(name = "fieldahead", length = 200)
	public String getFieldahead() {
		return this.fieldahead;
	}

	public void setFieldahead(String fieldahead) {
		this.fieldahead = fieldahead;
	}

	@Column(name = "fieldlen", nullable = false)

	public int getFieldlen() {
		return this.fieldlen;
	}

	public void setFieldlen(int fieldlen) {
		this.fieldlen = fieldlen;
	}

	@Column(name = "digitslen")

	public Integer getDigitslen() {
		return this.digitslen;
	}

	public void setDigitslen(Integer digitslen) {
		this.digitslen = digitslen;
	}

	@Column(name = "fieldgroup", nullable = false, length = 40)

	public String getFieldgroup() {
		return this.fieldgroup;
	}

	public void setFieldgroup(String fieldgroup) {
		this.fieldgroup = fieldgroup;
	}

	@Column(name = "fieldrelation", length = 40)

	public String getFieldrelation() {
		return this.fieldrelation;
	}

	public void setFieldrelation(String fieldrelation) {
		this.fieldrelation = fieldrelation;
	}

	@Column(name = "jointable", length = 40)

	public String getJointable() {
		return this.jointable;
	}

	public void setJointable(String jointable) {
		this.jointable = jointable;
	}

	@Column(name = "joincolumnname", length = 40)

	public String getJoincolumnname() {
		return this.joincolumnname;
	}

	public void setJoincolumnname(String joincolumnname) {
		this.joincolumnname = joincolumnname;
	}

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "additionfieldid")
	public FAdditionfield getFAdditionfield() {
		return this.FAdditionfield;
	}

	public void setFAdditionfield(FAdditionfield FAdditionfield) {
		this.FAdditionfield = FAdditionfield;
	}

	@Column(name = "fieldformula", length = 200)

	public String getFieldformula() {
		return this.fieldformula;
	}

	public void setFieldformula(String fieldformula) {
		this.fieldformula = fieldformula;
	}

	@Column(name = "isdisable")

	public Boolean getIsdisable() {
		return this.isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	@Column(name = "ishidden")

	public Boolean getIshidden() {
		return this.ishidden;
	}

	public void setIshidden(Boolean ishidden) {
		this.ishidden = ishidden;
	}

	@Column(name = "allownew")

	public Boolean getAllownew() {
		return this.allownew;
	}

	public void setAllownew(Boolean allownew) {
		this.allownew = allownew;
	}

	@Column(name = "allowedit")

	public Boolean getAllowedit() {
		return this.allowedit;
	}

	public void setAllowedit(Boolean allowedit) {
		this.allowedit = allowedit;
	}

	@Column(name = "allowgroup")

	public Boolean getAllowgroup() {
		return this.allowgroup;
	}

	public void setAllowgroup(Boolean allowgroup) {
		this.allowgroup = allowgroup;
	}

	@Column(name = "allowsummary")

	public Boolean getAllowsummary() {
		return this.allowsummary;
	}

	public void setAllowsummary(Boolean allowsummary) {
		this.allowsummary = allowsummary;
	}

	@Column(name = "allowaggregate")

	public Boolean getAllowaggregate() {
		return this.allowaggregate;
	}

	public void setAllowaggregate(Boolean allowaggregate) {
		this.allowaggregate = allowaggregate;
	}

	@Column(name = "shownavigatortree")

	public Boolean getShownavigatortree() {
		return this.shownavigatortree;
	}

	public void setShownavigatortree(Boolean shownavigatortree) {
		this.shownavigatortree = shownavigatortree;
	}

	@Column(name = "newneedselected")

	public Boolean getNewneedselected() {
		return this.newneedselected;
	}

	public void setNewneedselected(Boolean newneedselected) {
		this.newneedselected = newneedselected;
	}

	@Column(name = "ischartcategory")

	public Boolean getIschartcategory() {
		return this.ischartcategory;
	}

	public void setIschartcategory(Boolean ischartcategory) {
		this.ischartcategory = ischartcategory;
	}

	@Column(name = "ischartnumeric")

	public Boolean getIschartnumeric() {
		return this.ischartnumeric;
	}

	public void setIschartnumeric(Boolean ischartnumeric) {
		this.ischartnumeric = ischartnumeric;
	}

	@Column(name = "haveattachment")

	public Boolean getHaveattachment() {
		return this.haveattachment;
	}

	public void setHaveattachment(Boolean haveattachment) {
		this.haveattachment = haveattachment;
	}

	@Column(name = "ismonetary")

	public Boolean getIsmonetary() {
		return this.ismonetary;
	}

	public void setIsmonetary(Boolean ismonetary) {
		this.ismonetary = ismonetary;
	}

	@Column(name = "isrequired")

	public Boolean getIsrequired() {
		return this.isrequired;
	}

	public void setIsrequired(Boolean isrequired) {
		this.isrequired = isrequired;
	}

	@Column(name = "maxval", precision = 10, scale = 0)

	public Long getMaxval() {
		return this.maxval;
	}

	public void setMaxval(Long maxval) {
		this.maxval = maxval;
	}

	@Column(name = "minval", precision = 10, scale = 0)

	public Long getMinval() {
		return this.minval;
	}

	public void setMinval(Long minval) {
		this.minval = minval;
	}

	@Column(name = "monetarytype", length = 20)

	public String getMonetarytype() {
		return monetarytype;
	}

	public void setMonetarytype(String monetarytype) {
		this.monetarytype = monetarytype;
	}

	@Column(name = "regexvalue", length = 200)
	public String getRegexvalue() {
		return this.regexvalue;
	}

	public void setRegexvalue(String regexvalue) {
		this.regexvalue = regexvalue;
	}

	@Column(name = "regextext", length = 200)
	public String getRegextext() {
		return this.regextext;
	}

	public void setRegextext(String regextext) {
		this.regextext = regextext;
	}

	@Column(name = "vtype", length = 200)

	public String getVtype() {
		return this.vtype;
	}

	public void setVtype(String vtype) {
		this.vtype = vtype;
	}

	@Column(name = "jsvalue", length = 200)

	public String getJsvalue() {
		return this.jsvalue;
	}

	public void setJsvalue(String jsvalue) {
		this.jsvalue = jsvalue;
	}

	@Column(name = "unittext", length = 60)

	public String getUnittext() {
		return unittext;
	}

	public void setUnittext(String unittext) {
		this.unittext = unittext;
	}

	@Column(name = "defaultvalue", length = 60)

	public String getDefaultvalue() {
		return this.defaultvalue;
	}

	public void setDefaultvalue(String defaultvalue) {
		this.defaultvalue = defaultvalue;
	}

	@Column(name = "propertyvalue", length = 200)

	public String getPropertyvalue() {
		return this.propertyvalue;
	}

	public void setPropertyvalue(String propertyvalue) {
		this.propertyvalue = propertyvalue;
	}

	@Column(name = "divisor", length = 60)

	public String getDivisor() {
		return this.divisor;
	}

	public void setDivisor(String divisor) {
		this.divisor = divisor;
	}

	@Column(name = "denominator", length = 60)

	public String getDenominator() {
		return this.denominator;
	}

	public void setDenominator(String denominator) {
		this.denominator = denominator;
	}

	@Column(name = "tooltiptpl", length = 200)

	public String getTooltiptpl() {
		return this.tooltiptpl;
	}

	public void setTooltiptpl(String tooltiptpl) {
		this.tooltiptpl = tooltiptpl;
	}

	@Column(name = "othersetting", length = 200)

	public String getOthersetting() {
		return this.othersetting;
	}

	public void setOthersetting(String othersetting) {
		this.othersetting = othersetting;
	}

	@Column(name = "iconcls", length = 60)

	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "modelset", length = 200)

	public String getModelset() {
		return this.modelset;
	}

	public void setModelset(String modelset) {
		this.modelset = modelset;
	}

	@Column(name = "gridcolumnset", length = 200)
	public String getGridcolumnset() {
		return this.gridcolumnset;
	}

	public void setGridcolumnset(String gridcolumnset) {
		this.gridcolumnset = gridcolumnset;
	}

	@Column(name = "formfieldset", length = 200)
	public String getFormfieldset() {
		return this.formfieldset;
	}

	public void setFormfieldset(String formfieldset) {
		this.formfieldset = formfieldset;
	}

	@Column(name = "reportset", length = 200)
	public String getReportset() {
		return this.reportset;
	}

	public void setReportset(String reportset) {
		this.reportset = reportset;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
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

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FDataobjectfield")
	public Set<FRolefieldlimit> getFRolefieldlimits() {
		return this.FRolefieldlimits;
	}

	public void setFRolefieldlimits(Set<FRolefieldlimit> FRolefieldlimits) {
		this.FRolefieldlimits = FRolefieldlimits;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FDataobjectfield")
	public Set<FDatafilterrolelimit> getFDatafilterrolelimits() {
		return this.FDatafilterrolelimits;
	}

	public void setFDatafilterrolelimits(Set<FDatafilterrolelimit> FDatafilterrolelimits) {
		this.FDatafilterrolelimits = FDatafilterrolelimits;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FDataobjectfield")
	public Set<FovGridschemecolumn> getFovGridschemecolumns() {
		return this.fovGridschemecolumns;
	}

	public void setFovGridschemecolumns(Set<FovGridschemecolumn> fovGridschemecolumns) {
		this.fovGridschemecolumns = fovGridschemecolumns;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FDataobjectfield")
	public Set<FUserfieldlimit> getFUserfieldlimits() {
		return this.FUserfieldlimits;
	}

	public void setFUserfieldlimits(Set<FUserfieldlimit> FUserfieldlimits) {
		this.FUserfieldlimits = FUserfieldlimits;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FDataobjectfield")
	public Set<FovFormschemedetail> getFovFormschemedetails() {
		return fovFormschemedetails;
	}

	public void setFovFormschemedetails(Set<FovFormschemedetail> fovFormschemedetails) {
		this.fovFormschemedetails = fovFormschemedetails;
	}

}
