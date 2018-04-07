// $Id: Id23Process.java,v 1.2 2006/10/26 03:08:37 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32668
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id23;
import jp.co.daifuku.wms.asrs.communication.as21.As21MachineState;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.MachineAlterKey;
import jp.co.daifuku.wms.base.dbhandler.MachineHandler;
import jp.co.daifuku.wms.base.dbhandler.MachineSearchKey;
import jp.co.daifuku.wms.base.entity.Machine;
import jp.co.daifuku.wms.base.entity.Station;

//#CM32669
/**
 * This class processes the receiving of response to work completion. According to the contents of response received, it updates the GROUPCONTROLER.
 * When the status of GROUPCONTROLLER is altered to OFFLINE, it disconnects the status of MACHINESTATE under GROUPCONTROLLER.
 * Except any machines off-line remain the status.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:08:37 $
 * @author  $Author: suresh $
 */
public class Id23Process extends IdProcess
{
	//#CM32670
	//Class fields----------------------------------------------------

	//#CM32671
	// Class variables -----------------------------------------------
	//#CM32672
	/**
	 * Preserves target AGCNO.
	 */
	 private int  wAgcNumber;

	//#CM32673
	// Class method --------------------------------------------------
	//#CM32674
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return("$Revision: 1.2 $,$Date: 2006/10/26 03:08:37 $");
	}

	//#CM32675
	// Constructors --------------------------------------------------
	//#CM32676
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id23Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM32677
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id23Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM32678
	// Public methods ------------------------------------------------

	//#CM32679
	// Package methods -----------------------------------------------

	//#CM32680
	// Protected methods ---------------------------------------------
	//#CM32681
	/**
	 * Processes the response to work completion.
	 * Based on the classificaiotn of response received, handles the respective operations.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		As21Id23   id23dt = new As21Id23(rdt) ;

		//#CM32682
		// Based on the AGCNo. received, generate the instance of GroupController.
		GroupController gct = GroupController.getInstance(wConn , wAgcNumber);
		String str = id23dt.getResponseClassification ();

		//#CM32683
		// Normal end
		if (str.equals(As21Id23.NORMAL_END))
		{
			//#CM32684
			// If hte classificaiton of response is normal, set the GroupController.Status off-line.
			gct.setStatus(GroupController.STATUS_OFFLINE);

			//#CM32685
			// Updating the machine table
			//#CM32686
			// Generatest the list of machine information based on the AGCNo.
			MachineSearchKey    mk = new MachineSearchKey();
			MachineHandler mh = new MachineHandler(wConn);

			mk.setControllerNumber(wAgcNumber);  
			Machine[] machines = (Machine[])mh.find(mk);
			for (int i = 0 ; i < machines.length ; i++)
			{
				//#CM32687
				// If the machine status is other than offline, change status to no communication (status disabled)
				if (machines[i].getState() != As21MachineState.STATE_OFFLINE )
				{
					MachineAlterKey mak = new MachineAlterKey();
					mak.setMachineType(machines[i].getMachineType());
					mak.setMachineNumber(machines[i].getMachineNumber());
					mak.setControllerNumber(machines[i].getControllerNumber());
					mak.updateState(Machine.STATE_DISCONNECT);
					mak.updateErrorCode(null);
					mh.modify(mak);					
				}
			}

			//#CM32688
			// update station and aisle status
			Id30Process id30 = new Id30Process(wAgcNumber);
			for (int i = 0 ; i < machines.length ; i++)
			{
				Station st = StationFactory.makeStation(wConn, machines[i].getStationNumber());
				id30.updateStatus(wConn, st);
			}			
		}
		//#CM32689
		// Not able to end
		else if (str.equals(As21Id23.END_IMPOSS))
		{
			//#CM32690
			// Sets GroupController.Status on-line.
			gct.setStatus(GroupController.STATUS_ONLINE);
			Object[] tObj = new Object[2] ;
			tObj[0] = id23dt.getModelCode() ;
			tObj[1] = id23dt.getMachineNo() ;
			//#CM32691
			// 6024028=Unable to terminate was received in response to End Work. Machine type Code={0} Machine No.={1}.
			RmiMsgLogClient.write(6024028, LogMessage.F_WARN, "Id23Process", tObj);
		}
		//#CM32692
		// Data error
		else if (str.equals(As21Id23.DATA_ERR))
		{
			//#CM32693
			// Sets GroupController.Status on-line
			gct.setStatus(GroupController.STATUS_ONLINE);
			Object[] tObj = new Object[2] ;
			tObj[0] = id23dt.getModelCode() ;
			tObj[1] = id23dt.getMachineNo() ;
			//#CM32694
			// 6024029=Data error was received in response to End Work. Machine type Code={0} Machine No.={1}
			RmiMsgLogClient.write(6024029, LogMessage.F_WARN, "Id23Process", tObj);
		}
		//#CM32695
		// Classificaiton of replay is invalid.
		else
		{
			//#CM32696
			// Sets GroupController.Status on-line
			gct.setStatus(GroupController.STATUS_ONLINE);
			Object[] tObj = new Object[2] ;
			tObj[0] = "23";
			tObj[1] = str;
			//#CM32697
			// 6026037=Response category for text ID {0} is invalid.  Receive response category={1}
			RmiMsgLogClient.write(6026037, LogMessage.F_ERROR, "Id23Process", tObj);
		}
	}

	//#CM32698
	// Private methods -----------------------------------------------

}
//#CM32699
//end of class
