// $Id: SessionShippingQtyListRet.java,v 1.1.1.1 2006/08/17 09:34:28 mori Exp $
package jp.co.daifuku.wms.shipping.display.web.listbox.sessionret;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * <CODE>SessionShippingQtyListRet</CODE>クラスは、実績View情報を検索し結果を保持するクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionShippingQtyListRet(Connection conn,	ShippingParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(ShippingParameter param)</CODE>メソッドを呼び出し実績View情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞*必須項目
 *   <DIR>
 *     荷主コード*<BR>
 *     開始出荷日<BR>
 *     終了出荷日<BR>
 *     出荷先コード<BR>
 *     伝票No.<BR>
 *     商品コード<BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DVRESULTVIEW <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を<CODE>ShippingParameter</CODE>配列にセットし返します。<BR>
 * <BR>
 *   ＜表示項目＞
 *   <DIR>
 *     荷主ｺｰﾄﾞ<BR>
 *     荷主名称<BR>
 *     出荷日<BR>
 *     出荷予定日<BR>
 *     出荷先ｺｰﾄﾞ<BR>
 *     出荷先名称<BR>
 *     出荷伝票No.<BR>
 *     出荷伝票行No.<BR>
 *     商品ｺｰﾄﾞ<BR>
 *     商品名称<BR>
 *     ｹｰｽ入数<BR>
 *     ﾎﾞｰﾙ入数<BR>
 *     作業予定ｹｰｽ数<BR>
 *     作業予定ﾋﾟｰｽ数<BR>
 *     実績ｹｰｽ数<BR>
 *     実績ﾋﾟｰｽ数<BR>
 *     欠品ｹｰｽ数<BR>
 *     欠品ﾋﾟｰｽ数<BR>
 *     賞味期限<BR>
 *   </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>Y.Okamura</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:28 $
 * @author  $Author: mori $
 */
public class SessionShippingQtyListRet extends SessionRet
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
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:28 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * 検索を行うための<CODE>find(ShippingParameter param)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(ShippingParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * <BR>
	 * ＜検索条件＞*必須項目
	 *   <DIR>
	 *     荷主コード*<BR>
	 *     開始出荷日<BR>
	 *     終了出荷日<BR>
	 *     出荷先コード<BR>
	 *     伝票No.<BR>
	 *     商品コード<BR>
	 *     作業区分：出荷<BR>
	 *   </DIR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      <code>ShippingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionShippingQtyListRet(Connection conn, ShippingParameter param) throws Exception
	{
		this.wConn = conn;

		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * 実績View情報の検索結果を、指定件数分返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.表示データを何件取得するかを指定するための計算を行います。<BR>
	 *   2.結果セットから作業情報を取得します。<BR>
	 *   3.作業情報から表示データを取得し<CODE>ShippingParameter</CODE>にセットします。<BR>
	 *   4.表示情報を返します。<BR>
	 * </DIR>
	 * <BR>
	 * ＜検索結果＞<BR>
	 * <DIR>
	 *     荷主ｺｰﾄﾞ<BR>
	 *     荷主名称<BR>
	 *     出荷日<BR>
	 *     出荷予定日<BR>
	 *     出荷先ｺｰﾄﾞ<BR>
	 *     出荷先名称<BR>
	 *     出荷伝票No.<BR>
	 *     出荷伝票行No.<BR>
	 *     商品ｺｰﾄﾞ<BR>
	 *     商品名称<BR>
	 *     ｹｰｽ入数<BR>
	 *     ﾎﾞｰﾙ入数<BR>
	 *     作業予定ｹｰｽ数<BR>
	 *     作業予定ﾋﾟｰｽ数<BR>
	 *     実績ｹｰｽ数<BR>
	 *     実績ﾋﾟｰｽ数<BR>
	 *     欠品ｹｰｽ数<BR>
	 *     欠品ﾋﾟｰｽ数<BR>
	 *     賞味期限<BR>
	 * </DIR>
	 * @return 表示情報を含む<CODE>ShippingParameter</CODE>クラス
	 */
	public ShippingParameter[] getEntities()
	{
		ResultView[] resultArray = null;
		ShippingParameter[] param = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				// 検索結果を取得する。
				resultArray = (ResultView[]) ((ResultViewFinder) wFinder).getEntities(wStartpoint, wEndpoint);
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
	 * パラメータから検索条件を取得し実績View情報の検索を行うためのメソッドです。<BR>
	 * 
	 * @param param   検索条件を取得するためのパラメータ
	 */
	private void find(ShippingParameter param) throws Exception
	{
		int count = 0;

		ResultViewSearchKey rkey = new ResultViewSearchKey();
		ResultViewSearchKey consignorkey = new ResultViewSearchKey();
		// 検索条件をセットする
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			rkey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		if (!StringUtil.isBlank(param.getFromShippingDate()))
		{
			rkey.setWorkDate(param.getFromShippingDate(), ">=");
			consignorkey.setWorkDate(param.getFromShippingDate(), ">=");
		}
		if (!StringUtil.isBlank(param.getToShippingDate()))
		{
			rkey.setWorkDate(param.getToShippingDate(), "<=");
			consignorkey.setWorkDate(param.getToShippingDate(), "<=");
		}
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			rkey.setCustomerCode(param.getCustomerCode());
			consignorkey.setCustomerCode(param.getCustomerCode());
		}
		if (!StringUtil.isBlank(param.getShippingTicketNo()))
		{
			rkey.setShippingTicketNo(param.getShippingTicketNo());
			consignorkey.setShippingTicketNo(param.getShippingTicketNo());
		}
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			rkey.setItemCode(param.getItemCode());
			consignorkey.setItemCode(param.getItemCode());
		}
		rkey.setJobType(SystemDefine.JOB_TYPE_SHIPINSPECTION);
		consignorkey.setJobType(SystemDefine.JOB_TYPE_SHIPINSPECTION);
		
		rkey.setWorkDateOrder(1, true);
		rkey.setPlanDateOrder(2, true);
		rkey.setCustomerCodeOrder(3, true);
		rkey.setShippingTicketNoOrder(4, true);
		rkey.setShippingLineNoOrder(5, true);
		rkey.setItemCodeOrder(6, true);
		rkey.setRegistDateOrder(7,true);
		rkey.setResultQtyOrder(8, true);

		wFinder = new ResultViewFinder(wConn);
		// カーソルオープン
		wFinder.open();
		// 検索を実行する
		count = ((ResultViewFinder) wFinder).search(rkey);
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
			ResultView resultView[] = (ResultView[]) consignorFinder.getEntities(0, 1);

			if (resultView != null && resultView.length != 0)
			{
				wConsignorName = resultView[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

	/**
	 * 実績View情報を<CODE>ShippingParameter</CODE>にセットするためのクラスです。<BR>
	 * 
	 * @param result 実績View情報
	 * @return ShippingParameter[] 実績View情報をセットした<CODE>ShippingParameter</CODE>クラス
	 */
	private ShippingParameter[] getDispData(ResultView[] result)
	{
		ShippingParameter[] param = new ShippingParameter[result.length];

		for (int i = 0; i < result.length; i++)
		{
			param[i] = new ShippingParameter();
			param[i].setConsignorCode(result[i].getConsignorCode());
			param[i].setConsignorName(wConsignorName);
			param[i].setShippingDate(result[i].getWorkDate());
			param[i].setPlanDate(result[i].getPlanDate());
			param[i].setCustomerCode(result[i].getCustomerCode());
			param[i].setCustomerName(result[i].getCustomerName1());
			param[i].setShippingTicketNo(result[i].getShippingTicketNo());
			param[i].setShippingLineNo(result[i].getShippingLineNo());
			param[i].setItemCode(result[i].getItemCode());
			param[i].setItemName(result[i].getItemName1());
			param[i].setEnteringQty(result[i].getEnteringQty());
			param[i].setBundleEnteringQty(result[i].getBundleEnteringQty());
			param[i].setPlanCaseQty(DisplayUtil.getCaseQty(result[i].getPlanEnableQty(), result[i].getEnteringQty()));
			param[i].setPlanPieceQty(DisplayUtil.getPieceQty(result[i].getPlanEnableQty(), result[i].getEnteringQty()));
			param[i].setResultCaseQty(DisplayUtil.getCaseQty(result[i].getResultQty(), result[i].getEnteringQty()));
			param[i].setResultPieceQty(DisplayUtil.getPieceQty(result[i].getResultQty(), result[i].getEnteringQty()));
			param[i].setShortageCaseQty(DisplayUtil.getCaseQty(result[i].getShortageCnt(), result[i].getEnteringQty()));
			param[i].setShortagePieceQty(DisplayUtil.getPieceQty(result[i].getShortageCnt(), result[i].getEnteringQty()));
			param[i].setUseByDate(result[i].getResultUseByDate());

		}

		return param;
	}

}
//end of class
