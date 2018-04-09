// $Id: ToolBankSearchKey.java,v 1.2 2006/10/30 02:17:20 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47547
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM47548
/**<en>
 * This class sets the keys to search Bank table.
 *
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>P. Jain</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:20 $
 * @author  $Author: suresh $
 </en>*/
public class ToolBankSearchKey extends ToolSQLSearchKey
{
	//#CM47549
	// Class fields --------------------------------------------------
	//#CM47550
	//<en> Define here the column which may be used as a search condition or the which may be sorted.</en> 
	private static final String WHSTATIONNUMBER = "WHSTATIONNUMBER";
	private static final String AISLESTATIONNUMBER = "AISLESTATIONNUMBER";
	private static final String NBANK = "NBANK";

	//#CM47551
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		WHSTATIONNUMBER,
		AISLESTATIONNUMBER,
		NBANK
	};

	//#CM47552
	// Class method --------------------------------------------------
	//#CM47553
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM47554
	// Constructors --------------------------------------------------
	//#CM47555
	/**<en>
	 * Default constructor
	 </en>*/
	public ToolBankSearchKey()
	{
		setColumns(Columns);
	}

	//#CM47556
	// Public methods ------------------------------------------------

	//#CM47557
	/**<en>
	 * Set the search value of WHSTATIONNUMBER.
	 * @param st WHSTATIONNUMBER
	 </en>*/
	public void setWareHouseStationNumber(String st)
	{
		setValue(WHSTATIONNUMBER, st);
	}

	//#CM47558
	/**<en>
	 * Retrieve the WHSTATIONNUMBER.
	 * @return WHSTATIONNUMBER
	 </en>*/
	public String getWareHouseStationNumber()
	{
		return (String)getValue(WHSTATIONNUMBER);
	}

	//#CM47559
	/**<en>
	 * Set the sort order of WHSTATIONNUMBER.
	 </en>*/
	public void setWareHouseStationNumberOrder(int num, boolean bool)
	{
		setOrder(WHSTATIONNUMBER, num, bool);
	}

	//#CM47560
	/**<en>
	 * Retrieve the sort order of WHSTATIONNUMBER.
	 </en>*/
	public int getWareHouseStationNumberOrder()
	{
		return (getOrder(WHSTATIONNUMBER));
	}

	//#CM47561
	/**<en>
	 * Set the search value of AISLESTATIONNUMBER.
	 * @param st AISLESTATIONNUMBER
	 </en>*/
	public void setAisleStationNumber(String st)
	{
		setValue(AISLESTATIONNUMBER, st);
	}

	//#CM47562
	/**<en>
	 * Retrieve the AISLESTATIONNUMBER.
	 * @return AISLESTATIONNUMBER
	 </en>*/
	public String getAisleStationNumber()
	{
		return (String)getValue(AISLESTATIONNUMBER);
	}

	//#CM47563
	/**<en>
	 * Set the sort order of AISLESTATIONNUMBER.
	 </en>*/
	public void setAisleStationNumberOrder(int num, boolean bool)
	{
		setOrder(AISLESTATIONNUMBER, num, bool);
	}

	//#CM47564
	/**<en>
	 * Retrieve the sort order of AISLESTATIONNUMBER.
	 </en>*/
	public int getAisleStationNumberOrder()
	{
		return (getOrder(AISLESTATIONNUMBER));
	}

	//#CM47565
	/**<en>
	 * Set the sort order of NBANK.
	 </en>*/
	public void setBankOrder(int num, boolean bool)
	{
		setOrder(NBANK, num, bool);
	}

	//#CM47566
	/**<en>
	 * Retrieve the sort order of NBANK.
	 </en>*/
	public int getBankOrder()
	{
		return (getOrder(NBANK));
	}

	//#CM47567
	// Package methods -----------------------------------------------

	//#CM47568
	// Protected methods ---------------------------------------------

	//#CM47569
	// Private methods -----------------------------------------------

	//#CM47570
	// Inner Class ---------------------------------------------------

}
//#CM47571
//end of class

