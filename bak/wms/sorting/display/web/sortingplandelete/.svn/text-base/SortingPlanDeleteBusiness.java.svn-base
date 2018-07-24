// $Id: SortingPlanDeleteBusiness.java,v 1.1.1.1 2006/08/17 09:34:32 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingplandelete;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.Constants;
import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
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
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;
import jp.co.daifuku.wms.sorting.schedule.SortingPlanDeleteSCH;

/**
 * Designer : S.Ishigane <BR>
 * Maker : T.Kuroda <BR>
* <BR>
 * 仕分予定データ削除クラスです。 <BR>
 * 画面から入力された内容をパラメータにセットしスケジュールに渡し、表示処理を行います。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで <BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.ページロード時 （<CODE>page_Load</CODE> メソッド） <BR>
 * <BR>
 * <DIR>画面初期表示で仕分予定情報に荷主コードが1件のみ存在した場合、表示します。 <BR>
 * <BR>
 * [パラメータ] <BR>
 * <BR>
 * <DIR>なし <BR>
 * </DIR> <BR>
 * [返却データ] <BR>
 * <BR>
 * <DIR>荷主コード <BR>
 * </DIR> </DIR> <BR>
 * 2.表示ボタン押下処理（ <CODE>btn_View_Click</CODE> メソッド） <BR>
 * <BR>
 * <DIR>入力項目のチェックを行い、正しい場合パラメータにセットして渡します。 <BR>
 * スケジュールの結果をメッセージエリアにセットし表示します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <BR>
 * <DIR>作業者コード* <BR>
 * パスワード* <BR>
 * 荷主コード* <BR>
 * 開始予定日 <BR>
 * 終了予定日 <BR>
 * </DIR> <BR>
 * [返却データ] <BR>
 * <BR>
 * <DIR>選択Box有効/無効フラグ <BR>
 * 荷主コード <BR>
 * 荷主名称 <BR>
 * 仕分予定日 <BR>
 * </DIR> </DIR> 3.削除ボタン押下処理（ <CODE>btn_Delete_Click</CODE> メソッド） <BR>
 * <BR>
 * <DIR>ためうちエリアに表示されている内容を全データをパラメータとして渡します。 <BR>
 * それにより削除処理を行います。メッセージを取得し表示します。 <BR>
 * <BR>
 * [パラメータ] *必須入力 <BR>
 * <BR>
 * <DIR>
 * 作業者コード* <BR>
 * パスワード* <BR>
 * 荷主コード* <BR>
 * リスト発行要求* <BR>
 * 選択BoxON/OFF* <BR>
 * 仕分予定日* <BR>
 * ためうち行No.* <BR>
 * <BR>
 * </DIR> <BR>
 * [返却データ] <BR>
 * <BR>
 * <DIR>
 * 選択Box有効/無効フラグ <BR>
 * 荷主コード <BR>
 * 荷主名称 <BR>
 * 仕分予定日 <BR>
 * </DIR> </DIR> <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/25</TD>
 * <TD>T.Kuroda</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * n $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class SortingPlanDeleteBusiness extends SortingPlanDelete implements WMSConstants
{
	// Class fields --------------------------------------------------
	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/sorting/listbox/listsortingconsignor/ListSortingConsignor.do";
	// 仕分予定日検索ポップアップアドレス
	private static final String DO_SRCH_SORTINGPLANDATE = "/sorting/listbox/listsortingdate/ListSortingDate.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";
	
	/**
	 * 選択チェックボックスの列番号
	 */
	private static final int COL_SELECT = 1;

	/**
	 * 仕分予定日の列番号
	 */
	private static final int COL_RETRIEVAL_PLAN_DATE = 2;
	
	/**
	 * ViewStateのKey（荷主コード）
	 */
	private static String KEY_CONSIGNOR_CODE = "CONSIGNOR_CODE";
	
	/**
	 * ViewStateのKey（開始仕分予定日）
	 */
	private static String KEY_FROM_SORTING_PLAN_DATE = "FROM_SORTING_PLAN_DATE";
	
	/**
	 * ViewStateのKey（終了仕分予定日）
	 */
	private static String KEY_TO_SORTING_PLAN_DATE = "TO_SORTING_PLAN_DATE";
	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面が読み込まれたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <BR>
	 * <DIR>1.タイトルを表示します。 <BR>
	 * 2.入力エリアを初期設定します。 <BR>
	 * 3.スケジュールを開始します。 <BR>
	 * 4.全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンを無効にします。 <BR>
	 * 5.カーソルを作業コードにセットします。 <BR>
	 * </DIR> <BR>
	 * 項目[初期値] <BR>
	 * <BR>
	 * 作業者コード[なし] <BR>
	 * パスワード[なし] <BR>
	 * 荷主コード[検索値] <BR>
	 * 開始仕分予定日[なし] <BR>
	 * 終了仕分予定日[なし] <BR>
	 * 仕分予定削除一覧リスト発行する[true] <BR>
	 * <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 各入力フィールドに初期値をセットします。
		this.txt_WorkerCode.setText("");
		this.txt_Password.setText("");

		// 荷主コード
		this.txt_ConsignorCode.setText(getConsignorCode());
		this.txt_StrtPickPlanDate.setText("");
		this.txt_EdPickPlanDate.setText("");
		this.chk_CommonUseEvent.setChecked(true);

		// ためうち部のボタンを無効にする
		setBtnTrueFalse(false);

		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}
	
	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * <DIR>概要:ダイアログを表示します。 <BR>
	 * </DIR> <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
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
		// ダイアログを表示します MSG-W0007=削除しますか？
		btn_Delete.setBeforeConfirm("MSG-W0007");
		// ダイアログを表示します MSG-W0012=一覧入力情報がクリアされます。宜しいですか？
		btn_ListClear.setBeforeConfirm("MSG-W0012");
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが呼ばれます。 <BR>
	 * Pageに定義されているpage_DlgBackをオーバライドします。 <BR>
	 * <BR>
	 * 概要:検索画面の返却データを取得しセットします <BR>
	 * <BR>
	 * <DIR>1.ポップアップの検索画面から返される値を取得します。 <BR>
	 * 2.値が空でないときに画面にセットします。 <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		String consignorCode = param.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);
		Date startSortingPlanDate = WmsFormatter.toDate(param.getParameter(ListSortingDateBusiness.STARTSORTINGPLANDATE_KEY), this.getHttpRequest().getLocale());
		Date endEndPlanDate = WmsFormatter.toDate(param.getParameter(ListSortingDateBusiness.ENDSORTINGPLANDATE_KEY), this.getHttpRequest().getLocale());

		// 荷主コード
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		// 開始予定日
		if (!StringUtil.isBlank(startSortingPlanDate))
		{
			this.txt_StrtPickPlanDate.setDate(startSortingPlanDate);
		}
		// 終了予定日
		if (!StringUtil.isBlank(endEndPlanDate))
		{
			this.txt_EdPickPlanDate.setDate(endEndPlanDate);
		}

		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * スケジュールから荷主コードを取得するためのメソッドです。 <BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主コードを返します。 <BR>
	 * <DIR>1.荷主コードが1件しかない場合該当荷主を返します。そうでない場合はnullを返します。 <BR>
	 * <DIR>
	 * 
	 * @throws Exception
	 *             全ての例外を報告します。
	 * 
	 * @return 荷主コード
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			// コネクションを取得します。
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			// パラメータの宣言
			SortingParameter param = new SortingParameter();
			param.setStatusFlagL(SortingParameter.STATUS_FLAG_UNSTARTED);

			// スケジュールから荷主コードを取得します。
			WmsScheduler schedule = new SortingPlanDeleteSCH();
			
			// スケジュールを開始します。
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
				// コネクションのクローズを行います。
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

	/**
	 * ボタンの編集可否を設定するメソッドです。 <BR>
	 * <BR>
	 * 概要：false or trueを受け取ってボタンの編集可否を設定します。 <BR>
	 * 
	 * @param status(false
	 *            or true)
	 * @return なし
	 */
	private void setBtnTrueFalse(final boolean arg)
	{
		// ためうち部のボタン
		this.btn_AllCheck.setEnabled(arg);
		this.btn_AllCheckClear.setEnabled(arg);
		this.btn_Delete.setEnabled(arg);
		this.btn_ListClear.setEnabled(arg);
	}

	/**
	 * ためうちエリアに検索結果をマッピングするためのメソッドです。 <BR>
	 * <BR>
	 * 概要：スケジュールから取得した検索結果をためうちエリアに表示するのに使用します。 <BR>
	 * ためうちエリアに仕分予定日をリストセルを表示します。 <BR>
	 * <BR>
	 * リストセルの列番号一覧 <DIR>1：選択box <BR>
	 * 2：仕分予定日 <BR>
	 * </DIR>
	 * 
	 * @param param
	 *            Parameter[] ためうちエリアに表示するための情報。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void addList(final SortingParameter[] param) throws Exception
	{
		// ためうちエリアに検索結果を表示します。
		// 荷主

		// リストセルに値をセット
		for (int i = 0; i < param.length; i++)
		{
			// リストセルに行を追加
			this.lst_PickingPlanDelete.addRow();

			// 値をセットする行を選択します
			this.lst_PickingPlanDelete.setCurrentRow(i + 1);

			// 選択チェックボックス有効値(デフォルト：チェックなし)
			this.lst_PickingPlanDelete.setChecked(COL_SELECT, false);
			
			// 仕分予定日
			this.lst_PickingPlanDelete.setValue(COL_RETRIEVAL_PLAN_DATE, WmsFormatter.toDispDate(param[i].getPlanDate(), this.getHttpRequest().getLocale()));
		}
	}

	/**
	 * リストセルの選択チェックボックスの値を設定します。
	 * 
	 * @param check チェックボックスに設定する値
	 */
	private void listCellCheckAllChange(final boolean check)
	{
		for (int i = 1; i < this.lst_PickingPlanDelete.getMaxRows(); i++)
		{
			this.lst_PickingPlanDelete.setCurrentRow(i);
			this.lst_PickingPlanDelete.setChecked(COL_SELECT, check);
		}
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
	 * 概要:メニュー画面に遷移します。<BR>
	 * <BR>
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
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。 <BR>
	 * 該当データが見つからない場合は要素数0の <CODE>Parameter</CODE>
	 * 配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。 <BR>
	 * <BR>
	 * [パラメータ] *必須入力 <BR>
	 * <BR>
	 * <DIR>荷主コード <BR>
	 * </DIR> <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		// 予定検索
		param.setParameter(ListSortingConsignorBusiness.SEARCHCONSIGNOR_KEY, SortingParameter.SEARCH_SORTING_PLAN);

		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(SortingParameter.STATUS_FLAG_UNSTARTED);
		// 作業状態をセット
		param.setParameter(ListSortingConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);

		// 処理中画面->結果画面
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StartPickPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtPickPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtPickPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtPickPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStartPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 開始仕分予定日の検索ボタンが押下されたときに呼ばれます。 <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。 <BR>
	 * 該当データが見つからない場合は要素数0の <CODE>Parameter</CODE>
	 * 配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。 <BR>
	 * <BR>
	 * [パラメータ] *必須入力 <BR>
	 * <BR>
	 * <DIR>荷主コード* <BR>
	 * 開始仕分予定日 <BR>
	 * </DIR> <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_PSearchStartPlanDate_Click(ActionEvent e) throws Exception
	{
		// 仕分予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();

		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		// 開始仕分予定日
		param.setParameter(ListSortingDateBusiness.STARTSORTINGPLANDATE_KEY, WmsFormatter.toParamDate(this.txt_StrtPickPlanDate.getDate()));

		// 開始フラグをセット
		param.setParameter(ListSortingDateBusiness.RANGESORTINGPLANDATE_KEY, SortingParameter.RANGE_START);

		// 検索フラグ
		param.setParameter(ListSortingDateBusiness.SEARCHSORTINGPLANDATE_KEY, SortingParameter.SEARCH_SORTING_PLAN);

		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(SortingParameter.STATUS_FLAG_UNSTARTED);
		// 作業状態をセット
		param.setParameter(ListSortingDateBusiness.WORKSTATUSSORTINGPLANDATE_KEY, search);

		// 処理中画面->結果画面
		redirect(DO_SRCH_SORTINGPLANDATE, param, DO_SRCH_PROCESS);
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
	public void lbl_EndPickPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdPickPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdPickPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdPickPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 終了仕分予定日の検索ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。 <BR>
	 * 該当データが見つからない場合は要素数0の <CODE>Parameter</CODE>
	 * 配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。 <BR>
	 * <BR>
	 * [パラメータ] *必須入力 <BR>
	 * <BR>
	 * <DIR>荷主コード* <BR>
	 * 終了予定日 <BR>
	 * </DIR> <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_PSearchEndPlanDate_Click(ActionEvent e) throws Exception
	{
		// 仕分予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();

		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		// 終了予定日
		param.setParameter(ListSortingDateBusiness.ENDSORTINGPLANDATE_KEY, WmsFormatter.toParamDate(this.txt_EdPickPlanDate.getDate()));

		//　終了フラグをセット
		param.setParameter(ListSortingDateBusiness.RANGESORTINGPLANDATE_KEY, SortingParameter.RANGE_END);

		// 検索フラグ
		param.setParameter(ListSortingDateBusiness.SEARCHSORTINGPLANDATE_KEY, SortingParameter.SEARCH_SORTING_PLAN);

		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(SortingParameter.STATUS_FLAG_UNSTARTED);
		// 作業状態をセット
		param.setParameter(ListSortingDateBusiness.WORKSTATUSSORTINGPLANDATE_KEY, search);

		// 処理中画面->結果画面
		redirect(DO_SRCH_SORTINGPLANDATE, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickPlanDltLst_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUseEvent_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUseEvent_Change(ActionEvent e) throws Exception
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
	 * 表示ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:入力エリアの入力項目を条件に、ためうちに表示します。 <BR>
	 * <BR>
	 * <DIR>1.カーソルを作業者コードにセットします。 2.入力エリアの入力項目のチェックを行います。 <BR>
	 * <DIR>（必須入力、文字数、文字属性） <BR>
	 * (開始仕分予定日、終了仕分予定日より小さい) <BR>
	 * </DIR> 3.スケジュールを開始します。 <BR>
	 * 4.スケジュールの結果をためうちエリアに表示を行います。 <BR>
	 * 5.全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンを有効にします。 <BR>
	 * 6.入力エリアの条件内容を保持します。 <BR>
	 * </DIR> <BR>
	 * <BR>
	 * リストセルの列番号一覧 <BR>
	 * <DIR>1:選択 <BR>
	 * 2:仕分予定日 <BR>
	 * </DIR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		// ため打ち部分をすべて初期化します
		btn_ListClear_Click(e);

		// 入力チェックを行う（書式、必須、禁止文字）
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();

		// 開始仕分予定日が終了仕分予定日より小さいこと
		if (!StringUtil.isBlank(this.txt_StrtPickPlanDate.getDate()) && !StringUtil.isBlank(this.txt_EdPickPlanDate.getDate()))
		{
			if (this.txt_StrtPickPlanDate.getDate().after(this.txt_EdPickPlanDate.getDate()))
			{
				// 6023071 = 開始仕分予定日は、終了仕分予定日より前の日付にしてください。
				message.setMsgResourceKey("6023071");
				return;
			}
		}

		Connection conn = null;

		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// パラメータの宣言
			SortingParameter param = new SortingParameter();

			// スケジュールパラメータをセットします。
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 開始仕分予定日
			param.setFromPlanDate(WmsFormatter.toParamDate(this.txt_StrtPickPlanDate.getDate()));
			// 終了仕分予定日
			param.setToPlanDate(WmsFormatter.toParamDate(this.txt_EdPickPlanDate.getDate()));

			// スケジュールの宣言
			WmsScheduler schedule = new SortingPlanDeleteSCH();
			
			// スケジュールを開始する
			SortingParameter[] viewParam = (SortingParameter[]) schedule.query(conn, param);

			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了します。
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			// ViewStateに検索値を保持します。
			// 荷主コード
			this.getViewState().setString(KEY_CONSIGNOR_CODE, txt_ConsignorCode.getText());
			// 開始仕分予定日
			this.getViewState().setString(KEY_FROM_SORTING_PLAN_DATE, param.getFromPlanDate());
			// 終了仕分予定日
			this.getViewState().setString(KEY_TO_SORTING_PLAN_DATE, param.getToPlanDate());

			// ためうちに値を表示します。
			addList(viewParam);

			// ためうちのボタンを有効にします。
			setBtnTrueFalse(true);
			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());
		}
		catch (Exception ex)
		{
			// エラー発生時トランザクションをロールバックする。
			// 20050819 modify start T.Kishimoto
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
	 * クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:入力エリアをクリアします。 <BR>
	 * </BR> <DIR>1.入力エリアの項目をクリアします。 <BR>
	 * <DIR>作業者コード[そのまま] <BR>
	 * パスワード[そのまま] <BR>
	 * 荷主コード[検索値] <BR>
	 * 開始仕分予定日[クリア] <BR>
	 * 終了仕分予定日[クリア] <BR>
	 * 仕分予定削除一覧リスト発行する[ture] <BR>
	 * </DIR> 2.カーソルを作業者コードにセットします。 <BR>
	 * 3.ためうちエリアの内容は保持します。 <BR>
	 * </DIR> <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 各入力フィールドに初期値をセットします。
		txt_ConsignorCode.setText(getConsignorCode());
		this.txt_StrtPickPlanDate.setText("");
		this.txt_EdPickPlanDate.setText("");
		this.chk_CommonUseEvent.setChecked(true);

		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 全件選択ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの選択チェックボックスに全てチェックを入れます。 <BR>
	 * </BR> <DIR>1.選択boxが有効のものにチェックを入れます。 <BR>
	 * 2.カーソルを作業者コードにセットします。 <BR>
	 * 3.入力エリアの内容は保持します。 <BR>
	 * </DIR> <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		listCellCheckAllChange(true);

		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 全選択解除ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの選択チェックボックスに全てチェックをクリアします。 <BR>
	 * </BR> <DIR>1.選択boxが有効のもののチェックをクリアします。 <BR>
	 * 2.カーソルを作業者コードにセットします。 <BR>
	 * 3.入力エリアの内容は保持します。 <BR>
	 * </DIR> <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		listCellCheckAllChange(false);

		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Delete_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 削除ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、仕分予定情報の削除を行います。 <BR>
	 * </BR> <DIR>1.カーソルを作業者コードにセットします。 <BR>
	 * 2.viewStateから値を取得してパラメータにセットします。 <BR>
	 * 3.ためうちエリアの全情報をパラメータにセットします。 <BR>
	 * 4.スケジュールを開始します。 <BR>
	 * 5.ためうちエリアに、更新後のスケジュールからの情報を再取込みし、表示します。 <BR>
	 * 6.ためうち情報が存在しない場合、全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンは無効にします。 <BR>
	 * </DIR> <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);

		// 作業者コード,パスワードの入力チェックを行う。
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;

		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 削除するデータがあるか調べる
			boolean wCheck = false;

			// ためうちの要素数分チェックをかけ、更新を行う行のみVectorに値をセットしていく。
			Vector vecParam = new Vector(this.lst_PickingPlanDelete.getMaxRows());
			for (int i = 1; i < this.lst_PickingPlanDelete.getMaxRows(); i++)
			{
				// 操作する行を取得します。
				this.lst_PickingPlanDelete.setCurrentRow(i);

				SortingParameter param = new SortingParameter();

				if (!this.lst_PickingPlanDelete.getChecked(1))
				{
					continue;
				}
				else
				{
					wCheck = true;
				}

				// 端末No.
				UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
				param.setTerminalNumber(userHandler.getTerminalNo());
				// 作業者コード
				param.setWorkerCode(txt_WorkerCode.getText());
				// パスワードのセット
				param.setPassword(txt_Password.getText());
				// 仕分予定削除一覧リスト発行をするかをセット
				param.setSortingDeleteListFlg(this.chk_CommonUseEvent.getChecked());

				// 再表示用の検索条件のセット(荷主、開始仕分予定日、終了仕分予定日)
				param.setConsignorCode(this.getViewState().getString(KEY_CONSIGNOR_CODE));
				param.setFromPlanDate(this.getViewState().getString(KEY_FROM_SORTING_PLAN_DATE));
				param.setToPlanDate(this.getViewState().getString(KEY_TO_SORTING_PLAN_DATE));
				
				// 選択チェック条件
				param.setSelectBoxCheck(this.lst_PickingPlanDelete.getChecked(1));
				// 仕分予定日
				param.setPlanDate(WmsFormatter.toParamDate(this.lst_PickingPlanDelete.getValue(2), this.getHttpRequest().getLocale()));
				// ためうち行NO
				param.setRowNo(i);

				vecParam.addElement(param);
			}

			// 選択ボックスのチェックが入っていない
			if (wCheck == false)
			{
				// 6023111 = 削除するデータをチェックしてください。
				message.setMsgResourceKey("6023111");
				return;
			}

			// パラメータの宣言
			SortingParameter[] paramArray = new SortingParameter[vecParam.size()];
			// パラメータに値をコピーします
			vecParam.copyInto(paramArray);

			// スケジュールスタート
			WmsScheduler schedule = new SortingPlanDeleteSCH();

			SortingParameter[] viewParam = (SortingParameter[]) schedule.startSCHgetParams(conn, paramArray);

			// 返却データがnullならば更新に失敗。
			// ロールバックとメッセージのセットを行う。(ためうちは前データを表示したまま)
			if (viewParam == null)
			{
				// ロールバック
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			// 返却データの要素数が0以上ならば更新に成功。
			// トランザクションの確定とためうちの再表示を行う。
			else if (viewParam.length >= 0)
			{
				// コミット
				conn.commit();

				// 表示するデータがない場合
				if (viewParam.length == 0)
				{
					// ためうち部をクリアします
					btn_ListClear_Click(e);
				}
				else
				{
					// スケジュールが正常に完了して表示データを取得した場合、表示します。
					this.lst_PickingPlanDelete.clearRow();
					// ためうちに再表示します
					addList(viewParam);
				}
			}

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			// エラー発生時トランザクションをロールバックする。
			// 20050819 modify start T.Kishimoto
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
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 一覧クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの表示をクリアするか、ダイアログボックスを表示し、確認します。 <BR>
	 * </BR> <DIR>1.確認ダイアログボックスを表示します。"一覧入力情報がクリアされます。宜しいですか?" <BR>
	 * [確認ダイアログ OK] <BR>
	 * <DIR>1.リストセルを初期化します。 <BR>
	 * 2.ためうちエリアの条件を消します。 <BR>
	 * 3.全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンを無効にします。 <BR>
	 * 4.カーソルを作業者コードにセットします。 <BR>
	 * </DIR> [確認ダイアログ キャンセル] <BR>
	 * <DIR>なにもしません <BR>
	 * </DIR> </DIR> <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// 行情報を全て削除します。
		this.lst_PickingPlanDelete.clearRow();
		// ためうち部のボタンを無効にします。
		setBtnTrueFalse(false);
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanDelete_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanDelete_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanDelete_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanDelete_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanDelete_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanDelete_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_PickingPlanDelete_Click(ActionEvent e) throws Exception
	{
	}
}
//end of class
