package net.ebaolife.husqvarna.framework.interceptor;


import net.ebaolife.husqvarna.framework.bean.ErrorType;
import net.ebaolife.husqvarna.framework.bean.UserBean;
import net.ebaolife.husqvarna.framework.critical.CriticalObject;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.utils.CommonUtils;
import net.ebaolife.husqvarna.framework.utils.Globals;
import net.ebaolife.husqvarna.framework.utils.ProjectUtils;
import net.ebaolife.husqvarna.framework.utils.SessionUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ValidLoginFilter implements Filter {

	public void doFilter(ServletRequest requ, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) requ;
		final HttpServletResponse response = (HttpServletResponse) resp;
		String url = request.getRequestURL().toString();
		boolean beFilter = true;
		for (String s : Globals.NOFILTERS) {
			if (url.endsWith(s)) {
				beFilter = false;
				break;
			}
		}
		if (beFilter) {
			HttpSession session = request.getSession();
			UserBean bean = (UserBean) session.getAttribute(Globals.SYSTEM_USER);
			if (bean == null || CommonUtils.isEmpty(bean.getUserid())) {
				String sessionid = request.getParameter("JSESSIONID");
				if (!CommonUtils.isEmpty(sessionid)) {
					HttpSession old_session = SessionUtils.SessionContext.get(sessionid);
					session.setAttribute(Globals.SYSTEM_USER, old_session.getAttribute(Globals.SYSTEM_USER));
				} else {
					ProjectUtils.sendError(ErrorType.E999, request, response);
					return;
				}
			}
		}
		setBasePath(request);
		setCriticalObject(request, response);
		chain.doFilter(request, response);
	}

	@Override
	public void destroy() {

	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {

	}

	private void setBasePath(HttpServletRequest request) {
		request.setAttribute(Globals.BASE_PATH, request.getContextPath());
	}

	private void setCriticalObject(final HttpServletRequest request, final HttpServletResponse response) {
		Local.setCriticalObject(new CriticalObject(request, response));
	}
}
