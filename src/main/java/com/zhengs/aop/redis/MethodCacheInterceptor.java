package com.zhengs.aop.redis;

import java.util.ArrayList;
import java.util.List;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.zhengs.utils.redis.RedisUtil;

/**
 * 切面MethodCacheInterceptor，这是用来给不同的方法来加入判断如果缓存存在数据，从缓存取数据。否则第一次从数据库取，并将结果保存到缓存
 * @author zhengshan
 * @Date 2016-5-19
 */
public class MethodCacheInterceptor implements MethodInterceptor {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LogManager.getLogger();

	/**
	 * redis工具类
	 */
	private RedisUtil redisUtil;
	
	/**
	 * 不加入缓存的service名称
	 */
	private List<String> targetNamesList = new ArrayList<String>();
	
	/**
	 * 不加入缓存的方法名称
	 */
	private List<String> methodNamesList = new ArrayList<String>();

	/**
	 * 初始化读取不需要加入缓存的类名和方法名称
	 */
	public MethodCacheInterceptor() {
		
	}

	@Override
	public Object invoke(MethodInvocation invocation) throws Throwable {
		Object value = null;

		String targetName = invocation.getThis().getClass().getName();
		String methodName = invocation.getMethod().getName();
		
		// 不需要缓存的内容
		if(!isAddCache(targetName, methodName)) {
			// 执行方法返回结果
			return invocation.proceed();
		}
		Object[] arguments = invocation.getArguments();
		String key = getCacheKey(targetName, methodName, arguments);

		try {
			// 判断是否有缓存
			if (redisUtil.exists(key)) {
				return redisUtil.get(key);
			}
			
			// 写入缓存
			value = invocation.proceed();
			if (value != null) {
				final String tkey = key;
				final Object tvalue = value;
				new Thread(new Runnable() {
					@Override
					public void run() {
						redisUtil.set(tkey, tvalue);
					}
				}).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
			if (value == null) {
				return invocation.proceed();
			}
		}
		
		return value;
	}

	/**
	 * 是否加入缓存
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param targetName 类名称
	 *  @param methodName 方法名称
	 *  @return
	 */
	private boolean isAddCache(String targetName, String methodName) {
		boolean flag = true;
		if (targetNamesList.contains(targetName) || methodNamesList.contains(methodName)) {
			flag = false;
		}
		return flag;
	}

	/**
	 * 创建缓存key
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param targetName 类名称
	 *  @param methodName 方法名称
	 *  @param arguments key值
	 *  @return
	 */
	private String getCacheKey(String targetName, String methodName, Object[] arguments) {
		StringBuffer sbu = new StringBuffer();
		if ((arguments != null) && (arguments.length != 0)) {
			for (int i = 0; i < arguments.length; i++) {
				if(sbu.length() != 0){
					sbu.append("_");
				}
				sbu.append(arguments[i]);
			}
		}
		return sbu.toString();
	}

	/**
	 * 注入工具类
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param redisUtil
	 */
	public void setRedisUtil(RedisUtil redisUtil) {
		this.redisUtil = redisUtil;
	}
}