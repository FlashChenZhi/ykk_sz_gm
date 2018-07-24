// $Id: RetrievalStationOperator.java,v 1.2 2006/10/26 08:40:24 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42577
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.asrs.common.AsrsParam;
import jp.co.daifuku.wms.asrs.communication.as21.SystemTextTransmission;
import jp.co.daifuku.wms.asrs.control.CarryCompleteOperator;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;

//#CM42578
/**<en>
 * Defined in this clss of station is hte behaviour of retrieve dedicated station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:40:24 $
 * @author  $Author: suresh $
 </en>*/
public class RetrievalStationOperator extends StationOperator
{
	//#CM42579
	// Class fields --------------------------------------------------

	//#CM42580
	// Class variables -----------------------------------------------
	//#CM42581
	/**<en>
	 * class name
	 </en>*/
	private final String CLASS_NAME = "RetrievalStationOperator";

	//#CM42582
	// Class method --------------------------------------------------
	//#CM42583
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:40:24 $") ;
	}

	//#CM42584
	// Constructors --------------------------------------------------
	//#CM42585
	/**<en>
	 * Create a new instance of <code>Station</code>. If the instance which already has the 
	 * defined stations is needed, please use <code>StationFactory</code> class.
	 * @param  conn     :Connection with database
	 * @param  snum     :own station no. preserved
	 * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
	 * @see StationFactory
	 </en>*/
	public RetrievalStationOperator(Connection conn, String snum) throws ReadWriteException
	{
		super(conn, snum);
	}

	//#CM42586
	/**
	 * Make the instance of new <code>RetrievalStationOperator</code>.
	 * Maintain the Station instance passed by the argument.
	 * @param conn Database connection
	 * @param st   Station instance
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 */
	public RetrievalStationOperator(Connection conn, Station st) throws ReadWriteException
	{
		super(conn, st);
	}

	//#CM42587
	// Public methods ------------------------------------------------
	//#CM42588
	/**<en>
	 * This is the arrival process at the retrieve dedicated station.
	 * Update process of the carry data arrived.
	 * The station is retrieve dedicated; if this method is called for the station which does not
	 * operate the arrival reports, it returns exception.
	 * @param ci :CarryInformation to update
	 * @param plt:Palette instance
	 * @throws InvalidDefineException :Notifies if it received the data of storage, direct travel or dummy arival.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency with tranport section in 
	 * CarryInformation instance.
	 * @throws ReadWriteException     :Notifies if exception occured when processing for database.
	 * @throws NotFoundException      :Notifies if there is no such data.
	 </en>*/
	public void arrival(CarryInformation ci, Palette plt) throws InvalidDefineException, 
																	 ReadWriteException,
																	 NotFoundException
	{
		if (ci.getCarryKey().equals(AsrsParam.DUMMY_MCKEY))
		{
			//#CM42589
			//<en> Arrival of dummy pallet at the retrieve dedicated station is invalid; returns exception.</en>
			//#CM42590
			//<en> Arrival of dummy pallet at retrieval station is invalid; returns exception.</en>
			//#CM42591
			//<en> 6024022=Picking only station. Storage arrival report is invalid. ST No={0} mckey={1}</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = getStationNumber();
			tObj[1] = ci.getCarryKey();
			RmiMsgLogClient.write(6024022, LogMessage.F_NOTICE, "RetrievalStationOperator", tObj);
			throw (new InvalidDefineException("6024022" + wDelim + tObj[0] + wDelim + tObj[1])) ;
		}
		
		//#CM42592
		//<en> Branches the process according to the transport section of CarryInformation.</en>
		switch (ci.getCarryKind())
		{
			//#CM42593
			//<en> If it received storage data, returns null.</en>
			case CarryInformation.CARRYKIND_STORAGE:
				throw new InvalidDefineException("Storage Palette is invalid arrival");

			//#CM42594
			//<en> If it received direct travel data, processes the unit retrieval transfer.</en>
			case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
				//#CM42595
				//<en> Deletes the carry data (handling just as unit retrieval transfer).</en>
				CarryCompleteOperator carryOperate = new CarryCompleteOperator();
				carryOperate.setClassName(CLASS_NAME);
				carryOperate.unitRetrieval(wConn, ci, true);
				break;

			//#CM42596
			//<en> If it received the retrieval data, call the retrieval arrival process.</en>
			case CarryInformation.CARRYKIND_RETRIEVAL:
				updateArrival(ci);
				break;

				//#CM42597
				// Output the log at other division.
			default:
				//#CM42598
				//<en> 6024018=Attribute {1} value of acquired instance {0} is invalid. {1}={2}</en>
				Object[] tObj = new Object[3] ;
				tObj[0] = "CarryInfomation";
				tObj[1] = "CarryKind";
				tObj[2] = new Integer(ci.getCarryKind()) ;
				RmiMsgLogClient.write(6024018, LogMessage.F_WARN, "RetrievalStationOperator", tObj);
		}
	}

	//#CM42599
	/**<en>
	 * Processing the retrieval data for the arrival at retrieval station.
	 * It updates the carry data at retrieval dedicated station.
	 * As it is the retrieve dedicated station, delete the carry data regardless of the contents of retrieval instruction.
	 * @param ci :CarryInformation to be updated
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in definition.
	 * @throws ReadWriteException     :Notifies if exception occured when processing for database.
	 * @throws NotFoundException      :Notifies if there is no such data. 
	 </en>*/
	public void updateArrival(CarryInformation ci) throws InvalidDefineException,
																	 ReadWriteException,
																	 NotFoundException
	{
		CarryCompleteOperator carryOperate = new CarryCompleteOperator();
		carryOperate.setClassName(CLASS_NAME);
		
		if (getStation().isReStoringOperation())
		{
			//#CM42600
			//<en> Updates the unit retrieval stocks (with creation of re-storage schedule data)</en>
			carryOperate.unitRetrieval(wConn, ci, true);
		}
		else
		{
			//#CM42601
			//<en> Updates the unit retrieval stocks (with no creation of re-storage schedule data)</en>
			carryOperate.unitRetrieval(wConn, ci, false);
		}
	}

	//#CM42602
	/**<en>
	 * Updating the on-line indication and job instructions for this station.
	 * Following procedures are taken to update the job instruction for retrieval dedicated station.
	 *   1.Delete data of on-line indication(<code>OperationDisplay</code>) which matches the CarryKey of the carry data.
	 *   2.Submit the MC work completion report.
	 * If this method called and if the transport section of this carry data was anything other than 'retrieval' or
	 * 'direct travel', and if the station attribute designates no on-line indications, it notifies InvalidDefineException.
	 * @param ci :CarryInformation to be updated
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in the instance. 
	 * @throws ReadWriteException     :Notifies if any rtouble occured in data access.
	 * @throws NotFoundException      :Notifies if there is no such data. 
	 </en>*/
	public void operationDisplayUpdate(CarryInformation ci) throws InvalidDefineException, 
																   ReadWriteException,
																   NotFoundException
	{
		//#CM42603
		//<en> This method is valid only for stations which operates the on-line indication.</en>
		if (getOperationDisplay() == Station.OPERATIONINSTRUCTION)
		{
			switch (ci.getCarryKind())
			{
				//#CM42604
				//<en> Deletes the data of on-line indication.</en>
				//#CM42605
				//<en> Also submit the MC work completion report.</en>
				case CarryInformation.CARRYKIND_RETRIEVAL:
				case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
					//#CM42606
					//<en> Deletes data of on-line indication.</en>
					OperationDisplayHandler   ohandl = new OperationDisplayHandler(wConn);
					OperationDisplaySearchKey skey   = new OperationDisplaySearchKey();
					skey.setCarryKey(ci.getCarryKey());
					ohandl.drop(skey);
					
					try
					{
						//#CM42607
						//<en> Then submit the MC work completion report.</en>
						SystemTextTransmission.id45send(ci, wConn);
					}
					catch (Exception e)
					{
						throw new ReadWriteException(e.getMessage());
					}
					break;
					
				default:
					//#CM42608
					//<en> For any status other than retrieval and direct travel, it throws exceptions</en>
					//#CM42609
					//<en> as their work types are unavailable for processing.</en>
					throw new InvalidDefineException("");
			}
		}
		else
		{
			//#CM42610
			//<en> If htis method is called for the station where the on-line indicaiotns is not operated,</en>
			//#CM42611
			//<en> throw exception.</en>
			throw new InvalidDefineException("");
		}
	}

	//#CM42612
	// Package methods -----------------------------------------------

	//#CM42613
	// Protected methods ---------------------------------------------

	//#CM42614
	// Private methods -----------------------------------------------

}
//#CM42615
//end of class

