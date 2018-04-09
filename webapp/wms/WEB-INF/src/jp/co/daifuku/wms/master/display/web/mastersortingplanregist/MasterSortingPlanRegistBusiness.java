// $Id: MasterSortingPlanRegistBusiness.java,v 1.1.1.1 2006/08/17 09:34:18 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.mastersortingplanregist;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

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
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.master.display.web.listbox.listmasteritem.ListMasterItemBusiness;
import jp.co.daifuku.wms.master.schedule.MasterSortingParameter;
import jp.co.daifuku.wms.master.schedule.MasterSortingPlanRegistSCH;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingconsignor.ListSortingConsignorBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingdate.ListSortingDateBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingitem.ListSortingItemBusiness;
import jp.co.daifuku.wms.sorting.display.web.listbox.listsortingplanregist.ListSortingPlanRegistBusiness;
import jp.co.daifuku.wms.sorting.schedule.SortingParameter;

/**
 * Designer : S.Ishigane<BR>
 * Maker    : T.Uehata <BR>
 * <BR>
 * 仕分予定登録除画面クラスです。 <BR>
 * 仕分予定登録を行うスケジュールにパラメータを引き渡します。 <BR>
 * また、トランザクションのコミット・ロールバックは本画面で行います。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * <BR>
 * 1.次へボタン押下処理( <CODE>btn_Next_Click()</CODE> メソッド) <BR>
 * <BR>
 * <DIR>
 * 画面から入力した内容をパラメータの入力チェックをします。 <BR>
 * そのパラメータをviewStateに格納し、スケジュールに引き渡します。 <BR>
 * スケジュールから受け取った値とパラメータを元に仕分予定登録(詳細情報)画面へ遷移します。 <BR>
 * <BR>
 * [ViewStateパラメータ] *必須入力 <BR>
 * <BR>
 * 作業者コード* <BR>
 * パスワード* <BR>
 * 荷主コード* <BR>
 * 荷主名称 <BR>
 * 仕分予定日* <BR>
 * 商品コード* <BR>
 * 商品名称 <BR>
 * ケース/ピース区分* <BR>
 * ケース入数* <BR>
 * ケースITF <BR>
 * ボール入数 <BR>
 * ボールITF <BR>
 * <BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>N.Sawa(DFK)</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:18 $
 * @author  $Author: mori $
 */
public class MasterSortingPlanRegistBusiness extends MasterSortingPlanRegist implements WMSConstants
{

	// 遷移先アドレス
	// 荷主検索ポップアップアドレス
	private static final String DO_SRCH_CONSIGNOR = "/master/listbox/listmasterconsignor/ListMasterConsignor.do";
	// 仕分予定日検索ポップアップアドレス
	private static final String DO_SRCH_SORTINGPLANDATE = "/sorting/listbox/listsortingdate/ListSortingDate.do";
	// 商品一覧検索ポップアップアドレス
	private static final String DO_SRCH_ITEM = "/master/listbox/listmasteritem/ListMasterItem.do";
	// 仕分予定検索ポップアップアドレス
	private static final String DO_SRCH_SORTINGPLAN = "/sorting/listbox/listsortingplanregist/ListSortingPlanRegist.do";
	// 仕分予定登録(基本情報設定画面)アドレス
	protected static final String DO_REGIST = "/master/mastersortingplanregist/MasterSortingPlanRegist.do";
	// 仕分予定登録(詳細情報登録画面)アドレス
	private static final String DO_REGIST2 = "/master/mastersortingplanregist/MasterSortingPlanRegist2.do";
	// 検索ポップアップ呼び出し中画面アドレス
	protected static final String DO_SRCH_PROCESS = "/progress.do";

	// 作業者コード
	protected static final String WORKERCODE = "WORKERCODE";
	// パスワード
	protected static final String PASSWORD = "PASSWORD";
	// メッセージ
	protected static final String MESSAGE = "MESSAGE";

	// ケース/ピース区分(コード)
	protected static final String CASEPIECEFLG = "CASEPIECEFLG";
	// ケース/ピース区分(名称)
	protected static final String CASEPIECEFLGNM = "CASEPIECEFLGNM";
	// ケース入数
	protected static final String CASEQTY = "CASEQTY";
	// ボール入数
	protected static final String BUNDLEQTY = "BUNDLEQTY";
	// ケースITF
	protected static final String CASEITF = "CASEITF";
	// ボールITF
	protected static final String BUNDLEITF = "BUNDLEITF";
	// タイトル
	protected static final String TITLE = "TITLE";

