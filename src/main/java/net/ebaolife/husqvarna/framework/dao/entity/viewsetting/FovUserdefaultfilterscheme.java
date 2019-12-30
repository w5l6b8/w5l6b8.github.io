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
@Table(name = "fov_userdefaultfilterscheme")
public class FovUserdefaultfilterscheme implements java.io.Serializable {

	private static final long serialVersionUID = 5756530239427038353L;
	private String userdefaultid;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FUser FUser;
	private FovFilterscheme fovFilterscheme;

	public FovUserdefaultfilterscheme() {
	}

	public FovUserdefaultfilterscheme(String userdefaultid, FUser FUser, FovFilterscheme fovFilterscheme) {
		this.userdefaultid = userdefaultid;
		this.FUser = FUser;
		this.fovFilterscheme = fovFilterscheme;
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
	@JoinColumn(name = "userid", nullable = false)
	public FUser getFUser() {
		return this.FUser;
	}

	public void setFUser(FUser FUser) {
		this.FUser = FUser;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "filterschemeid", nullable = false)
	public FovFilterscheme getFovFilterscheme() {
		return this.fovFilterscheme;
	}

	public void setFovFilterscheme(FovFilterscheme fovFilterscheme) {
		this.fovFilterscheme = fovFilterscheme;
	}

}
