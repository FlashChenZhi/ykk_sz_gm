// $Id: HardZone.java,v 1.2 2006/10/30 02:33:25 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49511
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
import jp.co.daifuku.common.MessageResource;

//#CM49512
/**<en>
 * This class is used to control the hard zones of locations.
 * Storage zone will be determined based on the entered data such as load height, etc.
 * It is possible to define just one zone per location. 
 * Also it is possible to keep the prioritized location order, e.g., small load -> medium load -> large.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:25 $
 * @author  $Author: suresh $
 </en>*/
public class HardZone extends ToolEntity
{
	//#CM49513
	// Class fields --------------------------------------------------

	//#CM49514
	// Class variables -----------------------------------------------
	//#CM49515
	/**<en>
	 * Hard zone no.
	 </en>*/
	protected int wHardZoneID ;

	//#CM49516
	/**<en>
	 * Name of the hard zone
	 </en>*/
	protected String wName ;
	
	//#CM49517
	/**<en>
	 * Warehouse no. that the station belongs to
	 </en>*/
	protected String wWareHouseStationNumber ;

	//#CM49518
	/**<en>
	 * Load height
	 </en>*/
	protected int wHeight = 0;

	//#CM49519
	/**<en>
	 * Priority in hard zone operation
	 </en>*/
	protected String wPriority ;

	//#CM49520
	/**<en>
	 * starting bank 
	 </en>*/
	protected int wStartBank = 0;
	
	//#CM49521
	/**<en>
	 * starting bay 
	 </en>*/
	protected int wStartBay = 0;
	
	//#CM49522
	/**<en>
	 * starting level 
	 </en>*/
	protected int wStartLevel = 0;
	
	//#CM49523
	/**<en>
	 * ending bank 
	 </en>*/
	protected int wEndBank = 0;
	
	//#CM49524
	/**<en>
	 * ending bay 
	 </en>*/
	protected int wEndBay = 0;
	
	//#CM49525
	/**<en>
	 * ending level 
	 </en>*/
	protected int wEndLevel = 0;
	
	//#CM49526
	/**<en>
	 * Delimiter
	 * This is the delimiter of the parameter for MessageDef when Exception occured.
	 </en>*/
	public String wDelim = MessageResource.DELIM ;

	//#CM49527
	// Class method --------------------------------------------------
	//#CM49528
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:25 $") ;
	}

	//#CM49529
	// Constructors --------------------------------------------------
	//#CM49530
	/**<en>
	 * Construct new <CODE>HardZone</CODE>.
	 </en>*/
	public HardZone()
	{
	}

	//#CM49531
	// Public methods ------------------------------------------------

	//#CM49532
	/**<en>
	 * Set the hard zone ID.
	 * @param hz :hard zone ID
	 </en>*/
	public void setHardZoneID(int hz)
	{
		wHardZoneID = hz;
	}

	//#CM49533
	/**<en>
	 * Retrieve the hard zone ID.
	 * @return :hard zone ID
	 </en>*/
	public int getHardZoneID()
	{
		return wHardZoneID;
	}

	//#CM49534
	/**<en>
	 * Set the name of the hard zone.
	 * @param nam :name of the hard zone
	 </en>*/
	public void setName(String nam)
	{
		wName = nam;
	}

	//#CM49535
	/**<en>
	 * Retrieve name of the hard zone.
	 * @return :name of the hard zone
	 </en>*/
	public String getName()
	{
		return wName;
	}

	//#CM49536
	/**<en>
	 * Set the warehouse no. this zone belongs to.
	 * @param whnum :warehouse no.
	 </en>*/
	public void setWareHouseStationNumber(String whnum)
	{
		wWareHouseStationNumber = whnum;
	}

	//#CM49537
	/**<en>
	 * Retrieve the warehouse no. this zone belongs to.
	 * @return :warehouse no.
	 </en>*/
	public String getWareHouseStationNumber()
	{
		return wWareHouseStationNumber;
	}

	//#CM49538
	/**<en>
	 * Set the load height of the hard zone.
	 * @param hgt :the load height to set
	 </en>*/
	public void setHeight(int hgt)
	{
		wHeight = hgt ;
	}

	//#CM49539
	/**<en>
	 * Retrieve the load height of hard zone.
	 * @return :the load height
	 </en>*/
	public int getHeight()
	{
		return wHeight ;
	}

	//#CM49540
	/**<en>
	 * Set the priority of hard zone search.
	 * @param pri :the priority of hard zone search
	 </en>*/
	public void setPriority(String pri)
	{
		wPriority = pri ;
	}

	//#CM49541
	/**<en>
	 * Retrieve the priority of hard zone search.
	 * @return :the priority of hard zone search
	 </en>*/
	public String getPriority()
	{
		return wPriority ;
	}

	//#CM49542
	/**<en>
	 * Set the startinging bank.
	 * @param sbnk :starting bank.
	 </en>*/
	public void setStartBank(int sbnk)
	{
		wStartBank = sbnk ;
	}

	//#CM49543
	/**<en>
	 * Retrieve the starting bank.
	 * @return :starting bank
	 </en>*/
	public int getStartBank()
	{
		return wStartBank ;
	}
	
	//#CM49544
	/**<en>
	 * Sest teh starting bay.
	 * @param sbay :starting bay
	 </en>*/
	public void setStartBay(int sbay)
	{
		wStartBay = sbay ;
	}

	//#CM49545
	/**<en>
	 * Retrieve the starting bay.
	 * @return :starting bay
	 </en>*/
	public int getStartBay()
	{
		return wStartBay ;
	}
	
	//#CM49546
	/**<en>
	 * Set the starting level.
	 * @param slvl :starting level
	 </en>*/
	public void setStartLevel(int slvl)
	{
		wStartLevel = slvl ;
	}

	//#CM49547
	/**<en>
	 * Retrieve the starting level.
	 * @return :starting level
	 </en>*/
	public int getStartLevel()
	{
		return wStartLevel ;
	}	
	
	//#CM49548
	/**<en>
	 * Set the ending bank.
	 * @param ebnk :the ending bank
	 </en>*/
	public void setEndBank(int ebnk)
	{
		wEndBank = ebnk ;
	}

	//#CM49549
	/**<en>
	 * Retrieve the ending bank.
	 * @return :the ending bank
	 </en>*/
	public int getEndBank()
	{
		return wEndBank ;
	}	
	
	//#CM49550
	/**<en>
	 * Set the ending bay.
	 * @param ebay :the ending bay
	 </en>*/
	public void setEndBay(int ebay)
	{
		wEndBay = ebay ;
	}

	//#CM49551
	/**<en>
	 * Retrieve the ending bay.
	 * @return :the ending bay
	 </en>*/
	public int getEndBay()
	{
		return wEndBay ;
	}	
	
	//#CM49552
	/**<en>
	 * Set the ending level.
	 * @param elvl :the ending level
	 </en>*/
	public void setEndLevel(int elvl)
	{
		wEndLevel = elvl ;
	}

	//#CM49553
	/**<en>
	 * Retrieve the ending level.
	 * @return :the ending level
	 </en>*/
	public int getEndLevel()
	{
		return wEndLevel ;
	}	
	
	//#CM49554
	// Package methods -----------------------------------------------

	//#CM49555
	// Protected methods ---------------------------------------------

	//#CM49556
	// Private methods -----------------------------------------------

}
//#CM49557
//end of class

