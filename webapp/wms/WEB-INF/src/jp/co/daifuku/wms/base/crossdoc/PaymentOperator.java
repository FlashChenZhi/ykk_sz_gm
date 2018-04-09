package jp.co.daifuku.wms.base.crossdoc;

//#CM644422
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM644423
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * Update each file based on Partial delivery information Instance. <BR>
 * <BR>
 * 1.Work information acquisition order definition Processing(<CODE>selectWorkOrder()</CODE>Method) <BR> 
 * <BR>
 * <DIR>
 *   Return Acquisition condition of work information (Dnworkinfo) to do Partial delivery Processing <BR>
 *   and acquisition order by Parameter.  <BR>
 *   Processing is divided by the work flag. <BR>
 *   <Parameter > <BR>
 *   <DIR>
 *     Instance of Partial delivery information(Payment) <BR>
 *     Work flag <BR>
 *   </DIR>
 *   <Return information> <BR>
 *   <DIR>
 *     Especially, none
 *   </DIR>
 *   <Acquisition condition> <BR>
 *   <DIR>
 *     1-1.When Work flag of Parameter is [Storage] <BR>
 *       <DIR>
 *        - Corresponding Plan unique key of work information file (Dnworkinfo) with Storage Plan unique key of Partial delivery information Instance(Payment). <BR>
 *        - The possible work information qty is excluding 0.  <BR>
 *       </DIR>
 *     1-2.When Work flag of Parameter is [Receiving] <BR>
 *       <DIR>
 *        - Corresponding Plan unique key of work information file (Dnworkinfo) with Receiving Plan unique key of Partial delivery information Instance(Payment). <BR>
 *        - The possible work information qty is excluding 0.  <BR>
 *       </DIR>
 *     1-3.When Work flag of Parameter is [Picking] <BR>
 *       <DIR>
 *        - Corresponding Plan unique key of work information file (Dnworkinfo) with Picking Plan unique key of Partial delivery information Instance(Payment). <BR>
 *        - The possible work information qty is excluding 0.  <BR>
 *       </DIR>
 *     1-4.When Work flag of Parameter is [Sorting] <BR>
 *       <DIR>
 *        - Corresponding Plan unique key of work information file (Dnworkinfo) with Sorting Plan unique key of Partial delivery information Instance(Payment). <BR>
 *        - The possible work information qty is excluding 0.  <BR>
 *       </DIR>
 *     1-5.When Work flag of Parameter is [Shipping] <BR>
 *       <DIR>
 *        - Corresponding Plan unique key of work information file (Dnworkinfo) with Shipping Plan unique key of Partial delivery information Instance(Payment). <BR>
 *        - The possible work information qty is excluding 0.  <BR>
 *       </DIR> 
 *     <The order of reading> <BR>
 *       The Plan unique key which corresponds respectively as follows. <BR>
 *       <DIR>
 *         - Ascending order of Receiving Plan unique key<BR>
 *         - Ascending order of Storage Plan unique key<BR>
 *         - Ascending order of Picking Plan unique key<BR>
 *         - Ascending order of Sorting Plan unique key<BR>
 *         - Ascending order of Shipping Plan unique key<BR>
 *       </DIR>
 *   </DIR>
 *   <The retrieval order> <BR>
 *   <DIR>
 *    - Ascending order on registration date <BR>
 *   </DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 2.Partial delivery update Processing(<CODE>UpdatePayment()</CODE>Method) <BR> 
 * <BR>
 * <DIR>
 *   Return acquisition information to do Partial delivery update Processing by Parameter.  <BR>
 *   <Parameter > <BR>
 *   <DIR>
 *     Instance of Partial delivery information(Payment) <BR>
 *   </DIR>
 *   <Return information> <BR>
 *   <DIR>
 *    Return True at the normal termination and False at abnormality Error. 
 *   </DIR>
 *   <Processing details> <BR>
 *   <DIR>
 *     1.Start check <BR>
 *     <DIR>
 *       - Do not do following Processing when Instance of Partial delivery information(Payment) of <BR>
 *        Parameter is Null or the data length is 0. <BR>
 *     </DIR>
 *     <BR>
 *     2.Partial delivery information Instance(Payment) retrieval <BR>
 *     <DIR>
 *       - Retrieve Instance of Partial delivery information(Payment) with the following conditions and the order of reading.  <BR>
 *        Do not do following Processing when you cannot detect pertinent data.  <BR>
 *        <DIR>
 *        <Search condition> <BR>
 *          <DIR>
 *           - Completed allocated qty is data other than 0.  <BR>
 *          </DIR>
 *        </DIR>
 *     </DIR>
 *     <BR>
 *     3.Do following information from Partial delivery information Instance(Payment) on Parameter as a key  <BR>
 *       with the retrieval of work information file (Dnworkinfo). <BR>
 *     <DIR>
 *       <Retrieval key> <BR>
 *         <DIR>
 *         Do the work acquisition order definition Processing (selectWorkOrderMethod). <BR>
 *         </DIR>
 *     </DIR>
 *     <BR>
 *     4.Skip Work actual qty and work information on Partial delivery information Instance.  <BR>
 *     <DIR>
 *       As for the comparison with Work actual qty, judge the status of the work information file as follows.  <BR>
 *        - Possible work qty in case of status is [Stand by] or [Working]. <BR>
 *        - Possible work qty - Work Allocated qty in case of status is [Completed]. <BR>
 *     </DIR>
 *     5.Work information (Dnworkinfo) update Processing <BR>
 *     <DIR>
 *       - Based on Partial delivery information Instance(Payment) and retrieved work information file (Dnworkinfo) <BR>
 *        Update work information file (Dnworkinfo).  <BR>
 *        <DIR>
 *        <Update condition> <BR>
 *          <DIR>
 *           - Plan unique key matches with retrieved work information <BR>
 *           - The registration date order matches with retrieved work information <BR>
 *           - The possible work information qty is excluding 0.  <BR>
 *          </DIR>
 *        <Content of update> <BR>
 *          <DIR>
 *           - Possible work qty : Set remaining qty <BR>
 *           - Status flag :  Change it to [Started] <BR>
 *           - Last updated date and time : System date <BR>
 *           - Last update Processing name : Processing name <BR>
 *          </DIR>
 *        </DIR>      
 *     </DIR>
 *     <BR>
 *     6.Work information (Dnworkinfo) making Processing <BR>
 *     <DIR>
 *       - Work information file (DnWorkInfo) is newly made only before updating [in case of Work qty - remaining qty is more than 1] <BR>
 *       - Read and make the content of the registration of the item when you exclude the following items by the same content as the former file.  <BR>
 *        <DIR>
 *        <Content of registration> <BR>
 *          <DIR>
 *            - Work No. : Number it newly.  <BR>
 *            - Consolidating Work No. : Set above mentioned Work No. <BR>
 *            - Status flag : Completed <BR>
 *            - Stock ID : Number it newly.  <BR>
 *            - Work plan qty : Planned qty before update - remaining qty <BR>
 *            - Possible work qty : Planned qty before update - remaining qty <BR>
 *            - Work actual qty : 0 <BR>
 *            - Work Pending qty : 0 <BR>
 *            - Registration date and time : System date <BR>
 *            - Registration Process name : Processing name <BR>
 *            - Last updated date and time : System date <BR>
 *            - Last update Processing name : Processing name <BR>
 *          </DIR>
 *        </DIR>
 *     </DIR>
 *     <BR>
 *     7.Inventory information (Dmstock) update Processing <BR>
 *     <DIR>
 *       - Based on Partial delivery information Instance(Payment) and retrieved work information file (Dnworkinfo) <BR>
 *        Update stock information file (Dmstock).  <BR>
 *        <DIR>
 *        <Update condition> <BR>
 *          <DIR>
 *           - Stock ID matches with retrieved work information file (Dnworkinfo) <BR>
 *          </DIR>
 *        <Content of update> <BR>
 *          <DIR>
 *           - Stock qty : Set remaining qty <BR>
 *           - Last updated date and time : System date <BR>
 *           - Last update Processing name : Processing name <BR>
 *          </DIR>
 *        </DIR>      
 *     </DIR>
 *     <BR>
 *     8.Inventory information (Dmstock) making Process <BR>
 *     <DIR>
 *       - Make stock information file (Dmstock) newly when you newly make work information file (Dnworkinfo) by above mentioned Processing.  <BR>
 *       - Read and make the content of the registration of the item when you exclude the following items by the same content as the former file.  <BR>
 *        <DIR>
 *        <Content of registration> <BR>
 *          <DIR>
 *            - Stock ID : Stock ID which numbered it when above mentioned work information is registered <BR>
 *            - Stock qty : Work plan qty of work information file (Dnworkinfo) calculated when above mentioned work information is registered<BR>
 *            - Drawing qty : Work plan qty of work information file (Dnworkinfo) calculated when above mentioned work information is registered<BR>
 *            - Storage Plan qty : 0 <BR>
 *            - Final Picking date : Space <BR>
 *            - Registration date and time : System date <BR>
 *            - Registration Process name : Processing name <BR>
 *            - Last updated date and time : System date <BR>
 *            - Last update Processing name : Processing name <BR>
 *          </DIR>
 *        </DIR>
 *     </DIR>
 *     <BR>
 *     9.Next work information (Dnnextproc) update Processing <BR>
 *     <DIR>
 *       - Based on Partial delivery information Instance(Payment) and retrieved work information file (Dnworkinfo) <BR>
 *        Update next work information file (Dnnextproc).  <BR>
 *        <DIR>
 *        <Update condition> <BR> 
 *          <DIR>
 *           - Partial delivery information Instance(Payment) matches with next work information unique key <BR>
 *          </DIR>
 *        <Content of update> <BR>
 *          <DIR>
 *           - Work actual qty <BR>
 *            Add Completed allocated qty of Partial delivery information Instance(Payment) to Work actual qty of <BR>
 *            next work information file (Dnnextproc).  <BR>
 *           - Work Pending qty <BR>
 *            Add Completed allocated qty of Partial delivery information Instance(Payment) to Work actual qty of <BR>
 *            next work information file (Dnnextproc).  <BR>
 *           - Status flag : Update it to [Completed] when Work plan qty <= Work actual qty.  <BR>
 *           - Last updated date and time : System date <BR>
 *           - Last update Processing name : Processing name <BR>
 *          </DIR>
 *        </DIR>      
 *     </DIR>
 * <BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/12/06</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Date: 2006/11/07 06:12:51 $
 * @author  $Author: suresh $ 
 */
