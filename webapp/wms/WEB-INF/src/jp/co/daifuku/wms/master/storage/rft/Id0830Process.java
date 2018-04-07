// $Id: Id0830Process.java,v 1.1.1.1 2006/08/17 09:34:21 mori Exp $
package jp.co.daifuku.wms.master.storage.rft;

import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.ShelfInvalidityException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.storage.rft.RFTId0830;
import jp.co.daifuku.wms.storage.rft.RFTId5830;
import jp.co.daifuku.wms.base.entity.Item;

/**
 * Designer : Y.Taki<BR>
 * Maker : E.Takeda<BR>
 * <BR>
 * RFTからの簡易棚卸商品問合せ要求に対する処理を行います。<BR>
 * Id0830Operateクラスの提供する機能を使用し、在庫情報から在庫データを取得します。<BR>
 * また、棚卸情報を検索し、既に棚卸し済みかどうかを確認します。<BR>
 * 取得したデータをもとに、RFTに送信する応答電文を生成します。<BR>
 * <BR>
 * 簡易棚卸し商品問合せ要求処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *  受信データから、必要な情報を取得します。<BR>
 *  システム情報から在庫管理パッケージの有無をチェックします。<BR>
 *  在庫管理パッケージありの場合、Id0830Operateクラスの機能で在庫情報を検索し、在庫を取得します。<BR>
 *  また、棚卸情報を検索し、棚卸しデータを取得します。<BR>
 *  在庫の有無、棚卸しデータの有無から応答のフラグをセットします。<BR>
 *  送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/19</TD><TD>E.Takeda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:21 $
 * @author  $Author: mori $
 */
