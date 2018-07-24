// $Id: MovementWriter.java,v 1.6 2006/12/13 09:04:09 suresh Exp $
package jp.co.daifuku.wms.storage.report;

//#CM574575
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.dbhandler.MovementReportFinder;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.storage.entity.TotalMovement;

//#CM574576
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * This class creates the stock relocation work list and calls the print process<BR>
 * With the conditions specified in the schedule class, this class searches the stock relocation (dnmovement) table
 * and creates data file for printing<BR>
 * <BR>
 * This class processes the following<BR>
 * <BR>
 * Process that creates the report data file (<CODE>startPrint</CODE> method) <BR>
 * <DIR>
 *   Search stock relocation info<BR>
 *   Data file for printing is not created if target data does not exist.<BR>
 *   If target data exists, this class creates data file for printing and calls print process.<BR>
 * <BR>
 *   <Search condition> Required Input*
 *   <DIR>
 *     Job No. (Vector)*<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/18</TD><TD>Y.Okamura</TD><TD>Create new</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.6 $, $Date: 2006/12/13 09:04:09 $
 * @author  $Author: suresh $
 */
public class MovementWriter extends CSVWriter
{
	//#CM574577
	// Class fields --------------------------------------------------

	//#CM574578
	// Class variables -----------------------------------------------
	//#CM574579
	/**
	 * Job No. (Vector)
	 */
	private Vector wJobNo;

	//#CM574580
	/**
	 * Consignor name
	 */
	private String wConsignorName = "";
	
	//#CM574581
	/**
	 * Item name
	 */
	private String wItemName = "";

	//#CM574582
	// Class method --------------------------------------------------
	//#CM574583
	/**
	 * Returns version of this class
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.6 $,$Date: 2006/12/13 09:04:09 $");
	}

	//#CM574584
	// Constructors --------------------------------------------------
	//#CM574585
	/**
	 * Construct InventoryInquiryWriter object<BR>
	 * Set connection and locale<BR>
	 * @param conn <CODE>Connection</CODE> database connection object
	 */
	public MovementWriter(Connection conn)
	{
		super(conn);
	}

	//#CM574586
	// Public methods ------------------------------------------------
	//#CM574587
	/**
	 * Set search value to Job No. (Vector)
	 * @param pJobNo Vector Job No. (Vector)
	 */
	public void setJobNo(Vector pJobNo)
	{
		wJobNo = pJobNo;
	}

	//#CM574588
	/**
	 * Fetch Job No. (Vector)
	 * @return Job No. (Vector)
	 */
	public Vector getJobNo()
	{
		return wJobNo;
	}