public class PaymentOperator extends Object
{
	//#CM644424
	//	Class variables -----------------------------------------------
	//#CM644425
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM644426
	/**
	 * Processing name
	 */
	private static String wProcessName = "PaymentOperator";

	//#CM644427
	/**
	 *  Work process qty 
	 */
	private int wWorkPtn = 5;
	
	//#CM644428
	/**
	 * Connection Object
	 */
	Connection wConn = null;

	//#CM644429
	/**
	 * Stock information handler
	 */
	StockHandler wStockHandler = null;

	//#CM644430
	/**
	 * Stock information Retrieval key
	 */
	StockSearchKey wStockKey = null;

	//#CM644431
	/**
	 * Stock information update key
	 */
	StockAlterKey wStockAltKey = null;

	//#CM644432
	/**
	 * Stock control package existence
	 * true:Yes false:No
	 */
	boolean wExistStockPack = false;
	
	//#CM644433
	/**
	 * Start update Processing available
	 * true:Renew the flag false:Do not renew the flag
	 */
	boolean wStartFlag = true;
	
	//#CM644434
	/**
	 * Flag which indicates whether Work information file is updated or not
	 * true: Updated false: Not updated
	 */
	boolean wUpdateOnOff = false;

	//#CM644435
	/**
	 * Work information Handler
	 */
	WorkingInformationHandler wWorkHandler = null;

