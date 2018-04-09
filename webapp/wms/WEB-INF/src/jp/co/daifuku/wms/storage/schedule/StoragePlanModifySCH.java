package jp.co.daifuku.wms.storage.schedule;

/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanReportFinder;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : K.Matsuda <BR>
 * Maker : K.Matsuda. <BR>
 * <BR>
 * This class modifies or deletes WEB storage plan data. <BR>
 * Receive the contents entered via screen as a parameter and execute the processes for modifying or deleting the storage plan data. <BR>
 * Each method in this class receives a connection object and executes the process for updating the database. <BR>
 * However, each method disables to commit and roll back of transactions. <BR>
 * This class executes the following processes. <BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 * 	Search for the Consignor Code with status Standby through the Storage Plan info and. If only the same Consignor Code exists,<BR>
 * 	return the Consignor Code.<BR>
 * 	Return null if two or more Consignor Code exist.<BR>
 *	<BR>
 *	<Search conditions> <BR>
 *	<DIR>
 *		Status flag=0: Standby
 *	</DIR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Next button 1(<CODE>nextCheck()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *	Receive the contents entered via screen as a parameter and return the results of check for worker code, password and presence of corresponding data. <BR>
 *	Return true if corresponding data exists in the Storage Plan info and the contents of Worker code and password are correct.<BR>
 *	Set the error message "There was no target data" and return false, if no corresponding data exists in the Storage Plan info.<BR>
 *	If the maximum count of corresponding data or more exist in the Storage Plan info, set the error message "{0} data corresponded. The count exceeds {1}. Please set more search conditions", and<BR>
 *	return false.
 *	For the contents of error, enable to obtain the contents using <CODE>getMessage()</CODE> method.<BR>
 *	<BR>
 *	<Parameter> *Mandatory Input<BR>
 *	<DIR>
 *		Worker Code* <BR>
 *		Password*<BR>
 *		Consignor code*<BR>
 *		Planned storage date*<BR>
 *		Item Code*<BR>
 *		Case Storage Location<BR>
 *		Piece Storage Location<BR>
 *      Category of generating plan* <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 3.Process by clicking on Next button 2(<CODE>Query()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *	Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
 *	Return <CODE>Parameter</CODE> array with the number of elements 0 if no corresponding data found. Or, return null when condition error occurs.<BR>
 *	Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 *	Obtain only the data with status flag Standby and display in the order of Case storage location and then Piece storage location. <BR>
 *	<BR>
 *	<Parameter> *Mandatory Input<BR>
 *	<DIR>
 *		Consignor code*<BR>
 *		Planned storage date*<BR>
 *		Item Code*<BR>
 *		Case Storage Location<BR>
 *		Piece Storage Location<BR>
 *      Category of generating plan* <BR>
 *	</DIR>
 *	<BR>
 *	<Returned data> <BR>
 *	<DIR>
 *		Consignor code <BR>
 *		Consignor name <BR>
 *		Planned storage date <BR>
 *		Item Code <BR>
 *		Item Name <BR>
 *		Case Storage Location<BR>
 *		Piece Storage Location <BR>
 *		Packed qty per Case <BR>
 *		Packed qty per bundle <BR>
 *		Host planned Case qty <BR>
 *		Host Planned Piece Qty <BR>
 *		Case ITF <BR>
 *		Bundle ITF <BR>
 *		Case/Piece division(Maintain it as a data before modified)<BR>
 *		Storage Plan unique key <BR>
 *		Last update date/time <BR>
 *	</DIR>
 *	<BR>
 *	<Calculate the Host Planned Qty.><BR>
 *	<DIR>
 *		Case/Piece division is:<BR>
 *		<DIR>
 *			In the case of Case:Host planned Case qty=Planned Qty/Packed qty per Case , Host Planned Piece Qty=0<BR>
 *			In the case of Piece:Host planned Case qty=0 , Host Planned Piece Qty=Planned Qty<BR>
 *			In the case of None:Host planned Case qty=Planned Qty/Packed qty per Case , Host Planned Piece Qty=Planned Qty%Packed qty per Case<BR>
 *			For Mixed::Host planned Case qty=Planned Qty/Packed qty per Case , Host Planned Piece Qty=Planned Qty%Packed qty per Case<BR>
 *		</DIR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 4.Process by clicking on the Input button(<CODE>check()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *	Receive the contents entered via screen as a parameter and return the check result for mandatory input, overflow, or duplication. <BR>
 *	Return true if the status flag and the last update date/time of the corresponding data are not changed after entering proper value. <BR>
 *	Return false when condition error occurs. <BR>
 *  Disable to check for duplication across the existing data if the Planning category to "2: "Linked to Receiving" ". <BR>
 *	Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *	<BR>
 *	<Parameter> *Mandatory Input <BR>
 *	<DIR>
 *		Storage Plan unique key* <BR>
 *		Consignor code* <BR>
 *		Planned storage date* <BR>
 *		Item Code* <BR>
 *		Case Storage Location<BR>
 *		Piece Storage Location<BR>
 *		Packed qty per Case <BR>
 *		Packed qty per bundle <BR>
 *		Host planned Case qty <BR>
 *		Host Planned Piece Qty <BR>
 *		Last update date/time* <BR>
 *		Category of generating plan* <BR>
 *	</DIR>
 *	<BR>
 *  <Contents of check for mandatory input> <BR>
 *	<DIR>
 *		Ensure that the Packed qty per Case is 1 or larger if any value is entered in the Case qty.<BR>
 * 	</DIR>
 *	<BR>
 *	<Contents of check for overflow.><BR>
 *	<DIR>
 *		Host planned Case qty *Ensure that the result value (Packed qty per Case + Host Planned Piece Qty) is 6 or less digits number.<BR>
 *	</DIR>
 *	<BR>
 *	<Details of check for duplication><BR>
 *	<DIR>
 *		Search for the Storage Plan info using the Storage Plan unique key of the parameter. Ensure no data be found.<BR>
 *		However, Disable to check for duplication across the existing data if the Planning category to "2: "Linked to Receiving" ". <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 5.Process by clicking on Delete button or Delete All button(<CODE>startSCH()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *	Receive the contents displayed in the preset area as a parameter and execute the process to delete the Storage Plan data.<BR>
 *	Return true when the process normally completed, or return false when failed to schedule completely due to condition error or other causes.<BR>
 *	Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 *	<BR>
 *  <Parameter> *Mandatory Input<BR>
 *	<DIR>
 *		Storage Plan unique key*<BR>
 *		Consignor code*<BR>
 *		Planned storage date*<BR>
 *		Item Code*<BR>
 *		Case Storage Location*<BR>
 *		Piece Storage Location*<BR>
 *		Last update date/time* <BR>
 *	</DIR>
 *	<BR>
 *	<Process> <BR>
 *	<DIR>
 *		1.Search through the Work status using Storage Plan unique key (Lock the DB) and check for presence of the data.<BR>
 *		2.Search through the Storage Plan information using Storage Plan unique key and check for presence of the data.<BR>
 *		3.Check whether last update date/time value of the obtained Storage Plan info is consistent with the last update date/time for the parameter.<BR>
 *		Set the error message "No.{0} Unable to process this data. It has been updated via other work station.", <BR>
 *		and return null.<BR>
 *		4. Update process. Return true, when normally processed.<BR>
 *		  Set an error message for each error and return false, when error occurs during processing.<BR>
 *	</DIR>
 *	<BR>
 *	<Update Process> <BR>
 *	<DIR>
 *		-Update the table in the sequence of Work status, Storage Plan info, and then inventory information to prevent from deadlocking. <BR>
 *		-Update Work Status Table (DNWORKINFO) <BR>
 *		<DIR>
 *			Update the status flag Work Status linked with Storage Plan unique key of the updated Storage Plan info to Delete.<BR>
 *		</DIR>
 *		<BR>
 *		-Update of Storage Plan information table (DNSTORAGEPLAN) <BR>
 * 		<DIR>
 *			Update the status flag to Delete.<BR>
 *		</DIR>
 *		<BR>
 *		-Update Inventory Information Table (DMSTOCK). <BR>
 *		<DIR>
 *			Update the Status flag of inventory information linked with Stock ID of the updated Work status to Completed.<BR>
 *		</DIR>
 *	</DIR>
 *	<BR>
 * </DIR>
 * <BR>
 * 6.Process by clicking on Modify/Add button(<CODE>startSCHgetParams()</CODE>Method) <BR>
 * <BR>
 * <DIR>
 *	Receive the contents displayed in the preset area as a parameter and execute the process to modify the Storage Plan data. <BR>
 *  For data with Add division "2: "Linked to Receiving" ", disable to check for duplication across other existing data with the modified/added data. <BR>
 *  For the data with Add division "2: "Linked to Receiving"", generate a data in the Add division "2: "Linked to Receiving"" even if the data modified via screen and re-generated. <BR>
 *	When process normally completed, obtain the data to be output in the preset area from database again using a condition entered in the initial display, and return it. <BR>
 *	If no corresponding data found in the repeated search to obtain it, return <CODE>Parameter</CODE> array with the number of elements equal to 0.<BR>
 *	Return null when the schedule failed to complete due to condition error or other causes while processing to modify data or obtain data again. <BR>
 *	Enable to refer to the content of the error using <CODE>getMessage()</CODE> method. <BR>
 *	<BR>
 *	<Parameter> *Mandatory Input<BR>
 *	<DIR>
 *		Storage Plan unique key* <BR>
 *		Consignor code* <BR>
 *		Consignor name <BR>
 *		Planned storage date* <BR>
 *		Item Code* <BR>
 *		Item Name <BR>
 *		Case Storage Location<BR>
 *		Piece Storage Location<BR>
 *		Packed qty per Case <BR>
 *		Packed qty per bundle <BR>
 *		Host planned Case qty <BR>
 *		Host Planned Piece Qty <BR>
 *		Case ITF <BR>
 *		Bundle ITF <BR>
 *		Last update date/time* <BR>
 *		Case Storage Location in the initial display.<BR>
 *		Piece Storage Location of the 1st screen<BR>
 *		Case/Piece division(data before modified)<BR>
 *		Category of generating plan* <BR>
 *	</DIR>
 *	<BR>
 *	<Returned data> <BR>
 *	<DIR>
 *		Storage Plan unique key<BR>
 *		Consignor code<BR>
 *		Consignor name<BR>
 *		Planned storage date<BR>
 *		Item Code<BR>
 *		Item Name<BR>
 *		Case Storage Location<BR>
 *		Piece Storage Location<BR>
 *		Packed qty per Case <BR>
 *		Packed qty per bundle <BR>
 *		Host planned Case qty <BR>
 *		Host Planned Piece Qty <BR>
 *		Case ITF <BR>
 *		Bundle ITF <BR>
 *		Last update date/time<BR>
 *		Case/Piece division<BR>
 *	</DIR>
 *	<BR>
 *	<Process><BR>
 *	<DIR>
 *		1.Check Daily Update Processing<BR>
 *		2.Check Exclusion.<BR>
 *		3.Process to delete source data<BR>
 *		4.Process for adding the modified data<BR>
 *		5.Execute the process to obtain the data to be displayed again.<BR>
 *	</DIR>
 *	<BR>
 *	<Check Daily Update Processing><BR>
 *	<DIR>
 *		1.Search for the System definition info and check the Processing Daily Update flag.<BR>
 *		2.Continue the process if Daily Update Processing flag is "0: Stopping".<BR>
 *		When "Daily Update Processing" flag is "1: Start up ", set the error message "6023321 = Daily update is in process. Cannot process. ",<BR>
 *		and return null.<BR>
 *	</DIR>
 *	<Check Exclusion.><BR>
 *	<DIR>
 *		1.Search through the Work status using Storage Plan unique key (Lock the DB) and check for presence of the data.<BR>
 *		2.Search through the Storage Plan information using Storage Plan unique key and check for presence of the data.<BR>
 *		3.Check whether last update date/time value of the obtained Storage Plan info is consistent with the last update date/time for the parameter.<BR>
 *		Set the error message "No.{0} Unable to process this data. It has been updated via other work station.", when error occurs due to either one of checks, <BR>
 *		and return null.<BR>
 *	</DIR>
 * <BR>
 *   <Process to delete source data> <BR>
 *     -Update the table in the sequence of Work status, Storage Plan info, and then inventory information to prevent from deadlocking. <BR>
 *		<DIR>
 *			-Update Work Status Table (DNWORKINFO) <BR>
 *			<DIR>
 *				Update the status flag Work Status linked with Storage Plan unique key of the updated Storage Plan info to Delete.<BR>
 *			</DIR>
 *			<BR>
 *			-Update of Storage Plan information table (DNSTORAGEPLAN) <BR>
 * 			<DIR>
 *				Update the status flag to Delete.<BR>
 *			</DIR>
 *			<BR>
 *			-Update Inventory Information Table (DMSTOCK). <BR>
 *			<DIR>
 *				Update the Status flag of inventory information linked with Stock ID of the updated Work status to Completed. <BR>
 *			</DIR>
 *		</DIR>
 *	<BR>
 *	<Process for adding the modified data> <BR>
 *	<DIR>
 *		-Update Work Status Table (DNWORKINFO)adding <BR>
 *		<DIR>
 *			Add the Work Status based on the contents added at this time to the Storage Plan info. <BR>
 *		</DIR>
 *		<BR>
 *		-Update of Storage Plan information table (DNSTORAGEPLAN)adding <BR>
 *		<DIR>
 *			Add the Storage Plan info based on the contents of the Received parameter, <BR>
 *		</DIR>
 *		<BR>
 *		-Update Inventory Information Table (DMSTOCK). <BR>
 *		<DIR>
 *			Add the inventory information based on the contents added this time to the Storage Plan info. <BR>
 *		</DIR>
 *	</DIR>
 *	<BR>
 *	<Execute the process to obtain the data to be displayed again><BR>
 *	<DIR>
 *		-Search for the Storage Plan info using Consignor Code + Planned storage date + Item Code + Case Storage Location in the initial display + Piece Storage Location in the initial display as keys.<BR>
 *		-If the search result count is 1 or more, return the result. If the search result count is 0, return the array with element qty 0 (zero).
 *	</DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/06</TD><TD>K.Matsuda</TD><TD>Create New</TD></TR>
 * <TR><TD>2005/05/17</TD><TD>T.Nakajima</TD><TD>This was solved by adding the Add division "2: Linked to Receiving".</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:23:10 $
 * @author  $Author: suresh $
 */
public class StoragePlanModifySCH extends AbstractStorageSCH
{

	// Class variables -----------------------------------------------
	/**
	 * Process name
	 */
	private static String wProcessName = "StoragePlanModifySCH";

	/**
	 * Storage Plan info handler
	 */
	protected StoragePlanHandler wPlanHandler = null;

	/**
	 * Storage Plan search key
	 */
	protected StoragePlanSearchKey wPlanKey = null;

	/**
	 * Work Status handler
	 */
	protected WorkingInformationHandler wWorkHandler = null;

	/**
	 * Work Status search key
	 */
	protected WorkingInformationSearchKey wWorkKey = null;

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ( "$Revision: 1.3 $,$Date: 2006/11/21 04:23:10 $" );
	}
	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StoragePlanModifySCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code found in the Storage Plan info, return the corresponding Consignor Code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{
		// Set the corresponding Consignor code.
		StorageSupportParameter storageParameter = new StorageSupportParameter();

		// Generate an instance of Storage Plan information handlers
		StoragePlanSearchKey storagePlanSearchKey = new StoragePlanSearchKey();
		StoragePlanHandler storagePlanHandler = new StoragePlanHandler( conn );
		StoragePlan storagePlan = null;

		try
		{
			// Set Search Condition
			// Status flag= "Standby "
			storagePlanSearchKey.setStatusFlag( StoragePlan.STATUS_FLAG_UNSTART );
			// Set the Consignor Code for grouping condition.
			storagePlanSearchKey.setConsignorCodeGroup( 1 );
			storagePlanSearchKey.setConsignorCodeCollect( "" );

			// If one result obtained, search it and set it for storageParameter.
			if(storagePlanHandler.count(storagePlanSearchKey) == 1)
			{
				try
				{
					storagePlan = (StoragePlan)storagePlanHandler.findPrimary(storagePlanSearchKey);
				}
				catch(NoPrimaryException e)
				{
					return new StorageSupportParameter();
				}
				storageParameter.setConsignorCode(storagePlan.getConsignorCode());
			}

		}
		catch ( ReadWriteException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}

		return storageParameter;
	}

	/**
	 * Receive the contents entered via screen as a parameter and obtain data for output in the preset area from the database and return it.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Instance to maintain database connection.
	 * @param searchParam Instance of <CODE>StorageSupportParameter</CODE> class with conditions to obtain the display data.<BR>
	 *         Designating any instance other than <CODE>StorageSupportParameter</CODE> throws ScheduleException.<BR>
	 * @return Array of <CODE>StorageSupportParameter</CODE> instance with search result.<BR>
	 *          If no corresponding record found, return the array of the number of elements equal to 0.<BR>
	 *          Return null if the search result count exceeds 1000 or when input condition error occurs.<BR>
	 *          Returning array with element qty 0 (zero) or null allows to obtain the error contents as a message using <CODE>getMessage()</CODE> method.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] query( Connection conn, Parameter searchParam ) throws ReadWriteException, ScheduleException
	{

		// search result
		StorageSupportParameter[] resultParameter = null;
		// Consignor name
		String consignorName = "";
		// Item Name
		String itemName = "";

		// Generate Planned Storage Information instance of handlers.
		StoragePlanHandler storagePlanHandler = new StoragePlanHandler( conn );
		StoragePlanSearchKey storagePlanSearchKey = new StoragePlanSearchKey();
		StoragePlan[] storage = null;

		// Generate Instance to obtain Consignor Name.
		StoragePlanReportFinder storagePlanFinder = new StoragePlanReportFinder(conn);
		StoragePlanSearchKey consignorSearchKey = new StoragePlanSearchKey();
		StoragePlan[] consignor = null;

		try
		{
			StorageSupportParameter parameter = (StorageSupportParameter)searchParam;

			// Set the search conditions.
			storagePlanSearchKey.KeyClear();
			// Consignor code
			storagePlanSearchKey.setConsignorCode(parameter.getConsignorCode());
			consignorSearchKey.setConsignorCode(parameter.getConsignorCode());
			// Planned storage date
			storagePlanSearchKey.setPlanDate(parameter.getStoragePlanDate());
			consignorSearchKey.setPlanDate(parameter.getStoragePlanDate());
			// Item Code
			storagePlanSearchKey.setItemCode(parameter.getItemCode());
			consignorSearchKey.setItemCode(parameter.getItemCode());

			// Case Storage Location
			if(!StringUtil.isBlank(parameter.getCaseLocationCondition()))
			{
				storagePlanSearchKey.setCaseLocation(parameter.getCaseLocationCondition());
				consignorSearchKey.setCaseLocation(parameter.getCaseLocationCondition());
			}
			// Piece Storage Location
			if(!StringUtil.isBlank(parameter.getPieceLocationCondition()))
			{
				storagePlanSearchKey.setPieceLocation( parameter.getPieceLocationCondition() );
				consignorSearchKey.setPieceLocation(parameter.getPieceLocationCondition());
			}

			// Status flag (Standby)
			storagePlanSearchKey.setStatusFlag( StoragePlan.STATUS_FLAG_UNSTART );

		    // Category of generating plan
		 	if (StorageSupportParameter.REGIST_KIND_NOT_INSTOCK.equals(parameter.getRegistKbn()))
		 	{
				// Extract/Add via Screen
				String status[] = {SystemDefine.REGIST_KIND_HOST, SystemDefine.REGIST_KIND_WMS};
				storagePlanSearchKey.setRegistKind(status);
				consignorSearchKey.setRegistKind(status);
		 	}
		 	else if (SystemDefine.REGIST_KIND_INSTOCK.equals(parameter.getRegistKbn()))
		 	{
				// Link with Receiving
				storagePlanSearchKey.setRegistKind(SystemDefine.REGIST_KIND_INSTOCK);
				consignorSearchKey.setRegistKind(SystemDefine.REGIST_KIND_INSTOCK);
		 	}

			// Set the sorting order.
			// Case Storage Location
			storagePlanSearchKey.setCaseLocationOrder( 1, true );
			// Piece Storage Location
			storagePlanSearchKey.setPieceLocationOrder( 2, true );

			// Obtain the Search result of Storage Plan information.
			storage = ( StoragePlan[] )storagePlanHandler.find( storagePlanSearchKey );

			// Obtain the Consignor name with the latest added date.
			consignorSearchKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
			consignorSearchKey.setRegistDateOrder( 1, false );
			storagePlanFinder.open();

			if (storagePlanFinder.search( consignorSearchKey ) > 0)
			{
				consignor = (StoragePlan[])storagePlanFinder.getEntities(1);
				if(consignor != null && consignor.length > 0)
				{
					consignorName = consignor[0].getConsignorName();
					itemName = consignor[0].getItemName1();
				}
			}
			storagePlanFinder.close();

			// Generate Vector instance.
			Vector vec = new Vector();

			for ( int i = 0; i < storage.length; i ++ )
			{
				StorageSupportParameter temp = new StorageSupportParameter();

				// Consignor code
				temp.setConsignorCode(storage[i].getConsignorCode());
				// Consignor name
				temp.setConsignorName(consignorName);
				// Planned storage date
				temp.setStoragePlanDate(storage[i].getPlanDate());
				// Item Code
				temp.setItemCode(storage[i].getItemCode());
				// Item Name
				temp.setItemName(itemName);
				// Case Storage Location
				temp.setCaseLocation(storage[i].getCaseLocation());
				// Piece Storage Location
				temp.setPieceLocation(storage[i].getPieceLocation());
				// Packed qty per Case
				temp.setEnteringQty(storage[i].getEnteringQty());
				// Packed qty per bundle
				temp.setBundleEnteringQty(storage[i].getBundleEnteringQty());
				// Case/Piece division
				temp.setCasePieceflg(storage[i].getCasePieceFlag());
				// Host planned Case qty
				temp.setPlanCaseQty(DisplayUtil.getCaseQty(storage[i].getPlanQty(), storage[i].getEnteringQty(),
						storage[i].getCasePieceFlag()));
				// Host Planned Piece Qty
				temp.setPlanPieceQty(DisplayUtil.getPieceQty(storage[i].getPlanQty(), storage[i].getEnteringQty(),
						storage[i].getCasePieceFlag()));
				// Case ITF
				temp.setITF(storage[i].getItf());
				// Bundle ITF
				temp.setBundleITF(storage[i].getBundleItf());
				// Storage Plan unique key
				temp.setStoragePlanUKey(storage[i].getStoragePlanUkey());
				// Last update date/time
				temp.setLastUpdateDate(storage[i].getLastUpdateDate());
				// Category of generating plan
				if (SystemDefine.REGIST_KIND_HOST.equals(storage[i].getRegistKind()) ||
					SystemDefine.REGIST_KIND_WMS.equals(storage[i].getRegistKind()))
				{
					//Extract/Add via Screen
					temp.setRegistKbn(StorageSupportParameter.REGIST_KIND_NOT_INSTOCK);
				}
				else if (SystemDefine.REGIST_KIND_INSTOCK.equals(storage[i].getRegistKind()))
				{
					// Link with Receiving
					temp.setRegistKbn(SystemDefine.REGIST_KIND_INSTOCK);
				}

				vec.addElement(temp);
			}
			resultParameter = new StorageSupportParameter[vec.size()];
			vec.copyInto( resultParameter );

			if(resultParameter == null)
			{
				return new StorageSupportParameter[0];
			}

			// 6001013 = Data is shown.
			wMessage = "6001013";
		}
		catch ( ReadWriteException e )
		{
			throw new ReadWriteException( e.getMessage() );
		}

		return resultParameter;
	}

	/**
	 * Receive the contents entered via screen as a parameter and return the exclusion check result. <BR>
	 * If no corresponding data exists when searching through the Work status using the Storage Plan unique key of parameter as a key, or<BR>
	 * If no corresponding data exists when searching through the Storage Plan using the Storage Plan unique key of parameter as a key,  <BR>
	 * regard any data with latest update date/time updated as exclusion error and return false. <BR>
	 * Return true if corresponding data exist and no changed in the last update date/time. <BR>
	 * @param conn Instance to maintain database connection.
	 * @param checkParam Instance of <CODE>StorageSupportParameter</CODE> class with input contents. <BR>
	 *        Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @param inputParams Array of <CODE>StorageSupportParameter</CODE> class instance with the contents in the preset area. <BR>
	 *        Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean check( Connection conn, Parameter checkParam ) throws ScheduleException, ReadWriteException
	{

		// Generate instance of handlers for storage Plan information
		StoragePlanSearchKey storagePlanSearchKey = new StoragePlanSearchKey();
		StoragePlanHandler storagePlanHandler = new StoragePlanHandler( conn );
		StoragePlan storage = null;

		// Generate Instance of handlers for Work status.
		WorkingInformationHandler workingInformationHandler = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey workingInformationSearchKey = new WorkingInformationSearchKey();
		WorkingInformation[] workingInformation = null;

		try
		{
			// Search conditions for parameters
			StorageSupportParameter parameter = (StorageSupportParameter)checkParam;

			// Set the search conditions.
			// Storage Plan unique key
			workingInformationSearchKey.setPlanUkey(parameter.getStoragePlanUKey());
			storagePlanSearchKey.setStoragePlanUkey(parameter.getStoragePlanUKey());

			// Search for the Work status and lock the DB.
			workingInformation = (WorkingInformation[])workingInformationHandler.findForUpdate(workingInformationSearchKey);

			if(workingInformation == null || workingInformation.length == 0)
			{
				return false;
			}

			// Search through the Storage Plan information.
			storage = ( StoragePlan )storagePlanHandler.findPrimaryForUpdate( storagePlanSearchKey );

			if ( storage != null )
			{
				if ( !storage.getLastUpdateDate().equals( parameter.getLastUpdateDate() ) )
				{
					return false;
				}
			}
			else
			{
				return false;
			}
		}
		catch ( NoPrimaryException e )
		{
			// 6026020 = Multiple records are found in a search by the unique key. {0}{0}
			RmiMsgLogClient.write("6026020" + wDelim + "DNSTORAGEPLAN", wProcessName);
			// 6006040 = Data mismatch occurred. See log.{0}
			wMessage = "6006040" + wProcessName;
			throw new ScheduleException( e.getMessage() );
		}

		return true;

	}

	/**
	 * Receive the contents entered via screen and contents of Preset area as a parameter and <BR>
	 * check for mandatory, overflow and duplication and return the checking results.<BR>
	 * Return true if no same data is found in the Storage Plan info,<BR>
	 * Regard such a case as an error where condition error occurs or the same data exists, and return false. <BR>
	 * Disable to check for duplication of data with Planning category "2: "Linked to Receiving"". <BR>
	 * @param conn Instance to maintain database connection.
	 * @param checkParam Instance of <CODE>StorageSupportParameter</CODE> class with input contents. <BR>
	 *        Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @param inputParams Array of <CODE>StorageSupportParameter</CODE> class instance with the contents in the preset area. <BR>
	 *        Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean check( Connection conn, Parameter checkParam, Parameter[] inputParams )
		throws ScheduleException, ReadWriteException
	{
		// Contents of the input area
		StorageSupportParameter parameter = (StorageSupportParameter)checkParam;
		// Contents of Preset area
		StorageSupportParameter[] listParameter = (StorageSupportParameter[])inputParams;


		// Check for mandatory input by condition.
		// Check for mandatory input in the Packed qty per Case when entering the Host planned case qty.
		if ( parameter.getPlanCaseQty() > 0 && parameter.getEnteringQty() == 0 )
		{
			// 6023019 = Please enter 1 or greater in the packed quantity per case.
			wMessage = "6023019";
			return false;
		}

		// Check that 1 or more value input in the Host planned case qty or Host planned piece qty.
		if ( parameter.getPlanCaseQty() == 0 && parameter.getPlanPieceQty() == 0 )
		{
			// 6023383 = Enter 1 or greater value for Host Planned Case Qty. or Host Planned Piece Qty.
			wMessage = "6023383";
			return false;
		}

		// Check Overflow.
		if ( ( long )parameter.getEnteringQty() * ( long )parameter.getPlanCaseQty() + ( long )parameter.getPlanPieceQty() > WmsParam.MAX_TOTAL_QTY )
		{
			// 6023058 = Please enter {1} or smaller for {0}.
			wMessage = "6023058" + wDelim + DisplayText.getText( "LBL-W0379" ) + wDelim + MAX_TOTAL_QTY_DISP;
			return false;
		}

		// Disable to check for duplication across the DB for data with Planning category "2: "Linked to Receiving" ".
		if (!SystemDefine.REGIST_KIND_INSTOCK.equals(parameter.getRegistKbn()))
		{
			// Check for duplication across the preset area.
			if ( listParameter != null )
			{

				String inputCasePiece = "";

				// If the input data is case
				inputCasePiece = getCasePieceFlag(parameter);
				
				// If the input data is case
				if (inputCasePiece.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
				{
					// check the preset area data for case, mixed
					for ( int i = 0; i < listParameter.length; i ++ )
					{
						if (listParameter[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE)
						 || listParameter[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_MIXED))
						{
							if (listParameter[i].getCaseLocation().equals(parameter.getCaseLocation()))
							{
								// 6023090 = The data already exists.
								wMessage = "6023090";
								return false;
							}
						}
					}
				}
				// If the input data is piece
				else if (inputCasePiece.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
				{
					// check the preset area data for piece, mixed
					for ( int i = 0; i < listParameter.length; i ++ )
					{
						if (listParameter[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE)
						 || listParameter[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_MIXED))
						{
							if (listParameter[i].getPieceLocation().equals(parameter.getPieceLocation()))
							{
								// 6023090 = The data already exists.
								wMessage = "6023090";
								return false;
							}
						}
					}
				}
				// If the input data is mixed
				else if (inputCasePiece.equals(StorageSupportParameter.CASEPIECE_FLAG_MIXED))
				{
					// check preset area data for case, mixed.
					// compare case location with case, mixed.
					// compare piece location with piece, mixed.
					for ( int i = 0; i < listParameter.length; i ++ )
					{
						if (listParameter[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_CASE)
						 || listParameter[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_MIXED))
						{
							if (listParameter[i].getCaseLocation().equals(parameter.getCaseLocation()))
							{
								// 6023090 = The data already exists.
								wMessage = "6023090";
								return false;
							}
						}
						if (listParameter[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE)
						 || listParameter[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_MIXED))
						{
							if (listParameter[i].getPieceLocation().equals(parameter.getPieceLocation()))
							{
								// 6023090 = The data already exists.
								wMessage = "6023090";
								return false;
							}
						}
					}
				}
				// If the input data is "none"
				else if (inputCasePiece.equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
				{
					// acquire input location from input area
					String inputLoc = null;
					if (!StringUtil.isBlank(parameter.getCaseLocation()))
						inputLoc = parameter.getCaseLocation();
					else
						inputLoc = parameter.getPieceLocation();

					// compare preset area location that is input with "none"
					for ( int i = 0; i < listParameter.length; i ++ )
					{
						String lstLoc = null;
						
						if (!StringUtil.isBlank(listParameter[i].getCaseLocation()))
						{
							lstLoc = listParameter[i].getCaseLocation();
						}
						else
						{
							lstLoc = listParameter[i].getPieceLocation();
						}
						
						if (listParameter[i].getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
						{
							if (inputLoc.equals(lstLoc)
							 && listParameter[i].getCasePieceflg().equals(inputCasePiece))
							{
								// 6023090 = The data already exists.
								wMessage = "6023090";
								return false;
							}
						}
					}
				}
			}

			// Check for duplication across Work status (storage)
			// Determine Work Status Division
			String casePieceFlag = "";
			// Location with the division "None"
			String locationNothing = "";

			// Case/Piece divison
			casePieceFlag = getCasePieceFlag(parameter);

			parameter.setCasePieceflg(casePieceFlag);

			// Determine the location for None.
			if(casePieceFlag.equals(StoragePlan.CASEPIECE_FLAG_NOTHING))
			{
				// For Case Storage Location
				if(!StringUtil.isBlank(parameter.getCaseLocation()) && StringUtil.isBlank(parameter.getPieceLocation()))
				{
					locationNothing = parameter.getCaseLocation();
				}
				// In the case of Piece Storage Location
				if(StringUtil.isBlank(parameter.getCaseLocation()) && !StringUtil.isBlank(parameter.getPieceLocation()))
				{
					locationNothing = parameter.getPieceLocation();
				}
			}


			// Check for duplication by division.
			WorkingInformationHandler workingInformationHandler = new WorkingInformationHandler(conn);
			WorkingInformationSearchKey workingInformationSearchKey = new WorkingInformationSearchKey();

			// For Mixed:
			if(casePieceFlag.equals(StoragePlan.CASEPIECE_FLAG_MIX))
			{
				// Set the search conditions.(Case work)
				workingInformationSearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
				workingInformationSearchKey.setConsignorCode(parameter.getConsignorCode());
				workingInformationSearchKey.setPlanDate(parameter.getStoragePlanDate());
				workingInformationSearchKey.setItemCode(parameter.getItemCode());
				workingInformationSearchKey.setLocationNo(parameter.getCaseLocation());
				workingInformationSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				workingInformationSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
				workingInformationSearchKey.setPlanUkey(parameter.getStoragePlanUKey(), "!=");

				if(workingInformationHandler.count(workingInformationSearchKey) > 0)
				{
					// Only using the Work status, it is impossible to determine whether the data is "Linked to Receiving" or not. Therefore,
					if(checkStoragePlan(conn, parameter, casePieceFlag) > 0)
					{
						// 6023090 = The data already exists.
						wMessage = "6023090";
						return false;
					}
				}

				// Key Clear
				workingInformationSearchKey.KeyClear();

				// Set the search conditions.(Piece Work)
				workingInformationSearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
				workingInformationSearchKey.setConsignorCode(parameter.getConsignorCode());
				workingInformationSearchKey.setPlanDate(parameter.getStoragePlanDate());
				workingInformationSearchKey.setItemCode(parameter.getItemCode());
				workingInformationSearchKey.setLocationNo(parameter.getPieceLocation());
				workingInformationSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				workingInformationSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
				workingInformationSearchKey.setPlanUkey(parameter.getStoragePlanUKey(), "!=");

				// Search(Piece Work)
				if(workingInformationHandler.count(workingInformationSearchKey) > 0)
				{
					// Only using the Work status, it is impossible to determine whether the data is "Linked to Receiving" or not. Therefore,
					if(checkStoragePlan(conn, parameter, casePieceFlag) > 0)
					{
						// 6023090 = The data already exists.
						wMessage = "6023090";
						return false;
					}
				}
			}
			// In other case,
			else
			{
				// Set the search conditions.
				workingInformationSearchKey.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
				workingInformationSearchKey.setConsignorCode(parameter.getConsignorCode());
				workingInformationSearchKey.setPlanDate(parameter.getStoragePlanDate());
				workingInformationSearchKey.setItemCode(parameter.getItemCode());
				workingInformationSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_DELETE, "!=");
				workingInformationSearchKey.setPlanUkey(parameter.getStoragePlanUKey(), "!=");

				// Set the search conditions for each division.
				// None
				if(casePieceFlag.equals(StoragePlan.CASEPIECE_FLAG_NOTHING))
				{
					workingInformationSearchKey.setLocationNo(locationNothing);
					workingInformationSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_NOTHING);
				}
				// Case
				else if(casePieceFlag.equals(StoragePlan.CASEPIECE_FLAG_CASE))
				{
					workingInformationSearchKey.setLocationNo(parameter.getCaseLocation());
					workingInformationSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_CASE);
				}
				// Piece
				else if(casePieceFlag.equals(StoragePlan.CASEPIECE_FLAG_PIECE))
				{
					workingInformationSearchKey.setLocationNo(parameter.getPieceLocation());
					workingInformationSearchKey.setWorkFormFlag(WorkingInformation.CASEPIECE_FLAG_PIECE);
				}

				// Search
				if(workingInformationHandler.count(workingInformationSearchKey) > 0)
				{
					// Only using the Work status, it is impossible to determine whether the data is "Linked to Receiving" or not. Therefore,
					if(checkStoragePlan(conn, parameter, casePieceFlag) > 0)
					{
						// 6023090 = The data already exists.
						wMessage = "6023090";
						return false;
					}

				}
			}
		}

		// 6001019 = Entry was accepted.
		wMessage = "6001019";

		return true;
	}

	/**
	 * Receive the contents entered via screen as a parameter and return the result of check for Worker code, password, and presence of corresponding data. <BR>
	 * Return true if corresponding data exist and the contents of Worker code and password are correct.<BR>
	 * Return false when no corresponding data exists in the Storage Plan info, or return false when the parameter contents have some problem.
	 * For the contents of error, enable to obtain the contents using <CODE>getMessage()</CODE> method.<BR>
	 * @param conn Database connection object
	 * @param checkParam Instance of <CODE>StorageSupportParameter</CODE> class with input contents. <BR>
	 *        Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *        Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return Return true when the schedule process normally completed, or return false when it failed or not allowed.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean nextCheck( Connection conn, Parameter checkParam ) throws ReadWriteException, ScheduleException
	{

		// Generate Plan info instance of handlers.
		StoragePlanHandler storagePlanHandler = new StoragePlanHandler( conn );
		StoragePlanSearchKey storagePlanSearchKey = new StoragePlanSearchKey();

		// Check Daily Update Processing
		if(isDailyUpdate(conn))
		{
			return false;
		}

		StorageSupportParameter parameter = ( StorageSupportParameter ) checkParam;

		// Check the Worker code and password
		if(checkWorker(conn, parameter))
		{
			// Set the search conditions.
			// Consignor code
			storagePlanSearchKey.setConsignorCode( parameter.getConsignorCode() );
			// Planned storage date
			storagePlanSearchKey.setPlanDate( parameter.getStoragePlanDate() );
			// Item Code
			storagePlanSearchKey.setItemCode( parameter.getItemCode() );
			// Case Storage Location
			if(!StringUtil.isBlank(parameter.getCaseLocation()))
			{
				storagePlanSearchKey.setCaseLocation( parameter.getCaseLocation() );
			}
			// Piece Storage Location
			if(!StringUtil.isBlank(parameter.getPieceLocation()))
			{
				storagePlanSearchKey.setPieceLocation( parameter.getPieceLocation() );
			}
			// Status flag (Standby)
			storagePlanSearchKey.setStatusFlag( StoragePlan.STATUS_FLAG_UNSTART );
			// Category of generating plan
			if (StorageSupportParameter.REGIST_KIND_NOT_INSTOCK.equals(parameter.getRegistKbn()))
			{
				//Extract/Add via Screen
				String status[] = {SystemDefine.REGIST_KIND_HOST, SystemDefine.REGIST_KIND_WMS};
				storagePlanSearchKey.setRegistKind(status);
			}
			else if (SystemDefine.REGIST_KIND_INSTOCK.equals(parameter.getRegistKbn()))
			{
				// Link with Receiving
				storagePlanSearchKey.setRegistKind(SystemDefine.REGIST_KIND_INSTOCK);
			}

			// Obtain the Search result count of Storage Plan information.
			if (!canLowerDisplay(storagePlanHandler.count(storagePlanSearchKey)))
			{
				if (returnNoDisplayParameter() == null || returnNoDisplayParameter().length <= 0)
				{
					return false;
				}
			}
		}
		else
		{
			return false;
		}

		return true;
	}

	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for deleting the Storage Plan data.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * Return true if the schedule normally completed, or return false if failed.
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StorageSupportParameter</CODE> class instance with contents of commitment. <BR>
	 *         Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return Check result (normal : true abnormal : false)
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean startSCH( Connection conn, Parameter[] startParams )
		throws ReadWriteException, ScheduleException
	{
		// Flag to decide db commit and rollback
		boolean registFlag = false;

		// This flag determines whether "Processing Extraction" flag is updated in its own class.
		boolean updateLoadDataFlag = false;

		try
		{
			// Input information of the parameter
			StorageSupportParameter[] inputParam = (StorageSupportParameter[]) startParams;

			// Check Daily Update Processing
			if(isDailyUpdate(conn))
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

			// Check for exclusion of all the target data.
			if (!lockPlanUkeyAll(conn, startParams))
			{
				// 6003006 = Unable to process this data. It has been updated via other work station.
				wMessage = "6003006";
				return false;
			}

			// Generate handlers.
			getStoragePlanHandler(conn);
			getWorkingInformationHandler(conn);
			StoragePlanOperator planOperator = new StoragePlanOperator(conn);

			for ( int i = 0; i < inputParam.length; i ++ )
			{

				// Check for exclusion.
				if ( !check( conn, inputParam[i] ) )
				{
					// Close the process when error via other terminal occurs.
					// 6023209 = No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + inputParam[i].getRowNo();
					return false;
				}

				// Execute process for deleting (Work status, Plan info, and inventory information )
				planOperator.updateStoragePlan(inputParam[i].getStoragePlanUKey(), wProcessName);
			}

			// 6001005 = Deleted.
			wMessage = "6001005";
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
			if(updateLoadDataFlag)
			{
				// Update the Pricessubg Extraction flag: Stopping
				updateLoadDataFlag(conn, false);
				doCommit(conn, wProcessName);
			}
		}
	}

	/**
	 * Receive the contents entered via screen as a parameter and start the schedule for modifying the Storage Plan data.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * When process normally completed, obtain the data to be output in the preset area from database again using a condition entered in the initial display, and return it. <BR>
	 * Return null when the schedule failed to complete due to condition error or other causes.
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StorageParameter</CODE> classinstance with contents of commitment. <BR>
	 *         Designating any instance other than StorageSupportParameter instance throws <CODE>ScheduleException</CODE>.<BR>
	 *         Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.
	 * @return <CODE>StorageSupportParameter</CODE> instance array that holds the search result<BR>
	 *          Return 0 if search returns no records<BR>
	 *          If the search result either exceeds the maximum display limit or abnormal, return null<BR>
	 *          If the response is 0 or null, <CODE>getMessage</CODE> method is used to fetch the details
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter[] startSCHgetParams( Connection conn, Parameter[] startParams )
		throws ReadWriteException, ScheduleException
	{

		// Flag to determine the displayed message
		boolean messageFlag = false;

		// Deciding DB commit and rollback
		boolean registFlag = false;

		// This flag determines whether "Processing Extraction" flag is updated in its own class.
		boolean updateLoadDataFlag = false;

		try
		{
			// Input information of the parameter
			StorageSupportParameter[] param = (StorageSupportParameter[]) startParams;

			// Check Daily Update Processing
			if(isDailyUpdate(conn))
			{
				return null;
			}
			// Extraction flag check process null: Processing Extract
			if (isLoadingData(conn))
			{
				return null;
			}
			// Update the extraction flag: "Processing Extract"
			if (!updateLoadDataFlag(conn, true))
			{
				return null;
			}
			doCommit(conn, wProcessName);
			updateLoadDataFlag = true;

			// Check for exclusion of all the target data.
			if (!lockPlanUkeyAll(conn, startParams))
			{
				// 6003006 = Unable to process this data. It has been updated via other work station.
				wMessage = "6003006";
				return null;
			}

			// Obtain the batch No. and Worker name.
			SequenceHandler sequenceHandler = new SequenceHandler(conn);
			String batch_seqno = sequenceHandler.nextStoragePlanBatchNo();
			String workerName = getWorkerName(conn, param[0].getWorkerCode());

			// Generate handlers.
			getStoragePlanHandler(conn);
			getWorkingInformationHandler(conn);
			StoragePlanOperator planOperator = new StoragePlanOperator(conn);

			for ( int i = 0; i < param.length; i ++ )
			{
				// Check for exclusion.
				if (!lock(param[i]))
				{
					// Close the process when error via other terminal occurs.
					// 6023209 = No.{0} The data has been updated via other terminal.
					wMessage = "6023209" + wDelim + param[i].getRowNo();
					return null;
				}
				// Determine Case/Piece Division
				param[i].setTotalPlanQty(param[i].getEnteringQty() * param[i].getPlanCaseQty() + param[i].getPlanPieceQty());
				if (!param[i].getCasePieceflg().equals(getCasePieceFlag(param[i])))
				{
					// Flag to determine the displayed message
					messageFlag = true;
				}
				param[i].setCasePieceflg(getCasePieceFlag(param[i]));		
				// Obtain the planning category.
				// Set the Planning category for 0th parameter in StoragePlanModifyBusiness2 class.
				param[i].setRegistKbn(param[0].getRegistKbn());

				// Execute the process for deleting Delete process of source information (Work status, Plan info, and inventory information ).
				planOperator.updateStoragePlan(param[i].getStoragePlanUKey(), wProcessName);

				// Enable to check for duplication in the DB for data with Planning category Extraction or Add via Screen.
				// Disable to check for duplication in the DB for data with Planning category "Linked to Receiving ".
				if (StorageSupportParameter.REGIST_KIND_NOT_INSTOCK.equals(param[i].getRegistKbn())) 
				{
					// Check for duplication across DB (Check the Storage Plan info)
					StoragePlan[] storage = planOperator.findStoragePlanForUpdate(param[i]);
					if (storage != null && storage.length != 0)
					{
						// 6023273=No.{0}{1}
						// 6006007=Cannot add. The same data already exists.
						wMessage = "6023273" + wDelim + param[i].getRowNo() + wDelim + MessageResource.getMessage("6006007");
						return null;

					}
				}
				// Set the data for generating a Storage Plan information.
				// Total Storage Planned Qty
				param[i].setTotalPlanQty(param[i].getEnteringQty() * param[i].getPlanCaseQty() + param[i].getPlanPieceQty());
				// Batch No.
				param[i].setBatchNo(batch_seqno);
				// Worker Code
				param[i].setWorkerCode(param[0].getWorkerCode());
				// Worker Name
				param[i].setWorkerName(workerName);
				// Terminal No.
				param[i].setTerminalNumber(param[0].getTerminalNumber());
				// Add division
				if (StorageSupportParameter.REGIST_KIND_NOT_INSTOCK.equals(param[i].getRegistKbn()))
				{
					//Extract/Add via Screen
					param[i].setRegistKbn(SystemDefine.REGIST_KIND_WMS);
					param[i].setSupplierCode("");
					param[i].setSupplierName1("");
				}
				else if (SystemDefine.REGIST_KIND_INSTOCK.equals(param[i].getRegistKbn()))
				{
					// Link with Receiving
					param[i].setRegistKbn(SystemDefine.REGIST_KIND_INSTOCK);
					// Set the supplier code and the supplier name that were set for the deleted data, for a new data to be added if the planning category is "Linked to Receiving ",
					wPlanKey.KeyClear();
					wPlanKey.setStoragePlanUkey(param[i].getStoragePlanUKey());
					StoragePlan storagePlan = (StoragePlan) wPlanHandler.findPrimary(wPlanKey);
					param[i].setSupplierCode(storagePlan.getSupplierCode());
					param[i].setSupplierName1(storagePlan.getSupplierName1());
				}
				// Process name
				param[i].setRegistPName(wProcessName);

				// Generate Storage Plan information (Plan information, Work status, inventory information)
				planOperator.createStoragePlan(param[i]);

			}
			// Search for the Storage Plan information again.
			StorageSupportParameter[] viewParam = (StorageSupportParameter[]) this.query(conn, param[0]);

			registFlag = true;

			if (messageFlag)
			{
				// 6021014 = Case/Piece Class is changed in the data. Please confirm Storage Plan Inquiry.
				wMessage = "6021014";
			}
			else
			{
				// 6001004 = Modified.
				wMessage = "6001004";
			}
			// Return the last Storage Plan info.
			return viewParam;
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
	 * Generate handler and search keys of Storage Plan info.
	 *
	 * @param conn Database connection object
	 */
	protected void getStoragePlanHandler(Connection conn)
	{
		wPlanHandler = new StoragePlanHandler(conn);
		wPlanKey = new StoragePlanSearchKey();
	}

	/**
	 * Generate handler and search keys of Storage Plan info.
	 *
	 * @param conn Database connection object
	 */
	protected void getWorkingInformationHandler(Connection conn)
	{
		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();
	}

	/**
	 * Search for the Work Status / Storage Plan information to be updated and lock it.<BR>
	 * Invoke a method to generate instance before invoking this method.<BR>
	 * Return true if successfully searched and locked the data to be updated. Return false if failed to search due to error via other terminal.<BR>
	 * Regard the following cases as error via other terminal.<BR>
	 * <DIR>
	 *   -If no search result after searching through the Work status,<BR>
	 *   -If no search result when searching for the sorted Plan info, or <BR>
	 *   -If the data shown on the display differs from the last update date/time, when searching through the sorted Plan info .
	 * </DIR>
	 *
	 * @param inputParam <CODE>SortingParameter</CODE> that includes data to be updated.
	 * @return Return whether successfully searched the data to be updated and locked it
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	protected boolean lock(StorageSupportParameter inputParam) throws ReadWriteException, ScheduleException
	{
		try
		{
			// Search for the Work status.
			WorkingInformation[] workInfo = null;

			// Set the search conditions.
			wWorkKey.KeyClear();
			wWorkKey.setPlanUkey(inputParam.getStoragePlanUKey());

			// Search for the Work Status and lock it.
			workInfo = (WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);

			// No corresponding data exists in Work status (updated at other station)
			if (workInfo == null || workInfo.length == 0)
			{
				return false;
			}

			// Search through the Storage Plan information.
			StoragePlan storage = null;

			// Set the search conditions.
			wPlanKey.KeyClear();
			wPlanKey.setStoragePlanUkey(inputParam.getStoragePlanUKey());

			storage = (StoragePlan) wPlanHandler.findPrimaryForUpdate(wPlanKey);

			// If there is no search result (data was deleted)
			if (storage == null)
			{
				return false;
			}
			// Last update date/time does not correspond (updated at other work station)
			if (!storage.getLastUpdateDate().equals(inputParam.getLastUpdateDate()))
				return false;

		}
		catch (NoPrimaryException e)
		{
			// 6026020 = Multiple records are found in a search by the unique key. {0}{0}
			RmiMsgLogClient.write("6026020" + wDelim + "DnStoragePlan", wProcessName);
			// 6006040 = Data mismatch occurred. See log.{0}
			wMessage = "6006040" + wProcessName;
			throw new ScheduleException(e.getMessage());
		}

		return true;
	}
	// Private methods -----------------------------------------------

	/**
	 * Search through the Storage Plan info and check for duplication of data with the modified contents.
	 *
	 * @param conn Instance to maintain database connection.
	 * @param parameter search conditions
	 * @param casePieceFlag Case/Piece division
	 * @return Count of duplicate data
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	private int checkStoragePlan(Connection conn, StorageSupportParameter parameter, String casePieceFlag) throws ReadWriteException
	{
		StoragePlanSearchKey searchKey = new StoragePlanSearchKey();
		StoragePlanHandler storagePlanHandler = new StoragePlanHandler(conn);

		searchKey.KeyClear();
		searchKey.setPlanDate(parameter.getStoragePlanDate());
		searchKey.setConsignorCode(parameter.getConsignorCode());
		searchKey.setItemCode(parameter.getItemCode());
		// Case
		if (casePieceFlag.equals(StorageSupportParameter.CASEPIECE_FLAG_CASE))
		{
			// ((division: Mixed OR division: Case) AND Work location: Case Location )
			searchKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_MIXED, "=", "((", "", "OR");
			searchKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_CASE, "=", "", ")", "AND");
			searchKey.setCaseLocation(parameter.getCaseLocation(), "=", "", ")", "AND");
		}
		// Piece
		else if (casePieceFlag.equals(StorageSupportParameter.CASEPIECE_FLAG_PIECE))
		{
			// ((Division:Mixed OR Division:Piece) AND Work Location:Piece Location)
			searchKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_MIXED, "=", "((", "", "OR");
			searchKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_PIECE, "=", "", ")", "AND");
			searchKey.setPieceLocation(parameter.getPieceLocation(), "=", "", ")", "AND");
		}
		// None
		else if (casePieceFlag.equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING))
		{
			searchKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_NOTHING);
			searchKey.setCaseLocation(parameter.getCaseLocation(), "=", "(", "", "AND");
			searchKey.setPieceLocation(parameter.getPieceLocation(), "=", "", ")", "AND");

		}
		// Mixed
		else
		{
			// ((Division:Case AND Work Location:Case Location) OR
			// (Division:Piece  AND Work Location:Piece Location) OR
			// (Division is Mixed, AND Work location is Case Location, AND Work location is Piece Location )
			searchKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_CASE, "=", "((", "", "AND");
			searchKey.setCaseLocation(parameter.getCaseLocation(), "=", "", ")", "OR");
			searchKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_PIECE, "=", "(", "", "AND");
			searchKey.setPieceLocation(parameter.getPieceLocation(), "=", "", ")", "OR");
			searchKey.setCasePieceFlag(StorageSupportParameter.CASEPIECE_FLAG_MIXED, "=", "(", "", "AND");
			searchKey.setCaseLocation(parameter.getCaseLocation(), "=", "", "", "AND");
			searchKey.setPieceLocation(parameter.getPieceLocation(), "=", "", "))", "AND");
		}
		searchKey.setStatusFlag(StorageSupportParameter.STATUS_FLAG_DELETE, "!=");
		searchKey.setRegistKind(SystemDefine.REGIST_KIND_INSTOCK, "!=");
		searchKey.setStoragePlanUkey(parameter.getStoragePlanUKey(), "!=");
		// Search(Storage Plan info)
		return storagePlanHandler.count(searchKey);
	}
	
	/**
	 * 入力された内容のケースピース区分を求めます。
	 * @param param ケースピース区分を求めるための情報を含む<CODE>StorageSupportParameter</CODE>
	 * @return ケースピース区分
	 * @throws ScheduleException スケジュール処理の実行中に予期しない例外が発生した場合に通知されます。
	 */
	private String getCasePieceFlag(StorageSupportParameter parameter) throws ScheduleException
	{
		// 入庫予定情報ハンドラ類のインスタンス生成
		StoragePlanOperator planOperator = new StoragePlanOperator();
		
		if (parameter.getCasePieceflg().equals(StorageSupportParameter.CASEPIECE_FLAG_NOTHING)
		 && (StringUtil.isBlank(parameter.getCaseLocation()) && StringUtil.isBlank(parameter.getPieceLocation())))
		{
			return StorageSupportParameter.CASEPIECE_FLAG_NOTHING;
		}
		else
		{
			return planOperator.getCasePieceFlag(parameter);
		}
	}

}
//end of class
