// $Id: InstockReceivePlanRegistBusiness.java,v 1.1.1.1 2006/08/17 09:34:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.instockreceiveplanregist;
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
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivecustomer.ListInstockReceiveCustomerBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceivePlanRegistSCH;

/**
 * Designer : T.Yoshino <BR>
 * Maker : D.Hakui <BR>

 * <BR>
 * 入荷予定データ登録画面クラスです。 <BR>
 * 入荷予定データ登録を行うスケジュールにパラメータを引き渡します。 <BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * <DIR>
 * 1.次へボタン押下処理( <CODE>btn_Next_Click()</CODE> メソッド) <BR>
 * <BR>
 * <DIR>
 * 画面から入力した内容をチェックし、パラメータにセットします。 <BR>
 * そのパラメータをviewStateに格納し、スケジュールに引き渡します。 <BR>
 * スケジュールから受け取った値とパラメータを元に入荷予定データ登録(詳細情報)画面へ遷移します。 <BR>
 * <BR>
 * [ViewStateパラメータ] *必須入力 <BR>
 * <BR>
 * 作業者コード* <BR>
 * パスワード* <BR>
 * 荷主コード* <BR>
 * 荷主名称 <BR>
 * 入荷予定日* <BR>
 * 仕入先コード* <BR>
 * 仕入先名称 <BR>
 * TC/DC区分* <BR>
 * 出荷先コード*（TC品時必須入力） <BR>
 * 出荷先名称 <BR>
 * 伝票No.* <BR>
 * <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:11 $
 * @author  $Author: mori $
 */
public class InstockReceivePlanRegistBusiness extends InstockReceivePlanRegist implements WMSConstants
{
	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/instockreceive/listbox/listinstockreceiveconsignor/ListInstockReceiveConsignor.do";
	// 入荷予定日検索ポップアップアドレス
	private static final String DO_SRCH_INSTOCKPLANDATE = "/instockreceive/listbox/listinstockreceiveplandate/ListInstockReceivePlanDate.do";
	// 仕入先検索ポップアップアドレス
	private static final String DO_SRCH_SUPPLIER = "/instockreceive/listbox/listinstockreceivesupplier/ListInstockReceiveSupplier.do";
	// 出荷先検索ポップアップアドレス
	private static final String DO_SRCH_CUSTOMER = "/instockreceive/listbox/listinstockreceivecustomer/ListInstockReceiveCustomer.do";
	// 伝票Noポップアップアドレス
	protected static final String DO_SRCH_TICKET = "/instockreceive/listbox/listinstockreceiveticket/ListInstockReceiveTicket.do";
	// 入荷予定検索ポップアップアドレス
	private static final String DO_SRCH_INSTOCKPLAN = "/instockreceive/listbox/listinstockreceiveplan/ListInstockReceivePlan.do";
	// 入荷予定登録(基本情報設定画面)アドレス
	protected static final String DO_REGIST = "/instockreceive/instockreceiveplanregist/InstockReceivePlanRegist.do";
	// 入荷予定登録2(詳細情報設定画面)アドレス
	protected static final String DO_REGIST2 = "/instockreceive/instockreceiveplanregist/InstockReceivePlanRegist2.do";
	
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";

