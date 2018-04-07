// $Id: ListInstockReceivePlanListBusiness.java,v 1.1.1.1 2006/08/17 09:34:12 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplanlist;
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
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret.SessionInstockReceivePlanListRet;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * クロス／ＤＣ入荷予定一覧リストボックスクラスです。<BR>
 * 親画面から入力した荷主コード、開始入荷予定日、終了入荷予定日、仕入先コード、<BR>
 * 開始伝票No.、終了伝票No.、商品コード、クロス／ＤＣ、作業状態を基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 親画面から入力した荷主コード、開始入荷予定日、終了入荷予定日、仕入先コード、<BR>
 * 開始伝票No.、終了伝票No.、商品コード、クロス／ＤＣ、作業状態をキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/05</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:12 $
 * @author  $Author: mori $
 */
public class ListInstockReceivePlanListBusiness
	extends ListInstockReceivePlanList
	implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * クロス／ＤＣフラグの受け渡しに使用するキーです
	 */
	public static final String CROSSDCFLAG_KEY = "CROSSDCFLAG_KEY";

	/** 
	 * 作業状態の受け渡しに使用するキーです
	 */
	public static final String WORKSTATUSPLANLIST_KEY = "WORKSTATUSPLANLIST_KEY";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。 <BR>
	 * <DIR>
	 *	項目 <BR>
	 *	<DIR>
	 *		入荷予定日 <BR>
	 *		仕入先ｺｰﾄﾞ <BR>
	 *		伝票№ <BR>
	 *		商品ｺｰﾄﾞ <BR>
	 *		ｹｰｽ入数 <BR>
	 *		ﾎｽﾄ予定ｹｰｽ数 <BR>
	 *		実績ｹｰｽ数 <BR>
	 *		ｸﾛｽ/DC <BR>
	 *      状態<BR>
	 *		仕入先名称 <BR>
	 *		行№ <BR>
	 *		商品名称 <BR>
	 *		ﾎﾞｰﾙ入数 <BR>
	 *		ﾎｽﾄ予定ﾋﾟｰｽ数 <BR>
	 *		実績ﾋﾟｰｽ数 <BR>
	 *<BR>
	 *		バルーンヘルプ <BR>
	 *		仕入先名称 <BR>
	 *		商品名称 <BR>
	 *		実績ｹｰｽ数 <BR>
	 *		実績ﾋﾟｰｽ数 <BR>
	 *		ｸﾛｽ/DC <BR>
	 *      状態<BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// クロス／ＤＣ入荷予定一覧
		lbl_ListName.setText(DisplayText.getText("TLE-W0093"));

		// パラメータを取得
		// 荷主コード
		String consignorcode =
			request.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 開始入荷予定日
		String startinstockplandate =
			request.getParameter(ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY);
		// 終了入荷予定日
		String endinstockplandate =
			request.getParameter(ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY);
		// 仕入先コード
		String suppliercode =
			request.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// 開始伝票No.
		String startticketnumber =
			request.getParameter(ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY);
		// 終了伝票No.
		String endticketnumber =
			request.getParameter(ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY);
		// 商品コード
		String itemcode = request.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);
		// クロス／ＤＣ
		String crossdcflag = request.getParameter(CROSSDCFLAG_KEY);
		// 作業状態
		String search = request.getParameter(WORKSTATUSPLANLIST_KEY);

		// 荷主コードの必須入力チェック、禁止文字チェック
		if (!WmsCheckker
			.consignorCheck(consignorcode, lst_InstkCrsDcPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 入荷予定日の範囲チェック
		if (!WmsCheckker
			.rangeInstockPlanDateCheck(
				startinstockplandate,
				endinstockplandate,
				lst_InstkCrsDcPlanList,
				pgr_U,
				pgr_D,
				lbl_InMsg))
		{
			return;
		}

		// 仕入先コードの禁止文字チェック
		if (!WmsCheckker.charCheck(suppliercode, lst_InstkCrsDcPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 開始伝票No.の禁止文字チェック
		if (!WmsCheckker
			.charCheck(startticketnumber, lst_InstkCrsDcPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 終了伝票No.の禁止文字チェック
		if (!WmsCheckker
			.charCheck(endticketnumber, lst_InstkCrsDcPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 伝票No.の範囲チェック
		if (!WmsCheckker
			.rangeTicketNoCheck(
				startticketnumber,
				endticketnumber,
				lst_InstkCrsDcPlanList,
				pgr_U,
				pgr_D,
				lbl_InMsg))
		{
			return;
		}

		// 商品コードの禁止文字チェック
		if (!WmsCheckker.charCheck(itemcode, lst_InstkCrsDcPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 検索条件を画面に表示
		lbl_JavaSetConsignorCode.setText(consignorcode);

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
		InstockReceiveParameter param = new InstockReceiveParameter();
		// 荷主コード
		param.setConsignorCode(consignorcode);
		// 開始入荷予定日
		param.setFromPlanDate(startinstockplandate);
		// 終了入荷予定日
		param.setToPlanDate(endinstockplandate);
		// 仕入先コード
		param.setSupplierCode(suppliercode);
		// 開始伝票No.
		param.setFromTicketNo(startticketnumber);
		// 終了伝票No.
		param.setToTicketNo(endticketnumber);
		// 商品コード
		param.setItemCode(itemcode);
		// クロス／ＤＣ
		param.setTcdcFlag(crossdcflag);
		// 作業状態
		param.setStatusFlag(search);

		// SessionInstockReceivePlanListRet インスタンス生成
		SessionInstockReceivePlanListRet listbox =
			new SessionInstockReceivePlanListRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * ページを変更するメソッド <BR>
	 * @param listbox
	 * @param actionName
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setList(SessionInstockReceivePlanListRet listbox, String actionName)
		throws Exception
	{
		// ロケールを取得
		Locale locale = this.getHttpRequest().getLocale();

		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		InstockReceiveParameter[] irparam = (InstockReceiveParameter[]) listbox.getEntities();
		int len = 0;
		if (irparam != null)
			len = irparam.length;
		if (len > 0)
		{
			// 検索条件の荷主名称をセット
			lbl_JavaSetConsignorName.setText(irparam[0].getConsignorName());

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
			lst_InstkCrsDcPlanList.clearRow();

			// ToolTipで使用する
			// 仕入先名称
			String title_SupplierName = DisplayText.getText("LBL-W0253");
			// 商品名称
			String title_ItemName = DisplayText.getText("LBL-W0103");
			// 実績ケース数
			String title_ResultCase = DisplayText.getText("LBL-W0418");
			// 実績ピース数
			String title_ResultPiece = DisplayText.getText("LBL-W0417");
			// クロス/DC
			String title_CrossDCFlag = DisplayText.getText("LBL-W0029");
			// 状態
			String title_StatusName = DisplayText.getText("LBL-W0229");
			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_InstkCrsDcPlanList.getMaxRows();
				// 行追加
				lst_InstkCrsDcPlanList.addRow();

				// 最終行に移動
				lst_InstkCrsDcPlanList.setCurrentRow(count);
				lst_InstkCrsDcPlanList.setValue(
					1,
					WmsFormatter.toDispDate(irparam[i].getPlanDate(), locale));
				lst_InstkCrsDcPlanList.setValue(2, irparam[i].getSupplierCode());
				lst_InstkCrsDcPlanList.setValue(3, irparam[i].getInstockTicketNo());
				lst_InstkCrsDcPlanList.setValue(4, irparam[i].getItemCode());
				lst_InstkCrsDcPlanList.setValue(
					5,
					WmsFormatter.getNumFormat(irparam[i].getEnteringQty()));
				lst_InstkCrsDcPlanList.setValue(
					6,
					WmsFormatter.getNumFormat(irparam[i].getPlanCaseQty()));
				lst_InstkCrsDcPlanList.setValue(
					7,
					WmsFormatter.getNumFormat(irparam[i].getResultCaseQty()));
				lst_InstkCrsDcPlanList.setValue(8, irparam[i].getTcdcName());
				lst_InstkCrsDcPlanList.setValue(9, irparam[i].getStatusName());
				lst_InstkCrsDcPlanList.setValue(10, irparam[i].getSupplierName());
				lst_InstkCrsDcPlanList.setValue(
					11,
					Integer.toString(irparam[i].getInstockLineNo()));
				lst_InstkCrsDcPlanList.setValue(12, irparam[i].getItemName());
				lst_InstkCrsDcPlanList.setValue(
					13,
					WmsFormatter.getNumFormat(irparam[i].getBundleEnteringQty()));
				lst_InstkCrsDcPlanList.setValue(
					14,
					WmsFormatter.getNumFormat(irparam[i].getPlanPieceQty()));
				lst_InstkCrsDcPlanList.setValue(
					15,
					WmsFormatter.getNumFormat(irparam[i].getResultPieceQty()));

				// ToolTip用のデータを編集
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_SupplierName, irparam[i].getSupplierName());
				toolTip.add(title_ItemName, irparam[i].getItemName());
				toolTip.add(title_ResultCase, WmsFormatter.getNumFormat(irparam[i].getResultCaseQty()));
				toolTip.add(title_ResultPiece, WmsFormatter.getNumFormat(irparam[i].getResultPieceQty()));
				toolTip.add(title_CrossDCFlag, irparam[i].getTcdcName());
				toolTip.add(title_StatusName, irparam[i].getStatusName());

				// TOOL TIPをセットする	
				lst_InstkCrsDcPlanList.setToolTip(count, toolTip.getText());
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
			lst_InstkCrsDcPlanList.setVisible(false);
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
	public void lbl_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetConsignorName_Server(ActionEvent e) throws Exception
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
	public void lst_InstkCrsDcPlanList_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcPlanList_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcPlanList_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcPlanList_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcPlanList_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcPlanList_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcPlanList_Click(ActionEvent e) throws Exception
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
		SessionInstockReceivePlanListRet listbox =
			(SessionInstockReceivePlanListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceivePlanListRet listbox =
			(SessionInstockReceivePlanListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceivePlanListRet listbox =
			(SessionInstockReceivePlanListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceivePlanListRet listbox =
			(SessionInstockReceivePlanListRet) this.getSession().getAttribute("LISTBOX");
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
