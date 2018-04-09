// $Id: SessionTcInstockReceiveListRet.java,v 1.1.1.1 2006/08/17 09:34:13 mori Exp $
package jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * <BR>
 * 作業情報を検索し表示するためのクラスです。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * <B>1.検索処理(<CODE>SessionTcInstockReceiveListRet(Connection, InstockReceiveParameter)</CODE>メソッド)<BR></B>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(InstockReceiveParameter)</CODE>メソッドを呼び出し作業情報の検索を行います。<BR>
 * <BR>
 *   ＜入力データ＞*必須項目
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>画面名称</th><td>：</td><th>パラメータ名</th><tr>
 *     <tr><td>*</td><td>荷主コード</td><td>：</td><td>ConsignorCode</td></tr>
 *     <tr><td>*</td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>仕入先コード</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>出荷先コード</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td>*</td><td>表示順</td><td>：</td><td>DspOrder</td></tr>
 *     <tr><td>*</td><td>作業状態</td><td>：</td><td>StatusFlag</td></tr>
 *   </table>
 *   </DIR>
 *   ＜検索テーブル＞
 *   <DIR>
 *     DNWORKINFO<BR>
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
 *     <tr><td></td><td>入荷予定日</td><td>：</td><td>PlanDate</td></tr>
 *     <tr><td></td><td>仕入先ｺｰﾄﾞ</td><td>：</td><td>SupplierCode</td></tr>
 *     <tr><td></td><td>仕入先名称</td><td>：</td><td>SupplierName</td></tr>
 *     <tr><td></td><td>出荷先ｺｰﾄﾞ</td><td>：</td><td>CustomerCode</td></tr>
 *     <tr><td></td><td>出荷先名称</td><td>：</td><td>CustomerName</td></tr>
 *     <tr><td></td><td>伝票No.</td><td>：</td><td>InstockTicketNo</td></tr>
 *     <tr><td></td><td>行No.</td><td>：</td><td>InstockLineNo</td></tr>
 *     <tr><td></td><td>商品ｺｰﾄﾞ</td><td>：</td><td>ItemCode</td></tr>
 *     <tr><td></td><td>商品名称</td><td>：</td><td>ItemName</td></tr>
 *     <tr><td></td><td>入荷総数</td><td>：</td><td>TotalPlanQty</td></tr>
 *     <tr><td></td><td>ｹｰｽ入数</td><td>：</td><td>EnteringQty</td></tr>
 *     <tr><td></td><td>ﾎﾞｰﾙ入数</td><td>：</td><td>BundleEnteringQty</td></tr>
 *     <tr><td></td><td>作業予定ｹｰｽ数</td><td>：</td><td>PlanCaseQty</td></tr>
 *     <tr><td></td><td>作業予定ﾋﾟｰｽ数</td><td>：</td><td>PlanPieceQty</td></tr>
 *     <tr><td></td><td>実績ｹｰｽ数</td><td>：</td><td>ResultCaseQty</td></tr>
 *     <tr><td></td><td>実績ﾋﾟｰｽ数</td><td>：</td><td>ResultPieceQty</td></tr>
 *     <tr><td></td><td>賞味期限</td><td>：</td><td>UseByDate</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * 
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/2</TD><TD>H.Akiyama</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:13 $
 * @author  $Author: mori $
 */
