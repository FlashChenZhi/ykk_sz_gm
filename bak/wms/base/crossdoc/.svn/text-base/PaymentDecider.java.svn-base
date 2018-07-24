package jp.co.daifuku.wms.base.crossdoc;

//#CM644383
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoSearchKey;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;

//#CM644384
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * next settlement processing of partial delivery (Simple allotment)(<CODE>simpleDecider()</CODE>Method) <BR> 
 * <BR>
 * <DIR>
 *   Return the allocation qty of information Processing of partial delivery which delivers partially by Parameter. (In the Instance form) <BR>
 *   <BR>
 *   <Parameter > <BR><DIR>
 *     Connection <BR>
 *     Plan unique key : Plan information to have completed Processing <BR>
 *     Total completed qty <BR>
 *   </DIR>
 *   <Return information> <BR><DIR>
 *     In Instance of partial delivery information form (Payment).  <BR>
 *   </DIR>
 *   <BR>
 *   < Content of Processing> <BR><DIR>
 *     1.Acquire object information from next process information (DNNEXTPROC). <BR>
 *     2.Allot Total completed qty of Parameter to each next process information. (Simple allotment) <BR>
 *     <BR>
 *     <Acquisition condition> <BR><DIR>
 *       1-1.Plan unique key and agreement of Parameter.  <BR>
 *       1-2.The status flag is only of not Processing or the under Processing. <BR>
 *       (X)Read by exclusive specification Processing. <BR>
 *     </DIR>
 *     <The acquisition order> <BR><DIR>
 *        - Ascending order of Receiving Plan unique key<BR>
 *        - Ascending order of Storage Plan unique key<BR>
 *        - Ascending order of Picking Plan unique key<BR>
 *        - Ascending order of Sorting Plan unique key<BR>
 *        - Ascending order of Shipping Plan unique key<BR>
 *     </DIR>
 *     <BR>
 *     2-1.Obtain Work plan qty-(Completed plan qty+Actual shortage qty) of next process information.  <BR>
 *     2-2.Obtain Completed allocated qty from the completed qty (remaining qty) and the above mentioned and the amount. <BR>
 *     2-3.Make Instance of partial delivery information by next process information and the amount of Completed allocated qty.  <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/2</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:13:16 $
 * @author  $Author: suresh $
 */
public class PaymentDecider extends Object
{
	//#CM644385
	//	Class variables -----------------------------------------------
	//#CM644386
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;
		
	//#CM644387
	/**
	 * Message maintenance Area<BR>
	 * Use it to maintain the content when condition Error etc. are generated while calling each Method. 
	 */
	private String wMessage;

	//#CM644388
	// Class method --------------------------------------------------
	//#CM644389
	/**
	 * Return Version of this Class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:13:16 $");
	}
	//#CM644390
	// Constructors --------------------------------------------------
	//#CM644391
	/**
	 * Initialize this Class. 
	 */
	public PaymentDecider()
	{
		wMessage = null;
	}

