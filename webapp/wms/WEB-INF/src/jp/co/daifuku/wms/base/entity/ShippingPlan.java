//#CM706084
//$Id: ShippingPlan.java,v 1.5 2006/11/16 02:15:38 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM706085
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
import jp.co.daifuku.wms.base.entity.ShippingPlan;

//#CM706086
/**
 * Entity class of SHIPPINGPLAN
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:38 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class ShippingPlan
		extends AbstractEntity
{
	//#CM706087
	//------------------------------------------------------------
	//#CM706088
	// class variables (prefix '$')
	//#CM706089
	//------------------------------------------------------------
	//#CM706090
	//	private String	$classVar ;

	//#CM706091
	//------------------------------------------------------------
	//#CM706092
	// fields (upper case only)
	//#CM706093
	//------------------------------------------------------------
	//#CM706094
	/*
	 *  * Table name : SHIPPINGPLAN
	 * Shipping Plan Unique key :      SHIPPINGPLANUKEY    varchar2(16)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Shipping Plan date :            PLANDATE            varchar2(8)
	 * Consignor Code :                CONSIGNORCODE       varchar2(16)
	 * Consignor Name :                CONSIGNORNAME       varchar2(40)
	 * Customer Code :                 CUSTOMERCODE        varchar2(16)
	 * Customer Name :                 CUSTOMERNAME1       varchar2(40)
	 * Shipping Ticket no :            SHIPPINGTICKETNO    varchar2(16)
	 * Shipping Line no :              SHIPPINGLINENO      number
	 * Item Code :                     ITEMCODE            varchar2(16)
	 * Item Name :                     ITEMNAME1           varchar2(40)
	 * Shipping Plan qty :             PLANQTY             number
	 * Shipping Result qty :           RESULTQTY           number
	 * Shipping Shortage qty :         SHORTAGECNT         number
	 * Entering Case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case/Piece flag :               CASEPIECEFLAG       varchar2(1)
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * Ordering Date :                 ORDERINGDATE        varchar2(8)
	 * TC/DC flag :                    TCDCFLAG            varchar2(1)
	 * Supplier code :                 SUPPLIERCODE        varchar2(16)
	 * Supplier name :                 SUPPLIERNAME1       varchar2(40)
	 * Receiving Ticket No :           INSTOCKTICKETNO     varchar2(16)
	 * Receiving Line no :             INSTOCKLINENO       number
	 * Use By Date :                   USEBYDATE           varchar2(8)
	 * Lot No :                        LOTNO               varchar2(20)
	 * Plan detail comments :          PLANINFORMATION     varchar2(40)
	 * Batch No (Schedule No) :        BATCHNO             varchar2(8)
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Worker name :                   WORKERNAME          varchar2(20)
	 * Terminal no :                   TERMINALNO          varchar2(3)
	 * Registration type :             REGISTKIND          varchar2(1)
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM706095
	/**Table name definition*/

	public static final String TABLE_NAME = "DNSHIPPINGPLAN";

	//#CM706096
	/** Column Definition (SHIPPINGPLANUKEY) */

	public static final FieldName SHIPPINGPLANUKEY = new FieldName("SHIPPING_PLAN_UKEY");

	//#CM706097
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM706098
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM706099
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM706100
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM706101
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM706102
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM706103
	/** Column Definition (SHIPPINGTICKETNO) */

	public static final FieldName SHIPPINGTICKETNO = new FieldName("SHIPPING_TICKET_NO");

	//#CM706104
	/** Column Definition (SHIPPINGLINENO) */

	public static final FieldName SHIPPINGLINENO = new FieldName("SHIPPING_LINE_NO");

	//#CM706105
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM706106
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM706107
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM706108
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM706109
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM706110
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM706111
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM706112
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM706113
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM706114
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM706115
	/** Column Definition (ORDERINGDATE) */

	public static final FieldName ORDERINGDATE = new FieldName("ORDERING_DATE");

	//#CM706116
	/** Column Definition (TCDCFLAG) */

	public static final FieldName TCDCFLAG = new FieldName("TCDC_FLAG");

	//#CM706117
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM706118
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM706119
	/** Column Definition (INSTOCKTICKETNO) */

	public static final FieldName INSTOCKTICKETNO = new FieldName("INSTOCK_TICKET_NO");

	//#CM706120
	/** Column Definition (INSTOCKLINENO) */

	public static final FieldName INSTOCKLINENO = new FieldName("INSTOCK_LINE_NO");

	//#CM706121
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM706122
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM706123
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM706124
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM706125
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM706126
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM706127
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM706128
	/** Column Definition (REGISTKIND) */

	public static final FieldName REGISTKIND = new FieldName("REGIST_KIND");

	//#CM706129
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM706130
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM706131
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM706132
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM706133
	//------------------------------------------------------------
	//#CM706134
	// properties (prefix 'p_')
	//#CM706135
	//------------------------------------------------------------
	//#CM706136
	//	private String	p_Name ;


	//#CM706137
	//------------------------------------------------------------
	//#CM706138
	// instance variables (prefix '_')
	//#CM706139
	//------------------------------------------------------------
	//#CM706140
	//	private String	_instanceVar ;

	//#CM706141
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM706142
	//------------------------------------------------------------
	//#CM706143
	// constructors
	//#CM706144
	//------------------------------------------------------------

	//#CM706145
	/**
	 * Prepare class name list and generate instance
	 */
	public ShippingPlan()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM706146
	//------------------------------------------------------------
	//#CM706147
	// accessors
	//#CM706148
	//------------------------------------------------------------

	//#CM706149
	/**
	 * Set value to Shipping Plan Unique key
	 * @param arg Shipping Plan Unique key to be set
	 */
	public void setShippingPlanUkey(String arg)
	{
		setValue(SHIPPINGPLANUKEY, arg);
	}

	//#CM706150
	/**
	 * Fetch Shipping Plan Unique key
	 * @return Shipping Plan Unique key
	 */
	public String getShippingPlanUkey()
	{
		return getValue(ShippingPlan.SHIPPINGPLANUKEY).toString();
	}

	//#CM706151
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

	//#CM706152
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(ShippingPlan.STATUSFLAG).toString();
	}

	//#CM706153
	/**
	 * Set value to Shipping Plan date
	 * @param arg Shipping Plan date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM706154
	/**
	 * Fetch Shipping Plan date
	 * @return Shipping Plan date
	 */
	public String getPlanDate()
	{
		return getValue(ShippingPlan.PLANDATE).toString();
	}

	//#CM706155
	/**
	 * Set value to Consignor Code
	 * @param arg Consignor Code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM706156
	/**
	 * Fetch Consignor Code
	 * @return Consignor Code
	 */
	public String getConsignorCode()
	{
		return getValue(ShippingPlan.CONSIGNORCODE).toString();
	}

	//#CM706157
	/**
	 * Set value to Consignor Name
	 * @param arg Consignor Name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM706158
	/**
	 * Fetch Consignor Name
	 * @return Consignor Name
	 */
	public String getConsignorName()
	{
		return getValue(ShippingPlan.CONSIGNORNAME).toString();
	}

	//#CM706159
	/**
	 * Set value to Customer Code
	 * @param arg Customer Code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM706160
	/**
	 * Fetch Customer Code
	 * @return Customer Code
	 */
	public String getCustomerCode()
	{
		return getValue(ShippingPlan.CUSTOMERCODE).toString();
	}

	//#CM706161
	/**
	 * Set value to Customer Name
	 * @param arg Customer Name to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM706162
	/**
	 * Fetch Customer Name
	 * @return Customer Name
	 */
	public String getCustomerName1()
	{
		return getValue(ShippingPlan.CUSTOMERNAME1).toString();
	}

	//#CM706163
	/**
	 * Set value to Shipping Ticket no
	 * @param arg Shipping Ticket no to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		setValue(SHIPPINGTICKETNO, arg);
	}

	//#CM706164
	/**
	 * Fetch Shipping Ticket no
	 * @return Shipping Ticket no
	 */
	public String getShippingTicketNo()
	{
		return getValue(ShippingPlan.SHIPPINGTICKETNO).toString();
	}

	//#CM706165
	/**
	 * Set value to Shipping Line no
	 * @param arg Shipping Line no to be set
	 */
	public void setShippingLineNo(int arg)
	{
		setValue(SHIPPINGLINENO, new Integer(arg));
	}

	//#CM706166
	/**
	 * Fetch Shipping Line no
	 * @return Shipping Line no
	 */
	public int getShippingLineNo()
	{
		return getBigDecimal(ShippingPlan.SHIPPINGLINENO).intValue();
	}

	//#CM706167
	/**
	 * Set value to Item Code
	 * @param arg Item Code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM706168
	/**
	 * Fetch Item Code
	 * @return Item Code
	 */
	public String getItemCode()
	{
		return getValue(ShippingPlan.ITEMCODE).toString();
	}

	//#CM706169
	/**
	 * Set value to Item Name
	 * @param arg Item Name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM706170
	/**
	 * Fetch Item Name
	 * @return Item Name
	 */
	public String getItemName1()
	{
		return getValue(ShippingPlan.ITEMNAME1).toString();
	}

	//#CM706171
	/**
	 * Set value to Shipping Plan qty
	 * @param arg Shipping Plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM706172
	/**
	 * Fetch Shipping Plan qty
	 * @return Shipping Plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(ShippingPlan.PLANQTY).intValue();
	}

	//#CM706173
	/**
	 * Set value to Shipping Result qty
	 * @param arg Shipping Result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM706174
	/**
	 * Fetch Shipping Result qty
	 * @return Shipping Result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(ShippingPlan.RESULTQTY).intValue();
	}

	//#CM706175
	/**
	 * Set value to Shipping Shortage qty
	 * @param arg Shipping Shortage qty to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM706176
	/**
	 * Fetch Shipping Shortage qty
	 * @return Shipping Shortage qty
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(ShippingPlan.SHORTAGECNT).intValue();
	}

	//#CM706177
	/**
	 * Set value to Entering Case qty
	 * @param arg Entering Case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM706178
	/**
	 * Fetch Entering Case qty
	 * @return Entering Case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(ShippingPlan.ENTERINGQTY).intValue();
	}

	//#CM706179
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM706180
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(ShippingPlan.BUNDLEENTERINGQTY).intValue();
	}

	//#CM706181
	/**
	 * Set value to Case/Piece flag
	 * @param arg Case/Piece flag to be set
	 */
	public void setCasePieceFlag(String arg) throws InvalidStatusException
	{
		if ( ( arg.equals( CASEPIECE_FLAG_NOTHING ) ) ||
			 ( arg.equals( CASEPIECE_FLAG_CASE ) )    ||
			 ( arg.equals( CASEPIECE_FLAG_PIECE ) ) )
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

	//#CM706182
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(ShippingPlan.CASEPIECEFLAG).toString();
	}

	//#CM706183
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM706184
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(ShippingPlan.ITF).toString();
	}

	//#CM706185
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM706186
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(ShippingPlan.BUNDLEITF).toString();
	}

	//#CM706187
	/**
	 * Set value to Ordering Date
	 * @param arg Ordering Date to be set
	 */
	public void setOrderingDate(String arg)
	{
		setValue(ORDERINGDATE, arg);
	}

	//#CM706188
	/**
	 * Fetch Ordering Date
	 * @return Ordering Date
	 */
	public String getOrderingDate()
	{
		return getValue(ShippingPlan.ORDERINGDATE).toString();
	}

	//#CM706189
	/**
	 * Set value to TC/DC flag
	 * @param arg TC/DC flag to be set
	 */
	public void setTcdcFlag(String arg)
	{
		setValue(TCDCFLAG, arg);
	}

	//#CM706190
	/**
	 * Fetch TC/DC flag
	 * @return TC/DC flag
	 */
	public String getTcdcFlag()
	{
		return getValue(ShippingPlan.TCDCFLAG).toString();
	}

	//#CM706191
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM706192
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(ShippingPlan.SUPPLIERCODE).toString();
	}

	//#CM706193
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM706194
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(ShippingPlan.SUPPLIERNAME1).toString();
	}

	//#CM706195
	/**
	 * Set value to Receiving Ticket No
	 * @param arg Receiving Ticket No to be set
	 */
	public void setInstockTicketNo(String arg)
	{
		setValue(INSTOCKTICKETNO, arg);
	}

	//#CM706196
	/**
	 * Fetch Receiving Ticket No
	 * @return Receiving Ticket No
	 */
	public String getInstockTicketNo()
	{
		return getValue(ShippingPlan.INSTOCKTICKETNO).toString();
	}

	//#CM706197
	/**
	 * Set value to Receiving Line no
	 * @param arg Receiving Line no to be set
	 */
	public void setInstockLineNo(int arg)
	{
		setValue(INSTOCKLINENO, new Integer(arg));
	}

	//#CM706198
	/**
	 * Fetch Receiving Line no
	 * @return Receiving Line no
	 */
	public int getInstockLineNo()
	{
		return getBigDecimal(ShippingPlan.INSTOCKLINENO).intValue();
	}

	//#CM706199
	/**
	 * Set value to Use By Date
	 * @param arg Use By Date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM706200
	/**
	 * Fetch Use By Date
	 * @return Use By Date
	 */
	public String getUseByDate()
	{
		return getValue(ShippingPlan.USEBYDATE).toString();
	}

	//#CM706201
	/**
	 * Set value to Lot No
	 * @param arg Lot No to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM706202
	/**
	 * Fetch Lot No
	 * @return Lot No
	 */
	public String getLotNo()
	{
		return getValue(ShippingPlan.LOTNO).toString();
	}

	//#CM706203
	/**
	 * Set value to Plan detail comments
	 * @param arg Plan detail comments to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM706204
	/**
	 * Fetch Plan detail comments
	 * @return Plan detail comments
	 */
	public String getPlanInformation()
	{
		return getValue(ShippingPlan.PLANINFORMATION).toString();
	}

	//#CM706205
	/**
	 * Set value to Batch No (Schedule No)
	 * @param arg Batch No (Schedule No) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM706206
	/**
	 * Fetch Batch No (Schedule No)
	 * @return Batch No (Schedule No)
	 */
	public String getBatchNo()
	{
		return getValue(ShippingPlan.BATCHNO).toString();
	}

	//#CM706207
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM706208
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(ShippingPlan.WORKERCODE).toString();
	}

	//#CM706209
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM706210
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(ShippingPlan.WORKERNAME).toString();
	}

	//#CM706211
	/**
	 * Set value to Terminal no
	 * @param arg Terminal no to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM706212
	/**
	 * Fetch Terminal no
	 * @return Terminal no
	 */
	public String getTerminalNo()
	{
		return getValue(ShippingPlan.TERMINALNO).toString();
	}

	//#CM706213
	/**
	 * Set value to Registration type
	 * @param arg Registration type to be set
	 */
	public void setRegistKind(String arg)
	{
		setValue(REGISTKIND, arg);
	}

	//#CM706214
	/**
	 * Fetch Registration type
	 * @return Registration type
	 */
	public String getRegistKind()
	{
		return getValue(ShippingPlan.REGISTKIND).toString();
	}

	//#CM706215
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM706216
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(ShippingPlan.REGISTDATE);
	}

	//#CM706217
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM706218
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(ShippingPlan.REGISTPNAME).toString();
	}

	//#CM706219
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM706220
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(ShippingPlan.LASTUPDATEDATE);
	}

	//#CM706221
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM706222
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(ShippingPlan.LASTUPDATEPNAME).toString();
	}


	//#CM706223
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM706224
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
	//#CM706225
	// package methods
	//#CM706226
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706227
	//------------------------------------------------------------


	//#CM706228
	//------------------------------------------------------------
	//#CM706229
	// protected methods
	//#CM706230
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706231
	//------------------------------------------------------------


	//#CM706232
	//------------------------------------------------------------
	//#CM706233
	// private methods
	//#CM706234
	//------------------------------------------------------------
	//#CM706235
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + SHIPPINGPLANUKEY);
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
		lst.add(prefix + ORDERINGDATE);
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


	//#CM706236
	//------------------------------------------------------------
	//#CM706237
	// utility methods
	//#CM706238
	//------------------------------------------------------------
	//#CM706239
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: ShippingPlan.java,v 1.5 2006/11/16 02:15:38 suresh Exp $" ;
	}
}
