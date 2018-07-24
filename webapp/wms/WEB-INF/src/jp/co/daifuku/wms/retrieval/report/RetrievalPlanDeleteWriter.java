// $Id: RetrievalPlanDeleteWriter.java,v 1.6 2007/02/07 04:19:38 suresh Exp $
package jp.co.daifuku.wms.retrieval.report;

//#CM591969
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;

//#CM591970
/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda <BR>
 * 
 * Allow the <CODE>RetrievalPlanDeleteWriter</CODE> class to generate a data file for Picking Plan Delele report and execute printing.<BR>
 * Search for Picking Plan Info using the conditions set in the <CODE>RetrievalPlanDeleteSCH</CODE> class, and<BR>
 * generate a Report file for Picking Plan Delete report from the search result.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * Execute the process for generating a data file for report. (<CODE>startPrint()</CODE>method)<BR>
 *	<DIR>
 *	1.Search for the count of Picking Plan Info using the conditions set in the <CODE>RetrievalPlanDeleteSCH</CODE> class.<BR>
 *	2.Generate data file for report if one or more result found. If zero, return false and close.<BR>
 *	3.Execute the process for printing.<BR>
 *  4.Return true if print process normally completed.<BR>
 *    Return false when error occurs in the process of printing.<BR>
 *<BR>
 * 	<Parameter>*Mandatory Input<BR>
 * 	Picking Plan unique key*<BR>
 * <BR>
 * 	<Search conditions><BR>
 * 	Picking Plan unique key<BR>
 * <BR>
 *	<Data to be printed><BR>
 *	"Print" field item: DB field item<BR>
 *	Consignor: Consignor Code + Consignor Name<BR>
 *	Planned Picking Date: Planned Picking Date<BR>
 *	Item Code: Item Code<BR>
 *	Item Name: Item Name<BR>
 *	Total qty: Planned Picking Qty
 *	Packed Qty per Case: Packed Qty per Case<BR>
 *	Packed qty per bundle: Packed qty per bundle<BR>
 *	Picking Case Qty: Planned Picking Qty /Packed Qty per Case<BR>
 *	Picking Piece qty: Planned Picking qty % Packed qty per Case<BR>
 *	Case Picking Location: Case Item Picking Location<BR>
 *	Piece Picking Location: Piece Item Picking Location<BR>
 *	Case Order No.: Order No. Case<BR>
 *	Piece Order No.: Order No. Piece<BR>
 * 	</DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/19</TD><TD>K.Matsuda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2007/02/07 04:19:38 $
 * @author  $Author: suresh $
 */
public class RetrievalPlanDeleteWriter extends CSVWriter
{
	//#CM591971
	// Class fields --------------------------------------------------

	//#CM591972
	// Class variables -----------------------------------------------
	//#CM591973
	/**
	 * Picking Plan unique key to be deleted
	 */
	protected Vector wRetrievalPlanUKey = null;
	
	//#CM591974
	/**
	 * Consignor Name to be displayed
	 */
	protected String wConsignorName = "" ;

