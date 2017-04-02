package com.rxtx.utils;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class LogUtil {
	
	public static Logger getLogger(Class<?> cls){
		return Logger.getLogger(cls);
	}
	
	public static void init(){
		String base = System.getProperty("user.dir");
		base += "\\src\\main\\resource\\test";	// 用于debug
		PropertyConfigurator.configure(base + File.separator + "log4j.properties");
		
	}
}
