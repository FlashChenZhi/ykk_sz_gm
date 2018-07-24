// $Id: HardZoneParameter.java,v 1.2 2006/10/30 02:52:04 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;

//#CM50983
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.location.Zone;
import jp.co.daifuku.common.Parameter;

//#CM50984
/**<en>
 * This class preserves the zone parameters.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/11/21</TD><TD> K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:04 $
 * @author  $Author: suresh $
 </en>*/
public class HardZoneParameter extends Parameter
{
	//#CM50985
	// Class fields --------------------------------------------------
	//#CM50986
	// Class variables -----------------------------------------------
	
	//#CM50987
	/**<en>
	 * Zone instance
	 </en>*/
	private Zone wInstance;

	//#CM50988
	/**<en>
	 * Zone no.
	 </en>*/
	protected int wZoneID ;

	//#CM50989
	/**<en>
	 * Zone name
	 </en>*/
	protected String wZoneName ;

	//#CM50990
	/**<en>
	 * Load height
	 </en>*/
	protected int wHeight ;

	//#CM50991
	/**<en>
	 * Warehouse no. that the station belongs to
	 </en>*/
	protected String wWareHouseStationNumber ;

	//#CM50992
	/**<en>
	 * Name of the warehouse no. that the station belongs to
	 </en>*/
	protected String wWareHouseName ;

	//#CM50993
	/**<en>
	 * Direction of search for empty locations
	 </en>*/
	protected int wDirection;

	//#CM50994
	/**<en>
	 * Starting bank which specifies the range
	 </en>*/
	protected int wStartBank ;

	//#CM50995
	/**<en>
	 * Starting bay which specifies the range
	 </en>*/
	protected int wStartBay ;

	//#CM50996
	/**<en>
	 * Starting level which specifies the range
	 </en>*/
	protected int wStartLevel ;

	//#CM50997
	/**<en>
	 * Ending bank which specifies the range
	 </en>*/
	protected int wEndBank ;

	//#CM50998
	/**<en>
	 * Ending bay which specifies the range
	 </en>*/
	protected int wEndBay ;

	//#CM50999
	/**<en>
	 * Ending level which specifies the range
	 </en>*/
	private int wEndLevel ;

	//#CM51000
	/**<en>
	 * Preserve the priority in hard zone operation. 
	 * This variable is valid only when the hard zone is the type specified.
	 </en>*/
	private String wPriority = "          ";



