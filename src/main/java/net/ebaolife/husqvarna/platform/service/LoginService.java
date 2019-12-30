package net.ebaolife.husqvarna.platform.service;


import net.ebaolife.husqvarna.framework.bean.ResultBean;
import net.ebaolife.husqvarna.framework.bean.UserBean;
import net.ebaolife.husqvarna.framework.core.annotation.SystemLogs;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.Dao;
import net.ebaolife.husqvarna.framework.dao.entity.log.FUserloginlog;
import net.ebaolife.husqvarna.framework.dao.entity.system.FCompany;
import net.ebaolife.husqvarna.framework.dao.entity.system.FSysteminfo;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.utils.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class LoginService {
	@Resource
	private Dao dao;

	@SystemLogs("用户登陆")
	public ResultBean login(String companyid, String usercode, String password, Boolean invalidate) {
		FUser userinfo = dao.findByPropertyFirst(FUser.class, "usercode", usercode, "companyid", companyid);
		if (userinfo == null)
			return new ResultBean(false, "2");
		password = MD5.MD5Encode(password + userinfo.getSalt());
		if (!password.equals(userinfo.getPassword()))
			return new ResultBean(false, "3");
		if (CommonUtils.isEmpty(userinfo.getIslocked()) || userinfo.getIslocked())
			return new ResultBean(false, "4");
		if (CommonUtils.isEmpty(userinfo.getIsvalid()) || !userinfo.getIsvalid())
			return new ResultBean(false, "5");
		String singleonline = ProjectUtils.getInitParameter("sys.singleOnline");
		if (singleonline.equals("true")) {
			if (CommonUtils.is(invalidate)) {
				SessionUtils.invalidateOnlineUser(userinfo.getUserid());
			} else if (SessionUtils.isOnlineUser(userinfo.getUserid())) {
				return new ResultBean(false, "7");
			}
		}

		return new ResultBean(true, "");
	}

	public FUserloginlog createLoginlog(String userid) {
		FUserloginlog loginlog = new FUserloginlog();
		loginlog.setFUser(dao.findById(FUser.class, userid));
		loginlog.setLogindate(new Date());
		loginlog.setIpaddress(CommonFunction.getIpAddr(Local.getRequest()));
		dao.save(loginlog);
		return loginlog;
	}

	public void writeLogout(FUserloginlog loginlog, String logouttype) {
		loginlog.setLogoutdate(new Date());
		loginlog.setLogouttype(logouttype);
		dao.update(loginlog);
	}

	@SystemLogs("查询登陆成功用户信息")
	public UserBean getUserInfo(String companyid, String usercode) {
		String sql = "select a.userid,a.usercode,a.username,a.usertype, "
				+ "  b.companyid,b.companyname,b.companylongname,b.levelid, "
				+ "  c.personnelid,c.personnelcode,c.personnelname, "
				+ "  d.orgid departmentid,d.OrgCode departmentcode,d.orgname departmentname  " + "  from f_user a  "
				+ "	inner join f_company b on a.companyid = b.companyid "
				+ "	left join f_personnel c on a.personnelid = c.personnelid "
				+ "	left join f_organization d on c.orgid = d.orgid" + "  where a.usercode = '" + usercode
				+ "' and a.companyid = '" + companyid + "'";
		List<UserBean> list = dao.executeSQLQuery(sql, UserBean.class);
		return list.size() == 0 ? null : list.get(0);
	}

	public Map<String, Object> getFSysteminfo(String companyid) {

		FCompany company = dao.findById(FCompany.class, companyid);

		List<FSysteminfo> infos = new ArrayList<FSysteminfo>(company.getFSysteminfos());
		FSysteminfo systeminfo = infos.get(0);

		Map<String, Object> cfg = new HashMap<String, Object>();

		Map<String, Object> companymap = new HashMap<String, Object>();
		companymap.put("companyid", company.getCompanyid());
		companymap.put("companyname", company.getCompanyname());
		companymap.put("companylongname", company.getCompanylongname());
		companymap.put("address", company.getAddress());
		companymap.put("linkmen", company.getLinkmen());
		companymap.put("telnumber", company.getTelnumber());
		companymap.put("servicedepartment", company.getServicedepartment());
		companymap.put("servicemen", company.getServicemen());
		companymap.put("servicetelnumber", company.getServicetelnumber());
		companymap.put("serviceqq", company.getServiceqq());
		companymap.put("serviceemail", company.getServiceemail());
		companymap.put("servicehomepage", company.getServicehomepage());
		cfg.put("company", companymap);

		Map<String, Object> systeminfomap = new HashMap<String, Object>();
		systeminfomap.put("systemname", systeminfo.getSystemname());
		systeminfomap.put("systemversion", systeminfo.getSystemversion());
		systeminfomap.put("iconurl", systeminfo.getIconurl());
		systeminfomap.put("iconcls", systeminfo.getIconcls());
		systeminfomap.put("systemaddition", systeminfo.getSystemaddition());
		systeminfomap.put("copyrightowner", systeminfo.getCopyrightowner());
		systeminfomap.put("copyrightinfo", systeminfo.getCopyrightinfo());
		systeminfomap.put("allowsavepassword", systeminfo.getAllowsavepassword());
		systeminfomap.put("savepassworddays", systeminfo.getSavepassworddays());
		systeminfomap.put("needidentifingcode", systeminfo.getNeedidentifingcode());
		systeminfomap.put("alwaysneedidentifingcode", systeminfo.getAlwaysneedidentifingcode());
		systeminfomap.put("forgetpassword", systeminfo.getForgetpassword());
		cfg.put("systeminfo", systeminfomap);

		return cfg;
	}

}
