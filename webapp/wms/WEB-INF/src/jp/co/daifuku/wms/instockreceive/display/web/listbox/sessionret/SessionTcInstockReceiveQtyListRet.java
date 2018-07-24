// $Id: SessionTcInstockReceiveQtyListRet.java,v 1.1.1.1 2006/08/17 09:34:13 mori Exp $
package jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret;

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
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * 実績情報Viewを検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * <B>1.検索処理(<CODE>SessionTcInstockReceiveQtyListRet(Connection, InstockReceiveParameter)</CODE>メソッド)<BR></B>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(InstockReceiveParameter)</CODE>メソッドを呼び出し実績情報Viewの検索を行います。<BR>
 * <BR>
 *   ＜入力データ＞*必須項目
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th><tr>
 *     <tr><td>*</td><td>作業区分</td><td>：</td><td>01(入荷)</td><tr>
 *     <tr><td>*</td><td>TC/DC区分</td><td>：</td><td>TC</td><tr>
 *     <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>開始入荷受付日</td><td>：</td><td>FromInstockReceiveDate</td></tr>
 *     <tr><td></td><td>終了入荷受付日</td><td>：</td><td>ToInstockReceiveDate</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td></td><td>開始伝票No.</td><td>：</td><td>FromTicketNo</td></tr>
 *     <tr><td></td><td>終了伝票No.</td><td>：</td><td>ToTicketNo</td></tr>
 *     <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
 *   </table>
 *   </DIR>
 *   ＜検索テーブル＞
 *   <DIR>
 *     DVRESULTVIEW<BR>
 *   </DIR>
 * </DIR>
 * 
 * <B>2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR></B>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を<CODE>InstockReceiveParameter</CODE>配列にセットし返します。<BR>
 * <BR>
 *   ＜返却データ＞
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th></tr>
 *     <tr><td></td><td>荷主ｺｰﾄﾞ</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>荷主名称(登録日時の一番新しいもの)</td><td>：</td><td>ConsignorName</td></tr>
 *     <tr><td></td><td>入荷受付日</td><td>：</td><td>InstockReceiveDate</td></tr>
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>仕入先ｺｰﾄﾞ</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>仕入先名称</td><td>：</td><td>SupplierName</td></tr>
 *     <tr><td></td><td>出荷先ｺｰﾄﾞ</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td></td><td>出荷先名称</td><td>：</td><td>CustomerName</td></tr>
 *     <tr><td></td><td>伝票No.</td><td>：</td><td>InstockTicketNo</td></tr>
 *     <tr><td></td><td>行No.</td><td>：</td><td>InstockLineNo</td></tr>
 *     <tr><td></td><td>商品ｺｰﾄﾞ</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>商品名称</td><td>：</td><td>ItemName</td></tr>
 *     <tr><td></td><td>ｹｰｽ入数</td><td>：</td><td>EnteringQty</td></tr>
 *     <tr><td></td><td>ﾎﾞｰﾙ入数</td><td>：</td><td>BundleEnteringQty</td></tr>
 *     <tr><td></td><td>作業予定ｹｰｽ数</td><td>：</td><td>PlanCaseQty</td></tr>
 *     <tr><td></td><td>作業予定ﾋﾟｰｽ数</td><td>：</td><td>PlanPieceQty</td></tr>
 *     <tr><td></td><td>実績ｹｰｽ数</td><td>：</td><td>ResultCaseQty</td></tr>
 *     <tr><td></td><td>実績ﾋﾟｰｽ数</td><td>：</td><td>ResultPieceQty</td></tr>
 *     <tr><td></td><td>欠品ｹｰｽ数</td><td>：</td><td>ShortageCaseQty</td></tr>
 *     <tr><td></td><td>欠品ﾋﾟｰｽ数</td><td>：</td><td>ShortagePieceQty</td></tr>
 *     <tr><td></td><td>賞味期限</td><td>：</td><td>UseByDate</td></tr>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:13 $
 * @author  $Author: mori $
 */
