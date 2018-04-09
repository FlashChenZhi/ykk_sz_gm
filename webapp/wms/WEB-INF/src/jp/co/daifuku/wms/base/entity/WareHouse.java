//#CM707350
//$Id: WareHouse.java,v 1.5 2006/11/16 02:15:35 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM707351
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.WareHouse;

//#CM707352
/**
 * Entity class of WAREHOUSE
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


public class WareHouse
		extends Station
{
	//#CM707353
	//------------------------------------------------------------
	//#CM707354
	// class variables (prefix '$')
	//#CM707355
	//------------------------------------------------------------
	//#CM707356
	//	private String	$classVar ;
//#CM707357
/**<jp>
	 * Field which shows type of warehouse (All)
	 </jp>*/
	//#CM707358
	/**<en>
	 * Field of warehouse type (All warehouses)
	 </en>*/
	public static final int ALL_WAREHOUSE = -1 ;

	//#CM707359
	/**<jp>
	 * Field which shows type of warehouse (Automatic warehouse)
	 </jp>*/
	//#CM707360
	/**<en>
	 * Field of warehouse type (automated wraehouse)
	 </en>*/
	public static final int AUTOMATID_WAREHOUSE = 1 ;

	//#CM707361
	/**<jp>
	 * Field which shows type of warehouse (Conventional warehouse)
	 </jp>*/
	//#CM707362
	/**<en>
	 * Field of warehouse type (conventional warehouse)
	 </en>*/
	public static final int CONVENTIONAL_WAREHOUSE = 2 ;

	//#CM707363
	/**<jp>
	 * Field which shows operation type of automatic warehouse (Open)
	 </jp>*/
	//#CM707364
	/**<en>
	 * Field of operation type of automated warehouse (open)
	 </en>*/
	public static final int EMPLOYMENT_OPEN = 1 ;

	//#CM707365
	/**<jp>
	 * Field which shows operation type of automatic warehouse (Close)
	 </jp>*/
	//#CM707366
	/**<en>
	 * Field of operation type of automated warehouse (close)
	 </en>*/
	public static final int EMPLOYMENT_CLOSE = 2 ;

	//#CM707367
	//------------------------------------------------------------
	//#CM707368
	// fields (upper case only)
	//#CM707369
	//------------------------------------------------------------
	//#CM707370
	/*
	 *  * Table name : WAREHOUSE
	 * station number :                STATIONNUMBER       varchar2(16)
	 * warehouse number :              WAREHOUSENUMBER     number
	 * warehouse type :                WAREHOUSETYPE       number
	 * maximum possible palette :      MAXMIXEDPALETTE     number
	 * warehouse name :                WAREHOUSENAME       varchar2(60)
	 * last used station number :      LASTUSEDSTATIONNUMBERvarchar2(16)
	 * automatic warehouse type :      EMPLOYMENTTYPE      number
	 */

	//#CM707371
	/**Table name definition*/

	public static final String TABLE_NAME = "WAREHOUSE";

	//#CM707372
	/** Column Definition (STATIONNUMBER) */

	public static final FieldName STATIONNUMBER = new FieldName("STATIONNUMBER");

	//#CM707373
	/** Column Definition (WAREHOUSENUMBER) */

	public static final FieldName WAREHOUSENUMBER = new FieldName("WAREHOUSENUMBER");

	//#CM707374
	/** Column Definition (WAREHOUSETYPE) */

	public static final FieldName WAREHOUSETYPE = new FieldName("WAREHOUSETYPE");

	//#CM707375
	/** Column Definition (MAXMIXEDPALETTE) */

	public static final FieldName MAXMIXEDPALETTE = new FieldName("MAXMIXEDPALETTE");

	//#CM707376
	/** Column Definition (WAREHOUSENAME) */

	public static final FieldName WAREHOUSENAME = new FieldName("WAREHOUSENAME");

	//#CM707377
	/** Column Definition (LASTUSEDSTATIONNUMBER) */

	public static final FieldName LASTUSEDSTATIONNUMBER = new FieldName("LASTUSEDSTATIONNUMBER");

	//#CM707378
	/** Column Definition (EMPLOYMENTTYPE) */

	public static final FieldName EMPLOYMENTTYPE = new FieldName("EMPLOYMENTTYPE");


	//#CM707379
	//------------------------------------------------------------
	//#CM707380
	// properties (prefix 'p_')
	//#CM707381
	//------------------------------------------------------------
	//#CM707382
	//	private String	p_Name ;


	//#CM707383
	//------------------------------------------------------------
	//#CM707384
	// instance variables (prefix '_')
	//#CM707385
	//------------------------------------------------------------
	//#CM707386
	//	private String	_instanceVar ;

	//#CM707387
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM707388
	//------------------------------------------------------------
	//#CM707389
	// constructors
	//#CM707390
	//------------------------------------------------------------

	//#CM707391
	/**
	 * Prepare class name list and generate instance
	 */
	public WareHouse()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM707392
	//------------------------------------------------------------
	//#CM707393
	// accessors
	//#CM707394
	//------------------------------------------------------------

	//#CM707395
	/**
	 * Set value to station number
	 * @param arg station number to be set
	 */
	public void setStationNumber(String arg)
	{
		setValue(STATIONNUMBER, arg);
	}

	//#CM707396
	/**
	 * Fetch station number
	 * @return station number
	 */
	public String getStationNumber()
	{
		return getValue(WareHouse.STATIONNUMBER).toString();
	}

	//#CM707397
	/**
	 * Set value to warehouse number
	 * @param arg warehouse number to be set
	 */
	public void setWareHouseNumber(int arg)
	{
		setValue(WAREHOUSENUMBER, new Integer(arg));
	}

	//#CM707398
	/**
	 * Fetch warehouse number
	 * @return warehouse number
	 */
	public int getWareHouseNumber()
	{
		return getBigDecimal(WareHouse.WAREHOUSENUMBER).intValue();
	}

	//#CM707399
	/**
	 * Set value to warehouse type
	 * @param arg warehouse type to be set
	 */
	public void setWareHouseType(int arg)
	{
		setValue(WAREHOUSETYPE, new Integer(arg));
	}

	//#CM707400
	/**
	 * Fetch warehouse type
	 * @return warehouse type
	 */
	public int getWareHouseType()
	{
		return getBigDecimal(WareHouse.WAREHOUSETYPE).intValue();
	}

	//#CM707401
	/**
	 * Set value to maximum possible palette
	 * @param arg maximum possible palette to be set
	 */
	public void setMaxMixedPalette(int arg)
	{
		setValue(MAXMIXEDPALETTE, new Integer(arg));
	}

	//#CM707402
	/**
	 * Fetch maximum possible palette
	 * @return maximum possible palette
	 */
	public int getMaxMixedPalette()
	{
		return getBigDecimal(WareHouse.MAXMIXEDPALETTE).intValue();
	}

	//#CM707403
	/**
	 * Set value to warehouse name
	 * @param arg warehouse name to be set
	 */
	public void setWareHouseName(String arg)
	{
		setValue(WAREHOUSENAME, arg);
	}

	//#CM707404
	/**
	 * Fetch warehouse name
	 * @return warehouse name
	 */
	public String getWareHouseName()
	{
		return getValue(WareHouse.WAREHOUSENAME).toString();
	}

	//#CM707405
	/**
	 * Set value to last used station number
	 * @param arg last used station number to be set
	 */
	public void setLastUsedStationNumber(String arg)
	{
		setValue(LASTUSEDSTATIONNUMBER, arg);
	}

	//#CM707406
	/**
	 * Fetch last used station number
	 * @return last used station number
	 */
	public String getLastUsedStationNumber()
	{
		return getValue(WareHouse.LASTUSEDSTATIONNUMBER).toString();
	}

	//#CM707407
	/**
	 * Set value to automatic warehouse type
	 * @param arg automatic warehouse type to be set
	 */
	public void setEmploymentType(int arg)
	{
		setValue(EMPLOYMENTTYPE, new Integer(arg));
	}

	//#CM707408
	/**
	 * Fetch automatic warehouse type
	 * @return automatic warehouse type
	 */
	public int getEmploymentType()
	{
		return getBigDecimal(WareHouse.EMPLOYMENTTYPE).intValue();
	}


	//#CM707409
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM707410
	/**
	*
	*
	*/
	public void setInitCreateColumn()
	{
		setValue(WAREHOUSENUMBER, new Integer(0));
		setValue(WAREHOUSETYPE, new Integer(0));
		setValue(MAXMIXEDPALETTE, new Integer(0));
		setValue(EMPLOYMENTTYPE, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM707411
	// package methods
	//#CM707412
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707413
	//------------------------------------------------------------


	//#CM707414
	//------------------------------------------------------------
	//#CM707415
	// protected methods
	//#CM707416
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707417
	//------------------------------------------------------------


	//#CM707418
	//------------------------------------------------------------
	//#CM707419
	// private methods
	//#CM707420
	//------------------------------------------------------------
	//#CM707421
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + STATIONNUMBER);
		lst.add(prefix + WAREHOUSENUMBER);
		lst.add(prefix + WAREHOUSETYPE);
		lst.add(prefix + MAXMIXEDPALETTE);
		lst.add(prefix + WAREHOUSENAME);
		lst.add(prefix + LASTUSEDSTATIONNUMBER);
		lst.add(prefix + EMPLOYMENTTYPE);
	}


	//#CM707422
	//------------------------------------------------------------
	//#CM707423
	// utility methods
	//#CM707424
	//------------------------------------------------------------
	//#CM707425
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: WareHouse.java,v 1.5 2006/11/16 02:15:35 suresh Exp $" ;
	}
}
