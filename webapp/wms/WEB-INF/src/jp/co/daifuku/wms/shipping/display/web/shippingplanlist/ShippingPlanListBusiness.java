// $Id: ShippingPlanListBusiness.java,v 1.1.1.1 2006/08/17 09:34:29 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingplanlist;
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
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingconsignor.ListShippingConsignorBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingcustomer.ListShippingCustomerBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingitem.ListShippingItemBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingplandate.ListShippingPlanDateBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingticket.ListShippingTicketBusiness;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;
import jp.co.daifuku.wms.shipping.schedule.ShippingPlanListSCH;
/**
 * Designer : Y.Hirata <BR>
 * Maker : Y.Hirata <BR>
 * <BR>
 * 出荷予定リスト発行画面クラスです。<BR>
 * 出荷予定リスト発行処理を行うスケジュールにパラメータを引き渡します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_View_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  画面から入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールが表示用のデータを検索し、ポップアップ画面で表示します。<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   荷主コード*<BR>
 *   開始出荷予定日<BR>
 *   終了出荷予定日<BR>
 *   出荷先コード<BR>
 *   伝票No.<BR>
 *   商品コード<BR>
 *  </DIR>
 * </DIR>
 * 
 * <BR>
 * 2.印刷ボタン押下処理(<CODE>btn_PDisplay_Click</CODE>) <BR>
 * <BR>
 * <DIR>
 *  画面から入力した内容をパラメータにセットし、
 *  そのパラメータを基にスケジュールがデータを検索し印刷します。<BR>
 *  スケジュールは印刷に成功した場合はtrue、失敗した場合はfalseを返します。<BR>
 *  <BR>
 *  [パラメータ] *必須入力<BR>
 *  <DIR>
 *   荷主コード*<BR>
 *   開始出荷予定日<BR>
 *   終了出荷予定日<BR>
 *   出荷先コード<BR>
 *   伝票No.<BR>
 *   商品コード<BR>
 *  </DIR>
 * </DIR>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>Y.Hirata</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:29 $
 * @author  $Author: mori $
 */
