// $Id: EmptyPaletteSelector.java,v 1.2 2006/10/26 08:40:27 suresh Exp $
package jp.co.daifuku.wms.asrs.location ;
//#CM42320
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;
import java.sql.SQLException;

import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.asrs.entity.Zone;
import jp.co.daifuku.common.InvalidDefineException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.NotFoundException;

//#CM42321
/**<en>
 * This class conducts search for empty pallets.
 * This is used in closed operataion when searching for empty pallets.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:40:27 $
 * @author  $Author: suresh $
 </en>*/
public class  EmptyPaletteSelector
{
	//#CM42322
	// Class fields --------------------------------------------------
	//#CM42323
	/**<en>
	 * Connection with database
	 </en>*/
	protected Connection wConn ;
	
	//#CM42324
	// Class variables -----------------------------------------------

	//#CM42325
	// Class method --------------------------------------------------
	//#CM42326
	/**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:40:27 $") ;
	}

	//#CM42327
	// Constructors --------------------------------------------------
	//#CM42328
	/**<en>
	 * Generates instances, using the Connection instance for database as parameter.
	 * As transaction control is not internally conducted, it is necessary to commit transaction control externally.
	 * @param conn  :<code>Connection</code> to connect with database
	 </en>*/
	public EmptyPaletteSelector(Connection conn)
	{
		wConn = conn ;
	}

	//#CM42329
	// Public methods ------------------------------------------------
	//#CM42330
	/**<en>
	 * Retrieves a list of aisle searchable for empty locations from the specified storing station.
	 * Gets the empty location from those aisles.
	 * If no empty location was found, it returns null.
	 * @param wh         :warehouse instance subject to the empty pallet search
	 * @param targetZone :zone array subject to the empty pallet search
	 * @param st         :storing station
	 * @return :searched location
	 * @throws ReadWriteException :Notifies if error occured when accessing database.
	 * @throws ReadWriteException :Notifies if it failed loading the route definition file.
	 * @throws InvalidDefineException :Notifies if there are any data inconsistency in table.
	 </en>*/
	public Shelf findShelf(WareHouse wh, Zone[] targetZone, Station st) throws ReadWriteException, InvalidDefineException
	{
		try
		{
			//#CM42331
			//<en> Retrieves from the storing station a list of aisle searchable for empty location.</en>
			AisleSelector asel = new AisleSelector(wConn, wh, st);
			
			//#CM42332
			//<en> Searchese the empty location according to the prioritized aisles.</en>
			while (asel.next())
			{
				Shelf tShelf = asel.findPalette(targetZone);

				if (tShelf != null)
				{
					//#CM42333
					//<en> The aisle station no. used and/or fixed in latest search should be put at the bottom of search list in </en>
					//<en> next search.</en>
					asel.determin();
					return(tShelf) ;
				}
			}
			
			//#CM42334
			//<en> It returns null if there is no empty locations.</en>
			return(null) ;
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

	//#CM42335
	// Package methods -----------------------------------------------

	//#CM42336
	// Protected methods ---------------------------------------------

	//#CM42337
	// Private methods -----------------------------------------------

}
//#CM42338
//end of class


