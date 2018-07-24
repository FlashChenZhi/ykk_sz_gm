package jp.co.daifuku.wms.retrieval.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM723951
/*
 * Created on 2004/11/04
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM723952
/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * Allow this class to execute the process for inquiring the Order Picking Plan. <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to inquire the Order Picking Plan. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process for Initial Display (<CODE>initFind()</CODE> method) <BR> 
 * <BR>
 * <DIR>
 *   Return the Consignor Code, if only one Consignor Code exists in the Picking Plan Info. <BR> 
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR> 
 *   <BR>
 *   [search conditions]  <BR> 
 *   <BR>
 *   For data with status flag "Standby", "Working", "Partially Completed", or "Completed" <BR>
 * </DIR>
 * <BR>
 * 2. Process by clicking "Display" button (<CODE>query()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and obtain the data to output to the preset area from the database and return it. <BR>
 *   If no corresponding data is found, return <CODE>Parameter</CODE> array with number of elements equal to 0. Return null when condition error occurs. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 *   Search through the Picking Plan Info table. (DNRETRIEVALPLAN) <BR>
 *   Disable to display if the search target count exceeds 1000 (MAX_NUMBER_OF_DISP defined in WMSParam). <BR>
 *   Obtain the Consignor name with later added date/time needed to display in the listcell header. <BR>
 *   <BR>
 *   [search conditions]  <BR> 
 *   <BR>
 *   For data with status flag "Standby", "Working", "Partially Completed", or "Completed" <BR>
 *   <BR>
 *   [Parameter]  *Mandatory Input<BR>
 *   <BR>
 *   Consignor Code* <BR>
 *   Start Planned Picking Date <BR>
 *   End Planned Picking Date <BR>
 *   Customer Code <BR>
 *   Case Order No.<BR>
 * 	 Piece Order No.<BR>
 *   Work status:<BR>
 *   <BR>
 *   [Returned data]  <BR>
 *   <BR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Planned Picking Date <BR>
 *   Customer Code <BR>
 *   Customer Name <BR>
 *   Case Order No. <BR>
 *   Piece Order No. <BR>
 * 	 Item Code <BR>
 *   Item Name <BR>
 *   Division  <BR>
 *   Packed Qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Host planned Case qty  <BR>
 *   Host Planned Piece Qty <BR>
 *   Result Case Qty  <BR>
 *   Result Piece Qty  <BR>
 *   Case Picking Location <BR>
 *   Piece Picking Location  <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Status name  <BR>
 *   Added date  <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/27</TD><TD>K.Toda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @author suresh kayamboo
 * @version 2004/11/04
 */
public class RetrievalOrderPlanInquirySCH extends AbstractRetrievalSCH
{

	//#CM723953
	//  Constants----------------------------------------------------

	//#CM723954
	//Attributes---------------------------------------------------

	//#CM723955
	//Static-------------------------------------------------------

	//#CM723956
	//Constructors-------------------------------------------------
	//#CM723957
	/**
	 * Initialize this class. 
	 */
	public RetrievalOrderPlanInquirySCH()
	{
		wMessage = null;
	}

