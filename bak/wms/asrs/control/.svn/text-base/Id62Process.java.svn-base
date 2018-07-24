// $Id: Id62Process.java,v 1.2 2006/10/26 02:22:54 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33413
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id62;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.entity.Station;

//#CM33414
/**
 * This class processes the response to the work mode switch instruction.
 * If the response classification is either normal or switching the work mode, there will be no
 * processing. For error response with other cases, it cancels the mode switch request at the corresponding station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:22:54 $
 * @author  $Author: suresh $
 */
public class Id62Process extends IdProcess
{
	//#CM33415
	// Class fields --------------------------------------------------
	//#CM33416
	/**
	 * Reponse classification (normal acceptance)
	 */
	private static final String NORMAL			= "00" ;

	//#CM33417
	/**
	 * Reponse classification (switching to Error Mode)
	 */
	private static final String MODE_CHANGE		= "01" ;

	//#CM33418
	/**
	 * Reponse classification (Error Station No .Error)
	 */
	private static final String STATION_ERROR	= "02" ;

	//#CM33419
	/**
	 *Reponse classification (Error instruction Mode)
	 */
	private static final String MISION_MODE		= "03" ;

	//#CM33420
	/**
	 * Reponse classification (Error, with Carry data)
	 */
	private static final String H_Mode			= "04" ;


	//#CM33421
	// Class variables -----------------------------------------------
	//#CM33422
	/**
	 * Preseres the AGCno. to update.
	 */
	private int  wAgcNumber;

	//#CM33423
	// Class method --------------------------------------------------
	//#CM33424
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:22:54 $") ;
	}

	//#CM33425
	// Constructors --------------------------------------------------
	//#CM33426
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id62Process()
	{
		super();
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33427
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id62Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33428
	// Public methods ------------------------------------------------

	//#CM33429
	// Package methods -----------------------------------------------

	//#CM33430
	// Protected methods ---------------------------------------------
	//#CM33431
	/**
	 * Processes the response to the work mode switch instruction.
	 * Based on the StationNO. of the text received, search <code>Station</code>.
	 * Then based on the reponse classification, update the work mode switch request.
	 * There will be no processing if it is normal or if switching the work mode.
	 * If the response was error for other cases, it cncels the mode switch request at corresponding stations.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt)throws Exception
	{
		As21Id62 id62dt = new As21Id62(rdt) ;
		StationAlterKey sakey = new StationAlterKey();
		StationHandler sh = new StationHandler(wConn);
		Station st = StationFactory.makeStation(wConn, id62dt.getStationNo());

		String responseClassification = id62dt.getResponseClassification();

		if (responseClassification.equals(NORMAL))
		{
			//#CM33432
			// If normally processed, there will be no processing.
		}
		else if (responseClassification.equals(MODE_CHANGE))
		{
			//#CM33433
			// If mode is being switched, it determines there should be no problem and waits for 
			//#CM33434
			// completion report. (there will be no processing)
		}
		else if (responseClassification.equals(STATION_ERROR))
		{
			//#CM33435
			// 6022023=Unable to accept the mode change request due to invalid station No. ST No={0}
			Object[] tObj = new Object[1] ;
			tObj[0] = st.getStationNumber() ;
			RmiMsgLogClient.write(6022023, LogMessage.F_ERROR, "Id62Process", tObj);

			//#CM33436
			// Cancels the mode switch request.
			sakey.setStationNumber(st.getStationNumber());
			sakey.updateModeRequest(Station.NO_REQUEST);
			sakey.updateModeRequestTime(null);
			sh.modify(sakey);
		}
		else if (responseClassification.equals(MISION_MODE))
		{
			//#CM33437
			// 6022024=Unable to accept the mode change request due to invalid mode. ST No={0}
			Object[] tObj = new Object[1] ;
			tObj[0] = st.getStationNumber() ;
			RmiMsgLogClient.write(6022024, LogMessage.F_ERROR, "Id62Process", tObj);

			//#CM33438
			// Cancels the mode switch request.
			sakey.setStationNumber(st.getStationNumber());
			sakey.updateModeRequest(Station.NO_REQUEST);
			sakey.updateModeRequestTime(null);
			sh.modify(sakey);
		}
		else if (responseClassification.equals(H_Mode))
		{
			//#CM33439
			// 6022025=Unable to accept the mode change request. Transfer data exists. ST No={0}
			Object[] tObj = new Object[1] ;
			tObj[0] = st.getStationNumber() ;
			RmiMsgLogClient.write(6022025, LogMessage.F_ERROR, "Id62Process", tObj);

			//#CM33440
			// Cancels the mode switch request.
			sakey.setStationNumber(st.getStationNumber());
			sakey.updateModeRequest(Station.NO_REQUEST);
			sakey.updateModeRequestTime(null);
			sh.modify(sakey);
		}
		else
		{
			throw (new InvalidProtocolException("Invalid Completion Class for Incoming")) ;
		}
	}
}
