package jp.co.daifuku.wms.base.crossdoc;

//#CM644318
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.base.entity.SystemDefine;

//#CM644319
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * CrossDoc Processing(<CODE>complete()</CODE>Method) <BR> 
 * Do not do Commit or Rollback of Transaction though each Method of this Class must update Connection Object <BR>
 * and update receipt Database Processing. <BR>
 * Return true when Processing is normally completed and return false when it does not complete Schedule on condition Error etc. <BR>
 * The content of Error can be refered by using <CODE>getMessage() </CODE> Method. <BR>
 * Do following Processing in this Class. <BR>
 * <BR>
 * <DIR>
 *   Method which connects update Processing of CrossDoc according to the parameter.  <BR>
 *   <BR>
 *   <Parameter > <BR><DIR>
 *     Connection <BR>
 *     Plan unique key : Plan information to have completed Processing <BR>
 *     Total completed qty <BR>
 *     Total pending qty <BR>
 *   </DIR>
 *   <Return information> <BR><DIR>
 *     true :  When Processing completed normally.<BR>
 *     false: When Processing terminated abnormally.  <BR>
 *   </DIR>
 *   <BR>
 *   < Content of Processing> <BR><DIR>
 *     1.Start pending settlement Processing (ShortageDestDecider). <BR>
 *     2.Start Pending update Processing (ShortageOperator) when as much as one Pending information exists.<BR>
 *     3.Start partial pending settlement Processing (PaymentDestDecider).<BR>
 *     4.Start update Processing (PaymentOperator) of paying in installments when as much as one information of partial pending exists. <BR>
 *     <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/2</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:14:07 $
 * @author  $Author: suresh $
 */
public class CrossDocOperator extends Object
{
	//#CM644320
	//	Class variables -----------------------------------------------
	//#CM644321
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;
		
	//#CM644322
	/**
	 * Message maintenance Area<BR>
	 * Use it to maintain the content when condition Error etc. are generated while calling each Method. 
	 */
	private String wMessage;

	//#CM644323
	// Class method --------------------------------------------------
	//#CM644324
	/**
	 * Return Version of this Class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:14:07 $");
	}
	//#CM644325
	// Constructors --------------------------------------------------
	//#CM644326
	/**
	 * Initialize this Class. 
	 */
	public CrossDocOperator()
	{
		wMessage = null;
	}

