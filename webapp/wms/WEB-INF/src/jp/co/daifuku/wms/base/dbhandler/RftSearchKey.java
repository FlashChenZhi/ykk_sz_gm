//$Id: RftSearchKey.java,v 1.3 2006/11/13 04:33:02 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Rft;

/**
 * This is the search key class for RFT use.
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
 * @version $Revision: 1.3 $, $Date: 2006/11/13 04:33:02 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class RftSearchKey
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
	public RftSearchKey()
	{
		super(new Rft()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 *Set search value for  RFT No
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRftNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RFTNO.toString(), arg);
	}
	/**
	 *Set search value for  RFT No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRftNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RFTNO.toString(), arg);
	}
	/**
	 *Set search value for  RFT No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRftNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RFTNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  RFT No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRftNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RFTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for RFT No
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRftNoOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.RFTNO.toString(), num, bool);
	}

	/**
	 * Set grouping order RFT No
	 * @param num grouping order
	 */
	public void setRftNoGroup(int num) 
	{
		setGroup(_Prefix + Rft.RFTNO.toString(), num);
	}

	/**
	 * Fetch RFT No info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRftNoCollect()
	{
		setCollect(_Prefix + Rft.RFTNO.toString(), "");
	}

	/**
	 * Fetch RFT No info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRftNoCollect(String compcode)
	{
		setCollect(_Prefix + Rft.RFTNO.toString(), compcode);
	}

	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.WORKERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + Rft.WORKERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Worker code
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setWorkerCodeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.WORKERCODE.toString(), num, bool);
	}

	/**
	 * Set grouping order Worker code
	 * @param num grouping order
	 */
	public void setWorkerCodeGroup(int num)
	{
		setGroup(_Prefix + Rft.WORKERCODE.toString(), num);
	}

	/**
	 * Fetch Worker code info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setWorkerCodeCollect()
	{
		setCollect(_Prefix + Rft.WORKERCODE.toString(), "");
	}

	/**
	 * Fetch Worker code info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setWorkerCodeCollect(String compcode)
	{
		setCollect(_Prefix + Rft.WORKERCODE.toString(), compcode);
	}

	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.JOBTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Work type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.JOBTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.JOBTYPE.toString(), arg, compcode);
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
		setValue(_Prefix + Rft.JOBTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Work type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setJobTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.JOBTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order Work type
	 * @param num grouping order
	 */
	public void setJobTypeGroup(int num) 
	{
		setGroup(_Prefix + Rft.JOBTYPE.toString(), num);
	}

	/**
	 * Fetch Work type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setJobTypeCollect()
	{
		setCollect(_Prefix + Rft.JOBTYPE.toString(), "");
	}

	/**
	 * Fetch Work type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setJobTypeCollect(String compcode)
	{
		setCollect(_Prefix + Rft.JOBTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.STATUSFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + Rft.STATUSFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Status flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setStatusFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.STATUSFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Status flag
	 * @param num grouping order
	 */
	public void setStatusFlagGroup(int num) 
	{
		setGroup(_Prefix + Rft.STATUSFLAG.toString(), num);
	}

	/**
	 * Fetch Status flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setStatusFlagCollect()
	{
		setCollect(_Prefix + Rft.STATUSFLAG.toString(), "");
	}

	/**
	 * Fetch Status flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setStatusFlagCollect(String compcode)
	{
		setCollect(_Prefix + Rft.STATUSFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Rest flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESTFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Rest flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESTFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Rest flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESTFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Rest flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESTFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Rest flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRestFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.RESTFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Rest flag
	 * @param num grouping order
	 */
	public void setRestFlagGroup(int num)
	{
		setGroup(_Prefix + Rft.RESTFLAG.toString(), num);
	}

	/**
	 * Fetch Rest flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRestFlagCollect()
	{
		setCollect(_Prefix + Rft.RESTFLAG.toString(), "");
	}

	/**
	 * Fetch Rest flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRestFlagCollect(String compcode)
	{
		setCollect(_Prefix + Rft.RESTFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Terminal type
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.TERMINALTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Terminal type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.TERMINALTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Terminal type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.TERMINALTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Terminal type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalType(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Rft.TERMINALTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Terminal type
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setTerminalTypeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.TERMINALTYPE.toString(), num, bool);
	}

	/**
	 * Set grouping order Terminal type
	 * @param num grouping order
	 */
	public void setTerminalTypeGroup(int num) 
	{
		setGroup(_Prefix + Rft.TERMINALTYPE.toString(), num);
	}

	/**
	 * Fetch Terminal type info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setTerminalTypeCollect()
	{
		setCollect(_Prefix + Rft.TERMINALTYPE.toString(), "");
	}

	/**
	 * Fetch Terminal type info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setTerminalTypeCollect(String compcode)
	{
		setCollect(_Prefix + Rft.TERMINALTYPE.toString(), compcode);
	}

	/**
	 *Set search value for  IP Address
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIpAddress(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.IPADDRESS.toString(), arg);
	}
	/**
	 *Set search value for  IP Address
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIpAddress(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.IPADDRESS.toString(), arg);
	}
	/**
	 *Set search value for  IP Address
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIpAddress(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.IPADDRESS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  IP Address
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIpAddress(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Rft.IPADDRESS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for IP Address
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setIpAddressOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.IPADDRESS.toString(), num, bool);
	}

	/**
	 * Set grouping order IP Address
	 * @param num grouping order
	 */
	public void setIpAddressGroup(int num)
	{
		setGroup(_Prefix + Rft.IPADDRESS.toString(), num);
	}

	/**
	 * Fetch IP Address info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setIpAddressCollect()
	{
		setCollect(_Prefix + Rft.IPADDRESS.toString(), "");
	}

	/**
	 * Fetch IP Address info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setIpAddressCollect(String compcode)
	{
		setCollect(_Prefix + Rft.IPADDRESS.toString(), compcode);
	}

	/**
	 *Set search value for  Radio flag
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRadioFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RADIOFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Radio flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRadioFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RADIOFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Radio flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRadioFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RADIOFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Radio flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRadioFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RADIOFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Radio flag
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRadioFlagOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.RADIOFLAG.toString(), num, bool);
	}

	/**
	 * Set grouping order Radio flag
	 * @param num grouping order
	 */
	public void setRadioFlagGroup(int num) 
	{
		setGroup(_Prefix + Rft.RADIOFLAG.toString(), num);
	}

	/**
	 * Fetch Radio flag info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRadioFlagCollect()
	{
		setCollect(_Prefix + Rft.RADIOFLAG.toString(), "");
	}

	/**
	 * Fetch Radio flag info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRadioFlagCollect(String compcode)
	{
		setCollect(_Prefix + Rft.RADIOFLAG.toString(), compcode);
	}

	/**
	 *Set search value for  Rest start time
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestStartTime(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESTSTARTTIME.toString(), arg);
	}
	/**
	 *Set search value for  Rest start time
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestStartTime(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESTSTARTTIME.toString(), arg);
	}
	/**
	 *Set search value for  Rest start time
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestStartTime(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESTSTARTTIME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Rest start time
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestStartTime(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESTSTARTTIME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Rest start time
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRestStartTimeOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.RESTSTARTTIME.toString(), num, bool);
	}

	/**
	 * Set grouping order Rest start time
	 * @param num grouping order
	 */
	public void setRestStartTimeGroup(int num) 
	{
		setGroup(_Prefix + Rft.RESTSTARTTIME.toString(), num);
	}

	/**
	 * Fetch Rest start time info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRestStartTimeCollect()
	{
		setCollect(_Prefix + Rft.RESTSTARTTIME.toString(), "");
	}

	/**
	 * Fetch Rest start time info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRestStartTimeCollect(String compcode)
	{
		setCollect(_Prefix + Rft.RESTSTARTTIME.toString(), compcode);
	}

	/**
	 *Set search value for  Response ID
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResponseId(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESPONSEID.toString(), arg);
	}
	/**
	 *Set search value for  Response ID
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResponseId(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESPONSEID.toString(), arg);
	}
	/**
	 *Set search value for  Response ID
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResponseId(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESPONSEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Response ID
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResponseId(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESPONSEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Response ID
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setResponseIdOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.RESPONSEID.toString(), num, bool);
	}

	/**
	 * Set grouping order Response ID
	 * @param num grouping order
	 */
	public void setResponseIdGroup(int num)
	{
		setGroup(_Prefix + Rft.RESPONSEID.toString(), num);
	}

	/**
	 * Fetch Response ID info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setResponseIdCollect()
	{
		setCollect(_Prefix + Rft.RESPONSEID.toString(), "");
	}

	/**
	 * Fetch Response ID info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setResponseIdCollect(String compcode)
	{
		setCollect(_Prefix + Rft.RESPONSEID.toString(), compcode);
	}

	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Rft.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.REGISTDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered date
	 * @param num grouping order
	 */
	public void setRegistDateGroup(int num) 
	{
		setGroup(_Prefix + Rft.REGISTDATE.toString(), num);
	}

	/**
	 * Fetch Registered date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistDateCollect()
	{
		setCollect(_Prefix + Rft.REGISTDATE.toString(), "");
	}

	/**
	 * Fetch Registered date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistDateCollect(String compcode)
	{
		setCollect(_Prefix + Rft.REGISTDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Rft.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Registered name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setRegistPnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.REGISTPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Registered name
	 * @param num grouping order
	 */
	public void setRegistPnameGroup(int num)
	{
		setGroup(_Prefix + Rft.REGISTPNAME.toString(), num);
	}

	/**
	 * Fetch Registered name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setRegistPnameCollect()
	{
		setCollect(_Prefix + Rft.REGISTPNAME.toString(), "");
	}

	/**
	 * Fetch Registered name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setRegistPnameCollect(String compcode)
	{
		setCollect(_Prefix + Rft.REGISTPNAME.toString(), compcode);
	}

	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Rft.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update date
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdateDateOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.LASTUPDATEDATE.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update date
	 * @param num grouping order
	 */
	public void setLastUpdateDateGroup(int num) 
	{
		setGroup(_Prefix + Rft.LASTUPDATEDATE.toString(), num);
	}

	/**
	 * Fetch Last update date info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdateDateCollect()
	{
		setCollect(_Prefix + Rft.LASTUPDATEDATE.toString(), "");
	}

	/**
	 * Fetch Last update date info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdateDateCollect(String compcode)
	{
		setCollect(_Prefix + Rft.LASTUPDATEDATE.toString(), compcode);
	}

	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Rft.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Rft.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Rft.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set sort order for Last update name
	 * @param num Specify sort order
	 * @param bool true (ascending) or false (descending)
	 */
	public void setLastUpdatePnameOrder(int num, boolean bool)
	{
		setOrder(_Prefix + Rft.LASTUPDATEPNAME.toString(), num, bool);
	}

	/**
	 * Set grouping order Last update name
	 * @param num grouping order
	 */
	public void setLastUpdatePnameGroup(int num) 
	{
		setGroup(_Prefix + Rft.LASTUPDATEPNAME.toString(), num);
	}

	/**
	 * Fetch Last update name info acquisition order
	 * Set empty value if there is no argument
	 */
	public void setLastUpdatePnameCollect()
	{
		setCollect(_Prefix + Rft.LASTUPDATEPNAME.toString(), "");
	}

	/**
	 * Fetch Last update name info acquisition order
	 * @param compcode Specify conditions for collect (MAX,MIN,MAX,AVERAGE etc.,)
	 */
	public void setLastUpdatePnameCollect(String compcode)
	{
		setCollect(_Prefix + Rft.LASTUPDATEPNAME.toString(), compcode);
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
		return "$Id: RftSearchKey.java,v 1.3 2006/11/13 04:33:02 suresh Exp $" ;
	}
}
