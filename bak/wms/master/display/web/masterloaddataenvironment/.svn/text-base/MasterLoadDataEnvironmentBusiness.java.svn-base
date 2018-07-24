// $Id: MasterLoadDataEnvironmentBusiness.java,v 1.1.1.1 2006/08/17 09:34:18 mori Exp $
package jp.co.daifuku.wms.master.display.web.masterloaddataenvironment;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

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
import jp.co.daifuku.wms.base.system.display.web.listbox.listfolderselect.ListFolderSelectBusiness;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.master.schedule.MasterLoadDataEnvironmentSCH;

/**
 * Designer : hota <BR>
 * Maker : hota <BR>
 * <BR>
 * マスタデータ取り込み（環境設定）クラスです。<BR>
 * 画面から入力した内容をパラメータにセットし、<BR>
 * そのパラメータを基にスケジュールが取り込みデータの格納フォルダとファイル名をEnvironmentInformationファイルに設定します。<BR>
 * スケジュールから結果を受け取り、処理が正常に完了した場合はtrueを、<BR>
 * 条件エラーなどでスケジュールが完了しなかった場合はfalseを受け取ります。<BR>
 * スケジュールの結果は、スケジュールから取得したメッセージを画面に出力します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.登録ボタン押下処理（<CODE>btn_Submit_Click()</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	画面から入力した内容をEnvironmentInformationファイルに設定します。<BR>
 * 	条件エラーなどが発生した場合はエラーメッセージを返します。<BR>
 * 	<DIR>
 * 		･ファイル名を ***ABC*** とした場合、前３桁、後３桁でABCが含まれるファイルを指定格納場所より取得します。<BR>
 * 	</DIR>
 * <BR>
 * 	[パラメータ] *必須入力<BR>
 * 	<BR>
 * 	<DIR>
 * 		作業者コード* <BR>
 * 		パスワード* <BR>
 * 		荷主マスタデータ格納フォルダ* <BR>
 * 		荷主マスタデータファイル名* <BR>
 * 		仕入先マスタデータ格納フォルダ* <BR>
 * 		仕入先マスタデータファイル名* <BR>
 * 		出荷先マスタデータ格納フォルダ* <BR>
 * 		出荷先マスタデータファイル名* <BR>
 * 		商品マスタデータ格納フォルダ* <BR>
 * 		商品マスタデータファイル名* <BR>
 * 	</DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:18 $
 * @author  $Author: mori $
 */
public class MasterLoadDataEnvironmentBusiness extends MasterLoadDataEnvironment implements WMSConstants
{
	// Class fields --------------------------------------------------

	/** 
	 * ボタンフラグ（荷主マスタ） 
	 */
	public static final String LOAD_CONSIGNOR = "11";

	/** 
	 * ボタンフラグ（仕入先マスタ）
	 */
	public static final String LOAD_SUPPLIER = "12";

	/** 
	 * ボタンフラグ（出荷先マスタ）
	 */
	public static final String LOAD_CUSTOMER = "13";

