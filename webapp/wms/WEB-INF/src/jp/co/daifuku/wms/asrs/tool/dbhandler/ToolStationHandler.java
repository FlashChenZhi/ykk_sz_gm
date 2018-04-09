//#CM48517
//$Id: ToolStationHandler.java,v 1.2 2006/10/30 02:17:13 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48518
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
import java.sql.Timestamp;
import java.util.Vector;

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.location.Aisle;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM48519
/**<en>
 * This class is used to retrieve/store the <code>Station</code> class from/to database.
 * As for the subclass of <code>Station</code>, Handler will be requried respectively.
 * Please use <code>StationFactory</code> to retrieve <code>Station</code> and the subclasses.
 * As <code>getHandler</code> method has been prepared for <code>Station</code> and the subclasses,
 * in case the Handler to support is unknown, retrieve one by using <code>getHandler</code> method.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:13 $
 * @author  $Author: suresh $
 </en>*/
public class ToolStationHandler implements ToolDatabaseHandler
{

	//#CM48520
	// Class fields --------------------------------------------------
	
	public static final String STATION_HANDLE = "jp.co.daifuku.wms.base.dbhandler.StationHandler";
	
	
	
	//#CM48521
	// Class variables -----------------------------------------------
	//#CM48522
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_STATION";

	//#CM48523
	/**<en> name of the STATIONTYPE table</en>*/

	protected final String wStationTypeTableName = wTableName + "TYPE";

	//#CM48524
	/**<en>
	 * Connection instance to connect with database
	 * Transaction control is not conducted in this class.
	 </en>*/
	protected Connection wConn ;

	//#CM48525
	/**<en>
	 * Variables which control the statements.
	 </en>*/
	protected Statement wStatement = null ;

