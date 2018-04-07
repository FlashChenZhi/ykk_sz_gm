// $Id: ToolZoneInformationHandler.java,v 1.2 2006/10/30 02:17:09 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM49226
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

import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.wms.asrs.tool.location.ZoneInformation;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM49227
/**<en>
 * This class is used to retrieve/store the <code>ToolZoneInformationHandler</code> class 
 * from/to database.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:09 $
 * @author  $Author: suresh $
 </en>*/
public class ToolZoneInformationHandler implements ToolDatabaseHandler
{

	//#CM49228
	// Class fields --------------------------------------------------

	//#CM49229
	// Class variables -----------------------------------------------
	//#CM49230
	/**<en> *name of the table</en>*/

	private String wTableName = "TEMP_ZONEINFO";
	//#CM49231
	/**<en>
	 * Connection isntance to connect with database
	 * Transaction control is not conducted in this class.
	 </en>*/
	protected Connection wConn ;

	//#CM49232
	// Class method --------------------------------------------------
	//#CM49233
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:09 $") ;
	}

	//#CM49234
	// Constructors --------------------------------------------------
	//#CM49235
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolZoneInformationHandler(Connection conn)
	{
		setConnection(conn) ;
	}
	//#CM49236
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 * @param tablename :name of the table
	 </en>*/
	public ToolZoneInformationHandler(Connection conn, String tablename)
	{
		setConnection(conn) ;
		wTableName = tablename;
	}

	//#CM49237
	// Public methods ------------------------------------------------
	//#CM49238
	/**<en>
	 * Set <code>Connection</code> to connect with database.
	 * @param conn :Connection to set
	 </en>*/
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}

	//#CM49239
	/**<en>
	 * Retrieve <code>Connection</code> to connect with database.
	 * @return :<code>Connection</code> currently preserved
	 </en>*/
	public Connection getConnection()
	{
		return(wConn) ;
	}

	//#CM49240
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
			//#CM49241
			// execute the sql
			rset = stmt.executeQuery(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM49242
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM49243
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
				//#CM49244
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM49245
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}

	}

	//#CM49246
	/**<en>
	 * Search and retrieve the ZONE.
	 * @param key :key for the search. ToolZoneInformationSearchKey has to be the key.
	 * @return :the array of the created object
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[]  fmtObj = new Object[2] ;
		ZoneInformation[] zoneArray = null;

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
			stmt = wConn.createStatement();
			rset = stmt.executeQuery(sqlstring) ;
			zoneArray = makeZoneInformation(rset) ;
		}
		catch (SQLException e)
		{
			//#CM49247
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
			//#CM49248
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName));
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
				//#CM49249
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
				//#CM49250
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
		return zoneArray;
	}

	//#CM49251
	/**<en>
	 * Count the number of <code>ZONEINFO</code>.
	 * @param key :Key for the search
	 * @return    :number of search results
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int count(ToolSearchKey key) throws ReadWriteException
	{
		ToolEntity[] ent = find(key) ;
		return ent.length ;
	}

	//#CM49252
	/**<en>
	 * Create new zone information in database.
	 * @param tgt  :entity instance which has the zone information to create
	 * @throws ReadWriteException  :Notifies if error occured in connection with database.
	 * @throws DataExistsException :Notifies if zone information that match the numbers used in warehouse 
	 * has been  registered.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
		Statement stmt = null;

		String sql = "INSERT INTO "+wTableName+" ("	+
						"ZONEID"					+	// 0
						", ZONENAME"				+	// 1
						", TYPE"					+	// 2
						") values (" +
						"{0},{1},{2}" +
					")" ;

	 	try
	 	{
			//#CM49253
			//<en>FTTB Data duplication check to be done.</en>
			ZoneInformation ins = (ZoneInformation)tgt;
			stmt = wConn.createStatement();

			Object [] obj = setToZoneInformation(ins) ;
			String sqlstring = SimpleFormat.format(sql, obj) ;
			stmt.executeUpdate(sqlstring) ;
		}
		catch (SQLException e)
		{
			//#CM49254
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
			//#CM49255
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName));
		}
		finally
		{
			try
			{
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM49256
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
				//#CM49257
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
	}

	//#CM49258
	/**<en>
	 * Modify the zone information in database.
	 * @param  key :AlterKey instance which preserves the contents and conditions of the modification
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies when data to modify cannot be found in database.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		//#CM49259
		//-------------------------------------------------
		//#CM49260
		// variable define
		//#CM49261
		//-------------------------------------------------
		Station[] fndStation = null ;
		Object[]  fmtObj     = new Object[3] ;
		String    table      = wTableName; 
		Statement stmt       = null;

		String fmtSQL = " UPDATE {0} SET {1} {2}";

		fmtObj[0] = table;

		if (key.ModifyContents(table) == null)
		{
			//#CM49262
			//<en> Exception if the update values have not been set.</en>

			Object[] tobj = {table};
			RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, "ToolZoneInformationHandler", tobj);
			throw (new InvalidDefineException("6126005"));
		}
		fmtObj[1] = key.ModifyContents(table);

		if (key.ReferenceCondition(table) == null)
		{
			//#CM49263
			//<en> Exception if the update conditions have not been set.</en>

			Object[] tobj = {table};
			RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, "ToolZoneInformationHandler", tobj);
			throw (new InvalidDefineException("6126006"));
		}
		fmtObj[2] = "WHERE " + key.ReferenceCondition(table);

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

		try
		{
			stmt = wConn.createStatement();
			int count = stmt.executeUpdate(sqlstring) ;	// private exec sql method
			if (count == 0)
			{
				RmiMsgLogClient.write("6126014" + wDelim + wTableName, "ToolZoneInformationHandler");
				throw new NotFoundException( "6126014" + wDelim + wTableName);			}
		}
		catch (SQLException e)
		{
			//#CM49264
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
			//#CM49265
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName));
		}
		finally
		{
			try
			{
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM49266
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
				//#CM49267
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
	}

	//#CM49268
	/**<en>
	 * Modify the zone information in database.
	 * @param tgt :entity instance which has the zone information to modify
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if PlannedRetrieval to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		Statement stmt = null;

		String sql = " UPDATE "+wTableName+" SET" +
						" ZONEID = {0},"		+	// 0
						" ZONENAME = {1},"		+	// 1
						" TYPE = {2} "			+	// 2
						" WHERE ZONEID = {0}";
		try
		{
			ZoneInformation ins = (ZoneInformation)tgt;
			stmt = wConn.createStatement();
			Object [] obj = setToZoneInformation(ins) ;
			String sqlstring = SimpleFormat.format(sql, obj) ;
			int count = stmt.executeUpdate(sqlstring) ;
			if (count == 0)
			{
				RmiMsgLogClient.write("6126014" + wDelim + wTableName, "ToolZoneInformationHandler");
				throw new NotFoundException( "6126014" + wDelim + wTableName);
			}
		}
		catch (SQLException e)
		{
			//#CM49269
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
			//#CM49270
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName));
		}
		finally
		{
			try
			{
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM49271
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
				//#CM49272
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
	}

	//#CM49273
	/**<en>
	 * Delete from database the zone information which has been passed through parameter.
	 * @param tgt :entity instance which has the zone informtion to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if PlannedRetrieval data to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws NotFoundException, ReadWriteException
	{
		Statement   stmt         = null;
		ResultSet   rset         = null;

		String sql = "DELETE FROM "+wTableName+" WHERE ZONEID = {0}"; 

		try
		{
			ZoneInformation ins = (ZoneInformation)tgt;
			stmt = wConn.createStatement();
			Object [] obj = setToZoneInformation(ins) ;
			String sqlstring = SimpleFormat.format(sql, obj) ;

			int count = stmt.executeUpdate(sqlstring) ;
			if (count == 0)
			{
				RmiMsgLogClient.write("6126015" + wDelim + wTableName, "ToolZoneInformationHandler");
				throw new NotFoundException( "6126015" + wDelim + wTableName);				
			}
		}
		catch (SQLException e)
		{
			//#CM49274
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
			//#CM49275
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName));
		}
		finally
		{
			try
			{
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM49276
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
				//#CM49277
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
	}

	//#CM49278
	/**<en>
	 * Delete from database the information that match the key which has been passed through parameter.
	 * @param key :key of the data to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolSearchKey key) throws NotFoundException, ReadWriteException
	{
		//#CM49279
		//<en> Delete from DB.</en>
		ToolEntity[] tgts = find(key) ;
		for (int i=0; i < tgts.length; i++)
		{
			drop(tgts[i]) ;
		}
	}


	//#CM49280
	// Package methods -----------------------------------------------

	//#CM49281
	// Protected methods ---------------------------------------------
	//#CM49282
	/**<en>
	 * Retrieve information from the <code>ZoneInformation</code> instance and set to th Object array
	 * as a string(<code>String</code>).
	 * This is prepared for INSERT and UPDATE.
	 * When storing in database and if appropiate, it will set string null in some cases.
	 * Enclose the string type items (VARCHAR) in ' single quotations.
	 * @param tgt :<code>ZoneInformation</code> instance to retrieve the information 
	 * @return :the Object array
	 * <p>
	 * The order of the arrays whould be as follows.<br>
	 * <pre>
	 * ZONEID           // 0
	 * ZONENAME         // 1
	 * TYPE             // 2
	 * </pre></p>
	 </en>*/
	/**<en>
	 * Retrieve information from the <code>ZoneInformation</code> instance and set to th Object array
	 * as a string(<code>String</code>).
	 * This is prepared for INSERT and UPDATE.
	 * When storing in database and if appropiate, it will set string null in some cases.
	 * Enclose the string type items (VARCHAR) in ' single quotations.
	 * @param tgt :<code>ZoneInformation</code> instance to retrieve the information 
	 * @return :the Object array
	 * <p>
	 * The order of the arrays whould be as follows.<br>
	 * <pre>
	 * ZONEID           // 0
	 * ZONENAME         // 1
	 * TYPE             // 2
	 * </pre></p>
	 </en>*/
	protected Object[] setToZoneInformation(ZoneInformation tgt)
	{

		Object[] fmtObj = new Object[3] ;

		//#CM49283
		// set parameters
		fmtObj[0] = Integer.toString(tgt.getZoneID()) ;
		fmtObj[1] = DBFormat.format(tgt.getZoneName()) ;
		fmtObj[2] = Integer.toString(tgt.getType()) ;
		return (fmtObj) ;
	}

	//#CM49284
	// Private methods -----------------------------------------------
	//#CM49285
	/**<en>
	 * Retrieve each item from <code>ResultSet</code> and generate the <code>ZoneInformation</code> instance.
	 * @param rset <CODE>ResultSet</CODE> search result
	 * @return :the array of the created object
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	private ZoneInformation[] makeZoneInformation(ResultSet rset) throws ReadWriteException
	{

		Vector tmpVect = new Vector() ;	// temporary store for ZoneInformation instances

		//#CM49286
		// The data get from resultset and make new ZoneInformation instance
		try
		{
			while (rset.next())
			{
				ZoneInformation tmpa = new ZoneInformation() ;
				tmpa.setZoneID(rset.getInt("ZONEID"));
				tmpa.setZoneName(DBFormat.replace(rset.getString("ZONENAME"))) ;
				tmpa.setType(rset.getInt("TYPE"));
				tmpa.setHandler(this) ;
				//#CM49287
				// append new Aisle instance to Vector
				tmpVect.add(tmpa) ;
			}
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (SQLException e)
		{
			//#CM49288
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneInformationHandler" ) ;
			//#CM49289
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName));
		}

		//#CM49290
		// move instance from vector to array of Aisle
		ZoneInformation[] zns = new ZoneInformation[tmpVect.size()] ;
		tmpVect.copyInto(zns);

		return zns ;
	}
}
//#CM49291
//end of class

