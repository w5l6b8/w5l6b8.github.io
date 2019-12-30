package net.ebaolife.husqvarna.framework.dao.entity.usershare;

import net.ebaolife.husqvarna.framework.dao.entity.limit.FRole;
import net.ebaolife.husqvarna.framework.dao.entity.system.FOrganization;
import net.ebaolife.husqvarna.framework.dao.entity.system.FPersonnel;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.FovGridnavigatescheme;
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
@Table(name = "f_gridnavigateshare")
@SuppressWarnings("serial")

public class FGridnavigateshare implements java.io.Serializable {

	private String navigateshareid;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FOrganization FOrganization;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FPersonnel FPersonnel;
	private net.ebaolife.husqvarna.framework.dao.entity.limit.FRole FRole;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FUser FUser;
	private FovGridnavigatescheme fovGridnavigatescheme;

	public FGridnavigateshare() {
	}

	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "navigateshareid", unique = true, nullable = false, length = 40)
	public String getNavigateshareid() {
		return this.navigateshareid;
	}

	public void setNavigateshareid(String navigateshareid) {
		this.navigateshareid = navigateshareid;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "orgid")
	public FOrganization getFOrganization() {
		return this.FOrganization;
	}

	public void setFOrganization(FOrganization FOrganization) {
		this.FOrganization = FOrganization;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "personnelid")
	public FPersonnel getFPersonnel() {
		return this.FPersonnel;
	}

	public void setFPersonnel(FPersonnel FPersonnel) {
		this.FPersonnel = FPersonnel;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "roleid")
	public FRole getFRole() {
		return this.FRole;
	}

	public void setFRole(FRole FRole) {
		this.FRole = FRole;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid")
	public FUser getFUser() {
		return this.FUser;
	}

	public void setFUser(FUser FUser) {
		this.FUser = FUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "schemeid", nullable = false)
	public FovGridnavigatescheme getFovGridnavigatescheme() {
		return this.fovGridnavigatescheme;
	}

	public void setFovGridnavigatescheme(FovGridnavigatescheme fovGridnavigatescheme) {
		this.fovGridnavigatescheme = fovGridnavigatescheme;
	}

}