	//#CM591975
	// Class method --------------------------------------------------
	//#CM591976
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2007/02/07 04:19:38 $") ;
	}

	//#CM591977
	// Constructors --------------------------------------------------
	//#CM591978
	/**
	 * Construct the RetrievalPlanDeleteWriter object.
	 * @param conn <CODE>Connection</CODE> Database connection object
	 */
	public RetrievalPlanDeleteWriter(Connection conn)
	{
		super(conn);
	}
	
	//#CM591979
	// Public methods ------------------------------------------------
	//#CM591980
	/**
	 * Obtain the Picking Plan unique key.
	 * @return	Picking Plan unique key
	 */
	public Vector getRetrievalPlanUKey()
	{
		return wRetrievalPlanUKey;
	}

	//#CM591981
	/**
	 * Set the Picking Plan unique key.
	 * @param Picking Plan unique key to be set (vector) 
	 */
	public void setRetrievalPlanUKey(Vector retrievalPlanUKey)
	{
		wRetrievalPlanUKey = retrievalPlanUKey;
	}


	//#CM591982
	/**
	 * Generate a CSV file for Picking Plan Delete report and execute printing.<BR>
	 * Search through the count of Picking Plan Info using the committed condition.<BR>
	 * Generate data file for report if one or more result found. If zero, return false and close.<BR>
	 * Execute the process for printing.<BR>
	 * Return true if print process normally completed.<BR>
	 * Return false when error occurs in the process of printing.<BR>
	 * 
	 * @return boolean Result whether the printing succeeded or not.
	 */
	public boolean startPrint()
	{
		RetrievalPlanReportFinder retrievalPlanReportFinder =  new RetrievalPlanReportFinder(wConn);

		try
		{
			RetrievalPlanSearchKey retrievalPlanSearchKey = new RetrievalPlanSearchKey(); 
			RetrievalPlan[] retrievalPlan = null ;

			//#CM591983
			// SearchKey Clear
			retrievalPlanSearchKey.KeyClear();
			
			//#CM591984
			// Set the search conditions.
			String[] searchPlanUKey = new String[wRetrievalPlanUKey.size()];
			wRetrievalPlanUKey.copyInto(searchPlanUKey);
			retrievalPlanSearchKey.setRetrievalPlanUkey(searchPlanUKey);
			
			retrievalPlanSearchKey.setPlanDateOrder(1, true);
			retrievalPlanSearchKey.setItemCodeOrder(2, true);
			retrievalPlanSearchKey.setCaseLocationOrder(3, true);
			retrievalPlanSearchKey.setPieceLocationOrder(4, true);
			retrievalPlanSearchKey.setCaseOrderNoOrder(5, true);
			retrievalPlanSearchKey.setPieceOrderNoOrder(6, true);
			
			
			//#CM591985
			// Open the cursor.
			retrievalPlanReportFinder.open();
			
			//#CM591986
			// No target data
			if (retrievalPlanReportFinder.search(retrievalPlanSearchKey) <= 0 )
			{
				//#CM591987
				// No print data found.
				wMessage = "6003010";
				return false;
			}
			
			//#CM591988
			// Commit the consignor name.
			getDisplayConsignorName();

			//#CM591989
			//Generate a file to be output.
			if(!createPrintWriter(FNAME_RETRIEVAL_DELETE))
			{
				return false;
			}
			
			//#CM591990
			// Generate a data file per 100 search results until there remains no result data.
			while(retrievalPlanReportFinder.isNext())
			{
				//#CM591991
				// Obtain up to 100 search results.
				retrievalPlan = (RetrievalPlan[])retrievalPlanReportFinder.getEntities(100);

				for (int i = 0; i < retrievalPlan.length; i++)
				{
					wStrText.append(re + "");
	
					//#CM591992
					// Consignor Code
					wStrText.append(ReportOperation.format(retrievalPlan[i].getConsignorCode()) + tb);
					//#CM591993
					// Consignor Name
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					//#CM591994
					// Planned Picking Date
					wStrText.append(ReportOperation.format(retrievalPlan[i].getPlanDate()) + tb);
					//#CM591995
					// Item Code
					wStrText.append(ReportOperation.format(retrievalPlan[i].getItemCode()) + tb);
					//#CM591996
					// Item Name
					wStrText.append(ReportOperation.format(retrievalPlan[i].getItemName1()) + tb);
					//#CM591997
					// Total qty
					wStrText.append(retrievalPlan[i].getPlanQty() + tb);
					//#CM591998
					// Packed Qty per Case
					wStrText.append(retrievalPlan[i].getEnteringQty() + tb);
					//#CM591999
					// Packed qty per bundle
					wStrText.append(retrievalPlan[i].getBundleEnteringQty() + tb);
					//#CM592000
					// Picking Case QTY
					wStrText.append(DisplayUtil.getCaseQty(retrievalPlan[i].getPlanQty(),retrievalPlan[i].getEnteringQty()) + tb);
					//#CM592001
					// Picking Piece QTY
					wStrText.append(DisplayUtil.getPieceQty(retrievalPlan[i].getPlanQty(),retrievalPlan[i].getEnteringQty()) + tb);
					//#CM592002
					// Case Picking Location
					wStrText.append(ReportOperation.format(retrievalPlan[i].getCaseLocation()) + tb);
					//#CM592003
					// Piece Picking Location
					wStrText.append(ReportOperation.format(retrievalPlan[i].getPieceLocation()) + tb);
					//#CM592004
					// Case Order No.
					wStrText.append(ReportOperation.format(retrievalPlan[i].getCaseOrderNo()) + tb);
					//#CM592005
					// Piece Order No.
					wStrText.append(ReportOperation.format(retrievalPlan[i].getPieceOrderNo()) + tb);
					
					//#CM592006
					// Writing
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}

			//#CM592007
			// Close the stream.
			wPWriter.close();

			//#CM592008
			// Execute UCXSingle.
			if (!executeUCX(JOBID_RETRIEVAL_DELETE))
			{
				//#CM592009
				// Failed to print. See log.
				return false;
			}

			//#CM592010
			// Move the data file to the backup folder.
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException e)
		{	
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				//#CM592011
				// Execute processes for closing the cursor for the opened database.
				retrievalPlanReportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM592012
				// Database error occurred. See log.
				setMessage("6007002");
				return false;			
			}
		}

		return true;
	}

	//#CM592013
	// Package methods -----------------------------------------------

	//#CM592014
	// Protected methods ---------------------------------------------
	//#CM592015
	/**
	 * Obtain the Consignor name to display in the List.<BR>
	 * Search for the Picking Plan info with the latest Added Date/Time using the search condition for data to be printed, and <BR>
	 * return the Customer Name of the leading data.<BR>
	 * Return null if the count of search results is 0.<BR>
	 * 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void getDisplayConsignorName() throws ReadWriteException
	{
		//#CM592016
		// Generate the Finder instance.
		RetrievalPlanReportFinder consignorFinder = new RetrievalPlanReportFinder(wConn);
		RetrievalPlanSearchKey retrievalPlanSearchKey = new RetrievalPlanSearchKey();
		
		//#CM592017
		// Set the search conditions.
		String[] searchPlanUKey = new String[wRetrievalPlanUKey.size()];
		wRetrievalPlanUKey.copyInto(searchPlanUKey);
		retrievalPlanSearchKey.setRetrievalPlanUkey(searchPlanUKey);
		
		retrievalPlanSearchKey.setConsignorNameCollect("");
		retrievalPlanSearchKey.setRegistDateOrder(1, false);
		
		//#CM592018
		// Search Consignor Name
		consignorFinder.open();
		
		if (consignorFinder.search(retrievalPlanSearchKey) > 0)
		{
			RetrievalPlan[] retrievalPlan = (RetrievalPlan[]) consignorFinder.getEntities(1);

			if (retrievalPlan != null && retrievalPlan.length != 0)
			{
				wConsignorName = retrievalPlan[0].getConsignorName();
			}
		}
		
		consignorFinder.close();
	}
	
	//#CM592019
	// Private methods -----------------------------------------------

}
//#CM592020
//end of class
