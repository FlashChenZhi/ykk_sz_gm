//#CM34210
//$Id: ASStockFinder.java,v 1.2 2006/10/30 07:09:22 suresh Exp $
package jp.co.daifuku.wms.asrs.dbhandler;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.entity.ASStock;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.dbhandler.StockFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.AbstractEntity;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Stock;

//#CM34211
/**
 * fetch stock info based on location status
 * If palette or stock info does not exist, receive from shelf
 *
 * @author $Revision: 1.2 $, $Date: 2006/10/30 07:09:22 $
 * @version $Author: suresh $
 */
public class ASStockFinder  extends StockFinder
{
	//#CM34212
	// Class filelds -----------------------------------------------

	//#CM34213
	// Class method --------------------------------------------------
	//#CM34214
	/**
	 * return revision of this class
	 * @return revision as string
	 */
	public static String getVersion()
	{
		return "$Id: ASStockFinder.java,v 1.2 2006/10/30 07:09:22 suresh Exp $" ;
	}

	//#CM34215
	// Class valiable ------------------------------------------------

	//#CM34216
	// Constructors --------------------------------------------------
	//#CM34217
	/**
	 * Generates the instance according to the parameter of Connection instance to connect with database.
	 * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
	 * @param conn  :<code>Connection</code> to connect with database
	 */
	public ASStockFinder(Connection conn)
	{
		super(conn);
	}

	//#CM34218
	//------------------------------------------------------------
	//#CM34219
	// protected methods
	//#CM34220
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM34221
	//------------------------------------------------------------
	//#CM34222
	/**
	 * @see dbhandler.AbstractDBFinder#createEntity()
	 */
	protected AbstractEntity createEntity()
	{
		return (new ASStock());
	}

	//#CM34223
	// Public methods ------------------------------------------------
	//#CM34224
	/**
	 * Search location, palette, stock and fetch the stock info related to location 
	 * If stock info does not exist, fetch from location info
	 * Search is not possible if nothing is set in the search status
	 * The result becomes "no target data"
	 * 
	 * @param stkKey stock��Search key
	 * @param whStNo warehouse station no.
	 * @param status search status (AsScheduleParameter search condition)
	 * @return search result count
	 * @throws ReadWriteException Throw exception that occurs during database connection
	 */
	public int search(StockSearchKey stkKey, String whStNo, String status[]) throws ReadWriteException
	{
		close();
		open();
		
		//#CM34225
		// Object array to set search condition, fetch condition
		Object[]  fmtObj = new Object[2];
		
		int count = 0;
		ResultSet countret  = null ;

		try
		{
			//#CM34226
			// Return 0 if nothing is set in the search condition
			if (status == null)
			{
				p_ResultSet = null;
				return 0;
			}
			
			String fmtCountSQL =	" SELECT COUNT(*) COUNT FROM SHELF, PALETTE, " +
									" (SELECT * FROM DMSTOCK WHERE STATUS_FLAG = '"+ 
									Stock.STOCK_STATUSFLAG_OCCUPIED + "') DMSTOCK " +
									" WHERE SHELF.STATIONNUMBER = PALETTE.CURRENTSTATIONNUMBER(+) " +
									" AND SHELF.STATIONNUMBER = DMSTOCK.LOCATION_NO(+) ";

			String fmtSQL = 		" SELECT SHELF.STATIONNUMBER S_STATIONNUMBER, SHELF.STATUS S_STATUS, " +
									" SHELF.PRESENCE S_PRESENCE, SHELF.ACCESSNGFLAG S_ACCESSNGFLAG, " +
									" PALETTE.EMPTY P_EMPTY, PALETTE.STATUS P_STATUS, DMSTOCK.* " + 
									" FROM SHELF, PALETTE, " +
									" (SELECT * FROM DMSTOCK WHERE STATUS_FLAG = '"+ 
									Stock.STOCK_STATUSFLAG_OCCUPIED + "') DMSTOCK " +
									" WHERE SHELF.STATIONNUMBER = PALETTE.CURRENTSTATIONNUMBER(+) " +
									" AND SHELF.STATIONNUMBER = DMSTOCK.LOCATION_NO(+) ";

			if (!StringUtil.isBlank(whStNo))
			{
				fmtCountSQL += "AND SHELF.WHSTATIONNUMBER = " + DBFormat.format(whStNo);
				fmtSQL += "AND SHELF.WHSTATIONNUMBER = " + DBFormat.format(whStNo);
			}
			
			//#CM34227
			// Create SQL to search status
			String statusSql = connectStatusSQL(status);
			fmtCountSQL += statusSql + "{0}";
			fmtSQL += statusSql + "{0} {1}";
			
			//#CM34228
			// Set conditions based on search key
			//#CM34229
			// add search conditions
			if (stkKey.getReferenceCondition() != null)
			{
				fmtObj[0] = " AND " + stkKey.getReferenceCondition() ;
			}
			
			//#CM34230
			// reading order
			if (stkKey.getSortCondition() != null)
			{
				fmtObj[1] = " ORDER BY SHELF.STATIONNUMBER, " + stkKey.getSortCondition() ;
			}
			else
			{
				fmtObj[1] = " ORDER BY SHELF.STATIONNUMBER ";
			}

			//#CM34231
			// execute search if the count is equal to 1 or more
			String sqlcountstring = SimpleFormat.format(fmtCountSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "ASStatusLocationFider COUNT SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "ASStatusLocationFider SQL[" + sqlstring + "]") ;
				p_ResultSet = p_Statement.executeQuery(sqlstring);
			}
			else
			{
				p_ResultSet = null;
			}
			
		}
		catch (SQLException e)
		{
			//#CM34232
			//6006002 = Database error occured.  {0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "ASStatusLocationFider" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "SHELF PALETTE")) ;
		}
		
		return count;
	}
	
