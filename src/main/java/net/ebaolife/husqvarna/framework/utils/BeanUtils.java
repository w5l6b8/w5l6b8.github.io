package net.ebaolife.husqvarna.framework.utils;

import org.apache.commons.beanutils.ContextClassLoaderLocal;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.beanutils.converters.SqlTimestampConverter;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.List;

public class BeanUtils {

	public static void copyProperties(Object dest, Object orig, String[] includes)
			throws IllegalAccessException, InvocationTargetException {
		MyBeanUtilsBean.getInstance(includes).copyProperties(dest, orig);
	}

	public static void copyProperties(Object dest, Object orig)
			throws IllegalAccessException, InvocationTargetException {
		MyBeanUtilsBean.getInstance(null).copyProperties(dest, orig);
	}

	public static void setData(Object object, String key, Object value) {
		String[] keys = key.split("[.]");
		if (keys.length > 1) {
			Object obj = object;
			try {
				for (int i = 0; i < keys.length - 1; i++) {
					obj = getLastField(obj, keys[i]);
				}
				String filedname = CommonUtils.underlineToCamelhump(keys[keys.length - 1]);
				Field field = obj.getClass().getDeclaredField(filedname);
				field.setAccessible(true);
				field.set(obj, value);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private static Object getLastField(Object object, String name) throws Exception {
		Class<?> clazz = object.getClass();
		Field field = clazz.getDeclaredField(name);
		field.setAccessible(true);
		Object childObj = field.get(object);
		if (childObj == null) {
			childObj = field.getType().newInstance();
			field.set(object, childObj);
		}
		return childObj;
	}
}

class MyBeanUtilsBean extends org.apache.commons.beanutils.BeanUtilsBean {
	private static List<String> includes = null;

	@SuppressWarnings("rawtypes")
	private static final ContextClassLoaderLocal BEANS_BY_CLASSLOADER = new ContextClassLoaderLocal() {
		protected Object initialValue() {
			return new MyBeanUtilsBean();
		}
	};

	public static MyBeanUtilsBean getInstance(String[] includes) {
		MyBeanUtilsBean.includes = includes == null ? null : Arrays.asList(includes);
		MyBeanUtilsBean beanUtilsBean = ((MyBeanUtilsBean) BEANS_BY_CLASSLOADER.get());
		ConvertUtilsBean cub = beanUtilsBean.getConvertUtils();
		cub.register(new DateConverter(null), java.util.Date.class);
		cub.register(new SqlTimestampConverter(null), java.sql.Timestamp.class);
		return beanUtilsBean;
	}

	public void copyProperty(Object bean, String name, Object value)
			throws IllegalAccessException, InvocationTargetException {
		if (!CommonUtils.isEmpty(includes) && !includes.contains(name))
			return;
		super.copyProperty(bean, name, value);
	}

}
