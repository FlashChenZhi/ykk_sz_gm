// $Id: MasterDefineLoadDataMenuBusiness.java,v 1.1.1.1 2006/08/17 09:34:17 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.masterdefineloaddatamenu;

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.common.WmsScheduler;
import jp.co.daifuku.wms.base.display.web.BusinessClassHelper;
import jp.co.daifuku.wms.base.display.web.ExceptionHandler;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.master.schedule.MasterDefineLoadDataMenuSCH;


/**
 * Designer : hota <BR>
 * Maker : hota <BR>
 * <BR>
 * マスタデータ取り込み(データ項目設定)画面クラスです。 <BR>
 * 入力エリアから入力した内容をパラメータにセットしスケジュールに渡し、２画面目に遷移します。 <BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、条件エラーなどで <BR>
 * スケジュールが完了しなかった場合はfalseを受け取ります。 <BR>
 * スケジュールの結果、スケジュールから取得したメッセージを画面に出力します。 <BR>
 *
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 次へボタン押下処理（<CODE>btn_Next_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 *   入力エリア情報で遷移を行います。 <BR>
 * <BR>
 *   [パラメータ] *必須入力<BR>
 * <BR>
 * <DIR>
 *   作業者コード* <BR>
 *   パスワード* <BR>
 * </DIR>
 * <BR>
 *   [パラメータ]+1つ以上必須選択 <BR>
 * <BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:17 $
 * @author  $Author: mori $
 */
