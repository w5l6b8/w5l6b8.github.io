package net.ebaolife.husqvarna.framework.dao.entity.system;

import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@SuppressWarnings("serial")
@Entity
@DynamicUpdate
@Table(name = "f_systeminfo", uniqueConstraints = @UniqueConstraint(columnNames = "companyid"))
public class FSysteminfo implements java.io.Serializable {

  private String systeminfoid;
  private FCompany FCompany;
  private String systemname;
  private String systemversion;
  private String iconurl;
  private String iconcls;
  private byte[] iconfile;
  private String systemaddition;
  private String copyrightowner;
  private String copyrightinfo;
  private Boolean allowsavepassword;
  private Integer savepassworddays;
  private String passwordlevel;
  private Boolean needreplaceinitialpassword;
  private Boolean allowexternallogin;
  private Boolean needidentifingcode;
  private Boolean alwaysneedidentifingcode;
  private String forgetpassword;
  private Boolean allowloginagain;
  private Integer maxusers;
  private Integer sessiontimeoutminute;
  private Integer additionfilemaxmb;
  private String attachmentreducemode;
  private String previewexts;
  private String allowtopdf;
  private Boolean attachmentsavetofile;
  private String attachmentsavedir;
  private Boolean isencrypt;
  private String attachmentdatabasename;
  private String backupfilename;
  private String remark;

  public FSysteminfo() {}


  public FSysteminfo(String systeminfoid, FCompany FCompany) {
    this.systeminfoid = systeminfoid;
    this.FCompany = FCompany;
  }

  public FSysteminfo(String systeminfoid, FCompany FCompany, String systemname, String systemversion, String iconurl,
      String iconcls, byte[] iconfile, String systemaddition, String copyrightowner, String copyrightinfo,
      Boolean allowsavepassword, Integer savepassworddays, String passwordlevel, Boolean needreplaceinitialpassword,
      Boolean allowexternallogin, Boolean needidentifingcode, Boolean alwaysneedidentifingcode, String forgetpassword,
      Boolean allowloginagain, Integer maxusers, Integer sessiontimeoutminute, Integer additionfilemaxmb,
      String attachmentreducemode, String previewexts, String allowtopdf, Boolean attachmentsavetofile,
      String attachmentsavedir, Boolean isencrypt, String attachmentdatabasename, String backupfilename,
      String remark) {
    this.systeminfoid = systeminfoid;
    this.FCompany = FCompany;
    this.systemname = systemname;
    this.systemversion = systemversion;
    this.iconurl = iconurl;
    this.iconcls = iconcls;
    this.iconfile = iconfile;
    this.systemaddition = systemaddition;
    this.copyrightowner = copyrightowner;
    this.copyrightinfo = copyrightinfo;
    this.allowsavepassword = allowsavepassword;
    this.savepassworddays = savepassworddays;
    this.passwordlevel = passwordlevel;
    this.needreplaceinitialpassword = needreplaceinitialpassword;
    this.allowexternallogin = allowexternallogin;
    this.needidentifingcode = needidentifingcode;
    this.alwaysneedidentifingcode = alwaysneedidentifingcode;
    this.forgetpassword = forgetpassword;
    this.allowloginagain = allowloginagain;
    this.maxusers = maxusers;
    this.sessiontimeoutminute = sessiontimeoutminute;
    this.additionfilemaxmb = additionfilemaxmb;
    this.attachmentreducemode = attachmentreducemode;
    this.previewexts = previewexts;
    this.allowtopdf = allowtopdf;
    this.attachmentsavetofile = attachmentsavetofile;
    this.attachmentsavedir = attachmentsavedir;
    this.isencrypt = isencrypt;
    this.attachmentdatabasename = attachmentdatabasename;
    this.backupfilename = backupfilename;
    this.remark = remark;
  }

  @Id
  @GeneratedValue(generator = "generator")
  @GenericGenerator(name = "generator", strategy = "uuid.hex")
  @Column(name = "systeminfoid", unique = true, nullable = false, length = 40)
  public String getSysteminfoid() {
    return this.systeminfoid;
  }

  public void setSysteminfoid(String systeminfoid) {
    this.systeminfoid = systeminfoid;
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "companyid", unique = true, nullable = false)
  public FCompany getFCompany() {
    return this.FCompany;
  }

  public void setFCompany(FCompany FCompany) {
    this.FCompany = FCompany;
  }


  @Column(name = "systemname", length = 50)
  public String getSystemname() {
    return this.systemname;
  }

  public void setSystemname(String systemname) {
    this.systemname = systemname;
  }


  @Column(name = "systemversion", length = 50)
  public String getSystemversion() {
    return this.systemversion;
  }

  public void setSystemversion(String systemversion) {
    this.systemversion = systemversion;
  }


  @Column(name = "iconurl", length = 50)
  public String getIconurl() {
    return this.iconurl;
  }

  public void setIconurl(String iconurl) {
    this.iconurl = iconurl;
  }


  @Column(name = "iconcls", length = 50)
  public String getIconcls() {
    return this.iconcls;
  }

  public void setIconcls(String iconcls) {
    this.iconcls = iconcls;
  }


  @Column(name = "iconfile")
  public byte[] getIconfile() {
    return this.iconfile;
  }

  public void setIconfile(byte[] iconfile) {
    this.iconfile = iconfile;
  }


  @Column(name = "systemaddition", length = 200)
  public String getSystemaddition() {
    return this.systemaddition;
  }

  public void setSystemaddition(String systemaddition) {
    this.systemaddition = systemaddition;
  }


