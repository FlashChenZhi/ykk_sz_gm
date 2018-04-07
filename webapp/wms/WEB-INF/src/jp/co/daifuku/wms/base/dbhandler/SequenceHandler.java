// $Id: SequenceHandler.java,v 1.2 2006/11/15 04:25:39 kamala Exp $
package jp.co.daifuku.wms.base.dbhandler ;

//#CM708592
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import jp.co.daifuku.common.MessageResource;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DBFormat;
import jp.co.daifuku.common.text.SimpleFormat;

//#CM708593
/**
 * The class which acquires various order numbers such as work No and set unit keys. 
 * The acquisition of the order number uses the order object of ORACLE. 
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>Kaminishi</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/15 04:25:39 $
 * @author  $Author: kamala $
 */
public class SequenceHandler extends Object
{
	//#CM708594
	// Class fields --------------------------------------------------
	//#CM708595
	/**
	 * The field which shows the order object of Receiving plan unique key (INSTOCKPLANKEY). 
	 */
	public final String INSTOCKPLANKEY = "INSTOCKPLANKEY_SEQ";
	public final String INSTOCK_FORMAT = "FM0000000000";	// FMは先頭の空白をカットするKeyWord
	public final String INSTOCK_PREFIX = "INSTPL" ;

	//#CM708596
	/**
	 * The field which shows the order object of Storage plan unique key (STORAGEPLANKEY). 
	 */
	public final String STORAGEPLANKEY = "STORAGEPLANKEY_SEQ";
	public final String STORAGE_FORMAT = "FM0000000000";	// FMは先頭の空白をカットするKeyWord
	public final String STORAGE_PREFIX = "STRGPL" ;

	//#CM708597
	/**
	 * The field which shows the order object of Picking plan unique key (RETRIEVALPLANKEY). 
	 */
	public final String RETRIEVALPLANKEY = "RETRIEVALPLANKEY_SEQ";
	public final String RETRIEVAL_FORMAT = "FM0000000000";	// FMは先頭の空白をカットするKeyWord
	public final String RETRIEVAL_PREFIX = "RTRVPL" ;

	//#CM708598
	/**
	 * The field which shows the order object of Sorting plan unique key (SORTINGPLANKEY). 
	 */
	public final String SORTINGPLANKEY = "SORTINGPLANKEY_SEQ";
	public final String SORTING_FORMAT = "FM0000000000";	// FMは先頭の空白をカットするKeyWord
	public final String SORTING_PREFIX = "STNGPL" ;

	//#CM708599
	/**
	 * The field which shows the order object of Shipping plan unique key (SHIPPINGPLANKEY). 
	 */
	public final String SHIPPINGPLANKEY = "SHIPPINGPLANKEY_SEQ";
	public final String SHIPPING_FORMAT = "FM0000000000";	// FMは先頭の空白をカットするKeyWord
	public final String SHIPPING_PREFIX = "SHIPPL" ;

	//#CM708600
	/**
	 * The field which shows the order object of batch No(NOPLAN_BATNO) for the results of no plan work. 
	 */
	public final String NOPLAN_BATNO = "NOPLAN_BATCHNO_SEQ";
	public final String NOPLAN_BATNO_FORMAT = "FM00000000";	// FMは先頭の空白をカットするKeyWord
	public final String NOPLAN_BATNO_PREFIX = "" ;

	//#CM708601
	/**
	 * The field which shows the order object of Receiving plan batch No. (INSTOCKPLANBATNO). 
	 */
	public final String INSTOCKPLANBATNO = "INSTOCKPLAN_BATCHNO_SEQ";
	public final String INSTOCKBATNO_FORMAT = "FM00000000";	// FMは先頭の空白をカットするKeyWord
	public final String INSTOCKBATNO_PREFIX = "" ;

	//#CM708602
	/**
	 * The field which shows the order object of Storage plan batch No. (STORAGEPLANBATNO). 
	 */
	public final String STORAGEPLANBATNO = "STORAGEPLAN_BATCHNO_SEQ";
	public final String STORAGEBATNO_FORMAT = "FM00000000";	// FMは先頭の空白をカットするKeyWord
	public final String STORAGEBATNO_PREFIX = "" ;

	//#CM708603
	/**
	 * The field which shows the order object of Retrieval plan batch No. (RETRIEVALPLANBATNO). 
	 */
	public final String RETRIEVALPLANBATNO = "RETRIEVALPLAN_BATCHNO_SEQ";
	public final String RETRIEVALBATNO_FORMAT = "FM00000000";	// FMは先頭の空白をカットするKeyWord
	public final String RETRIEVALBATNO_PREFIX = "" ;

	//#CM708604
	/**
	 * The field which shows the order object of Sorting plan batch No. (SORTINGPLANBATNO). 
	 */
	public final String SORTINGPLANBATNO = "SORTINGPLAN_BATCHNO_SEQ";
	public final String SORTINGBATNO_FORMAT = "FM00000000";	// FMは先頭の空白をカットするKeyWord
	public final String SORTINGBATNO_PREFIX = "" ;

	//#CM708605
	/**
	 * The field which shows the order object of Shipping plan batch No. (SHIPPINGPLANBATNO). 
	 */
	public final String SHIPPINGPLANBATNO = "SHIPPINGPLAN_BATCHNO_SEQ";
	public final String SHIPPINGBATNO_FORMAT = "FM00000000";	// FMは先頭の空白をカットするKeyWord
	public final String SHIPPINGBATNO_PREFIX = "" ;


