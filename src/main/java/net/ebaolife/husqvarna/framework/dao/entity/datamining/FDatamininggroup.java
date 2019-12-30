package net.ebaolife.husqvarna.framework.dao.entity.datamining;

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
@Table(name = "f_datamininggroup", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class FDatamininggroup implements java.io.Serializable {

	private String groupid;
	private String title;
	private Integer orderno;
	private String iconcls;
	private String remark;
	private Set<FDataminingscheme> FDataminingschemes = new HashSet<FDataminingscheme>(0);

	public FDatamininggroup() {
	}

	public FDatamininggroup(String groupid, String title) {
		this.groupid = groupid;
		this.title = title;
	}

	public FDatamininggroup(String groupid, String title, Integer orderno, String iconcls, String remark,
			Set<FDataminingscheme> FDataminingschemes) {
		this.groupid = groupid;
		this.title = title;
		this.orderno = orderno;
		this.iconcls = iconcls;
		this.remark = remark;
		this.FDataminingschemes = FDataminingschemes;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "groupid", unique = true, nullable = false, length = 40)
	public String getGroupid() {
		return this.groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	@Column(name = "title", unique = true, nullable = false, length = 50)
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

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDatamininggroup")
	@OrderBy("orderno")
	public Set<FDataminingscheme> getFDataminingschemes() {
		return this.FDataminingschemes;
	}

	public void setFDataminingschemes(Set<FDataminingscheme> FDataminingschemes) {
		this.FDataminingschemes = FDataminingschemes;
	}

}
