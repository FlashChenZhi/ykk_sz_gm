// $Id: Id0121Process.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.IOException;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdProcess;

/**
 * RFTからの出荷先一覧要求に対する処理を行う。
 * Id0121Operateクラスの提供する機能を使用して、出荷先の一覧を検索し、応答電文を生成します。
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0121Process extends IdProcess
{
	// Class fields----------------------------------------------------
	/**
	 * クラス名を表すフィールド
	 */
	private static final String CLASS_NAME = "Id0121Process";

	// Class variables -----------------------------------------------

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
	 * 出荷先問い合わせ処理を行います。
	 * 受信した作業予定日、荷主コード、仕入先コードから出荷先一覧を検索し、検索結果をファイルに書きこみます。
	 * また、出荷先一覧ファイル名を送信電文にセットします。
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0121 rftId0121 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5121 rftId5121 = null;

		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0121 = (RFTId0121) PackageManager.getObject("RFTId0121");
			rftId0121.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5121 = (RFTId5121) PackageManager.getObject("RFTId5121");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*121");
			throw e;
		}
		// 受信電文からRFT号機を取得
		String rftNo = rftId0121.getRftNo();
		
		// 受信電文から作業者コードを取得
		String workerCode = rftId0121.getWorkerCode();
		
		// 受信電文から作業予定日を取得
		String planDate = rftId0121.getWorkDay();
		
		// 受信電文から荷主コードを取得
		String consignorCode = rftId0121.getConsignorCode();
		
		// 受信電文から仕入先コードを取得
		String supplierCode = rftId0121.getSupplierCode();

		// 応答フラグを保持する変数
		String ansCode = RFTId5121.ANS_CODE_NORMAL;
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5121.ErrorDetails.NORMAL;
		
		// 取得した出荷先情報を格納する配列
		WorkingInformation[] workinfo = null;

		// ファイル名を作成
		String sendpath = WmsParam.RFTSEND;		// wms/rft/send/
		
		// 送信ファイル名
		String sendFileName = sendpath + rftNo + "\\" + Id5121DataFile.ANS_FILE_NAME;
		
		String className = "";
		try
		{
			// Id0121Operateのインスタンスを生成
		    className = "Id0121Operate";
			Id0121Operate id0121Operate = (Id0121Operate) PackageManager.getObject(className);
			id0121Operate.setConnection(wConn);

			// 出荷先一覧情報を取得
			workinfo = id0121Operate.getCustomerList(planDate, consignorCode, supplierCode, workerCode, rftNo);

			// 出荷先一覧ファイルを作成
			className = "Id5121DataFile";
			Id5121DataFile dataFile = (Id5121DataFile) PackageManager.getObject(className);
			dataFile.setFileName(sendFileName);
			dataFile.write(workinfo);

			
		}
		// WorkingInformationOperateのインスタンスから情報を取得できなかった場合はエラー
		catch (NotFoundException e)
		{
			// 応答フラグ：該当データ無し
			ansCode = RFTId5121.ANS_CODE_NULL;
		}
		// その他のエラーがあった場合
		catch (ReadWriteException e)
		{
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5121.ANS_CODE_ERROR;
			errDetails = RFTId5121.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e)
        {
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, className, e.getMessage());
			// 応答フラグ：エラー
			ansCode = RFTId5121.ANS_CODE_ERROR ;
			errDetails = RFTId5121.ErrorDetails.INSTACIATE_ERROR;
        }
		catch (IOException e)
        {
		    // 6006020 = ファイルの入出力エラーが発生しました。{0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			// 応答フラグ：エラー
			ansCode = RFTId5121.ANS_CODE_ERROR ;
			errDetails = RFTId5121.ErrorDetails.I_O_ERROR;
        }
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5121.ANS_CODE_ERROR;
			errDetails = RFTId5121.ErrorDetails.INTERNAL_ERROR;
		}

		// 応答電文の作成
		// STX
		rftId5121.setSTX();
		
		// SEQ
		rftId5121.setSEQ(0);
		
		// ID
		rftId5121.setID(RFTId5121.ID);
		
		// RFT送信時間
		rftId5121.setRftSendDate(rftId0121.getRftSendDate());
		
		// SERVER送信時間
		rftId5121.setServSendDate();
		
		// RFT号機
		rftId5121.setRftNo(rftNo);

		// 担当者コード
		rftId5121.setWorkerCode(rftId0121.getWorkerCode());
		
		// 出荷先一覧ファイル名
		rftId5121.setTableFileName(sendFileName) ;
		
		// ファイルレコード数
		if (ansCode.equals(RFTId5121.ANS_CODE_NORMAL))
		{
			rftId5121.setFileRecordNumber(workinfo.length);
		}
		else
		{
			rftId5121.setFileRecordNumber(0);
		}
		
		// 応答フラグ
		rftId5121.setAnsCode(ansCode);
		
		// エラー詳細
		rftId5121.setErrDetails(errDetails);
		
		// ETX
		rftId5121.setETX() ;

		// 応答電文を獲得する。
		rftId5121.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class
