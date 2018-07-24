package jp.co.daifuku.wms.YkkGMAX.Utils;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.wms.YkkGMAX.Exceptions.YKKDBException;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugLevel;
import jp.co.daifuku.wms.YkkGMAX.Utils.Debugprinter.DebugPrinter;

public class ConnectionManager
{

    public static Connection getConnection() throws YKKDBException
    {
	// String user = PropertyLoader.load(DBProperty.USER);
	// String password = PropertyLoader.load(DBProperty.PASSWORD);
	// String connectString =
	// PropertyLoader.load(DBProperty.CONNECT_STRING);

	Connection conn = null;

	try
	{
	    // DriverManager.registerDriver(new
	    // oracle.jdbc.driver.OracleDriver());
	    // conn = DriverManager.getConnection(connectString, user,
	    // password);
	    // conn.setAutoCommit(false);
	    conn = jp.co.daifuku.bluedog.sql.ConnectionManager
		    .getConnection("wms");
	}
	catch (SQLException e)
	{
	    DebugPrinter.print(DebugLevel.ERROR, e.getMessage());
	    YKKDBException ex = new YKKDBException();
	    ex.setResourceKey("7200003");
	    throw ex;
	}

	return conn;
    }
}
