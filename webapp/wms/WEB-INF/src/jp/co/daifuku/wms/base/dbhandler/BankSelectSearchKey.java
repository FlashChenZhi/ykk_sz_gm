//$Id: BankSelectSearchKey.java,v 1.3 2006/11/13 04:33:11 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.BankSelect;

/**
 * This is the search key class for BANKSELECT use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:11 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class BankSelectSearchKey
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
	public BankSelectSearchKey()
	{
		super(new BankSelect()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  Warehouse station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  Warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  Warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.WHSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Warehouse station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + BankSelect.WHSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order Warehouse station number
	 * @param num grouping order
	 */
	public void setWHStationNumberGroup(int num)
	{
		setGroup(_Prefix + BankSelect.WHSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch Warehouse station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWHStationNumberCollect()
	{
		setCollect(_Prefix + BankSelect.WHSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch Warehouse station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWHStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + BankSelect.WHSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  Aisle station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  Aisle station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  Aisle station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.AISLESTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Aisle station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.AISLESTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Aisle station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAisleStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + BankSelect.AISLESTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order Aisle station number
	 * @param num grouping order
	 */
	public void setAisleStationNumberGroup(int num)
	{
		setGroup(_Prefix + BankSelect.AISLESTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch Aisle station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAisleStationNumberCollect()
	{
		setCollect(_Prefix + BankSelect.AISLESTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch Aisle station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAisleStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + BankSelect.AISLESTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  Bank
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNbank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.NBANK.toString(), arg);
	}
	/**
	 *Set search value for  Bank
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNbank(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.NBANK.toString(), arg);
	}
	/**
	 *Set search value for  Bank
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNbank(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.NBANK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Bank
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNbank(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.NBANK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Bank
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setNbankOrder(int num, boolean bool)
	{
		setOrder(_Prefix + BankSelect.NBANK.toString(), num, bool);
	}

	/**
	 * Set grouping order Bank
	 * @param num grouping order
	 */
	public void setNbankGroup(int num)
	{
		setGroup(_Prefix + BankSelect.NBANK.toString(), num);
	}

	/**
	 * Fetch Bank info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setNbankCollect()
	{
		setCollect(_Prefix + BankSelect.NBANK.toString(), "");
	}

	/**
	 * Fetch Bank info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setNbankCollect(String compcode)
	{
		setCollect(_Prefix + BankSelect.NBANK.toString(), compcode);
	}

	/**
	 *Set search value for  Pair bank number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairBank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.PAIRBANK.toString(), arg);
	}
	/**
	 *Set search value for  Pair bank number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairBank(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.PAIRBANK.toString(), arg);
	}
	/**
	 *Set search value for  Pair bank number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairBank(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.PAIRBANK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Pair bank number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairBank(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.PAIRBANK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Pair bank number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPairBankOrder(int num, boolean bool)
	{
		setOrder(_Prefix + BankSelect.PAIRBANK.toString(), num, bool);
	}

	/**
	 * Set grouping order Pair bank number
	 * @param num grouping order
	 */
	public void setPairBankGroup(int num)
	{
		setGroup(_Prefix + BankSelect.PAIRBANK.toString(), num);
	}

	/**
	 * Fetch Pair bank number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPairBankCollect()
	{
		setCollect(_Prefix + BankSelect.PAIRBANK.toString(), "");
	}

	/**
	 * Fetch Pair bank number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPairBankCollect(String compcode)
	{
		setCollect(_Prefix + BankSelect.PAIRBANK.toString(), compcode);
	}

	/**
	 *Set search value for  front, depth type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.SIDE.toString(), arg);
	}
	/**
	 *Set search value for  front, depth type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.SIDE.toString(), arg);
	}
	/**
	 *Set search value for  front, depth type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.SIDE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  front, depth type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.SIDE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for front, depth type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSideOrder(int num, boolean bool)
	{
		setOrder(_Prefix + BankSelect.SIDE.toString(), num, bool);
	}

	/**
	 * Set grouping order front, depth type
	 * @param num grouping order
	 */
	public void setSideGroup(int num)
	{
		setGroup(_Prefix + BankSelect.SIDE.toString(), num);
	}

	/**
	 * Fetch front, depth type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSideCollect()
	{
		setCollect(_Prefix + BankSelect.SIDE.toString(), "");
	}

	/**
	 * Fetch front, depth type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSideCollect(String compcode)
	{
		setCollect(_Prefix + BankSelect.SIDE.toString(), compcode);
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
		return "$Id: BankSelectSearchKey.java,v 1.3 2006/11/13 04:33:11 suresh Exp $" ;
	}
}
