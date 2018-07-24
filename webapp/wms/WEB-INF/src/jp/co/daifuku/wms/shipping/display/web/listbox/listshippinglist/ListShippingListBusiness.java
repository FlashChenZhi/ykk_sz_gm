// $Id: ListShippingListBusiness.java,v 1.1.1.1 2006/08/17 09:34:28 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.listbox.listshippinglist;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingconsignor.ListShippingConsignorBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingcustomer.ListShippingCustomerBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingitem.ListShippingItemBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingplandate.ListShippingPlanDateBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingticket.ListShippingTicketBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.sessionret.SessionShippingListRet;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * 出荷作業リスト一覧検索リストボックスクラスです。<BR>
 * 親画面から入力した荷主コード、開始出荷予定日、終了出荷予定日、出荷先コード、伝票No.、商品コードを基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した荷主コード、開始出荷予定日、終了出荷予定日、出荷先コード、伝票No.、商品コードをキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/19</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:28 $
 * @author  $Author: mori $
 */
public class ListShippingListBusiness extends ListShippingList implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * 作業状態の受け渡しに使用するキーです
	 */
	public static final String WORKSTATUS_KEY = "WORKSTATUS_KEY";
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。 <BR>
	 * <DIR>
	 *	項目 <BR>
	 *	<DIR>
	 *		出荷予定日 <BR>
	 *		出荷先ｺｰﾄﾞ <BR>
	 *		伝票№ <BR>
	 *		商品ｺｰﾄﾞ <BR>
	 *		ｹｰｽ入数 <BR>
	 *		作業予定ｹｰｽ数 <BR>
	 *		実績ｹｰｽ数 <BR>
	 *		出荷先名称 <BR>
	 *		伝票行№ <BR>
	 *		商品名称 <BR>
	 *		ﾎﾞｰﾙ入数 <BR>
	 *		作業予定ﾋﾟｰｽ数 <BR>
	 *		実績ﾋﾟｰｽ数 <BR>
	 *<BR>
	 *		バルーンヘルプ <BR>
	 *		出荷先名称 <BR>
	 *		商品名称 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 出荷作業一覧
		lbl_ListName.setText(DisplayText.getText("TLE-W0058"));

		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY);
		// 開始出荷予定日
		String startshippingplandate = request.getParameter(ListShippingPlanDateBusiness.STARTSHIPPINGPLANDATE_KEY);
		// 終了出荷予定日
		String endshippingplandate = request.getParameter(ListShippingPlanDateBusiness.ENDSHIPPINGPLANDATE_KEY);
		// 出荷先コード
		String customercode = request.getParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY);
		// 伝票No.
		String ticketnumber = request.getParameter(ListShippingTicketBusiness.TICKETNUMBER_KEY);
		// 商品コード
		String itemcode = request.getParameter(ListShippingItemBusiness.ITEMCODE_KEY);
		// 作業状態
		String statusflag = request.getParameter(WORKSTATUS_KEY); 

		// 荷主コードの必須入力チェック、禁止文字チェック
		if (!WmsCheckker.consignorCheck(consignorcode, lst_ShippingWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 日付の範囲チェック
		if (!WmsCheckker.rangePlanDateCheck(startshippingplandate, endshippingplandate, lst_ShippingWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 出荷先コードの禁止文字チェック
		if (!WmsCheckker.charCheck(customercode, lst_ShippingWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 伝票No.の禁止文字チェック
		if (!WmsCheckker.charCheck(ticketnumber, lst_ShippingWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 商品コードの禁止文字チェック
		if (!WmsCheckker.charCheck(itemcode, lst_ShippingWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 検索条件を画面に表示
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDateStrt.setDate(WmsFormatter.toDate(startshippingplandate));
		txt_FDateEd.setDate(WmsFormatter.toDate(endshippingplandate));

		// Sessionに取り残されているオブジェクトのコネクションをクローズする
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			// コネクションのクローズ
			sRet.closeConnection();
			// セッションから削除する
			this.getSession().removeAttribute("LISTBOX");
		}

		// コネクションの取得
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		// パラメータをセット
		ShippingParameter param = new ShippingParameter();
		// 荷主コード
		param.setConsignorCode(consignorcode);
		// 開始出荷予定日
		param.setFromPlanDate(startshippingplandate);
		// 終了出荷予定日
		param.setToPlanDate(endshippingplandate);
		// 出荷先コード
		param.setCustomerCode(customercode);
		// 伝票No.
		param.setShippingTicketNo(ticketnumber);
		// 商品コード
		param.setItemCode(itemcode);
		// 作業状態
		param.setStatusFlag(statusflag);
		
		// SessionShippingListRet インスタンス生成
		SessionShippingListRet listbox = new SessionShippingListRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * ページを変更するメソッド <BR>
	 * 作業情報テーブルを検索します。 <BR>
	 * @param listbox このリストボックスのSession
	 * @param actionName 押されたボタンに対するactionName
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setList(SessionShippingListRet listbox, String actionName) throws Exception
	{
		// ロケールを取得
		Locale locale = this.getHttpRequest().getLocale();

		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		ShippingParameter[] sparam = listbox.getEntities();
		int len = 0;
		if (sparam != null)
			len = sparam.length;
		if (len > 0)
		{
			// 検索条件の荷主名称をセット
			lbl_JavaSetCnsgnrNm.setText(sparam[0].getConsignorName());

			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(listbox.getLength());
			// 1Page表示件数
			pgr_U.setPage(listbox.getCondition());
			// 開始位置
			pgr_U.setIndex(listbox.getCurrent() + 1);
			// 最大件数
			pgr_D.setMax(listbox.getLength());
			// 1Page表示件数
			pgr_D.setPage(listbox.getCondition());
			// 開始位置
			pgr_D.setIndex(listbox.getCurrent() + 1);

			// メッセージを隠します
			lbl_InMsg.setVisible(false);

			// 行を全て削除
			lst_ShippingWorkList.clearRow();

			//ToolTipで使用する
			String title_CustomerName = DisplayText.getText("LBL-W0036");
			String title_ItemName = DisplayText.getText("LBL-W0103");

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ShippingWorkList.getMaxRows();
				// 行追加
				lst_ShippingWorkList.addRow();

				// 最終行に移動
				lst_ShippingWorkList.setCurrentRow(count);
				lst_ShippingWorkList.setValue(1, WmsFormatter.toDispDate(sparam[i].getPlanDate(), locale));
				lst_ShippingWorkList.setValue(2, sparam[i].getCustomerCode());
				lst_ShippingWorkList.setValue(3, sparam[i].getShippingTicketNo());
				lst_ShippingWorkList.setValue(4, sparam[i].getItemCode());
				lst_ShippingWorkList.setValue(5, WmsFormatter.getNumFormat(sparam[i].getEnteringQty()));
				lst_ShippingWorkList.setValue(6, WmsFormatter.getNumFormat(sparam[i].getPlanCaseQty()));
				lst_ShippingWorkList.setValue(7, WmsFormatter.getNumFormat(sparam[i].getResultCaseQty()));
				lst_ShippingWorkList.setValue(8, sparam[i].getCustomerName());
				lst_ShippingWorkList.setValue(9, Integer.toString(sparam[i].getShippingLineNo()));
				lst_ShippingWorkList.setValue(10, sparam[i].getItemName());
				lst_ShippingWorkList.setValue(11, WmsFormatter.getNumFormat(sparam[i].getBundleEnteringQty()));
				lst_ShippingWorkList.setValue(12, WmsFormatter.getNumFormat(sparam[i].getPlanPieceQty()));
				lst_ShippingWorkList.setValue(13, WmsFormatter.getNumFormat(sparam[i].getResultPieceQty()));


				// ToolTip用のデータを編集
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_CustomerName, sparam[i].getCustomerName());
				toolTip.add(title_ItemName, sparam[i].getItemName());

				// TOOL TIPをセットする	
				lst_ShippingWorkList.setToolTip(count, toolTip.getText());
			}
		}
		else
		{
			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(0);
			// 1Page表示件数
			pgr_U.setPage(0);
			// 開始位置
			pgr_U.setIndex(0);
			// 最大件数
			pgr_D.setMax(0);
			// 1Page表示件数
			pgr_D.setPage(0);
			// 開始位置
			pgr_D.setIndex(0);

			// 検索結果の件数についてのチェックを行なう
			String errorMsg = listbox.checkLength();
			// ヘッダーを隠します
			lst_ShippingWorkList.setVisible(false);
			// エラーメッセージ表示
			lbl_InMsg.setResourceKey(errorMsg);
		}
	}

	// Event handler methods -----------------------------------------
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrCd_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetCnsgnrNm_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShippingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateStrt_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateEd_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 「閉じる」ボタンを押したときの処理を行います。 <BR>
	 *  <BR>
	 * リストボックスを閉じ、親画面へ遷移します。 <BR>
	 *  <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	/** 
	 * 「＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 次のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	/** 
	 * 「＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * １ページ前を表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	/** 
	 * 「＞＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 最終ページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	/** 
	 * 「＜＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 一番最初のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShippingWorkList_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShippingWorkList_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShippingWorkList_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShippingWorkList_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShippingWorkList_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShippingWorkList_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ShippingWorkList_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 「＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 次のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		// Sessionにlistboxを保持
		SessionShippingListRet listbox = (SessionShippingListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
	}

	/** 
	 * 「＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * １ページ前を表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		// Sessionにlistboxを保持
		SessionShippingListRet listbox = (SessionShippingListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
	}

	/** 
	 * 「＞＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 最終ページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		// Sessionにlistboxを保持
		SessionShippingListRet listbox = (SessionShippingListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
	}

	/** 
	 * 「＜＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 一番最初のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		// Sessionにlistboxを保持
		SessionShippingListRet listbox = (SessionShippingListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 「閉じる」ボタンを押したときの処理を行います。 <BR>
	 *  <BR>
	 * リストボックスを閉じ、親画面へ遷移します。 <BR>
	 *  <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
		// Sessionにlistboxを保持
		SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		// Sessionに値がある場合
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				// ステートメントを閉じる
				finder.close();
			}
			// コネクションをクローズ
			sessionret.closeConnection();
		}
		// セッションから削除する
		this.getSession().removeAttribute("LISTBOX");
		// 親画面に戻る
		parentRedirect(null);
	}
}
//end of class
