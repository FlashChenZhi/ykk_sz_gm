// $Id: Id0340Process.java,v 1.3 2007/02/07 04:19:41 suresh Exp $
package jp.co.daifuku.wms.retrieval.rft;

import java.sql.SQLException;

import jp.co.daifuku.common.DataExistsException;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LockTimeOutException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.common.OverflowException;
import jp.co.daifuku.wms.base.communication.rft.PackageManager;
import jp.co.daifuku.wms.base.communication.rft.RftLogMessage;
import jp.co.daifuku.wms.base.entity.WorkerResult;
import jp.co.daifuku.wms.base.entity.WorkingInformation;
import jp.co.daifuku.wms.base.rft.BaseOperate;
import jp.co.daifuku.wms.base.rft.IdProcess;

//#CM720811
/*
 * Copyright 2000-2005 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM720812
/**
 * Designer : Y.Taki<BR>
 * Maker : Y.Taki<BR>
 * <BR>
 * Allow this class to execute the process responding to the request from RFT for Item Picking data.<BR>
 * Search for the corresponding Item Picking work status in the work status using functions provided by RetrievalItemOperate class, and 
 * generate a response electronic statement to send to RFT. <BR>
 * <BR>
 * Process for requesting Item Picking data (<CODE>processReceivedId()</CODE>method)<BR>
 * <BR>
 * <DIR>
 *   Obtain the required info from the Received data.<BR>
 *   Search through the work status using the functions of RetrievalItemOperate class, and obtain the Item Picking data. <BR>
 *   Generate a sending electronic statement and set the characters to be sent to the Sending buffer.<BR>
 *   For data with Response flag "0: Normal", update the electronic statement field item of the RFT Work Status to the content of the generated response electronic statement.<BR>
 *    [Update Conditions] 
 *   <UL><LI>RFTNo</LI></UL>
 *    [UpdateContent] 
 *   <UL><LI>Response electronic statement: Generated response electronic statement</LI>
 *       <LI>Last update date/time: Date/time on system</LI>
 *       <LI>Last update process name: ID0340</LI>
 *   </UL>
 *   Output the log when error occurs during Update process.<BR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>T.Tani</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2007/02/07 04:19:41 $
 * @author  $Author: suresh $
 */
public class Id0340Process extends IdProcess
{
	private static final String CLASS_NAME = "Id0340Process";
	
	private static final String PROCESS_NAME = "ID0340";

	//#CM720813
	// Class variables -----------------------------------------------

