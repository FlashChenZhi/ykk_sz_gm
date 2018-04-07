package jp.co.daifuku.wms.storage.schedule;

// $Id: StorageDataLoader.java,v 1.2 2006/10/16 08:04:58 suresh Exp $
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.IniFileOperation;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.location.EmptyLocation;
import jp.co.daifuku.wms.base.location.IdmEmptyLocationSelector;
import jp.co.daifuku.wms.base.system.schedule.AbstractExternalDataLoader;
import jp.co.daifuku.wms.base.system.schedule.SystemParameter;
import jp.co.daifuku.wms.base.utility.CsvIllegalDataException;
import jp.co.daifuku.wms.base.utility.DataLoadCsvReader;
import jp.co.daifuku.wms.base.utility.DataLoadStatusCsvFileWriter;

/**
 * Designer : T.Yamashita <BR>
 * Update-Designer : C.Kaminishizono <BR>
 * Maker : T.Yamashita <BR>
 * <BR>
 * <CODE>StorageDataLoader</CODE> class executes the process to extract a storage data.<BR>
 * Inherit <CODE>AbstractExternalDataLoader</CODE> abstract class and implement the required processes.<BR>
 * The method in this class receives the connection object and executes process for updating the database.<BR>
 * However, this method disables to commit and roll back of transactions.<BR>
 * This class executes the following processes.<BR>
 * <BR>
 * 1.Data taking process(<CODE>load(Connection conn, WareNaviSystem wns, Parameter searchParam)</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive Connection object, WareNaviSystem object, and Parameter object as a parameter and add the data to the database via HOST storage plan file (CSV file)<BR>
 *    (Storage Plan info, Work status, and inventory information ) <BR>
 *   Lock the Work status and inventory information for exclusive process at starting the process.<BR>
 *   Return false when error occurs in the method.<BR>
 *   Enable to refer to the content of the error using <CODE>getMessage()</CODE> method.<BR>
 * <BR>
 *   <Parameter><BR>
 *     Connection object <BR>
 *     WareNaviSystem object <BR>
 *     SystemParameter object <BR>
 * <BR>
 *   <Details of processes> <BR>
 *      1.Generate Extraction History File<BR>
 * <DIR>
 *        File is renamed to the name concatenated with date (YYYYMMDD) + time (HHMISS) + extracted file name.<BR>
 * </DIR>
 *      2.Initialize Data count.<BR>
 * <DIR>
 *        All loaded data count, submitted/added data count, updated data count, skipped data count<BR>
 *        Add flag False, Overwrite flag False, Skip flag False<BR>
 * </DIR>
 *      3.Loading of HOST Storage Plan file<BR>
 * <DIR>
 *        Load the data from the plan file defined for the environment file (environmental information)<BR>
 *        Obtain the lines of the corresponding Planned Date that was input from HOST Storage Plan file via screen.<BR>
 * </DIR>
 *      4.Verify the consistency of loading data.<BR>
 * <DIR>
 *        date field          --- Check for the number of digits, forbidden character, and execute date translate check<BR>
 *        ASCII string field (count)   --- Check for the number of digits and ASCII code check, and check for forbidden character.<BR>
 *        string field        --- Check for the number of digits and forbidden character.<BR>
 *        numeric field          --- Check the number of digits, date translation, and negative value.<BR>
 *        When error occurs, write additionally the error contents to the extraction error file and close the process and return False.<BR>
 * </DIR>
 * <BR>
 *      Features were added according to the addition of Empty Information control features. 2005.04.11 Author GSC:C.Kaminishizono <BR>
 *      5.Determine the storage location if both Case item location and Piece item location are selected to None, storage location. <BR>
 * <DIR>
 *        Obtain the storage location using the Empty search method. <BR>
 *        Set the Obtained Empty No for Case location and Piece location. <BR>
 *        Disable to update if no empty location exists. <BR>
 * </DIR>
 *      Features were added according to the addition of Empty Information control features. End line. <BR>
 * <BR>
 *      6.Identify the Case/Piece division of the obtained data.<BR>
 * <BR>
 * <DIR>
 *        For the data that is set for both Case location and Piece location, if the planned qty is equal to the Packed qty. per case or smaller, identify it as Piece<BR>
 *        For the data that is set for both Case location and Piece location, if the planned qty can be divided by Packed qty. per case with no remainder, identify it as Case<BR>
 *        For the data that is set for both Case location and Piece location, if dividing the planned qty by the Packed qty. per case produces quotient with remainder, identify as Mixed<BR>
 * </DIR>
 *      7.Check the specification of the loading data.<BR>
 * <BR>
 * <DIR>
 *        Check wheter the planned work qty is set to more than 0.<BR>
 *        Check for whether either of Piece item storage or Case Item Storage Location is set, if no inventory package.<BR>
 * </DIR>
 *      8.Check for presence of the data.<BR>
 * <DIR>
 *        Search through the Work status and check whether any data with the same aggregation key exists.<BR>
 * 		  Aggregation key(Planned Date, Consignor Code, Item Code, Case/Piece division, Piece Location No., Case Location No.)
 *        Use Planned storage date + Consignor code + JAN + Case/Piece division + Location No. as aggregation keys<BR>
 *        Skip any existing data with status other than Standby or Deleted and load the next data (Count up the skipped data).<BR>
 *        If data exists, make the Overwrite flag True.<BR>
 *        Note) For data with Case/Piece division Mixed, search through both work info of Case and Piece but<BR>
 *          skip data if the status of either one of case or piece data is other than Standby or Deleted.<BR>
 * </DIR>
 *      9.Process to delete the status of existing data<BR>
 * <DIR>
 *        If any data with Standby that was foundat the step 7, change its status to Deleted.
 *        Update tables of Work status, Storage Plan info, and inventory information.
 *        Change the Status of Work status and Storage Plan info to Deleted. change the status of inventory information to Completed.
 *        Update Work status-> Storage Plan info-> inventory information in this sequence.
 *        Lock only the table of Work status.
 * </DIR>
 *      10.Execute the process for adding the Storage Plan info, Work status, or inventory information.<BR>
 * <DIR>
 *        Typically, build a relation between Storage Plan info, Work status, and inventory information to be 1: 1: 1.<BR>
 *        For mixed data, however, build a relation between Storage Plan info, Work status, and inventory information to be 1 : 2 : 2.<BR>
 *        Write additionally the successfully added data to the extraction error file as a normal data.<BR>
 *        Make the Add flag True if there is a data to be added.<BR>
 * </DIR>
 *     11.Set Message.<BR>
 * <DIR>
 *        If the Add flag is True and Overwrite flag is True and Skip flag is True:<BR>
 *        "Skipped some data and closed extracting the data. (data added doubly)"<BR>
 *        If the Add flag is True and Overwrite flag is True:<BR>
 *        Data extraction closed normally (double records were found)<BR>
 *        If Submit/Add flag is True and Skip flag is True:<BR>
 *        "Sipped some data and closed."<BR>
 *        For data with Add flag True:<BR>
 *        "Data extraction has been normally completed."<BR>
 *        If Submit/Add flag is False and Skip flag is True: <BR>
 *        " Skipped some data but no target data found."<BR>
 *        If Add flag is False:<BR>
 *        " No target data was found."<BR>
 * </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/04/11</TD><TD>C.Kaminishizono</TD><TD>ロケーション管理機能追加に伴う修正</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/16 08:04:58 $
 * @author  $Author: suresh $
 */
