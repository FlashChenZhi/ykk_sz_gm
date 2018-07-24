package jp.co.daifuku.wms.storage.schedule;

import jp.co.daifuku.wms.base.common.Parameter;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * <BR>
 * Use the <CODE>StorageSupportWorkingInquiryParameter</CODE> class to pass the parameter between the storage status inquiry screen and the schedule.<BR>
 * This class maintains the items (count) to be used in storage status inquiry screen.<BR>
 * <BR>
 * <DIR>
 * Allow the<CODE>StorageSupportWorkingInquiryParameter</CODE> class to maintain the items (count):<BR>
 * <BR>
 *     Consignor code <BR>
 *     Planned storage date(Pull-down) <BR>
 *     Planned storage date <BR>
 *
 *     Total_Work count<BR>
 *     Total_Case Qty<BR>
 *     Total_Piece Qty<BR>
 *     Total_Consignor count<BR>
 *
 *     Not Processed_Work count<BR>
 *     Not Processed_Case Qty<BR>
 *     Not Processed_Piece qty<BR>
 *     Not Processed_Consignor count<BR>
 *
 *     Processing_Work count<BR>
 *     Processing_Case Qty<BR>
 *     Processing_Piece qty<BR>
 *     Processing_Consignor count<BR>
 *
 *     Processed_Work count<BR>
 *     Processed_Case Qty<BR>
 *     Processed_Piece qty<BR>
 *     Processed_Consignor count<BR>
 *
 *     Progress rate_Work count<BR>
 *     Progress rate_Case Qty<BR>
 *     Progress rate_Piece qty<BR>
 *     Progress rate_Consignor count<BR>
 *
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>K.Matsuda</TD><TD>Create New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/18 06:51:54 $
 * @author  $Author: suresh $
 */
public class StorageWorkingInquiryParameter extends Parameter
{
	// Class variables -----------------------------------------------
	/**
	 * Consignor code
	 */
	private String wConsignorCode;

	/**
	 * Planned storage date(Pull-down)
	 */
	private String wPlanDateP[];

	/**
	 * Planned storage date
	 */
	private String wPlanDate;


	/**
	 * Total_Work count
	 */
	private int wTotalWorkCount;

	/**
	 * Total_Case Qty
	 */
	private long wTotalCaseCount;

	/**
	 * Total_Piece Qty
	 */
	private long wTotalPieceCount;

	/**
	 * Total_Consignor count
	 */
	private int wTotalConsignorCount;


	/**
	 * Not Processed_Work count
	 */
	private int wUnstartWorkCount;

	/**
	 * Not Processed_Case Qty
	 */
	private long wUnstartCaseCount;

	/**
	 * Not Processed_Piece qty
	 */
	private long wUnstartPieceCount;

	/**
	 * Not Processed_Consignor count
	 */
	private int wUnstartConsignorCount;


	/**
	 * Processing_Work count
	 */
	private int wNowWorkCount;

	/**
	 * Processing_Case Qty
	 */
	private long wNowCaseCount;

	/**
	 * Processing_Piece qty
	 */
	private long wNowPieceCount;

	/**
	 * Processing_Consignor count
	 */
	private int wNowConsignorCount;


	/**
	 * Processed_Work count
	 */
	private int wFinishWorkCount;

	/**
	 * Processed_Case Qty
	 */
	private long wFinishCaseCount;

	/**
	 * Processed_Piece qty
	 */
	private long wFinishPieceCount;

	/**
	 * Processed_Consignor count
	 */
	private int wFinishConsignorCount;


	/**
	 * Progress rate_Work count
	 */
	private String wWorkProgressiveRate;

	/**
	 * Progress rate_Case Qty
	 */
	private String wCaseProgressiveRate;

	/**
	 * Progress rate_Piece qty
	 */
	private String wPieceProgressiveRate;

