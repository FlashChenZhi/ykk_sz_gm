// $Id: MasterStoragePlanRegistBusiness.java,v 1.1.1.1 2006/08/17 09:34:19 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterstorageplanregist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
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
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.master.schedule.MasterStoragePlanRegistSCH;
import jp.co.daifuku.wms.master.schedule.MasterStorageSupportParameter;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageconsignor.ListStorageConsignorBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageitem.ListStorageItemBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststoragelocation.ListStorageLocationBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplandate.ListStoragePlanDateBusiness;
import jp.co.daifuku.wms.storage.display.web.listbox.liststorageplanregist.ListStoragePlanRegistBusiness;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

/**
 * Designer : T.Uehata <BR>
 * Maker : T.Uehata <BR>
 * <BR>
 * 入庫予定登録クラスです。<BR>
 * 画面から入力した内容をパラメータにセットし、<BR>
 * そのパラメータを基にスケジュールが入庫予定登録をします。<BR>
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
 * 		入庫予定日* <BR>
 * 		商品コード* <BR>
 * 		商品名称 <BR>
 * 		ケース／ピース区分* <BR>
 * 		入庫棚 <BR>
 * 		ケース入数 <BR>
 * 		ホスト予定ケース数*1 <BR>
 * 		ケースＩＴＦ <BR>
 * 		ボール入数 <BR>
 * 		ホスト予定ピース数*1 <BR>
 * 		ボールＩＴＦ <BR>
 *		<BR>
 * 		*1 <BR>
 * 		いずれか選択必須入力 <BR>
 * 	</DIR>
 *  <BR>
 *  [出力用のデータ] <BR>
 *  <BR>
 * 	<DIR>
 *  	荷主コード <BR>
 *  	荷主名称 <BR>
 *  	入庫予定日 <BR>
 *  	商品コード <BR>
 *  	商品名称 <BR>
 *  	区分 <BR>
 *  	入庫棚 <BR>
 *  	ケース入数 <BR>
 *  	ボール入数 <BR>
 *  	ホスト予定ケース数 <BR>
 *  	ホスト予定ピース数 <BR>
 *  	ケースITF <BR>
 *  	ボールITF <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.登録ボタン押下処理(<CODE>btn_StorageStart_Click()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *  ためうちエリアから入力した内容をパラメータにセットし、<BR>
 *  そのパラメータをもとにスケジュールが入庫予定登録を行います。<BR>
 *  スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *  スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * 	<DIR>
 *   [パラメータ] *必須入力 <BR>
 *   <BR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 *  	荷主コード* <BR>
 *  	荷主名称 <BR>
 *  	入庫予定日* <BR>
 *  	商品コード* <BR>
 *  	商品名称 <BR>
 *  	区分* <BR>
 *  	入庫棚* <BR>
 *  	ケース入数 <BR>
 *  	ボール入数 <BR>
 *  	ホスト予定ケース数 <BR>
 *  	ホスト予定ピース数 <BR>
 *  	ケースITF <BR>
 *  	ボールITF <BR>
 *  </DIR>
 * </DIR>
 * リストセルの列番号一覧<BR>
 *   <DIR>
 * 		1.取消ボタン <BR>
 * 		2.修正ボタン <BR>
 * 		3.荷主コード <BR>
 * 		4.入庫予定日 <BR>
 * 		5.商品コード <BR>
 * 		6.区分 <BR>
 * 		7.入庫棚 <BR>
 * 		8.ケース入数 <BR>
 * 		9.ホスト予定ケース数 <BR>
 * 		10.ケースITF <BR>
 * 		11.荷主名称 <BR>
 * 		12.商品名称 <BR>
 * 		13.ボール入数 <BR>
 * 		14.ホスト予定ピース数 <BR>
 * 		15.ボールITF <BR>
 *   </DIR>
 * <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/14</TD><TD>T.Uehata</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/05/16</TD><TD>T.Nakajima</TD><TD>登録区分「2：入荷連携」追加による対応</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:19 $
 * @author  $Author: mori $
 */
