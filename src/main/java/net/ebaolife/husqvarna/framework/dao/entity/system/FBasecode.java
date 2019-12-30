package net.ebaolife.husqvarna.framework.dao.entity.system;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;


@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name = "f_basecode")

public class FBasecode implements java.io.Serializable {

	

	private String id;
	private FBasecodetype FBasecodetype;
	private String codecode;
	private String codename;
	private String codevalue;
	private Boolean isvalid;
	private String custom1;
	private String custom2;
	private String custom3;
	private String custom4;
	private String custom5;
	private Double custom6;
	private Double custom7;

	public FBasecode() {
	}

	public FBasecode(FBasecodetype FBasecodetype, String codecode, String codename, Boolean isvalid) {
		this.FBasecodetype = FBasecodetype;
		this.codecode = codecode;
		this.codename = codename;
		this.isvalid = isvalid;
	}

	public FBasecode(FBasecodetype FBasecodetype, String codecode, String codename, String codevalue, Boolean isvalid,
			String custom1, String custom2, String custom3, String custom4, String custom5, Double custom6,
			Double custom7) {
		this.FBasecodetype = FBasecodetype;
		this.codecode = codecode;
		this.codename = codename;
		this.codevalue = codevalue;
		this.isvalid = isvalid;
		this.custom1 = custom1;
		this.custom2 = custom2;
		this.custom3 = custom3;
		this.custom4 = custom4;
		this.custom5 = custom5;
		this.custom6 = custom6;
		this.custom7 = custom7;
	}

	
	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "id", unique = true, nullable = false, length = 40)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "codetype", nullable = false)

	public FBasecodetype getFBasecodetype() {
		return this.FBasecodetype;
	}

	public void setFBasecodetype(FBasecodetype FBasecodetype) {
		this.FBasecodetype = FBasecodetype;
	}

	@Column(name = "codecode", nullable = false, length = 40)

	public String getCodecode() {
		return this.codecode;
	}

	public void setCodecode(String codecode) {
		this.codecode = codecode;
	}

	@Column(name = "codename", nullable = false, length = 200)

	public String getCodename() {
		return this.codename;
	}

	public void setCodename(String codename) {
		this.codename = codename;
	}

	@Column(name = "codevalue", length = 500)

	public String getCodevalue() {
		return this.codevalue;
	}

	public void setCodevalue(String codevalue) {
		this.codevalue = codevalue;
	}

	@Column(name = "isvalid", nullable = false)

	public Boolean getIsvalid() {
		return this.isvalid;
	}

	public void setIsvalid(Boolean isvalid) {
		this.isvalid = isvalid;
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

}