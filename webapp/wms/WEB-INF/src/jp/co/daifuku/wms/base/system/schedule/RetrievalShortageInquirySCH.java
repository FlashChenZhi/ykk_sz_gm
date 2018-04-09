//#CM698992
//$Id: RetrievalShortageInquirySCH.java,v 1.2 2006/11/13 06:03:04 suresh Exp $

//#CM698993
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.base.system.schedule;

import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoSearchKey;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.system.report.RetrievalShortageInquiryWriter;
//#CM698994
/**
 * Designer : Y.Okamura <BR>
 * Maker : Y.Okamura <BR>
 * <BR>
 * Allow this class to invoke the class for executing the process for printing Shortage checklist.<BR>
 * Receive the contents entered via screen as a parameter and invoke the class for printing the Shortage check list.<BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1.Initial Display Process(<CODE>initFind()</CODE> method)<BR>
 * <DIR>
 *   If only one consignor code exists in the shortage info, return the corresponding consignor code.<BR>
 *   Return null if no corresponding data found, or two or more corresponding data exist.<BR>
 * </DIR>
 * 
 * 2.Process by clicking "Print" button (<CODE>startSCH()</CODE> method)<BR>
 * <BR>
 * <DIR>
 *   Receive the contents entered via screen as a parameter and pass the parameter to the class for printing the Shortage check list.<BR>
 *   Search for the printing contents in the Writer class.<BR>
 *   Receive true if printing succeeded or false if failed.<BR>
 *   Use the <CODE>getMessage()</CODE> method to refer the content of error.<BR>
 * <BR>
 *   <Parameter> *Mandatory Input<BR>
 *   <DIR>
 *     Added date*
 *     Consignor Code*
 *     Unit of View*
 *   </DIR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/02/09</TD><TD>Y.Okamura</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/13 06:03:04 $
 * @author  $Author: suresh $
 */
public class RetrievalShortageInquirySCH extends AbstractSystemSCH
{

	//#CM698995
	// Class variables -----------------------------------------------

