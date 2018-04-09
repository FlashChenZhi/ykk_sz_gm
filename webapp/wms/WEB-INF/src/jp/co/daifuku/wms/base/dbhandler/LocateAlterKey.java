//$Id: LocateAlterKey.java,v 1.2 2006/11/09 07:52:52 suresh Exp $
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
import jp.co.daifuku.wms.base.entity.Locate;

/**
 * Update key class for LOCATE use
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
 * @version $Revision: 1.2 $, $Date: 2006/11/09 07:52:52 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class LocateAlterKey
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
	public LocateAlterKey()
	{
		super(new Locate()) ;
		_Prefix = getTableName() + ".";
	}

	//------------------------------------------------------------
	// accessors
	//------------------------------------------------------------

	/**
	 * Set search value for Area No
	 * @param arg Area No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AREANO.toString(), arg);
	}
	/**
	 * Set search value for Area No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AREANO.toString(), arg);
	}
	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AREANO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Area No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAreaNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AREANO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Area No
	 * @param arg Update value for Area No
	 */
	public void updateAreaNo(String arg)
	{
		setUpdValue(_Prefix + Locate.AREANO.toString(), arg);
	}

	/**
	 * Set search value for Location No (Block No)
	 * @param arg Location No (Block No)<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LOCATIONNO.toString(), arg);
	}
	/**
	 * Set search value for Location No (Block No)
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LOCATIONNO.toString(), arg);
	}
	/**
	 *Set search value for  Location No (Block No)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LOCATIONNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Location No (Block No)
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLocationNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LOCATIONNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Location No (Block No)
	 * @param arg Update value for Location No (Block No)
	 */
	public void updateLocationNo(String arg)
	{
		setUpdValue(_Prefix + Locate.LOCATIONNO.toString(), arg);
	}

	/**
	 * Set search value for Aisle No
	 * @param arg Aisle No<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNo(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AISLENO.toString(), arg);
	}
	/**
	 * Set search value for Aisle No
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNo(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AISLENO.toString(), arg);
	}
	/**
	 *Set search value for  Aisle No
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNo(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AISLENO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Aisle No
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setAisleNo(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.AISLENO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Aisle No
	 * @param arg Update value for Aisle No
	 */
	public void updateAisleNo(int arg)
	{
		setUpdValue(_Prefix + Locate.AISLENO.toString(), arg);
	}

	/**
	 * Set search value for Bank No
	 * @param arg Bank No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBankNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BANKNO.toString(), arg);
	}
	/**
	 * Set search value for Bank No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBankNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BANKNO.toString(), arg);
	}
	/**
	 *Set search value for  Bank No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBankNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BANKNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Bank No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBankNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BANKNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Bank No
	 * @param arg Update value for Bank No
	 */
	public void updateBankNo(String arg)
	{
		setUpdValue(_Prefix + Locate.BANKNO.toString(), arg);
	}

	/**
	 * Set search value for Bay No
	 * @param arg Bay No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBayNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BAYNO.toString(), arg);
	}
	/**
	 * Set search value for Bay No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBayNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BAYNO.toString(), arg);
	}
	/**
	 *Set search value for  Bay No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBayNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BAYNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Bay No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setBayNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.BAYNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Bay No
	 * @param arg Update value for Bay No
	 */
	public void updateBayNo(String arg)
	{
		setUpdValue(_Prefix + Locate.BAYNO.toString(), arg);
	}

	/**
	 * Set search value for Level No
	 * @param arg Level No<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLevelNo(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LEVELNO.toString(), arg);
	}
	/**
	 * Set search value for Level No
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLevelNo(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LEVELNO.toString(), arg);
	}
	/**
	 *Set search value for  Level No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLevelNo(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LEVELNO.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Level No
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLevelNo(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LEVELNO.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Level No
	 * @param arg Update value for Level No
	 */
	public void updateLevelNo(String arg)
	{
		setUpdValue(_Prefix + Locate.LEVELNO.toString(), arg);
	}

	/**
	 * Set search value for Status flag
	 * @param arg Status flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.STATUSFLAG.toString(), arg);
	}
	/**
	 * Set search value for Status flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.STATUSFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Status flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setStatusFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.STATUSFLAG.toString(), arg, compcode);
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
		setValue(_Prefix + Locate.STATUSFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Status flag
	 * @param arg Update value for Status flag
	 */
	public void updateStatusFlag(String arg)
	{
		setUpdValue(_Prefix + Locate.STATUSFLAG.toString(), arg);
	}

	/**
	 * Set search value for Prohibition flag
	 * @param arg Prohibition flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProhibitionFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PROHIBITIONFLAG.toString(), arg);
	}
	/**
	 * Set search value for Prohibition flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProhibitionFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PROHIBITIONFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Prohibition flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProhibitionFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PROHIBITIONFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Prohibition flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setProhibitionFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PROHIBITIONFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Prohibition flag
	 * @param arg Update value for Prohibition flag
	 */
	public void updateProhibitionFlag(String arg)
	{
		setUpdValue(_Prefix + Locate.PROHIBITIONFLAG.toString(), arg);
	}

	/**
	 * Set search value for Filling rate
	 * @param arg Filling rate<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFillingRate(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.FILLINGRATE.toString(), arg);
	}
	/**
	 * Set search value for Filling rate
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFillingRate(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.FILLINGRATE.toString(), arg);
	}
	/**
	 *Set search value for  Filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFillingRate(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.FILLINGRATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setFillingRate(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.FILLINGRATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Filling rate
	 * @param arg Update value for Filling rate
	 */
	public void updateFillingRate(int arg)
	{
		setUpdValue(_Prefix + Locate.FILLINGRATE.toString(), arg);
	}

	/**
	 * Set search value for Plan filling rate
	 * @param arg Plan filling rate<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanFillingRate(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PLANFILLINGRATE.toString(), arg);
	}
	/**
	 * Set search value for Plan filling rate
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanFillingRate(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PLANFILLINGRATE.toString(), arg);
	}
	/**
	 *Set search value for  Plan filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanFillingRate(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PLANFILLINGRATE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Plan filling rate
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setPlanFillingRate(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.PLANFILLINGRATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Plan filling rate
	 * @param arg Update value for Plan filling rate
	 */
	public void updatePlanFillingRate(int arg)
	{
		setUpdValue(_Prefix + Locate.PLANFILLINGRATE.toString(), arg);
	}

	/**
	 * Set search value for Mixed load count
	 * @param arg Mixed load count<br>
	 * Set search value for Integer
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMixedLoadCnt(int arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.MIXEDLOADCNT.toString(), arg);
	}
	/**
	 * Set search value for Mixed load count
	 * @param arg Integer array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMixedLoadCnt(int arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.MIXEDLOADCNT.toString(), arg);
	}
	/**
	 *Set search value for  Mixed load count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMixedLoadCnt(int arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.MIXEDLOADCNT.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Mixed load count
	 * @param arg Set search value for Integer
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setMixedLoadCnt(int arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.MIXEDLOADCNT.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Mixed load count
	 * @param arg Update value for Mixed load count
	 */
	public void updateMixedLoadCnt(int arg)
	{
		setUpdValue(_Prefix + Locate.MIXEDLOADCNT.toString(), arg);
	}

	/**
	 * Set search value for Zone
	 * @param arg Zone<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZone(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.ZONE.toString(), arg);
	}
	/**
	 * Set search value for Zone
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZone(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.ZONE.toString(), arg);
	}
	/**
	 *Set search value for  Zone
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZone(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.ZONE.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Zone
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setZone(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.ZONE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Zone
	 * @param arg Update value for Zone
	 */
	public void updateZone(String arg)
	{
		setUpdValue(_Prefix + Locate.ZONE.toString(), arg);
	}

	/**
	 * Set search value for Registration flag
	 * @param arg Registration flag<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistFlag(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTFLAG.toString(), arg);
	}
	/**
	 * Set search value for Registration flag
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistFlag(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTFLAG.toString(), arg);
	}
	/**
	 *Set search value for  Registration flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistFlag(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTFLAG.toString(), arg, compcode);
	}
	/**
	 *Set search value for  Registration flag
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @param left_paren left parenthesis
	 * @param right_paren right parenthesis
	 * @param and_or AND, OR compare condition
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistFlag(String arg, String compcode, String left_paren, String right_paren, String and_or) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTFLAG.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registration flag
	 * @param arg Update value for Registration flag
	 */
	public void updateRegistFlag(String arg)
	{
		setUpdValue(_Prefix + Locate.REGISTFLAG.toString(), arg);
	}

	/**
	 * Set search value for Registered date
	 * @param arg Registered date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTDATE.toString(), arg);
	}
	/**
	 * Set search value for Registered date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTDATE.toString(), arg);
	}
	/**
	 *Set search value for  Registered date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Locate.REGISTDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered date
	 * @param arg Update value for Registered date
	 */
	public void updateRegistDate(Date arg)
	{
		setUpdValue(_Prefix + Locate.REGISTDATE.toString(), arg);
	}

	/**
	 * Set search value for Registered name
	 * @param arg Registered name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTPNAME.toString(), arg);
	}
	/**
	 * Set search value for Registered name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Registered name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setRegistPname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.REGISTPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Locate.REGISTPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Registered name
	 * @param arg Update value for Registered name
	 */
	public void updateRegistPname(String arg)
	{
		setUpdValue(_Prefix + Locate.REGISTPNAME.toString(), arg);
	}

	/**
	 * Set search value for Last update date
	 * @param arg Last update date<br>
	 * Set search value for Date
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 * Set search value for Last update date
	 * @param arg Date array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEDATE.toString(), arg);
	}
	/**
	 *Set search value for  Last update date
	 * @param arg Set search value for Date
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdateDate(Date arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEDATE.toString(), arg, compcode);
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
		setValue(_Prefix + Locate.LASTUPDATEDATE.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update date
	 * @param arg Update value for Last update date
	 */
	public void updateLastUpdateDate(Date arg)
	{
		setUpdValue(_Prefix + Locate.LASTUPDATEDATE.toString(), arg);
	}

	/**
	 * Set search value for Last update name
	 * @param arg Last update name<br>
	 * Set search value for String
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 * Set search value for Last update name
	 * @param arg String array
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg[]) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEPNAME.toString(), arg);
	}
	/**
	 *Set search value for  Last update name
	 * @param arg Set search value for String
	 * @param compcode comparison code like >, <, =
	 * @throws ReadWriteException if the format is abnormal
	 */
	public void setLastUpdatePname(String arg, String compcode) throws ReadWriteException
	{
		setValue(_Prefix + Locate.LASTUPDATEPNAME.toString(), arg, compcode);
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
		setValue(_Prefix + Locate.LASTUPDATEPNAME.toString(), arg, compcode, left_paren, right_paren, and_or);
	}

	/**
	 * Set update value for Last update name
	 * @param arg Update value for Last update name
	 */
	public void updateLastUpdatePname(String arg)
	{
		setUpdValue(_Prefix + Locate.LASTUPDATEPNAME.toString(), arg);
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
		return "$Id: LocateAlterKey.java,v 1.2 2006/11/09 07:52:52 suresh Exp $" ;
	}
}
