// $Id: InstockReceivePlanListBusiness.java,v 1.1.1.1 2006/08/17 09:34:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.instockreceiveplanlist;
import java.sql.Connection;
import java.sql.SQLException;

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
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplanlist.ListInstockReceivePlanListBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceivePlanListSCH;

/**
 * Designer : T.Yoshino<BR>
 * Maker : S.Ishigane<BR>
 * <BR>
 * クロス/DC入荷予定リスト発行を行う画面クラスです。<BR>
 * クロス/DC入荷予定リスト発行を行うスケジュールにパラメータを引き渡します。<BR>
 * <BR>
 * <DIR>
 * 1.表示ボタン押下処理(<CODE>btn_PDisplay_Click<CODE>) <BR>
 * <BR>
 * <DIR>
 * 	画面から入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールが表示用のデータを検索し、ポップアップ画面で表示します。<BR>
 *  <BR>
 *  [パラメータ] *必須入力 <BR>
 *  <DIR>
 *   荷主コード *<BR>
 *   開始入荷予定日<BR>
 *   終了入荷予定日<BR>
 *   仕入先コード<BR>
 *   開始伝票No.<BR>
 *   終了伝票No.<BR>
 *   商品コード<BR>
 *   クロス/DC *<BR>
 *   作業状態 *<BR>
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
 *   開始入荷予定日<BR>
 *   終了入荷予定日<BR>
 *   仕入先コード<BR>
 *   開始伝票No.<BR>
 *   終了伝票No.<BR>
 *   商品コード<BR>
 *   クロス/DC *<BR>
 *   作業状態 *<BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>D.Hakui</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:11 $
 * @author  $Author: mori $
 */
public class InstockReceivePlanListBusiness extends InstockReceivePlanList implements WMSConstants
{
	// Class fields --------------------------------------------------
    
	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/instockreceive/listbox/listinstockreceiveconsignor/ListInstockReceiveConsignor.do";
	// 入荷予定日検索ポップアップアドレス
	private static final String DO_SRCH_INSTOCKPLANDATE = "/instockreceive/listbox/listinstockreceiveplandate/ListInstockReceivePlanDate.do";
	// 仕入先コード検索ポップアップアドレス
	private static final String DO_SRCH_SUPPLIER_CODE = "/instockreceive/listbox/listinstockreceivesupplier/ListInstockReceiveSupplier.do";
	// 伝票No.検索ポップアップアドレス
	private static final String DO_SRCH_TICKET_NO = "/instockreceive/listbox/listinstockreceiveticket/ListInstockReceiveTicket.do";
	// 商品検索ポップアップアドレス
	private static final String DO_SRCH_ITEM = "/instockreceive/listbox/listinstockreceiveitem/ListInstockReceiveItem.do";
	// クロス／ＤＣ入荷予定一覧アドレス
	private static final String DO_SRCH_INSTOCK_LIST = "/instockreceive/listbox/listinstockreceiveplanlist/ListInstockReceivePlanList.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";
	// どのコントロールから呼び出されたダイアログかを保持：印刷ボタン
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
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
	 *  荷主コード      [該当荷主が1件しかない場合初期表示を行う]<BR>
	 *  開始入荷予定日  [なし]<BR>
	 *  終了入荷予定日  [なし]<BR>
	 *  仕入先コード      [なし]<BR>
	 *  開始伝票No.  [なし]<BR>
	 *  終了伝票No.  [なし]<BR>
	 *  商品コード      [なし]<BR>
	 * 	クロス/DC		 	[全て]<BR>
	 * 	作業状態 [全て]<BR>
	 *  </DIR>
	 * </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
	    //	  荷主コードが一件の場合、荷主コードを取得します。
	    txt_ConsignorCode.setText(getConsignorCode());
	    
	    //各フィールドに初期値を設定します。
	    txt_StrtInstkPlanDate.setText("");
	    txt_EdInstkPlanDate.setText("");
	    txt_SupplierCode.setText("");
	    txt_StartTicketNo.setText("");
	    txt_EndTicketNo.setText("");
	    txt_ItemCode.setText("");
	    
	    rdo_CrossDCFlagAll.setChecked(true);
	    pul_WorkStatusStorage.setSelectedIndex(0);
	    
