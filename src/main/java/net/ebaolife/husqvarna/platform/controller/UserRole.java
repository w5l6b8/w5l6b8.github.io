package net.ebaolife.husqvarna.platform.controller;


import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.bean.TreeNode;
import net.ebaolife.husqvarna.platform.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/platform/userrole")

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
public class UserRole {
  @Autowired
  private UserRoleService userRoleService;


  @RequestMapping("/getrolelimit")
  public @ResponseBody List<TreeNode> getRoleLimitTree(String roleid) {
    return userRoleService.getRoleLimitTree(roleid);

  }


  @RequestMapping("/saverolelimit")
  public @ResponseBody
  ActionResult saveRoleLimit(String roleid, String ids) {
    try {
      return userRoleService.saveRoleLimit(roleid, ids);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }


  @RequestMapping("/getuserlimit")
  public @ResponseBody List<TreeNode> getUserLimitTree(String roleid) {
    return userRoleService.getUserLimitTree(roleid);

  }


  @RequestMapping("/getuseralllimit")
  public @ResponseBody List<TreeNode> getUserAllLimitTree(String roleid) {
    return userRoleService.getUserAllLimitTree(roleid);
  }
  
  
  @RequestMapping("/saveuserlimit")
  public @ResponseBody ActionResult saveUserLimit(String roleid, String ids) {
    try {
      return userRoleService.saveUserLimit(roleid, ids);
    } catch (Exception e) {
      e.printStackTrace();
      throw e;
    }
  }






  @RequestMapping("/updateadditionfunctiontocmodule")
  public @ResponseBody ActionResult updateAdditionFunctionToCModule(String functionid) {
    return userRoleService.updateAdditionFunctionToCModule(functionid);

  }



}
