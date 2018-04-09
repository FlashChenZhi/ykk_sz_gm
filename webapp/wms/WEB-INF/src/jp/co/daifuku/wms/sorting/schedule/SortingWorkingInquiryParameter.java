package jp.co.daifuku.wms.sorting.schedule;

import jp.co.daifuku.wms.base.common.Parameter;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : S.Yoshida <BR>
 * Maker : S.Yoshida <BR>
 * <BR>
 * <CODE>SortingWorkingInquiryParameter</CODE>クラスは、仕分状況照会画面～スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは仕分状況照会画面で使用される項目を保持します。<BR>
 * <BR>
 * <DIR>
 * <CODE>SortingWorkingInquiryParameter</CODE>クラスが保持する項目<BR>
 * <BR>
 *     荷主コード <BR>
 *     仕分予定日(プルダウン) <BR>
 *     仕分予定日 <BR>
 * 
 *     合計_仕分先数<BR>
 *     合計_伝票枚数<BR>
 *     合計_アイテム数<BR>
 *     合計_数量(ケース数、ピース数)<BR>
 *     合計_荷主数<BR>
 * 
 *     未処理_仕分先数<BR>
 *     未処理_伝票枚数<BR>
 *     未処理_アイテム数<BR>
 *     未処理_数量(ケース数、ピース数)<BR>
 *     未処理_荷主数<BR>
 * 
 *     作業中_仕分先数<BR>
 *     作業中_伝票枚数<BR>
 *     作業中_アイテム数<BR>
 *     作業中_数量(ケース数、ピース数)<BR>
 *     作業中_荷主数<BR>
 * 
 *     処理済_仕分先数<BR>
 *     処理済_伝票枚数<BR>
 *     処理済_アイテム数<BR>
 *     処理済_数量(ケース数、ピース数)<BR>
 *     処理済_荷主数<BR>
 *     
 *     進捗率_仕分先数<BR>
 *     進捗率_伝票枚数<BR>
 *     進捗率_アイテム数<BR>
 *     進捗率_数量(ケース数、ピース数)<BR>
 *     進捗率_荷主数<BR>
 *
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/27</TD><TD>S.Yoshida</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:34 $
 * @author  $Author: mori $
 */
public class SortingWorkingInquiryParameter extends Parameter
{
	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	private String wConsignorCode;

	/**
	 * 仕分予定日(プルダウン)
	 */
	private String wPlanDateP[];

	/**
	 * 仕分予定日
	 */
	private String wPlanDate;
	
	/**
	 * 合計_仕分先数
	 */
	private int wTotalSoringCount;

	/**
	 * 合計_伝票枚数
	 */
	private int wTotalTicketCount;

	/**
	 * 合計_アイテム数
	 */
	private int wTotalItemCount;

	/**
	 * 合計_数量(ケース数、ピース数)
	 */
	private long wTotalQtyCount;

	/**
	 * 合計_荷主数
	 */
	private int wTotalConsignorCount;
	
	
	/**
	 * 未処理_仕分先数
	 */
	private int wUnstartSoringCount;

	/**
	 * 未処理_伝票枚数
	 */
	private int wUnstartTicketCount;

	/**
	 * 未処理_アイテム数
	 */
	private int wUnstartItemCount;

	/**
	 * 未処理_数量(ケース数、ピース数)
	 */
	private long wUnstartQtyCount;

	/**
	 * 未処理_荷主数
	 */
	private int wUnstartConsignorCount;


	/**
	 * 作業中_仕分先数
	 */
	private int wNowSoringCount;

	/**
	 * 作業中_伝票枚数
	 */
	private int wNowTicketCount;

	/**
	 * 作業中_アイテム数
	 */
	private int wNowItemCount;

	/**
	 * 作業中_数量(ケース数、ピース数)
	 */
	private long wNowQtyCount;

	/**
	 * 作業中_荷主数
	 */
	private int wNowConsignorCount;


	/**
	 * 処理済_仕分先数
	 */
	private int wFinishSoringCount;

	/**
	 * 処理済_伝票枚数
	 */
	private int wFinishTicketCount;

