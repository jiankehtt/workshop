package com.rxtx.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.log4j.Logger;

import com.rxtx.model.WsList;
import com.rxtx.utils.LogUtil;
import com.rxtx.utils.PropertyUtil;
import com.rxtx.utils.StringUtil;

public class JDBCUtils {
	private  static String url = "";
	private  static String userName = "";
	private  static String password = "";
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
			if(StringUtil.isEmpty(url)){
				url = PropertyUtil.getProperty("dburl");
				userName = PropertyUtil.getProperty("dbuser");
				password = PropertyUtil.getProperty("dbpasswd");
			}
			logger.debug("url "+url+"  "+userName+"  "+ password);
			conn = DriverManager.getConnection(url, userName, password);
		} catch (Exception e) {
			logger.error("Exception "+e.getMessage());
		}
		return conn;
	}

	/**
	 * 写一个查询数据库语句的方法
	 */
	public void QuerySql() {
		String sql = "select * from ws_tasks";
		try {
			conn = getConnection();// 连接数据库
			ps = conn.prepareStatement(sql);// 2.创建Satement并设置参数
			rs = ps.executeQuery(sql); // 3.ִ执行SQL语句
			// 4.处理结果集
			while (rs.next()) {
				// u=new UserInfo();
				// u.setUserId(rs.getInt("userId"));
				// u.setUserName(rs.getString("userName"));
				// u.setPassword(rs.getString("password"));
				// u.setRemark(rs.getString("remark"));
				// System.out.println("uesrName"+u.getUserName());
			}
			System.out.println(rs.next());
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
	}

	public static void add(WsList wsList) {
		String sql = "insert into ws_list(guid,rfid,comPort) values(?,?,?)";
		try {
			conn = getConnection();// 连接数据库
			ps = conn.prepareStatement(sql);// 2.创建Satement并设置参数
			ps.setString(1, wsList.getGuid());
			ps.setString(2, wsList.getRfid());
			ps.setString(3, wsList.getComport());
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

	// /**
	// * @return修改数据
	// */
	// public int update(UserInfo user){
	// UserInfo u;
	// int row=0;
	//// j.Connection();
	// String sql="update userInfo set userName=?,password=? where userId=?";
	// try {
	// conn=getConnection();//连接数据库
	// ps=conn.prepareStatement(sql);// 2.创建Satement并设置参数
	//// rs=ps.executeQuery(sql); // 3.ִ执行SQL语句,緊緊用于查找語句
	// //sql語句中寫了幾個字段，下面就必須要有幾個字段
	// ps.setString(1, user.getUserName());
	// ps.setString(2, user.getPassword());
	// ps.setInt(3, user.getUserId());
	// // 4.处理结果集
	// row=ps.executeUpdate();
	// System.out.println(row);
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// }finally{
	// //释放资源
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
		j.QuerySql();// 在控制台顯示出查找方法
	}
}
