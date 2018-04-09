// $Id: ListSortingPlanListBusiness.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.listbox.listsortingplanlist;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingdate.ListSortingDateBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem.ListSortingItemBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.sessionret.SessionSortingPlanListRet;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * 仕分予定リスト一覧検索リストボックスクラスです。<BR>
 * 親画面から入力した荷主コード、仕分予定日、商品コード、ｹｰｽ/ﾋﾟｰｽ区分、クロス/DC区分、作業状態を基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した荷主コード、仕分予定日、商品コード、ｹｰｽ/ﾋﾟｰｽ区分、クロス/DC区分、作業状態をキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class ListSortingPlanListBusiness extends ListSortingPlanList implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * ケース/ピース区分の受け渡しに使用するキーです
	 */
	public static final String CASEPIECEFLAG_KEY = "CASEPIECEFLAG_KEY";
	
	/** 
	 * クロス/DC区分の受け渡しに使用するキーです
	 */
	public static final String CROSSDC_KEY = "CROSSDC_KEY";
	
	/** 
	 * 作業状態の受け渡しに使用するキーです
	 */
	public static final String STATUS_FLAG_KEY = "STATUS_FLAG_KEY";
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。 <BR>
	 * <DIR>
	 *	項目 <BR>
	 *	<DIR>
	 *		商品ｺｰﾄﾞ <BR>
	 *		ｹｰｽ/ﾋﾟｰｽ区分 <BR>
	 *		ｸﾛｽ/DC区分 <BR>
	 *		出荷先ｺｰﾄﾞ <BR>
	 *		ｹｰｽ仕分場所 <BR>
	 *		ｹｰｽ入数 <BR>
	 *		ﾎｽﾄ予定ｹｰｽ数 <BR>
	 *		実績ｹｰｽ数 <BR>
	 *		状態 <BR>
	 *		ｹｰｽITF <BR>
	 *		仕入先ｺｰﾄﾞ <BR>
	 *		商品名称 <BR>
	 *		出荷先名称 <BR>
	 *		ｹｰｽ仕分場所 <BR>
	 *		ﾎﾞｰﾙ入数 <BR>
	 *		ﾎｽﾄ予定ﾋﾟｰｽ数 <BR>
	 *		実績ﾋﾟｰｽ数 <BR>
	 *		ﾎﾞｰﾙITF <BR>
	 *		仕入先名称 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 仕分予定一覧
		lbl_ListName.setText(DisplayText.getText("TLE-W0083"));
		
		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);
		// 仕分予定日
		String sortingplandate = request.getParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY);
		// 商品コード
		String itemcode = request.getParameter(ListSortingItemBusiness.ITEMCODE_KEY);
		// ｹｰｽ/ﾋﾟｰｽ区分
		String casepieceflag = request.getParameter(CASEPIECEFLAG_KEY);
		// クロス/DC区分
		String crossdcflag = request.getParameter(CROSSDC_KEY);
		// 作業状態
		String statusflag = request.getParameter(STATUS_FLAG_KEY);
		
		// 荷主コードの必須入力チェック、禁止文字チェック
		if (!WmsCheckker.consignorCheck(consignorcode, lst_PickingPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 仕分予定日の必須入力チェック、禁止文字チェック
		if (!WmsCheckker.sortingplandateCheck(sortingplandate, lst_PickingPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 商品コードの禁止文字チェック
		if (!WmsCheckker.charCheck(itemcode, lst_PickingPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 検索条件を画面に表示
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_F_Date.setDate(WmsFormatter.toDate(sortingplandate));
		
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
		SortingParameter param = new SortingParameter();
		// 荷主コード
		param.setConsignorCode(consignorcode);
		// 仕分予定日
		param.setPlanDate(sortingplandate);
		// 商品コード
		param.setItemCode(itemcode);
		// ｹｰｽ/ﾋﾟｰｽ区分
		param.setCasePieceFlag(casepieceflag);
		// クロス/DC区分
		param.setTcdcFlag(crossdcflag);
		// 作業状態
		param.setStatusFlag(statusflag);
		// SessionShippingListRet インスタンス生成
		SessionSortingPlanListRet listbox = new SessionSortingPlanListRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}


	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * ページを変更するメソッド <BR>
	 * 予定テーブルを検索します。 <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 */
	private void setList(SessionSortingPlanListRet listbox, String actionName)
	{
		
		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		SortingParameter[] sparam = (SortingParameter[])listbox.getEntities();
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
		
			//　荷主名称セット
			lbl_JavaSetCnsgnrNm.setText(sparam[0].getConsignorName());

			// 行を全て削除
			lst_PickingPlanList.clearRow();
			
			//ToolTipで使用する
			String title_ItemName = DisplayText.getText("LBL-W0103");
			String title_CustomerName = DisplayText.getText("LBL-W0036");
			String title_SupplierName = DisplayText.getText("LBL-W0253");
			String title_plancase = DisplayText.getText("LBL-W0331");
			String title_planpiece = DisplayText.getText("LBL-W0332");
			String title_resultcase = DisplayText.getText("LBL-W0167");
			String title_resultpiece = DisplayText.getText("LBL-W0169");
			String title_joutai = DisplayText.getText("LBL-W0229");
			String title_SupplierCode = DisplayText.getText("LBL-W0322");

			
			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_PickingPlanList.getMaxRows();
				// 行追加
				lst_PickingPlanList.addRow();

				// 最終行に移動
				lst_PickingPlanList.setCurrentRow(count);
				lst_PickingPlanList.setValue(0, sparam[i].getCasePieceFlag());
				lst_PickingPlanList.setValue(1, sparam[i].getItemCode());
				lst_PickingPlanList.setValue(2, sparam[i].getCasePieceName());
				lst_PickingPlanList.setValue(3, sparam[i].getTcdcName());
				lst_PickingPlanList.setValue(4, sparam[i].getCustomerCode());
				lst_PickingPlanList.setValue(5, sparam[i].getCaseSortingLocation());
				lst_PickingPlanList.setValue(6, WmsFormatter.getNumFormat(sparam[i].getEnteringQty()));
				lst_PickingPlanList.setValue(7, WmsFormatter.getNumFormat(sparam[i].getPlanCaseQty()));
				lst_PickingPlanList.setValue(8, WmsFormatter.getNumFormat(sparam[i].getResultCaseQty()));
				lst_PickingPlanList.setValue(9, sparam[i].getStatusName());
				lst_PickingPlanList.setValue(10, sparam[i].getSupplierCode());
				lst_PickingPlanList.setValue(11, sparam[i].getItemName());
				lst_PickingPlanList.setValue(12, sparam[i].getCustomerName());
				lst_PickingPlanList.setValue(13, sparam[i].getPieceSortingLocation());
				lst_PickingPlanList.setValue(14, WmsFormatter.getNumFormat(sparam[i].getBundleEnteringQty()));
				lst_PickingPlanList.setValue(15, WmsFormatter.getNumFormat(sparam[i].getPlanPieceQty()));
				lst_PickingPlanList.setValue(16, WmsFormatter.getNumFormat(sparam[i].getResultPieceQty()));
				lst_PickingPlanList.setValue(17, sparam[i].getSupplierName());
				
				// ToolTip用のデータを編集
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_ItemName, sparam[i].getItemName());
				toolTip.add(title_CustomerName, sparam[i].getCustomerName());
				toolTip.add(title_plancase, sparam[i].getPlanCaseQty());
				toolTip.add(title_planpiece, sparam[i].getPlanPieceQty());
				toolTip.add(title_resultcase, sparam[i].getResultCaseQty());
				toolTip.add(title_resultpiece, sparam[i].getResultPieceQty());
				toolTip.add(title_joutai, sparam[i].getStatusName());
				toolTip.add(title_SupplierCode, sparam[i].getSupplierCode());
				toolTip.add(title_SupplierName, sparam[i].getSupplierName());

				// TOOL TIPをセットする	
				lst_PickingPlanList.setToolTip(count, toolTip.getText());
				
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
			lst_PickingPlanList.setVisible(false);
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
	public void lbl_PickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void W_F_Date_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void W_F_Date_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void W_F_Date_TabKey(ActionEvent e) throws Exception
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
	public void lst_PickingPlanList_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanList_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanList_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanList_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanList_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanList_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanList_Click(ActionEvent e) throws Exception
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
		SessionSortingPlanListRet listbox = (SessionSortingPlanListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionSortingPlanListRet listbox = (SessionSortingPlanListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionSortingPlanListRet listbox = (SessionSortingPlanListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionSortingPlanListRet listbox = (SessionSortingPlanListRet) this.getSession().getAttribute("LISTBOX");
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
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_F_Date_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_F_Date_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_F_Date_TabKey(ActionEvent e) throws Exception
	{
	}


}
//end of class
