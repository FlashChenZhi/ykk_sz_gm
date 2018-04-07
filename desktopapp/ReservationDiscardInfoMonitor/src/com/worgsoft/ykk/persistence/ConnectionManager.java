package com.worgsoft.ykk.persistence;

import oracle.jdbc.driver.OracleDriver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.worgsoft.util.common.DBProperty;
import com.worgsoft.util.common.PropertyLoader;

/**
 * Created by IntelliJ IDEA.
 * User: Jack
 * Date: 2008-11-12
 * Time: 18:02:34
 * To change this template use File | Settings | File Templates.
 */
public class ConnectionManager
{
    public static Connection getConnection() throws Exception
    {
        DriverManager.registerDriver(new OracleDriver());
		String user = PropertyLoader.load(DBProperty.USER);
		String password = PropertyLoader.load(DBProperty.PASSWORD);
		String connectString = PropertyLoader.load(DBProperty.CONNECT_STRING);
		Connection conn = DriverManager.getConnection(connectString, user,
				password);
		conn.setAutoCommit(false);
		return conn;
    }
}
