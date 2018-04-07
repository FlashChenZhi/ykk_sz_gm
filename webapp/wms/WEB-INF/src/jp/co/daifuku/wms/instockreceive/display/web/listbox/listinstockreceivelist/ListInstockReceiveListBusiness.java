// $Id: ListInstockReceiveListBusiness.java,v 1.1.1.1 2006/08/17 09:34:12 mori Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivelist;
import java.sql.Connection;
import java.util.Locale;

import jp.co.daifuku.bluedog.sql.ConnectionManager;
import jp.co.daifuku.bluedog.webapp.ActionEvent;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.WMSConstants;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.ToolTipHelper;
import jp.co.daifuku.wms.base.display.web.WmsCheckker;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveconsignor.ListInstockReceiveConsignorBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveitem.ListInstockReceiveItemBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceiveplandate.ListInstockReceivePlanDateBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.listinstockreceivesupplier.ListInstockReceiveSupplierBusiness;
import jp.co.daifuku.wms.instockreceive.display.web.listbox.sessionret.SessionInstockReceiveListRet;
import jp.co.daifuku.wms.instockreceive.schedule.InstockReceiveParameter;

/**
 * Designer : K.Mukai <BR>
 * Maker : A.Nagasawa <BR>
 * <BR> 
 * クロス/DC入荷作業リスト一覧検索リストボックスクラスです。<BR>
 * 親画面から入力した荷主コード、入荷予定日、仕入先コード、商品コード、クロス/DC区分、表示順、作業状態を基に検索します。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.初期画面（<CODE>page_Load(ActionEvent e)</CODE>メソッド）<BR>
 * <BR>
 * <DIR>
 * 	親画面から入力した荷主コード、入荷予定日、仕入先コード、商品コード、クロス/DC区分、表示順、作業状態をキーにして検索し、画面に表示します。<BR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/02/13</TD><TD>A.Nagasawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:12 $
 * @author  $Author: mori $
 */
public class ListInstockReceiveListBusiness extends ListInstockReceiveList implements WMSConstants
{
	// Class fields --------------------------------------------------
	/** 
	 * クロス/DC区分の受け渡しに使用するキーです
	 */
	public static final String CROSSDCFLAG_KEY = "CROSSDCFLAG_KEY";
	
	/** 
	 * 表示順の受け渡しに使用するキーです
	 */
	public static final String DSPORDER_KEY = "DSPORDER_KEY";
	/** 
	 * 作業状態の受け渡しに使用するキーです
	 */
	public static final String STATUS_FLAG_KEY = "STATUS_FLAG_KEY";
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------

