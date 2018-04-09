//$Id: WareHouseAlterKey.java,v 1.3 2006/11/13 04:32:56 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.WareHouse;

/**
 * Update key class for WAREHOUSE use
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:32:56 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WareHouseAlterKey
		extends AbstractSQLAlterKey
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
	public WareHouseAlterKey()
	{
		super(new WareHouse()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for station number
	 * @param arg station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.STATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for station number
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
	 * Set update value for station number
	 * @param arg Update value for station number
	 */
	public void updateStationNumber(String arg)
	{
		setUpdValue(_Prefix + WareHouse.STATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for warehouse number
	 * @param arg warehouse number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSENUMBER.toString(), arg);
	}
	/**
	 * Set search value for warehouse number
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
	 * Set update value for warehouse number
	 * @param arg Update value for warehouse number
	 */
	public void updateWareHouseNumber(int arg)
	{
		setUpdValue(_Prefix + WareHouse.WAREHOUSENUMBER.toString(), arg);
	}

	/**
	 * Set search value for warehouse type
	 * @param arg warehouse type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSETYPE.toString(), arg);
	}
	/**
	 * Set search value for warehouse type
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
	 * Set update value for warehouse type
	 * @param arg Update value for warehouse type
	 */
	public void updateWareHouseType(int arg)
	{
		setUpdValue(_Prefix + WareHouse.WAREHOUSETYPE.toString(), arg);
	}

	/**
	 * Set search value for maximum possible palette
	 * @param arg maximum possible palette<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxMixedPalette(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.MAXMIXEDPALETTE.toString(), arg);
	}
	/**
	 * Set search value for maximum possible palette
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
	 * Set update value for maximum possible palette
	 * @param arg Update value for maximum possible palette
	 */
	public void updateMaxMixedPalette(int arg)
	{
		setUpdValue(_Prefix + WareHouse.MAXMIXEDPALETTE.toString(), arg);
	}

	/**
	 * Set search value for warehouse name
	 * @param arg warehouse name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWareHouseName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.WAREHOUSENAME.toString(), arg);
	}
	/**
	 * Set search value for warehouse name
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
	 * Set update value for warehouse name
	 * @param arg Update value for warehouse name
	 */
	public void updateWareHouseName(String arg)
	{
		setUpdValue(_Prefix + WareHouse.WAREHOUSENAME.toString(), arg);
	}

	/**
	 * Set search value for last used station number
	 * @param arg last used station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.LASTUSEDSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for last used station number
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
	 * Set update value for last used station number
	 * @param arg Update value for last used station number
	 */
	public void updateLastUsedStationNumber(String arg)
	{
		setUpdValue(_Prefix + WareHouse.LASTUSEDSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for automatic warehouse type
	 * @param arg automatic warehouse type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmploymentType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareHouse.EMPLOYMENTTYPE.toString(), arg);
	}
	/**
	 * Set search value for automatic warehouse type
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
	 * Set update value for automatic warehouse type
	 * @param arg Update value for automatic warehouse type
	 */
	public void updateEmploymentType(int arg)
	{
		setUpdValue(_Prefix + WareHouse.EMPLOYMENTTYPE.toString(), arg);
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
		return "$Id: WareHouseAlterKey.java,v 1.3 2006/11/13 04:32:56 suresh Exp $" ;
	}
}
