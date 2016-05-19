package com.zhengs.utils.redis;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * redis工具类
 * @author zhengshan
 * @Date 2016-5-19
 */
public final class RedisUtil {
	/**
	 * 日志对象
	 */
	private static final Logger logger = LogManager.getLogger();

	/**
	 * redis操作对象
	 */
	private RedisTemplate<Serializable, Object> redisTemplate;

	/**
	 * 批量删除对应的value
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param keys
	 */
	public void remove(final String... keys) {
		for (String key : keys) {
			remove(key);
		}
	}

	/**
	 * 批量删除key
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param pattern
	 */
	public void removePattern(final String pattern) {
		Set<Serializable> keys = redisTemplate.keys(pattern);
		if (keys.size() > 0)
			redisTemplate.delete(keys);
	}

	/**
	 * 删除对应的value
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param key 键 
	 */
	public void remove(final String key) {
		if (exists(key)) {
			redisTemplate.delete(key);
		}
	}

	/**
	 * 断缓存中是否有对应的value
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param key 键
	 *  @return true或false
	 */
	public boolean exists(final String key) {
		return redisTemplate.hasKey(key);
	}

	/**
	 * 读取缓存
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param key 键
	 *  @return value值
	 */
	public Object get(final String key) {
		Object result = null;
		ValueOperations<Serializable, Object> operations = redisTemplate
				.opsForValue();
		result = operations.get(key);
		return result;
	}

	/**
	 * 写入缓存
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param key 键
	 *  @param value 值
	 *  @return true或false
	 */
	public boolean set(final String key, Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 写入缓存
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param key 键
	 *  @param value 值
	 *  @param expireTime 失效时间
	 *  @return true或false
	 */
	public boolean set(final String key, Object value, Long expireTime) {
		boolean result = false;
		try {
			ValueOperations<Serializable, Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 注入redisTemplate对象
	 *	@author zhengshan
	 *	@Date 2016-5-19
	 *  @param redisTemplate
	 */
	public void setRedisTemplate(RedisTemplate<Serializable, Object> redisTemplate) {
		this.redisTemplate = redisTemplate;
	}
}