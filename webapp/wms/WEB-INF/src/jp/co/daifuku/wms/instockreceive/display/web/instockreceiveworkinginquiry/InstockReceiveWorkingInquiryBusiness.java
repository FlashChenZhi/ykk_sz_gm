// $Id: InstockReceiveWorkingInquiryBusiness.java,v 1.1.1.1 2006/08/17 09:34:12 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.instockreceiveworkinginquiry;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.FixedListCell;
import jp.co.daifuku.bluedog.ui.control.PullDownItem;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.PulldownHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveWorkingInquiryParameter;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveWorkingInquirySCH;

/**
 * Designer : A.Nagasawa <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * 入荷状況照会画面クラスです。<BR>
 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
 * スケジュールから表示エリア出力用のデータを受け取り、表示エリアに出力します。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_View_Click()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *    入力エリアの入力項目を条件に、入荷予定情報を検索し、一覧に作業進捗の表示を行います。<BR>
 * <BR>
 *    [パラメータ] *必須入力<BR>
 * <BR>
 *    <DIR>
 *       荷主コード <BR>
 *       入荷予定日* <BR>
 *    </DIR>
 * <BR>
 *   [返却データ] <BR>
 * <BR>
 *    <DIR>
 *       ・TC入荷、DC入荷、クロスTC入荷別に取得<BR>
 *        出荷先数(合計、未処理、作業中、処理済、進捗率)<BR>
 *        伝票枚数(合計、未処理、作業中、処理済、進捗率)<BR>
 *        アイテム数(合計、未処理、作業中、処理済、進捗率)<BR>
 *        ケース数(合計、未処理、作業中、処理済、進捗率)<BR>
 *        ピース数(合計、未処理、作業中、処理済、進捗率)<BR>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:12 $
 * @author  $Author: mori $
 */
