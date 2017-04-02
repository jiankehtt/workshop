package com.rxtx;

import java.io.File;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Main {

	private static Logger logger = Logger.getLogger(Main.class);
	public static void main(String[] args) {
		String base = System.getProperty("user.dir");
		base += "\\src\\main\\resource\\test";	// 用于debug
		PropertyConfigurator.configure(base + File.separator + "log4j.properties");
		
		for(int i=0;i<100;i++){
			logger.error("  "+i);
		}
//		SerialReader r = new SerialReader();
//		
//		List<String> ports = r.findPort();
//		for(String s: ports){
//			r.openSerialPort(s);
//			logger.error("s");
//			
//		}
	}
}
