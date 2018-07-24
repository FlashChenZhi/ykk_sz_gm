// $Id: InstockReceivePlanDeleteBusiness.java,v 1.1.1.1 2006/08/17 09:34:11 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.instockreceiveplandelete;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.Constants;
import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.DialogEvent;
import jp.co.daifuku.bluedog.webapp.DialogParameters;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceivePlanDeleteSCH;

/**
 * Designer : T.Yoshino <BR>
 * Maker : K.Matsuda<BR>
 * 
 * 入荷予定情報の削除の画面クラスです<BR>
 * 画面から入力された内容をパラメータにセットしてスケジュールに渡し、表示処理を行います。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで <BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。 <BR>
 * ためうちエリアに表示された内容をパラメータにセットしスケジュールに渡し、削除処理を行います。<BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで<BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * <BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期表示処理(<CODE>page_Load()</CODE>)<BR>
 * <DIR>
 * 		画面初期表示で入荷予定情報に荷主コードが1件のみ存在した場合、表示します。 <BR>
 * 		<BR>
 * 		[パラメータ] <BR>
 * 		<BR>
 * 		<DIR>
 * 			なし<BR>
 * 		</DIR>
 * 		<BR>
 * 		[返却データ] <BR>
 * 		<BR>
 * 		<DIR>
 * 			荷主コード <BR>
 * 		</DIR>
 * 		<BR>
 * </DIR>
 * <BR>
 * 2.表示ボタン押下処理(<CODE>btn_View_Click()</CODE>)<BR>
 * <DIR>
 * 		入力項目のチェックを行い、正しい場合パラメータにセットしてスケジュールクラスに渡します。 <BR>
 * 		スケジュールの結果をためうちエリア、メッセージエリアにセットし、表示します。 <BR>
 * 		<BR>
 * 		[パラメータ] *必須入力<BR>
 * 		<DIR>
 * 			作業者コード*<BR>
 * 			パスワード*<BR>
 * 			荷主コード*<BR>
 * 			開始入荷予定日<BR>
 * 			終了入荷予定日<BR>
 * 			TC/DC区分*<BR>
 * 			入荷予定削除一覧リスト発行区分*<BR>
 * 		</DIR>
 * 		<BR>
 * 		[返却データ]<BR>
 * 		<DIR>
 * 			各メッセージ<BR>
 * 			選択チェックBoxの有効／無効 <BR>
 * 			荷主コード<BR>
 * 			荷主名称<BR>
 * 			入荷予定日<BR>
 * 			ＴＣ／ＤＣ(名称)<BR>
 * 			仕入先コード<BR>
 * 			仕入先名称<BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * 3.削除ボタン押下処理(<CODE>btn_Delete_Click()</CODE>)<BR>
 * <DIR>
 * 		作業者コードとパスワードの入力チェックを行います。<BR>
 * 		入力チェックが正常ならば、選択チェックのついているためうちデータをスケジュールに渡して、削除処理を行います。<BR>
 * 		<BR>
 * 		[パラメータ] *必須入力<BR>
 * 		<DIR>
 * 			作業者コード*<BR>
 * 			パスワード*<BR>
 * 			荷主コード*<BR>
 * 			入荷予定日*<BR>
 * 			ＴＣ／ＤＣ*<BR>
 * 			仕入先コード*<BR>
 * 			ためうち行No.*<BR>
 * 		</DIR>
 * 		<BR>
 * 		[返却データ]<BR>
 * 		<DIR>
 * 			各メッセージ<BR>
 * 			選択チェックBoxの有効／無効 <BR>
 * 			荷主コード<BR>
 * 			荷主名称<BR>
 * 			入荷予定日<BR>
 * 			ＴＣ／ＤＣ(名称)<BR>
 * 			仕入先コード<BR>
 * 			仕入先名称<BR>
 * 		</DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/12</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:11 $
 * @author  $Author: mori $
 */
public class InstockReceivePlanDeleteBusiness extends InstockReceivePlanDelete implements WMSConstants
{
	// Class fields --------------------------------------------------
	/**
	 * 選択チェックボックスの列番号
	 */
	private static final int COL_SELECT = 1;

