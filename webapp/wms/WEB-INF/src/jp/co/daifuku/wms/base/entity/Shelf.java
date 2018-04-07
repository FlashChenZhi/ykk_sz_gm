//#CM705979
//$Id: Shelf.java,v 1.5 2006/11/16 02:15:38 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM705980
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */


import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Shelf;

//#CM705981
/**
 * Entity class of SHELF
 *
 * <hr><table border="1" cellpadding="3" cellspacing="0" width="90%">
 * <caption><font size="+1"><b>Update History</b></font></caption>
 * <tbody><tr bgcolor="#CCCCFF" class="TableHeadingColor" align="center">
 * <td><b>Date</b></td><td><b>Author</b></td><td><b>Comment</b></td></tr>
 *
 * <!-- update history -->
 * <tr><td nowrap>2005/04/21</td><td nowrap>ss</td>
 * <td>Class created.</td></tr>
 *
 * </tbody></table><hr>
 *
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:38 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Shelf extends Station
{
	//#CM705982
	//------------------------------------------------------------
	//#CM705983
	// class variables (prefix '$')
	//#CM705984
	//------------------------------------------------------------
	//#CM705985
	//	private String	$classVar ;
	//#CM705986
	/**<jp>
	 * Show the status of the location (Unuseable)
	 </jp>*/
	//#CM705987
	/**<en>
	 * The state of a shelf is expressed.(Use is impossible.)
	 </en>*/
	public static final int    STATUS_NG = 0;

	//#CM705988
	/**<jp>
	 * Show the status of the location (Useable)
	 </jp>*/
	//#CM705989
	/**<en>
	 * The state of a shelf is expressed.(Use is possible.)
	 </en>*/
	public static final int    STATUS_OK = 1;

	//#CM705990
	/**<jp>
	 * Field which shows accessibility and impossibility (Accessible)
	 </jp>*/
	//#CM705991
	/**<en>
	 * Field of accessibility (accesible)
	 </en>*/
	public static final int    ACCESS_OK = 0 ;

	//#CM705992
	/**<jp>
	 * Field which shows accessibility and impossibility (Impossible)
	 </jp>*/
	//#CM705993
	/**<en>
	 * Field of accessibility (inaccessible)
	 </en>*/
	public static final int    ACCESS_NG = 1 ;

	//#CM705994
	/**<jp>
	 * Field which shows status of location (Empty location)
	 </jp>*/
	//#CM705995
	/**<en>
	 * Field of shelf status (empty)
	 </en>*/
	public static final int    PRESENCE_EMPTY = 0;

	//#CM705996
	/**<jp>
	 * Field which shows status of location (Actual location)
	 </jp>*/
	//#CM705997
	/**<en>
	 * Field of shelf status (loaded)
	 </en>*/
	public static final int    PRESENCE_STORAGED = 1;

	//#CM705998
	/**<jp>
	 * Field which shows status of location (Reserved location)
	 </jp>*/
	//#CM705999
	/**<en>
	 * Field of shelf status (reserved)
	 </en>*/
	public static final int    PRESENCE_RESERVATION = 2;

	//#CM706000
	/**<jp>
	 * Field which shows Hard zone
	 </jp>*/
	//#CM706001
	/**<en>
	 * Field of hard zone
	 </en>*/
	public static final int	HARD = 1;

	//#CM706002
	/**<jp>
	 * Field which shows Soft zone
	 </jp>*/
	//#CM706003
	/**<en>
	 * Field of soft zone
	 </en>*/
	public static final int SOFT = 2;

	//#CM706004
	//------------------------------------------------------------
	//#CM706005
	// fields (upper case only)
	//#CM706006
	//------------------------------------------------------------
	//#CM706007
	/*
	 *  * Table name : SHELF
	 * station number :                STATIONNUMBER       varchar2(16)
	 * bank :                          NBANK               number
	 * bay :                           NBAY                number
	 * level :                         NLEVEL              number
	 * warehouse station number :      WHSTATIONNUMBER     varchar2(16)
	 * status :                        STATUS              number
	 * location status :               PRESENCE            number
	 * hard zone :                     HARDZONEID          number
	 * soft zone :                     SOFTZONEID          number
	 * parent station number :         PARENTSTATIONNUMBER varchar2(16)
	 * location restriction flag :     ACCESSNGFLAG        number
	 * location search order :         PRIORITY            number
	 * pair station number :           PAIRSTATIONNUMBER   varchar2(16)
	 * from side, deep number :        SIDE                number
	 */

	//#CM706008
	/**Table name definition*/

	public static final String TABLE_NAME = "SHELF";

	//#CM706009
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM706010
	/** Column Definition (NBANK) */

	public static final FieldName NBANK = new FieldName("NBANK");

	//#CM706011
	/** Column Definition (NBAY) */

	public static final FieldName NBAY = new FieldName("NBAY");

	//#CM706012
	/** Column Definition (NLEVEL) */

	public static final FieldName NLEVEL = new FieldName("NLEVEL");

	//#CM706013
	/** Column Definition (WHSTATIONNUMBER) */

	public static final FieldName WHSTATIONNUMBER = new FieldName("WHSTATIONNUMBER");

	//#CM706014
	/** Column Definition (STATUS) */

	public static final FieldName STATUS = new FieldName("STATUS");

	//#CM706015
	/** Column Definition (PRESENCE) */

	public static final FieldName PRESENCE = new FieldName("PRESENCE");

	//#CM706016
	/** Column Definition (HARDZONEID) */

	public static final FieldName HARDZONEID = new FieldName("HARDZONEID");

	//#CM706017
	/** Column Definition (SOFTZONEID) */

	public static final FieldName SOFTZONEID = new FieldName("SOFTZONEID");

	//#CM706018
	/** Column Definition (PARENTSTATIONNUMBER) */

	public static final FieldName PARENTSTATIONNUMBER = new FieldName("PARENTSTATIONNUMBER");

	//#CM706019
	/** Column Definition (ACCESSNGFLAG) */

	public static final FieldName ACCESSNGFLAG = new FieldName("ACCESSNGFLAG");

	//#CM706020
	/** Column Definition (PRIORITY) */

	public static final FieldName PRIORITY = new FieldName("PRIORITY");

	//#CM706021
	/** Column Definition (PAIRSTATIONNUMBER) */

	public static final FieldName PAIRSTATIONNUMBER = new FieldName("PAIRSTATIONNUMBER");

	//#CM706022
	/** Column Definition (SIDE) */

	public static final FieldName SIDE = new FieldName("SIDE");


	//#CM706023
	//------------------------------------------------------------
	//#CM706024
	// properties (prefix 'p_')
	//#CM706025
	//------------------------------------------------------------
	//#CM706026
	//	private String	p_Name ;


	//#CM706027
	//------------------------------------------------------------
	//#CM706028
	// instance variables (prefix '_')
	//#CM706029
	//------------------------------------------------------------
	//#CM706030
	//	private String	_instanceVar ;

	//#CM706031
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM706032
	//------------------------------------------------------------
	//#CM706033
	// constructors
	//#CM706034
	//------------------------------------------------------------

	//#CM706035
	/**
	 * Prepare class name list and generate instance
	 */
	public Shelf()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM706036
	//------------------------------------------------------------
	//#CM706037
	// accessors
	//#CM706038
	//------------------------------------------------------------

	//#CM706039
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM706040
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationNumber()
	{
		return getValue(Shelf.STATIONNUMBER).toString();
	}

	//#CM706041
	/**
	 * Set value to bank
	 * @param arg bank to be set
	 */
	public void setNBank(int arg)
	{
		setValue(NBANK, new Integer(arg));
	}

	//#CM706042
	/**
	 * Fetch bank
	 * @return bank
	 */
	public int getNBank()
	{
		return getBigDecimal(Shelf.NBANK).intValue();
	}

	//#CM706043
	/**
	 * Set value to bay
	 * @param arg bay to be set
	 */
	public void setNBay(int arg)
	{
		setValue(NBAY, new Integer(arg));
	}

	//#CM706044
	/**
	 * Fetch bay
	 * @return bay
	 */
	public int getNBay()
	{
		return getBigDecimal(Shelf.NBAY).intValue();
	}

	//#CM706045
	/**
	 * Set value to level
	 * @param arg level to be set
	 */
	public void setNLevel(int arg)
	{
		setValue(NLEVEL, new Integer(arg));
	}

	//#CM706046
	/**
	 * Fetch level
	 * @return level
	 */
	public int getNLevel()
	{
		return getBigDecimal(Shelf.NLEVEL).intValue();
	}

	//#CM706047
	/**
	 * Set value to warehouse station number
	 * @param arg warehouse station number to be set
	 */
	public void setWHStationNumber(String arg)
	{
		setValue(WHSTATIONNUMBER, arg);
	}

	//#CM706048
	/**
	 * Fetch warehouse station number
	 * @return warehouse station number
	 */
	public String getWHStationNumber()
	{
		return getValue(Shelf.WHSTATIONNUMBER).toString();
	}

	//#CM706049
	/**
	 * Set value to status
	 * @param arg status to be set
	 */
	public void setStatus(int arg)
	{
		setValue(STATUS, new Integer(arg));
	}

	//#CM706050
	/**
	 * Fetch status
	 * @return status
	 */
	public int getStatus()
	{
		return getBigDecimal(Shelf.STATUS).intValue();
	}

	//#CM706051
	/**
	 * Set value to location status
	 * @param arg location status to be set
	 */
	public void setPresence(int arg)
	{
		setValue(PRESENCE, new Integer(arg));
	}

	//#CM706052
	/**
	 * Fetch location status
	 * @return location status
	 */
	public int getPresence()
	{
		return getBigDecimal(Shelf.PRESENCE).intValue();
	}

	//#CM706053
	/**
	 * Set value to hard zone
	 * @param arg hard zone to be set
	 */
	public void setHardZoneID(int arg)
	{
		setValue(HARDZONEID, new Integer(arg));
	}

	//#CM706054
	/**
	 * Fetch hard zone
	 * @return hard zone
	 */
	public int getHardZoneID()
	{
		return getBigDecimal(Shelf.HARDZONEID).intValue();
	}

	//#CM706055
	/**
	 * Set value to soft zone
	 * @param arg soft zone to be set
	 */
	public void setSoftZoneID(int arg)
	{
		setValue(SOFTZONEID, new Integer(arg));
	}

	//#CM706056
	/**
	 * Fetch soft zone
	 * @return soft zone
	 */
	public int getSoftZoneID()
	{
		return getBigDecimal(Shelf.SOFTZONEID).intValue();
	}

	//#CM706057
	/**
	 * Set value to parent station number
	 * @param arg parent station number to be set
	 */
	public void setParentStationNumber(String arg)
	{
		setValue(PARENTSTATIONNUMBER, arg);
	}

	//#CM706058
	/**
	 * Fetch parent station number
	 * @return parent station number
	 */
	public String getParentStationNumber()
	{
		return getValue(Shelf.PARENTSTATIONNUMBER).toString();
	}

	//#CM706059
	/**
	 * Set value to location restriction flag
	 * @param arg location restriction flag to be set
	 */
	public void setAccessNgFlag(int arg)
	{
		setValue(ACCESSNGFLAG, new Integer(arg));
	}

	//#CM706060
	/**
	 * Fetch location restriction flag
	 * @return location restriction flag
	 */
	public int getAccessNgFlag()
	{
		return getBigDecimal(Shelf.ACCESSNGFLAG).intValue();
	}

	//#CM706061
	/**
	 * Set value to location search order
	 * @param arg location search order to be set
	 */
	public void setPriority(int arg)
	{
		setValue(PRIORITY, new Integer(arg));
	}

	//#CM706062
	/**
	 * Fetch location search order
	 * @return location search order
	 */
	public int getPriority()
	{
		return getBigDecimal(Shelf.PRIORITY).intValue();
	}

	//#CM706063
	/**
	 * Set value to pair station number
	 * @param arg pair station number to be set
	 */
	public void setPairStationNumber(String arg)
	{
		setValue(PAIRSTATIONNUMBER, arg);
	}

	//#CM706064
	/**
	 * Fetch pair station number
	 * @return pair station number
	 */
	public String getPairStationNumber()
	{
		return getValue(Shelf.PAIRSTATIONNUMBER).toString();
	}

	//#CM706065
	/**
	 * Set value to from side, deep number
	 * @param arg from side, deep number to be set
	 */
	public void setSide(int arg)
	{
		setValue(SIDE, new Integer(arg));
	}

	//#CM706066
	/**
	 * Fetch from side, deep number
	 * @return from side, deep number
	 */
	public int getSide()
	{
		return getBigDecimal(Shelf.SIDE).intValue();
	}


	//#CM706067
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM706068
/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(NBANK, new Integer(0));
		setValue(NBAY, new Integer(0));
		setValue(NLEVEL, new Integer(0));
		setValue(STATUS, new Integer(0));
		setValue(PRESENCE, new Integer(0));
		setValue(HARDZONEID, new Integer(0));
		setValue(SOFTZONEID, new Integer(0));
		setValue(ACCESSNGFLAG, new Integer(0));
		setValue(PRIORITY, new Integer(0));
		setValue(SIDE, new Integer(0));
	}

	/**
	 * <BR>
	 * <BR>
	 * <BR>
	 * @return true :
	 */
	public boolean isSendable()
	{
		return true;
	}
	//------------------------------------------------------------
	//#CM706069
	// package methods
	//#CM706070
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706071
	//------------------------------------------------------------


	//#CM706072
	//------------------------------------------------------------
	//#CM706073
	// protected methods
	//#CM706074
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706075
	//------------------------------------------------------------


	//#CM706076
	//------------------------------------------------------------
	//#CM706077
	// private methods
	//#CM706078
	//------------------------------------------------------------
	//#CM706079
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + NBANK);
		lst.add(prefix + NBAY);
		lst.add(prefix + NLEVEL);
		lst.add(prefix + WHSTATIONNUMBER);
		lst.add(prefix + STATUS);
		lst.add(prefix + PRESENCE);
		lst.add(prefix + HARDZONEID);
		lst.add(prefix + SOFTZONEID);
		lst.add(prefix + PARENTSTATIONNUMBER);
		lst.add(prefix + ACCESSNGFLAG);
		lst.add(prefix + PRIORITY);
		lst.add(prefix + PAIRSTATIONNUMBER);
		lst.add(prefix + SIDE);
	}


	//#CM706080
	//------------------------------------------------------------
	//#CM706081
	// utility methods
	//#CM706082
	//------------------------------------------------------------
	//#CM706083
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Shelf.java,v 1.5 2006/11/16 02:15:38 suresh Exp $" ;
	}
}
