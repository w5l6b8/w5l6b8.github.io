package net.ebaolife.husqvarna.framework.dao.entity.viewsetting;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.usershare.FGridschemeshare;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
@Table(name = "fov_gridscheme", uniqueConstraints = @UniqueConstraint(columnNames = { "objectid", "schemename" }))

public class FovGridscheme implements java.io.Serializable {

	private static final long serialVersionUID = -4539503187105108133L;
	private String gridschemeid;
	private FDataobject FDataobject;
	private FUser FUser;
	private String schemename;
	private Integer orderno;
	private String selectionmode;
	private String dblclickaction;
	private String expandaction;
	private Boolean disablegrouped;
	private String othersetting;
	private Boolean isshare;
	private Boolean isshareowner;
	private String remark;
	private Set<FGridschemeshare> FGridschemeshares = new HashSet<FGridschemeshare>(0);
	private List<FovGridschemecolumn> columns;
	private Set<FovUserdefaultgridscheme> fovUserdefaultgridschemes = new HashSet<FovUserdefaultgridscheme>(0);

	public FovGridscheme() {
	}

	public List<FovGridschemecolumn> _getFields() {
		List<FovGridschemecolumn> result = new ArrayList<FovGridschemecolumn>();
		addFieldsToResult(this.getColumns(), result);
		return result;
	}

	private void addFieldsToResult(List<FovGridschemecolumn> columns, List<FovGridschemecolumn> result) {
		for (FovGridschemecolumn column : columns) {
			if (column.getFDataobjectfield() != null) {
				result.add(column);
			} else
				addFieldsToResult(column.getColumns(), result);
		}
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "gridschemeid", unique = true, nullable = false, length = 40)

	public String getGridschemeid() {
		return this.gridschemeid;
	}

	public void setGridschemeid(String gridschemeid) {
		this.gridschemeid = gridschemeid;
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

	@Column(name = "selectionmode", length = 10)

	public String getSelectionmode() {
		return this.selectionmode;
	}

	public void setSelectionmode(String selectionmode) {
		this.selectionmode = selectionmode;
	}

	@Column(name = "dblclickaction", length = 10)

	public String getDblclickaction() {
		return this.dblclickaction;
	}

	public void setDblclickaction(String dblclickaction) {
		this.dblclickaction = dblclickaction;
	}

	@Column(name = "expandaction", length = 10)

	public String getExpandaction() {
		return this.expandaction;
	}

	public void setExpandaction(String expandaction) {
		this.expandaction = expandaction;
	}

	@Column(name = "disablegrouped")

	public Boolean getDisablegrouped() {
		return this.disablegrouped;
	}

	public void setDisablegrouped(Boolean disablegrouped) {
		this.disablegrouped = disablegrouped;
	}

	@Column(name = "othersetting", length = 200)

	public String getOthersetting() {
		return this.othersetting;
	}

	public void setOthersetting(String othersetting) {
		this.othersetting = othersetting;
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

	@Column(name = "remark", length = 200)

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fovGridscheme")

	public Set<FGridschemeshare> getFGridschemeshares() {
		return this.FGridschemeshares;
	}

	public void setFGridschemeshares(Set<FGridschemeshare> FGridschemeshares) {
		this.FGridschemeshares = FGridschemeshares;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fovGridscheme")
	@OrderBy
	public List<FovGridschemecolumn> getColumns() {
		return this.columns;
	}

	public void setColumns(List<FovGridschemecolumn> columns) {
		this.columns = columns;
	}

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "fovGridscheme")

	public Set<FovUserdefaultgridscheme> getFovUserdefaultgridschemes() {
		return this.fovUserdefaultgridschemes;
	}

	public void setFovUserdefaultgridschemes(Set<FovUserdefaultgridscheme> fovUserdefaultgridschemes) {
		this.fovUserdefaultgridschemes = fovUserdefaultgridschemes;
	}

}
