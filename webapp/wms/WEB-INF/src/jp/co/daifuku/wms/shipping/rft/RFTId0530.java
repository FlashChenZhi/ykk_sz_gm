// $Id: RFTId0530.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 商品単位出荷検品開始要求 ID=0530 電文を処理します。
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id0530の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>				<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No</td>				<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>					<td>4 byte</td>	<td>0340</td></tr>
 * <tr><td>ハンディ送信時間</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td>	<td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>		<td>3 byte</td>	<td></td></tr>
 * <tr><td>作業者コード</td>		<td>4 byte</td>	<td></td></tr>
 * <tr><td>荷主コード</td>			<td>16 byte</td></tr>
 * <tr><td>出荷予定日</td>			<td>8 byte</td></tr>
 * <tr><td>出荷先コード</td>		<td>16 byte</td></tr>
 * <tr><td>バッチNo.</td>			<td>3 byte</td></tr>
 * <tr><td>便No.</td>				<td>3 byte</td></tr>
 * <tr><td>スキャン商品コード1</td>	<td>16 byte</td></tr>
 * <tr><td>スキャン商品コード2</td>	<td>16 byte</td></tr>
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
public class RFTId0530 extends RecvIdMessage
{
	// Class fields --------------------------------------------------
	/**
	 * 作業者コードのオフセットの定義
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;

	/**
	 * 荷主コードのオフセットの定義
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;

	/**
	 * 予定日のオフセットの定義
	 */
	private static final int OFF_SHIPPING_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

	/**
	 * 出荷先コードのオフセットの定義
	 */
	private static final int OFF_CUSTOMER_CODE = OFF_SHIPPING_PLAN_DATE + LEN_PLAN_DATE;

	/**
	 * バッチNo.のオフセットの定義
	 */
	private static final int OFF_BATCH_NO = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;

	/**
	 * 便No.のオフセットの定義
	 */
	private static final int OFF_BATCH2_NO = OFF_BATCH_NO + LEN_BATCH_NO;

	/**
	 * スキャン商品コード1のオフセットの定義
	 */
	private static final int OFF_SCAN_ITEM_CODE1 = OFF_BATCH2_NO + LEN_BATCH2_NO;

	/**
	 * スキャン商品コード1のオフセットの定義
	 */
	private static final int OFF_SCAN_ITEM_CODE2 = OFF_SCAN_ITEM_CODE1 + LEN_ITEM_CODE;

	/**
	 * ID番号
	 */
	public static final String ID = "0530";

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
	public RFTId0530()
	{
		super();

		offEtx = OFF_SCAN_ITEM_CODE2 + LEN_ITEM_CODE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}

	/**
	 * RFTから受信した電文をコンストラクタに渡します。
	 * @param rftId0530 出荷検品JAN問合せ ID=0530 電文
	 */
	public RFTId0530(byte[] rftId0530)
	{
		super();
		setReceiveMessage(rftId0530);
	}

	// Public methods ------------------------------------------------
	/**
	 * 商品単位出荷検品開始要求電文から担当者コードを取得します。
	 * @return		作業者コード
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, +LEN_WORKER_CODE);
		return workerCode.trim();
	}

	/**
	 * 商品単位出荷検品開始要求電文から荷主コードを取得します。
	 * @return		荷主コード
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}

	/**
	 * 商品単位出荷検品開始要求電文から出荷予定日を取得します。
	 * @return		出荷予定日
	 */
	public String getShippingPlanDate()
	{
		String shippingPlanDate = getFromBuffer(OFF_SHIPPING_PLAN_DATE, LEN_PLAN_DATE);
		return shippingPlanDate;
	}

	/**
	 * 商品単位出荷検品開始要求電文から出荷先コードを取得します。
	 * @return		出荷先コード
	 */
	public String getCustomerCode()
	{
		String customerCode = getFromBuffer(OFF_CUSTOMER_CODE, LEN_CUSTOMER_CODE);
		return customerCode.trim();
	}

	/**
	 * 商品単位出荷検品開始要求電文からバッチNo.を取得します。
	 * @return		バッチNo.
	 */
	public String getBatchNo()
	{
		String batchNo = getFromBuffer(OFF_BATCH_NO, LEN_BATCH_NO);
		return batchNo.trim();
	}

	/**
	 * 商品単位出荷検品開始要求電文から便No.を取得します。
	 * @return		便No.
	 */
	public String getBatch2No()
	{
		String batch2No = getFromBuffer(OFF_BATCH2_NO, LEN_BATCH2_NO);
		return batch2No.trim();
	}

	/**
	 * 商品単位出荷検品開始要求電文から商品コードを取得します。
	 * @return		商品コード
	 */
	public String getScanItemCode1()
	{
		String itemCode = getFromBuffer(OFF_SCAN_ITEM_CODE1, LEN_ITEM_CODE);
		return itemCode.trim();
	}

	/**
	 * 商品単位出荷検品開始要求電文から商品コードを取得します。
	 * @return		ITFtoJAN変換された商品コード
	 */
	public String getScanItemCode2()
	{
		String itemCode = getFromBuffer(OFF_SCAN_ITEM_CODE2, LEN_ITEM_CODE);
		return itemCode.trim();
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class
