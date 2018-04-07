// $Id: Id0013Process.java,v 1.1.1.1 2006/08/17 09:34:15 mori Exp $
package jp.co.daifuku.wms.master.base.rft;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.Item;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.RFTId0013;
import jp.co.daifuku.wms.base.rft.RFTId5013;

/**
 * Designer : T.Konishi<BR>
 * Maker : <BR>
 * <BR>
 * RFTからの商品情報問合せ要求(ID0013)に対する処理を行います。<BR>
 * Id0013Operateクラスの提供する機能を使用し、在庫情報から商品情報を取得します。<BR>
 * RFTに送信する応答電文を生成します。<BR>
 * <BR>
 * 商品情報問合せ要求処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *  受信データから、必要な情報を取得します。<BR>
 *  Id0013Operateクラスの機能で、在庫情報を検索し、荷主コード＋商品コードが一致する商品の情報を取得します。<BR>
 *  該当データのうち最も入庫日が新しいデータを入庫商品の情報とします。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:15 $
 * @author  $Author: mori $
 */
public class Id0013Process extends IdProcess
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0013Process";
	
	// Class variables -----------------------------------------------
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:15 $";
	}

	// Constructors --------------------------------------------------
	public Id0013Process()
	{
		super() ;
	}
	
	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn		DBConnection情報
	 */
	public Id0013Process(Connection conn)
	{
		super() ;
		wConn = conn ;
	}

	// Public methods ------------------------------------------------

	/**
	 * 商品情報問合せ要求(ID0013)に対する処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5013)をバイト列で作成します。<BR>
	 * <BR>
	 * 日次処理中で無いことを確認します。
	 * 日次処理中の場合は、5:日次処理中のエラーを返します。<BR>
	 * 作業者別実績の対象レコードがある事を確認します。無い場合は、新規作成します。<BR>
	 * ID0013Operateクラスを利用し、在庫情報を検索します。<BR>
	 * １最も入庫日の新しい在庫の情報を送信電文にセットします。<BR>
	 * <BR>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		RFTId0013 rftId0013 = null;
		RFTId5013 rftId5013 = null;
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0013 = (RFTId0013) PackageManager.getObject("RFTId0013");
			rftId0013.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5013 = (RFTId5013) PackageManager.getObject("RFTId5013");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0013.getRftNo();

		// 受信電文から作業者コードを取得
		String workerCode = rftId0013.getWorkerCode();
		
		// 受信電文から荷主コードを取得
		String consignorCode = rftId0013.getConsignorCode();
		
		// 受信電文からスキャン商品コードを取得
		String itemCode = rftId0013.getScanItemCode1();
		
		// 受信電文からスキャン商品コード2を取得
		String convertedJanCode = rftId0013.getScanItemCode2();
		
		// 受信電文から荷姿を取得
		String itemForm = rftId0013.getItemForm();
			
		// 荷主名を保持する変数
		String consignorName = "";

		// エリアを保持する変数
		String areaNo = "";

		// ロケーションを保持する変数
		String locationNo = "";

		// JANコードを保持する変数
		String janCode = "";

		// 商品識別コードを保持する変数
		String itemId = "";
	
		// ボールITFを保持する変数
		String bundleItf = "";

		// ケースITFを保持する変数
		String caseItf = "";

		// 商品名称を保持する変数
		String itemName = "";

		// ボール入数を保持する変数
		int bundleEnteringQty = 0;

		// ケース入数を保持する変数
		int enteringQty = 0;

		// 賞味期限を保持する変数
		String useByDate = "";
	
		// 移動ラック有無フラグ
		String mobileRackFlag = RFTId5013.NO_MOBILE_RACK_FLAG;

		// 応答フラグを保持する変数
		String ansCode = RFTId5013.ANS_CODE_NORMAL;
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5013.ErrorDetails.NORMAL;
		
		try
		{
			if(DisplayText.isPatternMatching(itemCode))
			{
				errDetails = RFTId5013.ErrorDetails.NULL;
				throw new NotFoundException(RFTId5013.ANS_CODE_ERROR);
			}
			if(DisplayText.isPatternMatching(convertedJanCode))
			{
				errDetails = RFTId5013.ErrorDetails.NULL;
				throw new NotFoundException(RFTId5013.ANS_CODE_ERROR);
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
				ansCode = RFTId5013.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//-----------------
				// 作業者別実績の存在チェック
				//-----------------
				WorkerResult[] workerResult =
					baseOperate.getWorkerResult(
						WorkingInformation.JOB_TYPE_EX_STORAGE,
						workerCode,
						rftNo);
				if (workerResult.length == 0)
				{
					// 作業者実績の新規作成
					baseOperate.createWorkerResult(
						WorkingInformation.JOB_TYPE_EX_STORAGE,
						workerCode,
						rftNo);
					// commit
					wConn.commit();
				}

				// Id0013Operateのインスタンスを生成
				Id0013Operate id0013Operate = (Id0013Operate) PackageManager.getObject("Id0013Operate");
				id0013Operate.setConnection(wConn);
				
				
				// 移動ラックパッケージが導入されているか
				if (baseOperate.isExistIdmPackage(wConn))
				{
					mobileRackFlag = RFTId5013.MOBILE_RACK_FLAG;
				}
				
				
				//-----------------
				// 商品マスタ情報からデータを取得
				//-----------------
				Item itemInfo = id0013Operate.getItemFromMaster(
					consignorCode,
					itemCode,
					convertedJanCode,
					itemForm,
					rftNo,
					workerCode);
				if (itemInfo != null)
				{
					// 商品マスタデータから取得できるデータを取得し、保持変数にセット
					locationNo = "";
					areaNo = "";
					itemId = "";
					janCode = itemInfo.getItemCode();
					bundleItf = itemInfo.getBundleItf();
					caseItf = itemInfo.getITF();
					itemName = itemInfo.getItemName1();
					bundleEnteringQty = itemInfo.getBundleEnteringQty();
					enteringQty = itemInfo.getEnteringQty();
					useByDate = "";
					
					consignorName = id0013Operate.getConsignorNameFromMaster(consignorCode);
					if (consignorName == null)
					{
						consignorName = "";
					}
					ansCode = RFTId5013.ANS_CODE_NORMAL;
			
				}
				else{
					//-----------------
					// 在庫情報からデータを取得
					//-----------------				
					Stock stockInfo = id0013Operate.getItemFromStock(
						consignorCode,
						itemCode,
						convertedJanCode,
						itemForm,
						rftNo,
						workerCode);
	
					if (stockInfo != null)
					{
						// 在庫データから取得できるデータを取得し、保持変数にセット
						consignorName = stockInfo.getConsignorName() ;
						locationNo = stockInfo.getLocationNo();
						areaNo = stockInfo.getAreaNo();
						itemId = stockInfo.getStockId();
						janCode = stockInfo.getItemCode();
						bundleItf = stockInfo.getBundleItf();
						caseItf = stockInfo.getItf();
						itemName = stockInfo.getItemName1();
						bundleEnteringQty = stockInfo.getBundleEnteringQty();
						enteringQty = stockInfo.getEnteringQty();
						useByDate = stockInfo.getUseByDate();
						ansCode = RFTId5013.ANS_CODE_NORMAL;
					}
					else
					{
						// 応答フラグを 9:エラー （エラー詳細 20:対象無し） でリターン
						errDetails = RFTId5013.ErrorDetails.NULL;
						ansCode = RFTId5013.ANS_CODE_ERROR;
					}
				}
			}

		}
		// 情報を取得できなかった場合はエラー
		catch(NotFoundException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = e.getMessage();
			if (ansCode == null || ansCode == "")
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				errDetails = RFTId5013.ErrorDetails.NULL;
				ansCode = RFTId5013.ANS_CODE_ERROR;		
			}
		}
		// その他のエラーがあった場合
		catch(ReadWriteException e)
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
			ansCode = RFTId5013.ANS_CODE_ERROR ;
			errDetails = RFTId5013.ErrorDetails.DB_ACCESS_ERROR;
		} 
		catch (IllegalAccessException e) {
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*013",e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5013.ANS_CODE_ERROR;
			errDetails = RFTId5013.ErrorDetails.INSTACIATE_ERROR;
		} 
		catch (DataExistsException e) {
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
			ansCode = RFTId5013.ANS_CODE_ERROR;
			errDetails = RFTId5013.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		} 
		catch (SQLException e) {
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
			ansCode = RFTId5013.ANS_CODE_ERROR;
			errDetails = RFTId5013.ErrorDetails.DB_ACCESS_ERROR;
		} 

		catch (ScheduleException e)
		{
			// 6006001=予期しないエラーが発生しました。{0}
			RftLogMessage.print(6006001, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			// 応答フラグ：エラー
			ansCode = RFTId5013.ANS_CODE_ERROR;
			errDetails = RFTId5013.ErrorDetails.SCHEDULE_ERROR;
		}
		// その他のエラー
		catch(Exception e)
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
			ansCode = RFTId5013.ANS_CODE_ERROR ;
			errDetails = RFTId5013.ErrorDetails.INTERNAL_ERROR;
		}


		// 応答電文の作成
		// STX
		rftId5013.setSTX();
		// SEQ
		rftId5013.setSEQ(0);
		// ID
		rftId5013.setID(RFTId5013.ID);
		// RFT送信時間
		rftId5013.setRftSendDate(rftId0013.getRftSendDate());
		// SERVER送信時間
		rftId5013.setServSendDate();
		// RFT号機
		rftId5013.setRftNo(rftNo);

		// 担当者コード
		rftId5013.setWorkerCode(workerCode);
		
		// 荷主コード
		rftId5013.setConsignorCode(consignorCode);
		
		// 荷主名をセット
		rftId5013.setConsignorName(consignorName);
		
		// エリアNo.をセット
		rftId5013.setAreaNo(areaNo);

		// ロケーションNo.をセット
		rftId5013.setLocationNo(locationNo);
		
		// 商品識別コードをセット
		rftId5013.setItemId(itemId);
		
		// JANコード
		rftId5013.setJANCode(janCode);
		
		// ボールITFをセット
		rftId5013.setBundleITF(bundleItf);
		
		// ケースITFをセット
		rftId5013.setITF(caseItf);
		
		// 商品名称をセット
		rftId5013.setItemName(itemName);
		
		// ボール入数をセット
		rftId5013.setBundleEnteringQty(bundleEnteringQty);
		
		// ケース入数をセット
		rftId5013.setEnteringQty(enteringQty);

		// 賞味期限をセット
		rftId5013.setUseByDate(useByDate);

		// 荷姿をセット
		rftId5013.setItemForm(itemForm);
		
		// 移動ラック有無フラグ
		rftId5013.setMobileRackFlag(mobileRackFlag);
		
		// 応答フラグ
		rftId5013.setAnsCode(ansCode);
		
		// エラー詳細
		rftId5013.setErrDetails(errDetails);
		
		// ETX
		rftId5013.setETX();

		// 応答電文を獲得する。
		rftId5013.getSendMessage(sdt) ;

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
