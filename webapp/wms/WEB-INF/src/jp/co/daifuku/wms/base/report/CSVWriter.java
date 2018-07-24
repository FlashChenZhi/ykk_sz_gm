// $Id: CSVWriter.java,v 1.5 2006/12/13 08:52:30 suresh Exp $
package jp.co.daifuku.wms.base.report;

//#CM670744
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.Locale;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.SimpleFormat;
import jp.co.daifuku.wms.base.dbhandler.SequenceHandler;

//#CM670745
/**
 * The parent class of the servlet which makes the image data file for the report. <BR>
 *<BR>
 * JOBID<BR>
 * Stock inquiry system WMS501 ~ <BR>
 * Storage system     WMS600 ~ <BR>
 * Picking system     WMS700 ~ <BR>
 *
 * <BR>
 * Caution : It is not possible to go if a part of item of this class is not suitable with PrintDirect of the wing system and do be careful when correcting it. 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2002/08/30</TD><TD>K.Nishiura</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 08:52:30 $
 * @author  $Author: suresh $
 */
public abstract class CSVWriter
{
	//#CM670746
	// Class fields --------------------------------------------------
	//#CM670747
	/**
	 * Data file name for Receiving Plan List(Cross/DC)
	 */
	public static final String FNAME_INSTOCK_PLAN = "instockplan";
	public static final String JOBID_INSTOCK_PLAN = "WMS000";
    /**
     * 入荷予定リスト(ｸﾛｽ/DC)用ヘッダー
     */
    public static final String HEADER_INSTOCK_PLAN =
      "astr,Consignor_code,Consignor_name,Plan_date,Supplier_code,Supplier_name1,Ticket_no,Line_no,Item_code,Item_name1," +
      "enteringqty,enteringbundleqty,hostplancaseqty,hostplanpieceqty,resultcaseqty,resultpieceqty,crossdc,statusFlag";

	//#CM670748
	/**
	 * Data file name for Receiving Plan List(TC)
	 */
	public static final String FNAME_INSTOCK_PLAN_TC = "instockplan_tc";
	public static final String JOBID_INSTOCK_PLAN_TC = "WMS004";
    /**
     * 入荷予定リスト(TC)用ヘッダー
     */
    public static final String HEADER_INSTOCK_PLAN_TC =
      "astr,Consignor_code,Consignor_name,Plan_date,Supplier_code,Supplier_name1,CustomerCode,CustomerName,Ticket_no,Line_no," +
      "Item_code,Item_name1,enteringqty,enteringbundleqty,hostplancaseqty,hostplanpieceqty,resultcaseqty,resultpieceqty,statusFlag";

	//#CM670749
	/**
	 * Data file name for Receiving Result List(Cross/DC)
	 */
	public static final String FNAME_INSTOCK_QTY = "instockqty";
	public static final String JOBID_INSTOCK_QTY = "WMS001";
    /**
     * 入荷実績リスト(ｸﾛｽ/DC)用ヘッダー
     */
    public static final String HEADER_INSTOCK_QTY =
      "astr,Consignor_code,Consignor_name,instockreceiveworkdate,instockreceiveplandate,Supplier_code,Supplier_name1,ticketno," +
      "lineno,itemcode,itemname,enteringqty,bundleqty,plancaseqty,planpieceqty,resultcaseqty,resultpieceqty,shortagecaseqty,shortagepieceqty,use_by_date,cross/dc";

	//#CM670750
	/**
	 * Data file name for Receiving Result List(TC)
	 */
	public static final String FNAME_INSTOCK_QTY_TC = "instockqty_tc";
	public static final String JOBID_INSTOCK_QTY_TC = "WMS005";
    /**
     * 入荷実績リスト(TC)用ヘッダー
     **/
    public static final String HEADER_INSTOCK_QTY_TC =
      "astr,Consignor_code,Consignor_name,instockreceiveworkdate,instockreceiveplandate,Supplier_code,Supplier_name1,customercode," +
      "customername,ticketno,lineno,itemcode,itemname,enteringqty,bundleqty,plancaseqty,planpieceqty,resultcaseqty,resultpieceqty,shortagecaseqty,shortagepieceqty,use_by_date";

	//#CM670751
	/**
	 * Data file name for Receiving Work List (Cross/DC)
	 */
	public static final String FNAME_INSTOCK_INSPECTION = "inspection";
	public static final String JOBID_INSTOCK_INSPECTION = "WMS002";
    /**
     * 入荷作業リスト(ｸﾛｽ/DC)用ヘッダー
     **/
    public static final String HEADER_INSTOCK_INSPECTION =
      "astr,Consignor_code,Consignor_name,instockreceiveplandate,Supplier_code,Supplier_name1,ticketno,lineno,itemcode,itemname," +
      "allinstockreceiveqty,enteringqty,bundleqty,plancaseqty,planpieceqty,resultcaseqty,resultpieceqty,use_by_date,cross/dc";
    
