package net.ebaolife.husqvarna.framework.dao.entity.attachment;


import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;


@Entity
@SuppressWarnings("serial")
@DynamicUpdate
@Table(name = "f_dataobjectattachmentpdffile")
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
public class FDataobjectattachmentpdffile implements java.io.Serializable {


  private String attachmentid;
  private FDataobjectattachment FDataobjectattachment;
  private byte[] filepdfdata;

  public FDataobjectattachmentpdffile() {}


  public FDataobjectattachmentpdffile(FDataobjectattachment FDataobjectattachment) {
    this.FDataobjectattachment = FDataobjectattachment;
  }

  public FDataobjectattachmentpdffile(FDataobjectattachment FDataobjectattachment, byte[] filepdfdata) {
    this.FDataobjectattachment = FDataobjectattachment;
    this.filepdfdata = filepdfdata;
  }

  @GenericGenerator(name = "generator", strategy = "foreign", parameters = @Parameter(name = "property", value = "FDataobjectattachment"))
  @Id
  @GeneratedValue(generator = "generator")
  @Column(name = "attachmentid", unique = true, nullable = false, length = 40)
  public String getAttachmentid() {
    return this.attachmentid;
  }

  public void setAttachmentid(String attachmentid) {
    this.attachmentid = attachmentid;
  }

  @OneToOne(fetch = FetchType.LAZY)
  @PrimaryKeyJoinColumn
  public FDataobjectattachment getFDataobjectattachment() {
    return this.FDataobjectattachment;
  }

  public void setFDataobjectattachment(FDataobjectattachment FDataobjectattachment) {
    this.FDataobjectattachment = FDataobjectattachment;
  }


  @Column(name = "filepdfdata")
  public byte[] getFilepdfdata() {
    return this.filepdfdata;
  }

  public void setFilepdfdata(byte[] filepdfdata) {
    this.filepdfdata = filepdfdata;
  }



}


