//#CM704165
//$Id: InstockPlan.java,v 1.5 2006/11/16 02:15:44 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM704166
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
import jp.co.daifuku.wms.base.entity.InstockPlan;

//#CM704167
/**
 * Entity class of INSTOCKPLAN
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:44 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class InstockPlan
		extends AbstractEntity
{
	//#CM704168
	//------------------------------------------------------------
	//#CM704169
	// class variables (prefix '$')
	//#CM704170
	//------------------------------------------------------------
	//#CM704171
	//	private String	$classVar ;

	//#CM704172
	//------------------------------------------------------------
	//#CM704173
	// fields (upper case only)
	//#CM704174
	//------------------------------------------------------------
	//#CM704175
	/*
	 *  * Table name : INSTOCKPLAN
	 * Receiving Plan List key :       INSTOCKPLANUKEY     varchar2(16)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Receiving Plan Date :           PLANDATE            varchar2(8)
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Consignor name :                CONSIGNORNAME       varchar2(40)
	 * Supplier code :                 SUPPLIERCODE        varchar2(16)
	 * Supplier name :                 SUPPLIERNAME1       varchar2(40)
	 * Receiving ticket no :           INSTOCKTICKETNO     varchar2(16)
	 * Receiving line no :             INSTOCKLINENO       number
	 * Item code :                     ITEMCODE            varchar2(16)
	 * Item name :                     ITEMNAME1           varchar2(40)
	 * Receiving plan qty :            PLANQTY             number
	 * Receiving result qty :          RESULTQTY           number
	 * Receiving shortage qty :        SHORTAGECNT         number
	 * Entering case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case/Piece flag :               CASEPIECEFLAG       varchar2(1)
	 * Case-ITF :                      ITF                 varchar2(16)
	 * Bundle-ITF :                    BUNDLEITF           varchar2(16)
	 * Ordering date :                 ORDERINGDATE        varchar2(8)
	 * TC/DC flag :                    TCDCFLAG            varchar2(1)
	 * Customer code :                 CUSTOMERCODE        varchar2(16)
	 * Customer name :                 CUSTOMERNAME1       varchar2(40)
	 * Use By Date :                   USEBYDATE           varchar2(8)
	 * Lot no :                        LOTNO               varchar2(20)
	 * Plan details comment :          PLANINFORMATION     varchar2(40)
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

	//#CM704176
	/**Table name definition*/

	public static final String TABLE_NAME = "DNINSTOCKPLAN";

	//#CM704177
	/** Column Definition (INSTOCKPLANUKEY) */

	public static final FieldName INSTOCKPLANUKEY = new FieldName("INSTOCK_PLAN_UKEY");

	//#CM704178
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM704179
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM704180
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM704181
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM704182
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM704183
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM704184
	/** Column Definition (INSTOCKTICKETNO) */

	public static final FieldName INSTOCKTICKETNO = new FieldName("INSTOCK_TICKET_NO");

	//#CM704185
	/** Column Definition (INSTOCKLINENO) */

	public static final FieldName INSTOCKLINENO = new FieldName("INSTOCK_LINE_NO");

	//#CM704186
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM704187
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM704188
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM704189
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM704190
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM704191
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM704192
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM704193
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM704194
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM704195
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM704196
	/** Column Definition (ORDERINGDATE) */

	public static final FieldName ORDERINGDATE = new FieldName("ORDERING_DATE");

	//#CM704197
	/** Column Definition (TCDCFLAG) */

	public static final FieldName TCDCFLAG = new FieldName("TCDC_FLAG");

	//#CM704198
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM704199
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM704200
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM704201
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM704202
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM704203
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM704204
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM704205
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM704206
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM704207
	/** Column Definition (REGISTKIND) */

	public static final FieldName REGISTKIND = new FieldName("REGIST_KIND");

	//#CM704208
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM704209
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM704210
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM704211
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM704212
	//------------------------------------------------------------
	//#CM704213
	// properties (prefix 'p_')
	//#CM704214
	//------------------------------------------------------------
	//#CM704215
	//	private String	p_Name ;


	//#CM704216
	//------------------------------------------------------------
	//#CM704217
	// instance variables (prefix '_')
	//#CM704218
	//------------------------------------------------------------
	//#CM704219
	//	private String	_instanceVar ;

	//#CM704220
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM704221
	//------------------------------------------------------------
	//#CM704222
	// constructors
	//#CM704223
	//------------------------------------------------------------

	//#CM704224
	/**
	 * Prepare class name list and generate instance
	 */
	public InstockPlan()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM704225
	//------------------------------------------------------------
	//#CM704226
	// accessors
	//#CM704227
	//------------------------------------------------------------

	//#CM704228
	/**
	 * Set value to Receiving Plan List key
	 * @param arg Receiving Plan List key to be set
	 */
	public void setInstockPlanUkey(String arg)
	{
		setValue(INSTOCKPLANUKEY, arg);
	}

	//#CM704229
	/**
	 * Fetch Receiving Plan List key
	 * @return Receiving Plan List key
	 */
	public String getInstockPlanUkey()
	{
		return getValue(InstockPlan.INSTOCKPLANUKEY).toString();
	}

	//#CM704230
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

	//#CM704231
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(InstockPlan.STATUSFLAG).toString();
	}

	//#CM704232
	/**
	 * Set value to Receiving Plan Date
	 * @param arg Receiving Plan Date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM704233
	/**
	 * Fetch Receiving Plan Date
	 * @return Receiving Plan Date
	 */
	public String getPlanDate()
	{
		return getValue(InstockPlan.PLANDATE).toString();
	}

	//#CM704234
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM704235
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(InstockPlan.CONSIGNORCODE).toString();
	}

	//#CM704236
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM704237
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(InstockPlan.CONSIGNORNAME).toString();
	}

	//#CM704238
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM704239
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(InstockPlan.SUPPLIERCODE).toString();
	}

	//#CM704240
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM704241
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(InstockPlan.SUPPLIERNAME1).toString();
	}

	//#CM704242
	/**
	 * Set value to Receiving ticket no
	 * @param arg Receiving ticket no to be set
	 */
	public void setInstockTicketNo(String arg)
	{
		setValue(INSTOCKTICKETNO, arg);
	}

	//#CM704243
	/**
	 * Fetch Receiving ticket no
	 * @return Receiving ticket no
	 */
	public String getInstockTicketNo()
	{
		return getValue(InstockPlan.INSTOCKTICKETNO).toString();
	}

	//#CM704244
	/**
	 * Set value to Receiving line no
	 * @param arg Receiving line no to be set
	 */
	public void setInstockLineNo(int arg)
	{
		setValue(INSTOCKLINENO, new Integer(arg));
	}

	//#CM704245
	/**
	 * Fetch Receiving line no
	 * @return Receiving line no
	 */
	public int getInstockLineNo()
	{
		return getBigDecimal(InstockPlan.INSTOCKLINENO).intValue();
	}

	//#CM704246
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM704247
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(InstockPlan.ITEMCODE).toString();
	}

	//#CM704248
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM704249
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(InstockPlan.ITEMNAME1).toString();
	}

	//#CM704250
	/**
	 * Set value to Receiving plan qty
	 * @param arg Receiving plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM704251
	/**
	 * Fetch Receiving plan qty
	 * @return Receiving plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(InstockPlan.PLANQTY).intValue();
	}

	//#CM704252
	/**
	 * Set value to Receiving result qty
	 * @param arg Receiving result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM704253
	/**
	 * Fetch Receiving result qty
	 * @return Receiving result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(InstockPlan.RESULTQTY).intValue();
	}

	//#CM704254
	/**
	 * Set value to Receiving shortage qty
	 * @param arg Receiving shortage qty to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM704255
	/**
	 * Fetch Receiving shortage qty
	 * @return Receiving shortage qty
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(InstockPlan.SHORTAGECNT).intValue();
	}

	//#CM704256
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM704257
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(InstockPlan.ENTERINGQTY).intValue();
	}

	//#CM704258
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM704259
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(InstockPlan.BUNDLEENTERINGQTY).intValue();
	}

	//#CM704260
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

	//#CM704261
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(InstockPlan.CASEPIECEFLAG).toString();
	}

	//#CM704262
	/**
	 * Set value to Case-ITF
	 * @param arg Case-ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM704263
	/**
	 * Fetch Case-ITF
	 * @return Case-ITF
	 */
	public String getItf()
	{
		return getValue(InstockPlan.ITF).toString();
	}

	//#CM704264
	/**
	 * Set value to Bundle-ITF
	 * @param arg Bundle-ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM704265
	/**
	 * Fetch Bundle-ITF
	 * @return Bundle-ITF
	 */
	public String getBundleItf()
	{
		return getValue(InstockPlan.BUNDLEITF).toString();
	}

	//#CM704266
	/**
	 * Set value to Ordering date
	 * @param arg Ordering date to be set
	 */
	public void setOrderingDate(String arg)
	{
		setValue(ORDERINGDATE, arg);
	}

	//#CM704267
	/**
	 * Fetch Ordering date
	 * @return Ordering date
	 */
	public String getOrderingDate()
	{
		return getValue(InstockPlan.ORDERINGDATE).toString();
	}

	//#CM704268
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
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM704269
	/**
	 * Fetch TC/DC flag
	 * @return TC/DC flag
	 */
	public String getTcdcFlag()
	{
		return getValue(InstockPlan.TCDCFLAG).toString();
	}

	//#CM704270
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM704271
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(InstockPlan.CUSTOMERCODE).toString();
	}

	//#CM704272
	/**
	 * Set value to Customer name
	 * @param arg Customer name to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM704273
	/**
	 * Fetch Customer name
	 * @return Customer name
	 */
	public String getCustomerName1()
	{
		return getValue(InstockPlan.CUSTOMERNAME1).toString();
	}

	//#CM704274
	/**
	 * Set value to Use By Date
	 * @param arg Use By Date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM704275
	/**
	 * Fetch Use By Date
	 * @return Use By Date
	 */
	public String getUseByDate()
	{
		return getValue(InstockPlan.USEBYDATE).toString();
	}

	//#CM704276
	/**
	 * Set value to Lot no
	 * @param arg Lot no to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM704277
	/**
	 * Fetch Lot no
	 * @return Lot no
	 */
	public String getLotNo()
	{
		return getValue(InstockPlan.LOTNO).toString();
	}

	//#CM704278
	/**
	 * Set value to Plan details comment
	 * @param arg Plan details comment to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM704279
	/**
	 * Fetch Plan details comment
	 * @return Plan details comment
	 */
	public String getPlanInformation()
	{
		return getValue(InstockPlan.PLANINFORMATION).toString();
	}

	//#CM704280
	/**
	 * Set value to Batch No (Schedule No)
	 * @param arg Batch No (Schedule No) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM704281
	/**
	 * Fetch Batch No (Schedule No)
	 * @return Batch No (Schedule No)
	 */
	public String getBatchNo()
	{
		return getValue(InstockPlan.BATCHNO).toString();
	}

	//#CM704282
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM704283
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(InstockPlan.WORKERCODE).toString();
	}

	//#CM704284
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM704285
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(InstockPlan.WORKERNAME).toString();
	}

	//#CM704286
	/**
	 * Set value to Terminal no
	 * @param arg Terminal no to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM704287
	/**
	 * Fetch Terminal no
	 * @return Terminal no
	 */
	public String getTerminalNo()
	{
		return getValue(InstockPlan.TERMINALNO).toString();
	}

	//#CM704288
	/**
	 * Set value to Registration type
	 * @param arg Registration type to be set
	 */
	public void setRegistKind(String arg) throws InvalidStatusException
	{
		if ((arg.equals(REGIST_KIND_HOST))
			|| (arg.equals(REGIST_KIND_WMS)))
		{
			setValue(REGISTKIND, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wRegistKind";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM704289
	/**
	 * Fetch Registration type
	 * @return Registration type
	 */
	public String getRegistKind()
	{
		return getValue(InstockPlan.REGISTKIND).toString();
	}

	//#CM704290
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM704291
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(InstockPlan.REGISTDATE);
	}

	//#CM704292
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM704293
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(InstockPlan.REGISTPNAME).toString();
	}

	//#CM704294
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM704295
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(InstockPlan.LASTUPDATEDATE);
	}

	//#CM704296
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM704297
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(InstockPlan.LASTUPDATEPNAME).toString();
	}


	//#CM704298
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM704299
	/**
	 *<BR>
	 *<BR>
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

	}

	/**
	 *<BR>
	 *<BR>
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
	//#CM704300
	// package methods
	//#CM704301
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704302
	//------------------------------------------------------------


	//#CM704303
	//------------------------------------------------------------
	//#CM704304
	// protected methods
	//#CM704305
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704306
	//------------------------------------------------------------


	//#CM704307
	//------------------------------------------------------------
	//#CM704308
	// private methods
	//#CM704309
	//------------------------------------------------------------
	//#CM704310
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + INSTOCKPLANUKEY);
		lst.add(prefix + STATUSFLAG);
		lst.add(prefix + PLANDATE);
		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + CONSIGNORNAME);
		lst.add(prefix + SUPPLIERCODE);
		lst.add(prefix + SUPPLIERNAME1);
		lst.add(prefix + INSTOCKTICKETNO);
		lst.add(prefix + INSTOCKLINENO);
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
		lst.add(prefix + CUSTOMERCODE);
		lst.add(prefix + CUSTOMERNAME1);
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


	//#CM704311
	//------------------------------------------------------------
	//#CM704312
	// utility methods
	//#CM704313
	//------------------------------------------------------------
	//#CM704314
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: InstockPlan.java,v 1.5 2006/11/16 02:15:44 suresh Exp $" ;
	}
}