	//#CM670752
	/**
	 * Data file name for Receiving Work List (TC)
	 */
	public static final String FNAME_INSTOCK_INSPECTION_TC = "inspection_tc";
	public static final String JOBID_INSTOCK_INSPECTION_TC = "WMS006";
    /**
     * 入荷作業リスト(TC)用ヘッダー
     **/
    public static final String HEADER_INSTOCK_INSPECTION_TC = 
      "astr,Consignor_code,Consignor_name,instockreceiveplandate,Supplier_code,Supplier_name1,customercode,customername,ticketno," +
      "lineno,itemcode,itemname,allinstockreceiveqty,enteringqty,bundleqty,plancaseqty,planpieceqty,resultcaseqty,resultpieceqty,use_by_date";

	//#CM670753
	/**
	 * Data file name for Receiving Plan deletion List 
	 */
	public static final String FNAME_INSTOCK_DELETE = "inspectiondelete";
	public static final String JOBID_INSTOCK_DELETE = "WMS003";
    /**
     * 入荷予定削除一覧リスト用ヘッダー
     **/
    public static final String HEADER_INSTOCK_DELETE = 
      "astr,Consignor_code,Consignor_name,instockplandate,tcdcflag,Supplier_code,Supplier_name1,Ticket_no,Line_no,Item_code,Item_name1," +
      "Plan_qty,enteringqty,enteringbundleqty,hostplancaseqty,hostplanpieceqty";

	//#CM670754
	/**
	 * Data file name for Shipping plan list
	 */
	public static final String FNAME_SHIPPING_PLAN = "shippingplan";
	public static final String JOBID_SHIPPING_PLAN = "WMS010";
    /**
     * 出荷予定リスト用ヘッダー
     **/
    public static final String HEADER_SHIPPING_PLAN =
      "astr,ConsignorCode,ConsignorName,ShippingDay,CustomerCode,CustomerName,TicketNo,LineNo,ItemCode,ItemName,EnteringQty,BundleEnteringQty," +
      "PlanQty,CaseQty,PieceQty,ResultCaseQty,ResultPieceQty,statusFlag";
    
	//#CM670755
	/**
	 * Data file name for Shipping result list
	 */
	public static final String FNAME_SHIPPING_QTY = "shippingqty";
	public static final String JOBID_SHIPPING_QTY = "WMS011";
    /**
     * 出荷実績リスト用ヘッダー
     */
    public static final String HEADER_SHIPPING_QTY =
     "astr,ConsignorCode,ConsignorName,ShippingDay,ShippingPlanDate,CustomerCode,CustomerName,TicketNo,LineNo,ItemCode,ItemName,EnteringQty," +
     "BundleEnteringQty,PlanCaseQty,PlanPieceQty,ResultQty,ResultCaseQty,ResultPieceQty,ShortageCaseQty,ShortagePieceQty,UseByDate";
    
	//#CM670756
	/**
	 * Data file name for Shipping Plan deletion List 
	 */
	public static final String FNAME_SHIPPING_DELETE = "shippingdelete";
	public static final String JOBID_SHIPPING_DELETE = "WMS012";
    /**
     * 出荷予定削除一覧リスト用ヘッダー
     */
    public static final String HEADER_SHIPPING_DELETE = 
     "astr,ConsignorCode,ConsignorName,SupplierCode,SupplierName,ShippingPlanDate,TicketNo,LineNo,ItemCode,ItemName,EnteringQty,BundleEnteringQty," +
     "PlanQty,CaseQty,PieceQty,TcdcFlag";
    
	//#CM670757
	/**
	 * Data file name for Shipping work List 
	 */
	public static final String FNAME_SHIPPING_LIST = "shipping";
	public static final String JOBID_SHIPPING_LIST = "WMS013";
    /**
     * 出荷作業一覧リスト用ヘッダー
     **/
    public static final String HEADER_SHIPPING_LIST =
     "astr,ConsignorCode,ConsignorName,ShippingDay,CustomerCode,CustomerName,TicketNo,LineNo,ItemCode,ItemName,EnteringQty,BundleEnteringQty," +
     "PlanQty,CaseQty,PieceQty";
     
	//#CM670758
	/**
	 * Data file name for Sorting plan list
	 */
	public static final String FNAME_PICKING_PLAN = "pickingplan";
	public static final String JOBID_PICKING_PLAN = "WMS020";
    /**
     * 仕分予定リスト用ヘッダー
     **/
    public static final String HEADER_PICKING_PLAN = 
    "astr,consignorcode,consignorname,pickingplandate,itemcode,itemname,crossdcflag,planallqty,scrossdcflag,customercode,customername,casepickingplace," +
    "piecepickingplace,caseqty,bundleqty,hostplancaseqty,hostplanpieceqty,resultcaseqty,resultpieceqty,status,suppliercode,suppliername";

	//#CM670759
	/**
	 * Data file name for Sorting result list
	 */
	public static final String FNAME_PICKING_QTY = "pickingqty";
	public static final String JOBID_PICKING_QTY = "WMS021";
    /**
     * 仕分実績リスト用ヘッダー
     */
    public static final String HEADER_PICKING_QTY = 
     "astr,consignorcode,consignorname,pickingdate,itemcode,itemname,cpf,crossdcflag,allcaseqty,allpieceqty,pickingplace,customercode,customername," +
     "scrossdcflag,caseqty,bundleqty,plancaseqty,planpieceqty,resultcaseqty,resultpieceqty,lostcaseqty,lostpieceqty,suppliercode,suppliername";

