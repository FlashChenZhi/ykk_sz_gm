//$Id: ShippingWorkingInquiryParameter.java,v 1.1.1.1 2006/08/17 09:34:31 mori Exp $
package jp.co.daifuku.wms.shipping.schedule;

import jp.co.daifuku.wms.base.common.Parameter;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * <CODE>ShippingWorkingInquiryParameter</CODE>クラスは、出荷状況照会画面～スケジュール間のパラメータの受渡しに使用します。<BR>
 * このクラスでは出荷状況照会画面で使用される項目を保持します。使用する項目は画面によって異なります。<BR>
 * <BR>
 * <DIR>
 * <CODE>ShippingWorkingInquiryParameter</CODE>クラスが保持する項目<BR>
 * <DIR>
 *     荷主コード <BR>
 *     出荷予定日(プルダウン) <BR>
 *     出荷予定日 <BR>
 *     未処理_出荷先数<BR>
 *     未処理_伝票枚数<BR>
 *     未処理_アイテム数<BR>
 *     未処理_ケース数<BR>
 *     未処理_ピース数<BR>
 *     未処理_荷主数<BR>
 *     作業中_出荷先数<BR>
 *     作業中_伝票枚数<BR>
 *     作業中_アイテム数<BR>
 *     作業中_ケース数<BR>
 *     作業中_ピース数<BR>
 *     作業中_荷主数<BR>
 *     処理済_出荷先数<BR>
 *     処理済_伝票枚数<BR>
 *     処理済_アイテム数<BR>
 *     処理済_ケース数<BR>
 *     処理済_ピース数<BR>
 *     処理済_荷主数<BR>
 *     出荷先数_合計<BR>
 *     伝票枚数_合計<BR>
 *     アイテム数_合計<BR>
 *     ケース数_合計<BR>
 *     ピース数_合計<BR>
 *     荷主数_合計<BR>
 *     出荷先数_進捗率<BR>
 *     伝票枚数_進捗率<BR>
 *     アイテム数_進捗率<BR>
 *     ケース数_進捗率<BR>
 *     ピース数_進捗率<BR>
 *     荷主数_進捗率<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/20</TD><TD>Y.Kubo</TD><TD>新規作成</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.1.1.1 $, $Date: 2006/08/17 09:34:31 $
 * @author  $Author: mori $
 */
public class ShippingWorkingInquiryParameter extends Parameter
{
	// Class variables -----------------------------------------------
	/**
	 * 荷主コード
	 */
	private String wConsignorCode;

	/**
	 * 出荷予定日(プルダウン)
	 */
	private String wPlanDateP[];

	/**
	 * 出荷予定日
	 */
	private String wPlanDate;
	
	/**
	 * 未処理_出荷先数
	 */
	private int wUnstartCustomerCount;

	/**
	 * 未処理_伝票枚数
	 */
	private int wUnstartTicketCount;

	/**
	 * 未処理_アイテム数
	 */
	private int wUnstartItemCount;

	/**
	 * 未処理_ケース数
	 */
	private long wUnstartCaseCount;

	/**
	 * 未処理_ピース数
	 */
	private long wUnstartPieceCount;

	/**
	 * 未処理_荷主数
	 */
	private int wUnstartConsignorCount;

	/**
	 * 作業中_出荷先数
	 */
	private int wNowCustomerCount;

	/**
	 * 作業中_伝票枚数
	 */
	private int wNowTicketCount;

	/**
	 * 作業中_アイテム数
	 */
	private int wNowItemCount;

	/**
	 * 作業中_ケース数
	 */
	private long wNowCaseCount;

	/**
	 * 作業中_ピース数
	 */
	private long wNowPieceCount;

	/**
	 * 作業中_荷主数
	 */
	private int wNowConsignorCount;

	/**
	 * 処理済_出荷先数
	 */
	private int wFinishCustomerCount;

	/**
	 * 処理済_伝票枚数
	 */
	private int wFinishTicketCount;

	/**
	 * 処理済_アイテム数
	 */
	private int wFinishItemCount;

	/**
	 * 処理済_ケース数
	 */
	private long wFinishCaseCount;

	/**
	 * 処理済_ピース数
	 */
	private long wFinishPieceCount;

	/**
	 * 処理済_荷主数
	 */
	private int wFinishConsignorCount;

	/**
	 * 出荷先数_合計
	 */
	private int wCustomerTotal;

	/**
	 * 伝票枚数_合計
	 */
	private int wTicketTotal;

	/**
	 * アイテム数_合計
	 */
	private int wItemTotal;

	/**
	 * ケース数_合計
	 */
	private long wCaseTotal;

	/**
	 * ピース数_合計
	 */
	private long wPieceTotal;

	/**
	 * 荷主数_合計
	 */
	private int wConsignorTotal;

	/**
	 * 出荷先数_進捗率
	 */
	private String wCustomerRate;

	/**
	 * 伝票枚数_進捗率
	 */
	private String wTicketRate;

	/**
	 * アイテム数_進捗率
	 */
	private String wItemRate;

	/**
	 * ケース数_進捗率
	 */
	private String wCaseRate;

