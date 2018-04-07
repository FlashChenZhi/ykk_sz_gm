// $Id: ToolHardZoneHandler.java,v 1.2 2006/10/30 02:17:17 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47712
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
import jp.co.daifuku.wms.asrs.tool.location.HardZone;
import jp.co.daifuku.wms.asrs.tool.location.Zone;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM47713
/**<en>
 * This class is used to retrieve/store the <code>Zone</code> class to/from database.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:17 $
 * @author  $Author: suresh $
 </en>*/

public class ToolHardZoneHandler implements ToolDatabaseHandler
{

	//#CM47714
	// Class fields --------------------------------------------------

	//#CM47715
	// Class variables -----------------------------------------------
	
	//#CM47716
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_HARDZONE";
	
	//#CM47717
	/**<en>
	 * Connection instance to connect with database
	 * Transaction control is not conducted in this class.
	 </en>*/
	protected Connection wConn ;

	//#CM47718
	// Class method --------------------------------------------------
	//#CM47719
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:17 $") ;
	}

	//#CM47720
	// Constructors --------------------------------------------------
	//#CM47721
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolHardZoneHandler(Connection conn)
	{
		setConnection(conn) ;
	}
	//#CM47722
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 * @param tablename :name of the table
	 </en>*/
	public ToolHardZoneHandler(Connection conn, String tablename)
	{
		setConnection(conn) ;
		wTableName = tablename;
	}

	//#CM47723
	// Public methods ------------------------------------------------
	//#CM47724
	/**<en>
	 * Set the <code>Connection</code> to connect with database.
	 * @param conn :Connection to set
	 </en>*/
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}

	//#CM47725
	/**<en>
	 * Retrieve the <code>Connection</code> to connect with database.
	 * @return :<code>Connection</code> currently preserved
	 </en>*/
	public Connection getConnection()
	{
		return(wConn) ;
	}

	//#CM47726
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
			//#CM47727
			// execute the sql
			rset = stmt.executeQuery(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM47728
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47729
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
				//#CM47730
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM47731
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}

	}

	//#CM47732
	/**<en>
	 * Search and retrieve the TEMP_HARDZONE.
	 * @param key :Key for search. ToolHardZoneSearchKey has to be used as a key.
	 * @return :the array of the created object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[]  fmtObj = new Object[2] ;
		HardZone[] zoneArray = null;

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
			zoneArray = makeZone(rset) ;
		}
		catch (SQLException e)
		{
			//#CM47733
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
			//#CM47734
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
				//#CM47735
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
				//#CM47736
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
		return zoneArray;
	}

	//#CM47737
	/**<en>
	 * Count the number of <code>Zone</code>.
	 * @param key :Key for search. 
	 * @return :number of search results
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int count(ToolSearchKey key) throws ReadWriteException
	{
		Statement stmt      = null;
		ResultSet rset      = null;
		int       count     = 0;
		Object[] fmtObj = new Object[1] ;

	 	try
	 	{
			stmt = wConn.createStatement();

			String fmtSQL = "SELECT COUNT(1) COUNT FROM "+ wTableName +" {0}" ;

			if (key.ReferenceCondition() != null)
			{
				fmtObj[0] = "WHERE " + key.ReferenceCondition();
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			rset = stmt.executeQuery(sqlstring) ;

			while (rset.next())
			{
				count = rset.getInt("count");
			}
		}
		catch(SQLException e)
		{
			//#CM47738
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
			//#CM47739
			//<en>Throw the ReadWriteException here.</en>
			throw new ReadWriteException() ;
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
				//#CM47740
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
				//#CM47741
				//<en>Throw the ReadWriteException here.</en>
				throw new ReadWriteException() ;
			}
		}
		return count ;

//#CM47742
//		Entity[] ent = find(key) ;
//#CM47743
//		return ent.length ;
	}

	//#CM47744
	/**<en>
	 * Newly create hte zone information in database.
	 * @param tgt :entity instance which preserves the zone information to create
	 * @throws ReadWriteException  :Notifies if error occured in connection with database.
	 * @throws DataExistsException Notifies if the zone information registered match the zone no.
	 * of warehousees.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
		Statement stmt = null;

		String sql = "INSERT INTO "+ wTableName +" ("		+
						"HARDZONEID"			+	// 0
						", NAME"				+	// 1
						", WHSTATIONNUMBER"		+	// 2
						", HEIGHT"				+	// 3
						", PRIORITY"			+	// 4
						", STARTBANK"			+	// 5
						", STARTBAY"			+	// 6
						", STARTLEVEL"			+	// 7
						", ENDBANK"				+	// 8
						", ENDBAY"				+	// 9
						", ENDLEVEL"			+	// 10
						") values (" +
						"{0},{1},{2},{3},{4},{5},{6},{7},{8},{9},{10}" +
					")" ;

	 	try
	 	{
			//#CM47745
			//<en>FTTB Data duplication check to be done.</en>

			HardZone ins = (HardZone)tgt;
			stmt = wConn.createStatement();
//#CM47746
/**<en>			*********************************************************
			  If identical serial no. exist, obtain a different no. 
			*********************************************************</en>*/
