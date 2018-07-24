// $Id: AisleSelector.java,v 1.2 2006/10/26 08:40:28 suresh Exp $
package jp.co.daifuku.wms.asrs.location;

//#CM42180
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.wms.asrs.dbhandler.DoubleDeepShelfHandler;
import jp.co.daifuku.wms.asrs.dbhandler.ASShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseAlterKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.text.StringUtil;

//#CM42181
/**<en>
 * This class conducts search for aisle.
 * This class is used to obtain the list of aisle searchable for empty location.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/03/16</TD><TD>INOUE</TD><TD><code>getAisleStationNumbers</code>Does not throw exception.even if it does not aisle station in warehouse</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $
 * @author  $Author: suresh $
 </en>*/
public class AisleSelector extends Object
{

	//#CM42182
	// Class fields --------------------------------------------------

	//#CM42183
	// Class variables -----------------------------------------------

	//#CM42184
	/**<en>
	 * Connection to connect with database
	 </en>*/
	protected Connection wConn = null;

	//#CM42185
	/**<en>
	 * Information of warehouse the aisle belongs to
	 </en>*/
	protected WareHouse wWareHouse = null;

	//#CM42186
	/**<en>
	 * Sending station
	 </en>*/
	protected Station wFromStation = null;

	//#CM42187
	/**<en>
	 * It preserves the list of aisle stations subject to search.
	 * Stations are listed in order of searched aisles.
	 </en>*/
	protected Aisle[] wAisles = null;

	//#CM42188
	/**<en>
	 * Class for route check
	 </en>*/
	protected RouteController wRouteController = null;

	//#CM42189
	/**<en>
	 * Index of aisle subject to search
	 </en>*/
	private int wAisleIndex = -1;

	//#CM42190
	/**<en>
	 * Used in LogWrite when Exception occured.
	 </en>*/
	protected StringWriter wSW = new StringWriter();

	//#CM42191
	/**<en>
	 * Used in LogWrite when Exception occured.
	 </en>*/
	protected PrintWriter  wPW = new PrintWriter(wSW);

	//#CM42192
	/**<en>
	 * Delimiter
	 * delimiter of parameter in MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM42193
	// Class method --------------------------------------------------
	//#CM42194
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:40:28 $") ;
	}

	//#CM42195
	// Constructors --------------------------------------------------
	//#CM42196
	/**<en>
	 * Generates the instance according to the parameter, the connection instance to connect with database, 
	 * instance of warehouse and instance of sending station.
	 * @param conn :Connection with database
	 * @param wh :instance of warehouse subject to empty location search
	 * @param st  :instance of sending station
 	 * @throws ReadWriteException    : Notifies if error occured when accessing database.
	 * @throws InvalidDefineException: Notifies if inconsistency occured in definition information.
	 * @throws NotFoundException     : Notifies if there is no such data.
	 * @throws SQLException          : Notifies if database error occurrs .
	 </en>*/
	public AisleSelector(Connection conn, WareHouse wh, Station st) throws
													ReadWriteException, InvalidDefineException,
													NotFoundException, SQLException
	{
		wConn = conn;
		wFromStation = st;
		wWareHouse = wh;
		wRouteController = new RouteController(conn);

		if (st.getWHStationNumber().equals(wh.getStationNumber()) == false)
		{
			//#CM42197
			// Because WareHouse of the station is not corresponding, the aisle decision from the specified station to the warehouse is improper.
			Object[] tObj = new Object[2] ;
			tObj[0] = st.getStationNumber();
			tObj[1] = wh.getStationNumber();
			RmiMsgLogClient.write(6026079, LogMessage.F_ERROR, this.getClass().getName(), tObj);
			throw new InvalidDefineException("6026079" + wDelim + tObj[0] + wDelim + tObj[1]);
		}

		//#CM42198
		//<en> Generates a list of aisle stations.</en>
		createAisleInformations();
	}

	
	//#CM42199
	// Public methods ------------------------------------------------
	//#CM42200
	/**<en>
	 * Determines the aisles subject to search. It is necessary to call this method and determine the aisle
	 * subject to search prior to calling getBank() method.
	 * @return Returns true if searchable bank exists, or false if all banks of all aisle were searched.
 	 * @throws ReadWriteException     : Notifies if error occured when accessing database.
	 * @throws InvalidDefineException : Notifies if inconsistency occured in the information of definition.
	 * @throws NotFoundException      : Notifies if there is no such data.
	 * @throws SQLException           : Notifies if database error occurrs .
	 </en>*/
	public boolean next() throws ReadWriteException, InvalidDefineException,
									NotFoundException, SQLException
	{
		//#CM42201
		//<en> Check the inventory checking flag.</en>
		wAisleIndex++;
		for ( ; wAisleIndex < wAisles.length ; wAisleIndex++)
		{
			//#CM42202
			//<en> Route check will not be done if the sending station and receiving station are the same station.</en>
			if (!wFromStation.getStationNumber().equals(wAisles[wAisleIndex].getStationNumber()))
			{
				if (!wRouteController.routeDetermin(wFromStation, wAisles[wAisleIndex]))
				{
					//#CM42203
					//<en> Carry route unavailable</en>
					//#CM42204
					//<en> 6022013=There is no available transfer route.</en>
					Object[] tObj = new Object[2] ;
					tObj[0] = wFromStation;
					tObj[1] = wAisles[wAisleIndex].getStationNumber();
					RmiMsgLogClient.write(6022013, LogMessage.F_DEBUG, "AisleSelector", tObj);
					continue;
				}
			}

			return true;
		}
		
		//#CM42205
		//<en> Searched through all aisles.</en>
		return false;
	}