	/** 
	 * ボタンフラグ（商品マスタ）
	 */
	public static final String LOAD_ITEM = "14";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。<BR>
	 * <BR>
	 * 項目[初期値]<BR>
	 * <DIR>
	 * 	荷主マスタデータ格納フォルダ	[EnvironmentInformationより取得] <BR>
	 * 	荷主マスタデータファイル名		[EnvironmentInformationより取得] <BR>
	 * 	仕入先マスタデータ格納フォルダ	[EnvironmentInformationより取得] <BR>
	 * 	仕入先マスタデータファイル名	[EnvironmentInformationより取得] <BR>
	 * 	出荷先マスタデータ格納フォルダ	[EnvironmentInformationより取得] <BR>
	 * 	出荷先マスタデータファイル名	[EnvironmentInformationより取得] <BR>
	 * 	商品マスタデータ格納フォルダ	[EnvironmentInformationより取得] <BR>
	 * 	商品マスタデータファイル名		[EnvironmentInformationより取得] <BR>
	 * </DIR>
	 * 1.カーソルを作業者コードにセットします。<BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 初期表示を行う
		StartDisp();

	}

	/**
	 * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
	 * <BR>
	 * 概要:このメソッドでは以下の処理を行います<BR>
	 * <BR>
	 * <DIR>
	 *		登録ボタンが押下されたときダイアログを表示する。 <BR>
	 * </DIR>
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
		
		// 登録確認ダイアログ表示
		btn_Submit.setBeforeConfirm("MSG-W0009");
	}
	
	/**
	 * pageに定義されているpage_DlgBackをオーバライドします。 <BR>
	 *「検索」ボタンを押して、戻ってくるときにこのメソッドが呼ばれます。 <BR>
	 * <BR>
	 * 概要:リストボックスで決定した取り込みディレクトリを反映します。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_DlgBack(ActionEvent e) throws Exception
	{
		DialogParameters param = ((DialogEvent) e).getDialogParameters();
		// リストボックスから選択されたパラメータを取得
		String folder = param.getParameter(ListFolderSelectBusiness.FOLDER_KEY);
		String btnflug = param.getParameter(ListFolderSelectBusiness.BTNFLUG_KEY);

		if (btnflug != null && !btnflug.equals(""))
		{
			if (btnflug.equals(LOAD_CONSIGNOR))
			{
				// 荷主マスタデータ格納フォルダが空ではないときに値をセットする
				if (!StringUtil.isBlank(folder))
				{
				    txt_MstConsignorFld.setText(folder);
				}
			}
			else if (btnflug.equals(LOAD_SUPPLIER))
			{
				// 仕入先マスタデータ格納フォルダが空ではないときに値をセットする
				if (!StringUtil.isBlank(folder))
				{
				    txt_MstSupplierFld.setText(folder);
				}
			}
			else if (btnflug.equals(LOAD_CUSTOMER))
			{
				// 出荷先マスタデータ格納フォルダが空ではないときに値をセットする
				if (!StringUtil.isBlank(folder))
				{
				    txt_MstCustomerFld.setText(folder);
				}
			}
			else if (btnflug.equals(LOAD_ITEM))
			{
				// 商品マスタデータ格納フォルダが空ではないときに値をセットする
				if (!StringUtil.isBlank(folder))
				{
				    txt_MstItemFld.setText(folder);
				}
			}

		}
		
		//フォーカスを作業者コードにセットする 
		setFocus(txt_WorkerCode);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

	/**
	 * 画面の初期表示を行います。<BR>
	 * <BR>
	 * 概要:インストールパッケージのみ設定可能とします。<BR>
	 * <BR>
	 * <DIR>
	 *		インストールパッケージの項目を設定可能とし、<BR>
	 *		各パッケージの取り込みディレクトリをファイルより読み出します。<BR>
	 * </DIR>
	 * @throws Exception 全ての例外を報告します。
	 */
	private void StartDisp() throws Exception
	{
		// フォーカスを作業者コードにセットする 
		setFocus(txt_WorkerCode);
		Connection conn = null;
		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);

			WmsScheduler schedule = new MasterLoadDataEnvironmentSCH();

			// 初期表示用の環境情報を読み込む
			SystemParameter param = (SystemParameter) schedule.initFind(conn, null);

			// パッケージが入っている項目だけ入力可能にします
			// 荷主マスタデータ

				// 荷主マスタデータ格納フォルダ
			txt_MstConsignorFld.setText(param.getFolder_LoadMasterConsignorData());
				// 荷主マスタデータファイル名
			txt_MstConsignorFileNm.setText(param.getFileName_LoadMasterConsignorData());

			// 仕入先マスタデータ

				// 仕入先マスタデータ格納フォルダ
			txt_MstSupplierFld.setText(param.getFolder_LoadMasterSupplierData());
				// 仕入先マスタデータファイル名
			txt_MstSupplierFileNm.setText(param.getFileName_LoadMasterSupplierData());

			// 出荷先マスタデータ

				// 出荷先マスタデータ格納フォルダ
			txt_MstCustomerFld.setText(param.getFolder_LoadMasterCustomerData());
				// 出荷先マスタデータファイル名
			txt_MstCustomerFileNm.setText(param.getFileName_LoadMasterCustomerData());

			// 商品マスタデータ

				// 商品マスタデータ格納フォルダ
			txt_MstItemFld.setText(param.getFolder_LoadMasterItemjData());
				// 商品マスタデータファイル名
			txt_MstItemFileNm.setText(param.getFileName_LoadMasterItemData());

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
	public void tab_Set_Click(ActionEvent e) throws Exception
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
	 * メニューボタンを押下されたときに呼ばれます。<BR>  
	 * 概要:メニュー画面に遷移します。<BR>
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
	public void lbl_MstConsignorDataFld_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstConsignorFld_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstConsignorFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstConsignorFld_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstConsignorFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceConsignorMaster_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 荷主マスタデータ格納フォルダの参照ボタンが押下されたときに呼ばれます。<BR>
	 * 概要:荷主マスタデータ格納フォルダの一覧表示を行い、格納するフォルダを選択します。<BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ReferenceConsignorMaster_Click(ActionEvent e) throws Exception
	{
		// 次画面へ渡すパラメータをセット
		ForwardParameters param = new ForwardParameters();
		// 入荷予定データ格納フォルダ
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_MstConsignorFld.getText());
		// 「参照」ボタンフラグ
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, LOAD_CONSIGNOR);

		// 処理中画面->結果画面
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MstConsignorDataFileNm_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstConsignorFileNm_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstConsignorFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstConsignorFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstConsignorFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MstSupplierDataFld_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstSupplierFld_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstSupplierFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstSupplierFld_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstSupplierFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceSupplierMaster_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕入先マスタデータ格納フォルダの参照ボタンが押下されたときに呼ばれます。<BR>
	 * 概要:仕入先マスタデータ格納フォルダの一覧表示を行い、格納するフォルダを選択します。<BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ReferenceSupplierMaster_Click(ActionEvent e) throws Exception
	{
		// 次画面へ渡すパラメータをセット
		ForwardParameters param = new ForwardParameters();
		// 入庫予定データ格納フォルダ
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_MstSupplierFld.getText());
		// 「参照」ボタンフラグ
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, LOAD_SUPPLIER);

		// 処理中画面->結果画面
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void W_MstSupplierDataFileNm_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstSupplierFileNm_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstSupplierFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstSupplierFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstSupplierFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MstCustomerDataFld_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstCustomerFld_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstCustomerFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstCustomerFld_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstCustomerFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceCustomerMaster_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 出荷先マスタデータ格納フォルダの参照ボタンが押下されたときに呼ばれます。<BR>
	 * 概要:出荷先マスタデータ格納フォルダの一覧表示を行い、格納するフォルダを選択します。<BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ReferenceCustomerMaster_Click(ActionEvent e) throws Exception
	{
		// 次画面へ渡すパラメータをセット
		ForwardParameters param = new ForwardParameters();
		// 出庫予定データ格納フォルダ
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_MstCustomerFld.getText());
		// 「参照」ボタンフラグ
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, LOAD_CUSTOMER);

		// 処理中画面->結果画面
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MstCustmoerDataFileNm_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstCustomerFileNm_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstCustomerFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstCustomerFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstCustomerFileNm_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MstItemDataFld_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstItemFld_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstItemFld_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstItemFld_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstItemFld_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_ReferenceItemMaster_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 商品マスタデータ格納フォルダの参照ボタンが押下されたときに呼ばれます。<BR>
	 * 概要:商品マスタデータ格納フォルダの一覧表示を行い、格納するフォルダを選択します。<BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_ReferenceItemMaster_Click(ActionEvent e) throws Exception
	{
		// 次画面へ渡すパラメータをセット
		ForwardParameters param = new ForwardParameters();
		// 仕分予定データ格納フォルダ
		param.setParameter(ListFolderSelectBusiness.FOLDER_KEY, txt_MstItemFld.getText());
		// 「参照」ボタンフラグ
		param.setParameter(ListFolderSelectBusiness.BTNFLUG_KEY, LOAD_ITEM);

		// 処理中画面->結果画面
		redirect("/system/listbox/listfolderselect/ListFolderSelect.do", param, "/progress.do");
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_MstItemDataFileNm_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstItemFileNm_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstItemFileNm_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstItemFileNm_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_MstItemFileNm_InputComplete(ActionEvent e) throws Exception
	{
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
	 * 登録ボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 概要:環境設定を行うかを、ダイアログボックスを表示し、確認します。<BR>
	 * <BR>
	 * <DIR>
	 * 	1.カーソルを作業者コードにセットします。 <BR>
	 * <BR> 
	 * 	2.登録確認ダイアログボックスを表示します。"登録しますか？"<BR>
	 * 	<DIR>
	 * 		[確認ダイアログ キャンセル]<BR>
	 * 		<DIR>
	 * 			なにも行いません。<BR>
	 * 		</DIR>
	 * 		[確認ダイアログ OK]<BR>
	 * 		<DIR>
	 * 		概要:入力エリア情報で環境設定を行います。<BR>
	 * 			1.入力エリアの入力項目のチェックを行います。（必須入力、文字数、文字属性）<BR>
	 * 			2.スケジュールを開始する。<BR>
	 * 				<DIR>
	 * 					･導入されているパッケージの格納フォルダ、ファイル名を更新します。<BR>
	 * 					 存在しないフォルダの場合エラー<BR>
	 * 					 メッセージを表示する："正しいフォルダを指定してください"<BR>
	 * 					･EnvironmentInformationを更新します。<BR>
	 * 				</DIR>
	 * 			3.入力エリアの内容は保持します。<BR>
	 * 			
	 * 		</DIR>
	 * 	</DIR>	
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Submit_Click(ActionEvent e) throws Exception
	{
		// フォーカスを作業者コードにセットする 
		setFocus(txt_WorkerCode);
		// 入力チェック
		txt_WorkerCode.validate();
		txt_Password.validate();

		Connection conn = null;
		try
		{
			// コネクション取得
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);


			WmsScheduler schedule = new MasterLoadDataEnvironmentSCH();

			// 初期表示用の環境情報を読み込む
			SystemParameter sysparam = (SystemParameter) schedule.initFind(conn, null);

		    txt_MstConsignorFld.validate();
		    txt_MstConsignorFileNm.validate();

		    txt_MstSupplierFld.validate();
		    txt_MstSupplierFileNm.validate();

		    txt_MstCustomerFld.validate();
		    txt_MstCustomerFileNm.validate();

		    txt_MstItemFld.validate();
		    txt_MstItemFileNm.validate();



			Vector vecParam = new Vector(1);

			SystemParameter param = new SystemParameter();

			// 作業者コード
			param.setWorkerCode(txt_WorkerCode.getText());
			// パスワード
			param.setPassword(txt_Password.getText());
			// 荷主マスタデータ格納フォルダ
			param.setFolder_LoadMasterConsignorData(txt_MstConsignorFld.getText());
			// 荷主マスタデータファイル名
			param.setFileName_LoadMasterConsignorData(txt_MstConsignorFileNm.getText());
			// 仕入先マスタデータ格納フォルダ
			param.setFolder_LoadMasterSupplierData(txt_MstSupplierFld.getText());
			// 仕入先マスタファイル名
			param.setFileName_LoadMasterSupplierData(txt_MstSupplierFileNm.getText());
			// 出荷先マスタデータ格納フォルダ
			param.setFolder_LoadMasterCustomerData(txt_MstCustomerFld.getText());
			// 出荷先マスタデータファイル名
			param.setFileName_LoadMasterCustomerData(txt_MstCustomerFileNm.getText());
			// 商品マスタデータ格納フォルダ
			param.setFolder_LoadMasterItemjData(txt_MstItemFld.getText());
			// 商品マスタデータファイル名
			param.setFileName_LoadMasterItemData(txt_MstItemFileNm.getText());

			vecParam.addElement(param);

			SystemParameter[] paramArray = new SystemParameter[vecParam.size()];
			vecParam.copyInto(paramArray);

			// スケジュールスタート
			if (schedule.startSCH(conn, paramArray))
			{
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
			}
			else
			{
				// メッセージをセット
				message.setMsgResourceKey(schedule.getMessage());
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
	 * クリアボタンが押下されたときに呼ばれます。
	 * <BR>
	 * 概要:入力情報を初期表示（Environment Informationより取得した状態）に戻します。<BR>
	 * 作業者コードとパスワードのクリアは行いません。<BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 初期表示処理を行う
		StartDisp();
	}


}
//end of class
