// $Id: Id0830Operate.java,v 1.1.1.1 2006/08/17 09:34:21 mori Exp $
package jp.co.daifuku.wms.master.storage.rft;
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ShelfInvalidityException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckHandler;
import jp.co.daifuku.wms.base.dbhandler.InventoryCheckSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.LocateOperator;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.master.operator.AreaOperator;

/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * 棚卸し商品データ要求に対するデータ処理を行ないます。<BR>
 * Id0093Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * 在庫情報検索処理(<CODE>getStockDataOfInventoryCheck()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *  現在の在庫情報データを取得します。複数件該当した場合、集約を行ないます。<BR>
 *  該当データが無かった場合は、nullを返します。<BR>
 *  該当データがあった場合は、集約を行ないます。（この集約で複数の賞味期限がある時は、賞味期限には空白をセットします。）<BR>
 * </DIR>
 * <BR>
 * 棚卸作業情報検索処理(<CODE>getInventoryCheckData()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   引数項目を条件に棚卸作業情報を検索します。<BR>
 *   該当データが無かった場合は、nullを返します。<BR>
 *   該当データが複数件あった場合は、棚卸情報データを集約します。（この集約で複数の賞味期限がある時は、賞味期限には空白をセットします。）<BR>
 *   １件の場合はそのデータを返します。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:21 $
 * @author  $Author: mori $
 */
public class Id0830Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0830Operate";
	// Class variables -----------------------------------------------
	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 * @param なし
	 */
	public Id0830Operate()
	{
		super();
	}

	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param <code>conn</code> DBConnection情報
	 */
	public Id0830Operate(Connection conn)
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
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:21 $");
	}

	/**
	 * 現在の在庫情報データを取得します。複数件該当した場合、集約を行ないます。<BR>
	 * <BR>
	 * 荷主コード、棚、商品コード、エリア区分:AS/RS以外を検索キーとして、検索します。<BR>
	 * 検索の結果、該当する在庫データが無い場合は、nullを返します。<BR>
	 * 該当する在庫データがある場合は、在庫データを集約します。<BR>
	 * 集約時に、複数の賞味期限がある時は、賞味期限には空白をセットします。<BR>
	 * 集約結果を、戻り値として返します。<BR>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	locationNo 		ロケーション
	 * @param	scanCode1 		スキャン商品コード1
	 * @param	scanCode2		スキャン商品コード2
	 * @return	在庫情報エンティティ
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 * @throws ShelfInvalidityException
	*/
	public Stock getStockDataOfInventoryCheck(
		String consignorCode,
		String areaNo,
		String locationNo,
		String scanCode1,
		String scanCode2)
		throws OverflowException, ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		// 在庫を検索した結果を保持する変数
		Stock[] stockData = null;
		// 集約後の在庫を保持する変数
		Stock retStockData = null;

		stockData = getStockDataList(consignorCode, areaNo, locationNo, scanCode1, scanCode2);
		
		if (stockData.length == 0)
		{
			return null;
		}
		
		// 在庫を集約
		retStockData = collectStockData(stockData);

		if (retStockData.getStockQty() > 0)
		{
			return retStockData;
		}
		else
		{
			return null;
		}
	}

	/**
	 * 棚卸情報から、指定条件に該当する、処理フラグが未確定(0)のデータを取得します。<BR>
	 * <BR>
	 * 荷主コード、棚、商品コード、エリア区分:AS/RS以外を検索キーとして、検索します。<BR>
	 * 検索の結果、該当する棚卸情報データが無い場合は、nullを返します。<BR>
	 * 該当する棚卸情報データがある場合は、棚卸情報データを集約します。<BR>
	 * 集約時に、複数の賞味期限がある時は、賞味期限には空白をセットします。<BR>
	 * 集約結果を、戻り値として返します。<BR>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	locationNo		棚卸ロケーション
	 * @param	itemCode 		商品コード
	 * @return	棚卸情報エンティティ配列
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 * @throws ShelfInvalidityException
	*/
	public InventoryCheck getInventoryCheckData(
		String consignorCode,
		String areaNo,
		String locationNo,
		String itemCode)
		throws OverflowException, ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		// 棚卸情報を検索した結果を保持する変数
		InventoryCheck[] inventoryCheckData = null;
		// 集約後の在庫を保持する変数
		InventoryCheck retInventoryCheckData = null;
				
		inventoryCheckData = getInventoryCheckDataList(consignorCode, areaNo, locationNo, itemCode);
		if (inventoryCheckData.length == 0)
		{
			return null;
		}
				
		// 棚卸情報を集約
		retInventoryCheckData = collectInventoryCheckData(inventoryCheckData);
	
		// 検索結果を返却する
		return retInventoryCheckData;
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
	
	// Package methods -----------------------------------------------
	// Protected methods ---------------------------------------------
	/**
	 * 在庫情報から次の条件で在庫データを検索します。<BR>
	 * <DIR>
	 *  在庫ステータス = 2:センター在庫<BR>
	 *  荷主コード、ロケーション、商品コード = パラメータより取得<BR>
	 *  ソート順･･･ケースピース区分(降順)>賞味期限<BR>
	 * </DIR>
	 * 取得データは、在庫情報エンティティ配列で返します。
	 * データが取得できない場合は、空の配列を返します。<BR>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	locationNo 		ロケーション
	 * @param	scanCode1 		スキャンコード1
	 * @param	scanCode2		スキャンコード2
	 * @return	在庫情報エンティティ
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 * @throws ShelfInvalidityException
	*/
	protected Stock[] getStockDataList(
		String consignorCode,
		String areaNo,
		String locationNo,
		String scanCode1,
		String scanCode2)
		throws ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		// 戻り値を保持する配列
		Stock[] stockData	= null;
		
		StockSearchKey stockKey = new StockSearchKey();
		StockHandler stockHandler = new StockHandler(wConn);
		//-----------------
		// 在庫情報の検索(1回目）
		//-----------------
		stockKey = getStockSearchKey(consignorCode,areaNo,locationNo);
		stockKey.setItemCode(scanCode1);
		
		stockKey.setCasePieceFlagOrder(1, false);
		stockKey.setUseByDateOrder(2, true);
		
		// 検索を行う
		stockData = (Stock[]) stockHandler.find(stockKey);

		// 該当データがある場合検索結果を返す
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		//-----------------
		// 在庫情報の検索(2回目）
		//-----------------
		stockKey = getStockSearchKey(consignorCode, areaNo, locationNo);
		stockKey.setItf(scanCode1);
		
		stockKey.setCasePieceFlagOrder(1, false);
		stockKey.setUseByDateOrder(2, true);
		
		// 検索を行う
		stockData = (Stock[]) stockHandler.find(stockKey);

		// 該当データがある場合検索結果を返す
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		//-----------------
		// 在庫情報の検索(3回目）
		//-----------------
		stockKey = getStockSearchKey(consignorCode, areaNo, locationNo);
		stockKey.setBundleItf(scanCode1);
		
		stockKey.setCasePieceFlagOrder(1, false);
		stockKey.setUseByDateOrder(2, true);
		
		// 検索を行う
		stockData = (Stock[]) stockHandler.find(stockKey);

		// 該当データがある場合検索結果を返す
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		if(!StringUtil.isBlank(scanCode2))
		{
			//-----------------
			// 在庫情報の検索(4回目）
			//-----------------
			stockKey = getStockSearchKey(consignorCode, areaNo, locationNo);
			stockKey.setItemCode(scanCode2);
			
			stockKey.setCasePieceFlagOrder(1, false);
			stockKey.setUseByDateOrder(2, true);
			
			// 検索を行う
			stockData = (Stock[]) stockHandler.find(stockKey);

			// 該当データがある場合検索結果を返す
			if (stockData != null && stockData.length > 0)
			{
				return stockData;
			}
		}

		// 検索結果がない場合、要素数0のStockクラス配列を返す
		return new Stock[0];
	}

	/**
	 * 在庫情報から次の条件で在庫データを検索します。<BR>
	 * <DIR>
	 *  在庫ステータス = 2:センター在庫<BR>
	 *  荷主コード、商品コード = パラメータより取得<BR>
	 *  ソート順･･･入庫日時(降順)<BR>
	 *  在庫情報.エリアNo. = エリアマスタ情報.エリアNo.<BR>
	 *  エリアマスタ情報.エリア区分 = 2:ASRS以外<BR>
	 * </DIR>
	 * 取得データは、在庫情報エンティティ配列で返します。<BR>
	 * データが取得できない場合は、空の配列を返します。<BR>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	itemCode 		商品コード
	 * @return	在庫情報エンティティ
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 * @throws ShelfInvalidityException
	*/
	protected Stock[] getStockDataList(
		String consignorCode,
		String scanCode1,
		String scanCode2)
		throws ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		// 戻り値を保持する配列
		Stock[] stockData	= null;
		
		StockSearchKey stockKey = new StockSearchKey();
		StockHandler stockHandler = new StockHandler(wConn);
		//-----------------
		// 在庫情報の検索(1回目）
		//-----------------
		stockKey = getStockSearchKey(consignorCode, null, null);
		stockKey.setItemCode(scanCode1);
		
		stockKey.setInstockDateOrder(1, false);
		
		// 検索を行う
		stockData = (Stock[]) stockHandler.find(stockKey);

		// 該当データがある場合検索結果を返す
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		// -----------------
		// 在庫情報の検索(2回目）
		//-----------------
		stockKey = getStockSearchKey(consignorCode, null, null);
		stockKey.setItf(scanCode1);
		
		stockKey.setInstockDateOrder(1, false);
		
		// 検索を行う
		stockData = (Stock[]) stockHandler.find(stockKey);

		// 該当データがある場合検索結果を返す
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		// -----------------
		// 在庫情報の検索(3回目）
		//-----------------
		stockKey = getStockSearchKey(consignorCode, null, null);
		stockKey.setBundleItf(scanCode1);
		
		stockKey.setInstockDateOrder(1, false);
		
		// 検索を行う
		stockData = (Stock[]) stockHandler.find(stockKey);

		// 該当データがある場合検索結果を返す
		if (stockData != null && stockData.length > 0)
		{
			return stockData;
		}
		
		if(!StringUtil.isBlank(scanCode2))
		{
			// -----------------
			// 在庫情報の検索(4回目）
			//-----------------
			stockKey = getStockSearchKey(consignorCode, null, null);
			stockKey.setItemCode(scanCode2);
			
			stockKey.setInstockDateOrder(1, false);
			
			// 検索を行う
			stockData = (Stock[]) stockHandler.find(stockKey);
			// 該当データがある場合検索結果を返す			
			if (stockData != null && stockData.length > 0)
			{
				return stockData;
			}
		}

		// 検索結果を返却する
		return new Stock[0];
	}

	/**
	 * 棚卸し情報から次の条件で在庫データを検索します。<BR>
	 * <DIR>
	 *  処理フラグ：0(未確定)<BR>
	 *  荷主コード、棚卸ロケーション、商品コード = パラメータより取得<BR>
	 *  ソート順･･･ケースピース区分(降順)>賞味期限<BR>
	 * </DIR>
	 * 取得データは、棚卸し情報エンティティ配列で返します。<BR>
	 * データが取得できない場合は、空の配列を返します。<BR>
	 * <BR>
	 * @param	consignorCode	荷主コード
	 * @param	locationNo 		ロケーション
	 * @param	itemCode 		商品コード
	 * @return	棚卸し情報エンティティ
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 * @throws ShelfInvalidityException
	*/
	protected InventoryCheck[] getInventoryCheckDataList(
		String consignorCode,
		String areaNo,
		String locationNo,
		String itemCode)
		throws ReadWriteException, ScheduleException, ShelfInvalidityException
	{
	    LocateOperator lOperator = new LocateOperator(wConn);
		if(lOperator.isAsrsLocation(locationNo))
		{
			// 6023443=No.{0} 指定された棚は自動倉庫の棚のため入力できません。
			throw new ShelfInvalidityException();
		}	
	    
	    
	    // 戻り値を保持する配列
		InventoryCheck[] inventoryData	= null;
		
		InventoryCheckSearchKey inventoryKey = new InventoryCheckSearchKey();
		InventoryCheckHandler inventoryHandler = new InventoryCheckHandler(wConn);
		//-----------------
		// 棚卸し情報の検索
		//-----------------
		// 処理フラグが、0(未確定)を対象にする。
		inventoryKey.setStatusFlag(InventoryCheck.STATUS_FLAG_NOTDECISION);
		inventoryKey.setConsignorCode(consignorCode);
		if (! StringUtil.isBlank(areaNo))
		{
			inventoryKey.setAreaNo(areaNo);
		}
		inventoryKey.setLocationNo(locationNo);
		inventoryKey.setItemCode(itemCode);

		inventoryKey.setCasePieceFlagOrder(1, false);
		inventoryKey.setUseByDateOrder(2, true);
		
		// 検索を行う
		inventoryData = (InventoryCheck[]) inventoryHandler.find(inventoryKey);

		// 検索結果がない場合、要素数0のStockクラス配列を返す
		if (inventoryData == null || inventoryData.length == 0)
		{
			return new InventoryCheck[0];
		}

		// 検索結果を返却する
		return inventoryData;
	}

	/**
	 * 在庫データを集約します。<BR>
	 * パラメータとして受け取った在庫情報エンティティ配列について、１レコードに集約します。<BR>
	 * 集約する場合は、在庫数と引当て数を加算し、それ以外の項目は、配列の最初のデータとします。<BR>
	 * 但し、賞味期限については、異なる賞味期限があったときには、賞味期限の値を空白にします。<BR>
	 * 集約の結果を、在庫情報エンティティで返します。<BR>
	 * <BR>
	 * @param	stockData	在庫情報エンティティ配列
	 * @return	在庫情報エンティティ
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 */
	protected Stock collectStockData(Stock[] stockData) throws OverflowException
	{
		// 作業用の在庫情報エンティティ 
		Stock workStock = null;
		
		for (int i = 0; i < stockData.length; i++)
		{
			if (i==0)
			{
				workStock = stockData[i];
			}
			else
			{
				// 商品コードが異なる場合は除く
				if(! workStock.getItemCode().equals(stockData[i].getItemCode()))
				{
					continue;
				}
				if ((workStock.getStockQty() + stockData[i].getStockQty() <= SystemParameter.MAXSTOCKQTY)
					&& (workStock.getAllocationQty() + stockData[i].getAllocationQty() <= SystemParameter.MAXSTOCKQTY)){
					workStock.setStockQty(workStock.getStockQty() + stockData[i].getStockQty());
					workStock.setAllocationQty(workStock.getAllocationQty() + stockData[i].getAllocationQty());
				}
				else
				{
					// 6026028=集約処理でオーバーフローが発生しました。テーブル名:{0}
					RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DMSTOCK");
					throw new OverflowException();
				}
				if (! workStock.getUseByDate().equals(stockData[i].getUseByDate()))
				{
					workStock.setUseByDate("");
				}
			}
		}
		return workStock;
	}

	/**
	 * 棚卸しデータを集約します。<BR>
	 * パラメータとして受け取った棚卸し情報エンティティ配列について、１レコードに集約します。<BR>
	 * 集約する場合は、棚卸し結果数を加算し、それ以外の項目は、配列の最初のデータとします。<BR>
	 * 但し、賞味期限については、異なる賞味期限があったときには、賞味期限の値を空白にします。<BR>
	 * 集約の結果を、棚卸し情報エンティティで返します。<BR>
	 * <BR>
	 * @param	InventoryStockData	棚卸し情報エンティティ配列
	 * @return	棚卸し情報エンティティ
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 */
	protected InventoryCheck collectInventoryCheckData(InventoryCheck[] inventoryCheckData) throws OverflowException
	{
		// 作業用の棚卸し情報エンティティ 
		InventoryCheck workInventory = null;
		
		for (int i = 0; i < inventoryCheckData.length; i++)
		{
			if (i==0)
			{
				workInventory = inventoryCheckData[i];
			}
			else
			{
				if (workInventory.getResultStockQty() + inventoryCheckData[i].getResultStockQty() <= SystemParameter.MAXSTOCKQTY)
				{
					workInventory.setResultStockQty(workInventory.getResultStockQty() + inventoryCheckData[i].getResultStockQty());
				}
				else
				{
					// 6026028=集約処理でオーバーフローが発生しました。テーブル名:{0}
					RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DNINVENTORYCHECK");
					throw new OverflowException();
				}
				if (! workInventory.getUseByDate().equals(inventoryCheckData[i].getUseByDate()))
				{
					workInventory.setUseByDate("");
				}
			}
		}
		return workInventory;
	}
	
	/**
	 * 簡易棚卸商品問合せの在庫検索用の基本キーを取得します。
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 * @throws ShelfInvalidityException
	 */
	private StockSearchKey getStockSearchKey(String consignorCode, String areaNo, String locationNo) throws ReadWriteException, ScheduleException, ShelfInvalidityException
	{
		StockSearchKey stockKey = new StockSearchKey();
		//-----------------
		// 在庫情報の検索
		//-----------------
		// 在庫ステータスが センター在庫 を対象にする。
		stockKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		stockKey.setConsignorCode(consignorCode);

		if (! StringUtil.isBlank(locationNo))
		{
			LocateOperator lOperator = new LocateOperator(wConn);
			if(lOperator.isAsrsLocation(locationNo))
			{
				// 6023443=No.{0} 指定された棚は自動倉庫の棚のため入力できません。
				throw new ShelfInvalidityException();
			}
			stockKey.setLocationNo(locationNo);
			
			if (! StringUtil.isBlank(areaNo))
			{
				stockKey.setAreaNo(areaNo);
			}
			else 
			{
				AreaOperator AreaOperator = new AreaOperator(wConn);
				
				String[] areaNoASRS = null;
				int[] areaType = new int[2];
				areaType[0] = Area.SYSTEM_DISC_KEY_WMS;
				areaType[1] = Area.SYSTEM_DISC_KEY_IDM;
				// ASRS以外のエリアを取得し、検索条件に付加する。
				// 該当エリアがなかった場合はIS NULL検索
				areaNoASRS = AreaOperator.getAreaNo(areaType);
				stockKey.setAreaNo(areaNoASRS);
			}
		}

		return stockKey;
	}

	// Private methods -----------------------------------------------

}
//end of class
