package com.rxtx.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WorkshopListUtil {
	private static List<String> workList = null;
	private static Map<String, String> portsMap = new HashMap<String,String>();
	
	public static List<String> getList(){
		if(workList==null){
			workList =  Arrays.asList(PropertyUtil.getProperty("workList").split(";"));
		}
		return workList;
	}
	/**
	 * 从配置的队列中识别发卡器对应端口
	 * @param p
	 * @return
	 */
	public static String  getFKcom(String p){
		p = p.toUpperCase();
		if(StringUtil.isEmpty(portsMap.get(p))){
			for(String port: getList()){
				port = port.toUpperCase();
				if(port.contains(p)){
					portsMap.put(p,port.substring(0,port.indexOf("-")));
					break;
				}
			}
		}
		return portsMap.get(p);
	}
	
	
	public static void main(String[] args) {
		System.err.println(WorkshopListUtil.getFKcom("Com51"));
	}

}
