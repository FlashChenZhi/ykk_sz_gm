// $Id: SortingPlanInquiryBusiness.java,v 1.1.1.1 2006/08/17 09:34:32 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingplaninquiry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
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
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingdate.ListSortingDateBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem.ListSortingItemBusiness;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;
import jp.co.daifuku.wms.sorting.schedule.SortingPlanInquirySCH;

/**
 * Designer : D.Hakui <BR>
 * Maker : D.Hakui <BR>
 * <BR>
 * 仕分予定照会画面クラスです。<BR>
 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
 * スケジュールからためうちエリア出力用のデータを受け取り、ためうちエリアに出力します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理（<CODE>btn_View_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
 *   該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
 * <BR>
 *   [パラメータ] *必須入力<BR>
 * <BR>
 *     <DIR>
 *     荷主コード* <BR>
 *     仕分予定日* <BR>
 *     商品コード* <BR>
 *     ケース／ピース区分* <BR>
 *     クロス／ＤＣ区分* <BR>
 *     作業状態フラグ* <BR>
 *     </DIR>
 * <BR>
 *   [返却データ] <BR>
 * <BR>
 *    <DIR>
 *    荷主コード <BR>
 *    荷主名称 <BR>
 *    仕分予定日 <BR>
 *    商品コード <BR>
 *    商品名称 <BR>
 *    ケース／ピース区分 <BR>
 *    クロス／ＤＣ区分 <BR>
 *    出荷先コード <BR>
 *    出荷先名称 <BR>
 *    仕分場所 <BR>
 *    ケース入数 <BR>
 *    ボール入数 <BR>
 *    ホスト予定ケース数 <BR>
 *    ホスト予定ピース数 <BR>
 *    作業状態 <BR>
 * 	  状態名称 <BR>
 * 	  ケース仕分場所 <BR>
 * 	  ピース仕分場所 <BR>
 *    仕入先コード <BR>
 *    仕入先名称 <BR>
 *    ケースITF <BR>
 *    ボールITF <BR>
 *    出荷伝票No <BR>
 *    出荷伝票行No <BR>
 *    入荷伝票No <BR>
 *    入荷伝票行No <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>D.Hakui</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:32 $
 * @author  $Author: mori $
 */
