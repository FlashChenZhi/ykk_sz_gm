// $Id: TerminalArea.java,v 1.2 2006/10/30 02:33:23 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.location ;

//#CM49905
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.common.ToolEntity;
//#CM49906
/**<en>
 * This class preserves information of teminals and the respective stations (workshops) preserved by them.
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/12/12</TD><TD>inoue</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:33:23 $
 * @author  $Author: suresh $
 </en>*/
public class TerminalArea extends ToolEntity
{
	//#CM49907
	// Class fields --------------------------------------------------

	//#CM49908
	// Class variables -----------------------------------------------
	//#CM49909
	/**<en>
	 * station no.
	 </en>*/
	protected String wStationNumber;

	//#CM49910
	/**<en>
	 * area ID
	 </en>*/
	protected int wAreaId;

	//#CM49911
	/**<en>
	 * terminal ID
	 </en>*/
	protected String wTerminal;

	//#CM49912
	// Class method --------------------------------------------------
	//#CM49913
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 02:33:23 $") ;
	}

	//#CM49914
	// Constructors --------------------------------------------------
	//#CM49915
	/**<en>
	 * Construct new <CODE>TerminalArea</CODE>.
	 </en>*/
	public TerminalArea()
	{
	}

	//#CM49916
	/**<en>
	 * Construct new <CODE>TerminalArea</CODE>.
	 * @param stationnumber   :station no.
	 * @param areaid          :area ID
	 * @param terminal        :terminal ID
	 </en>*/
	public TerminalArea( 
							String  stationnumber, 
							int		areaid,
					    	String	terminal
			      )
	{
		//#CM49917
		//<en> Set as an instance variable.</en>
		setStationNumber(stationnumber) ;
		setAreaId(areaid);
		setTerminalNumber(terminal) ;
	}

	//#CM49918
	// Public methods ------------------------------------------------
	//#CM49919
	/**<en>
	 * Set a value of station no.
	 * @param stno :station no. to set
	 </en>*/
	public void setStationNumber(String stno)
	{
		wStationNumber = stno ;
	}

	//#CM49920
	/**<en>
	 * Retrieve the station no.
	 * @return station no.
	 </en>*/
	public String getStationNumber()
	{
		return wStationNumber ;
	}

	//#CM49921
	/**<en>
	 * Set a value of area ID.
	 * @param areaid :area ID to set
	 </en>*/
	public void setAreaId(int areaid)
	{
		wAreaId = areaid ;
	}

	//#CM49922
	/**<en>
	 * Retrieve the area ID.
	 * @return area ID
	 </en>*/
	public int getAreaId()
	{
		return wAreaId ;
	}

	//#CM49923
	/**<en>
	 * Set the value of terminal ID.
	 * @param terminal :terminal ID to set
	 </en>*/
	public void setTerminalNumber(String terminal) 
	{
		wTerminal = terminal ;
	}

	//#CM49924
	/**<en>
	 * Retrieve the terminal ID.
	 * @return terminal ID
	 </en>*/
	public String getTerminalNumber()
	{
		return wTerminal ;
	}
	
	//#CM49925
	// Package methods -----------------------------------------------

	//#CM49926
	// Protected methods ---------------------------------------------

	//#CM49927
	// Private methods -----------------------------------------------
}
//#CM49928
//end of class
