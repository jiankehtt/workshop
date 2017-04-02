package com.rxtx;


import java.util.List;

import org.apache.log4j.Logger;

import com.rxtx.utils.LogUtil;


public class Main {
	static Logger logger = LogUtil.getLogger(Main.class);
	public static void main(String[] args) {
		LogUtil.init();

		SerialReader r = new SerialReader();
		final List<String> ports = r.findPort();
		for(final String port:ports){
			SerialReader w = new SerialReader();
			w.openSerialPort(port);
		}
	}
}