	/**
	 * 画面の初期化を行います。 <BR>
	 * <DIR>
	 *	項目 <BR>
	 *	<DIR>
	 *		仕入先ｺｰﾄﾞ <BR>
	 *		伝票No. <BR>
	 *		商品ｺｰﾄﾞ <BR>
	 *		入荷総数 <BR>
	 *		ｹｰｽ入数 <BR>
	 *		作業予定ｹｰｽ数 <BR>
	 *		実績ｹｰｽ数 <BR>
	 *		賞味期限 <BR>
	 *		クロス/DC <BR>
	 *		仕入先名称 <BR>
	 *		行No. <BR>
	 *		商品名称 <BR>
	 *		ﾎﾞｰﾙ入数 <BR>
	 *		作業予定ﾋﾟｰｽ数 <BR>
	 *		実績ﾋﾟｰｽ数 <BR>
	 *<BR>
	 *		バルーンヘルプ <BR>
	 *		仕入先名称 <BR>
	 *		商品名称 <BR>
	 *		実績ケース数 <BR>
	 *		実績ピース数 <BR>
	 *		賞味期限 <BR>
	 *		クロス/DC区分 <BR>
	 *	</DIR>
	 * </DIR>
	 * @param e ActionEvent イベントの情報を格納するクラスです。
	 * @throws Exception 全ての例外を報告します。
	 */
	public void page_Load(ActionEvent e) throws Exception
	{
		// 画面名をセットする
		// クロス/DC入荷作業一覧
		lbl_ListName.setText(DisplayText.getText("TLE-W0091"));
		
		// パラメータを取得
		// 荷主コード
		String consignorcode = request.getParameter(ListInstockReceiveConsignorBusiness.CONSIGNORCODE_KEY);
		// 入荷予定日
		String instockplandate = request.getParameter(ListInstockReceivePlanDateBusiness.INSTOCKPLANDATE_KEY);
		// 仕入先コード
		String suppliercode = request.getParameter(ListInstockReceiveSupplierBusiness.SUPPLIERCODE_KEY);
		// 商品コード
		String itemcode = request.getParameter(ListInstockReceiveItemBusiness.ITEMCODE_KEY);
		// クロス/DC区分
		String crossdcflag = request.getParameter(ListInstockReceiveListBusiness.CROSSDCFLAG_KEY);
		// 表示順
		String dsporder = request.getParameter(ListInstockReceiveListBusiness.DSPORDER_KEY);
		// 作業状態
		String statusflag = request.getParameter(STATUS_FLAG_KEY);
		
		// 荷主コードの必須入力チェック、禁止文字チェック
		if (!WmsCheckker
			.consignorCheck(consignorcode, lst_InstkCrsDcWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		// 入荷予定日の必須入力チェック、禁止文字チェック
		if (!WmsCheckker
			.instockplandateCheck(instockplandate, lst_InstkCrsDcWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		// 仕入先コードの禁止文字チェック
		if (!WmsCheckker
			.charCheck(suppliercode, lst_InstkCrsDcWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}
		// 商品コードの禁止文字チェック
		if (!WmsCheckker.charCheck(itemcode, lst_InstkCrsDcWorkList, pgr_U, pgr_D, lbl_InMsg))
		{
			return;
		}

		// 検索条件を画面に表示
		lbl_JavaSetConsignorCode.setText(consignorcode);
		txt_FDateInstockPlanDate.setDate(WmsFormatter.toDate(instockplandate));
		
		
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
		InstockReceiveParameter param = new InstockReceiveParameter();
		// 荷主コード
		param.setConsignorCode(consignorcode);
		// 入荷予定日
		param.setPlanDate(instockplandate);
		// 仕入先コード
		param.setSupplierCode(suppliercode);
		// 商品コード
		param.setItemCode(itemcode);
		// クロス/DC区分
		param.setTcdcFlag(crossdcflag);
		// 表示順
		param.setDspOrder(dsporder);
		// 作業状態
		param.setStatusFlag(statusflag);
		
		// SessionInstockReceiveListRet インスタンス生成
		SessionInstockReceiveListRet listbox = new SessionInstockReceiveListRet(conn, param);
		// Sessionにlistboxを保持
		this.getSession().setAttribute("LISTBOX", listbox);
		setList(listbox, "first");
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * ページを変更するメソッド <BR>
	 * @param listbox
	 * @param string
	 */
   private void setList(SessionInstockReceiveListRet listbox, String actionName)
   {
	   // ロケールを取得
	   Locale locale = this.getHttpRequest().getLocale();
		
	   // ページ情報をセット
	   listbox.setActionName(actionName);

	   // 検索結果を取得
	   InstockReceiveParameter[] sparam = (InstockReceiveParameter[])listbox.getEntities();
	   int len = 0;
	   if (sparam != null)
		   len = sparam.length;
	   if (len > 0)
	   {
		   // 検索条件の荷主名称をセット
		   lbl_JavaSetConsignorName.setText(sparam[0].getConsignorName());
			
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
		   lst_InstkCrsDcWorkList.clearRow();

		   //ToolTipで使用する
		   String title_SuppulierName = DisplayText.getText("LBL-W0253");
		   String title_ItemName = DisplayText.getText("LBL-W0103");
		   String title_ResultCase = DisplayText.getText("LBL-W0418");
		   String title_ResultPiece = DisplayText.getText("LBL-W0417");
		   String title_UseByDate = DisplayText.getText("LBL-W0270");
		   String title_CrossDCFlag = DisplayText.getText("LBL-W0029");
		   
			
		   for (int i = 0; i < len; i++)
		   {
			   // 最終行を取得
			   int count = lst_InstkCrsDcWorkList.getMaxRows();
			   // 行追加
			   lst_InstkCrsDcWorkList.addRow();

			   // 最終行に移動
			   lst_InstkCrsDcWorkList.setCurrentRow(count);
			   lst_InstkCrsDcWorkList.setValue(0, sparam[i].getTcdcFlag());
			   lst_InstkCrsDcWorkList.setValue(1, sparam[i].getSupplierCode());
			   lst_InstkCrsDcWorkList.setValue(2, sparam[i].getInstockTicketNo());
			   lst_InstkCrsDcWorkList.setValue(3, sparam[i].getItemCode());
		   	   lst_InstkCrsDcWorkList.setValue(4, WmsFormatter.getNumFormat(sparam[i].getTotalPlanQty()));
			   lst_InstkCrsDcWorkList.setValue(5, WmsFormatter.getNumFormat(sparam[i].getEnteringQty()));
			   lst_InstkCrsDcWorkList.setValue(6, WmsFormatter.getNumFormat(sparam[i].getPlanCaseQty()));
			   lst_InstkCrsDcWorkList.setValue(7, WmsFormatter.getNumFormat(sparam[i].getResultCaseQty()));
			   lst_InstkCrsDcWorkList.setValue(8, sparam[i].getUseByDate());
			   lst_InstkCrsDcWorkList.setValue(9, sparam[i].getTcdcName());
			   lst_InstkCrsDcWorkList.setValue(10, sparam[i].getSupplierName());
			   lst_InstkCrsDcWorkList.setValue(11, WmsFormatter.getNumFormat(sparam[i].getInstockLineNo()));
			   lst_InstkCrsDcWorkList.setValue(12, sparam[i].getItemName());
			   lst_InstkCrsDcWorkList.setValue(13, WmsFormatter.getNumFormat(sparam[i].getBundleEnteringQty()));
			   lst_InstkCrsDcWorkList.setValue(14, WmsFormatter.getNumFormat(sparam[i].getPlanPieceQty()));
			   lst_InstkCrsDcWorkList.setValue(15, WmsFormatter.getNumFormat(sparam[i].getResultPieceQty()));
				
			   // ToolTip用のデータを編集
			   ToolTipHelper toolTip = new ToolTipHelper();
			   toolTip.add(title_SuppulierName, sparam[i].getSupplierName());
			   toolTip.add(title_ItemName, sparam[i].getItemName());
			   toolTip.add(title_ResultCase, WmsFormatter.getNumFormat(sparam[i].getResultCaseQty()));
			   toolTip.add(title_ResultPiece, WmsFormatter.getNumFormat(sparam[i].getResultPieceQty()));
			   toolTip.add(title_UseByDate, sparam[i].getUseByDate());
			   toolTip.add(title_CrossDCFlag, sparam[i].getTcdcName());

			   // TOOL TIPをセットする	
			   lst_InstkCrsDcWorkList.setToolTip(count, toolTip.getText());
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
		   lst_InstkCrsDcWorkList.setVisible(false);
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
	public void lbl_SearchCondition_Server(ActionEvent e) throws Exception
	{
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
	public void lbl_JavaSetConsignorCode_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lbl_JavaSetConsignorName_Server(ActionEvent e) throws Exception
	{
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
	public void txt_FDateInstockPlanDate_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateInstockPlanDate_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void txt_FDateInstockPlanDate_TabKey(ActionEvent e) throws Exception
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
	public void lst_InstkCrsDcWorkList_EnterKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcWorkList_TabKey(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcWorkList_InputComplete(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcWorkList_ColumClick(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcWorkList_Server(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcWorkList_Change(ActionEvent e) throws Exception
	{
	}

	/** 
	 * 
	 * @param e ActionEvent 
	 * @throws Exception 
	 */
	public void lst_InstkCrsDcWorkList_Click(ActionEvent e) throws Exception
	{
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
		// Sessionにlistboxを保持
		SessionInstockReceiveListRet listbox = (SessionInstockReceiveListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "next");
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
		// Sessionにlistboxを保持
		SessionInstockReceiveListRet listbox = (SessionInstockReceiveListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "previous");
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
		// Sessionにlistboxを保持
		SessionInstockReceiveListRet listbox = (SessionInstockReceiveListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "last");
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
		// Sessionにlistboxを保持
		SessionInstockReceiveListRet listbox = (SessionInstockReceiveListRet) this.getSession().getAttribute("LISTBOX");
		setList(listbox, "first");
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
