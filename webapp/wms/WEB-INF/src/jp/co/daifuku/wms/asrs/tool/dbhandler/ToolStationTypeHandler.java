//#CM48812
//$Id: ToolStationTypeHandler.java,v 1.2 2006/10/30 02:17:12 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48813
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
import jp.co.daifuku.wms.asrs.tool.location.StationType;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM48814
/**<en>
 * This class is used to retrieve/store the <code>StationType</code> class from/to database.
 * As for the subclass of <code>StationType</code>, Handler will be requried respectively.
 * Please use <code>StationFactory</code> to retrieve <code>StationType</code> and the subclasses.
 * As <code>getHandler</code> method has been prepared for <code>StationType</code> and the subclasses,
 * in case the Handler to support is unknown, retrieve one by using <code>getHandler</code> mwthod.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/09</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:12 $
 * @author  $Author: suresh $
 </en>*/
public class ToolStationTypeHandler implements ToolDatabaseHandler
{

	//#CM48815
	// Class fields --------------------------------------------------
	
	//#CM48816
	// Class variables -----------------------------------------------
	//#CM48817
	/**<en> name of the table </en>*/

	private String wTableName = "TEMP_STATIONTYPE";

	//#CM48818
	/**<en>
	 * Connection instance to connect with database
	 * Transaction control is not conducted in this class.
	 </en>*/
	protected Connection wConn ;

	//#CM48819
	/**<en>
	 * Variables which control the statements.
	 </en>*/
	protected Statement wStatement = null ;

