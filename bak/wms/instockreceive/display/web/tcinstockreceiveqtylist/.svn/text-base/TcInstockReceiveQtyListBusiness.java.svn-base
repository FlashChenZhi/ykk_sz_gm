// $Id: TcInstockReceiveQtyListBusiness.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.tcinstockreceiveqtylist;
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
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivedate.ListInstockReceiveDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.TcInstockReceiveQtyListSCH;

/**
 * Designer : K.Mukai <BR>
 * Maker : T.Yoshino<BR>
 * ＴＣ入荷実績リスト発行クラスです<BR>
 * ＴＣ入荷実績リスト発行処理を行うスケジュールにパラメータを引き渡します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_PDisplay_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 *  画面から入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
 *  スケジュールから検索結果を受け取り、ＴＣ入荷実績リスト一覧画面を呼出します<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   荷主コード*<BR>
 *   開始入荷受付日<BR>
 *   終了入荷受付日<BR>
 *   仕入先コード<BR>
 *   出荷先コード<BR>
 *   開始伝票No．<BR>
 *   終了伝票No．<BR>
 *   商品コード<BR>
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
 *   開始入荷受付日<BR>
 *   終了入荷受付日<BR>
 *   仕入先コード<BR>
 *   出荷先コード<BR>
 *   開始伝票No．<BR>
 *   終了伝票No．<BR>
 *   商品コード<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/08</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class TcInstockReceiveQtyListBusiness extends TcInstockReceiveQtyList implements WMSConstants
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
	 * 開始入荷受付日 [なし]<BR>
	 * 終了入荷受付日 [なし]<BR>
	 * 仕入先コード [なし]<BR>
	 * 出荷先コード [なし]<BR>
	 * 開始伝票No． [なし]<BR>
	 * 終了伝票No． [なし]<BR>
	 * 商品コード   [なし]<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 各入力フィールドに初期値をセットします。
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		// 開始入荷受付日
		txt_StrtInstkAcpDate.setText("");
		// 終了入荷受付日
		txt_EdInstkAcpDate.setText("");		
		// 仕入先コード
		txt_SupplierCode.setText("");
		// 出荷先コード
		txt_CustomerCode.setText("");		
		// 開始伝票No.
		txt_StartTicketNo.setText("");
		// 終了伝票No.
		txt_EndTicketNo.setText("");				
		// 商品コード
		txt_ItemCode.setText("");	
		
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
			// 荷主コードにカーソル遷移
			setFocus(txt_ConsignorCode);
		    // 荷主コードにフォーカスを設定します。
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
			WmsScheduler schedule = new TcInstockReceiveQtyListSCH();
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
		// 開始入荷受付日
		Date startinstockacceptdate = WmsFormatter.toDate(
			param.getParameter(ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY),
			this.getHttpRequest().getLocale());
		// 終了入荷受付日
		Date endinstockacceptdate = WmsFormatter.toDate(
			param.getParameter(ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY),
			this.getHttpRequest().getLocale());
		// 仕入先コード
		String suppliercode = param.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// 出荷先コード
		String customercode = param.getParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY);
		// 開始伝票№
		String startticketno = param.getParameter(ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY);
		// 終了伝票№
		String endticketno = param.getParameter(ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY);		
		// 商品コード
		String itemcode = param.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);
		
		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 開始入荷受付日
		if (!StringUtil.isBlank(startinstockacceptdate))
		{
			txt_StrtInstkAcpDate.setDate(startinstockacceptdate);
		}
		// 終了入荷受付日
		if (!StringUtil.isBlank(endinstockacceptdate))
		{
			txt_EdInstkAcpDate.setDate(endinstockacceptdate);
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
		// 開始伝票No.
		if (!StringUtil.isBlank(startticketno))
		{
			txt_StartTicketNo.setText(startticketno);
		}
		// 終了伝票No.
		if (!StringUtil.isBlank(endticketno))
		{
			txt_EndTicketNo.setText(endticketno);
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
	protected InstockReceiveParameter createParameter()
	{
		// スケジュールパラメータをセットする
		InstockReceiveParameter param = new InstockReceiveParameter();
		param = new InstockReceiveParameter();
		
		// 荷主コード
		param.setConsignorCode(txt_ConsignorCode.getText());
		// 開始入荷受付日
		param.setFromInstockReceiveDate(WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
		// 終了入荷受付日
		param.setToInstockReceiveDate(WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
		// 仕入先コード
		param.setSupplierCode(txt_SupplierCode.getText());
		// 出荷先コード
		param.setCustomerCode(txt_CustomerCode.getText());			
		// 開始伝票No.
		param.setFromTicketNo(txt_StartTicketNo.getText());
		// 終了伝票No.
		param.setToTicketNo(txt_EndTicketNo.getText());			
		// 商品コード
		param.setItemCode(txt_ItemCode.getText());		
		
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
			WmsScheduler schedule = new TcInstockReceiveQtyListSCH();
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
		// 検索フラグ
		param.setParameter(
			ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
			
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
	public void lbl_StrtInstkAcpDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtInstkAcpDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtInstkAcpDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtInstkAcpDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtInstkAcpDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 開始入荷受付日検索ボタンを押下したときに呼ばれます。<BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし<BR>
	 * その検索条件で入荷受付日一覧検索リストボックスを表示します。<BR>
	 * [パラメータ] *必須入力
	 * <DIR>
	 * 荷主コード<BR>
	 * 開始入荷受付日<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStrtInstkAcpDate_Click(ActionEvent e) throws Exception
	{
		// 入荷受付日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,txt_ConsignorCode.getText());
		// 開始入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY,WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
		// 開始フラグ(入荷受付日範囲フラグ)
		param.setParameter(ListInstockReceiveDateBusiness.RANGEINSTOCKDATE_KEY,InstockReceiveParameter.RANGE_START);
		// TCDC区分
		param.setParameter(
			ListInstockReceiveDateBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceivedate/ListInstockReceiveDate.do",
			param,
			"/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromToAcpDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EdInstkAcpDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdInstkAcpDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdInstkAcpDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdInstkAcpDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdInstkAcpDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 終了入荷受付日検索ボタンを押下したときに呼ばれます。<BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし<BR>
	 * その検索条件で入荷受付日一覧検索リストボックスを表示します。<BR>
	 * [パラメータ] *必須入力
	 * <DIR>
	 * 荷主コード<BR>
	 * 終了入荷受付日<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEdInstkAcpDate_Click(ActionEvent e) throws Exception
	{
		// 入荷受付日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,txt_ConsignorCode.getText());
		// 終了入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY,WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
		// 終了フラグ(入荷受付日範囲フラグ)
		param.setParameter(ListInstockReceiveDateBusiness.RANGEINSTOCKDATE_KEY,InstockReceiveParameter.RANGE_END);
		// TCDC区分
		param.setParameter(
			ListInstockReceiveDateBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceivedate/ListInstockReceiveDate.do",
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
	 * 開始入荷受付日<BR>
	 * 終了入荷受付日<BR>
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
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 開始入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
		// 終了入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// 検索フラグ
		param.setParameter(
			ListInstockReceiveSupplierBusiness.SEARCHSUPPLIER_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
		
		// TCDC区分
		param.setParameter(
			ListInstockReceiveSupplierBusiness.TCDCFLAG_KEY,
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
	 * 開始入荷受付日<BR>
	 * 終了入荷受付日<BR>
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
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 開始入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
		// 終了入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// 出荷先コード
		param.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());		
		// 検索フラグ
		param.setParameter(
			ListInstockReceiveCustomerBusiness.SEARCHCUSTOMER_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
		// TCDC区分
		param.setParameter(
			ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY,
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
	 * 開始伝票No．検索ボタンを押下したときに呼ばれます。<BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし<BR>
	 * その検索条件で伝票No．一覧検索リストボックスを表示します。<BR>
	 * [パラメータ] *必須入力
	 * <DIR>
	 * 荷主コード<BR>
	 * 開始入荷受付日<BR>
	 * 終了入荷受付日<BR>
	 * 仕入先コード<BR>
	 * 出荷先コード<BR>
	 * 開始伝票No．<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStartTicketNo_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 開始入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
		// 終了入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// 出荷先コード
		param.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());				
		// 開始伝票No．
		param.setParameter(ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY, txt_StartTicketNo.getText());		
		// 検索フラグ
		param.setParameter(
			ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
		// 開始フラグ(伝票No.範囲フラグ)
		param.setParameter(ListInstockReceiveTicketBusiness.RANGETICKETNUMBER_KEY,InstockReceiveParameter.RANGE_START);			
		// TCDC区分
		param.setParameter(
			ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceiveticket/ListInstockReceiveTicket.do",
			param,
			"/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromToTicketNo_Server(ActionEvent e) throws Exception
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
	 * 終了伝票No．検索ボタンを押下したときに呼ばれます。<BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし<BR>
	 * その検索条件で伝票No．一覧検索リストボックスを表示します。<BR>
	 * [パラメータ] *必須入力
	 * <DIR>
	 * 荷主コード<BR>
	 * 開始入荷受付日<BR>
	 * 終了入荷受付日<BR>
	 * 仕入先コード<BR>
	 * 出荷先コード<BR>
	 * 終了伝票No．<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEndTicketNo_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 開始入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
		// 終了入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// 出荷先コード
		param.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());				
		// 終了伝票No．
		param.setParameter(ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY, txt_EndTicketNo.getText());		
		// 検索フラグ
		param.setParameter(
			ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
		// 終了フラグ(伝票No.範囲フラグ)
		param.setParameter(ListInstockReceiveTicketBusiness.RANGETICKETNUMBER_KEY,InstockReceiveParameter.RANGE_END);			
		// TCDC区分
		param.setParameter(
			ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceiveticket/ListInstockReceiveTicket.do",
			param,
			"/progress.do");
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
	 * 開始入荷受付日<BR>
	 * 終了入荷受付日<BR>
	 * 仕入先コード<BR>
	 * 出荷先コード<BR>
	 * 開始伝票No．<BR>
	 * 終了伝票No．<BR>
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
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 開始入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
		// 終了入荷受付日
		param.setParameter(ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// 出荷先コード
		param.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());				
		// 開始伝票No．
		param.setParameter(ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY, txt_StartTicketNo.getText());				
		// 終了伝票No．
		param.setParameter(ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY, txt_EndTicketNo.getText());
		// 商品コード
		param.setParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());				
		// 検索フラグ
		param.setParameter(
			ListInstockReceiveItemBusiness.SEARCHITEM_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
		// TCDC区分
		param.setParameter(
			ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
			
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceiveitem/ListInstockReceiveItem.do",
			param,
			"/progress.do");
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
	 * 開始入荷受付日<BR>
	 * 終了入荷受付日<BR>
	 * 仕入先コード<BR>
	 * 出荷先コード<BR>
	 * 開始伝票No．<BR>
	 * 終了伝票No．<BR>
	 * 商品コード<BR>
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
		// 開始入荷受付日をセット
		forwardParam.setParameter(ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));	
		// 終了入荷受付日をセット
		forwardParam.setParameter(ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY, WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));	
		// 仕入先コードをセット
		forwardParam.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		// 出荷先コードをセット
		forwardParam.setParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());		
		// 開始伝票No.をセット
		forwardParam.setParameter(ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY, txt_StartTicketNo.getText());
		// 終了伝票No.をセット
		forwardParam.setParameter(ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY, txt_EndTicketNo.getText());
		// 商品コードをセット
		forwardParam.setParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());						

		// TC入荷実績リスト一覧リストボックスを表示する。
		redirect("/instockreceive/listbox/listtcinstockreceiveqtylist/ListTcInstockReceiveQtyList.do", forwardParam, "/progress.do");
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
		txt_StrtInstkAcpDate.validate(false);
		txt_EdInstkAcpDate.validate(false);		
		txt_SupplierCode.validate(false);
		txt_CustomerCode.validate(false);		
		txt_StartTicketNo.validate(false);
		txt_EndTicketNo.validate(false);			
		txt_ItemCode.validate(false);		
	
		// 開始入荷受付日が終了入荷受付日より大きい
		if (!StringUtil.isBlank(txt_StrtInstkAcpDate.getDate())
			&& !StringUtil.isBlank(txt_EdInstkAcpDate.getDate()))
		{
			if (txt_StrtInstkAcpDate.getDate().compareTo(txt_EdInstkAcpDate.getDate()) > 0)
			{
				// 6023039=開始入荷受付日は、終了入荷受付日より前の日付にしてください。
				message.setMsgResourceKey("6023039");
				return;
			}
		}
		// 開始伝票No．が終了伝票No．より大きい		
		if (!StringUtil.isBlank(txt_StartTicketNo.getText())
			&& !StringUtil.isBlank(txt_EndTicketNo.getText()))
		{
			if (txt_StartTicketNo.getText().compareTo(txt_EndTicketNo.getText()) > 0)
			{
				// 6023037=開始伝票Noは、終了伝票Noより小さい値にしてください。
				message.setMsgResourceKey("6023037");
				return;
			}
		}		
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
			WmsScheduler schedule = new TcInstockReceiveQtyListSCH();
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
		// 開始入荷受付日
		txt_StrtInstkAcpDate.setText("");
		// 終了入荷受付日
		txt_EdInstkAcpDate.setText("");		
		// 仕入先コード
		txt_SupplierCode.setText("");
		// 出荷先コード
		txt_CustomerCode.setText("");		
		// 開始伝票No.
		txt_StartTicketNo.setText("");		
		// 終了伝票No.
		txt_EndTicketNo.setText("");		
		// 商品コード
		txt_ItemCode.setText("");	

		// 荷主コードにフォーカスセット
		setFocus(txt_ConsignorCode);
	}


}
//end of class
