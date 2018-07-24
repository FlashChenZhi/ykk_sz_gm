// $Id: SortingWorkingInquiryBusiness.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.sorting.display.web.sortingworkinginquiry;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.PulldownHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;
import jp.co.daifuku.wms.sorting.schedule.SortingWorkingInquiryParameter;
import jp.co.daifuku.wms.sorting.schedule.SortingWorkingInquirySCH;

/**
 * Designer : D.Hakui <BR>
 * Maker : D.Hakui <BR>
 * <BR>
 * 仕分状況照会画面クラスです。<BR>
 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
 * スケジュールから表示エリア出力用のデータを受け取り、表示エリアに出力します。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_View_Click()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *    入力エリアの入力項目を条件に、仕分予定情報を検索し、一覧に作業進捗の表示を行います。<BR>
 * <BR>
 *    [パラメータ] *必須入力<BR>
 * <BR>
 *    <DIR>
 *       荷主コード <BR>
 *       仕分予定日* <BR>
 *    </DIR>
 * <BR>
 *   [返却データ] <BR>
 * <BR>
 *    <DIR>
 * 		  ケース仕分
 *        仕分先数(合計、未処理、作業中、処理済、進捗率)<BR>
 *        アイテム数(合計、未処理、作業中、処理済、進捗率)<BR>
 *        ケース数(合計、未処理、作業中、処理済、進捗率)<BR>
 *        荷主数(合計、未処理、作業中、処理済、進捗率)<BR>
 *    </DIR>
 * <BR>
 *   [返却データ] <BR>
 * <BR>
 *    <DIR>
 * 　　　 ピース仕分
 *        仕分先数(合計、未処理、作業中、処理済、進捗率)<BR>
 *        アイテム数(合計、未処理、作業中、処理済、進捗率)<BR>
 *        ケース数(合計、未処理、作業中、処理済、進捗率)<BR>
 *        荷主数(合計、未処理、作業中、処理済、進捗率)<BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
 * @author  $Author: mori $
 */
public class SortingWorkingInquiryBusiness extends SortingWorkingInquiry implements WMSConstants
{
	/** 
	 * 入庫予定日ブランク用
	 */
	public static final String BLANK_DATE = "        ";
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

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
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 *   <DIR>
	 *   荷主コード[荷主コード] <BR>
	 *     <DIR>
	 *     （予定情報の未作業荷主が一件の場合） <BR>
	 *     </DIR>
	 *     仕分予定日[なし] <BR>
	 *   </DIR>
	 * 
	 *   <DIR>
	 *   仕分予定日[仕分予定日] <BR>
	 *     <DIR>
	 *     </DIR>
	 *     仕分予定日[なし] <BR>
	 *   </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;

		try
		{
			// プルダウンを使用不可にします
			pul_PickingPlanDate.setEnabled(false);
			
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			SortingWorkingInquiryParameter initParam = new SortingWorkingInquiryParameter();
			WmsScheduler schedule = new SortingWorkingInquirySCH(); 
			SortingWorkingInquiryParameter param = (SortingWorkingInquiryParameter) schedule.initFind(conn, initParam);
			
			// 荷主コードが1件の場合、初期表示させる。
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}

