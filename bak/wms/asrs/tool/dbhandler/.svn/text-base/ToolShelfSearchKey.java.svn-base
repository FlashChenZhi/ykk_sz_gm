// $Id: ToolShelfSearchKey.java,v 1.2 2006/10/30 02:17:15 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48158
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM48159
/**<en>
 * This class sets the keys when searching the Shelf table.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2003/05/20</TD><TD>kawashima</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:15 $
 * @author  $Author: suresh $
 </en>*/
public class ToolShelfSearchKey extends ToolSQLSearchKey
{
	//#CM48160
	// Class fields --------------------------------------------------
	//#CM48161
	//<en> Define here the column which may be used as a search condition or the which may be sorted.</en>
	private static final String STATIONNUMBER		= "STATIONNUMBER";
	private static final String NBANK				= "NBANK";
	private static final String NBAY					= "NBAY";
	private static final String NLEVEL				= "NLEVEL";
	private static final String WHSTATIONNUMBER		= "WHSTATIONNUMBER";
	private static final String STATUS				= "STATUS";
	private static final String PRESENCE				= "PRESENCE";
	private static final String HARDZONEID				= "HARDZONEID";
	private static final String SOFTZONEID				= "SOFTZONEID";
	private static final String PARENTSTATIONNUMBER	= "PARENTSTATIONNUMBER";
	private static final String ACCESSNGFLAG			= "ACCESSNGFLAG";
	
	
	
