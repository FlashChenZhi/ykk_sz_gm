// $Id: Id35Process.java,v 1.2 2006/10/26 02:24:47 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM33307
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.InvalidProtocolException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.communication.as21.As21Id35;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Station;
//#CM33308
/**
 * This class processes the carry data deletion report. It deletes the CarryInformation corresponding to the specified mckey.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 02:24:47 $
 * @author  $Author: suresh $
 */
public class Id35Process extends IdProcess
{
	//#CM33309
	// Class fields --------------------------------------------------

	//#CM33310
	// Class variables -----------------------------------------------
	//#CM33311
	// AGCNo
	private int wAgcNumber ;

	//#CM33312
	/**
	 * class name
	 */
	private static final String CLASS_NAME = "Id35Process";

	//#CM33313
	// Class method --------------------------------------------------
	//#CM33314
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 02:24:47 $") ;
	}

	//#CM33315
	// Constructors --------------------------------------------------
	//#CM33316
	/**
     * Default constructor
     * Sets GroupController.DEFAULT_AGC_NUMBER as AGCNo.
     */
	public Id35Process()
	{
		super() ;
		wAgcNumber = GroupController.DEFAULT_AGC_NUMBER;
	}

	//#CM33317
	/**
	 * Sets the AGCNo passed through parameter, then initialize this class.
	 * @param agcNumber AGCNo
	 */
	public Id35Process(int agcNumber)
	{
		super();
		wAgcNumber = agcNumber;
	}

	//#CM33318
	// Public methods ------------------------------------------------

	//#CM33319
	// Package methods -----------------------------------------------

	//#CM33320
	// Protected methods ---------------------------------------------
	//#CM33321
	/**
	 * Processes the carry data deletion report.
	 * If the Carry Key received was for dummy arrival data, clear the dummy arrival data in the station.
	 * If it is a normal Carry Key, delete the carry data and stock data according to the corresponding CarryInfomation.
	 * @param :communication message received
	 * @throws  Exception  :in case any error occured
	 */
	protected void processReceivedInfo(byte[] rdt) throws Exception
	{
		As21Id35 id35dt = new As21Id35(rdt) ;
		
		CarryCompleteOperator carryCompOpe = new CarryCompleteOperator();
		carryCompOpe.setClassName(CLASS_NAME);

		//#CM33322
		// 6022032=Transfer data deletion report is received. mckey={0}
		Object[] tObj = new Object[1] ;
		tObj[0] = id35dt.getMcKey();
		RmiMsgLogClient.write(6022032 , LogMessage.F_NOTICE, this.getClass().getName(), tObj);

		//#CM33323
		// Checks the MCkey.
		if (AsrsParam.DUMMY_MCKEY.equals(id35dt.getMcKey()))
		{
			//#CM33324
			// If it was a dummy arrival, clear the arrival data of receiving station.
			StationOperator sOpe = new StationOperator(wConn, id35dt.getReceivingStationNo());
			sOpe.dropArrival();
		}
		else
		{
			//#CM33325
			//Searches the CarryInfo based on MCKEY.
			CarryInformationSearchKey cskey = new CarryInformationSearchKey() ;
			cskey.setCarryKey(id35dt.getMcKey()) ;
			CarryInformationHandler cih = new CarryInformationHandler(wConn) ;
			CarryInformation[] earr = (CarryInformation[]) cih.find(cskey) ;
			//#CM33326
			// There is no corresponding data.
			if (earr.length == 0)
			{
				//#CM33327
				// 6026038=Transfer data for the designated MCKey does not exist. mckey={0}
				tObj = new Object[1] ;
				tObj[0] = id35dt.getMcKey();
				RmiMsgLogClient.write(6026038 , LogMessage.F_ERROR, this.getClass().getName(), tObj);
				return ;
			}
			
			if (earr[0] instanceof CarryInformation)
			{
				CarryInformation ci= (CarryInformation)earr[0];
				if (ci.getCmdStatus() == CarryInformation.CMDSTATUS_ARRIVAL)
				{	
					Station curStation = StationFactory.makeStation(wConn, ci.getSourceStationNumber());

					if (curStation.isArrivalCheck())
					{
						//#CM33328
						// If the pallet arrived at the station where arrival reports is operated,
						//#CM33329
						// the arrival data should be cleared.
						StationOperator sOpe = new StationOperator(wConn, ci.getSourceStationNumber());
						sOpe.dropArrival();
					}
				}
				
				//#CM33330
				//Carryinfo WorkInfo Palette Stock are to be deleted
				//#CM33331
				// 6022033=Transfer data is deleted. mckey={0}
				carryCompOpe.drop(wConn, ci, true) ;
				tObj = new Object[1] ;
				tObj[0] = id35dt.getMcKey();
				RmiMsgLogClient.write(6022033, LogMessage.F_NOTICE, this.getClass().getName(), tObj);
			}
			else
			{
				tObj = new Object[1] ;
				tObj[0] = "CarryInformation" ;
				//#CM33332
				// 6006008=Object other than {0} was returned.
				RmiMsgLogClient.write(6006008, LogMessage.F_ERROR, this.getClass().getName(), tObj);
				throw (new InvalidProtocolException("6006008" + wDelim + tObj[0])) ;
			}
		}
	}

	//#CM33333
	// Private methods -----------------------------------------------
}
//#CM33334
//end of class

