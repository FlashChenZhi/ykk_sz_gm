// $Id: Id21Process.java,v 1.2 2006/10/26 03:09:26 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32601
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id21;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.base.dbhandler.StationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.entity.Station;

//#CM32602
/**
 * This class process Start work receipt response process. GROUPCONTROLER is updated based
 * on the receipt response contents.
 * If system recovery is carried out,
 * write the details to message log file. Data clear process is not done at eWareNavi end.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/24</TD><TD>Y.Kawai</TD><TD>eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:09:26 $
 * @author  $Author: suresh $
 */
public class Id21Process extends IdProcess
{
	//#CM32603
	//Class fields----------------------------------------------------

	//#CM32604
	// Class variables -----------------------------------------------
	//#CM32605
	/**
	 * Preserves the AGCNo to update.
	 */
	private int  wAgcNumber;

	//#CM32606
	// Class method --------------------------------------------------
	//#CM32607
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 03:09:26 $");
	}

	//#CM32608
	// Constructors --------------------------------------------------
	//#CM32609
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id21Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM32610
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id21Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM32611
	// Public methods ------------------------------------------------

	//#CM32612
	// Package methods -----------------------------------------------

	//#CM32613
	// Protected methods ---------------------------------------------
	//#CM32614
	/**
	 * Processes the response to the work start.
	 * Based on the classificaiotn of response received, handles the respective operations.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	 protected void processReceivedInfo(byte[] rdt) throws Exception
	 {
		As21Id21   id21dt = new As21Id21(rdt) ;

		//#CM32615
		// Generating the instance of GroupController corresponding to the ACGCNo. received.
		GroupController gct = GroupController.getInstance(wConn, wAgcNumber);
		String str = id21dt.getResponseClassification();

		//#CM32616
		// If the the classificaiotn of response is normal, sets the GroupControler.Status on-line,
		//#CM32617
		// then initializes the status of the station.
		if (str.equals(As21Id21.NORMAL))
		{
			gct.setStatus(GroupController.STATUS_ONLINE);
			initialize();
		}
		//#CM32618
		// If the the classificaiotn of response os AGC status error, message log should be output.
		else if (str.equals(As21Id21.AGC_CONDITION_ERR))
		{
			Object[] tObj = new Object[1] ;
			tObj[0] = Integer.toString(wAgcNumber) ;
			//#CM32619
			// 6024001=SRC status error SRC No.={0}
			RmiMsgLogClient.write(6024001, LogMessage.F_ERROR, "Id21Process", tObj);
		}
		//#CM32620
		// If the classificaiotn of response is DATA ERROR, message log should be output.
		else if (str.equals(As21Id21.DATA_ERR))
		{
			//#CM32621
			// 6024002=DATA ERROR
			RmiMsgLogClient.write(6024002, LogMessage.F_ERROR, "Id21Process", null);
		}
		//#CM32622
		// The classificaiotn of response is invalid.
		else
		{
			Object[] tObj = new Object[2] ;
			tObj[0] = "21" ;
			tObj[1] = str ;
			//#CM32623
			// 6026037=Response category for text ID {0} is invalid.  Receive response category={1}
			RmiMsgLogClient.write(6026037, LogMessage.F_ERROR, "Id21Process", tObj);
			throw (new InvalidProtocolException("6026037" + wDelim + tObj[0] + wDelim + tObj[1])) ;
		}

		//#CM32624
		// If SYSTEM RECOVERY is operated, message log should be output.
		if( id21dt.getSystemRecoveryReport() )
		{
			Object[] tObj = new Object[1] ;
			tObj[0] = Integer.toString(wAgcNumber) ;
			//#CM32625
			// 6022014=System recovery implementation report is received from SRC No. {0}.
			RmiMsgLogClient.write(6022014, LogMessage.F_INFO, "Id21Process", tObj);
		}
	}

	//#CM32626
	// Private methods -----------------------------------------------
	//#CM32627
	/**
	 * Initializes the status of the station which belongs to <code>GroupControler</code>.
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in condition of data update.
	 * @throws RemoteException : Exception related to communication generated while executing remote method call
	 */
	public void initialize() throws ReadWriteException, InvalidDefineException, NotFoundException
	{
		//#CM32628
		// Updating the station table.
		//#CM32629
		// With AGCNo as a condition, generates the station instance and update the status based on the machine information.
		//#CM32630
		// Generating instance of SearchKey.
		StationSearchKey sk = new StationSearchKey(); 
		sk.setControllerNumber(wAgcNumber);
		
		//#CM32631
		// Generating handler instance.
		StationHandler sh = new StationHandler(wConn);
		Station[] st = (Station[])sh.find(sk);
		for (int i = 0 ; i < st.length ; i++)
		{
			//#CM32632
			// If the station is 'storage/retrieval ready' and not on 'AGC mode', initialize.
			if (st[i].getStationType() == Station.STATION_TYPE_INOUT && st[i].getModeType() != Station.AGC_MODE_CHANGE)
			{
				//#CM32633
	 			// current mode              :nutral
	 			//#CM32634
	 			// request for mode switch   : no request for mode switch
	 			//#CM32635
	 			// start time of the switch  : null
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
//#CM32636
//end of class
