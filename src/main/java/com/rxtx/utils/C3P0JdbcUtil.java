package com.rxtx.utils;

import java.beans.PropertyVetoException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;//重要  

public class C3P0JdbcUtil {
	
	private static String url = "";
	private static String userName = "";
	private static String password = "";
	
	private static Logger logger = LogUtil.getLogger(C3P0JdbcUtil.class);
	static ComboPooledDataSource cpds = null;
	

	public static Connection getConnection(){// 完成初始化
		synchronized (url) {
			if(cpds==null){
				 cpds = new ComboPooledDataSource();
					if (StringUtil.isEmpty(url)) {
						url = PropertyUtil.getProperty("dburl");
						userName = PropertyUtil.getProperty("dbuser");
						password = PropertyUtil.getProperty("dbpasswd");
					}
					try {
						cpds.setDriverClass("com.mysql.jdbc.Driver");
					} catch (PropertyVetoException e) {
						logger.error("static init c3p0 error "+e.getMessage());
					}
					cpds.setJdbcUrl(url);
					cpds.setUser(userName);
					cpds.setPassword(password);
					cpds.setAcquireIncrement(5);
					cpds.setInitialPoolSize(10);
					cpds.setMaxPoolSize(20);
			}
		}
		try {
			return cpds.getConnection();
		} catch (SQLException e) {
			logger.error("getConnection error "+e.getMessage());
		}
		
		return null;
	}

	public static void release(Connection conn, Statement st, ResultSet rs) {
		if (rs != null) {
			try {
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			rs = null;
		}
		if (st != null) {
			try {
				st.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			st = null;
		}
		if (conn != null) {
			try {
				conn.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			conn = null;
		}
	}

}