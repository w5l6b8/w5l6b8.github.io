package net.ebaolife.husqvarna.framework.utils;

import com.alibaba.fastjson.JSON;
import net.ebaolife.husqvarna.platform.logic.FUserLogic;
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;

import java.lang.reflect.Method;

public class ParameterNameUtils {
	private static ParameterNameDiscoverer parameterNameDiscoverer;

	public static void main(String[] args) throws Exception {
		Method[] method = FUserLogic.class.getMethods();
		System.out.println(JSON.toJSONString(getMethodParameterNames(method[2])));
	}

	public static String[] getMethodParameterNames(Method method) {
		final Class<?>[] parameterTypes = method.getParameterTypes();
		if (parameterTypes == null || parameterTypes.length == 0) {
			return null;
		}
		String[] parameterNames = new String[parameterTypes.length];
		try {
			if (parameterNameDiscoverer == null) {
				parameterNameDiscoverer = new LocalVariableTableParameterNameDiscoverer();
			}
			parameterNames = parameterNameDiscoverer.getParameterNames(method);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return parameterNames;
	}

}