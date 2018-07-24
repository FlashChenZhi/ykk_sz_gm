// $Id: TcInstockReceiveQtyInquiryBusiness.java,v 1.1.1.1 2006/08/17 09:34:13 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.tcinstockreceiveqtyinquiry;
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
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivecustomer.ListInstockReceiveCustomerBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivedate.ListInstockReceiveDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.TcInstockReceiveQtyInquirySCH;

/**
 * Designer : H.Murakado<BR>
 * Maker : H.Murakado <BR>
 * <BR>
 * TC入荷実績照会画面クラスです。<BR>
 * TC入荷実績照会処理を行うスケジュールにパラメータを引き渡します。<BR>
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
 *	開始入荷受付日<BR>
 *	終了入荷受付日<BR>
 *	仕入先コード<BR>
 *	出荷先コード<BR>
 *	開始伝票No.<BR>
 *	終了伝票No.<BR>
 *	商品コード<BR>
 *	表示順*
 *		<DIR>
 *   	入荷受付日順<BR>
 *   	入荷予定日順<BR>
 *		</DIR>
 *		<DIR>
 *   	伝票No.順<BR>
 *   	商品コード順<BR>
 *		</DIR>
 * <BR>
 *   [出力用のデータ] <BR>
 * 	 <DIR>
 *   <BR>
 *  	入荷受付日<BR>
 *  	入荷予定日<BR>
 *  	仕入先コード<BR>
 *  	仕入先名称<BR>
 *  	出荷先コード<BR>
 *  	出荷先名称<BR>
 *  	伝票№<BR>
 *  	行№<BR>
 *  	商品コード<BR>
 *  	商品名称<BR>
 *  	ケース入数<BR>
 *  	ボール入数<BR>
 *  	作業予定ケース数<BR>
 *  	作業予定ピース数<BR>
 *  	実績ケース数<BR>
 *  	実績ピース数<BR>
 *  	欠品ケース数<BR>
 *  	欠品ピース数<BR>
 *  	賞味期限<BR>
 *  	ケースＩＴＦ<BR>
 *  	ボールＩＴＦ<BR>
 *  	作業者コード<BR>
 *  	作業者名<BR>
 * 	   </DIR>
 * 	  </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/09</TD><TD>H.Murakado</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:13 $
 * @author  $Author: mori $
 */
public class TcInstockReceiveQtyInquiryBusiness extends TcInstockReceiveQtyInquiry implements WMSConstants
{
	// Class fields --------------------------------------------------

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
	 *    1.タイトルを表示します。<BR>
	 *    2.入力エリアを初期設定します。<BR>
	 *    3.スケジュールを開始します。 <BR>
	 *    4.カーソルを荷主コードにセットします。<BR>
	 * </DIR>
	 * <BR>
	 * 項目[初期値] <BR>
	 * <BR>
	 * 荷主コード		[検索値] <BR>
	 * 開始入荷受付日	[なし] <BR>
	 * 終了入荷受付日	[なし] <BR>
	 * 仕入先コード		[なし] <BR>
	 * 出荷先コード		[なし] <BR>
	 * 開始伝票No.		[なし] <BR>
	 * 終了伝票No.		[なし] <BR>
	 * 商品コード		[なし] <BR>
	 * 表示順			[入荷受付日順] <BR>
	 * 					[伝票No.順] <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		setInitDsp();
		
