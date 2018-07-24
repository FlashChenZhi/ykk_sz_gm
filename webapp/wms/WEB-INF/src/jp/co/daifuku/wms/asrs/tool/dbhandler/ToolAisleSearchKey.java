// $Id: ToolAisleSearchKey.java,v 1.2 2006/10/30 02:17:22 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;
//#CM47273
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM47274
/**<en>
 * This key class is used when searching the AISLE table using the handler and when generating
 * the instance of Aisle class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:22 $
 * @author  $Author: suresh $
 </en>*/
public class ToolAisleSearchKey extends ToolSQLSearchKey
{
	//#CM47275
	// Class fields --------------------------------------------------
	//#CM47276
	//<en> Define here the column which may be used as a search condition or the which may be sorted.</en>
	private static final String STATIONNUMBER   = "STATIONNUMBER";
	private static final String WHSTATIONNUMBER = "WHSTATIONNUMBER";
	private static final String AISLENUMBER      = "AISLENUMBER";
	private static final String CONTROLLERNUMBER  = "CONTROLLERNUMBER";

	//#CM47277
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		STATIONNUMBER,
		WHSTATIONNUMBER,
		AISLENUMBER,
		CONTROLLERNUMBER
	};

	//#CM47278
	// Class method --------------------------------------------------
	//#CM47279
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM47280
	// Constructors --------------------------------------------------
	//#CM47281
	/**<en>
	 * Set the column definition.
	 </en>*/
	public ToolAisleSearchKey()
	{
		setColumns(Columns);
	}

	//#CM47282
	// Public methods ------------------------------------------------
	//#CM47283
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 * @param stnum :the search value of STATIONNUMBER
	 </en>*/
	public void setNumber(String stnum)
	{
		setValue(STATIONNUMBER, stnum);
	}

	//#CM47284
	/**<en>
	 * Retrieve the STATIONNUMBER.
	 * @return STATIONNUMBER
	 </en>*/
	public String getNumber()
	{
		return (String)getValue(STATIONNUMBER);
	}

	//#CM47285
	/**<en>
	 * Set the sort order of STATIONNUMBER.
	 * @param num  :priority in sort order
	 * @param bool :ascending order if specified true
	 </en>*/ 
	public void setNumberOrder(int num, boolean bool)
	{
		setOrder(STATIONNUMBER, num, bool);
	}

	//#CM47286
	/**<en>
	 * Retrieve the sort order of STATIONNUMBER
	 * @return :the sort order of STATIONNUMBER
	 </en>*/
	public int getNumberOrder()
	{
		return (getOrder(STATIONNUMBER));
	}

//	/**<jp>CMENJP4977#CM
//#CM47287
//	 *The retrieval value of WHSTATIONNUMBER is set. 
//#CM47288
//	 * @deprecated Use setWareHouseStationNumber.
//#CM47289
//	 * @param WHSTNumber Retrieval value of WHSTATIONNUMBER
//#CM47290
//	 </en>*/ 
//#CM47291
//	public void setWHStationNumber(String WHSTNumber)
//#CM47292
//	{
//#CM47293
//		setValue(WHSTATIONNUMBER, WHSTNumber);
//#CM47294
//	}
//#CM47295
//
//	/**<jp>CMENJP4978#CM
//#CM47296
//	 * The retrieval value of WHSTATIONNUMBER is acquired. 
//#CM47297
//	 * @deprecated Use getWareHouseStationNumber. .
//#CM47298
//	 * @return WHSTATIONNUMBER
//#CM47299
//	 </en>*/
//#CM47300
//	public String getWHStationNumber()
//#CM47301
//	{
//#CM47302
//		return(String)getValue(WHSTATIONNUMBER);
//#CM47303
//	}


	//#CM47304
	/**<en>
	 * Set the search value of WHSTATIONNUMBER.
	 * @param WHSTNumber :the search value of WHSTATIONNUMBER
	 </en>*/ 
	public void setWareHouseStationNumber(String WHSTNumber)
	{
		setValue(WHSTATIONNUMBER, WHSTNumber);
	}

	//#CM47305
	/**<en>
	 * Retrieve the search value of WHSTATIONNUMBER.
	 * @return WHSTATIONNUMBER
	 </en>*/
	public String getWareHouseStationNumber()
	{
		return(String)getValue(WHSTATIONNUMBER);
	}
	//#CM47306
	/**<en>
	 * Set the search value of WHSTATIONNUMBER.
	 * @param num :priority in sort order
	 * @param bool :ascending order if specified true
	 </en>*/ 
	public void setWareHouseStationNumberOrder(int num, boolean bool)
	{
		setOrder(WHSTATIONNUMBER, num, bool);
	}

	//#CM47307
	/**<en>
	 * Set the search value of AISLENUMBER.
	 * @param AlNumber :the search value of AISLENUMBER
	 </en>*/ 
	public void setAisleNumber(String AlNumber)
	{
		setValue(AISLENUMBER, AlNumber);
	}

	//#CM47308
	/**<en>
	 * Retrieve the search value of AISLENUMBER.
	 * @return AISLENUMBER
	 </en>*/
	public String getAisleNumber()
	{
		return(String)getValue(AISLENUMBER);
	}

	//#CM47309
	/**<en>
	 * Retrieve the search value of WHSTATIONNUMBER.
	 * @return :the search value of WHSTATIONNUMBER
	 </en>*/
	public int getWareHouseStationNumberOrder()
	{
		return (getOrder(WHSTATIONNUMBER));


	}
	//#CM47310
	/**<en>
	 * Set the sort order of AISLENUMBER.
	 * @param num  :priority in sort order
	 * @param bool :ascending order if specified true
	 </en>*/ 
	public void setAisleNumberOrder(int num, boolean bool)
	{
		setOrder(AISLENUMBER, num, bool);
	}

	//#CM47311
	/**<en>
	 * Retrieve the sort order of AISLENUMBER.
	 * @return :the sort order of AISLENUMBER
	 </en>*/
	public int getAisleNumberOrder()
	{
		return (getOrder(AISLENUMBER));
	}

	//#CM47312
	/**<en>
	 * Set the search value of CONTROLLERNUMBER.
	 </en>*/
	public void setControllerNumber(int num)
	{
		setValue(CONTROLLERNUMBER, num);
	}

	//#CM47313
	/**<en>
	 * Retrieve the search value of CONTROLLERNUMBER.
	 </en>*/
	public int getControllerNumber()
	{
		Integer intobj = (Integer)getValue(CONTROLLERNUMBER);
		return(intobj.intValue());
	}


	//#CM47314
	// Package methods -----------------------------------------------

	//#CM47315
	// Protected methods ---------------------------------------------

	//#CM47316
	// Private methods -----------------------------------------------

	//#CM47317
	// Inner Class ---------------------------------------------------

}
//#CM47318
//end of class

