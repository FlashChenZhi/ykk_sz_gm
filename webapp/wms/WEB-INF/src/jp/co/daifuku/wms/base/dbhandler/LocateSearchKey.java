//$Id: LocateSearchKey.java,v 1.3 2006/11/13 04:33:07 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Locate;

/**
 * This is the search key class for LOCATE use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:07 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class LocateSearchKey
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
	public LocateSearchKey()
	{
		super(new Locate()) ;
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
		setValue(_Prefix + Locate.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AREANO.toString(), arg, compcode);
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
		setValue(_Prefix + Locate.AREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Area No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAreaNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.AREANO.toString(), num, bool);
	}

	/**
	 * Set grouping order Area No
	 * @param num grouping order
	 */
	public void setAreaNoGroup(int num)
	{
		setGroup(_Prefix + Locate.AREANO.toString(), num);
	}

	/**
	 * Fetch Area No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAreaNoCollect()
	{
		setCollect(_Prefix + Locate.AREANO.toString(), "");
	}

	/**
	 * Fetch Area No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAreaNoCollect(String compcode)
	{
		setCollect(_Prefix + Locate.AREANO.toString(), compcode);
	}

	/**
	 *Set search value for  Location No (Block No)
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Location No (Block No)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Location No (Block No)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LOCATIONNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Location No (Block No)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LOCATIONNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Location No (Block No)
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLocationNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.LOCATIONNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Location No (Block No)
	 * @param num grouping order
	 */
	public void setLocationNoGroup(int num)
	{
		setGroup(_Prefix + Locate.LOCATIONNO.toString(), num);
	}

	/**
	 * Fetch Location No (Block No) info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLocationNoCollect()
	{
		setCollect(_Prefix + Locate.LOCATIONNO.toString(), "");
	}

	/**
	 * Fetch Location No (Block No) info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLocationNoCollect(String compcode)
	{
		setCollect(_Prefix + Locate.LOCATIONNO.toString(), compcode);
	}

	/**
	 *Set search value for  Aisle No
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AISLENO.toString(), arg);
	}
	/**
	 *Set search value for  Aisle No
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AISLENO.toString(), arg);
	}
	/**
	 *Set search value for  Aisle No
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AISLENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Aisle No
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AISLENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Aisle No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAisleNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.AISLENO.toString(), num, bool);
	}

	/**
	 * Set grouping order Aisle No
	 * @param num grouping order
	 */
	public void setAisleNoGroup(int num)
	{
		setGroup(_Prefix + Locate.AISLENO.toString(), num);
	}

	/**
	 * Fetch Aisle No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAisleNoCollect()
	{
		setCollect(_Prefix + Locate.AISLENO.toString(), "");
	}

	/**
	 * Fetch Aisle No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAisleNoCollect(String compcode)
	{
		setCollect(_Prefix + Locate.AISLENO.toString(), compcode);
	}

	/**
	 *Set search value for  Bank No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBankNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BANKNO.toString(), arg);
	}
	/**
	 *Set search value for  Bank No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBankNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BANKNO.toString(), arg);
	}
	/**
	 *Set search value for  Bank No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBankNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BANKNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Bank No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBankNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BANKNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Bank No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBankNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.BANKNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Bank No
	 * @param num grouping order
	 */
	public void setBankNoGroup(int num)
	{
		setGroup(_Prefix + Locate.BANKNO.toString(), num);
	}

	/**
	 * Fetch Bank No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBankNoCollect()
	{
		setCollect(_Prefix + Locate.BANKNO.toString(), "");
	}

	/**
	 * Fetch Bank No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBankNoCollect(String compcode)
	{
		setCollect(_Prefix + Locate.BANKNO.toString(), compcode);
	}

	/**
	 *Set search value for  Bay No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBayNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BAYNO.toString(), arg);
	}
	/**
	 *Set search value for  Bay No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBayNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BAYNO.toString(), arg);
	}
	/**
	 *Set search value for  Bay No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBayNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BAYNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Bay No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBayNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BAYNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Bay No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBayNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.BAYNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Bay No
	 * @param num grouping order
	 */
	public void setBayNoGroup(int num)
	{
		setGroup(_Prefix + Locate.BAYNO.toString(), num);
	}

	/**
	 * Fetch Bay No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBayNoCollect()
	{
		setCollect(_Prefix + Locate.BAYNO.toString(), "");
	}

	/**
	 * Fetch Bay No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBayNoCollect(String compcode)
	{
		setCollect(_Prefix + Locate.BAYNO.toString(), compcode);
	}

	/**
	 *Set search value for  Level No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLevelNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LEVELNO.toString(), arg);
	}
	/**
	 *Set search value for  Level No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLevelNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LEVELNO.toString(), arg);
	}
	/**
	 *Set search value for  Level No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLevelNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LEVELNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Level No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLevelNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LEVELNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Level No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLevelNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.LEVELNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Level No
	 * @param num grouping order
	 */
	public void setLevelNoGroup(int num)
	{
		setGroup(_Prefix + Locate.LEVELNO.toString(), num);
	}

	/**
	 * Fetch Level No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLevelNoCollect()
	{
		setCollect(_Prefix + Locate.LEVELNO.toString(), "");
	}

	/**
	 * Fetch Level No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLevelNoCollect(String compcode)
	{
		setCollect(_Prefix + Locate.LEVELNO.toString(), compcode);
	}

	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.STATUSFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.STATUSFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Status flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.STATUSFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Status flag
	 * @param num grouping order
	 */
	public void setStatusFlagGroup(int num)
	{
		setGroup(_Prefix + Locate.STATUSFLAG.toString(), num);
	}

	/**
	 * Fetch Status flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusFlagCollect()
	{
		setCollect(_Prefix + Locate.STATUSFLAG.toString(), "");
	}

	/**
	 * Fetch Status flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusFlagCollect(String compcode)
	{
		setCollect(_Prefix + Locate.STATUSFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Prohibition flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProhibitionFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PROHIBITIONFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Prohibition flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProhibitionFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PROHIBITIONFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Prohibition flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProhibitionFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PROHIBITIONFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Prohibition flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProhibitionFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PROHIBITIONFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Prohibition flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setProhibitionFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.PROHIBITIONFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Prohibition flag
	 * @param num grouping order
	 */
	public void setProhibitionFlagGroup(int num)
	{
		setGroup(_Prefix + Locate.PROHIBITIONFLAG.toString(), num);
	}

	/**
	 * Fetch Prohibition flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setProhibitionFlagCollect()
	{
		setCollect(_Prefix + Locate.PROHIBITIONFLAG.toString(), "");
	}

	/**
	 * Fetch Prohibition flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setProhibitionFlagCollect(String compcode)
	{
		setCollect(_Prefix + Locate.PROHIBITIONFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Filling rate
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFillingRate(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.FILLINGRATE.toString(), arg);
	}
	/**
	 *Set search value for  Filling rate
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFillingRate(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.FILLINGRATE.toString(), arg);
	}
	/**
	 *Set search value for  Filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFillingRate(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.FILLINGRATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFillingRate(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.FILLINGRATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Filling rate
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setFillingRateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.FILLINGRATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Filling rate
	 * @param num grouping order
	 */
	public void setFillingRateGroup(int num)
	{
		setGroup(_Prefix + Locate.FILLINGRATE.toString(), num);
	}

	/**
	 * Fetch Filling rate info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setFillingRateCollect()
	{
		setCollect(_Prefix + Locate.FILLINGRATE.toString(), "");
	}

	/**
	 * Fetch Filling rate info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setFillingRateCollect(String compcode)
	{
		setCollect(_Prefix + Locate.FILLINGRATE.toString(), compcode);
	}

	/**
	 *Set search value for  Plan filling rate
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanFillingRate(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PLANFILLINGRATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan filling rate
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanFillingRate(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PLANFILLINGRATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanFillingRate(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PLANFILLINGRATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanFillingRate(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PLANFILLINGRATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan filling rate
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanFillingRateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.PLANFILLINGRATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan filling rate
	 * @param num grouping order
	 */
	public void setPlanFillingRateGroup(int num)
	{
		setGroup(_Prefix + Locate.PLANFILLINGRATE.toString(), num);
	}

	/**
	 * Fetch Plan filling rate info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanFillingRateCollect()
	{
		setCollect(_Prefix + Locate.PLANFILLINGRATE.toString(), "");
	}

	/**
	 * Fetch Plan filling rate info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanFillingRateCollect(String compcode)
	{
		setCollect(_Prefix + Locate.PLANFILLINGRATE.toString(), compcode);
	}

	/**
	 *Set search value for  Mixed load count
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMixedLoadCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.MIXEDLOADCNT.toString(), arg);
	}
	/**
	 *Set search value for  Mixed load count
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMixedLoadCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.MIXEDLOADCNT.toString(), arg);
	}
	/**
	 *Set search value for  Mixed load count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMixedLoadCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.MIXEDLOADCNT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Mixed load count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMixedLoadCnt(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.MIXEDLOADCNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Mixed load count
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setMixedLoadCntOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.MIXEDLOADCNT.toString(), num, bool);
	}

	/**
	 * Set grouping order Mixed load count
	 * @param num grouping order
	 */
	public void setMixedLoadCntGroup(int num)
	{
		setGroup(_Prefix + Locate.MIXEDLOADCNT.toString(), num);
	}

	/**
	 * Fetch Mixed load count info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setMixedLoadCntCollect()
	{
		setCollect(_Prefix + Locate.MIXEDLOADCNT.toString(), "");
	}

	/**
	 * Fetch Mixed load count info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setMixedLoadCntCollect(String compcode)
	{
		setCollect(_Prefix + Locate.MIXEDLOADCNT.toString(), compcode);
	}

	/**
	 *Set search value for  Zone
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZone(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.ZONE.toString(), arg);
	}
	/**
	 *Set search value for  Zone
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZone(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.ZONE.toString(), arg);
	}
	/**
	 *Set search value for  Zone
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZone(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.ZONE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Zone
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZone(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.ZONE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Zone
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setZoneOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.ZONE.toString(), num, bool);
	}

	/**
	 * Set grouping order Zone
	 * @param num grouping order
	 */
	public void setZoneGroup(int num)
	{
		setGroup(_Prefix + Locate.ZONE.toString(), num);
	}

	/**
	 * Fetch Zone info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setZoneCollect()
	{
		setCollect(_Prefix + Locate.ZONE.toString(), "");
	}

	/**
	 * Fetch Zone info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setZoneCollect(String compcode)
	{
		setCollect(_Prefix + Locate.ZONE.toString(), compcode);
	}

	/**
	 *Set search value for  Registration flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Registration flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Registration flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Registration flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registration flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.REGISTFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Registration flag
	 * @param num grouping order
	 */
	public void setRegistFlagGroup(int num)
	{
		setGroup(_Prefix + Locate.REGISTFLAG.toString(), num);
	}

	/**
	 * Fetch Registration flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistFlagCollect()
	{
		setCollect(_Prefix + Locate.REGISTFLAG.toString(), "");
	}

	/**
	 * Fetch Registration flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistFlagCollect(String compcode)
	{
		setCollect(_Prefix + Locate.REGISTFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Locate.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.REGISTDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered date
	 * @param num grouping order
	 */
	public void setRegistDateGroup(int num) 
	{
		setGroup(_Prefix + Locate.REGISTDATE.toString(), num);
	}

	/**
	 * Fetch Registered date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistDateCollect()
	{
		setCollect(_Prefix + Locate.REGISTDATE.toString(), "");
	}

	/**
	 * Fetch Registered date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistDateCollect(String compcode)
	{
		setCollect(_Prefix + Locate.REGISTDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Locate.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistPnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.REGISTPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered name
	 * @param num grouping order
	 */
	public void setRegistPnameGroup(int num)
	{
		setGroup(_Prefix + Locate.REGISTPNAME.toString(), num);
	}

	/**
	 * Fetch Registered name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistPnameCollect()
	{
		setCollect(_Prefix + Locate.REGISTPNAME.toString(), "");
	}

	/**
	 * Fetch Registered name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistPnameCollect(String compcode)
	{
		setCollect(_Prefix + Locate.REGISTPNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Locate.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.LASTUPDATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update date
	 * @param num grouping order
	 */
	public void setLastUpdateDateGroup(int num)
	{
		setGroup(_Prefix + Locate.LASTUPDATEDATE.toString(), num);
	}

	/**
	 * Fetch Last update date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdateDateCollect()
	{
		setCollect(_Prefix + Locate.LASTUPDATEDATE.toString(), "");
	}

	/**
	 * Fetch Last update date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdateDateCollect(String compcode)
	{
		setCollect(_Prefix + Locate.LASTUPDATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Locate.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdatePnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Locate.LASTUPDATEPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update name
	 * @param num grouping order
	 */
	public void setLastUpdatePnameGroup(int num)
	{
		setGroup(_Prefix + Locate.LASTUPDATEPNAME.toString(), num);
	}

	/**
	 * Fetch Last update name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdatePnameCollect()
	{
		setCollect(_Prefix + Locate.LASTUPDATEPNAME.toString(), "");
	}

	/**
	 * Fetch Last update name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdatePnameCollect(String compcode)
	{
		setCollect(_Prefix + Locate.LASTUPDATEPNAME.toString(), compcode);
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
		return "$Id: LocateSearchKey.java,v 1.3 2006/11/13 04:33:07 suresh Exp $" ;
	}
}