	//#CM670760
	/**
	 * Data file name for Sorting plan deletion list
	 */
	public static final String FNAME_PICKING_DELETE = "pickingdelete";
	public static final String JOBID_PICKING_DELETE = "WMS022";
    /**
     * 仕分予定削除一覧用ヘッダー
     */
    public static final String HEADER_PICKING_DELETE =
    "astr,consignorcode,consignorname,pickingplandate,itemcode,itemname,flag,hostplanallqty,caseqty,bundleqty,hostplancaseqty,hostplanpieceqty,pickingcount";

	//#CM670761
	/**
	 * Data file name for Customer wise sorting work list
	 */
	public static final String FNAME_PICKING_SETCUSTOMER = "pickingsetcustomer";
	public static final String JOBID_PICKING_SETCUSTOMER = "WMS023";
    /**
     * 出荷先別仕分作業リスト用ヘッダー
     */
    public static final String HEADER_PICKING_SETCUSTOMER =
    "astr,consignorcode,consignorname,pickingplandate,itemcode,itemname,cpf,crossdcflag,planallqty,pickingplace,customercode,customername,scpf,allqty," +
    "caseqty,bundleqty,plancaseqty,planpieceqty";

	//#CM670762
	/**
	 * Data file name for Worker wise Result inquiry
	 */
	public static final String FNAME_WORKER_QTY = "workerqtyinquiry";
	public static final String JOBID_WORKER_QTY = "WMS030";
    /**
     * 作業者別実績照会用ヘッダー
     */
    public static final String HEADER_WORKER_QTY =
    "astr,FromDate,ToDate,WorkDay,WorkerCode,WorkerName,JobType,WorkStart,WorkEnd,WorkTime,WorkQty,WorkCnt,WorkQtyHour,WorkCntHour,RftNo,GroupCondition";    
     
	//#CM670763
	/**
	 * Data file name for Pending check list
	 */
	public static final String FNAME_SHORTAGEINFO = "shortageinfoinquiry";
	public static final String JOBID_SHORTAGEINFO = "WMS031";
    /**
     * 欠品チェックリスト用ヘッダー
     */
    public static final String HEADER_SHORTAGEINFO =
     "astr,ConsignorCode,ConsignorName,PlanDate,ItemCode,ItemName,EnteringQty,BundleEnteringQty,PlanCaseQty,PlanPieceQty,EnableCaseQty,EnablePieceQty," +
     "ShortageCaseQty,ShortagePieceQty,CaseLocation,PieceLocation,CaseOrderNo,PieceOrderNo,CustomerName";
    
	//#CM670764
	/**
	 * Data file name for Item wise Stock list
	 */
	public static final String FNAME_ITEM_ITEMWORK = "itemworkinginq";
	public static final String JOBID_ITEM_ITEMWORK = "WMS501";
    /**
     * 商品別在庫リスト用ヘッダー
     */
    public static final String HEADER_ITEM_ITEMWORK =
     "astr,ConsignorCode,ConsignorName,ItemCode,ItemName,EnteringQty,BundleEnteringQty,StockCaseQty,StockPieceQty,AllocationCaseQty,AllocationPieceQty,Flag,Location," +
     "CaseItf,PieceItf,StorageDate,StorageTime,UseByDate";
    
	//#CM670765
	/**
	 * Data file name for Location wise Stock list
	 */
	public static final String FNAME_ITEM_LOCATIONWORK = "locationworkinginq";
	public static final String JOBID_ITEM_LOCATIONWORK = "WMS502";
    /**
     * 棚別在庫リスト用ヘッダー
     */
    public static final String HEADER_ITEM_LOCATIONWORK =
     "astr,ConsignorCode,ConsignorName,Location,ItemCode,ItemName,EnteringQty,BundleEnteringQty,Stock,StockPieceQty,AllocationCaseQty,AllocationPieceQty,Flag,CaseItf," +
     "PieceItf,StorageDate,StorageTime,UseByDate";
     
	//#CM670766
	/**
	 * Data file name for Long-term stay item list output
	 */
	public static final String FNAME_ITEM_DEADSTOCK = "deadstockinq";
	public static final String JOBID_ITEM_DEADSTOCK = "WMS503";
    /**
     * 長期滞留品リスト出力用ヘッダー
     */
    public static final String HEADER_ITEM_DEADSTOCK =
    "astr,consignorcode,consignorname,stockday,stocktime,itemcode,itemname,kubun,locat,enteringqty,enteringbundleqty,caseqty,pieceqty,itf,bundleitf,use_by_date";

	//#CM670767
	/**
	 * Data file name for Printing Inventory filling in list
	 */
	public static final String FNAME_INVENTORY = "inventoryinq";
	public static final String JOBID_INVENTORY = "WMS504";
    /**
     * 棚卸記入リスト発行用ヘッダー
     */
    public static final String HEADER_INVENTORY =
    "astr,consignorcode,consignorname,locat,itemcode,itemname,kubun,enteringqty,enteringbundleqty,caseqty,pieceqty,caseitf,bundleitf,use_by_date,collectType";
    
