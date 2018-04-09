// $Id: ToolAs21MachineSearchKey.java,v 1.2 2006/10/30 02:17:21 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47359
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM47360
/**<en>
 * This class sets the keys when seaerching the machine table.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/07/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:21 $
 * @author  $Author: suresh $
 </en>*/
public class ToolAs21MachineSearchKey extends ToolSQLSearchKey
{
	//#CM47361
	// Class fields --------------------------------------------------

	//#CM47362
	//<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
	private static final String STATIONNUMBER		= "STATIONNUMBER";
	private static final String MACHINETYPE			= "MACHINETYPE";
	private static final String MACHINENUMBER		= "MACHINENUMBER";
	private static final String STATUS			 	= "STATUS";
	private static final String CONTROLLERNUMBER	= "CONTROLLERNUMBER";

	//#CM47363
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		STATIONNUMBER,
		MACHINETYPE,
		MACHINENUMBER,
		STATUS,
		CONTROLLERNUMBER
	};

	//#CM47364
	// Class method --------------------------------------------------
	//#CM47365
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM47366
	// Constructors --------------------------------------------------
	//#CM47367
	/**<en>
	 * Default constructor
	 </en>*/
	public ToolAs21MachineSearchKey()
	{
		setColumns(Columns);
	}

	//#CM47368
	// Public methods ------------------------------------------------

	//#CM47369
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 </en>*/
	public void setStationNumber(String stno)
	{
		setValue(STATIONNUMBER, stno);
	}

	//#CM47370
	/**<en>
	 * Retrieve the STATIONNUMBER.
	 </en>*/
	public String getStationNumber()
	{
		return (String)getValue(STATIONNUMBER);
	}

	//#CM47371
	/**<en>
	 * Set the sort order of STATIONNUMBER.
	 </en>*/
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(STATIONNUMBER, num, bool);
	}

	//#CM47372
	/**<en>
	 * Retrieve the sort order of STATIONNUMBER.
	 </en>*/
	public int getStationNumberOrder()
	{
		return (getOrder(STATIONNUMBER));
	}

	//#CM47373
	/**<en>
	 * Set the search value of MACHINETYPE.
	 </en>*/
	public void setType(int type)
	{
		setValue(MACHINETYPE, type);
	}

	//#CM47374
	/**<en>
	 * Retrieve the search value of MACHINETYPE.
	 </en>*/
	public int getType()
	{
		Integer intobj = (Integer)getValue(MACHINETYPE);
		return(intobj.intValue());
	}

	//#CM47375
	/**<en>
	 * Set the search value of MACHINETYPE.
	 </en>*/
	public void setTypeOrder(int num, boolean bool)
	{
		setOrder(MACHINETYPE, num, bool);
	}

	//#CM47376
	/**<en>
	 * Retrieve the sort order of MACHINETYPE.
	 </en>*/
	public int getTypeOrder()
	{
		return (getOrder(MACHINETYPE));
	}

	//#CM47377
	/**<en>
	 * Set the search value of MACHINENUMBER.
	 </en>*/
	public void setNumber(int num)
	{
		setValue(MACHINENUMBER, num);
	}

	//#CM47378
	/**<en>
	 * Retrieve the search value of MACHINENUMBER.
	 </en>*/
	public int getNumber()
	{
		Integer intobj = (Integer)getValue(MACHINENUMBER);
		return(intobj.intValue());
	}

	//#CM47379
	/**<en>
	 * Set the sort order of MACHINENUMBER.
	 </en>*/
	public void setNumberOrder(int num, boolean bool)
	{
		setOrder(MACHINENUMBER, num, bool);
	}

	//#CM47380
	/**<en>
	 * Retrieve the sort order of MACHINENUMBER.
	 </en>*/
	public int getNumberOrder()
	{
		return (getOrder(MACHINENUMBER));
	}

	//#CM47381
	/**<en>
	 * Set the search value of STATUS.
	 </en>*/
	public void setStatus(int num)
	{
		setValue(STATUS, num);
	}

	//#CM47382
	/**<en>
	 * Retrieve the search value of STATUS.
	 </en>*/
	public int getStatus()
	{
		Integer intobj = (Integer)getValue(STATUS);
		return(intobj.intValue());
	}

	//#CM47383
	/**<en>
	 * Set the search value of CONTROLLERNUMBER.
	 </en>*/
	public void setControllerNumber(int num)
	{
		setValue(CONTROLLERNUMBER, num);
	}

	//#CM47384
	/**<en>
	 * Retrieve the search value of CONTROLLERNUMBER.
	 </en>*/
	public int getControllerNumber()
	{
		Integer intobj = (Integer)getValue(CONTROLLERNUMBER);
		return(intobj.intValue());
	}

	//#CM47385
	/**<en>
	 * Set the sort order of CONTROLLERNUMBER.
	 </en>*/
	public void setControllerNumberOrder(int num, boolean bool)
	{
		setOrder(CONTROLLERNUMBER, num, bool);
	}

	//#CM47386
	/**<en>
	 * Retrieve the sort order of CONTROLLERNUMBER.
	 </en>*/
	public int getControllerNumberOrder()
	{
		return (getOrder(CONTROLLERNUMBER));
	}


	//#CM47387
	// Package methods -----------------------------------------------

	//#CM47388
	// Protected methods ---------------------------------------------

	//#CM47389
	// Private methods -----------------------------------------------

	//#CM47390
	// Inner Class ---------------------------------------------------

}
//#CM47391
//end of class

