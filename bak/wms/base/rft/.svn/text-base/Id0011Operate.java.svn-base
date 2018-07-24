// $Id: Id0011Operate.java,v 1.3 2006/11/14 06:24:36 kamala Exp $
package jp.co.daifuku.wms.base.rft;

//#CM700866
/*
 * Copyright 2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

/**
 * Designer : T.Konishi <BR>
 * Maker :   <BR>
 * <BR>
 * The class to process Plan date Inquiry(ID0011). <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * Receive Consignor Code, Work type, Detail Work type, and Work plan date as a parameter, and retrieve Work information. <BR>
 * Plan date confirmation(<CODE>checkPlanDate()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive Consignor Code, Work type, Detail Work type, and Work plan date as a parameter.
 *   Retrieve Data which can work from Work information. <BR>
 *   Search condition is different according to the combination of Work type and Detail Work type. <BR>
 *
 * <P>
 * <TABLE border="1">
 * <CAPTION>Add Search condition by Work type and Detail Work type</CAPTION>
 * <TR><TH>Work type</TH><TH>Detail Work type</TH>		<TH>Add Search condition</TH></tr>
 * <TR><TD>01:Receiving </TD>	<TD>1:Supplier ItemUnit</TD>	<TD>Work information.TC/DC flag = DC or Cross TC</TD></tr>
 * <TR><TD>01:Receiving </TD>	<TD>2:Supplier Unit</TD>		<TD>Work information.TC/DC flag = DC or Cross TC</TD></tr>
 * <TR><TD>01:Receiving </TD>	<TD>3:Customer Unit</TD>		<TD>Work information.TC/DC flag = TC</TD></tr>
 * <TR><TD>03:Picking </TD>	<TD>1:Order </TD>			<TD>Work information.Order No != Empty</TD></tr>
 * <TR><TD>03:Picking </TD>	<TD>2:Item</TD>			<TD>Work information.Order No = Empty</TD></tr>
 * <TR><TD>05:Shipping </TD>	<TD>1:ItemUnit</TD>			<TD>  </TD></tr>
 * <TR><TD>05:Shipping </TD>	<TD>2:Customer Unit</TD>		<TD>  </TD></tr>
 * </TABLE>
 * </P>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/14 06:24:36 $
 * @author  $Author: kamala $
 */
public class Id0011Operate extends IdOperate
{
	//#CM700868
	// Class fields----------------------------------------------------
	//#CM700869
	// Class variables -----------------------------------------------
	//#CM700870
	// Constructors --------------------------------------------------
	//#CM700871
	/**
	 * Generate the instance.
	 */
	public Id0011Operate()
	{
		super();
	}

	//#CM700872
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance.
	 * @param conn Database connection
	 */
	public Id0011Operate(Connection conn)
	{
		this();
		wConn = conn;
	}

