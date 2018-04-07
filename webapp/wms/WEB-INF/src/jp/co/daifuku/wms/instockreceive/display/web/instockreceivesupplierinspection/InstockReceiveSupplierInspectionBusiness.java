// $Id: InstockReceiveSupplierInspectionBusiness.java,v 1.2 2006/11/10 00:32:23 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.instockreceivesupplierinspection;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.authentication.DfkUserInfo;
import jp.co.daifuku.authentication.UserInfoHandler;
import jp.co.daifuku.bluedog.exception.ValidateException;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.ui.control.ListCellColumn;
import jp.co.daifuku.bluedog.util.MessageResources;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveSupplierInspectionSCH;

/**
 * Designer : T.Yoshino <BR>
 * Maker : T.Uehata<BR>
 * <BR>
 * 仕入先別入荷検品設定画面クラスです。<BR>
 * 仕入先別入荷検品設定を行うスケジュールにパラメータを引き渡します。<BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.表示ボタン押下処理(<CODE>btn_View_Click()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面から入力した内容をパラメータにセットし、そのパラメータをもとにスケジュールが表示用のデータを検索します。<BR>
 *   スケジュールからためうちエリア出力用のデータを受け取り、ためうちエリアに出力します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力<BR>
 *   <BR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード* <BR>
 *   入荷予定日* <BR>
 *   仕入先コード* <BR>
 *   商品コード <BR>
 *   クロス/DC <BR>
 *   表示順* <BR>
 *   <BR>
 *   [出力用のデータ] <BR>
 *   <BR>
 *   荷主コード <BR>
 *   荷主名称 <BR>
 *   入荷予定日 <BR>
 *   仕入先コード <BR>
 *   仕入先名称 <BR>
 *   入荷先名称 <BR>
 *   伝票No. <BR>
 *   伝票行No. <BR>
 *   商品コード <BR>
 *   商品名称 <BR>
 *   入荷総数 <BR>
 *   ケース入数 <BR>
 *   ボール入数 <BR>
 *   作業予定ケース数 <BR>
 *   作業予定ピース数 <BR>
 *   賞味期限 <BR>
 *   クロス/DC <BR>
 *   作業No. <BR>
 * </DIR>
 * <BR>
 * 2.登録ボタン押下処理(<CODE>btn_Submit_Click()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *   ためうちエリアから入力した内容をパラメータにセットし、そのパラメータをもとにスケジュールが仕入先別入荷検品設定を行います。<BR>
 *   スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *   条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *   スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 *   <BR>
 *   [パラメータ] *必須入力 +いづれか必須入力 <BR>
 *   <BR>* 必須の入力項目
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード* <BR>
 *   入荷予定日* <BR>
 *   仕入先コード* <BR>
 *   商品コード <BR>
 *   表示順* <BR>
 *   入荷残数の初期入力を行うチェック* <BR>
 *   ケース入数 <BR>
 *   作業予定ケース数 <BR>
 *   作業予定ピース数 <BR>
 *   入荷ケース数+ <BR>
 *   入荷ピース数+ <BR>
 *   分納フラグ <BR>
 *   欠品フラグ+ <BR>
 *   賞味期限 <BR>
 *   行No. <BR>
 *   TC/DC区分 <BR>
 *   作業No. <BR>
 *   最終更新日時 <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/08</TD><TD>T.Yoshino</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:32:23 $
 * @author  $Author: suresh $
 */
public class InstockReceiveSupplierInspectionBusiness extends InstockReceiveSupplierInspection implements WMSConstants
{
	// Class fields --------------------------------------------------
	// 遷移先アドレス
	// 荷主検索
	private final static String DO_SEARCH_CNSGNR = "/instockreceive/listbox/listinstockreceiveconsignor/ListInstockReceiveConsignor.do";
	// 入荷予定日検索
	private final static String DO_SEARCH_PLANDATE = "/instockreceive/listbox/listinstockreceiveplandate/ListInstockReceivePlanDate.do";
	// 仕入先検索
	private final static String DO_SEARCH_SUPPLIER = "/instockreceive/listbox/listinstockreceivesupplier/ListInstockReceiveSupplier.do";
	// 商品検索
	private final static String DO_SEARCH_ITEM = "/instockreceive/listbox/listinstockreceiveitem/ListInstockReceiveItem.do";
	// 検索中
	private final static String DO_PROCESS = "/progress.do";
	
	
	// 商品コードを保持するためのキー
	private final static String VK_ITEMCODE = "VK_ITEMCODE";

	// クロス／ＤＣ(全て)を保持するためのキー
	private final static String VK_CROSSDC_ALL = "VK_CROSSDC_ALL";

	// クロス／ＤＣ(クロス)を保持するためのキー
	private final static String VK_CROSSDC_CS = "VK_CROSSDC_CS";

	// クロス／ＤＣ(ＤＣ)を保持するためのキー
	private final static String VK_CROSSDC_DC = "VK_CROSSDC_DC";

