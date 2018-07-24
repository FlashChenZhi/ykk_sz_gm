//$Id: MachineSearchKey.java,v 1.3 2006/11/13 04:33:06 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.Machine;

/**
 * This is the search key class for MACHINE use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:06 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class MachineSearchKey
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
	public MachineSearchKey()
	{
		super(new Machine()) ;
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
		setValue(_Prefix + Machine.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Machine.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Machine.STATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + Machine.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Machine.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationNumberGroup(int num)
	{
		setGroup(_Prefix + Machine.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(_Prefix + Machine.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Machine.STATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  machine type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMachineType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Machine.MACHINETYPE.toString(), arg);
	}
	/**
	 *Set search value for  machine type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMachineType(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Machine.MACHINETYPE.toString(), arg);
	}
	/**
	 *Set search value for  machine type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMachineType(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Machine.MACHINETYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  machine type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMachineType(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Machine.MACHINETYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for machine type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setMachineTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Machine.MACHINETYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order machine type
	 * @param num grouping order
	 */
	public void setMachineTypeGroup(int num)
	{
		setGroup(_Prefix + Machine.MACHINETYPE.toString(), num);
	}

	/**
	 * Fetch machine type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setMachineTypeCollect()
	{
		setCollect(_Prefix + Machine.MACHINETYPE.toString(), "");
	}

	/**
	 * Fetch machine type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setMachineTypeCollect(String compcode)
	{
		setCollect(_Prefix + Machine.MACHINETYPE.toString(), compcode);
	}

	/**
	 *Set search value for  machine number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMachineNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Machine.MACHINENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  machine number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMachineNumber(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Machine.MACHINENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  machine number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMachineNumber(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Machine.MACHINENUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  machine number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMachineNumber(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Machine.MACHINENUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for machine number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setMachineNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Machine.MACHINENUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order machine number
	 * @param num grouping order
	 */
	public void setMachineNumberGroup(int num)
	{
		setGroup(_Prefix + Machine.MACHINENUMBER.toString(), num);
	}

	/**
	 * Fetch machine number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setMachineNumberCollect()
	{
		setCollect(_Prefix + Machine.MACHINENUMBER.toString(), "");
	}

	/**
	 * Fetch machine number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setMachineNumberCollect(String compcode)
	{
		setCollect(_Prefix + Machine.MACHINENUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setState(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Machine.STATE.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setState(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Machine.STATE.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setState(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Machine.STATE.toString(), arg, compcode);
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
	public void setState(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Machine.STATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Machine.STATE.toString(), num, bool);
	}

	/**
	 * Set grouping order status
	 * @param num grouping order
	 */
	public void setStateGroup(int num)
	{
		setGroup(_Prefix + Machine.STATE.toString(), num);
	}

	/**
	 * Fetch status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStateCollect()
	{
		setCollect(_Prefix + Machine.STATE.toString(), "");
	}

	/**
	 * Fetch status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStateCollect(String compcode)
	{
		setCollect(_Prefix + Machine.STATE.toString(), compcode);
	}

	/**
	 *Set search value for  error code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Machine.ERRORCODE.toString(), arg);
	}
	/**
	 *Set search value for  error code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Machine.ERRORCODE.toString(), arg);
	}
	/**
	 *Set search value for  error code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Machine.ERRORCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  error code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Machine.ERRORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for error code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setErrorCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Machine.ERRORCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order error code
	 * @param num grouping order
	 */
	public void setErrorCodeGroup(int num)
	{
		setGroup(_Prefix + Machine.ERRORCODE.toString(), num);
	}

	/**
	 * Fetch error code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setErrorCodeCollect()
	{
		setCollect(_Prefix + Machine.ERRORCODE.toString(), "");
	}

	/**
	 * Fetch error code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setErrorCodeCollect(String compcode)
	{
		setCollect(_Prefix + Machine.ERRORCODE.toString(), compcode);
	}

	/**
	 *Set search value for  controller number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Machine.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  controller number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Machine.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  controller number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Machine.CONTROLLERNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  controller number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Machine.CONTROLLERNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for controller number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setControllerNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Machine.CONTROLLERNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order controller number
	 * @param num grouping order
	 */
	public void setControllerNumberGroup(int num)
	{
		setGroup(_Prefix + Machine.CONTROLLERNUMBER.toString(), num);
	}

	/**
	 * Fetch controller number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setControllerNumberCollect()
	{
		setCollect(_Prefix + Machine.CONTROLLERNUMBER.toString(), "");
	}

	/**
	 * Fetch controller number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setControllerNumberCollect(String compcode)
	{
		setCollect(_Prefix + Machine.CONTROLLERNUMBER.toString(), compcode);
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
		return "$Id: MachineSearchKey.java,v 1.3 2006/11/13 04:33:06 suresh Exp $" ;
	}
}
