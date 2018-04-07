// $Id: Id0911Process.java,v 1.1.1.1 2006/08/17 09:34:20 mori Exp $
package jp.co.daifuku.wms.master.stockcontrol.rft;

import java.io.IOException;
import java.sql.SQLException;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.stockcontrol.rft.RFTId0911;
import jp.co.daifuku.wms.stockcontrol.rft.RFTId5911;

/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * RFTからの在庫情報問合せに対する処理を行います。
 * Id0911Operateクラスの提供する機能を使用し、在庫情報から例外出庫可能なデータを検索します。
 * RFTに送信する応答電文を生成します。 <BR>
 * <BR>
 * 在庫情報問合せ処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *  受信データから作業条件を取得します。<BR>
 *  システム情報から在庫管理パッケージの有無をチェックします。<BR>
 *  在庫管理パッケージありの場合、Id0911Operateクラスの機能で在庫情報を検索し、例外出庫可能なデータを取得します。<BR>
 *  送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 *  在庫管理パッケージありの場合で、賞味期限が指定されておらず、かつ複数賞味期限が該当する場合には、
 * 　	応答フラグに、7(複数件該当)をセットし、一覧ファイルを作成します。
 * 	在庫管理パッケージなしの場合、在庫の検索は行わず、在庫に関する情報は空白または0で送信電文を作成し、送信します。
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:20 $
 * @author  $Author: mori $
 */
