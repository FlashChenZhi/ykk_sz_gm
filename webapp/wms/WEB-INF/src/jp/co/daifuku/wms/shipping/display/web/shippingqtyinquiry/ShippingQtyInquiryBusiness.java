// $Id: ShippingQtyInquiryBusiness.java,v 1.1.1.1 2006/08/17 09:34:29 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingqtyinquiry;
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
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingdate.ListShippingDateBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingitem.ListShippingItemBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingticket.ListShippingTicketBusiness;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;
import jp.co.daifuku.wms.shipping.schedule.ShippingQtyInquirySCH;

/**
 * Designer : Y.Hirata <BR>
 * Maker : Y.Hirata <BR>
 * <BR>
 * 出荷実績照会画面クラスです。<BR>
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
 *     開始出荷日 <BR>
 *     終了出荷日 <BR>
 *     出荷先コード <BR>
 *     伝票No. <BR>
 *     商品コード <BR>
 *     表示順* <BR>
 *     </DIR>
 * <BR>
 *   [返却データ] <BR>
 * <BR>
 *     <DIR>
 *    荷主コード <BR>
 *    荷主名称 <BR>
 *    出荷日 <BR>
 *    出荷予定日 <BR>
 *    出荷先コード <BR>
 *    出荷先名称 <BR>
 *    伝票No. <BR>
 *    行No. <BR>
 *    商品コード <BR>
 *    商品名称 <BR>
 *    ケース入数 <BR>
 *    ボール入数 <BR>
 *    作業予定ケース数 <BR>
 *    作業予定ピース数 <BR>
 *    実績ケース数 <BR>
 *    実績ピース数 <BR>
 *    欠品ケース入数 <BR>
 *    欠品ピース入数 <BR>
 *    賞味期限 <BR>
 *    作業者コード <BR>
 *    作業者名 <BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/09</TD><TD>Y.Hirata(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:29 $
 * @author  $Author: mori $
 */
