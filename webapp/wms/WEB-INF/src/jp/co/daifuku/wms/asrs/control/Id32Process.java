// $Id: Id32Process.java,v 1.2 2006/10/26 03:04:25 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33024
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id32;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;


//#CM33025
/**
 * This class processes the response to retrieval instruction. It updates teh corresponding CarryInformation
 * according to the classification of response. 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 03:04:25 $
 * @author  $Author: suresh $
 */
public class Id32Process extends IdProcess
{
	//#CM33026
	// Class fields --------------------------------------------------
	//#CM33027
	// Classificaiotn of response (normal)
	private static final int RESPONSE_NORMAL = 0 ;
	//#CM33028
	// Classificaiotn of response (multiple set)
	private static final int MULTI_SETTING   = 3 ;
	//#CM33029
	// Classificaiotn of response (off-line)
	private static final int OFFLINE         = 6 ;
	//#CM33030
	// Classificaiotn of response (BUFFER FULL)
	private static final int BUFFER_BULL     = 11 ;
	//#CM33031
	// Classificaiotn of response (DATA ERROR)
	private static final int DATA_ERROR      = 99 ;

	//#CM33032
	// AGCNo
	private int wAgcNumber ;

	//#CM33033
	// Class variables -----------------------------------------------

	//#CM33034
	// Class method --------------------------------------------------
	//#CM33035
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 03:04:25 $") ;
	}

	//#CM33036
	// Constructors --------------------------------------------------
	//#CM33037
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id32Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}
	
	//#CM33038
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id32Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33039
	// Public methods ------------------------------------------------

	//#CM33040
	// Package methods -----------------------------------------------

	//#CM33041
	// Protected methods ---------------------------------------------
	//#CM33042
	/**
	 * Processing the response to retrieval instruction.
	 * Search <code>CarryInformation</code> based on the MC Key of the received communication message,
	 * then according to the classification of reply to the RetrievalInstruction, alter the carry status.
	 * However the call source needs to commit or rollback the transaction, as they are not done here.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
        Object[] tObj = new Object[1] ;
		CarryInformation ci ;
		As21Id32 id32dt = new As21Id32(rdt) ;
		CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
		CarryInformationSearchKey cskey = new CarryInformationSearchKey() ;
		CarryInformationAlterKey cakey = new CarryInformationAlterKey();


		String[] ckey     = id32dt.getMcKey();
		int[]    responce = id32dt.getResponseData();

		//#CM33043
		// Processes as much text data received (max. 2 in standard)
		for (int i = 0 ; i < ckey.length ; i++)
		{
			//#CM33044
			// Sets MC key as a search condition.
			cskey.KeyClear();
			cskey.setCarryKey(ckey[i]) ;
			
			//#CM33045
			// Retrieves corresponding CarryInfo.
			CarryInformation[] earr = (CarryInformation[]) cih.find(cskey) ;
			
			//#CM33046
			// There is no correspondig data.
			if (earr.length == 0)
			{
				//#CM33047
				// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
				tObj = new Object[1] ;
				tObj[0] = ckey[i];
				RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, "Id32Process", tObj);
				continue ;
			}

			if (earr[0] instanceof CarryInformation)
			{
				ci = (CarryInformation)earr[0] ;
				PaletteAlterKey pakey = new PaletteAlterKey();
				PaletteHandler paletteHandle = new PaletteHandler(wConn);
				
				//#CM33048
				// Checks the classification of response.
				switch (responce[i])
				{
					//#CM33049
					// Normal response and carry status to be altered to 'instruction given'.
					case RESPONSE_NORMAL:
						//#CM33050
						// If carry status is 'wait to reply' or to be checked,
						if(ci.getCmdStatus() == CarryInformation.CMDSTATUS_WAIT_RESPONSE)
						{
							cakey.KeyClear();
							cakey.updateCmdStatus(CarryInformation.CMDSTATUS_INSTRUCTION);
							cakey.setCarryKey(ci.getCarryKey());
							cih.modify(cakey);
						}
						else
						{
							//#CM33051
							// If carry status is anything other than 'wait for response', it output the 
							//#CM33052
							// warning message. There is no processing of carry data.
							//#CM33053
							// 6024015=Unexpected reply to picking instruction. No action for the received text. mckey={0}
							tObj = new Object[1] ;
							tObj[0] = id32dt.getMcKey();
							RmiMsgLogClient.write(6024015, LogMessage.F_WARN, "Id32Process", tObj);
						}
						break;

					//#CM33054
					// Outputting log for Multiple set
					case MULTI_SETTING:
						//#CM33055
						// 6024006=Multiple allocation was received in response to transfer instruction. mckey={0}
						tObj = new Object[1] ;
						tObj[0] = ckey[i];
						RmiMsgLogClient.write(6024006, LogMessage.F_WARN, "Id32Process", tObj);
						break;

					//#CM33056
					// Off-line
					case OFFLINE:
						//#CM33057
						// 6024008=Offline was received in response to transfer instruction. mckey={0}
						tObj = new Object[1] ;
						tObj[0] = ckey[i];
						RmiMsgLogClient.write(6024008, LogMessage.F_WARN, "Id32Process", tObj);
						
						//#CM33058
						// Reset the carry status to 'start'.
						cakey.KeyClear();
						cakey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
						cakey.setCarryKey(ci.getCarryKey());
						cih.modify(cakey);
						
						//#CM33059
						// Set the status of pallet to 'reserved for retrieval'
						pakey.KeyClear();
						pakey.setPaletteId(ci.getPaletteId());
						pakey.updateStatus(Palette.RETRIEVAL_PLAN);
						pakey.updateRefixDate(new java.util.Date());
						paletteHandle.modify(pakey);

						//#CM33060
						// Modifies the status of GroupControler to off-line.
						GroupController tgc = GroupController.getInstance(wConn, wAgcNumber);
						tgc.setStatus(GroupController.STATUS_OFFLINE) ;
						break;

					//#CM33061
					// Buffer full
					case BUFFER_BULL:
						//#CM33062
						// 6024010=Buffer full was received in response to transfer instruction. mckey={0}
						tObj = new Object[1] ;
						tObj[0] = ckey[i];
						RmiMsgLogClient.write(6024010, LogMessage.F_WARN, "Id32Process", tObj);
						
						//#CM33063
						// Reset the carry status to 'start'.
						cakey.KeyClear();
						cakey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
						cakey.setCarryKey(ci.getCarryKey());
						cih.modify(cakey);
						
						//#CM33064
						// Set the status of pallet to 'reserved for retrieval'
						pakey.KeyClear();
						pakey.updateStatus(Palette.RETRIEVAL_PLAN);
						pakey.updateRefixDate(new java.util.Date());
						pakey.setPaletteId(ci.getPaletteId());
						paletteHandle.modify(pakey);
						break;

					//#CM33065
					// Data error
					case DATA_ERROR:
						//#CM33066
						// 6024011=Data error was received in response to transfer instruction. mckey={0}
						tObj = new Object[1] ;
						tObj[0] = ckey[i];
						RmiMsgLogClient.write(6024011, LogMessage.F_WARN, "Id32Process", tObj);
						
						//#CM33067
						// Modifies the carry status to 'error'.
						cakey.KeyClear();
						cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ERROR);
						cakey.setCarryKey(ci.getCarryKey());
						cih.modify(cakey);
						
						//#CM33068
						// Alther the status of receiving station to 'suspended'.
						StationOperator sOpe = new StationOperator(wConn, ci.getDestStationNumber());
						sOpe.alterSuspend(true);
						break;

					default:
						//#CM33069
						// 6026037=Response category for text ID {0} is invalid.  Receive response category={1}
						tObj = new Object[2] ;
						tObj[0] = "32";
						tObj[1] = Integer.toString(responce[i]);
						RmiMsgLogClient.write(6026037, LogMessage.F_WARN, "Id32Process", tObj);
						throw new InvalidDefineException("6026037" + wDelim + tObj[1]);
				}
			}
		}
	}

	//#CM33070
	// Private methods -----------------------------------------------

}
//#CM33071
//end of class

