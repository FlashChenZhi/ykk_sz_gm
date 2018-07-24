//$Id: SortingWorkListBusiness.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingworklist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

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
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingdate.ListSortingDateBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem.ListSortingItemBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingworklist.ListSortingWorkListBusiness;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;
import jp.co.daifuku.wms.sorting.schedule.SortingWorkListSCH;

/**
 * Designer : D.Hakui<BR>
 * Maker : D.Hakui<BR>
 * <BR>
 * 仕分作業リスト発行を行う画面クラスです。<BR>
 * 仕分作業リスト発行を行うスケジュールにパラメータを引き渡します。<BR>
 * <BR>
 * <DIR>
 * 1.表示ボタン押下処理(<CODE>btn_PDisplay_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 * 	画面から入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールが表示用のデータを検索し、ポップアップ画面で表示します。<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   荷主コード *<BR>
 * 	 仕分予定日<BR>
 * 	 商品コード<BR>
 * 	 ケース/ピース区分 *<BR>
 * 	 クロス/DC *<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.印刷ボタン押下処理(<CODE>btn_PDisplay_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 *  画面から入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールがデータを検索し印刷します。<BR>
 *  スケジュールは印刷に成功した場合はtrue、失敗した場合はfalseを返します。<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   荷主コード *<BR>
 * 	 仕分予定日<BR>
 * 	 商品コード<BR>
 * 	 ケース/ピース区分 *<BR>
 * 	 クロス/DC *<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>D.Hakui</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class SortingWorkListBusiness extends SortingWorkList implements WMSConstants
{
	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/sorting/listbox/listsortingconsignor/ListSortingConsignor.do";
	// 仕分予定日検索ポップアップアドレス
	protected static final String DO_SRCH_SORTINGPLANDATE = "/sorting/listbox/listsortingdate/ListSortingDate.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";
	// 商品検索ポップアップ呼び出しアドレス
	protected static final String DO_SRCH_SORTINGITEM = "/sorting/listbox/listsortingitem/ListSortingItem.do";
	// 結果画面ポップアップ呼び出しアドレス
	protected static final String DO_SRCH_SORTINGLIST = "/sorting/listbox/listsortingworklist/ListSortingWorkList.do";
	// どのコントロールから呼び出されたダイアログかを保持：印刷ボタン

	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * <BR>
	 * <DIR>
	 *  項目名[初期値]
	 *  <DIR>
	 *  荷主コード			[該当荷主が1件しかない場合初期表示を行う]<BR>
	 *  仕分予定日			[なし]<BR>
	 *  商品コード			[なし]<BR>
		 *  ケース/ピース区分	[全て]<BR>
		 *  クロス/DC			[全て]<BR>
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
		txt_ConsignorCode.setText(getConsignorCode());
		txt_PickingPlanDate.setText("");
		txt_ItemCode.setText("");
		rdo_CpfAll.setChecked(true);
		rdo_CrossDCFlagAll.setChecked(true);

		// フォーカスを荷主コードにセットします。
		setFocus(txt_ConsignorCode);

	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。Pageに定義されているpage_DlgBackをオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);
		// 仕分予定日
		Date pickingplandate = WmsFormatter.toDate(param.getParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY), this.getHttpRequest().getLocale());
		// 商品コード
		String itemcode = param.getParameter(ListSortingItemBusiness.ITEMCODE_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 仕分予定日
		if (!StringUtil.isBlank(pickingplandate))
		{
			txt_PickingPlanDate.setDate(pickingplandate);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		// 荷主コードにフォーカスセット
		setFocus(txt_ConsignorCode);
	}

	/** 
	 * ダイアログボタンから、戻ってくるときにこのメソッドが
	 * 呼ばれます。Pageに定義されているpage_ConfirmBackをオーバライドします。
	 * <BR>
	 * 概要：ダイアログにて「はい」を選択された場合、該当の処理を実行します。<BR>
	 * <BR>
	 * 1.どのダイアログからの戻りかをチェックします。<BR>
	 * 2.ダイアログで「はい」「いいえ」のどちらが選択されたかをチェックします。<BR>
	 * 3.「はい」が選択された場合、スケジュールを開始します。<BR>
	 * 4.印刷ダイアログの場合<BR>
	 *   <DIR>
	 *   4-1.入力項目をパラメータにセットする。<BR>
	 *   4-2.印刷スケジュールを開始する。<BR>
	 *   4-3.スケジュールの結果をメッセージエリアに表示する。<BR>
	 *	 </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
    
	public void page_ConfirmBack(ActionEvent e) throws Exception
	{
		try
		{
			// 荷主コードにセットフォーカス
			setFocus(txt_ConsignorCode);

			// どのダイアログからの戻りかをチェックする
			if (!this.getViewState().getBoolean(DIALOG_PRINT))
			{
				return;
			}
			// ダイアログで「はい」が押された場合true
			// ダイアログで「いいえ」が押された場合false
			boolean isExecute = new Boolean(e.getEventArgs().get(0).toString()).booleanValue();
			// 「いいえ」を押された場合、処理を終了する
			// ここでのメッセージのセットは不要です
			if (!isExecute)
			{
				return;
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			// ダイアログの動作が終了したため、必ずフラグをOFFにする
			this.getViewState().setBoolean(DIALOG_PRINT, false);
		}
		
		// 印刷スケジュールを開始する
		Connection conn = null;
		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 入力値をパラメータにセットする
			SortingParameter[] param = new SortingParameter[1];
			param[0] = createParameter();

			// スケジュールを開始する
			WmsScheduler schedule = new SortingWorkListSCH();
			schedule.startSCH(conn, param);
			
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
	* 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	* <BR>
	* <DIR>
	*  概要:ダイアログを表示します。 <BR>
	* </DIR>
	*
	* @param e ActionEvent イベントの情報を格納するクラスです。
	* @throws Exception 全ての例外を報告します。
	*/
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
		{
			// パラメータを取得
			String title = CollectionUtils.getMenuParam(0, menuparam);
			String functionID = CollectionUtils.getMenuParam(1, menuparam);
			String menuID = CollectionUtils.getMenuParam(2, menuparam);
			// ViewStateへ保持する
			this.getViewState().setString(M_TITLE_KEY, title);
			this.getViewState().setString(M_FUNCTIONID_KEY, functionID);
			this.getViewState().setString(M_MENUID_KEY, menuID);
			// 画面名称をセットする
			lbl_SettingName.setResourceKey(title);
			// ヘルプファイルへのパスをセットする
			btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()));
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/** 
	 * 入力エリアの入力値をセットしたパラメータオブジェクトを生成します。<BR>
	 * @return 入力エリアの入力値を含んだパラメータオブジェクト
	 */
	protected SortingParameter createParameter()
	{
		// スケジュールパラメータをセットする
		SortingParameter param = new SortingParameter();
		param = new SortingParameter();
		param.setConsignorCode(txt_ConsignorCode.getText());
		param.setPlanDate(WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
		param.setItemCode(txt_ItemCode.getText());
		// ケース/ピース区分
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
		// クロス/DC
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
		// 作業状態
		// 全て
		if (pul_WorkStatus.getSelectedIndex() == 0)
		{
			param.setStatusFlag(SortingParameter.STATUS_FLAG_ALL);
		}
		// 未開始
		else if (pul_WorkStatus.getSelectedIndex() == 1)
		{
			param.setStatusFlag(SortingParameter.STATUS_FLAG_UNSTARTED);
		}
		// 作業中
		else if (pul_WorkStatus.getSelectedIndex() == 2)
		{
			param.setStatusFlag(SortingParameter.STATUS_FLAG_WORKING);
		}
		// 完了
		else if (pul_WorkStatus.getSelectedIndex() == 3)
		{
			param.setStatusFlag(SortingParameter.STATUS_FLAG_COMPLETION);
		}

		return param;	

	}
	// Private methods -----------------------------------------------

	/**
	 * スケジュールから荷主コードを取得するためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主コードを返します。<BR>
	 *
	 * @throws Exception 全ての例外を報告します。
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			SortingParameter param = new SortingParameter();

			// スケジュールから荷主コードを取得する。
			WmsScheduler schedule = new SortingWorkListSCH();
			param = (SortingParameter) schedule.initFind(conn, param);

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
				// コネクションのクローズを行う
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
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
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 検索フラグ(作業)
		param.setParameter(ListSortingConsignorBusiness.SEARCHCONSIGNOR_KEY, SortingParameter.SEARCH_SORTING_WORK);

		// 処理中画面->結果画面
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
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
	 * その検索条件で仕分予定日検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  仕分予定日<BR>
	 *  </DIR>
	 * <BR>
	 *
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchPickingPlanDate_Click(ActionEvent e) throws Exception
	{
		// 仕分予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 仕分予定日
		param.setParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
		// 検索フラグ
		param.setParameter(ListSortingDateBusiness.SEARCHSORTINGPLANDATE_KEY, SortingParameter.SEARCH_SORTING_WORK);

		// 処理中画面->結果画面
		redirect(DO_SRCH_SORTINGPLANDATE, param, DO_SRCH_PROCESS);
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
	 *  仕分予定日<BR>
	 *  商品コード<BR>
	 *  </DIR>
	 * <BR>
	 *
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 仕分予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 仕分予定日
		param.setParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
		// 商品コード
		param.setParameter(ListSortingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 検索フラグ(作業)
		param.setParameter(ListSortingItemBusiness.SEARCHITEM_KEY, SortingParameter.SEARCH_SORTING_WORK);
		// 処理中画面->結果画面
		redirect(DO_SRCH_SORTINGITEM, param, DO_SRCH_PROCESS);
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
	public void lbl_CrossDCTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Display_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 表示ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をパラメータにセットし別画面仕分作業一覧リストボックスを表示します。<BR>
	 * 本メソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 * 1.入力チェック（必須入力、文字数、文字属性）<BR>
	 * 2.入力チェック（開始仕分予定日がが終了仕分予定日より小さいこと）<BR>
	 * 3.入力項目をパラメータにセットする。<BR>
	 * 4.仕分作業一覧リストボックスを表示する。<BR>
	 * </DIR>
	 *
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Display_Click(ActionEvent e) throws Exception
	{
		// 仕分作業一覧画面へ検索条件をセットする
		ForwardParameters forwardParam = new ForwardParameters();
		// 荷主コード
		forwardParam.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 仕分予定日
		forwardParam.setParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
		// 商品コード
		forwardParam.setParameter(ListSortingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		// ケース/ピース区分
		// 全て
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(ListSortingWorkListBusiness.CASEPIECEFLAG_KEY, SortingParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_CpfCase.getChecked())
		{
			forwardParam.setParameter(ListSortingWorkListBusiness.CASEPIECEFLAG_KEY, SortingParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(ListSortingWorkListBusiness.CASEPIECEFLAG_KEY, SortingParameter.CASEPIECE_FLAG_PIECE);
		}

		// クロス/ＤＣ
		// 全て
		if (rdo_CrossDCFlagAll.getChecked())
		{
			forwardParam.setParameter(ListSortingWorkListBusiness.CROSSDC_KEY, SortingParameter.TCDC_FLAG_ALL);
		}
		// クロス
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			forwardParam.setParameter(ListSortingWorkListBusiness.CROSSDC_KEY, SortingParameter.TCDC_FLAG_CROSSTC);
		}
		// DC
		else if (rdo_CrossDCFlagDC.getChecked())
		{
			forwardParam.setParameter(ListSortingWorkListBusiness.CROSSDC_KEY, SortingParameter.TCDC_FLAG_DC);
		}

		// 作業状態
		// 全て
		if (pul_WorkStatus.getSelectedIndex() == 0)
		{
			forwardParam.setParameter(ListSortingWorkListBusiness.STATUSFLAG_KEY, SortingParameter.STATUS_FLAG_ALL);
		}
		// 未開始
		else if (pul_WorkStatus.getSelectedIndex() == 1)
		{
			forwardParam.setParameter(ListSortingWorkListBusiness.STATUSFLAG_KEY, SortingParameter.STATUS_FLAG_UNSTARTED);
		}
		// 作業中
		else if (pul_WorkStatus.getSelectedIndex() == 2)
		{
			forwardParam.setParameter(ListSortingWorkListBusiness.STATUSFLAG_KEY, SortingParameter.STATUS_FLAG_WORKING);
		}
		// 完了
		else if (pul_WorkStatus.getSelectedIndex() == 3)
		{
			forwardParam.setParameter(ListSortingWorkListBusiness.STATUSFLAG_KEY, SortingParameter.STATUS_FLAG_COMPLETION);
		}

		// 処理中画面->結果画面
		redirect(DO_SRCH_SORTINGLIST, forwardParam, DO_SRCH_PROCESS);
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void btn_Print_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 印刷ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をパラメータにセットし印刷スケジュールを開始します。<BR>
	 * スケジュールは印刷に成功した場合はtrue、印刷に失敗した場合はfalseを返します。<BR>
	 * <BR>
	 *
	 * 1.確認ダイアログボックスを表示し、確認します。<BR>
	 * <DIR>
	 *   "印刷しますか？"<BR>
	 *	[確認ダイアログ キャンセル]<BR>
	 *	<DIR>
	 *		何も行いません。
	 *	</DIR>
	 *	[確認ダイアログ OK]<BR>
	 *	<DIR>
	 *			1.入力チェック（必須入力、文字数、文字属性）<BR>
	 *			2.入力項目をパラメータにセットする。<BR>
	 *			3.印刷スケジュールを開始する。<BR>
	 *			4.スケジュールの結果をメッセージエリアに表示する。<BR>
	 *	</DIR>
	 *
	 *
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		// 荷主コードにセットフォーカス
		setFocus(txt_ConsignorCode);
		// 入力チェックを行います。
		txt_ConsignorCode.validate();
		txt_PickingPlanDate.validate();
		txt_ItemCode.validate(false);

		Connection conn = null;

		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 入力値をセットします。
			SortingParameter param = createParameter();
			
			// 件数チェックを行い、印刷データがある場合
			// 印刷確認ダイアログを表示する。
			// ない場合は、ダイアログを表示せず処理を終了する。
			WmsScheduler schedule = new SortingWorkListSCH();
			int reportCount = schedule.count(conn, param);
			if (reportCount != 0)
			{
				// MSG-W0061={0}件該当しました。<BR>印刷しますか？
				setConfirm("MSG-W0061" + wDelim + reportCount);
				// 印刷ボタンからダイアログ表示されたことを記憶する
				this.getViewState().setBoolean(DIALOG_PRINT, true);
			}
			else
			{
				// メッセージをセット
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
				// コネクションのクローズを行う
				if (conn != null)
				{
					conn.close();
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
		txt_PickingPlanDate.setText("");
		txt_ItemCode.setText("");
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CrossDCFlagAll.setChecked(true);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);
		pul_WorkStatus.setSelectedIndex(0);

		// フォーカスを荷主コードにセットします。
		setFocus(txt_ConsignorCode);
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
	public void rdo_CpfAllAnd_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAllAnd_Click(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAllDist_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 *
	 * @param e ActionEvent
	 * @throws Exception
	 */
	public void rdo_CpfAllDist_Click(ActionEvent e) throws Exception
	{
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

}
//end of class