public class SessionTcInstockReceiveQtyListRet extends SessionRet
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
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:13 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * 検索を行うための<CODE>find(InstockReceiveParameter)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(InstockReceiveParameter)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      <code>InstockReceiveParameter</code> 検索条件を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionTcInstockReceiveQtyListRet(Connection conn, InstockReceiveParameter param) throws Exception
	{
		this.wConn = conn;

		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * 実績情報Viewの検索結果を、指定件数分返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.表示データを何件取得するかを指定するための計算を行います。<BR>
	 *   2.結果セットから実績情報Viewを取得します。<BR>
	 *   3.実績情報Viewから表示データを取得し<CODE>InstockReceiveParameter</CODE>にセットします。<BR>
	 *   3.表示情報を返します。<BR>
	 * </DIR>
	 * 
	 * @return 表示情報を含む<CODE>InstockReceiveParameter</CODE>クラス
	 */
	public InstockReceiveParameter[] getEntities()
	{
		ResultView[] view = null;
		InstockReceiveParameter[] resultParam = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				// 検索結果を取得する。
				view = (ResultView[]) ((ResultViewFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				// InstockReceiveParameterにセットしなおす。
				resultParam = getDispData(view);

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
	 * パラメータから検索条件を取得し実績情報Viewの検索を行うためのメソッドです。<BR>
	 * 
	 * @param param   検索条件を取得するためのパラメータ
	 */
	private void find(InstockReceiveParameter param) throws Exception
	{
		int count = 0;

		ResultViewSearchKey viewKey = new ResultViewSearchKey();
		ResultViewSearchKey consignorkey = new ResultViewSearchKey();
		
		// 検索条件をセットする
		// 荷主コード
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			viewKey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		// 開始入荷受付日
		if (!StringUtil.isBlank(param.getFromInstockReceiveDate()))
		{
			viewKey.setWorkDate(param.getFromInstockReceiveDate(), ">=");
			consignorkey.setWorkDate(param.getFromInstockReceiveDate(), ">=");
		}
		// 終了入荷受付日
		if (!StringUtil.isBlank(param.getToInstockReceiveDate()))
		{
			viewKey.setWorkDate(param.getToInstockReceiveDate(), "<=");
			consignorkey.setWorkDate(param.getToInstockReceiveDate(), "<=");
		}
		// 仕入先コード
		if (!StringUtil.isBlank(param.getSupplierCode()))
		{
			viewKey.setSupplierCode(param.getSupplierCode());
			consignorkey.setSupplierCode(param.getSupplierCode());
		}
		// 出荷先コード
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			viewKey.setCustomerCode(param.getCustomerCode());
			consignorkey.setCustomerCode(param.getCustomerCode());
		}
		// 開始伝票No.
		if (!StringUtil.isBlank(param.getFromTicketNo()))
		{
			viewKey.setInstockTicketNo(param.getFromTicketNo(), ">=");
			consignorkey.setInstockTicketNo(param.getFromTicketNo(), ">=");
		}
		// 終了伝票No.
		if (!StringUtil.isBlank(param.getToTicketNo()))
		{
			viewKey.setInstockTicketNo(param.getToTicketNo(), "<=");
			consignorkey.setInstockTicketNo(param.getToTicketNo(), "<=");
		}
		// 商品コード
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			viewKey.setItemCode(param.getItemCode());
			consignorkey.setItemCode(param.getItemCode());
		}
		// 作業区分：入荷
		viewKey.setJobType(ResultView.JOB_TYPE_INSTOCK);
		consignorkey.setJobType(ResultView.JOB_TYPE_INSTOCK);
		// TCDC区分：TC
		viewKey.setTcdcFlag(ResultView.TCDC_FLAG_TC);
		consignorkey.setTcdcFlag(ResultView.TCDC_FLAG_TC);
		
		// ソート順
		// 入荷受付日
		viewKey.setWorkDateOrder(1,true);
		// 入荷予定日
		viewKey.setPlanDateOrder(2,true);
		// 仕入先コード
		viewKey.setSupplierCodeOrder(3,true);
		// 出荷先コード
		viewKey.setCustomerCodeOrder(4,true);
		// 伝票No.
		viewKey.setInstockTicketNoOrder(5,true);
		// 行No.
		viewKey.setInstockLineNoOrder(6,true);
		// 商品コード
		viewKey.setItemCodeOrder(7,true);
		// 登録日
		viewKey.setRegistDateOrder(8,true);
		// 実績数
		viewKey.setResultQtyOrder(9,true);
				
		wFinder = new ResultViewFinder(wConn);
		// カーソルオープン
		wFinder.open();
		// 検索を実行する
		count = ((ResultViewFinder) wFinder).search(viewKey);
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
			ResultView view[] = (ResultView[]) consignorFinder.getEntities(0, 1);

			if (view != null && view.length != 0)
			{
				wConsignorName = view[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

	/**
	 * 実績情報Viewを<CODE>InstockReceiveParameter</CODE>にセットするためのクラスです。。<BR>
	 * 
	 * @param result 実績情報View
	 * @return InstockReceiveParameter[] 実績情報Viewをセットした<CODE>InstockReceiveParameter</CODE>クラス
	 */
	private InstockReceiveParameter[] getDispData(ResultView[] result)
	{
		InstockReceiveParameter[] param = null;
		
		Vector tempVec = new Vector();

		for (int i = 0; i < result.length; i++)
		{
			InstockReceiveParameter tempParam = new InstockReceiveParameter();

			// 荷主コード
			tempParam.setConsignorCode(result[i].getConsignorCode());
			// 荷主名称
			tempParam.setConsignorName(wConsignorName);
			// 入荷受付日
			tempParam.setInstockReceiveDate(result[i].getWorkDate());
			// 入荷予定日
			tempParam.setPlanDate(result[i].getPlanDate());
			// 仕入先コード
			tempParam.setSupplierCode(result[i].getSupplierCode());
			// 仕入先名称
			tempParam.setSupplierName(result[i].getSupplierName1());
			// 出荷先コード
			tempParam.setCustomerCode(result[i].getCustomerCode());
			// 出荷先名称
			tempParam.setCustomerName(result[i].getCustomerName1());
			// 入荷伝票No.
			tempParam.setInstockTicketNo(result[i].getInstockTicketNo());
			// 入荷伝票行No.
			tempParam.setInstockLineNo(result[i].getInstockLineNo());
			// 商品コード
			tempParam.setItemCode(result[i].getItemCode());
			// 商品名称
			tempParam.setItemName(result[i].getItemName1());
			// ケース入数
			tempParam.setEnteringQty(result[i].getEnteringQty());
			// ボール入数
			tempParam.setBundleEnteringQty(result[i].getBundleEnteringQty());
			// 予定ケース数
			tempParam.setPlanCaseQty(DisplayUtil.getCaseQty(result[i].getPlanEnableQty(), result[i].getEnteringQty()));
			// 予定ピース数
			tempParam.setPlanPieceQty(DisplayUtil.getPieceQty(result[i].getPlanEnableQty(), result[i].getEnteringQty()));
			// 実績ケース数
			tempParam.setResultCaseQty(DisplayUtil.getCaseQty(result[i].getResultQty(), result[i].getEnteringQty()));
			// 実績ピース数
			tempParam.setResultPieceQty(DisplayUtil.getPieceQty(result[i].getResultQty(), result[i].getEnteringQty()));
			// 欠品ケース数
			tempParam.setShortageCaseQty(DisplayUtil.getCaseQty(result[i].getShortageCnt(), result[i].getEnteringQty()));
			// 欠品ピース数
			tempParam.setShortagePieceQty(DisplayUtil.getPieceQty(result[i].getShortageCnt(), result[i].getEnteringQty()));
			// 賞味期限
			tempParam.setUseByDate(result[i].getResultUseByDate());
			
			tempVec.add(tempParam);

		}
		param = new InstockReceiveParameter[tempVec.size()];
		tempVec.copyInto(param);

		return param;
	}

}
//end of class
