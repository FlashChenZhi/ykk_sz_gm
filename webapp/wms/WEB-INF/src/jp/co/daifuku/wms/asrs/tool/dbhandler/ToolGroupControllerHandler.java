// $Id: ToolGroupControllerHandler.java,v 1.2 2006/10/30 02:17:18 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47628
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
import jp.co.daifuku.wms.asrs.tool.location.GroupControllerInformation;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM47629
/**<en>
 * This class operates the GroupControllergroup.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:18 $
 * @author  $Author: suresh $
 </en>*/
public class ToolGroupControllerHandler implements ToolDatabaseHandler
{
	//#CM47630
	// Class fields --------------------------------------------------

	public static final String GROUPCONTROLLER_HANDLE = "jp.co.daifuku.wms.base.dbhandler.GroupControllerHandler";

	//#CM47631
	// Class variables -----------------------------------------------
	//#CM47632
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_GROUPCONTROLLER";
	
	//#CM47633
	/**<en>
	 * Connection instance to connect with database
	 * Transaction control is not conducted in this class.
	 </en>*/
	protected Connection wConn ;

	//#CM47634
	// Class method --------------------------------------------------
	//#CM47635
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:18 $") ;
	}



	//#CM47636
	// Constructors --------------------------------------------------
	//#CM47637
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolGroupControllerHandler(Connection conn)
	{
		setConnection(conn) ;
	}
	//#CM47638
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 * @param tableName :name of the table
	 </en>*/
	public ToolGroupControllerHandler(Connection conn, String tableName)
	{
		this(conn);
		wTableName = tableName;
	}


	//#CM47639
	// Public methods ------------------------------------------------
	//#CM47640
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
			//#CM47641
			// execute the sql
			rset = stmt.executeQuery(sqlstring) ;
		}
		catch(SQLException e)
		{
			//#CM47642
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM47643
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
				//#CM47644
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM47645
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException()) ;
			}
		}

	}
	//#CM47646
	/**<en>
	 * Set the <code>Connection</code> to connect with database.
	 * @param conn :Connection to set
	 </en>*/
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}

	//#CM47647
	/**<en>
	 * Retrieve the <code>Connection</code> to connect with database.
	 * @return :<code>Connection</code> currently preserved
	 </en>*/
	public Connection getConnection()
	{
		return(wConn) ;
	}

	//#CM47648
	/**<en>
	 * Search and retireve the GroupController.
	 * @param key :Key for search
	 * @return :the array of GroupController instance generated based on the search result
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
	{
		Statement stmt			= null;
		ResultSet rset			= null;
		GroupControllerInformation[]    groupcontrollerArray = null;
		Object[] fmtObj = new Object[2] ;

	 	try
	 	{
			stmt = wConn.createStatement();

			String fmtSQL = "SELECT * FROM "+ wTableName + "{0} {1}";

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
			rset = stmt.executeQuery(sqlstring) ;

			groupcontrollerArray = convertGroupController(rset);

		}
		catch(SQLException e)
		{
			//#CM47649
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
			//#CM47650
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "GroupController")) ;
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
				//#CM47651
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
				//#CM47652
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "GroupController")) ;
			}
		}
		return groupcontrollerArray;
	}

	//#CM47653
	/**<en>
	 * Return the number of data that meets the conditions of specifeid key.
	 * @param key :Key for search
	 * @return :number of search results
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
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

			String fmtSQL = "SELECT COUNT(1) COUNT FROM "+ wTableName + " {0}" ;

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
			//#CM47654
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
			//#CM47655
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "GroupController")) ;
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
				//#CM47656
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
				//#CM47657
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "GroupController")) ;
			}
		}

		return count ;
	}

	//#CM47658
	/**<en>
	 * Newly create the GroupController information in database.
	 * @param tgt :entity instance which preserves the GroupController information to create
	 * @throws ReadWriteException  :Notifies if it failed to load from the storage system. 
	 * @throws DataExistsException :Notifies if the same GroupControllerNumber is already registered
	 * in database.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
		Statement stmt  = null;
		ResultSet rset  = null;
		int ptcnt = 0;
		int itcnt = 0;
		int cicnt = 0;
		try
		{
			stmt = wConn.createStatement();
			GroupControllerInformation groupcontroller = (GroupControllerInformation)tgt;
			String sql = " INSERT INTO "+ wTableName + " (" +
							"CONTROLLERNUMBER" 	+
							", STATUS"			+
							", IPADDRESS"		+
							", PORT" 			+
							") values (" +
							"{0}, {1}, {2}, {3}" +
						")" ;
									
			String sqlstring = null;
			Object [] groupcontrollerObj = setToGroupController(groupcontroller) ;
			
			//#CM47659
			//<en> If the data exists in GROUPCONTROLLER table.</en>
			if (isGroupControllerTable(wConn, groupcontroller)) 
			{
				//#CM47660
				//<en> Registration is not possible if the data already exists.</en>
				RmiMsgLogClient.write(6126008, LogMessage.F_ERROR, "ToolGroupControllerHandler", null);
				throw (new DataExistsException("6126008"));
			}
			else
			{
				sqlstring = SimpleFormat.format(sql, groupcontrollerObj) ;
				stmt.executeUpdate(sqlstring) ;
			}
		}
		catch(SQLException e)
		{
			//#CM47661
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
			//#CM47662
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126019")) ;
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
				//#CM47663
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
				//#CM47664
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "GroupController")) ;
			}
		}
	}

	//#CM47665
	/**<en>
	 * Modify the GroupController information in database.
	 * @param tgt :entity instance which preserves the GroupController information to create
	 * @throws NotFoundException  :Notifies if information cannot be found as a result of update.
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
	 </en>*/
	public void modify(ToolEntity tgt) throws NotFoundException, ReadWriteException
	{

	}

	//#CM47666
	/**<en>
	 * Modify the GroupController information in database. The contents and conditions of the modification
	 * will be obtained by ToolAlterKey.
	 * @param  key :AlterKey isntance which preserves the contents and conditions of modification 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{

	}

	//#CM47667
	/**<en>
	 * Delete from database the GroupController information passed through parameter.
	 * @param tgt :entity instance which preserves the GroupController information to delete
	 * @throws NotFoundException  :Notifies if data cannot be found as a result of search.
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
	 </en>*/
	public void drop(ToolEntity tgt) throws NotFoundException, ReadWriteException
	{
		Statement   stmt         = null;
		ResultSet   rset         = null;

		try
		{
			//#CM47668
			//<en>Cast the tgt to Customer class.</en>
			GroupControllerInformation	groupcontroller= (GroupControllerInformation)tgt;
			stmt 				= wConn.createStatement();

			String sqlgroupcontroller = "DELETE FROM "+ wTableName + " " +
							" WHERE CONTROLLERNUMBER = {0} ";

			String sqlstring = null;

			Object [] groupcontrollernumObj = setToGroupController(groupcontroller) ;

			sqlstring = SimpleFormat.format(sqlgroupcontroller, groupcontrollernumObj) ;
			int count = stmt.executeUpdate(sqlstring) ;
			if (count == 0)
			{
				Object[] tObj = new Object[2] ;
				tObj[0] = "CUSTOMER";
				RmiMsgLogClient.write(6126015, LogMessage.F_ERROR, "ToolGroupControllerHandler", tObj);
				throw (new NotFoundException( "6126015" + wDelim + tObj[0] ));
			}
		}
		catch(SQLException e)
		{
			//#CM47669
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
			//#CM47670
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
				//#CM47671
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
				//#CM47672
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				throw (new ReadWriteException("6126013" + wDelim + "GroupController")) ;
			}
		}

	}

	//#CM47673
	/**<en>
	 * Delete from database the information that match the key passed through parameter.
	 * @param key :key for the information to delete
 	 * @throws NotFoundException  :Notifies if information cannot be found as a result of search.
	 * @throws ReadWriteException :Notifies if it failed to load from the storage system. 
	 </en>*/
	public void drop(ToolSearchKey key) throws NotFoundException, ReadWriteException
	{
		//#CM47674
		//<en> Delete from DB.</en>
		ToolEntity[] tgts = find(key) ;
		for (int i=0; i < tgts.length; i++)
		{
			drop(tgts[i]) ;
		}
	}

	//#CM47675
	// Public methods ------------------------------------------------

	//#CM47676
	// Package methods -----------------------------------------------

	//#CM47677
	// Protected methods ---------------------------------------------

	//#CM47678
	/**<en>
	 * Mapping of result set.
	 * @param rset <CODE>ResultSet</CODE> :search result
 	 * @throws ReadWriteException :Notifies the exception itself that occurred in connection with database.
	 </en>*/
	protected GroupControllerInformation[] convertGroupController(ResultSet rset) throws ReadWriteException
	{
		Vector vec = new Vector();
		GroupControllerInformation[] groupcontrollerArray = null;

		try
		{
			while (rset.next())
			{
				vec.addElement (new GroupControllerInformation(
				                           rset.getInt("controllernumber"),
				                           rset.getInt("status"),
				                           DBFormat.replace(rset.getString("ipaddress")),
				                           rset.getInt("port")
                                          )
                                );
			}

			groupcontrollerArray = new GroupControllerInformation[vec.size()];
			vec.copyInto(groupcontrollerArray);
		}
		catch(SQLException e)
		{
			//#CM47679
			//<en>6126001 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
			//#CM47680
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			throw (new ReadWriteException("6126013" + wDelim + "GroupController")) ;
		}
		return groupcontrollerArray;
	}

	//#CM47681
	// Private methods -----------------------------------------------
	//#CM47682
	/**<en>
	 * Based on the contents of retrieved cust instance, generate the string array to 
	 * execute the DML string in CUSTOMER table.
	 * @param  cust :Customer isntance to edit
	 * @return :string array for SQL execution
	 </en>*/
	private Object[] setToGroupController(GroupControllerInformation groupcontroller)
	{
		Vector vec = new Vector();

		vec.addElement(Integer.toString(groupcontroller.getControllerNumber()));
		vec.addElement(Integer.toString(groupcontroller.getStatus()));
		vec.addElement(DBFormat.format(groupcontroller.getIPAddress()));
		vec.addElement(Integer.toString(groupcontroller.getPort()));

		Object[] obj = new Object[vec.size()];
		vec.copyInto(obj);
		return obj;
	}
	//#CM47683
	/**<en>
	 * Check whether/not the specified groupcontrollernum exists in GROUPCONTROLLER table of database.
	 * @param conn :Connection to connect with database
	 * @param groupcontrollernum  :CUSTOMER to be searched
	 * @return     :whether/not the GROUPCONTROLLER table exists
	 </en>*/
	private boolean isGroupControllerTable(Connection conn, GroupControllerInformation groupcontroller) throws ReadWriteException
	{
		Statement stmt = null;
		ResultSet rset = null;
		int count = 0;
		try
		{
			stmt = conn.createStatement();
			rset = stmt.executeQuery("SELECT COUNT(*) COUNT FROM "+ wTableName + " WHERE CONTROLLERNUMBER = "
											+ groupcontroller.getControllerNumber() + "");
			while(rset.next())
			{
				count = rset.getInt("COUNT");
			}
		}
		catch(SQLException e)
		{
			//#CM47684
			//<en>6126001 = Database error occured.{0}</en>

			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
			//#CM47685
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>
			//#CM47686
			//<en>6126013 = Failed to search {0}. Please refer to the log.</en>

			throw (new ReadWriteException("6126013" + wDelim + "GroupController")) ;
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
				//#CM47687
				//<en>6126001 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolGroupControllerHandler" ) ;
				//#CM47688
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>
				//#CM47689
				//<en>6126013 = Failed to search {0}. Please refer to the log.</en>
				throw (new ReadWriteException("6126013" + wDelim + "GroupController")) ;
			}
		}
		if (count == 0)
			return false;
		else
			return true;
	}
}
//#CM47690
//end of class

