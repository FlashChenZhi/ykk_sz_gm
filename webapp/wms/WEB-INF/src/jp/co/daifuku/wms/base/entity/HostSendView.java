//#CM703828
//$Id: HostSendView.java,v 1.4 2006/11/13 04:31:06 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM703829
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
import jp.co.daifuku.wms.base.entity.HostSendView;

//#CM703830
/**
 * Entity class of DVHOSTSENDVIEW
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
 * @version $Revision: 1.4 $, $Date: 2006/11/13 04:31:06 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class HostSendView
		extends AbstractEntity
{
	//#CM703831
	//------------------------------------------------------------
	//#CM703832
	// class variables (prefix '$')
	//#CM703833
	//------------------------------------------------------------
	//#CM703834
	//	private String	$classVar ;

	//#CM703835
	//------------------------------------------------------------
	//#CM703836
	// fields (upper case only)
	//#CM703837
	//------------------------------------------------------------
	//#CM703838
	/*
	 *  * Table name : DVHOSTSENDVIEW
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
	 * Lot no :                        LOTNO               varchar2(20)
	 * Plan details comment :          PLANINFORMATION     varchar2(40)
	 * Order no :                      ORDERNO             varchar2(16)
	 * Order sequence no :             ORDERSEQNO          number
	 * Order date :                    ORDERINGDATE        varchar2(8)
	 * Use by date :                   USEBYDATE           varchar2(8)
	 * Batch no (Schedule no) :        BATCHNO             varchar2(8)
	 * System identification key :     SYSTEMDISCKEY       number
	 * Registered date :               REGISTDATE          date
	 * Last update date :              LASTUPDATEDATE      date
	 */

	//#CM703839
	/**Table name definition*/

	public static final String TABLE_NAME = "DVHOSTSENDVIEW";

	//#CM703840
	/** Column Definition (WORKDATE) */

	public static final FieldName WORKDATE = new FieldName("WORK_DATE");

	//#CM703841
	/** Column Definition (JOBNO) */

	public static final FieldName JOBNO = new FieldName("JOB_NO");

	//#CM703842
	/** Column Definition (JOBTYPE) */

	public static final FieldName JOBTYPE = new FieldName("JOB_TYPE");

	//#CM703843
	/** Column Definition (COLLECTJOBNO) */

	public static final FieldName COLLECTJOBNO = new FieldName("COLLECT_JOB_NO");

	//#CM703844
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM703845
	/** Column Definition (BEGINNINGFLAG) */

	public static final FieldName BEGINNINGFLAG = new FieldName("BEGINNING_FLAG");

	//#CM703846
	/** Column Definition (PLANUKEY) */

	public static final FieldName PLANUKEY = new FieldName("PLAN_UKEY");

	//#CM703847
	/** Column Definition (STOCKID) */

	public static final FieldName STOCKID = new FieldName("STOCK_ID");

	//#CM703848
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM703849
	/** Column Definition (ZONENO) */

	public static final FieldName ZONENO = new FieldName("ZONE_NO");

	//#CM703850
	/** Column Definition (LOCATIONNO) */

	public static final FieldName LOCATIONNO = new FieldName("LOCATION_NO");

	//#CM703851
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM703852
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM703853
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM703854
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM703855
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM703856
	/** Column Definition (INSTOCKTICKETNO) */

	public static final FieldName INSTOCKTICKETNO = new FieldName("INSTOCK_TICKET_NO");

	//#CM703857
	/** Column Definition (INSTOCKLINENO) */

	public static final FieldName INSTOCKLINENO = new FieldName("INSTOCK_LINE_NO");

	//#CM703858
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM703859
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM703860
	/** Column Definition (SHIPPINGTICKETNO) */

	public static final FieldName SHIPPINGTICKETNO = new FieldName("SHIPPING_TICKET_NO");

	//#CM703861
	/** Column Definition (SHIPPINGLINENO) */

	public static final FieldName SHIPPINGLINENO = new FieldName("SHIPPING_LINE_NO");

	//#CM703862
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM703863
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM703864
	/** Column Definition (HOSTPLANQTY) */

	public static final FieldName HOSTPLANQTY = new FieldName("HOST_PLAN_QTY");

	//#CM703865
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM703866
	/** Column Definition (PLANENABLEQTY) */

	public static final FieldName PLANENABLEQTY = new FieldName("PLAN_ENABLE_QTY");

	//#CM703867
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM703868
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM703869
	/** Column Definition (PENDINGQTY) */

	public static final FieldName PENDINGQTY = new FieldName("PENDING_QTY");

	//#CM703870
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM703871
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM703872
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM703873
	/** Column Definition (WORKFORMFLAG) */

	public static final FieldName WORKFORMFLAG = new FieldName("WORK_FORM_FLAG");

	//#CM703874
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM703875
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM703876
	/** Column Definition (TCDCFLAG) */

	public static final FieldName TCDCFLAG = new FieldName("TCDC_FLAG");

	//#CM703877
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM703878
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM703879
	/** Column Definition (ORDERNO) */

	public static final FieldName ORDERNO = new FieldName("ORDER_NO");

	//#CM703880
	/** Column Definition (ORDERSEQNO) */

	public static final FieldName ORDERSEQNO = new FieldName("ORDER_SEQ_NO");

	//#CM703881
	/** Column Definition (ORDERINGDATE) */

	public static final FieldName ORDERINGDATE = new FieldName("ORDERING_DATE");

	//#CM703882
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM703883
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM703884
	/** Column Definition (SYSTEMDISCKEY) */

	public static final FieldName SYSTEMDISCKEY = new FieldName("SYSTEM_DISC_KEY");

	//#CM703885
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM703886
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");


	//#CM703887
	//------------------------------------------------------------
	//#CM703888
	// properties (prefix 'p_')
	//#CM703889
	//------------------------------------------------------------
	//#CM703890
	//	private String	p_Name ;


	//#CM703891
	//------------------------------------------------------------
	//#CM703892
	// instance variables (prefix '_')
	//#CM703893
	//------------------------------------------------------------
	//#CM703894
	//	private String	_instanceVar ;

	//#CM703895
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM703896
	//------------------------------------------------------------
	//#CM703897
	// constructors
	//#CM703898
	//------------------------------------------------------------

	//#CM703899
	/**
	 * Prepare class name list and generate instance
	 */
	public HostSendView()
	{
		super() ;
		prepare() ;
	}

	//#CM703900
	//------------------------------------------------------------
	//#CM703901
	// accessors
	//#CM703902
	//------------------------------------------------------------

	//#CM703903
	/**
	 * Set value to Work date
	 * @param arg Work date to be set
	 */
	public void setWorkDate(String arg)
	{
		setValue(WORKDATE, arg);
	}

	//#CM703904
	/**
	 * Fetch Work date
	 * @return Work date
	 */
	public String getWorkDate()
	{
		return getValue(HostSendView.WORKDATE).toString();
	}

	//#CM703905
	/**
	 * Set value to Work no
	 * @param arg Work no to be set
	 */
	public void setJobNo(String arg)
	{
		setValue(JOBNO, arg);
	}

	//#CM703906
	/**
	 * Fetch Work no
	 * @return Work no
	 */
	public String getJobNo()
	{
		return getValue(HostSendView.JOBNO).toString();
	}

	//#CM703907
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
			//#CM703908
			// 6006009 = Cannot set. Value beyond the specific range is specified. Class={0} Variable={1} Value={2}
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM703909
	/**
	 * Fetch Work type
	 * @return Work type
	 */
	public String getJobType()
	{
		return getValue(HostSendView.JOBTYPE).toString();
	}

	//#CM703910
	/**
	 * Set value to Collect work no
	 * @param arg Collect work no to be set
	 */
	public void setCollectJobNo(String arg)
	{
		setValue(COLLECTJOBNO, arg);
	}

	//#CM703911
	/**
	 * Fetch Collect work no
	 * @return Collect work no
	 */
	public String getCollectJobNo()
	{
		return getValue(HostSendView.COLLECTJOBNO).toString();
	}

	//#CM703912
	/**
	 * Set value to Status flag
	 * @param arg Status flag to be set
	 */
	public void setStatusFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(STATUS_FLAG_UNSTART))
			|| (arg.equals(STATUS_FLAG_START))
			|| (arg.equals(STATUS_FLAG_NOWWORKING))
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
			//#CM703913
			// 6006009 = Cannot set. Value beyond the specific range is specified. Class={0} Variable={1} Value={2}
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM703914
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(HostSendView.STATUSFLAG).toString();
	}

	//#CM703915
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
			//#CM703916
			// 6006009 = Cannot set. Value beyond the specific range is specified. Class={0} Variable={1} Value={2}
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM703917
	/**
	 * Fetch Work start flag
	 * @return Work start flag
	 */
	public String getBeginningFlag()
	{
		return getValue(HostSendView.BEGINNINGFLAG).toString();
	}

	//#CM703918
	/**
	 * Set value to Plan unique key
	 * @param arg Plan unique key to be set
	 */
	public void setPlanUkey(String arg)
	{
		setValue(PLANUKEY, arg);
	}

	//#CM703919
	/**
	 * Fetch Plan unique key
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return getValue(HostSendView.PLANUKEY).toString();
	}

	//#CM703920
	/**
	 * Set value to Stock ID
	 * @param arg Stock ID to be set
	 */
	public void setStockId(String arg)
	{
		setValue(STOCKID, arg);
	}

	//#CM703921
	/**
	 * Fetch Stock ID
	 * @return Stock ID
	 */
	public String getStockId()
	{
		return getValue(HostSendView.STOCKID).toString();
	}

	//#CM703922
	/**
	 * Set value to Area no
	 * @param arg Area no to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM703923
	/**
	 * Fetch Area no
	 * @return Area no
	 */
	public String getAreaNo()
	{
		return getValue(HostSendView.AREANO).toString();
	}

	//#CM703924
	/**
	 * Set value to Zone no
	 * @param arg Zone no to be set
	 */
	public void setZoneNo(String arg)
	{
		setValue(ZONENO, arg);
	}

	//#CM703925
	/**
	 * Fetch Zone no
	 * @return Zone no
	 */
	public String getZoneNo()
	{
		return getValue(HostSendView.ZONENO).toString();
	}

	//#CM703926
	/**
	 * Set value to Location no
	 * @param arg Location no to be set
	 */
	public void setLocationNo(String arg)
	{
		setValue(LOCATIONNO, arg);
	}

	//#CM703927
	/**
	 * Fetch Location no
	 * @return Location no
	 */
	public String getLocationNo()
	{
		return getValue(HostSendView.LOCATIONNO).toString();
	}

	//#CM703928
	/**
	 * Set value to Plan date
	 * @param arg Plan date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM703929
	/**
	 * Fetch Plan date
	 * @return Plan date
	 */
	public String getPlanDate()
	{
		return getValue(HostSendView.PLANDATE).toString();
	}

	//#CM703930
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM703931
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(HostSendView.CONSIGNORCODE).toString();
	}

	//#CM703932
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM703933
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(HostSendView.CONSIGNORNAME).toString();
	}

	//#CM703934
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM703935
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(HostSendView.SUPPLIERCODE).toString();
	}

	//#CM703936
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM703937
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(HostSendView.SUPPLIERNAME1).toString();
	}

	//#CM703938
	/**
	 * Set value to Receiving ticket no
	 * @param arg Receiving ticket no to be set
	 */
	public void setInstockTicketNo(String arg)
	{
		setValue(INSTOCKTICKETNO, arg);
	}

	//#CM703939
	/**
	 * Fetch Receiving ticket no
	 * @return Receiving ticket no
	 */
	public String getInstockTicketNo()
	{
		return getValue(HostSendView.INSTOCKTICKETNO).toString();
	}

	//#CM703940
	/**
	 * Set value to Receiving line no
	 * @param arg Receiving line no to be set
	 */
	public void setInstockLineNo(int arg)
	{
		setValue(INSTOCKLINENO, new Integer(arg));
	}

	//#CM703941
	/**
	 * Fetch Receiving line no
	 * @return Receiving line no
	 */
	public int getInstockLineNo()
	{
		return getBigDecimal(HostSendView.INSTOCKLINENO).intValue();
	}

	//#CM703942
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM703943
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(HostSendView.CUSTOMERCODE).toString();
	}

	//#CM703944
	/**
	 * Set value to Customer name
	 * @param arg Customer name to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM703945
	/**
	 * Fetch Customer name
	 * @return Customer name
	 */
	public String getCustomerName1()
	{
		return getValue(HostSendView.CUSTOMERNAME1).toString();
	}

	//#CM703946
	/**
	 * Set value to Shipping ticket no
	 * @param arg Shipping ticket no to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		setValue(SHIPPINGTICKETNO, arg);
	}

	//#CM703947
	/**
	 * Fetch Shipping ticket no
	 * @return Shipping ticket no
	 */
	public String getShippingTicketNo()
	{
		return getValue(HostSendView.SHIPPINGTICKETNO).toString();
	}

	//#CM703948
	/**
	 * Set value to Shipping line no
	 * @param arg Shipping line no to be set
	 */
	public void setShippingLineNo(int arg)
	{
		setValue(SHIPPINGLINENO, new Integer(arg));
	}

	//#CM703949
	/**
	 * Fetch Shipping line no
	 * @return Shipping line no
	 */
	public int getShippingLineNo()
	{
		return getBigDecimal(HostSendView.SHIPPINGLINENO).intValue();
	}

	//#CM703950
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM703951
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(HostSendView.ITEMCODE).toString();
	}

	//#CM703952
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM703953
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(HostSendView.ITEMNAME1).toString();
	}

	//#CM703954
	/**
	 * Set value to Host plan qty
	 * @param arg Host plan qty to be set
	 */
	public void setHostPlanQty(int arg)
	{
		setValue(HOSTPLANQTY, new Integer(arg));
	}

	//#CM703955
	/**
	 * Fetch Host plan qty
	 * @return Host plan qty
	 */
	public int getHostPlanQty()
	{
		return getBigDecimal(HostSendView.HOSTPLANQTY).intValue();
	}

	//#CM703956
	/**
	 * Set value to Work plan qty
	 * @param arg Work plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM703957
	/**
	 * Fetch Work plan qty
	 * @return Work plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(HostSendView.PLANQTY).intValue();
	}

	//#CM703958
	/**
	 * Set value to Plan enabled qty
	 * @param arg Plan enabled qty to be set
	 */
	public void setPlanEnableQty(int arg)
	{
		setValue(PLANENABLEQTY, new Integer(arg));
	}

	//#CM703959
	/**
	 * Fetch Plan enabled qty
	 * @return Plan enabled qty
	 */
	public int getPlanEnableQty()
	{
		return getBigDecimal(HostSendView.PLANENABLEQTY).intValue();
	}

	//#CM703960
	/**
	 * Set value to Result qty
	 * @param arg Result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM703961
	/**
	 * Fetch Result qty
	 * @return Result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(HostSendView.RESULTQTY).intValue();
	}

	//#CM703962
	/**
	 * Set value to Shortage qty
	 * @param arg Shortage qty to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM703963
	/**
	 * Fetch Shortage qty
	 * @return Shortage qty
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(HostSendView.SHORTAGECNT).intValue();
	}

	//#CM703964
	/**
	 * Set value to Pending qty
	 * @param arg Pending qty to be set
	 */
	public void setPendingQty(int arg)
	{
		setValue(PENDINGQTY, new Integer(arg));
	}

	//#CM703965
	/**
	 * Fetch Pending qty
	 * @return Pending qty
	 */
	public int getPendingQty()
	{
		return getBigDecimal(HostSendView.PENDINGQTY).intValue();
	}

	//#CM703966
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM703967
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(HostSendView.ENTERINGQTY).intValue();
	}

	//#CM703968
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM703969
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(HostSendView.BUNDLEENTERINGQTY).intValue();
	}

	//#CM703970
	/**
	 * Set value to Case/Piece flag
	 * @param arg Case/Piece flag to be set
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
			//#CM703971
			// 6006009 = Cannot set. Value beyond the specific range is specified. Class={0} Variable={1} Value={2}
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM703972
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(HostSendView.CASEPIECEFLAG).toString();
	}

	//#CM703973
	/**
	 * Set value to Case/Piece flag (Work form flag)
	 * @param arg Case/Piece flag (Work form flag) to be set
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
			//#CM703974
			// 6006009 = Cannot set. Value beyond the specific range is specified. Class={0} Variable={1} Value={2}
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM703975
	/**
	 * Fetch Case/Piece flag (Work form flag)
	 * @return Case/Piece flag (Work form flag)
	 */
	public String getWorkFormFlag()
	{
		return getValue(HostSendView.WORKFORMFLAG).toString();
	}

	//#CM703976
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM703977
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(HostSendView.ITF).toString();
	}

	//#CM703978
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM703979
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(HostSendView.BUNDLEITF).toString();
	}

	//#CM703980
	/**
	 * Set value to TC/DC flag
	 * @param arg TC/DC flag to be set
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
			//#CM703981
			// 6006009 = Cannot set. Value beyond the specific range is specified. Class={0} Variable={1} Value={2}
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (new InvalidStatusException("6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}
	//#CM703982
	/**
	 * Fetch TC/DC flag
	 * @return TC/DC flag
	 */
	public String getTcdcFlag()
	{
		return getValue(HostSendView.TCDCFLAG).toString();
	}

	//#CM703983
	/**
	 * Set value to Lot no
	 * @param arg Lot no to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM703984
	/**
	 * Fetch Lot no
	 * @return Lot no
	 */
	public String getLotNo()
	{
		return getValue(HostSendView.LOTNO).toString();
	}

	//#CM703985
	/**
	 * Set value to Plan details comment
	 * @param arg Plan details comment to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM703986
	/**
	 * Fetch Plan details comment
	 * @return Plan details comment
	 */
	public String getPlanInformation()
	{
		return getValue(HostSendView.PLANINFORMATION).toString();
	}

	//#CM703987
	/**
	 * Set value to Order no
	 * @param arg Order no to be set
	 */
	public void setOrderNo(String arg)
	{
		setValue(ORDERNO, arg);
	}

	//#CM703988
	/**
	 * Fetch Order no
	 * @return Order no
	 */
	public String getOrderNo()
	{
		return getValue(HostSendView.ORDERNO).toString();
	}

	//#CM703989
	/**
	 * Set value to Order sequence no
	 * @param arg Order sequence no to be set
	 */
	public void setOrderSeqNo(int arg)
	{
		setValue(ORDERSEQNO, new Integer(arg));
	}

	//#CM703990
	/**
	 * Fetch Order sequence no
	 * @return Order sequence no
	 */
	public int getOrderSeqNo()
	{
		return getBigDecimal(HostSendView.ORDERSEQNO).intValue();
	}

	//#CM703991
	/**
	 * Set value to Order date
	 * @param arg Order date to be set
	 */
	public void setOrderingDate(String arg)
	{
		setValue(ORDERINGDATE, arg);
	}

	//#CM703992
	/**
	 * Fetch Order date
	 * @return Order date
	 */
	public String getOrderingDate()
	{
		return getValue(HostSendView.ORDERINGDATE).toString();
	}

	//#CM703993
	/**
	 * Set value to Use by date
	 * @param arg Use by date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM703994
	/**
	 * Fetch Use by date
	 * @return Use by date
	 */
	public String getUseByDate()
	{
		return getValue(HostSendView.USEBYDATE).toString();
	}

	//#CM703995
	/**
	 * Set value to Batch no (Schedule no)
	 * @param arg Batch no (Schedule no) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM703996
	/**
	 * Fetch Batch no (Schedule no)
	 * @return Batch no (Schedule no)
	 */
	public String getBatchNo()
	{
		return getValue(HostSendView.BATCHNO).toString();
	}

	//#CM703997
	/**
	 * Set value to System identification key
	 * @param arg System identification key to be set
	 */
	public void setSystemDiscKey(int arg)
	{
		setValue(SYSTEMDISCKEY, new Integer(arg));
	}

	//#CM703998
	/**
	 * Fetch System identification key
	 * @return System identification key
	 */
	public int getSystemDiscKey()
	{
		return getBigDecimal(HostSendView.SYSTEMDISCKEY).intValue();
	}

	//#CM703999
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM704000
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(HostSendView.REGISTDATE);
	}

	//#CM704001
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM704002
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(HostSendView.LASTUPDATEDATE);
	}


	//#CM704003
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM704004
	//------------------------------------------------------------
	//#CM704005
	// package methods
	//#CM704006
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704007
	//------------------------------------------------------------


	//#CM704008
	//------------------------------------------------------------
	//#CM704009
	// protected methods
	//#CM704010
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704011
	//------------------------------------------------------------


	//#CM704012
	//------------------------------------------------------------
	//#CM704013
	// private methods
	//#CM704014
	//------------------------------------------------------------
	//#CM704015
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
		lst.add(prefix + LOTNO);
		lst.add(prefix + PLANINFORMATION);
		lst.add(prefix + ORDERNO);
		lst.add(prefix + ORDERSEQNO);
		lst.add(prefix + ORDERINGDATE);
		lst.add(prefix + USEBYDATE);
		lst.add(prefix + BATCHNO);
		lst.add(prefix + SYSTEMDISCKEY);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + LASTUPDATEDATE);
	}


	//#CM704016
	//------------------------------------------------------------
	//#CM704017
	// utility methods
	//#CM704018
	//------------------------------------------------------------
	//#CM704019
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: HostSendView.java,v 1.4 2006/11/13 04:31:06 suresh Exp $" ;
	}
}
