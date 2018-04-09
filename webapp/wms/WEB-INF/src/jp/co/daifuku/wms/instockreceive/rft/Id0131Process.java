// $Id: Id0131Process.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
/**
 * Designer : Y.Taki <BR>
 * Maker :  E.Takeda <BR>
 * <BR>
 * RFTからの商品単位入荷検品実績送信(ID0131)に対する処理を行います。<BR>
 * Id0131Operateクラスの提供する機能を使用し、作業情報の該当する検品データの更新を行い、
 * RFTに送信する応答電文を生成します。<BR>
 * <BR>
 * 商品単位入荷検品実績送信処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   ID0131Operateクラスの機能で、キャンセル、保留又は確定の処理を呼び出し、応答フラグを取得します。<BR>
 *   応答フラグが0：正常の場合はRFT管理情報の電文項目をNULLに更新します。<BR>
 *   [更新条件]
 *   <UL><LI>RFTNo</LI></UL>
 *   [更新内容]
 *   <UL><LI>電文：NULL</LI>
 *       <LI>最終更新日時：システム日時</LI>
 *       <LI>最終更新処理名：ID0131</LI>
 *   </UL>
 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0131Process extends IdProcess
{
	// Class fields----------------------------------------------------

	// Class variables -----------------------------------------------
	private static final String CLASS_NAME = "Id0131Process";
	
	private static final String PROCESS_NAME = "ID0131";
		
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:14 $";
	}

	// Constructors --------------------------------------------------

	// Public methods ------------------------------------------------
	/**
	 * 商品単位入荷検品実績送信(ID0131)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5131)をバイト列で作成します。<BR>
	 * 受付区分によって、キャンセル、分納又は確定の処理を呼び出し、応答フラグを取得します。<BR>
	 * 入荷総アイテム数を取得します。<BR>
	 * 入荷残アイテム数を取得します。<BR>
     * 応答フラグが0：正常の場合はRFT管理情報の電文項目をNULLに更新します。<BR>
     * [更新条件]
     * <UL><LI>RFTNo</LI></UL>
     * [更新内容]
     * <UL><LI>電文：NULL</LI>
     *     <LI>最終更新日時：システム日時</LI>
     *     <LI>最終更新処理名：ID0131</LI>
     * </UL>
     * 
	 * 送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0131 rftId0131 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5131 rftId5131 = null;
		
		WorkingInformation resultData = null;
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0131 = (RFTId0131) PackageManager.getObject("RFTId0131");
			rftId0131.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5131 = (RFTId5131) PackageManager.getObject("RFTId5131");

			// 実績データ保持用のインスタンスを生成
			resultData = (WorkingInformation) PackageManager.getObject("WorkingInformation");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*131", e.getMessage());
			throw e;
		}

		// 受信電文からRFT号機を取得
		resultData.setTerminalNo(rftId0131.getRftNo());

		// 受信電文から担当者コードを取得
		resultData.setWorkerCode(rftId0131.getWorkerCode());

		// 受信電文から荷主コードを取得
		resultData.setConsignorCode(rftId0131.getConsignorCode());

		// 受信電文から入荷予定日を取得
		resultData.setPlanDate(rftId0131.getPlanDate());

		// 受信電文から仕入れ先コードを取得
		resultData.setSupplierCode(rftId0131.getSupplierCode());

		// 受信電文から商品コードを取得
		resultData.setItemCode(rftId0131.getJANCode());
		
		resultData.setCollectJobNo(rftId0131.getItemId());
		resultData.setItemCode(rftId0131.getJANCode());
		resultData.setBundleItf(rftId0131.getBundleITF());
		resultData.setItf(rftId0131.getITF());
		resultData.setItemName1(rftId0131.getItemName());
		resultData.setLotNo(rftId0131.getLotNo());
		resultData.setResultUseByDate(rftId0131.getUseByDate());

		// 受信電文から受付区分を取得
		String receiptClass = rftId0131.getReceiveFlag();

		// 応答フラグを保持する変数
		String ansCode = "";
		// エラー詳細を保持する変数
		String errDetails = RFTId5131.ErrorDetails.NORMAL;

		// 実績応答の総数、残数は未使用
		int totalCount = 0;
		int remainingCount = 0;
		
		Id0131Operate id0131Operate = null;

		try
		{
			if(!receiptClass.equals(ReceiptClass.CANCEL) && DisplayText.isPatternMatching(rftId0131.getUseByDate()))
			{
				throw new InvalidDefineException("USE_BY_DATE[" + rftId0131.getUseByDate() +"]");
			}
			resultData.setTcdcFlag(rftId0131.getTCDCFlag());
			resultData.setBundleEnteringQty(rftId0131.getBundleEnteringQty());
			resultData.setEnteringQty(rftId0131.getEnteringQty());
			resultData.setPlanEnableQty(rftId0131.getPlanQty());
			resultData.setResultQty(rftId0131.getResultQty());
			resultData.setWorkTime(rftId0131.getWorkSeconds());

			// Id0131Operateのインスタンスを生成
			id0131Operate = (Id0131Operate) PackageManager.getObject("Id0131Operate");
			id0131Operate.setConnection(wConn);

			// 誤検回数を取得する
			int missScanCnt = rftId0131.getInspectionErrCount();

			ansCode = id0131Operate.doComplete(resultData, receiptClass, missScanCnt);
		
			if(ansCode.equals(RFTId5131.ANS_CODE_NORMAL))
			{
				// 応答フラグが正常の場合は作業中データを削除する
				RFTId5131.deleteWorkingData(rftId0131.getRftNo(), PROCESS_NAME,  wConn);
				wConn.commit();
			}

		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "Id0131Operate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5131.ANS_CODE_ERROR;
			errDetails = RFTId5131.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (InvalidStatusException e)
		{
			RftLogMessage.printStackTrace(6006003, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5131.ANS_CODE_ERROR;
			errDetails = RFTId5131.ErrorDetails.DB_INVALID_VALUE;
		}
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
			ansCode = RFTId5131.ANS_CODE_ERROR;
			errDetails = RFTId5131.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5131.ANS_CODE_ERROR;
			errDetails = RFTId5131.ErrorDetails.PARAMETER_ERROR;
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
			ansCode = RFTId5131.ANS_CODE_ERROR;
			errDetails = RFTId5131.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch(NumberFormatException e)
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
			ansCode = RFTId5131.ANS_CODE_ERROR;
			errDetails = RFTId5131.ErrorDetails.PARAMETER_ERROR;
		}
		catch(Exception e)
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
			ansCode = RFTId5131.ANS_CODE_ERROR;
			errDetails = RFTId5131.ErrorDetails.INTERNAL_ERROR;
			
		}
		
		// 応答電文の作成
		// STX
		rftId5131.setSTX();
		// SEQ
		rftId5131.setSEQ(0);
		// ID
		rftId5131.setID(RFTId5131.ID);
		// RFT送信時間
		rftId5131.setRftSendDate(rftId0131.getRftSendDate());
		// SERVER送信時間
		rftId5131.setServSendDate();
		// RFT号機
		rftId5131.setRftNo(rftId0131.getRftNo());
		// 担当者コード
		rftId5131.setWorkerCode(rftId0131.getWorkerCode());
		// 総アイテム数
		rftId5131.setTotalItemCount(totalCount);
		// 残アイテム数
		rftId5131.setRemainingItemCount(remainingCount);
		// 応答フラグ
		rftId5131.setAnsCode(ansCode);
		// エラー詳細
		if (ansCode.equals(RFTId5131.ANS_CODE_ERROR)
				&& errDetails.equals(RFTId5131.ErrorDetails.NORMAL))
		{
			errDetails = id0131Operate.getErrorDetails();
		}
		rftId5131.setErrDetails(errDetails);

		// ETX
		rftId5131.setETX();
		
		// 応答電文を獲得する。
		rftId5131.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

