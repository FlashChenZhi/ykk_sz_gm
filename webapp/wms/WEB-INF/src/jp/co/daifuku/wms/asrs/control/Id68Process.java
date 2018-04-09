// $Id: Id68Process.java,v 1.2 2006/10/26 02:21:07 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33503
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.util.Date;

import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id68;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.OperationDisplay;
import jp.co.daifuku.wms.base.entity.Station;

//#CM33504
/**
 * This class processes the on-line indication trigger report. It records the contents received in 
 * OPERATIONDISPLAY, then keeps them subject to indication for on-line operation.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:21:07 $
 * @author  $Author: suresh $
 */
public class Id68Process extends IdProcess
{
	//#CM33505
	// Class variables -----------------------------------------------
	//#CM33506
	/**
	 * AGCNo.
	 */
	private int  wAgcNumber;

	//#CM33507
	// Class method --------------------------------------------------
	//#CM33508
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:21:07 $") ;
	}

	//#CM33509
	// Constructors --------------------------------------------------
	//#CM33510
	/**
     * Default constructor
     * GroupController.DEFAULT_AGC_NUMBER is set to AGCNo
     */
	public Id68Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}
	
	//#CM33511
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id68Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33512
	// Public methods ------------------------------------------------

	//#CM33513
	// Package methods -----------------------------------------------
	
	//#CM33514
	// Protected methods ---------------------------------------------
	//#CM33515
	/**
	 * Processes the communication message of on-line indication trigger report.
	 * Sets the contents received to OPERATIONDISPLAY.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		//#CM33516
		// Generates the instance of As21Id68.
		As21Id68 id68dt = new As21Id68(rdt) ;

		CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
		CarryInformationSearchKey cskey = new CarryInformationSearchKey() ;
		
		//#CM33517
		// Set the MC key as a search condition.
		cskey.setCarryKey(id68dt.getMcKey()) ;
		
		//#CM33518
		// Retrieve the corresponding CarryInfo.
		CarryInformation[] carryInfo = (CarryInformation[]) cih.find(cskey) ;

		//#CM33519
		// There is no corresponding data.
		if (carryInfo.length == 0)
		{
			//#CM33520
			// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
			Object[] tObj = new Object[1] ;
			tObj[0] = id68dt.getMcKey();
			RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, "Id68Process", tObj);
			return ;
		}

		//#CM33521
		// Register only the on-line indication and the station that has on-line indication function.
		Station st = null;
		try
		{
			st = StationFactory.makeStation(wConn, id68dt.getStationNo());
		}
		catch (NotFoundException e)
		{
			//#CM33522
			// If there is no corresponding data, 
			//#CM33523
			// no handling of exception is done but terminates this ID processing jsut as it is. 
			return;
		}
		
		if ((st.getOperationDisplay() == Station.OPERATIONDISPONLY)
		 || (st.getOperationDisplay() == Station.OPERATIONINSTRUCTION))
		{
			OperationDisplay od = new OperationDisplay();
			OperationDisplayHandler odh = new OperationDisplayHandler(wConn);
			//#CM33524
			// Records in OPERATIONDISPLAY data.
			od.setCarryKey(id68dt.getMcKey());
			od.setStationNumber(id68dt.getStationNo());
			od.setArrivalDate(new Date());
			odh.create(od);
		}
	}
}
//#CM33525
//end of class

