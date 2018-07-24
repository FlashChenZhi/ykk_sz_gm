//#CM720561
//$Id: Id0330Process.java,v 1.3 2007/02/07 04:19:39 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

//#CM720562
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.sql.SQLException;
import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.rft.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM720563
/**
 * Designer :E.Takeda <BR>
 * Maker :E.Takeda<BR>
 * <BR>
 * Allow this class to execute the process responding to the request from RFT for starting Order Picking (ID0330). <BR>
 * Search for the corresponding Order Picking info in the work status using functions provided by Id0330Operate class, and  generate a response electronic statement to send to RFT. <BR>
 * <BR>
 * Process for request for starting Order Picking ( <CODE>processReceivedId()</CODE> method) <BR>
 * <BR>
 * <DIR>Search through the work status Work Status by Picking Work division (Order/Area) obtained from the received electronic statement, using the function of the ID0330Operate class, and
 * obtain the Order Picking file. <BR>
 * <BR>
 * <OL>
 * <LI>For Order-specified data: Check whether all the designated data is workable or not using the function of ID0330Operate class. <BR>
 * If there is one or more data with no work (impossible to work), return error for a whole the data and check each order for error. <BR>
 * If normal, obtain the Order Picking data from the work status and generate an Order Picking file. Return normal as a response.<BR> </LI>
 * <BR>
 * <LI>For Area-specified data: Obtain all the work data of the Order No. determined in the class for the process to determine Order No. for specifying Area from workable data that contains the designated Area No. and Zone No., and
 * generate a Order Picking file if normal. Return normal as a response. <BR> </LI>
 * </OL>
 * Generate a Work file together with a record in the "processing" data storage info using the content of the file.<BR>
 *  <p>
 *  Generate a storage information of data with status "Processing". If it already resided, update it.<BR>
 *   [Update Conditions] 
 *  <UL><LI>RFTNo</LI>
 *      <LI>Line No</LI>
 *  </UL>
 *   [Contents to be updated/generated] 
 *  <UL><LI>RFTNo (to create)</LI>
 *  	<LI>file name:ID5330.txt</LI>
 *  	<LI>Line No</LI>
 *  	<LI>work data (1 data)</LI>
 *  </UL>
 * If completed normal, update the electronic statement field item of the RFT Work Status to the content of the generated response electronic statement.<BR>
 *  [Update Conditions] 
 *  <UL><LI>RFTNo</LI></UL>
 *  [UpdateContent] 
 *  <UL><LI>Electronic statement: Generated response electronic statement</LI>
 *      <LI>Last update date/time: Date/time on system</LI>
 *      <LI>Last update process name: ID0330</LI>
 *  </UL> 
 * Output the log when if error occurs while updating.<BR>
 * Generate a sending electronic statement and set the characters to be sent to the Sending buffer. <BR>
 * 
 * 
 * </DIR> <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACTING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor">
 * <TD>Date</TD>
 * <TD>Name</TD>
 * <TD>Comment</TD>
 * </TR>
 * <TR>
 * <TD>2005/11/04</TD>
 * <TD>E.Takeda</TD>
 * <TD>created this class</TD>
 * <TR></TABLE> <BR>
 * 
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:39 $
 * @author $Author: suresh $
 */

public class Id0330Process extends IdProcess
{
	//#CM720564
	//  Class fields --------------------------------------------------
	//#CM720565
	/**
	 * A field that represents this class.
	 */
	private static final String CLASS_NAME = "Id0330Process";
	
	//#CM720566
	/**
	 * Name of Process for updating
	 */
	private static final String PROCESS_NAME = "ID0330";

	//#CM720567
	//Class variables -----------------------------------------------

