package net.ebaolife.husqvarna.framework.dao.entity.viewsetting;

import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
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
@Table(name = "fov_userdefaultgridscheme")

public class FovUserdefaultgridscheme implements java.io.Serializable {

	private static final long serialVersionUID = -8348812579631640300L;
	private String userdefaultid;
	private FovGridscheme fovGridscheme;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FUser FUser;

	public FovUserdefaultgridscheme() {
	}

	@Id
	@GeneratedValue(generator = "generator")
	@GenericGenerator(name = "generator", strategy = "uuid.hex")
	@Column(name = "userdefaultid", unique = true, nullable = false, length = 40)

	public String getUserdefaultid() {
		return this.userdefaultid;
	}

	public void setUserdefaultid(String userdefaultid) {
		this.userdefaultid = userdefaultid;
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
	@JoinColumn(name = "userid", nullable = false)

	public FUser getFUser() {
		return this.FUser;
	}

	public void setFUser(FUser FUser) {
		this.FUser = FUser;
	}

}