// $Id: Id0013Operate.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $
package jp.co.daifuku.wms.master.base.rft;
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;

/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * RFTからの例外入庫商品データ要求に対するデータ処理を行ないます。<BR>
 * Id0013Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * 例外入庫データ検索処理(<CODE>findStockData()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *  荷主コード＋JANコードで在庫情報を検索し、最も新しい入庫日のデータを取得します。<BR>
 *  該当データが無かった場合は、NotFoundExceptionを返します。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class Id0013Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	// Class variables -----------------------------------------------
	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 * @param なし
	 */
	public Id0013Operate()
	{
		super();
	}

	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn		DBConnection情報
	 */
	public Id0013Operate(Connection conn)
	{
		super();
		wConn = conn;
	}

	/**
	 * WareNaviManager
	 */
	private WareNaviSystemManager wWmsManager = null;
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:15 $";
	}

	/**
	 * 商品データを、在庫情報から取得します。<BR>
	 * パラメータとして、荷主コード、スキャン商品コード、スキャン商品コード2、
	 * 荷姿、RFT号機番号、作業者コードを受け取ります。<BR>
	 * 荷主コード、、スキャン商品コード、スキャン商品コード2、荷姿をパラメータとして、
	 * 在庫データ検索メソッド(getStockData)を呼び出します。<BR>
	 * 検索結果がnullの場合
	 * 在庫データ検索(荷姿を検索条から外す)メソッド(getStockDataNoItemForm)を呼び出します。<BR>
	 * <DIR>
	 *  在庫データ検索メソッドでデータが取得できない場合は、Nullを返します。<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	itemCode 		商品コード
	 * @param	convertedJanCodeスキャン商品コード2
	 * @param	itemForm	 	荷姿
	 * @param	rftNo			RFT号機番号
	 * @param	workerCode		作業者コード
	 * @return	在庫情報エンティティ配列
	 * @throws ReadWriteException
	 * @throws ScheduleException
	*/
	public Stock getItemFromStock(
		String consignorCode,
		String itemCode,
		String convertedJanCode,
		String itemForm,
		String rftNo,
		String workerCode)
		throws ReadWriteException, ScheduleException
	{

		// 在庫情報を検索した結果を保持する変数
		Stock stockData = null;
		
		// 在庫情報を検索する(荷姿を検索条件に含む)
		stockData = getStockData(consignorCode, itemCode, convertedJanCode, itemForm);
		if (stockData == null)
		{
			// 検索結果がnullの場合
			
			// 在庫情報を検索する(荷姿を検索条件から外す)
			stockData = getStockData(consignorCode, itemCode, convertedJanCode, null);
			if (stockData != null)
			{
				// 検索結果がnullでない場合
				// エリアNo. ロケーションNo.に空文字列
				stockData.setAreaNo("");
				stockData.setLocationNo("");
			}
		}
		return stockData;
	}

	/**
	 * 商品マスタ情報から、指定条件に該当する商品情報を取得します。<BR>
	 * <BR>
	 * 商品マスタ情報を次の条件で検索します。
	 * 荷主コード    =パラメータより取得<BR>
	 * 削除区分=0:使用可<BR>
	 * 商品マスタ情報を次の項目で検索します。
	 * スキャン商品コード1を商品コード→JANコード→ケースITF→ボールITF →
	 * (スキャン商品コード2が空白以外の場合)スキャン商品コード2を商品コード→JANコード の順に検索します。
	 * 該当データが見つかった時点でそのデータを返します。該当データが無い場合は、nullを返します。
	 * <BR>
	 * @param	consignorCode	荷主コード<BR>
	 * @param	itemCode 		スキャン商品コード1<BR>
	 * @param	convertedJanCodeスキャン商品コード2<BR>
	 * @param	itemForm	 	荷姿
	 * @param	rftNo			RFT号機番号
	 * @param	workerCode		作業者コード
	 * @return	商品情報エンティティ<BR>
	 * @throws ReadWriteException
	 * @throws ScheduleException
	*/
	public Item getItemFromMaster(
			String consignorCode,
			String itemCode,
			String convertedJanCode,
			String itemForm,
			String rftNo,
			String workerCode)
			throws ReadWriteException, ScheduleException
		{
			// 戻り値を保持する配列
			Item[] itemData	= null;
			
			ItemSearchKey skey = new ItemSearchKey();
			ItemHandler itemHandler = new ItemHandler(wConn);
			//-----------------
			// スキャンされたコード1を商品コードとして検索を行う
			//-----------------
			skey.KeyClear();
			skey.setConsignorCode(consignorCode);
			skey.setItemCode(itemCode);	
			// 検索を行う
			itemData = (Item[]) itemHandler.find(skey);
			if (itemData != null && itemData.length > 0)
			{
				return itemData[0];
			}
			//-----------------
			// スキャンされたコード1をJANコードとして検索を行う
			//-----------------
			skey.KeyClear();
			skey.setConsignorCode(consignorCode);
			skey.setJAN(itemCode);	
			// 検索を行う
			itemData = (Item[]) itemHandler.find(skey);
			if (itemData != null && itemData.length > 0)
			{
				return itemData[0];
			}
			//-----------------
			// スキャンされたコード1をケースITFとして検索を行う
			//-----------------
			skey.KeyClear();
			skey.setConsignorCode(consignorCode);
			skey.setITF(itemCode);	
			// 検索を行う
			itemData = (Item[]) itemHandler.find(skey);
			if (itemData != null && itemData.length > 0)
			{
				return itemData[0];
			}
			//-----------------
			// スキャンされたコード1をボールITFとして検索を行う
			//-----------------
			skey.KeyClear();
			skey.setConsignorCode(consignorCode);
			skey.setBundleItf(itemCode);	
			// 検索を行う
			itemData = (Item[]) itemHandler.find(skey);
			if (itemData != null && itemData.length > 0)
			{
				return itemData[0];
			}
			
			if (! StringUtil.isBlank(convertedJanCode))
			{
				//-----------------
				// スキャンされたコード2を商品コードとして検索を行う
				//-----------------
				skey.KeyClear();
				skey.setConsignorCode(consignorCode);
				skey.setItemCode(convertedJanCode);	
				// 検索を行う
				itemData = (Item[]) itemHandler.find(skey);
				if (itemData != null && itemData.length > 0)
				{
					return itemData[0];
				}
				//-----------------
				// スキャンされたコード2をJANコードとして検索を行う
				//-----------------
				skey.KeyClear();
				skey.setConsignorCode(consignorCode);
				skey.setJAN(convertedJanCode);	
				// 検索を行う
				itemData = (Item[]) itemHandler.find(skey);
				if (itemData != null && itemData.length > 0)
				{
					return itemData[0];
				}
			}
			return null;
		}

	/**
	 * 荷主マスタ情報から、指定荷主コードに該当する荷主名称を取得します。<BR>
	 * <BR>
	 * 荷主マスタ情報を次の条件で検索します。該当データが無い場合は、nullを返します。
	 * 荷主コード    =パラメータより取得<BR>
	 * 削除区分=0:使用可<BR>
	 * <BR>
	 * @param	consignorCode	荷主コード<BR>
	 * @return	荷主名称<BR>
	 * @throws ReadWriteException
	 * @throws ScheduleException
	*/
	public String getConsignorNameFromMaster(
			String consignorCode)
			throws ReadWriteException, ScheduleException
		{
			// 戻り値を保持する配列
			Consignor[] consData	= null;
			
			ConsignorSearchKey skey = new ConsignorSearchKey();
			ConsignorHandler consHandler = new ConsignorHandler(wConn);
			skey.setConsignorCode(consignorCode);
			// 検索を行う
			consData = (Consignor[]) consHandler.find(skey);
			if (consData != null && consData.length > 0)
			{
				return consData[0].getConsignorName();
			}
			return null;
		}

	
	// Package methods -----------------------------------------------
	// Protected methods ---------------------------------------------
	/**
	 * 在庫情報から、指定条件に該当する商品情報を取得します。<BR>
	 * <BR>
	 * 在庫情報を次の条件で検索します。
	 * 荷主コード    =パラメータより取得<BR>
	 * 在庫ステータス=在庫<BR>
	 * 在庫情報.エリアNo=エリアマスタ情報.エリアNo<BR>
	 * エリアマスタ情報.エリア区分=2:ASRS以外<BR>
	 * 在庫情報を次の項目で検索します。
	 * スキャン商品コード1をJANコード→ケースITF→ボールITF→
	 * スキャン商品コード2をITF to JANコード変換の順に検索します。
	 * 入庫日を降順でソートし、1件目のデータを取得します。
	 * 該当データがある場合はそのデータを返します。該当データが無い場合は、nullを返します。
	 * 
	 * <DIR>
	 *  荷主コード：引数consignorCode<BR>
	 *  商品コード：引数itemCode<BR>
	 *  状態フラグ：センター在庫<BR>
	 * </DIR>
	 * <BR>
	 * @param	consignorCode	荷主コード<BR>
	 * @param	itemCode 		スキャン商品コード1<BR>
	 * @param	convertedJanCodeスキャン商品コード2<BR>
	 * @param	itemForm 		荷姿<BR>
	 * @return	在庫情報エンティティ<BR>
	 * @throws ReadWriteException
	 * @throws ScheduleException
	*/
	protected Stock getStockData(
			String consignorCode,
			String itemCode,
			String convertedJanCode,
			String itemForm
			)
			throws ReadWriteException, ScheduleException
		{
			// 戻り値を保持する配列
			Stock[] stockData	= null;
			
			StockSearchKey skey = new StockSearchKey();
			StockHandler stockHandler = new StockHandler(wConn);
			//-----------------
			// 1回目と5回目在庫情報の検索
			// スキャンされたコードをJANコードとして検索を行う
			//-----------------
			skey = getBaseCondition(consignorCode,itemForm);
			skey.setItemCode(itemCode);	
			// 検索を行う
			stockData = (Stock[]) stockHandler.find(skey);

			if (stockData != null && stockData.length > 0)
			{
				return stockData[0];
			}
			//-----------------
			// 2回目と6回目在庫情報の検索
			// スキャンされたコードをケースITFとして検索を行う
			//-----------------
			skey = getBaseCondition(consignorCode,itemForm);
			skey.setItf(itemCode);	
			// 検索を行う
			stockData = (Stock[]) stockHandler.find(skey);

			if (stockData != null && stockData.length > 0)
			{
				return stockData[0];
			}
			//-----------------
			// 3回目と7回目在庫情報の検索
			// スキャンされたコードをボールITFとして検索を行う
			//-----------------
			skey = getBaseCondition(consignorCode,itemForm);
			skey.setBundleItf(itemCode);	
			// 検索を行う
			stockData = (Stock[]) stockHandler.find(skey);

			if (stockData != null && stockData.length > 0)
			{
				return stockData[0];
			}
			if (! StringUtil.isBlank(convertedJanCode))
			{		
				//-----------------
				// 4回目と8回目在庫情報の検索
				// ITFtoJAN変換されたコードがセットされている場合、
				// それをJANコードとして検索を行う
				//-----------------
				skey = getBaseCondition(consignorCode,itemForm);
				skey.setItemCode(convertedJanCode);		
				// 検索を行う
				stockData = (Stock[]) stockHandler.find(skey);

				if (stockData != null && stockData.length > 0)
				{
					return stockData[0];
				}				
			}
			return null;
		}

	/**
	 * 商品情報問合せ要求に対する作業可能データを検索する際の基本となる検索条件を生成します。
	 * 検索の基本条件は以下のとおりとします。
	 * <DIR>
	 *  状態フラグ 	= 2(在庫) 
	 * </DIR>
	 * @param	consignorCode	荷主コード
	 * @param	itemForm		荷姿
	 * @return	作業情報検索キー	作業情報サーチキー<BR>
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 */
	protected StockSearchKey getBaseCondition(
			String consignorCode,String itemForm) throws ReadWriteException, ScheduleException
	{
		StockSearchKey skey = new StockSearchKey();
	
		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		skey.setConsignorCode(consignorCode);
		
		AreaOperator AreaOperator = new AreaOperator(wConn);
		
		skey.setInstockDateOrder(1, false);
		if(itemForm != null)
		{
			skey.setCasePieceFlag(itemForm);
		}

		return skey;
	}


	// Private methods -----------------------------------------------

}
//end of class
