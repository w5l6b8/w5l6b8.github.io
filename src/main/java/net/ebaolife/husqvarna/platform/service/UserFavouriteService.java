package net.ebaolife.husqvarna.platform.service;

import net.ebaolife.husqvarna.framework.bean.ActionResult;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.DaoImpl;
import net.ebaolife.husqvarna.framework.dao.entity.system.FUser;
import net.ebaolife.husqvarna.framework.dao.entity.viewsetting.*;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service

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
public class UserFavouriteService {

	@Resource
	private DaoImpl dao;

	public ActionResult setDefaultGridScheme(String schemeid) {
		FovGridscheme scheme = dao.findById(FovGridscheme.class, schemeid);
		FUser user = dao.findById(FUser.class, Local.getUserid());
		FovUserdefaultgridscheme ownscheme = null;
		for (FovUserdefaultgridscheme us : user.getFovUserdefaultgridschemes()) {
			if (us.getFovGridscheme().getFDataobject() == scheme.getFDataobject()) {
				ownscheme = us;
				break;
			}
		}
		if (ownscheme == null) {
			ownscheme = new FovUserdefaultgridscheme();
			ownscheme.setFUser(user);
		}
		ownscheme.setFovGridscheme(scheme);
		dao.save(ownscheme);
		return new ActionResult();
	}

	public ActionResult setDefaultFilterScheme(String schemeid) {
		FovFilterscheme scheme = dao.findById(FovFilterscheme.class, schemeid);
		FUser user = dao.findById(FUser.class, Local.getUserid());
		FovUserdefaultfilterscheme ownscheme = null;
		for (FovUserdefaultfilterscheme us : user.getFovUserdefaultfilterschemes()) {
			if (us.getFovFilterscheme().getFDataobject() == scheme.getFDataobject()) {
				ownscheme = us;
				break;
			}
		}
		if (ownscheme == null) {
			ownscheme = new FovUserdefaultfilterscheme();
			ownscheme.setFUser(user);
		}
		ownscheme.setFovFilterscheme(scheme);
		dao.save(ownscheme);
		return new ActionResult();
	}

	public ActionResult setDefaultNavigateScheme(String schemeid) {

		FovGridnavigatescheme scheme = dao.findById(FovGridnavigatescheme.class, schemeid);
		FUser user = dao.findById(FUser.class, Local.getUserid());
		FovUserdefaultnavigatescheme ownscheme = null;
		for (FovUserdefaultnavigatescheme us : user.getFovUserdefaultnavigateschemes()) {
			if (us.getFovGridnavigatescheme().getFDataobject() == scheme.getFDataobject()) {
				ownscheme = us;
				break;
			}
		}
		if (ownscheme == null) {
			ownscheme = new FovUserdefaultnavigatescheme();
			ownscheme.setFUser(user);
		}
		ownscheme.setFovGridnavigatescheme(scheme);
		dao.save(ownscheme);
		return new ActionResult();
	}

}
