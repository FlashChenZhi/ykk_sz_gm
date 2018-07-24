// $Id: Zone.java,v 1.2 2006/10/30 02:33:22 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49972
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.text.StringUtil;

//#CM49973
/**<en>
 * This class controls the zone of locations.
 * Zone will be assigned to each shelf of the warehouse. Zone information is managed as follows.
 * Distinctions of zone control method are preserved in zone type of the instance.
 * 1: Soft zone
 *   Storage zone will be determined based on the zone information in article name master.
 *   The range of the zone (bank, bay and level) is preserved. 
 *   Also it is possible to include more than one zone range in one location.
 * 2: Hard zone
 *   Storage zone will be determined based on teh entered data of load height, etc.
 *   Also it is possible to keep the prioritized zones d.g. small load size -> medium load size -> large.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:22 $
 * @author  $Author: suresh $
 </en>*/
public class Zone extends ZoneInformation
{
	//#CM49974
	// Class fields --------------------------------------------------
	//#CM49975
	/**<en>
	 * Field of search direction at the empty location search  (from HP front)
	 </en>*/
	public static final int HP_FRONT = 1;

	//#CM49976
	/**<en>
	 * Field of search direction at the empty location search (from HP lower level)
	 </en>*/
	public static final int HP_LOWER = 2;

	//#CM49977
	/**<en>
	 * Field of search direction at the empty location search  (from OP front)
	 </en>*/
	public static final int OP_FRONT = 3;

	//#CM49978
	/**<en>
	 * Field of search direction at the empty location search  (from OP loser level)
	 </en>*/
	public static final int OP_LOWER = 4;

	//#CM49979
	/**<en>
	 * Element numbers of bank, bay and level for start/end shelf (Bank)
	 </en>*/
	public static final int BANK = 0 ;

	//#CM49980
	/**<en>
	 *  Element numbers of bank, bay and level for start/end shelf (Bay)
	 </en>*/
	public static final int BAY = 1 ;

	//#CM49981
	/**<en>
	 *  Element numbers of bank, bay and level for start/end shelf (Level)
	 </en>*/
	public static final int LEVEL = 2 ;

	//#CM49982
	// Class variables -----------------------------------------------
	//#CM49983
	/**<en>
	 * Warehouse no. the station belongs to
	 </en>*/
	protected String wWareHouseStationNumber ;

	//#CM49984
	/**<en>
	 * Zone no.
	 </en>*/
	protected int wZoneID ;

	//#CM49985
	/**<en>
	 * Zone name
	 </en>*/
	protected String wZoneName ;

	//#CM49986
	/**<en>
	 * Load height
	 </en>*/
	protected int wHeight = 0;

	//#CM49987
	/**<en>
	 * Direction of empty location search
	 </en>*/
	protected int wDirection;

	//#CM49988
	/**<en>
	 * Starting bank which specifies the range. 
	 * The variable is valid only with soft zone type.
	 </en>*/
	protected int wStartBank ;

	//#CM49989
	/**<en>
	 * Starting bay which specifies the range. 
	 * The variable is valid only with soft zone type.
	 </en>*/
	protected int wStartBay ;

	//#CM49990
	/**<en>
	 * Starting level which specifies the range. 
	 * The variable is valid only with soft zone type.
	 </en>*/
	protected int wStartLevel ;

	//#CM49991
	/**<en>
	 * Ending bank which specifies the range. 
	 * The variable is valid only with soft zone type.
	 </en>*/
	protected int wEndBank ;

	//#CM49992
	/**<en>
	 * Ending bay which specifies the range.
	 * The variable is valid only with soft zone type.
	 </en>*/
	protected int wEndBay ;

	//#CM49993
	/**<en>
	 * Ending level which specifies the range.
	 * The variable is valid only with soft zone type.
	 </en>*/
	private int wEndLevel ;

	//#CM49994
	/**<en>
	 * Prioritized order of zone search. It is preserved for in case the identical 
	 * zone ID existed.
	 * The variable is valid only with soft zone type.
	 </en>*/
	protected int wOrderNumber ;

	//#CM49995
	/**<en>
	 * This number is used to identify the serial no. and zone. 
	 </en>*/
	protected int wSerialNumber ;

	//#CM49996
	/**<en>
	 * Preserve the priority of hard zone operation. THis variable is valid only with
	 * hard zone type.
	 </en>*/
	private String wPriority ;

