// $Id: ListSortingPlanRegistBusiness.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.listbox.listsortingplanregist;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingdate.ListSortingDateBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem.ListSortingItemBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.sessionret.SessionSortingWorkInfoRegistRet;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer :A.Nagasawa<BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * 仕分予定一覧(仕分予定登録、修正削除)リストボックスクラスです。 <BR>
 * 親画面から入力した荷主コード、仕分予定日、商品コードを基に検索します。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期画面（ <CODE>page_Load(ActionEvent e)</CODE> メソッド） <BR>
 * <BR>
 * <DIR>親画面から入力した荷主コード、仕分予定日、商品コードをキーにして検索し、画面に表示します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <BR>
 * 荷主コード* <BR>
 * 仕分予定日 <BR>
 * 商品コード <BR>
 * <BR>
 * [出力用のデータ] <BR>
 * <BR>
 * 荷主コード <BR>
 * 荷主名称 <BR>
 * 仕分予定日 <BR>
 * 商品コード <BR>
 * 商品名称 <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>A.Naagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class ListSortingPlanRegistBusiness extends ListSortingPlanRegist implements WMSConstants
{
	// Class fields --------------------------------------------------
	/**
	 * 検索状態フラグ
	 */
	public static final String SEARCHSTATUS_KEY = "SEARCHSTATUS_KEY";
	
	/**
	 * ケース入数
	 */
	public static final String ENTERING_KEY = "ENTERING_KEY";

	/**
	 * ボール入数
	 */
	public static final String BUNDLEENTERING_KEY = "BUNDLEENTERING_KEY";

	/**
	 * ケースITF
	 */
	public static final String ITF_KEY = "ITF_KEY";

	/**
	 * ボールITF
	 */
	public static final String BUNDLEITF_KEY = "BUNDLEITF_KEY";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 仕分予定一覧
		lbl_ListName.setText(DisplayText.getText("TLE-W0083"));

		// パラメータ取得
		// 荷主コード
		String consignorcode = request.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);
		// 仕分予定日
		String sortingplandate = request.getParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY);
		// 商品コード
		String itemcode = request.getParameter(ListSortingItemBusiness.ITEMCODE_KEY);
		// 状態フラグ
		String[] statusFlag = request.getParameterValues(SEARCHSTATUS_KEY);

		// 荷主コードの必須入力チェック、禁止文字チェック
		if (!WmsCheckker.consignorCheck(consignorcode, lst_ListSortingPlanRegist, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 商品ードの禁止文字チェック
		if (!WmsCheckker.charCheck(itemcode, lst_ListSortingPlanRegist, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 検索条件にセット
		lbl_JavaSetCnsgnrCd.setText(consignorcode);

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
		// 状態フラグ
		param.setSearchStatus(statusFlag);

		// SessionSortingMaintenanceRet インスタンス生成
		SessionSortingWorkInfoRegistRet listbox = new SessionSortingWorkInfoRegistRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * ページを変更するメソッド <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setList(SessionSortingWorkInfoRegistRet listbox, String actionName) throws Exception
	{
		// ロケールを取得
		Locale locale = this.getHttpRequest().getLocale();

		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		SortingParameter[] splan = (SortingParameter[]) listbox.getEntities();
		int len = 0;
		if (splan != null)
			len = splan.length;
		if (len > 0)
		{
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
			// 検索条件荷主名称セット
			lbl_JavaSetCnsgnrNm.setText(splan[0].getConsignorName());

			// 行を全て削除
			lst_ListSortingPlanRegist.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ListSortingPlanRegist.getMaxRows();
				// 行追加
				lst_ListSortingPlanRegist.addRow();

				// 最終行に移動
				lst_ListSortingPlanRegist.setCurrentRow(count);
				// 選択ボタン
				lst_ListSortingPlanRegist.setValue(1, Integer.toString(count + listbox.getCurrent()));
				// 仕分予定日
				lst_ListSortingPlanRegist.setValue(2, WmsFormatter.toDispDate(splan[i].getPlanDate(), locale));
				// 商品コード
				lst_ListSortingPlanRegist.setValue(3, splan[i].getItemCode());
				// ケース入数
				lst_ListSortingPlanRegist.setValue(4, WmsFormatter.getNumFormat(splan[i].getEnteringQty()));
				// ケースITF
				lst_ListSortingPlanRegist.setValue(5, splan[i].getITF());
				// 商品名称
				lst_ListSortingPlanRegist.setValue(6, splan[i].getItemName());
				// ボール入数
				lst_ListSortingPlanRegist.setValue(7, WmsFormatter.getNumFormat(splan[i].getBundleEnteringQty()));
				// ボールITF
				lst_ListSortingPlanRegist.setValue(8, splan[i].getBundleITF());

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
			lst_ListSortingPlanRegist.setVisible(false);
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
		SessionSortingWorkInfoRegistRet listbox = (SessionSortingWorkInfoRegistRet) this.getSession().getAttribute("LISTBOX");
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
		SessionSortingWorkInfoRegistRet listbox = (SessionSortingWorkInfoRegistRet) this.getSession().getAttribute("LISTBOX");
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
		SessionSortingWorkInfoRegistRet listbox = (SessionSortingWorkInfoRegistRet) this.getSession().getAttribute("LISTBOX");
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
		SessionSortingWorkInfoRegistRet listbox = (SessionSortingWorkInfoRegistRet) this.getSession().getAttribute("LISTBOX");
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
	public void lst_ListSortingPlanRegist_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingPlanRegist_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingPlanRegist_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingPlanRegist_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingPlanRegist_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ListSortingPlanRegist_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面に選択した情報を渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * [親画面に返すパラメータ]<BR>
	 * <DIV>
	 * 荷主コード <BR>
	 * 荷主名称 <BR>
	 * 仕分予定日 <BR>
	 * 商品コード <BR>
	 * 商品名称 <BR>
	 * </DIV>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_ListSortingPlanRegist_Click(ActionEvent e) throws Exception
	{
		// 現在の行をセット
		lst_ListSortingPlanRegist.setCurrentRow(lst_ListSortingPlanRegist.getActiveRow());
		lst_ListSortingPlanRegist.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		// 荷主名称
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORNAME_KEY, lbl_JavaSetCnsgnrNm.getText());
		// 仕分予定日
		param.setParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY, lst_ListSortingPlanRegist.getValue(2));
		// 商品コード
		param.setParameter(ListSortingItemBusiness.ITEMCODE_KEY, lst_ListSortingPlanRegist.getValue(3));
		// 商品名称
		param.setParameter(ListSortingItemBusiness.ITEMNAME_KEY, lst_ListSortingPlanRegist.getValue(6));
		// ケース入数
		param.setParameter(ENTERING_KEY, lst_ListSortingPlanRegist.getValue(4));
		// ボール入数
		param.setParameter(BUNDLEENTERING_KEY, lst_ListSortingPlanRegist.getValue(7));
		// ケースITF
		param.setParameter(ITF_KEY, lst_ListSortingPlanRegist.getValue(5));
		// ボールITF
		param.setParameter(BUNDLEITF_KEY, lst_ListSortingPlanRegist.getValue(8));

		// 親画面に遷移する
		parentRedirect(param);
	}

}
//end of class
