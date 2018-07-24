// $Id: ToolTerminalAreaHandler.java,v 1.2 2006/10/30 02:17:11 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48917
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
import jp.co.daifuku.wms.asrs.tool.location.TerminalArea;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM48918
/**<en>
 * This class is used to operate the group of terminal area information.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/02/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:11 $
 * @author  $Author: suresh $
 </en>*/
public class ToolTerminalAreaHandler implements ToolDatabaseHandler
{

	//#CM48919
	// Class fields --------------------------------------------------

	//#CM48920
	// Class variables -----------------------------------------------
	//#CM48921
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_TERMINALAREA";

	//#CM48922
	/**<en>
	 * Connection instance to connect with database
	 * Transaction control is not conducted in this class.
	 </en>*/
	protected Connection wConn ;

	//#CM48923
	// Class method --------------------------------------------------
	//#CM48924
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:11 $") ;
	}

	//#CM48925
	// Constructors --------------------------------------------------
	//#CM48926
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolTerminalAreaHandler(Connection conn)
	{
		setConnection(conn) ;
	}

	//#CM48927
	// Public methods ------------------------------------------------
	//#CM48928
	/**<en>
	 * Delete all data from the table.
	 * @throws ReadWriteException Notifies if error occured in connection with database.
	 </en>*/
	public void truncate() throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		try
		{
			stmt = wConn.createStatement();
			String sqlstring = "TRUNCATE TABLE "+ wTableName ;
			//#CM48929
			// execute the sql
			rset = stmt.executeQuery(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM48930
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48931
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
				//#CM48932
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48933
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}

	}
	//#CM48934
	/**<en>
	 * Set the <code>Connection</code> to connect with database.
	 * @param conn :Connection to set
	 </en>*/
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}

	//#CM48935
	/**<en>
	 * Retrieve the <code>Connection</code> to connect with database.
	 * @return :<code>Connection</code> currently preserved
	 </en>*/
	public Connection getConnection()
	{
		return(wConn) ;
	}

	//#CM48936
	/**<en>
	 * Search and retrienve the TerminalArea.
	 * @param key :Key for teh search
	 * @return :the array of the TerminalArea generated based on the search results
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system.
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
	{
		Statement stmt			= null;
		ResultSet rset			= null;
		TerminalArea[] terminalareaArray  = null;
		Object[] fmtObj = new Object[2] ;
	 	try
	 	{
			stmt = wConn.createStatement();

			String fmtSQL = "SELECT * FROM  "+ wTableName + " {0} {1}";

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
			rset = stmt.executeQuery(sqlstring) ;

			terminalareaArray = convertTerminalArea(rset);
		}
		catch(SQLException e)
		{
			//#CM48937
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
			//#CM48938
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
				//#CM48939
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
				//#CM48940
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "TERMINALAREA")) ;
			}
		}
		return terminalareaArray;
	}

	//#CM48941
	/**<en>
	 * THe process is not implemented.
	 * @param key :Key for search
	 * @return :number of search results
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system.
	 </en>*/
	public int count(ToolSearchKey key) throws ReadWriteException
	{
		return 0;
	}

	//#CM48942
	/**<en>
	 * Create the new TerminalArea information in database.
	 * @param tgt :entity instance which has the TerminalArea information to create
	 * @throws ReadWriteException  :Notifies if it failed to load from the storage system.
	 * @throws DataExistsException :Notifies if identical Customer was already registered in database.
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
			stmt 				= wConn.createStatement();
			TerminalArea    terminalarea  	= (TerminalArea)tgt;

			String sql = 	" INSERT INTO  "+ wTableName + " (" +
								" STATIONNUMBER," +
								" AREAID," +
								" TERMINALNUMBER" +
								"  ) VALUES ( {0}, {1}, {2} ) ";

			Object [] terminalareaObj = setToTerminalArea(terminalarea) ;

			String sqlstring = SimpleFormat.format(sql, terminalareaObj) ;
			stmt.executeUpdate(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM48943
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
			//#CM48944
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
				//#CM48945
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
				//#CM48946
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "Terminal")) ;
			}
		}
	}

	//#CM48947
	/**<en>
	 * Modify the TerminalArea information in database.
	 * @param tgt :entity instance which has the TerminalArea information to modify
	 * @throws NotFoundException  :Notifies if data cannot be found as a result of update.
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system.
	 </en>*/
	public void modify(ToolEntity tgt) throws NotFoundException, ReadWriteException
	{
		Statement stmt        = null;
		int       updateCount = 0;
		try
		{
			//#CM48948
			//<en>Cast the tgt in TerminalArea class.</en>
			TerminalArea        terminalarea  = (TerminalArea)tgt;
	
			stmt = wConn.createStatement();

			String sql = 	" UPDATE  "+ wTableName + " SET" +
								" STATIONNUMBER = {0}," +
								" AREAID = {1}," +
								" TERMINALNUMBER = {2}" +
								" WHERE STATIONNUMBER = {0} ";

			Object [] terminalareaObj = setToTerminalArea(terminalarea) ;

			String sqlstring = SimpleFormat.format(sql, terminalareaObj) ;
			int count = stmt.executeUpdate(sqlstring) ;
			if (count == 0)
			{
				Object[] tObj = new Object[1] ;
				tObj[0] = "TERMINALAREA";
				RmiMsgLogClient.write(6126014, LogMessage.F_ERROR, "TerminalAreaHandler", tObj);
				throw new NotFoundException( "6126014" + wDelim + tObj[0] );
			}
		}
		catch(SQLException e)
		{
			//#CM48949
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
			//#CM48950
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "TerminalArea")) ;
		}
		finally
		{
			try
			{
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM48951
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
				//#CM48952
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "TerminalArea")) ;
			}
		}
	}

	//#CM48953
	/**<en>
	 * Modify the TerminalArea in database. Please use modify(ToolEntity) when updating
	 * TerminalArea table.
	 * This method is not implemented.
	 * @param  key :ToolAlterKey instance which preserves the contents and conditions of the modification
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if the data to modify cannot be found.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
	}

	//#CM48954
	/**<en>
	 * Delete the TerminalArea data passed through parameter from database.
	 * @param tgt :entity instance which has the TerminalArea informtion to delete
	 * @throws NotFoundException  :Notifies if data cannot be found as a search result.
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system.
	 </en>*/
	public void drop(ToolEntity tgt) throws NotFoundException, ReadWriteException
	{
		Statement   stmt         = null;
		ResultSet   rset         = null;

		try
		{
			//#CM48955
			//<en>Cast tgt to TerminalArea class.</en>
			TerminalArea		terminal= (TerminalArea)tgt;
			stmt 				= wConn.createStatement();

			String sql = "DELETE FROM  "+ wTableName + " " +
							" WHERE STATIONNUMBER = {0} AND" +
										 " AREAID = {1} AND" +
								 " TERMINALNUMBER = {2} ";

			Object [] terminalObj = setToTerminalArea(terminal) ;

			String sqlstring = SimpleFormat.format(sql, terminalObj) ;

			int count = stmt.executeUpdate(sqlstring) ;
			if (count == 0)
			{
				Object[] tObj = new Object[1] ;
				tObj[0] = "TERMINALAREA";
				RmiMsgLogClient.write(6126015, LogMessage.F_ERROR, "TerminalAreaHandler", tObj);
				throw new NotFoundException( "6126015" + wDelim + tObj[0] );
			}
		}
		catch(SQLException e)
		{
			//#CM48956
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
			//#CM48957
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126020")) ;
		}
		finally
		{
			try
			{
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM48958
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
				//#CM48959
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "TerminalArea")) ;
			}
		}

	}

	//#CM48960
	/**<en>
	 * Delete from database the information that match the key which was passed through parameter.
	 * @param key :key for the data to delete
	 * @throws NotFoundException  :Notifies if the data cannot be found as a search result.
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system.
	 </en>*/
	public void drop(ToolSearchKey key) throws ReadWriteException
	{
		Statement stmt			= null;
		Object[] fmtObj = new Object[2] ;
		try
		{
			stmt = wConn.createStatement();

			String fmtSQL = "DELETE FROM  "+ wTableName + " {0}";

			if (key.ReferenceCondition() != null)
			{
				fmtObj[0] = "WHERE " + key.ReferenceCondition();
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			System.out.println(sqlstring);
			int count = stmt.executeUpdate(sqlstring) ;

		}
		catch(SQLException e)
		{
			//#CM48961
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
			//#CM48962
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "TerminalArea")) ;
		}
		finally
		{
			try
			{
				if (stmt != null) { stmt.close(); }
			}
			catch(SQLException e)
			{
				//#CM48963
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
				//#CM48964
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "TERMINALAREA")) ;
			}
		}
	}

	//#CM48965
	// Package methods -----------------------------------------------

	//#CM48966
	// Protected methods ---------------------------------------------
	//#CM48967
	/**<en>
	 * Mapping of the result set.
	 * @param rset <CODE>ResultSet</CODE> search result
 	 * @throws ReadWriteException :Notifies the exception itself that occurred in connection with database.
	 </en>*/
	protected TerminalArea[] convertTerminalArea(ResultSet rset) throws ReadWriteException
	{
		Vector vec = new Vector();
		TerminalArea[] terminalAreaArray = null;
		try 
		{
			while (rset.next())
			{
				vec.addElement (new TerminalArea(  
				                           DBFormat.replace(rset.getString("STATIONNUMBER")),
										   rset.getInt("AREAID"),
				                           DBFormat.replace(rset.getString("TERMINALNUMBER"))
                                          ));
			}
			terminalAreaArray = new TerminalArea[vec.size()];
			vec.copyInto(terminalAreaArray);
		}
		catch(SQLException e)
		{
			//#CM48968
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
			//#CM48969
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "TerminalArea")) ;
		}
		return terminalAreaArray;
	}

	//#CM48970
	// Private methods -----------------------------------------------
	//#CM48971
	/**<en>
	 * Based on the contents of TerminalArea isntance retrieved, generate the string array with which 
	 * to run DML string in TERMINALAREA table.
	 * @param  TerminalArea :TerminalArea isntance to edit
	 * @return :string array to run SQL
	 </en>*/
	private Object[] setToTerminalArea(TerminalArea ta)
	{
		Vector vec = new Vector();

		vec.addElement(DBFormat.format(ta.getStationNumber()));
		vec.addElement(DBFormat.format(Integer.toString(ta.getAreaId())));
		vec.addElement(DBFormat.format(ta.getTerminalNumber()));


		Object[] obj = new Object[vec.size()];
		vec.copyInto(obj);
		return obj;
	}

	//#CM48972
	/**<en>
	 * Check whether/not the specified serial numbers exxxist in zone table of database.
	 * @param serialNo  :serial no.
	 * @return     :whether/not the identical serial no. exists
	 </en>*/
