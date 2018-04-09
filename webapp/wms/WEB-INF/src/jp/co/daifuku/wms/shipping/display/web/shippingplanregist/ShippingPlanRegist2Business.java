// $Id: ShippingPlanRegist2Business.java,v 1.1.1.1 2006/08/17 09:34:29 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.shipping.display.web.shippingplanregist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.Label;
import jp.co.daifuku.bluedog.ui.control.NumberTextBox;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.util.Formatter;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingconsignor.ListShippingConsignorBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingitem.ListShippingItemBusiness;
import jp.co.daifuku.wms.shipping.display.web.listbox.listshippingsupplier.ListShippingSupplierBusiness;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;
import jp.co.daifuku.wms.shipping.schedule.ShippingPlanRegistSCH;

/**
 * Designer : H.Akiyama <BR>
 * Maker : H.Akiyama <BR>
 * <BR>
 * 出荷予定データ登録(詳細情報登録)画面クラスです。<BR>
 * 基本情報入力画面の入力情報を、上部表示領域に表示します。<BR>
 * 出荷予定データ登録を行うスケジュールにパラメータを引き渡します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.入力ボタン押下処理(<CODE>btn_Input_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   入力エリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが入力条件のチェックを行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   結果がtrueの時は入力エリアの情報をためうちエリアに追加します。<BR>
 *   修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   <DIR>
 * 		出荷予定日* <BR>
 * 		荷主コード* <BR>
 * 		出荷先コード* <BR>
 * 		伝票No.* <BR>
 *		行No.* <BR>
 *		商品コード* <BR>
 *		商品名称 <BR>
 *		ケース入数*2 <BR>
 *		ボール入数 <BR>
 *		ホスト予定ケース数*3 <BR>
 *		ホスト予定ピース数*3 <BR>
 *		ケースITF <BR>
 *		ボールITF <BR>
 *		ＴＣ／ＤＣ区分* <BR>
 *		仕入先コード*4 <BR>
 *		仕入先名称 <BR>
 *		入荷伝票No.*5 <BR>
 *		入荷伝票行No.*5 <BR>
 *   <BR>
 * 		*2 <BR>
 * 		ホスト予定ケース数(>0)入力時、必須入力 <BR>
 * 		*3 <BR>
 * 		ホスト予定ケース数、予定ピース数何れかに1以上の入力が必須条件 <BR>
 * 		ケース数×ケース入数＋ピース数が0でない事(>0) <BR>
 * 		*4 <BR>
 *	 	ＴＣ／ＤＣ区分：クロスまたはＴＣ指定時、必須入力 <BR>
 * 		*5 <BR>
 * 		ＴＣ／ＤＣ区分：クロス指定時、必須入力 <BR>
 *	 </DIR>
 * </DIR>
 * <BR>
 * 2.登録ボタン押下処理(<CODE>btn_Submit_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   ためうちエリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが出荷予定データ登録を行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   結果がtrueの時はためうちの登録情報をクリアします。<BR>
 *   <BR>
 *   [パラメータ]<BR>
 *   <BR>
 *   <DIR>
 * 		出荷予定日 <BR>
 * 		荷主コード <BR>
 * 		荷主名称 <BR>
 * 		出荷先コード <BR>
 * 		出荷先名称 <BR>
 * 		伝票No. <BR>
 *		行No. <BR>
 *		商品コード <BR>
 *		商品名称 <BR>
 *		ケース入数 <BR>
 *		ボール入数 <BR>
 *		ホスト予定ケース数 <BR>
 *		ホスト予定ピース数 <BR>
 *		ケースITF <BR>
 *		ボールITF <BR>
 *		ＴＣ／ＤＣ区分 <BR>
 *		仕入先コード <BR>
 *		仕入先名称 <BR>
 *		入荷伝票No. <BR>
 *		入荷伝票行No. <BR>
 *	 </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>H.Akiyama</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:29 $
 * @author  $Author: mori $
 */
