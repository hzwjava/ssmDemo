package com.zhengs.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;

/**
 * 配置文件工具类
 * @author zhengshan
 * @Date 2016-5-18
 */
public class PropertiesUtil {

	/**
	 * 配置文件对象
	 */
	private static Properties p = new Properties();
	
	static {
		try {
			URL url = PropertiesUtil.class.getClassLoader().getResource("/config/mongoDb.properties");
			InputStream in = new BufferedInputStream(new FileInputStream(url.getFile()));
			p.load(in);
		} catch (IOException e) {
			System.out.println("读取配置信息出错！");
		}
	}

	/**
	 * 根据key得到value的值
	 *	@author zhengshan
	 *	@Date 2016-5-18
	 *  @param key
	 *  @return
	 */
	public static String getValue(String key) {
		return p.getProperty(key);
	}
}