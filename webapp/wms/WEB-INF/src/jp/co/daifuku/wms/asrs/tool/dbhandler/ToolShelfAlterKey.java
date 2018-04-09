// $Id: ToolShelfAlterKey.java,v 1.2 2006/10/30 02:17:16 suresh Exp $

package jp.co.daifuku.wms.asrs.tool.dbhandler ;

//#CM47875
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import jp.co.daifuku.wms.asrs.tool.location.Shelf;
//#CM47876
/**<en>
 * This class defines the information which is used in the update of Shelf table.
 * It will be used when updating data using ShelfHandler.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>mori</TD><TD>created this class</TD></TR>
 * <TR><TD>2003/12/11</TD><TD>okamura</TD><TD>added the setting method of update conditions.<BR>
 * setAccessNgFlag method and getAccessNgFlag method have been added.
 * </TD></TR>
 * <TR><TD></TD><TD></TD><TD>Modified the update value setting method.<BR>
 * If the parameter of updateAccessNgFlag method is true, set as inaccessible locations.
 * If this flag is false, set as acessible locations.
 * </TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 02:17:16 $
 * @author  $Author: suresh $
 </en>*/
public class ToolShelfAlterKey extends ToolSQLAlterKey
{
	//#CM47877
	// Class fields --------------------------------------------------

	//#CM47878
	//<en> Define here the columns which could be search conditions or the target data of update.</en>
	private static final String STATIONNUMBER 	= "TEMP_SHELF.STATIONNUMBER";
	private static final String STATUS			= "TEMP_SHELF.STATUS";
	private static final String PRESENCE			= "TEMP_SHELF.PRESENCE";
	private static final String ACCESSNGFLAG		= "TEMP_SHELF.ACCESSNGFLAG";
	private static final String HARDZONEID		= "TEMP_SHELF.HARDZONEID";
	private static final String SOFTZONEID		= "TEMP_SHELF.SOFTZONEID";
	private static final String NBANK			= "TEMP_SHELF.NBANK";
	private static final String NBAY				= "TEMP_SHELF.NBAY";
	private static final String NLEVEL			= "TEMP_SHELF.NLEVEL";
	private static final String WHSTATIONNUMBER	= "TEMP_SHELF.WHSTATIONNUMBER";

	//#CM47879
	// Class variables -----------------------------------------------

	//#CM47880
	//<en> Set the variable defined with the declared column to the array.</en>
	private static final String[] Columns =
	{
		STATIONNUMBER,
		STATUS,
		PRESENCE,
		ACCESSNGFLAG,
		HARDZONEID,
		SOFTZONEID,
		NBANK,
		NBAY,
		NLEVEL,
		WHSTATIONNUMBER
	};

