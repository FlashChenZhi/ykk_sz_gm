// $Id: Id25Process.java,v 1.2 2006/10/26 03:07:19 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32755
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id25;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;

//#CM32756
/**
 * This class processes the response to Carry Instruction. According to the classification of response,
 * it updates the corresponding CarryInformation.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:07:19 $
 * @author  $Author: suresh $
 */
public class Id25Process extends IdProcess
{
	//#CM32757
	// Class fields --------------------------------------------------
	//#CM32758
	// classificaiton of response (normal)
	private static final int RESPONSE_NORMAL = 0 ;
	//#CM32759
	// classificaiton of response (load presence error)
	private static final int LOAD_ERR        = 1 ;
	//#CM32760
	// classificaiton of response (multiple set)
	private static final int MULTI_SETTING   = 3 ;
	//#CM32761
	// classificaiton of response (breakdown)
	private static final int FAILURE         = 4 ;
	//#CM32762
	// classificaiton of response (equipment off-line)
	private static final int EQUIP_OFFLINE   = 5 ;
	//#CM32763
	// classificaiton of response(OFFLINE)
	private static final int OFFLINE         = 6 ;
	//#CM32764
	// classificaiton of response (condition error)
	private static final int CONDITION_ERROR = 7 ;
	//#CM32765
	// classificaiton of response(BUFFER FULL)
	private static final int BUFFER_BULL     = 11 ;
	//#CM32766
	// classificaiton of response(DATA ERROR)
	private static final int DATA_ERROR      = 99 ;

	//#CM32767
	// Class variables -----------------------------------------------
	//#CM32768
	/**
	 * Preseves the target AGCNO.
	 */
	 private int  wAgcNumber;

	//#CM32769
	/**
	 * class name
	 */
	private static final String CLASS_NAME = "Id25Process";

