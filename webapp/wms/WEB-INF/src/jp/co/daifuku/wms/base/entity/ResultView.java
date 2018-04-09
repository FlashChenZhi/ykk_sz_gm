//#CM705504
//$Id: ResultView.java,v 1.4 2006/11/13 04:31:01 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM705505
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;

import jp.co.daifuku.common.InvalidStatusException;
import jp.co.daifuku.common.LogMessage;
import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.ResultView;

//#CM705506
/**
 * Entity class of DVRESULTVIEW
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
 * @version $Revision: 1.4 $, $Date: 2006/11/13 04:31:01 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ResultView
		extends AbstractEntity
{
	//#CM705507
	//------------------------------------------------------------
	//#CM705508
	// class variables (prefix '$')
	//#CM705509
	//------------------------------------------------------------
	//#CM705510
	//	private String	$classVar ;

	//#CM705511
	//------------------------------------------------------------
	//#CM705512
	// fields (upper case only)
	//#CM705513
	//------------------------------------------------------------
	//#CM705514
	/*
	 *  * Table name : DVRESULTVIEW
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

	//#CM705515
	/**Table name definition*/

	public static final String TABLE_NAME = "DVRESULTVIEW";

	//#CM705516
	/** Column Definition (WORKDATE) */

	public static final FieldName WORKDATE = new FieldName("WORK_DATE");

	//#CM705517
	/** Column Definition (JOBNO) */

	public static final FieldName JOBNO = new FieldName("JOB_NO");

	//#CM705518
	/** Column Definition (JOBTYPE) */

	public static final FieldName JOBTYPE = new FieldName("JOB_TYPE");

	//#CM705519
	/** Column Definition (COLLECTJOBNO) */

	public static final FieldName COLLECTJOBNO = new FieldName("COLLECT_JOB_NO");

	//#CM705520
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM705521
	/** Column Definition (BEGINNINGFLAG) */

	public static final FieldName BEGINNINGFLAG = new FieldName("BEGINNING_FLAG");

	//#CM705522
	/** Column Definition (PLANUKEY) */

	public static final FieldName PLANUKEY = new FieldName("PLAN_UKEY");

	//#CM705523
	/** Column Definition (STOCKID) */

	public static final FieldName STOCKID = new FieldName("STOCK_ID");

	//#CM705524
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM705525
	/** Column Definition (ZONENO) */

	public static final FieldName ZONENO = new FieldName("ZONE_NO");

	//#CM705526
	/** Column Definition (LOCATIONNO) */

	public static final FieldName LOCATIONNO = new FieldName("LOCATION_NO");

	//#CM705527
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM705528
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM705529
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM705530
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM705531
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM705532
	/** Column Definition (INSTOCKTICKETNO) */

	public static final FieldName INSTOCKTICKETNO = new FieldName("INSTOCK_TICKET_NO");

	//#CM705533
	/** Column Definition (INSTOCKLINENO) */

	public static final FieldName INSTOCKLINENO = new FieldName("INSTOCK_LINE_NO");

	//#CM705534
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM705535
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM705536
	/** Column Definition (SHIPPINGTICKETNO) */

	public static final FieldName SHIPPINGTICKETNO = new FieldName("SHIPPING_TICKET_NO");

	//#CM705537
	/** Column Definition (SHIPPINGLINENO) */

	public static final FieldName SHIPPINGLINENO = new FieldName("SHIPPING_LINE_NO");

	//#CM705538
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM705539
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM705540
	/** Column Definition (HOSTPLANQTY) */

	public static final FieldName HOSTPLANQTY = new FieldName("HOST_PLAN_QTY");

	//#CM705541
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM705542
	/** Column Definition (PLANENABLEQTY) */

	public static final FieldName PLANENABLEQTY = new FieldName("PLAN_ENABLE_QTY");

	//#CM705543
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM705544
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM705545
	/** Column Definition (PENDINGQTY) */

	public static final FieldName PENDINGQTY = new FieldName("PENDING_QTY");

	//#CM705546
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM705547
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM705548
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM705549
	/** Column Definition (WORKFORMFLAG) */

	public static final FieldName WORKFORMFLAG = new FieldName("WORK_FORM_FLAG");

	//#CM705550
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM705551
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM705552
	/** Column Definition (TCDCFLAG) */

	public static final FieldName TCDCFLAG = new FieldName("TCDC_FLAG");

	//#CM705553
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM705554
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM705555
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM705556
	/** Column Definition (ORDERNO) */

	public static final FieldName ORDERNO = new FieldName("ORDER_NO");

	//#CM705557
	/** Column Definition (ORDERSEQNO) */

	public static final FieldName ORDERSEQNO = new FieldName("ORDER_SEQ_NO");

	//#CM705558
	/** Column Definition (ORDERINGDATE) */

	public static final FieldName ORDERINGDATE = new FieldName("ORDERING_DATE");

	//#CM705559
	/** Column Definition (RESULTUSEBYDATE) */

	public static final FieldName RESULTUSEBYDATE = new FieldName("RESULT_USE_BY_DATE");

	//#CM705560
	/** Column Definition (RESULTLOTNO) */

	public static final FieldName RESULTLOTNO = new FieldName("RESULT_LOT_NO");

	//#CM705561
	/** Column Definition (RESULTLOCATIONNO) */

	public static final FieldName RESULTLOCATIONNO = new FieldName("RESULT_LOCATION_NO");

	//#CM705562
	/** Column Definition (REPORTFLAG) */

	public static final FieldName REPORTFLAG = new FieldName("REPORT_FLAG");

	//#CM705563
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM705564
	/** Column Definition (SYSTEMDISCKEY) */

	public static final FieldName SYSTEMDISCKEY = new FieldName("SYSTEM_DISC_KEY");

	//#CM705565
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM705566
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM705567
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM705568
	/** Column Definition (PLANREGISTDATE) */

	public static final FieldName PLANREGISTDATE = new FieldName("PLAN_REGIST_DATE");

	//#CM705569
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM705570
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM705571
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM705572
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM705573
	//------------------------------------------------------------
	//#CM705574
	// properties (prefix 'p_')
	//#CM705575
	//------------------------------------------------------------
	//#CM705576
	//	private String	p_Name ;


	//#CM705577
	//------------------------------------------------------------
	//#CM705578
	// instance variables (prefix '_')
	//#CM705579
	//------------------------------------------------------------
	//#CM705580
	//	private String	_instanceVar ;

	//#CM705581
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM705582
	//------------------------------------------------------------
	//#CM705583
	// constructors
	//#CM705584
	//------------------------------------------------------------

	//#CM705585
	/**
	 * Prepare class name list and generate instance
	 */
	public ResultView()
	{
		super() ;
		prepare() ;
	}

	//#CM705586
	//------------------------------------------------------------
	//#CM705587
	// accessors
	//#CM705588
	//------------------------------------------------------------

	//#CM705589
	/**
	 * Set value to Work date
	 * @param arg Work date to be set
	 */
	public void setWorkDate(String arg)
	{
		setValue(WORKDATE, arg);
	}

	//#CM705590
	/**
	 * Fetch Work date
	 * @return Work date
	 */
	public String getWorkDate()
	{
		return getValue(ResultView.WORKDATE).toString();
	}

	//#CM705591
	/**
	 * Set value to Work No
	 * @param arg Work No to be set
	 */
	public void setJobNo(String arg)
	{
		setValue(JOBNO, arg);
	}

	//#CM705592
	/**
	 * Fetch Work No
	 * @return Work No
	 */
	public String getJobNo()
	{
		return getValue(ResultView.JOBNO).toString();
	}

	//#CM705593
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

	//#CM705594
	/**
	 * Fetch Work type
	 * @return Work type
	 */
	public String getJobType()
	{
		return getValue(ResultView.JOBTYPE).toString();
	}

	//#CM705595
	/**
	 * Set value to Collect work no
	 * @param arg Collect work no to be set
	 */
	public void setCollectJobNo(String arg)
	{
		setValue(COLLECTJOBNO, arg);
	}

	//#CM705596
	/**
	 * Fetch Collect work no
	 * @return Collect work no
	 */
	public String getCollectJobNo()
	{
		return getValue(ResultView.COLLECTJOBNO).toString();
	}

	//#CM705597
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

	//#CM705598
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(ResultView.STATUSFLAG).toString();
	}

	//#CM705599
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

	//#CM705600
	/**
	 * Fetch Work start flag
	 * @return Work start flag
	 */
	public String getBeginningFlag()
	{
		return getValue(ResultView.BEGINNINGFLAG).toString();
	}

	//#CM705601
	/**
	 * Set value to Plan unique key
	 * @param arg Plan unique key to be set
	 */
	public void setPlanUkey(String arg)
	{
		setValue(PLANUKEY, arg);
	}

	//#CM705602
	/**
	 * Fetch Plan unique key
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return getValue(ResultView.PLANUKEY).toString();
	}

	//#CM705603
	/**
	 * Set value to Stock ID
	 * @param arg Stock ID to be set
	 */
	public void setStockId(String arg)
	{
		setValue(STOCKID, arg);
	}

	//#CM705604
	/**
	 * Fetch Stock ID
	 * @return Stock ID
	 */
	public String getStockId()
	{
		return getValue(ResultView.STOCKID).toString();
	}

	//#CM705605
	/**
	 * Set value to Area No
	 * @param arg Area No to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM705606
	/**
	 * Fetch Area No
	 * @return Area No
	 */
	public String getAreaNo()
	{
		return getValue(ResultView.AREANO).toString();
	}

	//#CM705607
	/**
	 * Set value to Zone No
	 * @param arg Zone No to be set
	 */
	public void setZoneNo(String arg)
	{
		setValue(ZONENO, arg);
	}

	//#CM705608
	/**
	 * Fetch Zone No
	 * @return Zone No
	 */
	public String getZoneNo()
	{
		return getValue(ResultView.ZONENO).toString();
	}

	//#CM705609
	/**
	 * Set value to Location No
	 * @param arg Location No to be set
	 */
	public void setLocationNo(String arg)
	{
		setValue(LOCATIONNO, arg);
	}

	//#CM705610
	/**
	 * Fetch Location No
	 * @return Location No
	 */
	public String getLocationNo()
	{
		return getValue(ResultView.LOCATIONNO).toString();
	}

	//#CM705611
	/**
	 * Set value to Plan date
	 * @param arg Plan date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM705612
	/**
	 * Fetch Plan date
	 * @return Plan date
	 */
	public String getPlanDate()
	{
		return getValue(ResultView.PLANDATE).toString();
	}

	//#CM705613
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM705614
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(ResultView.CONSIGNORCODE).toString();
	}

	//#CM705615
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM705616
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(ResultView.CONSIGNORNAME).toString();
	}

	//#CM705617
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM705618
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(ResultView.SUPPLIERCODE).toString();
	}

	//#CM705619
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM705620
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(ResultView.SUPPLIERNAME1).toString();
	}

	//#CM705621
	/**
	 * Set value to Receiving ticket no
	 * @param arg Receiving ticket no to be set
	 */
	public void setInstockTicketNo(String arg)
	{
		setValue(INSTOCKTICKETNO, arg);
	}

	//#CM705622
	/**
	 * Fetch Receiving ticket no
	 * @return Receiving ticket no
	 */
	public String getInstockTicketNo()
	{
		return getValue(ResultView.INSTOCKTICKETNO).toString();
	}

	//#CM705623
	/**
	 * Set value to Receiving line no
	 * @param arg Receiving line no to be set
	 */
	public void setInstockLineNo(int arg)
	{
		setValue(INSTOCKLINENO, new Integer(arg));
	}

	//#CM705624
	/**
	 * Fetch Receiving line no
	 * @return Receiving line no
	 */
	public int getInstockLineNo()
	{
		return getBigDecimal(ResultView.INSTOCKLINENO).intValue();
	}

	//#CM705625
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM705626
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(ResultView.CUSTOMERCODE).toString();
	}

	//#CM705627
	/**
	 * Set value to Customer name
	 * @param arg Customer name to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM705628
	/**
	 * Fetch Customer name
	 * @return Customer name
	 */
	public String getCustomerName1()
	{
		return getValue(ResultView.CUSTOMERNAME1).toString();
	}

	//#CM705629
	/**
	 * Set value to Shipping ticket no
	 * @param arg Shipping ticket no to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		setValue(SHIPPINGTICKETNO, arg);
	}

	//#CM705630
	/**
	 * Fetch Shipping ticket no
	 * @return Shipping ticket no
	 */
	public String getShippingTicketNo()
	{
		return getValue(ResultView.SHIPPINGTICKETNO).toString();
	}

	//#CM705631
	/**
	 * Set value to Shipping line no
	 * @param arg Shipping line no to be set
	 */
	public void setShippingLineNo(int arg)
	{
		setValue(SHIPPINGLINENO, new Integer(arg));
	}

	//#CM705632
	/**
	 * Fetch Shipping line no
	 * @return Shipping line no
	 */
	public int getShippingLineNo()
	{
		return getBigDecimal(ResultView.SHIPPINGLINENO).intValue();
	}

	//#CM705633
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM705634
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(ResultView.ITEMCODE).toString();
	}

	//#CM705635
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM705636
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(ResultView.ITEMNAME1).toString();
	}

	//#CM705637
	/**
	 * Set value to Host plan qty
	 * @param arg Host plan qty to be set
	 */
	public void setHostPlanQty(int arg)
	{
		setValue(HOSTPLANQTY, new Integer(arg));
	}

	//#CM705638
	/**
	 * Fetch Host plan qty
	 * @return Host plan qty
	 */
	public int getHostPlanQty()
	{
		return getBigDecimal(ResultView.HOSTPLANQTY).intValue();
	}

	//#CM705639
	/**
	 * Set value to Work plan qty
	 * @param arg Work plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM705640
	/**
	 * Fetch Work plan qty
	 * @return Work plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(ResultView.PLANQTY).intValue();
	}

	//#CM705641
	/**
	 * Set value to Plan enabled qty
	 * @param arg Plan enabled qty to be set
	 */
	public void setPlanEnableQty(int arg)
	{
		setValue(PLANENABLEQTY, new Integer(arg));
	}

	//#CM705642
	/**
	 * Fetch Plan enabled qty
	 * @return Plan enabled qty
	 */
	public int getPlanEnableQty()
	{
		return getBigDecimal(ResultView.PLANENABLEQTY).intValue();
	}

	//#CM705643
	/**
	 * Set value to Result qty
	 * @param arg Result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM705644
	/**
	 * Fetch Result qty
	 * @return Result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(ResultView.RESULTQTY).intValue();
	}

	//#CM705645
	/**
	 * Set value to Shortage qty
	 * @param arg Shortage qty to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM705646
	/**
	 * Fetch Shortage qty
	 * @return Shortage qty
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(ResultView.SHORTAGECNT).intValue();
	}

	//#CM705647
	/**
	 * Set value to Pending qty
	 * @param arg Pending qty to be set
	 */
	public void setPendingQty(int arg)
	{
		setValue(PENDINGQTY, new Integer(arg));
	}

	//#CM705648
	/**
	 * Fetch Pending qty
	 * @return Pending qty
	 */
	public int getPendingQty()
	{
		return getBigDecimal(ResultView.PENDINGQTY).intValue();
	}

	//#CM705649
	/**
	 * Set value to Entering Case qty
	 * @param arg Entering Case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM705650
	/**
	 * Fetch Entering Case qty
	 * @return Entering Case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(ResultView.ENTERINGQTY).intValue();
	}

	//#CM705651
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM705652
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(ResultView.BUNDLEENTERINGQTY).intValue();
	}

	//#CM705653
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

	//#CM705654
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(ResultView.CASEPIECEFLAG).toString();
	}

	//#CM705655
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

	//#CM705656
	/**
	 * Fetch Case/Piece flag (Work form flag)
	 * @return Case/Piece flag (Work form flag)
	 */
	public String getWorkFormFlag()
	{
		return getValue(ResultView.WORKFORMFLAG).toString();
	}

	//#CM705657
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM705658
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(ResultView.ITF).toString();
	}

	//#CM705659
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM705660
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(ResultView.BUNDLEITF).toString();
	}

	//#CM705661
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

	//#CM705662
	/**
	 * Fetch TC/DC flag
	 * @return TC/DC flag
	 */
	public String getTcdcFlag()
	{
		return getValue(ResultView.TCDCFLAG).toString();
	}

	//#CM705663
	/**
	 * Set value to Use By Date
	 * @param arg Use By Date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM705664
	/**
	 * Fetch Use By Date
	 * @return Use By Date
	 */
	public String getUseByDate()
	{
		return getValue(ResultView.USEBYDATE).toString();
	}

	//#CM705665
	/**
	 * Set value to Lot no
	 * @param arg Lot no to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM705666
	/**
	 * Fetch Lot no
	 * @return Lot no
	 */
	public String getLotNo()
	{
		return getValue(ResultView.LOTNO).toString();
	}

	//#CM705667
	/**
	 * Set value to Plan detail comments
	 * @param arg Plan detail comments to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM705668
	/**
	 * Fetch Plan detail comments
	 * @return Plan detail comments
	 */
	public String getPlanInformation()
	{
		return getValue(ResultView.PLANINFORMATION).toString();
	}

	//#CM705669
	/**
	 * Set value to Order no
	 * @param arg Order no to be set
	 */
	public void setOrderNo(String arg)
	{
		setValue(ORDERNO, arg);
	}

	//#CM705670
	/**
	 * Fetch Order no
	 * @return Order no
	 */
	public String getOrderNo()
	{
		return getValue(ResultView.ORDERNO).toString();
	}

	//#CM705671
	/**
	 * Set value to Order sequence no
	 * @param arg Order sequence no to be set
	 */
	public void setOrderSeqNo(int arg)
	{
		setValue(ORDERSEQNO, new Integer(arg));
	}

	//#CM705672
	/**
	 * Fetch Order sequence no
	 * @return Order sequence no
	 */
	public int getOrderSeqNo()
	{
		return getBigDecimal(ResultView.ORDERSEQNO).intValue();
	}

	//#CM705673
	/**
	 * Set value to Ordering date
	 * @param arg Ordering date to be set
	 */
	public void setOrderingDate(String arg)
	{
		setValue(ORDERINGDATE, arg);
	}

	//#CM705674
	/**
	 * Fetch Ordering date
	 * @return Ordering date
	 */
	public String getOrderingDate()
	{
		return getValue(ResultView.ORDERINGDATE).toString();
	}

	//#CM705675
	/**
	 * Set value to Result use by date
	 * @param arg Result use by date to be set
	 */
	public void setResultUseByDate(String arg)
	{
		setValue(RESULTUSEBYDATE, arg);
	}

	//#CM705676
	/**
	 * Fetch Result use by date
	 * @return Result use by date
	 */
	public String getResultUseByDate()
	{
		return getValue(ResultView.RESULTUSEBYDATE).toString();
	}

	//#CM705677
	/**
	 * Set value to Result lot no
	 * @param arg Result lot no to be set
	 */
	public void setResultLotNo(String arg)
	{
		setValue(RESULTLOTNO, arg);
	}

	//#CM705678
	/**
	 * Fetch Result lot no
	 * @return Result lot no
	 */
	public String getResultLotNo()
	{
		return getValue(ResultView.RESULTLOTNO).toString();
	}

	//#CM705679
	/**
	 * Set value to Result location no
	 * @param arg Result location no to be set
	 */
	public void setResultLocationNo(String arg)
	{
		setValue(RESULTLOCATIONNO, arg);
	}

	//#CM705680
	/**
	 * Fetch Result location no
	 * @return Result location no
	 */
	public String getResultLocationNo()
	{
		return getValue(ResultView.RESULTLOCATIONNO).toString();
	}

	//#CM705681
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

	//#CM705682
	/**
	 * Fetch Report flag
	 * @return Report flag
	 */
	public String getReportFlag()
	{
		return getValue(ResultView.REPORTFLAG).toString();
	}

	//#CM705683
	/**
	 * Set value to Batch no (Schedule no)
	 * @param arg Batch no (Schedule no) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM705684
	/**
	 * Fetch Batch no (Schedule no)
	 * @return Batch no (Schedule no)
	 */
	public String getBatchNo()
	{
		return getValue(ResultView.BATCHNO).toString();
	}

	//#CM705685
	/**
	 * Set value to System identification key
	 * @param arg System identification key to be set
	 */
	public void setSystemDiscKey(int arg)
	{
		setValue(SYSTEMDISCKEY, new Integer(arg));
	}

	//#CM705686
	/**
	 * Fetch System identification key
	 * @return System identification key
	 */
	public int getSystemDiscKey()
	{
		return getBigDecimal(ResultView.SYSTEMDISCKEY).intValue();
	}

	//#CM705687
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM705688
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(ResultView.WORKERCODE).toString();
	}

	//#CM705689
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM705690
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(ResultView.WORKERNAME).toString();
	}

	//#CM705691
	/**
	 * Set value to Terminal no
	 * @param arg Terminal no to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM705692
	/**
	 * Fetch Terminal no
	 * @return Terminal no
	 */
	public String getTerminalNo()
	{
		return getValue(ResultView.TERMINALNO).toString();
	}

	//#CM705693
	/**
	 * Set value to Plan registration date
	 * @param arg Plan registration date to be set
	 */
	public void setPlanRegistDate(Date arg)
	{
		setValue(PLANREGISTDATE, arg);
	}

	//#CM705694
	/**
	 * Fetch Plan registration date
	 * @return Plan registration date
	 */
	public Date getPlanRegistDate()
	{
		return (Date)getValue(ResultView.PLANREGISTDATE);
	}

	//#CM705695
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM705696
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(ResultView.REGISTDATE);
	}

	//#CM705697
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM705698
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(ResultView.REGISTPNAME).toString();
	}

	//#CM705699
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM705700
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(ResultView.LASTUPDATEDATE);
	}

	//#CM705701
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM705702
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(ResultView.LASTUPDATEPNAME).toString();
	}


	//#CM705703
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM705704
	//------------------------------------------------------------
	//#CM705705
	// package methods
	//#CM705706
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705707
	//------------------------------------------------------------


	//#CM705708
	//------------------------------------------------------------
	//#CM705709
	// protected methods
	//#CM705710
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705711
	//------------------------------------------------------------


	//#CM705712
	//------------------------------------------------------------
	//#CM705713
	// private methods
	//#CM705714
	//------------------------------------------------------------
	//#CM705715
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


	//#CM705716
	//------------------------------------------------------------
	//#CM705717
	// utility methods
	//#CM705718
	//------------------------------------------------------------
	//#CM705719
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: ResultView.java,v 1.4 2006/11/13 04:31:01 suresh Exp $" ;
	}
}
