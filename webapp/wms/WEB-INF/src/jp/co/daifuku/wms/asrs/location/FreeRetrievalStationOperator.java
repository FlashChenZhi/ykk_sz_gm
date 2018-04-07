// $Id: FreeRetrievalStationOperator.java,v 1.2 2006/10/26 08:40:26 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42339
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.sql.Connection;
import java.util.Date;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteAlterKey;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;
import jp.co.daifuku.wms.asrs.control.Id33Process;

//#CM42340
/**<en>
 * Defined in this class of station is the behaviour of retrieval free station (retrieval side of 
 * U-shaped conveyor, etc.).
 * Defined in this class is the behaviour of 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:40:26 $
 * @author  $Author: suresh $
 </en>*/
public class FreeRetrievalStationOperator extends StationOperator
{
	//#CM42341
	// Class fields --------------------------------------------------

	//#CM42342
	// Class variables -----------------------------------------------
	//#CM42343
	/**<en>
	 * Preserves the station no. of pairing storage station.
	 </en>*/
	private String wFreeStorageStationNumber = null;

	//#CM42344
	/**<en>
	 * class name
	 </en>*/
	private final String CLASS_NAME = "FreeRetrievalStationOperator";

	//#CM42345
	// Class method --------------------------------------------------
	//#CM42346
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:40:26 $") ;
	}

	//#CM42347
	// Constructors --------------------------------------------------
	//#CM42348
	/**<en>
	 * Create a new instance of <code>Station</code>. If the instance which already has
	 * defined stations is necessary, please use <code>StationFactory</code> class.
	 * @param  conn     :Connection with database
	 * @param  snum     :own station no. preserved
	 * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
	 * @throws IOException            : Notifies if file I/O error occured.
	 * @see StationFactory
	 </en>*/
	public FreeRetrievalStationOperator(Connection conn, String snum)
										throws ReadWriteException, IOException
	{
		super(conn, snum);
		//#CM42349
		//<en> Retrieves the free storage station pairing to the own sation no.</en>
		wFreeStorageStationNumber = Route.getConnectorStationTo(snum);
	}

	//#CM42350
	/**
	 * Make the instance of new <code>FreeRetrievalStationOperator</code >.
	 * Maintain the Station instance passed by the argument.
	 * @param conn Database connection
	 * @param st   Station instance
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 * @throws IOException            It is notified when file I/O error occurs.
	 */
	public FreeRetrievalStationOperator(Connection conn, Station st)
										throws ReadWriteException, IOException
	{
		super(conn, st);
		//#CM42351
		//<en> Retrieves the free storage station pairing to the own sation no.</en>
		wFreeStorageStationNumber = Route.getConnectorStationTo(st.getStationNumber());
	}

	//#CM42352
	// Public methods ------------------------------------------------
	//#CM42353
	/**<en>
	 * Retrieves the storage station, the other of the pair.
	 * @return :the storage station of the pair
	 </en>*/
	public String getFreeStorageStationNumber()
	{
		return wFreeStorageStationNumber;
	}

	//#CM42354
	/**<en>
	 * This is the arrival process at the retrieval free station.
	 * Update process of the carry data arrived.
	 * Data will be updated according to the transport section of the received carry data.
	 * @param ci :CarryInformation to update
	 * @param plt:Palette instance
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in instance.
	 * @throws ReadWriteException     :Notifies if trouble occured in data access.
	 * @throws NotFoundException      :Notifies if there is no such data.
	 </en>*/
	public void arrival(CarryInformation ci, Palette plt) throws InvalidDefineException, 
																	 ReadWriteException,
																	 NotFoundException
	{
		if (ci.getCarryKey().equals(AsrsParam.DUMMY_MCKEY))
		{
			//#CM42355
			//<en> Arrival of dummy pallets at retrieval station is invalid; it should return exception.</en>
			//#CM42356
			//<en> 6024022=Picking only station. Storage arrival report is invalid. ST No={0} mckey={1}</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = getStationNumber();
			tObj[1] = ci.getCarryKey();
			RmiMsgLogClient.write(6024022, LogMessage.F_NOTICE, "FreeRetrievalStationOperator", tObj);
			throw (new InvalidDefineException("6024022" + wDelim + tObj[0] + wDelim + tObj[1])) ;
		}
		else
		{
			//#CM42357
			//<en> Branching the process according to the transport sections in CarryInformation.</en>
			switch (ci.getCarryKind())
			{
				//#CM42358
				//<en> Direct travel: it determines the pallet already was arrived at destination and deletes the carry data.</en>
				case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
					//#CM42359
					//<en> Deletes the carry data (handled just as unit retrieval transfer)</en>
					CarryCompleteOperator carryOperate = new CarryCompleteOperator();
					carryOperate.setClassName(CLASS_NAME);
					//#CM42360
					// Make the schedule of the re-stock data.
					carryOperate.unitRetrieval(wConn, ci, true);
					break;

				//#CM42361
				//<en> Invokes the process of arrival of the retrieval.</en>
				case CarryInformation.CARRYKIND_RETRIEVAL:
					updateArrival(ci);
					break;

				//#CM42362
				// Output the log at other division.
				default:
					//#CM42363
					//<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
					Object[] tObj = new Object[3] ;
					tObj[0] = "CarryInfomation";
					tObj[1] = "CarryKind";
					tObj[2] = new Integer(ci.getCarryKind()) ;
					RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "FreeRetrievalStationOperator", tObj);
			}
		}
	}

	//#CM42364
	/**<en>
	 * Processing the retireval data for arrival at restrieval station.
	 * It updates the carry data at retrieval free station.
	 * If it is the unit retrieval, deletes carry data and stock data.
	 * If it is a pick retrieval, records the CARRYKEY of that carry data into arrival data.
	 * @param ci :CarryInformation instance to be uodated
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in definition.
	 * @throws ReadWriteException     :Notifies if exception occured when processing for database. 
	 * @throws NotFoundException      :Notifies if there is no such data.
	 </en>*/
	public void updateArrival(CarryInformation ci) throws InvalidDefineException,
																	 ReadWriteException,
																	 NotFoundException
	{
		try
		{
			//#CM42365
			//<en> In case of operation without on-line indication, or in case only the on-line indication is operated,</en>
			//<en> the stock will be update at arrival process.</en>
			//<en> In case the on-line indication is operated, the update processing of carry data will be called by</en>
			//<en> the screen of on-line indication.</en>
			//<en> If it is for job instruction, the updates of carry data will be called by job instruction display.</en>
			//#CM42366
			//<en>The transportation data update processing is called from the work instruction screen at the CMENJP3808$CM work instruction operation. </en>
		if ((getOperationDisplay() == Station.NOT_OPERATIONDISPLAY)
			 || (getOperationDisplay() == Station.OPERATIONDISPONLY))
			{
				//#CM42367
				//<en> Processes the arrival updates.</en>
				processArrival(ci);
			}
		}
		catch (InvalidStatusException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
	}

	//#CM42368
	/**<en>
	 * Do the work display and the work instruction update processing to the station of piece.
	 * The work instruction on character Free station delivery side of piece update processing does the following work.
	 *   1.Change the transportation division of the transportation data to the stock.
	 *   2.Delete corresponding work display data (<code>OperationDisplay</code>) to CARRYKEY of the transportation data.
	 * The work display attribute of this station notifies InvalidDefineException when this method is called when there is no work display the delivery
	 * or not going directly the transportation division of the transportation data. 
	 * @param ci Object CarryInformation instance
	 * @throws InvalidDefineException It is notified when there is no adjustment in information in the instance.
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 * @throws NotFoundException      It is notified when the object data is not found.
	 </en>*/
	public void operationDisplayUpdate(CarryInformation ci) throws InvalidDefineException, 
																   ReadWriteException,
																   NotFoundException
	{
		try
		{
			CarryInformationHandler   chandl = new CarryInformationHandler(wConn);
			OperationDisplayHandler   ohandl = new OperationDisplayHandler(wConn);
			CarryInformationAlterKey         altkey = new CarryInformationAlterKey();
			OperationDisplaySearchKey skey   = new OperationDisplaySearchKey();
	
			//#CM42369
			//<en> This method is only valid for stations which handles operations with on-line indications.</en>
			if (getOperationDisplay() == Station.OPERATIONINSTRUCTION)
			{
				switch (ci.getCarryKind())
				{
					//#CM42370
					//<en> If the transport section says 'direct travel' or 'pick retrieval', modifies to 'storage' and </en>
					//#CM42371
					//<en> delete on-line indication data.</en>
					case CarryInformation.CARRYKIND_RETRIEVAL:
					case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
						//#CM42372
						//<en>	Arrival update processing.</en>
						processArrival(ci);
						
						//#CM42373
						//<en> Terminates the process as tge arraival process is done in processArrival method at</en>
						//<en> unit retrieval.</en>
						if (ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_UNIT || ci.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
						{
							break;
						}
						
						//#CM42374
						// Because the arrival report might go up on the tip of the stock station
						//#CM42375
						// In timing to which the button is pushed, update the transportation data to beginning.
						//#CM42376
						//<en> alter the status of carry data to 'start'</en>
						altkey.setCarryKey(ci.getCarryKey());
						altkey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
						altkey.updateSourceStationNumber(wFreeStorageStationNumber);
						chandl.modify(altkey);
						
						//#CM42377
						//<en> Deleting the on-line indicaiton data</en>
						skey.setCarryKey(ci.getCarryKey());
						ohandl.drop(skey);
						break;
						
					default:
						//#CM42378
						//<en> Any status other than 'retrieval' and 'direct travel' are the work type unavailable for </en>
						//#CM42379
						//<en> processing; it therefore throws exception.</en>
						throw new InvalidDefineException("");
				}
			}
			else
			{
				//#CM42380
				//<en> It also throws exception when this method is called for station which has no on-line indication.</en>
				//#CM42381
				//<en>Slow when this method is called for station without CMENJP3819$CM work display.</en>
				throw new InvalidDefineException("");
			}
		}
		catch (InvalidStatusException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
	}

	//#CM42382
	// Package methods -----------------------------------------------

	//#CM42383
	// Protected methods ---------------------------------------------

	//#CM42384
	// Private methods -----------------------------------------------
	//#CM42385
	/**<en>
	 * Update processing of carry data at arrival and the stock data.
	 * @param ci :objective CarryInformation instance
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in instance.
	 * @throws ReadWriteException     :Notifies if any trouble occured in data access.
	 * @throws NotFoundException      :Notifies if there is no such data.
	 * @throws InvalidStatusException :Notifies if invalid status has been set.
	 </en>*/
	private void processArrival(CarryInformation ci) throws InvalidDefineException,
																				ReadWriteException,
																				NotFoundException,
																				InvalidStatusException
	{
		//#CM42386
		//<en> in case of INSTRUCTION or RESPONSE_WAIT, process retrieval completion</en>
		if ((ci.getCmdStatus() == CarryInformation.CMDSTATUS_INSTRUCTION)
		 || (ci.getCmdStatus() == CarryInformation.CMDSTATUS_WAIT_RESPONSE))
		{
			if (ci.getCarryKind() != CarryInformation.CARRYKIND_DIRECT_TRAVEL)
			{
				//#CM42387
				//<en> Logging catch work complition report</en>
				//#CM42388
				//<en> 6022022=Picking completion is not processed. Forcing to complete picking. mckey={0}</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = ci.getCarryKey() ;
				RmiMsgLogClient.write(6022022, LogMessage.F_WARN, this.getClass().getName(), tObj);
				//#CM42389
				//<en> process retrieval complition for  CarryInformation</en>
				Id33Process.normalRetrievalCompletion(wConn, ci);
			}
		}
		
		//#CM42390
		// Process the unit delivery at the processing when going directly arrives.
		if (ci.getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
		{
			unitRetrieval(ci);
			return ;
		}

		//#CM42391
		//<en> Branching the process according to the retrieval instruction detail.</en>
		switch (ci.getRetrievalDetail())
		{
			//#CM42392
			//<en> Unit retrieval</en>
			case CarryInformation.RETRIEVALDETAIL_UNIT: 
				unitRetrieval(ci);
				break;

			//#CM42393
			//<en> Pick retrieval, replenishment storage and inventory checks</en>
			case CarryInformation.RETRIEVALDETAIL_PICKING: 
			case CarryInformation.RETRIEVALDETAIL_ADD_STORING: 
			case CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK:
				//#CM42394
				//<en> Update the stock quantity and switch the inventory checking flag.</en>
				CarryCompleteOperator carryOperate = new CarryCompleteOperator();
				carryOperate.setClassName(CLASS_NAME);
				carryOperate.updateStock(wConn, ci);

				//#CM42395
				// Update the arrival data to the transportation data for the stock.
				CarryInformationHandler cih = new CarryInformationHandler(wConn);
				CarryInformationAlterKey cakey = new CarryInformationAlterKey();
				cakey.setCarryKey(ci.getCarryKey());
				cakey.updateSourceStationNumber(getStationNumber());
				cakey.updateCmdStatus(CarryInformation.CMDSTATUS_ARRIVAL);
				if ((ci.getReStoringFlag() == CarryInformation.RESTORING_SAME_LOC) ||
					(ci.getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK))
				{
					//#CM42396
					// To return to the same shelf when same Mod or a delivery detailed instruction is a stock confirmation
					//#CM42397
					//<en> Selecting retrieval station no.(location no.) for receiving station no.</en>
					cakey.updateDestStationNumber(ci.getRetrievalStationNumber());
				}
				else
				{
					//#CM42398
					//<en> Selecting retrieval station no.(location no.) for receiving station no.</en>
					cakey.updateDestStationNumber(wStation.getWHStationNumber());
				}
				cakey.updateCarryKind(CarryInformation.CARRYKIND_STORAGE);
				cakey.updateArrivalDate(new Date());
				cih.modify(cakey);
				
				//#CM42399
				//<en> Switch the pallet to 'storing'.</en>
				PaletteHandler pltHandler = new PaletteHandler(wConn);
				PaletteAlterKey pltAKey = new PaletteAlterKey();
				pltAKey.setPaletteId(ci.getPaletteId());
				pltAKey.updateStatus(Palette.STORAGE_PLAN);
				pltHandler.modify(pltAKey);
				break;

			default:
				//#CM42400
				//<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
				Object[] tObj = new Object[3] ;
				tObj[0] = "CarryInfomation";
				tObj[1] = "RetrievalDetail";
				tObj[2] = new Integer(ci.getRetrievalDetail()) ;
				RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "FreeRetrievalStationOperator", tObj);
		}
	}
	
	//#CM42401
	/**
	 * Check the work of the re-stock existence of the station, and call the arrival processing of the unit delivery.
	 * 
	 * @param ci Transportation data
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 * @throws NotFoundException      It is notified when the object data is not found.
	 * @throws InvalidDefineException It is notified when there is no adjustment in information in the instance. 
	 */
	private void unitRetrieval(CarryInformation ci) throws ReadWriteException, NotFoundException, InvalidDefineException
	{
		CarryCompleteOperator carryOperate = new CarryCompleteOperator();
		carryOperate.setClassName(CLASS_NAME);

		if (getStation().isReStoringOperation())
		{
			//#CM42402
			//<en> Updates the unit retrieval stocks (with creation of scheduled re-storage data)</en>
			carryOperate.unitRetrieval(wConn, ci, true);
		}
		else
		{
			//#CM42403
			//<en> Updates the unit retrieval stocks (no creation ofscheduled re-storage data)</en>
			carryOperate.unitRetrieval(wConn, ci, false);
		}
	}
}
//#CM42404
//end of class
