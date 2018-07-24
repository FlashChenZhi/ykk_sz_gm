// $Id: RackToRackMoveSelector.java,v 1.2 2006/10/26 08:40:25 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;

//#CM42539
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.wms.base.entity.Palette;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;

//#CM42540
/**<en>
 * This class handles the determination of location for location to location move.
 * Basically its behaviour is the same as <code>DepthShelfSelector</code>.
 * It searches the empty locations for the location to location move, according to the
 * specified zone and search direction of empty locations.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/04/18</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:40:25 $
 * @author  $Author: suresh $
 </en>*/
public class RackToRackMoveSelector extends DepthShelfSelector
{
	//#CM42541
	// Class fields --------------------------------------------------

	//#CM42542
	// Class variables -----------------------------------------------

	//#CM42543
	// Class method --------------------------------------------------
	//#CM42544
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:40:25 $") ;
	}

	//#CM42545
	// Constructors --------------------------------------------------
	//#CM42546
	/**<en>
	 * Generates instances, using the Connection instance for database as parameter.
	 * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
	 * @param conn  :<code>Connection</code> to connect with database
	 * @param zs    :ZoneSelector for zone search
	 </en>*/
	public RackToRackMoveSelector(Connection conn, ZoneSelector zs)
	{
		super(conn, zs);
	}

	//#CM42547
	// Public methods ------------------------------------------------

	//#CM42548
	// Package methods -----------------------------------------------

	//#CM42549
	// Protected methods ---------------------------------------------
	//#CM42550
	/**<en>
	 * Conducts the empty location search for the location to location move.
	 * @param wh         warehouse instance subject to empty location search
	 * @param targetZone zone subject to empty location search
	 * @param plt        pallet subject to empty location search
	 * @return :searched location
	 * @throws ReadWriteException :Notifies if exception occured when processing database.
	 * @throws ReadWriteException :Notifies if it failed to load the route definition file.
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in table.
	 </en>*/
	protected Shelf findShelf(WareHouse wh, Zone[] targetZone, Palette plt) throws ReadWriteException, InvalidDefineException
	{
		//#CM42551
		//<en> Generates the instance of sending station based on the current position of pallet.</en>
		Station st = null;
		try
		{
			st = StationFactory.makeStation(wConn, plt.getCurrentStationNumber());
			
			//#CM42552
			//<en> Retrieves a list of aisles searchable for empty locations according to the sending station.</en>
			AisleSelector asel = new AisleSelector(wConn, wh, st);
			
			//#CM42553
			//<en> Searches for the empty location according to the priority of aisles.</en>
			while (asel.next())
			{
				Shelf tShelf = asel.findRackToRack(targetZone);
				if (tShelf != null)
				{
					//#CM42554
					//<en> The station no. used or fixed in the latest search should be put to the bottom of search list</en>
					//<en> in next empty location search.</en>
					asel.determin();
					return tShelf ;
				}
			}
			
			//#CM42555
			//<en> Returns null if there are no empty locations.</en>
			return null ;
		}
		catch (NotFoundException ne)
		{
			throw new ReadWriteException(ne.getMessage());
		}
		catch (SQLException se)
		{
			throw new ReadWriteException(se.getMessage());
		}
	}

	//#CM42556
	// Private methods -----------------------------------------------
}
//#CM42557
//end of class

