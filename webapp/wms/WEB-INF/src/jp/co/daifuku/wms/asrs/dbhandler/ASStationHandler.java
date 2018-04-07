//#CM34105
//$Id: ASStationHandler.java,v 1.2 2006/10/30 07:09:23 suresh Exp $

package jp.co.daifuku.wms.asrs.dbhandler ;

//#CM34106
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Station;

//#CM34107
/**
 * This class retrieves/store <code>Station</code> class from/to the database.
 * As for the subclass for <code>Station</code>, each requires the Handler.
 * <code>getHandler</code> method is prepared for <code>Station</code> and its subclass. 
 * If supporting Handler is unknown, use <code>getHandler</code> method and retrieve.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 07:09:23 $
 * @author  $Author: suresh $
 */
public class ASStationHandler extends StationHandler
{
	//#CM34108
	// Class fields --------------------------------------------------

	//#CM34109
	/**
	 * This value is used when retrieving the station list with no specification for sendability.
	 */
	public static final int SENDABLE_BOTH = 2;

	//#CM34110
	// Class variables -----------------------------------------------
	//#CM34111
	/**
	 * Connection instance to connect with database
	 * Transaction control is not conducted in this class.
	 */
	protected Connection wConn ;

	//#CM34112
	/**
	 * Variable which controls the statement
	 */
	protected Statement wStatement = null ;

	//#CM34113
	// Class method --------------------------------------------------
	//#CM34114
	/**
	 * Return the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 07:09:23 $") ;
	}

	//#CM34115
	// Constructors --------------------------------------------------
	//#CM34116
	/**
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection with database
	 */
	public ASStationHandler(Connection conn)
	{
		super(conn);
		setConnection(conn) ;
	}

