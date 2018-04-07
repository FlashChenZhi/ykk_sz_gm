//#CM707775
//$Id: WorkingInformation.java,v 1.4 2006/11/16 02:15:26 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM707776
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
import jp.co.daifuku.wms.base.entity.WorkingInformation;

//#CM707777
/**
 * Entity class of WORKINFO
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
 * @version $Revision: 1.4 $, $Date: 2006/11/16 02:15:26 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class WorkingInformation
		extends AbstractEntity implements Cloneable
{
	//#CM707778
	//------------------------------------------------------------
	//#CM707779
	// class variables (prefix '$')
	//#CM707780
	//------------------------------------------------------------
	//#CM707781
	//	private String	$classVar ;

	//#CM707782
	//------------------------------------------------------------
	//#CM707783
	// fields (upper case only)
	//#CM707784
	//------------------------------------------------------------
	//#CM707785
	/*
	 *  * Table name : WORKINFO
	 * Work no :                       JOBNO               varchar2(8)
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
	 * Plan enable qty :               PLANENABLEQTY       number
	 * Result qty :                    RESULTQTY           number
	 * Shortage qty :                  SHORTAGECNT         number
	 * Pending qty :                   PENDINGQTY          number
	 * Entering Case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case/Piece Flag :               CASEPIECEFLAG       varchar2(1)
	 * Case/Piece Flag (Work form flag) :WORKFORMFLAG        varchar2(1)
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * TC/DC Flag :                    TCDCFLAG            varchar2(1)
	 * Use By Date :                   USEBYDATE           varchar2(8)
	 * Lot :                           LOTNO               varchar2(20)
	 * Plan :                          PLANINFORMATION     varchar2(40)
	 * Order no :                      ORDERNO             varchar2(16)
	 * Order sequence no :             ORDERSEQNO          number
	 * Ordering date :                 ORDERINGDATE        varchar2(8)
	 * Result use by date :            RESULTUSEBYDATE     varchar2(8)
	 * Result :                        RESULTLOTNO         varchar2(20)
	 * Result location no :            RESULTLOCATIONNO    varchar2(16)
	 * Report flag :                   REPORTFLAG          varchar2(1)
	 * Batch no (Schedule no) :        BATCHNO             varchar2(8)
	 * System Connection key :         SYSTEMCONNKEY       varchar2(16)
	 * System Identification key :     SYSTEMDISCKEY       number
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Worker name :                   WORKERNAME          varchar2(20)
	 * Terminal No :                   TERMINALNO          varchar2(3)
	 * Plan registration date :        PLANREGISTDATE      date
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM707786
	/**Table name definition*/

	public static final String TABLE_NAME = "DNWORKINFO";

	//#CM707787
	/** Column Definition (JOBNO) */

	public static final FieldName JOBNO = new FieldName("JOB_NO");

	//#CM707788
	/** Column Definition (JOBTYPE) */

	public static final FieldName JOBTYPE = new FieldName("JOB_TYPE");

	//#CM707789
	/** Column Definition (COLLECTJOBNO) */

	public static final FieldName COLLECTJOBNO = new FieldName("COLLECT_JOB_NO");

	//#CM707790
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM707791
	/** Column Definition (BEGINNINGFLAG) */

	public static final FieldName BEGINNINGFLAG = new FieldName("BEGINNING_FLAG");

	//#CM707792
	/** Column Definition (PLANUKEY) */

	public static final FieldName PLANUKEY = new FieldName("PLAN_UKEY");

	//#CM707793
	/** Column Definition (STOCKID) */

	public static final FieldName STOCKID = new FieldName("STOCK_ID");

	//#CM707794
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM707795
	/** Column Definition (ZONENO) */

	public static final FieldName ZONENO = new FieldName("ZONE_NO");

	//#CM707796
	/** Column Definition (LOCATIONNO) */

	public static final FieldName LOCATIONNO = new FieldName("LOCATION_NO");

	//#CM707797
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM707798
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM707799
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM707800
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM707801
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM707802
	/** Column Definition (INSTOCKTICKETNO) */

	public static final FieldName INSTOCKTICKETNO = new FieldName("INSTOCK_TICKET_NO");

	//#CM707803
	/** Column Definition (INSTOCKLINENO) */

	public static final FieldName INSTOCKLINENO = new FieldName("INSTOCK_LINE_NO");

	//#CM707804
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM707805
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM707806
	/** Column Definition (SHIPPINGTICKETNO) */

	public static final FieldName SHIPPINGTICKETNO = new FieldName("SHIPPING_TICKET_NO");

	//#CM707807
	/** Column Definition (SHIPPINGLINENO) */

	public static final FieldName SHIPPINGLINENO = new FieldName("SHIPPING_LINE_NO");

	//#CM707808
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM707809
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM707810
	/** Column Definition (HOSTPLANQTY) */

	public static final FieldName HOSTPLANQTY = new FieldName("HOST_PLAN_QTY");

	//#CM707811
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM707812
	/** Column Definition (PLANENABLEQTY) */

	public static final FieldName PLANENABLEQTY = new FieldName("PLAN_ENABLE_QTY");

	//#CM707813
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM707814
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM707815
	/** Column Definition (PENDINGQTY) */

	public static final FieldName PENDINGQTY = new FieldName("PENDING_QTY");

	//#CM707816
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM707817
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM707818
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM707819
	/** Column Definition (WORKFORMFLAG) */

	public static final FieldName WORKFORMFLAG = new FieldName("WORK_FORM_FLAG");

	//#CM707820
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM707821
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM707822
	/** Column Definition (TCDCFLAG) */

	public static final FieldName TCDCFLAG = new FieldName("TCDC_FLAG");

	//#CM707823
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM707824
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM707825
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM707826
	/** Column Definition (ORDERNO) */

	public static final FieldName ORDERNO = new FieldName("ORDER_NO");

	//#CM707827
	/** Column Definition (ORDERSEQNO) */

	public static final FieldName ORDERSEQNO = new FieldName("ORDER_SEQ_NO");

	//#CM707828
	/** Column Definition (ORDERINGDATE) */

	public static final FieldName ORDERINGDATE = new FieldName("ORDERING_DATE");

	//#CM707829
	/** Column Definition (RESULTUSEBYDATE) */

	public static final FieldName RESULTUSEBYDATE = new FieldName("RESULT_USE_BY_DATE");

	//#CM707830
	/** Column Definition (RESULTLOTNO) */

	public static final FieldName RESULTLOTNO = new FieldName("RESULT_LOT_NO");

	//#CM707831
	/** Column Definition (RESULTLOCATIONNO) */

	public static final FieldName RESULTLOCATIONNO = new FieldName("RESULT_LOCATION_NO");

	//#CM707832
	/** Column Definition (REPORTFLAG) */

	public static final FieldName REPORTFLAG = new FieldName("REPORT_FLAG");

	//#CM707833
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM707834
	/** Column Definition (SYSTEMCONNKEY) */

	public static final FieldName SYSTEMCONNKEY = new FieldName("SYSTEM_CONN_KEY");

	//#CM707835
	/** Column Definition (SYSTEMDISCKEY) */

	public static final FieldName SYSTEMDISCKEY = new FieldName("SYSTEM_DISC_KEY");

	//#CM707836
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM707837
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM707838
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM707839
	/** Column Definition (PLANREGISTDATE) */

	public static final FieldName PLANREGISTDATE = new FieldName("PLAN_REGIST_DATE");

	//#CM707840
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM707841
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM707842
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM707843
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM707844
	//------------------------------------------------------------
	//#CM707845
	// properties (prefix 'p_')
	//#CM707846
	//------------------------------------------------------------
	//#CM707847
	//	private String	p_Name ;


	//#CM707848
	//------------------------------------------------------------
	//#CM707849
	// instance variables (prefix '_')
	//#CM707850
	//------------------------------------------------------------
	//#CM707851
	//	private String	_instanceVar ;

	//#CM707852
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM707853
	//------------------------------------------------------------
	//#CM707854
	// constructors
	//#CM707855
	//------------------------------------------------------------

	//#CM707856
	/**
	 * Prepare class name list and generate instance
	 */
	public WorkingInformation()
	{
		super() ;
		prepare() ;
    	setInitCreateColumn();
	}

	//#CM707857
	//------------------------------------------------------------
	//#CM707858
	// accessors
	//#CM707859
	//------------------------------------------------------------

	//#CM707860
	/**
	 * Set value to Work no
	 * @param arg Work no to be set
	 */
	public void setJobNo(String arg)
	{
		setValue(JOBNO, arg);
	}

	//#CM707861
	/**
	 * Fetch Work no
	 * @return Work no
	 */
	public String getJobNo()
	{
		return getValue(WorkingInformation.JOBNO).toString();
	}

	//#CM707862
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
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM707863
	/**
	 * Fetch Work type
	 * @return Work type
	 */
	public String getJobType()
	{
		return getValue(WorkingInformation.JOBTYPE).toString();
	}

	//#CM707864
	/**
	 * Set value to Collect work no
	 * @param arg Collect work no to be set
	 */
	public void setCollectJobNo(String arg)
	{
		setValue(COLLECTJOBNO, arg);
	}

	//#CM707865
	/**
	 * Fetch Collect work no
	 * @return Collect work no
	 */
	public String getCollectJobNo()
	{
		return getValue(WorkingInformation.COLLECTJOBNO).toString();
	}

	//#CM707866
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
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM707867
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(WorkingInformation.STATUSFLAG).toString();
	}

	//#CM707868
	/**
	 * Set value to Work start flag
	 * @param arg Work start flag to be set
	 */
	public void setBeginningFlag(String arg) throws InvalidStatusException
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
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM707869
	/**
	 * Fetch Work start flag
	 * @return Work start flag
	 */
	public String getBeginningFlag()
	{
		return getValue(WorkingInformation.BEGINNINGFLAG).toString();
	}

	//#CM707870
	/**
	 * Set value to Plan unique key
	 * @param arg Plan unique key to be set
	 */
	public void setPlanUkey(String arg)
	{
		setValue(PLANUKEY, arg);
	}

	//#CM707871
	/**
	 * Fetch Plan unique key
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return getValue(WorkingInformation.PLANUKEY).toString();
	}

	//#CM707872
	/**
	 * Set value to Stock ID
	 * @param arg Stock ID to be set
	 */
	public void setStockId(String arg)
	{
		setValue(STOCKID, arg);
	}

	//#CM707873
	/**
	 * Fetch Stock ID
	 * @return Stock ID
	 */
	public String getStockId()
	{
		return getValue(WorkingInformation.STOCKID).toString();
	}

	//#CM707874
	/**
	 * Set value to Area No
	 * @param arg Area No to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM707875
	/**
	 * Fetch Area No
	 * @return Area No
	 */
	public String getAreaNo()
	{
		return getValue(WorkingInformation.AREANO).toString();
	}

	//#CM707876
	/**
	 * Set value to Zone No
	 * @param arg Zone No to be set
	 */
	public void setZoneNo(String arg)
	{
		setValue(ZONENO, arg);
	}

	//#CM707877
	/**
	 * Fetch Zone No
	 * @return Zone No
	 */
	public String getZoneNo()
	{
		return getValue(WorkingInformation.ZONENO).toString();
	}

	//#CM707878
	/**
	 * Set value to Location No
	 * @param arg Location No to be set
	 */
	public void setLocationNo(String arg)
	{
		setValue(LOCATIONNO, arg);
	}

	//#CM707879
	/**
	 * Fetch Location No
	 * @return Location No
	 */
	public String getLocationNo()
	{
		return getValue(WorkingInformation.LOCATIONNO).toString();
	}

	//#CM707880
	/**
	 * Set value to Plan date
	 * @param arg Plan date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM707881
	/**
	 * Fetch Plan date
	 * @return Plan date
	 */
	public String getPlanDate()
	{
		return getValue(WorkingInformation.PLANDATE).toString();
	}

	//#CM707882
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM707883
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(WorkingInformation.CONSIGNORCODE).toString();
	}

	//#CM707884
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM707885
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(WorkingInformation.CONSIGNORNAME).toString();
	}

	//#CM707886
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM707887
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(WorkingInformation.SUPPLIERCODE).toString();
	}

	//#CM707888
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM707889
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(WorkingInformation.SUPPLIERNAME1).toString();
	}

	//#CM707890
	/**
	 * Set value to Receiving ticket no
	 * @param arg Receiving ticket no to be set
	 */
	public void setInstockTicketNo(String arg)
	{
		setValue(INSTOCKTICKETNO, arg);
	}

	//#CM707891
	/**
	 * Fetch Receiving ticket no
	 * @return Receiving ticket no
	 */
	public String getInstockTicketNo()
	{
		return getValue(WorkingInformation.INSTOCKTICKETNO).toString();
	}

	//#CM707892
	/**
	 * Set value to Receiving line no
	 * @param arg Receiving line no to be set
	 */
	public void setInstockLineNo(int arg)
	{
		setValue(INSTOCKLINENO, new Integer(arg));
	}

	//#CM707893
	/**
	 * Fetch Receiving line no
	 * @return Receiving line no
	 */
	public int getInstockLineNo()
	{
		return getBigDecimal(WorkingInformation.INSTOCKLINENO).intValue();
	}

	//#CM707894
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM707895
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(WorkingInformation.CUSTOMERCODE).toString();
	}

	//#CM707896
	/**
	 * Set value to Customer name
	 * @param arg Customer name to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM707897
	/**
	 * Fetch Customer name
	 * @return Customer name
	 */
	public String getCustomerName1()
	{
		return getValue(WorkingInformation.CUSTOMERNAME1).toString();
	}

	//#CM707898
	/**
	 * Set value to Shipping ticket no
	 * @param arg Shipping ticket no to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		setValue(SHIPPINGTICKETNO, arg);
	}

	//#CM707899
	/**
	 * Fetch Shipping ticket no
	 * @return Shipping ticket no
	 */
	public String getShippingTicketNo()
	{
		return getValue(WorkingInformation.SHIPPINGTICKETNO).toString();
	}

	//#CM707900
	/**
	 * Set value to Shipping line no
	 * @param arg Shipping line no to be set
	 */
	public void setShippingLineNo(int arg)
	{
		setValue(SHIPPINGLINENO, new Integer(arg));
	}

	//#CM707901
	/**
	 * Fetch Shipping line no
	 * @return Shipping line no
	 */
	public int getShippingLineNo()
	{
		return getBigDecimal(WorkingInformation.SHIPPINGLINENO).intValue();
	}

	//#CM707902
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM707903
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(WorkingInformation.ITEMCODE).toString();
	}

	//#CM707904
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM707905
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(WorkingInformation.ITEMNAME1).toString();
	}

	//#CM707906
	/**
	 * Set value to Host plan qty
	 * @param arg Host plan qty to be set
	 */
	public void setHostPlanQty(int arg)
	{
		setValue(HOSTPLANQTY, new Integer(arg));
	}

	//#CM707907
	/**
	 * Fetch Host plan qty
	 * @return Host plan qty
	 */
	public int getHostPlanQty()
	{
		return getBigDecimal(WorkingInformation.HOSTPLANQTY).intValue();
	}

	//#CM707908
	/**
	 * Set value to Work plan qty
	 * @param arg Work plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM707909
	/**
	 * Fetch Work plan qty
	 * @return Work plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(WorkingInformation.PLANQTY).intValue();
	}

	//#CM707910
	/**
	 * Set value to Plan enable qty
	 * @param arg Plan enable qty to be set
	 */
	public void setPlanEnableQty(int arg)
	{
		setValue(PLANENABLEQTY, new Integer(arg));
	}

	//#CM707911
	/**
	 * Fetch Plan enable qty
	 * @return Plan enable qty
	 */
	public int getPlanEnableQty()
	{
		return getBigDecimal(WorkingInformation.PLANENABLEQTY).intValue();
	}

	//#CM707912
	/**
	 * Set value to Result qty
	 * @param arg Result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM707913
	/**
	 * Fetch Result qty
	 * @return Result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(WorkingInformation.RESULTQTY).intValue();
	}

	//#CM707914
	/**
	 * Set value to Shortage qty
	 * @param arg Shortage qty to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM707915
	/**
	 * Fetch Shortage qty
	 * @return Shortage qty
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(WorkingInformation.SHORTAGECNT).intValue();
	}

	//#CM707916
	/**
	 * Set value to Pending qty
	 * @param arg Pending qty to be set
	 */
	public void setPendingQty(int arg)
	{
		setValue(PENDINGQTY, new Integer(arg));
	}

	//#CM707917
	/**
	 * Fetch Pending qty
	 * @return Pending qty
	 */
	public int getPendingQty()
	{
		return getBigDecimal(WorkingInformation.PENDINGQTY).intValue();
	}

	//#CM707918
	/**
	 * Set value to Entering Case qty
	 * @param arg Entering Case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM707919
	/**
	 * Fetch Entering Case qty
	 * @return Entering Case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(WorkingInformation.ENTERINGQTY).intValue();
	}

	//#CM707920
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM707921
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(WorkingInformation.BUNDLEENTERINGQTY).intValue();
	}

	//#CM707922
	/**
	 * Set value to Case/Piece Flag
	 * @param arg Case/Piece Flag to be set
	 */
	public void setCasePieceFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(CASEPIECE_FLAG_CASE))
			|| (arg.equals(CASEPIECE_FLAG_PIECE))
			|| (arg.equals(CASEPIECE_FLAG_NOTHING)))
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

	//#CM707923
	/**
	 * Fetch Case/Piece Flag
	 * @return Case/Piece Flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(WorkingInformation.CASEPIECEFLAG).toString();
	}

	//#CM707924
	/**
	 * Set value to Case/Piece Flag (Work form flag)
	 * @param arg Case/Piece Flag (Work form flag) to be set
	 */
	public void setWorkFormFlag(String arg) throws InvalidStatusException
	{
if ((arg.equals(CASEPIECE_FLAG_CASE))
			|| (arg.equals(CASEPIECE_FLAG_PIECE))
			|| (arg.equals(CASEPIECE_FLAG_NOTHING)))
		{
			setValue(WORKFORMFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wWorkFormFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM707925
	/**
	 * Fetch Case/Piece Flag (Work form flag)
	 * @return Case/Piece Flag (Work form flag)
	 */
	public String getWorkFormFlag()
	{
		return getValue(WorkingInformation.WORKFORMFLAG).toString();
	}

	//#CM707926
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM707927
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(WorkingInformation.ITF).toString();
	}

	//#CM707928
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM707929
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(WorkingInformation.BUNDLEITF).toString();
	}

	//#CM707930
	/**
	 * Set value to TC/DC Flag
	 * @param arg TC/DC Flag to be set
	 */
	public void setTcdcFlag(String arg) throws InvalidStatusException
	{
if ((arg.equals(TCDC_FLAG_DC))
			|| (arg.equals(TCDC_FLAG_TC))
			|| (arg.equals(TCDC_FLAG_CROSSTC)))
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
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM707931
	/**
	 * Fetch TC/DC Flag
	 * @return TC/DC Flag
	 */
	public String getTcdcFlag()
	{
		return getValue(WorkingInformation.TCDCFLAG).toString();
	}

	//#CM707932
	/**
	 * Set value to Use By Date
	 * @param arg Use By Date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM707933
	/**
	 * Fetch Use By Date
	 * @return Use By Date
	 */
	public String getUseByDate()
	{
		return getValue(WorkingInformation.USEBYDATE).toString();
	}

	//#CM707934
	/**
	 * Set value to Lot
	 * @param arg Lot to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM707935
	/**
	 * Fetch Lot
	 * @return Lot
	 */
	public String getLotNo()
	{
		return getValue(WorkingInformation.LOTNO).toString();
	}

	//#CM707936
	/**
	 * Set value to Plan
	 * @param arg Plan to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM707937
	/**
	 * Fetch Plan
	 * @return Plan
	 */
	public String getPlanInformation()
	{
		return getValue(WorkingInformation.PLANINFORMATION).toString();
	}

	//#CM707938
	/**
	 * Set value to Order no
	 * @param arg Order no to be set
	 */
	public void setOrderNo(String arg)
	{
		setValue(ORDERNO, arg);
	}

	//#CM707939
	/**
	 * Fetch Order no
	 * @return Order no
	 */
	public String getOrderNo()
	{
		return getValue(WorkingInformation.ORDERNO).toString();
	}

	//#CM707940
	/**
	 * Set value to Order sequence no
	 * @param arg Order sequence no to be set
	 */
	public void setOrderSeqNo(int arg)
	{
		setValue(ORDERSEQNO, new Integer(arg));
	}

	//#CM707941
	/**
	 * Fetch Order sequence no
	 * @return Order sequence no
	 */
	public int getOrderSeqNo()
	{
		return getBigDecimal(WorkingInformation.ORDERSEQNO).intValue();
	}

	//#CM707942
	/**
	 * Set value to Ordering date
	 * @param arg Ordering date to be set
	 */
	public void setOrderingDate(String arg)
	{
		setValue(ORDERINGDATE, arg);
	}

	//#CM707943
	/**
	 * Fetch Ordering date
	 * @return Ordering date
	 */
	public String getOrderingDate()
	{
		return getValue(WorkingInformation.ORDERINGDATE).toString();
	}

	//#CM707944
	/**
	 * Set value to Result use by date
	 * @param arg Result use by date to be set
	 */
	public void setResultUseByDate(String arg)
	{
		setValue(RESULTUSEBYDATE, arg);
	}

	//#CM707945
	/**
	 * Fetch Result use by date
	 * @return Result use by date
	 */
	public String getResultUseByDate()
	{
		return getValue(WorkingInformation.RESULTUSEBYDATE).toString();
	}

	//#CM707946
	/**
	 * Set value to Result
	 * @param arg Result to be set
	 */
	public void setResultLotNo(String arg)
	{
		setValue(RESULTLOTNO, arg);
	}

	//#CM707947
	/**
	 * Fetch Result
	 * @return Result
	 */
	public String getResultLotNo()
	{
		return getValue(WorkingInformation.RESULTLOTNO).toString();
	}

	//#CM707948
	/**
	 * Set value to Result location no
	 * @param arg Result location no to be set
	 */
	public void setResultLocationNo(String arg)
	{
		setValue(RESULTLOCATIONNO, arg);
	}

	//#CM707949
	/**
	 * Fetch Result location no
	 * @return Result location no
	 */
	public String getResultLocationNo()
	{
		return getValue(WorkingInformation.RESULTLOCATIONNO).toString();
	}

	//#CM707950
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
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM707951
	/**
	 * Fetch Report flag
	 * @return Report flag
	 */
	public String getReportFlag()
	{
		return getValue(WorkingInformation.REPORTFLAG).toString();
	}

	//#CM707952
	/**
	 * Set value to Batch no (Schedule no)
	 * @param arg Batch no (Schedule no) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM707953
	/**
	 * Fetch Batch no (Schedule no)
	 * @return Batch no (Schedule no)
	 */
	public String getBatchNo()
	{
		return getValue(WorkingInformation.BATCHNO).toString();
	}

	//#CM707954
	/**
	 * Set value to System Connection key
	 * @param arg System Connection key to be set
	 */
	public void setSystemConnKey(String arg)
	{
		setValue(SYSTEMCONNKEY, arg);
	}

	//#CM707955
	/**
	 * Fetch System Connection key
	 * @return System Connection key
	 */
	public String getSystemConnKey()
	{
		return getValue(WorkingInformation.SYSTEMCONNKEY).toString();
	}

	//#CM707956
	/**
	 * Set value to System Identification key
	 * @param arg System Identification key to be set
	 */
	public void setSystemDiscKey(int arg)
	{
		setValue(SYSTEMDISCKEY, new Integer(arg));
	}

	//#CM707957
	/**
	 * Fetch System Identification key
	 * @return System Identification key
	 */
	public int getSystemDiscKey()
	{
		return getBigDecimal(WorkingInformation.SYSTEMDISCKEY).intValue();
	}

	//#CM707958
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM707959
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(WorkingInformation.WORKERCODE).toString();
	}

	//#CM707960
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM707961
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(WorkingInformation.WORKERNAME).toString();
	}

	//#CM707962
	/**
	 * Set value to Terminal No
	 * @param arg Terminal No to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM707963
	/**
	 * Fetch Terminal No
	 * @return Terminal No
	 */
	public String getTerminalNo()
	{
		return getValue(WorkingInformation.TERMINALNO).toString();
	}

	//#CM707964
	/**
	 * Set value to Plan registration date
	 * @param arg Plan registration date to be set
	 */
	public void setPlanRegistDate(Date arg)
	{
		setValue(PLANREGISTDATE, arg);
	}

	//#CM707965
	/**
	 * Fetch Plan registration date
	 * @return Plan registration date
	 */
	public Date getPlanRegistDate()
	{
		return (Date)getValue(WorkingInformation.PLANREGISTDATE);
	}

	//#CM707966
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM707967
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(WorkingInformation.REGISTDATE);
	}

	//#CM707968
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM707969
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(WorkingInformation.REGISTPNAME).toString();
	}

	//#CM707970
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM707971
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(WorkingInformation.LASTUPDATEDATE);
	}

	//#CM707972
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM707973
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(WorkingInformation.LASTUPDATEPNAME).toString();
	}


	//#CM707974
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}
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
		setValue(INSTOCKLINENO, new Integer(0));
		setValue(SHIPPINGLINENO, new Integer(0));
		setValue(ORDERSEQNO, new Integer(0));
		setValue(SYSTEMDISCKEY, new Integer(0));

	}

	public String[] getAutoUpdateColumnArray()
	{
		String prefix = TABLE_NAME + "." ;

		Vector autoColumn = new Vector();
		autoColumn.add(prefix + LASTUPDATEDATE);

		String[] autoColumnArray = new String[autoColumn.size()];
		autoColumn.copyInto(autoColumnArray);

		return autoColumnArray;

	}

	//#CM707975
	/**
	 * Generate the reproduction of the object.
	 * @return Copy of the object
	 */
	public Object clone()
	{
		WorkingInformation dest = new WorkingInformation();
		try
		{
			dest.setAreaNo(getAreaNo());
			dest.setZoneNo(getZoneNo());
			dest.setBatchNo(getBatchNo());
			dest.setBeginningFlag(getBeginningFlag());
			dest.setBundleEnteringQty(getBundleEnteringQty());
			dest.setBundleItf(getBundleItf());
			dest.setCasePieceFlag(getCasePieceFlag());
			dest.setCollectJobNo(getCollectJobNo());
			dest.setConsignorCode(getConsignorCode());
			dest.setConsignorName(getConsignorName());
			dest.setCustomerCode(getCustomerCode());
			dest.setCustomerName1(getCustomerName1());
			dest.setEnteringQty(getEnteringQty());
			dest.setHostPlanQty(getHostPlanQty());
			dest.setInstockLineNo(getInstockLineNo());
			dest.setInstockTicketNo(getInstockTicketNo());
			dest.setItemCode(getItemCode());
			dest.setItemName1(getItemName1());
			dest.setItf(getItf());
			dest.setJobNo(getJobNo());
			dest.setJobType(getJobType());
			dest.setLastUpdateDate(getLastUpdateDate());
			dest.setLastUpdatePname(getLastUpdatePname());
			dest.setLocationNo(getLocationNo());
			dest.setLotNo(getLotNo());
			dest.setOrderingDate(getOrderingDate());
			dest.setOrderNo(getOrderNo());
			dest.setOrderSeqNo(getOrderSeqNo());
			dest.setPendingQty(getPendingQty());
			dest.setPlanDate(getPlanDate());
			dest.setPlanEnableQty(getPlanEnableQty());
			dest.setPlanInformation(getPlanInformation());
			dest.setPlanQty(getPlanQty());
			dest.setPlanRegistDate(getPlanRegistDate());
			dest.setPlanUkey(getPlanUkey());
			dest.setRegistDate(getRegistDate());
			dest.setRegistPname(getRegistPname());
			dest.setReportFlag(getReportFlag());
			dest.setResultLocationNo(getResultLocationNo());
			dest.setResultLotNo(getResultLotNo());
			dest.setResultQty(getResultQty());
			dest.setResultUseByDate(getResultUseByDate());
			dest.setShippingLineNo(getShippingLineNo());
			dest.setShippingTicketNo(getShippingTicketNo());
			dest.setShortageCnt(getShortageCnt());
			dest.setStatusFlag(getStatusFlag());
			dest.setStockId(getStockId());
			dest.setSupplierCode(getSupplierCode());
			dest.setSupplierName1(getSupplierName1());
			dest.setTcdcFlag(getTcdcFlag());
			dest.setTerminalNo(getTerminalNo());
			dest.setUseByDate(getUseByDate());
			dest.setWorkerCode(getWorkerCode());
			dest.setWorkerName(getWorkerName());
			dest.setWorkFormFlag(getWorkFormFlag());
			dest.setSystemConnKey(getSystemConnKey());
			dest.setSystemDiscKey(getSystemDiscKey());
		}
		catch (InvalidStatusException e)
		{
			//#CM707976
			// Do not process this exception because it is sure not to be generated.
		}

		return dest;
	}
	//#CM707977
	//------------------------------------------------------------
	//#CM707978
	// package methods
	//#CM707979
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707980
	//------------------------------------------------------------


	//#CM707981
	//------------------------------------------------------------
	//#CM707982
	// protected methods
	//#CM707983
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707984
	//------------------------------------------------------------


	//#CM707985
	//------------------------------------------------------------
	//#CM707986
	// private methods
	//#CM707987
	//------------------------------------------------------------
	//#CM707988
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

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


	//#CM707989
	//------------------------------------------------------------
	//#CM707990
	// utility methods
	//#CM707991
	//------------------------------------------------------------
	//#CM707992
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: WorkingInformation.java,v 1.4 2006/11/16 02:15:26 suresh Exp $" ;
	}
}
