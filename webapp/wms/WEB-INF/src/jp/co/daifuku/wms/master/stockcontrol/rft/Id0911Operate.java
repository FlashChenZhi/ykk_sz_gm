// $Id: Id0911Operate.java,v 1.1.1.1 2006/08/17 09:34:20 mori Exp $
package jp.co.daifuku.wms.master.stockcontrol.rft;
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Vector;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.ConsignorHandler;
import jp.co.daifuku.wms.base.dbhandler.ConsignorSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ItemHandler;
import jp.co.daifuku.wms.base.dbhandler.ItemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.Area;
import jp.co.daifuku.wms.base.entity.Consignor;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.rft.DataColumn;
import jp.co.daifuku.wms.base.rft.IdOperate;
import jp.co.daifuku.wms.base.rft.Idutils;
import jp.co.daifuku.wms.base.rft.SystemParameter;
import jp.co.daifuku.wms.master.operator.AreaOperator;
import jp.co.daifuku.wms.stockcontrol.rft.RFTId0911;

/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * 例外出庫可能在庫問合せ処理要求に対するデータ処理を行ないます。<BR>
 * Id0152Processから呼び出されるビジネスロジックが実装されます。<BR>
 * <BR>
 * 例外出庫可能在庫取得(<CODE>getStockData()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *  一覧選択フラグが0(初回時)の場合は、賞味期限を検索条件に入れずに検索します。
 *  該当データが無かった場合は、NotFoundExceptionを返します。
 * 
 *  一覧選択フラグが1(一覧から選択時)の場合は、賞味期限を検索条件に入れて、
 * 	StockAllocateOperatorクラスstockSearchメソッドで検索します。
 *  該当データが無かった場合は、NotFoundExceptionを返します。
 *  該当データがあった場合は、その在庫の情報を返します。
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>Y.Taki</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class Id0911Operate extends IdOperate
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0911Operate";
	// Class variables -----------------------------------------------
	// Constructors --------------------------------------------------
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $";
	}

	/**
	 * 在庫データを取得します。<BR>
	 * <BR>
	 * 一覧選択フラグが、0(初回時)の場合、賞味期限を検索条件に入れずに、在庫を検索します。<BR>
	 * 一覧選択フラグが、1(一覧から選択時)の場合、賞味期限を検索条件に入れて、在庫を検索します。<BR>
	 * <BR>
	 * 検索の結果、該当する在庫データが無い場合は、NotFoundExceptionを返します。<BR>
	 * 出庫可能数が0のデータを排除します。その結果、該当する在庫データが無くなった場合は、NotFoundExceptionを返します。<BR>
	 * 該当する在庫データがある場合は、在庫データを集約します。<BR>
	 * 集約結果を、戻り値として返します。<BR>
	 * <BR>
	 * @param	consignorCode	荷主コード<BR>
	 * @param	areaNo 			エリア<BR>
	 * @param	locationNo 		ロケーション<BR>
	 * @param	casePieceFlag	荷姿<BR>
	 * @param	scanCode1 		スキャンコード1<BR>
	 * @param	scanCode2 		スキャンコード2<BR>
	 * @param	listSelectionFlag	一覧選択フラグ<BR>
	 * @param	useByDate 		賞味期限<BR>
	 * @return	preStockData　在庫情報エンティティ<BR>
	 * @throws NotFoundException 作業可能なデータが見つからない時に通知されます。
	 * @throws InvalidDefineException 指定された値が異常（空白や、禁止文字が含まれている）時に通知されます。
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 * @throws IllegalAccessException 
	 * @throws ReadWriteException データベースとの接続で異常が発生した場合に通知されます。
	 * @throws ScheduleException 予期しない例外が発生した場合に通知されます。
	*/
	public Stock[] getDeliverableStockData(
		String consignorCode,
		String areaNo,
		String locationNo,
		String casePieceFlag,
		String scanCode1,
		String scanCode2,
		String listSelectionFlag,
		String useByDate
		)
		throws NotFoundException, OverflowException, IllegalAccessException, ReadWriteException, ScheduleException
	{
		// 在庫を検索した結果を保持する変数
		Stock[] preStockData = null;
		// 集約後の出庫可能在庫を保持する変数
		Stock[] retpossibleStockData = null;

		if (listSelectionFlag.equals(RFTId0911.LIST_SELECTION_FALSE))
		{
			// 一覧選択フラグが、0(初回時)の場合
			preStockData = getStockData(consignorCode, areaNo, locationNo, casePieceFlag, null, scanCode1, scanCode2);
		}
		else{
			// 一覧選択フラグが、1(一覧から選択時)の場合
			preStockData = getStockData(consignorCode, areaNo, locationNo, casePieceFlag, useByDate, scanCode1, scanCode2);
		}
		if (preStockData == null)
		{
			NotFoundException e = new NotFoundException();
			throw e;
		}
				
		// 出庫可能在庫を集約
		retpossibleStockData = collectStockData(preStockData);

		// 検索結果を返却する
		return retpossibleStockData;
	}	

	/**
	 * 在庫賞味期限一覧ファイル作成処理を行います。<BR>
	 * 在庫情報データを順番に指定されたファイル名のファイルに書きこみます。<BR>
	 * @param  data  一覧データ
	 * @param  filename  在庫賞味期限一覧ファイル名
	 * @throws IOException ファイル入出力エラー発生時に通知されます。
	 */
	public void createTableFile(Stock[] data, String filename) throws IOException
	{
		// ファイル書きこみストリーム生成
		PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(filename))) ;
		
		// 配列の要素数だけループをまわす
		for(int i = 0 ; i < data.length ; i++)
		{
			// Stockインスタンスから賞味期限取り出してファイルに書きこむ
			// データを固定長にする
			// 賞味期限
			String useByDate = Idutils.createDataLeft(data[i].getUseByDate(), DataColumn.LEN_USE_BY_DATE) ;
			
			// 文字列を連結
			StringBuffer buffer = new StringBuffer() ;
			buffer.append(useByDate) ;
			
			// ファイルに書きこんで改行
			pw.println(buffer.toString()) ;
		}
		
		// ファイル書きこみストリームを閉じる
		pw.close() ;
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
	 * 在庫データを集約します。<BR>
	 * パラメータとして受け取った在庫情報エンティティ配列について、賞味期限をキーに集約します。<BR>
	 * 受け取った在庫情報エンティティ配列は、賞味期限>ケースピース区分 順にソートされている必要があります。<BR>
	 * 集約する場合は、在庫数と引当て数を加算し、それ以外の項目は、配列の添え字の小さいデータとします。<BR>
	 * 集約の結果を、在庫情報エンティティ配列で返します。
	 * <BR>
	 * @param	moveableStockData	在庫情報エンティティ配列<BR>
	 * @return	在庫情報エンティティ配列<BR>
	 * @throws OverflowException 数値項目の桁数が超過した時に通知されます。
	 */
	protected Stock[] collectStockData(Stock[] possibleStockData) throws OverflowException
	{
		if (possibleStockData.length == 0)
		{
			return new Stock[0];
		}
		// 作業用のベクター 
		Vector workVec = new Vector();
		// 作業用の在庫エンティティ 
		Stock workStock = null;
		workStock = possibleStockData[0];
		for (int i = 1; i < possibleStockData.length; i++)
		{
			if (workStock.getUseByDate().equals(possibleStockData[i].getUseByDate()))
			{
				if ((workStock.getStockQty() + possibleStockData[i].getStockQty() <= SystemParameter.MAXSTOCKQTY)
					&& (workStock.getAllocationQty() + possibleStockData[i].getAllocationQty() <= SystemParameter.MAXSTOCKQTY))
				{
					workStock.setStockQty(workStock.getStockQty() + possibleStockData[i].getStockQty());
					workStock.setAllocationQty(workStock.getAllocationQty() + possibleStockData[i].getAllocationQty());
				}
				else
				{
					// 6026028=集約処理でオーバーフローが発生しました。テーブル名:{0}
					RftLogMessage.print(6026028, LogMessage.F_ERROR, CLASS_NAME, "DMSTOCK");
					throw new OverflowException();
				}
			}
			else
			{
				workVec.addElement(workStock);
				workStock = possibleStockData[i];
			}
		}
		workVec.addElement(workStock);
		return (Stock[])workVec.toArray(new Stock[workVec.size()]);		
	}
	
	/**
	 * 在庫情報問合せに対する在庫データを検索します。
	 * 検索の基本条件は以下のとおりとします。
	 * <DIR>
	 *   荷主コード = パラメータより取得<BR>
	 *   ロケーションNo = パラメータより取得<BR>
	 *   在庫ステータス= 2：センター在庫<BR>
	 *   エリアNo = パラメータより取得<BR>
	 *   在庫情報.エリアNo = エリアマスタ情報.エリアNo<BR>
	 *   エリアマスタ情報.エリア区分 = 2：ASRS以外<BR>
	 * </DIR>
	 * 
	 * この条件に加え、スキャンコードをJANコード、ケースITF、ボールITFの順に当てはめて検索します。<BR>
	 * 順に検索し、在庫データが見つかった段階で検索を中断し、
	 * 取得したデータを返します。<BR>
	 * この3回の検索のいずれでも在庫データが見つからなかった場合かつ、ITFtoJAN変換されたコードが
	 * 空でない場合、<CODE>convertedJanCode</CODE>をJANコードとして扱い、検索します。
	 * <BR>
	 * 計4回の検索で作業可能データが見つからなかった場合はnullを返します。
	 * <BR>
	 * @param	consignorCode		荷主コード
	 * @param	areaNo				エリア
	 * @param	locationNo			ロケーション
	 * @return	在庫情報  			在庫情報エンティティの配列<BR>
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 * @throws LockTimeOutException 	一定時間データベースのロックが解除されない時に通知されます。
	 * @throws IllegalAccessException 
	 */
	protected Stock[] getStockData(
			String consignorCode,
			String areaNo,
			String locationNo,
			String casePieceFlag,
			String useByDate,
			String scanCode1,
			String scanCode2) throws ReadWriteException, ScheduleException
	{
		
			// Storckを検索した結果を保持する変数
			Stock[] stock = null;

			StockSearchKey skey = new StockSearchKey();
			StockHandler obj = new StockHandler(wConn);
			//-----------------
			// 在庫情報の検索(１回目)
			// スキャンされたコードをJANコードとして検索を行う
			//-----------------
			skey = getStockSearchKey(consignorCode,areaNo, locationNo, casePieceFlag, useByDate);
			skey.setItemCode(scanCode1);
			
			stock = (Stock[]) obj.find(skey);
			// Stockを検索した結果を保持する変数


			if (stock != null && stock.length > 0)
			{
				return stock;
			}
			
			//-----------------
			// 在庫情報の検索(２回目)
			// スキャンされたコードをケースITFとして検索を行う
			//-----------------
			skey = getStockSearchKey(consignorCode, areaNo, locationNo, casePieceFlag, useByDate);
			skey.setItf(scanCode1);
			
			stock = (Stock[]) obj.find(skey);

			if (stock != null && stock.length > 0)
			{
				return stock;
			}

			//-----------------
			// 在庫情報の検索(３回目)
			// スキャンされたコードをボールITFとして検索を行う
			//-----------------
			skey = getStockSearchKey(consignorCode, areaNo, locationNo, casePieceFlag, useByDate);
			skey.setBundleItf(scanCode1);
			
			stock = (Stock[]) obj.find(skey);

			if (stock != null && stock.length > 0)
			{
				return stock;
			}

			if (! StringUtil.isBlank(scanCode2))
			{
				//-----------------
				// 在庫情報の検索(４回目)
				// ITFtoJAN変換されたコードがセットされている場合、
				// それをJANコードとして検索を行う
				//-----------------
				skey = getStockSearchKey(consignorCode, areaNo, locationNo, casePieceFlag, useByDate);
				skey.setItemCode(scanCode2);

				stock = (Stock[]) obj.find(skey);

				if (stock != null && stock.length > 0)
				{
					return stock;
				}
			}
				
		return null;
	}
	
	/**
	 * 在庫情報のデータを取得するための共通条件を格納した
	 * SearchKeyを返す。<BR>
	 * 集約等の条件は呼び出し元でセットすること。<BR>
	 * <BR>
	 * (検索条件)
	 * <UL>
	 *  <LI>荷主コード</LI>
	 *  <LI>在庫ステータス = センター在庫
	 *  <LI>エリアNo</LI>
	 *  <LI>ロケーションNo</LI>
	 *  <LI>ｹｰｽﾋﾟｰｽ区分 = 全ての場合は検索条件からはずす</LI>
	 *  <LI>賞味期限 = 空の場合は検索条件からはずす</LI>
	 *  <LI>移動可能数　= 0でないもの</LI>
	 * </UL>
	 * 
	 * @param consignorCode	荷主コード
	 * @param areaNo		エリアNo
	 * @param locationNo		ロケーションNo
	 * @param casePieceFlag	ｹｰｽﾋﾟｰｽ区分
	 * @param useByDate		賞味期限
	 * @return		作業データ検索用SearchKey
	 * @throws ReadWriteException
	 * @throws ScheduleException
	 */
	public StockSearchKey getStockSearchKey(
	        String consignorCode,
	        String areaNo,
	        String locationNo,
	        String casePieceFlag,
	        String useByDate
	        ) throws ReadWriteException, ScheduleException
	{
	    StockSearchKey skey = new StockSearchKey();

		skey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);	// センター在庫
	    skey.setConsignorCode(consignorCode);					// 荷主コード
	    if (! StringUtil.isBlank(areaNo))
	    {
		    skey.setAreaNo (areaNo);							// エリアNo
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
			skey.setAreaNo(areaNoASRS);
	    }
	    skey.setLocationNo (locationNo);						// ロケーションNo
	    if (! casePieceFlag.equals(RFTId0911.CASE_PIECE_FLAG_ALL))
	    {
		    skey.setCasePieceFlag(casePieceFlag);				// ｹｰｽﾋﾟｰｽ区分
	    }
	    if (useByDate != null)
	    {
		    skey.setUseByDate(useByDate);						// 賞味期限
	    }
	    skey.setAllocationQty(0, "!=");							// 出庫可能数0以外
  
		skey.setUseByDateOrder(1, true);
		skey.setItemCodeOrder(2, true);
		skey.setItfOrder(3, true);
		skey.setBundleItfOrder(4, true);

	    return skey;
	}


	// Private methods -----------------------------------------------

}
//end of class
