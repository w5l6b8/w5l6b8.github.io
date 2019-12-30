package net.ebaolife.husqvarna.framework.dao.entity.dataobject;

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

@Entity
@DynamicUpdate
@Table(name = "f_dataobjectviewdetail")
public class FDataobjectviewdetail implements java.io.Serializable {

	private static final long serialVersionUID = 1462104008929293143L;
	private String viewdetailid;
	private FDataobjectcondition FDataobjectcondition;
	private FDataobjectview FDataobjectview;
	private Integer orderno;

	public FDataobjectviewdetail() {
	}

	public FDataobjectviewdetail(String viewdetailid, FDataobjectcondition FDataobjectcondition,
			FDataobjectview FDataobjectview) {
		this.viewdetailid = viewdetailid;
		this.FDataobjectcondition = FDataobjectcondition;
		this.FDataobjectview = FDataobjectview;
	}

	public FDataobjectviewdetail(String viewdetailid, FDataobjectcondition FDataobjectcondition,
			FDataobjectview FDataobjectview, Integer orderno) {
		this.viewdetailid = viewdetailid;
		this.FDataobjectcondition = FDataobjectcondition;
		this.FDataobjectview = FDataobjectview;
		this.orderno = orderno;
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "viewdetailid", unique = true, nullable = false, length = 40)
	public String getViewdetailid() {
		return this.viewdetailid;
	}

	public void setViewdetailid(String viewdetailid) {
		this.viewdetailid = viewdetailid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "conditionid", nullable = false)
	public FDataobjectcondition getFDataobjectcondition() {
		return this.FDataobjectcondition;
	}

	public void setFDataobjectcondition(FDataobjectcondition FDataobjectcondition) {
		this.FDataobjectcondition = FDataobjectcondition;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "viewschemeid", nullable = false)
	public FDataobjectview getFDataobjectview() {
		return this.FDataobjectview;
	}

	public void setFDataobjectview(FDataobjectview FDataobjectview) {
		this.FDataobjectview = FDataobjectview;
	}

	@Column(name = "orderno")
	public Integer getOrderno() {
		return this.orderno;
	}

	public void setOrderno(Integer orderno) {
		this.orderno = orderno;
	}

}
