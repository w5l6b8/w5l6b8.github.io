package net.ebaolife.husqvarna.framework.dao.entity.viewsetting;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
@Table(name = "fov_formscheme", uniqueConstraints = @UniqueConstraint(columnNames = { "objectid", "userid",
		"schemename" }))

public class FovFormscheme implements java.io.Serializable {

	private static final long serialVersionUID = -7017184930674484494L;
	private String formschemeid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject FDataobject;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FUser FUser;
	private String schemename;
	private Integer orderno;
	private String operatetype;
	private String formtype;
	private String layout;
	private Integer height;
	private Integer width;
	private Integer cols;
	private String widths;
	private String othersetting;
	private String newonlastfieldenter;
	private String buttonsposition;
	private Boolean isshare;
	private Boolean isshareowner;
	private String customform;
	private String remark;
	private List<FovFormschemedetail> details;

	private String objectid;

	public FovFormscheme() {
	}

	public FovFormscheme(FDataobject FDataobject, String schemename) {
		this.FDataobject = FDataobject;
		this.schemename = schemename;
	}

	public List<FovFormschemedetail> _getFields() {
		List<FovFormschemedetail> result = new ArrayList<FovFormschemedetail>();
		addFieldsToResult(this.getDetails(), result);
		return result;
	}

	private void addFieldsToResult(List<FovFormschemedetail> columns, List<FovFormschemedetail> result) {
		if (columns != null) {
			for (FovFormschemedetail column : columns) {
				if (column.getFDataobjectfield() != null) {
					result.add(column);
				} else
					addFieldsToResult(column.getDetails(), result);
			}
		}
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "formschemeid", unique = true, nullable = false, length = 40)

	public String getFormschemeid() {
		return this.formschemeid;
	}

	public void setFormschemeid(String formschemeid) {
		this.formschemeid = formschemeid;
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
	@JoinColumn(name = "userid")

	public FUser getFUser() {
		return this.FUser;
	}

	public void setFUser(FUser FUser) {
		this.FUser = FUser;
	}

	@Column(name = "schemename", nullable = false, length = 50)

	public String getSchemename() {
		return this.schemename;
	}

	public void setSchemename(String schemename) {
		this.schemename = schemename;
	}

	@Column(name = "orderno")

	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "operatetype", length = 10)

	public String getOperatetype() {
		return this.operatetype;
	}

	public void setOperatetype(String operatetype) {
		this.operatetype = operatetype;
	}

	@Column(name = "formtype", length = 10)

	public String getFormtype() {
		return this.formtype;
	}

	public void setFormtype(String formtype) {
		this.formtype = formtype;
	}

	@Column(name = "layout", length = 50)
	public String getLayout() {
		return this.layout;
	}

	public void setLayout(String layout) {
		this.layout = layout;
	}

	@Column(name = "height")

	public Integer getHeight() {
		return this.height;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	@Column(name = "width")

	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name = "cols")

	public Integer getCols() {
		return this.cols;
	}

	public void setCols(Integer cols) {
		this.cols = cols;
	}

	@Column(name = "widths", length = 200)

	public String getWidths() {
		return this.widths;
	}

	public void setWidths(String widths) {
		this.widths = widths;
	}

	@Column(name = "othersetting", length = 200)

	public String getOthersetting() {
		return this.othersetting;
	}

	public void setOthersetting(String othersetting) {
		this.othersetting = othersetting;
	}

	@Column(name = "newonlastfieldenter", length = 10)

	public String getNewonlastfieldenter() {
		return this.newonlastfieldenter;
	}

	public void setNewonlastfieldenter(String newonlastfieldenter) {
		this.newonlastfieldenter = newonlastfieldenter;
	}

	@Column(name = "buttonsposition", length = 10)

	public String getButtonsposition() {
		return this.buttonsposition;
	}

	public void setButtonsposition(String buttonsposition) {
		this.buttonsposition = buttonsposition;
	}

	@Column(name = "isshare")

	public Boolean getIsshare() {
		return this.isshare;
	}

	public void setIsshare(Boolean isshare) {
		this.isshare = isshare;
	}

	@Column(name = "isshareowner")

	public Boolean getIsshareowner() {
		return this.isshareowner;
	}

	public void setIsshareowner(Boolean isshareowner) {
		this.isshareowner = isshareowner;
	}

	@Column(name = "customform", length = 100)
	public String getCustomform() {
		return this.customform;
	}

	public void setCustomform(String customform) {
		this.customform = customform;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fovFormscheme")
	@OrderBy("orderno")
	public List<FovFormschemedetail> getDetails() {
		return this.details;
	}

	public void setDetails(List<FovFormschemedetail> details) {
		this.details = details;
	}

	@Column(updatable = false, insertable = false)
	public String getObjectid() {
		return objectid;
	}

	public void setObjectid(String objectid) {
		this.objectid = objectid;
	}
}
