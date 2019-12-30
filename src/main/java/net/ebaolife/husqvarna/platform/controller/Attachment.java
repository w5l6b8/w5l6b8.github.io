package net.ebaolife.husqvarna.platform.controller;


import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.bean.FileUploadBean;
import net.ebaolife.husqvarna.framework.exception.ConnectOpenOfficeException;
import net.ebaolife.husqvarna.platform.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

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
@Controller
@RequestMapping("/platform/attachment")
public class Attachment {

  @Autowired
  private AttachmentService attachmentService;

  @RequestMapping(value = "/upload")
  @ResponseBody
  public ActionResult upload(FileUploadBean uploaditem, BindingResult bindingResult, HttpServletRequest request) {
    try {
      attachmentService.upload(uploaditem, bindingResult);
    } catch (IOException e) {
      e.printStackTrace();
      return new ActionResult(false, "附件文件保存时，文件系统错误!");
    } catch (IllegalAccessException e) {
      e.printStackTrace();
      return new ActionResult(false, e.getMessage());
    } catch (InvocationTargetException e) {
      e.printStackTrace();
      return new ActionResult(false, e.getMessage());
    } catch (ConnectOpenOfficeException e) {
      e.printStackTrace();
      return new ActionResult(false, e.getMessage());
    } catch (RuntimeException e){
      e.printStackTrace();
      return new ActionResult(false, e.getMessage());
    }
    return new ActionResult();
  }





  @RequestMapping(value = "/preview")
  public void preview(String attachmentid) {
    try {
      attachmentService.preview(attachmentid);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }





  @RequestMapping(value = "/download")
  public void download(String attachmentid) {
    try {
      attachmentService.download(attachmentid);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @RequestMapping("downloadall.do")
  public void downloadAll(String moduleName, String idkey) {
    try {
      attachmentService.downloadAll(moduleName, idkey);
    } catch (Exception e) {
      e.printStackTrace();
    }


  }
}
