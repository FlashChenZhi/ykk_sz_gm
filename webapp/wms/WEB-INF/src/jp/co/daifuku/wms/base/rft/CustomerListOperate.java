package jp.co.daifuku.wms.base.rft;

//#CM700290
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

//#CM700291
//import
import java.sql.Connection;

import jp.co.daifuku.common.NotFoundException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.WorkingInformationSearchKey;
import jp.co.daifuku.wms.base.entity.WorkingInformation;


//#CM700292
/**
 * Designer : Y.Taki <BR>
 * Maker :   <BR>
 * <BR>
 * The class to process it to Receiving Inspection Customer list request (ID0121) and Shipping Inspection Customer list request (ID0520). <BR>
 * Succeed to <CODE>IdOperate</CODE> class, and mount necessary processing. <BR>
 * Receive Work plan date, Consignor Code, and Work flag as a parameter, and acquire Customer list information. <BR>
 * Make the Customer list file (Customer code ascending order). <BR>
 * <BR>
 * Customer list acquisition(<CODE>findCustomerAll()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive Work plan date, Consignor Code, and Work flag as a parameter, and return Customer list information. <BR>
 * <BR>
 * <BR>
 * Customer list file making(<CODE>createTableFile()</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Receive Customer list information and the file name (full path) as a parameter, and make the Customer list file. <BR>
 * <BR>
 * </DIR>
 * <BR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/07/21</TD><TD>K.Mori</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/11/14 06:08:57 $
 * @author  $Author: suresh $
 */
public class CustomerListOperate extends IdOperate
{
	//#CM700293
	// Class fields --------------------------------------------------
	//#CM700294
	// Class variables -----------------------------------------------
	//#CM700295
	// Constructors --------------------------------------------------
	//#CM700296
	/**
	 * Generate the instance. 
	 */
	public CustomerListOperate()
	{
	}
	//#CM700297
	/**
	 * <code>Connection</code> for the database connection is specified, and Generate the instance. 
	 * @param conn Database connection
	 */
	public CustomerListOperate(Connection conn)
	{
		wConn = conn ;
	}

	//#CM700298
	// Class method --------------------------------------------------
	//#CM700299
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/11/14 06:08:57 $");
	}

	//#CM700300
	// Public methods ------------------------------------------------
	//#CM700301
	/**
	 * Acquire the Customer list from Work information(dnworkinfo). <BR>
	 * Status flag(status_flag) is 0: From Stand by data<BR>
	 * In condition (Work flag,Work plan date,Consignor Code) of specifying it, acquire the Customer popup sheet in Customer code ascending order. <BR>
	 * However, consolidate the repetition data in one. The Customer code excludes blank data. <BR>
	 * @param  planDate Work plan date
	 * @param  consignorCode Consignor Code
	 * @param  findType Work flag
	 * @return Customer List information
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base. 
	 * @throws NotFoundException  It is notified when Customer information is not found. 
	 */
	public WorkingInformation[] findCustomerAll( String planDate , String consignorCode, String findType ) throws ReadWriteException, NotFoundException
	{
		WorkingInformationSearchKey workInfoSearchKey = new WorkingInformationSearchKey();

		//#CM700302
		//-----------------
		//#CM700303
		// Set Acquisition item
		//#CM700304
		//-----------------
		//#CM700305
		// Customer Code
		workInfoSearchKey.setCustomerCodeCollect();
		//#CM700306
		// Customer Name
		workInfoSearchKey.setCustomerName1Collect();

		//#CM700307
		//-----------------
		//#CM700308
		// Set Search condition
		//#CM700309
		//-----------------
		//#CM700310
		// Work flag
		workInfoSearchKey.setJobType( findType );
		//#CM700311
		// Work plan date
		workInfoSearchKey.setPlanDate( planDate );
		//#CM700312
		// Consignor Code
		workInfoSearchKey.setConsignorCode( consignorCode );
		//#CM700313
		// Status flag
		workInfoSearchKey.setStatusFlag( WorkingInformation.STATUS_FLAG_UNSTART );
		//#CM700314
		// Work start flag
		workInfoSearchKey.setBeginningFlag( WorkingInformation.BEGINNING_FLAG_STARTED );

		//#CM700315
		//-----------------
		//#CM700316
		// Consolidating condition set
		//#CM700317
		//-----------------
		workInfoSearchKey.setCustomerCodeGroup( 1 );
		workInfoSearchKey.setCustomerName1Group( 2 );
		
		//#CM700318
		//-----------------
		//#CM700319
		// The sorting order set
		//#CM700320
		//-----------------
		workInfoSearchKey.setCustomerCodeOrder( 1, true );
		workInfoSearchKey.setCustomerName1Order( 2, true );

		//#CM700321
		//-----------------
		//#CM700322
		// Retrieval processing
		//#CM700323
		//-----------------
		WorkingInformationHandler workInfoHandler = new WorkingInformationHandler( wConn );
		WorkingInformation[] workInfo = (WorkingInformation[])workInfoHandler.find( workInfoSearchKey );

		//#CM700324
		// When Work information cannot be acquired, throws NotFoundException. 
		if( workInfo == null || workInfo.length == 0 )
		{
			throw (new NotFoundException());
		}
		
		//#CM700325
		// Customer List information acquisition
		return workInfo;
	}

	//#CM700326
	// Package methods -----------------------------------------------
	//#CM700327
	// Protected methods ---------------------------------------------
	//#CM700328
	// Private methods -----------------------------------------------
}
//#CM700329
//end of class
