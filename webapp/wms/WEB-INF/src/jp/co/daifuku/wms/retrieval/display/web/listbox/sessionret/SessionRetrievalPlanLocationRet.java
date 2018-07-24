package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712908
/*
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.dbhandler.RetrievalPlanSummarizeFinder;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712909
/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Picking Location list (Picking Plan). <BR>
 * Obtain Case location or Piece location based on the division, and display it in the ascending order. 
 * Receive the search conditions as a parameter, and search through Picking Location list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalPlanLocationRet(Connection conn,RetrievalSupportParameter rtParam)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter rtParam)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code<BR>
 *   Planned Picking Date<BR>
 *     Item Code<BR>
 *     Status flag other than Deleted<BR>
 *    Case/Piece division <BR>
 *    Picking Location <BR>
 *   </DIR>
 *   <Search table>  <BR>
 *   <DIR>
 *     DnRetrievalPlan <BR>
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
 *     Picking Location <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor>
 * <TD>Date</TD><TD>Name</TD><TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2004/10/09</TD><TD>Muneendra</TD><TD>New</TD>
 * <TD>2006/06/07</TD><TD>Y.Okamura</TD><TD>Search table was changed. </TD>
 * </TR>
 * </TABLE> <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:04 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalPlanLocationRet extends SessionRet
{
	//#CM712910
	// Class fields --------------------------------------------------

	//#CM712911
	// Class variables -----------------------------------------------

	//#CM712912
	// Class method --------------------------------------------------
	//#CM712913
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:04 $");
	}

	//#CM712914
	// Constructors --------------------------------------------------
	//#CM712915
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalPlanLocationRet(Connection conn, RetrievalSupportParameter rtParam) throws Exception
	{
		wConn = conn;
		find(rtParam);
	}

	//#CM712916
	// Public methods ------------------------------------------------
	//#CM712917
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
		RetrievalPlan[] plan = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				plan = (RetrievalPlan[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (RetrievalSupportParameter[]) convertToWorkInfoSupportParams(plan);

			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;

	}

	//#CM712918
	// Package methods -----------------------------------------------

	//#CM712919
	// Protected methods ---------------------------------------------

	//#CM712920
	// Private methods -----------------------------------------------
	//#CM712921
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanSummarizeFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param rtParam      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter rtParam) throws Exception
	{
		RetrievalPlanSearchKey[] planKey = null;
		
		//#CM712922
		// If Case/Piece division is designated via screen (available via Add Plan screen) 
		if (RetrievalSupportParameter.CASEPIECE_FLAG_CASE.equals(rtParam.getCasePieceflg()))
		{
			planKey = new RetrievalPlanSearchKey[1];
			planKey[0] = new RetrievalPlanSearchKey();
			
			String[] casepiece = {RetrievalPlan.CASEPIECE_FLAG_MIX, RetrievalPlan.CASEPIECE_FLAG_CASE};
			setSearchKey(planKey[0], rtParam);
			planKey[0].setCasePieceFlag(casepiece);
			planKey[0].setCaseLocation("", "IS NOT NULL");
			if (!StringUtil.isBlank(rtParam.getRetrievalLocation()))
			{
				planKey[0].setCaseLocation(rtParam.getRetrievalLocation());
				
			}
			planKey[0].setCaseLocationCollect();

		}
		else if (RetrievalSupportParameter.CASEPIECE_FLAG_PIECE.equals(rtParam.getCasePieceflg()))
		{
			planKey = new RetrievalPlanSearchKey[1];
			String[] casepiece = {RetrievalPlan.CASEPIECE_FLAG_MIX, RetrievalPlan.CASEPIECE_FLAG_PIECE};
			planKey[0] = new RetrievalPlanSearchKey();
			setSearchKey(planKey[0], rtParam);
			planKey[0].setCasePieceFlag(casepiece);
			planKey[0].setPieceLocation("", "IS NOT NULL");
			if (!StringUtil.isBlank(rtParam.getRetrievalLocation()))
			{
				planKey[0].setPieceLocation(rtParam.getRetrievalLocation());
				
			}
			planKey[0].setPieceLocationCollect();
			
		}
		else if (RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING.equals(rtParam.getCasePieceflg()))
		{
			planKey = new RetrievalPlanSearchKey[1];
			planKey[0] = new RetrievalPlanSearchKey();
			setSearchKey(planKey[0], rtParam);
			planKey[0].setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			planKey[0].setCaseLocation("", "IS NOT NULL", "(", "", "OR");
			planKey[0].setPieceLocation("", "IS NOT NULL", "", ")", "AND");
			if (!StringUtil.isBlank(rtParam.getRetrievalLocation()))
			{
				planKey[0].setCaseLocation(rtParam.getRetrievalLocation(), "=", "(", "", "OR");
				planKey[0].setPieceLocation(rtParam.getRetrievalLocation(), "=", "", ")", "AND");
				
			}
			
		}

		wFinder = new RetrievalPlanSummarizeFinder(wConn);
		//#CM712923
		// Open cursor 
		wFinder.open();
		int count = ((RetrievalPlanSummarizeFinder)wFinder).searchLocationNo(planKey);
		//#CM712924
		//Set count for  wLength. 
		wLength = count;
		wCurrent = 0;

	}

	//#CM712925
	/**
	 * Set the conditions input via screen as search conditions. 
	 * @param key Search key to be set
	 * @param rtParam Search conditions to be set
	 * @throws ReadWriteException Announce when error occurs on DB connection. 
	 */
	private void setSearchKey(RetrievalPlanSearchKey sKey, RetrievalSupportParameter rtParam)
		throws ReadWriteException
	{
		//#CM712926
		// Set the search conditions. 
		//#CM712927
		//Consignor Code
		if (!StringUtil.isBlank(rtParam.getConsignorCode()))
		{
			sKey.setConsignorCode(rtParam.getConsignorCode());
		}
		//#CM712928
		// Planned Picking Date
		if (!StringUtil.isBlank(rtParam.getRetrievalPlanDate()))
		{
			sKey.setPlanDate(rtParam.getRetrievalPlanDate());
		}
		//#CM712929
		// Item Code
		if (!StringUtil.isBlank(rtParam.getItemCode()))
		{
			sKey.setItemCode(rtParam.getItemCode());
		}
		sKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
	}
	
	//#CM712930
	/**
	 * Allow this method to set the RetrievalPlan Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param ety Picking Plan Info 
	 * @return Parameter []  Array of <CODE>RetrievalSupportParameter</CODE> class that has set Picking Plan Info.
	 */
	private Parameter[] convertToWorkInfoSupportParams(Entity[] ety)
	{
		RetrievalPlan[] plan = (RetrievalPlan[]) ety;

		RetrievalSupportParameter[] rtParam = new RetrievalSupportParameter[plan.length];

		for (int i = 0; i < plan.length; i++)
		{
			rtParam[i] = new RetrievalSupportParameter();
			//#CM712931
			// Case/Piece division 
			rtParam[i].setCasePieceflg(plan[i].getCasePieceFlag());
			//#CM712932
			// Picking Location 
			rtParam[i].setRetrievalLocation(plan[i].getCaseLocation());

		}
		return rtParam;

	}

}
//#CM712933
//end of class
