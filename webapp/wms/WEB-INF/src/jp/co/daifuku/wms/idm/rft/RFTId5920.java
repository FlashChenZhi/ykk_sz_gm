// $Id: RFTId5920.java,v 1.1.1.1 2006/08/17 09:34:10 mori Exp $
package jp.co.daifuku.wms.idm.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 移動ラック空棚データ応答(IdmEmptyLocationResponse) ID=5920 電文を処理します
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id5920の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>				<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td> <td></td></tr>
 * <tr><td>ID</td>					<td>4 byte</td> <td>5920</td></tr>
 * <tr><td>ハンディ送信時間</td>	<td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>サーバ送信時間</td>		<td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No.</td>		<td>3 byte</td> <td></td></tr>
 * <tr><td>担当者コード</td>		<td>4 byte</td> <td></td></tr>
 * <tr><td>荷主コード</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>商品コード</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>ケース・ピース区分</td>	<td>1 byte</td> <td>1:ケース 2:ピース 3:指定なし</td></tr>
 * <tr><td>空棚区分</td>			<td>1 byte</td> <td>0:空棚 1:補充棚</td></tr>
 * <tr><td>一覧ファイル名</td>		<td>30 byte</td><td></td></tr>
 * <tr><td>応答フラグ</td>			<td>1 byte</td> <td>0:正常 8:該当データ無し 9:エラー</td></tr>
 * <tr><td>エラー詳細</td>			<td>2 byte</td> <td></td></tr>
 * <tr><td>ET:</td>					<td>1 byte</td> <td></td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/05</TD><TD>C.Kaminishizono</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:10 $
 * @author  $Author: mori $
 */
public class RFTId5920 extends SendIdMessage
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
	 * 商品コードのオフセットの定義
	 */
	private static final int OFF_ITEM_CODE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE ;
	
	/**
	 * ケース・ピース区分のオフセットの定義
	 */
	private static final int OFF_CASE_PIECE_FLAG = OFF_ITEM_CODE + LEN_ITEM_CODE ;

	/**
	 * エリアNOのオフセットの定義
	 */
	private static final int OFF_AREA_NO = OFF_CASE_PIECE_FLAG + LEN_CASE_PIECE_FLAG ;

	/**
	 * 空棚区分のオフセットの定義
	 */
	private static final int OFF_EMPTY_KIND = OFF_AREA_NO + LEN_AREA_NO ;
		
	/**
	 * 一覧ファイル名のオフセットの定義
	 */
	private static final int OFF_FILE_NAME = OFF_EMPTY_KIND + LEN_EMPTY_KIND ;
	
	/**
	 * ファイルレコード数のオフセット
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_FILE_NAME + LEN_FILE_NAME;

	/**
	 * ID番号
	 */
	public static final String ID = "5920";
	
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:10 $") ;
	}

	// Constructors --------------------------------------------------
	/**
	 * コンストラクタ
	 */
	public RFTId5920 ()
	{
		super() ;
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
	public void setWorkerCode (String workerCode)
	{
		setToBuffer(workerCode, OFF_WORKER_CODE) ;
	}
	
	/**
	 * 荷主コードを設定します。
	 * @param  consignorCode  荷主コード
	 */
	public void setConsignorCode (String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE) ;
	}
	
	/**
	 * 商品コードを設定します。
	 * @param  itemCode  商品コード
	 */
	public void setItemCode (String itemCode)
	{
		setToBuffer(itemCode, OFF_ITEM_CODE) ;
	}
	
	/**
	 * ケース・ピース区分を設定します。
	 * @param  casePieceFlag  ケース・ピース区分
	 */
	public void setCasePieceFlag (String casePieceFlag)
	{
		setToBuffer(casePieceFlag, OFF_CASE_PIECE_FLAG) ;
	}
	
	/**
	 * エリアを設定します。
	 * @param  areaNo  エリア
	 */
	public void setAreaNo(String areaNo)
	{
		setToBuffer(areaNo, OFF_AREA_NO) ;
	}
	
	/**
	 * 空棚区分を設定します。
	 * @param  emptyKind  空棚区分
	 */
	public void setEmptyKind (String emptyKind)
	{
		// データを右詰めで格納する
		setToBufferRight(emptyKind, OFF_EMPTY_KIND, LEN_EMPTY_KIND) ;
	}
	
	/**
	 * 一覧ファイル名を設定します。
	 * @param  ansFileName 移動作業No.
	 */
	public void setAnsFileName (String ansFileName)
	{
		setToBuffer(ansFileName, OFF_FILE_NAME) ;
	}
	
	/**
	 * ファイルレコード数を設定します。
	 * @param	出荷先一覧ファイルのレコード数
	 */
	public void setFileRecordNumber(int fileRecordNumber)
	{
		setToBufferRight(fileRecordNumber, OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

