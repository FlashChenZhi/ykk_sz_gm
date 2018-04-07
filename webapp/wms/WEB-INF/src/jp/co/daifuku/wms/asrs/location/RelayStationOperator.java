// $Id: RelayStationOperator.java,v 1.2 2006/10/26 08:40:25 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42558
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
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;

//#CM42559
/**<en>
 * This class of station defines the behaviour of the relay station.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2005/11/01</TD><TD>Y.Okamura</TD><TD>For eWareNavi</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:40:25 $
 * @author  $Author: suresh $
 </en>*/
public class RelayStationOperator extends StationOperator
{
	//#CM42560
	// Class fields --------------------------------------------------

	//#CM42561
	// Class variables -----------------------------------------------
	
	//#CM42562
	// Class method --------------------------------------------------
	//#CM42563
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:40:25 $") ;
	}

	//#CM42564
	// Constructors --------------------------------------------------
	//#CM42565
	/**<en>
	 * Create a new instance of <code>Station</code>. If the instance which already has the 
	 * defined stations is needed, please use <code>StationFactory</code> class.
	 * @param  conn     :Connection with database
	 * @param  snum     :own station no. preserved
	 * @throws ReadWriteException     : Notifies if any trouble occured in data access. 
	 * @see StationFactory
	 </en>*/
	public RelayStationOperator(Connection conn, String snum) throws ReadWriteException
	{
		super(conn, snum);
	}

	//#CM42566
	/**
	 * Make the instance of new <code>RelayStationOperator</code>.
	 * Maintain the Station instance passed by the argument.
	 * @param conn Database connection
	 * @param st   Station instance
	 * @throws ReadWriteException     Notify when the trouble occurs by the data access.
	 */
	public RelayStationOperator(Connection conn, Station st) throws ReadWriteException
	{
		super(conn, st);
	}

	//#CM42567
	/**<en>
	 * This is the arrival process at the relay station.
	 * Records the carry data arrived in station.
	 * If this method is called for the station which the attribute is 'no arrival report is operated',
	 * it returns InvalidDefineException.
	 * @param ci :CarryInformation to update
	 * @param plt:Palette instance
	 * @throws InvalidDefineException :Notifies if the arrival report is not operate at the station.
	 * @throws ReadWriteException     :Notifies if any rtouble occured in data access.
	 * @throws NotFoundException      :Notifies if there is no such data. 
	 </en>*/
	public void arrival(CarryInformation ci, Palette plt) throws InvalidDefineException, 
																	 ReadWriteException,
																	 NotFoundException
	{
		//#CM42568
		//<en> Checks to see whether/not the arrival checking is done at station.</en>
		if (getStation().isArrivalCheck())
		{
			//#CM42569
			//<en> If the station operates the arrival reports, it records the arrival data.</en>
			registArrival(ci.getCarryKey(), plt);
			
			//#CM42570
			//<en> Requets to submit the carru instruction.</en>
			carryRequest();
		}
		else
		{
			//#CM42571
			//<en> If the station does not operate the arrival reports, it returns the exception.</en>
			//#CM42572
			//<en> 6024024=No arrival report for the station. Arrival is invalid. ST No={0} mckey={1}</en>
			Object[] tObj = new Object[2] ;
			tObj[0] = getStationNumber();
			tObj[1] = ci.getCarryKey();
			RmiMsgLogClient.write(6024024, LogMessage.F_NOTICE, "RelayStationOperator", tObj);
			throw (new InvalidDefineException("6024024" + wDelim + tObj[0] + wDelim + tObj[1])) ;
		}
	}

	//#CM42573
	// Package methods -----------------------------------------------

	//#CM42574
	// Protected methods ---------------------------------------------

	//#CM42575
	// Private methods -----------------------------------------------

}
//#CM42576
//end of class

