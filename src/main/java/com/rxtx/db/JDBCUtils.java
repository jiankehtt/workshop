package com.rxtx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.rxtx.comm.Constants;
import com.rxtx.model.WsList;
import com.rxtx.model.WsTask;
import com.rxtx.utils.LogUtil;
import com.rxtx.utils.PropertyUtil;
import com.rxtx.utils.StringUtil;

public class JDBCUtils {
	private static String url = "";
	private static String userName = "";
	private static String password = "";
	static Connection conn;
	static PreparedStatement ps;
	static ResultSet rs;
	private static Logger logger = LogUtil.getLogger(JDBCUtils.class);

	/**
	 * 写一个连接数据库的方法
	 */
	public static Connection getConnection() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			if (StringUtil.isEmpty(url)) {
				url = PropertyUtil.getProperty("dburl");
				userName = PropertyUtil.getProperty("dbuser");
				password = PropertyUtil.getProperty("dbpasswd");
			}
			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			logger.error("Exception " + e.getMessage());
		}
		return conn;
	}

	/**
	 * 写一个查询数据库语句的方法
	 */
	public static List<WsTask> getWsTasks(String rfid, int type) {
		String q = "repair_station_no  is null";
		if (type == 2) {
			q = "wash_station_no  is null";
		}
		String sql = "select * from ws_tasks where  rf_id = '" + rfid + "' and task_end_time is null  and " + q;
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
				ws.setRfId(rs.getString("rf_id"));
				wsTasks.add(ws);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			// 释放资源
			try {
				rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return wsTasks;
	}

	public static void add(WsList wsList) {
		String sql = "insert into ws_list(guid,rfid,comPort,task_id) values(?,?,?,?)";
		try {
			conn = getConnection();// 连接数据库
			ps = conn.prepareStatement(sql);// 2.创建Satement并设置参数
			ps.setString(1, wsList.getGuid());
			ps.setString(2, wsList.getRfid());
			ps.setString(3, wsList.getComport());
			ps.setInt(4, wsList.getTaskId());
			
			// 4.处理结果集
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				ps.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

		}
	}

	/**
	 * @return修改数据
	 */
	public static int update(WsTask ws,int type) {
		int row = 0;
		
		String sql = "update ws_tasks set repair_begin_time=?,repair_station_no=? where task_id=?";
		if(type==2){
			sql = "update ws_tasks set wash_begin_time=?,wash_station_no=? where task_id=?";
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
			logger.error("update  --"+row+"  "+sql+"  "+type);
		} catch (SQLException e) {
			logger.error("update  --"+row+"  "+sql+"  ");
			
		} finally {
			// 释放资源
			try {
				// rs.close();
				ps.close();
				conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return row;
	}

	// /**
	// * @return删除操作
	// */
	// public int delete(UserInfo user){
	// UserInfo u;
	// int row=0;
	//// j.Connection();
	// String sql="delete from userInfo where userId=?";
	// try {
	// conn=getConnection();//连接数据库
	// ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
	//// rs=ps.executeQuery(sql); // 3.ִ执行SQL语句,緊緊用于查找語句
	// //sql語句中寫了幾個字段，下面就必須要有幾個字段
	// ps.setInt(1, user.getUserId());
	// // 4.处理结果集
	// row=ps.executeUpdate();
	// System.out.println(row);
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }finally{
	// //释放资源【執行完sql要記得釋放資源】
	// try {
	//// rs.close();
	// ps.close();
	// conn.close();
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }
	// }
	// return row;
	// }
	public static void main(String[] args) {
		LogUtil.init();
		JDBCUtils j = new JDBCUtils();
	}
}
