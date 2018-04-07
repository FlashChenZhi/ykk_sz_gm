package jp.co.daifuku.wms.retrieval.display.web.listbox.sessionret;
//#CM712774
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
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.retrieval.schedule.RetrievalSupportParameter;

//#CM712775
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * Allow this class to search for data in the listbox of Customer list (Picking Plan). <BR>
 * Receive the Receive search conditions as a parameter and search through Customer list. <BR>
 * Maintain the instance in the session to use this class. 
 * Delete it from the session after use. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalPlanCustomerRet(Connection conn, RetrievalSupportParameter param)</CODE> method) <BR>
 * <DIR>
 *   Execute this when the initial display of the listbox screen appears.<BR>
 *   Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method and search for Picking Plan Info. <BR>
 * <BR>
 *   <Search conditions> No mandatory field item<BR>
 *   <DIR>
 * 	   Consignor Code <BR>
 * 	   Item Code <BR>
 * 	   Planned Picking Date <BR>
 * 	   Shipping Ticket No  <BR>
 *     Case Location<BR>
 *    Piece Location <BR> 				
 *     Customer Code<BR>
 *     Case/Piece division<BR>
 *     Status flag<BR>
 *    Item / Order flag <BR>
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
 * <TR><TD>2004/10/06</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2007/02/07 04:19:03 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalPlanCustomerRet extends SessionRet
{
	//#CM712776
	// Class fields --------------------------------------------------

	//#CM712777
	// Class variables -----------------------------------------------

	//#CM712778
	// Class method --------------------------------------------------
	//#CM712779
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2007/02/07 04:19:03 $");
	}

	//#CM712780
	// Constructors --------------------------------------------------
	//#CM712781
	/**
	 * Invoke the <CODE>find(RetrievalSupportParameter param)</CODE> method to execute searching. <BR>
	 * Allow the <CODE>find(RetrievalSupportParameter param)</CODE> method to set the number of obtained data. <BR>
	 * Require to invoke the <code>getEntities</code> method to obtain the search result, <BR>
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	public SessionRetrievalPlanCustomerRet(Connection conn, RetrievalSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM712782
	// Public methods ------------------------------------------------
	//#CM712783
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

	//#CM712784
	// Package methods -----------------------------------------------

	//#CM712785
	// Protected methods ---------------------------------------------

	//#CM712786
	// Private methods -----------------------------------------------
	//#CM712787
	/**
	 * Print the SQL statement based on the input parameter. <BR>
	 * Maintain <code>RetrievalPlanFinder</code> that executes search, as an instance variable.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result. <BR>
	 * @param param      Parameter that contains the search result of <CODE>RetrievalSupportParameter</CODE>. 
	 * @throws Exception Announce when some error occurs during loading data. 
	 */
	private void find(RetrievalSupportParameter param) throws Exception
	{

		RetrievalPlanSearchKey skey = new RetrievalPlanSearchKey();
		//#CM712788
		// Set the search conditions.
		//#CM712789
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM712790
		// Item Code
		if (!StringUtil.isBlank(param.getItemCode()))
		{
			skey.setItemCode(param.getItemCode());
		}
		//#CM712791
		// Planned Picking Date
		if (!StringUtil.isBlank(param.getRetrievalPlanDate()))
		{
			skey.setPlanDate(param.getRetrievalPlanDate());
		}
		//#CM712792
		// Start Planned Picking Date
		if (!StringUtil.isBlank(param.getFromRetrievalPlanDate()))
		{
			skey.setPlanDate(param.getFromRetrievalPlanDate(), ">=");
		}
		//#CM712793
		// End Planned Picking Date
		if (!StringUtil.isBlank(param.getToRetrievalPlanDate()))
		{
			skey.setPlanDate(param.getToRetrievalPlanDate(), "<=");
		}

		//#CM712794
		// Shipping Ticket No 
		if (!StringUtil.isBlank(param.getShippingTicketNo()))
		{
			skey.setShippingTicketNo(param.getShippingTicketNo());
		}
		//#CM712795
		// Case Location
		if (!StringUtil.isBlank(param.getCaseLocation()))
		{
			skey.setCaseLocation(param.getCaseLocation());
		}
		//#CM712796
		// Piece Location 
		if (!StringUtil.isBlank(param.getPieceLocation()))
		{
			skey.setPieceLocation(param.getPieceLocation());
		}
		//#CM712797
		// Customer Code
		if (!StringUtil.isBlank(param.getCustomerCode()))
		{
			skey.setCustomerCode(param.getCustomerCode());
		}
		else
		{
			skey.setCustomerCode("", "IS NOT NULL");
		}
		//#CM712798
		// Grouping condition 
		skey.setCustomerCodeGroup(1);
		skey.setCustomerName1Group(2);
		//#CM712799
		// Set the Conditions for obtaining data. 
		skey.setCustomerCodeCollect("");
		skey.setCustomerName1Collect("");

		//#CM712800
		// Set the sorting order. 
		skey.setCustomerCodeOrder(1, true);
		skey.setCustomerName1Order(2, true);

		//#CM712801
		// Case/Piece division 
		if (!StringUtil.isBlank(param.getCasePieceflg()))
		{
			if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_CASE))
			{
				//#CM712802
				// Case or Mixed for Case data
				String[] casePiece = {RetrievalPlan.CASEPIECE_FLAG_CASE, RetrievalPlan.CASEPIECE_FLAG_MIX};
				skey.setCasePieceFlag(casePiece);
			}
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				//#CM712803
				// Piece or Mixed , for "Piece" data. 
				String[] casePiece = {RetrievalPlan.CASEPIECE_FLAG_PIECE, RetrievalPlan.CASEPIECE_FLAG_MIX};
				skey.setCasePieceFlag(casePiece);
			}
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				//#CM712804
				// None 
				skey.setCasePieceFlag(RetrievalPlan.CASEPIECE_FLAG_NOTHING);
			}
			else if (param.getCasePieceflg().equals(RetrievalSupportParameter.CASEPIECE_FLAG_ALL))
			{
				//#CM712805
				// All 
			}
		}
		//#CM712806
		// Status flag
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
			//#CM712807
			// Set it using OR condition. 
			skey.setStatusFlag(search);
		}
		else
		{
			//#CM712808
			// Search for data with status other than Deleted, if nothing is set. 
			skey.setStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE, "!=");
		}

		//#CM712809
		// Item / Order flag 
		//#CM712810
		// Division Item 
		if (RetrievalSupportParameter.ITEMORDERFLAG_ITEM.equals(param.getItemOrderFlag()))
		{
			skey.setPieceOrderNo("");
			skey.setCaseOrderNo("");
		}
		//#CM712811
		// Division Order 
		else if (RetrievalSupportParameter.ITEMORDERFLAG_ORDER.equals(param.getItemOrderFlag()))
		{
			skey.setPieceOrderNo("", "IS NOT NULL", "(", "", "OR");
			skey.setCaseOrderNo("", "IS NOT NULL", "", ")", "OR");
		}

		wFinder = new RetrievalPlanFinder(wConn);
		//#CM712812
		// Open the cursor.
		wFinder.open();
		int count = ((RetrievalPlanFinder) wFinder).search(skey);
		//#CM712813
		// Initialize. 
		wLength = count;
		wCurrent = 0;
	}

	//#CM712814
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
			stParam[i].setCustomerCode(retrievalPlan[i].getCustomerCode());
			stParam[i].setCustomerName(retrievalPlan[i].getCustomerName1());
		}

		return stParam;
	}

}
//#CM712815
//end of class
