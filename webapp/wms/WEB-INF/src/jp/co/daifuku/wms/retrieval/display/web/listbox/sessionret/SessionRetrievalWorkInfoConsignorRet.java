package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM713167
/*
 * Created on Oct 5, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM713168
/** 
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Consignor list (Picking Work). <BR>
 * Receive the search conditions as a parameter, and search through Consignor list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalWorkInfoConsignorRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Work Status Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Work type: Picking(03)*<BR>
 *     Planned Picking Date<BR>
 *     Consignor Code<BR>
 *    Work status:<BR>
 *     System identification key <BR>
 *     Start Work flag<BR>
 *     Item / Order flag <BR>
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
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/28</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:08 $
 * @author  $Author: suresh $
 */

public class SessionRetrievalWorkInfoConsignorRet extends SessionRet
{
	//#CM713169
	// Class fields --------------------------------------------------

	//#CM713170
	// Class variables -----------------------------------------------

	//#CM713171
	// Class method --------------------------------------------------
	//#CM713172
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:08 $");
	}

	//#CM713173
	// Constructors --------------------------------------------------
	//#CM713174
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalWorkInfoConsignorRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM713175
	// Public methods ------------------------------------------------
	//#CM713176
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

	//#CM713177
	// Package methods -----------------------------------------------

	//#CM713178
	// Protected methods ---------------------------------------------

	//#CM713179
	// Private methods -----------------------------------------------
	//#CM713180
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>WorkingInformationFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 */
	private void find(RetrievalSupportParameter param) throws Exception
	{
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);

		//#CM713181
		// Set the search conditions.
		//#CM713182
		// Planned Picking Date
		if (!StringUtil.isBlank(param.getRetrievalPlanDate()))
		{
			skey.setPlanDate(param.getRetrievalPlanDate());
		}
		//#CM713183
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		else
		{
			skey.setConsignorCode("", "IS NOT NULL");
		}
		//#CM713184
		// Grouping condition 
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);
		//#CM713185
		// Set the Conditions for obtaining data. 
		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");

		//#CM713186
		// Set the sorting order. 
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);

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
		
		//#CM713187
		// If the Area type is other than ASRS Work, search for works other than ASRS Work and exclude also the data that evdenced the Shortage 
		if (RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS.equals(param.getAreaTypeFlag()))
		{
		    skey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		    skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		}

		//#CM713188
		// Distinquish between Item and Order. 
		if (!StringUtil.isBlank(param.getItemOrderFlag()))
		{
			//#CM713189
			// Item 
			if (param.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
			{
				skey.setOrderNo("", "");
			}
			//#CM713190
			// Order 
			else
			{
				skey.setOrderNo("", "IS NOT NULL");
			}
		}
		
		//#CM713191
		// For data with the allocation flag Allocated, search for the allocated data only. 
		if (RetrievalSupportParameter.ALLOCATION_COMPLETION.equals(param.getAllocationFlag()))
		{
			//#CM713192
			// System identification key: any key other than AS/RS(2) and Status flag: Standby(0),  OR
			skey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
			//#CM713193
			// System identification key: AS/RS(2) and Status flag: Working. (2) 
			skey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
			skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");
		}

		wFinder = new WorkingInformationFinder(wConn);
		//#CM713194
		// Open the cursor.
		wFinder.open();
		int count = ((WorkingInformationFinder) wFinder).search(skey);
		//#CM713195
		// Initialize. 
		wLength = count;
		wCurrent = 0;
	}

	//#CM713196
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
			stParam[i].setConsignorCode(workInfo[i].getConsignorCode());
			stParam[i].setConsignorName(workInfo[i].getConsignorName());

		}

		return stParam;

	}

}
//#CM713197
//end of class
