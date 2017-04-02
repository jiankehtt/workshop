package com.rxtx;


import java.util.List;

import com.rxtx.utils.LogUtil;


public class Main {

	public static void main(String[] args) {
		LogUtil.init();
		SerialReader r = new SerialReader();
		List<String> ports = r.findPort();
		for(String s: ports){
			r.openSerialPort(s);
		}
	}
}
