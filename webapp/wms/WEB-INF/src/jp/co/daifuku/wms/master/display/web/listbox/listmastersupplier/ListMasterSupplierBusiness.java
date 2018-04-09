//$Id: ListMasterSupplierBusiness.java,v 1.1.1.1 2006/08/17 09:34:16 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.listbox.listmastersupplier;
import java.sql.Connection;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.master.display.web.listbox.sessionret.SessionMasterSupplierRet;
import jp.co.daifuku.wms.master.schedule.MasterParameter;

/**
 * Designer :<BR>
 * Maker :<BR>
 * <BR>
 * 仕入先検索リストボックスクラスです。 <BR>
 * 親画面から入力した仕入先コードを基に検索します。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期画面（ <CODE>page_Load(ActionEvent e)</CODE> メソッド） <BR>
 * <BR>
 * <DIR>親画面から入力した仕入先コードをキーにして検索し、画面に表示します。 <BR>
 * <BR>
 * </DIR> 2.選択した行のボタン（ <CODE>lst_SupplierSearch_Click</CODE> メソッド） <BR>
 * <BR>
 * <DIR>選択行の仕入先コード、仕入先名称を親画面に渡し、リストボックスを閉じます。 <BR>
 * <BR>
 * </DIR> <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2006/01/16</TD>
 * <TD>()</TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:16 $
 * @author $Author: mori $
 */
public class ListMasterSupplierBusiness extends ListMasterSupplier implements WMSConstants {
    // Class fields --------------------------------------------------
    /**
     * 荷主コードの受け渡しに使用するキーです
     */
    public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

    /**
     * 仕入先コードの受け渡しに使用するキーです
     */
    public static final String SUPPLIERCODE_KEY = "SUPPLIERCODE_KEY";

