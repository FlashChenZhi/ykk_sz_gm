package jp.co.daifuku.wms.retrieval.schedule;

import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM723117
/*
 * Created on 2004/11/08
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM723118
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * Allow this class to execute the process for inquiring the Item Picking Plan. <BR>
 * Receive the contents entered via screen as a parameter, and execute the process to inquire the Item Picking Plan. <BR>
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
 *   Case Order No.: NULL  <BR>
 *   Piece Order No.: NULL  <BR>
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
 *   Case Order No.: NULL  <BR>
 *   Piece Order No.: NULL  <BR>
 *   <BR>
 *   [Parameter]  *Mandatory Input<BR>
 *   <BR>
 *   Consignor Code* <BR>
 *   Start Planned Picking Date <BR>
 *   End Planned Picking Date <BR>
 *   Item Code <BR>
 *   Work status:<BR>
 *   <BR>
 *   [Returned data]  <BR>
 *   <BR>
 *   Consignor Code <BR>
 *   Consignor Name <BR>
 *   Planned Picking Date <BR>
 * 	 Item Code <BR>
 *   Item Name <BR>
 *   Division  <BR>
 *   Case Picking Location <BR>
 *   Piece Picking Location  <BR>
 *   Packed Qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Host planned Case qty  <BR>
 *   Host Planned Piece Qty <BR>
 *   Result Case Qty  <BR>
 *   Result Piece Qty  <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   Status name  <BR>
 *   Added date <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/27</TD><TD>K.Toda</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author: suresh kayamboo
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:54 $
 */
public class RetrievalItemPlanInquirySCH extends AbstractRetrievalSCH
{

	//#CM723119
	//Constants----------------------------------------------------

	//#CM723120
	//Attributes---------------------------------------------------
	//#CM723121
	//private Connection wConn;

	//#CM723122
	//Static-------------------------------------------------------

	//#CM723123
	//Constructors-------------------------------------------------
	//#CM723124
	/**
	 * Initialize this class. 
	 */
	public RetrievalItemPlanInquirySCH()
	{
		wMessage = null;
	}