	/**
	 * 入荷予定日の列番号
	 */
	private static final int COL_INSTOCK_PLAN_DATE = 2;
	
	/**
	 * ＴＣ／ＤＣの列番号
	 */
	private static final int COL_TC_DC_FLAG = 3;
	
	/**
	 * 仕入先コードの列番号
	 */
	private static final int COL_SUPPLIER_CODE = 4;
	
	/**
	 * 仕入先名称の列番号
	 */
	private static final int COL_SUPPLIER_NAME = 5;
	
	/**
	 * ViewStateのkey(荷主コード)
	 */
	private static final String KEY_CONSIGNOR_CODE = "CONSIGNOR_CODE";
	
	/**
	 * ViewStateのkey(開始入荷予定日)
	 */
	private static final String KEY_FROM_INSTOCK_PLAN_DATE = "FROM_INSTOCK_PLAN_DATE";
	
	/**
	 * ViewStateのkey(終了入荷予定日)
	 */
	private static final String KEY_TO_INSTOCK_PLAN_DATE = "TO_INSTOCK_PLAN_DATE";
	
	/**
	 * ViewStateのkey(ＴＣ／ＤＣ区分)
	 */
	private static final String KEY_TC_DC_FLAG = "TC_DC_FLAG";

	/**
	 * HIDDEN項目用：0 = ケースピース区分
	 */
	protected static final int HIDDEN0 = 0;
	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面が読み込まれたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。<BR>
	 * <BR>
	 * <DIR>
	 *    1.入力エリアを初期設定します。<BR>
	 *    2.スケジュールを開始します。 <BR>
	 *    3.全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンを無効にします。<BR>
	 *    4.カーソルを作業者コードにセットします。<BR>
	 * </DIR>
	 * <BR>
	 * 項目[初期値] <BR>
	 * <BR>
	 * 作業者コード[なし] <BR>
	 * パスワード[なし] <BR>
	 * 荷主コード[検索値] <BR>
	 * 開始入荷予定日[なし] <BR>
	 * 終了入荷予定日[なし] <BR>
	 * 入荷予定削除一覧リスト発行する[true] <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 初期値のセット
		setInitView(false);
		
