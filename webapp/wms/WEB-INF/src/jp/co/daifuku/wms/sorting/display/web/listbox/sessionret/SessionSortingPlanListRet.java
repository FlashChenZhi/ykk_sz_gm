// $Id: SessionSortingPlanListRet.java,v 1.1.1.1 2006/08/17 09:34:32 mori Exp $
package jp.co.daifuku.wms.sorting.display.web.listbox.sessionret;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * 仕分予定情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionSortingPlanListRet(Connection, SortingParameter)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(SortingParameter)</CODE>メソッドを呼び出し仕分予定情報の検索を行います。<BR>
 * <BR>
 *   [検索条件]*必須項目
 *   <DIR>
 *     荷主コード* <BR>
 *     仕分予定日* <BR>
 *     商品コード <BR>
 *     ケースピース区分* <BR>
 *     クロス／ＤＣ* <BR>
 *     作業状態* <BR>
 *   </DIR>
 *   ＜検索テーブル＞
 *   <DIR>
 *     DnSortingPlan<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を<CODE>SortingParameter</CODE>配列にセットし返します。<BR>
 * <BR>
 *   [表示項目]
 *   <DIR>
 *     荷主ｺｰﾄﾞ <BR>
 *     荷主名称 <BR>
 *     仕分予定日 <BR>
 *     商品ｺｰﾄﾞ	<BR>
 *     商品名称	<BR>
 *     ｹｰｽ/ﾋﾟｰｽ<BR>
 *     ｸﾛｽ/DC <BR>
 *     出荷先ｺｰﾄﾞ<BR>
 *     出荷先名称<BR>
 *     ｹｰｽ仕分場所 <BR>
 *     ﾋﾟｰｽ仕分場所 <BR>
 *     ｹｰｽ入数 <BR>
 *     ﾎﾞｰﾙ入数	<BR>
 *     ﾎｽﾄ予定ｹｰｽ数 <BR>
 *     ﾎｽﾄ予定ﾋﾟｰｽ数 <BR>
 *     実績ｹｰｽ数 <BR>
 *     実績ﾋﾟｰｽ数 <BR>
 *     状態	<BR>
 *     ｹｰｽITF <BR>
 *     ﾎﾞｰﾙITF<BR>
 *     仕入先ｺｰﾄﾞ <BR>
 *     仕入先名称 <BR>
 *   </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/08</TD><TD>K.Mukai</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class SessionSortingPlanListRet extends SessionRet
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 荷主名称取得用
	 */
	private String wConsignorName = "";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:32 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * 検索を行うための<CODE>find(SortingParameter)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(SortingParameter)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      <code>SortingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionSortingPlanListRet(Connection conn, SortingParameter param) throws Exception
	{
		this.wConn = conn;

		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * 仕分予定情報の検索結果を、指定件数分返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.表示データを何件取得するかを指定するための計算を行います。<BR>
	 *   2.結果セットから仕分予定情報を取得します。<BR>
	 *   3.仕分予定情報から表示データを取得し<CODE>SortingParameter</CODE>にセットします。<BR>
	 *   3.表示情報を返します。<BR>
	 * </DIR>
	 * 
	 * @return 表示情報を含む<CODE>SortingParameter</CODE>クラス
	 */
	public SortingParameter[] getEntities()
	{
		SortingPlan[] sortingPlan = null;
		SortingParameter[] resultParam = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				// 検索結果を取得する。
				sortingPlan = (SortingPlan[]) ((SortingPlanFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				// SortingParameterにセットしなおす。
				resultParam = getDispData(sortingPlan);

			}
			catch (Exception e)
			{
				//エラーをログファイルに落とす
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return resultParam;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * パラメータから検索条件を取得し仕分予定情報の検索を行うためのメソッドです。<BR>
	 * 
	 * @param param   検索条件を取得するためのパラメータ
	 * @throws Exception 全ての例外を報告します。
	 */
	private void find(SortingParameter param) throws Exception
	{
		int count = 0;

		SortingPlanSearchKey planKey = new SortingPlanSearchKey();
		SortingPlanSearchKey consignorkey = new SortingPlanSearchKey();

		// 検索条件をセットする
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			planKey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		if (!StringUtil.isBlank(param.getPlanDate()))
		{
			planKey.setPlanDate(param.getPlanDate());
			consignorkey.setPlanDate(param.getPlanDate());
		}
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			planKey.setItemCode(param.getItemCode());
			consignorkey.setItemCode(param.getItemCode());
		}
		
		// ケース/ピース区分 全ての場合は何もセットしない
		if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
		{
			// ケース
			planKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_CASE);
			consignorkey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_CASE);
		}
		else if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
		{
			// ピース
			planKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_PIECE);
			consignorkey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_PIECE);
		}
		else if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_MIXED))
		{
			// ケース･ピース
			planKey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_MIX);
			consignorkey.setCasePieceFlag(SortingPlan.CASEPIECE_FLAG_MIX);
		}
		
		// クロス/DC 全ての場合は何もセットしない(作業区分が仕分なのでTCは存在しないはず)
		if (param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			// クロス
			planKey.setTcdcFlag(SortingPlan.TCDC_FLAG_CROSSTC);
			consignorkey.setTcdcFlag(SortingPlan.TCDC_FLAG_CROSSTC);
		}
		else if (param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
		{
			// DC
			planKey.setTcdcFlag(SortingPlan.TCDC_FLAG_DC);
			consignorkey.setTcdcFlag(SortingPlan.TCDC_FLAG_DC);
		}
		
		// 作業状態 全ての場合は削除以外をセット
		if (param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_UNSTARTED))
		{
			// 未開始
			planKey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);
			consignorkey.setStatusFlag(SortingPlan.STATUS_FLAG_UNSTART);
		}
		else if (param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_WORKING))
		{
			// 作業中
			planKey.setStatusFlag(SortingPlan.STATUS_FLAG_NOWWORKING);
			consignorkey.setStatusFlag(SortingPlan.STATUS_FLAG_NOWWORKING);
		}
		else if (param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION))
		{
			// 一部完了
			planKey.setStatusFlag(SortingPlan.STATUS_FLAG_COMPLETE_IN_PART);
			consignorkey.setStatusFlag(SortingPlan.STATUS_FLAG_COMPLETE_IN_PART);
		}
		else if (param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_COMPLETION))
		{
			// 完了
			planKey.setStatusFlag(SortingPlan.STATUS_FLAG_COMPLETION);
			consignorkey.setStatusFlag(SortingPlan.STATUS_FLAG_COMPLETION);
		}
		else
		{
			// 全て
			planKey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
			consignorkey.setStatusFlag(SortingPlan.STATUS_FLAG_DELETE, "!=");
		}
		
		// ソート順
		planKey.setItemCodeOrder(1, true);
		planKey.setCasePieceFlagOrder(2, true);
		planKey.setTcdcFlagOrder(3, false);
		planKey.setCustomerCodeOrder(4, true);
		planKey.setStatusFlagOrder(5, true);
		planKey.setSupplierCodeOrder(6, true);

		wFinder = new SortingPlanFinder(wConn);
		// カーソルオープン
		wFinder.open();
		// 検索を実行する
		count = ((SortingPlanFinder) wFinder).search(planKey);
		// 初期化
		wLength = count;
		wCurrent = 0;

		// 荷主名称を取得する
		consignorkey.setConsignorNameCollect("");
		consignorkey.setRegistDateOrder(1, false);

		SortingPlanFinder consignorFinder = new SortingPlanFinder(wConn);
		consignorFinder.open();
		int nameCount = consignorFinder.search(consignorkey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			SortingPlan sortplan[] = (SortingPlan[]) consignorFinder.getEntities(0, 1);

			if (sortplan != null && sortplan.length != 0)
			{
				wConsignorName = sortplan[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

	/**
	 * 仕分予定情報を<CODE>SortingParameter</CODE>にセットするためのクラスです。<BR>
	 * 
	 * @param winfo 仕分予定情報
	 * @return SortingParameter[] 作業情報をセットした<CODE>SortingParameter</CODE>クラス
	 */
	private SortingParameter[] getDispData(SortingPlan[] sortPlan)
	{
		SortingParameter[] sortPlanParam = null;

		Vector tempVec = new Vector();

		for (int i = 0; i < sortPlan.length; i++)
		{
			SortingParameter tempParam = new SortingParameter();

			tempParam.setConsignorCode(sortPlan[i].getConsignorCode());
			tempParam.setConsignorName(wConsignorName);
			tempParam.setPlanDate(sortPlan[i].getPlanDate());
			tempParam.setItemCode(sortPlan[i].getItemCode());
			tempParam.setItemName(sortPlan[i].getItemName1());
			tempParam.setCasePieceName(DisplayUtil.getPieceCaseValue(sortPlan[i].getCasePieceFlag()));
			tempParam.setTcdcName(DisplayUtil.getTcDcValue(sortPlan[i].getTcdcFlag()));
			tempParam.setCustomerCode(sortPlan[i].getCustomerCode());
			tempParam.setCustomerName(sortPlan[i].getCustomerName1());
			tempParam.setCaseSortingLocation(sortPlan[i].getCaseLocation());
			tempParam.setPieceSortingLocation(sortPlan[i].getPieceLocation());
			
			tempParam.setEnteringQty(sortPlan[i].getEnteringQty());
			tempParam.setBundleEnteringQty(sortPlan[i].getBundleEnteringQty());
			tempParam.setStatusName(DisplayUtil.getRetrievalPlanStatusValue(sortPlan[i].getStatusFlag()));
			tempParam.setITF(sortPlan[i].getItf());
			tempParam.setBundleITF(sortPlan[i].getBundleItf());			
			tempParam.setSupplierCode(sortPlan[i].getSupplierCode());
			tempParam.setSupplierName(sortPlan[i].getSupplierName1());
			
			// ホスト予定ケース数とホスト予定ピース数、実績ケース数と実績ピース数をセット
			if (sortPlan[i].getCasePieceFlag().equals(SortingPlan.CASEPIECE_FLAG_CASE) || sortPlan[i].getCasePieceFlag().equals(SortingPlan.CASEPIECE_FLAG_MIX))
			{	
				int entering_Qty = sortPlan[i].getEnteringQty();
				int planQty = sortPlan[i].getPlanQty();
				int resultQty = sortPlan[i].getResultQty();
				if (sortPlan[i].getEnteringQty() > 0)
				{	
					tempParam.setPlanCaseQty(planQty/entering_Qty);
					tempParam.setPlanPieceQty(planQty%entering_Qty);
					tempParam.setResultCaseQty(resultQty/entering_Qty);
					tempParam.setResultPieceQty(resultQty%entering_Qty);
				}
				else
				{
					tempParam.setPlanCaseQty(0);
					tempParam.setPlanPieceQty(sortPlan[i].getPlanQty());
					tempParam.setResultCaseQty(0);
					tempParam.setResultPieceQty(sortPlan[i].getResultQty());
				}
			}
			else
			{
				tempParam.setPlanCaseQty(0);
				tempParam.setPlanPieceQty(sortPlan[i].getPlanQty());
				tempParam.setResultCaseQty(0);
				tempParam.setResultPieceQty(sortPlan[i].getResultQty());
			}

			tempVec.add(tempParam);

		}
		sortPlanParam = new SortingParameter[tempVec.size()];
		tempVec.copyInto(sortPlanParam);

		return sortPlanParam;
	}

}
//end of class
