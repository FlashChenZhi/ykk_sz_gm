package jp.co.daifuku.wms.asrs.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.asrs.entity.ASShelfStock;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.AbstractEntity;

//#CM34242
/**
 * Database handler to fetch AS/RS stock info
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
 * @version $Revision: 1.2 $, $Date: 2006/10/30 06:57:46 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */

public class ASStockHandler extends StockHandler
{
	//#CM34243
	//------------------------------------------------------------
	//#CM34244
	// class variables (prefix '$')
	//#CM34245
	//------------------------------------------------------------
	//#CM34246
	//	private String	$classVar ;

	//#CM34247
	//------------------------------------------------------------
	//#CM34248
	// fields (upper case only)
	//#CM34249
	//------------------------------------------------------------
	//#CM34250
	//	public static final int FIELD_VALUE = 1 ;

	//#CM34251
	//------------------------------------------------------------
	//#CM34252
	// properties (prefix 'p_')
	//#CM34253
	//------------------------------------------------------------
	//#CM34254
	/**
	 * Database connection instance<BR>
	 * This class does not do transaction control
	 */
	private Connection p_Conn ;

	//#CM34255
	/**
	 * Reference table name
	 */
	private String p_TableName = "DMSTOCK, PALETTE, SHELF" ;

	//#CM34256
	//------------------------------------------------------------
	//#CM34257
	// instance variables (prefix '_')
	//#CM34258
	//------------------------------------------------------------
	//#CM34259
	//	private String	_instanceVar ;

	//#CM34260
	//------------------------------------------------------------
	//#CM34261
	// constructors
	//#CM34262
	//------------------------------------------------------------
	//#CM34263
	/**
	 * return revision of this class
	 * @return revision as string
	 */
	public static String getVersion()
	{
		return "$Id: ASStockHandler.java,v 1.2 2006/10/30 06:57:46 suresh Exp $" ;
	}

	//#CM34264
	/**
	 * specify database connection and create instance
	 * @param conn database connection object
	 */
	public ASStockHandler(Connection conn)
	{
		super(conn) ;
	}

	//#CM34265
	/**
	 * Set <code>Connection</code> for database connection use
	 * @param conn Connection object
	 */
	public void setConnection(Connection conn)
	{
		p_Conn = conn ;
	}

	//#CM34266
	/**
	 * Fetch <code>Connection</code> for database connection use�B
	 * @return The <code>Connection</code> in storage
	 */
	public Connection getConnection()
	{
		return (p_Conn) ;
	}

	//#CM34267
	/**
	 * Set <code>TableName</code> for database connection
	 * @param tblName TableName
	 */
	public void setTableName(String tblName)
	{
		p_TableName = tblName ;
	}

	//#CM34268
	/**
	 * Fetch <code>TableName</code> for database connection
	 * @return The <code>TableName</code> in storage
	 */
	public String getTableName()
	{
		return p_TableName ;
	}

