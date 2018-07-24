// $Id: RFTId5532.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 出荷先別出荷検品データ応答(StorageJANResponse) ID=5532 電文を処理します。
 * 
 * <p>
 * <table border="1">
 * <CAPTION>ID5532の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>				<TH>長さ</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td></tr>
 * <tr><td>ハンディ送信時間</td>	<td>6 byte</td></tr>
 * <tr><td>サーバ送信時間</td>		<td>6 byte</td></tr>
 * <tr><td>ハンディ号機No.</td>		<td>3 byte</td></tr>
 * <tr><td>作業者コード</td>		<td>4 byte</td></tr>
 * <tr><td>荷主コード</td>			<td>16 byte</td></tr>
 * <tr><td>予定日</td>				<td>8 byte</td></tr>
 * <tr><td>バッチNo.</td>			<td>3 byte</td></tr>
 * <tr><td>便No.</td>				<td>3 byte</td></tr>
 * <tr><td>出荷先コード</td>		<td>16 byte</td></tr>
 * <tr><td>荷主名</td>				<td>40 byte</td></tr>
 * <tr><td>出荷先名</td>			<td>40 byte</td></tr>
 * <tr><td>応答ファイル名</td>		<td>30 byte</td></tr>
 * <tr><td>ファイルレコード数</td>	<td>6 byte</td></tr>
 * <tr><td>応答フラグ</td>			<td>1 byte</td></tr>
 * <tr><td>エラー詳細</td>			<td>2 byte</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td></tr>
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
public class RFTId5532 extends SendIdMessage
{
	// Class fields --------------------------------------------------
	/**
	 * 担当者コードのオフセットの定義
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	/**
	 * 荷主コードのオフセットの定義
	 */
	private static final int OFF_CONSIGNOR_CODE =
		OFF_WORKER_CODE + LEN_WORKER_CODE;

	/**
	 * 出荷予定日のオフセットの定義
	 */
	private static final int OFF_SHIPPING_PLAN_DATE =
		OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

	/**
	 * バッチNo.のオフセットの定義
	 */
	private static final int OFF_BATCH_NO =
		OFF_SHIPPING_PLAN_DATE + LEN_PLAN_DATE;

	/**
	 * 便No.のオフセットの定義
	 */
	private static final int OFF_BATCH2_NO = OFF_BATCH_NO + LEN_BATCH_NO;

	/**
	 * 出荷先コードのオフセットの定義
	 */
	private static final int OFF_CUSTOMER_CODE = OFF_BATCH2_NO + LEN_BATCH2_NO;

	/**
	 * 荷主名のオフセットの定義
	 */
	private static final int OFF_CONSIGNOR_NAME =
		OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;

	/**
	 * 出荷先名のオフセットの定義
	 */
	private static final int OFF_CUSTOMER_NAME =
		OFF_CONSIGNOR_NAME + LEN_CONSIGNOR_NAME;

	/**
	 * 作業ファイル名のオフセットの定義
	 */
	private static final int OFF_ANS_FILE_NAME =
		OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;

	/**
	 * ファイルレコード数のオフセットの定義
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_ANS_FILE_NAME + LEN_FILE_NAME;

	/**
	 * ID番号
	 */
	public static final String ID = "5532";

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
	public RFTId5532()
	{
		super();

		offAnsCode = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}

	// Public methods ------------------------------------------------
	/**
	 * 担当者コードを設定します。
	 * @param  workerCode  担当者コード
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}

	/**
	 * 荷主コードを設定します。
	 * @param  consignorCode  荷主コード
	 */
	public void setConsignorCode(String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE);
	}

	/**
	 * 出荷予定日を設定します。
	 * @param  shippingPlanDate  出荷予定日
	 */
	public void setShippingPlanDate(String shippingPlanDate)
	{
		setToBuffer(shippingPlanDate, OFF_SHIPPING_PLAN_DATE);
	}

	/**
	 * バッチNo.を設定します。
	 * @param  batchNo  バッチNo.
	 */
	public void setBatchNo(String batchNo)
	{
		setToBuffer(batchNo, OFF_BATCH_NO);
	}

	/**
	 * 便No.を設定します。
	 * @param  batch2No  便No.
	 */
	public void setBatch2No(String batch2No)
	{
		setToBuffer(batch2No, OFF_BATCH2_NO);
	}

	/**
	 * 出荷先コードを設定します。
	 * @param  customerCode  出荷先コード
	 */
	public void setCustomerCode(String customerCode)
	{
		setToBuffer(customerCode, OFF_CUSTOMER_CODE);
	}

	/**
	 * 荷主名を設定します。
	 * @param  consignorName  荷主名
	 */
	public void setConsignorName(String consignorName)
	{
		setToBuffer(consignorName, OFF_CONSIGNOR_NAME);
	}

	/**
	 * 出荷先名を設定します。
	 * @param  customerName  出荷先名
	 */
	public void setCustomerName(String customerName)
	{
		setToBuffer(customerName, OFF_CUSTOMER_NAME);
	}

	/**
	 * 応答ファイル名を設定します。
	 * @param  ansFileName  応答ファイル名
	 */
	public void setAnsFileName(String ansFileName)
	{
		setToBuffer(ansFileName, OFF_ANS_FILE_NAME);
	}

	/**
	 * ファイルレコード数を設定します。
	 * @param  records  ファイルレコード数
	 */
	public void setFileRecordNumber (int records)
	{
		setToBufferRight(records, OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
	}

	/**
	 * 出荷先単位出荷検品開始応答電文から荷主コードを取得します。
	 * @return		荷主コード
	 */
	public String getConsignorCode()
	{
		String consignorCode =
			getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}

	/**
	 * 出荷先単位出荷検品開始応答電文から出荷予定日を取得します。
	 * @return		出荷予定日
	 */
	public String getShippingPlanDate()
	{
		String shippingPlanDate =
			getFromBuffer(OFF_SHIPPING_PLAN_DATE, LEN_PLAN_DATE);
		return shippingPlanDate;
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
