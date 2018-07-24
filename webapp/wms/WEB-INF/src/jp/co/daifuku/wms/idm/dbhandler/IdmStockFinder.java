//$Id: IdmStockFinder.java,v 1.1.1.1 2006/08/17 09:34:09 mori Exp $
package jp.co.daifuku.wms.idm.dbhandler ;

/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.entity.Stock;

/**
 * データベースのStock表とLocation表を結合した検索専用のFinderクラスです。<BR>
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:09 $
 * @author  $Author: mori $
 */
public class IdmStockFinder implements DatabaseFinder
{
	// Class filelds -----------------------------------------------
	/**
	 * ステートメントを管理する変数。
	 */
	protected Statement wStatement = null ;

	/**
	 * 検索結果を保持する変数。
	 */
	protected ResultSet wResultSet = null ;
	
	/**
	 * データベース接続用のConnectionインスタンス。
	 * トランザクション管理は、このクラスの中では行わない。
	 */
	protected Connection wConn ;

	/**
	 * 検索条件の保管を行う。
	 */
	protected SearchKey wSkey;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:09 $") ;
	}

	// Constructors --------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public IdmStockFinder(Connection conn)
	{
		setConnection(conn) ;
	}

	// Public methods ------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>を設定します。
	 * @param conn 設定するConnection
	 */
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}
	
	/**
	 * ステートメントを生成し、カーソルをオープンします。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public void open() throws ReadWriteException
	{
		try
		{
			wStatement = wConn.createStatement(
												ResultSet.TYPE_SCROLL_INSENSITIVE, 
											  	ResultSet.CONCUR_READ_ONLY
										      ) ;
		}
		catch (SQLException e)
		{
			//6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "StockFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + Integer.toString(e.getErrorCode()))) ;
		}
	}

	/**
	 * データベースの検索結果をエンティティ配列にして返します。
	 * @param start 検索結果の指定された開始位置
	 * @param end 検索結果の指定された終了位置
	 * @return エンティティ配列
 	 * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
	 */
	public Entity[] getEntities(int start, int end) throws ReadWriteException
	{
		Vector	vec	= new Vector();
		Timestamp tims = null; 
		Stock[] Stock = null;

		boolean wAllflag = false ;
		
		try {
			// 取得条件の有無を判定する。
			// 取得条件が未指定時は、全項目を取得する。
			
			String wStr = wSkey.getCollectCondition();
			
			if (wStr != null)
			{
				wAllflag = true ;
			}
			// 表示件数
			int count = end - start;
			if (wResultSet.absolute(start+1))
			{
				for (int i = 0; i < count; i++)
				{
					if(i > 0)
					{
						wResultSet.next();
					}

					Stock vStock = new Stock() ;
					if (wAllflag == false)
					{
						vStock.setStockId(DBFormat.replace(wResultSet.getString("STOCK_ID")));
						vStock.setPlanUkey(DBFormat.replace(wResultSet.getString("PLAN_UKEY")));
						vStock.setAreaNo(DBFormat.replace(wResultSet.getString("AREA_NO")));
						vStock.setLocationNo(DBFormat.replace(wResultSet.getString("LOCATION_NO")));
						vStock.setItemCode(DBFormat.replace(wResultSet.getString("ITEM_CODE")));
						vStock.setItemName1(DBFormat.replace(wResultSet.getString("ITEM_NAME1")));
						vStock.setStatusFlag(DBFormat.replace(wResultSet.getString("STATUS_FLAG")));
						vStock.setStockQty(wResultSet.getInt("STOCK_QTY"));
						vStock.setAllocationQty(wResultSet.getInt("ALLOCATION_QTY"));
						vStock.setPlanQty(wResultSet.getInt("PLAN_QTY"));
						vStock.setCasePieceFlag(DBFormat.replace(wResultSet.getString("CASE_PIECE_FLAG")));

						tims = wResultSet.getTimestamp("INSTOCK_DATE") ;
						if(tims!= null)
						{
							vStock.setInstockDate(new java.util.Date(tims.getTime())) ;
						}
						
						vStock.setLastShippingDate(DBFormat.replace(wResultSet.getString("LAST_SHIPPING_DATE")));
						vStock.setUseByDate(DBFormat.replace(wResultSet.getString("USE_BY_DATE")));
						vStock.setLotNo(DBFormat.replace(wResultSet.getString("LOT_NO")));
						vStock.setPlanInformation(DBFormat.replace(wResultSet.getString("PLAN_INFORMATION")));
						vStock.setConsignorCode(DBFormat.replace(wResultSet.getString("CONSIGNOR_CODE")));
						vStock.setConsignorName(DBFormat.replace(wResultSet.getString("CONSIGNOR_NAME")));
						vStock.setSupplierCode(DBFormat.replace(wResultSet.getString("SUPPLIER_CODE")));
						vStock.setSupplierName1(DBFormat.replace(wResultSet.getString("SUPPLIER_NAME1")));
						vStock.setEnteringQty(wResultSet.getInt("ENTERING_QTY"));
						vStock.setBundleEnteringQty(wResultSet.getInt("BUNDLE_ENTERING_QTY"));
						vStock.setItf(DBFormat.replace(wResultSet.getString("ITF")));
						vStock.setBundleItf(DBFormat.replace(wResultSet.getString("BUNDLE_ITF")));
						tims = wResultSet.getTimestamp("REGIST_DATE");
						if (tims != null)
						{
							vStock.setRegistDate(new java.util.Date(tims.getTime()));
						}
						vStock.setRegistPname(DBFormat.replace(wResultSet.getString("REGIST_PNAME")));
						tims = wResultSet.getTimestamp("LAST_UPDATE_DATE");
						if (tims != null)
						{
							vStock.setLastUpdateDate(new java.util.Date(tims.getTime()));
						}
						vStock.setLastUpdatePname(DBFormat.replace(wResultSet.getString("LAST_UPDATE_PNAME")));
					
					} else {
						if (wSkey.checkCollection("STOCK_ID") == true)
						{
							vStock.setStockId(DBFormat.replace(wResultSet.getString("STOCK_ID")));
						}
						if (wSkey.checkCollection("PLAN_UKEY") == true)
						{
							vStock.setPlanUkey(DBFormat.replace(wResultSet.getString("PLAN_UKEY")));
						}
						if (wSkey.checkCollection("AREA_NO") == true)
						{
							vStock.setAreaNo(DBFormat.replace(wResultSet.getString("AREA_NO")));
						}
						if (wSkey.checkCollection("LOCATION_NO") == true)
						{
							vStock.setLocationNo(DBFormat.replace(wResultSet.getString("LOCATION_NO")));
						}
						if (wSkey.checkCollection("ITEM_CODE") == true)
						{
							vStock.setItemCode(DBFormat.replace(wResultSet.getString("ITEM_CODE")));
						}
						if (wSkey.checkCollection("ITEM_NAME1") == true)
						{
							vStock.setItemName1(DBFormat.replace(wResultSet.getString("ITEM_NAME1")));
						}
						if (wSkey.checkCollection("STATUS_FLAG") == true)
						{					
							vStock.setStatusFlag(DBFormat.replace(wResultSet.getString("STATUS_FLAG")));
						}
						if (wSkey.checkCollection("STOCK_QTY") == true)
						{					
							vStock.setStockQty(wResultSet.getInt("STOCK_QTY"));
						}
						if (wSkey.checkCollection("ALLOCATION_QTY") == true)
						{					
							vStock.setAllocationQty(wResultSet.getInt("ALLOCATION_QTY"));
						}
						if (wSkey.checkCollection("PLAN_QTY") == true)
						{					
							vStock.setPlanQty(wResultSet.getInt("PLAN_QTY"));
						}
						if (wSkey.checkCollection("CASE_PIECE_FLAG") == true)
						{
							vStock.setCasePieceFlag(DBFormat.replace(wResultSet.getString("CASE_PIECE_FLAG")));
						}					
						if (wSkey.checkCollection("INSTOCK_DATE") == true)
						{
							tims = wResultSet.getTimestamp("INSTOCK_DATE") ;
							if(tims!= null)
							{
								vStock.setInstockDate(new java.util.Date(tims.getTime())) ;
							}
						}					
						if (wSkey.checkCollection("LAST_SHIPPING_DATE") == true)
						{
							vStock.setLastShippingDate(DBFormat.replace(wResultSet.getString("LAST_SHIPPING_DATE")));
						}					
						if (wSkey.checkCollection("USE_BY_DATE") == true)
						{
							vStock.setUseByDate(DBFormat.replace(wResultSet.getString("USE_BY_DATE")));
						}					
						if (wSkey.checkCollection("LOT_NO") == true)
						{
							vStock.setLotNo(DBFormat.replace(wResultSet.getString("LOT_NO")));
						}					
						if (wSkey.checkCollection("PLAN_INFORMATION") == true)
						{					
							vStock.setPlanInformation(DBFormat.replace(wResultSet.getString("PLAN_INFORMATION")));
						}
						if (wSkey.checkCollection("CONSIGNOR_CODE") == true)
						{					
							vStock.setConsignorCode(DBFormat.replace(wResultSet.getString("CONSIGNOR_CODE")));
						}
						if (wSkey.checkCollection("CONSIGNOR_NAME") == true)
						{					
							vStock.setConsignorName(DBFormat.replace(wResultSet.getString("CONSIGNOR_NAME")));
						}
						if (wSkey.checkCollection("SUPPLIER_CODE") == true)
						{					
							vStock.setSupplierCode(DBFormat.replace(wResultSet.getString("SUPPLIER_CODE")));
						}
						if (wSkey.checkCollection("SUPPLIER_NAME1") == true)
						{					
							vStock.setSupplierName1(DBFormat.replace(wResultSet.getString("SUPPLIER_NAME1")));
						}
						if (wSkey.checkCollection("ENTERING_QTY") == true)
						{					
							vStock.setEnteringQty(wResultSet.getInt("ENTERING_QTY"));
						}
						if (wSkey.checkCollection("BUNDLE_ENTERING_QTY") == true)
						{					
							vStock.setBundleEnteringQty(wResultSet.getInt("BUNDLE_ENTERING_QTY"));
						}
						if (wSkey.checkCollection("ITF") == true)
						{					
							vStock.setItf(DBFormat.replace(wResultSet.getString("ITF")));
						}
						if (wSkey.checkCollection("BUNDLE_ITF") == true)
						{					
							vStock.setBundleItf(DBFormat.replace(wResultSet.getString("BUNDLE_ITF")));
						}
						if (wSkey.checkCollection("REGIST_DATE") == true)
						{					
							tims = wResultSet.getTimestamp("REGIST_DATE");
							if (tims != null)
							{
								vStock.setRegistDate(new java.util.Date(tims.getTime()));
							}
						}
						if (wSkey.checkCollection("REGIST_PNAME") == true)
						{					
							vStock.setRegistPname(DBFormat.replace(wResultSet.getString("REGIST_PNAME")));
						}
						if (wSkey.checkCollection("LAST_UPDATE_DATE") == true)
						{					
							tims = wResultSet.getTimestamp("LAST_UPDATE_DATE");
							if (tims != null)
							{
								vStock.setLastUpdateDate(new java.util.Date(tims.getTime()));
							}
						}
						if (wSkey.checkCollection("LAST_UPDATE_PNAME") == true)
						{					
							vStock.setLastUpdatePname(DBFormat.replace(wResultSet.getString("LAST_UPDATE_PNAME")));
						}
					}
					vec.addElement(vStock);
				}
				Stock = new Stock[vec.size()];
				vec.copyInto(Stock);
			}
			else
			{
				// 指定された行が正しくありません。
				RmiMsgLogClient.write(6006010, LogMessage.F_ERROR, "StockFinder", null);
				throw new ReadWriteException("6006010");
			}
		}
		catch (InvalidStatusException e)
		{
			// 6006002 = {0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), "StockFinder");
			// ReadWriteExceptionthrow
			// 6006039 = {0}
			throw (new ReadWriteException("6006039" + wDelim + "DnStock"));
		}
		catch (SQLException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "StockFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DnStock")) ;
		}
		return Stock;
	}

	/**
	 * ステートメントをクローズします。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 */
	public void close() throws ReadWriteException
	{
		try
		{
			if (wResultSet != null) { wResultSet.close();  wResultSet = null; }
			if (wStatement != null) { wStatement.close();  wStatement = null; }
		}
		catch (SQLException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), "StockFinder");
			throw (new ReadWriteException("6006002" + wDelim + "DnStock"));
		}
	}


	/**
	 * Stockを検索し、取得します。
	 * @param key 検索のためのKey
	 * @return 検索結果の件数
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 */
	public int search(SearchKey key) throws ReadWriteException
	{
		Object[]  fmtObj        = new Object[4] ;
		Object[] cntObj = new Object[4];
		int count = 0;
		ResultSet countret  = null ;
		
	 	try
	 	{
			// 検索条件の保管を行う。
			wSkey = key ;						
			
			String fmtCountSQL = "SELECT COUNT({0}) COUNT " 
				+ "FROM DMSTOCK, DMLOCATE  "
				+ "{1} {2} {3} ";

			String fmtSQL = "SELECT {0} "
				+ "FROM DMSTOCK, DMLOCATE  "
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
DEBUG.MSG("HANDLER", "Stock Finder COUNT SQL[" + sqlcountstring + "]") ;
			countret = wStatement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			// 件数がMAXDISP以下の場合にのみ検索を実行します
			if ( count <= MAXDISP )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "Stock Finder SQL[" + sqlstring + "]") ;
				wResultSet = wStatement.executeQuery(sqlstring);
			}
			else
			{
				wResultSet = null;
			}
		}
		catch (SQLException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "StockFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DMSTOCK")) ;
		}
		return count;

	}


	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

