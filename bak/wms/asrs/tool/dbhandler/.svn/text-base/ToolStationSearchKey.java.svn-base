// $Id: ToolStationSearchKey.java,v 1.2 2006/10/30 02:17:13 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48764
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.communication.as21.GroupController;
//#CM48765
/**<en>
 * This class specifies the keys for station search by using station no. or the station ID
 * as keys.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:13 $
 * @author  $Author: suresh $
 </en>*/
public class ToolStationSearchKey extends ToolSQLSearchKey
{
	//#CM48766
	// Class fields --------------------------------------------------
	//#CM48767
	//<en> Define here the column which may be used as a search condition or the which may be sorted.</en>
	private static final String STATIONNUMBER       = "STATIONNUMBER";
	private static final String STATIONTYPE         = "STATIONTYPE";
	private static final String CONTROLLERNUMBER    = "CONTROLLERNUMBER";
	private static final String WHSTATIONNUMBER     = "WHSTATIONNUMBER";
	private static final String SENDABLE            = "SENDABLE";
	private static final String PARENTSTATIONNUMBER = "PARENTSTATIONNUMBER";
	private static final String STATUS              = "STATUS";
	private static final String AISLESTATIONNUMBER  = "AISLESTATIONNUMBER";
	private static final String MODETYPE            = "MODETYPE";
	private static final String WORKPLACETYPE       = "WORKPLACETYPE";