	/**
	 * Progress rate_Consignor count
	 */
	private String wConsignorProgressiveRate;

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/18 06:51:54 $");
	}

	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StorageWorkingInquiryParameter()
	{
	}

	// Public methods ------------------------------------------------
	/**
	 * Set the Consignor code.
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg;
	}

	/**
	 * Obtain the Consignor code.
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	/**
	 * Set the Planned storage date (pull-down).
	 * @param arg Planned storage date (pull-down) to be set
	 */
	public void setPlanDateP(String arg[])
	{
		wPlanDateP = arg;
	}

	/**
	 * Obtain the Planned storage date (pull down).
	 * @return Planned storage date(Pull-down)
	 */
	public String[] getPlanDateP()
	{
		return wPlanDateP;
	}

	/**
	 * Set the Planned storage date.
	 * @param arg Planned storage date to be set
	 */
	public void setPlanDate(String arg)
	{
		wPlanDate = arg;
	}

	/**
	 * Obtain the Planned storage date.
	 * @return Planned storage date
	 */
	public String getPlanDate()
	{
		return wPlanDate;
	}

	/**
	 *  Set the Total_ Work count.
	 * @param Total_ Work count to be set
	 */
	public void setTotalWorkCount(int arg)
	{
		wTotalWorkCount = arg;
	}

	/**
	 * Obtain the Total_ Work count.
	 * @return Total_ Work count
	 */
	public int getTotalWorkCount()
	{
		return wTotalWorkCount;
	}

	/**
	 * Set the Total_ Case qty.
	 * @param Total_ Case qty to be set
	 */
	public void setTotalCaseCount(long arg)
	{
		wTotalCaseCount = arg;
	}

	/**
	 * Obtain Total_ Case qty.
	 * @return Total_ Case qty
	 */
	public long getTotalCaseCount()
	{
		return wTotalCaseCount;
	}

	/**
	 * Set the Total_ Piece qty.
	 * @param Total_ Piece qty to be set
	 */
	public void setTotalPieceCount(long arg)
	{
		wTotalPieceCount = arg;
	}

	/**
	 * Obtain Total_ Piece qty
	 * @return Total_ Piece qty
	 */
	public long getTotalPieceCount()
	{
		return wTotalPieceCount;
	}

	/**
	 * Set the Total_ Count of consignors.
	 * @param total_ Count of consignors to be set
	 */
	public void setTotalConsignorCount(int arg)
	{
		wTotalConsignorCount = arg;
	}

	/**
	 *  Obtain total_ Count of consignors.
	 * @return total_ Count of consignors
	 */
	public int getTotalConsignorCount()
	{
		return wTotalConsignorCount;
	}

	/**
	 * Set the Not-processed_ Work count.
	 * @param Not-processed_ Work count to be set
	 */
	public void setUnstartWorkCount(int arg)
	{
		wUnstartWorkCount = arg;
	}

	/**
	 * Obtain the Not-processed_ Work count.
	 * @return Not-processed_ Work count
	 */
	public int getUnstartWorkCount()
	{
		return wUnstartWorkCount;
	}

	/**
	 * Set the Not-processed_ Case qty.
	 * @param Not-processed_Case qty to be set
	 */
	public void setUnstartCaseCount(long arg)
	{
		wUnstartCaseCount = arg;
	}

	/**
	 * Obtain the Not-processed_Case qty.
	 * @return Not-processed_Case qty
	 */
	public long getUnstartCaseCount()
	{
		return wUnstartCaseCount;
	}

	/**
	 * Set the Not-processed_ Piece qty.
	 * @param Not-processed_Piece qty to be set
	 */
	public void setUnstartPieceCount(long arg)
	{
		wUnstartPieceCount = arg;
	}

	/**
	 * Obtain the Not-processed_Piece qty.
	 * @return Not-processed_Piece qty
	 */
	public long getUnstartPieceCount()
	{
		return wUnstartPieceCount;
	}

	/**
	 * Set the Not-processed_ Count of consignors.
	 * @param Not-processed_ Count of consignors to be set
	 */
	public void setUnstartConsignorCount(int arg)
	{
		wUnstartConsignorCount = arg;
	}

	/**
	 * Obtain the Not-processed_ Count of consignors.
	 * @return Not-processed_ Count of consignors
	 */
	public int getUnstartConsignorCount()
	{
		return wUnstartConsignorCount;
	}


	/**
	 * Set the Processing_ Work count.
	 * @param Processing_Work count to be set
	 */
	public void setNowWorkCount(int arg)
	{
		wNowWorkCount = arg;
	}

	/**
	 * Obtain the Processing_Work count.
	 * @return Processing_Work count
	 */
	public int getNowWorkCount()
	{
		return wNowWorkCount;
	}

	/**
	 * Set the Processing_ Case qty.
	 * @param Processing_Case qty to be set
	 */
	public void setNowCaseCount(long arg)
	{
		wNowCaseCount = arg;
	}

	/**
	 * Obtain the Processing_Case qty.
	 * @return Processing_Case qty
	 */
	public long getNowCaseCount()
	{
		return wNowCaseCount;
	}

	/**
	 * Set the Processing_ Piece qty.
	 * @param Processing_Piece qty to be set
	 */
	public void setNowPieceCount(long arg)
	{
		wNowPieceCount = arg;
	}

	/**
	 * Obtain the Processing_Piece qty.
	 * @return Processing_Piece qty
	 */
	public long getNowPieceCount()
	{
		return wNowPieceCount;
	}

	/**
	 * Set the Processing_ Count of consignors.
	 * @param Processing_Count of consignors to be set
	 */
	public void setNowConsignorCount(int arg)
	{
		wNowConsignorCount = arg;
	}

	/**
	 * Obtain the Processing_Count of consignors.
	 * @return Processing_Count of consignors
	 */
	public int getNowConsignorCount()
	{
		return wNowConsignorCount;
	}

	/**
	 * Set the Processed_Work count.
	 * @param Processed_Work count to be set
	 */
	public void setFinishWorkCount(int arg)
	{
		wFinishWorkCount = arg;
	}

	/**
	 * Obtain the Processed_Work count.
	 * @return Processed_Work count
	 */
	public int getFinishWorkCount()
	{
		return wFinishWorkCount;
	}

	/**
	 * Set the Processed_ Case qty.
	 * @param Processed_Case qty to be set
	 */
	public void setFinishCaseCount(long arg)
	{
		wFinishCaseCount = arg;
	}

	/**
	 * Obtain the Processed_Case qty.
	 * @return Processed_Case qty
	 */
	public long getFinishCaseCount()
	{
		return wFinishCaseCount;
	}

	/**
	 * Set the Processed_Piece qty.
	 * @param Processed_Piece qty to be set
	 */
	public void setFinishPieceCount(long arg)
	{
		wFinishPieceCount = arg;
	}

	/**
	 * Obtain the Processed_ Piece qty.
	 * @return Processed_Piece qty
	 */
	public long getFinishPieceCount()
	{
		return wFinishPieceCount;
	}

	/**
	 * Set the Processed_Count of consignors.
	 * @param Processed_Count of consignors to be set
	 */
	public void setFinishConsignorCount(int arg)
	{
		wFinishConsignorCount = arg;
	}

	/**
	 * Obtain the Processed_Count of consignors.
	 * @return Processed_Count of consignors
	 */
	public int getFinishConsignorCount()
	{
		return wFinishConsignorCount;
	}

	/**
	 * Set the Progress rate_ Work count.
	 * @param Progress rate_Work count to be set
	 */
	public void setWorkProgressiveRate(String arg)
	{
		wWorkProgressiveRate = arg;
	}

	/**
	 * Obtain the Progress rate_ Work count.
	 * @return Progress rate_Work count
	 */
	public String getWorkRate()
	{
		return wWorkProgressiveRate;
	}

	/**
	 * Set the Progress rate_ Case qty.
	 * @param Progress rate_ Case qty to be set
	 */
	public void setCaseProgressiveRate(String arg)
	{
		wCaseProgressiveRate = arg;
	}

	/**
	 * Obtain Progress rate_ Case qty
	 * @return Progress rate_ Case qty
	 */
	public String getCaseProgressiveRate()
	{
		return wCaseProgressiveRate;
	}

	/**
	 * Set the Progress rate_Piece qty.
	 * @param Set the Progress rate_Piece qty
	 */
	public void setPieceProgressiveRate(String arg)
	{
		wPieceProgressiveRate = arg;
	}

	/**
	 * Obtain the Progress rate_Piece qty.
	 * @return Progress rate_Piece qty
	 */
	public String getPieceProgressiveRate()
	{
		return wPieceProgressiveRate;
	}

	/**
	 * Set the Progress rate_ Count of consignors.
	 * @param Progress rate_ Count of consignors to be set
	 */
	public void setConsignorProgressiveRate(String arg)
	{
		wConsignorProgressiveRate = arg;
	}

	/**
	 * Obtain the Progress rate_ Count of consignors.
	 * @return Progress rate_ Count of consignors
	 */
	public String getConsignorProgressiveRate()
	{
		return wConsignorProgressiveRate;
	}

}
