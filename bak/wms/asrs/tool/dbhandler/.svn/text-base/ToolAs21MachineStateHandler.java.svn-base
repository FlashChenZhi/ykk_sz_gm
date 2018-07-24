// $Id: ToolAs21MachineStateHandler.java,v 1.2 2006/10/30 02:17:20 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47392
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
import jp.co.daifuku.wms.asrs.tool.common.ToolParam;
import jp.co.daifuku.wms.asrs.tool.communication.as21.As21MachineState;
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

//#CM47393
/**<en>
 * This class is used to retrieve/store the <code>As21MachineState</code> class  
 * of environment setting tool from/to database.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:20 $
 * @author  $Author: suresh $
 </en>*/
public class ToolAs21MachineStateHandler implements ToolDatabaseHandler
{

	//#CM47394
	// Class fields --------------------------------------------------

	//#CM47395
	// Class variables -----------------------------------------------
	Connection wConn ;
	Statement wStatement = null ;

	//#CM47396
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_MACHINE";

	//#CM47397
	// Class method --------------------------------------------------

	//#CM47398
	// Constructors --------------------------------------------------
	//#CM47399
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn : to connect with database
	 </en>*/
	public ToolAs21MachineStateHandler(Connection conn)
	{
		setConnection(conn) ;
	}

	//#CM47400
	// Public methods ------------------------------------------------
	//#CM47401
	/**<en>
	 * Set the <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}
	//#CM47402
	/**<en>
	 * Retrieve the <code>Connection</code> to connect with database.
	 * @return :Connection to connect with database
	 </en>*/
	public Connection getConnection()
	{
		return (wConn) ;
	}

