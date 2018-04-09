package jp.co.daifuku.wms.asrs.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.entity.AbstractEntity;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM34294
/**
 * This class operates the location info, palette info, stock info<BR>
 * Describe SQL that can't be used with normal ReportFinder<BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Y.Kato</b></td><td><b>new</b></td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision, $Date: 2006/10/30 06:57:46 $
 * @author  C.Kaminishizono
 * @author  Last commit: $Author: suresh $
 */
public class ASStockReportFinder extends StockReportFinder
{
	//#CM34295
	// Class variables -----------------------------------------------

	//#CM34296
	// Constructors --------------------------------------------------
	//#CM34297
	/**
	 * specify database connection and create instance
	 * @param conn database connection object
	 */
	public ASStockReportFinder(Connection conn)
	{
		super(conn);
	}

	//#CM34298
	/**
	 * @see dbhandler.AbstractDBFinder#createEntity()
	 */	
	protected AbstractEntity createEntity()
	{
		return (new Stock());
	}

	//#CM34299
	/**
	 * return revision of this class
	 * @return revision as string
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 06:57:46 $") ;
	}

	//#CM34300
	// Public methods ------------------------------------------------
	//#CM34301
	/**
	 * Search location, palette, stock that relates to stockinfo and return result count
	 * 
	 * @param skey  search key
	 * @return search result count
	 * @throws ReadWriteException throw any database access exception
	 */
	public int searchStock(SearchKey[] skey) throws ReadWriteException
	{
		close();
		open();
		
		setTableName("DMSTOCK, PALETTE, SHELF");

		Object[] fmtObj = new Object[5];
		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " "
				+ "WHERE DMSTOCK.PALETTEID = PALETTE.PALETTEID AND DMSTOCK.LOCATION_NO = SHELF.STATIONNUMBER "
				+ "	{1} {2} {3} ";
			String fmtSQL = "SELECT {0} " 
				+ "FROM " + getTableName() + " "
				+ "WHERE DMSTOCK.PALETTEID = PALETTE.PALETTEID AND DMSTOCK.LOCATION_NO = SHELF.STATIONNUMBER "
				+ "	{1} {2} {3} {4}";
		
			//#CM34302
			// add search conditions		
			fmtObj[0] = "DMSTOCK.*, SHELF.*" ;
			cntObj[0] = "*" ;
			if (skey[0].getReferenceCondition() != null)
			{
				fmtObj[1] = " AND " + skey[0].getReferenceCondition();
				cntObj[1] = " AND " + skey[0].getReferenceCondition();
			}
			
			if (skey[1].getReferenceCondition() != null)
			{
				fmtObj[2] = " AND " + skey[1].getReferenceCondition();
				cntObj[2] = " AND " + skey[1].getReferenceCondition();
			}
			
			if (skey[2].getReferenceCondition() != null)
			{
				fmtObj[3] = " AND " + skey[2].getReferenceCondition();
				cntObj[3] = " AND " + skey[2].getReferenceCondition();
			}
			
			if (skey[0].getSortCondition() != null)
			{
				fmtObj[4] = " ORDER BY " + skey[0].getSortCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", getTableName() + " ReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			try
			{
				countret = p_Statement.executeQuery(sqlcountstring);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			
			//#CM34303
			//execute search if the count is equal to 1 or more
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", getTableName() + " RportFinder SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
				isNextFlag = true;
			}
			else
			{
				p_ResultSet = null;
				isNextFlag = false;
			}
		}
		catch (SQLException e)
		{
			//#CM34304
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		
		return count;
	}

	//#CM34305
	/**
	 * Search location, palette, stock that relates to stockinfo and return result count
	 * @param skey  search key
	 * @return search result count
	 * @throws ReadWriteException throw any database access exception
	 */
	public int count(SearchKey[] skey) throws ReadWriteException
	{
		close();
		open();
		setTableName("DMSTOCK, PALETTE, SHELF");

		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " "
				+ "WHERE DMSTOCK.PALETTEID = PALETTE.PALETTEID AND DMSTOCK.LOCATION_NO = SHELF.STATIONNUMBER "
				+ "	{1} {2} {3} ";

			//#CM34306
			// add search conditions		
			cntObj[0] = "*" ;
			if (skey[0].getReferenceCondition() != null)
			{
				cntObj[1] = " AND " + skey[0].getReferenceCondition();
			}
			
			if (skey[1].getReferenceCondition() != null)
			{
				cntObj[2] = " AND " + skey[1].getReferenceCondition();
			}
			
			if (skey[2].getReferenceCondition() != null)
			{
				cntObj[3] = " AND " + skey[2].getReferenceCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", getTableName() + " ReportFinder COUNT SQL[" + sqlcountstring + "]") ;
			try
			{
				countret = p_Statement.executeQuery(sqlcountstring);
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
		}
		catch (SQLException e)
		{
			//#CM34307
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		
		return count;
	}
}