	//#CM670768
	/**
	 * Data file name for Printing Unplanned Storage list
	 */
	public static final String FNAME_EX_STRAGE = "stocknoplanstrage";
	public static final String JOBID_EX_STRAGE = "WMS505";
    /**
     * 予定外入庫リスト発行用ヘッダー
     */
    public static final String HEADER_EX_STRAGE =
     "astr,ConsignorCode,ConsignorName,StorageDate,ItemCode,ItemName,Flag,StorageLocation,EnteringQty,BundleEnteringQty,StorageCaseQty,StoragePieceQty,CaseItf,PieceItf,UseByDate";
     
	//#CM670769
	/**
	 * Data file name for Printing Unplanned Picking list
	 */
	public static final String FNAME_EX_PICKING = "stocknoplanpicking";
	public static final String JOBID_EX_PICKING = "WMS506";
    /**
     * 予定外出庫リスト発行用ヘッダー
     */
    public static final String HEADER_EX_PICKING =
     "astr,ConsignorCode,ConsignorName,RetrievalDate,CustomerCode,CustomerName,ItemCode,ItemName,Flag,RetrievalLocation,EnteringQty,BundleEnteringQty,RetrievalCaseQty," +
     "RetrievalPieceQty,CaseItf,PieceItf,UseByDate";
      
	//#CM670770
	/**
	 * Data file name for Printing Stock movement work list
	 */
	public static final String FNAME_STOCKMOVE = "stockmove";
	public static final String JOBID_STOCKMOVE = "WMS507";
    /**
     * 在庫移動作業リスト発行用ヘッダー
     */
    public static final String HEADER_STOCKMOVE =
     "astr,consignorcode,consignorname,sourcelocationno,itemcode,itemname,enteringqty,movementcaseqty,movementqty,destlocationno,usebydate";
    
	//#CM670771
	/**
	 * Data file name for Printing Mobile rack Storage work list
	 */
	public static final String FNAME_IDMSTORAGE = "idmstorage";
	public static final String JOBID_IDMSTORAGE = "WMS508";
    /**
     * 移動ラック入庫作業リスト発行用ヘッダー
     */
    public static final String HEADER_IDMSTORAGE =
     "astr,ConsignorCode,ConsignorName,StorageDate,StorageLocation,ItemCode,ItemName,Flag,EnteringQty,BundleEnteringQty,StorageCaseQty,StoragePieceQty,CaseItf,PieceItf,UseByDate";
     
	//#CM670772
	/**
	 * Data file name for Printing Mobile rack Picking work list
	 */
	public static final String FNAME_IDMRETRIEVAL = "idmretrieval";
	public static final String JOBID_IDMRETRIEVAL = "WMS509";
    /**
     * 移動ラック出庫作業リスト発行用ヘッダー
     */
    public static final String HEADER_IDMRETRIEVAL =
     "astr,ConsignorCode,ConsignorName,RetrievalDate,CustomerCode,CustomerName,RetrievalLocation,ItemCode,ItemName,Flag,EnteringQty,BundleEnteringQty,RetrievalCaseQty," +
     "RetrievalPieceQty,CaseItf,PieceItf,UseByDate";

	//#CM670773
	/**
	 * Data file name for Storage Plan list
	 */
	public static final String FNAME_STRAGE_PLAN = "strageplan";
	public static final String JOBID_STRAGE_PLAN = "WMS601";
    /**
     * 入庫予定リスト用ヘッダー
     */
    public static final String HEADER_STRAGE_PLAN =
     "astr,consignorcode,consignorname,storageplandate,itemcode,itemname,Flag,storagecaselocation,storagepiecelocation,enteringqty,bundleenteringqty,hostplancaseqty,hostplanpieceqty," +
     "resultcaseqty,resultpieceqty,status,caseitf,bundleitf";
     
	//#CM670774
	/**
	 * Data file name for Storage order book list
	 */
	public static final String FNAME_STRAGE_WORK = "stragereprint";
	public static final String JOBID_STRAGE_WORK = "WMS602";
    /**
     * 入庫指図書リスト用ヘッダー
     */
    public static final String HEADER_STRAGE_WORK =
    "astr,ConsignorCode,ConsignorName,StoragePlanDate,ItemCode,ItemName,Flag,StorageLocation,EnteringQty,BundleEnteringQty,StorageTotal,StorageCaseQty,StoragePieceQty,ResultCaseQty," +
    "ResultPieceQty,UseByDate,CaseItf,BundleItf";

	//#CM670775
	/**
	 * Data file name for Storage Result list
	 */
	public static final String FNAME_STRAGE_QTY = "strageqty";
	public static final String JOBID_STRAGE_QTY = "WMS603";
    /**
     * 入庫実績リスト用ヘッダー
     */
    public static final String HEADER_STRAGE_QTY =
    "astr,consignorcode,consignorname,storagedate,planstoragedate,itemcode,itemname,kubun,location,enteringqty,enteringbundleqty,plancaseqty,planpieceqty,resultcaseqty,resultpieceqty," +
    "shortageqty,shortagepieceqty,caseitf,bundleitf,use_by_date";
    