	//#CM720814
	// Class method --------------------------------------------------
	//#CM720815
	/**
	 * Return the version of this class.
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return "$Revision: 1.3 $,$Date: 2007/02/07 04:19:41 $";
	}

	//#CM720816
	// Constructors --------------------------------------------------
	//#CM720817
	// Public methods ------------------------------------------------
	//#CM720818
	/**
	 * Execute the process for requesting Item Picking data (ID0340).<BR>
	 * Receive the received electronic statement in the form of a byte array and generate a response (ID5340) in the form of a byte array.<BR>
	 * <BR>
	 * First of all, ensure that "Processing Daily Update" is not active.<BR>
	 * If the data status is "Processing Daily Update", set 5 ("Processing Daily Update") for the Response flag.<BR>
	 * Ensure that there is a record of the Result by Worker exists.<BR>
	 * If no data exists, create a new one.<BR>
	 * <BR>
	 * Search for the Work Status using the requested condition and obtain the Item Picking data.<BR>
	 * Obtain one Item Picking data, if necessary, of the aggregation result.
	 * Set the obtained result in the Sent electronic statement.<BR>
	 * For data with Response flag "0: Normal", update the electronic statement field item of the RFT Work Status to the content of the generated response electronic statement.<BR>
	 *    [Update Conditions] 
	 *   <UL><LI>RFTNo</LI></UL>
	 *    [UpdateContent] 
	 *   <UL><LI>Response electronic statement: Generated response electronic statement</LI>
	 *       <LI>Last update date/time: Date/time on system</LI>
	 *       <LI>Last update process name: ID0340</LI>
	 *   </UL>
	 * Output the log when error occurs during Update process.
	 * 
	 * <BR>
	 * @param  rdt  Received buffer
	 * @param  sdt  Buffer from which sends data
	 * @throws Exception
	 */
	public void processReceivedId(byte[] rdt, byte[] sdt) throws Exception
	{
		RFTId0340 rftId0340 = null;
		RFTId5340 rftId5340 = null;

		try
		{
			//#CM720819
			// Generate the instance for analyzing the Received electronic statement.
			rftId0340 = (RFTId0340) PackageManager.getObject("RFTId0340");
			rftId0340.setReceiveMessage(rdt);

			//#CM720820
			// Generate an instance for generating a sending electronic statement
			rftId5340 = (RFTId5340) PackageManager.getObject("RFTId5340");
		}
		catch (IllegalAccessException e)
		{
        	//#CM720821
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RFTId*340", e.getMessage());
			throw e;
		}

		//#CM720822
		// Obtain the RFT machine from the Received electronic statement.
		String rftNo = rftId0340.getRftNo();

		//#CM720823
		// Obtain the Worker code from the Received electronic statement.
		String workerCode = rftId0340.getWorkerCode();

		//#CM720824
		// Obtain the Consignor code from the Received electronic statement.
		String consignorCode = rftId0340.getConsignorCode();

		//#CM720825
		// Obtain the Planned Picking Date from the received electronic statement.
		String pickingPlanDate = rftId0340.getPlanDate();

		//#CM720826
		// Obtain the Input area No. from the received electronic statement.
		String inputAreaNo = rftId0340.getAreaNo();
		
		//#CM720827
		// Obtain the Input Zone No. from the received electronic statement.
		String inputZoneNo = rftId0340.getZoneNo();
		
		//#CM720828
		// Obtain the Approach direction from the received electronic statement.
		String approachDirection = rftId0340.getApproachDirection();

		//#CM720829
		// Obtain the Case/Piece division (Work Type) from the received electronic statement.
		String workForm = rftId0340.getWorkForm();
		
		//#CM720830
		// Obtain the Selected Case/Piece division from the received electronic statement.
		String selectCasePiece = rftId0340.getWorkForm();
		
		//#CM720831
		// Obtain the Super Location No. from the received electronic statement.
		String baseLocation = rftId0340.getLocationNo();
		
		//#CM720832
		// Obtain the Super JAN Code from the received electronic statement.
		String baseJanCode = rftId0340.getBaseJANCode();
		
		//#CM720833
		// Obtain the Super Case/Piece division from the received electronic statement.
		String baseCasePieceFlag = rftId0340.getBaseCasePieceFlag();
		
		//#CM720834
		// Obtain the Super Expiry Date from the received electronic statement.
		String baseUseByDate = rftId0340.getBaseUseByDate();
		
		//#CM720835
		// Obtain the Super Bundle ITF from the received electronic statement.
		String baseBundleITF = rftId0340.getBaseBundleITF();
		
		//#CM720836
		// Obtain the Super Case ITF from the received electronic statement.
		String baseCaseITF = rftId0340.getBaseCaseITF();
		
		//#CM720837
		// Obtain the Super Aggregation Work No. from the received electronic statement.
		String baseTotalWorkNo = rftId0340.getBaseTotalWorkNo();
		
		//#CM720838
		// Variable for maintaining the JAN code.
		String JANCode ="";

		//#CM720839
		// Variable for maintaining the Consignor Name.
		String consignorName = "";

		//#CM720840
		// Variable for maintaining the Picking No.
		String pickingNo = "";

		//#CM720841
		// Variable for maintaining the Area No.
		String areaNo = "";

		//#CM720842
		// Variable for maintaining the Zone No.
		String zoneNo = "";

		//#CM720843
		// Variable for maintaining the Location
		String location = "";

		//#CM720844
		// Variable for maintaining the Aggregation Work No.
		String collectJobNo = "";

		//#CM720845
		// Variable for maintaining the Bundle ITF
		String bundleITF = "";

		//#CM720846
		// Variable for maintaining the Case ITF.
		String ITF = "";

		//#CM720847
		// Variable for maintaining the item name.
		String itemName = "";

		//#CM720848
		// Variable for maintaining the Packed qty per bundle.
		String bundleEnteringQty = "";

		//#CM720849
		// Variable for maintaining the Packed qty per case variable
		String enteringQty = "";

		//#CM720850
		// Variable for maintaining the Expiry Date
		String useByDate = "";

		//#CM720851
		// Variable for maintaining the Instructed picking qty
		int pickingInstructionQty = 0;

		//#CM720852
		// Variable for maintaining the Total Picking Count
		int totalPickingQty = 0;

		//#CM720853
		// Variable for maintaining the Count of remaining picking
		int remainingPickingQty = 0;

		//#CM720854
		// Variable for maintaining the Response flag
		String ansCode = "";
		
		//#CM720855
		// Variable for maintaining the detail of the error.
		String errorDetail = RFTId5340.ErrorDetails.NORMAL;

		try
		{
			if(DisplayText.isPatternMatching(areaNo))
			{
				throw new NotFoundException(RFTId5340.ANS_CODE_NULL);
			}
			if(DisplayText.isPatternMatching(zoneNo))
			{
				throw new NotFoundException(RFTId5340.ANS_CODE_NULL);
			}

			//#CM720856
			// Generate BaseOperate instance
			BaseOperate baseOperate = (BaseOperate) PackageManager.getObject("BaseOperate");
			baseOperate.setConnection(wConn);

			//#CM720857
			//-----------------
			//#CM720858
			// Check Daily Maintenance Processing
			//#CM720859
			//-----------------
			if (baseOperate.isLoadingDailyUpdate())
			{
				//#CM720860
				// Return the Status flag as  "7: Daily Update Processing" 
				ansCode = RFTId5340.ANS_CODE_DAILY_UPDATING;
			}
			else
			{
				//#CM720861
				//-----------------
				//#CM720862
				// Check for presence of Result by Worker.
				//#CM720863
				//-----------------
				WorkerResult[] workerResult =
					baseOperate.getWorkerResult(
						WorkingInformation.JOB_TYPE_RETRIEVAL,
						workerCode,
						rftNo);
				if (workerResult.length == 0)
				{
					//#CM720864
					// Create New Worker Result
					baseOperate.createWorkerResult(
						WorkingInformation.JOB_TYPE_RETRIEVAL,
						workerCode,
						rftNo);
					//#CM720865
					// commit
					wConn.commit();
				}

				//#CM720866
				// Generate the RetrievalItemOperate instance.
				RetrievalItemOperate retrievalItemOperate =
				    (RetrievalItemOperate) PackageManager.getObject("RetrievalItemOperate");
				retrievalItemOperate.setConnection(wConn);

				//#CM720867
				// Obtain the Item Picking Plan Info from RetrievalItemOperate instance.
				WorkingInformation retrievalItemWorkData = retrievalItemOperate.startRetrievalItem(
						consignorCode,
						pickingPlanDate,
						inputAreaNo,
						inputZoneNo,
						approachDirection,
						workForm,
						rftNo,
						workerCode,
						baseLocation,
						baseJanCode,
						baseCasePieceFlag,
						baseUseByDate,
						baseBundleITF,
						baseCaseITF,
						baseTotalWorkNo);
				//#CM720868
				// Reserve the data to set for the response electronic statement.
				//#CM720869
				// Set the Consignor Name.
				consignorName = retrievalItemWorkData.getConsignorName();
				//#CM720870
				// Set the Picking No.
				pickingNo = retrievalItemWorkData.getJobNo();
				//#CM720871
				// Set the Area No.
				areaNo = retrievalItemWorkData.getAreaNo();
				//#CM720872
				// Set the Zone No.
				zoneNo = retrievalItemWorkData.getZoneNo();
				//#CM720873
				// Set the Location.
				location = retrievalItemWorkData.getLocationNo();
				//#CM720874
				// Set the Item identification code (Aggregation Work No.).
				collectJobNo = retrievalItemWorkData.getCollectJobNo();
				//#CM720875
				// Set the Bundle ITF.
				bundleITF = retrievalItemWorkData.getBundleItf();
				//#CM720876
				// Set the Case ITF.
				ITF = retrievalItemWorkData.getItf();
				//#CM720877
				// Set the JAN.
				JANCode = retrievalItemWorkData.getItemCode();
				//#CM720878
				// Set the item name.
				itemName = retrievalItemWorkData.getItemName1();
				//#CM720879
				// Set the Packed qty per bundle.
				bundleEnteringQty =	Integer.toString(retrievalItemWorkData.getBundleEnteringQty());
				//#CM720880
				// Set the Packed qty per case.
				enteringQty = Integer.toString(retrievalItemWorkData.getEnteringQty());
				//#CM720881
				// Set the expiry date.
				useByDate = retrievalItemWorkData.getUseByDate();
				//#CM720882
				// Set the Instructed picking qty.
				pickingInstructionQty = retrievalItemWorkData.getPlanEnableQty();

				//#CM720883
				// Obtain the count of total Storage Work plan data from RetrievalItemOperate instance.
				totalPickingQty = retrievalItemOperate.countRetrievalItemDataOfAll(
								consignorCode,
								pickingPlanDate,
								inputAreaNo,
								inputZoneNo,
								workForm);
				//#CM720884
				// Obtain the count of remaining storage works from RetrievalItemOperate instance.
				remainingPickingQty = retrievalItemOperate.countRetrievalItemDataOfWorkable(
								consignorCode,
								pickingPlanDate,
								inputAreaNo,
								inputZoneNo,
								workForm,
								rftNo,
								workerCode);
				//#CM720885
				// Set the Case/Piece division (Work Type).
				workForm = retrievalItemWorkData.getWorkFormFlag();

				//#CM720886
				// Commit the DB.
				wConn.commit();		
				ansCode = RFTId5340.ANS_CODE_NORMAL;
				
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
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = e.getMessage();
			if (ansCode.equals(RFTId5340.ANS_CODE_ERROR))
			{
				errorDetail = RFTId5340.ErrorDetails.NULL;
			}
			else if (! RFTId5340.checkAnsCode(ansCode))
			{
				//#CM720887
				// 6026015=Error occurred during ID process. {0}
				RftLogMessage.printStackTrace(6026015, LogMessage.F_ERROR, CLASS_NAME, e);
				ansCode = RFTId5340.ANS_CODE_ERROR;
				errorDetail = RFTId5340.ErrorDetails.NULL;
			}
		}
		//#CM720888
		// If failed to cancel the data lock within the specified time:
		catch (LockTimeOutException e)
		{
			//#CM720889
			// SELECT FOR UPDATE Time-out
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5340.ANS_CODE_MAINTENANCE;
		}
		//#CM720890
		// In the event of overflow in the process of aggregation:
		catch (OverflowException e)
		{
			String errData = "[ConsignorCode:" + consignorCode +
							" PlanDate:" + pickingPlanDate +
							" WorkForm:" + workForm +
							" RftNo:" + rftNo +
							" WorkerCode:" + workerCode +"]";
			//#CM720891
			// 6026027=Length overflow. Process was aborted. {0}
			RftLogMessage.print(6026027, LogMessage.F_ERROR, CLASS_NAME, errData);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5340.ANS_CODE_ERROR;
			errorDetail = RFTId5340.ErrorDetails.COLLECTION_OVERFLOW;
			
		}
		catch (ReadWriteException e)
		{
			//#CM720892
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
			ansCode = RFTId5340.ANS_CODE_ERROR;
			errorDetail = RFTId5340.ErrorDetails.DB_ACCESS_ERROR;
		}
        catch (IllegalAccessException e)
        {
        	//#CM720893
        	// 6006003=Failed to generate the instance. Class name={0} {1}
			RftLogMessage.print(6006003, LogMessage.F_ERROR, CLASS_NAME, "RetrievalItemOperate", e.getMessage());
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5340.ANS_CODE_ERROR;
			errorDetail = RFTId5340.ErrorDetails.INSTACIATE_ERROR;
        }
        catch (DataExistsException e)
        {
			//#CM720894
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
			ansCode = RFTId5340.ANS_CODE_ERROR;
			errorDetail = RFTId5340.ErrorDetails.DB_UNIQUE_KEY_ERROR;
        }
        catch (SQLException e)
        {
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
			ansCode = RFTId5340.ANS_CODE_ERROR;
			errorDetail = RFTId5340.ErrorDetails.DB_ACCESS_ERROR;
        }
        catch (InvalidDefineException e)
        {
			//#CM720895
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
			ansCode = RFTId5340.ANS_CODE_ERROR;
			errorDetail = RFTId5340.ErrorDetails.PARAMETER_ERROR;
        }
		catch (NoPrimaryException e)
		{
			//#CM720896
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
			ansCode = RFTId5340.ANS_CODE_ERROR;
			errorDetail = RFTId5340.ErrorDetails.DB_UNIQUE_KEY_ERROR;
		}
		//#CM720897
		// If any other error occurs:
		catch (Exception e)
		{
			//#CM720898
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
			ansCode = RFTId5340.ANS_CODE_ERROR;
			errorDetail = RFTId5340.ErrorDetails.INTERNAL_ERROR;
		}


		//#CM720899
		// Generate Response Electronic Statement.
		//#CM720900
		// STX
		rftId5340.setSTX();

		//#CM720901
		// SEQ
		rftId5340.setSEQ(0);

		//#CM720902
		// ID
		rftId5340.setID(RFTId5340.ID);

		//#CM720903
		// Period for sending by RFT
		rftId5340.setRftSendDate(rftId0340.getRftSendDate());

		//#CM720904
		// Period for sending by SERVER
		rftId5340.setServSendDate();

		//#CM720905
		// RFT Machine
		rftId5340.setRftNo(rftId0340.getRftNo());

		//#CM720906
		// Personnel Code
		rftId5340.setWorkerCode(rftId0340.getWorkerCode());

		//#CM720907
		// Consignor Code
		rftId5340.setConsignorCode(consignorCode);

		//#CM720908
		// Planned Picking Date
		rftId5340.setPickingPlanDate(pickingPlanDate);
		
		//#CM720909
		// Input area No.
		rftId5340.setInputAreaNo(inputAreaNo);
		
		//#CM720910
		// Input Zone No.
		rftId5340.setInputZoneNo(inputZoneNo);
		
		//#CM720911
		// Approach direction
		rftId5340.setApproachDirection(approachDirection);
		
		//#CM720912
		// Selected Case/Piece division
		rftId5340.setSelectCasePiece(selectCasePiece);

		//#CM720913
		// JAN Code
		rftId5340.setJANCode(JANCode);

		//#CM720914
		// Case/Piece division (Work Type) 
		rftId5340.setCasePieceFlag(workForm);

		//#CM720915
		// Set the Consignor Name.
		rftId5340.setConsignorName(consignorName);

		//#CM720916
		// Set the Picking No.
		rftId5340.setPickingNo(pickingNo);

		//#CM720917
		// Set the Area No.
		rftId5340.setAreaNo(areaNo);

		//#CM720918
		// Set the Zone No.
		rftId5340.setZoneNo(zoneNo);

		//#CM720919
		// Set the Location.
		rftId5340.setLocation(location);

		//#CM720920
		// Set the Processing Replenish flag.
		rftId5340.setReplenishingFlag("0");

		//#CM720921
		// Set the Aggregation Work No.
		rftId5340.setCollectJobNo(collectJobNo);

		//#CM720922
		// Set the Bundle ITF.
		rftId5340.setBundleITF(bundleITF);

		//#CM720923
		// Set the Case ITF.
		rftId5340.setITF(ITF);

		//#CM720924
		// Set the item name.
		rftId5340.setItemName(itemName);

		//#CM720925
		// Set the Packed qty per bundle.
		rftId5340.setBundleEnteringQty(bundleEnteringQty);

		//#CM720926
		// Set the Packed qty per case.
		rftId5340.setEnteringQty(enteringQty);

		//#CM720927
		//Set the expiry date.
		rftId5340.setUseByDate(useByDate);

		//#CM720928
		// Set the Instructed picking qty.
		rftId5340.setPickingInstructionQty(pickingInstructionQty);

		//#CM720929
		// Set the Total Picking Count.
		rftId5340.setTotalPickingQty(totalPickingQty);

		//#CM720930
		// Set the Count of remaining picking.
		rftId5340.setRemainingPickingQty(remainingPickingQty);

		//#CM720931
		// Response flag
		rftId5340.setAnsCode(ansCode);
		
		//#CM720932
		// Details of error
		rftId5340.setErrDetails(errorDetail);

		//#CM720933
		// ETX
		rftId5340.setETX();

		//#CM720934
		// Obtain the response electronic statement.
		rftId5340.getSendMessage(sdt);
		
		//#CM720935
		// For data with Response flag "0: Normal", store the response electronic statement to the file.
		try
		{
			if (ansCode.equals(RFTId5340.ANS_CODE_NORMAL))
			{
				rftId5340.saveResponseId(rftNo, PROCESS_NAME, wConn);
				wConn.commit();
			}
		}
		catch (Exception e)
		{
			RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, e);
			try
			{
				wConn.rollback();
			}
			catch (SQLException sqlex)
			{
				RftLogMessage.printStackTrace(6006002, LogMessage.F_ERROR, CLASS_NAME, sqlex);
			}
		}

	}

	//#CM720936
	// Package methods -----------------------------------------------

	//#CM720937
	// Protected methods ---------------------------------------------

	//#CM720938
	// Private methods -----------------------------------------------

}
//#CM720939
//end of class
