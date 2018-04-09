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
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * 実績情報Viewを検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * <B>1.検索処理(<CODE>SessionInstockReceiveItemResultRet(Connection, InstockReceiveParameter)</CODE>メソッド)<BR></B>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(InstockReceiveParameter)</CODE>メソッドを呼び出し実績情報Viewの検索を行います。<BR>
 * <BR>
 *   ＜入力データ＞*必須項目
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th><tr>
 *     <tr><td>*</td><td>作業区分</td><td>：</td><td>01(入荷)</td></tr>
 *     <tr><td></td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>開始入荷日</td><td>：</td><td>FromInstockReceiveDate</td></tr>
 *     <tr><td></td><td>終了入荷日</td><td>：</td><td>ToInstockReceiveDate</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>開始伝票No.</td><td>：</td><td>FromTicketNo</td></tr>
 *     <tr><td></td><td>終了伝票No.</td><td>：</td><td>ToTicketNo</td></tr>
 *     <tr><td></td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
 *     <tr><td></td><td>状態フラグ</td><td>：</td><td>StatusFlag</td></tr>
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
 *     <tr><td></td><td>商品ｺｰﾄﾞ</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>商品名称</td><td>：</td><td>ItemName</td></tr>
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

public class SessionInstockReceiveItemResultRet extends SessionRet
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
		public SessionInstockReceiveItemResultRet(Connection conn, InstockReceiveParameter param) throws Exception
		{
			this.wConn = conn;
			find(param);
		}

		// Public methods ------------------------------------------------

		/**
		 * <CODE>DNRESULTVIEW</CODE>の検索結果を返します。
		 * <DIR>
		 * ＜検索結果＞
		 *  商品コード<BR>
		 * 	商品名称<BR>
		 * </DIR>
		 * @return DNRESULTVIEWの検索結果
		 */
		public Parameter[] getEntities()
		{
			InstockReceiveParameter[] resultArray = null;
			ResultView temp[] = null;
			if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
			{	
				try
				{	
					temp = (ResultView[])((ResultViewFinder)wFinder).getEntities(wStartpoint, wEndpoint);
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
		 * 検索を行う<code>ResultViewFinder</code>はインスタンス変数として保持します。<BR>
		 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
		 * @param param      <code>InstockReceiveParameter</code> 検索条件を含むパラメータ
		 */
		private void find(InstockReceiveParameter param) throws Exception
		{	

			ResultViewSearchKey skey = new ResultViewSearchKey();
			// 検索実行
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				skey.setConsignorCode(param.getConsignorCode());
			}

			if (!StringUtil.isBlank(param.getFromInstockReceiveDate()))
			{
				//開始入荷日をセット。
				skey.setWorkDate(param.getFromInstockReceiveDate(),">=");
			}
			if (!StringUtil.isBlank(param.getToInstockReceiveDate()))
			{
				//終了入荷日
				skey.setWorkDate(param.getToInstockReceiveDate(),"<=");
			}
			
			if (!StringUtil.isBlank(param.getSupplierCode()))
			{
				// 仕入先コード	
				skey.setSupplierCode(param.getSupplierCode());
			}
			// 出荷先コード
			if (!StringUtil.isBlank(param.getCustomerCode()))
			{		
				skey.setCustomerCode(param.getCustomerCode());
			}
			// 開始伝票No.
			if (!StringUtil.isBlank(param.getFromTicketNo()))
			{		
				skey.setInstockTicketNo(param.getFromTicketNo(),">=");
			}
			
			// 終了伝票No.
			if (!StringUtil.isBlank(param.getToTicketNo()))
			{		
				skey.setInstockTicketNo(param.getToTicketNo(),"<=");
			}
			
			
			// 商品コード
			if (!StringUtil.isBlank(param.getItemCode()))
			{		
				skey.setItemCode(param.getItemCode());
			}
			else
			{
				skey.setItemCode("","IS NOT NULL");
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
			
			// 作業区分
			skey.setJobType(ResultView.JOB_TYPE_INSTOCK);
			
			// グループ順をセットします
			skey.setItemCodeGroup(1);
			skey.setItemName1Group(2);
			// 取得順をセットします
			skey.setItemCodeCollect("");
			skey.setItemName1Collect("");
			
			// ソート順をセットします
			skey.setItemCodeOrder(1, true);
			skey.setItemName1Order(2,true);
			
			// 作業状態
			if(param.getSearchStatus() != null && param.getSearchStatus().length > 0)
			{
				String[] search = new String[param.getSearchStatus().length];
				for(int i = 0; i < param.getSearchStatus().length; ++i)
				{
					if(param.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
					{
						search[i] = ResultView.STATUS_FLAG_UNSTART;
					}
					else if(param.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_STARTED))
					{
						search[i] = ResultView.STATUS_FLAG_START;
					}
					else if(param.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
					{
						search[i] = ResultView.STATUS_FLAG_NOWWORKING;
					}
					else if(param.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION))
					{
						search[i] = ResultView.STATUS_FLAG_COMPLETE_IN_PART;
					}
					else if(param.getSearchStatus()[i].equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
					{
						search[i] = ResultView.STATUS_FLAG_COMPLETION;
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
				skey.setStatusFlag(ResultView.STATUS_FLAG_DELETE, "!=");
			}
			wFinder = new ResultViewFinder(wConn);
			// カーソルオープン
			wFinder.open();
			int count = ((ResultViewFinder)wFinder).search(skey);
			// 初期化
			wLength = count;
			wCurrent = 0;
		}
			
		/**
		 * このクラスは ResultView エンティティ を InstockReceiveParameter パラメータに設定する。 <BR>
		 * 
		 * @param instockVIew 実績情報View
		 * @return InstockReceiveParameter[] 実績情報Viewをセットした<CODE>InstockReceiveParameter</CODE>クラス
		 */
		private InstockReceiveParameter[] convertToInStockParams(ResultView[] instockVIew)
		{
			InstockReceiveParameter[] stParam = null;
			
			if (instockVIew == null || instockVIew.length==0)
			{	
			 	return null;
			}
				stParam = new InstockReceiveParameter[instockVIew.length];
				for (int i = 0; i < instockVIew.length; i++)
				{
						stParam[i] = new InstockReceiveParameter();
						stParam[i].setItemCode(instockVIew[i].getItemCode());
						stParam[i].setItemName(instockVIew[i].getItemName1());
					
				}
				
			return stParam;
		}
}
//end of class
