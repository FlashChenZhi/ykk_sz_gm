// $Id: Id0831Process.java,v 1.1.1.1 2006/08/17 09:34:21 mori Exp $
package jp.co.daifuku.wms.master.storage.rft;

/*
 * Copyright 2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.InventoryCheck;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.storage.rft.RFTId0831;
import jp.co.daifuku.wms.storage.rft.RFTId5831;

/**
 * RFTからの簡易棚卸実績送信に対する処理を行うクラスです。<BR>
 * Id0831Operateクラスの提供する機能を使用し、棚卸作業情報の登録処理を行います。
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/19</TD><TD>E.Takeda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:21 $
 * @author  $Author: mori $
 */

public class Id0831Process extends IdProcess
{
	// Class fields----------------------------------------------------
	private static final String CLASS_NAME = "Id0831Process";

	//Class variables -----------------------------------------------
	
	//Class methods -------------------------------------------------
	
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:21 $";
	}
	
	//Constructors --------------------------------------------------
	//Public methods ------------------------------------------------
	/**
	 * 簡易棚卸実績送信処理を行います。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		//受信電文分解用のインスタンスを生成
		RFTId0831 rftId0831 = null;
		
		//送信電文作成用のインスタンスを生成
		RFTId5831 rftId5831 = null;
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0831 = (RFTId0831) PackageManager.getObject("RFTId0831");
			rftId0831.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5831 = (RFTId5831) PackageManager.getObject("RFTId5831");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*831", e.getMessage());
			throw e;
		}

		//応答フラグ保持用変数
		String ansCode = RFTId5831.ANS_CODE_NORMAL;
		
		//エラー詳細保持用変数
		String errDetails = RFTId5831.ErrorDetails.NORMAL;
		
		// 棚卸し実績データ保持用オブジェクト
		InventoryCheck resultData = new InventoryCheck();
		
		//RFT0830を使用して担当者コードを取得
		resultData.setWorkerCode(rftId0831.getWorkerCode());
		
		//RFT0831を使用してRFTNo.を取得
		resultData.setTerminalNo(rftId0831.getRftNo());
		
		//RFT0831を使用して荷主コードを取得
		resultData.setConsignorCode(rftId0831.getConsignorCode());
		
		//RFT0831を使用して荷主名を取得
		resultData.setConsignorName(rftId0831.getConsignorName());
		
		//RFT0831を使用してエリアNoを取得
		resultData.setAreaNo(rftId0831.getAreaNo());
		
		//RFT0831を使用して棚卸ロケーションを取得
		resultData.setLocationNo(rftId0831.getInventoryLocation());
		
		//RFT0831を使用してJANコードを取得
		resultData.setItemCode(rftId0831.getJANCode());
		
		//RFT0831を使用してボールITFを取得
		resultData.setBundleItf(rftId0831.getBundleITF());
		
		//RFT0831を使用してケースITFを取得
		resultData.setItf(rftId0831.getITF());
		
		//RFT0831を使用して商品名称を取得
		resultData.setItemName1(rftId0831.getItemName());
		
		//RFT0831を使用して荷姿を取得
		resultData.setCasePieceFlag(rftId0831.getItemForm().trim());
		
		//RFT0831を使用して賞味期限を取得
		resultData.setUseByDate(rftId0831.getUseByDate().trim());
		
		//RFT0831を使用して新規在庫作成区分を取得
		String enableCreateNewStock = rftId0831.getEnableCreateNewStock();
				
		//RFT0831を使用して完了区分を取得
		String completionFlag = rftId0831.getCompletionFlag().trim();
		
		try
		{
			if(DisplayText.isPatternMatching(rftId0831.getConsignorCode()))
			{
				throw new InvalidDefineException("CONSIGNOR_CODE[" + rftId0831.getConsignorCode() +"]");
			}
			if(DisplayText.isPatternMatching(rftId0831.getInventoryLocation()))
			{
				throw new InvalidDefineException("LOCATION_NO[" + rftId0831.getInventoryLocation() +"]");
			}
			if(DisplayText.isPatternMatching(rftId0831.getJANCode()))
			{
				throw new InvalidDefineException("JAN_CODE[" + rftId0831.getJANCode() +"]");
			}
			if(DisplayText.isPatternMatching(resultData.getUseByDate()))
			{
				throw new InvalidDefineException("USE_BY_DATE[" + resultData.getUseByDate() +"]");
			}
			//RFT0831を使用してボール入数を取得
			int bundleEnteringQty = rftId0831.getBundleEnteringQty();
			resultData.setBundleEnteringQty(bundleEnteringQty);
			
			//RFT0831を使用してケース入数を取得
			int enteringQty = rftId0831.getEnteringQty();
			resultData.setEnteringQty(enteringQty);
			
			//RFT0831を使用して在庫数を取得
			int stockQty = rftId0831.getStockQty();
			resultData.setStockQty(stockQty);
			
			//RFT0831を使用して元棚卸数を取得
			int originalInventoryQty = rftId0831.getOriginalInventoryQty();
			
			//RFT0831を使用して棚卸実績数を取得
			int inventoryResultQty = rftId0831.getInventoryResultQty();
			resultData.setResultStockQty(inventoryResultQty);
			
			//RFT0831を使用して作業時間を取得
			int workTime = rftId0831.getWorkSeconds();
			
			// Id0831Operateのインスタンスを生成
			Id0831Operate id0831Operate =
			    (Id0831Operate) PackageManager.getObject("Id0831Operate");
			id0831Operate.setConnection(wConn);
			id0831Operate.initialize();

			ansCode = id0831Operate.doComplete(resultData,
			        							originalInventoryQty,
			        							completionFlag,
			        							enableCreateNewStock,
			        							workTime);
			errDetails = id0831Operate.getErrDetails();
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "Id0831Operate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5831.ANS_CODE_ERROR;
			errDetails = RFTId5831.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (NumberFormatException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errDetails = RFTId5831.ErrorDetails.PARAMETER_ERROR;
			ansCode = RFTId5831.ANS_CODE_ERROR;
		}
		catch (InvalidDefineException e)
		{
			// 6026022=指定された値は、空白か、禁止文字が含まれています。{0}
			RftLogMessage.print(6026022, LogMessage.F_ERROR, CLASS_NAME, e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			// 応答フラグ：エラー
			ansCode = RFTId5831.ANS_CODE_ERROR;
			errDetails =RFTId5831.ErrorDetails.PARAMETER_ERROR; 
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			errDetails = RFTId5831.ErrorDetails.INTERNAL_ERROR;
			ansCode = RFTId5831.ANS_CODE_ERROR;
		}
		
		//RFTId1094インスタンスを使用して送信電文を作成
		//STX
		rftId5831.setSTX();

		//SEQ
		rftId5831.setSEQ(0);

		//ID
		rftId5831.setID(RFTId5831.ID);

		//ハンディ送信時間
		rftId5831.setRftSendDate(rftId0831.getRftSendDate());

		//サーバー送信時間
		rftId5831.setServSendDate();

		//RFT号機
		rftId5831.setRftNo(resultData.getTerminalNo());
		
		//担当者コード
		rftId5831.setWorkerCode(resultData.getWorkerCode());
		
		//応答フラグ
		rftId5831.setAnsCode(ansCode);
		
		// エラー詳細
		rftId5831.setErrDetails(errDetails);
		
		//ETX
		rftId5831.setETX();
		
		//電文の獲得
		rftId5831.getSendMessage(sdt);
		
	}
	
	//Package methods -----------------------------------------------

	//Protected methods ---------------------------------------------
	
	//Private methods -----------------------------------------------
	
}
//end of class
