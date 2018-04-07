//#CM707015
//$Id: StoragePlan.java,v 1.5 2006/11/16 02:15:36 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM707016
/*
 * Copyright(c) 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.util.Date;
import java.util.List;
import java.util.Vector;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.wms.base.dbhandler.FieldName;
import jp.co.daifuku.wms.base.entity.StoragePlan;

//#CM707017
/**
 * Entity class of STORAGEPLAN
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:36 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class StoragePlan
		extends AbstractEntity
{
	//#CM707018
	//------------------------------------------------------------
	//#CM707019
	// class variables (prefix '$')
	//#CM707020
	//------------------------------------------------------------
	//#CM707021
	//	private String	$classVar ;

	//#CM707022
	//------------------------------------------------------------
	//#CM707023
	// fields (upper case only)
	//#CM707024
	//------------------------------------------------------------
	//#CM707025
	/*
	 *  * Table name : STORAGEPLAN
	 * Storage Plan List key :         STORAGEPLANUKEY     varchar2(16)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Storage Plan Date :             PLANDATE            varchar2(8)
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Consignor name :                CONSIGNORNAME       varchar2(40)
	 * Supplier :                      SUPPLIERCODE        varchar2(16)
	 * Supplier :                      SUPPLIERNAME1       varchar2(40)
	 * Item code :                     ITEMCODE            varchar2(16)
	 * Item name :                     ITEMNAME1           varchar2(40)
	 * Storage Plan qty :              PLANQTY             number
	 * Storage Result qty :            RESULTQTY           number
	 * Storage Shortage qty :          SHORTAGECNT         number
	 * Entering case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case / Piece flag :             CASEPIECEFLAG       varchar2(1)
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * Piece Storage location :        PIECELOCATION       varchar2(16)
	 * Case Storage location :         CASELOCATION        varchar2(16)
	 * Use By Date :                   USEBYDATE           varchar2(8)
	 * Lot :                           LOTNO               varchar2(20)
	 * Plan :                          PLANINFORMATION     varchar2(40)
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

	//#CM707026
	/**Table name definition*/

	public static final String TABLE_NAME = "DNSTORAGEPLAN";

	//#CM707027
	/** Column Definition (STORAGEPLANUKEY) */

	public static final FieldName STORAGEPLANUKEY = new FieldName("STORAGE_PLAN_UKEY");

	//#CM707028
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM707029
	/** Column Definition (PLANDATE) */

	public static final FieldName PLANDATE = new FieldName("PLAN_DATE");

	//#CM707030
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM707031
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM707032
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM707033
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM707034
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM707035
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM707036
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM707037
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM707038
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM707039
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM707040
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM707041
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM707042
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM707043
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM707044
	/** Column Definition (PIECELOCATION) */

	public static final FieldName PIECELOCATION = new FieldName("PIECE_LOCATION");

	//#CM707045
	/** Column Definition (CASELOCATION) */

	public static final FieldName CASELOCATION = new FieldName("CASE_LOCATION");

	//#CM707046
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM707047
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM707048
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM707049
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM707050
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM707051
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM707052
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM707053
	/** Column Definition (REGISTKIND) */

	public static final FieldName REGISTKIND = new FieldName("REGIST_KIND");

	//#CM707054
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM707055
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM707056
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM707057
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM707058
	//------------------------------------------------------------
	//#CM707059
	// properties (prefix 'p_')
	//#CM707060
	//------------------------------------------------------------
	//#CM707061
	//	private String	p_Name ;


	//#CM707062
	//------------------------------------------------------------
	//#CM707063
	// instance variables (prefix '_')
	//#CM707064
	//------------------------------------------------------------
	//#CM707065
	//	private String	_instanceVar ;

	//#CM707066
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM707067
	//------------------------------------------------------------
	//#CM707068
	// constructors
	//#CM707069
	//------------------------------------------------------------

	//#CM707070
	/**
	 * Prepare class name list and generate instance
	 */
	public StoragePlan()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM707071
	//------------------------------------------------------------
	//#CM707072
	// accessors
	//#CM707073
	//------------------------------------------------------------

	//#CM707074
	/**
	 * Set value to Storage Plan List key
	 * @param arg Storage Plan List key to be set
	 */
	public void setStoragePlanUkey(String arg)
	{
		setValue(STORAGEPLANUKEY, arg);
	}

	//#CM707075
	/**
	 * Fetch Storage Plan List key
	 * @return Storage Plan List key
	 */
	public String getStoragePlanUkey()
	{
		return getValue(StoragePlan.STORAGEPLANUKEY).toString();
	}

	//#CM707076
	/**
	 * Set value to Status flag
	 * @param arg Status flag to be set
	 */
	public void setStatusFlag(String arg)
	{
		setValue(STATUSFLAG, arg);
	}

	//#CM707077
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(StoragePlan.STATUSFLAG).toString();
	}

	//#CM707078
	/**
	 * Set value to Storage Plan Date
	 * @param arg Storage Plan Date to be set
	 */
	public void setPlanDate(String arg)
	{
		setValue(PLANDATE, arg);
	}

	//#CM707079
	/**
	 * Fetch Storage Plan Date
	 * @return Storage Plan Date
	 */
	public String getPlanDate()
	{
		return getValue(StoragePlan.PLANDATE).toString();
	}

	//#CM707080
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM707081
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(StoragePlan.CONSIGNORCODE).toString();
	}

	//#CM707082
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM707083
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(StoragePlan.CONSIGNORNAME).toString();
	}

	//#CM707084
	/**
	 * Set value to Supplier
	 * @param arg Supplier to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM707085
	/**
	 * Fetch Supplier
	 * @return Supplier
	 */
	public String getSupplierCode()
	{
		return getValue(StoragePlan.SUPPLIERCODE).toString();
	}

	//#CM707086
	/**
	 * Set value to Supplier
	 * @param arg Supplier to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM707087
	/**
	 * Fetch Supplier
	 * @return Supplier
	 */
	public String getSupplierName1()
	{
		return getValue(StoragePlan.SUPPLIERNAME1).toString();
	}

	//#CM707088
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM707089
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(StoragePlan.ITEMCODE).toString();
	}

	//#CM707090
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM707091
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(StoragePlan.ITEMNAME1).toString();
	}

	//#CM707092
	/**
	 * Set value to Storage Plan qty
	 * @param arg Storage Plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM707093
	/**
	 * Fetch Storage Plan qty
	 * @return Storage Plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(StoragePlan.PLANQTY).intValue();
	}

	//#CM707094
	/**
	 * Set value to Storage Result qty
	 * @param arg Storage Result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM707095
	/**
	 * Fetch Storage Result qty
	 * @return Storage Result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(StoragePlan.RESULTQTY).intValue();
	}

	//#CM707096
	/**
	 * Set value to Storage Shortage qty
	 * @param arg Storage Shortage qty to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM707097
	/**
	 * Fetch Storage Shortage qty
	 * @return Storage Shortage qty
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(StoragePlan.SHORTAGECNT).intValue();
	}

	//#CM707098
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM707099
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(StoragePlan.ENTERINGQTY).intValue();
	}

	//#CM707100
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM707101
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(StoragePlan.BUNDLEENTERINGQTY).intValue();
	}

	//#CM707102
	/**
	 * Set value to Case / Piece flag
	 * @param arg Case / Piece flag to be set
	 */
	public void setCasePieceFlag(String arg)
	{
		setValue(CASEPIECEFLAG, arg);
	}

	//#CM707103
	/**
	 * Fetch Case / Piece flag
	 * @return Case / Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(StoragePlan.CASEPIECEFLAG).toString();
	}

	//#CM707104
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM707105
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(StoragePlan.ITF).toString();
	}

	//#CM707106
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM707107
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(StoragePlan.BUNDLEITF).toString();
	}

	//#CM707108
	/**
	 * Set value to Piece Storage location
	 * @param arg Piece Storage location to be set
	 */
	public void setPieceLocation(String arg)
	{
		setValue(PIECELOCATION, arg);
	}

	//#CM707109
	/**
	 * Fetch Piece Storage location
	 * @return Piece Storage location
	 */
	public String getPieceLocation()
	{
		return getValue(StoragePlan.PIECELOCATION).toString();
	}

	//#CM707110
	/**
	 * Set value to Case Storage location
	 * @param arg Case Storage location to be set
	 */
	public void setCaseLocation(String arg)
	{
		setValue(CASELOCATION, arg);
	}

	//#CM707111
	/**
	 * Fetch Case Storage location
	 * @return Case Storage location
	 */
	public String getCaseLocation()
	{
		return getValue(StoragePlan.CASELOCATION).toString();
	}

	//#CM707112
	/**
	 * Set value to Use By Date
	 * @param arg Use By Date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM707113
	/**
	 * Fetch Use By Date
	 * @return Use By Date
	 */
	public String getUseByDate()
	{
		return getValue(StoragePlan.USEBYDATE).toString();
	}

	//#CM707114
	/**
	 * Set value to Lot
	 * @param arg Lot to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM707115
	/**
	 * Fetch Lot
	 * @return Lot
	 */
	public String getLotNo()
	{
		return getValue(StoragePlan.LOTNO).toString();
	}

	//#CM707116
	/**
	 * Set value to Plan
	 * @param arg Plan to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM707117
	/**
	 * Fetch Plan
	 * @return Plan
	 */
	public String getPlanInformation()
	{
		return getValue(StoragePlan.PLANINFORMATION).toString();
	}

	//#CM707118
	/**
	 * Set value to Batch No (Schedule No)
	 * @param arg Batch No (Schedule No) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM707119
	/**
	 * Fetch Batch No (Schedule No)
	 * @return Batch No (Schedule No)
	 */
	public String getBatchNo()
	{
		return getValue(StoragePlan.BATCHNO).toString();
	}

	//#CM707120
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM707121
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(StoragePlan.WORKERCODE).toString();
	}

	//#CM707122
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM707123
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(StoragePlan.WORKERNAME).toString();
	}

	//#CM707124
	/**
	 * Set value to Terminal no
	 * @param arg Terminal no to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM707125
	/**
	 * Fetch Terminal no
	 * @return Terminal no
	 */
	public String getTerminalNo()
	{
		return getValue(StoragePlan.TERMINALNO).toString();
	}

	//#CM707126
	/**
	 * Set value to Registration type
	 * @param arg Registration type to be set
	 */
	public void setRegistKind(String arg)
	{
		setValue(REGISTKIND, arg);
	}

	//#CM707127
	/**
	 * Fetch Registration type
	 * @return Registration type
	 */
	public String getRegistKind()
	{
		return getValue(StoragePlan.REGISTKIND).toString();
	}

	//#CM707128
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM707129
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(StoragePlan.REGISTDATE);
	}

	//#CM707130
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM707131
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(StoragePlan.REGISTPNAME).toString();
	}

	//#CM707132
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM707133
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(StoragePlan.LASTUPDATEDATE);
	}

	//#CM707134
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM707135
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(StoragePlan.LASTUPDATEPNAME).toString();
	}


	//#CM707136
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

	//#CM707137
	public void setInitCreateColumn()
	{
		setValue(LASTUPDATEDATE, "");
		setValue(REGISTDATE, "");

		setValue(PLANQTY, new Integer(0));
		setValue(RESULTQTY, new Integer(0));
		setValue(SHORTAGECNT, new Integer(0));
		setValue(ENTERINGQTY, new Integer(0));
		setValue(BUNDLEENTERINGQTY, new Integer(0));

	}

	/**
	 * ??????????????<BR>
	 * ??????????<BR>
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
	//#CM707138
	// package methods
	//#CM707139
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707140
	//------------------------------------------------------------


	//#CM707141
	//------------------------------------------------------------
	//#CM707142
	// protected methods
	//#CM707143
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM707144
	//------------------------------------------------------------


	//#CM707145
	//------------------------------------------------------------
	//#CM707146
	// private methods
	//#CM707147
	//------------------------------------------------------------
	//#CM707148
	/**
	 * Prepare class name list. (for SearchKey, AlterKey use)
	 * Match with column definition
	 */
	private void prepare()
	{
		List lst = getColumnList() ;

		String prefix = TABLE_NAME + "." ;

		lst.add(prefix + STORAGEPLANUKEY);
		lst.add(prefix + STATUSFLAG);
		lst.add(prefix + PLANDATE);
		lst.add(prefix + CONSIGNORCODE);
		lst.add(prefix + CONSIGNORNAME);
		lst.add(prefix + SUPPLIERCODE);
		lst.add(prefix + SUPPLIERNAME1);
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
		lst.add(prefix + PIECELOCATION);
		lst.add(prefix + CASELOCATION);
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


	//#CM707149
	//------------------------------------------------------------
	//#CM707150
	// utility methods
	//#CM707151
	//------------------------------------------------------------
	//#CM707152
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: StoragePlan.java,v 1.5 2006/11/16 02:15:36 suresh Exp $" ;
	}
}
