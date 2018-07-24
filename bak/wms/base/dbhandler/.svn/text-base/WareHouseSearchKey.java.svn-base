//$Id: WareHouseSearchKey.java,v 1.3 2006/11/13 04:32:55 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**
 * This is the search key class for WAREHOUSE use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:32:55 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WareHouseSearchKey
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
	public WareHouseSearchKey()
	{
		super(new WareHouse()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.STATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + WareHouse.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareHouse.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationNumberGroup(int num)
	{
		setGroup(_Prefix + WareHouse.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(_Prefix + WareHouse.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + WareHouse.STATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseNumber(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseNumber(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSENUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  warehouse number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseNumber(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSENUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWareHouseNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareHouse.WAREHOUSENUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse number
	 * @param num grouping order
	 */
	public void setWareHouseNumberGroup(int num)
	{
		setGroup(_Prefix + WareHouse.WAREHOUSENUMBER.toString(), num);
	}

	/**
	 * Fetch warehouse number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWareHouseNumberCollect()
	{
		setCollect(_Prefix + WareHouse.WAREHOUSENUMBER.toString(), "");
	}

	/**
	 * Fetch warehouse number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWareHouseNumberCollect(String compcode)
	{
		setCollect(_Prefix + WareHouse.WAREHOUSENUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSETYPE.toString(), arg);
	}
	/**
	 *Set search value for  warehouse type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseType(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSETYPE.toString(), arg);
	}
	/**
	 *Set search value for  warehouse type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseType(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSETYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  warehouse type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseType(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSETYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWareHouseTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareHouse.WAREHOUSETYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse type
	 * @param num grouping order
	 */
	public void setWareHouseTypeGroup(int num)
	{
		setGroup(_Prefix + WareHouse.WAREHOUSETYPE.toString(), num);
	}

	/**
	 * Fetch warehouse type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWareHouseTypeCollect()
	{
		setCollect(_Prefix + WareHouse.WAREHOUSETYPE.toString(), "");
	}

	/**
	 * Fetch warehouse type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWareHouseTypeCollect(String compcode)
	{
		setCollect(_Prefix + WareHouse.WAREHOUSETYPE.toString(), compcode);
	}

	/**
	 *Set search value for  maximum possible palette
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxMixedPalette(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.MAXMIXEDPALETTE.toString(), arg);
	}
	/**
	 *Set search value for  maximum possible palette
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxMixedPalette(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.MAXMIXEDPALETTE.toString(), arg);
	}
	/**
	 *Set search value for  maximum possible palette
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxMixedPalette(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.MAXMIXEDPALETTE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  maximum possible palette
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxMixedPalette(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.MAXMIXEDPALETTE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for maximum possible palette
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setMaxMixedPaletteOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareHouse.MAXMIXEDPALETTE.toString(), num, bool);
	}

	/**
	 * Set grouping order maximum possible palette
	 * @param num grouping order
	 */
	public void setMaxMixedPaletteGroup(int num)
	{
		setGroup(_Prefix + WareHouse.MAXMIXEDPALETTE.toString(), num);
	}

	/**
	 * Fetch maximum possible palette info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setMaxMixedPaletteCollect()
	{
		setCollect(_Prefix + WareHouse.MAXMIXEDPALETTE.toString(), "");
	}

	/**
	 * Fetch maximum possible palette info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setMaxMixedPaletteCollect(String compcode)
	{
		setCollect(_Prefix + WareHouse.MAXMIXEDPALETTE.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSENAME.toString(), arg);
	}
	/**
	 *Set search value for  warehouse name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSENAME.toString(), arg);
	}
	/**
	 *Set search value for  warehouse name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSENAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  warehouse name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSENAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWareHouseNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareHouse.WAREHOUSENAME.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse name
	 * @param num grouping order
	 */
	public void setWareHouseNameGroup(int num)
	{
		setGroup(_Prefix + WareHouse.WAREHOUSENAME.toString(), num);
	}

	/**
	 * Fetch warehouse name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWareHouseNameCollect()
	{
		setCollect(_Prefix + WareHouse.WAREHOUSENAME.toString(), "");
	}

	/**
	 * Fetch warehouse name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWareHouseNameCollect(String compcode)
	{
		setCollect(_Prefix + WareHouse.WAREHOUSENAME.toString(), compcode);
	}

	/**
	 *Set search value for  last used station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.LASTUSEDSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  last used station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.LASTUSEDSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  last used station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.LASTUSEDSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last used station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.LASTUSEDSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for last used station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUsedStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareHouse.LASTUSEDSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order last used station number
	 * @param num grouping order
	 */
	public void setLastUsedStationNumberGroup(int num)
	{
		setGroup(_Prefix + WareHouse.LASTUSEDSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch last used station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUsedStationNumberCollect()
	{
		setCollect(_Prefix + WareHouse.LASTUSEDSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch last used station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUsedStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + WareHouse.LASTUSEDSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  automatic warehouse type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmploymentType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.EMPLOYMENTTYPE.toString(), arg);
	}
	/**
	 *Set search value for  automatic warehouse type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmploymentType(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.EMPLOYMENTTYPE.toString(), arg);
	}
	/**
	 *Set search value for  automatic warehouse type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmploymentType(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.EMPLOYMENTTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  automatic warehouse type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmploymentType(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.EMPLOYMENTTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for automatic warehouse type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setEmploymentTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareHouse.EMPLOYMENTTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order automatic warehouse type
	 * @param num grouping order
	 */
	public void setEmploymentTypeGroup(int num)
	{
		setGroup(_Prefix + WareHouse.EMPLOYMENTTYPE.toString(), num);
	}

	/**
	 * Fetch automatic warehouse type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setEmploymentTypeCollect()
	{
		setCollect(_Prefix + WareHouse.EMPLOYMENTTYPE.toString(), "");
	}

	/**
	 * Fetch automatic warehouse type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setEmploymentTypeCollect(String compcode)
	{
		setCollect(_Prefix + WareHouse.EMPLOYMENTTYPE.toString(), compcode);
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
		return "$Id: WareHouseSearchKey.java,v 1.3 2006/11/13 04:32:55 suresh Exp $" ;
	}
}
