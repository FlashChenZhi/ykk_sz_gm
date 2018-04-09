//$Id: ASInventoryCheckAlterKey.java,v 1.3 2006/11/20 06:53:06 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.ASInventoryCheck;

/**
 * Update key class for ASINVENTORYCHECK use
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
 * @version $Revision: 1.3 $, $Date: 2006/11/20 06:53:06 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ASInventoryCheckAlterKey
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
	public ASInventoryCheckAlterKey()
	{
		super(new ASInventoryCheck()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for schedule number
	 * @param arg schedule number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 * Set search value for schedule number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for schedule number
	 * @param arg Update value for schedule number
	 */
	public void updateScheduleNumber(String arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.SCHEDULENUMBER.toString(), arg);
	}

	/**
	 * Set search value for creation date
	 * @param arg creation date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CREATEDATE.toString(), arg);
	}
	/**
	 * Set search value for creation date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CREATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CREATEDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CREATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for creation date
	 * @param arg Update value for creation date
	 */
	public void updateCreateDate(Date arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.CREATEDATE.toString(), arg);
	}

	/**
	 * Set search value for setting type
	 * @param arg setting type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), arg);
	}
	/**
	 * Set search value for setting type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), arg);
	}
	/**
	 *Set search value for  setting type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSettingType(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), arg, compcode);
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
		setValue(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for setting type
	 * @param arg Update value for setting type
	 */
	public void updateSettingType(int arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.SETTINGTYPE.toString(), arg);
	}

	/**
	 * Set search value for warehouse station number
	 * @param arg warehouse station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for warehouse station number
	 * @param arg Update value for warehouse station number
	 */
	public void updateWHStationNumber(String arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.WHSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for consignor code
	 * @param arg consignor code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), arg);
	}
	/**
	 * Set search value for consignor code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for consignor code
	 * @param arg Update value for consignor code
	 */
	public void updateConsignorCode(String arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.CONSIGNORCODE.toString(), arg);
	}

	/**
	 * Set search value for consignor name
	 * @param arg consignor name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), arg);
	}
	/**
	 * Set search value for consignor name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for consignor name
	 * @param arg Update value for consignor name
	 */
	public void updateConsignorName(String arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.CONSIGNORNAME.toString(), arg);
	}

	/**
	 * Set search value for start location
	 * @param arg start location<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromLocation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), arg);
	}
	/**
	 * Set search value for start location
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromLocation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), arg);
	}
	/**
	 *Set search value for  start location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromLocation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  start location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromLocation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for start location
	 * @param arg Update value for start location
	 */
	public void updateFromLocation(String arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.FROMLOCATION.toString(), arg);
	}

	/**
	 * Set search value for last location
	 * @param arg last location<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToLocation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOLOCATION.toString(), arg);
	}
	/**
	 * Set search value for last location
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToLocation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOLOCATION.toString(), arg);
	}
	/**
	 *Set search value for  last location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToLocation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOLOCATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last location
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToLocation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOLOCATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for last location
	 * @param arg Update value for last location
	 */
	public void updateToLocation(String arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.TOLOCATION.toString(), arg);
	}

	/**
	 * Set search value for starting item code
	 * @param arg starting item code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), arg);
	}
	/**
	 * Set search value for starting item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  starting item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  starting item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFromItemCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for starting item code
	 * @param arg Update value for starting item code
	 */
	public void updateFromItemCode(String arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.FROMITEMCODE.toString(), arg);
	}

	/**
	 * Set search value for last item code
	 * @param arg last item code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), arg);
	}
	/**
	 * Set search value for last item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  last item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setToItemCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for last item code
	 * @param arg Update value for last item code
	 */
	public void updateToItemCode(String arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.TOITEMCODE.toString(), arg);
	}

	/**
	 * Set search value for lot number
	 * @param arg lot number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), arg);
	}
	/**
	 * Set search value for lot number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for lot number
	 * @param arg Update value for lot number
	 */
	public void updateLotNumber(String arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.LOTNUMBER.toString(), arg);
	}

	/**
	 * Set search value for station number
	 * @param arg station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for station number
	 * @param arg Update value for station number
	 */
	public void updateStationNumber(String arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.STATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for status
	 * @param arg status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATUS.toString(), arg);
	}
	/**
	 * Set search value for status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ASInventoryCheck.STATUS.toString(), arg, compcode);
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
		setValue(_Prefix + ASInventoryCheck.STATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for status
	 * @param arg Update value for status
	 */
	public void updateStatus(int arg)
	{
		setUpdValue(_Prefix + ASInventoryCheck.STATUS.toString(), arg);
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
		return "$Id: ASInventoryCheckAlterKey.java,v 1.3 2006/11/20 06:53:06 suresh Exp $" ;
	}
}
