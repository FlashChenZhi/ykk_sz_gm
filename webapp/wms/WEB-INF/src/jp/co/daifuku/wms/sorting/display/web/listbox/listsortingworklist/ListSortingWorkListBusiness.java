// $Id: ListSortingWorkListBusiness.java,v 1.1.1.1 2006/08/17 09:34:32 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.listbox.listsortingworklist;
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
import jp.co.daifuku.wms.sorting.display.web.listbox.sessionret.SessionSortingWorkListRet;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * 仕分作業リスト一覧検索リストボックスクラスです。<BR>
 * 親画面から入力した荷主コード、仕分予定日、商品コード、ｹｰｽ/ﾋﾟｰｽ区分、クロス/DC区分を基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した荷主コード、仕分予定日、商品コード、ｹｰｽ/ﾋﾟｰｽ区分、クロス/DC区分をキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class ListSortingWorkListBusiness extends ListSortingWorkList implements WMSConstants
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
	public static final String STATUSFLAG_KEY = "STATUSFLAG_KEY";

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
	 *		仕分場所 <BR>
	 *		出荷先ｺｰﾄﾞ <BR>
	 *		仕分総数 <BR>
	 *		ｹｰｽ入数 <BR>
	 *		仕分ｹｰｽ数 <BR>
	 *		商品名称 <BR>
	 *		出荷先名称 <BR>
	 *		ﾎﾞｰﾙ入数 <BR>
	 *		仕分ﾋﾟｰｽ数 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 出荷作業一覧
		lbl_ListName.setText(DisplayText.getText("TLE-W0088"));
		
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
		String statusFlag = request.getParameter(STATUSFLAG_KEY);
		

		// 荷主コードの必須入力チェック、禁止文字チェック
		if (!WmsCheckker.consignorCheck(consignorcode, lst_PickingWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 仕分予定日の必須入力チェック、禁止文字チェック
		if (!WmsCheckker.sortingplandateCheck(sortingplandate, lst_PickingWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 商品コードの禁止文字チェック
		if (!WmsCheckker.charCheck(itemcode, lst_PickingWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 検索条件を画面に表示
		lbl_JavaSetCnsgnrCd.setText(consignorcode);
		txt_FDateStrt.setDate(WmsFormatter.toDate(sortingplandate));
		
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
		param.setStatusFlag(statusFlag);

		// SessionShippingListRet インスタンス生成
		SessionSortingWorkListRet listbox = new SessionSortingWorkListRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * @param listbox
	 * @param string
	 */
	private void setList(SessionSortingWorkListRet listbox, String actionName)
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
			lst_PickingWorkList.clearRow();
			//ToolTipで使用する
			String title_totalplanqty = DisplayText.getText("LBL-W0413");
			String title_caseqty = DisplayText.getText("LBL-W0130");
			String title_pieceqty = DisplayText.getText("LBL-W0132");
			String title_caseentering = DisplayText.getText("LBL-W0007");
			String title_bundleentering = DisplayText.getText("LBL-W0005");
			
			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_PickingWorkList.getMaxRows();
				// 行追加
				lst_PickingWorkList.addRow();

				// 最終行に移動
				lst_PickingWorkList.setCurrentRow(count);
				// 商品コード
				lst_PickingWorkList.setValue(1, sparam[i].getItemCode());
				// ケースピース名称
				lst_PickingWorkList.setValue(2, sparam[i].getCasePieceName());
				// TCDC名称
				lst_PickingWorkList.setValue(3, sparam[i].getTcdcName());
				// 仕分場所
				lst_PickingWorkList.setValue(4, sparam[i].getSortingLocation());
				// 出荷先コード
				lst_PickingWorkList.setValue(5, sparam[i].getCustomerCode());
				// 仕分総数
				lst_PickingWorkList.setValue(6, WmsFormatter.getNumFormat(sparam[i].getTotalPlanQty()));
				// ケース入数
				lst_PickingWorkList.setValue(7, WmsFormatter.getNumFormat(sparam[i].getEnteringQty()));
				// 仕分ケース数
				lst_PickingWorkList.setValue(8, WmsFormatter.getNumFormat(sparam[i].getPlanCaseQty()));
				// 商品名称
				lst_PickingWorkList.setValue(9, sparam[i].getItemName());
				// 出荷先名称
				lst_PickingWorkList.setValue(10, sparam[i].getCustomerName());
				// ボール入数
				lst_PickingWorkList.setValue(11, WmsFormatter.getNumFormat(sparam[i].getBundleEnteringQty()));
				// 仕分ピース数
				lst_PickingWorkList.setValue(12, WmsFormatter.getNumFormat(sparam[i].getPlanPieceQty()));
				
				// ToolTip用のデータを編集
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_totalplanqty, sparam[i].getTotalPlanQty());
				toolTip.add(title_caseentering, sparam[i].getBundleEnteringQty());
				toolTip.add(title_bundleentering, sparam[i].getBundleEnteringQty());
				toolTip.add(title_caseqty, sparam[i].getPlanCaseQty());
				toolTip.add(title_pieceqty, sparam[i].getPlanPieceQty());

				// TOOL TIPをセットする	
				lst_PickingWorkList.setToolTip(count, toolTip.getText());
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
			lst_PickingWorkList.setVisible(false);
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
	public void lst_PickingWorkList_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingWorkList_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingWorkList_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingWorkList_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingWorkList_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingWorkList_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingWorkList_Click(ActionEvent e) throws Exception
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
		SessionSortingWorkListRet listbox = (SessionSortingWorkListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionSortingWorkListRet listbox = (SessionSortingWorkListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionSortingWorkListRet listbox = (SessionSortingWorkListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionSortingWorkListRet listbox = (SessionSortingWorkListRet) this.getSession().getAttribute("LISTBOX");
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
