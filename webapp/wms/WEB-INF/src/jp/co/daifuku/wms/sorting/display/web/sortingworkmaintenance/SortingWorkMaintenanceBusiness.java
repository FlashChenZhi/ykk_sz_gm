// $Id: SortingWorkMaintenanceBusiness.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingworkmaintenance;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.ListCellColumn;
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
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingdate.ListSortingDateBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem.ListSortingItemBusiness;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;
import jp.co.daifuku.wms.sorting.schedule.SortingWorkMaintenanceSCH;

/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * 仕分作業メンテナンス画面クラスです。<BR>
 * 仕分作業メンテナンス処理を行うスケジュールにパラメータを引き渡します。<BR>
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
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   	作業者コード*<BR>
 *   	パスワード*<BR>
 *   	荷主コード*<BR>
 *   	作業状態*<BR>
 *   	仕分予定日*<BR>
 *   	商品コード<BR>
 *   	ケース/ピース区分*<BR>
 *   	クロス/DC区分*<BR>
 *  </DIR>
 * 
 *  [返却データ]<BR>
 *  <DIR>
 *   	荷主コード <BR>
 *   	荷主名称 <BR>
 *   	仕分予定日 <BR>
 *   	商品コード <BR>
 *   	商品名称 <BR>
 * 		ケース/ピース区分<BR>
 *   	ケース/ピース区分名称<BR>
 *    	クロス/DC区分<BR>
 *   	クロス/DC区分名称<BR>
 *   	出荷先ｺｰﾄﾞ<BR>
 *   	出荷先名称<BR>
 *   	ケース入数 <BR>
 *   	ボール入数 <BR>
 *   	作業予定ケース数 <BR>
 *   	作業予定ピース数 <BR>
 *   	仕分ケース数 <BR>
 *   	仕分ピース数 <BR>
 *   	状態フラグ <BR>
 *   	仕分場所 <BR>
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
 *   	状態フラグ* <BR>
 *   	仕分予定日* <BR>
 *   	商品コード* <BR>
 *   	ケース入数 <BR>
 *   	仕分ケース数 <BR>
 *   	仕分ピース数 <BR>
 *   	状態<BR>
 *   	押下ボタンの種類* <BR>
 *   	端末No* <BR>
 *   	ケース/ピース区分* <BR>
 *    	クロス/DC区分* <BR>
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
 *   	仕分予定日 <BR>
 *   	商品コード <BR>
 *   	商品名称 <BR>
 * 		ケース/ピース区分<BR>
 *   	ケース/ピース区分名称<BR>
 *    	クロス/DC区分<BR>
 *   	クロス/DC区分名称<BR>
 *   	出荷先ｺｰﾄﾞ<BR>
 *   	出荷先名称<BR>
 *   	ケース入数 <BR>
 *   	ボール入数 <BR>
 *   	作業予定ケース数 <BR>
 *   	作業予定ピース数 <BR>
 *   	仕分ケース数 <BR>
 *   	仕分ピース数 <BR>
 *   	状態フラグ <BR>
 *   	仕分場所 <BR>
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
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class SortingWorkMaintenanceBusiness extends SortingWorkMaintenance implements WMSConstants
{
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
	 * ViewState用：仕分予定日
	 */
	protected static final String SORTINGPLANDATE = "SORTING_PLAN_DATE";
	
	/**
	 * ViewState用：商品コード
	 */
	protected static final String ITEMCODE = "ITEM_CODE";
	/**
	 * ViewState用：商品名称
	 */
	protected static final String ITEMNAME = "ITEM_NAME";
	/**
	 * ViewState用：ケース/ピース区分
	 */
	protected static final String CASEPIECEFLAG = "CASE_PIECE_FLAG";
	
	/**
	 * ViewState用：クロス/DC区分
	 */
	protected static final String CROSSDCFLAG = "CROSS_DC_FLAG";
	
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
	 *  作業者コード    		[なし]<BR>
	 *  パスワード      		[なし]<BR>
	 *  荷主コード      		[該当荷主が1件しかない場合初期表示を行う]<BR>
	 *  作業状態        		["全て"]<BR>
	 *  仕分予定日      		[なし]<BR>
	 *  商品コード      		[なし]<BR>
	 *  ケース/ピース区分    	["全て"]<BR>
	 *  クロス/DC区分   		["全て"]<BR>
	 *  修正登録ボタン			[使用不可]<BR>
	 *  一括作業中解除ボタン	[使用不可]<BR>
	 *  一覧クリアボタン		[使用不可]<BR>
	 *  カーソル        		[作業者コード]
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
		// 作業状態プルダウン：全て
		pul_WorkStatus.setSelectedIndex(0);
		txt_PickingPlanDate.setText("");
		txt_ItemCode.setText("");
		// ケース/ピース区分ラジオボックス：全て
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		// クロス/DC区分ラジオボックス：全て
		rdo_CrossDCFlagAll.setChecked(true);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);
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
	 *       1.ボタン押下前メッセージを出力します。 <BR>
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
		}
		
		// 修正登録ボタン押下時メッセージ
		// MSG-W0008=修正登録しますか？
		btn_ModifySubmit.setBeforeConfirm("MSG-W0033");

		// 一括作業中解除ボタン押下時メッセージ
		// MSG-W0013=未作業状態にします。
		btn_AllWorkingClear.setBeforeConfirm("MSG-W0013");
		
		// 一覧クリアボタン押下時メッセージ
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
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorCode = param.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);
		// 入庫予定日
		Date storagePlanDate = 
			WmsFormatter.toDate(
				param.getParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY), 
					this.getHttpRequest().getLocale());
		// 商品コード
		String itemCode = param.getParameter(ListSortingItemBusiness.ITEMCODE_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		// 入庫予定日
		if (!StringUtil.isBlank(storagePlanDate))
		{
			txt_PickingPlanDate.setDate(storagePlanDate);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemCode))
		{
			txt_ItemCode.setText(itemCode);
		}

		// フォーカスを作業者コードにセット
		setFocus(txt_WorkerCode);
	}


	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

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
		Connection conn = null ;
		
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			SortingParameter param = new SortingParameter();

			// スケジュールから荷主コードを取得する。
			WmsScheduler schedule = new SortingWorkMaintenanceSCH();
			param = (SortingParameter)schedule.initFind(conn, param);

			// 荷主コードが1件の場合、初期表示させる。			
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
		forwardParam.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, 
			txt_ConsignorCode.getText());
		// 検索フラグ：予定
		forwardParam.setParameter(ListSortingConsignorBusiness.SEARCHCONSIGNOR_KEY, 
			SortingParameter.SEARCH_SORTING_WORK);

		// 荷主検索リストボックスを表示する。
		redirect(
			"/sorting/listbox/listsortingconsignor/ListSortingConsignor.do", forwardParam, "/progress.do");
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
	public void pul_StatusRetrieval_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_StatusRetrieval_Change(ActionEvent e) throws Exception
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
	public void txt_PickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕分予定日検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で出荷予定日検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 * 	作業状態<BR>
	 *  仕分予定日<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchPickingPlanDate_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		
		// 荷主コードをセット
		forwardParam.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, 
			txt_ConsignorCode.getText());



		// 作業状態
		if(pul_WorkStatus.getSelectedIndex() != 0)
		{
			
			// 作業状態
			String[] search = new String[1];
			
			// プルダウン：未開始
			if(pul_WorkStatus.getSelectedIndex() == 1)
			{
				// 未開始
				search[0] = new String(SortingParameter.STATUS_FLAG_UNSTARTED);
			}
			// プルダウン：作業中
			else if(pul_WorkStatus.getSelectedIndex() == 2)
			{
				// 作業中
				search[0] = new String(SortingParameter.STATUS_FLAG_WORKING);
			}
			// プルダウン：完了
			else if(pul_WorkStatus.getSelectedIndex() == 3)
			{
				// 完了
				search[0] = new String(SortingParameter.STATUS_FLAG_COMPLETION);
			}
			
			// 作業状態をパラメータにセット
			forwardParam.setParameter(ListSortingDateBusiness.WORKSTATUSSORTINGPLANDATE_KEY, search);
		}
		
		// 入庫予定日をセット
		forwardParam.setParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY, 
			WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));

		// 検索フラグ
		forwardParam.setParameter(ListSortingDateBusiness.SEARCHSORTINGPLANDATE_KEY, SortingParameter.SEARCH_SORTING_WORK);

		// 入庫予定日検索リストボックスを表示する。
		redirect(
			"/sorting/listbox/listsortingdate/ListSortingDate.do", forwardParam, "/progress.do");
	
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
	public void btn_PSearchItemCd_Server(ActionEvent e) throws Exception
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
	 *  仕分予定日<BR>
	 *  商品コード<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchItemCd_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		
		// 荷主コードをセット
		forwardParam.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, 
			txt_ConsignorCode.getText());


		// 作業状態
		if(pul_WorkStatus.getSelectedIndex() != 0)
		{
			
			// 作業状態
			String[] search = new String[1];
			
			// プルダウン：未開始
			if(pul_WorkStatus.getSelectedIndex() == 1)
			{
				// 未開始
				search[0] = new String(SortingParameter.STATUS_FLAG_UNSTARTED);
			}
			// プルダウン：作業中
			else if(pul_WorkStatus.getSelectedIndex() == 2)
			{
				// 作業中
				search[0] = new String(SortingParameter.STATUS_FLAG_WORKING);
			}
			// プルダウン：完了
			else if(pul_WorkStatus.getSelectedIndex() == 3)
			{
				// 完了
				search[0] = new String(SortingParameter.STATUS_FLAG_COMPLETION);
			}
			
			// 作業状態をパラメータにセット
			forwardParam.setParameter(ListSortingItemBusiness.WORKSTATUSITEM_KEY, search);
		}

		// 入庫予定日をセット
		forwardParam.setParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY, 
			WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
		// 商品コードをセット
		forwardParam.setParameter(ListSortingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 検索フラグ：予定
		forwardParam.setParameter(ListSortingItemBusiness.SEARCHITEM_KEY, 
			SortingParameter.SEARCH_SORTING_WORK);
	
		// 商品検索リストボックスを表示する。
		redirect("/sorting/listbox/listsortingitem/ListSortingItem.do", forwardParam, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CrossDCFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CrossDCFlagAll_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CrossDCFlagAll_Click(ActionEvent e) throws Exception
	{
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
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagCross_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagDC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagDC_Click(ActionEvent e) throws Exception
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
	 * 概要：入力エリアの入力項目をパラメータにセットし、スケジュールを開始します。
	 * そのスケジュールの検索結果をためうちエリアに表示します。<BR>
	 * 本メソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 * 1.入力チェック（必須入力、文字数、文字属性）<BR>
	 * 2.入力項目をパラメータにセットします。<BR>
	 * 3.スケジュールを開始します。(検索を行います)<BR>
	 * 4.検索条件をViewStateに記憶します。<BR>
	 * 5.表示項目があればためうちエリアに表示します。<BR>
	 * </DIR>
	 * <BR>
	 * <DIR>
	 * リストセルの列番号一覧
	 * <DIR>
	 * 0：HIDDEN項目<BR>
	 * 1：ケース/ピース区分<BR>
	 * 2：クロス/DC区分<BR>
	 * 3：出荷先ｺｰﾄﾞ<BR>
	 * 4：ケース入数<BR>
	 * 5：作業予定ケース数<BR>
	 * 6：仕分ケース数<BR>
	 * 7：状態<BR>
	 * 8：仕分場所<BR>
	 * 9：出荷先名称<BR>
	 * 10：ボール入数<BR>
	 * 11：作業予定ピース数<BR>
	 * 12：仕分ピース数<BR>
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
		
		// ためうちをクリアします
		listCellClear();
			
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
		
		// 入力チェックを行う（書式、必須、禁止文字）
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		pul_WorkStatus.validate();
		txt_PickingPlanDate.validate();
		txt_ItemCode.validate();
		
		Connection conn = null;
		
		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールパラメータをセットする
			SortingParameter param = new SortingParameter();
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード 
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 作業状態：全て
			if(pul_WorkStatus.getSelectedIndex() == 0 )
			{
				param.setStatusFlag(SortingParameter.STATUS_FLAG_ALL);
			}
			// 作業状態：未開始
			else if(pul_WorkStatus.getSelectedIndex() == 1)
			{
				param.setStatusFlag(SortingParameter.STATUS_FLAG_UNSTARTED);
			}
			// 作業状態：作業中
			else if(pul_WorkStatus.getSelectedIndex() == 2)
			{
				param.setStatusFlag(SortingParameter.STATUS_FLAG_WORKING);
			}
			// 作業状態：完了
			else if(pul_WorkStatus.getSelectedIndex() == 3)
			{
				param.setStatusFlag(SortingParameter.STATUS_FLAG_COMPLETION);
			}
			// 仕分予定日
			param.setPlanDate(WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// ケース／ピース区分
			// 全て
			if (rdo_CpfAll.getChecked())
			{
				param.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_ALL);
			}
			// ケース
			else if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_CASE);
			}
			// ピース
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_PIECE);
			}
			// クロス/DC区分
			// 全て
			if (rdo_CrossDCFlagAll.getChecked())
			{
				param.setTcdcFlag(SortingParameter.TCDC_FLAG_ALL);
			}
			// クロス
			else if (rdo_CrossDCFlagCross.getChecked())
			{
				param.setTcdcFlag(SortingParameter.TCDC_FLAG_CROSSTC);
			}
			// DC
			else if (rdo_CrossDCFlagDC.getChecked())
			{
				param.setTcdcFlag(SortingParameter.TCDC_FLAG_DC);
			}
			
			
			// スケジュールを開始する
			WmsScheduler schedule = new SortingWorkMaintenanceSCH();
			SortingParameter[] viewParam = (SortingParameter[])schedule.query(conn, param);
			
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
	 * ためうちエリアに検索結果をマッピングするためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した検索結果をためうちエリアに表示するのに使用します。<BR>
	 * ためうちエリアに荷主コード、荷主名称、仕分予定日とリストセルを表示します。<BR>
	 * <BR>
	 * リストセルの列番号一覧
	 * <DIR>
	 * 0：HIDDEN項目<BR>
	 * 	<DIR>
	 * 		0：仕分ケース数<BR>
	 * 		1：仕分ピース数<BR>
	 * 		2：状態<BR>
	 * 		4：作業No. <BR>
	 * 		5：最終更新日時<BR>
	 * 	</DIR>
	 * 1：ケース/ピース区分<BR>
	 * 2：クロス/DC区分<BR>
	 * 3：出荷先ｺｰﾄﾞ<BR>
	 * 4：ケース入数<BR>
	 * 5：作業予定ケース数<BR>
	 * 6：仕分ケース数<BR>
	 * 7：状態<BR>
	 * 8：仕分場所<BR>
	 * 9：出荷先名称<BR>
	 * 10：ボール入数<BR>
	 * 11：作業予定ピース数<BR>
	 * 12：仕分ピース数<BR>
	 * </DIR>
	 * 
	 * @param param Parameter[] ためうちエリアに表示するための情報。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void addList(SortingParameter[] param)
	{
		SortingParameter[] viewParam = param;
		
		// 荷主コード
		txt_SRConsignorCode.setText(this.getViewState().getString(CONSIGNORCODE));
		// 荷主名称
		txt_SRConsignorName.setText(this.getViewState().getString(CONSIGNORNAME));
		// 仕分予定日
		txt_RPickingPlanDate.setDate(WmsFormatter.toDate
			(this.getViewState().getString(SORTINGPLANDATE)));
		// 商品コード
		txt_RItemCode.setText(this.getViewState().getString(ITEMCODE));
		// 荷主名称
		txt_RItemName.setText(this.getViewState().getString(ITEMNAME));

		// 出荷先名称
		String label_customername = DisplayText.getText("LBL-W0036");
		// 仕分場所
		String label_sortinglocation = DisplayText.getText("LBL-W0133");
		// 実績報告
		String label_reportflag = DisplayText.getText("LBL-W0389");
		// 作業者コード
		String label_workercode = DisplayText.getText("LBL-W0274");
		// 作業者名
		String label_workername = DisplayText.getText("LBL-W0276");

		// リストセルに値をセットする
		for(int i = 0; i < viewParam.length; i++)
		{
			// リストセルに行を追加する
			lst_SSortingWorkMaintenance.addRow();
			// 値をセットする行を選択する
			lst_SSortingWorkMaintenance.setCurrentRow(lst_SSortingWorkMaintenance.getMaxRows() - 1);
			
			// HIDDEN項目を連結する。
			String hidden = createHiddenList(viewParam[i]);
			
			// 検索結果を各セルにセットしていく
			// HIDDEN項目
			lst_SSortingWorkMaintenance.setValue(0, hidden);
			
			// ケース/ピース区分
			lst_SSortingWorkMaintenance.setValue(1, viewParam[i].getCasePieceName());
			// クロス/DC区分
			lst_SSortingWorkMaintenance.setValue(2, viewParam[i].getTcdcName());
			// 出荷先ｺｰﾄﾞ
			lst_SSortingWorkMaintenance.setValue(3, viewParam[i].getCustomerCode());
			// ケース入数
			lst_SSortingWorkMaintenance.setValue(4, Formatter.getNumFormat(viewParam[i].getEnteringQty()));
			// 作業予定ケース数
			lst_SSortingWorkMaintenance.setValue(5, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
			// 仕分ケース数
			lst_SSortingWorkMaintenance.setValue(6, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
			// 状態
			if(viewParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_UNSTARTED))
			{
				// 未開始
				lst_SSortingWorkMaintenance.setValue(7, "0");
			}
			else if(viewParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_WORKING))
			{
				// 作業中
				lst_SSortingWorkMaintenance.setValue(7, "1");
			}
			else if(viewParam[i].getStatusFlagL().equals(SortingParameter.STATUS_FLAG_COMPLETION))
			{
				// 完了
				lst_SSortingWorkMaintenance.setValue(7,"2");
			}
			// 仕分場所
			lst_SSortingWorkMaintenance.setValue(8, viewParam[i].getSortingLocation());
			// 実績報告
			lst_SSortingWorkMaintenance.setValue(9,viewParam[i].getReportFlagName());
			// 出荷先名称
			lst_SSortingWorkMaintenance.setValue(10, viewParam[i].getCustomerName());
			// ボール入数
			lst_SSortingWorkMaintenance.setValue(11, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			// 作業予定ピース数
			lst_SSortingWorkMaintenance.setValue(12, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			// 仕分ピース数
			lst_SSortingWorkMaintenance.setValue(13, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));


			//tool tipをセット
			ToolTipHelper toolTip = new ToolTipHelper();
			// 出荷先名称
			toolTip.add(label_customername,viewParam[i].getCustomerName());
			// 仕分場所
			toolTip.add(label_sortinglocation,viewParam[i].getSortingLocation());
			// 実績報告
			toolTip.add(label_reportflag,viewParam[i].getReportFlagName());
			// 作業者コード
			toolTip.add(label_workercode,viewParam[i].getWorkerCode());
			// 作業者名
			toolTip.add(label_workername,viewParam[i].getWorkerName());
			
			lst_SSortingWorkMaintenance.setToolTip(i+1, toolTip.getText());
			
			
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
	 * 		0：ケースピース区分<BR>
	 * 		1：クロスDC区分<BR>
	 * 		2：仕分ケース数<BR>
	 * 		3：仕分ピース数<BR>
	 * 		4：状態<BR>
	 * 		5：作業No. <BR>
	 * 		6：最終更新日時<BR>
	 * </DIR>
	 * 
	 * @param viewParam StorageSupportParameter HIDDEN項目を含む<CODE>Parameter</CODE>クラス。
	 * @return 連結したHIDDEN項目
	 */
	private String createHiddenList(SortingParameter viewParam)
	{
		String hidden = null;
		
		// HIDDEN項目文字列作成 
		ArrayList hiddenList = new ArrayList();
		// ケースピース区分
		hiddenList.add(0, viewParam.getCasePieceFlag());
		// クロスDC区分
		hiddenList.add(1, viewParam.getTcdcFlag());
		// 仕分ケース数
		hiddenList.add(2, Formatter.getNumFormat(viewParam.getResultCaseQty()));
		// 仕分ピース数
		hiddenList.add(3, Formatter.getNumFormat(viewParam.getResultPieceQty()));
		// 状態
		if(viewParam.getStatusFlagL().equals(SortingParameter.STATUS_FLAG_UNSTARTED))
		{
			// 未開始
			hiddenList.add(4, L_STATUSFLAGUNSTARTED);
		}
		else if(viewParam.getStatusFlagL().equals(SortingParameter.STATUS_FLAG_WORKING))
		{
			// 作業中
			hiddenList.add(4, L_STATUSFLAGWORKING);
		}
		else if(viewParam.getStatusFlagL().equals(SortingParameter.STATUS_FLAG_COMPLETION))
		{
			// 完了
			hiddenList.add(4, L_STATUSFLAGCOMPLETION);
		}
		// 作業No.
		hiddenList.add(5, viewParam.getJobNo());
		// 最終更新日時
		hiddenList.add(6, WmsFormatter.getTimeStampString(viewParam.getLastUpdateDate()));
		
		// HIDDEN項目を連結します
		hidden = CollectionUtils.getConnectedString(hiddenList);
		
		return hidden;
	}


	/** 
	 * 検索条件をViewStateに保持するためのメソッドです。<BR>
	 * <BR>
	 * 概要：検索条件をViewStateに保持します。<BR>
	 * <BR>
	 * ViewStateのkey一覧
	 * <DIR>
	 * WORKER_CODE：作業者コード<BR>
	 * PASSWORD：パスワード<BR>
	 * CONSITNOR_CODE：荷主コード<BR>
	 * CONSITNOR_NAME：荷主名称<BR>
	 * WORK_STATUS：作業状態<BR>
	 * SORTING_PLAN_DATE：仕分予定日<BR>
	 * ITEM_CODE：商品コード<BR>
	 * CASE_PIECE_FLAG：ケース／ピースフラグ<BR>
	 * CROSS_DC_FLAG: クロス/DC区分
	 * </DIR>
	 * 
	 * @param param Parameter ためうちエリアに表示するための情報。
	 */
	private void setViewState(SortingParameter parameter)
	{
		// 作業者コード
		this.getViewState().setString(WORKERCODE, txt_WorkerCode.getText());
		
		// パスワード
		this.getViewState().setString(PASSWORD, txt_Password.getText());
		
		// 荷主コード
		this.getViewState().setString(CONSIGNORCODE, txt_ConsignorCode.getText());
		
		// 荷主名称
		this.getViewState().setString(CONSIGNORNAME, parameter.getConsignorName());
		
		// 作業状態
		if(pul_WorkStatus.getSelectedIndex() == 0)
		{
			// 全て
			this.getViewState().setString(WORKSTATUS, SortingParameter.STATUS_FLAG_ALL);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 1)
		{
			// 未開始
			this.getViewState().setString(WORKSTATUS, SortingParameter.STATUS_FLAG_UNSTARTED);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 2)
		{
			// 作業中
			this.getViewState().setString(WORKSTATUS, SortingParameter.STATUS_FLAG_WORKING);
		}
		else if(pul_WorkStatus.getSelectedIndex() == 3)
		{
			// 完了
			this.getViewState().setString(WORKSTATUS, SortingParameter.STATUS_FLAG_COMPLETION);
		}
		// 仕分予定日
		this.getViewState().setString(SORTINGPLANDATE, WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
		
		// 商品コード
		this.getViewState().setString(ITEMCODE, txt_ItemCode.getText());
		
		// 商品名称
		this.getViewState().setString(ITEMNAME, parameter.getItemName());
		
		// ケース/ピース区分
		// 全て
		if (rdo_CpfAll.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, SortingParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_CpfCase.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, SortingParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_CpfPiece.getChecked())
		{
			this.getViewState().setString(CASEPIECEFLAG, SortingParameter.CASEPIECE_FLAG_PIECE);
		}
		// クロス/DC区分
		// 全て
		if (rdo_CrossDCFlagAll.getChecked())
		{
			this.getViewState().setString(CROSSDCFLAG, SortingParameter.TCDC_FLAG_ALL);
		}
		// クロス
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			this.getViewState().setString(CROSSDCFLAG, SortingParameter.TCDC_FLAG_CROSSTC);
		}
		// DC
		else if (rdo_CrossDCFlagDC.getChecked())
		{
			this.getViewState().setString(CROSSDCFLAG, SortingParameter.TCDC_FLAG_DC);
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
		// 作業状態プルダウン：全て
		pul_WorkStatus.setSelectedIndex(0);
		txt_PickingPlanDate.setText("");
		txt_ItemCode.setText("");
		// ケース/ピース区分ラジオボックス：全て
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		// クロス/DC区分ラジオボックス：全て
		rdo_CrossDCFlagAll.setChecked(true);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);

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
		updateList(SortingParameter.BUTTON_MODIFYSUBMIT);
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
		updateList(SortingParameter.BUTTON_ALLWORKINGCLEAR);		
	}

	/** 
	 * 修正登録ボタン、一括作業中解除ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：ためうちエリアの行のうち、更新対象となるデータのみを更新します。<BR>
	 * ためうちエリアに保持している更新に必要な情報をパラメータにセットし、スケジュールを開始します。<BR>
	 * スケジュールが正常に完了した場合、表示ボタン押下時と同じ条件の検索結果をためうちエリアに再表示します。<BR>
	 * 正常に完了しなかった場合はエラーメッセージをメッセージエリアに表示します。<BR>
	 * また、ためうちエリアのデータは更新されずそのまま表示します。<BR>
	 * また、トランザクションの確定は本メソッドで行います。<BR>
	 * また、<CODE>NumberFormatException</CODE>が起こった場合はそのままthrowし画面にエラーを表示します。<BR>
	 * <BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <DIR>
	 * 1.入力項目のチェック。（必須、文字種別、禁止文字）<BR>
	 * 2.更新対象データがあるかのチェック。<BR>
	 * 3.更新情報をパラメータにセットしスケジュールを開始。<BR>
	 * 4.トランザクションのコミット・ロールバック。<BR>
	 * 5.ためうちエリアの表示。<BR>
	 * </DIR>
	 * <BR>
	 * 更新パラメータ一覧(* 必須入力)<BR>
	 * <DIR>
	 * 作業者コード*<BR>
	 * パスワード*<BR>
	 * 荷主コード(再検索用)*<BR>
	 * 作業状態(再検索用)*<BR>
	 * 入庫予定日(再検索用)*<BR>
	 * 商品コード(再検索用)*<BR>
	 * ケース/ピース区分(再検索用)*<BR>
	 * 押下ボタンの種類* <BR>
	 * ためうち行No*<BR>
	 * 端末No*<BR>
	 * ケース入数 <BR>
	 * 入庫ケース数 <BR>
	 * 入庫ピース数 <BR>
	 * 入庫棚 <BR>
	 * 状態<BR>
	 * 賞味期限 <BR>
	 * <隠し項目> <BR>
	 * 作業No.* <BR>
	 * 最終更新日時* <BR>
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
			ArrayList lst = (ArrayList) lst_SSortingWorkMaintenance.getListCellColumns();
			ListCellColumn[] List_Title = new ListCellColumn[lst.size() + 1];
			for (int l = 0; l < lst.size(); l++)
			{
				ListCellColumn listtemp = (ListCellColumn) lst.get(l);
				List_Title[l + 1] = new ListCellColumn();
				List_Title[l + 1] = (ListCellColumn) listtemp;
			}

			// ためうちの要素数分チェックをかけ、更新を行う行のみVectorに値をセットしていく。
			Vector vecParam = new Vector(lst_SSortingWorkMaintenance.getMaxRows());
			for(int i = 1; i < lst_SSortingWorkMaintenance.getMaxRows(); i++)
			{
				// 作業行を選択する
				lst_SSortingWorkMaintenance.setCurrentRow(i);
				
				// ためうちの入力チェックを行う。(仕分ケース数、状態、仕分ピース数)
				// 仕分ケース数
				lst_SSortingWorkMaintenance.validate(6, false);
				// 状態
				lst_SSortingWorkMaintenance.validate(7, true);
				// 仕分ピース数
				lst_SSortingWorkMaintenance.validate(12, false);
				
				// 更新対象ならば処理を行うため、Vectorに値をセットする
				if(isChangeData(pButtonType))
				{
					SortingParameter param = new SortingParameter();
						
					// 作業者コード、パスワードのセット
					param.setWorkerCode(txt_WorkerCode.getText());
					param.setPassword(txt_Password.getText());
					
					// 再表示用の検索条件のセット
					// (荷主コード、作業状態、仕分予定日、商品コード、ケース/ピース区分)
					// 荷主コード
					param.setConsignorCode(this.getViewState().getString(CONSIGNORCODE));
					// 作業状態
					param.setStatusFlag(this.getViewState().getString(WORKSTATUS));
					// 仕分予定日
					param.setPlanDate
						(this.getViewState().getString(SORTINGPLANDATE));
					// 商品コード
					param.setItemCode(this.getViewState().getString(ITEMCODE));
					// ケース/ピース区分
					param.setCasePieceFlag(this.getViewState().getString(CASEPIECEFLAG));
					// クロス/DC区分
					param.setTcdcFlag(this.getViewState().getString(CROSSDCFLAG));
					
					// 更新値をセット
					// (仕分ケース数、仕分ピース数、状態、ケース入数、押下ボタン、ためうち行、端末No.)
					// 仕分ケース数	
					param.setResultCaseQty(Formatter.getInt(lst_SSortingWorkMaintenance.getValue(6)));
					// 仕分ピース数
					param.setResultPieceQty(Formatter.getInt(lst_SSortingWorkMaintenance.getValue(13)));
					// 状態
					// 状態：未開始
					if(lst_SSortingWorkMaintenance.getValue(7).equals("0"))
					{
						param.setStatusFlagL(SortingParameter.STATUS_FLAG_UNSTARTED);
					}
					// 状態：作業中
					else if(lst_SSortingWorkMaintenance.getValue(7).equals("1"))
					{
						param.setStatusFlagL(SortingParameter.STATUS_FLAG_WORKING);
					}
					// 状態：完了
					else if(lst_SSortingWorkMaintenance.getValue(7).equals("2"))
					{
						param.setStatusFlagL(SortingParameter.STATUS_FLAG_COMPLETION);

					}

					// ケース入数
					param.setEnteringQty(Formatter.getInt(lst_SSortingWorkMaintenance.getValue(4)));
					// 押下ボタン
					param.setButtonType(pButtonType);
					// ためうち行
					param.setRowNo(lst_SSortingWorkMaintenance.getCurrentRow());
					// 端末No.
					UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
					param.setTerminalNumber(userHandler.getTerminalNo());
					
					// HIDDEN項目をセット
					// (作業No.(5)、最終更新日時(6))
					String hidden = lst_SSortingWorkMaintenance.getValue(0);
					param.setJobNo(CollectionUtils.getString(5, hidden));
					param.setLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(6, hidden)));
					
					vecParam.addElement(param);
				}
			}
				
			// 要素数0ならば"更新対象データがありません。"処理を終了
			if(vecParam.size() == 0)
			{
				message.setMsgResourceKey("6023154");
				return;
			}
				
			// パラメータに値をコピーする
			SortingParameter[] paramArray = new SortingParameter[vecParam.size()];
			vecParam.copyInto(paramArray);
				
			// スケジュールスタート
			WmsScheduler schedule = new SortingWorkMaintenanceSCH();
			SortingParameter[] viewParam = (SortingParameter[])schedule.startSCHgetParams(conn, paramArray);
			
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
				if (viewParam.length > 0)
				{
					addList(viewParam);
				}

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
		if(pButtonType.equals(SortingParameter.BUTTON_MODIFYSUBMIT))
		{
			// HIDDEN項目を取得する。
			
			String hidden = lst_SSortingWorkMaintenance.getValue(0);
			// ケースピース区分
			String orgCasePiece = CollectionUtils.getString(0, hidden); 
			// クロスDC区分
			String orgCrossDc = CollectionUtils.getString(1, hidden); 
			// 仕分ケース数
			String orgCaseQty = CollectionUtils.getString(2, hidden);
			// 仕分ピース数
			String orgPieceQty = CollectionUtils.getString(3, hidden);
			// 状態
			String orgStatus = CollectionUtils.getString(4, hidden);
			
			// 入力テキストがひとつも変更されていない場合処理を行わない。
			// 仕分ケース数
			if(!lst_SSortingWorkMaintenance.getValue(6).equals(orgCaseQty))
			{
				return true;
			}
			// 状態
			else if(!lst_SSortingWorkMaintenance.getValue(7).equals(orgStatus))
			{
				return true;
			}
			// 仕分ピース数
			else if(!lst_SSortingWorkMaintenance.getValue(13).equals(orgPieceQty))
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
			String hidden = lst_SSortingWorkMaintenance.getValue(0);
			String orgStatus = CollectionUtils.getString(4, hidden);

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
		txt_RPickingPlanDate.setText("");
		txt_RItemCode.setText("");
		txt_RItemName.setText("");

		// ためうちエリアを削除
		lst_SSortingWorkMaintenance.clearRow();
		
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
	public void lbl_Consignor_Server(ActionEvent e) throws Exception
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
	public void lbl_SPickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SRPickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SRPickingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SRPickingPlanDate_TabKey(ActionEvent e) throws Exception
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
	public void rdo_CrossDCFlagAll_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagAll_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RPickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RPickingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RPickingPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSortingWorkMaintenance_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSortingWorkMaintenance_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSortingWorkMaintenance_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSortingWorkMaintenance_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSortingWorkMaintenance_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSortingWorkMaintenance_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SSortingWorkMaintenance_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Item_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RItemName_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//end of class