	/**
	 * 読取専用フラグを保持するViewState用キーです
	 */
	public static final String IS_READ_ONLY_KEY = "IS_READ_ONLY";
	
	/**
	 * 荷主コードの最終使用日を保持するViewState用キーです
	 */
	public static final String CONSIG_DATE_KEY = "CONSIG_DATE";
	
	/**
	 * 商品コードの最終使用日を保持するViewState用キーです
	 */
	public static final String ITEM_DATE_KEY = "ITEM_DATE";
	
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * 画面の初期化を行います。 <BR>
	 * <BR>
	 * 概要:画面の初期表示を行います。 <BR>
	 * <DIR>
	 * 項目名[初期値] <BR>
	 * <DIR>
	 * 作業者コード [なし] <BR>
	 * パスワード [なし] <BR>
	 * 荷主コード [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 荷主名称 [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 仕分予定日 [なし] <BR>
	 * 商品コード [なし] <BR>
	 * 商品名称 [なし] <BR>
	 * ケース/ピース区分 ["全て"選択] <BR>
	 * ケース入数 [なし] <BR>
	 * ケースITF [なし] <BR>
	 * ボール入数 [なし] <BR>
	 * ボールITF [なし] <BR>
	 * </DIR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 2画面目から戻ってきた時にタイトルをセットする
		if (!StringUtil.isBlank(this.viewState.getString(TITLE)))
		{
			lbl_SettingName.setResourceKey(this.viewState.getString(TITLE));
		}

		// 画面を初期化します
		setInitView(true);

		// 基本情報設定のタブを前出しします
		tab_BscDtlRst.setSelectedIndex(1);

		// 仕分予定登録(詳細).戻るボタン押下時、
		// ViewStateに値がセットされていれば、その値をセットします
		// 作業者コード
		if (!StringUtil.isBlank(this.viewState.getString(WORKERCODE)))
		{
			txt_WorkerCode.setText(this.viewState.getString(WORKERCODE));
		}
		// パスワード
		if (!StringUtil.isBlank(this.getViewState().getString(PASSWORD)))
		{
			txt_Password.setText(this.getViewState().getString(PASSWORD));
		}
		// 荷主コード
		if (!StringUtil.isBlank(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORCODE_KEY)))
		{
			txt_ConsignorCode.setText(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORCODE_KEY));
		}
		// 荷主名称
		if (!StringUtil.isBlank(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORNAME_KEY)))
		{
			txt_ConsignorName.setText(this.getViewState().getString(ListSortingConsignorBusiness.CONSIGNORNAME_KEY));
		}
		// 仕分予定日
		if (!StringUtil.isBlank(this.getViewState().getString(ListSortingDateBusiness.SORTINGPLANDATE_KEY)))
		{
			txt_PickingPlanDate.setDate(WmsFormatter.toDate(this.getViewState().getString(ListSortingDateBusiness.SORTINGPLANDATE_KEY)));
		}
		// 商品コード
		if (!StringUtil.isBlank(this.getViewState().getString(ListSortingItemBusiness.ITEMCODE_KEY)))
		{
			txt_ItemCode.setText(this.getViewState().getString(ListSortingItemBusiness.ITEMCODE_KEY));
		}
		// 商品名称
		if (!StringUtil.isBlank(this.getViewState().getString(ListSortingItemBusiness.ITEMNAME_KEY)))
		{
			txt_ItemName.setText(this.getViewState().getString(ListSortingItemBusiness.ITEMNAME_KEY));
		}
		// ケース入数
		if (!StringUtil.isBlank(this.getViewState().getString(CASEQTY)))
		{
			txt_CaseEntering.setInt(this.getViewState().getInt(CASEQTY));
		}
		// ボール入数
		if (!StringUtil.isBlank(this.getViewState().getString(BUNDLEQTY)))
		{
			txt_BundleEntering.setInt(this.getViewState().getInt(BUNDLEQTY));
		}
		// ケースITF
		if (!StringUtil.isBlank(this.getViewState().getString(CASEITF)))
		{
			txt_CaseItf.setText(this.getViewState().getString(CASEITF));
		}
		// ボールITF
		if (!StringUtil.isBlank(this.getViewState().getString(BUNDLEITF)))
		{
			txt_BundleItf.setText(this.getViewState().getString(BUNDLEITF));
		}
	}

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
		
		// フォーカスを作業者コードにセットする
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
		String consignorcode = param.getParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY);
		// 荷主名称
		String consignorname = param.getParameter(ListSortingConsignorBusiness.CONSIGNORNAME_KEY);
		// 仕分予定日
		Date pickingplandate = WmsFormatter.toDate(param.getParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY), this.getHttpRequest().getLocale());
		// 商品コード
		String itemcode = param.getParameter(ListSortingItemBusiness.ITEMCODE_KEY);
		// 商品名称
		String itemname = param.getParameter(ListSortingItemBusiness.ITEMNAME_KEY);
		// ケース入数
		String enteringQty = param.getParameter(ListMasterItemBusiness.ENTERING_KEY); 
		// ボール入数
		String bundleEnteringQty = param.getParameter(ListMasterItemBusiness.BUNDLEENTERING_KEY);
		// ケースITF
		String itf = param.getParameter(ListSortingPlanRegistBusiness.ITF_KEY);
		// ボールITF
		String bundleItf = param.getParameter(ListSortingPlanRegistBusiness.BUNDLEITF_KEY);
		

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
		// 仕分予定日
		if (!StringUtil.isBlank(pickingplandate))
		{
			txt_PickingPlanDate.setDate(pickingplandate);
		}
		// 商品コード
		if (!StringUtil.isBlank(itemcode))
		{
			txt_ItemCode.setText(itemcode);
		}
		// 商品名称
		if (!StringUtil.isBlank(itemname))
		{
			txt_ItemName.setText(itemname);
		}
		// ケース入数
		if (!StringUtil.isBlank(enteringQty))
		{
			txt_CaseEntering.setInt(WmsFormatter.getInt(enteringQty));
		}
		// ボール入数
		if (!StringUtil.isBlank(bundleEnteringQty))
		{
			txt_BundleEntering.setInt(WmsFormatter.getInt(bundleEnteringQty));
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

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

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
	 * 荷主名称 [該当荷主が1件しかない場合初期表示を行う] <BR>
	 * 仕分予定日 [なし] <BR>
	 * 商品コード [なし] <BR>
	 * 商品名称 [なし] <BR>
	 * ケース/ピース区分 ["全て"選択] <BR>
	 * ケース入数 [なし] <BR>
	 * ケースITF [なし] <BR>
	 * ボール入数 [なし] <BR>
	 * ボールITF [なし] <BR>
	 * <BR>
	 * </DIR>
	 * </DIR>
	 * </DIR>
	 * @throws Exception
	 *             全ての例外を報告します。
	 */
	private void setInitView(boolean wkrClrFlg) throws Exception
	{
		// 作業者ｺｰﾄﾞにフォーカス異動
		setFocus(txt_WorkerCode);

		// 入力項目の初期化を行う
		if (wkrClrFlg)
		{
			// 作業者コード
			txt_WorkerCode.setText("");
			// パスワード
			txt_Password.setText("");
		}
		// 仕分予定日
		txt_PickingPlanDate.setText("");
		// 商品コード
		txt_ItemCode.setText("");
		// 商品名称
		txt_ItemName.setText("");
		// ケース入数
		txt_CaseEntering.setText("");
		// ボール入数
		txt_BundleEntering.setText("");
		// ケースITF
		txt_CaseItf.setText("");
		// ボールITF
		txt_BundleItf.setText("");
		// 荷主コード、荷主名称
		setConsignor();

		if (!StringUtil.isBlank(this.getViewState().getString(MESSAGE)))
		{
			message.setMsgResourceKey(this.getViewState().getString(MESSAGE));
		}
	}

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
			MasterSortingParameter param = new MasterSortingParameter();

			// スケジュールから荷主コードを取得します
			WmsScheduler schedule = new MasterSortingPlanRegistSCH(conn);
			param = (MasterSortingParameter) schedule.initFind(conn, param);

			if (param != null)
			{
				txt_ConsignorCode.setText(param.getConsignorCode());
				txt_ConsignorName.setText(param.getConsignorName());
				
				if (param.getMergeType() == MasterSortingParameter.MERGE_TYPE_OVERWRITE)
				{
					txt_ConsignorName.setReadOnly(true);
					txt_ItemName.setReadOnly(true);
					txt_CaseEntering.setReadOnly(true);
					txt_BundleEntering.setReadOnly(true);
					txt_CaseItf.setReadOnly(true);
					txt_BundleItf.setReadOnly(true);
					this.getViewState().setBoolean(IS_READ_ONLY_KEY, true);
				}
				else
				{
					this.getViewState().setBoolean(IS_READ_ONLY_KEY, false);
				}
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
	}

	/**
	 * 入力チェックを行います。<BR>
	 * 異常があった場合は、メッセージをセットし、falseを返します。<BR>
	 * 
	 * @return true:異常なし false:異常あり
	 */
	private boolean checkContainNgText()
	{
		
		WmsCheckker checker = new WmsCheckker();
		
		// 荷主コード
		if (!checker.checkContainNgText(txt_ConsignorCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		// 荷主名称
		if (!checker.checkContainNgText(txt_ConsignorName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		// 商品コード
		if (!checker.checkContainNgText(txt_ItemCode))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		// 商品名称
		if (!checker.checkContainNgText(txt_ItemName))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		// ケースITF 
		if (!checker.checkContainNgText(txt_CaseItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		// ボールITF 
		if (!checker.checkContainNgText(txt_BundleItf))
		{
			message.setMsgResourceKey(checker.getMessage());
			return false;	
		}
		
		return true;
		
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
	public void btn_PSearchConsignorCode_Click(ActionEvent e) throws Exception
	{
		// 荷主検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 検索フラグ：作業
		param.setParameter(ListSortingConsignorBusiness.SEARCHCONSIGNOR_KEY, SortingParameter.SEARCH_SORTING_WORK);
		// 状態フラグ 削除以外
		// セット不要
		// 処理中画面->結果画面
		redirect(DO_SRCH_CONSIGNOR, param, DO_SRCH_PROCESS);
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
	public void lbl_PickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_PickingPlanDate_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPickingPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕分予定日の検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で仕分予定日一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       仕分予定日 <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchPickingPlanDate_Click(ActionEvent e) throws Exception
	{
		// 仕分予定日検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 仕分予定日
		param.setParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
		// 検索フラグ
		param.setParameter(ListSortingDateBusiness.SEARCHSORTINGPLANDATE_KEY, SortingParameter.SEARCH_SORTING_WORK);

		// 状態フラグ 削除以外
		// セット不要

		// 処理中画面->結果画面
		redirect(DO_SRCH_SORTINGPLANDATE, param, DO_SRCH_PROCESS);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_PSearchPickPlan_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 仕分予定検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で仕分予定検索リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] *必須入力 <BR>
	 *    <DIR>
	 *       荷主コード* <BR>
	 *       仕分予定日 <BR>
	 *       商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchPickPlan_Click(ActionEvent e) throws Exception
	{
		// 仕分予定検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 仕分予定日
		param.setParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
		// 商品コード
		param.setParameter(ListSortingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
		// 状態フラグ
		// 削除以外なのでなにもセットしない
		// 処理中画面->結果画面
		redirect(DO_SRCH_SORTINGPLAN, param, DO_SRCH_PROCESS);
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
	 * 商品コードの検索ボタンが押下されたときに呼ばれます。<BR>
	 * <BR>
	 * 概要:検索条件をパラメータにセットし、その検索条件で商品一覧リストボックスを表示します。<BR>
	 * <BR>
	 * <DIR>
	 *    [パラメータ] <BR>
	 *    <DIR>
	 *       荷主コード <BR>
	 *       仕分予定日 <BR>
	 *       商品コード <BR>
	 *    </DIR>
	 * <BR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_PSearchItemCode_Click(ActionEvent e) throws Exception
	{
		// 商品一覧検索画面へ検索条件をセットする
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
		// 仕分予定日
		param.setParameter(ListSortingDateBusiness.SORTINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
		// 商品コード
		param.setParameter(ListSortingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());

		// 検索フラグ：作業
		param.setParameter(ListSortingItemBusiness.SEARCHITEM_KEY, SortingParameter.SEARCH_SORTING_WORK);
		// 状態フラグ 削除以外
		// セット不要
		// 処理中画面->結果画面
		redirect(DO_SRCH_ITEM, param, DO_SRCH_PROCESS);
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
	public void btn_Next_Server(ActionEvent e) throws Exception
	{
	}

	/**
	 * 次へボタンが押下されたときに呼ばれます。 <BR>
	 * <BR>
	 * 概要：入力エリアの入力項目を条件に、仕分予定登録(詳細情報)画面に遷移します。 <BR>
	 * <BR>
	 * <DIR>
	 * 1.入力エリア入力項目のチェックを行います。(必須入力) <BR>
	 * 2.スケジュールを開始します。 <BR>
	 * <DIR>
	 * [パラメータ] *必須入力 <BR>
	 * <DIR>
	 * 作業者コード* <BR>
	 * パスワード* <BR>
	 * 荷主コード* <BR>
	 * 荷主名称 <BR>
	 * 仕分予定日* <BR>
	 * 商品コード* <BR>
	 * 商品名称 <BR>
	 * ケースピース区分* <BR>
	 * ケース入数 <BR>
	 * ボール入数 <BR>
	 * ケースITF <BR>
	 * ボールITF <BR>
	 * </DIR>
	 * </DIR>
	 * 3.スケジュールの結果がtrueであれば、入力項目を条件に次画面(詳細情報登録)に遷移します。 <BR>
	 * <DIR>
	 * [ViewStateパラメータ] *必須入力 <BR>
	 * <DIR>
	 * 作業者コード* <BR>
	 * パスワード* <BR>
	 * 荷主コード* <BR>
	 * 荷主名称 <BR>
	 * 仕分予定日* <BR>
	 * 商品コード* <BR>
	 * 商品名称 <BR>
	 * ケースピース区分* <BR>
	 * ケース入数 <BR>
	 * ボール入数 <BR>
	 * ケースITF <BR>
	 * ボールITF <BR>
	 * <BR>
	 * </DIR>
	 * </DIR>
	 * falseの時はスケジュールから取得したメッセージを画面に出力します。 <BR>
	 * <BR>
	 * 4.入力エリアの内容は保持します。 <BR>
	 * <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Next_Click(ActionEvent e) throws Exception
	{
		// カーソルを作業者コードにセットします
		setFocus(txt_WorkerCode);

		// 必須入力チェック
		// 作業者コード
		txt_WorkerCode.validate();
		// パスワード
		txt_Password.validate();
		// 荷主コード
		txt_ConsignorCode.validate();
		// 荷主名称
		txt_ConsignorName.validate(false);
		// 仕分予定日
		txt_PickingPlanDate.validate();
		// 商品コード
		txt_ItemCode.validate();
		// 商品名称
		txt_ItemName.validate(false);
		// ケース入数
		txt_CaseEntering.validate(false);
		// ボール入数
		txt_BundleEntering.validate(false);
		// ケースITF
		txt_CaseItf.validate(false);
		// ボールITF
		txt_BundleItf.validate(false);
		
		if (!checkContainNgText())
		{
			return;
		}
		
		// パラメータをセットします
		MasterSortingParameter sparam = new MasterSortingParameter();

		// 作業者コード
		sparam.setWorkerCode(txt_WorkerCode.getText());
		// パスワード
		sparam.setPassword(txt_Password.getText());
		// 荷主コード
		sparam.setConsignorCode(txt_ConsignorCode.getText());
		// 荷主名称
		sparam.setConsignorName(txt_ConsignorName.getText());
		// 仕分予定日
		sparam.setPlanDate(WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
		// 商品コード
		sparam.setItemCode(txt_ItemCode.getText());
		// 商品名称
		sparam.setItemName(txt_ItemName.getText());
		// ケース入数
		sparam.setEnteringQty(txt_CaseEntering.getInt());
		// ボール入数
		sparam.setBundleEnteringQty(txt_BundleEntering.getInt());
		// ケースITF
		sparam.setITF(txt_CaseItf.getText());
		// ボールITF
		sparam.setBundleITF(txt_BundleItf.getText());

		Connection conn = null;
		try
		{
			// コネクションを取得します
			conn = ConnectionManager.getConnection(DATASOURCE_NAME);
	
			// スケジュールクラスのインスタンス生成をします
			WmsScheduler schedule = new MasterSortingPlanRegistSCH(conn);

			// 補完を行うため、パラメータに入力された値をセットする
			MasterSortingParameter mergeParam = new MasterSortingParameter();
			mergeParam.setConsignorCode(txt_ConsignorCode.getText());
			mergeParam.setConsignorName(txt_ConsignorName.getText());
			mergeParam.setItemCode(txt_ItemCode.getText());
			mergeParam.setItemName(txt_ItemName.getText());
			mergeParam.setEnteringQty(txt_CaseEntering.getInt());
			mergeParam.setBundleEnteringQty(txt_BundleEntering.getInt());
			mergeParam.setITF(txt_CaseItf.getText());
			mergeParam.setBundleITF(txt_BundleItf.getText());
			
			// 補完処理を行った結果で、入力チェックを行う
			mergeParam = (MasterSortingParameter) schedule.query(conn, mergeParam)[0];
			txt_ConsignorName.setText(mergeParam.getConsignorName());
			txt_ItemName.setText(mergeParam.getItemName());
			txt_CaseEntering.setInt(mergeParam.getEnteringQty());
			txt_BundleEntering.setInt(mergeParam.getBundleEnteringQty());
			txt_CaseItf.setText(mergeParam.getITF());
			txt_BundleItf.setText(mergeParam.getBundleITF());
            // 各コードの最終使用日を保持する
			this.getViewState().setString(CONSIG_DATE_KEY, WmsFormatter.getTimeStampString(mergeParam.getConsignorLastUpdateDate()));
			this.getViewState().setString(ITEM_DATE_KEY, WmsFormatter.getTimeStampString(mergeParam.getItemLastUpdateDate()));
			
			// スケジュールクラスで入力パラメーターチェックで問題がなければ
			// viewStateに画面パラメータをセットして仕分予定登録(詳細)へ遷移します
			if (schedule.nextCheck(conn, sparam))
			{
				// 画面タイトル
				viewState.setString(TITLE, lbl_SettingName.getResourceKey());
				// 作業者ｺｰﾄﾞ
				viewState.setString(WORKERCODE, txt_WorkerCode.getText());
				// ﾊﾟｽﾜｰﾄﾞ
				viewState.setString(PASSWORD, txt_Password.getText());
				// 荷主ｺｰﾄﾞ
				viewState.setString(ListSortingConsignorBusiness.CONSIGNORCODE_KEY, txt_ConsignorCode.getText());
				// 荷主名称
				viewState.setString(ListSortingConsignorBusiness.CONSIGNORNAME_KEY, txt_ConsignorName.getText());
				// 仕分予定日
				viewState.setString(ListSortingDateBusiness.SORTINGPLANDATE_KEY, WmsFormatter.toParamDate(txt_PickingPlanDate.getDate()));
				// 商品コード
				viewState.setString(ListSortingItemBusiness.ITEMCODE_KEY, txt_ItemCode.getText());
				// 商品名称
				viewState.setString(ListSortingItemBusiness.ITEMNAME_KEY, txt_ItemName.getText());
				// ケース入数
				viewState.setString(CASEQTY, Integer.toString(txt_CaseEntering.getInt()));
				// ボール入数
				viewState.setString(BUNDLEQTY, Integer.toString(txt_BundleEntering.getInt()));
				// ケースITF
				viewState.setString(CASEITF, txt_CaseItf.getText());
				// ボールITF
				viewState.setString(BUNDLEITF, txt_BundleItf.getText());

				// 基本情報設定画面->詳細情報登録画面
				forward(DO_REGIST2);
			}
			else
			{
				// 対象データが存在しない場合は、エラーメッセージを表示します
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
	 * 概要:入力エリアをクリアします。(作業者コード、パスワードはクリアしません) <BR>
	 * <BR>
	 * <DIR>
	 * 1.画面の初期化を行います。 <BR>
	 * 初期値については <CODE>setInitView(boolean)</CODE> メソッドを参照してください。 <BR>
	 * </DIR>
	 * 
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Clear_Click(ActionEvent e) throws Exception
	{
		// 画面を初期化します
		setInitView(false);
	}

}
//end of class