		// ためうちエリアのボタン無効化
		setBtnTrueFalse(false);
	}
	
	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * <DIR>概要:ダイアログを表示します。 <BR>
	 * </DIR> <BR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
		String menuparam = this.getHttpRequest().getParameter(Constants.MENUPARAM);
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
		// ダイアログを表示します MSG-W0007=削除しますか？
		btn_Delete.setBeforeConfirm("MSG-W0007");
		// ダイアログを表示します MSG-W0012=一覧入力情報がクリアされます。宜しいですか？
		btn_ListClear.setBeforeConfirm("MSG-W0012");
	}

	/**
	 * ポップアップウインドから、戻ってくるときにこのメソッドが呼ばれます。 <BR>
	 * Pageに定義されているpage_DlgBackをオーバライドします。 <BR>
	 * <BR>
	 * 概要:検索画面の返却データを取得しセットします <BR>
	 * <BR>
	 * <DIR>
	 * 		1.ポップアップの検索画面から返される値を取得します。 <BR>
	 * 		2.値が空でないときに画面にセットします。 <BR>
	 * 		<BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		String consignorCode = param.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		Date startInstockPlanDate = WmsFormatter.toDate(param.getParameter(ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY), this.getHttpRequest().getLocale());
		Date endInstockPlanDate = WmsFormatter.toDate(param.getParameter(ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY), this.getHttpRequest().getLocale());

		// 荷主コード
		if (!StringUtil.isBlank(consignorCode))
		{
			txt_ConsignorCode.setText(consignorCode);
		}
		// 開始入荷予定日
		if (!StringUtil.isBlank(startInstockPlanDate))
		{
			this.txt_StrtInstkPlanDate.setDate(startInstockPlanDate);
		}
		// 終了入荷予定日
		if (!StringUtil.isBlank(endInstockPlanDate))
		{
			this.txt_EdInstkPlanDate.setDate(endInstockPlanDate);
		}

		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * スケジュールから荷主コードを取得するためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した荷主コードを返します。<BR>
	 * <DIR>
	 *   1.荷主コードが1件しかない場合該当荷主を返します。そうでない場合はnullを返します。 <BR>
	 * <DIR>
	 * @throws Exception 全ての例外を報告します。
	 *
	 * @return 荷主コード
	 */
	private String getConsignorCode() throws Exception
	{
		Connection conn = null;

		try
		{
			// コネクションを取得します。
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
			// パラメータの宣言
			InstockReceiveParameter param = new InstockReceiveParameter();

			// スケジュールから荷主コードを取得します。
			WmsScheduler schedule = new InstockReceivePlanDeleteSCH();
			// スケジュールを開始します。
			param = (InstockReceiveParameter) schedule.initFind(conn, param);

			// paramにデータがあります。
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
				// コネクションのクローズを行います。
				if (conn != null)
				{
					conn.close();
				}
			}
			catch (SQLException se)
			{
				message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(se, this.getClass()));
			}
		}
		return null;
	}
	
	/**
	 * ボタンの編集可否を設定するメソッドです。<BR>
	 * <BR>
	 * 概要：false or trueを受け取ってボタンの編集可否を設定します。<BR>
	 * @param  status(false or true)
	 * @return なし
	 */
	private void setBtnTrueFalse(boolean arg)
	{
		// ためうち部のボタン
		btn_AllCheck.setEnabled(arg);
		btn_AllCheckClear.setEnabled(arg);
		btn_Delete.setEnabled(arg);
		btn_ListClear.setEnabled(arg);
	}
	
	/**
	 * ためうちエリアに検索結果をマッピングするためのメソッドです。<BR>
	 * <BR>
	 * 概要：スケジュールから取得した検索結果をためうちエリアに表示するのに使用します。<BR>
	 * ためうちエリアに荷主コード、荷主名称、入荷予定日、ＴＣ／ＤＣ、仕入先コード、仕入先名称とリストセルを表示します。<BR>
	 * <BR>
	 * リストセルの列番号一覧
	 * <DIR>
	 * 1：選択box <BR>
	 * 2：入荷予定日 <BR>
	 * 3：ＴＣ／ＤＣ<BR>
	 * 4：仕入先コード<BR>
	 * 5：仕入先名称 <BR>
	 * </DIR>
	 *
	 * @param param InstockReceiveParameter[] ためうちエリアに表示するための情報。
	 * @throws Exception 全ての例外を報告します。
	 */
	private void addList(InstockReceiveParameter[] param) throws Exception
	{
		// ためうちエリアに検索結果を表示します。
		// 荷主
		txt_RConsignorCode.setText(param[0].getConsignorCode());
		txt_RConsignorName.setText(param[0].getConsignorName());

		// リストセルに値をセット
		for (int i = 0; i < param.length; i++)
		{
			// リストセルに行を追加
			lst_SInstkRcvPlanDelete.addRow();

			// 値をセットする行を選択します
			lst_SInstkRcvPlanDelete.setCurrentRow(i+1);
			
			// 入荷予定日
			lst_SInstkRcvPlanDelete.setValue(2, WmsFormatter.toDispDate(param[i].getPlanDate(), this.getHttpRequest().getLocale()));
			// ＴＣ／ＤＣ
			lst_SInstkRcvPlanDelete.setValue(3, param[i].getTcdcName());
			// 仕入先コード
			lst_SInstkRcvPlanDelete.setValue(4, param[i].getSupplierCode());
			// 仕入先名称
			lst_SInstkRcvPlanDelete.setValue(5, param[i].getSupplierName());
			
			// HIDDEN項目にケースピース区分を保持
			String hidden = createHiddenList(param[i]);
			lst_SInstkRcvPlanDelete.setValue(0, hidden);
			
		}
	}
	
	/**
	 * 初期表示処理。
	 * 荷主コード、開始入荷予定日、終了入荷予定日をクリアし、
	 * ＴＣ／ＤＣ区分を「全て」にセットし、削除リスト発行区分にチェックを入れます。<BR>
	 * 渡されたパラメータがfalseならば、作業者コード、パスワードもクリアします。
	 * 作業者コードにカーソルをセットします。
	 * @param arg	
	 * @throws Exception
	 */
	private void setInitView(boolean arg) throws Exception
	{
		// 初期値のセット
		// パラメータがfalseなら作業者コード、パスワードをクリア
		if(!arg)
		{
			// 作業者コード
			txt_WorkerCode.setText("");
			// パスワード
			txt_Password.setText("");
		}
		// 荷主コード
		txt_ConsignorCode.setText(getConsignorCode());
		// 開始入荷予定日
		txt_StrtInstkPlanDate.setText("");
		// 終了入荷予定日
		txt_EdInstkPlanDate.setText("");
		// ＴＣ／ＤＣ区分
		rdo_CrossDCFlagAll.setChecked(true);
		rdo_CrossDCFlagDC.setChecked(false);
		rdo_CrossDCFlagCross.setChecked(false);
		rdo_CrossDCFlagTC.setChecked(false);
		// 入予定削除一覧リスト発行区分
		chk_CommonUse.setChecked(true);
		
		// 作業者コードにカーソルをセット
		setFocus(txt_WorkerCode);
	}
	
	/**
	 * HIDDEN項目を１つの文字列に連結するためのメソッドです。<BR>
	 * <BR>
	 * HIDDENの項目順一覧
	 * <DIR>
	 * 		0：出庫ケース数<BR>
	 * 		1：出庫ピース数<BR>
	 * 		2：状態<BR>
	 * 		3：賞味期限<BR>
	 * 		4：作業No. <BR>
	 * 		5：最終更新日時<BR>
	 * </DIR>
	 * 
	 * @param viewParam InstockReceiveParameter HIDDEN項目を含む<CODE>Parameter</CODE>クラス。
	 * @return 連結したHIDDEN項目
	 */
	private String createHiddenList(InstockReceiveParameter viewParam)
	{
		String hidden = null;

		// HIDDEN項目文字列作成 
		ArrayList hiddenList = new ArrayList();

		// TC/DC区分(リストセル)
		hiddenList.add(HIDDEN0, viewParam.getTcdcFlag());

		// HIDDEN項目を連結します
		hidden = CollectionUtils.getConnectedString(hiddenList);

		return hidden;
	}
	
	/**
	 * リストセルの選択チェックボックスの値を設定します。
	 * 
	 * @param check チェックボックスに設定する値
	 */
	private void listCellCheckAllChange(boolean check)
	{
		for (int i = 1; i < this.lst_SInstkRcvPlanDelete.getMaxRows(); i++)
		{
			this.lst_SInstkRcvPlanDelete.setCurrentRow(i);
			this.lst_SInstkRcvPlanDelete.setChecked(COL_SELECT, check);
		}
		
		setFocus(this.txt_WorkerCode);
	}
	
	/**
	 * リストセルのクリア処理を行います。
	 * @throws Exception	全ての例外を報告します。
	 */
	public void listClear() throws Exception
	{
		// 行情報を全て削除します。
		lst_SInstkRcvPlanDelete.clearRow();
		// ためうちエリアの荷主部分を消す
		txt_RConsignorCode.setText("");
		txt_RConsignorName.setText("");
		
		// ためうち部のボタンを無効にします。
		setBtnTrueFalse(false);
		// カーソルを作業者コードにセットします。
		setFocus(txt_WorkerCode);
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
		// メニュー画面に遷移します。
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
	 * 荷主コードの検索ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。 <BR>
	 * 該当データが見つからない場合は要素数0の <CODE>Parameter</CODE>
	 * 配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。 <BR>
	 * <BR>
	 * [パラメータ] *必須入力 <BR>
	 * <DIR>
	 * 		荷主コード<BR>
	 * 		状態＝未開始<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception	全ての例外を報告します。
	 */
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();

		// 荷主コードをセット
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 状態
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.WORKSTATUSCONSIGNOR_KEY,
			InstockPlan.STATUS_FLAG_UNSTART);
			
		// 検索フラグ：予定
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
		// 荷主検索リストボックスを表示する。
		redirect(
			"/instockreceive/listbox/listinstockreceiveconsignor/ListInstockReceiveConsignor.do",
			forwardParam, "/progress.do");
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
	 * 開始入荷予定日の検索ボタンが押下されたときに呼ばれます。 <BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。 <BR>
	 * 該当データが見つからない場合は要素数0の <CODE>Parameter</CODE>
	 * 配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。 <BR>
	 * <BR>
	 * [パラメータ] *必須入力 <BR>
	 * <DIR>
	 * 		荷主コード <BR>
	 * 		開始入荷予定日<BR>
	 * 		状態＝未開始<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchStrtInstkPlanDate_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();

		// 荷主コードをセット
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 開始入荷予定日をセット
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.STARTINSTOCKPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));
		
		// 入荷予定日範囲フラグ(開始)をセット
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.RANGEINSTOCKPLANDATE_KEY,
			InstockReceiveParameter.RANGE_START);
		
		// 状態
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.WORKSTATUSINSTOCKPLANDATE_KEY,
			InstockPlan.STATUS_FLAG_UNSTART);
		
		// 検索フラグ：予定
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
		// 入荷予定日検索リストボックスを表示する。
		redirect(
			"/instockreceive/listbox/listinstockreceiveplandate/ListInstockReceivePlanDate.do",
			forwardParam, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_FromTo_Server(ActionEvent e) throws Exception
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
	 * 終了入荷予定日の検索ボタンが押下されたときに呼ばれます。<BR>
	 * 画面から入力した内容をパラメータにセットし、そのパラメータを基にスケジュールが表示用のデータを検索します。 <BR>
	 * 該当データが見つからない場合は要素数0の <CODE>Parameter</CODE>
	 * 配列を受け取ります。また、条件エラーなどが発生した場合はnullを受け取ります。 <BR>
	 * <BR>
	 * [パラメータ] *必須入力 <BR>
	 * <DIR>
	 * 		荷主コード <BR>
	 * 		終了入荷予定日<BR>
	 * 		状態＝未開始<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */ 
	public void btn_PSearchEdInstkPlanDate_Click(ActionEvent e) throws Exception
	{
		// 検索条件をセットするインスタンスを宣言
		ForwardParameters forwardParam = new ForwardParameters();

		// 荷主コードをセット
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY,
			txt_ConsignorCode.getText());
			
		// 終了入荷予定日をセット
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.ENDINSTOCKPLANDATE_KEY,
			WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));
		
		// 入荷予定日範囲フラグ(終了)をセット
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.RANGEINSTOCKPLANDATE_KEY,
			InstockReceiveParameter.RANGE_END);
		
		// 状態
		forwardParam.setParameter(
			ListInstockReceivePlanDateBusiness.WORKSTATUSINSTOCKPLANDATE_KEY,
			InstockPlan.STATUS_FLAG_UNSTART);
		
		// 検索フラグ：予定
		forwardParam.setParameter(
			ListInstockReceiveConsignorBusiness.SEARCHCONSIGNOR_KEY,
			InstockReceiveParameter.SEARCHFLAG_PLAN);
			
		// 入荷予定日検索リストボックスを表示する。
		redirect(
		"/instockreceive/listbox/listinstockreceiveplandate/ListInstockReceivePlanDate.do",
		forwardParam, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_TCDCFlag_Server(ActionEvent e) throws Exception
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
	public void lbl_InstkPlanDltList_Server(ActionEvent e) throws Exception
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
	 * 表示ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:入力エリアの入力項目を条件に、ためうちに表示します。 <BR>
	 * <BR>
	 * <DIR>
	 * 		1.カーソルを作業者コードにセットします。<BR>
	 * 		2.入力エリアの入力項目のチェックを行います。<BR>
	 * 		<DIR>
	 * 			(必須入力、文字数、文字属性)<BR>
	 * 			(開始入荷予定日 ≦ 終了入荷予定日) <BR>
	 * 		</DIR>
	 * 		3.スケジュールを開始します。 <BR>
	 * 		4.スケジュールの結果をためうちエリアに表示を行います。 <BR>
	 * 		5.全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンを有効にします。 <BR>
	 * 		6.入力エリアの条件内容を保持します。 <BR>
	 * </DIR>
	 * <BR>
	 * リストセルの列番号一覧 <BR>
	 * <DIR>
	 * 		1:選択<BR>
	 * 		2:入荷予定日<BR>
	 *      3:TC/DC
	 * 		4:仕入先コード<BR>
	 * 		5:仕入先名称<BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_View_Click(ActionEvent e) throws Exception
	{
		// 作業者コードにカーソルをセット
		setFocus(txt_WorkerCode);
		
		// ためうちクリア
		listClear();
		
		// 入力チェック(必須入力、文字数、文字属性)
		txt_WorkerCode.validate();
		txt_Password.validate();
		txt_ConsignorCode.validate();
		
		// 開始入荷予定日が終了入荷予定日より小さいこと
		if (!StringUtil.isBlank(txt_StrtInstkPlanDate.getDate())
			&& !StringUtil.isBlank(txt_EdInstkPlanDate.getDate()))
		{
			if (txt_StrtInstkPlanDate.getDate().after(txt_EdInstkPlanDate.getDate()))
			{
				// 6023045 = 開始入荷予定日は、終了入荷予定日より前の日付にしてください。
				message.setMsgResourceKey("6023045");
				return;
			}
		}
		
		// コネクション保持用
		Connection conn = null;

		try
		{
			/* スケジュールを開始する */
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// パラメータの宣言
			InstockReceiveParameter param = new InstockReceiveParameter();

			// スケジュールパラメータをセットします。
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主コード
			param.setConsignorCode(txt_ConsignorCode.getText());
			// 開始入荷予定日
			param.setFromPlanDate(WmsFormatter.toParamDate(txt_StrtInstkPlanDate.getDate()));
			// 終了入荷予定日
			param.setToPlanDate(WmsFormatter.toParamDate(txt_EdInstkPlanDate.getDate()));
			// ＴＣ／ＤＣ区分
			if(rdo_CrossDCFlagAll.getChecked())
			{
				// 全て
				param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_ALL);
			}
			else if(rdo_CrossDCFlagDC.getChecked())
			{
				// DC
				param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_DC);
			}
			else if(rdo_CrossDCFlagCross.getChecked())
			{
				// クロス
				param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_CROSSTC);
			}
			else if(rdo_CrossDCFlagTC.getChecked())
			{
				// TC
				param.setTcdcFlag(InstockReceiveParameter.TCDC_FLAG_TC);
			}

			// スケジュールの宣言
			WmsScheduler schedule = new InstockReceivePlanDeleteSCH();
			// スケジュールを開始する
			InstockReceiveParameter[] viewParam = (InstockReceiveParameter[]) schedule.query(conn, param);

			// 何らかのエラーが起こった場合、または表示データがなかった場合は処理を終了します。
			if (viewParam == null || viewParam.length == 0)
			{
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}

			// ViewStateに検索値を保持します。 
			// 荷主コード
			this.getViewState().setString(KEY_CONSIGNOR_CODE, txt_ConsignorCode.getText());
			// 開始入荷予定日
			this.getViewState().setString(KEY_FROM_INSTOCK_PLAN_DATE, param.getFromPlanDate());
			// 終了入荷予定日
			this.getViewState().setString(KEY_TO_INSTOCK_PLAN_DATE, param.getToPlanDate());
			// ＴＣ／ＤＣ区分
			this.getViewState().setString(KEY_TC_DC_FLAG, param.getTcdcFlag());

			// ためうちに値を表示します。
			addList(viewParam);

			// ためうちのボタンを有効にします。
			setBtnTrueFalse(true);
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
				// コネクションのクローズを行う
				if (conn != null)
				{
					conn.rollback();
					conn.close();
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
	 * 概要:入力エリアをクリアします。<BR>
	 * <DIR>
	 * 		1.初期表示のスケジュールを開始します。
	 * 		2.入力エリアの項目をクリアします。 <BR>
	 * 		<DIR>
	 * 			作業者コード[そのまま] <BR>
	 * 			パスワード[そのまま] <BR>
	 * 			荷主コード[検索値] <BR>
	 * 			開始入荷予定日[クリア] <BR>
	 * 			終了入荷予定日[クリア] <BR>
	 * 			入荷予定削除一覧リスト発行する[true] <BR>
	 * 		</DIR>
	 * 		3.カーソルを作業者コードにセットします。<BR>
	 * 		4.ためうちエリアの内容は保持します。 <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 初期表示処理を呼びます。
		setInitView(true);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheck_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 全件選択ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:ためうちエリアの選択チェックボックスに全てチェックを入れます。<BR>
	 * <DIR>
	 * 		1.選択boxが有効のものにチェックを入れます。<BR>
	 * 		2.カーソルを作業者コードにセットします。<BR>
	 * 		3.入力エリアの内容は保持します。<BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_AllCheck_Click(ActionEvent e) throws Exception
	{
		listCellCheckAllChange(true);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_AllCheckClear_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 全選択解除ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの選択チェックボックスに全てチェックをクリアします。 <BR>
	 * <DIR>
	 * 		1.選択boxが有効のもののチェックをクリアします。 <BR>
	 * 		2.カーソルを作業者コードにセットします。 <BR>
	 * 		3.入力エリアの内容は保持します。 <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_AllCheckClear_Click(ActionEvent e) throws Exception
	{
		listCellCheckAllChange(false);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Delete_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 削除ボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの情報で、入荷予定情報の削除を行います。 <BR>
	 * <DIR>
	 * 		1.カーソルを作業者コードにセットします。 <BR>
	 * 		2.作業者コード、パスワードの入力チェックを行います。<BR>
	 * 		3.viewStateからＴＣ／ＤＣ区分、削除リスト発行区分を取得してパラメータにセットします。 <BR>
	 * 		4.選択チェックがチェックされているためうちエリアの情報をパラメータにセットします。<BR>
	 * 		5.スケジュールを開始します。 <BR>
	 * 		6.ためうちエリアに、更新後のスケジュールからの情報を再取込みし、表示します。 <BR>
	 * 		7.ためうち情報が存在しない場合、ためうちエリアの荷主をクリアし、全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンは無効にします。 <BR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Delete_Click(ActionEvent e) throws Exception
	{
		// 作業者コードにカーソルをセット
		setFocus(txt_WorkerCode);
		
		// 作業者コード,パスワードの入力チェックを行う。
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;

		try
		{
			// コネクションの取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			// 削除するデータがあるか調べる
			boolean wCheck = false;

			// ためうちの要素数分チェックをかけ、更新を行う行のみVectorに値をセットしていく。
			Vector vecParam = new Vector(lst_SInstkRcvPlanDelete.getMaxRows());
			for (int i = 1; i < lst_SInstkRcvPlanDelete.getMaxRows(); i++)
			{
				// 操作する行を取得します。
				lst_SInstkRcvPlanDelete.setCurrentRow(i);
				
				// 選択チェックをチェック
				if(!lst_SInstkRcvPlanDelete.getChecked(COL_SELECT))
				{
					// チェックがついていない行は何もしない
					continue;
				}
				else
				{
					wCheck = true;
				}
				
				InstockReceiveParameter param = new InstockReceiveParameter();

				// 作業者コード
				param.setWorkerCode(txt_WorkerCode.getText());
				// パスワードのセット
				param.setPassword(txt_Password.getText());
				// 出荷予定削除一覧リスト発行をするかをセット
				param.setInstockReceiveDeleteListFlg(chk_CommonUse.getChecked());

				// 再表示用の検索条件のセット(荷主、開始出荷予定日、終了出荷予定日)
				param.setConsignorCode(this.getViewState().getString(KEY_CONSIGNOR_CODE));
				param.setFromPlanDate(this.getViewState().getString(KEY_FROM_INSTOCK_PLAN_DATE));
				param.setToPlanDate(this.getViewState().getString(KEY_TO_INSTOCK_PLAN_DATE));
				param.setTcdcFlag(this.getViewState().getString(KEY_TC_DC_FLAG));

				// 選択チェックの有無
				param.setSelectBoxCheck(true);
				// 入荷予定日
				param.setPlanDate(WmsFormatter.toParamDate(lst_SInstkRcvPlanDelete.getValue(2), this.getHttpRequest().getLocale()));
				// ＴＣ／ＤＣをHIDDEN項目から取得
				String hidden = lst_SInstkRcvPlanDelete.getValue(0);
				param.setTcdcFlagL(CollectionUtils.getString(HIDDEN0, hidden));
				// 仕入先コード
				param.setSupplierCode(lst_SInstkRcvPlanDelete.getValue(4));
				// 仕入先名称
				param.setSupplierName(lst_SInstkRcvPlanDelete.getValue(5));
				// ためうち行NO
				param.setRowNo(i);

				vecParam.addElement(param);
			}

			// 選択ボックスのチェックが入っていない
			if (wCheck == false)
			{
				// 6023111 = 削除するデータをチェックしてください。
				message.setMsgResourceKey("6023111");
				return;
			}

			// パラメータの宣言
			InstockReceiveParameter[] paramArray = new InstockReceiveParameter[vecParam.size()];
			// パラメータに値をコピーします
			vecParam.copyInto(paramArray);

			// スケジュールスタート
			WmsScheduler schedule = new InstockReceivePlanDeleteSCH();

			InstockReceiveParameter[] viewParam =
				(InstockReceiveParameter[]) schedule.startSCHgetParams(conn, paramArray);

			// 返却データがnullならば更新に失敗。
			// ロールバックとメッセージのセットを行う。(ためうちは前データを表示したまま)
			if (viewParam == null)
			{
				// ロールバック
				conn.rollback();
				message.setMsgResourceKey(schedule.getMessage());
				return;
			}
			// 返却データの要素数が0以上ならば更新に成功。
			// トランザクションの確定とためうちの再表示を行う。
			else if (viewParam.length >= 0)
			{
				// コミット 
				conn.commit();

				// 表示するデータがない場合
				if (viewParam.length == 0)
				{
					// ためうち部をクリアします
					btn_ListClear_Click(e);
				}
				else
				{
					// スケジュールが正常に完了して表示データを取得した場合、表示します。
					lst_SInstkRcvPlanDelete.clearRow();
					// ためうちに再表示します
					addList(viewParam);
				}

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
				// コネクションのクローズを行う
				if (conn != null)
				{
					conn.rollback();
					conn.close();
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
	public void btn_ListClear_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 一覧クリアボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:ためうちエリアの表示をクリアするか、ダイアログボックスを表示し、確認します。<BR>
	 * <DIR>
	 * 		1.確認ダイアログボックスを表示します。"一覧入力情報がクリアされます。宜しいですか?" <BR>
	 * 		[確認ダイアログ OK] <BR>
	 * 		<DIR>
	 * 			1.リストセルを初期化します。 <BR>
	 * 			2.ためうちエリアの条件を消します。 <BR>
	 * 			3.全件選択ボタン・全選択解除ボタン・削除ボタン・一覧クリアボタンを無効にします。 <BR>
	 * 			4.カーソルを作業者コードにセットします。 <BR>
	 * 		</DIR>
	 * 		[確認ダイアログ キャンセル] <BR>
	 * 		<DIR>
	 * 			なにもしません <BR>
	 * 		</DIR>
	 * </DIR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ListClear_Click(ActionEvent e) throws Exception
	{
		// リストセルのクリア処理を呼び出します。
		listClear();
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_Consignor_Server(ActionEvent e) throws Exception
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
	public void lst_SInstkRcvPlanDelete_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvPlanDelete_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvPlanDelete_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvPlanDelete_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvPlanDelete_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvPlanDelete_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_SInstkRcvPlanDelete_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagTC_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_CrossDCFlagTC_Click(ActionEvent e) throws Exception
	{
	}


}
//end of class
