//$Id: PaletteAlterKey.java,v 1.2 2006/11/09 07:51:42 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Palette;

/**
 * Update key class for PALETTE use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:51:42 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class PaletteAlterKey
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
	public PaletteAlterKey()
	{
		super(new Palette()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for palette id
	 * @param arg palette id<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTEID.toString(), arg);
	}
	/**
	 * Set search value for palette id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTEID.toString(), arg);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTEID.toString(), arg, compcode);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for palette id
	 * @param arg Update value for palette id
	 */
	public void updatePaletteId(int arg)
	{
		setUpdValue(_Prefix + Palette.PALETTEID.toString(), arg);
	}

	/**
	 * Set search value for current station number
	 * @param arg current station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for current station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  current station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  current station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCurrentStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for current station number
	 * @param arg Update value for current station number
	 */
	public void updateCurrentStationNumber(String arg)
	{
		setUpdValue(_Prefix + Palette.CURRENTSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for warehouse station number
	 * @param arg warehouse station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWhStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for warehouse station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWhStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.WHSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  warehouse station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWhStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.WHSTATIONNUMBER.toString(), arg, compcode);
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
	public void setWhStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.WHSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for warehouse station number
	 * @param arg Update value for warehouse station number
	 */
	public void updateWhStationNumber(String arg)
	{
		setUpdValue(_Prefix + Palette.WHSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for palette type id
	 * @param arg palette type id<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeId(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTETYPEID.toString(), arg);
	}
	/**
	 * Set search value for palette type id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeId(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTETYPEID.toString(), arg);
	}
	/**
	 *Set search value for  palette type id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteTypeId(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTETYPEID.toString(), arg, compcode);
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
	public void setPaletteTypeId(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.PALETTETYPEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for palette type id
	 * @param arg Update value for palette type id
	 */
	public void updatePalettetTypeId(int arg)
	{
		setUpdValue(_Prefix + Palette.PALETTETYPEID.toString(), arg);
	}

	/**
	 * Set search value for stock status
	 * @param arg stock status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.STATUS.toString(), arg);
	}
	/**
	 * Set search value for stock status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.STATUS.toString(), arg);
	}
	/**
	 *Set search value for  stock status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.STATUS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  stock status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatus(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.STATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for stock status
	 * @param arg Update value for stock status
	 */
	public void updateStatus(int arg)
	{
		setUpdValue(_Prefix + Palette.STATUS.toString(), arg);
	}

	/**
	 * Set search value for allocation status
	 * @param arg allocation status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAllocation(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.ALLOCATION.toString(), arg);
	}
	/**
	 * Set search value for allocation status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAllocation(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.ALLOCATION.toString(), arg);
	}
	/**
	 *Set search value for  allocation status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAllocation(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.ALLOCATION.toString(), arg, compcode);
	}
	/**
	 *Set search value for  allocation status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAllocation(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.ALLOCATION.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for allocation status
	 * @param arg Update value for allocation status
	 */
	public void updateAllocation(int arg)
	{
		setUpdValue(_Prefix + Palette.ALLOCATION.toString(), arg);
	}

	/**
	 * Set search value for empty palette status
	 * @param arg empty palette status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmpty(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.EMPTY.toString(), arg);
	}
	/**
	 * Set search value for empty palette status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmpty(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.EMPTY.toString(), arg);
	}
	/**
	 *Set search value for  empty palette status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmpty(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.EMPTY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  empty palette status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEmpty(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.EMPTY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for empty palette status
	 * @param arg Update value for empty palette status
	 */
	public void updateEmpty(int arg)
	{
		setUpdValue(_Prefix + Palette.EMPTY.toString(), arg);
	}

	/**
	 * Set search value for updatation date
	 * @param arg updatation date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRefixDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.REFIXDATE.toString(), arg);
	}
	/**
	 * Set search value for updatation date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRefixDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.REFIXDATE.toString(), arg);
	}
	/**
	 *Set search value for  updatation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRefixDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.REFIXDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  updatation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRefixDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.REFIXDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for updatation date
	 * @param arg Update value for updatation date
	 */
	public void updateRefixDate(Date arg)
	{
		setUpdValue(_Prefix + Palette.REFIXDATE.toString(), arg);
	}

	/**
	 * Set search value for filling rate
	 * @param arg filling rate<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRate(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.RATE.toString(), arg);
	}
	/**
	 * Set search value for filling rate
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRate(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.RATE.toString(), arg);
	}
	/**
	 *Set search value for  filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRate(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.RATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRate(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.RATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for filling rate
	 * @param arg Update value for filling rate
	 */
	public void updateRate(int arg)
	{
		setUpdValue(_Prefix + Palette.RATE.toString(), arg);
	}

	/**
	 * Set search value for load height
	 * @param arg load height<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.HEIGHT.toString(), arg);
	}
	/**
	 * Set search value for load height
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.HEIGHT.toString(), arg);
	}
	/**
	 *Set search value for  load height
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.HEIGHT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  load height
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setHeight(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.HEIGHT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for load height
	 * @param arg Update value for load height
	 */
	public void updateHeight(int arg)
	{
		setUpdValue(_Prefix + Palette.HEIGHT.toString(), arg);
	}

	/**
	 * Set search value for palette info
	 * @param arg palette info<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Palette.BCDATA.toString(), arg);
	}
	/**
	 * Set search value for palette info
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Palette.BCDATA.toString(), arg);
	}
	/**
	 *Set search value for  palette info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Palette.BCDATA.toString(), arg, compcode);
	}
	/**
	 *Set search value for  palette info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBcData(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Palette.BCDATA.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for palette info
	 * @param arg Update value for palette info
	 */
	public void updateBcData(String arg)
	{
		setUpdValue(_Prefix + Palette.BCDATA.toString(), arg);
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
		return "$Id: PaletteAlterKey.java,v 1.2 2006/11/09 07:51:42 suresh Exp $" ;
	}
}
