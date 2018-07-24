// $Id: RFTId5510.java,v 1.1.1.1 2006/08/17 09:34:30 mori Exp $
package jp.co.daifuku.wms.shipping.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * ソケット通信での「出荷先応答 ID = 5510」電文を作成します。
 *
 * <p>
 * <table border="1">
 * <CAPTION>Id5510の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>			<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>5510</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>担当者コード</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>荷主コード</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>出荷予定日</td>		<td>8 byte</td>	<td>YYYYMMDD</td></tr>
 * <tr><td>バッチNo</td>		<td>3 byte</td>	<td>未使用</td></tr>
 * <tr><td>便No</td>			<td>3 byte</td>	<td>未使用</td></tr>
 * <tr><td>出荷先コード</td>    <td>16 byte</td><td></td></tr>
 * <tr><td>出荷先名</td>		<td>40 byte</td><td></td></tr>
 * <tr><td>残アイテム数</td>	<td>9 byte</td>	<td></td></tr>
 * <tr><td>応答フラグ</td>		<td>1 byte</td>	<td>0:正常 8:該当データ無し 9:エラー</td></tr>
 * <tr><td>エラー詳細</td>		<td>2 byte</td>	<td></td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:30 $
 * @author  $Author: mori $
 */

public class RFTId5510 extends SendIdMessage
{
	// Class field --------------------------------------------------
	/**
	 * 担当者コードのオフセット
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	/**
	 * 荷主コードのオフセット
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	/**
	 * 出荷予定日のオフセット
	 */
	private static final int OFF_SHIPPING_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	/**
	 * バッチNoのオフセット
	 */
	private static final int OFF_BATCH_NO = OFF_SHIPPING_PLAN_DATE + LEN_PLAN_DATE;
	
	/**
	 * 便Noのオフセット
	 */
	private static final int OFF_BATCH2_NO = OFF_BATCH_NO + LEN_BATCH_NO;
	
	/**
	 * 出荷先コードのオフセット
	 */
	private static final int OFF_CUSTOMER_CODE = OFF_BATCH2_NO + LEN_BATCH2_NO;
	
	/**
	 * 出荷先名のオフセット
	 */
	private static final int OFF_CUSTOMER_NAME = OFF_CUSTOMER_CODE + LEN_CUSTOMER_CODE;
	
	/**
	 * 残アイテム数のオフセット
	 */
	private static final int OFF_REMAINING_ITEM_COUNT = OFF_CUSTOMER_NAME + LEN_CUSTOMER_NAME;

	/**
	 * ID番号
	 */
	public static final String ID = "5510";
	
	// Class method ------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $Date: 2006/08/17 09:34:30 $";
	}
	
	// Constructors -------------------------------------------------
	/**
	 * コンストラクタ
	 */
	public RFTId5510 ()
	{
		super();
		offAnsCode = OFF_REMAINING_ITEM_COUNT + LEN_REMAINING_ITEM_COUNT;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	
	// Public methods -----------------------------------------------
	/**
	 * 担当者コードを設定します。
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}
	
	/**
	 * 荷主コードを設定します。
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
	 */
	public void setBatchNo(String batchNo)
	{
		setToBuffer(batchNo, OFF_BATCH_NO);
	}
	
	/**
	 * 便No.を設定します。
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
	 * 出荷先名を設定します。
	 * @param  customerName  出荷先名
	 */
	public void setCustomerName(String customerName)
	{
		setToBuffer(customerName, OFF_CUSTOMER_NAME);
	}

	/**
	 * 残アイテム数を設定します。
	 * @param  remainingItemCount  残アイテム数
	 */
	public void setRemainingItemCount(int remainingItemCount)
	{
		setToBufferRight(remainingItemCount, OFF_REMAINING_ITEM_COUNT,LEN_REMAINING_ITEM_COUNT);
	}

	// Package methods ----------------------------------------------

	// Protected methods --------------------------------------------

	// Private methods ----------------------------------------------

}
//end of class