	//#CM644392
	// Public methods ------------------------------------------------
	//#CM644393
	/**
	 * Processing the allotment of previous Completed plan qty (Simple allotment).  <BR>
	 * Acquire next process information which becomes an object with Plan unique key, and allot the completed qty. <BR>
	 * @param   pConn         Connection
	 * @param   pPlanUkey     Plan unique key whose work is completed
	 * @param   pCompleteQty  Completed plan qty
	 * @return  Payment[]     Instance of partial delivery information form
	 * @throws  ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 */
	protected Payment[] simpleDecider(Connection pConn, String pPlanUkey, int pCompleteQty) throws ReadWriteException
	{
		//#CM644394
		// Next process information Instance
		NextProcessInfoHandler nextProcHandler = new NextProcessInfoHandler(pConn);
		NextProcessInfoSearchKey nextProcSearchKey = new NextProcessInfoSearchKey();

		Vector	vec = new Vector();

		//#CM644395
		// Keep the completed qty. 
		int wCompQty = pCompleteQty;
		
		//#CM644396
		// Define next process information Acquisition condition. 
		//#CM644397
		// Plan unique key and agreement of Parameter. 
		nextProcSearchKey.setPlanUkey(pPlanUkey);
		//#CM644398
		// The status flag is only of not Processing or the under Processing.
		String[] wStatus = {NextProcessInfo.STATUS_FLAG_UNSTART, NextProcessInfo.STATUS_FLAG_START};
		nextProcSearchKey.setStatusFlag(wStatus);
		
		//#CM644399
		// Define the acquisition order. 
		//#CM644400
		// Ascending order of Receiving Plan unique key
		//#CM644401
		// Ascending order of Storage Plan unique key
		//#CM644402
		// Ascending order of Picking Plan unique key
		//#CM644403
		// Ascending order of Sorting Plan unique key
		//#CM644404
		// Ascending order of Shipping Plan unique key
		nextProcSearchKey.setInstockPlanUkeyOrder(1, true);
		nextProcSearchKey.setStoragePlanUkeyOrder(2, true);
		nextProcSearchKey.setRetrievalPlanUkeyOrder(3, true);
		nextProcSearchKey.setSortingPlanUkeyOrder(4, true);
		nextProcSearchKey.setShippingPlanUkeyOrder(5, true);

		NextProcessInfo[] rNextProc = (NextProcessInfo[])nextProcHandler.findForUpdate(nextProcSearchKey);
				
		//#CM644405
		// Acquisition information Vector is edited. 
		for (int slc=0; slc<rNextProc.length; slc++)
		{
			//#CM644406
			// End Processing when the allotment is completed partially. 
			if (wCompQty <= 0)		break ;
			
			Payment gPayment = new Payment();
			
			//#CM644407
			// Unique key of next work information
			gPayment.setNextProcUkey(rNextProc[slc].getNextProcUkey());
			//#CM644408
			// Plan unique key
			gPayment.setPlanUkey(rNextProc[slc].getPlanUkey());
			//#CM644409
			// Receiving Plan unique key
			gPayment.setInstockPlanUkey(rNextProc[slc].getInstockPlanUkey());
			//#CM644410
			// Storage Plan unique key
			gPayment.setStoragePlanUkey(rNextProc[slc].getStoragePlanUkey());
			//#CM644411
			// Picking Plan unique key
			gPayment.setRetrievalPlanUkey(rNextProc[slc].getRetrievalPlanUkey());
			//#CM644412
			// Sorting Plan unique key
			gPayment.setSortingPlanUkey(rNextProc[slc].getSortingPlanUkey());
			//#CM644413
			// Shipping Plan unique key
			gPayment.setShippingPlanUkey(rNextProc[slc].getShippingPlanUkey());
			//#CM644414
			// Work plan qty
			gPayment.setPlanQty(rNextProc[slc].getPlanQty());
			//#CM644415
			// Work actual qty
			gPayment.setResultQty(rNextProc[slc].getResultQty());
			//#CM644416
			// Actual shortage qty
			gPayment.setShortageQty(rNextProc[slc].getShortageQty());
			//#CM644417
			// Pending allocation qty
			int wkQty = rNextProc[slc].getPlanQty() - (rNextProc[slc].getResultQty() + rNextProc[slc].getShortageQty());
			//#CM644418
			// When Processing of the partial plan qty is completed, it is judged it is off the subject. 
			if (wkQty <= 0)	continue;
			
			if (wkQty >= wCompQty)
			{
				gPayment.setCompleteQty(wCompQty);
				wCompQty = 0;
			}
			else
			{
				gPayment.setCompleteQty(wkQty);
				wCompQty -= wkQty;
			}

			//#CM644419
			// Keep it in the VECTOR area. 
			vec.addElement(gPayment);
		}
		
		Payment[] wPayment = new Payment[vec.size()];
		vec.copyInto(wPayment);

		return (Payment[])wPayment;
				
	}
	
	//#CM644420
	// Private methods -----------------------------------------------
	
}
//#CM644421
//end of class
