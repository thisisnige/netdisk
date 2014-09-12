package com.iamnige.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.iamnige.logger.Logger;

public class MariaDBConnection{
	public static Connection con;
	public static Connection getConnection() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
		if(null == con){
			try {
				Class.forName(MariaDBConf.DRIVER).newInstance();
				con = DriverManager.getConnection(MariaDBConf.URL, MariaDBConf.USERNAME, MariaDBConf.PASSWORD);
				return con;
			} catch (InstantiationException e) {
				Logger.logError(e.getMessage());
				throw e;
			} catch (IllegalAccessException e) {
				Logger.logError(e.getMessage());
				throw e;
			} catch (ClassNotFoundException e) {
				Logger.logError(e.getMessage());
				throw e;
			} catch (SQLException e) {
				Logger.logError(e.getMessage());
				throw e;
			}
		}else{
			return con;
		}
	}
}
