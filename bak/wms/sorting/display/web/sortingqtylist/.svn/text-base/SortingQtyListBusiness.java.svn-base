//$Id: SortingQtyListBusiness.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingqtylist;
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
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem.ListSortingItemBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingqtylist.ListSortingQtyListBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingresultdate.ListSortingResultDateBusiness;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;
import jp.co.daifuku.wms.sorting.schedule.SortingQtyListSCH;

/**
 * Designer : D.Hakui<BR>
 * Maker : D.Hakui <BR>
 * <BR>
 * 仕分実績リスト発行画面クラスです。<BR>
 * 仕分実績リスト発行を行うスケジュールにパラメータを引き渡します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_PDisplay_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 *  画面から入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
 *  スケジュールから検索結果を受け取り、仕分実績リスト一覧画面を呼出します<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   荷主コード*<BR>
 *   仕分日<BR>
 *   商品コード<BR>
 *   ケース／ピース区分*<BR>
 *   クロス／DC*<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.印刷ボタン押下処理(<CODE>btn_Print_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 *  画面から入力した内容をパラメータにセットし、<BR>
 *  そのパラメータを基にスケジュールがデータを検索し印刷します。 <BR>
 *  スケジュールは印刷に成功した場合はtrue、失敗した場合はfalseを返します。<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   荷主コード*<BR>
 *   仕分日<BR>
 *   商品コード<BR>
 *   ケース／ピース区分<BR>
 *   クロス／DC<BR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>D.Hakui</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class SortingQtyListBusiness extends SortingQtyList implements WMSConstants
{
	// 遷移先アドレス
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";
	// 商品検索ポップアップ呼び出しアドレス
	protected static final String DO_SRCH_SORTINGITEM = "/sorting/listbox/listsortingitem/ListSortingItem.do";
	// 結果画面ポップアップ呼び出しアドレス
	protected static final String DO_SRCH_SORTINGLIST = "/sorting/listbox/listsortingqtylist/ListSortingQtyList.do";
	// どのコントロールから呼び出されたダイアログかを保持：印刷ボタン
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";	
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * 概要:画面の初期表示を行います。クリアボタンでpage_loadは呼ばない。 <BR>
	 * <BR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 荷主コード [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 仕分日 [なし]<BR>
	 * 商品コード [なし]<BR>
	 * ケース／ピース区分<BR>
	 * クロス／DC<BR>
	 * </DIR>
	 *
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 各入力フィールドに初期値をセットします。
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		// 仕分予定日
		txt_PickingDate.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// ケース／ピース区分
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		// クロス／DC区分
		rdo_CrossDCFlagAll.setChecked(true);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);
		
		// 荷主コードにカーソル遷移
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
			// 荷主コードにフォーカスセット
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
			WmsScheduler schedule = new SortingQtyListSCH();
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
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if(menuparam != null)
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
			btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
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
		// 仕分日
		Date pickingdate = WmsFormatter.toDate(param.getParameter(ListSortingResultDateBusiness.SORTINGDATE_KEY), this.getHttpRequest().getLocale());
		// 商品コード
		String itemcode = param.getParameter(ListSortingItemBusiness.ITEMCODE_KEY);
		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 仕分日
		if (!StringUtil.isBlank(pickingdate))
		{
			txt_PickingDate.setDate(pickingdate);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		// 荷主コードにフォーカスセット
		setFocus(txt_ConsignorCode);
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/** 
	 * 入力エリアの入力値をセットしたパラメータオブジェクトを生成します。<BR>
	 * 
	 * @return 入力エリアの入力値を含んだパラメータオブジェクト
	 */
	protected SortingParameter createParameter()
	{
		// スケジュールパラメータをセットする
		SortingParameter param = new SortingParameter();
		param = new SortingParameter();
		
		// 荷主コード
		param.setConsignorCode(txt_ConsignorCode.getText());
		// 仕分日
		param.setSortingDate(WmsFormatter.toParamDate(txt_PickingDate.getDate()));
		// 商品コード
		param.setItemCode(txt_ItemCode.getText());	
		// ケース／ピース区分				
		if (rdo_CpfAll.getChecked())
		{
			param.setCasePieceFlag(
				SortingParameter.CASEPIECE_FLAG_ALL);
		}
		else if (rdo_CpfCase.getChecked())
		{
			param.setCasePieceFlag(
				SortingParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			param.setCasePieceFlag(
				SortingParameter.CASEPIECE_FLAG_PIECE);
		}

		// クロス/ＤＣ
		// 全て
		if (rdo_CrossDCFlagAll.getChecked())
		{
			param.setTcdcFlag(
					SortingParameter.TCDC_FLAG_ALL);
		}
		// クロス
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			param.setTcdcFlag(
					SortingParameter.TCDC_FLAG_CROSSTC);
		}
		// ＤＣ
		else if (rdo_CrossDCFlagDC.getChecked())
		{
			param.setTcdcFlag(
					SortingParameter.TCDC_FLAG_DC);
		}
		return param;	

	}
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
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			SortingParameter param = new SortingParameter();

			// スケジュールから荷主コードを取得する。
			WmsScheduler schedule = new SortingQtyListSCH();
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
			// コネクションのクローズを行う
			if (conn != null)
			{
				conn.close();
			}
		}
		return "";
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
	 * メニューボタンを押下したときに呼ばれます。<BR>
	 * 概要：メニュー画面に遷移します<BR>
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
	 * 荷主検索ボタンを押下したときに呼ばれます。<BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし<BR>
	 * その検索条件で荷主一覧検索リストボックスを表示します。<BR>
	 * [パラメータ] *必須入力
	 * <DIR>
	 * 荷主コード<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListSortingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 検索フラグ
		param.setParameter(
			ListSortingConsignorBusiness.SEARCHCONSIGNOR_KEY,
			SortingParameter.SEARCH_SORTING_QTY);
		// 処理中画面->結果画面
		redirect(
			"/sorting/listbox/listsortingconsignor/ListSortingConsignor.do",
			param,
			DO_SRCH_PROCESS);	
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_PickingDate_Server(ActionEvent e) throws Exception
	{
	}
	
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingDate_Server(ActionEvent e) throws Exception
	{
	}
	
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingDate_EnterKey(ActionEvent e) throws Exception
	{
	}
	
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingDate_TabKey(ActionEvent e) throws Exception
	{
	}
	
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingDate_InputComplete(ActionEvent e) throws Exception
	{
	}
	
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPickingDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕分日検索ボタンを押下したときに呼ばれます。<BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし<BR>
	 * その検索条件で仕分日一覧検索リストボックスを表示します。<BR>
	 * [パラメータ] *必須入力
	 * <DIR>
	 * 荷主コード<BR>
	 * 仕分予定日<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchPickingDate_Click(ActionEvent e) throws Exception
	{
		//仕分日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		//荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 仕分日
		param.setParameter(ListSortingResultDateBusiness.SORTINGDATE_KEY, WmsFormatter.toParamDate(txt_PickingDate.getDate()));

		// 処理中画面->結果画面
		redirect(
			"/sorting/listbox/listsortingresultdate/ListSortingResultDate.do",
			param,
			DO_SRCH_PROCESS);
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
	 * 商品検索ボタンを押下したときに呼ばれます。<BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし<BR>
	 * その検索条件で商品一覧検索リストボックスを表示します。<BR>
	 * [パラメータ] *必須入力
	 * <DIR>
	 * 荷主コード<BR>
	 * 仕分日<BR>
	 * 商品コード<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 仕分日
		param.setParameter(ListSortingResultDateBusiness.SORTINGDATE_KEY, WmsFormatter.toParamDate(txt_PickingDate.getDate()));
		// 商品コード
		param.setParameter(ListSortingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 検索フラグ
		param.setParameter(
			ListSortingItemBusiness.SEARCHITEM_KEY,
			SortingParameter.SEARCH_SORTING_QTY);
		// 処理中画面->結果画面
		redirect(
			"/sorting/listbox/listsortingitem/ListSortingItem.do",
			param,
			DO_SRCH_PROCESS);
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
	public void btn_Display_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 表示ボタン押下時に呼ばれます。<BR>
	 * 概要：入力エリアの入力項目をパラメータのセットし別画面で仕分実績一覧<BR>
	 * リストボックスを表示します<BR><BR>
	 * [パラメータ] *必須入力<BR>
	 * <DIR>
	 * 荷主コード*<BR>
	 * 仕分日<BR>
	 * 商品コード<BR>
	 * ケース／ピース区分<BR>
	 * クロス／DC<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Display_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		// 荷主コードをセット
		forwardParam.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 仕分日をセット
		forwardParam.setParameter(ListSortingResultDateBusiness.SORTINGDATE_KEY, WmsFormatter.toParamDate(txt_PickingDate.getDate()));
		// 商品コードをセット
		forwardParam.setParameter(ListSortingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		// ケース/ピース区分
		// 全て
		if (rdo_CpfAll.getChecked())
		{
			forwardParam.setParameter(
				ListSortingQtyListBusiness.CASEPIECEFLAG_KEY,
				SortingParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		else if (rdo_CpfCase.getChecked())
		{
			forwardParam.setParameter(
					ListSortingQtyListBusiness.CASEPIECEFLAG_KEY,
					SortingParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_CpfPiece.getChecked())
		{
			forwardParam.setParameter(
					ListSortingQtyListBusiness.CASEPIECEFLAG_KEY,
					SortingParameter.CASEPIECE_FLAG_PIECE);
		}

		// クロス/ＤＣ
		// 全て
		if (rdo_CrossDCFlagAll.getChecked())
		{
			forwardParam.setParameter(
					ListSortingQtyListBusiness.CROSSDC_KEY,
					SortingParameter.TCDC_FLAG_ALL);
		}
		// クロス
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			forwardParam.setParameter(
					ListSortingQtyListBusiness.CROSSDC_KEY,
					SortingParameter.TCDC_FLAG_CROSSTC);
		}
		// DC
		else if (rdo_CrossDCFlagDC.getChecked())
		{
			forwardParam.setParameter(
					ListSortingQtyListBusiness.CROSSDC_KEY,
					SortingParameter.TCDC_FLAG_DC);
		}
	
		
		// 仕分実績リスト一覧リストボックスを表示する。
		redirect(
				DO_SRCH_SORTINGLIST,
				forwardParam, 
				DO_SRCH_PROCESS
				);
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
	 * 印刷ボタン押下時に呼ばれます。<BR>
	 * 概要：入力エリアの入力項目をパラメータにセットし印刷スケジュールを開始します。<BR>
	 * スケジュールは印刷に成功した場合はtrue、印刷に失敗した場合はfalseを返します。<BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		// 荷主コードにフォーカスセット
		setFocus(txt_ConsignorCode);
		// 入力チェックを行います。
		txt_ConsignorCode.validate();
		txt_PickingDate.validate();
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
			WmsScheduler schedule = new SortingQtyListSCH();
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
	 * クリアボタン押下時に呼ばれます。<BR>
	 * 概要：入力エリアをクリアします。<BR><BR>
	 * 画面の初期化を行います。 <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 初期値セット
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		// 仕分日
		txt_PickingDate.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// ケース／ピース区分
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		// クロス／DC区分
		rdo_CrossDCFlagAll.setChecked(true);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);
		// 荷主コードにフォーカスセット
		setFocus(txt_ConsignorCode);
	}

}
//end of class
