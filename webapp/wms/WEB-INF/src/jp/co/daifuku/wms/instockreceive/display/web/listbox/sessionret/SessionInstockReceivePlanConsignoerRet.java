package jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret;
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
import jp.co.daifuku.wms.base.dbhandler.InstockPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * 入荷予定情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * <B>1.検索処理(<CODE>SessionInstockReceivePlanConsignoerRet(Connection, InstockReceiveParameter)</CODE>メソッド)<BR></B>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(InstockReceiveParameter)</CODE>メソッドを呼び出し入荷予定情報の検索を行います。<BR>
 * <BR>
 *   ＜入力データ＞*必須項目
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th><tr>
 *     <tr><td></td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
 *     <tr><td></td><td>状態フラグ</td><td>：</td><td>StatusFlag</td></tr>
 *   </table>
 *   </DIR>
 *   ＜検索テーブル＞
 *   <DIR>
 *     DNINSTOCKPLAN<BR>
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
 *     <tr><td></td><td>荷主名称</td><td>：</td><td>ConsignorName</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/01</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:13 $
 * @author  $Author: mori $
 */

public class SessionInstockReceivePlanConsignoerRet extends SessionRet
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
			return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:13 $");
		}

		/**
		 * 検索を行うための<CODE>find(InStockReceiveParameter param)</CODE>メソッドを呼び出します。<BR>
		 * <CODE>find(InStockReceiveParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
		 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
		 * 
		 * @param conn       <code>Connection</code>
		 * @param param      <code>InStockReceiveParameter</code> 検索条件を含むパラメータ
		 * @throws Exception 何らかの例外が発生した場合に通知されます。
		 */
		public SessionInstockReceivePlanConsignoerRet(Connection conn, InstockReceiveParameter param) throws Exception
		{
			this.wConn = conn;
			find(param);
		}

		
		// Public methods ------------------------------------------------

		/**
		 * <CODE>DNINSTOCKPLAN</CODE>の検索結果を返します。
		 * <DIR>
		 * ＜検索結果＞
		 * 荷主コード<BR>
		 * 荷主名称<BR>
		 * </DIR>
		 * @return DNINSTOCKPLANの検索結果
		 */
		public Parameter[] getEntities()
		{
			InstockReceiveParameter[] resultArray = null;
			InstockPlan temp[] = null;
			if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
			{	
				try
				{	
					temp = (InstockPlan[])((InstockPlanFinder)wFinder).getEntities(wStartpoint, wEndpoint);
					resultArray = convertToInStockParams(temp);
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
		 * 検索を行う<code>InStockPlanFinder</code>はインスタンス変数として保持します。<BR>
		 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
		 * @param param      <code>InstockReceiveParameter</code> 検索条件を含むパラメータ
		 */
		private void find(InstockReceiveParameter param) throws Exception
		{	

			InstockPlanSearchKey skey = new InstockPlanSearchKey();
			
			// 検索実行
			// 入荷予定日
			if (!StringUtil.isBlank(param.getPlanDate()))
			{
				skey.setPlanDate(param.getPlanDate());
			}
			// 荷主コード		
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				skey.setConsignorCode(param.getConsignorCode());
			}
			else
			{
				skey.setConsignorCode("","IS NOT NULL");
			}
			// TC/DC区分
			if (!StringUtil.isBlank(param.getTcdcFlag()))
			{
				if(InstockReceiveParameter.TCDC_FLAG_TC.equals(param.getTcdcFlag()))
				{
					skey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC);
				}
				else if(InstockReceiveParameter.TCDC_FLAG_DC.equals(param.getTcdcFlag()))
				{
					skey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
				}
				else if(InstockReceiveParameter.TCDC_FLAG_CROSSTC.equals(param.getTcdcFlag()))
				{
					skey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
				}
				else if(InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC.equals(param.getTcdcFlag()))
				{
					skey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC,"!=");
				}
			
			}
			
			// グループ順をセットします
			skey.setConsignorCodeGroup(1);
			skey.setConsignorNameGroup(2);
			// 取得順をセットします
			skey.setConsignorCodeCollect("");
			skey.setConsignorNameCollect("");
			
			// ソート順をセットします
			skey.setConsignorCodeOrder(1, true);
			skey.setConsignorNameOrder(2, true);
			
			// 作業状態
			if(param.getSearchStatus() != null && param.getSearchStatus().length > 0)
			{
				String[] search = new String[param.getSearchStatus().length];
				for(int i = 0; i < param.getSearchStatus().length; ++i)
				{
					if(param.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
					{
						search[i] = InstockPlan.STATUS_FLAG_UNSTART;
					}
					else if(param.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_STARTED))
					{
						search[i] = InstockPlan.STATUS_FLAG_START;
					}
					else if(param.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
					{
						search[i] = InstockPlan.STATUS_FLAG_NOWWORKING;
					}
					else if(param.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION))
					{
						search[i] = InstockPlan.STATUS_FLAG_COMPLETE_IN_PART;
					}
					else if(param.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
					{
						search[i] = InstockPlan.STATUS_FLAG_COMPLETION;
					}
					else
					{
						search[i] = "*";
					}
				}
				skey.setStatusFlag(search);
			}
			else
			{
				skey.setStatusFlag(InstockPlan.STATUS_FLAG_DELETE, "!=");
			}
			wFinder = new InstockPlanFinder(wConn);
			// カーソルオープン
			wFinder.open();
			int count = ((InstockPlanFinder)wFinder).search(skey);
			// 初期化
			wLength = count;
			wCurrent = 0;
		}
			
		/**
		 * このクラスは InStockPlan エンテイテイ を InstockReceiveParameter パラメータに設定する。 <BR>
		 * 
		 * @param instockPlan 入荷予定情報
		 * @return InstockReceiveParameter[] 入荷予定情報をセットした<CODE>InstockReceiveParameter</CODE>クラス
		 */
		private InstockReceiveParameter[] convertToInStockParams(InstockPlan[] instockPlan)
		{
			InstockReceiveParameter[] stParam = null;
			
			if (instockPlan == null || instockPlan.length==0)
			{	
			 	return null;
			}
			stParam = new InstockReceiveParameter[instockPlan.length];
			for (int i = 0; i < instockPlan.length; i++)
			{
					stParam[i] = new InstockReceiveParameter();
					stParam[i].setConsignorCode(instockPlan[i].getConsignorCode());
					stParam[i].setConsignorName(instockPlan[i].getConsignorName());
				
			}
				
			return stParam;
		}
}
//end of class
