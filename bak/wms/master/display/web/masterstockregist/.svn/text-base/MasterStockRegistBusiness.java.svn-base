// $Id: MasterStockRegistBusiness.java,v 1.1.1.1 2006/08/17 09:34:18 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterstockregist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
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
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.utility.DateUtil;
import jp.co.daifuku.wms.master.schedule.MasterStockRegistSCH;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockconsignor.ListStockConsignorBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockitem.ListStockItemBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststocklocation.ListStockLocationBusiness;
import jp.co.daifuku.wms.stockcontrol.display.web.listbox.liststockmaintenance.ListStockMaintenanceBusiness;
import jp.co.daifuku.wms.stockcontrol.schedule.StockControlParameter;
import jp.co.daifuku.wms.master.schedule.MasterStockControlParameter;

/**
 * Designer : T.Yoshino <BR>
 * Maker : K.Mukai <BR>
 * <BR>
 * 在庫メンテナンス登録クラスです。<BR>
 * 画面から入力した内容をパラメータにセットし、<BR>
 * そのパラメータを基にスケジュールが在庫メンテナンス登録をします。<BR>
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
 * 		棚* <BR>
 * 		ケース入数*2 *4<BR>
 * 		在庫ケース数*2 <BR>
 * 		ケースITF <BR>
 * 		ボール入数 <BR>
 *		在庫ピース数 *4 <BR>
 *		ボールITF <BR>
 *		入庫日* <BR>
 *		入庫時間 <BR>
 *		賞味期限 <BR>
 *		予定外入庫リストを発行する <BR>
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
 *  	棚 <BR>
 *  	ケース入数 <BR>
 *  	ボール入数 <BR>
 *  	在庫ケース数 <BR>
 *  	在庫ピース数 <BR>
 *  	ケースITF <BR>
 *  	ボールITF <BR>
 *  	入庫日 <BR>
 *  	入庫時間 <BR>
 *  	賞味期限 <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * 2.登録ボタン押下処理(<CODE>btn_StorageStart_Click()</CODE>メソッド) <BR>
 * <BR>
 * <DIR>
 *  ためうちエリアから入力した内容をパラメータにセットし、そのパラメータをもとにスケジュールが在庫メンテナンス登録を行います。<BR>
 *  スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 *  条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 *  スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * 	<DIR>
 *   [パラメータ] *必須入力 <BR>
 *   <BR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 *   荷主コード* <BR>
 *   荷主名称*<BR>
 *   商品コード* <BR>
 *   商品名称 <BR>
 *   ケース/ピース区分* <BR>
 *   ロケーションNo.* <BR>
 *   ケース入数* <BR>
 *   ボール入数* <BR>
 *   在庫ケース数* <BR>
 *   在庫ピース数* <BR>
 *   ケースITF <BR>
 *   ボールITF <BR>
 *   出庫日* <BR>
 *   出庫時間 <BR>
 *   賞味期限 <BR>
 *  </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/9/29</TD><TD>K.Mukai</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:18 $
 * @author  $Author: mori $
 */
