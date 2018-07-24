// $Id: ToolStationTypeSearchKey.java,v 1.2 2006/10/30 02:17:12 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM48873
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
//#CM48874
/**<en>
 * This class is used to specify keys when searching the station type based on the station no.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:12 $
 * @author  $Author: suresh $
 </en>*/
public class ToolStationTypeSearchKey extends ToolSQLSearchKey
{
	//#CM48875
	// Class fields --------------------------------------------------
	//#CM48876
	//<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
	private static final String STATIONNUMBER       = "STATIONNUMBER";
	private static final String HANDLERCLASS        = "HANDLERCLASS";

	//#CM48877
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		STATIONNUMBER,
		HANDLERCLASS
	};

	//#CM48878
	// Class method --------------------------------------------------
	//#CM48879
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM48880
	// Constructors --------------------------------------------------
	//#CM48881
	/**<en>
	 * Default constructor
	 </en>*/
	public ToolStationTypeSearchKey()
	{
		setColumns(Columns);
	}

	//#CM48882
	// Public methods ------------------------------------------------

	//#CM48883
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 </en>*/
	public void setNumber(String stnum)
	{
		setValue(STATIONNUMBER, stnum);
	}

	//#CM48884
	/**<en>
	 * Retrieve the STATIONNUMBER.
	 </en>*/
	public String getNumber()
	{
		return (String)getValue(STATIONNUMBER);
	}

	//#CM48885
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 * Each station no. passed as arrays will be connected by placing OR inbetween.
	 </en>*/
	public void setNumber(String[] stnums)
	{
		setValue(STATIONNUMBER, stnums);
	}

	//#CM48886
	/**<en>
	 * Set the sort order of STATIONNUMBER.
	 </en>*/
	public void setNumberOrder(int num, boolean bool)
	{
		setOrder(STATIONNUMBER, num, bool);
	}

	//#CM48887
	/**<en>
	 * Retrieve hte sort ordr of STATIONNUMBER.
	 </en>*/
	public int getNumberOrder()
	{
		return (getOrder(STATIONNUMBER));
	}

	//#CM48888
	/**<en>
	 * Set the search value of HANDLERCLASS.
	 </en>*/
	public void setHandlerClass(String hdcl)
	{
		setValue(HANDLERCLASS, hdcl);
	}
	
	//#CM48889
	/**<en>
	 * Set the search value of HANDLERCLASS.
	 </en>*/
	public void setHandlerClass(String[] hdcl)
	{
		setValue(HANDLERCLASS, hdcl);
	}

	//#CM48890
	/**<en>
	 * Retrieve the HANDLERCLASS.
	 </en>*/
	public String getHandlerClass()
	{
		return (String)getValue(HANDLERCLASS);
	}

	//#CM48891
	// Package methods -----------------------------------------------

	//#CM48892
	// Protected methods ---------------------------------------------

	//#CM48893
	// Private methods -----------------------------------------------

	//#CM48894
	// Inner Class ---------------------------------------------------

}
//#CM48895
//end of class

