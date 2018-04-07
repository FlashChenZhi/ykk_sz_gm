// $Id: SessionShippingPlanListRet.java,v 1.1.1.1 2006/08/17 09:34:28 mori Exp $
package jp.co.daifuku.wms.shipping.display.web.listbox.sessionret;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * 
 * <CODE>SessionShippingPlanListRet</CODE>クラスは、出荷予定リスト用の検索を行い結果を保持するクラスです。<BR>
 * このクラスを使用する場合は、DBとのセッションを保持するため、このクラスへの参照を保持してください。<BR>
 * 使用後に、<CODE>closeConnection()</CODE>メソッドを使用して、セッションを閉じてください。<BR>
 * <BR>
 * このクラスは、以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionShippingPlanListRet(Connection conn, ShippingParameter param)</CODE>コンストラクタ)<BR>
 * 	<DIR>
 * 	リストボックスの初期表示時に行われます。<BR>
 * 	<CODE>find(ShippingParameter param)</CODE>メソッドを呼び出し、出荷予定情報を検索し、<BR>
 * 	出荷予定日の一覧を保持します。<BR>
 * 	<BR>
 * 	＜検索条件＞ *必須入力<BR>
 *  <DIR>
 * 	荷主コード*<BR>
 * 	開始出荷予定日<BR>
 * 	終了出荷予定日<BR>
 *  出荷先コード<BR>
 *  伝票No.<BR>
 *  商品コード<BR>
 *  </DIR>
 * 	</DIR>
 * 	<BR>
 * 2.表示レコード取得処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * 	<DIR>
 * 	画面表示するレコードを取得します。
 * 	検索処理で検索した結果から、表示する分を<CODE>ShippingParameter</CODE>クラスの配列で返します。<BR>
 * 	<BR>
 * 	＜<CODE>ShippingParameter</CODE>クラスにセットする項目＞<BR>
 *  </DIR>
 *  荷主コード<BR>
 *  荷主名称<BR>
 *  出荷予定日<BR>
 *  出荷先コード<BR>
 *  出荷先名称<BR>
 *  伝票No.<BR>
 *  伝票行No.<BR>
 *  商品コード<BR>
 *  商品名称<BR>
 *  ケース入数<BR>
 *  ピース入数<BR>
 *  ホスト予定総数<BR>
 *  ホスト予定ケース数<BR>
 *  ホスト予定ピース数<BR>
 *  実績ケース数<BR>
 *  実績ピース数<BR>
 *  <DIR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/18</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:28 $
 * @author  $Author: mori $
 */
