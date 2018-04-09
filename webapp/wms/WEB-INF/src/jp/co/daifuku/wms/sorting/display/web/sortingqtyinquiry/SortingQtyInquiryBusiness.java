// $Id: SortingQtyInquiryBusiness.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingqtyinquiry;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Locale;

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
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem.ListSortingItemBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingresultdate.ListSortingResultDateBusiness;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;
import jp.co.daifuku.wms.sorting.schedule.SortingQtyInquirySCH;

/**
 * Designer : D.Hakui<BR>
 * Maker : D.Hakui<BR>
 * <BR>
 * 仕分実績照会画面クラスです。<BR>
 * 仕分実績照会処理を行うスケジュールにパラメータを引き渡します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_View_Click</CODE>)<BR>
 * <BR>
 * <DIR>
 * 	画面から入力した内容をパラメータにセットし、
 * 	そのパラメータを基にスケジュールが表示用のデータを検索し、ポップアップ画面で表示します。<BR>
 * 	<BR>
 * 	[パラメータ] *必須入力<BR>
 * 	<DIR>
 *	荷主コード*<BR>
 *	仕分日<BR>
 *	商品コード<BR>
 *	ケース／ピース区分*<BR>
 *		<DIR>
 *   	全て<BR>
 *   	ケース<BR>
 *   	ピース<BR>
 *  	</DIR>
 *	クロス／DC*<BR>
 *		<DIR>
 *   	全て<BR>
 *   	クロス<BR>
 *   	DC<BR>
 *  	</DIR>
 * <BR>
 *   [出力用のデータ] <BR>
 * 	 <DIR>
 *   <BR>
 *		仕分日<BR>
 *		仕分予定日<BR>
 *		クロス／DC <BR>
 *		ケース／ピース <BR>
 *		出荷先コード <BR>
 *		出荷先名称<BR>
 *		ケース入数 <BR>
 *		ボール入数 <BR>
 *		作業予定ケース数<BR>
 *		作業予定ピース数<BR>
 *		実績ケース数<BR>
 *		実績ピース数<BR>
 *		欠品ケース数<BR>
 *		欠品ピース数<BR>
 *		仕分場所<BR>
 *		仕入先コード<BR>
 *		仕入先名称 <BR>
 *		作業者コード<BR>
 *		作業者名 <BR>
 * 	   </DIR>
 * 	  </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/15</TD><TD>D.Hakui</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class SortingQtyInquiryBusiness extends SortingQtyInquiry implements WMSConstants
{
	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/sorting/listbox/listsortingconsignor/ListSortingConsignor.do";
	// 仕分日検索ポップアップアドレス
	protected static final String DO_SRCH_SORTING_RESULT_DATE = "/sorting/listbox/listsortingresultdate/ListSortingResultDate.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";
	// 商品検索ポップアップ呼び出しアドレス
	protected static final String DO_SRCH_SORTING_ITEM = "/sorting/listbox/listsortingitem/ListSortingItem.do";
	// 結果画面ポップアップ呼び出しアドレス
	protected static final String DO_SRCH_SORTING_LIST = "/sorting/listbox/listsortingplanregist/ListSortingPlanRegist.do";
	
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
	 *   仕分日[なし] <BR>
	 *   商品コード[なし] <BR>
	 *   ケース／ピース区分[全て] <BR>
	 *   クロス／ＤＣ区分 [全て]<BR>
	 *   </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		// 荷主コード（ためうち）
		txt_JavaSetConsignorCode.setText("");
		// 荷主名称（ためうち）
		txt_JavaSetConsignorName.setText("");
		// 仕分日（ためうち）
		txt_JavaSetPickingPlanDate.setText("");
		// 商品コード（ためうち）
		txt_JavaSetItemCode.setText("");
		// 商品名称（ためうち）
		txt_JavaSetItemName.setText("");
		// リストセル
		lst_SortingQtyInquiry.clearRow();
		
		setInitDsp();

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
	}
	
	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorCode = param.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);
		// 仕分日
		Date sortingDate = 
			WmsFormatter.toDate(
			param.getParameter(ListSortingResultDateBusiness.SORTINGDATE_KEY),
			this.getHttpRequest().getLocale());
		// 商品コード
		String itemCode = param.getParameter(ListSortingItemBusiness.ITEMCODE_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		// 仕分日
		if (!StringUtil.isBlank(sortingDate))
		{
			txt_PickingDate.setDate(sortingDate);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemCode))
		{
			txt_ItemCode.setText(itemCode);
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
	private void setInitDsp() throws Exception
	{
		
		// 荷主コード
		txt_ConsignorCode.setText("");
		// 仕分日
		txt_PickingDate.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// ケース／ピース区分
		rdo_CpfAll.setChecked(true);
		rdo_CpfCase.setChecked(false);
		rdo_CpfPiece.setChecked(false);
		// クロス／DC
		rdo_CrossDCFlagAll.setChecked(true);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);

		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);

		Connection conn = null;

		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			WmsScheduler schedule = new SortingQtyInquirySCH();
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
	 * メニューボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:メニュー画面に遷移します。<BR>
	 * <BR>
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
			SortingParameter.SEARCH_SORTING_QTY);
								
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
	public void btn_PSearchPickingDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕分日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ]<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     仕分日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchPickingDate_Click(ActionEvent e) throws Exception
	{
		// 仕分日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();

		// 荷主コード
		param.setParameter(
			ListSortingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 仕分日
		param.setParameter(
			ListSortingResultDateBusiness.SORTINGDATE_KEY,
			WmsFormatter.toParamDate(txt_PickingDate.getDate()));

		// 処理中画面->結果画面
		redirect(
				DO_SRCH_SORTING_RESULT_DATE,
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
	 *     仕分日 <BR>
	 *     商品コード <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListSortingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 仕分日
		param.setParameter(
			ListSortingResultDateBusiness.SORTINGDATE_KEY,
			WmsFormatter.toParamDate(txt_PickingDate.getDate()));
		
		// 商品コード
		param.setParameter(
			ListSortingItemBusiness.ITEMCODE_KEY,
			txt_ItemCode.getText());
			
		// 画面情報
		param.setParameter(
			ListSortingItemBusiness.SEARCHITEM_KEY,
			SortingParameter.SEARCH_SORTING_QTY);
						
		// 処理中画面->結果画面
		redirect(
			DO_SRCH_SORTING_ITEM,
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
	 *         仕分日* <BR>
	 *         商品コード* <BR>
	 *         ケース／ピース区分* <BR>
	 *         クロス／DC* <BR>
	 *         </DIR>
	 *       </DIR>
	 *     3.スケジュールの結果をためうちエリアに表示を行います。 <BR>
	 *     4.カーソルを荷主コードにセットします。 <BR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * リストセルの列番号一覧 <BR>
	 * <DIR>
	 *     1:仕分予定日 <BR>
	 *     2:ｹｰｽ/ﾋﾟｰｽ <BR>
	 *     3:ｸﾛｽ/DC <BR>
	 *     4:出荷先ｺｰﾄﾞ <BR>
	 *     5:ｹｰｽ入数 <BR>
	 *     6:作業予定ｹｰｽ数<BR>
	 *     7:実績ｹｰｽ数<BR>
	 *     8:欠品ｹｰｽ数<BR>
	 *     9:仕分場所 <BR>
	 *    10:仕入先ｺｰﾄﾞ <BR>
	 *    11:ｹｰｽITF <BR>
	 *    12:出荷伝票No <BR>
	 *    13:入荷伝票No <BR>
	 *    14:作業者ｺｰﾄﾞ <BR>
	 *    15:出荷先名称 <BR>
	 *    16:ﾎﾞｰﾙ入数 <BR>
	 *    17:作業予定ﾋﾟｰｽ数 <BR>
	 *    18:実績ﾋﾟｰｽ数 <BR>
	 *    19:欠品ﾋﾟｰｽ数 <BR>
	 *    20:仕入先名称 <BR>
	 *    21:ﾎﾞｰﾙITF <BR>
	 *    22:出荷伝票行No <BR>
	 *    23:入荷伝票行No <BR>
	 *    24:作業者名 <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);

		// ためうちエリアをクリアする
		lst_SortingQtyInquiry.clearRow();
		txt_JavaSetConsignorCode.setText("");
		txt_JavaSetConsignorName.setText("");
		txt_JavaSetItemCode.setText("");
		txt_JavaSetItemName.setText("");
		txt_JavaSetPickingPlanDate.setText("");
		
		// 入力チェックを行う（書式、必須、禁止文字）
		// 荷主コード
		txt_ConsignorCode.validate();
		// 仕分予定日
		txt_PickingDate.validate();
		// 商品コード
		txt_ItemCode.validate();
		
		Connection conn = null;
		
		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			// スケジュールパラメータをセットする
			SortingParameter param = new SortingParameter();
			
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 仕分日
			param.setSortingDate(WmsFormatter.toParamDate(txt_PickingDate.getDate()));
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
			// クロス／ＤＣ区分
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
			
			// スケジュールを開始する
			WmsScheduler schedule = new SortingQtyInquirySCH();
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
			// 最新の荷主名（ためうち）
			txt_JavaSetConsignorName.setText(viewParam[0].getConsignorName());
			// 仕分日（ためうち）
			txt_JavaSetPickingPlanDate.setDate(WmsFormatter.toDate(viewParam[0].getSortingDate()));
			// 商品コード（ためうち）
			txt_JavaSetItemCode.setText(viewParam[0].getItemCode());
			// 商品名（ためうち）
			txt_JavaSetItemName.setText(viewParam[0].getItemName());
			
			// ロケールを取得
			Locale locale = this.getHttpRequest().getLocale();

			// 出荷先名称
			String label_customername = DisplayText.getText("LBL-W0036");
			// 仕分場所
			String label_sortinglocation = DisplayText.getText("LBL-W0133");
			// 仕入先コード
			String label_suppliercode = DisplayText.getText("LBL-W0251");
			// 仕入先名称
			String label_suppliername = DisplayText.getText("LBL-W0253");
			// ケースＩＴＦ
			String label_caseitf = DisplayText.getText("LBL-W0338");
			// ボールＩＴＦ
			String label_bundleitf = DisplayText.getText("LBL-W0337");
			// 出荷伝票No.
			String label_shippingticketno = DisplayText.getText("LBL-W0206");
			// 出荷伝票行No.
			String label_shippinglineno = DisplayText.getText("LBL-W0205");
			// 入荷伝票No.
			String label_instockticketno = DisplayText.getText("LBL-W0091");
			// 入荷伝票行No.
			String label_instocklineno = DisplayText.getText("LBL-W0090");
			// 作業者コード
			String label_workercode = DisplayText.getText("LBL-W0274");
			// 作業者名
			String label_workername = DisplayText.getText("LBL-W0276");

			// リストセルに値をセットする
			for(int i = 0; i < viewParam.length; i++)
			{
				// 行追加
				lst_SortingQtyInquiry.addRow();
				lst_SortingQtyInquiry.setCurrentRow(i + 1);
	
				int j = 1;
				
				// 検索結果を各セルにセットしていく
				// （1列目）
				// 仕分予定日
				lst_SortingQtyInquiry.setValue(j++, WmsFormatter.toDispDate(viewParam[i].getPlanDate(), locale));
				// ｹｰｽ/ﾋﾟｰｽ
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getCasePieceName());
				// ｸﾛｽ/DC
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getTcdcName());
				// 出荷先ｺｰﾄﾞ
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getCustomerCode());
				// ｹｰｽ入数
				lst_SortingQtyInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getEnteringQty()));
				// 作業予定ｹｰｽ数
				lst_SortingQtyInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				// 実績ｹｰｽ数
				lst_SortingQtyInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				// 欠品ｹｰｽ数
				lst_SortingQtyInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getShortageCaseQty()));
				// 仕分場所
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getSortingLocation());
				// 仕入先ｺｰﾄﾞ
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getSupplierCode());
				// ｹｰｽITF
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getITF());
				// 出荷伝票No
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getShippingTicketNo());
				// 入荷伝票No
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getInstockTicketNo());
				// 作業者ｺｰﾄﾞ
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getWorkerCode());
				
				// (２列目)
				// 出荷先名称
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getCustomerName());
				// ﾎﾞｰﾙ入り数
				lst_SortingQtyInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				// 作業予定ﾋﾟｰｽ数
				lst_SortingQtyInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				// 実績ﾋﾟｰｽ数
				lst_SortingQtyInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				// 欠品ﾋﾟｰｽ数
				lst_SortingQtyInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getShortagePieceQty()));
				// 仕入先名称
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getSupplierName());
				// ﾎﾞｰﾙITF
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getBundleITF());
				// 出荷伝票行No
				lst_SortingQtyInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getShippingLineNo()));
				// 入荷伝票行No
				lst_SortingQtyInquiry.setValue(j++, Formatter.getNumFormat(viewParam[i].getInstockLineNo()));
				// 作業者名
				lst_SortingQtyInquiry.setValue(j++, viewParam[i].getWorkerName());
				
				//tool tip（バルーン項目）をセット
				ToolTipHelper toolTip = new ToolTipHelper();
				
				// 出荷先名称
				toolTip.add(label_customername,viewParam[i].getCustomerName());
				// 仕分場所
				toolTip.add(label_sortinglocation,viewParam[i].getSortingLocation());
				// 仕入先コード
				toolTip.add(label_suppliercode,viewParam[i].getSupplierCode());
				// 仕入先名称
				toolTip.add(label_suppliername,viewParam[i].getSupplierName());
				// ケースＩＴＦ
				toolTip.add(label_caseitf,viewParam[i].getITF());
				// ボールＩＴＦ
				toolTip.add(label_bundleitf,viewParam[i].getBundleITF());
				// 出荷伝票No.
				toolTip.add(label_shippingticketno,viewParam[i].getShippingTicketNo());
				// 出荷伝票行No.
				toolTip.add(label_shippinglineno,Formatter.getNumFormat(viewParam[i].getShippingLineNo()));
				// 入荷伝票No.
				toolTip.add(label_instockticketno,viewParam[i].getInstockTicketNo());
				// 入荷伝票行No.
				toolTip.add(label_instocklineno,Formatter.getNumFormat(viewParam[i].getInstockLineNo()));
				// 作業者コード
				toolTip.add(label_workercode,viewParam[i].getWorkerCode());
				// 作業者名
				toolTip.add(label_workername,viewParam[i].getWorkerName());
				
				lst_SortingQtyInquiry.setToolTip(i+1, toolTip.getText());
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
	 *     初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		setInitDsp();
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
	public void lbl_SPickingDate_Server(ActionEvent e) throws Exception
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
	public void lst_SortingQtyInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingQtyInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingQtyInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingQtyInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingQtyInquiry_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingQtyInquiry_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingQtyInquiry_Click(ActionEvent e) throws Exception
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
