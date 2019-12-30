package net.ebaolife.husqvarna.framework.dao.entity.limit;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield;
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
@Table(name = "f_userfieldlimit")
@SuppressWarnings("serial")

public class FUserfieldlimit implements java.io.Serializable {

	private String limitid;
	private net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectfield FDataobjectfield;
	private net.ebaolife.husqvarna.framework.dao.entity.system.FUser FUser;
	private Boolean readonly;
	private Boolean hidden;
	private Boolean important;

	public FUserfieldlimit() {
	}

	public FUserfieldlimit(FDataobjectfield FDataobjectfield, FUser FUser) {
		this.FDataobjectfield = FDataobjectfield;
		this.FUser = FUser;
	}

	public FUserfieldlimit(FDataobjectfield FDataobjectfield, FUser FUser, Boolean readonly, Boolean hidden,
			Boolean important) {
		this.FDataobjectfield = FDataobjectfield;
		this.FUser = FUser;
		this.readonly = readonly;
		this.hidden = hidden;
		this.important = important;
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
	@JoinColumn(name = "fieldid", nullable = false)

	public FDataobjectfield getFDataobjectfield() {
		return this.FDataobjectfield;
	}

	public void setFDataobjectfield(FDataobjectfield FDataobjectfield) {
		this.FDataobjectfield = FDataobjectfield;
	}

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", nullable = false)

	public FUser getFUser() {
		return this.FUser;
	}

	public void setFUser(FUser FUser) {
		this.FUser = FUser;
	}

	@Column(name = "readonly")

	public Boolean getReadonly() {
		return this.readonly;
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	@Column(name = "hidden")

	public Boolean getHidden() {
		return this.hidden;
	}

	public void setHidden(Boolean hidden) {
		this.hidden = hidden;
	}

	@Column(name = "important")

	public Boolean getImportant() {
		return this.important;
	}

	public void setImportant(Boolean important) {
		this.important = important;
	}

}