public class ShippingQtyInquiryBusiness extends ShippingQtyInquiry implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * 概要:このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *       1.カーソルを荷主コードにセットします。 <BR>
	 * </DIR>
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
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 *   <DIR>
	 *   荷主コード[荷主コード] <BR>
	 *     <DIR>
	 *     （実績情報の荷主が一件の場合） <BR>
	 *     </DIR>
	 *   開始出荷日[なし] <BR>
	 *   終了出荷日[なし] <BR>
	 *   出荷先コード[なし] <BR>
	 *   伝票No.[なし] <BR>
	 *   商品コード[なし] <BR>
	 *   表示順 <BR>
	 *     <DIR>
	 *     出荷日順[true] <BR>
	 *     出荷予定日順[false] <BR>
	 *     伝票No.順[true] <BR>
	 *     商品コード順[false] <BR>
	 *     </DIR>
	 *   </DIR>
	 * @param e ActionEvent
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		
		// 荷主コード（ためうち）
		txt_SRConsignorCode.setText("");
		// 荷主名（ためうち）
		txt_SRConsignorName.setText("");
		// リストセル
		lst_SShippingResultInquiry.clearRow();
		
		setInitDsp();

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
		// 開始出荷日
		Date startdate = WmsFormatter.toDate(param.getParameter(ListShippingDateBusiness.STARTSHIPPINGDATE_KEY)
								   			,this.getHttpRequest().getLocale());
		// 終了出荷日
		Date enddate = WmsFormatter.toDate(param.getParameter(ListShippingDateBusiness.ENDSHIPPINGDATE_KEY)
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
		// 開始出荷日
		if (!StringUtil.isBlank(startdate))
		{
			txt_StartShippingDate.setDate(startdate);
		}
		// 終了出荷日
		if (!StringUtil.isBlank(enddate))
		{
			txt_EndShippingDate.setDate(enddate);
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
			// 開始出荷日
			txt_StartShippingDate.setText("");
			// 終了出荷日
			txt_EndShippingDate.setText("");
			// 出荷先コード
			txt_CustomerCode.setText("");
			// 伝票No.
			txt_TicketNo.setText("");
			// 商品コード
			txt_ItemCode.setText("");
			// 出荷先順
			rdo_SpdShpDate.setChecked(true);
			// 出荷予定日順
			rdo_SpdShpPlanDate.setChecked(false);
			// 伝票No.順
			rdo_TicTkt.setChecked(true);
			// 商品コード順
			rdo_TicItem.setChecked(false);

			// 荷主コードにカーソル遷移
			setFocus(txt_ConsignorCode);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			
			WmsScheduler schedule = new ShippingQtyInquirySCH();
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
			ShippingParameter.SEARCHFLAG_RESULT);
			
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
	public void lbl_StartShippingDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartShippingDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartShippingDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StartShippingDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchStartDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 開始出荷日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。<BR>
	 * また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始出荷日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStartDate_Click(ActionEvent e) throws Exception
	{
		
		// 開始出荷日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 開始出荷日
		param.setParameter(
			ListShippingDateBusiness.STARTSHIPPINGDATE_KEY,
			WmsFormatter.toParamDate(txt_StartShippingDate.getDate()));

		// 開始フラグをセット
		param.setParameter(
			ListShippingDateBusiness.RANGESHIPPINGDATE_KEY,
			ShippingParameter.RANGE_START);
			
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingdate/ListShippingDate.do",
			param,
			"/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromToShpDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_EndShippingDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndShippingDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndShippingDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_EndShippingDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 終了出荷日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。<BR>
	 * また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     終了出荷日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEndDate_Click(ActionEvent e) throws Exception
	{
		
		// 終了出荷日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 終了出荷日
		param.setParameter(
			ListShippingDateBusiness.ENDSHIPPINGDATE_KEY,
			WmsFormatter.toParamDate(txt_EndShippingDate.getDate()));

		// 終了フラグをセット
		param.setParameter(
			ListShippingDateBusiness.RANGESHIPPINGDATE_KEY,
			ShippingParameter.RANGE_END);
			
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingdate/ListShippingDate.do",
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
	 *     出荷先コード <BR>
	 *     開始出荷日 <BR>
	 *     終了出荷日 <BR>
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
			
		// 開始出荷日
		param.setParameter(
			ListShippingDateBusiness.STARTSHIPPINGDATE_KEY,
			WmsFormatter.toParamDate(txt_StartShippingDate.getDate()));
			
		// 終了出荷日
		param.setParameter(
			ListShippingDateBusiness.ENDSHIPPINGDATE_KEY,
			WmsFormatter.toParamDate(txt_EndShippingDate.getDate()));
			
		// 出荷先コード
		param.setParameter(
			ListShippingCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
		
		// 画面情報
		param.setParameter(
			ListShippingCustomerBusiness.SEARCHCUSTOMER_KEY,
			ShippingParameter.SEARCHFLAG_RESULT);
			
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
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。<BR>
	 * また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始出荷日 <BR>
	 *     終了出荷日 <BR>
	 *     出荷先コード <BR>
	 *     伝票No. <BR>
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
			
		// 開始出荷日
		param.setParameter(
			ListShippingDateBusiness.STARTSHIPPINGDATE_KEY,
			WmsFormatter.toParamDate(txt_StartShippingDate.getDate()));
			
		// 終了出荷日
		param.setParameter(
			ListShippingDateBusiness.ENDSHIPPINGDATE_KEY,
			WmsFormatter.toParamDate(txt_EndShippingDate.getDate()));
			
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
			ShippingParameter.SEARCHFLAG_RESULT);
			
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
	public void lbl_DspOrder_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_SpdShpDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_SpdShpDate_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_SpdShpPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_SpdShpPlanDate_Click(ActionEvent e) throws Exception
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
	 *       (開始出荷日 <= 終了出荷日) <BR>
	 *       </DIR>
     *     2.スケジュールを開始します。 <BR>
     *       <DIR>
	 *       [パラメータ] *必須入力<BR>
	 *       <BR>
	 *         <DIR>
	 *         荷主コード* <BR>
	 *         開始出荷日 <BR>
	 *         終了出荷日 <BR>
	 *         出荷先コード <BR>
	 *         伝票No. <BR>
	 *         商品コード <BR>
	 *         表示順* <BR>
	 *         </DIR>
	 *       </DIR>
     *     3.スケジュールの結果をためうちエリアに表示を行います。 <BR>
     *     4.カーソルを荷主コードにセットします。 <BR>
     * </DIR>
     * <BR>
     * <BR>
	 * リストセルの列番号一覧 <BR>
	 * <DIR>
	 *     1:出荷日 <BR>
	 *     2:出荷先ｺｰﾄﾞ <BR>
	 *     3:伝票No. <BR>
	 *     4:商品ｺｰﾄﾞ <BR>
	 *     5:ｹｰｽ入数 <BR>
	 *     6:作業予定ｹｰｽ数 <BR>
	 *     7:実績ｹｰｽ数 <BR>
	 *     8:欠品ｹｰｽ数 <BR>
	 *     9:賞味期限 <BR>
	 *    10:作業者コード <BR>
	 *    11:出荷予定日 <BR>
	 *    12:出荷先名称 <BR>
	 *    13:行No. <BR>
	 *    14:商品名称 <BR>
	 *    15:ﾎﾞｰﾙ入数 <BR>
	 *    16:作業予定ﾋﾟｰｽ数 <BR>
	 *    17:実績ﾋﾟｰｽ数 <BR>
	 *    19:欠品ﾋﾟｰｽ数 <BR>
	 *    19:作業者名 <BR>
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
			lst_SShippingResultInquiry.clearRow();
			txt_SRConsignorCode.setText("");
			txt_SRConsignorName.setText("");
			
			// 入力チェックを行う（書式、必須、禁止文字）
			// 荷主コード
			txt_ConsignorCode.validate();
			// 開始出荷日
			txt_StartShippingDate.validate(false);
			// 終了出荷日
			txt_EndShippingDate.validate(false);
			// 出荷先コード
			txt_CustomerCode.validate(false);
			// 伝票No.
			txt_TicketNo.validate(false);
			// 商品コード
			txt_ItemCode.validate(false);
			
			// 出荷日の大小チェックを行う
			if(!StringUtil.isBlank(txt_StartShippingDate.getDate()) && !StringUtil.isBlank(txt_EndShippingDate.getDate()))
			{
				if (txt_StartShippingDate.getDate().after(txt_EndShippingDate.getDate()))
				{
					// エラーメッセージを表示し、終了する。
					// 6023049 = 開始出荷日は、終了出荷日より前の日付にしてください。
					message.setMsgResourceKey("6023049");
					return ;
				}
			}
			
			// スケジュールパラメータをセットする
			ShippingParameter param = new ShippingParameter();
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 開始出荷日
			param.setFromShippingDate(WmsFormatter.toParamDate(txt_StartShippingDate.getDate()));
			// 終了出荷日
			param.setToShippingDate(WmsFormatter.toParamDate(txt_EndShippingDate.getDate()));
			// 出荷先コード
			param.setCustomerCode(txt_CustomerCode.getText());
			// 伝票No.
			param.setShippingTicketNo(txt_TicketNo.getText());
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			
			// 表示順1
			// 出荷日順
			if (rdo_SpdShpDate.getChecked())
			{
				param.setDspOrder(ShippingParameter.DSPORDER_SHIPPINGDATE);
			}
			// 出荷予定日順
			else if (rdo_SpdShpPlanDate.getChecked())
			{
				param.setDspOrder(ShippingParameter.DSPORDER_SHIPPINGPLANDATE);
			}
			
			// 表示順2
			// 伝票No.順
			if (rdo_TicTkt.getChecked())
			{
				param.setDspOrder2(ShippingParameter.DSPORDER_TICKET);
			}
			// 商品コード順
			else if (rdo_TicItem.getChecked())
			{
				param.setDspOrder2(ShippingParameter.DSPORDER_ITEM);
			}

			// スケジュールを開始する
			WmsScheduler schedule = new ShippingQtyInquirySCH();
			ShippingParameter[] viewParam = (ShippingParameter[])schedule.query(conn, param);

			
			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了する。
			if( viewParam == null || viewParam.length == 0 )
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			// スケジュールが正常に完了して表示データを取得した場合、表示する。
			// 荷主コード（ためうち）
			txt_SRConsignorCode.setText(viewParam[0].getConsignorCode());
			// 荷主名（ためうち）
			txt_SRConsignorName.setText(viewParam[0].getConsignorName());

			// 出荷先コード
			String label_customercode = DisplayText.getText("LBL-W0036");
			// 商品コード
			String label_itemcode = DisplayText.getText("LBL-W0103");
			// 欠品ケース数
			String label_shortagecaseqty = DisplayText.getText("LBL-W0208");
			// 欠品ピース数
			String label_shortagepieceqty = DisplayText.getText("LBL-W0209");
			// 賞味期限
			String label_usebydate = DisplayText.getText("LBL-W0270");
			// 作業者コード
			String label_workercode = DisplayText.getText("LBL-W0274");
			// 作業者名
			String label_workername = DisplayText.getText("LBL-W0276");
			
			// リストセルに値をセットする
			for(int i = 0; i < viewParam.length; i++)
			{

				// 行追加
				lst_SShippingResultInquiry.addRow();
				lst_SShippingResultInquiry.setCurrentRow(i + 1);
				
				// 検索結果を各セルにセットしていく
				// （1列目）
				// 出荷日
				lst_SShippingResultInquiry.setValue(1, WmsFormatter.toDispDate(viewParam[i].getShippingDate(),this.getHttpRequest().getLocale()));
				// 出荷先コード
				lst_SShippingResultInquiry.setValue(2, viewParam[i].getCustomerCode());
				// 伝票No.
				lst_SShippingResultInquiry.setValue(3, viewParam[i].getShippingTicketNo());
				// 商品コード
				lst_SShippingResultInquiry.setValue(4, viewParam[i].getItemCode());
				// ケース入数
				lst_SShippingResultInquiry.setValue(5, Formatter.getNumFormat(viewParam[i].getEnteringQty()));
				// 作業予定ケース数
				lst_SShippingResultInquiry.setValue(6, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				// 実績ケース数
				lst_SShippingResultInquiry.setValue(7, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				// 欠品ケース数
				lst_SShippingResultInquiry.setValue(8, Formatter.getNumFormat(viewParam[i].getShortageCaseQty()));
				// 賞味期限
				lst_SShippingResultInquiry.setValue(9, viewParam[i].getUseByDate());
				// 作業者コード
				lst_SShippingResultInquiry.setValue(10, viewParam[i].getWorkerCode());
				
				// （2列目）
				// 出荷予定日
				lst_SShippingResultInquiry.setValue(11, WmsFormatter.toDispDate(viewParam[i].getPlanDate(),this.getHttpRequest().getLocale()));
				// 出荷先名称
				lst_SShippingResultInquiry.setValue(12, viewParam[i].getCustomerName());
				// 行No.
				lst_SShippingResultInquiry.setValue(13, Integer.toString(viewParam[i].getShippingLineNo()));
				// 商品名称
				lst_SShippingResultInquiry.setValue(14, viewParam[i].getItemName());
				// ボール入数
				lst_SShippingResultInquiry.setValue(15, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				// 作業予定ピース数
				lst_SShippingResultInquiry.setValue(16, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				// 実績ピース数
				lst_SShippingResultInquiry.setValue(17, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				// 欠品ピース数
				lst_SShippingResultInquiry.setValue(18, Formatter.getNumFormat(viewParam[i].getShortagePieceQty()));
				// 作業者名
				lst_SShippingResultInquiry.setValue(19, viewParam[i].getWorkerName());

				//tool tipをセット
				ToolTipHelper toolTip = new ToolTipHelper();
				// 出荷先コード
				toolTip.add(label_customercode,viewParam[i].getCustomerName());
				// 商品コード
				toolTip.add(label_itemcode,viewParam[i].getItemName());
				// 欠品ケース数
				toolTip.add(label_shortagecaseqty,Formatter.getNumFormat(viewParam[i].getShortageCaseQty()));
				// 欠品ピース数
				toolTip.add(label_shortagepieceqty,Formatter.getNumFormat(viewParam[i].getShortagePieceQty()));
				// 賞味期限
				toolTip.add(label_usebydate,viewParam[i].getUseByDate());
				// 作業者コード
				toolTip.add(label_workercode,viewParam[i].getWorkerCode());
				// 作業者名
				toolTip.add(label_workername,viewParam[i].getWorkerName());
				
				lst_SShippingResultInquiry.setToolTip(i+1, toolTip.getText());
				
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
	public void lst_SShippingResultInquiry_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShippingResultInquiry_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShippingResultInquiry_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShippingResultInquiry_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShippingResultInquiry_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShippingResultInquiry_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SShippingResultInquiry_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_SConsignor_Server(ActionEvent e) throws Exception
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
	 * 伝票No.コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始出荷日 <BR>
	 *     終了出荷日 <BR>
	 *     出荷先コード <BR>
	 *     伝票No. <BR>
	 *     </DIR>
	 * <BR>
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
			
		// 開始出荷日
		param.setParameter(
			ListShippingDateBusiness.STARTSHIPPINGDATE_KEY,
			WmsFormatter.toParamDate(txt_StartShippingDate.getDate()));
			
		// 終了出荷日
		param.setParameter(
			ListShippingDateBusiness.ENDSHIPPINGDATE_KEY,
			WmsFormatter.toParamDate(txt_EndShippingDate.getDate()));
			
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
			ShippingParameter.SEARCHFLAG_RESULT);
			
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
	public void rdo_TicItem_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_TicItem_Click(ActionEvent e) throws Exception
	{
	}
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void tab_W_Inquiry_Click(ActionEvent e) throws Exception
	{
	}


}
//end of class
