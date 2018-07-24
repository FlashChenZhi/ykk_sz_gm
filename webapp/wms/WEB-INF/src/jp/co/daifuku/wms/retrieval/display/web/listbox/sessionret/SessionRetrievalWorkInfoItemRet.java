package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM713231
/*
 * Created on 2004/10/07
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
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM713232
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data for the listbox of Item list (Picking Work). <BR>
 * Receive the search conditions as a parameter, and search through Item list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalWorkInfoItemRet(Connection conn,RetrievalSupportParameter rtParam)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter rtParam)</CODE> method and search for Work Status Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Work type: Picking(03)*<BR>
 *     Consignor Code<BR>
 *     Planned Picking Date<BR>
 *     Start Planned Picking Date<BR>
 *     End Planned Picking Date<BR>
 *     Item Code<BR>
 *    Work status:<BR>
 *     Item / Order flag <BR>
 *     System identification key <BR>
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
 *     Item Code<BR>
 *     Item Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor>
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/28</TD>
 * <TD>Muneendra</TD>
 * <TD>New</TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:09 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalWorkInfoItemRet extends SessionRet
{
	//#CM713233
	// Class fields --------------------------------------------------

	//#CM713234
	// Class variables -----------------------------------------------

	//#CM713235
	// Class method --------------------------------------------------
	//#CM713236
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:09 $");
	}

	//#CM713237
	// Constructors --------------------------------------------------
	//#CM713238
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalWorkInfoItemRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}

	//#CM713239
	// Public methods ------------------------------------------------
	//#CM713240
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
		WorkingInformation[] temp = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				temp = (WorkingInformation[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (RetrievalSupportParameter[]) convertToStorageSupportParams(temp);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM713241
	// Package methods -----------------------------------------------

	//#CM713242
	// Protected methods ---------------------------------------------

	//#CM713243
	// Private methods -----------------------------------------------
	//#CM713244
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>WorkingInformationFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		WorkingInformationSearchKey sKey = new WorkingInformationSearchKey();
		sKey.setJobType(SystemDefine.JOB_TYPE_RETRIEVAL);

		//#CM713245
		// Set the search conditions. 
		//#CM713246
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}
		//#CM713247
		// If the Planned Picking Date is set for: 
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rtParam.getRetrievalPlanDate());
		}
		else
		{
			//#CM713248
			//Start Planned Picking Date
			if (!StringUtil.isBlank(rtParam.getFromRetrievalPlanDate()))
			{
				//#CM713249
				//Set the Start Planned Picking Date. 
				sKey.setPlanDate(rtParam.getFromRetrievalPlanDate(), ">=");
			}
			if (!StringUtil.isBlank(rtParam.getToRetrievalPlanDate()))
			{
				//#CM713250
				//End Planned Picking Date
				sKey.setPlanDate(rtParam.getToRetrievalPlanDate(), "<=");
			}
		}
		//#CM713251
		// Set the Item Code. 
		if (!StringUtil.isBlank(rtParam.getItemCode()))
		{
			sKey.setItemCode(rtParam.getItemCode());
		}
		else
		{
			sKey.setItemCode("", "IS NOT NULL");
		}
		if (rtParam.getSearchStatus() != null && rtParam.getSearchStatus().length > 0)
		{
			String[] search = new String[rtParam.getSearchStatus().length];
			for (int i = 0; i < rtParam.getSearchStatus().length; ++i)
			{
				if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_UNSTART;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = WorkingInformation.STATUS_FLAG_START;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = WorkingInformation.STATUS_FLAG_NOWWORKING;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (rtParam.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = WorkingInformation.STATUS_FLAG_COMPLETION;
				}
				else
				{
					search[i] = "*";
				}
			}
			sKey.setStatusFlag(search);
		}
		else
		{
			sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		}

		//#CM713252
		// Distinquish between Item and Order. 
		if (!StringUtil.isBlank(rtParam.getItemOrderFlag()))
		{
			//#CM713253
			// Item 
			if (rtParam.getItemOrderFlag().equals(RetrievalSupportParameter.ITEMORDERFLAG_ITEM))
			{
				sKey.setOrderNo("", "");
			}
			//#CM713254
			// Order 
			else
			{
				sKey.setOrderNo("", "IS NOT NULL");
			}
		}
		//#CM713255
		// If the area type is other than ASRS Work, search for works excluding ASRS Work and also Standby data.
		if (RetrievalSupportParameter.AREA_TYPE_FLAG_NOASRS.equals(rtParam.getAreaTypeFlag()))
		{
		    sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");
		    sKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		}

		//#CM713256
		// For data with the allocation flag Allocated, search for the allocated data only. 
		if (RetrievalSupportParameter.ALLOCATION_COMPLETION.equals(rtParam.getAllocationFlag()))
		{
			//#CM713257
			// System identification key: any key other than AS/RS(2) and Status flag: Standby(0),  OR
			sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=", "((", "", "AND");
			sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "", ")", "OR");
			//#CM713258
			// System identification key: AS/RS(2) and Status flag: Working. (2) 
			sKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "=", "(", "", "AND");
			sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "", "))", "AND");
		}

		//#CM713259
		//Set the Conditions for obtaining data. 
		//#CM713260
		//Item Code
		sKey.setItemCodeCollect("");
		//#CM713261
		//Item Name
		sKey.setItemName1Collect("");

		//#CM713262
		//Grouping condition 
		//#CM713263
		//Item Code
		sKey.setItemCodeGroup(1);
		//#CM713264
		//Item Name
		sKey.setItemName1Group(2);

		//#CM713265
		// Set the sorting order. 
		sKey.setItemCodeOrder(1, true);
		sKey.setItemName1Order(2, true);

		wFinder = new WorkingInformationFinder(wConn);
		//#CM713266
		// Open cursor 
		wFinder.open();
		int count = wFinder.search(sKey);
		//#CM713267
		//Set count for  wLength. 
		wLength = count;
		wCurrent = 0;

	}

	//#CM713268
	/**
	 * Allow this class to set the Workinginformation Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param ety Work Status Info
	 * @return Parameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Work Status.
	 */
	private Parameter[] convertToStorageSupportParams(Entity[] ety)
	{
		RetrievalSupportParameter[] rtParam = null;
		WorkingInformation[] workInfo = (WorkingInformation[]) ety;
		if ((workInfo != null) && (workInfo.length != 0))
		{
			rtParam = new RetrievalSupportParameter[workInfo.length];
			for (int i = 0; i < workInfo.length; i++)
			{
				rtParam[i] = new RetrievalSupportParameter();
				if (!StringUtil.isBlank(workInfo[i].getItemCode()))
				{
					rtParam[i].setItemCode(workInfo[i].getItemCode()); //商品コード
				}
				if (!StringUtil.isBlank(workInfo[i].getItemName1()))
				{
					rtParam[i].setItemName(workInfo[i].getItemName1()); //商品名
				}
			}
		}
		return rtParam;

	}

}
//#CM713269
//end of class
