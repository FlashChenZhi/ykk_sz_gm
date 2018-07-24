// $Id: SessionShippingResultConsignorRet.java,v 1.1.1.1 2006/08/17 09:34:28 mori Exp $
package jp.co.daifuku.wms.shipping.display.web.listbox.sessionret;

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
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewFinder;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.shipping.schedule.ShippingParameter;

/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * <CODE>SessionShippingResultConsignorRet</CODE>クラスは、実績情報Viewから<BR>
 * 荷主一覧リストボックス用の検索を行うクラスです。<BR>
 * 検索条件をパラメータとして受け取り、荷主一覧の検索を行います。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。<BR>
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionShippingConsignorRet(Connection conn,ShippingParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(ShippingParameter param)</CODE>メソッドを呼び出し実績情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目*<BR>
 *   <DIR>
 *     荷主コード<BR>
 *     区分：出荷*<BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DVRESULTVIEW <BR>
 *   </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   1.検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を実績情報配列にセットし返します。<BR>
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
 * <TR><TD>2008/08/16</TD><TD>M.INOUE</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:28 $
 * @author  $Author: mori $
 */
public class SessionShippingResultConsignorRet extends SessionRet
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
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:28 $");
	}

	/**
	 * 検索を行うための<CODE>find(ShippingParameter param)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(ShippingParameter param)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * <DIR>
	 * ＜検索条件＞<BR>
	 * 荷主コード<BR>
	 * 区分：出荷<BR>
	 * </DIR>
	 * @param conn       <code>Connection</code>
	 * @param param      <code>ShippingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます
	 */
	public SessionShippingResultConsignorRet(Connection conn, ShippingParameter param) throws Exception
	{
		this.wConn = conn;
		
		// 検索
		find(param);
	}

	// Public methods ------------------------------------------------

	/**
	 * <CODE>ResultView</CODE>の検索結果を返します。
	 * <DIR>
	 * ＜検索結果＞<BR>
	 * 荷主コード<BR>
	 * 荷主名称<BR>
	 * </DIR>
	 * @return ResultViewの検索結果
	 */
	public Parameter[] getEntities()
	{
		ShippingParameter[] resultArray = null;
		ResultView[] temp = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				temp = (ResultView[]) ((ResultViewFinder)wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToShippingParams(temp);
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
	 * 検索を行う<code>ShippingPlanFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param param      <code>ShippingParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます
	 */
	private void find(ShippingParameter param) throws Exception
	{
		int count = 0;

		ResultViewSearchKey skey = new ResultViewSearchKey();
		// 検索実行
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		else
		{
			skey.setConsignorCode("","IS NOT NULL");
		}
		// 作業区分（出荷検品）
		skey.setJobType(SystemDefine.JOB_TYPE_SHIPINSPECTION);
		
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);
		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);
		if(param.getSearchStatus() != null && param.getSearchStatus().length > 0)
		{
			String[] search = new String[param.getSearchStatus().length];
			for(int i = 0; i < param.getSearchStatus().length; ++i)
			{
				if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_UNSTARTING))
				{
					search[i] = ResultView.STATUS_FLAG_UNSTART;
				}
				else if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_START))
				{
					search[i] = ResultView.STATUS_FLAG_START;
				}
				else if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_NOWWORKING))
				{
					search[i] = ResultView.STATUS_FLAG_NOWWORKING;
				}
				else if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_RECEPTION_IN_PART))
				{
					search[i] = ResultView.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if(param.getSearchStatus()[i].equals(ShippingParameter.WORKSTATUS_FINISH))
				{
					search[i] = ResultView.STATUS_FLAG_COMPLETION;
				}
				else
				{
					search[i] = "*";
				}
			}
			skey.setStatusFlag(search);
		}
		else
		{
			skey.setStatusFlag(ResultView.STATUS_FLAG_DELETE, "!=");
		}
		wFinder = new ResultViewFinder(wConn);
		// カーソルオープン
		wFinder.open();
		count = ((ResultViewFinder)wFinder).search(skey);
		// 初期化
		wLength = count;
		wCurrent = 0;
	}
	
	/**
	 * このクラスは ResultView エンティティ を ShippingParameter パラメータに設定する。 <BR>
	 * 
	 * @param shippingVIew 実績情報View
	 * @return 実績情報Viewをセットした<CODE>ShippingParameter</CODE>クラスの配列
	 */
	private ShippingParameter[] convertToShippingParams(ResultView[] shippingVIew)
	{
		ShippingParameter[] stParam = null;
		
		if (shippingVIew == null || shippingVIew.length==0)
		{	
		 	return null;
		}
			stParam = new ShippingParameter[shippingVIew.length];
			for (int i = 0; i < shippingVIew.length; i++)
			{
					stParam[i] = new ShippingParameter();
					stParam[i].setConsignorCode(shippingVIew[i].getConsignorCode());
					stParam[i].setConsignorName(shippingVIew[i].getConsignorName());
				
			}
			
		return stParam;
	}
	
}
//end of class
