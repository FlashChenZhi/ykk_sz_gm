// $Id: SessionShippingPlanTicketRet.java,v 1.1.1.1 2006/08/17 09:34:28 mori Exp $
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
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * 
 * <CODE>SessionShippingPlanTicketRet</CODE>クラスは、伝票No.の検索を行い結果を保持するクラスです。<BR>
 * このクラスを使用する場合は、DBとのセッションを保持するため、このクラスへの参照を保持してください。<BR>
 * 使用後に、<CODE>closeConnection()</CODE>メソッドを使用して、セッションを閉じてください。<BR>
 * <BR>
 * このクラスは、以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionShippingPlanTicketRet(Connection conn, ShippingParameter param)</CODE>コンストラクタ)<BR>
 * 	<DIR>
 * 	リストボックスの初期表示時に行われます。<BR>
 * 	<CODE>find(ShippingParameter param)</CODE>メソッドを呼び出し、出荷予定情報を検索し、<BR>
 * 	伝票No.の一覧を保持します。<BR>
 * 	<BR>
 * 	＜検索条件＞ *必須入力<BR>
 * 	<DIR>
 * 	荷主コード*<BR>
 *  状態 <BR>
 * 	出荷予定日<BR>
 * 	開始出荷予定日<BR>
 * 	終了出荷予定日<BR>
 *  出荷先コード<BR>
 *  伝票No.<BR>
 *  開始伝票No.<BR>
 *  終了伝票No.<BR>
 * 	</DIR>
 * 	</DIR>
 *  ＜検索テーブル＞ <BR>
 *  <DIR>
 *    DNSHIPPINGPLAN
 *  </DIR>
 * 	<BR>
 * 2.表示レコード取得処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * 	<DIR>
 * 	画面表示するレコードを取得します。
 * 	検索処理で検索した結果から、表示する分を<CODE>ShippingPlan</CODE>クラスの配列で返します。<BR>
 * 	<BR>
 * 	＜項目＞<BR>
 * 	<DIR>
 * 	伝票No.<BR>
 * 	</DIR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/23</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:28 $
 * @author  $Author: mori $
 */
public class SessionShippingPlanTicketRet extends SessionRet
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
	 * 出荷予定情報の検索処理と結果の件数取得を行います。
	 * 
	 * @param	conn	<code>Connection</code>
	 * @param	param	<CODE>ShippingParameter</CODE>クラス
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionShippingPlanTicketRet(Connection conn, ShippingParameter param) throws Exception
	{
		// コネクションの保持
		wConn = conn ;
		
		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * <CODE>出荷予定情報</CODE>の検索結果を返します。 <BR>
	 * <DIR>
	 * ＜検索結果＞ <BR>
	 *   <DIR>
	 *     伝票No.<BR>
	 *   </DIR>
	 * </DIR>
	 * 
	 * @return	<CODE>ShippingPlan</CODE>クラスの配列
	 */
	public ShippingPlan[] getEntities()
	{
		ShippingPlan[] resultArray = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray =
					(ShippingPlan[]) ((ShippingPlanFinder) wFinder).getEntities(
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
	 * パラメータにセットされた条件にしたがって、出荷予定情報を検索し、<BR>
	 * 伝票No.の一覧と結果の件数を保持します。<BR>
	 * @param	param	<CODE>ShippingParameter</CODE>クラス<BR>
	 * @throws Exception 何らかの例外が発生した場合に通知されます。<BR>
	 */
	private void find(ShippingParameter param) throws Exception
	{
		// Finderインスタンス生成
		wFinder = new ShippingPlanFinder(wConn);
		ShippingPlanSearchKey shippingPlanSearchKey = new ShippingPlanSearchKey();
		
		// 検索条件をセット
		shippingPlanSearchKey.setShippingTicketNoCollect("");
		if(!StringUtil.isBlank(param.getConsignorCode()))
		{
			shippingPlanSearchKey.setConsignorCode(param.getConsignorCode());
		}
		
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
			shippingPlanSearchKey.setStatusFlag(search);
		}
		else
		{
			shippingPlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		}
		// 状態フラグ 削除以外
		shippingPlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		
		// 出荷予定日がセットされている場合
		if(!StringUtil.isBlank(param.getPlanDate()))
		{
			shippingPlanSearchKey.setPlanDate(param.getPlanDate());
		}
		// 開始出荷予定日 or 終了出荷予定日がセットされている場合
		else
		{
			// 開始出荷予定日がセットされている
			if(!StringUtil.isBlank(param.getFromPlanDate()))
			{
				shippingPlanSearchKey.setPlanDate(param.getFromPlanDate(), ">=");
			}
			// 終了出荷予定日がセットされている
			if(!StringUtil.isBlank(param.getToPlanDate()))
			{
				shippingPlanSearchKey.setPlanDate(param.getToPlanDate(), "<=");
			}
		}
		if(!StringUtil.isBlank(param.getCustomerCode()))
		{
			shippingPlanSearchKey.setCustomerCode(param.getCustomerCode());
		}
		
		// 伝票No.がセットされている場合
		if(!StringUtil.isBlank(param.getShippingTicketNo()))
		{
			shippingPlanSearchKey.setShippingTicketNo(param.getShippingTicketNo());
		}
		// 開始伝票No. or 終了伝票No.がセットされている場合
		else
		{
			if(!StringUtil.isBlank(param.getFromTicketNo()))
			{
				shippingPlanSearchKey.setShippingTicketNo(param.getFromTicketNo());
			}
			if(!StringUtil.isBlank(param.getToTicketNo()))
			{
				shippingPlanSearchKey.setShippingTicketNo(param.getToTicketNo());
			}
		}
		
		shippingPlanSearchKey.setShippingTicketNoGroup(1);
		shippingPlanSearchKey.setShippingTicketNoOrder(1, true);
		
		// Finderのカーソルオープン
		wFinder.open();
		
		// 検索（結果の件数を取得）
		wLength = wFinder.search(shippingPlanSearchKey);
		
		// 現在の表示件数を初期化
		wCurrent = 0;
	}
	
}
//end of class
