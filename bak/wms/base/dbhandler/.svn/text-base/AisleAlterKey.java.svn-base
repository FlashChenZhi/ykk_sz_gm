//$Id: AisleAlterKey.java,v 1.3 2006/11/13 04:33:13 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.Aisle;

/**
 * Update key class for AISLE use
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:13 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class AisleAlterKey
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
	public AisleAlterKey()
	{
		super(new Aisle()) ;
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
		setValue(_Prefix + Aisle.STATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for station number
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
	 * Set update value for station number
	 * @param arg Update value for station number
	 */
	public void updateStationNumber(String arg)
	{
		setUpdValue(_Prefix + Aisle.STATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for warehouse station number
	 * @param arg warehouse station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for warehouse station number
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
	 * Set update value for warehouse station number
	 * @param arg Update value for warehouse station number
	 */
	public void updateWHStationNumber(String arg)
	{
		setUpdValue(_Prefix + Aisle.WHSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for aisle number
	 * @param arg aisle number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.AISLENUMBER.toString(), arg);
	}
	/**
	 * Set search value for aisle number
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
	 * Set update value for aisle number
	 * @param arg Update value for aisle number
	 */
	public void updateAisleNumber(int arg)
	{
		setUpdValue(_Prefix + Aisle.AISLENUMBER.toString(), arg);
	}

	/**
	 * Set search value for controller number
	 * @param arg controller number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 * Set search value for controller number
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
	 * Set update value for controller number
	 * @param arg Update value for controller number
	 */
	public void updateControllerNumber(int arg)
	{
		setUpdValue(_Prefix + Aisle.CONTROLLERNUMBER.toString(), arg);
	}

	/**
	 * Set search value for double deep type
	 * @param arg double deep type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDoubleDeepKind(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.DOUBLEDEEPKIND.toString(), arg);
	}
	/**
	 * Set search value for double deep type
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
	 * Set update value for double deep type
	 * @param arg Update value for double deep type
	 */
	public void updateDoubleDeepKind(int arg)
	{
		setUpdValue(_Prefix + Aisle.DOUBLEDEEPKIND.toString(), arg);
	}

	/**
	 * Set search value for status
	 * @param arg status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.STATUS.toString(), arg);
	}
	/**
	 * Set search value for status
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
	 * Set update value for status
	 * @param arg Update value for status
	 */
	public void updateStatus(int arg)
	{
		setUpdValue(_Prefix + Aisle.STATUS.toString(), arg);
	}

	/**
	 * Set search value for inventory controll check flag
	 * @param arg inventory controll check flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckFlag(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.INVENTORYCHECKFLAG.toString(), arg);
	}
	/**
	 * Set search value for inventory controll check flag
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
	 * Set update value for inventory controll check flag
	 * @param arg Update value for inventory controll check flag
	 */
	public void updateInventoryCheckFlag(int arg)
	{
		setUpdValue(_Prefix + Aisle.INVENTORYCHECKFLAG.toString(), arg);
	}

	/**
	 * Set search value for last used bank
	 * @param arg last used bank<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Aisle.LASTUSEDBANK.toString(), arg);
	}
	/**
	 * Set search value for last used bank
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
	 * Set update value for last used bank
	 * @param arg Update value for last used bank
	 */
	public void updateLastUsedBank(int arg)
	{
		setUpdValue(_Prefix + Aisle.LASTUSEDBANK.toString(), arg);
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
		return "$Id: AisleAlterKey.java,v 1.3 2006/11/13 04:33:13 suresh Exp $" ;
	}
}
