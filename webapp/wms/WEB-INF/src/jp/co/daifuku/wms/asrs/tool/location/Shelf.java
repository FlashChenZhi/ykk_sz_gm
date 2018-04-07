// $Id: Shelf.java,v 1.2 2006/10/30 02:33:25 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49558
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.text.DecimalFormat;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.common.WmsParam;

//#CM49559
/**<en>
 * This class is used to preserve the shelf information.
 * Unlike the stations such as conveyers, there are no opearations with shelves e.g. start ups;
 * therefore some of the information are fixed and preserved.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/11</TD><TD>K.Mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>Added the field of unspecified soft zone.<BR>
 * This will be used when newly creating the locations.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:25 $
 * @author  $Author: suresh $
 </en>*/
public class Shelf extends Station
{
	//#CM49560
	// Class fields --------------------------------------------------
	//#CM49561
	/**<en>
	 * Default station no. of the warehouse
	 </en>*/
	public static final int    DEFAULT_WH_NUM = 1 ;

	//#CM49562
	/**<en>
	 * Field of accessibility (accessible)
	 </en>*/
	public static final int    ACCESS_OK = 0 ;

	//#CM49563
	/**<en>
	 * Field of accessibility (inaccessible)
	 </en>*/
	public static final int    ACCESS_NG = 1 ;

	//#CM49564
	/**<en>
	 * Field of location status (empty)
	 </en>*/
	public static final int    PRESENCE_EMPTY = 0;  

	//#CM49565
	/**<en>
	 * Field of location status (loaded)
	 </en>*/
	public static final int    PRESENCE_STORAGED = 1;  

	//#CM49566
	/**<en>
	 * Field of location status (reserved)
	 </en>*/
	public static final int    PRESENCE_RESERVATION = 2;
	
	//#CM49567
	/**<en>
	 * Field of hard zone
	 </en>*/
	public static final int	HARD = 1;
	
	//#CM49568
	/**<en>
	 * Field of soft zone
	 </en>*/
	public static final int SOFT = 2;

	//#CM49569
	/**<en>
	 * Field of non-set up soft zone
	 </en>*/
	public static final int UN_SETTING = 0 ;

	//#CM49570
	// Class variables -----------------------------------------------

	//#CM49571
	/**<en>
	 * Preserve the bank of the location.
	 </en>*/
	private int wBank ;
	
	//#CM49572
	/**<en>
	 * Preserve the bay of the location.
	 </en>*/
	private int wBay ;
	
	//#CM49573
	/**<en>
	 * Preserve the level of the location.
	 </en>*/
	private int wLevel ;

	//#CM49574
	/**<en>
	 * Preserve the warehouse that this location belongs to.
	 </en>*/
	private Warehouse wWarehouse ;

	//#CM49575
	/**<en>
	 * Preserve the status of the location.
	 </en>*/
	private int wPresence ;

	//#CM49576
	/**<en>
	 * Preserve the hard zone.
	 </en>*/
	private int wHardZone ;

	//#CM49577
	/**<en>
	 * Preserve the soft zone.
	 </en>*/
	private int wSoftZone ;
	
	//#CM49578
	/**<en>
	 * Preserve the accessibility.
	 </en>*/
	private boolean wAccessNgFlag = false;

	//#CM49579
	/**<en>
	 * Preserves the positions of the shelves. (front or rear)
	 </en>*/
	private int wSide;

	//#CM49580
	/**<en>
	 * Preserve the priority.
	 </en>*/
	private int wPriority;

	//#CM49581
	/**<en>
	 * Preserves station no. (location no.) of pair shelves.
	 </en>*/
	private String wPairStationNumber ;

