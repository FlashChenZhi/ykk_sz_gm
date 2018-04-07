package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;

//#CM712965
/*
 * Created on Oct 22, 2004
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
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;

//#CM712966
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Picking Plan list (to add Picking Plan). <BR>
 * Receive the search conditions as a parameter, and search through Picking Plan list (to add Picking Plan). <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalPlanRegistRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Work Status Info. <BR>
 * <BR>
 *   <Search conditions> Mandatory field item *<BR>
 *   <DIR>
 *     Consignor Code*<BR>
 *    Work status: other than Deleted *<BR>
 *   Work type: Picking*<BR>
 *     Planned Picking Date<BR>
 *   Item Code<BR>
 *   Order No.<BR>
 *    Case/Piece division <BR>
 *   Customer Code<BR>
 *   </DIR>
 *   <Search table>  <BR>
 *   <DIR>
 *     DNWORKINFO <BR>
 *   </DIR>
 *   <Searching Order >  <BR>
 *   <DIR>
 *     Planned Picking Date<BR>
 *     Item Code<BR>
 *     Case Picking Location<BR>
 *     Piece Picking Location <BR>
 *     Customer Code<BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
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
 *   Planned Picking Date<BR>
 *   Item Code<BR>
 *   Item Name<BR>
 *   Packed Qty per Case<BR>
 *   Packed qty per bundle<BR>
 *   Case Picking Location<BR>
 *     Piece Picking Location <BR>
 *     Case Order No.<BR>
 *     Piece Order No.<BR>
 *   Customer Code<BR>
 *   Customer Name<BR>
 *   Case/Piece division name<BR>
 *   Case ITF<BR>
 *   Bundle ITF<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/22</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author muneendra y
 * @version $Revision 1.2 Oct 22, 2004
 */
public class SessionRetrievalPlanRegistRet extends SessionRet
{
	//#CM712967
	// Class fields --------------------------------------------------

	//#CM712968
	// Class variables -----------------------------------------------