	//#CM670776
	/**
	 * Data file name for Storage plan deletion list
	 */
	public static final String FNAME_STRAGE_DELETE = "strageplandelete";
	public static final String JOBID_STRAGE_DELETE = "WMS604";
    /**
     * 入庫予定削除リスト用ヘッダー
     */
    public static final String HEADER_STRAGE_DELETE =
     "astr,consignorcode,consignorname,plandate,itemcode,itemname,caselocation,piecelocation,enteringqty,bundleenteringqty,totalqty,caseqty,pieceqty,caseitf,bundleitf";

	//#CM670777
	/**
	 * Data file name for Inventory work list
	 */
	public static final String FNAME_INVENTORY_WORK = "inventorywork";
	public static final String JOBID_INVENTORY_WORK = "WMS605";
    /**
     * 棚卸作業一覧リスト用ヘッダー
     */
    public static final String HEADER_INVENTORY_WORK = 
     "astr,consignorcode,consignorname,location,itemcode,itemname,enteringqty,enteringbundleqty,inventorycheckcaseqty,inventorycheckpieceqty,stockcaseqty,stockpieceqty,use_by_date";

	//#CM670778
	/**
	 * Data file name for Start Order wise Picking
	 */
	public static final String FNAME_RETRIEVAL_ORDERWORK = "retrievalorderstartprint";
	public static final String JOBID_RETRIEVAL_ORDERWORK = "WMS701";
    /**
     * オーダーNo.別出荷開始用ヘッダー
     */
    public static final String HEADER_RETRIEVAL_ORDERWORK =
    "astr,casepieceflag,consignorcode,consignorname,retrievalplandate,customercode,customername,orderno,location,itemcode,itemname,kubun,planall,enteringqty,enteringbundleqty,plancaseqty," +
    "planpieceqty,use_by_date,caseitf,bundleitf";
    
	//#CM670779
	/**
	 * Data file name for Start Item wise Picking
	 */
	public static final String FNAME_RETRIEVAL_ITEMWORK = "retrievalitemstartprint";
	public static final String JOBID_RETRIEVAL_ITEMWORK = "WMS702";
    /**
     * アイテム別出庫開始用ヘッダー
     */
    public static final String HEADER_RETRIEVAL_ITEMWORK =
     "astr,casepieceflag,consignorcode,consignorname,retrievalplandate,location,itemcode,itemname,kubun,planall,enteringqty,enteringbundleqty,plancaseqty,planpieceqty,use_by_date,caseitf,bundleitf";

	//#CM670780
	/**
	 * Data file name for Order wise Picking plan list output
	 */
	public static final String FNAME_RETRIEVAL_ORDERPLAN = "retrievalorderplanlistprint";
	public static final String JOBID_RETRIEVAL_ORDERPLAN = "WMS703";
    /**
     * オーダーNO.別出庫予定リスト出力用ヘッダー
     */
    public static final String HEADER_RETRIEVAL_ORDERPLAN =
     "astr,consignorcode,consignorname,planday,customercode,customername,caseorder,pieceorder,itemcode,itemname,casepieceflag,enteringqty,enteringbundleqty,hostplancaseqty,hostplanpieceqty,retrievalcaseqty," +
     "retrievalpieceqty,caselocation,piecelocation,status";
    
	//#CM670781
	/**
	 * Data file name for Item wise Picking plan list output
	 */
	public static final String FNAME_RETRIEVAL_ITEMPLAN = "retrievalitemplanlistprint";
	public static final String JOBID_RETRIEVAL_ITEMPLAN = "WMS704";
    /**
     * アイテム別出庫予定リスト出力用ヘッダー
     */
    public static final String HEADER_RETRIEVAL_ITEMPLAN =
     "astr,consignorcode,consignorname,planday,itemcode,itemname,casepieceflag,caseqty,bundleqty,hostplancaseqty,hostplanpieceqty,retrievalcaseqty,retrievalpieceqty,caselocation,piecelocation,status";     

	//#CM670782
	/**
	 * Data file name for Picking plan deletion list
	 */
	public static final String FNAME_RETRIEVAL_DELETE = "retrievalplandelete";
	public static final String JOBID_RETRIEVAL_DELETE = "WMS705";
    /**
     * 出庫予定削除リスト用ヘッダー
     */
    public static final String HEADER_RETRIEVAL_DELETE =
     "astr,consignorcode,consignorname,plandate,itemcode,itemname,allqty,enteringqty,enteringbundleqty,hostplancaseqty,hostplanpieceqty,caselocation,piecelocation,caseorderno,pieceorderno"; 
     
