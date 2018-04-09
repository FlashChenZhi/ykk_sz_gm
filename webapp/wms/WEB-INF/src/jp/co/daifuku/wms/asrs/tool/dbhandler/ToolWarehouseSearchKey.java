// $Id: ToolWarehouseSearchKey.java,v 1.2 2006/10/30 02:17:10 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM49115
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM49116
/**<en>
 * This class is used to set keys for Warehouse table search.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/08</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:10 $
 * @author  $Author: suresh $
 </en>*/
public class ToolWarehouseSearchKey extends ToolSQLSearchKey
{
	//#CM49117
	// Class fields --------------------------------------------------
	//#CM49118
	//<en> Define here the column which may be used as a search condition or the which may be sorted.</en>
	private static final String WAREHOUSENUMBER = "WAREHOUSENUMBER";
	private static final String WAREHOUSETYPE = "WAREHOUSETYPE";
	private static final String STATIONNUMBER = "STATIONNUMBER";
	private static final String ZONETYPE      = "ZONETYPE";

	//#CM49119
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		WAREHOUSENUMBER,
		WAREHOUSETYPE,
		STATIONNUMBER,
		ZONETYPE
	};

	//#CM49120
	// Class method --------------------------------------------------
	//#CM49121
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM49122
	// Constructors --------------------------------------------------
	//#CM49123
	/**<en>
	 * Default constructor
	 </en>*/
	public ToolWarehouseSearchKey()
	{
		setColumns(Columns);
	}

	//#CM49124
	// Public methods ------------------------------------------------

	//#CM49125
	/**<en>
	 * Set the search value of WAREHOUSENUMBER.
	 </en>*/
	public void setWarehouseNumber(int whnum)
	{
		setValue(WAREHOUSENUMBER, whnum);
	}

	//#CM49126
	/**<en>
	 * Retrieve the WAREHOUSENUMBER.
	 </en>*/
	public String getWarehouseNumber()
	{
		return (String)getValue(WAREHOUSENUMBER);
	}
	//#CM49127
	/**<en>
	 * Set the sort order of WAREHOUSENUMBER.
	 </en>*/
	public void setWarehouseNumberOrder(int num, boolean bool)
	{
		setOrder(WAREHOUSENUMBER, num, bool);
	}

	//#CM49128
	/**<en>
	 * Retrieve the sort order of WAREHOUSENUMBER.
	 </en>*/
	public int getWarehouseNumberOrder()
	{
		return (getOrder(WAREHOUSENUMBER));
	}
	//#CM49129
	/**<en>
	 * Set the sort order of WAREHOUSENUMBER.
	 * @deprecated :please use setWarehouseNumberOrder.
	 </en>*/
	public void setNumberOrder(int num, boolean bool)
	{
		setOrder(WAREHOUSENUMBER, num, bool);
	}

	//#CM49130
	/**<en>
	 * Retrieve the sort order of WAREHOUSENUMBER.
	 * @deprecated :please use getWarehouseNumberOrder.
	 </en>*/
	public int getNumberOrder()
	{
		return (getOrder(WAREHOUSENUMBER));
	}
	


	//#CM49131
	/**<en>
	 * Set the search value of WAREHOUSETYPE.
	 </en>*/
	public void setWarehouseType(int arg)
	{
		setValue(WAREHOUSETYPE, arg);
	}

	//#CM49132
	/**<en>
	 * Retrieve the search value of WAREHOUSETYPE.
	 </en>*/
	public int getWarehouseType()
	{
		Integer intobj = (Integer)getValue(WAREHOUSETYPE);
		return(intobj.intValue());
	}

	//#CM49133
	/**<en>
	 * Set the sort order of WAREHOUSETYPE.
	 </en>*/
	public void setWarehouseTypeOrder(int num, boolean bool)
	{
		setOrder(WAREHOUSETYPE, num, bool);
	}

	//#CM49134
	/**<en>
	 * Retrieve the sort order of WAREHOUSETYPE.
	 </en>*/
	public int getWarehouseTypeOrder()
	{
		return (getOrder(WAREHOUSETYPE));
	}



	//#CM49135
	/**<en>
	 * Set the search value of STATIONNUMBER (warehouse station no.).
	 </en>*/
	public void setWarehouseStationNumber(String whnum)
	{
		setValue(STATIONNUMBER, whnum);
	}

	//#CM49136
	/**<en>
	 * Retrieve the STATIONNUMBER(warehouse station no.).
	 </en>*/
	public String getWarehouseStationNumber()
	{
		return (String)getValue(STATIONNUMBER);
	}

	//#CM49137
	/**<en>
	 * Set the sort order of STATIONNUMBER(warehouse station no.).
	 </en>*/
	public void setWarehouseStationNumberOrder(int num, boolean bool)
	{
		setOrder(STATIONNUMBER, num, bool);
	}

	//#CM49138
	/**<en>
	 * Retrieve the sort order of STATIONNUMBER(warehouse station no.).
	 </en>*/
	public int getWarehouseStationNumberOrder()
	{
		return (getOrder(STATIONNUMBER));
	}

	//#CM49139
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 * @deprecated :please use setWarehouseStationNumber.
	 </en>*/
	public void setWHStationNumber(String whnum)
	{
		setWarehouseStationNumber(whnum);
	}

	//#CM49140
	/**<en>
	 * Retrieve the STATIONNUMBER.
	 * @deprecated :please use getWarehouseStationNumber.
	 </en>*/
	public String getWHStationNumber()
	{
		return (getWarehouseStationNumber());
	}

	//#CM49141
	/**<en>
	 * Set the srot order of STATIONNUMBER.
	 * @deprecated :please use setWarehouseStationNumberOrder.
	 </en>*/
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setWarehouseStationNumberOrder(num, bool);
	}

	//#CM49142
	/**<en>
	 * Retrieve the sort order of STATIONNUMBER.
	 * @deprecated :please use getWarehouseStationNumberOrder.
	 </en>*/
	public int getWHStationNumberOrder()
	{
		return (getWarehouseStationNumberOrder());
	}

	//#CM49143
	/**<en>
	 * Set the search value of ZONETYPE.
	 </en>*/
	public void setZoneType(int arg)
	{
		setValue(ZONETYPE, arg);
	}

	//#CM49144
	/**<en>
	 * Retrieve the search value of ZONETYPE.
	 </en>*/
	public int getZoneType()
	{
		Integer intobj = (Integer)getValue(ZONETYPE);
		return(intobj.intValue());
	}

	//#CM49145
	/**<en>
	 * Set the search value of ZONETYPE.
	 * Each aone type passed through parameter will be connected by placing OR in between.
	 </en>*/
	public void setZoneType(int[] arg)
	{
		setValue(ZONETYPE, arg);
	}


	//#CM49146
	/**<en>
	 * Set the sort order of ZONETYPE.
	 </en>*/
	public void setZoneTypeOrder(int num, boolean bool)
	{
		setOrder(ZONETYPE, num, bool);
	}

	//#CM49147
	/**<en>
	 * Retrieve the sort order of ZONETYPE.
	 </en>*/
	public int getZoneTypeOrder()
	{
		return (getOrder(ZONETYPE));
	}

	//#CM49148
	// Package methods -----------------------------------------------

	//#CM49149
	// Protected methods ---------------------------------------------

	//#CM49150
	// Private methods -----------------------------------------------

	//#CM49151
	// Inner Class ---------------------------------------------------

}
//#CM49152
//end of class

