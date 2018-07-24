// $Id: Id28Process.java,v 1.2 2006/10/26 03:05:46 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32920
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id28;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;
//#CM32921
/**
 * This class processes the response to the instruction of destination change.
 * It handles the process based on the classsification of the response. 
 * If it is normaly received, it regards that the instruction of destination change has been
 * accepted normally, therefore it will not carry out any process.
 * For any other reports, it should alter tje status of CarryInformation to error and invalidates the Carry data.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:05:46 $
 * @author  $Author: suresh $
 */
public class Id28Process extends IdProcess
{
	//#CM32922
	// Class fields --------------------------------------------------

	//#CM32923
	// Class variables -----------------------------------------------
	//#CM32924
	/**
	 * Preserves the target AGCNO.
	 */
	 private int  wAgcNumber;

	//#CM32925
	// Class method --------------------------------------------------
	//#CM32926
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:05:46 $") ;
	}

	//#CM32927
	// Constructors --------------------------------------------------
	//#CM32928
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id28Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM32929
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id28Process(int num)
	{
		super() ;
		wAgcNumber = num ;
	}

	//#CM32930
	// Public methods ------------------------------------------------

	//#CM32931
	// Package methods -----------------------------------------------

	//#CM32932
	// Protected methods ---------------------------------------------
	//#CM32933
	/**
	 * Processing the response to instrruction of destination change.
	 * Based on MC Key of received communiacation message, search <code>CarryInformation</code>
	 * and alter the carry status 'error' in case the error response was received.
	 * However the call source needs to commit or rollback the transaction, as they are not done here.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		CarryInformation ci ;
		As21Id28 id28dt = new As21Id28(rdt) ;
		CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
		Object[] tObj = null;

		//#CM32934
		// Obtains the response classificaiton.
		String response = id28dt.getResponseClassification() ;

		//#CM32935
		// Error report, alter hte carry status to 'error'.
		if (!As21Id28.CLASS_NORMAL_RECEIVE.equals(response))
		{
			//#CM32936
			// Sets MC key as a search condition.
			String mckey = id28dt.getMcKey() ;
			CarryInformationSearchKey cskey = new CarryInformationSearchKey() ;
			cskey.setCarryKey(mckey) ;
			
			//#CM32937
			// Obtains the corrensponding CarryInfo.
			CarryInformation[] earr = (CarryInformation[]) cih.find(cskey) ;
			
			//#CM32938
			// There is no corresponding data.
			if (earr.length == 0)
			{
				tObj = new Object[1] ;
				tObj[0] = mckey;
				//#CM32939
				// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
				RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, "Id28Process", tObj);
				return ;
			}

			if (earr[0] instanceof CarryInformation)
			{
				//#CM32940
				// In case the 'unacceptable' response to teh destination change instruction was 
				// received, the carry status needs to be updated 'error'.
				ci = (CarryInformation) earr[0] ;

				CarryInformationAlterKey cakey = new CarryInformationAlterKey();
				cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
				cakey.setCarryKey(ci.getCarryKey());
				cih.modify(cakey);
				
				tObj = new Object[2] ;
				tObj[0] = new String(response) ;
				tObj[1] = mckey ;
				//#CM32941
				// 6024013=Carry destination change command was not accepted. Response category={0} mckey={1}
				RmiMsgLogClient.write(6024013, LogMessage.F_WARN, "Id28Process", tObj);
			}
			else
			{
				tObj[0] = "CarryInformation" ;
				//#CM32942
				// 6006008=Object other than {0} was returned.
				RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, "Id28Process", tObj);
				throw (new InvalidProtocolException("6006008" + wDelim + tObj[0])) ;
			}
		}
	}
	
	//#CM32943
	// Private methods -----------------------------------------------

}
//#CM32944
//end of class

