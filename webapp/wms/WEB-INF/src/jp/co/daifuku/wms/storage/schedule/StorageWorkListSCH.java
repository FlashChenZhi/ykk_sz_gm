package jp.co.daifuku.wms.storage.schedule;
/*
 * Created on Sep 28, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
// JDK imports
import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.storage.report.StorageWriter;

/**
 * Designer : Muneendra <BR>
 * Maker : Muneendra <BR>
 * This class allows to invoke the process for printing WEB storage work list.<BR>
 * Receive the contents entered via screen as a parameter and invoke the class for printing the storage work list.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method) <BR><BR><DIR>
 *   If only one Consignor Code exists in the Storage Plan info, return the corresponding Consignor Code. <BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist. <BR>
 * <BR>
 *   <Search conditions> <BR><DIR>
 *     Work division:Storage<BR>
 *     Status flag: other than Deleted (9) <BR>
 *     Start Work flag:Started(1) <BR></DIR></DIR>
 * <BR>
 * 2.Process by clicking on Print button (<CODE>startSCH()</CODE>Method) <BR><BR><DIR>
 *   Receive the contents entered via screen as a parameter and pass the parameter to the class for printing the storage work list.<BR>
 *   Search for the printing contents in the Writer class.<BR>
 *   If successfully printed, receive true from the class for printing the work list. If failed, receive false<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 *   <Parameter> *Mandatory Input <BR><DIR>
 *     Consignor code* <BR>
 *     Planned start storage Date <BR>
 *     Planned end storage Date <BR>
 *     Item Code<BR>
 *     Case/Piece division* <BR>
 * 	   Work Status*</DIR>
 * <BR>
 *   <Print Process> <BR>
 * <DIR>
 * 	   1.Set the value that was set for parameter in the <CODE>StorageWriter</CODE> class.<BR>
 * 	   2.Using the <CODE>StorageWriter</CODE> class, print the Storage Work List.<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/28</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/18 06:53:41 $
 * @author  $Author: suresh $
 */
public class StorageWorkListSCH extends AbstractStorageSCH
{
	// Class variables -----------------------------------------------

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/18 06:53:41 $");
	}
	// Constructors --------------------------------------------------
	/**
	 * Initialize this class.
	 */
	public StorageWorkListSCH()
	{
		wMessage = null;
	}

	// Public methods ------------------------------------------------
	/**
	 * This method supports operations to obtain the data required for initial display.<BR>
	 * If only one Consignor Code exists in the Storage Plan info, return the corresponding Consignor Code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 * @param conn Connection Database connection object
	 * @param searchParam Parameter This Class inherits the <CODE>Parameter</CODE> class with search conditions
	 * @return This class implements the <CODE>Parameter</CODE> interface that contains search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{

		WorkingInformationSearchKey searchKey = new WorkingInformationSearchKey();
		searchKey.setConsignorCodeGroup(1);
		searchKey.setConsignorCodeCollect();
		WorkingInformationHandler workinfoHandle = new WorkingInformationHandler(conn);

		StorageSupportParameter parameter = null;
		// Set the Work division.
		searchKey.setJobType(SystemDefine.JOB_TYPE_STORAGE);
        //COLLECT FIELDS
		searchKey.setConsignorCodeCollect("");
		// Search for data other than ASRS work.
		searchKey.setSystemDiscKey(SystemDefine.SYSTEM_DISC_KEY_ASRS, "!=");

		if (workinfoHandle.count(searchKey) == 1)
		{
			WorkingInformation wi[] = (WorkingInformation[]) workinfoHandle.find(searchKey);

			if (wi.length == 1)
			{
				parameter = new StorageSupportParameter();
				parameter.setConsignorCode(wi[0].getConsignorCode());
				parameter.setConsignorName(wi[0].getConsignorName());
			}
		}
		return parameter;
	}

	/**
	 * Receive the contents entered via screen as a parameter and start the storage schedule.<BR>
	 * Assume that two or more data may be input via preset area or others. So, require the parameter to receive them in the form of array.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * If the division in the <CODE>StorageSupportParameter</CODE> instance for printing the storage instruction is true, <BR>
	 * execute the process for printing the storage instruction list using the <CODE>StorageListReportWriter</CODE> class.<BR>
	 * Return true if the schedule normally completed, or return false if failed.
	 * @param conn Instance to maintain database connection.
	 * @param startParams Array of <CODE>StorageListReportWriter</CODE> class instance with commitment contents.
	 * @return True if the schedule process is normal. Else false.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)throws ReadWriteException, ScheduleException
	{
		// Input information of the parameter
		StorageSupportParameter storageParameter = (StorageSupportParameter) startParams[0];

		if (storageParameter == null)
		{
			wMessage = "6027005";
			throw new ScheduleException();
		}

		StorageWriter writer = createWriter(conn, storageParameter);

		if (writer.startPrint())
		{
			wMessage = "6001010";
			return true;
		}
		else
		{
			wMessage = writer.getMessage();
			return false;
		}

	}

	/**
	 * Obtain the Count of print targets based on the info entered via screen.<BR>
	 * If no target data found, or if input error found, return 0 (count).<BR>
	 * When 0 count is found, use <CODE>getMessage</CODE> in the process by the invoking source and
	 * obtain the error message.<BR>
	 *
	 * @param conn Database connection object
	 * @param countParam <CODE>Parameter</CODE> object that includes search conditions
	 * @return Count of print targets
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		StorageSupportParameter param = (StorageSupportParameter) countParam;

		// Set the search conditions and generate the print process class.
		StorageWriter writer = createWriter(conn, param);
		// Obtain the count of targets.
		int result = writer.count();
		if (result == 0)
		{
			// 6003010 = Print data was not found.
			wMessage = "6003010";
		}

		return result;

	}

	// Protected methods ------------------------------------------------

	/**
	 * Set the Information entered via screen for a print process class and
	 * generate a print process class.<BR>
	 *
	 * @param conn Database connection object
	 * @param parameter Parameter object includes search conditions
	 * @return Print Process class
	 */
	protected StorageWriter createWriter(Connection conn, StorageSupportParameter storageParameter)
	{

		StorageWriter writer = new StorageWriter(conn);

        // Set the value for storage location.
		writer.setConsignorCode(storageParameter.getConsignorCode());
		writer.setFromPlanDate(storageParameter.getFromStoragePlanDate());
		writer.setToPlanDate(storageParameter.getToStoragePlanDate());
		writer.setItemCode(storageParameter.getItemCode());
		writer.setStatusFlag(storageParameter.getWorkStatus());
		writer.setCasePieceFlag(storageParameter.getCasePieceflg());
		return writer;
	}

}