	//#CM708606
	/**
	 * The field which shows the order object of stock ID(STOCK_ID). 
	 */
	public final String STOCKID = "STOCK_ID_SEQ";
	public final String STOCKID_FORMAT = "FM000000";		// FMは先頭の空白をカットするKeyWord
	public final String STOCKID_PREFIX = "ST" ;

	//#CM708607
	/**
	 * The field which shows the order object of work No(JOB_NO). 
	 */
	public final String JOBNO = "JOBNO_SEQ" ;
	public final String JOBNO_FORMAT = "FM00000000";		// FMは先頭の空白をカットするKeyWord
	public final String JOBNO_PREFIX = "" ;

//#CM708608
/* 2006/6/21 v2.6.0 START K.Shimizu Deletes for unused. */

	//#CM708609
	/**
	 * The field which shows the order object of movement work No. (MOVE_JOB_NO). 
	 */
	//#CM708610
	/*
	public final String MOVEJOBNO = "MOVE_JOB_NO_SEQ" ;
	public final String MOVEJOBNO_FORMAT = "FM000000";		// FM is KeyWord which cuts the first blank. 
	public final String MOVEJOBNO_PREFIX = "MV" ;
	*/
	/*
	public final String MOVEJOBNO = "MOVE_JOB_NO_SEQ" ;
	public final String MOVEJOBNO_FORMAT = "FM000000";		// FM is KeyWord which cuts the first blank. 
	public final String MOVEJOBNO_PREFIX = "MV" ;
	*/
//#CM708611
/* 2006/6/21 END */


	//#CM708612
	/**
	 * The field which shows the order object of report No. 
	 */
	public final String REPORTNO = "REPORTNO_SEQ";

	//#CM708613
	/**
	 * The field which shows the order object of next work information schedule unique key (NEXTPROCKEY). 
	 */
	public final String NEXTPROCKEY = "NEXTPROCKEY_SEQ";
	public final String NEXTPROC_FORMAT = "FM0000000000";	// FMは先頭の空白をカットするKeyWord
	public final String NEXTPROC_PREFIX = "NEXTPR" ;

	//#CM708614
	// AS/RS Package -----
	//#CM708615
	/**<jp>
	 * The field which shows the order object of transportation Key(Mckey). 
	 </jp>*/
	//#CM708616
	/**<en>
	 * Field: sequence object of CarryKey(Mckey)
	 </en>*/
	public final String CARRYKEY = "CARRYKEY_SEQ" ;
	public final String CARRYKEY_FORMAT = "FM00000000"; 

	//#CM708617
	/**<jp>
	 * The field which shows the order object of PaletteID. 
	 </jp>*/
	//#CM708618
	/**<en>
	 * Field: sequence object of PaletteID
	 </en>*/
	public final String PALETTEID = "PALETTEID_SEQ";
	public final String PALETTEID_FORMAT = "FM0000000"; 

	//#CM708619
	/**<jp>
	 * The field which shows the order object of work No. 
	 </jp>*/
	//#CM708620
	/**<en>
	 * Field: sequence object of work no.
	 </en>*/
	public final String WORKNO = "WORKNO_SEQ";
	public final String WORKNO_FORMAT = "FM00000000";

	//#CM708621
	/**<jp>
	 * The field which shows the order object of Storage work No. 
	 </jp>*/
	//#CM708622
	/**<en>
	 * Field: sequence object of storage work no.
	 </en>*/
	public final String STORAGE_WORKNO = "STORAGE_WORKNO_SEQ";
	public final String STORAGE_WORKNO_FORMAT = "FM00000000";

	//#CM708623
	/**<jp>
	 * The field which shows the order object of Picking work No. 
	 </jp>*/
	//#CM708624
	/**<en>
	 * Field: sequence object of retrieval work no.
	 </en>*/
	public final String RETRIEVAL_WORKNO = "RETRIEVAL_WORKNO_SEQ";
	public final String RETRIEVAL_WORKNO_FORMAT = "FM00000000";

	//#CM708625
	/**<jp>
	 * The field which shows the order object of schedule No. 
	 </jp>*/
	//#CM708626
	/**<en>
	 * Field: sequence object of schedule no.
	 </en>*/
	public final String SCHNO = "SCHNO_SEQ";
	public final String SCHNO_FORMAT = "FM000000000";

	//#CM708627
	/**<jp>
	 *The maximum frequency which loops by numbering about palette ID
	 *The maximum number of shelves which was able to be thought with AWC was set to an initial value. 
	 </jp>*/
	//#CM708628
	/**<en>
	 * MAX. number of looping time in getting number for pallet ID
	 *The MAX. location number, that AWC could possibly consider, has been set as initial value.
	 </en>*/
	private final int COUNT_MAX = 150000;
	//#CM708629
	// ----- AS/RS Package

	//#CM708630
	// Class variables -----------------------------------------------
	//#CM708631
	/**
	 * Connection instance for data base connection
	 */
	protected Connection wConn ;

	//#CM708632
	/**
	 * Use it for LogWrite when Exception is generated. 
	 */
	public StringWriter wSW = new StringWriter();

	//#CM708633
	/**
	 * Use it for LogWrite when Exception is generated. 
	 */
	public PrintWriter  wPW = new PrintWriter(wSW);

	//#CM708634
	/**
	 * Delimiter
	 * When Exception is generated, the delimiter of the parameter of the message of MessageDef. 
	 */
	public String wDelim = MessageResource.DELIM ;

