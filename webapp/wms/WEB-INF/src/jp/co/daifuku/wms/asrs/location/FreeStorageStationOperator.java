// $Id: FreeStorageStationOperator.java,v 1.3 2006/11/21 04:22:30 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42405
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.asrs.common.AsrsParam;

//#CM42406
/**<en>
 * Defined in this class of station is the behaviour of storage free station
 * (storage side of U-shaped conveyor, etc.)
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/02/17</TD><TD>INOUE</TD><TD>Correction made so that load size info will be set from arrival data.</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.3 $, $Date: 2006/11/21 04:22:30 $
 * @author  $Author: suresh $
 </en>*/
public class FreeStorageStationOperator extends StationOperator
{
	//#CM42407
	// Class fields --------------------------------------------------

	//#CM42408
	// Class variables -----------------------------------------------
	//#CM42409
	/**<en>
	 * Preserves the station no. of pairing free retrieval station.
	 </en>*/
	private String wFreeRetrievalStationNumber = null;

	//#CM42410
	// Class method --------------------------------------------------
	//#CM42411
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/11/21 04:22:30 $") ;
	}

	//#CM42412
	// Constructors --------------------------------------------------
	//#CM42413
	/**<en>
	 * Creates new instance of <code>Station</code>. If the instance which has stations
	 * already defined is needed, please use <code>StationFactory</code> class.
	 * @param  conn     :Connection with database
	 * @param  snum     :own station no. preserved
	 * @throws ReadWriteException     : Notifies if any trouble occured in data access.
	 * @throws IOException            : Notifies if file I/O error occured.
	 * @see StationFactory
	 </en>*/
	public FreeStorageStationOperator(Connection conn,String snum)
										throws ReadWriteException, IOException
	{
		super(conn, snum);
		//#CM42414
		//<en> Retrieves the paring free retrieval station, according to the own station no.</en>
		wFreeRetrievalStationNumber = Route.getConnectorStation(snum);
	}

	//#CM42415
	/**
	 * Make the instance of new <code>FreeStorageStationOperator</code>.
	 * Maintain the Station instance passed by the argument.
	 * @param conn Database connection
	 * @param st   Station instance
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 * @throws IOException            It is notified when file I/O error occurs.
	 */
	public FreeStorageStationOperator(Connection conn, Station st)
										throws ReadWriteException, IOException
	{
		super(conn, st);
		//#CM42416
		//<en> Retrieves the paring free retrieval station, according to the own station no.</en>
		wFreeRetrievalStationNumber = Route.getConnectorStation(st.getStationNumber());
	}

	//#CM42417
	// Public methods ------------------------------------------------
	//#CM42418
	/**<en>
	 * Retrieves retrieval station, the other of ther pair.
	 * @return retrieval station pairing
	 </en>*/
	public String getFreeRetrievalStationNumber()
	{
		return wFreeRetrievalStationNumber;
	}

	//#CM42419
	/**<en>
	 * This is the arrival process at the storage free station.
	 * Update process of carry data arrived .
	 * The CarryInformation instance received should always be thte dummy arrival data;
	 * Retrieves the CarryInformation isntance from the retrieval free station.
	 * If there is no data in the retrieval free station, it preserve the data as a dummy arrival data of new storage.
	 * @param ci :CarryInformation to update
	 * @param plt:Palette instance
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in the instance.
	 * @throws ReadWriteException     :Notifies if any rtouble occured in data access.
	 * @throws NotFoundException      :Notifies if there is no such data.
	 </en>*/
	public void arrival(CarryInformation ci, Palette plt) throws InvalidDefineException,
																	 ReadWriteException,
																	 NotFoundException
	{
		if (ci.getCarryKey().equals(AsrsParam.DUMMY_MCKEY))
		{
			//#CM42420
			//<en> Obtain 1 carry data using the free retrieval station no., the other of the pair, as the Key.</en>
			CarryInformation arrivalCi = getArrivalInfo(getFreeRetrievalStationNumber(), plt);
			if (arrivalCi == null)
			{
				//#CM42421
				//<en> If there is no arrival data at retrieval side, it determinese the data to be the dummy arrival</en>
				//<en> data and record as a dummy arrival data.</en>
				//<en> If there is no arrival data at the retrieval side, it determines it should be the dummy arrival</en>
				//<en> data of new storage and records the dummy arrival data.</en>
				//#CM42422
				//<en>It is judged the dummy arrival data for CMENJP3844$CM new stock, and records the dummy arrival data. </en>
			//#CM42423
			// Or, do the same processing as the initial storing at the station definition with the work display operation.
				registArrival(ci.getCarryKey(), plt);
			}
			else
			{
				CarryInformationAlterKey cakey = new CarryInformationAlterKey();
				cakey.setCarryKey(arrivalCi.getCarryKey());
				//#CM42424
				//<en> Modify the carry status to 'start'.</en>
				cakey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
				//#CM42425
				//<en> Selecting this station for sending station no.</en>
				cakey.updateSourceStationNumber(getStationNumber());

				CarryInformationHandler cinfoHandl = new CarryInformationHandler(wConn);
				cinfoHandl.modify(cakey);

				//#CM42426
				//<en> If the arrival data exists at retrieval side, it records as an arrival data of own station.</en>
				//<en> If there is the arrival data at retrieval side, it records the data as the station's own arrival data.</en>
				registArrival(arrivalCi.getCarryKey(), plt);
			}
			//#CM42427
			//<en> Requests to submit the carry instruction.</en>
			carryRequest();
		}
		else
		{
			//#CM42428
			//<en> It returns exceptions for anything other than dummy arrival. (Only dummy arrival can be processed.)</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = getStationNumber();
			tObj[1] = ci.getCarryKey();
			//#CM42429
			//<en> 6024025=Arrival report is invalid except for dummy arrival. ST No={0} mckey={1}</en>
			RmiMsgLogClient.write(6024025, LogMessage.F_NOTICE, "FreeStorageStationOperator", tObj);
			throw (new InvalidDefineException("6024025" + wDelim + tObj[0] + wDelim + tObj[1])) ;
		}
	}

	//#CM42430
	/**<en>
	 * Processing the on-line indication update and job instruction update for this station.
	 * Following procedures are taken to update the job instruction for storage free station.
	 *   1.Modify the status of carry data to 'start'.
	 *   2.Delete the on-line indication data(<code>OperationDisplay</code>) that matches the CARRYKEY in carry data.
	 *   3.Then submits the request for carry instruction.
	 * If this called to the station with attribute of no on-line indication, it notifies InvalidDefineException.
	 * @param ci :objective CarryInformation
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in the instance.
	 * @throws ReadWriteException     :Notifies if any rtouble occured in data access.
	 * @throws NotFoundException      :Notifies if there is no such data.
	 </en>*/
	public void operationDisplayUpdate(CarryInformation ci) throws InvalidDefineException,
																   ReadWriteException,
																   NotFoundException
	{
		CarryInformationHandler   chandl = new CarryInformationHandler(wConn);
		CarryInformationAlterKey         cinfoAltkey = new CarryInformationAlterKey();
		OperationDisplayHandler   ohandl = new OperationDisplayHandler(wConn);
		OperationDisplaySearchKey odskey   = new OperationDisplaySearchKey();

		//#CM42431
		//<en> This method is only valid for stations which operates the on-line indication.</en>
		if (getOperationDisplay() == Station.OPERATIONINSTRUCTION)
		{
			switch (ci.getCarryKind())
			{
				//#CM42432
				//<en> If the transport section says 'storage' or 'direct travel', modify the carry data to 'start' and </en>
				//<en> delete on-line indication data.</en>
				case CarryInformation.CARRYKIND_STORAGE:
				case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
					//#CM42433
					//<en> alter the status of carry data to 'start'</en>
					cinfoAltkey.setCarryKey(ci.getCarryKey());
					cinfoAltkey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
					chandl.modify(cinfoAltkey);

					//#CM42434
					//<en> Deleting the on-line indicaiton data</en>
					odskey.setCarryKey(ci.getCarryKey());
					ohandl.drop(odskey);

					//#CM42435
					//<en> Requests to submit the carry instruction.</en>
					carryRequest();
					break;

				default:
					//#CM42436
					//<en> Any status other than above are the work types unavailable for processing; </en>
					//#CM42437
					//<en> it therefore throws exception.</en>
					throw new InvalidDefineException("");
			}
		}
		else
		{
			//#CM42438
			//<en> Also throws exception if the station which does not operate the on-lin indication called this metho.d</en>
			//#CM42439
			//<en>Slow when this method is called for station without CMENJP3859$CM work display.</en>
			throw new InvalidDefineException("");
		}
	}

	//#CM42440
	// Package methods -----------------------------------------------

	//#CM42441
	// Protected methods ---------------------------------------------

	//#CM42442
	// Private methods -----------------------------------------------
	//#CM42443
	/**<en>
	 * Search and get arrival data. Return just one instance which has the same sending station number as this
	 * specified station number given, and which also contains the oldest arrival date of applicable data.
	 * @param conn :
	 * @param  stnum :station number to be searched
	 * @return       :CarryInformation instance searched
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.
	 </en>*/
	private CarryInformation getArrivalInfo(String stnum, Palette plt) throws ReadWriteException
	{
		CarryInformation[] cinfos = null;
		CarryInformationSearchKey ciKey = new CarryInformationSearchKey();
		//#CM42444
		// Retrieval condition set
		//#CM42445
		// Transportation former station
		ciKey.setSourceStationNumber(stnum);
		//#CM42446
		// State of transportation: Arrival
		ciKey.setCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL);

		// Acquire PALETTEID and add it to the search condition if BCDATA is set.
		if (!StringUtil.isBlank(plt.getBcData()))
		{
			Palette[] pal = null;
			PaletteSearchKey palKey = new PaletteSearchKey();

			// BCDATA is set in the search condition of the PALETTE table
			palKey.setBcData(plt.getBcData());

			PaletteHandler palh = new PaletteHandler(wConn);
			if (palh.count(palKey) > 0)
			{
				pal = (Palette[]) palh.find(palKey);
			}

			if (pal != null)
			{
				// Add it to the search condition if you can acquire PALETTEID.
				// Palette ID
				ciKey.setPaletteId(pal[0].getPaletteId());
			}
			else
			{
				// Treat as an initial storing when the retrieval result is 0
				return null;
			}
		}

		//#CM42447
		// The retrieval order set
		//#CM42448
		// Arrival date
		ciKey.setArrivalDateOrder(1, true);

		CarryInformationHandler cih = new CarryInformationHandler(wConn);
		if (cih.count(ciKey) > 0)
		{
			cinfos = (CarryInformation[]) cih.find(ciKey);
		}

		//#CM42449
		// It is good for the arrival data by one instance.
		if (cinfos != null)
		{
			return cinfos[0];
		}
		else
		{
			return null;
		}
	}
}
//#CM42450
//end of class