public class StorageDataLoader extends AbstractExternalDataLoader
{
	// Class fields --------------------------------------------------

	// Class variables -----------------------------------------------
	/**
	 * Class Name
	 */
	private final String pName = "StorageDataLoader";
	/**
	 * HOST data(Planned storage date)<BR>
	 */
	protected String externalPlanDate = "";
	/**
	 * HOST data(Consignor code)<BR>
	 */
	protected String externalConsignorCode = "";
	/**
	 * HOST data(Consignor name)<BR>
	 */
	protected String externalConsignorName = "";
	/**
	 * HOST data(Item Code)<BR>
	 */
	protected String externalItemCode = "";
	/**
	 * HOST data(Bundle ITF)<BR>
	 */
	protected String externalBundleITF = "";
	/**
	 * HOST data(Case ITF)<BR>
	 */
	protected String externalITF = "";
	/**
	 * HOST data(Item Name)<BR>
	 */
	protected String externalItemName = "";
	/**
	 * HOST data(Piece sorting place)<BR>
	 */
	protected String externalPieceLocation = "";
	/**
	 * HOST data(Case Item sorting place)<BR>
	 */
	protected String externalCaseLocation = "";
	/**
	 * WareNaviSystem Information instance
	 */
	protected WareNaviSystemHandler wWarenaviHandler = null;
	/**
	 * WareNaviSystem search key instance
	 */
	protected WareNaviSystemSearchKey wsearchKey = null;

