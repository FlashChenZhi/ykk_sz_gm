// $Id: InstockReceivePlanModifyBusiness.java,v 1.1.1.1 2006/08/17 09:34:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.instockreceiveplanmodify;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivecustomer.ListInstockReceiveCustomerBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplan.ListInstockReceivePlanBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceivePlanModifySCH;

/**
 * 
 * Designer : T.Yoshino<BR>
 * Maker : T.Kuroda <BR>
 * <BR>
 * 入荷予定修正・削除画面クラスです。 <BR>
 * 入荷予定修正・削除を行うスケジュールにパラメータを引き渡します。 <BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * <BR>
 * 1.次へボタン押下処理( <CODE>btn_Next_Click()</CODE> メソッド) <BR>
 * <BR>
 * <DIR>
 * 画面から入力した内容をチェックし、パラメータにセットします。 <BR>
 * そのパラメータをviewStateに格納し、スケジュールに引き渡します。 <BR>
 * スケジュールから受け取った値とパラメータを元に入荷予定メンテナンス修正･削除(詳細)画面へ遷移します。 <BR>
 * <BR>
 * [ViewStateパラメータ] *必須入力 +条件によっては必須入力 <BR>
 * <BR>
 * 作業者コード* <BR>
 * パスワード* <BR>
 * 荷主コード* <BR>
 * 入荷予定日* <BR>
 * 仕入先コード* <BR>
 * TC/DC区分* <BR>
 * 出荷先コード+ <BR>
 * 伝票No.*<BR>
 * <BR>
 * </DIR> </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/11/08</TD>
 * <TD>T.Kuroda</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * <BR>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:11 $
 * @author $Author: mori $
 */
public class InstockReceivePlanModifyBusiness extends InstockReceivePlanModify implements WMSConstants
{
	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/instockreceive/listbox/listinstockreceiveconsignor/ListInstockReceiveConsignor.do";
	// 入荷予定日検索ポップアップアドレス
	private static final String DO_SRCH_INSTOCKPLANDATE = "/instockreceive/listbox/listinstockreceiveplandate/ListInstockReceivePlanDate.do";
	// 仕入先コード検索ポップアップアドレス
	private static final String DO_SRCH_SUPPLIER_CODE = "/instockreceive/listbox/listinstockreceivesupplier/ListInstockReceiveSupplier.do";
	// 出荷先コード検索ポップアップアドレス
	private static final String DO_SRCH_CUSTOMER_CODE = "/instockreceive/listbox/listinstockreceivecustomer/ListInstockReceiveCustomer.do";
	// 伝票No.検索ポップアップアドレス
	private static final String DO_SRCH_TICKET_NO = "/instockreceive/listbox/listinstockreceiveticket/ListInstockReceiveTicket.do";
	// 入荷予定検索ポップアップアドレス
	private static final String DO_SRCH_INSTOCKPLAN = "/instockreceive/listbox/listinstockreceiveplan/ListInstockReceivePlan.do";
	// 入荷予定修正削除(基本情報設定画面)アドレス
	protected static final String DO_MODIFY = "/instockreceive/instockreceiveplanmodify/InstockReceivePlanModify.do";
	// 入荷予定修正削除(詳細情報修正削除画面)アドレス
	private static final String DO_MODIFY2 = "/instockreceive/instockreceiveplanmodify/InstockReceivePlanModify2.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";

	// 作業者コード
	protected static final String WORKERCODE = "WORKERCODE";

	// パスワード
	protected static final String PASSWORD = "PASSWORD";

	// ＴＣ／ＤＣ区分
	protected static final String TCDCFLAG = "TCDCFLAG";

	// メッセージ
	protected static final String MESSAGE = "MESSAGE";

