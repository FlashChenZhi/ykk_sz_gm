// $Id: AllocationClearance.java,v 1.2 2006/10/26 01:43:58 suresh Exp $
package jp.co.daifuku.wms.asrs.control ;

//#CM32007
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Vector;

import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.location.StationFactory;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteHandler;
import jp.co.daifuku.wms.base.dbhandler.PaletteSearchKey;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Station;

//#CM32008
/**
 * Processing the release cancel of allocations.
 * This class will be called in process of end inventory.
 * Commitmnet of DB should no be conducted in this class.
 * Commitment should be done either in the screen to call this method or within application.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/01/1</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 01:43:58 $
 * @author  $Author: suresh $
 */
public class AllocationClearance
{
	//#CM32009
	// Class fields --------------------------------------------------
	//#CM32010
	/**
	 * Connection instance to connect with database
	 * Transaction control is not conducted in this class.
	 */
	protected Connection wConn ;

	//#CM32011
	// Class variables -----------------------------------------------

	//#CM32012
	// Class method --------------------------------------------------
	//#CM32013
	/**
	 * Returns the version of this class.
	 * @return Version and the date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 01:43:58 $") ;
	}

	//#CM32014
	// Constructors --------------------------------------------------
	//#CM32015
	/**
	 * @param conn :Connection to connect with database
	 */
	public AllocationClearance(Connection conn)
	{
		wConn = conn;
	}

	//#CM32016
	// Public methods ------------------------------------------------
	//#CM32017
	/**
	 * Processing the release cancel of allocations in carry data by schedule no.
	 * If it is a retrieval data, it only deletes the CarryInformation.
	 * If it is a storage, it also deletes Palette and Stock that CarryInformation gets reference to.
	 * For the return value, it returns the aisle station no. that deleted CarryInformation preserved.
	 * @param sclno  :schedule no.
	 * @return :List of aisle station no. that deleted CarryInformation preserved.
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws NotFoundException  :Notifies if data to delete cannot be found.
	 * @throws InvalidDefineException :Notifies if data inconsistency occured when updated.
	 */
	public String[] dropwithScheduleno(String sclno) throws  ReadWriteException,
											   				 NotFoundException,
															 InvalidDefineException
	{
		CarryInformationHandler cih = new CarryInformationHandler(wConn);
		CarryInformationSearchKey cisk = new CarryInformationSearchKey();
		PaletteSearchKey psKey = new PaletteSearchKey();
		PaletteHandler pHandle = new PaletteHandler(wConn);
		
		CarryCompleteOperator carryCompOpe = new CarryCompleteOperator();

		cisk.setScheduleNumber(sclno);
		cisk.setCarryKeyOrder(1, true);
		CarryInformation[] ci = (CarryInformation[])cih.find(cisk);
		if (ci.length == 0)
		{
			throw new NotFoundException();
		}

		Vector carryvec = new Vector(ci.length);
		boolean addflag;
		for(int i = 0; i < ci.length; i++)
		{
			psKey.KeyClear();
			psKey.setPaletteId(ci[i].getPaletteId());
			Palette[] palette = (Palette[])pHandle.find(psKey);
	
			addflag = true;
			//#CM32018
			//if conveyance data exist without schedule no. for the same palette, it is out of deletion target
			CarryInformationHandler chandle = new CarryInformationHandler(wConn);
			CarryInformationSearchKey ckey = new CarryInformationSearchKey();
			ckey.setPaletteId(palette[0].getPaletteId());
			CarryInformation[] chkCI = (CarryInformation[])chandle.find(ckey);
			if(chkCI.length > 1)
			{
				for(int j = 0; j < chkCI.length; j++)
				{
					if(StringUtil.isBlank(chkCI[j].getScheduleNumber()))
					{
						addflag = false;
					}
				}
			}

			//#CM32019
			//data of location movement during stock confirmation can't be deleted
			if(!StringUtil.isBlank(ci[i].getRetrievalStationNumber()) &&
			   !StringUtil.isBlank(ci[i].getSourceStationNumber()) &&
			   !ci[i].getRetrievalStationNumber().equals(ci[i].getSourceStationNumber()))
			{
				try
				{
					Station sourceSt = StationFactory.makeStation(wConn, ci[i].getSourceStationNumber());
					Station retSt = StationFactory.makeStation(wConn, ci[i].getRetrievalStationNumber());
					if (sourceSt instanceof Shelf && retSt instanceof Shelf)
					{
						addflag = false;
					}
				}
				catch(SQLException e)
				{
					throw new ReadWriteException(e.getMessage());
				}
			}

			if(addflag)
			{
				carryvec.addElement(ci[i]);
			}
		}
		CarryInformation[] carry = new CarryInformation[carryvec.size()];
		carryvec.toArray(carry);
		if(carry.length == 0)
		{
			throw new NotFoundException();
		}
		else
		{
			return carryCompOpe.releaseAllocation(wConn, carry);
		}
	}

	//#CM32020
	// Package methods -----------------------------------------------

	//#CM32021
	// Protected methods ---------------------------------------------

	//#CM32022
	// Private methods -----------------------------------------------

}
//#CM32023
//end of class