   /**
    * Generate Empty Search Instance
    */
   protected IdmEmptyLocationSelector oIdmEmpLocSel = null;

	// Class method --------------------------------------------------
	/**
	 * Return the version of this class.
	 * @return Version and Date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/16 08:04:58 $");
	}

	// Constructors --------------------------------------------------
	// Public methods ------------------------------------------------
	/**
	 * Receive Connection object, WareNaviSystem object, and SystemParameter object as a parameter and extract the Storage Plan data.<BR>
	 * For detailed operations, enable to refer to the section "Explanations of Class ".<BR>
	 * @param conn Database connection object
	 * @param wns  WareNaviSystem object
	 * @param searchParam This Class inherits the <CODE>Parameter</CODE> class.
	 * @return true if normal. false in case of error
	 * @throws ReadWriteException Announce when error occurs on the database connection.
	 * @throws ScheduleException Announce it when unexpected exception occurs in the process of checking.
	 */
	public boolean load(Connection conn, WareNaviSystem wns, Parameter searchParam) throws ReadWriteException, ScheduleException
	{
		// Obtain the Planned Work Date from the parameter
		SequenceHandler SQ = new SequenceHandler(conn);
		SystemParameter param = (SystemParameter) searchParam;
		String PlanDate = param.getPlanDate();
		String batch_seqno = "";
		String wFileName = "";

		// Initialize the count.(All loaded data count, submitted/added data count, updated data count, skipped data count)
		InitItemCount();

		// Generate DataLoadCsvReader instance.
		// Generate Extraction history file in the DataLoadCsvReader class.
		DataLoadCsvReader DRR = null;

		// Generate a class instance to generate an extraction error file.
		DataLoadStatusCsvFileWriter errlist = new DataLoadStatusCsvFileWriter();

		try
		{
			// Generate an instance for WareNavi System info
			wWarenaviHandler = new WareNaviSystemHandler(conn);

			// Generate StoragePlanOperator instance.
			StoragePlanOperator storageoperator = new StoragePlanOperator(conn);

			// Generate Empty Search Instance
			oIdmEmpLocSel = new IdmEmptyLocationSelector(conn);

			// Lock the work status and Inventory information with Work division Storage.
			storageoperator.lockWorkingInfoStockData();

			// Obtain the Storage Plan file name.
			wFileName = GetStorageFile();

			// Load the CSV file and set the class.
			DRR = new DataLoadCsvReader(DataLoadCsvReader.LOADTYPE_STRAGESUPPORT, PlanDate, "PLAN_DATE");

			// Check File Size
			if (DRR.getFileSize() <= 0)
			{
				setMessage("6023289" + wDelim + wFileName);
				return false;
			}

			// Obtain Batch No.
			if (batch_seqno.equals(""))
			{
                batch_seqno = SQ.nextStoragePlanBatchNo();
			}

			boolean update_flag = false;
			boolean record_check_flag = true;
			StoragePlan[] storageplan = null;
			StorageSupportParameter parameter = null;

			// Obtain the next one line of the corresponding planned date from the plan file.
			// Loop until any data no longer obtained.
			while (DRR.MoveNext())
			{
				// Count up all loaded data.
				wAllItemCount++;

				update_flag = false;
				parameter = new StorageSupportParameter();

				// Obtain the file name for error.
				if (errlist.getFileName().equals(""))
				{
					errlist.setFileName(DRR.getFileName());
				}

				// Set the value for StorageSupportParameter.
				parameter = setStorageSupportParameter(DRR,param,batch_seqno,pName);

				// Check
				if (!check(parameter, DRR, errlist))
				{
					// skip reading it during error
					record_check_flag = false;
					continue;
				}
				if (!record_check_flag)
				{
					continue;
				}

				storageplan =storageoperator.findStoragePlan(parameter);
				if ( storageplan != null && storageplan.length != 0)
				{
					// Two or more data exist in the mixed data
					// If 2 or more data obtained or one or more data with status other than standby, skip such data.
					boolean existData = false;
					for (int j = 0; j < storageplan.length; j++)
					{
						if (!storageplan[j].getStatusFlag().equals(StoragePlan.STATUS_FLAG_UNSTART))
						{
							existData = true;
						}
					}
					// Skip any one or more data with status other than Standby and record the log.
					if (existData)
					{
						// SKIP log
						// 6022003=Skipped. The same data already exists.File: {0} Line: {0}file:{0} line:{1}
						String[] msg = { DRR.getFileName(), DRR.getLineNo()};
						RmiMsgLogClient.write(6022003, pName, msg);
						setSkipFlag(true);
						continue;
					}
					// Execute the process for deleting If there is only data with status Standby.
					for (int j = 0; j < storageplan.length; j++)
					{
						storageoperator.updateStoragePlan(storageplan[j].getStoragePlanUkey(),pName);
						update_flag = true;
						setOverWriteFlag(true);
					}

				}
				storageoperator.createStoragePlan(parameter);
				setRegistFlag(true);
				if ( update_flag)
				{
					//6022002=Same data were loaded. File: {0} Line: {1}File:{0} line:{1}
					String[] msg = { DRR.getFileName(), DRR.getLineNo()};
					RmiMsgLogClient.write(6022002, pName, msg);
					// Count-up the updated data.
					wUpdateItemCount++;
				}
				else
				{
					// Count up the added data.
					wInsertItemCount++;
				}

				// Write also normal data in the extraction error file generation class.
				errlist.addStatusCsvFile(DataLoadStatusCsvFileWriter.STATUS_NORMAL, DRR.getLineData(), getMessage());
			}
			if (!record_check_flag )
			{
				// 6023326=Error data was found in the Load Plan Data. The process was aborted. See log.
				setMessage("6023326");
				return false;
			}
			return true;
		}
		catch (FileNotFoundException e)
		{
			// No file was found.{0}
			RmiMsgLogClient.write(new TraceHandler(6003009, e), pName);
			setMessage("6003009" + wDelim + wFileName);
			return false;
		}
		catch (IOException e)
		{
			//6006020 =  File I/O error occurred.
			RmiMsgLogClient.write(new TraceHandler(6006020, e), pName);
			throw new ScheduleException(e.getMessage());
		}
		catch (ClassNotFoundException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (IllegalAccessException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (InstantiationException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		catch (CsvIllegalDataException e)
		{
			// 6023326 = Error data was found in the Load Plan Data. The process was aborted. See log.
			setMessage("6023326");

			// 6026085 = Error data is found in the load data. Detail:{0}
			Object[] msg = new Object[1];
			msg[0] = e.getMessage() ;
			RmiMsgLogClient.write(6026085, pName, msg);
			return false;
		}
		catch (InvalidDefineException e)
		{
			// 6023326 = Error data was found in the Load Plan Data. The process was aborted. See log.
			setMessage("6023326");
			// 6026006=Error data was found in data format. File: {0} Line: {1}file:{0} line:{1}
			String[] msg = { DRR.getFileName(), DRR.getLineNo()};
			RmiMsgLogClient.write(6026006, pName, msg);
			return false;
		}
		catch (InvalidStatusException e)
		{
			throw new ScheduleException(e.getMessage());
		}
		finally
		{
			// If one or more target data exist, write it to the error file.
			if (!errlist.getFileName().equals(""))
			{
				errlist.writeStatusCsvFile();
			}
		}
	}
	// Package methods -----------------------------------------------

	// Protected methods ---------------------------------------------

	/**
	 * Set file record to parameter
	 * @param DRR   DataLoadCsvReader class
	 * @param param    SystemParameter class
	 * @param batno batch no.
	 * @param cName Class Name
	 * @return the parameter set with file record
	 * @throws ReadWriteException       Throw exception if database error occurs
	 * @throws CsvIllegalDataException  If the CSV file contents have illegal entry
	 * @throws InvalidDefineException If the parameter values are abnormal (eg. illegal characters)
	 */
	protected StorageSupportParameter setStorageSupportParameter(DataLoadCsvReader DRR,
		SystemParameter param, String batno, String cName) throws ReadWriteException, CsvIllegalDataException, InvalidStatusException
	{
		StorageSupportParameter parameter = new StorageSupportParameter();

		// Planned storage date
		if (DRR.getEnable("PLAN_DATE"))
		{
			externalPlanDate =  DRR.getDateValue("PLAN_DATE");
			if (!externalPlanDate.equals(""))
			{
				parameter.setStoragePlanDate(externalPlanDate);
			}
		}
		// Consignor code
		if (DRR.getEnable("CONSIGNOR_CODE"))
		{
			externalConsignorCode = DRR.getAsciiValue("CONSIGNOR_CODE");
			if (!externalConsignorCode.equals(""))
			{
				parameter.setConsignorCode(externalConsignorCode);
			}
			else
			{
				// Omitting the Consignor Code sets "0 ".
				parameter.setConsignorCode("0");
			}
		}
		else
		{
			// Omitting the Consignor Code sets "0 ".
			parameter.setConsignorCode("0");
		}
		// Consignor name
		if (DRR.getEnable("CONSIGNOR_NAME"))
		{
			externalConsignorName = DRR.getValue("CONSIGNOR_NAME");
			if (!externalConsignorName.equals(""))
			{
				parameter.setConsignorName(externalConsignorName);
			}
		}
		// Item Code
		if (DRR.getEnable("ITEM_CODE"))
		{
			externalItemCode = DRR.getAsciiValue("ITEM_CODE");
			if (!externalItemCode.equals(""))
			{
				parameter.setItemCode(externalItemCode);
			}
		}
		// Bundle ITF
		if (DRR.getEnable("BUNDLE_ITF"))
		{
			externalBundleITF = DRR.getAsciiValue("BUNDLE_ITF");
			if (!externalBundleITF.equals(""))
			{
				parameter.setBundleITF(externalBundleITF);
			}
		}
		// Case ITF
		if (DRR.getEnable("ITF"))
		{
			externalITF = DRR.getAsciiValue("ITF");
			if (!externalITF.equals(""))
			{
				parameter.setITF(externalITF);
			}
		}
		// Packed qty per bundle
		if (DRR.getEnable("BUNDLE_ENTERING_QTY"))
		{
			parameter.setBundleEnteringQty(DRR.getIntValue("BUNDLE_ENTERING_QTY"));
		}
		//Packed qty per Case
		if (DRR.getEnable("ENTERING_QTY"))
		{
			parameter.setEnteringQty(DRR.getIntValue("ENTERING_QTY"));
		}
		// Item Name
		if (DRR.getEnable("ITEM_NAME1"))
		{
			externalItemName = DRR.getValue("ITEM_NAME1");
			if (!externalItemName.equals(""))
			{
				parameter.setItemName(externalItemName);
			}
		}
		// Storage Planned Qty
		if (DRR.getEnable("PLAN_QTY"))
		{
			parameter.setTotalPlanQty(DRR.getIntValue("PLAN_QTY"));
		}

		 // It was amended to confirm that a check is placed in the Empty search option or not before searching the table.
		// Piece Location No.
		String w_pLocation = "";
		if (DRR.getEnable("JOB_LOCATION_PIECE"))
		{
			externalPieceLocation = DRR.getAsciiValue("JOB_LOCATION_PIECE");
			if (!externalPieceLocation.equals(""))
			{
				parameter.setPieceLocation(externalPieceLocation);
				w_pLocation = externalPieceLocation;
			}
			else
			{
				parameter.setPieceLocation("");
			}
		}
		// Case Item Location No.
		String w_cLocation = "";
		if (DRR.getEnable("JOB_LOCATION_CASE"))
		{
			externalCaseLocation = DRR.getAsciiValue("JOB_LOCATION_CASE");
			if (!externalCaseLocation.equals(""))
			{
				parameter.setCaseLocation(externalCaseLocation);
				w_cLocation = externalCaseLocation;
			}
			else
			{
				parameter.setCaseLocation("");
			}
		}

		// If any location and piece item location not defined, search Empty and set it for the piece item location.
		if ((StringUtil.isBlank(w_pLocation)) && (StringUtil.isBlank(w_cLocation)))
		{
			try
			{
				if(param.getSelectLoadIdmAuto())
				{
					// Obtain the presence of Relocation rack Package.
					wsearchKey = new WareNaviSystemSearchKey();
					WareNaviSystem wms = (WareNaviSystem) wWarenaviHandler.findPrimary(wsearchKey);
					// Relocation rack package
					String idmpack = wms.getIdmPack();
					if (idmpack.equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
					{
						// Search for Empty.
						EmptyLocation wEmpLocation = oIdmEmpLocSel.selectEmptyLocation();
						if (wEmpLocation.getReturnCode() == EmptyLocation.RET_OK)
						{
							parameter.setCaseLocation(wEmpLocation.getLocation());
							parameter.setPieceLocation(wEmpLocation.getLocation());
						}
					}
				}
			}
			catch (NoPrimaryException e)
			{
				// 6006040=Data mismatch occurred. Please refer to the log TABLE={0}
				RmiMsgLogClient.write("6006040" + wDelim + "WareNaviSystem", this.getClass().getName());
				// Throw ReadWriteException here.(Do not need to set error message.)
				throw (new ReadWriteException(e.getMessage()));
			}
		}

		// Set the Case Qty / Piece Qty.
		if (parameter.getTotalPlanQty() > 0)
		{
			// Calculate the Case Qty / Piece Qty.
			if (parameter.getEnteringQty() > 0)
			{
				parameter.setPlanCaseQty(parameter.getTotalPlanQty() / parameter.getEnteringQty());
				parameter.setPlanPieceQty(parameter.getTotalPlanQty() % parameter.getEnteringQty());
			}
			else
			{
				parameter.setPlanCaseQty(0);
				parameter.setPlanPieceQty(parameter.getTotalPlanQty());
			}
		}

		// Set Case/Piece division only when planned qty is entered.
		if (parameter.getTotalPlanQty() > 0)
		{
			// Identify the Case/Piece division.
			parameter.setCasePieceflg(CasePieceFlagCHECK(parameter));
		}

		// Batch No.
		parameter.setBatchNo(batno);
		// Add dvision
		parameter.setRegistKbn(StoragePlan.REGIST_KIND_HOST);
		// Terminal No.
		parameter.setTerminalNumber(param.getTerminalNumber());
		// Worker Code
		parameter.setWorkerCode(param.getWorkerCode());
		// Worker Name
		parameter.setWorkerName(param.getWorkerName());
		// Name of Add Process
		parameter.setRegistPName(pName);
		// Last update process name
		parameter.setLastUpdatePName(pName);

		return parameter;
	}
	/**
	 * Execute the process for checking the item (count) in the designated data.
	 * Allow the invoking side to set the error message and write it into the extraction error file via the invoking side.
	 * @param externalstoragedata Designate the ExternalStorageData object throws.
	 * @param DRR Designate the DataLoadCsvReader object.
	 * @param errlist Designate the DataLoadStatusCsvFileWriter object.
	 * @return Return true when normal, false when error occurs.
	 * @throws ReadWriteException If database error occurs
	 */
	protected boolean check(StorageSupportParameter parameter, DataLoadCsvReader DRR, DataLoadStatusCsvFileWriter errlist)
		throws ReadWriteException
	{
		boolean ret = true;
		int ErrorCode = 0;

		// Error if planned qty in the loaded data is 0 or smaller.
		if (parameter.getTotalPlanQty() <= 0)
		{
			// 6022005 = Data with 0 or less Planned Qty. is found. File: {0} Line: {1}file:{0} line:{1}
			ErrorCode = 6022005;
			ret = false;
		}

		if (!ret)
		{
			// Record the process error in the log.
			String[] msg = { DRR.getFileName(), DRR.getLineNo()};
			setMessage( ErrorCode + wDelim + DRR.getFileName() + wDelim + DRR.getLineNo());
			RmiMsgLogClient.write(ErrorCode, "StorageDataLoader", msg);
			// Write error contents additionally in the extraction error file generation class.
			errlist.addStatusCsvFile(DataLoadStatusCsvFileWriter.STATUS_ERROR, DRR.getLineData(), getMessage());
		}

		return ret;
	}

	/**
	 * Obtain the Path and file name of Storage Plan file.
	 * @return Storage Plan file name (path + file name)
	 * @throws ScheduleException Announce it when unexpected exception occurs.
	 */
	protected String GetStorageFile() throws ScheduleException
	{
		try
		{
			IniFileOperation IO = new IniFileOperation(WmsParam.ENVIRONMENT);
			String DataLoadPath = IO.get("DATALOAD_FOLDER", "STRAGE_SUPPORT");
			String DataLoadFile = IO.get("DATALOAD_FILENAME", "STRAGE_SUPPORT");
			return DataLoadPath + DataLoadFile;
		}
		catch (ReadWriteException e)
		{
			//  Environmental info file was not found.{0}
			Object[] msg = new Object[1];
			msg[0] = WmsParam.ENVIRONMENT;
			RmiMsgLogClient.write(new TraceHandler(6026004, e), "StorageDataLoader", msg);
			throw new ScheduleException();
		}
	}

	/**
	 * Identify Case/Piece division of the designated data.
	 * @param  parameter Designate the StorageSupportParameter object.
	 * @return Case/Piece division
	 */
	protected String CasePieceFlagCHECK(StorageSupportParameter parameter)
	{
		String casepieceflag = "";

		// Require to set either of Piece Item Storage Location or Case Item Storage Location.
		if (StringUtil.isBlank(parameter.getPieceLocation()) && !StringUtil.isBlank(parameter.getCaseLocation())
		|| (!StringUtil.isBlank(parameter.getPieceLocation()) && StringUtil.isBlank(parameter.getCaseLocation())))
		{
			// None
			casepieceflag = StorageSupportParameter.CASEPIECE_FLAG_NOTHING;
		}
		else
		{
			// Planned work case qty is more than 0 (zero) and Planned work piece qty is 0 or less
			if (parameter.getPlanCaseQty() > 0 && parameter.getPlanPieceQty() <= 0)
			{
				// Case
				casepieceflag = StorageSupportParameter.CASEPIECE_FLAG_CASE;
			}
			// Planned work case qty is not more than 0 (zero) and Planned work piece qty is larger than 0
			if (parameter.getPlanCaseQty() <= 0 && parameter.getPlanPieceQty() > 0)
			{
				// Piece
				casepieceflag = StorageSupportParameter.CASEPIECE_FLAG_PIECE;
			}
			// Both Planned work case qty and Planned work piece qty are larger than 0
			if (parameter.getPlanCaseQty() > 0 && parameter.getPlanPieceQty() > 0)
			{
				// Mixed
				casepieceflag = StorageSupportParameter.CASEPIECE_FLAG_MIXED;
			}
		}

		return casepieceflag;
	}

	// Private methods -----------------------------------------------
}
//end of class