	/**
	 * 処理済_アイテム数
	 */
	private int wFinishItemCount;

	/**
	 * 処理済_数量(ケース数、ピース数)
	 */
	private long wFinishQtyCount;

	/**
	 * 処理済_荷主数
	 */
	private int wFinishConsignorCount;


	/**
	 * 進捗率_仕分先数
	 */
	private String wSoringProgressiveRate;

	/**
	 * 進捗率_伝票枚数
	 */
	private String wTicketProgressiveRate;

	/**
	 * 進捗率_アイテム数
	 */
	private String wItemProgressiveRate;

	/**
	 * 進捗率_数量(ケース数、ピース数)
	 */
	private String wQtyProgressiveRate;

	/**
	 * 進捗率_荷主数
	 */
	private String wConsignorProgressiveRate;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:34 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * このクラスの初期化を行ないます。
	 */
	public SortingWorkingInquiryParameter()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * 荷主コードをセットします。
	 * @param arg セットする荷主コード
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg;
	}

	/**
	 * 荷主コードを取得します。
	 * @return 荷主コード
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	/**
	 * 仕分予定日(プルダウン)をセットします。
	 * @param arg セットする仕分予定日(プルダウン)
	 */
	public void setPlanDateP(String arg[])
	{
		wPlanDateP = arg;
	}

	/**
	 * 仕分予定日(プルダウン)を取得します。
	 * @return 仕分予定日(プルダウン)
	 */
	public String[] getPlanDateP()
	{
		return wPlanDateP;
	}
	
	/**
	 * 仕分予定日をセットします。
	 * @param arg セットする仕分予定日
	 */
	public void setPlanDate(String arg)
	{
		wPlanDate = arg;
	}

	/**
	 * 仕分予定日を取得します。
	 * @return 仕分予定日
	 */
	public String getPlanDate()
	{
		return wPlanDate;
	}

	/**
	 * 合計_仕分先数をセットします。
	 * @param セットする合計_仕分先数
	 */
	public void setTotalSoringCount(int arg)
	{
		wTotalSoringCount = arg;
	}

	/**
	 * 合計_仕分先数を取得します。
	 * @return 合計_仕分先数
	 */
	public int getTotalSoringCount()
	{
		return wTotalSoringCount;
	}

	/**
	 * 合計_伝票枚数をセットします。
	 * @param セットする合計_伝票枚数
	 */
	public void setTotalTicketCount(int arg)
	{
		wTotalTicketCount = arg;
	}

	/**
	 * 合計_伝票枚数を取得します。
	 * @return 合計_伝票枚数
	 */
	public int getTotalTicketCount()
	{
		return wTotalTicketCount;
	}

	/**
	 * 合計_アイテム数をセットします。
	 * @param セットする合計_アイテム数
	 */
	public void setTotalItemCount(int arg)
	{
		wTotalItemCount = arg;
	}

	/**
	 * 合計_アイテム数を取得します。
	 * @return 合計_アイテム数
	 */
	public int getTotalItemCount()
	{
		return wTotalItemCount;
	}

	/**
	 * 合計_数量(ケース数、ピース数)をセットします。
	 * @param セットする合計_数量(ケース数、ピース数)
	 */
	public void setTotalQtyCount(long arg)
	{
		wTotalQtyCount = arg;
	}

	/**
	 * 合計_数量(ケース数、ピース数)を取得します。
	 * @return 合計_数量(ケース数、ピース数)
	 */
	public long getTotalQtyCount()
	{
		return wTotalQtyCount;
	}

	/**
	 * 合計_荷主数をセットします。
	 * @param セットする合計_荷主数
	 */
	public void setTotalConsignorCount(int arg)
	{
		wTotalConsignorCount = arg;
	}

	/**
	 * 合計_荷主数を取得します。
	 * @return 合計_荷主数
	 */
	public int getTotalConsignorCount()
	{
		return wTotalConsignorCount;
	}

	/**
	 * 未処理_仕分先数をセットします。
	 * @param セットする未処理_仕分先数
	 */
	public void setUnstartSoringCount(int arg)
	{
		wUnstartSoringCount = arg;
	}

	/**
	 * 未処理_仕分先数を取得します。
	 * @return 未処理_仕分先数
	 */
	public int getUnstartSoringCount()
	{
		return wUnstartSoringCount;
	}

	/**
	 * 未処理_伝票枚数をセットします。
	 * @param セットする未処理_伝票枚数
	 */
	public void setUnstartTicketCount(int arg)
	{
		wUnstartTicketCount = arg;
	}

	/**
	 * 未処理_伝票枚数を取得します。
	 * @return 未処理_伝票枚数
	 */
	public int getUnstartTicketCount()
	{
		return wUnstartTicketCount;
	}

	/**
	 * 未処理_アイテム数をセットします。
	 * @param セットする未処理_アイテム数
	 */
	public void setUnstartItemCount(int arg)
	{
		wUnstartItemCount = arg;
	}

	/**
	 * 未処理_アイテム数を取得します。
	 * @return 未処理_アイテム数
	 */
	public int getUnstartItemCount()
	{
		return wUnstartItemCount;
	}

	/**
	 * 未処理_数量(ケース数、ピース数)をセットします。
	 * @param セットする未処理_数量(ケース数、ピース数)
	 */
	public void setUnstartQtyCount(long arg)
	{
		wUnstartQtyCount = arg;
	}

	/**
	 * 未処理_数量(ケース数、ピース数)を取得します。
	 * @return 未処理_数量(ケース数、ピース数)
	 */
	public long getUnstartQtyCount()
	{
		return wUnstartQtyCount;
	}

	/**
	 * 未処理_荷主数をセットします。
	 * @param セットする未処理_荷主数
	 */
	public void setUnstartConsignorCount(int arg)
	{
		wUnstartConsignorCount = arg;
	}

	/**
	 * 未処理_荷主数を取得します。
	 * @return 未処理_荷主数
	 */
	public int getUnstartConsignorCount()
	{
		return wUnstartConsignorCount;
	}


	/**
	 * 作業中_仕分先数をセットします。
	 * @param セットする作業中_仕分先数
	 */
	public void setNowSoringCount(int arg)
	{
		wNowSoringCount = arg;
	}

	/**
	 * 作業中_仕分先数を取得します。
	 * @return 作業中_仕分先数
	 */
	public int getNowSoringCount()
	{
		return wNowSoringCount;
	}

	/**
	 * 作業中_伝票枚数をセットします。
	 * @param セットする作業中_伝票枚数
	 */
	public void setNowTicketCount(int arg)
	{
		wNowTicketCount = arg;
	}

	/**
	 * 作業中_伝票枚数を取得します。
	 * @return 作業中_伝票枚数
	 */
	public int getNowTicketCount()
	{
		return wNowTicketCount;
	}

	/**
	 * 作業中_アイテム数をセットします。
	 * @param セットする作業中_アイテム数
	 */
	public void setNowItemCount(int arg)
	{
		wNowItemCount = arg;
	}

	/**
	 * 作業中_アイテム数を取得します。
	 * @return 作業中_アイテム数
	 */
	public int getNowItemCount()
	{
		return wNowItemCount;
	}

	/**
	 * 作業中_数量(ケース数、ピース数)をセットします。
	 * @param セットする作業中_数量(ケース数、ピース数)
	 */
	public void setNowQtyCount(long arg)
	{
		wNowQtyCount = arg;
	}

	/**
	 * 作業中_数量(ケース数、ピース数)を取得します。
	 * @return 作業中_数量(ケース数、ピース数)
	 */
	public long getNowQtyCount()
	{
		return wNowQtyCount;
	}

	/**
	 * 作業中_荷主数をセットします。
	 * @param セットする作業中_荷主数
	 */
	public void setNowConsignorCount(int arg)
	{
		wNowConsignorCount = arg;
	}

	/**
	 * 作業中_荷主数を取得します。
	 * @return 作業中_荷主数
	 */
	public int getNowConsignorCount()
	{
		return wNowConsignorCount;
	}

	/**
	 * 処理済_仕分先数をセットします。
	 * @param セットする処理済_仕分先数
	 */
	public void setFinishSoringCount(int arg)
	{
		wFinishSoringCount = arg;
	}

	/**
	 * 処理済_仕分先数を取得します。
	 * @return 処理済_仕分先数
	 */
	public int getFinishSoringCount()
	{
		return wFinishSoringCount;
	}

	/**
	 * 処理済_伝票枚数をセットします。
	 * @param セットする処理済_伝票枚数
	 */
	public void setFinishTicketCount(int arg)
	{
		wFinishTicketCount = arg;
	}

	/**
	 * 処理済_伝票枚数を取得します。
	 * @return 処理済_伝票枚数
	 */
	public int getFinishTicketCount()
	{
		return wFinishTicketCount;
	}

	/**
	 * 処理済_アイテム数をセットします。
	 * @param セットする処理済_アイテム数
	 */
	public void setFinishItemCount(int arg)
	{
		wFinishItemCount = arg;
	}

	/**
	 * 処理済_アイテム数を取得します。
	 * @return 処理済_アイテム数
	 */
	public int getFinishItemCount()
	{
		return wFinishItemCount;
	}

	/**
	 * 処理済_数量(ケース数、ピース数)をセットします。
	 * @param セットする処理済_数量(ケース数、ピース数)
	 */
	public void setFinishQtyCount(long arg)
	{
		wFinishQtyCount = arg;
	}

	/**
	 * 処理済_数量(ケース数、ピース数)を取得します。
	 * @return 処理済_数量(ケース数、ピース数)
	 */
	public long getFinishQtyCount()
	{
		return wFinishQtyCount;
	}

	/**
	 * 処理済_荷主数をセットします。
	 * @param セットする処理済_荷主数
	 */
	public void setFinishConsignorCount(int arg)
	{
		wFinishConsignorCount = arg;
	}

	/**
	 * 処理済_荷主数を取得します。
	 * @return 処理済_荷主数
	 */
	public int getFinishConsignorCount()
	{
		return wFinishConsignorCount;
	}



	/**
	 * 進捗率_仕分先数をセットします。
	 * @param セットする進捗率_仕分先数
	 */
	public void setSoringProgressiveRate(String arg)
	{
		wSoringProgressiveRate = arg;
	}

	/**
	 * 進捗率_仕分先数を取得します。
	 * @return 進捗率_仕分先数
	 */
	public String getSoringProgressiveRate()
	{
		return wSoringProgressiveRate;
	}

	/**
	 * 進捗率_伝票枚数をセットします。
	 * @param セットする進捗率_伝票枚数
	 */
	public void setTicketProgressiveRate(String arg)
	{
		wTicketProgressiveRate = arg;
	}

	/**
	 * 進捗率_伝票枚数を取得します。
	 * @return 進捗率_伝票枚数
	 */
	public String getTicketProgressiveRate()
	{
		return wTicketProgressiveRate;
	}

	/**
	 * 進捗率_アイテム数をセットします。
	 * @param セットする進捗率_アイテム数
	 */
	public void setItemProgressiveRate(String arg)
	{
		wItemProgressiveRate = arg;
	}

	/**
	 * 進捗率_アイテム数を取得します。
	 * @return 進捗率_アイテム数
	 */
	public String getItemProgressiveRate()
	{
		return wItemProgressiveRate;
	}

	/**
	 * 進捗率_数量(ケース数、ピース数)をセットします。
	 * @param セットする進捗率_数量(ケース数、ピース数)
	 */
	public void setQtyProgressiveRate(String arg)
	{
		wQtyProgressiveRate = arg;
	}

	/**
	 * 進捗率_数量(ケース数、ピース数)を取得します。
	 * @return 進捗率_数量(ケース数、ピース数)
	 */
	public String getQtyProgressiveRate()
	{
		return wQtyProgressiveRate;
	}

	/**
	 * 進捗率_荷主数をセットします。
	 * @param セットする進捗率_荷主数
	 */
	public void setConsignorProgressiveRate(String arg)
	{
		wConsignorProgressiveRate = arg;
	}

	/**
	 * 進捗率_荷主数を取得します。
	 * @return 進捗率_荷主数
	 */
	public String getConsignorProgressiveRate()
	{
		return wConsignorProgressiveRate;
	}

}