	// 作業者コード
	protected static final String WORKERCODE = "WORKERCODE";
	// パスワード
	protected static final String PASSWORD = "PASSWORD";
	// メッセージ
	protected static final String MESSAGE = "MESSAGE";
	// ＴＣ／ＤＣ区分(コード)
	protected static final String TCDCFLG = "TCDCFLG";
	/**
	 * タイトルの受け渡しに使用するViewState用キーです
	 */
	public static final String TITLE_KEY = "TITLE_KEY";	
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
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
		}
	}
	
	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 作業者コード [なし] <BR>
	 * パスワード [なし] <BR>
	 * 荷主コード [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 荷主名称 [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 入荷予定日 [なし] <BR>
	 * 仕入先コード [なし] <BR>
	 * 仕入先名称 [なし] <BR>
	 * TC/DC区分 [TC選択] <BR>
	 * 出荷先コード [なし] <BR>
	 * 出荷先名称 [なし] <BR>
	 * 伝票No. [なし] <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// タイトル
		if (!StringUtil.isBlank(this.viewState.getString(TITLE_KEY)))
		{
			lbl_SettingName.setResourceKey(this.viewState.getString(TITLE_KEY));
		}	
			
		 setInitView(true);

		// 入荷予定登録(詳細).戻るボタン押下時、
		// ViewStateに値がセットされていれば、その値をセットします

		// 作業者コード
		if (!StringUtil.isBlank(this.viewState.getString(WORKERCODE)))
		{
			txt_WorkerCode.setText(this.getViewState().getString(WORKERCODE));
		}	
		// パスワード
		if (!StringUtil.isBlank(this.getViewState().getString(PASSWORD)))
		{
			txt_Password.setText(this.getViewState().getString(PASSWORD));
		}
		// 荷主コード
		if (!StringUtil.isBlank(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY)))
		{
			txt_ConsignorCode.setText(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY));
		}
		// 荷主名称
		if (!StringUtil.isBlank(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORNAME_KEY)))
		{
			txt_ConsignorName.setText(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORNAME_KEY));
		}
		// 入荷予定日
		if (!StringUtil.isBlank(this.getViewState().getString(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY)))
		{
			txt_InstockPlanDate.setText(this.getViewState().getString(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY));
		}
		// 仕入先コード
		if (!StringUtil.isBlank(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY)))
		{
			txt_SupplierCode.setText(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY));
		}
		// 仕入先名称
		if (!StringUtil.isBlank(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERNAME_KEY)))
		{
			txt_SupplierName.setText(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERNAME_KEY));
		}
		// ＴＣ／ＤＣ区分
		if (!StringUtil.isBlank(this.getViewState().getString(InstockReceivePlanRegistBusiness.TCDCFLG)))
		{
			String str = this.getViewState().getString(InstockReceivePlanRegistBusiness.TCDCFLG);
			
			if (str.equals( InstockReceiveParameter.TCDC_FLAG_DC ))
			{
				// DC
				rdo_CrossDCFlagDC.setChecked(true);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagTC.setChecked(false);
				// 出荷先コード(読み取り専用)
				txt_CustomerCode.setReadOnly(true);
				// 出荷先名称(読み取り専用)
				txt_CustomerName.setReadOnly(true);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(false);
			}
			else if (str.equals( InstockReceiveParameter.TCDC_FLAG_TC ))
			{
				// TC
				rdo_CrossDCFlagDC.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagTC.setChecked(true);
				// 出荷先コード
				txt_CustomerCode.setReadOnly(false);
				// 出荷先名称
				txt_CustomerName.setReadOnly(false);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(true);
			}
			else if (str.equals( InstockReceiveParameter.TCDC_FLAG_CROSSTC ))
			{
				// クロス
				rdo_CrossDCFlagDC.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(true);
				rdo_CrossDCFlagTC.setChecked(false);
				// 出荷先コード(読み取り専用)
				txt_CustomerCode.setReadOnly(true);
				// 出荷先名称(読み取り専用)
				txt_CustomerName.setReadOnly(true);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(false);
			}
		}
		// 出荷先コード
		if (!StringUtil.isBlank(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY)))
		{
			txt_CustomerCode.setText(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY));
		}
		// 出荷先名称
		if (!StringUtil.isBlank(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERNAME_KEY)))
		{
			txt_CustomerName.setText(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERNAME_KEY));
		}
		// 伝票No
		if (!StringUtil.isBlank(this.getViewState().getString(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY)))
		{
			txt_TicketNo.setText(this.getViewState().getString(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY));
		}
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{

		// 作業者ｺｰﾄﾞにフォーカス移動
		setFocus(txt_WorkerCode);
		
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 荷主名称
		String consignorname = param.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORNAME_KEY);
		// 入荷予定日
		String reciveplandate = param.getParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY);
		// 仕入先コード
		String suppliercode = param.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// 仕入先名称
		String suppliername = param.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERNAME_KEY);
		// 出荷先コード
		String customercode = param.getParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY);
		// 出荷先名称
		String customername = param.getParameter(ListInstockReceiveCustomerBusiness.CUSTOMERNAME_KEY);
		// 伝票No.
		String ticketno = param.getParameter(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY);
		// TC/DC区分
		String tcdcflag = param.getParameter(ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY);
		
		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 荷主名称
		if (!StringUtil.isBlank(consignorname))
		{
			txt_ConsignorName.setText(consignorname);
		}
		// 入荷予定日
		if (!StringUtil.isBlank(reciveplandate))
		{
			txt_InstockPlanDate.setText(reciveplandate);
		}
		// 仕入先コード
		if (!StringUtil.isBlank(suppliercode))
		{
			txt_SupplierCode.setText(suppliercode);
		}
		// 仕入先名称
		if (!StringUtil.isBlank(suppliername))
		{
			txt_SupplierName.setText(suppliername);
		}
		// 出荷先コード
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		// 出荷先名称
		if (!StringUtil.isBlank(customername))
		{
			txt_CustomerName.setText(customername);
		}
		// 伝票No.
		if (!StringUtil.isBlank(ticketno))
		{
			txt_TicketNo.setText(ticketno);
		}
		// TC/DC区分
		if (!StringUtil.isBlank(tcdcflag))
		{
			if (tcdcflag.equals(InstockReceiveParameter.TCDC_FLAG_DC))
			{
				rdo_CrossDCFlagDC.setChecked(true);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagTC.setChecked(false);
				// 出荷先コード(読み取り専用)
				txt_CustomerCode.setReadOnly(true);
				// 出荷先名称(読み取り専用)
				txt_CustomerName.setReadOnly(true);
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
				// 出荷先名称(読み取り専用)
				txt_CustomerName.setReadOnly(true);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(false);
			}
			else if (tcdcflag.equals(InstockReceiveParameter.TCDC_FLAG_TC))
			{
				rdo_CrossDCFlagDC.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagTC.setChecked(true);
				// 出荷先コード
				txt_CustomerCode.setReadOnly(false);
				// 出荷先名称
				txt_CustomerName.setReadOnly(false);
				// 出荷先検索ボタン
				btn_PSearchCustomerCode.setEnabled(true);
			}
		}
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。
	 * 異常があった場合は、メッセージをセットし、falseを返します。
	 * 
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ConsignorName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_SupplierCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_SupplierName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CustomerCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CustomerName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_TicketNo))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	// Private methods -----------------------------------------------
	/**
	 * 画面の初期化を行うメソッドです。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * [パラメーター] <BR>
	 * <DIR>
	 * wkrClrFlg trueの場合作業者ｺｰﾄﾞ/ﾊﾟｽﾜｰﾄﾞを初期化、falseの場合作業者ｺｰﾄﾞ/ﾊﾟｽﾜｰﾄﾞを初期化しない <BR>
	 * <DIR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 作業者ｺｰﾄﾞ [なし/クリアボタン押下時はそのまま] <BR>
	 * ﾊﾟｽﾜｰﾄﾞ [なし/クリアボタン押下時はそのまま] <BR>
	 * 荷主コード [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 荷主名称 [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 入荷予定日 [なし] <BR>
	 * 仕入先コード [なし] <BR>
	 * 仕入先名称 [なし] <BR>
	 * TC/DC区分 [TC選択] <BR>
	 * 出荷先コード [なし] <BR>
	 * 出荷先名称 [なし] <BR>
	 * 伝票No. [なし] <BR>
	 * <BR>
	 * </DIR>
	 * </DIR>
	 * </DIR>
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitView(boolean wkrClrFlg) throws Exception
	{
		// 作業者ｺｰﾄﾞにフォーカス移動
		setFocus(txt_WorkerCode);
		
		// タブを「基本情報設定」に設定
		tab_BscDtlRst.setSelectedIndex(1);
		
		// 入力項目の初期化を行う
		if (wkrClrFlg)
		{
			// 作業者コード
			txt_WorkerCode.setText("");
			// パスワード
			txt_Password.setText("");
		}
				
		// 荷主コード、荷主名称
		setConsignor();
		// 入荷予定日
		txt_InstockPlanDate.setText("");
		// 仕入先コード
		txt_SupplierCode.setText("");
		// 仕入先名称
		txt_SupplierName.setText("");
		// ＴＣ／ＤＣ区分：ＴＣ
		rdo_CrossDCFlagTC.setChecked(true);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);
		// 出荷先コード：編集可能状態
		txt_CustomerCode.setText("");
		txt_CustomerCode.setReadOnly(false);
		// 出荷先名称：編集可能状態
		txt_CustomerName.setText("");
		txt_CustomerName.setReadOnly(false);
		// 出荷先コード検索ボタン：編集可能状態
		btn_PSearchCustomerCode.setEnabled(true);
		// 伝票No
		txt_TicketNo.setText("");
		
		if(!StringUtil.isBlank(this.getViewState().getString(MESSAGE)))
		{
			message.setMsgResourceKey(this.getViewState().getString(MESSAGE));
		}
	}

	/**
	 * 初期表示時、スケジュールから荷主を取得するためのメソッドです。 <BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主を入力エリアにセットします。 <BR>
	 * 
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setConsignor() throws Exception
	{
		Connection conn = null;
		try
		{
			// 20050830 modify start  Y.Kagawa
			txt_ConsignorCode.setText("");
			txt_ConsignorName.setText("");
			
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			InstockReceiveParameter param = new InstockReceiveParameter();

			// スケジュールから荷主コードを取得します
			WmsScheduler schedule = new InstockReceivePlanRegistSCH();
			param = (InstockReceiveParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
			}
			else
			{
				txt_ConsignorCode.setText("");
				txt_ConsignorName.setText("");
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
	}
	
	// Event handler methods -----------------------------------------
	/** 
	 * メニューボタンが押下された時に呼ばれます。<BR>
	 * <BR>
	 * 概要:このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   メニュー画面に遷移します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		forward(BusinessClassHelper.getSubMenuPath(this.getViewState().getString(M_MENUID_KEY)));
	}

	/** 
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で荷主検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       検索フラグ（予定） <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
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
		// 処理中画面->結果画面
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 入荷予定日の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入荷予定日検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       入荷予定日 <BR>
	 *       検索コード（予定） <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchInstockPlanDate_Click(ActionEvent e) throws Exception
	{
		// 入荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
		// 処理中画面->結果画面
		redirect(DO_SRCH_INSTOCKPLANDATE, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 仕入先コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で仕入先検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       入荷予定日 <BR>
	 *       仕入先コード <BR>
	 *       検索フラグ（予定） <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
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
		// 検索フラグ：予定
		param.setParameter(ListInstockReceiveSupplierBusiness.SEARCHSUPPLIER_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);
		// 処理中画面->結果画面
		redirect(DO_SRCH_SUPPLIER, param, DO_SRCH_PROCESS);
	}

	/** 
	 * TC/DC区分のラジオボタン（DC)が選択されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:出荷先コード、出荷先名称、出荷先検索ボタンを無効にします。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagDC_Click(ActionEvent e) throws Exception
	{
		// 出荷先コード
		txt_CustomerCode.setReadOnly(true);
		txt_CustomerCode.setText("");
		// 出荷先名称
		txt_CustomerName.setReadOnly(true);
		txt_CustomerName.setText("");
		// 作業者ｺｰﾄﾞにフォーカス移動
		setFocus(txt_WorkerCode);
		// 出荷先検索ボタン
		btn_PSearchCustomerCode.setEnabled(false);
	}

	/** 
	 * TC/DC区分のラジオボタン（クロス)が選択されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:出荷先コード、出荷先名称、出荷先検索ボタンを無効にします。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagCross_Click(ActionEvent e) throws Exception
	{
		// 出荷先コード
		txt_CustomerCode.setReadOnly(true);
		txt_CustomerCode.setText("");
		// 出荷先名称
		txt_CustomerName.setReadOnly(true);
		txt_CustomerName.setText("");
		// 作業者ｺｰﾄﾞにフォーカス移動
		setFocus(txt_WorkerCode);
		// 出荷先検索ボタン
		btn_PSearchCustomerCode.setEnabled(false);
	}

	/** 
	 * TC/DC区分のラジオボタン（TC)が選択されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:出荷先コード、出荷先名称、出荷先検索ボタンを有効にします。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void rdo_CrossDCFlagTC_Click(ActionEvent e) throws Exception
	{
		// 出荷先コード
		txt_CustomerCode.setReadOnly(false);
		// 出荷先名称
		txt_CustomerName.setReadOnly(false);
		// 作業者ｺｰﾄﾞにフォーカス移動
		setFocus(txt_WorkerCode);
		// 出荷先検索ボタン
		btn_PSearchCustomerCode.setEnabled(true);
	}

	/** 
	 * 出荷先コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で出荷先検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       入荷予定日 <BR>
	 *       仕入先コード <BR>
	 *       TC/DC区分 <BR>
	 *       出荷先コード <BR>
	 *       検索フラグ（予定）<BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		// 出荷先検索画面へ検索条件をセットする
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
		// 検索フラグ：予定
		param.setParameter(ListInstockReceiveCustomerBusiness.SEARCHCUSTOMER_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);
		// 処理中画面->結果画面
		redirect(DO_SRCH_CUSTOMER, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 伝票No.の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入荷伝票検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       入荷予定日 <BR>
	 *       仕入先コード <BR>
	 *       TC/DC区分 <BR>
	 *       出荷先コード <BR>
	 *       伝票No. <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchTicketNo_Click(ActionEvent e) throws Exception
	{
		// 伝票No検索画面へ検索条件をセットする
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
		// 検索フラグ：予定
		param.setParameter(ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);
		// 処理中画面->結果画面
		redirect(DO_SRCH_TICKET, param, DO_SRCH_PROCESS);
	}

	/**
	 * 次へボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要：入力エリアの入力項目を条件に、入荷予定データ予定登録(詳細情報)画面に遷移します。 <BR>
	 * <BR>
	 * <DIR>
	 * 1.入力エリア入力項目のチェックを行います。(必須入力) <BR>
	 * 2.スケジュールを開始します。 <BR>
	 * <DIR>
	 * [パラメータ] *必須入力 <BR>
	 * <DIR>
	 * 作業者コード* <BR>
	 * パスワード* <BR>
	 * 荷主コード* <BR>
	 * 荷主名称 <BR>
	 * 入荷予定日* <BR>
	 * 仕入先コード* <BR>
	 * 仕入先名称 <BR>
	 * ＴＣ／ＤＣ区分* <BR>
	 * 出荷先コード*(TC品時必須入力)<BR>
	 * 出荷先名称 <BR>
	 * 伝票Ｎｏ．* <BR>
	 * </DIR>
	 * </DIR>
	 * 3.スケジュールの結果がtrueであれば、入力項目を条件に次画面(詳細情報登録)に遷移します。 <BR>
	 * <DIR>
	 * [ViewStateパラメータ] *必須入力 <BR>
	 * <DIR>
	 * 作業者コード* <BR>
	 * パスワード* <BR>
	 * 荷主コード* <BR>
	 * 荷主名称 <BR>
	 * 入荷予定日* <BR>
	 * 仕入先コード* <BR>
	 * 仕入先名称 <BR>
	 * ＴＣ／ＤＣ区分* <BR>
	 * 出荷先コード*(TC品時必須入力) <BR>
	 * 出荷先名称 <BR>
	 * 伝票Ｎｏ．* <BR>
	 * </DIR>
	 * </DIR>
	 * falseの時はスケジュールから取得したメッセージを画面に出力します。 <BR>
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

		// ViewState格納用
		String tcdcFlg = "";
		
		// 入力チェック
		// 作業者コード(必須)
		txt_WorkerCode.validate();
		// パスワード(必須)
		txt_Password.validate();
		// 荷主コード(必須)
		txt_ConsignorCode.validate();
		// 入荷予定日(必須)
		txt_InstockPlanDate.validate();
		// 仕入先コード(必須)
		txt_SupplierCode.validate();
		// 20050811 modify start Y.Kagawa
		// ＴＣ／ＤＣ区分がＴＣの場合、出荷先コードは必須
		txt_CustomerCode.validate(rdo_CrossDCFlagTC.getChecked());
		// 伝票No.(必須)
		txt_TicketNo.validate();
		
		// 荷主名称
		txt_ConsignorName.validate(false);
		// 仕入先名称
		txt_SupplierName.validate(false);
		// 20050811 modify start Y.Kagawa
		// 出荷先名称
		txt_CustomerName.validate(false);
		
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}

		// パラメータをセットします
		InstockReceiveParameter sparam = new InstockReceiveParameter();

		// 作業者コード
		sparam.setWorkerCode(txt_WorkerCode.getText());
		// パスワード
		sparam.setPassword(txt_Password.getText());
		// 荷主コード
		sparam.setConsignorCode(txt_ConsignorCode.getText());
		// 荷主名称
		sparam.setConsignorName(txt_ConsignorName.getText());
		// 入荷予定日
		sparam.setPlanDate(WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
		// 仕入先コード
		sparam.setSupplierCode(txt_SupplierCode.getText());
		// 仕入先名称
		sparam.setSupplierName(txt_SupplierName.getText());
		// TD/DC区分
		if(rdo_CrossDCFlagCross.getChecked())
		{
			tcdcFlg = InstockReceiveParameter.TCDC_FLAG_CROSSTC;
			sparam.setTcdcFlag( tcdcFlg );
		}
		else if(rdo_CrossDCFlagDC.getChecked())
		{
			tcdcFlg = InstockReceiveParameter.TCDC_FLAG_DC;
			sparam.setTcdcFlag( tcdcFlg );
		}
		else
		{
			tcdcFlg = InstockReceiveParameter.TCDC_FLAG_TC;
			sparam.setTcdcFlag( tcdcFlg );
		}
		// 出荷先コード
		sparam.setCustomerCode(txt_CustomerCode.getText());
		// 出荷先名称
		sparam.setCustomerName(txt_CustomerName.getText());
		// 伝票No
		sparam.setInstockTicketNo(txt_TicketNo.getText());
		
		Connection conn = null;
		
		// スケジュールクラスのインスタンス生成をします
		WmsScheduler schedule = new InstockReceivePlanRegistSCH();
		
		try
		{
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールクラスで入力パラメーターチェックで問題がなければ
			// viewStateに画面パラメータをセットして入荷予定データ登録(詳細)へ遷移します
			if (schedule.nextCheck(conn, sparam))
			{
				// タイトル
				viewState.setString(TITLE_KEY, lbl_SettingName.getResourceKey());
				// 作業者ｺｰﾄﾞ
				viewState.setString(WORKERCODE, txt_WorkerCode.getText());
				// ﾊﾟｽﾜｰﾄﾞ
				viewState.setString(PASSWORD, txt_Password.getText());
				// 荷主ｺｰﾄﾞ
				viewState.setString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
				// 荷主名称
				viewState.setString(ListInstockReceiveConsignorBusiness.CONSIGNORNAME_KEY, txt_ConsignorName.getText());
				// 入荷予定日
				viewState.setString(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, txt_InstockPlanDate.getText());
				// 仕入先コード
				viewState.setString(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
				// 仕入先名称
				viewState.setString(ListInstockReceiveSupplierBusiness.SUPPLIERNAME_KEY, txt_SupplierName.getText());
				// ＴＣ／ＤＣ区分
				viewState.setString(TCDCFLG, tcdcFlg);
				// 出荷先コード
				viewState.setString(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
				// 出荷先名称
				viewState.setString(ListInstockReceiveCustomerBusiness.CUSTOMERNAME_KEY, txt_CustomerName.getText());
				// 伝票No
				viewState.setString(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY, txt_TicketNo.getText());
				
				// 基本情報設定画面->詳細情報登録画面
				forward(DO_REGIST2);
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
		// 作業者コード：パスワード以外の項目をデフォルトに戻します。
		 setInitView(false);
	}
	
	/** 
	 * 入荷予定検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入荷予定検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力 <BR>
	 *    <DIR>
	 *       荷主コード* <BR>
	 *       仕入先コード <BR>
	 *       入荷予定日 <BR>
	 *       TC/DC区分 <BR>
	 *       出荷先コード <BR>
	 *       伝票No. <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PInstkPlanSrch_Click(ActionEvent e) throws Exception
	{
		// 入荷予定検索画面へ検索条件をセットする
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
		// 処理中画面->結果画面
		redirect(DO_SRCH_INSTOCKPLAN, param, DO_SRCH_PROCESS);
	}

}
//end of class