//#CM48973
/*	private boolean isTerminalAreaTable(int serialNo) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[]  fmtObj = new Object[2] ;
		int count = 0;
		String fmtSQL = "SELECT COUNT(*) COUNT FROM  "+ wTableName + " WHERE SERIALNUMBER = {0} ";

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
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			//<en>6126013 = Failed to search {0}. Please refer ot the log.</en>
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
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				//<en>6126013 = Failed to search {0}. Please refer to the log.</en>
				throw (new ReadWriteException("6126013" + wDelim + "TerminalArea")) ;
			}
		}
		if (count == 0)
			return false;
		else
			return true;
	}*/
/*	private boolean isTerminalAreaTable(int serialNo) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		Object[]  fmtObj = new Object[2] ;
		int count = 0;
		String fmtSQL = "SELECT COUNT(*) COUNT FROM  "+ wTableName + " WHERE SERIALNUMBER = {0} ";

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
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			//<en>6126013 = Failed to search {0}. Please refer ot the log.</en>
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
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "TerminalAreaHandler" ) ;
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				//<en>6126013 = Failed to search {0}. Please refer to the log.</en>
				throw (new ReadWriteException("6126013" + wDelim + "TerminalArea")) ;
			}
		}
		if (count == 0)
			return false;
		else
			return true;
	}*/
}
//#CM48974
//end of class

