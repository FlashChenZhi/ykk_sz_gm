//$Id: ShelfAlterKey.java,v 1.3 2006/11/13 04:33:02 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.Shelf;

/**
 * Update key class for SHELF use
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:02 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ShelfAlterKey
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
	public ShelfAlterKey()
	{
		super(new Shelf()) ;
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
		setValue(_Prefix + Shelf.STATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + Shelf.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for station number
	 * @param arg Update value for station number
	 */
	public void updateStationNumber(String arg)
	{
		setUpdValue(_Prefix + Shelf.STATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for bank
	 * @param arg bank<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBank(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBANK.toString(), arg);
	}
	/**
	 * Set search value for bank
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBank(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBANK.toString(), arg);
	}
	/**
	 *Set search value for  bank
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBank(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBANK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  bank
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBank(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBANK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for bank
	 * @param arg Update value for bank
	 */
	public void updateNBank(int arg)
	{
		setUpdValue(_Prefix + Shelf.NBANK.toString(), arg);
	}

	/**
	 * Set search value for bay
	 * @param arg bay<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBay(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBAY.toString(), arg);
	}
	/**
	 * Set search value for bay
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBay(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBAY.toString(), arg);
	}
	/**
	 *Set search value for  bay
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBay(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBAY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  bay
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNBay(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NBAY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for bay
	 * @param arg Update value for bay
	 */
	public void updateNBay(int arg)
	{
		setUpdValue(_Prefix + Shelf.NBAY.toString(), arg);
	}

	/**
	 * Set search value for level
	 * @param arg level<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNLevel(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NLEVEL.toString(), arg);
	}
	/**
	 * Set search value for level
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNLevel(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NLEVEL.toString(), arg);
	}
	/**
	 *Set search value for  level
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNLevel(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NLEVEL.toString(), arg, compcode);
	}
	/**
	 *Set search value for  level
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNLevel(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.NLEVEL.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for level
	 * @param arg Update value for level
	 */
	public void updateNLevel(int arg)
	{
		setUpdValue(_Prefix + Shelf.NLEVEL.toString(), arg);
	}

	/**
	 * Set search value for warehouse station number
	 * @param arg warehouse station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.WHSTATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + Shelf.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for warehouse station number
	 * @param arg Update value for warehouse station number
	 */
	public void updateWHStationNumber(String arg)
	{
		setUpdValue(_Prefix + Shelf.WHSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for status
	 * @param arg status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATUS.toString(), arg);
	}
	/**
	 * Set search value for status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.STATUS.toString(), arg, compcode);
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
		setValue(_Prefix + Shelf.STATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for status
	 * @param arg Update value for status
	 */
	public void updateStatus(int arg)
	{
		setUpdValue(_Prefix + Shelf.STATUS.toString(), arg);
	}

	/**
	 * Set search value for location status
	 * @param arg location status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPresence(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRESENCE.toString(), arg);
	}
	/**
	 * Set search value for location status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPresence(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRESENCE.toString(), arg);
	}
	/**
	 *Set search value for  location status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPresence(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRESENCE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  location status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPresence(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRESENCE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for location status
	 * @param arg Update value for location status
	 */
	public void updatePresence(int arg)
	{
		setUpdValue(_Prefix + Shelf.PRESENCE.toString(), arg);
	}

	/**
	 * Set search value for hard zone
	 * @param arg hard zone<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.HARDZONEID.toString(), arg);
	}
	/**
	 * Set search value for hard zone
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.HARDZONEID.toString(), arg);
	}
	/**
	 *Set search value for  hard zone
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.HARDZONEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  hard zone
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHardZoneID(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.HARDZONEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for hard zone
	 * @param arg Update value for hard zone
	 */
	public void updateHardZoneID(int arg)
	{
		setUpdValue(_Prefix + Shelf.HARDZONEID.toString(), arg);
	}

	/**
	 * Set search value for soft zone
	 * @param arg soft zone<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSoftZoneID(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SOFTZONEID.toString(), arg);
	}
	/**
	 * Set search value for soft zone
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSoftZoneID(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SOFTZONEID.toString(), arg);
	}
	/**
	 *Set search value for  soft zone
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSoftZoneID(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SOFTZONEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  soft zone
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSoftZoneID(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SOFTZONEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for soft zone
	 * @param arg Update value for soft zone
	 */
	public void updateSoftZoneID(int arg)
	{
		setUpdValue(_Prefix + Shelf.SOFTZONEID.toString(), arg);
	}

	/**
	 * Set search value for parent station number
	 * @param arg parent station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for parent station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  parent station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  parent station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for parent station number
	 * @param arg Update value for parent station number
	 */
	public void updateParentStationNumber(String arg)
	{
		setUpdValue(_Prefix + Shelf.PARENTSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for location restriction flag
	 * @param arg location restriction flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessNgFlag(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.ACCESSNGFLAG.toString(), arg);
	}
	/**
	 * Set search value for location restriction flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessNgFlag(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.ACCESSNGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  location restriction flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessNgFlag(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.ACCESSNGFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  location restriction flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAccessNgFlag(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.ACCESSNGFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for location restriction flag
	 * @param arg Update value for location restriction flag
	 */
	public void updateAccessNgFlag(int arg)
	{
		setUpdValue(_Prefix + Shelf.ACCESSNGFLAG.toString(), arg);
	}

	/**
	 * Set search value for location search order
	 * @param arg location search order<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRIORITY.toString(), arg);
	}
	/**
	 * Set search value for location search order
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRIORITY.toString(), arg);
	}
	/**
	 *Set search value for  location search order
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRIORITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  location search order
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PRIORITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for location search order
	 * @param arg Update value for location search order
	 */
	public void updatePriority(int arg)
	{
		setUpdValue(_Prefix + Shelf.PRIORITY.toString(), arg);
	}

	/**
	 * Set search value for pair station number
	 * @param arg pair station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for pair station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  pair station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  pair station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPairStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for pair station number
	 * @param arg Update value for pair station number
	 */
	public void updatePairStationNumber(String arg)
	{
		setUpdValue(_Prefix + Shelf.PAIRSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for from side, deep number
	 * @param arg from side, deep number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SIDE.toString(), arg);
	}
	/**
	 * Set search value for from side, deep number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SIDE.toString(), arg);
	}
	/**
	 *Set search value for  from side, deep number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SIDE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  from side, deep number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSide(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Shelf.SIDE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for from side, deep number
	 * @param arg Update value for from side, deep number
	 */
	public void updateSide(int arg)
	{
		setUpdValue(_Prefix + Shelf.SIDE.toString(), arg);
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
		return "$Id: ShelfAlterKey.java,v 1.3 2006/11/13 04:33:02 suresh Exp $" ;
	}
}
