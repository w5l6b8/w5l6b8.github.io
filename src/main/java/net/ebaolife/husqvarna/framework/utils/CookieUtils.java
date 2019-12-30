package net.ebaolife.husqvarna.framework.utils;

import net.ebaolife.husqvarna.framework.critical.Local;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtils {

	public static Cookie[] getCookies() {
		HttpServletRequest request = Local.getCriticalObject().getRequest();
		Cookie cookies[] = request.getCookies();
		return cookies;
	}

	public static String getCookie(String name) {
		HttpServletRequest request = Local.getCriticalObject().getRequest();
		String cookieValue = null;
		Cookie cookies[] = request.getCookies();
		if (cookies == null)
			return cookieValue;
		for (int i = 0; i < cookies.length; i++) {
			String cookieName = cookies[i].getName();
			if (cookieName.equalsIgnoreCase(name)) {
				cookieValue = cookies[i].getValue();
				continue;
			}
		}
		return cookieValue;
	}

	public static void delCookie(String name) {
		HttpServletRequest request = Local.getCriticalObject().getRequest();
		HttpServletResponse response = Local.getCriticalObject().getResponse();
		Cookie cookies[] = request.getCookies();
		if (cookies == null)
			return;
		for (int i = 0; i < cookies.length; i++) {
			String cookieName = cookies[i].getName();
			if (cookieName.equalsIgnoreCase(name)) {
				cookies[i].setMaxAge(0);
				response.addCookie(cookies[i]);
				break;
			}
		}
	}

	public static void delAllCookie() {
		HttpServletRequest request = Local.getCriticalObject().getRequest();
		HttpServletResponse response = Local.getCriticalObject().getResponse();
		Cookie cookies[] = request.getCookies();
		if (cookies == null)
			return;
		for (int i = 0; i < cookies.length; i++) {
			cookies[i].setMaxAge(0);
			response.addCookie(cookies[i]);
		}
	}

	public static void addCookie(String key, String value) {
		Cookie[] cookies = getCookies();
		boolean isnull = true;
		if (cookies == null)
			return;
		for (int i = 0; i < cookies.length; i++) {
			Cookie cookie = cookies[i];
			if (key.equalsIgnoreCase(cookie.getName())) {
				isnull = false;
				cookie.setValue(value);
				break;
			}
		}
		if (isnull) {
			HttpServletResponse response = Local.getCriticalObject().getResponse();
			Cookie cookie = new Cookie(key, value);
			cookie.setMaxAge(3600 * 24 * 30);
			response.addCookie(cookie);
		}
	}
}
