// $Id: Zone.java,v 1.2 2006/10/26 08:12:12 suresh Exp $
package jp.co.daifuku.wms.asrs.entity ;

//#CM42040
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.entity.HardZone;
import jp.co.daifuku.common.MessageResource;

//#CM42041
/**<en>
 * This class is used for zone control of shelves.
 * Zone is assigned to each shlf of the warehouse.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/26 08:12:12 $
 * @author  $Author: suresh $
 </en>*/
public class Zone extends Entity
{
	//#CM42042
	// Class fields --------------------------------------------------
	//#CM42043
	/**<en>
	 * Field of search direction at empty location search (from HP fron side)
	 </en>*/
	public static final int HP_FRONT = 1;

	//#CM42044
	/**<en>
	 * Field of search direction at empty location search (from HP lower level)
	 </en>*/
	public static final int HP_LOWER = 2;

	//#CM42045
	/**<en>
	 * Field of search direction at empty location search (from OP front side)
	 </en>*/
	public static final int OP_FRONT = 3;

	//#CM42046
	/**<en>
	 * Field of search direction at empty location search (from OP lower level)
	 </en>*/
	public static final int OP_LOWER = 4;

	//#CM42047
	/**<en>
	 * Element numbers of start/end shelf of Bank
	 </en>*/
	public static final int BANK = 0 ;

	//#CM42048
	/**<en>
	 * Element numbers of start/end shelf of Bay
	 </en>*/
	public static final int BAY = 1 ;

	//#CM42049
	/**<en>
	 * Element numbers of start/end shelf of Level
	 </en>*/
	public static final int LEVEL = 2 ;

	//#CM42050
	// Class variables -----------------------------------------------
	//#CM42051
	/**<en>
	 * Zone no.
	 </en>*/
	protected int wSoftZoneID ;

	//#CM42052
	/**<en>
	 * Name of the zone
	 </en>*/
	protected String wName ;
	
	//#CM42053
	/**<en>
	 * Warehouse no, the station belongs to
	 </en>*/
	protected String wWHStationNumber ;

	//#CM42054
	/**<en>
	 * Search direction in empty location search
	 </en>*/
	protected int wDirection;

	//#CM42055
	/**<en>
	 * Object of <CODE>HardZone</CODE>
	 </en>*/
	protected HardZone wTargetHardZone = null;

	//#CM42056
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM42057
	/**<en>
	 * Start Bank
	 </en>*/
	protected int wStartBank ;

	//#CM42058
	/**<en>
	 * Start Bay
	 </en>*/
	protected int wStartBay ;

	//#CM42059
	/**<en>
	 * Start Level
	 </en>*/
	protected int wStartLevel ;

	//#CM42060
	/**<en>
	 * End Bank
	 </en>*/
	protected int wEndBank ;

	//#CM42061
	/**<en>
	 * End Bay
	 </en>*/
	protected int wEndBay ;

	//#CM42062
	/**<en>
	 * End Level
	 </en>*/
	private int wEndLevel ;


