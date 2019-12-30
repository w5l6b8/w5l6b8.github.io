package net.ebaolife.husqvarna.framework.dao.entity.dictionary;

import com.alibaba.fastjson.JSONObject;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridnavigateschemedetail;
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

@Entity
@DynamicUpdate
@Table(name = "f_numbergroup", uniqueConstraints = @UniqueConstraint(columnNames = "title"))
public class FNumbergroup implements java.io.Serializable {

	private static final long serialVersionUID = 2739399643536106231L;
	private String numbergroupid;
	private String title;
	private String remark;
	private Set<FNumbergroupdetail> FNumbergroupdetails = new HashSet<FNumbergroupdetail>(0);
	private Set<FovGridnavigateschemedetail> fovGridnavigateschemedetails = new HashSet<FovGridnavigateschemedetail>(0);

	public FNumbergroup() {
	}

	public String genExpression(String fieldname) {
		StringBuilder sb = new StringBuilder();
		sb.append("( case");
		for (FNumbergroupdetail detail : getFNumbergroupdetails()) {
			sb.append(" when (" + detail.genExpression(fieldname) + ") then " + (detail.getOrderno() + 1000));
			sb.append("\r\n");
		}
		sb.append(" end )");
		return sb.toString();
	}

	public String getDetailTitle(Integer orderId) {
		for (FNumbergroupdetail detail : getFNumbergroupdetails()) {
			if (detail.getOrderno().equals(orderId))
				return detail.getTitle();
		}
		return "未定义";
	}

	public JSONObject genJsonObject() {
		JSONObject object = new JSONObject();
		object.put("title", title);
		object.put("numbergroupid", numbergroupid);
		object.put("remark", remark);
		return object;

	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "numbergroupid", unique = true, nullable = false, length = 40)
	public String getNumbergroupid() {
		return this.numbergroupid;
	}

	public void setNumbergroupid(String numbergroupid) {
		this.numbergroupid = numbergroupid;
	}

	@Column(name = "title", unique = true, nullable = false, length = 50)
	public String getTitle() {
		return this.title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	@Column(name = "remark", length = 200)
	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FNumbergroup")
	public Set<FNumbergroupdetail> getFNumbergroupdetails() {
		return this.FNumbergroupdetails;
	}

	public void setFNumbergroupdetails(Set<FNumbergroupdetail> FNumbergroupdetails) {
		this.FNumbergroupdetails = FNumbergroupdetails;
	}

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "FNumbergroup")
	public Set<FovGridnavigateschemedetail> getFovGridnavigateschemedetails() {
		return this.fovGridnavigateschemedetails;
	}

	public void setFovGridnavigateschemedetails(Set<FovGridnavigateschemedetail> fovGridnavigateschemedetails) {
		this.fovGridnavigateschemedetails = fovGridnavigateschemedetails;
	}

}
