// $Id: ShippingPlanInquiryBusiness.java,v 1.1.1.1 2006/08/17 09:34:29 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingplaninquiry;

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
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingconsignor.ListShippingConsignorBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingcustomer.ListShippingCustomerBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingitem.ListShippingItemBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingplandate.ListShippingPlanDateBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingticket.ListShippingTicketBusiness;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;
import jp.co.daifuku.wms.shipping.schedule.ShippingPlanInquirySCH;
/**
 * Designer : Y.Hirata <BR>
 * Maker : Y.Hirata <BR>
 * <BR>
 * 出荷予定照会画面クラスです。<BR>
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
 *     開始出荷予定日 <BR>
 *     終了出荷予定日 <BR>
 *     出荷先コード <BR>
 *     開始伝票No. <BR>
 *     終了伝票No. <BR>
 *     商品コード <BR>
 *     作業状態 <BR>
 *     </DIR>
 * <BR>
 *   [返却データ] <BR>
 * <BR>
 *    <DIR>
 *    荷主コード <BR>
 *    荷主名称 <BR>
 *    出荷予定日 <BR>
 *    出荷先コード <BR>
 *    出荷先名称 <BR>
 *    出荷伝票No. <BR>
 *    出荷伝票行No. <BR>
 *    商品コード <BR>
 *    商品名称 <BR>
 *    ケース入数 <BR>
 *    ボール入数 <BR>
 *    ホスト予定ケース数 <BR>
 *    ホスト予定ピース数 <BR>
 *    実績ケース数 <BR>
 *    実績ピース数 <BR>
 *    状態 <BR>
 *    仕入先コード <BR>
 *    仕入先名称 <BR>
 *    入荷伝票No. <BR>
 *    入荷伝票行No. <BR>
 *    ケースITF <BR>
 *    ボールITF <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>Y.Hirata</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:29 $
 * @author  $Author: mori $
 */
public class ShippingPlanInquiryBusiness extends ShippingPlanInquiry implements WMSConstants
{
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
	 *   開始出荷予定日[なし] <BR>
	 *   終了出荷予定日[なし] <BR>
	 *   出荷先コード[なし] <BR>
	 *   開始伝票No.[なし] <BR>
	 *   終了伝票No.[なし] <BR>
	 *   商品コード[なし] <BR>
	 *   作業状態 [全て]<BR>
	 *   </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 荷主コード（ためうち）
		txt_SRConsignorCode.setText("");
		// 荷主名称（ためうち）
		txt_SRConsignorName.setText("");
		// リストセル
		lst_SShpPlanIqrSrch.clearRow();
		
