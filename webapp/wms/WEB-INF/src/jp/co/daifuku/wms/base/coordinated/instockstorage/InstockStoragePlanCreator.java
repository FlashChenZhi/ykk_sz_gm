package jp.co.daifuku.wms.base.coordinated.instockstorage;
//#CM644167
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.DisplayUtil;
import jp.co.daifuku.wms.base.entity.SystemDefine; 
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;
import jp.co.daifuku.wms.base.dbhandler.StockAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StockHandler;
import jp.co.daifuku.wms.base.dbhandler.StockSearchKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanHandler;
import jp.co.daifuku.wms.base.dbhandler.StoragePlanSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemHandler;
import jp.co.daifuku.wms.base.dbhandler.WareNaviSystemSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.Stock;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM644168
/**
 * Designer : T.Terasaki <BR>
 * Maker : T.Terasaki <BR>
 * <BR>
 * The processing class to generate plan information, work information, and the inventory information on the stock from work information to do completion of receiving. <BR>
 * The main method is the method of making the stock plan <CODE>(startMakePlan) </CODE><BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/11/02</TD><TD>M.Inoue</TD><TD>New Making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/07 06:09:39 $
 * @author  $Author: suresh $
 */
public class InstockStoragePlanCreator extends Object
{
	//#CM644169
	// Class fields --------------------------------------------------
	//#CM644170
	/**
	 * Storage plan information handler
	 */
	private StoragePlanHandler wPlanHandler = null;

	//#CM644171
	/**
	 * Storage plan information retrieval key
	 */
	private StoragePlanSearchKey wPlanKey = null;

	//#CM644172
	/**
	 * Storage plan information update key
	 */
	private StoragePlanAlterKey wPlanAltKey = null;

	//#CM644173
	/**
	 * Work information handler
	 */
	private WorkingInformationHandler wWorkHandler = null;

	//#CM644174
	/**
	 * Work information retrieval key
	 */
	private WorkingInformationSearchKey wWorkKey = null;

	//#CM644175
	/**
	 * Work information update key
	 */
	private WorkingInformationAlterKey wWorkAltKey = null;

	//#CM644176
	/**
	 * Stock information handler
	 */
	private StockHandler wStockHandler = null;

	//#CM644177
	/**
	 * Stock information retrieval key
	 */
	private StockSearchKey wStockKey = null;

	//#CM644178
	/**
	 * Stock information update key
	 */
	private StockAlterKey wStockAltKey = null;
	
	//#CM644179
	/**
	 * Sequence Handler
	 */
	private SequenceHandler wSequenceHandler = null;

	//#CM644180
	//	Class variables -----------------------------------------------
	//#CM644181
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM644182
	/**
	 * Message maintenance area<BR>
	 * Use it to maintain the content when the condition error etc. occur by calling each method. 
	 */
	protected String wMessage = "";

	//#CM644183
	/**
	 * Processing name
	 */
	private static String wProcessName = "InstockStoragePlanCreator";
	
