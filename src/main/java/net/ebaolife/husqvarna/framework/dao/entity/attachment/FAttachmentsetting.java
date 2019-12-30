package net.ebaolife.husqvarna.framework.dao.entity.attachment;


import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@SuppressWarnings("serial")
@DynamicUpdate
@Table(name = "f_attachmentsetting")
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
public class FAttachmentsetting implements java.io.Serializable {

  private int settingid;
  private boolean saveinfilesystem;
  private String rootpath;
  private String pathmode;
  private Integer filemaxsize;
  private boolean createpreviewimage;
  private boolean createpreviewpdf;
  private Integer imagewidth;
  private Integer imageheight;
  private String remark;

  public FAttachmentsetting() {}


  public FAttachmentsetting(int settingid, boolean saveinfilesystem) {
    this.settingid = settingid;
    this.saveinfilesystem = saveinfilesystem;
  }

  public FAttachmentsetting(int settingid, boolean saveinfilesystem, String rootpath, String pathmode,
      Integer filemaxsize, boolean createpreviewimage, boolean createpreviewpdf, Integer imagewidth, Integer imageheight,
      String remark) {
    this.settingid = settingid;
    this.saveinfilesystem = saveinfilesystem;
    this.rootpath = rootpath;
    this.pathmode = pathmode;
    this.filemaxsize = filemaxsize;
    this.createpreviewimage = createpreviewimage;
    this.createpreviewpdf = createpreviewpdf;
    this.imagewidth = imagewidth;
    this.imageheight = imageheight;
    this.remark = remark;
  }

  @Id
  @Column(name = "settingid", unique = true, nullable = false)
  public int getSettingid() {
    return this.settingid;
  }

  public void setSettingid(int settingid) {
    this.settingid = settingid;
  }


  @Column(name = "saveinfilesystem", nullable = false)
  public boolean getSaveinfilesystem() {
    return this.saveinfilesystem;
  }

  public void setSaveinfilesystem(boolean saveinfilesystem) {
    this.saveinfilesystem = saveinfilesystem;
  }


  @Column(name = "rootpath", length = 200)
  public String getRootpath() {
    return this.rootpath;
  }

  public void setRootpath(String rootpath) {
    this.rootpath = rootpath;
  }


  @Column(name = "pathmode", length = 20)
  public String getPathmode() {
    return this.pathmode;
  }

  public void setPathmode(String pathmode) {
    this.pathmode = pathmode;
  }


  @Column(name = "filemaxsize")
  public Integer getFilemaxsize() {
    return this.filemaxsize;
  }

  public void setFilemaxsize(Integer filemaxsize) {
    this.filemaxsize = filemaxsize;
  }


  @Column(name = "createpreviewimage")
  public boolean getCreatepreviewimage() {
    return this.createpreviewimage;
  }

  public void setCreatepreviewimage(boolean createpreviewimage) {
    this.createpreviewimage = createpreviewimage;
  }


  @Column(name = "createpreviewpdf")
  public boolean getCreatepreviewpdf() {
    return this.createpreviewpdf;
  }

  public void setCreatepreviewpdf(boolean createpreviewpdf) {
    this.createpreviewpdf = createpreviewpdf;
  }


  @Column(name = "imagewidth")
  public Integer getImagewidth() {
    return this.imagewidth;
  }

  public void setImagewidth(Integer imagewidth) {
    this.imagewidth = imagewidth;
  }


  @Column(name = "imageheight")
  public Integer getImageheight() {
    return this.imageheight;
  }

  public void setImageheight(Integer imageheight) {
    this.imageheight = imageheight;
  }


  @Column(name = "remark", length = 200)
  public String getRemark() {
    return this.remark;
  }

  public void setRemark(String remark) {
    this.remark = remark;
  }



}


