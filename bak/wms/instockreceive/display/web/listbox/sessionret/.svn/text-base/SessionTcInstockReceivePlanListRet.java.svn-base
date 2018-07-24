package jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret;

import java.sql.Connection;
/*
 * Created on Nov 2, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
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
 * <B>1.検索処理(<CODE>SessionTcInstockReceivePlanListRet(Connection, InstockReceiveParameter)</CODE>メソッド)<BR></B>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(InstockReceiveParameter)</CODE>メソッドを呼び出し入荷予定情報の検索を行います。<BR>
 * <BR>
 *   ＜入力データ＞*必須項目
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th><tr>
 *     <tr><td>*</td><td>状態フラグ</td><td>：</td><td>削除以外</td></tr>
 *     <tr><td>*</td><td>TC/DC区分</td><td>：</td><td>TcdcFlag</td></tr>
 *     <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td></td><td>開始入荷予定日</td><td>：</td><td>FromPlanDate</td></tr>
 *     <tr><td></td><td>終了入荷予定日</td><td>：</td><td>ToPlanDate</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>開始伝票No.</td><td>：</td><td>FromTicketNo</td></tr>
 *     <tr><td></td><td>終了伝票No.</td><td>：</td><td>ToTicketNo</td></tr>
 *     <tr><td></td><td>商品コード</td><td>：</td><td>ItemCode</td></tr>
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
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>仕入先ｺｰﾄﾞ</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>仕入先名称</td><td>：</td><td>SupplierName</td></tr>
 *     <tr><td></td><td>出荷先ｺｰﾄﾞ</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td></td><td>出荷先名称</td><td>：</td><td>CustomerName</td></tr>
 *     <tr><td></td><td>入荷伝票No.</td><td>：</td><td>InstockTicketNo</td></tr>
 *     <tr><td></td><td>入荷行No.</td><td>：</td><td>InstockLineNo</td></tr>
 *     <tr><td></td><td>商品ｺｰﾄﾞ</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>商品名称</td><td>：</td><td>ItemName</td></tr>
 *     <tr><td></td><td>ボール入数</td><td>：</td><td>BundleEnteringQty</td></tr>
 *     <tr><td></td><td>ケース入数</td><td>：</td><td>EnteringQty</td></tr>
 *     <tr><td></td><td>賞味期限</td><td>：</td><td>UseByDate</td></tr>
 *     <tr><td></td><td>予定ケース数</td><td>：</td><td>PlanCaseQty</td></tr>
 *     <tr><td></td><td>予定ピース数</td><td>：</td><td>PlanPieceQty</td></tr>
 *     <tr><td></td><td>実績ケース数</td><td>：</td><td>ResultCaseQty</td></tr>
 *     <tr><td></td><td>実績ピース数</td><td>：</td><td>ResultPieceQty</td></tr>
 *     <tr><td></td><td>状態</td><td>：</td><td>StatusName</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/02	</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:13 $
 * @author  $Author: mori $
 */


