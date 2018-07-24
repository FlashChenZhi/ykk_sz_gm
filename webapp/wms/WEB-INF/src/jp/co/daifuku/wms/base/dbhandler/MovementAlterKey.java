//$Id: MovementAlterKey.java,v 1.2 2006/11/09 07:51:48 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Movement;

/**
 * Update key class for MOVEMENT use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:51:48 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class MovementAlterKey
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
	public MovementAlterKey()
	{
		super(new Movement()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for Work no
	 * @param arg Work no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.JOBNO.toString(), arg);
	}
	/**
	 * Set search value for Work no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.JOBNO.toString(), arg);
	}
	/**
	 *Set search value for  Work no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.JOBNO.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.JOBNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Work no
	 * @param arg Update value for Work no
	 */
	public void updateJobNo(String arg)
	{
		setUpdValue(_Prefix + Movement.JOBNO.toString(), arg);
	}

	/**
	 * Set search value for Work type
	 * @param arg Work type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.JOBTYPE.toString(), arg);
	}
	/**
	 * Set search value for Work type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.JOBTYPE.toString(), arg);
	}
	/**
	 *Set search value for  Work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setJobType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.JOBTYPE.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.JOBTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Work type
	 * @param arg Update value for Work type
	 */
	public void updateJobType(String arg)
	{
		setUpdValue(_Prefix + Movement.JOBTYPE.toString(), arg);
	}

	/**
	 * Set search value for Collect work no
	 * @param arg Collect work no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCollectJobNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.COLLECTJOBNO.toString(), arg);
	}
	/**
	 * Set search value for Collect work no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCollectJobNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.COLLECTJOBNO.toString(), arg);
	}
	/**
	 *Set search value for  Collect work no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCollectJobNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.COLLECTJOBNO.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.COLLECTJOBNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Collect work no
	 * @param arg Update value for Collect work no
	 */
	public void updateCollectJobNo(String arg)
	{
		setUpdValue(_Prefix + Movement.COLLECTJOBNO.toString(), arg);
	}

	/**
	 * Set search value for Stock ID
	 * @param arg Stock ID<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockId(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.STOCKID.toString(), arg);
	}
	/**
	 * Set search value for Stock ID
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockId(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.STOCKID.toString(), arg);
	}
	/**
	 *Set search value for  Stock ID
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockId(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.STOCKID.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.STOCKID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Stock ID
	 * @param arg Update value for Stock ID
	 */
	public void updateStockId(String arg)
	{
		setUpdValue(_Prefix + Movement.STOCKID.toString(), arg);
	}

	/**
	 * Set search value for Area no
	 * @param arg Area no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.AREANO.toString(), arg);
	}
	/**
	 * Set search value for Area no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.AREANO.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.AREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area no
	 * @param arg Update value for Area no
	 */
	public void updateAreaNo(String arg)
	{
		setUpdValue(_Prefix + Movement.AREANO.toString(), arg);
	}

	/**
	 * Set search value for Location no
	 * @param arg Location no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LOCATIONNO.toString(), arg);
	}
	/**
	 * Set search value for Location no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Location no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LOCATIONNO.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.LOCATIONNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Location no
	 * @param arg Update value for Location no
	 */
	public void updateLocationNo(String arg)
	{
		setUpdValue(_Prefix + Movement.LOCATIONNO.toString(), arg);
	}

	/**
	 * Set search value for Status flag
	 * @param arg Status flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.STATUSFLAG.toString(), arg);
	}
	/**
	 * Set search value for Status flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.STATUSFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.STATUSFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Status flag
	 * @param arg Update value for Status flag
	 */
	public void updateStatusFlag(String arg)
	{
		setUpdValue(_Prefix + Movement.STATUSFLAG.toString(), arg);
	}

	/**
	 * Set search value for Work start flag
	 * @param arg Work start flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBeginningFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BEGINNINGFLAG.toString(), arg);
	}
	/**
	 * Set search value for Work start flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBeginningFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BEGINNINGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Work start flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBeginningFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BEGINNINGFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.BEGINNINGFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Work start flag
	 * @param arg Update value for Work start flag
	 */
	public void updateBeginningFlag(String arg)
	{
		setUpdValue(_Prefix + Movement.BEGINNINGFLAG.toString(), arg);
	}

	/**
	 * Set search value for Work date
	 * @param arg Work date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKDATE.toString(), arg);
	}
	/**
	 * Set search value for Work date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKDATE.toString(), arg);
	}
	/**
	 *Set search value for  Work date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.WORKDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Work date
	 * @param arg Update value for Work date
	 */
	public void updateWorkDate(String arg)
	{
		setUpdValue(_Prefix + Movement.WORKDATE.toString(), arg);
	}

	/**
	 * Set search value for Consignor code
	 * @param arg Consignor code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.CONSIGNORCODE.toString(), arg);
	}
	/**
	 * Set search value for Consignor code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.CONSIGNORCODE.toString(), arg);
	}
	/**
	 *Set search value for  Consignor code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.CONSIGNORCODE.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.CONSIGNORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Consignor code
	 * @param arg Update value for Consignor code
	 */
	public void updateConsignorCode(String arg)
	{
		setUpdValue(_Prefix + Movement.CONSIGNORCODE.toString(), arg);
	}

	/**
	 * Set search value for Consignor name
	 * @param arg Consignor name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.CONSIGNORNAME.toString(), arg);
	}
	/**
	 * Set search value for Consignor name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.CONSIGNORNAME.toString(), arg);
	}
	/**
	 *Set search value for  Consignor name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setConsignorName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.CONSIGNORNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.CONSIGNORNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Consignor name
	 * @param arg Update value for Consignor name
	 */
	public void updateConsignorName(String arg)
	{
		setUpdValue(_Prefix + Movement.CONSIGNORNAME.toString(), arg);
	}

	/**
	 * Set search value for Supplier code
	 * @param arg Supplier code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.SUPPLIERCODE.toString(), arg);
	}
	/**
	 * Set search value for Supplier code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.SUPPLIERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Supplier code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.SUPPLIERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.SUPPLIERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Supplier code
	 * @param arg Update value for Supplier code
	 */
	public void updateSupplierCode(String arg)
	{
		setUpdValue(_Prefix + Movement.SUPPLIERCODE.toString(), arg);
	}

	/**
	 * Set search value for Supplier name
	 * @param arg Supplier name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.SUPPLIERNAME1.toString(), arg);
	}
	/**
	 * Set search value for Supplier name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.SUPPLIERNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Supplier name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSupplierName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.SUPPLIERNAME1.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.SUPPLIERNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Supplier name
	 * @param arg Update value for Supplier name
	 */
	public void updateSupplierName1(String arg)
	{
		setUpdValue(_Prefix + Movement.SUPPLIERNAME1.toString(), arg);
	}

	/**
	 * Set search value for Item code
	 * @param arg Item code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ITEMCODE.toString(), arg);
	}
	/**
	 * Set search value for Item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  Item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ITEMCODE.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.ITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Item code
	 * @param arg Update value for Item code
	 */
	public void updateItemCode(String arg)
	{
		setUpdValue(_Prefix + Movement.ITEMCODE.toString(), arg);
	}

	/**
	 * Set search value for Item name
	 * @param arg Item name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ITEMNAME1.toString(), arg);
	}
	/**
	 * Set search value for Item name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ITEMNAME1.toString(), arg);
	}
	/**
	 *Set search value for  Item name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemName1(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ITEMNAME1.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.ITEMNAME1.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Item name
	 * @param arg Update value for Item name
	 */
	public void updateItemName1(String arg)
	{
		setUpdValue(_Prefix + Movement.ITEMNAME1.toString(), arg);
	}

	/**
	 * Set search value for Plan qty
	 * @param arg Plan qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PLANQTY.toString(), arg);
	}
	/**
	 * Set search value for Plan qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PLANQTY.toString(), arg);
	}
	/**
	 *Set search value for  Plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PLANQTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanQty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PLANQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Plan qty
	 * @param arg Update value for Plan qty
	 */
	public void updatePlanQty(int arg)
	{
		setUpdValue(_Prefix + Movement.PLANQTY.toString(), arg);
	}

	/**
	 * Set search value for Result qty
	 * @param arg Result qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTQTY.toString(), arg);
	}
	/**
	 * Set search value for Result qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTQTY.toString(), arg);
	}
	/**
	 *Set search value for  Result qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTQTY.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.RESULTQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Result qty
	 * @param arg Update value for Result qty
	 */
	public void updateResultQty(int arg)
	{
		setUpdValue(_Prefix + Movement.RESULTQTY.toString(), arg);
	}

	/**
	 * Set search value for Shortage qty
	 * @param arg Shortage qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.SHORTAGECNT.toString(), arg);
	}
	/**
	 * Set search value for Shortage qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.SHORTAGECNT.toString(), arg);
	}
	/**
	 *Set search value for  Shortage qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShortageCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.SHORTAGECNT.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.SHORTAGECNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Shortage qty
	 * @param arg Update value for Shortage qty
	 */
	public void updateShortageCnt(int arg)
	{
		setUpdValue(_Prefix + Movement.SHORTAGECNT.toString(), arg);
	}

	/**
	 * Set search value for Entering case qty
	 * @param arg Entering case qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ENTERINGQTY.toString(), arg);
	}
	/**
	 * Set search value for Entering case qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Entering case qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ENTERINGQTY.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.ENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Entering case qty
	 * @param arg Update value for Entering case qty
	 */
	public void updateEnteringQty(int arg)
	{
		setUpdValue(_Prefix + Movement.ENTERINGQTY.toString(), arg);
	}

	/**
	 * Set search value for Bundle qty
	 * @param arg Bundle qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 * Set search value for Bundle qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BUNDLEENTERINGQTY.toString(), arg);
	}
	/**
	 *Set search value for  Bundle qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleEnteringQty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BUNDLEENTERINGQTY.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.BUNDLEENTERINGQTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Bundle qty
	 * @param arg Update value for Bundle qty
	 */
	public void updateBundleEnteringQty(int arg)
	{
		setUpdValue(_Prefix + Movement.BUNDLEENTERINGQTY.toString(), arg);
	}

	/**
	 * Set search value for Case/Piece flag
	 * @param arg Case/Piece flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 * Set search value for Case/Piece flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.CASEPIECEFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCasePieceFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.CASEPIECEFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.CASEPIECEFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Case/Piece flag
	 * @param arg Update value for Case/Piece flag
	 */
	public void updateCasePieceFlag(String arg)
	{
		setUpdValue(_Prefix + Movement.CASEPIECEFLAG.toString(), arg);
	}

	/**
	 * Set search value for Case/Piece flag (Work form flag)
	 * @param arg Case/Piece flag (Work form flag)<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkFormFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKFORMFLAG.toString(), arg);
	}
	/**
	 * Set search value for Case/Piece flag (Work form flag)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkFormFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKFORMFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Case/Piece flag (Work form flag)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkFormFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKFORMFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.WORKFORMFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Case/Piece flag (Work form flag)
	 * @param arg Update value for Case/Piece flag (Work form flag)
	 */
	public void updateWorkFormFlag(String arg)
	{
		setUpdValue(_Prefix + Movement.WORKFORMFLAG.toString(), arg);
	}

	/**
	 * Set search value for Case ITF
	 * @param arg Case ITF<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ITF.toString(), arg);
	}
	/**
	 * Set search value for Case ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ITF.toString(), arg);
	}
	/**
	 *Set search value for  Case ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.ITF.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.ITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Case ITF
	 * @param arg Update value for Case ITF
	 */
	public void updateItf(String arg)
	{
		setUpdValue(_Prefix + Movement.ITF.toString(), arg);
	}

	/**
	 * Set search value for Bundle ITF
	 * @param arg Bundle ITF<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BUNDLEITF.toString(), arg);
	}
	/**
	 * Set search value for Bundle ITF
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BUNDLEITF.toString(), arg);
	}
	/**
	 *Set search value for  Bundle ITF
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBundleItf(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BUNDLEITF.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.BUNDLEITF.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Bundle ITF
	 * @param arg Update value for Bundle ITF
	 */
	public void updateBundleItf(String arg)
	{
		setUpdValue(_Prefix + Movement.BUNDLEITF.toString(), arg);
	}

	/**
	 * Set search value for Use by date
	 * @param arg Use by date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.USEBYDATE.toString(), arg);
	}
	/**
	 * Set search value for Use by date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.USEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Use by date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setUseByDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.USEBYDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.USEBYDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Use by date
	 * @param arg Update value for Use by date
	 */
	public void updateUseByDate(String arg)
	{
		setUpdValue(_Prefix + Movement.USEBYDATE.toString(), arg);
	}

	/**
	 * Set search value for Lot no
	 * @param arg Lot no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LOTNO.toString(), arg);
	}
	/**
	 * Set search value for Lot no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Lot no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LOTNO.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.LOTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Lot no
	 * @param arg Update value for Lot no
	 */
	public void updateLotNo(String arg)
	{
		setUpdValue(_Prefix + Movement.LOTNO.toString(), arg);
	}

	/**
	 * Set search value for Plan details comment
	 * @param arg Plan details comment<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PLANINFORMATION.toString(), arg);
	}
	/**
	 * Set search value for Plan details comment
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PLANINFORMATION.toString(), arg);
	}
	/**
	 *Set search value for  Plan details comment
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanInformation(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PLANINFORMATION.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.PLANINFORMATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Plan details comment
	 * @param arg Update value for Plan details comment
	 */
	public void updatePlanInformation(String arg)
	{
		setUpdValue(_Prefix + Movement.PLANINFORMATION.toString(), arg);
	}

	/**
	 * Set search value for Result use by date
	 * @param arg Result use by date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultUseByDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTUSEBYDATE.toString(), arg);
	}
	/**
	 * Set search value for Result use by date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultUseByDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTUSEBYDATE.toString(), arg);
	}
	/**
	 *Set search value for  Result use by date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultUseByDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTUSEBYDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.RESULTUSEBYDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Result use by date
	 * @param arg Update value for Result use by date
	 */
	public void updateResultUseByDate(String arg)
	{
		setUpdValue(_Prefix + Movement.RESULTUSEBYDATE.toString(), arg);
	}

	/**
	 * Set search value for Result lot no
	 * @param arg Result lot no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLotNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTLOTNO.toString(), arg);
	}
	/**
	 * Set search value for Result lot no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLotNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTLOTNO.toString(), arg);
	}
	/**
	 *Set search value for  Result lot no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLotNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTLOTNO.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.RESULTLOTNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Result lot no
	 * @param arg Update value for Result lot no
	 */
	public void updateResultLotNo(String arg)
	{
		setUpdValue(_Prefix + Movement.RESULTLOTNO.toString(), arg);
	}

	/**
	 * Set search value for Result area no
	 * @param arg Result area no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultAreaNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTAREANO.toString(), arg);
	}
	/**
	 * Set search value for Result area no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTAREANO.toString(), arg);
	}
	/**
	 *Set search value for  Result area no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTAREANO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Result area no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultAreaNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTAREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Result area no
	 * @param arg Update value for Result area no
	 */
	public void updateResultAreaNo(String arg)
	{
		setUpdValue(_Prefix + Movement.RESULTAREANO.toString(), arg);
	}

	/**
	 * Set search value for Result location no
	 * @param arg Result location no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLocationNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTLOCATIONNO.toString(), arg);
	}
	/**
	 * Set search value for Result location no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLocationNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTLOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Result location no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultLocationNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RESULTLOCATIONNO.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.RESULTLOCATIONNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Result location no
	 * @param arg Update value for Result location no
	 */
	public void updateResultLocationNo(String arg)
	{
		setUpdValue(_Prefix + Movement.RESULTLOCATIONNO.toString(), arg);
	}

	/**
	 * Set search value for Report flag
	 * @param arg Report flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.REPORTFLAG.toString(), arg);
	}
	/**
	 * Set search value for Report flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.REPORTFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Report flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.REPORTFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.REPORTFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Report flag
	 * @param arg Update value for Report flag
	 */
	public void updateReportFlag(String arg)
	{
		setUpdValue(_Prefix + Movement.REPORTFLAG.toString(), arg);
	}

	/**
	 * Set search value for Pring flag
	 * @param arg Pring flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPrintFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PRINTFLAG.toString(), arg);
	}
	/**
	 * Set search value for Pring flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPrintFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PRINTFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Pring flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPrintFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PRINTFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Pring flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPrintFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Movement.PRINTFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Pring flag
	 * @param arg Update value for Pring flag
	 */
	public void updatePrintFlag(String arg)
	{
		setUpdValue(_Prefix + Movement.PRINTFLAG.toString(), arg);
	}

	/**
	 * Set search value for Batch no (Schedule no)
	 * @param arg Batch no (Schedule no)<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BATCHNO.toString(), arg);
	}
	/**
	 * Set search value for Batch no (Schedule no)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BATCHNO.toString(), arg);
	}
	/**
	 *Set search value for  Batch no (Schedule no)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBatchNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.BATCHNO.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.BATCHNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Batch no (Schedule no)
	 * @param arg Update value for Batch no (Schedule no)
	 */
	public void updateBatchNo(String arg)
	{
		setUpdValue(_Prefix + Movement.BATCHNO.toString(), arg);
	}

	/**
	 * Set search value for Worker code
	 * @param arg Worker code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKERCODE.toString(), arg);
	}
	/**
	 * Set search value for Worker code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKERCODE.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.WORKERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Worker code
	 * @param arg Update value for Worker code
	 */
	public void updateWorkerCode(String arg)
	{
		setUpdValue(_Prefix + Movement.WORKERCODE.toString(), arg);
	}

	/**
	 * Set search value for Worker name
	 * @param arg Worker name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKERNAME.toString(), arg);
	}
	/**
	 * Set search value for Worker name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkerName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.WORKERNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.WORKERNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Worker name
	 * @param arg Update value for Worker name
	 */
	public void updateWorkerName(String arg)
	{
		setUpdValue(_Prefix + Movement.WORKERNAME.toString(), arg);
	}

	/**
	 * Set search value for Terminal no
	 * @param arg Terminal no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.TERMINALNO.toString(), arg);
	}
	/**
	 * Set search value for Terminal no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.TERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Terminal no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTerminalNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.TERMINALNO.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.TERMINALNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Terminal no
	 * @param arg Update value for Terminal no
	 */
	public void updateTerminalNo(String arg)
	{
		setUpdValue(_Prefix + Movement.TERMINALNO.toString(), arg);
	}

	/**
	 * Set search value for Retrieval worker code
	 * @param arg Retrieval worker code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalWorkerCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALWORKERCODE.toString(), arg);
	}
	/**
	 * Set search value for Retrieval worker code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalWorkerCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALWORKERCODE.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalWorkerCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALWORKERCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Retrieval worker code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalWorkerCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALWORKERCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Retrieval worker code
	 * @param arg Update value for Retrieval worker code
	 */
	public void updateRetrievalWorkerCode(String arg)
	{
		setUpdValue(_Prefix + Movement.RETRIEVALWORKERCODE.toString(), arg);
	}

	/**
	 * Set search value for Retrieval worker name
	 * @param arg Retrieval worker name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalWorkerName(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALWORKERNAME.toString(), arg);
	}
	/**
	 * Set search value for Retrieval worker name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalWorkerName(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALWORKERNAME.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalWorkerName(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALWORKERNAME.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Retrieval worker name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalWorkerName(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALWORKERNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Retrieval worker name
	 * @param arg Update value for Retrieval worker name
	 */
	public void updateRetrievalWorkerName(String arg)
	{
		setUpdValue(_Prefix + Movement.RETRIEVALWORKERNAME.toString(), arg);
	}

	/**
	 * Set search value for Retrieval Terminal no
	 * @param arg Retrieval Terminal no<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalTerminalNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALTERMINALNO.toString(), arg);
	}
	/**
	 * Set search value for Retrieval Terminal no
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalTerminalNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALTERMINALNO.toString(), arg);
	}
	/**
	 *Set search value for  Retrieval Terminal no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalTerminalNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALTERMINALNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Retrieval Terminal no
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalTerminalNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Movement.RETRIEVALTERMINALNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Retrieval Terminal no
	 * @param arg Update value for Retrieval Terminal no
	 */
	public void updateRetrievalTerminalNo(String arg)
	{
		setUpdValue(_Prefix + Movement.RETRIEVALTERMINALNO.toString(), arg);
	}

	/**
	 * Set search value for Registered date
	 * @param arg Registered date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.REGISTDATE.toString(), arg);
	}
	/**
	 * Set search value for Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered date
	 * @param arg Update value for Registered date
	 */
	public void updateRegistDate(Date arg)
	{
		setUpdValue(_Prefix + Movement.REGISTDATE.toString(), arg);
	}

	/**
	 * Set search value for Registered name
	 * @param arg Registered name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.REGISTPNAME.toString(), arg);
	}
	/**
	 * Set search value for Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered name
	 * @param arg Update value for Registered name
	 */
	public void updateRegistPname(String arg)
	{
		setUpdValue(_Prefix + Movement.REGISTPNAME.toString(), arg);
	}

	/**
	 * Set search value for Last update date
	 * @param arg Last update date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 * Set search value for Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update date
	 * @param arg Update value for Last update date
	 */
	public void updateLastUpdateDate(Date arg)
	{
		setUpdValue(_Prefix + Movement.LASTUPDATEDATE.toString(), arg);
	}

	/**
	 * Set search value for Last update name
	 * @param arg Last update name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 * Set search value for Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Movement.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Movement.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update name
	 * @param arg Update value for Last update name
	 */
	public void updateLastUpdatePname(String arg)
	{
		setUpdValue(_Prefix + Movement.LASTUPDATEPNAME.toString(), arg);
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
		return "$Id: MovementAlterKey.java,v 1.2 2006/11/09 07:51:48 suresh Exp $" ;
	}
}
