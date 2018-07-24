// $Id: RFTId0533.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 出荷先単位出荷検品実績送信 ID=0533 電文を処理します。
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id0533の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No</td>			<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0533</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>作業者コード</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>荷主コード</td>		<td>16 byte</td></tr>
 * <tr><td>予定日</td>			<td>8 byte</td>	<TD>YYYYMMDD</TD></tr>
 * <tr><td>バッチNo</td>		<td>3 byte</td></tr>
 * <tr><td>便No</td>			<td>3 byte</td></tr>
 * <tr><td>出荷先コード</td>	<td>16 byte</td></tr>
 * <tr><td>出荷先名</td>		<td>40 byte</td></tr>
 * <tr><td>作業時間</td>		<td>5 byte</td></tr>
 * <tr><td>誤検回数</td>		<td>5 byte</td>	<td>未使用</td></tr>
 * <tr><td>完了フラグ</td>		<td>1 byte</td>	<TD>0:正常<BR>1:欠品<BR>2:保留<BR>9:キャンセル</TD></tr>
 * <tr><td>実績ファイル名称</td><td>30 byte</td><TD>完了区分がキャンセルの場合は使用しない</TD></tr>
 * <tr><td>ファイルレコード数</td>	<td>6 byte</td>	<td></td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */
public class RFTId0533 extends RecvIdMessage
{
	// Class fields --------------------------------------------------
	/**
	 * 担当者コードのオフセットの定義
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	/**
	 * 荷主コードのオフセットの定義
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	/**
	 * 出荷予定日のオフセットの定義
	 */
	private static final int OFF_SHIPPING_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	/**
	 * バッチNo.のオフセットの定義
	 */
	private static final int OFF_BATCH_NO = OFF_SHIPPING_PLAN_DATE + LEN_PLAN_DATE ;
	
	/**
	 * 便No.のオフセットの定義
	 */
	private static final int OFF_BATCH2_NO = OFF_BATCH_NO + LEN_BATCH_NO ;
	
	/**
	 * 出荷先コードのオフセットの定義
	 */
	private static final int OFF_CUSTOMER_CODE = OFF_BATCH2_NO + LEN_BATCH2_NO ;
	
	/**
	 * 出荷先名のオフセットの定義
	 */
	private static final int OFF_CUSTOMER_NAME = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE ;
	
	/**
	 * 作業時間のオフセットの定義
	 */
	private static final int OFF_WORK_SECONDS = OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;

	/**
	 * 誤検回数のオフセットの定義
	 */
	private static final int OFF_INSPECTION_ERR_COUNT = OFF_WORK_SECONDS + LEN_WORK_TIME;

	/**
	 * 完了フラグのオフセットの定義
	 */
	private static final int OFF_COMPLETION_FLAG = OFF_INSPECTION_ERR_COUNT + LEN_INSPECTION_ERR_COUNT;
	
	/**
	 * 実績ファイル名称のオフセットの定義
	 */
	private static final int OFF_RESULT_FILE_NAME = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
	
	/**
	 * ファイルレコード数のオフセットの定義
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_RESULT_FILE_NAME + LEN_FILE_NAME;

	/**
	 * ID番号
	 */
	public static final String ID = "0533";
	
	/**
	 * 完了区分の正常を表すフィールド
	 */
	public static final String COMPLETION_FLAG_NORMAL = "0" ;
	
	/**
	 * 完了区分の欠品を表すフィールド
	 */
	public static final String COMPLETION_FLAG_LACK = "1" ;
	
	/**
	 * 完了区分の保留を表すフィールド
	 */
	public static final String COMPLETION_FLAG_RESERVE = "2" ;
	
	/**
	 * 完了区分のキャンセルを表すフィールド
	 */
	public static final String COMPLETION_FLAG_CANCEL = "9" ;


	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:30 $";
	}

	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ
	 */
	public RFTId0533 ()
	{
		super() ;

		offEtx = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	/**
	 * RFTから受信した電文をコンストラクタに渡します。
	 * @param rftId0533 出荷先単位出荷検品実績送信 ID=0533 電文
	 */
	public RFTId0533 (byte[] rftId0533)
	{
		super() ;

		setReceiveMessage(rftId0533) ;
	}

	// Public methods ------------------------------------------------
	/**
	 * 出荷先単位出荷検品実績送信電文から担当者コードを取得します。
	 * @return		担当者コード
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	/**
	 * 出荷先単位出荷検品実績送信電文から荷主コードを取得します。
	 * @return		荷主コード
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	/**
	 * 出荷先単位出荷検品実績送信電文から出荷予定日を取得します。
	 * @return		出荷予定日
	 */
	public String getShippingPlanDate ()
	{
		String shippingPlanDate = getFromBuffer(OFF_SHIPPING_PLAN_DATE, LEN_PLAN_DATE) ;
		return shippingPlanDate;
	}
	
	/**
	 * 出荷先単位出荷検品実績送信電文からバッチNo.を取得します。
	 * @return		バッチNo.
	 */
	public String getBatchNo ()
	{
		String batchNo = getFromBuffer(OFF_BATCH_NO, LEN_BATCH_NO) ;
		return batchNo.trim();
	}
	
	/**
	 * 出荷先単位出荷検品実績送信電文から便No.を取得します。
	 * @return		便No.
	 */
	public String getBatch2No ()
	{
		String batch2No = getFromBuffer(OFF_BATCH2_NO, LEN_BATCH2_NO) ;
		return batch2No.trim();
	}
	
	/**
	 * 出荷先単位出荷検品実績送信電文から出荷先コードを取得します。
	 * @return		出荷先コード
	 */
	public String getCustomerCode ()
	{
		String customerCode = getFromBuffer(OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE) ;
		return customerCode.trim();
	}
	
	/**
	 * 出荷先単位出荷検品実績送信電文から出荷先名を取得します。
	 * @return		出荷先名
	 */
	public String getCustomerName ()
	{
		String customerName = getFromBuffer(OFF_CUSTOMER_NAME, LEN_CUSTOMER_NAME) ;
		return customerName.trim();
	}
	
	/**
	 * 出荷先単位出荷検品実績送信電文から作業時間を取得します。
	 * @return		作業時間
	 */
	public int getWorkSeconds ()
	{
		int workSeconds = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSeconds;
	}

	/**
	 * 出荷先単位出荷検品実績送信電文から誤検回数を取得します。
	 * @return		誤検回数
	 */
	public int getInspectionErrCount ()
	{
		int count = getIntFromBuffer(OFF_INSPECTION_ERR_COUNT, LEN_INSPECTION_ERR_COUNT);
		return count;
	}

	/**
	 * 出荷先単位出荷検品実績送信電文から完了区分を取得します。
	 * @return		完了区分
	 * 				0:完了
	 * 				1:欠品
	 * 				2:保留
	 * 				9:キャンセル
	 */
	public String getCompletionFlag ()
	{
		String completionFlag = getFromBuffer(OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG) ;
		return completionFlag;
	}
	
	/**
	 * 出荷先単位出荷検品実績送信電文から実績ファイル名称を取得します。
	 * @return		実績ファイル名称
	 */
	public String getResultFileName ()
	{
		String resultFileName = getFromBuffer(OFF_RESULT_FILE_NAME, LEN_FILE_NAME) ;
		return resultFileName.trim();
	}
	
	/**
	 * 出荷先単位出荷検品実績送信電文からファイルレコード数を取得します。
	 * @return		ファイルレコード数
	 */
	public int getFileRecordNumber ()
	{
		int records = getIntFromBuffer(OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
		return records;
	}
	

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------
}
//end of class