	//#CM48526
	// Class method --------------------------------------------------
	//#CM48527
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:13 $") ;
	}

	//#CM48528
	// Constructors --------------------------------------------------
	//#CM48529
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolStationHandler(Connection conn)
	{
		setConnection(conn) ;
	}
	//#CM48530
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection connect with database
	 * @param tablename :name of the table
	 </en>*/
	public ToolStationHandler(Connection conn, String tablename)
	{
		setConnection(conn) ;
		wTableName = tablename;
	}

	//#CM48531
	// Public methods ------------------------------------------------

	//#CM48532
	/**<en>
	 * Check whether/not the station no. which has been specified by paramter in the 
	 * STATIONTYPE table exists.
	 * @param stationNo :station no.
	 * @return:true if the no. exists.
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public boolean isStationType(String stationNo) throws ReadWriteException
	{
		Statement stmt      = null;
		ResultSet rset      = null;
		int count = 0;

	 	try
	 	{
			stmt = wConn.createStatement();
			String sqlstring = "SELECT COUNT(1) COUNT FROM " + wStationTypeTableName
								+ " WHERE STATIONNUMBER = '"+ stationNo + "'" ;

			rset = stmt.executeQuery(sqlstring) ;
			while (rset.next())
			{
				count = rset.getInt("COUNT");
			}
			if(count == 0)
			{
				return false;
			}
			return true;
		}
		catch(SQLException e)
		{
				//#CM48533
				//<en>6126001 = Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48534
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM48535
				//<en>6126001 = Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48536
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;

			}
		}
	}
	//#CM48537
	/**<en>
	 * Check whether/not the station no. which has been specified by paramter in the 
	 * STATIONTYPE table exists.
	 * In this method, it checks whether/not the specified station no. exists in any handler classes
	 * other than the specicified handler class.
	 * @param stationNo :station no.
	 * @param handlerClass :the handelr class which is used to generate the sation instance.
	 * @return :whether/not the identical data exist (true: identical data exists, false: does not exist)
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public boolean isStationType(String stationNo, String handlerClass) throws ReadWriteException
	{
		Statement stmt      = null;
		ResultSet rset      = null;
		int count = 0;

	 	try
	 	{
			stmt = wConn.createStatement();
			String sqlstring = "SELECT COUNT(1) COUNT FROM " + wStationTypeTableName
								+ " WHERE STATIONNUMBER = '"+ stationNo + "'" +" AND "
								+ " HANDLERCLASS != '" + handlerClass + "'";

			rset = stmt.executeQuery(sqlstring) ;
			while (rset.next())
			{
				count = rset.getInt("COUNT");
			}
			if(count == 0)
			{
				return false;
			}
			return true;
		}
		catch(SQLException e)
		{
				//#CM48538
				//<en>6126001 = Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48539
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM48540
				//<en>6126001 = Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48541
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;

			}
		}
	}

	//#CM48542
	/**<en>
	 * Delete all data from the table.
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public void truncate() throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		try
		{
			stmt = wConn.createStatement();
			String sqlstring = "TRUNCATE TABLE "+ wTableName ;
			//#CM48543
			// execute the sql
			rset = stmt.executeQuery(sqlstring) ;
		}
		catch(SQLException e)
		{
				//#CM48544
				//<en>6126001 = Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48545
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM48546
				//<en>6126001 = Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48547
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}

	}
	
	//#CM48548
	/**<en>
	 * Set the <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect
	 </en>*/
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}

	//#CM48549
	/**<en>
	 * Retrieve the <code>Connection</code> to connect with database.
	 * @return :<code>Connection</code> currently preserved
	 </en>*/
	public Connection getConnection()
	{
		return(wConn) ;
	}

	//#CM48550
	/**<en>
	 * Search the station.  <code>StationSearchKey</code> should be used for a search key.
	 * @param key :Key for the search
	 * @return :the array of the created object
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
	{
		//#CM48551
		//-------------------------------------------------
		//#CM48552
		// variable define
		//#CM48553
		//-------------------------------------------------
		Station[] fndStation = null ;
		Object[]  fmtObj = new Object[2] ;

		//#CM48554
		// for database access
		ResultSet rset = null ;

		String fmtSQL = "SELECT * FROM "+ wTableName +" {0} {1}" ;

		if (key.ReferenceCondition() != null)
		{
			if (key.SortCondition() != null)
			{
				fmtObj[0] = "WHERE " + key.ReferenceCondition();
				fmtObj[1] = "ORDER BY " + key.SortCondition();
			}
			else
			{
				fmtObj[0] = "WHERE " + key.ReferenceCondition();
			}
		}
		else if (key.SortCondition() != null)
		{
			fmtObj[0] = "ORDER BY " + key.SortCondition();
		}

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

		try
		{
			rset = executeSQL(sqlstring, true) ;	// private exec sql method
		}
		catch (NotFoundException nfe)
		{
			//#CM48555
			//<en> This should not occur;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException dee)
		{
			//#CM48556
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}

		//#CM48557
		// make Station instances from resultset
		//#CM48558
		// !!! makeStation() is private method of this.
		fndStation = makeStation(rset) ;

		return fndStation;
	}

	//#CM48559
	/**<en>
	 * Search the station. (this method has been created upon request of display and forms.)
	 * @param type used for determining if it is storage or retreival
	 * @return :the array of the created object
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public ToolEntity[] multifind(int type) throws ReadWriteException
	{
		Station[] stnArray = null ;
		ResultSet 	rset		= null ;
		String		sqlstring;

		try
		{
			//#CM48560
			//stmt = wConn.createStatement();

			//#CM48561
			//<en> For storage, or storage/retrieval available</en>
			if ( type == jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_INOUT_IN)
			{
				sqlstring = "SELECT * FROM "+wTableName+" WHERE STATIONTYPE="+
											 jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_IN + 
											 " OR STATIONTYPE=" + jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_INOUT+
											" ORDER BY STATIONNUMBER" ;
				rset = executeSQL(sqlstring, true) ;	// private exec sql method
			}
			else
			//#CM48562
			//<en> For retrieval, or storage/retrieval available</en>
			if ( type == jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_INOUT_OUT)
			{
				sqlstring = "SELECT * FROM "+wTableName+" WHERE STATIONTYPE="+
							 jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_OUT + 
							" OR STATIONTYPE=" + jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_INOUT+
							" ORDER BY STATIONNUMBER";
				rset = executeSQL(sqlstring, true) ;	// private exec sql method
			}
			else
			//#CM48563
			//<en> For storage, for retrieval,or for storage/retrieval available</en>
			if ( type == jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_INOUT_IN_OUT)
			{
				sqlstring =  	"SELECT * FROM " + wTableName +
								" WHERE STATIONTYPE=" + jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_IN + 
								" OR    STATIONTYPE=" + jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_OUT +
								" OR    STATIONTYPE=" + jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_INOUT +
								" ORDER BY STATIONNUMBER";
				rset = executeSQL(sqlstring, true) ;	// private exec sql method
			}
			else
			//#CM48564
			//<en> storage</en>
			if ( type == jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_IN)
			{
				sqlstring = 	"SELECT * FROM "+ wTableName +" WHERE STATIONTYPE="+
								 jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_IN + 
								" ORDER BY STATIONNUMBER";
				rset = executeSQL(sqlstring, true) ;	// private exec sql method
			}
			else
			//#CM48565
			//<en> retrieval</en>
			if ( type == jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_OUT)
			{
				sqlstring = 	"SELECT * FROM "+wTableName+" WHERE STATIONTYPE="+
								jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_OUT + 
								" ORDER BY STATIONNUMBER";
				rset = executeSQL(sqlstring, true) ;	// private exec sql method
			}
			else
			//#CM48566
			//<en> storage/retrieval available</en>
			if ( type == jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_INOUT)
			{
				sqlstring = "SELECT * FROM "+wTableName+" WHERE STATIONTYPE="+
							jp.co.daifuku.wms.asrs.tool.location.Station.STATION_TYPE_INOUT + 
							" ORDER BY STATIONNUMBER";
				rset = executeSQL(sqlstring, true) ;	// private exec sql method
			}
			//#CM48567
			//<en> all case (including RM and STV)</en>

			else
			{
				rset = executeSQL("SELECT * FROM "+wTableName+" ORDER BY STATIONNUMBER", true) ;	// private exec sql method
			}

			//#CM48568
			// make Station instances from resultset
			stnArray = makeStation(rset) ;
		}
		catch(Exception e)
		{
			//#CM48569
			//<en>6126001 =Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48570
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126001" + wDelim + "Warehouse")) ;
		}
		finally
		{
		}
		return stnArray;
	}
	//#CM48571
	/**<en>
	 * Search the station.
	 * @param 	whstationnumber 
	 * @return :the array of the created object
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public ToolEntity[] multifind(String whstationnumber) throws ReadWriteException
	{
		Station[] stnArray = null ;
		ResultSet 	rset		= null ;
		Statement   stmt        = null ;
		
		try
		{
			stmt = wConn.createStatement();
			rset = stmt.executeQuery( 	"SELECT * FROM "+wTableName+" WHERE  WHSTATIONNUMBER="+
											 DBFormat.format(whstationnumber)+ 
											" ORDER BY STATIONNUMBER");
		
			//#CM48572
			// make Station instances from resultset
			stnArray = makeStation(rset) ;
		}
		catch(SQLException e)
		{
			//#CM48573
			//<en>6126001 = Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolStationHandler" ) ;
			//#CM48574
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "Station"));
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM48575
				//<en>6126001 =Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48576
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126001" + wDelim + "Warehouse")) ;
			}
		}
		return stnArray;
	}

	//#CM48577
	/**<en>
	 * Search the station.
	 * @param 	stno :the array of StationNumber
	 * @return modetypeArray :the array of Station ModeType(String type)
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public String[] multifind(String[] stno) throws ReadWriteException
	{
		String[] modetypeArray = null ;
		ResultSet 	rset		= null ;
		Statement   stmt        = null ;
		
		String stArray = "";
		String stnum = "";
		Vector 		vec          = new Vector();
		for(int i=0; i<stno.length; i++)
		{
			String st = stno[i];
			if(i==0)
			{
				stArray = st;
			}
			else
			{
				stArray = stArray + "," + st;
			}
		}
		try
		{
			stmt = wConn.createStatement();
			String sqlstring = "SELECT DISTINCT(MODETYPE) FROM "+ wTableName
			              + "WHERE STATIONNUMBER IN ( " + stArray  +" )" ;
			rset = stmt.executeQuery(sqlstring);
			
			if (rset != null)
			{
				while (rset.next())
					stnum  = rset.getString("MODETYPE") ;
					vec.addElement(stnum);
					modetypeArray = new String[vec.size()];
					vec.copyInto(modetypeArray);
			}
		
		}
		catch(SQLException e)
		{
			//#CM48578
			//<en>6126001 = Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolStationHandler" ) ;
			//#CM48579
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "Station"));
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM48580
				//<en>6126001 =Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48581
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126001" + wDelim + "Warehouse")) ;
			}
		}
		return modetypeArray;
	}


	//#CM48582
	/**<en>
	 * Return insertion delivery Station including all Workshop in the area. 
	 * Use it when you make terminal area information on the eWareNavi AS/RS setting tool. 
	 * @return Station list
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 </en>*/
	public Station[] getStationInArea() throws ReadWriteException
	{
		ResultSet 	rset		= null ;
		Statement   stmt        = null ;
		Station[] stnArray = null ;

		try
		{
			stmt = wConn.createStatement();
			String sqlstring = "SELECT TEMP_STATION.* " +
				               "FROM TEMP_STATION, TEMP_WAREHOUSE " +		               
							   "WHERE TEMP_STATION.WHSTATIONNUMBER = TEMP_WAREHOUSE.STATIONNUMBER " + 
//#CM48583
// It comments until the problem of the DMAREA development is solved. 
//#CM48584
//							   "AND TEMP_WAREHOUSE.STATIONNUMBER = DMAREA.AREA_NO " +
							   "AND TEMP_STATION.STATIONTYPE IN ('1', '2', '3') " ;

			rset = stmt.executeQuery(sqlstring);
			
			if (rset != null)
			{
				//#CM48585
				// make Station instances from resultset
				stnArray = makeStation(rset) ;
			}
		
		}
		catch(SQLException e)
		{
			//#CM48586
			//<en>6126001 = Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolStationHandler" ) ;
			//#CM48587
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "Station"));
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM48588
				//<en>6126001 =Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48589
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126001" + wDelim + "Warehouse")) ;
			}
		}
		return stnArray;
	}
	
	//#CM48590
	/**<en>
	 * Return all the terminal definitions in the system. 
	 * Retrieve it for convenience' sake by this handler though the TEAMINAL table is retrieved. 
	 * Use it when you make terminal area information on the eWareNavi AS/RS setting tool. 
	 * @return Station list
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 </en>*/
	public String[] getTerminalNumbers() throws ReadWriteException
	{
		ResultSet 	rset		= null ;
		Statement   stmt        = null ;
		String[] 	starray 	= null ;

		try
		{
			stmt = wConn.createStatement();
			String sqlstring = "SELECT TERMINALNUMBER FROM TERMINAL ";
			rset = stmt.executeQuery(sqlstring);
			
			Vector vec = new Vector(20) ;
			while (rset.next())
			{
				vec.addElement(rset.getString("TERMINALNUMBER"));
			}	
			starray = new String[vec.size()] ;
			vec.copyInto(starray);			
		
		}
		catch(SQLException e)
		{
			//#CM48591
			//<en>6126001 = Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolStationHandler" ) ;
			//#CM48592
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "Station"));
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM48593
				//<en>6126001 =Database error occured.  {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48594
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126001" + wDelim + "Warehouse")) ;
			}
		}
		return starray;
	}
	


	//#CM48595
	/**<en>
	 * Search stations which can accept the storage carry instrucions, instantiate the result data
	 * and return.
	 * Except the stations of automatic mode switch.
	 * @return :the list of sendable station searched based on the station type
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public ToolEntity[] getSendableStorageStations() throws ReadWriteException
	{
		Station[] stnArray = null ;
		ResultSet rset = null ;

		try
		{
			String sqlstring = "SELECT * FROM " + wTableName
							   + "WHERE SENDABLE  = " + Station.SENDABLE
							   + "  AND MODETYPE != " + Station.AUTOMATIC_MODE_CHANGE
							   + "  AND (STATIONTYPE = " + Station.STATION_TYPE_IN
							   + "   OR  STATIONTYPE = " + Station.STATION_TYPE_INOUT + ")"
							   + " ORDER BY STATIONNUMBER" ;
			rset = executeSQL(sqlstring, true) ;
			stnArray = makeStation(rset) ;
		}
		catch (Exception e)
		{
			//#CM48596
			//<en>6126001 =Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48597
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126001" + wDelim + "Warehouse")) ;
		}
		finally
		{
		}
		return stnArray;
	}

	//#CM48598
	/**<en>
	 * Search stations which can accept the retrieval carry instrucions, instantiate the result data
	 * and return.
	 * Except the stations of automatic mode switch.
	 * @return :the list of sendable station searched based on the station type
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public ToolEntity[] getSendableRetrievalStations() throws ReadWriteException
	{
		Station[] stnArray = null ;
		ResultSet rset = null ;

		try
		{
			String sqlstring = "SELECT * FROM " + wTableName
							   + "WHERE SENDABLE  = " + Station.SENDABLE
							   + "  AND MODETYPE != " + Station.AUTOMATIC_MODE_CHANGE
							   + "  AND (STATIONTYPE = " + Station.STATION_TYPE_OUT
							   + "   OR STATIONTYPE = " + Station.STATION_TYPE_INOUT + ")"
							   + " ORDER BY STATIONNUMBER" ;
			rset = executeSQL(sqlstring, true) ;
			stnArray = makeStation(rset) ;
		}
		catch (Exception e)
		{
			//#CM48599
			//<en>6126001 =Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48600
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126001" + wDelim + "Warehouse")) ;
		}
		finally
		{
		}
		return stnArray;
	}

	//#CM48601
	/**<en>
	 * Search information from database based on parameters and return number of results 
	 * (number of station data).
	 * @param key :Key for search
	 * @return :number of search results
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public int count(ToolSearchKey key) throws ReadWriteException
	{
		//#CM48602
		//-------------------------------------------------
		//#CM48603
		// variable define
		//#CM48604
		//-------------------------------------------------
		Object[]  fmtObj = new Object[1] ;
		int count = 0;

		//#CM48605
		// for database access
		ResultSet rset = null ;

		String fmtSQL = "SELECT count(1) count FROM "+ wTableName +" {0} " ;

		if (key.ReferenceCondition() != null)
		{
			fmtObj[0] = "WHERE " + key.ReferenceCondition();
		}

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
		try
		{
			rset = executeSQL(sqlstring, true) ;	// private exec sql method
			if (rset != null)
			{
				while (rset.next())
					count = rset.getInt("COUNT") ;
			}
		}
		catch (SQLException e)
		{
			//#CM48606
			//<en>6126001 =Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48607
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126001" + wDelim + "Warehouse")) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM48608
			//<en> This should not occur;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException dee)
		{
			//#CM48609
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		finally
		{
			closeStatement();
		}

		return count;
	}

	//#CM48610
	/**<en>
	 * Create teh new information in database.
	 * @param tgt :entity instance which has the information to create
	 * @throws ReadWriteException  :Notifies if error occured in connection with database. 
	 * @throws DataExistsException :Notifies if the same information has been registered in database.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
		//#CM48611
		//-------------------------------------------------
		//#CM48612
		// variable define
		//#CM48613
		//-------------------------------------------------
		String fmtSQL = "INSERT INTO "+wTableName+" (" +
						"  STATIONNUMBER" +				// 0
						", MAXPALETTEQUANTITY" +		// 1
						", MAXINSTRUCTION" +			// 2
						", SENDABLE" +					// 3
						", STATUS" +					// 4
						", CONTROLLERNUMBER" +			// 5
						", STATIONTYPE" +				// 6
						", SETTINGTYPE" +				// 7
						", WORKPLACETYPE" +				// 8
						", OPERATIONDISPLAY" +			// 9
						", STATIONNAME" +				// 10
						", SUSPEND" +					// 11
						", ARRIVALCHECK" +				// 12
						", LOADSIZECHECK" +				// 13
						", REMOVE" + 					// 14
						", INVENTORYCHECKFLAG" +		// 15
						", RESTORINGOPERATION" + 		// 16
						", RESTORINGINSTRUCTION" + 		// 17
						", POPERATIONNEED" +			// 18
						", WHSTATIONNUMBER" +			// 19
						", PARENTSTATIONNUMBER" +		// 20
						", AISLESTATIONNUMBER" +			// 21
						", NEXTSTATIONNUMBER" +			// 22
						", LASTUSEDSTATIONNUMBER" +		// 23
						", REJECTSTATIONNUMBER" +		// 24
						", MODETYPE" +					// 25
						", CURRENTMODE" +				// 26
						", MODEREQUEST" +				// 27
						", MODEREQUESTTIME" +			// 28
						", CARRYKEY" +					// 29
						", HEIGHT" +					// 30
						", BCDATA" +					// 31
						", CLASSNAME" +					// 32
						") VALUES (" +
						"{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10},{11},{12},{13},{14},{15},{16},{17},{18},{19},{20},{21},{22},{23},{24},{25},{26},{27},{28},{29},{30},{31},{32}" +
						")" ;

		String fmtSQL_StationType = "INSERT INTO "+wStationTypeTableName+" (" +
						"  STATIONNUMBER" +				// 0
						",  HANDLERCLASS" +				// 1
						") VALUES (" +
						"{0}, '" + STATION_HANDLE + "'" + 
						")" ;

		Station tgtSt ;
		String sqlstring ;
		//#CM48614
		//-------------------------------------------------
		//#CM48615
		// process
		//#CM48616
		//-------------------------------------------------
		if (tgt instanceof Station)
		{
			tgtSt = (Station)tgt ;
		}
		else
		{
			//#CM48617
			//<en>Fatal error has occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Station Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}

		try
		{
			//#CM48618
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtSt) ;
			//#CM48619
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM48620
			// execute the sql
			executeSQL(sqlstring, false) ;

			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			executeSQL(sqlstring, false) ;

		}
		catch (NotFoundException nfe)
		{
			//#CM48621
			//<en> This should not occur;</en>

			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
	}

	//#CM48622
	/**<en>
	 * Modify the information in database.
	 * @param tgt :entity instance which has the information to modify
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM48623
		//<en> Update DB.</en>
		//#CM48624
		//<en>-------------------------------------------------</en>
		//#CM48625
		//<en> variable define</en>
		//#CM48626
		//<en>-------------------------------------------------</en>
		String fmtSQL = "UPDATE "+wTableName+" set" +
						"  MAXPALETTEQUANTITY = {1} " +			// 1
						", MAXINSTRUCTION = {2} " +				// 2
						", SENDABLE = {3} " +					// 3
						", STATUS = {4} " +						// 4
						", CONTROLLERNUMBER = {5} " +			// 5
						", STATIONTYPE = {6} " +				// 6
						", SETTINGTYPE = {7} " +				// 7
						", WORKPLACETYPE = {8} " +				// 8
						", OPERATIONDISPLAY = {9} " +			// 9
						", STATIONNAME = {10} " +				// 10
						", SUSPEND = {11} " +					// 11
						", ARRIVALCHECK = {12} " +				// 12
						", LOADSIZECHECK = {13} " +				// 13
						", REMOVE = {14} " +					// 14
						", INVENTORYCHECKFLAG = {15} " +		// 15
						", RESTORINGOPERATION = {16} " + 		// 16
						", RESTORINGINSTRUCTION = {17} " + 		// 17
						", POPERATIONNEED = {18} " +			// 18
						", WHSTATIONNUMBER = {19} " +			// 19
						", PARENTSTATIONNUMBER = {20} " +		// 20
						", AISLESTATIONNUMBER = {21} " +			// 21
						", NEXTSTATIONNUMBER = {22} " +			// 22
						", LASTUSEDSTATIONNUMBER = {23} " +		// 23
						", REJECTSTATIONNUMBER = {24} " +		// 24
						", MODETYPE = {25} " +					// 25
						", CURRENTMODE = {26} " +				// 26
						", MODEREQUEST = {27} " +				// 27
						", MODEREQUESTTIME = {28} " +			// 28
						", CARRYKEY = {29} " +					// 29
						", HEIGHT = {30} " +					// 30
						", BCDATA = {31} " +					// 31
						", CLASSNAME = {32} " +					// 32
						" WHERE STATIONNUMBER = {0}" ;			// 0

		Station tgtSt ;
		String sqlstring ;
		//#CM48627
		//-------------------------------------------------
		//#CM48628
		// process
		//#CM48629
		//-------------------------------------------------
		if (tgt instanceof Station)
		{
			tgtSt = (Station)tgt ;
		}
		else
		{
			//#CM48630
			//<en>Fatal error has occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Station Instance.", "ToolStationHandler");
			throw (new ReadWriteException("6126499")) ;
		}

		try
		{
			//#CM48631
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtSt) ;
			//#CM48632
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM48633
			// execute the sql
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException dee)
		{
			//#CM48634
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM48635
	/**<en>
	 * Modify the shelf information in database. The contents and conditions of the modificaiton will be 
	 * obtained by ToolAlterKey.
	 * @param  key :ToolAlterKey instance preserved by contents and conditions of the modification
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies when data to updata cannot be found in database.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		//#CM48636
		//-------------------------------------------------
		//#CM48637
		// variable define
		//#CM48638
		//-------------------------------------------------
		Object[]  fmtObj     = new Object[3] ;
		String    table      = wTableName; 

		String fmtSQL = " UPDATE {0} SET {1} {2}";
		//#CM48639
		// for database access
		ResultSet rset = null ;

		fmtObj[0] = table;

		if (key.ModifyContents(table) == null)
		{
			//#CM48640
			//<en> Exception if the update values have not been set.</en>
			Object[] tobj = {table};
			//#CM48641
			//<en>6126005 = Cannot update the database as the update value has not been set. TABLE={0}</en>
			RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, "ToolStationHandler", tobj);
			throw (new InvalidDefineException("6126005"));
		}
		fmtObj[1] = key.ModifyContents(table);

		if (key.ReferenceCondition(table) == null)
		{
			//#CM48642
			//<en> Exception if the update conditions have not been set.</en>
			Object[] tobj = {table};
			//#CM48643
			//<en>6126006 = Cannot update the database as the update condiitons have not been set. TABLE={0}</en>
			RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, "ToolStationHandler", tobj);
			throw (new InvalidDefineException("6126006"));
		}
		fmtObj[2] = "WHERE " + key.ReferenceCondition(table);

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
		try
		{
			rset = executeSQL(sqlstring, false) ;	// private exec sql method
		}
		catch (DataExistsException dee)
		{
			//#CM48644
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM48645
	/**<en>
	 * Delete from database the information of entity instance which has been passed through parameter.
	 * @param tgt :entity instance which has the informtion to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM48646
		//<en> Delete from DB</en>
		//#CM48647
		//<en>-------------------------------------------------</en>
		//#CM48648
		//<en> variable define</en>
		//#CM48649
		//<en>-------------------------------------------------</en>
		String fmtSQL = "DELETE FROM " + wTableName +
						" WHERE STATIONNUMBER = {0}" ;			// 0

		String fmtSQL_StationType = "DELETE FROM "+wStationTypeTableName +
						" WHERE STATIONNUMBER = {0}" ;			// 0


		//#CM48650
		// for database access
		ResultSet rset = null ;

		Station tgtSt ;
		String sqlstring ;
		//#CM48651
		//-------------------------------------------------
		//#CM48652
		// process
		//#CM48653
		//-------------------------------------------------
		if (tgt instanceof Station)
		{
			tgtSt = (Station)tgt ;
		}
		else
		{
			//#CM48654
			//<en>Fatal error has occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Station Instance.", "ToolStationHandler");
			throw (new ReadWriteException("6126499")) ;
		}

		try
		{
			//#CM48655
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtSt) ;
			//#CM48656
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM48657
			// execute the sql
			executeSQL(sqlstring, false) ;

			//#CM48658
			//for stationtype table.
			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			//#CM48659
			// execute the sql
			executeSQL(sqlstring, false) ;

		}
		catch (DataExistsException dee)
		{
			//#CM48660
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM48661
	/**<en>
	 * Delete from database the information which match the key passed through parameter.
	 * @param key :key for the to-delete information
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException
	{
		//#CM48662
		//<en> Delete from DB</en>
		ToolEntity[] tgts = find(key) ;
		for (int i=0; i < tgts.length; i++)
		{
			drop(tgts[i]) ;
		}
	}

	//#CM48663
	/**
	 * Update the Mode of packing check of STATION which belongs to Aisle of double deep the Mode of packing check and to exist. <BR>
	 * @param tgt Entity instance with changed information
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	public void modifyLoadSizeCheck() throws ReadWriteException
	{
		//#CM48664
		//<en> Update DB.</en>
		//#CM48665
		//<en>-------------------------------------------------</en>
		//#CM48666
		//<en> variable define</en>
		//#CM48667
		//<en>-------------------------------------------------</en>
		String sqlstring = "UPDATE " + wTableName 
						 + " SET LOADSIZECHECK = " + DBFormat.format(Integer.toString(Station.LOADSIZECHECK)) 
						 + " WHERE EXISTS (SELECT 1 FROM TEMP_AISLE "
						 + "                        WHERE AISLESTATIONNUMBER = TEMP_AISLE.STATIONNUMBER" 
						 + "                          AND TEMP_AISLE.DOUBLEDEEPKIND = " + DBFormat.format(Integer.toString(Aisle.DOUBLE_DEEP))
						 + "               )";
		try
		{
			//#CM48668
			// execute the sql
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException dee)
		{
			//#CM48669
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		catch (NotFoundException ne)
		{
			//#CM48670
			// Do not do the error. 
		}
	}

	//#CM48671
	// Package methods -----------------------------------------------

	//#CM48672
	// Protected methods ---------------------------------------------
	//#CM48673
	/**<en>
	 * Close the wStatement which is the instance variable.
	 * It is necessary that the cursor generated by executeSQL method should be closed.
	 </en>*/
	protected void closeStatement()
	{
		try
		{
			if (wStatement != null)
			{
				 wStatement.close() ;
			} 
			wStatement = null ;
		}
		catch (SQLException se)
		{
			//#CM48674
			//<en>6126001 = Database error occured.  {0}</en>

			RmiMsgLogClient.write( new TraceHandler(6126001, se), "ToolStationHandler" ) ;
		}
		catch (NullPointerException npe)
		{
			//#CM48675
			//<en>Also carry out the output of error log.</en>
			Object[] tObj = new Object[1] ;
			if (wStatement != null)
			{
				tObj[0] = wStatement.toString() ;
			}
			else
			{
				tObj[0] = "null" ;
			}
			//#CM48676
			//<en>6126007 = Could not close the cursor. Statement=[{0}]</en>
			RmiMsgLogClient.write(6126007, LogMessage.F_ERROR, "ToolStationHandler", tObj) ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//#CM48677
	/**<en>
	 * Receive and execute the SQL string.
	 * @param sqlstr :SQL string to execute
	 * @param query  :true if it is a query
	 * @return :<code>ResultSet</code> of the results, or null for all other cases
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if result of the exection was 0.
	 * @throws DataExistsException :If the unique restriction is broken at Insert.
	 </en>*/
	protected ResultSet executeSQL(String sqlstr, boolean query) throws ReadWriteException, NotFoundException, DataExistsException
	{
		ResultSet rset = null ;
		try
		{
			//#CM48678
			//<en> A scroll cursor will be requried in order to view line 0 by first() of the query.</en>
			wStatement = wConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
										, ResultSet.CONCUR_READ_ONLY
									) ;
			if (query)
			{
				//#CM48679
				// SELECT
				rset = wStatement.executeQuery(sqlstr);
			//#CM48680
			//	closeStatement() ;
			}
			else
			{
				//#CM48681
				// INSERT,UPDATE,DELETE
				int rrows = wStatement.executeUpdate(sqlstr);
				if (rrows == 0)
				{
					//#CM48682
					//<en>6123001 = No target data was found.</en>
					throw (new NotFoundException("6123001")) ;
				}
				closeStatement() ;
			}
		}
		catch (SQLException se)
		{
			if (se.getErrorCode() == ToolParam.DATAEXISTS)
			{
				//#CM48683
				//<en>6126008 = Cannot registrate; the identical data already exists.</en>
				RmiMsgLogClient.write(6126008, LogMessage.F_ERROR, "ToolStationHandler", null);
				throw (new DataExistsException("6126008")) ;
			}
			//#CM48684
			//<en>6126001 = Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, se), "ToolStationHandler" ) ;

			//#CM48685
			//<en> Database error occured.  </en>
			String msg = "6126001" ; 
			throw (new ReadWriteException(msg)) ;
		}
		return (rset) ;
	}

	//#CM48686
	/**<en>
	 * Retireve information from the <code>Station</code> instance and set in Object array.
	 * This is prepared for INSERT and UPDATE.
	 * When storing in database and if appropiate, it will set string null in some cases.
	 * Enclose the string type items in ' single quotations.
	 * @param tgtSt :<code>Station</code> instance to retrieve data
	 * @return :Object array
	 * <p>
	 * The order of the arrays whould be as follows.<br>
	 * <pre>
	 * stationnumber			// 0
	 * maxpalettequantity		// 1
	 * maxinstruction			// 2
	 * sendable					// 3
	 * status					// 4
	 * controllernumber 		// 5
	 * stationtype				// 6
	 * settingtype				// 7
	 * workplacetype			// 8
	 * operationdisplay			// 9
	 * stationname				// 10
	 * suspend					// 11
	 * arrivalcheck				// 12
	 * loadsizecheck			// 13
	 * remove					// 14
	 * inventorycheckflag		// 15
	 * restoringoperation		// 16
	 * restoringinstruction		// 17
	 * poperationneed			// 18
	 * whstationnumber			// 19
	 * parentstationnumber		// 20
	 * ailestationnumber		// 21
	 * nextstationnumber		// 22
	 * lastusedstationnumber	// 23
	 * rejectstationnumber		// 24
	 * modetype					// 25
	 * currentmode				// 26
	 * moderequest				// 27
	 * moderequesttime			// 28
	 * carrykey					// 29
	 * height					// 30
	 * bcdata					// 31
	 * classname				// 32
	 * </pre></p>
	 </en>*/
	/**<en>
	 * Retireve information from the <code>Station</code> instance and set in Object array.
	 * This is prepared for INSERT and UPDATE.
	 * When storing in database and if appropiate, it will set string null in some cases.
	 * Enclose the string type items in ' single quotations.
	 * @param tgtSt :<code>Station</code> instance to retrieve data
	 * @return :Object array
	 * <p>
	 * The order of the arrays whould be as follows.<br>
	 * <pre>
	 * stationnumber			// 0
	 * maxpalettequantity		// 1
	 * maxinstruction			// 2
	 * sendable					// 3
	 * status					// 4
	 * controllernumber 		// 5
	 * stationtype				// 6
	 * settingtype				// 7
	 * workplacetype			// 8
	 * operationdisplay			// 9
	 * stationname				// 10
	 * suspend					// 11
	 * arrivalcheck				// 12
	 * loadsizecheck			// 13
	 * remove					// 14
	 * inventorycheckflag		// 15
	 * restoringoperation		// 16
	 * restoringinstruction		// 17
	 * poperationneed			// 18
	 * whstationnumber			// 19
	 * parentstationnumber		// 20
	 * ailestationnumber		// 21
	 * nextstationnumber		// 22
	 * lastusedstationnumber	// 23
	 * rejectstationnumber		// 24
	 * modetype					// 25
	 * currentmode				// 26
	 * moderequest				// 27
	 * moderequesttime			// 28
	 * carrykey					// 29
	 * height					// 30
	 * bcdata					// 31
	 * classname				// 32
	 * </pre></p>
	 </en>*/
	protected Object[] setToArray(Station tgtSt)
	{
		Object[] fmtObj = new Object[33] ;
		//#CM48687
		// set parameters
		//#CM48688
		// station number
		fmtObj[0] = DBFormat.format(tgtSt.getNumber()) ;
		//#CM48689
		// max palette quantity
		fmtObj[1] = Integer.toString(tgtSt.getMaxPaletteQuantity()) ;
		//#CM48690
		//<en> max number of carry instruction sendable</en>
		fmtObj[2] = Integer.toString(tgtSt.getMaxInstruction()) ;
		//#CM48691
		// sendable
		if (tgtSt.isSendable())
		{
			fmtObj[3] = Integer.toString(Station.SENDABLE) ;
		}
		else
		{
			fmtObj[3] = Integer.toString(Station.NOT_SENDABLE) ;
		}
		//#CM48692
		// status
		fmtObj[4] = Integer.toString(tgtSt.getStatus()) ;
		//#CM48693
		// group controller id
		if (tgtSt.getGroupController() == null)
		{
			fmtObj[5] = "0" ;
		}
		else
		{
			fmtObj[5] = Integer.toString(tgtSt.getGroupController().getNumber()) ;
		}
		//#CM48694
		// station type
		fmtObj[6] = Integer.toString(tgtSt.getType()) ;
		//#CM48695
		// setting type (for in-station)
		fmtObj[7] = Integer.toString(tgtSt.getInSettingType()) ;
		//#CM48696
		// workplace type
		fmtObj[8] = Integer.toString(tgtSt.getWorkPlaceType()) ;
		//#CM48697
		// operationdisplay
		fmtObj[9] = Integer.toString(tgtSt.getOperationDisplay()) ;
		//#CM48698
		// station name
		fmtObj[10] = DBFormat.format(tgtSt.getName()) ;
		//#CM48699
		// suspend
		if (tgtSt.isSuspend())
		{
			fmtObj[11] = Integer.toString(Station.SUSPEND) ;
		}
		else
		{
			fmtObj[11] = Integer.toString(Station.NOT_SUSPEND) ;
		}
		//#CM48700
		// arrivalcheck
		if (tgtSt.isArrivalCheck())
		{
			fmtObj[12] = Integer.toString(Station.ARRIVALCHECK) ;
		}
		else
		{
			fmtObj[12] = Integer.toString(Station.NOT_ARRIVALCHECK) ;
		}
		//#CM48701
		// loadsizecheck
		if (tgtSt.isLoadSizeCheck())
		{
			fmtObj[13] = Integer.toString(Station.LOADSIZECHECK) ;
		}
		else
		{
			fmtObj[13] = Integer.toString(Station.NOT_LOADSIZECHECK) ;
		}
		//#CM48702
		// removecheck
		if (tgtSt.isRemove())
		{
			fmtObj[14] = Integer.toString(Station.PAYOUT_OK) ;
		}
		else
		{
			fmtObj[14] = Integer.toString(Station.PAYOUT_NG) ;
		}
		//#CM48703
		// inventorycheckflag
		fmtObj[15] = Integer.toString(tgtSt.getInventoryCheckFlag()) ;
		//#CM48704
		// restoringoperation
		if (tgtSt.isReStoringOperation())
		{
			fmtObj[16] = Integer.toString(Station.CREATE_RESTORING) ;
		}
		else
		{
			fmtObj[16] = Integer.toString(Station.NOT_CREATE_RESTORING) ;
		}
		//#CM48705
		// restoringinstruction
		fmtObj[17] = Integer.toString(tgtSt.getReStoringInstruction()) ;
		//#CM48706
		// poperationneed
		if (tgtSt.isPoperationNeed())
		{
			fmtObj[18] = Integer.toString(Station.POPERATIONNEED) ;
		}
		else
		{
			fmtObj[18] = Integer.toString(Station.NOT_POPERATIONNEED) ;
		}
		//#CM48707
		// werehouse station number
		fmtObj[19] = DBFormat.format(tgtSt.getWarehouseStationNumber()) ;
		//#CM48708
		// parent station number
		fmtObj[20] = DBFormat.format(tgtSt.getParentStationNumber()) ;
		//#CM48709
		// aile station number
		fmtObj[21] = DBFormat.format(tgtSt.getAisleStationNumber()) ;
		//#CM48710
		// next station number
		fmtObj[22] = DBFormat.format(tgtSt.getNextStationNumber()) ;
		//#CM48711
		// lastused station number
		fmtObj[23] = DBFormat.format(tgtSt.getLastUsedStationNumber()) ;
		//#CM48712
		// reject station number
		fmtObj[24] = DBFormat.format(tgtSt.getRejectStationNumber()) ;
		//#CM48713
		// mode type
		fmtObj[25] = Integer.toString(tgtSt.getModeType()) ;
		//#CM48714
		// current mode
		fmtObj[26] = Integer.toString(tgtSt.getCurrentMode()) ;
		//#CM48715
		// mode request
		fmtObj[27] = Integer.toString(tgtSt.getChangeModeRequest()) ;
		//#CM48716
		// mode request time
		fmtObj[28] = DBFormat.format(tgtSt.getChangeModeRequestTime()) ;
		//#CM48717
		// height
		fmtObj[29] = DBFormat.format(tgtSt.getCarryKey()) ;
		//#CM48718
		// height
		fmtObj[30] = Integer.toString(tgtSt.getHeight()) ;
		//#CM48719
		// bar code data
		fmtObj[31] = DBFormat.format(tgtSt.getBCData()) ;
		//#CM48720
		// class name
		fmtObj[32] = DBFormat.format(tgtSt.getClassName()) ;

		return (fmtObj) ;
	}

	//#CM48721
	/**<en>
	 * Retrieve each item from <code>ResultSet</code> and generate the <code>Station</code> instance.
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	protected Station[] makeStation(ResultSet rset) throws ReadWriteException
	{
		Vector tmpStVect = new Vector(20) ;	// temporary store for Station instances
		Station tmpst = null;
		//#CM48722
		// data get from resultset and make new Station instance
		try
		{
			while (rset.next())
			{
				String clsname = DBFormat.replace(rset.getString("CLASSNAME"));

				tmpst = new Station(DBFormat.replace(rset.getString("STATIONNUMBER"))) ;
				//#CM48723
				//<en>No meaning of instantiation with the class name for TEMP_STATION table. See comment.</en>

//#CM48724
/*
				if (StringUtil.isBlank(clsname))
				{
					//<en> station number</en>
					tmpst = new Station(DBFormat.replace(rset.getString("STATIONNUMBER"))) ;
				}
				else
				{
					//<en> load class</en>
					Class lclass = Class.forName(clsname) ;
					Class[] typeparams = new Class[1] ;
					typeparams[0] = Class.forName("java.lang.String") ;
					Constructor cconst = lclass.getConstructor(typeparams) ;
					//<en> set actual parameter</en>
					Object[] tparams = new Object[1] ;
					tparams[0] = DBFormat.replace(rset.getString("STATIONNUMBER"));
					//<en> getting Object</en>
					Object tgt = cconst.newInstance(tparams) ;
					if (tgt instanceof Station)
					{
						tmpst = (Station)tgt; 
					}
					else
					{
						Object[] tObj = new Object[1] ;
						tObj[0] = "Station" ;
						RmiMsgLogClient.write(6126011, LogMessage.F_ERROR, "ToolStationHandler", tObj);
						throw (new InvalidDefineException("6126011" + wDelim + tObj[0])) ;
					}
				}
*/
/*
				if (StringUtil.isBlank(clsname))
				{
					//<en> station number</en>
					tmpst = new Station(DBFormat.replace(rset.getString("STATIONNUMBER"))) ;
				}
				else
				{
					//<en> load class</en>
					Class lclass = Class.forName(clsname) ;
					Class[] typeparams = new Class[1] ;
					typeparams[0] = Class.forName("java.lang.String") ;
					Constructor cconst = lclass.getConstructor(typeparams) ;
					//<en> set actual parameter</en>
					Object[] tparams = new Object[1] ;
					tparams[0] = DBFormat.replace(rset.getString("STATIONNUMBER"));
					//<en> getting Object</en>
					Object tgt = cconst.newInstance(tparams) ;
					if (tgt instanceof Station)
					{
						tmpst = (Station)tgt; 
					}
					else
					{
						Object[] tObj = new Object[1] ;
						tObj[0] = "Station" ;
						RmiMsgLogClient.write(6126011, LogMessage.F_ERROR, "ToolStationHandler", tObj);
						throw (new InvalidDefineException("6126011" + wDelim + tObj[0])) ;
					}
				}
*/

				//#CM48725
				// max palette quantity
				tmpst.setMaxPaletteQuantity(rset.getInt("MAXPALETTEQUANTITY")) ;
				//#CM48726
				//<en> max number of carry instrucitons sendable</en>
				tmpst.setMaxInstruction(rset.getInt("MAXINSTRUCTION")) ;
				//#CM48727
				// sendable
				boolean snd = (rset.getInt("SENDABLE") == Station.SENDABLE) ;
				tmpst.setSendable(snd) ;
				//#CM48728
				// station status
				tmpst.setStatus(rset.getInt("STATUS")) ;
				//#CM48729
				// group controller
				tmpst.setGroupController(GroupController.getInstance(wConn, rset.getInt("CONTROLLERNUMBER"))) ;
				//#CM48730
				// station type
				tmpst.setType(rset.getInt("STATIONTYPE")) ;
				//#CM48731
				// setting type
				tmpst.setInSettingType(rset.getInt("SETTINGTYPE")) ;
				//#CM48732
				// workplace type
				tmpst.setWorkPlaceType(rset.getInt("WORKPLACETYPE")) ;
				//#CM48733
				// operationdisplay
				tmpst.setOperationDisplay(rset.getInt("OPERATIONDISPLAY")) ;
				//#CM48734
				// station name
				tmpst.setName(DBFormat.replace(rset.getString("STATIONNAME"))) ;
				//#CM48735
				// suspend
				boolean sus = (rset.getInt("SUSPEND") == Station.SUSPEND) ;
				tmpst.setSuspend(sus) ;
				//#CM48736
				// arrivalCheck
				boolean arr = (rset.getInt("ARRIVALCHECK") == Station.ARRIVALCHECK) ;
				tmpst.setArrivalCheck(arr) ;
				//#CM48737
				// loadSizeCheck
				boolean load = (rset.getInt("LOADSIZECHECK") == Station.LOADSIZECHECK) ;
				tmpst.setLoadSizeCheck(load) ;
				//#CM48738
				//<en> removal</en>
				boolean pay = (rset.getInt("REMOVE") == Station.PAYOUT_OK) ;
				tmpst.setRemove(pay) ;
				//#CM48739
				// inventory check Flag
				tmpst.setInventoryCheckFlag(rset.getInt("INVENTORYCHECKFLAG")) ;
				//#CM48740
				// Restoring Operation
				boolean res = (rset.getInt("RESTORINGOPERATION") == Station.CREATE_RESTORING) ;
				tmpst.setReStoringOperation(res) ;
				//#CM48741
				// Restoring Instruction
				tmpst.setReStoringInstruction(rset.getInt("RESTORINGINSTRUCTION")) ;
				//#CM48742
				// PoperationNeed
				boolean pop = (rset.getInt("POPERATIONNEED") == Station.POPERATIONNEED) ;
				tmpst.setPoperationNeed(pop) ;
				//#CM48743
				// WareHouse
				tmpst.setWarehouseStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER")));
				//#CM48744
				// station parent station
				tmpst.setParentStationNumber(DBFormat.replace(rset.getString("PARENTSTATIONNUMBER")));
				//#CM48745
				// ails station number
				tmpst.setAisleStationNumber(DBFormat.replace(rset.getString("AISLESTATIONNUMBER"))) ;
				//#CM48746
				// next station number
				tmpst.setNextStationNumber(DBFormat.replace(rset.getString("NEXTSTATIONNUMBER"))) ;
				//#CM48747
				// last station number
				tmpst.setLastUsedStationNumber(DBFormat.replace(rset.getString("LASTUSEDSTATIONNUMBER"))) ;
				//#CM48748
				// reject station number
				tmpst.setRejectStationNumber(DBFormat.replace(rset.getString("REJECTSTATIONNUMBER")));
				//#CM48749
				// mode type
				tmpst.setModeType(rset.getInt("MODETYPE")) ;
				//#CM48750
				// cyrrent mode
				tmpst.setCurrentMode(rset.getInt("CURRENTMODE")) ;
				//#CM48751
				// change mode request
				tmpst.setChangeModeRequest(rset.getInt("MODEREQUEST")) ;
				//#CM48752
				// change mode request time 
				Timestamp tims = rset.getTimestamp("MODEREQUESTTIME");
				java.util.Date mdate = null;
				if (tims != null)
					mdate = new java.util.Date(tims.getTime());
				else
					mdate = null;
				tmpst.setChangeModeRequestTime(mdate) ;
				//#CM48753
				// cached carrykey
				tmpst.setCarryKey(DBFormat.replace(rset.getString("CARRYKEY"))) ;
				//#CM48754
				// cached height of palette
				tmpst.setHeight(rset.getInt("HEIGHT")) ;
				//#CM48755
				// cached bar code data of palette
				tmpst.setBCData(DBFormat.replace(rset.getString("BCDATA"))) ;
				//#CM48756
				// class name
				tmpst.setClassName(DBFormat.replace(rset.getString("CLASSNAME"))) ;

				//#CM48757
				// station handler
				tmpst.setHandler(this) ;

				//#CM48758
				// append new Station instance to Vector
				tmpStVect.add(tmpst) ;
			}
		}
		catch (SQLException e)
		{
			//#CM48759
			//<en>6126001 = Database error occured.  {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolStationHandler" ) ;
			throw (new ReadWriteException("6126001" + wDelim + "Station")) ;
		}
		catch (Exception e)
		{
			if (e instanceof ReadWriteException)
			{
				throw (ReadWriteException)e;
			}
			e.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = "Station" ;
			tObj[1] = wSW.toString() ;
			//#CM48760
			//<en>6126003 = Failed to generate the instance. class name={0} {1}</en>
			RmiMsgLogClient.write(6126003, LogMessage.F_ERROR, "ToolStationHandler", tObj);
			throw (new ReadWriteException("6126003" + wDelim + tObj[0])) ;
		}
		finally
		{
			closeStatement() ;
		}

		//#CM48761
		// move instance from vector to array of Station
		Station[] rstarr = new Station[tmpStVect.size()] ;
		tmpStVect.copyInto(rstarr);

		return (rstarr) ;
	}

	//#CM48762
	// Private methods -----------------------------------------------
}
//#CM48763
//end of class

