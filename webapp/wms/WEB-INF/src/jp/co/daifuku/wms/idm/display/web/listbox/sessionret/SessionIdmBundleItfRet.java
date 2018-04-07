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
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.idm.dbhandler.IdmStockFinder;
import jp.co.daifuku.wms.idm.schedule.IdmControlParameter;
import jp.co.daifuku.wms.idm.schedule.IdmOperate;

/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * ボールITF一覧リストボックス（移動ラック）用のデータを検索するためのクラスです。<BR>
 * 検索条件をパラメータとして受け取り、ボール一覧の検索を行います。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionIdmBundleItfRet(Connection conn,IdmControlParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(IdmControlParameter param)</CODE>メソッドを呼び出し作業情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目なし<BR>
 *   <DIR>
 *     荷主コード<BR>
 *     商品コード<BR>
 *     開始棚<BR>
 *     終了棚<BR>
 *     ケース・ピース区分<BR>
 *     ケースITF<BR>
 *     ボールITF<BR>
 *     状態フラグが在庫 <BR>
 *     在庫数が１以上 <BR>
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
 *     ボールITF<BR>
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
public class SessionIdmBundleItfRet extends SessionRet
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * 検索を行うための<CODE>find(IdmControlParameter stParam)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(IdmControlParameter stParam)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      <code>StockControlParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionIdmBundleItfRet(Connection conn, IdmControlParameter stParam) throws Exception
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
			try
			{
				stock = (Stock[])wFinder.getEntities(wStartpoint,wEndpoint) ;
				resultArray = convertToIdmParams(stock) ;
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
		StockSearchKey stKey = new StockSearchKey() ;
		
		if(!StringUtil.isBlank(stParam.getConsignorCode()))
		{
			stKey.setConsignorCode(stParam.getConsignorCode()) ;
		}
	
		if(!StringUtil.isBlank(stParam.getItemCode()))
		{
			stKey.setItemCode(stParam.getItemCode()) ;
		}
		
		if(stParam.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_CASE))
		{
			stKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_CASE) ;
		}
		else if(stParam.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_PIECE))
		{
			stKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_PIECE) ;
		}
		else if(stParam.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			stKey.setCasePieceFlag(Stock.CASEPIECE_FLAG_NOTHING) ;
		}
		else if(stParam.getCasePieceFlag().equals(IdmControlParameter.CASEPIECE_FLAG_ALL))
		{
			// 何もしません
		}

		if(!StringUtil.isBlank(stParam.getStartBankNo()))
		{
			IdmOperate iOpe = new IdmOperate();
			stKey.setLocationNo(iOpe.importFormatIdmLocation(stParam.getStartBankNo(), stParam.getStartBayNo(), stParam.getStartLevelNo()), ">=") ;
		}
		
		if(!StringUtil.isBlank(stParam.getEndBankNo()))
		{
			IdmOperate iOpe = new IdmOperate();
			String chToLocation = iOpe.importFormatIdmLocation(stParam.getEndBankNo(), stParam.getEndBayNo(), stParam.getEndLevelNo()).replaceAll(" ", "Z");
			stKey.setLocationNo(chToLocation,"<=") ;
		}
		
		if(!StringUtil.isBlank(stParam.getITF()))
		{
			stKey.setItf(stParam.getITF()) ;
		}
		
		if(!StringUtil.isBlank(stParam.getBundleITF()))
		{
			stKey.setBundleItf(stParam.getBundleITF()) ;
		}
		
		// 状態フラグが在庫
		stKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 在庫数が１以上
		stKey.setStockQty(0,">");
		
		// ボールITFがNull以外
		stKey.setBundleItf("","IS NOT NULL");
		
		// 取得順をセット
		// ボールITF
		stKey.setBundleItfCollect();
		// 荷主名称
		stKey.setConsignorNameCollect("MIN");
		// 商品名称
		stKey.setItemName1Collect("MIN");
						
		// ソート順をセット			
		stKey.setBundleItfOrder(1,true);

		// グループ順をセット
		stKey.setBundleItfGroup(1);
		
		// IdmStockFinderオブジェクトを作成
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
	 * @param stock 在庫情報
	 * @return IdmControlParameter[] 在庫情報をセットした<CODE>IdmControlParameter</CODE>パラメータ
	 */
	private IdmControlParameter[] convertToIdmParams(Stock[] stock)
	{
		IdmControlParameter[] stParam = null ;
		if((stock != null) && (stock.length != 0))
		{
			stParam = new IdmControlParameter[stock.length] ;
			
			for(int i=0; i < stock.length; i++)
			{
				stParam[i] = new IdmControlParameter() ;
				if((stock[i].getBundleItf() != null) && !stock[i].getBundleItf().equals(""))
				{					
					stParam[i].setBundleITF(stock[i].getBundleItf()) ; //Bundle ITF
					stParam[i].setConsignorName(stock[i].getConsignorName()); // 荷主名称
					stParam[i].setItemName(stock[i].getItemName1()); // 商品名称
				}			
			}
		}		
		return stParam ;
	}
}
//end of class
