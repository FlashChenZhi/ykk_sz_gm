package jp.co.daifuku.wms.idm.display.web.listbox.sessionret;
/*
 * Created on 2004/09/15
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.idm.schedule.IdmControlParameter;
import jp.co.daifuku.wms.idm.dbhandler.IdmStockFinder;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 補充棚一覧リストボックス（移動ラック）用のデータを検索するためのクラスです。<BR>
 * 検索条件をパラメータとして受け取り、補充棚一覧の検索を行います。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionIdmCaseItfRet(Connection conn,IdmControlParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(IdmControlParameter param)</CODE>メソッドを呼び出し作業情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目なし<BR>
 *   <DIR>
 *     荷主コード<BR>
 *     商品コード<BR>
 *     ケース・ピース区分<BR>
 *     棚No<BR>
 *     状態フラグが在庫 <BR>
 *     在庫数が１以上 <BR>
 *     エリアNoが移動ラックエリアNo <BR>
 *   </DIR>
 *   ＜検索テーブル＞ <BR>
 *   <DIR>
 *     DMSTOCK <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.表示処理(<CODE>getEntities()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   画面に表示するためのデータを取得します。<BR>
 *   1.検索処理にて得た検索結果から表示情報を取得します。<BR>
 *   検索結果を作業情報配列にセットし返します。<BR>
 * <BR>
 *   ＜表示項目＞
 *   <DIR>
 *     ロケーションNo <BR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *     ケース/ピース区分 <BR>
 *     ケース/ピース区分名称 <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/05/17</TD><TD>kaminishizono</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class SessionIdmReplenishLocationRet extends SessionRet
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------	
	/**
	 * 検索を行うための<CODE>find(IdmControlParameter stParam)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(IdmControlParameter stParam)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param conn       <code>Connection</code>
	 * @param stParam      <code>IdmControlParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionIdmReplenishLocationRet(Connection conn, IdmControlParameter stParam) throws Exception
	{
		wConn = conn ;		
		find(stParam) ;
	}
	
	// Public methods ------------------------------------------------
	/**
	 * 在庫情報の検索結果を、指定件数分返します。<BR>
	 * このメソッドでは以下の処理を行います。<BR>
	 * <BR>
	 * <DIR>
	 *   1.表示データを何件取得するかを指定するための計算を行います。<BR>
	 *   2.結果セットから在庫情報を取得します。<BR>
	 *   3.在庫情報から表示データを取得し<CODE>IdmControlParameter</CODE>にセットします。<BR>
	 *   4.表示情報を返します。<BR>
	 * </DIR>
	 * @return 表示情報を含む<CODE>IdmControlParameter</CODE>クラス
	 */
	public IdmControlParameter[] getEntities()
	{			
		IdmControlParameter[] resultArray = null ;
		Stock[] stock = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	    	
			System.out.println("wStartpoint :" + wStartpoint + " wEndpoint :" + wEndpoint) ;
			try
			{
				stock = (Stock[])wFinder.getEntities(wStartpoint,wEndpoint) ;
				resultArray = (IdmControlParameter[])convertToIdmParams(stock) ;
			}
			catch (Exception e)
			{	    		
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
	 * 検索を行う<code>IdmStockFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param stParam      <code>IdmControlParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public void find(IdmControlParameter stParam) throws Exception
	{
		String pBank = null;
		String pBay = null;
		String pLevel = null;

		StockSearchKey stKey = new StockSearchKey() ;
		
		if(!StringUtil.isBlank(stParam.getBankNo()))
		{
			pBank = stParam.getBankNo() ;
		}
		if(!StringUtil.isBlank(stParam.getBayNo()))
		{
			pBay = stParam.getBayNo() ;
		}
		if(!StringUtil.isBlank(stParam.getLevelNo()))
		{
			pLevel = stParam.getLevelNo() ;
		}

		// WmsParamより、移動ラック用のエリアNoを取得します。
		String w_AreaNo = WmsParam.IDM_AREA_NO;
		
		// 検索条件をセットする
		stKey.KeyClear();
		stKey.setAreaNo(w_AreaNo);
		String pLocation = "";
		if (!StringUtil.isBlank(pBank))
		{
			pLocation += pBank + '-';
		}
		else
		{
			pLocation += "__-";
		}
		if (!StringUtil.isBlank(pBay))
		{
			pLocation += pBay + '-';
		}
		else
		{
			pLocation += "__-";
		}
		if (!StringUtil.isBlank(pLevel))
		{
			pLocation += pLevel;
		}
		else
		{
			pLocation += "__";
		}
		stKey.setLocationNo(pLocation, "LIKE");
		if (!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			stKey.setConsignorCode(stParam.getConsignorCode());
		}
		if (!StringUtil.isBlank(stParam.getItemCode()))
		{
			stKey.setItemCode(stParam.getItemCode());
		}
		if ((!StringUtil.isBlank(stParam.getCasePieceFlag()))	&&
			(!stParam.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_ALL)))
		{
			stKey.setCasePieceFlag(stParam.getCasePieceFlag());
		}
		// 状態フラグが在庫。
		stKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 数量が１以上
		stKey.setStockQty(0, ">");
		// 集約条件を指定します。
		stKey.setLocationNoGroup(1);
		stKey.setConsignorCodeGroup(2);
		stKey.setCasePieceFlagGroup(3);
		stKey.setItemCodeGroup(4);
		stKey.setItemName1Group(5);
		
		// 取得項目を定義します。
		stKey.setLocationNoCollect("");
		stKey.setCasePieceFlagCollect("");
		stKey.setItemCodeCollect("");
		stKey.setItemName1Collect("");
		
		// ロケーションNoの昇順に取得します。
		stKey.setLocationNoOrder(1, true);
		stKey.setItemCodeOrder(2, true);
		stKey.setCasePieceFlagOrder(3, true);

		wFinder = new IdmStockFinder(wConn) ;
		// カーソルをオープン
		wFinder.open() ;
		
		// 検索を実行
		int count = wFinder.search(stKey);
		wLength = count ;
		wCurrent = 0 ;
		
	}

	/**
	 * このメソッドは、<CODE>Stock</CODE>エンティティを<CODE>IdmControlParameter</CODE>パラメータに変換します。<BR>
	 * @param ety エンティティ配列
	 * @return IdmControlParameter[] 在庫情報をセットした<CODE>IdmControlParameter</CODE>パラメータ
	 */
	private Parameter[] convertToIdmParams(Entity[] ety)
	{
		IdmControlParameter[] stParam = null ;
		
		Stock[] stock = (Stock[])ety ;

		if((stock != null) && (stock.length != 0))
		{
			stParam = new IdmControlParameter[stock.length] ;
			for(int i=0; i < stock.length; i++)
			{
				stParam[i] = new IdmControlParameter() ;
				stParam[i].setLocationNo(stock[i].getLocationNo()) ;	// ロケーションNo
				stParam[i].setConsignorCode(stock[i].getConsignorCode()) ;		// 荷主コード
				stParam[i].setConsignorName(stock[i].getConsignorName()) ;		// 荷主名称
				stParam[i].setItemCode(stock[i].getItemCode()) ;		// 商品コード
				stParam[i].setItemName(stock[i].getItemName1()) ;		// 商品名称
				stParam[i].setCasePieceFlagDisp(stock[i].getCasePieceFlag()) ;	// ケース/ピース区分
			}
		}	
		
		return stParam ;
		
	}
}
//end of class