	//#CM47881
	// Class method --------------------------------------------------
	//#CM47882
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $") ;
	}

	//#CM47883
	// Constructors --------------------------------------------------
	//#CM47884
	/**<en>
	 * Initialize the declared table column.
	 </en>*/
	public ToolShelfAlterKey()
	{
		setColumns(Columns);
	}

	//#CM47885
	// Public methods ------------------------------------------------
	//#CM47886
	//<en>==========<Method of update condition settings>================</en>
	//#CM47887
	/**<en>
	 * Set the search value of STATIONNUMBER.
	 * @param :the search value of STATIONNUMBER
	 </en>*/
	public void setNumber(String stnum)
	{
		setValue(STATIONNUMBER, stnum);
	}

	//#CM47888
	/**<en>
	 * Retrieve STATIONNUMBER.
	 * @return STATIONNUMBER
	 </en>*/
	public String getNumber()
	{
		return (String)getValue(STATIONNUMBER);
	}

	//#CM47889
	/**<en>
	 * Set the search value of PRESENCE.
	 * @param :the search value of PRESENCE
	 </en>*/
	public void setPresence(int pre)
	{
		setValue(PRESENCE, pre);
	}

	//#CM47890
	/**<en>
	 * Retrieve STATIONNUMBER.
	 * @return STATIONNUMBER
	 </en>*/
	public String getPresence()
	{
		return (String)getValue(PRESENCE);
	}

	//#CM47891
	/**<en>
	 * Set the search value of NBANK.
	 * @param :the search value of NBANK
	 </en>*/
	public void setNBank(int nbank)
	{
		setValue(NBANK, nbank);
	}

	//#CM47892
	/**<en>
	 * Retrieve NBANK.
	 * @return NBANK
	 </en>*/
	public String getNBank()
	{
		return (String)getValue(NBANK);
	}

	//#CM47893
	/**<en>
	 * Set the search value of NBAY.
	 * @param :the search value of NBAY
	 </en>*/
	public void setNBay(int nbay)
	{
		setValue(NBAY, nbay);
	}

	//#CM47894
	/**<en>
	 * Retrieve NBANK.
	 * @return NBANK
	 </en>*/
	public String getNBay()
	{
		return (String)getValue(NBAY);
	}

	//#CM47895
	/**<en>
	 * Set the search value of NLEVEL.
	 * @param :the search value of NLEVEL
	 </en>*/
	public void setNLevel(int nlevel)
	{
		setValue(NLEVEL, nlevel);
	}

	//#CM47896
	/**<en>
	 * Retrieve NLEVEL.
	 * @return NLEVEL
	 </en>*/
	public String getNLevel()
	{
		return (String)getValue(NLEVEL);
	}

	//#CM47897
	/**<en>
	 * Set the search value of WHSTATIONNUMBER.
	 * @param :the search value of WHSTATIONNUMBER
	 </en>*/
	public void setWHNumber(String whstnum)
	{
		setValue(WHSTATIONNUMBER, whstnum);
	}

	//#CM47898
	/**<en>
	 * Retrieve WHSTATIONNUMBER.
	 * @return WHSTATIONNUMBER
	 </en>*/
	public String getWHNumber()
	{
		return (String)getValue(WHSTATIONNUMBER);
	}

//#CM47899
/*  2003/12/11  INSERT  okamura START  */

	//#CM47900
	/**<en>
	 * Set the search value of ACCESSNGFLAG.
	 * @param :the search value of ACCESSNGFLAG
	 </en>*/
	public void setAccessNgFlag(int acc)
	{
		setValue(ACCESSNGFLAG, acc);
	}

	//#CM47901
	/**<en>
	 * Retrieve ACCESSNGFLAG.
	 * @return ACCESSNGFLAG
	 </en>*/
	public String getAccessNgFlag()
	{
		return (String)getValue(ACCESSNGFLAG);
	}
//#CM47902
/*  2003/12/11  INSERT  okamura END  */

	//#CM47903
	//<en>========<Method of update valud settings>=============</en>
	//#CM47904
	/**<en>
	 * Set the update value of STATUS.
	 * @param :the update value of STATUS
	 </en>*/
	public void updateStatus(int sts)
	{
		setUpdValue(STATUS, sts);
	}

	//#CM47905
	/**<en>
	 * Set the update value of PRESENCE.
	 * @param :the update value of PRESENCE
	 </en>*/
	public void updatePresence(int pre)
	{
		setUpdValue(PRESENCE, pre);
	}

	//#CM47906
	/**<en>
	 * Set the update value of ACCESSNGFLAG.
	 * @param :the update value of ACCESSNGFLAG
	 </en>*/
	public void updateAccessNgFlag(boolean acc)
	{
		if (acc)
		{
//#CM47907
/*  2003/12/11  MODIRY  okamura START  */

			setUpdValue(ACCESSNGFLAG, Shelf.ACCESS_NG) ;
		}
		else
		{
			setUpdValue(ACCESSNGFLAG, Shelf.ACCESS_OK) ;
//#CM47908
/*  2003/12/11  MODIFY  okamura END  */

		}
	}

	//#CM47909
	/**<en>
	 * Set the update value of HARDZONE.
	 * @param :the update value of HARDZONE
	 </en>*/
	public void updateHardZone(int hzone)
	{
		setUpdValue(HARDZONEID, hzone);
	}
	
	//#CM47910
	/**<en>
	 * Set the update value of SOFTZONE.
	 * @param szone :the update value of SOFTZONE
	 </en>*/
	public void updateSoftZone(int szone)
	{
		setUpdValue(SOFTZONEID, szone);
	}

	//#CM47911
	// Package methods -----------------------------------------------

	//#CM47912
	// Protected methods ---------------------------------------------

	//#CM47913
	// Private methods -----------------------------------------------

	//#CM47914
	// Inner Class ---------------------------------------------------

}
//#CM47915
//end of class

