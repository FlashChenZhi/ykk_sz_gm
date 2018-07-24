// $Id: TcInstockReceiveListBusiness.java,v 1.1.1.1 2006/08/17 09:34:13 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.tcinstockreceivelist;
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
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivecustomer.ListInstockReceiveCustomerBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listtcinstockreceivelist.ListTcInstockReceiveListBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.TcInstockReceiveListSCH;

/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Yoshino<BR>
 * ＴＣ 入荷作業リスト発行クラスです<BR>
 * ＴＣ 入荷作業リスト発行処理を行うスケジュールにパラメータを引き渡します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_PDisplay_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 *  画面から入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
 *  スケジュールから検索結果を受け取り、ＴＣ 入荷作業リスト一覧画面を呼出します<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   荷主コード*<BR>
 *   入荷予定日<BR>
 *   仕入先コード<BR>
 *   出荷先コード<BR>
 *   表示順*<BR>
 *    <DIR>
 *    伝票Ｎｏ．順<BR>
 *    商品コード順<BR>
 *    </DIR>
 *   作業状態*<BR>
 *    <DIR>
 *    全て<BR>
 *    未開始<BR>
 *    作業中<BR>
 *    一部完了<BR>
 *    完了<BR> 
 *    </DIR>
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
 *   入荷予定日<BR>
 *   仕入先コード<BR>
 *   出荷先コード<BR>
 *   表示順*<BR>
 *    <DIR>
 *    伝票Ｎｏ．順<BR>
 *    商品コード順<BR>
 *    </DIR>
 *   作業状態*<BR>
 *    <DIR>
 *    全て<BR>
 *    未開始<BR>
 *    作業中<BR>
 *    一部完了<BR>
 *    完了<BR> 
 *    </DIR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/08</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:13 $
 * @author  $Author: mori $
 */