public class MasterDefineLoadDataMenuBusiness extends MasterDefineLoadDataMenu implements WMSConstants
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * カーソルを作業者コードに初期セットします。 <BR>
	 * <DIR>
	 *   項目[初期値] <BR>
	 *   <DIR>
	 *     荷主マスタデータ[選択] <BR>
	 *     仕入先マスタデータ[選択なし] <BR>
	 *     出荷先マスタデータ[選択なし] <BR>
	 *     商品マスタデータ[選択なし] <BR>
	 *   </DIR>
	 *   <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// フォーカスを作業者コードに初期セットします
		setFocus(txt_WorkerCode);

		// 2画面目から戻った時タイトルをセット
		if (!StringUtil.isBlank(getViewState().getString("TITLE")))
		{
			lbl_SettingName.setResourceKey(getViewState().getString("TITLE"));
		}
		// タブの左側を浮き上がるようにセットします
		tab_LodClm_Set.setSelectedIndex(1);

		// 1画面目でセットした作業者コードをViewStateから取得
		if (!StringUtil.isBlank(getViewState().getString("WORKERCODE")))
		{
			txt_WorkerCode.setText(getViewState().getString("WORKERCODE"));
		}
		// 1画面目でセットしたパスワードをViewStateから取得
		if (!StringUtil.isBlank(getViewState().getString("PASSWORD")))
		{
			txt_Password.setText(getViewState().getString("PASSWORD"));
		}
		// 1画面目でセットした選択項目ををViewStateから取得
		if (!StringUtil.isBlank(getViewState().getString("SELECT")))
		{
			if (getViewState()
				.getString("SELECT")
				.equals(SystemParameter.SELECTDEFINELOADDATA_CONSIGNOR))
			{
				rdo_MstConsignorData.setChecked(true);
				rdo_Mst_SupplierData.setChecked(false);
				rdo_MstCustomerData.setChecked(false);
				rdo_MstItemData.setChecked(false);
			}
			else if (
				getViewState().getString("SELECT").equals(
					SystemParameter.SELECTDEFINELOADDATA_SUPPLIER))
			{
				rdo_MstConsignorData.setChecked(false);
				rdo_Mst_SupplierData.setChecked(true);
				rdo_MstCustomerData.setChecked(false);
				rdo_MstItemData.setChecked(false);
			}
			else if (
				getViewState().getString("SELECT").equals(
					SystemParameter.SELECTDEFINELOADDATA_CUSTOMER))
			{
				rdo_MstConsignorData.setChecked(false);
				rdo_Mst_SupplierData.setChecked(false);
				rdo_MstCustomerData.setChecked(true);
				rdo_MstItemData.setChecked(false);
			}
			else if (
				getViewState().getString("SELECT").equals(
					SystemParameter.SELECTDEFINELOADDATA_ITEM))
			{
				rdo_MstConsignorData.setChecked(false);
				rdo_Mst_SupplierData.setChecked(false);
				rdo_MstCustomerData.setChecked(false);
				rdo_MstItemData.setChecked(true);
			}
		}
		else
		{
			rdo_MstConsignorData.setChecked(true);
			rdo_Mst_SupplierData.setChecked(false);
			rdo_MstCustomerData.setChecked(false);
			rdo_MstItemData.setChecked(false);
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

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

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
	public void tab_LodClm_Set_Click(ActionEvent e) throws Exception
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
	 * メニューボタンが押下された時に呼ばれます。 <BR>
	 * <BR>
	 * メニュー画面に遷移します。<BR>
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
	public void rdo_MstConsignorData_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_MstConsignorData_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Mst_SupplierData_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_Mst_SupplierData_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_MstCustomerData_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_MstCustomerData_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_MstItemData_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void rdo_MstItemData_Click(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 次へボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要:入力エリアの入力項目より設定画面に遷移する。 <BR>
	 * <BR>
	 * 1.カーソルを作業者コードに初期セットします。 <BR>
	 * 2.入力エリアの入力項目のチェックを行います。(必須入力,文字数,文字属性) <BR>
	 * 3.次画面（設定画面）に遷移します。 <BR>
	 * 4.入力エリアの内容は保持します。 <BR>
	 * <DIR>
	 *	パラメタが正常であるかの精査を行い、各パッケージの設定画面へ遷移します。<BR>
	 *	<DIR>
	 *  	[パラメータ]<BR>
	 * 		<DIR>
	 * 		作業者コード<BR>
	 * 		パスワード<BR>
	 * 		※各マスタパッケージを識別する定数<BR>
	 *		</DIR>
	 *	</DIR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		// フォーカスを作業者コードに初期セットします
		setFocus(txt_WorkerCode);

		// 入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;
		try
		{
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			SystemParameter param = new SystemParameter();

			// コネクションの取得

			WmsScheduler schedule = new MasterDefineLoadDataMenuSCH();

			// パラメータに値をセットします
			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());

			// 荷主マスタデータ
			if (rdo_MstConsignorData.getChecked())
			{
				param.setSelectDefineLoadData(SystemParameter.SELECTDEFINELOADDATA_CONSIGNOR);
			}
			// 仕入先マスタデータ
			if (rdo_Mst_SupplierData.getChecked())
			{
				param.setSelectDefineLoadData(SystemParameter.SELECTDEFINELOADDATA_SUPPLIER);
			}
			// 出荷先マスタデータ
			if (rdo_MstCustomerData.getChecked())
			{
				param.setSelectDefineLoadData(SystemParameter.SELECTDEFINELOADDATA_CUSTOMER);
			}
			// 商品マスタデータ
			if (rdo_MstItemData.getChecked())
			{
				param.setSelectDefineLoadData(SystemParameter.SELECTDEFINELOADDATA_ITEM);
			}

			if (schedule.nextCheck(conn, param))
			{
				// 入荷予定データ
				if (rdo_MstConsignorData.getChecked())
				{
					forward("/master/masterdefineloaddatamenu/MasterDefineLoadDataMenuConsignor.do");
				}
				// 入庫予定データ
				else if (rdo_Mst_SupplierData.getChecked())
				{
					forward("/master/masterdefineloaddatamenu/MasterDefineLoadDataMenuSupplier.do");
				}
				// 出庫予定データ
				else if (rdo_MstCustomerData.getChecked())
				{
					forward("/master/masterdefineloaddatamenu/MasterDefineLoadDataMenuCustomer.do");
				}
				// 仕分予定データ
				else if (rdo_MstItemData.getChecked())
				{
					forward("/master/masterdefineloaddatamenu/MasterDefineLoadDataMenuItem.do");
				}

				// 画面で入力した作業者コードをViewStateへ保存
				getViewState().setString("WORKERCODE", txt_WorkerCode.getText());
				// 画面で入力したパスワードをViewStateへ保存
				getViewState().setString("PASSWORD", txt_Password.getText());
				// 画面で選択した項目をViewStateへ保存
				getViewState().setString("SELECT", param.getSelectDefineLoadData());
				// 画面のタイトルをViewStateへ保存
				getViewState().setString("TITLE", lbl_SettingName.getResourceKey());
			}
			else
			{
				if (schedule.getMessage() != null)
				{
					// メッセージを表示します
					message.setMsgResourceKey(schedule.getMessage());
				}
			}
		}
		catch (Exception ex)
		{
			message.setMsgResourceKey(ExceptionHandler.getDisplayMessage(ex, this.getClass()));
		}
		finally
		{
			// フォーカスを作業者コードに初期セットします
			setFocus(txt_WorkerCode);
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
	 * クリアボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:入力エリアをクリアします。<BR>
	 * <BR>
	 * <DIR>
	 *   入力エリアの作業者コードとパスワード以外の項目を初期値に戻します。<BR>
	 *   <DIR>
	 *     初期値については<CODE>page_Load(ActionEvent e)</CODE>を参照してください。 <BR>
	 *   </DIR>
	 *   カーソルを作業者コードに初期セットします。 <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// フォーカスを作業者コードに初期セットします
		setFocus(txt_WorkerCode);

		// 2画面目から戻った時タイトルをセット
		if (!StringUtil.isBlank(getViewState().getString("TITLE")))
		{
			lbl_SettingName.setResourceKey(getViewState().getString("TITLE"));
		}
		// タブの左側を浮き上がるようにセットします
		tab_LodClm_Set.setSelectedIndex(1);

		// 1画面目でセットした作業者コードをViewStateから取得
		if (!StringUtil.isBlank(getViewState().getString("WORKERCODE")))
		{
			txt_WorkerCode.setText(getViewState().getString("WORKERCODE"));
		}
		// 1画面目でセットしたパスワードをViewStateから取得
		if (!StringUtil.isBlank(getViewState().getString("PASSWORD")))
		{
			txt_Password.setText(getViewState().getString("PASSWORD"));
		}
		// 1画面目でセットした選択項目ををViewStateから取得
		if (!StringUtil.isBlank(getViewState().getString("SELECT")))
		{
			if (getViewState()
				.getString("SELECT")
				.equals(SystemParameter.SELECTDEFINELOADDATA_CONSIGNOR))
			{
				rdo_MstConsignorData.setChecked(true);
				rdo_Mst_SupplierData.setChecked(false);
				rdo_MstCustomerData.setChecked(false);
				rdo_MstItemData.setChecked(false);
			}
			else if (
				getViewState().getString("SELECT").equals(
					SystemParameter.SELECTDEFINELOADDATA_SUPPLIER))
			{
				rdo_MstConsignorData.setChecked(false);
				rdo_Mst_SupplierData.setChecked(true);
				rdo_MstCustomerData.setChecked(false);
				rdo_MstItemData.setChecked(false);
			}
			else if (
				getViewState().getString("SELECT").equals(
					SystemParameter.SELECTDEFINELOADDATA_CUSTOMER))
			{
				rdo_MstConsignorData.setChecked(false);
				rdo_Mst_SupplierData.setChecked(false);
				rdo_MstCustomerData.setChecked(true);
				rdo_MstItemData.setChecked(false);
			}
			else if (
				getViewState().getString("SELECT").equals(
					SystemParameter.SELECTDEFINELOADDATA_ITEM))
			{
				rdo_MstConsignorData.setChecked(false);
				rdo_Mst_SupplierData.setChecked(false);
				rdo_MstCustomerData.setChecked(false);
				rdo_MstItemData.setChecked(true);
			}
		}
		else
		{
			rdo_MstConsignorData.setChecked(true);
			rdo_Mst_SupplierData.setChecked(false);
			rdo_MstCustomerData.setChecked(false);
			rdo_MstItemData.setChecked(false);
		}
	}

}
//end of class
