package jp.co.daifuku.wms.master.display.web.listbox.sessionret;
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.master.operator.ConsignorCodeFinder;
import jp.co.daifuku.wms.master.schedule.MasterParameter;

/**
 * Designer :  hota<BR>
 * Maker :  hota<BR>
 * <BR>
 * 荷主一覧リストボックス（マスタパッケージ 出荷先マスタ）用のデータを検索するためのクラスです。<BR>
 * 検索条件をパラメータとして受け取り、荷主一覧の検索を行います。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionConsignorCustomerRet(Connection conn,	MasterParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(MasterParameter param)</CODE>メソッドを呼び出し荷主マスタ 出荷先マスタの検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目なし<BR>
 *   <DIR>
 *     荷主コード<BR>
 *     出荷予定日<BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DMCONSIGNOR <BR>
 *     DMCUSTOMER <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   1.検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を出荷予定情報配列にセットし返します。<BR>
 * <BR>
 *   ＜表示項目＞
 *   <DIR>
 *     荷主コード<BR>
 *     荷主名称<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>YYYY/MM/DD</TD><TD>  </TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/10 00:34:27 $
 * @author  $Author: suresh $
 */
public class SessionMasterConsignorCustomerRet extends SessionRet
{
	// Class fields -----------------------------------------------------

	/**
	 * 検索結果を保持する変数。
	 */
	static protected ResultSet wResultSet = null ;
	
	/**
	 * 検索フラグ（出荷先）
	 */
	final protected String serchFlg = "2";
	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/10 00:34:27 $");
	}

	/**
	 * 検索を行うための<CODE>find(ShippingParameter param)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(ShippingParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      <code>MasterParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionMasterConsignorCustomerRet(Connection conn, MasterParameter param) throws Exception
	{
		this.wConn = conn;
		
		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * <CODE>DMCONSIGNOR</CODE>の検索結果を返します。
	 * <DIR>
	 * ＜検索結果＞
	 * ･荷主コード<BR>
	 * ･荷主名称<BR>
	 * </DIR>
	 * @return DMCONSIGNORの検索結果
	 * @throws SQLException
	 */
	public Parameter[] getEntities() throws SQLException
	{
		
	    MasterParameter[] resultArray = null;
	    Consignor temp[] = null;
	    
	    if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
	    {
	        try
	        {
	            temp = (Consignor[]) ((ConsignorCodeFinder)wFinder).getEntities(wStartpoint, wEndpoint);
	            resultArray = convertToMasterParams(temp);
	        }
			catch (Exception e)
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
	 * 入力されたパラメータをもとにSQL文を発行します。<BR>
	 * 検索を行う<code>ConsignorFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param param      <code>MasterParameter</code>検索結果を含むパラメータ
	 */
	private void find(MasterParameter param) throws Exception
	{
		ConsignorSearchKey skey = new ConsignorSearchKey();
		
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		else
		{
			skey.setConsignorCode("","IS NOT NULL");
		}
		
	    int count = 0;

		wFinder = new ConsignorCodeFinder(wConn);
		// カーソルオープン
		wFinder.open();
		count = ((ConsignorCodeFinder)wFinder).searchConsinor(skey, serchFlg);
		// 初期化
		wLength = count;
		wCurrent = 0;
	}
	
	/**
	 * このクラスは Consignor エンテイテイ を MasterParameter パラメータに設定する。 <BR>
	 * 
	 * @param Consignor 荷主情報
	 * @return MasterParameter[] 荷主情報をセットした<CODE>MasterParameter</CODE>クラス
	 */
	private MasterParameter[] convertToMasterParams(Consignor[] consignor)
	{
	    MasterParameter[] stParam = null;
	    
	    if (consignor == null || consignor.length == 0)
	    {
	        return null;
	    }
	    stParam = new MasterParameter[consignor.length];
	    for (int i = 0; i < consignor.length; i++)
	    {
	        stParam[i] = new MasterParameter();
	        stParam[i].setConsignorCode(consignor[i].getConsignorCode());
	        stParam[i].setConsignorName(consignor[i].getConsignorName());
	    }
	    
        return stParam;
	}

}
