//$Id: ShippingHostSendViewFinder.java,v 1.1.1.1 2006/08/17 09:34:27 mori Exp $
package jp.co.daifuku.wms.shipping.dbhandler ;

/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;
import java.sql.Timestamp;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DEBUG;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.SearchKey;
import jp.co.daifuku.wms.base.entity.HostSendView;
import jp.co.daifuku.wms.base.dbhandler.HostSendViewReportFinder;

/**
 * データベースからHostSend表を検索しHostSendViewにマッピングするためのクラスです。
 * 画面に検索結果を一覧表示する場合このクラスを使用します。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/03/11</TD><TD>kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:27 $
 * @author  $Author: mori $
 */
public class ShippingHostSendViewFinder extends HostSendViewReportFinder
{
	// Class filelds -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:27 $") ;
	}

	// Class valiable ------------------------------------------------
	/**
	 *  このクラス内で一時的に検索条件を保持するための変数
	 */
	private SearchKey wSkey ;
	
	// Constructors --------------------------------------------------
	/**
	 * データベース接続用の<code>Connection</code>を指定して、インスタンスを生成します。
	 * @param conn データベース接続用 Connection
	 */
	public ShippingHostSendViewFinder(Connection conn)
	{
		super(conn);
	}

	// Public methods ------------------------------------------------

	/**
	 * データベースの検索結果をエンティティ配列にして返します。
	 * @param count 最大取得件数
	 * @return エンティティ配列
 	 * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
	 */
	public Entity[] getEntityes(int count) throws ReadWriteException
	{
		Vector vec = new Vector();
		HostSendView[] resultViewTemp = null;
		Timestamp tims = null;
		boolean wNotAllflag = false;

		try
		{
			// 取得条件の有無を判定する。
			// 取得条件が未指定時は、全項目を取得する。
			if (wSkey.getCollectCondition() != null)
			{
				wNotAllflag = true;
			}
			// 今回読み込んだ件数をカウントする。
			int readCount = 0;
			// ResultSetをクローズするかどうかのフラグ。最終行を読み込んだ後にクローズする。
			boolean endFlag = true;
			while (p_ResultSet.next())
			{

				HostSendView vResult = new HostSendView();

				if (!wNotAllflag)
				{
					vResult.setWorkDate(DBFormat.replace(p_ResultSet.getString("WORK_DATE")));
					vResult.setJobNo(DBFormat.replace(p_ResultSet.getString("JOB_NO")));
					vResult.setJobType(DBFormat.replace(p_ResultSet.getString("JOB_TYPE")));
					vResult.setCollectJobNo(DBFormat.replace(p_ResultSet.getString("COLLECT_JOB_NO")));
					vResult.setStatusFlag(DBFormat.replace(p_ResultSet.getString("STATUS_FLAG")));
					vResult.setBeginningFlag(DBFormat.replace(p_ResultSet.getString("BEGINNING_FLAG")));
					vResult.setPlanUkey(DBFormat.replace(p_ResultSet.getString("PLAN_UKEY")));
					vResult.setStockId(DBFormat.replace(p_ResultSet.getString("STOCK_ID")));
					vResult.setAreaNo(DBFormat.replace(p_ResultSet.getString("AREA_NO")));
					vResult.setLocationNo(DBFormat.replace(p_ResultSet.getString("LOCATION_NO")));
					vResult.setPlanDate(DBFormat.replace(p_ResultSet.getString("PLAN_DATE")));
					vResult.setConsignorCode(DBFormat.replace(p_ResultSet.getString("CONSIGNOR_CODE")));
					vResult.setConsignorName(DBFormat.replace(p_ResultSet.getString("CONSIGNOR_NAME")));
					vResult.setSupplierCode(DBFormat.replace(p_ResultSet.getString("SUPPLIER_CODE")));
					vResult.setSupplierName1(DBFormat.replace(p_ResultSet.getString("SUPPLIER_NAME1")));
					vResult.setInstockTicketNo(DBFormat.replace(p_ResultSet.getString("INSTOCK_TICKET_NO")));
					vResult.setInstockLineNo(p_ResultSet.getInt("INSTOCK_LINE_NO"));
					vResult.setCustomerCode(DBFormat.replace(p_ResultSet.getString("CUSTOMER_CODE")));
					vResult.setCustomerName1(DBFormat.replace(p_ResultSet.getString("CUSTOMER_NAME1")));
					vResult.setShippingTicketNo(DBFormat.replace(p_ResultSet.getString("SHIPPING_TICKET_NO")));
					vResult.setShippingLineNo(p_ResultSet.getInt("SHIPPING_LINE_NO"));
					vResult.setItemCode(DBFormat.replace(p_ResultSet.getString("ITEM_CODE")));
					vResult.setItemName1(DBFormat.replace(p_ResultSet.getString("ITEM_NAME1")));
					vResult.setHostPlanQty(p_ResultSet.getInt("HOST_PLAN_QTY"));
					vResult.setPlanQty(p_ResultSet.getInt("PLAN_QTY"));
					vResult.setPlanEnableQty(p_ResultSet.getInt("PLAN_ENABLE_QTY"));
					vResult.setResultQty(p_ResultSet.getInt("RESULT_QTY"));
					vResult.setShortageCnt(p_ResultSet.getInt("SHORTAGE_CNT"));
					vResult.setPendingQty(p_ResultSet.getInt("PENDING_QTY"));
					vResult.setEnteringQty(p_ResultSet.getInt("ENTERING_QTY"));
					vResult.setBundleEnteringQty(p_ResultSet.getInt("BUNDLE_ENTERING_QTY"));
					vResult.setCasePieceFlag(DBFormat.replace(p_ResultSet.getString("CASE_PIECE_FLAG")));
					vResult.setWorkFormFlag(DBFormat.replace(p_ResultSet.getString("WORK_FORM_FLAG")));
					vResult.setItf(DBFormat.replace(p_ResultSet.getString("ITF")));
					vResult.setBundleItf(DBFormat.replace(p_ResultSet.getString("BUNDLE_ITF")));
					vResult.setTcdcFlag(DBFormat.replace(p_ResultSet.getString("TCDC_FLAG")));
					vResult.setUseByDate(DBFormat.replace(p_ResultSet.getString("USE_BY_DATE")));
					vResult.setPlanInformation(DBFormat.replace(p_ResultSet.getString("PLAN_INFORMATION")));
					vResult.setOrderNo(DBFormat.replace(p_ResultSet.getString("ORDER_NO")));
					vResult.setOrderingDate(DBFormat.replace(p_ResultSet.getString("ORDERING_DATE")));
					vResult.setBatchNo(DBFormat.replace(p_ResultSet.getString("BATCH_NO")));
					tims = p_ResultSet.getTimestamp("REGIST_DATE");
					if (tims != null)
					{
						vResult.setRegistDate(new java.util.Date(tims.getTime()));
					}
					tims = p_ResultSet.getTimestamp("LAST_UPDATE_DATE");
					if (tims != null)
					{
						vResult.setLastUpdateDate(new java.util.Date(tims.getTime()));
					}
				}
				else
				{
					if (wSkey.checkCollection("WORK_DATE") == true)
					{
						vResult.setWorkDate(DBFormat.replace(p_ResultSet.getString("WORK_DATE")));
					}
					if (wSkey.checkCollection("JOB_NO") == true)
					{
						vResult.setJobNo(DBFormat.replace(p_ResultSet.getString("JOB_NO")));
					}
					if (wSkey.checkCollection("JOB_TYPE") == true)
					{
						vResult.setJobType(DBFormat.replace(p_ResultSet.getString("JOB_TYPE")));
					}
					if (wSkey.checkCollection("COLLECT_JOB_NO") == true)
					{
						vResult.setCollectJobNo(DBFormat.replace(p_ResultSet.getString("COLLECT_JOB_NO")));
					}
					if (wSkey.checkCollection("STATUS_FLAG") == true)
					{
						vResult.setStatusFlag(DBFormat.replace(p_ResultSet.getString("STATUS_FLAG")));
					}
					if (wSkey.checkCollection("BEGINNING_FLAG") == true)
					{
						vResult.setBeginningFlag(DBFormat.replace(p_ResultSet.getString("BEGINNING_FLAG")));
					}
					if (wSkey.checkCollection("PLAN_UKEY") == true)
					{
						vResult.setPlanUkey(DBFormat.replace(p_ResultSet.getString("PLAN_UKEY")));
					}
					if (wSkey.checkCollection("STOCK_ID") == true)
					{
						vResult.setStockId(DBFormat.replace(p_ResultSet.getString("STOCK_ID")));
					}
					if (wSkey.checkCollection("AREA_NO") == true)
					{
						vResult.setAreaNo(DBFormat.replace(p_ResultSet.getString("AREA_NO")));
					}
					if (wSkey.checkCollection("LOCATION_NO") == true)
					{
						vResult.setLocationNo(DBFormat.replace(p_ResultSet.getString("LOCATION_NO")));
					}
					if (wSkey.checkCollection("PLAN_DATE") == true)
					{
						vResult.setPlanDate(DBFormat.replace(p_ResultSet.getString("PLAN_DATE")));
					}
					if (wSkey.checkCollection("CONSIGNOR_CODE") == true)
					{
						vResult.setConsignorCode(DBFormat.replace(p_ResultSet.getString("CONSIGNOR_CODE")));
					}
					if (wSkey.checkCollection("CONSIGNOR_NAME") == true)
					{
						vResult.setConsignorName(DBFormat.replace(p_ResultSet.getString("CONSIGNOR_NAME")));
					}
					if (wSkey.checkCollection("SUPPLIER_CODE") == true)
					{
						vResult.setSupplierCode(DBFormat.replace(p_ResultSet.getString("SUPPLIER_CODE")));
					}
					if (wSkey.checkCollection("SUPPLIER_NAME1") == true)
					{
						vResult.setSupplierName1(DBFormat.replace(p_ResultSet.getString("SUPPLIER_NAME1")));
					}
					if (wSkey.checkCollection("INSTOCK_TICKET_NO") == true)
					{
						vResult.setInstockTicketNo(DBFormat.replace(p_ResultSet.getString("INSTOCK_TICKET_NO")));
					}
					if (wSkey.checkCollection("INSTOCK_LINE_NO") == true)
					{
						vResult.setInstockLineNo(p_ResultSet.getInt("INSTOCK_LINE_NO"));
					}
					if (wSkey.checkCollection("CUSTOMER_CODE") == true)
					{
						vResult.setCustomerCode(DBFormat.replace(p_ResultSet.getString("CUSTOMER_CODE")));
					}
					if (wSkey.checkCollection("CUSTOMER_NAME1") == true)
					{
						vResult.setCustomerName1(DBFormat.replace(p_ResultSet.getString("CUSTOMER_NAME1")));
					}
					if (wSkey.checkCollection("SHIPPING_TICKET_NO") == true)
					{
						vResult.setShippingTicketNo(DBFormat.replace(p_ResultSet.getString("SHIPPING_TICKET_NO")));
					}
					if (wSkey.checkCollection("SHIPPING_LINE_NO") == true)
					{
						vResult.setShippingLineNo(p_ResultSet.getInt("SHIPPING_LINE_NO"));
					}
					if (wSkey.checkCollection("ITEM_CODE") == true)
					{
						vResult.setItemCode(DBFormat.replace(p_ResultSet.getString("ITEM_CODE")));
					}
					if (wSkey.checkCollection("ITEM_NAME1") == true)
					{
						vResult.setItemName1(DBFormat.replace(p_ResultSet.getString("ITEM_NAME1")));
					}
					if (wSkey.checkCollection("HOST_PLAN_QTY") == true)
					{
						vResult.setHostPlanQty(p_ResultSet.getInt("HOST_PLAN_QTY"));
					}
					if (wSkey.checkCollection("PLAN_QTY") == true)
					{
						vResult.setPlanQty(p_ResultSet.getInt("PLAN_QTY"));
					}
					if (wSkey.checkCollection("PLAN_ENABLE_QTY") == true)
					{
						vResult.setPlanEnableQty(p_ResultSet.getInt("PLAN_ENABLE_QTY"));
					}
					if (wSkey.checkCollection("RESULT_QTY") == true)
					{
						vResult.setResultQty(p_ResultSet.getInt("RESULT_QTY"));
					}
					if (wSkey.checkCollection("SHORTAGE_CNT") == true)
					{
						vResult.setShortageCnt(p_ResultSet.getInt("SHORTAGE_CNT"));
					}
					if (wSkey.checkCollection("PENDING_QTY") == true)
					{
						vResult.setPendingQty(p_ResultSet.getInt("PENDING_QTY"));
					}
					if (wSkey.checkCollection("ENTERING_QTY") == true)
					{
						vResult.setEnteringQty(p_ResultSet.getInt("ENTERING_QTY"));
					}
					if (wSkey.checkCollection("BUNDLE_ENTERING_QTY") == true)
					{
						vResult.setBundleEnteringQty(p_ResultSet.getInt("BUNDLE_ENTERING_QTY"));
					}
					if (wSkey.checkCollection("CASE_PIECE_FLAG") == true)
					{
						vResult.setCasePieceFlag(DBFormat.replace(p_ResultSet.getString("CASE_PIECE_FLAG")));
					}
					if (wSkey.checkCollection("WORK_FORM_FLAG") == true)
					{
						vResult.setWorkFormFlag(DBFormat.replace(p_ResultSet.getString("WORK_FORM_FLAG")));
					}
					if (wSkey.checkCollection("ITF") == true)
					{
						vResult.setItf(DBFormat.replace(p_ResultSet.getString("ITF")));
					}
					if (wSkey.checkCollection("BUNDLE_ITF") == true)
					{
						vResult.setBundleItf(DBFormat.replace(p_ResultSet.getString("BUNDLE_ITF")));
					}
					if (wSkey.checkCollection("TCDC_FLAG") == true)
					{
						vResult.setTcdcFlag(DBFormat.replace(p_ResultSet.getString("TCDC_FLAG")));
					}
					if (wSkey.checkCollection("USE_BY_DATE") == true)
					{
						vResult.setUseByDate(DBFormat.replace(p_ResultSet.getString("USE_BY_DATE")));
					}
					if (wSkey.checkCollection("PLAN_INFORMATION") == true)
					{
						vResult.setPlanInformation(DBFormat.replace(p_ResultSet.getString("PLAN_INFORMATION")));
					}
					if (wSkey.checkCollection("ORDER_NO") == true)
					{
						vResult.setOrderNo(DBFormat.replace(p_ResultSet.getString("ORDER_NO")));
					}
					if (wSkey.checkCollection("ORDERING_DATE") == true)
					{
						vResult.setOrderingDate(DBFormat.replace(p_ResultSet.getString("ORDERING_DATE")));
					}
					if (wSkey.checkCollection("BATCH_NO") == true)
					{
						vResult.setBatchNo(DBFormat.replace(p_ResultSet.getString("BATCH_NO")));
					}
					if (wSkey.checkCollection("REGIST_DATE") == true)
					{
						tims = p_ResultSet.getTimestamp("REGIST_DATE");
						if (tims != null)
						{
							vResult.setRegistDate(new java.util.Date(tims.getTime()));
						}
					}
					if (wSkey.checkCollection("LAST_UPDATE_DATE") == true)
					{
						tims = p_ResultSet.getTimestamp("LAST_UPDATE_DATE");
						if (tims != null)
						{
							vResult.setLastUpdateDate(new java.util.Date(tims.getTime()));
						}
					}
				}

				vec.addElement(vResult);
				readCount++;
				if (count <= readCount)
				{
					endFlag = false;
					break;
				}

			}

			// 最終行を読み込んだ後はクローズする。
			if (endFlag)
			{
				isNextFlag = false ;
				close();
			}

			resultViewTemp = new HostSendView[vec.size()];
			vec.copyInto(resultViewTemp);
		}
		catch (InvalidStatusException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			throw (new ReadWriteException("6006002" + wDelim + "HostSendView"));
		}
		catch (SQLException e)
		{
			// 6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
			throw (new ReadWriteException("6006002" + wDelim + "HostSendView"));
		}

		return resultViewTemp;
	}

	/**
	 * HostSendを検索し、取得します。
	 * @param key 検索のためのKey
	 * @return 検索結果の件数
	 * @throws ReadWriteException データベースとの接続で発生した例外をそのまま通知します。
	 */
	public int search(ShippingHostSendViewSearchKey key, String sql) throws ReadWriteException
	{
		close();
		open();
		Object[]  fmtObj = new Object[5];
		Object[]  cntObj = new Object[5];
		int count = 0;
		ResultSet countret  = null ;

	 	try
	 	{
			// 検索条件の保管を行う。
			wSkey = key ;
			
			String fmtCountSQL = "SELECT COUNT({0}) COUNT FROM DVHOSTSENDVIEW {1} {2} {3} {4} ";

			String fmtSQL = "SELECT DISTINCT {0} ";
			fmtSQL += "FROM DVHOSTSENDVIEW " + "{1} {2} {3} {4}";

			// 	取得条件を編集する。(COUNT用)
			// 全項目取得とする。
			cntObj[0] = " * " ;

			// 取得条件を編集する。
			if (key.getCollectCondition() != null)
			{
				fmtObj[0] = key.getCollectCondition() ;
				fmtObj[0] = fmtObj[0] + ", " + key.ReferenceJoinColumns() ;
			} else {
				// 取得条件に指定なし時、全項目取得とする。
				fmtObj[0] = " * " ;
			}

			// 結合テーブルを編集する。
			if(!StringUtil.isBlank(key.getJoinTable()))
			{
				if(StringUtil.isBlank(sql))
				{
					fmtObj[1] = ", " + key.getJoinTable();
					cntObj[1] = ", " + key.getJoinTable();
				}
				else
				{
					fmtObj[1] = ", ( " + sql + " ) " + key.getJoinTable();
					cntObj[1] = ", ( " + sql + " ) " + key.getJoinTable();
				}
			}

			fmtObj[2] = "";
			// 検索条件を編集する。			
			if (key.getReferenceCondition() != null)
			{
				fmtObj[2] = "WHERE " + key.getReferenceCondition();
				cntObj[2] = "WHERE " + key.getReferenceCondition();
			}

			// 結合条件を編集する。
			if (key.ReferenceJoinWhere() != null)
			{
				if(StringUtil.isBlank(fmtObj[2].toString()))
				{
					fmtObj[2] = " WHERE " + key.ReferenceJoinWhere();
					cntObj[2] = " WHERE " + key.ReferenceJoinWhere();
				}
				else
				{
					fmtObj[2] = fmtObj[2] + " AND " + key.ReferenceJoinWhere();
					cntObj[2] = cntObj[2] + " AND " + key.ReferenceJoinWhere();
				}
			}

			// 集約条件を編集する。
			if (key.getGroupCondition() != null)
			{
				fmtObj[3] = " GROUP BY " + key.getGroupCondition();
				cntObj[3] = " GROUP BY " + key.getGroupCondition();
			}

			// 読込み順を編集する。
			if (key.getSortCondition() != null)
			{
				fmtObj[4] = " ORDER BY " + key.getSortCondition();
				cntObj[4] = " ORDER BY " + key.getSortCondition();
			}

			String sqlcountstring = SimpleFormat.format(fmtCountSQL, cntObj) ;
DEBUG.MSG("HANDLER", "ShippingHostSendView Finder COUNT SQL[" + sqlcountstring + "]") ;
			countret = p_Statement.executeQuery(sqlcountstring);
			while (countret.next())
			{
				count = countret.getInt("COUNT");
			}
			//１件以上の場合にのみ検索を実行します
			if ( count > 0 )
			{
				String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
DEBUG.MSG("HANDLER", "ShippingHostSendView Finder SQL[" + sqlstring + "]") ;
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
			//6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "ShippngHostSendViewFinder" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "DvHostSendView")) ;
		}
		return count;
	}

	/**
	 * HostSendViewを検索するSQL文を作成します。
	 * @param key 検索のためのKey
	 * @return 作成されたSQL文
	 * @throws ReadWriteException SQL文の作成条件にて障害発生した場合に通知されます。
	 */
	public String createFindSql(SearchKey key) throws ReadWriteException
	{
		Object[] fmtObj = new Object[4];

		String fmtSQL = "SELECT {0} ";
		fmtSQL += "FROM DVHOSTSENDVIEW " + "{1} {2} {3}";

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
			fmtObj[1] = " WHERE " + key.getReferenceCondition();
		}

		// 集約条件を編集する。
		if (key.getGroupCondition() != null)
		{
			fmtObj[2] = " GROUP BY " + key.getGroupCondition();
		}

		// 読込み順を編集する。
		if (key.getSortCondition() != null)
		{
			fmtObj[3] = " ORDER BY " + key.getSortCondition();
		}
		String sqlstring = SimpleFormat.format(fmtSQL, fmtObj);

		return sqlstring;
	}

	/**
	 * ステートメントをクローズします。
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知します。
	 */
	public void close() throws ReadWriteException
	{
		try
		{
			// クローズすると言うことは次にResultSetから読み込まないと言うことなのでisNextFlagをFalseにする。
			isNextFlag = false;
			if (p_ResultSet != null) 
			{
				p_ResultSet.close();
				p_ResultSet = null;
			}
			if (p_Statement != null) 
			{
				p_Statement.close();
				p_Statement = null;
			}
		}
		catch (Exception e)
		{
			//6006002 = データベースエラーが発生しました。{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), this.getClass().getName() ) ;
			//ここで、ReadWriteExceptionをエラーメッセージ付きでthrowする。
			throw (new ReadWriteException("6006002" + wDelim + "DvHostSendView"));
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

