// $Id: RFTId5110.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 入荷検品仕入先応答 ID=5110」電文を作成します
 *
 * <p>
 * <table border="1">
 * <CAPTION>Id5110の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ No</td>			<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>5110</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>作業者コード</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>荷主コード</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>入荷予定日</td>		<td>8 byte</td>	<td>YYYYMMDD</td></tr>
 * <tr><td>バッチNo</td>		<td>3 byte</td>	<td>未使用</td></tr>
 * <tr><td>便No</td>			<td>3 byte</td>	<td>未使用</td></tr>
 * <tr><td>入力区分</td>		<td>1 byte</td>	<td>1:仕入先</td></tr>
 * <tr><td>入荷作業種別</td>	<td>1 byte</td>	<td>1:TC（出荷先別検品）<BR>2:クロスTC、DC（仕入先別検品）</td></tr>
 * <tr><td>仕入先コード</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>仕入先名</td>		<td>40 byte</td><td></td></tr>
 * <tr><td>残アイテム数</td>	<td>9 byte</td>	<td></td></tr>
 * <tr><td>応答フラグ</td>		<td>1 byte</td>	<td></td></tr>
 * <tr><td>エラー詳細</td>		<td>2 byte</td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */

public class RFTId5110 extends SendIdMessage
{
	// Class field --------------------------------------------------
	/**
	 * 作業者コードのオフセット
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	/**
	 * 荷主コードのオフセット
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	/**
	 * 入荷予定日のオフセット
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	/**
	 * バッチNoのオフセット
	 */
	private static final int OFF_BATCH_NO = OFF_PLAN_DATE + LEN_PLAN_DATE;
	
	/**
	 * 便Noのオフセット
	 */
	private static final int OFF_BATCH2_NO = OFF_BATCH_NO + LEN_BATCH_NO;
	
	/**
	 * 入力区分のオフセット
	 */
	private static final int OFF_INPUT_TYPE = OFF_BATCH2_NO + LEN_BATCH2_NO;
	
	/**
	 * 入荷作業種別のオフセット
	 */
	private static final int OFF_INSTOCK_WORK_TYPE = OFF_INPUT_TYPE + LEN_INPUT_TYPE;
	
	/**
	 * 仕入先コードのオフセット
	 */
	private static final int OFF_SUPPLIER_CODE = OFF_INSTOCK_WORK_TYPE + LEN_INSTOCK_WORK_TYPE;
	
	/**
	 * 仕入先名のオフセット
	 */
	private static final int OFF_SUPPLIER_NAME = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE;
	
	/**
	 * 残アイテム数のオフセット
	 */
	private static final int OFF_REMAINING_ITEM_COUNT = OFF_SUPPLIER_NAME + LEN_SUPPLIER_NAME;
	
	/**
	 * ID番号
	 */
	public static final String ID = "5110";

	/**
	 * ID番号
	 */
	public static final String INPUT_TYPE_SUPPLIER = "1";

	// Class method ------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $Date: 2006/08/17 09:34:14 $";
	}
	
	// Constructors -------------------------------------------------
	/**
	 * コンストラクタ
	 */
	public RFTId5110()
	{
		super();

		offAnsCode = OFF_REMAINING_ITEM_COUNT + LEN_REMAINING_ITEM_COUNT;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;
	}
	
	// Public methods -----------------------------------------------
	/**
	 * 作業者コードを設定します。
	 * @param	workerCode	作業者コード
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}
	
	/**
	 * 荷主コードを設定します。
	 * @param	consignorCode	荷主コード
	 */
	public void setConsignorCode(String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE);
	}
	
	/**
	 * 入荷予定日を設定します。
	 * @param	planDate	入荷予定日
	 */
	public void setPlanDate(String planDate)
	{
		setToBuffer(planDate, OFF_PLAN_DATE);
	}
	
	/**
	 * バッチNo.を設定します。
	 * @param	batchNo		バッチNo.
	 */
	public void setBatchNo(String batchNo)
	{
		setToBuffer(batchNo, OFF_BATCH_NO);
	}
	
	/**
	 * 便No.を設定します。
	 * @param	batch2No	便No.
	 */
	public void setBatch2No(String batch2No)
	{
		setToBuffer(batch2No, OFF_BATCH2_NO);
	}
	
	/**
	 * 入力区分を設定します。
	 * @param	inputType	入力区分
	 */
	public void setInputType(String inputType)
	{
		setToBuffer(inputType, OFF_INPUT_TYPE);
	}
	
	/**
	 * 入荷作業種別を設定します。
	 * @param	instockWorkType	入荷作業種別
	 */
	public void setInstockWorkType(String instockWorkType)
	{
		setToBuffer(instockWorkType, OFF_INSTOCK_WORK_TYPE);
	}
	
	/**
	 * 仕入先コードを設定します。
	 * @param	supplierCode	仕入先コード
	 */
	public void setSupplierCode(String supplierCode)
	{
		setToBuffer(supplierCode, OFF_SUPPLIER_CODE);
	}
	
	/**
	 * 仕入先名を設定します。
	 * @param	supplierName	仕入先名
	 */
	public void setSupplierName(String supplierName)
	{
		setToBuffer(supplierName, OFF_SUPPLIER_NAME);
	}
	
	/**
	 * 残アイテム数を設定します。
	 * @param	remainingItemNumber	残アイテム数
	 */
	public void setRemainingItemCount(int remainingItemNumber)
	{
		setToBufferRight(remainingItemNumber, OFF_REMAINING_ITEM_COUNT, LEN_REMAINING_ITEM_COUNT);
	}
	
	// Package methods ----------------------------------------------
	
	// Protected methods --------------------------------------------
	
	// Private methods ----------------------------------------------

}
//end of class