	//#CM48162
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		STATIONNUMBER,
		PRESENCE,
		NBANK,
		NBAY,
		NLEVEL,
		STATUS,
		HARDZONEID,
		SOFTZONEID,
		WHSTATIONNUMBER,
		PARENTSTATIONNUMBER,
		ACCESSNGFLAG
	};

	//#CM48163
	// Class method --------------------------------------------------
	//#CM48164
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM48165
	// Constructors --------------------------------------------------
	//#CM48166
	/**<en>
	 * Default constructor
	 </en>*/
	public ToolShelfSearchKey()
	{
		setColumns(Columns);
	}

	//#CM48167
	// Public methods ------------------------------------------------

	//#CM48168
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 </en>*/
	public void setNumber(String stnum)
	{
		setValue(STATIONNUMBER, stnum);
	}

	//#CM48169
	/**<en>
	 * Retrieve STATIONNUMBER.
	 </en>*/
	public String getNumber()
	{
		return (String)getValue(STATIONNUMBER);
	}

	//#CM48170
	/**<en>
	 * Set the sort order of STATIONNUMBER.
	 </en>*/
	public void setNumberOrder(int num, boolean bool)
	{
		setOrder(STATIONNUMBER, num, bool);
	}

	//#CM48171
	/**<en>
	 * Set the sort order of STATIONNUMBER.
	 </en>*/
	public int getNumberOrder()
	{
		return (getOrder(STATIONNUMBER));
	}

	//#CM48172
	/**<en>
	 * Set the search value of PRESENCE.
	 </en>*/
	public void setPresence(int type)
	{
		setValue(PRESENCE, type);
	}

	//#CM48173
	/**<en>
	 * Retrieve the search value of PRESENCE.
	 </en>*/
	public int getPresence()
	{
		Integer intobj = (Integer)getValue(PRESENCE);
		return(intobj.intValue());
	}

	//#CM48174
	/**<en>
	 * Set the search value of STATUS.
	 </en>*/
	public void setStatus(int hzone)
	{
		setValue(STATUS, hzone);
	}

	//#CM48175
	/**<en>
	 * Retrieve STATUS.
	 </en>*/
	public int getStatus()
	{
		Integer intobj = (Integer)getValue(STATUS);
		return (intobj.intValue());
	}

	//#CM48176
	/**<en>
	 * Set the search value of HARDZONE.
	 </en>*/
	public void setHardZone(int hzone)
	{
		setValue(HARDZONEID, hzone);
	}

	//#CM48177
	/**<en>
	 * Retrieve HARDZONE.
	 </en>*/
	public int getHardZone()
	{
		Integer intobj = (Integer)getValue(HARDZONEID);
		return (intobj.intValue());
	}

	//#CM48178
	/**<en>
	 * Set the sort order of HARDZONE.
	 </en>*/
	public void setHardZoneOrder(int num, boolean bool)
	{
		setOrder(HARDZONEID, num, bool);
	}

	//#CM48179
	/**<en>
	 * Retrieve the sort order of HARDZONE.
	 </en>*/
	public int getHardZoneOrder()
	{
		return (getOrder(HARDZONEID));
	}

	//#CM48180
	/**<en>
	 * Set the search value of SOFTZONE.
	 * @param szone :search value of SOFTZONE
	 </en>*/
	public void setSoftZone(int szone)
	{
		setValue(SOFTZONEID, szone);
	}

	//#CM48181
	/**<en>
	 * RetrieveSOFTZONE.
	 * @return SOFTZONE
	 </en>*/
	public int getSoftZone()
	{
		Integer intobj = (Integer)getValue(SOFTZONEID);
		return (intobj.intValue());
	}

	//#CM48182
	/**<en>
	 * Set the sort order of SOFTZONE.
	 * @param num  :priority in search
	 * @param bool :true if searching in ascending order, or false if in descending order
	 </en>*/
	public void setSoftZoneOrder(int num, boolean bool)
	{
		setOrder(SOFTZONEID, num, bool);
	}

	//#CM48183
	/**<en>
	 * Retrieve the sort order of SOFTZONE.
	 * @return :order of SOFTZONE
	 </en>*/
	public int getSoftZoneOrder()
	{
		return (getOrder(SOFTZONEID));
	}

	//#CM48184
	/**<en>
	 * Set the search value of NBANK.
	 </en>*/
	public void setBank(int nbank)
	{
		setValue(NBANK, nbank);
	}

	//#CM48185
	/**<en>
	 * Retrieve NBANK.
	 </en>*/
	public int getBank()
	{
		Integer intobj = (Integer)getValue(NBANK);
		return (intobj.intValue());
	}

	//#CM48186
	/**<en>
	 * Set the sort order of NBANK.
	 </en>*/
	public void setBankOrder(int num, boolean bool)
	{
		setOrder(NBANK, num, bool);
	}

	//#CM48187
	/**<en>
	 * Retrieve the sort order of NBANK.
	 </en>*/
	public int getBankOrder()
	{
		return (getOrder(NBANK));
	}

	//#CM48188
	/**<en>
	 * Set the search value of NBAY.
	 </en>*/
	public void setBay(int nbay)
	{
		setValue(NBAY, nbay);
	}

	//#CM48189
	/**<en>
	 * Retrieve NBAY.
	 </en>*/
	public int getBay()
	{
		Integer intobj = (Integer)getValue(NBAY);
		return (intobj.intValue());
	}

	//#CM48190
	/**<en>
	 * Set the sort order of NBAY.
	 </en>*/
	public void setBayOrder(int num, boolean bool)
	{
		setOrder(NBAY, num, bool);
	}

	//#CM48191
	/**<en>
	 * Retrieve the sort order of NBAY.
	 </en>*/
	public int getBayOrder()
	{
		return (getOrder(NBAY));
	}

	//#CM48192
	/**<en>
	 * Set the search value of NLEVEL.
	 </en>*/
	public void setLevel(int nlevel)
	{
		setValue(NLEVEL, nlevel);
	}

	//#CM48193
	/**<en>
	 * Retrieve NLEVEL.
	 </en>*/
	public int getLevel()
	{
		Integer intobj = (Integer)getValue(NLEVEL);
		return (intobj.intValue());
	}

	//#CM48194
	/**<en>
	 * Set the sort order of NLEVEL.
	 </en>*/
	public void setLevelOrder(int num, boolean bool)
	{
		setOrder(NLEVEL, num, bool);
	}

	//#CM48195
	/**<en>
	 * Retrieve the sort order of NLEVEL.
	 </en>*/
	public int getLevelOrder()
	{
		return (getOrder(NLEVEL));
	}

	//#CM48196
	/**<en>
	 * Set the search value of WHSTATIONNUMBER.
	 </en>*/
	public void setWHNumber(String stnum)
	{
		setValue(WHSTATIONNUMBER, stnum);
	}

	//#CM48197
	/**<en>
	 * Retrieve WHSTATIONNUMBER.
	 </en>*/
	public String getWHNumber()
	{
		return (String)getValue(WHSTATIONNUMBER);
	}


	//#CM48198
	/**<en>
	 * Set the search value of WHSTATIONNUMBER.
	 </en>*/
	public void setWarehouseStationNumber(String stnum)
	{
		setValue(WHSTATIONNUMBER, stnum);
	}

	//#CM48199
	/**<en>
	 * Retrieve WHSTATIONNUMBER.
	 </en>*/
	public String getWarehouseStationNumber()
	{
		return (String)getValue(WHSTATIONNUMBER);
	}

	//#CM48200
	/**<en>
	 * Set the sort order of WHSTATIONNUMBER.
	 </en>*/
	public void setWHNumberOrder(int num, boolean bool)
	{
		setOrder(WHSTATIONNUMBER, num, bool);
	}

	//#CM48201
	/**<en>
	 * Retrieve the sort order of WHSTATIONNUMBER.
	 </en>*/
	public int getWHNumberOrder()
	{
		return (getOrder(WHSTATIONNUMBER));
	}

	//#CM48202
	/**<en>
	 * Set the search value of PARENTSTATIONNUMBER.
	 </en>*/
	public void setParentStationNumber(String pnum)
	{
		setValue(PARENTSTATIONNUMBER, pnum);
	}

	//#CM48203
	/**<en>
	 * Retrieve the search value of PARENTSTATIONNUMBER.
	 * @return :parent station no.
	 </en>*/
	public String getParentStationNumber()
	{
		return (String)getValue(PARENTSTATIONNUMBER);
	}

	//#CM48204
	/**<en>
	 * Set the search value of ACCESSNGFLAG.
	 </en>*/
	public void setAccessNgFlag(int acc)
	{
		setValue(ACCESSNGFLAG, acc);
	}

	//#CM48205
	/**<en>
	 * Retrieve the search value of ACCESSNGFLAG.
	 </en>*/
	public int getAccessNgFlag()
	{
		Integer intobj = (Integer)getValue(ACCESSNGFLAG);
		return(intobj.intValue());
	}


	//#CM48206
	// Package methods -----------------------------------------------

	//#CM48207
	// Protected methods ---------------------------------------------

	//#CM48208
	// Private methods -----------------------------------------------

	//#CM48209
	// Inner Class ---------------------------------------------------

}
//#CM48210
//end of class

