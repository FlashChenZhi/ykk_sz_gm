// $Id: SessionRetrievalShortageRet.java,v 1.2 2006/11/13 08:21:02 suresh Exp $

//#CM693181
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.display.web.listbox.sessionret;
import java.sql.Connection;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoFinder;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;

//#CM693182
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to search for the shortage info and display it.<BR>
 * Maintain the instance in the session to use this class.
 * Delete it from the session after use.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Process for searching(<CODE>SessionRetrievalShortageRet(Connection conn, SystemParameter param)</CODE>method)<BR>
 * <DIR>
 *   Execute this when displaying the listbox screen as default.<BR>
 *   Invoke the <CODE>find(SystemParameter param)</CODE> method and search for the shortage info.<BR>
 *   Check the display condition when searching and change the method for obtaining it.<BR>
 *   <DIR>
 *     1.By Item: Aggregate by consignor code and item code and execute the search.<BR>
 *     2.By Plan: Search without aggregation.<BR>
 *   </DIR>
 * <BR>
 *   <Search conditions>*Mandatory item (count)
 *   <DIR>
 *     <TABLE>
 *     <TR><TD>*</TD><TD>Added Date/Time</TD><TD>�F</TD><TD>RegistDate</TD></TR>
 *     <TR><TD></TD><TD>Consignor Code</TD><TD>�F</TD><TD>ConsignorCode</TD></TR>
 *     <TR><TD>*</TD><TD>Display condition</TD><TD>�F</TD><TD>DispType</TD></TR>
 *     </TABLE>
 *   </DIR>
 *   <Search table>
 *   <DIR>
 *     DNSHORTAGEINFO<BR>
 *   </DIR>
 * </DIR>
 * 
 * 2.Process for displaying(<CODE>getEntities()</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the data to display on the screen.<BR>
 *   Obtain the display info that was obtained in the search process.<BR>
 *   Set the search result in the <CODE>SystemParameter</CODE> array and return it.<BR>
 * <BR>
 *   <Field items to displayed>
 *   <DIR>
 *     <TABLE>
 *     <TR><TD></TD><TD>Consignor Code</TD><TD>�F</TD><TD>ConsignorCode</TD></TR>
 *     <TR><TD></TD><TD>Consignor Name</TD><TD>�F</TD><TD>ConsignorName</TD></TR>
 *     <TR><TD></TD><TD>Item Code</TD><TD>�F</TD><TD>ItemCode</TD></TR>
 *     <TR><TD></TD><TD>Item Name</TD><TD>�F</TD><TD>ItemName</TD></TR>
 *     <TR><TD></TD><TD>Packed Qty per Case</TD><TD>�F</TD><TD>EnteringQty</TD></TR>
 *     <TR><TD></TD><TD>Packed Qty per Bundle</TD><TD>�F</TD><TD>BundleEnteringQty</TD></TR>
 *     <TR><TD></TD><TD>Planned Case Qty</TD><TD>�F</TD><TD>PlanCaseQty</TD></TR>
 *     <TR><TD></TD><TD>Planned Piece Qty</TD><TD>�F</TD><TD>PlanPieceQty</TD></TR>
 *     <TR><TD></TD><TD>Allocatable Case Qty</TD><TD>�F</TD><TD>EnableCaseQty</TD></TR>
 *     <TR><TD></TD><TD>Allocatable Piece Qty</TD><TD>�F</TD><TD>EnablePieceQty</TD></TR>
 *     <TR><TD></TD><TD>Shortage Packed Qty per Case</TD><TD>�F</TD><TD>ShortageCaseQty</TD></TR>
 *     <TR><TD></TD><TD>Shortage Piece Qty</TD><TD>�F</TD><TD>ShortagePieceQty</TD></TR>
 *     <TR><TD></TD><TD>Case Picking Location</TD><TD>�F</TD><TD>CaseLocation</TD></TR>
 *     <TR><TD></TD><TD>Piece Picking Location</TD><TD>�F</TD><TD>PieceLocation</TD></TR>
 *     <TR><TD></TD><TD>Case Order No.</TD><TD>�F</TD><TD>CaseOrderNo</TD></TR>
 *     <TR><TD></TD><TD>Piece Order No.</TD><TD>�F</TD><TD>PieceOrderNo</TD></TR>
 *     <TR><TD></TD><TD>Customer Name</TD><TD>�F</TD><TD>CustomerName</TD></TR>
 *     <TR><TD></TD><TD>Shipping Ticket No</TD><TD>�F</TD><TD>ShippingTicketNo</TD></TR>
 *     <TR><TD></TD><TD>Shipping ticket line No.</TD><TD>�F</TD><TD>ShippingTicketLineNo</TD></TR>
 *     </TABLE>
 *   </DIR>
 * </DIR>
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/02/09</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 08:21:02 $
 * @author  $Author: suresh $
 */
public class SessionRetrievalShortageRet extends SessionRet
{
	//#CM693183
	// Class fields --------------------------------------------------

	//#CM693184
	// Class variables -----------------------------------------------

	//#CM693185
	// Class method --------------------------------------------------
	//#CM693186
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 08:21:02 $");
	}

	//#CM693187
	// Constructors --------------------------------------------------
	//#CM693188
	/**
	 * Invoke the <CODE>find(SystemParameter param)</CODE> method for searching.<BR>
	 * Allow the <CODE>find(SystemParameter param)</CODE> method to set the count of obtained data.<BR>
	 * Require to invoke the <CODE>getEntities</CODE> method to obtain the search result.<BR>
	 * 
	 * @param conn       <code>Connection</code>
	 * @param param      Parameter that contains the <code>SystemParameter</code> search result.
	 * @throws Exception Announce when some error occurs during loading data.
	 */
	public SessionRetrievalShortageRet(Connection conn, SystemParameter param) throws Exception
	{
		this.wConn = conn;

		//#CM693189
		// Search
		find(param);
	}

	//#CM693190
	// Public methods ------------------------------------------------
	//#CM693191
	/**
	 * Return the designated number of search results of the shortage info.<BR>
	 * Allow this method to execute the following processes.<BR>
	 * <BR>
	 * <DIR>
	 *   1.Calculate to specify the count of display data to be obtained.<BR>
	 *   2.Obtain the shortage info. from the result set.<BR>
	 *   3.Obtain the display data from the shortage info and set it in <CODE>SystemParameter</CODE>.<BR>
	 *   4.Return information to be displayed.<BR>
	 * </DIR>
	 * 
	 * @return <CODE>SystemParameter</CODE> class that contains information to be displayed.
	 */
	public SystemParameter[] getEntities()
	{
		ShortageInfo[] resultArray = null;
		SystemParameter[] param = null;

		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				//#CM693192
				// Obtain the search result.
				resultArray = (ShortageInfo[]) ((ShortageInfoFinder) wFinder).getEntities(wStartpoint, wEndpoint);
				//#CM693193
				// Execute setting for SystemParameter again.
				param = getDispData(resultArray);

			}
			catch (Exception e)
			{
				//#CM693194
				//Record the error in the log file.
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;

		return param;
	}

	//#CM693195
	// Package methods -----------------------------------------------

	//#CM693196
	// Protected methods ---------------------------------------------

	//#CM693197
	// Private methods -----------------------------------------------
	//#CM693198
	/**
	 * Allow this method to obtain the search conditions from parameter and search for the shortage info.<BR>
	 * 
	 * @param param   Parameter to obtain search conditions.
	 * @throws Exception Report all exceptions.
	 */
	private void find(SystemParameter param) throws Exception
	{
		
		//#CM693199
		// Obtain the Batch No. from the added date/time and the consignor code.
		String batchNo = getBatchNo(param);
		
		//#CM693200
		// Set the search conditions.
		ShortageInfoSearchKey shortageKey = new ShortageInfoSearchKey();
		
		//#CM693201
		// Batch No.
		shortageKey.setBatchNo(batchNo);
		//#CM693202
		// Consignor Code
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			shortageKey.setConsignorCode(param.getConsignorCode());
		}
		
		//#CM693203
		// View by Item: Aggregate items and display them.
		if (param.getDispType().equals(SystemParameter.DISP_TYPE_ITEM))
		{
			//#CM693204
			// Set the Sequence of group.
			shortageKey.setConsignorCodeGroup(1);
			shortageKey.setPlanDateGroup(2);
			shortageKey.setItemCodeGroup(3);
			
			//#CM693205
			// Set the obtaining sequence.
			shortageKey.setConsignorCodeCollect("");
			shortageKey.setConsignorNameCollect("MAX");
			shortageKey.setPlanDateCollect("");
			shortageKey.setItemCodeCollect("");
			shortageKey.setItemName1Collect("MAX");
			shortageKey.setEnteringQtyCollect("MAX");
			shortageKey.setBundleEnteringQtyCollect("MAX");
			shortageKey.setPlanQtyCollect("SUM");
			shortageKey.setEnableQtyCollect("SUM");
			shortageKey.setShortageCntCollect("SUM");
			
			//#CM693206
			// Set the sorting order.
			shortageKey.setConsignorCodeOrder(1, true);
			shortageKey.setItemCodeOrder(2, true);

		}
		else
		{
			//#CM693207
			// Set the sorting order.
			shortageKey.setConsignorCodeOrder(1, true);
			shortageKey.setConsignorNameOrder(2, true);
			shortageKey.setItemCodeOrder(3, true);
			shortageKey.setItemName1Order(4, true);
			shortageKey.setCaseLocationOrder(5, true);
			shortageKey.setPieceLocationOrder(6, true);
		}
		
		wFinder = new ShortageInfoFinder(wConn);
		//#CM693208
		// Open the cursor.
		wFinder.open();
		//#CM693209
		// Obtain the count of data to be displayed.
		int count = wFinder.search(shortageKey);
		//#CM693210
		// Initialize.
		wLength = count;
		wCurrent = 0;

	}
	
	//#CM693211
	/**
	 * Allow this method to obtain the Batch No. from the added date/time and the consignor code.<BR>
	 * @param  param  Parameter
	 * @return String Batch No..
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private String getBatchNo(SystemParameter param) throws ReadWriteException
	{
		try 
		{
			//#CM693212
			// Set the search conditions.
			ShortageInfoSearchKey shortageKey = new ShortageInfoSearchKey();
		
			//#CM693213
			// Added date
			if (!StringUtil.isBlank(param.getRegistDate()))
			{
				shortageKey.setRegistDate(param.getRegistDate());
			}
			//#CM693214
			// Consignor Code
			if (!StringUtil.isBlank(param.getConsignorCode()))
			{
				shortageKey.setConsignorCode(param.getConsignorCode());
			}
		
			//#CM693215
			// Group the data by Batch No.
			shortageKey.setBatchNoGroup(1);
			shortageKey.setBatchNoCollect();
		
			//#CM693216
			// Execute searching.
			ShortageInfoHandler shortageHandler = new ShortageInfoHandler(wConn);
			ShortageInfo result = (ShortageInfo)shortageHandler.findPrimary(shortageKey);

			//#CM693217
			// Obtain the Batch No. from the search result and return it.
			if (result != null)
			{
				return result.getBatchNo();
			}
			else
			{
				return "";
			}
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM693218
	/**
	 * Allow this class to set the <CODE>ShortageInfo</CODE> entity for <CODE>SystemParameter</CODE>.<BR>
	 * 
	 * @param result shortage info
	 * @return SystemParameter[] <CODE>SystemParameter</CODE> class that are set with shortage info.
	 */
	private SystemParameter[] getDispData(ShortageInfo[] result)
	{
		SystemParameter[] param = new SystemParameter[result.length];

		for (int i = 0; i < result.length; i++)
		{
			param[i] = new SystemParameter();
			//#CM693219
			// Planned Picking Date
			param[i].setPlanDate(result[i].getPlanDate());
			//#CM693220
			// Consignor Code
			param[i].setConsignorCode(result[i].getConsignorCode());
			//#CM693221
			// Consignor Name
			param[i].setConsignorName(result[i].getConsignorName());
			//#CM693222
			// Item Code
			param[i].setItemCode(result[i].getItemCode());
			//#CM693223
			// Item Name
			param[i].setItemName(result[i].getItemName1());
			//#CM693224
			// Packed Qty per Case
			param[i].setEnteringQty(result[i].getEnteringQty());
			//#CM693225
			// Packed qty per bundle
			param[i].setBundleEnteringQty(result[i].getBundleEnteringQty());
			//#CM693226
			// Count up only the piece qty for a piece item.
			if (result[i].getCasePieceFlag().equals(ShortageInfo.CASEPIECE_FLAG_PIECE))
			{
				//#CM693227
				// Planned Case Qty
				param[i].setPlanCaseQty(0);
				//#CM693228
				// Planned Piece Qty
				param[i].setPlanPieceQty(result[i].getPlanQty());
				//#CM693229
				// Allocatable Case Qty
				param[i].setEnableCaseQty(0);
				//#CM693230
				// Allocatable Piece Qty
				param[i].setEnablePieceQty(result[i].getEnableQty());
				//#CM693231
				// Case Shortage Qty
				param[i].setShortageCaseQty(0);
				//#CM693232
				// Piece Shortage Qty
				param[i].setShortagePieceQty(result[i].getShortageCnt());
			}
			else
			{
				//#CM693233
				// Planned Case Qty
				param[i].setPlanCaseQty(DisplayUtil.getCaseQty(result[i].getPlanQty(), result[i].getEnteringQty()));
				//#CM693234
				// Planned Piece Qty
				param[i].setPlanPieceQty(DisplayUtil.getPieceQty(result[i].getPlanQty(), result[i].getEnteringQty()));
				//#CM693235
				// Allocatable Case Qty
				param[i].setEnableCaseQty(DisplayUtil.getCaseQty(result[i].getEnableQty(), result[i].getEnteringQty()));
				//#CM693236
				// Allocatable Piece Qty
				param[i].setEnablePieceQty(DisplayUtil.getPieceQty(result[i].getEnableQty(), result[i].getEnteringQty()));
				//#CM693237
				// Case Shortage Qty
				param[i].setShortageCaseQty(DisplayUtil.getCaseQty(result[i].getShortageCnt(), result[i].getEnteringQty()));
				//#CM693238
				// Piece Shortage Qty
				param[i].setShortagePieceQty(DisplayUtil.getPieceQty(result[i].getShortageCnt(), result[i].getEnteringQty()));
			}
			//#CM693239
			// Case Picking Location
			param[i].setCaseLocation(result[i].getCaseLocation());
			//#CM693240
			// Piece Picking Location
			param[i].setPieceLocation(result[i].getPieceLocation());
			//#CM693241
			// Case Order No.
			param[i].setCaseOrderNo(result[i].getCaseOrderNo());
			//#CM693242
			// Piece Order No.
			param[i].setPieceOrderNo(result[i].getPieceOrderNo());
			//#CM693243
			// Customer Name
			param[i].setCustomerName(result[i].getCustomerName1());
			//#CM693244
			// Shipping Ticket No
			param[i].setShippingTicketNo(result[i].getShippingTicketNo());
			//#CM693245
			// Shipping ticket line No.
			param[i].setShippingTicketLineNo(result[i].getShippingLineNo());
		}

		return param;
	}

}
//#CM693246
//end of class