	//#CM49997
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM49998
	// Class method --------------------------------------------------
	//#CM49999
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:22 $") ;
	}

	//#CM50000
	// Constructors --------------------------------------------------
	//#CM50001
	/**<en>
	 * Construct new <CODE>Zone</CODE>.
	 </en>*/
	public Zone()
	{
	}

	//#CM50002
	// Public methods ------------------------------------------------

	//#CM50003
	/**<en>
	 * Compare this Zone object with the object which has been specified through parameter
	 * to see if they are equal.
	 * @param obj :Object to compare
	 * @return true if both objects are the same, or false if not.
	 </en>*/
	public boolean equals(Zone obj)
	{
		if(	wZoneID == obj.getZoneID() &&
			StringUtil.isEqualsStr(wWareHouseStationNumber, obj.getWareHouseStationNumber()) &&
			wZoneName == obj.getZoneName() &&
			wHeight == obj.getHeight() &&
			wDirection == obj.getDirection() &&
			wStartBank == obj.getStartBank() &&
			wStartBay == obj.getStartBay() &&
			wStartLevel == obj.getStartLevel() &&
			wEndBank == obj.getEndBank() &&
			wEndBay ==  obj.getEndBay() &&
			wEndLevel == obj.getEndLevel() &&
			wOrderNumber == obj.getOrderNumber() &&
			wSerialNumber == obj.getSerialNumber() &&
			wPriority == obj.getPriority())
		{
			return true;
		}
		
		return false;
	}

	//#CM50004
	/**<en>
	 * Set hte zone no.
	 * @param zid   :zone no. to set
	 </en>*/
	public void setZoneID(int zid)
	{
		wZoneID = zid ;
	}

	//#CM50005
	/**<en>
	 * Retrieve the zone no.
	 * @return    :zone no.
	 </en>*/
	public int getZoneID()
	{
		return wZoneID ;
	}

	//#CM50006
	/**<en>
	 * Set the name of the soft zone.
	 * @param nm :the name of the soft zone to set
	 </en>*/
	public void setZoneName(String nm)
	{
		wZoneName = nm ;
	}

	//#CM50007
	/**<en>
	 * Retrieve the name of the soft zone.
	 * @return    :the name of the soft zone
	 </en>*/
	public String getZoneName()
	{
		return(wZoneName) ;
	}

	//#CM50008
	/**<en>
	 * Set the warehouse no. that this zone belong to.
	 * @param whnum :warehouse no.
	 </en>*/
	public void setWareHouseStationNumber(String whnum)
	{
		wWareHouseStationNumber = whnum;
	}

	//#CM50009
	/**<en>
	 * Retrieve the warehouse no. that this zone belong to.
	 * @return :warehouse no.
	 </en>*/
	public String getWareHouseStationNumber()
	{
		return wWareHouseStationNumber;
	}

	//#CM50010
	/**<en>
	 * Retrievet the load height of the hard zone.
	 * @return :load height
	 </en>*/
	public int getHeight()
	{
		return wHeight ;
	}

	//#CM50011
	/**<en>
	 * Set the load height of the hard zone.
	 * @param hgt :load height to set
	 </en>*/
	public void setHeight(int hgt)
	{
		wHeight = hgt ;
	}

	//#CM50012
	/**<en>
	 * Set the direction of empty location search.
	 * @param di :the direction of empty location search to set
	 </en>*/
	public void setDirection(int di)
	{
		wDirection = di ;
	}

	//#CM50013
	/**<en>
	 * Retrieve the direction of empty location search.
	 * @return :the direction of empty location search
	 </en>*/
	public int getDirection()
	{
		return wDirection ;
	}

	//#CM50014
	/**<en>
	 * Set the starting bank of the soft zone.
	 * @param sbank :starting bank
	 </en>*/
	public void setStartBank(int sbank)
	{
		wStartBank = sbank ;
	}

	//#CM50015
	/**<en>
	 * Retrieve the starting bank of the soft zone.
	 * @return :starting bank
	 </en>*/
	public int getStartBank()
	{
		return wStartBank ;
	}

	//#CM50016
	/**<en>
	 * Set the starting bay of the soft zone.
	 * @param sbay :the starting bay
	 </en>*/
	public void setStartBay(int sbay)
	{
		wStartBay = sbay ;
	}

	//#CM50017
	/**<en>
	 * Retrieve the starting bay of the soft zone.
	 * @return :the starting bay 
	 </en>*/
	public int getStartBay()
	{
		return wStartBay ;
	}

	//#CM50018
	/**<en>
	 * Set the starting level of the soft zone.
	 * @param slevel :strating level
	 </en>*/
	public void setStartLevel(int slevel)
	{
		wStartLevel = slevel ;
	}

	//#CM50019
	/**<en>
	 * Retrieve the starting level of the soft zone.
	 * @return :starting level
	 </en>*/
	public int getStartLevel()
	{
		return wStartLevel ;
	}

	//#CM50020
	/**<en>
	 * Set teh ending bank of the soft zone.
	 * @param ebank :ending bank
	 </en>*/
	public void setEndBank(int ebank)
	{
		wEndBank = ebank ;
	}

	//#CM50021
	/**<en>
	 * Retrieve the ending bank of the soft zone.
	 * @return :ending bank
	 </en>*/
	public int getEndBank()
	{
		return wEndBank ;
	}

	//#CM50022
	/**<en>
	 * Set the ending bay of the soft zone.
	 * @param ebay :ending bay
	 </en>*/
	public void setEndBay(int ebay)
	{
		wEndBay = ebay ;
	}

	//#CM50023
	/**<en>
	 * Retrieve the ending bay of the soft zone.
	 * @return :ending bay
	 </en>*/
	public int getEndBay()
	{
		return wEndBay ;
	}

	//#CM50024
	/**<en>
	 * Set the ending level of the soft zone.
	 * @param elevel :ending level
	 </en>*/
	public void setEndLevel(int elevel)
	{
		wEndLevel = elevel ;
	}

	//#CM50025
	/**<en>
	 * Retrieve the ending level of the soft zone.
	 * @return :ending level
	 </en>*/
	public int getEndLevel()
	{
		return wEndLevel ;
	}

	//#CM50026
	/**<en>
	 * Set the priority of zone search.
	 * @param order :the priority of zone search
	 </en>*/
	public void setOrderNumber(int order)
	{
		wOrderNumber = order ;
	}

	//#CM50027
	/**<en>
	 * Retrieve the priority of zone search.
	 * @return :the priority of zone search
	 </en>*/
	public int getOrderNumber()
	{
		return wOrderNumber ;
	}

	//#CM50028
	/**<en>
	 * Set the serial no..
	 * @param sno :serial no.
	 </en>*/
	public void setSerialNumber(int sno)
	{
		wSerialNumber = sno ;
	}

	//#CM50029
	/**<en>
	 * Retrieve the serial no.
	 * @return :serial no.
	 </en>*/
	public int getSerialNumber()
	{
		return wSerialNumber ;
	}

	//#CM50030
	/**<en>
	 * Set the priority of hard zone operation.
	 * @param pri :the priority of hard zone operation
	 </en>*/
	public void setPriority(String pri)
	{
		wPriority = pri ;
	}

	//#CM50031
	/**<en>
	 * Retrieve the priority of hard zone operation.
	 * @return :the priority of hard zone operation
	 </en>*/
	public String getPriority()
	{
		return wPriority ;
	}

	//#CM50032
	/**<en>
	 * Return the string representation of Zone.
	 * @return    string representation
	 </en>*/
	public String toString()
	{
		StringBuffer buf = new StringBuffer(100) ;
		buf.append("\nZoneID:" + Integer.toString(wZoneID)) ;
		buf.append("\nDirection:" + Integer.toString(wDirection)) ;
		buf.append("\nWareHouseStationNumber:" + wWareHouseStationNumber) ;
		buf.append("\nStartBank:" + Integer.toString(wStartBank)) ;
		buf.append("\nStartBay:" + Integer.toString(wStartBay)) ;
		buf.append("\nStartLevel:" + Integer.toString(wStartLevel)) ;
		buf.append("\nEndBank:" + Integer.toString(wEndBank)) ;
		buf.append("\nEndBay:" + Integer.toString(wEndBay)) ;
		buf.append("\nEndLevel:" + Integer.toString(wEndLevel)) ;
		buf.append("\nOrderNumber:" + Integer.toString(wOrderNumber)) ;
		buf.append("\nSerialNumber:" + Integer.toString(wSerialNumber)) ;
		buf.append("\nPriority:" + wPriority) ;
		return buf.toString() ;
	}

	//#CM50033
	// Package methods -----------------------------------------------

	//#CM50034
	// Protected methods ---------------------------------------------

	//#CM50035
	// Private methods -----------------------------------------------

}
//#CM50036
//end of class

