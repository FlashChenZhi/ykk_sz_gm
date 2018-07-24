// $Id: ToolZoneInformationSearchKey.java,v 1.2 2006/10/30 02:17:09 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM49292
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM49293
/**<en>
 * This key class is used to search the ZONEINFO table by using handler class and to generate
 * the instance of ZoneInformation class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:09 $
 * @author  $Author: suresh $
 </en>*/
public class ToolZoneInformationSearchKey extends ToolSQLSearchKey
{
	//#CM49294
	// Class fields --------------------------------------------------
	//#CM49295
	//<en> Define here the column which may be used as a search condition or the which may be sorted.</en>
	private static final String ZONEID  = "ZONEID";
	private static final String TYPE    = "TYPE";

	//#CM49296
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		ZONEID,
		TYPE
	};

	//#CM49297
	// Class method --------------------------------------------------
	//#CM49298
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date 
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM49299
	// Constructors --------------------------------------------------
	//#CM49300
	/**<en>
	 * Set the column definition.
	 </en>*/
	public ToolZoneInformationSearchKey()
	{
		setColumns(Columns);
	}

	//#CM49301
	// Public methods ------------------------------------------------

	//#CM49302
	/**<en>
	 * Set the search value of ZONEID.
	 * @param zid :zone ID to be searched
	 </en>*/
	public void setZoneID(int zid)
	{
		setValue(ZONEID, zid);
	}

	//#CM49303
	/**<en>
	 * Retrieve the search value of ZONEID.
	 * @return :zone ID
	 </en>*/
	public int getZoneID()
	{
		Integer intobj = (Integer)getValue(ZONEID);
		return (intobj.intValue());
	}

	//#CM49304
	/**<en>
	 * Set the sort order of ZONEID.
	 </en>*/
	public void setZoneIDOrder(int num, boolean bool)
	{
		setOrder(ZONEID, num, bool);
	}

	//#CM49305
	/**<en>
	 * Retrieve the sort order of ZONEID.
	 </en>*/
	public int getZoneIDOrder()
	{
		return (getOrder(ZONEID));
	}

	//#CM49306
	/**<en>
	 * Set the search value of TYPE.
	 * @param type :zone type to be sareched
	 </en>*/
	public void setType(int type)
	{
		setValue(TYPE, type);
	}

	//#CM49307
	/**<en>
	 * Retrieve the search value of the search value of TYPE.
	 * @return :zone type
	 </en>*/
	public int getType()
	{
		Integer intobj = (Integer)getValue(TYPE);
		return (intobj.intValue());
	}


	//#CM49308
	// Package methods -----------------------------------------------

	//#CM49309
	// Protected methods ---------------------------------------------

	//#CM49310
	// Private methods -----------------------------------------------

	//#CM49311
	// Inner Class ---------------------------------------------------

}
//#CM49312
//end of class

