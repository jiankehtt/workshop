package com.rxtx.logic;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.rxtx.comm.Constants;
import com.rxtx.db.JDBCUtils;
import com.rxtx.db.WsPortDb;
import com.rxtx.model.WsPort;
import com.rxtx.model.WsTask;
import com.rxtx.model.creater.WsListCreater;
import com.rxtx.utils.LogUtil;

public class WorkLogic {

	private Map<String, WsPort> wsPorts = new HashMap<String, WsPort>();
	private Logger logger = LogUtil.getLogger(WorkLogic.class);

	public void saveData(String rfid, String port) {
		int type = checkType(port);
		List<WsTask> wsTasks = JDBCUtils.getWsTasks(rfid, type);
		if (wsTasks != null && wsTasks.size() > 0) {
			WsTask ws = wsTasks.get(0);

			if (type == Constants.TYPE_REPAIR) {
				ws.setRepairStationNo(port);
				ws.setRepairBeginTime(System.currentTimeMillis());
			}
			if (type == Constants.TYPE_WASH) {
				ws.setWaitBeginTime(System.currentTimeMillis());
				ws.setWaitStationNo(port);
			}

			JDBCUtils.update(wsTasks.get(0), type);
		}

		JDBCUtils.add(WsListCreater.createWsList(rfid, port));
	}

	private int checkType(String port) {
		if (wsPorts.size() == 0) {
			wsPorts = WsPortDb.getWsPorts();
		}
		WsPort p = wsPorts.get(port);
		if (p == null) {
			wsPorts = WsPortDb.getWsPorts();
			logger.error("Port 没有登记！！！" + port);
			return 1;
		}

		return p.getPortType();
	}

}
