// $Id: ListInstockReceivePlanBusiness.java,v 1.1.1.1 2006/08/17 09:34:12 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplan;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivecustomer.ListInstockReceiveCustomerBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret.SessionInstockReceivePlanRet;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : T.Hondo <BR>
 * Maker : T.Hondo <BR>
 * <BR>
 * 入荷予定一覧検索リストボックスクラスです。<BR>
 * 親画面から入力した荷主コード、入荷予定日、仕入先コード、ＴＣ／ＤＣ区分、出荷先コードを基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した荷主コード、入荷予定日、仕入先コード、ＴＣ／ＤＣ区分、出荷先コードをキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * 2.選択した行のボタン（<CODE>lst_ShippingPlanSearch_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	選択行の荷主コード、入荷予定日、仕入先コード、出荷先コードを親画面に渡し、リストボックスを閉じます。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/19</TD><TD>T.Hondo</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:12 $
 * @author  $Author: mori $
 */
public class ListInstockReceivePlanBusiness extends ListInstockReceivePlan implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * 作業状態の受け渡しに使用するキーです
	 */
	public static final String WORKSTATUSINSTOCKPLAN_KEY = "WORKSTATUSINSTOCKPLAN_KEY";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。 <BR>
	 * <DIR>
	 *	項目 <BR>
	 *	<DIR>
	 *		選択 <BR>
	 *		入荷予定日 <BR>
	 *		仕入先コード <BR>
	 *		仕入先名称 <BR>
	 *		TC/DC区分 <BR>
	 *		出荷先コード <BR>
	 *		出荷先名称 <BR>
	 *		伝票No. <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 入荷予定検索
		lbl_ListName.setText(DisplayText.getText("TLE-W0052"));

		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 入荷予定日
		String instockreceiveplandate = request.getParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY);
		// 仕入先コード
		String suppliercode = request.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// TC/DC区分
		String tcdcflag = request.getParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY);
		// 出荷先コード
		String customercode = request.getParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY);
		// 伝票No.
		String ticketnumber = request.getParameter(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY);
		// 作業状態
		String[] search = request.getParameterValues(WORKSTATUSINSTOCKPLAN_KEY);

		// 荷主コードの必須入力チェック、禁止文字チェック
		if (!WmsCheckker.consignorCheck(consignorcode, lst_InstkPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 仕入先コードの禁止文字チェック
		if (!WmsCheckker.charCheck(suppliercode, lst_InstkPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 出荷先コードの禁止文字チェック
		if (!WmsCheckker.charCheck(customercode, lst_InstkPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 伝票No.の禁止文字チェック
		if (!WmsCheckker.charCheck(ticketnumber, lst_InstkPlanList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 検索条件にセット
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
		// 入荷予定日
		param.setPlanDate(instockreceiveplandate);
		// 仕入先コード
		param.setSupplierCode(suppliercode);
		// TC/DC区分
		param.setTcdcFlag(tcdcflag);
		// 出荷先コード
		param.setCustomerCode(customercode);
		// 伝票No.
		param.setInstockTicketNo(ticketnumber);
		// 作業状態
		param.setSearchStatus(search);

		// SessionInstockReceivePlanRet インスタンス生成
		SessionInstockReceivePlanRet listbox = new SessionInstockReceivePlanRet(conn, param);
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
	private void setList(SessionInstockReceivePlanRet listbox, String actionName) throws Exception
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

			lbl_JavaSetConsignorName.setText(irparam[0].getConsignorName());

			// 行を全て削除
			lst_InstkPlanList.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_InstkPlanList.getMaxRows();
				// 行追加
				lst_InstkPlanList.addRow();

				// 最終行に移動
				lst_InstkPlanList.setCurrentRow(count);
				lst_InstkPlanList.setValue(0, irparam[i].getTcdcFlag());
				lst_InstkPlanList.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_InstkPlanList.setValue(2, WmsFormatter.toDispDate(irparam[i].getPlanDate(), locale));
				lst_InstkPlanList.setValue(3, irparam[i].getSupplierCode());
				lst_InstkPlanList.setValue(4, irparam[i].getTcdcName());
				lst_InstkPlanList.setValue(5, irparam[i].getCustomerCode());
				lst_InstkPlanList.setValue(6, irparam[i].getInstockTicketNo());
				lst_InstkPlanList.setValue(7, irparam[i].getSupplierName());
				lst_InstkPlanList.setValue(8, irparam[i].getCustomerName());
				
				//ToolTip
				ToolTipHelper tTip = new ToolTipHelper();
				// 出荷先名称
				tTip.add(DisplayText.getText("LBL-W0036"), irparam[i].getCustomerName());
				// 伝票No.
				tTip.add(DisplayText.getText("LBL-W0266"), irparam[i].getInstockTicketNo());
				lst_InstkPlanList.setToolTip(count, tTip.getText());
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
			lst_InstkPlanList.setVisible(false);
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
	public void lst_InstkPlanList_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkPlanList_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkPlanList_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkPlanList_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkPlanList_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkPlanList_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面に荷主コード、荷主名称、入荷予定日、仕入先コード、仕入先名称、出荷先コード、出荷先名称、伝票No.を渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void lst_InstkPlanList_Click(ActionEvent e) throws Exception
	{
		// ロケールを取得
		Locale locale = this.getHttpRequest().getLocale();

		// 現在の行をセット
		lst_InstkPlanList.setCurrentRow(lst_InstkPlanList.getActiveRow());
		lst_InstkPlanList.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetConsignorCode.getText());
		// 荷主名称
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORNAME_KEY, lbl_JavaSetConsignorName.getText());
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, lst_InstkPlanList.getValue(2));
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, lst_InstkPlanList.getValue(3));
		// 仕入先名称
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERNAME_KEY, lst_InstkPlanList.getValue(7));
		// 出荷先コード
		param.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, lst_InstkPlanList.getValue(5));
		// 出荷先名称
		param.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERNAME_KEY, lst_InstkPlanList.getValue(8));
		// 伝票No.
		param.setParameter(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY, lst_InstkPlanList.getValue(6));

		// 区分
		param.setParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY, lst_InstkPlanList.getValue(0));

		// 親画面に遷移する
		parentRedirect(param);
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
		SessionInstockReceivePlanRet listbox = (SessionInstockReceivePlanRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceivePlanRet listbox = (SessionInstockReceivePlanRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceivePlanRet listbox = (SessionInstockReceivePlanRet) this.getSession().getAttribute("LISTBOX");
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
		SessionInstockReceivePlanRet listbox = (SessionInstockReceivePlanRet) this.getSession().getAttribute("LISTBOX");
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
