//$Id: ListMasterItemBusiness.java,v 1.1.1.1 2006/08/17 09:34:16 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.master.display.web.listbox.listmasteritem;
import java.sql.Connection;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.bluedog.webapp.ForwardParameters;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.master.display.web.listbox.sessionret.SessionMasterItemRet;
import jp.co.daifuku.wms.master.schedule.MasterParameter;

/**
 * Designer :<BR>
 * Maker :<BR>
 * <BR>
 * 商品検索リストボックスクラスです。 <BR>
 * 親画面から入力した商品コードを基に検索します。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.初期画面（ <CODE>page_Load(ActionEvent e)</CODE> メソッド） <BR>
 * <BR>
 * <DIR>親画面から入力した荷主コードと商品コードをキーにして検索し、画面に表示します。 <BR>
 * <BR>
 * </DIR> 2.選択した行のボタン（ <CODE>lst_ItemSearch_Click</CODE> メソッド） <BR>
 * <BR>
 * <DIR>選択行の荷主コード、商品コード、商品名称を親画面に渡し、リストボックスを閉じます。 <BR>
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
 * <TD> </TD>
 * <TD>created this class</TD>
 * </TR>
 * </TABLE> <BR>
 * 
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:16 $
 * @author $Author: mori $
 */
public class ListMasterItemBusiness extends ListMasterItem implements WMSConstants {
    // Class fields --------------------------------------------------
    /**
     * 荷主コードの受け渡しに使用するキーです
     */
    public static final String CONSIGNORCODE_KEY = "CONSIGNORCODE_KEY";

    /**
     * 商品コードの受け渡しに使用するキーです
     */
    public static final String ITEMCODE_KEY = "ITEMCODE_KEY";

    /**
     * 商品名称の受け渡しに使用するキーです
     */
    public static final String ITEMNAME_KEY = "ITEMNAME_KEY";
    
    /**
     * ケース入数の受け渡しに使用するキーです
     */
    public static final String ENTERINGQTY_KEY = "ENTERINGQTY_KEY";
    
    /**
     * ボール入数の受け渡しに使用するキーです
     */
    public static final String BUNDLEENTERINGQTY_KEY = "BUNDLEENTERINGQTY_KEY";
    
	/**
	 * ケース入数の受け渡しに使用するキーです
	 */
	public static final String ENTERING_KEY = "ENTERING_KEY";

	/**
	 * ボール入数の受け渡しに使用するキーです
	 */
	public static final String BUNDLEENTERING_KEY = "BUNDLEENTERING_KEY";
    /**
     * ケースITFの受け渡しに使用するキーです
     */
    public static final String ITF_KEY = "ITF_KEY";
    
    /**
     * ボールITFの受け渡しに使用するキーです
     */
    public static final String BUNDLEITF_KEY = "BUNDLEITF_KEY";

	/** 
	 * ケースITFの受け渡しに使用するキーです
	 */
	public static final String CASEITF_KEY = "CASEITF_KEY";
	
	/** 
	 * ケース入数の受け渡しに使用するキーです
	 */
	public static final String CASEETR_KEY = "CASEETR_KEY";
	
	/** 
	 * ボール入数の受け渡しに使用するキーです	
	 */
	public static final String BUNDLEETR_KEY = "BUNDLEETR_KEY";
	
	/** 
	 * ボール入り数の受け渡しに使用するキーです
	 */
	public static final String BUNDLE_ENTERINGQTY_KEY = "BUNDLE_ENTERINGQTY_KEY";
	
    // Class variables -----------------------------------------------

    // Class method --------------------------------------------------

    // Constructors --------------------------------------------------

    // Public methods ------------------------------------------------

