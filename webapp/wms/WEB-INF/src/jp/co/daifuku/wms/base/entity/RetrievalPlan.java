//#CM705720
//$Id: RetrievalPlan.java,v 1.5 2006/11/16 02:15:39 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM705721
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
import jp.co.daifuku.wms.base.entity.RetrievalPlan;

//#CM705722
/**
 * Entity class of RETRIEVALPLAN
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:39 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class RetrievalPlan
		extends AbstractEntity
{
	//#CM705723
	//------------------------------------------------------------
	//#CM705724
	// class variables (prefix '$')
	//#CM705725
	//------------------------------------------------------------
	//#CM705726
	//	private String	$classVar ;
//#CM705727
/**
	 * Schedule Processing flag (Unprocessed)
	 */
	public static final String SCH_FLAG_UNSTART = "0" ;

	//#CM705728
	/**
	 * Schedule Processing flag (Case processed)
	 */
	public static final String SCH_FLAG_CASE_COMPLETION = "1" ;

	//#CM705729
	/**
	 * Schedule Processing flag (Piece processed)
	 */
	public static final String SCH_FLAG_PIECE_COMPLETION = "2" ;

	//#CM705730
	/**
	 * Schedule Processing flag (Processed)
	 */
	public static final String SCH_FLAG_COMPLETION = "3" ;

	//#CM705731
	//------------------------------------------------------------
	//#CM705732
	// fields (upper case only)
	//#CM705733
	//------------------------------------------------------------
	//#CM705734
	/*
	 *  * Table name : RETRIEVALPLAN
	 * Retrieval Plan Unique key :     RETRIEVALPLANUKEY   varchar2(16)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Retrieval Plan Date :           PLANDATE            varchar2(8)
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Consignor name :                CONSIGNORNAME       varchar2(40)
	 * Customer code :                 CUSTOMERCODE        varchar2(16)
	 * Customer name1 :                CUSTOMERNAME1       varchar2(40)
	 * Shipping ticket no :            SHIPPINGTICKETNO    varchar2(16)
	 * Shipping line no :              SHIPPINGLINENO      number
	 * Item code :                     ITEMCODE            varchar2(16)
	 * Item name :                     ITEMNAME1           varchar2(40)
	 * Plan qty :                      PLANQTY             number
	 * Result qty :                    RESULTQTY           number
	 * Shortage qty :                  SHORTAGECNT         number
	 * Entering case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case/Piece flag :               CASEPIECEFLAG       varchar2(1)
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * Piece Retrieval Area :          PIECEAREA           varchar2(16)
	 * Case Retrieval Area :           CASEAREA            varchar2(16)
	 * Piece Retrieval Zone :          PIECEZONE           varchar2(3)
	 * Case Retrieval Zone :           CASEZONE            varchar2(3)
	 * Piece Retrieval Location :      PIECELOCATION       varchar2(16)
	 * Case Retrieval Location :       CASELOCATION        varchar2(16)
	 * Piece Order No :                PIECEORDERNO        varchar2(16)
	 * Case Order No :                 CASEORDERNO         varchar2(16)
	 * Use By Date :                   USEBYDATE           varchar2(8)
	 * Lot :                           LOTNO               varchar2(20)
	 * Plan :                          PLANINFORMATION     varchar2(40)
	 * Batch No (Schedule No) :        BATCHNO             varchar2(8)
	 * Host collection batch key :     HOSTCOLLECTBATCHNO  varchar2(32)
	 * Schedule flag :                 SCHFLAG             varchar2(1)
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Worker name :                   WORKERNAME          varchar2(20)
	 * Terminal no :                   TERMINALNO          varchar2(3)
	 * Registration type :             REGISTKIND          varchar2(1)
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM705735
	/**Table name definition*/

	public static final String TABLE_NAME = "DNRETRIEVALPLAN";

	//#CM705736
	/** Column Definition (RETRIEVALPLANUKEY) */

	public static final FieldName RETRIEVALPLANUKEY = new FieldName("RETRIEVAL_PLAN_UKEY");

	//#CM705737
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM705738
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM705739
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM705740
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM705741
	/** Column Definition (CUSTOMERCODE) */

	public static final FieldName CUSTOMERCODE = new FieldName("CUSTOMER_CODE");

	//#CM705742
	/** Column Definition (CUSTOMERNAME1) */

	public static final FieldName CUSTOMERNAME1 = new FieldName("CUSTOMER_NAME1");

	//#CM705743
	/** Column Definition (SHIPPINGTICKETNO) */

	public static final FieldName SHIPPINGTICKETNO = new FieldName("SHIPPING_TICKET_NO");

	//#CM705744
	/** Column Definition (SHIPPINGLINENO) */

	public static final FieldName SHIPPINGLINENO = new FieldName("SHIPPING_LINE_NO");

	//#CM705745
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM705746
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM705747
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM705748
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM705749
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM705750
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM705751
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM705752
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM705753
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM705754
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM705755
	/** Column Definition (PIECEAREA) */

	public static final FieldName PIECEAREA = new FieldName("PIECE_AREA");

	//#CM705756
	/** Column Definition (CASEAREA) */

	public static final FieldName CASEAREA = new FieldName("CASE_AREA");

	//#CM705757
	/** Column Definition (PIECEZONE) */

	public static final FieldName PIECEZONE = new FieldName("PIECE_ZONE");

	//#CM705758
	/** Column Definition (CASEZONE) */

	public static final FieldName CASEZONE = new FieldName("CASE_ZONE");

	//#CM705759
	/** Column Definition (PIECELOCATION) */

	public static final FieldName PIECELOCATION = new FieldName("PIECE_LOCATION");

	//#CM705760
	/** Column Definition (CASELOCATION) */

	public static final FieldName CASELOCATION = new FieldName("CASE_LOCATION");

	//#CM705761
	/** Column Definition (PIECEORDERNO) */

	public static final FieldName PIECEORDERNO = new FieldName("PIECE_ORDER_NO");

	//#CM705762
	/** Column Definition (CASEORDERNO) */

	public static final FieldName CASEORDERNO = new FieldName("CASE_ORDER_NO");

	//#CM705763
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM705764
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM705765
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM705766
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM705767
	/** Column Definition (HOSTCOLLECTBATCHNO) */

	public static final FieldName HOSTCOLLECTBATCHNO = new FieldName("HOST_COLLECT_BATCHNO");

	//#CM705768
	/** Column Definition (SCHFLAG) */

	public static final FieldName SCHFLAG = new FieldName("SCH_FLAG");

	//#CM705769
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM705770
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM705771
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM705772
	/** Column Definition (REGISTKIND) */

	public static final FieldName REGISTKIND = new FieldName("REGIST_KIND");

	//#CM705773
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM705774
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM705775
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM705776
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM705777
	//------------------------------------------------------------
	//#CM705778
	// properties (prefix 'p_')
	//#CM705779
	//------------------------------------------------------------
	//#CM705780
	//	private String	p_Name ;


	//#CM705781
	//------------------------------------------------------------
	//#CM705782
	// instance variables (prefix '_')
	//#CM705783
	//------------------------------------------------------------
	//#CM705784
	//	private String	_instanceVar ;

	//#CM705785
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM705786
	//------------------------------------------------------------
	//#CM705787
	// constructors
	//#CM705788
	//------------------------------------------------------------

	//#CM705789
	/**
	 * Prepare class name list and generate instance
	 */
	public RetrievalPlan()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM705790
	//------------------------------------------------------------
	//#CM705791
	// accessors
	//#CM705792
	//------------------------------------------------------------

	//#CM705793
	/**
	 * Set value to Retrieval Plan Unique key
	 * @param arg Retrieval Plan Unique key to be set
	 */
	public void setRetrievalPlanUkey(String arg)
	{
		setValue(RETRIEVALPLANUKEY, arg);
	}

	//#CM705794
	/**
	 * Fetch Retrieval Plan Unique key
	 * @return Retrieval Plan Unique key
	 */
	public String getRetrievalPlanUkey()
	{
		return getValue(RetrievalPlan.RETRIEVALPLANUKEY).toString();
	}

	//#CM705795
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

	//#CM705796
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(RetrievalPlan.STATUSFLAG).toString();
	}

	//#CM705797
	/**
	 * Set value to Retrieval Plan Date
	 * @param arg Retrieval Plan Date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM705798
	/**
	 * Fetch Retrieval Plan Date
	 * @return Retrieval Plan Date
	 */
	public String getPlanDate()
	{
		return getValue(RetrievalPlan.PLANDATE).toString();
	}

	//#CM705799
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM705800
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(RetrievalPlan.CONSIGNORCODE).toString();
	}

	//#CM705801
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM705802
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(RetrievalPlan.CONSIGNORNAME).toString();
	}

	//#CM705803
	/**
	 * Set value to Customer code
	 * @param arg Customer code to be set
	 */
	public void setCustomerCode(String arg)
	{
		setValue(CUSTOMERCODE, arg);
	}

	//#CM705804
	/**
	 * Fetch Customer code
	 * @return Customer code
	 */
	public String getCustomerCode()
	{
		return getValue(RetrievalPlan.CUSTOMERCODE).toString();
	}

	//#CM705805
	/**
	 * Set value to Customer name1
	 * @param arg Customer name1 to be set
	 */
	public void setCustomerName1(String arg)
	{
		setValue(CUSTOMERNAME1, arg);
	}

	//#CM705806
	/**
	 * Fetch Customer name1
	 * @return Customer name1
	 */
	public String getCustomerName1()
	{
		return getValue(RetrievalPlan.CUSTOMERNAME1).toString();
	}

	//#CM705807
	/**
	 * Set value to Shipping ticket no
	 * @param arg Shipping ticket no to be set
	 */
	public void setShippingTicketNo(String arg)
	{
		setValue(SHIPPINGTICKETNO, arg);
	}

	//#CM705808
	/**
	 * Fetch Shipping ticket no
	 * @return Shipping ticket no
	 */
	public String getShippingTicketNo()
	{
		return getValue(RetrievalPlan.SHIPPINGTICKETNO).toString();
	}

	//#CM705809
	/**
	 * Set value to Shipping line no
	 * @param arg Shipping line no to be set
	 */
	public void setShippingLineNo(int arg)
	{
		setValue(SHIPPINGLINENO, new Integer(arg));
	}

	//#CM705810
	/**
	 * Fetch Shipping line no
	 * @return Shipping line no
	 */
	public int getShippingLineNo()
	{
		return getBigDecimal(RetrievalPlan.SHIPPINGLINENO).intValue();
	}

	//#CM705811
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM705812
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(RetrievalPlan.ITEMCODE).toString();
	}

	//#CM705813
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM705814
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(RetrievalPlan.ITEMNAME1).toString();
	}

	//#CM705815
	/**
	 * Set value to Plan qty
	 * @param arg Plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM705816
	/**
	 * Fetch Plan qty
	 * @return Plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(RetrievalPlan.PLANQTY).intValue();
	}

	//#CM705817
	/**
	 * Set value to Result qty
	 * @param arg Result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM705818
	/**
	 * Fetch Result qty
	 * @return Result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(RetrievalPlan.RESULTQTY).intValue();
	}

	//#CM705819
	/**
	 * Set value to Shortage qty
	 * @param arg Shortage qty to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM705820
	/**
	 * Fetch Shortage qty
	 * @return Shortage qty
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(RetrievalPlan.SHORTAGECNT).intValue();
	}

	//#CM705821
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM705822
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(RetrievalPlan.ENTERINGQTY).intValue();
	}

	//#CM705823
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM705824
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(RetrievalPlan.BUNDLEENTERINGQTY).intValue();
	}

	//#CM705825
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

	//#CM705826
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(RetrievalPlan.CASEPIECEFLAG).toString();
	}

	//#CM705827
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM705828
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(RetrievalPlan.ITF).toString();
	}

	//#CM705829
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM705830
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(RetrievalPlan.BUNDLEITF).toString();
	}

	//#CM705831
	/**
	 * Set value to Piece Retrieval Area
	 * @param arg Piece Retrieval Area to be set
	 */
	public void setPieceArea(String arg)
	{
		setValue(PIECEAREA, arg);
	}

	//#CM705832
	/**
	 * Fetch Piece Retrieval Area
	 * @return Piece Retrieval Area
	 */
	public String getPieceArea()
	{
		return getValue(RetrievalPlan.PIECEAREA).toString();
	}

	//#CM705833
	/**
	 * Set value to Case Retrieval Area
	 * @param arg Case Retrieval Area to be set
	 */
	public void setCaseArea(String arg)
	{
		setValue(CASEAREA, arg);
	}

	//#CM705834
	/**
	 * Fetch Case Retrieval Area
	 * @return Case Retrieval Area
	 */
	public String getCaseArea()
	{
		return getValue(RetrievalPlan.CASEAREA).toString();
	}

	//#CM705835
	/**
	 * Set value to Piece Retrieval Zone
	 * @param arg Piece Retrieval Zone to be set
	 */
	public void setPieceZone(String arg)
	{
		setValue(PIECEZONE, arg);
	}

	//#CM705836
	/**
	 * Fetch Piece Retrieval Zone
	 * @return Piece Retrieval Zone
	 */
	public String getPieceZone()
	{
		return getValue(RetrievalPlan.PIECEZONE).toString();
	}

	//#CM705837
	/**
	 * Set value to Case Retrieval Zone
	 * @param arg Case Retrieval Zone to be set
	 */
	public void setCaseZone(String arg)
	{
		setValue(CASEZONE, arg);
	}

	//#CM705838
	/**
	 * Fetch Case Retrieval Zone
	 * @return Case Retrieval Zone
	 */
	public String getCaseZone()
	{
		return getValue(RetrievalPlan.CASEZONE).toString();
	}

	//#CM705839
	/**
	 * Set value to Piece Retrieval Location
	 * @param arg Piece Retrieval Location to be set
	 */
	public void setPieceLocation(String arg)
	{
		setValue(PIECELOCATION, arg);
	}

	//#CM705840
	/**
	 * Fetch Piece Retrieval Location
	 * @return Piece Retrieval Location
	 */
	public String getPieceLocation()
	{
		return getValue(RetrievalPlan.PIECELOCATION).toString();
	}

	//#CM705841
	/**
	 * Set value to Case Retrieval Location
	 * @param arg Case Retrieval Location to be set
	 */
	public void setCaseLocation(String arg)
	{
		setValue(CASELOCATION, arg);
	}

	//#CM705842
	/**
	 * Fetch Case Retrieval Location
	 * @return Case Retrieval Location
	 */
	public String getCaseLocation()
	{
		return getValue(RetrievalPlan.CASELOCATION).toString();
	}

	//#CM705843
	/**
	 * Set value to Piece Order No
	 * @param arg Piece Order No to be set
	 */
	public void setPieceOrderNo(String arg)
	{
		setValue(PIECEORDERNO, arg);
	}

	//#CM705844
	/**
	 * Fetch Piece Order No
	 * @return Piece Order No
	 */
	public String getPieceOrderNo()
	{
		return getValue(RetrievalPlan.PIECEORDERNO).toString();
	}

	//#CM705845
	/**
	 * Set value to Case Order No
	 * @param arg Case Order No to be set
	 */
	public void setCaseOrderNo(String arg)
	{
		setValue(CASEORDERNO, arg);
	}

	//#CM705846
	/**
	 * Fetch Case Order No
	 * @return Case Order No
	 */
	public String getCaseOrderNo()
	{
		return getValue(RetrievalPlan.CASEORDERNO).toString();
	}

	//#CM705847
	/**
	 * Set value to Use By Date
	 * @param arg Use By Date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM705848
	/**
	 * Fetch Use By Date
	 * @return Use By Date
	 */
	public String getUseByDate()
	{
		return getValue(RetrievalPlan.USEBYDATE).toString();
	}

	//#CM705849
	/**
	 * Set value to Lot
	 * @param arg Lot to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM705850
	/**
	 * Fetch Lot
	 * @return Lot
	 */
	public String getLotNo()
	{
		return getValue(RetrievalPlan.LOTNO).toString();
	}

	//#CM705851
	/**
	 * Set value to Plan
	 * @param arg Plan to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM705852
	/**
	 * Fetch Plan
	 * @return Plan
	 */
	public String getPlanInformation()
	{
		return getValue(RetrievalPlan.PLANINFORMATION).toString();
	}

	//#CM705853
	/**
	 * Set value to Batch No (Schedule No)
	 * @param arg Batch No (Schedule No) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM705854
	/**
	 * Fetch Batch No (Schedule No)
	 * @return Batch No (Schedule No)
	 */
	public String getBatchNo()
	{
		return getValue(RetrievalPlan.BATCHNO).toString();
	}

	//#CM705855
	/**
	 * Set value to Host collection batch key
	 * @param arg Host collection batch key to be set
	 */
	public void setHostCollectBatchno(String arg)
	{
		setValue(HOSTCOLLECTBATCHNO, arg);
	}

	//#CM705856
	/**
	 * Fetch Host collection batch key
	 * @return Host collection batch key
	 */
	public String getHostCollectBatchno()
	{
		return getValue(RetrievalPlan.HOSTCOLLECTBATCHNO).toString();
	}

	//#CM705857
	/**
	 * Set value to Schedule flag
	 * @param arg Schedule flag to be set
	 */
	public void setSchFlag(String arg) throws InvalidStatusException
	{
		if ((arg.equals(SCH_FLAG_UNSTART))
			|| (arg.equals(SCH_FLAG_CASE_COMPLETION ))
			|| (arg.equals(SCH_FLAG_PIECE_COMPLETION))
			|| (arg.equals(SCH_FLAG_COMPLETION)))
		{
			setValue(SCHFLAG, arg);
		}
		else
		{
			Object[] tObj = new Object[3];
			tObj[0] = this.getClass().getName();
			tObj[1] = "wSchFlag";
			tObj[2] = arg;
			String classname = (String) tObj[0];
			RmiMsgLogClient.write(6006009, LogMessage.F_ERROR, classname, tObj);
			throw (
				new InvalidStatusException(
					"6006009" + wDelim + tObj[0] + wDelim + tObj[1] + wDelim + tObj[2]));
		}
	}


	//#CM705858
	/**
	 * Fetch Schedule flag
	 * @return Schedule flag
	 */
	public String getSchFlag()
	{
		return getValue(RetrievalPlan.SCHFLAG).toString();
	}

	//#CM705859
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM705860
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(RetrievalPlan.WORKERCODE).toString();
	}

	//#CM705861
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM705862
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(RetrievalPlan.WORKERNAME).toString();
	}

	//#CM705863
	/**
	 * Set value to Terminal no
	 * @param arg Terminal no to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM705864
	/**
	 * Fetch Terminal no
	 * @return Terminal no
	 */
	public String getTerminalNo()
	{
		return getValue(RetrievalPlan.TERMINALNO).toString();
	}

	//#CM705865
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

	//#CM705866
	/**
	 * Fetch Registration type
	 * @return Registration type
	 */
	public String getRegistKind()
	{
		return getValue(RetrievalPlan.REGISTKIND).toString();
	}

	//#CM705867
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM705868
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(RetrievalPlan.REGISTDATE);
	}

	//#CM705869
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM705870
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(RetrievalPlan.REGISTPNAME).toString();
	}

	//#CM705871
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM705872
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(RetrievalPlan.LASTUPDATEDATE);
	}

	//#CM705873
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM705874
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(RetrievalPlan.LASTUPDATEPNAME).toString();
	}


	//#CM705875
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM705876
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
	//#CM705877
	// package methods
	//#CM705878
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705879
	//------------------------------------------------------------


	//#CM705880
	//------------------------------------------------------------
	//#CM705881
	// protected methods
	//#CM705882
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM705883
	//------------------------------------------------------------


	//#CM705884
	//------------------------------------------------------------
	//#CM705885
	// private methods
	//#CM705886
	//------------------------------------------------------------
	//#CM705887
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + RETRIEVALPLANUKEY);
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
		lst.add(prefix + PIECEAREA);
		lst.add(prefix + CASEAREA);
		lst.add(prefix + PIECEZONE);
		lst.add(prefix + CASEZONE);
		lst.add(prefix + PIECELOCATION);
		lst.add(prefix + CASELOCATION);
		lst.add(prefix + PIECEORDERNO);
		lst.add(prefix + CASEORDERNO);
		lst.add(prefix + USEBYDATE);
		lst.add(prefix + LOTNO);
		lst.add(prefix + PLANINFORMATION);
		lst.add(prefix + BATCHNO);
		lst.add(prefix + HOSTCOLLECTBATCHNO);
		lst.add(prefix + SCHFLAG);
		lst.add(prefix + WORKERCODE);
		lst.add(prefix + WORKERNAME);
		lst.add(prefix + TERMINALNO);
		lst.add(prefix + REGISTKIND);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM705888
	//------------------------------------------------------------
	//#CM705889
	// utility methods
	//#CM705890
	//------------------------------------------------------------
	//#CM705891
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: RetrievalPlan.java,v 1.5 2006/11/16 02:15:39 suresh Exp $" ;
	}
}
