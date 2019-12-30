package net.ebaolife.husqvarna.framework.dao.entity.datamining;

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
@Table(name = "f_dataminingcolumngroup")
public class FDataminingcolumngroup implements java.io.Serializable {

	private String columngroupid;
	private FDataminingcolumngroup FDataminingcolumngroup;
	private FDataminingscheme FDataminingscheme;
	private int orderno;
	private String title;
	private String groupcondition;
	private String othersetting;
	private String remark;
	private Set<FDataminingcolumngroup> FDataminingcolumngroups = new HashSet<FDataminingcolumngroup>(0);

	public FDataminingcolumngroup() {
	}

	public FDataminingcolumngroup(String columngroupid, int orderno, String title) {
		this.columngroupid = columngroupid;
		this.orderno = orderno;
		this.title = title;
	}

	public FDataminingcolumngroup(String columngroupid, FDataminingcolumngroup FDataminingcolumngroup,
			FDataminingscheme FDataminingscheme, int orderno, String title, String groupcondition, String othersetting,
			String remark, Set<FDataminingcolumngroup> FDataminingcolumngroups) {
		this.columngroupid = columngroupid;
		this.FDataminingcolumngroup = FDataminingcolumngroup;
		this.FDataminingscheme = FDataminingscheme;
		this.orderno = orderno;
		this.title = title;
		this.groupcondition = groupcondition;
		this.othersetting = othersetting;
		this.remark = remark;
		this.FDataminingcolumngroups = FDataminingcolumngroups;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "columngroupid", unique = true, nullable = false, length = 40)
	public String getColumngroupid() {
		return this.columngroupid;
	}

	public void setColumngroupid(String columngroupid) {
		this.columngroupid = columngroupid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parentid")
	public FDataminingcolumngroup getFDataminingcolumngroup() {
		return this.FDataminingcolumngroup;
	}

	public void setFDataminingcolumngroup(FDataminingcolumngroup FDataminingcolumngroup) {
		this.FDataminingcolumngroup = FDataminingcolumngroup;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schemeid")
	public FDataminingscheme getFDataminingscheme() {
		return this.FDataminingscheme;
	}

	public void setFDataminingscheme(FDataminingscheme FDataminingscheme) {
		this.FDataminingscheme = FDataminingscheme;
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

	@Column(name = "groupcondition", length = 65535)
	public String getGroupcondition() {
		return this.groupcondition;
	}

	public void setGroupcondition(String groupcondition) {
		this.groupcondition = groupcondition;
	}

	@Column(name = "othersetting", length = 200)
	public String getOthersetting() {
		return this.othersetting;
	}

	public void setOthersetting(String othersetting) {
		this.othersetting = othersetting;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FDataminingcolumngroup")
	@OrderBy("orderno")
	public Set<FDataminingcolumngroup> getFDataminingcolumngroups() {
		return this.FDataminingcolumngroups;
	}

	public void setFDataminingcolumngroups(Set<FDataminingcolumngroup> FDataminingcolumngroups) {
		this.FDataminingcolumngroups = FDataminingcolumngroups;
	}

}
