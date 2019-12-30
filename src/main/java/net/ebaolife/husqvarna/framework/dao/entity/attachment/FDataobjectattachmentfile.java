package net.ebaolife.husqvarna.framework.dao.entity.attachment;


import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

import javax.persistence.*;


@Entity
@SuppressWarnings("serial")
@DynamicUpdate
@Table(name = "f_dataobjectattachmentfile")
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
public class FDataobjectattachmentfile implements java.io.Serializable {


  private String attachmentid;
  private FDataobjectattachment FDataobjectattachment;
  private byte[] filedata;

  public FDataobjectattachmentfile() {}


  public FDataobjectattachmentfile(FDataobjectattachment FDataobjectattachment) {
    this.FDataobjectattachment = FDataobjectattachment;
  }

  public FDataobjectattachmentfile(FDataobjectattachment FDataobjectattachment, byte[] filedata) {
    this.FDataobjectattachment = FDataobjectattachment;
    this.filedata = filedata;
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


  @Column(name = "filedata")
  public byte[] getFiledata() {
    return this.filedata;
  }

  public void setFiledata(byte[] filedata) {
    this.filedata = filedata;
  }



}


