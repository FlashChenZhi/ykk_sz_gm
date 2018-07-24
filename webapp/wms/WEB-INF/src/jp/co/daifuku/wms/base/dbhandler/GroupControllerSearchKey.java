//$Id: GroupControllerSearchKey.java,v 1.3 2006/11/13 04:33:09 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.GroupController;

/**
 * This is the search key class for GROUPCONTROLLER use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:09 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class GroupControllerSearchKey
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
	public GroupControllerSearchKey()
	{
		super(new GroupController()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  controller number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  controller number
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
	 * Set sort order for controller number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setControllerNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + GroupController.CONTROLLERNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order controller number
	 * @param num grouping order
	 */
	public void setControllerNumberGroup(int num)
	{
		setGroup(_Prefix + GroupController.CONTROLLERNUMBER.toString(), num);
	}

	/**
	 * Fetch controller number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setControllerNumberCollect()
	{
		setCollect(_Prefix + GroupController.CONTROLLERNUMBER.toString(), "");
	}

	/**
	 * Fetch controller number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setControllerNumberCollect(String compcode)
	{
		setCollect(_Prefix + GroupController.CONTROLLERNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
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
	 * Set sort order for status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusOrder(int num, boolean bool)
	{
		setOrder(_Prefix + GroupController.STATUS.toString(), num, bool);
	}

	/**
	 * Set grouping order status
	 * @param num grouping order
	 */
	public void setStatusGroup(int num)
	{
		setGroup(_Prefix + GroupController.STATUS.toString(), num);
	}

	/**
	 * Fetch status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusCollect()
	{
		setCollect(_Prefix + GroupController.STATUS.toString(), "");
	}

	/**
	 * Fetch status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusCollect(String compcode)
	{
		setCollect(_Prefix + GroupController.STATUS.toString(), compcode);
	}

	/**
	 *Set search value for  IP address
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIPAddress(String arg) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.IPADDRESS.toString(), arg);
	}
	/**
	 *Set search value for  IP address
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
	 * Set sort order for IP address
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setIPAddressOrder(int num, boolean bool)
	{
		setOrder(_Prefix + GroupController.IPADDRESS.toString(), num, bool);
	}

	/**
	 * Set grouping order IP address
	 * @param num grouping order
	 */
	public void setIPAddressGroup(int num)
	{
		setGroup(_Prefix + GroupController.IPADDRESS.toString(), num);
	}

	/**
	 * Fetch IP address info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setIPAddressCollect()
	{
		setCollect(_Prefix + GroupController.IPADDRESS.toString(), "");
	}

	/**
	 * Fetch IP address info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setIPAddressCollect(String compcode)
	{
		setCollect(_Prefix + GroupController.IPADDRESS.toString(), compcode);
	}

	/**
	 *Set search value for  port number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPort(int arg) throws ReadWriteException
	{
		setValue(_Prefix + GroupController.PORT.toString(), arg);
	}
	/**
	 *Set search value for  port number
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
	 * Set sort order for port number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPortOrder(int num, boolean bool)
	{
		setOrder(_Prefix + GroupController.PORT.toString(), num, bool);
	}

	/**
	 * Set grouping order port number
	 * @param num grouping order
	 */
	public void setPortGroup(int num)
	{
		setGroup(_Prefix + GroupController.PORT.toString(), num);
	}

	/**
	 * Fetch port number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPortCollect()
	{
		setCollect(_Prefix + GroupController.PORT.toString(), "");
	}

	/**
	 * Fetch port number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPortCollect(String compcode)
	{
		setCollect(_Prefix + GroupController.PORT.toString(), compcode);
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
		return "$Id: GroupControllerSearchKey.java,v 1.3 2006/11/13 04:33:09 suresh Exp $" ;
	}
}
