// $Id: Id31Process.java,v 1.2 2006/10/26 03:04:52 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32997
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id31;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;

//#CM32998
/**
 * This class processes the response to instruction of alternative location. It processes based on the classificiaotn of reply.
 * When normally accepted, it regards that instruction of destination change has been accepted
 * normally and therefore there will be no process.
 * If any other reports accepted, it alters the status of CarryInformation and invalidates the Carry Data.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:04:52 $
 * @author  $Author: suresh $
 */
public class Id31Process extends IdProcess
{
	//#CM32999
	// Class fields --------------------------------------------------

	//#CM33000
	// Class variables -----------------------------------------------
	//#CM33001
	/**
	 * Preserves the target AGCNO.
	 */
	 private int  wAgcNumber;

	//#CM33002
	// Class method --------------------------------------------------
	//#CM33003
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:04:52 $") ;
	}

	//#CM33004
	// Constructors --------------------------------------------------
	//#CM33005
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id31Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33006
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id31Process(int num)
	{
		super() ;
		wAgcNumber = num ;
	}

	//#CM33007
	// Public methods ------------------------------------------------

	//#CM33008
	// Package methods -----------------------------------------------

	//#CM33009
	// Protected methods ---------------------------------------------
	//#CM33010
	/**
	 * Procesing the reply to the instruction for alternative location.
	 * Based on the MC Key in communication message received, it searches <code>CarryInformation</code>.
	 * If the reply was an error, alter the carry status to 'error'.
	 * owever the call source needs to commit or rollback the transaction, as they are not done here.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		As21Id31 id31dt = new As21Id31(rdt) ;
		Object[] tObj = null;

		//#CM33011
		// Retrieves the reply classification.
		String response = id31dt.getResponseClassification() ;

		CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
		CarryInformationSearchKey cskey = new CarryInformationSearchKey() ;

		//#CM33012
		// normaly received
		if(As21Id31.CLASS_NORMAL_RECEIVE.equals(response))
		{
			String mckey = id31dt.getMcKey() ;
			cskey.setCarryKey(mckey) ;
			CarryInformation[] earr = (CarryInformation[]) cih.find(cskey) ;
			
			//#CM33013
			// There is no corresponding data.
			if (earr.length == 0)
			{
				tObj = new Object[1] ;
				tObj[0] = mckey;
				//#CM33014
				// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
				RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, "Id31Process", tObj);
			}
		}
		else
		{
			//#CM33015
			// Sets MC key as a search condition.
			String mckey = id31dt.getMcKey() ;
			cskey.setCarryKey(mckey) ;
			
			//#CM33016
			// Retrieves corresponding CarryInfo.
			CarryInformation[] earr = (CarryInformation[]) cih.find(cskey) ;
			
			//#CM33017
			// There is no corresponding data.
			if (earr.length == 0)
			{
				tObj = new Object[1] ;
				tObj[0] = mckey;
				//#CM33018
				// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
				RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, "Id31Process", tObj);
				return ;
			}

			if (earr[0] instanceof CarryInformation)
			{
				//#CM33019
				// Alters the carry status to error.
				CarryInformation ci = (CarryInformation)earr[0] ;
				CarryInformationAlterKey cakey = new CarryInformationAlterKey();
				cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
				cakey.setCarryKey(ci.getCarryKey());
				cih.modify(cakey);
				
				//#CM33020
				// 6024014=Alternate location command was not accepted. Response category={0} mckey={1}
				tObj = new Object[2] ;
				tObj[0] = new String(response) ;
				tObj[1] = mckey ;
				RmiMsgLogClient.write(6024014, LogMessage.F_ERROR, "Id31Process", tObj);
			}
			else
			{
				tObj[0] = "CarryInformation" ;
				//#CM33021
				// 6006008=Object other than {0} was returned.
				RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, "Id31Process", tObj);
				throw (new InvalidProtocolException("6006008" + wDelim + tObj[0])) ;
			}
		}
	}
	
	//#CM33022
	// Private methods -----------------------------------------------

}
//#CM33023
//end of class

