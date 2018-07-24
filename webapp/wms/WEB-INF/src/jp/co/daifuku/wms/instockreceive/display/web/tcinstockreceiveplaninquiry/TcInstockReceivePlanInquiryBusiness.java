// $Id: TcInstockReceivePlanInquiryBusiness.java,v 1.1.1.1 2006/08/17 09:34:13 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.tcinstockreceiveplaninquiry;
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
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.TcInstockReceivePlanInquirySCH;

/**
 * Designer : T.Yoshino <BR>
 * Maker : A.Nagasawa <BR>
 * <BR>
 * TC入荷予定照会画面クラスです。<BR>
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
 *     開始入荷予定日 <BR>
 *     終了入荷予定日 <BR>
 *     仕入先コード <BR>
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
 *    入荷予定日 <BR>
 *    仕入先コード <BR>
 *    仕入先名称 <BR>
 *    出荷先コード <BR>
 *    出荷先名称 <BR>
 *    入荷伝票No. <BR>
 *    入荷伝票行No. <BR>
 *    商品コード <BR>
 *    商品名称 <BR>
 *    ケース入数 <BR>
 *    ボール入数 <BR>
 *    ホスト予定ケース数 <BR>
 *    ホスト予定ピース数 <BR>
 *    実績ケース数 <BR>
 *    実績ピース数 <BR>
 *    状態 <BR>
 *    ケースITF <BR>
 *    ボールITF <BR>
 *    </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/16</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:13 $
 * @author  $Author: mori $
 */