	//#CM42206
	/**<en>
	 * Store in memory the aisle station no. currently subject to search as a station of end-use in 
	 * warehouse information.
	 * This is used when location is determined in empty location search. This station will remain the
	 * last of aisle search order until the next empty location search.
	 * @throws ReadWriteException :Notifies if error occured when accessing database.
	 </en>*/
	public void determin() throws ReadWriteException
	{
		try
		{
			if ((wAisleIndex >= 0) && (wAisleIndex <= wAisles.length))
			{
				wWareHouse.setLastUsedStationNumber(wAisles[wAisleIndex].getStationNumber());
				WareHouseAlterKey wKey = new WareHouseAlterKey();
				WareHouseHandler whandle = new WareHouseHandler(wConn);
				wKey.setStationNumber(wWareHouse.getStationNumber());
				wKey.updateLastUsedStationNumber(wAisles[wAisleIndex].getStationNumber());
				whandle.modify(wKey);
			}
		}
		catch (InvalidDefineException ie)
		{
			throw new ReadWriteException(ie.getMessage());
		}
		catch (NotFoundException ne)
		{
			throw new ReadWriteException(ne.getMessage());
		}
	}
	
	
	//#CM42207
	/**<en>
	 * Conducts empty location search within the specified search zone.
	 * Location search will be done with the aisle this instance currently indicates.
	 * If there is no empty location, it returns null.
	 * @param tZone :zone subject to the empty location search
	 * @return :Shelf instance searched
	 * @throws ReadWriteException :Notifies if error occured when accessing database.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 </en>*/
	public Shelf findShelf(Zone[] tZone) throws ReadWriteException, InvalidDefineException
	{
		//#CM42208
		// For double deep
		if (wAisles[wAisleIndex].getDoubleDeepKind() == Aisle.DOUBLE_DEEP)
		{
			DoubleDeepShelfHandler ddsHandle = new DoubleDeepShelfHandler(wConn);
			//#CM42209
			// Retrieve an empty shelf specifying the aisle and the zone shown now.
			return ddsHandle.findEmptyShelfForDoubleDeep(wAisles[wAisleIndex], tZone);
		}
		else
		{
			ShelfOperator sHandle = new ShelfOperator(wConn);
			//#CM42210
			// Retrieve an empty shelf specifying the aisle and the zone shown now.
			return sHandle.findEmptyShelf(wAisles[wAisleIndex], tZone);			
		}
	}
	
