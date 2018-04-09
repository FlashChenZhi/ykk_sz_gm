//$Id: SessionMasterSupplierRet.java,v 1.2 2006/11/10 00:34:24 suresh Exp $
package jp.co.daifuku.wms.master.display.web.listbox.sessionret;

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.SupplierFinder;
import jp.co.daifuku.wms.base.dbhandler.SupplierSearchKey;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Supplier;
import jp.co.daifuku.wms.master.schedule.MasterParameter;

/**
 * 出荷先一覧リストボックス（出荷予定）用のデータを検索するためのクラスです。 <BR>
 * 検索条件をパラメータとして受け取り、出荷先一覧の検索を行います。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。 <BR>
 * 使用後はセッションから削除して下さい。 <BR>
 * このクラスでは以下の処理を行います。 <BR>
 * <BR>
 * 1.検索処理(<CODE>SessionSupplierRet(Connection conn,	MasterParameter param)</CODE>メソッド) <BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。 <BR>
 *   <CODE>find(MasterParameter param)</CODE>メソッドを呼び出し対象情報の検索を行います。 <BR>
 * <BR>
 *   ＜検索条件＞ 必須項目* <BR>
 *   <DIR>
 *     ･荷主コード <BR>
 *     ･出荷先コード <BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     ･DMSupplier <BR>
 *   </DIR>
 *   ＜返却データ＞ <BR>
 *   <DIR>
 *    ･対象件数<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   1.検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を出荷先情報配列にセットし返します。<BR>
 *   ＜表示項目＞<BR>
 *   <DIR>
 *     ･出荷先コード<BR>
 *     ･出荷先名称<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>//</TD><TD></TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * 
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:34:24 $
 * @author  $Author: suresh $
 */
public class SessionMasterSupplierRet extends SessionRet 
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/10 00:34:24 $");
	}

	/**
	 * 検索キーをセットしSQL文を発行します。<BR>
	 * <DIR>
	 *   ＜検索条件＞ 必須項目* <BR>
	 *   <DIR>
	 *     ･荷主コード <BR>
	 *     ･出荷先コード <BR>
	 *   </DIR>
	 *   ＜返却データ＞ <BR>
	 *   <DIR>
	 *    ･対象件数<BR>
	 *   </DIR>
	 * </DIR>
	 * @param conn       <code>Connection</code>
	 * @param param      <code>MasterParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionMasterSupplierRet(Connection conn, MasterParameter param) throws Exception
	{
	    this.wConn = conn;
	    
	    // 検索
	    find(param);
	}

	// Public methods ------------------------------------------------

	/**
	 * <CODE>出荷先情報</CODE>の検索結果を返します。 <BR>
	 * <DIR>
	 * ＜検索結果＞ <BR>
	 *   <DIR>
	 *     ･出荷先コード<BR>
	 *     ･出荷先名称<BR>
	 *   </DIR>
	 * </DIR>
	 * @return 出荷先情報の検索結果
	 */
	public MasterParameter[] getEntities()
	{
	    MasterParameter[] resultArray = null;
	    Supplier temp[] = null;
	    
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try{	            
	            temp = (Supplier[]) ((SupplierFinder)wFinder).getEntities(wStartpoint, wEndpoint);
	            resultArray = convertToMasterParams(temp);
			}
			catch(Exception e)
			{
				//エラーをログファイルに落とす
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
	/**
	 * 入力されたパラメータをもとにSQL文を発行します。 <BR>
	 * 検索を行う<code>SupplierFinder</code>はインスタンス変数として保持します。 <BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。 <BR>
	 * @param param      <code>MasterParameter</code>検索結果を含むパラメータ
     */
    private void find(MasterParameter param) throws Exception
    {
        SupplierSearchKey skey = new SupplierSearchKey();
        
		// 検索実行
		// 荷主コード
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		else
		{
			skey.setConsignorCode("","IS NOT NULL");
		}
		// 出荷先コード
		if (!StringUtil.isBlank(param.getSupplierCode()))
		{
			skey.setSupplierCode(param.getSupplierCode());
		}
		else
		{
			skey.setSupplierCode("","IS NOT NULL");
		}
		// グループ順をセットします
		skey.setConsignorCodeGroup(1);
		skey.setSupplierCodeGroup(2);
		skey.setSupplierName1Group(3);
		// 取得順をセットします
		skey.setConsignorCodeCollect("");
		skey.setSupplierCodeCollect("");
		skey.setSupplierName1Collect("");
		// ソート順をセットします
		skey.setConsignorCodeOrder(1, true);
		skey.setSupplierCodeOrder(2, true);
		
		wFinder = new SupplierFinder(wConn);
		// カーソルオープン
		wFinder.open();
		int count = ((SupplierFinder)wFinder).search(skey);
		// 初期化
		wLength = count;
		wCurrent = 0;
    }
	
	/**
	 * このクラスは SupplierFinder エンティティ を MasterParameter パラメータに設定する。 <BR>
	 * 
	 * @param Supplier	出荷先情報
	 * @return MasterParameter[] 出荷先情報をセットした<CODE>MasterParameter</CODE>クラス
	 */
	private MasterParameter[] convertToMasterParams(Supplier[] Supplier)
	{
	    MasterParameter[] stParam = null;
		
		if (Supplier == null || Supplier.length==0)
		{	
		 	return null;
		}
		stParam = new MasterParameter[Supplier.length];
		for (int i = 0; i < Supplier.length; i++)
		{
				stParam[i] = new MasterParameter();
				stParam[i].setConsignorCode(Supplier[i].getConsignorCode());
				stParam[i].setSupplierCode(Supplier[i].getSupplierCode());
				stParam[i].setSupplierName(Supplier[i].getSupplierName1());
		}
			
		return stParam;
	}
	
}
//end of class
