// $Id: ToolAisleHandler.java,v 1.2 2006/10/30 02:17:22 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;
//#CM47185
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
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.tool.location.Aisle;
import jp.co.daifuku.wms.asrs.tool.location.Station;
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

//#CM47186
/**<en>
 * This class us used to retrieve and store the <code>Aisle</code> class from/to database.
 * Normally, please use <code>StationFactory</code> concerning the retrieval of <code>Aisle</code> class.
 * For <code>Aisle</code> class, <code>getHandler</code> methods are prepared; if support of a Handler
 * is needed, retrieve one by using <code>getHandler</code> method.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:22 $
 * @author  $Author: suresh $
 </en>*/
public class ToolAisleHandler extends ToolStationHandler
{

	//#CM47187
	// Class fields --------------------------------------------------
	public static final String AISLE_HANDLE = "jp.co.daifuku.wms.base.dbhandler.AisleHandler";

	//#CM47188
	// Class variables -----------------------------------------------
	//#CM47189
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_AISLE";

	//#CM47190
	// Class method --------------------------------------------------
	//#CM47191
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:22 $") ;
	}


	//#CM47192
	// Constructors --------------------------------------------------
	//#CM47193
	/**<en>
	 * Generate the instance by sprcifing <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolAisleHandler(Connection conn)
	{
		super (conn) ;
	}
	//#CM47194
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn : Connection to connect with database
	 * @param tablename :table name
	 </en>*/
	public ToolAisleHandler(Connection conn, String tablename)
	{
		super (conn) ;
		wTableName = tablename;
	}

	//#CM47195
	// Public methods ------------------------------------------------
	//#CM47196
	/**<en>
	 * Delete all table data.
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
			//#CM47197
			// execute the sql
			rset = stmt.executeQuery(sqlstring) ;
			
			sqlstring = "DELETE FROM " + wStationTypeTableName +
						" WHERE HANDLERCLASS = " + "'" + AISLE_HANDLE + "'" ;
			//#CM47198
			// execute the sql
			rset = stmt.executeQuery(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM47199
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47200
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
				//#CM47201
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM47202
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}
	}
	//#CM47203
	/**<en>
	 * Search and retrieve the aisle.
	 * @param key :Key for search 
	 * @return :the array of the object created
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
	{
		//#CM47204
		//-------------------------------------------------
		//#CM47205
		// variable define
		//#CM47206
		//-------------------------------------------------
		Aisle[] fnd = null ;	// for return variable
		Object[]  fmtObj = new Object[2] ;

		//#CM47207
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
			//#CM47208
			//<en> This will not occur;</en>
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException dee)
		{
			//#CM47209
			//<en> This will not occur;</en>
			throw (new ReadWriteException(dee.getMessage())) ;
		}

		fnd = makeAisle(rset) ;

		return(fnd) ;
	}

	//#CM47210
	/**<en>
	 * Count the number of the <code>Aisle</code>.
	 * @param key :Key for search
	 * @return :number of search result
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public int count(ToolSearchKey key) throws ReadWriteException
	{
		//#CM47211
		//<en> Create the object by using find, then return the number.</en>
		//#CM47212
		//<en>!!! This movement is not desirable on performance basis. The process should be rebuilt depending on circumstances.</en>
		int tcount = 0 ;
		try
		{
			ToolEntity[] ent = find(key) ;
			tcount = ent.length ;
		}
		catch (Exception e)
		{
			//#CM47213
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "AisleHandler" ) ;
			//#CM47214
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "Aisle")) ;
		}
		return(tcount) ;
	}

	//#CM47215
	/**<en>
	 * Create the new aisle information in database.
	 * @param tgt :entity instance which has the aisle information creating.
	 * @throws ReadWriteException  :Notifies if error occured in connection with database.
	 * @throws DataExistsException :Notifies if the same warehouse is already registered in database.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
		//#CM47216
		//-------------------------------------------------
		//#CM47217
		// variable define
		//#CM47218
		//-------------------------------------------------
		String fmtSQL = "INSERT INTO "+ wTableName +" ("	+
						"  STATIONNUMBER"			+	// 0
						", WHSTATIONNUMBER"			+	// 1
						", AISLENUMBER"				+	// 2
						", CONTROLLERNUMBER"		+	// 3
						", DOUBLEDEEPKIND"			+	// 4
						", STATUS"					+	// 5
						", INVENTORYCHECKFLAG"		+	// 6
						", LASTUSEDBANK"			+	// 7
						") values (" +
						"{0},{1},{2},{3},{4},{5},{6},{7}" +
					")" ;
		String fmtSQL_StationType = "INSERT INTO "+wStationTypeTableName+" (" +
						"  STATIONNUMBER" +				// 0
						",  HANDLERCLASS" +				// 1
						") VALUES (" +
						"{0}, '" + AISLE_HANDLE + "'" + 
						")" ;

		Aisle tgtAisle ;
		String sqlstring ;
		//#CM47219
		//-------------------------------------------------
		//#CM47220
		// process
		//#CM47221
		//-------------------------------------------------
		if (tgt instanceof Aisle)
		{
			tgtAisle = (Aisle)tgt ;
		}
		else
		{
			//#CM47222
			//<en>Fatal error occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Aisle Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}
		try
		{
			//#CM47223
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtAisle) ;
			//#CM47224
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM47225
			// execute the sql
			executeSQL(sqlstring, false) ;
			
			//#CM47226
			//for stationtype table
			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			executeSQL(sqlstring, false) ;
		}
		catch (NotFoundException dee)
		{
			//#CM47227
			//<en> This should not happen;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM47228
	/**<en>
	 * Modify the warehouse information in database.
	 * @param tgt :entity instance which has the warehouse insformation to create
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if warehouse to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM47229
		//-------------------------------------------------
		//#CM47230
		// variable define
		//#CM47231
		//-------------------------------------------------
		String fmtSQL = "UPDATE "+ wTableName +" SET " +
						", WHSTATIONNUMBER			= {1}" +
						", AISLENUMBER				= {2}" +
						", CONTROLLERNUMBER			= {3}" +
						", DOUBLEDEEPKIND			= {4}" +
						", STATUS					= {5}" +
						", INVENTORYCHECKFLAG		= {6}" +
						", LASTUSEDBANK				= {7}" +
						" WHERE " +
						" STATIONNUMBER = {0}" ;

		Aisle tgtAisle ;
		String sqlstring ;
		//#CM47232
		//-------------------------------------------------
		//#CM47233
		// process
		//#CM47234
		//-------------------------------------------------
		if (tgt instanceof Aisle)
		{
			tgtAisle = (Aisle)tgt ;
		}
		else
		{
			//#CM47235
			//<en>Fatal error occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Aisle Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}
		try
		{
			//#CM47236
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtAisle) ;
			//#CM47237
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM47238
			// execute the sql
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException dee)
		{
			//#CM47239
			//<en> This should not occur;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}

	//#CM47240
	/**<en>
	 * Modify the location data in database. The contents and conditions of the modification
	 * will be retrieved by AlterKey.
	 * @param  key ::AlterKey isntance which preserves the contents and conditions of modification 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if the target data to mofdify cannot be found in database.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		//#CM47241
		//-------------------------------------------------
		//#CM47242
		// variable define
		//#CM47243
		//-------------------------------------------------
		Station[] fndStation = null ;
		Object[]  fmtObj     = new Object[3] ;
		String    table      = wTableName; 

		String fmtSQL = " UPDATE {0} SET {1} {2}";
		//#CM47244
		// for database access
		ResultSet rset = null ;

		fmtObj[0] = table;

		if (key.ModifyContents(table) == null)
		{
			//#CM47245
			//<en> Exception if the conditions of update has not been set.</en>
			Object[] tobj = {table};
			RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, this.getClass().getName(), tobj);
			throw (new InvalidDefineException("6126005"));
		}
		fmtObj[1] = key.ModifyContents(table);

		if (key.ReferenceCondition(table) == null)
		{
			//#CM47246
			//<en> Exception if the conditions of update has not been set.</en>
			Object[] tobj = {table};
			RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, this.getClass().getName(), tobj);
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
			//#CM47247
			//<en> Tis should not happen;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
	}


	//#CM47248
	/**<en>
	 * Delete from database the warehouse information passed through parameter.
	 * @param tgt :entity instance which preserves the warehouse information to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if the warehouse information to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM47249
		//-------------------------------------------------
		//#CM47250
		// variable define
		//#CM47251
		//-------------------------------------------------
		String fmtSQL = "DELETE FROM " + wTableName +
						" WHERE" +
						" STATIONNUMBER = {0}" ;
		String fmtSQL_StationType = "DELETE FROM "+wStationTypeTableName +
						" WHERE STATIONNUMBER = {0}" ;			// 0

		Aisle tgtAisle ;
		String sqlstring ;
		//#CM47252
		//-------------------------------------------------
		//#CM47253
		// process
		//#CM47254
		//-------------------------------------------------
		if (tgt instanceof Aisle)
		{
			tgtAisle = (Aisle)tgt ;
		}
		else
		{
			//#CM47255
			//<en>Fatal error occurred.{0}</en>
			RmiMsgLogClient.write("6126499"+wDelim+"Illegal argument. Set Aisle Instance.", this.getClass().getName());
			throw (new ReadWriteException("6126499")) ;
		}
		try
		{
			//#CM47256
			// setting Station information to Object array
			Object [] fmtObj = setToArray(tgtAisle) ;
			//#CM47257
			// create actual SQL
			sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			//#CM47258
			// execute the sql
			executeSQL(sqlstring, false) ;
			
			//#CM47259
			//for stationtype table.
			sqlstring = SimpleFormat.format(fmtSQL_StationType, fmtObj) ;
			//#CM47260
			// execute the sql
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException nfe)
		{
			//#CM47261
			//<en> This should not occur;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
	}

	//#CM47262
	// Package methods -----------------------------------------------

	//#CM47263
	// Protected methods ---------------------------------------------
	//#CM47264
	/**<en>
	 * Retrieve the information from <code>Aisle</code> isntance, then set to Object array
	 * as a string (<code>String</code>).
	 * This is prepared for INSERT and UPDATE.
	 * When storing data in database and if null is appropriate, the string null will be set.
	 * The string type items (VARCHAR) should be snclosed in '(single quotations).
	 * @param tgt :<code>Aisle</code> instance to retrieve the information
	 * @return Object array
	 * <p>
	 * The order of array should be as follows.<br>
	 * <pre>
	 * STATIONNUMBER       // 0
	 * WHSTATIONNUMBER     // 1
	 * AISLENUMBER          // 2
	 * CONTROLLERNUMBER    // 3
	 * DOUBLEDEEPKIND      // 4
	 * STATUS              // 5
	 * INVENTORYCHECKFLAG  // 6
	 * LASTUSEDBANK        // 7
	 * </pre></p>
	 </en>*/
	/**<en>
	 * Retrieve the information from <code>Aisle</code> isntance, then set to Object array
	 * as a string (<code>String</code>).
	 * This is prepared for INSERT and UPDATE.
	 * When storing data in database and if null is appropriate, the string null will be set.
	 * The string type items (VARCHAR) should be snclosed in '(single quotations).
	 * @param tgt :<code>Aisle</code> instance to retrieve the information
	 * @return Object array
	 * <p>
	 * The order of array should be as follows.<br>
	 * <pre>
	 * STATIONNUMBER       // 0
	 * WHSTATIONNUMBER     // 1
	 * AISLENUMBER          // 2
	 * CONTROLLERNUMBER    // 3
	 * DOUBLEDEEPKIND      // 4
	 * STATUS              // 5
	 * INVENTORYCHECKFLAG  // 6
	 * LASTUSEDBANK        // 7
	 * </pre></p>
	 </en>*/
	protected Object[] setToArray(Aisle tgt)
	{

		Object[] fmtObj = new Object[8] ;
		//#CM47265
		// set parameters
		fmtObj[0] = DBFormat.format(tgt.getNumber()) ;
		fmtObj[1] = DBFormat.format(tgt.getWarehouseStationNumber()) ;
		fmtObj[2] = Integer.toString(tgt.getAisleNumber()) ;
		if (tgt.getGroupController() == null)
		{
			fmtObj[3] = null;
		}
		else
		{
			fmtObj[3] = Integer.toString(tgt.getGroupController().getNumber()) ;
		}
		fmtObj[4] = Integer.toString(tgt.getDoubleDeepKind()) ;
		fmtObj[5] = Integer.toString(tgt.getStatus()) ;
		fmtObj[6] = Integer.toString(tgt.getInventoryCheckFlag()) ;
		fmtObj[7] = Integer.toString(tgt.getLastUsedBank()) ;

		return (fmtObj) ;
	}

	//#CM47266
	// Private methods -----------------------------------------------
	//#CM47267
	/**<en>
	 * Retrieve each item from <code>ResultSet</code> and generate isntances of <code>Aisle</code>.
	 * @throws ReadWriteException :Notifies if error occurred in connection with database.
	 </en>*/
	private Aisle[] makeAisle(ResultSet rset) throws ReadWriteException
	{
		Vector tmpVect = new Vector(10) ;	// temporary store for Aisle instances

		//#CM47268
		// data get from resultset and make new Aisle instance
		try
		{
			while (rset.next())
			{
				Aisle tmpa = new Aisle(DBFormat.replace(rset.getString("STATIONNUMBER"))) ;
				tmpa.setWarehouseStationNumber(DBFormat.replace(rset.getString("WHSTATIONNUMBER"))) ;
				tmpa.setAisleNumber(rset.getInt("AISLENUMBER"));
				tmpa.setGroupController(GroupController.getInstance(wConn, rset.getInt("CONTROLLERNUMBER"))) ;
				tmpa.setDoubleDeepKind(rset.getInt("DOUBLEDEEPKIND"));
				tmpa.setStatus(rset.getInt("STATUS"));
				tmpa.setInventoryCheckFlag(rset.getInt("INVENTORYCHECKFLAG"));
				tmpa.setLastUsedBank(rset.getInt("LASTUSEDBANK"));
				tmpa.setHandler(this) ;
				//#CM47269
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
			//#CM47270
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "AisleHandler" ) ;
			throw (new ReadWriteException()) ;
		}
		finally
		{
			closeStatement() ;
		}

		//#CM47271
		// move instance from vector to array of Aisle
		Aisle[] aisles = new Aisle[tmpVect.size()] ;
		tmpVect.copyInto(aisles);

		return (aisles) ;
	}

}
//#CM47272
//end of class

