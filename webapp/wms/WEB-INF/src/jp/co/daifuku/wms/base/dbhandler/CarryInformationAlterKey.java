//$Id: CarryInformationAlterKey.java,v 1.2 2006/11/09 07:53:15 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.CarryInformation;

/**
 * Update key class for CARRYINFO use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:53:15 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class CarryInformationAlterKey
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
	public CarryInformationAlterKey()
	{
		super(new CarryInformation()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for carry key
	 * @param arg carry key<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKEY.toString(), arg);
	}
	/**
	 * Set search value for carry key
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKEY.toString(), arg);
	}
	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKEY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  carry key
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKey(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKEY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for carry key
	 * @param arg Update value for carry key
	 */
	public void updateCarryKey(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.CARRYKEY.toString(), arg);
	}

	/**
	 * Set search value for palette id
	 * @param arg palette id<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PALETTEID.toString(), arg);
	}
	/**
	 * Set search value for palette id
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PALETTEID.toString(), arg);
	}
	/**
	 *Set search value for  palette id
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPaletteId(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PALETTEID.toString(), arg, compcode);
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
		setValue(_Prefix + CarryInformation.PALETTEID.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for palette id
	 * @param arg Update value for palette id
	 */
	public void updatePaletteId(int arg)
	{
		setUpdValue(_Prefix + CarryInformation.PALETTEID.toString(), arg);
	}

	/**
	 * Set search value for creation date
	 * @param arg creation date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CREATEDATE.toString(), arg);
	}
	/**
	 * Set search value for creation date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CREATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  creation date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCreateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CREATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + CarryInformation.CREATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for creation date
	 * @param arg Update value for creation date
	 */
	public void updateCreateDate(Date arg)
	{
		setUpdValue(_Prefix + CarryInformation.CREATEDATE.toString(), arg);
	}

	/**
	 * Set search value for work type
	 * @param arg work type<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKTYPE.toString(), arg);
	}
	/**
	 * Set search value for work type
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKTYPE.toString(), arg);
	}
	/**
	 *Set search value for  work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKTYPE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  work type
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkType(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKTYPE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for work type
	 * @param arg Update value for work type
	 */
	public void updateWorkType(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.WORKTYPE.toString(), arg);
	}

	/**
	 * Set search value for picking group
	 * @param arg picking group<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setGroupNumber(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.GROUPNUMBER.toString(), arg);
	}
	/**
	 * Set search value for picking group
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setGroupNumber(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.GROUPNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  picking group
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setGroupNumber(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.GROUPNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  picking group
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setGroupNumber(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.GROUPNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for picking group
	 * @param arg Update value for picking group
	 */
	public void updateGroupNumber(int arg)
	{
		setUpdValue(_Prefix + CarryInformation.GROUPNUMBER.toString(), arg);
	}

	/**
	 * Set search value for conveyance status
	 * @param arg conveyance status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCmdStatus(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CMDSTATUS.toString(), arg);
	}
	/**
	 * Set search value for conveyance status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCmdStatus(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CMDSTATUS.toString(), arg);
	}
	/**
	 *Set search value for  conveyance status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCmdStatus(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CMDSTATUS.toString(), arg, compcode);
	}
	/**
	 *Set search value for  conveyance status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCmdStatus(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CMDSTATUS.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for conveyance status
	 * @param arg Update value for conveyance status
	 */
	public void updateCmdStatus(int arg)
	{
		setUpdValue(_Prefix + CarryInformation.CMDSTATUS.toString(), arg);
	}

	/**
	 * Set search value for priority type
	 * @param arg priority type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PRIORITY.toString(), arg);
	}
	/**
	 * Set search value for priority type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PRIORITY.toString(), arg);
	}
	/**
	 *Set search value for  priority type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PRIORITY.toString(), arg, compcode);
	}
	/**
	 *Set search value for  priority type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPriority(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.PRIORITY.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for priority type
	 * @param arg Update value for priority type
	 */
	public void updatePriority(int arg)
	{
		setUpdValue(_Prefix + CarryInformation.PRIORITY.toString(), arg);
	}

	/**
	 * Set search value for restorage flag
	 * @param arg restorage flag<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringFlag(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RESTORINGFLAG.toString(), arg);
	}
	/**
	 * Set search value for restorage flag
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringFlag(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RESTORINGFLAG.toString(), arg);
	}
	/**
	 *Set search value for  restorage flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringFlag(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RESTORINGFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  restorage flag
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setReStoringFlag(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RESTORINGFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for restorage flag
	 * @param arg Update value for restorage flag
	 */
	public void updateReStoringFlag(int arg)
	{
		setUpdValue(_Prefix + CarryInformation.RESTORINGFLAG.toString(), arg);
	}

	/**
	 * Set search value for conveyance type
	 * @param arg conveyance type<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKind(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKIND.toString(), arg);
	}
	/**
	 * Set search value for conveyance type
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKind(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKIND.toString(), arg);
	}
	/**
	 *Set search value for  conveyance type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKind(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKIND.toString(), arg, compcode);
	}
	/**
	 *Set search value for  conveyance type
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCarryKind(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CARRYKIND.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for conveyance type
	 * @param arg Update value for conveyance type
	 */
	public void updateCarryKind(int arg)
	{
		setUpdValue(_Prefix + CarryInformation.CARRYKIND.toString(), arg);
	}

	/**
	 * Set search value for picking location number
	 * @param arg picking location number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for picking location number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  picking location number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  picking location number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for picking location number
	 * @param arg Update value for picking location number
	 */
	public void updateRetrievalStationNumber(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.RETRIEVALSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for picking detail
	 * @param arg picking detail<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalDetail(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), arg);
	}
	/**
	 * Set search value for picking detail
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalDetail(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), arg);
	}
	/**
	 *Set search value for  picking detail
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalDetail(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), arg, compcode);
	}
	/**
	 *Set search value for  picking detail
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRetrievalDetail(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for picking detail
	 * @param arg Update value for picking detail
	 */
	public void updateRetrievalDetail(int arg)
	{
		setUpdValue(_Prefix + CarryInformation.RETRIEVALDETAIL.toString(), arg);
	}

	/**
	 * Set search value for work number
	 * @param arg work number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKNUMBER.toString(), arg);
	}
	/**
	 * Set search value for work number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  work number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setWorkNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.WORKNUMBER.toString(), arg, compcode);
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
		setValue(_Prefix + CarryInformation.WORKNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for work number
	 * @param arg Update value for work number
	 */
	public void updateWorkNumber(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.WORKNUMBER.toString(), arg);
	}

	/**
	 * Set search value for source station number
	 * @param arg source station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSourceStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for source station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSourceStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  source station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSourceStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  source station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setSourceStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for source station number
	 * @param arg Update value for source station number
	 */
	public void updateSourceStationNumber(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.SOURCESTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for destination station number
	 * @param arg destination station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDestStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for destination station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDestStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  destination station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDestStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  destination station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setDestStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for destination station number
	 * @param arg Update value for destination station number
	 */
	public void updateDestStationNumber(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.DESTSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for arrival date
	 * @param arg arrival date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ARRIVALDATE.toString(), arg);
	}
	/**
	 * Set search value for arrival date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ARRIVALDATE.toString(), arg);
	}
	/**
	 *Set search value for  arrival date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ARRIVALDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  arrival date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setArrivalDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ARRIVALDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for arrival date
	 * @param arg Update value for arrival date
	 */
	public void updateArrivalDate(Date arg)
	{
		setUpdValue(_Prefix + CarryInformation.ARRIVALDATE.toString(), arg);
	}

	/**
	 * Set search value for control info
	 * @param arg control info<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControlInfo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CONTROLINFO.toString(), arg);
	}
	/**
	 * Set search value for control info
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControlInfo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CONTROLINFO.toString(), arg);
	}
	/**
	 *Set search value for  control info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControlInfo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CONTROLINFO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  control info
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setControlInfo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CONTROLINFO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for control info
	 * @param arg Update value for control info
	 */
	public void updateControlInfo(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.CONTROLINFO.toString(), arg);
	}

	/**
	 * Set search value for cancel request status
	 * @param arg cancel request status<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequest(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUEST.toString(), arg);
	}
	/**
	 * Set search value for cancel request status
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequest(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUEST.toString(), arg);
	}
	/**
	 *Set search value for  cancel request status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequest(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUEST.toString(), arg, compcode);
	}
	/**
	 *Set search value for  cancel request status
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequest(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUEST.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for cancel request status
	 * @param arg Update value for cancel request status
	 */
	public void updateCancelRequest(int arg)
	{
		setUpdValue(_Prefix + CarryInformation.CANCELREQUEST.toString(), arg);
	}

	/**
	 * Set search value for cancellation request date
	 * @param arg cancellation request date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequestDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), arg);
	}
	/**
	 * Set search value for cancellation request date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequestDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), arg);
	}
	/**
	 *Set search value for  cancellation request date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequestDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  cancellation request date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setCancelRequestDate(Date arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for cancellation request date
	 * @param arg Update value for cancellation request date
	 */
	public void updateCancelRequestDate(Date arg)
	{
		setUpdValue(_Prefix + CarryInformation.CANCELREQUESTDATE.toString(), arg);
	}

	/**
	 * Set search value for schedule number
	 * @param arg schedule number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 * Set search value for schedule number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), arg);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  schedule number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setScheduleNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for schedule number
	 * @param arg Update value for schedule number
	 */
	public void updateScheduleNumber(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.SCHEDULENUMBER.toString(), arg);
	}

	/**
	 * Set search value for aisle station number
	 * @param arg aisle station number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for aisle station number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  aisle station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  aisle station number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for aisle station number
	 * @param arg Update value for aisle station number
	 */
	public void updateAisleStationNumber(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.AISLESTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for last sation number
	 * @param arg last sation number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndStationNumber(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), arg);
	}
	/**
	 * Set search value for last sation number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndStationNumber(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), arg);
	}
	/**
	 *Set search value for  last sation number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndStationNumber(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), arg, compcode);
	}
	/**
	 *Set search value for  last sation number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setEndStationNumber(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for last sation number
	 * @param arg Update value for last sation number
	 */
	public void updateEndStationNumber(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.ENDSTATIONNUMBER.toString(), arg);
	}

	/**
	 * Set search value for error code
	 * @param arg error code<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(int arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ERRORCODE.toString(), arg);
	}
	/**
	 * Set search value for error code
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ERRORCODE.toString(), arg);
	}
	/**
	 *Set search value for  error code
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ERRORCODE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  error code
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setErrorCode(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.ERRORCODE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for error code
	 * @param arg Update value for error code
	 */
	public void updateErrorCode(int arg)
	{
		setUpdValue(_Prefix + CarryInformation.ERRORCODE.toString(), arg);
	}

	/**
	 * Set search value for maintenance terminal number
	 * @param arg maintenance terminal number<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaintenanceTerminal(String arg) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), arg);
	}
	/**
	 * Set search value for maintenance terminal number
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaintenanceTerminal(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), arg);
	}
	/**
	 *Set search value for  maintenance terminal number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaintenanceTerminal(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), arg, compcode);
	}
	/**
	 *Set search value for  maintenance terminal number
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMaintenanceTerminal(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for maintenance terminal number
	 * @param arg Update value for maintenance terminal number
	 */
	public void updateMaintenanceTerminal(String arg)
	{
		setUpdValue(_Prefix + CarryInformation.MAINTENANCETERMINAL.toString(), arg);
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
		return "$Id: CarryInformationAlterKey.java,v 1.2 2006/11/09 07:53:15 suresh Exp $" ;
	}
}
