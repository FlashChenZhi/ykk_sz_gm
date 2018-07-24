package jp.co.daifuku.wms.base.crossdoc;

//#CM644704
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
import jp.co.daifuku.wms.base.dbhandler.HostSendHandler;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoAlterKey;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoHandler;
import jp.co.daifuku.wms.base.dbhandler.NextProcessInfoSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.ShippingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SortingPlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.HostSend;
import jp.co.daifuku.wms.base.entity.NextProcessInfo;
import jp.co.daifuku.wms.base.entity.ShippingPlan;
import jp.co.daifuku.wms.base.entity.SortingPlan;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.SystemDefine;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM644705
/**
 * Designer : K.Toda <BR>
 * Maker : K.Toda <BR>
 * <BR>
 * Update each file based on Pending information Instance. <BR>
 * <BR>
 * 1.Work information acquisition order definition Processing(<CODE>selectWorkOrder()</CODE>Method) <BR> 
 * <BR>
 * <DIR>
 *   Return Acquisition condition of Work information(Dnworkinfo) which does <BR>
 *   Pending Processing and acquisition order by Parameter.  <BR>
 *   Processing is divided by the work flag. <BR>
 *   <Parameter > <BR>
 *   <DIR>
 *     Instance of Pending Information(Shortage) <BR>
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
 *        - The corresponding Plan unique key of Work information file (DnWorkInfo) with Storage Plan unique key of Pending information Instance(Shortage). <BR>
 *        - The possible work information qty is excluding 0.  <BR>
 *       </DIR>
 *     1-2.When Work flag of Parameter is [Receiving] <BR>
 *       <DIR>
 *        - The corresponding Plan unique key of Work information file (DnWorkInfo) with Receiving Plan unique key of Pending information Instance(Shortage). <BR>
 *        - The possible work information qty is excluding 0.  <BR>
 *       </DIR>
 *     1-3.When Work flag of Parameter is [Picking] <BR>
 *       <DIR>
 *        - The corresponding Plan unique key of Work information file (DnWorkInfo) with Picking Plan unique key of Pending information Instance(Shortage). <BR>
 *        - The possible work information qty is excluding 0.  <BR>
 *       </DIR>
 *     1-4.When Work flag of Parameter is [Sorting] <BR>
 *       <DIR>
 *        - The corresponding Plan unique key of Work information file (DnWorkInfo) with Sorting Plan unique key of Pending information Instance(Shortage). <BR>
 *        - The possible work information qty is excluding 0.  <BR>
 *       </DIR>
 *     1-5.When Work flag of Parameter is [Shipping] <BR>
 *       <DIR>
 *        - The corresponding Plan unique key of Work information file (DnWorkInfo) with Shipping Plan unique key of Pending information Instance(Shortage). <BR>
 *        - The possible work information qty is excluding 0.  <BR>
 *       </DIR>
 *     <The order of reading> <BR>
 *       <DIR>
 *        - Descending order of Receiving Plan unique key<BR>
 *        - Descending order of Storage Plan unique key<BR>
 *        - Descending order of Picking Plan unique key<BR>
 *        - Descending order of Sorting Plan unique key<BR>
 *        - Descending order of Shipping Plan unique key<BR>
 *       </DIR>
 *   </DIR>
 *   <The retrieval order> <BR>
 *   <DIR>
 *    - Descending order of Registration date and time <BR>
 *   </DIR>
 *   <BR>
 * </DIR>
 * <BR>
 * 2.Pending update Processing(<CODE>UpdateShortage()</CODE>Method) <BR> 
 * <BR>
 * <DIR>
 *   Return acquisition information to do Pending update Processing by Parameter.  <BR>
 *   <Parameter > <BR>
 *   <DIR>
 *     Connection with Database <BR>
 *     Instance of Pending Information(Shortage) <BR>
 *   </DIR>
 *   <Return information> <BR>
 *   <DIR>
 *    Return True at the normal termination and False at abnormality Error. 
 *   </DIR>
 *   <Processing details> <BR>
 *   <DIR>
 *     1.Start check <BR>
 *     <DIR>
 *       - Instance of Pending Information(Shortage) of Parameter is Null or Do not do the following Processing <BR>
 *        in case of 0 in the data length.  <BR>
 *     </DIR>
 *     <BR>
 *     2.Pending information Instance(Shortage) retrieval <BR>
 *     <DIR>
 *       - Retrieve Instance of Pending Information(Shortage) with the following conditions and the order of reading.  <BR>
 *        <DIR>
 *        <Search condition> <BR>
 *          <DIR>
 *           - The corresponding Receiving Plan unique key of Pending information instance (Shortage) with Plan unique key of Parameter <BR>
 *           - Pending allocation qty is data other than 0.  <BR>
 *          </DIR>
 *        </DIR>
 *     </DIR>
 *     <BR>
 *     3.Do following information from Pending information Instance(Shortage) on Parameter as a key with the retrieval of <BR>
 *       Work information file (Dnworkinfo).  <BR>
 *     <DIR>
 *       <Retrieval key> <BR>
 *         <DIR>
 *         Do the work acquisition order definition Processing (selectWorkOrderMethod). <BR>
 *         </DIR>
 *     </DIR>
 *     <BR>
 *     4.Skip Actual shortage qty and Work information of Pending information Instance.  <BR>
 *     <DIR>
 *       As for the comparison with Actual shortage qty, judge the status of the Work information file as follows. <BR>
 *        - Possible work qty in case of status is [Stand by] or [Working]. <BR>
 *        - Possible work qty - Work Allocated qty in case of status is [Completed]. <BR>
 *     </DIR>
 *     <BR>
 *     5.Work information (Dnworkinfo) update Processing <BR>
 *     <DIR>
 *       - Based on Cahin information Instance(Shortage) and retrieved Work information file (Dnworkinfo) <BR>
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
 *           - Possible work qty <BR>
 *            Subtract it from Possible work qty of Work information file (Dnworkinfo) for Pending allocation qty of <BR>
 *            Pending information Instance(Shortage).  <BR>
 *           - Status flag : Change it to [deletion] when Possible work qty becomes 0 or less.  <BR>
 *           - Last updated date and time : System date <BR>
 *           - Last update Processing name : Processing name <BR>
 *          </DIR>
 *        </DIR>      
 *     </DIR>
 *     <BR>
 *     6.Work information (Dnworkinfo) making Processing <BR>
 *     <DIR>
 *       - Make Work information file (Dnworkinfo) which corresponds to the subtraction newly.  <BR>
 *       - Read and make the content of the registration of the item when you exclude the following items by the same content as the former file.  <BR>
 *        <DIR>
 *        <Content of registration> <BR>
 *          <DIR>
 *            - Work No. : Number it newly.  <BR>
 *            - Status flag : Completed <BR>
 *            - Stock ID : Space clear <BR>
 *            - Possible work qty : Pending allocation qty or Pending information Instance(Shortage) <BR>
 *            - Work actual qty : 0 <BR>
 *            - Work Pending qty : Pending allocation qty or Pending information Instance(Shortage) <BR>
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
 *       - Based on Cahin information Instance(Shortage) and retrieved Work information file (Dnworkinfo) <BR>
 *        Update stock information file (Dmstock).  <BR>
 *        <DIR>
 *        <Update condition> <BR>
 *          <DIR>
 *           - Stock ID matches with retrieved work information file (Dnworkinfo) <BR>
 *          </DIR>
 *        <Content of update> <BR>
 *          <DIR>
 *           - Stock qty <BR>
 *            Subtract it from Stock qty of Work information file (Dnworkinfo) for Pending allocation qty of <BR>
 *            Pending information Instance(Shortage).  <BR>
 *           - Drawing qty <BR>
 *            Subtract it from Drawing qty of Work information file (Dnworkinfo) for Pending allocation qty of <BR>
 *            Pending information Instance(Shortage).  <BR>
 *           - Last updated date and time : System date <BR>
 *           - Last update Processing name : Processing name <BR>
 *          </DIR>
 *        </DIR>      
 *     </DIR>
 *     <BR>
 *     8.Sorting schedule information (Dnsortingplan) update Processing<BR>
 *     <DIR>
 *       - Based on Cahin information Instance(Shortage) and retrieved Work information file (Dnworkinfo) <BR>
 *        Update Sorting plan information file (Dnsortingplan).  <BR>
 *        <DIR>
 *        <Update condition> <BR>
 *          <DIR>
 *           - Corresponding Sorting Plan unique key with Sorting Plan unique key of Pending information Instance(Shortage). <BR>
 *          </DIR>
 *        <Content of update> <BR>
 *          <DIR>
 *           - Work Pending qty <BR>
 *            Add to Work Pending qty of Sorting schedule information file (Dnsortingplan) for Pending allocation qty of <BR>
 *            Pending information Instance(Shortage).  <BR>
 *           - Work Status flag : When the total of Shortage qty and Actual qty is Plan qty or more, Changes to [Completed]. <BR>
 *            In the following cases, changes to [Partially Completed] for Status flag [Stand by].  <BR>
 *           - Last updated date and time : System date <BR>
 *           - Last update Processing name : Processing name <BR>
 *          </DIR>
 *        </DIR>      
 *     </DIR>
 *     <BR>
 *     9.Shipping schedule information (Dnshippingplan) update Processing <BR>
 *     <DIR>
 *       - Based on Cahin information Instance(Shortage) and retrieved Work information file (Dnworkinfo) <BR>
 *        Update Shipping schedule information file (Dnshippingplan). <BR>
 *        <DIR>
 *        <Update condition> <BR>
 *          <DIR>
 *           - Corresponding Shipping Plan unique key with Sorting Plan unique key of Pending information Instance(Shortage). <BR>
 *          </DIR>
 *        <Content of update> <BR>
 *          <DIR>
 *           - Work Pending qty <BR>
 *            Add to Work Pending qty of Shipping schedule information file (Dnsortingplan) for Pending allocation qty of <BR>
 *            Pending information Instance(Shortage).  <BR>
 *           - Work Status flag : When the total of Shortage qty and Actual qty is Plan qty or more, Changes to [Completed]. <BR>
 *            In the following cases, changes to [Partially Completed] for Status flag [Stand by].  <BR>
 *           - Last updated date and time : System date <BR>
 *           - Last update Processing name : Processing name <BR>
 *          </DIR>
 *        </DIR>      
 *     </DIR>
 *     <BR>
 *     10.Results information (Dnresult) making Processing <BR>
 *     <DIR>
 *       - Make results file (Dnresult) which corresponds to the subtraction newly.  <BR>
 *       - When the following items are excluded, Content of registration of the item acquires and makes the <BR>
 *        item from Work information file (Dnworkinfo) which makes newly. <BR>
 *        <DIR>
 *        <Registered information> <BR>
 *          <DIR>
 *           - Work flag : Receiving  <BR>
 *           - Registration date and time : System date <BR>
 *           - Registration Process name : Processing name <BR>
 *           - Last updated date and time : System date <BR>
 *           - Last update Processing name : Processing name <BR>
 *          </DIR>
 *        </DIR>
 *     </DIR>
 *     <BR>
 *     11.Next work information (Dnnextproc) update Processing <BR>
 *     <DIR>
 *       - Based on Cahin information Instance(Shortage) and retrieved Work information file (Dnworkinfo) <BR>
 *        Update next work information file (Dnnextproc).  <BR>
 *        <DIR>
 *        <Update condition> <BR>
 *          <DIR>
 *           - Pending information Instance(Shortage) matches with Next work information unique key <BR>
 *          </DIR>
 *        <Content of update> <BR>
 *          <DIR>
 *           - Work plan qty <BR>
 *            Subtract it from Work plan qty of Next work information file (Dnnextproc) for Pending allocation qty of <BR>
 *            Pending information Instance(Shortage).  <BR>
 *           - Status flag : Change to [Completed] when Work plan qty becomes 0 or less.  <BR>
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
 * <TR><TD>2004/12/03</TD><TD>K.Toda</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Date: 2006/11/07 06:11:16 $
 * @author  $Author: suresh $
 */
