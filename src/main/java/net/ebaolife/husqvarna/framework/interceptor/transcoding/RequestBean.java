package net.ebaolife.husqvarna.framework.interceptor.transcoding;

import java.lang.annotation.*;

@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RequestBean {
	String value() default "_def_param_bean";
}