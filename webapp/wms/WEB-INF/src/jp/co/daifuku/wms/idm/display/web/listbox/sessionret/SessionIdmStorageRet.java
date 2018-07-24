package jp.co.daifuku.wms.idm.display.web.listbox.sessionret;
/*
 * Created on 2004/09/27 Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights
 * Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd. Use is
 * subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.idm.dbhandler.IdmStockFinder;
import jp.co.daifuku.wms.idm.schedule.IdmControlParameter;


/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 在庫一覧リストボックス（移動ラック）用のデータを検索するためのクラスです。<BR>
 * 検索条件をパラメータとして受け取り、商品コード一覧の検索を行います。<BR>
 * 尚、このクラスを使用する際はインスタンスをセッションに保持して下さい。
 * 使用後はセッションから削除して下さい。<BR>
 * このクラスでは以下の処理を行います。<BR>
 * <BR>
 * 1.検索処理(<CODE>SessionIdmItemRet(Connection conn,IdmControlParameter param)</CODE>メソッド)<BR>
 * <DIR>
 *   リストボックス画面が初期表示された時に実行されます。<BR>
 *   <CODE>find(IdmControlParameter param)</CODE>メソッドを呼び出し作業情報の検索を行います。<BR>
 * <BR>
 *   ＜検索条件＞ 必須項目なし<BR>
 *   <DIR>
 *     荷主コード <BR>
 *     商品コード <BR>
 *     ケース/ピース区分 <BR>
 *     状態フラグが在庫 <BR>
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
 *     荷主コード <BR>
 *     荷主名称 <BR>
 *     商品コード <BR>
 *     商品名称 <BR>
 *     ケース/ピース区分名称 <BR>
 *     棚No <BR>
 *     ケース入数 <BR>
 *     ボール入数 <BR>
 *     ケースITF <BR>
 *     ボールITF <BR>
 *     賞味期限 <BR>
 *     入庫日時 <BR>
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
public class SessionIdmStorageRet extends SessionRet
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
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $");
	}

	/**
	 * 検索を行うための<CODE>find(IdmControlParameter stParam)</CODE>メソッドを呼び出します。<BR>
	 * <CODE>find(IdmControlParameter stParam)</CODE>メソッドでは取得件数が何件あるかをセットします。<BR>
	 * また、検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param conn       <code>Connection</code>
	 * @param param      <code>IdmControlParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	public SessionIdmStorageRet(Connection conn, IdmControlParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	// Public methods ------------------------------------------------
	/**
	 * <CODE>DMSTOCK</CODE>の検索結果を返します。
	 * <DIR>
	 * ＜検索結果＞ <BR>
	 *     荷主コード <BR>
	 *     荷主名称 <BR>
	 *     商品コード <BR>
	 *     商品名称 <BR>
	 *     ケース/ピース区分名称 <BR>
	 *     棚No <BR>
	 *     ケース入数 <BR>
	 *     ボール入数 <BR>
	 *     ケースITF <BR>
	 *     ボールITF <BR>
	 *     賞味期限 <BR>
	 *     入庫日時 <BR>
	 * </DIR>
	 * @return DMSTOCKの検索結果
	 */
	public Parameter[] getEntities()
	{
		IdmControlParameter[] resultArray = null;
		Stock temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try
			{	
				temp = (Stock[])((IdmStockFinder)wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToStockParams(temp);
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
	 * 検索を行う<code>IdmStockFinder</code>はインスタンス変数として保持します。<BR>
	 * 検索結果を取得するには<code>getEntities</code>メソッドを呼ぶ必要があります。<BR>
	 * @param param      <code>IdmControlParameter</code>検索結果を含むパラメータ
	 * @throws Exception 何らかの例外が発生した場合に通知されます。
	 */
	private void find(IdmControlParameter stParam) throws Exception
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
			// 何もしません。
		}
		// 状態フラグが在庫
		stKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		// 在庫数が１以上
		stKey.setStockQty(0, ">");
		
		// オーダー順をセット
		stKey.setItemCodeOrder(1, true);
		stKey.setItemName1Order(2,true);
		stKey.setCasePieceFlagOrder(3,true);
		stKey.setLocationNoOrder(4,true);
		stKey.setUseByDateOrder(5,true);
	
		wFinder = new IdmStockFinder(wConn);
		// カーソルオープン
		wFinder.open();
		int count = ((IdmStockFinder)wFinder).search(stKey);
		// 初期化
		wLength = count;
		wCurrent = 0;
			
	}
		
	/**
	 * このメソッドは、<CODE>Stock</CODE>エンティティを<CODE>IdmControlParameter</CODE>パラメータに変換します。<BR>
	 * @param stock エンティティ配列
	 * @return IdmControlParameter[] 在庫情報をセットした<CODE>IdmControlParameter</CODE>パラメータ
	 */
	private IdmControlParameter[] convertToStockParams(Stock[] stock)
	{
		IdmControlParameter[] stParam = null;		
		if (stock == null || stock.length==0)
		{	
			return null;
		}
		stParam = new IdmControlParameter[stock.length];
		for (int i = 0; i < stock.length; i++)
		{
			stParam[i] = new IdmControlParameter();
			stParam[i].setConsignorCode(stock[i].getConsignorCode());
			stParam[i].setConsignorName(stock[i].getConsignorName());
			stParam[i].setItemCode(stock[i].getItemCode());
			stParam[i].setItemName(stock[i].getItemName1());
			stParam[i].setCasePieceFlagDisp(stock[i].getCasePieceFlag());
			stParam[i].setCasePieceFlagNameDisp(DisplayUtil.getPieceCaseValue(stock[i].getCasePieceFlag()));
			stParam[i].setLocationNo(stock[i].getLocationNo());
			stParam[i].setEnteringQty(stock[i].getEnteringQty());
			stParam[i].setBundleEnteringQty(stock[i].getBundleEnteringQty());
			stParam[i].setITF(stock[i].getItf());
			stParam[i].setBundleITF(stock[i].getBundleItf());
			stParam[i].setUseByDate(stock[i].getUseByDate());
			stParam[i].setStorageDate(stock[i].getInstockDate());
		}
			
		return stParam;
	}
}
//end of class
