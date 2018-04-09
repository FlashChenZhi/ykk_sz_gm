// $Id: RFTId0110.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 入荷検品仕入先問合せ ID=0110 電文を処理します。
 *
 * <p>
 * <table border="1">
 * <CAPTION>Id0110の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>			<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0110</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>担当者コード</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>荷主コード</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>入荷予定日</td>		<td>8 byte</td>	<td>YYYYMMDD</td></tr>
 * <tr><td>バッチNo</td>		<td>3 byte</td>	<td>未使用</td></tr>
 * <tr><td>便No</td>			<td>3 byte</td>	<td>未使用</td></tr>
 * <tr><td>入力区分</td>		<td>1 byte</td> <td>1:仕入先</td></tr>
 * <tr><td>入荷作業種別</td>	<td>1 byte</td>	<td>1:TC（出荷先別検品）<BR>2:クロスTC、DC（仕入先別検品）</td></tr>
 * <tr><td>仕入先コード</td>	<td>16 byte</td><td></td></tr>
 * <tr><td>ETX</td>				<td>1 byte</td>	<td>0x03</td></tr>
 * </table>
 * </p>
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/02/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:14 $
 * @author  $Author: mori $
 */
 
public class RFTId0110 extends RecvIdMessage
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
	 * ID番号
	 */
	public static final String ID = "0110";

	// Class variables ----------------------------------------------
	
	// Class method -------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $Date: 2006/08/17 09:34:14 $";
	}
	
	//Constructors --------------------------------------------------
	/**
	 * インスタンスを生成します。
	 */
	public RFTId0110 ()
	{
		super() ;

		offEtx = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	// Public methods -----------------------------------------------
	/**
	 * 入荷検品仕入先問合せ電文から担当者コードを取得します。
	 * @return   担当者コード
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	/**
	 * 入荷検品仕入先問合せ電文から荷主コードを取得します。
	 * @return   荷主コード
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	/**
	 * 入荷検品仕入先問合せ電文から入荷予定日を取得します。
	 * @return   入荷予定日
	 */
	public String getPlanDate()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate;
	}
	
	/**
	 * 入荷検品仕入先問合せ電文からバッチNoを取得します。
	 * @return   バッチNo
	 */
	public String getBatchNo()
	{
		String batchNo = getFromBuffer(OFF_BATCH_NO, LEN_BATCH_NO);
		return batchNo.trim();
	}
	
	/**
	 * 入荷検品仕入先問合せ電文から便Noを取得します。
	 * @return   便No
	 */
	public String getBatch2No()
	{
		String batch2No = getFromBuffer(OFF_BATCH2_NO, LEN_BATCH2_NO);
		return batch2No.trim();
	}
	
	/**
	 * 入荷検品仕入先問合せ電文から入力区分を取得します。
	 * @return   入力区分
	 */
	public String getInputType()
	{
		String inputType = getFromBuffer(OFF_INPUT_TYPE, LEN_INPUT_TYPE);
		return inputType.trim();
	}
	
	/**
	 * 入荷検品仕入先問合せ電文から入荷作業種別を取得します。
	 * @return   入荷作業種別
	 */
	public String getInstockWorkType()
	{
		String instockWorkType = getFromBuffer(OFF_INSTOCK_WORK_TYPE, LEN_INSTOCK_WORK_TYPE);
		return instockWorkType.trim();
	}
	
	/**
	 * 入荷検品仕入先問合せ電文から仕入先コードを取得します。
	 * @return   仕入先／発注番号
	 */
	public String getSupplierCode()
	{
		String supplierCode = getFromBuffer(OFF_SUPPLIER_CODE, LEN_SUPPLIER_CODE);
		return supplierCode.trim();
	}
	
	// Package methods ----------------------------------------------
	
	// Protected methods --------------------------------------------
	
	// Private methods ----------------------------------------------
	
}
//end of class