    /**
     * 仕入先名称の受け渡しに使用するキーです
     */
    public static final String SUPPLIERNAME_KEY = "SUPPLIERNAME_KEY";

    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * 画面の初期化を行います。 <BR>
     * <DIR>項目 <BR>
     * <DIR>選択 <BR>
     * 仕入先コード <BR>
     * 仕入先名称 <BR>
     * </DIR> </DIR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void page_Load(ActionEvent e) throws Exception {
        // 画面名をセットする
        // 仕入先検索
        lbl_ListName.setText(DisplayText.getText("TLE-W0021"));

        // パラメータを取得
        // 荷主コード
        String consignorcode = request.getParameter(CONSIGNORCODE_KEY);
        // 仕入先コード
        String suppliercode = request.getParameter(SUPPLIERCODE_KEY);

        // Sessionに取り残されているオブジェクトのコネクションをクローズする
        SessionRet sRet = (SessionRet) this.getSession()
                .getAttribute("LISTBOX");
        if (sRet != null) {
            DatabaseFinder finder = sRet.getFinder();
            if (finder != null) {
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
        // 仕入先コード
        param.setSupplierCode(suppliercode);

        // SessionSupplierRet インスタンス生成
        SessionMasterSupplierRet listbox = new SessionMasterSupplierRet(conn, param);
        // Sessionにlistboxを保持
        this.getSession().setAttribute("LISTBOX", listbox);
        setList(listbox, "first");
    }

    /**
     * 各コントロールイベント呼び出し前に呼び出されます。 <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void page_Initialize(ActionEvent e) throws Exception {
    }

    // Package methods -----------------------------------------------

    // Protected methods ---------------------------------------------

    // Private methods -----------------------------------------------
    /**
     * ページを変更するメソッド <BR>
     * 予定テーブルを検索します。 <BR>
     * 
     * @param listbox ページに表示するリストボックス情報。
	 * @param actionName 画面遷移のパターン文字列。
     * @throws Exception
     *             全ての例外を報告します。
     */
    private void setList(SessionMasterSupplierRet listbox, String actionName)
            throws Exception {
        // ページ情報をセット
        listbox.setActionName(actionName);

        // 検索結果を取得
        MasterParameter[] sparam = (MasterParameter[]) listbox.getEntities();
        int len = 0;
        if (sparam != null)
            len = sparam.length;
        if (len > 0) {
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
            lst_SupplierSearch.clearRow();

            for (int i = 0; i < len; i++) {
                // 最終行を取得
                int count = lst_SupplierSearch.getMaxRows();
                // 行追加
                lst_SupplierSearch.addRow();

                // 最終行に移動
                lst_SupplierSearch.setCurrentRow(count);
                lst_SupplierSearch.setValue(1, Integer.toString(count
                        + listbox.getCurrent()));
                lst_SupplierSearch.setValue(2, sparam[i].getConsignorCode());
                lst_SupplierSearch.setValue(3, sparam[i].getSupplierCode());
                lst_SupplierSearch.setValue(4, sparam[i].getSupplierName());
            }
        } else {
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
            lst_SupplierSearch.setVisible(false);
            // エラーメッセージ表示
            lbl_InMsg.setResourceKey(errorMsg);
        }

    }

    // Event handler methods -----------------------------------------
    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lbl_ListName_Server(ActionEvent e) throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void btn_Close_U_Server(ActionEvent e) throws Exception {
    }

    /**
     * 「閉じる」ボタンを押したときの処理を行います。 <BR>
     * <BR>
     * リストボックスを閉じ、親画面へ遷移します。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void btn_Close_U_Click(ActionEvent e) throws Exception {
        btn_Close_D_Click(e);
    }

    /**
     * 「＞」ボタンを押下したときの処理を行います。 <BR>
     * <BR>
     * 次のページを表示します。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void pgr_U_Next(ActionEvent e) throws Exception {
        pgr_D_Next(e);
    }

    /**
     * 「＜」ボタンを押下したときの処理を行います。 <BR>
     * <BR>
     * １ページ前を表示します。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void pgr_U_Prev(ActionEvent e) throws Exception {
        pgr_D_Prev(e);
    }

    /**
     * 「＞＞」ボタンを押下したときの処理を行います。 <BR>
     * <BR>
     * 最終ページを表示します。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void pgr_U_Last(ActionEvent e) throws Exception {
        pgr_D_Last(e);
    }

    /**
     * 「＜＜」ボタンを押下したときの処理を行います。 <BR>
     * <BR>
     * 一番最初のページを表示します。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void pgr_U_First(ActionEvent e) throws Exception {
        pgr_D_First(e);
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lbl_InMsg_Server(ActionEvent e) throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_SupplierSearch_EnterKey(ActionEvent e) throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_SupplierSearch_TabKey(ActionEvent e) throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_SupplierSearch_InputComplete(ActionEvent e)
            throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_SupplierSearch_ColumClick(ActionEvent e) throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_SupplierSearch_Server(ActionEvent e) throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_SupplierSearch_Change(ActionEvent e) throws Exception {
    }

    /**
     * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
     * <BR>
     * 親画面に仕入先コード、仕入先名称を渡し、リストボックスを閉じます。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void lst_SupplierSearch_Click(ActionEvent e) throws Exception {
        // 現在の行をセット
        lst_SupplierSearch.setCurrentRow(lst_SupplierSearch.getActiveRow());
        lst_SupplierSearch.getValue(1);

        // 親画面に返却するパラメータセット
        ForwardParameters param = new ForwardParameters();
        // 荷主コード
        param.setParameter(CONSIGNORCODE_KEY, lst_SupplierSearch.getValue(2));
        // 仕入先コード
        param.setParameter(SUPPLIERCODE_KEY, lst_SupplierSearch.getValue(3));
        // 仕入先名称
        param.setParameter(SUPPLIERNAME_KEY, lst_SupplierSearch.getValue(4));

        // 親画面に遷移する
        parentRedirect(param);
    }

    /**
     * 「＞」ボタンを押下したときの処理を行います。 <BR>
     * <BR>
     * 次のページを表示します。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void pgr_D_Next(ActionEvent e) throws Exception {
        // Sessionにlistboxを保持
        SessionMasterSupplierRet listbox = (SessionMasterSupplierRet) this.getSession()
                .getAttribute("LISTBOX");
        setList(listbox, "next");
    }

    /**
     * 「＜」ボタンを押下したときの処理を行います。 <BR>
     * <BR>
     * １ページ前を表示します。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void pgr_D_Prev(ActionEvent e) throws Exception {
        // Sessionにlistboxを保持
        SessionMasterSupplierRet listbox = (SessionMasterSupplierRet) this.getSession()
                .getAttribute("LISTBOX");
        setList(listbox, "previous");
    }

    /**
     * 「＞＞」ボタンを押下したときの処理を行います。 <BR>
     * <BR>
     * 最終ページを表示します。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void pgr_D_Last(ActionEvent e) throws Exception {
        // Sessionにlistboxを保持
        SessionMasterSupplierRet listbox = (SessionMasterSupplierRet) this.getSession()
                .getAttribute("LISTBOX");
        setList(listbox, "last");
    }

    /**
     * 「＜＜」ボタンを押下したときの処理を行います。 <BR>
     * <BR>
     * 一番最初のページを表示します。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void pgr_D_First(ActionEvent e) throws Exception {
        // Sessionにlistboxを保持
        SessionMasterSupplierRet listbox = (SessionMasterSupplierRet) this.getSession()
                .getAttribute("LISTBOX");
        setList(listbox, "first");
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void btn_Close_D_Server(ActionEvent e) throws Exception {
    }

    /**
     * 「閉じる」ボタンを押したときの処理を行います。 <BR>
     * <BR>
     * リストボックスを閉じ、親画面へ遷移します。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void btn_Close_D_Click(ActionEvent e) throws Exception {
        // Sessionにlistboxを保持
        SessionRet sessionret = (SessionRet) this.getSession().getAttribute(
                "LISTBOX");
        // Sessionに値がある場合
        if (sessionret != null) {
            DatabaseFinder finder = sessionret.getFinder();
            if (finder != null) {
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