	//#CM42211
	/**<en>
	 * Conducts empty palette search within the specified search zone, according to the direction of search.
	 * Palette search will be done with the aisle this instance currently indicates.
	 * If there is no empty location, it returns null.
	 * @param  tZone :zone subject to empty palette search
	 * @return :Palette instance searched
	 * @throws ReadWriteException :Notifies if error occured when accessing database.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 </en>*/
	public Shelf findPalette(Zone[] tZone) throws ReadWriteException, InvalidDefineException
	{
		//#CM42212
		// For double deep
		if (wAisles[wAisleIndex].getDoubleDeepKind() == Aisle.DOUBLE_DEEP)
		{
			return null;
		}
		else
		{
			ASShelfHandler sHandle = new ASShelfHandler(wConn);
			//#CM42213
			// Retrieve an empty palette specifying the aisle and the zone shown now.
			return sHandle.findEmptyPalette(wAisles[wAisleIndex], tZone);
		}
	}
		
	//#CM42214
	/**<en>
	 * Conducts empty location search within the specified search zone.
	 * Location search will be done with the aisle this instance currently indicates.
	 * If there is no empty location, it returns null.
	 * @param  tZone :zone subject to empty location search
	 * @return :Palette instance searched
	 * @throws ReadWriteException :Notifies if error occured when accessing database.
	 * @throws InvalidDefineException :Notifies if there are any inconsistency in update conditions of tables.
	 </en>*/
	public Shelf findRackToRack(Zone[] tZone) throws ReadWriteException, InvalidDefineException
	{
		//#CM42215
		// For double deep
		if (wAisles[wAisleIndex].getDoubleDeepKind() == Aisle.DOUBLE_DEEP)
		{
			DoubleDeepShelfHandler ddsHandle = new DoubleDeepShelfHandler(wConn);
			//#CM42216
			// Retrieve an empty palette specifying the aisle and the zone shown now.
			return ddsHandle.findEmptyShelfForRackToRack(wAisles[wAisleIndex], tZone);
		}
		else
		{
			ShelfOperator sHandle = new ShelfOperator(wConn);
			//#CM42217
			// Retrieve an empty palette specifying the aisle and the zone shown now.
			return sHandle.findEmptyShelf(wAisles[wAisleIndex], tZone);		
		}
	}

	//#CM42218
	// Package methods -----------------------------------------------

