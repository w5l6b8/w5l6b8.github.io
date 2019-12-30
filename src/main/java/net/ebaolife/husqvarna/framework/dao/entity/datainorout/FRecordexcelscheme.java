package net.ebaolife.husqvarna.framework.dao.entity.datainorout;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name = "f_recordexcelscheme")
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
public class FRecordexcelscheme implements java.io.Serializable {

	private String schemeid;
	private FDataobject FDataobject;
	private String title;
	private Boolean issystem;
	private Integer orderno;
	private String iconurl;
	private String iconcls;
	private Boolean isdisable;
	private String stype;
	private String filename;
	private Integer filesize;
	private String author;
	private Date uploaddate;
	private byte[] filedata;
	private String othersetting;

	public FRecordexcelscheme() {
	}

	public FRecordexcelscheme(String schemeid, FDataobject FDataobject, String title) {
		this.schemeid = schemeid;
		this.FDataobject = FDataobject;
		this.title = title;
	}

	public FRecordexcelscheme(String schemeid, FDataobject FDataobject, String title, Boolean issystem, Integer orderno,
			String iconurl, String iconcls, Boolean isdisable, String stype, String filename, Integer filesize,
			String author, Date uploaddate, byte[] filedata, String othersetting) {
		this.schemeid = schemeid;
		this.FDataobject = FDataobject;
		this.title = title;
		this.issystem = issystem;
		this.orderno = orderno;
		this.iconurl = iconurl;
		this.iconcls = iconcls;
		this.isdisable = isdisable;
		this.stype = stype;
		this.filename = filename;
		this.filesize = filesize;
		this.author = author;
		this.uploaddate = uploaddate;
		this.filedata = filedata;
		this.othersetting = othersetting;
	}

	public JSONObject _getJsonData() {
		JSONObject result = new JSONObject();
		result.put("schemeid", schemeid);
		result.put("title", title);
		result.put("iconurl", iconurl);
		result.put("iconcls", iconcls);
		return result;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")

	@Column(name = "schemeid", unique = true, nullable = false, length = 40)
	public String getSchemeid() {
		return this.schemeid;
	}

	public void setSchemeid(String schemeid) {
		this.schemeid = schemeid;
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

	@Column(name = "issystem")
	public Boolean getIssystem() {
		return this.issystem;
	}

	public void setIssystem(Boolean issystem) {
		this.issystem = issystem;
	}

	@Column(name = "orderno")
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

	@Column(name = "iconurl", length = 200)
	public String getIconurl() {
		return this.iconurl;
	}

	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}

	@Column(name = "iconcls", length = 50)
	public String getIconcls() {
		return this.iconcls;
	}

	public void setIconcls(String iconcls) {
		this.iconcls = iconcls;
	}

	@Column(name = "isdisable")
	public Boolean getIsdisable() {
		return this.isdisable;
	}

	public void setIsdisable(Boolean isdisable) {
		this.isdisable = isdisable;
	}

	@Column(name = "stype", length = 20)
	public String getStype() {
		return this.stype;
	}

	public void setStype(String stype) {
		this.stype = stype;
	}

	@Column(name = "filename", length = 200)
	public String getFilename() {
		return this.filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	@Column(name = "filesize")
	public Integer getFilesize() {
		return this.filesize;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	@Column(name = "author", length = 40)
	public String getAuthor() {
		return this.author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "uploaddate", length = 19)
	public Date getUploaddate() {
		return this.uploaddate;
	}

	public void setUploaddate(Date uploaddate) {
		this.uploaddate = uploaddate;
	}

	@Column(name = "filedata")
	public byte[] getFiledata() {
		return this.filedata;
	}

	public void setFiledata(byte[] filedata) {
		this.filedata = filedata;
	}

	@Column(name = "othersetting", length = 200)
	public String getOthersetting() {
		return this.othersetting;
	}

	public void setOthersetting(String othersetting) {
		this.othersetting = othersetting;
	}

}
