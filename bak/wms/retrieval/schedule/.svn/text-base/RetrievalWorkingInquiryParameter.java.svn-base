package jp.co.daifuku.wms.retrieval.schedule;

import jp.co.daifuku.wms.base.common.Parameter;

//#CM725950
/*
 * Created on 2004/10/18
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM725951
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * Use <CODE>RetrievalWorkingInquiryParameter</CODE> class to pass parameters between the Inquiry Picking Status screen and the schedule. <BR>
 * Allow this class to maintain the field items used in the Picking status inquiry screen. The field item in use depends on each screen. <BR>
 * <BR>
 * <DIR>
 * Field item which the <CODE>RetreivalWorkingInquiryParameter</CODE> class maintains <BR>
 * <BR>
 *     Consignor Code <BR>
 *     Planned Picking Date (Pulldown)  <BR>
 *     Planned Picking Date <BR>
 *     
 *     Total_Order Count <BR>
 *     Total_Work Count <BR>
 *     Total_ Case Qty <BR>
 *     Total_ Piece Qty <BR>
 *     Total_ Number of consignors <BR>
 *     
 *     Not Processed- Order Count <BR>
 *     Not Processed_Work Count <BR>
 *     Not Processed_ Case Qty <BR>
 *     Not Processed_ Piece Qty <BR>
 *     Not Processed_ Number of consignors <BR>
 * 
 *     Working Total_Order Count <BR>
 *     Working_Work Count <BR>
 *     Working Total_ Case Qty <BR>
 *     Working Total_ Piece Qty <BR>
 *     Working Total_ Number of consignors <BR>
 *     
 *     Processed_Order Count <BR>
 *     Processed_Work Count <BR>
 *     Processed_ Case Qty <BR>
 *     Processed_ Piece Qty <BR>
 *     Processed_ Number of consignors <BR>
 *     
 *     Progress rate_Order Count <BR>
 *     Progress rate_Work Count <BR>
 *     Progress rate_ Case Qty <BR>
 *     Progress rate_ Piece Qty <BR>
 *     Progress rate_ Number of consignors <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @author suresh kayamboo
 * @version 2004/10/18
 */
public class RetrievalWorkingInquiryParameter extends Parameter
{
    //#CM725952
    //Constants-----------------------------------------------
    //#CM725953
    //Attributes----------------------------------------------
	//#CM725954
	/**
	 * Consignor Code
	 */
	private String wConsignorCode;

	//#CM725955
	/**
	 * Planned Picking Date (Pulldown) 
	 */
	private String wPlanDateP[];

	//#CM725956
	/**
	 * Planned Picking Date
	 */
	private String wPlanDate;
	
	//#CM725957
	/**
	 * Total_Order Count 
	 */
	private int wOrderTotal;
	
	//#CM725958
	/**
	 * Total_Work Count 
	 */
	private int wWorkTotal;

	//#CM725959
	/**
	 * Total_ Case Qty 
	 */
	private long wCaseTotal;

	//#CM725960
	/**
	 * Total_ Piece Qty 
	 */
	private long wPieceTotal;

	//#CM725961
	/**
	 * Total_ Number of consignors 
	 */
	private int wConsignorTotal;

	//#CM725962
	/**
	 * Not Processed_Order Count 
	 */
	private int wUnstartOrderCount;

	//#CM725963
	/**
	 * Not Processed_Work Count 
	 */
	private int wUnstartWorkCount;

	//#CM725964
	/**
	 * Not Processed_ Case Qty 
	 */
	private long wUnstartCaseCount;

	//#CM725965
	/**
	 * Not Processed_ Piece Qty 
	 */
	private long wUnstartPieceCount;

	//#CM725966
	/**
	 * Not Processed_ Number of consignors 
	 */
	private int wUnstartConsignorCount;

	//#CM725967
	/**
	 * Working Total_Order Count 
	 */
	private int wNowOrderCount;

	//#CM725968
	/**
	 * Working_Work Count 
	 */
	private int wNowWorkCount;

	//#CM725969
	/**
	 * Working Total_ Case Qty 
	 */
	private long wNowCaseCount;

	//#CM725970
	/**
	 * Working Total_ Piece Qty 
	 */
	private long wNowPieceCount;

	//#CM725971
	/**
	 * Working Total_ Number of consignors 
	 */
	private int wNowConsignorCount;

	//#CM725972
	/**
	 * Processed_Order Count 
	 */
	private int wFinishOrderCount;
	
	//#CM725973
	/**
	 * Processed_Work Count 
	 */
	private int wFinishWorkCount;

