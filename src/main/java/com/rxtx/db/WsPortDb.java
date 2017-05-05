package com.rxtx.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.rxtx.model.WsPort;

public class WsPortDb {
	
	/***
	 * 获取端口列表
	 * @return
	 */
	public static Map<String, WsPort> getWsPorts() {
		String sql = "select * from ws_com_ports ";
		List<WsPort> wsPorts = new ArrayList<WsPort>();
		Connection conn = null;
		ResultSet rs = null;
		Map<String, WsPort> ports = new HashMap<String, WsPort>();
		try {
			conn = JDBCUtils.getConnection();// 连接数据库
			rs = conn.createStatement().executeQuery(sql); // 3.ִ执行SQL语句
			WsPort ws = null;
			// 4.处理结果集
			while (rs.next()) {
				ws = new WsPort();
				ws.setId(rs.getInt("com_port_id"));
				ws.setPortName(rs.getString("com_port_no"));
				ws.setPortType(rs.getString("com_port_type"));
				ws.setPortSerial(rs.getString("com_port_serial"));
				wsPorts.add(ws);
			}
			
			for(WsPort port : wsPorts){
				ports.put(port.getPortName(), port);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			// 释放资源
			try {
				rs.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return ports;
	}

}