	//#CM708635
	// Class method --------------------------------------------------
	//#CM708636
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/15 04:25:39 $") ;
	}

	//#CM708637
	// Constructors --------------------------------------------------
	//#CM708638
	/**
	 * Generate the instance specifying < code>Connection</code > for the data base connection. 
	 * @param conn Connection for data base connection
	 */
	public SequenceHandler(Connection conn)
	{
		setConnection(conn) ;
	}

	//#CM708639
	/**
	 * Set <code>Connection</code> for the data base connection. 
	 * @param conn Connection to be set
	 */
	public void setConnection(Connection conn)
	{
		wConn = conn ;
	}

	//#CM708640
	/**
	 * Acquire <code>Connection</code> for the data base connection. 
	 * @return <code>Connection</code> being maintained
	 */
	public Connection getConnection()
	{
		return(wConn) ;
	}

	//#CM708641
	// Public methods ------------------------------------------------
	//#CM708642
	/**
	 * Acquire Receiving plan unique key. Receiving plan unique key is shown by the character string of 16 digits. (For ex:INSTPL0000243070) 
	 * @return Receiving plan unique key
	 */
	public String nextInstockPlanKey() throws ReadWriteException
	{
        //#CM708643
        // Retrieved with acquired Receiving plan unique key. 
		String Seq_ISkey = null;
        int St_count = 1;

		InstockPlanSearchKey ssKey = new InstockPlanSearchKey() ;
        InstockPlanHandler tObj = new InstockPlanHandler(getConnection()) ;

		//#CM708644
		// Check whether acquisition Receiving plan unique key exists in the schedule of arrival of goods file. 
		//#CM708645
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_ISkey = getSequence(INSTOCKPLANKEY, INSTOCK_FORMAT, INSTOCK_PREFIX);
			ssKey.KeyClear();
			ssKey.setInstockPlanUkey( Seq_ISkey );
			ssKey.setInstockPlanUkeyCollect();
			St_count = tObj.count( ssKey );

		}

		return Seq_ISkey;
	}
	//#CM708646
	/**
	 * Acquire Storage plan unique key. Storage plan unique key is shown by the character string of 16 digits. (For ex:STRGPL0000243070) 
	 * @return Storage plan unique key
	 */
	public String nextStoragePlanKey() throws ReadWriteException
	{
        //#CM708647
        // Retrieved with acquired Storage plan unique key. 
		String Seq_STkey = null;
        int St_count = 1;

        StoragePlanSearchKey ssKey = new StoragePlanSearchKey() ;
        StoragePlanHandler tObj = new StoragePlanHandler(getConnection()) ;

		//#CM708648
		// Check whether acquisition Storage plan unique key exists in the stock schedule file. 
		//#CM708649
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_STkey = getSequence(STORAGEPLANKEY, STORAGE_FORMAT, STORAGE_PREFIX);
			ssKey.KeyClear ();
			ssKey.setStoragePlanUkey( Seq_STkey );
			ssKey.setStoragePlanUkeyCollect();
            St_count = tObj.count( ssKey );

		}

		return Seq_STkey;
	}

	//#CM708650
	/**
	 * Acquire Picking plan unique key. Picking plan unique key is shown by the character string of 16 digits. (For ex:RTRVPL0000243070) 
	 * @return Picking plan unique key
	 */
	public String nextRetrievalPlanKey() throws ReadWriteException
	{
        //#CM708651
        // It retrieves it with acquired Picking plan unique key. 
		String Seq_RTkey = null;
        int St_count = 1;

        RetrievalPlanSearchKey ssKey = new RetrievalPlanSearchKey() ;
        RetrievalPlanHandler tObj = new RetrievalPlanHandler(getConnection()) ;

		//#CM708652
		// Check whether acquisition Picking plan unique key exists in the due out file. 
		//#CM708653
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_RTkey = getSequence(RETRIEVALPLANKEY, RETRIEVAL_FORMAT, RETRIEVAL_PREFIX);
			ssKey.KeyClear();
			ssKey.setRetrievalPlanUkey( Seq_RTkey );
			ssKey.setRetrievalPlanUkeyCollect();
            St_count = tObj.count( ssKey );

		}

		return Seq_RTkey;
	}

	//#CM708654
	/**
	 * Acquire Sorting plan unique key. Sorting plan unique key is shown by the character string of 16 digits. (For ex:RTRVPL0000243070) 
	 * @return Sorting plan unique key
	 */
	public String nextSortingPlanKey() throws ReadWriteException
	{
        //#CM708655
        // Retrieved with acquired Sorting plan unique key. 
		String Seq_STkey = null;
        int St_count = 1;

        SortingPlanSearchKey ssKey = new SortingPlanSearchKey() ;
        SortingPlanHandler tObj = new SortingPlanHandler(getConnection()) ;

		//#CM708656
		// Check whether acquisition Sorting plan unique key exists in the sort schedule file. 
		//#CM708657
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_STkey = getSequence(SORTINGPLANKEY, SORTING_FORMAT, SORTING_PREFIX);
			ssKey.KeyClear();
			ssKey.setSortingPlanUkey( Seq_STkey );
			ssKey.setSortingPlanUkeyCollect();
            St_count = tObj.count( ssKey );

		}

		return Seq_STkey;
	}

	//#CM708658
	/**
	 * Acquire Shipping plan unique key. Shipping plan unique key is shown by the character string of 16 digits. (For ex:SHIPPL0000243070) 
	 * @return Shipping plan unique key
	 */
	public String nextShippingPlanKey() throws ReadWriteException
	{
        //#CM708659
        // It retrieves it with acquired Shipping plan unique key. 
		String Seq_ISkey = null;
        int St_count = 1;

		ShippingPlanSearchKey ssKey = new ShippingPlanSearchKey() ;
		ShippingPlanHandler  tObj = new ShippingPlanHandler(getConnection()) ;

		//#CM708660
		// Check whether acquisition Shipping plan unique key exists in the shipment schedule file. 
		//#CM708661
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_ISkey = getSequence(SHIPPINGPLANKEY, SHIPPING_FORMAT, SHIPPING_PREFIX);
			ssKey.KeyClear();
			ssKey.setShippingPlanUkey( Seq_ISkey );
			ssKey.setShippingPlanUkeyCollect();
            St_count = tObj.count( ssKey );

		}

		return Seq_ISkey;
	}
    
    //#CM708662
    /**
     * Batch No for results of no plan work making
     * @return No plan work batch no
     */
    public String nextNoPlanBatchNo() throws ReadWriteException
    {
		//#CM708663
		// Retrieved with acquired Batch No for results of no plan work making. 
        String Seq_ISkey = null;
		int St_count = 1;

		HostSendSearchKey ssKey = new HostSendSearchKey() ;
		HostSendHandler tObj = new HostSendHandler(getConnection()) ;

		//#CM708664
		// Acquisition Batch No for results of no plan work making . However, check whether to exist in HostSend. 
		//#CM708665
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_ISkey = getSequence(NOPLAN_BATNO, NOPLAN_BATNO_FORMAT, NOPLAN_BATNO_PREFIX);
			ssKey.KeyClear();
			ssKey.setBatchNo( Seq_ISkey );
			ssKey.setBatchNoCollect();
			St_count = tObj.count( ssKey );

		}

        return Seq_ISkey;
    }
    
	//#CM708666
	/**
	 * Acquire Receiving Plan batch  No.. Receiving Plan batch  No. is shown by the character string of eight digits. (For ex:00243070) 
	 * @return Receiving Plan batch No.
	 */
	public String nextInstockPlanBatchNo() throws ReadWriteException
	{
        //#CM708667
        // Retrieved by acquired Receiving Plan batch  No.
		String Seq_ISkey = null;
        int St_count = 1;

		InstockPlanSearchKey ssKey = new InstockPlanSearchKey() ;
        InstockPlanHandler tObj = new InstockPlanHandler(getConnection()) ;

		//#CM708668
		// Check whether acquisition Receiving Plan batch  No. exists in the Receiving plan file. 
		//#CM708669
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_ISkey = getSequence(INSTOCKPLANBATNO, INSTOCKBATNO_FORMAT, INSTOCKBATNO_PREFIX);
			ssKey.KeyClear();
			ssKey.setBatchNo( Seq_ISkey );
			ssKey.setBatchNoCollect();
            St_count = tObj.count( ssKey );

		}

		return Seq_ISkey;
	}

	//#CM708670
	/**
	 * Acquire Storage plan batch  No.. Storage plan batch  No. is shown by the character string of eight digits. (For ex:00243070) 
	 * @return Storage plan batch No.
	 */
	public String nextStoragePlanBatchNo() throws ReadWriteException
	{
        //#CM708671
        // Retrieved by acquired Storage plan batch  No.
		String Seq_STkey = null;
        int St_count = 1;

        StoragePlanSearchKey ssKey = new StoragePlanSearchKey() ;
        StoragePlanHandler tObj = new StoragePlanHandler(getConnection()) ;

		//#CM708672
		// Check whether acquisition Storage plan batch  No. exists in the storage plan file. 
		//#CM708673
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_STkey = getSequence(STORAGEPLANBATNO, STORAGEBATNO_FORMAT, STORAGEBATNO_PREFIX);
			ssKey.KeyClear();
			ssKey.setBatchNo( Seq_STkey );
			ssKey.setBatchNoCollect();
            St_count = tObj.count( ssKey );

		}

		return Seq_STkey;
	}

	//#CM708674
	/**
	 * Acquire Picking plan batch  No. Picking plan batch  No. is shown by the character string of eight digits. (For ex:00243070) 
	 * @return Picking plan batch No.
	 */
	public String nextRetrievalPlanBatchNo() throws ReadWriteException
	{
        //#CM708675
        // Retrieved by acquired Picking plan batch  No.. 
		String Seq_RTkey = null;
        int St_count = 1;

        RetrievalPlanSearchKey ssKey = new RetrievalPlanSearchKey() ;
        RetrievalPlanHandler tObj = new RetrievalPlanHandler(getConnection()) ;

		//#CM708676
		// Check whether acquisition Picking plan batch  No. exists in the picking file. 
		//#CM708677
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_RTkey = getSequence(RETRIEVALPLANBATNO, RETRIEVALBATNO_FORMAT, RETRIEVALBATNO_PREFIX);
			ssKey.KeyClear();
			ssKey.setBatchNo( Seq_RTkey );
			ssKey.setBatchNoCollect();
            St_count = tObj.count( ssKey );

		}

		return Seq_RTkey;
	}

	//#CM708678
	/**
	 * Acquire Sorting plan batch  No.. Sorting plan batch  No. is shown by the character string of eight digits. (For ex:00243070) 
	 * @return Sorting plan batch No.
	 */
	public String nextSortingPlanBatchNo() throws ReadWriteException
	{
        //#CM708679
        // Retrieved by acquired Sorting plan batch  No.. 
		String Seq_STkey = null;
        int St_count = 1;

        SortingPlanSearchKey ssKey = new SortingPlanSearchKey() ;
        SortingPlanHandler tObj = new SortingPlanHandler(getConnection()) ;

		//#CM708680
		// Check whether acquisition Sorting plan batch  No. exists in the sort plan file. 
		//#CM708681
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_STkey = getSequence(SORTINGPLANBATNO, SORTINGBATNO_FORMAT, SORTINGBATNO_PREFIX);
			ssKey.KeyClear();
			ssKey.setBatchNo( Seq_STkey );
			ssKey.setBatchNoCollect();
            St_count = tObj.count( ssKey );

		}

		return Seq_STkey;
	}

	//#CM708682
	/**
	 * Acquire Shipping plan batch  No.. Shipping plan batch  No. is shown by the character string of eight digits. (For ex:SHIPPL0000243070) 
	 * @return Shipping plan batch No.
	 */
	public String nextShippingPlanBatchNo() throws ReadWriteException
	{
        //#CM708683
        // Retrieved by acquired Shipping plan batch  No.. 
		String Seq_ISkey = null;
        int St_count = 1;

		ShippingPlanSearchKey ssKey = new ShippingPlanSearchKey() ;
		ShippingPlanHandler  tObj = new ShippingPlanHandler(getConnection()) ;

		//#CM708684
		// Check whether acquisition Shipping plan batch  No. exists in the shipment plan file. 
		//#CM708685
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_ISkey = getSequence(SHIPPINGPLANBATNO, SHIPPINGBATNO_FORMAT, SHIPPINGBATNO_PREFIX);
			ssKey.KeyClear();
			ssKey.setBatchNo( Seq_ISkey );
			ssKey.setBatchNoCollect();
            St_count = tObj.count( ssKey );

		}

		return Seq_ISkey;
	}

	//#CM708686
	/**
	 * Acquire stock ID. Stock ID is shown by the character string of eight digits. (For ex:ST023070) 
	 * @return Stock ID
	 */
	public String nextStockId() throws ReadWriteException
	{
        //#CM708687
        // Retrieved with acquired Stock ID. 
		String Seq_Id = null;
        int St_count = 1;

        StockSearchKey ssKey	= new StockSearchKey();
        StockHandler sObj = new StockHandler( getConnection() );

		//#CM708688
		// Check whether acquisition stock ID exists in the stock file. 
		//#CM708689
		// Acquire it again if it exists. 
		while( St_count != 0 ){
	
			Seq_Id = getSequence(STOCKID, STOCKID_FORMAT, STOCKID_PREFIX);
			ssKey.KeyClear();
			ssKey.setStockId( Seq_Id );
			ssKey.setStockIdCollect();
            St_count = sObj.count( ssKey );

		}

		return Seq_Id;
	}

	//#CM708690
	/**
	 * Acquire work No. Work No is shown by the character string of eight digits. (For ex:00243070) 
	 * @return Work No.
	 */
	public String nextJobNo() throws ReadWriteException
	{

        //#CM708691
        // Retrieved by acquired work No.
		String Seq_JobNo = null;
		int St_count = 1;

        WorkingInformationSearchKey ssKey  = new WorkingInformationSearchKey();
        WorkingInformationHandler fObj	= new WorkingInformationHandler(getConnection());

		//#CM708692
		// Check whether acquisition work No. exists in the work information file. 
		//#CM708693
		// Acquire it again if it exists. 
        //#CM708694
        // JOB_NO is checked. 
		while( St_count != 0 ){

			Seq_JobNo = getSequence(JOBNO, JOBNO_FORMAT) ;
			//#CM708695
			// JOB_NO is checked. 
			ssKey.KeyClear();
			ssKey.setJobNo( Seq_JobNo );
			ssKey.setJobNoCollect();
			St_count = fObj.count( ssKey );

		}

		return Seq_JobNo;
	}

