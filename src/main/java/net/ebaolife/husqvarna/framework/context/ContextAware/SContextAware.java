package net.ebaolife.husqvarna.framework.context.ContextAware;

import org.springframework.web.context.ServletContextAware;

import javax.servlet.ServletContext;


public class SContextAware implements ServletContextAware{

	private static ServletContext servletContext;

	@Override
	public void setServletContext(ServletContext servletContext) {
		SContextAware.servletContext = servletContext;
	}

	public static ServletContext getServletContext() {
		return servletContext;
	}

}