// $Id: SessionShippingDateRet.java,v 1.1.1.1 2006/08/17 09:34:28 mori Exp $
package jp.co.daifuku.wms.shipping.display.web.listbox.sessionret;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
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
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * 
 * <CODE>SessionShippingDateRet</CODE>クラスは、実績情報テーブルから出荷日の検索を行い結果を保持するクラスです。<BR>
 * このクラスを使用する場合は、DBとのセッションを保持するため、このクラスへの参照を保持してください。<BR>
 * 使用後に、<CODE>closeConnection()</CODE>メソッドを使用して、セッションを閉じてください。<BR>
 * <BR>
 * このクラスは、以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionShippingDateRet(Connection conn, ShippingParameter param)</CODE>コンストラクタ)<BR>
 * 	<DIR>
 * 	リストボックスの初期表示時に行われます。<BR>
 * 	<CODE>find(ShippingParameter param)</CODE>メソッドを呼び出し、出荷の実績情報を検索し、<BR>
 * 	出荷日の一覧を保持します。<BR>
 * 	<BR>
 * 	＜パラメータ＞ *必須入力<BR>
 * 	<DIR>
 * 	荷主コード*<BR>
 * 	開始出荷日<BR>
 * 	終了出荷日<BR>
 * 	</DIR>
 * <BR>
 *  ＜検索条件＞<BR>
 * 	</DIR>
 * 	荷主コード<BR>
 * 	開始出荷日<BR>
 * 	終了出荷日<BR>
 * 	作業区分:出荷<BR>
 * 	</DIR>
 * 	</DIR>
 * 	<BR>
 * 2.表示レコード取得処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * 	<DIR>
 * 	画面表示するレコードを取得します。<BR>
 * 	検索処理で検索した結果から、表示する分を<CODE>ResultView</CODE>クラスの配列で返します。<BR>
 * 	<BR>
 * 	＜項目＞<BR>
 * 	<DIR>
 * 	出荷日<BR>
 * 	</DIR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:28 $
 * @author  $Author: mori $
 */
public class SessionShippingDateRet extends SessionRet
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
	 * 出荷の実績情報の検索処理と結果の件数取得を行います。
	 * 
	 * @param	conn	データベースコネクション
	 * @param	param	<CODE>ShippingParameter</CODE>クラス
	 * @throws Exception 全ての例外を報告します。
	 */
	public SessionShippingDateRet(Connection conn, ShippingParameter param) throws Exception
	{
		// コネクションの保持
		wConn = conn ;
		
		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * 指定された件数分の出荷日を保持している一覧から取得します。
	 * 
	 * @return	<CODE>ResultView</CODE>クラスの配列
	 */
	public ResultView[] getEntities()
	{
		ResultView[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray =
					(ResultView[]) ((ResultViewFinder) wFinder).getEntities(
						wStartpoint,
						wEndpoint);
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
	 * パラメータにセットされた条件にしたがって、出荷の実績情報を検索し、<BR>
	 * 出荷日の一覧と結果の件数を保持します。<BR>
	 * 検索結果を取得するには<CODE>getEntities</CODE>メソッドを呼ぶ必要があります。 <BR>
	 * @param	param	<CODE>ShippingParameter</CODE>クラス<BR>
	 * @throws Exception 全ての例外を報告します。
	 */
	private void find(ShippingParameter param) throws Exception
	{
		// Finderインスタンス生成
		wFinder = new ResultViewFinder(wConn);
		ResultViewSearchKey resultViewSearchKey = new ResultViewSearchKey();
		
		// 検索条件をセット
		resultViewSearchKey.setJobType(SystemDefine.JOB_TYPE_SHIPINSPECTION);
		resultViewSearchKey.setWorkDateCollect("");
		if(!StringUtil.isBlank(param.getConsignorCode()))
		{
			resultViewSearchKey.setConsignorCode(param.getConsignorCode());
		}
		// 出荷日がセットされている場合
		if(!StringUtil.isBlank(param.getShippingDate()))
		{
			resultViewSearchKey.setWorkDate(param.getShippingDate());
		}
		// 開始出荷日 or 終了出荷日がセットされている場合
		else
		{
			// 開始出荷日がセットされている
			if(!StringUtil.isBlank(param.getFromShippingDate()))
			{
				resultViewSearchKey.setWorkDate(param.getFromShippingDate());
			}
			// 終了出荷日がセットされている
			if(!StringUtil.isBlank(param.getToShippingDate()))
			{
				resultViewSearchKey.setWorkDate(param.getToShippingDate());
			}
		}
		resultViewSearchKey.setWorkDateGroup(1);
		resultViewSearchKey.setWorkDateOrder(1, true);
		
		// Finderのカーソルオープン
		wFinder.open();
		
		// 検索（結果の件数を取得）
		wLength = wFinder.search(resultViewSearchKey);
		
		// 現在の表示件数を初期化
		wCurrent = 0;
	}

}
//end of class