public class TcInstockReceivePlanInquiryBusiness extends TcInstockReceivePlanInquiry implements WMSConstants
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
	 *   開始入荷予定日[なし] <BR>
	 *   終了入荷予定日[なし] <BR>
	 *   仕入先コード[なし] <BR>
	 *   出荷先コード[なし] <BR>
	 *   開始伝票No.[なし] <BR>
	 *   終了伝票No.[なし] <BR>
	 *   商品コード[なし] <BR>
	 *   作業状態 [全て]<BR>
	 *   </DIR>
	 * @param e ActionEvent
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		setInitDsp();
		
		// 荷主コード（ためうち）
		txt_RConsignorCode.setText("");
		// 荷主名称（ためうち）
		txt_RConsignorName.setText("");
		// リストセル
		lst_STCInstkPlnIqr.clearRow();
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
			// 荷主コードにカーソル遷移
			setFocus(txt_ConsignorCode);
			
			//荷主コード
			txt_ConsignorCode.setText("");
			// 開始入荷予定日
			txt_StrtInstkPlanDate.setText("");
			// 終了入荷予定日
			txt_EdInstkPlanDate.setText("");
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
			// 作業状態
			pul_WorkStatusStorage.setSelectedIndex(0);
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			WmsScheduler schedule = new TcInstockReceivePlanInquirySCH();
			InstockReceiveParameter param = (InstockReceiveParameter)schedule.initFind(conn, null);
				
			// 荷主コードが1件の場合、初期表示させる。
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
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
		// 開始入荷予定日
		Date startdate = WmsFormatter.toDate(param.getParameter(ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY)
											,this.getHttpRequest().getLocale());
		// 終了入荷予定日
		Date enddate = WmsFormatter.toDate(param.getParameter(ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY)
											,this.getHttpRequest().getLocale());
		// 仕入先コード
		String suppliercode = param.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// 出荷先コード
		String customercode = param.getParameter(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY);
		// 開始伝票No.
		String startticketno = param.getParameter(ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY);
		// 終了伝票No.
		String endticketno = param.getParameter(ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY);
		// 商品コード
		String itemcode = param.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);


		// 空ではないときに値をセットする

		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 開始入荷予定日
		if (!StringUtil.isBlank(startdate))
		{
			txt_StrtInstkPlanDate.setDate(startdate);
		}
		// 終了入荷予定日
		if (!StringUtil.isBlank(enddate))
		{
			txt_EdInstkPlanDate.setDate(enddate);
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
		
		// 荷主コードにカーソル遷移
		setFocus(txt_ConsignorCode);
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
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 画面情報
		param.setParameter(
			ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
		
		// 作業状態(入荷予定情報の未開始、開始済、作業中、一部完了、完了)
		String[] search = new String[4];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(InstockReceiveParameter.STATUS_FLAG_WORKING);
		search[2] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		search[3] = new String(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
		param.setParameter(ListInstockReceiveConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		
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
	 * 開始入荷予定日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始入荷予定日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStrtInstkPlanDate_Click(ActionEvent e) throws Exception
	{
		// 開始入荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
		ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 開始入荷予定日
		param.setParameter(
		ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));

		// 開始フラグをセット
		param.setParameter(
			ListInstockReceivePlanDateBusiness.RANGEINSTOCKPLANDATE_KEY,
			InstockReceiveParameter.RANGE_START);
		
		// TCDC区分
		param.setParameter(
			ListInstockReceivePlanDateBusiness.TCDCFLAG_KEY,
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
	 * 終了入荷予定日の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     終了終了入荷予定日 <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchEdInstkPlanDate_Click(ActionEvent e) throws Exception
	{
		// 終了入荷予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
		ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());

		// 終了入荷予定日
		param.setParameter(
		ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));

		// 終了フラグをセット
		param.setParameter(
			ListInstockReceivePlanDateBusiness.RANGEINSTOCKPLANDATE_KEY,
			InstockReceiveParameter.RANGE_END);
			
		// TCDC区分
		param.setParameter(
			ListInstockReceivePlanDateBusiness.TCDCFLAG_KEY,
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
	 * 仕入先コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始入荷予定日 <BR>
	 *     終了入荷予定日 <BR>
	 *     仕入先コード <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchSupplierCode_Click(ActionEvent e) throws Exception
	{
		// 仕入先検索画面へ検索条件をセットする
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
			
		// TCDC区分
		param.setParameter(
			ListInstockReceiveSupplierBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_TC);
		
		// 画面情報
		param.setParameter(
			ListInstockReceiveSupplierBusiness.SEARCHSUPPLIER_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
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
	 * 出荷先コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始入荷予定日 <BR>
	 *     終了入荷予定日 <BR>
	 *     仕入先コード <BR>
	 *     出荷先コード <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchCustomerCode_Click(ActionEvent e) throws Exception
	{
		// 仕入先検索画面へ検索条件をセットする
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
			
		// 出荷先コード
		param.setParameter(
			ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
		
		// 画面情報
		param.setParameter(
			ListInstockReceiveCustomerBusiness.SEARCHCUSTOMER_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
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
	 * 開始伝票No.コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始入荷予定日 <BR>
	 *     終了入荷予定日 <BR>
	 *     仕入先コード <BR>
	 *     出荷先コード <BR>
	 *     開始伝票No. <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_PSearchStartTicketNo_Click(ActionEvent e) throws Exception
	{
		// 伝票No.検索画面へ検索条件をセットする
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
			
		// 出荷先コード
		param.setParameter(
			ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
			
		// 開始伝票No.
		param.setParameter(
			ListInstockReceiveTicketBusiness.STARTTICKETNUMBER_KEY,
			txt_StartTicketNo.getText());
			
		// 開始フラグをセット
		param.setParameter(
		ListInstockReceiveTicketBusiness.RANGETICKETNUMBER_KEY,
			InstockReceiveParameter.RANGE_START);
			
		// 画面情報
		param.setParameter(
			ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
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
	 * 終了伝票No.の検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始入荷予定日 <BR>
	 *     終了入荷予定日 <BR>
	 *     仕入先コード <BR>
	 *     出荷先コード <BR>
	 *     終了伝票No. <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEndTicketNo_Click(ActionEvent e) throws Exception
	{
		// 伝票No.検索画面へ検索条件をセットする
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
			
		// 出荷先コード
		param.setParameter(
			ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
			
		// 終了伝票No.
		param.setParameter(
			ListInstockReceiveTicketBusiness.ENDTICKETNUMBER_KEY,
			txt_EndTicketNo.getText());
			
		// 終了フラグをセット
		param.setParameter(
		ListInstockReceiveTicketBusiness.RANGETICKETNUMBER_KEY,
			InstockReceiveParameter.RANGE_END);
			
		// 画面情報
		param.setParameter(
			ListInstockReceiveTicketBusiness.SEARCHTICKETNUMBER_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
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
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。<BR>
	 * 該当データが見つからない場合は要素数0の<CODE>Parameter</CODE>配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。<BR>
	 * <BR>
	 *   [パラメータ] *必須入力<BR>
	 * <BR>
	 *     <DIR>
	 *     荷主コード <BR>
	 *     開始入荷予定日 <BR>
	 *     終了入荷予定日 <BR>
	 *     仕入先コード <BR>
	 *     出荷先コード <BR>
	 *     開始伝票No. <BR>
	 *     終了伝票No. <BR>
	 *     商品コード <BR>
	 *     </DIR>
	 * <BR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
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
			
		// 出荷先コード
		param.setParameter(
			ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY,
			txt_CustomerCode.getText());
			
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
			
		// 画面情報
		param.setParameter(
			ListInstockReceiveItemBusiness.SEARCHITEM_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
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
	 *       (開始入荷予定日 <= 終了入荷予定日) <BR>
	 *       (開始伝票No. <= 終了伝票No.) <BR>
	 *       </DIR>
	 *     2.スケジュールを開始します。 <BR>
	 *       <DIR>
	 *       [パラメータ] *必須入力<BR>
	 *       <BR>
	 *         <DIR>
	 *         荷主コード* <BR>
	 *         開始入荷予定日 <BR>
	 *         終了入荷予定日 <BR>
	 *         仕入先コード <BR>
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
	 *     1:入荷予定日 <BR>
	 *     2:仕入先ｺｰﾄﾞ <BR>
	 *     3:出荷先ｺｰﾄﾞ <BR>
	 *     4:伝票No. <BR>
	 *     5:商品ｺｰﾄﾞ <BR>
	 *     6:ｹｰｽ入数 <BR>
	 *     7:ﾎｽﾄ予定ｹｰｽ数 <BR>
	 *     8:実績ｹｰｽ数 <BR>
	 *     9:状態 <BR>
	 *    10:ｹｰｽITF <BR>
	 *    11:仕入先名称 <BR>
	 *    12:出荷先名称 <BR>
	 *    13:行No. <BR>
	 *    14:商品名称 <BR>
	 *    15:ﾎﾞｰﾙ入数 <BR>
	 *    16:ﾎｽﾄ予定ﾋﾟｰｽ数 <BR>
	 *    17:実績ﾋﾟｰｽ数 <BR>
	 *    18:ﾎﾞｰﾙITF <BR>
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
			lst_STCInstkPlnIqr.clearRow();
			txt_RConsignorCode.setText("");
			txt_RConsignorName.setText("");
			
			// 入力チェックを行う（書式、必須、禁止文字）
			// 荷主コード
			txt_ConsignorCode.validate();
			// 開始入荷予定日
			txt_StrtInstkPlanDate.validate(false);
			// 終了入荷予定日
			txt_EdInstkPlanDate.validate(false);
			// 仕入先コード
			txt_SupplierCode.validate(false);
			// 出荷先コード
			txt_CustomerCode.validate(false);
			// 開始伝票No.
			txt_StartTicketNo.validate(false);
			// 終了伝票No.
			txt_EndTicketNo.validate(false);
			// 商品コード
			txt_ItemCode.validate(false);
			// 作業状態
			pul_WorkStatusStorage.validate(false);
			// 入荷予定日の大小チェックを行う
			if (txt_StrtInstkPlanDate.getDate() != null && txt_EdInstkPlanDate.getDate() != null)
			{

				if (txt_StrtInstkPlanDate.getDate().after(txt_EdInstkPlanDate.getDate()))
				{
					// エラーメッセージを表示し、終了する。
					// 6023045 = 開始入荷予定日は、終了入荷予定日より前の日付にしてください。
					message.setMsgResourceKey("6023036");
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
			InstockReceiveParameter param = new InstockReceiveParameter();
			
			param.setConsignorCode(txt_ConsignorCode.getText());
			param.setFromPlanDate(WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));
			param.setToPlanDate(WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));
			param.setSupplierCode(txt_SupplierCode.getText());
			param.setCustomerCode(txt_CustomerCode.getText());
			param.setFromTicketNo(txt_StartTicketNo.getText());
			param.setToTicketNo(txt_EndTicketNo.getText());
			param.setItemCode(txt_ItemCode.getText());
			// 作業状態
			// 全て
			if (pul_WorkStatusStorage.getSelectedIndex() == 0)
			{
				param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_ALL);
			}
			// 未開始
			else if (pul_WorkStatusStorage.getSelectedIndex() == 1)
			{
				param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
			}
			// 作業中
			else if (pul_WorkStatusStorage.getSelectedIndex() == 2)
			{
				param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_WORKING);
			}
			// 一部完了
			else if (pul_WorkStatusStorage.getSelectedIndex() == 3)
			{
				param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
			}
			// 完了
			else if (pul_WorkStatusStorage.getSelectedIndex() == 4)
			{
				param.setStatusFlag(InstockReceiveParameter.STATUS_FLAG_COMPLETION);
			}

			// スケジュールを開始する
			WmsScheduler schedule = new TcInstockReceivePlanInquirySCH();
			InstockReceiveParameter[] viewParam = (InstockReceiveParameter[])schedule.query(conn, param);
			
			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了する。
			if( viewParam == null || viewParam.length == 0 )
			{
				// コネクションクローズ
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
			// ケース入数				
			String label_caseqty = DisplayText.getText("LBL-W0007");
			// ボール入数
			String label_bundleqty = DisplayText.getText("LBL-W0005");
			// ホスト予定ケース数
			String label_plancaseqty = DisplayText.getText("LBL-W0331");
			// ホスト予定ピース数
			String label_planpieceqty = DisplayText.getText("LBL-W0332");
			// 実績ケース数
			String label_resultcaseqty = DisplayText.getText("LBL-W0167");				
			// 実績ピース数
			String label_resultpieceqty = DisplayText.getText("LBL-W0169");
			// 状態
			String label_status = DisplayText.getText("LBL-W0229");
			// ケースITF
			String label_caseitf = DisplayText.getText("LBL-W0010");
			// ボールITF
			String label_bundleitf = DisplayText.getText("LBL-W0006");
			
			// リストセルに値をセットする
			for(int i = 0; i < viewParam.length; i++)
			{

				// 行追加
				lst_STCInstkPlnIqr.addRow();
				lst_STCInstkPlnIqr.setCurrentRow(i + 1);

				
				// 検索結果を各セルにセットしていく
				// （1列目）
				// 入荷予定日
				lst_STCInstkPlnIqr.setValue(1,  WmsFormatter.toDispDate(viewParam[i].getPlanDate(),this.getHttpRequest().getLocale()));
				// 仕入先コード
				lst_STCInstkPlnIqr.setValue(2,  viewParam[i].getSupplierCode());
				// 出荷先コード
				lst_STCInstkPlnIqr.setValue(3,  viewParam[i].getCustomerCode());
				// 入荷伝票No.
				lst_STCInstkPlnIqr.setValue(4,  viewParam[i].getInstockTicketNo());
				// 商品コード
				lst_STCInstkPlnIqr.setValue(5,  viewParam[i].getItemCode());
				// ケース入数
				lst_STCInstkPlnIqr.setValue(6,  Formatter.getNumFormat(viewParam[i].getEnteringQty()));
				// ホスト予定ケース数
				lst_STCInstkPlnIqr.setValue(7,  Formatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				// 実績ケース数
				lst_STCInstkPlnIqr.setValue(8,  Formatter.getNumFormat(viewParam[i].getResultCaseQty()));
				// 状態
				lst_STCInstkPlnIqr.setValue(9,  viewParam[i].getStatusName());
				// ケースITF
				lst_STCInstkPlnIqr.setValue(10, viewParam[i].getITF());
				
				// （2列目）
				// 仕入先名称
				lst_STCInstkPlnIqr.setValue(11, viewParam[i].getSupplierName());
				// 出荷先名称
				lst_STCInstkPlnIqr.setValue(12, viewParam[i].getCustomerName());
				// 入荷伝票行No.
				lst_STCInstkPlnIqr.setValue(13, Integer.toString(viewParam[i].getInstockLineNo()));
				// 商品名称
				lst_STCInstkPlnIqr.setValue(14, viewParam[i].getItemName());
				// ボール入数
				lst_STCInstkPlnIqr.setValue(15, Formatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
				// ホスト予定ピース数
				lst_STCInstkPlnIqr.setValue(16, Formatter.getNumFormat(viewParam[i].getPlanPieceQty()));
				// 実績ピース数
				lst_STCInstkPlnIqr.setValue(17, Formatter.getNumFormat(viewParam[i].getResultPieceQty()));
				// ボールITF
				lst_STCInstkPlnIqr.setValue(18, viewParam[i].getBundleITF());
				
				
				
				//tool tipをセット
				ToolTipHelper toolTip = new ToolTipHelper();
				// 仕入先名称
				toolTip.add(label_suppliername,viewParam[i].getSupplierName());
				// 出荷先名称
				toolTip.add(label_customername,viewParam[i].getCustomerName());
				// 商品名称
				toolTip.add(label_itemname,viewParam[i].getItemName());
				//ケース入数				
				toolTip.add(label_caseqty, viewParam[i].getEnteringQty());
				//ボール入数
				toolTip.add(label_bundleqty, viewParam[i].getBundleEnteringQty());
				//ホスト予定ケース数
				toolTip.add(label_plancaseqty, viewParam[i].getPlanCaseQty());
				//ホスト予定ピース数
				toolTip.add(label_planpieceqty, viewParam[i].getPlanPieceQty());
				//実績ケース数
				toolTip.add(label_resultcaseqty, viewParam[i].getResultCaseQty());				
				//実績ピース数
				toolTip.add(label_resultpieceqty, viewParam[i].getResultPieceQty());		
				// 状態
				toolTip.add(label_status,viewParam[i].getStatusName());
				// ケース入数
				toolTip.add(label_caseitf,viewParam[i].getITF());
				// ボール入数
				toolTip.add(label_bundleitf,viewParam[i].getBundleITF());
				
				lst_STCInstkPlnIqr.setToolTip(i+1, toolTip.getText());
				

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
	public void lst_STCInstkPlnIqr_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STCInstkPlnIqr_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STCInstkPlnIqr_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STCInstkPlnIqr_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STCInstkPlnIqr_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STCInstkPlnIqr_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_STCInstkPlnIqr_Click(ActionEvent e) throws Exception
	{
	}


}
//end of class
