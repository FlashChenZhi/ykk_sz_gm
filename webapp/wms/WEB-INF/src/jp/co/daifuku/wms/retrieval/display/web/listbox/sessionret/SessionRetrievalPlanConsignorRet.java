package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712746
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
//#CM712747
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Consignor list (Picking Plan). <BR>
 * Receive the search conditions as a parameter, and search through Consignor list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalPlanConsignorRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *    Work status:<BR>
 *     Item - Order flag <BR>
 *     Schedule flag <BR>
 *   </DIR>
 *   <Search table>  <BR>
 *   <DIR>
 *     DNRETRIEVALPLAN <BR>
 *   </DIR>
 * </DIR>
 * 
 * 2. Process for displaying data (<CODE>getEntities()</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen. <BR>
 *   1. Obtain the display info that was obtained in the search process. <BR>
 *   Set the search result in the Picking Plan Info array and return it. <BR>
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
 * <TR><TD>2004/10/05</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:03 $
 * @author  $Author: suresh $
 */

public class SessionRetrievalPlanConsignorRet extends SessionRet
{
	//#CM712748
	// Class fields --------------------------------------------------

	//#CM712749
	// Class variables -----------------------------------------------

	//#CM712750
	// Class method --------------------------------------------------
	//#CM712751
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:03 $");
	}

	//#CM712752
	// Constructors --------------------------------------------------
	//#CM712753
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalPlanConsignorRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM712754
	// Public methods ------------------------------------------------
	//#CM712755
	/**
	 * Obtain the specified number of search results of the Picking Plan Info and return it as an array of RetrievalSupportParameter. <BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * <DIR>
	 *   1. Calculate to specify the count of display data to be obtained. <BR>
	 *   2.Obtain the Picking Plan Info from the result set.<BR>
	 *   3.Obtain the display data from the Picking Plan Info, and set it in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 *   4. Return information to be displayed. <BR>
	 * </DIR>
	 * 
	 * @return Array of <CODE>RetrievalSupportParameter</CODE> class that contains Picking Plan Info.
	 */
	public Parameter[] getEntities()
	{
		RetrievalSupportParameter[] resultArray = null;
		RetrievalPlan temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				temp = (RetrievalPlan[]) ((RetrievalPlanFinder) wFinder).getEntities(wStartpoint, wEndpoint);
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

	//#CM712756
	// Package methods -----------------------------------------------

	//#CM712757
	// Protected methods ---------------------------------------------

	//#CM712758
	// Private methods -----------------------------------------------
	//#CM712759
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 */
	private void find(RetrievalSupportParameter param) throws Exception
	{
		RetrievalPlanSearchKey skey = new RetrievalPlanSearchKey();
		//#CM712760
		// Execute searching.
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		else
		{
			skey.setConsignorCode("", "IS NOT NULL");
		}
		//#CM712761
		// Grouping condition 
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);
		//#CM712762
		// Set the Conditions for obtaining data. 
		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");

		//#CM712763
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
					search[i] = RetrievalPlan.STATUS_FLAG_UNSTART;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_START;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_NOWWORKING;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if (param.getSearchStatus()[i].equals(RetrievalSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = RetrievalPlan.STATUS_FLAG_COMPLETION;
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
			skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		}

		//#CM712764
		// Item / Order flag 
		//#CM712765
		// Division Item 
		if (RetrievalSupportParameter.ITEMORDERFLAG_ITEM.equals(param.getItemOrderFlag()))
		{
			//#CM712766
			// Data with no input value (omitted) in both Order No. options (Piece / Case) 
			skey.setPieceOrderNo("");
			skey.setCaseOrderNo("");
		}
		//#CM712767
		// Division Order 
		else if (RetrievalSupportParameter.ITEMORDERFLAG_ORDER.equals(param.getItemOrderFlag()))
		{
			//#CM712768
			// Data with input value in either of Order No. options (Piece / Case) 
			skey.setPieceOrderNo("", "IS NOT NULL", "(", "", "OR");
			skey.setCaseOrderNo("", "IS NOT NULL", "", ")", "AND");
		}
		
		//#CM712769
		// Schedule flag 
		if (param.getScheduleFlagArray() != null && param.getScheduleFlagArray().length > 0)
		{
			String[] schFlag = new String[param.getScheduleFlagArray().length];
			for (int i = 0; i < param.getScheduleFlagArray().length; i++)
			{
				if (param.getScheduleFlagArray()[i].equals(RetrievalSupportParameter.SCHEDULE_UNSTART))
				{
					schFlag[i] = RetrievalPlan.SCH_FLAG_UNSTART;
				}
				else if (param.getScheduleFlagArray()[i].equals(RetrievalSupportParameter.SCHEDULE_CASE_COMPLETION))
				{
					schFlag[i] = RetrievalPlan.SCH_FLAG_CASE_COMPLETION;
				}
				else if (param.getScheduleFlagArray()[i].equals(RetrievalSupportParameter.SCHEDULE_PIECE_COMPLETION))
				{
					schFlag[i] = RetrievalPlan.SCH_FLAG_PIECE_COMPLETION;
				}
				else if (param.getScheduleFlagArray()[i].equals(RetrievalSupportParameter.SCHEDULE_COMPLETION))
				{
					schFlag[i] = RetrievalPlan.SCH_FLAG_COMPLETION;
				}
			}
			skey.setSchFlag(schFlag);
		}

		wFinder = new RetrievalPlanFinder(wConn);
		//#CM712770
		// Open the cursor.
		wFinder.open();
		int count = ((RetrievalPlanFinder) wFinder).search(skey);
		//#CM712771
		// Initialize. 
		wLength = count;
		wCurrent = 0;

	}

	//#CM712772
	/**
	 * Allow this method to set the RetrievalPlan Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param retrievalPlan Picking Plan Info 
	 * @return RetrievalSupportParameter []  Array of <CODE>RetrievalSupportParameter</CODE> class that has set Picking Plan Info.
	 */
	private RetrievalSupportParameter[] convertToRetrievalSupportParams(RetrievalPlan[] retrievalPlan)
	{
		RetrievalSupportParameter[] stParam = null;

		if (retrievalPlan == null || retrievalPlan.length == 0)
		{
			return null;
		}
		stParam = new RetrievalSupportParameter[retrievalPlan.length];
		for (int i = 0; i < retrievalPlan.length; i++)
		{
			stParam[i] = new RetrievalSupportParameter();
			stParam[i].setConsignorCode(retrievalPlan[i].getConsignorCode());
			stParam[i].setConsignorName(retrievalPlan[i].getConsignorName());

		}
		return stParam;

	}

}
//#CM712773
//end of class
