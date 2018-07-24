// $Id: RFTId0121.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
/**
 * 入荷検品出荷先一覧問合せ Id=0121 電文を処理します
 *
 * <p>
 * <table border="1">
 * <CAPTION>Id0121の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>			<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>0121</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>担当者コード</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>作業日</td>			<td>8 byte</td>	<td>YYYYMMDD</td></tr>
 * <tr><td>荷主コード</td>		<td>16 byte</td><td></td></tr>
 * <tr><td>仕入先コード</td>	<td>16 byte</td></tr>
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
  
  public class RFTId0121 extends RecvIdMessage
{
	// Class field --------------------------------------------------
	/**
	 * 担当者コードのオフセット
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	/**
	 * 作業日のオフセット
	 */
	private static final int OFF_WORK_DAY = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	/**
	 * 荷主コードのオフセット
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORK_DAY + LEN_PLAN_DATE;
	
	/**
	 * 仕入先コードのオフセット
	 */
	private static final int OFF_SUPPLIER_CODE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;

	/**
	 * ID番号
	 */
	public static final String ID = "0121";

	// Class variables ----------------------------------------------

	// Class method
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $Date: 2006/08/17 09:34:14 $";
	}
	
	//Constructors --------------------------------------------------
	public RFTId0121 ()
	{
		super();
		offEtx = OFF_SUPPLIER_CODE + LEN_SUPPLIER_CODE;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}	
	
	// Public methods -----------------------------------------------
	/**
	 * 入荷検品仕入先一覧要求電文から担当者コードを取得します。
	 * @return   Worker Code
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	/**
	 * 入荷検品仕入先一覧要求電文から作業日を取得します。
	 * @return   Worker Day
	 */
	public String getWorkDay()
	{
		String workDay = getFromBuffer(OFF_WORK_DAY, LEN_PLAN_DATE);
		return workDay;
	}
	
	/**
	 * 入荷検品仕入先一覧要求電文から荷主コードを取得します。
	 * @return   Consignor Code
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	/**
	 * 入荷検品仕入先一覧要求電文から仕入先コードを取得します。
	 * @return   Supplier Code
	 */
	public String getSupplierCode()
	{
		String supplierCode = getFromBuffer(OFF_SUPPLIER_CODE, LEN_SUPPLIER_CODE);
		return supplierCode.trim();
	}
	
	// Package methods ----------------------------------------------
	
	// Package methods ----------------------------------------------

	// Private methods ----------------------------------------------

}
//end of class
