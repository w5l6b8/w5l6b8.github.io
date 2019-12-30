package net.ebaolife.husqvarna.framework.dao.entity.system;

import net.ebaolife.husqvarna.framework.dao.entity.usershare.FGridschemeshare;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@Entity
@DynamicUpdate
@Table(name = "f_personnel", uniqueConstraints = @UniqueConstraint(columnNames = {"personnelcode", "companyid"}))
@SuppressWarnings("serial")

public class FPersonnel implements java.io.Serializable {

  

  private String personnelid;
  private FOrganization FOrganization;
  private String personnelcode;
  private String personnelname;
  private String officetel;
  private String mobile;
  private String EMail;
  private Boolean isvalid;
  private Integer orderno;
  private byte[] favicon;

  private String companyid;
  private String creater;
  private Timestamp createdate;
  private String lastmodifier;
  private Timestamp lastmodifydate;
  private Set<FGridschemeshare> FGridschemeshares = new HashSet<FGridschemeshare>(0);
  private Set<FUser> FUsers = new HashSet<FUser>(0);

  public FPersonnel() {}

  public FPersonnel(FOrganization FOrganization, String personnelcode, String personnelname, Boolean isvalid,
      String companyid, String creater, Timestamp createdate) {
    this.FOrganization = FOrganization;
    this.personnelcode = personnelcode;
    this.personnelname = personnelname;
    this.isvalid = isvalid;
    this.companyid = companyid;
    this.creater = creater;
    this.createdate = createdate;
  }

  public FPersonnel(FOrganization FOrganization, String personnelcode, String personnelname, String officetel,
      String mobile, String EMail, Boolean isvalid, Integer orderno, String companyid, String creater,
      Timestamp createdate, String lastmodifier, Timestamp lastmodifydate, Set<FGridschemeshare> FGridschemeshares,
      Set<FUser> FUsers) {
    this.FOrganization = FOrganization;
    this.personnelcode = personnelcode;
    this.personnelname = personnelname;
    this.officetel = officetel;
    this.mobile = mobile;
    this.EMail = EMail;
    this.isvalid = isvalid;
    this.orderno = orderno;
    this.companyid = companyid;
    this.creater = creater;
    this.createdate = createdate;
    this.lastmodifier = lastmodifier;
    this.lastmodifydate = lastmodifydate;
    this.FGridschemeshares = FGridschemeshares;
    this.FUsers = FUsers;
  }

  
  @GenericGenerator(name = "generator", strategy = "uuid.hex")
  @Id
  @GeneratedValue(generator = "generator")

  @Column(name = "personnelid", unique = true, nullable = false, length = 40)

  public String getPersonnelid() {
    return this.personnelid;
  }

  public void setPersonnelid(String personnelid) {
    this.personnelid = personnelid;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "orgid", nullable = false)

  public FOrganization getFOrganization() {
    return this.FOrganization;
  }

  public void setFOrganization(FOrganization FOrganization) {
    this.FOrganization = FOrganization;
  }

  @Column(name = "personnelcode", nullable = false, length = 40)

  public String getPersonnelcode() {
    return this.personnelcode;
  }

  public void setPersonnelcode(String personnelcode) {
    this.personnelcode = personnelcode;
  }

  @Column(name = "personnelname", nullable = false, length = 100)

  public String getPersonnelname() {
    return this.personnelname;
  }

  public void setPersonnelname(String personnelname) {
    this.personnelname = personnelname;
  }

  @Column(name = "officetel", length = 20)

  public String getOfficetel() {
    return this.officetel;
  }

  public void setOfficetel(String officetel) {
    this.officetel = officetel;
  }

  @Column(name = "mobile", length = 20)

  public String getMobile() {
    return this.mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  @Column(name = "e_mail", length = 100)

  public String getEMail() {
    return this.EMail;
  }

  public void setEMail(String EMail) {
    this.EMail = EMail;
  }

  @Column(name = "isvalid", nullable = false)

  public Boolean getIsvalid() {
    return this.isvalid;
  }

  public void setIsvalid(Boolean isvalid) {
    this.isvalid = isvalid;
  }

  @Column(name = "orderno")

  public Integer getOrderno() {
    return this.orderno;
  }

  public void setOrderno(Integer orderno) {
    this.orderno = orderno;
  }

  public byte[] getFavicon() {
    return favicon;
  }

  public void setFavicon(byte[] favicon) {
    this.favicon = favicon;
  }

  @Column(name = "companyid", nullable = false, length = 40)

  public String getCompanyid() {
    return this.companyid;
  }

  public void setCompanyid(String companyid) {
    this.companyid = companyid;
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

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FPersonnel")

  public Set<FGridschemeshare> getFGridschemeshares() {
    return this.FGridschemeshares;
  }

  public void setFGridschemeshares(Set<FGridschemeshare> FGridschemeshares) {
    this.FGridschemeshares = FGridschemeshares;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FPersonnel")

  public Set<FUser> getFUsers() {
    return this.FUsers;
  }

  public void setFUsers(Set<FUser> FUsers) {
    this.FUsers = FUsers;
  }









}
