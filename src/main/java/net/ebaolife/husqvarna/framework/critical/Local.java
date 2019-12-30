package net.ebaolife.husqvarna.framework.critical;

import net.ebaolife.husqvarna.framework.bean.UserBean;
import net.ebaolife.husqvarna.framework.context.ContextAware.AppContextAware;
import net.ebaolife.husqvarna.framework.context.ContextAware.MvcContextAware;
import net.ebaolife.husqvarna.framework.context.ProjectContext;
import net.ebaolife.husqvarna.framework.context.ProjectSpace;
import net.ebaolife.husqvarna.framework.dao.Dao;
import net.ebaolife.husqvarna.framework.utils.PropertyPreFilters;
import org.springframework.beans.BeansException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Local {

	private static final ThreadLocal<CriticalObject> CriticalObjectStore = new ThreadLocal<CriticalObject>();

	public static Dao getDao() {
		return getCriticalObject().getDao();
	}

	public static boolean islogin() {
		return getUserBean() != null;
	}

	public static UserBean getUserBean() {
		CriticalObject obj = getCriticalObject();
		return obj == null ? null : obj.getUserBean();
	}

	public static String getCompanyid() {
		return getUserBean().getCompanyid();
	}

	public static String getUserid() {
		if (getUserBean() != null)
			return getUserBean().getUserid();
		else
			return null;
	}

	public static String getUsercode() {
		return getUserBean().getUsercode();
	}

	public static String getUsername() {
		return getUserBean().getUsername();
	}

	public static CriticalObject getCriticalObject() {
		return CriticalObjectStore.get();
	}

	public static void setCriticalObject(CriticalObject criticalObject) {
		CriticalObjectStore.set(criticalObject);
	}

	public static String getBasePath() {
		return getCriticalObject().getBasePath();
	}

	public static Object getBean(String name) {
		Object obj = null;
		try {
			obj = AppContextAware.getApplicationContext().getBean(name);
		} catch (BeansException e) {
			try {
				obj = MvcContextAware.getApplicationContext().getBean(name);
			} catch (BeansException e1) {
			}
		}
		return obj;
	}

	public static <T> T getBean(Class<T> requiredtype) {
		T bean = null;
		try {
			bean = AppContextAware.getApplicationContext().getBean(requiredtype);
		} catch (BeansException e) {
			bean = MvcContextAware.getApplicationContext().getBean(requiredtype);
		}
		return bean;
	}

	public static HttpServletRequest getRequest() {
		return getCriticalObject().getRequest();
	}

	public static HttpServletResponse getResponse() {
		return getCriticalObject().getResponse();
	}

	public static void writeJsonToHttpFilters(PropertyPreFilters features) {
		getCriticalObject().setFeatures(features);
	}

	public static PropertyPreFilters getJsonToHttpFilters() {
		if (getCriticalObject() == null)
			return null;
		return getCriticalObject().getFeatures();
	}

	public static void clearJsonToHttpFilters() {
		getCriticalObject().setFeatures(null);
	}

	public static ProjectSpace getProjectSpace() {
		return ProjectContext.getProjectSpace();
	}

}
