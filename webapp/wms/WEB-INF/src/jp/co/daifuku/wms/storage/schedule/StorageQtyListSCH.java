package jp.co.daifuku.wms.storage.schedule;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ResultViewSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ResultViewHandler;
import jp.co.daifuku.wms.base.display.web.WmsFormatter;
import jp.co.daifuku.wms.base.entity.ResultView;
import jp.co.daifuku.wms.storage.report.StorageQtyWriter;

/*
 * Created on 2004/09/29
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
/**
 * Designer : suresh kayamboo<BR>
 * Maker 	: suresh kayamboo<BR>
 * <BR>
 * <CODE>StorageQtyListSCH</CODE> class prints a list of Storage Result data inquiry.<BR>
 * Receive the contents entered via screen as a parameter and execute the process for printing the storage result list.<BR>
 * Each method in this class receives a connection object and executes the process for updating the database.<BR>
 * However, each method disables to commit and roll back of transactions.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE>Method)
 * <DIR>
 * 	Search for Consignor Code through the Result data inquiry. If only the same Consignor Code exists,<BR>
 * 	return the Consignor Code.<BR>
 * 	Return null if two or more Consignor Code exist.<BR>
 * </DIR>
 * <BR>
 * 2.Process by clicking on Print button (<CODE>startSCH()</CODE>Method)<BR>
 * <DIR>
 * 	Receive the contents entered via screen as a parameter and print the Storage Result List using this condition.<BR>
 * 	Return true when the process normally completed.<BR>
 * 	Return false when error occurs during printing the report.<BR>
 * 	Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 * 	<Parameter> *Mandatory Input<BR>
 * <BR>
 * 	Consignor code*<BR>
 * 	Start Storage Date<BR>
 * 	End Storage Date<BR>
 * 	Item Code<BR>
 * 	Case/Piece division<BR>
 * <BR>
 * 	<Print Process><BR>
 * 	1.Set the value, which was set for the parameter, in the <CODE>StorageSupportParameter</CODE> class.<BR>
 * 	2.Print a Storage Result List using <CODE>StorageQtyWriter</CODE> class.<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/09/29</TD><TD>suresh kayamboo</TD><TD>New</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author suresh kayamboo
 * @version $Revision 1.2 2004/09/29
 */
public class StorageQtyListSCH extends AbstractStorageSCH
{
	//Constants----------------------------------------

	//Attributes---------------------------------------

	//Static-------------------------------------------

	//Constructors-------------------------------------

	// Public methods ---------------------------------
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
		// Generate result info instance of handlers.
		ResultViewHandler wObj = new ResultViewHandler(conn);
		ResultViewSearchKey wKey = new ResultViewSearchKey();

		StorageSupportParameter wParam = new StorageSupportParameter();

        // Set the search conditions.
		wKey.setJobType(ResultView.JOB_TYPE_STORAGE);
		wKey.setConsignorCodeCollect(" ");
		wKey.setConsignorCodeGroup(1);

		if (wObj.count(wKey) == 1)
		{
			try
			{
				ResultView[] resultview = (ResultView[]) wObj.find(wKey);
				wParam.setConsignorCode(resultview[0].getConsignorCode());
			}
			catch (Exception e)
			{
				return new StorageSupportParameter();
			}
		}

		return wParam;
	}
	/**
	 * Start the schedule. According to the contents set in the parameter array designated in the <CODE>startParams</CODE>, <BR>
	 * execute the process for the schedule. The implement for scheduling depends on the class implementing this interface. <BR>
	 * Return true when the schedule process completed successfully. <BR>
	 * Return false if failed to schedule due to condition error or other causes. <BR>
	 * In this case,  enable to obtain the contents using <CODE>getMessage()</CODE>  method.
	 *
	 * @param conn Database connection object
	 * @param startParams Search condition object to search across the database.
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams) throws ReadWriteException,
			ScheduleException
	{
		StorageSupportParameter sParam = (StorageSupportParameter) startParams[0];
		if (sParam == null)
		{
			wMessage = "6003010";
			return false;
		}

		// Generate a print class.
		StorageQtyWriter spWriter = createWriter(conn, sParam);

		//Execute printing.
		if (spWriter.startPrint())
		{
			wMessage = "6001010";
			return true;
		} else
		{
			wMessage = spWriter.getMessage();
			return false;
		}
	}

	/**
	 * Obtain the Count of print targets based on the info entered via screen.<BR>
	 * If no target data found, or if input error found, return 0 (count).<BR>
	 * When 0 count is found, using <CODE>getMessage</CODE> in the process by the invoking source and
	 * obtain the error message.<BR>
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
		StorageQtyWriter writer = createWriter(conn, param);
		// Obtain the count of targets.
		int result = writer.count();
		if (result == 0)
		{
			// 6003010 = Print data was not found.
			wMessage = "6003010";
		}

		return result;

	}

	// Protected methods ---------------------------------

	/**
	 * Set the Information entered via screen for a print process class and
	 * Generate a print process class.<BR>
	 *
	 * @param conn Database connection object
	 * @param parameter Parameter object that includes search conditions
	 * @return Print Process class
	 */
	protected StorageQtyWriter createWriter(Connection conn, StorageSupportParameter sParam)
	{
		// Generate a print process class.
		StorageQtyWriter spWriter = new StorageQtyWriter(conn);
		if (!StringUtil.isBlank(sParam.getConsignorCode()))
		{
			spWriter.setConsignorCode(sParam.getConsignorCode());
		}

		// Set the Start Storage Date.
		if (!StringUtil.isBlank(sParam.getFromStorageDate()))
		{
			spWriter.setStartStorageWorkDate(WmsFormatter.toParamDate(sParam.getFromStorageDate()));
		}
		// Set the End Storage Date.
		if (!StringUtil.isBlank(sParam.getToStorageDate()))
		{
			spWriter.setEndStorageWorkDate(WmsFormatter.toParamDate(sParam.getToStorageDate()));
		}
		// Set the Item Code.
		if (!StringUtil.isBlank(sParam.getItemCode()))
		{
			spWriter.setItemCode(sParam.getItemCode());
		}
		// Set the Case  Piece division.
		if (!StringUtil.isBlank(sParam.getCasePieceflg()))
		{
			spWriter.setCasePieceFlag(sParam.getCasePieceflg());
		}

		return spWriter;
	}

	// Private methods -----------------------------------

	//InnerClasses----------------------------------------

}