public class Id0911Process extends IdProcess
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0911Process";

	/**
	 * 在庫賞味期限一覧ファイル名を表すフィールド
	 */
	private static final String TABLE_FILE_NAME = "ID5911.txt";

	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:20 $";
	}

	// Constructors --------------------------------------------------
	// Public methods ------------------------------------------------
	/**
	 * 在庫情報問合せ処理(ID0911)を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5911)をバイト列で作成します。<BR>
	 * <BR>
	 * 日次処理中で無いことを確認します。
	 * 日次処理中の場合は、5:日次処理中のエラーを返します。<BR>
	 * WareNaviSystemクラスを利用し、在庫管理パッケージの有無を調べます。<BR>
	 * 在庫管理パッケージありの場合は、ID0911Operateクラスを利用し、要求のあった条件で在庫情報を検索します。<BR>
	 * 出庫可能な在庫が1件該当した時には、その在庫データを送信電文にセットし、
	 * 応答フラグに「0:出庫可能在あり」をセットします。
	 * 出庫可能な在庫が複数件該当した時には、賞味期限一覧ファイルを作成し、応答フラグに7:複数件該当をセットします。<BR>
	 * 出庫可能な在庫が無い時は、応答フラグに「8:出庫可能在無し」をセットします。<BR>
	 * 在庫管理パッケージ無しの場合は、応答フラグに「3:在庫管理無し」をセットします。<BR>
	 * 送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
	 * <BR>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{

		RFTId0911 rftId0911 = null;
		RFTId5911 rftId5911 = null;
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0911 = (RFTId0911) PackageManager.getObject("RFTId0911");
			rftId0911.setReceiveMessage(rdt);
			// 送信電文作成用のインスタンスを生成
			rftId5911 = (RFTId5911) PackageManager.getObject("RFTId5911");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0911.getRftNo();

		// 受信電文から作業者コードを取得
		String workerCode = rftId0911.getWorkerCode();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0911.getConsignorCode();
		
		// 受信電文からエリアNo.を取得
		String areaNo = rftId0911.getAreaNo();
		
		// 受信電文からロケーションNo.を取得
		String locationNo = rftId0911.getLocationNo();
		
		// 受信電文から荷姿（ｹｰｽ・ﾋﾟｰｽ区分）を取得
		String casePieceFlag =rftId0911.getCasePieceFlag();
		
		// 受信電文からスキャンコード１を取得	
		String scanCode1 = rftId0911.getScanCode1();
		
		// 受信電文からスキャンコード２を取得
		String scanCode2 = rftId0911.getScanCode2();
				
		// 受信電文から一覧選択フラグを取得
		String listSelectionFlag = rftId0911.getListSelectionFlag();

		// 受信電文から賞味期限を取得
		String useByData = rftId0911.getUseByDate();

		// 荷主名を保持する変数
		String consignorName = "";
		
		//商品識別コードを保持する変数
		String itemID = "";

		// 商品コードを保持する変数
		String itemCode = "" ;
		
		// JANコードを保持する変数
		String JANCode = "";

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
		
		// 出庫可能数を保持する変数
		int stockQty = 0;	
		
		// 送信ファイル名を保持する変数
		String sendFileName = "";
		
		// ファイルレコード数を保持する変数
		int fileRecordNo = 0;

		// 応答フラグを保持する変数
		String ansCode = "";
		
		// エラー詳細を保持する変数
		String errorDetail = RFTId5911.ErrorDetails.NORMAL;

		// 一覧ファイル名を保持する変数
		String saveFileName = "";

		try
		{
			if(DisplayText.isPatternMatching(locationNo))
			{
				throw new NotFoundException(RFTId5911.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(areaNo))
			{
				throw new NotFoundException(RFTId5911.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(scanCode1))
			{
				throw new NotFoundException(RFTId5911.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(scanCode2))
			{
				throw new NotFoundException(RFTId5911.ANS_CODE_NULL);
			}

			// BaseOperateのインスタンスを生成
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//-----------------
			// 日次処理中チェック
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				// 状態フラグを 5:日次更新処理中 でリターン
				ansCode = RFTId5911.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				// WareNaviSystemHandlerのインスタンスを生成
				WareNaviSystemHandler wareNaviSystemHandler = new WareNaviSystemHandler(wConn);

				// Id0152Operateのインスタンスを生成
				Id0911Operate id0911Operate = (Id0911Operate)PackageManager.getObject("Id0911Operate");
				id0911Operate.setConnection(wConn);

				//	在庫管理パッケージの検索
				WareNaviSystem[] wareNaviSystem =
					(WareNaviSystem[]) wareNaviSystemHandler.find(new WareNaviSystemSearchKey());

				//在庫管理なしの場合
				if (wareNaviSystem[0].getStockPack().equals("0"))
				{
					//-----------------
					// 商品マスタ情報からデータを取得
					//-----------------
					Item itemInfo = id0911Operate.getItemFromMaster(
						consignorCode,
						scanCode1,
						scanCode2,
						rftNo,
						workerCode);
					if (itemInfo == null)
					{
						// 応答フラグ：該当データなし
						ansCode = RFTId5911.ANS_CODE_NULL;
					}
					else
					{
						// 商品マスタデータから取得できるデータを取得し、保持変数にセット
						locationNo = "";
						areaNo = "";
						itemID = "";
						JANCode = itemInfo.getItemCode();
						bundleITF = itemInfo.getBundleItf();
						ITF = itemInfo.getITF();
						itemName = itemInfo.getItemName1();
						bundleEnteringQty = itemInfo.getBundleEnteringQty();
						enteringQty = itemInfo.getEnteringQty();
						useByData = "";
						
						consignorName = id0911Operate.getConsignorNameFromMaster(consignorCode);
						if (consignorName == null)
						{
							consignorName = "";
						}
						//	応答フラグ：正常
						ansCode = RFTId5911.ANS_CODE_NORMAL;
					}
				}
				//在庫管理ありの場合
				else
				{
					//在庫情報を検索、取得
					Stock[] stock = null;	
					stock=id0911Operate.getDeliverableStockData(
										consignorCode,		// 荷主コード
										areaNo,				// エリアNo.
										locationNo,			// ロケーションNo.
										casePieceFlag,		// ｹｰｽ・ﾋﾟｰｽ区分
										scanCode1,			// スキャンコード1
										scanCode2,			// スキャンコード2
										listSelectionFlag,	// 一覧選択フラグ
										useByData			// 賞味期限
										);	
						
					// JANコードをセット
					JANCode = stock[0].getItemCode();
					// 荷主名をセット
					consignorName = stock[0].getConsignorName();
					// 商品識別コードをセット
					itemID = stock[0].getStockId();				
					// ボールITFをセット
					bundleITF = stock[0].getBundleItf();
					// ケースITFをセット
					ITF = stock[0].getItf();
					// 商品名称をセット
					itemName = stock[0].getItemName1();
					// ボール入数をセット
					bundleEnteringQty = stock[0].getBundleEnteringQty();
					// ケース入数をセット
					enteringQty = stock[0].getEnteringQty();
					// 賞味期限をセット
					useByData = stock[0].getUseByDate();
					// 移動出庫可能数を算出
					stockQty = stock[0].getAllocationQty();
					//	応答フラグ：正常
					ansCode = RFTId5911.ANS_CODE_NORMAL;
					
					// データが複数件の場合
					if (stock.length > 1)
					{
						// ファイル名を作成
						String datapath = WmsParam.DAIDATA; // c:/daifuku/data/
						String sendpath = WmsParam.RFTSEND; // wms/rft/send/
						// 送信ファイル名
						sendFileName = sendpath + rftNo + "\\" + TABLE_FILE_NAME;
						// 保存ファイル名
						saveFileName = datapath + sendFileName;

						// 一覧ファイルを作成
						id0911Operate.createTableFile(stock, saveFileName);
						// ファイルレコード数を取得
						fileRecordNo = stock.length;
						// 応答フラグ：複数件該当
						ansCode = RFTId5911.ANS_CODE_SOME_DATA;
					}
				}
			}
		}
		// stockOperateのインスタンスから情報を取得できなかった場合はエラー
		catch (NotFoundException e)
		{
			// 応答フラグ：該当データなし
			ansCode = RFTId5911.ANS_CODE_NULL;
		}
		// オーバーフローが発生した場合
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" AreaNo:" + areaNo +
							" LocationNo:" + locationNo +
							" ItemCode:" + JANCode +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			// 6026027=オーバーフロー発生のため、処理を中止しました。{0}
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME, errData);
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.COLLECTION_OVERFLOW;
		}
		// その他のエラーがあった場合
		catch (ReadWriteException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.DB_ACCESS_ERROR;
		}
        catch (IllegalAccessException e)
        {
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			// 応答フラグ：エラー
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.INSTACIATE_ERROR;
        }

		catch (IOException e)
		{
			// ログを出力する
			// 6006020=ファイルの入出力エラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006020, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.I_O_ERROR;
			
		}
		catch (ScheduleException e)
		{
			// ログを出力する
			// 6006001=予期しないエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.SCHEDULE_ERROR;
			
		}
		catch (Exception e)
		{
			// 6026015=ID対応処理で異常が発生しました。{0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5911.ANS_CODE_ERROR;
			errorDetail = RFTId5911.ErrorDetails.INTERNAL_ERROR;
		}

		// 応答電文の作成
		// STX
		rftId5911.setSTX();
		// SEQ
		rftId5911.setSEQ(0);
		// ID
		rftId5911.setID(RFTId5911.ID);
		// RFT送信時間
		rftId5911.setRftSendDate(rftId0911.getRftSendDate());
		// SERVER送信時間
		rftId5911.setServSendDate();
		// RFT号機
		rftId5911.setRftNo(rftNo);

		// 担当者コード
		rftId5911.setWorkerCode(workerCode);

		// 荷主コード
		rftId5911.setConsignorCode(consignorCode);
		
		// エリアNo
		rftId5911.setAreaNo(areaNo);	
		
		// ロケーションNo
		rftId5911.setLocationNo(locationNo);	

		// ｹｰｽ・ﾋﾟｰｽ区分
		rftId5911.setCasePieceFlag(casePieceFlag);
		
		// 荷主名をセット
		rftId5911.setConsignorName(consignorName);
		
		// 商品識別コード
		rftId5911.setItemId(itemID);

		// 商品コード
		rftId5911.setItemCode(itemCode);
		
		// JANコード
		rftId5911.setJANCode(JANCode);
		
		// ボールITFをセット
		rftId5911.setBundleITF(bundleITF);

		// ケースITFをセット
		rftId5911.setITF(ITF);

		// 商品名称をセット
		rftId5911.setItemName(itemName);

		// ボール入数をセット
		rftId5911.setBundleEnteringQty(bundleEnteringQty);

		// ケース入数をセット
		rftId5911.setEnteringQty(enteringQty);
		
		// 賞味期限をセット
		rftId5911.setUseByDate(useByData);

		// 一覧ファイル名をセット
		rftId5911.setFileName(sendFileName);
		
		// ファイルレコード数をセット
		rftId5911.setFileRecordNo(fileRecordNo);
		
		// 出庫可能数をセット
		rftId5911.setStockQty(stockQty);

		// 応答フラグ
		rftId5911.setAnsCode(ansCode);
		
		// エラー詳細をセット
		rftId5911.setErrDetails(errorDetail);

		// ETX
		rftId5911.setETX();

		// 応答電文を獲得する。
		rftId5911.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