			// 仕分予定日のデフォルト
			if (param.getPlanDateP() != null)
			{
				// プルダウンを使用可にします
				pul_PickingPlanDate.setEnabled(true);
				
				// 仕分予定日に空白が何件入っているかを取得します
				int blankCount = 0;
				for (int i = 0; i < param.getPlanDateP().length; i++)
				{
					if (StringUtil.isBlank(param.getPlanDateP()[i]))
					{
						blankCount++;
					}
				}
				
				String planDate[] = new String[param.getPlanDateP().length - blankCount];
				
				// 仕分予定日に値をセットします
				blankCount = 0;				
				for (int i = 0; i < param.getPlanDateP().length; i++)
				{
					if (!StringUtil.isBlank(param.getPlanDateP()[i]))
					{
						planDate[blankCount] =
							WmsFormatter.toDispDate(
								param.getPlanDateP()[i],
								this.getHttpRequest().getLocale());
						blankCount++;
					}
				}
				PulldownHelper.setPullDown(pul_PickingPlanDate, planDate);
				pul_PickingPlanDate.setSelectedIndex(0);
				// カーソルをプルダウンにセットする
				setFocus(pul_PickingPlanDate);
			}			
			//	 仕分予定日のデータがない場合、（日時更新後など）仕分予定日のプルダウンをdisableにします
			else if (param.getPlanDateP() == null || param.getPlanDateP().length == 0)
			{
				// プルダウンアイテムを生成します
				PullDownItem pItem = new PullDownItem();
				// プルダウンアイテムにブランクをセットします
				pItem.setValue(BLANK_DATE);
				// プルダウンに追加します
				pul_PickingPlanDate.addItem(pItem);
				// 先頭のプルダウンアイテムを表示します
				pul_PickingPlanDate.setSelectedIndex(0);
				// カーソルを荷主コードにセットする
				setFocus(txt_ConsignorCode);
			}

