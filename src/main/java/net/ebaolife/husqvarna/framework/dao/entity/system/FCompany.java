package net.ebaolife.husqvarna.framework.dao.entity.system;

import net.ebaolife.husqvarna.framework.dao.entity.limit.FRole;
import net.ebaolife.husqvarna.framework.dao.entity.module.FCompanymenu;
import net.ebaolife.husqvarna.framework.dao.entity.module.FCompanymodule;
import net.ebaolife.husqvarna.framework.dao.entity.module.FCompanymodulegroup;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;


@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name = "f_company")
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
public class FCompany implements java.io.Serializable {

  private String companyid;
  private FCompany FCompany;
  private String companyname;
  private String companylongname;
  private Boolean isvalid;
  private String levelid;
  private Integer orderno;
  private String address;
  private String linkmen;
  private String telnumber;
  private Timestamp startdate;
  private String postcode;
  private String remark;
  private String servicedepartment;
  private String servicemen;
  private String servicetelnumber;
  private String serviceqq;
  private String serviceemail;
  private String servicehomepage;
  private String creater;
  private Timestamp createdate;
  private String lastmodifier;
  private Timestamp lastmodifydate;
  private Set<FCompanymodulegroup> FCompanymodulegroups = new HashSet<FCompanymodulegroup>(0);
  private Set<FCompanymenu> FCompanymenus = new HashSet<FCompanymenu>(0);
  private Set<FCompany> FCompanies = new HashSet<FCompany>(0);
  private Set<FCompanymodule> FCompanymodules = new HashSet<FCompanymodule>(0);
  private Set<FOrganization> FOrganizations = new HashSet<FOrganization>(0);
  private Set<FRole> FRoles = new HashSet<FRole>(0);
  private Set<FSysteminfo> FSysteminfos = new HashSet<FSysteminfo>(0);

  public FCompany() {}

  public FCompany(String companyname, Boolean isvalid, String levelid, String creater, Timestamp createdate) {
    this.companyname = companyname;
    this.isvalid = isvalid;
    this.levelid = levelid;
    this.creater = creater;
    this.createdate = createdate;
  }

  public FCompany(FCompany FCompany, String companyname, String companylongname, Boolean isvalid, String levelid,
      Integer orderno, String address, String linkmen, String telnumber, Timestamp startdate, String postcode, String remark,
      String servicedepartment, String servicemen, String servicetelnumber, String serviceqq, String serviceemail,
      String servicehomepage, String creater, Timestamp createdate, String lastmodifier, Timestamp lastmodifydate,
      Set<FCompanymodulegroup> FCompanymodulegroups, Set<FCompany> FCompanies, Set<FOrganization> FOrganizations
    ) {
    this.FCompany = FCompany;
    this.companyname = companyname;
    this.companylongname = companylongname;
    this.isvalid = isvalid;
    this.levelid = levelid;
    this.orderno = orderno;
    this.address = address;
    this.linkmen = linkmen;
    this.telnumber = telnumber;
    this.startdate = startdate;
    this.postcode = postcode;
    this.remark = remark;
    this.servicedepartment = servicedepartment;
    this.servicemen = servicemen;
    this.servicetelnumber = servicetelnumber;
    this.serviceqq = serviceqq;
    this.serviceemail = serviceemail;
    this.servicehomepage = servicehomepage;
    this.creater = creater;
    this.createdate = createdate;
    this.lastmodifier = lastmodifier;
    this.lastmodifydate = lastmodifydate;
    this.FCompanymodulegroups = FCompanymodulegroups;
    this.FCompanies = FCompanies;
    this.FOrganizations = FOrganizations;
  }

  @Id
  
  

  @Column(name = "companyid", unique = true, nullable = false, length = 40)

  public String getCompanyid() {
    return this.companyid;
  }