//#CM47747
//			SequenceHandler sh = new SequenceHandler(wConn);
//#CM47748
//			int serialNo = 0;
//#CM47749
//			do
//#CM47750
//			{
//#CM47751
//				serialNo = sh.nextZoneSerialNo();
//#CM47752
//				serialNo++;
//#CM47753
//			}
//#CM47754
//<en>			//It exits the do loop when the searial no. are not identical.</en>
//#CM47755
//			while(isZoneTable(serialNo));

			Object [] obj = setToZone(ins) ;
			String sqlstring = SimpleFormat.format(sql, obj) ;
			stmt.executeUpdate(sqlstring) ;
		}
		catch (SQLException e)
		{
			//#CM47756
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
			//#CM47757
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
				//#CM47758
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
				//#CM47759
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
	}

	//#CM47760
	/**<en>
	 * Modify the zoner information in database. 
	 * @param  key :AlterKey isntance which preserves the contents and conditions of modification 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		//#CM47761
		//-------------------------------------------------
		//#CM47762
		// variable define
		//#CM47763
		//-------------------------------------------------
		Zone[] fndStation = null ;
		Object[]  fmtObj     = new Object[3] ;
		String    table      = wTableName; 
		Statement stmt       = null;

		String fmtSQL = " UPDATE {0} SET {1} {2}";
		//#CM47764
		// for database access
		ResultSet rset = null ;

		fmtObj[0] = table;

		if (key.ModifyContents(table) == null)
		{
			//#CM47765
			//<en> Exception if update value has not been set.</en>
			Object[] tobj = {table};
			RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, "ToolZoneHandler", tobj);
			throw (new InvalidDefineException("6126005"));
		}
		fmtObj[1] = key.ModifyContents(table);

		if (key.ReferenceCondition(table) == null)
		{
			//#CM47766
			//<en> Exception if update conditions have not been set.</en>
			Object[] tobj = {table};
			RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, "ToolZoneHandler", tobj);
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
				RmiMsgLogClient.write("6126014" + wDelim + wTableName, "ToolZoneHandler");
				throw new NotFoundException( "6126014" + wDelim + wTableName);
			}
		}
		catch (SQLException e)
		{
			//#CM47767
			//<en>6126001 = Database error occured.{0}</en>

			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
			//#CM47768
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
				//#CM47769
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
				//#CM47770
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
	}

	//#CM47771
	/**<en>
	 * Modify the zone information in database. Modify the zone information using the 
	 * SERIALNUMBER as a key.
	 * @param tgt :entity instance which preserves the zone information to create
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.  
	 * @throws NotFoundException  :Notifies if the zone to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		Statement stmt = null;

		String sql = " UPDATE "+ wTableName +" SET" 	+
						" ZONEID = {0},"		+	// 0
						" TYPE = {1},"		    +	// 1
						" HEIGHT = {2},"		+	// 2
						" DIRECTION = {3},"		+	// 3
						" WHSTATIONNUMBER = {4},"+	// 4
						" STARTBANK = {5},"		+	// 5
						" STARTBAY = {6},"		+	// 6
						" STARTLEVEL = {7},"	+	// 7
						" ENDBANK = {8}, "		+	// 8
						" ENDBAY = {9}, "		+	// 9
						" ENDLEVEL = {10}, "	+	// 10
						" ORDERNUMBER = {11}, "	+	// 11
						" PRIORITY = {13} "		+	// 13
						" WHERE SERIALNUMBER = {12}"; // 12
		try
		{
			HardZone ins = (HardZone)tgt;
			stmt = wConn.createStatement();
			Object [] obj = setToZone(ins) ;
			String sqlstring = SimpleFormat.format(sql, obj) ;
			int count = stmt.executeUpdate(sqlstring) ;
			if (count == 0)
			{
				RmiMsgLogClient.write("6126014" + wDelim + wTableName, "ToolZoneHandler");
				throw new NotFoundException("6126014" + wDelim + wTableName);
			}
		}
		catch (SQLException e)
		{
			//#CM47772
			//<en>6126001 = Database error occured.{0}</en>

			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
			//#CM47773
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
				//#CM47774
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
				//#CM47775
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
	}

	//#CM47776
	/**<en>
	 * Delete from database the zone information passed through parameter.
	 * In this method, data will be deleted by using SERIALNUMBER as a key.
	 * @param tgt :entity instance which preserves the zone information to delete
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.  
	 * @throws NotFoundException  :Notifies if zone to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws NotFoundException, ReadWriteException
	{
		Statement   stmt         = null;
		ResultSet   rset         = null;
		String sql = "DELETE FROM "+ wTableName +" "; 
		try
		{
			HardZone ins = (HardZone)tgt;
			stmt = wConn.createStatement();
			Object [] obj = setToZone(ins) ;
			String sqlstring = SimpleFormat.format(sql, obj) ;
			int count = stmt.executeUpdate(sqlstring) ;
			if (count == 0)
			{
				RmiMsgLogClient.write("6126015" + wDelim + wTableName, "ToolZoneHandler");
				throw new NotFoundException("6126015" + wDelim + wTableName);
			}
		}
		catch (SQLException e)
		{
			//#CM47777
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
			//#CM47778
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
				//#CM47779
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
				//#CM47780
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
	}

	//#CM47781
	/**<en>
	 * Delete from database the information that match the key passed through parameter.
	 * @param key :key for the information to delete
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.  
	 * @throws NotFoundException  :Notifies if information cannot be found as a result of search.
	 </en>*/
	public void drop(ToolSearchKey key) throws NotFoundException, ReadWriteException
	{
		//#CM47782
		//<en> Delete from DB.</en>
		ToolEntity[] tgts = find(key) ;

		if ( tgts.length > 0 )
		{ 
			drop(tgts[0]) ;		
		}
	}


	//#CM47783
	// Package methods -----------------------------------------------

	//#CM47784
	// Protected methods ---------------------------------------------
	//#CM47785
	/**<en>
	 * Retrieve the information from <code>Zone</code> instance and set to Object array
	 * as string (<code>String</code>) instance.
	 * This is prepared fpr INSERT and UPDATE.
	 * When saving data in database. it sets the string null if appropriate.
	 * String type items (VARCHAR) shuold be enclosed in ' (single quotations).
	 * @param tgt :<code>Zone</code> instance to retrieve the information
	 * @return :Object array
	 * <p>
	 * The order of arrays should be as follows.<br>
	 * <pre>
	 * ZONEID           // 0
	 * NAME       		// 1
	 * WHSTATIONNUMBER 	// 2
	 * HEIGHT	        // 3
	 * PRIORITY			// 4
	 * STARTBANK        // 5
	 * STARTBAY         // 6
	 * STARTLEVEL       // 7
	 * ENDBANK          // 8
	 * ENDBAY           // 9
	 * ENDLEVEL         // 10
	 * </pre></p>
	 </en>*/
	/**<en>
	 * Retrieve the information from <code>Zone</code> instance and set to Object array
	 * as string (<code>String</code>) instance.
	 * This is prepared fpr INSERT and UPDATE.
	 * When saving data in database. it sets the string null if appropriate.
	 * String type items (VARCHAR) shuold be enclosed in ' (single quotations).
	 * @param tgt :<code>Zone</code> instance to retrieve the information
	 * @return :Object array
	 * <p>
	 * The order of arrays should be as follows.<br>
	 * <pre>
	 * ZONEID           // 0
	 * NAME       		// 1
	 * WHSTATIONNUMBER 	// 2
	 * HEIGHT	        // 3
	 * PRIORITY			// 4
	 * STARTBANK        // 5
	 * STARTBAY         // 6
	 * STARTLEVEL       // 7
	 * ENDBANK          // 8
	 * ENDBAY           // 9
	 * ENDLEVEL         // 10
	 * </pre></p>
	 </en>*/
	protected Object[] setToZone(HardZone tgt)
	{

		Object[] fmtObj = new Object[11] ;

		//#CM47786
		// set parameters
		fmtObj[0] = Integer.toString(tgt.getHardZoneID()) ;
		fmtObj[1] = DBFormat.format(tgt.getName()) ;
		fmtObj[2] = DBFormat.format(tgt.getWareHouseStationNumber()) ;
		fmtObj[3] = Integer.toString(tgt.getHeight()) ;
		fmtObj[4] = DBFormat.format(tgt.getPriority()) ;
		fmtObj[5] = Integer.toString(tgt.getStartBank()) ;
		fmtObj[6] = Integer.toString(tgt.getStartBay()) ;
		fmtObj[7] = Integer.toString(tgt.getStartLevel()) ;
		fmtObj[8] = Integer.toString(tgt.getEndBank()) ;
		fmtObj[9] = Integer.toString(tgt.getEndBay()) ;
		fmtObj[10] = Integer.toString(tgt.getEndLevel()) ;

		return (fmtObj) ;
	}

	//#CM47787
	// Private methods -----------------------------------------------
	//#CM47788
	/**<en>
	 * Retrieve each item from <code>ResultSet</code> and generate the <code>Zone</code> instance.
	 * @throws ReadWriteException :Notifies if error occurred in connection with database.
	 </en>*/
	private HardZone[] makeZone(ResultSet rset) throws ReadWriteException
	{
		Vector tmpVect = new Vector(10) ;	// temporary store for Aisle instances
		//#CM47789
		// data get from resultset and make new Aisle instance
		try
		{
			while (rset.next())
			{
				HardZone tmpa = new HardZone() ;
				tmpa.setHardZoneID(rset.getInt("HARDZONEID"));
				tmpa.setName(DBFormat.replace(rset.getString("NAME")));
				tmpa.setWareHouseStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER"))) ;
				tmpa.setHeight(rset.getInt("HEIGHT"));
				tmpa.setPriority(DBFormat.replace(rset.getString("PRIORITY")));
				tmpa.setStartBank(rset.getInt("STARTBANK"));
				tmpa.setStartBay(rset.getInt("STARTBAY"));
				tmpa.setStartLevel(rset.getInt("STARTLEVEL"));
				tmpa.setEndBank(rset.getInt("ENDBANK"));
				tmpa.setEndBay(rset.getInt("ENDBAY"));
				tmpa.setEndLevel(rset.getInt("ENDLEVEL"));
				tmpa.setHandler(this) ;
				//#CM47790
				// append new Aisle instance to Vector
				tmpVect.add(tmpa) ;
			}
		}
		catch (SQLException e)
		{
			//#CM47791
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
			//#CM47792
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName));
		}

		//#CM47793
		// move instance from vector to array of Aisle
		HardZone[] zns = new HardZone[tmpVect.size()] ;
		tmpVect.copyInto(zns);

		return zns ;
	}
	//#CM47794
	/**<en>
	 * Check whether/not the specified serial no. exists in the zone table of database.
	 * @param serialNo  :serial no.
	 * @return     :whether/not the identical serial no. exists
	 </en>*/
	private boolean isZoneTable(int serialNo) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[]  fmtObj = new Object[2] ;
		int count = 0;
		String fmtSQL = "SELECT COUNT(*) COUNT FROM "+ wTableName +" WHERE SERIALNUMBER = {0} ";

		fmtObj[0] = "" + serialNo + "";
		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

		try 
		{
			stmt = wConn.createStatement();
			rset = stmt.executeQuery(sqlstring);
			while(rset.next())
			{
				count = rset.getInt("COUNT");
			}
		}
		catch(SQLException e)
		{
			//#CM47795
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
			//#CM47796
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			//#CM47797
			//<en>6126013 = Failed to search {0}. Please refer to the log.</en>
			throw (new ReadWriteException("6126013" + wDelim + wTableName)) ;
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
				//#CM47798
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
				//#CM47799
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				//#CM47800
				//<en>6126013 = Failed to search {0}. Please refer to the log.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName)) ;
			}
		}
		if (count == 0)
			return false;
		else
			return true;
	}
}
//#CM47801
//end of class