public class SortingPlanInquiryBusiness extends SortingPlanInquiry implements WMSConstants
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
	protected static final String DO_SRCH_SORTINGLIST = "/sorting/listbox/listsortingplanregist/ListSortingPlanRegist.do";
	
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 *   <DIR>
	 *   荷主コード[荷主コード] <BR>
	 *     <DIR>
	 *     （予定情報の未作業荷主が一件の場合） <BR>
	 *     </DIR>
	 *   仕分予定日[なし] <BR>
	 *   商品コード[なし] <BR>
	 *   ケース／ピース区分[全て] <BR>
	 *   クロス／ＤＣ[全て] <BR>
	 *   作業状態 [全て]<BR>
	 *   </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 入力エリア、ためうちエリアの初期化を行います。
		setInitDsp(true);
	}
	
	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * <BR>
	 * 概要:このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	
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
		// 荷主コードにフォーカスをセットします
		setFocus(txt_ConsignorCode);
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<BR><CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
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
		Date pickingplandate = 
			WmsFormatter.toDate(
			param.getParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY),
			this.getHttpRequest().getLocale());
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
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/** 
	 * 入力エリアの初期化を行います。
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setInitDsp(boolean detailclearflg) throws Exception
	{
		// 画面項目をクリアします。
		if (detailclearflg)
		{
			// 荷主コード（ためうち）
			txt_JavaSetConsignorCode.setText("");
			// 荷主名称（ためうち）
			txt_JavaSetConsignorName.setText("");
			// 商品コード（ためうち）
			txt_JavaSetItemCode.setText("");
			// 商品名称（ためうち）
			txt_JavaSetItemName.setText("");
			// 仕分予定日（ためうち）
			txt_JavaSetPickingPlanDate.setText("");
			// リストセル
			lst_SortingPlanInquiry.clearRow();	
		}
		
		// 荷主コード
		txt_ConsignorCode.setText("");
		// 仕分予定日
		txt_PickingPlanDate.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// ケース／ピース区分
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		rdo_CpfCasePiece.setChecked(false);
		// クロス／DC
		rdo_CrossDCFlagAll.setChecked(true);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);
		// 作業状態
		pul_WorkStatusSorting.setSelectedIndex(0);

		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);

		Connection conn = null;

		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			WmsScheduler schedule = new SortingPlanInquirySCH();
			SortingParameter param = (SortingParameter)schedule.initFind(conn, null);
			
			// 荷主コードが1件の場合、初期表示させる。
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}
		
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
			try
			{
				// コネクションクローズ
				if (conn != null)
					conn.close();
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
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
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
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
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 *  画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 *  該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ]<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     </DIR>
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
		param.setParameter(
			ListSortingConsignorBusiness.CONSIGNORCODE_KEY,	
			txt_ConsignorCode.getText());
		
		// 画面情報
		param.setParameter(
			ListSortingConsignorBusiness.SEARCHCONSIGNOR_KEY,
			SortingParameter.SEARCH_SORTING_WORK);
			
		// 作業状態(未開始、作業中、一部完了、完了)
		String[] search = new String[4];
		search[0] = new String(SortingParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(SortingParameter.STATUS_FLAG_WORKING);
		search[2] = new String(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		search[3] = new String(SortingParameter.STATUS_FLAG_COMPLETION);
		// 作業状態をセット
		param.setParameter(ListSortingConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
								
		// 処理中画面->結果画面
		redirect(
			DO_SRCH_CONSIGNOR,
			param,
			DO_SRCH_PROCESS);
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
	 * 仕分予定日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ]<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     仕分予定日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchPickingPlanDate_Click(ActionEvent e) throws Exception
	{
		// 仕分予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();

		// 荷主コード
		param.setParameter(
			ListSortingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 仕分予定日
		param.setParameter(
			ListSortingDateBusiness.SORTINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));

		// 検索フラグ
		param.setParameter(ListSortingDateBusiness.SEARCHSORTINGPLANDATE_KEY, SortingParameter.SEARCH_SORTING_WORK);

		// 作業状態(未開始、作業中、一部完了、完了)
		String[] search = new String[4];
		search[0] = new String(SortingParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(SortingParameter.STATUS_FLAG_WORKING);
		search[2] = new String(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		search[3] = new String(SortingParameter.STATUS_FLAG_COMPLETION);
		// 作業状態をセット
		param.setParameter(ListSortingDateBusiness.WORKSTATUSSORTINGPLANDATE_KEY, search);

		// 処理中画面->結果画面
		redirect(
				DO_SRCH_SORTINGPLANDATE,
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
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ]<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     仕分予定日 <BR>
	 *     商品コード <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListSortingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 仕分予定日
		param.setParameter(
			ListSortingDateBusiness.SORTINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
			
		// 商品コード
		param.setParameter(
			ListSortingItemBusiness.ITEMCODE_KEY,
			txt_ItemCode.getText());
			
		// 画面情報
		param.setParameter(
			ListSortingItemBusiness.SEARCHITEM_KEY,
			SortingParameter.SEARCH_SORTING_WORK);
			
		// 作業状態(未開始、作業中、一部完了、完了)
		String[] search = new String[4];
		search[0] = new String(SortingParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(SortingParameter.STATUS_FLAG_WORKING);
		search[2] = new String(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		search[3] = new String(SortingParameter.STATUS_FLAG_COMPLETION);
		// 作業状態をセット
		param.setParameter(ListSortingItemBusiness.WORKSTATUSITEM_KEY, search);
						
		// 処理中画面->結果画面
		redirect(
			DO_SRCH_SORTINGITEM,
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
	public void rdo_CpfCasePiece_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCasePiece_Click(ActionEvent e) throws Exception
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
	public void lbl_WorkStatus_Server(ActionEvent e) throws Exception
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
	 * 概要:入力エリアの入力項目を条件に、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *     以下の処理を行います。 <BR>
	 *     1.入力エリアの入力項目のチェックを行います。 <BR>
	 *       <DIR>
	 *      （必須入力、文字数、文字属性） <BR>
	 *       </DIR>
	 *     2.スケジュールを開始します。 <BR>
	 *       <DIR>
	 *       [パラメータ] *必須入力<BR>
	 *       <BR>
	 *         <DIR>
	 *         荷主コード* <BR>
	 *         仕分予定日* <BR>
	 *         商品コード* <BR>
	 *         ケース／ピース区分* <BR>
	 *         クロス／ＤＣ区分* <BR>
	 *         作業状態フラグ* <BR>
	 *         </DIR>
	 *       </DIR>
	 *     3.スケジュールの結果をためうちエリアに表示を行います。 <BR>
	 *     4.カーソルを荷主コードにセットします。 <BR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * リストセルの列番号一覧 <BR>
	 * <DIR>
	 * 	   1:ｹｰｽ/ﾋﾟｰｽ <BR>
	 *     2:ｸﾛｽ/DC <BR>
	 *     3:出荷先ｺｰﾄﾞ <BR>
	 *     4:ｹｰｽ入数 <BR>
	 *     5:ﾎｽﾄ予定ｹｰｽ数 <BR>
	 *     6:実績ｹｰｽ数 <BR>
	 *     7:状態 <BR>
	 * 	   8:ｹｰｽ仕分場所 <BR>
	 *     9:仕入先ｺｰﾄﾞ　<BR>
	 *    10:ｹｰｽITF <BR>
	 *    11:出荷伝票No. <BR>
	 *    12:入荷伝票No. <BR>
	 *    13:出荷先名称 <BR>
	 *    14:ﾎﾞｰﾙ入数 <BR>
	 *    15:ﾎｽﾄ予定ﾋﾟｰｽ数 <BR>
	 *    16:実績ﾋﾟｰｽ数 <BR>
	 *    17:ﾋﾟｰｽ仕分場所 <BR>
	 *    18:仕入先名称 <BR>
	 *    19:ﾎﾞｰﾙITF <BR>
	 *    20:出荷伝票行No. <BR>
	 *    21:入荷伝票行No. <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);

		// ためうちエリアをクリアする
		lst_SortingPlanInquiry.clearRow();
		txt_JavaSetConsignorCode.setText("");
		txt_JavaSetConsignorName.setText("");
		txt_JavaSetItemCode.setText("");
		txt_JavaSetItemName.setText("");
		txt_JavaSetPickingPlanDate.setText("");
		
		// 入力チェックを行う（書式、必須、禁止文字）
		// 荷主コード
		txt_ConsignorCode.validate();
		// 仕分予定日
		txt_PickingPlanDate.validate();
		// 商品コード
		txt_ItemCode.validate();
		// 作業状態
		pul_WorkStatusSorting.validate(false);
		
		Connection conn = null;
		
		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			// スケジュールパラメータをセットする
			SortingParameter param = new SortingParameter();
			
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
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
			// ケース・ピース
			else if (rdo_CpfCasePiece.getChecked())
			{
				param.setCasePieceFlag(SortingParameter.CASEPIECE_FLAG_MIXED);
			}

			// クロス／ＤＣ
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
			// ＤＣ
			else if (rdo_CrossDCFlagDC.getChecked())
			{
				param.setTcdcFlag(SortingParameter.TCDC_FLAG_DC);
			}

			// 作業状態
			// 全て
			if (pul_WorkStatusSorting.getSelectedIndex() == 0)
			{
				param.setStatusFlag(SortingParameter.STATUS_FLAG_ALL);
			}
			// 未開始
			else if (pul_WorkStatusSorting.getSelectedIndex() == 1)
			{
				param.setStatusFlag(SortingParameter.STATUS_FLAG_UNSTARTED);
			}
			// 作業中
			else if (pul_WorkStatusSorting.getSelectedIndex() == 2)
			{
				param.setStatusFlag(SortingParameter.STATUS_FLAG_WORKING);
			}
			// 一部完了
			else if (pul_WorkStatusSorting.getSelectedIndex() == 3)
			{
				param.setStatusFlag(SortingParameter.STATUS_FLAG_PARTIAL_COMPLETION);
			}
			// 完了
			else if (pul_WorkStatusSorting.getSelectedIndex() == 4)
			{
				param.setStatusFlag(SortingParameter.STATUS_FLAG_COMPLETION);
			}

		
			// スケジュールを開始する
			WmsScheduler schedule = new SortingPlanInquirySCH();
			SortingParameter[] viewParam = (SortingParameter[])schedule.query(conn, param);
			
			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了する。
			if( viewParam == null || viewParam.length == 0 )
			{
				// コネクションクローズ
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			// スケジュールが正常に完了して表示データを取得した場合、表示する。
			// 荷主コード（ためうち）
			txt_JavaSetConsignorCode.setText(viewParam[0].getConsignorCode());
			// 荷主名（ためうち）
			txt_JavaSetConsignorName.setText(viewParam[0].getConsignorName());
			// 商品コード（ためうち）
			txt_JavaSetItemCode.setText(viewParam[0].getItemCode());
			// 商品名（ためうち）
			txt_JavaSetItemName.setText(viewParam[0].getItemName());
			// 仕分予定日（ためうち）
			txt_JavaSetPickingPlanDate.setDate(WmsFormatter.toDate(viewParam[0].getPlanDate()));
			
			// リストセルに値をセットする
			// 出荷先名称
			String label_customername = DisplayText.getText("LBL-W0036");
			// 仕入先コード
			String label_suppliercode = DisplayText.getText("LBL-W0251");
			// 仕入先名称
			String label_suppliername = DisplayText.getText("LBL-W0253");
			// ケース仕分場所
			String label_casesortinglocation = DisplayText.getText("LBL-W0013");
			// ピース仕分場所
			String label_piecesortinglocation = DisplayText.getText("LBL-W0149");
			// ケースITF
			String label_caseitf = DisplayText.getText("LBL-W0338");
			// ボールITF
			String label_bundleitf = DisplayText.getText("LBL-W0337");
			// 出荷伝票No
			String label_shippingticketno = DisplayText.getText("LBL-W0206");
			// 出荷伝票行No
			String label_shippinglineno = DisplayText.getText("LBL-W0205");
			// 入荷伝票No
			String label_instockticketno = DisplayText.getText("LBL-W0091");
			// 入荷伝票行No
			String label_instocklineno = DisplayText.getText("LBL-W0090");

			for(int i = 0; i < viewParam.length; i++)
			{

				// 行追加
				lst_SortingPlanInquiry.addRow();
				lst_SortingPlanInquiry.setCurrentRow(i + 1);
	
				int j = 1;
				
				// 検索結果を各セルにセットしていく
				// （1列目）
				// ｹｰｽ/ﾋﾟｰｽ
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getCasePieceFlag());
				// ｸﾛｽ/DC
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getTcdcFlag());
				// 出荷先ｺｰﾄﾞ
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getCustomerCode());
				// ｹｰｽ入数
				lst_SortingPlanInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
				// ﾎｽﾄ予定ｹｰｽ数
				lst_SortingPlanInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				// 実績ｹｰｽ数
				lst_SortingPlanInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getResultCaseQty()));
				// 状態
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getStatusName());
				// ｹｰｽ仕分場所
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getCaseSortingLocation());
				// 仕入先ｺｰﾄﾞ
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getSupplierCode());
				// ｹｰｽITF
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getITF());
				// 出荷伝票No
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getShippingTicketNo());
				// 入荷伝票No
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getInstockTicketNo());
				
				// (２列目)
				// 出荷先名称
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getCustomerName());
				// ﾎﾞｰﾙ入数
				lst_SortingPlanInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				// ﾎｽﾄ予定ﾋﾟｰｽ数
				lst_SortingPlanInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				// 実績ﾋﾟｰｽ数
				lst_SortingPlanInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getResultPieceQty()));
				// ﾋﾟｰｽ仕分場所
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getPieceSortingLocation());
				// 仕入先名称
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getSupplierName());
				// ﾎﾞｰﾙITF
				lst_SortingPlanInquiry.setValue(j++, viewParam[i].getBundleITF());
				// 出荷伝票行No
				lst_SortingPlanInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getShippingLineNo()));
				// 入荷伝票行No
				lst_SortingPlanInquiry.setValue(j++, WmsFormatter.getNumFormat(viewParam[i].getInstockLineNo()));
				
				//tool tip（バルーン項目）をセット
				ToolTipHelper toolTip = new ToolTipHelper();
				// 出荷先名称
				toolTip.add(label_customername, viewParam[i].getCustomerName());
				// 仕入先コード
				toolTip.add(label_suppliercode, viewParam[i].getSupplierCode());
				// 仕入先名称
				toolTip.add(label_suppliername, viewParam[i].getSupplierName());
				// ケース仕分場所
				toolTip.add(label_casesortinglocation, viewParam[i].getCaseSortingLocation());
				// ピース仕分場所
				toolTip.add(label_piecesortinglocation, viewParam[i].getPieceSortingLocation());
				// ケースITF
				toolTip.add(label_caseitf, viewParam[i].getITF());
				// ボールITF
				toolTip.add(label_bundleitf, viewParam[i].getBundleITF());
				// 出荷伝票No
				toolTip.add(label_shippingticketno, viewParam[i].getShippingTicketNo());
				// 出荷伝票行No
				toolTip.add(label_shippinglineno, WmsFormatter.getNumFormat(viewParam[i].getShippingLineNo()));
				// 入荷伝票No
				toolTip.add(label_instockticketno, viewParam[i].getInstockTicketNo());
				// 入荷伝票行No
				toolTip.add(label_instocklineno, WmsFormatter.getNumFormat(viewParam[i].getInstockLineNo()));
				
				lst_SortingPlanInquiry.setToolTip(i+1, toolTip.getText());
			}
			
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
	 * クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:入力エリアをクリアします。 <BR>
	 * </BR>
	 * <DIR>
	 *     1.入力エリアの項目を初期値に戻します。
	 *     初期値については<CODE>setInitDsp(boolean detailclearflg)</CODE>メソッドを参照してください。
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 入力エリアを初期化します(ためうちエリアはそのままです)
		setInitDsp(false);
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
	public void lbl_SPickingPlanDate_Server(ActionEvent e) throws Exception
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
	public void lst_SortingPlanInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanInquiry_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanInquiry_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingPlanInquiry_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusSorting_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusSorting_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPickingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetPickingPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetItemCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetItemCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetItemCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_JavaSetItemName_InputComplete(ActionEvent e) throws Exception
	{
	}


}
//end of class
