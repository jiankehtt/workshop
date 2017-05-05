package com.rxtx.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.rxtx.comm.Constants;
import com.rxtx.model.WsList;
import com.rxtx.model.WsTask;
import com.rxtx.utils.C3P0JdbcUtil;
import com.rxtx.utils.LogUtil;

public class JDBCUtils {
	
	static Connection conn;
	static PreparedStatement ps;
	static ResultSet rs;
	private static Logger logger = LogUtil.getLogger(JDBCUtils.class);

	/**
	 * 写一个连接数据库的方法
	 */
	public static Connection getConnection() {
		return C3P0JdbcUtil.getConnection();
	}

	/**
	 * 写一个查询数据库语句的方法
	 */
	public static List<WsTask> getWsTasks(String rfid, int type) {
		String q = "repair_station_no  is null";
		if (type == 2) {
			q = "wash_station_no  is null";
		}
		String sql = "select * from ws_tasks where  rfid like '%" + rfid + "%' and task_end_time is null  and " + q;
		List<WsTask> wsTasks = new ArrayList<WsTask>();
		logger.debug("sql  : "+sql);
		try {
			conn = getConnection();// 连接数据库
			ps = conn.prepareStatement(sql);// 2.创建Satement并设置参数
			rs = ps.executeQuery(sql); // 3.ִ执行SQL语句
			WsTask ws = null;
			// 4.处理结果集
			while (rs.next()) {
				ws = new WsTask();
				ws.setTaskId(rs.getInt("task_id"));
				ws.setRfId(rs.getString("rfid"));
				wsTasks.add(ws);
			}
		} catch (SQLException e) {
			logger.error("getWsTasks error 1 "+ps.toString()+" Ex: "+e.getMessage());
		} finally {
			C3P0JdbcUtil.release(conn, ps, rs);
		}
		return wsTasks;
	}

	public static void add(Set<WsList> wsLists) {
		String sql = "insert into ws_logs(rfid,com_port_no,task_id,created_at) values(?,?,?,?)";
		try {
			conn = getConnection();// 连接数据库
			ps = conn.prepareStatement(sql);// 2.创建Satement并设置参数
			for(WsList ws : wsLists){
				ps.setString(1, ws.getRfid());
				ps.setString(2, ws.getComport());
				ps.setInt(3, ws.getTaskId());
				ps.setTimestamp(4,  new Timestamp(ws.getAddtime()) );
				// 4.处理结果集
				ps.executeUpdate();
			}
		} catch (SQLException e) {
			logger.error("add WsList error "+ps.toString()+" Ex: "+e.getMessage());
		} finally {
			C3P0JdbcUtil.release(conn, ps, rs);
		}
	}

	/**
	 * @return修改数据
	 */
	public static int update(WsTask ws,int type) {
		int row = 0;
		
		String sql = "update ws_tasks set repair_begin_time=?,repair_station_no=?,task_status = 2  where task_id=?";
		if(type==2){
			sql = "update ws_tasks set wash_begin_time=?,wash_station_no=?,task_status = 3 where task_id=?";
		}
		
		try {
			conn = getConnection();// 连接数据库
			ps = conn.prepareStatement(sql);// 2.创建Satement并设置参数
			if(type==Constants.TYPE_REPAIR){
				ps.setTimestamp(1, new Timestamp(ws.getRepairBeginTime()));
				ps.setString(2, ws.getRepairStationNo());
			}
			if(type==Constants.TYPE_WASH){
				ps.setTimestamp(1, new Timestamp(ws.getWashBeginTime()));
				ps.setString(2, ws.getWashStationNo());
			}
			ps.setInt(3, ws.getTaskId());
			
			// 4.处理结果集
			row = ps.executeUpdate();
		} catch (SQLException e) {
			logger.error("update WsTask error 1  --"+row+"  "+ps.toString()+"  "+type);
			
		} finally {
			C3P0JdbcUtil.release(conn, ps, rs);
		}
		return row;
	}

}