  public void setCompanyid(String companyid) {
    this.companyid = companyid;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parentid")

  public FCompany getFCompany() {
    return this.FCompany;
  }

  public void setFCompany(FCompany FCompany) {
    this.FCompany = FCompany;
  }

  @Column(name = "companyname", nullable = false, length = 200)

  public String getCompanyname() {
    return this.companyname;
  }

  public void setCompanyname(String companyname) {
    this.companyname = companyname;
  }

  @Column(name = "companylongname", length = 200)

  public String getCompanylongname() {
    return this.companylongname;
  }

  public void setCompanylongname(String companylongname) {
    this.companylongname = companylongname;
  }

  @Column(name = "isvalid", nullable = false)

  public Boolean getIsvalid() {
    return this.isvalid;
  }

  public void setIsvalid(Boolean isvalid) {
    this.isvalid = isvalid;
  }

  @Column(name = "levelid", nullable = false, length = 100)

  public String getLevelid() {
    return this.levelid;
  }

  public void setLevelid(String levelid) {
    this.levelid = levelid;
  }

  @Column(name = "orderno")

  public Integer getOrderno() {
    return this.orderno;
  }

  public void setOrderno(Integer orderno) {
    this.orderno = orderno;
  }

  @Column(name = "address", length = 50)

  public String getAddress() {
    return this.address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Column(name = "linkmen", length = 50)

  public String getLinkmen() {
    return this.linkmen;
  }

  public void setLinkmen(String linkmen) {
    this.linkmen = linkmen;
  }

  @Column(name = "telnumber", length = 50)

  public String getTelnumber() {
    return this.telnumber;
  }

  public void setTelnumber(String telnumber) {
    this.telnumber = telnumber;
  }

  @Column(name = "startdate", length = 19)
  public Timestamp getStartdate() {
    return this.startdate;
  }

  public void setStartdate(Timestamp startdate) {
    this.startdate = startdate;
  }

  @Column(name = "postcode", length = 6)

  public String getPostcode() {
    return this.postcode;
  }

  public void setPostcode(String postcode) {
    this.postcode = postcode;
  }

  @Column(name = "remark", length = 200)

  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }

  @Column(name = "servicedepartment", length = 50)

  public String getServicedepartment() {
    return this.servicedepartment;
  }

  public void setServicedepartment(String servicedepartment) {
    this.servicedepartment = servicedepartment;
  }

  @Column(name = "servicemen", length = 50)

  public String getServicemen() {
    return this.servicemen;
  }

  public void setServicemen(String servicemen) {
    this.servicemen = servicemen;
  }

  @Column(name = "servicetelnumber", length = 50)

  public String getServicetelnumber() {
    return this.servicetelnumber;
  }

  public void setServicetelnumber(String servicetelnumber) {
    this.servicetelnumber = servicetelnumber;
  }

  @Column(name = "serviceqq", length = 50)

  public String getServiceqq() {
    return this.serviceqq;
  }

  public void setServiceqq(String serviceqq) {
    this.serviceqq = serviceqq;
  }

  @Column(name = "serviceemail", length = 50)

  public String getServiceemail() {
    return this.serviceemail;
  }

  public void setServiceemail(String serviceemail) {
    this.serviceemail = serviceemail;
  }

  @Column(name = "servicehomepage", length = 50)

  public String getServicehomepage() {
    return this.servicehomepage;
  }

  public void setServicehomepage(String servicehomepage) {
    this.servicehomepage = servicehomepage;
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

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FCompany")
  @OrderBy("orderno")
  public Set<FCompanymodulegroup> getFCompanymodulegroups() {
    return this.FCompanymodulegroups;
  }

  public void setFCompanymodulegroups(Set<FCompanymodulegroup> FCompanymodulegroups) {
    this.FCompanymodulegroups = FCompanymodulegroups;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FCompany")

  public Set<FCompany> getFCompanies() {
    return this.FCompanies;
  }

  public void setFCompanies(Set<FCompany> FCompanies) {
    this.FCompanies = FCompanies;
  }

  @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "FCompany")

  public Set<FOrganization> getFOrganizations() {
    return this.FOrganizations;
  }

  public void setFOrganizations(Set<FOrganization> FOrganizations) {
    this.FOrganizations = FOrganizations;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "FCompany")
  public Set<FCompanymenu> getFCompanymenus() {
    return this.FCompanymenus;
  }

  public void setFCompanymenus(Set<FCompanymenu> FCompanymenus) {
    this.FCompanymenus = FCompanymenus;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "FCompany")
  public Set<FRole> getFRoles() {
    return this.FRoles;
  }

  public void setFRoles(Set<FRole> FRoles) {
    this.FRoles = FRoles;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "FCompany")
  public Set<FCompanymodule> getFCompanymodules() {
    return this.FCompanymodules;
  }

  public void setFCompanymodules(Set<FCompanymodule> FCompanymodules) {
    this.FCompanymodules = FCompanymodules;
  }

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "FCompany")
  public Set<FSysteminfo> getFSysteminfos() {
    return this.FSysteminfos;
  }

  public void setFSysteminfos(Set<FSysteminfo> FSysteminfos) {
    this.FSysteminfos = FSysteminfos;
  }

}
