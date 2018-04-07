//#CM699578
//$Id: WorkingDateSetSCH.java,v 1.2 2006/11/13 06:03:03 suresh Exp $

//#CM699579
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM699580
/**
 * Designer : Muneendra <BR>
 * Author : Muneendra <BR>
 * <BR> 
 * <BR>
 *1 <CODE>initFind()</CODE> metod: Allow the XXXBusiness class to invoke this methodto obtain the current work date from the DB.<BR>
 * <BR>
 * <DIR>
 *   Obtain the data to be displayed, from DB.<BR>
 *   If no data is found, return <CODE>Parameter</CODE> array with the number of elements equal to 0. If condition error occurs, return null.
 *   Allow the <CODE>getMessage()</CODE> method to refer the content of error.<BR>
 * <BR>
 *  <Parameter> Returned data <BR>
 * <DIR>
 *     Work date<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * 2 <CODE>startSCH</CODE> method: Allow Add button on screen to process this method.<BR>
 * <BR>
 * <DIR> 
 *  Use the WMSUser table to regard the worker code and the password to be valid.<BR>
 * 	Reflect the new work date entered via screen on the Warenavi system table.<BR>
 *   Allow the <CODE>getMessage()</CODE> method to refer the content of error. <BR>
 * <BR>
 *   <Parameter> Data input<BR>
 * <DIR>
 *     Worker Code* <BR>
 *     Password* <BR>   
 *     Work date* <BR>     
 * </DIR>
 * <BR>
 *  <Parameter> Returned data <BR>
 * <DIR>
 *     Boolean: True if update is executed normally. Otherwise, false.<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/08/17</TD><TD>Muneendra Y</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:03 $
 * @author  $Author: suresh $
 */
public class WorkingDateSetSCH extends AbstractSystemSCH
{

	//#CM699581
	// Class variables -----------------------------------------------

	//#CM699582
	// Constructors --------------------------------------------------
	//#CM699583
	/**
	 * Initialize this class.
	 */
	public WorkingDateSetSCH()
	{
		wMessage = null;
	}

	//#CM699584
	/**
	 * This method obtains the data required for initial display.<BR>
	 * Obtain the current work date from the System definitions.<BR>
	 * <BR>
	 * @param conn        Database connection object
	 * @param searchParam Set null for searchParam because search conditions are not required.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		SystemParameter parameter = new SystemParameter();
		//#CM699585
		// Obtain the Work Date.
		parameter.setWorkDate(getWorkDate(conn));

		return parameter;
	}

	//#CM699586
	// Public methods ------------------------------------------------
	//#CM699587
	/**
	 * Receive the entered data via screen as a parameter and check the entered Worker Code and password.<BR>
	 * Update the work date in the eWarenavi system table.<BR>
	 * Return true if the schedule normally completed, otherwise return false.<BR>
	 * @param conn        Database connection object
	 * @param startParams Array of Parameter entered via screen.
	 * @throws ReadWriteException  Announce when error occurs on the connection to database.
	 * @throws ScheduleException  Announce it when unexpected exception occurs during the check process.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{
		//#CM699588
		// Execute casting.
		SystemParameter parameter[] = (SystemParameter[]) startParams;

		//#CM699589
		// Check the Worker code and password.
		if (!checkWorker(conn, parameter[0], false))
		{
			return false;
		}
		//#CM699590
		// Check the daily update.
		if (isDailyUpdate(conn))
		{
			return false;
		}
		//#CM699591
		// Check Processing Load
		if (isLoadingData(conn))
		{
			return false;
		}
		//#CM699592
		// Check for reporting in progress.
		if (isReportData(conn))
		{
			return false;
		}
		
		WorkingInformationHandler wiHandle = new WorkingInformationHandler(conn);
		WorkingInformationSearchKey wiSearchKey = new WorkingInformationSearchKey();
		//#CM699593
		// Disable to execute daily update if any work with status "Processing" exists.
		wiSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING);
		if (wiHandle.count(wiSearchKey) > 0)
		{
			//#CM699594
			// 6007040=Working data exists. Unable to process.
			wMessage = "6007040";
			return false;
		}
		
		try
		{

			WareNaviSystemAlterKey alterKey = new WareNaviSystemAlterKey();

			alterKey.setSystemNo(WareNaviSystem.SYSTEM_NO);
			alterKey.updateWorkDate(parameter[0].getWorkDate());

			WareNaviSystemHandler systemhandler = new WareNaviSystemHandler(conn);
			//#CM699595
			// Update
			systemhandler.modify(alterKey);
			
			//#CM699596
			// 6001006=Data was committed.
			wMessage = "6001006";

			return true;

		}
		catch (NotFoundException notfoundException)
		{
			throw new ReadWriteException(notfoundException.getMessage());
		}
		catch (InvalidDefineException invalidDataException)
		{
			throw new ScheduleException(invalidDataException.getMessage());
		}
	}
}
//#CM699597
//end of class
