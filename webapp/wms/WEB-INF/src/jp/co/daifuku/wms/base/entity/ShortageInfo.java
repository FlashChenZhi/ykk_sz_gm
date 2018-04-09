//#CM706240
//$Id: ShortageInfo.java,v 1.5 2006/11/16 02:15:38 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM706241
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
import jp.co.daifuku.wms.base.entity.ShortageInfo;

//#CM706242
/**
 * Entity class of SHORTAGEINFO
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


public class ShortageInfo
		extends AbstractEntity
{
	//#CM706243
	//------------------------------------------------------------
	//#CM706244
	// class variables (prefix '$')
	//#CM706245
	//------------------------------------------------------------
	//#CM706246
	//	private String	$classVar ;

	//#CM706247
	//------------------------------------------------------------
	//#CM706248
	// fields (upper case only)
	//#CM706249
	//------------------------------------------------------------
	//#CM706250
	/*
	 *  * Table name : SHORTAGEINFO
	 * Plan unique key :               PLANUKEY            varchar2(16)
	 * Work type :                     JOBTYPE             varchar2(2)
	 * Plan date :                     PLANDATE            varchar2(8)
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Consignor name :                CONSIGNORNAME       varchar2(40)
	 * Customer code :                 CUSTOMERCODE        varchar2(16)
	 * Customer name :                 CUSTOMERNAME1       varchar2(40)
	 * Shipping ticket no :            SHIPPINGTICKETNO    varchar2(16)
	 * Shipping line no :              SHIPPINGLINENO      number
	 * Item code :                     ITEMCODE            varchar2(16)
	 * Item name :                     ITEMNAME1           varchar2(40)
	 * Plan qty :                      PLANQTY             number
	 * Enabled qty :                   ENABLEQTY           number
	 * Shortage count :                SHORTAGECNT         number
	 * Entering case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case/Piece flag :               CASEPIECEFLAG       varchar2(1)
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * Piece retrieval location :      PIECELOCATION       varchar2(16)
	 * Case retrieval location :       CASELOCATION        varchar2(16)
	 * Piece order no :                PIECEORDERNO        varchar2(16)
	 * Case order no :                 CASEORDERNO         varchar2(16)
	 * Use by date :                   USEBYDATE           varchar2(8)
	 * Lot no :                        LOTNO               varchar2(20)
	 * Plan details comment :          PLANINFORMATION     varchar2(40)
	 * Batch No (Schedule no) :        BATCHNO             varchar2(8)
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Worker name :                   WORKERNAME          varchar2(20)
	 * Terminal no :                   TERMINALNO          varchar2(3)
	 * Registration type :             REGISTKIND          varchar2(1)
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM706251
	/**Table name definition*/

	public static final String TABLE_NAME = "DNSHORTAGEINFO";

	//#CM706252
	/** Column Definition (PLANUKEY) */

	public static final FieldName PLANUKEY = new FieldName("PLAN_UKEY");

	//#CM706253
	/** Column Definition (JOBTYPE) */

	public static final FieldName JOBTYPE = new FieldName("JOB_TYPE");

	//#CM706254
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM706255
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM706256
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM706257
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM706258
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM706259
	/** Column Definition (SHIPPINGTICKETNO) */

	public static final FieldName SHIPPINGTICKETNO = new FieldName("SHIPPING_TICKET_NO");

	//#CM706260
	/** Column Definition (SHIPPINGLINENO) */

	public static final FieldName SHIPPINGLINENO = new FieldName("SHIPPING_LINE_NO");

	//#CM706261
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM706262
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM706263
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM706264
	/** Column Definition (ENABLEQTY) */

	public static final FieldName ENABLEQTY = new FieldName("ENABLE_QTY");

	//#CM706265
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM706266
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM706267
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM706268
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM706269
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM706270
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM706271
	/** Column Definition (PIECELOCATION) */

	public static final FieldName PIECELOCATION = new FieldName("PIECE_LOCATION");

	//#CM706272
	/** Column Definition (CASELOCATION) */

	public static final FieldName CASELOCATION = new FieldName("CASE_LOCATION");

	//#CM706273
	/** Column Definition (PIECEORDERNO) */

	public static final FieldName PIECEORDERNO = new FieldName("PIECE_ORDER_NO");

	//#CM706274
	/** Column Definition (CASEORDERNO) */

	public static final FieldName CASEORDERNO = new FieldName("CASE_ORDER_NO");

	//#CM706275
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM706276
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM706277
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM706278
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM706279
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM706280
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM706281
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM706282
	/** Column Definition (REGISTKIND) */

	public static final FieldName REGISTKIND = new FieldName("REGIST_KIND");

	//#CM706283
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM706284
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM706285
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM706286
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM706287
	//------------------------------------------------------------
	//#CM706288
	// properties (prefix 'p_')
	//#CM706289
	//------------------------------------------------------------
	//#CM706290
	//	private String	p_Name ;


	//#CM706291
	//------------------------------------------------------------
	//#CM706292
	// instance variables (prefix '_')
	//#CM706293
	//------------------------------------------------------------
	//#CM706294
	//	private String	_instanceVar ;

	//#CM706295
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM706296
	//------------------------------------------------------------
	//#CM706297
	// constructors
	//#CM706298
	//------------------------------------------------------------

	//#CM706299
	/**
	 * Prepare class name list and generate instance
	 */
	public ShortageInfo()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM706300
	//------------------------------------------------------------
	//#CM706301
	// accessors
	//#CM706302
	//------------------------------------------------------------

	//#CM706303
	/**
	 * Set value to Plan unique key
	 * @param arg Plan unique key to be set
	 */
	public void setPlanUkey(String arg)
	{
		setValue(PLANUKEY, arg);
	}

	//#CM706304
	/**
	 * Fetch Plan unique key
	 * @return Plan unique key
	 */
	public String getPlanUkey()
	{
		return getValue(ShortageInfo.PLANUKEY).toString();
	}

	//#CM706305
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
			|| (arg.equals(JOB_TYPE_SHIPINSPECTION)))
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

	//#CM706306
	/**
	 * Fetch Work type
	 * @return Work type
	 */
	public String getJobType()
	{
		return getValue(ShortageInfo.JOBTYPE).toString();
	}

	//#CM706307
	/**
	 * Set value to Plan date
	 * @param arg Plan date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM706308
	/**
	 * Fetch Plan date
	 * @return Plan date
	 */
	public String getPlanDate()
	{
		return getValue(ShortageInfo.PLANDATE).toString();
	}

	//#CM706309
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM706310
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(ShortageInfo.CONSIGNORCODE).toString();
	}

	//#CM706311
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM706312
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(ShortageInfo.CONSIGNORNAME).toString();
	}

	//#CM706313
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM706314
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(ShortageInfo.CUSTOMERCODE).toString();
	}

	//#CM706315
	/**
	 * Set value to Customer name
	 * @param arg Customer name to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM706316
	/**
	 * Fetch Customer name
	 * @return Customer name
	 */
	public String getCustomerName1()
	{
		return getValue(ShortageInfo.CUSTOMERNAME1).toString();
	}

	//#CM706317
	/**
	 * Set value to Shipping ticket no
	 * @param arg Shipping ticket no to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		setValue(SHIPPINGTICKETNO, arg);
	}

	//#CM706318
	/**
	 * Fetch Shipping ticket no
	 * @return Shipping ticket no
	 */
	public String getShippingTicketNo()
	{
		return getValue(ShortageInfo.SHIPPINGTICKETNO).toString();
	}

	//#CM706319
	/**
	 * Set value to Shipping line no
	 * @param arg Shipping line no to be set
	 */
	public void setShippingLineNo(int arg)
	{
		setValue(SHIPPINGLINENO, new Integer(arg));
	}

	//#CM706320
	/**
	 * Fetch Shipping line no
	 * @return Shipping line no
	 */
	public int getShippingLineNo()
	{
		return getBigDecimal(ShortageInfo.SHIPPINGLINENO).intValue();
	}

	//#CM706321
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM706322
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(ShortageInfo.ITEMCODE).toString();
	}

	//#CM706323
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM706324
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(ShortageInfo.ITEMNAME1).toString();
	}

	//#CM706325
	/**
	 * Set value to Plan qty
	 * @param arg Plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM706326
	/**
	 * Fetch Plan qty
	 * @return Plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(ShortageInfo.PLANQTY).intValue();
	}

	//#CM706327
	/**
	 * Set value to Enabled qty
	 * @param arg Enabled qty to be set
	 */
	public void setEnableQty(int arg)
	{
		setValue(ENABLEQTY, new Integer(arg));
	}

	//#CM706328
	/**
	 * Fetch Enabled qty
	 * @return Enabled qty
	 */
	public int getEnableQty()
	{
		return getBigDecimal(ShortageInfo.ENABLEQTY).intValue();
	}

	//#CM706329
	/**
	 * Set value to Shortage count
	 * @param arg Shortage count to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM706330
	/**
	 * Fetch Shortage count
	 * @return Shortage count
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(ShortageInfo.SHORTAGECNT).intValue();
	}

	//#CM706331
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM706332
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(ShortageInfo.ENTERINGQTY).intValue();
	}

	//#CM706333
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM706334
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(ShortageInfo.BUNDLEENTERINGQTY).intValue();
	}

	//#CM706335
	/**
	 * Set value to Case/Piece flag
	 * @param arg Case/Piece flag to be set
	 */
	public void setCasePieceFlag(String arg) throws InvalidStatusException
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
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}

	//#CM706336
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(ShortageInfo.CASEPIECEFLAG).toString();
	}

	//#CM706337
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM706338
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(ShortageInfo.ITF).toString();
	}

	//#CM706339
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM706340
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(ShortageInfo.BUNDLEITF).toString();
	}

	//#CM706341
	/**
	 * Set value to Piece retrieval location
	 * @param arg Piece retrieval location to be set
	 */
	public void setPieceLocation(String arg)
	{
		setValue(PIECELOCATION, arg);
	}

	//#CM706342
	/**
	 * Fetch Piece retrieval location
	 * @return Piece retrieval location
	 */
	public String getPieceLocation()
	{
		return getValue(ShortageInfo.PIECELOCATION).toString();
	}

	//#CM706343
	/**
	 * Set value to Case retrieval location
	 * @param arg Case retrieval location to be set
	 */
	public void setCaseLocation(String arg)
	{
		setValue(CASELOCATION, arg);
	}

	//#CM706344
	/**
	 * Fetch Case retrieval location
	 * @return Case retrieval location
	 */
	public String getCaseLocation()
	{
		return getValue(ShortageInfo.CASELOCATION).toString();
	}

	//#CM706345
	/**
	 * Set value to Piece order no
	 * @param arg Piece order no to be set
	 */
	public void setPieceOrderNo(String arg)
	{
		setValue(PIECEORDERNO, arg);
	}

	//#CM706346
	/**
	 * Fetch Piece order no
	 * @return Piece order no
	 */
	public String getPieceOrderNo()
	{
		return getValue(ShortageInfo.PIECEORDERNO).toString();
	}

	//#CM706347
	/**
	 * Set value to Case order no
	 * @param arg Case order no to be set
	 */
	public void setCaseOrderNo(String arg)
	{
		setValue(CASEORDERNO, arg);
	}

	//#CM706348
	/**
	 * Fetch Case order no
	 * @return Case order no
	 */
	public String getCaseOrderNo()
	{
		return getValue(ShortageInfo.CASEORDERNO).toString();
	}

	//#CM706349
	/**
	 * Set value to Use by date
	 * @param arg Use by date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM706350
	/**
	 * Fetch Use by date
	 * @return Use by date
	 */
	public String getUseByDate()
	{
		return getValue(ShortageInfo.USEBYDATE).toString();
	}

	//#CM706351
	/**
	 * Set value to Lot no
	 * @param arg Lot no to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM706352
	/**
	 * Fetch Lot no
	 * @return Lot no
	 */
	public String getLotNo()
	{
		return getValue(ShortageInfo.LOTNO).toString();
	}

	//#CM706353
	/**
	 * Set value to Plan details comment
	 * @param arg Plan details comment to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM706354
	/**
	 * Fetch Plan details comment
	 * @return Plan details comment
	 */
	public String getPlanInformation()
	{
		return getValue(ShortageInfo.PLANINFORMATION).toString();
	}

	//#CM706355
	/**
	 * Set value to Batch No (Schedule no)
	 * @param arg Batch No (Schedule no) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM706356
	/**
	 * Fetch Batch No (Schedule no)
	 * @return Batch No (Schedule no)
	 */
	public String getBatchNo()
	{
		return getValue(ShortageInfo.BATCHNO).toString();
	}

	//#CM706357
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM706358
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(ShortageInfo.WORKERCODE).toString();
	}

	//#CM706359
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM706360
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(ShortageInfo.WORKERNAME).toString();
	}

	//#CM706361
	/**
	 * Set value to Terminal no
	 * @param arg Terminal no to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM706362
	/**
	 * Fetch Terminal no
	 * @return Terminal no
	 */
	public String getTerminalNo()
	{
		return getValue(ShortageInfo.TERMINALNO).toString();
	}

	//#CM706363
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

	//#CM706364
	/**
	 * Fetch Registration type
	 * @return Registration type
	 */
	public String getRegistKind()
	{
		return getValue(ShortageInfo.REGISTKIND).toString();
	}

	//#CM706365
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM706366
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(ShortageInfo.REGISTDATE);
	}

	//#CM706367
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM706368
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(ShortageInfo.REGISTPNAME).toString();
	}

	//#CM706369
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM706370
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(ShortageInfo.LASTUPDATEDATE);
	}

	//#CM706371
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM706372
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(ShortageInfo.LASTUPDATEPNAME).toString();
	}


	//#CM706373
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM706374
	/**
	 * <BR>
	 * <BR>
	 */
	public void setInitCreateColumn()
	{
		setValue(LASTUPDATEDATE, "");
		setValue(REGISTDATE, "");

		setValue(PLANQTY, new Integer(0));
		setValue(SHORTAGECNT, new Integer(0));
		setValue(ENABLEQTY, new Integer(0));
		setValue(ENTERINGQTY, new Integer(0));
		setValue(BUNDLEENTERINGQTY, new Integer(0));
		setValue(SHIPPINGLINENO, new Integer(0));

	}

	/**
	 * <BR>
	 * <BR>
	 * @return ?????????
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
	//#CM706375
	// package methods
	//#CM706376
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706377
	//------------------------------------------------------------


	//#CM706378
	//------------------------------------------------------------
	//#CM706379
	// protected methods
	//#CM706380
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM706381
	//------------------------------------------------------------


	//#CM706382
	//------------------------------------------------------------
	//#CM706383
	// private methods
	//#CM706384
	//------------------------------------------------------------
	//#CM706385
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + PLANUKEY);
		lst.add(prefix + JOBTYPE);
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
		lst.add(prefix + ENABLEQTY);
		lst.add(prefix + SHORTAGECNT);
		lst.add(prefix + ENTERINGQTY);
		lst.add(prefix + BUNDLEENTERINGQTY);
		lst.add(prefix + CASEPIECEFLAG);
		lst.add(prefix + ITF);
		lst.add(prefix + BUNDLEITF);
		lst.add(prefix + PIECELOCATION);
		lst.add(prefix + CASELOCATION);
		lst.add(prefix + PIECEORDERNO);
		lst.add(prefix + CASEORDERNO);
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


	//#CM706386
	//------------------------------------------------------------
	//#CM706387
	// utility methods
	//#CM706388
	//------------------------------------------------------------
	//#CM706389
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: ShortageInfo.java,v 1.5 2006/11/16 02:15:38 suresh Exp $" ;
	}
}
