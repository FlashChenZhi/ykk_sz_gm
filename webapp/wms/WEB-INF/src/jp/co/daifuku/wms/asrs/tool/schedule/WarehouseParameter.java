// $Id: WarehouseParameter.java,v 1.2 2006/10/30 02:51:58 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.schedule ;
//#CM52046
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.Parameter;

//#CM52047
/**<en>
 * This entity class is used in warehouse settings.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:51:58 $
 * @author  $Author: suresh $
 </en>*/
public class WarehouseParameter extends Parameter
{
	//#CM52048
	// Class fields --------------------------------------------------

	//#CM52049
	// Class variables -----------------------------------------------
	//#CM52050
	/**<en>
	* Path of product number folder
	</en>*/
	String wFilePath = "";
	
    //#CM52051
    /**<en> Storage type </en>*/

    protected int wWarehouseNumber = 0;
    
    //#CM52052
    /**<en> Station no. </en>*/

    protected String wStationNumber = "";

    //#CM52053
    /**<en> Warehouse name</en>*/

    protected String wWarehouseName = "";

    //#CM52054
    /**<en> Zone type </en>*/

    protected int wZoneType = 0;

    //#CM52055
    /**<en> Max. mix-load quantity </en>*/

    protected int wMaxMixedQuantity = 0;
    
    //#CM52056
    /**<en> Warehouse type </en>*/

    protected int wWarehouseType = 0;

	//#CM52057
	/**<en> Operation type of automated warehouse </en>*/

	protected int wEploymentType = 0;


	//#CM52058
	// Class method --------------------------------------------------
	 //#CM52059
	 /**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:51:58 $") ;
	}

	//#CM52060
	// Constructors --------------------------------------------------
	//#CM52061
	/**<en>
	 * This constructor will be used.
	 * @param conn <CODE>Connection</CODE>
	 </en>*/
	public WarehouseParameter()   
	{
	}

	//#CM52062
	// Public methods ------------------------------------------------
	//#CM52063
	/**<en>
	* Set the path of product number folder.
	</en>*/
	public void setFilePath(String filepath)
	{
		wFilePath = filepath;
	}
	
	//#CM52064
	/**<en>
	* Return the path of product number folder.
	</en>*/
	public String getFilePath()
	{
		return wFilePath;
	}

	//#CM52065
	/**<en>
	 * Retrieve the storage type.
	 * @return wWarehouseNumber
	 </en>*/
	public int getWarehouseNumber()
	{
		return wWarehouseNumber;
	}
	//#CM52066
	/**<en>
	 * Set the storage type.
	 * @param WarehouseNumber
	 </en>*/
	public void setWarehouseNumber(int arg)
	{
		wWarehouseNumber = arg;
	}
	//#CM52067
	/**<en>
	 * Retrieve the station no.
	 * @return wStationNumber
	 </en>*/
	public String getStationNumber()
	{
		return wStationNumber;
	}
	//#CM52068
	/**<en>
	 * Set the station no.
	 * @param StationNumber
	 </en>*/
	public void setStationNumber(String arg)
	{
		wStationNumber = arg;
	}

	//#CM52069
	/**<en>
	 * Retrieve the warehouse name.
	 * @return wWarehouseName
	 </en>*/
	public String getWarehouseName()
	{
		return wWarehouseName;
	}
	//#CM52070
	/**<en>
	 * Set the warehouse name.
	 * @param WarehouseName
	 </en>*/
	public void setWarehouseName(String arg)
	{
		wWarehouseName = arg;
	}
	//#CM52071
	/**<en>
	 * Retrieve the zone type.
	 * @return wZoneType
	 </en>*/
	public int getZoneType()
	{
		return wZoneType;
	}
	//#CM52072
	/**<en>
	 * Set the zone type.
	 * @param ZoneType
	 </en>*/
	public void setZoneType(int arg)
	{
		wZoneType = arg;
	}

	//#CM52073
	/**<en>
	 * Retrieve the max. mix-load quantity.
	 * @return wMaxMixedQuantity
	 </en>*/
	public int getMaxMixedQuantity()
	{
		return wMaxMixedQuantity;
	}
	//#CM52074
	/**<en>
	 *Set the max. mix-load quantity.
	 * @param max. mix-load quantity
	 </en>*/
	public void setMaxMixedQuantity(int arg)
	{
		wMaxMixedQuantity = arg;
	}

	//#CM52075
	/**<en>
	 * Retrieve the warehouse type.
	 * @return wWarehouseType
	 </en>*/
	public int getWarehouseType()
	{
		return wWarehouseType ;
	}
	//#CM52076
	/**<en>
	 * Set the warehouse type.
	 * @param arg warehouse type
	 </en>*/
	public void setWarehouseType(int arg)
	{
		wWarehouseType = arg;
	}

	//#CM52077
	/**<en>
	 * Retrieve the operation type of automated warehouse.
	 * @return wAutoWarehouseType
	 </en>*/
	public int getEmploymentType()
	{
		return wEploymentType ;
	}
	//#CM52078
	/**<en>
	 * Set the operation type of automated warehouse.
	 * @param arg operation type of automated warehouse
	 </en>*/
	public void setEmploymentType(int arg)
	{
		wEploymentType = arg;
	}

	//#CM52079
	// Package methods -----------------------------------------------

	//#CM52080
	// Protected methods ---------------------------------------------

	//#CM52081
	// Private methods -----------------------------------------------

	
}
//#CM52082
//end of class
