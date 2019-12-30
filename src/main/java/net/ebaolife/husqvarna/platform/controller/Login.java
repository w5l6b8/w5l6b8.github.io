package net.ebaolife.husqvarna.platform.controller;


import net.ebaolife.husqvarna.framework.bean.ResultBean;
import net.ebaolife.husqvarna.framework.bean.UserBean;
import net.ebaolife.husqvarna.framework.core.annotation.SystemLogs;
import net.ebaolife.husqvarna.framework.dao.entity.log.FUserloginlog;
import net.ebaolife.husqvarna.framework.utils.Globals;
import net.ebaolife.husqvarna.framework.utils.SessionUtils;
import net.ebaolife.husqvarna.framework.utils.ValidateCode;
import net.ebaolife.husqvarna.platform.service.LoginService;
import net.ebaolife.husqvarna.platform.service.SystemFrameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Map;

@Controller
@RequestMapping("/login")
public class Login {

  @Resource
  private LoginService service;

  @Resource
  private SystemFrameService systemFrameService;

  public static final String VALIDATECODE = "validateCode";
  public static final String LOGINTIMES = "logintimes";
  public static String LOGOUTMODE = "userLogoutMethod";
  public static String LOGOUTMODESTAND = "正常退出";
  public static String LOGOUTMODETIMEOUT = "超时退出";

  @SystemLogs("用户登陆")
  @RequestMapping(value = "/validate")
  @ResponseBody
  public ResultBean validate(HttpServletRequest request, HttpServletResponse response, String companyid,
                             String usercode, String password, Boolean invalidate) {
    ResultBean result = service.login(companyid, usercode, password, invalidate);
    if (!result.isSuccess()) return result;
    UserBean bean = login(request, companyid, usercode);
    result.setData(bean);
    return result;
  }

  private UserBean login(HttpServletRequest request, String companyid, String usercode) {
    HttpSession session = request.getSession();
    String sessionid = session.getId();
    session.setMaxInactiveInterval(60 * 60 * 2); 
    UserBean bean = service.getUserInfo(companyid, usercode);
    session.setAttribute(Globals.SYSTEM_USER, bean);
    session.setAttribute(Globals.LOGINLOG, service.createLoginlog(bean.getUserid()));
    bean.setSessionid(sessionid);
    bean.setBasepath(request.getContextPath());
    SessionUtils.SessionContext.put(sessionid, session);
    return bean;
  }

  @SystemLogs("获取登录用户对象")
  @RequestMapping(value = "/getuserbean")
  @ResponseBody
  public UserBean getUserBean(HttpServletRequest request) {
    HttpSession session = request.getSession();
    return (UserBean) session.getAttribute(Globals.SYSTEM_USER);
  }


  @SystemLogs("用户登出")
  @RequestMapping(value = "/logout", method = RequestMethod.POST)
  @ResponseBody
  public String logout(HttpServletRequest request, HttpServletResponse response) {
    HttpSession session = request.getSession();
    String sessionid = session.getId();
    service.writeLogout((FUserloginlog) session.getAttribute(Globals.LOGINLOG),"正常登出");
    session.removeAttribute(Globals.LOGINLOG);
    SessionUtils.SessionContext.remove(sessionid);
    session.invalidate();
    return "true";
  }







  @RequestMapping(value = "/validatecode")
  @ResponseBody
  public void generateValidateCode(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setHeader("Cache-Control", "no-cache");
    String verifyCode = ValidateCode.generateTextCode(ValidateCode.TYPE_NUM_ONLY, 4, null).toLowerCase();
    request.getSession().setAttribute(VALIDATECODE, verifyCode);
    response.setContentType("image/jpeg");
    BufferedImage bim = ValidateCode.generateImageCode(verifyCode);
    ImageIO.write(bim, "JPEG", response.getOutputStream());
  }



  @RequestMapping(value = "/getsysteminfo")
  @ResponseBody
  public Map<String, Object> getFSysteminfo(String companyid) {
    return service.getFSysteminfo(companyid);
  }


  @RequestMapping(value = "/getuserfavicon")
  public void getUserFavicon(String userid) throws IOException {
    systemFrameService.getUserFavicon(userid);
  }

}
