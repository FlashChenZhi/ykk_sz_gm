// $Id: Id63Process.java,v 1.2 2006/10/26 02:22:29 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33441
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.rmi.RmiSendClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id63;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.entity.Station;

//#CM33442
/**
 * This class process the completion report of work mode switch. It modifies the current mode
 * of corresponding Station according to the specified completion mode.
 * When modifying, it will not considers the contents of requesting flag of the Station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:22:29 $
 * @author  $Author: suresh $
 */
public class Id63Process extends IdProcess
{
	//#CM33443
	// Class fields --------------------------------------------------

	//#CM33444
	/**
	 * Completion mode (storage)
	 */
	private static final int FAINL_MODE_IN = 1 ;

	//#CM33445
	/**
	 * Completion mode (retrieval)
	 */
	private static final int FAINL_MODE_OUT= 2 ;

	//#CM33446
	// Class variables -----------------------------------------------
	//#CM33447
	/**
	 * Preserves the AGCno. to update.
	 */
	private int  wAgcNumber;

	//#CM33448
	// Class method --------------------------------------------------
	//#CM33449
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:22:29 $") ;
	}

	//#CM33450
	// Constructors --------------------------------------------------
	//#CM33451
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id63Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33452
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id63Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33453
	// Public methods ------------------------------------------------

	//#CM33454
	// Package methods -----------------------------------------------

	//#CM33455
	// Protected methods ---------------------------------------------

	//#CM33456
	/**
	 * Processes the receiving of work mode switch completion report.
	 * Based on the Station no. in the communication message received, it searches <code>Station</code>
	 * and updates the work mode.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		As21Id63 id63dt = new As21Id63(rdt) ;
		StationAlterKey stakey = new StationAlterKey();
		StationHandler     sth = new StationHandler(wConn);

		Station st = StationFactory.makeStation(wConn,(id63dt.getStationNo()));
		int completionMode = id63dt.getCompletionMode();

		switch(completionMode){
			//#CM33457
			// Switching to storage mode
			case FAINL_MODE_IN:
				stakey.setStationNumber(st.getStationNumber());
				stakey.updateCurrentMode(Station.STORAGE_MODE);
				stakey.updateModeRequest(Station.NO_REQUEST);
				stakey.updateModeRequestTime(null);
				sth.modify(stakey);				
				break;
				
			//#CM33458
			// Switching to retrieval mode
			case FAINL_MODE_OUT:
				stakey.setStationNumber(st.getStationNumber());
				stakey.updateCurrentMode(Station.RETRIEVAL_MODE);
				stakey.updateModeRequest(Station.NO_REQUEST);
				stakey.updateModeRequestTime(null);
				sth.modify(stakey);
				break;
		}

		//#CM33459
		// If it was with automatic mode switch station, it sends the message
		//#CM33460
		// so that the carry instruction of automatic mode switch shall gets out
		//#CM33461
		// of the loop.
		if (st.getModeType() == Station.AUTOMATIC_MODE_CHANGE)
		{
			RmiSendClient rmiSndC = new RmiSendClient();
			Object[] param = new Object[2];
			param[0] = null;
			//#CM33462
			// Kicks the AutomaticModeChangeSender.
			rmiSndC.write("AutomaticModeChangeSender", param);
		}
	}
	
	//#CM33463
	// Private methods -----------------------------------------------

}
//#CM33464
//end of class
