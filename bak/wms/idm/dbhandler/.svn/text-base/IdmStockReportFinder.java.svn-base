package jp.co.daifuku.wms.idm.dbhandler;

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

/**
 * <CODE>ItemStorageReportFinder</CODE>クラスは、データベース（テーブル）を操作するためのクラスです。
 * このクラスは、DMSTOCKとDMLOCATEに紐付いたデータを検索するために使用します。
 * <BR>
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Y.Osawa</b></td><td><b>新規作成</b></td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision, $Date: 2006/08/17 09:34:09 $
 * @author  C.Kaminishizono
 * @author  Last commit: $Author: mori $
 */public class IdmStockReportFinder extends StockReportFinder
{
	// Class variables -----------------------------------------------

	// Constructors --------------------------------------------------
	/**
	 * データベースコネクションを指定してインスタンスを生成します。
	 * @param conn 接続済みのデータベースコネクション
	 */
	public IdmStockReportFinder(Connection conn)
	{
		super(conn);
	}

	/**
	 * このクラスのリビジョンを返します。
	 * @return リビジョン文字列。
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:09 $") ;
	}

	// Public methods ------------------------------------------------
	/**
	 * Stock, Locateを検索し、取得します。
	 * @param key 検索のためのKey
	 * @return 検索結果の件数
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 */
	public int search(SearchKey key) throws ReadWriteException
	{
		close();
		open();
		
		setTableName("DMSTOCK, DMLOCATE");

		Object[] fmtObj = new Object[4];
		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3} ";

			String fmtSQL = "SELECT {0} "
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3} ";
			// 取得条件を編集する。
			if (key.getCollectConditionForCount() != null)
			{
				cntObj[0] = key.getCollectConditionForCount();
			}
			else
			{
				// 取得条件に指定なし時、全項目取得とする。
				cntObj[0] = " * ";
			}
			
			// 取得条件を編集する。
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
			} else {
				// 取得条件に指定なし時、全項目取得とする。
				fmtObj[0] = " * " ;
			}

			// 検索条件を編集する。
			if (key.getReferenceCondition() != null)
			{
				fmtObj[1] = "WHERE " + key.getReferenceCondition() + "AND DMSTOCK.LOCATION_NO = DMLOCATE.LOCATION_NO";
				cntObj[1] = "WHERE " + key.getReferenceCondition() + "AND DMSTOCK.LOCATION_NO = DMLOCATE.LOCATION_NO";
			}
			else
			{
				fmtObj[1] = " WHERE DMSTOCK.LOCATION_NO = DMLOCATE.LOCATION_NO";
				cntObj[1] = " WHERE DMSTOCK.LOCATION_NO = DMLOCATE.LOCATION_NO";
			}

			// 集約条件を編集する。			
			if (key.getGroupCondition() != null)
			{
				fmtObj[2] = " GROUP BY " + key.getGroupCondition();
				cntObj[2] = " GROUP BY " + key.getGroupCondition();
			}

			// 読込み順を編集する。				
			if (key.getSortCondition() != null)
			{
				fmtObj[3] = "ORDER BY " + key.getSortCondition();
				cntObj[3] = "ORDER BY " + key.getSortCondition();
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
			// １件以上の場合にのみ検索を実行します
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
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		return count;

	}

	/**
	 * データベースから、パラメータに基づいて情報を検索し、結果の件数を返します。
	 * @param key 検索のためのKey
	 * @return 検索結果の件数
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public int count(SearchKey key) throws ReadWriteException
	{
		close();
		open();

		setTableName("DMSTOCK, DMLOCATE");

		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
		try
		{
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM " + getTableName() + " "
				+ "{1} {2} {3} ";

			// 取得条件を編集する。
			if (key.getCollectConditionForCount() != null)
			{
				cntObj[0] = key.getCollectConditionForCount();
			}
			else
			{
				// 取得条件に指定なし時、全項目取得とする。
				cntObj[0] = " * ";
			}
			
			// 検索条件を編集する。
			if (key.getReferenceCondition() != null)
			{
				cntObj[1] = "WHERE " + key.getReferenceCondition() + "AND DMSTOCK.LOCATION_NO = DMLOCATE.LOCATION_NO";
			}
			else
			{
				cntObj[1] = " WHERE DMSTOCK.LOCATION_NO = DMLOCATE.LOCATION_NO";
			}

			// 集約条件を編集する。			
			if (key.getGroupCondition() != null)
			{
				cntObj[2] = " GROUP BY " + key.getGroupCondition();
			}

			// 読込み順を編集する。				
			if (key.getSortCondition() != null)
			{
				cntObj[3] = "ORDER BY " + key.getSortCondition();
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
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), getTableName() ) ;
			throw (new ReadWriteException("6006002" + wDelim + getTableName())) ;
		}
		return count;
	}

}