    /**
     * 画面の初期化を行います。 <BR>
     * <DIR>項目 <BR>
     * <DIR>選択 <BR>
     * 荷主コード <BR>
     * 商品コード <BR>
     * 商品名称 <BR>
     * </DIR> </DIR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void page_Load(ActionEvent e) throws Exception {
        // 画面名をセットする
        // 商品検索
        lbl_ListName.setText(DisplayText.getText("TLE-W0043"));

        // パラメータを取得
        // 荷主コード
        String consignorcode = request.getParameter(CONSIGNORCODE_KEY);
        // 商品コード
        String itemcode = request.getParameter(ITEMCODE_KEY);

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
        // 商品コード
        param.setItemCode(itemcode);

        // SessionItemRet インスタンス生成
        SessionMasterItemRet listbox = new SessionMasterItemRet(conn, param);
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
    private void setList(SessionMasterItemRet listbox, String actionName)
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
            lst_ItemSearch.clearRow();

            for (int i = 0; i < len; i++) {
                // 最終行を取得
                int count = lst_ItemSearch.getMaxRows();
                // 行追加
                lst_ItemSearch.addRow();

                List hiddenList = new Vector();
                hiddenList.add(0, Integer.toString(sparam[i].getEnteringQty()));
                hiddenList.add(1, Integer.toString(sparam[i].getBundleEnteringQty()));
                hiddenList.add(2, sparam[i].getITF());
                hiddenList.add(3, sparam[i].getBundleITF());
                // 最終行に移動
                lst_ItemSearch.setCurrentRow(count);
                lst_ItemSearch.setValue(0, CollectionUtils.getConnectedString(hiddenList));
                lst_ItemSearch.setValue(1, Integer.toString(count
                        + listbox.getCurrent()));
                lst_ItemSearch.setValue(2, sparam[i].getConsignorCode());
                lst_ItemSearch.setValue(3, sparam[i].getItemCode());
                lst_ItemSearch.setValue(4, sparam[i].getItemName());
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
            lst_ItemSearch.setVisible(false);
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
    public void lst_ItemSearch_EnterKey(ActionEvent e) throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_ItemSearch_TabKey(ActionEvent e) throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_ItemSearch_InputComplete(ActionEvent e)
            throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_ItemSearch_ColumClick(ActionEvent e) throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_ItemSearch_Server(ActionEvent e) throws Exception {
    }

    /**
     * 
     * @param e
     *            ActionEvent
     * @throws Exception
     */
    public void lst_ItemSearch_Change(ActionEvent e) throws Exception {
    }

    /**
     * リストセルの選択ボタンを押下したときの処理を行います。 <BR>
     * <BR>
     * 親画面に商品コード、商品名称を渡し、リストボックスを閉じます。 <BR>
     * <BR>
     * 
     * @param e
     *            ActionEvent イベントの情報を格納するクラスです。
     * @throws Exception
     *             全ての例外を報告します。
     */
    public void lst_ItemSearch_Click(ActionEvent e) throws Exception {
        // 現在の行をセット
        lst_ItemSearch.setCurrentRow(lst_ItemSearch.getActiveRow());
        lst_ItemSearch.getValue(1);

        // 親画面に返却するパラメータセット
        ForwardParameters param = new ForwardParameters();
        // 荷主コード
        param.setParameter(CONSIGNORCODE_KEY, lst_ItemSearch.getValue(2));
        // 商品コード
        param.setParameter(ITEMCODE_KEY, lst_ItemSearch.getValue(3));
        // 商品名称
        param.setParameter(ITEMNAME_KEY, lst_ItemSearch.getValue(4));
        // ケース入数
        param.setParameter(ENTERINGQTY_KEY, CollectionUtils.getString(0, lst_ItemSearch.getValue(0)));
        param.setParameter(ENTERING_KEY, CollectionUtils.getString(0, lst_ItemSearch.getValue(0)));
        param.setParameter(CASEETR_KEY, CollectionUtils.getString(0, lst_ItemSearch.getValue(0)));
        // ボール入数
        param.setParameter(BUNDLEENTERINGQTY_KEY, CollectionUtils.getString(1, lst_ItemSearch.getValue(0)));
        param.setParameter(BUNDLEENTERING_KEY, CollectionUtils.getString(1, lst_ItemSearch.getValue(0)));
        param.setParameter(BUNDLEETR_KEY, CollectionUtils.getString(1, lst_ItemSearch.getValue(0)));
        param.setParameter(BUNDLE_ENTERINGQTY_KEY, CollectionUtils.getString(1, lst_ItemSearch.getValue(0)));
        // ケースITF
        param.setParameter(ITF_KEY, CollectionUtils.getString(2, lst_ItemSearch.getValue(0)));
        param.setParameter(CASEITF_KEY, CollectionUtils.getString(2, lst_ItemSearch.getValue(0)));
        // ボールITF
        param.setParameter(BUNDLEITF_KEY, CollectionUtils.getString(3, lst_ItemSearch.getValue(0)));
        
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
        SessionMasterItemRet listbox = (SessionMasterItemRet) this.getSession()
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
        SessionMasterItemRet listbox = (SessionMasterItemRet) this.getSession()
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
        SessionMasterItemRet listbox = (SessionMasterItemRet) this.getSession()
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
        SessionMasterItemRet listbox = (SessionMasterItemRet) this.getSession()
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