	//#CM644436
	/**
	 * Work information Retrieval key
	 */
	WorkingInformationSearchKey wWorkKey = null;

	//#CM644437
	/**
	 * Work information update key
	 */
	WorkingInformationAlterKey wWorkAltKey = null;

	//#CM644438
	/**
	 * Next Work information Handler
	 */
	NextProcessInfoHandler wNextHandler = null;

	//#CM644439
	/**
	 * Next work information Retrieval key
	 */
	NextProcessInfoSearchKey wNextKey = null;

	//#CM644440
	/**
	 * Next work information update key
	 */
	NextProcessInfoAlterKey wNextAltKey = null;
	
	//#CM644441
	/**
	 * Sequence Handler
	 */
	SequenceHandler wSequenceHandler = null;

	//#CM644442
	// Class method --------------------------------------------------
	//#CM644443
	/**
	 * Return Version of this Class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:12:51 $");
	}
	
	//#CM644444
	// Constructors --------------------------------------------------
	//#CM644445
	/**
	 * Initialize this Class. 
	 * 
	 * @param conn Database Connection Object
	 * @throws ScheduleException It is notified when the exception not anticipated in check Processing is generated. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 */
	public PaymentOperator(Connection conn) throws ScheduleException, ReadWriteException
	{
		wConn = conn;
		wStockHandler = new StockHandler(conn);
		wStockKey = new StockSearchKey();
		wStockAltKey = new StockAlterKey();

		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();
		wWorkAltKey = new WorkingInformationAlterKey();

		wNextHandler = new NextProcessInfoHandler(conn);
		wNextKey = new NextProcessInfoSearchKey();
		wNextAltKey = new NextProcessInfoAlterKey();
		
		wSequenceHandler = new SequenceHandler(conn);

		wExistStockPack = isExistStockPackage(conn);
	}

