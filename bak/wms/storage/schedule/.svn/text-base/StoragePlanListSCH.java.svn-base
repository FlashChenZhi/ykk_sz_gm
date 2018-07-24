// $Id: StoragePlanListSCH.java,v 1.2 2006/10/18 06:46:08 suresh Exp $

/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.storage.schedule;

import java.sql.Connection;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.storage.report.StoragePlanWriter;

/**
 * Designer : suresh kayamboo <BR>
 * Maker : suresh kayamboo <BR>
 * <BR>
 * This class allows to invoke the process for printing the storage plan list.<BR>
 * Receive the contents entered via screen as a parameter and invoke the class for printing the storage plan list.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)<BR>
 * <DIR>
 *   If only one Consignor Code exists in the Storage Plan info, return the corresponding Consignor Code.<BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.<BR>
 * <BR>
 *   <Search conditions>
 *   <DIR>
 *     Status flag: Other than delete <BR>
 *   </DIR>
 * </DIR>
 *
 * 2.Process by clicking on Print button (<CODE>startSCH()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and pass the parameter to the class for printing the plan list.<BR>
 *   Search for the printing contents in the Writer class.<BR>
 *   Receive true from the plan list print process class if succeeded in printing, or receive false when failed.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR>
 *   <DIR>
 * 		Consignor code*<BR>
 * 		Planned start storage Date<BR>
 * 		Planned end storage Date<BR>
 * 		Item Code<BR>
 * 		Work Status<BR>
 *   </DIR>
 *   <BR>
 *   <Check for process condition>
 *   <DIR>
 *     None<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/20</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/18 06:46:08 $
 * @author  $Author: suresh $
 */
public class StoragePlanListSCH extends AbstractStorageSCH
{


	//	 Public methods ------------------------------------------------
	/**
	 * This method supports operations to obtain the data required for initial display. <BR>
	 * For example, Initial Display for Consignor Code button, External Data Extraction button. <BR>
	 * Search conditions passes a class that inherits the <CODE>Parameter</CODE>  class. <BR>
	 * Requiring no search conditions sets null for <CODE>searchParam</CODE>.
	 *
	 * @param conn Database connection object
	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class with search conditions.
	 * @return This Class implements the <CODE>Parameter</CODE> interface containing search results.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam) throws ReadWriteException,
			ScheduleException
	{
		// Generate Planned Storage Information instance of handlers.
		StoragePlanSearchKey wKey = new StoragePlanSearchKey();
		StoragePlanHandler wObj = new StoragePlanHandler(conn);
		StorageSupportParameter wParam = null;

		// Set the search conditions.
		// Status flag= other than "Delete "
		wKey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
		wKey.setConsignorCodeGroup(1);
		wKey.setConsignorCodeCollect();

		if (wObj.count(wKey) == 1)
		{
			StoragePlan storage[] = (StoragePlan[]) wObj.find(wKey);

            // If only one Consignor code corresponds,
			if (storage.length == 1)
			{
				wParam = new StorageSupportParameter();
				wParam.setConsignorCode(storage[0].getConsignorCode());
			}
		}
		return wParam;
	}

	/**
	 * Obtain the Count of print targets based on the info entered via screen.<BR>
	 * If no target data found, or if input error found, return 0 (count).<BR>
	 * When 0 count of data is found, use <CODE>getMessage</CODE> in the process by the invoking source, and
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
		StoragePlanWriter writer = createWriter(conn, param);
		// Obtain the target count.
		int result = writer.count();
		if (result == 0)
		{
			// 6003010 = Print data was not found.
			wMessage = "6003010";
		}

		return result;

	}

	/**
	 * Receive the contents entered via screen as a parameter and pass the parameter to the class for printing the Storage Plan list.<BR>
	 * Disables to print with no print data.<BR>
	 * Receive true from the plan list print process class if succeeded in printing, or receive false if failed, and
	 * return the result.<BR>
	 * Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
	 * Start the schedule. According to the contents set in the parameter array designated in the <CODE>startParams</CODE>, <BR>
	 * execute the process for the schedule. The implement for scheduling depends on the class implementing this interface. <BR>
	 * Return true when the schedule process completed successfully. <BR>
	 * Return false if failed to schedule due to condition error or other causes. <BR>
	 * In this case,  enable to obtain the contents using <CODE>getMessage()</CODE>  method.<BR>
	 * @param conn Database connection object
	 * @param startParams Search condition object to search across the database.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException,
			ScheduleException
	{
		boolean isOk = false;
		StorageSupportParameter sParam = (StorageSupportParameter) startParams[0];
		if (sParam != null)
		{
			StoragePlanWriter spWriter = createWriter(conn, sParam);

			//Execute printing.
			isOk = spWriter.startPrint();
			if (isOk)
			{
				wMessage = "6001010";
			} else
			{
				wMessage = spWriter.getMessage();
			}
		}
		else
		{
			wMessage = "6003010";
			return false;
		}
		return isOk;
	}

	//	 Protected methods ------------------------------------------------
	/**
	 * Set the Information entered via screen for a print process class and
	 * Generate a print process class.<BR>
	 * @param conn Database connection object
	 * @param parameter Parameter object that includes search conditions
	 * @return Print Process class
	 */
	protected StoragePlanWriter createWriter(Connection conn, StorageSupportParameter sParam)
	{
		StoragePlanWriter spWriter = new StoragePlanWriter(conn);
		// Set the Consignor Code.
		if (!StringUtil.isBlank(sParam.getConsignorCode()))
		{
			spWriter.setConsignorCode(sParam.getConsignorCode());
		}

		// Set the Planned start storage date.
		if (!StringUtil.isBlank(sParam.getFromStoragePlanDate()))
		{
			spWriter.setStartStoragePlanDate(sParam.getFromStoragePlanDate());
		}
		// Set the Planned end storage date.
		if (!StringUtil.isBlank(sParam.getToStoragePlanDate()))
		{
			spWriter.setEndStoragePlanDate(sParam.getToStoragePlanDate());
		}
		// Set the Item Code.
		if (!StringUtil.isBlank(sParam.getItemCode()))
		{
			spWriter.setItemCode(sParam.getItemCode());
		}
		// Set the Case/Piece division.
		if (!StringUtil.isBlank(sParam.getCasePieceflg()))
		{
			spWriter.setCasePieceFlag(sParam.getCasePieceflg());
		}
		// Set the status flag.
		if(!StringUtil.isBlank(sParam.getWorkStatus()))
		{
			spWriter.setStatusFlag(sParam.getWorkStatus()) ;
		}
		return spWriter;
	}
}
