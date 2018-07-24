// $Id: ShelfStatusWebUI.java,v 1.2 2006/10/30 04:05:01 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.toolmenu ;

//#CM54077
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfHandler;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolShelfSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
import jp.co.daifuku.wms.asrs.tool.location.Warehouse;
import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;

//#CM54078
/**<en>
 * This is a utility class which is used when indicating the location status in maintenance.
 * It implements the search method which is specified with specific search conditions for 
 * location status.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/05/21</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:05:01 $
 * @author  $Author: suresh $
 </en>*/
public class ShelfStatusWebUI 
{
	//#CM54079
	// Class fields --------------------------------------------------

	//#CM54080
	// Class variables -----------------------------------------------

	//#CM54081
	// Class method --------------------------------------------------
	//#CM54082
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 04:05:01 $") ;
	}

	//#CM54083
	/**<en>
	 * Search locations by using Presence as a key.<BR>
	 * For empty location search, please set Shelf.PRESENCE_EMPTY to Presence.<BR>
	 * For loaded location search, please set Shelf.PRESENCE_STORAGED for Presence.<BR>
	 * For reserved location search, please set Shelf.PRESENCE_RESERVATION to Presence.<BR>
	 * For all location search, please set 99 to Presence.
	 * @param 	conn     <code>Connection</code>
	 * @param 	whstnum  warehouse StationNo
	 * @param 	wh       <code>WareHouse</code> instance
	 * @param 	Presence location status
	 * @return Shelf[]   result of location search
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.
	 * @throws NotFoundException  :Notifies if data cannot be found as a result of search.
	 </en>*/
	public static Shelf[] findShelf(Connection conn, String whstnum, Warehouse wh, int presence) throws ReadWriteException, NotFoundException
	{
		return findShelf(conn, whstnum, wh, presence, -1);
	}

	//#CM54084
	/**<en>
	 * Search the specified bank with Presence as condition.<BR>
	 * For empty location search, please set Shelf.PRESENCE_EMPTY to Presence.<BR>
	 * For loaded location search, please set Shelf.PRESENCE_STORAGED for Presence.<BR>
	 * For reserved location search, please set Shelf.PRESENCE_RESERVATION to Presence.<BR>
	 * For all location search, please set 99 to Presence.
	 * @param 	conn     <code>Connection</code>
	 * @param 	whstnum  warehouse StationNo
	 * @param 	wh       <code>Warehouse</code> instance
	 * @param 	Presence location status
	 * @param 	nbank    specified bank
	 * @return ShelfStatusView[]   :result of location search
	 * @throws ReadWriteException :Notifies of the exceptions as they are that occured in connection with database.
	 * @throws NotFoundException  :Notifies if data cannot be found as a result of search.
	 </en>*/
	public static Shelf[] findShelf(Connection conn, String whstnum, Warehouse wh, int presence, int nbank) throws ReadWriteException, NotFoundException
	{
		//#CM54085
		//<en> "SELECT * FROM SHELF WHERE PRESENCE = presence ORDER BY NBANK ASC, NLEVEL DESC, NBAY ASC"</en>
		//<en> For ORDER BY, arrange the nBank and nBay in ascending order and nLevel in descending order. </en>
		//<en> (to be used when displaying the image of empty locations)</en>
		//<en> Example: image of Bank1 (display the Mad Lavel in header line.)</en>
		//#CM54086
		//<en>Make the ascending order and nLevel make nBank and nBay by CMENJP8680$CM ORDER BY in the descending order. (To use it when the empty shelf image is displayed. )</en>
		//#CM54087
		//<en> Example image chart Bank1(Max of Level is displayed in the first line. )</en>
	//#CM54088
	// 	1-4 2-4 3-4 4-4
		//#CM54089
		// 	1-3 2-3 3-3 4-3
		//#CM54090
		// 	1-2 2-2 3-2 4-2
		//#CM54091
		// 	1-1 2-1 3-1 4-1
		ToolShelfHandler shHandle = new ToolShelfHandler(conn);
		ToolShelfSearchKey shkey = new ToolShelfSearchKey();
		shkey.setWarehouseStationNumber(whstnum);

		//#CM54092
		//<en> Set the Presence.</en>
		if ( presence != 99 )
		{
			shkey.setPresence(presence);
		}

		//#CM54093
		//<en> Set the Bank.</en>
		if ( nbank != -1 )
		{
			shkey.setBank(nbank);
		}

		//#CM54094
		//<en> Set the sort order of NBANK.</en>
		shkey.setBankOrder (1, true);				
		//#CM54095
		//<en> Set the sort order of NLEVEL.</en>
		shkey.setLevelOrder(2, false);				
		//#CM54096
		//<en> Set the sort order of NBAY.</en>
		shkey.setBayOrder  (3, true);				

		Shelf[] sharray = (Shelf[])shHandle.find(shkey);
		return sharray;
	}

	//#CM54097
	/**<en>
	 * Return where the actual Bank is in Bank array.
	 * This method has been created since in display of location status it requires where in Bank array 
	 * the value is given while with Pull-down menu, it returns actual Bank as a value.
	 * It returns "0" if the valud cannot be found.
	 * @param orgNo :actual Bank
	 * @return      :position in Bank array
	 </en>*/
	public static String getIndexOfBankArray(String orgNo, int[] bankArray)
	{
		for(int i = 0; i < bankArray.length; i++)
		{
			if(orgNo.equals(Integer.toString(bankArray[i])))
			{
				return Integer.toString(i);
			}
		}
		return "0";
	}
	//#CM54098
	// Constructors --------------------------------------------------

	//#CM54099
	// Public methods ------------------------------------------------

	//#CM54100
	// Package methods -----------------------------------------------

	//#CM54101
	// Protected methods ---------------------------------------------

	//#CM54102
	// Private methods -----------------------------------------------

}