	//#CM34117
	// Public methods ------------------------------------------------
	//#CM34118
	/**
	 * Set <code>Connection</code> to connect with database
	 * @param conn :Connection to set
	 */
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}

	//#CM34119
	/**
	 * Retrieve <code>Connection</code> to connect with database.
	 * @return :<code>Connection</code> currently preserved
	 */
	public Connection getConnection()
	{
		return(wConn) ;
	}

	//#CM34120
	/**
	 * Update the last used station no. of the database. This is for standalone aisle type exclusive use 
	 * @param  stno Last used station no.
	 * @throws ReadWriteException throw any abnormal database connection error
	 * @throws NotFoundException  Throw if the target for modification is not found in the database
	 * @throws InvalidDefineException Throw exception if the update contents are not set
	 */
	public void updateLastStaion(String stno) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		//#CM34121
		//-------------------------------------------------
		//#CM34122
		// variable define
		//#CM34123
		//-------------------------------------------------
		Object[]  fmtObj     = new Object[2] ;
		String    table      = "STATION"; 

		String fmtSQL = " UPDATE {0} SET LASTUSEDSTATIONNUMBER = {1}  WHERE " +
						" STATIONNUMBER = ( SELECT PARENTSTATIONNUMBER FROM {0} WHERE STATIONNUMBER = {1} )";

		//#CM34124
		// for database access
		ResultSet rset = null ;

		fmtObj[0] = table;
		fmtObj[1] = DBFormat.format(stno) ;

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
		try
		{
			rset = executeSQL(sqlstring, false) ;	// private exec sql method
		}
		catch (DataExistsException dee)
		{
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
			}
			catch(SQLException e)
			{
				//#CM34125
				//6006002 = Database error occured.{0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "ASStationHandler" ) ;
				throw (new ReadWriteException("6006002")) ;
			}
		}
	}

	//#CM34126
	// Package methods -----------------------------------------------

	//#CM34127
	// Protected methods ---------------------------------------------
	//#CM34128
	/**
	 * Close the wStatement, which is the instance variable.
	 * The cursor generated by executeSQL method must call this method to close.
	 */
	protected void closeStatement()
	{
		try
		{
			if (wStatement != null) wStatement.close() ;
			wStatement = null ;
		}
		catch (SQLException se)
		{
			//#CM34129
			//6006002 = Database error occured.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, se), "ASStationHandler" ) ;
		}
		catch (NullPointerException npe)
		{
			//#CM34130
			// Also perform the outputting og error log.
			Object[] tObj = new Object[1] ;
			if (wStatement != null)
			{
				tObj[0] = wStatement.toString() ;
			}
			else
			{
				tObj[0] = "null" ;
			}
			//#CM34131
			//6016066 = Cannot close the cursor. Statement=[{0}]
			RmiMsgLogClient.write(6016066, LogMessage.F_ERROR, "ASStationHandler", tObj) ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//#CM34132
	/**
	 * Accept the SQL statement and execute.
	 * @param sqlstr :SQL statement to execute
	 * @param query : true if it is query
	 * @return <code>ResultSet</code> of the result.  null for anything else
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if executed result was 0.
	 * @throws DataExistsException :Notifies if it broke the uniqye restriction at Insert.
	 */
	protected ResultSet executeSQL(String sqlstr, boolean query) throws ReadWriteException, NotFoundException, DataExistsException
	{
		ResultSet rset = null ;

		try
		{
			//#CM34133
			// Scroll cursor is required in order to view line 0 by first() in query.
			wStatement = wConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
										, ResultSet.CONCUR_READ_ONLY
									) ;
			if (query)
			{
				//#CM34134
				// SELECT
				rset = wStatement.executeQuery(sqlstr);
			}
			else
			{
				//#CM34135
				// INSERT,UPDATE,DELETE
				int rrows = wStatement.executeUpdate(sqlstr);
				closeStatement() ;
				if (rrows == 0)
				{
					throw (new NotFoundException("6003018")) ;
				}
			}
		}
		catch (SQLException se)
		{
			if (se.getErrorCode() == AsrsParam.DATAEXISTS)
			{
				//#CM34136
				//6026034=The data already exists. Cannot add the data.
				RmiMsgLogClient.write(6026034, LogMessage.F_ERROR, "ASStationHandler", null);
				throw (new DataExistsException("6026034")) ;
			}
			//#CM34137
			//6006002 = Database error occured.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, se), "ASStationHandler" ) ;
			throw (new ReadWriteException("6006002")) ;
		}
		return (rset) ;
	}

	//#CM34138
	/**
	 * Fetch storage station as an array (station type : storage, storage/picking)
	 * Return the station with refer to the condition specified in the argument
	 * 
	 * Usage :<BR>
	 * The data displayed in storage pulldown menu<BR>
	 * eg. getStorageStation(p_TerminalNo);<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getStorageSagyoba(String p_TerminalNo) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.WHSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  STATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (STATIONTYPE = " + Station.STATION_TYPE_IN
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " ) ";
			fmtSQL = fmtSQL + "   AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL ) ";
			fmtSQL = fmtSQL + " ORDER BY STATION.WHSTATIONNUMBER, STATION.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();
			
			while (rset.next())
			{
				Station wEntity = new Station();
				wEntity.setStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER")));
				wEntity.setStationName(DBFormat.replace(rset.getString("STATIONNAME")));
				wEntity.setWHStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER")));
				
				vec.addElement(wEntity);
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34139
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34140
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34141
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34142
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34143
	/**
	 * Fetch the workplace array for picking station ( station type : picking, storage/picking)
	 * Return the station with refer to the condition specified in the argument
	 * 
	 * Usage :<BR>
	 * The data displayed in picking pulldown menu<BR>
	 * eg. getRetrievalSagyoba(p_TerminalNo);<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getRetrievalSagyoba(String p_TerminalNo) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.WHSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  STATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (STATIONTYPE = " + Station.STATION_TYPE_OUT
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " ) ";
			fmtSQL = fmtSQL + "   AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL ) ";
			fmtSQL = fmtSQL + " ORDER BY STATION.WHSTATIONNUMBER, STATION.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();
			
			while (rset.next())
			{
				Station wEntity = new Station();
				wEntity.setStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER")));
				wEntity.setStationName(DBFormat.replace(rset.getString("STATIONNAME")));
				wEntity.setWHStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER")));
				
				vec.addElement(wEntity);
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34144
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34145
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34146
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34147
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34148
	/**
	 * Fetch the workplace array for product increase use station (Storage/Pickingstation, U type storage station)
	 * Return the station with refer to the condition specified in the argument
	 * 
	 * Usage :<BR>
	 * The data displayed in product increase use pulldown<BR>
	 * eg. getAddStorageSagyoba(p_TerminalNo);<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getAddStorageSagyoba(String p_TerminalNo) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.WHSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  STATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (TRIM(CLASSNAME) = " + DBFormat.format("jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator")
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " ) ";
			fmtSQL = fmtSQL + "   AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL ) ";
			fmtSQL = fmtSQL + " ORDER BY STATION.WHSTATIONNUMBER, STATION.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();
			
			while (rset.next())
			{
				Station wEntity = new Station();
				wEntity.setStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER")));
				wEntity.setStationName(DBFormat.replace(rset.getString("STATIONNAME")));
				wEntity.setWHStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER")));
				
				vec.addElement(wEntity);
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34149
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34150
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34151
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34152
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34153
	/**
	 * Fetch array of stock confirmation use station (Storage/Picking station, U type picking station)
	 * Return the station with refer to the condition specified in the argument
	 * 
	 * Usage :<BR>
	 * The data displayed in stock confirmation use pulldown<BR>
	 * eg. getInventoryCheckSagyoba(p_TerminalNo);<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getInventoryCheckSagyoba(String p_TerminalNo) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.WHSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  STATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (TRIM(CLASSNAME) = " + DBFormat.format("jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator")
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " ) ";
			fmtSQL = fmtSQL + "   AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL ) ";
			fmtSQL = fmtSQL + " ORDER BY STATION.WHSTATIONNUMBER, STATION.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();
			
			while (rset.next())
			{
				Station wEntity = new Station();
				wEntity.setStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER")));
				wEntity.setStationName(DBFormat.replace(rset.getString("STATIONNAME")));
				wEntity.setWHStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER")));
				
				vec.addElement(wEntity);
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34154
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34155
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34156
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34157
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34158
	/**
	 * (Work display use)
	 * Fetch the station array from the entire warehouse that is available for send instruction
	 * This method fetches the station array with warehouse and send type as the conditions. 
	 * The station type can be (Storage, Picking, Storage/Picking)
	 * Usage :<BR>
	 * eg. getStation(conn, SENDABLE_BOTH);<BR>
	 * @param conn <CODE>Connection</CODE>
	 * @param r_send Send possible type 1:Send possible  0:Can't send  2:Both
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getSagyobaWorkDisplay(String p_TerminalNo, int r_send) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.WHSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  STATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (OPERATIONDISPLAY = " + Station.OPERATIONDISPONLY
							+ "   OR OPERATIONDISPLAY = " + Station.OPERATIONINSTRUCTION + " ) ";
			if (r_send != SENDABLE_BOTH)
			{
				fmtSQL = fmtSQL + " AND SENDABLE = " + r_send;
			}
			fmtSQL = fmtSQL + " ORDER BY STATION.WHSTATIONNUMBER, STATION.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			//#CM34159
			// Generate the data for the number of retireval instruction generated, or loop until 
			// no data is left any more.
			
			//#CM34160
			// variable to store picking instruction data
			Vector vec = new Vector();
			
			while (rset.next())
			{
				Station wEntity = new Station();
				wEntity.setStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER")));
				wEntity.setStationName(DBFormat.replace(rset.getString("STATIONNAME")));
				wEntity.setWHStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER")));
				
				vec.addElement(wEntity);
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34161
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34162
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34163
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34164
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34165
	/**
	 * Fetch workplace array for work maintenance use station (station type:Storage, Storage/Picking, Picking)
	 * Return the station with refer to the condition specified in the argument
	 * But, group station is not included in the array
	 * 
	 * Usage :<BR>
	 * The data displayed in work maintenance pulldown menu<BR>
	 * eg. getStorageStation(p_TerminalNo);<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getSagyobaMntDisplay(String p_TerminalNo) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.WHSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  STATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (STATIONTYPE = " + Station.STATION_TYPE_IN
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_OUT + ")";
			fmtSQL = fmtSQL + "   AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL ) ";
			fmtSQL = fmtSQL + " ORDER BY STATION.WHSTATIONNUMBER, STATION.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();

			Station aEntity = new Station();
			aEntity.setStationNumber(WmsParam.ALL_STATION);
			aEntity.setStationName(DisplayText.getText("ALLSAGYOBA_TEXT"));
			aEntity.setWHStationNumber(WmsParam.ALL_STATION);
				
			vec.addElement(aEntity);
			
			while (rset.next())
			{
				Station wEntity = new Station();
				wEntity.setStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER")));
				wEntity.setStationName(DBFormat.replace(rset.getString("STATIONNAME")));
				wEntity.setWHStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER")));
				
				vec.addElement(wEntity);
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34166
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34167
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34168
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34169
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34170
	/**
	 * Fetch storage use station array (station type:Storage, Storage/Picking)
	 * Return the station with refer to the condition specified in the argument
	 * But, group station is not included in the array
	 * 
	 * Usage :<BR>
	 * The data displayed in storage pulldown menu<BR>
	 * eg. getStorageStation(p_TerminalNo, SENDABLE_BOTH);<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @param r_send Send possible type 1:Send possible  0:Can't send  2:Both
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getStorageStation(String p_TerminalNo, int r_send) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.PARENTSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  PARENTSTATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (STATIONTYPE = " + Station.STATION_TYPE_IN
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " )) ";
			fmtSQL = fmtSQL + " AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL ";
			fmtSQL = fmtSQL + " AND (STATIONTYPE = " + Station.STATION_TYPE_IN
							+ " OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " ) ";
			if (r_send != SENDABLE_BOTH)
			{
				fmtSQL = fmtSQL + " AND SENDABLE = " + r_send;
			}
			fmtSQL = fmtSQL + " ORDER BY STATION.PARENTSTATIONNUMBER, STATION.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();
			
			while (rset.next())
			{
				//#CM34171
				// If the received station info is workplace, fetch station info in relation to it
				String _carrentStation = DBFormat.replace(rset.getString("STATIONNUMBER"));
				Station[] _workStation = (Station[])this.getWorkStation(_carrentStation);
				for (int _plc=0; _plc<_workStation.length; _plc++)
				{
					Station wEntity = new Station();
					wEntity.setStationNumber(_workStation[_plc].getStationNumber());
					wEntity.setStationName(_workStation[_plc].getStationName());
					wEntity.setParentStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER")));
					
					vec.addElement(wEntity);
				}
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34172
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34173
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34174
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34175
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34176
	/**
	 * Fetch picking use station array (station type: Picking, Storage/Picking)
	 * Return the station with refer to the condition specified in the argument
	 * But, group station is not included in the array
	 * 
	 * Usage :<BR>
	 * The data displayed in picking pulldown menu<BR>
	 * eg. getRetrievalStation(p_TerminalNo, SENDABLE_BOTH, "9000");<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @param r_send Send possible type 1:Send possible  0:Can't send  2:Both
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getRetrievalStation(String p_TerminalNo, int r_send) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.PARENTSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  PARENTSTATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (STATIONTYPE = " + Station.STATION_TYPE_OUT
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " )) ";
			fmtSQL = fmtSQL + " AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL ";
			fmtSQL = fmtSQL + " AND (STATIONTYPE = " + Station.STATION_TYPE_OUT
							+ " OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " ) ";
			if (r_send != SENDABLE_BOTH)
			{
				fmtSQL = fmtSQL + " AND SENDABLE = " + r_send;
			}
			fmtSQL = fmtSQL + " ORDER BY STATION.PARENTSTATIONNUMBER, STATION.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();
			
			while (rset.next())
			{
				String _carrentStation = DBFormat.replace(rset.getString("STATIONNUMBER"));
				Station[] _workStation = (Station[])this.getWorkStation(_carrentStation);
				for (int _plc=0; _plc<_workStation.length; _plc++)
				{
					Station wEntity = new Station();
					wEntity.setStationNumber(_workStation[_plc].getStationNumber());
					wEntity.setStationName(_workStation[_plc].getStationName());
					wEntity.setParentStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER")));
					
					vec.addElement(wEntity);
				}
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34177
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34178
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34179
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34180
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34181
	/**
	 * Fetch product increase use station array (Storage/Pickingstation, U-type storage station)
	 * Return the station with refer to the condition specified in the argument
	 * But, group station is not included in the array
	 * 
	 * Usage :<BR>
	 * The data displayed in product increase use pulldown<BR>
	 * eg. getAddStorageStation (conn, SENDABLE_BOTH, "9000");<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @param r_send Send possible type 1:Send possible  0:Can't send  2:Both
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getAddStorageStation(String p_TerminalNo, int r_send) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.PARENTSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  PARENTSTATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (TRIM(CLASSNAME) = " + DBFormat.format("jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator")
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " )) ";
			fmtSQL = fmtSQL + " AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL ";
			fmtSQL = fmtSQL + " AND (TRIM(CLASSNAME) = " + DBFormat.format("jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator")
							+ " OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " ) ";
			if (r_send != SENDABLE_BOTH)
			{
				fmtSQL = fmtSQL + " AND SENDABLE = " + r_send;
			}
			fmtSQL = fmtSQL + " ORDER BY STATION.PARENTSTATIONNUMBER, STATION.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();
			
			while (rset.next())
			{
				String _carrentStation = DBFormat.replace(rset.getString("STATIONNUMBER"));
				Station[] _workStation = (Station[])this.getWorkStation(_carrentStation);
				for (int _plc=0; _plc<_workStation.length; _plc++)
				{
					Station wEntity = new Station();
					wEntity.setStationNumber(_workStation[_plc].getStationNumber());
					wEntity.setStationName(_workStation[_plc].getStationName());
					wEntity.setParentStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER")));
					
					vec.addElement(wEntity);
				}
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34182
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34183
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34184
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34185
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34186
	/**
	 * Fetch stock confirmation use station array (Storage/Pickingstation, U-type picking station)
	 * Return the station with refer to the condition specified in the argument
	 * But, group station is not included in the array
	 * 
	 * Usage :<BR>
	 * The data displayed in stock confirmation use pulldown<BR>
	 * eg. getInventoryCheckStation(conn, SENDABLE_BOTH, "9000");<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @param r_send Send possible type 1:Send possible  0:Can't send  2:Both
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getInventoryCheckStation(String p_TerminalNo, int r_send) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.PARENTSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  PARENTSTATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (TRIM(CLASSNAME) = " + DBFormat.format("jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator")
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " )) ";
			fmtSQL = fmtSQL + " AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL ";
			fmtSQL = fmtSQL + " AND (TRIM(CLASSNAME) = " + DBFormat.format("jp.co.daifuku.wms.asrs.location.FreeRetrievalStationOperator")
							+ " OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " ) ";
			if (r_send != SENDABLE_BOTH)
			{
				fmtSQL = fmtSQL + " AND SENDABLE = " + r_send;
			}
			fmtSQL = fmtSQL + " ORDER BY STATION.PARENTSTATIONNUMBER, STATION.STATIONNUMBER ";
 
			rset = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();
			
			while (rset.next())
			{
				String _carrentStation = DBFormat.replace(rset.getString("STATIONNUMBER"));
				Station[] _workStation = (Station[])this.getWorkStation(_carrentStation);
				for (int _plc=0; _plc<_workStation.length; _plc++)
				{
					Station wEntity = new Station();
					wEntity.setStationNumber(_workStation[_plc].getStationNumber());
					wEntity.setStationName(_workStation[_plc].getStationName());
					wEntity.setParentStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER")));
					
					vec.addElement(wEntity);
				}
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34187
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34188
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34189
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34190
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34191
	/**
	 * (Work display use)
	 * Fetch the station array from the entire warehouse that is available for send instruction
	 * This method fetches station array with terminal no. and send type as the 
	 * conditions, rather than station type (Storage, Picking, Storage/Picking)
	 * But, group station is not included in the array
	 * Usage :<BR>
	 * eg. getStation(conn, SENDABLE_BOTH);<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @param send Send possible type 1:Send possible  0:Can't send  2:Both
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getStationWorkDisplay(String p_TerminalNo, int r_send) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.PARENTSTATIONNUMBER "
							+ " FROM   STATION "
							+ " WHERE  PARENTSTATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ) ";
			fmtSQL = fmtSQL + " AND (OPERATIONDISPLAY = " + Station.OPERATIONDISPONLY
							+ " OR OPERATIONDISPLAY = " + Station.OPERATIONINSTRUCTION + " ) "
							+ " AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL ";
			if (r_send != SENDABLE_BOTH)
			{
				fmtSQL = fmtSQL + " AND SENDABLE = " + r_send;
			}
			fmtSQL = fmtSQL + " ORDER BY STATION.PARENTSTATIONNUMBER, STATION.STATIONNUMBER ";

			rset = stmt.executeQuery(fmtSQL);

			//#CM34192
			// Generate the data for the number of retireval instruction generated, or loop until 
			// no data is left any more.
			
			//#CM34193
			// variable to store picking instruction data
			Vector vec = new Vector();
			
			while (rset.next())
			{
				Station wEntity = new Station();
				wEntity.setStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER")));
				wEntity.setStationName(DBFormat.replace(rset.getString("STATIONNAME")));
				wEntity.setParentStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER")));
				
				vec.addElement(wEntity);
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34194
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34195
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34196
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34197
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34198
	/**
	 * Fetch the work maintenance use station array (station type: Storage, Storage/Picking, Picking)
	 * Return the station with refer to the condition specified in the argument
	 * But, group station is not included in the array
	 * 
	 * Usage :<BR>
	 * The data displayed in work maintenance pulldown menu<BR>
	 * eg. getStorageStation(p_TerminalNo, SENDABLE_BOTH);<BR>
	 * @param p_TerminalNo <CODE>terminal no</CODE>
	 * @param r_send Send possible type 1:Send possible  0:Can't send  2:Both
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getStationMntDisplay(String p_TerminalNo, int r_send) throws ReadWriteException
	{
		Statement stmt             = null;
		ResultSet rset             = null;
		ResultSet rsetAll          = null;
		Station[] rData  = null;

		try
		{
			stmt = getConnection().createStatement();

			String fmtSQL	= " SELECT STATION.STATIONNUMBER, "
							+ "        STATION.STATIONNAME, "
							+ "        STATION.PARENTSTATIONNUMBER, "
							+ "        STATION.SENDABLE "
							+ " FROM   STATION "
							+ " WHERE  PARENTSTATIONNUMBER "
							+ " IN (SELECT DISTINCT(PARENTSTATIONNUMBER) "
							+ "   FROM STATION "
							+ "   WHERE  STATIONNUMBER "
							+ "   IN (SELECT STATIONNUMBER "
							+ "     FROM   TERMINALAREA "
							+ "     WHERE  TERMINALNUMBER = " + DBFormat.format(p_TerminalNo)
							+ "     ) ";
			fmtSQL = fmtSQL + "   AND (STATIONTYPE = " + Station.STATION_TYPE_IN
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_OUT
							+ "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + " ) ";
			fmtSQL = fmtSQL + "   AND TRIM(PARENTSTATIONNUMBER) IS NOT NULL )";
			if (r_send != SENDABLE_BOTH)
			{
				fmtSQL = fmtSQL + " AND SENDABLE = " + r_send;
			}
			fmtSQL = fmtSQL + " ORDER BY STATION.PARENTSTATIONNUMBER, STATION.STATIONNUMBER ";

			rsetAll = stmt.executeQuery(fmtSQL);

			Vector vec = new Vector();

			Station astEntity = new Station();
			astEntity.setStationNumber(WmsParam.ALL_STATION);
			astEntity.setStationName(DisplayText.getText("ALLSTATION_TEXT"));
			astEntity.setParentStationNumber(WmsParam.ALL_STATION);
			
			vec.addElement(astEntity);

			while (rsetAll.next())
			{
				//#CM34199
				// display other than workplace
				if (rsetAll.getString("SENDABLE").equals(Integer.toString(Station.SENDABLE_TRUE)))
				{
					Station aEntity = new Station();
					aEntity.setStationNumber(DBFormat.replace(rsetAll.getString("STATIONNUMBER")));
					aEntity.setStationName(DBFormat.replace(rsetAll.getString("STATIONNAME")));
					aEntity.setParentStationNumber(WmsParam.ALL_STATION);
					
					vec.addElement(aEntity);
				}
			}
			
			rset = stmt.executeQuery(fmtSQL);
			while (rset.next())
			{
				String _carrentStation = DBFormat.replace(rset.getString("STATIONNUMBER"));
				Station[] _workStation = (Station[])this.getWorkStation(_carrentStation);
				for (int _plc=0; _plc<_workStation.length; _plc++)
				{
					Station wEntity = new Station();
					wEntity.setStationNumber(_workStation[_plc].getStationNumber());
					wEntity.setStationName(_workStation[_plc].getStationName());
					wEntity.setParentStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER")));
					
					vec.addElement(wEntity);
				}
			}
			rData = new Station[vec.size()];
			vec.copyInto(rData);
		}
		catch(SQLException e)
		{
			//#CM34200
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
			//#CM34201
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM34202
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "AsStationHandler" ) ;
				//#CM34203
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Station")) ;
			}
		}
		
		return rData;
	}

	//#CM34204
	/**
	 * Fetch the station array with refer to the passed workplace (station)
	 * Return the station with refer to the condition specified in the argument
	 * 
	 * Usage :<BR>
	 * @param p_station <CODE>station no.</CODE>
	 * @return station with refer to the condition specified in the argument
	 * @throws ReadWriteException throw any database access exception
	 */
	public Station[] getWorkStation(String p_station) throws ReadWriteException
	{
		StationHandler _StHandler = new StationHandler(wConn);
		StationSearchKey _StSearchKey = new StationSearchKey();

		_StSearchKey.KeyClear();
		_StSearchKey.setStationNumber(p_station);
		
		Station[] _rStation = (Station[])_StHandler.find(_StSearchKey);
		
		Vector _stVec = new Vector();
		
		for (int _plc=0; _plc<_rStation.length; _plc++)
		{
			if (_rStation[_plc].getWorkPlaceType() != Station.NOT_WORKPLACE)
			{
				_StSearchKey.KeyClear();
				_StSearchKey.setParentStationNumber(_rStation[_plc].getStationNumber());
				
				Station[] _roopStation = (Station[])_StHandler.find(_StSearchKey);
				//#CM34205
				// add station info to a vector object
				for (int _slc=0; _slc<_roopStation.length; _slc++)
				{
					Station[] _poolStation = (Station[])getWorkStation(_roopStation[_slc].getStationNumber());
					for (int _jlc=0; _jlc<_poolStation.length; _jlc++)
					{
						_stVec.add(_poolStation[_jlc]);
					}
				}
			}
			else
			{
				//#CM34206
				// add station info to a vector object
				//#CM34207
				// _rStaion[_plc]
				_stVec.add(_rStation[_plc]);
			}
		}
		Station[] _workStation = new Station[_stVec.size()];
		_stVec.copyInto(_workStation);
		
		return _workStation;		
	}

	//#CM34208
	// Private methods -----------------------------------------------
}
//#CM34209
//end of class