	// タイトル
	protected static final String TITLE = "TITLE";

	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			//パラメータを取得
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			//ViewStateへ保持する
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			//画面名称をセットする
			lbl_SettingName.setResourceKey(title);
			//ヘルプファイルへのパスをセットする
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID,
			// this.getHttpRequest()));
		}
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが 呼ばれます。 <CODE>Page</CODE> に定義されている <CODE>
	 * page_DlgBack</CODE> をオーバライドします。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorCode = param.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 入荷予定日
		String instockPlanDate = param.getParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY);
		// 仕入先コード
		String supplierCode = param.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// TC/DC区分
		String tcdcflag = param.getParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY);
		// 出荷先コード
		String customerCode = param.getParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY);
		// 伝票No.
		String ticketNo = param.getParameter(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		// 入荷予定日
		if (!StringUtil.isBlank(instockPlanDate))
		{
			txt_InstockPlanDate.setText(instockPlanDate);
		}
		// 仕入先コード
		if (!StringUtil.isBlank(supplierCode))
		{
			txt_SupplierCode.setText(supplierCode);
		}
		// TC/DCフラグ
		if (!StringUtil.isBlank(tcdcflag))
		{
			if (tcdcflag.equals(InstockReceiveParameter.TCDC_FLAG_DC))
			{
				rdo_CrossDCFlagDC.setChecked(true);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagTC.setChecked(false);
				// 出荷先コード(読み取り専用)
				txt_CustomerCode.setReadOnly(true);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(false);
			}
			else if (tcdcflag.equals(InstockReceiveParameter.TCDC_FLAG_CROSSTC))
			{
				rdo_CrossDCFlagDC.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(true);
				rdo_CrossDCFlagTC.setChecked(false);
				// 出荷先コード(読み取り専用)
				txt_CustomerCode.setReadOnly(true);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(false);
			}
			else if (tcdcflag.equals(InstockReceiveParameter.TCDC_FLAG_TC))
			{
				rdo_CrossDCFlagDC.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagTC.setChecked(true);
				// 出荷先コード(読み取り専用)
				txt_CustomerCode.setReadOnly(false);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(true);
			}
		}
		// 出荷先コード
		if (!StringUtil.isBlank(customerCode))
		{
			txt_CustomerCode.setText(customerCode);
		}
		// 伝票No.
		if (!StringUtil.isBlank(ticketNo))
		{
			txt_TicketNo.setText(ticketNo);
		}

		setFocus(txt_WorkerCode);
	}

	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>項目名[初期値] <BR>
	 * <DIR>作業者コード [なし] <BR>
	 * パスワード [なし] <BR>
	 * 荷主コード [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 入荷予定日 [なし] <BR>
	 * 仕入先コード [なし] <BR>
	 * TC/DC区分 [TC選択] <BR>
	 * 出荷先コード [なし] <BR>
	 * 伝票No. [なし] <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 2画面目から戻ってきた時にタイトルをセットする
		if (!StringUtil.isBlank(this.viewState.getString(TITLE)))
		{
			lbl_SettingName.setResourceKey(this.viewState.getString(TITLE));
		}

		// 基本情報設定のタブを前出しします
		tab_BscDtlMdfyDlt.setSelectedIndex(1);

		// 画面を初期状態にします
		setInitView(true);

		// 入荷予定メンテナンス修正･削除(詳細).戻るボタン押下時、
		// ViewStateに値がセットされていれば、その値をセットする。
		// 作業者ｺｰﾄﾞ
		if (!StringUtil.isBlank(viewState.getString(WORKERCODE)))
		{
			txt_WorkerCode.setText(viewState.getString(WORKERCODE));
		}
		// ﾊﾟｽﾜｰﾄﾞ
		if (!StringUtil.isBlank(getViewState().getString(PASSWORD)))
		{
			txt_Password.setText(getViewState().getString(PASSWORD));
		}
		// 荷主ｺｰﾄﾞ
		if (!StringUtil.isBlank(getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY)))
		{
			txt_ConsignorCode.setText(getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY));
		}
		// 入荷予定日
		if (!StringUtil.isBlank(getViewState().getString(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY)))
		{
			txt_InstockPlanDate.setDate(WmsFormatter.toDate(getViewState().getString(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY)));
		}
		// ＴＣ／ＤＣ区分
		String tcdcFlag = getViewState().getString(TCDCFLAG);
		if (!StringUtil.isBlank(tcdcFlag))
		{
			if (tcdcFlag.equals(InstockReceiveParameter.TCDC_FLAG_TC))
			{
				rdo_CrossDCFlagTC.setChecked(true);
				// 出荷先コード
				txt_CustomerCode.setReadOnly(false);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(true);
			}
			else if (tcdcFlag.equals(InstockReceiveParameter.TCDC_FLAG_CROSSTC))
			{
				rdo_CrossDCFlagCross.setChecked(true);
				// 出荷先コード(読み取り専用)
				txt_CustomerCode.setReadOnly(true);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(false);
			}
			else if (tcdcFlag.equals(InstockReceiveParameter.TCDC_FLAG_DC))
			{
				rdo_CrossDCFlagDC.setChecked(true);
				// 出荷先コード(読み取り専用)
				txt_CustomerCode.setReadOnly(true);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(false);
			}
		}
		// 仕入先コード
		if (!StringUtil.isBlank(getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY)))
		{
			txt_SupplierCode.setText(getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY));
		}
		// 出荷先コード
		if (!StringUtil.isBlank(getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY)))
		{
			txt_CustomerCode.setText(getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY));
		}
		// 伝票No.
		if (!StringUtil.isBlank(getViewState().getString(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY)))
		{
			txt_TicketNo.setText(getViewState().getString(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY));
		}
		// メッセージ
		if (!StringUtil.isBlank(this.getViewState().getString(MESSAGE)))
		{
			// メッセージが存在すれば表示します。
			message.setMsgResourceKey(this.getViewState().getString(MESSAGE));
			// メッセージを表示した後、ViewStateのメッセージをクリア
			this.getViewState().setString(MESSAGE, "");
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * 初期表示時、スケジュールから荷主コードを取得するためのメソッドです。 <BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主コードを返します。 <BR>
	 * 
	 * @return 荷主コード <BR>
	 *         該当データが1件のときは荷主コードの文字列、 <BR>
	 *         該当データが0件または複数件あれば空文字を返します。 <BR>
	 * 
	 * @throws Exception 全ての例外を報告します。
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;
		try
		{
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			InstockReceiveParameter param = new InstockReceiveParameter();

			// スケジュールから荷主コードを取得します
			WmsScheduler schedule = new InstockReceivePlanModifySCH();
			param = (InstockReceiveParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				return param.getConsignorCode();
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				 // コネクションクローズ
				 if (conn != null)
				 {
					 conn.close();
				 }
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
		return "";
	}

	/**
	 * 画面の初期化を行うメソッドです。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>[パラメータ] <BR>
	 * <DIR>wkrClrFlg trueの場合作業者ｺｰﾄﾞ/ﾊﾟｽﾜｰﾄﾞを初期化、falseの場合作業者ｺｰﾄﾞ/ﾊﾟｽﾜｰﾄﾞを初期化しない
	 * <BR>
	 * <DIR>項目名[初期値] <BR>
	 * <DIR>作業者ｺｰﾄﾞ [なし/クリアボタン押下時はそのまま] <BR>
	 * ﾊﾟｽﾜｰﾄﾞ [なし/クリアボタン押下時はそのまま] <BR>
	 * 荷主ｺｰﾄﾞ [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 入荷予定日 [なし] <BR>
	 * 仕入先コード [なし] <BR>
	 * TC/DC区分 [TC選択] <BR>
	 * 出荷先コード [なし] <BR>
	 * 伝票No. [なし] <BR>
	 * <BR>
	 * </DIR> </DIR> </DIR>
	 * 
	 * @param wkrClrFlg
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setInitView(boolean wkrClrFlg) throws Exception
	{
		// 入力項目の初期化を行う
		if (wkrClrFlg)
		{
			// 作業者ｺｰﾄﾞ
			txt_WorkerCode.setText("");
			// ﾊﾟｽﾜｰﾄﾞ
			txt_Password.setText("");
		}
		// 荷主ｺｰﾄﾞ
		txt_ConsignorCode.setText(getConsignorCode());
		// 入荷予定日
		txt_InstockPlanDate.setText("");
		// 仕入先コード
		txt_SupplierCode.setText("");
		// ＴＣ／ＤＣ区分：ＴＣ
		rdo_CrossDCFlagTC.setChecked(true);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);
		// 出荷先コード：編集可能状態
		txt_CustomerCode.setText("");
		txt_CustomerCode.setReadOnly(false);
		// 出荷先コード検索ボタン：編集可能状態
		btn_PSearchCustomerCode.setEnabled(true);
		// 伝票No.
		txt_TicketNo.setText("");

		// 作業者ｺｰﾄﾞにフォーカス異動
		setFocus(txt_WorkerCode);
	}

	// Event handler methods -----------------------------------------
	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * メニューボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:メニュー画面に遷移します。 <BR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_WorkerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_Password_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で荷主一覧リストボックスを表示します。 <BR>
	 * <BR>
	 * <DIR>[パラメータ] <BR>
	 * <DIR>荷主コード <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 検索フラグ：予定
		param.setParameter(ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		// 作業状態をセット
		param.setParameter(ListInstockReceiveConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		// 処理中画面->結果画面
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_ConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_InstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_InstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_InstockPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_InstockPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchInstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 入荷予定日の検索ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入荷予定日一覧リストボックスを表示します。 <BR>
	 * <BR>
	 * <DIR>[パラメータ] <BR>
	 * <DIR>荷主コード <BR>
	 * 入荷予定日 <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_PSearchInstockPlanDate_Click(ActionEvent e) throws Exception
	{
		// 入荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		// 作業状態をセット
		param.setParameter(ListInstockReceivePlanDateBusiness.WORKSTATUSINSTOCKPLANDATE_KEY, search);
		// 処理中画面->結果画面
		redirect(DO_SRCH_INSTOCKPLANDATE, param, DO_SRCH_PROCESS);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_SupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_SupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_SupplierCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_SupplierCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_SupplierCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchSupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 仕入先コードの検索ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入荷棚一覧リストボックスを表示します。 <BR>
	 * <BR>
	 * <DIR>[パラメータ] <BR>
	 * <DIR>荷主コード <BR>
	 * 入荷予定日 <BR>
	 * 仕入先コード <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchSupplierCode_Click(ActionEvent e) throws Exception
	{
		// 仕入先検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());

		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		// 作業状態をセット
		param.setParameter(ListInstockReceiveSupplierBusiness.WORKSTATUSSUPPLIER_KEY, search);
		// 検索フラグ
		param.setParameter(ListInstockReceiveSupplierBusiness.SEARCHSUPPLIER_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);

		// 処理中画面->結果画面
		redirect(DO_SRCH_SUPPLIER_CODE, param, DO_SRCH_PROCESS);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_SupplierName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SupplierName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SupplierName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SupplierName_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_SupplierName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_TCDCFlag_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void rdo_CrossDCFlagDC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * TC/DC区分のラジオボタン（DC)が選択されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:出荷先コード、出荷先検索ボタンを無効にします。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagDC_Click(ActionEvent e) throws Exception
	{
		// 出荷先コード
		txt_CustomerCode.setReadOnly(true);
		txt_CustomerCode.setText("");
		// 作業者ｺｰﾄﾞにフォーカス移動
		setFocus(txt_WorkerCode);
		// 出荷先検索ボタン
		btn_PSearchCustomerCode.setEnabled(false);
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CrossDCFlagCross_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * TC/DC区分のラジオボタン（クロス)が選択されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:出荷先コード、出荷先検索ボタンを無効にします。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagCross_Click(ActionEvent e) throws Exception
	{
		// 出荷先コード
		txt_CustomerCode.setReadOnly(true);
		txt_CustomerCode.setText("");
		// 作業者ｺｰﾄﾞにフォーカス移動
		setFocus(txt_WorkerCode);
		// 出荷先検索ボタン
		btn_PSearchCustomerCode.setEnabled(false);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagTC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * TC/DC区分のラジオボタン（TC)が選択されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:出荷先コード、出荷先検索ボタンを有効にします。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagTC_Click(ActionEvent e) throws Exception
	{
		// 出荷先コード
		txt_CustomerCode.setReadOnly(false);
		// 作業者ｺｰﾄﾞにフォーカス移動
		setFocus(txt_WorkerCode);
		// 出荷先検索ボタン
		btn_PSearchCustomerCode.setEnabled(true);
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void lbl_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CustomerCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CustomerCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void txt_CustomerCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 出荷先コードの検索ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入荷棚一覧リストボックスを表示します。 <BR>
	 * <BR>
	 * <DIR>[パラメータ] <BR>
	 * <DIR>荷主コード <BR>
	 * 入荷予定日 <BR>
	 * 仕入先コード <BR>
	 * 出荷先コード <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		// 出荷先コード検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// TC/DC区分
		if (rdo_CrossDCFlagDC.getChecked())
		{
			param.setParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY, InstockReceiveParameter.TCDC_FLAG_DC);
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			param.setParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY, InstockReceiveParameter.TCDC_FLAG_CROSSTC);
		}
		else if (rdo_CrossDCFlagTC.getChecked())
		{
			param.setParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY, InstockReceiveParameter.TCDC_FLAG_TC);
		}
		// 出荷先コード
		param.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());

		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		// 作業状態をセット
		param.setParameter(ListInstockReceiveCustomerBusiness.WORKSTATUSSUPPLIER_KEY, search);
		// 検索フラグ
		param.setParameter(ListInstockReceiveCustomerBusiness.SEARCHCUSTOMER_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);

		// 処理中画面->結果画面
		redirect(DO_SRCH_CUSTOMER_CODE, param, DO_SRCH_PROCESS);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_CustomerName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CustomerName_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CustomerName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CustomerName_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_CustomerName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void lbl_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_TicketNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_TicketNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void txt_TicketNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_PSearchTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 伝票No.の検索ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入荷伝票一覧No.リストボックスを表示します。 <BR>
	 * <BR>
	 * <DIR>[パラメータ] <BR>
	 * <DIR>荷主コード <BR>
	 * 入荷予定日 <BR>
	 * 仕入先コード <BR>
	 * TC/DC区分<BR>
	 * 出荷先コード <BR>
	 * 伝票No. <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_PSearchTicketNo_Click(ActionEvent e) throws Exception
	{
		// 伝票No.検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// TC/DC区分
		if (rdo_CrossDCFlagDC.getChecked())
		{
			param.setParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY, InstockReceiveParameter.TCDC_FLAG_DC);
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			param.setParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY, InstockReceiveParameter.TCDC_FLAG_CROSSTC);
		}
		else if (rdo_CrossDCFlagTC.getChecked())
		{
			param.setParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY, InstockReceiveParameter.TCDC_FLAG_TC);
		}
		// 出荷先コード
		param.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		// 伝票No.
		param.setParameter(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY, txt_TicketNo.getText());

		param.setParameter(ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);

		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		// 作業状態をセット
		param.setParameter(ListInstockReceiveTicketBusiness.WORKSTATUSTICKETNUMBER_KEY, search);

		// 処理中画面->結果画面
		redirect(DO_SRCH_TICKET_NO, param, DO_SRCH_PROCESS);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 次へボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要：入力エリアの入力項目を条件に、入荷予定修正･削除(詳細)画面に遷移します。 <BR>
	 * <BR>
	 * <DIR>1.入力エリア入力項目のチェックを行います。(必須入力) <BR>
	 * 2.スケジュールを開始します。 <BR>
	 * <DIR>[パラメータ] *必須入力 +条件によっては必須入力 <BR>
	 * <DIR>作業者コード* <BR>
	 * パスワード* <BR>
	 * 荷主コード* <BR>
	 * 入荷予定日* <BR>
	 * 仕入先コード* <BR>
	 * TC/DC区分* <BR>
	 * 出荷先コード+ <BR>
	 * 伝票No.* <BR>
	 * </DIR> </DIR> 3.スケジュールの結果がtrueであれば、入力項目を条件に次画面(予定登録詳細)に遷移します。 <BR>
	 * <DIR>[ViewStateパラメータ] *必須入力 +条件によっては必須入力 <BR>
	 * <DIR>作業者コード* <BR>
	 * パスワード* <BR>
	 * 荷主コード* <BR>
	 * 入荷予定日* <BR>
	 * 仕入先コード* <BR>
	 * TC/DC区分* <BR>
	 * 出荷先コード+ <BR>
	 * 伝票No.* <BR>
	 * <BR>
	 * </DIR> </DIR> falseの時はスケジュールから取得したメッセージを画面に出力します。 <BR>
	 * <BR>
	 * 4.入力エリアの内容は保持します。 <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードにセットします
		setFocus(txt_WorkerCode);

		// 必須入力チェック
		// 作業者コード
		txt_WorkerCode.validate();
		// パスワード
		txt_Password.validate();
		// 荷主コード
		txt_ConsignorCode.validate();
		// 入荷予定日
		txt_InstockPlanDate.validate();
		// 仕入先コード
		txt_SupplierCode.validate();
		// 出荷先コード（ＴＣ品時のみ必須）
		txt_CustomerCode.validate(rdo_CrossDCFlagTC.getChecked());
		// 伝票No.
		txt_TicketNo.validate();

		// パラメータをセットします
		InstockReceiveParameter param = new InstockReceiveParameter();

		// 作業者コード
		param.setWorkerCode(txt_WorkerCode.getText());
		// パスワード
		param.setPassword(txt_Password.getText());
		// 荷主コード
		param.setConsignorCode(txt_ConsignorCode.getText());
		// 入荷予定日
		param.setInstockPlanUkey(WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
		// 仕入先コード
		param.setSupplierCode(txt_SupplierCode.getText());
		// ＴＣ／ＤＣ区分
		if (rdo_CrossDCFlagTC.getChecked())
		{
			param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC);
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
		}
		else if (rdo_CrossDCFlagDC.getChecked())
		{
			param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
		}
		// 出荷先コード
		param.setCustomerCode(txt_CustomerCode.getText());
		// 伝票No.
		param.setInstockTicketNo(txt_TicketNo.getText());

		Connection conn = null;

		// スケジュールクラスのインスタンス生成をします
		WmsScheduler schedule = new InstockReceivePlanModifySCH();

		try
		{
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールクラスで入力パラメーターチェックで問題がなければ
			// viewStateに画面パラメータをセットして入荷予定修正・削除(詳細)へ遷移します
			if (schedule.nextCheck(conn, param))
			{
				// 作業者ｺｰﾄﾞ
				viewState.setString(WORKERCODE, txt_WorkerCode.getText());
				// ﾊﾟｽﾜｰﾄﾞ
				viewState.setString(PASSWORD, txt_Password.getText());
				// 荷主ｺｰﾄﾞ
				viewState.setString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
				// 入荷予定日
				viewState.setString(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
				// 仕入先コード
				viewState.setString(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
				// ＴＣ／ＤＣ区分
				if (rdo_CrossDCFlagTC.getChecked())
				{
					viewState.setString(TCDCFLAG, InstockReceiveParameter.TCDC_FLAG_TC);
				}
				else if (rdo_CrossDCFlagCross.getChecked())
				{
					viewState.setString(TCDCFLAG, InstockReceiveParameter.TCDC_FLAG_CROSSTC);
				}
				else if (rdo_CrossDCFlagDC.getChecked())
				{
					viewState.setString(TCDCFLAG, InstockReceiveParameter.TCDC_FLAG_DC);
				}
				// 出荷先コード
				viewState.setString(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
				// 伝票No.
				viewState.setString(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY, txt_TicketNo.getText());

				// 画面タイトル
				viewState.setString(TITLE, lbl_SettingName.getResourceKey());

				// 基本情報設定画面->詳細情報修正・削除画面
				forward(DO_MODIFY2);
			}
			else
			{
				// 対象データが存在しない場合は、エラーメッセージを表示します
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				 // コネクションクローズ
				 if (conn != null)
				 {
					 conn.rollback();
					 conn.close();
				 }
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:入力エリアをクリアします。(作業者コード、パスワードはクリアしません) <BR>
	 * <BR>
	 * <DIR>1.画面の初期化を行います。 <BR>
	 * 初期値については <CODE>setInitView(boolean)</CODE> メソッドを参照してください。 <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 画面初期表示用メソッドを呼び出す。(作業者コード、パスワードはクリアしません)
		setInitView(false);
	}

	/**
	 * 
	 * @param e
	 *            ActionEvent
	 * @throws Exception
	 */
	public void btn_PInstkPlanSrch_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 入荷予定検索ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入荷予定検索リストボックスを表示します。 <BR>
	 * <BR>
	 * <DIR>[パラメータ] *必須入力 <BR>
	 * <DIR>荷主コード* <BR>
	 * 入荷予定日 <BR>
	 * 仕入先コード <BR>
	 * TC/DC区分 <BR>
	 * 出荷先コード <BR>
	 * 伝票No. <BR>
	 * </DIR> <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_PInstkPlanSrch_Click(ActionEvent e) throws Exception
	{
		// 入荷予定データ検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// TC/DC区分
		if (rdo_CrossDCFlagDC.getChecked())
		{
			param.setParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY, InstockReceiveParameter.TCDC_FLAG_DC);
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			param.setParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY, InstockReceiveParameter.TCDC_FLAG_CROSSTC);
		}
		else if (rdo_CrossDCFlagTC.getChecked())
		{
			param.setParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY, InstockReceiveParameter.TCDC_FLAG_TC);
		}
		// 出荷先コード
		param.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		// 伝票No.
		param.setParameter(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY, txt_TicketNo.getText());
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		// 作業状態をセット
		param.setParameter(ListInstockReceivePlanBusiness.WORKSTATUSINSTOCKPLAN_KEY, search);
		// 処理中画面->結果画面
		redirect(DO_SRCH_INSTOCKPLAN, param, DO_SRCH_PROCESS);
	}

}
//end of class
