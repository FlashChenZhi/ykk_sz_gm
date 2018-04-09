// $Id: AbstractDBHandler.java,v 1.4 2006/11/16 04:52:05 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.AlterKey;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.entity.AbstractEntity;


//#CM708160
/**
 * <CODE>SAMDBHandler</CODE> class is a super-class to operate the data base (table).
 * <BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Y.Kato</b></td><td><b>New making</b></td></tr>
 *
 * <!-- Change history -->
 * <tr><td nowrap>2004/10/28</td><td nowrap>Y.Kato created this file.</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision, $Date: 2006/11/16 04:52:05 $
 * @author  Y.Kato
 * @author  Last commit: $Author: suresh $
 */
public abstract class AbstractDBHandler
		implements DatabaseHandler
{
	//#CM708161
	// Item name which sets updated date and hour automatically when updating or registering.
	private static String AUTOUPDATE = "LAST_UPDATE_DATE";

	//#CM708162
	// Item name which sets the registration day following automatically when registering.
	private static String AUTOINSERTDATE = "REGIST_DATE";

	//#CM708163
	//Item name which sets updated date and hour of palette automatically while updating or registering.
	private static String AUTOREFIXDATE = "REFIXDATE";
	//------------------------------------------------------------
	//#CM708164
	// properties (prefix 'p_')
	//#CM708165
	//------------------------------------------------------------
	//#CM708166
	/**
	 * Connection instance for data base connection. <BR>
	 * Do not do the transaction management in this class.
	 */
	private Connection p_Conn ;

	//#CM708167
	/**
	 * Reference table name
	 */
	private String p_TableName = null ;

	//#CM708168
	// Class variables -----------------------------------------------

	//#CM708169
	// Constructors --------------------------------------------------
	//#CM708170
	/**
	 * constructor comment.
	 *
	 * @param conn Connection
	 * @param tbName String
	 */
	public AbstractDBHandler(Connection conn, String tbName)
	{
		setTableName(tbName) ;
		setConnection(conn) ;
	}

	//#CM708171
	// Class method --------------------------------------------------
	//#CM708172
	/**
	 * Return Revision of this class.
	 * @return Revision character string.
	 */
	public static String getVersion()
	{
		return "$Id: AbstractDBHandler.java,v 1.4 2006/11/16 04:52:05 suresh Exp $" ;
	}

	//#CM708173
	// Public methods ------------------------------------------------
	//#CM708174
	/**
	 * Set <code>Connection</code> for the data base connection.
	 * @param conn Connection to be set
	 */
	public void setConnection(Connection conn)
	{
		p_Conn = conn ;
	}

	//#CM708175
	/**
	 * Acquire <code>Connection</code> for the database connection.
	 * @return <code>Connection</code> being maintained
	 */
	public Connection getConnection()
	{
		return (p_Conn) ;
	}

	//#CM708176
	/**
	 * Set <code>TableName</code> for the database connection
	 * @param tblName Table name to be set
	 */
	public void setTableName(String tblName)
	{
		p_TableName = tblName ;
	}

	//#CM708177
	/**
	 * Acquire <code>TableName</code> for the database connection
	 * @return <code>TableName</code> being maintained
	 */
	public String getTableName()
	{
		return p_TableName ;
	}

	//#CM708178
	/**
	 * Retrieve information on the data base based on the parameter, and return the object.
	 * @param key Key for retrieval
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public Entity[] find(SearchKey key)
			throws ReadWriteException
	{
		return find(key, 0) ;
	}

	//#CM708179
	/**
	 * Lock the table based on the parameter.
	 * @param key Key for retrieval
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public boolean lock(SearchKey key)
			throws ReadWriteException
	{
		Statement stmt = null ;
		Object[] fmtObj = new Object[3] ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT {0} FROM " + p_TableName + " {1} {2} FOR UPDATE " ;
			//#CM708180
			// Edit the acquisition condition.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			}
			else
			{
				//#CM708181
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing.
				fmtObj[0] = " * " ;
			}
			//#CM708182
			// Edit the search condition.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}
			//#CM708183
			// Edit the order of reading.
			if (key.getSortCondition() != null)
			{
				fmtObj[2] = " ORDER BY " + key.getSortCondition() ;
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " FINDFORUPDATE SQL[" + sqlstring + "]") ;
			return stmt.execute(sqlstring) ;
		}
		catch (SQLException e)
		{
			//#CM708184
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708185
			// Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708186
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708187
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
	}

	//#CM708188
	/**
	 * Lock the table based on the parameter.
	 * Lock pertinent information. When failing in the acquisition of the lock while other processing is locking
	 * Notify LockTimeOutException.
	 * @param key Key for retrieval
	 * @param second Time of lock waiting(second)
	 * @return Whether it was lockable or not
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public boolean lock(SearchKey key, int second)
			throws ReadWriteException, LockTimeOutException
	{
		Statement stmt = null ;
		Object[] fmtObj = new Object[4] ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT {0} FROM " + p_TableName + " {1} {2} FOR UPDATE WAIT {3}" ;
			//#CM708189
			// Edit the acquisition condition.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			}
			else
			{
				//#CM708190
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing.
				fmtObj[0] = " * " ;
			}
			//#CM708191
			// Edit the search condition.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}
			//#CM708192
			// Edit the order of reading.
			if (key.getSortCondition() != null)
			{
				fmtObj[2] = " ORDER BY " + key.getSortCondition() ;
			}
			fmtObj[3] = Integer.toString(second);
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " FINDFORUPDATE SQL[" + sqlstring + "]") ;
			return stmt.execute(sqlstring) ;
		}
		catch (SQLException e)
		{
			//#CM708193
			// Because the error code 54 and 30006 are the error codes only for Oracle
			//#CM708194
			// Review it when you use other data bases.
			//#CM708195
			// Moreover, because it becomes nowait treatment when 0 is specified by the wait specification
			//#CM708196
			// Check error code 54 of TimeOut when nowoait is specified.
			//#CM708197
			// When specification does TimeOut in the cases of except 0, 30006 is returned.
			if (e.getErrorCode() == 54)
			{
				throw new LockTimeOutException("SELECT FOR UPDATE TABLE " + p_TableName);
			}
			else if (e.getErrorCode() == 30006)
			{
				throw new LockTimeOutException("SELECT FOR UPDATE TABLE " + p_TableName);
			}
			//#CM708198
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708199
			// Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708200
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708201
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
	}

	//#CM708202
	/**
	 * Lock the table based on the parameter.
	 * Lock pertinent information. When failing in the acquisition of the lock while other processing is locking
	 * Notify LockTimeOutException.
	 * @param key Key for retrieval
	 * @param second Time of lock waiting(second)
	 * @return Whether it was lockable or not
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public boolean lockNowait(SearchKey key)
			throws ReadWriteException, LockTimeOutException
	{
		Statement stmt = null ;
		Object[] fmtObj = new Object[3] ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT {0} FROM " + p_TableName + " {1} {2} FOR UPDATE NOWAIT" ;
			//#CM708203
			// Edit the acquisition condition.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			}
			else
			{
				//#CM708204
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing.
				fmtObj[0] = " * " ;
			}
			//#CM708205
			// Edit the search condition.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}
			//#CM708206
			// Edit the order of reading.
			if (key.getSortCondition() != null)
			{
				fmtObj[2] = " ORDER BY " + key.getSortCondition() ;
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " FINDFORUPDATE SQL[" + sqlstring + "]") ;
			return stmt.execute(sqlstring) ;
		}
		catch (SQLException e)
		{
			//#CM708207
			// Because error code 54 is an error code only for Oracle
			//#CM708208
			// Review it when you use other data bases.
			if (e.getErrorCode() == 54)
			{
				throw new LockTimeOutException("SELECT NOWAIT TABLE " + p_TableName);
			}
			//#CM708209
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708210
			// Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708211
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708212
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
	}

	//#CM708213
	/**
	 * Retrieve information on the data base based on the parameter, and return the object.
	 * @param key Key for retrieval
	 * @param recMax Number of acquisition record upper bounds
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public Entity[] find(SearchKey key, int recMax)
			throws ReadWriteException
	{
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[4] ;
		Entity[] entity = null ;

		try
		{
			stmt = p_Conn.createStatement() ;

			String fmtSQL = "SELECT {0} FROM " + p_TableName + " {1} {2} {3}" ;

			//#CM708214
			// Edit the acquisition condition.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			}
			else
			{
				//#CM708215
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing.
				fmtObj[0] = " * " ;
			}

			//#CM708216
			// Edit the search condition.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}

			//#CM708217
			// Edit the consolidating condition.
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition() ;
			}

			//#CM708218
			// Edit the order of reading.
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = " ORDER BY " + key.getSortCondition() ;
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " FIND SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			entity = convertEntities(rset, key, recMax) ;
		}
		catch (SQLException e)
		{
			//#CM708219
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708220
			// Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708221
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708222
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
		return entity ;
	}

	//#CM708223
	/**
	 * Retrieve information on the data base based on the parameter, and return the object.
	 * Retrieval information must be Primary (Only one). NoPrimaryException is notified when existing by the plural.
	 * @param key Key for retrieval
	 *
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws NoPrimaryException
	 */
	public Entity findPrimary(SearchKey key)
			throws ReadWriteException,
				NoPrimaryException
	{
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[4] ;
		Entity entity[] = null ;

		try
		{
			//#CM708224
			// Acquire the number of cases of object information in the search condition.
			int rCount = count(key) ;
			if (rCount == 0)
			{
				//#CM708225
				// Return null when pertinent data is not found.
				return null ;
			}
			if (rCount > 1)
			{
				//#CM708226
				//6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, this.getClass().getName(), null) ;
				//#CM708227
				//Here, throw ReadWriteException with the error message.
				throw (new NoPrimaryException("6006002" + wDelim + p_TableName)) ;
			}
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT {0} " + "FROM " + p_TableName + " {1} {2} {3}" ;
			//#CM708228
			// Edit the acquisition condition.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			}
			else
			{
				//#CM708229
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing.
				fmtObj[0] = " * " ;
			}
			//#CM708230
			// Edit the search condition.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}
			//#CM708231
			// Edit the consolidating condition.
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition() ;
			}
			//#CM708232
			// Edit the order of reading.
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = " ORDER BY " + key.getSortCondition() ;
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " FINDPRINMARY SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			entity = convertEntities(rset, key, 0) ;
			//#CM708233
			/* 2006/06/21 v2.6.0 START Y.Okamura
			 * Data is processed at the other end after the case qty check is done.
			 * The acquisition result of the result set : because there might not be check in matching case qty
			 * Here, it is assumed that the case qty check is done again.  */
			if (entity == null || entity.length == 0)
			{
				//#CM708234
				// Return null when pertinent data is not found.
				return null ;
			}
			if (entity.length > 1)
			{
				//#CM708235
				//6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, this.getClass().getName(), null) ;
				//#CM708236
				//Here, throw ReadWriteException with the error message.
				throw (new NoPrimaryException("6006002" + wDelim + p_TableName)) ;
			}
			//#CM708237
			/* 2006/06/21 END */

		}
		catch (SQLException e)
		{
			//#CM708238
			//6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708239
			//Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708240
				//6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708241
				//Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
		return entity[0] ;
	}

	//#CM708242
	/**
	 * Retrieve information on the data base based on the parameter, and return the object.
	 * Lock acquired information.  * @param key Key for retrieval
	 * @param key
	 *
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public Entity[] findForUpdate(SearchKey key)
			throws ReadWriteException
	{
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[3] ;
		Entity[] entity = null ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT {0} FROM " + p_TableName + " {1} {2} FOR UPDATE " ;
			//#CM708243
			// Edit the acquisition condition.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			}
			else
			{
				//#CM708244
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing.
				fmtObj[0] = " * " ;
			}
			//#CM708245
			// Edit the search condition.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}
			//#CM708246
			// Edit the order of reading.
			if (key.getSortCondition() != null)
			{
				fmtObj[2] = " ORDER BY " + key.getSortCondition() ;
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " FINDFORUPDATE SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			entity = convertEntities(rset, key, 0) ;
		}
		catch (SQLException e)
		{
			//#CM708247
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708248
			// Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708249
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708250
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
		return entity ;
	}

	//#CM708251
	/**
	 * Retrieve information on the data base based on the parameter, and return the object.
	 * Lock acquired information. When doing, and exceeding with second specified number of lock waiting
	 * Notify LockTimeOutException.
	 * @param key
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public Entity[] findForUpdate(SearchKey key, int second)
			throws ReadWriteException, LockTimeOutException
	{
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[4] ;
		Entity[] entity = null ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT {0} FROM " + p_TableName + " {1} {2} FOR UPDATE WAIT {3}" ;
			//#CM708252
			// Edit the acquisition condition.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			}
			else
			{
				//#CM708253
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing.
				fmtObj[0] = " * " ;
			}
			//#CM708254
			// Edit the search condition.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}
			//#CM708255
			// Edit the order of reading.
			if (key.getSortCondition() != null)
			{
				fmtObj[2] = " ORDER BY " + key.getSortCondition() ;
			}
			fmtObj[3] = Integer.toString(second);
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " FINDFORUPDATE SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			entity = convertEntities(rset, key, 0) ;
		}
		catch (SQLException e)
		{
			//#CM708256
			// Because the error code 54 and 30006 are the error codes only for Oracle
			//#CM708257
			// Review it when you use other data bases.
			//#CM708258
			// Moreover, because it becomes nowait treatment when 0 is specified by the wait specification
			//#CM708259
			// Check error code 54 of TimeOut when nowoait is specified.
			//#CM708260
			// When specification does TimeOut in the cases of except 0, 30006 is returned.
			if (e.getErrorCode() == 54)
			{
				throw new LockTimeOutException("SELECT FOR UPDATE TABLE " + p_TableName);
			}
			else if (e.getErrorCode() == 30006)
			{
				throw new LockTimeOutException("SELECT FOR UPDATE TABLE " + p_TableName);
			}
			//#CM708261
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708262
			// Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708263
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708264
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
		return entity ;
	}

	//#CM708265
	/**
	 * Retrieve information on the data base based on the parameter, and return the object.
	 * Lock acquired information. When failing in the acquisition of the lock while other processing is locking
	 * Notify LockTimeOutException.
	 * @param Retrieval key
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public Entity[] findForUpdateNowait(SearchKey key)
			throws ReadWriteException, LockTimeOutException
	{
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[3] ;
		Entity[] entity = null ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT {0} FROM " + p_TableName + " {1} {2} FOR UPDATE NOWAIT" ;
			//#CM708266
			// Edit the acquisition condition.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			}
			else
			{
				//#CM708267
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing.
				fmtObj[0] = " * " ;
			}
			//#CM708268
			// Edit the search condition.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}
			//#CM708269
			// Edit the order of reading.
			if (key.getSortCondition() != null)
			{
				fmtObj[2] = " ORDER BY " + key.getSortCondition() ;
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " FINDFORUPDATE SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			entity = convertEntities(rset, key, 0) ;
		}
		catch (SQLException e)
		{
			//#CM708270
			// Because error code 54 is an error code only for Oracle
			//#CM708271
			// Review it when you use other data bases.
			if (e.getErrorCode() == 54)
			{
				throw new LockTimeOutException("SELECT FOR UPDATE NOWAIT TABLE " + p_TableName);
			}
			//#CM708272
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708273
			// Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708274
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708275
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
		return entity ;
	}


	//#CM708276
	/**
	 * Retrieve information on the data base based on the parameter, and return the object.
	 * Retrieval information must be Primary (Only one). NoPrimaryException is notified when existing by the plural.
	 * Lock acquired information.  * @param key Key for retrieval
	 * @param key
	 *
	 * @return Array of made object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws NoPrimaryException
	 */
	public Entity findPrimaryForUpdate(SearchKey key)
			throws ReadWriteException,
				NoPrimaryException
	{
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[3] ;
		Entity entity[] = null ;

		try
		{
			//#CM708277
			// Acquire the number of cases of object information in the search condition.
			int rCount = count(key) ;
			if (rCount == 0)
			{
				//#CM708278
				// Return null when pertinent data is not found.
				return null ;
			}
			if (rCount > 1)
			{
				//#CM708279
				//6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, this.getClass().getName(), null) ;
				//#CM708280
				//Here, throw ReadWriteException with the error message.
				throw (new NoPrimaryException("6006002" + wDelim + p_TableName)) ;
			}
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT {0} FROM " + p_TableName + " {1} {2} FOR UPDATE " ;
			//#CM708281
			// Edit the acquisition condition.
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			}
			else
			{
				//#CM708282
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing.
				fmtObj[0] = " * " ;
			}
			//#CM708283
			// Edit the search condition.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = " WHERE " + key.getReferenceCondition() ;
			}
			//#CM708284
			// Edit the order of reading.
			if (key.getSortCondition() != null)
			{
				fmtObj[2] = " ORDER BY " + key.getSortCondition() ;
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " FINDPRIMARYFORUPDATE SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			entity = convertEntities(rset, key, 0) ;
			//#CM708285
			/* 2006/06/21 v2.6.0 START Y.Okamura
			 * Data is processed at the other end after the case qty check is done.
			 * The acquisition result of the result set : because there might not be check in matching case qty
			 * Here, it is assumed that the case qty check is done again.  */
			if (entity == null || entity.length == 0)
			{
				//#CM708286
				// Return null when pertinent data is not found.
				return null ;
			}
			if (entity.length > 1)
			{
				//#CM708287
				//6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, this.getClass().getName(), null) ;
				//#CM708288
				//Here, throw ReadWriteException with the error message.
				throw (new NoPrimaryException("6006002" + wDelim + p_TableName)) ;
			}
			//#CM708289
			/* 2006/06/21 END */

		}
		catch (SQLException e)
		{
			//#CM708290
			//6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708291
			//Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708292
				//6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708293
				//Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
		return entity[0] ;
	}

	//#CM708294
	/**
	 * Retrieve information on the data base based on the parameter, and return the number of results.
	 * @param key Key for retrieval
	 * @return Retrieval results qty
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public int count(SearchKey key)
			throws ReadWriteException
	{
		int wCount = 0 ;
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[3] ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT COUNT({0}) COUNT FROM " + p_TableName + " {1} {2}" ;
			//#CM708295
			// Edit the acquisition condition.
			if (key.getCollectConditionForCount() != null)
			{
				fmtObj[0] = key.getCollectConditionForCount() ;
			}
			else
			{
				//#CM708296
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing.
				fmtObj[0] = " * " ;
			}
			//#CM708297
			// Edit the search condition.
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = "WHERE " + key.getReferenceCondition() ;
			}
			//#CM708298
			// Edit the consolidating condition.
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition() ;
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " COUNT SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;
			if (rset != null)
			{
				while (rset.next())
				{
					wCount = rset.getInt("COUNT") ;
				}
			}
		}
		catch (SQLException e)
		{
			//#CM708299
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708300
			// Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708301
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708302
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
		return wCount ;
	}

	//#CM708303
	/**
	 * Make the database new information.
	 * @param tgt Entity instance with made information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws DataExistsException It has already been notified when same information has registered in the data base.
	 */
	public void create(Entity tgt)
			throws ReadWriteException,
				DataExistsException
	{
		Statement stmt = null ;
		ResultSet rset = null ;

		//#CM708304
		/* 2006/07/05 v2.6.1 START Y.Okamura
		 * When SQLException is generated, it is corrected that SQL can be acquired.  */
		String sqlwi = null;
		try
		{
			AbstractEntity wi = (AbstractEntity)tgt ;
			stmt = p_Conn.createStatement() ;
			sqlwi = buildInsertSQL(wi) ;
DEBUG.MSG("HANDLER", p_TableName + " CREATE SQL[" + sqlwi + "]") ;
			stmt.executeUpdate(sqlwi) ;
		}
		catch (SQLException e)
		{
			//#CM708305
			// At the violation of a determinate restriction
			if (e.getErrorCode() == 1)
			{
				// At the violation of a determinate restriction
				// 6006029=Because the same data already exists, it is not possible to register.  SQLScript=[{0}]
				RmiMsgLogClient.write("6006029" + wDelim + sqlwi, this.getClass().getName());
				throw new DataExistsException("6006029" + wDelim + sqlwi);
			}
			else
			{
				//#CM708308
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708309
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
		//#CM708310
		/* 2006/07/05 END */

		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708311
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708312
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
	}

	//#CM708313
	/**
	 * Change information on the data base. Acquire the content of the change and the change condition from AlterKey.
	 * @param key Entity instance with changed information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws NotFoundException It is notified when information which should be changed is not found.
	 * @throws InvalidDefineException It is notified when the content of the update is not set.
	 */
	public void modify(AlterKey key)
			throws ReadWriteException,
				NotFoundException,
				InvalidDefineException
	{
		Object[] fmtObj = new Object[3] ;
		String[] tables = {	p_TableName	} ;
		String fmtSQL = " UPDATE {0} set {1} {2}" ;
		Statement stmt = null ;

		int count = 0 ;
		boolean execflag = false ;
		int tablecnt = 0 ;

		try
		{
			stmt = p_Conn.createStatement() ;
			//#CM708314
			// Execute SQL according to the table. (Only this method : why. )
			for (tablecnt = 0; tablecnt < tables.length; tablecnt++)
			{
				fmtObj[0] = tables[tablecnt] ;
				if (key.getModifyContents(tables[tablecnt]) == null)
				{
					continue ;
				}
				key.setAutoModify();
				fmtObj[1] = key.getModifyContents(tables[tablecnt]);

				if (key.getReferenceCondition(tables[tablecnt]) != null)
				{
					fmtObj[2] = "WHERE " + key.getReferenceCondition(tables[tablecnt]) ;
				}
				else
				{
					//#CM708315
					// Exception when update condition is not set
					Object[] tobj = { tables } ;
					//#CM708316
					// 6006023 = Cannot update database. No condition to update is set. TABLE={0}
					RmiMsgLogClient.write(6006023, LogMessage.F_ERROR, this.getClass().getName(),
							tobj) ;
					throw (new InvalidDefineException("6006023" + wDelim + p_TableName)) ;
				}
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " MODIFY SQL[" + sqlstring + "]") ;
				count = stmt.executeUpdate(sqlstring) ;
				if (count == 0)
				{
					Object[] tObj = new Object[1] ;
					tObj[0] = p_TableName ;
					//#CM708317
					// 6006005 = There is no data to update. Table Name: {0}
					RmiMsgLogClient.write(6006005, LogMessage.F_ERROR, this.getClass().getName(), tObj);
					throw (new NotFoundException("6006005" + wDelim + p_TableName));

				}
				execflag = true ;

			}
			if (execflag == false)
			{
				//#CM708318
				// Exception when UPDATE is not executed (change content undefinition)
				//#CM708319
				// 6006011 = Cannot update database. No updated value is set. TABLE={0}
				RmiMsgLogClient.write(6006011, LogMessage.F_ERROR, this.getClass().getName(), null);
				throw (new InvalidDefineException("6006011" + wDelim + p_TableName));

			}
		}
		catch (SQLException e)
		{
			//#CM708320
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708321
			// Here, throw ReadWriteException with the error message.
			//#CM708322
			// 6006036 = Failed to modify.
			throw (new ReadWriteException("6006036" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (stmt != null)
				{
					stmt.close() ;
				}
			}
			catch (SQLException e)
			{
				//#CM708323
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708324
				// Here, throw ReadWriteException with the error message.
				//#CM708325
				// 6006036 = Failed to modify.
				throw (new ReadWriteException("6006036" + wDelim + p_TableName)) ;
			}
		}
	}

	//#CM708326
	/**
	 * Delete information on the entity instance passed by the parameter from the data base.
	 * @param tgt Entity instance with deleted information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws NotFoundException It is notified when information which should be deleted is not found.
	 */
	public void drop(Entity tgt)
			throws NotFoundException,
				ReadWriteException
	{
		//#CM708327
		// Because the usage is uncertain, it does not mount.
	}

	//#CM708328
	/**
	 * Delete information which agrees with the key passed by the parameter from the data base.
	 * @param key Key to deleted information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 * @throws NotFoundException It is notified when information which should be deleted is not found.
	 */
	public void drop(SearchKey key)
			throws NotFoundException,
				ReadWriteException
	{
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[1] ;
		int count = 0 ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "DELETE FROM " + p_TableName + " {0}" ;
			if (key.getReferenceCondition() != null)
			{
				fmtObj[0] = "WHERE " + key.getReferenceCondition() ;
			}
			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " DROP SQL[" + sqlstring + "]") ;
			count = stmt.executeUpdate(sqlstring) ;
			if (count == 0)
			{
				//#CM708329
				// 6006006 = There is no data to delete. Table Name: {0}
				Object[] tObj = new Object[1] ;
				tObj[0] = p_TableName ;
				RmiMsgLogClient.write(6006006, LogMessage.F_ERROR, this.getClass().getName(), tObj) ;
				throw (new NotFoundException("6006006" + wDelim + p_TableName)) ;
			}
		}
		catch (SQLException e)
		{
			//#CM708330
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708331
			// Here, throw ReadWriteException with the error message.
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close() ;
					rset = null ;
				}
				if (stmt != null)
				{
					stmt.close() ;
					stmt = null ;
				}
			}
			catch (SQLException e)
			{
				//#CM708332
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM708333
				// Here, throw ReadWriteException with the error message.
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
	}

	//#CM708334
	// Protected methods ---------------------------------------------
	//#CM708335
	/**
	 * The mapping of the result set.
	 *
	 * @param rset <CODE>ResultSet</CODE> Connected result
	 * @param wSkey SearchKey
	 * @param max
	 * @throws ReadWriteException Notify the exception generated by the database connection as it is.
	 * @return Entity[]
	 */
	protected Entity[] convertEntities(ResultSet rset, SearchKey wSkey, int max)
			throws ReadWriteException
	{
		Vector entityList = new Vector() ;
		AbstractEntity[] entityArr = null ;
		AbstractEntity tmpEntity = createEntity() ;
		int resultCount = 0 ;
		Timestamp tims = null;
		if (max > 0)
		{
			resultCount = max ;
		}

		//#CM708336
		//int rcnt = 0 ;
		try
		{
			while (rset.next())
			{
				for (int i = 1; i <= rset.getMetaData().getColumnCount(); i++)
				{
					String colname = rset.getMetaData().getColumnName(i) ;
					FieldName field = new FieldName(colname) ;

					Object value = rset.getObject(i) ;
					if ((value instanceof java.util.Date) ||
						(value instanceof oracle.sql.DATE))
					{
						tims = rset.getTimestamp(i);
						value = new java.util.Date(tims.getTime());
					}

					tmpEntity.setValue(field, value) ;
				}
				entityList.addElement(tmpEntity) ;

				tmpEntity = createEntity() ;

				if (resultCount > 0)
				{
					resultCount = resultCount - 1 ;
					if (resultCount <= 0)
						break ;
				}
			}

			//#CM708337
			// Each table was cut in Cast to Entity[].
			entityArr = (AbstractEntity[])java.lang.reflect.Array.newInstance(
					tmpEntity.getClass(), entityList.size()) ;
			entityList.copyInto(entityArr) ;
		}
		catch (SQLException e)
		{
			//#CM708338
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708339
			// Here, throw ReadWriteException with the error message.
			//#CM708340
			// 6006039 = Failed to search for {0}.
			throw (new ReadWriteException("6006039" + wDelim + p_TableName)) ;
		}
		return entityArr ;
	}

	protected abstract AbstractEntity createEntity() ;

	//#CM708341
	// Private methods -----------------------------------------------
	//#CM708342
	/**
	 * Build SQL string for insert to host table.
	 * <br>Column order as follows. <pre>
	 *  Defined column (ascii sorted)
	 *  Fixed value column (ascii sorted)
	 *  </pre>
	 * @param ent
	 *
	 * @return String
	 */
	private String buildInsertSQL(AbstractEntity ent)
	{
		String SQL = null ;

		String preSQL = "INSERT INTO " + p_TableName + " (" ;
		String midSQL = ") VALUES (" ;
		String postSQL = ")" ;

		Map ValMap = ent.getValueMap() ;
		Object[] columns = ValMap.keySet().toArray() ;
		StringBuffer colNames = new StringBuffer() ;
		StringBuffer valStrings = new StringBuffer() ;

		//#CM708343
		// build column list and value posision for prepared statement.
		//#CM708344
		// host column map -> key = defined key, value = host table column name
		for (int i = 0; i < ValMap.size(); i++)
		{
			Object val = ValMap.get(columns[i]) ;
			if (val != null)
			{
				colNames.append(columns[i] + ",") ;
				if (columns[i].toString().equals(AUTOINSERTDATE))
				{
					valStrings.append(DBFormat.format(new Date()) + ",");
				}
				else if (columns[i].toString().equals(AUTOUPDATE))
				{
					valStrings.append(DBFormat.format(new Date()) + ",");
				}
				// 2006/08/07 add T.Kishimoto
				else if (columns[i].toString().equals(AUTOREFIXDATE))
				{
					valStrings.append(DBFormat.format(new Date()) + ",");
				}
				// T.Kishimoto add end
				else if (val == "")
				{
					valStrings.append("''" + ",") ;
				}
				//#CM708345
				// Giving of a single court of the item which starts with "TO_DATA(" is unnecessary.
				else if (val.toString().startsWith("TO_DATE("))
				{
					valStrings.append(val + ",") ;
				}
				else if (val instanceof Date)
				{
					valStrings.append(DBFormat.format((Date)val) + ",") ;
				}
				else
				{
					valStrings.append("'" + val.toString() + "'" + ",") ;
				}
			}
		}

		//#CM708346
		// remove last "," from string buffer.
		colNames.deleteCharAt(colNames.length() - 1) ;
		valStrings.deleteCharAt(valStrings.length() - 1) ;

		SQL = preSQL + colNames.toString() + midSQL + valStrings.toString() + postSQL ;

		return SQL ;
	}

}
//#CM708347
//end of class
