package net.ebaolife.husqvarna.framework.dao.entity.system;

import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobjectcondition;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FUserdatafilterrole;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FUserfieldlimit;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FUserfunctionlimit;
import net.ebaolife.husqvarna.framework.dao.entity.limit.FUserrole;
import net.ebaolife.husqvarna.framework.dao.entity.usershare.FGridnavigateshare;
import net.ebaolife.husqvarna.framework.dao.entity.usershare.FGridschemeshare;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.*;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Entity
@DynamicUpdate
@Table(name = "f_user", uniqueConstraints = @UniqueConstraint(columnNames = {"usercode", "companyid"}) )

public class FUser implements java.io.Serializable {

    private static final long serialVersionUID = 6284233164396607044L;
    private String userid;
    private FPersonnel FPersonnel;
    private String usertype;
    private String usercode;
    private String username;
    private String password;
    private String salt;
    
    private String companyid;
    private String description;
    private Boolean isvalid;
    private Boolean islocked;
    private Integer orderno;
    private String creater;
    private Timestamp createdate;
    private String lastmodifier;
    private Timestamp lastmodifydate;
    private Set<FUserdatafilterrole> FUserdatafilterroles = new HashSet<FUserdatafilterrole>(0);
    private Set<FGridschemeshare> FGridschemeshares = new HashSet<FGridschemeshare>(0);
    private Set<FUserrole> FUserroles = new HashSet<FUserrole>(0);
    private Set<FovGridscheme> fovGridschemes = new HashSet<FovGridscheme>(0);
    private Set<FovFormscheme> fovFormschemes = new HashSet<FovFormscheme>(0);
    private Set<FovUserdefaultgridscheme> fovUserdefaultgridschemes = new HashSet<FovUserdefaultgridscheme>(0);
    private Set<FovUserdefaultfilterscheme> fovUserdefaultfilterschemes = new HashSet<FovUserdefaultfilterscheme>(0);
    private Set<FovFilterscheme> fovFilterschemes = new HashSet<FovFilterscheme>(0);
    private Set<FUserfunctionlimit> FUserfunctionlimits = new HashSet<FUserfunctionlimit>(0);
    private Set<FUserfieldlimit> FUserfieldlimits = new HashSet<FUserfieldlimit>(0);
    private Set<FDataobjectcondition> FDataobjectconditions = new HashSet<FDataobjectcondition>(0);
    private Set<FGridnavigateshare> FGridnavigateshares = new HashSet<FGridnavigateshare>(0);
    private Set<FovUserdefaultnavigatescheme> fovUserdefaultnavigateschemes =
            new HashSet<FovUserdefaultnavigatescheme>(0);
    private Set<FovGridnavigatescheme> fovGridnavigateschemes = new HashSet<FovGridnavigatescheme>(0);
    private Set<FovGridsortscheme> fovGridsortschemes = new HashSet<FovGridsortscheme>(0);

    public FUser() {}

    
    @GenericGenerator(name = "generator", strategy = "uuid.hex")
    @Id
    @GeneratedValue(generator = "generator")
    @Column(name = "userid", unique = true, nullable = false, length = 40)
    public String getUserid() {
        return this.userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "personnelid")
    public FPersonnel getFPersonnel() {
        return this.FPersonnel;
    }

    public void setFPersonnel(FPersonnel FPersonnel) {
        this.FPersonnel = FPersonnel;
    }

    @Column(name = "usertype", nullable = false, length = 40)
    public String getUsertype() {
        return this.usertype;
    }

    public void setUsertype(String usertype) {
        this.usertype = usertype;
    }

    @Column(name = "usercode", nullable = false, length = 40)
    public String getUsercode() {
        return this.usercode;
    }

    public void setUsercode(String usercode) {
        this.usercode = usercode;
    }

    @Column(name = "username", nullable = false, length = 40)
    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Column(name = "password", nullable = false, length = 100)
    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Column(name = "salt", nullable = false, length = 40)
    public String getSalt() {
        return this.salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }










    @Column(name = "companyid", length = 40)
    public String getCompanyid() {
      return companyid;
    }
    
    public void setCompanyid(String companyid) {
      this.companyid = companyid;
    }

    @Column(name = "description", length = 200)
    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(name = "isvalid", nullable = false)
    public Boolean getIsvalid() {
        return this.isvalid;
    }

    public void setIsvalid(Boolean isvalid) {
        this.isvalid = isvalid;
    }

    @Column(name = "islocked", nullable = false)
    public Boolean getIslocked() {
        return this.islocked;
    }

    public void setIslocked(Boolean islocked) {
        this.islocked = islocked;
    }

    @Column(name = "orderno")
    public Integer getOrderno() {
        return this.orderno;
    }

    public void setOrderno(Integer orderno) {
        this.orderno = orderno;
    }

    @Column(name = "creater", nullable = false, length = 40)
    public String getCreater() {
        return this.creater;
    }

    public void setCreater(String creater) {
        this.creater = creater;
    }