  @Column(name = "copyrightowner", length = 50)
  public String getCopyrightowner() {
    return this.copyrightowner;
  }

  public void setCopyrightowner(String copyrightowner) {
    this.copyrightowner = copyrightowner;
  }


  @Column(name = "copyrightinfo", length = 50)
  public String getCopyrightinfo() {
    return this.copyrightinfo;
  }

  public void setCopyrightinfo(String copyrightinfo) {
    this.copyrightinfo = copyrightinfo;
  }


  @Column(name = "allowsavepassword")
  public Boolean getAllowsavepassword() {
    return this.allowsavepassword;
  }

  public void setAllowsavepassword(Boolean allowsavepassword) {
    this.allowsavepassword = allowsavepassword;
  }


  @Column(name = "savepassworddays")
  public Integer getSavepassworddays() {
    return this.savepassworddays;
  }

  public void setSavepassworddays(Integer savepassworddays) {
    this.savepassworddays = savepassworddays;
  }


  @Column(name = "passwordlevel", length = 10)
  public String getPasswordlevel() {
    return this.passwordlevel;
  }

  public void setPasswordlevel(String passwordlevel) {
    this.passwordlevel = passwordlevel;
  }


  @Column(name = "needreplaceinitialpassword")
  public Boolean getNeedreplaceinitialpassword() {
    return this.needreplaceinitialpassword;
  }

  public void setNeedreplaceinitialpassword(Boolean needreplaceinitialpassword) {
    this.needreplaceinitialpassword = needreplaceinitialpassword;
  }


  @Column(name = "allowexternallogin")
  public Boolean getAllowexternallogin() {
    return this.allowexternallogin;
  }

  public void setAllowexternallogin(Boolean allowexternallogin) {
    this.allowexternallogin = allowexternallogin;
  }


  @Column(name = "needidentifingcode")
  public Boolean getNeedidentifingcode() {
    return this.needidentifingcode;
  }

  public void setNeedidentifingcode(Boolean needidentifingcode) {
    this.needidentifingcode = needidentifingcode;
  }


  @Column(name = "alwaysneedidentifingcode")
  public Boolean getAlwaysneedidentifingcode() {
    return this.alwaysneedidentifingcode;
  }

  public void setAlwaysneedidentifingcode(Boolean alwaysneedidentifingcode) {
    this.alwaysneedidentifingcode = alwaysneedidentifingcode;
  }


  @Column(name = "forgetpassword", length = 200)
  public String getForgetpassword() {
    return this.forgetpassword;
  }

  public void setForgetpassword(String forgetpassword) {
    this.forgetpassword = forgetpassword;
  }


  @Column(name = "allowloginagain")
  public Boolean getAllowloginagain() {
    return this.allowloginagain;
  }

  public void setAllowloginagain(Boolean allowloginagain) {
    this.allowloginagain = allowloginagain;
  }


  @Column(name = "maxusers")
  public Integer getMaxusers() {
    return this.maxusers;
  }

  public void setMaxusers(Integer maxusers) {
    this.maxusers = maxusers;
  }


  @Column(name = "sessiontimeoutminute")
  public Integer getSessiontimeoutminute() {
    return this.sessiontimeoutminute;
  }

  public void setSessiontimeoutminute(Integer sessiontimeoutminute) {
    this.sessiontimeoutminute = sessiontimeoutminute;
  }


  @Column(name = "additionfilemaxmb")
  public Integer getAdditionfilemaxmb() {
    return this.additionfilemaxmb;
  }

  public void setAdditionfilemaxmb(Integer additionfilemaxmb) {
    this.additionfilemaxmb = additionfilemaxmb;
  }


  @Column(name = "attachmentreducemode", length = 10)
  public String getAttachmentreducemode() {
    return this.attachmentreducemode;
  }

  public void setAttachmentreducemode(String attachmentreducemode) {
    this.attachmentreducemode = attachmentreducemode;
  }


  @Column(name = "previewexts", length = 200)
  public String getPreviewexts() {
    return this.previewexts;
  }

  public void setPreviewexts(String previewexts) {
    this.previewexts = previewexts;
  }


  @Column(name = "allowtopdf", length = 200)
  public String getAllowtopdf() {
    return this.allowtopdf;
  }

  public void setAllowtopdf(String allowtopdf) {
    this.allowtopdf = allowtopdf;
  }


  @Column(name = "attachmentsavetofile")
  public Boolean getAttachmentsavetofile() {
    return this.attachmentsavetofile;
  }

  public void setAttachmentsavetofile(Boolean attachmentsavetofile) {
    this.attachmentsavetofile = attachmentsavetofile;
  }


  @Column(name = "attachmentsavedir", length = 200)
  public String getAttachmentsavedir() {
    return this.attachmentsavedir;
  }

  public void setAttachmentsavedir(String attachmentsavedir) {
    this.attachmentsavedir = attachmentsavedir;
  }


  @Column(name = "isencrypt")
  public Boolean getIsencrypt() {
    return this.isencrypt;
  }

  public void setIsencrypt(Boolean isencrypt) {
    this.isencrypt = isencrypt;
  }


  @Column(name = "attachmentdatabasename", length = 200)
  public String getAttachmentdatabasename() {
    return this.attachmentdatabasename;
  }

  public void setAttachmentdatabasename(String attachmentdatabasename) {
    this.attachmentdatabasename = attachmentdatabasename;
  }


  @Column(name = "backupfilename", length = 200)
  public String getBackupfilename() {
    return this.backupfilename;
  }

  public void setBackupfilename(String backupfilename) {
    this.backupfilename = backupfilename;
  }


  @Column(name = "remark", length = 200)
  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }



}