public class ShippingPlanListBusiness extends ShippingPlanList implements WMSConstants
{
	// Class fields --------------------------------------------------
	// どのコントロールから呼び出されたダイアログかを保持：印刷ボタン
	protected static final String DIALOG_PRINT = "DIALOG_PRINT";
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *  項目名[初期値]
	 *  <DIR>
	 *  荷主コード      [該当荷主が1件しかない場合初期表示を行う]<BR>
	 *  開始出荷予定日  [なし]
	 *  終了出荷予定日  [なし]
	 *  出荷先コード    [なし]<BR>
	 *  伝票No.         [なし]<BR>
	 *  商品コード      [なし]<BR>
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
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		// 開始出荷予定日
		txt_StrtShpPlanDate.setText("");
		// 終了出荷予定日
		txt_EdShpPlanDate.setText("");
		// 出荷先コード
		txt_CustomerCode.setText("");
		// 伝票No.
		txt_TicketNo.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		
		// 荷主コードにカーソル遷移
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
		String consignorcode = param.getParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY);
		// 開始出荷予定日
		Date startdate = WmsFormatter.toDate(param.getParameter(ListShippingPlanDateBusiness.STARTSHIPPINGPLANDATE_KEY)
											,this.getHttpRequest().getLocale());
		// 終了出荷予定日
		Date enddate = WmsFormatter.toDate(param.getParameter(ListShippingPlanDateBusiness.ENDSHIPPINGPLANDATE_KEY)
											,this.getHttpRequest().getLocale());
		// 出荷先コード
		String customercode = param.getParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY);
		// 伝票No.
		String ticketno = param.getParameter(ListShippingTicketBusiness.TICKETNUMBER_KEY);
		// 商品コード
		String itemcode = param.getParameter(ListShippingItemBusiness.ITEMCODE_KEY);
	
		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 開始出荷予定日
		if (!StringUtil.isBlank(startdate))
		{
			txt_StrtShpPlanDate.setDate(startdate);
		}
		// 終了出荷予定日
		if (!StringUtil.isBlank(enddate))
		{
			txt_EdShpPlanDate.setDate(enddate);
		}
		// 出荷先コード
		if (!StringUtil.isBlank(customercode))
		{
			txt_CustomerCode.setText(customercode);
		}
		// 伝票No.
		if (!StringUtil.isBlank(ticketno))
		{
			txt_TicketNo.setText(ticketno);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		
		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);
		
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
			//btn_Help.setUrl(BusinessClassHelper.getHelpPath(functionID, this.getHttpRequest()) );
		}
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
			ShippingParameter[] param = new ShippingParameter[1];
			param[0] = createParameter();
	
			// スケジュールを開始する
			WmsScheduler schedule = new ShippingPlanListSCH();
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

	// Protected methods ---------------------------------------------
	/** 
	 * 入力エリアの入力値をセットしたパラメータオブジェクトを生成します。<BR>
	 * 
	 * @return 入力エリアの入力値を含んだパラメータオブジェクト
	 */
	protected ShippingParameter createParameter()
	{
		// スケジュールパラメータをセットする
		ShippingParameter param = new ShippingParameter();
		param = new ShippingParameter();
		// 荷主コード
		param.setConsignorCode(txt_ConsignorCode.getText());
		// 開始出荷予定日
		param.setFromPlanDate(WmsFormatter.toParamDate(txt_StrtShpPlanDate.getDate()));
		// 終了出荷予定日
		param.setToPlanDate(WmsFormatter.toParamDate(txt_EdShpPlanDate.getDate()));
		// 出荷先コード
		param.setCustomerCode(txt_CustomerCode.getText());
		// 伝票No.
		param.setShippingTicketNo(txt_TicketNo.getText());
		// 商品コード
		param.setItemCode(txt_ItemCode.getText());
		
		// 作業状態
		// 全て
		if(pul_WorkStatusShipping.getSelectedIndex() == 0)
		{
			param.setStatusFlag(ShippingParameter.WORKSTATUS_ALL);
		}
		// 未開始
		else if(pul_WorkStatusShipping.getSelectedIndex() == 1)
		{
			param.setStatusFlag(ShippingParameter.WORKSTATUS_UNSTARTING);
		}
		// 作業中
		else if(pul_WorkStatusShipping.getSelectedIndex() == 2)
		{
			param.setStatusFlag(ShippingParameter.WORKSTATUS_NOWWORKING);
		}
		// 保留
		else if(pul_WorkStatusShipping.getSelectedIndex() == 3)
		{
			param.setStatusFlag(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		// 完了
		else if(pul_WorkStatusShipping.getSelectedIndex() == 4)
		{
			param.setStatusFlag(ShippingParameter.WORKSTATUS_FINISH);
		}
		return param;	

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
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
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
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{

		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
				
		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
				
		// 画面情報
		param.setParameter(
			ListShippingConsignorBusiness.SEARCHCONSIGNOR_KEY,
			ShippingParameter.SEARCHFLAG_PLAN);
					
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingconsignor/ListShippingConsignor.do",
			param,
			"/progress.do");

	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrtShpPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtShpPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtShpPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrtShpPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStrtPlan_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 開始出荷予定日検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で出荷予定日検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  開始出荷予定日<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStrtPlan_Click(ActionEvent e) throws Exception
	{
		
		// 開始出荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 開始出荷予定日
		param.setParameter(
			ListShippingPlanDateBusiness.STARTSHIPPINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtShpPlanDate.getDate()));

		// 開始フラグをセット
		param.setParameter(
			ListShippingPlanDateBusiness.RANGESHIPPINGPLANDATE_KEY,
			ShippingParameter.RANGE_START);
			
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingplandate/ListShippingPlanDate.do",
			param,
			"/progress.do");
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
	public void lbl_EdShpPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdShpPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdShpPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EdShpPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdPlan_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 終了出荷予定日検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で出荷予定日検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  終了出荷予定日<BR>
	 *  </DIR>
	 * <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEdPlan_Click(ActionEvent e) throws Exception
	{
		
		// 終了出荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 終了出荷予定日
		param.setParameter(
			ListShippingPlanDateBusiness.ENDSHIPPINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdShpPlanDate.getDate()));
			
		// 開始フラグをセット
		param.setParameter(
			ListShippingPlanDateBusiness.RANGESHIPPINGPLANDATE_KEY,
			ShippingParameter.RANGE_END);
			
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingplandate/ListShippingPlanDate.do",
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
	public void btn_PSearchCust_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷先検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で出荷先検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  開始出荷予定日<BR>
	 *  終了出荷予定日<BR>
	 *  出荷先コード<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchCust_Click(ActionEvent e) throws Exception
	{
		
		// 出荷先検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 開始出荷予定日
		param.setParameter(
			ListShippingPlanDateBusiness.STARTSHIPPINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtShpPlanDate.getDate()));
			
		// 終了出荷予定日
		param.setParameter(
			ListShippingPlanDateBusiness.ENDSHIPPINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdShpPlanDate.getDate()));
			
		// 出荷先コード
		param.setParameter(
			ListShippingCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
		
		// 画面情報
		param.setParameter(
			ListShippingCustomerBusiness.SEARCHCUSTOMER_KEY,
			ShippingParameter.SEARCHFLAG_PLAN);
			
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingcustomer/ListShippingCustomer.do",
			param,
			"/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNo_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNo_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNo_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_TicketNo_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchTkt_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 伝票No.検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：このメソッドは検索条件をパラメータにセットし、
	 * その検索条件で伝票No.検索リストボックスを表示します。<BR>
	 * <BR>
	 * [パラメータ]*必須入力
	 *  <DIR>
	 *  荷主コード<BR>
	 *  開始出荷予定日<BR>
	 *  終了出荷予定日<BR>
	 *  出荷先コード<BR>
	 *  伝票No.<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchTkt_Click(ActionEvent e) throws Exception
	{
		
		// 伝票No.検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 開始出荷予定日
		param.setParameter(
			ListShippingPlanDateBusiness.STARTSHIPPINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtShpPlanDate.getDate()));
			
		// 終了出荷予定日
		param.setParameter(
			ListShippingPlanDateBusiness.ENDSHIPPINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdShpPlanDate.getDate()));
			
		// 出荷先コード
		param.setParameter(
			ListShippingCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
			
		// 伝票No.
		param.setParameter(
			ListShippingTicketBusiness.TICKETNUMBER_KEY,
			txt_TicketNo.getText());
			
		// 画面情報
		param.setParameter(
			ListShippingTicketBusiness.SEARCHTICKETNUMBER_KEY,
			ShippingParameter.SEARCHFLAG_PLAN);
			
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingticket/ListShippingTicket.do",
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
	public void btn_PSearchItem_Server(ActionEvent e) throws Exception
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
	 *  開始出荷予定日<BR>
	 *  終了出荷予定日<BR>
	 *  出荷先コード<BR>
	 *  伝票No.<BR>
	 *  商品コード<BR>
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchItem_Click(ActionEvent e) throws Exception
	{
		
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 開始出荷予定日
		param.setParameter(
			ListShippingPlanDateBusiness.STARTSHIPPINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtShpPlanDate.getDate()));
			
		// 終了出荷予定日
		param.setParameter(
			ListShippingPlanDateBusiness.ENDSHIPPINGPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdShpPlanDate.getDate()));
			
		// 出荷先コード
		param.setParameter(
			ListShippingCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
			
		// 伝票No.
		param.setParameter(
			ListShippingTicketBusiness.TICKETNUMBER_KEY,
			txt_TicketNo.getText());
			
		// 商品コード
		param.setParameter(
			ListShippingItemBusiness.ITEMCODE_KEY,
			txt_ItemCode.getText());
			
		// 画面情報
		param.setParameter(
			ListShippingItemBusiness.SEARCHITEM_KEY,
			ShippingParameter.SEARCHFLAG_PLAN);
			
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingitem/ListShippingItem.do",
			param,
			"/progress.do");
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
	 * 概要：入力チェックを行い入力項目をパラメータにセットしスケジュールを開始します。<BR>
	 * スケジュールは入力された検索条件の件数返します。<BR>
	 * <DIR>
	 * 1.入力チェック（必須入力、文字数、文字属性）<BR>
	 * 2.入力チェック（開始出荷予定日が終了出荷予定日より小さいこと）<BR>
	 * 3.入力項目をパラメータにセットする。<BR>
	 * 4.印刷スケジュールを開始し検索件数を取得する。<BR>
	 * 5.確認ダイアログボックスを表示し、確認します。<BR>
	 * "X件該当しました。印刷しますか？"<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Print_Click(ActionEvent e) throws Exception
	{
		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);
		// 入力チェックを行います。
		if(!checkInputData())
		{
			return;
		}

		Connection conn = null;
		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 入力値をセットします。
			ShippingParameter param = createParameter();

			// 件数チェックを行い、印刷データがある場合
			// 印刷確認ダイアログを表示する。
			// ない場合は、ダイアログを表示せず処理を終了する。
			WmsScheduler schedule = new ShippingPlanListSCH();
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
	 *  入力エリアの項目を初期値に戻します。ただし、作業者コードとパスワードはクリアしません。<BR>
	 *  初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。
	 *  </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 各入力フィールドに初期値をセットします。
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		// 開始出荷予定日
		txt_StrtShpPlanDate.setText("");
		// 終了出荷予定日
		txt_EdShpPlanDate.setText("");
		// 出荷先コード
		txt_CustomerCode.setText("");
		// 伝票No.
		txt_TicketNo.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// 作業状態
		pul_WorkStatusShipping.setSelectedIndex(0);
		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);
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
	 * 概要：入力エリアの入力項目をパラメータにセットし別画面で出荷予定リスト一覧リストボックスを表示します。<BR>
	 * 本メソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 * 1.入力項目をパラメータにセットする。<BR>
	 * 2.出荷予定リスト一覧リストボックスを表示する。<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PDisplay_Click(ActionEvent e) throws Exception
	{
		
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();
		
		// 荷主コードをセット
		forwardParam.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		
		// 出荷予定日をセット
		forwardParam.setParameter(ListShippingPlanDateBusiness.STARTSHIPPINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_StrtShpPlanDate.getDate()));
		forwardParam.setParameter(ListShippingPlanDateBusiness.ENDSHIPPINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_EdShpPlanDate.getDate()));
		
		// 出荷先コードをセット
		forwardParam.setParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		
		// 伝票No.をセット
		forwardParam.setParameter(ListShippingTicketBusiness.TICKETNUMBER_KEY, txt_TicketNo.getText());
		
		// 商品コードをセット
		forwardParam.setParameter(ListShippingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 作業状態
		// 全て
		if(pul_WorkStatusShipping.getSelectedIndex() == 0)
		{
			forwardParam.setParameter(ListShippingItemBusiness.WORKSTATUSITEM_KEY,
			ShippingParameter.WORKSTATUS_ALL);
		}
		// 未開始
		else if(pul_WorkStatusShipping.getSelectedIndex() == 1)
		{
			forwardParam.setParameter(ListShippingItemBusiness.WORKSTATUSITEM_KEY,
			ShippingParameter.WORKSTATUS_UNSTARTING);
		}
		// 作業中
		else if(pul_WorkStatusShipping.getSelectedIndex() == 2)
		{
			forwardParam.setParameter(ListShippingItemBusiness.WORKSTATUSITEM_KEY,
			ShippingParameter.WORKSTATUS_NOWWORKING);
		}
		// 保留
		else if(pul_WorkStatusShipping.getSelectedIndex() == 3)
		{
			forwardParam.setParameter(ListShippingItemBusiness.WORKSTATUSITEM_KEY,
			ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		}
		// 完了
		else if(pul_WorkStatusShipping.getSelectedIndex() == 4)
		{
			forwardParam.setParameter(ListShippingItemBusiness.WORKSTATUSITEM_KEY,
			ShippingParameter.WORKSTATUS_FINISH);
		}
				
		// 予定一覧検索リストボックスを表示する。
		redirect("/shipping/listbox/listshippingplanlist/ListShippingPlanList.do", forwardParam, "/progress.do");

	}

	/** 
	 * スケジュールから荷主コードを取得するためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主コードを返します。<BR>
	 * 
	 * @return 荷主コード
	 * @throws Exception 全ての例外を報告します。
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null ;
		
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			ShippingParameter param = new ShippingParameter();

			// スケジュールから荷主コードを取得する。
			WmsScheduler schedule = new ShippingPlanListSCH();
			param = (ShippingParameter)schedule.initFind(conn, param);

			if(param != null)
			{
				return param.getConsignorCode();
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
		return null;	

	}

	/** 
	 * 入力チェックを行うためのメソッドです。<BR>
	 * 入力に間違いがなければtrue、間違いがあればfalseを返します。<BR>
	 * 
	 * @return 入力チェックの結果(true：OK  false：NG)
	 * @throws Exception 全ての例外を報告します。
	 */
	private boolean checkInputData() throws Exception
	{
		// 入力チェック
		// 荷主コード
		txt_ConsignorCode.validate();
		// 開始出荷予定日
		txt_StrtShpPlanDate.validate(false);
		// 終了出荷予定日
		txt_EdShpPlanDate.validate(false);
		// 出荷先コード
		txt_CustomerCode.validate(false);
		// 伝票No.
		txt_TicketNo.validate(false);
		// 商品コード
		txt_ItemCode.validate(false);
		
		// 開始出荷予定日が終了出荷予定日より小さいこと
		if(!StringUtil.isBlank(txt_StrtShpPlanDate.getDate()) && !StringUtil.isBlank(txt_EdShpPlanDate.getDate()))
		{
			if(txt_StrtShpPlanDate.getDate().after(txt_EdShpPlanDate.getDate()))
			{
				// 6023045 = 開始出荷予定日は、終了出荷予定日より前の日付にしてください。
				message.setMsgResourceKey("6023045");
				return false;
				
			}
		}
		return true;

	}
	
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_ListOutput_Click(ActionEvent e) throws Exception
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
	public void pul_WorkStatusShipping_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void pul_WorkStatusShipping_Change(ActionEvent e) throws Exception
	{
	}


}
//end of class
