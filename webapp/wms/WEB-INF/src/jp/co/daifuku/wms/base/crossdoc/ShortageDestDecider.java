package jp.co.daifuku.wms.base.crossdoc;

//#CM644665
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

//#CM644666
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Next settlement fixed Processing of Pending(Simple allotment)(<CODE>simpleDecider()</CODE>Method) <BR> 
 * <BR>
 * <DIR>
 *   Return the Pending allocation qty information which Processes pending by Parameter. (In the Instance form) <BR>
 *   <BR>
 *   <Parameter > <BR><DIR>
 *     Connection <BR>
 *     Plan unique key : Plan information to have completed Processing <BR>
 *     Total pending qty <BR>
 *   </DIR>
 *   <Return information> <BR><DIR>
 *     In Pending information Instance form (Shortage).  <BR>
 *   </DIR>
 *   <BR>
 *   < Content of Processing> <BR><DIR>
 *     1.Acquire object information from next process information (DNNEXTPROC). <BR>
 *     2.Allot Shortage qty of Parameter to next each process information. (Simple allotment) <BR>
 *     <BR>
 *     <Acquisition condition> <BR><DIR>
 *       1-1.Plan unique key and agreement of Parameter.  <BR>
 *       1-2.The status flag is only of not Processing or the under Processing. <BR>
 *       (X)Read by exclusive specification Processing. <BR>
 *     </DIR>
 *     <The acquisition order> <BR><DIR>
 *        - Descending order of Shipping Plan unique key<BR>
 *        - Descending order of Sorting Plan unique key<BR>
 *        - Descending order of Picking Plan unique key<BR>
 *        - Descending order of Storage Plan unique key<BR>
 *        - Descending order of Receiving Plan unique key<BR>
 *     </DIR>
 *     <BR>
 *     2-1.Obtain Work plan qty-(Completed plan qty+Actual shortage qty) of next process information.  <BR>
 *     2-2.Obtain Pending allocation qty from Shortage qty (number of the remainder) and the above mentioned and the amount. <BR>
 *     2-3.Make Pending information Instance by next process information and the amount of Pending allocation qty. <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/2</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:11:37 $
 * @author  $Author: suresh $
 */
public class ShortageDestDecider extends Object
{
	//#CM644667
	//	Class variables -----------------------------------------------
	//#CM644668
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;
		
	//#CM644669
	/**
	 * Message maintenance Area<BR>
	 * Use it to maintain the content when condition Error etc. are generated while calling each Method. 
	 */
	private String wMessage;

	//#CM644670
	// Class method --------------------------------------------------
	//#CM644671
	/**
	 * Return Version of this Class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:11:37 $");
	}
	//#CM644672
	// Constructors --------------------------------------------------
	//#CM644673
	/**
	 * Initialize this Class. 
	 */
	public ShortageDestDecider()
	{
		wMessage = null;
	}

