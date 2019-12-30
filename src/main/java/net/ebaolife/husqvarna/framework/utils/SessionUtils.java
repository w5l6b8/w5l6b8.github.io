package net.ebaolife.husqvarna.framework.utils;

import net.ebaolife.husqvarna.framework.bean.UserBean;
import net.ebaolife.husqvarna.framework.exception.ProjectException;

import javax.servlet.http.HttpSession;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;

public class SessionUtils {

	public static final Hashtable<String, HttpSession> SessionContext = new Hashtable<String, HttpSession>();

	public static boolean isOnlineUser(String userid) {
		boolean result = false;
		Iterator<Entry<String, HttpSession>> it = SessionContext.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HttpSession> entry = it.next();
			HttpSession session = entry.getValue();
			try {
				UserBean bean = (UserBean) session.getAttribute(Globals.SYSTEM_USER);
				if (bean == null)
					continue;
				if (bean.getUserid().equals(userid)) {
					result = true;
					break;
				}
			} catch (IllegalStateException e) {
				it.remove();
			}
		}
		return result;
	}

	public synchronized static void invalidateOnlineUser(String userid) {
		Iterator<Entry<String, HttpSession>> it = SessionContext.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HttpSession> entry = it.next();
			HttpSession session = entry.getValue();
			try {
				UserBean bean = (UserBean) session.getAttribute(Globals.SYSTEM_USER);
				if (bean == null)
					throw new ProjectException();
				if (bean.getUserid().equals(userid)) {
					invalidateOnlineUser(session);
					it.remove();
				}
			} catch (Exception e) {
				try {
					session.invalidate();
				} catch (Exception ex) {
				}
				it.remove();
			}
		}
	}

	public synchronized static void invalidateOnlineUser(HttpSession session) {
		session.invalidate();
	}

	public static HttpSession getHttpSession(String userid) {
		Iterator<Entry<String, HttpSession>> it = SessionContext.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HttpSession> entry = it.next();
			HttpSession session = entry.getValue();
			try {
				UserBean bean = (UserBean) session.getAttribute(Globals.SYSTEM_USER);
				if (bean == null)
					continue;
				if (bean.getUserid().equals(userid)) {
					return session;
				}
			} catch (IllegalStateException e) {
				it.remove();
			}
		}
		return null;
	}

	public static UserBean getUserBean(String userid) {
		Iterator<Entry<String, HttpSession>> it = SessionContext.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, HttpSession> entry = it.next();
			HttpSession session = entry.getValue();
			try {
				UserBean bean = (UserBean) session.getAttribute(Globals.SYSTEM_USER);
				if (bean == null)
					continue;
				if (bean.getUserid().equals(userid)) {
					return bean;
				}
			} catch (IllegalStateException e) {
				it.remove();
			}
		}
		return null;
	}
}
