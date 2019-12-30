package net.ebaolife.husqvarna.framework.interceptor.transcoding;

import java.lang.annotation.*;
import java.util.Map;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestList {
	Class<?> clazz() default Map.class;

	String value() default "_def_param_list";
}