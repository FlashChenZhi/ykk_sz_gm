//#CM707153
//$Id: Supplier.java,v 1.5 2006/11/16 02:15:35 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM707154
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.Supplier;

//#CM707155
/**
 * Entity class of SUPPLIER
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:35 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Supplier
		extends AbstractMaster
{
	//#CM707156
	//------------------------------------------------------------
	//#CM707157
	// class variables (prefix '$')
	//#CM707158
	//------------------------------------------------------------
	//#CM707159
	//	private String	$classVar ;

	//#CM707160
	//------------------------------------------------------------
	//#CM707161
	// fields (upper case only)
	//#CM707162
	//------------------------------------------------------------
	//#CM707163
	/*
	 *  * Table name : SUPPLIER
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Supplier code :                 SUPPLIERCODE        varchar2(16)
	 * Supplier name :                 SUPPLIERNAME1       varchar2(40)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(128)
	 * Last used date :                LASTUSEDDATE        date
	 * Delete flag :                   DELETEFLAG          varchar2(1)
	 */

	//#CM707164
	/**Table name definition*/

	public static final String TABLE_NAME = "DMSUPPLIER";

	//#CM707165
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM707166
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM707167
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM707168
	/** Column Definition (LASTUPDATEDATE) */

	//public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM707169
	/** Column Definition (LASTUPDATEPNAME) */

	//public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");

	//#CM707170
	/** Column Definition (LASTUSEDDATE) */

	//public static final FieldName LASTUSEDDATE = new FieldName("LAST_USED_DATE");

	//#CM707171
	/** Column Definition (DELETEFLAG) */

	//public static final FieldName DELETEFLAG = new FieldName("DELETE_FLAG");


	//#CM707172
	//------------------------------------------------------------
	//#CM707173
	// properties (prefix 'p_')
	//#CM707174
	//------------------------------------------------------------
	//#CM707175
	//	private String	p_Name ;


	//#CM707176
	//------------------------------------------------------------
	//#CM707177
	// instance variables (prefix '_')
	//#CM707178
	//------------------------------------------------------------
	//#CM707179
	//	private String	_instanceVar ;

	//#CM707180
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM707181
	//------------------------------------------------------------
	//#CM707182
	// constructors
	//#CM707183
	//------------------------------------------------------------

	//#CM707184
	/**
	 * Prepare class name list and generate instance
	 */
	public Supplier()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}
	//#CM707185
	//------------------------------------------------------------
	//#CM707186
	// public method
	//#CM707187
	//------------------------------------------------------------

	//#CM707188
	/**
	 * @see jp.co.daifuku.wms.base.entity.AbstractEntity#getAutoUpdateColumnArray()
	 */
	public String[] getAutoUpdateColumnArray()
	{

		String prefix = TABLE_NAME + "." ;
		String[] autoColumns = {
				prefix + LASTUPDATEDATE
		} ;
		return autoColumns ;
	}

	//#CM707189
	//------------------------------------------------------------
	//#CM707190
	// accessors
	//#CM707191
	//------------------------------------------------------------

	//#CM707192
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM707193
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(Supplier.CONSIGNORCODE).toString();
	}

	//#CM707194
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM707195
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(Supplier.SUPPLIERCODE).toString();
	}

	//#CM707196
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM707197
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(Supplier.SUPPLIERNAME1).toString();
	}

	//#CM707198
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM707199
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Supplier.LASTUPDATEDATE);
	}

	//#CM707200
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM707201
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Supplier.LASTUPDATEPNAME).toString();
	}

	//#CM707202
	/**
	 * Set value to Last used date
	 * @param arg Last used date to be set
	 */
	public void setLastUsedDate(Date arg)
	{
		setValue(LASTUSEDDATE, arg);
	}

	//#CM707203
	/**
	 * Fetch Last used date
	 * @return Last used date
	 */
	public Date getLastUsedDate()
	{
		return (Date)getValue(Supplier.LASTUSEDDATE);
	}

	//#CM707204
	/**
	 * Set value to Delete flag
	 * @param arg Delete flag to be set
	 */
	public void setDeleteFlag(String arg)
	{
		setValue(DELETEFLAG, arg);
	}

	//#CM707205
	/**
	 * Fetch Delete flag
	 * @return Delete flag
	 */
	public String getDeleteFlag()
	{
		return getValue(Supplier.DELETEFLAG).toString();
	}


	//#CM707206
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM707207
	//------------------------------------------------------------
	//#CM707208
	// package methods
	//#CM707209
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707210
	//------------------------------------------------------------


	//#CM707211
	//------------------------------------------------------------
	//#CM707212
	// protected methods
	//#CM707213
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707214
	//------------------------------------------------------------


	//#CM707215
	//------------------------------------------------------------
	//#CM707216
	// private methods
	//#CM707217
	//------------------------------------------------------------
	//#CM707218
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + SUPPLIERCODE);
		lst.add(prefix + SUPPLIERNAME1);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
		lst.add(prefix + LASTUSEDDATE);
		lst.add(prefix + DELETEFLAG);
	}


	//#CM707219
	//------------------------------------------------------------
	//#CM707220
	// utility methods
	//#CM707221
	//------------------------------------------------------------
	//#CM707222
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Supplier.java,v 1.5 2006/11/16 02:15:35 suresh Exp $" ;
	}
public void setInitCreateColumn()
	 {
		 setValue(LASTUPDATEDATE, "");
	 }

}
