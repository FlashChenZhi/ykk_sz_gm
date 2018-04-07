//$Id: BankSelectAlterKey.java,v 1.3 2006/11/13 04:33:11 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.BankSelect;

/**
 * Update key class for BANKSELECT use
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


public class BankSelectAlterKey
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
	public BankSelectAlterKey()
	{
		super(new BankSelect()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for Warehouse station number
	 * @param arg Warehouse station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for Warehouse station number
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
	 * Set update value for Warehouse station number
	 * @param arg Update value for Warehouse station number
	 */
	public void updateWHStationNumber(String arg)
	{
		setUpdValue(_Prefix + BankSelect.WHSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for Aisle station number
	 * @param arg Aisle station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for Aisle station number
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
	 * Set update value for Aisle station number
	 * @param arg Update value for Aisle station number
	 */
	public void updateAisleStationNumber(String arg)
	{
		setUpdValue(_Prefix + BankSelect.AISLESTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for Bank
	 * @param arg Bank<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNbank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.NBANK.toString(), arg);
	}
	/**
	 * Set search value for Bank
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
	 * Set update value for Bank
	 * @param arg Update value for Bank
	 */
	public void updateNbank(int arg)
	{
		setUpdValue(_Prefix + BankSelect.NBANK.toString(), arg);
	}

	/**
	 * Set search value for Pair bank number
	 * @param arg Pair bank number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairBank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.PAIRBANK.toString(), arg);
	}
	/**
	 * Set search value for Pair bank number
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
	 * Set update value for Pair bank number
	 * @param arg Update value for Pair bank number
	 */
	public void updatePairBank(int arg)
	{
		setUpdValue(_Prefix + BankSelect.PAIRBANK.toString(), arg);
	}

	/**
	 * Set search value for front, depth type
	 * @param arg front, depth type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg) throws ReadWriteException
	{
		setValue(_Prefix + BankSelect.SIDE.toString(), arg);
	}
	/**
	 * Set search value for front, depth type
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
	 * Set update value for front, depth type
	 * @param arg Update value for front, depth type
	 */
	public void updateSide(int arg)
	{
		setUpdValue(_Prefix + BankSelect.SIDE.toString(), arg);
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
		return "$Id: BankSelectAlterKey.java,v 1.3 2006/11/13 04:33:11 suresh Exp $" ;
	}
}
