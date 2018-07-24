// $Id: RFTId5120.java,v 1.1.1.1 2006/08/17 09:34:14 mori Exp $
package jp.co.daifuku.wms.instockreceive.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 * 
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 入荷検品仕入先一覧応答 ID=5120 電文を作成します
 *
 * <p>
 * <table border="1">
 * <CAPTION>Id5120の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				<td>1 byte</td>	<td>0x02</td></tr>
 * <tr><td>SEQ NO</td>			<td>4 byte</td>	<td>未使用</td></tr>
 * <tr><td>ID</td>				<td>4 byte</td>	<td>5120</td></tr>
 * <tr><td>ハンディ送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td><td>6 byte</td>	<td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	<td>3 byte</td>	<td></td></tr>
 * <tr><td>担当者コード</td>	<td>4 byte</td>	<td></td></tr>
 * <tr><td>一覧ファイル名</td>	<td>30 byte</td></tr>
 * <tr><td>ファイルレコード数</td><td>6 byte</td></tr>
 * <tr><td>応答フラグ</td>		<td>1 byte</td>
 *     <TD>0:正常<BR>8:該当データ無し<BR>9:エラー</TD></tr>
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

public class RFTId5120 extends SendIdMessage
{
	// Class field --------------------------------------------------
	/**
	 * 担当者コードのオフセット
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER;
	
	/**
	 * 一覧ファイル名のオフセット
	 */
	private static final int OFF_TABLE_FILE_NAME = OFF_WORKER_CODE + LEN_WORKER_CODE;
	
	/**
	 * ファイルレコード数のオフセット
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_TABLE_FILE_NAME + LEN_FILE_NAME;

	/**
	 * ID番号
	 */
	public static final String ID = "5120";
	
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
	 * インスタンスを生成します。
	 */
	public RFTId5120 ()
	{
		super();

		offAnsCode = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		offErrorDetails = offAnsCode + LEN_ANS_CODE;
		offEtx = offErrorDetails + LEN_ERROR_DETAILS;
		length = offEtx + LEN_ETX;

	}	
	// Public methods -----------------------------------------------
	/**
	 * 担当者コードを設定します。
	 * @param  workerCode  担当者コード
	 */
	public void setWorkerCode(String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE);
	}
	
	/**
	 * 一覧ファイル名を設定します。
	 * @param  tableFileName  一覧ファイル名
	 */
	public void setTableFileName(String tableFileName)
	{
		setToBuffer(tableFileName, OFF_TABLE_FILE_NAME);
	}
		
	/**
	 * ファイルレコード数を設定します。
	 * @param  fileRecordNumber  ファイルレコード数
	 */
	public void setFileRecordNumber(int fileRecordNumber)
	{
		setToBufferRight(fileRecordNumber, OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
	}
	
	// Package methods ----------------------------------------------
	
	// Protected methods --------------------------------------------
	
	// Private methods ----------------------------------------------

}
//end of class
