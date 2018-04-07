// $Id: MasterIdmNoPlanStorageBusiness.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masteridmnoplanstorage;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
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
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmconsignor.ListIdmConsignorBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmemplocation.ListIdmEmpLocationBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmitem.ListIdmItemBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmreplenishlocation.ListIdmReplenishLocationBusiness;
import jp.co.daifuku.wms.idm.display.web.listbox.listidmstorage.ListIdmStorageBusiness;
import jp.co.daifuku.wms.idm.schedule.IdmControlParameter;
import jp.co.daifuku.wms.idm.schedule.IdmOperate;
import jp.co.daifuku.wms.master.schedule.MasterIdmControlParameter;
import jp.co.daifuku.wms.master.schedule.MasterIdmNoPlanStorageSCH;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 移動ラック入庫設定クラスです。<BR>
 * 画面から入力した内容をパラメータにセットし、<BR>
 * そのパラメータを基にスケジュールが移動ラック入庫設定をします。<BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.入力ボタン押下処理（<CODE>btn_Input_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *  入力エリアから入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが入力条件のチェックを行います。<BR>
 *  スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *  スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *  結果がtrueの時は入力エリアの情報をためうちエリアに追加します。<BR>
 *  修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
 *  <BR>
 * 	[パラメータ] *必須入力<BR>
 * 	<BR>
 * 	<DIR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 * 		荷主コード* <BR>
 * 		荷主名称 <BR>
 * 		商品コード* <BR>
 * 		商品名称 <BR>
 * 		ケース／ピース区分* <BR>
 * 		入庫棚* <BR>
 * 		ケース入数*2 <BR>
 * 		入庫ケース数*2 *4 <BR>
 * 		ケースITF <BR>
 * 		ボール入数 <BR>
 *		入庫ピース数*3 *4 <BR>
 *		ボールITF <BR>
 *		賞味期限 <BR>
 *		入庫作業リストを発行する <BR>
 *		<BR>
 * 		*2 <BR>
 * 		ケース／ピース区分が1:ケース時、必須入力 <BR>
 * 		*3 <BR>
 * 		ケース／ピース区分が2:ピース時、必須入力 <BR>
 * 		*4 <BR>
 * 		ケース／ピース区分が0:指定なし時、<BR>
 *		入庫ケース数、入庫ピース数何れかに1以上の入力が必須条件 <BR>
 * 		ケース数×ケース入数＋ピース数が0でない事(>0) <BR>
 * 	</DIR>
 *  <BR>
 *  [出力用のデータ] <BR>
 *  <BR>
 * 	<DIR>
 *  	荷主コード <BR>
 *  	荷主名称 <BR>
 *  	商品コード <BR>
 *  	商品名称 <BR>
 *  	ケース/ピース区分 <BR>
 *  	ケース入数 <BR>
 *  	ボール入数 <BR>
 *  	入庫ケース数 <BR>
 *  	入庫ピース数 <BR>
 *  	ケースITF <BR>
 *  	ボールITF <BR>
 *  	賞味期限 <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.入庫開始ボタン押下処理(<CODE>btn_StorageStart_Click()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *  ためうちエリアから入力した内容をパラメータにセットし、そのパラメータをもとにスケジュールが移動ラック入庫設定を行います。<BR>
 *  スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *  スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *  <BR>
 *  [パラメータ] *必須入力 <BR>
 *  <BR>
 * 	<DIR>
 *  	作業者コード* <BR>
 *  	パスワード* <BR>
 *  	荷主コード <BR>
 *  	荷主名称 <BR>
 *  	商品コード <BR>
 *  	商品名称 <BR>
 *  	ケース/ピース区分 <BR>
 *  	入庫棚 <BR>
 *  	ケース入数 <BR>
 *  	ボール入数 <BR>
 *  	入庫ケース数 <BR>
 *  	入庫ピース数 <BR>
 *  	ケースITF <BR>
 *  	ボールITF <BR>
 *  	賞味期限 <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/20</TD><TD>C.Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:17 $
 * @author  $Author: mori $
 */
public class MasterIdmNoPlanStorageBusiness extends MasterIdmNoPlanStorage implements WMSConstants
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
	 *    1.荷主が１件の場合、荷主コード、荷主名称をテキストボックスに表示します。<BR>
	 *    2.カーソルを作業者コードにセットします。<BR>
	 *    2.「入庫作業リストを発行する」にチェックをします。<BR>
	 *    3.ためうちのボタン（入庫開始、一覧クリア）を無効にします。<BR>
	 * <BR>
	 *    項目名[初期値]<BR>
	 *    <DIR>
	 * 		作業者コード[なし] <BR>
	 * 		パスワード[なし] <BR>
	 * 		荷主コード[１件の場合、在庫情報テーブルの荷主コードをセット] <BR>
	 * 		荷主名称[１件の場合、在庫情報テーブルの荷主名称をセット] <BR>
	 * 		商品コード[なし] <BR>
	 * 		商品名称[なし] <BR>
	 * 		ケース／ピース区分[なし] <BR>
	 * 		入庫棚[なし] <BR>
	 * 		ケース入数[なし] <BR>
	 * 		入庫ケース数[なし] <BR>
	 * 		ケースITF[なし] <BR>
	 * 		ボール入数[なし] <BR>
	 * 		入庫ピース数[なし] <BR>
	 * 		ボールITF[なし] <BR>
	 * 		賞味期限[なし] <BR>
	 * 		入庫作業リストを発行する[チェックあり] <BR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
		// (デフォルト:-1)
		this.getViewState().setInt("LineNo", -1);

		// ボタン押下を不可にする
		// 入庫開始ボタン
		btn_StorageStart.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);

		// 初期表示をする
		setFirstDisp();
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
		// 入庫開始ボタン押下時確認ダイアログ "設定しますか？"
		btn_StorageStart.setBeforeConfirm("MSG-9000");

		// 一覧クリアボタン押下時確認ダイアログ "一覧入力情報がクリアされます。宜しいですか？"
		btn_ListClear.setBeforeConfirm("MSG-W0012");
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
		// 荷主コード
		String consignorcode = param.getParameter(ListIdmConsignorBusiness.CONSIGNORCODE_KEY);
		// 荷主名称
		String consignorname = param.getParameter(ListIdmConsignorBusiness.CONSIGNORNAME_KEY);
		// 商品コード
		String itemcode = param.getParameter(ListIdmItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListIdmItemBusiness.ITEMNAME_KEY);
		// ケース／ピース区分
		String casepieseflag = param.getParameter(ListIdmStorageBusiness.CASEPIECEFLAG_KEY);
		// 入庫バンク
		String storagebank = param.getParameter(ListIdmEmpLocationBusiness.BANKNO_KEY);
		// 入庫バンク
		String storagebay = param.getParameter(ListIdmEmpLocationBusiness.BAYNO_KEY);
		// 入庫バンク
		String storagelevel = param.getParameter(ListIdmEmpLocationBusiness.LEVELNO_KEY);
		// ケース入り数
		String entering_qty = param.getParameter(ListIdmStorageBusiness.ENTERINGQTY_KEY);
		// ボール入り数
		String bundle_entering_qty = param.getParameter(ListIdmStorageBusiness.BUNDLE_ENTERINGQTY_KEY);
		// ケースITF
		String itf = param.getParameter(ListIdmStorageBusiness.CASEITF_KEY);
		// ボールITF
		String boundle_itf = param.getParameter(ListIdmStorageBusiness.BUNDLEITF_KEY);
		// 賞味期限
		String use_by_date = param.getParameter(ListIdmStorageBusiness.USEBYDATE_KEY);
		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 荷主名称
		if (!StringUtil.isBlank(consignorname))
		{
			txt_ConsignorName.setText(consignorname);
		}
		// T.Kishimoto end
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		// 商品名称
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemName.setText(itemname);
		}
		// バンクNo
		if (!StringUtil.isBlank(storagebank))
		{
			txt_StorageBank.setText(storagebank);
		}
		// ベイNo
		if (!StringUtil.isBlank(storagebay))
		{
			txt_StorageBay.setText(storagebay);
		}
		// レベルNo
		if (!StringUtil.isBlank(storagelevel))
		{
			txt_StorageLevel.setText(storagelevel);
		}
		// ケース入り数
		if (!StringUtil.isBlank(entering_qty))
		{
			txt_CaseEntering.setText(entering_qty);
		}
		// ボール入り数
		if (!StringUtil.isBlank(bundle_entering_qty))
		{
			txt_BundleEntering.setText(bundle_entering_qty);
		}
		// ケースITF
		if (!StringUtil.isBlank(itf))
		{
			txt_CaseItf.setText(itf);
		}
		// ボールITF
		if (!StringUtil.isBlank(boundle_itf))
		{
			txt_BundleItf.setText(boundle_itf);
		}
		// 賞味期限
		if (!StringUtil.isBlank(use_by_date))
		{
			txt_UseByDate.setText(use_by_date);
		}

		// ケース・ピース区分
		if (!StringUtil.isBlank(casepieseflag))
		{
			if (casepieseflag.equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				// 指定なしにチェック
				rdo_CpfAppointOff.setChecked(true);
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAll.setChecked(false);
			}
			else if (casepieseflag.equals(IdmControlParameter.CASEPIECE_FLAG_CASE))
			{
				// ケースにチェック
				rdo_CpfCase.setChecked(true);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(false);
				rdo_CpfAll.setChecked(false);
			}
			else if (casepieseflag.equals(IdmControlParameter.CASEPIECE_FLAG_PIECE))
			{
				// ピースにチェック
				rdo_CpfPiece.setChecked(true);
				rdo_CpfCase.setChecked(false);
				rdo_CpfAppointOff.setChecked(false);
				rdo_CpfAll.setChecked(false);
			}
		}

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。<BR>
	 * 異常があった場合は、メッセージをセットし、falseを返します。<BR>
	 * 
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();

		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		if (!checker.checkContainNgText(txt_ConsignorName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
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
		if (!checker.checkContainNgText(txt_UseByDate))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
	
		return true;
		
	}
	// Private methods -----------------------------------------------

	/**
	 * 画面初期表示、クリア処理の場合にこのメソッドが呼ばれます。
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setFirstDisp() throws Exception
	{
		Connection conn = null;

		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterIdmNoPlanStorageSCH();

			MasterIdmControlParameter param = (MasterIdmControlParameter) schedule.initFind(conn, null);
			// 荷主コードが1件の場合、初期表示させる。
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
				
				if (param.getMergeType() == MasterIdmControlParameter.MERGE_TYPE_OVERWRITE)
				{
					txt_ConsignorName.setReadOnly(true);
					txt_ItemName.setReadOnly(true);
					txt_CaseEntering.setReadOnly(true);
					txt_BundleEntering.setReadOnly(true);
					txt_CaseItf.setReadOnly(true);
					txt_BundleItf.setReadOnly(true);
				}
			}
			else
			{
				txt_ConsignorCode.setText("");
				txt_ConsignorName.setText("");
			}

			// 商品コード
			txt_ItemCode.setText("");
			// 商品名称
			txt_ItemName.setText("");
			// ケース/ピース区分(ケース)
			rdo_CpfCase.setChecked(true);
			// ケース/ピース区分(ピース)
			rdo_CpfPiece.setChecked(false);
			// ケース/ピース区分(指定なし)
			rdo_CpfAppointOff.setChecked(false);
			// ケース/ピース区分(全て)
			rdo_CpfAll.setChecked(false);
			if (lst_INoPlanStorage.getMaxRows() <= 1)
			{
				// 入庫棚(バンク)
				txt_StorageBank.setText("");
				// 入庫棚(ベイ)
				txt_StorageBay.setText("");
				// 入庫棚(レベル)
				txt_StorageLevel.setText("");
			}
			// ケース入数
			txt_CaseEntering.setText("");
			// 入庫ケース数
			txt_StrgCaseQty.setText("");
			// ケースITF
			txt_CaseItf.setText("");
			// ボール入数
			txt_BundleEntering.setText("");
			// 入庫ピース数
			txt_StrgPieseQty.setText("");
			// ボールITF
			txt_BundleItf.setText("");
			// 賞味期限
			txt_UseByDate.setText("");
			// 「入庫作業リストを発行する」にチェックをする
			chk_CommonUse.setChecked(true);

			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);
		}
		catch (Exception ex)
		{
			// コネクションロールバック
			if (conn != null)
			{
				conn.rollback();
			}
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
	 * ケース、ピースの値チェックを行うメソッドです。<BR>
	 * <BR>
	 * 概要:ケース、ピースの値が0以上かをチェックします。<BR>
	 * 
	 * @param figure 	  値チェックするための使用桁です。
	 * @param name 	  値チェックする項目名です。
	 * @throws Exception 全ての例外を報告します。
	 * @return itemName 使用桁か位置が1以上じゃなかった場合項目名を返します。
	 */
	private String checkNumber(NumberTextBox figure, Label name) throws Exception
	{
		String itemName = null;

		if (!StringUtil.isBlank(Integer.toString(figure.getInt())))
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
	 * @param lineno 対象となる行No。
	 * @throws Exception 全ての例外を報告します。 
	 * @return listparam セットするためうちデータがあれば値を返却します。
	 */
	private MasterIdmControlParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_INoPlanStorage.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}

			// 行指定
			lst_INoPlanStorage.setCurrentRow(i);

			// スケジュールパラメータへセット
			MasterIdmControlParameter param = new MasterIdmControlParameter();
			// 入力エリア情報
			// 端末No.
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_PassWord.getText());
			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());
			// リスト発行区分
			param.setListFlg(chk_CommonUse.getChecked());
			// 荷主コード
			param.setConsignorCode(lst_INoPlanStorage.getValue(3));
			// 商品コード
			param.setItemCode(lst_INoPlanStorage.getValue(4));
			// ケース／ピース区分
			//param.setCasePieceFlag(lst_INoPlanStorage.getValue(0));
			param.setCasePieceFlag(CollectionUtils.getString(0, lst_INoPlanStorage.getValue(0)));
			// ケース入数
			param.setEnteringQty(Formatter.getInt(lst_INoPlanStorage.getValue(6)));
			// 入庫ケース数
			param.setStorageCaseQty(Formatter.getInt(lst_INoPlanStorage.getValue(7)));
			// ケースITF
			param.setITF(lst_INoPlanStorage.getValue(8));
			// 賞味期限
			param.setUseByDate(lst_INoPlanStorage.getValue(9));
			// 荷主名称
			param.setConsignorName(lst_INoPlanStorage.getValue(10));
			// 商品名称
			param.setItemName(lst_INoPlanStorage.getValue(11));
			// ボール入数
			param.setBundleEnteringQty(Formatter.getInt(lst_INoPlanStorage.getValue(12)));
			// 入庫ピース数
			param.setStoragePieceQty(Formatter.getInt(lst_INoPlanStorage.getValue(13)));
			// ボールITF
			param.setBundleITF(lst_INoPlanStorage.getValue(14));
			// 入庫棚(バンク)
			param.setBankNo(txt_StorageBank.getText());
			// 入庫棚(ベイ)
			param.setBayNo(txt_StorageBay.getText());
			// 入庫棚(レベル)
			param.setLevelNo(txt_StorageLevel.getText());
			// 入庫棚
			IdmOperate iOpe = new IdmOperate();
			param.setLocationNo(iOpe.importFormatIdmLocation(param.getBankNo(), param.getBayNo(), param.getLevelNo()));

			// 行No.を保持しておく
			param.setRowNo(i);

			// 荷主コード最終使用日
			param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, lst_INoPlanStorage.getValue(0))));
			// 商品コード最終使用日
			param.setItemLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(2, lst_INoPlanStorage.getValue(0))));
			
			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			MasterIdmControlParameter[] listparam = new MasterIdmControlParameter[vecParam.size()];
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
	 * 入力エリア.ケースピース区分ラジオボタンから対応する出庫パラメータ.ケース/ピース区分を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。
	 * @return ケースピースフラグを返却します。 
	 */
	private String getCasePieceFlagFromInputArea() throws Exception
	{
		// ケース/ピース区分
		if (rdo_CpfAppointOff.getChecked())
		{
			// 指定なし
			return IdmControlParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_CpfCase.getChecked())
		{
			// ケース
			return IdmControlParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_CpfPiece.getChecked())
		{
			// ピース
			return IdmControlParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	/**
	 * 在庫パラメータ.ケースピース区分から対応するケース/ピース区分を取得します。<BR>
	 * <BR>
	 * @param value ラジオボタンに対応する出庫パラメータ.ケース/ピース区分です。
	 * @throws Exception 全ての例外を報告します。  
	 * @return ケースピースフラグを返却します。 
	 */
	private String getCasePieceFlag(String value) throws Exception
	{
		// ケース/ピース区分
		if (value.equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			// 指定なし
			return SystemDefine.CASEPIECE_FLAG_NOTHING;
		}
		else if (value.equals(IdmControlParameter.CASEPIECE_FLAG_CASE))
		{
			// ケース
			return SystemDefine.CASEPIECE_FLAG_CASE;
		}
		else if (value.equals(IdmControlParameter.CASEPIECE_FLAG_PIECE))
		{
			// ピース
			return SystemDefine.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}

	/**
	 * リストセル.区分の内容を入力エリアのケース/ピース区分チェックボックスへ表示します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setCasePieceRBFromList() throws Exception
	{
		if (lst_INoPlanStorage.getValue(0).equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			// 指定なしにチェック
			rdo_CpfAppointOff.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAll.setChecked(false);
		}
		else if (lst_INoPlanStorage.getValue(0).equals(IdmControlParameter.CASEPIECE_FLAG_CASE))
		{
			// ケースにチェック
			rdo_CpfCase.setChecked(true);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
			rdo_CpfAll.setChecked(false);
		}
		else if (lst_INoPlanStorage.getValue(0).equals(IdmControlParameter.CASEPIECE_FLAG_PIECE))
		{
			// ピースにチェック
			rdo_CpfPiece.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
			rdo_CpfAll.setChecked(false);
		}
	}

	/**
	 * ためうちエリアに値をセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアに値をセットします。<BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setList(MasterIdmControlParameter mergeParam) throws Exception
	{
		//ToolTip用のデータを編集
		ToolTipHelper toolTip = new ToolTipHelper();
		// 荷主名称
		toolTip.add(DisplayText.getText("LBL-W0026"), txt_ConsignorName.getText());
		// 商品名称
		toolTip.add(DisplayText.getText("LBL-W0103"), txt_ItemName.getText());
		// ｹｰｽITF
		toolTip.add(DisplayText.getText("LBL-W0010"), txt_CaseItf.getText());
		// ﾎﾞｰﾙITF
		toolTip.add(DisplayText.getText("LBL-W0006"), txt_BundleItf.getText());
		// 賞味期限
		toolTip.add(DisplayText.getText("LBL-W0270"), txt_UseByDate.getText());

		//カレント行にTOOL TIPをセットする
		lst_INoPlanStorage.setToolTip(lst_INoPlanStorage.getCurrentRow(), toolTip.getText());

		// ケース／ピース区分（隠し項目）
		//lst_INoPlanStorage.setValue(0, getCasePieceFlagFromInputArea());
		List hiddenList = new Vector();
		hiddenList.add(0, getCasePieceFlagFromInputArea());
		// 荷主コード最終使用日
		hiddenList.add(1, WmsFormatter.getTimeStampString(mergeParam.getConsignorLastUpdateDate()));
		// 商品コード最終使用日
		hiddenList.add(2, WmsFormatter.getTimeStampString(mergeParam.getItemLastUpdateDate()));
		lst_INoPlanStorage.setValue(0, CollectionUtils.getConnectedString(hiddenList));
		// 荷主コード
		lst_INoPlanStorage.setValue(3, txt_ConsignorCode.getText());
		// 商品コード
		lst_INoPlanStorage.setValue(4, txt_ItemCode.getText());
		// 区分
		lst_INoPlanStorage.setValue(5, DisplayUtil.getPieceCaseValue(getCasePieceFlag(getCasePieceFlagFromInputArea())));
		// ケース入数
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_INoPlanStorage.setValue(6, "0");
		}
		else
		{
			lst_INoPlanStorage.setValue(6, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		// 入庫ケース数
		if (StringUtil.isBlank(txt_StrgCaseQty.getText()))
		{
			lst_INoPlanStorage.setValue(7, "0");
		}
		else
		{
			lst_INoPlanStorage.setValue(7, WmsFormatter.getNumFormat(txt_StrgCaseQty.getInt()));
		}
		// ケースITF
		lst_INoPlanStorage.setValue(8, txt_CaseItf.getText());
		// 賞味期限
		lst_INoPlanStorage.setValue(9, txt_UseByDate.getText());
		// 荷主名称
		lst_INoPlanStorage.setValue(10, txt_ConsignorName.getText());
		// 商品名称
		lst_INoPlanStorage.setValue(11, txt_ItemName.getText());
		// ボール入数
		if (StringUtil.isBlank(txt_BundleEntering.getText()))
		{
			lst_INoPlanStorage.setValue(12, "0");
		}
		else
		{
			lst_INoPlanStorage.setValue(12, WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		}
		// 入庫ピース数
		if (StringUtil.isBlank(txt_StrgPieseQty.getText()))
		{
			lst_INoPlanStorage.setValue(13, "0");
		}
		else
		{
			lst_INoPlanStorage.setValue(13, WmsFormatter.getNumFormat(txt_StrgPieseQty.getInt()));
		}
		// ボールITF
		lst_INoPlanStorage.setValue(14, txt_BundleItf.getText());
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
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。<BR>
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
	public void btn_PSearchItem_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListIdmConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListIdmItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 処理中画面->結果画面
		redirect("/master/listbox/listmasteritem/ListMasterItem.do", param, "/progress.do");
	}

	/** 
	 * 在庫検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で在庫照会一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力<BR>
	 *    <DIR>
	 *       荷主コード* <BR>
	 *       商品コード <BR>
	 *       ケース／ピース区分 <BR>
	 *       入庫棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PStockSearch_Click(ActionEvent e) throws Exception
	{
		// 在庫検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		
		// 荷主コード
		param.setParameter(
			ListIdmConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
			
		// 商品コード
		param.setParameter(ListIdmItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		
		// ケース／ピース区分
		// 全て
		if (rdo_CpfAll.getChecked())
		{
			param.setParameter(ListIdmStorageBusiness.CASEPIECEFLAG_KEY, IdmControlParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		if (rdo_CpfCase.getChecked())
		{
			param.setParameter(ListIdmStorageBusiness.CASEPIECEFLAG_KEY, IdmControlParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_CpfPiece.getChecked())
		{
			param.setParameter(ListIdmStorageBusiness.CASEPIECEFLAG_KEY, IdmControlParameter.CASEPIECE_FLAG_PIECE);
		}
		// 指定なし
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setParameter(ListIdmStorageBusiness.CASEPIECEFLAG_KEY, IdmControlParameter.CASEPIECE_FLAG_NOTHING);
		}

		// 処理中画面->結果画面
		redirect(
			"/idm/listbox/listidmstorage/ListIdmStorage.do", param, "/progress.do");
	}

	/**
	 * 入力ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアの入力項目をチェックし、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *   1.カーソルを作業者コードにセットします。<BR>
	 *   2.入力エリア入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *   3.スケジュールを開始します。<BR>
	 * 	 <DIR>
	 *   	[パラメータ] *必須入力<BR>
	 *   	<DIR>
	 * 			作業者コード* <BR>
	 * 			パスワード* <BR>
	 * 			荷主コード* <BR>
	 * 			荷主名称 <BR>
	 * 			商品コード* <BR>
	 * 			商品名称 <BR>
	 * 			ケース／ピース区分* <BR>
	 * 			入庫棚* <BR>
	 * 			ケース入数*2 <BR>
	 * 			入庫ケース数*2 *4 <BR>
	 * 			ケースITF <BR>
	 * 			ボール入数 <BR>
	 *			入庫ピース数*3 *4 <BR>
	 *			ボールITF <BR>
	 *			賞味期限 <BR>
	 *			予定外入庫リストを発行する <BR>
	 *		<BR>
	 * 			*2 <BR>
	 * 			ケース／ピース区分が1:ケース時、必須入力 <BR>
	 * 			*3 <BR>
	 * 			ケース／ピース区分が2:ピース時、必須入力 <BR>
	 * 			*4 <BR>
	 * 			ケース／ピース区分が0:指定なし時、<BR>
	 *			入庫ケース数、入庫ピース数何れかに1以上の入力が必須条件 <BR>
	 * 			ケース数×ケース入数＋ピース数が0でない事(>0) <BR>
	 *	 	</DIR>
	 *   </DIR>
	 *   <BR>
	 *   4.スケジュールの結果がtrueであれば、ためうちエリアに追加します。<BR>
	 *     修正ボタン押下後の入力ボタン押下時は、入力エリアの情報でためうちの修正対象データを更新します。<BR>
	 *   5.スケジュールの結果がtrueであれば、登録ボタン・一覧クリアボタンを有効にします。<BR>
	 *   6.スケジュールの結果がtrueであれば、ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *   7.スケジュールから取得したメッセージを画面に出力します。<BR>
	 * 	 8.入力エリアの内容は保持します。<BR>
	 *   <BR>
	 * 	 リストセルの列番号一覧<BR>
	 *   <DIR>
	 * 		3.荷主コード <BR>
	 * 		4.商品コード <BR>
	 * 		5.区分 <BR>
	 * 		6.ケース入数 <BR>
	 * 		7.入庫ケース数 <BR>
	 * 		8.ケースITF <BR>
	 * 		9.賞味期限 <BR>
	 * 		10.荷主名称 <BR>
	 * 		11.商品名称 <BR>
	 * 		12.ボール入数 <BR>
	 * 		13.入庫ピース数 <BR>
	 * 		14.ボールITF <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);

		// 必須入力チェック
		txt_WorkerCode.validate();
		txt_PassWord.validate();
		txt_StorageBank.validate(true);
		txt_StorageBay.validate(true);
		txt_StorageLevel.validate(true);
		txt_ConsignorCode.validate();
		txt_ItemCode.validate();

		// パターンマッチング文字
		txt_ConsignorName.validate(false);
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_StrgCaseQty.validate(false);
		txt_CaseItf.validate(false);
		txt_BundleEntering.validate(false);
		txt_StrgPieseQty.validate(false);
		txt_BundleItf.validate(false);
		txt_UseByDate.validate(false);
		
		String itemname = null;

		itemname = checkNumber(txt_CaseEntering, lbl_CaseEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_BundleEntering, lbl_BundleEntering);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgCaseQty, lbl_StrageCaseQty);
		if (StringUtil.isBlank(itemname))
			itemname = checkNumber(txt_StrgPieseQty, lbl_StragePieseQty);

		if (!StringUtil.isBlank(itemname))
		{
			// エラーメッセージを表示し、終了する。
			// 6023057 = {0}には{1}以上の値を入力してください。
			message.setMsgResourceKey("6023057" + wDelim + itemname + wDelim + "0");
			return;
		}
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;

		try
		{
			MasterIdmControlParameter mergeParam = new MasterIdmControlParameter();
			mergeParam.setConsignorCode(txt_ConsignorCode.getText());
			mergeParam.setConsignorName(txt_ConsignorName.getText());
			mergeParam.setItemCode(txt_ItemCode.getText());
			mergeParam.setItemName(txt_ItemName.getText());
			mergeParam.setEnteringQty(txt_CaseEntering.getInt());
			mergeParam.setBundleEnteringQty(txt_BundleEntering.getInt());
			mergeParam.setITF(txt_CaseItf.getText());
			mergeParam.setBundleITF(txt_BundleItf.getText());
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new MasterIdmNoPlanStorageSCH(conn);
			
			mergeParam = (MasterIdmControlParameter) schedule.query(conn, mergeParam)[0];
			// 入力エリアに補完結果を表示
			txt_ConsignorName.setText(mergeParam.getConsignorName());
			txt_ItemName.setText(mergeParam.getItemName());
			txt_CaseEntering.setInt(mergeParam.getEnteringQty());
			txt_BundleEntering.setInt(mergeParam.getBundleEnteringQty());
			txt_CaseItf.setText(mergeParam.getITF());
			txt_BundleItf.setText(mergeParam.getBundleITF());
			
			// スケジュールパラメータへセット
			// 入力エリア
			MasterIdmControlParameter param = new MasterIdmControlParameter();
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_PassWord.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 荷主名称
			param.setConsignorName(txt_ConsignorName.getText());
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());
			// ケースピース区分
			param.setCasePieceFlag(getCasePieceFlagFromInputArea());
			// 入庫棚(バンク)
			param.setBankNo(txt_StorageBank.getText());
			// 入庫棚(ベイ)
			param.setBayNo(txt_StorageBay.getText());
			// 入庫棚(レベル)
			param.setLevelNo(txt_StorageLevel.getText());
			// ケース入数
			param.setEnteringQty(txt_CaseEntering.getInt());
			// 入庫ケース数
			param.setStorageCaseQty(txt_StrgCaseQty.getInt());
			// ケースITF
			param.setITF(txt_CaseItf.getText());
			// ボール入数
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			// 入庫ピース数
			param.setStoragePieceQty(txt_StrgPieseQty.getInt());
			// ボールITF
			param.setBundleITF(txt_BundleItf.getText());
			// 賞味期限
			param.setUseByDate(txt_UseByDate.getText());
			// 予定外入庫リストを発行
			param.setListFlg(chk_CommonUse.getChecked());

			// スケジュールパラメータへセット
			// ためうちエリア
			IdmControlParameter[] listparam = null;

			// ためうちにデータがなければnullをセット
			if (lst_INoPlanStorage.getMaxRows() == 1)
			{
				listparam = null;
			}
			else
			{
				// データが存在していれば値をセット
				listparam = setListParam(this.getViewState().getInt("LineNo"));
			}

			if (schedule.check(conn, param, listparam))
			{
				param.setConsignorLastUpdateDate(mergeParam.getConsignorLastUpdateDate());
				param.setItemLastUpdateDate(mergeParam.getItemLastUpdateDate());
				// 結果がtrueであればためうちエリアにデータをセット
				if (this.getViewState().getInt("LineNo") == -1)
				{
					// 新規入力であれば、ためうちに追加
					lst_INoPlanStorage.addRow();
					lst_INoPlanStorage.setCurrentRow(lst_INoPlanStorage.getMaxRows() - 1);
					setList(param);
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新
					lst_INoPlanStorage.setCurrentRow(this.getViewState().getInt("LineNo"));
					setList(param);
					// 選択行の色を元に戻す
					lst_INoPlanStorage.resetHighlight();
				}

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);

				// ボタン押下を可能にする
				// 入庫開始ボタン
				btn_StorageStart.setEnabled(true);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(true);
				// 入庫棚の入力を不可状態にする
				txt_StorageBank.setReadOnly(true);
				txt_StorageBay.setReadOnly(true);
				txt_StorageLevel.setReadOnly(true);
				// 空棚検索ボタン・補充棚検索ボタンを不可にする
				btn_PSearchEmpLocation.setEnabled(false);
				btn_PSearchReplenishLocation.setEnabled(false);

			}

			// メッセージをセット
			message.setMsgResourceKey(schedule.getMessage());

		}
		catch (Exception ex)
		{
			// コネクションロールバック
			if (conn != null)
			{
				conn.rollback();
			}
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
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要：入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリアの項目を初期値に戻します。<BR>
	 *    <DIR>
	 *  	･初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。<BR>
	 *    </DIR>
	 *    2.カーソルを作業者コードにセットします。<BR>
	 *    3.ためうちエリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// クリア処理を行う
		setFirstDisp();
	}

	/**
	 * 入庫開始ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、予定外入庫設定を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを作業者コードにセットします。<BR>
	 *    2.入庫開始を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 *    <DIR>
	 *      "設定しますか？"<BR>
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
	 * 						ためうち.荷主コード <BR>
	 * 						ためうち.荷主名称 <BR>
	 *						ためうち.商品コード <BR>
	 *						ためうち.商品名称 <BR>
	 *						ためうち.区分 <BR>
	 *						ためうち.入庫棚 <BR>
	 *						ためうち.ケース入数 <BR>
	 *						ためうち.ボール入数 <BR>
	 *						ためうち.入庫ケース数 <BR>
	 *						ためうち.入庫ピース数 <BR>
	 *						ためうち.ケースITF <BR>
	 *						ためうち.ボールITF <BR>
	 *						ためうち.賞味期限 <BR>
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
	 * 		3.荷主コード <BR>
	 * 		4.商品コード <BR>
	 * 		5.区分 <BR>
	 * 		6.入庫棚 <BR>
	 * 		7.ケース入数 <BR>
	 * 		8.入庫ケース数 <BR>
	 * 		9.ケースITF <BR>
	 * 		10.賞味期限 <BR>
	 * 		11.荷主名称 <BR>
	 * 		12.商品名称 <BR>
	 * 		13.ボール入数 <BR>
	 * 		14.入庫ピース数 <BR>
	 * 		15.ボールITF <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_StorageStart_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		try
		{
			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);

			// スケジュールパラメータへセット
			MasterIdmControlParameter[] param = null;
			// ためうちエリアの全データをセット
			param = setListParam(-1);

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterIdmNoPlanStorageSCH(conn);

			// スケジュールスタート
			if (schedule.startSCH(conn, param))
			{
				// 結果がtrueであれば、commit
				conn.commit();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());

				// 一覧クリア
				lst_INoPlanStorage.clearRow();

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);

				// ボタン押下を不可にする
				// 入庫開始ボタン
				btn_StorageStart.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);
				// 入庫棚の入力を可能状態にする
				txt_StorageBank.setReadOnly(false);
				txt_StorageBay.setReadOnly(false);
				txt_StorageLevel.setReadOnly(false);
				// 空棚検索ボタン・補充棚検索ボタンを可能状態にする
				btn_PSearchEmpLocation.setEnabled(true);
				btn_PSearchReplenishLocation.setEnabled(true);
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
			ex.printStackTrace();
			// コネクションロールバック
			if (conn != null)
			{
				conn.rollback();
			}
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
	 *				5.カーソルを作業者コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// 行をすべて削除
		lst_INoPlanStorage.clearRow();

		// ボタン押下を不可にする
		// 入庫開始ボタン
		btn_StorageStart.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);
		// 入庫棚の入力を可能にする
		txt_StorageBank.setReadOnly(false);
		txt_StorageBay.setReadOnly(false);
		txt_StorageLevel.setReadOnly(false);
		// 空棚検索ボタン・補充棚検索ボタンを不可にする
		btn_PSearchEmpLocation.setEnabled(true);
		btn_PSearchReplenishLocation.setEnabled(true);

		// 修正状態をデフォルトに戻す
		this.getViewState().setInt("LineNo", -1);

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}

	/** 
	 * 空棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       入庫棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchEmpLocation_Click(ActionEvent e) throws Exception
	{
		// 棚検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 入庫棚(バンク)
		param.setParameter(ListIdmEmpLocationBusiness.BANKNO_KEY, txt_StorageBank.getText());
		// 入庫棚(ベイ)
		param.setParameter(ListIdmEmpLocationBusiness.BAYNO_KEY, txt_StorageBay.getText());
		// 入庫棚(レベル)
		param.setParameter(ListIdmEmpLocationBusiness.LEVELNO_KEY, txt_StorageLevel.getText());
		// 処理中画面->結果画面
		redirect(
			"/idm/listbox/listidmemplocation/ListIdmEmpLocation.do",
			param,
			"/progress.do");
	}

	/** 
	 * 補充棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 *       ケース・ピース区分 <BR>
	 *       入庫棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchReplenishLocation_Click(ActionEvent e) throws Exception
	{
		// 棚検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListIdmConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListIdmItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());		
		// ケース／ピース区分
		// 全て
		if (rdo_CpfAll.getChecked())
		{
			param.setParameter(ListIdmReplenishLocationBusiness.CASEPIECEFLAG_KEY, IdmControlParameter.CASEPIECE_FLAG_ALL);
		}
		// ケース
		if (rdo_CpfCase.getChecked())
		{
			param.setParameter(ListIdmReplenishLocationBusiness.CASEPIECEFLAG_KEY, IdmControlParameter.CASEPIECE_FLAG_CASE);
		}
		// ピース
		else if (rdo_CpfPiece.getChecked())
		{
			param.setParameter(ListIdmReplenishLocationBusiness.CASEPIECEFLAG_KEY, IdmControlParameter.CASEPIECE_FLAG_PIECE);
		}
		// 指定なし
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setParameter(ListIdmReplenishLocationBusiness.CASEPIECEFLAG_KEY, IdmControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		// 入庫棚(バンク)
		param.setParameter(ListIdmEmpLocationBusiness.BANKNO_KEY, txt_StorageBank.getText());
		// 入庫棚(ベイ)
		param.setParameter(ListIdmEmpLocationBusiness.BAYNO_KEY, txt_StorageBay.getText());
		// 入庫棚(レベル)
		param.setParameter(ListIdmEmpLocationBusiness.LEVELNO_KEY, txt_StorageLevel.getText());
		// 処理中画面->結果画面
		redirect("/idm/listbox/listidmreplenishlocation/ListIdmReplenishLocation.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignor_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListIdmConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 処理中画面->結果画面
		redirect("/master/listbox/listmasterconsignor/ListMasterConsignor.do", param, "/progress.do");
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
	 *				4.カーソルを作業者コードにセットします。<BR>
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
	 *    4.カーソルを作業者コードにセットします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_INoPlanStorage_Click(ActionEvent e) throws Exception
	{
		// 取消ボタンクリック時
		if (lst_INoPlanStorage.getActiveCol() == 1)
		{
			// リスト削除
			lst_INoPlanStorage.removeRow(lst_INoPlanStorage.getActiveRow());

			// ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にする
			// ためうちにデータがなければnullをセット
			if (lst_INoPlanStorage.getMaxRows() == 1)
			{
				// ボタン押下を不可にする
				// 入庫開始ボタン
				btn_StorageStart.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);
				// 入庫棚の入力を可能にする
				txt_StorageBank.setReadOnly(false);
				txt_StorageBay.setReadOnly(false);
				txt_StorageLevel.setReadOnly(false);
				// 空棚検索ボタン・補充棚検索ボタンを不可にする
				btn_PSearchEmpLocation.setEnabled(true);
				btn_PSearchReplenishLocation.setEnabled(true);
			}

			// 修正状態をデフォルトに戻す
			this.getViewState().setInt("LineNo", -1);

			// 選択行の色を元に戻す
			lst_INoPlanStorage.resetHighlight();

			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);

		}
		// 修正ボタンクリック時(Modify動作を行う)
		else if (lst_INoPlanStorage.getActiveCol() == 2)
		{
			// 現在の行をセット
			lst_INoPlanStorage.setCurrentRow(lst_INoPlanStorage.getActiveRow());
			// 荷主コード
			txt_ConsignorCode.setText(lst_INoPlanStorage.getValue(3));
			// 商品コード
			txt_ItemCode.setText(lst_INoPlanStorage.getValue(4));
			// ケース／ピース区分
			setCasePieceRBFromList();
			// ケース入数
			txt_CaseEntering.setText(lst_INoPlanStorage.getValue(6));
			// 入庫ケース数
			txt_StrgCaseQty.setText(lst_INoPlanStorage.getValue(7));
			// ケースITF
			txt_CaseItf.setText(lst_INoPlanStorage.getValue(8));
			// 賞味期限
			txt_UseByDate.setText(lst_INoPlanStorage.getValue(9));
			// 荷主名称
			txt_ConsignorName.setText(lst_INoPlanStorage.getValue(10));
			// 商品名称
			txt_ItemName.setText(lst_INoPlanStorage.getValue(11));
			// ボール入数
			txt_BundleEntering.setText(lst_INoPlanStorage.getValue(12));
			// 入庫ピース数
			txt_StrgPieseQty.setText(lst_INoPlanStorage.getValue(13));
			// ボールITF
			txt_BundleItf.setText(lst_INoPlanStorage.getValue(14));

			// ViewStateに保存
			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			this.getViewState().setInt("LineNo", lst_INoPlanStorage.getActiveRow());

			//修正行を黄色に変更する
			lst_INoPlanStorage.setHighlight(lst_INoPlanStorage.getActiveRow());

			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);
			if (lst_INoPlanStorage.getMaxRows() == 2)
			{
				// 入庫棚の入力を可能にする
				txt_StorageBank.setReadOnly(false);
				txt_StorageBay.setReadOnly(false);
				txt_StorageLevel.setReadOnly(false);
				// 空棚検索ボタン・補充棚検索ボタンを不可にする
				btn_PSearchEmpLocation.setEnabled(true);
				btn_PSearchReplenishLocation.setEnabled(true);
			}
		}
	}
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
	public void W_Set_Click(ActionEvent e) throws Exception
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
	public void lbl_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PassWord_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StorageLocation_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageBank_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageBank_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageBank_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageBank_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphn1_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageBay_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageBay_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageBay_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageBay_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Hyphn2_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLevel_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLevel_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLevel_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StorageLevel_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchEmpLocation_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchReplenishLocation_Server(ActionEvent e) throws Exception
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
	public void btn_PSearchConsignor_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ConsignorName_InputComplete(ActionEvent e) throws Exception
	{
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
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PStockSearch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_ItemName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CasePieceFlag_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfCase_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfPiece_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAppointOff_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CpfAll_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StrageCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgCaseQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgCaseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgCaseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_CaseItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleEntering_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_StragePieseQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPieseQty_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPieseQty_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_StrgPieseQty_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_BundleItf_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_UseByDate_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Idmschstwoli_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void chk_CommonUse_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Input_Server(ActionEvent e) throws Exception
	{
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
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_StorageStart_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_INoPlanStorage_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_INoPlanStorage_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_INoPlanStorage_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_INoPlanStorage_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_INoPlanStorage_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_INoPlanStorage_Change(ActionEvent e) throws Exception
	{
	}


}
//end of class
