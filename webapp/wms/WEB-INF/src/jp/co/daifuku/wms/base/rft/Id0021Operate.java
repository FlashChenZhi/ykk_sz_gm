// $Id: Id0021Operate.java,v 1.2 2006/11/14 06:09:03 suresh Exp $
package jp.co.daifuku.wms.base.rft;

//#CM701246
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM701247
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do list request (ID0021) processing of the date. <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * <BR>
 * List acquisition of date(<CODE>findPlanDate()</CODE> Method)<BR>
 * <BR>
 *   <DIR>
 *   Receive Consignor Code, Work type, and Detail Work type as a parameter, and return date List information. <BR>
 *   <BR>
 *   </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:03 $
 * @author  $Author: suresh $
 */
/**
 * Designer : K.Shimizu <BR>
 * Maker :   <BR>
 * <BR>
 * The class to do list request (ID0021) processing of the date. <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * <BR>
 * List acquisition of date(<CODE>findPlanDate()</CODE> Method)<BR>
 * <BR>
 *   <DIR>
 *   Receive Consignor Code, Work type, and Detail Work type as a parameter, and return date List information. <BR>
 *   <BR>
 *   </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:09:03 $
 * @author  $Author: suresh $
 */
public class Id0021Operate extends IdOperate
{
	//#CM701248
	// Class fields----------------------------------------------------
	//#CM701249
	// Class variables -----------------------------------------------
	//#CM701250
	// Constructors --------------------------------------------------
	//#CM701251
	/**
	 * Generate the instance. 
	 */
	public Id0021Operate()
	{
		super();
	}

	//#CM701252
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public Id0021Operate(Connection conn)
	{
		this();
		wConn = conn;
	}

	//#CM701253
	// Class method --------------------------------------------------
	//#CM701254
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.2 $,$Date: 2006/11/14 06:09:03 $";
	}

	//#CM701255
	// Public methods ------------------------------------------------
	//#CM701256
	/**
	 * Acquire the date list from Work information(dnworkinfo). <BR>
	 * From the data whose Status flag is Stand by and Work start flag is Started,<BR>
	 * Acquire the list of date Data in ascending order in condition (Consignor Code,Work type,Detail Work type,
	 * Work plan date) of specifying it. <BR>
	 * However, exclude repetition Data. Moreover, exclude it from Search condition when Consignor Code is space. <BR>
	 * <BR>
	 *   <DIR>
	 *   [Acquisition item]<BR>
	 *     <DIR>
	 *     Plan date<BR>
	 *     </DIR>
	 *   [Search condition]<BR>
	 *     <DIR>
	 *     Work flag      :Acquisition from parameter<BR>
	 *     Consignor Code    :Acquisition from parameter (Remove from the condition for the blank. ) <BR>
	 *     Work plan date    :Acquisition from parameter<BR>
	 *     Status flag    :Thing done while Stand by or automatic terminal and self worker are working<BR>
	 *     Work start flag:Started<BR>
	 *     TC/DC flag     :DC or Cross TC (When work type is 01 and a work detailed type is only 1 or 2) <BR>
	 *     TC/DC flag     :TC (When work type is 01 and a work detailed type is only 3) <BR>
	 *     Order No.   :The one which is not empty (Only when work type is 03 and work detailed type is only 1) <BR>
	 *     Order No.   :The one which is empty (Only when work type is 03 and work detailed type is only 2) <BR>
	 *     </DIR>
	 *   </DIR>
	 * 
	 * @param  consignorCode 	Consignor Code
	 * @param	workType		Work type
	 * @param	workDetails		Detail Work type
	 * @param	rftNo			RFT Number
	 * @param	workerCode		Worker code
	 * @return	Date List information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException  It is notified when date information is not found. 
	 */
	public String[] findPlanDate(
			String consignorCode,
			String workType,
			String workDetails,
			String rftNo,
			String workerCode)
		throws ReadWriteException, NotFoundException
	{
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();

		//#CM701257
		//-----------------
		//#CM701258
		// Set Acquisition item
		//#CM701259
		//-----------------
		//#CM701260
		// Plan date
		workInfoSearchKey.setPlanDateCollect("DISTINCT");

		//#CM701261
		//-----------------
		//#CM701262
		// Set Search condition
		//#CM701263
		//-----------------
		//#CM701264
		// Work flag
		workInfoSearchKey.setJobType(workType);
		//#CM701265
		// Do not add it to the condition for the Consignor Code space. 
		if (consignorCode.trim().length() > 0)
		{
			workInfoSearchKey.setConsignorCode(consignorCode);
		}
		//#CM701266
		// Status flag
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		workInfoSearchKey.setWorkerCode(workerCode);
		workInfoSearchKey.setTerminalNo(rftNo, "=", "", "))", "AND");
		//#CM701267
		// Work start flag
		workInfoSearchKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		if (workType.equals(WorkingInformation.JOB_TYPE_INSTOCK))
		{
			if (workDetails.equals(WorkDetails.INSTOCK_ITEM)
					|| workDetails.equals(WorkDetails.INSTOCK_SUPPLIER))
			{
				//#CM701268
				// Retrieve DC and crossing TC of the unit of the supplier or each item. 
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC, "=", "(", "", "OR");
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC, "=", "", ")", "AND");
			}
			else if (workDetails.equals(WorkDetails.INSTOCK_CUSTOMER))
			{
				//#CM701269
				// Retrieve TC for each and every customer
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
			}
		}
		else if (workType.equals(WorkingInformation.JOB_TYPE_RETRIEVAL))
		{
			if (workDetails.equals(WorkDetails.RETRIEVAL_ORDER))
			{
				//#CM701270
				// Retrieve data where order No is set at the order picking. 
				workInfoSearchKey.setOrderNo("", "IS NOT NULL");
			}
			else if (workDetails.equals(WorkDetails.RETRIEVAL_ITEM))
			{
				//#CM701271
				// Order No retrieves empty data at the item picking. 
				workInfoSearchKey.setOrderNo("", "=");
			}
		}

		//#CM701272
		//-----------------
		//#CM701273
		// The sorting order set
		//#CM701274
		//-----------------
		//#CM701275
		// Date ascending order
		workInfoSearchKey.setPlanDateOrder(1, true);

		//#CM701276
		//-----------------
		//#CM701277
		// Retrieval processing
		//#CM701278
		//-----------------
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
		WorkingInformation[] workInfo =
			(WorkingInformation[]) workInfoHandler.find(workInfoSearchKey);

		//#CM701279
		// When Work information cannot be acquired, throws NotFoundException. 
		if (workInfo == null || workInfo.length == 0)
		{
			throw new NotFoundException();
		}

		//#CM701280
		// List Data acquisition of date
		String[] data = new String[workInfo.length];
		for (int i = 0; i < workInfo.length; i++)
		{
			data[i] = workInfo[i].getPlanDate();
		}

		return data;
	}

	//#CM701281
	// Package methods ----------------------------------------------
	//#CM701282
	// Protected methods --------------------------------------------
	//#CM701283
	// Private methods -----------------------------------------------

}
//#CM701284
//end of class
