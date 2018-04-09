// $Id: Id0531Process.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.IdProcess;
/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * 商品単位出荷検品実績送信(ID0531)処理を行うためのクラスです。<BR>
 * <CODE>IdProcess</CODE>クラスを継承し、必要な処理を実装します。<BR>
 * Id0531Operateクラスの提供する機能を使用し、作業情報の該当する検品データの更新を行い、
 * RFTに送信する応答電文を生成します。<BR>
 * <BR>
 * 商品単位出荷検品J実績送信処理(<CODE>processReceivedId()</CODE>メソッド)<BR>
 * <BR>
 * <DIR>
 *   受信データから、必要な情報を取得します。<BR>
 *   完了フラグが保留か確定の場合は、電文からから必要なデータを取得します。<BR>
 *   Id0531Operateクラスの機能で、キャンセル、保留又は確定の処理を呼び出し、応答フラグを取得します。<BR>
 *   応答フラグが0：正常の場合はRFT作業情報の電文項目をNULLに更新します。<BR>
 *   [更新条件]
 *   <UL><LI>RFTNo</LI></UL>
 *   [更新内容]
 *   <UL><LI>応答電文：NULL</LI>
 *       <LI>最終更新日時:システム日時</LI>
 *       <LI>最終更新処理名：ID0531</LI>
 *   </UL>
 *   送信電文を作成し、送信バッファに送信するテキストをセットします。<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>T.Konishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class Id0531Process extends IdProcess
{
	// Class fields----------------------------------------------------

	// Class variables -----------------------------------------------
	private static final String CLASS_NAME = "Id0531Process";
	
	private static final String PROCESS_NAME = "ID0531";
	
	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $";
	}

	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ。
	 */
	public Id0531Process()
	{
		super();
	}
	
	/**
	 * DBConnection情報をコンストラクタに渡します。
	 * @param conn DBConnection情報
	 */
	public Id0531Process(Connection conn)
	{
		super() ;
		wConn = conn ;
	}

	// Public methods ------------------------------------------------
	/**
	 * 商品単位出荷検品実績送信(Id0531)の処理を行います。<BR>
	 * 受信した電文をバイト列で受け取り、それに対する応答(ID5531)をバイト列で作成します。<BR>
	 * 完了区分によって、キャンセル、保留又は確定の処理を呼び出し、結果のテキストを作成します。<BR>
	 * 応答フラグが0：正常の場合はRFT作業情報の電文項目をNULLに更新します。<BR>
	 *   [更新条件]
	 *   <UL><LI>RFTNo</LI></UL>
	 *   [更新内容]
	 *   <UL><LI>応答電文：NULL</LI>
	 *       <LI>最終更新日時</LI>
	 *       <LI>最終更新処理名</LI>
	 *   </UL>
	 * @param  rdt  受信バッファ
	 * @param  sdt  送信バッファ
	 * @throws Exception 全ての例外を報告します。
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		// 受信電文解析用のインスタンスを生成
		RFTId0531 rftId0531 = null;

		// 送信電文作成用のインスタンスを生成
		RFTId5531 rftId5531 = null;
		
		// 作業情報のインスタンスを生成
		WorkingInformation workinfo = new WorkingInformation();
		try
		{
			// 受信電文解析用のインスタンスを生成
			rftId0531 = (RFTId0531) PackageManager.getObject("RFTId0531");
			rftId0531.setReceiveMessage(rdt);

			// 送信電文作成用のインスタンスを生成
			rftId5531 = (RFTId5531) PackageManager.getObject("RFTId5531");
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*531", e.getMessage());
			throw e;
		}

		// 受信電文からRFT号機を取得
		String rftNo = rftId0531.getRftNo();

		// 受信電文から担当者コードを取得
		String workerCode = rftId0531.getWorkerCode();

		// 受信電文から荷主コードを取得
		String consignorCode = rftId0531.getConsignorCode();

		// 受信電文から出荷予定日を取得
		String planDate = rftId0531.getShippingPlanDate();

		// 受信電文から出荷先コードを取得
		String customerCode = rftId0531.getCustomerCode();

		// 受信電文からJANコードを取得
		String janCode = rftId0531.getJanCode();

		// 受信電文から完了区分を取得
		String completionFlag = rftId0531.getCompletionFlag();

		// 応答フラグを保持する変数
		String ansCode = RFTId5531.ANS_CODE_NORMAL;
		
		// エラー詳細を保持する変数
		String errDetails = RFTId5531.ErrorDetails.NORMAL;

		// 総アイテム数を保持する変数
		int totalItemCount = 0;

		// 残アイテム数を保持する変数
		int remainingItemCount = 0;
	
		Id0531Operate id0531Operate = null;
		
		try
		{
			// 受信電文から作業情報インスタンスを作成
			workinfo.setConsignorCode(rftId0531.getConsignorCode());
			workinfo.setConsignorName(rftId0531.getConsignorName());
			workinfo.setPlanDate(rftId0531.getShippingPlanDate());
			workinfo.setCustomerCode(rftId0531.getCustomerCode());
			workinfo.setCustomerName1(rftId0531.getCustomerName());
			workinfo.setCollectJobNo(rftId0531.getItemId());
			workinfo.setItemCode(rftId0531.getJanCode());
			workinfo.setBundleItf(rftId0531.getBundleItf());
			workinfo.setItf(rftId0531.getItf());
			workinfo.setResultUseByDate(rftId0531.getUseByDate());
			workinfo.setPlanEnableQty(rftId0531.getShippingPlanQty());
			workinfo.setResultQty(rftId0531.getShippingResultQty());
			workinfo.setWorkTime(rftId0531.getWorkSeconds());
			
			if(!completionFlag.equals(RFTId0531.COMPLETION_FLAG_CANCEL) && DisplayText.isPatternMatching(workinfo.getResultUseByDate()))
			{
				throw new InvalidDefineException("USE_BY_DATE[" + workinfo.getResultUseByDate() +"]");
			}
			
			// Id0531Operateのインスタンスを生成
			id0531Operate = (Id0531Operate) PackageManager.getObject("Id0531Operate");
			id0531Operate.setConnection(wConn);

			ansCode = id0531Operate.doComplete(
					consignorCode,
					planDate,
					customerCode,
					janCode,
					rftNo,
					workerCode,
					completionFlag,
					workinfo,
					rftId0531.getWorkSeconds(),
					rftId0531.getInspectionErrCount());
			
			if (ansCode.equals(RFTId5531.ANS_CODE_NORMAL))
			{
			    // 応答フラグが正常の場合は作業中データを削除する
			    RFTId5531.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
			    wConn.commit();
			}
		}
		catch (IllegalAccessException e)
		{
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "Id0531Operate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5531.ANS_CODE_ERROR;
			errDetails = RFTId5531.ErrorDetails.INSTACIATE_ERROR;
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
			ansCode = RFTId5531.ANS_CODE_ERROR;
			errDetails = RFTId5531.ErrorDetails.DB_ACCESS_ERROR;
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
			ansCode = RFTId5531.ANS_CODE_ERROR;
			errDetails = RFTId5531.ErrorDetails.PARAMETER_ERROR;
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
			ansCode = RFTId5531.ANS_CODE_ERROR;
			errDetails = RFTId5531.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch(NumberFormatException e)
		{
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			errDetails = RFTId5531.ErrorDetails.PARAMETER_ERROR;
			ansCode = RFTId5531.ANS_CODE_ERROR;
		}
		catch(Exception e)
		{
			RftLogMessage.printStackTrace(6006001, LogMessage.F_ERROR, CLASS_NAME, e);
			// 応答フラグ：エラー
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}

			errDetails = RFTId5531.ErrorDetails.INTERNAL_ERROR;
			ansCode = RFTId5531.ANS_CODE_ERROR;
			
		}
		
		// 応答電文の作成
		// STX
		rftId5531.setSTX();
		// SEQ
		rftId5531.setSEQ(0);
		// ID
		rftId5531.setID(RFTId5531.ID);
		// RFT送信時間
		rftId5531.setRftSendDate(rftId0531.getRftSendDate());
		// SERVER送信時間
		rftId5531.setServSendDate();
		// RFT号機
		rftId5531.setRftNo(rftNo);
		// 担当者コード
		rftId5531.setWorkerCode(workerCode);
		// 総アイテム数
		rftId5531.setTotalItemCount(totalItemCount);
		// 残アイテム数
		rftId5531.setRemainingItemCount(remainingItemCount);
		// 応答フラグ
		rftId5531.setAnsCode(ansCode);
		// エラー詳細
		if (ansCode.equals(RFTId5531.ANS_CODE_ERROR)
				&& errDetails.equals(RFTId5531.ErrorDetails.NORMAL))
		{
			errDetails = id0531Operate.getErrorDetails();
		}
		rftId5531.setErrDetails(errDetails);
		// ETX
		rftId5531.setETX();

		// 応答電文を獲得する。
		rftId5531.getSendMessage(sdt);

	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

