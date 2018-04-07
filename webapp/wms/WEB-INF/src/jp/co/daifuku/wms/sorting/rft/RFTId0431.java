// $Id: RFTId0431.java,v 1.1.1.1 2006/08/17 09:34:33 mori Exp $
package jp.co.daifuku.wms.sorting.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
 /**
  * ソケット通信での「仕分け実績送信 ID = 0431」電文を処理します
  *
  * <p>
  * <table border="1">
  * <CAPTION>Id0431の電文の構造</CAPTION>
  * <TR><TH>項目名</TH>				<TH>長さ</TH>	 <TH>内容</TH></TR>
  * <tr><td>STX</td>				<td> 1byte</td><td>0x02</td></tr>
  * <tr><td>SEQ NO</td>				<td> 4byte</td><td></td></tr>
  * <tr><td>ID</td>					<td> 4byte</td><td>0431</td></tr>
  * <tr><td>ハンディ送信時間</td>	<td> 6byte</td><td>HHMMSS</td></tr>
  * <tr><td>サーバー送信時間</td>	<td> 6byte</td><td>HHMMSS</td></tr>
  * <tr><td>ハンディ号機No</td>		<td> 3byte</td><td></td></tr>
  * <tr><td>担当者コード</td>		<td> 4byte</td><td></td></tr>
  * <tr><td>荷主コード</td>			<td>16byte</td><td></td></tr>
  * <tr><td>仕分予定日</td>			<td> 8byte</td><td></td></tr>
  * <tr><td>JANコード</td>			<td>16byte</td><td></td></tr>
  * <tr><td>作業形態</td>			<td> 1byte</td><td>1:ケース　2:ピース</td></tr>
  * <tr><td>作業時間</td>			<td> 1byte</td><td></td></tr>
  * <tr><td>ミス回数</td>			<td> 5byte</td><td></td></tr>
  * <tr><td>完了区分</td>			<td> 5byte</td><td>0:確定 9:キャンセル</td></tr>
  * <tr><td>実績ファイル名</td>		<td>30byte</td><td></td></tr>
  * <tr><td>ファイルレコード数</td>	<td> 6byte</td><td></td></tr>
  * <tr><td>ETX</td>				<td> 1byte</td><td>0x03</td></tr>
  * </table>
  * </p>
  * <BR>
  * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
  * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
  * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
  * </TABLE>
  * <BR>
  * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:33 $
  * @author  $Author: mori $
  */

public class RFTId0431 extends RecvIdMessage
{
	// Class field --------------------------------------------------
	/**
	 * 担当者コードのオフセット
	 */
	private static final int OFF_WORKER_CODE = LEN_HEADER ;
	
	/**
	 * 荷主コードのオフセット
	 */
	private static final int OFF_CONSIGNOR_CODE = OFF_WORKER_CODE + LEN_WORKER_CODE ;
	
	/**
	 * 仕分予定日のオフセット
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	/**
	 * JANコードのオフセット
	 */
	private static final int OFF_JAN_CODE = OFF_PLAN_DATE + LEN_PLAN_DATE ;
	
	/**
	 * 作業形態(ケース・ピース区分)のオフセット
	 */
	private static final int OFF_WORK_FORM = OFF_JAN_CODE + LEN_JAN_CODE;
	
	/**
	 * 作業時間のオフセットの定義
	 */
	private static final int OFF_WORK_SECONDS = OFF_WORK_FORM + LEN_WORK_FORM;

	/**
	 * ミス回数のオフセットの定義
	 */
	private static final int OFF_MISS_COUNT = OFF_WORK_SECONDS + LEN_WORK_TIME;
		
	/**
	 * 完了区分のオフセット
	 */
	private static final int OFF_COMPLETION_FLAG = OFF_MISS_COUNT + LEN_MISS_COUNT;
	
	/**
	 * 実績ファイル名のオフセット
	 */
	private static final int OFF_FILE_NAME = OFF_COMPLETION_FLAG + LEN_COMPLETION_FLAG;
	
	/**
	 * ファイルレコード数のオフセット
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_FILE_NAME + LEN_FILE_NAME ;
	
	/**
	 * ID番号
	 */
	public static final String ID = "0431";
	
	// Class variables ----------------------------------------------
	
	// Class method -------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $Date: 2006/08/17 09:34:33 $") ;
	}
	
	// Constructors -------------------------------------------------
	/**
	 * コンストラクタ
	 */
	public RFTId0431 ()
	{
		super() ;
		offEtx = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	// Public methods -----------------------------------------------
	/**
	 * 担当者コードを取得します。
	 * @return   担当者コード
	 */
	public String getWorkerCode()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE);
		return workerCode.trim();
	}
	
	/**
	 * 荷主コードを取得します
	 * @return   荷主コード
	 */
	public String getConsignorCode()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE);
		return consignorCode.trim();
	}
	
	/**
	 * 仕分予定日を取得します
	 * @return   仕分予定日
	 */
	public String getPlanDate()
	{
		String planDate = getFromBuffer(OFF_PLAN_DATE, LEN_PLAN_DATE);
		return planDate.trim();
	}
	
	/**
	 * JANコードを取得します
	 * @return		JANコード
	 */
	public String getJANCode ()
	{
		String JANCode = getFromBuffer(OFF_JAN_CODE, LEN_JAN_CODE) ;
		return JANCode.trim();
	}
	
	/**
	 * 作業形態（ケース・ピース区分）を取得します
	 * @return   作業形態
	 */
	public String getWorkForm()
	{
		String workForm = getFromBuffer(OFF_WORK_FORM, LEN_WORK_FORM);
		return workForm.trim();
	}
	
	/**
	 * 作業時間を取得します。
	 * @return		作業時間
	 */
	public int getWorkSeconds ()
	{
		int workSeconds = getIntFromBuffer(OFF_WORK_SECONDS, LEN_WORK_TIME);
		return workSeconds;
	}

	/**
	 * ミス回数を取得します。
	 * @return		ミス回数
	 */
	public int getMissCount ()
	{
		int count = getIntFromBuffer(OFF_MISS_COUNT, LEN_MISS_COUNT);
		return count;
	}
	
	/**
	 * 完了区分を取得します
	 * @return   完了区分
	 */
	public String getCompletionFlag()
	{
		String completionFlag = getFromBuffer(OFF_COMPLETION_FLAG, LEN_COMPLETION_FLAG);
		return completionFlag.trim();
	}
	
	/**
	 * 実績ファイル名を取得します
	 * @return   実績ファイル名
	 */
	public String getResultFileName()
	{
		String resultFileName = getFromBuffer(OFF_FILE_NAME, LEN_FILE_NAME);
		return resultFileName.trim();
	}
	
	/**
	 * ファイルレコード数を設定します。
	 * @param fileRecordNumber ファイルレコード数
	 */
	public int getFileRecordNumber()
	{
		int fileRecordNumber = getIntFromBuffer(OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
		return fileRecordNumber;
	}
	
	
	// Package methods ----------------------------------------------
	
	// Protected methods --------------------------------------------

	// Private methods ----------------------------------------------
	
}
//end of class