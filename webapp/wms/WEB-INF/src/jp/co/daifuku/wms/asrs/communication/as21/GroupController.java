// $Id: GroupController.java,v 1.2 2006/10/26 01:05:13 suresh Exp $
package jp.co.daifuku.wms.asrs.communication.as21 ;

//#CM31232
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jp.co.daifuku.common.CommonParam;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM31233
/**
 * This class is used to control the informatin of group controller.
 * It preserves the attributes and constant numbers that are necessary for the control of group controller.
 * Values of each instance variables, that are defined in class, are retrieved from group controller table. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:05:13 $
 * @author  $Author: suresh $
 */
public class GroupController extends Object
{
	//#CM31234
	// Class fields --------------------------------------------------
	//#CM31235
	/**
	 * Field for the status of group controller (unknown)
	 */
	public static final int STATUS_UNKNOWN = 0 ;

	//#CM31236
	/**
	 * Field for the status of group controller (on-line)
	 */
	public static final int STATUS_ONLINE = 1 ;

	//#CM31237
	/**
	 * Field fore the status of group controller (off-line/ connection complete)
	 */
	public static final int STATUS_OFFLINE = 2 ;

	//#CM31238
	/**
	 * Field for the status of group controller (reserved for off-line)
	 */
	public static final int STATUS_END_RESERVATION = 3 ;

	//#CM31239
	/**
	 * Field ifor the default group controller
	 */
	public static final int DEFAULT_AGC_NUMBER = 1 ;

	//#CM31240
	// Class variables -----------------------------------------------
	//#CM31241
	/**
	 * Variables which preserves the connection for database use.
	 * It is provided from external. No transasction is performed inside the class.
	 */
	private Connection wConn ;

	//#CM31242
	/**
	 * Variables which preserves the gourp controller numbers
	 */
	private int wGCNumber = -1 ;

	//#CM31243
	/**
	 * Variables which preserves the IP address of the group controller
	 */
	private InetAddress wGCIP ;

	//#CM31244
	/**
	 * Variables which preserves the host of the group controller
	 */
	private String wHostName;

	//#CM31245
	/**
	 *  Variables which preserves the port number of the group controller
	 */
	private int wGCPort ;

	//#CM31246
	/**
	 *  Variables which preserves the status of the group controller.
	 */
	private int wGCStatus ;

	//#CM31247
	/**
	 * Variables which preserves the group controller for the table search use.
	 */
	private int wSelNumber ;

	//#CM31248
	/**
	 * Is used in log writing when Exception ocured(StringWriter).
	 */
	public StringWriter wSW = new StringWriter();

	//#CM31249
	/**
	 * Is used when exception occured and writing its log(PrintWriter).
	 */
	public PrintWriter  wPW = new PrintWriter(wSW);

	//#CM31250
	/**
	 * Delimiter
	 * Delimiter of parameters in MessageDe when exception occured.
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM31251
	// Class method --------------------------------------------------
	//#CM31252
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:05:13 $") ;
	}

	//#CM31253
	/**
	 * Create the instance of <code>GroupController</code> based on the group controleer number, then returns it.
	 * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
	 * is not done internally, it requires the external commitment.
	 * @param gno : number of <code>GroupController</code> to create
	 * @return    : the object <code>GroupController</code> created on argument base.
	 */
	public static GroupController getInstance(Connection conn, int gno)
	{
		GroupController gc = new GroupController(conn, gno) ;
		return(gc) ;
	}

	//#CM31254
	/**
	 * Create instance of all group controller <code>GroupController</code> then returns it.
	 * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
	 * is not done internally, it requires the external commitment.
	 * @return : array of the object<code>GroupController</code> created on argument base.
	 * @throws ReadWriteException : Notifies if exceptin occurs during the database connection.
	 */
	public static GroupController[] getInstances(Connection conn) throws ReadWriteException
	{
		//#CM31255
		// Get numbers of all group controller.
		int[] gcids = getGCNo(conn) ;

		//#CM31256
		// Create the array of group controller
		GroupController[] gcs = new GroupController[gcids.length] ;

		//#CM31257
		// Create all group controller instance
		for (int i=0; i < gcids.length; i++)
		{
			gcs[i] = new GroupController(conn, gcids[i]) ;
		}
		
		return(gcs) ;
	}