	//#CM42219
	// Protected methods ---------------------------------------------
	//#CM42220
	/**<en>
	 * Generates a list of aisle station no. based on the specified sending station.
	 * If the definition of the sending station includes the aisle station information, that aisle station no. will be used.
	 * If the sending station is not defined for aisle station, a list of aisle stations will b retireved based on the warehouse
	 * station no.
	 * Aisle station no. will be lined in order of aisle station no. sequence. Except if the station of end-use has been set
	 * in the warehouse information, which is preserved by sending station, that station will be put in the end of the list. 
	 * This order of aisle station no. will be used as an order if empty location search.
	 * Also the list does not include the aisle stations which the route from the sending station is found unavailable.
	 * Example
	 *   Order of aisle station no. when station of end-use is unset in warehouse information:
	 *     9001
	 *     9002
	 *     9003
	 *   Order of aisle station no. when 9001 has been set as the station of end-use in warehouse information:
	 *     9002
	 *     9003
	 *     9001
	 * @throws ReadWriteException     : Notifies if error occured when accessing database.
	 * @throws InvalidDefineException : Notifies if there is no definition of aisle station for st at all.
	 * @throws NotFoundException      : Notifies if there is no such data.
	 * @throws SQLException           : Notifies if database error occurrs .
	 </en>*/
	protected void createAisleInformations() throws ReadWriteException, InvalidDefineException,
														NotFoundException, SQLException
	{
		//#CM42221
		//<en> Branching the process according to whether/not the setting of aisle station is done in the specified station data.</en>
		if (!StringUtil.isBlank(wFromStation.getAisleStationNumber()))
		{
			//#CM42222
			//<en> If the definition of aisle station no. is set:</en>
			//#CM42223
			//<en> Check to see if it is the instance of aisle class, then store in the list of aisle search.</en>
			Station st = StationFactory.makeStation(wConn, wFromStation.getAisleStationNumber());
			if (st instanceof Aisle)
			{
				wAisles = new Aisle[1];
				wAisles[0] = (Aisle)st;
			}
			else
			{
				//#CM42224
				//<en> Exception if the definition is not the instance of Aisle class.</en>
				//#CM42225
				//<en> 6026049=TAisle is not determined. AisleNo.={0}</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = wFromStation.getAisleStationNumber();
				RmiMsgLogClient.write(6026049, LogMessage.F_ERROR, "AisleSelector", tObj);
				throw new InvalidDefineException("6026049" + wDelim + tObj[0]);
			}
		}
		else
		{
			//#CM42226
			//<en> If aisle station is undefined, retrieve the list of aisle station according to the warehouse information.</en>
			AisleSearchKey akey = new AisleSearchKey();
			AisleHandler ahandl = new AisleHandler(wConn);
			akey.setWHStationNumber(wFromStation.getWHStationNumber());
			akey.setAisleNumberOrder(1, true);
			Aisle[] wksts = (Aisle[])ahandl.find(akey);
			if (wksts.length == 0)
			{
				//#CM42227
				//<en> Exception if there is no definition for aisle station at all.</en>
				//#CM42228
				//<en> 6026049=TAisle is not determined. AisleNo.={0}</en>
				Object[] tObj = new Object[1] ;
				tObj[0] = wFromStation.getStationNumber();
				RmiMsgLogClient.write(6026049, LogMessage.F_ERROR, "AisleSelector", tObj);
				throw new InvalidDefineException("6026049" + wDelim + tObj[0]);
			}
			
			//#CM42229
			//<en> Rearrange the order of aisle station no. according to the warehouse information.</en>
			wAisles = sortWareHouseAisles((WareHouse)StationFactory.makeStation(wConn, wFromStation.getWHStationNumber()), wksts);
		}
	}

	//#CM42230
	/**<en>
	 * According to the station no. of end-use in specified warehouse information, rearrange the order of aisle station no.
	 * @param wh :warehouse information
	 * @param Aislests :list of aisle station no.
	 * @return aisle station no. in order rearranged
	 </en>*/
	protected Aisle[] sortWareHouseAisles(WareHouse wh, Aisle[] Aislests)
	{
		if (StringUtil.isBlank(wh.getLastUsedStationNumber()))
		{
			return (Aislests);
		}
		else
		{
			//#CM42231
			//<en> Rearrange the order of aisle station no. if the station no. of end-use has been set in warehouse information.</en>
			Aisle[] newsts = new Aisle[Aislests.length];
			for (int i = 0, pt = 0 ; i < newsts.length ; i++)
			{
				//#CM42232
				//<en> If station no. match, place the following station in the beginning of array.</en>
				if (Aislests[i].getStationNumber().equals(wh.getLastUsedStationNumber()))
				{
					for (int j = i + 1 ; j < newsts.length ; j++)
					{
						newsts[pt] = Aislests[j];
						pt++;
					}
					for (int k = 0 ; k < i + 1 ; k++)
					{
						newsts[pt] = Aislests[k];
						pt++;
					}
					return newsts;
				}
			}
		}
		
		//#CM42233
		//<en> If the station of end-use in warehouse cannot be found in the aisle station list, return it as it is.</en>
		return (Aislests);
	}

	//#CM42234
	// Private methods -----------------------------------------------

}
