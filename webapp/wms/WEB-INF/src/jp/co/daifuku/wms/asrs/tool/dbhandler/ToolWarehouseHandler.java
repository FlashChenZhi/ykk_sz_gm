// $Id: ToolWarehouseHandler.java,v 1.2 2006/10/30 02:17:10 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM49024
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
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
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

//#CM49025
/**<en>
 * This class is used to retrieve/store the <code>Warehouse</code> class from/to database.
 * Please use <code>StationFactory</code> in normal process to retrieve <code>Warehouse</code> class.
 * As <code>getHandler</code> method has been prepared for <code>Warehouse</code> class,
 * in case the Handler to support is unknown, retrieve one by using <code>getHandler</code> method.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:10 $
 * @author  $Author: suresh $
 </en>*/
public class ToolWarehouseHandler extends ToolStationHandler
{

	//#CM49026
	// Class fields --------------------------------------------------
	public static final String WAREHOUSE_HANDLE = "jp.co.daifuku.wms.base.dbhandler.WareHouseHandler";

	//#CM49027
	// Class variables -----------------------------------------------
	
	//#CM49028
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_WAREHOUSE";
	
	//#CM49029
	// Class method --------------------------------------------------
	//#CM49030
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:10 $") ;
	}

	//#CM49031
	// Constructors --------------------------------------------------
	//#CM49032
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolWarehouseHandler(Connection conn)
	{
		super (conn) ;
	}
	//#CM49033
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 * @param tablename :name of the table
	 </en>*/
	public ToolWarehouseHandler(Connection conn, String tablename)
	{
		super (conn) ;
		wTableName = tablename;
	}

	//#CM49034
	// Public methods ------------------------------------------------
	
	
	
	//#CM49035
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
			//#CM49036
			// execute the sql
			rset = stmt.executeQuery(sqlstring) ;
			
			sqlstring = "DELETE FROM " + wStationTypeTableName +
						" WHERE HANDLERCLASS = " + "'" + WAREHOUSE_HANDLE + "'" ;
			//#CM49037
			// execute the sql
			stmt.executeQuery(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM49038
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM49039
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
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "WarehouseHandler" ) ;
				//#CM49040
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM49041
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
	}
	
	//#CM49042
	/**<en>
	 * Search and retrieve the warehouse.
	 * @param key :Key for the search
	 * @return :the array of the created object
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
	{

		//#CM49043
		//-------------------------------------------------
		//#CM49044
		// variable define
		//#CM49045
		//-------------------------------------------------

		Warehouse[] fndWH = null ;	// for return variable
		Object[]  fmtObj = new Object[2] ;

		//#CM49046
		// for database access
		ResultSet rset = null ;

		String fmtSQL = "SELECT * FROM "+ wTableName +" {0} {1}" ;

		if (key.ReferenceCondition() != null)
		{
			if (key.SortCondition() != null)
			{
				fmtObj[0] = " WHERE " + key.ReferenceCondition();
				fmtObj[1] = " ORDER BY " + key.SortCondition();
			}
			else
			{
				fmtObj[0] = " WHERE " + key.ReferenceCondition();
			}
		}
		else if (key.SortCondition() != null)
		{
			fmtObj[0] = " ORDER BY " + key.SortCondition();
		}

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

		try
		{
			rset = executeSQL(sqlstring, true) ;	// private exec sql method
		}
		catch (NotFoundException nfe)
		{
			//#CM49047
			//<en>  This should not occur;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException dee)
		{
			//#CM49048
			//<en>  This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}

		//#CM49049
		// make Warehouse instances from resultset
		//#CM49050
		// !!! makeWarehouse() is private method of this.
		fndWH = makeWarehouse(rset) ;

		return(fndWH) ;
	}

	//#CM49051
	/**<en>
	 * Count the number of <code>Warehouse</code>.
	 * @param key :Key for the search
	 * @return    :number of search results
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int count(ToolSearchKey key) throws ReadWriteException
	{
		//#CM49052
		//<en>Create the object using find, then return the number.</en>
		//<en>Attention! This is not a preferable process on a perfoemace basis.</en>
		//<en>This may have to be rebuilt on demand. </en>
		//#CM49053
		//<en>CMENJP6052$CM !!! Recreate it for performance reasons.</en>
	int tcount = 0 ;
		try
		{
			ToolEntity[] whs = find(key) ;
			tcount = whs.length ;
		}
		catch (ReadWriteException e)
		{
			//#CM49054
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>

			throw (new ReadWriteException("6126001" + wDelim + "Warehouse")) ;
		}
		catch(Exception e)
		{
			//#CM49055
			//<en>6126001 =Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "WarehouseHandler" ) ;
			//#CM49056
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126001" + wDelim + "Warehouse")) ;
		}
		
		return(tcount) ;
	}

	//#CM49057
	/**<en>
	 * Create new warehouse information in database.
	 * @param tgt :entity instance which has the warehouse information to create
	 * @throws ReadWriteException  :Notifies if error occured in connection with database.
	 * @throws DataExistsException :Notifies if the same warehouse has been registered in database.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
		//#CM49058
		//-------------------------------------------------
		//#CM49059
		// variable define
		//#CM49060
		//-------------------------------------------------

		String fmtSQL = "INSERT INTO "+ wTableName +" ("	+
						"  STATIONNUMBER"			+	// 0
						", WAREHOUSENUMBER"			+	// 1
						", WAREHOUSETYPE"			+	// 2
						", MAXMIXEDPALETTE"			+	// 3
						", WAREHOUSENAME"			+	// 4
						", LASTUSEDSTATIONNUMBER"	+	// 5
						", EMPLOYMENTTYPE"			+	// 6
						") values (" +
						"{0},{1},{2},{3},{4}, {5},{6}" +
					")" ;
		String fmtSQL_StationType = "INSERT INTO "+wStationTypeTableName+" (" +
						"  STATIONNUMBER" +				// 0
						",  HANDLERCLASS" +				// 1
						") VALUES (" +
						"{0}, '" + WAREHOUSE_HANDLE + "'" + 
						")" ;

		Warehouse tgtWH ;
		String sqlstring ;
		//#CM49061
		//-------------------------------------------------
		//#CM49062
		// process
		//#CM49063
		//-------------------------------------------------
		if (tgt instanceof Warehouse)
		{
			tgtWH = (Warehouse)tgt ;
		}
		else
		{
			//#CM49064
			//<en>Fatal error has occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Warehouse Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}
		try
		{
			//#CM49065
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtWH) ;
			//#CM49066
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM49067
			// execute the sql
			executeSQL(sqlstring, false) ;
			
			//#CM49068
			//for stationtype table
			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			executeSQL(sqlstring, false) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM49069
			//<en> This should not occur;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
	}

	//#CM49070
	/**<en>
	 * Modify the warehouse information in database.
	 * @param tgt :entity instance which has the warehouse information to modify
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data of warehouse to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM49071
		//-------------------------------------------------
		//#CM49072
		// variable define
		//#CM49073
		//-------------------------------------------------
		String fmtSQL = "UPDATE "+ wTableName +" SET " +
						"  WAREHOUSENUMBER = {1}" +
						", WAREHOUSETYPE = {2}" +
						", MAXMIXEDPALETTE = {3}" +
						", WAREHOUSENAME = {4}" +
						", LASTUSEDSTATIONNUMBER = {5}" +
						", EMPLOYMENTTYPE = {6}" +
						"  WHERE STATIONNUMBER = {0}" ;

		Warehouse tgtWH ;
		String sqlstring ;
		//#CM49074
		//-------------------------------------------------
		//#CM49075
		// process
		//#CM49076
		//-------------------------------------------------
		if (tgt instanceof Warehouse)
		{
			tgtWH = (Warehouse)tgt ;
		}
		else
		{
			//#CM49077
			//<en>Fatal error has occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Warehouse Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}
		try
		{
			//#CM49078
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtWH) ;
			//#CM49079
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM49080
			// execute the sql
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException nfe)
		{
			//#CM49081
			//<en> This should not occur;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
	}

	//#CM49082
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
		//#CM49083
		//-------------------------------------------------
		//#CM49084
		// variable define
		//#CM49085
		//-------------------------------------------------
		Station[] fndStation = null ;
		Object[]  fmtObj     = new Object[3] ;
		String    table      = wTableName; 

		String fmtSQL = " UPDATE {0} SET {1} {2}";
		//#CM49086
		// for database access
		ResultSet rset = null ;

		fmtObj[0] = table;

		if (key.ModifyContents(table) == null)
		{
			//#CM49087
			//<en> Exception if the update values have not been set.</en>
			Object[] tobj = {table};
			RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, "WarehouseHandler", tobj);
			throw (new InvalidDefineException("6126005"));
		}
		fmtObj[1] = key.ModifyContents(table);

		if (key.ReferenceCondition(table) == null)
		{
			//#CM49088
			//<en> Exception if the update conditions have not been set.</en>
			Object[] tobj = {table};
			RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, "WarehouseHandler", tobj);
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
			//#CM49089
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new InvalidDefineException(dee.getMessage())) ;
		}
	}

	//#CM49090
	/**<en>
	 * Delete from database the warehouse information of entity instance which has been passed through parameter.
	 * @param tgt :entity instance which has the warehouse informtion to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if warehouse data to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM49091
		//-------------------------------------------------
		//#CM49092
		// variable define
		//#CM49093
		//-------------------------------------------------
		String fmtSQL = "DELETE FROM " + wTableName +
						" WHERE" +
						" STATIONNUMBER = {0}" ;

		String fmtSQL_StationType = "DELETE FROM "+wStationTypeTableName +
						" WHERE STATIONNUMBER = {0}" ;			// 0


		Warehouse tgtWH ;
		String sqlstring ;
		//#CM49094
		//-------------------------------------------------
		//#CM49095
		// process
		//#CM49096
		//-------------------------------------------------
		if (tgt instanceof Warehouse)
		{
			tgtWH = (Warehouse)tgt ;
		}
		else
		{
			//#CM49097
			//<en>Fatal error has occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Warehouse Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}
		try
		{
			//#CM49098
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtWH) ;
			//#CM49099
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM49100
			// execute the sql
			executeSQL(sqlstring, false) ;
			
			//#CM49101
			//for stationtype table.
			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			//#CM49102
			// execute the sql
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException nfe)
		{
			//#CM49103
			//<en> This should not occur;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
	}

	//#CM49104
	// Package methods -----------------------------------------------

	//#CM49105
	// Protected methods ---------------------------------------------
	//#CM49106
	/**<en>
	 * Retrieve information from the <code>Warehouse</code> instance and set to the Object array
	 * as a string.(<code>String</code>).
	 * This is prepared for INSERT and UPDATE.
	 * When storing in database and if appropiate, it will set string null in some cases.
	 * Enclose the string type items (VARCHAR) in ' single quotations.
	 * @param tgtWh :<code>Shelf</code> instance to retrieve the information
	 * @return :the Object array
	 * <p>
	 * The order of the arrays whould be as follows.<br>
	 * <pre>
	 * STATIONNUMBER         // 0
	 * WAREHOUSENUMBER       // 1
	 * WAREHOUSETYPE         // 2
	 * MAXMIXEDPALETTE       // 3
	 * WAREHOUSENAME         // 4
 	 * LASTUSEDSTATIONNUMBER // 5
 	 * EMPLOYMENTTYPE		 // 6
	 * </pre></p>
	 </en>*/
	/**<en>
	 * Retrieve information from the <code>Warehouse</code> instance and set to the Object array
	 * as a string.(<code>String</code>).
	 * This is prepared for INSERT and UPDATE.
	 * When storing in database and if appropiate, it will set string null in some cases.
	 * Enclose the string type items (VARCHAR) in ' single quotations.
	 * @param tgtWh :<code>Shelf</code> instance to retrieve the information
	 * @return :the Object array
	 * <p>
	 * The order of the arrays whould be as follows.<br>
	 * <pre>
	 * STATIONNUMBER         // 0
	 * WAREHOUSENUMBER       // 1
	 * WAREHOUSETYPE         // 2
	 * MAXMIXEDPALETTE       // 3
	 * WAREHOUSENAME         // 4
 	 * LASTUSEDSTATIONNUMBER // 5
 	 * EMPLOYMENTTYPE		 // 6
	 * </pre></p>
	 </en>*/
	protected Object[] setToArray(Warehouse tgtWH)
	{
		Object[] fmtObj = new Object[8] ;

		//#CM49107
		// set parameters
		fmtObj[0] = DBFormat.format(tgtWH.getNumber()) ;
		fmtObj[1] = Integer.toString(tgtWH.getWarehouseNumber()) ;
		fmtObj[2] = Integer.toString(tgtWH.getWarehouseType()) ;
		fmtObj[3] = Integer.toString(tgtWH.getMaxMixedPalette()) ;
		fmtObj[4] = DBFormat.format(tgtWH.getName()) ;
		fmtObj[5] = DBFormat.format(tgtWH.getLastUsedStationNumber()) ;
		fmtObj[6] = Integer.toString(tgtWH.getEmploymentType()) ;
		return (fmtObj) ;
	}

	//#CM49108
	// Private methods -----------------------------------------------
	//#CM49109
	/**<en>
	 * Retrieve each item from <code>ResultSet</code> and generate the <code>Warehouse</code> instance.
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	private Warehouse[] makeWarehouse(ResultSet rset) throws ReadWriteException
	{
		Vector tmpwhVect = new Vector(10) ;	// temporary store for Warehouse instances

		//#CM49110
		// data get from resultset and make new Warehouse instance
		try
		{
			while(rset.next())
			{
				Warehouse tmpwh = new Warehouse(DBFormat.replace(rset.getString("STATIONNUMBER"))) ;
				tmpwh.setWarehouseStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER")) );
				tmpwh.setWarehouseNumber(rset.getInt("WAREHOUSENUMBER")) ;
				tmpwh.setWarehouseType(rset.getInt("WAREHOUSETYPE"));
				tmpwh.setMaxMixedPalette(rset.getInt("MAXMIXEDPALETTE"));
				tmpwh.setName(DBFormat.replace(rset.getString("WAREHOUSENAME"))) ;
				tmpwh.setLastUsedStationNumber(DBFormat.replace(rset.getString("LASTUSEDSTATIONNUMBER"))) ;
				tmpwh.setEmploymentType(rset.getInt("EMPLOYMENTTYPE"));
				tmpwh.setHandler(this) ;

				//#CM49111
				// append new Warehouse instance to Vector
				tmpwhVect.add(tmpwh) ;
			}
		}
		catch (InvalidStatusException ise)
		{
			ise.printStackTrace() ;
			throw new ReadWriteException(ise.getMessage());
		}
		catch (SQLException e)
		{
			//#CM49112
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "WarehouseHandler" ) ;
			throw (new ReadWriteException()) ;
		}
		finally
		{
			closeStatement() ;
		}

		//#CM49113
		// move instance from vector to array of Warehouse
		Warehouse[] rwharr = new Warehouse[tmpwhVect.size()] ;
		tmpwhVect.copyInto(rwharr);

		return (rwharr) ;
	}
}
//#CM49114
//end of class

