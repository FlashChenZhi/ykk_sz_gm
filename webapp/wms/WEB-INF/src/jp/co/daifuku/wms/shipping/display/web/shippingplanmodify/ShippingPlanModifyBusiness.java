// $Id: ShippingPlanModifyBusiness.java,v 1.1.1.1 2006/08/17 09:34:29 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingplanmodify;
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
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingplan.ListShippingPlanBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingplandate.ListShippingPlanDateBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingticket.ListShippingTicketBusiness;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;
import jp.co.daifuku.wms.shipping.schedule.ShippingPlanModifySCH;

/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * <BR>
 * 出荷予定データ修正・削除(基本情報設定)画面クラスです。<BR>
 * 画面から入力した内容をViewStateにセットし、そのパラメータを基に予定データ修正・削除(詳細)画面に遷移します。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.次へボタン押下処理(<CODE>btn_Next_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *    入力エリアの入力項目をViewStateにセットし、そのパラメータを基に予定データ修正・削除(詳細)画面に遷移します。<BR>
 * <BR>
 *    [ViewStateパラメータ] *必須入力<BR>
 * <BR>
 *    <DIR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 * 		荷主コード* <BR>
 * 		出荷予定日* <BR>
 * 		出荷先コード* <BR>
 * 		伝票No.* <BR>
 *    </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/23</TD><TD>H.Akiyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:29 $
 * @author  $Author: mori $
 */ 
public class ShippingPlanModifyBusiness extends ShippingPlanModify implements WMSConstants
{
	/**
	 * タイトルの受け渡しに使用するViewState用キーです
	 */
	public static final String TITLE_KEY = "TITLE_KEY";	
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
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
	 * 画面の初期化を行います。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *    1.カーソルを作業者コードに初期セットします。<BR>
	 * <BR>
	 *    項目名[初期値]<BR>
	 *    <DIR>
	 *       作業者コード[なし]<BR>
	 *       パスワード[なし]<BR>
	 *       荷主コード[出荷予定情報の未作業荷主が1件の場合、荷主コードを初期表示します。]<BR>
	 * 	 	 出荷予定日[なし]<BR>
	 * 	 	 出荷先コード[なし]<BR>
	 * 	 	 伝票No.[なし]<BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		// 2画面目から戻ってきた時画面タイトルをセットする
		if(!StringUtil.isBlank(this.getViewState().getString(TITLE_KEY)))
		{
			lbl_SettingName.setResourceKey(this.viewState.getString(TITLE_KEY));
		}

		// 基本情報設定のタブを前出しする
		tab_BscDtlMdfyDlt.setSelectedIndex(1);
		
		setInitView();
		
		// 戻るボタン押下時、ViewStateに値がセットされていれば、その値をセットする
		// 作業者コード
		if(!StringUtil.isBlank(this.getViewState().getString("WorkerCode")))
		{
			txt_WorkerCode.setText(this.getViewState().getString("WorkerCode"));
		}

		// パスワード
		if(!StringUtil.isBlank(this.getViewState().getString("Password")))
		{
			txt_Password.setText(this.getViewState().getString("Password"));
		}
		
		// 荷主コード
		if(!StringUtil.isBlank(this.getViewState().getString("ConsignorCode")))
		{
			txt_ConsignorCode.setText(this.getViewState().getString("ConsignorCode"));
		}
			
		// 出荷予定日
		if(!StringUtil.isBlank(this.getViewState().getString("ShippingPlanDate")))
		{
			txt_ShippingPlanDate.setDate(WmsFormatter.toDate(
				this.getViewState().getString("ShippingPlanDate"),this.getHttpRequest().getLocale()));
		}
			
		// 出荷先コード
		if(!StringUtil.isBlank(this.getViewState().getString("CustomerCode")))
		{
			txt_CustomerCode.setText(this.getViewState().getString("CustomerCode"));
		}
			
		// 伝票No.
		if(!StringUtil.isBlank(this.getViewState().getString("TicketNo")))
		{
			txt_TicketNo.setText(this.getViewState().getString("TicketNo"));
		}

		// エラーメッセージ
		if(!StringUtil.isBlank(this.getViewState().getString("Message")))
		{
			// エラーメッセージを表示
			message.setMsgResourceKey(this.getViewState().getString("Message"));
			// メッセージを表示した後、ViewStateのメッセージをクリア
			this.getViewState().setString("Message", "");
		}

	}
	
	/**
	 * 画面の初期化を行うメソッドです。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * [パラメーター] <BR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 作業者ｺｰﾄﾞ [なし/クリアボタン押下時はそのまま] <BR>
	 * ﾊﾟｽﾜｰﾄﾞ [なし/クリアボタン押下時はそのまま] <BR>
	 * 荷主コード [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 荷主名称 [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 出荷予定日 [なし] <BR>
	 * 出荷先コード [なし] <BR>
	 * 出荷先名称 [なし] <BR>
	 * 伝票No. [なし] <BR>
	 * <BR>
	 * </DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。
	 */
	private void setInitView() throws Exception
	{
		Connection conn = null;
		
		try
		{
			// 作業者ｺｰﾄﾞにフォーカス異動
			setFocus(txt_WorkerCode);
	
			// 荷主コード
			txt_ConsignorCode.setText("");
			// 出荷予定日
			txt_ShippingPlanDate.setText("");
			// 出荷先コード
			txt_CustomerCode.setText("");
			// 伝票No.
			txt_TicketNo.setText("");
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ShippingPlanModifySCH();

			ShippingParameter param = (ShippingParameter)schedule.initFind(conn, null);
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
			    {		
				    conn.rollback();
					conn.close();
			    }
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
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
		DialogParameters param = ((DialogEvent)e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY);
		// 出荷予定日
		Date shippingplandate = WmsFormatter.toDate(param.getParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY)
											,this.getHttpRequest().getLocale());
		// 出荷先コード
		String customercode = param.getParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY);
		// 伝票No.
		String ticketno = param.getParameter(ListShippingTicketBusiness.TICKETNUMBER_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 出荷予定日
		if (!StringUtil.isBlank(shippingplandate))
		{
			txt_ShippingPlanDate.setDate(shippingplandate);
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

		// フォーカスを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

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
	public void btn_PSearchCnsgnr_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で荷主一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 検索フラグ
		param.setParameter(ListShippingConsignorBusiness.SEARCHCONSIGNOR_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		// 作業状態をセット
		param.setParameter(ListShippingConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingconsignor/ListShippingConsignor.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ShippingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ShippingPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchShp_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷予定日の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で出荷予定日一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       出荷予定日 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchShp_Click(ActionEvent e) throws Exception
	{
		// 出荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 出荷予定日
		param.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, 
			WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		// 作業状態をセット
		param.setParameter(ListShippingPlanDateBusiness.WORKSTATUSSHIPPINGPLANDATE_KEY, search);
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingplandate/ListShippingPlanDate.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PShpPlanSrch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷予定検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で出荷予定データ一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード* <BR>
	 *       出荷予定日 <BR>
	 *       出荷先コード <BR>
	 *       伝票No <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PShpPlanSrch_Click(ActionEvent e) throws Exception
	{
		// 出荷予定データ検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 出荷予定日
		param.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, 
			WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		// 出荷先コード
		param.setParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		// 伝票No.
		param.setParameter(ListShippingTicketBusiness.TICKETNUMBER_KEY, txt_TicketNo.getText());
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		// 作業状態をセット
		param.setParameter(ListShippingPlanBusiness.WORKSTATUSSHIPPINGPLAN_KEY, search);
				
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingplan/ListShippingPlan.do", param, "/progress.do");
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
	 * 出荷先コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で出荷先一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       出荷予定日 <BR>
	 *       出荷先コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchCust_Click(ActionEvent e) throws Exception
	{
		// 出荷先検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 出荷予定日
		param.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, 
			WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		// 出荷先コード
		param.setParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		// 検索フラグ
		param.setParameter(ListShippingCustomerBusiness.SEARCHCUSTOMER_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		// 作業状態をセット
		param.setParameter(ListShippingCustomerBusiness.WORKSTATUSCUSTOMER_KEY, search);
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingcustomer/ListShippingCustomer.do", param, "/progress.do");
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
	 * 伝票Noの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で伝票No一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       出荷予定日 <BR>
	 *       出荷先コード <BR>
	 *       伝票No <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchTkt_Click(ActionEvent e) throws Exception
	{
		// 伝票No.検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListShippingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 出荷予定日
		param.setParameter(ListShippingPlanDateBusiness.SHIPPINGPLANDATE_KEY, 
			WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
		// 出荷先コード
		param.setParameter(ListShippingCustomerBusiness.CUSTOMERCODE_KEY, txt_CustomerCode.getText());
		// 伝票No.
		param.setParameter(ListShippingTicketBusiness.TICKETNUMBER_KEY, txt_TicketNo.getText());
		// 検索フラグ
		param.setParameter(ListShippingTicketBusiness.SEARCHTICKETNUMBER_KEY, ShippingParameter.SEARCHFLAG_PLAN);
		// 作業状態(未開始)
		String[] search = new String[1];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		// 作業状態をセット
		param.setParameter(ListShippingTicketBusiness.WORKSTATUSTICKETNUMBER_KEY, search);
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingticket/ListShippingTicket.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}


	/**
	 * 次へボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目を条件に、予定データ修正･削除(詳細)画面に遷移します。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *    2.スケジュールを開始します。<BR>
	 * 	  <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			作業者コード* <BR>
	 * 			パスワード* <BR>
	 * 			荷主コード* <BR>
	 * 			出荷予定日* <BR>
	 * 			出荷先コード* <BR>
	 * 			伝票No.* <BR>
	 *      </DIR>
	 *    </DIR>
	 *    3.スケジュールの結果がtrueであれば、入力項目を条件に次画面(予定登録詳細)に遷移します。<BR>
	 * 	  <DIR>
	 *   	[ViewStateパラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			作業者コード* <BR>
	 * 			パスワード* <BR>
	 * 			荷主コード* <BR>
	 * 			出荷予定日* <BR>
	 * 			出荷先コード* <BR>
	 * 			伝票No.* <BR>
	 *      </DIR>
	 *    </DIR>
	 *    falseの時はスケジュールから取得したメッセージを画面に出力します。<BR>
	 *    4.入力エリアの内容は保持します。<BR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
		
		// 必須入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_ShippingPlanDate.validate();
		txt_CustomerCode.validate();
		txt_TicketNo.validate();
		
		Connection conn = null;

		try
		{
			// スケジュールパラメータへセット
			ShippingParameter param = new ShippingParameter();
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 出荷予定日
			param.setPlanDate(WmsFormatter.toParamDate(txt_ShippingPlanDate.getDate()));
			// 出荷先コード
			param.setCustomerCode(txt_CustomerCode.getText());
			// 伝票No.
			param.setShippingTicketNo(txt_TicketNo.getText());
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ShippingPlanModifySCH();

			if(schedule.nextCheck(conn, param))
			{
				// Checkの結果がtrueの場合、ViewStateに値をセットして、次画面に遷移する
				
				// 作業者コード
				this.getViewState().setString("WorkerCode", txt_WorkerCode.getText());
				// パスワード
				this.getViewState().setString("Password", txt_Password.getText());
				// 荷主コード
				this.getViewState().setString("ConsignorCode", txt_ConsignorCode.getText());
				// 出荷予定日
				this.getViewState().setString("ShippingPlanDate", txt_ShippingPlanDate.getText());
				// 出荷先コード
				this.getViewState().setString("CustomerCode", txt_CustomerCode.getText());
				// 伝票No.
				this.getViewState().setString("TicketNo", txt_TicketNo.getText());
				// 画面タイトル
				this.getViewState().setString(TITLE_KEY, lbl_SettingName.getResourceKey());
				
				// 基本情報設定画面->詳細情報登録画面
				forward("/shipping/shippingplanmodify/ShippingPlanModify2.do");
			}
			else
			{
				// メッセージをセット
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
				// コネクションクローズ
				if (conn != null)
				{
					conn.rollback();
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
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
	 * <DIR>
	 *    1.入力エリアの項目を初期値に戻します。ただし、作業者コードとパスワードはクリアしません。<BR>
	 *    <DIR>
	 *  	･初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。<BR>
	 *    </DIR>
	 *    2.カーソルを作業者コードにセットします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		setInitView();
	}

}
//end of class