public class TcInstockReceiveListBusiness extends TcInstockReceiveList implements WMSConstants
{
	// Class fields --------------------------------------------------
	// どのコントロールから呼び出されたダイアログかを保持：印刷ボタン
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
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
	 * 入荷予定日 [なし]<BR>
	 * 仕入先コード [なし]<BR>
	 * 出荷先コード [なし]<BR>
	 * 表示順       [伝票Ｎｏ．順]
	 * 作業中       [全て]
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);
		
		// 各入力フィールドに初期値をセットします。
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		// 入荷予定日
		txt_InstockPlanDate.setText("");
		// 仕入先コード
		txt_SupplierCode.setText("");
		// 出荷先コード
		txt_CustomerCode.setText("");	
		// 表示順
		rdo_TicTkt.setChecked(true);
		rdo_TicItem.setChecked(false);
		// 作業状態
		pul_WorkStatus.setSelectedIndex(0);
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
			// 荷主コードにカーソル遷移
			setFocus(txt_ConsignorCode);
			// カーソルを荷主コードにセットします。
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
			InstockReceiveParameter[] param = new InstockReceiveParameter[1];
			param[0] = createParameter();

			// スケジュールを開始する
			WmsScheduler schedule = new TcInstockReceiveListSCH();
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
		String consignorcode = param.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 入荷予定日
		Date instockplandate = WmsFormatter.toDate(
			param.getParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY),this.getHttpRequest().getLocale());
		// 仕入先コード
		String suppliercode = param.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// 出荷先コード
		String customercode = param.getParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY);
		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 入荷予定日
		if (!StringUtil.isBlank(instockplandate))
		{
			txt_InstockPlanDate.setDate(instockplandate);
		}
		// 仕入先コード
		if (!StringUtil.isBlank(suppliercode))
		{
			txt_SupplierCode.setText(suppliercode);
		}
		// 出荷先コード
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
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
	protected InstockReceiveParameter createParameter()
	{
		// スケジュールパラメータをセットする
		InstockReceiveParameter param = new InstockReceiveParameter();
		param = new InstockReceiveParameter();
		
		// 荷主コード
		param.setConsignorCode(txt_ConsignorCode.getText());
		// 入荷予定日
		param.setPlanDate(WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
		// 仕入先コード
		param.setSupplierCode(txt_SupplierCode.getText());
		// 出荷先コード
		param.setCustomerCode(txt_CustomerCode.getText());				
		// 表示順				
		if (rdo_TicTkt.getChecked())
		{
			param.setDspOrder(
				InstockReceiveParameter.DISPLAY_ORDER_TICKET);
		}
		else if (rdo_TicItem.getChecked())
		{
			param.setDspOrder(
				InstockReceiveParameter.DISPLAY_ORDER_ITEM);
		}
		// 作業状態			
		// 全て
		if (pul_WorkStatus.getSelectedIndex() == 0)
		{
			param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_ALL);
		}
		// 未開始
		else if (pul_WorkStatus.getSelectedIndex() == 1)
		{
			param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		}
		// 作業中
		else if (pul_WorkStatus.getSelectedIndex() == 2)
		{
			param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_WORKING);
		}
		// 完了
		else if (pul_WorkStatus.getSelectedIndex() == 3)
		{
			param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
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
	 * @throws Exception 全ての例外を報告します
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			// コネクション取得	
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			InstockReceiveParameter param = new InstockReceiveParameter();

			// スケジュールから荷主コードを取得する。
			WmsScheduler schedule = new TcInstockReceiveListSCH();
			param = (InstockReceiveParameter) schedule.initFind(conn, param);

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
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 検索フラグ(ケースピース区分が無い為予定で検索できる)
		param.setParameter(
			ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
		// TCDC区分
		param.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);

		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceiveconsignor/ListInstockReceiveConsignor.do",
			param,
			"/progress.do");	
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
	 * 入荷予定日検索ボタンを押下したときに呼ばれます。<BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし<BR>
	 * その検索条件で入荷予定日一覧検索リストボックスを表示します。<BR>
	 * [パラメータ] *必須入力
	 * <DIR>
	 * 荷主コード<BR>
	 * 入荷予定日<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchInstockPlanDate_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY,WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));			

		// TCDC区分
		param.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);

		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceiveplandate/ListInstockReceivePlanDate.do",
			param,
			"/progress.do");	
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
	 * 仕入先検索ボタンを押下したときに呼ばれます。<BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし<BR>
	 * その検索条件で仕入先一覧検索リストボックスを表示します。<BR>
	 * [パラメータ] *必須入力
	 * <DIR>
	 * 荷主コード<BR>
	 * 入荷予定日<BR>
	 * 仕入先コード<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchSupplierCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,txt_ConsignorCode.getText());
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY,WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));			
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,txt_SupplierCode.getText());
		// 検索フラグ(ケースピース区分が無い為予定で検索できる)
		param.setParameter(
			ListInstockReceiveSupplierBusiness.SEARCHSUPPLIER_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
		// TCDC区分
		param.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
					
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceivesupplier/ListInstockReceiveSupplier.do",
			param,
			"/progress.do");
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
	public void btn_PSearchCustomerCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷先検索ボタンを押下したときに呼ばれます。<BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし<BR>
	 * その検索条件で出荷先一覧検索リストボックスを表示します。<BR>
	 * [パラメータ] *必須入力
	 * <DIR>
	 * 荷主コード<BR>
	 * 入荷予定日<BR>
	 * 仕入先コード<BR>
	 * 出荷先コード<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,txt_ConsignorCode.getText());
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY,WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));			
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,txt_SupplierCode.getText());
		// 商品コード
		param.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY,txt_CustomerCode.getText());
		// 検索フラグ(ケースピース区分が無い為予定で検索できる)
		param.setParameter(
			ListInstockReceiveCustomerBusiness.SEARCHCUSTOMER_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
		// TCDC区分
		param.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
					
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceivecustomer/ListInstockReceiveCustomer.do",
			param,
			"/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_DspOrder_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_TicTkt_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_TicTkt_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ItemCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_ItemCode_Click(ActionEvent e) throws Exception
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
	public void pul_WorkStatusStorage_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusStorage_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PDisplay_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 表示ボタン押下時に呼ばれます。<BR>
	 * 概要：入力エリアの入力項目をパラメータのセットし別画面でオーダー出庫実績一覧<BR>
	 * リストボックスを表示します<BR><BR>
	 * [パラメータ] *必須入力<BR>
	 * <DIR>
	 * 荷主コード*<BR>
	 * 入荷予定日*<BR>
	 * 仕入先コード<BR>
	 * 出荷先コード<BR>
	 * 表示順<BR>
	 * 作業状態<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		// 荷主コードをセット
		forwardParam.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 入荷予定日をセット
		forwardParam.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));	
		// 仕入先コードをセット
		forwardParam.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// 出荷先コードをセット
		forwardParam.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());						

		// 表示順をセット
		if (rdo_TicTkt.getChecked())
		{
			forwardParam.setParameter(ListTcInstockReceiveListBusiness.DSPORDER_KEY, InstockReceiveParameter.DISPLAY_ORDER_TICKET);
		}
		else if (rdo_TicItem.getChecked())
		{
			forwardParam.setParameter(ListTcInstockReceiveListBusiness.DSPORDER_KEY, InstockReceiveParameter.DISPLAY_ORDER_ITEM);
		}
		// 作業状態
		// 全て
		if (pul_WorkStatus.getSelectedIndex() == 0)
		{
			forwardParam.setParameter(
				ListTcInstockReceiveListBusiness.STATUS_FLAG_KEY,
				InstockReceiveParameter.STATUS_FLAG_ALL);
		}
		// 未開始
		else if (pul_WorkStatus.getSelectedIndex() == 1)
		{
			forwardParam.setParameter(
				ListTcInstockReceiveListBusiness.STATUS_FLAG_KEY,
				InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		}
		// 作業中
		else if (pul_WorkStatus.getSelectedIndex() == 2)
		{
			forwardParam.setParameter(
				ListTcInstockReceiveListBusiness.STATUS_FLAG_KEY,
				InstockReceiveParameter.STATUS_FLAG_WORKING);
		}
		// 完了
		else if (pul_WorkStatus.getSelectedIndex() == 3)
		{
			forwardParam.setParameter(
				ListTcInstockReceiveListBusiness.STATUS_FLAG_KEY,
				InstockReceiveParameter.STATUS_FLAG_COMPLETION);
		}				
		// ＴＣ入荷作業リスト一覧リストボックスを表示する。
		redirect("/instockreceive/listbox/listtcinstockreceivelist/ListTcInstockReceiveList.do", forwardParam, "/progress.do");
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
	 * 概要：入力エリアの入力項目をパラメータにセットし印刷データ件数をチェックします。<BR>
	 * 印刷データがある場合はダイアログを表示します。無い場合はメッセージを表示します。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);
		// 入力チェック
		txt_ConsignorCode.validate();
		txt_InstockPlanDate.validate();
		// パターンマッチング
		txt_SupplierCode.validate(false);
		txt_CustomerCode.validate(false);		
	
		Connection conn = null;
		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			// 入力値をセットします。
			InstockReceiveParameter param = createParameter();
			
			// 件数チェックを行い、印刷データがある場合
			// 印刷確認ダイアログを表示する。
			// ない場合は、ダイアログを表示せず処理を終了する。
			WmsScheduler schedule = new TcInstockReceiveListSCH();
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
		// 荷主コードにフォーカスセット
		setFocus(txt_ConsignorCode);
		
		// 初期値セット
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		// 入荷予定日
		txt_InstockPlanDate.setText("");		
		// 仕入先コード
		txt_SupplierCode.setText("");
		// 出荷先コード
		txt_CustomerCode.setText("");	
		// 表示順
		rdo_TicTkt.setChecked(true);
		rdo_TicItem.setChecked(false);
		// 作業状態
		pul_WorkStatus.setSelectedIndex(0);
	}


}
//end of class
