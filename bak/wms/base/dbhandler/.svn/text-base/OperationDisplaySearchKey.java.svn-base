//$Id: OperationDisplaySearchKey.java,v 1.3 2006/11/13 04:33:04 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.OperationDisplay;

/**
 * This is the search key class for OPERATIONDISPLAY use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:04 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class OperationDisplaySearchKey
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
	public OperationDisplaySearchKey()
	{
		super(new OperationDisplay()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.CARRYKEY.toString(), arg);
	}
	/**
	 *Set search value for  carry key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.CARRYKEY.toString(), arg);
	}
	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.CARRYKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.CARRYKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for carry key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCarryKeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + OperationDisplay.CARRYKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order carry key
	 * @param num grouping order
	 */
	public void setCarryKeyGroup(int num)
	{
		setGroup(_Prefix + OperationDisplay.CARRYKEY.toString(), num);
	}

	/**
	 * Fetch carry key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCarryKeyCollect()
	{
		setCollect(_Prefix + OperationDisplay.CARRYKEY.toString(), "");
	}

	/**
	 * Fetch carry key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCarryKeyCollect(String compcode)
	{
		setCollect(_Prefix + OperationDisplay.CARRYKEY.toString(), compcode);
	}

	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.STATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + OperationDisplay.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + OperationDisplay.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationNumberGroup(int num)
	{
		setGroup(_Prefix + OperationDisplay.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(_Prefix + OperationDisplay.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + OperationDisplay.STATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  arrival date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.ARRIVALDATE.toString(), arg);
	}
	/**
	 *Set search value for  arrival date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.ARRIVALDATE.toString(), arg);
	}
	/**
	 *Set search value for  arrival date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.ARRIVALDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  arrival date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.ARRIVALDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for arrival date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setArrivalDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + OperationDisplay.ARRIVALDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order arrival date
	 * @param num grouping order
	 */
	public void setArrivalDateGroup(int num)
	{
		setGroup(_Prefix + OperationDisplay.ARRIVALDATE.toString(), num);
	}

	/**
	 * Fetch arrival date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setArrivalDateCollect()
	{
		setCollect(_Prefix + OperationDisplay.ARRIVALDATE.toString(), "");
	}

	/**
	 * Fetch arrival date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setArrivalDateCollect(String compcode)
	{
		setCollect(_Prefix + OperationDisplay.ARRIVALDATE.toString(), compcode);
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
		return "$Id: OperationDisplaySearchKey.java,v 1.3 2006/11/13 04:33:04 suresh Exp $" ;
	}
}