	//#CM644184
	// Class method --------------------------------------------------
	//#CM644185
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/07 06:09:39 $");
	}
	//#CM644186
	// Constructors --------------------------------------------------
	//#CM644187
	/**
	 * Initialize this class. 
	 */
	public InstockStoragePlanCreator()
	{
	}
	
	//#CM644188
	/**
	 * Use this constructor when DB is retrieved and updated by using this class.  <BR>
	 * Make an instance necessary for updating the retrieval. 
	 * 
	 * @param conn Connection object with data base
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public InstockStoragePlanCreator(Connection conn) throws ReadWriteException
	{
		//#CM644189
		// Storage plan information
		wPlanAltKey = new StoragePlanAlterKey();
		wPlanHandler = new StoragePlanHandler(conn);
		wPlanKey = new StoragePlanSearchKey();
    	
		//#CM644190
		// Work information
		wWorkAltKey = new WorkingInformationAlterKey();
		wWorkHandler = new WorkingInformationHandler(conn);
		wWorkKey = new WorkingInformationSearchKey();
    	
		//#CM644191
		// Stock information
		wStockAltKey = new StockAlterKey();
		wStockHandler = new StockHandler(conn);
		wStockKey = new StockSearchKey();

		//#CM644192
		// Sequence
		wSequenceHandler = new SequenceHandler(conn);
	}

	//#CM644193
	// Public methods ------------------------------------------------
	//#CM644194
	/**
	 * The method of making Storage plan. <BR>
	 * Generate plan information, Work information, and Stock information of the storage for the completion of receiving Work information. <BR>
	 * Return true when processing ends normally and return false when failing. 
	 * <DIR>
	 *   1.When the TC/DC flag of Work information is DC, and work actual number >0, the object data is judged.<BR>
	 *     <DIR>
	 *     End normally when judging outside the object data. <BR>
	 *     </DIR> 
	 *   2.Retrieve the system definition table. <BR> 
	 *   3.Judge processing off the subject, except when there is a stock package of the system definition table, and end normally. <BR> 
	 *   4.Numbers the batch No from the Sequence table. <BR> 
	 *   5.Acquire the work date from the system definition table. <BR> 
	 *   6.Edit the Storage plan data and register. <BR> 
	 *   7.Work information from Storage plan information, Stock information is registred <CODE>(createStorageWorkInfo) </CODE><BR> 
	 *     <DIR>
	 *     </DIR> 
	 *   <BR> 
	 * </DIR>
	 * 
	 * @param 	conn Instance to maintain connection with data base. 
	 * @param	workinfo Work information renewed in completion of receiving
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @return True in case of processing normally, False when processing fails or it is not treatable
	 */
	public boolean startMakePlan( Connection conn, 
    								WorkingInformation workinfo)
	                 				throws ReadWriteException
	{

		try
		{
			//#CM644195
			// Object data check(DC goods and the actual number is 1 or more. )
			if ((!(workinfo.getTcdcFlag().equals(SystemDefine.TCDC_FLAG_DC))) ||
			    (workinfo.getResultQty() <= 0))
			{
				//#CM644196
				// Do not process it for the data of off the subject. 
				return true;
			}
			
			//#CM644197
			// Retrieval of system definition information
			WareNaviSystemHandler warenaviHandler = new WareNaviSystemHandler(conn);
			WareNaviSystemSearchKey wsearchKey = new WareNaviSystemSearchKey();
			WareNaviSystem wms = (WareNaviSystem) warenaviHandler.findPrimary(wsearchKey);
			if (wms == null)
			{
				//#CM644198
				// 6027006=No adjustment of data was generated. Refer to the log. TABLE={0}
				RmiMsgLogClient.write("6006040" + wDelim + "WareNaviSystem", wProcessName);
				//#CM644199
				// Here, throws ReadWriteException. (The set of the error message is unnecessary.)
				throw (new ReadWriteException());
			}
			if (!wms.getStoragePack().equals(WareNaviSystem.PACKAGE_FLAG_ADDON))
			{
				//#CM644200
				// Do not process it, except when there is a stock package. 
				return true;
			}
			
			//#CM644201
			// Acquire batch No.
			SequenceHandler sequenceHandler = new SequenceHandler(conn);
			String batch_seqno = "";
			batch_seqno = sequenceHandler.nextStoragePlanBatchNo();
		
			//#CM644202
			// Work date (system definition date)
			String sysdate = wms.getWorkDate();
				
			//#CM644203
			// Storage plan data edit and registration
			StoragePlan storagePlan = null;
			storagePlan = createStorage(workinfo, sysdate, batch_seqno);
			
			//#CM644204
			// Registration of Work information and Stock information
			createStorageWorkInfo(storagePlan, wProcessName);
			
			return true;
			
		}
		catch (NoPrimaryException e)
		{
			throw new ReadWriteException(e.getMessage());
		}

	}

	//#CM644205
	/**
	 * Make Work information and Stock information from Storage plan information. <BR>
	 * Make the case work and the piece work when Storage plan information is a mixture. <BR>
	 * 
	 * @param storagePlan Made Storage plan information
	 * @param processName Processing name
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void createStorageWorkInfo(StoragePlan storagePlan, String processName) throws ReadWriteException
	{
		String casePieceFlg = "";
		String locationNo = "";
		int planQty = 0;

		WorkingInformation workInfo = null;

		for (int i = 0; i < 2; i++)
		{
			//#CM644206
			// Things except the mixture of plan information (Case - piece - unspecified )
			if (!storagePlan.getCasePieceFlag().equals(StoragePlan.CASEPIECE_FLAG_MIX))
			{
				casePieceFlg = storagePlan.getCasePieceFlag();
				planQty = storagePlan.getPlanQty();
				if (storagePlan.getCasePieceFlag().equals(StoragePlan.CASEPIECE_FLAG_CASE))
				{
					//#CM644207
					// Case
					locationNo = storagePlan.getCaseLocation();
				}
				else if (storagePlan.getCasePieceFlag().equals(StoragePlan.CASEPIECE_FLAG_PIECE))
				{
					//#CM644208
					// Piece
					locationNo = storagePlan.getPieceLocation();
				}
				else
				{
					//#CM644209
					// None
					locationNo = storagePlan.getCaseLocation();
					if (locationNo.trim().equals(""))
					{
						locationNo = storagePlan.getPieceLocation();
					}
				}

			}
			//#CM644210
			// Plan information is the first processing in mix. (Process Case. )
			else if (i == 0)
			{
				//#CM644211
				// Case
				planQty = DisplayUtil.getCaseQty(storagePlan.getPlanQty(), storagePlan.getEnteringQty()) * storagePlan.getEnteringQty();
				locationNo = storagePlan.getCaseLocation();
				casePieceFlg = StoragePlan.CASEPIECE_FLAG_CASE;

			}
			//#CM644212
			// Plan information is the second processing in mix. (Process Piece. )
			else
			{
				//#CM644213
				// Piece
				planQty = DisplayUtil.getPieceQty(storagePlan.getPlanQty(), storagePlan.getEnteringQty());
				locationNo = storagePlan.getPieceLocation();
				casePieceFlg = StoragePlan.CASEPIECE_FLAG_PIECE;

			}
			//#CM644214
			// Make Work information. 
			workInfo = createWorkInfo(storagePlan, locationNo, planQty, casePieceFlg, processName);

			//#CM644215
			// Make Stock information. 
			createStock(workInfo, processName);

			//#CM644216
			// The loop comes off by one time, except for the mixture. 
			if (!storagePlan.getCasePieceFlag().equals(StoragePlan.CASEPIECE_FLAG_MIX))
			{
				break;
			}

		}
	}
	
	//#CM644217
	/**
	 * Make Work information from Storage plan information. <BR>
	 * Moreover, return made Work information. <BR>
	 * @param StoragePlan Storage plan information
	 * @param locationNo Storage location
	 * @param planQty Storage plan qty
	 * @param casePieceFlg Case/Piece flag
	 * @param processName Processing name
	 * @return Registered Work information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public WorkingInformation createWorkInfo(StoragePlan storagePlan, String locationNo, int planQty, String casePieceFlg, String processName)
		throws ReadWriteException
	{
		WorkingInformation workInfo = new WorkingInformation();
		
		try
		{
			//#CM644218
			// Numbering
			String job_seqno = wSequenceHandler.nextJobNo();
			String stockId_seqno = wSequenceHandler.nextStockId();

			//#CM644219
			// Work No.
			workInfo.setJobNo(job_seqno);
			//#CM644220
			// Work flag
			workInfo.setJobType(WorkingInformation.JOB_TYPE_STORAGE);
			//#CM644221
			// Consolidating Work No.
			workInfo.setCollectJobNo(job_seqno);
			//#CM644222
			// Status flag : Stand by
			workInfo.setStatusFlag(WorkingInformation.STATUS_FLAG_UNSTART);
			//#CM644223
			// Work start flag : Started
			workInfo.setBeginningFlag(WorkingInformation.BEGINNING_FLAG_STARTED);
			//#CM644224
			// Plan list key
			workInfo.setPlanUkey(storagePlan.getStoragePlanUkey());
			//#CM644225
			// Stock ID
			workInfo.setStockId(stockId_seqno);
			//#CM644226
			// Area No.
			//#CM644227
			// Location No.
			workInfo.setLocationNo(locationNo);
			//#CM644228
			// Plan date
			workInfo.setPlanDate(storagePlan.getPlanDate());
			//#CM644229
			// Consignor Code
			workInfo.setConsignorCode(storagePlan.getConsignorCode());
			//#CM644230
			// Consignor Name
			workInfo.setConsignorName(storagePlan.getConsignorName());
			//#CM644231
			// Supplier Code
			workInfo.setSupplierCode(storagePlan.getSupplierCode());
			//#CM644232
			// Supplier Name
			workInfo.setSupplierName1(storagePlan.getSupplierName1());
			//#CM644233
			// Item Code
			workInfo.setItemCode(storagePlan.getItemCode());
			//#CM644234
			// Item Name
			workInfo.setItemName1(storagePlan.getItemName1());
			//#CM644235
			// Number of work schedules(number of host schedules) : Storage plan qty
			//#CM644236
			// When the Case/Piece flag is a mixture : Total of Case Plan qty and Piece plab qty
			workInfo.setHostPlanQty(storagePlan.getPlanQty());
			//#CM644237
			// Work plan qty : Storage plan qty
			//#CM644238
			// When the Case/Piece flag is a mixture : Case Plan qty, Or Piece plan qty
			workInfo.setPlanQty(planQty);
			//#CM644239
			// Work possible qty : Storage plan qty
			//#CM644240
			// When the Case/Piece flag is a mixture : Case Plan qty, Or Piece plan qty
			workInfo.setPlanEnableQty(planQty);
			//#CM644241
			// Work actual qty
			workInfo.setResultQty(0);
			//#CM644242
			// Work shortage qty
			workInfo.setShortageCnt(0);
			//#CM644243
			// Reserved qty
			workInfo.setPendingQty(0);
			//#CM644244
			// Packed qty per case
			workInfo.setEnteringQty(storagePlan.getEnteringQty());
			//#CM644245
			// Packed qty per bundle
			workInfo.setBundleEnteringQty(storagePlan.getBundleEnteringQty());
			//#CM644246
			// Case/Piece flag (Mode of packing) 
			workInfo.setCasePieceFlag(casePieceFlg);
			//#CM644247
			// Case/Piece flag (Work form) 
			workInfo.setWorkFormFlag(casePieceFlg);
			//#CM644248
			// CaseITF
			workInfo.setItf(storagePlan.getItf());
			//#CM644249
			// Bundle ITF
			workInfo.setBundleItf(storagePlan.getBundleItf());
			//#CM644250
			// TC/DC flag
			workInfo.setTcdcFlag(WorkingInformation.TCDC_FLAG_DC);
			//#CM644251
			// Stand by report flag
			workInfo.setReportFlag(WorkingInformation.REPORT_FLAG_NOT_SENT);
			//#CM644252
			// Batch No.(Schedule No)
			workInfo.setBatchNo(storagePlan.getBatchNo());
			//#CM644253
			// Plan information registration date
			workInfo.setPlanRegistDate(new Date());
			//#CM644254
			// Registration Processing name
			workInfo.setRegistPname(processName);
			//#CM644255
			// Last update Processing name
			workInfo.setLastUpdatePname(processName);
			
			//#CM644256
			// Registration of Work information
			wWorkHandler.create(workInfo);
		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		
		return workInfo;

	}
	
	//#CM644257
	/**
	 * Make Stock information based on made Work information. 
	 * 
	 * @param workInfo Work information
	 * @param processName Processing name
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	public void createStock(WorkingInformation workInfo, String processName) throws ReadWriteException
	{	
		try
		{
			Stock stock = new Stock();

			//#CM644258
			// Stock ID
			stock.setStockId(workInfo.getStockId());
			//#CM644259
			// Plan list key
			stock.setPlanUkey(workInfo.getPlanUkey());
			//#CM644260
			// Area No.
			//#CM644261
			// Location No.
			stock.setLocationNo(workInfo.getLocationNo());
			//#CM644262
			// Item Code
			stock.setItemCode(workInfo.getItemCode());
			//#CM644263
			// Item Name
			stock.setItemName1(workInfo.getItemName1());
			//#CM644264
			// Stock status : Waiting for Storage
			stock.setStatusFlag(Stock.STOCK_STATUSFLAG_RECEIVINGRESERVE);
			//#CM644265
			// Stock qty
			stock.setStockQty(0);
			//#CM644266
			// Drqwing qty
			stock.setAllocationQty(0);
			//#CM644267
			// Plan qty
			stock.setPlanQty(workInfo.getPlanQty());
			//#CM644268
			// Case/Piece flag(Mode of packing)
			stock.setCasePieceFlag(workInfo.getCasePieceFlag());
			//#CM644269
			// Consignor Code
			stock.setConsignorCode(workInfo.getConsignorCode());
			//#CM644270
			// Consignor Name
			stock.setConsignorName(workInfo.getConsignorName());
			//#CM644271
			// Supplier Code
			stock.setSupplierCode(workInfo.getSupplierCode());
			//#CM644272
			// Supplier Name
			stock.setSupplierName1(workInfo.getSupplierName1());
			//#CM644273
			// Packed qty per case
			stock.setEnteringQty(workInfo.getEnteringQty());
			//#CM644274
			// Packed qty per bundle
			stock.setBundleEnteringQty(workInfo.getBundleEnteringQty());
			//#CM644275
			// CaseITF
			stock.setItf(workInfo.getItf());
			//#CM644276
			// Bundle ITF
			stock.setBundleItf(workInfo.getBundleItf());
			//#CM644277
			// Registration Processing name
			stock.setRegistPname(processName);
			//#CM644278
			// Last update Processing name
			stock.setLastUpdatePname(processName);

			//#CM644279
			// Registration of Stock information
			wStockHandler.create(stock);
		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		catch (InvalidStatusException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}
		
	}
	
	//#CM644280
	// Package methods -----------------------------------------------

	//#CM644281
	// Protected methods ---------------------------------------------

	//#CM644282
	// Private methods -----------------------------------------------
	//#CM644283
	/**
	 * Make Storage plan information from input information. 
	 * 
	 * @param	workinfo Work information renewed in completion of receiving
	 * @param	sysDate	Work date
	 * @param	batchNo	Batch No.
	 * @return Made Storage plan information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	private StoragePlan createStorage( WorkingInformation workinfo,
										String sysDate,
										String batchNo)
											throws ReadWriteException
	{
						
		StoragePlan resultPlan = new StoragePlan();
		try
		{
			//#CM644284
			// Storage plan list key	
			String planUkey_seqno = wSequenceHandler.nextStoragePlanKey();
			resultPlan.setStoragePlanUkey(planUkey_seqno);
			//#CM644285
			// Status flag : Stand by
			resultPlan.setStatusFlag(SystemDefine.STATUS_FLAG_UNSTART);
			//#CM644286
			// Storage plan date
			resultPlan.setPlanDate(sysDate);
			//#CM644287
			// Consignor Code
			resultPlan.setConsignorCode(workinfo.getConsignorCode());
			//#CM644288
			// Consignor Name
			resultPlan.setConsignorName(workinfo.getConsignorName());
			//#CM644289
			// Supplier Code
			resultPlan.setSupplierCode(workinfo.getSupplierCode());
			//#CM644290
			// Supplier Name
			resultPlan.setSupplierName1(workinfo.getSupplierName1());
			//#CM644291
			// Item Code
			resultPlan.setItemCode(workinfo.getItemCode());
			//#CM644292
			// Item Name
			resultPlan.setItemName1(workinfo.getItemName1());
			//#CM644293
			// Storage plan qty <- Actual receiving qty
			resultPlan.setPlanQty(workinfo.getResultQty());
			//#CM644294
			// Actual Storage qty
			resultPlan.setResultQty(0);
			//#CM644295
			// Storage shortage qty
			resultPlan.setShortageCnt(0);
			//#CM644296
			// Packed qty per case
			resultPlan.setEnteringQty(workinfo.getEnteringQty());
			//#CM644297
			// Packed qty per bundle
			resultPlan.setBundleEnteringQty(workinfo.getBundleEnteringQty());
			//#CM644298
			// Case/Piece flag
			//#CM644299
			// Packed qty per case = in case of 0
			if (workinfo.getEnteringQty() == 0)
			{
				resultPlan.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}
			//#CM644300
			// Packed qty per case > in case of Storage qty
			else if (workinfo.getEnteringQty() > workinfo.getResultQty())
			{
				resultPlan.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_PIECE);
			}
			else
			{
				//#CM644301
				// When Storage qty can be divided by Packed qty per case
				int Remainder = workinfo.getResultQty() % workinfo.getEnteringQty();
				if (Remainder == 0)
				{
					resultPlan.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_CASE);
				}
				else
				{
					resultPlan.setCasePieceFlag(SystemDefine.CASEPIECE_FLAG_MIX);
				}
			}
			//#CM644302
			// CaseITF
			resultPlan.setItf(workinfo.getItf());
			//#CM644303
			// Bundle ITF
			resultPlan.setBundleItf(workinfo.getBundleItf());
			//#CM644304
			// Piece item Storage location	//FTTB Correction main point with shelf crack
			resultPlan.setPieceLocation("");
			//#CM644305
			// Case item Storage location	//FTTB Correction main point with shelf crack
			resultPlan.setCaseLocation("");
			//#CM644306
			// Expiry date
			//#CM644307
			// Lot No.
			//#CM644308
			// Plan information comment
			resultPlan.setPlanInformation(workinfo.getPlanInformation());
			//#CM644309
			// Batch No.(Schedule No.) <- Numbered batch no
			resultPlan.setBatchNo(batchNo);
			//#CM644310
			// Worker code
			resultPlan.setWorkerCode(workinfo.getWorkerCode());
			//#CM644311
			// Worker name
			resultPlan.setWorkerName(workinfo.getWorkerName());
			//#CM644312
			// Terminal No. RFTNo.
			resultPlan.setTerminalNo(workinfo.getTerminalNo());
			//#CM644313
			// Registration flag
			resultPlan.setRegistKind(SystemDefine.REGIST_KIND_INSTOCK);
			//#CM644314
			// Registration Processing name
			resultPlan.setRegistPname(wProcessName);
			//#CM644315
			// Last update Processing name
			resultPlan.setLastUpdatePname(wProcessName);
			
			//#CM644316
			// Registration of Storage plan information
			wPlanHandler.create(resultPlan);

		}
		catch (DataExistsException e)
		{
			throw (new ReadWriteException(e.getMessage()));
		}

		return resultPlan;
	}
}
