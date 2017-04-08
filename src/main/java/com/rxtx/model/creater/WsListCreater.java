package com.rxtx.model.creater;

import java.util.UUID;

import com.rxtx.model.WsList;

public class WsListCreater {
	public static WsList createWsList(String rfid,String port,int taskid){
		  WsList ws = new WsList();
          ws.setGuid(UUID.randomUUID().toString().replaceAll("-", ""));
          ws.setRfid(rfid);
          ws.setComport(port);
          ws.setTaskId(taskid);
          return ws;
	}
}