	//#CM670783
	/**
	 * Data file name for Order wise Picking Result list output
	 */
	public static final String FNAME_RETRIEVAL_ORDERQTY = "retrievalorderqtylistprint";
	public static final String JOBID_RETRIEVAL_ORDERQTY = "WMS706";
    /**
     * オーダーNo.別出庫実績リスト出力用ヘッダー
     */
    public static final String HEADER_RETRIEVAL_ORDERQTY =
    "astr,consignorcode,consignorname,workday,workplanday,customercode,customername,oderno,itemcode,itemname,casepieceflag,caseqty,bundleenteringqty,workplancaseqty," +
    "workplanpieceqty,resultcaseqty,resultpieceqty,shortagecaseqty,shortagepieceqty,location,use_by_date";

	//#CM670784
	/**
	 * Data file name for Item wise Picking Result list output
	 */
	public static final String FNAME_RETRIEVAL_ITEMQTY = "retrievalitemqtylistprint";
	public static final String JOBID_RETRIEVAL_ITEMQTY = "WMS707";
    /**
     * アイテム別出庫実績リスト出力用ヘッダー
     */
    public static final String HEADER_RETRIEVAL_ITEMQTY =
     "astr,consignorcode,consignorname,workday,workplanday,itemcode,itemname,casepieceflag,caseqty,bundleenteringqty,workplancaseqty,workplanpieceqty,resultcaseqty," +
     "resultpieceqty,shortagecaseqty,shortagepieceqty,location,use_by_date"; 
     
	//#CM670785
	/**
	 * Data file name for Pending list : Item output
	 */
	public static final String FNAME_SHORTAGE_ITEM = "retrievalitemshortagelist";
	public static final String JOBID_SHORTAGE_ITEM = "WMS708";
    /**
     * 欠品リスト:アイテム出力用ヘッダー
     */
    public static final String HEADER_SHORTAGE_ITEM =
     "astr,CasePiece,ConsignorCode,ConsignorName,PlanDate,ItemCode,ItemName,CasePieceFlag,PlanQty,EnteringQty,BundleEnteringQty,PlanCaseQty,PlanPieceQty,ShortageQty,Location";
     
	//#CM670786
	/**
	 * Data file name for Pending list : Order output
	 */
	public static final String FNAME_SHORTAGE_ORDER = "retrievalordershortagelist";
	public static final String JOBID_SHORTAGE_ORDER = "WMS709";
    /**
     * 欠品リスト:オーダー出力用ヘッダー
     */
    public static final String HEADER_SHORTAGE_ORDER =
     "astr,CasePiece,ConsignorCode,ConsignorName,PlanDate,OrderNo,CustomerCode,CustomerName,ItemCode,ItemName,CasePieceFlag,PlanQty,ShortageQty,Location"; 
     
	//#CM670787
	/**
	 * Data file name for Printing AS/RS Unplanned Storage list
	 */
	public static final String FNAME_ASRSSTORAGE = "asrsstorage";
	public static final String JOBID_ASRSSTORAGE = "WMS801";
    /**
     * ＡＳＲＳ予定外入庫リスト発行用ヘッダー
     */
    public static final String HEADER_ASRSSTORAGE =
    "astr,Station,JobNo,LocationNo,ConsignorCode,ConsignorName,ItemCode,ItemName,UseByDate,StorageCaseQty,StoragePieceQty,Flag,CaseItf,PieceItf,CaseEnteringQty,BundleEnteringQty"; 
    
	//#CM670788
	/**
	 * Data file name for Printing AS/RS Unplanned Picking list
	 */
	public static final String FNAME_ASRSRETRIEVAL = "asrsretrieval";
	public static final String JOBID_ASRSRETRIEVAL = "WMS803";
    /**
     * ＡＳＲＳ予定外出庫リスト発行用ヘッダー
     */
    public static final String HEADER_ASRSRETRIEVAL =
     "astr,Station,ConsignorCode,ConsignorName,CustomerCode,CustomerName,WorkNo,RetrievalLocation,ItemCode,ItemName,Flag,UseByDate,EnteringQty,BundleEnteringQty,RetrievalCaseQty," +
     "RetrievalPieceQty,CaseItf,PieceItf";

	//#CM670789
	/**
	 * Data file name for Printing AS/RS Storage list
	 */
	public static final String FNAME_ASRSITEMSTORAGE = "asrsitemstorage";
	public static final String JOBID_ASRSITEMSTORAGE = "WMS804";
    /**
     * ＡＳＲＳ入庫リスト発行用ヘッダー
     */
    public static final String HEADER_ASRSITEMSTORAGE =
    "astr,Station,JobNo,LocationNo,ConsignorCode,ConsignorName,ItemCode,ItemName,PlanDate,UseByDate,StorageCaseQty,StoragePieceQty,Flag,CaseItf,PieceItf";
    
	//#CM670790
	/**
	 * Data file name for Printing Stock confirmation setting list
	 */
	public static final String FNAME_INVENTORYCHECK = "inventorycheck";
	public static final String JOBID_INVENTORYCHECK = "WMS805";
    /**
     * 在庫確認設定リスト発行用ヘッダー
     */
     public static final String HEADER_INVENTORYCHECK =
     "astr,Station,ConsignorCode,ConsignorName,WorkNo,RetrievalLocation,ItemCode,ItemName,Flag,EnteringQty,BundleEnteringQty,RetrievalCaseQty,RetrievalPieceQty," +
     "CaseItf,PieceItf,UseByDate";
     
