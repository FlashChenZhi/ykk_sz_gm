//$Id: WareNaviSystemAlterKey.java,v 1.3 2006/11/13 04:32:55 suresh Exp $
package jp.co.daifuku.wms.base.dbhandler;

/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.AbstractSQLAlterKey;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

/**
 * Update key class for WARENAVI_SYSTEM use
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


public class WareNaviSystemAlterKey
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
	public WareNaviSystemAlterKey()
	{
		super(new WareNaviSystem()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for System No
	 * @param arg System No<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSystemNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SYSTEMNO.toString(), arg);
	}
	/**
	 * Set search value for System No
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
	 * Set update value for System No
	 * @param arg Update value for System No
	 */
	public void updateSystemNo(int arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.SYSTEMNO.toString(), arg);
	}

	/**
	 * Set search value for Work date
	 * @param arg Work date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.WORKDATE.toString(), arg);
	}
	/**
	 * Set search value for Work date
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
	 * Set update value for Work date
	 * @param arg Update value for Work date
	 */
	public void updateWorkDate(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.WORKDATE.toString(), arg);
	}

	/**
	 * Set search value for DC Mode
	 * @param arg DC Mode<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDcOperation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.DCOPERATION.toString(), arg);
	}
	/**
	 * Set search value for DC Mode
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
	 * Set update value for DC Mode
	 * @param arg Update value for DC Mode
	 */
	public void updateDcOperation(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.DCOPERATION.toString(), arg);
	}

	/**
	 * Set search value for TC Mode
	 * @param arg TC Mode<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setTcOperation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.TCOPERATION.toString(), arg);
	}
	/**
	 * Set search value for TC Mode
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
	 * Set update value for TC Mode
	 * @param arg Update value for TC Mode
	 */
	public void updateTcOperation(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.TCOPERATION.toString(), arg);
	}

	/**
	 * Set search value for CrossDC enabled
	 * @param arg CrossDC enabled<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCrossdockOperation(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.CROSSDOCKOPERATION.toString(), arg);
	}
	/**
	 * Set search value for CrossDC enabled
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
	 * Set update value for CrossDC enabled
	 * @param arg Update value for CrossDC enabled
	 */
	public void updateCrossdockOperation(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.CROSSDOCKOPERATION.toString(), arg);
	}

	/**
	 * Set search value for Result hold period
	 * @param arg Result hold period<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setResultHoldPeriod(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.RESULTHOLDPERIOD.toString(), arg);
	}
	/**
	 * Set search value for Result hold period
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
	 * Set update value for Result hold period
	 * @param arg Update value for Result hold period
	 */
	public void updateResultHoldPeriod(int arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.RESULTHOLDPERIOD.toString(), arg);
	}

	/**
	 * Set search value for Plan hold period
	 * @param arg Plan hold period<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanHoldPeriod(int arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.PLANHOLDPERIOD.toString(), arg);
	}
	/**
	 * Set search value for Plan hold period
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
	 * Set update value for Plan hold period
	 * @param arg Update value for Plan hold period
	 */
	public void updatePlanHoldPeriod(int arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.PLANHOLDPERIOD.toString(), arg);
	}

	/**
	 * Set search value for Receiving package
	 * @param arg Receiving package<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInstockPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.INSTOCKPACK.toString(), arg);
	}
	/**
	 * Set search value for Receiving package
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
	 * Set update value for Receiving package
	 * @param arg Update value for Receiving package
	 */
	public void updateInstockPack(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.INSTOCKPACK.toString(), arg);
	}

	/**
	 * Set search value for Storage package
	 * @param arg Storage package<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStoragePack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.STORAGEPACK.toString(), arg);
	}
	/**
	 * Set search value for Storage package
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
	 * Set update value for Storage package
	 * @param arg Update value for Storage package
	 */
	public void updateStoragePack(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.STORAGEPACK.toString(), arg);
	}

	/**
	 * Set search value for Retrieval package
	 * @param arg Retrieval package<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.RETRIEVALPACK.toString(), arg);
	}
	/**
	 * Set search value for Retrieval package
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
	 * Set update value for Retrieval package
	 * @param arg Update value for Retrieval package
	 */
	public void updateRetrievalPack(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.RETRIEVALPACK.toString(), arg);
	}

	/**
	 * Set search value for Sorting package
	 * @param arg Sorting package<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSortingPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SORTINGPACK.toString(), arg);
	}
	/**
	 * Set search value for Sorting package
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
	 * Set update value for Sorting package
	 * @param arg Update value for Sorting package
	 */
	public void updateSortingPack(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.SORTINGPACK.toString(), arg);
	}

	/**
	 * Set search value for Shipping package
	 * @param arg Shipping package<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setShippingPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.SHIPPINGPACK.toString(), arg);
	}
	/**
	 * Set search value for Shipping package
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
	 * Set update value for Shipping package
	 * @param arg Update value for Shipping package
	 */
	public void updateShippingPack(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.SHIPPINGPACK.toString(), arg);
	}

	/**
	 * Set search value for Stock pack
	 * @param arg Stock pack<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStockPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.STOCKPACK.toString(), arg);
	}
	/**
	 * Set search value for Stock pack
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
	 * Set update value for Stock pack
	 * @param arg Update value for Stock pack
	 */
	public void updateStockPack(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.STOCKPACK.toString(), arg);
	}

	/**
	 * Set search value for CrossDC package
	 * @param arg CrossDC package<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCrossdockPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.CROSSDOCKPACK.toString(), arg);
	}
	/**
	 * Set search value for CrossDC package
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
	 * Set update value for CrossDC package
	 * @param arg Update value for CrossDC package
	 */
	public void updateCrossdockPack(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.CROSSDOCKPACK.toString(), arg);
	}

	/**
	 * Set search value for IDM Pack
	 * @param arg IDM Pack<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setIdmPack(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.IDMPACK.toString(), arg);
	}
	/**
	 * Set search value for IDM Pack
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
	 * Set update value for IDM Pack
	 * @param arg Update value for IDM Pack
	 */
	public void updateIdmPack(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.IDMPACK.toString(), arg);
	}

	/**
	 * Set search value for Daily update
	 * @param arg Daily update<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDailyUpdate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.DAILYUPDATE.toString(), arg);
	}
	/**
	 * Set search value for Daily update
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
	 * Set update value for Daily update
	 * @param arg Update value for Daily update
	 */
	public void updateDailyUpdate(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.DAILYUPDATE.toString(), arg);
	}

	/**
	 * Set search value for Loading data
	 * @param arg Loading data<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLoadData(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.LOADDATA.toString(), arg);
	}
	/**
	 * Set search value for Loading data
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
	 * Set update value for Loading data
	 * @param arg Update value for Loading data
	 */
	public void updateLoadData(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.LOADDATA.toString(), arg);
	}

	/**
	 * Set search value for Generating report
	 * @param arg Generating report<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReportData(String arg) throws ReadWriteException
	{
		setValue(_Prefix + WareNaviSystem.REPORTDATA.toString(), arg);
	}
	/**
	 * Set search value for Generating report
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
	 * Set update value for Generating report
	 * @param arg Update value for Generating report
	 */
	public void updateReportData(String arg)
	{
		setUpdValue(_Prefix + WareNaviSystem.REPORTDATA.toString(), arg);
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
		return "$Id: WareNaviSystemAlterKey.java,v 1.3 2006/11/13 04:32:55 suresh Exp $" ;
	}
}
