package net.ebaolife.husqvarna.framework.utils;

import com.alibaba.fastjson.JSON;
import net.ebaolife.husqvarna.framework.bean.DownloadFile;
import net.ebaolife.husqvarna.framework.bean.ErrorCode;
import net.ebaolife.husqvarna.framework.bean.ErrorType;
import net.ebaolife.husqvarna.framework.bean.ResultBean;
import net.ebaolife.husqvarna.framework.core.annotation.SystemLogs;
import net.ebaolife.husqvarna.framework.core.jdbc.support.mysql.MySQL5SqlFunction;
import net.ebaolife.husqvarna.framework.critical.Local;
import net.ebaolife.husqvarna.framework.dao.Dao;
import net.ebaolife.husqvarna.framework.dao.entity.dataobject.FDataobject;
import net.ebaolife.husqvarna.framework.exception.JavaException;
import org.hibernate.exception.ConstraintViolationException;
import org.hibernate.metamodel.internal.EntityTypeImpl;
import org.hibernate.metamodel.internal.MetamodelImpl;
import org.mp4parser.aspectj.lang.JoinPoint;
import org.springframework.web.servlet.LocaleResolver;

import javax.persistence.metamodel.EntityType;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectUtils {

	private final static Map<String, ResourceBundle> bundleMap = new HashMap<String, ResourceBundle>();

	private static Map<String, String> cfgMap = new HashMap<String, String>();

	private static Map<String, EntityTypeImpl<?>> entityTypeMap = null;

	private static Map<String, Map<String, Map<String, Object>>> indexMap = new HashMap<>();

	public static Locale getLanguage() {
		String local = CookieUtils.getCookie(Globals.COOKIE_LANGUAGE);
		local = CommonUtils.isEmpty(local) ? "zh" : local;
		return new Locale(local);
	}

	public static void setLanguage(LocaleResolver localeResolver, Locale local) {
		HttpServletResponse response = Local.getCriticalObject().getResponse();
		HttpServletRequest request = Local.getCriticalObject().getRequest();
		localeResolver.setLocale(request, response, local);
	}

	public static String getMessages(String key) {
		return getResourceBundle("messages/messages", ProjectUtils.getLanguage()).getString(key);
	}

	public static Map<String, String> getMessages(String[] keys) {
		Map<String, String> map = new HashMap<String, String>();
		if (keys == null)
			return map;
		for (int i = 0; i < keys.length; i++)
			map.put(keys[i], getMessages(keys[i]));
		return map;
	}

	public static Map<String, String> getAllMessages() {
		ResourceBundle bundle = getResourceBundle("messages/messages", ProjectUtils.getLanguage());
		Enumeration<String> em = bundle.getKeys();
		Map<String, String> map = new HashMap<String, String>();
		while (em.hasMoreElements()) {
			String key = em.nextElement();
			String value = bundle.getString(key);
			map.put(key, value);
		}
		return map;
	}

	public static String getInitParameter(String key) {
		String v = cfgMap.get(key);
		try {
			if (v == null) {
				v = getResourceBundle("application", null).getString(key);
				cfgMap.put(key, v);
			}
		} catch (Exception e) {
		}
		return v;
	}

	public static Map<String, String> getInitParameter(String[] keys) {
		Map<String, String> map = new HashMap<String, String>();
		if (keys == null)
			return map;
		ResourceBundle bundle = getResourceBundle("application", null);
		for (int i = 0; i < keys.length; i++)
			map.put(keys[i], bundle.getString(keys[i]));
		return map;
	}

	public static ResourceBundle getResourceBundle(String baseName, Locale locale) {
		String key = locale == null ? baseName : baseName + locale.toString();
		ResourceBundle bundle = bundleMap.get(key);
		if (bundle == null) {
			if (locale == null) {
				bundle = ResourceBundle.getBundle(baseName);
			} else {
				bundle = ResourceBundle.getBundle(baseName, locale);
			}
			bundleMap.put(key, bundle);
		}
		return bundle;
	}

	public static Map<String, EntityTypeImpl<?>> getEntityMap(Dao dao) {
		if (entityTypeMap == null) {
			entityTypeMap = new HashMap<String, EntityTypeImpl<?>>();
			MetamodelImpl metamodel = (MetamodelImpl) dao.getCurrentSession().getMetamodel();
			for (EntityType<?> entity : metamodel.getEntities()) {
				if (entity instanceof EntityTypeImpl) {
					EntityTypeImpl<?> impl = (EntityTypeImpl<?>) entity;
					entityTypeMap.put(impl.getName().toLowerCase(), impl);
				}
			}
		}
		return entityTypeMap;
	}

	public static Map<String, Map<String, Object>> getIndexMap(Dao dao, MySQL5SqlFunction sf, String schemename) {
		if (!indexMap.containsKey(schemename)) {
			indexMap.put(schemename, sf.getAllKeyInfo(dao, schemename));
		}
		return indexMap.get(schemename);
	}

	public static SystemLogs getSystemLogs(JoinPoint joinPoint) throws Exception {
		Annotation[] annotations = getAnnotation(joinPoint);
		return getSystemLogs(annotations);
	}

	public static SystemLogs getSystemLogs(Annotation[] annotations) throws Exception {
		SystemLogs slogs = null;
		for (int i = 0; i < annotations.length; i++) {
			if (annotations[i] instanceof SystemLogs) {
				slogs = (SystemLogs) annotations[i];
				break;
			}
		}
		return slogs;
	}

	public static Annotation[] getAnnotation(JoinPoint joinPoint) throws Exception {
		Annotation[] as = null;
		String classType = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Class<?> className = Class.forName(classType);
		Method[] methods = className.getMethods();
		for (int i = 0; i < methods.length; i++) {
			Method method = methods[i];
			if (method.getName().equals(methodName)) {
				as = method.getAnnotations();
				break;
			}
		}
		return as;
	}

	public static void writeJson(Object retVal) {
		writeData(retVal, true, true);
	}

	public static void writeHtml(Object retVal) {
		writeData(retVal, false, true);
	}

	public static void writeData(Object retVal, boolean isjson, boolean close) {
		HttpServletResponse response = Local.getResponse();
		PrintWriter out = null;
		try {
			out = response.getWriter();
			response.setCharacterEncoding("UTF-8");
			if (isjson) {
				response.setContentType("application/json; charset=utf-8");
				out.write(JSON.toJSONString(retVal));
			} else {
				response.setContentType("text/html;charset=UTF-8");
				out.write(String.valueOf(retVal));
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (close && out != null) {
				out.close();
			}
		}
	}

	public static void sendError(ErrorType type, HttpServletRequest request, HttpServletResponse response) {

		String head = request.getHeader("x-requested-with");
		try {
			if (head == null) {
				if (type == ErrorType.E999) {
					response.sendRedirect(request.getContextPath() + Globals.USER_LOGIN_BASEPATH);
				} else if (type == ErrorType.E998) {
					writeData("没有访问权限！", false, false);
				}
			} else {
				response.sendError(type.getValue());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getErrorMessage(Throwable throwable) {
		String msg = null;
		if (throwable instanceof JavaException) {
			msg = ((JavaException) throwable).getOriginalMessage();
		}
		if (msg == null || msg.length() == 0) {
			msg = throwable.getMessage();
		}
		return msg;
	}

	public static String getErrorStackTrace(Throwable throwable) {
		if (throwable instanceof JavaException) {
			return ((JavaException) throwable).toString();
		} else {
			String msg = throwable.toString();
			if (msg == null)
				msg = "";
			if (throwable != null) {
				StackTraceElement[] stackTrace = throwable.getStackTrace();
				if (stackTrace != null)
					for (int i = 0; i < stackTrace.length; i++)
						msg += "\n\t\tat " + stackTrace[i] + "";
			}
			return msg;
		}
	}

	public static void returnDownFile(DownloadFile df) throws IOException {
		HttpServletResponse response = Local.getResponse();
		OutputStream os = response.getOutputStream();
		response.setCharacterEncoding("utf-8");
		if (!CommonUtils.isEmpty(df.getContentType()))
			response.setContentType(df.getContentType());
		if (df.isDialog()) {
			if (CommonUtils.isEmpty(df.getFileName()))
				df.setFileName("unknown");
			response.setHeader("content-disposition",
					String.format("attachment;filename*=utf-8'zh_cn'%s", URLEncoder.encode(df.getFileName(), "utf-8")));
		}
		switch (df.getType()) {
		case 0:
			FileUtils.copy(df.getInputStream(), os);
			break;
		case 1:
			df.getExcel().write(os);
			os.close();
			break;
		}
	}

	public static void returnDownError(Exception e) {
		try {
			Local.getRequest().setAttribute(Globals.FILE_DOWNLOAD_ERROR, e.getMessage());
			RequestDispatcher dispatcher = Local.getRequest().getRequestDispatcher(Globals.ERROR_DOWNLOAD_BASEPATH);
			dispatcher.forward(Local.getRequest(), Local.getResponse());
		} catch (Exception e1) {

		}
	}

	public static ResultBean getErrorMassage(Exception e, FDataobject dataobject, Dao dao, MySQL5SqlFunction sf) {
		ResultBean result = new ResultBean(false, null);
		result.setErrorcode(ErrorCode.E500);
		Map<String, Object> resultMap = new HashMap<String, Object>();
		Map<String, Map<String, Object>> dataMap = getIndexMap(dao, sf, dataobject.getSchemaname());
		if (e.getCause() instanceof ConstraintViolationException) {
			ConstraintViolationException cve = (ConstraintViolationException) e.getCause();
			if (cve.getSQLState().equals("23000")) {

				String massage = cve.getCause().getMessage();
				if (!CommonUtils.isEmpty(massage) && massage.startsWith("Duplicate")) {
					Pattern pattern = Pattern.compile("Duplicate entry '(.*)' for key '(.*)'");
					Matcher matcher = pattern.matcher(massage);
					if (matcher.find()) {
						String value = "数据库已经存在此值，不能重复录入！";
						String key = matcher.group(2);
						String field = (String) dataMap.get(dataobject.getTablename().toLowerCase()).get(key);
						String fields[] = field.split(",");
						if (fields.length == 1) {
							resultMap.put(field, value);
						} else {
							for (int i = 0; i < fields.length; i++) {
								resultMap.put(fields[i], "联合主键重复!");
							}
						}
					}
				}
			}
		}
		result.setData(resultMap);
		return result;
	}
}