	//#CM698996
	// Class method --------------------------------------------------
	//#CM698997
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/13 06:03:04 $");
	}
	//#CM698998
	// Constructors --------------------------------------------------
	//#CM698999
	/**
	 * Initialize this class.
	 */
	public RetrievalShortageInquirySCH()
	{
		wMessage = null;
	}

	//#CM699000
	// Public methods ------------------------------------------------
	//#CM699001
	/**
	 * This method obtains the data required for initial display.<BR>
	 * If only one consignor code exists in the shortage info, return the corresponding consignor code.<BR>
	 * Return null if no corresponding data found, or two or more corresponding data exist.<BR>
	 * Set null for searchParam because search conditions are not required. <BR>
	 * <BR>
	 * @param conn        Database connection object
	 * @param searchParam Class that inherits <CODE>Parameter</CODE> class with search conditions.
	 * @return A class that implements the <CODE>Parameter</CODE> interface that contains the search result.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs in the process of checking.
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)
		throws ReadWriteException, ScheduleException
	{
		SystemParameter param = new SystemParameter();

		ShortageInfoHandler shortageHandler = new ShortageInfoHandler(conn);
		ShortageInfoSearchKey shortageKey = new ShortageInfoSearchKey();

		//#CM699002
		// Search the Added Date.
		//#CM699003
		// Group by Batch No. and obtain the minimum value of Added Date/Time.
		shortageKey.KeyClear();
		shortageKey.setBatchNoCollect("");
		shortageKey.setRegistDateCollect("MIN");
		shortageKey.setBatchNoGroup(1);

		//#CM699004
		// Search the Added Date/Time.
		ShortageInfo[] shortageArray = (ShortageInfo[]) shortageHandler.find(shortageKey);

		//#CM699005
		// Set the search result for the return value.
		if (shortageArray != null && shortageArray.length != 0)
		{
			Date[] registDate = new Date[shortageArray.length];

			for (int i = 0; i < shortageArray.length; i++)
			{
				registDate[i] = shortageArray[i].getRegistDate();
			}
			
			//#CM699006
			// Sort the Added Date/Time.
			for (int i = 0; i < registDate.length; i++)
			{
				for (int j = i; j < registDate.length; j++)
				{
					if (registDate[i].compareTo(registDate[j]) < 0)
					{
						Date dateTmp = registDate[i];
						registDate[i] = registDate[j];
						registDate[j] = dateTmp;
					}
				}
			}

			//#CM699007
			// Set the search result for the return value.
			param.setRegistDateArray(registDate);
		}

		//#CM699008
		// Search for the Consignor Code.
		try
		{
			//#CM699009
			// Use consignor code as GROUP BY condition.
			shortageKey.KeyClear();
			shortageKey.setConsignorCodeGroup(1);
			shortageKey.setConsignorCodeCollect("");

			if (shortageHandler.count(shortageKey) == 1)
			{
				//#CM699010
				// Search for the Consignor Code.
				ShortageInfo shortage = (ShortageInfo) shortageHandler.findPrimary(shortageKey);

				//#CM699011
				// Set the search result for the return value.
				param.setConsignorCode(shortage.getConsignorCode());
			}

		}
		catch (NoPrimaryException e)
		{
			return (new SystemParameter());
		}

		return param;

	}

	//#CM699012
	/**
	 * Receive the contents entered via screen as a parameter and pass the parameter to the class for printing the View list.<BR>
	 * Disables to print with no print data.<BR>
	 * Receive true from the class for printing the view list if printing succeeded or false if failed, and return the result.
	 * <BR>
	 * Use the <CODE>getMessage()</CODE> method to refer the content of error.<BR>
	 * 
	 * @param conn        Database connection object
	 * @param startParams Array of the instance of the <CODE>StorageSupportParameter</CODE> class with contents of setting.<BR>
	 * @return Return true when succeeded in the schedule process, or return false when failed in the schedule process.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of scheduling.
	 */
	public boolean startSCH(Connection conn, Parameter[] startParams)
		throws ReadWriteException, ScheduleException
	{

		//#CM699013
		// Translate the type of startParams.
		SystemParameter param = (SystemParameter) startParams[0];

		//#CM699014
		// Generate a print class.
		RetrievalShortageInquiryWriter writer = createWriter(conn, param);
		
		//#CM699015
		// Start the printing process.
		if (writer.startPrint())
		{
			//#CM699016
			// 6001010=Print completed successfully.
			wMessage = "6001010";
			return true;
		}
		else
		{
			//#CM699017
			// Display the error message.
			wMessage = writer.getMessage();
			return false;
		}
	}

	//#CM699018
	/** 
	 * Obtain the Count of print targets based on the info entered via screen.<BR>
	 * If no target data found, or if input error found, return 0 (count).<BR>
	 * When 0 data is found, obtain the error message using <CODE>getMessage</CODE> in the process by the invoking source.
	 * <BR>
	 * 
	 * @param conn       Database connection object
	 * @param countParam <CODE>Parameter</CODE> object that includes search conditions.
	 * @return Count of print targets
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException  Announce it when unexpected exception occurs.
	 */
	public int count(Connection conn, Parameter countParam) throws ReadWriteException, ScheduleException
	{
		SystemParameter param = (SystemParameter) countParam;
		
		//#CM699019
		// Set the search conditions and generate the print process class.
		RetrievalShortageInquiryWriter writer = createWriter(conn, param);
		//#CM699020
		// Obtain the count of target data.
		int result = writer.count();
		if (result == 0)
		{
			//#CM699021
			// 6003010=No print data found.
			wMessage = "6003010";
		}
		
		return result;
	
	}
	
	//#CM699022
	// Protected methods ------------------------------------------------
	//#CM699023
	/** 
	 * Set the Information entered via screen for a print process class and
	 * generate a print process class.<BR>
	 * 
	 * @param conn      Database connection object
	 * @param parameter Parameter object that includes search conditions
	 * @return A class to execute printing process.
	 */
	protected RetrievalShortageInquiryWriter createWriter(Connection conn, SystemParameter param)
	{
		//#CM699024
		// Set the print condition input via screen for print process class.
		RetrievalShortageInquiryWriter writer = new RetrievalShortageInquiryWriter(conn);
		writer.setRegistDate(param.getRegistDate());
		writer.setConsignorCode(param.getConsignorCode());
		if (param.getDispType().equals(SystemParameter.DISP_TYPE_ITEM))
		{
			writer.setDispType(RetrievalShortageInquiryWriter.DISP_TYPE_ITEM);
		}
		else
		{
			writer.setDispType(RetrievalShortageInquiryWriter.DISP_TYPE_PLAN);
		}
		
		return writer;

	}
	
}
//#CM699025
//end of class