	//#CM700873
	// Class method --------------------------------------------------
	//#CM700874
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2006/11/14 06:24:36 $";
	}

	//#CM700875
	// Public methods ------------------------------------------------
	//#CM700876
	/**
	 * Acquire Data which can work on the condition of specifying it from Work information(dnworkinfo). <BR>
	 * From the data whose Status flag is Stand by and Work start flag is Started,<BR>
	 * Acquire Data which can work on condition (Consignor Code,Work type,Detail Work type,Work plan date) of specifying it. <BR>
	 * <BR>
	 *   <DIR>
	 *   [Acquisition item]<BR>
	 *     <DIR>
	 *     Plan date<BR>
	 *     </DIR>
	 *   [Search condition]<BR>
	 *     <DIR>
	 *     Work flag      :Acquisition from parameter<BR>
	 *     Consignor Code    :Acquisition from parameter<BR>
	 *     Work plan date    :Acquisition from parameter<BR>
	 *     Status flag    :Thing done while Stand by or automatic terminal and self worker are working<BR>
	 *     Work start flag:Started<BR>
	 *     TC/DC flag     :DC or Cross TC (When work type is 01 and a work detailed type is only 1 or 2) <BR>
	 *     TC/DC flag     :TC (When work type is 01 and a work detailed type is only 3) <BR>
	 *     Order No.   :The one which is not empty (Only when work type is 03 and work detailed type is only 1) <BR>
	 *     Order No.   :The one which is empty (Only when work type is 03 and work detailed type is only 2) <BR>
	 *     </DIR>
	 *   </DIR>
	 * <DIR>
	 * </DIR>
	 * @param  consignorCode 	Consignor Code
	 * @param	workType		Work type
	 * @param	workDetails		Detail Work type
	 * @param  planDate      	Work plan date
	 * @param	rftNo			RFT Number
	 * @param	workerCode		Worker code
	 * @return		True when Data which can work exists,Otherwise, return false.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public boolean checkPlanDate(
		String consignorCode,
		String workType,
		String workDetails,
		String planDate,
		String rftNo,
		String workerCode)
		throws ReadWriteException
	{
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();

		//#CM700877
		//-----------------
		//#CM700878
		// Set Acquisition item
		//#CM700879
		//-----------------
		//#CM700880
		// Plan date
		workInfoSearchKey.setPlanDateCollect("DISTINCT");

		//#CM700881
		//-----------------
		//#CM700882
		// Set Search condition
		//#CM700883
		//-----------------
		//#CM700884
		// Work flag
		workInfoSearchKey.setJobType(workType);
		//#CM700885
		// Consignor Code
		if (! consignorCode.trim().equals(""))
		{
			workInfoSearchKey.setConsignorCode(consignorCode);
		}
		//#CM700886
		// Work plan date
		workInfoSearchKey.setPlanDate(planDate);
		//#CM700887
		// Status flag
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		workInfoSearchKey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		workInfoSearchKey.setWorkerCode(workerCode);
		workInfoSearchKey.setTerminalNo(rftNo, "=", "", "))", "AND");
		//#CM700888
		// Work start flag
		workInfoSearchKey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);

		if (workType.equals(WorkingInformation.JOB_TYPE_INSTOCK))
		{
			if (workDetails.equals(WorkDetails.INSTOCK_ITEM)
					|| workDetails.equals(WorkDetails.INSTOCK_SUPPLIER))
			{
				//#CM700889
				// Retrieve DC and crossing TC of the unit of the supplier or each item.
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC, "=", "(", "", "OR");
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_CROSSTC, "=", "", ")", "AND");
			}
			else if (workDetails.equals(WorkDetails.INSTOCK_CUSTOMER))
			{
				//#CM700890
				// Retrieve TC for each and every customer
				workInfoSearchKey.setTcdcFlag(WorkingInformation.TCDC_FLAG_TC);
			}
		}
		else if (workType.equals(WorkingInformation.JOB_TYPE_RETRIEVAL))
		{
			if (workDetails.equals(WorkDetails.RETRIEVAL_ORDER))
			{
				//#CM700891
				// Retrieve data where order No is set at the order picking.
				workInfoSearchKey.setOrderNo("", "IS NOT NULL");
			}
			else if (workDetails.equals(WorkDetails.RETRIEVAL_ITEM))
			{
				//#CM700892
				// Order No retrieves empty data at the item picking.
				workInfoSearchKey.setOrderNo("", "=");
			}
		}

		//#CM700893
		//-----------------
		//#CM700894
		// Retrieval processing
		//#CM700895
		//-----------------
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler(wConn);
		WorkingInformation[] workInfo =
			(WorkingInformation[]) workInfoHandler.find(workInfoSearchKey);

		if (workInfo == null || workInfo.length == 0)
		{
			//#CM700896
			// Return false when there is no corresponding Work information.
			return false;
		}

		return true;
	}

	//#CM700897
	// Package methods ----------------------------------------------
	//#CM700898
	// Protected methods --------------------------------------------
	//#CM700899
	// Private methods -----------------------------------------------
}
//#CM700900
//end of class