	//#CM644446
	// Public methods ------------------------------------------------
	//#CM644447
	/**
	 * Call this Method by object Work information extraction Processing. 
	 * @param sKey Retrieval key edit of Work information file
	 * @param wPayment    Partial delivery information Instance
	 * @param workkind    Work flag
	 * @return True when Object Work information can be extracted,False when it is not possible to extract it
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database.  <BR>
	 */ 
	protected boolean selectWorkOrder(WorkingInformationSearchKey sKey, Payment wPayment, String workkind)
																					throws ReadWriteException
	{
		//#CM644448
		// Divide the work based on Work flag
		//#CM644449
		// Receiving  
		if(workkind.equals(SystemDefine.JOB_TYPE_INSTOCK))
		{
			sKey.KeyClear() ;
			sKey.setPlanUkey(wPayment.getInstockPlanUkey());
			sKey.setPlanEnableQty(0, "!=");
			
			sKey.setPlanUkeyOrder(1, true);
			sKey.setRegistDateOrder(2, true) ;			
			sKey.setJobNoOrder(3, true) ;
		}
		//#CM644450
		// Storage 
		else if(workkind.equals(SystemDefine.JOB_TYPE_STORAGE))
		{
			sKey.KeyClear() ;
			sKey.setPlanUkey(wPayment.getStoragePlanUkey());
			sKey.setPlanEnableQty(0, "!=");

			sKey.setPlanUkeyOrder(1, true);
			sKey.setRegistDateOrder(2, true) ;
			sKey.setJobNoOrder(3, true) ;
		}
		//#CM644451
		// Picking 
		else if(workkind.equals(SystemDefine.JOB_TYPE_RETRIEVAL))
		{
			sKey.KeyClear() ;
			sKey.setPlanUkey(wPayment.getRetrievalPlanUkey());
			sKey.setPlanEnableQty(0, "!=");

			sKey.setPlanUkeyOrder(1, true);
			sKey.setRegistDateOrder(2, true) ;
			sKey.setJobNoOrder(3, true) ;
		}
		//#CM644452
		// Sorting 
		else if(workkind.equals(SystemDefine.JOB_TYPE_SORTING))
		{
			sKey.KeyClear() ;
			sKey.setPlanUkey(wPayment.getSortingPlanUkey());
			sKey.setPlanEnableQty(0, "!=");

			sKey.setPlanUkeyOrder(1, true);
			sKey.setRegistDateOrder(2, true) ;
			sKey.setJobNoOrder(3, true) ;
		}
		//#CM644453
		// Shipping 
		else if(workkind.equals(SystemDefine.JOB_TYPE_SHIPINSPECTION))
		{
			sKey.KeyClear() ;
			sKey.setPlanUkey(wPayment.getShippingPlanUkey());
			sKey.setPlanEnableQty(0, "!=");

			sKey.setPlanUkeyOrder(1, true);
			sKey.setRegistDateOrder(2, true) ;
			sKey.setJobNoOrder(3, true) ;
		}
		else
		{
			return false ;		
		}
		return true;
	}

	//#CM644454
	/**
	 * Do the Partial delivery update Processing. <BR>
	 * @param wPayment    Partial delivery information Instance
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 * @throws ScheduleException It is notified when the exception not anticipated in check Processing is generated. 
	 * @return True at the normal termination, False at abnormal or Error.
	 */
	public boolean UpdatePayment(Payment pPayment)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM644455
			// When Partial delivery information (Payment) Instance data is null
			//#CM644456
			// Do not do the following Processing.
			if (pPayment == null)
			{
				return true ;
			}

			//#CM644457
			// When Completed allocated qty of Partial delivery information (Payment) Instance data is 0
			//#CM644458
			// Do not do the following Processing.
			if (pPayment.getCompleteQty() == 0)
			{
				return true ;
			}

