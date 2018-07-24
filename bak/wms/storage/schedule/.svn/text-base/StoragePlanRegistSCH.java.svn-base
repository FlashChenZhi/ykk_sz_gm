package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.storage.dbhandler.StorageWorkingInformationHandler;

/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda  <BR>
 * <BR>
 * This class adds WEB storage plan data. <BR>
 * Receive the contents entered via screen as a parameter and execute the process for adding the storage plan data. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR><BR><DIR>
 * <BR>
 * <DIR>
 *   If only one Consignor Code exists in the Storage Plan info, return the corresponding Consignor Code and Consignor Name. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 *   <BR>
 *   [Search conditions] <BR>
 *   <BR>
 *   Status flag: Other than delete
 *   <BR>
 * </DIR>
 * 2.Process by clicking on the Input button(<CODE>check()</CODE>Method) <BR><BR><DIR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and check for mandatory, overflow and duplication and return the checking results. <BR>
 *   Finding no same corresponding data found in the Storage Plan info returns true.Return false when condition error occurs or corresponding data exists. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *   Check for duplication using Consignor code, Planned storage date, item code, storage location, status flag, and Add division as keys. <BR>
 *   If same data with status flag Standby or Deleted found, do not regard such data as duplicate error. Return true. <BR>
 *   Require to set the Add division for Host data or Terminal data. <BR>
 *   For data with Case/Piece division Case or Piece,Set the storage location, which was input via screen, for both Piece Item Storage Location and Case Item Storage Location.<BR>
 *   With Case/Piece division None,Set the Storage location entered via screen for Piece Item Storage Location.<BR>
 *   <BR>
 *   [Parameter] *Mandatory Input <BR>
 *   <BR>
 *   Consignor code* <BR>
 *   Consignor name <BR>
 *   Planned storage date* <BR>
 *   Item Code* <BR>
 *   Item Name <BR>
 *   Case/Piece division* <BR>
 *   Location No <BR>
 *   Packed qty per Case <BR>
 *   Packed qty per bundle <BR>
 *   Planned Case Qty <BR>
 *   Planned Piece Qty <BR>
 *   Case ITF <BR>
 *   Bundle ITF <BR>
 *   <BR>
 *   [Check for process condition] <BR>
 *   <BR>
 *   1.Ensure that the Packed qty per Case is 1 or larger if any value is entered in the Case qty. <BR>
 *   2.Require total input value in the planned case and the planned piece to be 1 or more. <BR>
 *
 *   <BR>
 * </DIR>
 * <BR>
 * 3.Process by clicking on Add button (<CODE>startSCH()</CODE>Method) <BR><BR><DIR>
 * <DIR>
 *   Receive the contents displayed in the preset area as a parameter and execute the process to add the storage plan data. <BR>
 *   Return true when the process normally completed, or return false when failed to schedule completely due to condition error or other causes. <BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *   <BR>
 *   [Parameter] *Mandatory Input <BR>
 *   <BR>
 *   Worker Code* <BR>
 *   Password* <BR>
 *   Consignor code* <BR>
 *   Consignor name* <BR>
 *   Planned storage date* <BR>
 *   Item Code* <BR>
 *   Item Name* <BR>
 *   Case/Piece division* <BR>
 *   Location No* <BR>
 *   Packed qty per Case* <BR>
 *   Packed qty per bundle* <BR>
 *   Planned Case Qty* <BR>
 *   Planned Piece Qty* <BR>
 *   Case ITF* <BR>
 *   Bundle ITF* <BR>
 *   Preset Line No* <BR>
 *   <BR>
 *   [Check for process condition] <BR>
 *   <BR>
 *   1.Ensure to define Worker code and password in the Worker master. <BR>
 *     <DIR>
 *     Check only the leading value of the array for the values of Worker code and password. <BR>
 *     </DIR>
 *   <BR>
 *   2.Ensure that two or more same Storage Plan info do not exist in the database. (exclusion check) <BR>
 *     <DIR>
 *     Note) Do not regard Storage Plan info with status flag Partially Completed, Completed, or Processing as a duplicate target data. <BR>
 *     Note) Do not regard Storage Plan info with Add division "Linked to Receiving" as a duplicate target data. <BR>
 *     </DIR>
 *   <BR>
 *   [Update Process] <BR>
 *   <BR>
 *   -Update the table in the sequence of Work status, Storage Plan info, and then inventory information to prevent from deadlocking. <BR>
 *   1.Search for the storage Plan info using Consignor code, Planned storage date, Item code, Case/Piece division, Storage location, Status flag, Add division as keys. <BR>
 *   <BR>
 *   [Submit/Add Process] <BR>
 *   <BR>
 *   -Add Work Status Table (DNWORKINFO). <BR>
 *     <DIR>
 *     Add the Work status based on the Storage Plan info and the inventory information contents added this time. <BR>
 *     </DIR>
 *   <BR>
 *   -Update of Storage Plan information table (DNSTORAGEPLAN)adding <BR>
 *     <DIR>
 *     Add the Storage Plan info based on the contents of the Received parameter, <BR>
 *     </DIR>
 *   <BR>
 *   -Add Inventory Information Table (DMSTOCK). <BR>
 *     <DIR>
 *     Add the inventory information based on the contents of Storage Plan info added this time. <BR>
 *     </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/13</TD><TD>K.Toda</TD><TD>Create New</TD></TR>
 * <TR><TD>2005/05/16</TD><TD>T.Nakajima</TD><TD>This was solved by adding the Add division "2: Linked to Receiving".</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:23:11 $
 * @author  $Author: suresh $
 */