	//#CM34233
	// Protected methods ------------------------------------------------
	//#CM34234
	/**
	 * Create SQL with respect to caller status
	 * 
	 * @param pStatus search target status
	 * @return search SQL
	 */
	protected String connectStatusSQL(String pStatus[])
	{
		String statusSql = "";
		for (int i = 0; i < pStatus.length; i++)
		{
			if (i == 0)
			{
				statusSql += " AND ( ";
			}
			else
			{
				statusSql += " OR ";
			}
			
			//#CM34235
			// result location
			if (pStatus[i].equals(Integer.toString(AsScheduleParameter.STATUS_STORAGED)))
			{
				statusSql += " (SHELF.PRESENCE = " + Shelf.PRESENCE_STORAGED +
							" AND PALETTE.STATUS = " + Palette.REGULAR +
							" AND PALETTE.EMPTY != " + Palette.STATUS_EMPTY + ") ";
			}
			//#CM34236
			// empty location
			else if (pStatus[i].equals(Integer.toString(AsScheduleParameter.STATUS_EMPTY)))
			{
				statusSql += " (SHELF.PRESENCE = " + Shelf.PRESENCE_EMPTY + ") ";
			}
			//#CM34237
			// Error location
			else if (pStatus[i].equals(Integer.toString(AsScheduleParameter.STATUS_IRREGULAR)))
			{
				statusSql += " (SHELF.PRESENCE = " + Shelf.PRESENCE_STORAGED +
							" AND PALETTE.STATUS = " + Palette.IRREGULAR + ") ";
			}
			//#CM34238
			// Empty Palette
			else if (pStatus[i].equals(Integer.toString(AsScheduleParameter.STATUS_EMPTYPALETTE)))
			{
				statusSql += " (SHELF.PRESENCE = " + Shelf.PRESENCE_STORAGED +
							" AND PALETTE.STATUS = " + Palette.REGULAR +
							" AND PALETTE.EMPTY = " + Palette.STATUS_EMPTY + ") ";
			}
			//#CM34239
			// restricted location
			else if (pStatus[i].equals(Integer.toString(AsScheduleParameter.STATUS_UNAVAILABLE)))
			{
				statusSql += " (SHELF.STATUS = " + Shelf.STATUS_NG + ") ";
			}
		}
		statusSql += " ) ";
		
		return statusSql;
		
	}

	//#CM34240
	// Private methods ------------------------------------------------

}
//#CM34241
// end of class