			//#CM644459
			// It loops the working line. 
			for (int work_koutei = 0; work_koutei < wWorkPtn; work_koutei++)
			{
				//#CM644460
				// Work information retrieval Processing
				String jobType = null;
				//#CM644461
				// Receiving 
				if (work_koutei == 0)
				{
					if (StringUtil.isBlank(pPayment.getInstockPlanUkey()))
						continue;
					jobType = SystemDefine.JOB_TYPE_INSTOCK ;
				}
				//#CM644462
				// Storage 
				else if (work_koutei == 1)
				{
					if (StringUtil.isBlank(pPayment.getStoragePlanUkey()))
						continue;
					jobType = SystemDefine.JOB_TYPE_STORAGE ;
				}
				//#CM644463
				// Picking 
				else if (work_koutei == 2)
				{
					if (StringUtil.isBlank(pPayment.getRetrievalPlanUkey()))
						continue;
					jobType = SystemDefine.JOB_TYPE_RETRIEVAL ;
				}
				//#CM644464
				// Sorting 
				else if (work_koutei == 3)
				{
					if (StringUtil.isBlank(pPayment.getSortingPlanUkey()))
						continue;
					jobType = SystemDefine.JOB_TYPE_SORTING ;
				}
				//#CM644465
				// Shipping 
				else if (work_koutei == 4)
				{
					if (StringUtil.isBlank(pPayment.getShippingPlanUkey()))
						continue;
					jobType = SystemDefine.JOB_TYPE_SHIPINSPECTION ;
				}	
				
				//#CM644466
				// Set the order of reading by using The acquisition order Method.
				boolean rStatus = this.selectWorkOrder(wWorkKey, pPayment, jobType);
				//#CM644467
				// Do not do the following Processing in case of Search condition Error. 
				if (rStatus == false)
					return true;

				//#CM644468
				// Read Work information by above mentioned Acquisition condition. (exclusive reading)
				WorkingInformation[] rWork = (WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);

				//#CM644469
				// Preserve information on Partial delivery information Instance in work. 
				//#CM644470
				// Work actual qty
				int resultQty = pPayment.getResultQty() ;
				//#CM644471
				// Completed allocated qty
				int completeQty = pPayment.getCompleteQty() ;
				//#CM644472
				// Possible work qty this time
				int enableQty = 0;
				//#CM644473
				// Work information is read. 
				for (int slc = 0; slc < rWork.length; slc++)
				{	
					//#CM644474
					// Skip Work actual qty and Work information of Partial delivery 
					//#CM644475
					// information (Payment) Instance. 
					//#CM644476
					// When the work status of work information is [Stand by] or [Working]
					if (rWork[slc].getStatusFlag().equals(SystemDefine.STATUS_FLAG_UNSTART) ||
						rWork[slc].getStatusFlag().equals(SystemDefine.STATUS_FLAG_START) ||
						rWork[slc].getStatusFlag().equals(SystemDefine.STATUS_FLAG_NOWWORKING))
					{
						//#CM644477
						// Compare it with Work actual qty and Possible work qty of Partial delivery information Instance. 
						if (resultQty >= rWork[slc].getPlanEnableQty())
						{
							//#CM644478
							// The skipped qty is subtracted from the actual qty. 
							resultQty = resultQty - rWork[slc].getPlanEnableQty() ;
							//#CM644479
							// Skip reading Work information this time. 
							continue ;
						}
						else
						{
							enableQty = rWork[slc].getPlanEnableQty() ;
							resultQty = 0 ;
						}
					}
					//#CM644480
					// When the work status of Work information is [Completed]
					else if (rWork[slc].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION))
					{
						//#CM644481
						// Compare it with Work actual qty of Partial delivery information Instance (Possible work qty - Reserved work qty). 
						if (resultQty >= (rWork[slc].getPlanEnableQty() - rWork[slc].getPendingQty()))
						{
							//#CM644482
							// The skipped qty is subtracted from the actual qty. 
							resultQty = resultQty - (rWork[slc].getPlanEnableQty() - rWork[slc].getPendingQty()) ;
							//#CM644483
							// Skip reading Work information this time. 
							continue ;
						}
						else
						{
							//#CM644484
							// Current work qty (Skipping is subtracted.) 
							enableQty = (rWork[slc].getPlanEnableQty() - rWork[slc].getPendingQty()) - resultQty ;
							resultQty = 0 ;
							
							//#CM644485
							// It must be assumed and Processed at the backend because Completed Processing has already been done. 
							continue ;
						}
					}
						
					//#CM644486
					// Calculate the remainder qty. 
					int zanCompQty = 0;
					//#CM644487
					// Compare the numbers of possible work with the number of Partial delivery information Instance of Partial delivery allocations this time. 
					//#CM644488
					// Use that the value as small as Processing qty. 
					if (completeQty < enableQty)
					{
						//#CM644489
						// Remaining qty = Current work qty  -  Partial delivery allocated qty of partial delivery information Instance
						zanCompQty = enableQty - completeQty ; 		
						//#CM644490
						// Use the number of Partial delivery allocations as Current work qty. 
						enableQty = completeQty ;
					}
					else
					{
						//#CM644491
						// Remaining qty = The remaining qty becomes 0 because of all Work information qty read this time. 
						zanCompQty = 0 ; 
					}

					//#CM644492
					// Work information update Processing
					wWorkAltKey.KeyClear();
					wWorkAltKey.setJobNo(rWork[slc].getJobNo());
					wWorkAltKey.setPlanUkey(rWork[slc].getPlanUkey());
					wWorkAltKey.setRegistDate(rWork[slc].getRegistDate());
					wWorkAltKey.setPlanEnableQty(0, "!=");
					wWorkAltKey.updatePlanEnableQty(enableQty);
					wWorkAltKey.updateLastUpdatePname(wProcessName);

					//#CM644493
					// Next, renew only the corresponding package about the flag. 
					if (wStartFlag == true)
					{
						wWorkAltKey.updateBeginningFlag(SystemDefine.BEGINNING_FLAG_STARTED);
					}
					wWorkHandler.modify(wWorkAltKey);
					
					//#CM644494
					// Memorize doing Work information update Processing. 
					wUpdateOnOff = true ;
		
					//#CM644495
					// Do following Processing from Work information before updating when the plan qty - Remaining qty is one or more. 
					if (zanCompQty >= 1)
					{										
						//#CM644496
						// Work information registration Processing
						if (!createWorkInfo(wConn,
						    zanCompQty,
							wProcessName,
							rWork[slc]))
							return false ;
					}
						
					//#CM644497
					// Subtract a part of work qty from Completed allocated qty. 
					completeQty = completeQty - enableQty ;
					
					//#CM644498
					// It comes off Processing when Completed allocated qty becomes 0 or less. 
					if (completeQty <= 0)
					break ;
					
				}
				