	//#CM574589
	/**
	 * Create stock relocation work list csv file and call print<BR>
	 * <BR>
	 * <DIR>
	 * 		1.Set search conditions and execute search<BR>
	 * 		2.If the search result is less than 1, don't print<BR>
	 * 		3.Fetch search result in lots of 100<BR>
	 * 		4.Print<BR>
	 * 		5.If print succeeds, move the data file to backup folder<BR>
	 * 		6.Return whether the print is successful or not<BR>
	 * </DIR>
	 * @return execution result(Print success:true Print failed:false)
	 */
	public boolean startPrint()
	{

		MovementReportFinder movementFinder = new MovementReportFinder(wConn);

		try
		{
			//#CM574590
			// execute search
			MovementSearchKey movementKey = new MovementSearchKey();

			//#CM574591
			// set search conditions
			setMovementSearchKey(movementKey);
			//#CM574592
			// set search order
			movementKey.setConsignorCodeOrder(1, true);
			movementKey.setLocationNoOrder(2, true);
			movementKey.setItemCodeOrder(3, true);
			movementKey.setResultLocationNoOrder(4, true);

			//#CM574593
			// Don't call print process if data does not exist
			if (movementFinder.search(movementKey) <= 0)
			{
				//#CM574594
				// 6003010 = Print data was not found.
				wMessage = "6003010";
				return false;
			}
			
			//#CM574595
			// create output file
			if (!createPrintWriter(FNAME_STOCKMOVE))
			{
				return false;
			}
			
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_STOCKMOVE);
            
			//#CM574596
			// Create data file until the search results are done
			Movement[] movement = null;
			TotalMovement[] totalMovement = null;
			while (movementFinder.isNext())
			{
				//#CM574597
				// Fetch search result
				movement = (Movement[]) movementFinder.getEntities(100);
				//#CM574598
				// Create print data from search result
				totalMovement = getSummarizeMovement(movement);
				
				for (int i = 0; i < totalMovement.length; i++)
				{
					//#CM574599
					// Fetch consignor name
					getDisplayConsignorName(totalMovement[i].getConsignorCode());
					//#CM574600
					// Fetch item name
					getItemName(totalMovement[i].getConsignorCode(), totalMovement[i].getItemCode());

					wStrText.append(re + "");

					//#CM574601
					// Consignor code
					wStrText.append(ReportOperation.format(totalMovement[i].getConsignorCode()) + tb);
					//#CM574602
					// Consignor name
					wStrText.append(ReportOperation.format(wConsignorName) + tb);
					//#CM574603
					// Relocation origin location
					wStrText.append(ReportOperation.format(totalMovement[i].getLocationNo()) + tb);
					//#CM574604
					// Item code
					wStrText.append(ReportOperation.format(totalMovement[i].getItemCode()) + tb);
					//#CM574605
					// Item name
					wStrText.append(ReportOperation.format(wItemName) + tb);
					//#CM574606
					// Packed qty per case
					wStrText.append(totalMovement[i].getEnteringQty() + tb);
					//#CM574607
					// Relocation case qty
					wStrText.append(DisplayUtil.getCaseQty(totalMovement[i].getTotalResultQty(), totalMovement[i].getEnteringQty(), totalMovement[i].getCasePieceFlag()) + tb);
					//#CM574608
					// Relocation piece qty
					wStrText.append(DisplayUtil.getPieceQty(totalMovement[i].getTotalResultQty(), totalMovement[i].getEnteringQty(), totalMovement[i].getCasePieceFlag()) + tb);
					//#CM574609
					// Relocation destination location
					if(totalMovement[i].getStatusFlag().equals(TotalMovement.STATUSFLAG_DELETE))
					{
						//#CM574610
						// LBL-W0393=*** Cancel ***
						wStrText.append(ReportOperation.format(DisplayText.getText("LBL-W0393")) + tb);
					}
					else
					{
						wStrText.append(ReportOperation.format(totalMovement[i].getResultLocationNo()) + tb);
					}
					//#CM574611
					// Expiry date
					wStrText.append(ReportOperation.format(totalMovement[i].getUseByDate()));

					//#CM574612
					// Output data to file
					wPWriter.print(wStrText);
					
					wStrText.setLength(0);

				}
			}

			//#CM574613
			// Close the stream
			wPWriter.close();

			//#CM574614
			// Execute UCXSingle. (Print)
			if (!executeUCX(JOBID_STOCKMOVE))
			{
				//#CM574615
				// Print failed. Refer to the log
				return false;
			}

			//#CM574616
			// If print succeeds, move the data file to backup folder
			ReportOperation.createBackupFile(wFileName);

		}
		catch (ReadWriteException re)
		{
			//#CM574617
			// 6007034 = Print failed. Refer to the log
			setMessage("6007034");

			return false;
		}
		finally
		{
			try
			{
				//#CM574618
				// Close the database cursor
				movementFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM574619
				// Database error occurred. Refer to the log
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM574620
	// Package methods -----------------------------------------------

	//#CM574621
	// Protected methods ---------------------------------------------

	//#CM574622
	// Private methods -----------------------------------------------
	//#CM574623
	/**
	 * This method sets the search conditions in the stock relocation search key
	 * @param searchKey MovementSearchKey search key for relocation work info
	 * @return search key with search conditions
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	private MovementSearchKey setMovementSearchKey(MovementSearchKey searchKey) throws ReadWriteException
	{
		//#CM574624
		// Set search key
		String[] jobNoArray = new String[wJobNo.size()];
		wJobNo.copyInto(jobNoArray);
		
		searchKey.setJobNo(jobNoArray);

		return searchKey;
	}

	//#CM574625
	/**
	 * Method to fetch the most recently registered consignor name<BR>
	 * @param pConsignorCode String Consignor code
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	private void getDisplayConsignorName(String pConsignorCode) throws ReadWriteException
	{
		//#CM574626
		// Create finder instance
		MovementReportFinder consignorFinder = new MovementReportFinder(wConn);
		MovementSearchKey searchKey = new MovementSearchKey();

		searchKey = setMovementSearchKey(searchKey);
		searchKey.setConsignorCode(pConsignorCode);
		searchKey.setConsignorNameCollect("");
		searchKey.setRegistDateOrder(1, false);
		
		//#CM574627
		// Execute search
		consignorFinder.open();
		if(consignorFinder.search(searchKey) > 0)
		{
			Movement[] movement = (Movement[]) consignorFinder.getEntities(1);

			if (movement != null && movement.length != 0)
			{
				wConsignorName = movement[0].getConsignorName();
			}
		}

		consignorFinder.close();
	}
	
	//#CM574628
	/**
	 * This method fetch the item name of the last update<BR>
	 * @param pConsignorCode String Consignor code
	 * @param pItemCode String Item code
	 * @throws ReadWriteException If abnormal error occurs in database connection
	 */
	private void getItemName(String pConsignorCode, String pItemCode) throws ReadWriteException
	{
		//#CM574629
		// Create finder instance
		MovementReportFinder itemFinder = new MovementReportFinder(wConn);
		MovementSearchKey serchKey = new MovementSearchKey();

		serchKey = setMovementSearchKey(serchKey);
		serchKey.setConsignorCode(pConsignorCode);
		serchKey.setItemCode(pItemCode);
		serchKey.setItemName1Collect("");
		serchKey.setRegistDateOrder(1, false);
		
		//#CM574630
		// Execute search
		itemFinder.open();
		if(itemFinder.search(serchKey) > 0)
		{
			Movement[] movement = (Movement[]) itemFinder.getEntities(1);

			if (movement != null && movement.length != 0)
			{
				wItemName = movement[0].getItemName1();
			}
		}

		itemFinder.close();
	}
	
	//#CM574631
	/**
	 * Create stock relocation info based on Collect Job No. and position<BR>
	 * Fetch packed qty per case with priority like [Case, None, Piece]<BR>
	 * @param pMovement Movement[] Stock relocation info to be grouped
	 * @return grouped stock relocation info
	 */
	private TotalMovement[] getSummarizeMovement(Movement[] pMovement)
	{
		TotalMovement[] resultMovement = null;
		TotalMovement tempMovement = null;
		Vector tempVec = new Vector();
		
		String collectJobNo = "";
		
		long movementQty = 0;
		String casePieceFlag = "";
		
		//#CM574632
		// grouping process
		for (int i = 0; i < pMovement.length; i++)
		{
			//#CM574633
			// If Collect Job no. is equal, accumulate work qty
			if (collectJobNo.equals(pMovement[i].getCollectJobNo()))
			{
				//#CM574634
				// accumulate relocation qty
				movementQty += pMovement[i].getResultQty();
						
				//#CM574635
				// Fetch packed qty per case with priority like [Case, None, Piece]
				//#CM574636
				// If the read type is "case"
				if (pMovement[i].getCasePieceFlag().equals(Movement.CASEPIECE_FLAG_CASE))
				{
					//#CM574637
					// Set packed qty per case in parameter
					tempMovement.setEnteringQty(pMovement[i].getEnteringQty());
					//#CM574638
					// set packed qty per case flag to case
					casePieceFlag = Movement.CASEPIECE_FLAG_CASE;
				}
				//#CM574639
				// If the read type is "none"
				else if (pMovement[i].getCasePieceFlag().equals(Movement.CASEPIECE_FLAG_NOTHING))
				{
					//#CM574640
					// If the type set already is "piece"
					if (casePieceFlag.equals(Movement.CASEPIECE_FLAG_PIECE))
					{
						//#CM574641
						// Set packed qty per case in parameter
						tempMovement.setEnteringQty(pMovement[i].getEnteringQty());
					}
				}
			}
			//#CM574642
			// If the Collect Job no. is not equal, create new data
			else
			{
				if (i != 0)
				{
					//#CM574643
					// set relocation qty
					tempMovement.setTotalResultQty(movementQty);
					
					//#CM574644
					// 
					tempVec.addElement(tempMovement);
				}
				
				tempMovement = new TotalMovement();
				
				//#CM574645
				// set value to check if it is the same data
				collectJobNo = pMovement[i].getCollectJobNo();
				//#CM574646
				// Clear accumulated variables
				movementQty = 0;
				
				//#CM574647
				// Stock qty
				movementQty = pMovement[i].getResultQty();
				
				//#CM574648
				// Consignor code
				tempMovement.setConsignorCode(pMovement[i].getConsignorCode());
				//#CM574649
				// Relocation origin location
				tempMovement.setLocationNo(pMovement[i].getLocationNo());
				//#CM574650
				// Item code
				tempMovement.setItemCode(pMovement[i].getItemCode()); 
				//#CM574651
				// Packed qty per case
				tempMovement.setEnteringQty(pMovement[i].getEnteringQty());
				//#CM574652
				// Relocation destination location
				tempMovement.setResultLocationNo(pMovement[i].getResultLocationNo());
				//#CM574653
				// Expiry date
				tempMovement.setUseByDate(pMovement[i].getResultUseByDate());
				tempMovement.setStatusFlag(pMovement[i].getStatusFlag());
				//#CM574654
				// Case piece type
				casePieceFlag = pMovement[i].getCasePieceFlag();
				
			}
			
		}
		//#CM574655
		// Set the remaining relocation work in a vector
		//#CM574656
		// set relocation qty
		tempMovement.setTotalResultQty(movementQty);
		tempVec.add(tempMovement);

		//#CM574657
		// set the grouping result to an array
		resultMovement = new TotalMovement[tempVec.size()];
		tempVec.copyInto(resultMovement);
		
		return resultMovement;
	}

}
//#CM574658
//end of class
