//$Id: StationSearchKey.java,v 1.3 2006/11/13 04:32:59 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.Station;

/**
 * This is the search key class for STATION use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:32:59 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class StationSearchKey
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
	public StationSearchKey()
	{
		super(new Station()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + Station.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.STATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order station number
	 * @param num grouping order
	 */
	public void setStationNumberGroup(int num)
	{
		setGroup(_Prefix + Station.STATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNumberCollect()
	{
		setCollect(_Prefix + Station.STATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Station.STATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  maximum palette qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxPaletteQuantity(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.MAXPALETTEQUANTITY.toString(), arg);
	}
	/**
	 *Set search value for  maximum palette qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxPaletteQuantity(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.MAXPALETTEQUANTITY.toString(), arg);
	}
	/**
	 *Set search value for  maximum palette qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxPaletteQuantity(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.MAXPALETTEQUANTITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  maximum palette qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxPaletteQuantity(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.MAXPALETTEQUANTITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for maximum palette qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setMaxPaletteQuantityOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.MAXPALETTEQUANTITY.toString(), num, bool);
	}

	/**
	 * Set grouping order maximum palette qty
	 * @param num grouping order
	 */
	public void setMaxPaletteQuantityGroup(int num)
	{
		setGroup(_Prefix + Station.MAXPALETTEQUANTITY.toString(), num);
	}

	/**
	 * Fetch maximum palette qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setMaxPaletteQuantityCollect()
	{
		setCollect(_Prefix + Station.MAXPALETTEQUANTITY.toString(), "");
	}

	/**
	 * Fetch maximum palette qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setMaxPaletteQuantityCollect(String compcode)
	{
		setCollect(_Prefix + Station.MAXPALETTEQUANTITY.toString(), compcode);
	}

	/**
	 *Set search value for  maximum instructions
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxInstruction(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.MAXINSTRUCTION.toString(), arg);
	}
	/**
	 *Set search value for  maximum instructions
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxInstruction(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.MAXINSTRUCTION.toString(), arg);
	}
	/**
	 *Set search value for  maximum instructions
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxInstruction(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.MAXINSTRUCTION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  maximum instructions
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxInstruction(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.MAXINSTRUCTION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for maximum instructions
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setMaxInstructionOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.MAXINSTRUCTION.toString(), num, bool);
	}

	/**
	 * Set grouping order maximum instructions
	 * @param num grouping order
	 */
	public void setMaxInstructionGroup(int num)
	{
		setGroup(_Prefix + Station.MAXINSTRUCTION.toString(), num);
	}

	/**
	 * Fetch maximum instructions info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setMaxInstructionCollect()
	{
		setCollect(_Prefix + Station.MAXINSTRUCTION.toString(), "");
	}

	/**
	 * Fetch maximum instructions info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setMaxInstructionCollect(String compcode)
	{
		setCollect(_Prefix + Station.MAXINSTRUCTION.toString(), compcode);
	}

	/**
	 *Set search value for  transmission type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSendable(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.SENDABLE.toString(), arg);
	}
	/**
	 *Set search value for  transmission type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSendable(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.SENDABLE.toString(), arg);
	}
	/**
	 *Set search value for  transmission type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSendable(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.SENDABLE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  transmission type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSendable(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.SENDABLE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for transmission type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSendableOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.SENDABLE.toString(), num, bool);
	}

	/**
	 * Set grouping order transmission type
	 * @param num grouping order
	 */
	public void setSendableGroup(int num)
	{
		setGroup(_Prefix + Station.SENDABLE.toString(), num);
	}

	/**
	 * Fetch transmission type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSendableCollect()
	{
		setCollect(_Prefix + Station.SENDABLE.toString(), "");
	}

	/**
	 * Fetch transmission type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSendableCollect(String compcode)
	{
		setCollect(_Prefix + Station.SENDABLE.toString(), compcode);
	}

	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATUS.toString(), arg, compcode);
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
		setValue(_Prefix + Station.STATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.STATUS.toString(), num, bool);
	}

	/**
	 * Set grouping order status
	 * @param num grouping order
	 */
	public void setStatusGroup(int num)
	{
		setGroup(_Prefix + Station.STATUS.toString(), num);
	}

	/**
	 * Fetch status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusCollect()
	{
		setCollect(_Prefix + Station.STATUS.toString(), "");
	}

	/**
	 * Fetch status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusCollect(String compcode)
	{
		setCollect(_Prefix + Station.STATUS.toString(), compcode);
	}

	/**
	 *Set search value for  controller number
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  controller number
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  controller number
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.CONTROLLERNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + Station.CONTROLLERNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for controller number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setControllerNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.CONTROLLERNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order controller number
	 * @param num grouping order
	 */
	public void setControllerNumberGroup(int num)
	{
		setGroup(_Prefix + Station.CONTROLLERNUMBER.toString(), num);
	}

	/**
	 * Fetch controller number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setControllerNumberCollect()
	{
		setCollect(_Prefix + Station.CONTROLLERNUMBER.toString(), "");
	}

	/**
	 * Fetch controller number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setControllerNumberCollect(String compcode)
	{
		setCollect(_Prefix + Station.CONTROLLERNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  station type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONTYPE.toString(), arg);
	}
	/**
	 *Set search value for  station type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationType(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONTYPE.toString(), arg);
	}
	/**
	 *Set search value for  station type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationType(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  station type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationType(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.STATIONTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order station type
	 * @param num grouping order
	 */
	public void setStationTypeGroup(int num)
	{
		setGroup(_Prefix + Station.STATIONTYPE.toString(), num);
	}

	/**
	 * Fetch station type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationTypeCollect()
	{
		setCollect(_Prefix + Station.STATIONTYPE.toString(), "");
	}

	/**
	 * Fetch station type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationTypeCollect(String compcode)
	{
		setCollect(_Prefix + Station.STATIONTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  setting type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.SETTINGTYPE.toString(), arg);
	}
	/**
	 *Set search value for  setting type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.SETTINGTYPE.toString(), arg);
	}
	/**
	 *Set search value for  setting type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.SETTINGTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  setting type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.SETTINGTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for setting type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSettingTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.SETTINGTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order setting type
	 * @param num grouping order
	 */
	public void setSettingTypeGroup(int num)
	{
		setGroup(_Prefix + Station.SETTINGTYPE.toString(), num);
	}

	/**
	 * Fetch setting type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSettingTypeCollect()
	{
		setCollect(_Prefix + Station.SETTINGTYPE.toString(), "");
	}

	/**
	 * Fetch setting type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSettingTypeCollect(String compcode)
	{
		setCollect(_Prefix + Station.SETTINGTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  work place type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkPlaceType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.WORKPLACETYPE.toString(), arg);
	}
	/**
	 *Set search value for  work place type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkPlaceType(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.WORKPLACETYPE.toString(), arg);
	}
	/**
	 *Set search value for  work place type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkPlaceType(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.WORKPLACETYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  work place type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkPlaceType(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.WORKPLACETYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for work place type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkPlaceTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.WORKPLACETYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order work place type
	 * @param num grouping order
	 */
	public void setWorkPlaceTypeGroup(int num)
	{
		setGroup(_Prefix + Station.WORKPLACETYPE.toString(), num);
	}

	/**
	 * Fetch work place type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkPlaceTypeCollect()
	{
		setCollect(_Prefix + Station.WORKPLACETYPE.toString(), "");
	}

	/**
	 * Fetch work place type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkPlaceTypeCollect(String compcode)
	{
		setCollect(_Prefix + Station.WORKPLACETYPE.toString(), compcode);
	}

	/**
	 *Set search value for  operation display
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOperationDisplay(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.OPERATIONDISPLAY.toString(), arg);
	}
	/**
	 *Set search value for  operation display
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOperationDisplay(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.OPERATIONDISPLAY.toString(), arg);
	}
	/**
	 *Set search value for  operation display
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOperationDisplay(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.OPERATIONDISPLAY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  operation display
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOperationDisplay(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.OPERATIONDISPLAY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for operation display
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setOperationDisplayOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.OPERATIONDISPLAY.toString(), num, bool);
	}

	/**
	 * Set grouping order operation display
	 * @param num grouping order
	 */
	public void setOperationDisplayGroup(int num)
	{
		setGroup(_Prefix + Station.OPERATIONDISPLAY.toString(), num);
	}

	/**
	 * Fetch operation display info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setOperationDisplayCollect()
	{
		setCollect(_Prefix + Station.OPERATIONDISPLAY.toString(), "");
	}

	/**
	 * Fetch operation display info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setOperationDisplayCollect(String compcode)
	{
		setCollect(_Prefix + Station.OPERATIONDISPLAY.toString(), compcode);
	}

	/**
	 *Set search value for  station name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONNAME.toString(), arg);
	}
	/**
	 *Set search value for  station name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONNAME.toString(), arg);
	}
	/**
	 *Set search value for  station name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  station name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for station name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStationNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.STATIONNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order station name
	 * @param num grouping order
	 */
	public void setStationNameGroup(int num)
	{
		setGroup(_Prefix + Station.STATIONNAME.toString(), num);
	}

	/**
	 * Fetch station name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStationNameCollect()
	{
		setCollect(_Prefix + Station.STATIONNAME.toString(), "");
	}

	/**
	 * Fetch station name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStationNameCollect(String compcode)
	{
		setCollect(_Prefix + Station.STATIONNAME.toString(), compcode);
	}

	/**
	 *Set search value for  suspend flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSuspend(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.SUSPEND.toString(), arg);
	}
	/**
	 *Set search value for  suspend flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSuspend(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.SUSPEND.toString(), arg);
	}
	/**
	 *Set search value for  suspend flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSuspend(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.SUSPEND.toString(), arg, compcode);
	}
	/**
	 *Set search value for  suspend flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSuspend(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.SUSPEND.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for suspend flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSuspendOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.SUSPEND.toString(), num, bool);
	}

	/**
	 * Set grouping order suspend flag
	 * @param num grouping order
	 */
	public void setSuspendGroup(int num)
	{
		setGroup(_Prefix + Station.SUSPEND.toString(), num);
	}

	/**
	 * Fetch suspend flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSuspendCollect()
	{
		setCollect(_Prefix + Station.SUSPEND.toString(), "");
	}

	/**
	 * Fetch suspend flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSuspendCollect(String compcode)
	{
		setCollect(_Prefix + Station.SUSPEND.toString(), compcode);
	}

	/**
	 *Set search value for  arrival check
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalCheck(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.ARRIVALCHECK.toString(), arg);
	}
	/**
	 *Set search value for  arrival check
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalCheck(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.ARRIVALCHECK.toString(), arg);
	}
	/**
	 *Set search value for  arrival check
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalCheck(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.ARRIVALCHECK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  arrival check
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalCheck(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.ARRIVALCHECK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for arrival check
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setArrivalCheckOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.ARRIVALCHECK.toString(), num, bool);
	}

	/**
	 * Set grouping order arrival check
	 * @param num grouping order
	 */
	public void setArrivalCheckGroup(int num)
	{
		setGroup(_Prefix + Station.ARRIVALCHECK.toString(), num);
	}

	/**
	 * Fetch arrival check info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setArrivalCheckCollect()
	{
		setCollect(_Prefix + Station.ARRIVALCHECK.toString(), "");
	}

	/**
	 * Fetch arrival check info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setArrivalCheckCollect(String compcode)
	{
		setCollect(_Prefix + Station.ARRIVALCHECK.toString(), compcode);
	}

	/**
	 *Set search value for  load size check
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLoadSizeCheck(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.LOADSIZECHECK.toString(), arg);
	}
	/**
	 *Set search value for  load size check
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLoadSizeCheck(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.LOADSIZECHECK.toString(), arg);
	}
	/**
	 *Set search value for  load size check
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLoadSizeCheck(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.LOADSIZECHECK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  load size check
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLoadSizeCheck(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.LOADSIZECHECK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for load size check
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLoadSizeCheckOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.LOADSIZECHECK.toString(), num, bool);
	}

	/**
	 * Set grouping order load size check
	 * @param num grouping order
	 */
	public void setLoadSizeCheckGroup(int num)
	{
		setGroup(_Prefix + Station.LOADSIZECHECK.toString(), num);
	}

	/**
	 * Fetch load size check info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLoadSizeCheckCollect()
	{
		setCollect(_Prefix + Station.LOADSIZECHECK.toString(), "");
	}

	/**
	 * Fetch load size check info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLoadSizeCheckCollect(String compcode)
	{
		setCollect(_Prefix + Station.LOADSIZECHECK.toString(), compcode);
	}

	/**
	 *Set search value for  removal flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRemove(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.REMOVE.toString(), arg);
	}
	/**
	 *Set search value for  removal flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRemove(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.REMOVE.toString(), arg);
	}
	/**
	 *Set search value for  removal flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRemove(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.REMOVE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  removal flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRemove(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.REMOVE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for removal flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRemoveOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.REMOVE.toString(), num, bool);
	}

	/**
	 * Set grouping order removal flag
	 * @param num grouping order
	 */
	public void setRemoveGroup(int num)
	{
		setGroup(_Prefix + Station.REMOVE.toString(), num);
	}

	/**
	 * Fetch removal flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRemoveCollect()
	{
		setCollect(_Prefix + Station.REMOVE.toString(), "");
	}

	/**
	 * Fetch removal flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRemoveCollect(String compcode)
	{
		setCollect(_Prefix + Station.REMOVE.toString(), compcode);
	}

	/**
	 *Set search value for  inventory check flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckFlag(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.INVENTORYCHECKFLAG.toString(), arg);
	}
	/**
	 *Set search value for  inventory check flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckFlag(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.INVENTORYCHECKFLAG.toString(), arg);
	}
	/**
	 *Set search value for  inventory check flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckFlag(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.INVENTORYCHECKFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  inventory check flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckFlag(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.INVENTORYCHECKFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for inventory check flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInventoryCheckFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.INVENTORYCHECKFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order inventory check flag
	 * @param num grouping order
	 */
	public void setInventoryCheckFlagGroup(int num)
	{
		setGroup(_Prefix + Station.INVENTORYCHECKFLAG.toString(), num);
	}

	/**
	 * Fetch inventory check flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInventoryCheckFlagCollect()
	{
		setCollect(_Prefix + Station.INVENTORYCHECKFLAG.toString(), "");
	}

	/**
	 * Fetch inventory check flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInventoryCheckFlagCollect(String compcode)
	{
		setCollect(_Prefix + Station.INVENTORYCHECKFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  re-storage flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringOperation(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.RESTORINGOPERATION.toString(), arg);
	}
	/**
	 *Set search value for  re-storage flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringOperation(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.RESTORINGOPERATION.toString(), arg);
	}
	/**
	 *Set search value for  re-storage flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringOperation(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.RESTORINGOPERATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  re-storage flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringOperation(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.RESTORINGOPERATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for re-storage flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setReStoringOperationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.RESTORINGOPERATION.toString(), num, bool);
	}

	/**
	 * Set grouping order re-storage flag
	 * @param num grouping order
	 */
	public void setReStoringOperationGroup(int num)
	{
		setGroup(_Prefix + Station.RESTORINGOPERATION.toString(), num);
	}

	/**
	 * Fetch re-storage flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setReStoringOperationCollect()
	{
		setCollect(_Prefix + Station.RESTORINGOPERATION.toString(), "");
	}

	/**
	 * Fetch re-storage flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setReStoringOperationCollect(String compcode)
	{
		setCollect(_Prefix + Station.RESTORINGOPERATION.toString(), compcode);
	}

	/**
	 *Set search value for  re-storage instruction flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringInstruction(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.RESTORINGINSTRUCTION.toString(), arg);
	}
	/**
	 *Set search value for  re-storage instruction flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringInstruction(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.RESTORINGINSTRUCTION.toString(), arg);
	}
	/**
	 *Set search value for  re-storage instruction flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringInstruction(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.RESTORINGINSTRUCTION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  re-storage instruction flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringInstruction(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.RESTORINGINSTRUCTION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for re-storage instruction flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setReStoringInstructionOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.RESTORINGINSTRUCTION.toString(), num, bool);
	}

	/**
	 * Set grouping order re-storage instruction flag
	 * @param num grouping order
	 */
	public void setReStoringInstructionGroup(int num)
	{
		setGroup(_Prefix + Station.RESTORINGINSTRUCTION.toString(), num);
	}

	/**
	 * Fetch re-storage instruction flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setReStoringInstructionCollect()
	{
		setCollect(_Prefix + Station.RESTORINGINSTRUCTION.toString(), "");
	}

	/**
	 * Fetch re-storage instruction flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setReStoringInstructionCollect(String compcode)
	{
		setCollect(_Prefix + Station.RESTORINGINSTRUCTION.toString(), compcode);
	}

	/**
	 *Set search value for  palette operation
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPoperationNeed(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.POPERATIONNEED.toString(), arg);
	}
	/**
	 *Set search value for  palette operation
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPoperationNeed(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.POPERATIONNEED.toString(), arg);
	}
	/**
	 *Set search value for  palette operation
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPoperationNeed(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.POPERATIONNEED.toString(), arg, compcode);
	}
	/**
	 *Set search value for  palette operation
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPoperationNeed(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.POPERATIONNEED.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for palette operation
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPoperationNeedOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.POPERATIONNEED.toString(), num, bool);
	}

	/**
	 * Set grouping order palette operation
	 * @param num grouping order
	 */
	public void setPoperationNeedGroup(int num)
	{
		setGroup(_Prefix + Station.POPERATIONNEED.toString(), num);
	}

	/**
	 * Fetch palette operation info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPoperationNeedCollect()
	{
		setCollect(_Prefix + Station.POPERATIONNEED.toString(), "");
	}

	/**
	 * Fetch palette operation info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPoperationNeedCollect(String compcode)
	{
		setCollect(_Prefix + Station.POPERATIONNEED.toString(), compcode);
	}

	/**
	 *Set search value for  warehouse station
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.WHSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  warehouse station
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for warehouse station
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWHStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.WHSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order warehouse station
	 * @param num grouping order
	 */
	public void setWHStationNumberGroup(int num)
	{
		setGroup(_Prefix + Station.WHSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch warehouse station info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWHStationNumberCollect()
	{
		setCollect(_Prefix + Station.WHSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch warehouse station info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWHStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Station.WHSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  parent station
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.PARENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  parent station
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.PARENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  parent station
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.PARENTSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  parent station
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.PARENTSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for parent station
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setParentStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.PARENTSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order parent station
	 * @param num grouping order
	 */
	public void setParentStationNumberGroup(int num)
	{
		setGroup(_Prefix + Station.PARENTSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch parent station info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setParentStationNumberCollect()
	{
		setCollect(_Prefix + Station.PARENTSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch parent station info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setParentStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Station.PARENTSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  aisle station
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  aisle station
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  aisle station
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.AISLESTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  aisle station
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.AISLESTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for aisle station
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAisleStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.AISLESTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order aisle station
	 * @param num grouping order
	 */
	public void setAisleStationNumberGroup(int num)
	{
		setGroup(_Prefix + Station.AISLESTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch aisle station info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAisleStationNumberCollect()
	{
		setCollect(_Prefix + Station.AISLESTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch aisle station info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAisleStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Station.AISLESTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  next station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNextStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.NEXTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  next station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNextStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.NEXTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  next station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNextStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.NEXTSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  next station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNextStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.NEXTSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for next station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setNextStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.NEXTSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order next station number
	 * @param num grouping order
	 */
	public void setNextStationNumberGroup(int num)
	{
		setGroup(_Prefix + Station.NEXTSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch next station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setNextStationNumberCollect()
	{
		setCollect(_Prefix + Station.NEXTSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch next station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setNextStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Station.NEXTSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  last used station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.LASTUSEDSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  last used station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.LASTUSEDSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  last used station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.LASTUSEDSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last used station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.LASTUSEDSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for last used station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUsedStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.LASTUSEDSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order last used station number
	 * @param num grouping order
	 */
	public void setLastUsedStationNumberGroup(int num)
	{
		setGroup(_Prefix + Station.LASTUSEDSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch last used station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUsedStationNumberCollect()
	{
		setCollect(_Prefix + Station.LASTUSEDSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch last used station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUsedStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Station.LASTUSEDSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  rejected station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRejectStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.REJECTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  rejected station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRejectStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.REJECTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  rejected station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRejectStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.REJECTSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  rejected station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRejectStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.REJECTSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for rejected station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRejectStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.REJECTSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order rejected station number
	 * @param num grouping order
	 */
	public void setRejectStationNumberGroup(int num)
	{
		setGroup(_Prefix + Station.REJECTSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch rejected station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRejectStationNumberCollect()
	{
		setCollect(_Prefix + Station.REJECTSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch rejected station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRejectStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + Station.REJECTSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  mode type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODETYPE.toString(), arg);
	}
	/**
	 *Set search value for  mode type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeType(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODETYPE.toString(), arg);
	}
	/**
	 *Set search value for  mode type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeType(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODETYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  mode type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeType(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODETYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for mode type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setModeTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.MODETYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order mode type
	 * @param num grouping order
	 */
	public void setModeTypeGroup(int num)
	{
		setGroup(_Prefix + Station.MODETYPE.toString(), num);
	}

	/**
	 * Fetch mode type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setModeTypeCollect()
	{
		setCollect(_Prefix + Station.MODETYPE.toString(), "");
	}

	/**
	 * Fetch mode type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setModeTypeCollect(String compcode)
	{
		setCollect(_Prefix + Station.MODETYPE.toString(), compcode);
	}

	/**
	 *Set search value for  current mode
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentMode(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.CURRENTMODE.toString(), arg);
	}
	/**
	 *Set search value for  current mode
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentMode(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.CURRENTMODE.toString(), arg);
	}
	/**
	 *Set search value for  current mode
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentMode(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.CURRENTMODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  current mode
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentMode(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.CURRENTMODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for current mode
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCurrentModeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.CURRENTMODE.toString(), num, bool);
	}

	/**
	 * Set grouping order current mode
	 * @param num grouping order
	 */
	public void setCurrentModeGroup(int num)
	{
		setGroup(_Prefix + Station.CURRENTMODE.toString(), num);
	}

	/**
	 * Fetch current mode info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCurrentModeCollect()
	{
		setCollect(_Prefix + Station.CURRENTMODE.toString(), "");
	}

	/**
	 * Fetch current mode info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCurrentModeCollect(String compcode)
	{
		setCollect(_Prefix + Station.CURRENTMODE.toString(), compcode);
	}

	/**
	 *Set search value for  mode request flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeRequest(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODEREQUEST.toString(), arg);
	}
	/**
	 *Set search value for  mode request flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeRequest(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODEREQUEST.toString(), arg);
	}
	/**
	 *Set search value for  mode request flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeRequest(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODEREQUEST.toString(), arg, compcode);
	}
	/**
	 *Set search value for  mode request flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeRequest(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODEREQUEST.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for mode request flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setModeRequestOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.MODEREQUEST.toString(), num, bool);
	}

	/**
	 * Set grouping order mode request flag
	 * @param num grouping order
	 */
	public void setModeRequestGroup(int num)
	{
		setGroup(_Prefix + Station.MODEREQUEST.toString(), num);
	}

	/**
	 * Fetch mode request flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setModeRequestCollect()
	{
		setCollect(_Prefix + Station.MODEREQUEST.toString(), "");
	}

	/**
	 * Fetch mode request flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setModeRequestCollect(String compcode)
	{
		setCollect(_Prefix + Station.MODEREQUEST.toString(), compcode);
	}

	/**
	 *Set search value for  mode request time
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeRequestTime(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODEREQUESTTIME.toString(), arg);
	}
	/**
	 *Set search value for  mode request time
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeRequestTime(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODEREQUESTTIME.toString(), arg);
	}
	/**
	 *Set search value for  mode request time
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeRequestTime(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODEREQUESTTIME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  mode request time
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeRequestTime(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODEREQUESTTIME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for mode request time
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setModeRequestTimeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.MODEREQUESTTIME.toString(), num, bool);
	}

	/**
	 * Set grouping order mode request time
	 * @param num grouping order
	 */
	public void setModeRequestTimeGroup(int num)
	{
		setGroup(_Prefix + Station.MODEREQUESTTIME.toString(), num);
	}

	/**
	 * Fetch mode request time info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setModeRequestTimeCollect()
	{
		setCollect(_Prefix + Station.MODEREQUESTTIME.toString(), "");
	}

	/**
	 * Fetch mode request time info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setModeRequestTimeCollect(String compcode)
	{
		setCollect(_Prefix + Station.MODEREQUESTTIME.toString(), compcode);
	}

	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.CARRYKEY.toString(), arg);
	}
	/**
	 *Set search value for  carry key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.CARRYKEY.toString(), arg);
	}
	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.CARRYKEY.toString(), arg, compcode);
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
		setValue(_Prefix + Station.CARRYKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for carry key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCarryKeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.CARRYKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order carry key
	 * @param num grouping order
	 */
	public void setCarryKeyGroup(int num)
	{
		setGroup(_Prefix + Station.CARRYKEY.toString(), num);
	}

	/**
	 * Fetch carry key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCarryKeyCollect()
	{
		setCollect(_Prefix + Station.CARRYKEY.toString(), "");
	}

	/**
	 * Fetch carry key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCarryKeyCollect(String compcode)
	{
		setCollect(_Prefix + Station.CARRYKEY.toString(), compcode);
	}

	/**
	 *Set search value for  load height
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.HEIGHT.toString(), arg);
	}
	/**
	 *Set search value for  load height
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.HEIGHT.toString(), arg);
	}
	/**
	 *Set search value for  load height
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.HEIGHT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  load height
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.HEIGHT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for load height
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setHeightOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.HEIGHT.toString(), num, bool);
	}

	/**
	 * Set grouping order load height
	 * @param num grouping order
	 */
	public void setHeightGroup(int num)
	{
		setGroup(_Prefix + Station.HEIGHT.toString(), num);
	}

	/**
	 * Fetch load height info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setHeightCollect()
	{
		setCollect(_Prefix + Station.HEIGHT.toString(), "");
	}

	/**
	 * Fetch load height info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setHeightCollect(String compcode)
	{
		setCollect(_Prefix + Station.HEIGHT.toString(), compcode);
	}

	/**
	 *Set search value for  bar code data
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBCData(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.BCDATA.toString(), arg);
	}
	/**
	 *Set search value for  bar code data
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBCData(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.BCDATA.toString(), arg);
	}
	/**
	 *Set search value for  bar code data
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBCData(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.BCDATA.toString(), arg, compcode);
	}
	/**
	 *Set search value for  bar code data
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBCData(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.BCDATA.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for bar code data
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBCDataOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.BCDATA.toString(), num, bool);
	}

	/**
	 * Set grouping order bar code data
	 * @param num grouping order
	 */
	public void setBCDataGroup(int num)
	{
		setGroup(_Prefix + Station.BCDATA.toString(), num);
	}

	/**
	 * Fetch bar code data info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBCDataCollect()
	{
		setCollect(_Prefix + Station.BCDATA.toString(), "");
	}

	/**
	 * Fetch bar code data info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBCDataCollect(String compcode)
	{
		setCollect(_Prefix + Station.BCDATA.toString(), compcode);
	}

	/**
	 *Set search value for  class name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setClassName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.CLASSNAME.toString(), arg);
	}
	/**
	 *Set search value for  class name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setClassName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Station.CLASSNAME.toString(), arg);
	}
	/**
	 *Set search value for  class name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setClassName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Station.CLASSNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  class name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setClassName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Station.CLASSNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for class name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setClassNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Station.CLASSNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order class name
	 * @param num grouping order
	 */
	public void setClassNameGroup(int num)
	{
		setGroup(_Prefix + Station.CLASSNAME.toString(), num);
	}

	/**
	 * Fetch class name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setClassNameCollect()
	{
		setCollect(_Prefix + Station.CLASSNAME.toString(), "");
	}

	/**
	 * Fetch class name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setClassNameCollect(String compcode)
	{
		setCollect(_Prefix + Station.CLASSNAME.toString(), compcode);
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
		return "$Id: StationSearchKey.java,v 1.3 2006/11/13 04:32:59 suresh Exp $" ;
	}
}
