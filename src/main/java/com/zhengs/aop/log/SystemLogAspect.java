package com.zhengs.aop.log;

import java.lang.reflect.Method;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import com.zhengs.bo.ResultDTO;

/**
 * 切点类
 * 
 * @version 1.0
 */
@Aspect
@Component
public class SystemLogAspect {
	// 本地异常日志记录对象
	private static final Logger logger = LogManager.getLogger();

	// Service层切点
	@Pointcut("@annotation(com.zhengs.aop.log.SystemServiceLog)")
	public void serviceAspect() {
	}

	// Controller层切点
	@Pointcut("@annotation(com.zhengs.aop.log.SystemControllerLog)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 * 
	 * @param joinPoint
	 *            切点
	 */
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//		HttpSession session = request.getSession();
		// 读取session中的用户
		// User user = (User) session.getAttribute(WebConstants.CURRENT_USER);
		// 请求的IP
//		String ip = request.getRemoteAddr();
		try {
			String userName = "张三";
			String method = joinPoint.getTarget().getClass().getName() + "."
					+ joinPoint.getSignature().getName() + "()";
			String desc = getControllerMethodDescription(joinPoint);
			String success = "成功";

			logger.error(method + ":" + userName + desc + success);
		} catch (Exception e) {
			// 记录本地异常日志
			logger.error("==前置通知异常==");
			logger.error("异常信息:{}", e.getMessage());
		}
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 * 
	 * @param joinPoint
	 * @param e
	 */
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		System.out.println("serviceAspect");
	}

	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getServiceMthodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemServiceLog.class).description();
					break;
				}
			}
		}
		return description;
	}

	/**
	 * 获取注解中对方法的描述信息 用于Controller层注解
	 * 
	 * @param joinPoint
	 *            切点
	 * @return 方法描述
	 * @throws Exception
	 */
	public static String getControllerMethodDescription(JoinPoint joinPoint) throws Exception {
		String targetName = joinPoint.getTarget().getClass().getName();
		String methodName = joinPoint.getSignature().getName();
		Object[] arguments = joinPoint.getArgs();
		Class targetClass = Class.forName(targetName);
		Method[] methods = targetClass.getMethods();
		String description = "";
		for (Method method : methods) {
			if (method.getName().equals(methodName)) {
				Class[] clazzs = method.getParameterTypes();
				if (clazzs.length == arguments.length) {
					description = method.getAnnotation(SystemControllerLog.class).description();
					break;
				}
			}
		}
		return description;
	}
	
	@Around("controllerAspect()")
	public Object around(ProceedingJoinPoint point) throws Throwable {
		Object[] method_param = null;
		Object object;
		try {
			method_param = point.getArgs();	//获取方法参数 
			object = point.proceed();
		} catch (Exception e) {
			// 异常处理记录日志..log.error(e);
			throw e;
		}
		
		String userName = "张三";
		String desc = getControllerMethodDescription(point);
		String success = "成功";
		ResultDTO dto = null;
		if(object instanceof ResultDTO){
			dto = (ResultDTO)object;
			success = dto.isFlag()  ? "成功" : "失败";
		}
		if(dto.getAct_desc() != null && !"".equals(dto.getAct_desc())){
			desc = dto.getAct_desc();
		}
		logger.error(userName + desc + dto.getAct_object_name() + success);
		
		return object;
	}
}