	// 表示順(伝票No.順)を保持するためのキー
	private final static String VK_DISPTICKET = "VK_DISPTICKET";

	// 表示順(商品コード順)を保持するためのキー
	private final static String VK_DISPITEM = "VK_DISPITEM";
	
	// リストセルの列指定を行う
	// リストセル隠し列
	private final static int LST_HDN = 0;
	// 伝票No
	private final static int LST_TKTNO = 1;
	// 商品コード
	private final static int LST_ITEMCD = 2;
	// 入荷総数
	private final static int LST_INSTKTOTAL = 3;
	// ケース入数
	private final static int LST_CASEETR = 4;
	// 作業予定ケース数
	private final static int LST_PLANCASEQTY = 5;
	// 入荷ケース数
	private final static int LST_INSTKCASEQTY = 6;
	// 分納
	private final static int LST_ISTL = 7;
	// 欠品
	private final static int LST_SHORT = 8;
	// 賞味期限
	private final static int LST_USEBYDATE = 9;
	// クロス/DC
	private final static int LST_CROSSDCNM = 10;
	// 行No
	private final static int LST_TKTLINENO = 11;
	// 商品名称
	private final static int LST_ITEMNM = 12;
	// ボール入数
	private final static int LST_BUNDLEETR = 13;
	// 作業予定ピース数
	private final static int LST_PLANPIECEQTY = 14;
	// 入荷ピース数
	private final static int LST_INSTKPIECEQTY = 15;
	
	// リストセル隠しパラメータ
	// 入荷ケース数
	private static final int HDNINSTKCASEQTY = 0;
	// 入荷ピース数
	private static final int HDNINSTKPIECEQTY = 1;
	// 作業No.
	private static final int HDNJOBNO = 2;
	// 最終更新日時
	private static final int HDNLASTUPDATEDATE = 3;
	// クロス/DC区分
	private static final int HDNTCDC = 4;
	
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * <BR>
	 * <DIR>
	 * 	 1.フォーカスを作業者コードに初期セットします。<BR>
	 * 	 <BR>
	 * </DIR>
	 * * @param e ActionEvent
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
		// フォーカスを作業者コードにセットする
		setFocus(txt_WorkerCode);

