//#CM703310
//$Id: Consignor.java,v 1.5 2006/11/16 02:15:46 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM703311
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
import jp.co.daifuku.wms.base.entity.Consignor;

//#CM703312
/**
 * Entity class of CONSIGNOR
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:46 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Consignor
		extends AbstractMaster
{
	//#CM703313
	//------------------------------------------------------------
	//#CM703314
	// class variables (prefix '$')
	//#CM703315
	//------------------------------------------------------------
	//#CM703316
	//	private String	$classVar ;

	//#CM703317
	//------------------------------------------------------------
	//#CM703318
	// fields (upper case only)
	//#CM703319
	//------------------------------------------------------------
	//#CM703320
	/*
	 *  * Table name : CONSIGNOR
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Consignor name :                CONSIGNORNAME       varchar2(40)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(128)
	 * Last used date :                LASTUSEDDATE        date
	 * Delete flag :                   DELETEFLAG          varchar2(1)
	 */

	//#CM703321
	/**Table name definition*/

	public static final String TABLE_NAME = "DMCONSIGNOR";

	//#CM703322
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM703323
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM703324
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM703325
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");

	//#CM703326
	/** Column Definition (LASTUSEDDATE) */

	public static final FieldName LASTUSEDDATE = new FieldName("LAST_USED_DATE");

	//#CM703327
	/** Column Definition (DELETEFLAG) */

	public static final FieldName DELETEFLAG = new FieldName("DELETE_FLAG");


	//#CM703328
	//------------------------------------------------------------
	//#CM703329
	// properties (prefix 'p_')
	//#CM703330
	//------------------------------------------------------------
	//#CM703331
	//	private String	p_Name ;


	//#CM703332
	//------------------------------------------------------------
	//#CM703333
	// instance variables (prefix '_')
	//#CM703334
	//------------------------------------------------------------
	//#CM703335
	//	private String	_instanceVar ;

	//#CM703336
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM703337
	//------------------------------------------------------------
	//#CM703338
	// constructors
	//#CM703339
	//------------------------------------------------------------

	//#CM703340
	/**
	 * Prepare class name list and generate instance
	 */
	public Consignor()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM703341
	//------------------------------------------------------------
	//#CM703342
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
	// accessors
	//#CM703343
	//------------------------------------------------------------

	//#CM703344
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM703345
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(Consignor.CONSIGNORCODE).toString();
	}

	//#CM703346
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM703347
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(Consignor.CONSIGNORNAME).toString();
	}

	//#CM703348
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM703349
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Consignor.LASTUPDATEDATE);
	}

	//#CM703350
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM703351
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Consignor.LASTUPDATEPNAME).toString();
	}

	//#CM703352
	/**
	 * Set value to Last used date
	 * @param arg Last used date to be set
	 */
	public void setLastUsedDate(Date arg)
	{
		setValue(LASTUSEDDATE, arg);
	}

	//#CM703353
	/**
	 * Fetch Last used date
	 * @return Last used date
	 */
	public Date getLastUsedDate()
	{
		return (Date)getValue(Consignor.LASTUSEDDATE);
	}

	//#CM703354
	/**
	 * Set value to Delete flag
	 * @param arg Delete flag to be set
	 */
	public void setDeleteFlag(String arg)
	{
		setValue(DELETEFLAG, arg);
	}

	//#CM703355
	/**
	 * Fetch Delete flag
	 * @return Delete flag
	 */
	public String getDeleteFlag()
	{
		return getValue(Consignor.DELETEFLAG).toString();
	}


	//#CM703356
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM703357
	//------------------------------------------------------------
	//#CM703358
	// package methods
	//#CM703359
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703360
	//------------------------------------------------------------


	//#CM703361
	//------------------------------------------------------------
	//#CM703362
	// protected methods
	//#CM703363
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703364
	//------------------------------------------------------------


	//#CM703365
	//------------------------------------------------------------
	//#CM703366
	// private methods
	//#CM703367
	//------------------------------------------------------------
	//#CM703368
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + CONSIGNORNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
		lst.add(prefix + LASTUSEDDATE);
		lst.add(prefix + DELETEFLAG);
	}

	 /**
	  * <BR>
	  * <BR>
	  */
	 public void setInitCreateColumn()
	 {
		 setValue(LASTUPDATEDATE, "");
	 }

	//#CM703369
	//------------------------------------------------------------
	//#CM703370
	// utility methods
	//#CM703371
	//------------------------------------------------------------
	//#CM703372
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Consignor.java,v 1.5 2006/11/16 02:15:46 suresh Exp $" ;
	}
}
