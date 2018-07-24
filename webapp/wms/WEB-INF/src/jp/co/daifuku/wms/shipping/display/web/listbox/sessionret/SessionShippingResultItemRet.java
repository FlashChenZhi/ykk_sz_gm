// $Id: SessionShippingResultItemRet.java,v 1.1.1.1 2006/08/17 09:34:28 mori Exp $
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
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * <CODE>SessionShippingResultItemRet</CODE>クラスは、実績情報Viewから<BR>
 * 商品一覧を検索し表示するためのクラスです。 <BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.検索処理(<CODE>SessionShippingResultItemRet(Connection conn, ShippingParameter param)</CODE>メソッド) <BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。 <BR>
 *   <CODE>find(ShippingParameter param)</CODE>メソッドを呼び出し出荷実績情報の検索を行います。 <BR>
 * <BR>
 *  <DIR>
 *   ＜検索条件＞ 必須項目*<BR>
 *   <DIR>
 *     荷主コード <BR>
 *     開始出庫日 <BR>
 *     終了出庫日 <BR>
 *     出荷先コード <BR>
 *     伝票No <BR>
 *     商品コード <BR>
 *     作業区分：出荷* <BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DVRESULTVIEW <BR>
 *   </DIR>
 *   ＜抽出項目＞ <BR>
 *   <DIR>
 *       商品コード <BR>
 *       商品名称 <BR>
 *   </DIR>
 *  </DIR>
 * </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。 <BR>
 * <BR>
 *   1.検索処理にて得た検索結果から表示情報を取得します。 <BR>
 *   検索結果を出荷実績情報配列にセットし返します。 <BR>
 * <BR>
 *   ＜表示項目＞
 *   <DIR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *   </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>K.Toda</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:28 $
 * @author  $Author: mori $
 */
public class SessionShippingResultItemRet extends SessionRet
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

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
	 * 検索を行うための<CODE>find(ShippingParameter param)</CODE>メソッドを呼び出します。 <BR>
	 * <CODE>find(ShippingParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。 <BR>
	 * また、検索結果を取得するには<CODE>getEntities</CODE>メソッドを呼ぶ必要があります。 <BR>
	 * <BR>
	 * <DIR>
	 * ＜検索条件＞ <BR>
	 * <DIR>
	 * 荷主コード <BR>
	 * 開始出荷予定日 <BR>
	 * 終了出荷予定日 <BR>
	 * 出荷先コード <BR>
	 * 伝票No <BR>
	 * 商品コード <BR>
	 * </DIR>
	 * </DIR>
	 * @param conn       <CODE>Connection</CODE>
	 * @param param      <CODE>ShippingParameter</CODE>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionShippingResultItemRet(Connection conn, ShippingParameter param) throws Exception
	{
		this.wConn = conn;
		
		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * 出荷実績情報<CODE>(ResultView)</CODE>の検索結果を、指定件数分返します。 <BR>
	 * <BR>
	 * <DIR>
	 * ＜検索結果＞ <BR>
	 * <DIR>
	 * 商品コード <BR>
	 * 商品名称 <BR>
	 * </DIR>
	 * </DIR>
	 * @return 出荷実績情報<CODE>(ResultView)</CODE>の検索結果配列
	 */
	public ResultView[] getEntities()
	{
		ResultView[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (ResultView[]) ((ResultViewFinder)wFinder).getEntities(wStartpoint, wEndpoint);
			}
			catch (Exception e)
			{
				//エラーをログファイルに落とす
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/** 
	 * 入力されたパラメータをもとにSQL文を発行し、実績情報Viewの検索を行います。 <BR>
	 * 検索を行う<CODE>ResultViewFinder</CODE>はインスタンス変数として保持します。 <BR>
	 * 検索結果を取得するには<CODE>getEntities</CODE>メソッドを呼ぶ必要があります。 <BR>
	 * @param param      <CODE>ShippingParameter</CODE>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	private void find(ShippingParameter param) throws Exception
	{
		int count = 0;

		ResultViewSearchKey wkey = new ResultViewSearchKey();

		// 検索実行
		// 荷主コード
		if (!StringUtil.isBlank(param.getConsignorCode()))
			wkey.setConsignorCode(param.getConsignorCode());
		// 開始出庫日
		if (!StringUtil.isBlank(param.getFromShippingDate()))
			wkey.setWorkDate(param.getFromShippingDate(), ">=");
		// 終了出庫日
		if (!StringUtil.isBlank(param.getToShippingDate()))
			wkey.setWorkDate(param.getToShippingDate(), "<=");
		// 出荷先コード
		if (!StringUtil.isBlank(param.getCustomerCode()))
			wkey.setCustomerCode(param.getCustomerCode());
		// 伝票No
		if (!StringUtil.isBlank(param.getShippingTicketNo()))
			wkey.setShippingTicketNo(param.getShippingTicketNo());
		// 商品コード
		if (!StringUtil.isBlank(param.getItemCode()))
			wkey.setItemCode(param.getItemCode());

		// 作業区分 『出荷』
		wkey.setJobType(SystemDefine.JOB_TYPE_SHIPINSPECTION);
		
		wkey.setItemCodeGroup(1);
		wkey.setItemName1Group(2);
		wkey.setItemCodeCollect("");
		wkey.setItemName1Collect("");
		wkey.setItemCodeOrder(1, true);
		wkey.setItemName1Order(2, true);

		wFinder = new ResultViewFinder(wConn);
		// カーソルオープン
		wFinder.open();
		count = ((ResultViewFinder)wFinder).search(wkey);
		// 初期化
		wLength = count;
		wCurrent = 0;
	}

}
//end of class