	//#CM48768
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		STATIONNUMBER,
		STATIONTYPE,
		CONTROLLERNUMBER,
		WHSTATIONNUMBER,
		SENDABLE,
		PARENTSTATIONNUMBER,
		STATUS,
		AISLESTATIONNUMBER,
		MODETYPE,
		WORKPLACETYPE
	};

	//#CM48769
	// Class method --------------------------------------------------
	//#CM48770
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM48771
	// Constructors --------------------------------------------------
	//#CM48772
	/**<en>
	 * Default constructor
	 </en>*/
	public ToolStationSearchKey()
	{
		setColumns(Columns);
	}

	//#CM48773
	// Public methods ------------------------------------------------

	//#CM48774
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 </en>*/
	public void setNumber(String stnum)
	{
		setValue(STATIONNUMBER, stnum);
	}

	//#CM48775
	/**<en>
	 * Retrieve the STATIONNUMBER.
	 </en>*/
	public String getNumber()
	{
		return (String)getValue(STATIONNUMBER);
	}

	//#CM48776
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 * Connect each station no. passed through parameter by placing OR in between.
	 </en>*/
	public void setNumber(String[] stnums)
	{
		setValue(STATIONNUMBER, stnums);
	}

	//#CM48777
	/**<en>
	 * Set the order of sorting STATIONNUMBER.
	 </en>*/
	public void setNumberOrder(int num, boolean bool)
	{
		setOrder(STATIONNUMBER, num, bool);
	}

	//#CM48778
	/**<en>
	 * Retrieve the order of sorting STATIONNUMBER.
	 </en>*/
	public int getNumberOrder()
	{
		return (getOrder(STATIONNUMBER));
	}

	//#CM48779
	/**<en>
	 * Set the search value of STATIONTYPE.
	 </en>*/
	public void setStationType(int type)
	{
		setValue(STATIONTYPE, type);
	}

	//#CM48780
	/**<en>
	 * Set the search value of STATIONTYPE.
	 * Connect each station type passed through parameter by placing OR in between.
	 </en>*/
	public void setStationType(int[] types)
	{
		setValue(STATIONTYPE, types);
	}


	//#CM48781
	/**<en>
	 * Retrieve the search value of STATIONTYPE.
	 </en>*/
	public int getStationType()
	{
		Integer intobj = (Integer)getValue(STATIONTYPE);
		return(intobj.intValue());
	}

	//#CM48782
	/**<en>
	 * Set the search value of CONTROLLERNUMBER.
	 </en>*/
	public void setController(GroupController gcon)
	{
		setControllerNumber(gcon.getNumber());
	}

	//#CM48783
	/**<en>
	 * Set the search value of CONTROLLERNUMBER.
	 </en>*/
	public void setControllerNumber(int num)
	{
		setValue(CONTROLLERNUMBER, num);
	}

	//#CM48784
	/**<en>
	 * Retrieve the search value of CONTROLLERNUMBER.
	 </en>*/
	public int getControllerNumber()
	{
		Integer intobj = (Integer)getValue(CONTROLLERNUMBER);
		return(intobj.intValue());
	}

	//#CM48785
	/**<en>
	 * Set the search value of WHSTATIONNUMBER.
	 * @deprecated :please use setWareHouseStationNumber.
	 </en>*/
	public void setWHStationNumber(String WHSTNumber)
	{
		setValue(WHSTATIONNUMBER, WHSTNumber);
	}

	//#CM48786
	/**<en>
	 * Retrieve the search value of WHSTATIONNUMBER.
	 * @deprecated :please use getWareHouseStationNumber.
	 </en>*/
	public String getWHStationNumber()
	{
		return(String)getValue(WHSTATIONNUMBER);
	}

	//#CM48787
	/**<en>
	 * Set the search value of WHSTATIONNUMBER.
	 </en>*/
	public void setWareHouseStationNumber(String WHSTNumber)
	{
		setValue(WHSTATIONNUMBER, WHSTNumber);
	}

	//#CM48788
	/**<en>
	 * Retrieve the search value of WHSTATIONNUMBER.
	 </en>*/
	public String getWareHouseStationNumber()
	{
		return(String)getValue(WHSTATIONNUMBER);
	}

	//#CM48789
	/**<en>
	 * Set the order of sorting WHSTATIONNUMBER.
	 </en>*/
	public void setWareHouseStationNumberOrder(int num, boolean bool)
	{
		setOrder(WHSTATIONNUMBER, num, bool);
	}

	//#CM48790
	/**<en>
	 * Retrieve the order of sorting WHSTATIONNUMBER.
	 </en>*/
	public int getWareHouseStationNumberOrder()
	{
		return (getOrder(WHSTATIONNUMBER));
	}

	//#CM48791
	/**<en>
	 * Set the search value of SENDABLE.
	 </en>*/
	public void setSendable(int sendable)
	{
		setValue(SENDABLE, sendable);
	}

	//#CM48792
	/**<en>
	 * Retrieve the search value of SENDABLE.
	 </en>*/
	public int getSendable()
	{
		Integer intobj = (Integer)getValue(SENDABLE);
		return(intobj.intValue());
	}

	//#CM48793
	/**<en>
	 * Set the search value of PARENTSTATIONNUMBER.
	 </en>*/
	public void setParentStationNumber(String pstnum)
	{
		setValue(PARENTSTATIONNUMBER, pstnum);
	}

	//#CM48794
	/**<en>
	 * Retrieve the search value of PARENTSTATIONNUMBER.
	 </en>*/
	public String getParentStationNumber()
	{
		return(String)getValue(PARENTSTATIONNUMBER);
	}

	//#CM48795
	/**<en>
	 * Set the search value of STATUS.
	 </en>*/
	public void setStatus(int stat)
	{
		setValue(STATUS, stat);
	}

	//#CM48796
	/**<en>
	 * Retrieve the search value of STATUS.
	 </en>*/
	public int getStatus()
	{
		Integer intobj = (Integer)getValue(STATUS);
		return(intobj.intValue());
	}

	//#CM48797
	/**<en>
	 * Set the search value of AISLESTATIONNUMBER.
	 </en>*/
	public void setAisleStationNumber(String aile)
	{
		setValue(AISLESTATIONNUMBER, aile);
	}

	//#CM48798
	/**<en>
	 * Retrieve the search value of AISLESTATIONNUMBER.
	 </en>*/
	public String getAisleStationNumber()
	{
		return(String)getValue(AISLESTATIONNUMBER);
	}

	//#CM48799
	/**<en>
	 * Set the order of sorting AISLESTATIONNUMBER.
	 </en>*/
	public void setAisleStationNumberOrder(int num, boolean bool)
	{
		setOrder(AISLESTATIONNUMBER, num, bool);
	}

	//#CM48800
	/**<en>
	 * Retrieve the order of sorting AISLESTATIONNUMBER.
	 </en>*/
	public int getAisleStationNumberOrder()
	{
		return (getOrder(AISLESTATIONNUMBER));
	}

	//#CM48801
	/**<en>
	 * Set the search value of MODETYPE.
	 </en>*/
	public void setModeType(int type)
	{
		setValue(MODETYPE, type);
	}

	//#CM48802
	/**<en>
	 * Set the search value of MODETYPE.
	 </en>*/
	public int getModeType()
	{
		Integer intobj = (Integer)getValue(MODETYPE);
		return(intobj.intValue());
	}

	//#CM48803
	/**<en>
	 * Set the search value of MODETYPE.
	 * Connect each mode type passed through parameter by placing OR in between.
	 </en>*/
	public void setModeType(int[] type)
	{
		setValue(MODETYPE, type);
	}

	//#CM48804
	/**<en>
	 * Set the search value of WORKPLACETYPE.
	 * @param type :zone type to be searched
	 </en>*/
	public void setWorkPlaceType(int type)
	{
		setValue(WORKPLACETYPE, type);
	}

	//#CM48805
	/**<en>
	 * Retrieve the search value of WORKPLACETYPE.
	 * @return :zone type
	 </en>*/
	public int getWorkPlaceType()
	{
		Integer intobj = (Integer)getValue(WORKPLACETYPE);
		return (intobj.intValue());
	}

	//#CM48806
	/**<en>
	 * Set the search value of WORKPLACETYPE.
	 * Connect each station type passed through parameter by placing OR in between.
	 * @param type :zone type to be searched
	 </en>*/
	public void setWorkPlaceType(int[] type)
	{
		setValue(WORKPLACETYPE, type);
	}

	//#CM48807
	// Package methods -----------------------------------------------

	//#CM48808
	// Protected methods ---------------------------------------------

	//#CM48809
	// Private methods -----------------------------------------------

	//#CM48810
	// Inner Class ---------------------------------------------------

}
//#CM48811
//end of class

