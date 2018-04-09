// $Id: Id24Process.java,v 1.2 2006/10/26 03:08:17 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32700
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id24;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.report.WorkMaintenanceWriter;
//#CM32701
/**
 * This class processes the response to CarryDataCancel. Based on the result of CANCEL, it updates the target CarryInformation.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/01/09</TD><TD>MIYASHITA</TD><TD>Response is completed successfully.</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:08:17 $
 * @author  $Author: suresh $
 */
public class Id24Process extends IdProcess
{
	//#CM32702
	// Class fields --------------------------------------------------
	//#CM32703
	/**
	 * Preserves target AGCNO.
	 */
	private int  wAgcNumber;

	//#CM32704
	// Class variables -----------------------------------------------
	//#CM32705
	/**
	 * class name
	 */
	private static final String CLASS_NAME = "Id24Process";

	//#CM32706
	// Class method --------------------------------------------------
	//#CM32707
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:08:17 $");
	}

	//#CM32708
	// Constructors --------------------------------------------------
	//#CM32709
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id24Process()
	{
		super();
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM32710
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id24Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM32711
	// Public methods ------------------------------------------------
	
	//#CM32712
	// Package methods -----------------------------------------------

	//#CM32713
	// Protected methods ---------------------------------------------
	//#CM32714
	/**
	 * Processing the reply to Carry Data Cancel.
	 * Search <code>CarryInformation</code> based on the MC Key of the received communication message,
	 * then according to the classification of reply to CarryDataCancel and the cancel request classsification
	 * of <code>CarryInformation</code>, process the cancelation.
	 * 1. In case the classification of reply is normal, or if there was no corrensponding carry data,
	 *    It branches the process according to the classification of cancel request for CarryInformation.
	 *    - Data Cancel
	 *      Reset the status of Carry Data to 'unprocessed'.
	 *    - Allocation release
	 *      Process the deletion of CarryData.
	 *    - Deletion of track data
	 *      Delete the Carry Data and the stock data.
	 * 2. Not able to Cancel due to the classification 'Carry started'.
	 *    Output the caution message, nothing else will be done.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		Object[] tObj = new Object[1] ;
		CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
		CarryInformationSearchKey cskey = new CarryInformationSearchKey() ;
		CarryInformationAlterKey cakey = new CarryInformationAlterKey();
		
		CarryCompleteOperator carryCompOpe = new CarryCompleteOperator();
		carryCompOpe.setClassName(CLASS_NAME);

		//#CM32715
		// Decomposition of the received text.
		As21Id24 id24dt = new As21Id24(rdt) ;

		//#CM32716
		// If normal completion or target carry data does not exist, call delete process
		if (As21Id24.RESULT_COMP_NORMAL.equals(id24dt.getCancellationResults())
		 || As21Id24.RESULT_NOT_DATA.equals(id24dt.getCancellationResults()))
		{
			tObj[0] = id24dt.getMcKey() ;
			if (As21Id24.RESULT_COMP_NORMAL.equals(id24dt.getCancellationResults()))
			{
				//#CM32717
				// 6022015=Result of received text cancel request -> Normal end. mckey={0}
				RmiMsgLogClient.write(6022015, LogMessage.F_NOTICE, "Id24Process", tObj);
			}
			else
			{
				//#CM32718
				// 6022017=Result of received text cancel request -> There is no data. mckey={0}
				RmiMsgLogClient.write(6022017, LogMessage.F_NOTICE, "Id24Process", tObj);
			}

			//#CM32719
			// By setting MC key as a search condition, searches the CarryInformation.
			cskey.setCarryKey(id24dt.getMcKey());
			//#CM32720
			// Obtains corresponding CarryInfo.
			Entity earr[] = cih.find(cskey);
			//#CM32721
			// There is no corresponding data.
			if (earr.length == 0)
			{
				tObj = new Object[1];
				tObj[0] = id24dt.getMcKey();
				//#CM32722
				// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
				RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, "Id24Process", tObj);
				//#CM32723
				// If there is no corresponding data, no handling of exception is done but terminates this ID processing jsut as it is. 
				return ;
			}
			
			if (earr[0] instanceof CarryInformation)
			{
				//#CM32724
				// Stores the carry information.
				CarryInformation ci = (CarryInformation)earr[0];
				
				//#CM32725
				// Print maintenance list
				WorkMaintenanceWriter writer = new WorkMaintenanceWriter(wConn);
				writer.setCarrykey(ci.getCarryKey());
				writer.setCmdStatus(ci.getCmdStatus());
				writer.setWorkType(ci.getWorkType());
				//#CM32726
				// cancel request classsification
				switch (ci.getCancelRequest())
				{
					//#CM32727
					// Cancel instructions
					case CarryInformation.CANCELREQUEST_CANCEL:
						writer.setJob("LBL-W0604");
						break;
						
					//#CM32728
					// Cancel allocation
					case CarryInformation.CANCELREQUEST_RELEASE:
						writer.setJob("LBL-W0605");
						break;
						
					//#CM32729
					// Delete track data
					case CarryInformation.CANCELREQUEST_DROP:
						writer.setJob("LBL-W0538");
						break;
				}
				writer.startPrint();

				//#CM32730
				// Checks the carry classification.
				switch (ci.getCarryKind())
				{
					//#CM32731
					// Storage, direct travel
					case CarryInformation.CARRYKIND_STORAGE:
					case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
						//#CM32732
						// Determines the process according to the classification of cancel request.
						if (ci.getCancelRequest() == CarryInformation.CANCELREQUEST_CANCEL)
						{
							//#CM32733
							// Canceling the storage data
							carryCompOpe.cancelStorage(wConn, ci);
							//#CM32734
							// Switching the cancel request flag to 'not requested'.
							cakey.updateCancelRequest(CarryInformation.CANCELREQUEST_UNDEMAND);
							cakey.updateCancelRequestDate(ci.getCancelRequestDate());
							cakey.setCarryKey(ci.getCarryKey());
							cih.modify(cakey);
						}
						else
						{
							//#CM32735
							// Deletes
							switch(ci.getCmdStatus())
							{
								//#CM32736
								// Allocated, start, wait for reply
								case CarryInformation.CMDSTATUS_ALLOCATION:
								case CarryInformation.CMDSTATUS_START:
								case CarryInformation.CMDSTATUS_WAIT_RESPONSE:
									carryCompOpe.drop(wConn, ci, false);
									break;
									
								//#CM32737
								// With any other cases, all carry works should be deleted. (with data)
								default:
									carryCompOpe.drop(wConn, ci, true);
									break;
							}
						}
						break;

					//#CM32738
					// Retrieval, location to location move
					case CarryInformation.CARRYKIND_RETRIEVAL:
					case CarryInformation.CARRYKIND_RACK_TO_RACK:
						//#CM32739
						// Determines the process according to the classification of cancel request.
						if (ci.getCancelRequest() == CarryInformation.CANCELREQUEST_CANCEL)
						{
							//#CM32740
							// Canceling the retrieval data
							carryCompOpe.cancelRetrieval(wConn, ci);
							//#CM32741
							// Switching the cancel request flag to 'not requested'.
							cakey.updateCancelRequest(CarryInformation.CANCELREQUEST_UNDEMAND);
							cakey.updateCancelRequestDate(ci.getCancelRequestDate());
							cakey.setCarryKey(ci.getCarryKey());
							cih.modify(cakey);							
						}
						else if (ci.getCancelRequest() == CarryInformation.CANCELREQUEST_RELEASE)
						{
							//#CM32742
							// Release of allocation
							switch (ci.getCmdStatus())
							{
								//#CM32743
								// Allocated, start, wait for reply, instruction provided
								case CarryInformation.CMDSTATUS_ALLOCATION:
								case CarryInformation.CMDSTATUS_START:
								case CarryInformation.CMDSTATUS_WAIT_RESPONSE:
								case CarryInformation.CMDSTATUS_INSTRUCTION:
									carryCompOpe.dropRetrieval(wConn, ci);
									break;
									
								default:
									tObj[0] = new Integer(ci.getCmdStatus()) ;
									//#CM32744
									// 6026068=Invalid transfer status. Unable to clear the allocation. Transfer status={0}
									RmiMsgLogClient.write(6026068, LogMessage.F_WARN, "Id24Process", tObj);
									break;
							}
						}
						else
						{
							//#CM32745
							// Delete
							carryCompOpe.drop(wConn, ci, true);
						}
						break;

					//#CM32746
					// Invalid carry classification
					default:
						tObj = new Object[3];
						tObj[0] = "CarryInformation";
						tObj[1] = "CarryKind" ;
						tObj[2] = Integer.toString(ci.getCarryKind());
						//#CM32747
						// 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}
						RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "Id24Process", tObj);
						throw (new InvalidStatusException("6024018" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
				}
			}
			else
			{
				tObj[0] = "CarryInformation" ;
				//#CM32748
				// 6006008=Object other than {0} was returned.
				RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, "Id24Process", tObj);
				throw (new InvalidProtocolException("6006008" + wDelim + tObj[0])) ;
			}
		}
		//#CM32749
		// Not able to Cancel as the Carry of corresponding data already started.
		else if (As21Id24.RESULT_NOT_CANCEL.equals(id24dt.getCancellationResults()))
		{
			tObj[0] = id24dt.getMcKey();
			//#CM32750
			// 6022016=Result of received text cancel request -> Unable to cancel. Data has been transferred. mckey={0}
			RmiMsgLogClient.write(6022016, LogMessage.F_NOTICE, "Id24Process", tObj);
		}
		//#CM32751
		// Reuslt of Cancel unknown.
		else
		{
			tObj[0] = new String(id24dt.getCancellationResults());
			//#CM32752
			// 6022018=Result of received text cancel request is invalid. Cancel result={0}
			RmiMsgLogClient.write(6022018, LogMessage.F_NOTICE, "Id24Process", tObj);
		}
	}

	//#CM32753
	// Private methods -----------------------------------------------

}
//#CM32754
//end of class