	//#CM644327
	// Public methods ------------------------------------------------
	//#CM644328
	/**
	 * Update connects each package information by the content of the parameter Processing.  <BR>
	 * Connect information which generates Pending Processing. <BR>
	 * Process the Connection information whose former process is completed.<BR>
	 * @param   pConn         Connection
	 * @param   pPlanUkey     Plan unique key whose work is completed
	 * @param   pCompleteQty  Completed plan qty
	 * @param   pShortageQty  Pending qty
	 * @return True in case of Schedule Processing is normal,False when Schedule Processing fail or Schedule cannot be done
	 * @throws  ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 * @throws  ScheduleException It is notified when the exception not anticipated in check Processing is generated. 
	 */
	public boolean complete(Connection pConn, String pPlanUkey, int pCompleteQty, int pShortageQty) throws ReadWriteException, ScheduleException
	{
		boolean rStatus = false;

		try
		{
			//#CM644329
			// Start next submit of pending update Processing when the pending qty is one or more. 
			if (pShortageQty > 0)
			{
				//#CM644330
				// Start next submit Processing of Pending by the content of Parameter. 
				ShortageDestDecider shortDecider = new ShortageDestDecider();
				Shortage[] rShort = shortDecider.simpleDecider(pConn, pPlanUkey, pShortageQty);
				
				//#CM644331
				// Start Pending update Processing only when Pending information exists. 
				if (rShort != null && rShort.length > 0)
				{
					for (int lc = 0; lc < rShort.length; lc++)
					{
						//#CM644332
						// When schedule information is not connected, following Processing also starts update Processing as unconnected.
						if (rShort[lc].getInstockPlanUkey() != null	&&
							rShort[lc].getInstockPlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
						{
							rShort[lc].setStoragePlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rShort[lc].setRetrievalPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rShort[lc].setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rShort[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						}
						else if (rShort[lc].getStoragePlanUkey() != null	&&
							rShort[lc].getStoragePlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
						{
							rShort[lc].setRetrievalPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rShort[lc].setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rShort[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						}
						else if (rShort[lc].getRetrievalPlanUkey() != null	&&
							rShort[lc].getRetrievalPlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
						{
							rShort[lc].setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rShort[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						}
						else if (rShort[lc].getSortingPlanUkey() != null	&&
							rShort[lc].getSortingPlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
						{
							rShort[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						}
						
						ShortageOperator mShortOpe = new ShortageOperator(pConn);
						rStatus = mShortOpe.UpdateShortage(rShort[lc]);
						if (rStatus == false)			return rStatus;
					}
				}
			}
			
			//#CM644333
			// Start update of the next submit of partial delivery reaching partial delivery processing when the completed qty is one or more. 
			if (pCompleteQty > 0)
			{
				//#CM644334
				// Start next submit Processing of partial delivery by the content of Parameter. 
				PaymentDecider paymentDecider = new PaymentDecider();
				Payment[] rPayment = paymentDecider.simpleDecider(pConn, pPlanUkey, pCompleteQty);
				
				//#CM644335
				// Start update Processing of partial delivery only when information of partial delivery exists. 
				if (rPayment != null && rPayment.length > 0)
				{
					for (int lc = 0; lc < rPayment.length; lc++)
					{
						//#CM644336
						// When schedule information is not connected, following Processing also starts update Processing as unconnected.
						if (rPayment[lc].getInstockPlanUkey() != null	&&
							rPayment[lc].getInstockPlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
						{
							rPayment[lc].setStoragePlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rPayment[lc].setRetrievalPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rPayment[lc].setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rPayment[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						}
						else if (rPayment[lc].getStoragePlanUkey() != null	&&
							rPayment[lc].getStoragePlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
						{
							rPayment[lc].setRetrievalPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rPayment[lc].setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rPayment[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						}
						else if (rPayment[lc].getRetrievalPlanUkey() != null	&&
							rPayment[lc].getRetrievalPlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
						{
							rPayment[lc].setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
							rPayment[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						}
						else if (rPayment[lc].getSortingPlanUkey() != null	&&
							rPayment[lc].getSortingPlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
						{
							rPayment[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						}
						
						PaymentOperator mPaymentOpe = new PaymentOperator(pConn);
						rStatus = mPaymentOpe.UpdatePayment(rPayment[lc]);
						if (rStatus == false)			return rStatus;
					}
				}
			}
			
			//#CM644337
			// Start checking that the schedule information is completed and pending qty decision Processing to the next process. 
			PlanFinishShortageDecider planFinishDecider = new PlanFinishShortageDecider();
			Shortage[] sShort = planFinishDecider.selectDecider(pConn, pPlanUkey);
			//#CM644338
			// Start Pending update Processing only when Pending information exists. 
			if (sShort != null && sShort.length > 0)
			{
				for (int lc = 0; lc < sShort.length; lc++)
				{
					//#CM644339
					// When schedule information is not connected, following Processing also starts update Processing as unconnected.
					if (sShort[lc].getInstockPlanUkey() != null	&&
						sShort[lc].getInstockPlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
					{
						sShort[lc].setStoragePlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						sShort[lc].setRetrievalPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						sShort[lc].setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						sShort[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					}
					else if (sShort[lc].getStoragePlanUkey() != null	&&
						sShort[lc].getStoragePlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
					{
						sShort[lc].setRetrievalPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						sShort[lc].setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						sShort[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					}
					else if (sShort[lc].getRetrievalPlanUkey() != null	&&
						sShort[lc].getRetrievalPlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
					{
						sShort[lc].setSortingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
						sShort[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					}
					else if (sShort[lc].getSortingPlanUkey() != null	&&
						sShort[lc].getSortingPlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
					{
						sShort[lc].setShippingPlanUkey(SystemDefine.PLAN_UKEY_DUMMY);
					}
						
					ShortageOperator mShortOpe = new ShortageOperator(pConn);
					rStatus = mShortOpe.UpdateShortage(sShort[lc]);
					if (rStatus == false)			return rStatus;
				}
			}
			
			return true;
		}
		catch (ScheduleException e)
		{
			throw new ScheduleException(e.getMessage());
		}

	}
	
	//#CM644340
	// Private methods -----------------------------------------------
	
}
//#CM644341
//end of class
