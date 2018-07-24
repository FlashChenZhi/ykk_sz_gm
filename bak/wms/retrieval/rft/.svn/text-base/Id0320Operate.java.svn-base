// $Id: Id0320Operate.java,v 1.3 2007/02/07 04:19:38 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

//#CM720359
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
import jp.co.daifuku.wms.base.rft.IdOperate;

//#CM720360
/**
 * Designer : T.Konishi <BR>
 * Maker :   <BR>
 * <BR>
 * Allow this class to execute the process for Order list (ID0320).<BR>
 * Inherit <CODE>IdOperate</CODE> class and implement the required processes.<BR>
 * Receive the Planned date, Consignor, and Work Type as a parameter, and 
 * obtain the Order list information from the Work Status.<BR>
 * <BR>
 * 
 * Obtain Order List (<CODE>getOrderList()</CODE>method)<BR>
 * <DIR>
 *   Receive the Planned date, Consignor, and Work Type as a Parameter and return the Order List info.<BR>
 * </DIR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:38 $
 * @author  $Author: suresh $
 */
/**
 * Designer : T.Konishi <BR>
 * Maker :   <BR>
 * <BR>
 * Allow this class to execute the process for Order list (ID0320).<BR>
 * Inherit <CODE>IdOperate</CODE> class and implement the required processes.<BR>
 * Receive the Planned date, Consignor, and Work Type as a parameter, and 
 * obtain the Order list information from the Work Status.<BR>
 * <BR>
 * 
 * Obtain Order List (<CODE>getOrderList()</CODE>method)<BR>
 * <DIR>
 *   Receive the Planned date, Consignor, and Work Type as a Parameter and return the Order List info.<BR>
 * </DIR>
 * 
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004//</TD><TD>K.Shimizu</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:38 $
 * @author  $Author: suresh $
 */
public class Id0320Operate extends IdOperate
{
	//#CM720361
	// Constructors --------------------------------------------------
	//#CM720362
	/**
	 * Generate an instance.
	 */
	public Id0320Operate()
	{
	}

	//#CM720363
	/**
	 * Designate <code>Connection</code> for database connection and generate the instance.
	 * @param conn Connection for database connection 
	 */
	public Id0320Operate(Connection conn)
	{
		wConn = conn;
	}

	//#CM720364
	// Public methods ------------------------------------------------
	//#CM720365
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2007/02/07 04:19:38 $";
	}

	//#CM720366
	/**
	 * Obtain the Order list from the Work Status (dnworkinfo) using the designated condition.<BR>
	 * From the work data with Work division "Picking", Status flag "Standby", and Start Work flag "Started",<BR>
	 * Using the designated condition (Planned Work Date, Consignor, and Work Type), obtain the data list of Order No., Consignor Code, and Consignor Name<BR>
	 * in the ascending order of the Order No.<BR>
	 * Exclude double entries.<BR>
	 * <BR>
	 *     <DIR>
	 *      [Item (count) to be obtained] <BR>
	 *         <DIR>
	 *         Order No.<BR>
	 *         Customer Code<BR>
	 *         Customer Name<BR>
	 *         </DIR>
	 *      [search conditions] <BR>
	 *         <DIR>
	 *         Planned Work Date    :Obtain from the Parameter.<BR>
	 *         Consignor Code    :Obtain from the Parameter.<BR>
	 *         Work Type      :Obtain from the Parameter.<BR>
	 *         Work division      :Picking<BR>
	 *         Status flag    :Standby<BR>
	 *         Start Work flag:Flag updated by its own terminal if it is "Started" or "Processing".<BR>
	 *         Order No.    : other than blank<BR>
	 *         </DIR>
	 *      [Aggregation Conditions] <BR>
	 *         <DIR>
	 *         Order No.<BR>
	 *         Customer Code<BR>
	 *         Customer Name<BR>
	 *         </DIR>
	 *      [Sorting order] <BR>
	 *         <DIR>
	 *         Order No.<BR>
	 *         Customer Code<BR>
	 *         </DIR>
	 *     </DIR>
	 * @param  planDate			Planned Work Date
	 * @param  consignorCode	Consignor Code
	 * @param  workForm			Work Type (Case, Piece, or All)
	 * @param	workerCode		Worker Code
	 * @param	rftNo			RFT No.
	 * @return Order list information
	 * @throws NotFoundException  Announce when Order info is not found.
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	public WorkingInformation[] getOrderList(
	        String planDate,
	        String consignorCode,
	        String workForm,
	        String workerCode,
	        String rftNo)
		throws ReadWriteException, NotFoundException
	{
		//#CM720367
		// Maintain the obtained Consignor info.
		WorkingInformation[] workinfo = null;
		
		WorkingInformationSearchKey skey = new WorkingInformationSearchKey();
		skey.setPlanDate(planDate);
		skey.setConsignorCode(consignorCode);
		if (!workForm.equals(RFTId0320.CASE_PIECE_All))
		{
			skey.setWorkFormFlag(workForm);
		}
		skey.setJobType(WorkingInformation.JOB_TYPE_RETRIEVAL);
		skey.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
		//#CM720368
		// Target the data with Status flag "Standby" or "Processing" (only for data with the same Worker Code and the same RFTNo.) 
		//#CM720369
		//  SQL statement ---  (DNWORKINFO.STATUS_FLAG = '0' or (DNWORKINFO.STATUS_FLAG = '2' AND DNWORKINFO.WORKER_CODE = 'workerCode' AND DNWORKINFO.TERMINAL_NO = 'rftNo' )) AND
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART, "=", "(", "", "OR");
		skey.setStatusFlag(WorkingInformation.STATUS_FLAG_NOWWORKING, "=", "(", "", "AND");
		skey.setWorkerCode(workerCode);
		skey.setTerminalNo(rftNo, "=", "", "))", "AND");
		skey.setOrderNo("", "IS NOT NULL");

		//#CM720370
		//-----------------
		//#CM720371
		// Set Aggregation condition.
		//#CM720372
		//-----------------
		skey.setOrderNoGroup(1);
		skey.setCustomerCodeGroup(2);
		skey.setCustomerName1Group(3);
		skey.setOrderNoCollect("");
		skey.setCustomerCodeCollect("");
		skey.setCustomerName1Collect("");
		
		//#CM720373
		//-----------------
		//#CM720374
		// Set Sort Order
		//#CM720375
		//-----------------
		skey.setOrderNoOrder(1, true);
		skey.setCustomerCodeOrder(2, true);
		skey.setCustomerName1Order(3, true);
		
		WorkingInformationHandler handler = new WorkingInformationHandler(wConn);
		workinfo = (WorkingInformation[])handler.find(skey);
		
		if (workinfo != null && workinfo.length == 0)
		{
		    //#CM720376
		    // No corresponding data
		    throw new NotFoundException();
		}
		return workinfo;
	}

	//#CM720377
	// Private methods -----------------------------------------------
}
//#CM720378
//end of class
