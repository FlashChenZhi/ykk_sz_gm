//$Id: GroupControllerAlterKey.java,v 1.3 2006/11/13 04:33:10 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.GroupController;

/**
 * Update key class for GROUPCONTROLLER use
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:10 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class GroupControllerAlterKey
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
	public GroupControllerAlterKey()
	{
		super(new GroupController()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for controller number
	 * @param arg controller number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 * Set search value for controller number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  controller number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.CONTROLLERNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + GroupController.CONTROLLERNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for controller number
	 * @param arg Update value for controller number
	 */
	public void updateControllerNumber(int arg)
	{
		setUpdValue(_Prefix + GroupController.CONTROLLERNUMBER.toString(), arg);
	}

	/**
	 * Set search value for status
	 * @param arg status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.STATUS.toString(), arg);
	}
	/**
	 * Set search value for status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.STATUS.toString(), arg, compcode);
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
		setValue(_Prefix + GroupController.STATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for status
	 * @param arg Update value for status
	 */
	public void updateStatus(int arg)
	{
		setUpdValue(_Prefix + GroupController.STATUS.toString(), arg);
	}

	/**
	 * Set search value for IP address
	 * @param arg IP address<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIPAddress(String arg) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.IPADDRESS.toString(), arg);
	}
	/**
	 * Set search value for IP address
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIPAddress(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.IPADDRESS.toString(), arg);
	}
	/**
	 *Set search value for  IP address
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIPAddress(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.IPADDRESS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  IP address
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIPAddress(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.IPADDRESS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for IP address
	 * @param arg Update value for IP address
	 */
	public void updateIPAddress(String arg)
	{
		setUpdValue(_Prefix + GroupController.IPADDRESS.toString(), arg);
	}

	/**
	 * Set search value for port number
	 * @param arg port number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPort(int arg) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.PORT.toString(), arg);
	}
	/**
	 * Set search value for port number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPort(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.PORT.toString(), arg);
	}
	/**
	 *Set search value for  port number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPort(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.PORT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  port number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPort(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.PORT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for port number
	 * @param arg Update value for port number
	 */
	public void updatePort(int arg)
	{
		setUpdValue(_Prefix + GroupController.PORT.toString(), arg);
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
		return "$Id: GroupControllerAlterKey.java,v 1.3 2006/11/13 04:33:10 suresh Exp $" ;
	}
}