	//#CM42063
	// Class method --------------------------------------------------
	//#CM42064
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/26 08:12:12 $") ;
	}

	//#CM42065
	// Constructors --------------------------------------------------

	//#CM42066
	// Public methods ------------------------------------------------
	//#CM42067
	/**<en>
	 * Sets the zone no.
	 * @param zid :zone no. to set
	 </en>*/
	public void setSoftZoneID(int zid)
	{
		wSoftZoneID = zid ;
	}

	//#CM42068
	/**<en>
	 * Gets the zone no.
	 * @return :zone no.
	 </en>*/
	public int getSoftZoneID()
	{
		return wSoftZoneID ;
	}

	//#CM42069
	/**<en>
	 * Sets name of zone.
	 * @param nm :name of zone to set
	 </en>*/
	public void setName(String nm)
	{
		wName = nm ;
	}

	//#CM42070
	/**<en>
	 * Getse name of zone
	 * @return :name of zone
	 </en>*/
	public String getName()
	{
		return(wName) ;
	}

	//#CM42071
	/**<en>
	 * Sets the warehouse no. this zone belongs to.
	 * @param whnum :warehouse no.
	 </en>*/
	public void setWHStationNumber(String whnum)
	{
		wWHStationNumber = whnum;
	}

	//#CM42072
	/**<en>
	 * Gets the warehouse no. this zone belongs to.
	 * @return warehouse no.
	 </en>*/
	public String getWHStationNumber()
	{
		return wWHStationNumber;
	}

	//#CM42073
	/**<en>
	 * Sets the direction of empty location search.
	 * @param di :direction of empty location search to set
	 </en>*/
	public void setDirection(int di)
	{
		wDirection = di ;
	}

	//#CM42074
	/**<en>
	 * Gets direction of empty location search.
	 * @return :direction of empty location search
	 </en>*/
	public int getDirection()
	{
		return wDirection ;
	}

	//#CM42075
	/**<en>
	 * Sets <CODE>HardZone</CODE>.
	 * @param zone :HardZone to set
	 </en>*/
	public void setHardZone(HardZone zone)
	{
		wTargetHardZone = zone ;
	}

	//#CM42076
	/**<en>
	 * Gets <CODE>HardZone</CODE>.
	 * @return HardZone
	 </en>*/
	public HardZone getHardZone()
	{
		return wTargetHardZone ;
	}

	//#CM42077
	/**<en>
	 * Sets Start Bank.
	 * @param sbank :Start Bank
	 </en>*/
	public void setStartBank(int sbank)
	{
		wStartBank = sbank ;
	}

	//#CM42078
	/**<en>
	 * Gets Start Bank.
	 * @return Start Bank
	 </en>*/
	public int getStartBank()
	{
		return wStartBank ;
	}

	//#CM42079
	/**<en>
	 * Sets Start Bay.
	 * @param sbay :Start Bay
	 </en>*/
	public void setStartBay(int sbay)
	{
		wStartBay = sbay ;
	}

	//#CM42080
	/**<en>
	 * Gets Start Bay.
	 * @return Start Bay
	 </en>*/
	public int getStartBay()
	{
		return wStartBay ;
	}

	//#CM42081
	/**<en>
	 * Sets Start Level.
	 * @param slevel :Start Level
	 </en>*/
	public void setStartLevel(int slevel)
	{
		wStartLevel = slevel ;
	}

	//#CM42082
	/**<en>
	 * Gets Start Level.
	 * @return Start Level
	 </en>*/
	public int getStartLevel()
	{
		return wStartLevel ;
	}

	//#CM42083
	/**<en>
	 * Sets End Bank.
	 * @param ebank :End Bank
	 </en>*/
	public void setEndBank(int ebank)
	{
		wEndBank = ebank ;
	}

	//#CM42084
	/**<en>
	 * Gets End Bank.
	 * @return End Bank
	 </en>*/
	public int getEndBank()
	{
		return wEndBank ;
	}

	//#CM42085
	/**<en>
	 * Sets End Bay.
	 * @param ebay :End Bay
	 </en>*/
	public void setEndBay(int ebay)
	{
		wEndBay = ebay ;
	}

	//#CM42086
	/**<en>
	 * Gets End Bay.
	 * @return End Bay
	 </en>*/
	public int getEndBay()
	{
		return wEndBay ;
	}

	//#CM42087
	/**<en>
	 * Sets End Level.
	 * @param elevel :End Level
	 </en>*/
	public void setEndLevel(int elevel)
	{
		wEndLevel = elevel ;
	}

	//#CM42088
	/**<en>
	 * Gets End Level.
	 * @return End Level
	 </en>*/
	public int getEndLevel()
	{
		return wEndLevel ;
	}

	//#CM42089
	// Package methods -----------------------------------------------

	//#CM42090
	// Protected methods ---------------------------------------------

	//#CM42091
	// Private methods -----------------------------------------------

}
//#CM42092
//end of class