public class ShortageOperator extends Object
{
	//#CM644706
	//	Class variables -----------------------------------------------
	//#CM644707
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM644708
	/**
	 * Processing name
	 */
	private static String wProcessName = "ShortageOperator";
	
	//#CM644709
	/** Work No. */

	private String wJobNo;

	//#CM644710
	/** Work process qty */

	private int wWorkPtn = 5;
	
	//#CM644711
	/**
	 * Connection Object
	 */
	Connection wConn = null;

	//#CM644712
	/**
	 * Stock information handler
	 */
	StockHandler wStockHandler = null;

	//#CM644713
	/**
	 * Stock information Retrieval key
	 */
	StockSearchKey wStockKey = null;

	//#CM644714
	/**
	 * Stock information update key
	 */
	StockAlterKey wStockAltKey = null;

	//#CM644715
	/**
	 * Stock control package existence
	 * True : Yes False : No
	 */
	boolean wExistStockPack = false;

	//#CM644716
	/**
	 * Shipping Package available
	 * True : Yes False : No
	 */
	boolean wExistShippingPack = false;
	
	//#CM644717
	/**
	 * Work date of eWareNavi System
	 */
	String wWmsWorkDate = null;

	//#CM644718
	/**
	 * Work information Handler
	 */
	WorkingInformationHandler wWorkHandler = null;