	/**
	 * ピース数_進捗率
	 */
	private String wPieceRate;

	/**
	 * 荷主数_進捗率
	 */
	private String wConsignorRate;

	// Class method --------------------------------------------------
	/**
	 * このクラスのバージョンを返します。
	 * @return バージョンと日付
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.1.1.1 $,$Date: 2006/08/17 09:34:31 $");
	}

	
	/**
	 * このクラスの初期化を行ないます。
	 */
	public ShippingWorkingInquiryParameter()
	{
	}
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
	 * 出荷予定日をセットします。
	 * @param arg セットする出荷予定日
	 */
	public void setPlanDateP(String arg[])
	{
		wPlanDateP = arg;
	}

	/**
	 * 出荷予定日を取得します。
	 * @return 出荷予定日
	 */
	public String[] getPlanDateP()
	{
		return wPlanDateP;
	}
	
	/**
	 * 出荷予定日をセットします。
	 * @param arg セットする出荷予定日
	 */
	public void setPlanDate(String arg)
	{
		wPlanDate = arg;
	}

	/**
	 * 出荷予定日を取得します。
	 * @return 出荷予定日
	 */
	public String getPlanDate()
	{
		return wPlanDate;
	}

	/**
	 * 未処理_出荷先数をセットします。
	 * @param arg セットする未処理_出荷先数
	 */
	public void setUnstartCustomerCount(int arg)
	{
		wUnstartCustomerCount = arg;
	}

	/**
	 * 未処理_出荷先数を取得します。
	 * @return 未処理_出荷先数
	 */
	public int getUnstartCustomerCount()
	{
		return wUnstartCustomerCount;
	}

	/**
	 * 未処理_伝票枚数をセットします。
	 * @param arg セットする未処理_伝票枚数
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
	 * @param arg セットする未処理_アイテム数
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
	 * 未処理_ケース数をセットします。
	 * @param arg セットする未処理_ケース数
	 */
	public void setUnstartCaseCount(long arg)
	{
		wUnstartCaseCount = arg;
	}

	/**
	 * 未処理_ケース数を取得します。
	 * @return 未処理_ケース数
	 */
	public long getUnstartCaseCount()
	{
		return wUnstartCaseCount;
	}

	/**
	 * 未処理_ピース数をセットします。
	 * @param arg セットする未処理_ピース数
	 */
	public void setUnstartPieceCount(long arg)
	{
		wUnstartPieceCount = arg;
	}

	/**
	 * 未処理_ピース数を取得します。
	 * @return 未処理_ピース数
	 */
	public long getUnstartPieceCount()
	{
		return wUnstartPieceCount;
	}

	/**
	 * 未処理_荷主数をセットします。
	 * @param arg セットする未処理_荷主数
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
	 * 作業中_出荷先数をセットします。
	 * @param arg セットする作業中_出荷先数
	 */
	public void setNowCustomerCount(int arg)
	{
		wNowCustomerCount = arg;
	}

	/**
	 * 作業中_出荷先数を取得します。
	 * @return 作業中_出荷先数
	 */
	public int getNowCustomerCount()
	{
		return wNowCustomerCount;
	}

	/**
	 * 作業中_伝票枚数をセットします。
	 * @param arg セットする作業中_伝票枚数
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
	 * @param arg セットする作業中_アイテム数
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
	 * 作業中_ケース数をセットします。
	 * @param arg セットする作業中_ケース数
	 */
	public void setNowCaseCount(long arg)
	{
		wNowCaseCount = arg;
	}

	/**
	 * 作業中_ケース数を取得します。
	 * @return 作業中_ケース数
	 */
	public long getNowCaseCount()
	{
		return wNowCaseCount;
	}

	/**
	 * 作業中_ピース数をセットします。
	 * @param arg セットする作業中_ピース数
	 */
	public void setNowPieceCount(long arg)
	{
		wNowPieceCount = arg;
	}

	/**
	 * 作業中_ピース数を取得します。
	 * @return 作業中_ピース数
	 */
	public long getNowPieceCount()
	{
		return wNowPieceCount;
	}

	/**
	 * 作業中_荷主数をセットします。
	 * @param arg セットする作業中_荷主数
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
	 * 処理済_出荷先数をセットします。
	 * @param arg セットする処理済_出荷先数
	 */
	public void setFinishCustomerCount(int arg)
	{
		wFinishCustomerCount = arg;
	}

	/**
	 * 処理済_出荷先数を取得します。
	 * @return 処理済_出荷先数
	 */
	public int getFinishCustomerCount()
	{
		return wFinishCustomerCount;
	}

	/**
	 * 処理済_伝票枚数をセットします。
	 * @param arg セットする処理済_伝票枚数
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
	 * @param arg セットする処理済_アイテム数
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
	 * 処理済_ケース数をセットします。
	 * @param arg セットする処理済_ケース数
	 */
	public void setFinishCaseCount(long arg)
	{
		wFinishCaseCount = arg;
	}

	/**
	 * 処理済_ケース数を取得します。
	 * @return 処理済_ケース数
	 */
	public long getFinishCaseCount()
	{
		return wFinishCaseCount;
	}