	//#CM49582
	// Class method --------------------------------------------------
	//#CM49583
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:25 $") ;
	}
	
	//#CM49584
	/**<en>
	 * Return the station no. based on the Bank, Bay and Level.
	 * @param bank   :bank
	 * @param bay    :bay
	 * @param level  :level
	 * @return station no. of the location (location no.)
	 </en>*/
	public static String getNumber(int bank, int bay, int level)
	{
		return(getNumber(DEFAULT_WH_NUM, bank, bay, level, 0)) ;
	}
	
	//#CM49585
	/**<en>
	 * Return the station no. based on the WareHouse, Bank, Bay and Level.
	 * @param whnum  :station no. of the warehouse
	 * @param bank   :bank
	 * @param bay    :bay
	 * @param level  :level
	 * @return station no. of the location (location no.)
	 </en>*/
	public static String getNumber(int whnum, int bank, int bay, int level)
	{
		return(getNumber(whnum, bank, bay, level, 0)) ;
	}
	
	//#CM49586
	/**<en>
	 * Return the station no. based on the Bank, Bay, Level and Address.
	 * @param whnum  :station no. of the warehouse
	 * @param bank   :bank
	 * @param bay    :bay
	 * @param level  :level
	 * @param addr   :address
	 * @return station no. of the location (location no.)
	 </en>*/
	public static String getNumber(int whnum, int bank, int bay, int level, int addr)
	{
		int banksize  = WmsParam.BANK_DB_LENGTH;
		int baysize   = WmsParam.BAY_DB_LENGTH;
		int levelsize = WmsParam.LEVEL_DB_LENGTH;
		int whsize = WmsParam.WAREHOUSE_DB_LENGTH;
		int areasize = WmsParam.AREA_DB_LENGTH;
		
		String whdecimal = "";
		for (int i = 0; i < whsize; i ++)
		{
			whdecimal = whdecimal + "0";
		}
		DecimalFormat whfmt = new DecimalFormat(whdecimal) ;
		
		String bankdecimal = "";
		for (int i = 0; i < banksize; i ++)
		{
			bankdecimal = bankdecimal + "0";
		}
		DecimalFormat bankfmt = new DecimalFormat(bankdecimal) ;
		
		String baydecimal = "";
		for (int i = 0; i < baysize; i ++)
		{
			baydecimal = baydecimal + "0";
		}
		DecimalFormat bayfmt = new DecimalFormat(baydecimal) ;
		
		String leveldecimal = "";
		for (int i = 0; i < levelsize; i ++)
		{
			leveldecimal = leveldecimal + "0";
		}
		DecimalFormat levelfmt = new DecimalFormat(leveldecimal) ;
		
		String areadecimal = "";
		for (int i = 0; i < areasize; i ++)
		{
			areadecimal = areadecimal + "0";
		}
		DecimalFormat areafmt = new DecimalFormat(areadecimal) ;
		
		return (whfmt.format(whnum) + bankfmt.format(bank) + bayfmt.format(bay) + levelfmt.format(level) + areafmt.format(addr)) ;
	}

	//#CM49587
	// Constructors --------------------------------------------------
	//#CM49588
	/**<en>
	 * Create a new instance of <code>Shelf</code> in order to newly control the locations.
	 * Please use <code>StationFactory</code> class if the instance which has the defined 
	 * shelf information already is required.
	 * @param snum  :station no. of the location (location no.)
	 * @see StationFactory
	 </en>*/
	public Shelf(String snum)
	{
		super(snum) ;
		
		//#CM49589
		//<en> max. number of preserved pallet is 1 and fixed.</en>
		wMaxPaletteQuantity = 1 ;
		
		//#CM49590
		//<en> the location is always sendable.</en>
		wSendable = true;
	}

	//#CM49591
	// Public methods ------------------------------------------------
	//#CM49592
	/**<en>
	 * Set the max. number of preserved pallet. Though this method exists to provide the 
	 * compatibility with other classes, only one pallet can be preserved in a shelf practically
	 * and therefore there will be no modificaiton in this method.
	 * @param pnum :the max. number of preserved pallet to set
	 </en>*/
	public void setMaxPaletteQuantity(int pnum)
	{
		super.setMaxPaletteQuantity(1) ;
	}

	//#CM49593
	/**<en>
	 * Retrieve the the max. number of preserved pallet.
	 * @return   :the max. number of preserved pallet
	 </en>*/
	public int getMaxPaletteQuantity()
	{
		return(1) ;
	}

	//#CM49594
	/**<en>
	 * Set the bank.
	 * @param b :bank
	 </en>*/
	public void setBank(int b)
	{
		wBank = b ;
	}
	
	//#CM49595
	/**<en>
	 * Set the bay.
	 * @param b :bay
	 </en>*/
	public void setBay(int b)
	{
		wBay = b ;
	}
	
	//#CM49596
	/**<en>
	 * Set the level.
	 * @param lv :level
	 </en>*/
	public void setLevel(int lv)
	{
		wLevel = lv ;
	}
	
	//#CM49597
	/**<en>
	 * Return the bank.
	 * @return   :bank
	 </en>*/
	public int getBank()
	{
		return (wBank) ;
	}
	
	//#CM49598
	/**<en>
	 * Return the bay.
	 * @return   :bay
	 </en>*/
	public int getBay()
	{
		return (wBay) ;
	}
	
	//#CM49599
	/**<en>
	 * Return the level.
	 * @return   :level
	 </en>*/
	public int getLevel()
	{
		return (wLevel) ;
	}

	//#CM49600
	/**<en>
	 * Set the status of the location.
	 * @param  pre :location status
     * @throws InvalidStatusException :Notifies if the contents of pre is invalid.
	 </en>*/
	public void setPresence(int pre) throws InvalidStatusException
	{
		//#CM49601
		//<en> Check the status.</en>
		switch(pre)
		{
			//#CM49602
			//<en> List of correct status</en>
			case PRESENCE_EMPTY:
			case PRESENCE_STORAGED:
			case PRESENCE_RESERVATION:
				wPresence = pre;
				break ;
				
			//#CM49603
			//<en> IF incorrect status were to set, it lets exception occur but will not modify the status.</en>
			default:
				//#CM49604
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[3] ;
				tObj[0] = this.getClass().getName() ;
				tObj[1] = "wPresence";
				tObj[2] = Integer.toString(pre) ;
				String classname = (String)tObj[0];
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, classname, tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM49605
	/**<en>
	 * Retrieve the location status.
	 * @return   :status of the location
	 </en>*/
	public int getPresence()
	{
		return wPresence;
	}

	//#CM49606
	/**<en>
	 * Set the hard zone.
	 * @param zone :hard zone
	 </en>*/
	public void setHardZone(int zone) 
	{
		wHardZone = zone;
	}

	//#CM49607
	/**<en>
	 * Retrieve the hard zone.
	 * @return   :hard zone
	 </en>*/
	public int getHardZone()
	{
		return wHardZone;
	}

	//#CM49608
	/**<en>
	 * Set the soft zone.
	 * @param zone :soft zone
	 </en>*/
	public void setSoftZone(int zone) 
	{
		wSoftZone = zone;
	}

	//#CM49609
	/**<en>
	 * Retrieve the soft zone.
	 * @return   :soft zone
	 </en>*/
	public int getSoftZone()
	{
		return wSoftZone;
	}
	
	//#CM49610
	/**<en>
	 * Set the inacceible flag.
	 * @param acs :true if setting the location inaccessible, or false if setting accessible.
	 </en>*/
	public void setAccessNgFlag(boolean acs)
	{
		wAccessNgFlag = acs ;
	}

	//#CM49611
	/**<en>
	 * Return whether/not this location is accessible.
	 * @return    true  :inaccesible
	 * @return    false :accessible
	 </en>*/
	public boolean isAccessNgFlag()
	{
		return(wAccessNgFlag) ;
	}

	//#CM49612
	/**<en>
	 * Sets the position of shelves.(front or rear)
	 * @param side :position of the shelves (front or rear)
	 * @throws InvalidStatusException :Notifies if contents of side was outside the range.
	 </en>*/
	public void setSide(int side) throws InvalidStatusException
	{
		//#CM49613
		//<en> Checking status</en>
		switch(side)
		{
			//#CM49614
			//<en> list of normal status</en>
			case Bank.NEAR:
			case Bank.FAR:
				wSide = side;
				break ;
				
			//#CM49615
			//<en> If the status other than normal was attempted to set, it lets occur the exception;</en>
			//<en> it will not change the status.</en>
			default:
				//#CM49616
				//<en> 6126009=Undefined {0} is set.</en>
				Object[] tObj = new Object[3] ;
				tObj[0] = this.getClass().getName() ;
				tObj[1] = "wSide";
				tObj[2] = Integer.toString(side) ;
				String classname = (String)tObj[0];
				RmiMsgLogClient.write(6126009, LogMessage.F_ERROR, classname, tObj);
				throw (new InvalidStatusException("6126009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM49617
	/**<en>
	 * Retrieves the position of the shelves. (front or rear).
	 * @return   position of the shelves (front or rear)
	 </en>*/
	public int getSide()
	{
		return wSide;
	}

	//#CM49618
	/**<en>
	 * Sets the priority.
	 * @param pty :priority
	 * @throws InvalidStatusException :Notifies if contents of pty was outside the range.
	 </en>*/
	public void setPriority(int pty) throws InvalidStatusException
	{
		wPriority = pty;
	}

	//#CM49619
	/**<en>
	 * Retrieves the priority.
	 * @return   priority
	 </en>*/
	public int getPriority()
	{
		return wPriority;
	}

	//#CM49620
	/**<en>
	 * Sets the station no.(location no.) of the paired shelves.
	 * @param pair :station no.(location no.) of the paired shelves
	 </en>*/
	public void setPairStationNumber(String pair)
	{
		wPairStationNumber = pair;
	}

	//#CM49621
	/**<en>
	 * Returns the station no.(location no.) of the paired shelves.
	 * @return station no.(location no.) of the paired shelves
	 </en>*/
	public String getPairStationNumber()
	{
		return wPairStationNumber;
	}

	//#CM49622
	// Package methods -----------------------------------------------

	//#CM49623
	// Protected methods ---------------------------------------------

	//#CM49624
	// Private methods -----------------------------------------------

}
//#CM49625
//end of class

