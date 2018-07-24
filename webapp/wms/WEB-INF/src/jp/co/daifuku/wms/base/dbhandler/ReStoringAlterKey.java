//$Id: ReStoringAlterKey.java,v 1.4 2006/11/20 04:28:04 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.ReStoring;

/**
 * Update key class for RESTORING use
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
 * @version $Revision: 1.4 $, $Date: 2006/11/20 04:28:04 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ReStoringAlterKey
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
	public ReStoringAlterKey()
	{
		super(new ReStoring()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for work number
	 * @param arg work number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WORKNUMBER.toString(), arg);
	}
	/**
	 * Set search value for work number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WORKNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WORKNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WORKNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for work number
	 * @param arg Update value for work number
	 */
	public void updateWorkNumber(String arg)
	{
		setUpdValue(_Prefix + ReStoring.WORKNUMBER.toString(), arg);
	}

	/**
	 * Set search value for creation date
	 * @param arg creation date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.CREATEDATE.toString(), arg);
	}
	/**
	 * Set search value for creation date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.CREATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.CREATEDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.CREATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for creation date
	 * @param arg Update value for creation date
	 */
	public void updateCreateDate(Date arg)
	{
		setUpdValue(_Prefix + ReStoring.CREATEDATE.toString(), arg);
	}

	/**
	 * Set search value for warehouse station number
	 * @param arg warehouse station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWHStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for warehouse station number
	 * @param arg Update value for warehouse station number
	 */
	public void updateWHStationNumber(String arg)
	{
		setUpdValue(_Prefix + ReStoring.WHSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for station number
	 * @param arg station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.STATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.STATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.STATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.STATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for station number
	 * @param arg Update value for station number
	 */
	public void updateStationNumber(String arg)
	{
		setUpdValue(_Prefix + ReStoring.STATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for palette type id
	 * @param arg palette type id<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeID(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PALETTETYPEID.toString(), arg);
	}
	/**
	 * Set search value for palette type id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeID(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PALETTETYPEID.toString(), arg);
	}
	/**
	 *Set search value for  palette type id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeID(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PALETTETYPEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  palette type id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeID(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PALETTETYPEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for palette type id
	 * @param arg Update value for palette type id
	 */
	public void updatePaletteTypeID(int arg)
	{
		setUpdValue(_Prefix + ReStoring.PALETTETYPEID.toString(), arg);
	}

	/**
	 * Set search value for item code
	 * @param arg item code<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.ITEMCODE.toString(), arg);
	}
	/**
	 * Set search value for item code
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.ITEMCODE.toString(), arg);
	}
	/**
	 *Set search value for  item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.ITEMCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  item code
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setItemCode(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.ITEMCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for item code
	 * @param arg Update value for item code
	 */
	public void updateItemCode(String arg)
	{
		setUpdValue(_Prefix + ReStoring.ITEMCODE.toString(), arg);
	}

	/**
	 * Set search value for lot number
	 * @param arg lot number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LOTNUMBER.toString(), arg);
	}
	/**
	 * Set search value for lot number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LOTNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LOTNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  lot number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLotNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LOTNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for lot number
	 * @param arg Update value for lot number
	 */
	public void updateLotNumber(String arg)
	{
		setUpdValue(_Prefix + ReStoring.LOTNUMBER.toString(), arg);
	}

	/**
	 * Set search value for stock qty
	 * @param arg stock qty<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setQuantity(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.QUANTITY.toString(), arg);
	}
	/**
	 * Set search value for stock qty
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setQuantity(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.QUANTITY.toString(), arg);
	}
	/**
	 *Set search value for  stock qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setQuantity(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.QUANTITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  stock qty
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setQuantity(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.QUANTITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for stock qty
	 * @param arg Update value for stock qty
	 */
	public void updateQuantity(int arg)
	{
		setUpdValue(_Prefix + ReStoring.QUANTITY.toString(), arg);
	}

	/**
	 * Set search value for storage date
	 * @param arg storage date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INCOMMINGDATE.toString(), arg);
	}
	/**
	 * Set search value for storage date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INCOMMINGDATE.toString(), arg);
	}
	/**
	 *Set search value for  storage date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INCOMMINGDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  storage date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInCommingDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INCOMMINGDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for storage date
	 * @param arg Update value for storage date
	 */
	public void updateInCommingDate(String arg)
	{
		setUpdValue(_Prefix + ReStoring.INCOMMINGDATE.toString(), arg);
	}

	/**
	 * Set search value for inventory check date
	 * @param arg inventory check date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), arg);
	}
	/**
	 * Set search value for inventory check date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), arg);
	}
	/**
	 *Set search value for  inventory check date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  inventory check date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setInventoryCheckDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for inventory check date
	 * @param arg Update value for inventory check date
	 */
	public void updateInventoryCheckDate(String arg)
	{
		setUpdValue(_Prefix + ReStoring.INVENTORYCHECKDATE.toString(), arg);
	}

	/**
	 * Set search value for last modified date
	 * @param arg last modified date<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastConfirmDate(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), arg);
	}
	/**
	 * Set search value for last modified date
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastConfirmDate(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), arg);
	}
	/**
	 *Set search value for  last modified date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastConfirmDate(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last modified date
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastConfirmDate(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for last modified date
	 * @param arg Update value for last modified date
	 */
	public void updateLastConfirmDate(String arg)
	{
		setUpdValue(_Prefix + ReStoring.LASTCONFIRMDATE.toString(), arg);
	}

	/**
	 * Set search value for re-storage type
	 * @param arg re-storage type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.RESTORING.toString(), arg);
	}
	/**
	 * Set search value for re-storage type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.RESTORING.toString(), arg);
	}
	/**
	 *Set search value for  re-storage type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.RESTORING.toString(), arg, compcode);
	}
	/**
	 *Set search value for  re-storage type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoring(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.RESTORING.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for re-storage type
	 * @param arg Update value for re-storage type
	 */
	public void updateReStoring(int arg)
	{
		setUpdValue(_Prefix + ReStoring.RESTORING.toString(), arg);
	}

	/**
	 * Set search value for barcode info
	 * @param arg barcode info<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.BCDATA.toString(), arg);
	}
	/**
	 * Set search value for barcode info
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.BCDATA.toString(), arg);
	}
	/**
	 *Set search value for  barcode info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.BCDATA.toString(), arg, compcode);
	}
	/**
	 *Set search value for  barcode info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.BCDATA.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for barcode info
	 * @param arg Update value for barcode info
	 */
	public void updateBcData(String arg)
	{
		setUpdValue(_Prefix + ReStoring.BCDATA.toString(), arg);
	}

	/**
	 * Set search value for process flag
	 * @param arg process flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProcessingFlag(int arg) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PROCESSINGFLAG.toString(), arg);
	}
	/**
	 * Set search value for process flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProcessingFlag(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PROCESSINGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  process flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProcessingFlag(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PROCESSINGFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  process flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProcessingFlag(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + ReStoring.PROCESSINGFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for process flag
	 * @param arg Update value for process flag
	 */
	public void updateProcessingFlag(int arg)
	{
		setUpdValue(_Prefix + ReStoring.PROCESSINGFLAG.toString(), arg);
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
		return "$Id: ReStoringAlterKey.java,v 1.4 2006/11/20 04:28:04 suresh Exp $" ;
	}
}
