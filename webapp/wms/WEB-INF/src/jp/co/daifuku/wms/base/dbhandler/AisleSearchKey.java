//$Id: AisleSearchKey.java,v 1.3 2006/11/13 04:33:12 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */



import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.Aisle;

/**
 * This is the search key class for AISLE use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:12 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class AisleSearchKey
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
	public AisleSearchKey()
	{
		super(new Aisle()) ;
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
		setValue(_Prefix + Aisle.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.STATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + Aisle.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Aisle.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationNumberGroup(int num)
	{
		setGroup(_Prefix + Aisle.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(_Prefix + Aisle.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Aisle.STATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.WHSTATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + Aisle.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Aisle.WHSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse station number
	 * @param num grouping order
	 */
	public void setWHStationNumberGroup(int num)
	{
		setGroup(_Prefix + Aisle.WHSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWHStationNumberCollect()
	{
		setCollect(_Prefix + Aisle.WHSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch warehouse station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWHStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Aisle.WHSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  aisle number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.AISLENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  aisle number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNumber(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.AISLENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  aisle number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNumber(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.AISLENUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  aisle number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNumber(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.AISLENUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for aisle number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAisleNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Aisle.AISLENUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order aisle number
	 * @param num grouping order
	 */
	public void setAisleNumberGroup(int num)
	{
		setGroup(_Prefix + Aisle.AISLENUMBER.toString(), num);
	}

	/**
	 * Fetch aisle number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAisleNumberCollect()
	{
		setCollect(_Prefix + Aisle.AISLENUMBER.toString(), "");
	}

	/**
	 * Fetch aisle number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAisleNumberCollect(String compcode)
	{
		setCollect(_Prefix + Aisle.AISLENUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  controller number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  controller number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  controller number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.CONTROLLERNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + Aisle.CONTROLLERNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for controller number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setControllerNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Aisle.CONTROLLERNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order controller number
	 * @param num grouping order
	 */
	public void setControllerNumberGroup(int num)
	{
		setGroup(_Prefix + Aisle.CONTROLLERNUMBER.toString(), num);
	}

	/**
	 * Fetch controller number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setControllerNumberCollect()
	{
		setCollect(_Prefix + Aisle.CONTROLLERNUMBER.toString(), "");
	}

	/**
	 * Fetch controller number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setControllerNumberCollect(String compcode)
	{
		setCollect(_Prefix + Aisle.CONTROLLERNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  double deep type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDoubleDeepKind(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.DOUBLEDEEPKIND.toString(), arg);
	}
	/**
	 *Set search value for  double deep type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDoubleDeepKind(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.DOUBLEDEEPKIND.toString(), arg);
	}
	/**
	 *Set search value for  double deep type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDoubleDeepKind(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.DOUBLEDEEPKIND.toString(), arg, compcode);
	}
	/**
	 *Set search value for  double deep type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDoubleDeepKind(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.DOUBLEDEEPKIND.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for double deep type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setDoubleDeepKindOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Aisle.DOUBLEDEEPKIND.toString(), num, bool);
	}

	/**
	 * Set grouping order double deep type
	 * @param num grouping order
	 */
	public void setDoubleDeepKindGroup(int num)
	{
		setGroup(_Prefix + Aisle.DOUBLEDEEPKIND.toString(), num);
	}

	/**
	 * Fetch double deep type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setDoubleDeepKindCollect()
	{
		setCollect(_Prefix + Aisle.DOUBLEDEEPKIND.toString(), "");
	}

	/**
	 * Fetch double deep type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setDoubleDeepKindCollect(String compcode)
	{
		setCollect(_Prefix + Aisle.DOUBLEDEEPKIND.toString(), compcode);
	}

	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.STATUS.toString(), arg, compcode);
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
		setValue(_Prefix + Aisle.STATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Aisle.STATUS.toString(), num, bool);
	}

	/**
	 * Set grouping order status
	 * @param num grouping order
	 */
	public void setStatusGroup(int num)
	{
		setGroup(_Prefix + Aisle.STATUS.toString(), num);
	}

	/**
	 * Fetch status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusCollect()
	{
		setCollect(_Prefix + Aisle.STATUS.toString(), "");
	}

	/**
	 * Fetch status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusCollect(String compcode)
	{
		setCollect(_Prefix + Aisle.STATUS.toString(), compcode);
	}

	/**
	 *Set search value for  inventory controll check flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckFlag(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.INVENTORYCHECKFLAG.toString(), arg);
	}
	/**
	 *Set search value for  inventory controll check flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckFlag(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.INVENTORYCHECKFLAG.toString(), arg);
	}
	/**
	 *Set search value for  inventory controll check flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckFlag(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.INVENTORYCHECKFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  inventory controll check flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckFlag(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.INVENTORYCHECKFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for inventory controll check flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInventoryCheckFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Aisle.INVENTORYCHECKFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order inventory controll check flag
	 * @param num grouping order
	 */
	public void setInventoryCheckFlagGroup(int num)
	{
		setGroup(_Prefix + Aisle.INVENTORYCHECKFLAG.toString(), num);
	}

	/**
	 * Fetch inventory controll check flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInventoryCheckFlagCollect()
	{
		setCollect(_Prefix + Aisle.INVENTORYCHECKFLAG.toString(), "");
	}

	/**
	 * Fetch inventory controll check flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInventoryCheckFlagCollect(String compcode)
	{
		setCollect(_Prefix + Aisle.INVENTORYCHECKFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  last used bank
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.LASTUSEDBANK.toString(), arg);
	}
	/**
	 *Set search value for  last used bank
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBank(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.LASTUSEDBANK.toString(), arg);
	}
	/**
	 *Set search value for  last used bank
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBank(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.LASTUSEDBANK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last used bank
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBank(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.LASTUSEDBANK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for last used bank
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUsedBankOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Aisle.LASTUSEDBANK.toString(), num, bool);
	}

	/**
	 * Set grouping order last used bank
	 * @param num grouping order
	 */
	public void setLastUsedBankGroup(int num)
	{
		setGroup(_Prefix + Aisle.LASTUSEDBANK.toString(), num);
	}

	/**
	 * Fetch last used bank info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUsedBankCollect()
	{
		setCollect(_Prefix + Aisle.LASTUSEDBANK.toString(), "");
	}

	/**
	 * Fetch last used bank info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUsedBankCollect(String compcode)
	{
		setCollect(_Prefix + Aisle.LASTUSEDBANK.toString(), compcode);
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
		return "$Id: AisleSearchKey.java,v 1.3 2006/11/13 04:33:12 suresh Exp $" ;
	}
}
