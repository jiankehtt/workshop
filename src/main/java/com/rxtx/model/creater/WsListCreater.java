package com.rxtx.model.creater;

import java.util.UUID;

import com.rxtx.model.WsList;

public class WsListCreater {
	public static WsList createWsList(String rfid,String port){
		  WsList ws = new WsList();
          ws.setGuid(UUID.randomUUID().toString().replaceAll("-", ""));
          ws.setRfid(rfid);
          ws.setComport(port);
          return ws;
	}
}
