package jp.co.daifuku.wms.base.crossdoc;

//#CM644568
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
import jp.co.daifuku.wms.base.dbhandler.InstockPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.InstockPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.entity.InstockPlan;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.StoragePlan;

//#CM644569
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Next Pending processing when previous work is completed(<CODE>selectDecider()</CODE>Method) <BR> 
 * <BR>
 * <DIR>
 *   Acquire previous plan information by Parameter, and judge whether Plan qty is Completed.  <BR>
 *   Return information which can do Pending Processing with the connected work when previous plan information is Completed. (In the Instance form) <BR>
 *   <BR>
 *   <Parameter > <BR><DIR>
 *     Connection <BR>
 *     Plan unique key : Plan information to have completed Processing <BR>
 *   </DIR>
 *   <Return information> <BR><DIR>
 *     In Pending information Instance form (Shortage).  <BR>
 *   </DIR>
 *   <BR>
 *   < Content of Processing> <BR><DIR>
 *     1.Acquire object information from next process information (DNNEXTPROC). <BR>
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
 *     2.Acquire each plan information (DNXXXXXPLAN) from next process information.  <BR>
 *     <Acquisition condition> <BR><DIR>
 *       2-1.Decide each schedule information from Work flag of next process information.  <BR>
 *       2-2.Plan unique key and agreement of Parameter.  <BR>
 *     </DIR>
 *     <BR>
 *     3.Next process information is assumed as Partial pending of Remaining work qty only when plan information is completed. <BR>
 *     <BR><DIR>
 *       3-1.Obtain Work plan qty-(Completed plan qty+Actual shortage qty) of next process information.  <BR>
 *       3-2.The requested remaining qty is set in the Pending allocation qty by next process information and above mentioned 3-1, and pending information Instance is made.  <BR>
 *     </DIR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/15</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:12:19 $
 * @author  $Author: suresh $
 */
public class PlanFinishShortageDecider extends Object
{
	//#CM644570
	//	Class variables -----------------------------------------------
	//#CM644571
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;
		
	//#CM644572
	/**
	 * Message maintenance Area<BR>
	 * Use it to maintain the content when condition Error etc. are generated while calling each Method. 
	 */
	private String wMessage;

	//#CM644573
	// Class method --------------------------------------------------
	//#CM644574
	/**
	 * Return Version of this Class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:12:19 $");
	}
	//#CM644575
	// Constructors --------------------------------------------------
	//#CM644576
	/**
	 * Initialize this Class. 
	 */
	public PlanFinishShortageDecider()
	{
		wMessage = null;
	}

