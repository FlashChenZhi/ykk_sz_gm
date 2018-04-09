//#CM706390
//$Id: SortingPlan.java,v 1.5 2006/11/16 02:15:37 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM706391
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.SortingPlan;

//#CM706392
/**
 * Entity class of SORTINGPLAN
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:37 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class SortingPlan
		extends AbstractEntity
{
	//#CM706393
	//------------------------------------------------------------
	//#CM706394
	// class variables (prefix '$')
	//#CM706395
	//------------------------------------------------------------
	//#CM706396
	//	private String	$classVar ;

	//#CM706397
	//------------------------------------------------------------
	//#CM706398
	// fields (upper case only)
	//#CM706399
	//------------------------------------------------------------
	//#CM706400
	/*
	 *  * Table name : SORTINGPLAN
	 * Sorting plan unique key :       SORTINGPLANUKEY     varchar2(16)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Plan date :                     PLANDATE            varchar2(8)
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Consignor name :                CONSIGNORNAME       varchar2(40)
	 * Customer code :                 CUSTOMERCODE        varchar2(16)
	 * Customer name :                 CUSTOMERNAME1       varchar2(40)
	 * Shipping ticket no :            SHIPPINGTICKETNO    varchar2(16)
	 * Shipping line no :              SHIPPINGLINENO      number
	 * Item code :                     ITEMCODE            varchar2(16)
	 * Item name :                     ITEMNAME1           varchar2(40)
	 * Sorting plan qty :              PLANQTY             number
	 * Sorting result qty :            RESULTQTY           number
	 * Storage count :                 SHORTAGECNT         number
	 * Entering case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case/Piece flag :               CASEPIECEFLAG       varchar2(1)
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * Piece Sorting location :        PIECELOCATION       varchar2(16)
	 * Case Sorting location :         CASELOCATION        varchar2(16)
	 * TC/DC Flag :                    TCDCFLAG            varchar2(1)
	 * Supplier code :                 SUPPLIERCODE        varchar2(16)
	 * Supplier name :                 SUPPLIERNAME1       varchar2(40)
	 * Receiving Ticket no :           INSTOCKTICKETNO     varchar2(16)
	 * Receiving line no :             INSTOCKLINENO       number
	 * Use By Date :                   USEBYDATE           varchar2(8)
	 * Lot :                           LOTNO               varchar2(20)
	 * Plan :                          PLANINFORMATION     varchar2(40)
	 * Batch No (Schedule No) :        BATCHNO             varchar2(8)
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Worker name :                   WORKERNAME          varchar2(20)
	 * Terminal No :                   TERMINALNO          varchar2(3)
	 * Registration type :             REGISTKIND          varchar2(1)
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM706401
	/**Table name definition*/

	public static final String TABLE_NAME = "DNSORTINGPLAN";

	//#CM706402
	/** Column Definition (SORTINGPLANUKEY) */

	public static final FieldName SORTINGPLANUKEY = new FieldName("SORTING_PLAN_UKEY");

	//#CM706403
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM706404
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM706405
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM706406
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM706407
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM706408
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM706409
	/** Column Definition (SHIPPINGTICKETNO) */

	public static final FieldName SHIPPINGTICKETNO = new FieldName("SHIPPING_TICKET_NO");

	//#CM706410
	/** Column Definition (SHIPPINGLINENO) */

	public static final FieldName SHIPPINGLINENO = new FieldName("SHIPPING_LINE_NO");

	//#CM706411
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM706412
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM706413
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM706414
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM706415
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM706416
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM706417
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM706418
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM706419
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM706420
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM706421
	/** Column Definition (PIECELOCATION) */

	public static final FieldName PIECELOCATION = new FieldName("PIECE_LOCATION");

	//#CM706422
	/** Column Definition (CASELOCATION) */

	public static final FieldName CASELOCATION = new FieldName("CASE_LOCATION");

	//#CM706423
	/** Column Definition (TCDCFLAG) */

	public static final FieldName TCDCFLAG = new FieldName("TCDC_FLAG");

	//#CM706424
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM706425
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM706426
	/** Column Definition (INSTOCKTICKETNO) */

	public static final FieldName INSTOCKTICKETNO = new FieldName("INSTOCK_TICKET_NO");

	//#CM706427
	/** Column Definition (INSTOCKLINENO) */

	public static final FieldName INSTOCKLINENO = new FieldName("INSTOCK_LINE_NO");

	//#CM706428
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM706429
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM706430
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM706431
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM706432
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM706433
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM706434
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM706435
	/** Column Definition (REGISTKIND) */

	public static final FieldName REGISTKIND = new FieldName("REGIST_KIND");

	//#CM706436
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM706437
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM706438
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM706439
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM706440
	//------------------------------------------------------------
	//#CM706441
	// properties (prefix 'p_')
	//#CM706442
	//------------------------------------------------------------
	//#CM706443
	//	private String	p_Name ;


	//#CM706444
	//------------------------------------------------------------
	//#CM706445
	// instance variables (prefix '_')
	//#CM706446
	//------------------------------------------------------------
	//#CM706447
	//	private String	_instanceVar ;

	//#CM706448
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM706449
	//------------------------------------------------------------
	//#CM706450
	// constructors
	//#CM706451
	//------------------------------------------------------------

	//#CM706452
	/**
	 * Prepare class name list and generate instance
	 */
	public SortingPlan()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM706453
	//------------------------------------------------------------
	//#CM706454
	// accessors
	//#CM706455
	//------------------------------------------------------------

	//#CM706456
	/**
	 * Set value to Sorting plan unique key
	 * @param arg Sorting plan unique key to be set
	 */
	public void setSortingPlanUkey(String arg)
	{
		setValue(SORTINGPLANUKEY, arg);
	}

	//#CM706457
	/**
	 * Fetch Sorting plan unique key
	 * @return Sorting plan unique key
	 */
	public String getSortingPlanUkey()
	{
		return getValue(SortingPlan.SORTINGPLANUKEY).toString();
	}

	//#CM706458
	/**
	 * Set value to Status flag
	 * @param arg Status flag to be set
	 */
	public void setStatusFlag(String arg) throws InvalidStatusException
	{
if ( ( arg.equals( STATUS_FLAG_UNSTART ) )          ||
			 ( arg.equals( STATUS_FLAG_START ) )            ||
			 ( arg.equals( STATUS_FLAG_NOWWORKING ) )       ||
			 ( arg.equals( STATUS_FLAG_COMPLETE_IN_PART ) ) ||
			 ( arg.equals( STATUS_FLAG_COMPLETION ) )       ||
			 ( arg.equals( STATUS_FLAG_DELETE ) ) )
		{
			setValue(STATUSFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[ 3 ];
			tObj[ 0 ] = this.getClass().getName();
			tObj[ 1 ] = "wStatusFlag";
			tObj[ 2 ] = arg;
			String classname = ( String ) tObj[ 0 ];
			RmiMsgLogClient.write( 6006009, LogMessage.F_ERROR, classname, tObj );
			throw ( new InvalidStatusException(
					"6006009" + wDelim + tObj[ 0 ] + wDelim + tObj[ 1 ] + wDelim + tObj[ 2 ] ) );
		}
	}

	//#CM706459
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(SortingPlan.STATUSFLAG).toString();
	}

	//#CM706460
	/**
	 * Set value to Plan date
	 * @param arg Plan date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM706461
	/**
	 * Fetch Plan date
	 * @return Plan date
	 */
	public String getPlanDate()
	{
		return getValue(SortingPlan.PLANDATE).toString();
	}

	//#CM706462
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM706463
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(SortingPlan.CONSIGNORCODE).toString();
	}

	//#CM706464
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM706465
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(SortingPlan.CONSIGNORNAME).toString();
	}

	//#CM706466
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM706467
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(SortingPlan.CUSTOMERCODE).toString();
	}

	//#CM706468
	/**
	 * Set value to Customer name
	 * @param arg Customer name to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM706469
	/**
	 * Fetch Customer name
	 * @return Customer name
	 */
	public String getCustomerName1()
	{
		return getValue(SortingPlan.CUSTOMERNAME1).toString();
	}

	//#CM706470
	/**
	 * Set value to Shipping ticket no
	 * @param arg Shipping ticket no to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		setValue(SHIPPINGTICKETNO, arg);
	}

	//#CM706471
	/**
	 * Fetch Shipping ticket no
	 * @return Shipping ticket no
	 */
	public String getShippingTicketNo()
	{
		return getValue(SortingPlan.SHIPPINGTICKETNO).toString();
	}

	//#CM706472
	/**
	 * Set value to Shipping line no
	 * @param arg Shipping line no to be set
	 */
	public void setShippingLineNo(int arg)
	{
		setValue(SHIPPINGLINENO, new Integer(arg));
	}

	//#CM706473
	/**
	 * Fetch Shipping line no
	 * @return Shipping line no
	 */
	public int getShippingLineNo()
	{
		return getBigDecimal(SortingPlan.SHIPPINGLINENO).intValue();
	}

	//#CM706474
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM706475
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(SortingPlan.ITEMCODE).toString();
	}

	//#CM706476
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM706477
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(SortingPlan.ITEMNAME1).toString();
	}

	//#CM706478
	/**
	 * Set value to Sorting plan qty
	 * @param arg Sorting plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM706479
	/**
	 * Fetch Sorting plan qty
	 * @return Sorting plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(SortingPlan.PLANQTY).intValue();
	}

	//#CM706480
	/**
	 * Set value to Sorting result qty
	 * @param arg Sorting result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM706481
	/**
	 * Fetch Sorting result qty
	 * @return Sorting result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(SortingPlan.RESULTQTY).intValue();
	}

	//#CM706482
	/**
	 * Set value to Storage count
	 * @param arg Storage count to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM706483
	/**
	 * Fetch Storage count
	 * @return Storage count
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(SortingPlan.SHORTAGECNT).intValue();
	}

	//#CM706484
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM706485
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(SortingPlan.ENTERINGQTY).intValue();
	}

	//#CM706486
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM706487
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(SortingPlan.BUNDLEENTERINGQTY).intValue();
	}

	//#CM706488
	/**
	 * Set value to Case/Piece flag
	 * @param arg Case/Piece flag to be set
	 */
	public void setCasePieceFlag(String arg) throws InvalidStatusException
	{
		if ( ( arg.equals( SortingPlan.CASEPIECE_FLAG_NOTHING ) ) ||
			 ( arg.equals( SortingPlan.CASEPIECE_FLAG_CASE ) )    ||
			 ( arg.equals( SortingPlan.CASEPIECE_FLAG_PIECE ) )   ||
			( arg.equals( SortingPlan.CASEPIECE_FLAG_MIX ) ) )
		{
			setValue(CASEPIECEFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[ 3 ];
			tObj[ 0 ] = this.getClass().getName();
			tObj[ 1 ] = "wCasePieceFlag";
			tObj[ 2 ] = arg;
			String classname = ( String ) tObj[ 0 ];
			RmiMsgLogClient.write( 6006009, LogMessage.F_ERROR, classname, tObj );
			throw ( new InvalidStatusException(
					"6006009" + wDelim + tObj[ 0 ] + wDelim + tObj[ 1 ] + wDelim + tObj[ 2 ] ) );
		}
	}

	//#CM706489
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(SortingPlan.CASEPIECEFLAG).toString();
	}

	//#CM706490
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM706491
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(SortingPlan.ITF).toString();
	}

	//#CM706492
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM706493
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(SortingPlan.BUNDLEITF).toString();
	}

	//#CM706494
	/**
	 * Set value to Piece Sorting location
	 * @param arg Piece Sorting location to be set
	 */
	public void setPieceLocation(String arg)
	{
		setValue(PIECELOCATION, arg);
	}

	//#CM706495
	/**
	 * Fetch Piece Sorting location
	 * @return Piece Sorting location
	 */
	public String getPieceLocation()
	{
		return getValue(SortingPlan.PIECELOCATION).toString();
	}

	//#CM706496
	/**
	 * Set value to Case Sorting location
	 * @param arg Case Sorting location to be set
	 */
	public void setCaseLocation(String arg)
	{
		setValue(CASELOCATION, arg);
	}

	//#CM706497
	/**
	 * Fetch Case Sorting location
	 * @return Case Sorting location
	 */
	public String getCaseLocation()
	{
		return getValue(SortingPlan.CASELOCATION).toString();
	}

	//#CM706498
	/**
	 * Set value to TC/DC Flag
	 * @param arg TC/DC Flag to be set
	 */
	public void setTcdcFlag(String arg)
	{
		setValue(TCDCFLAG, arg);
	}

	//#CM706499
	/**
	 * Fetch TC/DC Flag
	 * @return TC/DC Flag
	 */
	public String getTcdcFlag()
	{
		return getValue(SortingPlan.TCDCFLAG).toString();
	}

	//#CM706500
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM706501
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(SortingPlan.SUPPLIERCODE).toString();
	}

	//#CM706502
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM706503
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(SortingPlan.SUPPLIERNAME1).toString();
	}

	//#CM706504
	/**
	 * Set value to Receiving Ticket no
	 * @param arg Receiving Ticket no to be set
	 */
	public void setInstockTicketNo(String arg)
	{
		setValue(INSTOCKTICKETNO, arg);
	}

	//#CM706505
	/**
	 * Fetch Receiving Ticket no
	 * @return Receiving Ticket no
	 */
	public String getInstockTicketNo()
	{
		return getValue(SortingPlan.INSTOCKTICKETNO).toString();
	}

	//#CM706506
	/**
	 * Set value to Receiving line no
	 * @param arg Receiving line no to be set
	 */
	public void setInstockLineNo(int arg)
	{
		setValue(INSTOCKLINENO, new Integer(arg));
	}

	//#CM706507
	/**
	 * Fetch Receiving line no
	 * @return Receiving line no
	 */
	public int getInstockLineNo()
	{
		return getBigDecimal(SortingPlan.INSTOCKLINENO).intValue();
	}

	//#CM706508
	/**
	 * Set value to Use By Date
	 * @param arg Use By Date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM706509
	/**
	 * Fetch Use By Date
	 * @return Use By Date
	 */
	public String getUseByDate()
	{
		return getValue(SortingPlan.USEBYDATE).toString();
	}

	//#CM706510
	/**
	 * Set value to Lot
	 * @param arg Lot to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM706511
	/**
	 * Fetch Lot
	 * @return Lot
	 */
	public String getLotNo()
	{
		return getValue(SortingPlan.LOTNO).toString();
	}

	//#CM706512
	/**
	 * Set value to Plan
	 * @param arg Plan to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM706513
	/**
	 * Fetch Plan
	 * @return Plan
	 */
	public String getPlanInformation()
	{
		return getValue(SortingPlan.PLANINFORMATION).toString();
	}

	//#CM706514
	/**
	 * Set value to Batch No (Schedule No)
	 * @param arg Batch No (Schedule No) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM706515
	/**
	 * Fetch Batch No (Schedule No)
	 * @return Batch No (Schedule No)
	 */
	public String getBatchNo()
	{
		return getValue(SortingPlan.BATCHNO).toString();
	}

	//#CM706516
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM706517
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(SortingPlan.WORKERCODE).toString();
	}

	//#CM706518
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM706519
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(SortingPlan.WORKERNAME).toString();
	}

	//#CM706520
	/**
	 * Set value to Terminal No
	 * @param arg Terminal No to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM706521
	/**
	 * Fetch Terminal No
	 * @return Terminal No
	 */
	public String getTerminalNo()
	{
		return getValue(SortingPlan.TERMINALNO).toString();
	}

	//#CM706522
	/**
	 * Set value to Registration type
	 * @param arg Registration type to be set
	 */
	public void setRegistKind(String arg)
	{
		setValue(REGISTKIND, arg);
	}

	//#CM706523
	/**
	 * Fetch Registration type
	 * @return Registration type
	 */
	public String getRegistKind()
	{
		return getValue(SortingPlan.REGISTKIND).toString();
	}

	//#CM706524
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM706525
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(SortingPlan.REGISTDATE);
	}

	//#CM706526
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM706527
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(SortingPlan.REGISTPNAME).toString();
	}

	//#CM706528
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM706529
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(SortingPlan.LASTUPDATEDATE);
	}

	//#CM706530
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM706531
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(SortingPlan.LASTUPDATEPNAME).toString();
	}


	//#CM706532
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM706533
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(LASTUPDATEDATE, "");
		setValue(REGISTDATE, "");

		setValue(PLANQTY, new Integer(0));
		setValue(RESULTQTY, new Integer(0));
		setValue(SHORTAGECNT, new Integer(0));
		setValue(ENTERINGQTY, new Integer(0));
		setValue(BUNDLEENTERINGQTY, new Integer(0));
		setValue(INSTOCKLINENO, new Integer(0));
		setValue(SHIPPINGLINENO, new Integer(0));

	}

	/**
	 * <BR>
	 * <BR>
	 * @return
	 */
	public String[] getAutoUpdateColumnArray()
	{
		String prefix = TABLE_NAME + "." ;

		Vector autoColumn = new Vector();
		autoColumn.add(prefix + LASTUPDATEDATE);

		String[] autoColumnArray = new String[autoColumn.size()];
		autoColumn.copyInto(autoColumnArray);

		return autoColumnArray;

	}
	//------------------------------------------------------------
	//#CM706534
	// package methods
	//#CM706535
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706536
	//------------------------------------------------------------


	//#CM706537
	//------------------------------------------------------------
	//#CM706538
	// protected methods
	//#CM706539
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706540
	//------------------------------------------------------------


	//#CM706541
	//------------------------------------------------------------
	//#CM706542
	// private methods
	//#CM706543
	//------------------------------------------------------------
	//#CM706544
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + SORTINGPLANUKEY);
		lst.add(prefix + STATUSFLAG);
		lst.add(prefix + PLANDATE);
		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + CONSIGNORNAME);
		lst.add(prefix + CUSTOMERCODE);
		lst.add(prefix + CUSTOMERNAME1);
		lst.add(prefix + SHIPPINGTICKETNO);
		lst.add(prefix + SHIPPINGLINENO);
		lst.add(prefix + ITEMCODE);
		lst.add(prefix + ITEMNAME1);
		lst.add(prefix + PLANQTY);
		lst.add(prefix + RESULTQTY);
		lst.add(prefix + SHORTAGECNT);
		lst.add(prefix + ENTERINGQTY);
		lst.add(prefix + BUNDLEENTERINGQTY);
		lst.add(prefix + CASEPIECEFLAG);
		lst.add(prefix + ITF);
		lst.add(prefix + BUNDLEITF);
		lst.add(prefix + PIECELOCATION);
		lst.add(prefix + CASELOCATION);
		lst.add(prefix + TCDCFLAG);
		lst.add(prefix + SUPPLIERCODE);
		lst.add(prefix + SUPPLIERNAME1);
		lst.add(prefix + INSTOCKTICKETNO);
		lst.add(prefix + INSTOCKLINENO);
		lst.add(prefix + USEBYDATE);
		lst.add(prefix + LOTNO);
		lst.add(prefix + PLANINFORMATION);
		lst.add(prefix + BATCHNO);
		lst.add(prefix + WORKERCODE);
		lst.add(prefix + WORKERNAME);
		lst.add(prefix + TERMINALNO);
		lst.add(prefix + REGISTKIND);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM706545
	//------------------------------------------------------------
	//#CM706546
	// utility methods
	//#CM706547
	//------------------------------------------------------------
	//#CM706548
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: SortingPlan.java,v 1.5 2006/11/16 02:15:37 suresh Exp $" ;
	}
}
