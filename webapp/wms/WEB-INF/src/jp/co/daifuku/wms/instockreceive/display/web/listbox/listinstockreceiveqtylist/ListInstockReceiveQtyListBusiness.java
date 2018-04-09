// $Id: ListInstockReceiveQtyListBusiness.java,v 1.1.1.1 2006/08/17 09:34:12 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveqtylist;
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
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivedate.ListInstockReceiveDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret.SessionInstockReceiveQtyListRet;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : K.Mukai <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * クロス／ＤＣ入荷実績一覧リストボックスクラスです。<BR>
 * 親画面から入力した荷主コード、開始入荷受付日、終了入荷受付日、仕入先コード、<BR>
 * 開始伝票No.、終了伝票No.、商品コード、クロス／ＤＣ、を基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 親画面から入力した荷主コード、開始入荷受付日、終了入荷受付日、仕入先コード、<BR>
 * 開始伝票No.、終了伝票No.、商品コード、クロス／ＤＣ、をキーにして検索し、画面に表示します。<BR>
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
public class ListInstockReceiveQtyListBusiness extends ListInstockReceiveQtyList implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * クロス／ＤＣフラグの受け渡しに使用するキーです
	 */
	public static final String CROSSDCFLAG_KEY = "CROSSDCFLAG_KEY";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。 <BR>
	 * <DIR>
	 *	項目 <BR>
	 *	<DIR>
	 *		入荷受付日 <BR>
	 *		仕入先ｺｰﾄﾞ <BR>
	 *		伝票№ <BR>
	 *		商品ｺｰﾄﾞ <BR>
	 *		ｹｰｽ入数 <BR>
	 *		作業予定ｹｰｽ数 <BR>
	 *		実績ｹｰｽ数 <BR>
	 *		欠品ｹｰｽ数 <BR>
	 *		賞味期限 <BR>
	 *		ｸﾛｽ/DC <BR>
	 *		入荷予定日 <BR>
	 *		仕入先名称 <BR>
	 *		行№ <BR>
	 *		商品名称 <BR>
	 *		ﾎﾞｰﾙ入数 <BR>
	 *		作業予定ﾋﾟｰｽ数 <BR>
	 *		実績ﾋﾟｰｽ数 <BR>
	 *		欠品ﾋﾟｰｽ数 <BR>
	 *<BR>
	 *		バルーンヘルプ <BR>
	 *		仕入先名称 <BR>
	 *		商品名称 <BR>
	 *		実績ｹｰｽ数 <BR>
	 *		実績ﾋﾟｰｽ数 <BR>
	 *		欠品ｹｰｽ数 <BR>
	 *		欠品ﾋﾟｰｽ数 <BR>
	 *		賞味期限 <BR>
	 *		ｸﾛｽ/DC <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// クロス／ＤＣ入荷予定一覧
		lbl_ListName.setText(DisplayText.getText("TLE-W0095"));

		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 開始入荷受付日
		String startinstockdate = request.getParameter(ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY);
		// 終了入荷受付日
		String endinstockdate = request.getParameter(ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY);
		// 仕入先コード
		String suppliercode = request.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// 開始伝票No.
		String startticketnumber = request.getParameter(ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY);
		// 終了伝票No.
		String endticketnumber = request.getParameter(ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY);
		// 商品コード
		String itemcode = request.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);
		// クロス／ＤＣ
		String crossdcflag = request.getParameter(CROSSDCFLAG_KEY);

		// 荷主コードの必須入力チェック、禁止文字チェック
		if (!WmsCheckker.consignorCheck(consignorcode, lst_InstkCrsDcResultList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 入荷受付日の範囲チェック
		if (!WmsCheckker.rangeInstockDateCheck(startinstockdate, endinstockdate, lst_InstkCrsDcResultList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 仕入先コードの禁止文字チェック
		if (!WmsCheckker.charCheck(suppliercode, lst_InstkCrsDcResultList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 開始伝票No.の禁止文字チェック
		if (!WmsCheckker.charCheck(startticketnumber, lst_InstkCrsDcResultList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 終了伝票No.の禁止文字チェック
		if (!WmsCheckker.charCheck(endticketnumber, lst_InstkCrsDcResultList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 伝票No.の範囲チェック
		if (!WmsCheckker.rangeTicketNoCheck(startticketnumber, endticketnumber, lst_InstkCrsDcResultList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 商品コードの禁止文字チェック
		if (!WmsCheckker.charCheck(itemcode, lst_InstkCrsDcResultList, pgr_U, pgr_D, lbl_InMsg))
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
		// 開始入荷受付日
		param.setFromInstockReceiveDate(startinstockdate);
		// 終了入荷受付日
		param.setToInstockReceiveDate(endinstockdate);
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

		// SessionInstockReceiveQtyListRet インスタンス生成
		SessionInstockReceiveQtyListRet listbox = new SessionInstockReceiveQtyListRet(conn, param);
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
	private void setList(SessionInstockReceiveQtyListRet listbox, String actionName) throws Exception
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
			lst_InstkCrsDcResultList.clearRow();

			// ToolTipで使用する
			// 仕入先名称
			String title_SupplierName = DisplayText.getText("LBL-W0253");
			// 商品名称
			String title_ItemName = DisplayText.getText("LBL-W0103");
			// 実績ｹｰｽ数
			String title_resultcaseqty = DisplayText.getText("LBL-W0167");
			// 実績ﾋﾟｰｽ数
			String title_resultpieceqty = DisplayText.getText("LBL-W0169");
			// 欠品ｹｰｽ数
			String title_shortagecaseqty = DisplayText.getText("LBL-W0208");
			// 欠品ﾋﾟｰｽ数
			String title_shortagepieceqty = DisplayText.getText("LBL-W0209");
			// 賞味期限
			String title_usebydate = DisplayText.getText("LBL-W0270");
			// ｸﾛｽ/DC
			String title_crossdc = DisplayText.getText("LBL-W0028");			


			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_InstkCrsDcResultList.getMaxRows();
				// 行追加
				lst_InstkCrsDcResultList.addRow();

				// 最終行に移動
				lst_InstkCrsDcResultList.setCurrentRow(count);
				lst_InstkCrsDcResultList.setValue(1, WmsFormatter.toDispDate(irparam[i].getInstockReceiveDate(), locale));
				lst_InstkCrsDcResultList.setValue(2, irparam[i].getSupplierCode());
				lst_InstkCrsDcResultList.setValue(3, irparam[i].getInstockTicketNo());
				lst_InstkCrsDcResultList.setValue(4, irparam[i].getItemCode());
				lst_InstkCrsDcResultList.setValue(5, WmsFormatter.getNumFormat(irparam[i].getEnteringQty()));
				lst_InstkCrsDcResultList.setValue(6, WmsFormatter.getNumFormat(irparam[i].getPlanCaseQty()));
				lst_InstkCrsDcResultList.setValue(7, WmsFormatter.getNumFormat(irparam[i].getResultCaseQty()));
				lst_InstkCrsDcResultList.setValue(8, WmsFormatter.getNumFormat(irparam[i].getShortageCaseQty()));
				lst_InstkCrsDcResultList.setValue(9, irparam[i].getUseByDate());
				lst_InstkCrsDcResultList.setValue(10, irparam[i].getTcdcName());
				lst_InstkCrsDcResultList.setValue(11, WmsFormatter.toDispDate(irparam[i].getPlanDate(), locale));
				lst_InstkCrsDcResultList.setValue(12, irparam[i].getSupplierName());
				lst_InstkCrsDcResultList.setValue(13, Integer.toString(irparam[i].getInstockLineNo()));
				lst_InstkCrsDcResultList.setValue(14, irparam[i].getItemName());
				lst_InstkCrsDcResultList.setValue(15, WmsFormatter.getNumFormat(irparam[i].getBundleEnteringQty()));
				lst_InstkCrsDcResultList.setValue(16, WmsFormatter.getNumFormat(irparam[i].getPlanPieceQty()));
				lst_InstkCrsDcResultList.setValue(17, WmsFormatter.getNumFormat(irparam[i].getResultPieceQty()));
				lst_InstkCrsDcResultList.setValue(18, WmsFormatter.getNumFormat(irparam[i].getShortagePieceQty()));

				// ToolTip用のデータを編集
				ToolTipHelper toolTip = new ToolTipHelper();
				toolTip.add(title_SupplierName, irparam[i].getSupplierName());
				toolTip.add(title_ItemName, irparam[i].getItemName());
				// 実績ｹｰｽ数
				toolTip.add(title_resultcaseqty, WmsFormatter.getNumFormat(irparam[i].getResultCaseQty()));
				// 実績ﾋﾟｰｽ数
				toolTip.add(title_resultpieceqty, WmsFormatter.getNumFormat(irparam[i].getResultPieceQty()));
				// 欠品ｹｰｽ数
				toolTip.add(title_shortagecaseqty, WmsFormatter.getNumFormat(irparam[i].getShortageCaseQty()));
				// 欠品ﾋﾟｰｽ数
				toolTip.add(title_shortagepieceqty, WmsFormatter.getNumFormat(irparam[i].getShortagePieceQty()));
				// 賞味期限
				toolTip.add(title_usebydate, irparam[i].getUseByDate());
				// ｸﾛｽ/DC
				toolTip.add(title_crossdc, irparam[i].getTcdcName());

				// TOOL TIPをセットする	
				lst_InstkCrsDcResultList.setToolTip(count, toolTip.getText());
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
			lst_InstkCrsDcResultList.setVisible(false);
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
	public void lst_InstkCrsDcResultList_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcResultList_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcResultList_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcResultList_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcResultList_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcResultList_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcResultList_Click(ActionEvent e) throws Exception
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
		SessionInstockReceiveQtyListRet listbox = (SessionInstockReceiveQtyListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceiveQtyListRet listbox = (SessionInstockReceiveQtyListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceiveQtyListRet listbox = (SessionInstockReceiveQtyListRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceiveQtyListRet listbox = (SessionInstockReceiveQtyListRet) this.getSession().getAttribute("LISTBOX");
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
