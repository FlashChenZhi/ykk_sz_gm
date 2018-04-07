// $Id: InstockReceiveQtyInquiryBusiness.java,v 1.1.1.1 2006/08/17 09:34:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.instockreceiveqtyinquiry;
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
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivedate.ListInstockReceiveDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveQtyInquirySCH;

/**
 * Designer : H.Murakado<BR>
 * Maker : H.Murakado <BR>
 * <BR>
 * クロス／DC入荷実績照会画面クラスです。<BR>
 * クロス／DC入荷実績照会処理を行うスケジュールにパラメータを引き渡します。<BR>
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
 *	開始伝票No.<BR>
 *	終了伝票No.<BR>
 *	商品コード<BR>
 *	クロス／ＤＣ区分*<BR>
 *		<DIR>
 *   	全て<BR>
 *   	クロス<BR>
 *   	ＤＣ<BR>
 *		</DIR>
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
 *  	クロス／ＤＣ<BR>
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
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:11 $
 * @author  $Author: mori $
 */
public class InstockReceiveQtyInquiryBusiness extends InstockReceiveQtyInquiry implements WMSConstants
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
	 * <DIR>
	 * 項目[初期値] <BR>
	 * <BR>
	 * 荷主コード		[検索値] <BR>
	 * 開始入荷受付日	[なし] <BR>
	 * 終了入荷受付日	[なし] <BR>
	 * 仕入先コード		[なし] <BR>
	 * 開始伝票No.		[なし] <BR>
	 * 終了伝票No.		[なし] <BR>
	 * 商品コード		[なし] <BR>
	 * クロス／ＤＣ区分	[全て] <BR>
	 * 表示順			[入荷受付日順][伝票No.順] <BR>
	 * </DIR>
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
		lst_SInstkRcvQtyIqr.clearRow();
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
			// 開始伝票No.
			txt_StartTicketNo.setText("");
			// 終了伝票No.
			txt_EndTicketNo.setText("");
			// 商品コード
			txt_ItemCode.setText("");
			// クロス/DC：全て
			rdo_CrossDCFlag_All.setChecked(true);
			// クロス/DC：クロス
			rdo_CrossDCFlag_Cross.setChecked(false);
			// クロス/DC：DC
			rdo_CrossDCFlag_DC.setChecked(false);
			// 入荷受付日順
			rdo_IsdInstkAcpDate.setChecked(true);
			// 入荷予定日順
			rdo_IsdInstkPlanDate.setChecked(false);
			// 伝票No.順
			rdo_TicTkt.setChecked(true);
			// 商品コード順
			rdo_ItemCode.setChecked(false);
			
			// コネクション取得
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);

            // クロス/DC入荷実績照会スケジュールのインスタンス生成
			WmsScheduler schedule = new InstockReceiveQtyInquirySCH();
			
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
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);

			
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
			ListInstockReceiveDateBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
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
			ListInstockReceiveDateBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
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
			ListInstockReceiveSupplierBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
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
	 * 開始伝票No.の検索ボタンが押下されたときに呼ばれます。
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
	 *     開始伝票No.<BR>
	 *     </DIR>
	 * <BR>
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
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
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
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
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
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			
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
	public void lbl_CrossDCTwoByte_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_All_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_All_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_Cross_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_Cross_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_DC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlag_DC_Click(ActionEvent e) throws Exception
	{
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
	 *         開始伝票No. <BR>
	 *         終了伝票No. <BR>
	 *         商品コード <BR>
	 *         クロス／ＤＣ* <BR>
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
	 *     3:伝票№ <BR>
	 *     4:商品ｺｰﾄﾞ <BR>
	 *     5:ｹｰｽ入数 <BR>
	 *     6:作業予定ｹｰｽ数 <BR>
	 *     7:実績ｹｰｽ数 <BR>
	 *     8:欠品ｹｰｽ数 <BR>
	 *     9:賞味期限 <BR>
	 *    10:ｸﾛｽ/DC <BR>
	 *    11:ｹｰｽITF <BR>
	 *    12:作業者ｺｰﾄﾞ <BR>
	 *    13:入荷予定日 <BR>
	 *    14:仕入先名称 <BR>
	 *    15:行№ <BR>
	 *    16:商品名称 <BR>
	 *    17:ﾎﾞｰﾙ入数 <BR>
	 *    18:作業予定ﾋﾟｰｽ数 <BR>
	 *    19:実績ﾋﾟｰｽ数 <BR>
	 *    20:欠品ﾋﾟｰｽ数 <BR>
	 *    21:ﾎﾞｰﾙITF <BR>
	 *    22:作業者名 <BR>
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
		WmsScheduler schedule = new InstockReceiveQtyInquirySCH();
		
		try
		{
			// 荷主コードにカーソル遷移
			setFocus(txt_ConsignorCode);
			
			// コネクションの取得
			wConn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// ためうちエリアをクリアする
			lst_SInstkRcvQtyIqr.clearRow();
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
			// 開始伝票No.
			wParam.setFromTicketNo(txt_StartTicketNo.getText());
			// 終了伝票No.
			wParam.setToTicketNo(txt_EndTicketNo.getText());
			// 商品コード
			wParam.setItemCode(txt_ItemCode.getText());
			// クロス/DC
			if (rdo_CrossDCFlag_All.getChecked())
			{
				// 全て
				wParam.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
			}
			else if (rdo_CrossDCFlag_Cross.getChecked())
			{
				// クロス
				wParam.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
			}
			else if (rdo_CrossDCFlag_DC.getChecked())
			{
				// DC
				wParam.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
			}
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
			else if (rdo_ItemCode.getChecked())
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
			// 商品名称
			String label_itemname = DisplayText.getText("LBL-W0103");
			//作業予定ケース数
			String label_plancaseqty = DisplayText.getText("LBL-W0333");
			//作業予定ピース数
			String label_planpieceqty = DisplayText.getText("LBL-W0334");
			//実績ケース数
			String label_resultcaseqty = DisplayText.getText("LBL-W0167");
			//実績ピース数
			String label_resultpieceqty = DisplayText.getText("LBL-W0169");
			//欠品ケース数
			String label_shortagecaseqty = DisplayText.getText("LBL-W0208");
			//欠品ピース数
			String label_shortagepieceqty = DisplayText.getText("LBL-W0209");
			//賞味期限
			String label_usebydate = DisplayText.getText("LBL-W0270");
			//クロス/DC
			String label_crossdc = DisplayText.getText("LBL-W0028");
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
				lst_SInstkRcvQtyIqr.addRow();
				lst_SInstkRcvQtyIqr.setCurrentRow(i + 1);
				
				// 検索結果を各セルにセットしていく
				// （1列目）
				// 入荷受付日
				lst_SInstkRcvQtyIqr.setValue(1, WmsFormatter.toDispDate(viewParam[i].getInstockReceiveDate(),this.getHttpRequest().getLocale()));
				// 仕入先コード
				lst_SInstkRcvQtyIqr.setValue(2, viewParam[i].getSupplierCode());
				// 伝票No.
				lst_SInstkRcvQtyIqr.setValue(3, viewParam[i].getInstockTicketNo());
				// 商品コード
				lst_SInstkRcvQtyIqr.setValue(4, viewParam[i].getItemCode());
				// ケース入数
				lst_SInstkRcvQtyIqr.setValue(5, Formatter.getNumFormat(viewParam[i].getEnteringQty()));
				// 作業予定ケース数
				lst_SInstkRcvQtyIqr.setValue(6, Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				// 実績ケース数
				lst_SInstkRcvQtyIqr.setValue(7, Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				// 欠品ケース数
				lst_SInstkRcvQtyIqr.setValue(8, Formatter.getNumFormat(viewParam[i].getShortageCaseQty()));
				// 賞味期限
				lst_SInstkRcvQtyIqr.setValue(9, viewParam[i].getUseByDate());
				// クロス/DC
				lst_SInstkRcvQtyIqr.setValue(10, viewParam[i].getTcdcName());
				// ケースITF
				lst_SInstkRcvQtyIqr.setValue(11, viewParam[i].getITF());
				// 作業者コード
				lst_SInstkRcvQtyIqr.setValue(12, viewParam[i].getWorkerCode());
				
				// （2列目）
				// 入荷予定日
				lst_SInstkRcvQtyIqr.setValue(13, WmsFormatter.toDispDate(viewParam[i].getPlanDate(),this.getHttpRequest().getLocale()));
				// 仕入先名称
				lst_SInstkRcvQtyIqr.setValue(14, viewParam[i].getSupplierName());
				// 行No.
				lst_SInstkRcvQtyIqr.setValue(15, Integer.toString(viewParam[i].getInstockLineNo()));
				// 商品名称
				lst_SInstkRcvQtyIqr.setValue(16, viewParam[i].getItemName());
				// ボール入数
				lst_SInstkRcvQtyIqr.setValue(17, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				// 作業予定ピース数
				lst_SInstkRcvQtyIqr.setValue(18, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				// 実績ピース数
				lst_SInstkRcvQtyIqr.setValue(19, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				// 欠品ピース数
				lst_SInstkRcvQtyIqr.setValue(20, Formatter.getNumFormat(viewParam[i].getShortagePieceQty()));
				// ボールITF
				lst_SInstkRcvQtyIqr.setValue(21, viewParam[i].getBundleITF());
				// 作業者名
				lst_SInstkRcvQtyIqr.setValue(22, viewParam[i].getWorkerName());

				//tool tipをセット
				ToolTipHelper toolTip = new ToolTipHelper();
				// 仕入先名称
				toolTip.add(label_suppliername, viewParam[i].getSupplierName());
				// 商品名称
				toolTip.add(label_itemname, viewParam[i].getItemName());
				//2004 NOV 22
				//作業予定ケース数
				toolTip.add(label_plancaseqty, viewParam[i].getPlanCaseQty());
				//作業予定ピース数
				toolTip.add(label_planpieceqty, viewParam[i].getPlanPieceQty());
				//実績ケース数
				toolTip.add(label_resultcaseqty, viewParam[i].getResultCaseQty());
				//実績ピース数
				toolTip.add(label_resultpieceqty, viewParam[i].getResultPieceQty());
				//欠品ケース数
				toolTip.add(label_shortagecaseqty, viewParam[i].getShortageCaseQty());
				//欠品ピース数
				toolTip.add(label_shortagepieceqty, viewParam[i].getShortagePieceQty());
				//賞味期限
				toolTip.add(label_usebydate, viewParam[i].getUseByDate());
				//クロス/DC
				toolTip.add(label_crossdc, viewParam[i].getTcdcName());	
				// ケースITF
				toolTip.add(label_caseitf, viewParam[i].getITF());
				// ボールITF
				toolTip.add(label_bundleitf, viewParam[i].getBundleITF());
				// 作業者コード
				toolTip.add(label_workercode, viewParam[i].getWorkerCode());
				// 作業者名
				toolTip.add(label_workername, viewParam[i].getWorkerName());
				
				lst_SInstkRcvQtyIqr.setToolTip(i + 1, toolTip.getText());
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
	public void lst_SInstkRcvQtyIqr_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvQtyIqr_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvQtyIqr_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvQtyIqr_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvQtyIqr_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvQtyIqr_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvQtyIqr_Click(ActionEvent e) throws Exception
	{
	}

}
//end of class
