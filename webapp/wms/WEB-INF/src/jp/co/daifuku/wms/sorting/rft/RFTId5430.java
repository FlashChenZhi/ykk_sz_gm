// $Id: RFTId5430.java,v 1.1.1.1 2006/08/17 09:34:34 mori Exp $
package jp.co.daifuku.wms.sorting.rft;

import jp.co.daifuku.wms.base.rft.SendIdMessage;

/*
 * Copyright 2003-2005 DAIFUKU Co.,Ltd. All Rights Reserved.

 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
 
/**
 * ソケット通信での「仕分開始応答 ID = 5430」電文を処理します
 *
 * <p>
 * <table border="1">
 * <CAPTION>Id5430の電文の構造</CAPTION>
 * <TR><TH>項目名</TH>			  <TH>長さ</TH>	<TH>内容</TH></TR>
 * <tr><td>STX</td>				  <td> 1 byte</td><td>0x02</td></tr>
 * <tr><td>SEQ NO</td>			  <td> 4 byte</td><td></td></tr>
 * <tr><td>ID</td>				  <td> 4 byte</td><td>5430</td></tr>
 * <tr><td>ハンディ送信時間</td>  <td> 6 byte</td><td>HHMMSS</td></tr>
 * <tr><td>サーバー送信時間</td>  <td> 6 byte</td><td>HHMMSS</td></tr>
 * <tr><td>ハンディ号機No</td>	  <td> 3 byte</td><td></td></tr>
 * <tr><td>担当者コード</td>	  <td> 4 byte</td><td></td></tr>
 * <tr><td>荷主コード</td>		  <td>16 byte</td><td></td></tr>
 * <tr><td>仕分予定日</td>		  <td> 8 byte</td><td>YYYYMMDD</td></tr>
 * <tr><td>JANコード</td>		  <td>16 byte</td><td></td></tr>
 * <tr><td>ボールITF</td>		  <td>16 byte</td><td></td></tr>
 * <tr><td>ケースITF</td>		  <td>16 byte</td><td></td></tr>
 * <tr><td>ケース/ピース区分</td> <td> 1 byte</td><td>1:ケース　2:ピース</td></tr>
 * <tr><td>商品名</td>			  <td>40 byte</td><td></td></tr>
 * <tr><td>ボール入数</td>		  <td> 6 byte</td><td></td></tr>
 * <tr><td>ケース入数</td>		  <td> 6 byte</td><td></td></tr>
 * <tr><td>応答ファイル名</td>	  <td>30 byte</td><td></td></tr>
 * <tr><td>ファイルレコード数</td><td> 6 byte</td><td></td></tr>
 * <tr><td>総仕分先数</td>		  <td> 9 byte</td><td></td></tr>
 * <tr><td>総仕分数</td>		  <td> 9 byte</td><td></td></tr>
 * <tr><td>応答フラグ</td>		  <td> 1 byte</td><td></td></tr>
 * <tr><td>エラー詳細</td>		  <td> 2 byte</td><td></td></tr>
 * <tr><td>ETX</td>			 	  <td> 1 byte</td><td>0x03</td></tr>
 * </table>
 * </p>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/29</TD><TD>K.Matsuda</TD><TD>created this class</TD><TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */

