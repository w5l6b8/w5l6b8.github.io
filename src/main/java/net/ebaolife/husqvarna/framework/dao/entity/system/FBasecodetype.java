package net.ebaolife.husqvarna.framework.dao.entity.system;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;


@Entity
@DynamicUpdate
@Table(name = "f_basecodetype", uniqueConstraints = @UniqueConstraint(columnNames = {
		"viewname", "companyid" }) )
@SuppressWarnings("serial")

public class FBasecodetype implements java.io.Serializable {

	

	private String codetype;
	private String viewname;
	private String coderemark;
	private Boolean isvalid;
	private String companyid;
	private String remark;
	private String custom1;
	private String custom2;
	private String custom3;
	private String custom4;
	private String custom5;
	private Double custom6;
	private Double custom7;
	private Set<FBasecode> FBasecodes = new HashSet<FBasecode>(0);

	public FBasecodetype() {
	}

	public FBasecodetype(String coderemark, Boolean isvalid) {
		this.coderemark = coderemark;
		this.isvalid = isvalid;
	}

	public FBasecodetype(String viewname, String coderemark, Boolean isvalid, String companyid, String remark,
			String custom1, String custom2, String custom3, String custom4, String custom5, Double custom6,
			Double custom7, Set<FBasecode> FBasecodes) {
		this.viewname = viewname;
		this.coderemark = coderemark;
		this.isvalid = isvalid;
		this.companyid = companyid;
		this.remark = remark;
		this.custom1 = custom1;
		this.custom2 = custom2;
		this.custom3 = custom3;
		this.custom4 = custom4;
		this.custom5 = custom5;
		this.custom6 = custom6;
		this.custom7 = custom7;
		this.FBasecodes = FBasecodes;
	}

	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "codetype", unique = true, nullable = false, length = 40)

	public String getCodetype() {
		return this.codetype;
	}

	public void setCodetype(String codetype) {
		this.codetype = codetype;
	}

	@Column(name = "viewname", length = 40)

	public String getViewname() {
		return this.viewname;
	}

	public void setViewname(String viewname) {
		this.viewname = viewname;
	}

	@Column(name = "coderemark", nullable = false, length = 40)

	public String getCoderemark() {
		return this.coderemark;
	}

	public void setCoderemark(String coderemark) {
		this.coderemark = coderemark;
	}

	@Column(name = "isvalid", nullable = false)

	public Boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
	}

	@Column(name = "companyid", length = 40)

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	@Column(name = "remark", length = 200)

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "custom1", length = 100)

	public String getCustom1() {
		return this.custom1;
	}

	public void setCustom1(String custom1) {
		this.custom1 = custom1;
	}

	@Column(name = "custom2", length = 100)

	public String getCustom2() {
		return this.custom2;
	}

	public void setCustom2(String custom2) {
		this.custom2 = custom2;
	}

	@Column(name = "custom3", length = 100)

	public String getCustom3() {
		return this.custom3;
	}

	public void setCustom3(String custom3) {
		this.custom3 = custom3;
	}

	@Column(name = "custom4", length = 100)

	public String getCustom4() {
		return this.custom4;
	}

	public void setCustom4(String custom4) {
		this.custom4 = custom4;
	}

	@Column(name = "custom5", length = 100)

	public String getCustom5() {
		return this.custom5;
	}

	public void setCustom5(String custom5) {
		this.custom5 = custom5;
	}

	@Column(name = "custom6", precision = 10)

	public Double getCustom6() {
		return this.custom6;
	}

	public void setCustom6(Double custom6) {
		this.custom6 = custom6;
	}

	@Column(name = "custom7", precision = 10)

	public Double getCustom7() {
		return this.custom7;
	}

	public void setCustom7(Double custom7) {
		this.custom7 = custom7;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FBasecodetype")

	public Set<FBasecode> getFBasecodes() {
		return this.FBasecodes;
	}

	public void setFBasecodes(Set<FBasecode> FBasecodes) {
		this.FBasecodes = FBasecodes;
	}

}