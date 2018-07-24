//$Id: WorkerResultAlterKey.java,v 1.2 2006/11/09 07:50:19 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.WorkerResult;

/**
 * Update key class for WORKERRESULT use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:50:19 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WorkerResultAlterKey
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
	public WorkerResultAlterKey()
	{
		super(new WorkerResult()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for Work date
	 * @param arg Work date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKDATE.toString(), arg);
	}
	/**
	 * Set search value for Work date
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
	 * Set update value for Work date
	 * @param arg Update value for Work date
	 */
	public void updateWorkDate(String arg)
	{
		setUpdValue(_Prefix + WorkerResult.WORKDATE.toString(), arg);
	}

	/**
	 * Set search value for Work Start time
	 * @param arg Work Start time<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkStartTime(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKSTARTTIME.toString(), arg);
	}
	/**
	 * Set search value for Work Start time
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
	 * Set update value for Work Start time
	 * @param arg Update value for Work Start time
	 */
	public void updateWorkStartTime(Date arg)
	{
		setUpdValue(_Prefix + WorkerResult.WORKSTARTTIME.toString(), arg);
	}

	/**
	 * Set search value for Work End time
	 * @param arg Work End time<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkEndTime(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKENDTIME.toString(), arg);
	}
	/**
	 * Set search value for Work End time
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
	 * Set update value for Work End time
	 * @param arg Update value for Work End time
	 */
	public void updateWorkEndTime(Date arg)
	{
		setUpdValue(_Prefix + WorkerResult.WORKENDTIME.toString(), arg);
	}

	/**
	 * Set search value for Worker code
	 * @param arg Worker code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKERCODE.toString(), arg);
	}
	/**
	 * Set search value for Worker code
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
	 * Set update value for Worker code
	 * @param arg Update value for Worker code
	 */
	public void updateWorkerCode(String arg)
	{
		setUpdValue(_Prefix + WorkerResult.WORKERCODE.toString(), arg);
	}

	/**
	 * Set search value for Worker name
	 * @param arg Worker name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKERNAME.toString(), arg);
	}
	/**
	 * Set search value for Worker name
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
	 * Set update value for Worker name
	 * @param arg Update value for Worker name
	 */
	public void updateWorkerName(String arg)
	{
		setUpdValue(_Prefix + WorkerResult.WORKERNAME.toString(), arg);
	}

	/**
	 * Set search value for Terminal No
	 * @param arg Terminal No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.TERMINALNO.toString(), arg);
	}
	/**
	 * Set search value for Terminal No
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
	 * Set update value for Terminal No
	 * @param arg Update value for Terminal No
	 */
	public void updateTerminalNo(String arg)
	{
		setUpdValue(_Prefix + WorkerResult.TERMINALNO.toString(), arg);
	}

	/**
	 * Set search value for Work type
	 * @param arg Work type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.JOBTYPE.toString(), arg);
	}
	/**
	 * Set search value for Work type
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
	 * Set update value for Work type
	 * @param arg Update value for Work type
	 */
	public void updateJobType(String arg)
	{
		setUpdValue(_Prefix + WorkerResult.JOBTYPE.toString(), arg);
	}

	/**
	 * Set search value for Work qty
	 * @param arg Work qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKQTY.toString(), arg);
	}
	/**
	 * Set search value for Work qty
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
	 * Set update value for Work qty
	 * @param arg Update value for Work qty
	 */
	public void updateWorkQty(int arg)
	{
		setUpdValue(_Prefix + WorkerResult.WORKQTY.toString(), arg);
	}

	/**
	 * Set search value for Work count
	 * @param arg Work count<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKCNT.toString(), arg);
	}
	/**
	 * Set search value for Work count
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
	 * Set update value for Work count
	 * @param arg Update value for Work count
	 */
	public void updateWorkCnt(int arg)
	{
		setUpdValue(_Prefix + WorkerResult.WORKCNT.toString(), arg);
	}

	/**
	 * Set search value for Order count
	 * @param arg Order count<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.ORDERCNT.toString(), arg);
	}
	/**
	 * Set search value for Order count
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
	 * Set update value for Order count
	 * @param arg Update value for Order count
	 */
	public void updateOrderCnt(int arg)
	{
		setUpdValue(_Prefix + WorkerResult.ORDERCNT.toString(), arg);
	}

	/**
	 * Set search value for Work time
	 * @param arg Work time<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkTime(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.WORKTIME.toString(), arg);
	}
	/**
	 * Set search value for Work time
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
	 * Set update value for Work time
	 * @param arg Update value for Work time
	 */
	public void updateWorkTime(int arg)
	{
		setUpdValue(_Prefix + WorkerResult.WORKTIME.toString(), arg);
	}

	/**
	 * Set search value for Rest time
	 * @param arg Rest time<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestTime(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.RESTTIME.toString(), arg);
	}
	/**
	 * Set search value for Rest time
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
	 * Set update value for Rest time
	 * @param arg Update value for Rest time
	 */
	public void updateRestTime(int arg)
	{
		setUpdValue(_Prefix + WorkerResult.RESTTIME.toString(), arg);
	}

	/**
	 * Set search value for Actual work time
	 * @param arg Actual work time<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRealWorkTime(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.REALWORKTIME.toString(), arg);
	}
	/**
	 * Set search value for Actual work time
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
	 * Set update value for Actual work time
	 * @param arg Update value for Actual work time
	 */
	public void updateRealWorkTime(int arg)
	{
		setUpdValue(_Prefix + WorkerResult.REALWORKTIME.toString(), arg);
	}

	/**
	 * Set search value for Miss count
	 * @param arg Miss count<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMissScanCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WorkerResult.MISSSCANCNT.toString(), arg);
	}
	/**
	 * Set search value for Miss count
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
	 * Set update value for Miss count
	 * @param arg Update value for Miss count
	 */
	public void updateMissScanCnt(int arg)
	{
		setUpdValue(_Prefix + WorkerResult.MISSSCANCNT.toString(), arg);
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
		return "$Id: WorkerResultAlterKey.java,v 1.2 2006/11/09 07:50:19 suresh Exp $" ;
	}
}
