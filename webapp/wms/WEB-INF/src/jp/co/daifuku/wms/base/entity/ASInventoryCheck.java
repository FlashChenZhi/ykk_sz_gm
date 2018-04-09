//#CM702988
//$Id: ASInventoryCheck.java,v 1.5 2006/11/16 02:15:47 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM702989
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.ASInventoryCheck;

//#CM702990
/**
 * Entity class of ASINVENTORYCHECK
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:47 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ASInventoryCheck
		extends AbstractEntity
{
	//#CM702991
	//------------------------------------------------------------
	//#CM702992
	// class variables (prefix '$')
	//#CM702993
	//------------------------------------------------------------
	//#CM702994
	//	private String	$classVar ;
	//#CM702995
	/**<jp>
	 * Item which shows set type(stock confirmation)
	 </jp>*/
	//#CM702996
	/**<en>
	 * Item of setting type (check inventory)
	 </en>*/
	public static final int INVENTORYCHECK = 1 ;

	//#CM702997
	/**<jp>
	 * Item which shows set type(empty shelf confirmation)
	 </jp>*/
	//#CM702998
	/**<en>
	 * Item of setting type (check empty location)
	 </en>*/
	public static final int EMPTYLOCATIONCHECK = 2 ;

	//#CM702999
	/**<jp>
	 * Item which shows state(Being processed)
	 </jp>*/
	//#CM703000
	/**<en>
	 * Item to indicate status (in proces)
	 </en>*/
	public static final int SCHON = 1 ;

	//#CM703001
	/**<jp>
	 * Item which shows state(Processed)
	 </jp>*/
	//#CM703002
	/**<en>
	 * Item to indicate the status (processed)
	 </en>*/
	public static final int SCHOFF = 0 ;

	//#CM703003
	//------------------------------------------------------------
	//#CM703004
	// fields (upper case only)
	//#CM703005
	//------------------------------------------------------------
	//#CM703006
	/*
	 *  * Table name : ASINVENTORYCHECK
	 * schedule number :               SCHEDULENUMBER      varchar2(9)
	 * creation date :                 CREATEDATE          date
	 * setting type :                  SETTINGTYPE         number
	 * warehouse station number :      WHSTATIONNUMBER     varchar2(16)
	 * consignor code :                CONSIGNORCODE       varchar2(16)
	 * consignor name :                CONSIGNORNAME       varchar2(40)
	 * start location :                FROMLOCATION        varchar2(16)
	 * last location :                 TOLOCATION          varchar2(16)
	 * starting item code :            FROMITEMKEY         varchar2(40)
	 * last item code :                TOITEMKEY           varchar2(40)
	 * lot number :                    LOTNUMBER           varchar2(60)
	 * station number :                STATIONNUMBER       varchar2(16)
	 * status :                        STATUS              number
	 */

	//#CM703007
	/**Table name definition*/

	public static final String TABLE_NAME = "ASINVENTORYCHECK";

	//#CM703008
	/** Column Definition (SCHEDULENUMBER) */

	public static final FieldName SCHEDULENUMBER = new FieldName("SCHEDULENUMBER");

	//#CM703009
	/** Column Definition (CREATEDATE) */

	public static final FieldName CREATEDATE = new FieldName("CREATEDATE");

	//#CM703010
	/** Column Definition (SETTINGTYPE) */

	public static final FieldName SETTINGTYPE = new FieldName("SETTINGTYPE");

	//#CM703011
	/** Column Definition (WHSTATIONNUMBER) */

	public static final FieldName WHSTATIONNUMBER = new FieldName("WHSTATIONNUMBER");

	//#CM703012
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM703013
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM703014
	/** Column Definition (FROMLOCATION) */

	public static final FieldName FROMLOCATION = new FieldName("FROMLOCATION");

	//#CM703015
	/** Column Definition (TOLOCATION) */

	public static final FieldName TOLOCATION = new FieldName("TOLOCATION");

	//#CM703016
	/** Column Definition (FROMITEMKEY) */

	public static final FieldName FROMITEMCODE = new FieldName("FROM_ITEM_CODE");

	//#CM703017
	/** Column Definition (TOITEMKEY) */

	public static final FieldName TOITEMCODE = new FieldName("TO_ITEM_CODE");

	//#CM703018
	/** Column Definition (LOTNUMBER) */

	public static final FieldName LOTNUMBER = new FieldName("LOTNUMBER");

	//#CM703019
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM703020
	/** Column Definition (STATUS) */

	public static final FieldName STATUS = new FieldName("STATUS");


	//#CM703021
	//------------------------------------------------------------
	//#CM703022
	// properties (prefix 'p_')
	//#CM703023
	//------------------------------------------------------------
	//#CM703024
	//	private String	p_Name ;


	//#CM703025
	//------------------------------------------------------------
	//#CM703026
	// instance variables (prefix '_')
	//#CM703027
	//------------------------------------------------------------
	//#CM703028
	//	private String	_instanceVar ;

	//#CM703029
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM703030
	//------------------------------------------------------------
	//#CM703031
	// constructors
	//#CM703032
	//------------------------------------------------------------

	//#CM703033
	/**
	 * Prepare class name list and generate instance
	 */
	public ASInventoryCheck()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM703034
	//------------------------------------------------------------
	//#CM703035
	// accessors
	//#CM703036
	//------------------------------------------------------------

	//#CM703037
	/**
	 * Set value to schedule number
	 * @param arg schedule number to be set
	 */
	public void setScheduleNumber(String arg)
	{
		setValue(SCHEDULENUMBER, arg);
	}

	//#CM703038
	/**
	 * Fetch schedule number
	 * @return schedule number
	 */
	public String getScheduleNumber()
	{
		return getValue(ASInventoryCheck.SCHEDULENUMBER).toString();
	}

	//#CM703039
	/**
	 * Set value to creation date
	 * @param arg creation date to be set
	 */
	public void setCreateDate(Date arg)
	{
		setValue(CREATEDATE, arg);
	}

	//#CM703040
	/**
	 * Fetch creation date
	 * @return creation date
	 */
	public Date getCreateDate()
	{
		return (Date)getValue(ASInventoryCheck.CREATEDATE);
	}

	//#CM703041
	/**
	 * Set value to setting type
	 * @param arg setting type to be set
	 */
	public void setSettingType(int arg)
	{
		setValue(SETTINGTYPE, new Integer(arg));
	}

	//#CM703042
	/**
	 * Fetch setting type
	 * @return setting type
	 */
	public int getSettingType()
	{
		return getBigDecimal(ASInventoryCheck.SETTINGTYPE).intValue();
	}

	//#CM703043
	/**
	 * Set value to warehouse station number
	 * @param arg warehouse station number to be set
	 */
	public void setWHStationNumber(String arg)
	{
		setValue(WHSTATIONNUMBER, arg);
	}

	//#CM703044
	/**
	 * Fetch warehouse station number
	 * @return warehouse station number
	 */
	public String getWHStationNumber()
	{
		return getValue(ASInventoryCheck.WHSTATIONNUMBER).toString();
	}

	//#CM703045
	/**
	 * Set value to consignor code
	 * @param arg consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM703046
	/**
	 * Fetch consignor code
	 * @return consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(ASInventoryCheck.CONSIGNORCODE).toString();
	}

	//#CM703047
	/**
	 * Set value to consignor name
	 * @param arg consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM703048
	/**
	 * Fetch consignor name
	 * @return consignor name
	 */
	public String getConsignorName()
	{
		return getValue(ASInventoryCheck.CONSIGNORNAME).toString();
	}

	//#CM703049
	/**
	 * Set value to start location
	 * @param arg start location to be set
	 */
	public void setFromLocation(String arg)
	{
		setValue(FROMLOCATION, arg);
	}

	//#CM703050
	/**
	 * Fetch start location
	 * @return start location
	 */
	public String getFromLocation()
	{
		return getValue(ASInventoryCheck.FROMLOCATION).toString();
	}

	//#CM703051
	/**
	 * Set value to last location
	 * @param arg last location to be set
	 */
	public void setToLocation(String arg)
	{
		setValue(TOLOCATION, arg);
	}

	//#CM703052
	/**
	 * Fetch last location
	 * @return last location
	 */
	public String getToLocation()
	{
		return getValue(ASInventoryCheck.TOLOCATION).toString();
	}

	//#CM703053
	/**
	 * Set value to starting item code
	 * @param arg starting item code to be set
	 */
	public void setFromItemCode(String arg)
	{
		setValue(FROMITEMCODE, arg);
	}

	//#CM703054
	/**
	 * Fetch starting item code
	 * @return starting item code
	 */
	public String getFromItemCode()
	{
		return getValue(ASInventoryCheck.FROMITEMCODE).toString();
	}

	//#CM703055
	/**
	 * Set value to last item code
	 * @param arg last item code to be set
	 */
	public void setToItemCode(String arg)
	{
		setValue(TOITEMCODE, arg);
	}

	//#CM703056
	/**
	 * Fetch last item code
	 * @return last item code
	 */
	public String getToItemCode()
	{
		return getValue(ASInventoryCheck.TOITEMCODE).toString();
	}

	//#CM703057
	/**
	 * Set value to lot number
	 * @param arg lot number to be set
	 */
	public void setLotNumber(String arg)
	{
		setValue(LOTNUMBER, arg);
	}

	//#CM703058
	/**
	 * Fetch lot number
	 * @return lot number
	 */
	public String getLotNumber()
	{
		return getValue(ASInventoryCheck.LOTNUMBER).toString();
	}

	//#CM703059
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM703060
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationNumber()
	{
		return getValue(ASInventoryCheck.STATIONNUMBER).toString();
	}

	//#CM703061
	/**
	 * Set value to status
	 * @param arg status to be set
	 */
	public void setStatus(int arg)
	{
		setValue(STATUS, new Integer(arg));
	}

	//#CM703062
	/**
	 * Fetch status
	 * @return status
	 */
	public int getStatus()
	{
		return getBigDecimal(ASInventoryCheck.STATUS).intValue();
	}


	//#CM703063
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM703064
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(SETTINGTYPE, new Integer(0));
		setValue(STATUS, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM703065
	// package methods
	//#CM703066
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703067
	//------------------------------------------------------------


	//#CM703068
	//------------------------------------------------------------
	//#CM703069
	// protected methods
	//#CM703070
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703071
	//------------------------------------------------------------


	//#CM703072
	//------------------------------------------------------------
	//#CM703073
	// private methods
	//#CM703074
	//------------------------------------------------------------
	//#CM703075
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + SCHEDULENUMBER);
		lst.add(prefix + CREATEDATE);
		lst.add(prefix + SETTINGTYPE);
		lst.add(prefix + WHSTATIONNUMBER);
		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + CONSIGNORNAME);
		lst.add(prefix + FROMLOCATION);
		lst.add(prefix + TOLOCATION);
		lst.add(prefix + FROMITEMCODE);
		lst.add(prefix + TOITEMCODE);
		lst.add(prefix + LOTNUMBER);
		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + STATUS);
	}


	//#CM703076
	//------------------------------------------------------------
	//#CM703077
	// utility methods
	//#CM703078
	//------------------------------------------------------------
	//#CM703079
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: ASInventoryCheck.java,v 1.5 2006/11/16 02:15:47 suresh Exp $" ;
	}
}
