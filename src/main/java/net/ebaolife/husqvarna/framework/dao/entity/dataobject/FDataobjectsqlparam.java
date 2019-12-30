package net.ebaolife.husqvarna.framework.dao.entity.dataobject;

import net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary;
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
@Table(name = "f_dataobjectsqlparam", uniqueConstraints = { @UniqueConstraint(columnNames = { "objectid", "title" }),
		@UniqueConstraint(columnNames = { "objectid", "paramname" }) })
public class FDataobjectsqlparam implements java.io.Serializable {

	private String paramid;
	private FDataobject FDataobject;
	private FDataobject FDataobjectByParamobjectid;
	private net.ebaolife.husqvarna.framework.dao.entity.dictionary.FDictionary FDictionary;
	private int orderno;
	private String title;
	private String paramname;
	private String paramtype;
	private Integer paramlen;
	private String formatter;
	private String paramvalues;
	private Integer width;
	private Integer flex;
	private String remark;

	public FDataobjectsqlparam() {
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "paramid", unique = true, nullable = false, length = 40)
	public String getParamid() {
		return this.paramid;
	}

	public void setParamid(String paramid) {
		this.paramid = paramid;
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
	@JoinColumn(name = "paramobjectid")
	public FDataobject getFDataobjectByParamobjectid() {
		return this.FDataobjectByParamobjectid;
	}

	public void setFDataobjectByParamobjectid(FDataobject FDataobjectByParamobjectid) {
		this.FDataobjectByParamobjectid = FDataobjectByParamobjectid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dictionaryid")
	public FDictionary getFDictionary() {
		return this.FDictionary;
	}

	public void setFDictionary(FDictionary FDictionary) {
		this.FDictionary = FDictionary;
	}

	@Column(name = "orderno", nullable = false)
	public int getOrderno() {
		return this.orderno;
	}

	public void setOrderno(int orderno) {
		this.orderno = orderno;
	}

	@Column(name = "title", nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "paramname", nullable = false, length = 30)
	public String getParamname() {
		return this.paramname;
	}

	public void setParamname(String paramname) {
		this.paramname = paramname;
	}

	@Column(name = "paramtype", nullable = false, length = 30)
	public String getParamtype() {
		return this.paramtype;
	}

	public void setParamtype(String paramtype) {
		this.paramtype = paramtype;
	}

	@Column(name = "paramlen")
	public Integer getParamlen() {
		return this.paramlen;
	}

	public void setParamlen(Integer paramlen) {
		this.paramlen = paramlen;
	}

	@Column(name = "formatter", length = 100)
	public String getFormatter() {
		return this.formatter;
	}

	public void setFormatter(String formatter) {
		this.formatter = formatter;
	}

	@Column(name = "paramvalues", length = 65535)
	public String getParamvalues() {
		return this.paramvalues;
	}

	public void setParamvalues(String paramvalues) {
		this.paramvalues = paramvalues;
	}

	@Column(name = "width")
	public Integer getWidth() {
		return this.width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	@Column(name = "flex")
	public Integer getFlex() {
		return this.flex;
	}

	public void setFlex(Integer flex) {
		this.flex = flex;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

}