	    // 荷主コードにフォーカスを設定します。
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
		String consignorCode = param.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 開始入荷予定日
		String startInstockPlanDate = param.getParameter(ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY);
		// 終了入荷予定日
		String endInstockPlanDate = param.getParameter(ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY);
		// 仕入先コード
		String supplierCode = param.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// 開始伝票No.
		String startTicketNo = param.getParameter(ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY);
		// 終了伝票No.
		String endTicketNo = param.getParameter(ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY);
		// 商品コード
		String itemCode = param.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);
		
		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		// 開始入荷予定日
		if (!StringUtil.isBlank(startInstockPlanDate))
		{
			txt_StrtInstkPlanDate.setText(startInstockPlanDate);
		}
		// 終了入荷予定日
		if (!StringUtil.isBlank(endInstockPlanDate))
		{
			txt_EdInstkPlanDate.setText(endInstockPlanDate);
		}
		// 仕入先コード
		if (!StringUtil.isBlank(supplierCode))
		{
			txt_SupplierCode.setText(supplierCode);
		}
		// 開始伝票No.
		if (!StringUtil.isBlank(startTicketNo))
		{
			txt_StartTicketNo.setText(startTicketNo);
		}
		// 終了伝票No.
		if (!StringUtil.isBlank(endTicketNo))
		{
			txt_EndTicketNo.setText(endTicketNo);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemCode))
		{
			txt_ItemCode.setText(itemCode);
		}
		
	    // 荷主コードにフォーカスを設定します。
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
			WmsScheduler schedule = new InstockReceivePlanListSCH();
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
			
			//ヘルプファイルへのパスをセットする
			btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}

    }
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	// Protected methods ---------------------------------------------
	/** 
	 * 入力エリアの入力値をセットしたパラメータオブジェクトを生成します。<BR>
	 * 
	 * @return 入力エリアの入力値を含んだパラメータオブジェクト
	 */
	protected InstockReceiveParameter createParameter()
	{
		InstockReceiveParameter param = new InstockReceiveParameter();

		param.setConsignorCode(txt_ConsignorCode.getText());
		param.setFromPlanDate(WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));
		param.setToPlanDate(WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));
		param.setSupplierCode(txt_SupplierCode.getText());
		param.setFromTicketNo(txt_StartTicketNo.getText());
		param.setToTicketNo(txt_EndTicketNo.getText());
		param.setItemCode(txt_ItemCode.getText());
		// クロス/ＤＣ
		// 全て
		if (rdo_CrossDCFlagAll.getChecked())
		{
			param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_ALL);
		}
		// クロス
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
		}
		// DC
		else if (rdo_CrossDCFlagDC.getChecked())
		{
			param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
		}
		// 作業状態
		// 全て
		if(pul_WorkStatusStorage.getSelectedIndex() == 0)
		{
			param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_ALL);
		}
		// 未開始
		else if(pul_WorkStatusStorage.getSelectedIndex() == 1)
		{
			param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		}
		// 作業中
		else if(pul_WorkStatusStorage.getSelectedIndex() == 2)
		{
			param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_WORKING);
		}
		// 一部完了
		else if(pul_WorkStatusStorage.getSelectedIndex() == 3)
		{
			param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		}
		// 完了
		else if(pul_WorkStatusStorage.getSelectedIndex() == 4)
		{
			param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
		}		
		
		return param;	

	}
	// Private methods -----------------------------------------------
	/** 
	 * 入力チェックを行うためのメソッドです。<BR>
	 * 入力に間違いがなければtrue、間違いがあればfalseを返します。<BR>
	 * <BR>
	 * 
	 * @throws Exception 全ての例外を報告します。
	 * 
	 * @return 入力チェックの結果(true：OK  false：NG)
	 */
	private boolean checkInputData() throws Exception
	{
		// 入力チェック
		// 荷主コード
		txt_ConsignorCode.validate();
		// 開始入荷予定日
		txt_StrtInstkPlanDate.validate(false);
		// 終了入荷予定日
		txt_EdInstkPlanDate.validate(false);
		// 仕入先先コード
		txt_SupplierCode.validate(false);
		// 開始伝票No.
		txt_StartTicketNo.validate(false);
		// 終了伝票No.
		txt_EndTicketNo.validate(false);
		// 商品コード
		txt_ItemCode.validate(false);
		
		// 開始入荷予定日が終了入荷予定日より小さいこと
		if(!StringUtil.isBlank(txt_StrtInstkPlanDate.getDate()) && !StringUtil.isBlank(txt_EdInstkPlanDate.getDate()))
		{
			if(txt_StrtInstkPlanDate.getDate().after(txt_EdInstkPlanDate.getDate()))
			{
				// 6023036=開始入荷予定日は、終了入荷予定日より前の日付にしてください。
				message.setMsgResourceKey("6023036");
				return false;
			}
		}
		
		
		// 開始伝票Noが終了伝票Noより小さいこと
		if(!StringUtil.isBlank(txt_StartTicketNo.getText()) && !StringUtil.isBlank(txt_EndTicketNo.getText()))
		{
			if(txt_StartTicketNo.getText().compareTo(txt_EndTicketNo.getText()) > 0)			{
				// 6023037=開始伝票Noは、終了伝票Noより小さい値にしてください。
				message.setMsgResourceKey("6023037");
				return false;
			}
		    
		}
		
		return true;

	}
	
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
			InstockReceiveParameter param = new InstockReceiveParameter();

			// スケジュールから荷主コードを取得する。
			WmsScheduler schedule = new InstockReceivePlanListSCH();
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
	 * [パラメータ]
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
		param.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 検索フラグ(予定)
		param.setParameter(
			ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
		// TCDC区分
		param.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 中継画面→検索画面
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtInstkPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtInstkPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtInstkPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtInstkPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtInstkPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 開始入荷予定日検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で入荷予定日検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]
	 *  <DIR>
	 *  荷主コード<BR>
	 *  開始入荷予定日<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStrtInstkPlanDate_Click(ActionEvent e) throws Exception
	{
		// 入荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));
		
		// 範囲フラグ(開始)
		param.setParameter(
			ListInstockReceivePlanDateBusiness.RANGEINSTOCKPLANDATE_KEY,
			InstockReceiveParameter.RANGE_START);
			
		// TCDC区分
		param.setParameter(
			ListInstockReceivePlanDateBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 中継画面→検索画面
		redirect(DO_SRCH_INSTOCKPLANDATE, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromToInstkPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndInstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdInstkPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdInstkPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdInstkPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdInstkPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 終了入荷予定日検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で入荷予定日検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]
	 *  <DIR>
	 *  荷主コード<BR>
	 *  終了入荷予定日<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEdInstkPlanDate_Click(ActionEvent e) throws Exception
	{
		// 入荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 終了入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));
		
		// 範囲フラグ(開始)
		param.setParameter(
			ListInstockReceivePlanDateBusiness.RANGEINSTOCKPLANDATE_KEY,
			InstockReceiveParameter.RANGE_END);
			
		// TCDC区分
		param.setParameter(
			ListInstockReceivePlanDateBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 中継画面→検索画面
		redirect(DO_SRCH_INSTOCKPLANDATE, param, DO_SRCH_PROCESS);
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
	 * 仕入先検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で仕入先検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]
	 *  <DIR>
	 *  荷主コード<BR>
	 *  開始入荷予定日<BR>
	 *  終了入荷予定日<BR>
	 *  仕入先コード<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchSupplierCode_Click(ActionEvent e) throws Exception
	{
		// 入荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));
		
		// 終了入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));
		
		// 仕入先コード
		param.setParameter(
		        ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
		        txt_SupplierCode.getText());
		
		// 検索フラグ(予定)
		param.setParameter(
		        ListInstockReceiveSupplierBusiness.SEARCHSUPPLIER_KEY,
		        InstockReceiveParameter.SEARCHFLAG_PLAN);
		        
		// TCDC区分
		param.setParameter(
			ListInstockReceiveSupplierBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 中継画面→検索画面
		redirect(DO_SRCH_SUPPLIER_CODE, param, DO_SRCH_PROCESS);
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
	 * 開始伝票No.検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で伝票検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]
	 *  <DIR>
	 *  荷主コード<BR>
	 *  開始入荷予定日<BR>
	 *  終了入荷予定日<BR>
	 *  仕入先コード<BR>
	 *  開始伝票No.<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStartTicketNo_Click(ActionEvent e) throws Exception
	{
		// 入荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));
		
		// 終了入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));
		
		// 仕入先コード
		param.setParameter(
		        ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
		        txt_SupplierCode.getText());
		
		// 開始伝票No.
		param.setParameter(
		        ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY,
		        txt_StartTicketNo.getText());

		// 範囲フラグ（開始)
		param.setParameter(
		        ListInstockReceiveTicketBusiness.RANGETICKETNUMBER_KEY,
		        InstockReceiveParameter.RANGE_START);
		
		// 検索フラグ(予定)
		param.setParameter(
		        ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY,
		        InstockReceiveParameter.SEARCHFLAG_PLAN);
		        
		// TCDC区分
		param.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 中継画面→検索画面
		redirect(DO_SRCH_TICKET_NO, param, DO_SRCH_PROCESS);
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
	 * 終了伝票No.検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で伝票検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]
	 *  <DIR>
	 *  荷主コード<BR>
	 *  開始入荷予定日<BR>
	 *  終了入荷予定日<BR>
	 *  仕入先コード<BR>
	 *  終了伝票No.<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEndTicketNo_Click(ActionEvent e) throws Exception
	{
		// 入荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));
		
		// 終了入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));
		
		// 仕入先コード
		param.setParameter(
		        ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
		        txt_SupplierCode.getText());
		
		// 終了伝票No.
		param.setParameter(
		        ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY,
		        txt_EndTicketNo.getText());
		
		// 範囲フラグ（終了)
		param.setParameter(
		        ListInstockReceiveTicketBusiness.RANGETICKETNUMBER_KEY,
		        InstockReceiveParameter.RANGE_END);
		
		// 検索フラグ(予定)
		param.setParameter(
		        ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY,
		        InstockReceiveParameter.SEARCHFLAG_PLAN);
		        
		// TCDC区分
		param.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 中継画面→検索画面
		redirect(DO_SRCH_TICKET_NO, param, DO_SRCH_PROCESS);
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
	 * [パラメータ]
	 *  <DIR>
	 *  荷主コード<BR>
	 *  開始入荷予定日<BR>
	 *  終了入荷予定日<BR>
	 *  仕入先コード<BR>
	 *  開始伝票No.<BR>
	 *  終了伝票No.<BR>
	 * 	商品コード<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 入荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));
		
		// 終了入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));
		
		// 仕入先コード
		param.setParameter(
		        ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
		        txt_SupplierCode.getText());
		
		// 開始伝票No.
		param.setParameter(
		        ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY,
		        txt_StartTicketNo.getText());
		
		// 終了伝票No.
		param.setParameter(
		        ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY,
		        txt_EndTicketNo.getText());
		
		// 商品コード
		param.setParameter(
		        ListInstockReceiveItemBusiness.ITEMCODE_KEY,
		        txt_ItemCode.getText());
				
		// 検索フラグ（予定)
		param.setParameter(
		        ListInstockReceiveItemBusiness.SEARCHITEM_KEY,
		        InstockReceiveParameter.SEARCHFLAG_PLAN);
		        
		// TCDC区分
		param.setParameter(
			ListInstockReceiveItemBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 中継画面→検索画面
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
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
	 * 表示ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をパラメータにセットし別画面にクロス/DC入荷予定一覧リストボックスを表示します。<BR>
	 * 本メソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 * 1.入力項目をパラメータにセットする。<BR>
	 * 2.クロス/DC入荷予定一覧リストボックスを表示する。<BR>
	 * </DIR>
	 * 
	 * [パラメータ] *必須入力<BR>
	 * <DIR>
	 * 荷主コード*<BR>
	 * 開始入荷予定日<BR>
	 * 終了入荷予定日<BR>
	 * 仕入先コード<BR>
	 * 開始伝票No.<BR>
	 * 終了伝票No.<BR>
	 * 商品コード<BR>
	 * クロス／DC<BR>
	 * 作業状態<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		// クロス/DC予定リスト画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));
		
		// 終了入荷予定日
		param.setParameter(
		        ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY,
		        WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));
		
		// 仕入先コード
		param.setParameter(
		        ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
		        txt_SupplierCode.getText());
		
		// 開始伝票No.
		param.setParameter(
		        ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY,
		        txt_StartTicketNo.getText());
		
		// 終了伝票No.
		param.setParameter(
		        ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY,
		        txt_EndTicketNo.getText());
		
		// 商品コード
		param.setParameter(
		        ListInstockReceiveItemBusiness.ITEMCODE_KEY,
		        txt_ItemCode.getText());
		
		// クロス/DC
		// 全て
		if(rdo_CrossDCFlagAll.getChecked())
		{
		    param.setParameter(
		            ListInstockReceivePlanListBusiness.CROSSDCFLAG_KEY,
		            InstockReceiveParameter.TCDC_FLAG_ALL);
		}
		// クロス
		else if (rdo_CrossDCFlagCross.getChecked())
		{
		    param.setParameter(
		        ListInstockReceivePlanListBusiness.CROSSDCFLAG_KEY,    
		    	InstockReceiveParameter.TCDC_FLAG_CROSSTC);
		}
		// DC
		else if (rdo_CrossDCFlagDC.getChecked())
		{
		    param.setParameter(
			        ListInstockReceivePlanListBusiness.CROSSDCFLAG_KEY,    
			    	InstockReceiveParameter.TCDC_FLAG_DC);
		}
		
		// 作業状態
		// 全て
		if(pul_WorkStatusStorage.getSelectedIndex() == 0)
		{
			param.setParameter(
				ListInstockReceivePlanListBusiness.WORKSTATUSPLANLIST_KEY,
				InstockReceiveParameter.STATUS_FLAG_ALL);
		}
		// 未開始
		else if(pul_WorkStatusStorage.getSelectedIndex() == 1)
		{
			param.setParameter(
				ListInstockReceivePlanListBusiness.WORKSTATUSPLANLIST_KEY,
				InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		}
		// 作業中
		else if(pul_WorkStatusStorage.getSelectedIndex() == 2)
		{
			param.setParameter(
				ListInstockReceivePlanListBusiness.WORKSTATUSPLANLIST_KEY,
				InstockReceiveParameter.STATUS_FLAG_WORKING);
		}
		// 一部完了
		else if(pul_WorkStatusStorage.getSelectedIndex() == 3)
		{
			param.setParameter(
				ListInstockReceivePlanListBusiness.WORKSTATUSPLANLIST_KEY,
				InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		}
		// 完了
		else if(pul_WorkStatusStorage.getSelectedIndex() == 4)
		{
			param.setParameter(
				ListInstockReceivePlanListBusiness.WORKSTATUSPLANLIST_KEY,
				InstockReceiveParameter.STATUS_FLAG_COMPLETION);
		}

		// 中継画面->結果画面
		redirect(DO_SRCH_INSTOCK_LIST, param, DO_SRCH_PROCESS);
	    
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
	 * 概要：入力エリアの入力項目をパラメータにセットし印刷データ件数をチェックします。<BR>
	 * 印刷データがある場合はダイアログを表示します。無い場合はメッセージを表示します。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
	    // 荷主コードにフォーカスを設定します。
	    setFocus(txt_ConsignorCode);
		// 入力チェックを行います。
		if(!checkInputData())
		{
			return;
		}
		
		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 入力値をセットします。
			InstockReceiveParameter param = createParameter();
			
			// 件数チェックを行い、印刷データがある場合
			// 印刷確認ダイアログを表示する。
			// ない場合は、ダイアログを表示せず処理を終了する。
			WmsScheduler schedule = new InstockReceivePlanListSCH();
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
	    // 荷主コードが一件の場合、荷主コードを取得します。
	    txt_ConsignorCode.setText(getConsignorCode());
	    
	    //各フィールドに初期値を設定します。
	    txt_StrtInstkPlanDate.setText("");
	    txt_EdInstkPlanDate.setText("");
	    txt_SupplierCode.setText("");
	    txt_StartTicketNo.setText("");
	    txt_EndTicketNo.setText("");
	    txt_ItemCode.setText("");
	    
	    rdo_CrossDCFlagCross.setChecked(false);
	    rdo_CrossDCFlagDC.setChecked(false);
	    rdo_CrossDCFlagAll.setChecked(true);
	    pul_WorkStatusStorage.setSelectedIndex(0);
	    
	    // 荷主コードにフォーカスを設定します。
	    setFocus(txt_ConsignorCode);
	}


}
//end of class