	//#CM644577
	// Public methods ------------------------------------------------
	//#CM644578
	/**
	 * Processing which returns information which can do Pending Processing to the connected work when previous plan information is Completed. <BR>
	 * Acquire next process information which becomes an object with Plan unique key, and allot Shortage qty.  <BR>
	 * @param   pConn         Connection
	 * @param   pPlanUkey     Plan unique key whose work is completed
	 * @return  Shortage[]    Pending information Instance form
	 * @throws  ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 */
	protected Shortage[] selectDecider(Connection pConn, String pPlanUkey) throws ReadWriteException
	{
		//#CM644579
		// Next process information Instance
		NextProcessInfoHandler nextProcHandler = new NextProcessInfoHandler(pConn);
		NextProcessInfoSearchKey nextProcSearchKey = new NextProcessInfoSearchKey();

		Vector	vec = new Vector();
		
		//#CM644580
		// Define next process information Acquisition condition. 
		//#CM644581
		// Plan unique key and agreement of Parameter. 
		nextProcSearchKey.setPlanUkey(pPlanUkey);
		//#CM644582
		// The status flag is only of not Processing or the under Processing.
		String[] wStatus = {NextProcessInfo.STATUS_FLAG_UNSTART, NextProcessInfo.STATUS_FLAG_START};
		nextProcSearchKey.setStatusFlag(wStatus);
		
		//#CM644583
		// Define the acquisition order. 
		//#CM644584
		// Descending order of Shipping Plan unique key
		//#CM644585
		// Descending order of Sorting Plan unique key
		//#CM644586
		// Descending order of Picking Plan unique key
		//#CM644587
		// Descending order of Storage Plan unique key
		//#CM644588
		// Descending order of Receiving Plan unique key
		nextProcSearchKey.setShippingPlanUkeyOrder(1, false);
		nextProcSearchKey.setSortingPlanUkeyOrder(2, false);
		nextProcSearchKey.setRetrievalPlanUkeyOrder(3, false);
		nextProcSearchKey.setStoragePlanUkeyOrder(4, false);
		nextProcSearchKey.setInstockPlanUkeyOrder(5, false);

		NextProcessInfo[] rNextProc = (NextProcessInfo[])nextProcHandler.find(nextProcSearchKey);

		//#CM644589
		// Object information is not returned when next process information does not exist. 
		if (rNextProc.length <= 0)
		{
			return (new Shortage[0]);
		}

		//#CM644590
		// Status flag keeping Area of plan information
		String wReadStatus = "";
		
		//#CM644591
		// Acquire each plan information. 
		//#CM644592
		// Receiving 
		if (rNextProc[0].getWorkKind().equals(NextProcessInfo.JOB_TYPE_INSTOCK))
		{
			//#CM644593
			// Receiving Plan Information Instance
			InstockPlanHandler planHandler = new InstockPlanHandler(pConn);
			InstockPlanSearchKey planSearchKey = new InstockPlanSearchKey();
			
			//#CM644594
			// Retrieve it with a unique key to Parameter Processing. 
			planSearchKey.setInstockPlanUkey(pPlanUkey);
			
			InstockPlan[] wPlan = (InstockPlan[])planHandler.findForUpdate(planSearchKey);
			
			if (wPlan.length > 0 && wPlan[0].getStatusFlag() != null)
			{
				wReadStatus = wPlan[0].getStatusFlag();
			}
		}
		//#CM644595
		// Storage 
		else if (rNextProc[0].getWorkKind().equals(NextProcessInfo.JOB_TYPE_STORAGE))
		{
			//#CM644596
			// Storage Plan Information Instance
			StoragePlanHandler planHandler = new StoragePlanHandler(pConn);
			StoragePlanSearchKey planSearchKey = new StoragePlanSearchKey();
			
			//#CM644597
			// Retrieve it with a unique key to Parameter Processing. 
			planSearchKey.setStoragePlanUkey(pPlanUkey);
			
			StoragePlan[] wPlan = (StoragePlan[])planHandler.find(planSearchKey);
			
			if (wPlan.length > 0 && wPlan[0].getStatusFlag() != null)
			{
				wReadStatus = wPlan[0].getStatusFlag();
			}
		}
		//#CM644598
		// Picking 
		if (rNextProc[0].getWorkKind().equals(NextProcessInfo.JOB_TYPE_STORAGE))
		{
			//#CM644599
			// Picking Plan Information Instance
			RetrievalPlanHandler planHandler = new RetrievalPlanHandler(pConn);
			RetrievalPlanSearchKey planSearchKey = new RetrievalPlanSearchKey();
			
			//#CM644600
			// Retrieve it with a unique key to Parameter Processing. 
			planSearchKey.setRetrievalPlanUkey(pPlanUkey);
			
			RetrievalPlan[] wPlan = (RetrievalPlan[])planHandler.find(planSearchKey);
			
			if (wPlan.length > 0 && wPlan[0].getStatusFlag() != null)
			{
				wReadStatus = wPlan[0].getStatusFlag();
			}
		}
		//#CM644601
		// Sorting 
		if (rNextProc[0].getWorkKind().equals(NextProcessInfo.JOB_TYPE_SORTING))
		{
			//#CM644602
			// Sorting Plan Information Instance
			SortingPlanHandler planHandler = new SortingPlanHandler(pConn);
			SortingPlanSearchKey planSearchKey = new SortingPlanSearchKey();
			
			//#CM644603
			// Retrieve it with a unique key to Parameter Processing. 
			planSearchKey.setSortingPlanUkey(pPlanUkey);
			
			SortingPlan[] wPlan = (SortingPlan[])planHandler.find(planSearchKey);
			
			if (wPlan.length > 0 && wPlan[0].getStatusFlag() != null)
			{
				wReadStatus = wPlan[0].getStatusFlag();
			}
		}
		//#CM644604
		// Shipping 
		if (rNextProc[0].getWorkKind().equals(NextProcessInfo.JOB_TYPE_SHIPINSPECTION))
		{
			//#CM644605
			// Shipping Plan Information Instance
			ShippingPlanHandler planHandler = new ShippingPlanHandler(pConn);
			ShippingPlanSearchKey planSearchKey = new ShippingPlanSearchKey();
			
			//#CM644606
			// Retrieve it with a unique key to Parameter Processing. 
			planSearchKey.setShippingPlanUkey(pPlanUkey);
			
			ShippingPlan[] wPlan = (ShippingPlan[])planHandler.find(planSearchKey);
			
			if (wPlan.length > 0 && wPlan[0].getStatusFlag() != null)
			{
				wReadStatus = wPlan[0].getStatusFlag();
			}
		}

		//#CM644607
		// Object information is not returned, except when Status flag is Completed. 
		if (!wReadStatus.equals(NextProcessInfo.STATUS_FLAG_COMPLETION))
		{
			return (new Shortage[0]);
		}
		
		//#CM644608
		// Acquisition information Vector is edited. 
		for (int slc=0; slc<rNextProc.length; slc++)
		{			
			Shortage gShortage = new Shortage();
			
			//#CM644609
			// Unique key of next work information
			gShortage.setNextProcUkey(rNextProc[slc].getNextProcUkey());
			//#CM644610
			// Plan unique key
			gShortage.setPlanUkey(rNextProc[slc].getPlanUkey());
			//#CM644611
			// Receiving Plan unique key
			gShortage.setInstockPlanUkey(rNextProc[slc].getInstockPlanUkey());
			//#CM644612
			// Storage Plan unique key
			gShortage.setStoragePlanUkey(rNextProc[slc].getStoragePlanUkey());
			//#CM644613
			// Picking Plan unique key
			gShortage.setRetrievalPlanUkey(rNextProc[slc].getRetrievalPlanUkey());
			//#CM644614
			// Sorting Plan unique key
			gShortage.setSortingPlanUkey(rNextProc[slc].getSortingPlanUkey());
			//#CM644615
			// Shipping Plan unique key
			gShortage.setShippingPlanUkey(rNextProc[slc].getShippingPlanUkey());
			//#CM644616
			// Work plan qty
			gShortage.setPlanQty(rNextProc[slc].getPlanQty());
			//#CM644617
			// Work actual qty
			gShortage.setResultQty(rNextProc[slc].getResultQty());
			//#CM644618
			// Actual shortage qty
			gShortage.setShortageQty(rNextProc[slc].getShortageQty());
			//#CM644619
			// Pending allocation qty
			int wkQty = rNextProc[slc].getPlanQty() - (rNextProc[slc].getResultQty() + rNextProc[slc].getShortageQty());
			//#CM644620
			// When Processing of the partial plan qty is completed, it is judged it is off the subject. 
			if (wkQty <= 0)	continue;
			gShortage.setInsufficientQty(wkQty);

			//#CM644621
			// Keep it in the VECTOR area. 
			vec.addElement(gShortage);
		}
		
		Shortage[] wShortage = new Shortage[vec.size()];
		vec.copyInto(wShortage);

		return (Shortage[])wShortage;
				
	}
	
	//#CM644622
	// Private methods -----------------------------------------------
	
}
//#CM644623
//end of class
