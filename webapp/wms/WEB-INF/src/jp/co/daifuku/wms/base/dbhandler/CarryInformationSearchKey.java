//$Id: CarryInformationSearchKey.java,v 1.3 2006/11/13 04:33:10 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.CarryInformation;

/**
 * This is the search key class for CARRYINFO use.
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


public class CarryInformationSearchKey
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
	public CarryInformationSearchKey()
	{
		super(new CarryInformation()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKEY.toString(), arg);
	}
	/**
	 *Set search value for  carry key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKEY.toString(), arg);
	}
	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKEY.toString(), arg, compcode);
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
		setValue(_Prefix + CarryInformation.CARRYKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for carry key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCarryKeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.CARRYKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order carry key
	 * @param num grouping order
	 */
	public void setCarryKeyGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.CARRYKEY.toString(), num);
	}

	/**
	 * Fetch carry key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCarryKeyCollect()
	{
		setCollect(_Prefix + CarryInformation.CARRYKEY.toString(), "");
	}

	/**
	 * Fetch carry key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCarryKeyCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.CARRYKEY.toString(), compcode);
	}

	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PALETTEID.toString(), arg);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PALETTEID.toString(), arg);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PALETTEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PALETTEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for palette id
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPaletteIdOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.PALETTEID.toString(), num, bool);
	}

	/**
	 * Set grouping order palette id
	 * @param num grouping order
	 */
	public void setPaletteIdGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.PALETTEID.toString(), num);
	}

	/**
	 * Fetch palette id info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPaletteIdCollect()
	{
		setCollect(_Prefix + CarryInformation.PALETTEID.toString(), "");
	}

	/**
	 * Fetch palette id info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPaletteIdCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.PALETTEID.toString(), compcode);
	}

	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CREATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CREATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CREATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + CarryInformation.CREATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for creation date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCreateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.CREATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order creation date
	 * @param num grouping order
	 */
	public void setCreateDateGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.CREATEDATE.toString(), num);
	}

	/**
	 * Fetch creation date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCreateDateCollect()
	{
		setCollect(_Prefix + CarryInformation.CREATEDATE.toString(), "");
	}

	/**
	 * Fetch creation date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCreateDateCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.CREATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  work type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKTYPE.toString(), arg);
	}
	/**
	 *Set search value for  work type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKTYPE.toString(), arg);
	}
	/**
	 *Set search value for  work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for work type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.WORKTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order work type
	 * @param num grouping order
	 */
	public void setWorkTypeGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.WORKTYPE.toString(), num);
	}

	/**
	 * Fetch work type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkTypeCollect()
	{
		setCollect(_Prefix + CarryInformation.WORKTYPE.toString(), "");
	}

	/**
	 * Fetch work type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkTypeCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.WORKTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  picking group
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setGroupNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.GROUPNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  picking group
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setGroupNumber(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.GROUPNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  picking group
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setGroupNumber(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.GROUPNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  picking group
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setGroupNumber(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.GROUPNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for picking group
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setGroupNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.GROUPNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order picking group
	 * @param num grouping order
	 */
	public void setGroupNumberGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.GROUPNUMBER.toString(), num);
	}

	/**
	 * Fetch picking group info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setGroupNumberCollect()
	{
		setCollect(_Prefix + CarryInformation.GROUPNUMBER.toString(), "");
	}

	/**
	 * Fetch picking group info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setGroupNumberCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.GROUPNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  conveyance status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCmdStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CMDSTATUS.toString(), arg);
	}
	/**
	 *Set search value for  conveyance status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCmdStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CMDSTATUS.toString(), arg);
	}
	/**
	 *Set search value for  conveyance status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCmdStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CMDSTATUS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  conveyance status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCmdStatus(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CMDSTATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for conveyance status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCmdStatusOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.CMDSTATUS.toString(), num, bool);
	}

	/**
	 * Set grouping order conveyance status
	 * @param num grouping order
	 */
	public void setCmdStatusGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.CMDSTATUS.toString(), num);
	}

	/**
	 * Fetch conveyance status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCmdStatusCollect()
	{
		setCollect(_Prefix + CarryInformation.CMDSTATUS.toString(), "");
	}

	/**
	 * Fetch conveyance status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCmdStatusCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.CMDSTATUS.toString(), compcode);
	}

	/**
	 *Set search value for  priority type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PRIORITY.toString(), arg);
	}
	/**
	 *Set search value for  priority type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PRIORITY.toString(), arg);
	}
	/**
	 *Set search value for  priority type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PRIORITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  priority type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PRIORITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for priority type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPriorityOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.PRIORITY.toString(), num, bool);
	}

	/**
	 * Set grouping order priority type
	 * @param num grouping order
	 */
	public void setPriorityGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.PRIORITY.toString(), num);
	}

	/**
	 * Fetch priority type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPriorityCollect()
	{
		setCollect(_Prefix + CarryInformation.PRIORITY.toString(), "");
	}

	/**
	 * Fetch priority type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPriorityCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.PRIORITY.toString(), compcode);
	}

	/**
	 *Set search value for  restorage flag
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringFlag(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RESTORINGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  restorage flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringFlag(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RESTORINGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  restorage flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringFlag(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RESTORINGFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  restorage flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringFlag(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RESTORINGFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for restorage flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setReStoringFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.RESTORINGFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order restorage flag
	 * @param num grouping order
	 */
	public void setReStoringFlagGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.RESTORINGFLAG.toString(), num);
	}

	/**
	 * Fetch restorage flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setReStoringFlagCollect()
	{
		setCollect(_Prefix + CarryInformation.RESTORINGFLAG.toString(), "");
	}

	/**
	 * Fetch restorage flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setReStoringFlagCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.RESTORINGFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  conveyance type
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKind(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKIND.toString(), arg);
	}
	/**
	 *Set search value for  conveyance type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKind(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKIND.toString(), arg);
	}
	/**
	 *Set search value for  conveyance type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKind(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKIND.toString(), arg, compcode);
	}
	/**
	 *Set search value for  conveyance type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKind(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKIND.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for conveyance type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCarryKindOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.CARRYKIND.toString(), num, bool);
	}

	/**
	 * Set grouping order conveyance type
	 * @param num grouping order
	 */
	public void setCarryKindGroup(int num) 
	{
		setGroup(_Prefix + CarryInformation.CARRYKIND.toString(), num);
	}

	/**
	 * Fetch conveyance type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCarryKindCollect()
	{
		setCollect(_Prefix + CarryInformation.CARRYKIND.toString(), "");
	}

	/**
	 * Fetch conveyance type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCarryKindCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.CARRYKIND.toString(), compcode);
	}

	/**
	 *Set search value for  picking location number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  picking location number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  picking location number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  picking location number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for picking location number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRetrievalStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order picking location number
	 * @param num grouping order
	 */
	public void setRetrievalStationNumberGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch picking location number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRetrievalStationNumberCollect()
	{
		setCollect(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch picking location number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRetrievalStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  picking detail
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalDetail(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), arg);
	}
	/**
	 *Set search value for  picking detail
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalDetail(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), arg);
	}
	/**
	 *Set search value for  picking detail
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalDetail(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), arg, compcode);
	}
	/**
	 *Set search value for  picking detail
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalDetail(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for picking detail
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRetrievalDetailOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), num, bool);
	}

	/**
	 * Set grouping order picking detail
	 * @param num grouping order
	 */
	public void setRetrievalDetailGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), num);
	}

	/**
	 * Fetch picking detail info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRetrievalDetailCollect()
	{
		setCollect(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), "");
	}

	/**
	 * Fetch picking detail info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRetrievalDetailCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), compcode);
	}

	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  work number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for work number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.WORKNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order work number
	 * @param num grouping order
	 */
	public void setWorkNumberGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.WORKNUMBER.toString(), num);
	}

	/**
	 * Fetch work number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkNumberCollect()
	{
		setCollect(_Prefix + CarryInformation.WORKNUMBER.toString(), "");
	}

	/**
	 * Fetch work number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkNumberCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.WORKNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  source station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSourceStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  source station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSourceStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  source station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSourceStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  source station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSourceStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for source station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSourceStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order source station number
	 * @param num grouping order
	 */
	public void setSourceStationNumberGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch source station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSourceStationNumberCollect()
	{
		setCollect(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch source station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSourceStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  destination station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDestStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  destination station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDestStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  destination station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDestStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  destination station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDestStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for destination station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setDestStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order destination station number
	 * @param num grouping order
	 */
	public void setDestStationNumberGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch destination station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setDestStationNumberCollect()
	{
		setCollect(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch destination station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setDestStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  arrival date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ARRIVALDATE.toString(), arg);
	}
	/**
	 *Set search value for  arrival date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ARRIVALDATE.toString(), arg);
	}
	/**
	 *Set search value for  arrival date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ARRIVALDATE.toString(), arg, compcode);
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
	public void setArrivalDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ARRIVALDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for arrival date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setArrivalDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.ARRIVALDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order arrival date
	 * @param num grouping order
	 */
	public void setArrivalDateGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.ARRIVALDATE.toString(), num);
	}

	/**
	 * Fetch arrival date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setArrivalDateCollect()
	{
		setCollect(_Prefix + CarryInformation.ARRIVALDATE.toString(), "");
	}

	/**
	 * Fetch arrival date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setArrivalDateCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.ARRIVALDATE.toString(), compcode);
	}

	/**
	 *Set search value for  control info
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControlInfo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CONTROLINFO.toString(), arg);
	}
	/**
	 *Set search value for  control info
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControlInfo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CONTROLINFO.toString(), arg);
	}
	/**
	 *Set search value for  control info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControlInfo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CONTROLINFO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  control info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControlInfo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CONTROLINFO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for control info
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setControlInfoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.CONTROLINFO.toString(), num, bool);
	}

	/**
	 * Set grouping order control info
	 * @param num grouping order
	 */
	public void setControlInfoGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.CONTROLINFO.toString(), num);
	}

	/**
	 * Fetch control info info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setControlInfoCollect()
	{
		setCollect(_Prefix + CarryInformation.CONTROLINFO.toString(), "");
	}

	/**
	 * Fetch control info info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setControlInfoCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.CONTROLINFO.toString(), compcode);
	}

	/**
	 *Set search value for  cancel request status
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequest(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUEST.toString(), arg);
	}
	/**
	 *Set search value for  cancel request status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequest(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUEST.toString(), arg);
	}
	/**
	 *Set search value for  cancel request status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequest(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUEST.toString(), arg, compcode);
	}
	/**
	 *Set search value for  cancel request status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequest(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUEST.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for cancel request status
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCancelRequestOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.CANCELREQUEST.toString(), num, bool);
	}

	/**
	 * Set grouping order cancel request status
	 * @param num grouping order
	 */
	public void setCancelRequestGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.CANCELREQUEST.toString(), num);
	}

	/**
	 * Fetch cancel request status info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCancelRequestCollect()
	{
		setCollect(_Prefix + CarryInformation.CANCELREQUEST.toString(), "");
	}

	/**
	 * Fetch cancel request status info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCancelRequestCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.CANCELREQUEST.toString(), compcode);
	}

	/**
	 *Set search value for  cancellation request date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequestdate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), arg);
	}
	/**
	 *Set search value for  cancellation request date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequestdate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), arg);
	}
	/**
	 *Set search value for  cancellation request date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequestdate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  cancellation request date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequestdate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for cancellation request date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCancelRequestdateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order cancellation request date
	 * @param num grouping order
	 */
	public void setCancelRequestdateGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), num);
	}

	/**
	 * Fetch cancellation request date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCancelRequestdateCollect()
	{
		setCollect(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), "");
	}

	/**
	 * Fetch cancellation request date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCancelRequestdateCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), compcode);
	}

	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for schedule number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setScheduleNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order schedule number
	 * @param num grouping order
	 */
	public void setScheduleNumberGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), num);
	}

	/**
	 * Fetch schedule number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setScheduleNumberCollect()
	{
		setCollect(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), "");
	}

	/**
	 * Fetch schedule number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setScheduleNumberCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  aisle station number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  aisle station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  aisle station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  aisle station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for aisle station number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAisleStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order aisle station number
	 * @param num grouping order
	 */
	public void setAisleStationNumberGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch aisle station number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAisleStationNumberCollect()
	{
		setCollect(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch aisle station number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAisleStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  last sation number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  last sation number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  last sation number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last sation number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for last sation number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setEndStationNumberOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), num, bool);
	}

	/**
	 * Set grouping order last sation number
	 * @param num grouping order
	 */
	public void setEndStationNumberGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), num);
	}

	/**
	 * Fetch last sation number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setEndStationNumberCollect()
	{
		setCollect(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), "");
	}

	/**
	 * Fetch last sation number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setEndStationNumberCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), compcode);
	}

	/**
	 *Set search value for  error code
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ERRORCODE.toString(), arg);
	}
	/**
	 *Set search value for  error code
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ERRORCODE.toString(), arg);
	}
	/**
	 *Set search value for  error code
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ERRORCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  error code
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ERRORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for error code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setErrorCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.ERRORCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order error code
	 * @param num grouping order
	 */
	public void setErrorCodeGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.ERRORCODE.toString(), num);
	}

	/**
	 * Fetch error code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setErrorCodeCollect()
	{
		setCollect(_Prefix + CarryInformation.ERRORCODE.toString(), "");
	}

	/**
	 * Fetch error code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setErrorCodeCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.ERRORCODE.toString(), compcode);
	}

	/**
	 *Set search value for  maintenance terminal number
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaintenanceTerminal(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), arg);
	}
	/**
	 *Set search value for  maintenance terminal number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaintenanceTerminal(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), arg);
	}
	/**
	 *Set search value for  maintenance terminal number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaintenanceTerminal(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), arg, compcode);
	}
	/**
	 *Set search value for  maintenance terminal number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaintenanceTerminal(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for maintenance terminal number
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setMaintenanceTerminalOrder(int num, boolean bool)
	{
		setOrder(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), num, bool);
	}

	/**
	 * Set grouping order maintenance terminal number
	 * @param num grouping order
	 */
	public void setMaintenanceTerminalGroup(int num)
	{
		setGroup(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), num);
	}

	/**
	 * Fetch maintenance terminal number info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setMaintenanceTerminalCollect()
	{
		setCollect(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), "");
	}

	/**
	 * Fetch maintenance terminal number info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setMaintenanceTerminalCollect(String compcode)
	{
		setCollect(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), compcode);
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
		return "$Id: CarryInformationSearchKey.java,v 1.3 2006/11/13 04:33:10 suresh Exp $" ;
	}
}
