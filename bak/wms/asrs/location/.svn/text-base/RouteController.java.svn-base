// $Id: RouteController.java,v 1.3 2006/10/30 06:29:10 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42678
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Hashtable;

import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.asrs.location.StationOperator;
import jp.co.daifuku.wms.asrs.location.ShelfOperator;
import jp.co.daifuku.wms.asrs.location.AisleOperator;
import jp.co.daifuku.wms.asrs.communication.as21.GroupController;
import jp.co.daifuku.wms.asrs.dbhandler.ASStationHandler;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.StringUtil;

//#CM42679
/**<en>
 * This class controls the carry route relevant information of equipment.
 * According to the current position of specified pallet and workshop, it determines stations and checks the route.
 * When the workshopis specified for sending station, ir determinese the receiving station according to the style 
 * of workshop.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2004/01/22</TD><TD>M.Inoue</TD><TD>modify:in case of loadsizecheck,not search empty shelf by schedule</TD></TR>
 * </TABLE>
 * <BR> * @version $Revision: 1.3 $, $Date: 2006/10/30 06:29:10 $
 * @author  $Author: suresh $
 </en>*/
public class RouteController extends Object
{
	//#CM42680
	// Class fields --------------------------------------------------
	//#CM42681
	/**<en>
	 * Field for the results of route check<R>
	 * <code>ACTIVE</code> : route is available
	 </en>*/
	public static final int ACTIVE = 1 ;

	//#CM42682
	/**<en>
	 * Field for the results of route check<R>
	 * <code>ACTIVE</code> : route is available
	 </en>*/
	public static final int OFFLINE = 2 ;

	//#CM42683
	/**<en>
	 * Field for the results of route check<BR>
	 * <code>FAIL</code> : unavailable due to equipment error
	 </en>*/
	public static final int FAIL = 3 ;

	//#CM42684
	/**<en>
	 * Field for the results of route check<BR>
	 * <code>NO_STATION_INTO_WORKPLACE</code> : There is no available station in specified workshop.
	 </en>*/
	public static final int NO_STATION_INTO_WORKPLACE = 4 ;

	//#CM42685
	/**<en>
	 * Field for the results of route check<BR>
	 * <code>AISLE_INVENTORYCHEK</code>: there are aisles in specified workshops right in inventory checking
	 </en>*/
	public static final int AISLE_INVENTORYCHECK = 5 ;
	
	//#CM42686
	/**<en>
	 * Field for the results of route check<BR>
	 * <code>AISLE_EMPTYLOCATIONCHECK</code>: there are aisles in specified workshops where checking the empty location.
	 </en>*/
	public static final int AISLE_EMPTYLOCATIONCHECK = 6 ;
	
	//#CM42687
	/**<en>
	 * Field for the results of route check <BR>
	 * <code>AGC_OFFLINE</code>: the AGC, which controls the station of specified workshop, is off-line.
	 </en>*/
	public static final int AGC_OFFLINE = 7;

	//#CM42688
	/**<en>
	 * Status of the route
	 * <code>UNKNOWN</code> : Route check undone
	 </en>*/
	public static final int UNKNOWN = -1 ;

	//#CM42689
	/**<en>
	 * Status of the route
	 * <code>NOTFOUND</code>: Specified route definition was not found.
	 </en>*/
	public static final int NOTFOUND = -2 ;

	//#CM42690
	/**<en>
	 * Status of the rout
	 * <code>LOCATION_EMPTY</code>: empty location cannot be found.
	 </en>*/
	public static final int LOCATION_EMPTY = -3 ;

	//#CM42691
	/**<en>
	 * Used in LogWrite when Exception occurs.
	 </en>*/
	protected StringWriter wSW = new StringWriter();

	//#CM42692
	/**<en>
	 * Used in LogWrite when Exception occurs.
	 </en>*/
	protected PrintWriter  wPW = new PrintWriter(wSW);

	//#CM42693
	/**<en>
	 * Delimiter
	 * Delimiter of parameter in MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM42694
	// Class variables -----------------------------------------------
	//#CM42695
	/**<en>
	 * Connection with databse
	 </en>*/
	private Connection wConn ;

	//#CM42696
	/**<en>
	 * Sending station
	 </en>*/
	private Station wSrcStation ;

	//#CM42697
	/**<en>
	 * Receiving station
	 </en>*/
	private Station wDestStation ;

	//#CM42698
	/**<en>
	 * Variable which preserves the results of route check between sending station and receiving station.
	 </en>*/
	private int wRouteStatus = UNKNOWN;

	//#CM42699
	/**<en>
	 * Instance of <code>Hashtable</code> which records the results of carry route check
	 * The route which has already been checked will be retrieved from this instance and be returned.
	 </en>*/
	private Hashtable wRouteHashtable ;

	//#CM42700
	/**<en>
	 * The flag which determines whether/not the carry route check should always be done.
	 * If 'true', it processes the route checks always.
	 * If 'false', it does not process the route check on carry routes already checked.
	 * Default to route check for always.
	 </en>*/
	
	private boolean wAlwaysCheck = true;
	//#CM42701
	/**<en>
	 * Flag whether/not to return 'route available' when the station belongs to off-line AGC.
	 </en>*/
	private boolean wOffLineCheck = true;

	//#CM42702
	/**<en>
	 * <CODE>GroupController</CODE> that has been searched out is preserved.
	 </en>*/
	private Hashtable hashGC =null;

