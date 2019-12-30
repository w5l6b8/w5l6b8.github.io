package net.ebaolife.husqvarna.framework.dao.entity.limit;

import net.ebaolife.husqvarna.framework.dao.entity.module.FModulefunction;
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
@Table(name = "f_rolefunctionlimit")

public class FRolefunctionlimit implements java.io.Serializable {

	private String limitid;
	private net.ebaolife.husqvarna.framework.dao.entity.module.FModulefunction FModulefunction;
	private FRole FRole;
	private String type;

	public FRolefunctionlimit() {
	}

	public FRolefunctionlimit(FModulefunction FModulefunction, FRole FRole) {
		this.FModulefunction = FModulefunction;
		this.FRole = FRole;
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "limitid", unique = true, nullable = false, length = 40)

	public String getLimitid() {
		return this.limitid;
	}

	public void setLimitid(String limitid) {
		this.limitid = limitid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "functionid", nullable = false)

	public FModulefunction getFModulefunction() {
		return this.FModulefunction;
	}

	public void setFModulefunction(FModulefunction FModulefunction) {
		this.FModulefunction = FModulefunction;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid", nullable = false)

	public FRole getFRole() {
		return this.FRole;
	}

	public void setFRole(FRole FRole) {
		this.FRole = FRole;
	}

	@Column(name = "type", length = 2)

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

}
