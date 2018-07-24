//#CM707426
//$Id: WareNaviSystem.java,v 1.5 2006/11/16 02:15:34 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM707427
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.List;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.WareNaviSystem;

//#CM707428
/**
 * Entity class of WARENAVI_SYSTEM
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:34 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WareNaviSystem
		extends AbstractEntity
{
	//#CM707429
	//------------------------------------------------------------
	//#CM707430
	// class variables (prefix '$')
	//#CM707431
	//------------------------------------------------------------
	//#CM707432
	//	private String	$classVar ;
//#CM707433
/**
	 * Operation flag (Available)
	 */
	public static final String OPERATION_FLAG_ADDON = "1" ;

	//#CM707434
	/**
	 * Package flag (Available)
	 */
	public static final String PACKAGE_FLAG_ADDON = "1" ;

	//#CM707435
	/**
	 * Daily update flag (Stopping)
	 */
	public static final String DAILYUPDATE_STOP = "0" ;

	//#CM707436
	/**
	 * Daily update flag (Loading)
	 */
	public static final String DAILYUPDATE_LOADING = "1" ;

	//#CM707437
	/**
	 * Plan data loading flag (Stopping)
	 */
	public static final String LOADDATA_STOP = "0";

	//#CM707438
	/**
	 * Plan data loading flag (Loading)
	 */
	public static final String LOADDATA_LOADING = "1";


	//#CM707439
	/**
	 * Flag when report data is being made (Stopping)
	 */
	public static final String REPORTDATA_STOP = "0";

	//#CM707440
	/**
	 * Flag when report data is being made (Loading)
	 */
	public static final String REPORTDATA_LOADING = "1";

	//#CM707441
	/**
	 * Daily update flag (Default System No)
	 */
	public static final int SYSTEM_NO = 1 ;

	//#CM707442
	//------------------------------------------------------------
	//#CM707443
	// fields (upper case only)
	//#CM707444
	//------------------------------------------------------------
	//#CM707445
	/*
	 *  * Table name : WARENAVI_SYSTEM
	 * System No :                     SYSTEMNO            number
	 * Work date :                     WORKDATE            varchar2(8)
	 * DC Mode :                       DCOPERATION         varchar2(1)
	 * TC Mode :                       TCOPERATION         varchar2(1)
	 * CrossDC enabled :               CROSSDOCKOPERATION  varchar2(1)
	 * Result hold period :            RESULTHOLDPERIOD    number
	 * Plan hold period :              PLANHOLDPERIOD      number
	 * Receiving package :             INSTOCKPACK         varchar2(1)
	 * Storage package :               STORAGEPACK         varchar2(1)
	 * Retrieval package :             RETRIEVALPACK       varchar2(1)
	 * Sorting package :               SORTINGPACK         varchar2(1)
	 * Shipping package :              SHIPPINGPACK        varchar2(1)
	 * Stock pack :                    STOCKPACK           varchar2(1)
	 * CrossDC package :               CROSSDOCKPACK       varchar2(1)
	 * IDM Pack :                      IDMPACK             varchar2(1)
	 * Daily update :                  DAILYUPDATE         varchar2(1)
	 * Loading data :                  LOADDATA            varchar2(1)
	 * Generating report :             REPORTDATA          varchar2(1)
	 */

	//#CM707446
	/**Table name definition*/

	public static final String TABLE_NAME = "WARENAVI_SYSTEM";

	//#CM707447
	/** Column Definition (SYSTEMNO) */

	public static final FieldName SYSTEMNO = new FieldName("SYSTEM_NO");

	//#CM707448
	/** Column Definition (WORKDATE) */

	public static final FieldName WORKDATE = new FieldName("WORK_DATE");

	//#CM707449
	/** Column Definition (DCOPERATION) */

	public static final FieldName DCOPERATION = new FieldName("DC_OPERATION");

	//#CM707450
	/** Column Definition (TCOPERATION) */

	public static final FieldName TCOPERATION = new FieldName("TC_OPERATION");

	//#CM707451
	/** Column Definition (CROSSDOCKOPERATION) */

	public static final FieldName CROSSDOCKOPERATION = new FieldName("CROSSDOCK_OPERATION");

	//#CM707452
	/** Column Definition (RESULTHOLDPERIOD) */

	public static final FieldName RESULTHOLDPERIOD = new FieldName("RESULT_HOLD_PERIOD");

	//#CM707453
	/** Column Definition (PLANHOLDPERIOD) */

	public static final FieldName PLANHOLDPERIOD = new FieldName("PLAN_HOLD_PERIOD");

	//#CM707454
	/** Column Definition (INSTOCKPACK) */

	public static final FieldName INSTOCKPACK = new FieldName("INSTOCK_PACK");

	//#CM707455
	/** Column Definition (STORAGEPACK) */

	public static final FieldName STORAGEPACK = new FieldName("STORAGE_PACK");

	//#CM707456
	/** Column Definition (RETRIEVALPACK) */

	public static final FieldName RETRIEVALPACK = new FieldName("RETRIEVAL_PACK");

	//#CM707457
	/** Column Definition (SORTINGPACK) */

	public static final FieldName SORTINGPACK = new FieldName("SORTING_PACK");

	//#CM707458
	/** Column Definition (SHIPPINGPACK) */

	public static final FieldName SHIPPINGPACK = new FieldName("SHIPPING_PACK");

	//#CM707459
	/** Column Definition (STOCKPACK) */

	public static final FieldName STOCKPACK = new FieldName("STOCK_PACK");

	//#CM707460
	/** Column Definition (CROSSDOCKPACK) */

	public static final FieldName CROSSDOCKPACK = new FieldName("CROSSDOCK_PACK");

	//#CM707461
	/** Column Definition (IDMPACK) */

	public static final FieldName IDMPACK = new FieldName("IDM_PACK");

	//#CM707462
	/** Column Definition (DAILYUPDATE) */

	public static final FieldName DAILYUPDATE = new FieldName("DAILY_UPDATE");

	//#CM707463
	/** Column Definition (LOADDATA) */

	public static final FieldName LOADDATA = new FieldName("LOAD_DATA");

	//#CM707464
	/** Column Definition (REPORTDATA) */

	public static final FieldName REPORTDATA = new FieldName("REPORT_DATA");


	//#CM707465
	//------------------------------------------------------------
	//#CM707466
	// properties (prefix 'p_')
	//#CM707467
	//------------------------------------------------------------
	//#CM707468
	//	private String	p_Name ;


	//#CM707469
	//------------------------------------------------------------
	//#CM707470
	// instance variables (prefix '_')
	//#CM707471
	//------------------------------------------------------------
	//#CM707472
	//	private String	_instanceVar ;

	//#CM707473
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM707474
	//------------------------------------------------------------
	//#CM707475
	// constructors
	//#CM707476
	//------------------------------------------------------------

	//#CM707477
	/**
	 * Prepare class name list and generate instance
	 */
	public WareNaviSystem()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM707478
	//------------------------------------------------------------
	//#CM707479
	// accessors
	//#CM707480
	//------------------------------------------------------------

	//#CM707481
	/**
	 * Set value to System No
	 * @param arg System No to be set
	 */
	public void setSystemNo(int arg)
	{
		setValue(SYSTEMNO, new Integer(arg));
	}

	//#CM707482
	/**
	 * Fetch System No
	 * @return System No
	 */
	public int getSystemNo()
	{
		return getBigDecimal(WareNaviSystem.SYSTEMNO).intValue();
	}

	//#CM707483
	/**
	 * Set value to Work date
	 * @param arg Work date to be set
	 */
	public void setWorkDate(String arg)
	{
		setValue(WORKDATE, arg);
	}

	//#CM707484
	/**
	 * Fetch Work date
	 * @return Work date
	 */
	public String getWorkDate()
	{
		return getValue(WareNaviSystem.WORKDATE).toString();
	}

	//#CM707485
	/**
	 * Set value to DC Mode
	 * @param arg DC Mode to be set
	 */
	public void setDcOperation(String arg)
	{
		setValue(DCOPERATION, arg);
	}

	//#CM707486
	/**
	 * Fetch DC Mode
	 * @return DC Mode
	 */
	public String getDcOperation()
	{
		return getValue(WareNaviSystem.DCOPERATION).toString();
	}

	//#CM707487
	/**
	 * Set value to TC Mode
	 * @param arg TC Mode to be set
	 */
	public void setTcOperation(String arg)
	{
		setValue(TCOPERATION, arg);
	}

	//#CM707488
	/**
	 * Fetch TC Mode
	 * @return TC Mode
	 */
	public String getTcOperation()
	{
		return getValue(WareNaviSystem.TCOPERATION).toString();
	}

	//#CM707489
	/**
	 * Set value to CrossDC enabled
	 * @param arg CrossDC enabled to be set
	 */
	public void setCrossdockOperation(String arg)
	{
		setValue(CROSSDOCKOPERATION, arg);
	}

	//#CM707490
	/**
	 * Fetch CrossDC enabled
	 * @return CrossDC enabled
	 */
	public String getCrossdockOperation()
	{
		return getValue(WareNaviSystem.CROSSDOCKOPERATION).toString();
	}

	//#CM707491
	/**
	 * Set value to Result hold period
	 * @param arg Result hold period to be set
	 */
	public void setResultHoldPeriod(int arg)
	{
		setValue(RESULTHOLDPERIOD, new Integer(arg));
	}

	//#CM707492
	/**
	 * Fetch Result hold period
	 * @return Result hold period
	 */
	public int getResultHoldPeriod()
	{
		return getBigDecimal(WareNaviSystem.RESULTHOLDPERIOD).intValue();
	}

	//#CM707493
	/**
	 * Set value to Plan hold period
	 * @param arg Plan hold period to be set
	 */
	public void setPlanHoldPeriod(int arg)
	{
		setValue(PLANHOLDPERIOD, new Integer(arg));
	}

	//#CM707494
	/**
	 * Fetch Plan hold period
	 * @return Plan hold period
	 */
	public int getPlanHoldPeriod()
	{
		return getBigDecimal(WareNaviSystem.PLANHOLDPERIOD).intValue();
	}

	//#CM707495
	/**
	 * Set value to Receiving package
	 * @param arg Receiving package to be set
	 */
	public void setInstockPack(String arg)
	{
		setValue(INSTOCKPACK, arg);
	}

	//#CM707496
	/**
	 * Fetch Receiving package
	 * @return Receiving package
	 */
	public String getInstockPack()
	{
		return getValue(WareNaviSystem.INSTOCKPACK).toString();
	}

	//#CM707497
	/**
	 * Set value to Storage package
	 * @param arg Storage package to be set
	 */
	public void setStoragePack(String arg)
	{
		setValue(STORAGEPACK, arg);
	}

	//#CM707498
	/**
	 * Fetch Storage package
	 * @return Storage package
	 */
	public String getStoragePack()
	{
		return getValue(WareNaviSystem.STORAGEPACK).toString();
	}

	//#CM707499
	/**
	 * Set value to Retrieval package
	 * @param arg Retrieval package to be set
	 */
	public void setRetrievalPack(String arg)
	{
		setValue(RETRIEVALPACK, arg);
	}

	//#CM707500
	/**
	 * Fetch Retrieval package
	 * @return Retrieval package
	 */
	public String getRetrievalPack()
	{
		return getValue(WareNaviSystem.RETRIEVALPACK).toString();
	}

	//#CM707501
	/**
	 * Set value to Sorting package
	 * @param arg Sorting package to be set
	 */
	public void setSortingPack(String arg)
	{
		setValue(SORTINGPACK, arg);
	}

	//#CM707502
	/**
	 * Fetch Sorting package
	 * @return Sorting package
	 */
	public String getSortingPack()
	{
		return getValue(WareNaviSystem.SORTINGPACK).toString();
	}

	//#CM707503
	/**
	 * Set value to Shipping package
	 * @param arg Shipping package to be set
	 */
	public void setShippingPack(String arg)
	{
		setValue(SHIPPINGPACK, arg);
	}

	//#CM707504
	/**
	 * Fetch Shipping package
	 * @return Shipping package
	 */
	public String getShippingPack()
	{
		return getValue(WareNaviSystem.SHIPPINGPACK).toString();
	}

	//#CM707505
	/**
	 * Set value to Stock pack
	 * @param arg Stock pack to be set
	 */
	public void setStockPack(String arg)
	{
		setValue(STOCKPACK, arg);
	}

	//#CM707506
	/**
	 * Fetch Stock pack
	 * @return Stock pack
	 */
	public String getStockPack()
	{
		return getValue(WareNaviSystem.STOCKPACK).toString();
	}

	//#CM707507
	/**
	 * Set value to CrossDC package
	 * @param arg CrossDC package to be set
	 */
	public void setCrossdockPack(String arg)
	{
		setValue(CROSSDOCKPACK, arg);
	}

	//#CM707508
	/**
	 * Fetch CrossDC package
	 * @return CrossDC package
	 */
	public String getCrossdockPack()
	{
		return getValue(WareNaviSystem.CROSSDOCKPACK).toString();
	}

	//#CM707509
	/**
	 * Set value to IDM Pack
	 * @param arg IDM Pack to be set
	 */
	public void setIdmPack(String arg)
	{
		setValue(IDMPACK, arg);
	}

	//#CM707510
	/**
	 * Fetch IDM Pack
	 * @return IDM Pack
	 */
	public String getIdmPack()
	{
		return getValue(WareNaviSystem.IDMPACK).toString();
	}

	//#CM707511
	/**
	 * Set value to Daily update
	 * @param arg Daily update to be set
	 */
	public void setDailyUpdate(String arg)
	{
		setValue(DAILYUPDATE, arg);
	}

	//#CM707512
	/**
	 * Fetch Daily update
	 * @return Daily update
	 */
	public String getDailyUpdate()
	{
		return getValue(WareNaviSystem.DAILYUPDATE).toString();
	}

	//#CM707513
	/**
	 * Set value to Loading data
	 * @param arg Loading data to be set
	 */
	public void setLoadData(String arg)
	{
		setValue(LOADDATA, arg);
	}

	//#CM707514
	/**
	 * Fetch Loading data
	 * @return Loading data
	 */
	public String getLoadData()
	{
		return getValue(WareNaviSystem.LOADDATA).toString();
	}

	//#CM707515
	/**
	 * Set value to Generating report
	 * @param arg Generating report to be set
	 */
	public void setReportData(String arg)
	{
		setValue(REPORTDATA, arg);
	}

	//#CM707516
	/**
	 * Fetch Generating report
	 * @return Generating report
	 */
	public String getReportData()
	{
		return getValue(WareNaviSystem.REPORTDATA).toString();
	}


	//#CM707517
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM707518
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(SYSTEMNO, new Integer(0));
		setValue(RESULTHOLDPERIOD, new Integer(0));
		setValue(PLANHOLDPERIOD, new Integer(0));
	}
	//------------------------------------------------------------
	//#CM707519
	// package methods
	//#CM707520
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707521
	//------------------------------------------------------------


	//#CM707522
	//------------------------------------------------------------
	//#CM707523
	// protected methods
	//#CM707524
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707525
	//------------------------------------------------------------


	//#CM707526
	//------------------------------------------------------------
	//#CM707527
	// private methods
	//#CM707528
	//------------------------------------------------------------
	//#CM707529
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + SYSTEMNO);
		lst.add(prefix + WORKDATE);
		lst.add(prefix + DCOPERATION);
		lst.add(prefix + TCOPERATION);
		lst.add(prefix + CROSSDOCKOPERATION);
		lst.add(prefix + RESULTHOLDPERIOD);
		lst.add(prefix + PLANHOLDPERIOD);
		lst.add(prefix + INSTOCKPACK);
		lst.add(prefix + STORAGEPACK);
		lst.add(prefix + RETRIEVALPACK);
		lst.add(prefix + SORTINGPACK);
		lst.add(prefix + SHIPPINGPACK);
		lst.add(prefix + STOCKPACK);
		lst.add(prefix + CROSSDOCKPACK);
		lst.add(prefix + IDMPACK);
		lst.add(prefix + DAILYUPDATE);
		lst.add(prefix + LOADDATA);
		lst.add(prefix + REPORTDATA);
	}


	//#CM707530
	//------------------------------------------------------------
	//#CM707531
	// utility methods
	//#CM707532
	//------------------------------------------------------------
	//#CM707533
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: WareNaviSystem.java,v 1.5 2006/11/16 02:15:34 suresh Exp $" ;
	}
}
