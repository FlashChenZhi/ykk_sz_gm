// $Id: AbstractDBFinder.java,v 1.2 2006/11/15 04:25:35 kamala Exp $
package jp.co.daifuku.wms.base.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.entity.AbstractEntity;


//#CM708120
/**
 * < CODE>AbstructDBFinder</CODE > class is a super-class to operate the database (table). 
 * <BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Y.Kato</b></td><td><b>New making</b></td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision, $Date: 2006/11/15 04:25:35 $
 * @author  C.Kaminishizono
 * @author  Last commit: $Author: kamala $
 */
public abstract class AbstractDBFinder
			implements DatabaseFinder
{
	private static final int MAX_RESULT = 5000 ;

	//#CM708121
	//------------------------------------------------------------
	//#CM708122
	// properties (prefix 'p_')
	//#CM708123
	//------------------------------------------------------------
	//#CM708124
	/**
	 * Variable which manages statement. 
	 */
	protected Statement p_Statement = null ;

	//#CM708125
	/**
	 * Variable which maintains retrieval result. 
	 */
	protected ResultSet p_ResultSet = null ;

	//#CM708126
	/**
	 * Connection instance for data base connection. <BR>
	 * Do not do the transaction management in this class. 
	 */
	private Connection p_Conn ;

	//#CM708127
	/**
	 * Reference table name
	 */
	private String p_TableName = null ;

	//#CM708128
	// Class variables -----------------------------------------------

	//#CM708129
	// Constructors --------------------------------------------------
	//#CM708130
	/**
	 * constructor comment.
	 *
	 * @param conn Connection
	 * @param tbName String
	 */
	public AbstractDBFinder(Connection conn, String tbName)
	{
		setTableName(tbName) ;
		setConnection(conn) ;
	}

	//#CM708131
	// Class method --------------------------------------------------
	//#CM708132
	/**
	 * Return Revision of this class. 
	 * @return Revision character string. 
	 */
	public static String getVersion()
	{
		return "$Id: AbstractDBFinder.java,v 1.2 2006/11/15 04:25:35 kamala Exp $" ;
	}

	//#CM708133
	// Public methods ------------------------------------------------
	//#CM708134
	/**
	 * Set <code>Connection</code> for the database connection. 
	 * @param conn Connection to be set
	 */
	public void setConnection(Connection conn)
	{
		p_Conn = conn ;
	}

	//#CM708135
	/**
	 * Acquire <code>Connection</code> for the database connection. 
	 * @return < code>Connection</code> being maintained
	 */
	public Connection getConnection()
	{
		return (p_Conn) ;
	}

	//#CM708136
	/**
	 * Set <code>TableName</code> for the database connection. 
	 * @param tblName Table name to be set
	 */
	public void setTableName(String tblName)
	{
		p_TableName = tblName ;
	}

	//#CM708137
	/**
	 * Acquire <code>TableName</code> for the database connection. 
	 * @return <code>TableName</code> being maintained
	 */
	public String getTableName()
	{
		return p_TableName ;
	}
	
	//#CM708138
	/**
	 * Generate the statement, and open the cursor. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void open() throws ReadWriteException
	{
		try
		{
			p_Statement = p_Conn.createStatement(
												ResultSet.TYPE_SCROLL_INSENSITIVE, 
												ResultSet.CONCUR_READ_ONLY
											  ) ;
		}
		catch (SQLException e)
		{
			//#CM708139
			//6006002 = Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), p_TableName ) ;
			throw (new ReadWriteException("6006002" + wDelim + Integer.toString(e.getErrorCode()))) ;
		}
	}
	//#CM708140
	/**
	 * The mapping of the result set. 
	 *
	 * @param Starting position for which retrieval result is specified
	 * @param End position in which retrieval result was specified
	 * @return Entity array
	 * @throws ReadWriteException Notify the exception generated by the data base connection as it is. 
	 * @return Entity[]
	 */
	public Entity[] getEntities(int start, int end) throws ReadWriteException
	{
		Vector entityList = new Vector() ;
		AbstractEntity[] entityArr = null ;
		AbstractEntity tmpEntity = createEntity() ;
		try
		{
			//#CM708141
			// Display qty
			int count = end - start;
			if (p_ResultSet.absolute(start+1))
			{
				for (int lc = 0; lc < count; lc++)
				{
					if(lc > 0)
					{
						p_ResultSet.next();
					}
					for (int i = 1; i <= p_ResultSet.getMetaData().getColumnCount(); i++)
					{
						String colname = p_ResultSet.getMetaData().getColumnName(i) ;
						FieldName field = new FieldName(colname) ;
	
						//#CM708142
						// The class at time of the date might take the millisecond if it is not getTimestamp() and leak it. 
						Object value = p_ResultSet.getObject(i) ;
						if (value instanceof java.util.Date)
						{
							value = p_ResultSet.getTimestamp(i) ;
						}

						tmpEntity.setValue(field, value) ;
					}
					entityList.addElement(tmpEntity) ;
	
					tmpEntity = createEntity() ;
				}
	
				//#CM708143
				// Each table was cut in Cast. 
				entityArr = (AbstractEntity[])java.lang.reflect.Array.newInstance(
						tmpEntity.getClass(), entityList.size()) ;
				entityList.copyInto(entityArr) ;
			}
			else
			{
				//#CM708144
				// The specified line is not correct. 
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "AbstractDBFinder", null);
				throw new ReadWriteException("6006010");
			}
		}
		catch (SQLException e)
		{
			//#CM708145
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM708146
			// Here, throw ReadWriteException with the error message. 
			//#CM708147
			// 6006039 = Failed to search for {0}.
			throw (new ReadWriteException("6006039" + wDelim + p_TableName)) ;
		}
		return entityArr ;
	}

	protected abstract AbstractEntity createEntity() ;

	//#CM708148
	/**
	 * Close the statement. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void close() throws ReadWriteException
	{
		try
		{
			if (p_ResultSet != null) { p_ResultSet.close();  p_ResultSet = null; }
			if (p_Statement != null) { p_Statement.close();  p_Statement = null; }
		}
		catch (SQLException e)
		{
			//#CM708149
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			throw (new ReadWriteException("6006002" + wDelim + p_TableName));
		}
	}

	//#CM708150
	/**
	 * Retrieve the data base, and acquire it. 
	 * @param key Key for retrieval
	 * @return Number of retrieval results
	 * @throws ReadWriteException Notify the exception generated by the connection with the data base as it is. 
	 */
	public int search(SearchKey key) throws ReadWriteException
	{
		Object[] fmtObj = new Object[4] ;
		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + p_TableName + " "
				+ "{1} {2} {3} ";

			String fmtSQL = "SELECT {0} "
				+ "FROM " + p_TableName + " "
				+ "{1} {2} {3}";
//#CM708151
//			 Edit the acquisition condition. 
			if (key.getCollectConditionForCount() != null)
			{
				cntObj[0] = key.getCollectConditionForCount();
			}
			else
			{
				//#CM708152
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing. 
				cntObj[0] = " * ";
			}
			
			//#CM708153
			// Edit the acquisition condition. 
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			} else {
				//#CM708154
				// It is assumed to the acquisition condition all item acquisition at the time of a specified doing. 
				fmtObj[0] = " * " ;
			}

			//#CM708155
			// Edit the search condition. 
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = "WHERE " + key.getReferenceCondition();
				cntObj[1] = "WHERE " + key.getReferenceCondition();
			}

			//#CM708156
			// Edit the consolidating condition. 
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition();
				cntObj[2] = " GROUP BY " + key.getGroupCondition();
			}

			//#CM708157
			// Edit the order of reading. 
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = "ORDER BY " + key.getSortCondition();
				cntObj[3] = "ORDER BY " + key.getSortCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", p_TableName + " Finder COUNT SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//#CM708158
			//Execute the retrieval when the number of cases is only MAXDISP or less. 
			if ( count <= MAXDISP )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " Finder SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
			}
			else
			{
				p_ResultSet = null;
			}
		}
		catch (SQLException e)
		{
			//#CM708159
			//6006002 = Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), p_TableName ) ;
			throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
		}
		return count;

	}

}