	//#CM51001
	// Class method --------------------------------------------------
	//#CM51002
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:04 $") ;
	}

	//#CM51003
	// Constructors --------------------------------------------------

	//#CM51004
	/**<en>
	 * Initialize this class.
	 * @param conn :connection object with database
	 * @param kind :process type
	 </en>*/
	public HardZoneParameter()
	{
	}
	//#CM51005
	// Public methods ------------------------------------------------
	//#CM51006
	/**<en>
	 * Set the Zone instance.
	 * @param obj :instance to set
	 </en>*/
	public void setInstance(Zone obj)
	{
		wInstance = obj;
	}

	//#CM51007
	/**<en>
	 * Retrieve the Zone instance.
	 * @return Zone instance
	 </en>*/
	public Zone getInstance()
	{
		return wInstance ;
	}

	//#CM51008
	/**<en>
	 * Set the zone no.
	 * @param zid   :zone no. to set
	 </en>*/
	public void setZoneID(int zid)
	{
		wZoneID = zid ;
	}

	//#CM51009
	/**<en>
	 * Retrieve the zone no.
	 * @return    zone no.
	 </en>*/
	public int getZoneID()
	{
		return wZoneID ;
	}
	//#CM51010
	/**<en>
	 * Set the zone name.
	 * @param nm :zone name to set
	 </en>*/
	public void setZoneName(String nm)
	{
		wZoneName = nm ;
	}

	//#CM51011
	/**<en>
	 * Retrieve the zone name.
	 * @return    zone name
	 </en>*/
	public String getZoneName()
	{
		return(wZoneName) ;
	}

	//#CM51012
	/**<en>
	 * Set the load height.
	 * @param ht :load height to set
	 </en>*/
	public void setHeight(int ht)
	{
		wHeight = ht ;
	}

	//#CM51013
	/**<en>
	 * Retrieve the load height.
	 * @return    load height
	 </en>*/
	public int getHeight()
	{
		return(wHeight) ;
	}

	//#CM51014
	/**<en>
	 * Set the warehouse no. that this zone belongs to.
	 * @param wh warehouse no.
	 </en>*/
	public void setWareHouseStationNumber(String whnum)
	{
		wWareHouseStationNumber = whnum;
	}

	//#CM51015
	/**<en>
	 * Retrieve the warehouse no. that this zone belongs to.
	 * @return warehouse no.
	 </en>*/
	public String getWareHouseStationNumber()
	{
		return wWareHouseStationNumber;
	}


	//#CM51016
	/**<en>
	 * Set the name of the warehouse that this zobne belongs to.
	 * @param  the name of the warehouse
	 </en>*/
	public void setWareHouseName(String wh)
	{
		wWareHouseName = wh;
	}

	//#CM51017
	/**<en>
	 * Retrieve the name of the warehouse that this zobne belongs to.
	 * @return the name of the warehouse
	 </en>*/
	public String getWareHouseName()
	{
		return wWareHouseName;
	}

	//#CM51018
	/**<en>
	 * Set the search direction for empty locations.
	 * @param di :the search direction for empty locations to set
	 </en>*/
	public void setDirection(int di)
	{
		wDirection = di ;
	}

	//#CM51019
	/**<en>
	 * Retrieve the search direction for empty locations.
	 * @return :the search direction for empty locations
	 </en>*/
	public int getDirection()
	{
		return wDirection ;
	}

	//#CM51020
	/**<en>
	 * Set the starting bank of soft zone.
	 * @param sbank :starting bank
	 </en>*/
	public void setStartBank(int sbank)
	{
		wStartBank = sbank ;
	}

	//#CM51021
	/**<en>
	 * Retrieve the starting bank of soft zone.
	 * @return :starting bank
	 </en>*/
	public int getStartBank()
	{
		return wStartBank ;
	}

	//#CM51022
	/**<en>
	 * Set the starting bay of soft zone.
	 * @param sbay :starting bay
	 </en>*/
	public void setStartBay(int sbay)
	{
		wStartBay = sbay ;
	}

	//#CM51023
	/**<en>
	 * Retrieve the starting bay of soft zone.
	 * @return :starting bay
	 </en>*/
	public int getStartBay()
	{
		return wStartBay ;
	}

	//#CM51024
	/**<en>
	 * Set the starting level of soft zone.
	 * @param slevel :satrting level
	 </en>*/
	public void setStartLevel(int slevel)
	{
		wStartLevel = slevel ;
	}

	//#CM51025
	/**<en>
	 * Retrieve the starting level of soft zone.
	 * @return :satrting level
	 </en>*/
	public int getStartLevel()
	{
		return wStartLevel ;
	}

	//#CM51026
	/**<en>
	 * Set the ending bank of soft zone.
	 * @param ebank :ending bank
	 </en>*/
	public void setEndBank(int ebank)
	{
		wEndBank = ebank ;
	}

	//#CM51027
	/**<en>
	 * Retrieve the ending bank of soft zone.
	 * @return :ending bank
	 </en>*/
	public int getEndBank()
	{
		return wEndBank ;
	}

	//#CM51028
	/**<en>
	 * Set the ending bay of soft zone.
	 * @param ebay :ending bay
	 </en>*/
	public void setEndBay(int ebay)
	{
		wEndBay = ebay ;
	}

	//#CM51029
	/**<en>
	 * Retrieve the ending bay of soft zone.
	 * @return :ending bay
	 </en>*/
	public int getEndBay()
	{
		return wEndBay ;
	}

	//#CM51030
	/**<en>
	 * Set the ending level of soft zone.
	 * @param elevel :ending level
	 </en>*/
	public void setEndLevel(int elevel)
	{
		wEndLevel = elevel ;
	}

	//#CM51031
	/**<en>
	 * Retrieve the ending bank of soft zone.
	 * @return :ending level
	 </en>*/
	public int getEndLevel()
	{
		return wEndLevel ;
	}

	//#CM51032
	/**<en>
	 * Set the prioritized zone search order.
	 * @param sno :prioritized zone search order
	 </en>*/
	public void setPriority(String pri)
	{
		wPriority = pri ;
	}

	//#CM51033
	/**<en>
	 * Retrieve the prioritized zone search order.
	 * @return :prioritized zone search order
	 </en>*/
	public String getPriority()
	{
		return wPriority ;
	}

	//#CM51034
	// Package methods -----------------------------------------------

	//#CM51035
	// Protected methods ---------------------------------------------

	//#CM51036
	// Private methods -----------------------------------------------
}
//#CM51037
//end of class

