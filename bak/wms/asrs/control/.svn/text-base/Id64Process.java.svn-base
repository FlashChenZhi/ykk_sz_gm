// $Id: Id64Process.java,v 1.2 2006/10/26 02:22:05 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33465
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id64;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.OperationDisplay;
//#CM33466
/**
 * This class processes the pick up completion report. It updates hte status of corresponding CarryInformation to pick-up completion.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:22:05 $
 * @author  $Author: suresh $
 */
public class Id64Process extends IdProcess
{
	//#CM33467
	// Class fields --------------------------------------------------

	//#CM33468
	// Class variables -----------------------------------------------
	//#CM33469
	/**
	 * Preserves the target AGCno.
	 */
	 private int  wAgcNumber;

	//#CM33470
	// Class method --------------------------------------------------
	//#CM33471
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:22:05 $") ;
	}

	//#CM33472
	// Constructors --------------------------------------------------
	//#CM33473
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id64Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33474
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id64Process(int num)
	{
		super() ;
		wAgcNumber = num ;
	}

	//#CM33475
	// Public methods ------------------------------------------------

	//#CM33476
	// Package methods -----------------------------------------------

	//#CM33477
	// Protected methods ---------------------------------------------
	//#CM33478
	/**
	 * Processes the receiving of pick-up completion report.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt)throws Exception
	{
		CarryInformation ci ;
		As21Id64 id64dt = new As21Id64(rdt) ;
		CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
		String[] ckey     = id64dt.getVmcKey();

		//#CM33479
		// Processing as much data as text received (2 at max. in standard)
		for (int i = 0 ; i < ckey.length ; i++)
		{
			//#CM33480
			// Sets the MC key as a search condition.
			CarryInformationSearchKey cikey = new CarryInformationSearchKey() ;
			cikey.setCarryKey(ckey[i]) ;
			
			//#CM33481
			// Retrieves the corresponding CarryInfo.
			CarryInformation[] earr = (CarryInformation[]) cih.find(cikey) ;
			
			//#CM33482
			// There is no corresponding data.
			if (earr.length == 0)
			{
				//#CM33483
				// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
				Object[] tObj = new Object[1] ;
				tObj[0] = ckey[i];
				RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, "Id64Process", tObj);
				continue ;
			}
			
			if (earr[0] instanceof CarryInformation)
			{
				ci = (CarryInformation)earr[0] ;
				CarryInformationAlterKey cakey = new CarryInformationAlterKey();
				cakey.updateCmdStatus(CarryInformation.CMDSTATUS_PICKUP);
				cakey.setCarryKey(ci.getCarryKey());
				cih.modify(cakey);
			}
			else
			{
				//#CM33484
				// 6006008=Object other than {0} was returned.
				Object[] tObj = new Object[1] ;
				tObj[0] = "CarryInformation" ;
				RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, "Id64Process", tObj);
				throw (new InvalidProtocolException("6006008" + wDelim + tObj[0])) ;
			}
			
			//#CM33485
			// Checks whether/not the on-line indicaiton is operated. Delete if there is the on-line indication data.
			OperationDisplayHandler   ohandl = new OperationDisplayHandler(wConn);
			OperationDisplaySearchKey skey   = new OperationDisplaySearchKey();
			skey.setCarryKey(ci.getCarryKey());
			OperationDisplay[] odisp =  (OperationDisplay[])ohandl.find(skey);
			if (odisp.length > 0)
			{
				ohandl.drop(skey);
			}
		}
	}
	
	//#CM33486
	// Private methods -----------------------------------------------

}//end of class