public class MasterStockRegistBusiness extends MasterStockRegist implements WMSConstants
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
	 *    3.ためうちのボタン（登録、一覧クリア）を無効にします。<BR>
	 * <BR>
	 *    項目名[初期値]<BR>
	 *    <DIR>
	 * 		作業者コード	[なし] <BR>
	 * 		パスワード		[なし] <BR>
	 * 		荷主コード		[１件の場合、在庫情報テーブルの荷主コードをセット] <BR>
	 * 		荷主名称		[１件の場合、在庫情報テーブルの荷主名称をセット] <BR>
	 * 		商品コード		[なし] <BR>
	 * 		商品名称		[なし] <BR>
	 * 		ケース／ピース区分[なし] <BR>
	 * 		入庫棚			[なし] <BR>
	 * 		ケース入数		[なし] <BR>
	 * 		入庫ケース数	[なし] <BR>
	 * 		ケースITF		[なし] <BR>
	 * 		ボール入数		[なし] <BR>
	 * 		入庫ピース数	[なし] <BR>
	 * 		ボールITF		[なし] <BR>
	 * 		入庫日			[なし] <BR>
	 * 		入庫時間		[なし] <BR>
	 * 		賞味期限		[なし] <BR>
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
		btn_Submit.setEnabled(false);
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
		// 登録ボタン押下時確認ダイアログ "登録しますか？"
		btn_Submit.setBeforeConfirm("MSG-W0009");

		// 一覧クリアボタン押下時確認ダイアログ "一覧入力情報がクリアされます。宜しいですか？"
		btn_ListClear.setBeforeConfirm("MSG-W0012");

		// カーソルを作業者コードにセットする
		setFocus(txt_WorkerCode);
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが
	 * 呼ばれます。<BR><CODE>Page</CODE>に定義されている<CODE>page_DlgBack</CODE>をオーバライドします。
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		// 荷主コード
		String consignorcode = param.getParameter(ListStockConsignorBusiness.CONSIGNORCODE_KEY);
		// 荷主名称
		String consignorname = param.getParameter(ListStockConsignorBusiness.CONSIGNORNAME_KEY);
		// 商品コード
		String itemcode = param.getParameter(ListStockItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListStockItemBusiness.ITEMNAME_KEY);
		// ケース／ピース区分
		String casepieseflag = param.getParameter(ListStockLocationBusiness.CASEPIECEFLAG_KEY);
		// 棚
		String storagelocation = param.getParameter(ListStockLocationBusiness.LOCATION_KEY);
		// ケース入数
		String caseqty = param.getParameter(ListStockMaintenanceBusiness.ENTERINGQTY_KEY);
		// ボール入数
		String bundleqty = param.getParameter(ListStockMaintenanceBusiness.BUNDLE_ENTERINGQTY_KEY);
		// ケースITF
		String caseitf = param.getParameter(ListStockMaintenanceBusiness.CASEITF_KEY);
		// ボールITF
		String bundleitf = param.getParameter(ListStockMaintenanceBusiness.BUNDLEITF_KEY);
		// 入庫日
		String instockday = param.getParameter(ListStockMaintenanceBusiness.INSTOCKDAY_KEY);
		// 入庫日時
		String instocktime = param.getParameter(ListStockMaintenanceBusiness.INSTOCKTIME_KEY);
		// 賞味期限
		String usebydate = param.getParameter(ListStockMaintenanceBusiness.USEBYDATE_KEY);
		

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
		// ケース／ピース区分
		if (!StringUtil.isBlank(casepieseflag))
		{
			if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
			{
				rdo_CpfCase.setChecked(true);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
			{
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(true);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (casepieseflag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
			{
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(true);
			}
		}
		// 棚
		if (!StringUtil.isBlank(storagelocation))
		{
			txt_Location.setText(storagelocation);
		}
		// ケース入数
		if (!StringUtil.isBlank(caseqty))
		{
			txt_CaseEntering.setText(caseqty);
		}
		// ボール入数
		if (!StringUtil.isBlank(bundleqty))
		{
			txt_BundleEntering.setText(bundleqty);
		}
		// ケースITF
		if (!StringUtil.isBlank(caseitf))
		{
			txt_CaseItf.setText(caseitf);
		}
		// ボールITF
		if (!StringUtil.isBlank(bundleitf))
		{
			txt_BundleItf.setText(bundleitf);
		}
		// 入庫日
		if (!StringUtil.isBlank(instockday))
		{
			txt_StorageDate.setText(instockday);
		}
		// 入庫日時
		if (!StringUtil.isBlank(instocktime))
		{
			txt_StorageTime.setText(instocktime);
		}
		// 賞味期限
		if (!StringUtil.isBlank(usebydate))
		{
			txt_UseByDate.setText(usebydate);
		}

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
		if (!checker.checkContainNgText(txt_Location))
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
			// 荷主コード
			txt_ConsignorCode.setText("");
			// 荷主名称
			txt_ConsignorName.setText("");
			// 商品コード
			txt_ItemCode.setText("");
			// 商品名称
			txt_ItemName.setText("");
			// ケース／ピース区分
			rdo_CpfCase.setChecked(true);
			rdo_CpfPiece.setChecked(false);
			rdo_CpfAppointOff.setChecked(false);
			// 棚
			txt_Location.setText("");
			// ケース入数
			txt_CaseEntering.setText("");
			// 在庫ケース数
			txt_StcCaseQty.setText("");
			// ケースITF
			txt_CaseItf.setText("");
			// ボール入数
			txt_BundleEntering.setText("");
			// 在庫ピース数
			txt_StcPieceQty.setText("");
			// ボールITF
			txt_BundleItf.setText("");
			// 入庫時間
			txt_StorageTime.setText("");
			// 賞味期限
			txt_UseByDate.setText("");
			
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterStockRegistSCH(conn);

			MasterStockControlParameter param = (MasterStockControlParameter) schedule.initFind(conn, null);
			// 荷主コードが1件の場合、初期表示させる。
			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
				
				if (param.getMergeType() == MasterStockControlParameter.MERGE_TYPE_OVERWRITE)
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
			// 入庫日(システム日付)を初期セットします
			txt_StorageDate.setDate(new Date());

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

	/** Parameterクラスにためうちエリアのデータをセットするメソッドです。<BR>
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
	 * 			ためうちエリアの内容を持つ<CODE>StockControlParameter</CODE>クラスのインスタンスの配列<BR>
	 * 		</DIR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。 
	 */
	private MasterStockControlParameter[] setListParam(int lineno) throws Exception
	{

		Vector vecParam = new Vector();

		for (int i = 1; i < lst_SStockRegist.getMaxRows(); i++)
		{
			// 修正対象行は除く
			if (i == lineno)
			{
				continue;
			}

			// 行指定
			lst_SStockRegist.setCurrentRow(i);

			// スケジュールパラメータへセット
			MasterStockControlParameter param = new MasterStockControlParameter();
			// 入力エリア情報
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 端末No.
			UserInfoHandler userHandler = new UserInfoHandler((DfkUserInfo) this.getUserInfo());
			param.setTerminalNumber(userHandler.getTerminalNo());
			// 荷主コード
			param.setConsignorCode(lst_SStockRegist.getValue(3));
			// 商品コード
			param.setItemCode(lst_SStockRegist.getValue(4));
			// ケース／ピース区分
			if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0374")))
			{
				// 指定なし
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_NOTHING);
			}
			else if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0375")))
			{
				// ケース
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_CASE);
			}
			else if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0376")))
			{
				// ピース
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_PIECE);
			}
			// 棚
			param.setLocationNo(lst_SStockRegist.getValue(6));
			// ケース入数
			param.setEnteringQty(Formatter.getInt(lst_SStockRegist.getValue(7)));
			// 在庫ケース数
			param.setStockCaseQty(Formatter.getInt(lst_SStockRegist.getValue(8)));
			// ケースITF
			param.setITF(lst_SStockRegist.getValue(9));
			// 賞味期限
			param.setUseByDate(lst_SStockRegist.getValue(11));
			// 荷主名称
			param.setConsignorName(lst_SStockRegist.getValue(12));
			// 商品名称
			param.setItemName(lst_SStockRegist.getValue(13));
			// ボール入数
			param.setBundleEnteringQty(Formatter.getInt(lst_SStockRegist.getValue(14)));
			// 在庫ピース数
			param.setStockPieceQty(Formatter.getInt(lst_SStockRegist.getValue(15)));
			// ボールITF
			param.setBundleITF(lst_SStockRegist.getValue(16));

			// 行No.を保持しておく
			param.setRowNo(i);

			// 入庫日と入庫時間
			param.setStorageDate(
				WmsFormatter.getTimeStampDate(
					lst_SStockRegist.getValue(10),
					lst_SStockRegist.getValue(17),
					this.getHttpRequest().getLocale()));

			// 荷主コード最終使用日
			param.setConsignorLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(0, lst_SStockRegist.getValue(0))));
			// 商品コード最終使用日
			param.setItemLastUpdateDate(WmsFormatter.getTimeStampDate(CollectionUtils.getString(1, lst_SStockRegist.getValue(0))));
			
			vecParam.addElement(param);
		}

		if (vecParam.size() > 0)
		{
			// セットするためうちデータがあれば値をセット
			MasterStockControlParameter[] listparam = new MasterStockControlParameter[vecParam.size()];
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
	private void setList(String storageTime, MasterStockControlParameter mergeParam) throws Exception
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
		// 入庫日
		toolTip.add(DisplayText.getText("LBL-W0237"), txt_StorageDate.getText());
		// 入庫時間
		if (!StringUtil.isBlank(txt_StorageTime.getTime()))
		{
			toolTip.add(DisplayText.getText("LBL-W0368"), txt_StorageTime.getText());
		}
		else
		{
			toolTip.add(DisplayText.getText("LBL-W0368"), storageTime);
		}
		// 賞味期限
		toolTip.add(DisplayText.getText("LBL-W0270"), txt_UseByDate.getText());

		//カレント行にTOOL TIPをセットする
		lst_SStockRegist.setToolTip(lst_SStockRegist.getCurrentRow(), toolTip.getText());

		// 荷主コード
		lst_SStockRegist.setValue(3, txt_ConsignorCode.getText());
		// 商品コード
		lst_SStockRegist.setValue(4, txt_ItemCode.getText());
		// 区分
		if (rdo_CpfAppointOff.getChecked())
		{
			// 指定なし
			lst_SStockRegist.setValue(5, DisplayText.getText("LBL-W0374"));
		}
		else if (rdo_CpfCase.getChecked())
		{
			// ケース
			lst_SStockRegist.setValue(5, DisplayText.getText("LBL-W0375"));
		}
		else if (rdo_CpfPiece.getChecked())
		{
			// ピース
			lst_SStockRegist.setValue(5, DisplayText.getText("LBL-W0376"));
		}
		// 棚
		lst_SStockRegist.setValue(6, txt_Location.getText());
		// ケース入数
		if (StringUtil.isBlank(txt_CaseEntering.getText()))
		{
			lst_SStockRegist.setValue(7, "0");
		}
		else
		{
			lst_SStockRegist.setValue(7, WmsFormatter.getNumFormat(txt_CaseEntering.getInt()));

		}
		// 在庫ケース数
		if (StringUtil.isBlank(txt_StcCaseQty.getText()))
		{
			lst_SStockRegist.setValue(8, "0");
		}
		else
		{
			lst_SStockRegist.setValue(8, WmsFormatter.getNumFormat(txt_StcCaseQty.getInt()));
		}
		// ケースITF
		lst_SStockRegist.setValue(9, txt_CaseItf.getText());
		// 入庫日
		lst_SStockRegist.setValue(10, txt_StorageDate.getText());
		// 賞味期限
		lst_SStockRegist.setValue(11, txt_UseByDate.getText());
		// 荷主名称
		lst_SStockRegist.setValue(12, txt_ConsignorName.getText());
		// 商品名称
		lst_SStockRegist.setValue(13, txt_ItemName.getText());
		// ボール入数
		if (StringUtil.isBlank(txt_BundleEntering.getText()))
		{
			lst_SStockRegist.setValue(14, "0");
		}
		else
		{
			lst_SStockRegist.setValue(14, WmsFormatter.getNumFormat(txt_BundleEntering.getInt()));
		}
		// 在庫ピース数
		if (StringUtil.isBlank(txt_StcPieceQty.getText()))
		{
			lst_SStockRegist.setValue(15, "0");
		}
		else
		{
			lst_SStockRegist.setValue(15, WmsFormatter.getNumFormat(txt_StcPieceQty.getInt()));
		}
		// ボールITF
		lst_SStockRegist.setValue(16, txt_BundleItf.getText());
		// 入庫時間
		if (!StringUtil.isBlank(txt_StorageTime.getTime()))
		{
			lst_SStockRegist.setValue(17, txt_StorageTime.getText());
		}
		else
		{
			lst_SStockRegist.setValue(17, storageTime);
		}
		
		List hiddenList = new Vector();
		// 荷主コード最終使用日
		hiddenList.add(0, WmsFormatter.getTimeStampString(mergeParam.getConsignorLastUpdateDate()));
		// 商品コード最終使用日
		hiddenList.add(1, WmsFormatter.getTimeStampString(mergeParam.getItemLastUpdateDate()));
		lst_SStockRegist.setValue(0, CollectionUtils.getConnectedString(hiddenList));
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
	public void btn_PsearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 処理中画面->結果画面
		redirect(
			"/master/listbox/listmasterconsignor/ListMasterConsignor.do",
			param,
			"/progress.do");
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
	public void btn_PsearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 商品検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
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
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Pstocksearch_Click(ActionEvent e) throws Exception
	{
		// 在庫検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース／ピース区分
		if (rdo_CpfCase.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}

		// 棚
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_Location.getText());
		// エリアタイプフラグ（一覧表示時にASRSを省く）
		param.setParameter(ListStockConsignorBusiness.AREA_TYPE_KEY, StockControlParameter.AREA_TYPE_FLAG_NOASRS);
		// 処理中画面->結果画面
		redirect(
			"/stockcontrol/listbox/liststockmaintenance/ListStockMaintenance.do",
			param,
			"/progress.do");
	}

	/** 
	 * 棚の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で棚一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       商品コード <BR>
	 *       ケース／ピース区分 <BR>
	 *       棚 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PsearchLocation_Click(ActionEvent e) throws Exception
	{
		// 棚検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(
			ListStockConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
		// 商品コード
		param.setParameter(ListStockItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// ケース／ピース区分
		if (rdo_CpfCase.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_CASE);
		}
		else if (rdo_CpfPiece.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_PIECE);
		}
		else if (rdo_CpfAppointOff.getChecked())
		{
			param.setParameter(
				ListStockLocationBusiness.CASEPIECEFLAG_KEY,
				StockControlParameter.CASEPIECE_FLAG_NOTHING);
		}
		// 棚
		param.setParameter(ListStockLocationBusiness.LOCATION_KEY, txt_Location.getText());
		// 処理中画面->結果画面
		redirect(
			"/stockcontrol/listbox/liststocklocation/ListStockLocation.do",
			param,
			"/progress.do");
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
	 * 			棚* <BR>
	 * 			ケース入数*2 *4 <BR>
	 * 			在庫ケース数*2 <BR>
	 * 			ケースITF <BR>
	 * 			ボール入数 <BR>
	 *			在庫ピース数*3 <BR>
	 *			ボールITF <BR>
	 *			入庫日 <BR>
	 *			入庫時間 <BR>
	 *			賞味期限 <BR>
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
	 * 		6.棚 <BR>
	 * 		7.ケース入数 <BR>
	 * 		8.在庫ケース数 <BR>
	 * 		9.ケースITF <BR>
	 * 		10.入庫日 <BR>
	 * 		11.賞味期限 <BR>
	 * 		11.荷主名称 <BR>
	 * 		12.商品名称 <BR>
	 * 		13.ボール入数 <BR>
	 * 		14.在庫ピース数 <BR>
	 * 		15.ボールITF <BR>
	 * 		16.入庫時間 <BR>
	 *   </DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。  
	 */
	public void btn_Input_Click(ActionEvent e) throws Exception
	{
		// 必須入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		txt_ItemCode.validate();
		txt_Location.validate();
		txt_StorageDate.validate();

		// パターンマッチング文字
		txt_ConsignorName.validate(false);
		txt_ItemName.validate(false);
		txt_CaseEntering.validate(false);
		txt_StcCaseQty.validate(false);
		txt_CaseItf.validate(false);
		txt_BundleEntering.validate(false);
		txt_StcPieceQty.validate(false);
		txt_BundleItf.validate(false);
		txt_StorageTime.validate(false);
		txt_UseByDate.validate(false);
		// eWareNavi用入力文字チェック
		if (!checkContainNgText())
		{
			return;
		}
		Connection conn = null;

		try
		{
			MasterStockControlParameter mergeParam = new MasterStockControlParameter();
			mergeParam.setConsignorCode(txt_ConsignorCode.getText());
			mergeParam.setConsignorName(txt_ConsignorName.getText());
			mergeParam.setItemCode(txt_ItemCode.getText());
			mergeParam.setItemName(txt_ItemName.getText());
			mergeParam.setEnteringQty(txt_CaseEntering.getInt());
			mergeParam.setBundleEnteringQty(txt_BundleEntering.getInt());
			mergeParam.setITF(txt_CaseItf.getText());
			mergeParam.setBundleITF(txt_BundleItf.getText());
			
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			WmsScheduler schedule = new MasterStockRegistSCH(conn);
			
			mergeParam = (MasterStockControlParameter) schedule.query(conn, mergeParam)[0];
			// 入力エリアに補完結果を表示
			txt_ConsignorName.setText(mergeParam.getConsignorName());
			txt_ItemName.setText(mergeParam.getItemName());
			txt_CaseEntering.setInt(mergeParam.getEnteringQty());
			txt_BundleEntering.setInt(mergeParam.getBundleEnteringQty());
			txt_CaseItf.setText(mergeParam.getITF());
			txt_BundleItf.setText(mergeParam.getBundleITF());
			
			// スケジュールパラメータへセット
			// 入力エリア
			MasterStockControlParameter param = new MasterStockControlParameter();
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 荷主名称
			param.setConsignorName(txt_ConsignorName.getText());
			// 商品コード
			param.setItemCode(txt_ItemCode.getText());
			// 商品名称
			param.setItemName(txt_ItemName.getText());
			// ケース／ピース区分
			if (rdo_CpfCase.getChecked())
			{
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_CASE);
			}
			else if (rdo_CpfPiece.getChecked())
			{
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_PIECE);
			}
			else if (rdo_CpfAppointOff.getChecked())
			{
				param.setCasePieceFlag(StockControlParameter.CASEPIECE_FLAG_NOTHING);
			}
			// 棚
			param.setLocationNo(txt_Location.getText());
			// ケース入数
			param.setEnteringQty(txt_CaseEntering.getInt());
			// 在庫ケース数
			param.setStockCaseQty(txt_StcCaseQty.getInt());
			// ケースITF
			param.setITF(txt_CaseItf.getText());
			// ボール入数
			param.setBundleEnteringQty(txt_BundleEntering.getInt());
			// 在庫ピース数
			param.setStockPieceQty(txt_StcPieceQty.getInt());
			// ボールITF
			param.setBundleITF(txt_BundleItf.getText());
			//入庫日と入庫時間を結合してパラメータにセットします
			DateUtil dateutil = new DateUtil();
			Date storageDate = dateutil.getDate(txt_StorageDate.getDate(), txt_StorageTime.getTime());
			if (storageDate == null) storageDate = new Date();
			param.setStorageDate(storageDate);
			// 賞味期限		
			param.setUseByDate(txt_UseByDate.getText());

			// スケジュールパラメータへセット
			// ためうちエリア
			MasterStockControlParameter[] listparam = null;

			// ためうちにデータがなければnullをセット
			if (lst_SStockRegist.getMaxRows() == 1)
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
					lst_SStockRegist.addRow();
					lst_SStockRegist.setCurrentRow(lst_SStockRegist.getMaxRows() - 1);
					setList(WmsFormatter.getTimeFormat(storageDate, ""), param);
				}
				else
				{
					// 修正中の入力データであれば、修正対象行のデータを更新
					lst_SStockRegist.setCurrentRow(this.getViewState().getInt("LineNo"));
					setList(WmsFormatter.getTimeFormat(storageDate, ""), param);
					// 選択行の色を元に戻す
					lst_SStockRegist.resetHighlight();
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
	 * 登録開始ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、在庫メンテナンス登録を行います。<BR>
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
	 *						ためうち.入庫日 <BR>
	 *						ためうち.入庫時間 <BR>
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
	 * 		6.棚 <BR>
	 * 		7.ケース入数 <BR>
	 * 		8.在庫ケース数 <BR>
	 * 		9.ケースITF <BR>
	 * 		10.入庫日 <BR>
	 * 		11.賞味期限 <BR>
	 * 		11.荷主名称 <BR>
	 * 		12.商品名称 <BR>
	 * 		13.ボール入数 <BR>
	 * 		14.在庫ピース数 <BR>
	 * 		15.ボールITF <BR>
	 * 		16.入庫時間 <BR>
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
			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);

			// スケジュールパラメータへセット
			MasterStockControlParameter[] param = null;
			// ためうちエリアの全データをセット
			param = setListParam(-1);

			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterStockRegistSCH(conn);

			// スケジュールスタート
			if (schedule.startSCH(conn, param))
			{
				// 結果がtrueであれば、commit
				conn.commit();
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());

				// 一覧クリア
				lst_SStockRegist.clearRow();

				// 修正状態をデフォルトに戻す
				this.getViewState().setInt("LineNo", -1);

				// ボタン押下を不可にする
				// 入庫開始ボタン
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
		lst_SStockRegist.clearRow();

		// ボタン押下を不可にする
		// 登録ボタン
		btn_Submit.setEnabled(false);
		// 一覧クリアボタン
		btn_ListClear.setEnabled(false);

		// 修正状態をデフォルトに戻す
		this.getViewState().setInt("LineNo", -1);
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
	public void lst_SStockRegist_Click(ActionEvent e) throws Exception
	{
		// 取消ボタンクリック時
		if (lst_SStockRegist.getActiveCol() == 1)
		{
			// リスト削除
			lst_SStockRegist.removeRow(lst_SStockRegist.getActiveRow());

			// ためうち情報が存在しない場合、登録ボタン･一覧クリアボタンは無効にする
			// ためうちにデータがなければnullをセット
			if (lst_SStockRegist.getMaxRows() == 1)
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
			lst_SStockRegist.resetHighlight();

			// カーソルを作業者コードにセットする
			setFocus(txt_WorkerCode);

		}
		// 修正ボタンクリック時(Modify動作を行う)
		else if (lst_SStockRegist.getActiveCol() == 2)
		{
			// 現在の行をセット
			lst_SStockRegist.setCurrentRow(lst_SStockRegist.getActiveRow());
			// 荷主コード
			txt_ConsignorCode.setText(lst_SStockRegist.getValue(3));
			// 商品コード
			txt_ItemCode.setText(lst_SStockRegist.getValue(4));
			// ケース／ピース区分
			if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0374")))
			{
				// 指定なし
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(true);
			}
			else if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0375")))
			{
				// ケース
				rdo_CpfCase.setChecked(true);
				rdo_CpfPiece.setChecked(false);
				rdo_CpfAppointOff.setChecked(false);
			}
			else if (lst_SStockRegist.getValue(5).equals(DisplayText.getText("LBL-W0376")))
			{
				// ピース
				rdo_CpfCase.setChecked(false);
				rdo_CpfPiece.setChecked(true);
				rdo_CpfAppointOff.setChecked(false);
			}
			// 棚
			txt_Location.setText(lst_SStockRegist.getValue(6));
			// ケース入数
			txt_CaseEntering.setText(lst_SStockRegist.getValue(7));
			// 在庫ケース数
			txt_StcCaseQty.setText(lst_SStockRegist.getValue(8));
			// ケースITF
			txt_CaseItf.setText(lst_SStockRegist.getValue(9));
			// 入庫日
			txt_StorageDate.setText(lst_SStockRegist.getValue(10));
			// 賞味期限
			txt_UseByDate.setText(lst_SStockRegist.getValue(11));
			// 荷主名称
			txt_ConsignorName.setText(lst_SStockRegist.getValue(12));
			// 商品名称
			txt_ItemName.setText(lst_SStockRegist.getValue(13));
			// ボール入数
			txt_BundleEntering.setText(lst_SStockRegist.getValue(14));
			// 在庫ピース数
			txt_StcPieceQty.setText(lst_SStockRegist.getValue(15));
			// ボールITF
			txt_BundleItf.setText(lst_SStockRegist.getValue(16));
			// 入庫時間
			txt_StorageTime.setText(lst_SStockRegist.getValue(17));

			// ViewStateに保存
			// 修正ボタン押下による修正状態かどうかを判断するためにViewStateにためうち行No.をセット
			this.getViewState().setInt("LineNo", lst_SStockRegist.getActiveRow());

			//修正行を黄色に変更する
			lst_SStockRegist.setHighlight(lst_SStockRegist.getActiveRow());
		}
	}

}
//end of class