	//#CM712969
	// Class method --------------------------------------------------
	//#CM712970
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:05 $");
	}

	//#CM712971
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalPlanRegistRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}
	
	//#CM712972
	// Public methods ------------------------------------------------
	
	//#CM712973
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
				temp = (RetrievalPlan[])((RetrievalPlanFinder)wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToRetrievalSupportParams(temp);
			}
			catch (Exception e)
			{
				//#CM712974
				// Record the error in the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}
	
	//#CM712975
	// Package methods -----------------------------------------------

	//#CM712976
	// Protected methods ---------------------------------------------

	//#CM712977
	// Private methods -----------------------------------------------
	//#CM712978
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter searchParam) throws Exception
	{	
 
		RetrievalPlanSearchKey searchkey = new RetrievalPlanSearchKey();
		//#CM712979
		// Set the Status flag to any status other than "Deleted". 
		searchkey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
		//#CM712980
		// Set the Consignor code. It is a mandatory field item. 
		if(!StringUtil.isBlank(searchParam.getConsignorCode()))
		{
			searchkey.setConsignorCode(searchParam.getConsignorCode());	
		}
		//#CM712981
		//Set the Planned Picking date.
		if(!StringUtil.isBlank(searchParam.getRetrievalPlanDate()))
		{
			searchkey.setPlanDate(searchParam.getRetrievalPlanDate());
		}
		//#CM712982
		// Set the item code. 
		if(!StringUtil.isBlank(searchParam.getItemCode()))
		{
			searchkey.setItemCode(searchParam.getItemCode());
		}
		//#CM712983
		//Set the customer code.
		if(!StringUtil.isBlank(searchParam.getCustomerCode()))
		{
			searchkey.setCustomerCode(searchParam.getCustomerCode());
		}	
		if(!StringUtil.isBlank(searchParam.getCasePieceflg()))			
		{
			if (searchParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM712984
				// Case 
				searchkey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_CASE, "=","(","","OR");
				//#CM712985
				// Mixed 
				searchkey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "", ")", "AND");
				if (!StringUtil.isBlank(searchParam.getOrderNo()))
				{
					//#CM712986
					// Case Order No.
					searchkey.setCaseOrderNo(searchParam.getOrderNo());
				}
				if (!StringUtil.isBlank(searchParam.getRetrievalLocation()))
				{
					//#CM712987
					// Case Picking Location
					searchkey.setCaseLocation(searchParam.getRetrievalLocation());
				}
			}
			else if (searchParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM712988
				// Piece 
				searchkey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_PIECE, "=", "(", "", "OR");
				//#CM712989
				// Mixed 
				searchkey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_MIX, "=", "", ")", "AND");
				if (!StringUtil.isBlank(searchParam.getOrderNo()))
				{
					//#CM712990
					// Piece Order No.
					searchkey.setPieceOrderNo(searchParam.getOrderNo());
				}
				if (!StringUtil.isBlank(searchParam.getRetrievalLocation()))
				{
					//#CM712991
					// Piece Picking Location 
					searchkey.setPieceLocation(searchParam.getRetrievalLocation());
				}
			}
			else if (searchParam.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM712992
				// None 
				searchkey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
				if (!StringUtil.isBlank(searchParam.getOrderNo()))
				{
					//#CM712993
					// Case Order No.
					searchkey.setCaseOrderNo(searchParam.getOrderNo(), "=", "(", "", "OR");
					//#CM712994
					// Piece Order No.
					searchkey.setPieceOrderNo(searchParam.getOrderNo(), "=", "", ")", "AND");
				}
				if (!StringUtil.isBlank(searchParam.getRetrievalLocation()))
				{
					//#CM712995
					// Case Picking Location
					searchkey.setCaseLocation(searchParam.getRetrievalLocation(), "=", "(", "", "OR");
					//#CM712996
					// Piece Picking Location 
					searchkey.setPieceLocation(searchParam.getRetrievalLocation(), "=", "", ")", "AND");
				}
			}
		}
		
		//#CM712997
		// Set the order to obtain data. 	
		searchkey.setConsignorCodeCollect("");
		searchkey.setConsignorNameCollect("");
		searchkey.setPlanDateCollect("");				
		searchkey.setItemCodeCollect("");
		searchkey.setItemName1Collect("");
		searchkey.setEnteringQtyCollect("");
		searchkey.setBundleEnteringQtyCollect("");
		searchkey.setCaseLocationCollect("");
		searchkey.setPieceLocationCollect("");
		searchkey.setCaseOrderNoCollect("");
		searchkey.setPieceOrderNoCollect("");
		searchkey.setCustomerCodeCollect("");
		searchkey.setCustomerName1Collect("");
		searchkey.setCasePieceFlagCollect("");
		searchkey.setItfCollect("");
		searchkey.setBundleItfCollect("");
		
		//#CM712998
		// Set the sorting order. 		
		searchkey.setPlanDateOrder(1,true);
		searchkey.setItemCodeOrder(2,true);
		searchkey.setCaseLocationOrder(3,true);
		searchkey.setPieceLocationOrder(4,true);
		searchkey.setCustomerCodeOrder(5,true);
		searchkey.setCaseOrderNoOrder(6,true);
		searchkey.setPieceOrderNoOrder(7,true);
			
		wFinder = new RetrievalPlanFinder(wConn);
		//#CM712999
		// Open the cursor.
		wFinder.open();
		int count = ((RetrievalPlanFinder)wFinder).search(searchkey);
		//#CM713000
		// Initialize. 
		wLength = count;
		wCurrent = 0;
	}
	
	//#CM713001
	/**
	 * Allow this class to set the RetrievalPlan Entity in <CODE>RetrievalSupportParameter</CODE>. <BR>
	 * 
	 * @param retrievalPlan Work Status Info
	 * @return RetrievalSupportParameter []  <CODE>RetrievalSupportParameter</CODE> class that has set Work Status.
	 */
	private RetrievalSupportParameter[] convertToRetrievalSupportParams(RetrievalPlan[] retrievalPlan)
	{		
		RetrievalSupportParameter[] retrievalSupport = new RetrievalSupportParameter[retrievalPlan.length];
		for (int i = 0; i < retrievalPlan.length; ++i)
		{
			retrievalSupport [i] = new RetrievalSupportParameter();
			retrievalSupport [i].setRetrievalPlanDate(retrievalPlan[i].getPlanDate());
			retrievalSupport [i].setConsignorCode(retrievalPlan[i].getConsignorCode());
			retrievalSupport [i].setConsignorName(retrievalPlan[i].getConsignorName());
			retrievalSupport [i].setItemCode(retrievalPlan[i].getItemCode());
			retrievalSupport [i].setItemName(retrievalPlan[i].getItemName1());
			retrievalSupport [i].setBundleEnteringQty(retrievalPlan[i].getBundleEnteringQty());
			retrievalSupport [i].setEnteringQty(retrievalPlan[i].getEnteringQty());
			retrievalSupport [i].setCaseLocation(retrievalPlan[i].getCaseLocation());
			retrievalSupport [i].setPieceLocation(retrievalPlan[i].getPieceLocation());
			retrievalSupport [i].setCaseOrderNo(retrievalPlan[i].getCaseOrderNo());
			retrievalSupport [i].setPieceOrderNo(retrievalPlan[i].getPieceOrderNo());
			retrievalSupport [i].setCustomerCode(retrievalPlan[i].getCustomerCode());
			retrievalSupport [i].setCustomerName(retrievalPlan[i].getCustomerName1());
			retrievalSupport [i].setCasePieceflg(retrievalPlan[i].getCasePieceFlag());
			retrievalSupport [i].setITF(retrievalPlan[i].getItf());
			retrievalSupport [i].setBundleITF(retrievalPlan[i].getBundleItf());
		}
		return retrievalSupport;
	}
}
//#CM713002
//end of class