	//#CM42703
	// Class method --------------------------------------------------
	//#CM42704
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.3 $,$Date: 2006/10/30 06:29:10 $") ;
	}

	//#CM42705
	// Constructors --------------------------------------------------
	//#CM42706
	/**<en>
	 * Sets <code>Connection</code> to connect with database.
	 * @param conn:Connection to set
	 </en>*/
	public RouteController (Connection conn)
	{
		wConn = conn;
		wAlwaysCheck = true;
		wRouteHashtable = new Hashtable(10);
		hashGC = new Hashtable();
	}

	//#CM42707
	/**<en>
	 * Sets <code>Connection</code> to connect with database and the flag to determine whether/not to 
	 * conduct route check evry time, then generate an instance.
	 * @param conn :Connection to set
	 * @param always true: always conducts the route check.
	 *                false: Route check will not be done for the carry route the already checked.
	 </en>*/
	public RouteController (Connection conn, boolean always)
	{
		this(conn);
		wAlwaysCheck = always;
		hashGC = new Hashtable();
	}

	//#CM42708
	// Public methods ------------------------------------------------

	//#CM42709
	/**<en>
	 * Set whether/not to return 'route available' in case the workshop has been input - regardless of 
	 * AGC status (on/offline).
	 * @param ofline :if false is set, it returns 'route available' no matter AGC is off-line or on-line.
	 </en>*/
	public void setControllerOffLineCheck(boolean offline)
	{
		wOffLineCheck = offline;
	}
	
	//#CM42710
	/**<en>
	 * Determining the carry route of storage/direct travel.
	 * According to the destined pallet and receiving station no., conducts the route check and 
	 * determines the receiving station.
	 * If workshop is selected as a receiving station, receiving station needs to be determined.
	 * @param plt       :pallet to carry
	 * @param destStnum :receiving staton no.
	 * @return :true if receiving station is determined, or false if available station cannot be found. 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if specified station no. is .
	 </en>*/
	public boolean storageDetermin(Palette plt, String destStnum) throws ReadWriteException, InvalidDefineException
	{
		Station frst = null;
		Station tost = null;
		try
		{
			frst = StationFactory.makeStation(wConn, plt.getCurrentStationNumber());
			tost = StationFactory.makeStation(wConn, destStnum);
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}

		return storageDetermin(plt, frst, tost);
	}

	//#CM42711
	/**<en>
	 * Determines the carry route for storage and direct travels dedicated for control process.
	 * It is necessary that the sending station must be sendable station.
	 * It is necessary that the receiving station must be either a warehouse or sendable station.
	 * According to the carrying pallet and the receiving station no., route check to be conducted and 
	 * the destination needs to be determined.
	 * IF the carry is destined to a warehouse, determine the location (shelf).
	 * @param plt :pallet to carry
	 * @param frst :sending station
	 * @param tost :receiving station
	 * @return :true if receiving station is determined, or 'false' if available station cannot be found. 
	 * @throws DefineException :Notifies if the specified station no. was .
	 * @throws InvalidDefineException :Notifies if the receiving station no. specified by tost is not the 
	 * sendable station.
	 * @throws InvalidDefineException :Notifies if current station that plt refers to is not the sendable station.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public boolean storageDetermin(Palette plt, Station frst, Station tost) throws ReadWriteException, InvalidDefineException
	{
		try
		{
			if( frst.isSendable() == false )
			{
				//#CM42712
				//<en> If the sender is not the sendable station, it returns excetion.</en>
				throw new InvalidDefineException("From station is not sendable Station");
			}
			
			//#CM42713
			//<en> If the receiver is a warehouse, it determines the location.</en>
			if (tost instanceof WareHouse)
			{
				Shelf location = selectShelf(plt);
				if (location != null)
				{
					//#CM42714
					//<en> Reserves the searched location for the receiving.</en>
					ShelfOperator shop = new ShelfOperator(wConn, location.getStationNumber());
					shop.alterPresence(Shelf.PRESENCE_RESERVATION);
					wSrcStation = frst;
					wDestStation = location;
					wDestStation.setAisleStationNumber(location.getParentStationNumber());
					return true;
				}
				
				//#CM42715
				//<en> If there is no empty location, and if reject station is defined, set that value as </en>
				//<en> a destination.</en>
				Station currentst = StationFactory.makeStation(wConn, plt.getCurrentStationNumber()) ;
				if (StringUtil.isBlank(currentst.getRejectStationNumber()) == false)
				{
					wSrcStation = frst;
					wDestStation = StationFactory.makeStation(wConn, currentst.getRejectStationNumber());
					return true;
				}
				else
				{
					//#CM42716
					//<en> If there is neither empty location or the definition of reject station, this </en>
					//<en> carry route is not acceptable.</en>
					wSrcStation = null;
					wDestStation = null;
					return false;
				}
			}
			else
			{
				if (tost.isSendable() == true)
				{
					//#CM42717
					//<en> If both sending station and receiving stations are the normal station or locations,</en>
					//<en> it only conducts the route check.</en>
					return routeDetermin(frst, tost);
				}
				else
				{
					//#CM42718
					//<en> If the receiving station is neither a warehouse or sendable station, it returns exception.</en>
					throw new InvalidDefineException("To station is not sendable Station");
				}
			}
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
	}

	//#CM42719
	/**<en>
	 * Processing the storing location designation required for schedule process.
	  * According to the carrying pallet and the receiving station no., checks the route and designates the 
	 * receiving station.<BR>
	 * If it is sent from workshop, it processes the station designation.
	 * @param plt  :<code>Palette</code> instance which includes the sending station
	 * @param tost :receiving station
	 * @return :true if receiving station is designated, or false if available station cannot be found. 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if specified statio no. is Invalid.
	 </en>*/
	public boolean storageDeterminSCH(Palette plt, Station tost) throws ReadWriteException, InvalidDefineException
	{
		return storageDeterminSCH(plt, tost, true);
	}

	//#CM42720
	/**<en>
	 * Porcessing te storing location designation required for schedule process.
	 * According to the carrying pallet and the receiving station no., checks the route and designates the 
	 * receiving station.<BR>
	 * If it is received by a warehouse, it designates the location.<BR>
	 * If the receiver is the shelf, checks the route between sending station and the shelf.
	 * @param plt    :<code>Palette</code> instance which contains the sending station
	 * @param tost   :receiving station
	 * @param determ :Specify 'true' if the sender is a workshop and station needs to be designated.<BR>
	 *                Specify 'false' if only the route needs to be checked.
	 * @return 'true' if receiving station is designated, or 'false' if available station cannot be found. 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if the specified station no. is invalid.
	 </en>*/
	public boolean storageDeterminSCH(Palette plt, Station tost, boolean determ) throws ReadWriteException, 
																						InvalidDefineException
	{
		try
		{
			Station frst = StationFactory.makeStation(wConn, plt.getCurrentStationNumber());

			//#CM42721
			// Check whether a transportation former station can be transmitted as instruction information. 
			if( frst.isSendable() == true )
			{
				if (tost instanceof WareHouse)
				{
					//#CM42722
					//<en> Designating location from station (empty location search)</en>
					return shelfDeterminFromStation(plt, frst, (WareHouse)tost);
				}
				else if (tost instanceof Shelf)
				{
					//#CM42723
					//<en> Designating location from station (route check)</en>
					return shelfDeterminFromStation(plt, frst, (Shelf)tost);
				}
				else
				{
					//#CM42724
					//<en> Specified storign location is invalid. StationNo={0}</en>
					//#CM42725
					//<en>It is illegal stocking to the CMENJP4064$CM specification ahead. StationNo={0}</en>
					throw new InvalidDefineException("the storing location is invalid.");
				}
			}
			else
			{
				if (frst instanceof WorkPlace)
				{
					//#CM42726
					//<en> Designating location from workshop</en>
					return shelfDeterminFromWorkPlace(plt, (WorkPlace)frst, tost, determ);
				}
				else
				{
					//#CM42727
					//<en> If the receiving station is neither a warehouse or sendable station, it returns exception.</en>
					//#CM42728
					//<en>The station for which CMENJP4066$CM was specified is a workshop in which it stocks and it can work or not a station. StationNo={0}</en>
					throw new InvalidDefineException("sending location is invalid.");
				}
			}
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
	}

	//#CM42729
	/**<en>
	 * Processing the carry route desugnation by direct travel.<BR>
	 * According to the carrying pallet and receiving station no., it checks the route and designates the storing location.<BR>
	 * If the workshop is selected for sending station or receiving station, station will be designated respectively.
	 * If the receiving station is a workshop, it updates the station of end-use based on the result of station designatioln.
	 * @param frst :sending station or workshop
	 * @param tost :receiving station or workshop
	 * @return :true if receiving station is designated, false if available station cannot be found. 
	 * @throws InvalidDefineException :Notifies if specified station no. is invalid.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public boolean directTrancefarDeterminSCH(Station frst, Station tost)
														throws ReadWriteException, InvalidDefineException
	{
		return directTrancefarDeterminSCH(frst, tost, true);
	}

	//#CM42730
	/**<en>
	 * Processing the carry route designation for direct travel.<BR>
	 * According to the carrying pallet and receiving station no., it checks the route and designates the storing location.<BR>
	 * If the workshop is selected for sending station or receiving station, station will be designated respectively.
	 * @param frst :sending station or workshop
	 * @param tost :receiving station or workshop
	 * @param determ :Set true if the receiving station is a workshop and station needs to be designated, or false if only the 
	 * route needs to be checked.
	 * @return :true if receiving station is designated, false if available station cannot be found. 
	 * @throws InvalidDefineException :Notifies if specified station no. is invalid.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public boolean directTrancefarDeterminSCH(Station frst, Station tost, boolean determ)
														throws ReadWriteException, InvalidDefineException
	{
		if( frst.isSendable() == true )
		{
			//#CM42731
			//<en> Checks the route between sending station and receiving station or the workshop.</en>
			return directTrancefarDeterminFromStation(frst, tost, determ);
		}
		else
		{
			//#CM42732
			//<en> Checks th route between sending workshop and receiving station or workshop</en>
			if (frst instanceof WorkPlace)
			{
				return directTrancefarDeterminFromWorkPlace((WorkPlace)frst, tost, determ);
			}
			else
			{
				//#CM42733
				//<en> If the receiving station is neither a warehouse or sendable station, it returns exception.</en>
				//#CM42734
				//<en>The station for which CMENJP4071$CM was specified is a workshop in which it stocks and it can work or not a station. StationNo={0}</en>
				throw new InvalidDefineException("the sending staiton is invalid.");
			}
		}
	}

	//#CM42735
	/**<en>
	 * Designates the carry route for retrieval and location to location move.
	 * According to the carrying pallet and receiving station no., it checks the route and designates the receiving station.
	 * If a workshop is selected as receiving station, it designates the recieving station according to the workshop style.
	 * If the carrying pallet is located at anywhere other than shelves, the route will not be checked because it cannot
	 * determine to which shelf it should return.
	 * @param plt  :carrying pallet
	 * @param tost :receiving station or workshop
	 * @return :true if receiving station is designated, ot false if available station cannot be found. 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if specified station no. is invalid.
	 </en>*/
	public boolean retrievalDetermin(Palette plt, Station tost) throws ReadWriteException, InvalidDefineException
	{
		return retrievalDetermin(plt, tost, true, false , false);
	}

	//#CM42736
	/**<en>
	 * Designates the carry route for retrievals and location to location moves.
	 * According to the carrying pallet and receiving station no., it checks the route and designates the receiving station.
	 * If a workshop is selected as receiving station, it designates the recieving station according to the workshop style.
	 * If the carrying pallet is located at anywhere other than shelves, the route will not be checked because it cannot
	 * determine to which shelf it should return.
	 * @param plt    :carrying pallet
	 * @param tost   :receiving station or workshop
	 * @param determ :true if a workshop has been selected as desitination and renewing the workshop information, or false
	 * if only the check will be done.
	 * @return :true if the receiving station is designated, or false if available station cannot be found 
	 * @throws ReadWriteException Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if specified staion no. is invalid.
	 </en>*/
	public boolean retrievalDetermin(Palette plt, Station tost, boolean determ) throws ReadWriteException, InvalidDefineException
	{
		return retrievalDetermin(plt, tost, determ, false, false);
	}
	
	//#CM42737
	/**<en>
	 * Designates the carry route for retrievals and location to location moves.
	 * According to the carrying pallet and receiving station no., it checks the route and designates the receiving station.
	 * If a workshop is selected as receiving station, it designates the recieving station according to the workshop style.
	 * If the carrying pallet is located at anywhere other than shelves, the route will not be checked because it cannot
	 * determine to which shelf it should return.
	 * @param plt        :carrying pallet
	 * @param tost       :receiving station or workshop
	 * @param determ     :true if a workshop has been selected as desitination and renewing the workshop information, or false
	 *                     if only the check will be done.
	 * @param piconly    :Specify true if route-checking the stations where the pick retrieval is available in workshop only,
	 *                     or specify false if unit retrieval stations shall be included. This is valid if specified destination is 
	 *                     <code>WorkPlace<code>.
	 * @param removeonly :Specify true if selecting only the stations available for transfer in workshop, or specify
	 *                     false if those station unavailable for transfer shall be included. This is valid if <code>WorkPlace<code> is specifed
	 *                     as destination.<BR>
	 * @return :true if the receiving station is designated, or false if available station cannot be found 
	 * @throws InvalidDefineException :Notifies if specified staion no. is invalid.
	 * @throws ReadWriteException Notifies if error occured in connection with database.
	 </en>*/
	public boolean retrievalDetermin(Palette plt, Station tost, boolean determ, boolean piconly, boolean removeonly)
																	throws ReadWriteException, InvalidDefineException
	{
		return retrievalDetermin(plt, tost, determ,  piconly, removeonly, false);
	}
	
	//#CM42738
	/**<en>
	 * Designates the carry route for retrievals and location to location moves.
	 * According to the carrying pallet and receiving station no., it checks the route and designates the receiving station.
	 * If a workshop is selected as receiving station, it designates the recieving station according to the workshop style.
	 * If the carrying pallet is located at anywhere other than shelves, the route will not be checked because it cannot
	 * determine to which shelf it should return.
	 * @param plt           :carrying pallet
	 * @param tost          :receiving station or workshop
	 * @param determ        :true if a workshop has been selected as desitination and renewing the workshop information, or false
	 *                        if only the check will be done.
	 * @param piconly       :Specify true if route-checking the stations where the pick retrieval is available in workshop only,
	 *                        or specify false if unit retrieval stations shall be included. This is valid if specified destination is 
	 *                        <code>WorkPlace<code>.
	 * @param removeonly    :Specify true if selecting only the stations available for transfer in workshop, or specify
	 *                        false if those station unavailable for transfer shall be included. This is valid if <code>WorkPlace<code> is specifed
	 *                        as destination.<BR>
	 * @param inventoryflag :Flag which indicates whether/not to check the stations in the inventory check procedure. 
	 *                        false: It will not include the station in inventory checking procedure in the route.<BR>
	 *                        true: The route will be designated regardless of inventory checking is in progress at stations. (Therefore the route 
	 *                        may include stations on inventory checking.) This flag is valid only when the workshop is selected.
	 * @return :true if the receiving station is designated, or false if available station cannot be found 
	 * @throws InvalidDefineException :Notifies if specified staion no. is invalid.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 </en>*/
	public boolean retrievalDetermin(Palette plt, Station tost, boolean determ, boolean piconly, boolean removeonly, boolean inventoryflag)
																	throws ReadWriteException, InvalidDefineException
	{
		Station frst  = null;
		Station curst = null;
		try
		{
			//#CM42739
			//<en> Generates station based on the current position of pallet.</en>
			curst = StationFactory.makeStation(wConn, plt.getCurrentStationNumber());
			
			//#CM42740
			//<en> If the receiving station is workshop, should process the designation of receiving station.</en>
			if (tost instanceof WorkPlace)
			{
				WorkPlace wp = (WorkPlace)tost;
				String[] stnums = wp.getWPStations();
			
				//#CM42741
				/*<en>-------------------------------------------------------- 
				 * Designation of station with stand alone type workshop 
				 *--------------------------------------------------------
				 * Station decision of aisle independent type workshop
				 *-------------------------------------*</en>
				 */
				if (wp.getWorkPlaceType() == WorkPlace.STAND_ALONE_STATIONS)
				{
					//#CM42742
					//<en> If handling a set of stand alone style stations, destined station must be the stations</en>
					//<en> where the carry can be carried out from pallet.</en>
					if (curst instanceof Shelf)
					{
						//#CM42743
						//<en> In case the current position is a shelf, select the parent station as a sender</en>
						//<en> that the shlf belongs to.</en>
						frst = StationFactory.makeStation(wConn, curst.getParentStationNumber());
					}
					else
					{
						//#CM42744
						//<en> In case the current position is a station, select the aisle station no. of the station</en>
						//<en> as sending station.</en>
						frst = StationFactory.makeStation(wConn, curst.getAisleStationNumber());
					}
					
					//#CM42745
					//<en> Number of stations which has no definition for the carry route</en>
					int notFoundCnt = 0;
					
					//#CM42746
					//<en> Repears until a normal route should be found.</en>
					for (int stcnt = 0 ; stcnt < stnums.length ; stcnt++)
					{
						//#CM42747
						//<en> Determines it is the station that carry can be carried out if the aisle station no. which </en>
						//<en> current station of pallet belongs to and the aisle no. that station in the workshop</en>
						//<en> belongs to matches. Route also will be cheked.</en>
						//#CM42748
						//<en>Make to the station which can be transported and check the route if CMENJP4084$CM aisle Station No is corresponding.</en>
					Station nextst = wp.getWPStation(wConn);
						if (nextst.getAisleStationNumber().equals(frst.getStationNumber()))
						{
							//#CM42749
							//<en> Checks if nextst is available for retrieval.</en>
							//#CM42750
							//<en> Continue it it is not available.</en>
							if (!isRetrievalStation(nextst, inventoryflag, wOffLineCheck))
							{
								if( wRouteStatus == UNKNOWN )
								{
									//#CM42751
									// When check result (wRouteStatus) is not set with false in isRetrievalStation,
									//#CM42752
									// the station check result of being possible to deliver
									//#CM42753
									//  it sets NO_STATION_INTO_WORKPLACE.
									wRouteStatus = NO_STATION_INTO_WORKPLACE;
								}
								continue;
							}
							
							if (piconly)
							{
								//#CM42754
								//<en> If it is specified that only stations available for pick retrieval to be valid,</en>
								//#CM42755
								//<en> it excludes from stations for route check those dedicated for unit retrievals.</en>
								if( nextst.isUnitOnly() )
								{
									wRouteStatus = NO_STATION_INTO_WORKPLACE;
									continue;
								}
							}
							
							if (removeonly)
							{
								//#CM42756
								//<en> If it is specified that only stations available for transfers,</en>
								//#CM42757
								//<en> it excludes from stations for route check those unavailable for transfers.</en>
								if( !nextst.isRemove() )
								{
									wRouteStatus = NO_STATION_INTO_WORKPLACE;
									continue;
								}
							}
							
							//#CM42758
							//<en> Checks routes for carrying pallet and prioritized station; </en>
							//#CM42759
							//<en> If the route is usable, it returns true then terminates the process.</en>
							if (routeDetermin(frst, nextst))
							{
								if (determ)
								{
									//#CM42760
									//Renew the final use station in the child workshop because work cannot be evenly allotted even if LastUsedStationNumber
									//#CM42761
									// of the workshop station is renewed when the route in the workshop of the entire aisle independent type is decided.
									ASStationHandler sth = new ASStationHandler(wConn);
									sth.updateLastStaion(wDestStation.getStationNumber());

									//#CM42762
									//<en> Updates the station of end-use in workshop.</en>
									StationOperator sop = new StationOperator(wConn, tost.getStationNumber());
									sop.alterLastUsedStation(wDestStation.getStationNumber());
									return true;
								}
								
								return true;
							}
						}
						else
						{
							//#CM42763
							//<en> Counts if the aisle which the station of workshop refers to differs from the aisle</en>
							//#CM42764
							//<en> of shelf where the pallet exists.</en>
							notFoundCnt ++;
						}
					}
					
					if (notFoundCnt >= stnums.length)
					{
						//#CM42765
						//<en> If any route between this specified station of workshop and the pallet does not exist at all,</en>
						//#CM42766
						//<en> it sets 'no route is defined' in status.</en>
						wRouteStatus = NOTFOUND;
					}
					
					//#CM42767
					//<en> Carry unavailable at all stations.</en>
					//#CM42768
					//<en> NO_STATION_INTO_WORKPLACE or the result of route check must have been set in wRouteStatus, </en>
					//#CM42769
					//<en> therefore, it returns as it is.</en>
					return false;
				}
				//#CM42770
				/*<en>------------------------------------------------------------ 
				 * Designation of station with aisle connected type workshop
				 *------------------------------------------------------------
				 * Station decision of aisle uniting type workshop 
				 *-------------------------------------</en>*/
				else
				{
					//#CM42771
					//<en> If handling a set of aisle connected type stations, designates the prioritized stations.</en>
					for (int i = 0 ; i < stnums.length ; i++)
					{
						//#CM42772
						//<en> Retrieves prioritized station from the workshop.</en>
						Station nextst = wp.getWPStation(wConn);
						
						//#CM42773
						//<en> Checks if nextst is available for retrieval.</en>
						//#CM42774
						//<en> Continue if it is not available.</en>
						if (!isRetrievalStation(nextst, inventoryflag, wOffLineCheck))
						{
							if( wRouteStatus == UNKNOWN )
							{
								//#CM42775
							//Renew the final use station in the child workshop because work cannot be evenly allotted even if LastUsedStationNumber
								//#CM42776
							// of the workshop station is renewed when the route in the workshop of the entire aisle independent type is decided.
								//#CM42777
							//<en> Updates the station of end-use in workshop.</en>
								wRouteStatus = NO_STATION_INTO_WORKPLACE;
							}
							continue;
						}
						
						if (piconly)
						{
							//#CM42778
							//<en> If it is specified that only station available for pick retrieval should be valid, </en>
							//#CM42779
							//<en> it excludes from stations to route-check those dedicated for unit retrievals.</en>
							if(nextst.isUnitOnly())
							{
								wRouteStatus = NO_STATION_INTO_WORKPLACE;
								continue;
							}
						}
						
						if (removeonly)
						{
							//#CM42780
							//<en> If it is specified that only station available for transfer should be valid, </en>
							//#CM42781
							//<en> it excludes from stations to route-check those unavailable for transfer.</en>
							if(!nextst.isRemove())
							{
								wRouteStatus = NO_STATION_INTO_WORKPLACE;
								continue;
							}
						}
						
						if (curst instanceof Shelf)
						{
							//#CM42782
							//<en> In case the current position is a shelf, checks the route between the shelf and </en>
							//<en> the destination in workshop.</en>
							Station st = StationFactory.makeStation(wConn, curst.getParentStationNumber());
							if (routeDetermin(st, nextst))
							{
								//#CM42783
								//<en> Updates the station of end-use in workshop.</en>
								StationOperator sop = new StationOperator(wConn, tost.getStationNumber());
								sop.alterLastUsedStation(wDestStation.getStationNumber());
								return true;
 							}
						}
						else
						{
							//#CM42784
							//<en> If the current position is a normal station, it determines that the pallet is in</en>
							//<en> carry process and checks no problem unconditionally.</en>
							wSrcStation = curst;
							wDestStation = nextst;
							
							//#CM42785
							//<en> Updates the station of end-use in workshop.</en>
							StationOperator sop = new StationOperator(wConn, tost.getStationNumber());
							sop.alterLastUsedStation(wDestStation.getStationNumber());
							return true;
						}
					}
					
					//#CM42786
					//<en> All carry route are not acceptable.</en>
					return false;
				}
			}
			else
			{
				//#CM42787
				//<en> If a normal station is generated for a destined station, it selects that station as destination.</en>
				if (curst instanceof Shelf)
				{
					//#CM42788
					//<en> Verifies the type of the destined station : either stand alone type or aisle connected type</en>
					if (StringUtil.isBlank(tost.getAisleStationNumber()))
					{
						//#CM42789
						//<en> If a shelf is the current position, route-check for the shelf and the destined station.</en>
						Station st = StationFactory.makeStation(wConn, curst.getParentStationNumber());
						return routeDetermin(st, tost);
					}
					else
					{
						//#CM42790
						//<en> If the parent station no.(aisle station) is set as destined station,</en>
						//#CM42791
						//<en> it should be stand alone type station; carry is available only for shelves that belong to that aisle.</en>
						if (tost.getAisleStationNumber().equals(curst.getParentStationNumber()))
						{
							//#CM42792
							//<en> If a shelf is the current position, route-check for the shelf and the destined station.</en>
							Station st = StationFactory.makeStation(wConn, curst.getParentStationNumber());
							return routeDetermin(st, tost);
						}
						
						//#CM42793
						//<en> If no carry route is found, set 'there is no carry route' to the status and return by false.</en>
						wRouteStatus = NOTFOUND;
						return false;
					}
				}
				else
				{
					//#CM42794
					//<en> If a normal station is the current position, it determines that pallet is in carry process</en>
					//<en> and checks no problem unconditionally.</en>
					wSrcStation = curst;
					wDestStation = tost;
					return true;
				}
			}
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
	}

	//#CM42795
	/**<en>
	 * Determines whether/not the entered station is storage ready.<BR>
	 * This method is currently called by RouteController.<BR>
	 * Please call 2 methods above (isInStation and isStorageMode) respectively for scheduler.<BR>
	 * These 2 methods are called as a message is wanted which indicates either the type differs or
	 * the mode differs.<BR
	 * @param st    :station to check
	 * @param check :flag which indicates whether/not to on-line check. It returns true if on-line check 
	 * to be done and false if no on-line check is to be done.
	 * @return :true if entered station is storage ready, or false for other cases.
	 </en>*/
	public boolean isStorageStation(Station st, boolean check)
	{
		try
		{
			//#CM42796
			//<en> On-line checking</en>
			if (check)
			{
				GroupController GC = null;
				if (hashGC.containsKey(st.getStationNumber()))
				{
					GC = (GroupController)hashGC.get(st.getStationNumber());
				}
				else
				{
					GC = GroupController.getInstance(wConn, st.getControllerNumber());
					hashGC.put(st.getStationNumber(), GC);
				}
				
				//#CM42797
				//<en> Checks the status of the system.</en>
				if (GC.getStatus() != GroupController.STATUS_ONLINE)
				{
					wRouteStatus = AGC_OFFLINE; // AGC OFFLINE
					return false;
				}
			}

			//#CM42798
			//<en> Checks the mode.</en>
			if(!st.isStorageMode())
			{
				return false;
			}
			
			//#CM42799
			//<en> Checks the type.</en>
			if(!st.isInStation())
			{
				return false;
			}
			
			//#CM42800
			//<en> Checks the suspention flag.</en>
			if (st.isSuspend())
			{
				return false;
			}

			if (StringUtil.isBlank(st.getAisleStationNumber()))
			{
				//#CM42801
				//<en> If the aisle station is not set, the station should be the aisle connected style.</en>
				//#CM42802
				//<en> Therefore it only checks the status of work station.</en>
				if (st.getStatus() != Station.STATION_OK)
				{
					//#CM42803
					//<en> Sets the AGC off-line classification.</en>
					wRouteStatus = OFFLINE ;
					return false;
				}
			}
			else
			{
				//#CM42804
				//<en> If hte aisle station is set, </en>
				//#CM42805
				//<en> checks the carry route between work station and the aisle station.</en>
				Station tst = StationFactory.makeStation(wConn, st.getAisleStationNumber());
				if (routeDetermin(st, tst) == false)
				{
					return false;
				}
			}

			AisleOperator aop = new AisleOperator();

			//#CM42806
			//<en> Checks whether/not the inventory is being checked.</en>
			if (aop.isInventoryCheck(wConn, st.getStationNumber()))
			{
				//#CM42807
				//<en> Sets the message classification for inventory check in progress</en>
				wRouteStatus = AISLE_INVENTORYCHECK;
				return false;
			}
			
			//#CM42808
			//<en> Checks whether/not the empty location is being checked.</en>
			if (aop.isEmptyLocationCheck(wConn, st.getStationNumber()))
			{
				//#CM42809
				//<en> Sets the message classification for empty check in progress</en>
				wRouteStatus = AISLE_EMPTYLOCATIONCHECK;
				return false;
			}
			return true;
		}
		catch(ReadWriteException e)
		{
			return false;
		}
		catch(InvalidDefineException e)
		{
			return false;
		}
		catch(NotFoundException e)
		{
			return false;
		}
		catch(SQLException e)
		{
			return false;
		}
	}

	//#CM42810
	/**<en>
	 * Determines if the entered station is retrieval ready.<BR>
	 * This method is currently called by RouteController.<BR>
	 * Scheduler shuold call 2 methods above (isOutStation and isRetrievalMode) respectively.<BR>
	 * These 2 methods are called by scheduler as a message is wanted which indicates either the type
	 * differs or the mode differs. <BR>
	 * @param st            :station to check
	 * @param inventoryflag :Flag which indicates whether/not to check the inventory checking status.
	 *                        It returns - true: no check to be done, false: check to see if the inventory check is being done.
	 * @param check         :Flag which indicates whrther/not to on-line check. it returns true : on-line check
	 *                        to be done, false: no on-line check is to be done.
	 * @return :returns true if enterd station is retrieval ready. or false for any other cases.
	 </en>*/

	public boolean isRetrievalStation(Station st, boolean inventoryflag, boolean check)
	{
		try
		{
			//#CM42811
			//<en> On-line checks.</en>
			if (check)
			{
				GroupController GC = null;
				if (hashGC.containsKey(st.getStationNumber()))
				{
					GC = (GroupController)hashGC.get(st.getStationNumber());
				}
				else
				{
					GC = GroupController.getInstance(wConn, st.getControllerNumber());
					hashGC.put(st.getStationNumber(), GC);
				}
				
				//#CM42812
				//<en> Checks the sytem status.</en>
				if (GC.getStatus() != GroupController.STATUS_ONLINE)
				{
					wRouteStatus = AGC_OFFLINE; // AGC OFFLINE
					return false;
				}
			}

			//#CM42813
			//<en> Checks the mode.</en>
			if(!st.isRetrievalMode())
			{
				return false;
			}
			
			//#CM42814
			//<en> Checks the type.</en>
			if(!st.isOutStation())
			{
				return false;
			}
			
			//#CM42815
			//<en> Checks the suspention flag.</en>
			if (st.isSuspend())
			{
				return false;
			}

			AisleOperator aop = new AisleOperator();

			if (!inventoryflag)
			{
				//#CM42816
				//<en> Checks whether/not the inventory check is done.</en>
				if (aop.isInventoryCheck(wConn, st.getStationNumber()))
				{
					//#CM42817
					//<en> Sets the message classification for inventory check in progress</en>
					wRouteStatus = AISLE_INVENTORYCHECK;
					return false;
				}
			}
			//#CM42818
			//<en> Checks whether/not the empty location check is done.</en>
			if (aop.isEmptyLocationCheck(wConn, st.getStationNumber()))
			{
				//#CM42819
				//<en> Sets the message classification for empty check in progress</en>
				wRouteStatus = AISLE_EMPTYLOCATIONCHECK;
				return false;
			}
			return true;
		}
		catch(ReadWriteException e)
		{
			return false;
		}
		catch(InvalidDefineException e)
		{
			return false;
		}
		catch(NotFoundException e)
		{
			return false;
		}
	}

	//#CM42820
	/**<en>
	 * Processing the carry route designation.
	 * According to the carrying apllet and destined station, it checks routes and designates the destination.
	 * If the route is usable, it registers the sending station and destined station as instance variables.
	 * Regardless of the results of route checks, it stores the status of routes.
	 * @param frst :sending station
	 * @param tost :destined station
	 * @return :'true' if the carry route is usable, or 'false' if the route is unusable.
	 * @throws ReadWriteException     :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if the definition of the class was incorrect.
	 * @throws NotFoundException   :Notifies if such class was not found.
	 * @throws SQLException :Notifies of the exceptions as they are that occured in connection with database.
	 </en>*/
	public boolean routeDetermin(Station frst, Station tost) throws
											ReadWriteException, InvalidDefineException,
											NotFoundException, SQLException
	{
		try
		{
			//#CM42821
			//<en> Definition of station for route checks</en>
			Station fromStation = null;
			Station toStation = null;

			//#CM42822
			//<en> If the sending station and the destined are both shelves, they cannot be used in route checks.</en>
			//<en> Therefore the parent station must be retrieved.</en>
			if (frst instanceof Shelf)
			{
				fromStation = StationFactory.makeStation(wConn, frst.getParentStationNumber());
			}
			else
			{
				fromStation = frst;
			}
			
			if (tost instanceof Shelf)
			{
				toStation = StationFactory.makeStation(wConn, tost.getParentStationNumber());
			}
			else
			{
				toStation = tost;
			}
			
			//#CM42823
			//<en> Generates a key to search Hashtable in order to store the results of route checks.</en>
			String routeKey = fromStation.getStationNumber() + toStation.getStationNumber();
			
			//#CM42824
			//<en> If the route has been already route-checked, it should return the result preserved.</en>
			if (wAlwaysCheck == false)
			{
				if (wRouteHashtable.containsKey(routeKey))
				{
					Object obj = wRouteHashtable.get(routeKey);
					Route saveRoute = (Route)obj;
					if (saveRoute.getStatus())
					{
						wSrcStation = frst;
						wDestStation = tost;
						wRouteStatus = ACTIVE;
						return true;
					}
					else
					{
						wSrcStation = null;
						wDestStation = null;
						switch (saveRoute.getRouteStatus())
						{
							case Route.ACTIVE:
								wRouteStatus = ACTIVE;
								break;
								
							case Route.NOT_ACTIVE_OFFLINE:
								wRouteStatus = OFFLINE;
								break;
								
							case Route.NOT_ACTIVE_FAIL:
								wRouteStatus = FAIL;
								break;
								
							case Route.UNKNOWN:
								wRouteStatus = UNKNOWN;
								break;
								
							case Route.NOTFOUND:
								wRouteStatus = NOTFOUND;
								break;
								
							default:
								throw new InvalidDefineException("Unexpected status was returned by route check. status code=");
						}
						
						return false;
					}
				}
			}
			
			//#CM42825
			//<en> If the route check has not been done, it should conduct the route-check.</en>
			Route route = Route.getInstance(wConn, fromStation, toStation);
			if (route.check())
			{
				//#CM42826
				//<en> If the route is useable, it registers the sending station and the destined station.</en>
				wSrcStation = frst;
				wDestStation = tost;
				wRouteStatus = ACTIVE;
				//#CM42827
				//<en> If the route will not always be checked, it registers the result of this.</en>
				if (wAlwaysCheck == false)
				{
					wRouteHashtable.put(routeKey, route);
				}
				
				return true;
			}
			else
			{
				//#CM42828
				//<en> If the route is unusable, set NULL for sending station and the destined station.</en>
				wSrcStation = null;
				wDestStation = null;
				switch (route.getRouteStatus())
				{
					case Route.NOT_ACTIVE_OFFLINE:
						wRouteStatus = OFFLINE;
						break;
						
					case Route.NOT_ACTIVE_FAIL:
						wRouteStatus = FAIL;
						break;
						
					case Route.UNKNOWN:
						wRouteStatus = UNKNOWN;
						break;
						
					case Route.NOTFOUND:
						wRouteStatus = NOTFOUND;
						break;
						
					default:
						throw new InvalidDefineException("Unexpected status was returned by route check. status code=");
				}
				
				//#CM42829
				//<en> If the route will not always be checked, it registers the result of this.</en>
				if (wAlwaysCheck == false)
				{
					wRouteHashtable.put(routeKey, route);
				}
				
				return false;
			}
		}
		catch (IOException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM42830
	/**<en>
	 * Returns the sending station designated in route designation process.
	 * @return :sending station
	 </en>*/
	public Station getSrcStation()
	{
		return wSrcStation;
	}

	//#CM42831
	/**<en>
	 * Returns the desitned station designated in route designation process.
	 * @return :destined station
	 </en>*/
	public Station getDestStation()
	{
		return wDestStation;
	}

	//#CM42832
	/**<en>
	 * Returns status which has been set with result of route checks.<BR>
	 * If route check has not been done, <code>UNKNOWN</code> will return.<BR>
	 * @return :result of route check.  <code>UNKNOWN</code> will return if route check has not been done.
	 </en>*/
	public int getRouteStatus()
	{
		return wRouteStatus;
	}

	//#CM42833
	/**<en>
	 * Searches the empty location in the warehouse.
	 * The empty location will be searched by generating the <code>ShelfSelector</code> class 
	 * according to the zone control type preserved by this instance.<BR>
	 * The connection required in ShelfSelector will be obtained in handler class in this instance.
	 * @param plt   :pallet subject to empty location search
	 * @return      :instance of searched shelf
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in table.
	 </en>*/
	public Shelf selectShelf(Palette plt) throws ReadWriteException, InvalidDefineException
	{
		//#CM42834
		//<en> Obtains a list of zone.</en>
		ZoneSelector zsel = getZoneSelector();
		ShelfSelector ssel = new DepthShelfSelector(wConn, zsel);

		//#CM42835
		//<en> Start the empty location search.</en>
		return selectShelf(ssel, plt);
	}

	//#CM42836
	/**<en>
	 * Searches the shelf in the warehouse.
	 * For actulasearch, it internally utilizes the ShelfSelector.
	 * @param ssel :<code>ShelfSelector</code> to search warehouse
	 * @param plt  :target pallet
	 * @return  :instance of the searched shelf
	 * @throws ReadWriteException :Notifies if exception occured when processing for database.
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in table.
	 * @see ShelfSelector
	 </en>*/
	public Shelf selectShelf(ShelfSelector ssel, Palette plt) throws ReadWriteException, InvalidDefineException
	{
		Shelf fshelf = null ;

		WareHouseSearchKey key = new WareHouseSearchKey();
		key.setStationNumber(plt.getWHStationNumber());
		WareHouseHandler whHandler = new WareHouseHandler(wConn);
		WareHouse[] wh = (WareHouse[]) whHandler.find(key);
		fshelf = ssel.select(plt, wh[0]) ;

		return fshelf ;
	}

	//#CM42837
	/**<en>
	 * Generates an instance of <code>ZoneSelector</code> class.
	 * The connection which will be required in <code>ZoneSelector</code> will be obtained by handler calss 
	 * of this instance.
	 * @return instance of <code>ZoneSelector</code> class, designated
	 </en>*/
	public ZoneSelector getZoneSelector()
	{
		//#CM42838
		//<en> Generates the instance of ZoneSelector for simultaneous use for soft zone/hard zone.</en>
		ZoneSelector zsel = new CombineZoneSelector(wConn);

		return zsel;
	}

	//#CM42839
	// Package methods -----------------------------------------------

	//#CM42840
	// Protected methods ---------------------------------------------
	//#CM42841
	/**<en>
	 * Processing the carry route check for storage.
	 * It checks the route according to the sending station and the destined station.<BR>
	 * @param plt   :<code>Palette</code> instance which containes the empty search information.
	 * @param frst  :sending station
	 * @param toshf :destined station
	 * @return : 'true' if the carry is possible between the sending station and the destined station, 
	 * or 'false' if the carry is not possible.
	 * @throws InvalidDefineException :Notifies if specified station no. is invalid. 
	 * @throws ReadWriteException  :Notifies if error occured in connection with database.
	 </en>*/
	protected boolean shelfDeterminFromStation(Palette plt, Station frst, Shelf toshf) throws ReadWriteException,
																					       InvalidDefineException
	{
		try
		{
			//#CM42842
			//<en> Verifies the type of sending station, either the stand alone type or the aisle connected type.</en>
			if (StringUtil.isBlank(frst.getAisleStationNumber()))
			{
				//#CM42843
				//<en> Carries out the route-check for sending station and the parent station of the shelf (aisle)</en>
				if (routeDetermin(frst, StationFactory.makeStation(wConn, toshf.getParentStationNumber())))
				{
					//#CM42844
					//<en> If the route is successfully checked, the destination will </en>
					//#CM42845
					//<en> be swithced to aisle; therefore it shuold reset to a shelf again.</en>
					wDestStation = toshf;
					return true;
				}
				
				return false;
			}
			else
			{
				//#CM42846
				//<en> If a parent station no.(aisle station) is set as destined station,</en>
				//<en> the station should be the stand alone type; it regards that only shelves that belong to that </en>
				//<en> aisle are available for the carry.</en>
				//#CM42847
				//<en>It is enabled the transportation only of the shelf which belongs to the aisle because of CMENJP4172$CM aisle independent station. </en>
			if (frst.getAisleStationNumber().equals(toshf.getParentStationNumber()))
				{
					//#CM42848
					//<en> If a shelf is the current position, route-checks for the parent station of the shelf (aisle)</en>
					//<en> and the sending station.</en>
					if (routeDetermin(frst, StationFactory.makeStation(wConn, toshf.getParentStationNumber())))
					{
						//#CM42849
						//<en> If the route is successfully checked, the destination will </en>
						//#CM42850
						//<en> be swithced to aisle; therefore it shuold reset to a shelf again.</en>
						wDestStation = toshf;
						return true;
					}
					
					return false;
				}
				else
				{
					//#CM42851
					//<en> If there is no relation between this station specified and the aisle,</en>
					//#CM42852
					//<en> set 'no route is defined' to the status.</en>
					wRouteStatus = NOTFOUND;
					return false;
				}
			}
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
	}

	//#CM42853
	/**<en>
	 * Processing the empty location search for storage.
	 * According to the sending station and the warehouse destined, it designates the shelf for the storage.<BR>
	 * @param plt  :instance of <code>Palette</code> which containes the information of empty location search. 
	 * @param frst :sending station
	 * @param wh   :<code>WareHouse</code> instance which indicates the warehouse to store.
	 * @return : true if the carry for sending station and storing warehouse is possible, or false if carry is not possible.
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException: Notifies if specified station no. is invalid. 
	 </en>*/
	protected boolean shelfDeterminFromStation(Palette plt, Station frst, WareHouse wh) throws ReadWriteException,
																						       InvalidDefineException
	{
		try
		{
			//#CM42854
			//<en> If the sending station operates the load size detections, it does not conduct the empty location search.</en>
			//#CM42855
			//<en> Route will be checked if the station is stand alone type.</en>
			if (frst.isLoadSizeCheck())
			{
				if (StringUtil.isBlank(frst.getAisleStationNumber()))
				{
					//#CM42856
					//<en> If the station is aisle connected type, it always checks no problem.</en>
					//#CM42857
					//<en> The destined should be the warehouse station.</en>
					wSrcStation = frst;
					wDestStation = wh;
					wRouteStatus = ACTIVE;
					return true;
				}
				else
				{
					if (routeDetermin(frst, StationFactory.makeStation(wConn, frst.getAisleStationNumber())))
					{
						//#CM42858
						//<en> THe destined should ne the warehouse station, not the aisle station.</en>
						wSrcStation = frst;
						wDestStation = wh;
						wRouteStatus = ACTIVE;
						return true;
					}
					else
					{
						//#CM42859
						//<en> If the carry route is not acceptable, it uses the determination value of routeDetermin</en>
						//<en> method for wRouteStatus.</en>
						wSrcStation = null;
						wDestStation = null;
						return false;
					}
				}
			}
			
            Shelf location = selectShelf(plt);
            if (location != null)
			{
				//#CM42860
				//<en> Reserves the searched shelf for storage.</en>
    			ShelfOperator shop = new ShelfOperator(wConn, location.getStationNumber());
				shop.alterPresence(Shelf.PRESENCE_RESERVATION);
				wSrcStation = frst;
				wDestStation = location;
				wRouteStatus = ACTIVE;
				return true;
			}
            
			//#CM42861
			//<en> If there is no empty locations, and if reject station is defined, use the value </en>
			//<en> for destination.</en>
			Station currentst = StationFactory.makeStation(wConn, plt.getCurrentStationNumber()) ;
			if (StringUtil.isBlank(currentst.getRejectStationNumber()) == false)
			{
				wSrcStation = frst;
				wDestStation = StationFactory.makeStation(wConn, currentst.getRejectStationNumber());
				wRouteStatus = ACTIVE;
				return true;
			}
			else
			{
				//#CM42862
				//<en> The carry route is not acceptable if there is either no empty location or reject station.</en>
				//#CM42863
				//<en> Sets in message that there is no empty location.</en>
				wSrcStation = null;
				wDestStation = null;
				wRouteStatus = LOCATION_EMPTY;
				return false;
			}
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new InvalidDefineException(e.getMessage());
		}
	}

	//#CM42864
	/**<en>
	 * Processing the carry route check in case a workshop is the sending station.
	 * Designates the storing location according to the sending station contained in workshop and the destined station .<BR>
	 * It returns false if the storing location could not be designates at all sending stations.
	 * @param plt    :<code>Palette</code> instance which contains the information for empty location search.
	 * @param wp     :<code>WorkPlace</code> instance which is to be the sending workshop.
	 * @param tost   :destined station
	 * @param determ :specifies true if designating station as sending station is a workshop.<BR>
	 *                :sprcifies false if only the route check will be done.
	 * @return  :true if possible to carry, or false if not possible to carry
	 * @throws ReadWriteException     :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if specified station no. is invalid. 
	 </en>*/
	protected boolean shelfDeterminFromWorkPlace(Palette plt, WorkPlace wp, Station tost, boolean determ) 
																throws ReadWriteException, InvalidDefineException
	{
		if (tost instanceof WareHouse)
		{
			for (int i = 0 ; i < wp.getWPStations().length ; i++)
			{
				Station nextst = wp.getWPStation(wConn);
				//#CM42865
				//<en> Checks if nextst is available for storage.</en>

				if (isStorageStation(nextst, wOffLineCheck))
				{
					//#CM42866
					//<en> Designating the store location by sprcific shelves.</en>
					//#CM42867
					//<en> As it is also required in emtpty search, it sets the selected station.</en>
					plt.setCurrentStationNumber(nextst.getStationNumber());
					if (shelfDeterminFromStation(plt, nextst, (WareHouse)tost))
					{
						if (determ)
						{
							//#CM42868
							//<en> Updates the station of end-use in workshop.</en>
							try
							{
								//#CM42869
								//Renew the final use station in the child workshop because work cannot be evenly allotted even if LastUsedStationNumber
								//#CM42870
								// of the workshop station is renewed when the route in the workshop of the entire aisle independent type is decided.
								ASStationHandler sth = new ASStationHandler(wConn);
								sth.updateLastStaion(nextst.getStationNumber());
								StationOperator sop = new StationOperator(wConn, wp.getStationNumber());
								sop.alterLastUsedStation(nextst.getStationNumber());
							}
							catch (NotFoundException e)
							{
								throw new InvalidDefineException(e.getMessage());
							}
						}
						
						return true;
					}
					else
					{
						//#CM42871
						//<en> Sets a message if there is no empty location.</en>
						wRouteStatus = LOCATION_EMPTY;
					}
				}
			}
			
			//#CM42872
			//<en>When it is not a station which can be stocked</en>
			if( wRouteStatus == UNKNOWN )
			{
				//#CM42873
				//Set NO_STATION_INTO_WORKPLACE when check result (wRouteStatus)
				//#CM42874
				// is not set by the transportation route check.
				wRouteStatus = NO_STATION_INTO_WORKPLACE;
			}
		}
		else if (tost instanceof Shelf)
		{
			for (int i = 0 ; i < wp.getWPStations().length ; i++)
			{
				Station nextst = wp.getWPStation(wConn);
				//#CM42875
				//<en> Checks whether/not the nextst is available for storage.</en>
				if (isStorageStation(nextst, wOffLineCheck))
				{
					//#CM42876
					//<en> Designation of store location by specific shelves.</en>
					if (shelfDeterminFromStation(plt, nextst, (Shelf)tost))
					{
						if (determ)
						{
							//#CM42877
							//<en> Updates the station of end-use in workshop.</en>
							try
							{
								//#CM42878
								//Renew the final use station in the child workshop because work cannot be evenly allotted even if LastUsedStationNumber
								//#CM42879
								// of the workshop station is renewed when the route in the workshop of the entire aisle independent type is decided.
								ASStationHandler sth = new ASStationHandler(wConn);
								sth.updateLastStaion(nextst.getStationNumber());
								StationOperator sop = new StationOperator(wConn, wp.getStationNumber());
								sop.alterLastUsedStation(nextst.getStationNumber());
							}
							catch (NotFoundException e)
							{
								throw new InvalidDefineException(e.getMessage());
							}
						}
						return true;
					}
				}
			}
			if( wRouteStatus == UNKNOWN )
			{
				//#CM42880
				//Set NO_STATION_INTO_WORKPLACE when check result (wRouteStatus)
				//#CM42881
				// is not set by the transportation route check.
				wRouteStatus = NO_STATION_INTO_WORKPLACE;
			}
		}
		else
		{
			//#CM42882
			//<en> Specified store location is invalid. StationNo={0}</en>
			//#CM42883
			//<en>It is illegal stocking to the CMENJP4198$CM specification ahead. StationNo={0}</en>
			throw new InvalidDefineException("The store location is invalid.");
		}
		
		return false;
	}

	//#CM42884
	/**<en>
	 * Processing the carry route designation for direct travel, when the sender is a station.<BR>
	 * If a workshop is selected for the destination station, the station must be designated.
	 * If the sending stationsis not a SENDABLE station, it throws InvalidDefineException.
	 * If the destination is NOT_SENDABLE station and if it is not the instance of <code>WorkPlace</code>,
	 * it throws InvalidDefineException.
	 * @param frst   :sending station
	 * @param tost   :destined station or workshop
	 * @param determ :sets true if station is designated as destination is a workshop, or false if only
	 *                 the check will be done.
	 * @return  :true if destined station has been designated, or false if no station available for carry can be found. 
	 * @throws ReadWriteException :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if specified station no. is invalid. 
	 </en>*/
	protected boolean directTrancefarDeterminFromStation(Station frst, Station tost, boolean determ) 
																throws ReadWriteException, InvalidDefineException
	{
		try
		{
			if( frst.isSendable() == false )
			{
				//#CM42885
				//<en> The sending station specified is not sendable.</en>
				//#CM42886
				//<en>A transportation former station for which CMENJP4200$CM was specified cannot be transmitted. </en>
				throw new InvalidDefineException("The sending station specified is not sendable.");
			}

			if( tost.isSendable() == true )
			{
				//#CM42887
				//<en> Checks the route from sending station to the destined station.</en>
				return routeDetermin(frst, tost);
			}
			else
			{
				if (tost instanceof WorkPlace)
				{
					//#CM42888
					//<en> Checks the route from sending station to the destined station.</en>
					WorkPlace wp = (WorkPlace)tost;
					for (int i = 0 ; i < wp.getWPStations().length ; i++)
					{
						Station st = wp.getWPStation(wConn);
						//#CM42889
						//<en> Checking the store stations or in/out stations</en>
						if (st.getStationType() == Station.STATION_TYPE_OUT
						 || st.getStationType() == Station.STATION_TYPE_INOUT)
						{
							//#CM42890
							//<en> Not acceptable of the station is NOT dedicated for trannfer.</en>
							if( st.isRemove() == true )
							{
								//#CM42891
								//<en> Processing the store location designation with specific warehouse (empty location search)</en>
								if (routeDetermin(frst, st))
								{
									if (determ)
									{
										//#CM42892
										//<en> Updates the station of end-use in workshop.</en>
										try
										{
											StationOperator sop = new StationOperator(wConn, tost.getStationNumber());
											sop.alterLastUsedStation(wDestStation.getStationNumber());
										}
										catch (NotFoundException e)
										{
											throw new InvalidDefineException(e.getMessage());
										}
										
										return true;
									}
									
									return true;
								}
							}
						}
					}
					
					//#CM42893
					//<en> The carry is not acceptable at all stations; it sets the status message and returns false.</en>
					wRouteStatus = NO_STATION_INTO_WORKPLACE;
					return false;
				}
				else
				{
					//#CM42894
					//<en> If the NOT sendable station was not a worlshop, it notifies of ezception.</en>
					//<en> The specified station is not available for storage. StationNo={0}</en>
					//#CM42895
					//<en>CMENJP4208$CM FTTB log message acquisition</en>
					//#CM42896
					//<en>The specified station is a workshop in which it stocks and it can work or not a station. StationNo={0}</en>
				throw new InvalidDefineException("the sender is invalid.");
				}
			}
		}
		catch (SQLException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
		catch (NotFoundException e)
		{
			throw new ReadWriteException(e.getMessage());
		}
	}

	//#CM42897
	/**<en>
	 * Processing the carry route designation for direct travel, when the sender is a station.<BR>
	 * This method specifies the valid station in the workshop, then carries out <code>directTrancefarDeterminFromStation</code>.
	 * It returns true if the route is successfully checked at any stations in the workshop.
	 * Or it returns false if all station of that workshop is not usable.
	 * @param wp     :<code>WorkPlace</code> instance which should be the sender workshop.
	 * @param tost   :destined station or workshop
	 * @param determ :Sets true if station is designated since a workshop is the destination, or false of only the check 
	 * will be done.
	 * @return : true if destined station was designated or false if available station for the carry cannot be found. 
	 * @throws ReadWriteException     :Notifies if error occured in connection with database.
	 * @throws InvalidDefineException :Notifies if specified station no. is invalid. 
	 </en>*/
	protected boolean directTrancefarDeterminFromWorkPlace(WorkPlace wp, Station tost, boolean determ)
																throws ReadWriteException, InvalidDefineException
	{
		boolean status = false;

		//#CM42898
		//<en> Checks the route from sending station to the destined station.</en>
		for (int i = 0 ; i < wp.getWPStations().length ; i++)
		{
			Station nextst = wp.getWPStation(wConn);
			
			//#CM42899
			//<en> Stations other than receiving station</en>
			if (!nextst.getStationNumber().equals(tost.getStationNumber()))
			{
				//#CM42900
				//<en> Checks if nextst is available for storage.</en>
				if (isStorageStation(nextst, wOffLineCheck))
				{
					//#CM42901
					//<en> Destination designation process</en>
					status = directTrancefarDeterminFromStation(nextst, tost, determ);
					if (status)
					{
						if (determ)
						{
							//#CM42902
							//<en> Updates the station of end-use in workshop.</en>
							try
							{
								StationOperator sop = new StationOperator(wConn, wp.getStationNumber());
								sop.alterLastUsedStation(nextst.getStationNumber());
							}
							catch (NotFoundException e)
							{
								throw new InvalidDefineException(e.getMessage());
							}
						}
						
						return true;
					}
				}
			}
		}
		
		//#CM42903
		//<en> Carry is not accepted at all stations; sets status message, then retuns false.</en>
		if( wRouteStatus == UNKNOWN )
		{
			//#CM42904
			// Set NO_STATION_INTO_WORKPLACE when check result (wRouteStatus)
			//#CM42905
			// is not set by the transportation route check.
			wRouteStatus = NO_STATION_INTO_WORKPLACE;
		}
		
		return false;
	}

	//#CM42906
	// Private methods -----------------------------------------------

	//#CM42907
	// Inner Class ---------------------------------------------------
}
//#CM42908
//end of class

