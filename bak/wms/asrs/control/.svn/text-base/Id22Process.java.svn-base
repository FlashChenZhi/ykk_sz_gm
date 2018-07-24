// $Id: Id22Process.java,v 1.2 2006/10/26 03:09:04 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32637
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id22;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Station;

//#CM32638
/**
 * This class processes the receiving of date and time requests. It submits the data of date and time to the 
 * target AGC. When work start is reported, it updates the corresponding GROUPCONTROLLER on-line.
 * If the operation of system recovery is reported, it outputs the contents to message logs, however,
 * there will be no processing on eWareNavi side such as data clearing, etc.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:09:04 $
 * @author  $Author: suresh $
 */
public class Id22Process extends IdProcess
{
	//#CM32639
	//Class fields----------------------------------------------------

	//#CM32640
	// Class variables -----------------------------------------------
	//#CM32641
	/**
	 * Preservest the target AGCNo.
	 */
	private int  wAgcNumber;

	//#CM32642
	// Class method --------------------------------------------------
	//#CM32643
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 03:09:04 $");
	}
	

	//#CM32644
	// Constructors --------------------------------------------------
	//#CM32645
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id22Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM32646
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id22Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM32647
	// Public methods ------------------------------------------------

	//#CM32648
	// Package methods -----------------------------------------------

	//#CM32649
	// Protected methods ---------------------------------------------
	//#CM32650
	/**
	 * Processes the date and time requests.
	 * It submits the data of date and time to the target AGC and based on the classificaiotn of response received, handles the respective operations.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	 protected void processReceivedInfo(byte[] rdt) throws Exception
	 {
		As21Id22   id22dt = new As21Id22(rdt) ;

		//#CM32651
		// Generating the instance of GroupController corresponds to the received AGCNo.
		GroupController gct = GroupController.getInstance(wConn , wAgcNumber);
		int req = id22dt.getRequestClassification();

		//#CM32652
		// If the the request classification is work start, sets the GroupControler.Status on-line,
		//#CM32653
		// then initializes the status of the station.
		if (req == As21Id22.W_START)
		{
			gct.setStatus(GroupController.STATUS_ONLINE);
			initialize();
		}
		
		//#CM32654
		// Transmission of date and time data.
		SystemTextTransmission.id02send(wAgcNumber);

		//#CM32655
		// If the the request classification is work start and SYSTEM RECOVERY is operated, output the message log.
		if(req == As21Id22.W_START && id22dt.isSystemRecoveryReports())
		{
			Object[] tObj = new Object[1] ;
			tObj[0] = Integer.toString(wAgcNumber);
			//#CM32656
			// 6022014=System recovery implementation report is received from SRC No. {0}.
			RmiMsgLogClient.write(6022014, LogMessage.F_INFO, "Id22Process", tObj);
		}
	}

	//#CM32657
	// Private methods -----------------------------------------------
	//#CM32658
	/**
	 * Initializes the status of the station which belongs to <code>GroupControler</code>.
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in condition of data update.
	 * @throws RemoteException : Exception related to communication generated while executing remote method call
	 */
	public void initialize() throws ReadWriteException, InvalidDefineException, NotFoundException
	{
		//#CM32659
		// Updating the station table
		//#CM32660
		// With AGCNo as a condition, generates a station instatnce. Then, update status based on the machine information.
		//#CM32661
		// Generating the instance of SearchKey
		StationSearchKey sk = new StationSearchKey();   
		sk.setControllerNumber(wAgcNumber);
		
		//#CM32662
		// Generating the handler instance
		StationHandler sh = new StationHandler(wConn);  
		Station[] st = (Station[])sh.find(sk);
		for (int i = 0 ; i < st.length ; i++)
		{
			//#CM32663
			// Initialize if 'storage/retrieval ready' and not on 'AGC mode'.
			if (st[i].getStationType() == Station.STATION_TYPE_INOUT && st[i].getModeType() != Station.AGC_MODE_CHANGE)
			{
				//#CM32664
	 			// current mode              :nutral
	 			//#CM32665
	 			// mode switch request       :no request for mode switch
	 			//#CM32666
	 			// start time for mode switch:null
				StationAlterKey sak = new StationAlterKey();
				sak.setStationNumber(st[i].getStationNumber());
				sak.updateCurrentMode(Station.NEUTRAL);
				sak.updateModeRequest(Station.NO_REQUEST);
				sak.updateModeRequestTime(null);
				sh.modify(sak);
			}
		}
	}
}
//#CM32667
//end of class
