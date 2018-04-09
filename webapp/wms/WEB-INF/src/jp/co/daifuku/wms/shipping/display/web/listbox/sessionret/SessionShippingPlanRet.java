// $Id: SessionShippingPlanRet.java,v 1.1.1.1 2006/08/17 09:34:28 mori Exp $
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
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * <CODE>SessionShippingPlanRet</CODE>クラスは、出荷予定リスト用の検索を行うクラスです。<BR>
 * 検索条件をパラメータとして受け取り、出荷予定データ一覧の検索を行います。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionShippingPlanRet(Connection conn,	ShippingParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(ShippingParameter param)</CODE>メソッドを呼び出し出荷予定情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目*<BR>
 *   <DIR>
 *     荷主コード*<BR>
 *     出荷予定日<BR>
 *     出荷先コード<BR>
 *     伝票No.<BR>
 *     呼び出し画面フラグ<BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DNSHIPPINGPLAN <BR>
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
 *     出荷予定日<BR>
 *     出荷先コード<BR>
 *     出荷先名称<BR>
 *     伝票No.<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2008/08/18</TD><TD>M.INOUE</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:28 $
 * @author  $Author: mori $
 */
public class SessionShippingPlanRet extends SessionRet
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

	/**
	 * 検索を行うための<CODE>find(ShippingParameter param)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(ShippingParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * <DIR>
	 * ＜検索条件＞必須項目*<BR>
	 *     <DIR>
	 *     荷主コード*<BR>
	 *     出荷予定日<BR>
	 *     出荷先コード<BR>
	 *     伝票No.<BR>
	 *     状態フラグ<BR>
	 *     </DIR>
	 * </DIR>
	 * 登録画面からの呼び出しは状態フラグは削除以外<BR>
	 * 修正・削除画面からの呼び出しは状態フラグは未開始<BR>
	 * をパラメータからセットしてください。
	 * @param conn       <code>Connection</code>
	 * @param param      <code>ShippingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionShippingPlanRet(Connection conn, ShippingParameter param) throws Exception
	{
		this.wConn = conn;
		
		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------

	/**
	 * <CODE>DnShippingPlan</CODE>の検索結果を返します。
	 * <DIR>
	 * ＜検索結果＞<BR>
	 * <DIR>
	 *     出荷予定日<BR>
	 *     出荷先コード<BR>
	 *     出荷先名称<BR>
	 *     伝票No.<BR>
	 * </DIR>
	 * </DIR>
	 * @return DnShippingPlanの検索結果
	 */
	public ShippingPlan[] getEntities()
	{
		ShippingPlan[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (ShippingPlan[]) ((ShippingPlanFinder)wFinder).getEntities(wStartpoint, wEndpoint);
				if(resultArray!=null && resultArray.length!=0)
				{
					resultArray[0].setConsignorName(wConsignorName);
				}
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
	 * 検索を行う<code>ShippingPlanFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param param      <code>ShippingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	private void find(ShippingParameter param) throws Exception
	{
		int count = 0;

		ShippingPlanSearchKey skey = new ShippingPlanSearchKey();
		ShippingPlanSearchKey consignorkey = new ShippingPlanSearchKey();
		// 入力された検索条件セット		
		// 荷主コード
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
			consignorkey.setConsignorCode(param.getConsignorCode());
		}
		// 出荷予定日
		if (!StringUtil.isBlank(param.getPlanDate()))
		{
			skey.setPlanDate(param.getPlanDate());
			consignorkey.setPlanDate(param.getPlanDate());
		}
		// 出荷先コード
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			skey.setCustomerCode(param.getCustomerCode());
			consignorkey.setCustomerCode(param.getCustomerCode());
		}

		// 画面の検索条件をセット
		if(param.getSearchStatus() != null && param.getSearchStatus().length > 0)
		{
			String[] search = new String[param.getSearchStatus().length];
			for(int i = 0; i < param.getSearchStatus().length; i++)
			{
				if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_UNSTARTING))
				{
					search[i] = ShippingPlan.STATUS_FLAG_UNSTART;
				}
				else if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_START))
				{
					search[i] = ShippingPlan.STATUS_FLAG_START;
				}
				else if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_NOWWORKING))
				{
					search[i] = ShippingPlan.STATUS_FLAG_NOWWORKING;
				}
				else if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART))
				{
					search[i] = ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_FINISH))
				{
					search[i] = ShippingPlan.STATUS_FLAG_COMPLETION;
				}
				else if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_ALL))
				{
					search[i] = "*";
				}
			}
			skey.setStatusFlag(search);
			consignorkey.setStatusFlag(search);
		}
		else
		{
			skey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
			consignorkey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		}
		skey.setPlanDateOrder(1, true);
		skey.setCustomerCodeOrder(2, true);
		skey.setCustomerName1Order(3, true);
		skey.setShippingTicketNoOrder(4, true);
		
		// 集約条件
		skey.setPlanDateGroup(1);
		skey.setCustomerCodeGroup(2);
		skey.setCustomerName1Group(3);
		skey.setShippingTicketNoGroup(4);
		skey.setPlanDateCollect("");
		skey.setCustomerCodeCollect("");
		skey.setCustomerName1Collect("");
		skey.setShippingTicketNoCollect("");
		
		
		wFinder = new ShippingPlanFinder(wConn);
		// カーソルオープン
		wFinder.open();
		count = ((ShippingPlanFinder)wFinder).search(skey);
		// 初期化
		wLength = count;
		wCurrent = 0;
		
		// 荷主名称を取得する
		consignorkey.setConsignorNameCollect("");
		consignorkey.setRegistDateOrder(1, false);

		ShippingPlanFinder consignorFinder = new ShippingPlanFinder(wConn);
		consignorFinder.open();
		int nameCount = consignorFinder.search(consignorkey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			ShippingPlan winfo[] = (ShippingPlan[]) consignorFinder.getEntities(0, 1);

			if (winfo != null && winfo.length != 0)
			{
				wConsignorName = winfo[0].getConsignorName();
			}
		}
		consignorFinder.close();
	}
}
//end of class
