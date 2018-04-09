//$Id: ResultViewSearchKey.java,v 1.2 2006/11/09 07:51:34 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.ResultView;

/**
 * This is the search key class for DVRESULTVIEW use.
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:51:34 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ResultViewSearchKey
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
	public ResultViewSearchKey()
	{
		super(new ResultView()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  Work date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKDATE.toString(), arg);
	}
	/**
	 *Set search value for  Work date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKDATE.toString(), arg);
	}
	/**
	 *Set search value for  Work date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.WORKDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Work date
	 * @param num grouping order
	 */
	public void setWorkDateGroup(int num)
	{
		setGroup(_Prefix + ResultView.WORKDATE.toString(), num);
	}

	/**
	 * Fetch Work date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkDateCollect()
	{
		setCollect(_Prefix + ResultView.WORKDATE.toString(), "");
	}

	/**
	 * Fetch Work date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkDateCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.WORKDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Work No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.JOBNO.toString(), arg);
	}
	/**
	 *Set search value for  Work No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.JOBNO.toString(), arg);
	}
	/**
	 *Set search value for  Work No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.JOBNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.JOBNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setJobNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.JOBNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Work No
	 * @param num grouping order
	 */
	public void setJobNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.JOBNO.toString(), num);
	}

	/**
	 * Fetch Work No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setJobNoCollect()
	{
		setCollect(_Prefix + ResultView.JOBNO.toString(), "");
	}

	/**
	 * Fetch Work No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setJobNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.JOBNO.toString(), compcode);
	}

	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.JOBTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Work type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.JOBTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.JOBTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.JOBTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setJobTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.JOBTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order Work type
	 * @param num grouping order
	 */
	public void setJobTypeGroup(int num)
	{
		setGroup(_Prefix + ResultView.JOBTYPE.toString(), num);
	}

	/**
	 * Fetch Work type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setJobTypeCollect()
	{
		setCollect(_Prefix + ResultView.JOBTYPE.toString(), "");
	}

	/**
	 * Fetch Work type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setJobTypeCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.JOBTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  Collect work no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCollectJobNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.COLLECTJOBNO.toString(), arg);
	}
	/**
	 *Set search value for  Collect work no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCollectJobNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.COLLECTJOBNO.toString(), arg);
	}
	/**
	 *Set search value for  Collect work no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCollectJobNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.COLLECTJOBNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Collect work no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCollectJobNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.COLLECTJOBNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Collect work no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCollectJobNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.COLLECTJOBNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Collect work no
	 * @param num grouping order
	 */
	public void setCollectJobNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.COLLECTJOBNO.toString(), num);
	}

	/**
	 * Fetch Collect work no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCollectJobNoCollect()
	{
		setCollect(_Prefix + ResultView.COLLECTJOBNO.toString(), "");
	}

	/**
	 * Fetch Collect work no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCollectJobNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.COLLECTJOBNO.toString(), compcode);
	}

	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.STATUSFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + ResultView.STATUSFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Status flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.STATUSFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Status flag
	 * @param num grouping order
	 */
	public void setStatusFlagGroup(int num)
	{
		setGroup(_Prefix + ResultView.STATUSFLAG.toString(), num);
	}

	/**
	 * Fetch Status flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusFlagCollect()
	{
		setCollect(_Prefix + ResultView.STATUSFLAG.toString(), "");
	}

	/**
	 * Fetch Status flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusFlagCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.STATUSFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Work start flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBeginningFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BEGINNINGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Work start flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBeginningFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BEGINNINGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Work start flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBeginningFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BEGINNINGFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work start flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBeginningFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BEGINNINGFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work start flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBeginningFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.BEGINNINGFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Work start flag
	 * @param num grouping order
	 */
	public void setBeginningFlagGroup(int num)
	{
		setGroup(_Prefix + ResultView.BEGINNINGFLAG.toString(), num);
	}

	/**
	 * Fetch Work start flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBeginningFlagCollect()
	{
		setCollect(_Prefix + ResultView.BEGINNINGFLAG.toString(), "");
	}

	/**
	 * Fetch Work start flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBeginningFlagCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.BEGINNINGFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Plan unique key
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Plan unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANUKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan unique key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanUkeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.PLANUKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan unique key
	 * @param num grouping order
	 */
	public void setPlanUkeyGroup(int num)
	{
		setGroup(_Prefix + ResultView.PLANUKEY.toString(), num);
	}

	/**
	 * Fetch Plan unique key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanUkeyCollect()
	{
		setCollect(_Prefix + ResultView.PLANUKEY.toString(), "");
	}

	/**
	 * Fetch Plan unique key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanUkeyCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.PLANUKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Stock ID
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockId(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.STOCKID.toString(), arg);
	}
	/**
	 *Set search value for  Stock ID
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockId(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.STOCKID.toString(), arg);
	}
	/**
	 *Set search value for  Stock ID
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockId(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.STOCKID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Stock ID
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockId(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.STOCKID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Stock ID
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStockIdOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.STOCKID.toString(), num, bool);
	}

	/**
	 * Set grouping order Stock ID
	 * @param num grouping order
	 */
	public void setStockIdGroup(int num)
	{
		setGroup(_Prefix + ResultView.STOCKID.toString(), num);
	}

	/**
	 * Fetch Stock ID info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStockIdCollect()
	{
		setCollect(_Prefix + ResultView.STOCKID.toString(), "");
	}

	/**
	 * Fetch Stock ID info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStockIdCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.STOCKID.toString(), compcode);
	}

	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.AREANO.toString(), arg, compcode);
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
		setValue(_Prefix + ResultView.AREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Area No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setAreaNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.AREANO.toString(), num, bool);
	}

	/**
	 * Set grouping order Area No
	 * @param num grouping order
	 */
	public void setAreaNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.AREANO.toString(), num);
	}

	/**
	 * Fetch Area No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setAreaNoCollect()
	{
		setCollect(_Prefix + ResultView.AREANO.toString(), "");
	}

	/**
	 * Fetch Area No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setAreaNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.AREANO.toString(), compcode);
	}

	/**
	 *Set search value for  Zone No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ZONENO.toString(), arg);
	}
	/**
	 *Set search value for  Zone No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ZONENO.toString(), arg);
	}
	/**
	 *Set search value for  Zone No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ZONENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Zone No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ZONENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Zone No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setZoneNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.ZONENO.toString(), num, bool);
	}

	/**
	 * Set grouping order Zone No
	 * @param num grouping order
	 */
	public void setZoneNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.ZONENO.toString(), num);
	}

	/**
	 * Fetch Zone No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setZoneNoCollect()
	{
		setCollect(_Prefix + ResultView.ZONENO.toString(), "");
	}

	/**
	 * Fetch Zone No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setZoneNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.ZONENO.toString(), compcode);
	}

	/**
	 *Set search value for  Location No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Location No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Location No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LOCATIONNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Location No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LOCATIONNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Location No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLocationNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.LOCATIONNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Location No
	 * @param num grouping order
	 */
	public void setLocationNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.LOCATIONNO.toString(), num);
	}

	/**
	 * Fetch Location No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLocationNoCollect()
	{
		setCollect(_Prefix + ResultView.LOCATIONNO.toString(), "");
	}

	/**
	 * Fetch Location No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLocationNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.LOCATIONNO.toString(), compcode);
	}

	/**
	 *Set search value for  Plan date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.PLANDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan date
	 * @param num grouping order
	 */
	public void setPlanDateGroup(int num)
	{
		setGroup(_Prefix + ResultView.PLANDATE.toString(), num);
	}

	/**
	 * Fetch Plan date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanDateCollect()
	{
		setCollect(_Prefix + ResultView.PLANDATE.toString(), "");
	}

	/**
	 * Fetch Plan date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanDateCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.PLANDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CONSIGNORCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Consignor code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.CONSIGNORCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Consignor code
	 * @param num grouping order
	 */
	public void setConsignorCodeGroup(int num)
	{
		setGroup(_Prefix + ResultView.CONSIGNORCODE.toString(), num);
	}

	/**
	 * Fetch Consignor code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorCodeCollect()
	{
		setCollect(_Prefix + ResultView.CONSIGNORCODE.toString(), "");
	}

	/**
	 * Fetch Consignor code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorCodeCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.CONSIGNORCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Consignor name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  Consignor name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  Consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CONSIGNORNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CONSIGNORNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Consignor name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setConsignorNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.CONSIGNORNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Consignor name
	 * @param num grouping order
	 */
	public void setConsignorNameGroup(int num)
	{
		setGroup(_Prefix + ResultView.CONSIGNORNAME.toString(), num);
	}

	/**
	 * Fetch Consignor name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setConsignorNameCollect()
	{
		setCollect(_Prefix + ResultView.CONSIGNORNAME.toString(), "");
	}

	/**
	 * Fetch Consignor name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setConsignorNameCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.CONSIGNORNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SUPPLIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SUPPLIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SUPPLIERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SUPPLIERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Supplier code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSupplierCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.SUPPLIERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Supplier code
	 * @param num grouping order
	 */
	public void setSupplierCodeGroup(int num)
	{
		setGroup(_Prefix + ResultView.SUPPLIERCODE.toString(), num);
	}

	/**
	 * Fetch Supplier code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSupplierCodeCollect()
	{
		setCollect(_Prefix + ResultView.SUPPLIERCODE.toString(), "");
	}

	/**
	 * Fetch Supplier code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSupplierCodeCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.SUPPLIERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Supplier name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SUPPLIERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Supplier name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SUPPLIERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Supplier name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SUPPLIERNAME1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Supplier name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SUPPLIERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Supplier name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSupplierName1Order(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.SUPPLIERNAME1.toString(), num, bool);
	}

	/**
	 * Set grouping order Supplier name
	 * @param num grouping order
	 */
	public void setSupplierName1Group(int num)
	{
		setGroup(_Prefix + ResultView.SUPPLIERNAME1.toString(), num);
	}

	/**
	 * Fetch Supplier name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSupplierName1Collect()
	{
		setCollect(_Prefix + ResultView.SUPPLIERNAME1.toString(), "");
	}

	/**
	 * Fetch Supplier name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSupplierName1Collect(String compcode)
	{
		setCollect(_Prefix + ResultView.SUPPLIERNAME1.toString(), compcode);
	}

	/**
	 *Set search value for  Receiving ticket no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.INSTOCKTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving ticket no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.INSTOCKTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.INSTOCKTICKETNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Receiving ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.INSTOCKTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Receiving ticket no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInstockTicketNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.INSTOCKTICKETNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Receiving ticket no
	 * @param num grouping order
	 */
	public void setInstockTicketNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.INSTOCKTICKETNO.toString(), num);
	}

	/**
	 * Fetch Receiving ticket no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInstockTicketNoCollect()
	{
		setCollect(_Prefix + ResultView.INSTOCKTICKETNO.toString(), "");
	}

	/**
	 * Fetch Receiving ticket no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInstockTicketNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.INSTOCKTICKETNO.toString(), compcode);
	}

	/**
	 *Set search value for  Receiving line no
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.INSTOCKLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.INSTOCKLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.INSTOCKLINENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Receiving line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.INSTOCKLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Receiving line no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInstockLineNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.INSTOCKLINENO.toString(), num, bool);
	}

	/**
	 * Set grouping order Receiving line no
	 * @param num grouping order
	 */
	public void setInstockLineNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.INSTOCKLINENO.toString(), num);
	}

	/**
	 * Fetch Receiving line no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInstockLineNoCollect()
	{
		setCollect(_Prefix + ResultView.INSTOCKLINENO.toString(), "");
	}

	/**
	 * Fetch Receiving line no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInstockLineNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.INSTOCKLINENO.toString(), compcode);
	}

	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CUSTOMERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CUSTOMERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Customer code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.CUSTOMERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Customer code
	 * @param num grouping order
	 */
	public void setCustomerCodeGroup(int num)
	{
		setGroup(_Prefix + ResultView.CUSTOMERCODE.toString(), num);
	}

	/**
	 * Fetch Customer code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerCodeCollect()
	{
		setCollect(_Prefix + ResultView.CUSTOMERCODE.toString(), "");
	}

	/**
	 * Fetch Customer code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerCodeCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.CUSTOMERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Customer name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Customer name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Customer name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CUSTOMERNAME1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Customer name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CUSTOMERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Customer name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCustomerName1Order(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.CUSTOMERNAME1.toString(), num, bool);
	}

	/**
	 * Set grouping order Customer name
	 * @param num grouping order
	 */
	public void setCustomerName1Group(int num)
	{
		setGroup(_Prefix + ResultView.CUSTOMERNAME1.toString(), num);
	}

	/**
	 * Fetch Customer name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCustomerName1Collect()
	{
		setCollect(_Prefix + ResultView.CUSTOMERNAME1.toString(), "");
	}

	/**
	 * Fetch Customer name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCustomerName1Collect(String compcode)
	{
		setCollect(_Prefix + ResultView.CUSTOMERNAME1.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHIPPINGTICKETNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHIPPINGTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping ticket no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShippingTicketNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.SHIPPINGTICKETNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping ticket no
	 * @param num grouping order
	 */
	public void setShippingTicketNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.SHIPPINGTICKETNO.toString(), num);
	}

	/**
	 * Fetch Shipping ticket no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShippingTicketNoCollect()
	{
		setCollect(_Prefix + ResultView.SHIPPINGTICKETNO.toString(), "");
	}

	/**
	 * Fetch Shipping ticket no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShippingTicketNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.SHIPPINGTICKETNO.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHIPPINGLINENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHIPPINGLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping line no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShippingLineNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.SHIPPINGLINENO.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping line no
	 * @param num grouping order
	 */
	public void setShippingLineNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.SHIPPINGLINENO.toString(), num);
	}

	/**
	 * Fetch Shipping line no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShippingLineNoCollect()
	{
		setCollect(_Prefix + ResultView.SHIPPINGLINENO.toString(), "");
	}

	/**
	 * Fetch Shipping line no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShippingLineNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.SHIPPINGLINENO.toString(), compcode);
	}

	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITEMCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Item code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItemCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.ITEMCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Item code
	 * @param num grouping order
	 */
	public void setItemCodeGroup(int num)
	{
		setGroup(_Prefix + ResultView.ITEMCODE.toString(), num);
	}

	/**
	 * Fetch Item code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItemCodeCollect()
	{
		setCollect(_Prefix + ResultView.ITEMCODE.toString(), "");
	}

	/**
	 * Fetch Item code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItemCodeCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.ITEMCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Item name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Item name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Item name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITEMNAME1.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Item name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITEMNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Item name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItemName1Order(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.ITEMNAME1.toString(), num, bool);
	}

	/**
	 * Set grouping order Item name
	 * @param num grouping order
	 */
	public void setItemName1Group(int num)
	{
		setGroup(_Prefix + ResultView.ITEMNAME1.toString(), num);
	}

	/**
	 * Fetch Item name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItemName1Collect()
	{
		setCollect(_Prefix + ResultView.ITEMNAME1.toString(), "");
	}

	/**
	 * Fetch Item name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItemName1Collect(String compcode)
	{
		setCollect(_Prefix + ResultView.ITEMNAME1.toString(), compcode);
	}

	/**
	 *Set search value for  Host plan qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostPlanQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.HOSTPLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Host plan qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostPlanQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.HOSTPLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Host plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostPlanQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.HOSTPLANQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Host plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostPlanQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.HOSTPLANQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Host plan qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setHostPlanQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.HOSTPLANQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Host plan qty
	 * @param num grouping order
	 */
	public void setHostPlanQtyGroup(int num)
	{
		setGroup(_Prefix + ResultView.HOSTPLANQTY.toString(), num);
	}

	/**
	 * Fetch Host plan qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setHostPlanQtyCollect()
	{
		setCollect(_Prefix + ResultView.HOSTPLANQTY.toString(), "");
	}

	/**
	 * Fetch Host plan qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setHostPlanQtyCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.HOSTPLANQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Work plan qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work plan qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work plan qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.PLANQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Work plan qty
	 * @param num grouping order
	 */
	public void setPlanQtyGroup(int num)
	{
		setGroup(_Prefix + ResultView.PLANQTY.toString(), num);
	}

	/**
	 * Fetch Work plan qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanQtyCollect()
	{
		setCollect(_Prefix + ResultView.PLANQTY.toString(), "");
	}

	/**
	 * Fetch Work plan qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanQtyCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.PLANQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Plan enabled qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanEnableQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANENABLEQTY.toString(), arg);
	}
	/**
	 *Set search value for  Plan enabled qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanEnableQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANENABLEQTY.toString(), arg);
	}
	/**
	 *Set search value for  Plan enabled qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanEnableQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANENABLEQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan enabled qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanEnableQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANENABLEQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan enabled qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanEnableQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.PLANENABLEQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan enabled qty
	 * @param num grouping order
	 */
	public void setPlanEnableQtyGroup(int num)
	{
		setGroup(_Prefix + ResultView.PLANENABLEQTY.toString(), num);
	}

	/**
	 * Fetch Plan enabled qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanEnableQtyCollect()
	{
		setCollect(_Prefix + ResultView.PLANENABLEQTY.toString(), "");
	}

	/**
	 * Fetch Plan enabled qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanEnableQtyCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.PLANENABLEQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Result qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Result qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Result qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setResultQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.RESULTQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Result qty
	 * @param num grouping order
	 */
	public void setResultQtyGroup(int num)
	{
		setGroup(_Prefix + ResultView.RESULTQTY.toString(), num);
	}

	/**
	 * Fetch Result qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setResultQtyCollect()
	{
		setCollect(_Prefix + ResultView.RESULTQTY.toString(), "");
	}

	/**
	 * Fetch Result qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setResultQtyCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.RESULTQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Shortage qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHORTAGECNT.toString(), arg);
	}
	/**
	 *Set search value for  Shortage qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHORTAGECNT.toString(), arg);
	}
	/**
	 *Set search value for  Shortage qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHORTAGECNT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shortage qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SHORTAGECNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shortage qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShortageCntOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.SHORTAGECNT.toString(), num, bool);
	}

	/**
	 * Set grouping order Shortage qty
	 * @param num grouping order
	 */
	public void setShortageCntGroup(int num)
	{
		setGroup(_Prefix + ResultView.SHORTAGECNT.toString(), num);
	}

	/**
	 * Fetch Shortage qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShortageCntCollect()
	{
		setCollect(_Prefix + ResultView.SHORTAGECNT.toString(), "");
	}

	/**
	 * Fetch Shortage qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShortageCntCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.SHORTAGECNT.toString(), compcode);
	}

	/**
	 *Set search value for  Pending qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPendingQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PENDINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Pending qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPendingQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PENDINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Pending qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPendingQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PENDINGQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Pending qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPendingQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PENDINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Pending qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPendingQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.PENDINGQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Pending qty
	 * @param num grouping order
	 */
	public void setPendingQtyGroup(int num)
	{
		setGroup(_Prefix + ResultView.PENDINGQTY.toString(), num);
	}

	/**
	 * Fetch Pending qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPendingQtyCollect()
	{
		setCollect(_Prefix + ResultView.PENDINGQTY.toString(), "");
	}

	/**
	 * Fetch Pending qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPendingQtyCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.PENDINGQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Entering Case qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Entering Case qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Entering Case qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ENTERINGQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Entering Case qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Entering Case qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setEnteringQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.ENTERINGQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Entering Case qty
	 * @param num grouping order
	 */
	public void setEnteringQtyGroup(int num)
	{
		setGroup(_Prefix + ResultView.ENTERINGQTY.toString(), num);
	}

	/**
	 * Fetch Entering Case qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setEnteringQtyCollect()
	{
		setCollect(_Prefix + ResultView.ENTERINGQTY.toString(), "");
	}

	/**
	 * Fetch Entering Case qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setEnteringQtyCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.ENTERINGQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BUNDLEENTERINGQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BUNDLEENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Bundle qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBundleEnteringQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.BUNDLEENTERINGQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Bundle qty
	 * @param num grouping order
	 */
	public void setBundleEnteringQtyGroup(int num)
	{
		setGroup(_Prefix + ResultView.BUNDLEENTERINGQTY.toString(), num);
	}

	/**
	 * Fetch Bundle qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBundleEnteringQtyCollect()
	{
		setCollect(_Prefix + ResultView.BUNDLEENTERINGQTY.toString(), "");
	}

	/**
	 * Fetch Bundle qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBundleEnteringQtyCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.BUNDLEENTERINGQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CASEPIECEFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.CASEPIECEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case/Piece flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCasePieceFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.CASEPIECEFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Case/Piece flag
	 * @param num grouping order
	 */
	public void setCasePieceFlagGroup(int num)
	{
		setGroup(_Prefix + ResultView.CASEPIECEFLAG.toString(), num);
	}

	/**
	 * Fetch Case/Piece flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCasePieceFlagCollect()
	{
		setCollect(_Prefix + ResultView.CASEPIECEFLAG.toString(), "");
	}

	/**
	 * Fetch Case/Piece flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCasePieceFlagCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.CASEPIECEFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Case/Piece flag (Work form flag)
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkFormFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKFORMFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag (Work form flag)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkFormFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKFORMFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag (Work form flag)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkFormFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKFORMFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Case/Piece flag (Work form flag)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkFormFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKFORMFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case/Piece flag (Work form flag)
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkFormFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.WORKFORMFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Case/Piece flag (Work form flag)
	 * @param num grouping order
	 */
	public void setWorkFormFlagGroup(int num)
	{
		setGroup(_Prefix + ResultView.WORKFORMFLAG.toString(), num);
	}

	/**
	 * Fetch Case/Piece flag (Work form flag) info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkFormFlagCollect()
	{
		setCollect(_Prefix + ResultView.WORKFORMFLAG.toString(), "");
	}

	/**
	 * Fetch Case/Piece flag (Work form flag) info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkFormFlagCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.WORKFORMFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITF.toString(), arg);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITF.toString(), arg);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITF.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Case ITF
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setItfOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.ITF.toString(), num, bool);
	}

	/**
	 * Set grouping order Case ITF
	 * @param num grouping order
	 */
	public void setItfGroup(int num)
	{
		setGroup(_Prefix + ResultView.ITF.toString(), num);
	}

	/**
	 * Fetch Case ITF info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setItfCollect()
	{
		setCollect(_Prefix + ResultView.ITF.toString(), "");
	}

	/**
	 * Fetch Case ITF info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setItfCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.ITF.toString(), compcode);
	}

	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BUNDLEITF.toString(), arg);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BUNDLEITF.toString(), arg);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BUNDLEITF.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BUNDLEITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Bundle ITF
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBundleItfOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.BUNDLEITF.toString(), num, bool);
	}

	/**
	 * Set grouping order Bundle ITF
	 * @param num grouping order
	 */
	public void setBundleItfGroup(int num)
	{
		setGroup(_Prefix + ResultView.BUNDLEITF.toString(), num);
	}

	/**
	 * Fetch Bundle ITF info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBundleItfCollect()
	{
		setCollect(_Prefix + ResultView.BUNDLEITF.toString(), "");
	}

	/**
	 * Fetch Bundle ITF info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBundleItfCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.BUNDLEITF.toString(), compcode);
	}

	/**
	 *Set search value for  TC/DC flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.TCDCFLAG.toString(), arg);
	}
	/**
	 *Set search value for  TC/DC flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.TCDCFLAG.toString(), arg);
	}
	/**
	 *Set search value for  TC/DC flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.TCDCFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  TC/DC flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.TCDCFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for TC/DC flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setTcdcFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.TCDCFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order TC/DC flag
	 * @param num grouping order
	 */
	public void setTcdcFlagGroup(int num)
	{
		setGroup(_Prefix + ResultView.TCDCFLAG.toString(), num);
	}

	/**
	 * Fetch TC/DC flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setTcdcFlagCollect()
	{
		setCollect(_Prefix + ResultView.TCDCFLAG.toString(), "");
	}

	/**
	 * Fetch TC/DC flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setTcdcFlagCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.TCDCFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Use By Date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.USEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Use By Date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.USEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Use By Date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.USEBYDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Use By Date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.USEBYDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Use By Date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setUseByDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.USEBYDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Use By Date
	 * @param num grouping order
	 */
	public void setUseByDateGroup(int num)
	{
		setGroup(_Prefix + ResultView.USEBYDATE.toString(), num);
	}

	/**
	 * Fetch Use By Date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setUseByDateCollect()
	{
		setCollect(_Prefix + ResultView.USEBYDATE.toString(), "");
	}

	/**
	 * Fetch Use By Date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setUseByDateCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.USEBYDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Lot no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Lot no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Lot no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LOTNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Lot no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LOTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Lot no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLotNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.LOTNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Lot no
	 * @param num grouping order
	 */
	public void setLotNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.LOTNO.toString(), num);
	}

	/**
	 * Fetch Lot no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLotNoCollect()
	{
		setCollect(_Prefix + ResultView.LOTNO.toString(), "");
	}

	/**
	 * Fetch Lot no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLotNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.LOTNO.toString(), compcode);
	}

	/**
	 *Set search value for  Plan detail comments
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANINFORMATION.toString(), arg);
	}
	/**
	 *Set search value for  Plan detail comments
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANINFORMATION.toString(), arg);
	}
	/**
	 *Set search value for  Plan detail comments
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANINFORMATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan detail comments
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANINFORMATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan detail comments
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanInformationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.PLANINFORMATION.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan detail comments
	 * @param num grouping order
	 */
	public void setPlanInformationGroup(int num)
	{
		setGroup(_Prefix + ResultView.PLANINFORMATION.toString(), num);
	}

	/**
	 * Fetch Plan detail comments info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanInformationCollect()
	{
		setCollect(_Prefix + ResultView.PLANINFORMATION.toString(), "");
	}

	/**
	 * Fetch Plan detail comments info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanInformationCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.PLANINFORMATION.toString(), compcode);
	}

	/**
	 *Set search value for  Order no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERNO.toString(), arg);
	}
	/**
	 *Set search value for  Order no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERNO.toString(), arg);
	}
	/**
	 *Set search value for  Order no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Order no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Order no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setOrderNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.ORDERNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Order no
	 * @param num grouping order
	 */
	public void setOrderNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.ORDERNO.toString(), num);
	}

	/**
	 * Fetch Order no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setOrderNoCollect()
	{
		setCollect(_Prefix + ResultView.ORDERNO.toString(), "");
	}

	/**
	 * Fetch Order no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setOrderNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.ORDERNO.toString(), compcode);
	}

	/**
	 *Set search value for  Order sequence no
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderSeqNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERSEQNO.toString(), arg);
	}
	/**
	 *Set search value for  Order sequence no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderSeqNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERSEQNO.toString(), arg);
	}
	/**
	 *Set search value for  Order sequence no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderSeqNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERSEQNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Order sequence no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderSeqNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERSEQNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Order sequence no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setOrderSeqNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.ORDERSEQNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Order sequence no
	 * @param num grouping order
	 */
	public void setOrderSeqNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.ORDERSEQNO.toString(), num);
	}

	/**
	 * Fetch Order sequence no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setOrderSeqNoCollect()
	{
		setCollect(_Prefix + ResultView.ORDERSEQNO.toString(), "");
	}

	/**
	 * Fetch Order sequence no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setOrderSeqNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.ORDERSEQNO.toString(), compcode);
	}

	/**
	 *Set search value for  Ordering date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERINGDATE.toString(), arg);
	}
	/**
	 *Set search value for  Ordering date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERINGDATE.toString(), arg);
	}
	/**
	 *Set search value for  Ordering date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERINGDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Ordering date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.ORDERINGDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Ordering date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setOrderingDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.ORDERINGDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Ordering date
	 * @param num grouping order
	 */
	public void setOrderingDateGroup(int num)
	{
		setGroup(_Prefix + ResultView.ORDERINGDATE.toString(), num);
	}

	/**
	 * Fetch Ordering date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setOrderingDateCollect()
	{
		setCollect(_Prefix + ResultView.ORDERINGDATE.toString(), "");
	}

	/**
	 * Fetch Ordering date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setOrderingDateCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.ORDERINGDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Result use by date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultUseByDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTUSEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Result use by date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultUseByDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTUSEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Result use by date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultUseByDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTUSEBYDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Result use by date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultUseByDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTUSEBYDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Result use by date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setResultUseByDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.RESULTUSEBYDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Result use by date
	 * @param num grouping order
	 */
	public void setResultUseByDateGroup(int num)
	{
		setGroup(_Prefix + ResultView.RESULTUSEBYDATE.toString(), num);
	}

	/**
	 * Fetch Result use by date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setResultUseByDateCollect()
	{
		setCollect(_Prefix + ResultView.RESULTUSEBYDATE.toString(), "");
	}

	/**
	 * Fetch Result use by date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setResultUseByDateCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.RESULTUSEBYDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Result lot no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLotNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTLOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Result lot no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLotNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTLOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Result lot no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLotNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTLOTNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Result lot no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLotNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTLOTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Result lot no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setResultLotNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.RESULTLOTNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Result lot no
	 * @param num grouping order
	 */
	public void setResultLotNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.RESULTLOTNO.toString(), num);
	}

	/**
	 * Fetch Result lot no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setResultLotNoCollect()
	{
		setCollect(_Prefix + ResultView.RESULTLOTNO.toString(), "");
	}

	/**
	 * Fetch Result lot no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setResultLotNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.RESULTLOTNO.toString(), compcode);
	}

	/**
	 *Set search value for  Result location no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLocationNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTLOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Result location no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLocationNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTLOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Result location no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLocationNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTLOCATIONNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Result location no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLocationNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.RESULTLOCATIONNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Result location no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setResultLocationNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.RESULTLOCATIONNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Result location no
	 * @param num grouping order
	 */
	public void setResultLocationNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.RESULTLOCATIONNO.toString(), num);
	}

	/**
	 * Fetch Result location no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setResultLocationNoCollect()
	{
		setCollect(_Prefix + ResultView.RESULTLOCATIONNO.toString(), "");
	}

	/**
	 * Fetch Result location no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setResultLocationNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.RESULTLOCATIONNO.toString(), compcode);
	}

	/**
	 *Set search value for  Report flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.REPORTFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Report flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.REPORTFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Report flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.REPORTFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Report flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.REPORTFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Report flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setReportFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.REPORTFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Report flag
	 * @param num grouping order
	 */
	public void setReportFlagGroup(int num)
	{
		setGroup(_Prefix + ResultView.REPORTFLAG.toString(), num);
	}

	/**
	 * Fetch Report flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setReportFlagCollect()
	{
		setCollect(_Prefix + ResultView.REPORTFLAG.toString(), "");
	}

	/**
	 * Fetch Report flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setReportFlagCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.REPORTFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Batch no (Schedule no)
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Batch no (Schedule no)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Batch no (Schedule no)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BATCHNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Batch no (Schedule no)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.BATCHNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Batch no (Schedule no)
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setBatchNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.BATCHNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Batch no (Schedule no)
	 * @param num grouping order
	 */
	public void setBatchNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.BATCHNO.toString(), num);
	}

	/**
	 * Fetch Batch no (Schedule no) info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setBatchNoCollect()
	{
		setCollect(_Prefix + ResultView.BATCHNO.toString(), "");
	}

	/**
	 * Fetch Batch no (Schedule no) info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setBatchNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.BATCHNO.toString(), compcode);
	}

	/**
	 *Set search value for  System identification key
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemDiscKey(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SYSTEMDISCKEY.toString(), arg);
	}
	/**
	 *Set search value for  System identification key
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemDiscKey(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SYSTEMDISCKEY.toString(), arg);
	}
	/**
	 *Set search value for  System identification key
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemDiscKey(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SYSTEMDISCKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  System identification key
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemDiscKey(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.SYSTEMDISCKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for System identification key
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSystemDiscKeyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.SYSTEMDISCKEY.toString(), num, bool);
	}

	/**
	 * Set grouping order System identification key
	 * @param num grouping order
	 */
	public void setSystemDiscKeyGroup(int num)
	{
		setGroup(_Prefix + ResultView.SYSTEMDISCKEY.toString(), num);
	}

	/**
	 * Fetch System identification key info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSystemDiscKeyCollect()
	{
		setCollect(_Prefix + ResultView.SYSTEMDISCKEY.toString(), "");
	}

	/**
	 * Fetch System identification key info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSystemDiscKeyCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.SYSTEMDISCKEY.toString(), compcode);
	}

	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Worker code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.WORKERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker code
	 * @param num grouping order
	 */
	public void setWorkerCodeGroup(int num)
	{
		setGroup(_Prefix + ResultView.WORKERCODE.toString(), num);
	}

	/**
	 * Fetch Worker code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerCodeCollect()
	{
		setCollect(_Prefix + ResultView.WORKERCODE.toString(), "");
	}

	/**
	 * Fetch Worker code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerCodeCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.WORKERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKERNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.WORKERNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Worker name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.WORKERNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker name
	 * @param num grouping order
	 */
	public void setWorkerNameGroup(int num)
	{
		setGroup(_Prefix + ResultView.WORKERNAME.toString(), num);
	}

	/**
	 * Fetch Worker name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerNameCollect()
	{
		setCollect(_Prefix + ResultView.WORKERNAME.toString(), "");
	}

	/**
	 * Fetch Worker name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerNameCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.WORKERNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Terminal no
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.TERMINALNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Terminal no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.TERMINALNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Terminal no
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setTerminalNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.TERMINALNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Terminal no
	 * @param num grouping order
	 */
	public void setTerminalNoGroup(int num)
	{
		setGroup(_Prefix + ResultView.TERMINALNO.toString(), num);
	}

	/**
	 * Fetch Terminal no info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setTerminalNoCollect()
	{
		setCollect(_Prefix + ResultView.TERMINALNO.toString(), "");
	}

	/**
	 * Fetch Terminal no info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setTerminalNoCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.TERMINALNO.toString(), compcode);
	}

	/**
	 *Set search value for  Plan registration date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANREGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan registration date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANREGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan registration date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANREGISTDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan registration date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanRegistDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.PLANREGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan registration date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanRegistDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.PLANREGISTDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan registration date
	 * @param num grouping order
	 */
	public void setPlanRegistDateGroup(int num)
	{
		setGroup(_Prefix + ResultView.PLANREGISTDATE.toString(), num);
	}

	/**
	 * Fetch Plan registration date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanRegistDateCollect()
	{
		setCollect(_Prefix + ResultView.PLANREGISTDATE.toString(), "");
	}

	/**
	 * Fetch Plan registration date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanRegistDateCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.PLANREGISTDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + ResultView.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.REGISTDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered date
	 * @param num grouping order
	 */
	public void setRegistDateGroup(int num)
	{
		setGroup(_Prefix + ResultView.REGISTDATE.toString(), num);
	}

	/**
	 * Fetch Registered date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistDateCollect()
	{
		setCollect(_Prefix + ResultView.REGISTDATE.toString(), "");
	}

	/**
	 * Fetch Registered date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistDateCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.REGISTDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + ResultView.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistPnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.REGISTPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered name
	 * @param num grouping order
	 */
	public void setRegistPnameGroup(int num)
	{
		setGroup(_Prefix + ResultView.REGISTPNAME.toString(), num);
	}

	/**
	 * Fetch Registered name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistPnameCollect()
	{
		setCollect(_Prefix + ResultView.REGISTPNAME.toString(), "");
	}

	/**
	 * Fetch Registered name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistPnameCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.REGISTPNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + ResultView.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.LASTUPDATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update date
	 * @param num grouping order
	 */
	public void setLastUpdateDateGroup(int num)
	{
		setGroup(_Prefix + ResultView.LASTUPDATEDATE.toString(), num);
	}

	/**
	 * Fetch Last update date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdateDateCollect()
	{
		setCollect(_Prefix + ResultView.LASTUPDATEDATE.toString(), "");
	}

	/**
	 * Fetch Last update date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdateDateCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.LASTUPDATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ResultView.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + ResultView.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdatePnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + ResultView.LASTUPDATEPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update name
	 * @param num grouping order
	 */
	public void setLastUpdatePnameGroup(int num)
	{
		setGroup(_Prefix + ResultView.LASTUPDATEPNAME.toString(), num);
	}

	/**
	 * Fetch Last update name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdatePnameCollect()
	{
		setCollect(_Prefix + ResultView.LASTUPDATEPNAME.toString(), "");
	}

	/**
	 * Fetch Last update name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdatePnameCollect(String compcode)
	{
		setCollect(_Prefix + ResultView.LASTUPDATEPNAME.toString(), compcode);
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
		return "$Id: ResultViewSearchKey.java,v 1.2 2006/11/09 07:51:34 suresh Exp $" ;
	}
}
