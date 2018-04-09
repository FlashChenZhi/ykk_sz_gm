// $Id: Id0920Operate.java,v 1.1.1.1 2006/08/17 09:34:10 mori Exp $
package jp.co.daifuku.wms.idm.rft;
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.LocateHandler;
import jp.co.daifuku.wms.base.dbhandler.LocateSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Locate;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.idm.schedule.IdmOperate;

/**
 * <FONT COLOR="BLUE">
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * 移動ラック空棚データ要求に対するデータ処理を行ないます。 <BR>
 * 空棚区分に従い、移動ラック内の対象棚No一覧情報を取得します。 <BR>
 * Id0920Processから呼び出されるビジネスロジックが実装されます。 <BR>
 * <BR>
 * <DIR>
 *  -空棚区分が空棚の場合 <BR><DIR>
 *    移動ラック内の状態フラグが空棚の棚No一覧情報を取得して返却します。 <BR></DIR>
 *  <BR>
 *  -空棚区分が補充棚の場合 <BR><DIR>
 *    移動ラック内の状態フラグが実棚の棚No一覧情報を取得して返却します。 <BR></DIR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/05</TD><TD>C.Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 * <BR>
 */
public class Id0920Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	// Class variables -----------------------------------------------
	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 * @param なし
	 */
	public Id0920Operate()
	{
		super();
	}

	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param <code>conn</code> DBConnection情報
	 */
	public Id0920Operate(Connection conn)
	{
		super();
		wConn = conn;
	}
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $");
	}

	/**
	 * 空棚一覧情報を、ロケーション情報より取得します。<BR>
	 * @return String[]        対象棚Noの配列
	 * @throws ReadWriteException ファイル入出力エラー発生時に通知されます。
	 */
	public String[] getLocateData() throws ReadWriteException

	{
		// 検索条件作成Method生成
		IdmOperate sObj = new IdmOperate();
		
		// ロケーション情報のインスタンス生成		
		LocateHandler locateHandler = new LocateHandler(wConn);
		LocateSearchKey locateSearchKey = new LocateSearchKey();
		
		Vector vec = new Vector();		
		// 検索条件作成
		locateSearchKey = sObj.setVacantLocationKey(locateSearchKey, null, null, null);
		// ロケーション情報を取得します。
		Locate[] rLocate = (Locate[])locateHandler.find(locateSearchKey);
			
			
		if (rLocate.length == 0)		return null;
			
		for (int lc=0; lc<rLocate.length; lc++)
		{
			vec.addElement(rLocate[lc].getLocationNo());
		}

		String[] locate = new String[vec.size()];
		vec.copyInto(locate);
		return locate;
		
	}


	/**
	 * 補充棚一覧情報を、在庫情報より取得します。<BR>
	 * @param  pConsignorCode  荷主コード
	 * @param  pItemCode       商品コード
	 * @param  pCasePieceFlag  ケース・ピース区分
	 * @return String[]        対象棚Noの配列
	 * @throws ReadWriteException ファイル入出力エラー発生時に通知されます。
	 */
	public String[] getStockData(String pConsignorCode, String pItemCode, String pCasePieceFlag) throws ReadWriteException
	{
		
		// 検索条件作成Method生成
		IdmOperate sObj = new IdmOperate();
		// 在庫情報のインスタンス生成		
		StockHandler stockHandler = new StockHandler(wConn);
		StockSearchKey stockSearchKey = new StockSearchKey();
		
		Vector vec = new Vector();					
		// 検索条件作成。
		stockSearchKey = sObj.setReplenishLocationKey(stockSearchKey, null, null, null,
												pConsignorCode, pItemCode, pCasePieceFlag);
			
		// ロケーション情報を取得します。
		Stock[] rStock = (Stock[])stockHandler.find(stockSearchKey);
			
		if (rStock.length == 0)		return null;
			
		for (int lc=0; lc<rStock.length; lc++)
		{
			vec.addElement(rStock[lc].getLocationNo());
		}

		String[] locate = new String[vec.size()];
		vec.copyInto(locate);
		return locate;
			
	}

	// Package methods -----------------------------------------------
	
	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