public class SessionShippingPlanListRet extends SessionRet
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * 表示する荷主名称
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
	 * 出荷予定情報の検索処理と結果の件数取得を行います。
	 * 
	 * @param	conn	データベースコネクション
	 * @param	param	<CODE>ShippingParameter</CODE>クラス
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionShippingPlanListRet(Connection conn, ShippingParameter param) throws Exception
	{
		// コネクションの保持
		wConn = conn;

		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * 出荷予定情報<CODE>(Dnshippingplan)</CODE>の検索結果を、指定件数分返します。<BR>
	 * 
	 * @return	<CODE>ShippingParameter</CODE>クラスの配列
	 */
	public ShippingParameter[] getEntities()
	{
		ShippingPlan[] resultArray = null;
		ShippingParameter[] param = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				resultArray = (ShippingPlan[]) ((ShippingPlanFinder) wFinder).getEntities(wStartpoint, wEndpoint);

				// 表示用データを取得
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
	 * パラメータにセットされた条件にしたがって、出荷予定情報を検索し、<BR>
	 * 出荷予定の一覧と結果の件数を保持します。<BR>
	 * 検索結果を取得するには<CODE>getEntities</CODE>メソッドを呼ぶ必要があります。 <BR>
	 * @param	param	<CODE>ShippingParameter</CODE>クラス
 	 * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
	 */
	private void find(ShippingParameter param) throws ReadWriteException
	{
		// Finderインスタンス生成
		wFinder = new ShippingPlanFinder(wConn);
		ShippingPlanSearchKey shippingPlanSearchKey = new ShippingPlanSearchKey();

		// 検索条件をセット
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			shippingPlanSearchKey.setConsignorCode(param.getConsignorCode());
		}
		if (!StringUtil.isBlank(param.getFromPlanDate()))
		{
			shippingPlanSearchKey.setPlanDate(param.getFromPlanDate(), ">=");
		}
		if (!StringUtil.isBlank(param.getToPlanDate()))
		{
			shippingPlanSearchKey.setPlanDate(param.getToPlanDate(), "<=");
		}
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			shippingPlanSearchKey.setCustomerCode(param.getCustomerCode());
		}
		if (!StringUtil.isBlank(param.getShippingTicketNo()))
		{
			shippingPlanSearchKey.setShippingTicketNo(param.getShippingTicketNo());
		}
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			shippingPlanSearchKey.setItemCode(param.getItemCode());
		}

		//状態フラグ を セット。
		if (!StringUtil.isBlank(param.getStatusFlag()))
		{
			if (param.getStatusFlag().equals(ShippingParameter.WORKSTATUS_UNSTARTING))
			{
				//未開始
				shippingPlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
			}
			else if (param.getStatusFlag().equals(ShippingParameter.WORKSTATUS_NOWWORKING))
			{
				//作業中
				shippingPlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
			}
			else if (param.getStatusFlag().equals(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART))
			{
				//保留
				shippingPlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETE_IN_PART);
			}
			else if (param.getStatusFlag().equals(ShippingParameter.WORKSTATUS_FINISH))
			{
				//完了
				shippingPlanSearchKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
			}
			else if (param.getStatusFlag().equals(ShippingParameter.WORKSTATUS_ALL))
			{
				//do nothing 全て
				shippingPlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "<>");
			}
		}
		
		shippingPlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		shippingPlanSearchKey.setPlanDateOrder(1, true);
		shippingPlanSearchKey.setCustomerCodeOrder(2, true);
		shippingPlanSearchKey.setShippingTicketNoOrder(3, true);
		shippingPlanSearchKey.setShippingLineNoOrder(4, true);
		shippingPlanSearchKey.setItemCodeOrder(5, true);

		// Finderのカーソルオープン
		wFinder.open();

		// 検索（結果の件数を取得）
		wLength = wFinder.search(shippingPlanSearchKey);

		// 現在の表示件数を初期化
		wCurrent = 0;

		// 荷主名称取得
		getDisplayConsignorName(param);
	}

	/**
	 * 出荷予定情報を<CODE>ShippingParameter</CODE>クラスにセットして返します。<BR>
	 *
	 * @param	shippingPlan	<CODE>ShippingPlan</CODE>クラスの配列
	 */
	private ShippingParameter[] getDispData(ShippingPlan[] shippingPlan)
	{
		ShippingParameter[] param = new ShippingParameter[shippingPlan.length];

		for (int i = 0; i < shippingPlan.length; i++)
		{
			param[i] = new ShippingParameter();
			param[i].setConsignorCode(shippingPlan[i].getConsignorCode());
			param[i].setConsignorName(wConsignorName);
			param[i].setPlanDate(shippingPlan[i].getPlanDate());
			param[i].setCustomerCode(shippingPlan[i].getCustomerCode());
			param[i].setCustomerName(shippingPlan[i].getCustomerName1());
			param[i].setShippingTicketNo(shippingPlan[i].getShippingTicketNo());
			param[i].setShippingLineNo(shippingPlan[i].getShippingLineNo());
			param[i].setItemCode(shippingPlan[i].getItemCode());
			param[i].setItemName(shippingPlan[i].getItemName1());
			param[i].setEnteringQty(shippingPlan[i].getEnteringQty());
			param[i].setBundleEnteringQty(shippingPlan[i].getBundleEnteringQty());
			param[i].setPlanCaseQty(DisplayUtil.getCaseQty(shippingPlan[i].getPlanQty(), shippingPlan[i].getEnteringQty()));
			param[i].setPlanPieceQty(DisplayUtil.getPieceQty(shippingPlan[i].getPlanQty(), shippingPlan[i].getEnteringQty()));
			param[i].setResultCaseQty(DisplayUtil.getCaseQty(shippingPlan[i].getResultQty(), shippingPlan[i].getEnteringQty()));
			param[i].setResultPieceQty(DisplayUtil.getPieceQty(shippingPlan[i].getResultQty(), shippingPlan[i].getEnteringQty()));
			param[i].setStatusName(DisplayUtil.getShippingPlanStatusValue(shippingPlan[i].getStatusFlag()));
			
		}

		return param;
	}

	/**
	 * リストに表示するための荷主名称を取得します。<BR>
	 * 印刷データの検索条件で、登録日時が最新の出荷予定情報を検索し、<BR>
	 * 先頭のデータの荷主名称を取得します。<BR>
	 * 
	 * @param	param	<CODE>ShippingParameter</CODE>クラス
 	 * @throws ReadWriteException データベース接続で発生した例外をそのまま通知します。
	 */
	private void getDisplayConsignorName(ShippingParameter param) throws ReadWriteException
	{
		// Finderインスタンス生成
		ShippingPlanFinder consignorFinder = new ShippingPlanFinder(wConn);
		ShippingPlanSearchKey shippingPlanSearchKey = new ShippingPlanSearchKey();

		// 検索条件をセット
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			shippingPlanSearchKey.setConsignorCode(param.getConsignorCode());
		}
		if (!StringUtil.isBlank(param.getFromPlanDate()))
		{
			shippingPlanSearchKey.setPlanDate(param.getFromPlanDate(), ">=");
		}
		if (!StringUtil.isBlank(param.getToPlanDate()))
		{
			shippingPlanSearchKey.setPlanDate(param.getToPlanDate(), "<=");
		}
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			shippingPlanSearchKey.setCustomerCode(param.getCustomerCode());
		}
		if (!StringUtil.isBlank(param.getShippingTicketNo()))
		{
			shippingPlanSearchKey.setShippingTicketNo(param.getShippingTicketNo());
		}
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			shippingPlanSearchKey.setItemCode(param.getItemCode());
		}
		shippingPlanSearchKey.setStatusFlag(ShippingPlan.STATUS_FLAG_DELETE, "!=");
		shippingPlanSearchKey.setRegistDateOrder(1, false);

		// 荷主名称検索
		consignorFinder.open();
		int nameCount = consignorFinder.search(shippingPlanSearchKey);
		if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
		{
			ShippingPlan[] shippingPlan = (ShippingPlan[]) consignorFinder.getEntities(0, 1);

			if (shippingPlan != null && shippingPlan.length != 0)
			{
				wConsignorName = shippingPlan[0].getConsignorName();
			}
		}
		consignorFinder.close();
	}
	
}
//end of class
