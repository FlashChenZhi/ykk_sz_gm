package jp.co.daifuku.wms.retrieval.schedule;

// $Id: RetrievalDataLoader.java,v 1.3 2007/02/07 04:19:52 suresh Exp $
//#CM722673
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;
import java.util.Hashtable;
import java.util.Vector;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.RetrievalPlanHandler;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.ShortageInfoHandler;
import jp.co.daifuku.wms.base.entity.RetrievalPlan;
import jp.co.daifuku.wms.base.entity.ShortageInfo;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalDataLoader;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.CsvIllegalDataException;
import jp.co.daifuku.wms.base.utility.DataLoadCsvReader;
import jp.co.daifuku.wms.base.utility.DataLoadStatusCsvFileWriter;
import jp.co.daifuku.wms.base.utility.WareNaviSystemManager;

//#CM722674
/**
 * Designer : M.Inoue <BR>
 * Maker : M.Inoue <BR>
 * <BR>
 * Allow the <CODE>RetrievalDataLoader</CODE> class to execute the process for loading Picking Plan data.<BR>
 * Inherit <CODE>AbstractExternalDataLoader</CODE> abstract class and implement the required processes. <BR>
 * Allow the methods contained in this class to receive the connection object and execute process for updating the database. <BR>
 * In the event of shortage, cancel the allocation (disable to plan, work, or update inventory),
 * generate a shortage info table. <BR>
 * Allow this class to execute the following processes.<BR>
 * <BR>
 * 1. Process for loading data (<CODE>load(Connection conn, WareNaviSystem wns, Parameter searchParam)</CODE> method) <BR>
 * <BR>
 * <DIR>
 *   Receive Connection object, WareNaviSystem object, and Parameter object as a parameter, and 
 *   add the data via the HOSTPicking Plan file (CSV file)<BR>
 *   to the database. (Picking Plan Info and Inventory Info (only when Inventory package is disabled))  <BR>
 *   Generate a Work status by allocation process from the added Picking Plan Info and update the Inventory Info. 
 *   Return false when error occurs in the method. <BR>
 *   Failing to load data due to abnormal input of information, output its file name and the number of lines to the log. <BR>
 *   For data with input information Addedand with status other than Standby or Deleted, skip the data and output this event. <BR>
 *   For data with input information Addedand with status Standby, overwrite and load the data and output this event as a log. <BR>
 *   Use <CODE>getMessage()</CODE> method to refer to the content of the error <BR>
 *   Lock the Work Status Info and Inventory Info for exclusive process at starting the process. <BR>
 *   <BR>
 *   <Parameter> Mandatory Input<BR>
 *   Connection object  <BR>
 *   WareNaviSystem object  <BR>
 *   SystemParameter object  <BR>
 *   <BR>
 *   <Details of processes>  <BR>
 *   1. Generate an Loading History file <BR>
 *     <DIR>
 *     Form the filename with date (YYYYMMDD) + time (HHMISS) + loaded file name concatenated. <BR>
 *     </DIR>
 *   2. Initialize Data count. <BR>
 *     <DIR>
 *     Count of all loaded data, count of submitted/added data, Count of updated data, and count of skipped data <BR>
 *     </DIR>
 *   3.Declare Add Data Flag. <BR>
 *     <DIR>
 *     Declare false. Regard true if one or more entry (added) data exists. <BR>
 *     </DIR>
 *   4. Generate Class Instance. <BR>
 *     <DIR>
 *     - Generate DataLoadCsvReader instance. <BR>
 *     Allow this class to load data from the plan file that is defined in the environment file (EnvironmentInformation). <BR>
 *     <BR>
 *     - Generate a class instance to generate an loaded error file. <BR>
 *     (in the same format as the loaded file, and with the content of the same error as it) <BR>
 *     - Generate the SequenceHandler instance. <BR>
 *     </DIR>
 *   5. Obtain the next one line of the corresponding planned date from the plan file. <BR>
 *     <DIR>
 *     Loop until any data no longer obtained. <BR>
 *     <BR>
 *     - Start looping  -<BR>
 *       <DIR>
 *       - Check for presence of the same data. <BR>
 *         <DIR>
 *         - If there is no same data, add it as a new data. *<BR>
 *         - For the same data existing with status other than "Standby": <BR>
 *           Stop Process as an error <BR>
 *         - For the same data existing with status "Standby": <BR>
 *           Cancel the allocation of the same corresponding data. *<BR>
 *           If there is one or more shortage info corresponding to the Plan unique key of the relevant same data, delete such information. <BR>
 *         </DIR>
 *       - After checking for presence of the same data*, generate respectively the new info as follows. <BR>
 *         Plan Info <BR>
 *         Work Status Info<BR>
 *         Inventory Info <BR>
 *         shortage info (only for occurrence of shortage) <BR>
 *       - Once the shortage info has been created, cancel the allocation and add the shortage info. 
 *       </DIR>
 * 
 *       - Close looping  -<BR>
 *     <BR>
 *     </DIR>
 *   </DIR>
 * <BR>
 * <TABLE BORDER=1 CELLPADDING=3 CELLSPACING=0>
 * <TR BGCOLOR=#CCCCFF CLASS=TableHeadingColor><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/21</TD><TD>M.Inoue</TD><TD>New creation</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:52 $
 * @author  $Author: suresh $
 */
public class RetrievalDataLoader extends AbstractExternalDataLoader
{
	//#CM722675
	// Class fields --------------------------------------------------

