// $Id: ToolHardZoneSearchKey.java,v 1.2 2006/10/30 02:17:17 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47802
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM47803
/**<en>
 * This is a key class which is used to search TEMP_HARDZONE table by using handler class
 * and generate the instance of TEMP_HARDZone class.
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/05/01</TD><TD>ss</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:17 $
 * @author  $Author: suresh $
 </en>*/
public class ToolHardZoneSearchKey extends ToolSQLSearchKey
{
	//#CM47804
	// Class fields --------------------------------------------------
	//#CM47805
	//<en> Define here the column which may be used as a search condition or the which may be sorted. </en>
	private static final String HARDZONEID		= "HARDZONEID";
	private static final String NAME				= "NAME";
	private static final String WHSTATIONNUMBER	= "WHSTATIONNUMBER";
	private static final String HEIGHT			= "HEIGHT";
	private static final String PRIORITY			= "PRIORITY";
	private static final String STARTBANK		= "STARTBANK";
	private static final String STARTBAY			= "STARTBAY";
	private static final String STARTLEVEL		= "STARTLEVEL";
	private static final String ENDBANK			= "ENDBANK";
	private static final String ENDBAY			= "ENDBAY";
	private static final String ENDLEVEL			= "ENDLEVEL";

	//#CM47806
	// Class variables -----------------------------------------------
	private static final String[] Columns =
	{
		HARDZONEID,
		NAME,
		WHSTATIONNUMBER,
		HEIGHT,
		PRIORITY,
		STARTBANK,
		STARTBAY,
		STARTLEVEL,
		ENDBANK,
		ENDBAY,
		ENDLEVEL,
	};

	//#CM47807
	// Class method --------------------------------------------------
	//#CM47808
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM47809
	// Constructors --------------------------------------------------
	//#CM47810
	/**<en>
	 * Define the column definition.
	 </en>*/
	public ToolHardZoneSearchKey()
	{
		setColumns(Columns);
	}

	//#CM47811
	// Public methods ------------------------------------------------
	//#CM47812
	/**<en>
	 * Set the search value of hard zone ID.
	 * @param hzid :the search value of hard zone ID
	 </en>*/
	public void setHardZoneID(int hzid)
	{
		setValue(HARDZONEID, hzid);
	}

	//#CM47813
	/**<en>
	 * Retrieve the search value of hard zone ID.
	 * @return :hard zone
	 </en>*/
	public String getHardZoneID()
	{
		return (String)getValue(HARDZONEID);
	}

	//#CM47814
	/**<en>
	 * Set the sort order of hard zone ID.
	 * @param num  :prioriry in sort order
	 * @param bool true: in ascending order, false: in descending order
	 </en>*/
	public void setHardZoneIDOrder(int num, boolean bool)
	{
		setOrder(HARDZONEID, num, bool);
	}

	//#CM47815
	/**<en>
	 * Retrieve the sort order of hard zone ID.
	 * @return :the order of hard zone ID
	 </en>*/
	public int getHardZoneIDOrder()
	{
		return (getOrder(HARDZONEID));
	}

	//#CM47816
	/**<en>
	 * Set the search value of hard zone name.
	 * @param nam :the search value of hard zone name
	 </en>*/
	public void setName(String nam)
	{
		setValue(NAME, nam);
	}

	//#CM47817
	/**<en>
	 * Retrieve the search value of hard zone name.
	 * @return :name of hard zone
	 </en>*/
	public String getName()
	{
		return (String)getValue(NAME);
	}

	//#CM47818
	/**<en>
	 * Set the sort order of hard zone name.
	 * @param num :prioriry in sort order
	 * @param bool :true: in ascending order, false: in descending order
	 </en>*/
	public void setNameOrder(int num, boolean bool)
	{
		setOrder(NAME, num, bool);
	}

	//#CM47819
	/**<en>
	 * Retrieve the sort order of ghard zone name.
	 * @return :the order of hard zone name
	 </en>*/
	public int getNameOrder()
	{
		return (getOrder(NAME));
	}

	//#CM47820
	/**<en>
	 * Set the search value of WHSTATIONNUMBER.
	 * @param stnum :station no. to identify the warehouse to search
	 </en>*/
	public void setWHStationNumber(String stnum)
	{
		setValue(WHSTATIONNUMBER, stnum);
	}

	//#CM47821
	/**<en>
	 * Retrieve the search value of WHSTATIONNUMBER.
	 * @return :station no. to identify the warehouse
	 </en>*/
	public String getWHStationNumber()
	{
		return (String)getValue(WHSTATIONNUMBER);
	}

