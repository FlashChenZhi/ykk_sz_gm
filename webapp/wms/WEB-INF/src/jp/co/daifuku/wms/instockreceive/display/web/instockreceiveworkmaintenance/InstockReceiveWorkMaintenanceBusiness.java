// $Id: InstockReceiveWorkMaintenanceBusiness.java,v 1.2 2006/11/10 00:32:34 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.instockreceiveworkmaintenance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveWorkMaintenanceSCH;
;

/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida <BR>
 * <BR>
 * クロス/DC入荷作業メンテナンス画面クラスです。<BR>
 * クロス/DC入荷作業メンテナンス処理を行うスケジュールにパラメータを引き渡します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_View_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 *  画面から入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
 *  スケジュールから検索結果を受け取り、ためうちエリアに出力します。<BR>
 *  <BR>
 *  [パラメータ] *必須入力 <BR>
 *  <DIR>
 *   	作業者コード* <BR>
 *   	パスワード* <BR>
 *   	荷主コード* <BR>
 *   	作業状態* <BR>
 *   	入荷予定日* <BR>
 *      仕入先コード <BR>
 *      開始伝票No. <BR>
 *      終了伝票No. <BR>
 *   	商品コード <BR>
 *   	クロス/DC* <BR>
 *  </DIR>
 * 
 *  [返却データ]<BR>
 *  <DIR>
 *   	荷主コード <BR>
 *   	荷主名称 <BR>
 *   	入荷予定日 <BR>
 *      仕入先コード <BR>
 *      仕入先名称 <BR>
 *   	商品コード <BR>
 *   	商品名称 <BR>
 *   	ケース入数 <BR>
 *   	ボール入数 <BR>
 *   	作業予定ケース数 <BR>
 *   	作業予定ピース数 <BR>
 *   	実績ケース数 <BR>
 *   	実績ピース数 <BR>
 *   	作業状態(リストセル) <BR>
 *   	賞味期限(result_use_by_date) <BR>
 *      クロス/DC <BR>
 *      入荷伝票No. <BR>
 *      入荷行No. <BR>
 *   	実績報告区分(未報告/報告済) <BR>
 *      実績報告区分名称 <BR>
 *   	作業者コード <BR>
 *   	作業者名 <BR>
 * 		<隠し項目><BR>
 *   	作業No. <BR>
 *   	最終更新日時 <BR>
 *  </DIR>
 * </DIR>
 * 
 * <BR>
 * 2.修正登録ボタン押下処理(<CODE>btn_ModifySubmit_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 *  ためうちエリアから入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールが入庫作業メンテナンスを行います。<BR>
 *  処理が正常に完了した場合はtrueを、条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 *  スケジュールの結果、スケジュールから取得したメッセージを画面に出力し、ためうちエリアの再表示をします。<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   	作業者コード* <BR>
 *   	パスワード* <BR>
 *   	荷主コード* <BR>
 *   	作業状態* <BR>
 *   	入荷予定日* <BR>
 *      仕入先コード <BR>
 *      開始伝票No. <BR>
 *      終了伝票No. <BR>
 *   	商品コード* <BR>
 *      クロス/DC* <BR>
 *   	ケース入数 <BR>
 *   	入荷ケース数 <BR>
 *   	入荷ピース数 <BR>
 *   	作業状態(リストセル) <BR>
 *   	賞味期限 <BR>
 *   	押下ボタンの種類* <BR>
 *   	端末No* <BR>
 *   	ためうち行No* <BR>
 * 		<隠し項目> <BR>
 *   	作業No.* <BR>
 *   	最終更新日時* <BR>
 *  </DIR>
 * 
 *  [返却データ]<BR>
 *  <DIR>
 *   	荷主コード <BR>
 *   	荷主名称 <BR>
 *   	入荷予定日 <BR>
 *      仕入先コード <BR>
 *      仕入先名称 <BR>
 *   	商品コード <BR>
 *   	商品名称 <BR>
 *   	ケース入数 <BR>
 *   	ボール入数 <BR>
 *   	作業予定ケース数 <BR>
 *   	作業予定ピース数 <BR>
 *   	実績ケース数 <BR>
 *   	実績ピース数 <BR>
 *   	作業状態(リストセル) <BR>
 *   	賞味期限(result_use_by_date) <BR>
 *      クロス/DC <BR>
 *      入荷伝票No. <BR>
 *      入荷行No. <BR>
 *   	実績報告区分(未報告/報告済) <BR>
 *      実績報告区分名称 <BR>
 *   	作業者コード <BR>
 *   	作業者名 <BR>
 * 		<隠し項目><BR>
 *   	作業No. <BR>
 *   	最終更新日時 <BR>
 *  </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/09</TD><TD>S.Yoshida</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:32:34 $
 * @author  $Author: suresh $
 */
public class InstockReceiveWorkMaintenanceBusiness extends InstockReceiveWorkMaintenance implements WMSConstants
{
    
    int resultQty_i = 0;
    boolean flag = false;
	// Class fields --------------------------------------------------
	/**
	 * ViewState用：作業者コード
	 */
	protected static final String WORKERCODE = "WORKER_CODE";
	
	/**
	 * ViewState用：パスワード
	 */
	protected static final String PASSWORD = "PASSWORD";
	
	/**
	 * ViewState用：荷主コード
	 */
	protected static final String CONSIGNORCODE = "CONSIGNOR_CODE";
	
	/**
	 * ViewState用：荷主名称
	 */
	protected static final String CONSIGNORNAME = "CONSIGNOR_NAME";
	
	/**
	 * ViewState用：作業状態
	 */
	protected static final String WORKSTATUS = "WORK_STATUS";
	
	/**
	 * ViewState用：入荷予定日
	 */
	protected static final String INSTOCKPLANDATE = "INSTOCK_PLAN_DATE";
	
	/**
	 * ViewState用：仕入先コード
	 */
	protected static final String SUPPLIERCODE = "SUPPLIER_CODE";
	
	/**
	 * ViewState用：開始伝票No.
	 */
	protected static final String STRTICKETNO = "STR_TICKET_NO";
	
	/**
	 * ViewState用：終了伝票No.
	 */
	protected static final String ENDTICKETNO = "END_TICKET_NO";
	
	/**
	 * ViewState用：商品コード
	 */
	protected static final String ITEMCODE = "ITEM_CODE";
	
	/**
	 * ViewState用：クロス/DC
	 */
	protected static final String TCDCFLAG = "TCDC_FLAG";
	
	/**
	 * HIDDEN項目用：0 = 入荷ケース数
	 */
	protected static final int HIDDEN0 = 0;
	
	/**
	 * HIDDEN項目用：1 = 入荷ピース数
	 */
	protected static final int HIDDEN1 = 1;
	
	/**
	 * HIDDEN項目用：2 = 状態
	 */
	protected static final int HIDDEN2 = 2;
	
	/**
	 * HIDDEN項目用：3 = 賞味期限
	 */
	protected static final int HIDDEN3 = 3;
	
	/**
	 * HIDDEN項目用：4 = 作業No. 
	 */
	protected static final int HIDDEN4 = 4;
	
	/**
	 * HIDDEN項目用：5 = 最終更新日時
	 */
	protected static final int HIDDEN5 = 5;
	
	/**
	 * HIDDEN項目用：6 = 入荷予定一意キー
	 */
	protected static final int HIDDEN6 = 6;
	
	/**
	 * 入力エリアプルダウン用：作業状態フラグ 全て = 0
	 */
	protected static final int STATUSFLAGALL = 0;
	
	/**
	 * 入力エリアプルダウン用：作業状態フラグ 未開始 = 1
	 */
	protected static final int STATUSFLAGUNSTARTED = 1;
	
	/**
	 * 入力エリアプルダウン用：作業状態フラグ 作業中 = 2
	 */
	protected static final int STATUSFLAGWORKING = 2;
	
	/**
	 * 入力エリアプルダウン用：作業状態フラグ 完了 = 3
	 */
	protected static final int STATUSFLAGCOMPLETION = 3;
	
	/**
	 * ためうちプルダウン用：作業状態フラグ 未開始 = 0
	 */
	protected static final String L_STATUSFLAGUNSTARTED = "0";
	
	/**
	 * ためうちプルダウン用：作業状態フラグ 作業中 = 1
	 */
	protected static final String L_STATUSFLAGWORKING = "1";
	
	/**
	 * ためうちプルダウン用：作業状態フラグ 完了 = 2
	 */
	protected static final String L_STATUSFLAGCOMPLETION = "2";
	
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
	 *  作業者コード		[なし] <BR>
	 *  パスワード      	[なし] <BR>
	 *  荷主コード      	[該当荷主が1件しかない場合初期表示を行う] <BR>
	 *  作業状態        	["全て"] <BR>
	 *  入荷予定日      	[なし] <BR>
	 *  仕入先コード		[なし] <BR>
	 *  開始伝票No.			[なし] <BR>
	 *  終了伝票No.			[なし] <BR>
	 *  商品コード      	[なし] <BR>
	 *  クロス/DC		    ["全て"] <BR>
	 *  修正登録ボタン		[使用不可] <BR>
	 *  一括作業中解除ボタン	[使用不可] <BR>
	 *  一覧クリアボタン		[使用不可] <BR>
	 *  カーソル        	[作業者コード]
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
		// 作業者コード
		txt_WorkerCode.setText("");
		// パスワード
		txt_Password.setText("");
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		// 作業状態プルダウン：全て
		pul_WorkStatus.setSelectedIndex(0);
		// 入荷予定日
		txt_InstockPlanDate.setText("");
		// 仕入先コード
		txt_SupplierCode.setText("");
		// 開始伝票No.
		txt_StartTicketNo.setText("");
		// 終了伝票No.
		txt_EndTicketNo.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// クロス/DCラジオボックス：全て
		rdo_CrossDCFlag_All.setChecked(true);
		rdo_CrossDCFlag_Cross.setChecked(false);
		rdo_CrossDCFlag_DC.setChecked(false);
		
		// 修正登録ボタン、一括作業中解除ボタン、一覧クリアボタンを使用不可にします。
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
	 *       1.ボタン押下前メッセージを出力します。 <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		
		// パラメータを取得
		String wMenuParam = this.getHttpRequest().getParameter(MENUPARAM);
		
		if (wMenuParam != null)
		{
			// パラメータを取得
			// 画面名称
			String wTitle = CollectionUtils.getMenuParam(0, wMenuParam);
			// ファンクションID
			String wFunctionID = CollectionUtils.getMenuParam(1, wMenuParam);
			// メニューID
			String wMenuID = CollectionUtils.getMenuParam(2, wMenuParam);
			
			// 取得したパラメータをViewStateへ保持する。
			this.getViewState().setString(M_TITLE_KEY, wTitle);
			this.getViewState().setString(M_FUNCTIONID_KEY, wFunctionID);
			this.getViewState().setString(M_MENUID_KEY, wMenuID);
			
			// 画面名称をセットする。
			lbl_SettingName.setResourceKey(wTitle);
		}
		
		// 修正登録ボタン押下時メッセージをセットする。
		// MSG-W0008 = 修正登録しますか？
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		// 一括作業中解除ボタン押下時メッセージをセットする。
		// MSG-W0013 = 未作業状態にします。
		btn_AllWorkingClear.setBeforeConfirm("MSG-W0013");
		
		// 一覧クリアボタン押下時メッセージをセットする。
		// MSG-W0012 = 一覧入力情報がクリアされます。宜しいですか？
		btn_ListClear.setBeforeConfirm("MSG-W0012");
		
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
		
		DialogParameters wParam = ((DialogEvent) e).getDialogParameters();
		
		// リストボックスから選択されたパラメータを取得。
		// 荷主コード
		String wConsignorCode = wParam.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 入荷予定日
		Date wInstockPlanDate = WmsFormatter.toDate(
		                        	wParam.getParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY), 
		                        	this.getHttpRequest().getLocale());
		// 仕入先コード
		String wSupplierCode = wParam.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// 開始伝票No.
		String wStrTicketNo = wParam.getParameter(ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY);
		// 終了伝票No.
		String wEndTicketNo = wParam.getParameter(ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY);
		// 商品コード
		String wItemCode = wParam.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);

		// 空ではないときに値をセットする。
		// 荷主コード
		if (!StringUtil.isBlank(wConsignorCode))
		{
			txt_ConsignorCode.setText(wConsignorCode);
		}
		// 入荷予定日
		if (!StringUtil.isBlank(wInstockPlanDate))
		{
			txt_InstockPlanDate.setDate(wInstockPlanDate);
		}
		// 仕入先コード
		if (!StringUtil.isBlank(wSupplierCode))
		{
			txt_SupplierCode.setText(wSupplierCode);
		}
		// 開始伝票No.
		if (!StringUtil.isBlank(wStrTicketNo))
		{
			txt_StartTicketNo.setText(wStrTicketNo);
		}
		// 終了伝票No.
		if (!StringUtil.isBlank(wEndTicketNo))
		{
			txt_EndTicketNo.setText(wEndTicketNo);
		}
		// 商品コード
		if (!StringUtil.isBlank(wItemCode))
		{
			txt_ItemCode.setText(wItemCode);
		}

		// フォーカスを作業者コードにセット
		setFocus(txt_WorkerCode);

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

		lst_SInstkRcvWkMtn.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_SInstkRcvWkMtn.getValue(7) ,
				rowNo,
				lst_SInstkRcvWkMtn.getListCellColumn(7).getResourceKey() )
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
	public void txt_Password_InputComplete(ActionEvent e) throws Exception
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
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
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
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();

		// 荷主コードをセット
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 検索フラグ：予定
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);

		// TCDC区分
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
		// 荷主検索リストボックスを表示する。
		redirect(
			"/instockreceive/listbox/listinstockreceiveconsignor/ListInstockReceiveConsignor.do",
			forwardParam, "/progress.do");
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
	 * 入荷予定日検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 *  概要：このメソッドは検索条件をパラメータにセットし、
	 *  その検索条件で入荷予定日検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 * 	作業状態<BR>
	 *  入荷予定日<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchInstockPlanDate_Click(ActionEvent e) throws Exception
	{
		
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		
		// 荷主コードをセット
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 作業状態をセット
		if (pul_WorkStatus.getSelectedIndex() != 0)
		{

			if (pul_WorkStatus.getSelectedIndex() == 1)
			{
				String[] wStatus = new String[2];
				// 未開始、一部完了
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
				wStatus[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(
				ListInstockReceivePlanDateBusiness.WORKSTATUSINSTOCKPLANDATE_KEY,
					wStatus);
			}
			else if (pul_WorkStatus.getSelectedIndex() == 2)
			{
				String[] wStatus = new String[1];
				// 作業中
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_WORKING);
				forwardParam.setParameter(
				ListInstockReceivePlanDateBusiness.WORKSTATUSINSTOCKPLANDATE_KEY,
					wStatus);
			}
			else if (pul_WorkStatus.getSelectedIndex() == 3)
			{
				String[] wStatus = new String[2];
				// 完了、一部完了
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
				wStatus[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(
				ListInstockReceivePlanDateBusiness.WORKSTATUSINSTOCKPLANDATE_KEY,
					wStatus);
			}
		}
					
		// 入荷予定日をセット
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
			
		// TCDC区分
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
		// 入荷予定日検索リストボックスを表示する。
		redirect(
			"/instockreceive/listbox/listinstockreceiveplandate/ListInstockReceivePlanDate.do",
			forwardParam, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SupplierCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchSupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕入先検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で商品検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 * 	作業状態<BR>
	 *  入荷予定日<BR>
	 *  仕入先コード<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchSupplierCode_Click(ActionEvent e) throws Exception
	{
		
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();

		// 荷主コードをセット
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 作業状態をセット
		if (pul_WorkStatus.getSelectedIndex() != 0)
		{

			if (pul_WorkStatus.getSelectedIndex() == 1)
			{
				String[] wStatus = new String[2];
				// 未開始、一部完了
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
				wStatus[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(
				ListInstockReceiveSupplierBusiness.WORKSTATUSSUPPLIER_KEY,
					wStatus);
			}
			else if (pul_WorkStatus.getSelectedIndex() == 2)
			{
				String[] wStatus = new String[1];
				// 作業中
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_WORKING);
				forwardParam.setParameter(
				ListInstockReceiveSupplierBusiness.WORKSTATUSSUPPLIER_KEY,
					wStatus);
			}
			else if (pul_WorkStatus.getSelectedIndex() == 3)
			{
				String[] wStatus = new String[2];
				// 完了、一部完了
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
				wStatus[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(
				ListInstockReceiveSupplierBusiness.WORKSTATUSSUPPLIER_KEY,
					wStatus);
			}
		}
		// 入荷予定日をセット
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
			
		// 仕入先コードをセット
		forwardParam.setParameter(
			ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
			txt_SupplierCode.getText());
			
		// 検索フラグ：予定
		forwardParam.setParameter(
			ListInstockReceiveSupplierBusiness.SEARCHSUPPLIER_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
								  
		// TCDC区分
		forwardParam.setParameter(
			ListInstockReceiveSupplierBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
		// 仕入先検索リストボックスを表示する。
		redirect(
			"/instockreceive/listbox/listinstockreceivesupplier/ListInstockReceiveSupplier.do",
			forwardParam, "/progress.do");
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
	public void btn_PSearchStartTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 開始伝票No.検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で商品検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 * 	作業状態<BR>
	 *  入荷予定日<BR>
	 *  仕入先コード<BR>
	 *  開始伝票No. <BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStartTicketNo_Click(ActionEvent e) throws Exception
	{
		
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
	
		// 荷主コードをセット
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 作業状態をセット
		if (pul_WorkStatus.getSelectedIndex() != 0)
		{
			
			if (pul_WorkStatus.getSelectedIndex() == 1)
			{
				String[] wStatus = new String[2];
				// 未開始、一部完了
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
				wStatus[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(
				ListInstockReceiveTicketBusiness.WORKSTATUSTICKETNUMBER_KEY,
					wStatus);
			}
			else if (pul_WorkStatus.getSelectedIndex() == 2)
			{
				String[] wStatus = new String[1];
				// 作業中
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_WORKING);
				forwardParam.setParameter(
				ListInstockReceiveTicketBusiness.WORKSTATUSTICKETNUMBER_KEY,
					wStatus);
			}
			else if (pul_WorkStatus.getSelectedIndex() == 3)
			{
				String[] wStatus = new String[2];
				// 完了、一部完了
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
				wStatus[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(
				ListInstockReceiveTicketBusiness.WORKSTATUSTICKETNUMBER_KEY,
					wStatus);
			}
		}
			
		// 入荷予定日をセット
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
			
		// 仕入先コードをセット
		forwardParam.setParameter(
			ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
			txt_SupplierCode.getText());
			
		// 開始伝票No.をセット
		forwardParam.setParameter(
			ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY,
			txt_StartTicketNo.getText());
		
		// 開始フラグ
		forwardParam.setParameter(
			ListInstockReceiveTicketBusiness.RANGETICKETNUMBER_KEY,
			InstockReceiveParameter.RANGE_START);
				
		// 検索フラグ：予定
		forwardParam.setParameter(
			ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
								  
		// TCDC区分
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
		// 伝票No.検索リストボックスを表示する。
		redirect(
			"/instockreceive/listbox/listinstockreceiveticket/ListInstockReceiveTicket.do",
			forwardParam, "/progress.do");
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
	public void btn_PSearchEndTicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 終了伝票No.検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で商品検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 * 	作業状態<BR>
	 *  入荷予定日<BR>
	 *  仕入先コード<BR>
	 *  終了伝票No. <BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEndTicketNo_Click(ActionEvent e) throws Exception
	{
		
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();

		// 荷主コードをセット
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 作業状態をセット
		if (pul_WorkStatus.getSelectedIndex() != 0)
		{

			if (pul_WorkStatus.getSelectedIndex() == 1)
			{
				String[] wStatus = new String[2];
				// 未開始、一部完了
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
				wStatus[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(
				ListInstockReceiveTicketBusiness.WORKSTATUSTICKETNUMBER_KEY,
					wStatus);
			}
			else if (pul_WorkStatus.getSelectedIndex() == 2)
			{
				String[] wStatus = new String[1];
				// 作業中
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_WORKING);
				forwardParam.setParameter(
				ListInstockReceiveTicketBusiness.WORKSTATUSTICKETNUMBER_KEY,
					wStatus);
			}
			else if (pul_WorkStatus.getSelectedIndex() == 3)
			{
				String[] wStatus = new String[2];
				// 完了、一部完了
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
				wStatus[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(
				ListInstockReceiveTicketBusiness.WORKSTATUSTICKETNUMBER_KEY,
					wStatus);
			}
		}
		// 仕入先コードをセット
		forwardParam.setParameter(
			ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
			txt_SupplierCode.getText());
			
		// 終了伝票No.をセット
		forwardParam.setParameter(
			ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY,
			txt_EndTicketNo.getText());
			
		// 終了フラグ
		forwardParam.setParameter(
			ListInstockReceiveTicketBusiness.RANGETICKETNUMBER_KEY,
			InstockReceiveParameter.RANGE_END);
			
		// 検索フラグ：予定
		forwardParam.setParameter(
			ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
								  
		// TCDC区分
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
		// 伝票No.検索リストボックスを表示する。
		redirect(
			"/instockreceive/listbox/listinstockreceiveticket/ListInstockReceiveTicket.do",
			forwardParam, "/progress.do");
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
	public void btn_PSearchItemCode_Server(ActionEvent e) throws Exception
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
	 * 	作業状態<BR>
	 *  入荷予定日<BR>
	 *  仕入先コード<BR>
	 *  開始伝票No. <BR>
	 *  終了伝票No. <BR>
	 *  商品コード <BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();

		// 荷主コードをセット
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 作業状態をセット
		if (pul_WorkStatus.getSelectedIndex() != 0)
		{

			if (pul_WorkStatus.getSelectedIndex() == 1)
			{
				String[] wStatus = new String[2];
				// 未開始、一部完了
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
				wStatus[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(
				ListInstockReceiveItemBusiness.WORKSTATUSITEM_KEY,
					wStatus);
			}
			else if (pul_WorkStatus.getSelectedIndex() == 2)
			{
				String[] wStatus = new String[1];
				// 作業中
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_WORKING);
				forwardParam.setParameter(
				ListInstockReceiveItemBusiness.WORKSTATUSITEM_KEY,
					wStatus);
			}
			else if (pul_WorkStatus.getSelectedIndex() == 3)
			{
				String[] wStatus = new String[2];
				// 完了、一部完了
				wStatus[0] = new String(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
				wStatus[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
				forwardParam.setParameter(
				ListInstockReceiveItemBusiness.WORKSTATUSITEM_KEY,
					wStatus);
			}
		}

			
		// 入荷予定日をセット
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
			
		// 仕入先コードをセット
		forwardParam.setParameter(
			ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
			txt_SupplierCode.getText());
			
		// 開始伝票No.をセット
		forwardParam.setParameter(
			ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY,
			txt_StartTicketNo.getText());
			
		// 終了伝票No.をセット
		forwardParam.setParameter(
			ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY,
			txt_EndTicketNo.getText());
			
		// 商品コードをセット
		forwardParam.setParameter(
			ListInstockReceiveItemBusiness.ITEMCODE_KEY,
			txt_ItemCode.getText());
			
		// 検索フラグ：予定
		forwardParam.setParameter(
			ListInstockReceiveItemBusiness.SEARCHITEM_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
								  
		// TCDC区分
		forwardParam.setParameter(
			ListInstockReceiveItemBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
		// 商品検索リストボックスを表示する。
		redirect(
			"/instockreceive/listbox/listinstockreceiveitem/ListInstockReceiveItem.do",
			forwardParam, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CrossDCTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_All_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_All_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_Cross_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_Cross_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_DC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_DC_Click(ActionEvent e) throws Exception
	{
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
	 *  概要：入力エリアの入力項目をパラメータにセットし、スケジュールを開始します。
	 *  そのスケジュールの検索結果をためうちエリアに表示します。<BR>
	 *  本メソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.入力チェック（必須入力、文字数、文字属性）<BR>
	 *   2.入力項目をパラメータにセットします。<BR>
	 *   3.スケジュールを開始します。(検索を行います)<BR>
	 *   4.検索条件をViewStateに記憶します。<BR>
	 *   5.表示項目があればためうちエリアに表示します。<BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 *   リストセルの列番号一覧
	 * <DIR>
	 *    0：HIDDEN項目 <BR>
	 *    1：仕入先コード <BR>
	 *    2：商品コード <BR>
	 *    3：ケース入数 <BR>
	 *    4：作業予定ケース数 <BR>
	 *    5：入荷ケース数 <BR>
	 *    6：状態 <BR>
	 *    7：賞味期限 <BR>
	 *    8：クロス/DC <BR>
	 *    9：実績報告 <BR>
	 *    10：伝票No. <BR>
	 *    11：仕入先名称 <BR>
	 *    12：商品名称 <BR>
	 *    13：ボール入数 <BR>
	 *    14：作業予定ピース数 <BR>
	 *    15：入荷ピース数 <BR>
	 *    16：行No. <BR>
	 * </DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		
		// コネクション
		Connection wConn = null;
		// 入荷パラメータのインスタンス生成
		InstockReceiveParameter wParam = new InstockReceiveParameter();
		// クロス/DC入荷作業メンテナンススケジュールのインスタンス生成
		WmsScheduler schedule = new InstockReceiveWorkMaintenanceSCH();
		// 入荷パラメータ配列
		InstockReceiveParameter[] viewParam = null;
		
		// ためうちエリアをクリアする
		listCellClear();
			
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);

		// 入力チェックを行う（書式、必須、禁止文字）
		// 作業者コード
		txt_WorkerCode.validate();
		// パスワード
		txt_Password.validate();
		// 荷主コード
		txt_ConsignorCode.validate();
		// 作業状態
		pul_WorkStatus.validate();
		// 入荷予定日
		txt_InstockPlanDate.validate();
		// 仕入先コード
		txt_SupplierCode.validate(false);
		// 開始伝票No.
		txt_StartTicketNo.validate(false);
		// 終了伝票No.
		txt_EndTicketNo.validate(false);
		// 商品コード
		txt_ItemCode.validate(false);
		
		// 伝票No.の大小チェックを行う
		if (!StringUtil.isBlank(txt_StartTicketNo.getText()) && !StringUtil.isBlank(txt_EndTicketNo.getText()))
		{
			if (txt_StartTicketNo.getText().compareTo(txt_EndTicketNo.getText()) > 0)
			{
				// 終了伝票No.の方が開始伝票No.より大きい場合エラーメッセージを表示し終了する。
				// 6023037 = 開始伝票Noは、終了伝票Noより小さい値にしてください。
				message.setMsgResourceKey("6023037");
				return ;
			}
		}
		
		try
		{
			// コネクションの取得
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールパラメータをセットする
			// 作業者コード
			wParam.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			wParam.setPassword(txt_Password.getText());
			// 荷主コード 
			wParam.setConsignorCode(txt_ConsignorCode.getText());
			// 作業状態
			if (pul_WorkStatus.getSelectedIndex() == STATUSFLAGALL)
			{
				// 全て
				wParam.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_ALL);
			}
			else if (pul_WorkStatus.getSelectedIndex() == STATUSFLAGUNSTARTED)
			{
				// 未開始
				wParam.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
			}
			else if (pul_WorkStatus.getSelectedIndex() == STATUSFLAGWORKING)
			{
				// 作業中
				wParam.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_WORKING);
			}
			else if (pul_WorkStatus.getSelectedIndex() == STATUSFLAGCOMPLETION)
			{
				// 完了
				wParam.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
			}
			// 入荷予定日
			wParam.setPlanDate(WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
			// 仕入先コード
			wParam.setSupplierCode(txt_SupplierCode.getText());
			// 開始伝票No.
			wParam.setFromTicketNo(txt_StartTicketNo.getText());
			// 終了伝票No.
			wParam.setToTicketNo(txt_EndTicketNo.getText());
			// 商品コード
			wParam.setItemCode(txt_ItemCode.getText());
			// クロス/DC
			if (rdo_CrossDCFlag_All.getChecked())
			{
				// 全て
				wParam.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_ALL);
			}
			else if (rdo_CrossDCFlag_Cross.getChecked())
			{
				// ケース
				wParam.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
			}
			else if (rdo_CrossDCFlag_DC.getChecked())
			{
				// ピース
				wParam.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
			}
			
			// スケジュールを開始する
			viewParam = (InstockReceiveParameter[]) schedule.query(wConn, wParam);
			
			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了する。
			if (viewParam == null || viewParam.length == 0)
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
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			try
			{
				if (wConn != null)
				{
					// コネクションのクローズを行う
					wConn.close();
				}
			}
			catch (SQLException se)
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
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		// 作業状態プルダウン：全て
		pul_WorkStatus.setSelectedIndex(0);
		// 入荷予定日
		txt_InstockPlanDate.setText("");
		// 仕入先コード
		txt_SupplierCode.setText("");
		// 開始伝票No.
		txt_StartTicketNo.setText("");
		// 終了伝票No.
		txt_EndTicketNo.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// クロス/DCラジオボックス：全て
		rdo_CrossDCFlag_All.setChecked(true);
		rdo_CrossDCFlag_Cross.setChecked(false);
		rdo_CrossDCFlag_DC.setChecked(false);

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
		updateList(InstockReceiveParameter.BUTTON_MODIFYSUBMIT);
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
		updateList(InstockReceiveParameter.BUTTON_ALLWORKINGCLEAR);		
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
		// ためうちエリアをクリアします。
		listCellClear();
		
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
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
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstockPlanDateT_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RInstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RInstockPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RInstockPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvWkMtn_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvWkMtn_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvWkMtn_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvWkMtn_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvWkMtn_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvWkMtn_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvWkMtn_Click(ActionEvent e) throws Exception
	{
	}


	// private method -------------------------------------------------------
	/** 
	 * スケジュールから荷主コードを取得するためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主コードを返します。<BR>
	 * スケジュールは荷主コードが1件しかない場合該当荷主を返します。
	 * 荷主が複数件、または0件の場合はnullを返します。<BR>
	 * 
	 * @throws Exception 全ての例外を報告します。
	 * 
	 * @return 荷主コード
	 */
	private String getConsignorCode() throws Exception
	{
		
		// コネクション
		Connection wConn = null;
		// 入荷パラメータのインスタンス生成
		InstockReceiveParameter wParam = new InstockReceiveParameter();
		// クロス/DC入荷作業メンテナンススケジュールのインスタンス生成
		WmsScheduler schedule = new InstockReceiveWorkMaintenanceSCH();
		
		try
		{
			// コネクション取得
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);
			// スケジュールより荷主コードを取得する。
			wParam = (InstockReceiveParameter) schedule.initFind(wConn, wParam);

			// 取得したパラメータがnullでない場合			
			if (wParam != null)
			{
				// 該当する荷主を返す
				return wParam.getConsignorCode();
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
				if (wConn != null)
				{
					// コネクションのクローズを行う
					wConn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
		return null;	
		
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
		
		// 読み取り専用項目を削除します。
		// 荷主コード
		txt_RConsignorCode.setText("");
		// 荷主名称
		txt_RConsignorName.setText("");
		// 入荷予定日
		txt_RInstockPlanDate.setText("");

		// ためうちエリアを削除
		lst_SInstkRcvWkMtn.clearRow();
		
		// 修正登録ボタン、一括作業中解除ボタン、一覧クリアボタンを使用不可にします
		btn_ModifySubmit.setEnabled(false);
		btn_AllWorkingClear.setEnabled(false);
		btn_ListClear.setEnabled(false);
		
	}
	
	/** 
	 * 検索条件をViewStateに保持するためのメソッドです。<BR>
	 * <BR>
	 *  概要：検索条件をViewStateに保持します。<BR>
	 * <BR>
	 *   ViewStateのkey一覧
	 * <DIR>
	 *    WORKER_CODE		：作業者コード <BR>
	 *    PASSWORD			：パスワード <BR>
	 *    CONSITNOR_CODE	：荷主コード <BR>
	 *    CONSITNOR_NAME	：荷主名称 <BR>
	 *    WORK_STATUS		：作業状態 <BR>
	 *    INSTOCK_PLAN_DATE	：入荷予定日 <BR>
	 *    SUPPLIER_CODE		：仕入先コード <BR>
	 *    STR_TICKET_NO		：開始伝票No. <BR>
	 *    END_TICKET_NO		：終了伝票No. <BR>
	 *    ITEM_CODE			：商品コード <BR>
	 *    TCDC_FLAG			：クロス/DC <BR>
	 * </DIR>
	 * 
	 * @param param InstockReceiveParameter ためうちエリアに表示するための情報。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setViewState(InstockReceiveParameter param) throws Exception
	{
		
		// 作業者コード
		this.getViewState().setString(WORKERCODE, txt_WorkerCode.getText());
		// パスワード
		this.getViewState().setString(PASSWORD, txt_Password.getText());
		// 荷主コード
		this.getViewState().setString(CONSIGNORCODE, txt_ConsignorCode.getText());
		// 荷主名称
		this.getViewState().setString(CONSIGNORNAME, param.getConsignorName());
		// 作業状態
		if (pul_WorkStatus.getSelectedIndex() == STATUSFLAGALL)
		{
			// 全て
			this.getViewState().setString(WORKSTATUS, InstockReceiveParameter.STATUS_FLAG_ALL);
		}
		else if (pul_WorkStatus.getSelectedIndex() == STATUSFLAGUNSTARTED)
		{
			// 未開始
			this.getViewState().setString(WORKSTATUS, InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		}
		else if (pul_WorkStatus.getSelectedIndex() == STATUSFLAGWORKING)
		{
			// 作業中
			this.getViewState().setString(WORKSTATUS, InstockReceiveParameter.STATUS_FLAG_WORKING);
		}
		else if (pul_WorkStatus.getSelectedIndex() == STATUSFLAGCOMPLETION)
		{
			// 完了
			this.getViewState().setString(WORKSTATUS, InstockReceiveParameter.STATUS_FLAG_COMPLETION);
		}
		// 入荷予定日
		this.getViewState().setString(INSTOCKPLANDATE, txt_InstockPlanDate.getText());
		// 仕入先コード
		this.getViewState().setString(SUPPLIERCODE, txt_SupplierCode.getText());
		// 開始伝票No.
		this.getViewState().setString(STRTICKETNO, txt_StartTicketNo.getText());
		// 終了伝票No.
		this.getViewState().setString(ENDTICKETNO, txt_EndTicketNo.getText());
		// 商品コード
		this.getViewState().setString(ITEMCODE, txt_ItemCode.getText());
		// クロス/DC
		if (rdo_CrossDCFlag_All.getChecked())
		{
			// 全て
			this.getViewState().setString(TCDCFLAG, InstockReceiveParameter.TCDC_FLAG_ALL);
		}
		else if (rdo_CrossDCFlag_Cross.getChecked())
		{
			// クロス
			this.getViewState().setString(TCDCFLAG, InstockReceiveParameter.TCDC_FLAG_CROSSTC);
		}
		else if (rdo_CrossDCFlag_DC.getChecked())
		{
			// DC
			this.getViewState().setString(TCDCFLAG, InstockReceiveParameter.TCDC_FLAG_DC);
		}		
	}

	/** 
	 * ためうちエリアに検索結果をマッピングするためのメソッドです。<BR>
	 * <BR>
	 *  概要：スケジュールから取得した検索結果をためうちエリアに表示するのに使用します。<BR>
	 *        ためうちエリアに荷主コード、荷主名称、入荷予定日とリストセルを表示します。<BR>
	 * <BR>
	 *   リストセルの列番号一覧
	 * <DIR>
	 *    0：HIDDEN項目 <BR>
	 * 	<DIR>
	 * 		0：入荷ケース数 <BR>
	 * 		1：入荷ピース数 <BR>
	 * 		2：状態 <BR>
	 * 		3：賞味期限 <BR>
	 * 		4：作業No. <BR>
	 * 		5：最終更新日時 <BR>
	 * 	</DIR>
	 *    1：仕入先コード <BR>
	 *    2：商品コード <BR>
	 *    3：ケース入数 <BR>
	 *    4：作業予定ケース数 <BR>
	 *    5：入荷ケース数 <BR>
	 *    6：状態 <BR>
	 *    7：賞味期限 <BR>
	 *    8：クロス/DC <BR>
	 *    9：実績報告 <BR>
	 *    10：伝票No. <BR>
	 *    11：仕入先名称 <BR>
	 *    12：商品名称 <BR>
	 *    13：ボール入数 <BR>
	 *    14：作業予定ピース数 <BR>
	 *    15：入荷ピース数 <BR>
	 *    16：行No.
	 * </DIR>
	 * 
	 * @param param InstockReceiveParameter[] ためうちエリアに表示するための情報。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void addList(InstockReceiveParameter[] param) throws Exception
	{
		
		// 入荷パラメータ配列
		InstockReceiveParameter[] viewParam = param;
		
		// 荷主コード
		txt_RConsignorCode.setText(this.getViewState().getString(CONSIGNORCODE));
		// 荷主名称
		txt_RConsignorName.setText(this.getViewState().getString(CONSIGNORNAME));
		// 入荷予定日
		txt_RInstockPlanDate.setDate(WmsFormatter.toDate
			(this.getViewState().getString(INSTOCKPLANDATE),this.getHttpRequest().getLocale()));

		// 仕入先名称
		String label_suppliername = DisplayText.getText("LBL-W0253");
		// 商品名称
		String label_itemname = DisplayText.getText("LBL-W0103");
		// 賞味期限
		String label_usebydate = DisplayText.getText("LBL-W0270");
		// クロス/DC
		String label_crossdc = DisplayText.getText("LBL-W0028");
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
		for (int i = 0; i < viewParam.length; i++)
		{
			// リストセルに行を追加する。
			lst_SInstkRcvWkMtn.addRow();
			// 値をセットする行を選択する。
			lst_SInstkRcvWkMtn.setCurrentRow(lst_SInstkRcvWkMtn.getMaxRows() - 1);
			
			// 検索結果を各セルにセットしていく
			// HIDDEN項目
			lst_SInstkRcvWkMtn.setValue(0, createHiddenList(viewParam[i]));
			// 仕入先コード
			lst_SInstkRcvWkMtn.setValue(1, viewParam[i].getSupplierCode());
			// 商品コード
			lst_SInstkRcvWkMtn.setValue(2, viewParam[i].getItemCode());
			// ケース入数
			lst_SInstkRcvWkMtn.setValue(3, Formatter.getNumFormat(viewParam[i].getEnteringQty()));
			// 作業予定ケース数
			lst_SInstkRcvWkMtn.setValue(4, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
			// 入荷ケース数
			lst_SInstkRcvWkMtn.setValue(5, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
			// 状態
			if (viewParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
			{
				// 未開始
				lst_SInstkRcvWkMtn.setValue(6, L_STATUSFLAGUNSTARTED);
			}
			else if (viewParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
			{
				// 作業中
				lst_SInstkRcvWkMtn.setValue(6, L_STATUSFLAGWORKING);
			}
			else if (viewParam[i].getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
			{
				// 完了
				lst_SInstkRcvWkMtn.setValue(6, L_STATUSFLAGCOMPLETION);
			}
			// 賞味期限
			lst_SInstkRcvWkMtn.setValue(7, viewParam[i].getUseByDate());
			// クロス/DC
			lst_SInstkRcvWkMtn.setValue(8, viewParam[i].getTcdcName());
			// 実績報告
			lst_SInstkRcvWkMtn.setValue(9, viewParam[i].getReportFlagName());
			// 伝票No.
			lst_SInstkRcvWkMtn.setValue(10, viewParam[i].getInstockTicketNo());
			// 仕入先名称
			lst_SInstkRcvWkMtn.setValue(11, viewParam[i].getSupplierName());
			// 商品名称
			lst_SInstkRcvWkMtn.setValue(12, viewParam[i].getItemName());
			// ボール入数
			lst_SInstkRcvWkMtn.setValue(13, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			// 作業予定ピース数
			lst_SInstkRcvWkMtn.setValue(14, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			// 入荷ピース数
			lst_SInstkRcvWkMtn.setValue(15, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
			// 行No.
			lst_SInstkRcvWkMtn.setValue(16, String.valueOf(viewParam[i].getInstockLineNo()));
			
			// ToolTipのインスタンス生成
			ToolTipHelper toolTip = new ToolTipHelper();
			// バルーンをセット
			// 仕入先名称
			toolTip.add(label_suppliername, viewParam[i].getSupplierName());
			// 商品名称
			toolTip.add(label_itemname, viewParam[i].getItemName());
			// 賞味期限
			toolTip.add(label_usebydate, viewParam[i].getUseByDate());
			// クロス/DC
			toolTip.add(label_crossdc, viewParam[i].getTcdcName());
			// 実績報告
			toolTip.add(label_reportflag, viewParam[i].getReportFlagName());
			// 伝票No.
			toolTip.add(label_ticketno, viewParam[i].getInstockTicketNo());
			// 行No.
			toolTip.add(label_lineno, viewParam[i].getInstockLineNo());
			// 作業者コード
			toolTip.add(label_workercode, viewParam[i].getWorkerCode());
			// 作業者名
			toolTip.add(label_workername, viewParam[i].getWorkerName());
			
			lst_SInstkRcvWkMtn.setToolTip(lst_SInstkRcvWkMtn.getCurrentRow(), toolTip.getText());
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
	 * 		0：入荷ケース数<BR>
	 * 		1：入荷ピース数<BR>
	 * 		2：状態<BR>
	 * 		3：賞味期限<BR>
	 * 		4：作業No. <BR>
	 * 		5：最終更新日時<BR>
	 * 		6：入荷予定一意キー<BR>
	 * </DIR>
	 * 
	 * @param viewParam InstockReceiveParameter HIDDEN項目を含む<CODE>Parameter</CODE>クラス。
	 * @return 連結したHIDDEN項目
	 */
	private String createHiddenList(InstockReceiveParameter viewParam)
	{
		
		// Listクラスのインスタンス生成
		ArrayList wList = new ArrayList();
		
		// HIDDEN項目文字列作成 
		// 入荷ケース数
		wList.add(HIDDEN0, Formatter.getNumFormat(viewParam.getResultCaseQty()));
		// 入荷ピース数
		wList.add(HIDDEN1, Formatter.getNumFormat(viewParam.getResultPieceQty()));
		// 状態
		if (viewParam.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_UNSTARTED))
		{
			// 未開始
			wList.add(HIDDEN2, L_STATUSFLAGUNSTARTED);
		}
		else if (viewParam.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_WORKING))
		{
			// 作業中
			wList.add(HIDDEN2, L_STATUSFLAGWORKING);
		}
		else if (viewParam.getStatusFlagL().equals(InstockReceiveParameter.STATUS_FLAG_COMPLETION))
		{
			// 完了
			wList.add(HIDDEN2, L_STATUSFLAGCOMPLETION);
		}
		// 賞味期限
		wList.add(HIDDEN3, viewParam.getUseByDate());
		// 作業No.
		wList.add(HIDDEN4, viewParam.getJobNo());
		// 最終更新日時
		wList.add(HIDDEN5, WmsFormatter.getTimeStampString(viewParam.getLastUpdateDate()));	
		// 入荷予定一意キー
		wList.add(HIDDEN6, viewParam.getInstockPlanUkey());
		return CollectionUtils.getConnectedString(wList);
		
	}
	
	/** 
	 * 修正登録ボタン、一括作業中解除ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 *  概要：ためうちエリアの行のうち、更新対象となるデータのみを更新します。<BR>
	 *  ためうちエリアに保持している更新に必要な情報をパラメータにセットし、スケジュールを開始します。<BR>
	 *  スケジュールが正常に完了した場合、表示ボタン押下時と同じ条件の検索結果をためうちエリアに再表示します。<BR>
	 *  正常に完了しなかった場合はエラーメッセージをメッセージエリアに表示します。<BR>
	 *  また、ためうちエリアのデータは更新されずそのまま表示します。<BR>
	 *  また、トランザクションの確定は本メソッドで行います。<BR>
	 *  また、<CODE>NumberFormatException</CODE>が起こった場合はそのままthrowし画面にエラーを表示します。<BR>
	 * <BR>
	 *  このメソッドでは以下の処理を行います。<BR>
	 * <DIR>
	 *  1.入力項目のチェック。（必須、文字種別、禁止文字）<BR>
	 *  2.更新対象データがあるかのチェック。<BR>
	 *  3.更新情報をパラメータにセットしスケジュールを開始。<BR>
	 *  4.トランザクションのコミット・ロールバック。<BR>
	 *  5.ためうちエリアの表示。<BR>
	 * </DIR>
	 * <BR>
	 *  更新パラメータ一覧(* 必須入力) <BR>
	 * <DIR>
	 *   作業者コード* <BR>
	 *   パスワード* <BR>
	 *   荷主コード(再検索用)* <BR>
	 *   作業状態(再検索用)* <BR>
	 *   入荷予定日(再検索用)* <BR>
	 *   仕入先コード(再検索用) <BR>
	 *   開始伝票No.(再検索用) <BR>
	 *   終了伝票No.(再検索用) <BR>
	 *   商品コード(再検索用) <BR>
	 *   クロス/DC(再検索用)* <BR>
	 *   押下ボタンの種類* <BR>
	 *   ためうち行No* <BR>
	 *   端末No* <BR>
	 *   ケース入数 <BR>
	 *   入荷ケース数 <BR>
	 *   入荷ピース数 <BR>
	 *   状態(リストセル) <BR>
	 *   賞味期限 <BR>
	 *   <隠し項目> <BR>
	 *   作業No.* <BR>
	 *   最終更新日時* <BR>
	 * </DIR>
	 * 
	 * @param pButtonType String 押下されたボタンの種類
	 * @throws Exception 全ての例外を報告します。<BR>
	 */
	private void updateList(String pButtonType) throws Exception
	{
	    
	    
		// コネクション
		Connection wConn = null;
		// Vectorインスタンス生成
		Vector vecParam = new Vector(lst_SInstkRcvWkMtn.getMaxRows());
		// 入荷パラメータ配列
		InstockReceiveParameter[] paramArray = null;
		// クロス/DC入荷作業メンテナンススケジュールのインスタンス生成
		WmsScheduler schedule = new InstockReceiveWorkMaintenanceSCH();
		
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);

		// 作業者コード、パスワードの入力チェックを行う。
		txt_WorkerCode.validate();
		txt_Password.validate();
			
		try
		{
			// コネクションの取得
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			// ためうちの要素数分チェックをかけ、更新を行う行のみVectorに値をセットしていく。
			for (int i = 1; i < lst_SInstkRcvWkMtn.getMaxRows(); i++)
			{
				// 作業行を選択する
				lst_SInstkRcvWkMtn.setCurrentRow(i);
		
				// ためうちの入力チェックを行う。
				// 入荷ケース数
				lst_SInstkRcvWkMtn.validate(5, false);
				// 状態
				lst_SInstkRcvWkMtn.validate(6, true);
				// 賞味期限
				lst_SInstkRcvWkMtn.validate(7, false);
				// 入荷ピース数
				lst_SInstkRcvWkMtn.validate(15, false);

				// eWareNavi用入力文字チェック
				if (!checkContainNgText(i))
				{
					return;
				}

				// 更新対象ならば処理を行うため、Vectorに値をセットする
				if (isChangeData(pButtonType))
				{
					InstockReceiveParameter wParam = new InstockReceiveParameter();
						
					// 作業者コード
					wParam.setWorkerCode(txt_WorkerCode.getText());
					// パスワード
					wParam.setPassword(txt_Password.getText());
					
					// 再検索値をセットする。
					// 荷主コード
					wParam.setConsignorCode(this.getViewState().getString(CONSIGNORCODE));
					// 作業状態
					wParam.setStatusFlag(this.getViewState().getString(WORKSTATUS));
					// 入荷予定日
					wParam.setPlanDate(WmsFormatter.toParamDate(this.getViewState().getString
							           (INSTOCKPLANDATE), this.getHttpRequest().getLocale()));
				    // 仕入先コード
				    wParam.setSupplierCode(this.getViewState().getString(SUPPLIERCODE));
				    // 開始伝票No.
				    wParam.setFromTicketNo(this.getViewState().getString(STRTICKETNO));
				    // 終了伝票No.
				    wParam.setToTicketNo(this.getViewState().getString(ENDTICKETNO));
					// 商品コード
					wParam.setItemCode(this.getViewState().getString(ITEMCODE));
					
					// クロス/DC
					wParam.setTcdcFlag(this.getViewState().getString(TCDCFLAG));
					
					// 更新値をセットする。
					// 入荷ケース数	
					wParam.setResultCaseQty(Formatter.getInt(lst_SInstkRcvWkMtn.getValue(5)));
					// 入荷ピース数
					wParam.setResultPieceQty(Formatter.getInt(lst_SInstkRcvWkMtn.getValue(15)));
					// 状態
					if(lst_SInstkRcvWkMtn.getValue(6).equals(L_STATUSFLAGUNSTARTED))
					{
						// 未開始
						wParam.setStatusFlagL(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
					}
					else if(lst_SInstkRcvWkMtn.getValue(6).equals(L_STATUSFLAGWORKING))
					{
						// 作業中
						wParam.setStatusFlagL(InstockReceiveParameter.STATUS_FLAG_WORKING);
					}
					else if(lst_SInstkRcvWkMtn.getValue(6).equals(L_STATUSFLAGCOMPLETION))
					{
						// 完了
						wParam.setStatusFlagL(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
					}
					// 賞味期限
					wParam.setUseByDate(lst_SInstkRcvWkMtn.getValue(7));
					// ケース入数
					wParam.setEnteringQty(Formatter.getInt(lst_SInstkRcvWkMtn.getValue(3)));
					// 押下ボタン
					wParam.setButtonType(pButtonType);
					// ためうち行
					wParam.setRowNo(lst_SInstkRcvWkMtn.getCurrentRow());
					// 端末No.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					wParam.setTerminalNumber(userHandler.getTerminalNo());
					// 作業No.
					wParam.setJobNo(CollectionUtils.getString(HIDDEN4, lst_SInstkRcvWkMtn.getValue(0)));
					// 最終更新日時
					wParam.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(HIDDEN5, lst_SInstkRcvWkMtn.getValue(0))));
					
					// 入荷予定一意キー					
					wParam.setInstockPlanUkey(CollectionUtils.getString(HIDDEN6,lst_SInstkRcvWkMtn.getValue(0)));
					
					vecParam.addElement(wParam);
				}
			}
				
			// 要素数0の場合、処理終了
			if (vecParam.size() == 0)
			{
				// 6023154 = 更新対象データがありません。
				message.setMsgResourceKey("6023154");
				return;
			}
			
			// パラメータに値をコピーする
			paramArray = new InstockReceiveParameter[vecParam.size()];
			vecParam.copyInto(paramArray);
				
			// スケジュールスタート
			InstockReceiveParameter[] viewParam = (InstockReceiveParameter[]) schedule.startSCHgetParams(wConn, paramArray);
			
			// 返却データがnullならば更新に失敗。
			if (viewParam == null)
			{
				// ロールバック
				wConn.rollback();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
				
			// 返却データの要素数が0以上ならば更新に成功。
			else if (viewParam.length >= 0)
			{
				// コミット
				wConn.commit();
				// スケジュールが正常に完了して表示データを取得した場合、表示する。
				// ためうちエリアをクリアする
				listCellClear();
				if (viewParam.length > 0)
				{
					// ためうちエリアを再表示する
					addList(viewParam);
				}
			}
				
			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				if (wConn != null)
				{
					// コネクションのクローズを行う
					wConn.rollback();
					wConn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
	}

	/** 
	 * ためうちエリアの入力情報が更新対象であるかを調べるためのメソッドです。<BR>
	 * <BR>
	 *  概要：押下されたボタンの種類によって、ためうちエリアの入力情報が更新対象であるかどうかを調べます。
	 *  更新対象ならばtrue、更新対象でなければfalseを返します。<BR>
	 * 
	 * @param pButtonType 押下されたボタンの種類
	 * @throws Exception 全ての例外を報告します。
	 */
	private boolean isChangeData(String pButtonType) throws Exception
	{
		
		// 更新登録ボタンが押下された場合
		if (pButtonType.equals(InstockReceiveParameter.BUTTON_MODIFYSUBMIT))
		{
			// HIDDEN項目を取得する。
			// 入荷ケース数
			String wOrgCaseQty = CollectionUtils.getString(HIDDEN0, lst_SInstkRcvWkMtn.getValue(0));
			// 入荷ピース数
			String wOrgPieceQty = CollectionUtils.getString(HIDDEN1, lst_SInstkRcvWkMtn.getValue(0));
			// 状態
			String wOrgStatus = CollectionUtils.getString(HIDDEN2, lst_SInstkRcvWkMtn.getValue(0));
			// 賞味期限
			String wOrgUseByDate = CollectionUtils.getString(HIDDEN3, lst_SInstkRcvWkMtn.getValue(0));
			
			// 入力テキストがひとつも変更されていない場合処理を行わない。
			// 入荷ケース数
			if (!lst_SInstkRcvWkMtn.getValue(5).equals(wOrgCaseQty))
			{
				return true;
			}
			// 状態
			else if (!lst_SInstkRcvWkMtn.getValue(6).equals(wOrgStatus))
			{
				return true;
			}
			// 賞味期限
			// 状態が完了以外は変更とみなさない
			else if( lst_SInstkRcvWkMtn.getValue(6).equals(L_STATUSFLAGCOMPLETION)
			 && !lst_SInstkRcvWkMtn.getValue(7).equals(wOrgUseByDate))
			{
				return true;
			}
			// 入荷ピース数
			else if (!lst_SInstkRcvWkMtn.getValue(15).equals(wOrgPieceQty))
			{
				return true;
			}
			return false;
		}
		// 一括作業中解除が押下された場合
		else
		{
			// HIDDEN項目を取得する。
			// 状態
			String wOrgStatus = CollectionUtils.getString(HIDDEN2, lst_SInstkRcvWkMtn.getValue(0));

			// 初期表示されたときの"状態"が未開始か完了ならば処理を行わない
			if (wOrgStatus.equals(L_STATUSFLAGUNSTARTED) || wOrgStatus.equals(L_STATUSFLAGCOMPLETION))
			{
				return false;
			}
			return true;
		}
		
	}
}
//end of class