	//#CM722676
	// Class variables -----------------------------------------------
	//#CM722677
	/**
	 * Last update process name
	 */
	protected final String pName = "RetrievalDataLoader";
	//#CM722678
	// Class method --------------------------------------------------
	//#CM722679
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:52 $");
	}

	//#CM722680
	// Constructors --------------------------------------------------
	//#CM722681
	/**
	 * Constructor
	 */
	public RetrievalDataLoader()
	{
	}

	//#CM722682
	// Public methods ------------------------------------------------
	//#CM722683
	/**
	 * Receive Connection object and WareNaviSystem object, and SystemParameter object as parameters, and load the Picking Plan data. <BR>
	 * For detailed operations, enable to refer to the section Explanations of Class . <BR>
	 * @param conn Database connection object
	 * @param wns  WareNaviSystem object 
	 * @param searchParam Class that inherits the <CODE>Parameter</CODE> class. 
	 * @return Return true if normally added data, or return false if error occurs. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	public boolean load(Connection conn, WareNaviSystem wns, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		//#CM722684
		// Determine availability of Inventory package. 
		WareNaviSystemManager wms = new WareNaviSystemManager(conn);
		if (wms.isStockPack())
		{
			return createRetrievalPlanStockOn(conn, wns, searchParam);
		}
		else
		{
			return createRetrievalPlanStockOff(conn, wns, searchParam);
		}
	}
	//#CM722685
	// Package methods -----------------------------------------------
	//#CM722686
	/**
	 * Execute the process for checking the field item in the designated data. 
	 * Allow the invoking side to set the error message and write it into the loading error file via the invoking side. 
	 * @param param Input Parameter 
	 * @param operator Operator  
	 * @param wns WareNaviSystem object 
	 * @param DRR DataLoadCsvReader Object 
	 * @param errlist DataLoadStatusCsvFileWriter Object 
	 * @return Return true if normal, or return false when error occurs. 
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected boolean check(RetrievalSupportParameter param, RetrievalPlanOperator operator, WareNaviSystem wns, DataLoadCsvReader DRR, DataLoadStatusCsvFileWriter errlist)
			throws ScheduleException, ReadWriteException
	{
		boolean ret = true;
		int ErrorCode = 0;

		//#CM722687
		// Error if planned qty in the loaded data is 0 or smaller. 
		if (param.getTotalPlanQty() <= 0)
		{
			//#CM722688
			// 6022005=Data with 0 or less Planned Qty. is found. File: {0} Line: {1} 
			ErrorCode = 6022005;
			ret = false;
		}

		//#CM722689
		// For data with no Inventory package and no value set for Piece Item, Picking Location, Case Item, and Picking Location, regard it as error. 
		if (StringUtil.isBlank(param.getPieceLocation()) && StringUtil.isBlank(param.getCaseLocation()) && !wns.getStockPack().equals(WareNaviSystem.OPERATION_FLAG_ADDON))
		{
			//#CM722690
			// 6022006=Data without Piece Picking Location/Case Picking Location set is found. File: {0} Line: {1} 
			ErrorCode = 6022006;
			ret = false;
		}

		//#CM722691
		// For data with Case/Piece division None, disable to commit any Order No. in the Work Status Info. Therefore,
		//#CM722692
		// if values are set for both Case Order No. and Piece Order No., regard them as error. 
		//#CM722693
		// If the same value is set, regard it as normal. 
		if (ret && operator.getCasePieceFlag(param).equals(RetrievalSupportParameter.CASEPIECE_FLAG_NOTHING))
		{		
			if ((!StringUtil.isBlank(param.getCaseOrderNo()) && !StringUtil.isBlank(param.getPieceOrderNo()))
			&& (!param.getCaseOrderNo().equals(param.getPieceOrderNo())))
			{
				//#CM722694
				// 6022007=Wrong settings of Piece Order No./Case Order No. File: {0} Line: {1} 
				ErrorCode = 6022007;
				ret = false;
			}
		//#CM722695
		// Except for data with Case/Piece division "None", 
		//#CM722696
		// regard it as error if values are set for both Piece Order No. and Case Order No., or 
		//#CM722697
		// if any value other than blank are set for both.
		}else{
			if (ret && ((!StringUtil.isBlank(param.getCaseOrderNo()) && StringUtil.isBlank(param.getPieceOrderNo()))
			|| (StringUtil.isBlank(param.getCaseOrderNo()) && !StringUtil.isBlank(param.getPieceOrderNo()))))
			{
				//#CM722698
				// 6022007=Wrong settings of Piece Order No./Case Order No. File: {0} Line: {1} 
				ErrorCode = 6022007;
				ret = false;
			}
		}
		if (!StringUtil.isBlank(param.getCaseOrderNo()) || !StringUtil.isBlank(param.getPieceOrderNo()))
		{
			if (param.getCustomerCode().equals(""))
			{
				//#CM722699
				// 6022008=Order No. is entered. Please enter Customer Code. File: {0} Line: {1} 
				ErrorCode = 6022008;
				ret = false;
			}
		}

		if (!ret)
		{
			//#CM722700
			// Record the process error in the log. 
			String[] msg = { DRR.getFileName(), DRR.getLineNo()};
			setMessage( ErrorCode + wDelim + DRR.getFileName() + wDelim + DRR.getLineNo());
			RmiMsgLogClient.write( ErrorCode, "RetrievalDataLoader", msg );
			//#CM722701
			// Write error contents additionally in the loaded error file generation class. 
			errlist.addStatusCsvFile( DataLoadStatusCsvFileWriter.STATUS_ERROR, DRR.getLineData(), getMessage() );
		}

		return ret;
	}
	
	//#CM722702
	/**
	 * Obtain the path of the Picking Plan file and its file name. 
	 * 
	 * @return Picking Plan file + file name 
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 */
	protected String GetRetrievalFile() throws ScheduleException
	{
		try
		{
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
			String DataLoadPath = IO.get("DATALOAD_FOLDER", "RETRIEVAL_SUPPORT");
			String DataLoadFile = IO.get("DATALOAD_FILENAME", "RETRIEVAL_SUPPORT");
			return DataLoadPath + DataLoadFile;
		}
		catch (ReadWriteException e)
		{
			//#CM722703
			//  Environmental info file was not found.{0} 
			Object[] msg = new Object[1];
			msg[0] = WmsParam.ENVIRONMENT;
			RmiMsgLogClient.write(new TraceHandler(6026004, e), "DataLoadCsvReader", msg);
			throw new ScheduleException();
		}
	}

	//#CM722704
	/**
	 * Set the record of the file for the parameter. 
	 * 
	 * @param reader DataLoadCsvReader Object 
	 * @param bachNo Batch No. 
	 * @param workercode Worker Code
	 * @param workername Worker Name
	 * @param terminal Terminal No.
	 * @return Parameter object that contains Content of record 
	 * @throws CsvIllegalDataException Announce when the length of the field item of the designated key exceeds the defined length. 
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected RetrievalSupportParameter setParameter(DataLoadCsvReader reader, String bachNo, String workercode, String workername, String terminal) 
		throws ReadWriteException, ScheduleException, CsvIllegalDataException
	{

		RetrievalSupportParameter retParam = new RetrievalSupportParameter();
		//#CM722705
		// Planned Picking Date
		if (reader.getEnable("PLAN_DATE"))
			retParam.setRetrievalPlanDate(reader.getDateValue("PLAN_DATE"));
		//#CM722706
		// Translate it into "0" if the Consignor Code field item is disabled or set to blank.
		if (reader.getEnable("CONSIGNOR_CODE"))
		{
			if (!reader.getAsciiValue("CONSIGNOR_CODE").equals(""))
			{
				retParam.setConsignorCode(reader.getAsciiValue("CONSIGNOR_CODE"));
			}
			else
			{
				retParam.setConsignorCode("0");
			}
		}
		else
		{
			retParam.setConsignorCode("0");
		}
		//#CM722707
		// Consignor Name
		if (reader.getEnable("CONSIGNOR_NAME"))
			retParam.setConsignorName(reader.getValue("CONSIGNOR_NAME"));
		//#CM722708
		// Customer Code
		if (reader.getEnable("CUSTOMER_CODE"))
			retParam.setCustomerCode(reader.getAsciiValue("CUSTOMER_CODE"));
		//#CM722709
		// Customer Name 
		if (reader.getEnable("CUSTOMER_NAME"))
			retParam.setCustomerName(reader.getValue("CUSTOMER_NAME"));
		//#CM722710
		// Ticket No. 
		if (reader.getEnable("SHIPPING_TICKET_NO"))
			retParam.setShippingTicketNo(reader.getAsciiValue("SHIPPING_TICKET_NO"));
		//#CM722711
		// Line No
		if (reader.getEnable("SHIPPING_LINE_NO"))
			retParam.setShippingLineNo(reader.getIntValue("SHIPPING_LINE_NO"));
		//#CM722712
		// Item Code
		if (reader.getEnable("ITEM_CODE"))
			retParam.setItemCode(reader.getAsciiValue("ITEM_CODE"));
		//#CM722713
		// Bundle ITF
		if (reader.getEnable("BUNDLE_ITF"))
			retParam.setBundleITF(reader.getAsciiValue("BUNDLE_ITF"));
		//#CM722714
		// Case ITF
		if (reader.getEnable("ITF"))
			retParam.setITF(reader.getAsciiValue("ITF"));
		//#CM722715
		// Packed qty per bundle
		if (reader.getEnable("BUNDLE_ENTERING_QTY"))
			retParam.setBundleEnteringQty(reader.getIntValue("BUNDLE_ENTERING_QTY"));
		//#CM722716
		// Packed Qty per Case
		if (reader.getEnable("ENTERING_QTY"))
			retParam.setEnteringQty(reader.getIntValue("ENTERING_QTY"));
		//#CM722717
		// Item 
		if (reader.getEnable("ITEM_NAME1"))
			retParam.setItemName(reader.getValue("ITEM_NAME1"));
		//#CM722718
		// Picking qty 
		if (reader.getEnable("PLAN_QTY"))
			retParam.setTotalPlanQty(reader.getIntValue("PLAN_QTY"));
		//#CM722719
		// Piece Item Picking Location 
		if (reader.getEnable("JOB_LOCATION_PIECE"))
			retParam.setPieceLocation(reader.getAsciiValue("JOB_LOCATION_PIECE"));
		//#CM722720
		// Case Item Picking Location 
		if (reader.getEnable("JOB_LOCATION_CASE"))
			retParam.setCaseLocation(reader.getAsciiValue("JOB_LOCATION_CASE"));
		//#CM722721
		// Order No. (Piece) 
		if (reader.getEnable("ORDER_NO_PIECE"))
			retParam.setPieceOrderNo(reader.getAsciiValue("ORDER_NO_PIECE"));
		//#CM722722
		// Order No. (Case) 
		if (reader.getEnable("ORDER_NO_CASE"))
			retParam.setCaseOrderNo(reader.getAsciiValue("ORDER_NO_CASE"));
		//#CM722723
		// Batch No.
		retParam.setBatchNo(bachNo);
		//#CM722724
		// Added person code 
		retParam.setWorkerCode(workercode);
		//#CM722725
		// Added person name 
		retParam.setWorkerName(workername);
		//#CM722726
		// Terminal 
		retParam.setTerminalNumber(terminal);
		//#CM722727
		// Entry type 
		retParam.setRegistKind(RetrievalPlan.REGIST_KIND_HOST);
		return retParam;
	}
	
	//#CM722728
	/**
	 * Generate an entity of shortage info. 
	 * 
	 * @param pRetrievalPlan Picking Plan Info entity that forms the base of shortage info 
	 * @param pAllocateQty Allocatable qty 
	 * @param pRegistDate Added Date/Time 
	 * @return Entity of the generated shortage info 
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 */
	protected ShortageInfo createShortageInfoEntity(RetrievalPlan pRetrievalPlan, int pAllocateQty, Date pRegistDate) throws ReadWriteException
	{
		ShortageInfo shortageInfo = new ShortageInfo();
		
		try
		{
			//#CM722729
			// Plan unique key 
			shortageInfo.setPlanUkey(pRetrievalPlan.getRetrievalPlanUkey());
			//#CM722730
			// Work type: Picking
			shortageInfo.setJobType(ShortageInfo.JOB_TYPE_RETRIEVAL);
			//#CM722731
			// Planned date
			shortageInfo.setPlanDate(pRetrievalPlan.getPlanDate());
			//#CM722732
			// Consignor Code
			shortageInfo.setConsignorCode(pRetrievalPlan.getConsignorCode());
			//#CM722733
			// Consignor Name
			shortageInfo.setConsignorName(pRetrievalPlan.getConsignorName());
			//#CM722734
			// Customer Code
			shortageInfo.setCustomerCode(pRetrievalPlan.getCustomerCode());
			//#CM722735
			// Customer Name 
			shortageInfo.setCustomerName1(pRetrievalPlan.getCustomerName1());
			//#CM722736
			// Shipping Ticket No 
			shortageInfo.setShippingTicketNo(pRetrievalPlan.getShippingTicketNo());
			//#CM722737
			// Shipping ticket line 
			shortageInfo.setShippingLineNo(pRetrievalPlan.getShippingLineNo());
			//#CM722738
			// Item Code
			shortageInfo.setItemCode(pRetrievalPlan.getItemCode());
			//#CM722739
			// Item Name
			shortageInfo.setItemName1(pRetrievalPlan.getItemName1());
			//#CM722740
			// Planned qty 
			shortageInfo.setPlanQty(pRetrievalPlan.getPlanQty());
			//#CM722741
			// Allocatable qty 
			shortageInfo.setEnableQty(pAllocateQty);
			//#CM722742
			// Shortage Qty
			shortageInfo.setShortageCnt(pRetrievalPlan.getPlanQty() - pAllocateQty);
			//#CM722743
			// Packed Qty per Case
			shortageInfo.setEnteringQty(pRetrievalPlan.getEnteringQty());
			//#CM722744
			// Packed qty per bundle
			shortageInfo.setBundleEnteringQty(pRetrievalPlan.getBundleEnteringQty());
			//#CM722745
			// Case/Piece division 
			shortageInfo.setCasePieceFlag(pRetrievalPlan.getCasePieceFlag());
			//#CM722746
			// Case ITF
			shortageInfo.setItf(pRetrievalPlan.getItf());
			//#CM722747
			// Bundle ITF
			shortageInfo.setBundleItf(pRetrievalPlan.getBundleItf());
			//#CM722748
			// Piece Item Picking Location 
			shortageInfo.setPieceLocation(pRetrievalPlan.getPieceLocation());
			//#CM722749
			// Case Item Picking Location 
			shortageInfo.setCaseLocation(pRetrievalPlan.getCaseLocation());
			//#CM722750
			// Order No. Piece 
			shortageInfo.setPieceOrderNo(pRetrievalPlan.getPieceOrderNo());
			//#CM722751
			// Order No. Case 
			shortageInfo.setCaseOrderNo(pRetrievalPlan.getCaseOrderNo());
			//#CM722752
			// Expiry Date
			shortageInfo.setUseByDate(pRetrievalPlan.getUseByDate());
			//#CM722753
			// Lot No.
			shortageInfo.setLotNo(pRetrievalPlan.getLotNo());
			//#CM722754
			// Plan information Comment 
			shortageInfo.setPlanInformation(pRetrievalPlan.getPlanInformation());
			//#CM722755
			// Batch No. (Schedule No.) 
			shortageInfo.setBatchNo(pRetrievalPlan.getBatchNo());
			//#CM722756
			// Worker Code
			shortageInfo.setWorkerCode(pRetrievalPlan.getWorkerCode());
			//#CM722757
			// Worker Name
			shortageInfo.setWorkerName(pRetrievalPlan.getWorkerName());
			//#CM722758
			// Terminal No.
			shortageInfo.setTerminalNo(pRetrievalPlan.getTerminalNo());
			//#CM722759
			// Entry type 
			shortageInfo.setRegistKind(pRetrievalPlan.getRegistKind());
			//#CM722760
			// Added Date/Time 
			shortageInfo.setRegistDate(pRegistDate);
			//#CM722761
			// Name of Add Process
			shortageInfo.setRegistPname(pName);
			//#CM722762
			// Last update date/time
			//#CM722763
			// Allow the handler to set it automatically. 
			//#CM722764
			// Last update process name
			shortageInfo.setLastUpdatePname(pName);
		}
		catch (InvalidStatusException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		
		return shortageInfo;
	}
	

	//#CM722765
	// Protected methods ---------------------------------------------

	//#CM722766
	//	Private methods -----------------------------------------------
	//#CM722767
	/**
	 * Obtain the shortage info entity from HashTable, and execute the process for adding it in the shortage info table (DnShortageInfo). 
	 * 
	 * @param conn Database connection object 
	 * @param pShortageHash HashTable that contains shortage info entity 
	 * @param pKeyVec Shortage info HashTable key 
	 * @throws ReadWriteException Announce when error occurs on database connection.
	 */
	protected void createShortageInfo(Connection conn, Hashtable pShortageHash, Vector pKeyVec) throws ReadWriteException
	{
		
		//#CM722768
		// Add a new shortage info. 
		ShortageInfoHandler shortageHandler = new ShortageInfoHandler(conn);
		ShortageInfo shortageInfo = null;
		
		String[] key = new String[pKeyVec.size()];
		pKeyVec.copyInto(key);
		
		try
		{
			//#CM722769
			// Obtain from Hash for the number of elements of key. 
			for (int i = 0; i < key.length; i++)
			{
				//#CM722770
				// If the element of the corresponding key does not exist because it was deleted, return null. 
				//#CM722771
				// Therefore, disable to insert it into DB. 
				shortageInfo = (ShortageInfo) pShortageHash.get(key[i]);
				if (shortageInfo != null)
				{
					shortageHandler.create(shortageInfo);
				}
			}
		
		}
		catch (DataExistsException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}
	
	//#CM722772
	/**
	 * Generate the Picking Plan Info. 
	 * Use this method only when Inventory package is enabled. 
	 * 
	 * @param conn Database connection object 
	 * @param wns  WareNaviSystem object 
	 * @param searchParam Class that inherits the <CODE>Parameter</CODE> class. 
	 * @throws ReadWriteException Announce when error occurs on database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 * @return Return true if normally added data, or return false if error occurs. 
	 */
	protected boolean createRetrievalPlanStockOn(Connection conn, WareNaviSystem wns, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		//#CM722773
		// Obtain the Planned Work Date from the parameter 
		SystemParameter param = (SystemParameter) searchParam;
		String PlanDate = param.getPlanDate();
		String batch_seqno = "";
		String wFileName = "";
		//#CM722774
		// Existing Data Skip flag 
		boolean existingFlag = false;

		//#CM722775
		// Initialize the Count. (count of all the loaded data, count of the added data, count of the updated data, and count of the skipped data) 
		InitItemCount();

		//#CM722776
		// Generate DataLoadCsvReader instance. 
		//#CM722777
		// Generate Extraction history file in the DataLoadCsvReader class. 
		DataLoadCsvReader DRR = null;

		//#CM722778
		// Generate a class instance to generate an loaded error file. 
		DataLoadStatusCsvFileWriter errlist = new DataLoadStatusCsvFileWriter();

		//#CM722779
		// Process to submit/add data 
		RetrievalPlanOperator retOperator = new RetrievalPlanOperator(conn);

		try
		{
			//#CM722780
			// Lock the Picking plan data. 
			retOperator.lockRetrievalPlan();
			
			//#CM722781
			// Obtain the Picking Plan file name. 
			wFileName = GetRetrievalFile();

			//#CM722782
			// Load the CSV file and set the class. 
			DRR = new DataLoadCsvReader(DataLoadCsvReader.LOADTYPE_RETRIEVALSUPPORT, PlanDate, "PLAN_DATE");

			//#CM722783
			// Check File Size 
			if (DRR.getFileSize() <= 0)
			{
				//#CM722784
				// 6023289=Record volume is 0. File name: {0} 
				setMessage("6023289" + wDelim + wFileName);
				return false;
			}

			//#CM722785
			// Generate the SequenceHandler instance. 		
			SequenceHandler SQ = new SequenceHandler(conn);
			//#CM722786
			// Generate a Picking Plan Info operation class. 
			RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn); 
			RetrievalPlanAlterKey planAltKey = new RetrievalPlanAlterKey();
			
			//#CM722787
			// Add the Picking Plan Info. 
			batch_seqno = SQ.nextRetrievalPlanBatchNo();

			//#CM722788
			// Parameter to be added 
			RetrievalSupportParameter retParam = null;
			//#CM722789
			// Double entry check flag 
			boolean updateFlag = false;
			boolean record_check_flag = true;
			
			//#CM722790
			// Obtain the next one line of the corresponding planned date from the plan file. 
			//#CM722791
			// while (Obtain the next one line of the corresponding Planned Date.) {
			//#CM722792
			// Loop until any data no longer obtained. 
			while (DRR.MoveNext())
			{
				retParam = new RetrievalSupportParameter();
				updateFlag = false;
				
				//#CM722793
				// Count up all loaded data. 
				wAllItemCount++;
				//#CM722794
				// Obtain the file name for error. 
				if (errlist.getFileName().equals(""))
				{
					errlist.setFileName(DRR.getFileName());
				}
				//#CM722795
				// Set a single record of a file for the parameter. 
				retParam = setParameter(DRR, batch_seqno, param.getWorkerCode(), param.getWorkerName(), param.getTerminalNumber());

				//#CM722796
				// Check for input. 
				if (!check(retParam, retOperator, wns, DRR, errlist))
				{
					record_check_flag = false;
					continue;
				}
				if (!record_check_flag)
				{
					continue;
				}
				
				//#CM722797
				// Obtain the existing Picking Plan Info. 
				RetrievalPlan[] retrieval = null;
				retrieval = retOperator.findRetrievalPlanForUpdate(retParam, true);
				if (retrieval != null && retrieval.length != 0)
				{
					//#CM722798
					// Confirm the status of the existing data. 
					for (int i = 0;i < retrieval.length;i++)
					{
						//#CM722799
						// For data with work status other than Standby, turn the existing Data Check flag ON. 
						if (!retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_UNSTART))
						{
							existingFlag = true;
						}
						//#CM722800
						// Turn the Check flag ON for the existing data with the schedule flag other than Not Processed. 
						if (!retrieval[i].getSchFlag().equals(RetrievalPlan.SCH_FLAG_UNSTART))
						{
							existingFlag = true;
						}
					}
					
					//#CM722801
					// Execute skip process if there is one or more data. 
					if (existingFlag)
					{
						//#CM722802
						// 6022003=Skipped. The same data already exists. File: {0} Line: {0} 
						String[] msg = { DRR.getFileName(), DRR.getLineNo()};
						RmiMsgLogClient.write(6022003, "RetrievalDataLoader", msg);
						//#CM722803
						// Record the process error in the log. 
						//#CM722804
						// 6006007=Cannot add. The same data already exists. 
						setMessage( "6006007" );
						setSkipFlag(true);
						
					}
					else
					{
						//#CM722805
						// Delete data with status Standby only. 
						for (int i = 0;i < retrieval.length;i++)
						{
							//#CM722806
							// --------- Delete the picking plan info. 
							//#CM722807
							// Update the Picking Plan Info to "Deleted". 
							planAltKey.KeyClear();
							planAltKey.setRetrievalPlanUkey(retrieval[i].getRetrievalPlanUkey());
							planAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
							planHandler.modify(planAltKey);	
						}
						setOverWriteFlag(true);
						updateFlag = true;
					}
				}
				
				//#CM722808
				// Only when the Existing Data Skip flag is false, execute the Submit/Add process. 
				if( !existingFlag )
				{		
					//#CM722809
					// Process to submit/add Plan Info Process 
					retOperator.createRetrievalPlan(retParam, pName);			
					setRegistFlag(true);

					//#CM722810
					// Write also normal data in the loaded error file generation class. 
					errlist.addStatusCsvFile(DataLoadStatusCsvFileWriter.STATUS_NORMAL, DRR.getLineData(), "");
				}
				else
				{
					//#CM722811
					// Write error contents additionally in the loaded error file generation class. 
					errlist.addStatusCsvFile( DataLoadStatusCsvFileWriter.STATUS_ERROR, DRR.getLineData(), getMessage() );
					//#CM722812
					// Initialize the Skip flag. 
					existingFlag = false;
					
				}
				
				if (updateFlag)
				{
					//#CM722813
					// 6022002=Same data were loaded. File: {0} Line: {1} 
					String[] msg = { DRR.getFileName(), DRR.getLineNo()};
					RmiMsgLogClient.write(6022002, "RetrievalDataLoader", msg);
					//#CM722814
					// Count-up the updated data. 
					wUpdateItemCount++;
				}
				else
				{
					//#CM722815
					// Count up the added data. 
					wInsertItemCount++;
				}
			}
			if (!record_check_flag )
			{
				//#CM722816
				// 6023326=Error data was found in the Load Plan Data. The process was aborted. See log. 
				setMessage("6023326");
				return false;
			}
			return true;
			
		}
		catch (FileNotFoundException e)
		{
			//#CM722817
			// No file was found.{0} 
			RmiMsgLogClient.write(new TraceHandler(6003009, e), pName);
			setMessage("6003009" + wDelim + wFileName);
			return false;
		}
		catch (IOException e)
		{
			//#CM722818
			// 6006020=File I/O error occurred. 
			RmiMsgLogClient.write(new TraceHandler(6006020, e), pName);
			throw new ScheduleException();
		}
		catch (ClassNotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InstantiationException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (CsvIllegalDataException e)
		{
			//#CM722819
			// 6023326=Error data was found in the Load Plan Data. The process was aborted. 
			setMessage("6023326");
			
			//#CM722820
			// 6026085=Error data is found in the load data. Detail:{0} 
			Object[] msg = new Object[1]; 
			msg[0] = e.getMessage() ;
			RmiMsgLogClient.write(6026085, pName, msg);
			return false;
		} 
		catch (InvalidDefineException e)
		{
			//#CM722821
			// 6023326=Error data was found in the Load Plan Data. The process was aborted. 
			setMessage("6023326");
			//#CM722822
			// 6026006=Error data was found in data format. File: {0} Line: {1} 
			String[] msg = { DRR.getFileName(), DRR.getLineNo()};
			RmiMsgLogClient.write(6026006, pName, msg);
			return false;
		}
		finally
		{
			//#CM722823
			// If one or more target data exist, write it to the error file. 
			if (!errlist.getFileName().equals(""))
			{
				errlist.writeStatusCsvFile();
			}
		}
	}
	
	//#CM722824
	/**
	 * Generate the Picking Plan Info, the Work Status Info, and the Inventory Info. 
	 * Use this method only when Inventory package is disabled. 
	 * 
	 * @param conn Database connection object 
	 * @param wns  WareNaviSystem object 
	 * @param searchParam Class that inherits the <CODE>Parameter</CODE> class. 
	 * @throws ReadWriteException Announce when error occurs on database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs. 
	 * @return Return true if normally added data, or return false if error occurs. 
	 */
	protected boolean createRetrievalPlanStockOff(Connection conn, WareNaviSystem wns, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		//#CM722825
		// Obtain the Planned Work Date from the parameter 
		SystemParameter param = (SystemParameter) searchParam;
		String PlanDate = param.getPlanDate();
		String batch_seqno = "";
		String wFileName = "";
		//#CM722826
		// Existing Data Skip flag 
		boolean existingFlag = false;

		//#CM722827
		// Initialize the Count. (count of all the loaded data, count of the added data, count of the updated data, and count of the skipped data) 
		InitItemCount();

		//#CM722828
		// Generate DataLoadCsvReader instance. 
		//#CM722829
		// Generate Extraction history file in the DataLoadCsvReader class. 
		DataLoadCsvReader DRR = null;

		//#CM722830
		// Generate a class instance to generate an loaded error file. 
		DataLoadStatusCsvFileWriter errlist = new DataLoadStatusCsvFileWriter();

		//#CM722831
		// Process to submit/add data 
		RetrievalPlanOperator retOperator = new RetrievalPlanOperator(conn);
		RetrievalAllocateOperator retAllocator = new RetrievalAllocateOperator(conn);
		try
		{
			//#CM722832
			// Work type: Lock the Work Status Info and Inventory Info relating to Picking. 
			retOperator.lockWorkingInfoStockData();
			//#CM722833
			// Lock the Inventory Info.
			//#CM722834
			// (Lock the Center Inventory before starting the process for excluding others) 
			retOperator.lockStockData(conn);		

			//#CM722835
			// Obtain the Picking Plan file name. 
			wFileName = GetRetrievalFile();

			//#CM722836
			// Load the CSV file and set the class. 
			DRR = new DataLoadCsvReader(DataLoadCsvReader.LOADTYPE_RETRIEVALSUPPORT, PlanDate, "PLAN_DATE");

			//#CM722837
			// Check File Size 
			if (DRR.getFileSize() <= 0)
			{
				//#CM722838
				// 6023289=Record volume is 0. File name: {0} 
				setMessage("6023289" + wDelim + wFileName);
				return false;
			}

			//#CM722839
			// Generate the SequenceHandler instance. 		
			SequenceHandler SQ = new SequenceHandler(conn);
			//#CM722840
			// Generate a Picking Plan Info operation class. 
			RetrievalPlanHandler planHandler = new RetrievalPlanHandler(conn); 
			RetrievalPlanAlterKey planAltKey = new RetrievalPlanAlterKey();
			
			//#CM722841
			// Add the Picking Plan Info. 
			batch_seqno = SQ.nextRetrievalPlanBatchNo();

			//#CM722842
			// Store the entity of shortage info. 
			Hashtable shortageHash = new Hashtable();
			//#CM722843
			// List of shortageHash keys 
			Vector keyVec = new Vector();
			//#CM722844
			// Shortage info for temporary use 
			ShortageInfo tempShortage = null;
			//#CM722845
			// Added Date/Time loaded this time here 
			Date sysdate = new Date();

			//#CM722846
			// Parameter to be added 
			RetrievalSupportParameter retParam = null;
			//#CM722847
			// Double entry check flag 
			boolean updateFlag = false;
			boolean record_check_flag = true;
			
			//#CM722848
			// Obtain the next one line of the corresponding planned date from the plan file. 
			//#CM722849
			// while (Obtain the next one line of the corresponding Planned Date.) {
			//#CM722850
			// Loop until any data no longer obtained. 
			while (DRR.MoveNext())
			{
				retParam = new RetrievalSupportParameter();
				updateFlag = false;
				
				//#CM722851
				// Count up all loaded data. 
				wAllItemCount++;
				//#CM722852
				// Obtain the file name for error. 
				if (errlist.getFileName().equals(""))
				{
					errlist.setFileName(DRR.getFileName());
				}
				//#CM722853
				// Set a single record of a file for the parameter. 
				retParam = setParameter(DRR, batch_seqno, param.getWorkerCode(), param.getWorkerName(), param.getTerminalNumber());

				//#CM722854
				// Check for input. 
				if (!check(retParam, retOperator, wns, DRR, errlist))
				{
					record_check_flag = false;
					continue;
				}
				if (!record_check_flag)
				{
					continue;
				}
				
				//#CM722855
				// Obtain the existing Picking Plan Info. 
				RetrievalPlan[] retrieval = null;
				retrieval = retOperator.findRetrievalPlanForUpdate(retParam, true);
				if (retrieval != null && retrieval.length != 0)
				{
					//#CM722856
					// Confirm the status of the existing data. 
					for (int i = 0;i < retrieval.length;i++)
					{
						//#CM722857
						// For data with work status other than Standby, turn the existing Data Check flag ON. 
						if (!retrieval[i].getStatusFlag().equals(RetrievalPlan.STATUS_FLAG_UNSTART))
						{
							existingFlag = true;
						}
					}
					
					//#CM722858
					// Execute skip process if there is one or more data. 
					if (existingFlag)
					{
						//#CM722859
						// 6022003=Skipped. The same data already exists. File: {0} Line: {0} 
						String[] msg = { DRR.getFileName(), DRR.getLineNo()};
						RmiMsgLogClient.write(6022003, "RetrievalDataLoader", msg);
						//#CM722860
						// Record the process error in the log. 
						//#CM722861
						// 6006007=Cannot add. The same data already exists. 
						setMessage( "6006007" );
						setSkipFlag(true);
						
					}
					else
					{
						//#CM722862
						// Delete data with status Standby only. 
						for (int i = 0;i < retrieval.length;i++)
						{
							//#CM722863
							// ---------Delete the shortage info (Save temporarily). 
							tempShortage = null;
							//#CM722864
							// If shortage info corresponding to the unique key of the current targeted Picking Plan Info here
							//#CM722865
							// exsists in Hash, delete this shortage info because it has been generated here this time. 
							if (shortageHash.containsKey(retrieval[i].getRetrievalPlanUkey()))
							{
								//#CM722866
								// Obtain the shortage info to be deleted for checking at allocation cancel. 
								tempShortage = (ShortageInfo) shortageHash.get(retrieval[i].getRetrievalPlanUkey());
								//#CM722867
								// Delete the target data. 
								shortageHash.remove(tempShortage.getPlanUkey());
							}
							
							//#CM722868
							// ---------Delete the Work Status Info and the Inventory Info. 
							//#CM722869
							// If the allocatable qty in the shortage info is 0,
							//#CM722870
							// disable to generate Work Status Info nor Inventory Info and therefore disable to execute delete process. 
							//#CM722871
							// Execute the delete process only when the Allocatable Qty is 0 or more. 
							if (tempShortage == null || tempShortage.getEnableQty() != 0)
							{
								//#CM722872
								// Cancel the allocation (Work Status Info and Inventory Info). 
								if (!retAllocator.cancel(
										conn,
										retrieval[i].getRetrievalPlanUkey(),
										param.getWorkerCode(),
										param.getWorkerName(),
										param.getTerminalNumber(),
										pName))
								{
									//#CM722873
									// 6006002=Database error occurred.{0}
									RmiMsgLogClient.write("6006002" + wDelim + pName, this.getClass().getName());
									//#CM722874
									// 6023154=There is no data to update. 
									throw (new ReadWriteException("6023154"));
								}
	
							}
							
							//#CM722875
							// --------- Delete the picking plan info. 
							//#CM722876
							// Update the Picking Plan Info to "Deleted". 
							planAltKey.KeyClear();
							planAltKey.setRetrievalPlanUkey(retrieval[i].getRetrievalPlanUkey());
							planAltKey.updateStatusFlag(RetrievalPlan.STATUS_FLAG_DELETE);
							planHandler.modify(planAltKey);
	
						}
						setOverWriteFlag(true);
						updateFlag = true;
					}
				}
				
				//#CM722877
				// Only when the Existing Data Skip flag is false, execute the Submit/Add process. 
				if( !existingFlag )
				{
					//#CM722878
					// Process to submit/add Plan Info Process 
					RetrievalPlan retPlan = retOperator.createRetrievalPlan(retParam, pName);			
					setRegistFlag(true);
					
					//#CM722879
					// Execute the Allocation process. 
					int allocQty = retAllocator.allocate(conn, retPlan.getRetrievalPlanUkey(),
							 param.getWorkerCode(), param.getWorkerName(), param.getTerminalNumber(), pName);
					
					//#CM722880
					// Execute the process of shortage if the allocated qty is below the Planned qty (Shortage). 
					//#CM722881
					// Write the error log and generate the shortage info entity. 
					if (retPlan.getPlanQty() > allocQty)
					{
						//#CM722882
						// 6022009=Shortage occurred in the Picking Plan Data. Consignor:{0} Item Code:{1} Planned qty: {2} 
						String[] msg = { retPlan.getConsignorCode(), retPlan.getItemCode(), Integer.toString(retPlan.getPlanQty())};
						RmiMsgLogClient.write( 6022009, "RetrievalDataLoader", msg );
						//#CM722883
						// 6023397=Shortage error occurred. Process was aborted. Please confirm the shortage data from the picking plan data shortage check screen. 
						//#CM722884
						// Write error contents additionally in the loaded error file generation class. 
						errlist.addStatusCsvFile( DataLoadStatusCsvFileWriter.STATUS_ERROR, DRR.getLineData(), "6023397" );

						//#CM722885
						// Generate a shortage info and store it in Hash. 
						shortageHash.put(retPlan.getRetrievalPlanUkey(), createShortageInfoEntity(retPlan, allocQty, sysdate));
						//#CM722886
						// Store the key for extraction. 
						keyVec.add(retPlan.getRetrievalPlanUkey());

					}
					else
					{
						//#CM722887
						// Write also normal data in the loaded error file generation class. 
						errlist.addStatusCsvFile(DataLoadStatusCsvFileWriter.STATUS_NORMAL, DRR.getLineData(), "");
					}
				}
				else
				{
					//#CM722888
					// Write error contents additionally in the loaded error file generation class. 
					errlist.addStatusCsvFile( DataLoadStatusCsvFileWriter.STATUS_ERROR, DRR.getLineData(), getMessage() );
					//#CM722889
					// Initialize the Skip flag. 
					existingFlag = false;
					
				}
				
				if (updateFlag)
				{
					//#CM722890
					// 6022002=Same data were loaded. File: {0} Line: {1} 
					String[] msg = { DRR.getFileName(), DRR.getLineNo()};
					RmiMsgLogClient.write(6022002, "RetrievalDataLoader", msg);
					//#CM722891
					// Count-up the updated data. 
					wUpdateItemCount++;
				}
				else
				{
					//#CM722892
					// Count up the added data. 
					wInsertItemCount++;
				}
			}

			if (!record_check_flag )
			{
				//#CM722893
				// 6023326=Error data was found in the Load Plan Data. The process was aborted. See log. 
				setMessage("6023326");
				return false;
			}
			
			//#CM722894
			// Once the shortage info has been created, cancel the allocation and generate a data only for shortage info. 
			if (!shortageHash.isEmpty())
			{
				try
				{
					//#CM722895
					// Roll back the generated allocation info. 
					conn.rollback();

					//#CM722896
					// Generate the shortage info. 
					createShortageInfo(conn, shortageHash, keyVec);

					//#CM722897
					// Commit the transaction of the generated shortage info. 
					conn.commit();
				}
				catch (SQLException e)
				{
					//#CM722898
					// 6006002=Database error occurred.{0}
					RmiMsgLogClient.write(new TraceHandler(6006002, e), this.getClass().getName());
					throw new ReadWriteException(e.getMessage());
				}

				//#CM722899
				// 6023397=Shortage error occurred. Process was aborted. Please confirm the shortage data from the picking plan data shortage check screen. 
				setMessage("6023397");
				return false;
				
			}

			return true;
			
		}
		catch (FileNotFoundException e)
		{
			//#CM722900
			// No file was found.{0} 
			RmiMsgLogClient.write(new TraceHandler(6003009, e), pName);
			setMessage("6003009" + wDelim + wFileName);
			return false;
		}
		catch (IOException e)
		{
			//#CM722901
			// 6006020=File I/O error occurred. 
			RmiMsgLogClient.write(new TraceHandler(6006020, e), pName);
			throw new ScheduleException();
		}
		catch (ClassNotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InstantiationException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (CsvIllegalDataException e)
		{
			//#CM722902
			// 6023326=Error data was found in the Load Plan Data. The process was aborted. 
			setMessage("6023326");
			
			//#CM722903
			// 6026085=Error data is found in the load data. Detail:{0} 
			Object[] msg = new Object[1]; 
			msg[0] = e.getMessage() ;
			RmiMsgLogClient.write(6026085, pName, msg);
			return false;
		} 
		catch (InvalidDefineException e)
		{
			//#CM722904
			// 6023326=Error data was found in the Load Plan Data. The process was aborted. 
			setMessage("6023326");
			//#CM722905
			// 6026006=Error data was found in data format. File: {0} Line: {1} 
			String[] msg = { DRR.getFileName(), DRR.getLineNo()};
			RmiMsgLogClient.write(6026006, pName, msg);
			return false;
		}
		finally
		{
			//#CM722906
			// If one or more target data exist, write it to the error file. 
			if (!errlist.getFileName().equals(""))
			{
				errlist.writeStatusCsvFile();
			}
		}
	}
}
//#CM722907
//end of class
