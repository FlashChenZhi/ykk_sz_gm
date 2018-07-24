// $Id: DepthShelfSelector.java,v 1.2 2006/10/26 08:40:27 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42291
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;

//#CM42292
/**<en>
 * This class searches the empty locations in the zone, from front/rear. 
 * Zone is a unit of devided area used for warehouse control according to the fixed rules.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:40:27 $
 * @author  $Author: suresh $
 </en>*/
public class DepthShelfSelector implements ShelfSelector
{
	//#CM42293
	// Class fields --------------------------------------------------

	//#CM42294
	// Class variables -----------------------------------------------
	//#CM42295
	/**<en>
	 * Connection with database
	 </en>*/
	protected Connection wConn ;

	//#CM42296
	/**<en>
	 * ZoneSelector for zone search
	 </en>*/
	protected ZoneSelector wZoneSelector ;

	//#CM42297
	// Class method --------------------------------------------------
	//#CM42298
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:40:27 $") ;
	}

	//#CM42299
	// Constructors --------------------------------------------------
	//#CM42300
	/**<en>
	 * Generates the instance according to the parameter, the connection instance to connect with database.
	 * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
	 * @param conn  <code>Connection</code> to connect with database
	 * @param zs    ZoneSelector to connect with zone
	 </en>*/
	public DepthShelfSelector(Connection conn, ZoneSelector zs)
	{
		wConn = conn ;
		wZoneSelector = zs ;
	}

	//#CM42301
	// Public methods ------------------------------------------------
	//#CM42302
	/**<en>
	 * Searches location from the specified warehouse and current position of pallet, then return.
	 * @param plt        :pallet subject to empty search
	 * @param wh         :instance of warehouse subject to empty search
	 * @return :location to search
	 * @throws ReadWriteException :Notifies if it occured in database processing.
	 * @throws ReadWriteException :Notifies if loading of route definition file failed.
	 * @throws InvalidDefineException :Notifies if there are inconsistencies in table data.
	 </en>*/
	public Shelf select(Palette plt, WareHouse wh) throws ReadWriteException, InvalidDefineException
	{
		try
		{
			Shelf tShelf ;
			WareHouse tWH = (WareHouse)wh ;

			//#CM42303
			// Zone information is acquired from the palette and the warehouse.
			Zone[] zone = wZoneSelector.select(plt, tWH) ;

			//#CM42304
			// Return it without the shelf when it is not found by the zone retrieval.
			if (zone == null)
			{
				return null;
			}
			
			//#CM42305
			// Pass the argument all zones which can be stocked because it is assumed that the zone comes by the array and retrieve the retrieval.
 			tShelf = findShelf(tWH, zone, plt) ;
			if (tShelf != null)
			{
				//#CM42306
				// Return the Shelf instance when the shelf is found.
				return (tShelf) ;
			}
			else
			{
				//#CM42307
				// Empty shelf none
				return(null) ;
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

	//#CM42308
	/**<en>
	 * Set <code>ZoneSelector</code> to use in this class.
	 * @param zs :<code>ZoneSelector</code> to set
	 </en>*/
	public void setZoneSelector(ZoneSelector zs)
	{
		wZoneSelector = zs ;
	}

	//#CM42309
	/**<en>
	 * Retrieves <code>ZoneSelector</code>.
	 * @return :<code>ZoneSelector</code> to set
	 </en>*/
	public ZoneSelector getZoneSelector()
	{
		return (wZoneSelector) ;
	}


	//#CM42310
	// Package methods -----------------------------------------------

	//#CM42311
	// Protected methods ---------------------------------------------
	//#CM42312
	/**<en>
	 * Retrieves a list of aisle searchable for empty locations from teh sendinf stations of specified pallet, 
	 * then retrieves the empty location from that aisle.
	 * It returns null if there is no empty location.
	 * @param wh         Instance of warehouse subject to empty location search
	 * @param targetZone Zone instance array subject to empty location search
	 * @param plt        Pallet instance subject to empty location search
	 * @return location to search
	 * @throws ReadWriteException :Notifies if occured during the database processing.
	 * @throws ReadWriteException :Notifies if loading of route definition file failed.
	 * @throws InvalidDefineException :Notifies if there are inconsistencies in table data.
	 </en>*/
	protected Shelf findShelf(WareHouse wh, Zone[] targetZone, Palette plt) throws
								ReadWriteException, InvalidDefineException, NotFoundException, SQLException
	{
		//#CM42313
		//<en> Generates instance of sending station based on the current position of pallet.</en>
		Station st = null;
		try
		{
			st = StationFactory.makeStation(wConn, plt.getCurrentStationNumber());
		}
		catch (NotFoundException ne)
		{
			throw new ReadWriteException(ne.getMessage());
		}
		catch (SQLException se)
		{
			throw new ReadWriteException(se.getMessage());
		}
		
		//#CM42314
		//<en> Retrieves the list of aisle searchable for hte empty location search from sending station.</en>
		AisleSelector asel = new AisleSelector(wConn, wh, st);
		
		//#CM42315
		//<en> Searches the empty location according to the prioritized order of aisles.</en>
		while (asel.next())
		{
			Shelf tShelf = asel.findShelf(targetZone);
			if (tShelf != null)
			{
				//#CM42316
				//<en> Fixing the location; place the aisle station no. used in this search must be put to the last of </en>
				//<en> search order in the nezxt empty location search.</en>
				asel.determin();
				return(tShelf) ;
			}
		}
		
		//#CM42317
		//<en> Retunrs null if there is no wmpty location.</en>
		return(null) ;
	}

	//#CM42318
	// Private methods -----------------------------------------------
}
//#CM42319
//end of class