	//#CM725974
	/**
	 * Processed_ Case Qty 
	 */
	private long wFinishCaseCount;

	//#CM725975
	/**
	 * Processed_ Piece Qty 
	 */
	private long wFinishPieceCount;

	//#CM725976
	/**
	 * Processed_ Number of consignors 
	 */
	private int wFinishConsignorCount;

	//#CM725977
	/**
	 * Progress rate_Order Count 
	 */
	private String wOrderRate;

	//#CM725978
	/**
	 * Progress rate_Work Count 
	 */
	private String wWorkRate;

	//#CM725979
	/**
	 * Progress rate_ Case Qty 
	 */
	private String wCaseRate;

	//#CM725980
	/**
	 * Progress rate_ Piece Qty 
	 */
	private String wPieceRate;

	//#CM725981
	/**
	 * Progress rate_ Number of consignors 
	 */
	private String wConsignorRate;

	//#CM725982
	//Static----------------------------------------------------------
	
	//#CM725983
	//Constructors--------------------------------------------------
	//#CM725984
	/**
	 * Initialize this class. 
	 */
	public RetrievalWorkingInquiryParameter()
	{
	}
	
	//#CM725985
	//Public--------------------------------------------------
	//#CM725986
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:20:04 $");
	}

	//#CM725987
	/**
	 * Set the Consignor code.
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		wConsignorCode = arg;
	}

	//#CM725988
	/**
	 * Obtain the Consignor code.
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return wConsignorCode;
	}

	//#CM725989
	/**
	 * Set the Planned Picking Date (Pulldown). 
	 * @param arg Planned Picking Date to be set (Pulldown) 
	 */
	public void setPlanDateP(String arg[])
	{
		wPlanDateP = arg;
	}

	//#CM725990
	/**
	 * Obtain the Planned Picking Date (Pulldown). 
	 * @return Planned Picking Date (Pulldown) 
	 */
	public String[] getPlanDateP()
	{
		return wPlanDateP;
	}
	
	//#CM725991
	/**
	 * Set the Planned Picking date.
	 * @param arg Planned Picking Date to be set
	 */
	public void setPlanDate(String arg)
	{
		wPlanDate = arg;
	}

	//#CM725992
	/**
	 * Obtain the Planned Picking Date.
	 * @return Planned Picking Date
	 */
	public String getPlanDate()
	{
		return wPlanDate;
	}

	//#CM725993
	/**
	 * Set the Not Processed_Order Count. 
	 * @param arg Not Processed_Order Count to be set
	 */
	public void setUnstartOrderCount(int arg)
	{
		wUnstartOrderCount = arg;
	}

	//#CM725994
	/**
	 * Obtain the Not Processed_Order count. 
	 * @return Not Processed_Order Count 
	 */
	public int getUnstartOrderCount()
	{
		return wUnstartOrderCount;
	}

	//#CM725995
	/**
	 * Set the Not-processed_ Work count. 
	 * @param arg Not Processed_Work Count to be set
	 */
	public void setUnstartWorkCount(int arg)
	{
		wUnstartWorkCount = arg;
	}

	//#CM725996
	/**
	 * Obtain the Not-processed_ Work count. 
	 * @return Not Processed_Work Count 
	 */
	public int getUnstartWorkCount()
	{
		return wUnstartWorkCount;
	}

	//#CM725997
	/**
	 * Set the Not-processed_ Case qty. 
	 * @param arg Not Processed_ Case Qty to be set
	 */
	public void setUnstartCaseCount(long arg)
	{
		wUnstartCaseCount = arg;
	}

	//#CM725998
	/**
	 * Obtain the Not-processed_ Case qty. 
	 * @return Not Processed_ Case Qty 
	 */
	public long getUnstartCaseCount()
	{
		return wUnstartCaseCount;
	}

	//#CM725999
	/**
	 * Set the Not-processed_ Piece qty. 
	 * @param arg Not Processed_ Piece Qty to be set
	 */
	public void setUnstartPieceCount(long arg)
	{
		wUnstartPieceCount = arg;
	}

	//#CM726000
	/**
	 * Obtain the Not-processed_ Piece qty. 
	 * @return Not Processed_ Piece Qty 
	 */
	public long getUnstartPieceCount()
	{
		return wUnstartPieceCount;
	}

	//#CM726001
	/**
	 * Set the Not-processed_ Count of consignors. 
	 * @param arg Not Processed_ Number of consignors to be set
	 */
	public void setUnstartConsignorCount(int arg)
	{
		wUnstartConsignorCount = arg;
	}

	//#CM726002
	/**
	 * Obtain the Not-processed_ Count of consignors. 
	 * @return Not Processed_ Number of consignors 
	 */
	public int getUnstartConsignorCount()
	{
		return wUnstartConsignorCount;
	}

	//#CM726003
	/**
	 * Set the Working Total_Order Count. 
	 * @param arg Working_Order Count to be set
	 */
	public void setNowOrderCount(int arg)
	{
		wNowOrderCount = arg;
	}

	//#CM726004
	/**
	 * Obtain the Working Total_Order count 
	 * @return Working Total_Order Count 
	 */
	public int getNowOrderCount()
	{
		return wNowOrderCount;
	}

	//#CM726005
	/**
	 * Set the Working_ Work count. 
	 * @param arg Working_Work Count to be set
	 */
	public void setNowWorkCount(int arg)
	{
		wNowWorkCount = arg;
	}

	//#CM726006
	/**
	 * Obtain the Working_ Work count. 
	 * @return Working_Work Count 
	 */
	public int getNowWorkCount()
	{
		return wNowWorkCount;
	}
	
	//#CM726007
	/**
	 * Set the Working Total_ Case qty. 
	 * @param arg Working_ Case Qty to be set
	 */
	public void setNowCaseCount(long arg)
	{
		wNowCaseCount = arg;
	}

	//#CM726008
	/**
	 * Obtain the Working Total_ Case qty. 
	 * @return Working Total_ Case Qty 
	 */
	public long getNowCaseCount()
	{
		return wNowCaseCount;
	}

	//#CM726009
	/**
	 * Set the Working Total_ Piece qty. 
	 * @param arg Working_ Piece Qty to be set
	 */
	public void setNowPieceCount(long arg)
	{
		wNowPieceCount = arg;
	}

	//#CM726010
	/**
	 * Obtain the Working Total_ Piece qty. 
	 * @return Working Total_ Piece Qty 
	 */
	public long getNowPieceCount()
	{
		return wNowPieceCount;
	}

	//#CM726011
	/**
	 * Set the Working_ Count of consignors. 
	 * @param arg Working_ Number of consignors to be set
	 */
	public void setNowConsignorCount(int arg)
	{
		wNowConsignorCount = arg;
	}

	//#CM726012
	/**
	 * Obtain the Working_ Count of consignors. 
	 * @return Working Total_ Number of consignors 
	 */
	public int getNowConsignorCount()
	{
		return wNowConsignorCount;
	}

	//#CM726013
	/**
	 * Set the Processed_Order Count. 
	 * @param arg Processed_Order Count to be set
	 */
	public void setFinishOrderCount(int arg)
	{
		wFinishOrderCount = arg;
	}

	//#CM726014
	/**
	 * Obtain Processed_Order count. 
	 * @return Processed_Order Count 
	 */
	public int getFinishOrderCount()
	{
		return wFinishOrderCount;
	}

	//#CM726015
	/**
	 * Set the Processed_ Work count. 
	 * @param arg Processed_Work Count to be set
	 */
	public void setFinishWorkCount(int arg)
	{
		wFinishWorkCount = arg;
	}

	//#CM726016
	/**
	 * Obtain the Processed_ Work count. 
	 * @return Count of Processed_ Work 
	 */
	public int getFinishWorkCount()
	{
		return wFinishWorkCount;
	}


	//#CM726017
	/**
	 * Set the Processed_ Case qty. 
	 * @param arg Processed_ Case Qty to be set
	 */
	public void setFinishCaseCount(long arg)
	{
		wFinishCaseCount = arg;
	}

	//#CM726018
	/**
	 * Obtain the Processed_ Case qty. 
	 * @return Processed_ Case Qty 
	 */
	public long getFinishCaseCount()
	{
		return wFinishCaseCount;
	}

	//#CM726019
	/**
	 * Set the Processed_ Piece qty. 
	 * @param arg Processed_ Piece Qty to be set
	 */
	public void setFinishPieceCount(long arg)
	{
		wFinishPieceCount = arg;
	}

	//#CM726020
	/**
	 * Obtain the Processed_ Piece qty. 
	 * @return Processed_ Piece Qty 
	 */
	public long getFinishPieceCount()
	{
		return wFinishPieceCount;
	}

	//#CM726021
	/**
	 * Set the Processed_ Count of consignors. 
	 * @param arg Processed_ Number of consignors to be set
	 */
	public void setFinishConsignorCount(int arg)
	{
		wFinishConsignorCount = arg;
	}

	//#CM726022
	/**
	 * Obtain the Processed_ Count of consignors. 
	 * @return Processed_ Number of consignors 
	 */
	public int getFinishConsignorCount()
	{
		return wFinishConsignorCount;
	}

	//#CM726023
	/**
	 * Set the Total_Order Count. 
	 * @param arg Total Order Count Order Count to be set
	 */
	public void setOrderTotal(int arg)
	{
		wOrderTotal = arg;
	}

	//#CM726024
	/**
	 * Obtain the Total_Order count.
	 * @return Total_Order Count 
	 */
	public int getOrderTotal()
	{
		return wOrderTotal;
	}

	//#CM726025
	/**
	 * Set the Total_ Work count. 
	 * @param arg Total_Work Count to be set
	 */
	public void setWorkTotal(int arg)
	{
		wWorkTotal = arg;
	}

	//#CM726026
	/**
	 * Obtain the Total _ Work count. 
	 * @return Total_Work Count 
	 */
	public int getWorkTotal()
	{
		return wWorkTotal;
	}
	
	//#CM726027
	/**
	 * Set the Total_ Case qty. 
	 * @param arg Total_ Case Qty to be set
	 */
	public void setCaseTotal(long arg)
	{
		wCaseTotal = arg;
	}

	//#CM726028
	/**
	 * Obtain the Total_ Case Qty. 
	 * @return Total_ Case Qty 
	 */
	public long getCaseTotal()
	{
		return wCaseTotal;
	}

	//#CM726029
	/**
	 * Set the Total_ Piece qty. 
	 * @param arg Total_ Piece Qty to be set
	 */
	public void setPieceTotal(long arg)
	{
		wPieceTotal = arg;
	}

	//#CM726030
	/**
	 * Obtain the Total_ Piece Qty. 
	 * @return Total_ Piece Qty 
	 */
	public long getPieceTotal()
	{
		return wPieceTotal;
	}

	//#CM726031
	/**
	 * Set the Total_ Count of consignors. 
	 * @param arg Total_ Number of consignors to be set
	 */
	public void setConsignorTotal(int arg)
	{
		wConsignorTotal = arg;
	}

	//#CM726032
	/**
	 * Obtain the Total_ Number of consignors. 
	 * @return Total_ Number of consignors 
	 */
	public int getConsignorTotal()
	{
		return wConsignorTotal;
	}

	//#CM726033
	/**
	 * Set the Progress rate_Order Count. 
	 * @param arg Progress rate_Order Count to be set
	 */
	public void setOrderRate(String arg)
	{
		wOrderRate = arg;
	}

	//#CM726034
	/**
	 * Obtain the Progress rate_Order count. 
	 * @return Progress rate_Order Count 
	 */
	public String getOrderRate()
	{
		return wOrderRate;
	}

	//#CM726035
	/**
	 * Set the Progress rate_ Work count. 
	 * @param arg Progress rate_Work Count to be set
	 */
	public void setWorkRate(String arg)
	{
		wWorkRate = arg;
	}

	//#CM726036
	/**
	 * Obtain the Progress rate_ Work count. 
	 * @return Progress rate_Work Count 
	 */
	public String getWorkRate()
	{
		return wWorkRate;
	}
	
	//#CM726037
	/**
	 * Set the Progress rate_ Case qty. 
	 * @param arg Progress rate_ Case Qty to be set
	 */
	public void setCaseRate(String arg)
	{
		wCaseRate = arg;
	}

	//#CM726038
	/**
	 * Obtain the Progress rate_ Case Qty. 
	 * @return Progress rate_ Case Qty 
	 */
	public String getCaseRate()
	{
		return wCaseRate;
	}

	//#CM726039
	/**
	 * Set the Progress rate_ Piece Qty. 
	 * @param arg Progress rate_ Piece Qty to be set
	 */
	public void setPieceRate(String arg)
	{
		wPieceRate = arg;
	}

	//#CM726040
	/**
	 * Obtain the Progress rate_ Piece qty. 
	 * @return Progress rate_ Piece Qty 
	 */
	public String getPieceRate()
	{
		return wPieceRate;
	}

	//#CM726041
	/**
	 * Set the Progress rate_ Count of consignors. 
	 * @param arg Progress rate_ Number of consignors to be set
	 */
	public void setConsignorRate(String arg)
	{
		wConsignorRate = arg;
	}

	//#CM726042
	/**
	 * Obtain the Progress rate_ Count of consignors. 
	 * @return Progress rate_ Number of consignors 
	 */
	public String getConsignorRate()
	{
		return wConsignorRate;
	}

	//#CM726043
	//Private---------------------------------------------------------------
	//#CM726044
	//InnerClasses----------------------------------------------------------
}
