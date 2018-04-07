// $Id: Id61Process.java,v 1.2 2006/10/26 02:23:18 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33385
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.communication.as21.As21Id61;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.entity.Station;

//#CM33386
/**
 * This class proceses the work mode switch request. It switches the mode switch requesting flag of the 
 * corresponding Station according to the specified request classification.
 * Concerning AGC, it always returns the normal acceptance excepting the cases where corresponding station cannot be found
 * or the case where the process failed.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:23:18 $
 * @author  $Author: suresh $
 */
public class Id61Process extends IdProcess
{
	//#CM33387
	// Class fields --------------------------------------------------
	//#CM33388
	/**
	 * Request classification (storage)
	 */
	private static final int REQUEST_CLASS_IN         = 1 ;

	//#CM33389
	/**
	 * Request classification (retrieval)
	 */
	private static final int REQUEST_CLASS_OUT        = 2 ;

	//#CM33390
	/**
	 * Request classification (storage request cancel)
	 */
	private static final int REQUEST_CLASS_IN_CANCEL  = 3 ;

	//#CM33391
	/**
	 * Request classification (retrieval request cancel)
	 */
	private static final int REQUEST_CLASS_OUT_CANCEL = 4 ;

	//#CM33392
	/**
	 * Response classification (normaly received)
	 */
	private static final String RESPONCE_OK           = "00" ;

	//#CM33393
	/**
	 * Response classification (Error (working))
	 */
	private static final String RESPONCE_NG_WORKING   = "01" ;

	//#CM33394
	/**
	 * Response classification (Error (Station No.))
	 */
	private static final String RESPONCE_NG_STATION   = "02" ;

	//#CM33395
	// Class variables -----------------------------------------------
	private int  wAgcNumber;

	//#CM33396
	// Class method --------------------------------------------------
	//#CM33397
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:23:18 $") ;
	}

	//#CM33398
	// Constructors --------------------------------------------------
	//#CM33399
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id61Process()
	{
		super();
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33400
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id61Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33401
	// Public methods ------------------------------------------------

	//#CM33402
	// Package methods -----------------------------------------------

	//#CM33403
	// Protected methods ---------------------------------------------
	//#CM33404
	/**
	 * Processing the work mode switch reequest.
	 * Search <code>Station</code> based on the StationNo. of text received, then request 
	 * for the work mode switch based on the request classification.
	 * If normally processed, it sends the normal response to AGC.
	 * In case the Station was not found, or in case the process failed, it sends StationNo Error to AGC.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		java.util.Date curDate = new java.util.Date();
		StationAlterKey stakey = new StationAlterKey();
		StationHandler     sth = new StationHandler(wConn);
		
		String wReqinfo = null;
		As21Id61 id61dt = new As21Id61(rdt) ;
		Station st = null;
		
		try
		{
			st = StationFactory.makeStation(wConn, id61dt.getStationNo());
			try
			{
				int req = id61dt.getRequestClassification();
				switch (req)
				{
					//#CM33405
					// Requests to switch to the storage mode
					case REQUEST_CLASS_IN:
						stakey.setStationNumber(st.getStationNumber());
						stakey.updateModeRequest(Station.STORAGE_REQUEST);
						stakey.updateModeRequestTime(curDate);
						sth.modify(stakey);
						
						wReqinfo = RESPONCE_OK;
						break;
						
					//#CM33406
					// Requests to switch to the retrieval mode
					case REQUEST_CLASS_OUT:
						stakey.setStationNumber(st.getStationNumber());
						stakey.updateModeRequest(Station.RETRIEVAL_REQUEST);
						stakey.updateModeRequestTime(curDate);
						sth.modify(stakey);
						
						wReqinfo = RESPONCE_OK;
						break;
						
					//#CM33407
					// Cancels the request to switch to the retrieval mode
					case REQUEST_CLASS_IN_CANCEL:
						stakey.setStationNumber(st.getStationNumber());
						stakey.updateModeRequest(Station.NO_REQUEST);
						stakey.updateModeRequestTime(null);
						sth.modify(stakey);
					
						wReqinfo = RESPONCE_OK;
						break;
						
					//#CM33408
					// Cancels the request to switch to the storage mode
					case REQUEST_CLASS_OUT_CANCEL:
						stakey.setStationNumber(st.getStationNumber());
						stakey.updateModeRequest(Station.NO_REQUEST);
						stakey.updateModeRequestTime(null);
						sth.modify(stakey);
						
						wReqinfo = RESPONCE_OK;
						break;
						
					default:
						wReqinfo = RESPONCE_NG_STATION;
				}
				//#CM33409
				//Submitting the reply to work mode switch request.
				SystemTextTransmission.id41send(st.getStationNumber(), wReqinfo, wAgcNumber);
			}
			catch (Exception e)
			{
				//#CM33410
				// In case the update of received text failed, it sends StationNo Error.
				wReqinfo = RESPONCE_NG_STATION;
				SystemTextTransmission.id41send(id61dt.getStationNo(), wReqinfo, wAgcNumber);
				throw e;
			}
		}
		catch (Exception e)
		{
			//#CM33411
			// If the generation of station instance of the received text failed, it sends StationNo Error.
			wReqinfo = RESPONCE_NG_STATION;
			SystemTextTransmission.id41send(id61dt.getStationNo(), wReqinfo, wAgcNumber);
			throw e;
		}
	}
}
//#CM33412
//end of class
