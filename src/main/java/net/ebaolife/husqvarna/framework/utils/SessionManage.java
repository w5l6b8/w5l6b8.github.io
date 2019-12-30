package net.ebaolife.husqvarna.framework.utils;


import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.entity.log.FUserloginlog;
import net.ebaolife.husqvarna.platform.service.LoginService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

public class SessionManage implements HttpSessionListener, ServletContextListener {

	private static final Log log = LogFactory.getLog(SessionManage.class);

	public SessionManage() {
		log.debug("SessionManage created");
	}

	@Override
	public void sessionCreated(HttpSessionEvent event) {
		log.debug("Session created:" + event.getSession().getId());
	}

	@Override
	public void sessionDestroyed(HttpSessionEvent event) {
		log.debug("Session Destroyed:" + event.getSession().getId());
		HttpSession session = event.getSession();
		if (session.getAttribute(Globals.LOGINLOG) != null) {
			LoginService loginLogic = Local.getBean(LoginService.class);
			if (loginLogic != null)
				loginLogic.writeLogout((FUserloginlog) event.getSession().getAttribute(Globals.LOGINLOG), "超时登出");
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
	}

}
