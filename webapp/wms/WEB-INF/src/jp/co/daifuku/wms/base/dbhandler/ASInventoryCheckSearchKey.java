//$Id: ASInventoryCheckSearchKey.java,v 1.4 2006/11/20 06:52:16 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.ASInventoryCheck;

/**
 * This is the search key class for ASINVENTORYCHECK use.
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
 * @version $Revision: 1.4 $, $Date: 2006/11/20 06:52:16 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ASInventoryCheckSearchKey
		extends AbstractSQLSearchKey
{
	//------------------------------------------------------------
	// class variables (Prefix '$')
	//------------------------------------------------------------
	//	private String	$classVar ;

	//------------------------------------------------------------
	// fields (upper case only)
	//------------------------------------------------------------
	//	public static final int FIELD_VALUE = 1 ;

	//------------------------------------------------------------
	// properties (Prefix 'p_')
	//------------------------------------------------------------
	//	private String	p_Name ;

	//------------------------------------------------------------
	// instance variables (Prefix '_')
	//------------------------------------------------------------
	//	private String	_instanceVar ;

	private static String _Prefix = "";

	//------------------------------------------------------------
	// constructors
	//------------------------------------------------------------
	/**
	 * Prepare table name and column list and generate instance
	 *
	 */
	public ASInventoryCheckSearchKey()
	{
		super(new ASInventoryCheck()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for schedule number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setScheduleNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order schedule number
	 * @param num grouping order
	 */
	public void setScheduleNumberGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), num);
	}

	/**
	 * Fetch schedule number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setScheduleNumberCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), "");
	}

	/**
	 * Fetch schedule number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setScheduleNumberCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CREATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CREATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CREATEDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CREATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for creation date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCreateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.CREATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order creation date
	 * @param num grouping order
	 */
	public void setCreateDateGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.CREATEDATE.toString(), num);
	}

	/**
	 * Fetch creation date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCreateDateCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.CREATEDATE.toString(), "");
	}

	/**
	 * Fetch creation date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCreateDateCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.CREATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  setting type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), arg);
	}
	/**
	 *Set search value for  setting type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), arg);
	}
	/**
	 *Set search value for  setting type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  setting type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for setting type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSettingTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order setting type
	 * @param num grouping order
	 */
	public void setSettingTypeGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), num);
	}

	/**
	 * Fetch setting type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSettingTypeCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), "");
	}

	/**
	 * Fetch setting type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSettingTypeCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse station number
	 * @param num grouping order
	 */
	public void setWHStationNumberGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWHStationNumberCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWHStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  consignor code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  consignor code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for consignor code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order consignor code
	 * @param num grouping order
	 */
	public void setConsignorCodeGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), num);
	}

	/**
	 * Fetch consignor code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorCodeCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), "");
	}

	/**
	 * Fetch consignor code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorCodeCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), compcode);
	}

	/**
	 *Set search value for  consignor name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  consignor name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for consignor name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order consignor name
	 * @param num grouping order
	 */
	public void setConsignorNameGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), num);
	}

	/**
	 * Fetch consignor name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorNameCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), "");
	}

	/**
	 * Fetch consignor name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorNameCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), compcode);
	}

	/**
	 *Set search value for  start location
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromLocation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), arg);
	}
	/**
	 *Set search value for  start location
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromLocation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), arg);
	}
	/**
	 *Set search value for  start location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromLocation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  start location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromLocation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for start location
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setFromLocationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), num, bool);
	}

	/**
	 * Set grouping order start location
	 * @param num grouping order
	 */
	public void setFromLocationGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), num);
	}

	/**
	 * Fetch start location info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setFromLocationCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), "");
	}

	/**
	 * Fetch start location info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setFromLocationCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), compcode);
	}

	/**
	 *Set search value for  last location
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToLocation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOLOCATION.toString(), arg);
	}
	/**
	 *Set search value for  last location
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToLocation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOLOCATION.toString(), arg);
	}
	/**
	 *Set search value for  last location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToLocation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOLOCATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToLocation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOLOCATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for last location
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setToLocationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.TOLOCATION.toString(), num, bool);
	}

	/**
	 * Set grouping order last location
	 * @param num grouping order
	 */
	public void setToLocationGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.TOLOCATION.toString(), num);
	}

	/**
	 * Fetch last location info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setToLocationCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.TOLOCATION.toString(), "");
	}

	/**
	 * Fetch last location info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setToLocationCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.TOLOCATION.toString(), compcode);
	}

	/**
	 *Set search value for  starting item code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  starting item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  starting item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  starting item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromItemCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for starting item code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setFromItemCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order starting item code
	 * @param num grouping order
	 */
	public void setFromItemCodeGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), num);
	}

	/**
	 * Fetch starting item code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setFromItemCodeCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), "");
	}

	/**
	 * Fetch starting item code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setFromItemCodeCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), compcode);
	}

	/**
	 *Set search value for  last item code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  last item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  last item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToItemCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for last item code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setToItemCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order last item code
	 * @param num grouping order
	 */
	public void setToItemCodeGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), num);
	}

	/**
	 * Fetch last item code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setToItemCodeCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), "");
	}

	/**
	 * Fetch last item code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setToItemCodeCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), compcode);
	}

	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  lot number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for lot number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLotNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order lot number
	 * @param num grouping order
	 */
	public void setLotNumberGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), num);
	}

	/**
	 * Fetch lot number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLotNumberCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), "");
	}

	/**
	 * Fetch lot number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLotNumberCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationNumberGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATUS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ASInventoryCheck.STATUS.toString(), num, bool);
	}

	/**
	 * Set grouping order status
	 * @param num grouping order
	 */
	public void setStatusGroup(int num)
	{
		setGroup(_Prefix + ASInventoryCheck.STATUS.toString(), num);
	}

	/**
	 * Fetch status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusCollect()
	{
		setCollect(_Prefix + ASInventoryCheck.STATUS.toString(), "");
	}

	/**
	 * Fetch status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusCollect(String compcode)
	{
		setCollect(_Prefix + ASInventoryCheck.STATUS.toString(), compcode);
	}

	//------------------------------------------------------------
	// utility methods
	//------------------------------------------------------------
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: ASInventoryCheckSearchKey.java,v 1.4 2006/11/20 06:52:16 suresh Exp $" ;
	}
}
