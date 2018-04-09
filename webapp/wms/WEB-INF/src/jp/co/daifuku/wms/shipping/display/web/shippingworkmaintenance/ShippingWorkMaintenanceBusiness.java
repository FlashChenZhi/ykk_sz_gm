// $Id: ShippingWorkMaintenanceBusiness.java,v 1.2 2006/11/10 00:38:44 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingworkmaintenance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.ListCellColumn;
import jp.co.daifuku.bluedog.util.Formatter;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingconsignor.ListShippingConsignorBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingcustomer.ListShippingCustomerBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingitem.ListShippingItemBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingplandate.ListShippingPlanDateBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingticket.ListShippingTicketBusiness;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;
import jp.co.daifuku.wms.shipping.schedule.ShippingWorkMaintenanceSCH;

/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * 出荷作業メンテナンス画面クラスです。<BR>
 * 出荷作業メンテナンス処理を行うスケジュールにパラメータを引き渡します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_Submit_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  画面から入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
 *  スケジュールから検索結果を受け取り、ためうちエリアに出力します。<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   作業者コード*<BR>
 *   パスワード*<BR>
 *   荷主コード*<BR>
 *   作業状態*<BR>
 *   出荷予定日*<BR>
 *   出荷先コード<BR>
 *   開始伝票No.<BR>
 *   終了伝票No.<BR>
 *   商品コード<BR>
 *  </DIR>
 * 
 *  [返却データ]<BR>
 *  <DIR>
 *   荷主コード<BR>
 *   荷主名称<BR>
 *   出荷予定日<BR>
 *   出荷先ｺｰﾄﾞ<BR>
 *   出荷先名称<BR>
 *   商品ｺｰﾄﾞ<BR>
 *   商品名称<BR>
 *   ｹｰｽ入数<BR>
 *   ﾎﾞｰﾙ入数<BR>
 *   作業予定ｹｰｽ数<BR>
 *   作業予定ﾋﾟｰｽ数<BR>
 *   出荷ｹｰｽ数<BR>
 *   出荷ﾋﾟｰｽ数<BR>
 *   状態<BR>
 *   賞味期限<BR>
 *   実績報告<BR>
 *   伝票No.<BR>
 *   行No.<BR>
 *   JobNo(HIDDEN)<BR>
 *   最終更新日時(HIDDEN)<BR>
 *  </DIR>
 * </DIR>
 * 
 * <BR>
 * 2.修正登録ボタン押下処理(<CODE>btn_Submit_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  ためうちエリアから入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールが出荷検品メンテナンスを行います。<BR>
 *  処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 *  スケジュールの結果、スケジュールから取得したメッセージを画面に出力、ためうちエリアの再表示します。<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   作業者コード*<BR>
 *   パスワード*<BR>
 *   出荷ｹｰｽ数<BR>
 *   出荷ﾋﾟｰｽ数<BR>
 *   状態*<BR>
 *   賞味期限<BR>
 *   ｹｰｽ入数<BR>
 *   押下ボタン種別<BR>
 *   ためうち行<BR>
 *   JobNo(HIDDEN)<BR>
 *   最終更新日時(HIDDEN)<BR>
 *   荷主コード(再検索用)<BR>
 *   出荷予定日(再検索用)<BR>
 *   作業状態(再検索用)<BR>
 *   出荷先コード(再検索用)<BR>
 *   開始伝票No.(再検索用)<BR>
 *   終了伝票No.(再検索用)<BR>
 *   商品コード(再検索用)<BR>
 *  </DIR>
 * 
 *  [返却データ]<BR>
 *  <DIR>
 *   荷主コード<BR>
 *   荷主名称<BR>
 *   出荷予定日<BR>
 *   出荷先ｺｰﾄﾞ<BR>
 *   出荷先名称<BR>
 *   商品ｺｰﾄﾞ<BR>
 *   商品名称<BR>
 *   ｹｰｽ入数<BR>
 *   ﾎﾞｰﾙ入数<BR>
 *   作業予定ｹｰｽ数<BR>
 *   作業予定ﾋﾟｰｽ数<BR>
 *   出荷ｹｰｽ数<BR>
 *   出荷ﾋﾟｰｽ数<BR>
 *   状態<BR>
 *   賞味期限<BR>
 *   実績報告<BR>
 *   伝票No.<BR>
 *   行No.<BR>
 *   JobNo(HIDDEN)<BR>
 *   最終更新日時(HIDDEN)<BR>
 *  </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/06</TD><TD>Y.Okamura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:38:44 $
 * @author  $Author: suresh $
 */
public class ShippingWorkMaintenanceBusiness extends ShippingWorkMaintenance implements WMSConstants
{
	// Class fields --------------------------------------------------
	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *  項目名[初期値]
	 *  <DIR>
	 *  作業者コード    [なし]<BR>
	 *  パスワード      [なし]<BR>
	 *  荷主コード      [該当荷主が1件しかない場合初期表示を行う]<BR>
	 *  作業状態        ["全て"]<BR>
	 *  出荷予定日      [なし]<BR>
	 *  出荷先コード    [なし]<BR>
	 *  開始伝票No.     [なし]<BR>
	 *  終了伝票No.     [なし]<BR>
	 *  商品コード      [なし]<BR>
	 *  修正登録ボタン		[使用不可]<BR>
	 *  一括作業中解除ボタン[使用不可]<BR>
	 *  一覧クリアボタン[使用不可]<BR>
	 *  カーソル        [作業者コード]
	 *  </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{		
		// 各入力フィールドに初期値をセットします。
		txt_WorkerCode.setText("");
		txt_Password.setText("");
		txt_ConsignorCode.setText(getConsignorCode());
		txt_ShippingPlanDate.setText("");
		txt_CustomerCode.setText("");
		txt_StartTicketNo.setText("");
		txt_EndTicketNo.setText("");
		txt_ItemCode.setText("");
		
		// 修正登録ボタン、一括作業中解除ボタン、一覧クリアボタンを使用不可にします
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);

		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
		
	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * 概要:このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *       1.カーソルを荷主コードにセットします。 <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
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
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
		// MSG-W0008=修正登録しますか？
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		// MSG-W0013=未作業状態にします。
		btn_AllWorkingClear.setBeforeConfirm("MSG-W0013");
		
		// MSG-W0012 = 一覧入力情報がクリアされます。宜しいですか？
		btn_ListClear.setBeforeConfirm("MSG-W0012");
		
		setFocus(txt_WorkerCode);
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが呼ばれます。<BR>
	 * <CODE>Page</CODE>クラスに定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorCode = param.getParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY);
		// 出荷予定日
		Date shippingPlanDate = WmsFormatter.toDate(param.getParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY)
											,this.getHttpRequest().getLocale());
		// 出荷先コード
		String customerCode = param.getParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY);
		// 開始伝票No.
		String startTicketNo = param.getParameter(ListShippingTicketBusiness.STARTTICKETNUMBER_KEY);
		// 終了伝票No.
		String endTicketNo = param.getParameter(ListShippingTicketBusiness.ENDTICKETNUMBER_KEY);
		// 商品コード
		String itemCode = param.getParameter(ListShippingItemBusiness.ITEMCODE_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		// 出荷予定日
		if (!StringUtil.isBlank(shippingPlanDate))
		{
			txt_ShippingPlanDate.setDate(shippingPlanDate);
		}
		// 出荷先コード
		if (!StringUtil.isBlank(customerCode))
		{
			txt_CustomerCode.setText(customerCode);
		}
		// 開始伝票No.
		if (!StringUtil.isBlank(startTicketNo))
		{
			txt_StartTicketNo.setText(startTicketNo);
		}
		// 終了伝票No.
		if (!StringUtil.isBlank(endTicketNo))
		{
			txt_EndTicketNo.setText(endTicketNo);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemCode))
		{
			txt_ItemCode.setText(itemCode);
		}

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。
	 * 異常があった場合は、メッセージをセットし、falseを返します。
	 * 
	 * @param rowNo チェックを行う行No.
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText(int rowNo)
	{
		
		WmsCheckker checker = new WmsCheckker();

		lst_SShpWkMtn.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_SShpWkMtn.getValue(7) ,
				rowNo,
				lst_SShpWkMtn.getListCellColumn(7).getResourceKey() )
				)
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	// Private methods -----------------------------------------------

	// Event handler methods -----------------------------------------
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SettingName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Help_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ToMenu_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * メニューボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：メニュー画面に遷移します。 
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ToMenu_Click(ActionEvent e) throws Exception
	{
		// メニュー画面に遷移します。
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
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 荷主検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で荷主検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		
		// 荷主コードをセット
		forwardParam.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		forwardParam.setParameter(ListShippingConsignorBusiness.SEARCHCONSIGNOR_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		
		// 荷主検索リストボックスを表示する。
		redirect("/shipping/listbox/listshippingconsignor/ListShippingConsignor.do", forwardParam, "/progress.do");
		
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatus_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatus_Change(ActionEvent e) throws Exception
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
	public void txt_ShippingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchShp_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷予定日検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で出荷予定日検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  作業状態* <BR>
	 *  出荷予定日<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchShp_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		
		// 荷主コードをセット
		forwardParam.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		// 作業状態
		String[] search = new String[2];

		if(pul_WorkStatus.getSelectedIndex() == 0)
		{
			// 全て
			search[0] = new String(ShippingParameter.WORKSTATUS_ALL);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 1)
		{
			// 未開始、一部完了
			search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
			search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 2)
		{
			// 作業中
			search[0] = new String(ShippingParameter.WORKSTATUS_NOWWORKING);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 3)
		{
			// 完了、一部完了
			search[0] = new String(ShippingParameter.WORKSTATUS_FINISH);
			search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		forwardParam.setParameter(ListShippingPlanDateBusiness.WORKSTATUSSHIPPINGPLANDATE_KEY, search);

		// 出荷予定日をセット
		forwardParam.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		
		// 出荷予定日検索リストボックスを表示する。
		redirect("/shipping/listbox/listshippingplandate/ListShippingPlanDate.do", forwardParam, "/progress.do");

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
	public void btn_PSearchCust_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷先検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で出荷先検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  作業状態* <BR>
	 *  出荷予定日<BR>
	 *  出荷先コード<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchCust_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		
		// 荷主コードをセット
		forwardParam.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		
		// 作業状態
		String[] search = new String[2];

		if(pul_WorkStatus.getSelectedIndex() == 0)
		{
			// 全て
			search[0] = new String(ShippingParameter.WORKSTATUS_ALL);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 1)
		{
			// 未開始、一部完了
			search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
			search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 2)
		{
			// 作業中
			search[0] = new String(ShippingParameter.WORKSTATUS_NOWWORKING);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 3)
		{
			// 完了、一部完了
			search[0] = new String(ShippingParameter.WORKSTATUS_FINISH);
			search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		forwardParam.setParameter(ListShippingCustomerBusiness.WORKSTATUSCUSTOMER_KEY, search);

		// 出荷予定日をセット
		forwardParam.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		
		// 出荷先コードをセット
		forwardParam.setParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		forwardParam.setParameter(ListShippingCustomerBusiness.SEARCHCUSTOMER_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		
		// 出荷先検索リストボックスを表示する。
		redirect("/shipping/listbox/listshippingcustomer/ListShippingCustomer.do", forwardParam, "/progress.do");

	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartTicketNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartTicketNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartTicketNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtTkt_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 開始伝票No.検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で伝票No.検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  作業状態* <BR>
	 *  出荷予定日<BR>
	 *  出荷先コード<BR>
	 *  開始伝票No.<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStrtTkt_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		
		// 荷主コードをセット
		forwardParam.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		// 作業状態
		String[] search = new String[2];

		if(pul_WorkStatus.getSelectedIndex() == 0)
		{
			// 全て
			search[0] = new String(ShippingParameter.WORKSTATUS_ALL);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 1)
		{
			// 未開始、一部完了
			search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
			search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 2)
		{
			// 作業中
			search[0] = new String(ShippingParameter.WORKSTATUS_NOWWORKING);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 3)
		{
			// 完了、一部完了
			search[0] = new String(ShippingParameter.WORKSTATUS_FINISH);
			search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		forwardParam.setParameter(ListShippingTicketBusiness.WORKSTATUSTICKETNUMBER_KEY, search);

		// 出荷予定日をセット
		forwardParam.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		// 出荷先コードをセット
		forwardParam.setParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		// 開始伝票No.をセット
		forwardParam.setParameter(ListShippingTicketBusiness.STARTTICKETNUMBER_KEY, txt_StartTicketNo.getText());
		forwardParam.setParameter(ListShippingTicketBusiness.SEARCHTICKETNUMBER_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		// 開始フラグをセット
		forwardParam.setParameter(ListShippingTicketBusiness.RANGETICKETNUMBER_KEY,ShippingParameter.RANGE_START);
		
		// 伝票No.検索リストボックスを表示する。
		redirect("/shipping/listbox/listshippingticket/ListShippingTicket.do", forwardParam, "/progress.do");

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
	public void lbl_EndTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndTicketNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndTicketNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndTicketNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdTkt_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 終了伝票No.検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で伝票No.検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  作業状態* <BR>
	 *  出荷予定日<BR>
	 *  出荷先コード<BR>
	 *  終了伝票No.<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEdTkt_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		
		// 荷主コードをセット
		forwardParam.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		// 作業状態
		String[] search = new String[2];

		if(pul_WorkStatus.getSelectedIndex() == 0)
		{
			// 全て
			search[0] = new String(ShippingParameter.WORKSTATUS_ALL);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 1)
		{
			// 未開始、一部完了
			search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
			search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 2)
		{
			// 作業中
			search[0] = new String(ShippingParameter.WORKSTATUS_NOWWORKING);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 3)
		{
			// 完了、一部完了
			search[0] = new String(ShippingParameter.WORKSTATUS_FINISH);
			search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		forwardParam.setParameter(ListShippingTicketBusiness.WORKSTATUSTICKETNUMBER_KEY, search);

		// 出荷予定日をセット
		forwardParam.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		// 出荷先コードをセット
		forwardParam.setParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		// 終了伝票No.をセット
		forwardParam.setParameter(ListShippingTicketBusiness.ENDTICKETNUMBER_KEY, txt_EndTicketNo.getText());
		forwardParam.setParameter(ListShippingTicketBusiness.SEARCHTICKETNUMBER_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		// 開始フラグをセット
		forwardParam.setParameter(ListShippingTicketBusiness.RANGETICKETNUMBER_KEY,ShippingParameter.RANGE_END);
		
		// 伝票No.検索リストボックスを表示する。
		redirect("/shipping/listbox/listshippingticket/ListShippingTicket.do", forwardParam, "/progress.do");

	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItem_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 商品検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で商品検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  作業状態* <BR>
	 *  出荷予定日<BR>
	 *  出荷先コード<BR>
	 *  開始伝票No.<BR>
	 *  終了伝票No.<BR>
	 *  商品コード<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchItem_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		
		// 荷主コードをセット
		forwardParam.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		// 作業状態
		String[] search = new String[2];

		if(pul_WorkStatus.getSelectedIndex() == 0)
		{
			// 全て
			search[0] = new String(ShippingParameter.WORKSTATUS_ALL);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 1)
		{
			// 未開始、一部完了
			search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
			search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 2)
		{
			// 作業中
			search[0] = new String(ShippingParameter.WORKSTATUS_NOWWORKING);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 3)
		{
			// 完了、一部完了
			search[0] = new String(ShippingParameter.WORKSTATUS_FINISH);
			search[1] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		forwardParam.setParameter(ListShippingItemBusiness.WORKSTATUSITEM_KEY, search);

		// 出荷予定日をセット
		forwardParam.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		// 出荷先コードをセット
		forwardParam.setParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		// 開始伝票No.をセット
		forwardParam.setParameter(ListShippingTicketBusiness.STARTTICKETNUMBER_KEY, txt_StartTicketNo.getText());
		// 終了伝票No.をセット
		forwardParam.setParameter(ListShippingTicketBusiness.ENDTICKETNUMBER_KEY, txt_EndTicketNo.getText());
		// 商品コードをセット
		forwardParam.setParameter(ListShippingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		forwardParam.setParameter(ListShippingItemBusiness.SEARCHITEM_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		
		// 商品検索リストボックスを表示する。
		redirect("/shipping/listbox/listshippingitem/ListShippingItem.do", forwardParam, "/progress.do");

	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 表示ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をパラメータにセットし、スケジュールを開始します。
	 * そのスケジュールの検索結果をためうちエリアに表示します。<BR>
	 * 本メソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 * 1.入力チェック（必須入力、文字数、文字属性）<BR>
	 * 2.入力チェック（開始伝票No.が終了伝票No.より小さいこと）<BR>
	 * 3.入力項目をパラメータにセットする。<BR>
	 * 4.スケジュールを開始しする。(検索を行う)<BR>
	 * 5.検索条件をViewStateに記憶する。<BR>
	 * 6.表示項目があればためうちエリアに表示する。<BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 * リストセルの列番号一覧
	 * <DIR>
	 * 0：HIDDEN項目<BR>
	 * 1：出荷先ｺｰﾄﾞ<BR>
	 * 2：商品ｺｰﾄﾞ<BR>
	 * 3：ｹｰｽ入数<BR>
	 * 4：作業予定ｹｰｽ数<BR>
	 * 5：出荷ｹｰｽ数<BR>
	 * 6：状態<BR>
	 * 7：賞味期限<BR>
	 * 8：実績報告<BR> 
	 * 9：伝票No.<BR>
	 * 10：出荷先名称<BR>
	 * 11：商品名称<BR>
	 * 12：ﾎﾞｰﾙ入数<BR>
	 * 13：作業予定ﾋﾟｰｽ数<BR>
	 * 14：出荷ﾋﾟｰｽ数<BR>
	 * 15：行No.<BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		// 修正登録ボタン、一括作業中解除ボタン、一覧クリアボタンを使用不可にします
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);
		
		// ためうちエリアを削除します。
		listCellClear();
			
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);

		// 入力チェックを行う（書式、必須、禁止文字）
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		pul_WorkStatus.validate();
		txt_ShippingPlanDate.validate();
		txt_CustomerCode.validate(false);
		txt_StartTicketNo.validate(false);
		txt_EndTicketNo.validate(false);
		txt_ItemCode.validate(false);
			
		// 伝票No.の大小チェックを行う
		if(!StringUtil.isBlank(txt_StartTicketNo.getText()) && !StringUtil.isBlank(txt_EndTicketNo.getText()))
		{
			if(txt_StartTicketNo.getText().compareTo(txt_EndTicketNo.getText()) > 0)
			{
				// 終了伝票No.の方が開始伝票No.より大きい場合エラーメッセージを表示し、終了する。
				// 6023037 = 開始伝票Noは、終了伝票Noより小さい値にしてください。
				message.setMsgResourceKey("6023037");
				return ;
			}
		}

		Connection conn = null;
		
		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールパラメータをセットする
			ShippingParameter param = new ShippingParameter();
			
			param.setWorkerCode(txt_WorkerCode.getText());
			param.setPassword(txt_Password.getText());
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 全て
			if(pul_WorkStatus.getSelectedIndex() == 0)
			{
				param.setStatusFlag(ShippingParameter.WORKSTATUS_ALL);
			}
			// 未開始
			else if(pul_WorkStatus.getSelectedIndex() == 1)
			{
				param.setStatusFlag(ShippingParameter.WORKSTATUS_UNSTARTING);
			}
			// 作業中
			else if(pul_WorkStatus.getSelectedIndex() == 2)
			{
				param.setStatusFlag(ShippingParameter.WORKSTATUS_NOWWORKING);
			}
			// 完了
			else if(pul_WorkStatus.getSelectedIndex() == 3)
			{
				param.setStatusFlag(ShippingParameter.WORKSTATUS_FINISH);
			}
			param.setPlanDate(WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
			param.setCustomerCode(txt_CustomerCode.getText());
			param.setFromTicketNo(txt_StartTicketNo.getText());
			param.setToTicketNo(txt_EndTicketNo.getText());
			param.setItemCode(txt_ItemCode.getText());
			
			// スケジュールを開始する
			WmsScheduler schedule = new ShippingWorkMaintenanceSCH();
			ShippingParameter[] viewParam = (ShippingParameter[])schedule.query(conn, param);
			
			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了する。
			if( viewParam == null || viewParam.length == 0 )
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			// ViewStateに検索値を保持します。
			setViewState(viewParam[0]);
			// ためうちエリアに検索結果を表示します。
			addList(viewParam);
			
			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());			
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				// コネクションのクローズを行う
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアをクリアします。<BR>
	 * <BR>
	 *  <DIR>
	 *  入力エリアの項目を初期値に戻します。ただし、作業者コードとパスワードはクリアしません。<BR>
	 *  初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 各入力フィールドに初期値をセットします。
		txt_ConsignorCode.setText(getConsignorCode());
		pul_WorkStatus.setSelectedIndex(0);
		txt_ShippingPlanDate.setText("");
		txt_CustomerCode.setText("");
		txt_StartTicketNo.setText("");
		txt_EndTicketNo.setText("");
		txt_ItemCode.setText("");
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);

	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ModifySubmit_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 修正登録ボタンが押下されたときに呼ばれます。<BR>
	 * 一括作業中解除ボタン押下時と処理がほぼ同じため、<CODE>updateList(String)</CODE>メソッドで処理を行います。<BR>
	 * 本メソッドでは<CODE>updateList(String)</CODE>メソッドを呼び出します。<BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ModifySubmit_Click(ActionEvent e) throws Exception
	{
		updateList(ShippingParameter.BUTTON_MODIFYSUBMIT);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllWorkingClear_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 一括作業中解除ボタンが押下されたときに呼ばれます。<BR>
	 * 修正登録ボタン押下時と処理がほぼ同じため、<CODE>updateList(String)</CODE>メソッドで処理を行います。<BR>
	 * 本メソッドでは<CODE>updateList(String)</CODE>メソッドを呼び出します。<BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_AllWorkingClear_Click(ActionEvent e) throws Exception
	{
		updateList(ShippingParameter.BUTTON_ALLWORKINGCLEAR);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 一覧クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：ためうちエリアの表示を全てクリアします。<BR>
	 * このメソッドは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 * 1.ためうちエリアの表示をクリアします。<BR>
	 * 2.カーソルを作業者コードにセットします。<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// ためうちエリアを削除します。
		listCellClear();
		
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
		
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SConsignor_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SShippingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRShippingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRShippingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRShippingPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpWkMtn_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpWkMtn_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpWkMtn_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpWkMtn_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpWkMtn_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpWkMtn_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpWkMtn_Click(ActionEvent e) throws Exception
	{
	}

	// private method -------------------------------------------------------
	/** 
	 * スケジュールから荷主コードを取得するためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主コードを返します。<BR>
	 * スケジュールは荷主コードが1件しかない場合該当荷主を返します。
	 * 荷主が複数件、または0件の場合はnullを返します。<BR>
	 * @return 荷主コード
	 * @throws Exception 全ての例外を報告します。
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null ;
		
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			ShippingParameter param = new ShippingParameter();

			// スケジュールから荷主コードを取得する。
			WmsScheduler schedule = new ShippingWorkMaintenanceSCH();
			param = (ShippingParameter)schedule.initFind(conn, param);
			
			if ( param != null )
			{
				return param.getConsignorCode();
			}
		}
		catch(Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				// コネクションのクローズを行う
				if(conn != null)
				{
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
		return null;	

	}

	/** 
	 * 検索条件をViewStateに保持するためのメソッドです。<BR>
	 * <BR>
	 * 概要：検索条件をViewStateに保持します。<BR>
	 * <BR>
	 * ViewStateのkey一覧
	 * <DIR>
	 * CONSITNOR_CODE：荷主コード<BR>
	 * CONSIGNOR_NAME：荷主名称<BR>
	 * WORK_STATUS：作業状態<BR>
	 * SHIPPING_PLAN_DATE：出荷予定日<BR>
	 * CUSTOMER_CODE：出荷先コード<BR>
	 * STR_TICKET_NO：開始伝票No.<BR>
	 * ED_TICKET_NO：終了伝票No.<BR>
	 * ITEM_CODE：商品コード<BR>
	 * </DIR>
	 * 
	 * @param param ためうちエリアに表示するための情報。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setViewState(ShippingParameter param) throws Exception
	{
		
		this.getViewState().setString("CONSIGNOR_CODE", txt_ConsignorCode.getText());
		this.getViewState().setString("CONSIGNOR_NAME", param.getConsignorName());
		if(pul_WorkStatus.getSelectedIndex() == 0)
		{
			// 全て
			this.getViewState().setString("WORK_STATUS", ShippingParameter.WORKSTATUS_ALL);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 1)
		{
			// 未開始
			this.getViewState().setString("WORK_STATUS", ShippingParameter.WORKSTATUS_UNSTARTING);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 2)
		{
			// 作業中
			this.getViewState().setString("WORK_STATUS", ShippingParameter.WORKSTATUS_NOWWORKING);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 3)
		{
			// 完了
			this.getViewState().setString("WORK_STATUS", ShippingParameter.WORKSTATUS_FINISH);
		}
		
		this.getViewState().setString("SHIPPING_PLAN_DATE", txt_ShippingPlanDate.getText());
		this.getViewState().setString("CUSTOMER_CODE", txt_CustomerCode.getText());
		this.getViewState().setString("STR_TICKET_NO", txt_StartTicketNo.getText());
		this.getViewState().setString("ED_TICKET_NO", txt_EndTicketNo.getText());
		this.getViewState().setString("ITEM_CODE", txt_ItemCode.getText());

	}

	/** 
	 * ためうちエリアに検索結果をマッピングするためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した検索結果をためうちエリアに表示するのに使用します。<BR>
	 * ためうちエリアに荷主コード、荷主名称、出荷予定日とリストセルを表示します。<BR>
	 * <BR>
	 * リストセルの列番号一覧
	 * <DIR>
	 * 0：HIDDEN項目<BR>
	 * 1：出荷先ｺｰﾄﾞ<BR>
	 * 2：商品ｺｰﾄﾞ<BR>
	 * 3：ｹｰｽ入数<BR>
	 * 4：作業予定ｹｰｽ数<BR>
	 * 5：出荷ｹｰｽ数<BR>
	 * 6：状態<BR>
	 * 7：賞味期限<BR>
	 * 8：実績報告<BR>
	 * 9：伝票No.<BR>
	 * 10：出荷先名称<BR>
	 * 11：商品名称<BR>
	 * 12：ﾎﾞｰﾙ入数<BR>
	 * 13：作業予定ﾋﾟｰｽ数<BR>
	 * 14：出荷ﾋﾟｰｽ数<BR>
	 * 15：行No.<BR>
	 * </DIR>
	 * 
	 * @param param ShippingParameter[] ためうちエリアに表示するための情報。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void addList(ShippingParameter[] param) throws Exception
	{
		ShippingParameter[] viewParam = param;
		
		// 荷主
		txt_SRConsignorCode.setText(this.getViewState().getString("CONSIGNOR_CODE"));
		txt_SRConsignorName.setText(this.getViewState().getString("CONSIGNOR_NAME"));
		// 出荷予定日
		txt_SRShippingPlanDate.setDate(WmsFormatter.toDate(this.getViewState().getString("SHIPPING_PLAN_DATE"),this.getHttpRequest().getLocale()));

		// 出荷先名称
		String label_customername = DisplayText.getText("LBL-W0036");
		// 商品名称
		String label_itemname = DisplayText.getText("LBL-W0103");
		// 実績報告
		String label_reportflag = DisplayText.getText("LBL-W0389");
		// 伝票No.
		String label_ticketno = DisplayText.getText("LBL-W0266");
		// 行No.
		String label_lineno = DisplayText.getText("LBL-W0109");
		// 作業者コード
		String label_workercode = DisplayText.getText("LBL-W0274");
		// 作業者名
		String label_workername = DisplayText.getText("LBL-W0276");

		// リストセルに値をセットする
		for(int i = 0; i < viewParam.length; i++)
		{
			// リストセルに行を追加する
			lst_SShpWkMtn.addRow();
			// 値をセットする行を選択する
			lst_SShpWkMtn.setCurrentRow(lst_SShpWkMtn.getMaxRows() - 1);
			
			// HIDDEN項目を連結する。
			String hidden = createHiddenList(viewParam[i]);
			
			// 検索結果を各セルにセットしていく
			lst_SShpWkMtn.setValue(0, hidden);
			lst_SShpWkMtn.setValue(1, viewParam[i].getCustomerCode());
			lst_SShpWkMtn.setValue(2, viewParam[i].getItemCode());
			lst_SShpWkMtn.setValue(3, Formatter.getNumFormat(viewParam[i].getEnteringQty()));
			lst_SShpWkMtn.setValue(4, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
			lst_SShpWkMtn.setValue(5, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
			if(viewParam[i].getStatusFlagL().equals(ShippingParameter.WORKSTATUS_S_UNSTARTING))
			{
				// 未開始
				lst_SShpWkMtn.setValue(6, "0");
			}
			else if(viewParam[i].getStatusFlagL().equals(ShippingParameter.WORKSTATUS_S_NOWWORKING))
			{
				// 作業中
				lst_SShpWkMtn.setValue(6, "1");
			}
			else if(viewParam[i].getStatusFlagL().equals(ShippingParameter.WORKSTATUS_S_FINISH))
			{
				// 完了
				lst_SShpWkMtn.setValue(6, "2");
			}
			lst_SShpWkMtn.setValue(7, viewParam[i].getUseByDate());
			
			lst_SShpWkMtn.setValue(8, viewParam[i].getReportFlagName());
			
			lst_SShpWkMtn.setValue(9, viewParam[i].getShippingTicketNo());
			lst_SShpWkMtn.setValue(10, viewParam[i].getCustomerName());
			lst_SShpWkMtn.setValue(11, viewParam[i].getItemName());
			lst_SShpWkMtn.setValue(12, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			lst_SShpWkMtn.setValue(13, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			lst_SShpWkMtn.setValue(14, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
			lst_SShpWkMtn.setValue(15, Integer.toString(viewParam[i].getShippingLineNo()));
			
			
			//tool tipをセット
			ToolTipHelper toolTip = new ToolTipHelper();
			// 出荷先名称
			toolTip.add(label_customername,viewParam[i].getCustomerName());
			// 商品名称
			toolTip.add(label_itemname,viewParam[i].getItemName());
			// 実績報告
			toolTip.add(label_reportflag, viewParam[i].getReportFlagName());
			// 伝票No.
			toolTip.add(label_ticketno,viewParam[i].getShippingTicketNo());
			// 行No.
			toolTip.add(label_lineno,viewParam[i].getShippingLineNo());
			// 作業者コード
			toolTip.add(label_workercode,viewParam[i].getWorkerCode());
			// 作業者名
			toolTip.add(label_workername,viewParam[i].getWorkerName());

				
			lst_SShpWkMtn.setToolTip(i+1, toolTip.getText());
			
			
				
		}
		// 修正登録ボタン、一括作業中解除ボタン、一覧クリアボタンを使用可能にします
		btn_ModifySubmit.setEnabled(true);
		btn_AllWorkingClear.setEnabled(true);
		btn_ListClear.setEnabled(true);
	}

	/**
	 * HIDDEN項目を１つの文字列に連結するためのメソッドです。<BR>
	 * <BR>
	 * HIDDENの項目順一覧
	 * <DIR>
	 * 0：出荷ｹｰｽ数<BR>
	 * 1：出荷ﾋﾟｰｽ数<BR>
	 * 2：状態<BR>
	 * 3：賞味期限<BR>
	 * 4：JobNo<BR>
	 * 5：最終更新日時<BR>
	 * </DIR>
	 * 
	 * @param viewParam ShippingParameter HIDDEN項目を含む<CODE>Parameter</CODE>クラス。
	 * @return 連結したHIDDEN項目
	 */
	private String createHiddenList(ShippingParameter viewParam)
	{
		String hidden = null;
		
		// HIDDEN項目文字列作成 
		ArrayList hiddenList = new ArrayList();
		
		hiddenList.add(0, Formatter.getNumFormat(viewParam.getResultCaseQty()));
		hiddenList.add(1, Formatter.getNumFormat(viewParam.getResultPieceQty()));
		if(viewParam.getStatusFlagL().equals(ShippingParameter.WORKSTATUS_S_UNSTARTING))
		{
			// 未開始
			hiddenList.add(2, "0");
		}
		else if(viewParam.getStatusFlagL().equals(ShippingParameter.WORKSTATUS_S_NOWWORKING))
		{
			// 作業中
			hiddenList.add(2, "1");
		}
		else if(viewParam.getStatusFlagL().equals(ShippingParameter.WORKSTATUS_S_FINISH))
		{
			// 完了
			hiddenList.add(2, "2");
		}
		hiddenList.add(3, viewParam.getUseByDate());
		hiddenList.add(4, viewParam.getJobNo());
		hiddenList.add(5, WmsFormatter.getTimeStampString(viewParam.getLastUpdateDate()));
		hidden = CollectionUtils.getConnectedString(hiddenList);
		
		return hidden;
	}
	
	/** 
	 * 修正登録ボタン、一括作業中解除ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：ためうちエリアの行のうち、更新対象となるデータのみを更新します。<BR>
	 * ためうちエリアに保持している更新に必要な情報をパラメータにセットし、スケジュールを開始します。<BR>
	 * スケジュールが正常に完了した場合、表示ボタン押下時と同じ条件の検索結果をためうちエリアに再表示します。
	 * 正常に完了しなかった場合はエラーメッセージをメッセージエリアに表示します。
	 * また、ためうちエリアのデータは更新されずそのまま表示します。<BR>
	 * また、トランザクションの確定は本メソッドで行います。<BR>
	 * また、<CODE>NumberFormatException</CODE>が起こった場合はそのままthorwし画面にエラーを表示します。<BR>
	 * <BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <DIR>
	 * 1.入力項目のチェック。（必須、文字種別、禁止文字）
	 * 2.更新対象データがあるかのチェック。
	 * 3.更新情報をパラメータにセットしスケジュールを開始。
	 * 4.トランザクションのコミット・ロールバック。
	 * 5.ためうちエリアの表示。
	 * </DIR>
	 * <BR>
	 * 更新パラメータ一覧<BR>
	 * <DIR>
	 * 荷主コード(再検索用)<BR>
	 * 出荷予定日(再検索用)<BR>
	 * 作業状態(再検索用)<BR>
	 * 出荷先コード(再検索用)<BR>
	 * 開始伝票No.(再検索用)<BR>
	 * 終了伝票No.(再検索用)<BR>
	 * 商品コード(再検索用)<BR>
	 * 出荷ｹｰｽ数<BR>
	 * 出荷ﾋﾟｰｽ数<BR>
	 * 状態<BR>
	 * 賞味期限<BR>
	 * ｹｰｽ入数<BR>
	 * 押下ボタン種別<BR>
	 * ためうち行<BR>
	 * JobNo<BR>
	 * 最終更新日時<BR>
	 * </DIR>
	 * 
	 * @param pButtonType String 押下されたボタンの種類
	 * @throws Exception 全ての例外を報告します。<BR>
	 */
	private void updateList(String pButtonType) throws Exception
	{
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);

		// 作業者コード、パスワードの入力チェックを行う。
		txt_WorkerCode.validate();
		txt_Password.validate();
		
		Connection conn = null;
			
		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			// リストセルのタイトルを取得し、ListCellColumnに格納
			ArrayList lst = (ArrayList) lst_SShpWkMtn.getListCellColumns();
			ListCellColumn[] List_Title = new ListCellColumn[lst.size() + 1];
			for (int l = 0; l < lst.size(); l++)
			{
				ListCellColumn listtemp = (ListCellColumn) lst.get(l);
				List_Title[l + 1] = new ListCellColumn();
				List_Title[l + 1] = (ListCellColumn) listtemp;
			}

			// ためうちの要素数分チェックをかけ、更新を行う行のみVectorに値をセットしていく。
			Vector vecParam = new Vector(lst_SShpWkMtn.getMaxRows());
			for(int i = 1; i < lst_SShpWkMtn.getMaxRows(); i++)
			{
				// 作業行を選択する
				lst_SShpWkMtn.setCurrentRow(i);
				
				// ためうちの入力チェックを行う。(出荷ｹｰｽ数、状態、賞味期限、出荷ﾋﾟｰｽ数)
				lst_SShpWkMtn.validate(5, false);
				lst_SShpWkMtn.validate(6, true);
				lst_SShpWkMtn.validate(7, false);
				lst_SShpWkMtn.validate(14, false);
				
				// eWareNavi用入力文字チェック
				if (!checkContainNgText(i))
				{
					return;
				}
	
				// 出荷ｹｰｽ数、出荷ﾋﾟｰｽ数のマイナス値のチェックを行う
				String itemname = null;
				itemname = checkNumber(WmsFormatter.getInt(lst_SShpWkMtn.getValue(5)), List_Title[5]);
				if (StringUtil.isBlank(itemname))
					itemname = checkNumber(WmsFormatter.getInt(lst_SShpWkMtn.getValue(14)), List_Title[14]);
				if (!StringUtil.isBlank(itemname))
				{
					itemname = Integer.toString(i) + " " + itemname;
					// エラーメッセージを表示し、終了する。
					// 6023162=No.{0}には{1}以上の値を入力してください。
					message.setMsgResourceKey("6023162" + wDelim + itemname + wDelim + "0");
					return;
				}

				// 更新対象ならば処理を行うため、Vectorに値をセットする
				if(isChangeData(pButtonType))
				{
					ShippingParameter param = new ShippingParameter();
						
					// 作業者コード、パスワードのセット
					param.setWorkerCode(txt_WorkerCode.getText());
					param.setPassword(txt_Password.getText());
					
					// 再表示用の検索条件のセット(荷主、予定日、状態、出荷先、伝票（範囲）、商品)
					param.setConsignorCode(this.getViewState().getString("CONSIGNOR_CODE"));
					Locale locale =  getHttpRequest().getLocale();
					String planDate = WmsFormatter.toParamDate(this.getViewState().getString("SHIPPING_PLAN_DATE"), locale);
					param.setPlanDate(planDate);
					param.setStatusFlag(this.getViewState().getString("WORK_STATUS"));
					param.setCustomerCode(this.getViewState().getString("CUSTOMER_CODE"));
					param.setFromTicketNo(this.getViewState().getString("STR_TICKET_NO"));
					param.setToTicketNo(this.getViewState().getString("ED_TICKET_NO"));
					param.setItemCode(this.getViewState().getString("ITEM_CODE"));
					
					// 更新値をセット(出荷ｹｰｽ数、出荷ﾋﾟｰｽ数、状態、賞味期限、ｹｰｽ入数、押下ボタン、ためうち行)					
					param.setResultCaseQty(Formatter.getInt(lst_SShpWkMtn.getValue(5)));
					param.setResultPieceQty(Formatter.getInt(lst_SShpWkMtn.getValue(14)));
					// 未開始
					if(lst_SShpWkMtn.getValue(6).equals("0"))
					{
						param.setStatusFlagL(ShippingParameter.WORKSTATUS_S_UNSTARTING);
					}
					// 作業中
					else if(lst_SShpWkMtn.getValue(6).equals("1"))
					{
						param.setStatusFlagL(ShippingParameter.WORKSTATUS_S_NOWWORKING);
					}
					// 完了
					else if(lst_SShpWkMtn.getValue(6).equals("2"))
					{
						param.setStatusFlagL(ShippingParameter.WORKSTATUS_S_FINISH);
					}
					param.setUseByDate(lst_SShpWkMtn.getValue(7));
					param.setEnteringQty(Formatter.getInt(lst_SShpWkMtn.getValue(3)));
					param.setButtonType(pButtonType);
					param.setRowNo(lst_SShpWkMtn.getCurrentRow());
					// 端末No.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					param.setTerminalNumber(userHandler.getTerminalNo());
					
					// HIDDEN項目セット(JobNo(4)、最終更新日時(5))
					String hidden = lst_SShpWkMtn.getValue(0);
					param.setJobNo(CollectionUtils.getString(4, hidden));
					param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(5, hidden)));
					
					vecParam.addElement(param);
				}
			}
				
			// 要素数0ならば"更新対象データはありません"。処理を終了
			if(vecParam.size() == 0)
			{
				message.setMsgResourceKey("6023154");
				return;
			}
				
			// パラメータに値をコピーする
			ShippingParameter[] paramArray = new ShippingParameter[vecParam.size()];
			vecParam.copyInto(paramArray);
				
			// スケジュールスタート
			WmsScheduler schedule = new ShippingWorkMaintenanceSCH();
			ShippingParameter[] viewParam = (ShippingParameter[])schedule.startSCHgetParams(conn, paramArray);
			
			// 返却データがnullならば更新に失敗。
			// ロールバックとメッセージのセットを行う。(ためうちは前データを表示したまま)
			if( viewParam == null )
			{
				// ロールバック
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
				
			// 返却データの要素数が0以上ならば更新に成功。
			// トランザクションの確定とためうちの再表示を行う。
			else if( viewParam.length >= 0 )
			{
				// コミット
				conn.commit();
				// スケジュールが正常に完了して表示データを取得した場合、表示する。
				listCellClear();
				addList(viewParam);

			}
				
			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());
				
		}
		catch(Exception ex)
		{
			// エラー発生時トランザクションをロールバックする。
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				// コネクションのクローズを行う
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch(SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	/** 
	 * ためうちエリアの入力情報が更新対象であるかを調べるためのメソッドです。<BR>
	 * <BR>
	 * 概要：押下されたボタンの種類によって、ためうちエリアの入力情報が更新対象であるかどうかを調べます。
	 * 更新対象ならばtrue、更新対象でなければfalseを返します。<BR>
	 * 
	 * @param pButtonType 押下されたボタンの種類
	 * @throws Exception 全ての例外を報告します。
	 */
	private boolean isChangeData(String pButtonType) throws Exception
	{
		// 更新登録ボタンが押下された場合
		if(pButtonType.equals(ShippingParameter.BUTTON_MODIFYSUBMIT))
		{
			// HIDDEN項目を取得する。
			String hidden = lst_SShpWkMtn.getValue(0);
			String orgCaseQty = CollectionUtils.getString(0, hidden);
			String orgPieceQty = CollectionUtils.getString(1, hidden);
			String orgStatus = CollectionUtils.getString(2, hidden);
			String orgUseByDate = CollectionUtils.getString(3, hidden);
			
			// 入力テキストがひとつも変更されていない場合処理を行わない。
			// 出荷ｹｰｽ数
			if(!lst_SShpWkMtn.getValue(5).equals(orgCaseQty))
			{
				return true;
			}
			// 状態
			else if(!lst_SShpWkMtn.getValue(6).equals(orgStatus))
			{
				return true;
			}
			// 賞味期限
			// 状態が完了以外は変更とみなさない
			else if(lst_SShpWkMtn.getValue(6).equals("2")
			 && !lst_SShpWkMtn.getValue(7).equals(orgUseByDate))
			{
				return true;
			}
			// 出荷ﾋﾟｰｽ数
			else if(!lst_SShpWkMtn.getValue(14).equals(orgPieceQty))
			{
				return true;
			}
			// 何も変更されていなかった場合
			else
			{
				return false;
			}
		}
		// 一括作業中解除が押下された場合
		else
		{
			// HIDDEN項目を取得する。
			String hidden = lst_SShpWkMtn.getValue(0);
			String orgStatus = CollectionUtils.getString(2, hidden);

			// 初期表示されたときの"状態"が未開始か完了ならば処理を行わない
			if(orgStatus.equals("0") || orgStatus.equals("2"))
			{
				return false;
			}
			else
			{
				return true;
			}
		}
	}

	/** 
	 * ためうちエリアをクリアするメソッドです。<BR>
	 * <BR>
	 * 概要：ためうちエリアのリストセル、読み取り専用項目をクリアし、ためうち部のボタン押下を不可にします。
	 * 更新対象ならばtrue、更新対象でなければfalseを返します。<BR>
	 * 
	 * @throws Exception 全ての例外を報告します。
	 */
	private void listCellClear() throws Exception
	{
		// ためうちエリアを削除します。
		// 読み取り専用項目を削除
		txt_SRConsignorCode.setText("");
		txt_SRConsignorName.setText("");
		txt_SRShippingPlanDate.setText("");
		
		// ためうちエリアを削除
		lst_SShpWkMtn.clearRow();
		
		// 修正登録ボタン、一括作業中解除ボタン、一覧クリアボタンを使用不可にします
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);
	}
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 値チェックを行うメソッドです。<BR>
	 * <BR>
	 * 値が0以上かをチェックします。<BR>
	 * 
	 * @param Num 	     値チェックするための値です。
	 * @param ListName   値チェックする項目名です。
	 * @return itemName  値が1以上じゃなかった場合項目名を返します。
	 * @throws Exception 全ての例外を報告します。
	 */
	private String checkNumber(int Num, ListCellColumn ListName) throws Exception
	{
		String itemName = null;

		// 0以上か
		if (!StringUtil.isBlank(Integer.toString(Num)))
		{
			if (Num < 0)
			{
				// itemNameに項目名をセット
				itemName = DisplayText.getText(ListName.getResourceKey());
				return itemName;
			}
		}
		return itemName;
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_Maintenance_Click(ActionEvent e) throws Exception
	{
	}


}
//end of class