    @Column(name = "createdate", nullable = false, length = 19)
    public Timestamp getCreatedate() {
        return this.createdate;
    }

    public void setCreatedate(Timestamp createdate) {
        this.createdate = createdate;
    }

    @Column(name = "lastmodifier", length = 40)
    public String getLastmodifier() {
        return this.lastmodifier;
    }

    public void setLastmodifier(String lastmodifier) {
        this.lastmodifier = lastmodifier;
    }

    @Column(name = "lastmodifydate", length = 19)
    public Timestamp getLastmodifydate() {
        return this.lastmodifydate;
    }

    public void setLastmodifydate(Timestamp lastmodifydate) {
        this.lastmodifydate = lastmodifydate;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FUserdatafilterrole> getFUserdatafilterroles() {
        return this.FUserdatafilterroles;
    }

    public void setFUserdatafilterroles(Set<FUserdatafilterrole> FUserdatafilterroles) {
        this.FUserdatafilterroles = FUserdatafilterroles;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FGridschemeshare> getFGridschemeshares() {
        return this.FGridschemeshares;
    }

    public void setFGridschemeshares(Set<FGridschemeshare> FGridschemeshares) {
        this.FGridschemeshares = FGridschemeshares;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FUserrole> getFUserroles() {
        return this.FUserroles;
    }

    public void setFUserroles(Set<FUserrole> FUserroles) {
        this.FUserroles = FUserroles;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FovGridscheme> getFovGridschemes() {
        return this.fovGridschemes;
    }

    public void setFovGridschemes(Set<FovGridscheme> fovGridschemes) {
        this.fovGridschemes = fovGridschemes;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FovUserdefaultgridscheme> getFovUserdefaultgridschemes() {
        return this.fovUserdefaultgridschemes;
    }

    public void setFovUserdefaultgridschemes(Set<FovUserdefaultgridscheme> fovUserdefaultgridschemes) {
        this.fovUserdefaultgridschemes = fovUserdefaultgridschemes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FovUserdefaultfilterscheme> getFovUserdefaultfilterschemes() {
        return this.fovUserdefaultfilterschemes;
    }

    public void setFovUserdefaultfilterschemes(Set<FovUserdefaultfilterscheme> fovUserdefaultfilterschemes) {
        this.fovUserdefaultfilterschemes = fovUserdefaultfilterschemes;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FUserfunctionlimit> getFUserfunctionlimits() {
        return this.FUserfunctionlimits;
    }

    public void setFUserfunctionlimits(Set<FUserfunctionlimit> FUserfunctionlimits) {
        this.FUserfunctionlimits = FUserfunctionlimits;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FUserfieldlimit> getFUserfieldlimits() {
        return this.FUserfieldlimits;
    }

    public void setFUserfieldlimits(Set<FUserfieldlimit> FUserfieldlimits) {
        this.FUserfieldlimits = FUserfieldlimits;
    }

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FovFormscheme> getFovFormschemes() {
        return fovFormschemes;
    }

    public void setFovFormschemes(Set<FovFormscheme> fovFormschemes) {
        this.fovFormschemes = fovFormschemes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "FUser")
    @OrderBy("orderno")
    public Set<FovFilterscheme> getFovFilterschemes() {
        return this.fovFilterschemes;
    }

    public void setFovFilterschemes(Set<FovFilterscheme> fovFilterschemes) {
        this.fovFilterschemes = fovFilterschemes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FDataobjectcondition> getFDataobjectconditions() {
        return this.FDataobjectconditions;
    }

    public void setFDataobjectconditions(Set<FDataobjectcondition> FDataobjectconditions) {
        this.FDataobjectconditions = FDataobjectconditions;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FGridnavigateshare> getFGridnavigateshares() {
        return this.FGridnavigateshares;
    }

    public void setFGridnavigateshares(Set<FGridnavigateshare> FGridnavigateshares) {
        this.FGridnavigateshares = FGridnavigateshares;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FovUserdefaultnavigatescheme> getFovUserdefaultnavigateschemes() {
        return this.fovUserdefaultnavigateschemes;
    }

    public void setFovUserdefaultnavigateschemes(Set<FovUserdefaultnavigatescheme> fovUserdefaultnavigateschemes) {
        this.fovUserdefaultnavigateschemes = fovUserdefaultnavigateschemes;
    }

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FovGridnavigatescheme> getFovGridnavigateschemes() {
        return this.fovGridnavigateschemes;
    }

    public void setFovGridnavigateschemes(Set<FovGridnavigatescheme> fovGridnavigateschemes) {
        this.fovGridnavigateschemes = fovGridnavigateschemes;
    }

    @OrderBy("orderno")
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "FUser")
    public Set<FovGridsortscheme> getFovGridsortschemes() {
      return this.fovGridsortschemes;
    }

    public void setFovGridsortschemes(Set<FovGridsortscheme> fovGridsortschemes) {
      this.fovGridsortschemes = fovGridsortschemes;
    }

}
