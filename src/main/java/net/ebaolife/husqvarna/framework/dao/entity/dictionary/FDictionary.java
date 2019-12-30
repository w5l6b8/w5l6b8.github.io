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

@Entity
@DynamicUpdate
@Table(name = "f_dictionary")
@SuppressWarnings("serial")

public class FDictionary implements java.io.Serializable {

	public static final String NAMEENDS = "_dictname";

	private String dictionaryid;
	private FDictionarygroup FDictionarygroup;
	private String dcode;
	private String title;
	private Boolean islinkedkey;
	private Boolean islinkedorderno;
	private Boolean islinkedcode;
	private Boolean islinkedtext;
	private Boolean isdisplayorderno;
	private Boolean isdisplaycode;
	private String inputmethod;
	private Boolean disablecolumnlist;
	private Boolean columnsingle;
	private Boolean disabled;
	private String iconcls;
	private String remark;
	private Set<FDictionarydetail> FDictionarydetails = new HashSet<FDictionarydetail>(0);
	private Set<FDataobjectfield> FDataobjectfields = new HashSet<FDataobjectfield>(0);

	public FDictionary() {
	}

	public String _getDetailEqualsKey() {
		return this.getIslinkedkey() ? FDictionarydetail.ID
				: (this.getIslinkedcode() ? FDictionarydetail.CODE
						: (this.getIslinkedorderno() ? FDictionarydetail.ORDERNO : FDictionarydetail.TITLE));
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "dictionaryid", unique = true, nullable = false, length = 40)
	public String getDictionaryid() {
		return this.dictionaryid;
	}

	public void setDictionaryid(String dictionaryid) {
		this.dictionaryid = dictionaryid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "groupid", nullable = false)
	public FDictionarygroup getFDictionarygroup() {
		return this.FDictionarygroup;
	}

	public void setFDictionarygroup(FDictionarygroup FDictionarygroup) {
		this.FDictionarygroup = FDictionarygroup;
	}

	@Column(name = "dcode", nullable = false, length = 10)
	public String getDcode() {
		return this.dcode;
	}

	public void setDcode(String dcode) {
		this.dcode = dcode;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "islinkedkey")
	public Boolean getIslinkedkey() {
		return this.islinkedkey == null ? false : this.islinkedkey;
	}

	public void setIslinkedkey(Boolean islinkedkey) {
		this.islinkedkey = islinkedkey;
	}

	@Column(name = "islinkedorderno")
	public Boolean getIslinkedorderno() {
		return this.islinkedorderno == null ? false : this.islinkedorderno;
	}

	public void setIslinkedorderno(Boolean islinkedorderno) {
		this.islinkedorderno = islinkedorderno;
	}

	@Column(name = "islinkedcode")
	public Boolean getIslinkedcode() {
		return this.islinkedcode == null ? false : this.islinkedcode;
	}

	public void setIslinkedcode(Boolean islinkedcode) {
		this.islinkedcode = islinkedcode;
	}

	@Column(name = "islinkedtext")
	public Boolean getIslinkedtext() {
		return this.islinkedtext == null ? false : this.islinkedtext;
	}

	public void setIslinkedtext(Boolean islinkedtext) {
		this.islinkedtext = islinkedtext;
	}

	@Column(name = "isdisplayorderno")
	public Boolean getIsdisplayorderno() {
		return this.isdisplayorderno == null ? false : this.isdisplayorderno;
	}

	public void setIsdisplayorderno(Boolean isdisplayorderno) {
		this.isdisplayorderno = isdisplayorderno;
	}

	@Column(name = "isdisplaycode")
	public Boolean getIsdisplaycode() {
		return this.isdisplaycode == null ? false : this.isdisplaycode;
	}

	public void setIsdisplaycode(Boolean isdisplaycode) {
		this.isdisplaycode = isdisplaycode;
	}

	@Column(name = "inputmethod", length = 10)
	public String getInputmethod() {
		return this.inputmethod;
	}

	public void setInputmethod(String inputmethod) {
		this.inputmethod = inputmethod;
	}

	public Boolean getDisablecolumnlist() {
		return disablecolumnlist == null ? false : disablecolumnlist;
	}

	public void setDisablecolumnlist(Boolean disablecolumnlist) {
		this.disablecolumnlist = disablecolumnlist;
	}

	public Boolean getColumnsingle() {
		return columnsingle == null ? false : columnsingle;
	}

	public void setColumnsingle(Boolean columnsingle) {
		this.columnsingle = columnsingle;
	}

	@Column(name = "disabled")
	public Boolean getDisabled() {
		return this.disabled == null ? false : this.disabled;
	}

	public void setDisabled(Boolean disabled) {
		this.disabled = disabled;
	}

	@Column(name = "iconcls", length = 50)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDictionary")
	@OrderBy("orderno")
	public Set<FDictionarydetail> getFDictionarydetails() {
		return this.FDictionarydetails;
	}

	public void setFDictionarydetails(Set<FDictionarydetail> FDictionarydetails) {
		this.FDictionarydetails = FDictionarydetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDictionary")
	public Set<FDataobjectfield> getFDataobjectfields() {
		return this.FDataobjectfields;
	}

	public void setFDataobjectfields(Set<FDataobjectfield> FDataobjectfields) {
		this.FDataobjectfields = FDataobjectfields;
	}

}