	//#CM644674
	// Public methods ------------------------------------------------
	//#CM644675
	/**
	 * Processing which is the allotment of Shortage qty (Simple allotment). <BR>
	 * Acquire next process information which becomes an object with Plan unique key, and allot Shortage qty.  <BR>
	 * @param   pConn         Connection
	 * @param   pPlanUkey     Plan unique key whose work is completed
	 * @param   pShortageQty  Pending qty
	 * @return  Shortage[]    Pending information Instance form
	 * @throws  ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 */
	protected Shortage[] simpleDecider(Connection pConn, String pPlanUkey, int pShortageQty) throws ReadWriteException
	{
		//#CM644676
		// Next process information Instance
		NextProcessInfoHandler nextProcHandler = new NextProcessInfoHandler(pConn);
		NextProcessInfoSearchKey nextProcSearchKey = new NextProcessInfoSearchKey();

		Vector	vec = new Vector();

		//#CM644677
		// Keep the amount of Shortage qty. 
		int wShortQty = pShortageQty;
		
		//#CM644678
		// Define next process information Acquisition condition. 
		//#CM644679
		// Plan unique key and agreement of Parameter. 
		nextProcSearchKey.setPlanUkey(pPlanUkey);
		//#CM644680
		// The status flag is only of not Processing or the under Processing.
		String[] wStatus = {NextProcessInfo.STATUS_FLAG_UNSTART, NextProcessInfo.STATUS_FLAG_START};
		nextProcSearchKey.setStatusFlag(wStatus);
		
		//#CM644681
		// Define the acquisition order. 
		//#CM644682
		// Descending order of Shipping Plan unique key
		//#CM644683
		// Descending order of Sorting Plan unique key
		//#CM644684
		// Descending order of Picking Plan unique key
		//#CM644685
		// Descending order of Storage Plan unique key
		//#CM644686
		// Descending order of Receiving Plan unique key
		nextProcSearchKey.setShippingPlanUkeyOrder(1, false);
		nextProcSearchKey.setSortingPlanUkeyOrder(2, false);
		nextProcSearchKey.setRetrievalPlanUkeyOrder(3, false);
		nextProcSearchKey.setStoragePlanUkeyOrder(4, false);
		nextProcSearchKey.setInstockPlanUkeyOrder(5, false);

		NextProcessInfo[] rNextProc = (NextProcessInfo[])nextProcHandler.findForUpdate(nextProcSearchKey);
				
		//#CM644687
		// Acquisition information Vector is edited. 
		for (int slc=0; slc<rNextProc.length; slc++)
		{
			//#CM644688
			// End Processing at allotment Completed for partial Shortage qty. 
			if (wShortQty <= 0)		break ;
			
			Shortage gShortage = new Shortage();
			
			//#CM644689
			// Unique key of next work information
			gShortage.setNextProcUkey(rNextProc[slc].getNextProcUkey());
			//#CM644690
			// Plan unique key
			gShortage.setPlanUkey(rNextProc[slc].getPlanUkey());
			//#CM644691
			// Receiving Plan unique key
			gShortage.setInstockPlanUkey(rNextProc[slc].getInstockPlanUkey());
			//#CM644692
			// Storage Plan unique key
			gShortage.setStoragePlanUkey(rNextProc[slc].getStoragePlanUkey());
			//#CM644693
			// Picking Plan unique key
			gShortage.setRetrievalPlanUkey(rNextProc[slc].getRetrievalPlanUkey());
			//#CM644694
			// Sorting Plan unique key
			gShortage.setSortingPlanUkey(rNextProc[slc].getSortingPlanUkey());
			//#CM644695
			// Shipping Plan unique key
			gShortage.setShippingPlanUkey(rNextProc[slc].getShippingPlanUkey());
			//#CM644696
			// Work plan qty
			gShortage.setPlanQty(rNextProc[slc].getPlanQty());
			//#CM644697
			// Work actual qty
			gShortage.setResultQty(rNextProc[slc].getResultQty());
			//#CM644698
			// Actual shortage qty
			gShortage.setShortageQty(rNextProc[slc].getShortageQty());
			//#CM644699
			// Pending allocation qty
			int wkQty = rNextProc[slc].getPlanQty() - (rNextProc[slc].getResultQty() + rNextProc[slc].getShortageQty());
			//#CM644700
			// When Processing of the partial plan qty is completed, it is judged it is off the subject. 
			if (wkQty <= 0)	continue;
			
			if (wkQty >= wShortQty)
			{
				gShortage.setInsufficientQty(wShortQty);
				wShortQty = 0;
			}
			else
			{
				gShortage.setInsufficientQty(wkQty);
				wShortQty -= wkQty;
			}

			//#CM644701
			// Keep it in the VECTOR area. 
			vec.addElement(gShortage);
		}
		
		Shortage[] wShortage = new Shortage[vec.size()];
		vec.copyInto(wShortage);

		return (Shortage[])wShortage;
				
	}
	
	//#CM644702
	// Private methods -----------------------------------------------
	
}
//#CM644703
//end of class