	//#CM32770
	// Class method --------------------------------------------------
	//#CM32771
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:07:19 $") ;
	}

	//#CM32772
	/** 
	 * Performs the normal process of Reply to Carry Instruction. It clears the stock update data and the arrival data of sdnding station. 
	 * Besides receiving the Carry Instructions, this method will be used as a measure when the texts was skipped 
	 * and receiving the storage completion report text while target CarryInformation is in 'watit for response',  
	 * @param  conn : Connection
	 * @param  ci   : CarryInformation to update
	 * @throws InvalidDefineException :Notifies when definition of the class was incorrect.
	 * @throws ReadWriteException  :Notifies if error occured when accessing database.
     */
	public static void updateNormalResponce(Connection conn, CarryInformation ci) throws InvalidDefineException, ReadWriteException
	{
		try
		{
			StorageCarryCompleteOperator carryCompOpe = new StorageCarryCompleteOperator();
			StationOperator sOpe = new StationOperator(conn, ci.getSourceStationNumber());
			carryCompOpe.setClassName(CLASS_NAME);
			
			//#CM32773
			// Updates the stocks.
			carryCompOpe.updateStock(conn, ci) ;
			
			//#CM32774
			// Alters the carry status to 'instructed'
			CarryInformationAlterKey cakey = new CarryInformationAlterKey();
			CarryInformationHandler carryInfoHandle = new CarryInformationHandler(conn);
			cakey.setCarryKey(ci.getCarryKey());
			cakey.updateCmdStatus(CarryInformation.CMDSTATUS_INSTRUCTION);
			carryInfoHandle.modify(cakey);

			//#CM32775
			// If the sending station operates the arrival reports, it clears the arrival information.
			if (sOpe.getStation().isArrivalCheck())
			{
				sOpe.dropArrival();
			}
		}
		catch (NotFoundException ne)
		{
			throw new InvalidDefineException(ne.getMessage()) ;
		}
		catch (InvalidStatusException ie)
		{
			throw new InvalidDefineException(ie.getMessage()) ;
		}
		catch (ScheduleException ie)
		{
			throw new InvalidDefineException(ie.getMessage()) ;
		}
	}

	//#CM32776
	// Constructors --------------------------------------------------
	//#CM32777
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id25Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM32778
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id25Process(int num)
	{
		super() ;
		wAgcNumber = num ;
	}

	//#CM32779
	// Public methods ------------------------------------------------

	//#CM32780
	// Package methods -----------------------------------------------

	//#CM32781
	// Protected methods ---------------------------------------------
	//#CM32782
	/**
	 * Processes the response to Carry Instruction.
	 * Search <code>CarryInformation</code> based on the MC Key of the received communication message,
	 * then according to the classification of reply to the CarryInstruction, alter the carry status.
	 * However, the commitment and rollback of transaction must be done by call resource
	 * as they are not deone.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		As21Id25 id25dt = new As21Id25(rdt) ;
		CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
		CarryInformationSearchKey cskey = new CarryInformationSearchKey() ;
		CarryInformationAlterKey cakey = new CarryInformationAlterKey();

		Object[] tObj = null;

		//#CM32783
		// Sets the MC key as a search condition.
		cskey.setCarryKey(id25dt.getMcKey()) ;
		//#CM32784
		//Obtains corresponding CarryInfo.
		CarryInformation[] carryInfo =(CarryInformation[]) cih.find(cskey) ;

		//#CM32785
		// There is no corresponding data.
		if (carryInfo.length == 0)
		{
			tObj = new Object[1] ;
			tObj[0] = id25dt.getMcKey();
			//#CM32786
			// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
			RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, "Id25Process", tObj);
			return ;
		}		
		
		if (carryInfo[0] instanceof CarryInformation)
		{
			CarryInformation ci = (CarryInformation)carryInfo[0];
			StationOperator sOpe = new StationOperator(wConn, ci.getSourceStationNumber());			
			//#CM32787
			// Checks the classification of response.
			switch (id25dt.getResponseClassification())
			{
				//#CM32788
				// Normal response
				case RESPONSE_NORMAL:
					if (ci.getCmdStatus() == CarryInformation.CMDSTATUS_WAIT_RESPONSE)
					{
						//#CM32789
						// Calls the process of carry data update at the normal response.
						updateNormalResponce(wConn, ci);
					}
					else
					{
						tObj = new Object[1] ;
						tObj[0] = id25dt.getMcKey();
						//#CM32790
						// 6024004=Unexpected reply to carry instruction. No action for the received text. mckey={0}
						RmiMsgLogClient.write(6024004, LogMessage.F_WARN, "Id25Process", tObj);
					}
					break;

				//#CM32791
				// Reply classification (load presence error)
				case LOAD_ERR:
					tObj = new Object[1] ;
					tObj[0] = id25dt.getMcKey();
					//#CM32792
					// 6024005=Load presence error was received in response to transfer instruction. mckey={0}
					RmiMsgLogClient.write(6024005, LogMessage.F_WARN, "Id25Process", tObj);
					//#CM32793
					// Update the carry status to 'error'.
					cakey.KeyClear();
					cakey.setCarryKey(ci.getCarryKey());
					cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
					cih.modify(cakey);					
					break;

				//#CM32794
				// Multiple set
				case MULTI_SETTING:
					tObj = new Object[1] ;
					tObj[0] = id25dt.getMcKey();
					//#CM32795
					// 6024006=Multiple allocation was received in response to transfer instruction. mckey={0}
					RmiMsgLogClient.write(6024006, LogMessage.F_WARN, "Id25Process", tObj);
					break;

				//#CM32796
				// Breakdown
				case FAILURE:
					tObj = new Object[1] ;
					tObj[0] = id25dt.getMcKey();
					//#CM32797
					// 6024012=Equipment error was received in response to transfer instruction. Transkey={0}
					RmiMsgLogClient.write(6024012, LogMessage.F_WARN, "Id25Process", tObj);
					
					//#CM32798
					// Reset the carry status to 'start'.
					cakey.KeyClear();
					cakey.setCarryKey(ci.getCarryKey());
					cakey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
					cih.modify(cakey);					
					break;

				//#CM32799
				//  Equipment off-line
				case EQUIP_OFFLINE:
					tObj = new Object[1] ;
					tObj[0] = id25dt.getMcKey();
					//#CM32800
					// 6024007=Equipment offline was received in response to transfer instruction. mckey={0}
					RmiMsgLogClient.write(6024007, LogMessage.F_WARN, "Id25Process", tObj);
					
					//#CM32801
					// Reset the carry status to 'start'.
					cakey.KeyClear();
					cakey.setCarryKey(ci.getCarryKey());
					cakey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
					cih.modify(cakey);						
					break;

				//#CM32802
				// Off-line
				case OFFLINE:
					tObj = new Object[1] ;
					tObj[0] = id25dt.getMcKey();
					//#CM32803
					// 6024008=Offline was received in response to transfer instruction. mckey={0}
					RmiMsgLogClient.write(6024008, LogMessage.F_WARN, "Id25Process", tObj);
					
					//#CM32804
					// Reset the carry status to 'start'.
					cakey.KeyClear();
					cakey.setCarryKey(ci.getCarryKey());
					cakey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
					cih.modify(cakey);
					
					//#CM32805
					// Alter the state of group controller to off-line.
					GroupController gc = new GroupController(wConn, wAgcNumber);
					gc.setStatus(GroupController.STATUS_OFFLINE);					
					break;

				//#CM32806
				// Condition error
				case CONDITION_ERROR:
					tObj = new Object[1] ;
					tObj[0] = id25dt.getMcKey();
					//#CM32807
					// 6024009=Condition error was received in response to transfer instruction. mckey={0}
					RmiMsgLogClient.write(6024009, LogMessage.F_WARN, "Id25Process", tObj);
					
					//#CM32808
					// Reset the carry status to 'start'.
					cakey.KeyClear();
					cakey.setCarryKey(ci.getCarryKey());
					cakey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
					cih.modify(cakey);
					
					//#CM32809
					// Alter the status of sending station to 'suspended'.
					sOpe.alterSuspend(true);
					break;

				//#CM32810
				// BUFFER FULL
				case BUFFER_BULL:
					tObj = new Object[1] ;
					tObj[0] = id25dt.getMcKey();
					//#CM32811
					// 6024010=Buffer full was received in response to transfer instruction. mckey={0}
					RmiMsgLogClient.write(6024010, LogMessage.F_WARN, "Id25Process", tObj);
					
					//#CM32812
					// Reset the carry status to 'start'.
					cakey.KeyClear();
					cakey.setCarryKey(ci.getCarryKey());
					cakey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
					cih.modify(cakey);					
					break;

				//#CM32813
				// DATA ERROR
				case DATA_ERROR:
					tObj = new Object[1] ;
					tObj[0] = id25dt.getMcKey();
					//#CM32814
					// 6024011=Data error was received in response to transfer instruction. mckey={0}
					RmiMsgLogClient.write(6024011, LogMessage.F_WARN, "Id25Process", tObj);
					
					//#CM32815
					// Reset the carry status to 'start'.
					cakey.KeyClear();
					cakey.setCarryKey(ci.getCarryKey());
					cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
					cih.modify(cakey);
					
					//#CM32816
					// Alter the status of sending station to 'suspended'.
					sOpe.alterSuspend(true);
					
					//#CM32817
					// Clear the arrival data if the sending station operates the arrival reports.
					if (sOpe.getStation().isArrivalCheck())
					{
						sOpe.dropArrival();
					}
					break;

				//#CM32818
				// Undefined response classification 
				default:
					tObj = new Object[2] ;
					tObj[0] = "25";
					tObj[1] = Integer.toString(id25dt.getResponseClassification());
					//#CM32819
					// 6026037=Response category for text ID {0} is invalid.  Receive response category={1}
					RmiMsgLogClient.write(6026037, LogMessage.F_WARN, "Id25Process", tObj);
					throw new InvalidDefineException("6026037" + wDelim + tObj[0] + wDelim + tObj[1]);
			}
		}
	}

	//#CM32820
	// Private methods -----------------------------------------------

}
//#CM32821
//end of class