public class RFTId5430 extends  SendIdMessage
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
	 * 仕分予定日のオフセット
	 */
	private static final int OFF_PLAN_DATE = OFF_CONSIGNOR_CODE + LEN_CONSIGNOR_CODE;
	
	/**
	 * JANコードのオフセット
	 */
	private static final int OFF_JAN_CODE = OFF_PLAN_DATE + LEN_PLAN_DATE ;
	
	/**
	 * ボールITFのオフセットの定義
	 */
	private static final int OFF_BUNDLE_ITF = OFF_JAN_CODE + LEN_JAN_CODE;

	/**
	 * ケースITFのオフセットの定義
	 */
	private static final int OFF_ITF = OFF_BUNDLE_ITF + LEN_BUNDLE_ITF;
	
	/**
	 * 作業形態（ケース・ピース区分）のオフセット
	 */
	private static final int OFF_WORK_FORM = OFF_ITF + LEN_JAN_CODE;
	
	/**
	 * 商品名称のオフセットの定義
	 */
	private static final int OFF_ITEM_NAME = OFF_WORK_FORM + LEN_WORK_FORM ;
	
	/**
	 * ボール入数のオフセットの定義
	 */
	private static final int OFF_BUNDLE_ENTERING_QTY = OFF_ITEM_NAME + LEN_ITEM_NAME ;
	
	/**
	 * ケース入数のオフセットの定義
	 */
	private static final int OFF_ENTERING_QTY = OFF_BUNDLE_ENTERING_QTY + LEN_BUNDLE_ENTERING_QTY ;

	/**
	 * 作業ファイル名のオフセット
	 */
	private static final int OFF_FILE_NAME = OFF_ENTERING_QTY + LEN_ENTERING_QTY;
		
	/**
	 * ファイルレコード数のオフセット
	 */
	private static final int OFF_FILE_RECORD_NUMBER = OFF_FILE_NAME + LEN_FILE_NAME ;
		
	/**
	 * 総仕分先数のオフセットの定義
	 */
	private static final int OFF_TOTAL_SORTING_COUNT = OFF_FILE_RECORD_NUMBER + LEN_FILE_RECORD_NUMBER ;

	/**
	 * 総仕分数のオフセットの定義
	 */
	private static final int OFF_TOTAL_SORTING_QTY = OFF_TOTAL_SORTING_COUNT + LEN_TOTAL_SORTING_COUNT ;
	
	/**
	 * ID番号
	 */
	public static final String ID = "5430";
	
	// Class method ------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return "$Revision: 1.1.1.1 $Date: 2006/08/17 09:34:34 $";
	}
	
	// Constructors -------------------------------------------------
	/**
	 * コンストラクタ
	 */
	public RFTId5430 ()
	{
		super();
		offAnsCode = OFF_TOTAL_SORTING_QTY + LEN_TOTAL_SORTING_QTY;
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
	 * 荷主コードを設定します。
	 * @param  consignorCode  荷主コード
	 */
	public void setConsignorCode(String consignorCode)
	{
		setToBuffer(consignorCode, OFF_CONSIGNOR_CODE);
	}
	
	/**
	 * 仕分予定日を設定します。
	 * @param  planDate  仕分予定日
	 */
	public void setPlanDate (String planDate)
	{
		setToBuffer(planDate, OFF_PLAN_DATE);
	}
	
	/**
	 * 作業形態（ケース・ピース区分）を設定します。
	 * @param  workForm  作業形態
	 */
	public void setWorkForm (String workForm)
	{
		setToBuffer(workForm, OFF_WORK_FORM);
	}

	/**
	 * JANコードを設定します。
	 * @param  janCode  JANコード
	 */
	public void setJanCode (String janCode)
	{
		setToBuffer(janCode, OFF_JAN_CODE);
	}

	/**
	 * ボールITFコードを設定します。
	 * @param  BundleITF  ボールITF
	 */
	public void setBundleITF (String bundleITF)
	{
		setToBuffer(bundleITF, OFF_BUNDLE_ITF);
	}

	/**
	 * ケースITFコードを設定します。
	 * @param  BundleITF  ケースITF
	 */
	public void setITF (String ITF)
	{
		setToBuffer(ITF, OFF_ITF);
	}

	/**
	 * 商品名称を設定します。
	 * @param  itemName  商品名称
	 */
	public void setItemName (String itemName)
	{
		setToBuffer(itemName, OFF_ITEM_NAME);
	}
	
	/**
	 * ボール入数を設定します。
	 * @param  bundleEnteringQty  ボール入数
	 */
	public void setBundleEnteringQty (String bundleEnteringQty)
	{
		setToBufferRight(bundleEnteringQty , OFF_BUNDLE_ENTERING_QTY, LEN_BUNDLE_ENTERING_QTY);
	}
	
	/**
	 * ケース入数を設定します。
	 * @param  enteringQty  ケース入数
	 */
	public void setEnteringQty (String enteringQty)
	{
		setToBufferRight(enteringQty , OFF_ENTERING_QTY, LEN_ENTERING_QTY);
	}
	
	/**
	 * 作業ファイル名を設定します。
	 * @param  ansFileName  応答ファイル名
	 */
	public void setAnsFileName (String ansFileName)
	{
		setToBuffer(ansFileName, OFF_FILE_NAME);
	}
	
	/**
	 * ファイルレコード数を設定します。
	 * @param fileRecordNumber ファイルレコード数
	 */
	public void setFileRecordNumber(int fileRecordNumber)
	{
		setToBufferRight(fileRecordNumber, OFF_FILE_RECORD_NUMBER, LEN_FILE_RECORD_NUMBER);
	}
		
	/**
	 * 総仕分先数を設定します。
	 * @param  totalSortCount  総仕分先数
	 */
	public void setTotalSortingCount (int totalSortCount)
	{
		setToBufferRight(totalSortCount , OFF_TOTAL_SORTING_COUNT, LEN_TOTAL_SORTING_COUNT);
	}
	
	/**
	 * 総仕分数を設定します。
	 * @param  totalSortingQty  総仕分数
	 */
	public void setTotalSortingQty (int totalSortingQty)
	{
		setToBufferRight(totalSortingQty , OFF_TOTAL_SORTING_QTY, LEN_TOTAL_SORTING_QTY);
	}
	


	// Package methods ----------------------------------------------
	
	// Protected methods --------------------------------------------

	// Private methods ----------------------------------------------

}
//end of class
