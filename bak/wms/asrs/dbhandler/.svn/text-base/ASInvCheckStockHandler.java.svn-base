package jp.co.daifuku.wms.asrs.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;

//#CM33921
/**
 * AS/RS stock confirmation start handler
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- revision history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.2 $, $Date: 2006/10/30 07:09:24 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */

public class ASInvCheckStockHandler extends StockHandler
{
	//#CM33922
	//------------------------------------------------------------
	//#CM33923
	// class variables (prefix '$')
	//#CM33924
	//------------------------------------------------------------
	//#CM33925
	//	private String	$classVar ;

	//#CM33926
	//------------------------------------------------------------
	//#CM33927
	// fields (upper case only)
	//#CM33928
	//------------------------------------------------------------
	//#CM33929
	//	public static final int FIELD_VALUE = 1 ;

	//#CM33930
	//------------------------------------------------------------
	//#CM33931
	// properties (prefix 'p_')
	//#CM33932
	//------------------------------------------------------------
	//#CM33933
	/**
	 * Database connection instance<BR>
	 * This class does not do transaction control
	 */
	private Connection p_Conn ;

	//#CM33934
	/**
	 * Reference table name
	 */
	private String p_TableName = "DMSTOCK" ;

	//#CM33935
	//------------------------------------------------------------
	//#CM33936
	// instance variables (prefix '_')
	//#CM33937
	//------------------------------------------------------------
	//#CM33938
	//	private String	_instanceVar ;

	//#CM33939
	//------------------------------------------------------------
	//#CM33940
	// constructors
	//#CM33941
	//------------------------------------------------------------
	//#CM33942
	/**
	 * return revision of this class
	 * @return revision as string
	 */
	public static String getVersion()
	{
		return "$Id: ASInvCheckStockHandler.java,v 1.2 2006/10/30 07:09:24 suresh Exp $" ;
	}

	//#CM33943
	/**
	 * specify database connection and create instance
	 * @param conn database connection object
	 */
	public ASInvCheckStockHandler(Connection conn)
	{
		super(conn) ;
	}

	//#CM33944
	/**
	 * Set <code>Connection</code> for database connection use
	 * @param conn Connection object
	 */
	public void setConnection(Connection conn)
	{
		p_Conn = conn ;
	}

	//#CM33945
	/**
	 * Fetch <code>Connection</code> for database connection use�B
	 * @return The <code>Connection</code> in storage
	 */
	public Connection getConnection()
	{
		return (p_Conn) ;
	}

	//#CM33946
	/**
	 * Set <code>TableName</code> for database connection
	 * @param tblName TableName
	 */
	public void setTableName(String tblName)
	{
		p_TableName = tblName ;
	}

	//#CM33947
	/**
	 * Fetch <code>TableName</code> for database connection
	 * @return The <code>TableName</code> in storage
	 */
	public String getTableName()
	{
		return p_TableName ;
	}

	//#CM33948
	/**
	 * Search the database using the parameter info and return the result count
	 * @param skey stock info search key
	 * @param pkeypalette info search key
	 * @return search result count
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public int count(StockSearchKey skey, PaletteSearchKey pkey)
			throws ReadWriteException
	{
		int wCount = 0 ;
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[2] ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT COUNT( * ) COUNT FROM " + p_TableName + 
							" {0} {1} " ;

			//#CM33949
			// add search conditions
			if (skey.getReferenceCondition() != null)
			{
				fmtObj[0] = " WHERE " + skey.getReferenceCondition();
				if (pkey.getReferenceCondition() != null)
				{
					fmtObj[1] = " AND LOCATION_NO IN (SELECT PALETTE.CURRENTSTATIONNUMBER FROM PALETTE WHERE " 
								+ pkey.getReferenceCondition() + ")";
				}
			}
			else
			{
				if (pkey.getReferenceCondition() != null)
				{
					fmtObj[0] = " WHERE LOCATION_NO IN (SELECT PALETTE.CURRENTSTATIONNUMBER FROM PALETTE WHERE " 
								+ pkey.getReferenceCondition() + ")";
					fmtObj[1] = "";
				}
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
			//#CM33950
			// 6006002 = database error occurred{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM33951
			// throw ReadWriteException error message
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
				//#CM33952
				// 6006002 = database error occurred{0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM33953
				// throw ReadWriteException error message
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
		return wCount ;
	}

	//#CM33954
	/**
	 * Search the database using the parameter info and return the result count
	 * @param skey stock info search key
	 * @param pkeypalette info search key
	 * @return stock info search result
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public Entity[] find(StockSearchKey skey, PaletteSearchKey pkey)
			throws ReadWriteException
	{
		Entity[] rStock = null ;
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[3] ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT DMSTOCK.* FROM " + p_TableName + 
							" {0} {1} {2} " ;

			//#CM33955
			// add search conditions
			if (skey.getReferenceCondition() != null)
			{
				fmtObj[0] = " WHERE " + skey.getReferenceCondition();
				if (pkey.getReferenceCondition() != null)
				{
					fmtObj[1] = " AND LOCATION_NO IN (SELECT PALETTE.CURRENTSTATIONNUMBER FROM PALETTE WHERE " 
								+ pkey.getReferenceCondition() + ")";
				}
			}
			else
			{
				if (pkey.getReferenceCondition() != null)
				{
					fmtObj[0] = " WHERE LOCATION_NO IN (SELECT PALETTE.CURRENTSTATIONNUMBER FROM PALETTE WHERE " 
								+ pkey.getReferenceCondition() + ")";
					fmtObj[1] = "";
				}
			}
			if (skey.getSortCondition() != null)
			{
				fmtObj[2] = " ORDER BY " + skey.getSortCondition();
			}

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;

			rStock = convertEntities(rset, skey, 0);
		}
		catch(SQLException e)
		{
			//#CM33956
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "ASInvCheckStockHandler" ) ;
			//#CM33957
			//Here, throw ReadWriteException with error message attached. 
			throw (new ReadWriteException("6006002" + wDelim + "Stock+Palette")) ;
		}
		finally
		{
			try
			{
				if (rset != null)
				{
					rset.close();
					rset = null;
				}
				
				if (stmt != null)
				{
					stmt.close();
					stmt = null;
				}
			}
			catch(SQLException e)
			{
				//#CM33958
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "ASInvCheckStockHandler" ) ;
				//#CM33959
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Stock+Palette")) ;
			}
		}
		return rStock ;
	}
}