	/**
	 * 処理済_ピース数をセットします。
	 * @param arg セットする処理済_ピース数
	 */
	public void setFinishPieceCount(long arg)
	{
		wFinishPieceCount = arg;
	}

	/**
	 * 処理済_ピース数を取得します。
	 * @return 処理済_ピース数
	 */
	public long getFinishPieceCount()
	{
		return wFinishPieceCount;
	}

	/**
	 * 処理済_荷主数をセットします。
	 * @param arg セットする処理済_荷主数
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
	 * 出荷先数_合計をセットします。
	 * @param arg セットする出荷先数_合計
	 */
	public void setCustomerTotal(int arg)
	{
		wCustomerTotal = arg;
	}

	/**
	 * 出荷先数_合計を取得します。
	 * @return 出荷先数_合計
	 */
	public int getCustomerTotal()
	{
		return wCustomerTotal;
	}

	/**
	 * 伝票枚数_合計をセットします。
	 * @param arg セットする伝票枚数_合計
	 */
	public void setTicketTotal(int arg)
	{
		wTicketTotal = arg;
	}

	/**
	 * 伝票枚数_合計を取得します。
	 * @return 伝票枚数_合計
	 */
	public int getTicketTotal()
	{
		return wTicketTotal;
	}

	/**
	 * アイテム数_合計をセットします。
	 * @param arg セットするアイテム数_合計
	 */
	public void setItemTotal(int arg)
	{
		wItemTotal = arg;
	}

	/**
	 * アイテム数_合計を取得します。
	 * @return アイテム数_合計
	 */
	public int getItemTotal()
	{
		return wItemTotal;
	}

	/**
	 * ケース数_合計をセットします。
	 * @param arg セットするケース数_合計
	 */
	public void setCaseTotal(long arg)
	{
		wCaseTotal = arg;
	}

	/**
	 * ケース数_合計を取得します。
	 * @return ケース数_合計
	 */
	public long getCaseTotal()
	{
		return wCaseTotal;
	}

	/**
	 * ピース数_合計をセットします。
	 * @param arg セットするピース数_合計
	 */
	public void setPieceTotal(long arg)
	{
		wPieceTotal = arg;
	}

	/**
	 * ピース数_合計を取得します。
	 * @return ピース数_合計
	 */
	public long getPieceTotal()
	{
		return wPieceTotal;
	}

	/**
	 * 荷主数_合計をセットします。
	 * @param arg セットする荷主数_合計
	 */
	public void setConsignorTotal(int arg)
	{
		wConsignorTotal = arg;
	}

	/**
	 * 荷主数_合計を取得します。
	 * @return 荷主数_合計
	 */
	public int getConsignorTotal()
	{
		return wConsignorTotal;
	}

	/**
	 * 出荷先数_進捗率をセットします。
	 * @param arg セットする出荷先数_進捗率
	 */
	public void setCustomerRate(String arg)
	{
		wCustomerRate = arg;
	}

	/**
	 * 出荷先数_進捗率を取得します。
	 * @return 出荷先数_進捗率
	 */
	public String getCustomerRate()
	{
		return wCustomerRate;
	}

	/**
	 * 伝票枚数_進捗率をセットします。
	 * @param arg セットする伝票枚数_進捗率
	 */
	public void setTicketRate(String arg)
	{
		wTicketRate = arg;
	}

	/**
	 * 伝票枚数_進捗率を取得します。
	 * @return 伝票枚数_進捗率
	 */
	public String getTicketRate()
	{
		return wTicketRate;
	}

	/**
	 * アイテム数_進捗率をセットします。
	 * @param arg セットするアイテム数_進捗率
	 */
	public void setItemRate(String arg)
	{
		wItemRate = arg;
	}

	/**
	 * アイテム数_進捗率を取得します。
	 * @return アイテム数_進捗率
	 */
	public String getItemRate()
	{
		return wItemRate;
	}

	/**
	 * ケース数_進捗率をセットします。
	 * @param arg セットするケース数_進捗率
	 */
	public void setCaseRate(String arg)
	{
		wCaseRate = arg;
	}

	/**
	 * ケース数_進捗率を取得します。
	 * @return ケース数_進捗率
	 */
	public String getCaseRate()
	{
		return wCaseRate;
	}

	/**
	 * ピース数_進捗率をセットします。
	 * @param arg セットするピース数_進捗率
	 */
	public void setPieceRate(String arg)
	{
		wPieceRate = arg;
	}

	/**
	 * ピース数_進捗率を取得します。
	 * @return ピース数_進捗率
	 */
	public String getPieceRate()
	{
		return wPieceRate;
	}

	/**
	 * 荷主数_進捗率をセットします。
	 * @param arg セットする荷主数_進捗率
	 */
	public void setConsignorRate(String arg)
	{
		wConsignorRate = arg;
	}

	/**
	 * 荷主数_進捗率を取得します。
	 * @return 荷主数_進捗率
	 */
	public String getConsignorRate()
	{
		return wConsignorRate;
	}

}
