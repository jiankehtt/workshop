package com.rxtx.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


import org.apache.log4j.Logger;


public class PropertyUtil {
	private static final Logger logger = Logger.getLogger(PropertyUtil.class);
	private static Properties properties;
	static {
		// 加载属性文件
		try {
			String base = System.getProperty("user.dir");
			base += "\\src\\main\\resource\\test";	// 用于debug
			InputStream inputStream = new FileInputStream(base + File.separator + "config.properties");
			try {
				properties = new Properties();
				properties.load(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				inputStream.close();
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
	}

	public static String getProperty(String key) {
		return properties.getProperty(key);
	}
	/**
	 * 加载整型配置
	 * @param key 配置名称
	 * @return 配置值
	 */
	public static int getIntProperty(String key) {
		return getIntProperty(key, 0);
	}

	/**
	 * 加载整型配置
	 * @param key 配置名称
	 * @param def 默认值
	 * @return 配置值
	 */
	public static int getIntProperty(String key, int def) {
		String value = properties.getProperty(key);
		if(StringUtil.isNotEmpty(value))
			try {
				return Integer.valueOf(value);
			} catch (NumberFormatException e) {
				return def;
			}
		return def;
	}
}
