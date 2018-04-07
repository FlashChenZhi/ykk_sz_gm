//#CM703080
//$Id: BankSelect.java,v 1.5 2006/11/16 02:15:47 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM703081
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.BankSelect;

//#CM703082
/**
 * Entity class of BANKSELECT
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:47 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class BankSelect
		extends AbstractEntity
{
	//#CM703083
	//------------------------------------------------------------
	//#CM703084
	// class variables (prefix '$')
	//#CM703085
	//------------------------------------------------------------
	//#CM703086
	//	private String	$classVar ;

	//#CM703087
	/**<jp>
	 * Field which shows interior and this side of bank(this side bank)
	 </jp>*/
	//#CM703088
	/**<en>
	 * Filed: front of the bank
	 </en>*/
	public static final int NEAR = 0 ;

	//#CM703089
	/**<jp>
	 * Field which shows interior and this side of bank(interior side bank)
	 </jp>*/
	//#CM703090
	/**<en>
	 * Field: rear bank
	 </en>*/
	public static final int FAR   = 1 ;
	//#CM703091
	//------------------------------------------------------------
	//#CM703092
	// fields (upper case only)
	//#CM703093
	//------------------------------------------------------------
	//#CM703094
	/*
	 *  * Table name : BANKSELECT
	 * Warehouse station number :      WHSTATIONNUMBER     varchar2(16)
	 * Aisle station number :          AISLESTATIONNUMBER  varchar2(16)
	 * Bank :                          NBANK               number
	 * Pair bank number :              PAIRBANK            number
	 * front, depth type :             SIDE                number
	 */

	//#CM703095
	/**Table name definition*/

	public static final String TABLE_NAME = "BANKSELECT";

	//#CM703096
	/** Column Definition (WHSTATIONNUMBER) */

	public static final FieldName WHSTATIONNUMBER = new FieldName("WHSTATIONNUMBER");

	//#CM703097
	/** Column Definition (AISLESTATIONNUMBER) */

	public static final FieldName AISLESTATIONNUMBER = new FieldName("AISLESTATIONNUMBER");

	//#CM703098
	/** Column Definition (NBANK) */

	public static final FieldName NBANK = new FieldName("NBANK");

	//#CM703099
	/** Column Definition (PAIRBANK) */

	public static final FieldName PAIRBANK = new FieldName("PAIRBANK");

	//#CM703100
	/** Column Definition (SIDE) */

	public static final FieldName SIDE = new FieldName("SIDE");


	//#CM703101
	//------------------------------------------------------------
	//#CM703102
	// properties (prefix 'p_')
	//#CM703103
	//------------------------------------------------------------
	//#CM703104
	//	private String	p_Name ;


	//#CM703105
	//------------------------------------------------------------
	//#CM703106
	// instance variables (prefix '_')
	//#CM703107
	//------------------------------------------------------------
	//#CM703108
	//	private String	_instanceVar ;

	//#CM703109
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM703110
	//------------------------------------------------------------
	//#CM703111
	// constructors
	//#CM703112
	//------------------------------------------------------------

	//#CM703113
	/**
	 * Prepare class name list and generate instance
	 */
	public BankSelect()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM703114
	//------------------------------------------------------------
	//#CM703115
	// accessors
	//#CM703116
	//------------------------------------------------------------

	//#CM703117
	/**
	 * Set value to Warehouse station number
	 * @param arg Warehouse station number to be set
	 */
	public void setWHStationNumber(String arg)
	{
		setValue(WHSTATIONNUMBER, arg);
	}

	//#CM703118
	/**
	 * Fetch Warehouse station number
	 * @return Warehouse station number
	 */
	public String getWHStationNumber()
	{
		return getValue(BankSelect.WHSTATIONNUMBER).toString();
	}

	//#CM703119
	/**
	 * Set value to Aisle station number
	 * @param arg Aisle station number to be set
	 */
	public void setAisleStationNumber(String arg)
	{
		setValue(AISLESTATIONNUMBER, arg);
	}

	//#CM703120
	/**
	 * Fetch Aisle station number
	 * @return Aisle station number
	 */
	public String getAisleStationNumber()
	{
		return getValue(BankSelect.AISLESTATIONNUMBER).toString();
	}

	//#CM703121
	/**
	 * Set value to Bank
	 * @param arg Bank to be set
	 */
	public void setNbank(int arg)
	{
		setValue(NBANK, new Integer(arg));
	}

	//#CM703122
	/**
	 * Fetch Bank
	 * @return Bank
	 */
	public int getNbank()
	{
		return getBigDecimal(BankSelect.NBANK).intValue();
	}

	//#CM703123
	/**
	 * Set value to Pair bank number
	 * @param arg Pair bank number to be set
	 */
	public void setPairBank(int arg)
	{
		setValue(PAIRBANK, new Integer(arg));
	}

	//#CM703124
	/**
	 * Fetch Pair bank number
	 * @return Pair bank number
	 */
	public int getPairBank()
	{
		return getBigDecimal(BankSelect.PAIRBANK).intValue();
	}

	//#CM703125
	/**
	 * Set value to front, depth type
	 * @param arg front, depth type to be set
	 */
	public void setSide(int arg)
	{
		setValue(SIDE, new Integer(arg));
	}

	//#CM703126
	/**
	 * Fetch front, depth type
	 * @return front, depth type
	 */
	public int getSide()
	{
		return getBigDecimal(BankSelect.SIDE).intValue();
	}


	//#CM703127
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM703128
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(NBANK, new Integer(0));
		setValue(PAIRBANK, new Integer(0));
		setValue(SIDE, new Integer(0));

	}
	//------------------------------------------------------------
	//#CM703129
	// package methods
	//#CM703130
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703131
	//------------------------------------------------------------


	//#CM703132
	//------------------------------------------------------------
	//#CM703133
	// protected methods
	//#CM703134
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703135
	//------------------------------------------------------------


	//#CM703136
	//------------------------------------------------------------
	//#CM703137
	// private methods
	//#CM703138
	//------------------------------------------------------------
	//#CM703139
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + WHSTATIONNUMBER);
		lst.add(prefix + AISLESTATIONNUMBER);
		lst.add(prefix + NBANK);
		lst.add(prefix + PAIRBANK);
		lst.add(prefix + SIDE);
	}


	//#CM703140
	//------------------------------------------------------------
	//#CM703141
	// utility methods
	//#CM703142
	//------------------------------------------------------------
	//#CM703143
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: BankSelect.java,v 1.5 2006/11/16 02:15:47 suresh Exp $" ;
	}
}