	//#CM720568
	//Class methods -------------------------------------------------
	//#CM720569
	/**
	 * Return the version of this class.
	 * 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2007/02/07 04:19:39 $");
	}

	//#CM720570
	//Constructors --------------------------------------------------
	//#CM720571
	//Public methods ------------------------------------------------
	//#CM720572
	/**
	 * Execute the process for the request for starting Order Picking (ID0330). <BR>
	 * Receive the received electronic statement in the form of a bite array, and generate its corresponding response (ID5330) in the form of a bite array. <BR>
	 * <BR>
	 * First of all, ensure that "Processing Daily Update" is not active. <BR>
	 * If the data status is "Processing Daily Update", set 5 ("Processing Daily Update") for the Response flag. <BR>
	 * Ensure that there is a record of the Result by Worker exists. <BR>
	 * Create a new worker info if there is no worker result with the corresponding work date, Worker, and RFTNo. <BR>
	 * <BR>
	 * Search through the work status by Picking Work division (Order/Area) obtained from the received electronic statement, and obtain the Order Picking data. <BR>
	 * Generate a work file using the obtained content.<BR>
	 * <BR>
	 * <DIR> [Work division: 1  Order ] 
	 * <UL>
	 * <LI>Obtain the Picking data of the designated Order No.
	 * <LI>Sort in the order of Area > Zone > Location.
	 * <LI>Exclude data with no Order No. designated from the target.
	 * </UL>
	 * <p>
	 *  [Work division: 2 Area]  <BR>
	 * <UL>
	 * <LI>Obtain all the work with the Order No. determined in the Process class to determine the Order No. for specifying Area, from the work data containing the specified Area No. and Zone No. 
	 * 
	 * <LI>Sort the data in the ascending order of Area, Zone and Location in the designated Area and Zone, and
	 * obtain data so that the works with values smaller than the values of the designated Area No. and Zone No. follow to the work with the designated value.
	 * </UL>
	 * </DIR>
	 * <p>
	 * 	Generate simultaneously a record in the "processing" data storage info using the content of the work file.<BR>
	 *  If the corresponding data exists, update it.<BR>
	 *  <DIR>
	 *  <p>
	 *  Generate a storage information of data with status "Processing". If it already resided, update it.<BR>
	 *   [Update Conditions] 
	 *  <UL><LI>RFTNo</LI>
	 *      <LI>Line No</LI>
	 *  </UL>
	 *   [Contents to be updated/generated] 
	 *  <UL><LI>RFTNo (to create)</LI>
	 *  	<LI>file name:ID5330.txt</LI>
	 *  	<LI>Line No</LI>
	 *  	<LI>work data (1 data)</LI>
	 *  </UL>
	 * </DIR> Update the Picking Plan Info. <BR>
	 * <BR>
	 * Update Work Status. <BR>
	 * <BR>
	 * If there is no worker info with the corresponding work date, Worker, RFTNo., create a new Worker Result info. <BR>
	 * <BR>
	 * If completed normal, update the electronic statement field item of the RFT Work Status to the content of the generated response electronic statement.<BR>
	 * Output the log when if error occurs while updating.<BR>
	 * Set the obtained result in the Sent electronic statement. <BR>
	 * 
	 *  [Update Conditions] 
	 *  <UL><LI>RFTNo</LI></UL>
	 *  [UpdateContent] 
	 *  <UL><LI>Electronic statement: Generated response electronic statement</LI>
	 *      <LI>Last update date/time: Date/time on system</LI>
	 *      <LI>Last update process name: ID0330</LI>
	 *  </UL>
	 * <BR>
	 * 
	 * @param rdt
	 *            Received buffer
	 * @param sdt
	 *            Buffer from which sends data
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		RFTId0330 rftId0330 = null;
		RFTId5330 rftId5330 = null;

		try
		{
			//#CM720573
			// Generate the instance for analyzing the Received electronic statement.
			rftId0330 = (RFTId0330) PackageManager.getObject("RFTId0330");
			rftId0330.setReceiveMessage(rdt);

			//#CM720574
			// Generate an instance for generating a sending electronic statement
			rftId5330 = (RFTId5330) PackageManager.getObject("RFTId5330");
		}
		catch (IllegalAccessException e)
		{
			//#CM720575
			// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME,
					"RFTId*330", e.getMessage());
			throw e;
		}

		//#CM720576
		// Obtain the RFT machine from the Received electronic statement.
		String rftNo = rftId0330.getRftNo();

		//#CM720577
		// Obtain the Worker code from the Received electronic statement.
		String workerCode = rftId0330.getWorkerCode();

		//#CM720578
		// Obtain the Consignor code from the Received electronic statement.
		String consignorCode = rftId0330.getConsignorCode();

		//#CM720579
		// Obtain the Planned Picking Date from the received electronic statement.
		String pickingPlanDate = rftId0330.getPlanDate();

		//#CM720580
		// Obtain the Case/Piece division (Work Type) from the received electronic statement.
		String workForm = rftId0330.getSelectCasePieceFlag();

		//#CM720581
		// Variable for maintaining the Order No.
		String[] orderNo = new String[4];
		//#CM720582
		// Obtain the Order No. (1) from the received electronic statement.
		orderNo[0] = rftId0330.getOrderNo_1();

		//#CM720583
		// Obtain the Order No. (2) from the received electronic statement.
		orderNo[1] = rftId0330.getOrderNo_2();

		//#CM720584
		// Obtain the Order No. (3) from the received electronic statement.
		orderNo[2] = rftId0330.getOrderNo_3();

		//#CM720585
		// Obtain the Order No. (4) from the received electronic statement.
		orderNo[3] = rftId0330.getOrderNo_4();

		//#CM720586
		// Obtain the Area No. from the received electronic statement.
		String areaNo = rftId0330.getAreaNo();

		//#CM720587
		// Obtain the Zone No. from the received electronic statement.
		String zoneNo = rftId0330.getZoneNo();

		//#CM720588
		// Obtain the Picking Work division from the received electronic statement.
		String pickWorkType = rftId0330.getPickWorkType();

		//#CM720589
		// Variable for maintaining the Consignor Name.
		String consignorName = "";

		//#CM720590
		// Variable for maintaining the Customer Code
		String customerCode[] = { "", "", "", "" };

		//#CM720591
		//Variable for maintaining the Customer Name
		String customerName[] = { "", "", "", "" };

		//#CM720592
		//Variable for maintaining the "Response By-Order" flag
		String ansCode[] = { "", "", "", "" };

		//#CM720593
		// Variable for maintaining the Work file name
		String workFileName = "";

		//#CM720594
		// Variable for maintaining the file record count.
		int fileRecordNumber = 0;

		//#CM720595
		// Variable for maintaining the whole Response flag
		String wholeAnsCode = "";

		//#CM720596
		// Variable for maintaining the detail of the error.
		String errDetails = RFTId5330.ErrorDetails.NORMAL;
		
		//#CM720597
		// Id5330DataFile object variable
		Id5330DataFile id5330DataFile = null;

		try
		{
		
			//#CM720598
			// Generate BaseOperate instance
			BaseOperate baseOperate = (BaseOperate) PackageManager
					.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//#CM720599
			//-----------------
			//#CM720600
			// Check Daily Maintenance Processing
			//#CM720601
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM720602
				// Return the Status flag as  "5: Daily Update Processing". 
				wholeAnsCode = RFTId5330.ANS_CODE_DAILY_UPDATING;
				for (int i = 0; i < orderNo.length; i++)
				{
					if (!orderNo[i].equals(""))
					{
						ansCode[i] = RFTId5330.ANS_CODE_DAILY_UPDATING;
					}
				}
			}
			else
			{
				//#CM720603
				//-----------------
				//#CM720604
				// Check for presence of Result by Worker.
				//#CM720605
				//-----------------
				WorkerResult[] workerResult = baseOperate.getWorkerResult(
						WorkingInformation.JOB_TYPE_RETRIEVAL, workerCode,
						rftNo);
				if (workerResult.length == 0)
				{
					//#CM720606
					// Create New Worker Result
					baseOperate.createWorkerResult(
							WorkingInformation.JOB_TYPE_RETRIEVAL, workerCode,
							rftNo);
					//#CM720607
					// commit
					wConn.commit();
				}

				//#CM720608
				// Generate the Id0330Operate instance.
				Id0330Operate id0330Operate = (Id0330Operate) PackageManager
						.getObject("Id0330Operate");
				id0330Operate.setConnection(wConn);
				WorkingInformation[] retrievalWorkData = null;
				//#CM720609
				// For data with Picking Work division "Area":
				if (pickWorkType.equals(RFTId0330.JOB_TYPE_AREA))
				{
					if(DisplayText.isPatternMatching(areaNo))
					{
						wholeAnsCode = RFTId5330.ANS_CODE_NULL;
						throw new NotFoundException(wholeAnsCode);
					}
					if(DisplayText.isPatternMatching(zoneNo))
					{
						wholeAnsCode = RFTId5330.ANS_CODE_NULL;
						throw new NotFoundException(wholeAnsCode);
					}

					//#CM720610
					// Obtain the Area Picking Plan Info from Id0330Operate instance.
					retrievalWorkData = id0330Operate.getRetrievalAreaData(
						consignorCode, pickingPlanDate, workForm, areaNo,
						zoneNo, rftNo, workerCode);
				}
				else
				{
					//#CM720611
					// Obtain the Order Picking Plan Info from Id0330Operate instance.
					retrievalWorkData = id0330Operate
							.getRetrievalOrderData(consignorCode,
									pickingPlanDate, orderNo, workForm,
									rftNo, workerCode);

										
					if(retrievalWorkData == null)
					{
						//#CM720612
						// If any or all of the target Orders is impossible to work:
						for (int i = 0; i < orderNo.length; i++)
						{
							if (orderNo[i].equals(""))
							{
								continue;
							}
							if(DisplayText.isPatternMatching(orderNo[i]))
							{
								ansCode[i] = RFTId5330.ANS_CODE_NULL;
								wholeAnsCode = ansCode[i];
								continue;
							}
							try
							{
								WorkingInformation tempWorkingInfo = id0330Operate
										.pickUpAndCheckOrderData(consignorCode,
												pickingPlanDate, workForm,
												orderNo[i], rftNo, workerCode,
												null, null, pickWorkType);
								customerCode[i] = tempWorkingInfo.getCustomerCode();
								customerName[i] = tempWorkingInfo
										.getCustomerName1();
								ansCode[i] = RFTId5330.ANS_CODE_NORMAL;
							}
							catch (NotFoundException ex)
							{
								ansCode[i] = ex.getMessage();
								if (!RFTId5330.checkAnsCode(ansCode[i]))
								{
									RftLogMessage.printStackTrace(6026015,
											LogMessage.F_ERROR, CLASS_NAME, ex);
									ansCode[i] = RFTId5330.ANS_CODE_ERROR;
									errDetails = RFTId5330.ErrorDetails.NULL;
								}
								wholeAnsCode = ansCode[i];
								
							}
						}
						if (!RFTId5330.checkAnsCode(wholeAnsCode))
						{
							wholeAnsCode = RFTId5330.ANS_CODE_ERROR;
							errDetails = RFTId5330.ErrorDetails.NULL;
						}
						wConn.rollback();
					}
				}

				if (retrievalWorkData != null)
				{
					//#CM720613
					// Name of the file to be sent
					workFileName = WmsParam.RFTSEND + rftNo + "\\"
							+ Id5330DataFile.ANS_FILE_NAME;
					//#CM720614
					// Generate an object for operating file and generate an Order Picking file.
					 id5330DataFile = (Id5330DataFile) PackageManager
							.getObject("Id5330DataFile");
					id5330DataFile.setFileName(workFileName);
					id5330DataFile.write(retrievalWorkData);

					//#CM720615
					// Store the history of work data file.
					id5330DataFile.saveHistoryFile();

					//#CM720616
					// Store the data with status "Processing".
					fileRecordNumber = retrievalWorkData.length;
					id5330DataFile.saveWorkingDataFile(rftNo, workFileName, fileRecordNumber, wConn);

					//#CM720617
					// Set it for the variable for maintaining
					consignorName = retrievalWorkData[0].getConsignorName();
					//#CM720618
					/* 2006/6/16 v2.6.0 START E.Takeda There was failure in the case where the Order No. (1) was blank for the Specify Order field:
					 *  the information of Order No. (2) was set for Order (1). Such failure was revised and reflected. */
					if (StringUtil.isBlank(orderNo[0]) && pickWorkType.equals(RFTId0330.JOB_TYPE_AREA))
					{
						orderNo[0] = retrievalWorkData[0].getOrderNo();
						customerCode[0] = retrievalWorkData[0]
								.getCustomerCode();
						customerName[0] = retrievalWorkData[0]
								.getCustomerName1();
					}
					//#CM720619
					/* 2006/6/16 END */