public class ShippingPlanRegist2Business extends ShippingPlanRegist2 implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	protected final int LineNo_LEN = 3;

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
		String menuparam = this.getHttpRequest().getParameter(MENUPARAM);
		if (menuparam != null)
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
		// 登録ボタン押下時確認ダイアログ "登録しますか？"
		btn_Submit.setBeforeConfirm("MSG-W0009");

		// 一覧クリアボタン押下時確認ダイアログ "一覧入力情報がクリアされます。宜しいですか？"
		btn_ListClear.setBeforeConfirm("MSG-W0012");

	}

	/**
	 * 画面の初期化を行います。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *    1.基本情報入力画面の入力情報を、上部表示領域に表示します。<BR>
	 *    2.ViewStateのためうち行No.に初期値:-1を設定します。<BR>
	 *    2.登録ボタン・一覧クリアボタンを無効にします。<BR>
	 *    3.カーソルを行No.に初期セットします。<BR>
	 * <BR>
	 *    項目名[初期値]<BR>
	 *    <DIR>
	 * 		出荷予定日[基本情報.出荷予定日] <BR>
	 * 		荷主コード[基本情報.荷主コード] <BR>
	 * 		荷主名称[基本情報.荷主名称] <BR>
	 * 		出荷先コード[基本情報.出荷先コード] <BR>
	 * 		出荷先名称[基本情報.出荷先名称] <BR>
	 * 		伝票No.[基本情報.伝票No.] <BR>
	 * 		行No.[なし] <BR>
	 * 		商品コード[なし] <BR>
	 * 		商品名称[なし] <BR>
	 * 		ケース入数[なし] <BR>
	 * 		ボール入数[なし] <BR>
	 * 		ホスト予定ケース数[なし] <BR>
	 * 		ホスト予定ピース数[なし] <BR>
	 * 		ケースITF[なし] <BR>
	 * 		ボールITF[なし] <BR>
	 * 		ＴＣ／ＤＣ区分[なし] <BR>
	 * 		仕入先コード[なし] <BR>
	 * 		仕入先名称[なし] <BR>
	 * 		入荷伝票No.[なし] <BR>
	 * 		入荷伝票行No.[なし] <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		try
		{
			// 画面名をセットする
			// タイトル
			lbl_SettingName.setText(DisplayText.getText(this.getViewState().getString(ShippingPlanRegistBusiness.TITLE_KEY)));		

			// 詳細情報登録のタブを前出しする
			tab_BscDtlRst.setSelectedIndex(2);

			// ViewStateに値がセットされていれば、その値をセットする
			// 出荷予定日
			if (!StringUtil.isBlank(this.getViewState().getString("ShippingPlanDate")))
			{
				txt_FDateShp.setDate(
					WmsFormatter.toDate(
						this.getViewState().getString("ShippingPlanDate"),
						this.getHttpRequest().getLocale()));
			}
			// 荷主コード
			if (!StringUtil.isBlank(this.getViewState().getString("ConsignorCode")))
			{
				lbl_JavaSetCnsgnrCd.setText(this.getViewState().getString("ConsignorCode"));
			}

			// 荷主名称
			if (!StringUtil.isBlank(this.getViewState().getString("ConsignorName")))
			{
				lbl_JavaSetCnsgnrNm.setText(this.getViewState().getString("ConsignorName"));
			}

			// 出荷先コード
			if (!StringUtil.isBlank(this.getViewState().getString("CustomerCode")))
			{
				lbl_JavaSetCustCd.setText(this.getViewState().getString("CustomerCode"));
			}

			// 出荷先名称
			if (!StringUtil.isBlank(this.getViewState().getString("CustomerName")))
			{
				lbl_JavaSetCustNm.setText(this.getViewState().getString("CustomerName"));
			}

			// 伝票No.
			if (!StringUtil.isBlank(this.getViewState().getString("TicketNo")))
			{
				lbl_JavaSetTktNo.setText(this.getViewState().getString("TicketNo"));
			}
			
			// TC/DC区分
			rdo_CrossDCFlagDC.setChecked(false);
			rdo_CrossDCFlagCross.setChecked(false);
			rdo_CrossDCFlagTC.setChecked(true);

			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			// (デフォルト:-1)
			this.getViewState().setInt("LineNo", -1);

			// ボタン押下を不可にする
			// 登録ボタン
			btn_Submit.setEnabled(false);
			// 一覧クリアボタン
			btn_ListClear.setEnabled(false);

			// カーソルを行No.にセットする
			setFocus(txt_LineNo);

		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this));
		}
		finally
		{
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
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 商品コード
		String itemcode = param.getParameter(ListShippingItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListShippingItemBusiness.ITEMNAME_KEY);
		// 仕入先コード
		String suppliercode = param.getParameter(ListShippingSupplierBusiness.SUPPLIERCODE_KEY);
		// 仕入先名称
		String suppliername = param.getParameter(ListShippingSupplierBusiness.SUPPLIERNAME_KEY);

		// 空ではないときに値をセットする
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
			txt_ItemName.setText(itemname);
			// カーソルを行No.にセットする
			setFocus(txt_LineNo);
		}
		// 仕入先コード
		if (!StringUtil.isBlank(suppliercode))
		{
			txt_SupplierCode.setText(suppliercode);
			txt_SupplierName.setText(suppliername);
			// カーソルを行No.にセットする
			setFocus(txt_LineNo);
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。
	 * 異常があった場合は、メッセージをセットし、falseを返します。
	 * 
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ItemName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_CaseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_BundleItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_SupplierCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_SupplierName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_InstockTicketNo))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
	}

	/**
	 * Parameterクラスにためうちエリアのデータをセットするメソッドです。<BR>
	 * <BR>
	 * 概要:Parameterクラスにためうちエリアのデータをセットします。<BR>
	 * <BR>
	 * 1.新規入力であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No＝-1)<BR>
	 * 2.修正中の入力データであれば、修正対象行を除いたためうちデータをParameterクラスにセットします。<BR>
	 * 3.登録ボタン押下時であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No＝-1)<BR>
	 * <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			修正対象ためうち行No.* <BR>
	 * 		</DIR>
	 *   	[返却データ]<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>ShippingParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	protected ShippingParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		// 端末No.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		String termNo = userHandler.getTerminalNo();

		for (int i = 1; i < lst_SShpPlnDaRst.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}

			// 行指定
			lst_SShpPlnDaRst.setCurrentRow(i);

			// スケジュールパラメータへセット
			ShippingParameter param = new ShippingParameter();
			// 入力エリア情報
			// 出荷予定日
			param.setPlanDate(WmsFormatter.toParamDate(txt_FDateShp.getDate()));
			// 荷主コード
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			// 荷主名称
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			// 出荷先コード
			param.setCustomerCode(lbl_JavaSetCustCd.getText());
			// 出荷先名称
			param.setCustomerName(lbl_JavaSetCustNm.getText());
			// 伝票No.
			param.setShippingTicketNo(lbl_JavaSetTktNo.getText());
			// ためうちエリア情報
			// 行No.
			if(StringUtil.isBlank(lst_SShpPlnDaRst.getValue(3)))
			{
				param.setInstockLineNo(0);
			}
			else
			{
				param.setShippingLineNo(Integer.parseInt(lst_SShpPlnDaRst.getValue(3)));				
			}
			// 商品コード
			param.setItemCode(lst_SShpPlnDaRst.getValue(4));
			// ケース入数
			param.setEnteringQty(Formatter.getInt(lst_SShpPlnDaRst.getValue(5)));
			// ホスト予定ケース数
			param.setPlanCaseQty(Formatter.getInt(lst_SShpPlnDaRst.getValue(6)));
			// ケースITF
			param.setITF(lst_SShpPlnDaRst.getValue(7));
			// TC/DC区分(隠し項目)
			param.setTcdcFlag(lst_SShpPlnDaRst.getValue(0));
			// 仕入先コード
			param.setSupplierCode(lst_SShpPlnDaRst.getValue(9));
			// 入荷伝票No.
			param.setInstockTicketNo(lst_SShpPlnDaRst.getValue(10));
			// 商品名称
			param.setItemName(lst_SShpPlnDaRst.getValue(11));
			// ボール入数
			param.setBundleEnteringQty(Formatter.getInt(lst_SShpPlnDaRst.getValue(12)));
			// ホスト予定ピース数
			param.setPlanPieceQty(Formatter.getInt(lst_SShpPlnDaRst.getValue(13)));
			// ボールITF
			param.setBundleITF(lst_SShpPlnDaRst.getValue(14));
			// 仕入先名称
			param.setSupplierName(lst_SShpPlnDaRst.getValue(15));
			// 入荷伝票行No.	
			if(StringUtil.isBlank(lst_SShpPlnDaRst.getValue(16)))
			{
				param.setInstockLineNo(0);
			}
			else
			{
				param.setInstockLineNo(Integer.parseInt(lst_SShpPlnDaRst.getValue(16)));				
			}

			// 行No.を保持しておく
			param.setRowNo(i);

			// 作業者コード
			param.setWorkerCode(this.getViewState().getString("WorkerCode"));

			// 端末No
			param.setTerminalNumber(termNo);

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			ShippingParameter[] listparam = new ShippingParameter[vecParam.size()];
			vecParam.copyInto(listparam);
			return listparam;
		}
		else
		{
			// セットするためうちデータがなければnullをセット
			return null;
		}
	}

	/**
	 * ためうちエリアに値をセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアに値をセットします。<BR>
	 * <DIR>
	 * 		隠し項目
	 * 		<DIR>
	 * 			0.TC/DCフラグ <BR>
	 *	 	</DIR>
	 * </DIR>	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	protected void setList() throws Exception
	{
		//ToolTip用のデータを編集
		ToolTipHelper toolTip = new ToolTipHelper();
		// 商品名称
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		// 仕入先名称
		toolTip.add(DisplayText.getText("LBL-W0253"), txt_SupplierName.getText());
		// 入荷伝票No.
		toolTip.add(DisplayText.getText("LBL-W0091"), txt_InstockTicketNo.getText());
		// 入荷伝票行No.
		toolTip.add(DisplayText.getText("LBL-W0090"), txt_InstkTktLineNo.getText());
		//カレント行にTOOL TIPをセットする
		lst_SShpPlnDaRst.setToolTip(lst_SShpPlnDaRst.getCurrentRow(), toolTip.getText());

		// 隠し項目にTC/DCフラグをセットする
		if (rdo_CrossDCFlagDC.getChecked())
		{
			// DC
			lst_SShpPlnDaRst.setValue(0, ShippingParameter.TCDC_FLAG_DC);
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			// クロス
			lst_SShpPlnDaRst.setValue(0, ShippingParameter.TCDC_FLAG_CROSSTC);
		}
		else if (rdo_CrossDCFlagTC.getChecked())
		{
			// TC
			lst_SShpPlnDaRst.setValue(0, ShippingParameter.TCDC_FLAG_TC);
		}

		// 行No.
		lst_SShpPlnDaRst.setValue(3, txt_LineNo.getText());
		// 商品コード
		lst_SShpPlnDaRst.setValue(4, txt_ItemCode.getText());
		// ケース入数
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_SShpPlnDaRst.setValue(5, "0");
		}
		else
		{
			lst_SShpPlnDaRst.setValue(5, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		// ホスト予定ケース数
		if (StringUtil.isBlank(txt_PlanCaseQty.getText()))
		{
			lst_SShpPlnDaRst.setValue(6, "0");
		}
		else
		{
			lst_SShpPlnDaRst.setValue(6, WmsFormatter.getNumFormat(txt_PlanCaseQty.getInt()));
		}
		// ケースITF
		lst_SShpPlnDaRst.setValue(7, txt_CaseItf.getText());
		// TC/DC区分
		if (rdo_CrossDCFlagDC.getChecked())
		{
			// DC
			lst_SShpPlnDaRst.setValue(8, DisplayUtil.getTcDcValue(ShippingParameter.TCDC_FLAG_DC));
		}
		else if (rdo_CrossDCFlagCross.getChecked())
		{
			// クロス
			lst_SShpPlnDaRst.setValue(8, DisplayUtil.getTcDcValue(ShippingParameter.TCDC_FLAG_CROSSTC));
		}
		else if (rdo_CrossDCFlagTC.getChecked())
		{
			// TC
			lst_SShpPlnDaRst.setValue(8, DisplayUtil.getTcDcValue(ShippingParameter.TCDC_FLAG_TC));
		}

		// 仕入先コード
		lst_SShpPlnDaRst.setValue(9, txt_SupplierCode.getText());
		// 入荷伝票No.
		lst_SShpPlnDaRst.setValue(10, txt_InstockTicketNo.getText());
		// 商品名称
		lst_SShpPlnDaRst.setValue(11, txt_ItemName.getText());
		// ボール入数
		if (StringUtil.isBlank(txt_BundleEntering.getText()))
		{
			lst_SShpPlnDaRst.setValue(12, "0");
		}
		else
		{
			lst_SShpPlnDaRst.setValue(12, WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		}
		// ホスト予定ピース数
		if (StringUtil.isBlank(txt_PlanPieceQty.getText()))
		{
			lst_SShpPlnDaRst.setValue(13, "0");
		}
		else
		{
			lst_SShpPlnDaRst.setValue(13, WmsFormatter.getNumFormat(txt_PlanPieceQty.getInt()));
		}
		// ボールITF
		lst_SShpPlnDaRst.setValue(14, txt_BundleItf.getText());
		// 仕入先名称
		lst_SShpPlnDaRst.setValue(15, txt_SupplierName.getText());
		// 入荷伝票行No.
		lst_SShpPlnDaRst.setValue(16, Integer.toString(txt_InstkTktLineNo.getInt()));
	}

	// Private methods -----------------------------------------------
	/** 
	 * ケース、ピースの値チェックを行うメソッドです。<BR>
	 * <BR>
	 * 概要:ケース、ピースの値が0以上かをチェックします。<BR>
	 * 
	 * @param figure 	  値チェックするための使用桁です。
	 * @param name 	  値チェックする項目名です。
	 * @return 使用桁か位置が1以上じゃなかった場合項目名を返します。
	 * @throws Exception 全ての例外を報告します。
	 */
	private String checkNumber(NumberTextBox figure, Label name) throws Exception
	{
		String itemName = null;

		if(!StringUtil.isBlank(Integer.toString(figure.getInt())))
		{
			if (figure.getInt() < 0)
			{
				// itemNameに項目名をセット
				itemName = DisplayText.getText(name.getResourceKey());
				return itemName;
			}
		}
		return itemName;
	}
	
	// Event handler methods -----------------------------------------
	/**
	 * 戻るボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:出荷予定データ登録(基本情報設定)画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		// 詳細情報登録画面->基本情報設定画面
		forward("/shipping/shippingplanregist/ShippingPlanRegist.do");
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
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード* <BR>
	 *       商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
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
			lbl_JavaSetCnsgnrCd.getText());
		// 商品コード
		param.setParameter(ListShippingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 検索フラグ
		param.setParameter(
			ListShippingItemBusiness.SEARCHITEM_KEY,
			ShippingParameter.SEARCHFLAG_PLAN);
		// 処理中画面->結果画面
		redirect("/shipping/listbox/listshippingitem/ListShippingItem.do", param, "/progress.do");
	}

	/** 
	 * 仕入先コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で仕入先コード一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード* <BR>
	 *       仕入先コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchSpl_Click(ActionEvent e) throws Exception
	{
		// 仕入先コード検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListShippingConsignorBusiness.CONSIGNORCODE_KEY,
			lbl_JavaSetCnsgnrCd.getText());
		// 仕入先コード
		param.setParameter(
			ListShippingSupplierBusiness.SUPPLIERCODE_KEY,
			txt_SupplierCode.getText());
		// 検索フラグ
		param.setParameter(
			ListShippingSupplierBusiness.SEARCHSUPPLIER_KEY,
			ShippingParameter.SEARCHFLAG_PLAN);
		// 処理中画面->結果画面
		redirect(
			"/shipping/listbox/listshippingsupplier/ListShippingSupplier.do",
			param,
			"/progress.do");
	}

	/**
	 * 入力ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目を条件に、出荷予定情報を検索し、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    1.カーソルを行No.にセットします。<BR>
	 *    2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *    3.スケジュールを開始します。<BR>
	 * 	 <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			出荷予定日* <BR>
	 * 			荷主コード* <BR>
	 * 			出荷先コード* <BR>
	 * 			伝票No.* <BR>
	 *			行No.* <BR>
	 *			商品コード* <BR>
	 *			商品名称 <BR>
	 *			ケース入数*2 <BR>
	 *			ボール入数 <BR>
	 *			ホスト予定ケース数*3 <BR>
	 *			ホスト予定ピース数*3 <BR>
	 *			ケースITF <BR>
	 *			ボールITF <BR>
	 *			ＴＣ／ＤＣ区分* <BR>
	 *			仕入先コード*4 <BR>
	 *			仕入先名称 <BR>
	 *			入荷伝票No.*5 <BR>
	 *			入荷伝票行No.*5 <BR>
	 *			ためうち行No.<BR>
	 *   	<BR>
	 * 			*2 <BR>
	 * 			予定ケース数(>0)入力時、必須入力 <BR>
	 * 			*3 <BR>
	 * 			予定ケース数、予定ピース数何れかに1以上の入力が必須条件 <BR>
	 * 			ケース数×ケース入数＋ピース数が0でない事(>0) <BR>
	 * 			*4 <BR>
	 *	 		ＴＣ／ＤＣ区分：クロスまたはＴＣ指定時、必須入力 <BR>
	 * 			*5 <BR>
	 * 			ＴＣ／ＤＣ区分：クロス指定時、必須入力 <BR>
	 *	 	</DIR>
	 *   </DIR>
	 *   <BR>
	 *    4.スケジュールの結果がtrueであれば、ためうちエリアに追加します。<BR>
	 *    修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
	 *    5.スケジュールの結果がtrueであれば、登録ボタン・一覧クリアボタンを有効にします。<BR>
	 *    6.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *    7.スケジュールから取得したメッセージを画面に出力します。<BR>
	 * 	  8.入力エリアの内容は保持します。<BR>
	 *   <BR>
	 * 	 リストセルの列番号一覧<BR>
	 *   <DIR>
	 * 		3.行No. <BR>
	 * 		4.商品コード <BR>
	 * 		5.ケース入数 <BR>
	 * 		6.ホスト予定ケース数 <BR>
	 * 		7.ケースITF <BR>
	 * 		8.ＴＣ／ＤＣ <BR>
	 * 		9.仕入先コード <BR>
	 * 		10.入荷伝票No. <BR>
	 * 		11.商品名称 <BR>
	 * 		12.ボール入数 <BR>
	 * 		13.ホスト予定ピース数 <BR>
	 * 		14.ボールITF <BR>
	 * 		15.仕入先名称 <BR>
	 * 		16.入荷伝票行No. <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		// カーソルを行No.にセットする
		setFocus(txt_LineNo);

		// 入力チェック
		txt_LineNo.validate();
		txt_ItemCode.validate();
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_PlanCaseQty.validate(false);
		txt_CaseItf.validate(false);
		txt_BundleEntering.validate(false);
		txt_PlanPieceQty.validate(false);
		txt_BundleItf.validate(false);
		// 2006/07/25 modify start T.Kishimoto 項目チェックを修正
		if (rdo_CrossDCFlagDC.getChecked())
		{
			txt_SupplierCode.validate(false);
		}
		else
		{
			// DC以外は必須チェック
			txt_SupplierCode.validate();
		}
		
		txt_SupplierName.validate(false);
		
		if (rdo_CrossDCFlagCross.getChecked())
		{
			// クロスの場合は必須チェック
			txt_InstockTicketNo.validate();
			txt_InstkTktLineNo.validate();
		}
		else
		{
			txt_InstockTicketNo.validate(false);
			txt_InstkTktLineNo.validate(false);
		}
		// 2006/07/25 modify end T.Kishimoto

		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}

		String itemname = null;

		itemname = checkNumber(txt_CaseEntering, lbl_CaseEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_BundleEntering, lbl_BundleEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_PlanCaseQty, lbl_PlanCaseQty);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_PlanPieceQty, lbl_PlanPieceQty);

		if (!StringUtil.isBlank(itemname))
		{
			// エラーメッセージを表示し、終了する。
			// 6023057 = {0}には{1}以上の値を入力してください。
			message.setMsgResourceKey(
				"6023057" + wDelim + itemname + wDelim + "0");
			return;
		}

		Connection conn = null;

		try
		{
			// スケジュールパラメータへセット
			// 入力エリア
			ShippingParameter param = new ShippingParameter();
			// 出荷予定日
			param.setPlanDate(WmsFormatter.toParamDate(txt_FDateShp.getDate()));
			// 荷主コード
			param.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			// 荷主名称（桁数チェック用）
			param.setConsignorName(lbl_JavaSetCnsgnrNm.getText());
			// 出荷先コード
			param.setCustomerCode(lbl_JavaSetCustCd.getText());
			// 出荷先名称
			param.setCustomerName(lbl_JavaSetCustNm.getText());
			// 伝票No.
			param.setShippingTicketNo(lbl_JavaSetTktNo.getText());
			// 行No.
			if(StringUtil.isBlank(txt_LineNo.getText()))
			{
				param.setShippingLineNo(0);
			}
			else
			{
				param.setShippingLineNo(Integer.parseInt(txt_LineNo.getText()));
			}
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());
			// ケース入数
			param.setEnteringQty(txt_CaseEntering.getInt());
			// ボール入数
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			// ホスト予定ケース数
			param.setPlanCaseQty(txt_PlanCaseQty.getInt());
			// ホスト予定ピース数
			param.setPlanPieceQty(txt_PlanPieceQty.getInt());
			// ケースITF
			param.setITF(txt_CaseItf.getText());
			// ボールITF
			param.setBundleITF(txt_BundleItf.getText());
			// TC/DC区分
			if (rdo_CrossDCFlagDC.getChecked())
			{
				// DC
				param.setTcdcFlag(ShippingParameter.TCDC_FLAG_DC);
			}
			else if (rdo_CrossDCFlagCross.getChecked())
			{
				// クロス
				param.setTcdcFlag(ShippingParameter.TCDC_FLAG_CROSSTC);
			}
			else if (rdo_CrossDCFlagTC.getChecked())
			{
				// TC
				param.setTcdcFlag(ShippingParameter.TCDC_FLAG_TC);
			}

			// 仕入先コード
			param.setSupplierCode(txt_SupplierCode.getText());
			// 仕入先名称
			param.setSupplierName(txt_SupplierName.getText());
			// 入荷伝票No.
			param.setInstockTicketNo(txt_InstockTicketNo.getText());
			// 入荷伝票行No.
			if(StringUtil.isBlank(txt_InstkTktLineNo.getText()))
			{
				param.setInstockLineNo(0);
			}
			else
			{
				param.setInstockLineNo(Integer.parseInt(txt_InstkTktLineNo.getText()));				
			}
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールパラメータへセット
			// ためうちエリア
			ShippingParameter[] listparam = null;

			// ためうちにデータがなければnullをセット
			if (lst_SShpPlnDaRst.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt("LineNo"));
			}
			
			WmsScheduler schedule = new ShippingPlanRegistSCH();

			if (schedule.check(conn, param, listparam))
			{
				// 結果がtrueであればためうちエリアにデータをセット
				if (this.getViewState().getInt("LineNo") == -1)
				{
					// 新規入力であれば、ためうちに追加
					lst_SShpPlnDaRst.addRow();
					lst_SShpPlnDaRst.setCurrentRow(lst_SShpPlnDaRst.getMaxRows() - 1);
					setList();
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新
					lst_SShpPlnDaRst.setCurrentRow(this.getViewState().getInt("LineNo"));
					setList();
					// 選択行の色を元に戻す
					lst_SShpPlnDaRst.resetHighlight();
				}

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);

				// ボタン押下を可能にする
				// 登録ボタン
				btn_Submit.setEnabled(true);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(true);

			}

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

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
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリアの項目を初期値に戻します。<BR>
	 *    <DIR>
	 *  	･初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。<BR>
	 *    </DIR>
	 *    2.カーソルを行No.にセットします。<BR>
	 *    3.ためうちエリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 初期値をセット
		txt_LineNo.setText("");
		txt_ItemCode.setText("");
		txt_ItemName.setText("");
		txt_CaseEntering.setText("");
		txt_PlanCaseQty.setText("");
		txt_CaseItf.setText("");
		txt_BundleEntering.setText("");
		txt_PlanPieceQty.setText("");
		txt_BundleItf.setText("");
		rdo_CrossDCFlagTC.setChecked(true);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);
		txt_SupplierCode.setText("");
		txt_SupplierName.setText("");
		txt_InstockTicketNo.setText("");
		txt_InstkTktLineNo.setText("");

		// カーソルを行No.にセットする
		setFocus(txt_LineNo);
	}

	/**
	 * 登録ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、出荷予定データ登録を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを行No.にセットします。<BR>
	 *    2.登録を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "登録しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.スケジュールを開始します。<BR>
	 *				<DIR>
	 *   				[パラメータ]<BR>
	 * 					<DIR>
	 * 						出荷予定日 <BR>
	 * 						荷主コード <BR>
	 * 						荷主名称 <BR>
	 * 						出荷先コード <BR>
	 * 						出荷先名称 <BR>
	 * 						伝票No. <BR>
	 *						ためうち.行No. <BR>
	 *						ためうち.商品コード <BR>
	 *						ためうち.商品名称 <BR>
	 *						ためうち.ケース入数 <BR>
	 *						ためうち.ボール入数 <BR>
	 *						ためうち.ホスト予定ケース数 <BR>
	 *						ためうち.ホスト予定ピース数 <BR>
	 *						ためうち.ケースITF <BR>
	 *						ためうち.ボールITF <BR>
	 *						ためうち.ＴＣ／ＤＣ区分 <BR>
	 *						ためうち.仕入先コード <BR>
	 *						ためうち.仕入先名称 <BR>
	 *						ためうち.入荷伝票No. <BR>
	 *						ためうち.入荷伝票行No. <BR>
	 *						ためうち行No.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.スケジュールの結果がtrueの時は入力エリア、ためうちの登録情報をクリアします。<BR>
	 *				3.修正状態を解除します。(ViewStateのためうち行No.にデフォルト:-1を設定します。)<BR>
	 *    			falseの時はスケジュールから取得したメッセージを画面に出力します。<BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * 	 リストセルの列番号一覧<BR>
	 *   <DIR>
	 * 		3.行No. <BR>
	 * 		4.商品コード <BR>
	 * 		5.ケース入数 <BR>
	 * 		6.ホスト予定ケース数 <BR>
	 * 		7.ケースITF <BR>
	 * 		8.ＴＣ／ＤＣ <BR>
	 * 		9.仕入先コード <BR>
	 * 		10.入荷伝票No. <BR>
	 * 		11.商品名称 <BR>
	 * 		12.ボール入数 <BR>
	 * 		13.ホスト予定ピース数 <BR>
	 * 		14.ボールITF <BR>
	 * 		15.仕入先名称 <BR>
	 * 		16.入荷伝票行No. <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// カーソルを行No.にセットする
			setFocus(txt_LineNo);

			// スケジュールパラメータへセット
			ShippingParameter[] param = null;
			// ためうちエリアの全データをセット
			param = setListParam(-1);

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new ShippingPlanRegistSCH();

			// スケジュールスタート
			if (schedule.startSCH(conn, param))
			{
				// 結果がtrueであれば、commit
				conn.commit();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());

				// 一覧クリア
				lst_SShpPlnDaRst.clearRow();

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);

				// ボタン押下を不可にする
				// 登録ボタン
				btn_Submit.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);
			}
			else
			{
				conn.rollback();
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
	 * 一覧クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちの表示情報を全てクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報のクリアを行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 * 	    "一覧入力情報がクリアされます。宜しいですか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.ためうちの表示情報を全てクリアします。<BR>
	 *				2.登録ボタン・一覧クリアボタンを無効にします。<BR>
	 *				3.入力エリアの項目を全てクリアする。<BR>
	 * 				4.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *				5.カーソルを行No.にセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// 行をすべて削除
		lst_SShpPlnDaRst.clearRow();

		// ボタン押下を不可にする
		// 登録ボタン
		btn_Submit.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);

		// 修正状態をデフォルトに戻す
		this.getViewState().setInt("LineNo", -1);

		// カーソルを行No.にセットする
		setFocus(txt_LineNo);

	}

	/**
	 * 取消、修正ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 取消ボタン概要:ためうちの該当データをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.ためうち情報のクリアを行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "取消しますか？"<BR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 *			<DIR>
	 *				何も行いません。
	 *			</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 *			<DIR>
	 *				1.入力エリア、ためうちの該当データをクリアします。<BR>
	 * 				2.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *              3.ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にします。<BR>
	 *				4.カーソルを行No.にセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * 修正ボタン概要:ためうちの該当データを修正状態にします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.選択情報を上部の入力領域に表示します。<BR>
	 *    2.選択情報部を薄黄色にします。<BR>
	 *    3.ViewStateのためうち行No.に現在行を設定します。
	 *    4.カーソルを行No.にセットします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SShpPlnDaRst_Click(ActionEvent e) throws Exception
	{
		// 取消ボタンクリック時
		if (lst_SShpPlnDaRst.getActiveCol() == 1)
		{
			// リスト削除
			lst_SShpPlnDaRst.removeRow(lst_SShpPlnDaRst.getActiveRow());

			// ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にする
			// ためうちにデータがなければnullをセット
			if (lst_SShpPlnDaRst.getMaxRows() == 1)
			{
				// ボタン押下を不可にする
				// 登録ボタン
				btn_Submit.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);
			}

			// 修正状態をデフォルトに戻す
			this.getViewState().setInt("LineNo", -1);

			// 選択行の色を元に戻す
			lst_SShpPlnDaRst.resetHighlight();

			// カーソルを行No.にセットする
			setFocus(txt_LineNo);

		}
		// 修正ボタンクリック時(Modify動作を行う)
		else if (lst_SShpPlnDaRst.getActiveCol() == 2)
		{
			// 現在の行をセット
			lst_SShpPlnDaRst.setCurrentRow(lst_SShpPlnDaRst.getActiveRow());
			// 行No.
			txt_LineNo.setText(lst_SShpPlnDaRst.getValue(3));
			// 商品コード
			txt_ItemCode.setText(lst_SShpPlnDaRst.getValue(4));
			// ケース入数
			txt_CaseEntering.setText(lst_SShpPlnDaRst.getValue(5));
			// ホスト予定ケース数
			txt_PlanCaseQty.setText(lst_SShpPlnDaRst.getValue(6));
			// ケースITF
			txt_CaseItf.setText(lst_SShpPlnDaRst.getValue(7));
			// TC/DC(隠し項目)
			if (lst_SShpPlnDaRst.getValue(0).equals(ShippingParameter.TCDC_FLAG_DC))
			{
				// DC
				rdo_CrossDCFlagDC.setChecked(true);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagTC.setChecked(false);
			}
			else if (lst_SShpPlnDaRst.getValue(0).equals(ShippingParameter.TCDC_FLAG_CROSSTC))
			{
				// クロス
				rdo_CrossDCFlagDC.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(true);
				rdo_CrossDCFlagTC.setChecked(false);
			}
			if (lst_SShpPlnDaRst.getValue(0).equals(ShippingParameter.TCDC_FLAG_TC))
			{
				// TC
				rdo_CrossDCFlagDC.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagTC.setChecked(true);
			}
			// 仕入先コード
			txt_SupplierCode.setText(lst_SShpPlnDaRst.getValue(9));
			// 入荷伝票No.
			txt_InstockTicketNo.setText(lst_SShpPlnDaRst.getValue(10));
			// 商品名称
			txt_ItemName.setText(lst_SShpPlnDaRst.getValue(11));
			// ボール入数
			txt_BundleEntering.setText(lst_SShpPlnDaRst.getValue(12));
			// ホスト予定ピース数
			txt_PlanPieceQty.setText(lst_SShpPlnDaRst.getValue(13));
			// ボールITF
			txt_BundleItf.setText(lst_SShpPlnDaRst.getValue(14));
			// 仕入先名称
			txt_SupplierName.setText(lst_SShpPlnDaRst.getValue(15));
			// 入荷伝票行No.
			txt_InstkTktLineNo.setText(lst_SShpPlnDaRst.getValue(16));

			// ViewStateに保存
			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			this.getViewState().setInt("LineNo", lst_SShpPlnDaRst.getActiveRow());

			//修正行を黄色に変更する
			lst_SShpPlnDaRst.setHighlight(lst_SShpPlnDaRst.getActiveRow());

			// カーソルを行No.にセットする
			setFocus(txt_LineNo);
		}
	}

}
//end of class