				//#CM644499
				// Retain the Start flag and Update flag. 
				//#CM644500
				// Only when you do Work information update Processing
				if (wUpdateOnOff == true)
				{
					wStartFlag = false ;
				}
			}
			
			//#CM644501
			// Next Work information retrieval Processing
			wNextKey.KeyClear();
			wNextKey.setNextProcUkey(pPayment.getNextProcUkey());
			NextProcessInfo[] rNext = (NextProcessInfo[]) wNextHandler.findForUpdate(wNextKey);

			//#CM644502
			// Add Completed allocated qty of Partial delivery information Instance to Work actual qty. 
			//#CM644503
			// Next Work information update Processing
			wNextAltKey.KeyClear();
			wNextAltKey.setNextProcUkey(pPayment.getNextProcUkey());
			wNextAltKey.updateResultQty(rNext[0].getResultQty() + pPayment.getCompleteQty());
			wNextAltKey.updateLastUpdatePname(wProcessName);
			wNextHandler.modify(wNextAltKey);
			
			//#CM644504
			// Next Work information retrieval Processing
			wNextKey.setNextProcUkey(pPayment.getNextProcUkey());
			NextProcessInfo[] rNextAfter = (NextProcessInfo[]) wNextHandler.find(wNextKey);
			
			//#CM644505
			// Change status to [Completed] when Work plan qty of Next work information <= (Work actual qty + Work Pending qty)
			if (rNextAfter[0].getPlanQty() <= (rNextAfter[0].getResultQty() + rNextAfter[0].getShortageQty()))
			{
				//#CM644506
				// Change work status to [Completed]. 
				wNextAltKey.KeyClear();
				wNextAltKey.setNextProcUkey(pPayment.getNextProcUkey());
				wNextAltKey.updateStatusFlag(NextProcessInfo.STATUS_FLAG_PROCESSING_FINISH);
				wNextAltKey.updateLastUpdatePname(wProcessName);
				wNextHandler.modify(wNextAltKey);
			}
			//#CM644507
			// Change status to [Processing] when Work plan qty of Next work information> (Work actual qty + Work Pending qty)
			else
			{
				//#CM644508
				// Change work status to [Processing]. 
				wNextAltKey.KeyClear();
				wNextAltKey.setNextProcUkey(pPayment.getNextProcUkey());
				wNextAltKey.updateStatusFlag(NextProcessInfo.STATUS_FLAG_PROCESSING);
				wNextAltKey.updateLastUpdatePname(wProcessName);
				wNextHandler.modify(wNextAltKey);
			}
			
			return true;

		}
		catch (DataExistsException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM644509
	// Package methods -----------------------------------------------

	//#CM644510
	// Protected methods ---------------------------------------------

	//#CM644511
	/**
	 * Do not Acquire the stock control package referring to system definition table (WARENAVI_SYSTEM). <BR>
	 * Return true when there is a stock control package and return false in case of none. 
	 * 
	 * @param conn Database Connection Object
	 * @return True: There is a stock package.  False: There is no stock package. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 * @throws ScheduleException It is notified when the exception not anticipated in check Processing is generated. 
	 */
	private boolean isExistStockPackage(Connection conn)
		throws ReadWriteException, ScheduleException
	{

		WareNaviSystem wms = new WareNaviSystem();

		WareNaviSystemSearchKey skey = new WareNaviSystemSearchKey();
		WareNaviSystemHandler wnhdl = new WareNaviSystemHandler(conn);

		String stockPack = "";

		try
		{
			wms = (WareNaviSystem) wnhdl.findPrimary(skey);
			if (wms == null)
			{
				//#CM644512
				//6006002 = Database Error was generated.{0}
				RmiMsgLogClient.write(6006039, LogMessage.F_ERROR, "WareNaviSystemHandler", null);
				throw (new ScheduleException("6007039" + wDelim + "WARENAVI_SYSTEM"));
			}

			stockPack = wms.getStockPack();
			if (stockPack.equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
				return true;

		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		return false;
	}

	//#CM644513
	/**
	 * Register the Work information table. 
	 * @param  conn        Instance which maintains Connection with Database. 
	 * @param  zanQty      Plan qty - Remaining work qty
	 * @param  processname Processing name
	 * @param  workinfo Work informationInstance
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 * @throws DataExistsException Notified when same information had already been registered.
	 * @return Return true when it is possible to register in the Work information table normally. 
	 */
	protected boolean createWorkInfo(
		Connection conn,
		int zanQty,
		String processname,
		WorkingInformation workinfo)
		throws ReadWriteException, DataExistsException
	{
		
		try
		{
			WorkingInformation base_workinfo = new WorkingInformation();

			//#CM644514
			// Work No.
			String wjobno = wSequenceHandler.nextJobNo();
			base_workinfo.setJobNo(wjobno);
			//#CM644515
			// Work flag (Picking ) 
			base_workinfo.setJobType(workinfo.getJobType());
			//#CM644516
			// Consolidating Work No.
			base_workinfo.setCollectJobNo(wjobno);
			//#CM644517
			// Status flag (Stand by) 
			base_workinfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			//#CM644518
			// Work Start flag (Stand by) 
			base_workinfo.setBeginningFlag(workinfo.getBeginningFlag());
			//#CM644519
			// Plan unique key
			base_workinfo.setPlanUkey(workinfo.getPlanUkey());
			//#CM644520
			// Stock ID
			base_workinfo.setStockId(workinfo.getStockId());			
			//#CM644521
			// Area No
			base_workinfo.setAreaNo(workinfo.getAreaNo());
			//#CM644522
			// Location No
			base_workinfo.setLocationNo(workinfo.getLocationNo());
			//#CM644523
			// Plan date
			base_workinfo.setPlanDate(workinfo.getPlanDate());
			//#CM644524
			// Consignor Code
			base_workinfo.setConsignorCode(workinfo.getConsignorCode());
			//#CM644525
			// Consignor Name
			base_workinfo.setConsignorName(workinfo.getConsignorName());
			//#CM644526
			// Supplier ID
			base_workinfo.setSupplierCode(workinfo.getSupplierCode());
			//#CM644527
			// Supplier Name
			base_workinfo.setSupplierName1(workinfo.getSupplierName1());
			//#CM644528
			// Receiving slip No
			base_workinfo.setInstockTicketNo(workinfo.getInstockTicketNo());
			//#CM644529
			// Receiving slip line
			base_workinfo.setInstockLineNo(workinfo.getInstockLineNo());
			//#CM644530
			// Customer Code
			base_workinfo.setCustomerCode(workinfo.getCustomerCode());
			//#CM644531
			// Customer Name
			base_workinfo.setCustomerName1(workinfo.getCustomerName1());
			//#CM644532
			// Shipping line No
			base_workinfo.setShippingTicketNo(workinfo.getShippingTicketNo());
			//#CM644533
			// Shipping slip line
			base_workinfo.setShippingLineNo(workinfo.getShippingLineNo());
			//#CM644534
			// Item Code
			base_workinfo.setItemCode(workinfo.getItemCode());
			//#CM644535
			// Item Name
			base_workinfo.setItemName1(workinfo.getItemName1());
			//#CM644536
			// Host Plan qty
			base_workinfo.setHostPlanQty(workinfo.getHostPlanQty());
			//#CM644537
			// Work plan qty
			base_workinfo.setPlanQty(zanQty);
			//#CM644538
			// Possible work qty
			base_workinfo.setPlanEnableQty(zanQty);
			//#CM644539
			// Actual qty
			base_workinfo.setResultQty(0);
			//#CM644540
			// Shortage qty
			base_workinfo.setShortageCnt(0);
			//#CM644541
			// Reserved qty
			base_workinfo.setPendingQty(0);
			//#CM644542
			// Packed qty per case
			base_workinfo.setEnteringQty(workinfo.getEnteringQty());
			//#CM644543
			// Packed qty per bundle
			base_workinfo.setBundleEnteringQty(workinfo.getBundleEnteringQty());
			//#CM644544
			// Case/Piece flag : Mode of packing
			base_workinfo.setCasePieceFlag(workinfo.getCasePieceFlag());
			//#CM644545
			// Case/Piece flag : Work form
			base_workinfo.setWorkFormFlag(workinfo.getWorkFormFlag());
			//#CM644546
			// Case ITF
			base_workinfo.setItf(workinfo.getItf());
			//#CM644547
			// Bundle ITF
			base_workinfo.setBundleItf(workinfo.getBundleItf());
			//#CM644548
			// TC/DC flag (DC) 
			base_workinfo.setTcdcFlag(workinfo.getTcdcFlag());
			//#CM644549
			// Day of Expiry date
			base_workinfo.setUseByDate(workinfo.getUseByDate());
			//#CM644550
			// Lot No.
			base_workinfo.setLotNo(workinfo.getLotNo());
			//#CM644551
			// Plan information comment
			base_workinfo.setPlanInformation(workinfo.getPlanInformation());
			//#CM644552
			// Order No
			base_workinfo.setOrderNo(workinfo.getOrderNo());
			//#CM644553
			// Day of order
			base_workinfo.setOrderingDate(workinfo.getOrderingDate());
			//#CM644554
			// Expiry date
			base_workinfo.setResultUseByDate(workinfo.getResultUseByDate());
			//#CM644555
			// Lot No.
			base_workinfo.setResultLotNo(workinfo.getResultLotNo());
			//#CM644556
			// Work result Location
			base_workinfo.setResultLocationNo(workinfo.getResultLocationNo());
			//#CM644557
			// Stand by report flag
			base_workinfo.setReportFlag(workinfo.getReportFlag());
			//#CM644558
			// Batch No
			base_workinfo.setBatchNo(workinfo.getBatchNo());
			//#CM644559
			// Worker Code
			base_workinfo.setWorkerCode(workinfo.getWorkerCode());
			//#CM644560
			// Worker Name
			base_workinfo.setWorkerName(workinfo.getWorkerName());
			//#CM644561
			// Terminal No
			base_workinfo.setTerminalNo(workinfo.getTerminalNo());
			//#CM644562
			// Plan information Registration date and time
			base_workinfo.setPlanRegistDate(workinfo.getRegistDate());
			//#CM644563
			// Registration Process name
			base_workinfo.setRegistPname(processname);
			//#CM644564
			// Last update Processing name
			base_workinfo.setLastUpdatePname(processname);
			//#CM644565
			// Registration of data
			wWorkHandler.create(base_workinfo);

			return true;

		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (ReadWriteException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM644566
	// Private methods -----------------------------------------------

}
//#CM644567
//end of class