	//#CM723125
	//Public-------------------------------------------------------
	//#CM723126
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:54 $");
	}

	//#CM723127
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
		//#CM723128
		// Any status other than "Deleted" 
		sKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "<>");

		//#CM723129
		// Any value other than Order as this is an inquiry for Item Picking Plan 
		sKey.setCaseOrderNo("", "IS NULL");
		sKey.setPieceOrderNo("", "IS NULL");
		//#CM723130
		// Set the COLLECT clause. 
		sKey.setConsignorCodeCollect("");
		//#CM723131
		// Set the GROUP BY clause. 
		sKey.setConsignorCodeGroup(1);

		RetrievalPlanHandler rpHandler = new RetrievalPlanHandler(conn);

		if (rpHandler.count(sKey) == 1)
		{
			RetrievalPlan[] result = (RetrievalPlan[]) rpHandler.find(sKey);
			param = new RetrievalSupportParameter();
			param.setConsignorCode(result[0].getConsignorCode());
		}
		return param;
	}

	//#CM723132
	/**
	 * Allow this method to receive the input parameter from the business class and  <BR>
	 * execute the find method of RetrievalPlanHandler class.  <BR>
	 * Set the result data for the RetrievalSupportParameter and return it.  <BR>
	 * 
	 * @param conn Instance to maintain database connection. 
	 * @param searchParam
	 *            Instance of <CODE>RetrievalSupportParameter</CODE>
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
		RetrievalSupportParameter sParam = (RetrievalSupportParameter) searchParam;

		if ((sParam == null) || sParam.equals(""))
		{
			wMessage = "6003001";
			return null;
		}

		RetrievalPlanSearchKey sKey = new RetrievalPlanSearchKey();
		RetrievalPlanSearchKey nameKey = new RetrievalPlanSearchKey();

		if (!StringUtil.isBlank(sParam.getConsignorCode()))
		{
			//#CM723133
			// Consignor Code
			sKey.setConsignorCode(sParam.getConsignorCode());
			nameKey.setConsignorCode(sParam.getConsignorCode());
		}
		else
		{
			wMessage = "6023004";
			return null;
		}
		if (!StringUtil.isBlank(sParam.getFromRetrievalPlanDate()))
		{
			//#CM723134
			// Start Planned Picking Date
			sKey.setPlanDate(sParam.getFromRetrievalPlanDate(), ">=");
			nameKey.setPlanDate(sParam.getFromRetrievalPlanDate(), ">=");
		}
		if (!StringUtil.isBlank(sParam.getToRetrievalPlanDate()))
		{
			//#CM723135
			// End Planned Picking Date
			sKey.setPlanDate(sParam.getToRetrievalPlanDate(), "<=");
			nameKey.setPlanDate(sParam.getToRetrievalPlanDate(), "<=");
		}
		if (!StringUtil.isBlank(sParam.getItemCode()))
		{
			//#CM723136
			// Item Code
			sKey.setItemCode(sParam.getItemCode());
			nameKey.setItemCode(sParam.getItemCode());
		}

		//#CM723137
		// Status flag
		if (!StringUtil.isBlank(sParam.getWorkStatus()))
		{
			if (sParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
			{
				//#CM723138
				// Standby 
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
				nameKey.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
			}
			else if (sParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
			{
				//#CM723139
				// Start
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_START);
				nameKey.setStatusFlag(SystemDefine.STATUS_FLAG_START);
			}
			else if (sParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
			{
				//#CM723140
				// Working 
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
				nameKey.setStatusFlag(SystemDefine.STATUS_FLAG_NOWWORKING);
			}
			else if (sParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
			{
				//#CM723141
				// Partially Completed 
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETE_IN_PART);
				nameKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETE_IN_PART);
			}
			else if (sParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
			{
				//#CM723142
				// Completed 
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
				nameKey.setStatusFlag(SystemDefine.STATUS_FLAG_COMPLETION);
			}
			else if (sParam.getWorkStatus().equals(RetrievalSupportParameter.STATUS_FLAG_ALL))
			{
				//#CM723143
				// All 
				sKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "<>");
				nameKey.setStatusFlag(SystemDefine.STATUS_FLAG_DELETE, "<>");
			}
		}

		//#CM723144
		// Any value other than Order as this is an inquiry for Item Picking Plan 
		sKey.setCaseOrderNo("", "IS NULL");
		sKey.setPieceOrderNo("", "IS NULL");
		nameKey.setCaseOrderNo("", "IS NULL");
		nameKey.setPieceOrderNo("", "IS NULL");
		//#CM723145
		// Set the content to be displayed. 
		//#CM723146
		// Consignor Code
		sKey.setConsignorCodeCollect("");
		//#CM723147
		// Consignor Name
		sKey.setConsignorNameCollect("");
		//#CM723148
		// Planned Picking Date
		sKey.setPlanDateCollect("");
		//#CM723149
		// Item Code
		sKey.setItemCodeCollect("");
		//#CM723150
		// Item Name
		sKey.setItemName1Collect("");
		//#CM723151
		// Case/Piece division 
		sKey.setCasePieceFlagCollect("");
		//#CM723152
		// Case Picking Location
		sKey.setCaseLocationCollect("");
		//#CM723153
		// Piece Picking Location 
		sKey.setPieceLocationCollect("");
		//#CM723154
		// Packed Qty per Case
		sKey.setEnteringQtyCollect("");
		//#CM723155
		// Packed qty per bundle
		sKey.setBundleEnteringQtyCollect("");
		//#CM723156
		// Work Planned qty 
		sKey.setPlanQtyCollect("");
		//#CM723157
		// Result qty
		sKey.setResultQtyCollect("");
		//#CM723158
		// Case ITF
		sKey.setItfCollect("");
		//#CM723159
		// Bundle ITF
		sKey.setBundleItfCollect("");
		//#CM723160
		// Status flag
		sKey.setStatusFlagCollect("");
		//#CM723161
		// Added date 
		sKey.setRegistDateCollect("");

		//#CM723162
		// Set the Order By clause. 
		sKey.setPlanDateOrder(1, true);
		sKey.setItemCodeOrder(2, true);
		sKey.setCasePieceFlagOrder(3, true);
		sKey.setCaseLocationOrder(4, true);
		sKey.setPieceLocationOrder(5, true);

		RetrievalPlanHandler rHandler = new RetrievalPlanHandler(conn);
		if(!canLowerDisplay(rHandler.count(sKey)))
		{
			return returnNoDisplayParameter();
		}

		RetrievalPlan[] resultEntity = (RetrievalPlan[]) rHandler.find(sKey);
		RetrievalSupportParameter[] dispData = null;

		if ((resultEntity == null) || (resultEntity.length == 0))
		{
			wMessage = "6003011";
			return null;
		}

		//#CM723163
		// Set the entity for the parameter. 
		dispData = (RetrievalSupportParameter[]) this.setRetrievalSupportParameter(resultEntity);

		//#CM723164
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

		//#CM723165
		// Set the Consignor Code and the Consignor Name. 
		dispData[0].setConsignorCode(sParam.getConsignorCode());
		dispData[0].setConsignorName(consignorName);
			

		wMessage = "6001013";
		return dispData;
	}

	//#CM723166
	// Package methods -----------------------------------------------

	//#CM723167
	// Protected methods ---------------------------------------------
	//#CM723168
	/**
	 * Obtain the Date in the Date format and return it in the form of string. 
	 * 
	 * @param date Date in Date type 
	 * @return String (in yyyyMMddHHmmss format) 
	 */
	protected String getDateValue(Date date)
	{
		String datNumS = null;

		if (date != null)
		{
			//#CM723169
			// Generate pattern according to 24 hours. 
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			datNumS = sdf.format(date).trim();
		}

		return datNumS;
	}
	
	//#CM723170
	// Private methods -----------------------------------------------
	//#CM723171
	/**
	 * Allow this method to set ResultView entity for the parameter.  <BR>
	 * 
	 * @param resultEntity Array of RetrievalPlan object that contains info to be displayed on screen.
	 * @return Array of parameter object that contains info to be displayed on screen.
	 */
	private Parameter[] setRetrievalSupportParameter(RetrievalPlan[] resultEntity)
	{
		RetrievalSupportParameter[] dispData = new RetrievalSupportParameter[resultEntity.length];

		//#CM723172
		// Set Added date to "1". 
		String registDate = "1";
		for (int i = 0; i < resultEntity.length; i++)
		{
			dispData[i] = new RetrievalSupportParameter();

			if ((i == 0) || (Long.parseLong(registDate) < Long.parseLong(getDateValue(resultEntity[i].getRegistDate()))))
			{
				registDate = getDateValue(resultEntity[i].getRegistDate());
				dispData[0].setConsignorCode(resultEntity[i].getConsignorCode()); //荷主コード
				dispData[0].setConsignorName(resultEntity[i].getConsignorName()); //荷主名
			}

			//#CM723173
			// Planned Picking Date
			dispData[i].setRetrievalPlanDate(resultEntity[i].getPlanDate());
			
			//#CM723174
			// Item Code
			dispData[i].setItemCode(resultEntity[i].getItemCode());
			//#CM723175
			// Item Name
			dispData[i].setItemName(resultEntity[i].getItemName1());
			//#CM723176
			// Case/Piece division 
			if (!StringUtil.isBlank(resultEntity[i].getCasePieceFlag()))
			{
				if (resultEntity[i].getCasePieceFlag().equals(SystemDefine.CASEPIECE_FLAG_CASE))
				{
					//#CM723177
					// Case division 
					dispData[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(SystemDefine.CASEPIECE_FLAG_CASE));
				}
				else if (resultEntity[i].getCasePieceFlag().equals(SystemDefine.CASEPIECE_FLAG_PIECE))
				{
					//#CM723178
					// Piece division 
					dispData[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(SystemDefine.CASEPIECE_FLAG_PIECE));
				}
				else if (resultEntity[i].getCasePieceFlag().equals(SystemDefine.CASEPIECE_FLAG_NOTHING))
				{
					System.out.println(resultEntity[i].getCasePieceFlag());
					//#CM723179
					// None 
					dispData[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(SystemDefine.CASEPIECE_FLAG_NOTHING));
				}
				else if (resultEntity[i].getCasePieceFlag().equals(SystemDefine.CASEPIECE_FLAG_MIX))
				{
					//#CM723180
					// Case/Piece 
					dispData[i].setCasePieceflgName(DisplayUtil.getPieceCaseValue(SystemDefine.CASEPIECE_FLAG_MIX));
				}

			}

			//#CM723181
			// Case Picking Location
			dispData[i].setCaseLocation(resultEntity[i].getCaseLocation());
			//#CM723182
			// Piece Picking Location 
			dispData[i].setPieceLocation(resultEntity[i].getPieceLocation());
			//#CM723183
			// Packed Qty per Case
			dispData[i].setEnteringQty(resultEntity[i].getEnteringQty());

			if (resultEntity[i].getEnteringQty() >= 0)
			{
				
					//#CM723184
					// Planned Case Qty 
					dispData[i].setPlanCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getPlanQty(), resultEntity[i].getEnteringQty(),resultEntity[i].getCasePieceFlag()));
					//#CM723185
					// Planned Piece Qty 
					dispData[i].setPlanPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getPlanQty(), resultEntity[i].getEnteringQty(),resultEntity[i].getCasePieceFlag()));
					//#CM723186
					// Result Case Qty 
					dispData[i].setResultCaseQty(DisplayUtil.getCaseQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty(),resultEntity[i].getCasePieceFlag()));
					//#CM723187
					// Result Piece Qty 
					dispData[i].setResultPieceQty(DisplayUtil.getPieceQty(resultEntity[i].getResultQty(), resultEntity[i].getEnteringQty(),resultEntity[i].getCasePieceFlag()));				
			}
			else
			{
				//#CM723188
				// Planned Case Qty 
				dispData[i].setPlanCaseQty(0);
				//#CM723189
				// Planned Piece Qty 
				dispData[i].setPlanPieceQty(0);
				//#CM723190
				// Result Case Qty 
				dispData[i].setResultCaseQty(0);
				//#CM723191
				// Result Piece Qty 
				dispData[i].setResultPieceQty(0);
				//#CM723192
				// Shortage Case Qty
				dispData[i].setShortageCaseQty(0);
				//#CM723193
				// Shortage Piece Qty
				dispData[i].setShortagePieceQty(0);
			}
			//#CM723194
			// Packed qty per bundle
			dispData[i].setBundleEnteringQty(resultEntity[i].getBundleEnteringQty());

			//#CM723195
			// Case ITF
			dispData[i].setITF(resultEntity[i].getItf());
			
			//#CM723196
			// Bundle ITF
			dispData[i].setBundleITF(resultEntity[i].getBundleItf());
			

			//#CM723197
			// Status flag
			if (!StringUtil.isBlank(resultEntity[i].getStatusFlag()))
			{
				if (resultEntity[i].getStatusFlag().equals(SystemDefine.STATUS_FLAG_UNSTART))
				{
					//#CM723198
					// Standby 
					dispData[i].setWorkStatusName(DisplayUtil.getRetrievalPlanStatusValue(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED));
				}
				else if (resultEntity[i].getStatusFlag().equals(SystemDefine.STATUS_FLAG_START))
				{
					//#CM723199
					// Start
					dispData[i].setWorkStatusName(DisplayUtil.getRetrievalPlanStatusValue(RetrievalSupportParameter.STATUS_FLAG_STARTED));
				}
				else if (resultEntity[i].getStatusFlag().equals(SystemDefine.STATUS_FLAG_NOWWORKING))
				{
					//#CM723200
					// Working 
					dispData[i].setWorkStatusName(DisplayUtil.getRetrievalPlanStatusValue(RetrievalSupportParameter.STATUS_FLAG_WORKING));
				}
				else if (resultEntity[i].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETE_IN_PART))
				{
					//#CM723201
					// Partially Completed 
					dispData[i].setWorkStatusName(DisplayUtil.getRetrievalPlanStatusValue(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION));
				}
				else if (resultEntity[i].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION))
				{
					//#CM723202
					// Completed 
					dispData[i].setWorkStatusName(DisplayUtil.getRetrievalPlanStatusValue(RetrievalSupportParameter.STATUS_FLAG_COMPLETION));
				}
			}

		}

		return dispData;
	}

}