	//#CM31258
	/**
	 * Create the instance of <code>GroupController</code> based on the group controller number . If the status is 
	 * in on-line, it returns 'true'.
	 * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
	 * is not done internally, it requires the external commitment.
	 * @param gno Number of <code>GroupController</code> to create
	 * @return Status : on-line     : true
	 *                : anything other than on-line : false
     * @throws ReadWriteException : Notifies if exception occur during the database connection.
     * @throws ReadWriteException : Notifies if the IP address of the specified host cannot be found in group controller data
	 */
	public static boolean isOnLine(Connection conn, int gno) throws ReadWriteException
	{
		GroupController gc = new GroupController(conn, gno) ;
		if ( gc.getStatus() == STATUS_ONLINE )
		{
			return true ;
		}
		
		return false ;
	}

	//#CM31259
	// Constructors --------------------------------------------------
	//#CM31260
	/**
	 * Create instance by specifying the group controller number.
	 * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
	 * is not done internally, it requires the external commitment.
	 * @param gno : number of <code>GroupController</code> to create
	 */
	public GroupController(Connection conn, int gno)
	{
		wConn = conn ;
		wSelNumber = gno ;
	}

	//#CM31261
	// Public methods ------------------------------------------------
	//#CM31262
	/**
	 * Get the number of group controller
	 * @return    group controller number
	 */
	public int getNumber()
	{
		return(wSelNumber) ;
	}

	//#CM31263
	/**
	 * Get the host name
	 * @return host name
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws ReadWriteException : Notifies if the IP address of the specified host cannot be found in group controller data
	 */
	public String getHostName() throws ReadWriteException
	{
		//#CM31264
		// Get the data of group controller from DB
		loadGCinfo(false) ;
		
		return wHostName;
	}

	//#CM31265
	/**
	 * Set up the IP address of group controller
	 * @param ip : IP address of group controller
	 * @see java.net.InetAddress
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws ReadWriteException  : Notifies if the IP address of the specified host cannot be found in group controller data
	 */
	public void setIP(InetAddress ip) throws ReadWriteException
	{
		//#CM31266
		// Lock DB
		loadGCinfo(true) ;		
		
		//#CM31267
		// Update DB
		wGCIP = ip ;
		updateGCinfo() ;
	}

	//#CM31268
	/**
	 * Get the IP address of the group controller
	 * @return    IP address of the group controller
	 * @see java.net.InetAddress
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws ReadWriteException  : Notifies if the IP address of the specified host cannot be found in group controller data
	 */
	public InetAddress getIP() throws ReadWriteException
	{
		//#CM31269
		// Get the data of group controller from DB
		loadGCinfo(false) ;
		
		return(wGCIP) ;
	}

	//#CM31270
	/**
	 * Set the port number of the group controller
	 * @param port :the port number of the group controller
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws ReadWriteException  : Notifies if the IP address of the specified host cannot be found in group controller data
	 */
	public void setPort(int port) throws ReadWriteException
	{
		//#CM31271
		// Lock DB
		loadGCinfo(true) ;		
		
		//#CM31272
		// Update DB
		wGCPort = port ;
		updateGCinfo() ;
	}

	//#CM31273
	/**
	 * Get the port number of the group controller
	 * @return    the port number of the group controller
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws ReadWriteException  : Notifies if the IP address of the specified host cannot be found in group controller data
	 */
	public int getPort() throws ReadWriteException
	{
		//#CM31274
		// Get the data of group controller from DB
		loadGCinfo(false) ;
		
		return(wGCPort) ;
	}

	//#CM31275
	//----------------------------------------------------------------
	//#CM31276
	// Accessor method in dynamic state
	//#CM31277
	//----------------------------------------------------------------
	//#CM31278
	/**
	 * Set the state of the group controller<BR>
	 * List of the state was defined as a field of this class.
	 * @param sts State of the group controller
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws ReadWriteException  : Notifies if the IP address of the specified host cannot be found in group controller data
	 */
	public void setStatus(int sts) throws ReadWriteException
	{
		//#CM31279
		// Lock DB
		loadGCinfo(true) ;
		
		//#CM31280
		// Update DB
		wGCStatus = sts ;
		updateGCinfo() ;
	}

	//#CM31281
	/**
	 * Get the state of the group controller<BR>
	 * List of the state was defined as a field of this class.
	 * @return    State of the group controller
     * @throws ReadWriteException : Notifies if exception occured during the database connection.
     * @throws ReadWriteException  : Notifies if the IP address of the specified host cannot be found in group controller data
	 */
	public int getStatus() throws ReadWriteException
	{
		//#CM31282
		// Get the data of group controller from DB
		loadGCinfo(false) ;

		return(wGCStatus) ;
	}

	//#CM31283
	/**
	 * Retruns the string representation of the group controller
	 * @return    string representation of the group controller
	 */
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		try
		{
			buf.append("\nGroup Controller Number:" + Integer.toString(getNumber())) ;
			buf.append("\nGroup Controller IP:" + (getIP().toString())) ;
			buf.append("\nGroup Controller Port:" + Integer.toString(getPort())) ;
			buf.append("\nGroup Controller Status:" + Integer.toString(getStatus())) ;
		}
		catch (Exception e)
		{
			e.printStackTrace() ;
		}
		
