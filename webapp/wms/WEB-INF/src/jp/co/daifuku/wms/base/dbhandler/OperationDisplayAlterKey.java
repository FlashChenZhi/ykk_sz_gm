//$Id: OperationDisplayAlterKey.java,v 1.3 2006/11/13 04:33:04 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.OperationDisplay;

/**
 * Update key class for OPERATIONDISPLAY use
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


public class OperationDisplayAlterKey
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
	public OperationDisplayAlterKey()
	{
		super(new OperationDisplay()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for carry key
	 * @param arg carry key<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.CARRYKEY.toString(), arg);
	}
	/**
	 * Set search value for carry key
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
	 * Set update value for carry key
	 * @param arg Update value for carry key
	 */
	public void updateCarryKey(String arg)
	{
		setUpdValue(_Prefix + OperationDisplay.CARRYKEY.toString(), arg);
	}

	/**
	 * Set search value for station number
	 * @param arg station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.STATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for station number
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
	 * Set update value for station number
	 * @param arg Update value for station number
	 */
	public void updateStationNumber(String arg)
	{
		setUpdValue(_Prefix + OperationDisplay.STATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for arrival date
	 * @param arg arrival date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + OperationDisplay.ARRIVALDATE.toString(), arg);
	}
	/**
	 * Set search value for arrival date
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
	 * Set update value for arrival date
	 * @param arg Update value for arrival date
	 */
	public void updateArrivalDate(String arg)
	{
		setUpdValue(_Prefix + OperationDisplay.ARRIVALDATE.toString(), arg);
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
		return "$Id: OperationDisplayAlterKey.java,v 1.3 2006/11/13 04:33:04 suresh Exp $" ;
	}
}
