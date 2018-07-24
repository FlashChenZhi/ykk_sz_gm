package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM713322
/*
 * Created on Oct 6, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM713323
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Customer list (Picking Work). <BR>
 * Receive the Receive search conditions as a parameter and search through Customer list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalWorkInfoCustomerRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Work Status Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Work type: Picking(03)*<BR>
 * 	   Consignor Code<BR>
 * 	   Item Code<BR>
 *    Picking Location <BR>
 * 	   Planned Picking Date<BR>
 * 	   Ticket No. <BR>				
 *     Customer Code<BR>
 *     System identification key <BR>
 *   Case/Piece division<BR>
 *     Work status:<BR>
 *     Item - Order flag <BR>
 *   </DIR>
 *   <Search table>  <BR>
 *   <DIR>
 *     DNWORKINFO <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 *   1. Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the array of the Work Status Info and return it.<BR>
 * <BR>
 *   <Field items to be displayed> 
 *   <DIR>
 *     Customer Code<BR>
 *     Customer Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:09 $
 * @author  $Author: suresh $
 */

public class SessionRetrievaWorkInfoCustomerRet extends SessionRet
{
	//#CM713324
	// Class fields --------------------------------------------------

	//#CM713325
	// Class variables -----------------------------------------------

	//#CM713326
	// Class method --------------------------------------------------
	//#CM713327
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:09 $");
	}

	//#CM713328
	// Constructors --------------------------------------------------
	//#CM713329
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 *
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievaWorkInfoCustomerRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM713330
	// Public methods ------------------------------------------------
	//#CM713331
	/**
	 * Obtain the specified number of search results of the Work Status Info and return it as an array of RetrievalSupportParameter. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Work Status Info from the result set.<BR>
	 *   3.Obtain the display data from the Work Status Info, and set it in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. <BR>
	 * </DIR>
	 * 
	 * @return Array of <CODE>RetrievalSupportParameter</CODE> class that contains Work Status.
	 */
	public Parameter[] getEntities()
	{
		RetrievalSupportParameter[] resultArray = null;
		WorkingInformation temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				temp = (WorkingInformation[]) ((WorkingInformationFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToRetrievalSupportParams(temp);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM713332
	// Package methods -----------------------------------------------

	//#CM713333
	// Protected methods ---------------------------------------------

	//#CM713334
	// Private methods -----------------------------------------------
	//#CM713335
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>WorkingInformationFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter param) throws Exception
	{

		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		//#CM713336
		// Set the search conditions.
		//#CM713337
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}

		//#CM713338
		//Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			skey.setItemCode(param.getItemCode());
		}

		//#CM713339
		// Picking Location 
		if (!StringUtil.isBlank(param.getRetrievalLocation()))
		{
			skey.setLocationNo(param.getRetrievalLocation());
		}
		//#CM713340
		//Planned Picking Date
		if (!StringUtil.isBlank(param.getRetrievalPlanDate()))
		{
			skey.setPlanDate(param.getRetrievalPlanDate());
		}
		//#CM713341
		// Ticket No. 
		if (!StringUtil.isBlank(param.getShippingTicketNo()))
		{
			skey.setShippingTicketNo(param.getShippingTicketNo());
		}
		//#CM713342
		//Customer Code
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			skey.setCustomerCode(param.getCustomerCode());
		}
		else
		{
			skey.setCustomerCode("", "IS NOT NULL");
		}
		//#CM713343
		// If the area type is other than ASRS Work, search for works excluding ASRS Work 
		if (RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS.equals(param.getAreaTypeFlag()))
		{
		    skey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		}
		//#CM713344
		// Grouping condition 
		skey.setCustomerCodeGroup(1);
		skey.setCustomerName1Group(2);
		//#CM713345
		// Set the Conditions for obtaining data. 
		skey.setCustomerCodeCollect("");
		skey.setCustomerName1Collect("");

		//#CM713346
		// Set the sorting order. 
		skey.setCustomerCodeOrder(1, true);
		skey.setCustomerName1Order(2, true);

		//#CM713347
		// Case/Piece division 
		if (!StringUtil.isBlank(param.getCasePieceflg()))
		{

			if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM713348
				// Case 
				skey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
			}
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM713349
				// Piece 
				skey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
			}
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM713350
				// None 
				skey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
			}
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
			{
				//#CM713351
				// All 
			}
		}

		if (param.getSearchStatus() != null && param.getSearchStatus().length > 0)
		{
			String[] search = new String[param.getSearchStatus().length];
			for (int i = 0; i < param.getSearchStatus().length; ++i)
			{
				if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_UNSTART;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_START;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = WorkingInformation.STATUS_FLAG_NOWWORKING;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETION;
				}
				else
				{
					search[i] = "*";
				}
			}
			skey.setStatusFlag(search);
		}
		else
		{
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		}
		//#CM713352
		// Distinquish between Item and Order. 
		if (!StringUtil.isBlank(param.getItemOrderFlag()))
		{
			//#CM713353
			// Item 
			if (param.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
			{
				skey.setOrderNo("", "");
			}
			//#CM713354
			// Order 
			else
			{
				skey.setOrderNo("", "IS NOT NULL");
			}
		}
		wFinder = new WorkingInformationFinder(wConn);
		//#CM713355
		// Open the cursor.
		wFinder.open();
		int count = ((WorkingInformationFinder) wFinder).search(skey);
		//#CM713356
		// Initialize. 
		wLength = count;
		wCurrent = 0;

	}

	//#CM713357
	/**
	 * Allow this class to set the Workinginformation Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param workInfo Work Status Info
	 * @return RetrievalSupportParameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Work Status.
	 */
	private RetrievalSupportParameter[] convertToRetrievalSupportParams(WorkingInformation[] workInfo)
	{
		RetrievalSupportParameter[] stParam = null;

		if (workInfo == null || workInfo.length == 0)
		{
			return null;
		}
		stParam = new RetrievalSupportParameter[workInfo.length];
		for (int i = 0; i < workInfo.length; i++)
		{
			stParam[i] = new RetrievalSupportParameter();
			stParam[i].setCustomerCode(workInfo[i].getCustomerCode());
			stParam[i].setCustomerName(workInfo[i].getCustomerName1());

		}
		return stParam;

	}

}
//#CM713358
//end of class
