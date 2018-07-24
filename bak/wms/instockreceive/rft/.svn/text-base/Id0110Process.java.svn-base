// $Id: Id0110Process.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.AnswerCode;
import jp.co.daifuku.wms.base.rft.IdProcess;

/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * 入荷検品仕入先問合せ(ID0110)処理を行うためのクラスです。<BR>
 * <CODE>IdProcess</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * 受信電文から検索条件を取得し仕入先の検索を行い、応答電文を生成します。<BR>
 * 検索処理はBaseOperateクラスを使用します。<BR>
 * <BR>
 * 入荷検品仕入先問合せ処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信電文をパラメータとして受取り、応答電文を生成します。<BR>
 *   受信電文より荷主コード、入荷予定日、TC/DC区分を検索条件として仕入先名称を取得します。<BR>
 *   仕入先名称が取得できた場合は、正常応答電文を生成します。<BR>
 *   仕入先名称が取得できない場合は原因を判別し、異常応答電文を生成します。<BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
public class Id0110Process extends IdProcess
{
	// Class fields----------------------------------------------------
	/**
	 * クラス名を表すフィールド
	 */
	private static final String CLASS_NAME = "Id0110Process";

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
	 * 入荷検品仕入先問合せ処理を行います。
	 * 受信した条件から仕入先名を検索し、検索結果を仕入先名にセットします。
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0110 rftId0110 = null;
		// 送信電文作成用のインスタンスを生成
		RFTId5110 rftId5110 = null;
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0110 = (RFTId0110) PackageManager.getObject("RFTId0110");
			rftId0110.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5110 = (RFTId5110) PackageManager.getObject("RFTId5110");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*110", e.getMessage());
			throw e;
		}

		// 応答フラグを保持する変数
		String ansCode = RFTId5110.ANS_CODE_NORMAL;

		// 仕入先名を保持する変数
		String name = "";

		// 受信電文からRFT号機No.を取得
		String rftNo = rftId0110.getRftNo();

		// 受信電文から作業者コードを取得
		String workerCode = rftId0110.getWorkerCode();
		
		// 受信電文から作業予定日を取得
		String planDate = rftId0110.getPlanDate();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0110.getConsignorCode();

		// 受信電文から仕入先コードを取得
		String supplierCode = rftId0110.getSupplierCode();

		// 受信電文から入荷作業種別を取得
		String workType = rftId0110.getInstockWorkType();

		int remainingCount = 0;

		String errDetails = AnswerCode.ErrorDetails.NORMAL;

		String className = null;
		
		try
		{
			if(DisplayText.isPatternMatching(supplierCode))
			{
				throw new NotFoundException(RFTId5110.ANS_CODE_NULL);
			}
			// BaseOperateのインスタンスを生成
		    className = "Id0110Operate";
			Id0110Operate id0011Operate = (Id0110Operate) PackageManager.getObject(className);
			id0011Operate.setConnection(wConn);

			// 作業情報より仕入先名称を取得する
			name = id0011Operate.getSupplierName(
			        planDate,
			        consignorCode,
			        supplierCode,
			        workType,
			        workerCode,
			        rftNo);
		}
		// WorkerOperateインスタンスから情報を取得できなかった場合
		catch (NotFoundException e)
		{
			// 応答フラグ：該当作業者無し
			ansCode = RFTId5110.ANS_CODE_NULL;
		}
		// その他のエラーがあった場合
		catch (ReadWriteException e)
		{
			// 6006002=データベースエラーが発生しました。{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5110.ANS_CODE_ERROR;
			errDetails = RFTId5110.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (IllegalAccessException e)
		{
			// インスタンス生成に失敗
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, className, e.getMessage());
			// 応答フラグ：エラー
			ansCode = RFTId5110.ANS_CODE_ERROR;
			errDetails = RFTId5110.ErrorDetails.INSTACIATE_ERROR;
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			ansCode = RFTId5110.ANS_CODE_ERROR;
			errDetails = RFTId5110.ErrorDetails.INTERNAL_ERROR;
		}

		// 応答電文の作成
		// STX
		rftId5110.setSTX();
		// SEQ
		rftId5110.setSEQ(0);
		// ID
		rftId5110.setID(RFTId5110.ID);
		// RFT送信時間
		rftId5110.setRftSendDate(rftId0110.getRftSendDate());
		// SERVER送信時間
		rftId5110.setServSendDate();
		// RFT号機
		rftId5110.setRftNo(rftNo);
		// 作業者コード
		rftId5110.setWorkerCode(workerCode);
		// 荷主コード
		rftId5110.setConsignorCode(consignorCode);
		// 入荷予定日
		rftId5110.setPlanDate(planDate);
		// 入力区分
		rftId5110.setInputType(RFTId5110.INPUT_TYPE_SUPPLIER);
		// 入力作業種別
		rftId5110.setInstockWorkType(workType);
		// 仕入先コード
		rftId5110.setSupplierCode(supplierCode);
		// 仕入先名
		rftId5110.setSupplierName(name);
		// 残アイテム数
		rftId5110.setRemainingItemCount(remainingCount);
		// 応答フラグ
		rftId5110.setAnsCode(ansCode);
		// エラー詳細
		rftId5110.setErrDetails(errDetails);

		// ETX
		rftId5110.setETX();

		// 応答電文を獲得する。
		rftId5110.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