public class InstockReceiveWorkingInquiryBusiness extends InstockReceiveWorkingInquiry implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * 入荷予定日ブランク用
	 */
	public static final String BLANK_DATE = "        ";
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *    1.カーソルを荷主コードに初期セットします。<BR>
	 *    2.一覧表示項目は全て0をセット(進捗率は少数1桁までセット)し初期表示します。<BR>
	 * <BR>
	 *    項目名[初期値]<BR>
	 *    <DIR>
	 *       荷主コード[該当荷主が1件の場合、荷主コードを初期表示します。]<BR>
	 *       入荷予定日[検索入荷予定日の先頭の日付を初期表示します。]<BR>
	 *       リストセル[全て0をセット(進捗率は少数1桁までセット)し初期表示します。]
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		Connection conn = null;
		
		try
		{
			// プルダウンを使用可能にします
			pul_InstockPlanDate.setEnabled(false);

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			InstockReceiveWorkingInquiryParameter initParam = new InstockReceiveWorkingInquiryParameter();;
			WmsScheduler schedule = new InstockReceiveWorkingInquirySCH();
			InstockReceiveWorkingInquiryParameter param =
				(InstockReceiveWorkingInquiryParameter) schedule.initFind(conn, initParam);

			// 荷主コードが1件の場合、初期表示させる。
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
			}

			// 入荷予定日のデフォルト
			if (param.getPlanDateP() != null)
			{
				// プルダウンを使用可能にします
				pul_InstockPlanDate.setEnabled(true);
				
				// 入荷予定日に空白が何件入っているかを取得します
				int blankCount = 0;
				for (int i = 0; i < param.getPlanDateP().length; i++)
				{
					if (StringUtil.isBlank(param.getPlanDateP()[i]))
					{
						blankCount++;
					}
				}
				
				String planDate[] = new String[param.getPlanDateP().length - blankCount];
				
				// 入荷予定日に値をセットします
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
				PulldownHelper.setPullDown(pul_InstockPlanDate, planDate);
				pul_InstockPlanDate.setSelectedIndex(0);
				
				//カーソルをプルダウンにセットします
				setFocus(pul_InstockPlanDate);
			}

			// 入荷予定日のデータがない場合、（日時更新後など）入荷予定日のプルダウンを
			// disableにします
			else if (param.getPlanDateP() == null || param.getPlanDateP().length == 0)
			{
				// プルダウンアイテムを生成します
				PullDownItem pItem = new PullDownItem();
				// プルダウンアイテムにブランクをセットします
				pItem.setValue(BLANK_DATE);
				// プルダウンに追加します
				pul_InstockPlanDate.addItem(pItem);
				// 先頭のプルダウンアイテムを表示します
				pul_InstockPlanDate.setSelectedIndex(0);
				// プルダウンを使用不可にします
				//pul_InstockPlanDate.setEnabled(false);
				
			// カーソルを荷主コードにセットする
			setFocus(txt_ConsignorCode);

			}

			// リストセルのデフォルト
			clearListCell();
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
		String consignorcode = param.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
			// フォーカスを荷主コードにセットする
			setFocus(txt_ConsignorCode);
		}

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * プルダウンより選択された入荷予定日を取得するためのメソッドです。
	 * @return 入荷予定日（String）
	 */
	protected String getSelectedDateString()
	{
		// プルダウンに値がない場合はnullを返す。
		if (!pul_InstockPlanDate.getEnabled())
		{
			return null;
		}
		
		// プルダウンより入荷予定日を取得する
		String dateText = null;
		dateText = pul_InstockPlanDate.getSelectedItem().getValue();

		String selectedDate = WmsFormatter.toParamDate(dateText, this.getHttpRequest().getLocale());
		
		return selectedDate;
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
	 *  入荷予定日<BR>
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

		// 入荷予定日
		param.setParameter(ListInstockReceiveConsignorBusiness.INSTOCKPLANDATE_KEY, getSelectedDateString());
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 検索フラグ
		param.setParameter(ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);
		// 処理中画面->結果画面
		redirect("/instockreceive/listbox/listinstockreceiveconsignor/ListInstockReceiveConsignor.do", param, "/progress.do");
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
	public void pul_InstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_InstockPlanDate_Change(ActionEvent e) throws Exception
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
	 * 概要：入力エリアの入力項目を条件に、入荷予定情報を検索し、一覧に作業進捗の表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリア入力項目のチェックを行います。（必須入力、文字数、文字属性）<BR>
	 *    2.スケジュールを開始します。<BR>
	 *    <BR>
	 *    <DIR>
	 *       [パラメータ] *必須入力<BR>
	 *       <DIR>
	 *          荷主コード <BR>
	 *          入荷予定日* <BR>
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
	 *       2：ケース数<BR>
	 *       3：ピース数<BR>
	 *       4：荷主数<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		// リストセルをクリアします
		clearListCell();

		// カーソルを荷主コードにセットします
		setFocus(txt_ConsignorCode);

		// パターンマッチング文字
		txt_ConsignorCode.validate(false);

		Connection conn = null;

		try
		{
			// スケジュールパラメータをセットする
			InstockReceiveWorkingInquiryParameter param = new InstockReceiveWorkingInquiryParameter();
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 入荷予定日
			param.setPlanDate(
				WmsFormatter.toParamDate(
					pul_InstockPlanDate.getSelectedValue(),
					this.getHttpRequest().getLocale()));

			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールを開始する
			WmsScheduler schedule = new InstockReceiveWorkingInquirySCH();
			InstockReceiveWorkingInquiryParameter[] viewParam =
				(InstockReceiveWorkingInquiryParameter[]) schedule.query(conn, param);

			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了する。		
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			// 検索結果を各セルにセットしていく
			// TC入荷
			dispCell(lst_SInstkWorkingIqrTC, viewParam[0]);
			// クロスTC入荷
			dispCell(lst_SInstkWorkingIqrCrsTC, viewParam[2]);
			// DC入荷
			dispCell(lst_SInstkWorkingIqrDC, viewParam[1]);

			if (!StringUtil.isBlank(schedule.getMessage()))
			{
				// メッセージを表示します
				message.setMsgResourceKey(schedule.getMessage());
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
	 * リストセルに値をセットするメソッドです。 <BR>
	 * <BR>
	 * 概要:対象のセルに各状況の値をセットします。
	 * 
	 * @param cellName セットするリストセルの名前です。
	 * @param param セットする値です。
	 */
	private void dispCell(FixedListCell cellName, InstockReceiveWorkingInquiryParameter param)
	{
		
		InstockReceiveWorkingInquiryParameter viewParam = param;
		
		// リストセルの1行目に値をセットします
		cellName.setCurrentRow(1);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getSupplierTotal()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartSupplierCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowSupplierCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishSupplierCount()));
		cellName.setValue(5, viewParam.getSupplierRate());

		// リストセルの2行目に値をセットします
		cellName.setCurrentRow(2);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getTicketTotal()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartTicketCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowTicketCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishTicketCount()));
		cellName.setValue(5, viewParam.getTicketRate());

		// リストセルの3行目に値をセットします
		cellName.setCurrentRow(3);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getItemTotal()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartItemCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowItemCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishItemCount()));
		cellName.setValue(5, viewParam.getItemRate());

		// リストセルの4行目に値をセットします
		cellName.setCurrentRow(4);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getCaseTotal()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartCaseCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowCaseCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishCaseCount()));
		cellName.setValue(5, viewParam.getCaseRate());

		// リストセルの5行目に値をセットします
		cellName.setCurrentRow(5);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getPieceTotal()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartPieceCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowPieceCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishPieceCount()));
		cellName.setValue(5, viewParam.getPieceRate());

		// リストセルの6行目に値をセットします
		cellName.setCurrentRow(6);
		cellName.setValue(1, Formatter.getNumFormat(viewParam.getConsignorTotal()));
		cellName.setValue(2, Formatter.getNumFormat(viewParam.getUnstartConsignorCount()));
		cellName.setValue(3, Formatter.getNumFormat(viewParam.getNowConsignorCount()));
		cellName.setValue(4, Formatter.getNumFormat(viewParam.getFinishConsignorCount()));
		cellName.setValue(5, viewParam.getConsignorRate());
		
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrTC_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrTC_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrTC_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrTC_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrTC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrTC_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrTC_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrCrsTC_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrCrsTC_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrCrsTC_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrCrsTC_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrCrsTC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrCrsTC_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrCrsTC_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrDC_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrDC_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrDC_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrDC_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrDC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrDC_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkWorkingIqrDC_Click(ActionEvent e) throws Exception
	{
	}
	/** 
	 * リストセルの情報をクリアするメソッドです。<BR>
	 * <BR>
	 * 概要：リストセル内の項目にデフォルト値をセットします。<BR>
	 * 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void clearListCell() throws Exception
	{
		// 入荷状況照会（ＴＣ入荷）
		for (int i = 1; i < 7; i++)
		{
			lst_SInstkWorkingIqrTC.setCurrentRow(i);
			for (int j = 1; j < 5; j++)
			{
				lst_SInstkWorkingIqrTC.setValue(j, "0");
			}
			// 進捗率は少数1桁まで(0.0)をセット
			lst_SInstkWorkingIqrTC.setValue(5, WmsFormatter.changeProgressRate(0));
		}
		// 入荷状況照会（クロスＴＣ入荷）
		for (int i = 1; i < 7; i++)
		{
			lst_SInstkWorkingIqrCrsTC.setCurrentRow(i);
			for (int j = 1; j < 5; j++)
			{
				lst_SInstkWorkingIqrCrsTC.setValue(j, "0");
			}
			// 進捗率は少数1桁まで(0.0)をセット
			lst_SInstkWorkingIqrCrsTC.setValue(5, WmsFormatter.changeProgressRate(0));
		}
		// 入荷状況照会（ＤＣ入荷）
		for (int i = 1; i < 7; i++)
		{
			lst_SInstkWorkingIqrDC.setCurrentRow(i);
			for (int j = 1; j < 5; j++)
			{
				lst_SInstkWorkingIqrDC.setValue(j, "0");
			}
			// 進捗率は少数1桁まで(0.0)をセット
			lst_SInstkWorkingIqrDC.setValue(5, WmsFormatter.changeProgressRate(0));
		}

	}

}
//end of class
