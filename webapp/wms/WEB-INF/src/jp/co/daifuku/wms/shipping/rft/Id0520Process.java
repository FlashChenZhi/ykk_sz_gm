// $Id: Id0520Process.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.CustomerListOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;
import jp.co.daifuku.wms.instockreceive.rft.RFTId5121;


/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * 出荷検品出荷先一覧要求(ID0520)に対する処理を行うためのクラスです。<BR>
 * 受信電文から検索条件を取得し作業情報の検索を行い<BR>
 * 出荷検品出荷先一覧ファイルを作成し応答電文の生成を行います。<BR>
 * 検索及びファイル作成処理はIdCustomerListOperateクラスを使用します。<BR>
 * <BR>
 * 出荷検品出荷先一覧要求処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信電文をパラメータとして受取り、応答電文を生成します。<BR>
 *   受信電文より作業予定日、荷主コードを取得し、検索条件として出荷検品出荷先一覧情報を取得します。<BR>
 *   出荷検品出荷先一覧情報が取得できた場合は出荷検品出荷先一覧ファイルの作成を行い、正常応答電文を生成します。<BR>
 *   取得できない場合は原因を判別し、異常応答電文を生成します。<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class Id0520Process extends IdProcess
{
	// Class fields----------------------------------------------------
	
	// Class variables -----------------------------------------------
	private static final String CLASS_NAME = "Id0520Process";

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public Id0520Process()
	{
		super() ;
	}
	
	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn DBConnection情報
	 */
	public Id0520Process(Connection conn)
	{
		super() ;
		wConn = conn ;
	}

	// Public methods ------------------------------------------------
	/**
	 * 出荷検品出荷先一覧要求処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答をバイト列で作成します。<BR>
	 * <BR>
	 * <DIR>
	 *   受信データから、作業予定日、荷主コードを取得します。 <BR>
	 *   一覧ファイル名を作成します。 <BR>
	 *   IdCustomerListOperateクラスの機能で作業情報を検索し、応答フラグ、一覧データを取得します。 <BR>
	 *   応答フラグが正常であれば、検索結果をファイルに書きます。 <BR>
	 *   送信電文を作成し、送信バッファに送信するテキストをセットします。 <BR>
	 * </DIR>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{

		// 受信電文解析用のインスタンスを生成
		RFTId0520 rftId0520 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5520 rftId5520 = null;
		
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0520 = (RFTId0520)PackageManager.getObject("RFTId0520");
			rftId0520.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5520 = (RFTId5520)PackageManager.getObject("RFTId5520");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME,e.getMessage());
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0520.getRftNo() ;
		
		// 受信電文から作業予定日を取得
		String planDate = rftId0520.getPlanDate() ;
		
		// 受信電文から荷主コードを取得
		String consignorCode = rftId0520.getConsignorCode() ;

		// 応答フラグを保持する変数
		String ansCode = RFTId5520.ANS_CODE_NORMAL;
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5520.ErrorDetails.NORMAL;
	
		// 取得した出荷検品出荷先一覧情報を格納する配列
		WorkingInformation[] workinfo = null;

		// ファイル名を作成
		String sendpath = WmsParam.RFTSEND;
		
		// 送信ファイル名
		String sendFileName = sendpath + rftNo + "\\" + Id5520DataFile.ANS_FILE_NAME;

		String className = "";
		try
		{
			// CustomerListOperateのインスタンスを生成
		    className = "CustomerListOperate";
		    CustomerListOperate customerListOperate = (CustomerListOperate) PackageManager.getObject(className);
			customerListOperate.setConnection(wConn);

			// 出荷検品出荷先一覧情報を取得
			workinfo = customerListOperate.findCustomerAll(planDate , consignorCode ,WorkingInformation.JOB_TYPE_SHIPINSPECTION);
			
			// 出荷検品出荷先一覧ファイルを作成
			className = "Id5520DataFile";
			Id5520DataFile dataFile = (Id5520DataFile) PackageManager.getObject(className);
			dataFile.setFileName(sendFileName);
			dataFile.write(workinfo);
	
		}
		// WorkingInformationOperateのインスタンスから情報を取得できなかった場合はエラー
		catch(NotFoundException e)
		{
			// 応答フラグ：該当荷主無し
			ansCode = RFTId5520.ANS_CODE_NULL ;
		}

		// その他のエラーがあった場合
		catch (ReadWriteException e)
		{
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5520.ANS_CODE_ERROR;
			errDetails = RFTId5520.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, className, e.getMessage());
			// 応答フラグ：エラー
			ansCode = RFTId5520.ANS_CODE_ERROR ;
			errDetails = RFTId5520.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (IOException e)
		{
		    // 6006020 = ファイルの入出力エラーが発生しました。{0}
			RftLogMessage.print(6006020, LogMessage.F_ERROR, CLASS_NAME, sendFileName);
			// 応答フラグ：エラー
			ansCode = RFTId5520.ANS_CODE_ERROR ;
			errDetails = RFTId5520.ErrorDetails.I_O_ERROR;
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5520.ANS_CODE_ERROR;
			errDetails = RFTId5520.ErrorDetails.INTERNAL_ERROR;
		}

		// 応答電文の作成
		// STX
		rftId5520.setSTX() ;
		
		// SEQ
		rftId5520.setSEQ(0) ;
		
		// ID
		rftId5520.setID(RFTId5520.ID) ;
		
		// RFT送信時間
		rftId5520.setRftSendDate(rftId0520.getRftSendDate()) ;
		
		// SERVER送信時間
		rftId5520.setServSendDate() ;
		
		// RFT号機
		rftId5520.setRftNo(rftNo) ;

		// 担当者コード
		rftId5520.setWorkerCode(rftId0520.getWorkerCode()) ;
		
		// 出荷検品出荷先一覧ファイル名
		rftId5520.setTableFileName(sendFileName) ;
		
		// ファイルレコード数
		if (ansCode.equals(RFTId5121.ANS_CODE_NORMAL))
		{
			rftId5520.setFileRecordNumber(workinfo.length) ;
		}
		else
		{
			rftId5520.setFileRecordNumber(0);
		}
		
		// 応答フラグ
		rftId5520.setAnsCode(ansCode) ;
		
		// エラー詳細
		rftId5520.setErrDetails(errDetails);
		
		// ETX
		rftId5520.setETX() ;

		// 応答電文を獲得する。
		rftId5520.getSendMessage(sdt) ;

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------


}
//end of class
