// $Id: ToolBankHandler.java,v 1.2 2006/10/30 02:17:20 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47500
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
import jp.co.daifuku.wms.asrs.tool.location.Bank;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM47501
/**<en>
 * This class operates the Bank information (Bank) group.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:20 $
 * @author  $Author: suresh $
 </en>*/
public class ToolBankHandler implements ToolDatabaseHandler
{
	//#CM47502
	// Class fields --------------------------------------------------

	//#CM47503
	// Class variables -----------------------------------------------
	//#CM47504
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_BANKSELECT";
	//#CM47505
	/**<en>
	 * Connection instance to connect with database
	 * Transaction control is not conducted in this class.
	 </en>*/
	protected Connection wConn ;

	//#CM47506
	// Class method --------------------------------------------------
	//#CM47507
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:20 $") ;
	}

	//#CM47508
	// Constructors --------------------------------------------------
	//#CM47509
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolBankHandler(Connection conn)
	{
		setConnection(conn) ;
	}
	//#CM47510
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn    :Connection to connect with database
	 * @param tablename :name of the table
	 </en>*/
	public ToolBankHandler(Connection conn, String tablename)
	{
		setConnection(conn) ;
		wTableName = tablename;
	}

	//#CM47511
	// Public methods ------------------------------------------------
	//#CM47512
	/**<en>
	 * Set the <code>Connection</code> to connect with database.
	 * @param conn :Connection to set
	 </en>*/
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}

	//#CM47513
	/**<en>
	 * Retrieve the <code>Connection</code> to connect with database.
	 * @return :<code>Connection</code> currently preserved
	 </en>*/
	public Connection getConnection()
	{
		return(wConn) ;
	}
	//#CM47514
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
			//#CM47515
			// execute the sql
			rset = stmt.executeQuery(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM47516
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47517
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
				//#CM47518
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM47519
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}

	}

	//#CM47520
	/**<en>
	 * Search and retrieve Bank.
	 * @param key :Key for search
	 * @return :the array of Bank instance generated based on the search result
	 * @throws ReadWriteException :Notifies if it failed to loadi from the storate system.
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[]  fmtObj = new Object[2] ;
		Bank[] bankArray = null;

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
			bankArray = convertBank(rset) ;
		}
		catch (SQLException e)
		{
			//#CM47521
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
			//#CM47522
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
				//#CM47523
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
				//#CM47524
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + wTableName));
			}
		}
		return bankArray;
	}

	//#CM47525
	/**<en>
	 * Return the number of data that meets the conditions of specified Key.
	 * @param key :Key for search
	 * @return    :number of search result
	 * @throws ReadWriteException :Notifies if it failed to loadi from the storate system.
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
			//#CM47526
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
			//#CM47527
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
				//#CM47528
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolZoneHandler" ) ;
				//#CM47529
				//<en>Throw the ReadWriteException here.</en>
				throw new ReadWriteException() ;
			}
		}

		return count ;
	}

	//#CM47530
	/**<en>
	 * Newly create the Bank information in database.
	 * This has not been implemented due to no need of registration.
	 * @param tgt :entity instance which preserves the Bank information to create.
	 * @throws ReadWriteException  :Notifies if it failed to load from the storage system.
	 * @throws DataExistsException :Notifies if the same Bank is already registered in database.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
		Statement stmt  = null;
		ResultSet rset  = null;
		int       ptcnt = 0;
		int       itcnt = 0;
		int       cicnt = 0;

		try
		{
			stmt = wConn.createStatement();
			Bank bank = (Bank)tgt;

			String sql = 	" INSERT INTO  "+ wTableName + " (" +
								" WHSTATIONNUMBER," +
								" AISLESTATIONNUMBER," +
								" NBANK," +
								" PAIRBANK," +
								" SIDE " +
								"  ) VALUES ( {0}, {1}, {2}, {3}, {4} ) ";

			Object [] bankObj = setToBank(bank) ;

			String sqlstring = SimpleFormat.format(sql, bankObj) ;
			stmt.executeUpdate(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM47531
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
			//#CM47532
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "TerminalArea")) ;
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
				//#CM47533
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
				//#CM47534
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "Terminal")) ;
			}
		}
	}
	

	//#CM47535
	/**<en>
	 * Modify the information in database to the entity information passed through parameter.
	 * @param tgt :entity instance which preserves information to modify
	 * @throws ReadWriteException :Notifies if error occured in connection with database.  
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
	}
	//#CM47536
	/**<en>
	 * Modify the information in database. The contens and conditions of the modification
	 * will be obtained by ToolSearchKey.
	 * @param tgt :entity instance which preserves the information to modify
	 * @throws ReadWriteException :Notifies if error occured in connection with database.  
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 * @throws InvalidDefineException :Notifies if contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
	}


	//#CM47537
	/**<en>
	 * Delete from database the information of entity instance passed through parameter.
	 * @param tgt :entity instance which preserves the information to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.  
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
	}


	//#CM47538
	/**<en>
	 * Delete from database the information that match the key passed through parameter.
	 * @param key :key for the information to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database.  
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException
	{
	}



	//#CM47539
	// Package methods -----------------------------------------------

	//#CM47540
	// Protected methods ---------------------------------------------


	//#CM47541
	/**<en>
	 * Mapping of result set:
	 * @param rset <CODE>ResultSet</CODE> :result of search
 	 * @throws ReadWriteException :Notifies the exception itself that occurred in connection with database.
	 </en>*/
	protected Bank[] convertBank(ResultSet rset) throws ReadWriteException
	{
		Vector vec = new Vector();
		Bank[] bankArray = null;

		try
		{
			while (rset.next())
			{
				vec.addElement (new Bank(
				                           DBFormat.replace(rset.getString("WHSTATIONNUMBER")),
				                           DBFormat.replace(rset.getString("AISLESTATIONNUMBER")),
				                           rset.getInt("NBANK"),
				                           rset.getInt("PAIRBANK"),
				                           rset.getInt("SIDE")
                                          )
                                );
			}

			bankArray = new Bank[vec.size()];
			vec.copyInto(bankArray);
		}
		catch(SQLException e)
		{
			//#CM47542
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "BankHandler" ) ;
			//#CM47543
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "Bank")) ;
		}
		return bankArray;
	}

	//#CM47544
	// Private methods -----------------------------------------------
	//#CM47545
	/**<en>
	 * Based on the contents of retrieved cust instance, it generates the string array for 
	 * DML string to execute in CUSTOMER table.
	 * @param  cust :Customer instance to edit
	 * @return :string array to execute SQL
	 </en>*/
	private Object[] setToBank(Bank bank)
	{
		Vector vec = new Vector();

		vec.addElement(DBFormat.format(bank.getWareHouseStationNumber()));
		vec.addElement(DBFormat.format(bank.getAisleStationNumber()));
		vec.addElement(new Integer(bank.getBank()));
		vec.addElement(new Integer(bank.getPairBank()));
		vec.addElement(new Integer(bank.getSide()));

		Object[] obj = new Object[vec.size()];
		vec.copyInto(obj);
		return obj;
	}
}
//#CM47546
//end of class

