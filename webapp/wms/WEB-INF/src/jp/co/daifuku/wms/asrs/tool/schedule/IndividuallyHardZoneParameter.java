// $Id: IndividuallyHardZoneParameter.java,v 1.2 2006/10/30 02:52:03 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;
//#CM51128
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

//#CM51129
/**<en>
 * This is an entity class which is used in individual hard zone setting.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/28</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:52:03 $
 * @author  $Author: suresh $
 </en>*/
public class IndividuallyHardZoneParameter extends Parameter
{
	//#CM51130
	// Class fields --------------------------------------------------

	//#CM51131
	// Class variables -----------------------------------------------

	//#CM51132
	/**<en>
	* Path of the product number folder
	</en>*/
	String wFilePath = "";
	
    //#CM51133
    /**<en> storage type </en>*/

    protected int wWarehouseNumber = 0;

	//#CM51134
	/**<en> zone no. </en>*/

	protected int wZoneID ;

    //#CM51135
    /**<en> BANK </en>*/

    protected int wBank = 0;

    //#CM51136
    /**<en> BAY </en>*/

    protected int wBay = 0;

    //#CM51137
    /**<en> LEVEL </en>*/

    protected int wLevel = 0;

	//#CM51138
	// Class method --------------------------------------------------
	 //#CM51139
	 /**<en>
	 * Return the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:52:03 $") ;
	}

	//#CM51140
	// Constructors --------------------------------------------------
	//#CM51141
	/**<en>
	 * This constructor will be used.
	 * @param conn <CODE>Connection</CODE>
	 </en>*/
	public IndividuallyHardZoneParameter()   
	{
	}

	//#CM51142
	// Public methods ------------------------------------------------
	//#CM51143
	/**<en>
	* Set the path of the product number folder.
	</en>*/
	public void setFilePath(String filepath)
	{
		wFilePath = filepath;
	}
	
	//#CM51144
	/**<en>
	* Return the path of the product number folder.
	</en>*/
	public String getFilePath()
	{
		return wFilePath;
	}
	
	//#CM51145
	/**<en>
	 * Retrieve the storage type.
	 * @return wWarehouseNumber
	 </en>*/
	public int getWarehouseNumber()
	{
		return wWarehouseNumber;
	}
	//#CM51146
	/**<en>
	 * Set the storage type.
	 * @param WarehouseNumber
	 </en>*/
	public void setWarehouseNumber(int arg)
	{
		wWarehouseNumber = arg;
	}

	//#CM51147
	/**<en>
	 * Set teh zone no.
	 * @param zid   :zone no. to set
	 </en>*/
	public void setZoneID(int zid)
	{
		wZoneID = zid ;
	}

	//#CM51148
	/**<en>
	 * Retrieve the zone no.
	 * @return    zone no.
	 </en>*/
	public int getZoneID()
	{
		return wZoneID ;
	}

	//#CM51149
	/**<en>
	 * Retrieve the BANK.
	 * @return wBank
	 </en>*/
	public int getBank()
	{
		return wBank;
	}
	//#CM51150
	/**<en>
	 * Set the BANK.
	 * @param Bank
	 </en>*/
	public void setBank(int arg)
	{
		wBank = arg;
	}

	//#CM51151
	/**<en>
	 * Retrieve the BAY.
	 * @return wBay
	 </en>*/
	public int getBay()
	{
		return wBay;
	}
	//#CM51152
	/**<en>
	 * Set the BAY.
	 * @param Bay
	 </en>*/
	public void setBay(int arg)
	{
		wBay = arg;
	}
	//#CM51153
	/**<en>
	 * Retrieve the Level.
	 * @return wLevel
	 </en>*/
	public int getLevel()
	{
		return wLevel;
	}
	//#CM51154
	/**<en>
	 * Set the Level.
	 * @param Level
	 </en>*/
	public void setLevel(int arg)
	{
		wLevel = arg;
	}
	//#CM51155
	// Package methods -----------------------------------------------

	//#CM51156
	// Protected methods ---------------------------------------------

	//#CM51157
	// Private methods -----------------------------------------------

	
}
//#CM51158
//end of class