		// MSG-W0009 = 登録しますか？
		btn_Submit.setBeforeConfirm("MSG-W0009");
		// MSG-W0012 = 一覧入力情報がクリアされます。宜しいですか？
		btn_ListClear.setBeforeConfirm("MSG-W0012");
	}
	
	/**
	 * 画面の初期化を行います。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。クリアボタン押下時もこの処理を行います。
	 * <BR>
	 * <DIR>
	 *   1.登録ボタン・入荷数クリアボタン・一覧クリアボタンを無効にします。<BR>
	 * </DIR>
	 * @param e ActionEvent
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 入力エリアを初期化します
		setInitView(true);
		// ためうちエリアを初期化します
		setDetailClear();
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが呼ばれます。<BR>
	 * Pageに定義されているpage_DlgBackをオーバライドします。<BR>
	 * @param e ActionEvent
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		
		// 入荷予定日
		Date plandate = WmsFormatter.toDate(param.getParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY)
												 ,this.getHttpRequest().getLocale());
		
		// 仕入先コード
		String suppliercode = param.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		
		// 商品コード
		String itemcode = param.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);

		// 空ではないときに値をセットする
		// 荷主コード
		if (!StringUtil.isBlank(consignorcode))
		{
			txt_ConsignorCode.setText(consignorcode);
		}
		// 入荷予定日
		if (!StringUtil.isBlank(plandate))
		{
			
			txt_InstockPlanDate.setDate(plandate);
		}
		// 仕入先コード
		if (!StringUtil.isBlank(suppliercode))
		{
			txt_SupplierCode.setText(suppliercode);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------
	/**
	 * 入力チェックを行います。
	 * 異常があった場合は、メッセージをセットし、falseを返します。
	 * 
	 * @param rowNo チェックを行う行No.
	 * @return true:異常なし false:異常あり
	 */
	protected boolean checkContainNgText(int rowNo)
	{
		
		WmsCheckker checker = new WmsCheckker();

		lst_InstkRcvSplIsp.setCurrentRow(rowNo);

		if(!checker.checkContainNgText(
				lst_InstkRcvSplIsp.getValue(LST_USEBYDATE) ,
				rowNo,
				lst_InstkRcvSplIsp.getListCellColumn(LST_USEBYDATE).getResourceKey() )
				)
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
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * [パラメーター] <BR>
	 * <DIR>
	 * wkrClrFlg trueの場合作業者ｺｰﾄﾞ/ﾊﾟｽﾜｰﾄﾞを初期化、falseの場合作業者ｺｰﾄﾞ/ﾊﾟｽﾜｰﾄﾞを初期化しない <BR>
	 * <DIR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 作業者ｺｰﾄﾞ [なし/クリアボタン押下時はそのまま] <BR>
	 * ﾊﾟｽﾜｰﾄﾞ [なし/クリアボタン押下時はそのまま] <BR>
	 * 荷主コード [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 入荷予定日 [なし] <BR>
	 * 仕入先コード [なし] <BR>
	 * 商品コード [なし] <BR>
	 * クロス/ＤＣ ["全て"選択] <BR>
	 * 表示順 ["伝票Ｎｏ順"選択] <BR>
	 * 入荷残数の初期入力 [チェックなし] <BR>
	 * <BR>
	 * </DIR>
	 * </DIR>
	 * </DIR>
	 * @param wkrClrFlg
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitView(boolean wkrClrFlg) throws Exception
	{
		// 作業者ｺｰﾄﾞにフォーカスセット
		setFocus(txt_WorkerCode);
		
		// 入力項目の初期化を行う
		if (wkrClrFlg)
		{
			// 作業者コード
			txt_WorkerCode.setText("");
			// パスワード
			txt_Password.setText("");
		}
		// 荷主コード
		txt_ConsignorCode.setText(getConsignor());
		// 入荷予定日
		txt_InstockPlanDate.setText("");
		// 仕入先コード
		txt_SupplierCode.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// クロス/ＤＣ
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagDC.setChecked(false);
		rdo_CrossDCFlagAll.setChecked(true);
		// 表示順
		rdo_TicItem.setChecked(false);
		rdo_TicTkt.setChecked(true);
		// 入荷残数初期表示設定チェックボックス
		chk_CommonUse.setChecked(false);
	}
	

	/** 
	 * ためうちの表示情報を全てクリアします。<BR>
	 * <BR>
	 * 概要:ためうちの表示情報を初期化します。<BR>
	 * <BR>
	 * @throws Exception 
	 */
	private void setDetailClear() throws Exception
	{
		// ためうちエリアを初期化します
		txt_RConsignorCode.setText("");
		txt_RConsignorName.setText("");
		txt_RInstockPlanDate.setText("");
		txt_RSupplierCode.setText("");
		txt_RSupplierName.setText("");
		lst_InstkRcvSplIsp.clearRow();
		// ボタンを無効化します
		btn_Submit.setEnabled(false);
		btn_InstockQtyClear.setEnabled(false);
		btn_ListClear.setEnabled(false);
	}
	/** 
	 * 値チェックを行うメソッドです。<BR>
	 * <BR>
	 * 値が0以上かをチェックします。<BR>
	 * 
	 * @param Num 	      値チェックするための値です。
	 * @param ListName   値チェックする項目名です。
	 * @throws Exception 全ての例外を報告します。
	 * @return itemName  値が1以上じゃなかった場合項目名を返します。
	 */
	private String checkNumber(int Num, ListCellColumn ListName) throws Exception
	{
		String itemName = null;

		// 0以上か
		if (!StringUtil.isBlank(Integer.toString(Num)))
		{
			if (Num < 0)
			{
				// itemNameに項目名をセット
				itemName = DisplayText.getText(ListName.getResourceKey());
				return itemName;
			}
		}
		return itemName;
	}

	/**
	 * ため打ちエリアに値をセットします。
	 * @param viewParam InstockReceiveParameter[]
	 * @throws Exception  
	 */
	private void setList(InstockReceiveParameter[] viewParam) throws Exception
	{
		// 行をすべて削除
		lst_InstkRcvSplIsp.clearRow();

		// 荷主コード(読み取り専用)
		txt_RConsignorCode.setText(viewParam[0].getConsignorCode());
		// 荷主名称(読み取り専用)
		txt_RConsignorName.setText(viewParam[0].getConsignorName());

		// 入荷予定日(読み取り専用)
		// String型(YYYYMMDD)からDate型への変換を行います。
		txt_RInstockPlanDate.setDate(WmsFormatter.toDate(viewParam[0].getPlanDate()));
		// 仕入先コード(読み取り専用)
		txt_RSupplierCode.setText(viewParam[0].getSupplierCode());
		// 仕入先名称(読み取り専用)
		txt_RSupplierName.setText(viewParam[0].getSupplierName());
		
		// 商品名称
		String label_itemname = DisplayText.getText("LBL-W0103");
		// クロス/ＤＣ区分名称
		String label_crossdc = DisplayText.getText("LBL-W0410");

		for (int i = 0; i < viewParam.length; i++)
		{
			lst_InstkRcvSplIsp.setCurrentRow(i + 1);

			// 行追加
			lst_InstkRcvSplIsp.addRow();

			//ToolTip用のデータを編集
			ToolTipHelper toolTip = new ToolTipHelper();;
			// 商品名称
			toolTip.add(label_itemname, viewParam[i].getItemName());
			// クロス/ＤＣ区分名称
			toolTip.add(label_crossdc, viewParam[i].getTcdcName());
			
			//カレント行にTOOL TIPをセットする
			lst_InstkRcvSplIsp.setToolTip(lst_InstkRcvSplIsp.getCurrentRow(), toolTip.getText());
			
			// 伝票No
			lst_InstkRcvSplIsp.setValue(LST_TKTNO, viewParam[i].getInstockTicketNo());
			// 伝票行No
			lst_InstkRcvSplIsp.setValue(LST_TKTLINENO, WmsFormatter.getNumFormat(viewParam[i].getInstockLineNo()));
			// 商品コード
			lst_InstkRcvSplIsp.setValue(LST_ITEMCD, viewParam[i].getItemCode());
			// 商品名称
			lst_InstkRcvSplIsp.setValue(LST_ITEMNM, viewParam[i].getItemName());
            // 入荷総数
			lst_InstkRcvSplIsp.setValue(LST_INSTKTOTAL, WmsFormatter.getNumFormat(viewParam[i].getTotalPlanQty()));
            // ケース入数
			lst_InstkRcvSplIsp.setValue(LST_CASEETR, WmsFormatter.getNumFormat(viewParam[i].getEnteringQty()));
			// ボール入数
			lst_InstkRcvSplIsp.setValue(LST_BUNDLEETR, WmsFormatter.getNumFormat(viewParam[i].getBundleEnteringQty()));
			// 作業予定ケース数
			lst_InstkRcvSplIsp.setValue(LST_PLANCASEQTY, WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
			// 作業予定ピース数
			lst_InstkRcvSplIsp.setValue(LST_PLANPIECEQTY, WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));
			// 入荷ケース数、入荷ケース数は初期表示を行うチェックボックスの状態によって処理を分岐します
			if(chk_CommonUse.getChecked())
			{
				// 入荷ケース数
				lst_InstkRcvSplIsp.setValue(LST_INSTKCASEQTY, WmsFormatter.getNumFormat(viewParam[i].getResultCaseQty()));
				// 入荷ピース数
				lst_InstkRcvSplIsp.setValue(LST_INSTKPIECEQTY, WmsFormatter.getNumFormat(viewParam[i].getResultPieceQty()));
			}
			else
			{
				// 入荷ケース数
				lst_InstkRcvSplIsp.setValue(LST_INSTKCASEQTY, "");
				// 入荷ピース数
				lst_InstkRcvSplIsp.setValue(LST_INSTKPIECEQTY, "");
			}
			// 分納
			lst_InstkRcvSplIsp.setChecked(LST_ISTL, false);
			// 欠品
			lst_InstkRcvSplIsp.setChecked(LST_SHORT, false);
			// 賞味期限
			lst_InstkRcvSplIsp.setValue(LST_USEBYDATE, viewParam[i].getUseByDate());
			// クロス/DC
			lst_InstkRcvSplIsp.setValue(LST_CROSSDCNM, viewParam[i].getTcdcName());

			// 隠しパラメータの設定
			List hidden = new Vector();
			if (chk_CommonUse.getChecked())
			{
				// 入荷ケース数
				hidden.add(WmsFormatter.getNumFormat(viewParam[i].getPlanCaseQty()));
				// 入荷ピース数
				hidden.add(WmsFormatter.getNumFormat(viewParam[i].getPlanPieceQty()));	
			}
			else
			{
				// 入荷ケース数
				hidden.add("");
				// 入荷ピース数
				hidden.add("");
			}
			// 作業No
			hidden.add(viewParam[i].getJobNo());
			// 最終更新日時
			hidden.add(WmsFormatter.getTimeStampString(viewParam[i].getLastUpdateDate()));
			// TC/DC区分
			hidden.add(viewParam[i].getTcdcFlag());
			// セットします
			lst_InstkRcvSplIsp.setValue(LST_HDN, CollectionUtils.getConnectedString(hidden));
		}

		// ためうちエリアのボタンを有効化する
		btn_Submit.setEnabled(true);
		btn_InstockQtyClear.setEnabled(true);
		btn_ListClear.setEnabled(true);
			
	}

	
	/**
	 * 初期表示時、スケジュールから荷主を取得するためのメソッドです。 <BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主を入力エリアにセットします。 <BR>
	 * 
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private String getConsignor() throws Exception
	{
		Connection conn = null;
		try
		{
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			InstockReceiveParameter param = new InstockReceiveParameter();

			// スケジュールから荷主コードを取得します
			WmsScheduler schedule = new InstockReceiveSupplierInspectionSCH();
			param = (InstockReceiveParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				return param.getConsignorCode();
			}
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
					 conn.close();
				 }
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this));
			}
		}
		return "";
	}
	

	/** 
	 * リストセルが空文字の場合、0を返却します。<BR>
	 * <BR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	private int getListCellNum(String num) throws Exception
	{
		if(num == null || num.equals(""))
		{
			return 0;
		}
		else
		{
			return WmsFormatter.getInt(num);
		}
	}
	

	/** 
	 * リストセルの入力項目が変更されているかどうかチェックします。<BR>
	 * <BR>
	 * 概要：リストセルの入力エリアが修正されている場合はTrueを、変更されていない場合はfalseを返却します。 <BR>
	 * <BR> 
	 * @throws Exception 
	 */
	private boolean isChangeLine() throws Exception
	{	
		// 分納、欠品チェックボックスがチェックなし、
		// かつ入荷ケース数、入荷ピース数が空の場合：false
		if (!lst_InstkRcvSplIsp.getChecked(LST_ISTL) 
			&& !lst_InstkRcvSplIsp.getChecked(LST_SHORT)
			&& lst_InstkRcvSplIsp.getValue(LST_INSTKCASEQTY).equals("") 
			&& lst_InstkRcvSplIsp.getValue(LST_INSTKPIECEQTY).equals(""))
		{
			return false;
		}
		return true;
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
	public void btn_PSearchConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		
		// 検索フラグ(予定データ)
		param.setParameter(ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);
		
		// 作業状態(出荷予定情報の未開始、一部完了)
		String[] search = new String[2];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListInstockReceiveConsignorBusiness.WORKSTATUSCONSIGNOR_KEY, search);
		
		// TCDC区分
		param.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 処理中画面->結果画面
		redirect(DO_SEARCH_CNSGNR, param, DO_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstockPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_InstockPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchInstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 入荷予定日の検索ボタンが押下されたときに呼ばれます。
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchInstockPlanDate_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());

		// 入荷予定
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
		
		// 作業状態(出荷予定情報の未開始、一部完了)
		String[] search = new String[2];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListInstockReceivePlanDateBusiness.WORKSTATUSINSTOCKPLANDATE_KEY, search);
		
		// TCDC区分
		param.setParameter(
			ListInstockReceivePlanDateBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 処理中画面->結果画面
		redirect(DO_SEARCH_PLANDATE, param, DO_PROCESS);
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
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchSupplierCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));

		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());
		
		// 検索フラグ(予定データ)
		param.setParameter(ListInstockReceiveSupplierBusiness.SEARCHSUPPLIER_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);
		
		// 作業状態(出荷予定情報の未開始、一部完了)
		String[] search = new String[2];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListInstockReceiveSupplierBusiness.WORKSTATUSSUPPLIER_KEY, search);
		
		// TCDC区分
		param.setParameter(
			ListInstockReceiveSupplierBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 処理中画面->結果画面
		redirect(DO_SEARCH_SUPPLIER, param, DO_PROCESS);
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
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		
		// 入荷予定日
		param.setParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY, WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));

		// 仕入先コード
		param.setParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY, txt_SupplierCode.getText());

		// 商品コード
		param.setParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		
		// 検索フラグ(予定データ)
		param.setParameter(ListInstockReceiveItemBusiness.SEARCHITEM_KEY, InstockReceiveParameter.SEARCHFLAG_PLAN);
		
		// 作業状態(出荷予定情報の未開始、一部完了)
		String[] search = new String[2];
		search[0] = new String(InstockReceiveParameter.STATUS_FLAG_UNSTARTED);
		search[1] = new String(InstockReceiveParameter.STATUS_FLAG_PARTIAL_COMPLETION);
		param.setParameter(ListInstockReceiveItemBusiness.WORKSTATUSITEM_KEY, search);
		
		// TCDC区分
		param.setParameter(
			ListInstockReceiveConsignorBusiness.TCDCFLAG_KEY,
			InstockReceiveParameter.TCDC_FLAG_CROSS_AND_DC);
		
		// 処理中画面->結果画面
		redirect(DO_SEARCH_ITEM, param, DO_PROCESS);
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
	public void rdo_CrossDCFlagAll_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagAll_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagCross_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagCross_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagDC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagDC_Click(ActionEvent e) throws Exception
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
	public void lbl_InstkRestFirstInp_Server(ActionEvent e) throws Exception
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
	public void btn_View_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 表示ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:入力エリアの入力項目を条件に、入荷予定情報を検索し、ためうちに表示します。<BR>
	 * <BR>
	 * <DIR>
	 *   1.入力エリアの入力項目のチェックを行います。(必須入力、文字数、文字属性)<BR>
	 *   2.スケジュールを開始します。<BR>
	 *   3.ためうちエリアに表示を行います。<BR>
	 *   <BR>
	 *     <DIR>
	 *     ･入荷数の初期入力を行うにチェックあり:作業者入力領域に予定数をセットします。<BR>
	 *     <BR>
	 *     ･入荷数の初期入力を行うにチェックなし:作業者入力領域にNULLをセットします。<BR>
	 *     </DIR>
	 *   <BR>
	 *   4.登録ボタン・入荷数クリアボタン・一覧クリアボタンを有効にします。<BR>
	 *   5.入力エリアの内容は保持します。<BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		Connection conn = null;
		
		try
		{
			// 入力エラーの場合、ためうちエリアをクリアする。
			btn_ListClear_Click(e);

			// 入力チェック
			txt_WorkerCode.validate();
			txt_Password.validate();
			txt_ConsignorCode.validate();
			txt_InstockPlanDate.validate();
			txt_SupplierCode.validate();
			// パターンマッチング文字
			txt_ItemCode.validate(false);

			// スケジュールパラメータへセット
			InstockReceiveParameter param = new InstockReceiveParameter();
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 入荷予定日
			// Date型からString型(YYYYMMDD)への変換を行います。
			param.setPlanDate(WmsFormatter.toParamDate(txt_InstockPlanDate.getDate()));
			// 仕入先コード
			param.setSupplierCode(txt_SupplierCode.getText());
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// クロス/DC
			// 全て
			if (rdo_CrossDCFlagAll.getChecked())
			{
				param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_ALL);
				rdo_CrossDCFlagAll.setChecked(true);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagDC.setChecked(false);
			}
			// クロス
			else if (rdo_CrossDCFlagCross.getChecked())
			{
				param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
				rdo_CrossDCFlagAll.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(true);
				rdo_CrossDCFlagDC.setChecked(false);
			}
			// DC
			else if (rdo_CrossDCFlagDC.getChecked())
			{
				param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
				rdo_CrossDCFlagAll.setChecked(false);
				rdo_CrossDCFlagCross.setChecked(false);
				rdo_CrossDCFlagDC.setChecked(true);
			}
			// 表示順
			// 伝票No.順
			if (rdo_TicTkt.getChecked())
			{
				param.setDspOrder(InstockReceiveParameter.DISPLAY_ORDER_TICKET);
				rdo_TicTkt.setChecked(true);
				rdo_TicItem.setChecked(false);
			}
			// 商品コード順
			else if (rdo_TicItem.getChecked())
			{
				param.setDspOrder(InstockReceiveParameter.DISPLAY_ORDER_ITEM);
				rdo_TicTkt.setChecked(false);
				rdo_TicItem.setChecked(true);
			}
			// 入荷残数を表示するチェックボックス
			param.setRemnantDisplayFlag(chk_CommonUse.getChecked());

			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new InstockReceiveSupplierInspectionSCH();
			InstockReceiveParameter[] viewParam = (InstockReceiveParameter[]) schedule.query(conn, param);

			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			// リストセルのセット
			setList(viewParam);

			// ViewStateへ保持する
			// 商品コード
			this.getViewState().setString(VK_ITEMCODE, txt_ItemCode.getText());
			
			// クロス/DC
			this.getViewState().setBoolean(VK_CROSSDC_ALL, rdo_CrossDCFlagAll.getChecked());
			this.getViewState().setBoolean(VK_CROSSDC_CS, rdo_CrossDCFlagCross.getChecked());
			this.getViewState().setBoolean(VK_CROSSDC_DC, rdo_CrossDCFlagDC.getChecked());
			
			// 表示順
			this.getViewState().setBoolean(VK_DISPTICKET, rdo_TicTkt.getChecked());
			this.getViewState().setBoolean(VK_DISPITEM, rdo_TicItem.getChecked());
			
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
	 * 概要:入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *   1.画面の初期化を行います。<BR>
	 *     初期値については<CODE>page_Load(ActionEvent e)</CODE>メソッドを参照してください。<BR>
	 * </DIR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 入力エリアをクリアします。
		setInitView(false);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Submit_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 登録ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、仕入先別入荷検品を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.入力領域チェックを行います。<BR>
	 *     <DIR>
	 *     <BR>
	 *     ･数量入力領域に入力がない(null)場合:"更新対象データがありません。"のメッセージを表示します。<BR>
	 *     <BR>
	 *     ･入力がある場合:登録確認ダイアログボックスを表示します。"入荷確定しますか?"<BR>
	 *     <BR>
	 *     [確認ダイアログ キャンセル]<BR>
	 *     <BR>
	 *       <DIR>
	 *       なにも行いません。<BR>
	 *       </DIR>
	 *     <BR>
	 *     [確認ダイアログ OK]<BR>
	 *     <BR>
	 *       <DIR>
	 *       1.ためうちエリアの入力項目のチェックを行います。<BR>
	 *       2.スケジュールを開始します。<BR>
	 *       3.ためうちエリアに、更新後の情報を再取込し、表示します。<BR>
	 *       </DIR>
	 *     </DIR>
	 *   <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent
	 * @throws Exception 
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		// 入力チェック
		// 必須入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();

		// リストセルのタイトルを取得し、ListCellColumnに格納
		ArrayList lst = (ArrayList) lst_InstkRcvSplIsp.getListCellColumns();
		ListCellColumn[] List_Title = new ListCellColumn[lst.size() + 1];
		for (int l = 0; l < lst.size(); l++)
		{
			ListCellColumn listtemp = (ListCellColumn) lst.get(l);
			List_Title[l + 1] = new ListCellColumn();
			List_Title[l + 1] = (ListCellColumn) listtemp;
		}

		// 最大行数を取得
		int index = lst_InstkRcvSplIsp.getMaxRows();
				
		// リストセルの入力チェックを行います
		// 数値項目の０以下チェックと賞味期限の妥当性チェックのみ実施。表示時との比較が必要な場合は追加する。
		for (int i = 1; i < index; i++)
		{
			try
			{
				// 行指定
				lst_InstkRcvSplIsp.setCurrentRow(i);
				// 賞味期限の必須入力以外の妥当性チェックを行う
				lst_InstkRcvSplIsp.validate(LST_USEBYDATE, false);
				// eWareNavi用入力文字チェック
				if (!checkContainNgText(i))
				{
					return;
				}
				// 入荷ｹｰｽ数、入荷ﾋﾟｰｽ数のマイナス値のチェックを行う
				String itemname = null;
				itemname = checkNumber(WmsFormatter.getInt(lst_InstkRcvSplIsp.getValue(LST_INSTKCASEQTY)), List_Title[LST_INSTKCASEQTY]);
				if (StringUtil.isBlank(itemname))
				{
					itemname = checkNumber(WmsFormatter.getInt(lst_InstkRcvSplIsp.getValue(LST_INSTKPIECEQTY)), List_Title[LST_INSTKPIECEQTY]);
				}
				if (!StringUtil.isBlank(itemname))
				{
					itemname = Integer.toString(i) + " " + itemname;
					// エラーメッセージを表示し、終了する。
					// 6023162=No.{0}には{1}以上の値を入力してください。
					message.setMsgResourceKey("6023162" + wDelim + itemname + wDelim + "0");
					return;
				}
			}
			catch(ValidateException ve)
			{
				// メッセージをセット
				String errorMessage = MessageResources.getText(ve.getErrorNo(), ve.getBinds().toArray());
				// 6023273 = No.{0}{1}
				throw new ValidateException("6023273", Integer.toString(i), errorMessage);
			}
		}

		Connection conn = null;
		try
		{
						
			Vector vecParam = new Vector(index);

			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			String termNo = userHandler.getTerminalNo();
			
			
			for (int i = 1; i < index; i++)
			{
				// 行指定
				lst_InstkRcvSplIsp.setCurrentRow(i);
				
				
				if (isChangeLine())
				{
					// スケジュールパラメータへセット
					InstockReceiveParameter param = new InstockReceiveParameter();
					// 入力エリア情報(再表示用)
					// 作業者コード
					param.setWorkerCode(txt_WorkerCode.getText());
					// パスワード
					param.setPassword(txt_Password.getText());					
					// 荷主コード
					param.setConsignorCode(txt_RConsignorCode.getText());
					// 入荷予定日
					param.setPlanDate(WmsFormatter.toParamDate(txt_RInstockPlanDate.getDate()));
					// 仕入先コード
					param.setSupplierCode(txt_RSupplierCode.getText());
					// 商品コード
					param.setItemCode(this.getViewState().getString(VK_ITEMCODE));
					// クロス/DC
					// 全て
					if (this.getViewState().getBoolean(VK_CROSSDC_ALL))
					{
						param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_ALL);
					}
					// クロス
					else if (this.getViewState().getBoolean(VK_CROSSDC_CS))
					{
						param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
					}
					// DC
					else if (this.getViewState().getBoolean(VK_CROSSDC_DC))
					{
						param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
					}
					// 表示順
					// 伝票No.順
					if (this.getViewState().getBoolean(VK_DISPTICKET))
					{
						param.setDspOrder(InstockReceiveParameter.DISPLAY_ORDER_TICKET);
					}
					// 商品コード順
					else if (this.getViewState().getBoolean(VK_DISPITEM))
					{
						param.setDspOrder(InstockReceiveParameter.DISPLAY_ORDER_TICKET);
					}
					// 入荷残数を表示するチェックボックス
					param.setRemnantDisplayFlag(chk_CommonUse.getChecked());
					// リストセル情報(更新用)
					// ケース入数
					param.setEnteringQty(WmsFormatter.getInt(lst_InstkRcvSplIsp.getValue(LST_CASEETR)));
					// 作業予定ケース数
					param.setPlanCaseQty(WmsFormatter.getInt(lst_InstkRcvSplIsp.getValue(LST_PLANCASEQTY)));
					// 作業予定ピース数
					param.setPlanPieceQty(WmsFormatter.getInt(lst_InstkRcvSplIsp.getValue(LST_PLANPIECEQTY)));
					// 入荷ケース数
					param.setResultCaseQty(getListCellNum(lst_InstkRcvSplIsp.getValue(LST_INSTKCASEQTY)));
					// 入荷ピース数
					param.setResultPieceQty(getListCellNum(lst_InstkRcvSplIsp.getValue(LST_INSTKPIECEQTY)));
					// 分納フラグ
					param.setRemnantFlag(lst_InstkRcvSplIsp.getChecked(LST_ISTL));
					// 欠品フラグ
					param.setShortageFlag(lst_InstkRcvSplIsp.getChecked(LST_SHORT));
					// 賞味期限
					param.setUseByDate(lst_InstkRcvSplIsp.getValue(LST_USEBYDATE));
					// 作業No.
					param.setJobNo(CollectionUtils.getString(HDNJOBNO, lst_InstkRcvSplIsp.getValue(LST_HDN)));
					// 最終更新日時
					// String型(YYYYMMDDHHMMSS)からDate型への変換を行います。
					Date lastupdate = WmsFormatter.getTimeStampDate(CollectionUtils.getString(HDNLASTUPDATEDATE, lst_InstkRcvSplIsp.getValue(LST_HDN)));
					param.setLastUpdateDate(lastupdate);
					// クロス/DC
					param.setTcdcFlagL(CollectionUtils.getString(HDNTCDC, lst_InstkRcvSplIsp.getValue(LST_HDN)));

					// その他の情報
					// 端末No
					param.setTerminalNumber(termNo);
					// 行No.を保持しておく
					param.setRowNo(i);
					
					// セットしたパラメータをVectorに追加
					vecParam.addElement(param);
				}
			}
			
			if (vecParam.size() <= 0)
			{
				// 6023154 = 更新対象データがありません。
				message.setMsgResourceKey("6023154");
				return;
			}

			InstockReceiveParameter[] paramArray = new InstockReceiveParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			WmsScheduler schedule = new InstockReceiveSupplierInspectionSCH();
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// スケジュールスタート
			InstockReceiveParameter[] viewParam = (InstockReceiveParameter[]) schedule.startSCHgetParams(conn, paramArray);

			if (viewParam == null)
			{
				conn.rollback();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
			}
			else if (viewParam!= null && viewParam.length >= 0)
			{
				conn.commit();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
				// 対象データがなくなった場合、ためうちエリアをクリアする。
				btn_ListClear_Click(e);

				if (viewParam.length > 0)
				{
					// リストセルのセット
					setList(viewParam);
				}
			}
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
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_InstockQtyClear_Server(ActionEvent e) throws Exception
	{
	}


	/** 
	 * 入荷数クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちの入荷ケース/ピース数、分納、欠品につけたチェックをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *   1.ためうちエリアの項目をクリアします。<BR>
	 *   2.ためうちエリア隠し項目を修正します。
	 *   3.入力エリアの内容は保持します。<BR>
	 * </DIR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_InstockQtyClear_Click(ActionEvent e) throws Exception
	{
		// リストセルのセット
		for (int i = 1; i < lst_InstkRcvSplIsp.getMaxRows(); i++)
		{
			lst_InstkRcvSplIsp.setCurrentRow(i);
			lst_InstkRcvSplIsp.setValue(LST_INSTKCASEQTY, "");
			lst_InstkRcvSplIsp.setValue(LST_INSTKPIECEQTY, "");
			lst_InstkRcvSplIsp.setChecked(LST_ISTL, false);
			lst_InstkRcvSplIsp.setChecked(LST_SHORT, false);
				
			// 隠しパラメータの設定
			// リストセルに保持したパラメータをクリアする。
			List list = new Vector();
			// 入荷ケース数
			list.add("");
			// 入荷ピース数
			list.add("");
			// 作業No
			list.add(CollectionUtils.getString(HDNJOBNO, lst_InstkRcvSplIsp.getValue(LST_HDN)));
			// 最終更新日時
			list.add(CollectionUtils.getString(HDNLASTUPDATEDATE, lst_InstkRcvSplIsp.getValue(LST_HDN)));
			// TC/DC区分
			list.add(CollectionUtils.getString(HDNTCDC, lst_InstkRcvSplIsp.getValue(LST_HDN)));
			// パラメータを再セット
			lst_InstkRcvSplIsp.setValue(LST_HDN, CollectionUtils.getConnectedString(list));
		}
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
	 * 一覧クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちの表示情報を全てクリアします。<BR>
	 * <BR>
	 *   <DIR>
	 *   1.確認ダイアログボックスを表示します。<BR>
	 *   <BR>
	 *     <DIR>
	 *     [確認ダイアログ キャンセル]<BR>
	 *     <BR>
	 *       <DIR>
	 *       なにも行いません。<BR>
	 *       </DIR>
	 *     <BR>
	 *     [確認ダイアログ OK]<BR>
	 *     <BR>
	 *       <DIR>
	 *       1.登録ボタン・入荷数クリアボタン・一覧クリアボタンを無効にします。<BR>
	 *       2.入力エリアの内容は保持します。<BR>
	 *       </DIR>
	 *     </DIR>
	 *   <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// ためうちエリアを初期化します
		setDetailClear();
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
	public void lbl_InstockPlanDateT_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RInstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RInstockPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RInstockPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_Supplier_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RSupplierCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RSupplierCode_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RSupplierCode_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RSupplierCode_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RSupplierName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RSupplierName_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RSupplierName_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_RSupplierName_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkRcvSplIsp_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkRcvSplIsp_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkRcvSplIsp_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkRcvSplIsp_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkRcvSplIsp_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkRcvSplIsp_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkRcvSplIsp_Click(ActionEvent e) throws Exception
	{
	}


}
//end of class