	//#CM48820
	// Class method --------------------------------------------------
	//#CM48821
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:17:12 $") ;
	}

	//#CM48822
	// Constructors --------------------------------------------------
	//#CM48823
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection to connect with database
	 </en>*/
	public ToolStationTypeHandler(Connection conn)
	{
		setConnection(conn) ;
	}
	//#CM48824
	/**<en>
	 * Generate instance by specifying <code>Connection</code> to connect with database.
	 * @param conn :Connection connect with database
	 * @param tablename :name of the table
	 </en>*/
	public ToolStationTypeHandler(Connection conn, String tablename)
	{
		setConnection(conn) ;
		wTableName = tablename;
	}

	//#CM48825
	// Public methods ------------------------------------------------

	//#CM48826
	/**<en>
	 * Set the <code>Connection</code> to connect with database.
	 * @param conn :Connection to set
	 </en>*/
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}

	//#CM48827
	/**<en>
	 * Retrieve the <code>Connection</code> to connect with database.
	 * @return :<code>Connection</code> currently preserved
	 </en>*/
	public Connection getConnection()
	{
		return(wConn) ;
	}

	//#CM48828
	/**<en>
	 * Search the station type.  <code>StationSearchKey</code> should be used for a search key.
	 * @param key :Key for the search
	 * @return :the array of the created object
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	public ToolEntity[] find(ToolSearchKey key) throws ReadWriteException
	{
		//#CM48829
		//-------------------------------------------------
		//#CM48830
		// variable define
		//#CM48831
		//-------------------------------------------------
		StationType[] fndStationType = null ;
		Object[]  fmtObj = new Object[2] ;

		//#CM48832
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
			//#CM48833
			//<en> This should not occur;</en>

			nfe.printStackTrace() ;
			throw (new ReadWriteException(nfe.getMessage())) ;
		}
		catch (DataExistsException dee)
		{
			//#CM48834
			//<en> This should not occur;</en>

			dee.printStackTrace() ;
			throw (new ReadWriteException(dee.getMessage())) ;
		}

		//#CM48835
		// make StationType instances from resultset
		//#CM48836
		// !!! makeStationType() is private method of this.
		fndStationType = makeStationType(rset) ;

		return fndStationType;
	}

	//#CM48837
	/**<en>
	 * Search in database the information based on parameter and return the number of results
	 * (number of StationType data).
	 * @param key :Key for search
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
			//#CM48838
			//<en>6126001 = Database error occured. {0}</en>

			RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
			//#CM48839
			//<en>Here, the ReadWriteException will be thrown with an error message.</en>

			throw (new ReadWriteException("6126013" + wDelim + "StationType")) ;
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
				//#CM48840
				//<en>6126001 = Database error occured. {0}</en>

				RmiMsgLogClient.write( new TraceHandler(6126001, e), this.getClass().getName() ) ;
				//#CM48841
				//<en>Here, the ReadWriteException will be thrown with an error message.</en>

				throw (new ReadWriteException("6126013" + wDelim + "StationType")) ;
			}
		}

		return count ;
	}

	//#CM48842
	/**<en>
	 * Create the new information in database.
	 * @param tgt :entity instance which has the information to create
	 * @throws ReadWriteException  :Notifies if error occured in connection with database. 
	 * @throws DataExistsException :Notifies if database already has the same data registered.
	 </en>*/
	public void create(ToolEntity tgt) throws ReadWriteException, DataExistsException
	{
	}

	//#CM48843
	/**<en>
	 * Modify the information in database.
	 * @param tgt :entity instance which has the information to modify
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if data to modify cannot be found.
	 </en>*/
	public void modify(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
	}

	//#CM48844
	/**<en>
	 * Modify the shelf information in database. The contents and conditions of the modificaiton will be 
	 * obtained by ToolAlterKey.
	 * @param  key :ToolAlterKey instance which preserves the contents and conditions of the modification
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies when data to updata cannot be found in database.
	 * @throws InvalidDefineException :Notifies if the contents of update has not been set.
	 </en>*/
	public void modify(ToolAlterKey key) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
	}

	//#CM48845
	/**<en>
	 * Delete from database the information of entity instance which has been passed through parameter.
	 * @param tgt :entity instance which has the informtion to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolEntity tgt) throws ReadWriteException, NotFoundException
	{
	}

	//#CM48846
	/**<en>
	 * Delete from database the information of entity instance which has been passed through parameter.
	 * @param key :key of the data to delete
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 </en>*/
	public void drop(ToolSearchKey key) throws ReadWriteException, NotFoundException
	{
	}

	//#CM48847
	// Package methods -----------------------------------------------

	//#CM48848
	// Protected methods ---------------------------------------------
	//#CM48849
	/**<en>
	 * Close the wStatement which is the instance variable.
	 * It is necessary that the cursor generated by executeSQL method should be closed.
	 </en>*/
	protected void closeStatement()
	{
		try
		{
			if (wStatement != null) wStatement.close() ;
			wStatement = null ;
		}
		catch (SQLException se)
		{
			//#CM48850
			//<en>6126001 = Database error occured. {0}</en>

			RmiMsgLogClient.write( new TraceHandler(6126001, se), "ToolStationTypeHandler" ) ;
		}
		catch (NullPointerException npe)
		{
			//#CM48851
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
			//#CM48852
			//<en>6126007 = Could not close the cursor. Statement=[{0}]</en>

			RmiMsgLogClient.write(6126007, LogMessage.F_ERROR, "ToolStationTypeHandler", tObj) ;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	//#CM48853
	/**<en>
	 * Receive and execute the SQL string.
	 * @param sqlstr :SQL string to execute
	 * @param query :true if it is a query
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
			//#CM48854
			//<en> A scroll cursor will be requried in order to view line 0 by first() of the query.</en>

			wStatement = wConn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE
										, ResultSet.CONCUR_READ_ONLY
									) ;
			if (query)
			{
				//#CM48855
				// SELECT
				rset = wStatement.executeQuery(sqlstr);
			}
			else
			{
				//#CM48856
				// INSERT,UPDATE,DELETE
				int rrows = wStatement.executeUpdate(sqlstr);
				if (rrows == 0)
				{
					//#CM48857
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
				//#CM48858
				//<en>6126008 = Cannot registrate; the identical data already exists.</en>

				RmiMsgLogClient.write(6126008, LogMessage.F_ERROR, "ToolStationTypeHandler", null);
				throw (new DataExistsException("6126008")) ;
			}
			//#CM48859
			//<en>6126001 = Database error occured. {0}</en>

			RmiMsgLogClient.write( new TraceHandler(6126001, se), "ToolStationTypeHandler" ) ;

			//#CM48860
			//<en> Database error occured. </en>

			String msg = "6126001" ; 
			throw (new ReadWriteException(msg)) ;
		}
		return (rset) ;
	}

	//#CM48861
	/**<en>
	 * Retrieve each item from <code>ResultSet</code> and generate the <code>StationType</code> instance.
	 * @throws ReadWriteException :Notifies if error occured in connection with database. 
	 </en>*/
	protected StationType[] makeStationType(ResultSet rset) throws ReadWriteException
	{
		Vector tmpStVect = new Vector(20) ;	// temporary store for StationType instances
		StationType tmpst = null;
		//#CM48862
		// data get from resultset and make new StationType instance
		try
		{
			while (rset.next())
			{

				tmpst = new StationType() ;
				//#CM48863
				//<en>No meaning of instantiation with the class name for TEMP_STATION table. See comment.</en>

				//#CM48864
				//<en> station no.</en>

				tmpst.setStationNumber(DBFormat.replace(rset.getString("STATIONNUMBER"))) ;
				//#CM48865
				//<en> handler class</en>

				tmpst.setHandlerClass(DBFormat.replace(rset.getString("HANDLERCLASS"))) ;

				//#CM48866
				// station handler
				tmpst.setHandler(this) ;

				//#CM48867
				// append new Station instance to Vector
				tmpStVect.add(tmpst) ;
			}
		}
		catch (SQLException e)
		{
			//#CM48868
			//<en>6126001 = Database error occured. {0}</en>

			RmiMsgLogClient.write( new TraceHandler(6126001, e), "ToolStationTypeHandler" ) ;
			throw (new ReadWriteException("6126001" + wDelim + "StationType")) ;
		}
		catch (Exception e)
		{
			if (e instanceof ReadWriteException)
			{
				throw (ReadWriteException)e;
			}
			e.printStackTrace(wPW) ;
			Object[] tObj = new Object[2] ;
			tObj[0] = "StationType" ;
			tObj[1] = wSW.toString() ;
			//#CM48869
			//<en>6126003 = Failed to generate the instance. class name={0} {1}</en>

			RmiMsgLogClient.write(6126003, LogMessage.F_ERROR, "ToolStationTypeHandler", tObj);
			throw (new ReadWriteException("6126003" + wDelim + tObj[0])) ;
		}
		finally
		{
			closeStatement() ;
		}

		//#CM48870
		// move instance from vector to array of StationType
		StationType[] rstarr = new StationType[tmpStVect.size()] ;
		tmpStVect.copyInto(rstarr);

		return (rstarr) ;
	}

	//#CM48871
	// Private methods -----------------------------------------------
}
//#CM48872
//end of class

