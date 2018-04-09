// $Id: StorageStationOperator.java,v 1.2 2006/10/26 08:31:24 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM43138
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
import jp.co.daifuku.wms.base.dbhandler.CarryInformationAlterKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplayHandler;
import jp.co.daifuku.wms.base.dbhandler.OperationDisplaySearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;

//#CM43139
/**<en>
 * Defined in this class is the behaviour of storage dedicated station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/03/25</TD><TD>M.INOUE</TD><TD>In case of putting on the complete button, Correction made so that carrying status will be 'start'.</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:31:24 $
 * @author  $Author: suresh $
 </en>*/
public class StorageStationOperator extends StationOperator
{
	//#CM43140
	// Class fields --------------------------------------------------

	//#CM43141
	// Class variables -----------------------------------------------
	
	//#CM43142
	// Class method --------------------------------------------------
	//#CM43143
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:31:24 $") ;
	}

	//#CM43144
	// Constructors --------------------------------------------------
	//#CM43145
	/**<en>
	 * Create new isntance of <code>Station</code>. If the instance which already has the defined
	 * stations is needed, please used <code>StationFactory</code> class.
	 * @param  conn     :Connection with database
	 * @param  snum     :own station no. preserved
	 * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
	 * @see StationFactory
	 </en>*/
	public StorageStationOperator(Connection conn, String snum) throws ReadWriteException
	{
		super(conn, snum);
	}

	//#CM43146
	/**
	 * Make the instance of new < code>StorageStationOperator</code >.
	 * Maintain the Station instance passed by the argument.
	 * @param conn Database connection
	 * @param st   Station instance
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 */
	public StorageStationOperator(Connection conn, Station st) throws ReadWriteException
	{
		super(conn, st);
	}

	//#CM43147
	/**<en>
	 * This is the arrival process at the storage dedicated station.
	 * It records the carry data arrived in station.
	 * As the station is storage dedicated, It returns exception if this method is called to the station
	 * where the arrival report is not operated.
	 * @param ci :CarryInformation to update
	 * @param plt:Palette instance
	 * @throws InvalidDefineException :Notifies if the station does not operate the arrival report.
	 * @throws ReadWriteException     :Notifies if error occured in data access. 
	 * @throws NotFoundException      :Notifies if such data cannot be found.
	 </en>*/
	public void arrival(CarryInformation ci, Palette plt) throws InvalidDefineException, 
																	 ReadWriteException,
																	 NotFoundException
	{
		//#CM43148
		//<en> Checks whether/not the station handles the arrival check.</en>
		if (getStation().isArrivalCheck())
		{
			//#CM43149
			//<en> If the station handles the arrival reports, it records the arrival data.</en>
			registArrival(ci.getCarryKey(), plt);
			
			//#CM43150
			//<en> Then submits the request for carry instruction.</en>
			carryRequest();
		}
		else
		{
			//#CM43151
			//<en> If the station does not handle the arrival reports, it returns exception.</en>
			//#CM43152
			//<en> 6026032=The station status is not defined.</en>
			Object[] tObj = new Object[1] ;
			RmiMsgLogClient.write(6026032, LogMessage.F_ERROR, "StorageStation", tObj);
			throw (new InvalidDefineException("6026032")) ;
		}
	}

	//#CM43153
	/**<en>
	 * Processing the on-line indication on screen and its updates for this station.
	 * Following procedures are taken to update the job instruction for storage dedicated station.
	 *   1.Alter the status of carry data to 'start'.
	 *   2.Delete the data of on-line indication(<code>OperationDisplay</code>) that matches the CarryKey
	 *     of the carry data.
	 *   3.Then submits the request for carry instruction.
	 * If this method is called to the station with attribute of no on-line indication, it notifies
	 * InvalidDefineException.
	 * @param ci :target CarryInformation
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in instance.
	 * @throws ReadWriteException     :Notifies if error occured in data access.
	 * @throws NotFoundException      :Notifies if there is no such data.
	 </en>*/
	public void operationDisplayUpdate(CarryInformation ci) throws InvalidDefineException, 
																   ReadWriteException,
																   NotFoundException
	{
		CarryInformationHandler   chandl = new CarryInformationHandler(wConn);
		CarryInformationAlterKey         altkey = new CarryInformationAlterKey();
		OperationDisplayHandler   ohandl = new OperationDisplayHandler(wConn);
		OperationDisplaySearchKey skey   = new OperationDisplaySearchKey();

		//#CM43154
		//<en> This method is valid only with stations that operate the on-line indiciaton screens.</en>
		if (getOperationDisplay() == Station.OPERATIONINSTRUCTION)
		{
			switch (ci.getCarryKind())
			{
				//#CM43155
				//<en> If it is a storage, alter the status of carry data to 'start', then</en>
				//<en> delete the data of on-line indication.</en>
				case CarryInformation.CARRYKIND_STORAGE:
				case CarryInformation.CARRYKIND_DIRECT_TRAVEL:
					//#CM43156
					//<en> alter the status of carry data to 'start'</en>
					altkey.setCarryKey(ci.getCarryKey());
					altkey.updateCmdStatus(CarryInformation.CMDSTATUS_START);
					chandl.modify(altkey);
					
					//#CM43157
					//<en> Deleting the on-line indicaiton data</en>
					skey.setCarryKey(ci.getCarryKey());
					ohandl.drop(skey);
					
					//#CM43158
					//<en> Then submits the request for carry instruction.</en>
					carryRequest();
					break;

				default:
					//#CM43159
					//<en> Other status than above are of the work type unavailable for processing, therefore</en>
					//#CM43160
					//<en> it throws exceptions.</en>
					throw new InvalidDefineException("");
			}
		}
		else
		{
			//#CM43161
			//<en> Throws exception if this method is called to the station where the on-line indicaiton is not </en>
			//#CM43162
			//<en> in operation.</en>
			throw new InvalidDefineException("");
		}
	}

	//#CM43163
	// Package methods -----------------------------------------------

	//#CM43164
	// Protected methods ---------------------------------------------

	//#CM43165
	// Private methods -----------------------------------------------

}
//#CM43166
//end of class