	//#CM47822
	/**<en>
	 * Set the sort order of WHSTATIONNUMBER.
	 * @param num   :prioriry in sort order
	 * @param bool  true: in ascending order, false: in descending order
	 </en>*/
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setOrder(WHSTATIONNUMBER, num, bool);
	}

	//#CM47823
	/**<en>
	 * Set the search value of WHSTATIONNUMBER.
	 * @return :the order of station no. to identify the station no.
	 </en>*/
	public int getWHStationNumberOrder()
	{
		return (getOrder(WHSTATIONNUMBER));
	}

	//#CM47824
	/**<en>
	 * Set the search value of load height.
	 * @param hgt :the search value of load height
	 </en>*/
	public void setHeight(String hgt)
	{
		setValue(HEIGHT, hgt);
	}

	//#CM47825
	/**<en>
	 * Retrieve the search value of load height.
	 * @return :load height
	 </en>*/
	public String getHeight()
	{
		return (String)getValue(HEIGHT);
	}

	//#CM47826
	/**<en>
	 * Set the sort order of load height.
	 * @param num :prioriry in sort order
	 * @param bool :true: in ascending order, false: in descending order
	 </en>*/
	public void setHeightOrder(int num, boolean bool)
	{
		setOrder(HEIGHT, num, bool);
	}

	//#CM47827
	/**<en>
	 * Retrieve the sort order of load height
	 * @return :load height
	 </en>*/
	public int getHeightOrder()
	{
		return (getOrder(HEIGHT));
	}

	//#CM47828
	/**<en>
	 * Set the search value of priority.
	 * @param pri :the search value of priority
	 </en>*/
	public void setPriority(String pri)
	{
		setValue(PRIORITY, pri);
	}

	//#CM47829
	/**<en>
	 * Retrieve the search value of priority.
	 * @return :priority
	 </en>*/
	public String getPriority()
	{
		return (String)getValue(PRIORITY);
	}

	//#CM47830
	/**<en>
	 * Set the sort order of priority.
	 * @param num :prioriry in sort order
	 * @param bool :true: in ascending order, false: in descending order
	 </en>*/
	public void setPriorityOrder(int num, boolean bool)
	{
		setOrder(PRIORITY, num, bool);
	}

	//#CM47831
	/**<en>
	 * Retrieve the sort order of priority.
	 * @return :the order of priority
	 </en>*/
	public int getPriorityOrder()
	{
		return (getOrder(PRIORITY));
	}

	//#CM47832
	/**<en>
	 * Set the search value of STARTBANK.
	 * @param sbank :the search value of STARTBANK
	 </en>*/
	public void setStartBank(String sbank)
	{
		setValue(STARTBANK, sbank);
	}

	//#CM47833
	/**<en>
	 * Retrieve the search value of STARTBANK.
	 * @return STARTBANK
	 </en>*/
	public String getStartBank()
	{
		return (String)getValue(STARTBANK);
	}
	
	//#CM47834
	/**<en>
	 * Set the sort order of STARTBANK.
	 * @param num :prioriry in sort order
	 * @param bool :true: in ascending order, false: in descending order
	 </en>*/
	public void setStartBankOrder(int num, boolean bool)
	{
		setOrder(STARTBANK, num, bool);
	}

	//#CM47835
	/**<en>
	 * Retrieve the sort order of STARTBANK.
	 * @return :the order of STARTBANK
	 </en>*/
	public int getStartBankOrder()
	{
		return (getOrder(STARTBANK));
	}

	//#CM47836
	/**<en>
	 * Set the search value of STARTBAY.
	 * @param sbay :the search value of STARTBAY
	 </en>*/
	public void setStartBay(String sbay)
	{
		setValue(STARTBAY, sbay);
	}

	//#CM47837
	/**<en>
	 * Retrieve the search value of STARTBAY.
	 * @return STARTBAY
	 </en>*/
	public String getStartBay()
	{
		return (String)getValue(STARTBAY);
	}
	
	//#CM47838
	/**<en>
	 * Set the sort order of STARTBAY.
	 * @param num :prioriry in sort order
	 * @param bool :true: in ascending order, false: in descending order
	 </en>*/
	public void setStartBayOrder(int num, boolean bool)
	{
		setOrder(STARTBAY, num, bool);
	}

	//#CM47839
	/**<en>
	 * Retrieve the sort order of STARTBAY.
	 * @return :the order of STARTBAY
	 </en>*/
	public int getStartBayOrder()
	{
		return (getOrder(STARTBAY));
	}

	//#CM47840
	/**<en>
	 * Set the search value of STARTLEVEL.
	 * @param slvl :the search value of STARTLEVEL
	 </en>*/
	public void setStartLevel(String slvl)
	{
		setValue(STARTLEVEL, slvl);
	}

	//#CM47841
	/**<en>
	 * Retrieve the search value of STARTLEVEL.
	 * @return STARTLEVEL
	 </en>*/
	public String getStartLevel()
	{
		return (String)getValue(STARTLEVEL);
	}
	
	//#CM47842
	/**<en>
	 * Set the sort order of STARTLEVEL.
	 * @param num :prioriry in sort order
	 * @param bool :true: in ascending order, false: in descending order
	 </en>*/
	public void setStartLevelOrder(int num, boolean bool)
	{
		setOrder(STARTLEVEL, num, bool);
	}

	//#CM47843
	/**<en>
	 * Retrieve the sort order of STARTLEVEL.
	 * @return :the order of STARTLEVEL
	 </en>*/
	public int getStartLevelOrder()
	{
		return (getOrder(STARTLEVEL));
	}

	//#CM47844
	/**<en>
	 * Set the search value of ENDBANK.
	 * @param ebnk :the search value of ENDBANK
	 </en>*/
	public void setEndBank(String ebnk)
	{
		setValue(ENDBANK, ebnk);
	}

	//#CM47845
	/**<en>
	 * Retrieve the search value of ENDBANK.
	 * @return ENDBANK
	 </en>*/
	public String getEndBank()
	{
		return (String)getValue(ENDBANK);
	}
	
	//#CM47846
	/**<en>
	 * Set the sort order of ENDBANK.
	 * @param num :prioriry in sort order
	 * @param bool :true: in ascending order, false: in descending order
	 </en>*/
	public void setEndBankOrder(int num, boolean bool)
	{
		setOrder(ENDBANK, num, bool);
	}

	//#CM47847
	/**<en>
	 * Retrieve the sort order of ENDBAY.
	 </en>*/
	public int getEndBankOrder()
	{
		return (getOrder(ENDBANK));
	}

	//#CM47848
	/**<en>
	 * Set the search value of ENDBAY.
	 * @param ebay :the search value of ENDBAY
	 </en>*/
	public void setEndBay(String ebay)
	{
		setValue(ENDBAY, ebay);
	}

	//#CM47849
	/**<en>
	 * Retrieve the search value of ENDBAY.
	 * @return ENDBAY
	 </en>*/
	public String getEndBay()
	{
		return (String)getValue(ENDBAY);
	}
	
	//#CM47850
	/**<en>
	 * Set the sort order of ENDBAY.
	 * @param num  :prioriry in sort order
	 * @param bool true: in ascending order, false: in descending order
	 </en>*/
	public void setEndBayOrder(int num, boolean bool)
	{
		setOrder(ENDBAY, num, bool);
	}

	//#CM47851
	/**<en>
	 * Retrieve the sort order of ENDBAY.
	 </en>*/
	public int getEndBayOrder()
	{
		return (getOrder(ENDBAY));
	}

	//#CM47852
	/**<en>
	 * Set the search value of ENDLEVEL.
	 * @param elvl :the search value of ENDLEVEL.
	 </en>*/
	public void setEndLevel(String elvl)
	{
		setValue(ENDLEVEL, elvl);
	}

	//#CM47853
	/**<en>
	 * Retrieve the search value of ENDLEVEL.
	 * @return ENDLEVEL
	 </en>*/
	public String getEndLevel()
	{
		return (String)getValue(ENDLEVEL);
	}
	
	//#CM47854
	/**<en>
	 * Set the sort order of ENDLEVEL.
	 * @param num :prioriry in sort order
	 * @param bool :true: in ascending order, false: in descending order
	 </en>*/
	public void setEndLevelOrder(int num, boolean bool)
	{
		setOrder(ENDLEVEL, num, bool);
	}

	//#CM47855
	/**<en>
	 * Retrieve the sort order of ENDLEVEL.
	 * @return :the order of ENDLEVEL
	 </en>*/
	public int getEndLevelOrder()
	{
		return (getOrder(ENDLEVEL));
	}

	//#CM47856
	// Package methods -----------------------------------------------

	//#CM47857
	// Protected methods ---------------------------------------------

	//#CM47858
	// Private methods -----------------------------------------------

	//#CM47859
	// Inner Class ---------------------------------------------------

}
//#CM47860
//end of class

