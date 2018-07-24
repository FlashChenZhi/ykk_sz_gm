//#CM703608
//$Id: HostSend.java,v 1.5 2006/11/16 02:15:45 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM703609
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
import jp.co.daifuku.wms.base.entity.HostSend;

//#CM703610
/**
 * Entity class of HOSTSEND
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:45 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class HostSend
		extends AbstractEntity
{
	//#CM703611
	//------------------------------------------------------------
	//#CM703612
	// class variables (prefix '$')
	//#CM703613
	//------------------------------------------------------------
	//#CM703614
	//	private String	$classVar ;

	//#CM703615
	//------------------------------------------------------------
	//#CM703616
	// fields (upper case only)
	//#CM703617
	//------------------------------------------------------------
	//#CM703618
	/*
	 *  * Table name : HOSTSEND
	 * Work date :                     WORKDATE            varchar2(8)
	 * Work no :                       JOBNO               varchar2(8)
	 * Work type :                     JOBTYPE             varchar2(2)
	 * Collect work no :               COLLECTJOBNO        varchar2(8)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Work start flag :               BEGINNINGFLAG       varchar2(1)
	 * Plan unique key :               PLANUKEY            varchar2(16)
	 * Stock ID :                      STOCKID             varchar2(8)
	 * Area no :                       AREANO              varchar2(16)
	 * Zone no :                       ZONENO              varchar2(3)
	 * Location no :                   LOCATIONNO          varchar2(16)
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
	 * Entering case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case/Piece flag :               CASEPIECEFLAG       varchar2(1)
	 * Case/Piece flag (Work form flag) :WORKFORMFLAG        varchar2(1)
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * TC/DC flag :                    TCDCFLAG            varchar2(1)
	 * Use by date :                   USEBYDATE           varchar2(8)
	 * Lot No :                        LOTNO               varchar2(20)
	 * Plan details comment :          PLANINFORMATION     varchar2(40)
	 * Order no :                      ORDERNO             varchar2(16)
	 * Order sequence no :             ORDERSEQNO          number
	 * Ordering date :                 ORDERINGDATE        varchar2(8)
	 * Result use by date :            RESULTUSEBYDATE     varchar2(8)
	 * Result lot no :                 RESULTLOTNO         varchar2(20)
	 * Result location no :            RESULTLOCATIONNO    varchar2(16)
	 * Report flag :                   REPORTFLAG          varchar2(1)
	 * Batch No (Schedule No) :        BATCHNO             varchar2(8)
	 * System Connection key :         SYSTEMCONNKEY       varchar2(16)
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

	//#CM703619
	/**Table name definition*/

	public static final String TABLE_NAME = "DNHOSTSEND";

	//#CM703620
	/** Column Definition (WORKDATE) */

	public static final FieldName WORKDATE = new FieldName("WORK_DATE");

	//#CM703621
	/** Column Definition (JOBNO) */

	public static final FieldName JOBNO = new FieldName("JOB_NO");

	//#CM703622
	/** Column Definition (JOBTYPE) */

	public static final FieldName JOBTYPE = new FieldName("JOB_TYPE");

	//#CM703623
	/** Column Definition (COLLECTJOBNO) */

	public static final FieldName COLLECTJOBNO = new FieldName("COLLECT_JOB_NO");

	//#CM703624
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM703625
	/** Column Definition (BEGINNINGFLAG) */

	public static final FieldName BEGINNINGFLAG = new FieldName("BEGINNING_FLAG");

	//#CM703626
	/** Column Definition (PLANUKEY) */

	public static final FieldName PLANUKEY = new FieldName("PLAN_UKEY");

	//#CM703627
	/** Column Definition (STOCKID) */

	public static final FieldName STOCKID = new FieldName("STOCK_ID");

	//#CM703628
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM703629
	/** Column Definition (ZONENO) */

	public static final FieldName ZONENO = new FieldName("ZONE_NO");

	//#CM703630
	/** Column Definition (LOCATIONNO) */

	public static final FieldName LOCATIONNO = new FieldName("LOCATION_NO");

	//#CM703631
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM703632
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM703633
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM703634
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM703635
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM703636
	/** Column Definition (INSTOCKTICKETNO) */

	public static final FieldName INSTOCKTICKETNO = new FieldName("INSTOCK_TICKET_NO");

	//#CM703637
	/** Column Definition (INSTOCKLINENO) */

	public static final FieldName INSTOCKLINENO = new FieldName("INSTOCK_LINE_NO");

	//#CM703638
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM703639
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM703640
	/** Column Definition (SHIPPINGTICKETNO) */

	public static final FieldName SHIPPINGTICKETNO = new FieldName("SHIPPING_TICKET_NO");

	//#CM703641
	/** Column Definition (SHIPPINGLINENO) */

	public static final FieldName SHIPPINGLINENO = new FieldName("SHIPPING_LINE_NO");

	//#CM703642
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM703643
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM703644
	/** Column Definition (HOSTPLANQTY) */

	public static final FieldName HOSTPLANQTY = new FieldName("HOST_PLAN_QTY");

	//#CM703645
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM703646
	/** Column Definition (PLANENABLEQTY) */

	public static final FieldName PLANENABLEQTY = new FieldName("PLAN_ENABLE_QTY");

	//#CM703647
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM703648
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM703649
	/** Column Definition (PENDINGQTY) */

	public static final FieldName PENDINGQTY = new FieldName("PENDING_QTY");

	//#CM703650
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM703651
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM703652
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM703653
	/** Column Definition (WORKFORMFLAG) */

	public static final FieldName WORKFORMFLAG = new FieldName("WORK_FORM_FLAG");

	//#CM703654
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM703655
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM703656
	/** Column Definition (TCDCFLAG) */

	public static final FieldName TCDCFLAG = new FieldName("TCDC_FLAG");

	//#CM703657
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM703658
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM703659
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM703660
	/** Column Definition (ORDERNO) */

	public static final FieldName ORDERNO = new FieldName("ORDER_NO");

	//#CM703661
	/** Column Definition (ORDERSEQNO) */

	public static final FieldName ORDERSEQNO = new FieldName("ORDER_SEQ_NO");

	//#CM703662
	/** Column Definition (ORDERINGDATE) */

	public static final FieldName ORDERINGDATE = new FieldName("ORDERING_DATE");

	//#CM703663
	/** Column Definition (RESULTUSEBYDATE) */

	public static final FieldName RESULTUSEBYDATE = new FieldName("RESULT_USE_BY_DATE");

	//#CM703664
	/** Column Definition (RESULTLOTNO) */

	public static final FieldName RESULTLOTNO = new FieldName("RESULT_LOT_NO");

	//#CM703665
	/** Column Definition (RESULTLOCATIONNO) */

	public static final FieldName RESULTLOCATIONNO = new FieldName("RESULT_LOCATION_NO");

	//#CM703666
	/** Column Definition (REPORTFLAG) */

	public static final FieldName REPORTFLAG = new FieldName("REPORT_FLAG");

	//#CM703667
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM703668
	/** Column Definition (SYSTEMCONNKEY) */

	public static final FieldName SYSTEMCONNKEY = new FieldName("SYSTEM_CONN_KEY");

	//#CM703669
	/** Column Definition (SYSTEMDISCKEY) */

	public static final FieldName SYSTEMDISCKEY = new FieldName("SYSTEM_DISC_KEY");

	//#CM703670
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM703671
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM703672
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM703673
	/** Column Definition (PLANREGISTDATE) */

	public static final FieldName PLANREGISTDATE = new FieldName("PLAN_REGIST_DATE");

	//#CM703674
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM703675
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM703676
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM703677
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM703678
	//------------------------------------------------------------
	//#CM703679
	// properties (prefix 'p_')
	//#CM703680
	//------------------------------------------------------------
	//#CM703681
	//	private String	p_Name ;


	//#CM703682
	//------------------------------------------------------------
	//#CM703683
	// instance variables (prefix '_')
	//#CM703684
	//------------------------------------------------------------
	//#CM703685
	//	private String	_instanceVar ;

	//#CM703686
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM703687
	//------------------------------------------------------------
	//#CM703688
	// constructors
	//#CM703689
	//------------------------------------------------------------
	//#CM703690
	/**
	 * Generate the entity of results transmission information from the entity of work information.
	 * Set the every particular item of work information in the every particular item
	 * of results transmission information as it is giving a mind to usual completion processing.
	 * Set a value different from work information about the following items. <BR>
	 * <TABLE border="1">
	 * <TR><TH>Item</TH>			<TH>Value</TH></TR>
	 * <TR><TD>Work report flag</TD>	<TD>Unsent</TD></TR>
	 * <TR><TD>Registration date</TD>		<TD>Current system date</TD></TR>
	 * <TR><TD>Last updated date and time</TD>	<TD>Current system date</TD></TR>
	 * </TABLE>
	 *
	 * @param	workinfo	Updated work information
	 * @param	workDate	Work date
	 * @param	processName	Process name
	 */
	public HostSend(
		WorkingInformation workinfo,
		String workDate,
		String processName)
	{
		setInitCreateColumn();
		setValue(WORKDATE, workDate);
		setValue(JOBNO, workinfo.getJobNo());
		setValue(JOBTYPE, workinfo.getJobType());
		setValue(COLLECTJOBNO, workinfo.getCollectJobNo());
		setValue(PLANUKEY, workinfo.getPlanUkey());
		setValue(STATUSFLAG, workinfo.getStatusFlag());
		setValue(BEGINNINGFLAG, workinfo.getBeginningFlag());
		setValue(PLANDATE, workinfo.getPlanDate());
		setValue(CONSIGNORCODE, workinfo.getConsignorCode());
		setValue(CONSIGNORNAME, workinfo.getConsignorName());
		setValue(SUPPLIERCODE, workinfo.getSupplierCode());
		setValue(SUPPLIERNAME1, workinfo.getSupplierName1());
		setValue(INSTOCKTICKETNO, workinfo.getInstockTicketNo());
		setValue(INSTOCKLINENO, new Integer(workinfo.getInstockLineNo()));
		setValue(CUSTOMERCODE, workinfo.getCustomerCode());
		setValue(CUSTOMERNAME1, workinfo.getCustomerName1());
		setValue(SHIPPINGTICKETNO, workinfo.getShippingTicketNo());
		setValue(SHIPPINGLINENO, new Integer(workinfo.getShippingLineNo()));
		setValue(ITEMCODE, workinfo.getItemCode());
		setValue(ITEMNAME1, workinfo.getItemName1());
		setValue(HOSTPLANQTY, new Integer(workinfo.getHostPlanQty()));
		setValue(PLANQTY, new Integer(workinfo.getPlanQty()));
		setValue(PLANENABLEQTY, new Integer(workinfo.getPlanEnableQty()));
		setValue(RESULTQTY, new Integer(workinfo.getResultQty()));
		setValue(SHORTAGECNT, new Integer(workinfo.getShortageCnt()));
		setValue(PENDINGQTY, new Integer(workinfo.getPendingQty()));
		setValue(ENTERINGQTY, new Integer(workinfo.getEnteringQty()));
		setValue(BUNDLEENTERINGQTY, new Integer(workinfo.getBundleEnteringQty()));
		setValue(CASEPIECEFLAG, workinfo.getCasePieceFlag());
		setValue(WORKFORMFLAG, workinfo.getWorkFormFlag());
		setValue(ITF, workinfo.getItf());
		setValue(BUNDLEITF, workinfo.getBundleItf());
		setValue(ORDERINGDATE, workinfo.getOrderingDate());
		setValue(TCDCFLAG, workinfo.getTcdcFlag());
		setValue(USEBYDATE, workinfo.getUseByDate());
		setValue(LOTNO, workinfo.getLotNo());
		setValue(PLANINFORMATION, workinfo.getPlanInformation());
		setValue(STOCKID, workinfo.getStockId());
		setValue(AREANO, workinfo.getAreaNo());
		setValue(ZONENO, workinfo.getZoneNo());
		setValue(LOCATIONNO, workinfo.getLocationNo());
		setValue(ORDERNO, workinfo.getOrderNo());
		setValue(ORDERSEQNO, new Integer(workinfo.getOrderSeqNo()));
		setValue(RESULTUSEBYDATE, workinfo.getResultUseByDate());
		setValue(RESULTLOTNO, workinfo.getResultLotNo());
		setValue(RESULTLOCATIONNO, workinfo.getResultLocationNo());
		setValue(REPORTFLAG, HostSend.REPORT_FLAG_NOT_SENT);
		setValue(BATCHNO, workinfo.getBatchNo());
		setValue(SYSTEMCONNKEY, workinfo.getSystemConnKey());
		setValue(SYSTEMDISCKEY, new Integer(workinfo.getSystemDiscKey()));
		setValue(PLANREGISTDATE, workinfo.getPlanRegistDate());
		setValue(WORKERCODE, workinfo.getWorkerCode());
		setValue(WORKERNAME, workinfo.getWorkerName());
		setValue(TERMINALNO, workinfo.getTerminalNo());
		setValue(REGISTDATE, new Date());
		setValue(REGISTPNAME, processName);
		setValue(LASTUPDATEDATE, new Date());
		setValue(LASTUPDATEPNAME, processName);
	}

	//#CM703691
	/**
	 * Prepare class name list and generate instance
	 */
	public HostSend()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM703692
	//------------------------------------------------------------
	//#CM703693
	// accessors
	//#CM703694
	//------------------------------------------------------------

	//#CM703695
	/**
	 * Set value to Work date
	 * @param arg Work date to be set
	 */
	public void setWorkDate(String arg)
	{
		setValue(WORKDATE, arg);
	}

	//#CM703696
	/**
	 * Fetch Work date
	 * @return Work date
	 */
	public String getWorkDate()
	{
		return getValue(HostSend.WORKDATE).toString();
	}

	//#CM703697
	/**
	 * Set value to Work no
	 * @param arg Work no to be set
	 */
	public void setJobNo(String arg)
	{
		setValue(JOBNO, arg);
	}

	//#CM703698
	/**
	 * Fetch Work no
	 * @return Work no
	 */
	public String getJobNo()
	{
		return getValue(HostSend.JOBNO).toString();
	}

	//#CM703699
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
			|| (arg.equals(JOB_TYPE_INVENTORY_MINUS))
			|| (arg.equals(JOB_TYPE_ASRS_INVENTORY_CHECK)))
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

	//#CM703700
	/**
	 * Fetch Work type
	 * @return Work type
	 */
	public String getJobType()
	{
		return getValue(HostSend.JOBTYPE).toString();
	}

	//#CM703701
	/**
	 * Set value to Collect work no
	 * @param arg Collect work no to be set
	 */
	public void setCollectJobNo(String arg)
	{
		setValue(COLLECTJOBNO, arg);
	}

	//#CM703702
	/**
	 * Fetch Collect work no
	 * @return Collect work no
	 */
	public String getCollectJobNo()
	{
		return getValue(HostSend.COLLECTJOBNO).toString();
	}

	//#CM703703
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

	//#CM703704
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(HostSend.STATUSFLAG).toString();
	}

	//#CM703705
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

	//#CM703706
	/**
	 * Fetch Work start flag
	 * @return Work start flag
	 */
	public String getBeginningFlag()
	{
		return getValue(HostSend.BEGINNINGFLAG).toString();
	}

	//#CM703707
	/**
	 * Set value to Plan unique key
	 * @param arg Plan unique key to be set
	 */
	public void setPlanUkey(String arg)
	{
		setValue(PLANUKEY, arg);
	}

	//#CM703708
	/**
	 * Fetch Plan unique key
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return getValue(HostSend.PLANUKEY).toString();
	}

	//#CM703709
	/**
	 * Set value to Stock ID
	 * @param arg Stock ID to be set
	 */
	public void setStockId(String arg)
	{
		setValue(STOCKID, arg);
	}

	//#CM703710
	/**
	 * Fetch Stock ID
	 * @return Stock ID
	 */
	public String getStockId()
	{
		return getValue(HostSend.STOCKID).toString();
	}

	//#CM703711
	/**
	 * Set value to Area no
	 * @param arg Area no to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM703712
	/**
	 * Fetch Area no
	 * @return Area no
	 */
	public String getAreaNo()
	{
		return getValue(HostSend.AREANO).toString();
	}

	//#CM703713
	/**
	 * Set value to Zone no
	 * @param arg Zone no to be set
	 */
	public void setZoneNo(String arg)
	{
		setValue(ZONENO, arg);
	}

	//#CM703714
	/**
	 * Fetch Zone no
	 * @return Zone no
	 */
	public String getZoneNo()
	{
		return getValue(HostSend.ZONENO).toString();
	}

	//#CM703715
	/**
	 * Set value to Location no
	 * @param arg Location no to be set
	 */
	public void setLocationNo(String arg)
	{
		setValue(LOCATIONNO, arg);
	}

	//#CM703716
	/**
	 * Fetch Location no
	 * @return Location no
	 */
	public String getLocationNo()
	{
		return getValue(HostSend.LOCATIONNO).toString();
	}

	//#CM703717
	/**
	 * Set value to Plan date
	 * @param arg Plan date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM703718
	/**
	 * Fetch Plan date
	 * @return Plan date
	 */
	public String getPlanDate()
	{
		return getValue(HostSend.PLANDATE).toString();
	}

	//#CM703719
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM703720
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(HostSend.CONSIGNORCODE).toString();
	}

	//#CM703721
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM703722
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(HostSend.CONSIGNORNAME).toString();
	}

	//#CM703723
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM703724
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(HostSend.SUPPLIERCODE).toString();
	}

	//#CM703725
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM703726
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(HostSend.SUPPLIERNAME1).toString();
	}

	//#CM703727
	/**
	 * Set value to Receiving ticket no
	 * @param arg Receiving ticket no to be set
	 */
	public void setInstockTicketNo(String arg)
	{
		setValue(INSTOCKTICKETNO, arg);
	}

	//#CM703728
	/**
	 * Fetch Receiving ticket no
	 * @return Receiving ticket no
	 */
	public String getInstockTicketNo()
	{
		return getValue(HostSend.INSTOCKTICKETNO).toString();
	}

	//#CM703729
	/**
	 * Set value to Receiving line no
	 * @param arg Receiving line no to be set
	 */
	public void setInstockLineNo(int arg)
	{
		setValue(INSTOCKLINENO, new Integer(arg));
	}

	//#CM703730
	/**
	 * Fetch Receiving line no
	 * @return Receiving line no
	 */
	public int getInstockLineNo()
	{
		return getBigDecimal(HostSend.INSTOCKLINENO).intValue();
	}

	//#CM703731
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM703732
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(HostSend.CUSTOMERCODE).toString();
	}

	//#CM703733
	/**
	 * Set value to Customer name
	 * @param arg Customer name to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM703734
	/**
	 * Fetch Customer name
	 * @return Customer name
	 */
	public String getCustomerName1()
	{
		return getValue(HostSend.CUSTOMERNAME1).toString();
	}

	//#CM703735
	/**
	 * Set value to Shipping ticket no
	 * @param arg Shipping ticket no to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		setValue(SHIPPINGTICKETNO, arg);
	}

	//#CM703736
	/**
	 * Fetch Shipping ticket no
	 * @return Shipping ticket no
	 */
	public String getShippingTicketNo()
	{
		return getValue(HostSend.SHIPPINGTICKETNO).toString();
	}

	//#CM703737
	/**
	 * Set value to Shipping line no
	 * @param arg Shipping line no to be set
	 */
	public void setShippingLineNo(int arg)
	{
		setValue(SHIPPINGLINENO, new Integer(arg));
	}

	//#CM703738
	/**
	 * Fetch Shipping line no
	 * @return Shipping line no
	 */
	public int getShippingLineNo()
	{
		return getBigDecimal(HostSend.SHIPPINGLINENO).intValue();
	}

	//#CM703739
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM703740
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(HostSend.ITEMCODE).toString();
	}

	//#CM703741
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM703742
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(HostSend.ITEMNAME1).toString();
	}

	//#CM703743
	/**
	 * Set value to Host plan qty
	 * @param arg Host plan qty to be set
	 */
	public void setHostPlanQty(int arg)
	{
		setValue(HOSTPLANQTY, new Integer(arg));
	}

	//#CM703744
	/**
	 * Fetch Host plan qty
	 * @return Host plan qty
	 */
	public int getHostPlanQty()
	{
		return getBigDecimal(HostSend.HOSTPLANQTY).intValue();
	}

	//#CM703745
	/**
	 * Set value to Work plan qty
	 * @param arg Work plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM703746
	/**
	 * Fetch Work plan qty
	 * @return Work plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(HostSend.PLANQTY).intValue();
	}

	//#CM703747
	/**
	 * Set value to Plan enabled qty
	 * @param arg Plan enabled qty to be set
	 */
	public void setPlanEnableQty(int arg)
	{
		setValue(PLANENABLEQTY, new Integer(arg));
	}

	//#CM703748
	/**
	 * Fetch Plan enabled qty
	 * @return Plan enabled qty
	 */
	public int getPlanEnableQty()
	{
		return getBigDecimal(HostSend.PLANENABLEQTY).intValue();
	}

	//#CM703749
	/**
	 * Set value to Result qty
	 * @param arg Result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM703750
	/**
	 * Fetch Result qty
	 * @return Result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(HostSend.RESULTQTY).intValue();
	}

	//#CM703751
	/**
	 * Set value to Shortage qty
	 * @param arg Shortage qty to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM703752
	/**
	 * Fetch Shortage qty
	 * @return Shortage qty
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(HostSend.SHORTAGECNT).intValue();
	}

	//#CM703753
	/**
	 * Set value to Pending qty
	 * @param arg Pending qty to be set
	 */
	public void setPendingQty(int arg)
	{
		setValue(PENDINGQTY, new Integer(arg));
	}

	//#CM703754
	/**
	 * Fetch Pending qty
	 * @return Pending qty
	 */
	public int getPendingQty()
	{
		return getBigDecimal(HostSend.PENDINGQTY).intValue();
	}

	//#CM703755
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM703756
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(HostSend.ENTERINGQTY).intValue();
	}

	//#CM703757
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM703758
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(HostSend.BUNDLEENTERINGQTY).intValue();
	}

	//#CM703759
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

	//#CM703760
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(HostSend.CASEPIECEFLAG).toString();
	}

	//#CM703761
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

	//#CM703762
	/**
	 * Fetch Case/Piece flag (Work form flag)
	 * @return Case/Piece flag (Work form flag)
	 */
	public String getWorkFormFlag()
	{
		return getValue(HostSend.WORKFORMFLAG).toString();
	}

	//#CM703763
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM703764
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(HostSend.ITF).toString();
	}

	//#CM703765
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM703766
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(HostSend.BUNDLEITF).toString();
	}

	//#CM703767
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

	//#CM703768
	/**
	 * Fetch TC/DC flag
	 * @return TC/DC flag
	 */
	public String getTcdcFlag()
	{
		return getValue(HostSend.TCDCFLAG).toString();
	}

	//#CM703769
	/**
	 * Set value to Use by date
	 * @param arg Use by date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM703770
	/**
	 * Fetch Use by date
	 * @return Use by date
	 */
	public String getUseByDate()
	{
		return getValue(HostSend.USEBYDATE).toString();
	}

	//#CM703771
	/**
	 * Set value to Lot No
	 * @param arg Lot No to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM703772
	/**
	 * Fetch Lot No
	 * @return Lot No
	 */
	public String getLotNo()
	{
		return getValue(HostSend.LOTNO).toString();
	}

	//#CM703773
	/**
	 * Set value to Plan details comment
	 * @param arg Plan details comment to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM703774
	/**
	 * Fetch Plan details comment
	 * @return Plan details comment
	 */
	public String getPlanInformation()
	{
		return getValue(HostSend.PLANINFORMATION).toString();
	}

	//#CM703775
	/**
	 * Set value to Order no
	 * @param arg Order no to be set
	 */
	public void setOrderNo(String arg)
	{
		setValue(ORDERNO, arg);
	}

	//#CM703776
	/**
	 * Fetch Order no
	 * @return Order no
	 */
	public String getOrderNo()
	{
		return getValue(HostSend.ORDERNO).toString();
	}

	//#CM703777
	/**
	 * Set value to Order sequence no
	 * @param arg Order sequence no to be set
	 */
	public void setOrderSeqNo(int arg)
	{
		setValue(ORDERSEQNO, new Integer(arg));
	}

	//#CM703778
	/**
	 * Fetch Order sequence no
	 * @return Order sequence no
	 */
	public int getOrderSeqNo()
	{
		return getBigDecimal(HostSend.ORDERSEQNO).intValue();
	}

	//#CM703779
	/**
	 * Set value to Ordering date
	 * @param arg Ordering date to be set
	 */
	public void setOrderingDate(String arg)
	{
		setValue(ORDERINGDATE, arg);
	}

	//#CM703780
	/**
	 * Fetch Ordering date
	 * @return Ordering date
	 */
	public String getOrderingDate()
	{
		return getValue(HostSend.ORDERINGDATE).toString();
	}

	//#CM703781
	/**
	 * Set value to Result use by date
	 * @param arg Result use by date to be set
	 */
	public void setResultUseByDate(String arg)
	{
		setValue(RESULTUSEBYDATE, arg);
	}

	//#CM703782
	/**
	 * Fetch Result use by date
	 * @return Result use by date
	 */
	public String getResultUseByDate()
	{
		return getValue(HostSend.RESULTUSEBYDATE).toString();
	}

	//#CM703783
	/**
	 * Set value to Result lot no
	 * @param arg Result lot no to be set
	 */
	public void setResultLotNo(String arg)
	{
		setValue(RESULTLOTNO, arg);
	}

	//#CM703784
	/**
	 * Fetch Result lot no
	 * @return Result lot no
	 */
	public String getResultLotNo()
	{
		return getValue(HostSend.RESULTLOTNO).toString();
	}

	//#CM703785
	/**
	 * Set value to Result location no
	 * @param arg Result location no to be set
	 */
	public void setResultLocationNo(String arg)
	{
		setValue(RESULTLOCATIONNO, arg);
	}

	//#CM703786
	/**
	 * Fetch Result location no
	 * @return Result location no
	 */
	public String getResultLocationNo()
	{
		return getValue(HostSend.RESULTLOCATIONNO).toString();
	}

	//#CM703787
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

	//#CM703788
	/**
	 * Fetch Report flag
	 * @return Report flag
	 */
	public String getReportFlag()
	{
		return getValue(HostSend.REPORTFLAG).toString();
	}

	//#CM703789
	/**
	 * Set value to Batch No (Schedule No)
	 * @param arg Batch No (Schedule No) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM703790
	/**
	 * Fetch Batch No (Schedule No)
	 * @return Batch No (Schedule No)
	 */
	public String getBatchNo()
	{
		return getValue(HostSend.BATCHNO).toString();
	}

	//#CM703791
	/**
	 * Set value to System Connection key
	 * @param arg System Connection key to be set
	 */
	public void setSystemConnKey(String arg)
	{
		setValue(SYSTEMCONNKEY, arg);
	}

	//#CM703792
	/**
	 * Fetch System Connection key
	 * @return System Connection key
	 */
	public String getSystemConnKey()
	{
		return getValue(HostSend.SYSTEMCONNKEY).toString();
	}

	//#CM703793
	/**
	 * Set value to System identification key
	 * @param arg System identification key to be set
	 */
	public void setSystemDiscKey(int arg)
	{
		setValue(SYSTEMDISCKEY, new Integer(arg));
	}

	//#CM703794
	/**
	 * Fetch System identification key
	 * @return System identification key
	 */
	public int getSystemDiscKey()
	{
		return getBigDecimal(HostSend.SYSTEMDISCKEY).intValue();
	}

	//#CM703795
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM703796
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(HostSend.WORKERCODE).toString();
	}

	//#CM703797
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM703798
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(HostSend.WORKERNAME).toString();
	}

	//#CM703799
	/**
	 * Set value to Terminal no
	 * @param arg Terminal no to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM703800
	/**
	 * Fetch Terminal no
	 * @return Terminal no
	 */
	public String getTerminalNo()
	{
		return getValue(HostSend.TERMINALNO).toString();
	}

	//#CM703801
	/**
	 * Set value to Plan registration date
	 * @param arg Plan registration date to be set
	 */
	public void setPlanRegistDate(Date arg)
	{
		setValue(PLANREGISTDATE, arg);
	}

	//#CM703802
	/**
	 * Fetch Plan registration date
	 * @return Plan registration date
	 */
	public Date getPlanRegistDate()
	{
		return (Date)getValue(HostSend.PLANREGISTDATE);
	}

	//#CM703803
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM703804
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(HostSend.REGISTDATE);
	}

	//#CM703805
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM703806
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(HostSend.REGISTPNAME).toString();
	}

	//#CM703807
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM703808
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(HostSend.LASTUPDATEDATE);
	}

	//#CM703809
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM703810
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(HostSend.LASTUPDATEPNAME).toString();
	}


	//#CM703811
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM703812
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(LASTUPDATEDATE, "");
		setValue(REGISTDATE, "");

		setValue(INSTOCKLINENO, new Integer(0));
		setValue(SHIPPINGLINENO, new Integer(0));
		setValue(HOSTPLANQTY, new Integer(0));
		setValue(PLANQTY, new Integer(0));
		setValue(PLANENABLEQTY, new Integer(0));
		setValue(RESULTQTY, new Integer(0));
		setValue(SHORTAGECNT, new Integer(0));
		setValue(PENDINGQTY, new Integer(0));
		setValue(ENTERINGQTY, new Integer(0));
		setValue(BUNDLEENTERINGQTY, new Integer(0));
		setValue(INSTOCKLINENO, new Integer(0));
		setValue(SHIPPINGLINENO, new Integer(0));
		setValue(ORDERSEQNO, new Integer(0));
		setValue(SYSTEMDISCKEY, new Integer(0));
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
	//#CM703813
	// package methods
	//#CM703814
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703815
	//------------------------------------------------------------


	//#CM703816
	//------------------------------------------------------------
	//#CM703817
	// protected methods
	//#CM703818
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM703819
	//------------------------------------------------------------


	//#CM703820
	//------------------------------------------------------------
	//#CM703821
	// private methods
	//#CM703822
	//------------------------------------------------------------
	//#CM703823
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


	//#CM703824
	//------------------------------------------------------------
	//#CM703825
	// utility methods
	//#CM703826
	//------------------------------------------------------------
	//#CM703827
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: HostSend.java,v 1.5 2006/11/16 02:15:45 suresh Exp $" ;
	}
}