//#CM708696
/* 2006/6/21 v2.6.0 START K.Shimizu Deletes for unused. */

	//#CM708697
	/**
	 * Acquire movement Work No. Movement Work No is shown by the character string of eight digits. 
	 * @return Movement Work No.
	 */
	//#CM708698
	/*
	public String nextMoveJobNo() throws ReadWriteException
	{
		// Retrieved by acquired Shipping plan batch  No.. 
		String Seq_ISkey = null;
		int St_count = 1;

		MovementSearchKey ssKey = new MovementSearchKey() ;
		MovementHandler  tObj = new MovementHandler(getConnection()) ;

		// Check whether acquisition Shipping plan batch  No. exists in the shipment plan file. 
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_ISkey = getSequence(MOVEJOBNO, MOVEJOBNO_FORMAT, MOVEJOBNO_PREFIX);
			ssKey.KeyClear();
			ssKey.setJobNo( Seq_ISkey );
			ssKey.setJobNoCollect();
			St_count = tObj.count( ssKey );

		}

		return Seq_ISkey;
	}
	*/
	/*
	public String nextMoveJobNo() throws ReadWriteException
	{
		// Retrieved by acquired Shipping plan batch  No.. 
		String Seq_ISkey = null;
		int St_count = 1;

		MovementSearchKey ssKey = new MovementSearchKey() ;
		MovementHandler  tObj = new MovementHandler(getConnection()) ;

		// Check whether acquisition Shipping plan batch  No. exists in the shipment plan file. 
		// Acquire it again if it exists. 
		while( St_count != 0 ){

			Seq_ISkey = getSequence(MOVEJOBNO, MOVEJOBNO_FORMAT, MOVEJOBNO_PREFIX);
			ssKey.KeyClear();
			ssKey.setJobNo( Seq_ISkey );
			ssKey.setJobNoCollect();
			St_count = tObj.count( ssKey );

		}

		return Seq_ISkey;
	}
	*/