		// 荷主コード（ためうち）
		txt_RConsignorCode.setText("");
		// 荷主名（ためうち）
		txt_RConsignorName.setText("");
		// リストセル
		lst_STcInstkRcvQtyIqr.clearRow();
	}
	
	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * <BR>
	 * 概要:確認ダイアログを表示します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		
		// 	パラメータ取得
		String wMenuParam = this.getHttpRequest().getParameter(MENUPARAM);
		
		if (wMenuParam != null)
		{
			// 画面名称
			String wTitle = CollectionUtils.getMenuParam(0, wMenuParam);
			// ファンクションID
			String wFunctionID = CollectionUtils.getMenuParam(1, wMenuParam);
			// メニューID
			String wMenuID = CollectionUtils.getMenuParam(2, wMenuParam);
			
			// 取得したパラメータをViewStateへ保持する
			this.getViewState().setString(M_TITLE_KEY, wTitle);
			this.getViewState().setString(M_FUNCTIONID_KEY, wFunctionID);
			this.getViewState().setString(M_MENUID_KEY, wMenuID);
			
			//画面名称をセットする
			lbl_SettingName.setResourceKey(wTitle);
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
		
		DialogParameters wParam = ((DialogEvent) e).getDialogParameters();
		
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String wConsignorCode = wParam.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 開始入荷受付日
		Date wStrDate = WmsFormatter.toDate(wParam.getParameter(ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY)
											,this.getHttpRequest().getLocale());
		// 終了入荷受付日
		Date wEndDate = WmsFormatter.toDate(wParam.getParameter(ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY)
											,this.getHttpRequest().getLocale());
		// 仕入先コード
		String wSupplierCode = wParam.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// 出荷先コード
		String wCustomerCode = wParam.getParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY);
		// 開始伝票No.
		String wStrTicketNo = wParam.getParameter(ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY);
		// 終了伝票No.
		String wEndTicketNo = wParam.getParameter(ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY);
		// 商品コード
		String wItemCode = wParam.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);
		
		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(wConsignorCode))
		{
			txt_ConsignorCode.setText(wConsignorCode);
		}
		// 開始入荷受付日
		if (!StringUtil.isBlank(wStrDate))
		{
			txt_StrtInstkAcpDate.setDate(wStrDate);
		}
		// 終了出荷日
		if (!StringUtil.isBlank(wEndDate))
		{
			txt_EdInstkAcpDate.setDate(wEndDate);
		}
		// 仕入先コード
		if (!StringUtil.isBlank(wSupplierCode))
		{
			txt_SupplierCode.setText(wSupplierCode);
		}
		// 出荷先コード
		if (!StringUtil.isBlank(wCustomerCode))
		{
			txt_CustomerCode.setText(wCustomerCode);
		}
		// 開始伝票No.
		if (!StringUtil.isBlank(wStrTicketNo))
		{
			txt_StartTicketNo.setText(wStrTicketNo);
		}
		// 終了伝票No.
		if (!StringUtil.isBlank(wEndTicketNo))
		{
			txt_EndTicketNo.setText(wEndTicketNo);
		}
		// 商品コード
		if (!StringUtil.isBlank(wItemCode))
		{
			txt_ItemCode.setText(wItemCode);
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
		
		// コネクション
		Connection wConn = null;
		
		try
		{
			// 荷主コードにカーソル遷移
			setFocus(txt_ConsignorCode);
			
			//荷主コード
			txt_ConsignorCode.setText("");
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
			// 入荷受付日順
			rdo_IsdInstkAcpDate.setChecked(true);
			// 入荷予定日順
			rdo_IsdInstkPlanDate.setChecked(false);
			// 伝票No.順
			rdo_TicTkt.setChecked(true);
			// 商品コード順
			rdo_TicItem.setChecked(false);
			
			// コネクション取得
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// クロス/DC入荷実績照会スケジュールのインスタンス生成
			WmsScheduler schedule = new TcInstockReceiveQtyInquirySCH();
			
			// スケジュール初期表示処理を実行する。
			InstockReceiveParameter wParam = (InstockReceiveParameter) schedule.initFind(wConn, null);
			
			// 荷主コードが1件の場合、初期表示させる。
			if (wParam != null)
			{
				txt_ConsignorCode.setText(wParam.getConsignorCode());
			}
			else
			{
				txt_ConsignorCode.setText("");
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
				if (wConn != null)
				{
					// コネクションクローズ
					wConn.close();
				}
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
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 *  画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 *  該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
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
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters wParam = new ForwardParameters();
		
		// 荷主コード
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 画面情報
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
			
		// TCDC区分
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);

			
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceiveconsignor/ListInstockReceiveConsignor.do",
			wParam, "/progress.do");
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
	 * 開始入荷受付日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始入荷受付日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStrtInstkAcpDate_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters wParam = new ForwardParameters();
		
		// 荷主コード
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
			
		// 開始フラグ
		wParam.setParameter(
			ListInstockReceiveDateBusiness.RANGEINSTOCKDATE_KEY,
			InstockReceiveParameter.RANGE_START);
			
		// TCDC区分
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
			
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceivedate/ListInstockReceiveDate.do",
			wParam, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromToInstkAcpDate_Server(ActionEvent e) throws Exception
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
	 * 終了入荷受付日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     終了入荷受付日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEdInstkAcpDate_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters wParam = new ForwardParameters();
		
		// 荷主コード
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 終了入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY,
			WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
			
		// 終了フラグ
		wParam.setParameter(
			ListInstockReceiveDateBusiness.RANGEINSTOCKDATE_KEY,
			InstockReceiveParameter.RANGE_END);
		
		
		// TCDC区分
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
			
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceivedate/ListInstockReceiveDate.do",
			wParam, "/progress.do");
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
	 * 仕入先コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始入荷受付日 <BR>
	 *     終了入荷受付日 <BR>
	 *     仕入先コード <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchSupplierCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters wParam = new ForwardParameters();
		
		// 荷主コード
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
			
		// 終了入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY,
			WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
			
		// 仕入先コード
		wParam.setParameter(
			ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
			txt_SupplierCode.getText());			
			
		// 画面情報
		wParam.setParameter(
			ListInstockReceiveSupplierBusiness.SEARCHSUPPLIER_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
			
		// TCDC区分
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
			
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceivesupplier/ListInstockReceiveSupplier.do",
			wParam, "/progress.do");
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
		ForwardParameters wParam = new ForwardParameters();
		// 荷主コード
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, 
			txt_ConsignorCode.getText());
		// 開始入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY, 
			WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
		// 終了入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY, 
			WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
		// 仕入先コード
		wParam.setParameter(
			ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, 
			txt_SupplierCode.getText());
		// 出荷先コード
		wParam.setParameter(
			ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, 
			txt_CustomerCode.getText());		
		// 検索フラグ
		wParam.setParameter(
			ListInstockReceiveCustomerBusiness.SEARCHCUSTOMER_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
		// TCDC区分
		wParam.setParameter(
			ListInstockReceiveCustomerBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceivecustomer/ListInstockReceiveCustomer.do",
			wParam,
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
		ForwardParameters wParam = new ForwardParameters();
		
		// 荷主コード
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
			
		// 終了入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY,
			WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
			
		// 仕入先コード
		wParam.setParameter(
			ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
			txt_SupplierCode.getText());
			
		// 出荷先コード
		wParam.setParameter(
			ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, 
			txt_CustomerCode.getText());					
		
		// 開始伝票No.
		wParam.setParameter(
			ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY,
			txt_StartTicketNo.getText());
		
		// 開始フラグ
		wParam.setParameter(
			ListInstockReceiveTicketBusiness.RANGETICKETNUMBER_KEY,
			InstockReceiveParameter.RANGE_START);
					
		// 画面情報
		wParam.setParameter(
			ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
		
		// TCDC区分
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
		
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceiveticket/ListInstockReceiveTicket.do",
			wParam, "/progress.do");
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
	 * 終了伝票No.の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始入荷受付日 <BR>
	 *     終了入荷受付日 <BR>
	 *     仕入先コード <BR>
	 * 	   出荷先コード <BR>
	 *     終了伝票No.<BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEndTicketNo_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters wParam = new ForwardParameters();
		
		// 荷主コード
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
			
		// 終了入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY,
			WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
			
		// 仕入先コード
		wParam.setParameter(
			ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
			txt_SupplierCode.getText());
			
		// 出荷先コード
		wParam.setParameter(
			ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, 
			txt_CustomerCode.getText());								
		
		// 終了伝票No.
		wParam.setParameter(
			ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY,
			txt_EndTicketNo.getText());
		
		// 終了フラグ
		wParam.setParameter(
			ListInstockReceiveTicketBusiness.RANGETICKETNUMBER_KEY,
			InstockReceiveParameter.RANGE_END);
					
		// 画面情報
		wParam.setParameter(
			ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
			
		// TCDC区分
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
			
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceiveticket/ListInstockReceiveTicket.do",
			wParam, "/progress.do");
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
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始入荷受付日 <BR>
	 *     終了入荷受付日 <BR>
	 *     仕入先コード <BR>
	 * 	   出荷先コード <BR>
	 *     開始伝票No.<BR>
	 *     終了伝票No.<BR>
	 *     商品コード <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters wParam = new ForwardParameters();
		
		// 荷主コード
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 開始入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.STARTINSTOCKDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
			
		// 終了入荷受付日
		wParam.setParameter(
			ListInstockReceiveDateBusiness.ENDINSTOCKDATE_KEY,
			WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
			
		// 仕入先コード
		wParam.setParameter(
			ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY,
			txt_SupplierCode.getText());
			
		// 出荷先コード
		wParam.setParameter(
			ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY, 
			txt_CustomerCode.getText());											
		
		// 開始伝票No.
		wParam.setParameter(
			ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY,
			txt_StartTicketNo.getText());
			
		// 終了伝票No.
		wParam.setParameter(
			ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY,
			txt_EndTicketNo.getText());
			
		// 商品コード
		wParam.setParameter(
			ListInstockReceiveItemBusiness.ITEMCODE_KEY,
			txt_ItemCode.getText());
		
		// 画面情報
		wParam.setParameter(
			ListInstockReceiveItemBusiness.SEARCHITEM_KEY,
			InstockReceiveParameter.SEARCHFLAG_RESULT);
			
		// TCDC区分
		wParam.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
			
		// 処理中画面->結果画面
		redirect(
			"/instockreceive/listbox/listinstockreceiveitem/ListInstockReceiveItem.do",
			wParam, "/progress.do");
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
	public void rdo_IsdInstkAcpDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_IsdInstkAcpDate_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_IsdInstkPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_IsdInstkPlanDate_Click(ActionEvent e) throws Exception
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
	 *       (開始入荷受付日 <= 終了入荷受付日) <BR>
	 *       (開始伝票№ <= 終了伝票№) <BR>
	 *       </DIR>
	 *     2.スケジュールを開始します。 <BR>
	 *       <DIR>
	 *       [パラメータ] *必須入力<BR>
	 *       <BR>
	 *         <DIR>
	 *         荷主コード* <BR>
	 *         開始入荷受付日 <BR>
	 *         終了入荷受付日 <BR>
	 *         仕入先コード <BR>
	 * 	   	   出荷先コード <BR>
	 *         開始伝票No. <BR>
	 *         終了伝票No. <BR>
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
	 *     1:入荷受付日 <BR>
	 *     2:仕入先ｺｰﾄﾞ <BR>
	 * 	   3:出荷先コード <BR>
	 *     4:伝票№ <BR>
	 *     5:商品ｺｰﾄﾞ <BR>
	 *     6:ｹｰｽ入数 <BR>
	 *     7:作業予定ｹｰｽ数 <BR>
	 *     8:実績ｹｰｽ数 <BR>
	 *     9:欠品ｹｰｽ数 <BR>
	 *    10:賞味期限 <BR>
	 *    11:ｹｰｽITF <BR>
	 *    12:作業者ｺｰﾄﾞ <BR>
	 *    13:入荷予定日 <BR>
	 *    14:仕入先名称 <BR>
	 *    15:出荷先名称 <BR>
	 *    16:行№ <BR>
	 *    17:商品名称 <BR>
	 *    18:ﾎﾞｰﾙ入数 <BR>
	 *    19:作業予定ﾋﾟｰｽ数 <BR>
	 *    20:実績ﾋﾟｰｽ数 <BR>
	 *    21:欠品ﾋﾟｰｽ数 <BR>
	 *    22:ﾎﾞｰﾙITF <BR>
	 *    23:作業者名 <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		// コネクション
		Connection wConn = null;
		// 入荷パラメータクラスのインスタンス生成
		InstockReceiveParameter wParam = new InstockReceiveParameter();
		// クロス/DC入荷実績照会スケジュールのインスタンス生成
		WmsScheduler schedule = new TcInstockReceiveQtyInquirySCH();
		
		try
		{
			// 荷主コードにカーソル遷移
			setFocus(txt_ConsignorCode);
			
			// コネクションの取得
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// ためうちエリアをクリアする
			lst_STcInstkRcvQtyIqr.clearRow();
			txt_RConsignorCode.setText("");
			txt_RConsignorName.setText("");
			
			// 入力チェックを行う（書式、必須、禁止文字）
			// 荷主コード
			txt_ConsignorCode.validate();
			// 開始入荷受付日
			txt_StrtInstkAcpDate.validate(false);
			// 終了入荷受付日
			txt_EdInstkAcpDate.validate(false);
			// 仕入先コード
			txt_SupplierCode.validate(false);
			// 仕入先コード
			txt_CustomerCode.validate(false);
			// 開始伝票No.
			txt_StartTicketNo.validate(false);
			// 終了伝票No.
			txt_EndTicketNo.validate(false);
			// 商品コード
			txt_ItemCode.validate(false);
			
			// 入荷日の大小チェックを行う
			if (!StringUtil.isBlank(txt_StrtInstkAcpDate.getDate()) && !StringUtil.isBlank(txt_EdInstkAcpDate.getDate()))
			{
				if (txt_StrtInstkAcpDate.getDate().after(txt_EdInstkAcpDate.getDate()))
				{
					// エラーメッセージを表示し、終了する。
					// 6023039 = 開始入荷受付日は、終了入荷受付日より前の日付にしてください。
					message.setMsgResourceKey("6023039");
					return ;
				}
			}
			
			// 伝票No.の大小チェックを行う
			if (!StringUtil.isBlank(txt_StartTicketNo.getText()) && !StringUtil.isBlank(txt_EndTicketNo.getText()))
			{
				if (txt_StartTicketNo.getText().compareTo(txt_EndTicketNo.getText()) > 0)
				{
					// エラーメッセージを表示し、終了する。
					// 6023037 = 開始伝票Noは、終了伝票Noより小さい値にしてください。
					message.setMsgResourceKey("6023037");
					return;
				}
			}
			
			// スケジュールパラメータをセットする
			// 荷主コード
			wParam.setConsignorCode(txt_ConsignorCode.getText());
			// 開始入荷受付日
			wParam.setFromInstockReceiveDate(WmsFormatter.toParamDate(txt_StrtInstkAcpDate.getDate()));
			// 終了入荷受付日
			wParam.setToInstockReceiveDate(WmsFormatter.toParamDate(txt_EdInstkAcpDate.getDate()));
			// 仕入先コード
			wParam.setSupplierCode(txt_SupplierCode.getText());
			// 出荷先コード
			wParam.setCustomerCode(txt_CustomerCode.getText());
			// 開始伝票No.
			wParam.setFromTicketNo(txt_StartTicketNo.getText());
			// 終了伝票No.
			wParam.setToTicketNo(txt_EndTicketNo.getText());
			// 商品コード
			wParam.setItemCode(txt_ItemCode.getText());
			// 表示順
			if (rdo_IsdInstkAcpDate.getChecked())
			{
				// 入荷受付日順
				wParam.setDspOrderDate(InstockReceiveParameter.DISPLAY_ORDER_INSTOCK_DAY);
			}
			else if (rdo_IsdInstkPlanDate.getChecked())
			{
				// 入荷予定日順
				wParam.setDspOrderDate(InstockReceiveParameter.DISPLAY_ORDER_INSTOCK_PLAN_DAY);
			}
			if (rdo_TicTkt.getChecked())
			{
				// 伝票No.順
				wParam.setDspOrderItem(InstockReceiveParameter.DISPLAY_ORDER_TICKET);
			}
			else if (rdo_TicItem.getChecked())
			{
				wParam.setDspOrderItem(InstockReceiveParameter.DISPLAY_ORDER_ITEM);
			}

			// スケジュールを開始する
			InstockReceiveParameter[] viewParam = (InstockReceiveParameter[]) schedule.query(wConn, wParam);
			
			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了する。
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			
			// スケジュールが正常に完了して表示データを取得した場合、表示する。
			// 荷主コード（ためうち）
			txt_RConsignorCode.setText(viewParam[0].getConsignorCode());
			// 荷主名（ためうち）
			txt_RConsignorName.setText(viewParam[0].getConsignorName());

			// 仕入先名称
			String label_suppliername = DisplayText.getText("LBL-W0253");
			// 出荷先名称
			String label_customername = DisplayText.getText("LBL-W0036");
			// 商品名称
			String label_itemname = DisplayText.getText("LBL-W0103");
			// ケースITF
			String label_caseitf = DisplayText.getText("LBL-W0010");
			// ボールITF
			String label_bundleitf = DisplayText.getText("LBL-W0006");
			// 作業者コード
			String label_workercode = DisplayText.getText("LBL-W0274");
			// 作業者名
			String label_workername = DisplayText.getText("LBL-W0276");
			
			// リストセルに値をセットする
			for (int i = 0; i < viewParam.length; i++)
			{
				// 行追加
				lst_STcInstkRcvQtyIqr .addRow();
				lst_STcInstkRcvQtyIqr.setCurrentRow(i + 1);
				
				// 検索結果を各セルにセットしていく
				// （1列目）
				// 入荷受付日
				lst_STcInstkRcvQtyIqr.setValue(1, WmsFormatter.toDispDate(viewParam[i].getInstockReceiveDate(),this.getHttpRequest().getLocale()));
				// 仕入先コード
				lst_STcInstkRcvQtyIqr.setValue(2, viewParam[i].getSupplierCode());
				// 出荷先コード
				lst_STcInstkRcvQtyIqr.setValue(3, viewParam[i].getCustomerCode());
				// 伝票No.
				lst_STcInstkRcvQtyIqr.setValue(4, viewParam[i].getInstockTicketNo());
				// 商品コード
				lst_STcInstkRcvQtyIqr.setValue(5, viewParam[i].getItemCode());
				// ケース入数
				lst_STcInstkRcvQtyIqr.setValue(6, Formatter.getNumFormat(viewParam[i].getEnteringQty()));
				// 作業予定ケース数
				lst_STcInstkRcvQtyIqr.setValue(7, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				// 実績ケース数
				lst_STcInstkRcvQtyIqr.setValue(8, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				// 欠品ケース数
				lst_STcInstkRcvQtyIqr.setValue(9, Formatter.getNumFormat(viewParam[i].getShortageCaseQty()));
				// 賞味期限
				lst_STcInstkRcvQtyIqr.setValue(10, viewParam[i].getUseByDate());
				// ケースITF
				lst_STcInstkRcvQtyIqr.setValue(11, viewParam[i].getITF());
				// 作業者コード
				lst_STcInstkRcvQtyIqr.setValue(12, viewParam[i].getWorkerCode());
				
				// （2列目）
				// 入荷予定日
				lst_STcInstkRcvQtyIqr.setValue(13, WmsFormatter.toDispDate(viewParam[i].getPlanDate(),this.getHttpRequest().getLocale()));
				// 仕入先名称
				lst_STcInstkRcvQtyIqr.setValue(14, viewParam[i].getSupplierName());
				// 出荷先名称
				lst_STcInstkRcvQtyIqr.setValue(15, viewParam[i].getCustomerName());
				// 行No.
				lst_STcInstkRcvQtyIqr.setValue(16, Integer.toString(viewParam[i].getInstockLineNo()));
				// 商品名称
				lst_STcInstkRcvQtyIqr.setValue(17, viewParam[i].getItemName());
				// ボール入数
				lst_STcInstkRcvQtyIqr.setValue(18, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				// 作業予定ピース数
				lst_STcInstkRcvQtyIqr.setValue(19, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				// 実績ピース数
				lst_STcInstkRcvQtyIqr.setValue(20, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				// 欠品ピース数
				lst_STcInstkRcvQtyIqr.setValue(21, Formatter.getNumFormat(viewParam[i].getShortagePieceQty()));
				// ボールITF
				lst_STcInstkRcvQtyIqr.setValue(22, viewParam[i].getBundleITF());
				// 作業者名
				lst_STcInstkRcvQtyIqr.setValue(23, viewParam[i].getWorkerName());

				//tool tipをセット
				ToolTipHelper toolTip = new ToolTipHelper();
				// 仕入先名称
				toolTip.add(label_suppliername, viewParam[i].getSupplierName());
				// 出荷先名称
				toolTip.add(label_customername, viewParam[i].getCustomerName());
				// 商品名称
				toolTip.add(label_itemname, viewParam[i].getItemName());
				// ケースITF
				toolTip.add(label_caseitf, viewParam[i].getITF());
				// ボールITF
				toolTip.add(label_bundleitf, viewParam[i].getBundleITF());
				// 作業者コード
				toolTip.add(label_workercode, viewParam[i].getWorkerCode());
				// 作業者名
				toolTip.add(label_workername, viewParam[i].getWorkerName());
				
				lst_STcInstkRcvQtyIqr.setToolTip(i + 1, toolTip.getText());
			}
			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
			
		}
		finally
		{
			try
			{
				if (wConn != null)
				{
					// コネクションのクローズを行う
					wConn.close();
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
	public void txt_RConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RConsignorName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STcInstkRcvQtyIqr_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STcInstkRcvQtyIqr_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STcInstkRcvQtyIqr_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STcInstkRcvQtyIqr_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STcInstkRcvQtyIqr_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STcInstkRcvQtyIqr_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STcInstkRcvQtyIqr_Click(ActionEvent e) throws Exception
	{
	}


}
//end of class