public class Id0830Process extends IdProcess
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0830Process";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:21 $");
	}

	// Constructors --------------------------------------------------
	// Public methods ------------------------------------------------
	/**
	 * 簡易棚卸し商品問合せ要求処理(ID0830)を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5830)をバイト列で作成します。<BR>
	 * <BR>
	 * 日次処理中で無いことを確認します。
	 * 日次処理中の場合は、5:日次処理中のエラーを返します。<BR>
	 * 作業者別実績の対象レコードがある事を確認します。無い場合は、新規作成します。<BR>
	 * <BR>
	 * WareNaviSystemクラスを利用し、在庫管理パッケージの有無を調べます。<BR>
	 * 在庫管理パッケージありの場合は、ID0830Operateクラスを利用し、要求のあった条件で在庫情報を検索します。<BR>
	 * 該当する在庫がある時は、その在庫データを送信電文にセットし、在庫フラグに「1:在庫データあり」をセットします。（在庫データは集約されたデータを受け取ります）<BR>
	 * 在庫が無い時は、在庫フラグに「2:在庫データなし」をセットします。<BR>
	 * 在庫管理パッケージ無しの場合は、在庫フラグに「0:在庫管理無し」をセットします。<BR>
	 * <BR>
	 * ID0830Operateクラスを利用し、要求のあった条件で棚卸し情報を検索します。<BR>
	 * 棚卸しデータが無い時は、応答フラグに「0:新規棚卸し」をセットします。<BR>
	 * 棚卸しデータがある時は、応答フラグに「2:棚卸し済み」をセットします。<BR>
	 * <BR>
	 * 送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
	 * <BR>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		RFTId0830 rftId0830 = null;
		RFTId5830 rftId5830 = null;
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0830 = (RFTId0830) PackageManager.getObject("RFTId0830");
			rftId0830.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5830 = (RFTId5830) PackageManager.getObject("RFTId5830");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0830.getRftNo();
		// 受信電文から作業者コードを取得
		String workerCode = rftId0830.getWorkerCode();
		// 受信電文から荷主コードを取得
		String consignorCode = rftId0830.getConsignorCode();
		// 受信電文からエリアNoを取得
		String areaNo = rftId0830.getAreaNo();
		// 受信電文から棚卸ロケーションを取得
		String inventoryLocation = rftId0830.getInventryLocation();
		// 受信電文からスキャン商品コード1を取得
		String scanCode1 = rftId0830.getScanCode1();
		// 受信電文からスキャン商品コード2を取得
		String scanCode2 = rftId0830.getScanCode2();
		// 荷主名を保持する変数
		String consignorName = "";
		// ＪＡＮコードを保持する変数
		String JANCode = "";
		// 商品識別コードを保持する変数
		String itemId = "";
		// 商品コードを保持する変数
		String itemCode = "";
		// ボールITFを保持する変数
		String bundleITF = "";
		// ケースITFを保持する変数
		String ITF = "";
		// 商品名称を保持する変数
		String itemName = "";
		// ボール入数を保持する変数
		int bundleEnteringQty = 0;
		// ケース入数を保持する変数
		int enteringQty = 0;
		// 在庫数を保持する変数
		int stockQty = 0;
		// 棚卸数を保持する変数
		int inventoryCheckQty = 0;
		// ケース・ピース区分を保持する変数
		String casePieceFlag = SystemDefine.CASEPIECE_FLAG_NOTHING ;
		// 賞味期限を保持する変数
		String useByDate = "";
		// 在庫フラグを保持する変数
		String stockFlag = "";
		// 応答フラグを保持する変数
		String ansCode = "";
		// エラー詳細を保持する変数
		String errDetails = RFTId5830.ErrorDetails.NORMAL;
		try
		{
			if(DisplayText.isPatternMatching(inventoryLocation))
			{
				throw new NotFoundException(RFTId5830.ANS_CODE_ERROR);
			}
			if(DisplayText.isPatternMatching(scanCode1))
			{
				throw new NotFoundException(RFTId5830.ANS_CODE_ERROR);
			}
			if(DisplayText.isPatternMatching(scanCode2))
			{
				throw new NotFoundException(RFTId5830.ANS_CODE_ERROR);
			}

			// BaseOperateのインスタンスを生成
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//-----------------
			// 日次処理中チェック
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				// 応答フラグを 5:日次更新処理中 でリターン
				ansCode = RFTId5830.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//-----------------
				// 作業者別実績の存在チェック
				//-----------------
				WorkerResult[] workerResult =
					baseOperate.getWorkerResult(
						WorkingInformation.JOB_TYPE_INVENTORY,
						workerCode,
						rftNo);
				if (workerResult.length == 0)
				{
					// 作業者実績の新規作成
					baseOperate.createWorkerResult(
						WorkingInformation.JOB_TYPE_INVENTORY,
						workerCode,
						rftNo);
					// commit
					wConn.commit();
				}

				// Id0830Operateのインスタンスを生成
				Id0830Operate id0830Operate = (Id0830Operate)PackageManager.getObject("Id0830Operate");
				id0830Operate.setConnection(wConn);

				//-----------------
				// 商品マスタ情報を検索
				//-----------------
				Item itemInfo = id0830Operate.getItemFromMaster(
						consignorCode,
						scanCode1,
						scanCode2,
						rftNo,
						workerCode);
				
				//-----------------
				// 在庫情報を検索
				//-----------------
				Stock stock = null;

				// WareNaviSystemHandlerのインスタンスを生成
				WareNaviSystemHandler wareNaviSystemHandler = new WareNaviSystemHandler(wConn);

				//	在庫管理パッケージの検索
				WareNaviSystem[] wareNaviSystem =
					(WareNaviSystem[]) wareNaviSystemHandler.find(new WareNaviSystemSearchKey());

				//在庫管理なしの場合
				if (wareNaviSystem[0].getStockPack().equals("0"))
				{
					if (itemInfo != null)
					{
						// マスタが有る場合は、マスタデータから取得できるデータを取得し、保持変数にセット
						consignorName = "";
						JANCode = itemInfo.getItemCode();
						bundleITF = itemInfo.getBundleItf();
						ITF = itemInfo.getITF();
						itemName = itemInfo.getItemName1();
						bundleEnteringQty = itemInfo.getBundleEnteringQty();
						enteringQty = itemInfo.getEnteringQty();

						stockFlag = RFTId5830.STOCK_FLAG_STOCK_CONTROL_OFF;
						stock = null;
					}
					else
					{
						throw new NotFoundException(RFTId5830.ANS_CODE_ERROR);
					}
				}
				//在庫管理ありの場合
				else
				{
					//在庫情報を検索、取得
					stock =	id0830Operate.getStockDataOfInventoryCheck(
							consignorCode,
							areaNo,
							inventoryLocation,
							scanCode1,
							scanCode2);
					// 在庫データが見つからない場合
					if (stock == null)
					{
						stockFlag = RFTId5830.STOCK_FLAG_STOCK_NULL;
						// もし、別の棚に同じ商品があるなら、その商品の情報をセットする。（荷主名、商品名、入り数、等）
						Stock[] stockList = id0830Operate.getStockDataList(consignorCode, scanCode1, scanCode2);
						if (stockList.length > 0)
						{
							consignorName = stockList[0].getConsignorName();
							JANCode = stockList[0].getItemCode();
							bundleITF = stockList[0].getBundleItf();
							ITF = stockList[0].getItf();
							itemName = stockList[0].getItemName1();
							bundleEnteringQty = stockList[0].getBundleEnteringQty();
							enteringQty = stockList[0].getEnteringQty();
						}
						else if(itemInfo != null)
						{
							// マスタが有る場合は、マスタデータから取得できるデータを取得し、保持変数にセット
							consignorName = "";
							JANCode = itemInfo.getItemCode();
							bundleITF = itemInfo.getBundleItf();
							ITF = itemInfo.getITF();
							itemName = itemInfo.getItemName1();
							bundleEnteringQty = itemInfo.getBundleEnteringQty();
							enteringQty = itemInfo.getEnteringQty();
						}
						else
						{
							throw new NotFoundException(RFTId5830.ANS_CODE_ERROR);
						}
					}
					// 在庫データがある場合
					else
					{
						stockFlag = RFTId5830.STOCK_FLAG_STOCK_ON;
						// 在庫データから取得できるデータを取得し、保持変数にセット
						consignorName = stock.getConsignorName();
						JANCode = stock.getItemCode();
						bundleITF = stock.getBundleItf();
						ITF = stock.getItf();
						itemName = stock.getItemName1();
						bundleEnteringQty = stock.getBundleEnteringQty();
						enteringQty = stock.getEnteringQty();
						stockQty = stock.getStockQty();
						useByDate = stock.getUseByDate();
						itemId = stock.getStockId();
					}
				}

				//-----------------
				// 棚卸し情報を検索
				//-----------------
				InventoryCheck inventoryCheck =
					id0830Operate.getInventoryCheckData(
						consignorCode,
						areaNo,
						inventoryLocation,
						JANCode);

				
				// 棚卸しデータが見つからない場合
				if (inventoryCheck == null)
				{
					ansCode = RFTId5830.ANS_CODE_NORMAL;
				}
				// 棚卸しデータが1件の場合(複数件の場合はチェック済み)
				else
				{
					// 棚卸しデータから取得できるデータを取得し、保持変数にセット
					consignorName = inventoryCheck.getConsignorName();
					bundleITF = inventoryCheck.getBundleItf();
					ITF = inventoryCheck.getItf();
					itemName = inventoryCheck.getItemName1();
					bundleEnteringQty = inventoryCheck.getBundleEnteringQty();
					enteringQty = inventoryCheck.getEnteringQty();
					inventoryCheckQty = inventoryCheck.getResultStockQty();
					useByDate = inventoryCheck.getUseByDate();
					
					ansCode = RFTId5830.ANS_CODE_COMPLETION;
				}
			}
		}
		catch (NotFoundException e)
		{
			ansCode = e.getMessage();
			errDetails = RFTId5830.ErrorDetails.NULL;
			if (ansCode == null || ansCode.equals(""))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				ansCode = RFTId5830.ANS_CODE_ERROR;
				errDetails = RFTId5830.ErrorDetails.NULL;
			}
		}

		// オーバーフローが発生した場合
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" InventoryLocation:" + inventoryLocation +
							" ItemCode:" + JANCode +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			// 6026027=オーバーフロー発生のため、処理を中止しました。{0}
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
	
			ansCode = RFTId5830.ANS_CODE_ERROR;
			// 集約数オーバーフロー
			errDetails = RFTId5830.ErrorDetails.COLLECTION_OVERFLOW;
		}
		catch (IllegalAccessException e) {
        	// 6006003=インスタンスの生成に失敗しました。クラス名={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "BaseOperate");
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
	
			// 応答フラグ：エラー
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.INSTACIATE_ERROR;
		}
		// その他のエラーがあった場合
		catch (ReadWriteException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
	
			// 応答フラグ：エラー
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (DataExistsException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.DB_UNIQUE_KEY_ERROR;

		}
		catch (SQLException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.DB_ACCESS_ERROR;

		}
		catch (ScheduleException e)
		{
			// 6006001=予期しないエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.SCHEDULE_ERROR;

		}
		catch (ShelfInvalidityException e)
		{
		    String errData = " [LocationNo:" + inventoryLocation +
		    				 " RftNo:" + rftNo +
							 " WorkerCode:" + workerCode + "]";
		    
			// 6022039=指定された棚は自動倉庫の棚のため入力できません。{0}
			RftLogMessage.print(6022039, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.SHELF_INVALIDITY;

		}
		catch (Exception e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5830.ANS_CODE_ERROR;
			errDetails = RFTId5830.ErrorDetails.INTERNAL_ERROR;
		}


		// 応答電文の作成
		// STX
		rftId5830.setSTX();
		// SEQ
		rftId5830.setSEQ(0);
		// ID
		rftId5830.setID(RFTId5830.ID);
		// RFT送信時間
		rftId5830.setRftSendDate(rftId0830.getRftSendDate());
		// SERVER送信時間
		rftId5830.setServSendDate();
		// RFT号機
		rftId5830.setRftNo(rftNo);
		// 担当者コード
		rftId5830.setWorkerCode(workerCode);
		// 荷主コード
		rftId5830.setConsignorCode(consignorCode);
		// 荷主名をセット
		rftId5830.setConsignorName(consignorName);
		// エリアNoをセット
		rftId5830.setAreaNo(areaNo);
		// 棚卸ロケーション
		rftId5830.setInventoryLocation(inventoryLocation);
		// 商品識別コード
		rftId5830.setItemId(itemId);
		// 商品コードをセット
		rftId5830.setItemCode(itemCode);
		// JANコード
		rftId5830.setJANCode(JANCode);
		// ボールITFをセット
		rftId5830.setBundleITF(bundleITF);
		// ケースITFをセット
		rftId5830.setITF(ITF);
		// 商品名称をセット
		rftId5830.setItemName(itemName);
		// ボール入数をセット
		rftId5830.setBundleEnteringQty(bundleEnteringQty);
		// ケース入数をセット
		rftId5830.setEnteringQty(enteringQty);
		// 在庫数
		rftId5830.setStockQty(stockQty);
		// 棚卸数
		rftId5830.setInventoryCheckQty(inventoryCheckQty);
		// 荷姿(ケース・ピース)区分
		rftId5830.setItemForm(casePieceFlag) ;
		// 賞味期限
		rftId5830.setUseByDate(useByDate);
		// 在庫フラグ
		rftId5830.setStockFlag(stockFlag);
		// 応答フラグ
		rftId5830.setAnsCode(ansCode);
		// エラー詳細
		rftId5830.setErrDetails(errDetails);
		// ETX
		rftId5830.setETX();

		// 応答電文を獲得する。
		rftId5830.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
