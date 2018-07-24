// $Id: RFTId0920.java,v 1.1.1.1 2006/08/17 09:34:10 mori Exp $
package jp.co.daifuku.wms.idm.rft;

import jp.co.daifuku.wms.base.rft.RecvIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * 移動ラック空棚データ要求(IdmEmptyLocationRequest) ID=0920 電文を処理します
 * 
 * <p>
 * <table border="1">
 * <CAPTION>Id0920の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>				<TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>					<td>1 byte</td> <td>0x02</td></tr>
 * <tr><td>SEQ No.</td>				<td>4 byte</td> <td></td></tr>
 * <tr><td>ID</td>					<td>4 byte</td> <td>0920</td></tr>
 * <tr><td>ハンディ送信時間</td>	<td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>サーバ送信時間</td>		<td>6 byte</td> <td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No.</td>		<td>3 byte</td> <td></td></tr>
 * <tr><td>担当者コード</td>		<td>4 byte</td> <td></td></tr>
 * <tr><td>荷主コード</td>			<td>16 byte</td><td></td></tr>
 * <tr><td>ケース・ピース区分</td>	<td>1 byte</td> <td>1:ケース 2:ピース 3:指定なし</td></tr>
 * <tr><td>空棚区分</td>			<td>1 byte</td> <td>0:空棚 1:補充棚</td></tr>
 * <tr><td>ETX</td>					<td>1 byte</td> <td>0x03</td></tr>
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
public class RFTId0920 extends RecvIdMessage
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
	 * ケース･ピース区分の全てを表すフィールド
	 */
	public static final String CASE_PIECE_All = "0" ;

	/**
	 * 空棚区分の空棚要求を表すフィールド
	 */
	public static final String EMPTY_KIND_EMPTY = "0" ;
	
	/**
	 * 空棚区分の補充棚要求を表すフィールド
	 */
	public static final String EMPTY_KIND_REPLENISH = "1" ;
	
	/**
	 * ID番号
	 */
	public static final String ID = "0920";
	
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
	public RFTId0920 ()
	{
		super() ;
		offEtx = OFF_EMPTY_KIND + LEN_EMPTY_KIND;
		length = offEtx + LEN_ETX;
		wLocalBuffer = new byte[length];
	}
	
	/**
	 * RFTから受信した電文をコンストラクタに渡します。
	 * @param rftId0920 入庫JAN問い合わせ(StorageJANRequest) ID=0920 電文
	 */
	public RFTId0920 (byte[] rftId0920)
	{
		super() ;
		setReceiveMessage(rftId0920) ;
	}

	// Public methods ------------------------------------------------
	/**
	 * 移動ラック空棚データ要求(IdmEmptyLocationRequest)電文から担当者コードを取得します。
	 * @return		担当者コード
	 */
	public String getWorkerCode ()
	{
		String workerCode = getFromBuffer(OFF_WORKER_CODE, LEN_WORKER_CODE) ;
		return workerCode.trim();
	}
	
	/**
	 * 移動ラック空棚データ要求(IdmEmptyLocationRequest)電文から荷主コードを取得します。
	 * @return		荷主コード
	 */
	public String getConsignorCode ()
	{
		String consignorCode = getFromBuffer(OFF_CONSIGNOR_CODE, LEN_CONSIGNOR_CODE) ;
		return consignorCode.trim();
	}
	
	/**
	 * 移動ラック空棚データ要求(IdmEmptyLocationRequest)電文から商品コードを取得します。
	 * @return		商品コード
	 */
	public String getItemCode ()
	{
		String itemCode = getFromBuffer(OFF_ITEM_CODE, LEN_ITEM_CODE) ;
		return itemCode.trim();
	}
	
	/**
	 * 移動ラック空棚データ要求(IdmEmptyLocationRequest)電文からケース・ピース区分を取得します。
	 * @return		ケース・ピース区分
	 * 				1:ケース
	 * 				2:ピース
	 */
	public String getCasePieceFlag ()
	{
		String casePieceFlag = getFromBuffer(OFF_CASE_PIECE_FLAG, LEN_CASE_PIECE_FLAG) ;
		return casePieceFlag.trim();
	}
	
	/**
	 * 移動ラック空棚データ要求(IdmEmptyLocationRequest)電文からエリアを取得します。
	 * @return		エリア
	 */
	public String getAreaNo()
	{
		String areaNo = getFromBuffer(OFF_AREA_NO, LEN_AREA_NO) ;
		return areaNo.trim();
	}
	
	/**
	 * 移動ラック空棚データ要求(IdmEmptyLocationRequest)電文から空棚区分を取得します。
	 * @return		空棚区分
	 * 				0:空棚
	 * 				1:補充棚
	 */
	public String getEmptyKind ()
	{
		String casePieceFlag = getFromBuffer(OFF_EMPTY_KIND, LEN_EMPTY_KIND) ;
		return casePieceFlag.trim();
	}
	
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	// Private methods -----------------------------------------------

}
//end of class