					for (int i = 0; i < orderNo.length; i++)
					{
						//#CM720620
						// Variable for maintaining the tentative work status
						WorkingInformation tempWorkingInfo = id0330Operate
								.pickUpOrderData(orderNo[i], retrievalWorkData);
						if (tempWorkingInfo != null)
						{
							customerCode[i] = tempWorkingInfo.getCustomerCode();
							customerName[i] = tempWorkingInfo
									.getCustomerName1();
							ansCode[i] = RFTId5330.ANS_CODE_NORMAL;
						}
					}
					//#CM720621
					//	Response flag: Normal
					wholeAnsCode = RFTId5330.ANS_CODE_NORMAL;
					//#CM720622
					// commit
					wConn.commit();
				}
			}
		}
		catch (NotFoundException e)
		{
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}
			wholeAnsCode = e.getMessage();
			if(! RFTId5330.checkAnsCode(wholeAnsCode))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				wholeAnsCode = RFTId5330.ANS_CODE_ERROR;
				errDetails = RFTId5330.ErrorDetails.NULL;				
			}
			else if (wholeAnsCode.equals(RFTId5330.ANS_CODE_ERROR))
			{
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				errDetails = RFTId5330.ErrorDetails.NULL;				
			}
		}
		//#CM720623
		// In the event of overflow:
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode + " PlanDate:"
					+ pickingPlanDate + " WorkForm:" + workForm + " RftNo:"
					+ rftNo + " WorkerCode:" + workerCode + "]";
			//#CM720624
			// 6026027=Length overflow. Process was aborted. {0}
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME,
					errData);
			try
			{
				errDetails = RFTId5330.ErrorDetails.COLLECTION_OVERFLOW;
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}
			wholeAnsCode = RFTId5330.ANS_CODE_ERROR;
		}
		catch (ReadWriteException e)
		{
			//#CM720625
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				errDetails = RFTId5330.ErrorDetails.DB_ACCESS_ERROR;
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}
			wholeAnsCode = RFTId5330.ANS_CODE_ERROR;
		}
		catch (IOException e)
		{
			//#CM720626
			// Output the log.
			//#CM720627
			// 6006020=File I/O error occurred. {0}
			RftLogMessage.printStackTrace(6006020, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}
			errDetails = RFTId5330.ErrorDetails.I_O_ERROR;
			wholeAnsCode = RFTId5330.ANS_CODE_ERROR;

		}
		catch (IllegalAccessException e)
		{
			//#CM720628
			// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "", e
					.getMessage());
			try
			{
				errDetails = RFTId5330.ErrorDetails.INSTACIATE_ERROR;
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);
			}

			wholeAnsCode = RFTId5330.ANS_CODE_ERROR;

		}
		catch (DataExistsException e)
		{
			//#CM720629
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			wholeAnsCode = RFTId5330.ANS_CODE_ERROR;
			errDetails = RFTId5330.ErrorDetails.DB_UNIQUE_KEY_ERROR; 
		}
		catch (SQLException e)
		{
			//#CM720630
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			wholeAnsCode = RFTId5330.ANS_CODE_ERROR;
			errDetails = RFTId5330.ErrorDetails.DB_ACCESS_ERROR;
		}
		catch (InvalidDefineException e)
		{
			//#CM720631
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			wholeAnsCode = RFTId5330.ANS_CODE_ERROR;
			errDetails = RFTId5330.ErrorDetails.PARAMETER_ERROR;
		}
		catch (LockTimeOutException e)
		{
			try
			{

				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002,
						LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			//#CM720632
			//Response flag: Maintenance in process via other terminal
			wholeAnsCode = RFTId5330.ANS_CODE_MAINTENANCE;
		}
		catch (Exception e)
		{
			//#CM720633
			// 6026015=Error occurred during ID process. {0}
			RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR,
					CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR,
						CLASS_NAME, sqlex);

			}
			//#CM720634
			//Response flag: Error
			wholeAnsCode = RFTId5330.ANS_CODE_ERROR;
			errDetails = RFTId5330.ErrorDetails.INTERNAL_ERROR;
		}

		//#CM720635
		// If the response category of Order set for is blank, set [Response flag for All].
		for (int i = 0; i < orderNo.length; i++)
		{
			if ((!orderNo[i].equals("")) && ansCode[i].equals(""))
			{
				ansCode[i] = wholeAnsCode;
			}
		}

		//#CM720636
		// Generate Response Electronic Statement.
		//#CM720637
		//STX
		rftId5330.setSTX();
		//#CM720638
		//SEQ
		rftId5330.setSEQ(0);
		//#CM720639
		//ID
		rftId5330.setID(RFTId5330.ID);
		//#CM720640
		//Period for sending by Handy
		rftId5330.setRftSendDate(rftId0330.getRftSendDate());
		//#CM720641
		//Period for sending by SERVER
		rftId5330.setServSendDate();
		//#CM720642
		//RFT Machine
		rftId5330.setRftNo(rftId0330.getRftNo());
		//#CM720643
		//Personnel Code
		rftId5330.setWorkerCode(rftId0330.getWorkerCode());
		//#CM720644
		//Consignor Code
		rftId5330.setConsignorCode(consignorCode);
		//#CM720645
		//Planned Picking Date
		rftId5330.setPlanDate(pickingPlanDate);
		//#CM720646
		//Case/Piece division
		rftId5330.setCasePieceFlag(rftId0330.getSelectCasePieceFlag());
		//#CM720647
		//Consignor Name
		rftId5330.setConsignorName(consignorName);

		//#CM720648
		//Order No. 1
		rftId5330.setOrderNo_1(orderNo[0]);
		//#CM720649
		//Customer Code 1
		rftId5330.setCustomerCode_1(customerCode[0]);
		//#CM720650
		//Customer Name 1
		rftId5330.setCustomerName_1(customerName[0]);
		//#CM720651
		//Response flag 1
		rftId5330.setAnsCode_1(ansCode[0]);

		//#CM720652
		//Order No. 2
		rftId5330.setOrderNo_2(orderNo[1]);
		//#CM720653
		//Customer Code 2
		rftId5330.setCustomerCode_2(customerCode[1]);
		//#CM720654
		//Customer Name 2
		rftId5330.setCustomerName_2(customerName[1]);
		//#CM720655
		//Response flag 2
		rftId5330.setAnsCode_2(ansCode[1]);

		//#CM720656
		//Order No. 3
		rftId5330.setOrderNo_3(orderNo[2]);
		//#CM720657
		//Customer Code 3
		rftId5330.setCustomerCode_3(customerCode[2]);
		//#CM720658
		//Customer Name 3
		rftId5330.setCustomerName_3(customerName[2]);
		//#CM720659
		//Response flag 3
		rftId5330.setAnsCode_3(ansCode[2]);

		//#CM720660
		//Order No. 4
		rftId5330.setOrderNo_4(orderNo[3]);
		//#CM720661
		//Customer Code 4
		rftId5330.setCustomerCode_4(customerCode[3]);
		//#CM720662
		//Customer Name 4
		rftId5330.setCustomerName_4(customerName[3]);
		//#CM720663
		//Response flag 4
		rftId5330.setAnsCode_4(ansCode[3]);
		//#CM720664
		//Input area No.
		rftId5330.setAreaNo(rftId0330.getAreaNo());
		//#CM720665
		//Input Zone No.
		rftId5330.setZoneNo(rftId0330.getZoneNo());
		//#CM720666
		//Picking Work division
		rftId5330.setPickWorkType(rftId0330.getPickWorkType());
		//#CM720667
		//Response file name
		rftId5330.setWorkFileName(workFileName);
		//#CM720668
		//Number of records in a file.
		rftId5330.setFileMaxRecordNo(fileRecordNumber);
		//#CM720669
		//Response flag
		rftId5330.setAnsCode(wholeAnsCode);
		//#CM720670
		//Details of error
		rftId5330.setErrDetails(errDetails);
		//#CM720671
		//ETX
		rftId5330.setETX();

		//#CM720672
		//Obtain electronic statement.
		rftId5330.getSendMessage(sdt);

		//#CM720673
		// For data with Response flag "0: Normal", store the response electronic statement to the file.
		try
		{
			if (wholeAnsCode.equals(RFTId5330.ANS_CODE_NORMAL))
			{
				rftId5330.saveResponseId(rftNo, PROCESS_NAME, wConn);
				wConn.commit();
			}
			else
			{	
				//#CM720674
				// Delete the history file.
				if (id5330DataFile != null)
				{
					try
					{
						id5330DataFile.deleteHistoryFile();						
					}
					catch(Exception e)
					{
					}
				}
			}
		}
		catch (Exception e)
		{
			//#CM720675
			// 6006002=Database error occurred.{0}
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
				//#CM720676
				// Delete all the "Processing" data file.
				RFTId5330.deleteWorkingData(rftNo, PROCESS_NAME, wConn);
				wConn.commit();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
				wConn.rollback();
			}

		}
	}

	//#CM720677
	//Package methods -----------------------------------------------

	//#CM720678
	//Protected methods ---------------------------------------------

	//#CM720679
	//Private methods -----------------------------------------------
}
//#CM720680
//end of class

