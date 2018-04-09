//$Id: TerminalAreaAlterKey.java,v 1.3 2006/11/13 04:32:57 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.TerminalArea;

/**
 * Update key class for TERMINALAREA use
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:32:57 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class TerminalAreaAlterKey
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
	public TerminalAreaAlterKey()
	{
		super(new TerminalArea()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for terminal number
	 * @param arg terminal number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.TERMINALNUMBER.toString(), arg);
	}
	/**
	 * Set search value for terminal number
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
	 * Set update value for terminal number
	 * @param arg Update value for terminal number
	 */
	public void updateTerminalNumber(String arg)
	{
		setUpdValue(_Prefix + TerminalArea.TERMINALNUMBER.toString(), arg);
	}

	/**
	 * Set search value for area id
	 * @param arg area id<br>
	 * Set search value for Long
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaId(int arg) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.AREAID.toString(), arg);
	}
	/**
	 * Set search value for area id
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
	 * Set update value for area id
	 * @param arg Update value for area id
	 */
	public void updateAreaId(int arg)
	{
		setUpdValue(_Prefix + TerminalArea.AREAID.toString(), arg);
	}

	/**
	 * Set search value for station number
	 * @param arg station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + TerminalArea.STATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for station number
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
	 * Set update value for station number
	 * @param arg Update value for station number
	 */
	public void updateStationNumber(String arg)
	{
		setUpdValue(_Prefix + TerminalArea.STATIONNUMBER.toString(), arg);
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
		return "$Id: TerminalAreaAlterKey.java,v 1.3 2006/11/13 04:32:57 suresh Exp $" ;
	}
}