	//#CM670791
	/**
	 * Data file name for Printing AS/RS empty location list
	 */
	public static final String FNAME_EMPTYSHELF = "asrsemptyshelf";
	public static final String JOBID_EMPTYSHELF = "WMS806";
    /**
     * ＡＳＲＳ空欄リスト発行用ヘッダー
     */
    public static final String HEADER_EMPTYSHELF =
     "astr,warehouse,rm,shelfnumber";
     
	//#CM670792
	/**
	 * Data file name for Printing Work maintenance list
	 */
	public static final String FNAME_WORKMAINTENANCE = "asworkmaintenancelist";
	public static final String JOBID_WORKMAINTENANCE = "WMS807";
    /**
     * 作業メンテナンスリスト発行用ヘッダー
     */
    public static final String HEADER_WORKMAINTENANCE =
     "astr,fromLocation,toLocation,WorkNo,WorkType,RetrievalDetail,CmdStatus,CarryKey,ScheduleNo,Maintenance,Allocation,ConsignorCode,ConsignorName," +
     "ItemCode,ItemName,EnteringQty,BundleEnteringQty,StockCaseQty,StockPieceQty,PlanCaseQty,PlanPieceQty,CasePieceFlag,StorageDate,StorageTime,UseByDate";

	//#CM670793
	/**
	 * Data file name for Printing AS/RS item picking list
	 */
	public static final String FNAME_ASRSITEMRETRIEVAL = "asrsitemretrieval";
	public static final String JOBID_ASRSITEMRETRIEVAL = "WMS808";
    /**
     * ＡＳＲＳアイテムピッキングリスト発行用ヘッダー
     */
    public static final String HEADER_ASRSITEMRETRIEVAL =
     "astr,Station,ConsignorCode,ConsignorName,WorkNo,RetrievalLocation,ItemCode,ItemName,Flag,PlanDate,UseByDate,EnteringQty,BundleEnteringQty,RetrievalCaseQty,RetrievalPieceQty,CaseItf,PieceItf";
    
	//#CM670794
	/**
	 * Data file name for Printing AS/RS order picking list
	 */
	public static final String FNAME_ASRSORDERRETRIEVAL = "asrsorderretrieval";
	public static final String JOBID_ASRSORDERRETRIEVAL = "WMS809";
    /**
     * ＡＳＲＳオーダーピッキングリスト発行用ヘッダー
     */
    public static final String HEADER_ASRSORDERRETRIEVAL =
     "astr,Station,ConsignorCode,ConsignorName,CustomerCode,CustomerName,OrderNo,WorkNo,RetrievalLocation,ItemCode,ItemName,Flag,PlanDate,UseByDate,EnteringQty," +
     "BundleEnteringQty,RetrievalCaseQty,RetrievalPieceQty,CaseItf,PieceItf";
    
	//#CM670795
	/**
	 * Data file name for Printing AS/RS location wise Stock list
	 */
	public static final String FNAME_ASRSLOCATIONWORKINGINQUIRY = "asrslocationworkinginquiry";
	public static final String JOBID_ASRSLOCATIONWORKINGINQUIRY = "WMS810";
    /**
     *  ＡＳＲＳロケーション別在庫リスト発行用ヘッダー
     **/
     public static final String HEADER_ASRSLOCATIONWORKINGINQUIRY = 
     "astr,WareHouse,ConsignorCode,ConsignorName,Location,ItemCode,ItemName,EnteringQty,BundleEnteringQty,StockCaseQty,StockPieceQty,AllocationCaseQty," +
     "AllocationPieceQty,Flag,CaseItf,PieceItf,StorageDate,StorageTime,UseByDate";

	//#CM670796
	/**
	 * Extension of data file
	 */
	public static final String EXTENSION_DATA = ".txt";

	//#CM670797
	/**
	 * Extension of spool file for printing
	 */
	public static final String EXTENSION_SPL = ".spl";

	//#CM670798
	/**
	 * Delimitation of data(,)
	 */
	protected static final String tb = ",";
	
	//#CM670799
	/**
	 * Delimitation of record(CR,LF)
	 */
	protected static final String re = "\r\n";

	//#CM670800
	/**
	 * Asterisk (*) 
	 */
	protected static final String ASTRIX = "*" + tb ;

	//#CM670801
	// Class variables -----------------------------------------------
	//#CM670802
	/**
	 * <CODE>Connection</CODE> Object
	 */
	protected Connection wConn = null ;

	//#CM670803
	/**
	 * <CODE>Locale</CODE> Object
	 */
	protected Locale wLocale = null ;

	//#CM670804
	/**
	 * Store a detailed message of the error which occurred when the print is executed. 
	 */
	protected String wMessage = "";

	//#CM670805
	/**
	 * Print data
	 */
	protected StringBuffer wStrText = new StringBuffer();

	//#CM670806
	/**
	 * Data file name(Extension none)
	 */
	protected String wFileName = null;

	//#CM670807
	/**
	 * Print data header(Fixed item)
	 */
	protected String wHeadText = ASTRIX;

