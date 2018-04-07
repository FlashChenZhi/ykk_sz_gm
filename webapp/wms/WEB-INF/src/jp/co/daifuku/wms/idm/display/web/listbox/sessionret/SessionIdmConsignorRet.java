package jp.co.daifuku.wms.idm.display.web.listbox.sessionret;
/*
 * Created on Nov 1, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.idm.dbhandler.IdmStockFinder;
import jp.co.daifuku.wms.idm.schedule.IdmControlParameter;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 荷主一覧リストボックス（移動ラック）用のデータを検索するためのクラスです。<BR>
 * 検索条件をパラメータとして受け取り、荷主一覧の検索を行います。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionIdmConsignoerRet(Connection conn,IdmControlParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(IdmControlParameter param)</CODE>メソッドを呼び出し作業情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目なし<BR>
 *   <DIR>
 *     荷主コード<BR>
 *     状態フラグが在庫 <BR>
 *     在庫数が１以上 <BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DMSTOCK <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   1.検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を作業情報配列にセットし返します。<BR>
 * <BR>
 *   ＜表示項目＞
 *   <DIR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/17</TD><TD>kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */

public class SessionIdmConsignorRet extends SessionRet
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
			return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $");
		}

		/**
		 * 検索を行うための<CODE>find(IdmControlParameter param)</CODE>メソッドを呼び出します。<BR>
		 * <CODE>find(IdmControlParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
		 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
		 * <DIR>
		 * ＜検索条件＞<BR>
		 * ･荷主コード<BR>
		 * </DIR>
		 * @param conn       <code>Connection</code>
		 * @param param      <code>IdmControlParameter</code>検索結果を含むパラメータ
		 * @throws Exception 何らかの例外が発生した場合に通知されます。
		 */
		public SessionIdmConsignorRet(Connection conn, IdmControlParameter param) throws Exception
		{
			this.wConn = conn;
			find(param);
		}

		// Public methods ------------------------------------------------
		/**
		 * <CODE>DNSTOCK</CODE>の検索結果を返します。
		 * <DIR>
		 * ＜検索結果＞
		 * ･荷主コード<BR>
		 * ･荷主名称<BR>
		 * </DIR>
		 * @return DNSTOCKの検索結果
		 */
		public Parameter[] getEntities()
		{
			IdmControlParameter[] resultArray = null;
			Stock temp[] = null;
			if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
			{	
				try
				{	
					temp = (Stock[])((IdmStockFinder)wFinder).getEntities(wStartpoint, wEndpoint);
					resultArray = convertToIdmControlParams(temp);
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
		 * 入力されたパラメータをもとにSQL文を発行します。<BR>
		 * 検索を行う<code>IdmStockFinder</code>はインスタンス変数として保持します。<BR>
		 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
		 * @param param      <code>IdmControlParameter</code>検索結果を含むパラメータ
		 * @throws Exception 何らかの例外が発生した場合に通知されます。
		 */
		private void find(IdmControlParameter param) throws Exception
		{	

			StockSearchKey skey = new StockSearchKey();
			// 検索実行
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				skey.setConsignorCode(param.getConsignorCode());
			}
			else
			{
				skey.setConsignorCode("","IS NOT NULL");
			}

			// 状態フラグが在庫
			skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			// 在庫数が１以上
			skey.setStockQty(0, ">");
			
			// グループ順をセット
			skey.setConsignorCodeGroup(1);
			skey.setConsignorNameGroup(2);
			// 取得順をセット
			skey.setConsignorCodeCollect("");
			skey.setConsignorNameCollect("");
			
			// ソート順をセット
			skey.setConsignorCodeOrder(1, true);
			skey.setConsignorNameOrder(2, true);

			// IdmStockFinderオブジェクトを作成
			wFinder = new IdmStockFinder(wConn);
			// カーソルオープン
			wFinder.open();
			int count = ((IdmStockFinder)wFinder).search(skey);

			wLength = count;
			wCurrent = 0;
		}
		
		/**
		 * このメソッドは、<CODE>Stock</CODE>エンティティを<CODE>IdmControlParameter</CODE>パラメータに変換します。<BR>
		 * @param entityView 在庫情報
		 * @return IdmControlParameter[] 在庫情報をセットした<CODE>IdmControlParameter</CODE>パラメータ
		 */
		private IdmControlParameter[] convertToIdmControlParams(Stock[] entityView)
		{
			IdmControlParameter[] stParam = null;
			
			if (entityView == null || entityView.length==0)
			{	
			 	return null;
			}
				stParam = new IdmControlParameter[entityView.length];
				for (int i = 0; i < entityView.length; i++)
				{
						stParam[i] = new IdmControlParameter();
						stParam[i].setConsignorCode(entityView[i].getConsignorCode());
						stParam[i].setConsignorName(entityView[i].getConsignorName());
					
				}
				
			return stParam;
		}
}
//end of class
