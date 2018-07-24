//$Id: RftAlterKey.java,v 1.2 2006/11/09 07:51:32 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Rft;

/**
 * Update key class for RFT use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:51:32 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class RftAlterKey
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
	public RftAlterKey()
	{
		super(new Rft()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for RFT No
	 * @param arg RFT No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRftNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RFTNO.toString(), arg);
	}
	/**
	 * Set search value for RFT No
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
	 * Set update value for RFT No
	 * @param arg Update value for RFT No
	 */
	public void updateRftNo(String arg)
	{
		setUpdValue(_Prefix + Rft.RFTNO.toString(), arg);
	}

	/**
	 * Set search value for Worker code
	 * @param arg Worker code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.WORKERCODE.toString(), arg);
	}
	/**
	 * Set search value for Worker code
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
	 * Set update value for Worker code
	 * @param arg Update value for Worker code
	 */
	public void updateWorkerCode(String arg)
	{
		setUpdValue(_Prefix + Rft.WORKERCODE.toString(), arg);
	}

	/**
	 * Set search value for Work type
	 * @param arg Work type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.JOBTYPE.toString(), arg);
	}
	/**
	 * Set search value for Work type
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
	 * Set update value for Work type
	 * @param arg Update value for Work type
	 */
	public void updateJobType(String arg)
	{
		setUpdValue(_Prefix + Rft.JOBTYPE.toString(), arg);
	}

	/**
	 * Set search value for Status flag
	 * @param arg Status flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.STATUSFLAG.toString(), arg);
	}
	/**
	 * Set search value for Status flag
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
	 * Set update value for Status flag
	 * @param arg Update value for Status flag
	 */
	public void updateStatusFlag(String arg)
	{
		setUpdValue(_Prefix + Rft.STATUSFLAG.toString(), arg);
	}

	/**
	 * Set search value for Rest flag
	 * @param arg Rest flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESTFLAG.toString(), arg);
	}
	/**
	 * Set search value for Rest flag
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
	 * Set update value for Rest flag
	 * @param arg Update value for Rest flag
	 */
	public void updateRestFlag(String arg)
	{
		setUpdValue(_Prefix + Rft.RESTFLAG.toString(), arg);
	}

	/**
	 * Set search value for Terminal type
	 * @param arg Terminal type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.TERMINALTYPE.toString(), arg);
	}
	/**
	 * Set search value for Terminal type
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
	 * Set update value for Terminal type
	 * @param arg Update value for Terminal type
	 */
	public void updateTerminalType(String arg)
	{
		setUpdValue(_Prefix + Rft.TERMINALTYPE.toString(), arg);
	}

	/**
	 * Set search value for IP Address
	 * @param arg IP Address<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIpAddress(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.IPADDRESS.toString(), arg);
	}
	/**
	 * Set search value for IP Address
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
	 * Set update value for IP Address
	 * @param arg Update value for IP Address
	 */
	public void updateIpAddress(String arg)
	{
		setUpdValue(_Prefix + Rft.IPADDRESS.toString(), arg);
	}

	/**
	 * Set search value for Radio flag
	 * @param arg Radio flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRadioFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RADIOFLAG.toString(), arg);
	}
	/**
	 * Set search value for Radio flag
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
	 * Set update value for Radio flag
	 * @param arg Update value for Radio flag
	 */
	public void updateRadioFlag(String arg)
	{
		setUpdValue(_Prefix + Rft.RADIOFLAG.toString(), arg);
	}

	/**
	 * Set search value for Rest start time
	 * @param arg Rest start time<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRestStartTime(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESTSTARTTIME.toString(), arg);
	}
	/**
	 * Set search value for Rest start time
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
	 * Set update value for Rest start time
	 * @param arg Update value for Rest start time
	 */
	public void updateRestStartTime(Date arg)
	{
		setUpdValue(_Prefix + Rft.RESTSTARTTIME.toString(), arg);
	}

	/**
	 * Set search value for Response ID
	 * @param arg Response ID<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResponseId(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.RESPONSEID.toString(), arg);
	}
	/**
	 * Set search value for Response ID
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
	 * Set update value for Response ID
	 * @param arg Update value for Response ID
	 */
	public void updateResponseId(String arg)
	{
		setUpdValue(_Prefix + Rft.RESPONSEID.toString(), arg);
	}

	/**
	 * Set search value for Registered date
	 * @param arg Registered date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.REGISTDATE.toString(), arg);
	}
	/**
	 * Set search value for Registered date
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
	 * Set update value for Registered date
	 * @param arg Update value for Registered date
	 */
	public void updateRegistDate(Date arg)
	{
		setUpdValue(_Prefix + Rft.REGISTDATE.toString(), arg);
	}

	/**
	 * Set search value for Registered name
	 * @param arg Registered name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.REGISTPNAME.toString(), arg);
	}
	/**
	 * Set search value for Registered name
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
	 * Set update value for Registered name
	 * @param arg Update value for Registered name
	 */
	public void updateRegistPname(String arg)
	{
		setUpdValue(_Prefix + Rft.REGISTPNAME.toString(), arg);
	}

	/**
	 * Set search value for Last update date
	 * @param arg Last update date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 * Set search value for Last update date
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
	 * Set update value for Last update date
	 * @param arg Update value for Last update date
	 */
	public void updateLastUpdateDate(Date arg)
	{
		setUpdValue(_Prefix + Rft.LASTUPDATEDATE.toString(), arg);
	}

	/**
	 * Set search value for Last update name
	 * @param arg Last update name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Rft.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 * Set search value for Last update name
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
	 * Set update value for Last update name
	 * @param arg Update value for Last update name
	 */
	public void updateLastUpdatePname(String arg)
	{
		setUpdValue(_Prefix + Rft.LASTUPDATEPNAME.toString(), arg);
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
		return "$Id: RftAlterKey.java,v 1.2 2006/11/09 07:51:32 suresh Exp $" ;
	}
}