public class SessionTcInstockReceiveListRet extends SessionRet
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
	public SessionTcInstockReceiveListRet(Connection conn, InstockReceiveParameter param) throws Exception
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
	 *   3.作業情報から表示データを取得し<CODE>InstockReceiveParameter</CODE>にセットします。<BR>
	 *   3.表示情報を返します。<BR>
	 * </DIR>
	 * 
	 * @return 表示情報を含む<CODE>InstockReceiveParameter</CODE>クラス
	 */
	public InstockReceiveParameter[] getEntities()
	{
		WorkingInformation[] view = null;
		InstockReceiveParameter[] resultParam = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				// 検索結果を取得する。
				view = (WorkingInformation[]) ((WorkingInformationFinder) wFinder).getEntities(wStartpoint, wEndpoint);
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
	 * パラメータから検索条件を取得し作業情報の検索を行うためのメソッドです。<BR>
	 * 
	 * @param param   検索条件を取得するためのパラメータ
	 */
	private void find(InstockReceiveParameter param) throws Exception
	{
		int count = 0;

		WorkingInformationSearchKey viewKey = new WorkingInformationSearchKey();
		WorkingInformationSearchKey consignorkey = new WorkingInformationSearchKey();
		
		// 検索条件をセットする
		// 荷主コード
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			viewKey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		// 入荷予定日
		if (!StringUtil.isBlank(param.getPlanDate()))
		{
			viewKey.setPlanDate(param.getPlanDate());
			consignorkey.setPlanDate(param.getPlanDate());
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

		// 作業状態
		if(!StringUtil.isBlank(param.getStatusFlag()))
		{   // 作業状態が全ての時、削除以外のフラグをセット
			if(param.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_ALL))
			{
				viewKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE,"!=");
				consignorkey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE,"!=");
			}
			// 未開始
			else if(param.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
			{
				viewKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
				consignorkey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			}
			// 作業中
			else if(param.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
			{
				viewKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
				consignorkey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
			}
			// 完了
			else if(param.getStatusFlag().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
			{
				viewKey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
				consignorkey.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			}
			else
			{
				String wDelim = MessageResource.DELIM;
				// 例外を通知する
				Object[] tObj = new Object[3];
				tObj[0] = this.getClass().getName();
				tObj[1] = "param.getStatusFlag";
				tObj[2] = param.getStatusFlag();
				// 6006009=範囲外の値を指定されました。セットできません。Class={0} Variable={1} Value={2}
				RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, this.getClass().getName(), tObj);
				throw (new ScheduleException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
			}
		}
		
		// TC/DC区分：TC
		viewKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
		consignorkey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);

		// 作業区分：入荷
		viewKey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);
		consignorkey.setJobType(WorkingInformation.JOB_TYPE_INSTOCK);

		// 表示順
		if(!StringUtil.isBlank(param.getDspOrder()))
		{   // 表示順が伝票No.順の時
			if(param.getDspOrder().equals(InstockReceiveParameter.DISPLAY_ORDER_TICKET))
			{
				// 仕入先コード
				viewKey.setSupplierCodeOrder(1,true);
				// 出荷先コード
				viewKey.setCustomerCodeOrder(2,true);
				// 伝票No.
				viewKey.setInstockTicketNoOrder(3,true);
				// 行No.
				viewKey.setInstockLineNoOrder(4,true);
				// 商品コード
				viewKey.setItemCodeOrder(5,true);
				// 賞味期限
				viewKey.setUseByDateOrder(6,true);
			}
			// 表示順が商品コード順の時
			else if(param.getDspOrder().equals(InstockReceiveParameter.DISPLAY_ORDER_ITEM))
			{
				// 仕入先コード
				viewKey.setSupplierCodeOrder(1,true);
				// 出荷先コード
				viewKey.setCustomerCodeOrder(2,true);
				// 商品コード
				viewKey.setItemCodeOrder(3,true);
				// 伝票No.
				viewKey.setInstockTicketNoOrder(4,true);
				// 行No.
				viewKey.setInstockLineNoOrder(5,true);
				// 賞味期限
				viewKey.setUseByDateOrder(6,true);
			}
		}

		wFinder = new WorkingInformationFinder(wConn);
		// カーソルオープン
		wFinder.open();
		// 検索を実行する
		count = ((WorkingInformationFinder) wFinder).search(viewKey);
		// 初期化
		wLength = count;
		wCurrent = 0;

		// 荷主名称を取得する
		consignorkey.setConsignorNameCollect("");
		consignorkey.setRegistDateOrder(1, false);

		WorkingInformationFinder consignorFinder = new WorkingInformationFinder(wConn);
		consignorFinder.open();
		int nameCountCsg = consignorFinder.search(consignorkey);
		if (nameCountCsg > 0 && nameCountCsg <= DatabaseFinder.MAXDISP)
		{
			WorkingInformation view[] = (WorkingInformation[]) consignorFinder.getEntities(0, 1);

			if (view != null && view.length != 0)
			{
				wConsignorName = view[0].getConsignorName();
			}
		}
		consignorFinder.close();

	}

	/**
	 * 作業情報を<CODE>InstockReceiveParameter</CODE>にセットするためのクラスです。<BR>
	 * 
	 * @param work 作業情報
	 * @return InstockReceiveParameter[] 作業情報をセットした<CODE>InstockReceiveParameter</CODE>クラス
	 */
	private InstockReceiveParameter[] getDispData(WorkingInformation[] work)
	{
		InstockReceiveParameter[] param = null;
		
		Vector tempVec = new Vector();

		for (int i = 0; i < work.length; i++)
		{
			InstockReceiveParameter tempParam = new InstockReceiveParameter();
			// 荷主コード
			tempParam.setConsignorCode(work[i].getConsignorCode());
			// 荷主名称
			tempParam.setConsignorName(wConsignorName);
			// 入荷予定日
			tempParam.setPlanDate(work[i].getPlanDate());
			// 仕入先コード
			tempParam.setSupplierCode(work[i].getSupplierCode());
			// 仕入先名称
			tempParam.setSupplierName(work[i].getSupplierName1());
			// 出荷先コード
			tempParam.setCustomerCode(work[i].getCustomerCode());
			// 出荷先名称
			tempParam.setCustomerName(work[i].getCustomerName1());
			// 伝票No.
			tempParam.setInstockTicketNo(work[i].getInstockTicketNo());
			// 行No.
			tempParam.setInstockLineNo(work[i].getInstockLineNo());
			// 商品コード
			tempParam.setItemCode(work[i].getItemCode());
			// 商品名称
			tempParam.setItemName(work[i].getItemName1());
			// 入荷総数
			tempParam.setTotalPlanQty(work[i].getPlanEnableQty());
			// ケース入数
			tempParam.setEnteringQty(work[i].getEnteringQty());
			// ボール入数
			tempParam.setBundleEnteringQty(work[i].getBundleEnteringQty());
			
			// ケース/ピース区分：ケースor指定なしの場合、ケース数/ピース数に分けて表示
			if(work[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE)
			    || work[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING))
			{
                //作業予定ケース数
				tempParam.setPlanCaseQty(DisplayUtil.getCaseQty(work[i].getPlanEnableQty(), work[i].getEnteringQty()));
				// 作業予定ピース数
				tempParam.setPlanPieceQty(DisplayUtil.getPieceQty(work[i].getPlanEnableQty(), work[i].getEnteringQty()));
				// 実績ケース数
				tempParam.setResultCaseQty(DisplayUtil.getCaseQty(work[i].getResultQty(), work[i].getEnteringQty()));
				// 実績ピース数
				tempParam.setResultPieceQty(DisplayUtil.getPieceQty(work[i].getResultQty(), work[i].getEnteringQty()));
			}
			// ケース/ピース区分：ピースの場合、全てピース数で表示(ケース数は'0')
			else if(work[i].getWorkFormFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE))
			{
			    //作業予定ケース数
				tempParam.setPlanCaseQty(0);
				// 作業予定ピース数
				tempParam.setPlanPieceQty(work[i].getPlanEnableQty());
				// 実績ケース数
				tempParam.setResultCaseQty(0);
				// 実績ピース数
				tempParam.setResultPieceQty(work[i].getResultQty());
			}
			
			// 賞味期限
			tempParam.setUseByDate(work[i].getResultUseByDate());
			
			tempVec.add(tempParam);

		}
		param = new InstockReceiveParameter[tempVec.size()];
		tempVec.copyInto(param);

		return param;
	}

}
//end of class
