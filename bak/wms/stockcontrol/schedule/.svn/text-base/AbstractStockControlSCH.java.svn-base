package jp.co.daifuku.wms.stockcontrol.schedule;

//#CM10524
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.AbstractSCH;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.master.operator.AreaOperator;

//#CM10525
/**
 * Designer : Y.Kubo <BR>
 * Maker : Y.Kubo <BR>
 * <BR>
 * This is an abstract class and allows to execute the process for scheduling the inventory package.
 * Implement WmsScheduler interface and also implement the processes required for implementing this interface.
 * This class implements the common method. The class, which inherits this class, implements the individual behaviors for processing schedules, 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/17</TD><TD>Y.Kubo</TD><TD>Create New</TD></TR>
 * <TR><TD>2005/08/04</TD><TD>Y.Okamura</TD><TD>AbstractSCH was amended to allow to inherit.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/10 08:01:31 $
 * @author  $Author: suresh $
 */
public abstract class AbstractStockControlSCH extends AbstractSCH
{
	//#CM10526
	//	Class variables -----------------------------------------------
	//#CM10527
	/**
	 * Class Name
	 */
	public static final String CLASS_NAME = "AbstractStockControlSCH";
	
	//#CM10528
	// Class method --------------------------------------------------
	//#CM10529
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/10 08:01:31 $");
	}
	
	//#CM10530
	// Constructors --------------------------------------------------

	//#CM10531
	// Public methods ------------------------------------------------
	
	//#CM10532
	// Protected methods ---------------------------------------------
	//#CM10533
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in the inventory information, return the corresponding Consignor Code and Consignor Name.<BR>
	 * Obtain the Consignor name with the later added date/time.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.<BR>
	 * <BR>
	 * [Search conditions] <BR>
	 * <BR>
	 * Stock status��Center Inventory <BR>
	 * Ensure that the stock qty is 1 or more. <BR>
	 * <BR>
	 * @param conn Instance to maintain database connection.
 	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	protected Parameter consignorDisp(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();

		StockControlParameter dispData = new StockControlParameter();

		//#CM10534
		// Search Data
		//#CM10535
		// Stock status(Center Inventory)
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		//#CM10536
		// Ensure that the stock qty is 1 or more.
		searchKey.setStockQty(1, ">=");

		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM10537
			// Search Data			
			searchKey.KeyClear();
			//#CM10538
			// Stock status(Center Inventory)
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			//#CM10539
			// Ensure that the stock qty is 1 or more.
			searchKey.setStockQty(1, ">=");
			//#CM10540
			// Obtain the Consignor Name with the later added date/time.
			searchKey.setRegistDateOrder(1, false);
			
			searchKey.setConsignorCodeCollect();
			searchKey.setConsignorNameCollect();

			if (stockFinder.search(searchKey) > 0)
			{
				Stock[] consignorname = (Stock[]) stockFinder.getEntities(1);

				dispData.setConsignorCode(consignorname[0].getConsignorCode());
				dispData.setConsignorName(consignorname[0].getConsignorName());
				
			}
		}
		stockFinder.close();

		return dispData;
	}
	
	//#CM10541
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * Return the work date ( System defined date).<BR>
	 * If only one Consignor Code exists in the inventory information, return the corresponding Consignor Code and Consignor Name.<BR>
	 * Obtain the Consignor name with the later added date/time.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.<BR>
	 * <BR>
	 * [Search conditions] <BR>
	 * <BR>
	 * Stock status��Center Inventory <BR>
	 * Ensure that the stock qty is 1 or more. <BR>
	 * <BR>
	 * @param conn Instance to maintain database connection.
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter storagedateDisp(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		//#CM10542
		// Returned data
		StockControlParameter dispData = new StockControlParameter();

		//#CM10543
		// Work Date ( System defined date)
		dispData.setStorageDateString(getWorkDate(conn));
		
		StockReportFinder stockFinder = new StockReportFinder(conn);
		StockSearchKey searchKey = new StockSearchKey();
		//#CM10544
		// Set Search Condition
		searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
		searchKey.setStockQty(1, ">=");
		//#CM10545
		// Set Condition for Obtaining
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect("");

		if (stockFinder.search(searchKey) == 1)
		{
			//#CM10546
			// Search Data			
			searchKey.KeyClear();
			searchKey.setStatusFlag(Stock.STOCK_STATUSFLAG_OCCUPIED);
			searchKey.setStockQty(1, ">=");
			//#CM10547
			// Obtain the Consignor Name with the later added date/time.
			searchKey.setRegistDateOrder(1, false);
			
			searchKey.setConsignorCodeCollect();
			searchKey.setConsignorNameCollect();

			if (stockFinder.search(searchKey) > 0)
			{
				Stock[] consignor = (Stock[]) stockFinder.getEntities(1);

				dispData.setConsignorName(consignor[0].getConsignorName());
				dispData.setConsignorCode(consignor[0].getConsignorCode());

			}
		}
		stockFinder.close();
					
		return dispData;
		
	}

	//#CM10548
	/**
	 * Check that the contents of Worker Code and password are correct.<BR>
	 * Return true if the content is proper, or return false if improper.<BR>
	 * If the returned value is false,
	 * Require to obtain the result using <CODE>getMessage()</CODE> method.<BR>
	 * 
	 * @param  conn               Database connection object
	 * @param  searchParam    	   This parameter class includes contents to be checked for input.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 * @return Return true if the contents of Worker Code and password are proper, or return false if improper.
	 */
	protected boolean checkWorker(Connection conn, StockControlParameter searchParam) throws ReadWriteException, ScheduleException
	{
		
		String workerCode = searchParam.getWorkerCode();
		String password = searchParam.getPassword();
		
		return correctWorker(conn, workerCode, password);

	}
	
	//#CM10549
	/**
	 * Return values by reason when not allowing to display in the preset area.<BR>
	 * No target data: StockControlParameter[0]<BR>
	 * If exceeding the maximum count of data to be displayed: null<BR>
	 * <BR>
	 * <U>Use canLowerDisplay method of AbstractSCH class always before using this method.</U>
	 * 
	 * @return Values returned by each reason
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected StockControlParameter[] returnNoDisplayParameter() throws ScheduleException, ReadWriteException
	{
		if (getDisplayNumber() <= 0)
		{
			return new StockControlParameter[0];
		}
		
		if (getDisplayNumber() > WmsParam.MAX_NUMBER_OF_DISP)
		{
			return null;
		}		
		doScheduleExceptionHandling(CLASS_NAME);
		return null;
		
	}
	
	//#CM10550
	/**
	 * Check the input value.<BR>
	 * Return the check result. <BR>
	 * Enable to obtain the contents using <CODE>getMessage()</CODE> method.<BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * 1.Require to input any value within the designated range for Case/Piece division. <BR>
	 * <BR>
	 * 2.With Case/Piece division None, <BR>
	 *   <DIR>
	 *   Require to input Packed qty per Case if Case qty is input. <BR>
	 *   Require to input any value 1 or larger for Storage Case Qty or Storage Piece Qty. <BR>
	 *   </DIR>
	 * <BR>
	 * 3.With Case/Piece division Case <BR>
	 *   <DIR>
	 *   Requireto ensure that Packed qty per case is input. <BR>
	 *   Require to input any value 1 or larger for Storage Case Qty. <BR>
	 *   </DIR>
	 * <BR>
	 * 4.With Case/Piece division Piece, <BR>
	 *   <DIR>
	 *   Disable to input Storage Case Qty. <BR>
	 *   Require to input any value 1 or larger for Storage Piece Qty. <BR>
	 *   </DIR>
	 * <BR>
	 * @param  casepieceflag      Case/Piece division
	 * @param  enteringqty        Packed qty per Case
	 * @param  caseqty            Storage Case Qty
	 * @param  pieceqty           Storage Piece Qty
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 * @return true: if the input content is proper  false: if the input content is not proper
	 */
	protected boolean storageInputCheck(String casepieceflag, int enteringqty, int caseqty, int pieceqty) 
		throws ReadWriteException, ScheduleException
	{
		//#CM10551
		//	Check the Case/Piece division.
		if (!(casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING )
			|| casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_CASE )
			|| casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_PIECE )))
		{
			//#CM10552
			// 6023145 = Please enter value of specified range for the case/piece division.
			wMessage = "6023145";
			return false;
		}
		
		//#CM10553
		// With Case/Piece division None,
		if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM10554
			// Packed qty per Case is 1 or larger
			if(enteringqty > 0)
			{
				//#CM10555
				// If Storage Case Qty and Storage Piece Qty are 0,
				if(caseqty == 0 && pieceqty == 0)
				{
					//#CM10556
					// 6023198 = Please enter 1 or greater for the storage case/piece quantity.
					wMessage = "6023198";
					return false;
				}
			}
			else
			{
				//#CM10557
				// If Storage Case Qty is 1 or larger,
				if(caseqty > 0)
				{
					//#CM10558
					// 6023019 = Please enter 1 or greater in the packed quantity per case.
					wMessage = "6023019";
					return false;
				}
				//#CM10559
				// If Storage Case Qty and Storage Piece Qty are 0,
				else if(caseqty == 0 && pieceqty == 0)
				{
					//#CM10560
					// 6023198 = Please enter 1 or greater for the storage case/piece quantity.
					wMessage = "6023198";
					return false;
				}
			}
		}
		//#CM10561
		// With Case/Piece division Case
		else if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM10562
			// Packed qty per Case is 1 or larger
			if(enteringqty > 0)
			{
				//#CM10563
				// If Storage Case Qty is 0,
				if(caseqty == 0)
				{
					//#CM10564
					// 6023129 = Please enter the case quantity of storage.
					wMessage = "6023129";
					return false;
				}
			}
			else
			{
				//#CM10565
				// 6023019 = Please enter 1 or greater in the packed quantity per case.
				wMessage = "6023019";
				return false;
			}
		}
		//#CM10566
		// With Case/Piece division Piece,
		else if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM10567
			// If Storage Case Qty is 1 or larger,
			if(caseqty > 0)
			{
				//#CM10568
				// 6023285 = When Case/Piece Class is Piece
				wMessage = "6023285";
				return false;
			}
			else
			{
				//#CM10569
				// If Storage Piece Qty is 0,
				if(pieceqty == 0)
				{
					//#CM10570
					// 6023130 = Please enter the piece quantity of storage.
					wMessage = "6023130";
					return false;
				}
			}
		}
		
		return true;
	
	}
	
	//#CM10571
	/**
	 * Check the input value.<BR>
	 * Return the check result. <BR>
	 * Enable to obtain the contents using <CODE>getMessage()</CODE> method.<BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * 1.Require to input any value within the designated range for Case/Piece division. <BR>
	 * <BR>
	 * 2.With Case/Piece division None, <BR>
	 *   <DIR>
	 *   Require to input Packed qty per Case if Case qty is input. <BR>
	 *   Require to input any value 1 or larger for Stock Case Qty or Stock piece qty. <BR>
	 *   </DIR>
	 * <BR>
	 * 3.With Case/Piece division Case <BR>
	 *   <DIR>
	 *   Requireto ensure that Packed qty per case is input. <BR>
	 *   Require to input any value 1 or larger for Stock case qty. <BR>
	 *   </DIR>
	 * <BR>
	 * 4.With Case/Piece division Piece, <BR>
	 *   <DIR>
	 *   Disable to input Stock Case Qty. <BR>
	 *   Require to input any value 1 or larger for Stock piece qty. <BR>
	 *   </DIR>
	 * <BR>
	 * @param  casepieceflag      Case/Piece division
	 * @param  enteringqty        Packed qty per Case
	 * @param  caseqty            Stock Case Qty
	 * @param  pieceqty           stock piece qty
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 * @return true: if the input content is proper  false: if the input content is not proper
	 */
	protected boolean stockInputCheck(String casepieceflag, int enteringqty, int caseqty, int pieceqty) 
		throws ReadWriteException, ScheduleException
	{
		
		//#CM10572
		// Check the Case/Piece division.
		if (!casepieceflag.equals( StockControlParameter.CASEPIECE_FLAG_NOTHING ) &&
			 !casepieceflag.equals( StockControlParameter.CASEPIECE_FLAG_CASE ) &&
			 !casepieceflag.equals( StockControlParameter.CASEPIECE_FLAG_PIECE ) )
		{
			//#CM10573
			// 6023145 = Please enter value of specified range for the case/piece division.
			wMessage = "6023145";
			return false;
		}
		
		//#CM10574
		// With Case/Piece division None,
		if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING))
		{
			//#CM10575
			// Packed qty per Case is 1 or larger
			if(enteringqty > 0)
			{
				//#CM10576
				// If both stock case qty and stock piece qty are 0,
				if(caseqty == 0 && pieceqty == 0)
				{
					//#CM10577
					// 6023177 = Please enter 1 or greater for the case/piece stock quantity.
					wMessage = "6023177";
					return false;
				}
			}
			else
			{
				//#CM10578
				// Stock case qty is 1 or more.
				if(caseqty > 0)
				{
					//#CM10579
					// 6023019 = Please enter 1 or greater in the packed quantity per case.
					wMessage = "6023019";
					return false;
				}
				
				//#CM10580
				// If both stock case qty and stock piece qty are 0,
				if(caseqty == 0 && pieceqty == 0)
				{
					//#CM10581
					// 6023177 = Please enter 1 or greater for the case/piece stock quantity.
					wMessage = "6023177";
					return false;
				}
			}
		}
		//#CM10582
		// With Case/Piece division Case
		else if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			//#CM10583
			// Packed qty per Case is 1 or larger
			if(enteringqty > 0)
			{
				//#CM10584
				// If the stock case qty is 0,
				if(caseqty == 0)
				{
					//#CM10585
					// 6023282 = Please enter the value greater than 1 in the Stock Case Qty. field.
					wMessage = "6023282";
					return false;
				}
			}
			else
			{
				//#CM10586
				// 6023019 = Please enter 1 or greater in the packed quantity per case.
				wMessage = "6023019";
				return false;
			}
		}
		//#CM10587
		// With Case/Piece division Piece,
		else if (casepieceflag.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM10588
			// Stock case qty is 1 or more.
			if(caseqty > 0)
			{
				//#CM10589
				// 6023287 = When Case/Piece Class is Piece
				wMessage = "6023287";
				return false;
			}
			else
			{
				//#CM10590
				// If the stock piece qty is 0:
				if(pieceqty == 0)
				{
					//#CM10591
					// 6023283 = Please enter the value greater than 1 in the Stock Piece Qty. field.
					wMessage = "6023283";
					return false;
				}
			}
		}
		
		return true;
		
	}
	
	//#CM10592
	/**
	 * Check for input in the mode of Unplanned picking.<BR>
	 * Return the check result. <BR>
	 * Enable to obtain the contents using <CODE>getMessage()</CODE> method.<BR>
	 * Allow this method to execute the following processes. <BR>
	 * <BR>
	 * 1.For data with Case/Piece division Case or None,  <BR>
	 *   <DIR>
	 *   Ensure not to input case qty if the Packed qty per case is 0. <BR>
	 *   Require to input any value 1 or larger for piece qty, if the Packed qty per case is equal to 0. <BR>
	 *   Ensure to input Case qty or Piece qty if the Packed qty per case is 1 or larger. <BR>
	 *   </DIR>
	 * <BR>
	 * 2.With Case/Piece division Piece, <BR>
	 *   <DIR>
	 *   Ensure not to input any value 1 or more in the Require picking case qty. <BR>
	 *   Require to input any value 1 or larger for Picking piece qty. <BR>
	 *   </DIR>
	 * <BR>
	 * @param  pCasePieceFlag      Case/Piece division
	 * @param  pEnteringQty        Packed qty per Case
	 * @param  pCaseQty            Shipping Case Qty
	 * @param  pPieceQty           picking piece qty
	 * @param  pRowNo              Line No.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 * @return Return true if the input is perfect, or return false if imperfect.
	 */
	protected boolean stockRetrievalInputCheck(String pCasePieceFlag, int pEnteringQty, int pCaseQty, int pPieceQty, int pRowNo) 
		throws ReadWriteException, ScheduleException
	{
		//#CM10593
		// For data with Case/Piece division Case or None, 
		if (pCasePieceFlag.equals(StockControlParameter.CASEPIECE_FLAG_NOTHING)
		|| pCasePieceFlag.equals(StockControlParameter.CASEPIECE_FLAG_CASE))
		{
			if (pEnteringQty == 0)
			{
				if (pCaseQty > 0)
				{
					//#CM10594
					// 6023299=No.{0} If Packed Qty. per Case is 0
					wMessage = "6023299" + wDelim + pRowNo;
					return false;
				}
				else if (pPieceQty == 0)
				{
					//#CM10595
					// 6023273=No.{0} {1}
					//#CM10596
					// 6023336 = Enter 1 or more value in Picking Piece Qty.
					wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023336");
					return false;
				}
			}
			else
			{
				//#CM10597
				// If both picking case qty and picking piece qty are 0 (zero), 
				if (pCaseQty == 0 && pPieceQty == 0)
				{
					//#CM10598
					// 6023334 = Enter 1 or more value in Picking Case Qty./Picking Piece Qty.
					wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023334");
					return false;
				}
			}
		}
			
		//#CM10599
		// With Case/Piece division Piece,
		else if (pCasePieceFlag.equals(StockControlParameter.CASEPIECE_FLAG_PIECE))
		{
			//#CM10600
			// Picking case qty is 1 or more.
			if(pCaseQty > 0)
			{
				//#CM10601
				// 6023286 = When Case/Piece Class is Piece
				wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023286");
				return false;
			}
			
			//#CM10602
			// If picking piece qty is 0 (zero), 
			if(pPieceQty == 0)
			{
				//#CM10603
				// 6023336 = Enter 1 or more value in Picking Piece Qty.
				wMessage = "6023273" + wDelim + pRowNo + wDelim + MessageResource.getMessage("6023336");
				return false;
			}
		}
	
		return true;

	}

	//#CM10604
	/**
	 * Add the Sending Result Info table (DNHOSTSEND) <BR> 
	 * <BR>     
	 * Add the Sending Result Information based on the contents of the Received parameter. <BR>
	 * <BR>
	 * @param conn        Instance to maintain database connection.
	 * @param param       StockControlParameter class instance with contents that were input via screen.
	 * @param stockid     Stock ID
	 * @param workercode  Worker Code
	 * @param workername  Worker Name
	 * @param sysdate     Work Date ( System defined date)
	 * @param terminalno  Terminal No.
	 * @param jobtype     Work division
	 * @param processname Process name
	 * @param batchno     Batch No.
	 * @param tcdckbn	   TCDC division
	 * @param inputqty	   Work Result qty
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of scheduling.
	 * @return Return true when the process normally completed
	 */
	protected boolean createHostsend(
		Connection conn,
		StockControlParameter param,
		String stockid,
		String workercode,
		String workername,
		String sysdate,
		String terminalno,
		String jobtype,
		String processname,
		String batchno,
		String tcdckbn,
		int   inputqty)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			HostSendHandler hostsendHandler = new HostSendHandler(conn);
			HostSend hostsend = new HostSend();

			//#CM10605
			// Obtain each unique key to add.
			SequenceHandler sequence = new SequenceHandler(conn);
			//#CM10606
			// Work No.
			String jobno = sequence.nextJobNo();
			//#CM10607
			// Work Date
			hostsend.setWorkDate(sysdate);
			//#CM10608
			// Work No.
			hostsend.setJobNo(jobno);
			//#CM10609
			// Work division
			hostsend.setJobType(jobtype);
			//#CM10610
			// Aggregation Work No. (equal to Work No.)
			hostsend.setCollectJobNo(jobno);
			//#CM10611
			// Status flag (Completed)
			hostsend.setStatusFlag(HostSend.STATUS_FLAG_COMPLETION);
			//#CM10612
			// Start Work flag (Started)
			hostsend.setBeginningFlag(HostSend.BEGINNING_FLAG_STARTED);
			//#CM10613
			// Plan unique key
			hostsend.setPlanUkey("");
			//#CM10614
			// Stock ID
			hostsend.setStockId(stockid);
			//#CM10615
			// Area No.
			hostsend.setAreaNo(param.getAreaNo());
			//#CM10616
			// Location No.
			hostsend.setLocationNo(param.getLocationNo());
			//#CM10617
			// Planned date
			hostsend.setPlanDate(sysdate);
			//#CM10618
			// Consignor code
			hostsend.setConsignorCode(param.getConsignorCode());
			//#CM10619
			// Consignor name
			hostsend.setConsignorName(param.getConsignorName());
			//#CM10620
			// Supplier Code
			hostsend.setSupplierCode("");
			//#CM10621
			// Supplier Name
			hostsend.setSupplierName1("");
			//#CM10622
			// Receiving ticket No.
			hostsend.setInstockTicketNo("");
			//#CM10623
			// Receiving ticket Line No.
			hostsend.setInstockLineNo(0);
			//#CM10624
			// customer code
			hostsend.setCustomerCode(param.getCustomerCode());
			//#CM10625
			// customer name
			hostsend.setCustomerName1(param.getCustomerName());
			//#CM10626
			// Shipping Ticket No
			hostsend.setShippingTicketNo("");
			//#CM10627
			// Shipping ticket line No.
			hostsend.setShippingLineNo(0);
			//#CM10628
			// Item Code
			hostsend.setItemCode(param.getItemCode());
			//#CM10629
			// Item Name
			hostsend.setItemName1(param.getItemName());
			//#CM10630
			// Work Planned Qty(Host Planned Qty)
			hostsend.setHostPlanQty(0);
			//#CM10631
			// Work Planned Qty
			hostsend.setPlanQty(0);
			//#CM10632
			// Possible Work Qty
			hostsend.setPlanEnableQty(0);
			//#CM10633
			// Work Result qty
			hostsend.setResultQty(inputqty);
			//#CM10634
			// Work shortage qty
			hostsend.setShortageCnt(0);
			//#CM10635
			// Pending Qty
			hostsend.setPendingQty(0);
			//#CM10636
			// Packed qty per Case
			hostsend.setEnteringQty(param.getEnteringQty());
			//#CM10637
			// Packed qty per bundle
			hostsend.setBundleEnteringQty(param.getBundleEnteringQty());
			//#CM10638
			// Case/Piece division(Load Size)
			//#CM10639
			// Case/Piece division(Work type)
			if (StockControlParameter.CASEPIECE_FLAG_CASE.equals(param.getCasePieceFlag()))
			{
				hostsend.setCasePieceFlag(HostSend.CASEPIECE_FLAG_CASE);
				hostsend.setWorkFormFlag(HostSend.CASEPIECE_FLAG_CASE);
			}
			else if (StockControlParameter.CASEPIECE_FLAG_PIECE.equals(param.getCasePieceFlag()))
			{
				hostsend.setCasePieceFlag(HostSend.CASEPIECE_FLAG_PIECE);
				hostsend.setWorkFormFlag(HostSend.CASEPIECE_FLAG_PIECE);			
			}
			else if (StockControlParameter.CASEPIECE_FLAG_NOTHING.equals(param.getCasePieceFlag()))
			{
				hostsend.setCasePieceFlag(HostSend.CASEPIECE_FLAG_NOTHING);
				hostsend.setWorkFormFlag(HostSend.CASEPIECE_FLAG_NOTHING);			
			}

			//#CM10640
			// Case ITF
			hostsend.setItf(param.getITF());
			//#CM10641
			// Bundle ITF
			hostsend.setBundleItf(param.getBundleITF());
			//#CM10642
			// TC/DC Division
			hostsend.setTcdcFlag(tcdckbn);
			//#CM10643
			// Expiry Date
			hostsend.setUseByDate(param.getUseByDate());
			//#CM10644
			// Lot No.
			hostsend.setLotNo("");
			//#CM10645
			// Plan information Comment
			hostsend.setPlanInformation("");
			//#CM10646
			// Order No.
			hostsend.setOrderNo("");
			//#CM10647
			// Order Date
			hostsend.setOrderingDate("");
			//#CM10648
			// Expiry Date
			hostsend.setResultUseByDate(param.getUseByDate());
			//#CM10649
			// Lot No.
			hostsend.setResultLotNo("");
			//#CM10650
			// Work Result Location
			hostsend.setResultLocationNo(param.getLocationNo());
			//#CM10651
			// For data with Work division Increase Maintenance or Decrease Maintenance, regard its status as "Sent".
			if((jobtype.equals(SystemDefine.JOB_TYPE_MAINTENANCE_PLUS))
			|| (jobtype.equals(SystemDefine.JOB_TYPE_MAINTENANCE_MINUS)))
			{
				//#CM10652
				// Work report flag(Sent)
				hostsend.setReportFlag(HostSend.REPORT_FLAG_SENT);
			}
			else
			{
				//#CM10653
				// Work report flag(Not Sent)
				hostsend.setReportFlag(HostSend.REPORT_FLAG_NOT_SENT);
			}
			//#CM10654
			// Batch No.(Schedule No.)
			hostsend.setBatchNo(batchno);

			AreaOperator areaOpe = new AreaOperator(conn);
			//#CM10655
			// System distinct key
			hostsend.setSystemDiscKey(Integer.parseInt(areaOpe.getAreaType(param.getAreaNo())));
			//#CM10656
			// Worker Code
			hostsend.setWorkerCode(workercode);
			//#CM10657
			// Worker Name
			hostsend.setWorkerName(workername);
			//#CM10658
			// Terminal No.RFTNo.
			hostsend.setTerminalNo(terminalno);
			//#CM10659
			// Plan information Added Date/Time
			hostsend.setPlanRegistDate(null);
			//#CM10660
			// Added Date/Time
			hostsend.setRegistDate(new Date());
			//#CM10661
			// Name of Add Process
			hostsend.setRegistPname(processname);
			//#CM10662
			// Last update date/time
			hostsend.setLastUpdateDate(new Date());
			//#CM10663
			// Last update process name
			hostsend.setLastUpdatePname(processname);

			//#CM10664
			// Data addition
			hostsendHandler.create(hostsend);

			return true;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		} 
		catch (NumberFormatException e) 
		{
			throw new ScheduleException(e.getMessage());
		} 
	}
	



}
//#CM10665
//end of class