		return (buf.toString()) ;
	}

	//#CM31284
	// Package methods -----------------------------------------------

	//#CM31285
	// Protected methods ---------------------------------------------

	//#CM31286
	// Private methods -----------------------------------------------
	//#CM31287
	/**
	 * Get the data of group controller out of database.
	 * Key is the Group controller number.
	 * @param   locked : returns 'true' if locking the searched data, or 'false' if NOT locking.
	 * @throws ReadWriteException : Notifies if exception occured during the database connection.
	 * @throws ReadWriteException : Notifies if the IP address of the specified host cannot be found in group controller data
	 */
	private void loadGCinfo(boolean lockd) throws ReadWriteException
	{
		//#CM31288
		//-------------------------------------------------
		//#CM31289
		// variable define
		//#CM31290
		//-------------------------------------------------
		String sqltmpl = "SELECT" +
						" controllernumber" +
						", status" +
						", ipaddress" +
						", port " +
						" FROM groupcontroller " +
						" WHERE controllernumber = {0}" ;

		String sqllocktmpl = "SELECT" +
						" controllernumber" +
						", status" +
						", ipaddress" +
						", port " +
						" FROM groupcontroller " +
						" WHERE controllernumber = {0} FOR UPDATE" ;

		Statement stmt = null ;
		ResultSet rset = null ;

		Object[] fmtObj = new Object[1] ;
		String sqlstring ;

		//#CM31291
		//-------------------------------------------------
		//#CM31292
		// process start
		//#CM31293
		//-------------------------------------------------
		try 
		{
			fmtObj[0] = Integer.toString(wSelNumber) ;

			//#CM31294
			// create actual SQL string
			if (lockd)
			{
				//#CM31295
				// SQL to lock the selected lines
				sqlstring = SimpleFormat.format(sqllocktmpl, fmtObj) ;
			}
			else
			{
				//#CM31296
				// SQL not to lock the selected lines
				sqlstring = SimpleFormat.format(sqltmpl, fmtObj) ;
			}
			stmt = wConn.createStatement();
			rset = stmt.executeQuery(sqlstring);

			//#CM31297
			// fetch to first row
			if (rset.next() == false)
			{
				//#CM31298
				// 6026033=There is no data corresponding to the search. Table:{0}
				Object[] tObj = new Object[1] ;
				tObj[0] = "GroupController";
				RmiMsgLogClient.write(6026033, LogMessage.F_ERROR, "GroupController", tObj);
				throw (new ReadWriteException("6026033" + wDelim + tObj[0])) ;
			}

			//#CM31299
			// getting column data
			wGCNumber = rset.getInt("controllernumber") ;
			wGCStatus = rset.getInt("status") ;
			wGCPort = rset.getInt("port") ;
			try
			{
				wHostName = DBFormat.replace(rset.getString("ipaddress"));
				wGCIP = InetAddress.getByName(wHostName) ;
			}
			catch (UnknownHostException ue)
			{
				String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
				//#CM31300
				//Record the error in the log file.
				ue.printStackTrace(wPW) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = DBFormat.replace(rset.getString("ipaddress"));
				tObj[1] = stcomment + wSW.toString() ;
				//#CM31301
				// 6026036=Cannot find the IP address of the designated host. Host:{0}
				RmiMsgLogClient.write(6026036, LogMessage.F_ERROR, "GroupController", tObj);
				throw (new ReadWriteException("6026036" + wDelim + tObj[0])) ;
			}
		}
		catch (SQLException se)
		{
			String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
			//#CM31302
			//Record the error in the log file.
			se.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(se.getErrorCode()) ;
			tObj[1] = stcomment + wSW.toString() ;
			//#CM31303
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "GroupController", tObj);
			throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException se)
			{
				String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
				//#CM31304
				//Record the error in the log file.
				se.printStackTrace(wPW) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = new Integer(se.getErrorCode()) ;
				tObj[1] = stcomment + wSW.toString() ;
				//#CM31305
				// 6007030=Database error occured. error code={0}
				RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "GroupController", tObj);
				throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
			}
		}
	}

	//#CM31306
	/**
	 * Reflect the information of group controller to database.
	 * Key is the Group controller number.
	 * @throws ReadWriteException : Notifies if exception occured during the database connection.
	 */
	private void updateGCinfo() throws ReadWriteException
	{
		//#CM31307
		//-------------------------------------------------
		//#CM31308
		// variable define
		//#CM31309
		//-------------------------------------------------
		String sqltmpl = "UPDATE groupcontroller set " +
						"status = {1}" +
						", ipaddress = {2}" +
						", port = {3}" +
						" WHERE controllernumber = {0}" ;

		Statement stmt = null ;
		ResultSet rset = null ;

		Object[] fmtObj = new Object[4] ;
		String sqlstring ;

		//#CM31310
		//-------------------------------------------------
		//#CM31311
		// process start
		//#CM31312
		//-------------------------------------------------
		try
		{
			fmtObj[0] = Integer.toString(wGCNumber) ;
			fmtObj[1] = Integer.toString(wGCStatus) ;
			fmtObj[2] = "'"+ wGCIP.getHostName() +"'" ;
			fmtObj[3] = Integer.toString(wGCPort) ;

			//#CM31313
			// create actual SQL string
			sqlstring = SimpleFormat.format(sqltmpl, fmtObj) ;

			stmt = wConn.createStatement();
			stmt.executeUpdate(sqlstring);
		}
		catch (SQLException se)
		{
			String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
			//#CM31314
			//Record the error in the log file.
			se.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(se.getErrorCode()) ;
			tObj[1] = stcomment + wSW.toString() ;
			//#CM31315
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "GroupController", tObj);
			throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException se)
			{
				String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
				//#CM31316
				//Record the error in the log file.
				se.printStackTrace(wPW) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = new Integer(se.getErrorCode()) ;
				tObj[1] = stcomment + wSW.toString() ;
				//#CM31317
				// 6007030=Database error occured. error code={0}
				RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "GroupController", tObj);
				throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
			}
		}
	}

	//#CM31318
	/**
	 * Get number of all group controller out of database.
	 * @param conn  :As transaction control for <code>Connection</code>, for the use of database connection, 
	 * is not done internally, it requires the external commitment.
	 * @return Array of group controller number
	 * @throws ReadWriteException : Notifies if exception occured during the database connection.
	 */
	private static int[] getGCNo(Connection conn) throws ReadWriteException
	{
		//#CM31319
		// To be used when Exception occured and write the Log.
		StringWriter wsw = new StringWriter();
		//#CM31320
		// To be used when Exception occured and write the Log.
		PrintWriter  wpw = new PrintWriter(wsw);
		//#CM31321
		// Delimiter
		String wDelim = MessageResource.DELIM ;

		//#CM31322
		//-------------------------------------------------
		//#CM31323
		// variable define
		//#CM31324
		//-------------------------------------------------
		String sqlstring = "SELECT" +
						" CONTROLLERNUMBER" +
						" FROM groupcontroller " +
						" ORDER BY CONTROLLERNUMBER";

		Statement stmt = null ;
		ResultSet rset = null ;

		Vector wgcidVect = new Vector(10) ;
		int[] regGCNo ;

		//#CM31325
		//-------------------------------------------------
		//#CM31326
		// process start
		//#CM31327
		//-------------------------------------------------
		try
		{
			stmt = conn.createStatement();
			rset = stmt.executeQuery(sqlstring);

			//#CM31328
			// fetch and set to vector
			while (rset.next())
			{
				//#CM31329
				// getting column data
				wgcidVect.add(new Integer(rset.getInt("CONTROLLERNUMBER"))) ;
			}

			//#CM31330
			// set to array of int (for return value)
			regGCNo = new int[wgcidVect.size()] ;
			for (int i=0; i < regGCNo.length; i++)
			{
				Integer wi = (Integer)wgcidVect.remove(0) ;
				regGCNo[i] = wi.intValue() ;
			}
		}
		catch (SQLException se)
		{
			String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
			//#CM31331
			//Record the error in the log file.
			se.printStackTrace(wpw) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = new Integer(se.getErrorCode()) ;
			tObj[1] = stcomment + wsw.toString() ;
			//#CM31332
			// 6007030=Database error occured. error code={0}
			RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "GroupController", tObj);
			throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException se)
			{
				String stcomment = CommonParam.getParam("STACKTRACE_COMMENT");
				//#CM31333
				//Record the error in the log file.
				se.printStackTrace(wpw) ;
				Object[] tObj = new Object[2] ;
				tObj[0] = new Integer(se.getErrorCode()) ;
				tObj[1] = stcomment + wsw.toString() ;
				//#CM31334
				// 6007030=Database error occured. error code={0}
				RmiMsgLogClient.write(6007030, LogMessage.F_ERROR, "GroupController", tObj);
				throw (new ReadWriteException("6007030" + wDelim + tObj[0])) ;
			}
		}
		return (regGCNo) ;
	}
}
//#CM31335
//end of class
