//$Id: HostSendAlterKey.java,v 1.2 2006/11/09 07:53:05 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.HostSend;

/**
 * Update key class for HOSTSEND use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:53:05 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class HostSendAlterKey
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
	public HostSendAlterKey()
	{
		super(new HostSend()) ;
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
		setValue(_Prefix + HostSend.WORKDATE.toString(), arg);
	}
	/**
	 * Set search value for Work date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKDATE.toString(), arg);
	}
	/**
	 *Set search value for  Work date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKDATE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.WORKDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Work date
	 * @param arg Update value for Work date
	 */
	public void updateWorkDate(String arg)
	{
		setUpdValue(_Prefix + HostSend.WORKDATE.toString(), arg);
	}

	/**
	 * Set search value for Work no
	 * @param arg Work no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.JOBNO.toString(), arg);
	}
	/**
	 * Set search value for Work no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.JOBNO.toString(), arg);
	}
	/**
	 *Set search value for  Work no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.JOBNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Work no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.JOBNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Work no
	 * @param arg Update value for Work no
	 */
	public void updateJobNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.JOBNO.toString(), arg);
	}

	/**
	 * Set search value for Work type
	 * @param arg Work type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.JOBTYPE.toString(), arg);
	}
	/**
	 * Set search value for Work type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.JOBTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.JOBTYPE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.JOBTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Work type
	 * @param arg Update value for Work type
	 */
	public void updateJobType(String arg)
	{
		setUpdValue(_Prefix + HostSend.JOBTYPE.toString(), arg);
	}

	/**
	 * Set search value for Collect work no
	 * @param arg Collect work no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCollectJobNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.COLLECTJOBNO.toString(), arg);
	}
	/**
	 * Set search value for Collect work no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCollectJobNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.COLLECTJOBNO.toString(), arg);
	}
	/**
	 *Set search value for  Collect work no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCollectJobNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.COLLECTJOBNO.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.COLLECTJOBNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Collect work no
	 * @param arg Update value for Collect work no
	 */
	public void updateCollectJobNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.COLLECTJOBNO.toString(), arg);
	}

	/**
	 * Set search value for Status flag
	 * @param arg Status flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.STATUSFLAG.toString(), arg);
	}
	/**
	 * Set search value for Status flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.STATUSFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.STATUSFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Status flag
	 * @param arg Update value for Status flag
	 */
	public void updateStatusFlag(String arg)
	{
		setUpdValue(_Prefix + HostSend.STATUSFLAG.toString(), arg);
	}

	/**
	 * Set search value for Work start flag
	 * @param arg Work start flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBeginningFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BEGINNINGFLAG.toString(), arg);
	}
	/**
	 * Set search value for Work start flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBeginningFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BEGINNINGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Work start flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBeginningFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BEGINNINGFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.BEGINNINGFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Work start flag
	 * @param arg Update value for Work start flag
	 */
	public void updateBeginningFlag(String arg)
	{
		setUpdValue(_Prefix + HostSend.BEGINNINGFLAG.toString(), arg);
	}

	/**
	 * Set search value for Plan unique key
	 * @param arg Plan unique key<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANUKEY.toString(), arg);
	}
	/**
	 * Set search value for Plan unique key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANUKEY.toString(), arg);
	}
	/**
	 *Set search value for  Plan unique key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanUkey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANUKEY.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.PLANUKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Plan unique key
	 * @param arg Update value for Plan unique key
	 */
	public void updatePlanUkey(String arg)
	{
		setUpdValue(_Prefix + HostSend.PLANUKEY.toString(), arg);
	}

	/**
	 * Set search value for Stock ID
	 * @param arg Stock ID<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockId(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.STOCKID.toString(), arg);
	}
	/**
	 * Set search value for Stock ID
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockId(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.STOCKID.toString(), arg);
	}
	/**
	 *Set search value for  Stock ID
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockId(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.STOCKID.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.STOCKID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Stock ID
	 * @param arg Update value for Stock ID
	 */
	public void updateStockId(String arg)
	{
		setUpdValue(_Prefix + HostSend.STOCKID.toString(), arg);
	}

	/**
	 * Set search value for Area no
	 * @param arg Area no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.AREANO.toString(), arg);
	}
	/**
	 * Set search value for Area no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.AREANO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.AREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area no
	 * @param arg Update value for Area no
	 */
	public void updateAreaNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.AREANO.toString(), arg);
	}

	/**
	 * Set search value for Zone no
	 * @param arg Zone no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ZONENO.toString(), arg);
	}
	/**
	 * Set search value for Zone no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ZONENO.toString(), arg);
	}
	/**
	 *Set search value for  Zone no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ZONENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Zone no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZoneNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ZONENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Zone no
	 * @param arg Update value for Zone no
	 */
	public void updateZoneNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.ZONENO.toString(), arg);
	}

	/**
	 * Set search value for Location no
	 * @param arg Location no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LOCATIONNO.toString(), arg);
	}
	/**
	 * Set search value for Location no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Location no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LOCATIONNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Location no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LOCATIONNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Location no
	 * @param arg Update value for Location no
	 */
	public void updateLocationNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.LOCATIONNO.toString(), arg);
	}

	/**
	 * Set search value for Plan date
	 * @param arg Plan date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANDATE.toString(), arg);
	}
	/**
	 * Set search value for Plan date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANDATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANDATE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.PLANDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Plan date
	 * @param arg Update value for Plan date
	 */
	public void updatePlanDate(String arg)
	{
		setUpdValue(_Prefix + HostSend.PLANDATE.toString(), arg);
	}

	/**
	 * Set search value for Consignor code
	 * @param arg Consignor code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CONSIGNORCODE.toString(), arg);
	}
	/**
	 * Set search value for Consignor code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CONSIGNORCODE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Consignor code
	 * @param arg Update value for Consignor code
	 */
	public void updateConsignorCode(String arg)
	{
		setUpdValue(_Prefix + HostSend.CONSIGNORCODE.toString(), arg);
	}

	/**
	 * Set search value for Consignor name
	 * @param arg Consignor name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CONSIGNORNAME.toString(), arg);
	}
	/**
	 * Set search value for Consignor name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  Consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CONSIGNORNAME.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.CONSIGNORNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Consignor name
	 * @param arg Update value for Consignor name
	 */
	public void updateConsignorName(String arg)
	{
		setUpdValue(_Prefix + HostSend.CONSIGNORNAME.toString(), arg);
	}

	/**
	 * Set search value for Supplier code
	 * @param arg Supplier code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SUPPLIERCODE.toString(), arg);
	}
	/**
	 * Set search value for Supplier code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SUPPLIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SUPPLIERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.SUPPLIERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Supplier code
	 * @param arg Update value for Supplier code
	 */
	public void updateSupplierCode(String arg)
	{
		setUpdValue(_Prefix + HostSend.SUPPLIERCODE.toString(), arg);
	}

	/**
	 * Set search value for Supplier name
	 * @param arg Supplier name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SUPPLIERNAME1.toString(), arg);
	}
	/**
	 * Set search value for Supplier name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SUPPLIERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Supplier name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SUPPLIERNAME1.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.SUPPLIERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Supplier name
	 * @param arg Update value for Supplier name
	 */
	public void updateSupplierName1(String arg)
	{
		setUpdValue(_Prefix + HostSend.SUPPLIERNAME1.toString(), arg);
	}

	/**
	 * Set search value for Receiving ticket no
	 * @param arg Receiving ticket no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.INSTOCKTICKETNO.toString(), arg);
	}
	/**
	 * Set search value for Receiving ticket no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.INSTOCKTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.INSTOCKTICKETNO.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.INSTOCKTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Receiving ticket no
	 * @param arg Update value for Receiving ticket no
	 */
	public void updateInstockTicketNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.INSTOCKTICKETNO.toString(), arg);
	}

	/**
	 * Set search value for Receiving line no
	 * @param arg Receiving line no<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.INSTOCKLINENO.toString(), arg);
	}
	/**
	 * Set search value for Receiving line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.INSTOCKLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Receiving line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.INSTOCKLINENO.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.INSTOCKLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Receiving line no
	 * @param arg Update value for Receiving line no
	 */
	public void updateInstockLineNo(int arg)
	{
		setUpdValue(_Prefix + HostSend.INSTOCKLINENO.toString(), arg);
	}

	/**
	 * Set search value for Customer code
	 * @param arg Customer code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CUSTOMERCODE.toString(), arg);
	}
	/**
	 * Set search value for Customer code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CUSTOMERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Customer code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CUSTOMERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.CUSTOMERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Customer code
	 * @param arg Update value for Customer code
	 */
	public void updateCustomerCode(String arg)
	{
		setUpdValue(_Prefix + HostSend.CUSTOMERCODE.toString(), arg);
	}

	/**
	 * Set search value for Customer name
	 * @param arg Customer name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 * Set search value for Customer name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CUSTOMERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Customer name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCustomerName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CUSTOMERNAME1.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.CUSTOMERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Customer name
	 * @param arg Update value for Customer name
	 */
	public void updateCustomerName1(String arg)
	{
		setUpdValue(_Prefix + HostSend.CUSTOMERNAME1.toString(), arg);
	}

	/**
	 * Set search value for Shipping ticket no
	 * @param arg Shipping ticket no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 * Set search value for Shipping ticket no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SHIPPINGTICKETNO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping ticket no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingTicketNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SHIPPINGTICKETNO.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.SHIPPINGTICKETNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shipping ticket no
	 * @param arg Update value for Shipping ticket no
	 */
	public void updateShippingTicketNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.SHIPPINGTICKETNO.toString(), arg);
	}

	/**
	 * Set search value for Shipping line no
	 * @param arg Shipping line no<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 * Set search value for Shipping line no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SHIPPINGLINENO.toString(), arg);
	}
	/**
	 *Set search value for  Shipping line no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingLineNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SHIPPINGLINENO.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.SHIPPINGLINENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shipping line no
	 * @param arg Update value for Shipping line no
	 */
	public void updateShippingLineNo(int arg)
	{
		setUpdValue(_Prefix + HostSend.SHIPPINGLINENO.toString(), arg);
	}

	/**
	 * Set search value for Item code
	 * @param arg Item code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ITEMCODE.toString(), arg);
	}
	/**
	 * Set search value for Item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ITEMCODE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.ITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Item code
	 * @param arg Update value for Item code
	 */
	public void updateItemCode(String arg)
	{
		setUpdValue(_Prefix + HostSend.ITEMCODE.toString(), arg);
	}

	/**
	 * Set search value for Item name
	 * @param arg Item name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ITEMNAME1.toString(), arg);
	}
	/**
	 * Set search value for Item name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Item name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ITEMNAME1.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.ITEMNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Item name
	 * @param arg Update value for Item name
	 */
	public void updateItemName1(String arg)
	{
		setUpdValue(_Prefix + HostSend.ITEMNAME1.toString(), arg);
	}

	/**
	 * Set search value for Host plan qty
	 * @param arg Host plan qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostPlanQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.HOSTPLANQTY.toString(), arg);
	}
	/**
	 * Set search value for Host plan qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostPlanQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.HOSTPLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Host plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHostPlanQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.HOSTPLANQTY.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.HOSTPLANQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Host plan qty
	 * @param arg Update value for Host plan qty
	 */
	public void updateHostPlanQty(int arg)
	{
		setUpdValue(_Prefix + HostSend.HOSTPLANQTY.toString(), arg);
	}

	/**
	 * Set search value for Work plan qty
	 * @param arg Work plan qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANQTY.toString(), arg);
	}
	/**
	 * Set search value for Work plan qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Work plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANQTY.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.PLANQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Work plan qty
	 * @param arg Update value for Work plan qty
	 */
	public void updatePlanQty(int arg)
	{
		setUpdValue(_Prefix + HostSend.PLANQTY.toString(), arg);
	}

	/**
	 * Set search value for Plan enabled qty
	 * @param arg Plan enabled qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanEnableQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANENABLEQTY.toString(), arg);
	}
	/**
	 * Set search value for Plan enabled qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanEnableQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANENABLEQTY.toString(), arg);
	}
	/**
	 *Set search value for  Plan enabled qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanEnableQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANENABLEQTY.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.PLANENABLEQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Plan enabled qty
	 * @param arg Update value for Plan enabled qty
	 */
	public void updatePlanEnableQty(int arg)
	{
		setUpdValue(_Prefix + HostSend.PLANENABLEQTY.toString(), arg);
	}

	/**
	 * Set search value for Result qty
	 * @param arg Result qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTQTY.toString(), arg);
	}
	/**
	 * Set search value for Result qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTQTY.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.RESULTQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Result qty
	 * @param arg Update value for Result qty
	 */
	public void updateResultQty(int arg)
	{
		setUpdValue(_Prefix + HostSend.RESULTQTY.toString(), arg);
	}

	/**
	 * Set search value for Shortage qty
	 * @param arg Shortage qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SHORTAGECNT.toString(), arg);
	}
	/**
	 * Set search value for Shortage qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SHORTAGECNT.toString(), arg);
	}
	/**
	 *Set search value for  Shortage qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SHORTAGECNT.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.SHORTAGECNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shortage qty
	 * @param arg Update value for Shortage qty
	 */
	public void updateShortageCnt(int arg)
	{
		setUpdValue(_Prefix + HostSend.SHORTAGECNT.toString(), arg);
	}

	/**
	 * Set search value for Pending qty
	 * @param arg Pending qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPendingQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PENDINGQTY.toString(), arg);
	}
	/**
	 * Set search value for Pending qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPendingQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PENDINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Pending qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPendingQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PENDINGQTY.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.PENDINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Pending qty
	 * @param arg Update value for Pending qty
	 */
	public void updatePendingQty(int arg)
	{
		setUpdValue(_Prefix + HostSend.PENDINGQTY.toString(), arg);
	}

	/**
	 * Set search value for Entering case qty
	 * @param arg Entering case qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ENTERINGQTY.toString(), arg);
	}
	/**
	 * Set search value for Entering case qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Entering case qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ENTERINGQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Entering case qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Entering case qty
	 * @param arg Update value for Entering case qty
	 */
	public void updateEnteringQty(int arg)
	{
		setUpdValue(_Prefix + HostSend.ENTERINGQTY.toString(), arg);
	}

	/**
	 * Set search value for Bundle qty
	 * @param arg Bundle qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 * Set search value for Bundle qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BUNDLEENTERINGQTY.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.BUNDLEENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Bundle qty
	 * @param arg Update value for Bundle qty
	 */
	public void updateBundleEnteringQty(int arg)
	{
		setUpdValue(_Prefix + HostSend.BUNDLEENTERINGQTY.toString(), arg);
	}

	/**
	 * Set search value for Case/Piece flag
	 * @param arg Case/Piece flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 * Set search value for Case/Piece flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.CASEPIECEFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.CASEPIECEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Case/Piece flag
	 * @param arg Update value for Case/Piece flag
	 */
	public void updateCasePieceFlag(String arg)
	{
		setUpdValue(_Prefix + HostSend.CASEPIECEFLAG.toString(), arg);
	}

	/**
	 * Set search value for Case/Piece flag (Work form flag)
	 * @param arg Case/Piece flag (Work form flag)<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkFormFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKFORMFLAG.toString(), arg);
	}
	/**
	 * Set search value for Case/Piece flag (Work form flag)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkFormFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKFORMFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag (Work form flag)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkFormFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKFORMFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.WORKFORMFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Case/Piece flag (Work form flag)
	 * @param arg Update value for Case/Piece flag (Work form flag)
	 */
	public void updateWorkFormFlag(String arg)
	{
		setUpdValue(_Prefix + HostSend.WORKFORMFLAG.toString(), arg);
	}

	/**
	 * Set search value for Case ITF
	 * @param arg Case ITF<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ITF.toString(), arg);
	}
	/**
	 * Set search value for Case ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ITF.toString(), arg);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ITF.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.ITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Case ITF
	 * @param arg Update value for Case ITF
	 */
	public void updateItf(String arg)
	{
		setUpdValue(_Prefix + HostSend.ITF.toString(), arg);
	}

	/**
	 * Set search value for Bundle ITF
	 * @param arg Bundle ITF<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BUNDLEITF.toString(), arg);
	}
	/**
	 * Set search value for Bundle ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BUNDLEITF.toString(), arg);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BUNDLEITF.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.BUNDLEITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Bundle ITF
	 * @param arg Update value for Bundle ITF
	 */
	public void updateBundleItf(String arg)
	{
		setUpdValue(_Prefix + HostSend.BUNDLEITF.toString(), arg);
	}

	/**
	 * Set search value for TC/DC flag
	 * @param arg TC/DC flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.TCDCFLAG.toString(), arg);
	}
	/**
	 * Set search value for TC/DC flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.TCDCFLAG.toString(), arg);
	}
	/**
	 *Set search value for  TC/DC flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcdcFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.TCDCFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.TCDCFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for TC/DC flag
	 * @param arg Update value for TC/DC flag
	 */
	public void updateTcdcFlag(String arg)
	{
		setUpdValue(_Prefix + HostSend.TCDCFLAG.toString(), arg);
	}

	/**
	 * Set search value for Use by date
	 * @param arg Use by date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.USEBYDATE.toString(), arg);
	}
	/**
	 * Set search value for Use by date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.USEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Use by date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.USEBYDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Use by date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.USEBYDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Use by date
	 * @param arg Update value for Use by date
	 */
	public void updateUseByDate(String arg)
	{
		setUpdValue(_Prefix + HostSend.USEBYDATE.toString(), arg);
	}

	/**
	 * Set search value for Lot No
	 * @param arg Lot No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LOTNO.toString(), arg);
	}
	/**
	 * Set search value for Lot No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Lot No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LOTNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Lot No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LOTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Lot No
	 * @param arg Update value for Lot No
	 */
	public void updateLotNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.LOTNO.toString(), arg);
	}

	/**
	 * Set search value for Plan details comment
	 * @param arg Plan details comment<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANINFORMATION.toString(), arg);
	}
	/**
	 * Set search value for Plan details comment
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANINFORMATION.toString(), arg);
	}
	/**
	 *Set search value for  Plan details comment
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANINFORMATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan details comment
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANINFORMATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Plan details comment
	 * @param arg Update value for Plan details comment
	 */
	public void updatePlanInformation(String arg)
	{
		setUpdValue(_Prefix + HostSend.PLANINFORMATION.toString(), arg);
	}

	/**
	 * Set search value for Order no
	 * @param arg Order no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ORDERNO.toString(), arg);
	}
	/**
	 * Set search value for Order no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ORDERNO.toString(), arg);
	}
	/**
	 *Set search value for  Order no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ORDERNO.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.ORDERNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Order no
	 * @param arg Update value for Order no
	 */
	public void updateOrderNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.ORDERNO.toString(), arg);
	}

	/**
	 * Set search value for Order sequence no
	 * @param arg Order sequence no<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderSeqNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ORDERSEQNO.toString(), arg);
	}
	/**
	 * Set search value for Order sequence no
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderSeqNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ORDERSEQNO.toString(), arg);
	}
	/**
	 *Set search value for  Order sequence no
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderSeqNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ORDERSEQNO.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.ORDERSEQNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Order sequence no
	 * @param arg Update value for Order sequence no
	 */
	public void updateOrderSeqNo(int arg)
	{
		setUpdValue(_Prefix + HostSend.ORDERSEQNO.toString(), arg);
	}

	/**
	 * Set search value for Ordering date
	 * @param arg Ordering date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ORDERINGDATE.toString(), arg);
	}
	/**
	 * Set search value for Ordering date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ORDERINGDATE.toString(), arg);
	}
	/**
	 *Set search value for  Ordering date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setOrderingDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.ORDERINGDATE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.ORDERINGDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Ordering date
	 * @param arg Update value for Ordering date
	 */
	public void updateOrderingDate(String arg)
	{
		setUpdValue(_Prefix + HostSend.ORDERINGDATE.toString(), arg);
	}

	/**
	 * Set search value for Result use by date
	 * @param arg Result use by date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultUseByDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTUSEBYDATE.toString(), arg);
	}
	/**
	 * Set search value for Result use by date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultUseByDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTUSEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Result use by date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultUseByDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTUSEBYDATE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.RESULTUSEBYDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Result use by date
	 * @param arg Update value for Result use by date
	 */
	public void updateResultUseByDate(String arg)
	{
		setUpdValue(_Prefix + HostSend.RESULTUSEBYDATE.toString(), arg);
	}

	/**
	 * Set search value for Result lot no
	 * @param arg Result lot no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLotNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTLOTNO.toString(), arg);
	}
	/**
	 * Set search value for Result lot no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLotNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTLOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Result lot no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLotNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTLOTNO.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.RESULTLOTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Result lot no
	 * @param arg Update value for Result lot no
	 */
	public void updateResultLotNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.RESULTLOTNO.toString(), arg);
	}

	/**
	 * Set search value for Result location no
	 * @param arg Result location no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLocationNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTLOCATIONNO.toString(), arg);
	}
	/**
	 * Set search value for Result location no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLocationNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTLOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Result location no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLocationNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.RESULTLOCATIONNO.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.RESULTLOCATIONNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Result location no
	 * @param arg Update value for Result location no
	 */
	public void updateResultLocationNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.RESULTLOCATIONNO.toString(), arg);
	}

	/**
	 * Set search value for Report flag
	 * @param arg Report flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.REPORTFLAG.toString(), arg);
	}
	/**
	 * Set search value for Report flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.REPORTFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Report flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.REPORTFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.REPORTFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Report flag
	 * @param arg Update value for Report flag
	 */
	public void updateReportFlag(String arg)
	{
		setUpdValue(_Prefix + HostSend.REPORTFLAG.toString(), arg);
	}

	/**
	 * Set search value for Batch No (Schedule No)
	 * @param arg Batch No (Schedule No)<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BATCHNO.toString(), arg);
	}
	/**
	 * Set search value for Batch No (Schedule No)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Batch No (Schedule No)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BATCHNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Batch No (Schedule No)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.BATCHNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Batch No (Schedule No)
	 * @param arg Update value for Batch No (Schedule No)
	 */
	public void updateBatchNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.BATCHNO.toString(), arg);
	}

	/**
	 * Set search value for System Connection key
	 * @param arg System Connection key<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemConnKey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SYSTEMCONNKEY.toString(), arg);
	}
	/**
	 * Set search value for System Connection key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemConnKey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SYSTEMCONNKEY.toString(), arg);
	}
	/**
	 *Set search value for  System Connection key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemConnKey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SYSTEMCONNKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  System Connection key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemConnKey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SYSTEMCONNKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for System Connection key
	 * @param arg Update value for System Connection key
	 */
	public void updateSystemConnKey(String arg)
	{
		setUpdValue(_Prefix + HostSend.SYSTEMCONNKEY.toString(), arg);
	}

	/**
	 * Set search value for System identification key
	 * @param arg System identification key<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemDiscKey(int arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SYSTEMDISCKEY.toString(), arg);
	}
	/**
	 * Set search value for System identification key
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemDiscKey(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SYSTEMDISCKEY.toString(), arg);
	}
	/**
	 *Set search value for  System identification key
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemDiscKey(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.SYSTEMDISCKEY.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.SYSTEMDISCKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for System identification key
	 * @param arg Update value for System identification key
	 */
	public void updateSystemDiscKey(int arg)
	{
		setUpdValue(_Prefix + HostSend.SYSTEMDISCKEY.toString(), arg);
	}

	/**
	 * Set search value for Worker code
	 * @param arg Worker code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKERCODE.toString(), arg);
	}
	/**
	 * Set search value for Worker code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.WORKERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Worker code
	 * @param arg Update value for Worker code
	 */
	public void updateWorkerCode(String arg)
	{
		setUpdValue(_Prefix + HostSend.WORKERCODE.toString(), arg);
	}

	/**
	 * Set search value for Worker name
	 * @param arg Worker name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKERNAME.toString(), arg);
	}
	/**
	 * Set search value for Worker name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.WORKERNAME.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.WORKERNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Worker name
	 * @param arg Update value for Worker name
	 */
	public void updateWorkerName(String arg)
	{
		setUpdValue(_Prefix + HostSend.WORKERNAME.toString(), arg);
	}

	/**
	 * Set search value for Terminal no
	 * @param arg Terminal no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.TERMINALNO.toString(), arg);
	}
	/**
	 * Set search value for Terminal no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.TERMINALNO.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.TERMINALNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Terminal no
	 * @param arg Update value for Terminal no
	 */
	public void updateTerminalNo(String arg)
	{
		setUpdValue(_Prefix + HostSend.TERMINALNO.toString(), arg);
	}

	/**
	 * Set search value for Plan registration date
	 * @param arg Plan registration date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANREGISTDATE.toString(), arg);
	}
	/**
	 * Set search value for Plan registration date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANREGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan registration date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.PLANREGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.PLANREGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Plan registration date
	 * @param arg Update value for Plan registration date
	 */
	public void updatePlanRegistDate(Date arg)
	{
		setUpdValue(_Prefix + HostSend.PLANREGISTDATE.toString(), arg);
	}

	/**
	 * Set search value for Registered date
	 * @param arg Registered date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.REGISTDATE.toString(), arg);
	}
	/**
	 * Set search value for Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered date
	 * @param arg Update value for Registered date
	 */
	public void updateRegistDate(Date arg)
	{
		setUpdValue(_Prefix + HostSend.REGISTDATE.toString(), arg);
	}

	/**
	 * Set search value for Registered name
	 * @param arg Registered name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.REGISTPNAME.toString(), arg);
	}
	/**
	 * Set search value for Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered name
	 * @param arg Update value for Registered name
	 */
	public void updateRegistPname(String arg)
	{
		setUpdValue(_Prefix + HostSend.REGISTPNAME.toString(), arg);
	}

	/**
	 * Set search value for Last update date
	 * @param arg Last update date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 * Set search value for Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update date
	 * @param arg Update value for Last update date
	 */
	public void updateLastUpdateDate(Date arg)
	{
		setUpdValue(_Prefix + HostSend.LASTUPDATEDATE.toString(), arg);
	}

	/**
	 * Set search value for Last update name
	 * @param arg Last update name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 * Set search value for Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + HostSend.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + HostSend.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update name
	 * @param arg Update value for Last update name
	 */
	public void updateLastUpdatePname(String arg)
	{
		setUpdValue(_Prefix + HostSend.LASTUPDATEPNAME.toString(), arg);
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
		return "$Id: HostSendAlterKey.java,v 1.2 2006/11/09 07:53:05 suresh Exp $" ;
	}
}