public class StoragePlanRegistSCH extends AbstractStorageSCH
{

	// Class variables -----------------------------------------------
	/**
	 * Process name
	 */
	private static String wProcessName = "StoragePlanRegistSCH";

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.3 $,$Date: 2006/11/21 04:23:11 $" );
	}
	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StoragePlanRegistSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in the Storage Plan info, return the corresponding Consignor Code and Consignor Name.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageParameter</CODE> throws ScheduleException.<BR>
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{

		// Set the corresponding Consignor code.
		StorageSupportParameter wParam = new StorageSupportParameter();

		// Generate Planned Storage Information instance of handlers.
		StoragePlanSearchKey wKey = new StoragePlanSearchKey();
		StoragePlanReportFinder wObj = new StoragePlanReportFinder( conn );
		StoragePlan[] wStorageName = null;

		try
		{
			// Set the search conditions.
			// Status flag= other than "Delete "
			wKey.setStatusFlag( StoragePlan.STATUS_FLAG_DELETE, "!=" );
			// Set the Consignor Code for grouping condition.
			wKey.setConsignorCodeGroup( 1 );
			wKey.setConsignorCodeCollect( "DISTINCT" );

			wObj.open();
			// If the count is one,
			if ( wObj.search(wKey) == 1 )
			{
				// Set the search conditions.
				wKey.KeyClear();
				// Status flag= other than "Delete "
				wKey.setStatusFlag( StoragePlan.STATUS_FLAG_DELETE, "!=" );
				// Set the added date/time for the sorting order.
				wKey.setRegistDateOrder( 1, false );

				wKey.setConsignorCodeCollect();
				wKey.setConsignorNameCollect();

				// Obtain the Consignor Name with the last added date/time.
				if ( wObj.search( wKey ) > 0 )
				{
					wStorageName = (StoragePlan[])wObj.getEntities(1);
					wParam.setConsignorName( wStorageName[ 0 ].getConsignorName() );
					wParam.setConsignorCode( wStorageName[ 0 ].getConsignorCode() );
				}
			}
			wObj.close();

		}
		catch ( ReadWriteException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}

		return wParam;
	}

	/**
	 * Receive the contents entered via screen as a parameter and return the result of checking for presence of corresponding data.<BR>
	 * Finding no same corresponding data found in the Storage Plan info returns true. <BR>
	 * Return false when condition error occurs or corresponding data exists. <BR>
	 * If there is any same data with status flag Standby or Completed, return true instead of regarding as duplicate error. <BR>
	 * @param conn Instance to maintain database connection.
	 * @param checkParam Instance of <CODE>StorageParameter</CODE> class with input contents. <BR>
	 *         Designating any instance other than StorageParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException If abnormal error occurs during database connection
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean check( Connection conn, Parameter checkParam ) throws ScheduleException, ReadWriteException
	{

		// Generate Planned Storage Information instance of handlers.
		StoragePlanSearchKey wKey = new StoragePlanSearchKey();
		StoragePlanHandler wObj = new StoragePlanHandler( conn );
		StoragePlan[] wStorage = null;

		try
		{
			// Search conditions for parameters
			StorageSupportParameter wParam = ( StorageSupportParameter )checkParam;

			// Set the search conditions.
			// Consignor code
			wKey.setConsignorCode( wParam.getConsignorCode() );
			// Planned storage date
			wKey.setPlanDate( wParam.getStoragePlanDate () );
			// Item Code
			wKey.setItemCode( wParam.getItemCode() );
			// Case/Piece division
			wKey.setCasePieceFlag( wParam.getCasePieceflg() );
			// Storage Location
			if (wParam.getCasePieceflg().equals(StoragePlan.CASEPIECE_FLAG_CASE) || wParam.getCasePieceflg().equals(StoragePlan.CASEPIECE_FLAG_PIECE))
			{
				// For data with Case/Piece division Case or Piece,
				// Set the storage location, which was input via screen, for both Piece Item Storage Location and Case Item Storage Location.
				wKey.setPieceLocation(wParam.getStorageLocation());
				wKey.setCaseLocation(wParam.getStorageLocation());
			}
			else if(wParam.getCasePieceflg().equals(StoragePlan.CASEPIECE_FLAG_NOTHING))
			{
				// With Case/Piece division None,
				// Set the Storage location entered via screen for Piece Item Storage Location.
				wKey.setPieceLocation(wParam.getStorageLocation());
			}

			// Status flag (other than Standby)
			wKey.setStatusFlag( StoragePlan.STATUS_FLAG_UNSTART, "!=", "(", "", "AND" );
			// Status flag (other than Deleted)
			wKey.setStatusFlag( StoragePlan.STATUS_FLAG_DELETE, "!=", "", "", "AND" );
			// Status flag (other than Completed)
			wKey.setStatusFlag( StoragePlan.STATUS_FLAG_COMPLETION, "!=", "", ")", "AND" );

			// Obtain the Search result count of Storage Plan information.
			wStorage = ( StoragePlan[] )wObj.find( wKey );
			if ( wStorage != null && wStorage.length > 0 )
			{
				return false;
			}
		}
		catch ( ReadWriteException e )
		{
			throw new ScheduleException( e.getMessage() );
		}

		return true;
	}

	/**
	 * Receive the contents entered via screen as a parameter and <BR>
	 * Check for mandatory, overflow and duplication and return the checking results. <BR>
	 * Finding no same corresponding data found in the Storage Plan info returns true. <BR>
	 * Regard such a case as an exclusion error where condition error occurs or the corresponding data exists, and return false. <BR>
	 * If there is any same data with status flag Standby or Completed, return true instead of regarding as exclusion error. <BR>
	 * @param conn Instance to maintain database connection.
	 * @param checkParam Instance of <CODE>StorageParameter</CODE> class with input contents. <BR>
	 *         Designating any instance other than StorageParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @param inputParams Array of <CODE>StorageParameter</CODE> class instance with the contents in the preset area.<BR>
	 *         Designating any instance other than StorageParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException If abnormal error occurs during database connection
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean check( Connection conn, Parameter checkParam, Parameter[] inputParams )
		throws ScheduleException, ReadWriteException
	{
		// Generate Planned Storage Information instance of handlers.
		StoragePlanSearchKey wKey = new StoragePlanSearchKey();
		StoragePlanHandler wObj = new StoragePlanHandler( conn );
		StoragePlan[] wStorage = null;

		try
		{
			// Contents of the input area
			StorageSupportParameter wParam = ( StorageSupportParameter )checkParam;
			// Contents of Preset area
			StorageSupportParameter[] wParamList = ( StorageSupportParameter[] )inputParams;

			// Check the Worker code and password
			if (!checkWorker(conn, wParam))
			{
				return false;
			}

			// Check for mandatory input based on the condition.
			if (!storageInputCheck(wParam.getCasePieceflg(), wParam.getEnteringQty(), wParam.getPlanCaseQty(), wParam.getPlanPieceQty()))
			{
				return false;
			}

			// Execute the overflow check.
			if ( ( long )wParam.getEnteringQty() * ( long )wParam.getPlanCaseQty() + ( long )wParam.getPlanPieceQty() > WmsParam.MAX_TOTAL_QTY )
			{
				// 6023058 = Please enter {1} or smaller for {0}.
				wMessage = "6023058" + wDelim + DisplayText.getText( "LBL-W0377" ) + wDelim + MAX_TOTAL_QTY_DISP;
				return false;
			}

			// Check the count of data to be displayed.
			if ( wParamList != null && wParamList.length + 1 > WmsParam.MAX_NUMBER_OF_DISP )
			{
				// 6023096 = More than {0} data exist. Data cannot be entered.
				wMessage = "6023096" + wDelim + MAX_NUMBER_OF_DISP_DISP;
				return false;
			}


			// Check for duplication in the preset area.
			if ( wParamList != null )
			{
				for ( int i = 0; i < wParamList.length; i ++ )
				{
					if (wParamList[i].getConsignorCode().equals(wParam.getConsignorCode())
					&&  wParamList[i].getStoragePlanDate().equals(wParam.getStoragePlanDate())
					&&  wParamList[i].getItemCode().equals(wParam.getItemCode())
					&&  wParamList[i].getCasePieceflg().equals(wParam.getCasePieceflg())
					&&  wParamList[i].getStorageLocation().equals(wParam.getStorageLocation()))
					{
						// 6023090 = The data already exists.
						wMessage = "6023090";
						return false;
					}
				}
			}


			// Check for duplication across the DB.
			// Set the search conditions.
			// Consignor code
			wKey.setConsignorCode( wParam.getConsignorCode() );
			// Planned storage date
			wKey.setPlanDate( wParam.getStoragePlanDate() );
			// Item Code
			wKey.setItemCode( wParam.getItemCode() );
			// Storage Location
			if (wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
			{
				wKey.setCaseLocation(wParam.getStorageLocation());
			}
			else if (wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
			{
				wKey.setPieceLocation(wParam.getStorageLocation());
			}
			else if(wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
			{
				wKey.setPieceLocation(wParam.getStorageLocation());
				wKey.setPieceLocation(wParam.getStorageLocation(), "=","(", "", "OR");
				wKey.setCaseLocation(wParam.getStorageLocation(), "=","", ")", "AND");
			}
			// Partially Completed, Processing, Completed
			String status[] = {StoragePlan.STATUS_FLAG_COMPLETE_IN_PART, StoragePlan.STATUS_FLAG_NOWWORKING, StoragePlan.STATUS_FLAG_COMPLETION};
			wKey.setStatusFlag(status);
			// Data with Add division other than "2: Linked to Receiving"
			wKey.setRegistKind(StoragePlan.REGIST_KIND_INSTOCK, "!=");

			// Obtain the Search result of Storage Plan information.
			wStorage = ( StoragePlan[] )wObj.find( wKey );


			WorkingInformationHandler wiHandle = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey wiKey = new WorkingInformationSearchKey();

			for ( int i = 0; i < wStorage.length; i++ )
			{
				// If the value in the database is complex:
				if ( wStorage[i].getCasePieceFlag().equals( StoragePlan.CASEPIECE_FLAG_MIX ) )
				{

					wiKey.KeyClear();
					// Status (Processing, Completed)
					String[] checkstatus = { WorkingInformation.STATUS_FLAG_NOWWORKING, WorkingInformation.STATUS_FLAG_COMPLETE_IN_PART, WorkingInformation.STATUS_FLAG_COMPLETION };
					wiKey.setPlanUkey(wStorage[i].getStoragePlanUkey());
					wiKey.setStatusFlag(checkstatus);
					if (wiHandle.count(wiKey) > 0)
					{
						if (wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
						{
							if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_NOWWORKING ) )
							{
								// 6023269 = You cannot enter the data. The same data in process of work exists.
								wMessage = "6023269";
								return false;
							}
							else if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_COMPLETE_IN_PART ) )
							{
								// 6023270 = You cannot enter the data. The same data which is Partly Received exists.
								wMessage = "6023270";
								return false;
							}
							else if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_COMPLETION ))
							{
								// 6023337=Cannot enter. The same data is completed.
								wMessage = "6023337";
								return false;
							}
						}
						else if (wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
						{
							if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_NOWWORKING ) )
							{
								// 6023269 = You cannot enter the data. The same data in process of work exists.
								wMessage = "6023269";
								return false;
							}
							else if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_COMPLETE_IN_PART ) )
							{
								// 6023270 = You cannot enter the data. The same data which is Partly Received exists.
								wMessage = "6023270";
								return false;
							}
							else if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_COMPLETION ))
							{
								// 6023337=Cannot enter. The same data is completed.
								wMessage = "6023337";
								return false;
							}
						}
					}
				}
				// Database value; Case based
				else if (wStorage[i].getCasePieceFlag().equals( StoragePlan.CASEPIECE_FLAG_CASE ))
				{
					if (wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
					{
						if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_NOWWORKING ) )
						{
							// 6023269 = You cannot enter the data. The same data in process of work exists.
							wMessage = "6023269";
							return false;
						}
						else if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_COMPLETE_IN_PART ) )
						{
							// 6023270 = You cannot enter the data. The same data which is Partly Received exists.
							wMessage = "6023270";
							return false;
						}
						else if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_COMPLETION ))
						{
							// 6023337=Cannot enter. The same data is completed.
							wMessage = "6023337";
							return false;
						}
					}
				}
				// Database value: Piece based
				else if (wStorage[i].getCasePieceFlag().equals( StoragePlan.CASEPIECE_FLAG_PIECE ))
				{
					if (wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
					{
						if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_NOWWORKING ) )
						{
							// 6023269 = You cannot enter the data. The same data in process of work exists.
							wMessage = "6023269";
							return false;
						}
						else if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_COMPLETE_IN_PART ) )
						{
							// 6023270 = You cannot enter the data. The same data which is Partly Received exists.
							wMessage = "6023270";
							return false;
						}
						else if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_COMPLETION ))
						{
							// 6023337=Cannot enter. The same data is completed.
							wMessage = "6023337";
							return false;
						}
					}
				}
				// Database value; None
				else if (wStorage[i].getCasePieceFlag().equals( StoragePlan.CASEPIECE_FLAG_NOTHING ))
				{
					if (wParam.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
					{
						if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_NOWWORKING ) )
						{
							// 6023269 = You cannot enter the data. The same data in process of work exists.
							wMessage = "6023269";
							return false;
						}
						else if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_COMPLETE_IN_PART ) )
						{
							// 6023270 = You cannot enter the data. The same data which is Partly Received exists.
							wMessage = "6023270";
							return false;
						}
						else if ( wStorage[i].getStatusFlag().equals( StoragePlan.STATUS_FLAG_COMPLETION ))
						{
							// 6023337=Cannot enter. The same data is completed.
							wMessage = "6023337";
							return false;
						}
					}
				}
			}


		}
		catch ( ReadWriteException e )
		{
			throw new ScheduleException( e.getMessage() );
		}

		// 6001019 = Entry was accepted.
		wMessage = "6001019";

		return true;
	}

	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for adding the Storage Plan data.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Return true if the schedule normally completed, or return false if failed.
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StorageParameter</CODE> classinstance with contents of commitment. <BR>
	 *         Designating any instance other than StorageParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean startSCH( Connection conn, Parameter[] startParams ) throws ReadWriteException, ScheduleException
	{
		boolean registFlag = false;

		// This flag determines whether "Processing Extraction" flag is updated in its own class.
		boolean updateLoadDataFlag = false;

		try
		{
			// Input information of the parameter
			StorageSupportParameter[] wParam = ( StorageSupportParameter[] )startParams;

			if (wParam == null)
			{
				// 6006001=Unexpected error occurred.{0}
				RmiMsgLogClient.write("6006001" + wDelim + wProcessName, this.getClass().getName());
				// 6006001=Unexpected error occurred.{0}
				throw new ScheduleException("6006001" + wDelim + wProcessName);
			}

			// Check the Worker code and password
			if (!checkWorker(conn, wParam[0]))
			{
				return false;
			}

			// Check the Daily Update Processing.
			if (isDailyUpdate(conn))
			{
				return false;
			}

			// Extraction flag check process true: Processing Extract
			if (isLoadingData(conn))
			{
				return false;
			}
			// Update the extraction flag: "Processing Extract"
			if (!updateLoadDataFlag(conn, true))
			{
				return false;
			}
			doCommit(conn, wProcessName);
			updateLoadDataFlag = true;

			StorageWorkingInformationHandler wiHandle = new StorageWorkingInformationHandler(conn);

			if (!wiHandle.lockPlanData(startParams))
			{
				// 6003006 = Unable to process this data. It has been updated via other work station.
				wMessage = "6003006";
				return false;
			}

			// Obtain the Worker name.
			String workerName = "";
			workerName = getWorkerName(conn, wParam[0].getWorkerCode());
			// Obtain the batch No.
			SequenceHandler sequenceHandler = new SequenceHandler(conn);
			String batch_seqno = "";
            batch_seqno = sequenceHandler.nextStoragePlanBatchNo();

			StoragePlanOperator storagePlanOperator = new StoragePlanOperator(conn);
			StoragePlan[] storagePlan = null;

			for ( int i = 0; i < wParam.length; i++ )
			{
				// Set the Storage place.
				if (!wParam[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
				{
					wParam[i].setCaseLocation(wParam[i].getStorageLocation());
				}
				else
				{
					wParam[i].setCaseLocation("");
				}
				wParam[i].setPieceLocation(wParam[i].getStorageLocation());
				// Set the Add division.
				wParam[i].setRegistKbn(StoragePlan.REGIST_KIND_WMS);
				// Obtain the Storage Plan info already added.
				storagePlan = storagePlanOperator.findStoragePlanForUpdate(wParam[i]);

				// If it was already added, change the existing data to Deleted status and then add it.
				if (storagePlan != null && storagePlan.length != 0)
				{
					for (int j = 0; j < storagePlan.length; j++)
					{
						// Disable to add any data with data with Partially Completed.
						if (storagePlan[j].getStatusFlag().equals(StoragePlan.STATUS_FLAG_COMPLETE_IN_PART))
						{
							// 6023273=No.{0}{1}
							// 6023270 = You cannot enter the data. The same data which is Partly Received exists.
							wMessage = "6023273" + wDelim + wParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023270");
							return false;
						}
						// Disable to add any data with status Started or Processing.
						else if ((storagePlan[j].getStatusFlag().equals(StoragePlan.STATUS_FLAG_START)) || (storagePlan[j].getStatusFlag().equals(StoragePlan.STATUS_FLAG_NOWWORKING)))
						{
							// 6023273=No.{0}{1}
							// 6023269=You cannot enter the data. The same data in process of work exists.
							wMessage = "6023273" + wDelim + wParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023269");
							return false;
						}
						// Disable to add any data with status "Completed".
						else if (storagePlan[j].getStatusFlag().equals(StoragePlan.STATUS_FLAG_COMPLETION))
						{
							// 6023273=No.{0}{1}
							// 6023090 = The data already exists.
							wMessage = "6023273" + wDelim + wParam[i].getRowNo() + wDelim + MessageResource.getMessage("6023090");
							return false;
						}

						// Update each Status of Storage Plan information, Work status, and inventory information to Delete.
						storagePlanOperator.updateStoragePlan(storagePlan[j].getStoragePlanUkey(), wProcessName);
					}
				}

				// Set the value to be added for the parameter.
				// Storage Planned Qty(Total Bulk Qty)
				wParam[i].setTotalPlanQty(wParam[i].getPlanCaseQty() * wParam[i].getEnteringQty() + wParam[i].getPlanPieceQty());
				// Worker Code
				wParam[i].setWorkerCode(wParam[0].getWorkerCode());
				// Worker Name
				wParam[i].setWorkerName(workerName);
				// Batch No.
				wParam[i].setBatchNo(batch_seqno);
				// Terminal No.
				wParam[i].setTerminalNumber(wParam[0].getTerminalNumber());
				// Add division
				wParam[i].setRegistKbn(SystemDefine.REGIST_KIND_WMS);
				// Name of Add Process
				wParam[i].setRegistPName(wProcessName);

				// Create a new input information (Storage Plan information, Work status, inventory information)
				storagePlanOperator.createStoragePlan(wParam[i]);
			}

			// 6001003 = Added.
			wMessage = "6001003";
			registFlag = true;
			return registFlag;

		}
		catch (ReadWriteException e)
		{
			doRollBack(conn, wProcessName);
			throw new ReadWriteException(e.getMessage());
		}
		catch (Exception e)
		{
			doRollBack(conn, wProcessName);
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			// Failing to add rolls back the transaction.
			if (!registFlag)
			{
				doRollBack(conn, wProcessName);
			}
			// If "Processing Extraction" flag was updated in its own class,
			// change the Processing Extract flag to 0: Stopping.
			if( updateLoadDataFlag )
			{
				// Update the Pricessubg Extraction flag: Stopping
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
		}
	}

	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	/**
	 * Receive the Storage Plan info as a parameter and add it to the inventory information based on the contents.
	 * Search through the inventory information using the stock ID of the parameter as a key. If corresponding data exists, lock the target record and
	 * add a new inventory information. Decrease the Storage Planned qty respectively from the stock qty and the allocated qty in the record that was locked after added. Then update the stock qty and the allocated qty to the decreased value.
	 * @param conn     Instance to maintain database connection.
	 * @param storage Instance of StoragePlan class with contents of Storage Plan info.
	 * @param workinfo Instance of WorkingInformation class with contents of Work Status.(Use this when searching existing data)
	 * @param wStockId Newly numbered Stock ID
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected void createStock( Connection conn, StoragePlan storage, WorkingInformation[] workinfo, String wStockId )
		throws ReadWriteException
	{
		// Generate inventory info instance of handlers.
		StockHandler wObj = new StockHandler( conn );
		StockSearchKey wKey = new StockSearchKey();
		Stock[] wFindData = null;
		Stock wStock = new Stock();

		// Generate Vector class instance.
		Vector vec = new Vector();

		// Generate instance of WareNaviSystem handlers.
		WareNaviSystemSearchKey wWKey = new WareNaviSystemSearchKey();
		WareNaviSystemHandler wWObj = new WareNaviSystemHandler( conn );

		try
		{
			if ( workinfo != null )
			{
				for ( int i = 0; i < workinfo.length; i ++ )
				{
					Stock temp = new Stock();
					// Set the search conditions.
					wKey.KeyClear();
					// Stock ID
					wKey.setStockId( workinfo[ i ].getStockId() );
					// Lock the inventory information linked to Stock ID of the parameter.
					temp = ( Stock )wObj.findPrimaryForUpdate( wKey );

					// Storage Planned Qty (Decrease the Storage Planned Qty)
					temp.setPlanQty(temp.getPlanQty() - workinfo[i].getPlanQty());
					vec.addElement( temp );
				}
				wFindData = new Stock[ vec.size() ];
				vec.copyInto( wFindData );
			}

			// Execute the process for adding the inventory information.
			wWObj.find( wWKey );
			// Stock ID
			wStock.setStockId( wStockId );
			// Plan unique key
			wStock.setPlanUkey(storage.getStoragePlanUkey());
			// Area No.
			wStock.setAreaNo("");

			// Location No.
			// Ensuring that any value is always set in the Piece Item Storage Location regardless the Case/Piece division,
			// set the value of Piece Item Storage Location for the Location No.
			wStock.setLocationNo( storage.getPieceLocation() );

			// Item Code
			wStock.setItemCode( storage.getItemCode() );
			// Item Name
			wStock.setItemName1( storage.getItemName1() );
			// Stock status
			wStock.setStatusFlag( Stock.STOCK_STATUSFLAG_RECEIVINGRESERVE );
			// Stock Qty
			wStock.setStockQty(0);
			// allocated qty
			wStock.setAllocationQty(0);
			// Planned Qty
			wStock.setPlanQty( storage.getPlanQty() );
			// Case/Piece division(Load Size)
			wStock.setCasePieceFlag( storage.getCasePieceFlag() );
			// Storage Date
			wStock.setInstockDate( null );
			// Last Picking Date
			wStock.setLastShippingDate("");
			// Expiry Date
			wStock.setUseByDate("");
			// Lot No.
			wStock.setLotNo("");
			// Plan information Comment
			wStock.setPlanInformation("");
			// Consignor code
			wStock.setConsignorCode( storage.getConsignorCode() );
			// Consignor name
			wStock.setConsignorName( storage.getConsignorName() );
			// Supplier Code
			wStock.setSupplierCode( storage.getSupplierCode() );
			// Supplier Name
			wStock.setSupplierName1( storage.getSupplierName1() );
			// Packed qty per Case
			wStock.setEnteringQty( storage.getEnteringQty() );
			// Packed qty per bundle
			wStock.setBundleEnteringQty( storage.getBundleEnteringQty() );
			// Case ITF
			wStock.setItf( storage.getItf() );
			// Bundle ITF
			wStock.setBundleItf( storage.getBundleItf() );
			// Name of Add Process
			wStock.setRegistPname( wProcessName );
			// Last update process name
			wStock.setLastUpdatePname( wProcessName );
			// Add inventory information
			wObj.create( wStock );

			// Execute the process for updating the inventory information.
			if ( wFindData != null && wFindData.length > 0 )
			{
				// Update the Stock qty and Allocated qty of inventory information linked to Stock ID of the parameter.
				StockAlterKey wAKey = new StockAlterKey();
				for ( int i = 0; i < wFindData.length; i ++ )
				{
					// Set the update condition.
					wAKey.KeyClear();
					// Stock ID
					wAKey.setStockId( wFindData[ i ].getStockId() );

					// Set the update value.
					// Storage Planned Qty (Decrease the Storage Planned Qty)
					wAKey.updatePlanQty(wFindData[i].getPlanQty());
					// Stock status(Completed)
					wAKey.updateStatusFlag( Stock.STOCK_STATUSFLAG_COMPLETE );
					// Last update process name
					wAKey.updateLastUpdatePname( wProcessName );
					// Update
					wObj.modify( wAKey );
				}
			}
		}
		catch ( DataExistsException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( InvalidDefineException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( InvalidStatusException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NotFoundException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NoPrimaryException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( ReadWriteException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
	}

	/**
	 * Receive the contents entered via screen as a parameter and add the Work status based on the contents.
	 * Search through the Work status using the Storage Plan unique key of parameter as a key. If the corresponding Work status exists, lock the target record and
	 * add a New Work status. Update the Status flag of the target record locked after added to Deleted.
	 * @param conn          Instance to maintain database connection.
	 * @param param         StorageSupportParameter class instance with contents that were input via screen.
	 * @param wKey          The already added Storage Plan unique key
	 * @param wJobNo        Newly numbered Work No.
	 * @param wStorageUkey  Newly numbered Storage Plan unique key
	 * @param wStockId      Newly numbered Stock ID
	 * @param wBatchNo      Newly numbered Batch No.
	 * @return Work Status linked with Storage Plan unique key already added
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected WorkingInformation[] createWorkInfo( Connection conn, StorageSupportParameter param, String key,
												String wJobNo, String wStorageUkey, String wStockId, String wBatchNo ) throws ReadWriteException
	{
		// Generate work status instance of handlers.
		WorkingInformationHandler wObj = new WorkingInformationHandler( conn );
		WorkingInformationSearchKey wKey = new WorkingInformationSearchKey();
		WorkingInformation[] wFindData = null;
		WorkingInformation wWorkinfo = new WorkingInformation();

		try
		{
			if ( key != null )
			{
				// Set the search conditions.
				// Set the search conditions.
				wKey.setPlanUkey( key );
				// Shipping Plan unique key
				wFindData = ( WorkingInformation[] )wObj.findForUpdate( wKey );
			}


			// Execute the process for adding the Work status.

			// Work No.
			wWorkinfo.setJobNo( wJobNo );
			// Work division
			wWorkinfo.setJobType( WorkingInformation.JOB_TYPE_STORAGE );
			// Aggregation Work No.
			wWorkinfo.setCollectJobNo( wJobNo );
			// Status flag
			wWorkinfo.setStatusFlag( WorkingInformation.STATUS_FLAG_UNSTART );
			// Start Work flag
			wWorkinfo.setBeginningFlag( WorkingInformation.BEGINNING_FLAG_STARTED );
			// Plan unique key
			wWorkinfo.setPlanUkey( wStorageUkey );
			// Stock ID
			wWorkinfo.setStockId( wStockId );
			// Area No.
			wWorkinfo.setAreaNo("");
			// Location No.
			wWorkinfo.setLocationNo( param.getStorageLocation() );
			// Planned date
			wWorkinfo.setPlanDate( param.getStoragePlanDate() );
			// Consignor code
			wWorkinfo.setConsignorCode( param.getConsignorCode() );
			// Consignor name
			wWorkinfo.setConsignorName( param.getConsignorName() );
			// Supplier Code
			wWorkinfo.setSupplierCode("");
			// Supplier Name
			wWorkinfo.setSupplierName1("");
			// Receiving ticket No.
			wWorkinfo.setInstockTicketNo("");
			// Receiving ticket Line No.
			wWorkinfo.setInstockLineNo(0);
			// customer code
			wWorkinfo.setCustomerCode("");
			// customer name
			wWorkinfo.setCustomerName1("");
			// Shipping Ticket No
			wWorkinfo.setShippingTicketNo("");
			// Shipping ticket line No.
			wWorkinfo.setShippingLineNo(0);
			// Item Code
			wWorkinfo.setItemCode( param.getItemCode() );
			// Item Name
			wWorkinfo.setItemName1( param.getItemName() );
			// Work Planned Qty(Host Planned Qty)
			wWorkinfo.setHostPlanQty( param.getEnteringQty() * param.getPlanCaseQty() + param.getPlanPieceQty() );
			// Work Planned Qty
			wWorkinfo.setPlanQty( param.getEnteringQty() * param.getPlanCaseQty() + param.getPlanPieceQty() );
			// Possible Work Qty
			wWorkinfo.setPlanEnableQty( param.getEnteringQty() * param.getPlanCaseQty() + param.getPlanPieceQty() );
			// Work Result qty
			wWorkinfo.setResultQty( 0 );
			// Work shortage qty
			wWorkinfo.setShortageCnt( 0 );
			// Pending Qty
			wWorkinfo.setPendingQty( 0 );
			// Packed qty per Case
			wWorkinfo.setEnteringQty( param.getEnteringQty() );
			// Packed qty per bundle
			wWorkinfo.setBundleEnteringQty( param.getBundleEnteringQty() );
			// Case/Piece division(Load Size)
			wWorkinfo.setCasePieceFlag( param.getCasePieceflg() );
			// Case/Piece division(Work type)
			wWorkinfo.setWorkFormFlag( param.getCasePieceflg() );
			// Case ITF
			wWorkinfo.setItf( param.getITF() );
			// Bundle ITF
			wWorkinfo.setBundleItf( param.getBundleITF() );
			// TC/DC Division
			wWorkinfo.setTcdcFlag("0");
			// Expiry Date
			wWorkinfo.setUseByDate("");
			// Lot No.
			wWorkinfo.setLotNo("");
			// Plan information Comment
			wWorkinfo.setPlanInformation("");
			// Order No.
			wWorkinfo.setOrderNo("");
			// Order Date
			wWorkinfo.setOrderingDate("");
			// Expiry Date(result)
			wWorkinfo.setResultUseByDate("");
			// Lot No.(result)
			wWorkinfo.setResultLotNo("");
			// Work Result Location
			wWorkinfo.setResultLocationNo("");
			// Flag to report standby work
			wWorkinfo.setReportFlag( WorkingInformation.REPORT_FLAG_NOT_SENT );
			// Batch No.
			wWorkinfo.setBatchNo( wBatchNo );

			// Disable to set any Worker code, Worker name, and Terminal No. for data extraction.
			// Worker Code
			wWorkinfo.setWorkerCode("");
			// Worker Name
			wWorkinfo.setWorkerName("");
			// Terminal No.
			wWorkinfo.setTerminalNo("");

			// Plan information Added Date/Time
			// Due to automatic setting of the added date/time by "create ", setting of the added date/time for Entity may
			// differs from the added date/time between those in the Entity and the database.
			// Set the System date.
			wWorkinfo.setPlanRegistDate( new Date() );
			// Name of Add Process
			wWorkinfo.setRegistPname( wProcessName );
			// Last update process name
			wWorkinfo.setLastUpdatePname( wProcessName );
			// Add Work Status
			wObj.create( wWorkinfo );

			// Execute the process for updating the Work status.
			if ( wFindData != null && wFindData.length > 0 )
			{
				// Update the Status flag of Work status linked with the shipping plan unique key of the parameter to Deleted.
				WorkingInformationAlterKey wAKey = new WorkingInformationAlterKey();
				for ( int i = 0; i < wFindData.length; i ++ )
				{
					// Set the update condition.
					wAKey.KeyClear();
					// Work No.
					wAKey.setJobNo( wFindData[ i ].getJobNo() );

					// Set the update value.
					// Status flag(Deleted)
					wAKey.updateStatusFlag( WorkingInformation.STATUS_FLAG_DELETE );
					// Last update process name
					wAKey.updateLastUpdatePname( wProcessName );
					// Update
					wObj.modify( wAKey );
				}
			}

			return wFindData;
		}
		catch ( DataExistsException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( InvalidDefineException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( InvalidStatusException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( NotFoundException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
		catch ( ReadWriteException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}
	}

	// Private methods -----------------------------------------------
}
//end of class