	//#CM644719
	/**
	 * Work Information Retrieval key
	 */
	WorkingInformationSearchKey wWorkKey = null;

	//#CM644720
	/**
	 * Work information update key
	 */
	WorkingInformationAlterKey wWorkAltKey = null;

	//#CM644721
	/**
	 * Next work information Handler
	 */
	NextProcessInfoHandler wNextHandler = null;

	//#CM644722
	/**
	 * Next work Information Retrieval key
	 */
	NextProcessInfoSearchKey wNextKey = null;

	//#CM644723
	/**
	 * Next work information update key
	 */
	NextProcessInfoAlterKey wNextAltKey = null;
	
	//#CM644724
	/**
	 * Sorting Plan Information Handler
	 */
	SortingPlanHandler wSortingPlanHandler = null;

	//#CM644725
	/**
	 * Sorting Plan Information Retrieval key
	 */
	SortingPlanSearchKey wSortingPlanKey = null;

	//#CM644726
	/**
	 * Sorting Plan Information update key
	 */
	SortingPlanAlterKey wSortingPlanAltKey = null;

	//#CM644727
	/**
	 * Shipping Plan Information Handler
	 */
	ShippingPlanHandler wShippingPlanHandler = null;

	//#CM644728
	/**
	 * Shipping Plan Information Retrieval key
	 */
	ShippingPlanSearchKey wShippingPlanKey = null;

	//#CM644729
	/**
	 * Shipping Plan Information update key
	 */
	ShippingPlanAlterKey wShippingPlanAltKey = null;

	//#CM644730
	/**
	 * Sequence Handler
	 */
	SequenceHandler wSequenceHandler = null;

	//#CM644731
	// Class method --------------------------------------------------
	//#CM644732
	/**
	 * Return Version of this Class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:11:16 $");
	}
	
	//#CM644733
	// Constructors --------------------------------------------------
	//#CM644734
	/**
	 * Initialize this Class. 
	 * 
	 * @param conn Database Connection Object
	 * @throws ScheduleException It is notified when the exception not anticipated in check Processing is generated. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 */
	public ShortageOperator(Connection conn) throws ScheduleException, ReadWriteException
	{
		wConn = conn;
		
		//#CM644735
		// Set introduction Package and the work day. 
		setWareNaviSystem(conn);
		
		wStockHandler = new StockHandler(conn);
		wStockKey = new StockSearchKey();
		wStockAltKey = new StockAlterKey();

		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();
		wWorkAltKey = new WorkingInformationAlterKey();

		wNextHandler = new NextProcessInfoHandler(conn);
		wNextKey = new NextProcessInfoSearchKey();
		wNextAltKey = new NextProcessInfoAlterKey();
		
		wSortingPlanHandler = new SortingPlanHandler(conn);
		wSortingPlanKey = new SortingPlanSearchKey() ;
		wSortingPlanAltKey = new SortingPlanAlterKey();
		
		wShippingPlanHandler = new ShippingPlanHandler(conn);
		wShippingPlanKey = new ShippingPlanSearchKey() ;
		wShippingPlanAltKey = new ShippingPlanAlterKey();		
		
		wSequenceHandler = new SequenceHandler(conn);

	}

	//#CM644736
	// Public methods ------------------------------------------------
	//#CM644737
	/**
	 * Call this Method by object Work information extraction Processing.  
	 * @param sKey Retrieval key edit of Work information file
	 * @param pShortage   Pending Information Instance
	 * @param pWorkKind    Work flag
	 * @return True when Object Work information can be extracted,False when it is not possible to extract it
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database.  <BR>
	 */ 
	protected boolean selectWorkOrder(WorkingInformationSearchKey sKey, Shortage pShortage, String pWorkKind)
																					throws ReadWriteException
	{
		//#CM644738
		// Divide the work based on Work flag
		//#CM644739
		// Receiving 
		if(pWorkKind.equals(SystemDefine.JOB_TYPE_INSTOCK))
		{
			sKey.KeyClear() ;
			sKey.setPlanUkey(pShortage.getInstockPlanUkey());
			sKey.setPlanEnableQty(0, "!=");
			
			sKey.setPlanUkeyOrder(1, false);
			sKey.setRegistDateOrder(2, false) ;
			sKey.setJobNoOrder(3, false) ;
		}
		//#CM644740
		// Storage 
		else if(pWorkKind.equals(SystemDefine.JOB_TYPE_STORAGE))
		{
			sKey.KeyClear() ;
			sKey.setPlanUkey(pShortage.getStoragePlanUkey());
			sKey.setPlanEnableQty(0, "!=");

			sKey.setPlanUkeyOrder(1, false);
			sKey.setRegistDateOrder(2, false) ;
			sKey.setJobNoOrder(3, false) ;
		}
		//#CM644741
		// Picking 
		else if(pWorkKind.equals(SystemDefine.JOB_TYPE_RETRIEVAL))
		{
			sKey.KeyClear() ;
			sKey.setPlanUkey(pShortage.getRetrievalPlanUkey());
			sKey.setPlanEnableQty(0, "!=");

			sKey.setPlanUkeyOrder(1, false);
			sKey.setRegistDateOrder(2, false) ;
			sKey.setJobNoOrder(3, false) ;
		}
		//#CM644742
		// Sorting 
		else if(pWorkKind.equals(SystemDefine.JOB_TYPE_SORTING))
		{
			sKey.KeyClear() ;
			sKey.setPlanUkey(pShortage.getSortingPlanUkey());
			sKey.setPlanEnableQty(0, "!=");

			sKey.setPlanUkeyOrder(1, false);			
			sKey.setRegistDateOrder(2, false) ;
			sKey.setJobNoOrder(3, false) ;
		}
		//#CM644743
		// Shipping 
		else if(pWorkKind.equals(SystemDefine.JOB_TYPE_SHIPINSPECTION))
		{
			sKey.KeyClear() ;
			sKey.setPlanUkey(pShortage.getShippingPlanUkey());
			sKey.setPlanEnableQty(0, "!=");

			sKey.setPlanUkeyOrder(1, false);
			sKey.setRegistDateOrder(2, false) ;
			sKey.setJobNoOrder(3, false) ;
		}
		else
		{
			return false ;		
		}
		return true;
	}

