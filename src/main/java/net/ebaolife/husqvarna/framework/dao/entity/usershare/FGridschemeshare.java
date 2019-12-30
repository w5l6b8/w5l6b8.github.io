package net.ebaolife.husqvarna.framework.dao.entity.usershare;

import net.ebaolife.husqvarna.framework.dao.entity.limit.FRole;
import net.ebaolife.husqvarna.framework.dao.entity.system.FOrganization;
import net.ebaolife.husqvarna.framework.dao.entity.system.FPersonnel;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridscheme;
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
@Table(name = "f_gridschemeshare")
@SuppressWarnings("serial")

public class FGridschemeshare implements java.io.Serializable {

	private String gridshareid;
	private FovGridscheme fovGridscheme;
	private FPersonnel FPersonnel;
	private FUser FUser;
	private FRole FRole;
	private FOrganization FOrganization;

	public FGridschemeshare() {
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")

	@Column(name = "gridshareid", unique = true, nullable = false, length = 40)

	public String getGridshareid() {
		return this.gridshareid;
	}

	public void setGridshareid(String gridshareid) {
		this.gridshareid = gridshareid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gridschemeid", nullable = false)

	public FovGridscheme getFovGridscheme() {
		return this.fovGridscheme;
	}

	public void setFovGridscheme(FovGridscheme fovGridscheme) {
		this.fovGridscheme = fovGridscheme;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personnelid", nullable = false)

	public FPersonnel getFPersonnel() {
		return this.FPersonnel;
	}

	public void setFPersonnel(FPersonnel FPersonnel) {
		this.FPersonnel = FPersonnel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", nullable = false)

	public FUser getFUser() {
		return this.FUser;
	}

	public void setFUser(FUser FUser) {
		this.FUser = FUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid", nullable = false)

	public FRole getFRole() {
		return this.FRole;
	}

	public void setFRole(FRole FRole) {
		this.FRole = FRole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgid", nullable = false)

	public FOrganization getFOrganization() {
		return this.FOrganization;
	}

	public void setFOrganization(FOrganization FOrganization) {
		this.FOrganization = FOrganization;
	}

}