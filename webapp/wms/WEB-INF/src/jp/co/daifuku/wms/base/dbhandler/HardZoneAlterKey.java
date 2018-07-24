//$Id: HardZoneAlterKey.java,v 1.3 2006/11/13 04:33:09 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.HardZone;

/**
 * Update key class for HARDZONE use
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


public class HardZoneAlterKey
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
	public HardZoneAlterKey()
	{
		super(new HardZone()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for hard zone id
	 * @param arg hard zone id<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HARDZONEID.toString(), arg);
	}
	/**
	 * Set search value for hard zone id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HARDZONEID.toString(), arg);
	}
	/**
	 *Set search value for  hard zone id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HARDZONEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  hard zone id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HARDZONEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for hard zone id
	 * @param arg Update value for hard zone id
	 */
	public void updateHardZoneID(int arg)
	{
		setUpdValue(_Prefix + HardZone.HARDZONEID.toString(), arg);
	}

	/**
	 * Set search value for zone name
	 * @param arg zone name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.NAME.toString(), arg);
	}
	/**
	 * Set search value for zone name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.NAME.toString(), arg);
	}
	/**
	 *Set search value for  zone name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.NAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  zone name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.NAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for zone name
	 * @param arg Update value for zone name
	 */
	public void updateName(String arg)
	{
		setUpdValue(_Prefix + HardZone.NAME.toString(), arg);
	}

	/**
	 * Set search value for warehouse station number
	 * @param arg warehouse station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.WHSTATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + HardZone.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for warehouse station number
	 * @param arg Update value for warehouse station number
	 */
	public void updateWHStationNumber(String arg)
	{
		setUpdValue(_Prefix + HardZone.WHSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for height of load
	 * @param arg height of load<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HEIGHT.toString(), arg);
	}
	/**
	 * Set search value for height of load
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HEIGHT.toString(), arg);
	}
	/**
	 *Set search value for  height of load
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HEIGHT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  height of load
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.HEIGHT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for height of load
	 * @param arg Update value for height of load
	 */
	public void updateHeight(int arg)
	{
		setUpdValue(_Prefix + HardZone.HEIGHT.toString(), arg);
	}

	/**
	 * Set search value for zone priority order
	 * @param arg zone priority order<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.PRIORITY.toString(), arg);
	}
	/**
	 * Set search value for zone priority order
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.PRIORITY.toString(), arg);
	}
	/**
	 *Set search value for  zone priority order
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.PRIORITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  zone priority order
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.PRIORITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for zone priority order
	 * @param arg Update value for zone priority order
	 */
	public void updatePriority(String arg)
	{
		setUpdValue(_Prefix + HardZone.PRIORITY.toString(), arg);
	}

	/**
	 * Set search value for starting bank number
	 * @param arg starting bank number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBANK.toString(), arg);
	}
	/**
	 * Set search value for starting bank number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBank(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBANK.toString(), arg);
	}
	/**
	 *Set search value for  starting bank number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBank(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBANK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  starting bank number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBank(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBANK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for starting bank number
	 * @param arg Update value for starting bank number
	 */
	public void updateStartBank(int arg)
	{
		setUpdValue(_Prefix + HardZone.STARTBANK.toString(), arg);
	}

	/**
	 * Set search value for starting bay number
	 * @param arg starting bay number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBay(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBAY.toString(), arg);
	}
	/**
	 * Set search value for starting bay number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBay(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBAY.toString(), arg);
	}
	/**
	 *Set search value for  starting bay number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBay(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBAY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  starting bay number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartBay(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTBAY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for starting bay number
	 * @param arg Update value for starting bay number
	 */
	public void updateStartBay(int arg)
	{
		setUpdValue(_Prefix + HardZone.STARTBAY.toString(), arg);
	}

	/**
	 * Set search value for starting level
	 * @param arg starting level<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartLevel(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTLEVEL.toString(), arg);
	}
	/**
	 * Set search value for starting level
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartLevel(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTLEVEL.toString(), arg);
	}
	/**
	 *Set search value for  starting level
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartLevel(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTLEVEL.toString(), arg, compcode);
	}
	/**
	 *Set search value for  starting level
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStartLevel(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.STARTLEVEL.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for starting level
	 * @param arg Update value for starting level
	 */
	public void updateStartLevel(int arg)
	{
		setUpdValue(_Prefix + HardZone.STARTLEVEL.toString(), arg);
	}

	/**
	 * Set search value for last bank number
	 * @param arg last bank number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBANK.toString(), arg);
	}
	/**
	 * Set search value for last bank number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBank(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBANK.toString(), arg);
	}
	/**
	 *Set search value for  last bank number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBank(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBANK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last bank number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBank(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBANK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for last bank number
	 * @param arg Update value for last bank number
	 */
	public void updateEndBank(int arg)
	{
		setUpdValue(_Prefix + HardZone.ENDBANK.toString(), arg);
	}

	/**
	 * Set search value for last bay number
	 * @param arg last bay number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBay(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBAY.toString(), arg);
	}
	/**
	 * Set search value for last bay number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBay(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBAY.toString(), arg);
	}
	/**
	 *Set search value for  last bay number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBay(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBAY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last bay number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndBay(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDBAY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for last bay number
	 * @param arg Update value for last bay number
	 */
	public void updateEndBay(int arg)
	{
		setUpdValue(_Prefix + HardZone.ENDBAY.toString(), arg);
	}

	/**
	 * Set search value for last level number
	 * @param arg last level number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndLevel(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDLEVEL.toString(), arg);
	}
	/**
	 * Set search value for last level number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndLevel(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDLEVEL.toString(), arg);
	}
	/**
	 *Set search value for  last level number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndLevel(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDLEVEL.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last level number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndLevel(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HardZone.ENDLEVEL.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for last level number
	 * @param arg Update value for last level number
	 */
	public void updateEndLevel(int arg)
	{
		setUpdValue(_Prefix + HardZone.ENDLEVEL.toString(), arg);
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
		return "$Id: HardZoneAlterKey.java,v 1.3 2006/11/13 04:33:09 suresh Exp $" ;
	}
}