	//#CM644744
	/**
	 * Do Pending update Processing.
	 * @param pShortage    Pending Information Instance
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 * @throws ScheduleException It is notified when the exception not anticipated in check Processing is generated. 
	 * @return True at the normal termination. , False at abnormal or Error.
	 */
	public boolean UpdateShortage(Shortage pShortage)
		throws ReadWriteException, ScheduleException
	{
		try
		{
			//#CM644745
			// When Pending information (Shortage) Instance data is null
			//#CM644746
			// Do not do the following Processing.
			if (pShortage == null)
			{
				return true ;
			}
			
			//#CM644747
			// When Pending allocation qty of Pending information (Shortage) Instance data is 0
			//#CM644748
			// Do not do the following Processing.
			if (pShortage.getInsufficientQty() == 0)
			{
				return true ;
			}
			
			//#CM644749
			// It loops the working line. 
			for (int work_koutei = 0; work_koutei < wWorkPtn; work_koutei++)
			{
				//#CM644750
				// Work information retrieval Processing
				String jobType = null;
				if (work_koutei == 0)			// 入荷 
				{
					if (StringUtil.isBlank(pShortage.getInstockPlanUkey()))
						continue;
					jobType = SystemDefine.JOB_TYPE_INSTOCK ;
				}
				else if (work_koutei == 1)		// 入庫
				{
					if (StringUtil.isBlank(pShortage.getStoragePlanUkey()))
						continue;
					jobType = SystemDefine.JOB_TYPE_STORAGE ;
				}	
				else if (work_koutei == 2)		// 出庫
				{
					if (StringUtil.isBlank(pShortage.getRetrievalPlanUkey()))
						continue;
					jobType = SystemDefine.JOB_TYPE_RETRIEVAL ;
				}	
				else if (work_koutei == 3)		// 仕分
				{
					if (StringUtil.isBlank(pShortage.getSortingPlanUkey()))
						continue;
					jobType = SystemDefine.JOB_TYPE_SORTING ;
				}	
				else if (work_koutei == 4)		// 出荷
				{
					if (StringUtil.isBlank(pShortage.getShippingPlanUkey()))
						continue;
					jobType = SystemDefine.JOB_TYPE_SHIPINSPECTION ;
				}	

				//#CM644751
				// Work information retrieval Processing
				//#CM644752
				// Set the order of reading by using The acquisition order Method.
				boolean rStatus = this.selectWorkOrder(wWorkKey, pShortage, jobType);
				//#CM644753
				// Do not do the following Processing in case of Search condition Error. 
				if (rStatus == false)
					return true;
					
				//#CM644754
				// Read Work information by above mentioned Acquisition condition. (exclusive reading)
				WorkingInformation[] rWork = (WorkingInformation[]) wWorkHandler.findForUpdate(wWorkKey);
				
				//#CM644755
				// Retain information on Pending information Instance in work. 
				//#CM644756
				// Actual shortage qty(Generation when post-processing preceding Completed is done)
				int shortageQty = pShortage.getShortageQty() ;
				//#CM644757
				// Pending allocation qty to Plan Information
				int insuffQty = pShortage.getInsufficientQty() ;
				//#CM644758
				// Possible work qty in Work information
				int enableQty = 0;
				
				//#CM644759
				// Work information is read. 
				for (int slc = 0; slc < rWork.length; slc++)
				{
					//#CM644760
					// It comes off Processing when Pending allocation qty becomes 0 or less. 
					if (insuffQty <= 0)
						break ;

					//#CM644761
					// Renew Actual shortage qty and Work information of Pending information (Shortage) Instance. 
					//#CM644762
					// When work status of work information is [Stand by] or [Started] or [Working]
					if (rWork[slc].getStatusFlag().equals(SystemDefine.STATUS_FLAG_UNSTART) ||
						rWork[slc].getStatusFlag().equals(SystemDefine.STATUS_FLAG_START) ||
						rWork[slc].getStatusFlag().equals(SystemDefine.STATUS_FLAG_NOWWORKING))
					{
						//#CM644763
						// Compare it with Actual shortage qty and Possible work qty of Pending information Instance. 
						if (shortageQty >= rWork[slc].getPlanEnableQty())
						{
							//#CM644764
							// Current Possible work qty
							enableQty = rWork[slc].getPlanEnableQty();
						}
						else
						{
							//#CM644765
							// Current Possible work qty
							enableQty = rWork[slc].getPlanEnableQty();
							//#CM644766
							// Actual shortage qty is assumed to be 0 for Actual shortage qty<Possible work qty. 
							shortageQty = 0 ;
						}
					}
					//#CM644767
					// When the work status of Work information is [Completed] 
					else if (rWork[slc].getStatusFlag().equals(SystemDefine.STATUS_FLAG_COMPLETION))
					{
						//#CM644768
						// Compare Actual shortage qty of Pending information Instance with (Possible work qty - Reserved work qty). 
						//#CM644769
						// When Work information is Completed, it becomes Possible work qty -Reserved work qty = Actual shortage qty. 
						if (shortageQty >= (rWork[slc].getPlanEnableQty() - rWork[slc].getPendingQty()))
						{
							//#CM644770
							// The skipped qty is subtracted from the actual qty. 
							shortageQty -= (rWork[slc].getPlanEnableQty() - rWork[slc].getPendingQty()) ;
							//#CM644771
							// Reading Work information does not do Processing this time. 
							continue ;
						}
						else
						{
							//#CM644772
							// Current work qty (Skipping is subtracted.) 
							enableQty = (rWork[slc].getPlanEnableQty() - rWork[slc].getPendingQty()) - shortageQty ;
							//#CM644773
							// It is assumed 0 for Actual shortage qty < Possible work qty. 
							shortageQty = 0 ;
							//#CM644774
							// Subtract a part of work from Pending allocation qty. 
							insuffQty -= enableQty ;
							//#CM644775
							// Reading Work information does not do Processing this time. 
							continue ;
						}
					}
					
					//#CM644776
					// Possible work qty after Completed
					int zanCompQty = 0;
					//#CM644777
					// Shortage qty allocated to this work
					int workShortageQty = 0;
					
					//#CM644778
					// Compare Pending allocation qty to Plan Information of Pending information Instance and Possible work qty this time. 
					//#CM644779
					// Use that the value as small as Processing qty. 
					if (insuffQty < enableQty)
					{
						//#CM644780
						// Possible work qty after Completed = Current Possible work qty  -  Pending allocation qty of Pending Information Instance
						zanCompQty = enableQty - insuffQty ; 		
						//#CM644781
						// Use Pending allocation qty as Current work qty. 
						workShortageQty = insuffQty ;
					}
					else
					{
						//#CM644782
						// Shortage qty allocated to this work = All Possible work qty
						workShortageQty = enableQty;
						//#CM644783
						// Remaining qty becomes 0 because it is used for all parts Completed this time. 
						zanCompQty = 0 ; 
					}
					
					//#CM644784
					// Subtract Shortage qty allocated from Pending allocation qty. 
					insuffQty = insuffQty - workShortageQty ;

					//#CM644785
					// Work information update Processing(Pending subtraction Process from former Work information)
					wWorkAltKey.KeyClear();
					wWorkAltKey.setJobNo(rWork[slc].getJobNo());
					wWorkAltKey.updatePlanEnableQty(zanCompQty);
					//#CM644786
					// Update work status to [deletion] when Possible work qty of Work information is 0 or less. 
					if (zanCompQty <= 0)
					{
						wWorkAltKey.updateStatusFlag(SystemDefine.STATUS_FLAG_DELETE);
					}
					wWorkAltKey.updateLastUpdatePname(wProcessName);
					wWorkHandler.modify(wWorkAltKey);

					//#CM644787
					// Maintain Work information for making this time. (To make results information)
					WorkingInformation shortageWorkInfo = null;
					//#CM644788
					// Work information registration Processing(The new subtraction is registered as Pending. )
					shortageWorkInfo = createWorkInfo(wConn,
										workShortageQty,
										wProcessName,
										rWork[slc]);
					if (shortageWorkInfo == null)
						return false;
							
					//#CM644789
					// Stock information retrieval Processing(Acquire Stock qty and Drawing qty. ) 
					wStockKey.KeyClear();
					wStockKey.setStockId(rWork[slc].getStockId());
					Stock[] rStock = (Stock[]) wStockHandler.find(wStockKey);
					
					//#CM644790
					// Stock information update Processing
					//#CM644791
					// Stock qty and Drawing qty calculation
					//#CM644792
					// Subtract it from each value for Processing this time. 
					wStockAltKey.KeyClear();
					wStockAltKey.setStockId(rWork[slc].getStockId());
					wStockAltKey.updateStockQty(rStock[0].getStockQty() - workShortageQty);
					wStockAltKey.updateAllocationQty(rStock[0].getAllocationQty() - workShortageQty);
					wStockAltKey.updateLastUpdatePname(wProcessName);
					//#CM644793
					// Make the status to [Completed] if the number of drawing is 0 when Processing is Picking , Sorting or Shipping. 
					if (work_koutei == 2 || work_koutei == 3 || work_koutei == 4)
					{
						if ((rStock[0].getAllocationQty() - workShortageQty) <= 0)
							wStockAltKey.updateStatusFlag(Stock.STOCK_STATUSFLAG_COMPLETE);
					}
					wStockHandler.modify(wStockAltKey);

					//#CM644794
					// Sorting Plan unique key exists in Instance. 
					//#CM644795
					// Do following Processing data when current Processing is [Sorting]. 
					if (!StringUtil.isBlank(pShortage.getSortingPlanUkey()) &&
						work_koutei == 3)
					{
						//#CM644796
						// Sorting Plan Information retrieval Processing(Acquire Shortage qty) 
						wSortingPlanKey.KeyClear(); 
						wSortingPlanKey.setSortingPlanUkey(pShortage.getSortingPlanUkey());
						SortingPlan[] rSortingPlan = (SortingPlan[]) wSortingPlanHandler.find(wSortingPlanKey);

						//#CM644797
						// Sorting Plan Information update Processing
						//#CM644798
						// Add the current Processing number to Sorting Shortage qty.
						wSortingPlanAltKey.KeyClear();
						wSortingPlanAltKey.setSortingPlanUkey(pShortage.getSortingPlanUkey());
						wSortingPlanAltKey.updateShortageCnt(rSortingPlan[0].getShortageCnt() + workShortageQty);
						wSortingPlanAltKey.updateLastUpdatePname(wProcessName);
						//#CM644799
						// [Completed] when Plan qty of Sorting Plan Information <= (Actual qty + Shortage qty)
						if (rSortingPlan[0].getPlanQty() <= (rSortingPlan[0].getResultQty() + rSortingPlan[0].getShortageCnt() + workShortageQty))
						{
							wSortingPlanAltKey.updateStatusFlag(SortingPlan.STATUS_FLAG_COMPLETION);
						}
						//#CM644800
						// When Plan qty of Sorting Plan Information > (Actual qty + Shortage qty)
						//#CM644801
						// When the status of retrieved Sorting Plan Information is [Stand by], it is assumed as [Partially Completed].
						else if (rSortingPlan[0].getStatusFlag().equals(SortingPlan.STATUS_FLAG_UNSTART))
						{
							wSortingPlanAltKey.updateStatusFlag(SortingPlan.STATUS_FLAG_COMPLETE_IN_PART);
						}
						wSortingPlanHandler.modify(wSortingPlanAltKey);
						
					}

					//#CM644802
					// Shipping Plan unique key exists in Instance. 
					//#CM644803
					// Do following Processing when data of current Processing is Shipping.
					if (!StringUtil.isBlank(pShortage.getShippingPlanUkey()) &&
						work_koutei == 4)
					{
						//#CM644804
						// Shipping Plan Information retrieval Processing (Acquire Shortage qty) 
						wShippingPlanKey.KeyClear();
						wShippingPlanKey.setShippingPlanUkey(pShortage.getShippingPlanUkey());
						ShippingPlan[] rShippingPlan = (ShippingPlan[]) wShippingPlanHandler.find(wShippingPlanKey);
						
						//#CM644805
						// Shipping Plan Information update Processing
						//#CM644806
						// Add the Processing number to Shipping Shortage qty this time. 
						wShippingPlanAltKey.KeyClear();
						wShippingPlanAltKey.setShippingPlanUkey(pShortage.getShippingPlanUkey());
						wShippingPlanAltKey.updateShortageCnt(rShippingPlan[0].getShortageCnt() + workShortageQty);
						wShippingPlanAltKey.updateLastUpdatePname(wProcessName);
						//#CM644807
						// [Completed] when Plan qty of Shipping Plan Information <= (Actual qty + Shortage qty)
						if (rShippingPlan[0].getPlanQty() <= (rShippingPlan[0].getResultQty() + rShippingPlan[0].getShortageCnt() + workShortageQty))
						{
							wShippingPlanAltKey.updateStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETION);
						}
						//#CM644808
						// When Plan qty of Shipping Plan Information > (Actual qty + Shortage qty)
						//#CM644809
						// When the status of retrieved Shipping Plan Information is [Stand by], it is assumed [Partially Completed]. 
						else if (rShippingPlan[0].getStatusFlag().equals(SortingPlan.STATUS_FLAG_UNSTART))
						{
							wShippingPlanAltKey.updateStatusFlag(ShippingPlan.STATUS_FLAG_COMPLETE_IN_PART);
						}
						wShippingPlanHandler.modify(wShippingPlanAltKey);
						
						
						//#CM644810
						// Acquire Sorting unique key from Next work information of Receiving -> Sorting -> Shipping
						//#CM644811
						// Continue summing up of Shortage qty in Next work information of Sorting -> Shipping
						if (!StringUtil.isBlank(pShortage.getSortingPlanUkey()) &&
							!pShortage.getSortingPlanUkey().equals(SystemDefine.PLAN_UKEY_DUMMY))
						{
							wNextKey.KeyClear();
							wNextKey.setPlanUkey(pShortage.getSortingPlanUkey());
							wNextKey.setShippingPlanUkey(pShortage.getShippingPlanUkey());
							NextProcessInfo nextProc = (NextProcessInfo) wNextHandler.findPrimaryForUpdate(wNextKey);
				
							if (nextProc != null)
							{
								//#CM644812
								// Sum up Shortage qty to Next work information of Sorting -> Shipping.
								wNextAltKey.KeyClear();
								wNextAltKey.setNextProcUkey(nextProc.getNextProcUkey());
								wNextAltKey.updateShortageQty(nextProc.getShortageQty() + workShortageQty);
								wNextAltKey.updateLastUpdatePname(wProcessName);
								wNextHandler.modify(wNextAltKey);
							}
						}
						
					}
				
					//#CM644813
					// Results information making Processing
					if (!createHostsend(wConn,
						shortageWorkInfo))
						return false ;
	
				}
			}

			//#CM644814
			// Next Work information retrieval Processing
			wNextKey.KeyClear();
			wNextKey.setNextProcUkey(pShortage.getNextProcUkey());
			NextProcessInfo[] rNext = (NextProcessInfo[]) wNextHandler.findForUpdate(wNextKey);

			//#CM644815
			// Next Work information update Processing				
			//#CM644816
			// Add a part of current Processing to Actual shortage qty. 
			wNextAltKey.KeyClear();
			wNextAltKey.setNextProcUkey(pShortage.getNextProcUkey());
			wNextAltKey.updateShortageQty(rNext[0].getShortageQty() + pShortage.getInsufficientQty());
			wNextAltKey.updateLastUpdatePname(wProcessName);
			//#CM644817
			// [Completed] when Work plan qty of Next work information <= (Work actual qty + Work Pending qty)
			if (rNext[0].getPlanQty() <= (rNext[0].getResultQty() + rNext[0].getShortageQty() + pShortage.getInsufficientQty()))
			{
				//#CM644818
				// Change the Status to [Completed]
				wNextAltKey.updateStatusFlag(NextProcessInfo.STATUS_FLAG_PROCESSING_FINISH);
			}
			//#CM644819
			// [Processing] when Work plan qty of Next work information > (Work actual qty + Work Pending qty)
			else
			{
				//#CM644820
				// Change the Status to [Processing]
				wNextAltKey.updateStatusFlag(NextProcessInfo.STATUS_FLAG_PROCESSING);
			}
			wNextHandler.modify(wNextAltKey);
			
			return true;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (InvalidDefineException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}

	//#CM644821
	// Package methods -----------------------------------------------

	//#CM644822
	// Protected methods ---------------------------------------------

	//#CM644823
	/**
	 * Set none of stock control Package and Shipping Package referring to system definition table (WARENAVI_SYSTEM). <BR>
	 * 
	 * @param conn Database Connection Object
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 * @throws ScheduleException It is notified when the exception not anticipated in check Processing is generated. 
	 */
	private void setWareNaviSystem(Connection conn)
		throws ReadWriteException, ScheduleException
	{

		WareNaviSystem wms = new WareNaviSystem();

		WareNaviSystemSearchKey skey = new WareNaviSystemSearchKey();
		WareNaviSystemHandler wnhdl = new WareNaviSystemHandler(conn);

		try
		{
			wms = (WareNaviSystem) wnhdl.findPrimary(skey);
			if (wms == null)
			{
				//#CM644824
				//6006002 = Database Error was generated.{0}
				RmiMsgLogClient.write(6006039, LogMessage.F_ERROR, "WareNaviSystemHandler", null);
				throw (new ScheduleException("6007039" + wDelim + "WARENAVI_SYSTEM"));
			}
			
			//#CM644825
			// Acquire the day of the work of WMS. 
			wWmsWorkDate = wms.getWorkDate();
			
			//#CM644826
			// When Stock package is available
			if (wms.getStockPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
				wExistStockPack = true;
			//#CM644827
			// When Shipping Package is available
			if (wms.getShippingPack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
				wExistShippingPack = true;

		}
		catch (NoPrimaryException e)
		{
			throw new ScheduleException(e.getMessage());
		}
	}

	//#CM644828
	/**
	 * Register results transmission information table (DNHOSTSEND).  <BR> 
	 * <BR>     
	 * Register results transmission information based on the content of received Parameter. <BR>
	 * <BR>
	 * @param conn        Instance which maintains Connection with Database. 
	 * @param wkinfo      Instance of Work information file. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 * @return Return true when it is possible to register in the results transmission information table normally. 
	 */
	protected boolean createHostsend(
		Connection conn,
		WorkingInformation wkinfo)
		throws ReadWriteException
	{
		try
		{
			HostSendHandler hostsendHandler = new HostSendHandler(conn);
			HostSend hostsend = new HostSend();

			//#CM644829
			// Acquire each registered unique key. 
			SequenceHandler sequence = new SequenceHandler(conn);
			//#CM644830
			// Work date
			hostsend.setWorkDate(wWmsWorkDate);
			//#CM644831
			// Work No.
			hostsend.setJobNo(wJobNo);
			//#CM644832
			// Work flag
			hostsend.setJobType(wkinfo.getJobType());
			//#CM644833
			// Consolidating Work No.(Same Work No.)
			hostsend.setCollectJobNo(wJobNo);
			//#CM644834
			// Status flag(Completed)
			hostsend.setStatusFlag(wkinfo.getStatusFlag());
			//#CM644835
			// Work Start flag(Started)
			hostsend.setBeginningFlag(wkinfo.getBeginningFlag());
			//#CM644836
			// Plan unique key
			hostsend.setPlanUkey(wkinfo.getPlanUkey());
			//#CM644837
			// Stock ID
			hostsend.setStockId(wkinfo.getStockId());
			//#CM644838
			//  AreaNo.
			hostsend.setAreaNo(wkinfo.getAreaNo());
			//#CM644839
			// Location No.
			hostsend.setLocationNo(wkinfo.getLocationNo());
			//#CM644840
			// Plan date
			hostsend.setPlanDate(wkinfo.getPlanDate());
			//#CM644841
			// Consignor Code
			hostsend.setConsignorCode(wkinfo.getConsignorCode());
			//#CM644842
			// Consignor Name
			hostsend.setConsignorName(wkinfo.getConsignorName());
			//#CM644843
			// Supplier ID
			hostsend.setSupplierCode(wkinfo.getSupplierCode());
			//#CM644844
			// Supplier Name
			hostsend.setSupplierName1(wkinfo.getSupplierName1());
			//#CM644845
			// Receiving slip No.
			hostsend.setInstockTicketNo(wkinfo.getInstockTicketNo());
			//#CM644846
			// Receiving slip lineNo.
			hostsend.setInstockLineNo(wkinfo.getInstockLineNo());
			//#CM644847
			// Customer Code
			hostsend.setCustomerCode(wkinfo.getCustomerCode());
			//#CM644848
			// Customer Name
			hostsend.setCustomerName1(wkinfo.getCustomerName1());
			//#CM644849
			// Shipping line No.
			hostsend.setShippingTicketNo(wkinfo.getShippingTicketNo());
			//#CM644850
			// Shipping slip lineNo.
			hostsend.setShippingLineNo(wkinfo.getShippingLineNo());
			//#CM644851
			// Item Code
			hostsend.setItemCode(wkinfo.getItemCode());
			//#CM644852
			// Item Name
			hostsend.setItemName1(wkinfo.getItemName1());
			//#CM644853
			// Work plan qty(Host Plan qty)
			hostsend.setHostPlanQty(wkinfo.getHostPlanQty());
			//#CM644854
			// Work plan qty
			hostsend.setPlanQty(wkinfo.getPlanQty());
			//#CM644855
			// Possible work qty
			hostsend.setPlanEnableQty(wkinfo.getPlanEnableQty());
			//#CM644856
			// Work actual qty
			hostsend.setResultQty(wkinfo.getResultQty());
			//#CM644857
			// Work Pending qty
			hostsend.setShortageCnt(wkinfo.getShortageCnt());
			//#CM644858
			// Reserved qty
			hostsend.setPendingQty(wkinfo.getPendingQty());
			//#CM644859
			// Packed qty per case
			hostsend.setEnteringQty(wkinfo.getEnteringQty());
			//#CM644860
			// Packed qty per bundle
			hostsend.setBundleEnteringQty(wkinfo.getBundleEnteringQty());
			//#CM644861
			// Case/Piece flag (Mode of packing)
			hostsend.setCasePieceFlag(wkinfo.getCasePieceFlag());
			//#CM644862
			// Case/Piece flag (Work form) 
			hostsend.setWorkFormFlag(wkinfo.getCasePieceFlag());
			//#CM644863
			// Case ITF
			hostsend.setItf(wkinfo.getItf());
			//#CM644864
			// Bundle ITF
			hostsend.setBundleItf(wkinfo.getBundleItf());
			//#CM644865
			// TC/DC flag
			hostsend.setTcdcFlag(wkinfo.getTcdcFlag());
			//#CM644866
			// Expiry date
			hostsend.setUseByDate(wkinfo.getUseByDate());
			//#CM644867
			// Lot No.
			hostsend.setLotNo(wkinfo.getLotNo());
			//#CM644868
			// Plan information comment
			hostsend.setPlanInformation(wkinfo.getPlanInformation());
			//#CM644869
			// Order No. 
			hostsend.setOrderNo(wkinfo.getOrderNo());
			//#CM644870
			// Day of order
			hostsend.setOrderingDate(wkinfo.getOrderingDate());
			//#CM644871
			// Expiry date
			hostsend.setResultUseByDate(wkinfo.getResultUseByDate());
			//#CM644872
			// Lot No.
			hostsend.setResultLotNo(wkinfo.getResultLotNo());
			//#CM644873
			// Work result Location
			hostsend.setResultLocationNo(wkinfo.getResultLocationNo());
			//#CM644874
			// Work report flag (Unreported)
			hostsend.setReportFlag(HostSend.REPORT_FLAG_NOT_SENT);
			//#CM644875
			// Batch No.(ScheduleNo.)
			hostsend.setBatchNo(wkinfo.getBatchNo());
			//#CM644876
			// Worker Code
			hostsend.setWorkerCode(wkinfo.getWorkerCode());
			//#CM644877
			// Worker Name
			hostsend.setWorkerName(wkinfo.getWorkerName());
			//#CM644878
			// Terminal No, RFTNo.
			hostsend.setTerminalNo(wkinfo.getTerminalNo());
			//#CM644879
			// Plan information Registration date and time
			hostsend.setPlanRegistDate(wkinfo.getPlanRegistDate());
			//#CM644880
			// Registration Process name
			hostsend.setRegistPname(wkinfo.getRegistPname());
			//#CM644881
			// Last update Processing name
			hostsend.setLastUpdatePname(wkinfo.getLastUpdatePname());

			//#CM644882
			// Registration of data
			hostsendHandler.create(hostsend);

			return true;

		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
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

	//#CM644883
	/**
	 * Register the Work information table. 
	 * @param  conn        Instance which maintains Connection with Database. 
	 * @param  insffqty    Shortage qty
	 * @param  processname Processing name
	 * @param  workinfo Work informationInstance
	 * @return WorkingInformation Registered Work information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with Database. 
	 * @throws DataExistsException Notified when same information had already been registered.
	 */
	protected WorkingInformation createWorkInfo(
		Connection conn,
		int insffQty,
		String processname,
		WorkingInformation workinfo)
		throws ReadWriteException, DataExistsException
	{
		
		try
		{
			WorkingInformation base_workinfo = new WorkingInformation();

			//#CM644884
			// Work No.
			wJobNo = wSequenceHandler.nextJobNo();
			base_workinfo.setJobNo(wJobNo);
			//#CM644885
			// Work flag (Picking ) 
			base_workinfo.setJobType(workinfo.getJobType());
			//#CM644886
			// Consolidating Work No.
			base_workinfo.setCollectJobNo(wJobNo);
			//#CM644887
			// Status flag (Completed) 
			base_workinfo.setStatusFlag(WorkingInformation.STATUS_FLAG_COMPLETION);
			//#CM644888
			// Work Start flag (Started) 
			base_workinfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM644889
			// Plan unique key
			base_workinfo.setPlanUkey(workinfo.getPlanUkey());
			//#CM644890
			// Stock ID
			base_workinfo.setStockId(workinfo.getStockId());			
			//#CM644891
			//  AreaNo
			base_workinfo.setAreaNo(workinfo.getAreaNo());
			//#CM644892
			// Location No
			base_workinfo.setLocationNo(workinfo.getLocationNo());
			//#CM644893
			// Plan date
			base_workinfo.setPlanDate(workinfo.getPlanDate());
			//#CM644894
			// Consignor Code 
			base_workinfo.setConsignorCode(workinfo.getConsignorCode());
			//#CM644895
			// Consignor Name
			base_workinfo.setConsignorName(workinfo.getConsignorName());
			//#CM644896
			// Supplier ID
			base_workinfo.setSupplierCode(workinfo.getSupplierCode());
			//#CM644897
			// Supplier Name
			base_workinfo.setSupplierName1(workinfo.getSupplierName1());
			//#CM644898
			// Receiving slip No
			base_workinfo.setInstockTicketNo(workinfo.getInstockTicketNo());
			//#CM644899
			// Receiving slip line
			base_workinfo.setInstockLineNo(workinfo.getInstockLineNo());
			//#CM644900
			// Customer Code
			base_workinfo.setCustomerCode(workinfo.getCustomerCode());
			//#CM644901
			// Customer Name
			base_workinfo.setCustomerName1(workinfo.getCustomerName1());
			//#CM644902
			// Shipping line No
			base_workinfo.setShippingTicketNo(workinfo.getShippingTicketNo());
			//#CM644903
			// Shipping slip line
			base_workinfo.setShippingLineNo(workinfo.getShippingLineNo());
			//#CM644904
			// Item Code
			base_workinfo.setItemCode(workinfo.getItemCode());
			//#CM644905
			// Item Name
			base_workinfo.setItemName1(workinfo.getItemName1());
			//#CM644906
			// Host Plan qty
			base_workinfo.setHostPlanQty(workinfo.getHostPlanQty());
			//#CM644907
			// Work plan qty
			base_workinfo.setPlanQty(workinfo.getPlanQty());
			//#CM644908
			// Possible work qty
			base_workinfo.setPlanEnableQty(insffQty);
			//#CM644909
			// Actual qty
			base_workinfo.setResultQty(0);
			//#CM644910
			// Shortage qty
			base_workinfo.setShortageCnt(insffQty);
			//#CM644911
			// Reserved qty
			base_workinfo.setPendingQty(0);
			//#CM644912
			// Packed qty per case
			base_workinfo.setEnteringQty(workinfo.getEnteringQty());
			//#CM644913
			// Packed qty per bundle
			base_workinfo.setBundleEnteringQty(workinfo.getBundleEnteringQty());
			//#CM644914
			// Case/Piece flag : Mode of packing
			base_workinfo.setCasePieceFlag(workinfo.getCasePieceFlag());
			//#CM644915
			// Case/Piece flag : Work form
			base_workinfo.setWorkFormFlag(workinfo.getWorkFormFlag());
			//#CM644916
			// Case ITF
			base_workinfo.setItf(workinfo.getItf());
			//#CM644917
			// Bundle ITF
			base_workinfo.setBundleItf(workinfo.getBundleItf());
			//#CM644918
			// TC/DC flag (DC) 
			base_workinfo.setTcdcFlag(workinfo.getTcdcFlag());
			//#CM644919
			// Day of Expiry date
			base_workinfo.setUseByDate(workinfo.getUseByDate());
			//#CM644920
			// Lot No.
			base_workinfo.setLotNo(workinfo.getLotNo());
			//#CM644921
			// Plan information comment
			base_workinfo.setPlanInformation(workinfo.getPlanInformation());
			//#CM644922
			// Order No
			base_workinfo.setOrderNo(workinfo.getOrderNo());
			//#CM644923
			// Day of order
			base_workinfo.setOrderingDate(workinfo.getOrderingDate());
			//#CM644924
			// Expiry date
			base_workinfo.setResultUseByDate(workinfo.getResultUseByDate());
			//#CM644925
			// Lot No.
			base_workinfo.setResultLotNo(workinfo.getResultLotNo());
			//#CM644926
			// Expiry date
			base_workinfo.setResultUseByDate(workinfo.getResultUseByDate());
			//#CM644927
			// Lot No.
			base_workinfo.setResultLotNo(workinfo.getResultLotNo());
			//#CM644928
			// Work result Location
			base_workinfo.setResultLocationNo(workinfo.getResultLocationNo());
			//#CM644929
			// Stand by report flag
			base_workinfo.setReportFlag(workinfo.getReportFlag());
			//#CM644930
			// Batch No
			base_workinfo.setBatchNo(workinfo.getBatchNo());
			//#CM644931
			// Worker Code
			base_workinfo.setWorkerCode(workinfo.getWorkerCode());
			//#CM644932
			// Worker Name
			base_workinfo.setWorkerName(workinfo.getWorkerName());
			//#CM644933
			// Terminal No
			base_workinfo.setTerminalNo(workinfo.getTerminalNo());
			//#CM644934
			// Plan information Registration date and time
			base_workinfo.setPlanRegistDate(workinfo.getRegistDate());
			//#CM644935
			// Registration Process name
			base_workinfo.setRegistPname(processname);
			//#CM644936
			// Last update Processing name
			base_workinfo.setLastUpdatePname(processname);
			//#CM644937
			// Registration of data
			wWorkHandler.create(base_workinfo);

			return base_workinfo;

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

	//#CM644938
	// Private methods -----------------------------------------------

}
//#CM644939
//end of class
