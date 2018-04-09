//#CM705285
//$Id: Result.java,v 1.5 2006/11/16 02:15:40 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM705286
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
import jp.co.daifuku.wms.base.entity.Result;

//#CM705287
/**
 * Entity class of RESULT
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:40 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Result
		extends AbstractEntity
{
	//#CM705288
	//------------------------------------------------------------
	//#CM705289
	// class variables (prefix '$')
	//#CM705290
	//------------------------------------------------------------
	//#CM705291
	//	private String	$classVar ;

	//#CM705292
	//------------------------------------------------------------
	//#CM705293
	// fields (upper case only)
	//#CM705294
	//------------------------------------------------------------
	//#CM705295
	/*
	 *  * Table name : RESULT
	 * Work date :                     WORKDATE            varchar2(8)
	 * Work No :                       JOBNO               varchar2(8)
	 * Work type :                     JOBTYPE             varchar2(2)
	 * Collect work no :               COLLECTJOBNO        varchar2(8)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Work start flag :               BEGINNINGFLAG       varchar2(1)
	 * Plan unique key :               PLANUKEY            varchar2(16)
	 * Stock ID :                      STOCKID             varchar2(8)
	 * Area No :                       AREANO              varchar2(16)
	 * Zone No :                       ZONENO              varchar2(3)
	 * Location No :                   LOCATIONNO          varchar2(16)
	 * Plan date :                     PLANDATE            varchar2(8)
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Consignor name :                CONSIGNORNAME       varchar2(40)
	 * Supplier code :                 SUPPLIERCODE        varchar2(16)
	 * Supplier name :                 SUPPLIERNAME1       varchar2(40)
	 * Receiving ticket no :           INSTOCKTICKETNO     varchar2(16)
	 * Receiving line no :             INSTOCKLINENO       number
	 * Customer code :                 CUSTOMERCODE        varchar2(16)
	 * Customer name :                 CUSTOMERNAME1       varchar2(40)
	 * Shipping ticket no :            SHIPPINGTICKETNO    varchar2(16)
	 * Shipping line no :              SHIPPINGLINENO      number
	 * Item code :                     ITEMCODE            varchar2(16)
	 * Item name :                     ITEMNAME1           varchar2(40)
	 * Host plan qty :                 HOSTPLANQTY         number
	 * Work plan qty :                 PLANQTY             number
	 * Plan enabled qty :              PLANENABLEQTY       number
	 * Result qty :                    RESULTQTY           number
	 * Shortage qty :                  SHORTAGECNT         number
	 * Pending qty :                   PENDINGQTY          number
	 * Entering Case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case/Piece flag :               CASEPIECEFLAG       varchar2(1)
	 * Case/Piece flag (Work form flag) :WORKFORMFLAG        varchar2(1)
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * TC/DC flag :                    TCDCFLAG            varchar2(1)
	 * Use By Date :                   USEBYDATE           varchar2(8)
	 * Lot no :                        LOTNO               varchar2(20)
	 * Plan detail comments :          PLANINFORMATION     varchar2(40)
	 * Order no :                      ORDERNO             varchar2(16)
	 * Order sequence no :             ORDERSEQNO          number
	 * Ordering date :                 ORDERINGDATE        varchar2(8)
	 * Result use by date :            RESULTUSEBYDATE     varchar2(8)
	 * Result lot no :                 RESULTLOTNO         varchar2(20)
	 * Result location no :            RESULTLOCATIONNO    varchar2(16)
	 * Report flag :                   REPORTFLAG          varchar2(1)
	 * Batch no (Schedule no) :        BATCHNO             varchar2(8)
	 * System connection key :         SYSTEMCONNKEY       varchar2(16)
	 * System identification key :     SYSTEMDISCKEY       number
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Worker name :                   WORKERNAME          varchar2(20)
	 * Terminal no :                   TERMINALNO          varchar2(3)
	 * Plan registration date :        PLANREGISTDATE      date
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM705296
	/**Table name definition*/

	public static final String TABLE_NAME = "DNRESULT";

	//#CM705297
	/** Column Definition (WORKDATE) */

	public static final FieldName WORKDATE = new FieldName("WORK_DATE");

	//#CM705298
	/** Column Definition (JOBNO) */

	public static final FieldName JOBNO = new FieldName("JOB_NO");

	//#CM705299
	/** Column Definition (JOBTYPE) */

	public static final FieldName JOBTYPE = new FieldName("JOB_TYPE");

	//#CM705300
	/** Column Definition (COLLECTJOBNO) */

	public static final FieldName COLLECTJOBNO = new FieldName("COLLECT_JOB_NO");

	//#CM705301
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM705302
	/** Column Definition (BEGINNINGFLAG) */

	public static final FieldName BEGINNINGFLAG = new FieldName("BEGINNING_FLAG");

	//#CM705303
	/** Column Definition (PLANUKEY) */

	public static final FieldName PLANUKEY = new FieldName("PLAN_UKEY");

	//#CM705304
	/** Column Definition (STOCKID) */

	public static final FieldName STOCKID = new FieldName("STOCK_ID");

	//#CM705305
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM705306
	/** Column Definition (ZONENO) */

	public static final FieldName ZONENO = new FieldName("ZONE_NO");

	//#CM705307
	/** Column Definition (LOCATIONNO) */

	public static final FieldName LOCATIONNO = new FieldName("LOCATION_NO");

	//#CM705308
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM705309
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM705310
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM705311
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM705312
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM705313
	/** Column Definition (INSTOCKTICKETNO) */

	public static final FieldName INSTOCKTICKETNO = new FieldName("INSTOCK_TICKET_NO");

	//#CM705314
	/** Column Definition (INSTOCKLINENO) */

	public static final FieldName INSTOCKLINENO = new FieldName("INSTOCK_LINE_NO");

	//#CM705315
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM705316
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM705317
	/** Column Definition (SHIPPINGTICKETNO) */

	public static final FieldName SHIPPINGTICKETNO = new FieldName("SHIPPING_TICKET_NO");

	//#CM705318
	/** Column Definition (SHIPPINGLINENO) */

	public static final FieldName SHIPPINGLINENO = new FieldName("SHIPPING_LINE_NO");

	//#CM705319
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM705320
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM705321
	/** Column Definition (HOSTPLANQTY) */

	public static final FieldName HOSTPLANQTY = new FieldName("HOST_PLAN_QTY");

	//#CM705322
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM705323
	/** Column Definition (PLANENABLEQTY) */

	public static final FieldName PLANENABLEQTY = new FieldName("PLAN_ENABLE_QTY");

	//#CM705324
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM705325
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM705326
	/** Column Definition (PENDINGQTY) */

	public static final FieldName PENDINGQTY = new FieldName("PENDING_QTY");

	//#CM705327
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM705328
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM705329
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM705330
	/** Column Definition (WORKFORMFLAG) */

	public static final FieldName WORKFORMFLAG = new FieldName("WORK_FORM_FLAG");

	//#CM705331
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM705332
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM705333
	/** Column Definition (TCDCFLAG) */

	public static final FieldName TCDCFLAG = new FieldName("TCDC_FLAG");

	//#CM705334
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM705335
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM705336
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM705337
	/** Column Definition (ORDERNO) */

	public static final FieldName ORDERNO = new FieldName("ORDER_NO");

	//#CM705338
	/** Column Definition (ORDERSEQNO) */

	public static final FieldName ORDERSEQNO = new FieldName("ORDER_SEQ_NO");

	//#CM705339
	/** Column Definition (ORDERINGDATE) */

	public static final FieldName ORDERINGDATE = new FieldName("ORDERING_DATE");

	//#CM705340
	/** Column Definition (RESULTUSEBYDATE) */

	public static final FieldName RESULTUSEBYDATE = new FieldName("RESULT_USE_BY_DATE");

	//#CM705341
	/** Column Definition (RESULTLOTNO) */

	public static final FieldName RESULTLOTNO = new FieldName("RESULT_LOT_NO");

	//#CM705342
	/** Column Definition (RESULTLOCATIONNO) */

	public static final FieldName RESULTLOCATIONNO = new FieldName("RESULT_LOCATION_NO");

	//#CM705343
	/** Column Definition (REPORTFLAG) */

	public static final FieldName REPORTFLAG = new FieldName("REPORT_FLAG");

	//#CM705344
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM705345
	/** Column Definition (SYSTEMCONNKEY) */

	public static final FieldName SYSTEMCONNKEY = new FieldName("SYSTEM_CONN_KEY");

	//#CM705346
	/** Column Definition (SYSTEMDISCKEY) */

	public static final FieldName SYSTEMDISCKEY = new FieldName("SYSTEM_DISC_KEY");

	//#CM705347
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM705348
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM705349
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM705350
	/** Column Definition (PLANREGISTDATE) */

	public static final FieldName PLANREGISTDATE = new FieldName("PLAN_REGIST_DATE");

	//#CM705351
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM705352
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM705353
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM705354
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM705355
	//------------------------------------------------------------
	//#CM705356
	// properties (prefix 'p_')
	//#CM705357
	//------------------------------------------------------------
	//#CM705358
	//	private String	p_Name ;


	//#CM705359
	//------------------------------------------------------------
	//#CM705360
	// instance variables (prefix '_')
	//#CM705361
	//------------------------------------------------------------
	//#CM705362
	//	private String	_instanceVar ;

	//#CM705363
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM705364
	//------------------------------------------------------------
	//#CM705365
	// constructors
	//#CM705366
	//------------------------------------------------------------

	//#CM705367
	/**
	 * Prepare class name list and generate instance
	 */
	public Result()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM705368
	//------------------------------------------------------------
	//#CM705369
	// accessors
	//#CM705370
	//------------------------------------------------------------

	//#CM705371
	/**
	 * Set value to Work date
	 * @param arg Work date to be set
	 */
	public void setWorkDate(String arg)
	{
		setValue(WORKDATE, arg);
	}

	//#CM705372
	/**
	 * Fetch Work date
	 * @return Work date
	 */
	public String getWorkDate()
	{
		return getValue(Result.WORKDATE).toString();
	}

	//#CM705373
	/**
	 * Set value to Work No
	 * @param arg Work No to be set
	 */
	public void setJobNo(String arg)
	{
		setValue(JOBNO, arg);
	}

	//#CM705374
	/**
	 * Fetch Work No
	 * @return Work No
	 */
	public String getJobNo()
	{
		return getValue(Result.JOBNO).toString();
	}

	//#CM705375
	/**
	 * Set value to Work type
	 * @param arg Work type to be set
	 */
	public void setJobType(String arg) throws InvalidStatusException
	{
if ((arg.equals(JOB_TYPE_INSTOCK))
			|| (arg.equals(JOB_TYPE_STORAGE))
			|| (arg.equals(JOB_TYPE_RETRIEVAL))
			|| (arg.equals(JOB_TYPE_SORTING))
			|| (arg.equals(JOB_TYPE_SHIPINSPECTION))
			|| (arg.equals(JOB_TYPE_MOVEMENT_RETRIEVAL))
			|| (arg.equals(JOB_TYPE_MOVEMENT_STORAGE))
			|| (arg.equals(JOB_TYPE_EX_STORAGE))
			|| (arg.equals(JOB_TYPE_EX_RETRIEVAL))
			|| (arg.equals(JOB_TYPE_MAINTENANCE_PLUS))
			|| (arg.equals(JOB_TYPE_MAINTENANCE_MINUS))
			|| (arg.equals(JOB_TYPE_INVENTORY))
			|| (arg.equals(JOB_TYPE_INVENTORY_PLUS))
			|| (arg.equals(JOB_TYPE_INVENTORY_MINUS)))
		{
			setValue(JOBTYPE, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wJobType";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009"
						+ wDelim
						+ tObj[0]
						+ wDelim
						+ tObj[1]
						+ wDelim
						+ tObj[2]));
		}
	}

	//#CM705376
	/**
	 * Fetch Work type
	 * @return Work type
	 */
	public String getJobType()
	{
		return getValue(Result.JOBTYPE).toString();
	}

	//#CM705377
	/**
	 * Set value to Collect work no
	 * @param arg Collect work no to be set
	 */
	public void setCollectJobNo(String arg)
	{
		setValue(COLLECTJOBNO, arg);
	}

	//#CM705378
	/**
	 * Fetch Collect work no
	 * @return Collect work no
	 */
	public String getCollectJobNo()
	{
		return getValue(Result.COLLECTJOBNO).toString();
	}

	//#CM705379
	/**
	 * Set value to Status flag
	 * @param arg Status flag to be set
	 */
	public void setStatusFlag(String arg) throws InvalidStatusException
	{
if ((arg.equals(STATUS_FLAG_UNSTART))
			|| (arg.equals(STATUS_FLAG_START))
			|| (arg.equals(STATUS_FLAG_NOWWORKING))
			|| (arg.equals(STATUS_FLAG_COMPLETE_IN_PART))
			|| (arg.equals(STATUS_FLAG_COMPLETION))
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
					"6006009"
						+ wDelim
						+ tObj[0]
						+ wDelim
						+ tObj[1]
						+ wDelim
						+ tObj[2]));
		}
	}

	//#CM705380
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(Result.STATUSFLAG).toString();
	}

	//#CM705381
	/**
	 * Set value to Work start flag
	 * @param arg Work start flag to be set
	 */
	public void setBeginningFlag(String arg)
		throws InvalidStatusException
	{
		if ((arg.equals(BEGINNING_FLAG_NOT_STARTED))
			|| (arg.equals(BEGINNING_FLAG_STARTED)))
		{
			setValue(BEGINNINGFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wBeginningFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009"
						+ wDelim
						+ tObj[0]
						+ wDelim
						+ tObj[1]
						+ wDelim
						+ tObj[2]));
		}
	}

	//#CM705382
	/**
	 * Fetch Work start flag
	 * @return Work start flag
	 */
	public String getBeginningFlag()
	{
		return getValue(Result.BEGINNINGFLAG).toString();
	}

	//#CM705383
	/**
	 * Set value to Plan unique key
	 * @param arg Plan unique key to be set
	 */
	public void setPlanUkey(String arg)
	{
		setValue(PLANUKEY, arg);
	}

	//#CM705384
	/**
	 * Fetch Plan unique key
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return getValue(Result.PLANUKEY).toString();
	}

	//#CM705385
	/**
	 * Set value to Stock ID
	 * @param arg Stock ID to be set
	 */
	public void setStockId(String arg)
	{
		setValue(STOCKID, arg);
	}

	//#CM705386
	/**
	 * Fetch Stock ID
	 * @return Stock ID
	 */
	public String getStockId()
	{
		return getValue(Result.STOCKID).toString();
	}

	//#CM705387
	/**
	 * Set value to Area No
	 * @param arg Area No to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM705388
	/**
	 * Fetch Area No
	 * @return Area No
	 */
	public String getAreaNo()
	{
		return getValue(Result.AREANO).toString();
	}

	//#CM705389
	/**
	 * Set value to Zone No
	 * @param arg Zone No to be set
	 */
	public void setZoneNo(String arg)
	{
		setValue(ZONENO, arg);
	}

	//#CM705390
	/**
	 * Fetch Zone No
	 * @return Zone No
	 */
	public String getZoneNo()
	{
		return getValue(Result.ZONENO).toString();
	}

	//#CM705391
	/**
	 * Set value to Location No
	 * @param arg Location No to be set
	 */
	public void setLocationNo(String arg)
	{
		setValue(LOCATIONNO, arg);
	}

	//#CM705392
	/**
	 * Fetch Location No
	 * @return Location No
	 */
	public String getLocationNo()
	{
		return getValue(Result.LOCATIONNO).toString();
	}

	//#CM705393
	/**
	 * Set value to Plan date
	 * @param arg Plan date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM705394
	/**
	 * Fetch Plan date
	 * @return Plan date
	 */
	public String getPlanDate()
	{
		return getValue(Result.PLANDATE).toString();
	}

	//#CM705395
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM705396
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(Result.CONSIGNORCODE).toString();
	}

	//#CM705397
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM705398
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(Result.CONSIGNORNAME).toString();
	}

	//#CM705399
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM705400
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(Result.SUPPLIERCODE).toString();
	}

	//#CM705401
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM705402
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(Result.SUPPLIERNAME1).toString();
	}

	//#CM705403
	/**
	 * Set value to Receiving ticket no
	 * @param arg Receiving ticket no to be set
	 */
	public void setInstockTicketNo(String arg)
	{
		setValue(INSTOCKTICKETNO, arg);
	}

	//#CM705404
	/**
	 * Fetch Receiving ticket no
	 * @return Receiving ticket no
	 */
	public String getInstockTicketNo()
	{
		return getValue(Result.INSTOCKTICKETNO).toString();
	}

	//#CM705405
	/**
	 * Set value to Receiving line no
	 * @param arg Receiving line no to be set
	 */
	public void setInstockLineNo(int arg)
	{
		setValue(INSTOCKLINENO, new Integer(arg));
	}

	//#CM705406
	/**
	 * Fetch Receiving line no
	 * @return Receiving line no
	 */
	public int getInstockLineNo()
	{
		return getBigDecimal(Result.INSTOCKLINENO).intValue();
	}

	//#CM705407
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM705408
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(Result.CUSTOMERCODE).toString();
	}

	//#CM705409
	/**
	 * Set value to Customer name
	 * @param arg Customer name to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM705410
	/**
	 * Fetch Customer name
	 * @return Customer name
	 */
	public String getCustomerName1()
	{
		return getValue(Result.CUSTOMERNAME1).toString();
	}

	//#CM705411
	/**
	 * Set value to Shipping ticket no
	 * @param arg Shipping ticket no to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		setValue(SHIPPINGTICKETNO, arg);
	}

	//#CM705412
	/**
	 * Fetch Shipping ticket no
	 * @return Shipping ticket no
	 */
	public String getShippingTicketNo()
	{
		return getValue(Result.SHIPPINGTICKETNO).toString();
	}

	//#CM705413
	/**
	 * Set value to Shipping line no
	 * @param arg Shipping line no to be set
	 */
	public void setShippingLineNo(int arg)
	{
		setValue(SHIPPINGLINENO, new Integer(arg));
	}

	//#CM705414
	/**
	 * Fetch Shipping line no
	 * @return Shipping line no
	 */
	public int getShippingLineNo()
	{
		return getBigDecimal(Result.SHIPPINGLINENO).intValue();
	}

	//#CM705415
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM705416
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(Result.ITEMCODE).toString();
	}

	//#CM705417
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM705418
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(Result.ITEMNAME1).toString();
	}

	//#CM705419
	/**
	 * Set value to Host plan qty
	 * @param arg Host plan qty to be set
	 */
	public void setHostPlanQty(int arg)
	{
		setValue(HOSTPLANQTY, new Integer(arg));
	}

	//#CM705420
	/**
	 * Fetch Host plan qty
	 * @return Host plan qty
	 */
	public int getHostPlanQty()
	{
		return getBigDecimal(Result.HOSTPLANQTY).intValue();
	}

	//#CM705421
	/**
	 * Set value to Work plan qty
	 * @param arg Work plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM705422
	/**
	 * Fetch Work plan qty
	 * @return Work plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(Result.PLANQTY).intValue();
	}

	//#CM705423
	/**
	 * Set value to Plan enabled qty
	 * @param arg Plan enabled qty to be set
	 */
	public void setPlanEnableQty(int arg)
	{
		setValue(PLANENABLEQTY, new Integer(arg));
	}

	//#CM705424
	/**
	 * Fetch Plan enabled qty
	 * @return Plan enabled qty
	 */
	public int getPlanEnableQty()
	{
		return getBigDecimal(Result.PLANENABLEQTY).intValue();
	}

	//#CM705425
	/**
	 * Set value to Result qty
	 * @param arg Result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM705426
	/**
	 * Fetch Result qty
	 * @return Result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(Result.RESULTQTY).intValue();
	}

	//#CM705427
	/**
	 * Set value to Shortage qty
	 * @param arg Shortage qty to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM705428
	/**
	 * Fetch Shortage qty
	 * @return Shortage qty
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(Result.SHORTAGECNT).intValue();
	}

	//#CM705429
	/**
	 * Set value to Pending qty
	 * @param arg Pending qty to be set
	 */
	public void setPendingQty(int arg)
	{
		setValue(PENDINGQTY, new Integer(arg));
	}

	//#CM705430
	/**
	 * Fetch Pending qty
	 * @return Pending qty
	 */
	public int getPendingQty()
	{
		return getBigDecimal(Result.PENDINGQTY).intValue();
	}

	//#CM705431
	/**
	 * Set value to Entering Case qty
	 * @param arg Entering Case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM705432
	/**
	 * Fetch Entering Case qty
	 * @return Entering Case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(Result.ENTERINGQTY).intValue();
	}

	//#CM705433
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM705434
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(Result.BUNDLEENTERINGQTY).intValue();
	}

	//#CM705435
	/**
	 * Set value to Case/Piece flag
	 * @param arg Case/Piece flag to be set
	 */
	public void setCasePieceFlag(String arg)
		throws InvalidStatusException
	{
		if ((arg.equals(CASEPIECE_FLAG_NOTHING))
			|| (arg.equals(CASEPIECE_FLAG_CASE))
			|| (arg.equals(CASEPIECE_FLAG_PIECE))
			|| (arg.equals(CASEPIECE_FLAG_MIX)))
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
					"6006009"
						+ wDelim
						+ tObj[0]
						+ wDelim
						+ tObj[1]
						+ wDelim
						+ tObj[2]));
		}
	}

	//#CM705436
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(Result.CASEPIECEFLAG).toString();
	}

	//#CM705437
	/**
	 * Set value to Case/Piece flag (Work form flag)
	 * @param arg Case/Piece flag (Work form flag) to be set
	 */
	public void setWorkFormFlag(String arg)
		throws InvalidStatusException
	{
		if ((arg.equals(CASEPIECE_FLAG_NOTHING))
			|| (arg.equals(CASEPIECE_FLAG_CASE))
			|| (arg.equals(CASEPIECE_FLAG_PIECE))
			|| (arg.equals(CASEPIECE_FLAG_MIX)))
		{
			setValue(WORKFORMFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wWorkFromFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009"
						+ wDelim
						+ tObj[0]
						+ wDelim
						+ tObj[1]
						+ wDelim
						+ tObj[2]));
		}
	}

	//#CM705438
	/**
	 * Fetch Case/Piece flag (Work form flag)
	 * @return Case/Piece flag (Work form flag)
	 */
	public String getWorkFormFlag()
	{
		return getValue(Result.WORKFORMFLAG).toString();
	}

	//#CM705439
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM705440
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(Result.ITF).toString();
	}

	//#CM705441
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM705442
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(Result.BUNDLEITF).toString();
	}

	//#CM705443
	/**
	 * Set value to TC/DC flag
	 * @param arg TC/DC flag to be set
	 */
	public void setTcdcFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(TCDC_FLAG_DC))
			|| (arg.equals(TCDC_FLAG_CROSSTC))
			|| (arg.equals(TCDC_FLAG_TC)))
		{
			setValue(TCDCFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wTcdcFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009"
						+ wDelim
						+ tObj[0]
						+ wDelim
						+ tObj[1]
						+ wDelim
						+ tObj[2]));
		}
	}

	//#CM705444
	/**
	 * Fetch TC/DC flag
	 * @return TC/DC flag
	 */
	public String getTcdcFlag()
	{
		return getValue(Result.TCDCFLAG).toString();
	}

	//#CM705445
	/**
	 * Set value to Use By Date
	 * @param arg Use By Date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM705446
	/**
	 * Fetch Use By Date
	 * @return Use By Date
	 */
	public String getUseByDate()
	{
		return getValue(Result.USEBYDATE).toString();
	}

	//#CM705447
	/**
	 * Set value to Lot no
	 * @param arg Lot no to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM705448
	/**
	 * Fetch Lot no
	 * @return Lot no
	 */
	public String getLotNo()
	{
		return getValue(Result.LOTNO).toString();
	}

	//#CM705449
	/**
	 * Set value to Plan detail comments
	 * @param arg Plan detail comments to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM705450
	/**
	 * Fetch Plan detail comments
	 * @return Plan detail comments
	 */
	public String getPlanInformation()
	{
		return getValue(Result.PLANINFORMATION).toString();
	}

	//#CM705451
	/**
	 * Set value to Order no
	 * @param arg Order no to be set
	 */
	public void setOrderNo(String arg)
	{
		setValue(ORDERNO, arg);
	}

	//#CM705452
	/**
	 * Fetch Order no
	 * @return Order no
	 */
	public String getOrderNo()
	{
		return getValue(Result.ORDERNO).toString();
	}

	//#CM705453
	/**
	 * Set value to Order sequence no
	 * @param arg Order sequence no to be set
	 */
	public void setOrderSeqNo(int arg)
	{
		setValue(ORDERSEQNO, new Integer(arg));
	}

	//#CM705454
	/**
	 * Fetch Order sequence no
	 * @return Order sequence no
	 */
	public int getOrderSeqNo()
	{
		return getBigDecimal(Result.ORDERSEQNO).intValue();
	}

	//#CM705455
	/**
	 * Set value to Ordering date
	 * @param arg Ordering date to be set
	 */
	public void setOrderingDate(String arg)
	{
		setValue(ORDERINGDATE, arg);
	}

	//#CM705456
	/**
	 * Fetch Ordering date
	 * @return Ordering date
	 */
	public String getOrderingDate()
	{
		return getValue(Result.ORDERINGDATE).toString();
	}

	//#CM705457
	/**
	 * Set value to Result use by date
	 * @param arg Result use by date to be set
	 */
	public void setResultUseByDate(String arg)
	{
		setValue(RESULTUSEBYDATE, arg);
	}

	//#CM705458
	/**
	 * Fetch Result use by date
	 * @return Result use by date
	 */
	public String getResultUseByDate()
	{
		return getValue(Result.RESULTUSEBYDATE).toString();
	}

	//#CM705459
	/**
	 * Set value to Result lot no
	 * @param arg Result lot no to be set
	 */
	public void setResultLotNo(String arg)
	{
		setValue(RESULTLOTNO, arg);
	}

	//#CM705460
	/**
	 * Fetch Result lot no
	 * @return Result lot no
	 */
	public String getResultLotNo()
	{
		return getValue(Result.RESULTLOTNO).toString();
	}

	//#CM705461
	/**
	 * Set value to Result location no
	 * @param arg Result location no to be set
	 */
	public void setResultLocationNo(String arg)
	{
		setValue(RESULTLOCATIONNO, arg);
	}

	//#CM705462
	/**
	 * Fetch Result location no
	 * @return Result location no
	 */
	public String getResultLocationNo()
	{
		return getValue(Result.RESULTLOCATIONNO).toString();
	}

	//#CM705463
	/**
	 * Set value to Report flag
	 * @param arg Report flag to be set
	 */
	public void setReportFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(REPORT_FLAG_NOT_SENT))
			|| (arg.equals(REPORT_FLAG_SENT)))
		{
			setValue(REPORTFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wReportFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009"
						+ wDelim
						+ tObj[0]
						+ wDelim
						+ tObj[1]
						+ wDelim
						+ tObj[2]));
		}
	}

	//#CM705464
	/**
	 * Fetch Report flag
	 * @return Report flag
	 */
	public String getReportFlag()
	{
		return getValue(Result.REPORTFLAG).toString();
	}

	//#CM705465
	/**
	 * Set value to Batch no (Schedule no)
	 * @param arg Batch no (Schedule no) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM705466
	/**
	 * Fetch Batch no (Schedule no)
	 * @return Batch no (Schedule no)
	 */
	public String getBatchNo()
	{
		return getValue(Result.BATCHNO).toString();
	}

	//#CM705467
	/**
	 * Set value to System connection key
	 * @param arg System connection key to be set
	 */
	public void setSystemConnKey(String arg)
	{
		setValue(SYSTEMCONNKEY, arg);
	}

	//#CM705468
	/**
	 * Fetch System connection key
	 * @return System connection key
	 */
	public String getSystemConnKey()
	{
		return getValue(Result.SYSTEMCONNKEY).toString();
	}

	//#CM705469
	/**
	 * Set value to System identification key
	 * @param arg System identification key to be set
	 */
	public void setSystemDiscKey(int arg)
	{
		setValue(SYSTEMDISCKEY, new Integer(arg));
	}

	//#CM705470
	/**
	 * Fetch System identification key
	 * @return System identification key
	 */
	public int getSystemDiscKey()
	{
		return getBigDecimal(Result.SYSTEMDISCKEY).intValue();
	}

	//#CM705471
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM705472
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(Result.WORKERCODE).toString();
	}

	//#CM705473
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM705474
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(Result.WORKERNAME).toString();
	}

	//#CM705475
	/**
	 * Set value to Terminal no
	 * @param arg Terminal no to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM705476
	/**
	 * Fetch Terminal no
	 * @return Terminal no
	 */
	public String getTerminalNo()
	{
		return getValue(Result.TERMINALNO).toString();
	}

	//#CM705477
	/**
	 * Set value to Plan registration date
	 * @param arg Plan registration date to be set
	 */
	public void setPlanRegistDate(Date arg)
	{
		setValue(PLANREGISTDATE, arg);
	}

	//#CM705478
	/**
	 * Fetch Plan registration date
	 * @return Plan registration date
	 */
	public Date getPlanRegistDate()
	{
		return (Date)getValue(Result.PLANREGISTDATE);
	}

	//#CM705479
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM705480
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(Result.REGISTDATE);
	}

	//#CM705481
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM705482
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(Result.REGISTPNAME).toString();
	}

	//#CM705483
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM705484
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Result.LASTUPDATEDATE);
	}

	//#CM705485
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM705486
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Result.LASTUPDATEPNAME).toString();
	}


	//#CM705487
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM705488
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(LASTUPDATEDATE, "");
		setValue(REGISTDATE, "");
		setValue(HOSTPLANQTY, new Integer(0));
		setValue(PLANQTY, new Integer(0));
		setValue(PLANENABLEQTY, new Integer(0));
		setValue(RESULTQTY, new Integer(0));
		setValue(SHORTAGECNT, new Integer(0));
		setValue(PENDINGQTY, new Integer(0));
		setValue(ENTERINGQTY, new Integer(0));
		setValue(BUNDLEENTERINGQTY, new Integer(0));
		setValue(ORDERSEQNO, new Integer(0));
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
	//#CM705489
	// package methods
	//#CM705490
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705491
	//------------------------------------------------------------


	//#CM705492
	//------------------------------------------------------------
	//#CM705493
	// protected methods
	//#CM705494
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705495
	//------------------------------------------------------------


	//#CM705496
	//------------------------------------------------------------
	//#CM705497
	// private methods
	//#CM705498
	//------------------------------------------------------------
	//#CM705499
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + WORKDATE);
		lst.add(prefix + JOBNO);
		lst.add(prefix + JOBTYPE);
		lst.add(prefix + COLLECTJOBNO);
		lst.add(prefix + STATUSFLAG);
		lst.add(prefix + BEGINNINGFLAG);
		lst.add(prefix + PLANUKEY);
		lst.add(prefix + STOCKID);
		lst.add(prefix + AREANO);
		lst.add(prefix + ZONENO);
		lst.add(prefix + LOCATIONNO);
		lst.add(prefix + PLANDATE);
		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + CONSIGNORNAME);
		lst.add(prefix + SUPPLIERCODE);
		lst.add(prefix + SUPPLIERNAME1);
		lst.add(prefix + INSTOCKTICKETNO);
		lst.add(prefix + INSTOCKLINENO);
		lst.add(prefix + CUSTOMERCODE);
		lst.add(prefix + CUSTOMERNAME1);
		lst.add(prefix + SHIPPINGTICKETNO);
		lst.add(prefix + SHIPPINGLINENO);
		lst.add(prefix + ITEMCODE);
		lst.add(prefix + ITEMNAME1);
		lst.add(prefix + HOSTPLANQTY);
		lst.add(prefix + PLANQTY);
		lst.add(prefix + PLANENABLEQTY);
		lst.add(prefix + RESULTQTY);
		lst.add(prefix + SHORTAGECNT);
		lst.add(prefix + PENDINGQTY);
		lst.add(prefix + ENTERINGQTY);
		lst.add(prefix + BUNDLEENTERINGQTY);
		lst.add(prefix + CASEPIECEFLAG);
		lst.add(prefix + WORKFORMFLAG);
		lst.add(prefix + ITF);
		lst.add(prefix + BUNDLEITF);
		lst.add(prefix + TCDCFLAG);
		lst.add(prefix + USEBYDATE);
		lst.add(prefix + LOTNO);
		lst.add(prefix + PLANINFORMATION);
		lst.add(prefix + ORDERNO);
		lst.add(prefix + ORDERSEQNO);
		lst.add(prefix + ORDERINGDATE);
		lst.add(prefix + RESULTUSEBYDATE);
		lst.add(prefix + RESULTLOTNO);
		lst.add(prefix + RESULTLOCATIONNO);
		lst.add(prefix + REPORTFLAG);
		lst.add(prefix + BATCHNO);
		lst.add(prefix + SYSTEMCONNKEY);
		lst.add(prefix + SYSTEMDISCKEY);
		lst.add(prefix + WORKERCODE);
		lst.add(prefix + WORKERNAME);
		lst.add(prefix + TERMINALNO);
		lst.add(prefix + PLANREGISTDATE);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM705500
	//------------------------------------------------------------
	//#CM705501
	// utility methods
	//#CM705502
	//------------------------------------------------------------
	//#CM705503
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Result.java,v 1.5 2006/11/16 02:15:40 suresh Exp $" ;
	}
}
