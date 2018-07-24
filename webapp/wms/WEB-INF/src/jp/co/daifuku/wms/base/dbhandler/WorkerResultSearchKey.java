//$Id: WorkerResultSearchKey.java,v 1.3 2006/11/13 04:32:54 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.WorkerResult;

/**
 * This is the search key class for WORKERRESULT use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:32:54 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WorkerResultSearchKey
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
	public WorkerResultSearchKey()
	{
		super(new WorkerResult()) ;
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
		setValue(_Prefix + WorkerResult.WORKDATE.toString(), arg);
	}
	/**
	 *Set search value for  Work date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKDATE.toString(), arg);
	}
	/**
	 *Set search value for  Work date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKDATE.toString(), arg, compcode);
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
		setValue(_Prefix + WorkerResult.WORKDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.WORKDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Work date
	 * @param num grouping order
	 */
	public void setWorkDateGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.WORKDATE.toString(), num);
	}

	/**
	 * Fetch Work date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkDateCollect()
	{
		setCollect(_Prefix + WorkerResult.WORKDATE.toString(), "");
	}

	/**
	 * Fetch Work date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkDateCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.WORKDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Work Start time
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkStartTime(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKSTARTTIME.toString(), arg);
	}
	/**
	 *Set search value for  Work Start time
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkStartTime(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKSTARTTIME.toString(), arg);
	}
	/**
	 *Set search value for  Work Start time
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkStartTime(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKSTARTTIME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work Start time
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkStartTime(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKSTARTTIME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work Start time
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkStartTimeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.WORKSTARTTIME.toString(), num, bool);
	}

	/**
	 * Set grouping order Work Start time
	 * @param num grouping order
	 */
	public void setWorkStartTimeGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.WORKSTARTTIME.toString(), num);
	}

	/**
	 * Fetch Work Start time info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkStartTimeCollect()
	{
		setCollect(_Prefix + WorkerResult.WORKSTARTTIME.toString(), "");
	}

	/**
	 * Fetch Work Start time info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkStartTimeCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.WORKSTARTTIME.toString(), compcode);
	}

	/**
	 *Set search value for  Work End time
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkEndTime(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKENDTIME.toString(), arg);
	}
	/**
	 *Set search value for  Work End time
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkEndTime(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKENDTIME.toString(), arg);
	}
	/**
	 *Set search value for  Work End time
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkEndTime(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKENDTIME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work End time
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkEndTime(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKENDTIME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work End time
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkEndTimeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.WORKENDTIME.toString(), num, bool);
	}

	/**
	 * Set grouping order Work End time
	 * @param num grouping order
	 */
	public void setWorkEndTimeGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.WORKENDTIME.toString(), num);
	}

	/**
	 * Fetch Work End time info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkEndTimeCollect()
	{
		setCollect(_Prefix + WorkerResult.WORKENDTIME.toString(), "");
	}

	/**
	 * Fetch Work End time info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkEndTimeCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.WORKENDTIME.toString(), compcode);
	}

	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + WorkerResult.WORKERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Worker code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.WORKERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker code
	 * @param num grouping order
	 */
	public void setWorkerCodeGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.WORKERCODE.toString(), num);
	}

	/**
	 * Fetch Worker code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerCodeCollect()
	{
		setCollect(_Prefix + WorkerResult.WORKERCODE.toString(), "");
	}

	/**
	 * Fetch Worker code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerCodeCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.WORKERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKERNAME.toString(), arg, compcode);
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
		setValue(_Prefix + WorkerResult.WORKERNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Worker name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerNameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.WORKERNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker name
	 * @param num grouping order
	 */
	public void setWorkerNameGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.WORKERNAME.toString(), num);
	}

	/**
	 * Fetch Worker name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerNameCollect()
	{
		setCollect(_Prefix + WorkerResult.WORKERNAME.toString(), "");
	}

	/**
	 * Fetch Worker name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerNameCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.WORKERNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Terminal No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.TERMINALNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Terminal No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.TERMINALNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Terminal No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setTerminalNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.TERMINALNO.toString(), num, bool);
	}

	/**
	 * Set grouping order Terminal No
	 * @param num grouping order
	 */
	public void setTerminalNoGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.TERMINALNO.toString(), num);
	}

	/**
	 * Fetch Terminal No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setTerminalNoCollect()
	{
		setCollect(_Prefix + WorkerResult.TERMINALNO.toString(), "");
	}

	/**
	 * Fetch Terminal No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setTerminalNoCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.TERMINALNO.toString(), compcode);
	}

	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.JOBTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Work type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.JOBTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.JOBTYPE.toString(), arg, compcode);
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
		setValue(_Prefix + WorkerResult.JOBTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setJobTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.JOBTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order Work type
	 * @param num grouping order
	 */
	public void setJobTypeGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.JOBTYPE.toString(), num);
	}

	/**
	 * Fetch Work type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setJobTypeCollect()
	{
		setCollect(_Prefix + WorkerResult.JOBTYPE.toString(), "");
	}

	/**
	 * Fetch Work type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setJobTypeCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.JOBTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  Work qty
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work qty
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkQtyOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.WORKQTY.toString(), num, bool);
	}

	/**
	 * Set grouping order Work qty
	 * @param num grouping order
	 */
	public void setWorkQtyGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.WORKQTY.toString(), num);
	}

	/**
	 * Fetch Work qty info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkQtyCollect()
	{
		setCollect(_Prefix + WorkerResult.WORKQTY.toString(), "");
	}

	/**
	 * Fetch Work qty info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkQtyCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.WORKQTY.toString(), compcode);
	}

	/**
	 *Set search value for  Work count
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKCNT.toString(), arg);
	}
	/**
	 *Set search value for  Work count
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKCNT.toString(), arg);
	}
	/**
	 *Set search value for  Work count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKCNT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkCnt(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKCNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work count
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkCntOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.WORKCNT.toString(), num, bool);
	}

	/**
	 * Set grouping order Work count
	 * @param num grouping order
	 */
	public void setWorkCntGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.WORKCNT.toString(), num);
	}

	/**
	 * Fetch Work count info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkCntCollect()
	{
		setCollect(_Prefix + WorkerResult.WORKCNT.toString(), "");
	}

	/**
	 * Fetch Work count info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkCntCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.WORKCNT.toString(), compcode);
	}

	/**
	 *Set search value for  Order count
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.ORDERCNT.toString(), arg);
	}
	/**
	 *Set search value for  Order count
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.ORDERCNT.toString(), arg);
	}
	/**
	 *Set search value for  Order count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.ORDERCNT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Order count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderCnt(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.ORDERCNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Order count
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setOrderCntOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.ORDERCNT.toString(), num, bool);
	}

	/**
	 * Set grouping order Order count
	 * @param num grouping order
	 */
	public void setOrderCntGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.ORDERCNT.toString(), num);
	}

	/**
	 * Fetch Order count info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setOrderCntCollect()
	{
		setCollect(_Prefix + WorkerResult.ORDERCNT.toString(), "");
	}

	/**
	 * Fetch Order count info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setOrderCntCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.ORDERCNT.toString(), compcode);
	}

	/**
	 *Set search value for  Work time
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkTime(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKTIME.toString(), arg);
	}
	/**
	 *Set search value for  Work time
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkTime(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKTIME.toString(), arg);
	}
	/**
	 *Set search value for  Work time
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkTime(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKTIME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work time
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkTime(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKTIME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work time
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkTimeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.WORKTIME.toString(), num, bool);
	}

	/**
	 * Set grouping order Work time
	 * @param num grouping order
	 */
	public void setWorkTimeGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.WORKTIME.toString(), num);
	}

	/**
	 * Fetch Work time info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkTimeCollect()
	{
		setCollect(_Prefix + WorkerResult.WORKTIME.toString(), "");
	}

	/**
	 * Fetch Work time info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkTimeCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.WORKTIME.toString(), compcode);
	}

	/**
	 *Set search value for  Rest time
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestTime(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.RESTTIME.toString(), arg);
	}
	/**
	 *Set search value for  Rest time
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestTime(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.RESTTIME.toString(), arg);
	}
	/**
	 *Set search value for  Rest time
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestTime(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.RESTTIME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Rest time
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestTime(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.RESTTIME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Rest time
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRestTimeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.RESTTIME.toString(), num, bool);
	}

	/**
	 * Set grouping order Rest time
	 * @param num grouping order
	 */
	public void setRestTimeGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.RESTTIME.toString(), num);
	}

	/**
	 * Fetch Rest time info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRestTimeCollect()
	{
		setCollect(_Prefix + WorkerResult.RESTTIME.toString(), "");
	}

	/**
	 * Fetch Rest time info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRestTimeCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.RESTTIME.toString(), compcode);
	}

	/**
	 *Set search value for  Actual work time
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRealWorkTime(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.REALWORKTIME.toString(), arg);
	}
	/**
	 *Set search value for  Actual work time
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRealWorkTime(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.REALWORKTIME.toString(), arg);
	}
	/**
	 *Set search value for  Actual work time
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRealWorkTime(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.REALWORKTIME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Actual work time
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRealWorkTime(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.REALWORKTIME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Actual work time
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRealWorkTimeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.REALWORKTIME.toString(), num, bool);
	}

	/**
	 * Set grouping order Actual work time
	 * @param num grouping order
	 */
	public void setRealWorkTimeGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.REALWORKTIME.toString(), num);
	}

	/**
	 * Fetch Actual work time info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRealWorkTimeCollect()
	{
		setCollect(_Prefix + WorkerResult.REALWORKTIME.toString(), "");
	}

	/**
	 * Fetch Actual work time info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRealWorkTimeCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.REALWORKTIME.toString(), compcode);
	}

	/**
	 *Set search value for  Miss count
	 * @param arg Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMissScanCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.MISSSCANCNT.toString(), arg);
	}
	/**
	 *Set search value for  Miss count
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMissScanCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.MISSSCANCNT.toString(), arg);
	}
	/**
	 *Set search value for  Miss count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMissScanCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.MISSSCANCNT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Miss count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMissScanCnt(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.MISSSCANCNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Miss count
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setMissScanCntOrder(int num, boolean bool)
	{
		setOrder(_Prefix + WorkerResult.MISSSCANCNT.toString(), num, bool);
	}

	/**
	 * Set grouping order Miss count
	 * @param num grouping order
	 */
	public void setMissScanCntGroup(int num)
	{
		setGroup(_Prefix + WorkerResult.MISSSCANCNT.toString(), num);
	}

	/**
	 * Fetch Miss count info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setMissScanCntCollect()
	{
		setCollect(_Prefix + WorkerResult.MISSSCANCNT.toString(), "");
	}

	/**
	 * Fetch Miss count info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setMissScanCntCollect(String compcode)
	{
		setCollect(_Prefix + WorkerResult.MISSSCANCNT.toString(), compcode);
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
		return "$Id: WorkerResultSearchKey.java,v 1.3 2006/11/13 04:32:54 suresh Exp $" ;
	}
}