		setInitDsp();
	}

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
		// 開始伝票No.
		String startticketno = param.getParameter(ListShippingTicketBusiness.STARTTICKETNUMBER_KEY);
		// 終了伝票No.
		String endticketno = param.getParameter(ListShippingTicketBusiness.ENDTICKETNUMBER_KEY);
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
		
		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);
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
		Connection conn = null;
		try
		{
			// 荷主コード
			txt_ConsignorCode.setText("");
			// 開始出荷予定日
			txt_StrtShpPlanDate.setText("");
			// 終了出荷予定日
			txt_EdShpPlanDate.setText("");
			// 出荷先コード
			txt_CustomerCode.setText("");
			// 開始伝票No.
			txt_StartTicketNo.setText("");
			// 終了伝票No.
			txt_EndTicketNo.setText("");
			// 商品コード
			txt_ItemCode.setText("");
			// 作業状態
			pul_WorkStatusShipping.setSelectedIndex(0);

			// 荷主コードにカーソル遷移
			setFocus(txt_ConsignorCode);
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ShippingPlanInquirySCH();
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
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 *  画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 *  該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。<BR>
	 *  また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     </DIR>
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
		
		// 作業状態(出荷予定情報の未開始、開始済、作業中、一部完了、完了)
		String[] search = new String[5];
		search[0] = new String(ShippingParameter.WORKSTATUS_UNSTARTING);
		search[1] = new String(ShippingParameter.WORKSTATUS_START);
		search[2] = new String(ShippingParameter.WORKSTATUS_NOWWORKING);
		search[3] = new String(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
		search[4] = new String(ShippingParameter.WORKSTATUS_FINISH);
		param.setParameter(ListShippingConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		
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
	public void btn_PSearchStrtShp_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 開始出荷予定日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。<BR>
	 * また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始出荷予定日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStrtShp_Click(ActionEvent e) throws Exception
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
	public void lbl_FromToShp_Server(ActionEvent e) throws Exception
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
	 * 出荷先コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。<BR>
	 * また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始出荷予定日 <BR>
	 *     終了出荷予定日 <BR>
	 *     出荷先コード <BR>
	 *     </DIR>
	 * <BR>
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
	public void btn_PSearchStrtTkt_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 開始伝票No.コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始出荷予定日 <BR>
	 *     終了出荷予定日 <BR>
	 *     出荷先コード <BR>
	 *     開始伝票No. <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStrtTkt_Click(ActionEvent e) throws Exception
	{
		
		// 開始伝票No.検索画面へ検索条件をセットする
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
		
		// 開始伝票No.
		param.setParameter(
			ListShippingTicketBusiness.STARTTICKETNUMBER_KEY,
			txt_StartTicketNo.getText());
			
		// 開始フラグをセット
		param.setParameter(
			ListShippingTicketBusiness.RANGETICKETNUMBER_KEY,
			ShippingParameter.RANGE_START);
			
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
	public void lbl_FromToTkt_Server(ActionEvent e) throws Exception
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
	public void btn_PSearchEdTkt_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 終了伝票No.の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始出荷予定日 <BR>
	 *     終了出荷予定日 <BR>
	 *     出荷先コード <BR>
	 *     終了伝票No. <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEdTkt_Click(ActionEvent e) throws Exception
	{
		
		// 終了伝票No.検索画面へ検索条件をセットする
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
		
		// 終了伝票No.
		param.setParameter(
			ListShippingTicketBusiness.ENDTICKETNUMBER_KEY,
			txt_EndTicketNo.getText());
			
		// 終了フラグをセット
		param.setParameter(
			ListShippingTicketBusiness.RANGETICKETNUMBER_KEY,
			ShippingParameter.RANGE_END);
			
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
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード* <BR>
	 *     開始出荷予定日 <BR>
	 *     終了出荷予定日 <BR>
	 *     出荷先コード <BR>
	 *     開始伝票No. <BR>
	 *     終了伝票No. <BR>
	 *     商品コード <BR>
	 *     </DIR>
	 * <BR>
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
			
		// 開始伝票No.
		param.setParameter(
			ListShippingTicketBusiness.STARTTICKETNUMBER_KEY,
			txt_StartTicketNo.getText());
			
		// 終了伝票No.
		param.setParameter(
			ListShippingTicketBusiness.ENDTICKETNUMBER_KEY,
			txt_EndTicketNo.getText());
			
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
	 *       (開始出荷予定日 <= 終了出荷予定日) <BR>
	 *       (開始伝票No. <= 終了伝票No.) <BR>
	 *       </DIR>
     *     2.スケジュールを開始します。 <BR>
     *       <DIR>
	 *       [パラメータ] *必須入力<BR>
	 *       <BR>
	 *         <DIR>
	 *         荷主コード* <BR>
	 *         開始出荷予定日 <BR>
	 *         終了出荷予定日 <BR>
	 *         出荷先コード <BR>
	 *         開始伝票No. <BR>
	 *         終了伝票No. <BR>
	 *         商品コード <BR>
	 *         作業状態 <BR>
	 *         </DIR>
	 *       </DIR>
     *     3.スケジュールの結果をためうちエリアに表示を行います。 <BR>
     *     4.カーソルを荷主コードにセットします。 <BR>
     * </DIR>
     * <BR>
     * <BR>
	 * リストセルの列番号一覧 <BR>
	 * <DIR>
	 *     1:出荷予定日 <BR>
	 *     2:出荷先ｺｰﾄﾞ <BR>
	 *     3:出荷伝票No. <BR>
	 *     4:商品ｺｰﾄﾞ <BR>
	 *     5:ｹｰｽ入数 <BR>
	 *     6:ﾎｽﾄ予定ｹｰｽ数 <BR>
	 *     7:実績ｹｰｽ数 <BR>
	 *     8:状態 <BR>
	 *     9:仕入先ｺｰﾄﾞ <BR>
	 *    10:入荷伝票No. <BR>
	 *    11:ｹｰｽITF <BR>
	 *    12:出荷先名称 <BR>
	 *    13:出荷伝票行No. <BR>
	 *    14:商品名称 <BR>
	 *    15:ﾎﾞｰﾙ入数 <BR>
	 *    16:ﾎｽﾄ予定ﾋﾟｰｽ数 <BR>
	 *    17:実績ﾋﾟｰｽ数 <BR>
	 *    18:仕入先名称 <BR>
	 *    19:入荷伝票行No. <BR>
	 *    20:ﾎﾞｰﾙITF <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		
		try
		{
			// 荷主コードにカーソル遷移
			setFocus(txt_ConsignorCode);
			
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// ためうちエリアをクリアする
			lst_SShpPlanIqrSrch.clearRow();
			txt_SRConsignorCode.setText("");
			txt_SRConsignorName.setText("");
			
			// 入力チェックを行う（書式、必須、禁止文字）
			// 荷主コード
			txt_ConsignorCode.validate();
			// 開始出荷予定日
			txt_StrtShpPlanDate.validate(false);
			// 終了出荷予定日
			txt_EdShpPlanDate.validate(false);
			// 出荷先コード
			txt_CustomerCode.validate(false);
			// 開始伝票No.
			txt_StartTicketNo.validate(false);
			// 終了伝票No.
			txt_EndTicketNo.validate(false);
			// 商品コード
			txt_ItemCode.validate(false);
			// 作業状態
			pul_WorkStatusShipping.validate(false);
			// 出荷予定日の大小チェックを行う
			if (txt_StrtShpPlanDate.getDate() != null && txt_EdShpPlanDate.getDate() != null)
			{
				if (txt_StrtShpPlanDate.getDate().after(txt_EdShpPlanDate.getDate()))
				{
					// エラーメッセージを表示し、終了する。
					// 6023045 = 開始出荷予定日は、終了出荷予定日より前の日付にしてください。
					message.setMsgResourceKey("6023045");
					return ;
				}
			}
			// 伝票No.の大小チェックを行う
			if(!StringUtil.isBlank(txt_StartTicketNo.getText()) && !StringUtil.isBlank(txt_EndTicketNo.getText()))
			{
				if(txt_StartTicketNo.getText().compareTo(txt_EndTicketNo.getText()) > 0)
				{
					// エラーメッセージを表示し、終了する。
					// 6023037 = 開始伝票Noは、終了伝票Noより小さい値にしてください。
					message.setMsgResourceKey("6023037");
					return ;
				}
			}
			
			// スケジュールパラメータをセットする
			ShippingParameter param = new ShippingParameter();
			
			param.setConsignorCode(txt_ConsignorCode.getText());
			param.setFromPlanDate(WmsFormatter.toParamDate(txt_StrtShpPlanDate.getDate()));
			param.setToPlanDate(WmsFormatter.toParamDate(txt_EdShpPlanDate.getDate()));
			param.setCustomerCode(txt_CustomerCode.getText());
			param.setFromTicketNo(txt_StartTicketNo.getText());
			param.setToTicketNo(txt_EndTicketNo.getText());
			param.setItemCode(txt_ItemCode.getText());
			// 作業状態
			// 全て
			if (pul_WorkStatusShipping.getSelectedIndex() == 0)
			{
				param.setStatusFlag(ShippingParameter.WORKSTATUS_ALL);
			}
			// 未開始
			else if (pul_WorkStatusShipping.getSelectedIndex() == 1)
			{
				param.setStatusFlag(ShippingParameter.WORKSTATUS_S_UNSTARTING);
			}
			// 作業中
			else if (pul_WorkStatusShipping.getSelectedIndex() == 2)
			{
				param.setStatusFlag(ShippingParameter.WORKSTATUS_NOWWORKING);
			}
			// 保留
			else if (pul_WorkStatusShipping.getSelectedIndex() == 3)
			{
				param.setStatusFlag(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART);
			}
			// 完了
			else if (pul_WorkStatusShipping.getSelectedIndex() == 4)
			{
				param.setStatusFlag(ShippingParameter.WORKSTATUS_FINISH);
			}

			// スケジュールを開始する
			WmsScheduler schedule = new ShippingPlanInquirySCH();
			ShippingParameter[] viewParam = (ShippingParameter[])schedule.query(conn, param);
			
			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了する。
			if( viewParam == null || viewParam.length == 0 )
			{
				// コネクションクローズ
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			// スケジュールが正常に完了して表示データを取得した場合、表示する。
			// 荷主コード（ためうち）
			txt_SRConsignorCode.setText(viewParam[0].getConsignorCode());
			// 荷主名（ためうち）
			txt_SRConsignorName.setText(viewParam[0].getConsignorName());

			// 出荷先名称
			String label_customername = DisplayText.getText("LBL-W0036");
			// 商品名称
			String label_itemname = DisplayText.getText("LBL-W0103");
			// 状態
			String label_status = DisplayText.getText("LBL-W0229");
			// 仕入先コード
			String label_suppliercode = DisplayText.getText("LBL-W0251");
			// 仕入先名称
			String label_suppliername = DisplayText.getText("LBL-W0253");
			// 入荷伝票No.
			String label_instockticketno = DisplayText.getText("LBL-W0091");
			// 入荷伝票行No.
			String label_instocklineno = DisplayText.getText("LBL-W0090");
			// ケースITF
			String label_caseitf = DisplayText.getText("LBL-W0010");
			// ボールITF
			String label_bundleitf = DisplayText.getText("LBL-W0006");
			
			// リストセルに値をセットする
			for(int i = 0; i < viewParam.length; i++)
			{

				// 行追加
				lst_SShpPlanIqrSrch.addRow();
				lst_SShpPlanIqrSrch.setCurrentRow(i + 1);
				
				// 検索結果を各セルにセットしていく
				// （1列目）
				// 出荷予定日
				lst_SShpPlanIqrSrch.setValue(1,  WmsFormatter.toDispDate(viewParam[i].getPlanDate(),this.getHttpRequest().getLocale()));
				// 出荷先コード
				lst_SShpPlanIqrSrch.setValue(2,  viewParam[i].getCustomerCode());
				// 出荷伝票No.
				lst_SShpPlanIqrSrch.setValue(3,  viewParam[i].getShippingTicketNo());
				// 商品コード
				lst_SShpPlanIqrSrch.setValue(4,  viewParam[i].getItemCode());
				// ケース入数
				lst_SShpPlanIqrSrch.setValue(5,  Formatter.getNumFormat(viewParam[i].getEnteringQty()));
				// ホスト予定ケース数
				lst_SShpPlanIqrSrch.setValue(6,  Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				// 実績ケース数
				lst_SShpPlanIqrSrch.setValue(7,  Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				// 状態
				lst_SShpPlanIqrSrch.setValue(8,  viewParam[i].getStatusName());
				// 仕入先コード
				lst_SShpPlanIqrSrch.setValue(9,  viewParam[i].getSupplierCode());
				// 入荷伝票No.
				lst_SShpPlanIqrSrch.setValue(10, viewParam[i].getInstockTicketNo());
				// ケースITF
				lst_SShpPlanIqrSrch.setValue(11, viewParam[i].getITF());
				
				// （2列目）
				//出荷先名称
				lst_SShpPlanIqrSrch.setValue(12, viewParam[i].getCustomerName());
				// 出荷伝票行No.
				lst_SShpPlanIqrSrch.setValue(13, Integer.toString(viewParam[i].getShippingLineNo()));
				// 商品名称
				lst_SShpPlanIqrSrch.setValue(14, viewParam[i].getItemName());
				// ボール入数
				lst_SShpPlanIqrSrch.setValue(15, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				// ホスト予定ピース数
				lst_SShpPlanIqrSrch.setValue(16, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				// 実績ピース数
				lst_SShpPlanIqrSrch.setValue(17, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				// 仕入先名称
				lst_SShpPlanIqrSrch.setValue(18, viewParam[i].getSupplierName());
				// 入荷伝票行No.
				lst_SShpPlanIqrSrch.setValue(19, Integer.toString(viewParam[i].getInstockLineNo()));
				// ボールITF
				lst_SShpPlanIqrSrch.setValue(20, viewParam[i].getBundleITF());
				
				//tool tipをセット
				ToolTipHelper toolTip = new ToolTipHelper();
				// 出荷先名称
				toolTip.add(label_customername,viewParam[i].getCustomerName());
				// 商品名称
				toolTip.add(label_itemname,viewParam[i].getItemName());
				// 状態
				toolTip.add(label_status,viewParam[i].getStatusName());
				// 仕入先コード
				toolTip.add(label_suppliercode,viewParam[i].getSupplierCode());
				// 仕入先名称
				toolTip.add(label_suppliername,viewParam[i].getSupplierName());
				// 入荷伝票No.
				toolTip.add(label_instockticketno,viewParam[i].getInstockTicketNo());
				// 入荷伝票行No.
				toolTip.add(label_instocklineno,viewParam[i].getInstockLineNo());
				// ケースITF
				toolTip.add(label_caseitf,viewParam[i].getITF());
				// ボールITF
				toolTip.add(label_bundleitf,viewParam[i].getBundleITF());
				
				lst_SShpPlanIqrSrch.setToolTip(i+1, toolTip.getText());

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
	public void lst_SShpPlanIqrSrch_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlanIqrSrch_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlanIqrSrch_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlanIqrSrch_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlanIqrSrch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlanIqrSrch_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShpPlanIqrSrch_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_SRConsignorName_InputComplete(ActionEvent e) throws Exception
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

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEdShp_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 終了出荷予定日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     終了出荷予定日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEdShp_Click(ActionEvent e) throws Exception
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
			
		// 終了フラグをセット
		param.setParameter(
			ListShippingPlanDateBusiness.RANGESHIPPINGPLANDATE_KEY,
			ShippingParameter.RANGE_END);
			
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingplandate/ListShippingPlanDate.do",
			param,
			"/progress.do");
	}


}
//end of class