//#CM708699
/* 2006/6/21 END */


	//#CM708700
	/**
	 * Acquire Next work information plan unique Key. Next work information plan unique Key is shown by the character string of 16 digits. (For ex:NEXTPR0000243070) 
	 * @return Next work information plan unique Key
	 */
	public String nextNextProcKey() throws ReadWriteException
	{
        //#CM708701
        // Retrieved with acquired next work information plan unique Key. 
		String Seq_ISkey = null;
        int St_count = 1;
		NextProcessInfoSearchKey ssKey = new NextProcessInfoSearchKey() ;
		NextProcessInfoHandler tObj = new NextProcessInfoHandler(getConnection()) ;

		//#CM708702
		// Check whether acquisition following work information schedule unique Key exists in the next work information file. 
		//#CM708703
		// Acquire it again if it exists. 
		while( St_count != 0 )
		{

			Seq_ISkey = getSequence(NEXTPROCKEY, NEXTPROC_FORMAT, NEXTPROC_PREFIX);
			ssKey.KeyClear();
			ssKey.setNextProcUkey( Seq_ISkey );
			ssKey.setNextProcUkeyCollect();
            St_count = tObj.count( ssKey );

		}

		return Seq_ISkey;
	}

	//#CM708704
	/**<jp>
	 * Acquire Transportation key. Transportation key is shown by the character string of eight digits. (For ex: "00243070") 
	 * @return Transportation key
	 </jp>*/
	//#CM708705
	/**<en>
	 * Gets CarryKey. This Key is to be represented by a string of 8 digits. (e.g., 00243070)
	 * @return Carry Key
	 </en>*/
	public String nextCarryKey() throws ReadWriteException
	{
		return getSequence(CARRYKEY, CARRYKEY_FORMAT);
	}

	//#CM708706
	/**<jp>
	 * Acquire Palette ID. 
	 * @return Palette ID
	 </jp>*/
	//#CM708707
	/**<en>
	 * Gets pallet ID.
	 * @return Pallet ID
	 </en>*/
	public int nextPaletteId() throws ReadWriteException
	{
		PaletteHandler plHandle = new PaletteHandler(wConn);
		PaletteSearchKey plKey = new PaletteSearchKey();
		int pltID = 0;

		for(int i = 0; i < COUNT_MAX; i++)
		{
			pltID = getSequence(PALETTEID);
			plKey.KeyClear();
			plKey.setPaletteId(pltID);
			//#CM708708
			//<jp>When same palette ID is not found</jp>
			//#CM708709
			//<en>If the same pallet ID cannot be found,</en>
			if(plHandle.count(plKey) == 0)
			{
				return pltID;
			}
		}
		//#CM708710
		//<jp>When it is not possible to number it</jp>
		//#CM708711
		//<jp>6016102 = A fatal error occurred. {0}</jp>
		//#CM708712
		//<en>If the number could not be obtained,</en>
		//#CM708713
		//<en>6016102 = Fatal error occured.{0}</en>
		RmiMsgLogClient.write("6016102"+ wDelim + "Error occured while getting the PALETTE ID. Count of for loop was over the COUNT_MAX." , "SequenceHandler");
		throw (new ReadWriteException("6006039" + wDelim + "PALETTEID")) ;
	}

	//#CM708714
	/**<jp>
	 * Acquire Common work No. to Storage and Picking. Work No is shown by the character string of eight digits. (For ex: "00000001") 
	 * @return Work No.
	 </jp>*/
	//#CM708715
	/**<en>
	 * Gets common work no. for storage and retrrieval. This work no. is to be represented 
	 * as an 8 digit string. (e.g., 00000001)
	 * @return work no.
	 </en>*/
	public String nextWorkNumber() throws ReadWriteException
	{
		return getSequence(WORKNO, WORKNO_FORMAT);
	}

	//#CM708716
	/**<jp>
	 * Storage Acquire work No. Work No is shown by the character string of eight digits. (For ex: "10000001") 
	 * @return Storage Work No.
	 </jp>*/
	//#CM708717
	/**<en>
	 * Gets storage work no. This no. is to be represented as an 8 digit string. (e.g., 10000001)
	 * @return storage work no.
	 </en>*/
	public String nextStorageWorkNumber() throws ReadWriteException
	{
		return getSequence(STORAGE_WORKNO, STORAGE_WORKNO_FORMAT);
	}

	//#CM708718
	/**<jp>
	 * Picking Acquire work No. Work No is shown by the character string of eight digits. (For ex: "20000001") 
	 * @return Picking Work No.
	 </jp>*/
	//#CM708719
	/**<en>
	 * Gets retrieval work no. This work no. is to be represented as a string of 8digits. (e.g., 20000001)
	 * @return retrieval work no.
	 </en>*/
	public String nextRetrievalWorkNumber() throws ReadWriteException
	{
		return getSequence(RETRIEVAL_WORKNO, RETRIEVAL_WORKNO_FORMAT);
	}

	//#CM708720
	/**<jp>
	 * Acquire Schedule No. Schedule No. is shown by the character string of nine digits. 
	 * @return Schedule No.
	 </jp>*/
	//#CM708721
	/**<en>
	 * Gets schedule no. the schedule no. is to be represented as a string of 10 digits.
	 * @return schedule no.
	 </en>*/
	public String nextScheduleNumber() throws ReadWriteException
	{
		return getSequence(SCHNO, SCHNO_FORMAT);
	}

	//#CM708722
	/**<jp>
	 * Acquire Report No for the report. 
	 * @return Report No
	 </jp>*/
	//#CM708723
	/**<en>
	 * Gets the report no. for document.
	 * @return report no.
	 </en>*/
	public int nextReportNumber() throws ReadWriteException
	{
		return getSequence(REPORTNO);
	}
	
	//#CM708724
	/**<jp>
	 * Reset of Schedule number Sequence
	 * Used by the tightening day processing. 
	 </jp>*/
	//#CM708725
	/**<en>
	 * Resetting of Schedulenumber Sequence
	 * This is used in daily transactions.
	 </en>*/
	public void ResetSchedulerNumber()throws ReadWriteException
	{
		Statement stmt          = null;
		ResultSet rset          = null;
		String fmtSQL = "DROP SEQUENCE SCHNO_SEQ";
		try
		{
			stmt = wConn.createStatement();
			stmt.executeUpdate(fmtSQL);
		}
		catch (SQLException e)
		{
			//#CM708726
			//<jp>6006002 = Database error occurred.{0}</jp>
			//#CM708727
			//<en>6006002 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
			throw (new ReadWriteException("6006039" + wDelim + "SCHNO_SEQ")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM708728
				//<jp>6006002 = Database error occurred.{0}</jp>
				//#CM708729
				//<en>6006002 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
				throw (new ReadWriteException("6006039" + wDelim + "SCHNO_SEQ")) ;
			}
		}
		fmtSQL = "CREATE SEQUENCE SCHNO_SEQ START WITH 1 "
					+" MAXVALUE 999999999 CYCLE CACHE 10";
		try
		{
			stmt = wConn.createStatement();
			stmt.executeUpdate(fmtSQL);
		}
		catch (SQLException e)
		{
			//#CM708730
			//<jp>6006002 = Database error occurred.{0}</jp>
			//#CM708731
			//<en>6006002 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
			throw (new ReadWriteException("6006039" + wDelim + "SCHNO_SEQ")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM708732
				//<jp>6006002 = Database error occurred.{0}</jp>
				//#CM708733
				//<en>6006002 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
				throw (new ReadWriteException("6006039" + wDelim + "SCHNO_SEQ")) ;
			}
		}
		
	}

	//#CM708734
	/**<jp>
	 * Work  No. sequence reset
	 * Used by the tightening day processing. 
	 </jp>*/
	//#CM708735
	/**<en>
	 * Resetting of Storage work no Sequence
	 * This is used in daily transactions.
	 </en>*/
	public void ResetWorkNumber()throws ReadWriteException
	{
		Statement stmt          = null;
		ResultSet rset          = null;
		String fmtSQL = "DROP SEQUENCE WORKNO_SEQ";
		try
		{
			stmt = wConn.createStatement();
			stmt.executeUpdate(fmtSQL);
		}
		catch (SQLException e)
		{
			//#CM708736
			//<jp>6006002 = Database error occurred.{0}</jp>
			//#CM708737
			//<en>6006002 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
			throw (new ReadWriteException("6006039" + wDelim + "WORKNO_SEQ")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM708738
				//<jp>6006002 = Database error occurred.{0}</jp>
				//#CM708739
				//<en>6006002 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
				throw (new ReadWriteException("6006039" + wDelim + "WORKNO_SEQ")) ;
			}
		}
		fmtSQL = "CREATE SEQUENCE WORKNO_SEQ START WITH 1 MAXVALUE 9999999 CYCLE CACHE 10";
		try
		{
			stmt = wConn.createStatement();
			stmt.executeUpdate(fmtSQL);
		}
		catch (SQLException e)
		{
			//#CM708740
			//<jp>6006002 = Database error occurred.{0}</jp>
			//#CM708741
			//<en>6006002 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
			throw (new ReadWriteException("6006039" + wDelim + "WORKNO_SEQ")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM708742
				//<jp>6006002 = Database error occurred.{0}</jp>
				//#CM708743
				//<en>6006002 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
				throw (new ReadWriteException("6006039" + wDelim + "WORKNO_SEQ")) ;
			}
		}
	}

	//#CM708744
	/**<jp>
	 * Work  No. sequence (for storage) reset
	 * Used by the tightening day processing. 
	 </jp>*/
	//#CM708745
	/**<en>
	 * Resetting of Storage work no Sequence
	 * This is used in daily transactions.
	 </en>*/
	public void ResetStorageWorkNumber()throws ReadWriteException
	{
		Statement stmt          = null;
		ResultSet rset          = null;
		String fmtSQL = "DROP SEQUENCE STORAGE_WORKNO_SEQ";
		try
		{
			stmt = wConn.createStatement();
			stmt.executeUpdate(fmtSQL);
		}
		catch (SQLException e)
		{
			//#CM708746
			//<jp>6006002 = Database error occurred.{0}</jp>
			//#CM708747
			//<en>6006002 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
			throw (new ReadWriteException("6006039" + wDelim + "STORAGE_WORKNO_SEQ")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM708748
				//<jp>6006002 = Database error occurred.{0}</jp>
				//#CM708749
				//<en>6006002 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
				throw (new ReadWriteException("6006039" + wDelim + "STORAGE_WORKNO_SEQ")) ;
			}
		}
		fmtSQL = "CREATE SEQUENCE STORAGE_WORKNO_SEQ   START "
				 +"WITH 10000000 MINVALUE 10000000 MAXVALUE 19999999 CYCLE CACHE 10";
		try
		{
			stmt = wConn.createStatement();
			stmt.executeUpdate(fmtSQL);
		}
		catch (SQLException e)
		{
			//#CM708750
			//<jp>6006002 = Database error occurred.{0}</jp>
			//#CM708751
			//<en>6006002 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
			throw (new ReadWriteException("6006039" + wDelim + "STORAGE_WORKNO_SEQ")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM708752
				//<jp>6006002 = Database error occurred.{0}</jp>
				//#CM708753
				//<en>6006002 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
				throw (new ReadWriteException("6006039" + wDelim + "STORAGE_WORKNO_SEQ")) ;
			}
		}
	}

	//#CM708754
	/**<jp>
	 * Work  No. sequence (for picking) reset
	 * Used by the tightening day processing. 
	 </jp>*/
	//#CM708755
	/**<en>
	 * Resetting of Retrieval work no Sequence
	 * This is used in daily transactions.
	 </en>*/
	public void ResetRetrievalWorkNumber()throws ReadWriteException
	{
		Statement stmt          = null;
		ResultSet rset          = null;
		String fmtSQL = "DROP SEQUENCE RETRIEVAL_WORKNO_SEQ";
		try
		{
			stmt = wConn.createStatement();
			stmt.executeUpdate(fmtSQL);
		}
		catch (SQLException e)
		{
			//#CM708756
			//<jp>6006002 = Database error occurred.{0}</jp>
			//#CM708757
			//<en>6006002 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
			throw (new ReadWriteException("6006039" + wDelim + "RETRIEVAL_WORKNO_SEQ")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM708758
				//<jp>6006002 = Database error occurred.{0}</jp>
				//#CM708759
				//<en>6006002 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
				throw (new ReadWriteException("6006039" + wDelim + "RETRIEVAL_WORKNO_SEQ")) ;
			}
		}
		fmtSQL = "CREATE SEQUENCE RETRIEVAL_WORKNO_SEQ START WITH 20000000"
				 +"MINVALUE 20000000 MAXVALUE 29999999 CYCLE CACHE 10";
		try
		{
			stmt = wConn.createStatement();
			stmt.executeUpdate(fmtSQL);
		}
		catch (SQLException e)
		{
			//#CM708760
			//<jp>6006002 = Database error occurred.{0}</jp>
			//#CM708761
			//<en>6006002 = Database error occured.{0}</en>
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
			throw (new ReadWriteException("6006039" + wDelim + "RETRIEVAL_WORKNO_SEQ")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); }
				if (stmt != null) { stmt.close(); }
			}
			catch (SQLException e)
			{
				//#CM708762
				//<jp>6006002 = Database error occurred.{0}</jp>
				//#CM708763
				//<en>6006002 = Database error occured.{0}</en>
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
				throw (new ReadWriteException("6006039" + wDelim + "RETRIEVAL_WORKNO_SEQ")) ;
			}
		}
	}

	//#CM708764
	// Package methods -----------------------------------------------

	//#CM708765
	// Protected methods ---------------------------------------------

	//#CM708766
	// Private methods -----------------------------------------------
	//#CM708767
	/**
	 * Acquire the order number from the identifier directed with seqObj, format by the form specified with format, and return it. 
	 * @param The order object name to acquire the order number
	 * @param Format
	 * @param Prefix
	 * @return Edited the order number
	 * @throws ReadWriteException Notify the exception generated by the connection with the data base as it is. 
	 */
	private String getSequence(String seqObj, String fmt, String prefix) throws ReadWriteException
	{
		return (prefix + getSequence(seqObj, fmt)) ;
	}

	//#CM708768
	/**
	 * Acquire the order number from the identifier directed with seqObj, format by the form specified with format, and return it. 
	 * @param The order object name to acquire the order number
	 * @param Format
	 * @return Edited the order number
	 * @throws ReadWriteException Notify the exception generated by the connection with the data base as it is. 
	 */
	private String getSequence(String seqObj, String fmt) throws ReadWriteException
	{

		Statement stmt          = null;
		ResultSet rset          = null;
		String fmtSQL = "SELECT TO_CHAR({0}.NEXTVAL, {1}) AS SEQNUM FROM DUAL";
		Object[] fmtObj = new Object[2];
		String seqstr = null;

	 	try
	 	{
			stmt = wConn.createStatement();

			fmtObj[0] = seqObj;
			fmtObj[1] = DBFormat.format(fmt);

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			rset = stmt.executeQuery(sqlstring);
			rset.next();
			seqstr = rset.getString("SEQNUM");
		}
		
		catch (SQLException e)
		{
			//#CM708769
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "SequenceNo")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); rset = null; }
				if (stmt != null) { stmt.close(); stmt = null; }
			}
			catch (SQLException e)
			{
				//#CM708770
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
				throw (new ReadWriteException("6006002" + wDelim + "SequenceNo")) ;
			}
		}
		return seqstr;
	}

	//#CM708771
	/**
	 * Acquire the order number from the identifier directed with seqObj, format by the form specified with format, and return it. 
	 * @param The order object name to acquire the order number
	 * @throws ReadWriteException Notify the exception generated by the connection with the data base as it is. 
	 */
	private int getSequence(String seqObj) throws ReadWriteException
	{
		Statement stmt          = null;
		ResultSet rset          = null;
		String fmtSQL = "SELECT {0}.NEXTVAL AS SEQNUM FROM DUAL";
		Object[] fmtObj = new Object[1];
		int sequence = 0;

	 	try
	 	{
			stmt = wConn.createStatement();

			fmtObj[0] = seqObj;

			String sqlstring = SimpleFormat.format(fmtSQL, fmtObj) ;
			rset = stmt.executeQuery(sqlstring);
			rset.next();
			sequence = rset.getInt("SEQNUM");
		}
		catch (SQLException e)
		{
			//#CM708772
			// 6006002 = Database error occurred.{0}
			RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
			throw (new ReadWriteException("6006002" + wDelim + "SequenceNo")) ;
		}
		finally
		{
			try
			{
				if (rset != null) { rset.close(); rset = null; }
				if (stmt != null) { stmt.close(); stmt = null; }
			}
			catch (SQLException e)
			{
				//#CM708773
				// 6006002 = Database error occurred.{0}
				RmiMsgLogClient.write( new TraceHandler(6006002, e), "SequenceHandler" ) ;
				throw (new ReadWriteException("6006002" + wDelim + "SequenceNo")) ;
			}
		}
		return sequence;
	}
}
//#CM708774
//end of class

