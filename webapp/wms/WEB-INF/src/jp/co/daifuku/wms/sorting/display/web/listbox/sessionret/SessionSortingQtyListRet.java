// $Id: SessionSortingQtyListRet.java,v 1.1.1.1 2006/08/17 09:34:32 mori Exp $
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
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : K.Mukai <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * 実績情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionSortingQtyListRet(Connection, SortingParameter)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(SortingParameter)</CODE>メソッドを呼び出し実績情報の検索を行います。<BR>
 * <BR>
 *   [検索条件]*必須項目
 *   <DIR>
 *     荷主コード		: ConsignorCode *<BR>
 *     仕分日			: WorkDate <BR>
 *     商品コード		: ItemCode <BR>
 *     ケースピース区分  : CasePieceFlag * <BR>
 *     クロス／ＤＣ		: TcdcFlag * <BR>
 *   </DIR>
 *   [検索View]
 *   <DIR>
 *     DvResultView<BR>
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
 *     荷主ｺｰﾄﾞ		: ConsignorCode <BR>
 *     荷主名称		: ConsignorName <BR>
 *     仕分日		: SortingDate <BR>
 *     仕分予定日	: PlanDate <BR>
 *     ｹｰｽ/ﾋﾟｰｽ		: CasePieceName <BR>
 *     ｸﾛｽ/DC		: TcdcName <BR>
 *     商品ｺｰﾄﾞ		: ItemCode <BR>
 *     商品名称		: ItemName <BR>
 *     出荷先ｺｰﾄﾞ	: CustomerCode <BR>
 *     出荷先名称	: CustomerName <BR>
 *     ｹｰｽ入数		: EnteringQty <BR>
 *     ﾎﾞｰﾙ入数		: BundleEnteringQty <BR>
 *     作業予定ｹｰｽ数 : PlanCaseQty <BR>
 *     作業予定ﾋﾟｰｽ数: PlanPieceQty <BR>
 *     実績ｹｰｽ数	: ResultCaseQty <BR>
 *     実績ﾋﾟｰｽ数	: ResultPieceQty <BR>
 *     欠品ｹｰｽ数	: ShortageCaseQty <BR>
 *     欠品ﾋﾟｰｽ数	: ShortagePieceQty <BR>
 *     仕分場所		: SortingLocation <BR>
 *     仕入先ｺｰﾄﾞ	: SupplierCode <BR>
 *     仕入先名称	: SupplierName <BR>
 *     作業者ｺｰﾄﾞ	: WorkerCode <BR>
 *     作業者名		: WorkerName <BR>
 *   </DIR>
 * </DIR>
 * 
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
public class SessionSortingQtyListRet extends SessionRet
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
	public SessionSortingQtyListRet(Connection conn, SortingParameter param) throws Exception
	{
		this.wConn = conn;

		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * 実績情報の検索結果を、指定件数分返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.表示データを何件取得するかを指定するための計算を行います。<BR>
	 *   2.結果セットから実績情報を取得します。<BR>
	 *   3.実績情報から表示データを取得し<CODE>SortingParameter</CODE>にセットします。<BR>
	 *   3.表示情報を返します。<BR>
	 * </DIR>
	 * 
	 * @return 表示情報を含む<CODE>SortingParameter</CODE>クラス
	 */
	public SortingParameter[] getEntities()
	{
		ResultView[] resultArray = null;
		SortingParameter[] param = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				// 検索結果を取得する。
				resultArray =
					(ResultView[]) ((ResultViewFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				// ShippingParameterにセットしなおす。
				param = getDispData(resultArray);

			}
			catch (Exception e)
			{
				//エラーをログファイルに落とす
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return param;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * パラメータから検索条件を取得し実績情報の検索を行うためのメソッドです。<BR>
	 * 
	 * @param param   検索条件を取得するためのパラメータ
	 * @throws Exception 全ての例外を報告します。
	 */
	private void find(SortingParameter param) throws Exception
	{
		int count = 0;

		ResultViewSearchKey workKey = new ResultViewSearchKey();
		ResultViewSearchKey consignorkey = new ResultViewSearchKey();

		// 検索条件をセットする
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			workKey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		if (!StringUtil.isBlank(param.getSortingDate()))
		{
			workKey.setWorkDate(param.getSortingDate());
			consignorkey.setWorkDate(param.getSortingDate());
		}
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			workKey.setItemCode(param.getItemCode());
			consignorkey.setItemCode(param.getItemCode());
		}
		// ケース/ピース区分　全ての場合は何もセットしない
		if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_CASE))
		{
			// ケース
			workKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
			consignorkey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_CASE);
		}
		else if (param.getCasePieceFlag().equals(SortingParameter.CASEPIECE_FLAG_PIECE))
		{
			// ピース
			workKey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
			consignorkey.setWorkFormFlag(ResultView.CASEPIECE_FLAG_PIECE);
		}
		// クロス/DC　全ての場合は何もセットしない(作業区分が仕分なのでTCは存在しないはず)
		if (param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_CROSSTC))
		{
			// クロス
			workKey.setTcdcFlag(ResultView.TCDC_FLAG_CROSSTC);
			consignorkey.setTcdcFlag(ResultView.TCDC_FLAG_CROSSTC);
		}
		else if (param.getTcdcFlag().equals(SortingParameter.TCDC_FLAG_DC))
		{
			// DC
			workKey.setTcdcFlag(ResultView.TCDC_FLAG_DC);
			consignorkey.setTcdcFlag(ResultView.TCDC_FLAG_DC);
		}
		workKey.setJobType(ResultView.JOB_TYPE_SORTING);
		consignorkey.setJobType(ResultView.JOB_TYPE_SORTING);

		workKey.setPlanDateOrder(1,true);
		workKey.setItemCodeOrder(2, true);
		workKey.setCasePieceFlagOrder(3, true);
		workKey.setTcdcFlagOrder(4, false);
		workKey.setCustomerCodeOrder(5, true);
		workKey.setLocationNoOrder(6,true);
		workKey.setRegistDateOrder(7, true);
		workKey.setResultQtyOrder(8, true);

		wFinder = new ResultViewFinder(wConn);
		// カーソルオープン
		wFinder.open();
		// 検索を実行する
		count = ((ResultViewFinder) wFinder).search(workKey);
		// 初期化
		wLength = count;
		wCurrent = 0;

		// 荷主名称を取得する
		consignorkey.setConsignorNameCollect("");
		consignorkey.setRegistDateOrder(1, false);

		ResultViewFinder consignorFinder = new ResultViewFinder(wConn);
		consignorFinder.open();
		int nameCount = consignorFinder.search(consignorkey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			ResultView[] resultView =
				(ResultView[]) consignorFinder.getEntities(0, 1);

			if (resultView != null && resultView.length != 0)
			{
				wConsignorName = resultView[0].getConsignorName();
			}
		}
		consignorFinder.close();
	}

	/**
	 * 実績情報を<CODE>SortingParameter</CODE>にセットするためのクラスです。<BR>
	 * 
	 * @param winfo 実績情報
	 * @return SortingParameter[] 実績情報をセットした<CODE>SortingParameter</CODE>クラス
	 */
	private SortingParameter[] getDispData(ResultView[] result)
	{
		SortingParameter[] resultParam = null;

		Vector tempVec = new Vector();

		for (int i = 0; i < result.length; i++)
		{
			SortingParameter tempParam = new SortingParameter();

			tempParam.setConsignorCode(result[i].getConsignorCode());
			tempParam.setConsignorName(wConsignorName);
			tempParam.setPlanDate(result[i].getWorkDate());
			tempParam.setPlanDate(result[i].getPlanDate());
			tempParam.setCasePieceName(DisplayUtil.getPieceCaseValue(result[i].getCasePieceFlag()));
			tempParam.setTcdcName(DisplayUtil.getTcDcValue(result[i].getTcdcFlag()));
			tempParam.setItemCode(result[i].getItemCode());
			tempParam.setItemName(result[i].getItemName1());
			tempParam.setCustomerCode(result[i].getCustomerCode());
			tempParam.setCustomerName(result[i].getCustomerName1());
			tempParam.setEnteringQty(result[i].getEnteringQty());
			tempParam.setBundleEnteringQty(result[i].getBundleEnteringQty());
			tempParam.setSortingLocation(result[i].getLocationNo());
			tempParam.setSupplierCode(result[i].getSupplierCode());
			tempParam.setSupplierName(result[i].getSupplierName1());
			tempParam.setWorkerCode(result[i].getWorkerCode());
			tempParam.setWorkerName(result[i].getWorkerName());

			//予定ケース数
			tempParam.setPlanCaseQty(DisplayUtil.getCaseQty(result[i].getPlanEnableQty(),
					result[i].getEnteringQty(), result[i].getWorkFormFlag()));
			//予定ピース数
			tempParam.setPlanPieceQty(DisplayUtil.getPieceQty(result[i].getPlanEnableQty(),
					result[i].getEnteringQty(), result[i].getWorkFormFlag()));
			//実績ケース数
			tempParam.setResultCaseQty(DisplayUtil.getCaseQty(result[i].getResultQty(),
					result[i].getEnteringQty(), result[i].getWorkFormFlag()));
			//実績ピース数
			tempParam.setResultPieceQty(DisplayUtil.getPieceQty(result[i].getResultQty(),
					result[i].getEnteringQty(), result[i].getWorkFormFlag()));
			//欠品ケース数
			tempParam.setShortageCaseQty(DisplayUtil.getCaseQty(result[i].getShortageCnt(),
					result[i].getEnteringQty(), result[i].getWorkFormFlag()));
			//欠品ピース数
			tempParam.setShortagePieceQty(DisplayUtil.getPieceQty(result[i].getShortageCnt(),
					result[i].getEnteringQty(), result[i].getWorkFormFlag()));
			

			tempVec.add(tempParam);

		}
		resultParam = new SortingParameter[tempVec.size()];
		tempVec.copyInto(resultParam);

		return resultParam;
	}

}
//end of class