public class SessionTcInstockReceivePlanListRet extends SessionRet
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * 荷主名称取得用
	 */
	private String wConsignorName = "";
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:13 $");
	}

	/**
	 * 検索を行うための<CODE>find(InstockReceiveParameter param)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(InstockReceiveParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      <code>InstockReceiveParameter</code> 検索条件を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionTcInstockReceivePlanListRet(Connection conn, InstockReceiveParameter param) throws Exception
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
	 * 入荷予定日<BR>
	 * 仕入先コード<BR>
	 * 仕入先名称<BR>
	 * 出荷先コード<BR>
	 * 出荷先名称<BR>
	 * 入荷伝票No.<BR>
	 * 入荷行No.<BR>
	 * 商品コード<BR>
	 * 商品名称<BR>
	 * ボール入数<BR>
	 * ケース入数<BR>
	 * 賞味期限<BR>
	 * 予定ケース数<BR>
	 * 予定ピース数<BR>
	 * 実績ケース数<BR>
	 * 実績ピース数<BR>
	 * 状態<BR>
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
				resultArray = convertToTcInstockParams(temp);
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
	
	// Private methods -----------------------------------------------
	/**
	 * 入力されたパラメータをもとにSQL文を発行します。<BR>
	 * 検索を行う<code>InSotckPlanFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param param      <code>InstockReceiveParameter</code> 検索状態を含むパラメータ
	 */
	private void find(InstockReceiveParameter searchParam) throws Exception
	{	
		InstockPlanSearchKey searchKey = new InstockPlanSearchKey();
		// 荷主コード
		searchKey.setConsignorCode(searchParam.getConsignorCode());
		// 状態フラグに削除以外をセットします
		searchKey.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_DELETE,"!=");
		// TCDC区分がTCのみ
		searchKey.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC);
		// 開始入荷予定日
		if(!StringUtil.isBlank(searchParam.getFromPlanDate()))
		{
			searchKey.setPlanDate(searchParam.getFromPlanDate(),">=");
		}		
		// 終了入荷予定日
		if(!StringUtil.isBlank(searchParam.getToPlanDate()))
		{
			searchKey.setPlanDate(searchParam.getToPlanDate(),"<=");
		}
		// 仕入先コード
		if(!StringUtil.isBlank(searchParam.getSupplierCode()))
		{
			searchKey.setSupplierCode(searchParam.getSupplierCode());
		}
		
		// 出荷先コード
		if(!StringUtil.isBlank(searchParam.getCustomerCode()))
		{
			searchKey.setCustomerCode(searchParam.getCustomerCode());
		}
		
		// 開始伝票No.
		if(!StringUtil.isBlank(searchParam.getFromTicketNo()))
		{
			searchKey.setInstockTicketNo(searchParam.getFromTicketNo(),">=");
		}		
		// 終了伝票No.
		if(!StringUtil.isBlank(searchParam.getToTicketNo()))
		{
			searchKey.setInstockTicketNo(searchParam.getToTicketNo(),"<=");
		}		
		// 商品コード
		if(!StringUtil.isBlank(searchParam.getItemCode()))
		{
			searchKey.setItemCode(searchParam.getItemCode());
		}
		
		// 状態フラグ
		if(!StringUtil.isBlank(searchParam.getStatusFlag()))
		{
			
			if(!InstockReceiveParameter. STATUS_FLAG_ALL.equals(searchParam.getStatusFlag()) )
			{
				searchKey.setStatusFlag(searchParam.getStatusFlag());
			}
		}
		// 取得順をセットします		
		searchKey.setConsignorCodeCollect("");
		searchKey.setConsignorNameCollect("");
		searchKey.setPlanDateCollect("");
		searchKey.setSupplierCodeCollect("");
		searchKey.setSupplierName1Collect("");
		searchKey.setCustomerCodeCollect("");
		searchKey.setCustomerName1Collect("");
		searchKey.setInstockTicketNoCollect("");
		searchKey.setInstockLineNoCollect("");
		searchKey.setItemCodeCollect("");
		searchKey.setItemName1Collect("");
		searchKey.setEnteringQtyCollect("");
		searchKey.setBundleEnteringQtyCollect("");
		searchKey.setCasePieceFlagCollect("");
		searchKey.setPlanQtyCollect("");
		searchKey.setResultQtyCollect("");
		searchKey.setStatusFlagCollect("");
		searchKey.setUseByDateCollect("");
		searchKey.setTcdcFlagCollect("");
		
		// ソート順をセットします	
		searchKey.setPlanDateOrder(1,true);
		searchKey.setSupplierCodeOrder(2,true);
		searchKey.setCustomerCodeOrder(3,true);
		searchKey.setInstockTicketNoOrder(4,true);
		searchKey.setInstockLineNoOrder(5,true);
		
		wFinder = new InstockPlanFinder(wConn);
		// カーソルオープン
		wFinder.open();
		int count = ((InstockPlanFinder)wFinder).search(searchKey);
		// 初期化
		wLength = count;
		wCurrent = 0;
		
		searchKey.setRegistDateOrder(1, false);
		
		// 荷主名称検索
		InstockPlanFinder Finder = new InstockPlanFinder(wConn);
		Finder.open();
		int nameCount = Finder.search(searchKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			InstockPlan Instockplan[] = (InstockPlan[]) Finder.getEntities(0, 1);

			if (Instockplan != null && Instockplan.length != 0)
			{
				wConsignorName = Instockplan[0].getConsignorName();
			}
		}
		Finder.close();
	
	}
	
	/**
	 * このクラスは InStockPlan エンティティ を InstockReceiveParameter パラメータに変換します。 <BR>
	 * 
	 * @param inStockPlan 入荷予定情報
	 * @return InstockReceiveParameter[] 入荷予定情報をセットした<CODE>InstockReceiveParameter</CODE>クラス
	 */
	private InstockReceiveParameter[] convertToTcInstockParams(InstockPlan[] inStockPlan)
	{
		InstockReceiveParameter[] inStock = new InstockReceiveParameter[inStockPlan.length];
		for(int i=0;i<inStockPlan.length;++i)
		{
			inStock [i] = new InstockReceiveParameter();
			
			inStock [i].setConsignorCode(inStockPlan[i].getConsignorCode());
			inStock [i].setConsignorName(wConsignorName);
			inStock [i].setPlanDate(inStockPlan[i].getPlanDate());
			inStock [i].setSupplierCode(inStockPlan[i].getSupplierCode());
			inStock [i].setSupplierName(inStockPlan[i].getSupplierName1());
			inStock [i].setCustomerCode(inStockPlan[i].getCustomerCode());
			inStock [i].setCustomerName(inStockPlan[i].getCustomerName1());
			inStock [i].setInstockTicketNo(inStockPlan[i].getInstockTicketNo());
			inStock [i].setInstockLineNo(inStockPlan[i].getInstockLineNo());
			inStock [i].setItemCode(inStockPlan[i].getItemCode());
			inStock [i].setItemName(inStockPlan[i].getItemName1());			
			inStock [i].setBundleEnteringQty(inStockPlan[i].getBundleEnteringQty());			
			int entering_Qty = inStockPlan[i].getEnteringQty();
			inStock [i].setEnteringQty(entering_Qty);
			
			inStock [i].setUseByDate(inStockPlan[i].getUseByDate());
			inStock [i].setStatusName(DisplayUtil.getInstockPlanStatusValue(inStockPlan[i].getStatusFlag()));
			
			// ケース・ピース区分がケース、または指定なしのとき 
			if (SystemDefine.CASEPIECE_FLAG_CASE.equals(inStockPlan[i].getCasePieceFlag())|| SystemDefine.CASEPIECE_FLAG_NOTHING.equals(inStockPlan[i].getCasePieceFlag()))
			{	
				
				int planQty = inStockPlan[i].getPlanQty();
				int resultQty = inStockPlan[i].getResultQty();
				if (inStockPlan[i].getEnteringQty() > 0)
				{	
					 inStock [i].setPlanCaseQty(planQty/entering_Qty);
					 inStock [i].setPlanPieceQty(planQty%entering_Qty);
					
					 inStock [i].setResultCaseQty(resultQty/entering_Qty);
					 inStock [i].setResultPieceQty(resultQty%entering_Qty);
					 
				}else
				{
					inStock [i].setPlanCaseQty(0);
					inStock [i].setPlanPieceQty(inStockPlan[i].getPlanQty());
					inStock [i].setResultCaseQty(0);
					inStock [i].setResultPieceQty(inStockPlan[i].getResultQty());
				}
				
		   }else
		   {
				inStock [i].setPlanCaseQty(0);
				inStock [i].setPlanPieceQty(inStockPlan[i].getPlanQty());
				inStock [i].setResultCaseQty(0);
				inStock [i].setResultPieceQty(inStockPlan[i].getResultQty());
		   }
				
		}
		return inStock;		
	}
}
//end of class