	//#CM47403
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
			//#CM47404
			// execute the sql
			rset = stmt.executeQuery(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM47405
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47406
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
				//#CM47407
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM47408
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}

	}

	//#CM47409
	/**<en>
	 * Search and retrieve the machine status.
	 * @param key :Key for search
	 * @return    :the array of the created object
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException 
	{
		//#CM47410
		//-------------------------------------------------
		//#CM47411
		// variable define
		//#CM47412
		//-------------------------------------------------
		ResultSet rset      = null;
		As21MachineState[] fndMS = null ;

		//#CM47413
		// for error message
		String msg = "" ;	
		Object[] fmtObj = new Object[2] ;


		String fmtSQL = "SELECT * FROM " + wTableName + " {0} {1} ";

	 	try
	 	{
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

			//#CM47414
			// execute the sql (super)

			rset = executeSQL(sqlstring, true) ;

			//#CM47415
			// make As21MachineState instances from resultset
			//#CM47416
			// !!! makeAs21MachineState() is private method of this.
			fndMS = makeAs21MachineState(rset) ;
		}
		catch (DataExistsException dee)
		{
			//#CM47417
			//<en> This should not happen;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM47418
			//<en> This should not happen;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}

		return(fndMS) ;
	}

	//#CM47419
	/**<en>
	 * Retrieve the number of machine status data that match the search conditions.
	 * @param key :Key for search
	 * @return    :number of search results
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

			String fmtSQL = "SELECT COUNT(1) COUNT FROM " + wTableName + " {0} " ; 

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
			//#CM47420
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolAs21MachineStateHandler" ) ;
			//#CM47421
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "MachineState")) ;
			
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
				//#CM47422
				//<en>6126001 = Database error occured. {0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolAs21MachineStateHandler" ) ;
				//#CM47423
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "MachineState")) ;
			}
		}

		return count ;
	}

	//#CM47424
	/**<en>
	 * Newly create the new machine information in database.
	 * @param tgt  :entity instance which preserves the machine information to create.
	 * @throws ReadWriteException  :Notifies if error occured in connection with database. 
	 * @throws DataExistsException :Notifies if the same machine status is already registered in database.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{

		//#CM47425
		//-------------------------------------------------
		//#CM47426
		// variable define
		//#CM47427
		//-------------------------------------------------
		String fmtSQL = "INSERT INTO " + wTableName + " (" +
						//#CM47428
						// 0
						" STATIONNUMBER" +
						//#CM47429
						// 1
						", MACHINETYPE"  +
						//#CM47430
						// 2
						", MACHINENUMBER"+
						//#CM47431
						// 3
						", STATE"        +
						//#CM47432
						// 4
						", ERRORCODE"    +
						//#CM47433
						// 5
						", CONTROLLERNUMBER"+
						") VALUES (" +
						"{0},{1},{2},{3},{4},{5}" +
					")" ;

		As21MachineState tgtMS = null ;

		//#CM47434
		//-------------------------------------------------
		//#CM47435
		// process
		//#CM47436
		//-------------------------------------------------
		if (tgt instanceof As21MachineState)
		{
			tgtMS = (As21MachineState)tgt ;
		}
		else
		{
			RmiMsgLogClient.write(6126016, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
			throw (new ReadWriteException("6126016")) ;
		}
		Object [] fmtObj = setToMachine(tgtMS);
		//#CM47437
		// create actual sql
		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

		try
		{
			//#CM47438
			// execute the sql (super)
			executeSQL(sqlstring, false) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM47439
			//<en> This should not happen;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException de)
		{
			//#CM47440
			//<en> Failed to register the machine. The data already exists.</en>
			de.printStackTrace() ;
			throw (new ReadWriteException(de.getMessage())) ;
		}
	}

	//#CM47441
	/**<en>
	 * Modify the infornmation of machine status in database.
	 * @param tgt :entity instance which preserves the machine information to create.
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if the machine to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM47442
		//-------------------------------------------------
		//#CM47443
		// variable define
		//#CM47444
		//-------------------------------------------------
		String fmtSQL = "UPDATE " + wTableName + "SET " +
						" STATIONNUMBER = {0}" +
						", STATE        = {3}" +
						", ERRORCODE    = {4}" +
						" WHERE" +
						" MACHINETYPE = {1} AND MACHINENUMBER = {2} AND CONTROLLERNUMBER = {5}" ;

		As21MachineState tgtMS = null ;
		//#CM47445
		//-------------------------------------------------
		//#CM47446
		// process
		//#CM47447
		//-------------------------------------------------
		if (tgt instanceof As21MachineState)
		{
			tgtMS = (As21MachineState)tgt ;
		}
		else
		{
			RmiMsgLogClient.write(6126004, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
			throw (new ReadWriteException("6126004")) ;
		}
		Object [] fmtObj = setToMachine(tgtMS);
		//#CM47448
		// create actual sql
		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

		try
		{
			//#CM47449
			// execute the sql (super)
			executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException nfe)
		{
			//#CM47450
			//<en> This should not happen;</en>
			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM47451
			//<en> This should not happen;</en>
			nfe.printStackTrace() ;
			//#CM47452
			//<en> There is no corresponding machine.</en>
			throw new ReadWriteException("6124003") ;
		}
	}

	//#CM47453
	/**<en>
	 * Modify the information of machine status in database. The contents and conditions of modification 
	 * will be retrieved by ToolAlterKey.
	 * @param  key :ToolAlterKey instance which preservese the contents and conditions of modification
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if target data of modification cannot be found in database.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 * @throws InvalidDefineException :Notifies if the conditions of update have not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		//#CM47454
		//-------------------------------------------------
		//#CM47455
		// variable define
		//#CM47456
		//-------------------------------------------------
		Station[] fndStation = null ;
		Object[]  fmtObj     = new Object[3] ;
		String    table      = wTableName; 

		String fmtSQL = " UPDATE {0} SET {1} {2}";
		//#CM47457
		// for database access
		ResultSet rset = null ;

		fmtObj[0] = table;

		if (key.ModifyContents(table) == null)
		{
			//#CM47458
			//<en> Exception if update value has not been set;</en>
			Object[] tobj = {table};
			RmiMsgLogClient.write(6126006, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", tobj);
			throw (new InvalidDefineException("6126006"));
		}
		fmtObj[1] = key.ModifyContents(table);

		if (key.ReferenceCondition(table) == null)
		{
			//#CM47459
			//<en> Exception if update conditions have not been set;</en>
			Object[] tobj = {table};
			RmiMsgLogClient.write(6126005, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", tobj);
			throw (new InvalidDefineException("6126005"));
		}
		fmtObj[2] = "WHERE " + key.ReferenceCondition(table);

		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

		try
		{
			//#CM47460
			// private exec sql method
			rset = executeSQL(sqlstring, false) ;
		}
		catch (DataExistsException dee)
		{
			//#CM47461
			//<en> This should not happen;</en>
			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM47462
			//<en> This should not happen;</en>
			nfe.printStackTrace() ;
			//#CM47463
			//<en> There is no corresponding machine.</en>
			throw new ReadWriteException("6124003") ;
		}
	}

	//#CM47464
	/**<en>
	 * Delete from database the information of machine status passed through parameter.
	 * @param tgt :entity instance which has the machine status information to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if the machine status to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
		//#CM47465
		//-------------------------------------------------
		//#CM47466
		// variable define
		//#CM47467
		//-------------------------------------------------
		String fmtSQL = "DELETE FROM " + wTableName +
						" WHERE" +
						" MACHINETYPE = {1} AND MACHINENUMBER = {2} AND CONTROLLERNUMBER = {5}" ;

		As21MachineState tgtMS = null ;
		//#CM47468
		//-------------------------------------------------
		//#CM47469
		// process
		//#CM47470
		//-------------------------------------------------
		if (tgt instanceof As21MachineState)
		{
			tgtMS = (As21MachineState)tgt ;
		}
		else
		{
			//#CM47471
			//<en> This is not the instance of As21MachineState.</en>
			RmiMsgLogClient.write(6126016, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
			throw (new ReadWriteException("6126016")) ;
		}
		Object [] fmtObj = setToMachine(tgtMS);
		//#CM47472
		// create actual sql
		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;

		try
		{
			//#CM47473
			// execute the sql (super)
			executeSQL(sqlstring, false) ;
		}
		catch (NotFoundException nfe)
		{
			//#CM47474
			//<en> This should not happen;</en>
			nfe.printStackTrace() ;
			//#CM47475
			//<en> There is no corresponding mathine.</en>
			throw new ReadWriteException("6124003") ;
		}
		catch (DataExistsException nfe)
		{
			//#CM47476
			//<en> This should not happen;</en>
			nfe.printStackTrace() ;
			//#CM47477
			//<en> Could not process; the machine already exists.</en>
			throw new ReadWriteException("6126017") ;
		}
	}
	//#CM47478
	/**<en>
	 * Delete from database the information of machine status passed through parameter.
	 * @param key :entity instance which has the machine status information to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if the machine status to delete cannot be found.
	 </en>*/
	public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException
	{
		ToolEntity[] tgt = find(key) ;
		for (int i = 0; i < tgt.length; i++)
		{
			drop(tgt[i]) ;
		}
	}

	//#CM47479
	// Package methods -----------------------------------------------

	//#CM47480
	// Protected methods ---------------------------------------------

	protected void closeStatement(Statement stmt)
	{
		try
		{
			stmt.close() ;
		}
		catch (Exception e)
		{
		}
	}
	//#CM47481
	/**<en>
	 * Recieve and execute the SQL string.
	 * @param sqlstr :SQL string to execute
	 * @param query  :true if it is the query
	 * @return :returns <code>ResultSet</code> of results, or returns null for all other cases
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if the execution resulted no data (0).
	 * @throws DataExistsException :Notifies if the unique restriction is broken at Insert.
	 </en>*/
	protected ResultSet executeSQL(String sqlstr, boolean query) throws ReadWriteException, NotFoundException, DataExistsException
	{
		ResultSet rset = null ;
		try
		{
			//#CM47482
			//<en> A scroll cursor is required in order to see the line 0 by first() of query.</en>
			wStatement = wConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
										, ResultSet.CONCUR_READ_ONLY
									) ;
			if (query)
			{
				//#CM47483
				// SELECT
				rset = wStatement.executeQuery(sqlstr);
				//#CM47484
				// check no rows returned
//#CM47485
/*
				if (!rset.first())
				{
					wStatement.close();
					throw (new NotFoundException()) ;
				}
				rset.beforeFirst() ;
*/
			}
			else
			{
				//#CM47486
				// INSERT,UPDATE,DELETE
				int rrows = wStatement.executeUpdate(sqlstr);
				if (rrows == 0)
				{
					RmiMsgLogClient.write(6126018, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
					throw (new NotFoundException("6123001")) ;
				}
				wStatement.close();
			}
		}
		catch (SQLException se)
		{
			//#CM47487
			// Logging
			se.printStackTrace();
			//#CM47488
			// Getting message
//#CM47489
//			String msg = null ; // !!! getting from resource
			
			if (se.getErrorCode() == ToolParam.DATAEXISTS)
			{
				//#CM47490
				//<en> Could not process; the machine already exists.</en>
				RmiMsgLogClient.write(6126017, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
				throw (new DataExistsException("6126017")) ;
			}
			else
			{
				//#CM47491
				//<en> To get the message number </en>
				RmiMsgLogClient.write(6126018, LogMessage.F_ERROR, "ToolAs21MachineStateHandler", null);
				throw (new ReadWriteException("6123001")) ;
			}
		}
		return rset ;
	}

	//#CM47492
	// Private methods -----------------------------------------------
	private Object[] setToMachine(As21MachineState mac)
	{
		Vector vec = new Vector();

		vec.addElement(DBFormat.format(mac.getStationNumber()));
		vec.addElement(new Integer(mac.getType()));
		vec.addElement(new Integer(mac.getNumber()));
		vec.addElement(new Integer(mac.getState()));
		vec.addElement(DBFormat.format(mac.getErrorCode()));
		vec.addElement(new Integer(mac.getControllerNumber()));

		Object[] obj = new Object[vec.size()];
		vec.copyInto(obj);
		return obj;
	}

	//#CM47493
	/**<en>
	 * Retrieve each item from <code>ResultSet</code> and generate the <code>As21MachineState</code>
	 * instance .
	 * @throws ReadWriteException :Notifies if an error occurred in conneciton with database.
	 * @throws NotFoundException  :Notifies if there was no information in ResultSet.
	 </en>*/
	private As21MachineState[] makeAs21MachineState(ResultSet rset) throws ReadWriteException
	{
		//#CM47494
		// temporary store for As21MachineState instances
		Vector tmpMSVect = new Vector(10) ;
		As21MachineState tmpMS = null ;
		//#CM47495
		// data get from resultset and make new As21MachineState instance
		try
		{
			while(rset.next())
			{
				//#CM47496
				// append new As21MachineState instance to Vector
				tmpMS = new As21MachineState(0,0) ;
				tmpMS.setStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER"))) ;
				tmpMS.setType(rset.getInt("MACHINETYPE")) ;
				tmpMS.setNumber(rset.getInt("MACHINENUMBER")) ;
				tmpMS.setState(rset.getInt("STATE")) ;
				tmpMS.setErrorCode(DBFormat.replace(rset.getString("ERRORCODE"))) ;
				tmpMS.setControllerNumber(rset.getInt("CONTROLLERNUMBER")) ;
				tmpMS.setHandler(this) ;
				tmpMSVect.add(tmpMS) ;
			}
		}
		catch (SQLException se)
		{
			//#CM47497
			//<en>6126001 = Database error occured. {0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, se), "As21MachineStateHandler" ) ;
			//#CM47498
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + ""));
		}
		finally
		{
			closeStatement(wStatement) ;
		}

		As21MachineState[] array = new As21MachineState[tmpMSVect.size()];
		tmpMSVect.copyInto(array);

		return array ;
	}

}
//#CM47499
//end of class

