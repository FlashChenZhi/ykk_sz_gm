// $Id: ListMasterConsignorBusiness.java,v 1.1.1.1 2006/08/17 09:34:16 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.listbox.listmasterconsignor;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.master.schedule.MasterParameter;

import jp.co.daifuku.wms.master.display.web.listbox.sessionret.SessionMasterConsignorCustomerRet;
import jp.co.daifuku.wms.master.display.web.listbox.sessionret.SessionMasterConsignorItemRet;
import jp.co.daifuku.wms.master.display.web.listbox.sessionret.SessionMasterConsignorRet;
import jp.co.daifuku.wms.master.display.web.listbox.sessionret.SessionMasterConsignorSupplierRet;

/**
 * Designer :  <BR>
 * Maker :  <BR>
 * <BR>
 * 荷主検索リストボックスクラスです。<BR>
 * 親画面から入力した荷主コードを基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した荷主コードをキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * 2.選択した行のボタン（<CODE>lst_ConsignorSearch_Click</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	選択行の荷主コード、荷主名称を親画面に渡し、リストボックスを閉じます。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2006/01/12</TD><TD>    </TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:16 $
 * @author  $Author: mori $
 */
public class ListMasterConsignorBusiness extends ListMasterConsignor implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * 荷主コードの受け渡しに使用するキーです
	 */
	public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

	/** 
	 * 荷主名称の受け渡しに使用するキーです
	 */
	public static final String CONSIGNORNAME_KEY = "CONSIGNORNAME_KEY";

	/**
	 * 検索フラグを受け渡しするキーです
	 */
	public static final String SEARCH_KEY = "SEARCH_KEY";
	
	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。
	 * <DIR>
	 *	項目 <BR>
	 *	<DIR>
	 *		選択 <BR>
	 *		荷主コード <BR>
	 *		荷主名称 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// 荷主検索
		lbl_ListName.setText(DisplayText.getText("TLE-W0012"));

		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(CONSIGNORCODE_KEY);

		// 検索条件
		String searchKey = request.getParameter(SEARCH_KEY);
		if (StringUtil.isBlank(searchKey))
		{
			searchKey = MasterParameter.SEARCHFLAG_CONSIGNOR;
		}
		this.getViewState().setString(SEARCH_KEY, searchKey);

		// Sessionに取り残されているオブジェクトのコネクションをクローズする
		SessionRet sRet = (SessionRet) this.getSession().getAttribute("LISTBOX");
		if (sRet != null)
		{
			DatabaseFinder finder = sRet.getFinder();
			if (finder != null)
			{
				finder.close();
			}
			// コネクションのクローズ
			sRet.closeConnection();
			// セッションから削除する
			this.getSession().removeAttribute("LISTBOX");
		}

		// コネクションの取得
		Connection conn = ConnectionManager.getConnection(DATASOURCE_NAME);

		// パラメータをセット
		MasterParameter param = new MasterParameter();
		// 荷主コード
		param.setConsignorCode(consignorcode);
		
		// 検索条件
		if (searchKey.equals(MasterParameter.SEARCHFLAG_CONSIGNOR))
		{
			// SessionConsignorRet インスタンス生成
			SessionMasterConsignorRet listbox = new SessionMasterConsignorRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setList(listbox, "first");
		}
		else if (searchKey.equals(MasterParameter.SEARCHFLAG_SUPPLIER))
		{
			SessionMasterConsignorSupplierRet listbox = new SessionMasterConsignorSupplierRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setSupplierList(listbox, "first");
		}
		else if (searchKey.equals(MasterParameter.SEARCHFLAG_CUSTOMER))
		{
			SessionMasterConsignorCustomerRet listbox = new SessionMasterConsignorCustomerRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setCustomerList(listbox, "first");
		}
		else if (searchKey.equals(MasterParameter.SEARCHFLAG_ITEM))
		{
			SessionMasterConsignorItemRet listbox = new SessionMasterConsignorItemRet(conn, param);
			// Sessionにlistboxを保持
			this.getSession().setAttribute("LISTBOX", listbox);
			setItemList(listbox, "first");
		}

	}

    /**
	 * 各コントロールイベント呼び出し前に呼び出されます。<BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。 
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void page_Initialize(ActionEvent e) throws Exception
	{
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * ページを変更するメソッド <BR>
	 * 予定テーブルを検索します。 <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 * @throws Exception 全ての例外を報告します。
     */
    private void setList(SessionMasterConsignorRet listbox, String actionName)
		throws Exception
	{
		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		MasterParameter[] sparam = (MasterParameter[]) listbox.getEntities();
		int len = 0;
		if (sparam != null)
		    len = sparam.length;
		if (len > 0)
		{
			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(listbox.getLength());
			// 1Page表示件数
			pgr_U.setPage(listbox.getCondition());
			// 開始位置
			pgr_U.setIndex(listbox.getCurrent() + 1);
			// 最大件数
			pgr_D.setMax(listbox.getLength());
			// 1Page表示件数
			pgr_D.setPage(listbox.getCondition());
			// 開始位置
			pgr_D.setIndex(listbox.getCurrent() + 1);

			// メッセージを隠します
			lbl_InMsg.setVisible(false);

			// 行を全て削除
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ConsignorSearch.getMaxRows();
				// 行追加
				lst_ConsignorSearch.addRow();

				// 最終行に移動
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, sparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, sparam[i].getConsignorName());
			}
		}
		else
		{
			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(0);
			// 1Page表示件数
			pgr_U.setPage(0);
			// 開始位置
			pgr_U.setIndex(0);
			// 最大件数
			pgr_D.setMax(0);
			// 1Page表示件数
			pgr_D.setPage(0);
			// 開始位置
			pgr_D.setIndex(0);

			// 検索結果の件数についてのチェックを行なう
			String errorMsg = listbox.checkLength();
			// ヘッダーを隠します
			lst_ConsignorSearch.setVisible(false);
			// エラーメッセージ表示
			lbl_InMsg.setResourceKey(errorMsg);
		}
    }

	/**
	 * ページを変更するメソッド <BR>
	 * 予定テーブルを検索します。 <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 * @throws Exception 全ての例外を報告します。
     */
    private void setSupplierList(SessionMasterConsignorSupplierRet listbox, String actionName)
		throws Exception
	{
		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		MasterParameter[] sparam = (MasterParameter[]) listbox.getEntities();
		
		
		
		int len = 0;
		if (sparam != null)
		    len = sparam.length;
		if (len > 0)
		{
			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(listbox.getLength());
			// 1Page表示件数
			pgr_U.setPage(listbox.getCondition());
			// 開始位置
			pgr_U.setIndex(listbox.getCurrent() + 1);
			// 最大件数
			pgr_D.setMax(listbox.getLength());
			// 1Page表示件数
			pgr_D.setPage(listbox.getCondition());
			// 開始位置
			pgr_D.setIndex(listbox.getCurrent() + 1);

			// メッセージを隠します
			lbl_InMsg.setVisible(false);

			// 行を全て削除
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ConsignorSearch.getMaxRows();
				// 行追加
				lst_ConsignorSearch.addRow();

				// 最終行に移動
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, sparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, sparam[i].getConsignorName());
			}
		}
		else
		{
			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(0);
			// 1Page表示件数
			pgr_U.setPage(0);
			// 開始位置
			pgr_U.setIndex(0);
			// 最大件数
			pgr_D.setMax(0);
			// 1Page表示件数
			pgr_D.setPage(0);
			// 開始位置
			pgr_D.setIndex(0);

			// 検索結果の件数についてのチェックを行なう
			String errorMsg = listbox.checkLength();
			// ヘッダーを隠します
			lst_ConsignorSearch.setVisible(false);
			// エラーメッセージ表示
			lbl_InMsg.setResourceKey(errorMsg);
		}
    }
    
	/**
	 * ページを変更するメソッド <BR>
	 * 予定テーブルを検索します。 <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 * @throws Exception 全ての例外を報告します。
     */
    private void setCustomerList(SessionMasterConsignorCustomerRet listbox, String actionName)
		throws Exception
	{
		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		MasterParameter[] sparam = (MasterParameter[]) listbox.getEntities();
		
		
		
		int len = 0;
		if (sparam != null)
		    len = sparam.length;
		if (len > 0)
		{
			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(listbox.getLength());
			// 1Page表示件数
			pgr_U.setPage(listbox.getCondition());
			// 開始位置
			pgr_U.setIndex(listbox.getCurrent() + 1);
			// 最大件数
			pgr_D.setMax(listbox.getLength());
			// 1Page表示件数
			pgr_D.setPage(listbox.getCondition());
			// 開始位置
			pgr_D.setIndex(listbox.getCurrent() + 1);

			// メッセージを隠します
			lbl_InMsg.setVisible(false);

			// 行を全て削除
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ConsignorSearch.getMaxRows();
				// 行追加
				lst_ConsignorSearch.addRow();

				// 最終行に移動
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, sparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, sparam[i].getConsignorName());
			}
		}
		else
		{
			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(0);
			// 1Page表示件数
			pgr_U.setPage(0);
			// 開始位置
			pgr_U.setIndex(0);
			// 最大件数
			pgr_D.setMax(0);
			// 1Page表示件数
			pgr_D.setPage(0);
			// 開始位置
			pgr_D.setIndex(0);

			// 検索結果の件数についてのチェックを行なう
			String errorMsg = listbox.checkLength();
			// ヘッダーを隠します
			lst_ConsignorSearch.setVisible(false);
			// エラーメッセージ表示
			lbl_InMsg.setResourceKey(errorMsg);
		}
    }
    
	/**
	 * ページを変更するメソッド <BR>
	 * 予定テーブルを検索します。 <BR>
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
	 * @throws Exception 全ての例外を報告します。
     */
    private void setItemList(SessionMasterConsignorItemRet listbox, String actionName)
		throws Exception
	{
		// ページ情報をセット
		listbox.setActionName(actionName);

		// 検索結果を取得
		MasterParameter[] sparam = (MasterParameter[]) listbox.getEntities();
		
		
		
		int len = 0;
		if (sparam != null)
		    len = sparam.length;
		if (len > 0)
		{
			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(listbox.getLength());
			// 1Page表示件数
			pgr_U.setPage(listbox.getCondition());
			// 開始位置
			pgr_U.setIndex(listbox.getCurrent() + 1);
			// 最大件数
			pgr_D.setMax(listbox.getLength());
			// 1Page表示件数
			pgr_D.setPage(listbox.getCondition());
			// 開始位置
			pgr_D.setIndex(listbox.getCurrent() + 1);

			// メッセージを隠します
			lbl_InMsg.setVisible(false);

			// 行を全て削除
			lst_ConsignorSearch.clearRow();

			for (int i = 0; i < len; i++)
			{
				// 最終行を取得
				int count = lst_ConsignorSearch.getMaxRows();
				// 行追加
				lst_ConsignorSearch.addRow();

				// 最終行に移動
				lst_ConsignorSearch.setCurrentRow(count);
				lst_ConsignorSearch.setValue(1, Integer.toString(count + listbox.getCurrent()));
				lst_ConsignorSearch.setValue(2, sparam[i].getConsignorCode());
				lst_ConsignorSearch.setValue(3, sparam[i].getConsignorName());
			}
		}
		else
		{
			// Pagerへの値セット
			// 最大件数
			pgr_U.setMax(0);
			// 1Page表示件数
			pgr_U.setPage(0);
			// 開始位置
			pgr_U.setIndex(0);
			// 最大件数
			pgr_D.setMax(0);
			// 1Page表示件数
			pgr_D.setPage(0);
			// 開始位置
			pgr_D.setIndex(0);

			// 検索結果の件数についてのチェックを行なう
			String errorMsg = listbox.checkLength();
			// ヘッダーを隠します
			lst_ConsignorSearch.setVisible(false);
			// エラーメッセージ表示
			lbl_InMsg.setResourceKey(errorMsg);
		}
    }
	// Event handler methods -----------------------------------------
	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_ListName_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_U_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 「閉じる」ボタンを押したときの処理を行います。 <BR>
	 *  <BR>
	 * リストボックスを閉じ、親画面へ遷移します。 <BR>
	 *  <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void btn_Close_U_Click(ActionEvent e) throws Exception
	{
		btn_Close_D_Click(e);
	}

	/** 
	 * 「＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 次のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void pgr_U_Next(ActionEvent e) throws Exception
	{
		pgr_D_Next(e);
	}

	/** 
	 * 「＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * １ページ前を表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void pgr_U_Prev(ActionEvent e) throws Exception
	{
		pgr_D_Prev(e);
	}

	/** 
	 * 「＞＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 最終ページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void pgr_U_Last(ActionEvent e) throws Exception
	{
		pgr_D_Last(e);
	}

	/** 
	 * 「＜＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 一番最初のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void pgr_U_First(ActionEvent e) throws Exception
	{
		pgr_D_First(e);
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_InMsg_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_ConsignorSearch_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 *	親画面に荷主コード、荷主名称を渡し、リストボックスを閉じます。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void lst_ConsignorSearch_Click(ActionEvent e) throws Exception
	{
		// 現在の行をセット
		lst_ConsignorSearch.setCurrentRow(lst_ConsignorSearch.getActiveRow());
		lst_ConsignorSearch.getValue(1);

		// 親画面に返却するパラメータセット
		ForwardParameters param = new ForwardParameters();
		// 荷主コード
		param.setParameter(CONSIGNORCODE_KEY, lst_ConsignorSearch.getValue(2));
		// 荷主名称
		param.setParameter(CONSIGNORNAME_KEY, lst_ConsignorSearch.getValue(3));

		// 親画面に遷移する
		parentRedirect(param);
	}

	/** 
	 * 「＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 次のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void pgr_D_Next(ActionEvent e) throws Exception
	{
		
		// 検索フラグを取得
		String flag = viewState.getString(SEARCH_KEY);
		
		if (flag.equals(MasterParameter.SEARCHFLAG_CONSIGNOR))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorRet listbox = 
		        (SessionMasterConsignorRet) this.getSession().getAttribute("LISTBOX");
		    setList(listbox, "next");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_SUPPLIER))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorSupplierRet listbox = 
		        (SessionMasterConsignorSupplierRet) this.getSession().getAttribute("LISTBOX");
		    setSupplierList(listbox, "next");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_CUSTOMER))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorCustomerRet listbox = 
		        (SessionMasterConsignorCustomerRet) this.getSession().getAttribute("LISTBOX");
		    setCustomerList(listbox, "next");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_ITEM))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorItemRet listbox = 
		        (SessionMasterConsignorItemRet) this.getSession().getAttribute("LISTBOX");
		    setItemList(listbox, "next");
		}

	}

	/** 
	 * 「＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * １ページ前を表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void pgr_D_Prev(ActionEvent e) throws Exception
	{
		// 検索フラグを取得
		String flag = viewState.getString(SEARCH_KEY);
		
		if (flag.equals(MasterParameter.SEARCHFLAG_CONSIGNOR))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorRet listbox = 
		        (SessionMasterConsignorRet) this.getSession().getAttribute("LISTBOX");
		    setList(listbox, "previous");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_SUPPLIER))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorSupplierRet listbox = 
		        (SessionMasterConsignorSupplierRet) this.getSession().getAttribute("LISTBOX");
		    setSupplierList(listbox, "previous");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_CUSTOMER))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorCustomerRet listbox = 
		        (SessionMasterConsignorCustomerRet) this.getSession().getAttribute("LISTBOX");
		    setCustomerList(listbox, "previous");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_ITEM))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorItemRet listbox = 
		        (SessionMasterConsignorItemRet) this.getSession().getAttribute("LISTBOX");
		    setItemList(listbox, "previous");
		}

	}

	/** 
	 * 「＞＞」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 最終ページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void pgr_D_Last(ActionEvent e) throws Exception
	{
		// 検索フラグを取得
		String flag = viewState.getString(SEARCH_KEY);
		
		if (flag.equals(MasterParameter.SEARCHFLAG_CONSIGNOR))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorRet listbox = 
		        (SessionMasterConsignorRet) this.getSession().getAttribute("LISTBOX");
		    setList(listbox, "last");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_SUPPLIER))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorSupplierRet listbox = 
		        (SessionMasterConsignorSupplierRet) this.getSession().getAttribute("LISTBOX");
		    setSupplierList(listbox, "last");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_CUSTOMER))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorCustomerRet listbox = 
		        (SessionMasterConsignorCustomerRet) this.getSession().getAttribute("LISTBOX");
		    setCustomerList(listbox, "last");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_ITEM))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorItemRet listbox = 
		        (SessionMasterConsignorItemRet) this.getSession().getAttribute("LISTBOX");
		    setItemList(listbox, "last");
		}

	}

	/** 
	 * 「＜＜」ボタンを押下したときの処理を行います。 <BR>
	 * <BR>
	 * 一番最初のページを表示します。 <BR>
	 * <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void pgr_D_First(ActionEvent e) throws Exception
	{
		
		// 検索フラグを取得
		String flag = viewState.getString(SEARCH_KEY);
		
		if (flag.equals(MasterParameter.SEARCHFLAG_CONSIGNOR))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorRet listbox = 
		        (SessionMasterConsignorRet) this.getSession().getAttribute("LISTBOX");
		    setList(listbox, "first");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_SUPPLIER))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorSupplierRet listbox = 
		        (SessionMasterConsignorSupplierRet) this.getSession().getAttribute("LISTBOX");
		    setSupplierList(listbox, "first");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_CUSTOMER))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorCustomerRet listbox = 
		        (SessionMasterConsignorCustomerRet) this.getSession().getAttribute("LISTBOX");
		    setCustomerList(listbox, "first");
		}
		else if (flag.equals(MasterParameter.SEARCHFLAG_ITEM))
		{
		    // Sessionにlistboxを保持
		    SessionMasterConsignorItemRet listbox = 
		        (SessionMasterConsignorItemRet) this.getSession().getAttribute("LISTBOX");
		    setItemList(listbox, "first");
		}

	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void btn_Close_D_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 「閉じる」ボタンを押したときの処理を行います。 <BR>
	 *  <BR>
	 * リストボックスを閉じ、親画面へ遷移します。 <BR>
	 *  <BR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。 
	 */
	public void btn_Close_D_Click(ActionEvent e) throws Exception
	{
	    // Sessionにlistboxを保持
	    SessionRet sessionret = (SessionRet) this.getSession().getAttribute("LISTBOX");
		// Sessionに値がある場合
		if (sessionret != null)
		{
			DatabaseFinder finder = sessionret.getFinder();
			if (finder != null)
			{
				// ステートメントを閉じる
				finder.close();
			}
			// コネクションをクローズ
			sessionret.closeConnection();
		}
		// セッションから削除する
		this.getSession().removeAttribute("LISTBOX");
		// 親画面に戻る
		parentRedirect(null);
	}
}
//end of class