	//#CM34269
	/**
	 * Search the database using the parameter info and return the result count
	 * @param skey  stock info search key
	 * @param pkey palette info search key
	 * @param shkey location info search key
	 * @return search result count
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public int count(StockSearchKey skey, PaletteSearchKey pkey, ShelfSearchKey shkey)
			throws ReadWriteException
	{
		int wCount = 0 ;
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[3] ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT COUNT(*) COUNT FROM DMSTOCK, PALETTE, SHELF " + 
							" WHERE DMSTOCK.PALETTEID = PALETTE.PALETTEID AND DMSTOCK.LOCATION_NO = SHELF.STATIONNUMBER" +
							" {0} {1} {2} " ;

			//#CM34270
			// add search conditions			
			if (skey.getReferenceCondition() != null)
			{
				fmtObj[0] = " AND " + skey.getReferenceCondition();
			}
			
			if (pkey.getReferenceCondition() != null)
			{
				fmtObj[1] = " AND LOCATION_NO IN (SELECT PALETTE.CURRENTSTATIONNUMBER FROM PALETTE WHERE " 
							+ pkey.getReferenceCondition() + ")";
			}
			
			if (shkey.getReferenceCondition() != null)
			{
				fmtObj[2] = " AND " + shkey.getReferenceCondition();
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
			//#CM34271
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM34272
			//Here, throw ReadWriteException with error message attached. 
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
				//#CM34273
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
				//#CM34274
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + p_TableName)) ;
			}
		}
		
		return wCount ;
	}

	//#CM34275
	/**
	 * Search the database using the parameter info and return the result count
	 * @param skey  stock info search key
	 * @param pkey palette info search key
	 * @param shkey location info search key
	 * @return search result array
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public Entity[] find(StockSearchKey skey, PaletteSearchKey pkey, ShelfSearchKey shkey)
			throws ReadWriteException
	{
		Entity[] rStock = null ;
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[4] ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT DMSTOCK.*, SHELF.* FROM DMSTOCK, PALETTE, SHELF " + 
							" WHERE DMSTOCK.PALETTEID = PALETTE.PALETTEID AND DMSTOCK.LOCATION_NO = SHELF.STATIONNUMBER" +
							" {0} {1} {2} {3} " ;

			//#CM34276
			// add search conditions			
			if (skey.getReferenceCondition() != null)
			{
				fmtObj[0] = " AND " + skey.getReferenceCondition();
			}
			
			if (pkey.getReferenceCondition() != null)
			{
				fmtObj[1] = " AND LOCATION_NO IN (SELECT PALETTE.CURRENTSTATIONNUMBER FROM PALETTE WHERE " 
							+ pkey.getReferenceCondition() + ")";
			}
			
			if (shkey.getReferenceCondition() != null)
			{
				fmtObj[2] = " AND " + shkey.getReferenceCondition();
			}
			
			if (skey.getSortCondition() != null)
			{
				fmtObj[3] = " ORDER BY " + skey.getSortCondition();
			}

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;

			rStock = getEntities(rset);
		}
		catch(SQLException e)
		{
			//#CM34277
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "ASInvCheckStockHandler" ) ;
			//#CM34278
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
				//#CM34279
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "ASInvCheckStockHandler" ) ;
				//#CM34280
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Stock+Palette")) ;
			}
		}
		
		return rStock ;
	}

	//#CM34281
	/**
	 * Search the database using the parameter info and return the result count
	 * @param skey  stock info search key
	 * @param pkey palette info search key
	 * @param shkey location info search key
	 * @return search result array
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	public Entity[] findForUpdate(StockSearchKey skey, PaletteSearchKey pkey, ShelfSearchKey shkey)
			throws ReadWriteException
	{
		Entity[] rStock = null ;
		Statement stmt = null ;
		ResultSet rset = null ;
		Object[] fmtObj = new Object[4] ;

		try
		{
			stmt = p_Conn.createStatement() ;
			String fmtSQL = "SELECT DMSTOCK.*, SHELF.* FROM DMSTOCK, PALETTE, SHELF " + 
							" WHERE DMSTOCK.PALETTEID = PALETTE.PALETTEID AND DMSTOCK.LOCATION_NO = SHELF.STATIONNUMBER" +
							" {0} {1} {2} {3} " + 
							" FOR UPDATE ";

			//#CM34282
			// add search conditions			
			if (skey.getReferenceCondition() != null)
			{
				fmtObj[0] = " AND " + skey.getReferenceCondition();
			}
			
			if (pkey.getReferenceCondition() != null)
			{
				fmtObj[1] = " AND LOCATION_NO IN (SELECT PALETTE.CURRENTSTATIONNUMBER FROM PALETTE WHERE " 
							+ pkey.getReferenceCondition() + ")";
			}
			
			if (shkey.getReferenceCondition() != null)
			{
				fmtObj[2] = " AND " + shkey.getReferenceCondition();
			}
			
			if (skey.getSortCondition() != null)
			{
				fmtObj[3] = " ORDER BY " + skey.getSortCondition();
			}

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", p_TableName + " SQL[" + sqlstring + "]") ;
			rset = stmt.executeQuery(sqlstring) ;

			rStock = getEntities(rset);
		}
		catch(SQLException e)
		{
			//#CM34283
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "ASInvCheckStockHandler" ) ;
			//#CM34284
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
				//#CM34285
				//6006002 = Database error occured.  {0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "ASInvCheckStockHandler" ) ;
				//#CM34286
				//Here, throw ReadWriteException with error message attached. 
				throw (new ReadWriteException("6006002" + wDelim + "Stock+Palette")) ;
			}
		}
		
		return rStock ;
	}

	//#CM34287
	/**
	 * map the result set
	 *
	 * @param p_ResultSet  Search result count
	 * @return Entity array
	 * @throws ReadWriteException Throw any database exception as it is
	 */
	public Entity[] getEntities(ResultSet p_ResultSet) throws ReadWriteException
	{
		Vector entityList = new Vector() ;
		AbstractEntity[] entityArr = null ;
		AbstractEntity tmpEntity = new ASShelfStock() ;
		try
		{
			//#CM34288
			// Flag to decide whether to close the ResultSet or not. Close after the last record is read
			while ( p_ResultSet.next() )
			{			
				for (int i = 1; i <= p_ResultSet.getMetaData().getColumnCount(); i++)
				{
					String colname = p_ResultSet.getMetaData().getColumnName(i) ;
					FieldName field = new FieldName(colname) ;
	
					//#CM34289
					// Fetch milliseconds
					Object value = p_ResultSet.getObject(i) ;
					if (value instanceof java.util.Date)
					{
						value = p_ResultSet.getTimestamp(i) ;
					}

					tmpEntity.setValue(field, value) ;
				}
				entityList.addElement(tmpEntity) ;
	
				tmpEntity = new ASShelfStock() ;
			}
	
			//#CM34290
			// Cast with respect to every table Entity[]
			entityArr = (AbstractEntity[])java.lang.reflect.Array.newInstance(
					tmpEntity.getClass(), entityList.size()) ;
			entityList.copyInto(entityArr) ;

		}
		catch (SQLException e)
		{
			//#CM34291
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName()) ;
			//#CM34292
			//Here, throw ReadWriteException with error message attached. 
			//#CM34293
			//6006039 = Failed to search for {0}.
			throw (new ReadWriteException("6006039" + wDelim + "DNWORKINFO, CARRYINFO")) ;
		}
		
		return entityArr ;
	}
}
