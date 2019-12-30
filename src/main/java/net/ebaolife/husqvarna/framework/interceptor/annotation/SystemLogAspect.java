package net.ebaolife.husqvarna.framework.interceptor.annotation;

import org.mp4parser.aspectj.lang.JoinPoint;
import org.mp4parser.aspectj.lang.ProceedingJoinPoint;
import org.mp4parser.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Aspect
public class SystemLogAspect {

	private static final Logger logger = LoggerFactory.getLogger(SystemLogAspect.class);

	@Pointcut("execution(* net.ebaolife..service.*.*(..))")
	public void serviceAspect() {
	}

	@Pointcut("execution(* net.ebaolife..controller.*.*(..))")
	public void controllerAspect() {
	}

	@Before(value = "controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		try {

		} catch (Exception e) {
			e.printStackTrace();
			logger.error("异常信息:{}", e.getMessage());
		}
	}

	@Around(value = "controllerAspect()")
	public Object aroundPointcut(ProceedingJoinPoint pjp) throws Throwable {
		Object retVal = pjp.proceed();
		return retVal;
	}

	@AfterThrowing(pointcut = "controllerAspect()", throwing = "e")
	public void doControllerAfterThrowing(JoinPoint joinPoint, Throwable e) {
		writeErrorLog(joinPoint, e);
	}

	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doServiceAfterThrowing(JoinPoint joinPoint, Throwable e) {
		writeErrorLog(joinPoint, e);
	}

	private void writeErrorLog(JoinPoint joinPoint, Throwable e) {
		try {

		} catch (Exception ex) {

			System.out.println("异常信息:" + ex.getMessage());
			logger.error("异常信息:{}", ex.getMessage());
		}
	}
}