	//#CM723958
	//Public-------------------------------------------------------
	//#CM723959
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:58 $");
	}

	//#CM723960
	/**
	 * Allow this method to support the operation to obtain the data required for initial display. <BR>
	 * Return the Consignor Code, if only one Consignor Code exists in the Picking Plan Info. <BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.  <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>. 
	 * @param conn Database connection object
	 * @param searchParam Class that inherits the <CODE>Parameter</CODE> class with search conditions 
	 * @return Class that implements the <CODE>Parameter</CODE> interface that contains the search result. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */

	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalPlanSearchKey sKey = new RetrievalPlanSearchKey();

		RetrievalSupportParameter param = null;
		//#CM723961
		// Set the WHERE condition. 
		sKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "<>");
		sKey.setCaseOrderNo("", "IS NOT NULL", "(", "", "OR");
		sKey.setPieceOrderNo("", "IS NOT NULL", "", ")", "AND");
		//#CM723962
		// Set the GROUP BY condition. 
		sKey.setConsignorCodeGroup(1);
		//#CM723963
		// Set the COLLECT conditions. 
		sKey.setConsignorCodeCollect("");

		RetrievalPlanHandler rpHandler = new RetrievalPlanHandler(conn);

		if (rpHandler.count(sKey) == 1)
		{
			RetrievalPlan[] result = (RetrievalPlan[]) rpHandler.find(sKey);
			param = new RetrievalSupportParameter();
			param.setConsignorCode(result[0].getConsignorCode());
		}
		return param;
	}

	//#CM723964
	/**
	 * Receive the contents entered via screen as a parameter, and obtain the data for inquiring the Order Picking Plan from the database.  <BR>
	 * 
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam
	 *            Instance of   <CODE>RetrievalSupportParameter</CODE>
	 *            class containing condition to obtain data to be displayed. <CODE>RetrievalSupportParameter</CODE>
	 *            Designating any instance other than <CODE>RetrievalSupportParameter</CODE> throws ScheduleException.
	 * @return Array of the <CODE>RetrievalSupportParameter</CODE> instance with search result. <BR>
	 *         If no corresponding record is found, return the array with number of elements equal to 0.  <BR>
	 *         Return null when input condition error occurs.  <BR>
	 *         Returning null sets  <CODE>getMessage()</CODE>. 
	 *         Allo the method to obtain the error content as a message. <BR>
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking. 
	 */
	public Parameter[] query(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		RetrievalSupportParameter rsParam = (RetrievalSupportParameter) searchParam;
		RetrievalSupportParameter[] resultParam = null;

		if ((rsParam == null) || (rsParam.equals("")))
		{
			wMessage = "6003007";
			return null;
		}

		RetrievalPlanSearchKey sKey = new RetrievalPlanSearchKey();
		RetrievalPlanSearchKey nameKey = new RetrievalPlanSearchKey();

		//#CM723965
		// Set the input parameter for the Search Key. 
		//#CM723966
		// Consignor Code
		if (!StringUtil.isBlank(rsParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rsParam.getConsignorCode());
			nameKey.setConsignorCode(rsParam.getConsignorCode());
		}

		//#CM723967
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rsParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rsParam.getRetrievalPlanDate());
			nameKey.setPlanDate(rsParam.getRetrievalPlanDate());
		}
		else
		{ 
		    //#CM723968
		    // Disable to use it in the current screen. Enable to use it in the different screen if changed. 
			//#CM723969
			// Start Planned Picking Date
			if (!StringUtil.isBlank(rsParam.getFromRetrievalPlanDate()))
			{
				//#CM723970
				// Set the Start Planned Picking Date. 
				sKey.setPlanDate(rsParam.getFromRetrievalPlanDate(), ">=");
				nameKey.setPlanDate(rsParam.getFromRetrievalPlanDate(), ">=");
			}
			if (!StringUtil.isBlank(rsParam.getToRetrievalPlanDate()))
			{
				//#CM723971
				// End Planned Picking Date
				sKey.setPlanDate(rsParam.getToRetrievalPlanDate(), "<=");
				nameKey.setPlanDate(rsParam.getToRetrievalPlanDate(), "<=");
			}
		}

		//#CM723972
		// Customer Code
		if (!StringUtil.isBlank(rsParam.getCustomerCode()))
		{
			sKey.setCustomerCode(rsParam.getCustomerCode());
			nameKey.setCustomerCode(rsParam.getCustomerCode());
		}

		//#CM723973
		// Case Order No.
		if (!StringUtil.isBlank(rsParam.getCaseOrderNo()))
		{
			sKey.setCaseOrderNo(rsParam.getCaseOrderNo());
			nameKey.setCaseOrderNo(rsParam.getCaseOrderNo());
		}

		//#CM723974
		// Piece Order No.
		if (!StringUtil.isBlank(rsParam.getPieceOrderNo()))
		{
			sKey.setPieceOrderNo(rsParam.getPieceOrderNo());
			nameKey.setPieceOrderNo(rsParam.getPieceOrderNo());
		}

		//#CM723975
		// If input of Case Order or Piece Order is omitted:
		if (StringUtil.isBlank(rsParam.getCaseOrderNo()) && StringUtil.isBlank(rsParam.getPieceOrderNo()))
		{
			//#CM723976
			// Data with input value in either of Order No. options (Piece / Case) 
			sKey.setPieceOrderNo("", "IS NOT NULL", "(", "", "OR");
			sKey.setCaseOrderNo("", "IS NOT NULL", "", ")", "AND");
			nameKey.setPieceOrderNo("", "IS NOT NULL", "(", "", "OR");
			nameKey.setCaseOrderNo("", "IS NOT NULL", "", ")", "AND");
		}

		//#CM723977
		// Status flag
		if (!StringUtil.isBlank(rsParam.getWorkStatus()))
		{
			if (rsParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM723978
				// Standby 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
				nameKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_UNSTART);
			}
			else if (rsParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
			{
				//#CM723979
				// Start
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_START);
				nameKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_START);
			}
			else if (rsParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM723980
				// Working 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
				nameKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_NOWWORKING);
			}
			else if (rsParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				//#CM723981
				// Partially Completed 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
				nameKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART);
			}
			else if (rsParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM723982
				// Completed 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
				nameKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_COMPLETION);
			}
			else if (rsParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
			{
				//#CM723983
				// All 
				sKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "<>");
				nameKey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "<>");
			}
		}

		//#CM723984
		// Set the COLLECT conditions. 
		//#CM723985
		// Consignor Code
		sKey.setConsignorCodeCollect("");
		//#CM723986
		// Consignor Name
		sKey.setConsignorNameCollect("");
		//#CM723987
		// Planned Picking Date
		sKey.setPlanDateCollect("");
		//#CM723988
		// Case Order No.
		sKey.setCaseOrderNoCollect("");
		//#CM723989
		// Piece Order No.
		sKey.setPieceOrderNoCollect("");
		//#CM723990
		// Customer Code
		sKey.setCustomerCodeCollect("");
		//#CM723991
		// Customer Name 
		sKey.setCustomerName1Collect("");
		//#CM723992
		// Item Code
		sKey.setItemCodeCollect("");
		//#CM723993
		// Item Name
		sKey.setItemName1Collect("");
		//#CM723994
		// Division 
		sKey.setCasePieceFlagCollect("");
		//#CM723995
		// Packed Qty per Case
		sKey.setEnteringQtyCollect("");
		//#CM723996
		// Packed qty per bundle 
		sKey.setBundleEnteringQtyCollect("");
		//#CM723997
		// Planned qty 
		sKey.setPlanQtyCollect("");
		//#CM723998
		// Result qty
		sKey.setResultQtyCollect("");
		//#CM723999
		// Picking Case Location 
		sKey.setCaseLocationCollect("");
		//#CM724000
		// Picking Piece Location 
		sKey.setPieceLocationCollect("");
		//#CM724001
		// Case ITF
		sKey.setItfCollect("");
		//#CM724002
		// Bundle ITF
		sKey.setBundleItfCollect("");
		//#CM724003
		// status 
		sKey.setStatusFlagCollect("");
		//#CM724004
		// Added date 
		sKey.setRegistDateCollect("");

		//#CM724005
		// Set the ORDER BY condition. 
		sKey.setPlanDateOrder(1, true);
		sKey.setCustomerCodeOrder(2, true);
		sKey.setCaseOrderNoOrder(3, true);
		sKey.setPieceOrderNoOrder(4, true);
		sKey.setItemCodeOrder(5, true);
		sKey.setCasePieceFlagOrder(6, true);
		sKey.setCaseLocationOrder(7, true);
		sKey.setPieceLocationOrder(8, true);

		RetrievalPlanHandler rHandler = new RetrievalPlanHandler(conn);
		//#CM724006
		// Obtain the count of data to be displayed. 
		
		if(!canLowerDisplay(rHandler.count(sKey)))
		{
			return returnNoDisplayParameter();
		}
		
		RetrievalPlan[] retrievalPlan = (RetrievalPlan[]) rHandler.find(sKey);
		if ((retrievalPlan != null) && (retrievalPlan.length != 0))
		{
			resultParam = (RetrievalSupportParameter[]) this.convertToRetrievalSupportParams(retrievalPlan);
			
			//#CM724007
			// Obtain the Consignor Name.
			String consignorName = "";
			nameKey.setConsignorNameCollect("");
			nameKey.setRegistDateOrder(1, false);

			RetrievalPlanReportFinder consignorFinder = new RetrievalPlanReportFinder(conn);
			consignorFinder.open();
			int nameCount = ((RetrievalPlanReportFinder) consignorFinder).search(nameKey);
			if (nameCount > 0 && nameCount <= DatabaseFinder.MAXDISP)
			{
				RetrievalPlan rplan[] = (RetrievalPlan[]) ((RetrievalPlanReportFinder) consignorFinder).getEntities(1);

				if (rplan != null && rplan.length != 0)
				{
					consignorName = rplan[0].getConsignorName();
				}
			}
			consignorFinder.close();

			//#CM724008
			// Set the Consignor Code and the Consignor Name. 
			resultParam[0].setConsignorCode(rsParam.getConsignorCode());
			resultParam[0].setConsignorName(consignorName);
			
			wMessage = "6001013";
		}
		else
		{
			wMessage = "6003011";
			return null;
		}

		return resultParam;
		
	}
	
	//#CM724009
	// Package methods -----------------------------------------------

	//#CM724010
	// Protected methods ---------------------------------------------

	//#CM724011
	// Private methods -----------------------------------------------
	//#CM724012
	/**
	 * Allow this method to set Picking Plan Entity for the parameter.  <BR>
	 * If there are two or more data of Consignor names in the data with the same Consignor Code, set a new Consignor Code based on the Added date.  <BR>
	 * As for Planned Case Qty and Planned Piece Qty of data with division Case or None, set values classified into  Case and Piece.  <BR>
	 * Set all qty to Piece, when Case/Piece division is set to Piece  <BR>
	 * 
	 * @param ety Shipping Plan Entity 
	 * @return Array of Parameter object
	 */
	private Parameter[] convertToRetrievalSupportParams(Entity[] ety)
	{
		RetrievalSupportParameter[] rtParam = null;
		RetrievalPlan[] retrievalPlan = (RetrievalPlan[]) ety;
		
		if ((retrievalPlan != null) && (retrievalPlan.length != 0))
		{
			rtParam = new RetrievalSupportParameter[retrievalPlan.length];
			
			for (int i = 0; i < retrievalPlan.length; i++)
			{
				rtParam[i] = new RetrievalSupportParameter();

				//#CM724013
				// Planned Picking Date				
				rtParam[i].setRetrievalPlanDate(retrievalPlan[i].getPlanDate());
				
				//#CM724014
				// Case Order No.
				rtParam[i].setCaseOrderNo(retrievalPlan[i].getCaseOrderNo());
				
				//#CM724015
				// Piece Order No.
				rtParam[i].setPieceOrderNo(retrievalPlan[i].getPieceOrderNo());
				
				//#CM724016
				// Customer Code
				rtParam[i].setCustomerCode(retrievalPlan[i].getCustomerCode());
				
				//#CM724017
				// Customer Name 
				rtParam[i].setCustomerName(retrievalPlan[i].getCustomerName1());
				
				//#CM724018
				// Item Code
				rtParam[i].setItemCode(retrievalPlan[i].getItemCode());
				
				//#CM724019
				// Item Name
				rtParam[i].setItemName(retrievalPlan[i].getItemName1());
				
				//#CM724020
				// Division 
				//#CM724021
				// Case 
				rtParam[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(retrievalPlan[i].getCasePieceFlag()));
				

				//#CM724022
				// Packed Qty per Case
				rtParam[i].setEnteringQty(retrievalPlan[i].getEnteringQty());
				
			    //#CM724023
			    // Host planned Case qty 
			    rtParam[i].setPlanCaseQty(DisplayUtil.getCaseQty(retrievalPlan[i].getPlanQty(), retrievalPlan[i].getEnteringQty(),retrievalPlan[i].getCasePieceFlag()));
			    //#CM724024
			    // Host Planned Piece Qty
			    rtParam[i].setPlanPieceQty(DisplayUtil.getPieceQty(retrievalPlan[i].getPlanQty(), retrievalPlan[i].getEnteringQty(),retrievalPlan[i].getCasePieceFlag()));
			    //#CM724025
			    // Result Case Qty 
			    rtParam[i].setResultCaseQty(DisplayUtil.getCaseQty(retrievalPlan[i].getResultQty(), retrievalPlan[i].getEnteringQty(),retrievalPlan[i].getCasePieceFlag()));
			    //#CM724026
			    // Result Piece Qty 
			    rtParam[i].setResultPieceQty(DisplayUtil.getPieceQty(retrievalPlan[i].getResultQty(), retrievalPlan[i].getEnteringQty(),retrievalPlan[i].getCasePieceFlag()));
				
				//#CM724027
				// Packed qty per piece 
				rtParam[i].setBundleEnteringQty(retrievalPlan[i].getBundleEnteringQty());

				//#CM724028
				// Case Picking Location
				rtParam[i].setCaseLocation(retrievalPlan[i].getCaseLocation());
				
				//#CM724029
				// Piece Picking Location 
				rtParam[i].setPieceLocation(retrievalPlan[i].getPieceLocation());
				
				//#CM724030
				// Case ITF
				rtParam[i].setITF(retrievalPlan[i].getItf());
				
				//#CM724031
				// Piece ITF 
				rtParam[i].setBundleITF(retrievalPlan[i].getBundleItf());
				

				//#CM724032
				// status 
				//#CM724033
				// For a logic that displays status 
				//#CM724034
				// with the same Order No. and  
				//#CM724035
				// with Case division Working and with Piece division Started,
				//#CM724036
				// regard its status as "Working". 
				if (!StringUtil.isBlank(retrievalPlan[i].getStatusFlag()))
				{
					rtParam[i].setWorkStatusName(DisplayUtil.getRetrievalPlanStatusValue(retrievalPlan[i].getStatusFlag()));
				}
			}
		}

		return rtParam;
	}

}
//#CM724037
//end of class
