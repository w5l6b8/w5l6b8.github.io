package net.ebaolife.husqvarna.framework.context.ContextAware;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class MvcContextAware implements ApplicationContextAware{
	
	private static ApplicationContext appCtx;

	public void setApplicationContext(ApplicationContext context) {
		appCtx = context;
	}

	public static ApplicationContext getApplicationContext() {
		return appCtx;
	}
}