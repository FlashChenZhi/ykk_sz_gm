//$Id: AreaSearchKey.java,v 1.3 2006/11/13 04:33:12 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Area;

/**
 * This is the search key class for AREA use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:12 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class AreaSearchKey
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
	public AreaSearchKey()
	{
		super(new Area()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Area No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAreaNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.AREANO.toString(), num, bool);
	}

	/**
	 * Set grouping order Area No
	 * @param num grouping order
	 */
	public void setAreaNoGroup(int num)
	{
		setGroup(_Prefix + Area.AREANO.toString(), num);
	}

	/**
	 * Fetch Area No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAreaNoCollect()
	{
		setCollect(_Prefix + Area.AREANO.toString(), "");
	}

	/**
	 * Fetch Area No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAreaNoCollect(String compcode)
	{
		setCollect(_Prefix + Area.AREANO.toString(), compcode);
	}

	/**
	 *Set search value for  Area Name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANAME.toString(), arg);
	}
	/**
	 *Set search value for  Area Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANAME.toString(), arg);
	}
	/**
	 *Set search value for  Area Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREANAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Area Name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAreaNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.AREANAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Area Name
	 * @param num grouping order
	 */
	public void setAreaNameGroup(int num)
	{
		setGroup(_Prefix + Area.AREANAME.toString(), num);
	}

	/**
	 * Fetch Area Name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAreaNameCollect()
	{
		setCollect(_Prefix + Area.AREANAME.toString(), "");
	}

	/**
	 * Fetch Area Name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAreaNameCollect(String compcode)
	{
		setCollect(_Prefix + Area.AREANAME.toString(), compcode);
	}

	/**
	 *Set search value for  Area Type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPE.toString(), arg);
	}
	/**
	 *Set search value for  Area Type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPE.toString(), arg);
	}
	/**
	 *Set search value for  Area Type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area Type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaType(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Area Type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAreaTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.AREATYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order Area Type
	 * @param num grouping order
	 */
	public void setAreaTypeGroup(int num)
	{
		setGroup(_Prefix + Area.AREATYPE.toString(), num);
	}

	/**
	 * Fetch Area Type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAreaTypeCollect()
	{
		setCollect(_Prefix + Area.AREATYPE.toString(), "");
	}

	/**
	 * Fetch Area Type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAreaTypeCollect(String compcode)
	{
		setCollect(_Prefix + Area.AREATYPE.toString(), compcode);
	}

	/**
	 *Set search value for  Area Type Name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaTypeName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPENAME.toString(), arg);
	}
	/**
	 *Set search value for  Area Type Name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaTypeName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPENAME.toString(), arg);
	}
	/**
	 *Set search value for  Area Type Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaTypeName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPENAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area Type Name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaTypeName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREATYPENAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Area Type Name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAreaTypeNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.AREATYPENAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Area Type Name
	 * @param num grouping order
	 */
	public void setAreaTypeNameGroup(int num)
	{
		setGroup(_Prefix + Area.AREATYPENAME.toString(), num);
	}

	/**
	 * Fetch Area Type Name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAreaTypeNameCollect()
	{
		setCollect(_Prefix + Area.AREATYPENAME.toString(), "");
	}

	/**
	 * Fetch Area Type Name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAreaTypeNameCollect(String compcode)
	{
		setCollect(_Prefix + Area.AREATYPENAME.toString(), compcode);
	}

	/**
	 *Set search value for  Area Duty
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaDuty(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREADUTY.toString(), arg);
	}
	/**
	 *Set search value for  Area Duty
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaDuty(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREADUTY.toString(), arg);
	}
	/**
	 *Set search value for  Area Duty
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaDuty(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREADUTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area Duty
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaDuty(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.AREADUTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Area Duty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAreaDutyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.AREADUTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Area Duty
	 * @param num grouping order
	 */
	public void setAreaDutyGroup(int num)
	{
		setGroup(_Prefix + Area.AREADUTY.toString(), num);
	}

	/**
	 * Fetch Area Duty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAreaDutyCollect()
	{
		setCollect(_Prefix + Area.AREADUTY.toString(), "");
	}

	/**
	 * Fetch Area Duty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAreaDutyCollect(String compcode)
	{
		setCollect(_Prefix + Area.AREADUTY.toString(), compcode);
	}

	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.DELETEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Delete flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.DELETEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.DELETEFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Delete flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDeleteFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.DELETEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Delete flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setDeleteFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.DELETEFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Delete flag
	 * @param num grouping order
	 */
	public void setDeleteFlagGroup(int num)
	{
		setGroup(_Prefix + Area.DELETEFLAG.toString(), num);
	}

	/**
	 * Fetch Delete flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setDeleteFlagCollect()
	{
		setCollect(_Prefix + Area.DELETEFLAG.toString(), "");
	}

	/**
	 * Fetch Delete flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setDeleteFlagCollect(String compcode)
	{
		setCollect(_Prefix + Area.DELETEFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Vacant Search type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setVacantSearchType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.VACANTSEARCHTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Vacant Search type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setVacantSearchType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.VACANTSEARCHTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Vacant Search type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setVacantSearchType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.VACANTSEARCHTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Vacant Search type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setVacantSearchType(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.VACANTSEARCHTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Vacant Search type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setVacantSearchTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.VACANTSEARCHTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order Vacant Search type
	 * @param num grouping order
	 */
	public void setVacantSearchTypeGroup(int num)
	{
		setGroup(_Prefix + Area.VACANTSEARCHTYPE.toString(), num);
	}

	/**
	 * Fetch Vacant Search type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setVacantSearchTypeCollect()
	{
		setCollect(_Prefix + Area.VACANTSEARCHTYPE.toString(), "");
	}

	/**
	 * Fetch Vacant Search type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setVacantSearchTypeCollect(String compcode)
	{
		setCollect(_Prefix + Area.VACANTSEARCHTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  Last used bank no
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBankNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUSEDBANKNO.toString(), arg);
	}
	/**
	 *Set search value for  Last used bank no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBankNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUSEDBANKNO.toString(), arg);
	}
	/**
	 *Set search value for  Last used bank no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBankNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUSEDBANKNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Last used bank no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUsedBankNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUSEDBANKNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last used bank no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUsedBankNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.LASTUSEDBANKNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Last used bank no
	 * @param num grouping order
	 */
	public void setLastUsedBankNoGroup(int num)
	{
		setGroup(_Prefix + Area.LASTUSEDBANKNO.toString(), num);
	}

	/**
	 * Fetch Last used bank no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUsedBankNoCollect()
	{
		setCollect(_Prefix + Area.LASTUSEDBANKNO.toString(), "");
	}

	/**
	 * Fetch Last used bank no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUsedBankNoCollect(String compcode)
	{
		setCollect(_Prefix + Area.LASTUSEDBANKNO.toString(), compcode);
	}

	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.REGISTDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered date
	 * @param num grouping order
	 */
	public void setRegistDateGroup(int num)
	{
		setGroup(_Prefix + Area.REGISTDATE.toString(), num);
	}

	/**
	 * Fetch Registered date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistDateCollect()
	{
		setCollect(_Prefix + Area.REGISTDATE.toString(), "");
	}

	/**
	 * Fetch Registered date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistDateCollect(String compcode)
	{
		setCollect(_Prefix + Area.REGISTDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTPNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistPnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.REGISTPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered name
	 * @param num grouping order
	 */
	public void setRegistPnameGroup(int num)
	{
		setGroup(_Prefix + Area.REGISTPNAME.toString(), num);
	}

	/**
	 * Fetch Registered name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistPnameCollect()
	{
		setCollect(_Prefix + Area.REGISTPNAME.toString(), "");
	}

	/**
	 * Fetch Registered name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistPnameCollect(String compcode)
	{
		setCollect(_Prefix + Area.REGISTPNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.LASTUPDATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update date
	 * @param num grouping order
	 */
	public void setLastUpdateDateGroup(int num)
	{
		setGroup(_Prefix + Area.LASTUPDATEDATE.toString(), num);
	}

	/**
	 * Fetch Last update date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdateDateCollect()
	{
		setCollect(_Prefix + Area.LASTUPDATEDATE.toString(), "");
	}

	/**
	 * Fetch Last update date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdateDateCollect(String compcode)
	{
		setCollect(_Prefix + Area.LASTUPDATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEPNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Area.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdatePnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Area.LASTUPDATEPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update name
	 * @param num grouping order
	 */
	public void setLastUpdatePnameGroup(int num)
	{
		setGroup(_Prefix + Area.LASTUPDATEPNAME.toString(), num);
	}

	/**
	 * Fetch Last update name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdatePnameCollect()
	{
		setCollect(_Prefix + Area.LASTUPDATEPNAME.toString(), "");
	}

	/**
	 * Fetch Last update name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdatePnameCollect(String compcode)
	{
		setCollect(_Prefix + Area.LASTUPDATEPNAME.toString(), compcode);
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
		return "$Id: AreaSearchKey.java,v 1.3 2006/11/13 04:33:12 suresh Exp $" ;
	}
}
