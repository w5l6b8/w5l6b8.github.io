package net.ebaolife.husqvarna.framework.dao.entity.system;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@DynamicUpdate
@Table(name = "f_systemfileattach")
@SuppressWarnings("serial")

public class FSystemfileattach implements java.io.Serializable {

	

	private String id;
	private String companyid;
	private String fileclass;
	private String title;
	private String keyword;
	private String moduleid;
	private String filetype;
	private String originalname;
	private String filesuffix;
	private Long filesize;
	private Timestamp uploadtime;
	private Long viewnum;
	private Integer downloadnum;
	private String remark;
	private String sourcetablename;
	private String sourcefieldname;
	private String sourcerecordkey;
	private String custom1;
	private String custom2;
	private String custom3;
	private String custom4;
	private String custom5;
	private Double custom6;
	private Double custom7;

	public FSystemfileattach() {
	}

	public FSystemfileattach(String companyid) {
		this.companyid = companyid;
	}

	public FSystemfileattach(String companyid, String fileclass, String title, String keyword, String moduleid,
			String filetype, String originalname, String filesuffix, Long filesize, Timestamp uploadtime, Long viewnum,
			Integer downloadnum, String remark, String sourcetablename, String sourcefieldname, String sourcerecordkey,
			String custom1, String custom2, String custom3, String custom4, String custom5, Double custom6,
			Double custom7) {
		this.companyid = companyid;
		this.fileclass = fileclass;
		this.title = title;
		this.keyword = keyword;
		this.moduleid = moduleid;
		this.filetype = filetype;
		this.originalname = originalname;
		this.filesuffix = filesuffix;
		this.filesize = filesize;
		this.uploadtime = uploadtime;
		this.viewnum = viewnum;
		this.downloadnum = downloadnum;
		this.remark = remark;
		this.sourcetablename = sourcetablename;
		this.sourcefieldname = sourcefieldname;
		this.sourcerecordkey = sourcerecordkey;
		this.custom1 = custom1;
		this.custom2 = custom2;
		this.custom3 = custom3;
		this.custom4 = custom4;
		this.custom5 = custom5;
		this.custom6 = custom6;
		this.custom7 = custom7;
	}

	
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "id", unique = true, nullable = false, length = 40)

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "companyid", nullable = false, length = 40)

	public String getCompanyid() {
		return this.companyid;
	}

	public void setCompanyid(String companyid) {
		this.companyid = companyid;
	}

	@Column(name = "fileclass", length = 2)

	public String getFileclass() {
		return this.fileclass;
	}

	public void setFileclass(String fileclass) {
		this.fileclass = fileclass;
	}

	@Column(name = "title", length = 200)

	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "keyword", length = 1000)

	public String getKeyword() {
		return this.keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Column(name = "moduleid", length = 40)

	public String getModuleid() {
		return this.moduleid;
	}

	public void setModuleid(String moduleid) {
		this.moduleid = moduleid;
	}

	@Column(name = "filetype", length = 2)

	public String getFiletype() {
		return this.filetype;
	}

	public void setFiletype(String filetype) {
		this.filetype = filetype;
	}

	@Column(name = "originalname", length = 200)

	public String getOriginalname() {
		return this.originalname;
	}

	public void setOriginalname(String originalname) {
		this.originalname = originalname;
	}

	@Column(name = "filesuffix", length = 20)

	public String getFilesuffix() {
		return this.filesuffix;
	}

	public void setFilesuffix(String filesuffix) {
		this.filesuffix = filesuffix;
	}

	@Column(name = "filesize", precision = 10, scale = 0)

	public Long getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Long filesize) {
		this.filesize = filesize;
	}

	@Column(name = "uploadtime", length = 19)

	public Timestamp getUploadtime() {
		return this.uploadtime;
	}

	public void setUploadtime(Timestamp uploadtime) {
		this.uploadtime = uploadtime;
	}

	@Column(name = "viewnum", precision = 10, scale = 0)

	public Long getViewnum() {
		return this.viewnum;
	}

	public void setViewnum(Long viewnum) {
		this.viewnum = viewnum;
	}

	@Column(name = "downloadnum")

	public Integer getDownloadnum() {
		return this.downloadnum;
	}

	public void setDownloadnum(Integer downloadnum) {
		this.downloadnum = downloadnum;
	}

	@Column(name = "remark", length = 2000)

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Column(name = "sourcetablename", length = 100)

	public String getSourcetablename() {
		return this.sourcetablename;
	}

	public void setSourcetablename(String sourcetablename) {
		this.sourcetablename = sourcetablename;
	}

	@Column(name = "sourcefieldname", length = 100)

	public String getSourcefieldname() {
		return this.sourcefieldname;
	}

	public void setSourcefieldname(String sourcefieldname) {
		this.sourcefieldname = sourcefieldname;
	}

	@Column(name = "sourcerecordkey", length = 100)

	public String getSourcerecordkey() {
		return this.sourcerecordkey;
	}

	public void setSourcerecordkey(String sourcerecordkey) {
		this.sourcerecordkey = sourcerecordkey;
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