			// リストセルのデフォルト
			clearListCell( lst_SortingInquiryCase );
			clearListCell( lst_SortingInquiryPiece );
		}
		catch (Exception ex)
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
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * プルダウンより選択された仕分予定日を取得するためのメソッドです。
	 * @return 仕分予定日（String）
	 */
	protected String getSelectedDateString()
	{
		// プルダウンに値がない場合はnullを返す。
		if (!pul_PickingPlanDate.getEnabled())
		{
			return null;
		}
		
		// プルダウンより仕分予定日を取得する
		String dateText = null;
		dateText = pul_PickingPlanDate.getSelectedItem().getValue();

		String selectedDate = WmsFormatter.toParamDate(dateText, this.getHttpRequest().getLocale());
		
		return selectedDate;
	}
	// Private methods -----------------------------------------------

	/** 
	 * スケジュールから取得したリストセルのデータを、リストセルの各項目に代入するためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した仕分情報を返します。<BR>
	 * @param cellName データを挿入したいFixedListCellオブジェクト
	 * @param parameter スケジュールから取得したデータ
	 */
	private void dispCell(FixedListCell cellName, SortingWorkingInquiryParameter parameter)
	{
		SortingWorkingInquiryParameter viewParam = parameter;
		
		// リストセルの1行目に値をセットします
		cellName.setCurrentRow(1);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getTotalSoringCount()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartSoringCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowSoringCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishSoringCount()));
		cellName.setValue(5, viewParam.getSoringProgressiveRate());

		// リストセルの3行目に値をセットします
		cellName.setCurrentRow(2);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getTotalItemCount()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartItemCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowItemCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishItemCount()));
		cellName.setValue(5, viewParam.getItemProgressiveRate());

		// リストセルの4行目に値をセットします
		cellName.setCurrentRow(3);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getTotalQtyCount()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartQtyCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowQtyCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishQtyCount()));
		cellName.setValue(5, viewParam.getQtyProgressiveRate());

		// リストセルの5行目に値をセットします
		cellName.setCurrentRow(4);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getTotalConsignorCount()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartConsignorCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowConsignorCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishConsignorCount()));
		cellName.setValue(5, viewParam.getConsignorProgressiveRate());
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
		String consignorcode = param.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
			// フォーカスを荷主コードにセットする
			setFocus(txt_ConsignorCode);
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
		// 仕分予定日
		param.setParameter(
			ListSortingConsignorBusiness.SORTINGPLANDATE_KEY, 
			getSelectedDateString());		
		// 荷主コード
		param.setParameter(
			ListSortingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 検索フラグ
		param.setParameter(
			ListSortingConsignorBusiness.SEARCHCONSIGNOR_KEY,
			SortingParameter.SEARCH_SORTING_WORK);
		
		// 処理中画面->結果画面
		redirect(
			"/sorting/listbox/listsortingconsignor/ListSortingConsignor.do",
			param,
			"/progress.do");
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
	public void pul_PickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_PickingPlanDate_Change(ActionEvent e) throws Exception
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
	 * 概要：入力エリアの入力項目を条件に、仕分予定情報を検索し、一覧に作業進捗の表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリア入力項目のチェックを行います。（必須入力、文字数、文字属性）<BR>
	 *    2.スケジュールを開始します。<BR>
	 *    <BR>
	 *    <DIR>
	 *       [パラメータ] *必須入力<BR>
	 *       <DIR>
	 *          荷主コード <BR>
	 *          仕分予定日* <BR>
	 *       </DIR>
	 *    </DIR>
	 *    <BR>
	 *    3.一覧に表示を行います。<BR>
	 *    4.カーソルを荷主コードにセットします。<BR>
	 * <BR>
	 *    リストセルの列番号一覧<BR>
	 *    <DIR>
	 *       1：合計<BR>
	 *       2：未処理<BR>
	 *       3：作業中<BR>
	 *       4：処理済<BR>
	 *       5：進捗率<BR>
	 *    </DIR>
	 * <BR>
	 *    リストセルの行番号一覧<BR>
	 *    <DIR>
	 *       1：作業件数<BR>
	 *       2：伝票枚数<BR>
	 *       3：アイテム数<BR>
	 *       4：ケース数<BR>
	 *       5：荷主数<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{

		// リストセルをクリアします
		clearListCell( lst_SortingInquiryCase );
		clearListCell( lst_SortingInquiryPiece );

		// カーソルを荷主コードにセットします
		setFocus(txt_ConsignorCode);

		// 入力チェックを行う
		txt_ConsignorCode.validate(false);

		Connection conn = null;

		try
		{
			// コネクションの取得
			conn =ConnectionManager.getConnection(DATASOURCE_NAME);
			// スケジュールパラメータをセットする
			SortingWorkingInquiryParameter param = new SortingWorkingInquiryParameter();
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 出荷予定日
			param.setPlanDate(
				WmsFormatter.toParamDate(
					pul_PickingPlanDate.getSelectedValue(),
					this.getHttpRequest().getLocale()));

			// スケジュールを開始する
			WmsScheduler schedule = new SortingWorkingInquirySCH();
			SortingWorkingInquiryParameter[] viewParam = (SortingWorkingInquiryParameter[]) schedule.query(conn, param);

			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了する。		
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			// 検索結果を各セルにセットしていく
			dispCell(lst_SortingInquiryCase, viewParam[0]);
			dispCell(lst_SortingInquiryPiece, viewParam[1]);

			if (!StringUtil.isBlank(schedule.getMessage()))
			{
				// メッセージを表示します
				message.setMsgResourceKey(schedule.getMessage());
			}
		}
		catch (Exception ex)
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
	public void lst_SortingInquiryCase_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryCase_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryCase_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryCase_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryCase_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryCase_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryCase_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryPiece_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryPiece_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryPiece_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryPiece_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryPiece_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryPiece_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SortingInquiryPiece_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの初期値を設定するためのメソッドです。<BR>
	 * <BR>
	 * 概要：リストセルを初期値に設定します。。<BR>
	 * @param cellName 対象となるリストセルオブジェクト
	 * @throws Exception 全ての例外を報告します。
	 */
	public void clearListCell( FixedListCell cellName ) throws Exception
	{
		// 仕分状況照会（ケース仕分）
		for (int i = 1; i < 5; i++)
		{
			cellName.setCurrentRow(i);
			for (int j = 1; j < 6; j++)
			{
				cellName.setValue(j, "0");
			}
			// 進捗率は少数1桁まで(0.0)をセット
			cellName.setValue(5, WmsFormatter.changeProgressRate(0));
		}
	}
}
//end of class