	//#CM670808
	/**
	 * Made the Print data file in Comma Separated Value format.
	 */
	protected PrintWriter wPWriter = null;

	//#CM670809
	/**
	 * Delimiter
	 * The delimiter of the parameter of the message of MessageDef When Exception is generated.
	 */
	protected String wDelim = MessageResource.DELIM ;

	//#CM670810
	// Class method --------------------------------------------------
	//#CM670811
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.5 $,$Date: 2006/12/13 08:52:30 $") ;
	}

	//#CM670812
	/**
	 * Format the character string. Set " at both ends of the character string when outputting it to the text file. <BR>
	 * However, return the character string with 0 bytes when the given character string is a null character string and 0 bytes. 
	 * @param  fmt Specify the format of target character string.
	 * @return Return the formatted character string. 
	 */
	public static String format(String fmt)
	{
		return (SimpleFormat.format(fmt));
	}

	//#CM670813
	// Constructors --------------------------------------------------
	//#CM670814
	/**
	 * It is the constructor which sets <CODE>Connection</CODE>. 
	 * @param conn    <CODE>Connection</CODE>
	 */
	public CSVWriter(Connection conn)
	{
		wConn = conn ;
	}

	//#CM670815
	/**
	 * It is the constructor which sets <CODE>Connection</CODE> and <CODE>Locale</CODE>.
	 * @param conn    <CODE>Connection</CODE>
	 * @param locale <CODE>Locale</CODE>
	 */
	public CSVWriter(Connection conn, Locale locale)
	{
		wConn = conn ;
		setLocale(locale); 
	}

	//#CM670816
	// Public methods ------------------------------------------------
	//#CM670817
	/**
	 * Return the connection maintained in the instance. 
	 * @return    wConn  <CODE>Connection</CODE>
	 */
	public Connection getConnection()
	{
		return wConn ;
	}

	//#CM670818
	/**
	 * Set the <CODE>Locale</CODE> Object.
	 * @param  locale <CODE>Locale</CODE>
	 */
	public void setLocale(Locale locale)
	{
		wLocale = locale ;
	}

	//#CM670819
	/**
	 * Get the <CODE>Locale</CODE> Object.
	 * @return  wLocale <CODE>Locale</CODE>
	 */
	public Locale getLocale()
	{
		return wLocale ;
	}

	//#CM670820
	/**
	 * Return the message.
	 * @return Message
	 */
	public String getMessage()
	{
		return wMessage;
	}

	//#CM670821
	// Package methods -----------------------------------------------

	//#CM670822
	// Protected methods ---------------------------------------------
	//#CM670823
	/**
	 * Make the data file for the print, and begin printing. 
	 * @return Return true when succeeding in the print and return false when failing. 
	 */
	protected abstract boolean startPrint();

	//#CM670824
	/**
	 * Set specified Message in the Message storage area. 
	 * @param msg Message
	 */
	protected void setMessage(String msg)
	{
		wMessage = msg;
	}

	//#CM670825
	/**
	 * Acquire sequence No to decide Data file name. 
	 * @return  Sequence No.
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	protected String getSequenceNumber() throws ReadWriteException
	{
		DecimalFormat dformat = new DecimalFormat("0000000000") ;

		SequenceHandler seqh = new SequenceHandler(wConn);
		int file_seqno = seqh.nextReportNumber();

		return dformat.format(file_seqno);
	}
	
	//#CM670826
	/**
	 * Generate the file name, file and create the PrintWriter instance.
	 * @param fileName File Name
	 * @return Return true when succeeding in the generation of the PrintWriter instance and return false when failing. 
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 */
	protected boolean createPrintWriter(String fileName) throws ReadWriteException
	{
		try
		{
			//#CM670827
			// Acquisition File Name of Print data File Name + Sequence No. + Extension
			wFileName = fileName + getSequenceNumber() + EXTENSION_DATA;
			String filePath = ReportOperation.DEFAULT_FPATH + wFileName;

			//#CM670828
			// Output File Name is set. 
			wPWriter = new PrintWriter(new FileWriter(filePath), true);
			
		}
		catch (IOException e)
		{
			//#CM670829
			// 6006027 = It failed in making Print data. {0}
			RmiMsgLogClient.write(new TraceHandler(6006027, e), this.getClass().getName());
			//#CM670830
			// 6007034 = It failed in the print. Refer to the log. 
			setMessage("6007034");
			return false;
		}
		return true;

	}
	
	//#CM670831
	/**
	 * Executed by specifying JOBID and calling UCXSingle. 
	 * 
	 * @param jobId JOBID which begins print
	 * @return Print success : True Print failure : False
	 */
	protected boolean executeUCX(String jobId)
	{
		if (!ReportOperation.executeUCX(jobId, wFileName))
		{
			//#CM670832
			// 6026077 Failed in the print execution processing in UCXSingle. 
			RmiMsgLogClient.write("6026077", this.getClass().getName());
			
			//#CM670833
			// It failed in the print. Refer to the log. 
			setMessage("6007034");
			return false;
		}
		
		return true;

	}

	//#CM670834
	// Private methods -----------------------------------------------
}
//#CM670835
//end of class
