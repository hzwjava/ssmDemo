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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.zhengs.bo.ResultDTO;
import com.zhengs.sysConfig.service.ISysConfService;

/**
 * 切点类
 * @author zhengshan
 * @Date 2016-5-17
 */
@Aspect
@Component
public class SystemLogAspect {

	/**
	 * 日志对象
	 */
	private static final Logger logger = LogManager.getLogger();

	/**
	 * 系统配置service
	 */
	@Autowired
	private ISysConfService sysConfServiceImpl;
	
	/**
	 * service层切点
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 */
	@Deprecated
	@Pointcut("@annotation(com.zhengs.aop.log.SystemServiceLog)")
	public void serviceAspect() {
	}

	/**
	 * Controller层切点
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 */
	@Pointcut("@annotation(com.zhengs.aop.log.SystemControllerLog)")
	public void controllerAspect() {
	}

	/**
	 * 前置通知 用于拦截Controller层记录用户的操作
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param joinPoint 切点
	 */
	@Deprecated
	@Before("controllerAspect()")
	public void doBefore(JoinPoint joinPoint) {
		//待实现
	}

	/**
	 * 异常通知 用于拦截service层记录异常日志
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param joinPoint 切点
	 *  @param e 运行时异常
	 */
	@Deprecated
	@AfterThrowing(pointcut = "serviceAspect()", throwing = "e")
	public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
		//待实现
	}

	/**
	 * 获取注解中对方法的描述信息 用于service层注解
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param joinPoint 切点
	 *  @return 获取service注解的字符串
	 *  @throws Exception 运行时异常
	 */
	@Deprecated
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
	 *	@author zhengshan
	 *	@Date 2016-5-17
	 *  @param joinPoint 切点
	 *  @return 获取controller注解的字符串
	 *  @throws Exception 运行时异常
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
	
	/**
	 * 环绕通知 用于拦截Controller层记录用户的操作
	 *	@author zhengsha
	 *	@Date 2016-5-17
	 *  @param point 切点
	 *  @return 结果集
	 *  @throws Throwable 运行异常
	 */
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
		
		//操作结果
		String success = "成功";
		ResultDTO dto = null;
		if(object instanceof ResultDTO){
			dto = (ResultDTO)object;
			success = dto.isFlag()  ? "成功" : "失败";
		}
		
		//操作描述
		String desc = getControllerMethodDescription(point);
		if(dto.getAct_desc() != null && !"".equals(dto.getAct_desc())){
			desc = dto.getAct_desc();
		}
		
		//操作用户
		String userName = sysConfServiceImpl.getSysConfig("username");
		
		
		//记录操作日志
		logger.debug(userName+"-"+desc + dto.getAct_object_name() + success);
		
		return object;
	}
}