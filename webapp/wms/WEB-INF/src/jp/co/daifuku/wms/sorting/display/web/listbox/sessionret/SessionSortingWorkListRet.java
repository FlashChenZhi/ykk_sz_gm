// $Id: SessionSortingWorkListRet.java,v 1.1.1.1 2006/08/17 09:34:32 mori Exp $
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
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * 作業情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionSortingWorkListRet(Connection, SortingParameter)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(SortingParameter)</CODE>メソッドを呼び出し作業情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞*必須項目
 *   <DIR>
 *     作業区分：04(仕分)*<BR>
 *     荷主コード*<BR>
 *     仕分予定日<BR>
 *     商品コード<BR>
 *     ケースピース区分* <BR>
 *     クロス／ＤＣ* <BR>
 *   </DIR>
 *   ＜検索テーブル＞
 *   <DIR>
 *     DnWorkInfo<BR>
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
 *   ＜表示項目＞
 *   <DIR>
 *   <table>
 *     <tr><td>荷主ｺｰﾄﾞ</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td>荷主名称</td><td>：</td><td>ConsignorName</td></tr>
 *     <tr><td>仕分予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td>商品ｺｰﾄﾞ</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td>商品名称</td><td>：</td><td>ItemName</td></tr>
 *     <tr><td>ｹｰｽ/ﾋﾟｰｽ</td><td>：</td><td>CasePieceFlagName</td></tr>
 *     <tr><td>ｸﾛｽ/DC</td><td>：</td><td>TcDcName</td></tr>
 *     <tr><td>予定総数</td><td>：</td><td>TotalPlanQty</td></tr>
 *     <tr><td>ｹｰｽ入数</td><td>：</td><td>EnteringQty</td></tr>
 *     <tr><td>ﾎﾞｰﾙ入数</td><td>：</td><td>BundleEnteringQty</td></tr>
 *     <tr><td>予定ｹｰｽ数</td><td>：</td><td>PlanCaseQty</td></tr>
 *     <tr><td>予定ﾋﾟｰｽ数</td><td>：</td><td>PlanPieceQty</td></tr>
 *     <tr><td>仕分先数</td><td>：</td><td>仕分先数</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * 
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class SessionSortingWorkListRet extends SessionRet
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
	public SessionSortingWorkListRet(Connection conn, SortingParameter param) throws Exception
	{
		this.wConn = conn;

		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * 作業情報の検索結果を、指定件数分返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.表示データを何件取得するかを指定するための計算を行います。<BR>
	 *   2.結果セットから作業情報を取得します。<BR>
	 *   3.作業情報から表示データを取得し<CODE>SortingParameter</CODE>にセットします。<BR>
	 *   3.表示情報を返します。<BR>
	 * </DIR>
	 * 
	 * @return 表示情報を含む<CODE>SortingParameter</CODE>クラス
	 */
	public SortingParameter[] getEntities()
	{
		WorkingInformation[] workInfo = null;
		SortingParameter[] resultParam = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				// 検索結果を取得する。
				workInfo = (WorkingInformation[]) ((WorkingInformationFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				// SortingParameterにセットしなおす。
				resultParam = getDispData(workInfo);

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
	 * パラメータから検索条件を取得し作業情報の検索を行うためのメソッドです。<BR>
	 * 
	 * @param param   検索条件を取得するためのパラメータ
	 * @throws Exception 全ての例外を報告します。
	 */
	private void find(SortingParameter param) throws Exception
	{
		int count = 0;

		WorkingInformationSearchKey workKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey consignorkey = new WorkingInformationSearchKey();

		// 検索条件をセットする
		// 荷主コード
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			workKey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		// 仕分予定日
		if (!StringUtil.isBlank(param.getPlanDate()))
		{
			workKey.setPlanDate(param.getPlanDate());
			consignorkey.setPlanDate(param.getPlanDate());
		}
		// 商品コード
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			workKey.setItemCode(param.getItemCode());
			consignorkey.setItemCode(param.getItemCode());
		}
		// ケース/ピース区分　全ての場合は何もセットしない
		if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
		{
			// ケース
			workKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			consignorkey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
		}
		else if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
		{
			// ピース
			workKey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			consignorkey.setCasePieceFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
		}
		// クロス/DC　全ての場合は何もセットしない(作業区分が仕分なのでTCは存在しないはず)
		if (param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			// クロス
			workKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC);
			consignorkey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC);
		}
		else if (param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
		{
			// DC
			workKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			consignorkey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
		}
		// 作業状態(全て表示の場合は、削除以外)
		if (param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_UNSTARTED))
		{
			// 未開始
			workKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			consignorkey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
		}
		else if (param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_WORKING))
		{
			// 作業中
			workKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			consignorkey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		}
		else if (param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_COMPLETION))
		{
			// 完了
			workKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			consignorkey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
		}
		else if (param.getStatusFlag().equals(SortingParameter.STATUS_FLAG_ALL))
		{
			// 削除以外
			workKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
			consignorkey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		}
		// 作業区分：仕分
		workKey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		consignorkey.setJobType(WorkingInformation.JOB_TYPE_SORTING);
		
		// ソート順をセットする
		workKey.setItemCodeOrder(1, true);
		workKey.setCasePieceFlagOrder(2, true);
		workKey.setTcdcFlagOrder(3, false);
		workKey.setLocationNoOrder(4, true);
		workKey.setCustomerCodeOrder(5, true);
		
		wFinder = new WorkingInformationFinder(wConn);
		// カーソルオープン
		wFinder.open();
		// 検索を実行する
		count = ((WorkingInformationFinder) wFinder).search(workKey);
		// 初期化
		wLength = count;
		wCurrent = 0;

		// 荷主名称を取得する
		consignorkey.setConsignorNameCollect("");
		consignorkey.setRegistDateOrder(1, false);

		WorkingInformationFinder consignorFinder = new WorkingInformationFinder(wConn);
		consignorFinder.open();
		int nameCount = consignorFinder.search(consignorkey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			WorkingInformation workInfo[] = (WorkingInformation[]) consignorFinder.getEntities(0, 1);

			if (workInfo != null && workInfo.length != 0)
			{
				wConsignorName = workInfo[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

	/**
	 * 作業情報を<CODE>SortingParameter</CODE>にセットするためのクラスです。<BR>
	 * 
	 * @param winfo 作業情報
	 * @return SortingParameter[] 作業情報をセットした<CODE>SortingParameter</CODE>クラス
	 */
	private SortingParameter[] getDispData(WorkingInformation[] winfo)
	{
		SortingParameter[] resultParam = null;

		Vector tempVec = new Vector();

		for (int i = 0; i < winfo.length; i++)
		{
			SortingParameter tempParam = new SortingParameter();

			// 荷主コード
			tempParam.setConsignorCode(winfo[i].getConsignorCode());
			// 荷主名称
			tempParam.setConsignorName(wConsignorName);
			// 仕分予定日
			tempParam.setPlanDate(winfo[i].getPlanDate());
			// 商品コード
			tempParam.setItemCode(winfo[i].getItemCode());
			// 商品名称
			tempParam.setItemName(winfo[i].getItemName1());
			// ケースピース名称
			tempParam.setCasePieceName(DisplayUtil.getPieceCaseValue(winfo[i].getCasePieceFlag()));
			// クロスDC名称
			tempParam.setTcdcName(DisplayUtil.getTcDcValue(winfo[i].getTcdcFlag()));
			// 仕分場所
			tempParam.setSortingLocation(winfo[i].getLocationNo());
			// 出荷先コード
			tempParam.setCustomerCode(winfo[i].getCustomerCode());
			// 出荷先名称
			tempParam.setCustomerName(winfo[i].getCustomerName1());
			// 仕分総数
			tempParam.setTotalPlanQty(winfo[i].getPlanEnableQty());
			// ケース入数
			tempParam.setEnteringQty(winfo[i].getEnteringQty());
			// ボール入数
			tempParam.setBundleEnteringQty(winfo[i].getBundleEnteringQty());
			// 予定数表示。区分によって表示方法をかえる
			if (winfo[i].getCasePieceFlag().equals(WorkingInformation.CASEPIECE_FLAG_PIECE))
			{
				// ピースの場合
				tempParam.setPlanCaseQty(0);
				tempParam.setPlanPieceQty(winfo[i].getPlanEnableQty());
			}
			else
			{
				// ケースの場青
				tempParam.setPlanCaseQty(DisplayUtil.getCaseQty(winfo[i].getPlanEnableQty(), winfo[i].getEnteringQty()));
				tempParam.setPlanPieceQty(DisplayUtil.getPieceQty(winfo[i].getPlanEnableQty(), winfo[i].getEnteringQty()));
			}
			// 作業状態(名称)
			tempParam.setStatusName(DisplayUtil.getWorkingStatusValue(winfo[i].getStatusFlag()));

			tempVec.add(tempParam);

		}
		resultParam = new SortingParameter[tempVec.size()];
		tempVec.copyInto(resultParam);

		return resultParam;
	}

}
//end of class
