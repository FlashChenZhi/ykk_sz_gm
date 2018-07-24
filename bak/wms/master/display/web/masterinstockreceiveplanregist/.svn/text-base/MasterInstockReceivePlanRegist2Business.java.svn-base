// $Id: MasterInstockReceivePlanRegist2Business.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterinstockreceiveplanregist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
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
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivecustomer.ListInstockReceiveCustomerBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveticket.ListInstockReceiveTicketBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.master.display.web.listbox.listmasteritem.ListMasterItemBusiness;
import jp.co.daifuku.wms.master.schedule.MasterInstockReceiveParameter;
import jp.co.daifuku.wms.master.schedule.MasterInstockReceivePlanRegistSCH;

/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida <BR>
 * <BR>
 * 入荷予定データ登録(詳細情報登録)クラスです。<BR>
 * 基本情報入力画面の入力情報を、上部表示領域に表示します。<BR>
 * 入荷予定データ登録を行うスケジュールにパラメータを引き渡します。<BR>
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
 * 		行No.* <BR>
 * 		商品コード* <BR>
 * 		商品名称 <BR>
 * 		ケース入数*1 <BR>
 *		ボール入数 <BR>
 *		ホスト予定ケース数 *2 <BR>
 *		ホスト予定ピース数 *2 <BR>
 *		ケースＩＴＦ <BR>
 *		ピースＩＴＦ <BR>
 *   <BR>
 * 	 <BR>
 * 		*1 予定ケース数が入力されている場合必須<BR>
 * 		*2 ケース数、ピース数いずれかに１以上の入力が必須<BR>
 *	 </DIR>
 * </DIR>
 * <BR>
 * 2.登録ボタン押下処理(<CODE>btn_Submit_Click</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   ためうちエリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが入荷予定登録を行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   結果がtrueの時はためうちの登録情報をクリアします。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   <DIR>
 * 		viewState.作業者コード* <BR>
 * 		viewState.パスワード* <BR>
 * 		viewState.荷主コード <BR>
 * 		viewState.荷主名称 <BR>
 * 		viewState.入荷予定日 <BR>
 * 		viewState.ＴＣ／ＤＣ区分 <BR>
 * 		viewState.出荷先コード <BR>
 * 		viewState.出荷先名称 <BR>
 * 		viewState.伝票No <BR>
 * 		ためうち.行No.* <BR>
 * 		ためうち.商品コード* <BR>
 * 		ためうち.商品名称 <BR>
 * 		ためうち.ケース入数*1 <BR>
 *		ためうち.ボール入数 <BR>
 *		ためうち.ホスト予定ケース数 *2 <BR>
 *		ためうち.ホスト予定ピース数 *2 <BR>
 *		ためうち.ケースＩＴＦ <BR>
 *		ためうち.ピースＩＴＦ <BR>
 *		端末No* <BR>
 *	 <DIR>
 * 		*1 予定ケース数が入力されている場合必須<BR>
 * 		*2 ケース数、ピース数いずれかに１以上の入力が必須<BR>
 *	 </DIR>
 *	 </DIR>
 * </DIR>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:17 $
 * @author  $Author: mori $
 */
public class MasterInstockReceivePlanRegist2Business extends MasterInstockReceivePlanRegist2 implements WMSConstants
{
	// viewState用KEY(隠しパラメータ)
	// ためうち行No.
	private static final String LINENO = "LINENO";
	// 仕分予定一意ｷｰ
	private static final String SORTID = "SORTID";
	// 最終更新日時
	private static final String LASTUPDDATE = "LASTUPDDATE";

	// ＴＣ／ＤＣ区分(コード)
	protected static final String TCDCFLG = "TCDCFLG";
	
	// リストセル列指定定数
	// 隠しパラメータ(リストセル)
	private static final int LST_HDN = 0;
	// 取消ボタン(リストセル)
	private static final int LST_CLRBTN = 1;
	// 修正ボタン(リストセル)
	private static final int LST_MODBTN = 2;
	// 行No(リストセル)
	private static final int LST_LINENO = 3;
	// 商品コード(リストセル)
	private static final int LST_ITEMCD = 4;
	// ケース入数(リストセル)
	private static final int LST_CASE = 5;
	// ホスト予定ケース数(リストセル)
	private static final int LST_PLANCASE = 6;
	// ケースITF(リストセル)
	private static final int LST_CASEITF = 7;
	// 商品名称(リストセル)
	private static final int LST_ITEMNM = 8;
	// ボール入数(リストセル)
	private static final int LST_BUNDLE = 9;
	// ホスト予定ピース数(リストセル)
	private static final int LST_PLANPIECE = 10;
	// ボールITF(リストセル)
	private static final int LST_BUNDLEITF = 11;
	
	// リストセル隠し項目順序
	// 仕分予定一意ｷｰ
	private static final int HDNIDX_UKEY = 0;
	// 最終更新日時
	private static final int HDNIDX_UPDAY = 1;
	
	// ＤＣ
	private static final String TCDC_DC = "0";
	// ＴＣ
	private static final String TCDC_TC = "1";
	// クロス
	private static final String TCDC_CROSS = "2";

	// 遷移先アドレス
	// 商品検索ポップアップアドレス
	private static final String DO_SRCH_ITEM = "/master/listbox/listmasteritem/ListMasterItem.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * 
	 * @param e
	 *            ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception
	 *             全ての例外を報告します。
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
		}
		// 登録ボタン押下時確認ダイアログ "登録しますか？"
		btn_Submit.setBeforeConfirm("MSG-W0009");

		// 全取消ボタン押下時確認ダイアログ "一覧入力情報がクリアされます。宜しいですか？"
		btn_AllCancel.setBeforeConfirm("MSG-W0012");

	}
	
	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 荷主コード [基本情報で設定した内容] <BR>
	 * 荷主名称 [基本情報で設定した内容] <BR>
	 * 入荷予定日 [基本情報で設定した内容] <BR>
	 * 仕入先コード [基本情報で設定した内容] <BR>
	 * 仕入先名称 [基本情報で設定した内容] <BR>
	 * ＴＣ／ＤＣ区分 [基本情報で設定した内容] <BR>
	 * 出荷先コード [基本情報で設定した内容] <BR>
	 * 出荷先名称 [基本情報で設定した内容] <BR>
	 * 伝票Ｎｏ [基本情報で設定した内容] <BR>
	 * 行Ｎｏ． [なし] <BR>
	 * 商品コード [なし] <BR>
	 * 商品名称 [なし] <BR>
	 * ケース入数 [なし] <BR>
	 * ボール入数 [なし] <BR>
	 * ホスト予定ケース数 [なし] <BR>
	 * ホスト予定ピース数 [なし] <BR>
	 * ケースＩＴＦ [なし] <BR>
	 * ボールＩＴＦ [なし] <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// タイトル
		lbl_SettingName.setText(DisplayText.getText(this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.TITLE_KEY)));		
		
		// カーソルを行No.にセットする
		setFocus(txt_LineNo);

		// 画面を初期化します
		setInitView();

		// 詳細情報修正・削除のタブを前出しします
		tab_BscDtlRst.setSelectedIndex(2);

		// 入力エリアの固定項目を設定します
		// 荷主コード
		lbl_JavaSetCnsgnrCd.setText(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY));
		// 荷主名称
		lbl_JavaSetCnsgnrNm.setText(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORNAME_KEY));
		// 入荷予定日
		txt_FDate.setText(this.getViewState().getString(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY));
		// 仕入先コード
		lbl_JavaSetSplCd.setText(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY));
		// 仕入先名称
		lbl_JavaSetSplNm.setText(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERNAME_KEY));
		// ＴＣ／ＤＣ区分
		lbl_JavaSetTCDCFlag.setText( getCrossDcName(this.getViewState().getString(TCDCFLG)));
		// 出荷先コード
		lbl_JavaSetCustCd.setText(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY));
		// 出荷先名称
		lbl_JavaSetCustNm.setText(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERNAME_KEY));
		// 伝票No.
		lbl_JavaSetTicketNo.setText(this.getViewState().getString(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY));
		
		// １画面目で決定した読取専用かどうかの判断を引き継ぎ、２画面目も表示する
		if (this.getViewState().getBoolean(MasterInstockReceivePlanRegistBusiness.IS_READ_ONLY_KEY))
		{
			txt_ItemName.setReadOnly(true);
			txt_CaseEntering.setReadOnly(true);
			txt_CaseItf.setReadOnly(true);
			txt_BundleEntering.setReadOnly(true);
			txt_BundleItf.setReadOnly(true);
		}
		
		// ためうち行No.初期化
		this.getViewState().setInt(LINENO, -1);

		// ためうちエリアを初期化します
		lst_SInstkRcvPlanRst.clearRow();

		// 登録、全取消ボタンを無効化します
		btn_Submit.setEnabled(false);
		btn_AllCancel.setEnabled(false);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。<BR>
	 * <DIR>
	 * 概要：異常があった場合は、メッセージをセットし、falseを返します。
	 * </DIR>
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
		
		return true;
		
	}
	
	// Private methods -----------------------------------------------

	/**
	 * 画面の初期化を行うメソッドです。 <BR>
	 * <BR>
	 * 概要:画面の入力エリアの入力項目を初期化します。 <BR>
	 * <BR>
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitView() throws Exception
	{
		// フォーカスをセットします(行No.)
		setFocus(txt_LineNo);
		// 行No
		txt_LineNo.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// 商品名称
		txt_ItemName.setText("");
		// ケース入数
		txt_CaseEntering.setText("");
		// ボール入数
		txt_BundleEntering.setText("");
		// ホスト予定ケース数
		txt_HostCasePlanQty.setText("");
		// ホスト予定ピース数
		txt_HostPiecePlanQty.setText("");
		// ケースＩＴＦ
		txt_CaseItf.setText("");
		// ボールＩＴＦ
		txt_BundleItf.setText("");
	}

	/**
	 * 画面の初期化を行うメソッドです。 <BR>
	 * <BR>
	 * 概要:画面のためうちエリアを初期化します。 <BR>
	 * <BR>
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitViewDetail() throws Exception
	{
		// ためうち行をすべて削除します
		lst_SInstkRcvPlanRst.clearRow();

		// ボタン押下を不可にします
		// 登録ボタン
		btn_Submit.setEnabled(false);
		// 全取消ボタン
		btn_AllCancel.setEnabled(false);

		// 修正状態をデフォルト(-1)にします
		this.getViewState().setInt(LINENO, -1);
	}

	/** Parameterクラスにためうちエリアのデータをセットするメソッドです。<BR>
	 * <BR>
	 * 概要:Parameterクラスにためうちエリアのデータをセットします。<BR>
	 * <BR>
	 * 1.新規入力であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No=-1)<BR>
	 * 2.修正中の入力データであれば、修正対象行を除いたためうちデータをParameterクラスにセットします。<BR>
	 * 3.登録ボタン押下時であれば、全てのためうちデータをParameterクラスにセットします。(修正対象ためうち行No=-1)<BR>
	 * <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			修正対象ためうち行No.* <BR>
	 * 		</DIR>
	 *   	[返却データ]<BR>
	 *   	<DIR>
	 * 			ためうちエリアの内容を持つ<CODE>StorageSupportParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private MasterInstockReceiveParameter[] setListParam(int lineno) throws Exception
	{
		Vector vecParam = new Vector();
		
		// 端末No.
		UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
		String termNo = userHandler.getTerminalNo();

		for (int i = 1; i < lst_SInstkRcvPlanRst.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}

			// 行指定
			lst_SInstkRcvPlanRst.setCurrentRow(i);

			// スケジュールパラメータへセット
			MasterInstockReceiveParameter param = new MasterInstockReceiveParameter();
			// 入力エリア情報
			// 作業者コード
			param.setWorkerCode(this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.WORKERCODE));
			// パスワード
			param.setPassword(this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.PASSWORD));
			// 荷主コード
			param.setConsignorCode(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY));
			// 荷主名称
			param.setConsignorName(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORNAME_KEY));
			// 入荷予定日
			param.setPlanDate(WmsFormatter.toParamDate(this.getViewState().getString(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY), this.httpRequest.getLocale()));
			// 仕入先コード
			param.setSupplierCode(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY));
			// 仕入先名称
			param.setSupplierName(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERNAME_KEY));
			// TC/DC区分 名称からパラメータをセットする 
			param.setTcdcFlag(this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.TCDCFLG));
			// 出荷先コード
			param.setCustomerCode(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY));
			// 出荷先名称
			param.setCustomerName(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERNAME_KEY));
			// 伝票No
			param.setInstockTicketNo(this.getViewState().getString(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY));
			// 端末No
			param.setTerminalNumber(termNo);
			// 荷主コード最終使用日
			param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(
					this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.CONSIG_DATE_KEY)));
			// 商品コード最終使用日
			param.setSupplierLastUpdateDate(WmsFormatter.getTimeStampDate(
					this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.SUPPL_DATE_KEY)));
			// 出荷先コード最終使用日
			param.setCustomerLastUpdateDate(WmsFormatter.getTimeStampDate(
					this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.CUSTOM_DATE_KEY)));

			// ためうちエリア情報
			param.setInstockLineNo(Formatter.getInt(lst_SInstkRcvPlanRst.getValue(LST_LINENO)));
			// 商品コード
			param.setItemCode(lst_SInstkRcvPlanRst.getValue(LST_ITEMCD));
			// 商品名称
			param.setItemName(lst_SInstkRcvPlanRst.getValue(LST_ITEMNM));
			// ケース入数
			param.setEnteringQty(Formatter.getInt(lst_SInstkRcvPlanRst.getValue(LST_CASE)));
			// ホスト予定ケース数
			param.setPlanCaseQty(Formatter.getInt(lst_SInstkRcvPlanRst.getValue(LST_PLANCASE)));
			// ケースITF数
			param.setITF(lst_SInstkRcvPlanRst.getValue(LST_CASEITF));
			// ボール入数
			param.setBundleEnteringQty(Formatter.getInt(lst_SInstkRcvPlanRst.getValue(LST_BUNDLE)));
			// ホスト予定ピース数
			param.setPlanPieceQty(Formatter.getInt(lst_SInstkRcvPlanRst.getValue(LST_PLANPIECE)));
			// ボールITF
			param.setBundleITF(lst_SInstkRcvPlanRst.getValue(LST_BUNDLEITF));
			// 商品コード最終使用日
			param.setItemLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(LST_HDN, lst_SInstkRcvPlanRst.getValue(LST_HDN))));

			// 行No.を保持しておく
			param.setRowNo(i);

			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			MasterInstockReceiveParameter[] listparam = new MasterInstockReceiveParameter[vecParam.size()];
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
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setListRow(MasterInstockReceiveParameter mergeParam) throws Exception
	{
		// 行No
		lst_SInstkRcvPlanRst.setValue(LST_LINENO, txt_LineNo.getText());
		// 商品コード
		lst_SInstkRcvPlanRst.setValue(LST_ITEMCD, txt_ItemCode.getText());
		// ケース入数
		lst_SInstkRcvPlanRst.setValue(LST_CASE, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));
		// ホスト予定ケース数
		lst_SInstkRcvPlanRst.setValue(LST_PLANCASE, WmsFormatter.getNumFormat(txt_HostCasePlanQty.getInt()));
		// ケースITF
		lst_SInstkRcvPlanRst.setValue(LST_CASEITF, txt_CaseItf.getText());
		// 商品名称
		lst_SInstkRcvPlanRst.setValue(LST_ITEMNM, txt_ItemName.getText());
		// ボール入数
		lst_SInstkRcvPlanRst.setValue(LST_BUNDLE, WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		// ホスト予定ピース数
		lst_SInstkRcvPlanRst.setValue(LST_PLANPIECE, WmsFormatter.getNumFormat(txt_HostPiecePlanQty.getInt()));
		// ボールITF
		lst_SInstkRcvPlanRst.setValue(LST_BUNDLEITF, txt_BundleItf.getText());
		// 商品コード最終使用日
		List hiddenList = new Vector();
		hiddenList.add(LST_HDN, WmsFormatter.getTimeStampString(mergeParam.getItemLastUpdateDate()));
		lst_SInstkRcvPlanRst.setValue(LST_HDN, CollectionUtils.getConnectedString(hiddenList));

		// 修正状態をデフォルトに戻します
		this.getViewState().setInt(LINENO, -1);
	}

	/**
	 * クロス/DC区分から対応する名称を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCrossDcName( String str ) throws Exception
	{
		// クロス/DC
		if (str.equals( InstockReceiveParameter.TCDC_FLAG_DC ))
		{
			// DC
			return DisplayText.getText("LBL-W0358");
		}
		else if (str.equals( InstockReceiveParameter.TCDC_FLAG_TC ))
		{
			// TC
			return DisplayText.getText("LBL-W0360");
		}
		else if (str.equals( InstockReceiveParameter.TCDC_FLAG_CROSSTC ))
		{
			// クロス
			return DisplayText.getText("LBL-W0359");
		}

		return "";
	}
	
	/**
	 * ためうちエリアのクロス/DC区分から対応するパラメータを取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCrossDcParam(String str)
	{
		// クロス/DC
		if (str.equals(DisplayText.getText("LBL-W0358")))
		{
			// DC
			return InstockReceiveParameter.TCDC_FLAG_DC;
		}
		else if (str.equals(DisplayText.getText("LBL-W0360")))
		{
			// TC
			return InstockReceiveParameter.TCDC_FLAG_DC;
		}
		else if (str.equals(DisplayText.getText("LBL-W0359")))
		{
			// クロス
			return InstockReceiveParameter.TCDC_FLAG_DC;
		}

		return "";
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<BR><CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		// 行Noにフォーカス移動
		setFocus(txt_LineNo);
		
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 商品コード
		String itemCode = param.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemName = param.getParameter(ListInstockReceiveItemBusiness.ITEMNAME_KEY);
		// ケース入数
		String enteringQty = param.getParameter(ListMasterItemBusiness.ENTERINGQTY_KEY);
		// ボール入数
		String bundleEnteringQty = param.getParameter(ListMasterItemBusiness.BUNDLEENTERINGQTY_KEY);
		// ケースITF
		String itf = param.getParameter(ListMasterItemBusiness.ITF_KEY);
		// ボールITF
		String bundleItf = param.getParameter(ListMasterItemBusiness.BUNDLEITF_KEY);
		
		// 空ではないときに値をセットする
		// 商品コード
		if (!StringUtil.isBlank(itemCode))
		{
			txt_ItemCode.setText(itemCode);
		}
		// 商品名称
		if (!StringUtil.isBlank(itemName))
		{
			txt_ItemName.setText(itemName);
		}
		// ケース入数
		if (!StringUtil.isBlank(enteringQty))
		{
			txt_CaseEntering.setText(enteringQty);
		}
		// ボール入数
		if (!StringUtil.isBlank(bundleEnteringQty))
		{
			txt_BundleEntering.setText(bundleEnteringQty);
		}
		// ケースITF
		if (!StringUtil.isBlank(itf))
		{
			txt_CaseItf.setText(itf);
		}
		// ボールITF
		if (!StringUtil.isBlank(bundleItf))
		{
			txt_BundleItf.setText(bundleItf);
		}
	}
	

	// Event handler methods -----------------------------------------

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
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 出荷先検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();

		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, lbl_JavaSetCnsgnrCd.getText());
		// 商品コード
		param.setParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		// 処理中画面->結果画面
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 入力ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をチェックし、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *   1.カーソルを行Ｎｏにセットします。<BR>
	 *   2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *   3.スケジュールを開始します。<BR>
	 * 	 <DIR>
	 *   [パラメータ] *必須入力<BR>
	 *   <BR>
	 *   <DIR>
	 * 		行No* <BR>
	 * 		商品コード* <BR>
	 * 		商品名称 <BR>
	 * 		ケース入数*1 <BR>
	 *		ケースITF数 <BR>
	 *		ボール入数 <BR>
	 *		ホスト予定ピース数*2 <BR>
	 *		ボールITF <BR>
	 *   <BR>
	 * 		*1 <BR>
	 * 		予定ケース数が入力されている場合必須項目 <BR>
	 * 		*1 <BR>
	 * 		ケース数、ピース数何れかに１以上の入力が必須項目 <BR>
	 *	 </DIR>
	 *   <BR>
	 *   4.スケジュールの結果がtrueであれば、ためうちエリアに追加します。<BR>
	 *     修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
	 *   5.スケジュールの結果がtrueであれば、登録ボタン・一覧クリアボタンを有効にします。<BR>
	 *   6.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *   7.スケジュールから取得したメッセージを画面に出力します。<BR>
	 * 	 8.入力エリアの内容は保持します。<BR>
	 *   <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。  
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		// カーソルを行Noにセットする
		setFocus(txt_LineNo);

		// 必須入力チェック
		// 行No
		txt_LineNo.validate();
		// 商品コード
		txt_ItemCode.validate();
		// 商品名称
		txt_ItemName.validate(false);
		// ケース入数
		txt_CaseEntering.validate(false);
		// ホスト予定ケース数
		txt_HostCasePlanQty.validate(false);
		// ケースITF
		txt_CaseItf.validate(false);
		// ボール入数
		txt_BundleEntering.validate(false);
		// ホスト予定ピース数
		txt_HostPiecePlanQty.validate(false);
		// ボールITF
		txt_BundleItf.validate(false);
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;

		try
		{
			MasterInstockReceiveParameter mergeParam = new MasterInstockReceiveParameter();
			mergeParam.setConsignorCode(lbl_JavaSetCnsgnrCd.getText());
			mergeParam.setSupplierCode(lbl_JavaSetSplCd.getText());
			mergeParam.setCustomerCode(lbl_JavaSetCustCd.getText());
			mergeParam.setItemCode(txt_ItemCode.getText());
			mergeParam.setItemName(txt_ItemName.getText());
			mergeParam.setEnteringQty(txt_CaseEntering.getInt());
			mergeParam.setBundleEnteringQty(txt_BundleEntering.getInt());
			mergeParam.setITF(txt_CaseItf.getText());
			mergeParam.setBundleITF(txt_BundleItf.getText());
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new MasterInstockReceivePlanRegistSCH(conn);
			
			mergeParam = (MasterInstockReceiveParameter) schedule.query(conn, mergeParam)[0];
			// 入力エリアに補完結果を表示
			txt_ItemName.setText(mergeParam.getItemName());
			txt_CaseEntering.setInt(mergeParam.getEnteringQty());
			txt_BundleEntering.setInt(mergeParam.getBundleEnteringQty());
			txt_CaseItf.setText(mergeParam.getITF());
			txt_BundleItf.setText(mergeParam.getBundleITF());
			
			// スケジュールパラメータへセット
			MasterInstockReceiveParameter param = new MasterInstockReceiveParameter();
			// 荷主と出荷先の最終更新日時をチェックするためセットする
			param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(
					this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.CONSIG_DATE_KEY)));
			param.setSupplierLastUpdateDate(WmsFormatter.getTimeStampDate(
					this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.SUPPL_DATE_KEY)));
			param.setCustomerLastUpdateDate(WmsFormatter.getTimeStampDate(
					this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.CUSTOM_DATE_KEY)));
			
			// 作業者コード
			param.setWorkerCode(this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.WORKERCODE));
			// パスワード
			param.setPassword(this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.PASSWORD));
			// 荷主コード
			param.setConsignorCode(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY));
			// 荷主名称
			param.setConsignorName(this.getViewState().getString(ListInstockReceiveConsignorBusiness.CONSIGNORNAME_KEY));
			// 入荷予定日
			param.setPlanDate(WmsFormatter.toParamDate(this.getViewState().getString(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY), this.httpRequest.getLocale()));
			// 仕入先コード
			param.setSupplierCode(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY));
			// 仕入先名称
			param.setSupplierName(this.getViewState().getString(ListInstockReceiveSupplierBusiness.SUPPLIERNAME_KEY));
			// TC/DC区分
			param.setTcdcFlag(this.getViewState().getString(MasterInstockReceivePlanRegistBusiness.TCDCFLG));
			// 出荷先コード
			param.setCustomerCode(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERCODE_KEY));
			// 出荷先名称
			param.setCustomerName(this.getViewState().getString(ListInstockReceiveCustomerBusiness.CUSTOMERNAME_KEY));
			// 伝票No
			param.setInstockTicketNo(this.getViewState().getString(ListInstockReceiveTicketBusiness.TICKETNUMBER_KEY));

			// 行No
			param.setInstockLineNo(Formatter.getInt(txt_LineNo.getText()));
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());
			// ケース入数
			param.setEnteringQty(txt_CaseEntering.getInt());
			// ボール入数
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			// 予定ケース数
			param.setPlanCaseQty(txt_HostCasePlanQty.getInt());
			// 予定ピース数
			param.setPlanPieceQty(txt_HostPiecePlanQty.getInt());
			// ケースＩＴＦ
			param.setITF(txt_CaseItf.getText());
			// ボールＩＴＦ
			param.setBundleITF(txt_BundleItf.getText());

			// スケジュールパラメータへわたすためうちエリアの情報をセットします
			// ためうちエリア情報格納用
			InstockReceiveParameter[] listparam = null;

			if (lst_SInstkRcvPlanRst.getMaxRows() == 1)
			{
				// ためうちにデータがなければnullをセット
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt(LINENO));
			}

			// コネクションと入力エリア情報、ためうちエリア情報(修正行を除く)でスケジュールを実行します
			if (schedule.check(conn, param, listparam))
			{
				param.setItemLastUpdateDate(mergeParam.getItemLastUpdateDate());
				// 結果がtrueであればためうちエリアにデータをセットします
				if (this.getViewState().getInt(LINENO) == -1)
				{
					// 新規入力であれば、ためうちに追加します
					// ためうちエリアに行を追加します
					lst_SInstkRcvPlanRst.addRow();
					// 追加した行を編集対象行にします
					lst_SInstkRcvPlanRst.setCurrentRow(lst_SInstkRcvPlanRst.getMaxRows() - 1);
					// データをセットします
					setListRow(param);
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新します
					// 編集行を指定します
					lst_SInstkRcvPlanRst.setCurrentRow(this.getViewState().getInt(LINENO));
					// データをセットします
					setListRow(param);
					// 選択行の色を元に戻すします
					lst_SInstkRcvPlanRst.resetHighlight();
				}

				// ためうちエリアのボタン押下を可能にします
				// 登録ボタン
				btn_Submit.setEnabled(true);
				// 全取消ボタン
				btn_AllCancel.setEnabled(true);
			}

			// メッセージをセットします
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
	 * クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要：入力エリアをクリアします。 <BR>
	 * <BR>
	 * <DIR>
	 * 1.入力エリアの項目を初期値に戻します。 <BR>
	 * <DIR>
	 * ･初期値については <CODE>setInitView()</CODE> メソッドを参照してください。 <BR>
	 * </DIR> 
	 * 2.カーソルを出荷先コードにセットします。 <BR>
	 * 3.ためうちエリアの内容は保持します。 <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 入力エリアを初期化します
		setInitView();
	}

	/** 
	 * 登録ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、入荷予定登録を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを出荷先コードにセットします。<BR>
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
	 *   				[パラメータ]*必須入力<BR>
	 * 					<DIR>
	 * 						viewState.作業者コード* <BR>
	 * 						viewState.パスワード* <BR>
	 * 						荷主コード* <BR>
	 * 						荷主名称 <BR>
	 *						入荷予定日* <BR>
	 *						仕入先コード* <BR>
	 * 						仕入先名称 <BR>
	 * 						ＴＣ／ＤＣ区分* <BR>
	 *						出荷先コード* <BR>
	 * 						出荷先名称 <BR>
	 *						伝票No.* <BR>
	 *						ためうち.行No. <BR>
	 *						ためうち.商品コード <BR>
	 *						ためうち.商品名称 <BR>
	 *						ためうち.ケース入数 <BR>
	 *						ためうち.ボール入数 <BR>
	 *						ためうち.ホスト予定ケース数 <BR>
	 *						ためうち.ホスト予定ピース数 <BR>
	 *						ためうち.ケースITF <BR>
	 *						ためうち.ボールTIF <BR>
	 *						端末No.<BR>
	 *	 				</DIR>
	 *				</DIR>
	 *				<BR>
	 *				2.スケジュールの結果がtrueの時はためうちエリアの情報をクリアします。<BR>
	 *				3.修正状態を解除します。(ViewStateのためうち行No.にデフォルト:-1を設定します。)<BR>
	 *    			4.スケジュールから取得したメッセージを画面に出力します。<BR>
	 *			</DIR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// カーソルを行Noにセットする
			setFocus(txt_LineNo);

			// 更新データ格納用
			MasterInstockReceiveParameter[] param = null;
			// ためうちエリアの全データをセットします
			param = setListParam(-1);

			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterInstockReceivePlanRegistSCH(conn);

			// スケジュールを開始します
			if (schedule.startSCH(conn, param))
			{
				// 結果がtrueであれば、コミットします
				conn.commit();

				// ためうちエリアを初期化します
				setInitViewDetail();
			}
			else
			{
				// 結果がfalseであれば、ロールバックします
				conn.rollback();
			}

			// スケジュールからのメッセージをセットします
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
	 * 全取消ボタンが押下されたときに呼ばれます。<BR>
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
	 *				1.カーソルを行Noにセットします。<BR>
	 *				2.ためうちの表示情報を全てクリアします。<BR>
	 *				3.登録ボタン・一覧クリアボタンを無効にします。<BR>
	 * 				4.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_AllCancel_Click(ActionEvent e) throws Exception
	{
		// カーソルを行Noに設定します
		setFocus(txt_LineNo);

		// ためうちエリアを初期化します
		setInitViewDetail();
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
	 *				1.カーソルを行Noにセットします。<BR>
	 *				2.入力エリア、ためうちの該当データをクリアします。<BR>
	 * 				3.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *              4.ためうち情報が存在しない場合、登録ボタン･全取消ボタンは無効にします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * <BR>
	 * <BR>
	 * 修正ボタン概要:ためうちの該当データを修正状態にします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.カーソルを出荷先コードにセットします。<BR>
	 *    2.選択されたためうち情報を上部の入力エリアに表示します。<BR>
	 *    3.選択された行を薄黄色にします。<BR>
	 *    4.ViewStateのためうち行No.に選択された行の行番号を設定します。
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SInstkRcvPlanRst_Click(ActionEvent e) throws Exception
	{
		// カーソルを行Noにセットします
		setFocus(txt_LineNo);
		
		// 取消ボタンクリック時
		if (lst_SInstkRcvPlanRst.getActiveCol() == LST_CLRBTN)
		{
			// リスト削除
			lst_SInstkRcvPlanRst.removeRow(lst_SInstkRcvPlanRst.getActiveRow());

			// ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンを無効にします
			// ためうちにデータがなければnullをセット
			if (lst_SInstkRcvPlanRst.getMaxRows() == 1)
			{
				// ためうちエリアのボタン押下を不可にします
				// 登録ボタン
				btn_Submit.setEnabled(false);
				// 全取消ボタン
				btn_AllCancel.setEnabled(false);
			}

			// 修正状態をデフォルトに戻します(ためうち行Noと背景色)
			this.getViewState().setInt(LINENO, -1);
			lst_SInstkRcvPlanRst.resetHighlight();
		}
		// 修正ボタンクリック時
		else if (lst_SInstkRcvPlanRst.getActiveCol() == LST_MODBTN)
		{
			// ボタンが押下された行を入力エリアに表示します。
			// 修正対象行を指定します
			lst_SInstkRcvPlanRst.setCurrentRow(lst_SInstkRcvPlanRst.getActiveRow());

			// 行No
			txt_LineNo.setText(lst_SInstkRcvPlanRst.getValue(LST_LINENO));
			// 商品コード
			txt_ItemCode.setText(lst_SInstkRcvPlanRst.getValue(LST_ITEMCD));
			// 商品名称
			txt_ItemName.setText(lst_SInstkRcvPlanRst.getValue(LST_ITEMNM));
			// ケース入数
			txt_CaseEntering.setText(lst_SInstkRcvPlanRst.getValue(LST_CASE));
			// ボール入数
			txt_BundleEntering.setText(lst_SInstkRcvPlanRst.getValue(LST_BUNDLE));
			// ホスト予定ケース数
			txt_HostCasePlanQty.setText(lst_SInstkRcvPlanRst.getValue(LST_PLANCASE));
			// ホスト予定ピース数
			txt_HostPiecePlanQty.setText(lst_SInstkRcvPlanRst.getValue(LST_PLANPIECE));
			// ケースITF
			txt_CaseItf.setText(lst_SInstkRcvPlanRst.getValue(LST_CASEITF));
			// ボールITF
			txt_BundleItf.setText(lst_SInstkRcvPlanRst.getValue(LST_BUNDLEITF));
			
			// ViewStateに保存
			// 修正状態を判断するためにViewStateにためうち行No.をセットします
			this.getViewState().setInt(LINENO, lst_SInstkRcvPlanRst.getActiveRow());
			// 修正行を薄黄色に変更します
			lst_SInstkRcvPlanRst.setHighlight(lst_SInstkRcvPlanRst.getActiveRow());
			
			// カーソルを行Noにセットします
			setFocus(txt_LineNo);
		}
	}

	/**
	 * 戻るボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:仕分予定修正・削除(基本情報設定)画面に遷移します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Back_Click(ActionEvent e) throws Exception
	{
		this.getViewState().setString(MasterInstockReceivePlanRegistBusiness.MESSAGE, "");
		// 詳細情報登録画面->基本情報設定画面
		forward(MasterInstockReceivePlanRegistBusiness.DO_REGIST);
	}
}
//end of class
