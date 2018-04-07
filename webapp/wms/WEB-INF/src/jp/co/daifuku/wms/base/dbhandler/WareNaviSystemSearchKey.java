//$Id: WareNaviSystemSearchKey.java,v 1.3 2006/11/13 04:32:55 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLSearchKey;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

/**
 * This is the search key class for WARENAVI_SYSTEM use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:32:55 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WareNaviSystemSearchKey
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
	public WareNaviSystemSearchKey()
	{
		super(new WareNaviSystem()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  System No
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SYSTEMNO.toString(), arg);
	}
	/**
	 *Set search value for  System No
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SYSTEMNO.toString(), arg);
	}
	/**
	 *Set search value for  System No
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SYSTEMNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  System No
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SYSTEMNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for System No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSystemNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.SYSTEMNO.toString(), num, bool);
	}

	/**
	 * Set grouping order System No
	 * @param num grouping order
	 */
	public void setSystemNoGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.SYSTEMNO.toString(), num);
	}

	/**
	 * Fetch System No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSystemNoCollect()
	{
		setCollect(_Prefix + WareNaviSystem.SYSTEMNO.toString(), "");
	}

	/**
	 * Fetch System No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSystemNoCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.SYSTEMNO.toString(), compcode);
	}

	/**
	 *Set search value for  Work date
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.WORKDATE.toString(), arg);
	}
	/**
	 *Set search value for  Work date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.WORKDATE.toString(), arg);
	}
	/**
	 *Set search value for  Work date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.WORKDATE.toString(), arg, compcode);
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
		setValue(_Prefix + WareNaviSystem.WORKDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.WORKDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Work date
	 * @param num grouping order
	 */
	public void setWorkDateGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.WORKDATE.toString(), num);
	}

	/**
	 * Fetch Work date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkDateCollect()
	{
		setCollect(_Prefix + WareNaviSystem.WORKDATE.toString(), "");
	}

	/**
	 * Fetch Work date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkDateCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.WORKDATE.toString(), compcode);
	}

	/**
	 *Set search value for  DC Mode
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDcOperation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.DCOPERATION.toString(), arg);
	}
	/**
	 *Set search value for  DC Mode
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDcOperation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.DCOPERATION.toString(), arg);
	}
	/**
	 *Set search value for  DC Mode
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDcOperation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.DCOPERATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  DC Mode
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDcOperation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.DCOPERATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for DC Mode
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setDcOperationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.DCOPERATION.toString(), num, bool);
	}

	/**
	 * Set grouping order DC Mode
	 * @param num grouping order
	 */
	public void setDcOperationGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.DCOPERATION.toString(), num);
	}

	/**
	 * Fetch DC Mode info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setDcOperationCollect()
	{
		setCollect(_Prefix + WareNaviSystem.DCOPERATION.toString(), "");
	}

	/**
	 * Fetch DC Mode info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setDcOperationCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.DCOPERATION.toString(), compcode);
	}

	/**
	 *Set search value for  TC Mode
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcOperation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.TCOPERATION.toString(), arg);
	}
	/**
	 *Set search value for  TC Mode
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcOperation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.TCOPERATION.toString(), arg);
	}
	/**
	 *Set search value for  TC Mode
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcOperation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.TCOPERATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  TC Mode
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcOperation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.TCOPERATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for TC Mode
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setTcOperationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.TCOPERATION.toString(), num, bool);
	}

	/**
	 * Set grouping order TC Mode
	 * @param num grouping order
	 */
	public void setTcOperationGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.TCOPERATION.toString(), num);
	}

	/**
	 * Fetch TC Mode info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setTcOperationCollect()
	{
		setCollect(_Prefix + WareNaviSystem.TCOPERATION.toString(), "");
	}

	/**
	 * Fetch TC Mode info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setTcOperationCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.TCOPERATION.toString(), compcode);
	}

	/**
	 *Set search value for  CrossDC enabled
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCrossdockOperation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.CROSSDOCKOPERATION.toString(), arg);
	}
	/**
	 *Set search value for  CrossDC enabled
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCrossdockOperation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.CROSSDOCKOPERATION.toString(), arg);
	}
	/**
	 *Set search value for  CrossDC enabled
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCrossdockOperation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.CROSSDOCKOPERATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  CrossDC enabled
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCrossdockOperation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.CROSSDOCKOPERATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for CrossDC enabled
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCrossdockOperationOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.CROSSDOCKOPERATION.toString(), num, bool);
	}

	/**
	 * Set grouping order CrossDC enabled
	 * @param num grouping order
	 */
	public void setCrossdockOperationGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.CROSSDOCKOPERATION.toString(), num);
	}

	/**
	 * Fetch CrossDC enabled info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCrossdockOperationCollect()
	{
		setCollect(_Prefix + WareNaviSystem.CROSSDOCKOPERATION.toString(), "");
	}

	/**
	 * Fetch CrossDC enabled info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCrossdockOperationCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.CROSSDOCKOPERATION.toString(), compcode);
	}

	/**
	 *Set search value for  Result hold period
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultHoldPeriod(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.RESULTHOLDPERIOD.toString(), arg);
	}
	/**
	 *Set search value for  Result hold period
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultHoldPeriod(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.RESULTHOLDPERIOD.toString(), arg);
	}
	/**
	 *Set search value for  Result hold period
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultHoldPeriod(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.RESULTHOLDPERIOD.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Result hold period
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultHoldPeriod(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.RESULTHOLDPERIOD.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Result hold period
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setResultHoldPeriodOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.RESULTHOLDPERIOD.toString(), num, bool);
	}

	/**
	 * Set grouping order Result hold period
	 * @param num grouping order
	 */
	public void setResultHoldPeriodGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.RESULTHOLDPERIOD.toString(), num);
	}

	/**
	 * Fetch Result hold period info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setResultHoldPeriodCollect()
	{
		setCollect(_Prefix + WareNaviSystem.RESULTHOLDPERIOD.toString(), "");
	}

	/**
	 * Fetch Result hold period info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setResultHoldPeriodCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.RESULTHOLDPERIOD.toString(), compcode);
	}

	/**
	 *Set search value for  Plan hold period
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanHoldPeriod(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.PLANHOLDPERIOD.toString(), arg);
	}
	/**
	 *Set search value for  Plan hold period
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanHoldPeriod(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.PLANHOLDPERIOD.toString(), arg);
	}
	/**
	 *Set search value for  Plan hold period
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanHoldPeriod(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.PLANHOLDPERIOD.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan hold period
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanHoldPeriod(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.PLANHOLDPERIOD.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Plan hold period
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setPlanHoldPeriodOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.PLANHOLDPERIOD.toString(), num, bool);
	}

	/**
	 * Set grouping order Plan hold period
	 * @param num grouping order
	 */
	public void setPlanHoldPeriodGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.PLANHOLDPERIOD.toString(), num);
	}

	/**
	 * Fetch Plan hold period info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setPlanHoldPeriodCollect()
	{
		setCollect(_Prefix + WareNaviSystem.PLANHOLDPERIOD.toString(), "");
	}

	/**
	 * Fetch Plan hold period info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setPlanHoldPeriodCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.PLANHOLDPERIOD.toString(), compcode);
	}

	/**
	 *Set search value for  Receiving package
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.INSTOCKPACK.toString(), arg);
	}
	/**
	 *Set search value for  Receiving package
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockPack(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.INSTOCKPACK.toString(), arg);
	}
	/**
	 *Set search value for  Receiving package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockPack(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.INSTOCKPACK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Receiving package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockPack(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.INSTOCKPACK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Receiving package
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setInstockPackOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.INSTOCKPACK.toString(), num, bool);
	}

	/**
	 * Set grouping order Receiving package
	 * @param num grouping order
	 */
	public void setInstockPackGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.INSTOCKPACK.toString(), num);
	}

	/**
	 * Fetch Receiving package info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setInstockPackCollect()
	{
		setCollect(_Prefix + WareNaviSystem.INSTOCKPACK.toString(), "");
	}

	/**
	 * Fetch Receiving package info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setInstockPackCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.INSTOCKPACK.toString(), compcode);
	}

	/**
	 *Set search value for  Storage package
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoragePack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.STORAGEPACK.toString(), arg);
	}
	/**
	 *Set search value for  Storage package
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoragePack(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.STORAGEPACK.toString(), arg);
	}
	/**
	 *Set search value for  Storage package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoragePack(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.STORAGEPACK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Storage package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoragePack(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.STORAGEPACK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Storage package
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStoragePackOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.STORAGEPACK.toString(), num, bool);
	}

	/**
	 * Set grouping order Storage package
	 * @param num grouping order
	 */
	public void setStoragePackGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.STORAGEPACK.toString(), num);
	}

	/**
	 * Fetch Storage package info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStoragePackCollect()
	{
		setCollect(_Prefix + WareNaviSystem.STORAGEPACK.toString(), "");
	}

	/**
	 * Fetch Storage package info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStoragePackCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.STORAGEPACK.toString(), compcode);
	}

	/**
	 *Set search value for  Retrieval package
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.RETRIEVALPACK.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval package
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPack(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.RETRIEVALPACK.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPack(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.RETRIEVALPACK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Retrieval package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPack(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.RETRIEVALPACK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Retrieval package
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRetrievalPackOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.RETRIEVALPACK.toString(), num, bool);
	}

	/**
	 * Set grouping order Retrieval package
	 * @param num grouping order
	 */
	public void setRetrievalPackGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.RETRIEVALPACK.toString(), num);
	}

	/**
	 * Fetch Retrieval package info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRetrievalPackCollect()
	{
		setCollect(_Prefix + WareNaviSystem.RETRIEVALPACK.toString(), "");
	}

	/**
	 * Fetch Retrieval package info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRetrievalPackCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.RETRIEVALPACK.toString(), compcode);
	}

	/**
	 *Set search value for  Sorting package
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SORTINGPACK.toString(), arg);
	}
	/**
	 *Set search value for  Sorting package
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPack(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SORTINGPACK.toString(), arg);
	}
	/**
	 *Set search value for  Sorting package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPack(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SORTINGPACK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Sorting package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPack(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SORTINGPACK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Sorting package
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setSortingPackOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.SORTINGPACK.toString(), num, bool);
	}

	/**
	 * Set grouping order Sorting package
	 * @param num grouping order
	 */
	public void setSortingPackGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.SORTINGPACK.toString(), num);
	}

	/**
	 * Fetch Sorting package info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setSortingPackCollect()
	{
		setCollect(_Prefix + WareNaviSystem.SORTINGPACK.toString(), "");
	}

	/**
	 * Fetch Sorting package info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setSortingPackCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.SORTINGPACK.toString(), compcode);
	}

	/**
	 *Set search value for  Shipping package
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SHIPPINGPACK.toString(), arg);
	}
	/**
	 *Set search value for  Shipping package
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPack(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SHIPPINGPACK.toString(), arg);
	}
	/**
	 *Set search value for  Shipping package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPack(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SHIPPINGPACK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Shipping package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPack(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SHIPPINGPACK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Shipping package
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setShippingPackOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.SHIPPINGPACK.toString(), num, bool);
	}

	/**
	 * Set grouping order Shipping package
	 * @param num grouping order
	 */
	public void setShippingPackGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.SHIPPINGPACK.toString(), num);
	}

	/**
	 * Fetch Shipping package info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setShippingPackCollect()
	{
		setCollect(_Prefix + WareNaviSystem.SHIPPINGPACK.toString(), "");
	}

	/**
	 * Fetch Shipping package info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setShippingPackCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.SHIPPINGPACK.toString(), compcode);
	}

	/**
	 *Set search value for  Stock pack
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.STOCKPACK.toString(), arg);
	}
	/**
	 *Set search value for  Stock pack
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockPack(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.STOCKPACK.toString(), arg);
	}
	/**
	 *Set search value for  Stock pack
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockPack(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.STOCKPACK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Stock pack
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockPack(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.STOCKPACK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Stock pack
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStockPackOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.STOCKPACK.toString(), num, bool);
	}

	/**
	 * Set grouping order Stock pack
	 * @param num grouping order
	 */
	public void setStockPackGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.STOCKPACK.toString(), num);
	}

	/**
	 * Fetch Stock pack info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStockPackCollect()
	{
		setCollect(_Prefix + WareNaviSystem.STOCKPACK.toString(), "");
	}

	/**
	 * Fetch Stock pack info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStockPackCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.STOCKPACK.toString(), compcode);
	}

	/**
	 *Set search value for  CrossDC package
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCrossdockPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.CROSSDOCKPACK.toString(), arg);
	}
	/**
	 *Set search value for  CrossDC package
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCrossdockPack(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.CROSSDOCKPACK.toString(), arg);
	}
	/**
	 *Set search value for  CrossDC package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCrossdockPack(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.CROSSDOCKPACK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  CrossDC package
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCrossdockPack(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.CROSSDOCKPACK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for CrossDC package
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setCrossdockPackOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.CROSSDOCKPACK.toString(), num, bool);
	}

	/**
	 * Set grouping order CrossDC package
	 * @param num grouping order
	 */
	public void setCrossdockPackGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.CROSSDOCKPACK.toString(), num);
	}

	/**
	 * Fetch CrossDC package info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setCrossdockPackCollect()
	{
		setCollect(_Prefix + WareNaviSystem.CROSSDOCKPACK.toString(), "");
	}

	/**
	 * Fetch CrossDC package info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setCrossdockPackCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.CROSSDOCKPACK.toString(), compcode);
	}

	/**
	 *Set search value for  IDM Pack
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIdmPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.IDMPACK.toString(), arg);
	}
	/**
	 *Set search value for  IDM Pack
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIdmPack(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.IDMPACK.toString(), arg);
	}
	/**
	 *Set search value for  IDM Pack
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIdmPack(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.IDMPACK.toString(), arg, compcode);
	}
	/**
	 *Set search value for  IDM Pack
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIdmPack(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.IDMPACK.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for IDM Pack
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setIdmPackOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.IDMPACK.toString(), num, bool);
	}

	/**
	 * Set grouping order IDM Pack
	 * @param num grouping order
	 */
	public void setIdmPackGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.IDMPACK.toString(), num);
	}

	/**
	 * Fetch IDM Pack info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setIdmPackCollect()
	{
		setCollect(_Prefix + WareNaviSystem.IDMPACK.toString(), "");
	}

	/**
	 * Fetch IDM Pack info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setIdmPackCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.IDMPACK.toString(), compcode);
	}

	/**
	 *Set search value for  Daily update
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDailyUpdate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.DAILYUPDATE.toString(), arg);
	}
	/**
	 *Set search value for  Daily update
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDailyUpdate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.DAILYUPDATE.toString(), arg);
	}
	/**
	 *Set search value for  Daily update
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDailyUpdate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.DAILYUPDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Daily update
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDailyUpdate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.DAILYUPDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Daily update
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setDailyUpdateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.DAILYUPDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Daily update
	 * @param num grouping order
	 */
	public void setDailyUpdateGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.DAILYUPDATE.toString(), num);
	}

	/**
	 * Fetch Daily update info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setDailyUpdateCollect()
	{
		setCollect(_Prefix + WareNaviSystem.DAILYUPDATE.toString(), "");
	}

	/**
	 * Fetch Daily update info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setDailyUpdateCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.DAILYUPDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Loading data
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLoadData(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.LOADDATA.toString(), arg);
	}
	/**
	 *Set search value for  Loading data
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLoadData(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.LOADDATA.toString(), arg);
	}
	/**
	 *Set search value for  Loading data
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLoadData(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.LOADDATA.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Loading data
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLoadData(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.LOADDATA.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Loading data
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLoadDataOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.LOADDATA.toString(), num, bool);
	}

	/**
	 * Set grouping order Loading data
	 * @param num grouping order
	 */
	public void setLoadDataGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.LOADDATA.toString(), num);
	}

	/**
	 * Fetch Loading data info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLoadDataCollect()
	{
		setCollect(_Prefix + WareNaviSystem.LOADDATA.toString(), "");
	}

	/**
	 * Fetch Loading data info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLoadDataCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.LOADDATA.toString(), compcode);
	}

	/**
	 *Set search value for  Generating report
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportData(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.REPORTDATA.toString(), arg);
	}
	/**
	 *Set search value for  Generating report
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportData(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.REPORTDATA.toString(), arg);
	}
	/**
	 *Set search value for  Generating report
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportData(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.REPORTDATA.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Generating report
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportData(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.REPORTDATA.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Generating report
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setReportDataOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WareNaviSystem.REPORTDATA.toString(), num, bool);
	}

	/**
	 * Set grouping order Generating report
	 * @param num grouping order
	 */
	public void setReportDataGroup(int num)
	{
		setGroup(_Prefix + WareNaviSystem.REPORTDATA.toString(), num);
	}

	/**
	 * Fetch Generating report info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setReportDataCollect()
	{
		setCollect(_Prefix + WareNaviSystem.REPORTDATA.toString(), "");
	}

	/**
	 * Fetch Generating report info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setReportDataCollect(String compcode)
	{
		setCollect(_Prefix + WareNaviSystem.REPORTDATA.toString(), compcode);
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
		return "$Id: WareNaviSystemSearchKey.java,v 1.3 2006/11/13 04:32:55 suresh Exp $" ;
	}
}
