package net.ebaolife.husqvarna.platform.service;


import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.bean.TreeNode;
import net.ebaolife.husqvarna.framework.bean.UploadFileBean;
import net.ebaolife.husqvarna.framework.core.annotation.SystemLogs;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.system.FPersonnel;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.utils.CommonUtils;
import net.ebaolife.husqvarna.framework.utils.FileUtils;
import net.ebaolife.husqvarna.framework.utils.MD5;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemFrameService {

	@Resource
	private DaoImpl dao;

	@SystemLogs("获取系统左侧树形菜单")
	public List<TreeNode> getMenuTree() {
		String userid = Local.getUserid();
		String usertype = Local.getUserBean().getUsertype();
		String companyid = Local.getCompanyid();
		String sql = "select" + "	 a.menuid,a.menuname as text,a.parentid as parentId,a.icon,a.iconCls,a.iconColor, "
				+ "	 c.moduletype as type,c.modulesource as url,c.objectid,a.menutype,a.orderno,a.isexpand as expanded "
				+ " from" + " 	 f_companymenu a,f_companymodule b,f_module c " + " where a.companyid = b.companyid "
				+ " 	 and a.cmoduleid = b.cmoduleid " + " 	 and b.moduleid = c.moduleid "
				+ " 	 and a.companyid = '" + companyid + "' ";
		if (!usertype.equals("01")) {
			sql += " and (" + " 	   (" + " 		  select count(1) c "
					+ " 			 from f_modulefunction mf1,f_userfunctionlimit ufl "
					+ " 			 where mf1.cmoduleid = b.cmoduleid and mf1.functionid = ufl.functionid and ufl.userid = '"
					+ userid + "' " + " 	   ) > 0" + "     or " + "     ( " + " 		  select count(1) c "
					+ " 			 from f_modulefunction mf2,f_rolefunctionlimit rfl,f_userrole ur "
					+ " 			 where mf2.cmoduleid = b.cmoduleid and mf2.functionid = rfl.functionid and rfl.roleid = ur.roleid and ur.userid ='"
					+ userid + "' " + " 	   ) > 0" + " ) ";
		}
		sql += " order by a.orderno";
		List<TreeNode> dataList = dao.executeSQLQuery(sql, TreeNode.class);
		Map<String, TreeNode> parentMap = new HashMap<String, TreeNode>();
		for (TreeNode node : dataList) {
			if (!CommonUtils.isEmpty(node.getParentId())) {
				parentNode(parentMap, node);
			}
		}
		for (String key : parentMap.keySet()) {
			dataList.add(parentMap.get(key));
		}
		return dataList;
	}

	private void parentNode(Map<String, TreeNode> parentMap, TreeNode node) {
		String sql = "select a.menuid,a.menuname as text,a.parentid as parentId,"
				+ " a.icon,a.iconCls,a.iconColor,'00' as type,a.isexpand as expanded ,a.menutype,a.orderno"
				+ "  from f_companymenu a where a.menuid = ?  order by a.orderno ";
		TreeNode parentNode = dao.executeSQLQueryFirst(sql, TreeNode.class, node.getParentId());
		if (!CommonUtils.isEmpty(parentNode) && !parentMap.containsKey(parentNode.getMenuid())) {
			parentMap.put(parentNode.getMenuid(), parentNode);
			if (!CommonUtils.isEmpty(parentNode.getParentId())) {
				parentNode(parentMap, parentNode);
			}
		}
	}

	public void getUserFavicon(String userid) throws IOException {
		FUser user = dao.findById(FUser.class, userid == null ? Local.getUserid() : userid);
		FPersonnel personnel = user.getFPersonnel();
		HttpServletResponse response = Local.getResponse();
		if (personnel != null && personnel.getFavicon() != null && personnel.getFavicon().length > 0) {
			FileUtils.copy(new ByteArrayInputStream(personnel.getFavicon()), response.getOutputStream());
		} else {
			File defaultusericon = new File(Local.getProjectSpace().getImages(), "system/defaultuserfavicon.jpg");
			FileUtils.copy(defaultusericon, response.getOutputStream());
		}
	}

	@Transactional(propagation = Propagation.REQUIRED)
	public ActionResult uploadImageFileAndReturn(UploadFileBean uploadExcelBean, BindingResult bindingResult,
												 HttpServletRequest request) {
		ActionResult result = new ActionResult();
		InputStream is;
		try {
			is = uploadExcelBean.getFile().getInputStream();
			byte[] buffer = new byte[(int) uploadExcelBean.getFile().getSize()];
			is.read(buffer, 0, (int) uploadExcelBean.getFile().getSize());
			result.setMsg(buffer);
		} catch (IOException e) {
			e.printStackTrace();
			result.setSuccess(false);
			result.setMsg("上传的文件接收失败，可能是文件太大");
		}
		return result;
	}

	public ActionResult resetPassword(String userid) {
		FUser user = dao.findById(FUser.class, userid);
		user.setPassword(MD5.MD5Encode("123456" + user.getSalt()));
		dao.update(user);
		return new ActionResult();
	}

	public ActionResult changePassword(String oldPassword, String newPassword) {
		ActionResult result = new ActionResult();
		FUser user = dao.findById(FUser.class, Local.getUserid());
		String oldmd5 = MD5.MD5Encode(oldPassword + user.getSalt());
		if (oldmd5.equals(user.getPassword())) {
			user.setPassword(MD5.MD5Encode(newPassword + user.getSalt()));
			dao.update(user);
		} else {
			result.setSuccess(false);
		}

		return result;
	}
}
