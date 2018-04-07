//#CM704728
//$Id: Movement.java,v 1.5 2006/11/16 02:15:42 suresh Exp $
package jp.co.daifuku.wms.base.entity ;

//#CM704729
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
import jp.co.daifuku.wms.base.entity.Movement;

//#CM704730
/**
 * Entity class of MOVEMENT
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
 * @version $Revision: 1.5 $, $Date: 2006/11/16 02:15:42 $
 * @author  ss
 * @author  Last commit: $Author: suresh $
 */


public class Movement
		extends AbstractEntity implements Cloneable
{
	//#CM704731
	//------------------------------------------------------------
	//#CM704732
	// class variables (prefix '$')
	//#CM704733
	//------------------------------------------------------------
	//#CM704734
	//	private String	$classVar ;
	//#CM704735
	/**
	 * Status flag (Waiting for Storage)
	 */
	public static final String STATUSFLAG_UNSTART = "1" ;

	//#CM704736
	/**
	 * Status flag (Storing)
	 */
	public static final String STATUSFLAG_NOWWORKING = "2" ;

	//#CM704737
	/**
	 * Status flag (Completed)
	 */
	public static final String STATUSFLAG_COMPLETION = "3" ;

	//#CM704738
	/**
	 * Status flag (Deletion)
	 */
	public static final String STATUSFLAG_DELETE = "9" ;

	//#CM704739
	//------------------------------------------------------------
	//#CM704740
	// fields (upper case only)
	//#CM704741
	//------------------------------------------------------------
	//#CM704742
	/*
	 *  * Table name : MOVEMENT
	 * Work no :                       JOBNO               varchar2(8)
	 * Work type :                     JOBTYPE             varchar2(2)
	 * Collect work no :               COLLECTJOBNO        varchar2(8)
	 * Stock ID :                      STOCKID             varchar2(8)
	 * Area no :                       AREANO              varchar2(16)
	 * Location no :                   LOCATIONNO          varchar2(16)
	 * Status flag :                   STATUSFLAG          varchar2(1)
	 * Work start flag :               BEGINNINGFLAG       varchar2(1)
	 * Work date :                     WORKDATE            varchar2(8)
	 * Consignor code :                CONSIGNORCODE       varchar2(16)
	 * Consignor name :                CONSIGNORNAME       varchar2(40)
	 * Supplier code :                 SUPPLIERCODE        varchar2(16)
	 * Supplier name :                 SUPPLIERNAME1       varchar2(40)
	 * Item code :                     ITEMCODE            varchar2(16)
	 * Item name :                     ITEMNAME1           varchar2(40)
	 * Plan qty :                      PLANQTY             number
	 * Result qty :                    RESULTQTY           number
	 * Shortage qty :                  SHORTAGECNT         number
	 * Entering case qty :             ENTERINGQTY         number
	 * Bundle qty :                    BUNDLEENTERINGQTY   number
	 * Case/Piece flag :               CASEPIECEFLAG       varchar2(1)
	 * Case/Piece flag (Work form flag) :WORKFORMFLAG        varchar2(1)
	 * Case ITF :                      ITF                 varchar2(16)
	 * Bundle ITF :                    BUNDLEITF           varchar2(16)
	 * Use by date :                   USEBYDATE           varchar2(8)
	 * Lot no :                        LOTNO               varchar2(20)
	 * Plan details comment :          PLANINFORMATION     varchar2(40)
	 * Result use by date :            RESULTUSEBYDATE     varchar2(8)
	 * Result lot no :                 RESULTLOTNO         varchar2(20)
	 * Result area no :                RESULTAREANO        varchar2(16)
	 * Result location no :            RESULTLOCATIONNO    varchar2(16)
	 * Report flag :                   REPORTFLAG          varchar2(1)
	 * Pring flag :                    PRINTFLAG           varchar2(1)
	 * Batch no (Schedule no) :        BATCHNO             varchar2(8)
	 * Worker code :                   WORKERCODE          varchar2(4)
	 * Worker name :                   WORKERNAME          varchar2(20)
	 * Terminal no :                   TERMINALNO          varchar2(3)
	 * Retrieval worker code :         RETRIEVALWORKERCODE varchar2(4)
	 * Retrieval worker name :         RETRIEVALWORKERNAME varchar2(20)
	 * Retrieval Terminal no :         RETRIEVALTERMINALNO varchar2(3)
	 * Registered date :               REGISTDATE          date
	 * Registered name :               REGISTPNAME         varchar2(48)
	 * Last update date :              LASTUPDATEDATE      date
	 * Last update name :              LASTUPDATEPNAME     varchar2(48)
	 */

	//#CM704743
	/**Table name definition*/

	public static final String TABLE_NAME = "DNMOVEMENT";

	//#CM704744
	/** Column Definition (JOBNO) */

	public static final FieldName JOBNO = new FieldName("JOB_NO");

	//#CM704745
	/** Column Definition (JOBTYPE) */

	public static final FieldName JOBTYPE = new FieldName("JOB_TYPE");

	//#CM704746
	/** Column Definition (COLLECTJOBNO) */

	public static final FieldName COLLECTJOBNO = new FieldName("COLLECT_JOB_NO");

	//#CM704747
	/** Column Definition (STOCKID) */

	public static final FieldName STOCKID = new FieldName("STOCK_ID");

	//#CM704748
	/** Column Definition (AREANO) */

	public static final FieldName AREANO = new FieldName("AREA_NO");

	//#CM704749
	/** Column Definition (LOCATIONNO) */

	public static final FieldName LOCATIONNO = new FieldName("LOCATION_NO");

	//#CM704750
	/** Column Definition (STATUSFLAG) */

	public static final FieldName STATUSFLAG = new FieldName("STATUS_FLAG");

	//#CM704751
	/** Column Definition (BEGINNINGFLAG) */

	public static final FieldName BEGINNINGFLAG = new FieldName("BEGINNING_FLAG");

	//#CM704752
	/** Column Definition (WORKDATE) */

	public static final FieldName WORKDATE = new FieldName("WORK_DATE");

	//#CM704753
	/** Column Definition (CONSIGNORCODE) */

	public static final FieldName CONSIGNORCODE = new FieldName("CONSIGNOR_CODE");

	//#CM704754
	/** Column Definition (CONSIGNORNAME) */

	public static final FieldName CONSIGNORNAME = new FieldName("CONSIGNOR_NAME");

	//#CM704755
	/** Column Definition (SUPPLIERCODE) */

	public static final FieldName SUPPLIERCODE = new FieldName("SUPPLIER_CODE");

	//#CM704756
	/** Column Definition (SUPPLIERNAME1) */

	public static final FieldName SUPPLIERNAME1 = new FieldName("SUPPLIER_NAME1");

	//#CM704757
	/** Column Definition (ITEMCODE) */

	public static final FieldName ITEMCODE = new FieldName("ITEM_CODE");

	//#CM704758
	/** Column Definition (ITEMNAME1) */

	public static final FieldName ITEMNAME1 = new FieldName("ITEM_NAME1");

	//#CM704759
	/** Column Definition (PLANQTY) */

	public static final FieldName PLANQTY = new FieldName("PLAN_QTY");

	//#CM704760
	/** Column Definition (RESULTQTY) */

	public static final FieldName RESULTQTY = new FieldName("RESULT_QTY");

	//#CM704761
	/** Column Definition (SHORTAGECNT) */

	public static final FieldName SHORTAGECNT = new FieldName("SHORTAGE_CNT");

	//#CM704762
	/** Column Definition (ENTERINGQTY) */

	public static final FieldName ENTERINGQTY = new FieldName("ENTERING_QTY");

	//#CM704763
	/** Column Definition (BUNDLEENTERINGQTY) */

	public static final FieldName BUNDLEENTERINGQTY = new FieldName("BUNDLE_ENTERING_QTY");

	//#CM704764
	/** Column Definition (CASEPIECEFLAG) */

	public static final FieldName CASEPIECEFLAG = new FieldName("CASE_PIECE_FLAG");

	//#CM704765
	/** Column Definition (WORKFORMFLAG) */

	public static final FieldName WORKFORMFLAG = new FieldName("WORK_FORM_FLAG");

	//#CM704766
	/** Column Definition (ITF) */

	public static final FieldName ITF = new FieldName("ITF");

	//#CM704767
	/** Column Definition (BUNDLEITF) */

	public static final FieldName BUNDLEITF = new FieldName("BUNDLE_ITF");

	//#CM704768
	/** Column Definition (USEBYDATE) */

	public static final FieldName USEBYDATE = new FieldName("USE_BY_DATE");

	//#CM704769
	/** Column Definition (LOTNO) */

	public static final FieldName LOTNO = new FieldName("LOT_NO");

	//#CM704770
	/** Column Definition (PLANINFORMATION) */

	public static final FieldName PLANINFORMATION = new FieldName("PLAN_INFORMATION");

	//#CM704771
	/** Column Definition (RESULTUSEBYDATE) */

	public static final FieldName RESULTUSEBYDATE = new FieldName("RESULT_USE_BY_DATE");

	//#CM704772
	/** Column Definition (RESULTLOTNO) */

	public static final FieldName RESULTLOTNO = new FieldName("RESULT_LOT_NO");

	//#CM704773
	/** Column Definition (RESULTAREANO) */

	public static final FieldName RESULTAREANO = new FieldName("RESULT_AREA_NO");

	//#CM704774
	/** Column Definition (RESULTLOCATIONNO) */

	public static final FieldName RESULTLOCATIONNO = new FieldName("RESULT_LOCATION_NO");

	//#CM704775
	/** Column Definition (REPORTFLAG) */

	public static final FieldName REPORTFLAG = new FieldName("REPORT_FLAG");

	//#CM704776
	/** Column Definition (PRINTFLAG) */

	public static final FieldName PRINTFLAG = new FieldName("PRINT_FLAG");

	//#CM704777
	/** Column Definition (BATCHNO) */

	public static final FieldName BATCHNO = new FieldName("BATCH_NO");

	//#CM704778
	/** Column Definition (WORKERCODE) */

	public static final FieldName WORKERCODE = new FieldName("WORKER_CODE");

	//#CM704779
	/** Column Definition (WORKERNAME) */

	public static final FieldName WORKERNAME = new FieldName("WORKER_NAME");

	//#CM704780
	/** Column Definition (TERMINALNO) */

	public static final FieldName TERMINALNO = new FieldName("TERMINAL_NO");

	//#CM704781
	/** Column Definition (RETRIEVALWORKERCODE) */

	public static final FieldName RETRIEVALWORKERCODE = new FieldName("RETRIEVAL_WORKER_CODE");

	//#CM704782
	/** Column Definition (RETRIEVALWORKERNAME) */

	public static final FieldName RETRIEVALWORKERNAME = new FieldName("RETRIEVAL_WORKER_NAME");

	//#CM704783
	/** Column Definition (RETRIEVALTERMINALNO) */

	public static final FieldName RETRIEVALTERMINALNO = new FieldName("RETRIEVAL_TERMINAL_NO");

	//#CM704784
	/** Column Definition (REGISTDATE) */

	public static final FieldName REGISTDATE = new FieldName("REGIST_DATE");

	//#CM704785
	/** Column Definition (REGISTPNAME) */

	public static final FieldName REGISTPNAME = new FieldName("REGIST_PNAME");

	//#CM704786
	/** Column Definition (LASTUPDATEDATE) */

	public static final FieldName LASTUPDATEDATE = new FieldName("LAST_UPDATE_DATE");

	//#CM704787
	/** Column Definition (LASTUPDATEPNAME) */

	public static final FieldName LASTUPDATEPNAME = new FieldName("LAST_UPDATE_PNAME");


	//#CM704788
	//------------------------------------------------------------
	//#CM704789
	// properties (prefix 'p_')
	//#CM704790
	//------------------------------------------------------------
	//#CM704791
	//	private String	p_Name ;


	//#CM704792
	//------------------------------------------------------------
	//#CM704793
	// instance variables (prefix '_')
	//#CM704794
	//------------------------------------------------------------
	//#CM704795
	//	private String	_instanceVar ;

	//#CM704796
	/**
	 * Delimiter
	 */
	protected String wDelim = MessageResource.DELIM;

	//#CM704797
	//------------------------------------------------------------
	//#CM704798
	// constructors
	//#CM704799
	//------------------------------------------------------------

	//#CM704800
	/**
	 * Prepare class name list and generate instance
	 */
	public Movement()
	{
		super() ;
		prepare() ;
		setInitCreateColumn();
	}

	//#CM704801
	//------------------------------------------------------------
	//#CM704802
	// accessors
	//#CM704803
	//------------------------------------------------------------

	//#CM704804
	/**
	 * Set value to Work no
	 * @param arg Work no to be set
	 */
	public void setJobNo(String arg)
	{
		setValue(JOBNO, arg);
	}

	//#CM704805
	/**
	 * Fetch Work no
	 * @return Work no
	 */
	public String getJobNo()
	{
		return getValue(Movement.JOBNO).toString();
	}

	//#CM704806
	/**
	 * Set value to Work type
	 * @param arg Work type to be set
	 */
	public void setJobType(String arg)
	{
		setValue(JOBTYPE, arg);
	}

	//#CM704807
	/**
	 * Fetch Work type
	 * @return Work type
	 */
	public String getJobType()
	{
		return getValue(Movement.JOBTYPE).toString();
	}

	//#CM704808
	/**
	 * Set value to Collect work no
	 * @param arg Collect work no to be set
	 */
	public void setCollectJobNo(String arg)
	{
		setValue(COLLECTJOBNO, arg);
	}

	//#CM704809
	/**
	 * Fetch Collect work no
	 * @return Collect work no
	 */
	public String getCollectJobNo()
	{
		return getValue(Movement.COLLECTJOBNO).toString();
	}

	//#CM704810
	/**
	 * Set value to Stock ID
	 * @param arg Stock ID to be set
	 */
	public void setStockId(String arg)
	{
		setValue(STOCKID, arg);
	}

	//#CM704811
	/**
	 * Fetch Stock ID
	 * @return Stock ID
	 */
	public String getStockId()
	{
		return getValue(Movement.STOCKID).toString();
	}

	//#CM704812
	/**
	 * Set value to Area no
	 * @param arg Area no to be set
	 */
	public void setAreaNo(String arg)
	{
		setValue(AREANO, arg);
	}

	//#CM704813
	/**
	 * Fetch Area no
	 * @return Area no
	 */
	public String getAreaNo()
	{
		return getValue(Movement.AREANO).toString();
	}

	//#CM704814
	/**
	 * Set value to Location no
	 * @param arg Location no to be set
	 */
	public void setLocationNo(String arg)
	{
		setValue(LOCATIONNO, arg);
	}

	//#CM704815
	/**
	 * Fetch Location no
	 * @return Location no
	 */
	public String getLocationNo()
	{
		return getValue(Movement.LOCATIONNO).toString();
	}

	//#CM704816
	/**
	 * Set value to Status flag
	 * @param arg Status flag to be set
	 */
	public void setStatusFlag(String arg)
	{
		setValue(STATUSFLAG, arg);
	}

	//#CM704817
	/**
	 * Fetch Status flag
	 * @return Status flag
	 */
	public String getStatusFlag()
	{
		return getValue(Movement.STATUSFLAG).toString();
	}

	//#CM704818
	/**
	 * Set value to Work start flag
	 * @param arg Work start flag to be set
	 */
	public void setBeginningFlag(String arg)
	{
		setValue(BEGINNINGFLAG, arg);
	}

	//#CM704819
	/**
	 * Fetch Work start flag
	 * @return Work start flag
	 */
	public String getBeginningFlag()
	{
		return getValue(Movement.BEGINNINGFLAG).toString();
	}

	//#CM704820
	/**
	 * Set value to Work date
	 * @param arg Work date to be set
	 */
	public void setWorkDate(String arg)
	{
		setValue(WORKDATE, arg);
	}

	//#CM704821
	/**
	 * Fetch Work date
	 * @return Work date
	 */
	public String getWorkDate()
	{
		return getValue(Movement.WORKDATE).toString();
	}

	//#CM704822
	/**
	 * Set value to Consignor code
	 * @param arg Consignor code to be set
	 */
	public void setConsignorCode(String arg)
	{
		setValue(CONSIGNORCODE, arg);
	}

	//#CM704823
	/**
	 * Fetch Consignor code
	 * @return Consignor code
	 */
	public String getConsignorCode()
	{
		return getValue(Movement.CONSIGNORCODE).toString();
	}

	//#CM704824
	/**
	 * Set value to Consignor name
	 * @param arg Consignor name to be set
	 */
	public void setConsignorName(String arg)
	{
		setValue(CONSIGNORNAME, arg);
	}

	//#CM704825
	/**
	 * Fetch Consignor name
	 * @return Consignor name
	 */
	public String getConsignorName()
	{
		return getValue(Movement.CONSIGNORNAME).toString();
	}

	//#CM704826
	/**
	 * Set value to Supplier code
	 * @param arg Supplier code to be set
	 */
	public void setSupplierCode(String arg)
	{
		setValue(SUPPLIERCODE, arg);
	}

	//#CM704827
	/**
	 * Fetch Supplier code
	 * @return Supplier code
	 */
	public String getSupplierCode()
	{
		return getValue(Movement.SUPPLIERCODE).toString();
	}

	//#CM704828
	/**
	 * Set value to Supplier name
	 * @param arg Supplier name to be set
	 */
	public void setSupplierName1(String arg)
	{
		setValue(SUPPLIERNAME1, arg);
	}

	//#CM704829
	/**
	 * Fetch Supplier name
	 * @return Supplier name
	 */
	public String getSupplierName1()
	{
		return getValue(Movement.SUPPLIERNAME1).toString();
	}

	//#CM704830
	/**
	 * Set value to Item code
	 * @param arg Item code to be set
	 */
	public void setItemCode(String arg)
	{
		setValue(ITEMCODE, arg);
	}

	//#CM704831
	/**
	 * Fetch Item code
	 * @return Item code
	 */
	public String getItemCode()
	{
		return getValue(Movement.ITEMCODE).toString();
	}

	//#CM704832
	/**
	 * Set value to Item name
	 * @param arg Item name to be set
	 */
	public void setItemName1(String arg)
	{
		setValue(ITEMNAME1, arg);
	}

	//#CM704833
	/**
	 * Fetch Item name
	 * @return Item name
	 */
	public String getItemName1()
	{
		return getValue(Movement.ITEMNAME1).toString();
	}

	//#CM704834
	/**
	 * Set value to Plan qty
	 * @param arg Plan qty to be set
	 */
	public void setPlanQty(int arg)
	{
		setValue(PLANQTY, new Integer(arg));
	}

	//#CM704835
	/**
	 * Fetch Plan qty
	 * @return Plan qty
	 */
	public int getPlanQty()
	{
		return getBigDecimal(Movement.PLANQTY).intValue();
	}

	//#CM704836
	/**
	 * Set value to Result qty
	 * @param arg Result qty to be set
	 */
	public void setResultQty(int arg)
	{
		setValue(RESULTQTY, new Integer(arg));
	}

	//#CM704837
	/**
	 * Fetch Result qty
	 * @return Result qty
	 */
	public int getResultQty()
	{
		return getBigDecimal(Movement.RESULTQTY).intValue();
	}

	//#CM704838
	/**
	 * Set value to Shortage qty
	 * @param arg Shortage qty to be set
	 */
	public void setShortageCnt(int arg)
	{
		setValue(SHORTAGECNT, new Integer(arg));
	}

	//#CM704839
	/**
	 * Fetch Shortage qty
	 * @return Shortage qty
	 */
	public int getShortageCnt()
	{
		return getBigDecimal(Movement.SHORTAGECNT).intValue();
	}

	//#CM704840
	/**
	 * Set value to Entering case qty
	 * @param arg Entering case qty to be set
	 */
	public void setEnteringQty(int arg)
	{
		setValue(ENTERINGQTY, new Integer(arg));
	}

	//#CM704841
	/**
	 * Fetch Entering case qty
	 * @return Entering case qty
	 */
	public int getEnteringQty()
	{
		return getBigDecimal(Movement.ENTERINGQTY).intValue();
	}

	//#CM704842
	/**
	 * Set value to Bundle qty
	 * @param arg Bundle qty to be set
	 */
	public void setBundleEnteringQty(int arg)
	{
		setValue(BUNDLEENTERINGQTY, new Integer(arg));
	}

	//#CM704843
	/**
	 * Fetch Bundle qty
	 * @return Bundle qty
	 */
	public int getBundleEnteringQty()
	{
		return getBigDecimal(Movement.BUNDLEENTERINGQTY).intValue();
	}

	//#CM704844
	/**
	 * Set value to Case/Piece flag
	 * @param arg Case/Piece flag to be set
	 */
	public void setCasePieceFlag(String arg)
	{
		setValue(CASEPIECEFLAG, arg);
	}

	//#CM704845
	/**
	 * Fetch Case/Piece flag
	 * @return Case/Piece flag
	 */
	public String getCasePieceFlag()
	{
		return getValue(Movement.CASEPIECEFLAG).toString();
	}

	//#CM704846
	/**
	 * Set value to Case/Piece flag (Work form flag)
	 * @param arg Case/Piece flag (Work form flag) to be set
	 */
	public void setWorkFormFlag(String arg)
	{
		setValue(WORKFORMFLAG, arg);
	}

	//#CM704847
	/**
	 * Fetch Case/Piece flag (Work form flag)
	 * @return Case/Piece flag (Work form flag)
	 */
	public String getWorkFormFlag()
	{
		return getValue(Movement.WORKFORMFLAG).toString();
	}

	//#CM704848
	/**
	 * Set value to Case ITF
	 * @param arg Case ITF to be set
	 */
	public void setItf(String arg)
	{
		setValue(ITF, arg);
	}

	//#CM704849
	/**
	 * Fetch Case ITF
	 * @return Case ITF
	 */
	public String getItf()
	{
		return getValue(Movement.ITF).toString();
	}

	//#CM704850
	/**
	 * Set value to Bundle ITF
	 * @param arg Bundle ITF to be set
	 */
	public void setBundleItf(String arg)
	{
		setValue(BUNDLEITF, arg);
	}

	//#CM704851
	/**
	 * Fetch Bundle ITF
	 * @return Bundle ITF
	 */
	public String getBundleItf()
	{
		return getValue(Movement.BUNDLEITF).toString();
	}

	//#CM704852
	/**
	 * Set value to Use by date
	 * @param arg Use by date to be set
	 */
	public void setUseByDate(String arg)
	{
		setValue(USEBYDATE, arg);
	}

	//#CM704853
	/**
	 * Fetch Use by date
	 * @return Use by date
	 */
	public String getUseByDate()
	{
		return getValue(Movement.USEBYDATE).toString();
	}

	//#CM704854
	/**
	 * Set value to Lot no
	 * @param arg Lot no to be set
	 */
	public void setLotNo(String arg)
	{
		setValue(LOTNO, arg);
	}

	//#CM704855
	/**
	 * Fetch Lot no
	 * @return Lot no
	 */
	public String getLotNo()
	{
		return getValue(Movement.LOTNO).toString();
	}

	//#CM704856
	/**
	 * Set value to Plan details comment
	 * @param arg Plan details comment to be set
	 */
	public void setPlanInformation(String arg)
	{
		setValue(PLANINFORMATION, arg);
	}

	//#CM704857
	/**
	 * Fetch Plan details comment
	 * @return Plan details comment
	 */
	public String getPlanInformation()
	{
		return getValue(Movement.PLANINFORMATION).toString();
	}

	//#CM704858
	/**
	 * Set value to Result use by date
	 * @param arg Result use by date to be set
	 */
	public void setResultUseByDate(String arg)
	{
		setValue(RESULTUSEBYDATE, arg);
	}

	//#CM704859
	/**
	 * Fetch Result use by date
	 * @return Result use by date
	 */
	public String getResultUseByDate()
	{
		return getValue(Movement.RESULTUSEBYDATE).toString();
	}

	//#CM704860
	/**
	 * Set value to Result lot no
	 * @param arg Result lot no to be set
	 */
	public void setResultLotNo(String arg)
	{
		setValue(RESULTLOTNO, arg);
	}

	//#CM704861
	/**
	 * Fetch Result lot no
	 * @return Result lot no
	 */
	public String getResultLotNo()
	{
		return getValue(Movement.RESULTLOTNO).toString();
	}

	//#CM704862
	/**
	 * Set value to Result area no
	 * @param arg Result area no to be set
	 */
	public void setResultAreaNo(String arg)
	{
		setValue(RESULTAREANO, arg);
	}

	//#CM704863
	/**
	 * Fetch Result area no
	 * @return Result area no
	 */
	public String getResultAreaNo()
	{
		return getValue(Movement.RESULTAREANO).toString();
	}

	//#CM704864
	/**
	 * Set value to Result location no
	 * @param arg Result location no to be set
	 */
	public void setResultLocationNo(String arg)
	{
		setValue(RESULTLOCATIONNO, arg);
	}

	//#CM704865
	/**
	 * Fetch Result location no
	 * @return Result location no
	 */
	public String getResultLocationNo()
	{
		return getValue(Movement.RESULTLOCATIONNO).toString();
	}

	//#CM704866
	/**
	 * Set value to Report flag
	 * @param arg Report flag to be set
	 */
	public void setReportFlag(String arg)
	{
		setValue(REPORTFLAG, arg);
	}

	//#CM704867
	/**
	 * Fetch Report flag
	 * @return Report flag
	 */
	public String getReportFlag()
	{
		return getValue(Movement.REPORTFLAG).toString();
	}

	//#CM704868
	/**
	 * Set value to Pring flag
	 * @param arg Pring flag to be set
	 */
	public void setPrintFlag(String arg)
	{
		setValue(PRINTFLAG, arg);
	}

	//#CM704869
	/**
	 * Fetch Pring flag
	 * @return Pring flag
	 */
	public String getPrintFlag()
	{
		return getValue(Movement.PRINTFLAG).toString();
	}

	//#CM704870
	/**
	 * Set value to Batch no (Schedule no)
	 * @param arg Batch no (Schedule no) to be set
	 */
	public void setBatchNo(String arg)
	{
		setValue(BATCHNO, arg);
	}

	//#CM704871
	/**
	 * Fetch Batch no (Schedule no)
	 * @return Batch no (Schedule no)
	 */
	public String getBatchNo()
	{
		return getValue(Movement.BATCHNO).toString();
	}

	//#CM704872
	/**
	 * Set value to Worker code
	 * @param arg Worker code to be set
	 */
	public void setWorkerCode(String arg)
	{
		setValue(WORKERCODE, arg);
	}

	//#CM704873
	/**
	 * Fetch Worker code
	 * @return Worker code
	 */
	public String getWorkerCode()
	{
		return getValue(Movement.WORKERCODE).toString();
	}

	//#CM704874
	/**
	 * Set value to Worker name
	 * @param arg Worker name to be set
	 */
	public void setWorkerName(String arg)
	{
		setValue(WORKERNAME, arg);
	}

	//#CM704875
	/**
	 * Fetch Worker name
	 * @return Worker name
	 */
	public String getWorkerName()
	{
		return getValue(Movement.WORKERNAME).toString();
	}

	//#CM704876
	/**
	 * Set value to Terminal no
	 * @param arg Terminal no to be set
	 */
	public void setTerminalNo(String arg)
	{
		setValue(TERMINALNO, arg);
	}

	//#CM704877
	/**
	 * Fetch Terminal no
	 * @return Terminal no
	 */
	public String getTerminalNo()
	{
		return getValue(Movement.TERMINALNO).toString();
	}

	//#CM704878
	/**
	 * Set value to Retrieval worker code
	 * @param arg Retrieval worker code to be set
	 */
	public void setRetrievalWorkerCode(String arg)
	{
		setValue(RETRIEVALWORKERCODE, arg);
	}

	//#CM704879
	/**
	 * Fetch Retrieval worker code
	 * @return Retrieval worker code
	 */
	public String getRetrievalWorkerCode()
	{
		return getValue(Movement.RETRIEVALWORKERCODE).toString();
	}

	//#CM704880
	/**
	 * Set value to Retrieval worker name
	 * @param arg Retrieval worker name to be set
	 */
	public void setRetrievalWorkerName(String arg)
	{
		setValue(RETRIEVALWORKERNAME, arg);
	}

	//#CM704881
	/**
	 * Fetch Retrieval worker name
	 * @return Retrieval worker name
	 */
	public String getRetrievalWorkerName()
	{
		return getValue(Movement.RETRIEVALWORKERNAME).toString();
	}

	//#CM704882
	/**
	 * Set value to Retrieval Terminal no
	 * @param arg Retrieval Terminal no to be set
	 */
	public void setRetrievalTerminalNo(String arg)
	{
		setValue(RETRIEVALTERMINALNO, arg);
	}

	//#CM704883
	/**
	 * Fetch Retrieval Terminal no
	 * @return Retrieval Terminal no
	 */
	public String getRetrievalTerminalNo()
	{
		return getValue(Movement.RETRIEVALTERMINALNO).toString();
	}

	//#CM704884
	/**
	 * Set value to Registered date
	 * @param arg Registered date to be set
	 */
	public void setRegistDate(Date arg)
	{
		setValue(REGISTDATE, arg);
	}

	//#CM704885
	/**
	 * Fetch Registered date
	 * @return Registered date
	 */
	public Date getRegistDate()
	{
		return (Date)getValue(Movement.REGISTDATE);
	}

	//#CM704886
	/**
	 * Set value to Registered name
	 * @param arg Registered name to be set
	 */
	public void setRegistPname(String arg)
	{
		setValue(REGISTPNAME, arg);
	}

	//#CM704887
	/**
	 * Fetch Registered name
	 * @return Registered name
	 */
	public String getRegistPname()
	{
		return getValue(Movement.REGISTPNAME).toString();
	}

	//#CM704888
	/**
	 * Set value to Last update date
	 * @param arg Last update date to be set
	 */
	public void setLastUpdateDate(Date arg)
	{
		setValue(LASTUPDATEDATE, arg);
	}

	//#CM704889
	/**
	 * Fetch Last update date
	 * @return Last update date
	 */
	public Date getLastUpdateDate()
	{
		return (Date)getValue(Movement.LASTUPDATEDATE);
	}

	//#CM704890
	/**
	 * Set value to Last update name
	 * @param arg Last update name to be set
	 */
	public void setLastUpdatePname(String arg)
	{
		setValue(LASTUPDATEPNAME, arg);
	}

	//#CM704891
	/**
	 * Fetch Last update name
	 * @return Last update name
	 */
	public String getLastUpdatePname()
	{
		return getValue(Movement.LASTUPDATEPNAME).toString();
	}


	//#CM704892
	/**
	 * @see AbstractEntity#getTablename()
	 */
	public String getTablename()
	{
		return TABLE_NAME ;
	}

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
//#CM704893
/**
	 * Generate the reproduction of the object.
	 * @return Copy of present object
	 */



	public Object clone()
	{
		Movement dest = new Movement();

		dest.setAreaNo(getAreaNo());
		dest.setBatchNo(getBatchNo());
		dest.setBeginningFlag(getBeginningFlag());
		dest.setBundleEnteringQty(getBundleEnteringQty());
		dest.setBundleItf(getBundleItf());
		dest.setCasePieceFlag(getCasePieceFlag());
		dest.setCollectJobNo(getCollectJobNo());
		dest.setConsignorCode(getConsignorCode());
		dest.setConsignorName(getConsignorName());
		dest.setEnteringQty(getEnteringQty());
		dest.setItemCode(getItemCode());
		dest.setItemName1(getItemName1());
		dest.setItf(getItf());
		dest.setJobNo(getJobNo());
		dest.setJobType(getJobType());
		dest.setLastUpdateDate(getLastUpdateDate());
		dest.setLastUpdatePname(getLastUpdatePname());
		dest.setLocationNo(getLocationNo());
		dest.setLotNo(getLotNo());
		dest.setPlanInformation(getPlanInformation());
		dest.setPlanQty(getPlanQty());
		dest.setPrintFlag(getPrintFlag());
		dest.setRegistDate(getRegistDate());
		dest.setRegistPname(getRegistPname());
		dest.setReportFlag(getReportFlag());
		dest.setResultAreaNo(getResultAreaNo());
		dest.setResultLocationNo(getResultLocationNo());
		dest.setResultLotNo(getResultLotNo());
		dest.setResultQty(getResultQty());
		dest.setResultUseByDate(getResultUseByDate());
		dest.setRetrievalTerminalNo(getRetrievalTerminalNo());
		dest.setRetrievalWorkerCode(getRetrievalWorkerCode());
		dest.setRetrievalWorkerName(getRetrievalWorkerName());
		dest.setShortageCnt(getShortageCnt());
		dest.setStatusFlag(getStatusFlag());
		dest.setStockId(getStockId());
		dest.setSupplierCode(getSupplierCode());
		dest.setSupplierName1(getSupplierName1());
		dest.setTerminalNo(getTerminalNo());
		dest.setUseByDate(getUseByDate());
		dest.setWorkerCode(getWorkerCode());
		dest.setWorkerName(getWorkerName());
		dest.setWorkFormFlag(getWorkFormFlag());

		return dest;
	}
	//#CM704894
	//------------------------------------------------------------
	//#CM704895
	// package methods
	//#CM704896
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704897
	//------------------------------------------------------------


	//#CM704898
	//------------------------------------------------------------
	//#CM704899
	// protected methods
	//#CM704900
	// use {@inheritDoc} in the comment, If the method is overridden.
	//#CM704901
	//------------------------------------------------------------


	//#CM704902
	//------------------------------------------------------------
	//#CM704903
	// private methods
	//#CM704904
	//------------------------------------------------------------
	//#CM704905
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
		lst.add(prefix + STOCKID);
		lst.add(prefix + AREANO);
		lst.add(prefix + LOCATIONNO);
		lst.add(prefix + STATUSFLAG);
		lst.add(prefix + BEGINNINGFLAG);
		lst.add(prefix + WORKDATE);
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
		lst.add(prefix + WORKFORMFLAG);
		lst.add(prefix + ITF);
		lst.add(prefix + BUNDLEITF);
		lst.add(prefix + USEBYDATE);
		lst.add(prefix + LOTNO);
		lst.add(prefix + PLANINFORMATION);
		lst.add(prefix + RESULTUSEBYDATE);
		lst.add(prefix + RESULTLOTNO);
		lst.add(prefix + RESULTAREANO);
		lst.add(prefix + RESULTLOCATIONNO);
		lst.add(prefix + REPORTFLAG);
		lst.add(prefix + PRINTFLAG);
		lst.add(prefix + BATCHNO);
		lst.add(prefix + WORKERCODE);
		lst.add(prefix + WORKERNAME);
		lst.add(prefix + TERMINALNO);
		lst.add(prefix + RETRIEVALWORKERCODE);
		lst.add(prefix + RETRIEVALWORKERNAME);
		lst.add(prefix + RETRIEVALTERMINALNO);
		lst.add(prefix + REGISTDATE);
		lst.add(prefix + REGISTPNAME);
		lst.add(prefix + LASTUPDATEDATE);
		lst.add(prefix + LASTUPDATEPNAME);
	}


	//#CM704906
	//------------------------------------------------------------
	//#CM704907
	// utility methods
	//#CM704908
	//------------------------------------------------------------
	//#CM704909
	/**
	 * Returns this class revision
	 * @return revision value as String
	 */
	public static String getVersion()
	{
		return "$Id: Movement.java,v 1.5 2006/11/16 02:15:42 suresh Exp $" ;
	}
}
