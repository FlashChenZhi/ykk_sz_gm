//$Id: StationAlterKey.java,v 1.2 2006/11/09 07:50:37 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.Station;

/**
 * Update key class for STATION use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:50:37 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class StationAlterKey
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
	public StationAlterKey()
	{
		super(new Station()) ;
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
		setValue(_Prefix + Station.STATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for station number
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
	 * Set update value for station number
	 * @param arg Update value for station number
	 */
	public void updateStationNumber(String arg)
	{
		setUpdValue(_Prefix + Station.STATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for maximum palette qty
	 * @param arg maximum palette qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxPaletteQuantity(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.MAXPALETTEQUANTITY.toString(), arg);
	}
	/**
	 * Set search value for maximum palette qty
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
	 * Set update value for maximum palette qty
	 * @param arg Update value for maximum palette qty
	 */
	public void updateMaxPaletteQuantity(int arg)
	{
		setUpdValue(_Prefix + Station.MAXPALETTEQUANTITY.toString(), arg);
	}

	/**
	 * Set search value for maximum instructions
	 * @param arg maximum instructions<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaxInstruction(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.MAXINSTRUCTION.toString(), arg);
	}
	/**
	 * Set search value for maximum instructions
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
	 * Set update value for maximum instructions
	 * @param arg Update value for maximum instructions
	 */
	public void updateMaxInstruction(int arg)
	{
		setUpdValue(_Prefix + Station.MAXINSTRUCTION.toString(), arg);
	}

	/**
	 * Set search value for transmission type
	 * @param arg transmission type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSendable(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.SENDABLE.toString(), arg);
	}
	/**
	 * Set search value for transmission type
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
	 * Set update value for transmission type
	 * @param arg Update value for transmission type
	 */
	public void updateSendable(int arg)
	{
		setUpdValue(_Prefix + Station.SENDABLE.toString(), arg);
	}

	/**
	 * Set search value for status
	 * @param arg status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATUS.toString(), arg);
	}
	/**
	 * Set search value for status
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
	 * Set update value for status
	 * @param arg Update value for status
	 */
	public void updateStatus(int arg)
	{
		setUpdValue(_Prefix + Station.STATUS.toString(), arg);
	}

	/**
	 * Set search value for controller number
	 * @param arg controller number<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControllerNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.CONTROLLERNUMBER.toString(), arg);
	}
	/**
	 * Set search value for controller number
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
	 * Set update value for controller number
	 * @param arg Update value for controller number
	 */
	public void updateControllerNumber(int arg)
	{
		setUpdValue(_Prefix + Station.CONTROLLERNUMBER.toString(), arg);
	}

	/**
	 * Set search value for station type
	 * @param arg station type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONTYPE.toString(), arg);
	}
	/**
	 * Set search value for station type
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
	 * Set update value for station type
	 * @param arg Update value for station type
	 */
	public void updateStationType(int arg)
	{
		setUpdValue(_Prefix + Station.STATIONTYPE.toString(), arg);
	}

	/**
	 * Set search value for setting type
	 * @param arg setting type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.SETTINGTYPE.toString(), arg);
	}
	/**
	 * Set search value for setting type
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
	 * Set update value for setting type
	 * @param arg Update value for setting type
	 */
	public void updateSettingType(int arg)
	{
		setUpdValue(_Prefix + Station.SETTINGTYPE.toString(), arg);
	}

	/**
	 * Set search value for work place type
	 * @param arg work place type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkPlaceType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.WORKPLACETYPE.toString(), arg);
	}
	/**
	 * Set search value for work place type
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
	 * Set update value for work place type
	 * @param arg Update value for work place type
	 */
	public void updateWorkPlaceType(int arg)
	{
		setUpdValue(_Prefix + Station.WORKPLACETYPE.toString(), arg);
	}

	/**
	 * Set search value for operation display
	 * @param arg operation display<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOperationDisplay(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.OPERATIONDISPLAY.toString(), arg);
	}
	/**
	 * Set search value for operation display
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
	 * Set update value for operation display
	 * @param arg Update value for operation display
	 */
	public void updateOperationDisplay(int arg)
	{
		setUpdValue(_Prefix + Station.OPERATIONDISPLAY.toString(), arg);
	}

	/**
	 * Set search value for station name
	 * @param arg station name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.STATIONNAME.toString(), arg);
	}
	/**
	 * Set search value for station name
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
	 * Set update value for station name
	 * @param arg Update value for station name
	 */
	public void updateStationName(String arg)
	{
		setUpdValue(_Prefix + Station.STATIONNAME.toString(), arg);
	}

	/**
	 * Set search value for suspend flag
	 * @param arg suspend flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSuspend(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.SUSPEND.toString(), arg);
	}
	/**
	 * Set search value for suspend flag
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
	 * Set update value for suspend flag
	 * @param arg Update value for suspend flag
	 */
	public void updateSuspend(int arg)
	{
		setUpdValue(_Prefix + Station.SUSPEND.toString(), arg);
	}

	/**
	 * Set search value for arrival check
	 * @param arg arrival check<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalCheck(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.ARRIVALCHECK.toString(), arg);
	}
	/**
	 * Set search value for arrival check
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
	 * Set update value for arrival check
	 * @param arg Update value for arrival check
	 */
	public void updateArrivalCheck(int arg)
	{
		setUpdValue(_Prefix + Station.ARRIVALCHECK.toString(), arg);
	}

	/**
	 * Set search value for load size check
	 * @param arg load size check<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLoadSizeCheck(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.LOADSIZECHECK.toString(), arg);
	}
	/**
	 * Set search value for load size check
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
	 * Set update value for load size check
	 * @param arg Update value for load size check
	 */
	public void updateLoadSizeCheck(int arg)
	{
		setUpdValue(_Prefix + Station.LOADSIZECHECK.toString(), arg);
	}

	/**
	 * Set search value for removal flag
	 * @param arg removal flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRemove(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.REMOVE.toString(), arg);
	}
	/**
	 * Set search value for removal flag
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
	 * Set update value for removal flag
	 * @param arg Update value for removal flag
	 */
	public void updateRemove(int arg)
	{
		setUpdValue(_Prefix + Station.REMOVE.toString(), arg);
	}

	/**
	 * Set search value for inventory check flag
	 * @param arg inventory check flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckFlag(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.INVENTORYCHECKFLAG.toString(), arg);
	}
	/**
	 * Set search value for inventory check flag
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
	 * Set update value for inventory check flag
	 * @param arg Update value for inventory check flag
	 */
	public void updateInventoryCheckFlag(int arg)
	{
		setUpdValue(_Prefix + Station.INVENTORYCHECKFLAG.toString(), arg);
	}

	/**
	 * Set search value for re-storage flag
	 * @param arg re-storage flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringOperation(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.RESTORINGOPERATION.toString(), arg);
	}
	/**
	 * Set search value for re-storage flag
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
	 * Set update value for re-storage flag
	 * @param arg Update value for re-storage flag
	 */
	public void updateReStoringOperation(int arg)
	{
		setUpdValue(_Prefix + Station.RESTORINGOPERATION.toString(), arg);
	}

	/**
	 * Set search value for re-storage instruction flag
	 * @param arg re-storage instruction flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringInstruction(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.RESTORINGINSTRUCTION.toString(), arg);
	}
	/**
	 * Set search value for re-storage instruction flag
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
	 * Set update value for re-storage instruction flag
	 * @param arg Update value for re-storage instruction flag
	 */
	public void updateReStoringInstruction(int arg)
	{
		setUpdValue(_Prefix + Station.RESTORINGINSTRUCTION.toString(), arg);
	}

	/**
	 * Set search value for palette operation
	 * @param arg palette operation<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPoperationNeed(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.POPERATIONNEED.toString(), arg);
	}
	/**
	 * Set search value for palette operation
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
	 * Set update value for palette operation
	 * @param arg Update value for palette operation
	 */
	public void updatePoperationNeed(int arg)
	{
		setUpdValue(_Prefix + Station.POPERATIONNEED.toString(), arg);
	}

	/**
	 * Set search value for warehouse station
	 * @param arg warehouse station<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for warehouse station
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
	 * Set update value for warehouse station
	 * @param arg Update value for warehouse station
	 */
	public void updateWHStationNumber(String arg)
	{
		setUpdValue(_Prefix + Station.WHSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for parent station
	 * @param arg parent station<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setParentStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.PARENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for parent station
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
	 * Set update value for parent station
	 * @param arg Update value for parent station
	 */
	public void updateParentStationNumber(String arg)
	{
		setUpdValue(_Prefix + Station.PARENTSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for aisle station
	 * @param arg aisle station<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for aisle station
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
	 * Set update value for aisle station
	 * @param arg Update value for aisle station
	 */
	public void updateAisleStationNumber(String arg)
	{
		setUpdValue(_Prefix + Station.AISLESTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for next station number
	 * @param arg next station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setNextStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.NEXTSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for next station number
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
	 * Set update value for next station number
	 * @param arg Update value for next station number
	 */
	public void updateNextStationNumber(String arg)
	{
		setUpdValue(_Prefix + Station.NEXTSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for last used station number
	 * @param arg last used station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.LASTUSEDSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for last used station number
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
	 * Set update value for last used station number
	 * @param arg Update value for last used station number
	 */
	public void updateLastUsedStationNumber(String arg)
	{
		setUpdValue(_Prefix + Station.LASTUSEDSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for rejected station number
	 * @param arg rejected station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRejectStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.REJECTSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for rejected station number
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
	 * Set update value for rejected station number
	 * @param arg Update value for rejected station number
	 */
	public void updateRejectStationNumber(String arg)
	{
		setUpdValue(_Prefix + Station.REJECTSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for mode type
	 * @param arg mode type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODETYPE.toString(), arg);
	}
	/**
	 * Set search value for mode type
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
	 * Set update value for mode type
	 * @param arg Update value for mode type
	 */
	public void updateModeType(int arg)
	{
		setUpdValue(_Prefix + Station.MODETYPE.toString(), arg);
	}

	/**
	 * Set search value for current mode
	 * @param arg current mode<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentMode(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.CURRENTMODE.toString(), arg);
	}
	/**
	 * Set search value for current mode
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
	 * Set update value for current mode
	 * @param arg Update value for current mode
	 */
	public void updateCurrentMode(int arg)
	{
		setUpdValue(_Prefix + Station.CURRENTMODE.toString(), arg);
	}

	/**
	 * Set search value for mode request flag
	 * @param arg mode request flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeRequest(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODEREQUEST.toString(), arg);
	}
	/**
	 * Set search value for mode request flag
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
	 * Set update value for mode request flag
	 * @param arg Update value for mode request flag
	 */
	public void updateModeRequest(int arg)
	{
		setUpdValue(_Prefix + Station.MODEREQUEST.toString(), arg);
	}

	/**
	 * Set search value for mode request time
	 * @param arg mode request time<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setModeRequestTime(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.MODEREQUESTTIME.toString(), arg);
	}
	/**
	 * Set search value for mode request time
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
	 * Set update value for mode request time
	 * @param arg Update value for mode request time
	 */
	public void updateModeRequestTime(Date arg)
	{
		setUpdValue(_Prefix + Station.MODEREQUESTTIME.toString(), arg);
	}

	/**
	 * Set search value for carry key
	 * @param arg carry key<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.CARRYKEY.toString(), arg);
	}
	/**
	 * Set search value for carry key
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
	 * Set update value for carry key
	 * @param arg Update value for carry key
	 */
	public void updateCarryKey(String arg)
	{
		setUpdValue(_Prefix + Station.CARRYKEY.toString(), arg);
	}

	/**
	 * Set search value for load height
	 * @param arg load height<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.HEIGHT.toString(), arg);
	}
	/**
	 * Set search value for load height
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
	 * Set update value for load height
	 * @param arg Update value for load height
	 */
	public void updateHeight(int arg)
	{
		setUpdValue(_Prefix + Station.HEIGHT.toString(), arg);
	}

	/**
	 * Set search value for bar code data
	 * @param arg bar code data<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBCData(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.BCDATA.toString(), arg);
	}
	/**
	 * Set search value for bar code data
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
	 * Set update value for bar code data
	 * @param arg Update value for bar code data
	 */
	public void updateBCData(String arg)
	{
		setUpdValue(_Prefix + Station.BCDATA.toString(), arg);
	}

	/**
	 * Set search value for class name
	 * @param arg class name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setClassName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Station.CLASSNAME.toString(), arg);
	}
	/**
	 * Set search value for class name
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
	 * Set update value for class name
	 * @param arg Update value for class name
	 */
	public void updateClassName(String arg)
	{
		setUpdValue(_Prefix + Station.CLASSNAME.toString(), arg);
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
		return "$Id: StationAlterKey.java,v 1.2 2006/11/09 07:50:37 suresh Exp $" ;
	}
}
