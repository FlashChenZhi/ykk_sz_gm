//#CM704315
//$Id: InventoryCheck.java,v 1.5 2006/11/16 02:15:43 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM704316
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
import jp.co.daifuku.wms.base.entity.InventoryCheck;

//#CM704317
/**
 * Entity class of INVENTORYCHECK
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:43 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class InventoryCheck
		extends AbstractEntity
{
	//#CM704318
	//------------------------------------------------------------
	//#CM704319
	// class variables (prefix '$')
	//#CM704320
	//------------------------------------------------------------
	//#CM704321
	//	private String	$classVar ;
	//#CM704322
	/**
	 * Processing flag(undefined)
	 */
	public static final String STATUS_FLAG_NOTDECISION = "0" ;

	//#CM704323
	/**
	 * Processing flag(setting)
	 */
	public static final String STATUS_FLAG_DECISION = "1" ;

	//#CM704324
	/**
	 * Processing flag(Deletion)
	 */
	public static final String STATUS_FLAG_DELETE = "9" ;

	//#CM704325
	//------------------------------------------------------------
	//#CM704326
	// fields (upper case only)
	//#CM704327
	//------------------------------------------------------------
	//#CM704328
	/*
	 *  * Table name : INVENTORYCHECK
	 * Work No :                       JOBNO               varchar2(8)
	 * Stock ID :                      STOCKID             varchar2(8)
	 * Area No :                       AREANO              varchar2(16)
	 * Location No :                   LOCATIONNO          varchar2(16)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Inventory check barcode :       INVCHECKBCR         varchar2(16)
	 * Item code :                     ITEMCODE            varchar2(16)
	 * Item name :                     ITEMNAME1           varchar2(40)
	 * Stock Qty :                     STOCKQTY            number
	 * Allocation qty :                ALLOCATIONQTY       number
	 * Plan qty :                      PLANQTY             number
	 * Result Stock qty :              RESULTSTOCKQTY      number
	 * Case/Piece flag :               CASEPIECEFLAG       varchar2(1)
	 * Storage date :                  INSTOCKDATE         varchar2(8)
	 * Last Shipping date :            LASTSHIPPINGDATE    varchar2(8)
	 * Use By Date :                   USEBYDATE           varchar2(8)
	 * Lot No :                        LOTNO               varchar2(20)
	 * Plan details comment :          PLANINFORMATION     varchar2(40)
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Consignor name :                CONSIGNORNAME       varchar2(40)
	 * Supplier code :                 SUPPLIERCODE        varchar2(16)
	 * Supplier name :                 SUPPLIERNAME1       varchar2(40)
	 * Entering case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Worker name :                   WORKERNAME          varchar2(20)
	 * Terminal No :                   TERMINALNO          varchar2(3)
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM704329
	/**Table name definition*/

	public static final String TABLE_NAME = "DNINVENTORYCHECK";

	//#CM704330
	/** Column Definition (JOBNO) */

	public static final FieldName JOBNO = new FieldName("JOB_NO");

	//#CM704331
	/** Column Definition (STOCKID) */

	public static final FieldName STOCKID = new FieldName("STOCK_ID");

	//#CM704332
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM704333
	/** Column Definition (LOCATIONNO) */

	public static final FieldName LOCATIONNO = new FieldName("LOCATION_NO");

	//#CM704334
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM704335
	/** Column Definition (INVCHECKBCR) */

	public static final FieldName INVCHECKBCR = new FieldName("INVCHECK_BCR");

	//#CM704336
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM704337
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM704338
	/** Column Definition (STOCKQTY) */

	public static final FieldName STOCKQTY = new FieldName("STOCK_QTY");

	//#CM704339
	/** Column Definition (ALLOCATIONQTY) */

	public static final FieldName ALLOCATIONQTY = new FieldName("ALLOCATION_QTY");

	//#CM704340
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM704341
	/** Column Definition (RESULTSTOCKQTY) */

	public static final FieldName RESULTSTOCKQTY = new FieldName("RESULT_STOCK_QTY");

	//#CM704342
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM704343
	/** Column Definition (INSTOCKDATE) */

	public static final FieldName INSTOCKDATE = new FieldName("INSTOCK_DATE");

	//#CM704344
	/** Column Definition (LASTSHIPPINGDATE) */

	public static final FieldName LASTSHIPPINGDATE = new FieldName("LAST_SHIPPING_DATE");

	//#CM704345
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM704346
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM704347
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM704348
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM704349
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM704350
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM704351
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM704352
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM704353
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM704354
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM704355
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM704356
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM704357
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM704358
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM704359
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM704360
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM704361
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM704362
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM704363
	//------------------------------------------------------------
	//#CM704364
	// properties (prefix 'p_')
	//#CM704365
	//------------------------------------------------------------
	//#CM704366
	//	private String	p_Name ;


	//#CM704367
	//------------------------------------------------------------
	//#CM704368
	// instance variables (prefix '_')
	//#CM704369
	//------------------------------------------------------------
	//#CM704370
	//	private String	_instanceVar ;

	//#CM704371
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM704372
	//------------------------------------------------------------
	//#CM704373
	// constructors
	//#CM704374
	//------------------------------------------------------------

	//#CM704375
	/**
	 * Prepare class name list and generate instance
	 */
	public InventoryCheck()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM704376
	//------------------------------------------------------------
	//#CM704377
	// accessors
	//#CM704378
	//------------------------------------------------------------

	//#CM704379
	/**
	 * Set value to Work No
	 * @param arg Work No to be set
	 */
	public void setJobNo(String arg)
	{
		setValue(JOBNO, arg);
	}

	//#CM704380
	/**
	 * Fetch Work No
	 * @return Work No
	 */
	public String getJobNo()
	{
		return getValue(InventoryCheck.JOBNO).toString();
	}

	//#CM704381
	/**
	 * Set value to Stock ID
	 * @param arg Stock ID to be set
	 */
	public void setStockId(String arg)
	{
		setValue(STOCKID, arg);
	}

	//#CM704382
	/**
	 * Fetch Stock ID
	 * @return Stock ID
	 */
	public String getStockId()
	{
		return getValue(InventoryCheck.STOCKID).toString();
	}

	//#CM704383
	/**
	 * Set value to Area No
	 * @param arg Area No to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM704384
	/**
	 * Fetch Area No
	 * @return Area No
	 */
	public String getAreaNo()
	{
		return getValue(InventoryCheck.AREANO).toString();
	}

	//#CM704385
	/**
	 * Set value to Location No
	 * @param arg Location No to be set
	 */
	public void setLocationNo(String arg)
	{
		setValue(LOCATIONNO, arg);
	}

	//#CM704386
	/**
	 * Fetch Location No
	 * @return Location No
	 */
	public String getLocationNo()
	{
		return getValue(InventoryCheck.LOCATIONNO).toString();
	}

	//#CM704387
	/**
	 * Set value to Status flag
	 * @param arg Status flag to be set
	 */
	public void setStatusFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(STATUS_FLAG_NOTDECISION))
			|| (arg.equals(STATUS_FLAG_DECISION))
			|| (arg.equals(STATUS_FLAG_DELETE)))
		{
			setValue(STATUSFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wStatusFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM704388
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(InventoryCheck.STATUSFLAG).toString();
	}

	//#CM704389
	/**
	 * Set value to Inventory check barcode
	 * @param arg Inventory check barcode to be set
	 */
	public void setInvcheckBcr(String arg)
	{
		setValue(INVCHECKBCR, arg);
	}

	//#CM704390
	/**
	 * Fetch Inventory check barcode
	 * @return Inventory check barcode
	 */
	public String getInvcheckBcr()
	{
		return getValue(InventoryCheck.INVCHECKBCR).toString();
	}

	//#CM704391
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM704392
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(InventoryCheck.ITEMCODE).toString();
	}

	//#CM704393
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM704394
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(InventoryCheck.ITEMNAME1).toString();
	}

	//#CM704395
	/**
	 * Set value to Stock Qty
	 * @param arg Stock Qty to be set
	 */
	public void setStockQty(int arg)
	{
		setValue(STOCKQTY, new Integer(arg));
	}

	//#CM704396
	/**
	 * Fetch Stock Qty
	 * @return Stock Qty
	 */
	public int getStockQty()
	{
		return getBigDecimal(InventoryCheck.STOCKQTY).intValue();
	}

	//#CM704397
	/**
	 * Set value to Allocation qty
	 * @param arg Allocation qty to be set
	 */
	public void setAllocationQty(int arg)
	{
		setValue(ALLOCATIONQTY, new Integer(arg));
	}

	//#CM704398
	/**
	 * Fetch Allocation qty
	 * @return Allocation qty
	 */
	public int getAllocationQty()
	{
		return getBigDecimal(InventoryCheck.ALLOCATIONQTY).intValue();
	}

	//#CM704399
	/**
	 * Set value to Plan qty
	 * @param arg Plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM704400
	/**
	 * Fetch Plan qty
	 * @return Plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(InventoryCheck.PLANQTY).intValue();
	}

	//#CM704401
	/**
	 * Set value to Result Stock qty
	 * @param arg Result Stock qty to be set
	 */
	public void setResultStockQty(int arg)
	{
		setValue(RESULTSTOCKQTY, new Integer(arg));
	}

	//#CM704402
	/**
	 * Fetch Result Stock qty
	 * @return Result Stock qty
	 */
	public int getResultStockQty()
	{
		return getBigDecimal(InventoryCheck.RESULTSTOCKQTY).intValue();
	}

	//#CM704403
	/**
	 * Set value to Case/Piece flag
	 * @param arg Case/Piece flag to be set
	 */
	public void setCasePieceFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(CASEPIECE_FLAG_NOTHING))
			|| (arg.equals(CASEPIECE_FLAG_CASE))
			|| (arg.equals(CASEPIECE_FLAG_PIECE)))
		{
			setValue(CASEPIECEFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wCasePieceFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM704404
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(InventoryCheck.CASEPIECEFLAG).toString();
	}

	//#CM704405
	/**
	 * Set value to Storage date
	 * @param arg Storage date to be set
	 */
	public void setInstockDate(String arg)
	{
		setValue(INSTOCKDATE, arg);
	}

	//#CM704406
	/**
	 * Fetch Storage date
	 * @return Storage date
	 */
	public String getInstockDate()
	{
		return getValue(InventoryCheck.INSTOCKDATE).toString();
	}

	//#CM704407
	/**
	 * Set value to Last Shipping date
	 * @param arg Last Shipping date to be set
	 */
	public void setLastShippingDate(String arg)
	{
		setValue(LASTSHIPPINGDATE, arg);
	}

	//#CM704408
	/**
	 * Fetch Last Shipping date
	 * @return Last Shipping date
	 */
	public String getLastShippingDate()
	{
		return getValue(InventoryCheck.LASTSHIPPINGDATE).toString();
	}

	//#CM704409
	/**
	 * Set value to Use By Date
	 * @param arg Use By Date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM704410
	/**
	 * Fetch Use By Date
	 * @return Use By Date
	 */
	public String getUseByDate()
	{
		return getValue(InventoryCheck.USEBYDATE).toString();
	}

	//#CM704411
	/**
	 * Set value to Lot No
	 * @param arg Lot No to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM704412
	/**
	 * Fetch Lot No
	 * @return Lot No
	 */
	public String getLotNo()
	{
		return getValue(InventoryCheck.LOTNO).toString();
	}

	//#CM704413
	/**
	 * Set value to Plan details comment
	 * @param arg Plan details comment to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM704414
	/**
	 * Fetch Plan details comment
	 * @return Plan details comment
	 */
	public String getPlanInformation()
	{
		return getValue(InventoryCheck.PLANINFORMATION).toString();
	}

	//#CM704415
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM704416
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(InventoryCheck.CONSIGNORCODE).toString();
	}

	//#CM704417
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM704418
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(InventoryCheck.CONSIGNORNAME).toString();
	}

	//#CM704419
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM704420
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(InventoryCheck.SUPPLIERCODE).toString();
	}

	//#CM704421
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM704422
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(InventoryCheck.SUPPLIERNAME1).toString();
	}

	//#CM704423
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM704424
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(InventoryCheck.ENTERINGQTY).intValue();
	}

	//#CM704425
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM704426
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(InventoryCheck.BUNDLEENTERINGQTY).intValue();
	}

	//#CM704427
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM704428
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(InventoryCheck.ITF).toString();
	}

	//#CM704429
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM704430
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(InventoryCheck.BUNDLEITF).toString();
	}

	//#CM704431
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM704432
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(InventoryCheck.WORKERCODE).toString();
	}

	//#CM704433
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM704434
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(InventoryCheck.WORKERNAME).toString();
	}

	//#CM704435
	/**
	 * Set value to Terminal No
	 * @param arg Terminal No to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM704436
	/**
	 * Fetch Terminal No
	 * @return Terminal No
	 */
	public String getTerminalNo()
	{
		return getValue(InventoryCheck.TERMINALNO).toString();
	}

	//#CM704437
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM704438
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(InventoryCheck.REGISTDATE);
	}

	//#CM704439
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM704440
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(InventoryCheck.REGISTPNAME).toString();
	}

	//#CM704441
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM704442
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(InventoryCheck.LASTUPDATEDATE);
	}

	//#CM704443
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM704444
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(InventoryCheck.LASTUPDATEPNAME).toString();
	}


	//#CM704445
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM704446
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(LASTUPDATEDATE, "");
		setValue(REGISTDATE, "");

		setValue(PLANQTY, new Integer(0));
		setValue(ENTERINGQTY, new Integer(0));
		setValue(BUNDLEENTERINGQTY, new Integer(0));
		setValue(STOCKQTY, new Integer(0));
		setValue(ALLOCATIONQTY, new Integer(0));
		setValue(RESULTSTOCKQTY, new Integer(0));

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
	//#CM704447
	// package methods
	//#CM704448
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704449
	//------------------------------------------------------------


	//#CM704450
	//------------------------------------------------------------
	//#CM704451
	// protected methods
	//#CM704452
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704453
	//------------------------------------------------------------


	//#CM704454
	//------------------------------------------------------------
	//#CM704455
	// private methods
	//#CM704456
	//------------------------------------------------------------
	//#CM704457
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + JOBNO);
		lst.add(prefix + STOCKID);
		lst.add(prefix + AREANO);
		lst.add(prefix + LOCATIONNO);
		lst.add(prefix + STATUSFLAG);
		lst.add(prefix + INVCHECKBCR);
		lst.add(prefix + ITEMCODE);
		lst.add(prefix + ITEMNAME1);
		lst.add(prefix + STOCKQTY);
		lst.add(prefix + ALLOCATIONQTY);
		lst.add(prefix + PLANQTY);
		lst.add(prefix + RESULTSTOCKQTY);
		lst.add(prefix + CASEPIECEFLAG);
		lst.add(prefix + INSTOCKDATE);
		lst.add(prefix + LASTSHIPPINGDATE);
		lst.add(prefix + USEBYDATE);
		lst.add(prefix + LOTNO);
		lst.add(prefix + PLANINFORMATION);
		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + CONSIGNORNAME);
		lst.add(prefix + SUPPLIERCODE);
		lst.add(prefix + SUPPLIERNAME1);
		lst.add(prefix + ENTERINGQTY);
		lst.add(prefix + BUNDLEENTERINGQTY);
		lst.add(prefix + ITF);
		lst.add(prefix + BUNDLEITF);
		lst.add(prefix + WORKERCODE);
		lst.add(prefix + WORKERNAME);
		lst.add(prefix + TERMINALNO);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM704458
	//------------------------------------------------------------
	//#CM704459
	// utility methods
	//#CM704460
	//------------------------------------------------------------
	//#CM704461
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: InventoryCheck.java,v 1.5 2006/11/16 02:15:43 suresh Exp $" ;
	}
}
