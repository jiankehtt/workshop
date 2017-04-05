package com.rxtx.logic;

import java.util.List;

import com.rxtx.comm.Constants;
import com.rxtx.db.JDBCUtils;
import com.rxtx.model.WsTask;
import com.rxtx.model.creater.WsListCreater;

public class WorkLogic {
	
	
	public void saveData(String rfid,String port){
		int type = checkType(port);
		List<WsTask> wsTasks = JDBCUtils.getWsTasks(rfid, type);
		if(wsTasks!=null&&wsTasks.size()>0){
		WsTask ws = wsTasks.get(0);
		
		if(type ==Constants.TYPE_REPAIR){
			ws.setRepairStationNo(port);
			ws.setRepairBeginTime(System.currentTimeMillis());
		}
		if(type==Constants.TYPE_WASH){
			ws.setWaitBeginTime(System.currentTimeMillis());
			ws.setWaitStationNo(port);
		}
		
		JDBCUtils.update(wsTasks.get(0), type);
		}
		
		JDBCUtils.add(WsListCreater.createWsList(rfid,port));
	}

	private int checkType(String port) {
		if("COM50".equals(port)){
			return 1;
		}
		return 2;
	}

}