public class MasterStoragePlanRegistBusiness extends MasterStoragePlanRegist implements WMSConstants
{
	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR =
		"/master/listbox/listmasterconsignor/ListMasterConsignor.do";
	// 入庫予定日検索ポップアップアドレス
	private static final String DO_SRCH_STORAGEPLANDATE =
		"/storage/listbox/liststorageplandate/ListStoragePlanDate.do";
	// 商品一覧検索ポップアップアドレス
	private static final String DO_SRCH_ITEM =
		"/master/listbox/listmasteritem/ListMasterItem.do";
	// 入庫予定検索ポップアップアドレス
	private static final String DO_SRCH_STORAGEPLAN =
		"/storage/listbox/liststorageplanregist/ListStoragePlanRegist.do";
	// 入庫棚検索ポップアップアドレス
	protected static final String DO_SRCH_STRGLCT =
		"/storage/listbox/liststoragelocation/ListStorageLocation.do";
	// 検索ポップアップ呼び出し中画面アドレス
	private static final String DO_SRCH_PROCESS = "/progress.do";

	// 作業者コード
	private static final String WORKERCODE = "WORKERCODE";
	// パスワード
	private static final String PASSWORD = "PASSWORD";
	// メッセージ
	private static final String MESSAGE = "MESSAGE";
	// ためうち行No
	private static final String LINENO = "LINENO";

	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// private method --------------------------------------------------
	/**
	 * 入力エリアを初期状態にします。 <BR>
	 * <BR>
	 * 概要:入力エリアを空にします。 <BR>
	 * <DIR>
	 *      workerclearFlgがtrueの場合は作業者コード/パスワードをクリアします。<BR>
	 *      workerclearFlgがfalseの場合は作業者コード/パスワードはそのままにします。
	 * </DIR>
	 * <BR>
	 *  
	 * @throws Exception 全ての例外を報告します。
	 */
	private void inputDataClear(boolean workerclearFlg) throws Exception
	{
		// 作業者コードにカーソルをセットします
		setFocus(txt_WorkerCode);

		if (workerclearFlg)
		{
			// 作業者コード
			txt_WorkerCode.setText("");
			// パスワード
			txt_Password.setText("");
		}

		Connection conn = null;

		try
		{
			// 荷主コード、荷主名称
			setConsignor();
			// 入庫予定日
			txt_StoragePlanDate.setText("");
			// 商品コード
			txt_ItemCode.setText("");
			// 商品名称
			txt_ItemName.setText("");
			// ケース/ピース区分(ピース)
			rdo_CpfCase.setChecked(false);
			// ケース/ピース区分(指定なし)
			rdo_CpfAppointOff.setChecked(false);
			// ケース/ピース区分(ケース)
			rdo_CpfCase.setChecked(true);
			// 入庫棚
			txt_StorageLocation.setText("");
			// ケース入数
			txt_CaseEntering.setText("");
			// ホスト予定ケース数
			txt_HostCasePlanQty.setText("");
			// ケースITF
			txt_CaseItf.setText("");
			// ボール入数
			txt_BundleEntering.setText("");
			// ホスト予定ピース数
			txt_HostPiesePlanQty.setText("");
			// ボールITF
			txt_BundleItf.setText("");
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterStoragePlanRegistSCH(conn);

			MasterStorageSupportParameter param = (MasterStorageSupportParameter) schedule.initFind(conn, null);
			
			// 荷主コードが1件の場合、初期表示させる。
			if (param != null)
			{
				if (StringUtil.isBlank(txt_ConsignorCode.getText()))
				{
					txt_ConsignorCode.setText(param.getConsignorCode());
					txt_ConsignorName.setText(param.getConsignorName());
				}
				if (param.getMergeType() == MasterStorageSupportParameter.MERGE_TYPE_OVERWRITE)
				{
					txt_ConsignorName.setReadOnly(true);
					txt_ItemName.setReadOnly(true);
					txt_CaseEntering.setReadOnly(true);
					txt_BundleEntering.setReadOnly(true);
					txt_CaseItf.setReadOnly(true);
					txt_BundleItf.setReadOnly(true);
				}
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
	 * ためうちエリアに値をセットするメソッドです。<BR>
	 * <BR>
	 * 概要:ためうちエリアに値をセットします。<BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setListRow(MasterStorageSupportParameter mergeParam) throws Exception
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

		//カレント行にTOOL TIPをセットする
		lst_SStoragePlanRegist.setToolTip(
			lst_SStoragePlanRegist.getCurrentRow(),
			toolTip.getText());

		// 荷主コード
		lst_SStoragePlanRegist.setValue(3, txt_ConsignorCode.getText());
		// 入庫予定
		lst_SStoragePlanRegist.setValue(4, txt_StoragePlanDate.getText());
		// 商品コード
		lst_SStoragePlanRegist.setValue(5, txt_ItemCode.getText());
		// 区分
		lst_SStoragePlanRegist.setValue(6, getCpfName());

		// 入庫棚
		lst_SStoragePlanRegist.setValue(7, txt_StorageLocation.getText());

		// ケース入数
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_SStoragePlanRegist.setValue(8, "0");
		}
		else
		{
			lst_SStoragePlanRegist.setValue(
				8,
				WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		// ホスト予定ケース数
		if (StringUtil.isBlank(txt_HostCasePlanQty.getText()))
		{
			lst_SStoragePlanRegist.setValue(9, "0");
		}
		else
		{
			lst_SStoragePlanRegist.setValue(
				9,
				WmsFormatter.getNumFormat(txt_HostCasePlanQty.getInt()));
		}
		// ケースITF
		lst_SStoragePlanRegist.setValue(10, txt_CaseItf.getText());
		// 荷主名称
		lst_SStoragePlanRegist.setValue(11, txt_ConsignorName.getText());
		// 商品名称
		lst_SStoragePlanRegist.setValue(12, txt_ItemName.getText());
		// ボール入数
		if (StringUtil.isBlank(txt_BundleEntering.getText()))
		{
			lst_SStoragePlanRegist.setValue(13, "0");
		}
		else
		{
			lst_SStoragePlanRegist.setValue(
				13,
				WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		}
		// ホスト予定ピース数
		if (StringUtil.isBlank(txt_HostPiesePlanQty.getText()))
		{
			lst_SStoragePlanRegist.setValue(14, "0");
		}
		else
		{
			lst_SStoragePlanRegist.setValue(
				14,
				WmsFormatter.getNumFormat(txt_HostPiesePlanQty.getInt()));
		}
		// ボールITF
		lst_SStoragePlanRegist.setValue(15, txt_BundleItf.getText());

		List hiddenList = new Vector();
		// 荷主コード最終使用日
		hiddenList.add(0, WmsFormatter.getTimeStampString(mergeParam.getConsignorLastUpdateDate()));
		// 商品コード最終使用日
		hiddenList.add(1, WmsFormatter.getTimeStampString(mergeParam.getItemLastUpdateDate()));
		lst_SStoragePlanRegist.setValue(0, CollectionUtils.getConnectedString(hiddenList));
		
		// 修正状態をデフォルトに戻します
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
	private MasterStorageSupportParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		Locale locale = this.httpRequest.getLocale();

		for (int i = 1; i < lst_SStoragePlanRegist.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}

			// 行指定
			lst_SStoragePlanRegist.setCurrentRow(i);

			// スケジュールパラメータへセット
			MasterStorageSupportParameter param = new MasterStorageSupportParameter();
			// 入力エリア情報
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(lst_SStoragePlanRegist.getValue(3));
			// 入庫予定日
			param.setStoragePlanDate(
				WmsFormatter.toParamDate(lst_SStoragePlanRegist.getValue(4), locale));
			// 商品コード
			param.setItemCode(lst_SStoragePlanRegist.getValue(5));
			// ケース／ピース区分
			param.setCasePieceflg(getCpfCdToListCell());
			// 入庫棚
			param.setStorageLocation(lst_SStoragePlanRegist.getValue(7));
			// ケース入数
			param.setEnteringQty(Formatter.getInt(lst_SStoragePlanRegist.getValue(8)));
			// ホスト予定ケース数
			param.setPlanCaseQty(Formatter.getInt(lst_SStoragePlanRegist.getValue(9)));
			// ケースITF
			param.setITF(lst_SStoragePlanRegist.getValue(10));
			// 荷主名称
			param.setConsignorName(lst_SStoragePlanRegist.getValue(11));
			// 商品名称
			param.setItemName(lst_SStoragePlanRegist.getValue(12));
			// ボール入数
			param.setBundleEnteringQty(Formatter.getInt(lst_SStoragePlanRegist.getValue(13)));
			// ホスト予定ピース数
			param.setPlanPieceQty(Formatter.getInt(lst_SStoragePlanRegist.getValue(14)));
			// ボールITF
			param.setBundleITF(lst_SStoragePlanRegist.getValue(15));

			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());

			// 行No.を保持しておく
			param.setRowNo(i);

			// 荷主コード最終使用日
			param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(0, lst_SStoragePlanRegist.getValue(0))));
			// 商品コード最終使用日
			param.setItemLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, lst_SStoragePlanRegist.getValue(0))));
			
			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			MasterStorageSupportParameter[] listparam = new MasterStorageSupportParameter[vecParam.size()];
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
	 * ｹｰｽ/ﾋﾟｰｽ区分から対応する名称を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCpfName() throws Exception
	{
		// 区分
		if (rdo_CpfAppointOff.getChecked())
		{
			// 指定なし
			return DisplayText.getText("LBL-W0374");
		}
		else if (rdo_CpfCase.getChecked())
		{
			// ケース
			return DisplayText.getText("LBL-W0375");
		}
		else if (rdo_CpfPiece.getChecked())
		{
			// ピース
			return DisplayText.getText("LBL-W0376");
		}

		return "";
	}
	/**
	 * リストセル.区分から対応するｹｰｽ/ﾋﾟｰｽ区分を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCpfCdToListCell() throws Exception
	{
		// 区分
		if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0374")))
		{
			// 指定なし
			return StorageSupportParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0375")))
		{
			// ケース
			return StorageSupportParameter.CASEPIECE_FLAG_CASE;
		}
		else if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0376")))
		{
			// ピース
			return StorageSupportParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}
	/**
	 * 入力エリア.ケースピース区分ラジオﾎﾞﾀﾝから対応するｹｰｽ/ﾋﾟｰｽ区分を取得します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private String getCpfCdToInputArea() throws Exception
	{
		// 区分
		if (rdo_CpfAppointOff.getChecked())
		{
			// 指定なし
			return StorageSupportParameter.CASEPIECE_FLAG_NOTHING;
		}
		else if (rdo_CpfCase.getChecked())
		{
			// ケース
			return StorageSupportParameter.CASEPIECE_FLAG_CASE;
		}
		else if (rdo_CpfPiece.getChecked())
		{
			// ピース
			return StorageSupportParameter.CASEPIECE_FLAG_PIECE;
		}

		return "";
	}
	/**
	 * リストセル.区分の内容を入力エリアのｹｰｽ/ﾋﾟｰｽ区分へ表示します。<BR>
	 * <BR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private void setCpfCheck() throws Exception
	{
		if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0374")))
		{
			// 指定なしにチェック
			rdo_CpfAppointOff.setChecked(true);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(false);
		}
		else if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0375")))
		{
			// ケースにチェック
			rdo_CpfAppointOff.setChecked(false);
			rdo_CpfCase.setChecked(true);
			rdo_CpfPiece.setChecked(false);
		}
		else if (lst_SStoragePlanRegist.getValue(6).equals(DisplayText.getText("LBL-W0376")))
		{
			// ピースにチェック
			rdo_CpfAppointOff.setChecked(false);
			rdo_CpfCase.setChecked(false);
			rdo_CpfPiece.setChecked(true);
		}
	}
	
	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 作業者コード [なし] <BR>
	 * パスワード [なし] <BR>
	 * 荷主コード [なし] <BR>
	 * 開始入庫予定日 [なし] <BR>
	 * 終了入庫予定日 [なし] <BR>
	 * 表示集約 [「入庫予定日・商品コード集約」選択] <BR>
	 * リスト発行区分 [チェック状態] <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 */
	public void page_Load(ActionEvent e) throws Exception
	{

		// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセットします
		// (デフォルト:-1)
		this.getViewState().setInt(LINENO, -1);

		// 入力エリアの初期化します
		inputDataClear(true);
		
		// ためうちエリアを初期化します
		btn_Submit.setEnabled(false);
		btn_ListClear.setEnabled(false);
		lst_SStoragePlanRegist.clearRow();
		
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
		// 登録ボタン押下時確認ダイアログ "登録しますか？"
		btn_Submit.setBeforeConfirm("MSG-W0009");

		// 一覧クリアボタン押下時確認ダイアログ "一覧入力情報がクリアされます。宜しいですか？"
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<BR><CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。<BR>
	 * <BR>
	 * 概要:検索画面の返却データを取得しセットします<BR>
	 * <BR><DIR>
	 *      1.ポップアップの検索画面から返される値を取得します。<BR>
	 *      2.値が空でないときに画面にセットします。<BR>
	 * <BR></DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListStorageConsignorBusiness.CONSIGNORCODE_KEY);
		// 荷主名称
		String consignorname = param.getParameter(ListStorageConsignorBusiness.CONSIGNORNAME_KEY);
		// 入庫予定日
		String storageplandate =
			param.getParameter(ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY);
		// 商品コード
		String itemcode = param.getParameter(ListStorageItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListStorageItemBusiness.ITEMNAME_KEY);
		// 入庫棚
		String strglct = param.getParameter(ListStorageLocationBusiness.LOCATION_KEY);
		// ケース入数
		String entering = param.getParameter(ListStoragePlanRegistBusiness.CASEETR_KEY);
		// ボール入数
		String bundleentering = param.getParameter(ListStoragePlanRegistBusiness.BUNDLEETR_KEY);
		// ケースITF
		String itf = param.getParameter(ListStoragePlanRegistBusiness.CASEITF_KEY);
		// ボールITF
		String bundleitf = param.getParameter(ListStoragePlanRegistBusiness.BUNDLEITF_KEY);
		// ケース／ピースフラグ
		String casepieceflag = param.getParameter(ListStoragePlanRegistBusiness.CASEPIECEFLAG_KEY);

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
		// 入庫予定日
		if (!StringUtil.isBlank(storageplandate))
		{
			txt_StoragePlanDate.setText(storageplandate);
		}
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
		// ケース／ピースフラグ
		if (!StringUtil.isBlank(casepieceflag))
		{
			if (casepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
			{
				rdo_CpfCase.setChecked(true);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (casepieceflag.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(true);
				rdo_CpfAppointOff.setChecked(false);
			}
			else
			{
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(true);
			}
		}
		// 入庫棚
		if (!StringUtil.isBlank(strglct))
		{
			txt_StorageLocation.setText(strglct);
		}
		// ケース入数
		if (!StringUtil.isBlank(entering))
		{
			txt_CaseEntering.setText(entering);
		}
		// ボール入数
		if (!StringUtil.isBlank(bundleentering))
		{
			txt_BundleEntering.setText(bundleentering);
		}
		// ケースITF
		if (!StringUtil.isBlank(itf))
		{
			txt_CaseItf.setText(itf);
		}
		// ボールITF
		if (!StringUtil.isBlank(bundleitf))
		{
			txt_BundleItf.setText(bundleitf);
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。<BR>
	 * 異常があった場合は、メッセージをセットし、falseを返します。<BR>
	 * <BR>
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
		if (!checker.checkContainNgText(txt_StorageLocation))
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
	 * 初期表示時、スケジュールから荷主を取得するためのメソッドです。 <BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主を入力エリアにセットします。 <BR>
	 * 
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setConsignor() throws Exception
	{
		Connection conn = null;
		try
		{
			txt_ConsignorCode.setText("");
			txt_ConsignorName.setText("");
			
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			StorageSupportParameter param = new StorageSupportParameter();

			// スケジュールから荷主コードを取得します
			WmsScheduler schedule = new MasterStoragePlanRegistSCH(conn);
			param = (StorageSupportParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
			}
			else
			{
				txt_ConsignorCode.setText("");
				txt_ConsignorName.setText("");
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			// コネクションのクローズを行う
			if (conn != null)
			{
				conn.rollback();
				conn.close();
			}
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
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で荷主一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PsearchCnsgnr_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		
		// 処理中画面->結果画面
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 入庫予定日の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入庫予定日一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       入庫予定日 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PsearchStrgPlanDate_Click(ActionEvent e) throws Exception
	{
		// 入庫予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 入庫予定日
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		
		// 検索フラグ(作業)
		param.setParameter(ListStoragePlanDateBusiness.SEARCHSTORAGEPLANDATE_KEY, 
			StorageSupportParameter.SEARCH_STORAGE_WORK);
		
		// 処理中画面->結果画面
		redirect(DO_SRCH_STORAGEPLANDATE, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PStrgPlanSrch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 入庫予定検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入庫予定検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力 <BR>
	 *    <DIR>
	 *       荷主コード* <BR>
	 *       入庫予定日 <BR>
	 *       商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PStrgPlanSrch_Click(ActionEvent e) throws Exception
	{
		// 入庫予定検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 入庫予定日
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		// 商品コード
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		// 処理中画面->結果画面
		redirect(DO_SRCH_STORAGEPLAN, param, DO_SRCH_PROCESS);
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
	public void btn_PsearchItemCd_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		// 処理中画面->結果画面
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 入庫棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で入庫棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       入庫予定日 <BR>
	 *       商品コード <BR>
	 *       ケース/ピース区分 <BR>
	 *       入庫棚 <BR>
	 *       作業状態 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchStrgLocation_Click(ActionEvent e) throws Exception
	{
		// 入庫棚検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStorageConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 入庫予定日
		param.setParameter(
			ListStoragePlanDateBusiness.STORAGEPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
		// 商品コード
		param.setParameter(ListStorageItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 入庫棚
		param.setParameter(ListStorageLocationBusiness.LOCATION_KEY, txt_StorageLocation.getText());
		// ケースピース区分
		param.setParameter(ListStorageLocationBusiness.CASEPIECE_FLAG_KEY, getCpfCdToInputArea());
		// 検索フラグ(作業)
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_STORAGE_LOCATION_KEY,
			StorageSupportParameter.SEARCH_STORAGE_WORK);
		// 検索用のケース/ピース区分
		param.setParameter(
			ListStorageLocationBusiness.SEARCH_CASEPIECE_FLAG_KEY,
			StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
		// 処理中画面->結果画面
		redirect(DO_SRCH_STRGLCT, param, DO_SRCH_PROCESS);
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
	 * 		作業者コード* <BR>
	 * 		パスワード* <BR>
	 * 		荷主コード* <BR>
	 * 		荷主名称 <BR>
	 * 		商品コード* <BR>
	 * 		商品名称 <BR>
	 * 		入庫予定日* <BR>
	 * 		商品コード* <BR>
	 * 		商品名称 <BR>
	 * 		ケース／ピース区分* <BR>
	 * 		入庫棚 <BR>
	 * 		ケース入数 <BR>
	 * 		ホスト予定ケース数*1 <BR>
	 * 		ケースＩＴＦ <BR>
	 * 		ボール入数 <BR>
	 * 		ホスト予定ピース数*1 <BR>
	 * 		ボールＩＴＦ <BR>
	 *		<BR>
	 * 		*1 いずれか選択必須入力 <BR>
	 *		<BR>
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
		// 必須入力チェック
		// 作業者コード
		txt_WorkerCode.validate();
		// パスワード
		txt_Password.validate();
		// 荷主コード
		txt_ConsignorCode.validate();
		// 入庫予定日
		txt_StoragePlanDate.validate();
		// 商品コード
		txt_ItemCode.validate();

		// パターンマッチング
		// 荷主名称
		txt_ConsignorName.validate(false);
		// 商品名称
		txt_ItemName.validate(false);
		// 入庫棚
		txt_StorageLocation.validate(false);
		// ケース入数
		txt_CaseEntering.validate(false);
		// ボール入数
		txt_BundleEntering.validate(false);
		// ホスト予定ケース数
		txt_HostCasePlanQty.validate(false);
		// ホスト予定ピース数
		txt_HostPiesePlanQty.validate(false);
		// ケースITF
		txt_CaseItf.validate(false);
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
			MasterStorageSupportParameter mergeParam = new MasterStorageSupportParameter();
			mergeParam.setConsignorCode(txt_ConsignorCode.getText());
			mergeParam.setConsignorName(txt_ConsignorName.getText());
			mergeParam.setItemCode(txt_ItemCode.getText());
			mergeParam.setItemName(txt_ItemName.getText());
			mergeParam.setEnteringQty(txt_CaseEntering.getInt());
			mergeParam.setBundleEnteringQty(txt_BundleEntering.getInt());
			mergeParam.setITF(txt_CaseItf.getText());
			mergeParam.setBundleITF(txt_BundleItf.getText());
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new MasterStoragePlanRegistSCH(conn);
			
			mergeParam = (MasterStorageSupportParameter) schedule.query(conn, mergeParam)[0];
			// 入力エリアに補完結果を表示
			txt_ConsignorName.setText(mergeParam.getConsignorName());
			txt_ItemName.setText(mergeParam.getItemName());
			txt_CaseEntering.setInt(mergeParam.getEnteringQty());
			txt_BundleEntering.setInt(mergeParam.getBundleEnteringQty());
			txt_CaseItf.setText(mergeParam.getITF());
			txt_BundleItf.setText(mergeParam.getBundleITF());
			
			// スケジュールパラメータへセット
			// 入力エリア
			MasterStorageSupportParameter param = new MasterStorageSupportParameter();

			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 荷主名称
			param.setConsignorName(txt_ConsignorName.getText());
			// 入庫予定日
			param.setStoragePlanDate(WmsFormatter.toParamDate(txt_StoragePlanDate.getDate()));
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());

			// ケースピース区分
			param.setCasePieceflg(getCpfCdToInputArea());
			// 入庫棚
			param.setStorageLocation(txt_StorageLocation.getText());
			// ケース入庫棚
			param.setCaseLocation(txt_StorageLocation.getText());
			//ピース入庫棚
			param.setPieceLocation(txt_StorageLocation.getText());
			// ケース入数 
			param.setEnteringQty(Formatter.getInt(txt_CaseEntering.getText()));
			// ボール入数 
			param.setBundleEnteringQty(Formatter.getInt(txt_BundleEntering.getText()));
			// ホスト予定ケース数 
			param.setPlanCaseQty(Formatter.getInt(txt_HostCasePlanQty.getText()));
			// ホスト予定ピース数 
			param.setPlanPieceQty(Formatter.getInt(txt_HostPiesePlanQty.getText()));
			// ケースITF 
			param.setITF(txt_CaseItf.getText());
			// ボールITF 
			param.setBundleITF(txt_BundleItf.getText());

			// スケジュールパラメータへわたすためうちエリアの情報をセットします
			// ためうちエリア情報格納用
			StorageSupportParameter[] listparam = null;

			if (lst_SStoragePlanRegist.getMaxRows() == 1)
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
				param.setConsignorLastUpdateDate(mergeParam.getConsignorLastUpdateDate());
				// 結果がtrueであればためうちエリアにデータをセットします
				if (this.getViewState().getInt(LINENO) == -1)
				{
					// 新規入力であれば、ためうちに追加します
					// ためうちエリアに行を追加します
					lst_SStoragePlanRegist.addRow();
					// 追加した行を編集対象行にします
					lst_SStoragePlanRegist.setCurrentRow(lst_SStoragePlanRegist.getMaxRows() - 1);
					// データをセットします
					setListRow(param);
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新します
					// 編集行を指定します
					lst_SStoragePlanRegist.setCurrentRow(this.getViewState().getInt(LINENO));
					// データをセットします
					setListRow(param);
					// 選択行の色を元に戻すします
					lst_SStoragePlanRegist.resetHighlight();
				}

				// ためうちエリアのボタン押下を可能にします
				// 登録ボタン
				btn_Submit.setEnabled(true);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(true);
			}

			// メッセージをセットします
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
	 *      (作業者コード/パスワードはそのままにします。)
	 *    <DIR>
	 *  	･初期値については<CODE>inputDataClear(boolean)</CODE>メソッドを参照してください。<BR>
	 *    </DIR>
	 *    2.カーソルを作業者コードにセットします。<BR>
	 *    3.ためうちエリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 入力データを初期化します
		// (作業者コードとパスワードは初期化しません)
		inputDataClear(false);
	}

	/** 
	 * 登録開始ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、入庫予定登録を行います。<BR>
	 * <BR>
	 * <DIR>
	 *	  1.カーソルを作業者コードにセットします。<BR>
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
	 * 						作業者コード* <BR>
	 * 						パスワード* <BR>
	 * 						ためうち.荷主コード* <BR>
	 * 						ためうち.荷主名称 <BR>
	 *						ためうち.入庫予定日* <BR>
	 *						ためうち.商品コード* <BR>
	 *						ためうち.商品名称 <BR>
	 *						ためうち.区分* <BR>
	 *						ためうち.入庫棚 <BR>
	 *						ためうち.ケース入数 <BR>
	 *						ためうち.ボール入数 <BR>
	 *						ためうち.ホスト予定ケース数 <BR>
	 *						ためうち.ホスト予定ピース数 <BR>
	 *						ためうち.ケースITF <BR>
	 *						ためうち.ボールITF <BR>
	 *						ためうち行No.<BR>
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
		// 必須入力チェック
		// 作業者コード
		txt_WorkerCode.validate();
		// パスワード
		txt_Password.validate();

		Connection conn = null;
		try
		{
			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);

			// 更新データ格納用
			StorageSupportParameter[] param = null;
			// ためうちエリアの全データをセットします
			param = setListParam(-1);

			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterStoragePlanRegistSCH(conn);

			// スケジュールを開始します
			if (schedule.startSCH(conn, param))
			{
				// 結果がtrueであれば、コミットします
				conn.commit();

				// ためうちエリアの一覧をクリアします
				lst_SStoragePlanRegist.clearRow();

				// 修正状態をデフォルトに戻します
				this.getViewState().setInt(LINENO, -1);

				// ボタン押下を不可にします
				// 入庫開始ボタン
				btn_Submit.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);
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
			ex.printStackTrace();
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
	 * 				3.ViewStateのためうち行No.をデフォルト(初期値:-1)に設定します。<BR>
	 *				4.カーソルを作業者コードにセットします。<BR>
	 *			</DIR>
	 *    </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// ためうち行をすべて削除します
		lst_SStoragePlanRegist.clearRow();

		// ボタン押下を不可にします
		// 登録ボタン
		btn_Submit.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);

		// 修正状態をデフォルト(-1)にします
		this.getViewState().setInt(LINENO, -1);
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
	 *    1.選択されたためうち情報を上部の入力エリアに表示します。<BR>
	 *    2.選択された行を薄黄色にします。<BR>
	 *    3.ViewStateのためうち行No.に選択された行の行番号を設定します。
	 *    4.カーソルを作業者コードにセットします。<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_SStoragePlanRegist_Click(ActionEvent e) throws Exception
	{
		// 取消ボタンクリック時
		if (lst_SStoragePlanRegist.getActiveCol() == 1)
		{
			// リスト削除
			lst_SStoragePlanRegist.removeRow(lst_SStoragePlanRegist.getActiveRow());

			// ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンを無効にします
			// ためうちにデータがなければnullをセット
			if (lst_SStoragePlanRegist.getMaxRows() == 1)
			{
				// ためうちエリアのボタン押下を不可にします
				// 登録ボタン
				btn_Submit.setEnabled(false);
				// 一覧クリアボタン
				btn_ListClear.setEnabled(false);
			}

			// 修正状態をデフォルトに戻します(ためうち行Noと背景色)
			this.getViewState().setInt(LINENO, -1);
			lst_SStoragePlanRegist.resetHighlight();

			// カーソルを作業者コードにセットします
			setFocus(txt_WorkerCode);

		}
		// 修正ボタンクリック時
		else if (lst_SStoragePlanRegist.getActiveCol() == 2)
		{
			// ボタンが押下された行を入力エリアに表示します。
			// 修正対象行を指定します
			lst_SStoragePlanRegist.setCurrentRow(lst_SStoragePlanRegist.getActiveRow());

			// 荷主コード
			txt_ConsignorCode.setText(lst_SStoragePlanRegist.getValue(3));
			// 入庫予定日
			txt_StoragePlanDate.setText(lst_SStoragePlanRegist.getValue(4));
			// 商品コード
			txt_ItemCode.setText(lst_SStoragePlanRegist.getValue(5));
			// ケース／ピース区分
			setCpfCheck();
			// 入庫棚
			txt_StorageLocation.setText(lst_SStoragePlanRegist.getValue(7));
			// ケース入数
			txt_CaseEntering.setText(lst_SStoragePlanRegist.getValue(8));
			// ホスト予定ケース数
			txt_HostCasePlanQty.setText(lst_SStoragePlanRegist.getValue(9));
			// ケースITF
			txt_CaseItf.setText(lst_SStoragePlanRegist.getValue(10));
			// 荷主名称
			txt_ConsignorName.setText(lst_SStoragePlanRegist.getValue(11));
			// 商品名称
			txt_ItemName.setText(lst_SStoragePlanRegist.getValue(12));
			// ボール入数
			txt_BundleEntering.setText(lst_SStoragePlanRegist.getValue(13));
			// ホスト予定ピース数
			txt_HostPiesePlanQty.setText(lst_SStoragePlanRegist.getValue(14));
			// ボールITF
			txt_BundleItf.setText(lst_SStoragePlanRegist.getValue(15));

			// ViewStateに保存
			// 修正状態を判断するためにViewStateにためうち行No.をセットします
			this.getViewState().setInt(LINENO, lst_SStoragePlanRegist.getActiveRow());
			// 修正行を薄黄色に変更します
			lst_SStoragePlanRegist.setHighlight(lst_SStoragePlanRegist.getActiveRow());
		}
	}
	
}
//end of class
