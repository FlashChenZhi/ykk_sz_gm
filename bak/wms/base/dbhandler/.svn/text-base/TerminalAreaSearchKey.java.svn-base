//$Id: TerminalAreaSearchKey.java,v 1.3 2006/11/13 04:32:56 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.TerminalArea;

/**
 * This is the search key class for TERMINALAREA use.
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


public class TerminalAreaSearchKey
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
	public TerminalAreaSearchKey()
	{
		super(new TerminalArea()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  terminal number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.TERMINALNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  terminal number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.TERMINALNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  terminal number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.TERMINALNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  terminal number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.TERMINALNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for terminal number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setTerminalNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + TerminalArea.TERMINALNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order terminal number
	 * @param num grouping order
	 */
	public void setTerminalNumberGroup(int num)
	{
		setGroup(_Prefix + TerminalArea.TERMINALNUMBER.toString(), num);
	}

	/**
	 * Fetch terminal number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setTerminalNumberCollect()
	{
		setCollect(_Prefix + TerminalArea.TERMINALNUMBER.toString(), "");
	}

	/**
	 * Fetch terminal number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setTerminalNumberCollect(String compcode)
	{
		setCollect(_Prefix + TerminalArea.TERMINALNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  area id
	 * @param arg Set search value for Long
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaId(int arg) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.AREAID.toString(), arg);
	}
	/**
	 *Set search value for  area id
	 * @param arg Long array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaId(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.AREAID.toString(), arg);
	}
	/**
	 *Set search value for  area id
	 * @param arg Set search value for Long
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaId(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.AREAID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  area id
	 * @param arg Set search value for Long
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaId(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.AREAID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for area id
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAreaIdOrder(int num, boolean bool)
	{
		setOrder(_Prefix + TerminalArea.AREAID.toString(), num, bool);
	}

	/**
	 * Set grouping order area id
	 * @param num grouping order
	 */
	public void setAreaIdGroup(int num)
	{
		setGroup(_Prefix + TerminalArea.AREAID.toString(), num);
	}

	/**
	 * Fetch area id info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAreaIdCollect()
	{
		setCollect(_Prefix + TerminalArea.AREAID.toString(), "");
	}

	/**
	 * Fetch area id info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAreaIdCollect(String compcode)
	{
		setCollect(_Prefix + TerminalArea.AREAID.toString(), compcode);
	}

	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.STATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + TerminalArea.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + TerminalArea.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationNumberGroup(int num)
	{
		setGroup(_Prefix + TerminalArea.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(_Prefix + TerminalArea.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + TerminalArea.STATIONNUMBER.toString(), compcode);
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
		return "$Id: TerminalAreaSearchKey.java,v 1.3 2006/11/13 04:32:56 suresh Exp $" ;
	}
}
