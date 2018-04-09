// $Id: AsEmptyShelfListWriter.java,v 1.5 2006/12/13 09:03:20 suresh Exp $

//#CM43200
/*
 * Copyright 2000-2004 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.report;

import java.sql.Connection;
import java.util.Hashtable;

import jp.co.daifuku.wms.base.dbhandler.ShelfReportFinder;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.dbhandler.WareHouseHandler;
import jp.co.daifuku.wms.base.dbhandler.WareHouseSearchKey;
import jp.co.daifuku.wms.base.dbhandler.AisleHandler;
import jp.co.daifuku.wms.base.dbhandler.AisleSearchKey;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.base.entity.WareHouse;
import jp.co.daifuku.wms.base.entity.Aisle;
import jp.co.daifuku.wms.base.report.CSVWriter;
import jp.co.daifuku.wms.base.report.ReportOperation;
import jp.co.daifuku.wms.asrs.common.AsrsParam;

//#CM43201
/**
 * Designer : H.Ishizaki <BR>
 * Maker : H.Ishizaki <BR>
 * <BR>
 * < CODE>AsEmptyShelfListWriter</CODE > class makes the data file for the slit in an empty cell list, and executes the print. <BR>
 * Retrieve shelf information (Shelf) based on the condition set in < CODE>AsEmptyShelfListSCH</CODE > class.
 * Make the result as a slit file for an empty cell list. 
 * This class processes it as follows.  <BR>
 * <BR>
 * Data file making processing for slit(< CODE>startPrint() </ CODE > method) <BR>
 * <DIR>
 *	1.Retrieve the number of cases of shelf information (Shelf) on the condition set from < CODE>AsEmptyShelfListSCH</CODE > class. <BR>
 *	2.Make the slit file if the result is one or more. Return false and end in case of 0. <BR>
 *	3.Execute the printing job. <BR>
 *      4.Return true when the printing job is normal. <BR>
 *    Return false when the error occurs in the printing job. <BR>
 * <BR>
 * 	<Parameter>* Mandatory input <BR><DIR>
 * 	  Batch No * <BR></DIR>
 * <BR>
 * 	<Search condition> <BR><DIR>
 *    Batch No <BR></DIR>
 * <BR>
 *  < order retrieval> <BR><DIR>
 *    1.Ascending order of warehouse station No.<BR>
 *    2.Ascending order of parents station No.<BR>
 *    3.Ascending order of station No.<BR></DIR>
 * <BR>
 *	<print data> <BR><DIR>
 *	  Print entry		:DB item <BR>
 *	  Shelf			:State of + shelf of no. station + RM title machine No + state of warehouse (It is possible to use it). (empty shelf)<BR></DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/12/07</TD><TD>K.Toda</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.5 $, $Date: 2006/12/13 09:03:20 $
 * @author  $Author: suresh $
 */
public class AsEmptyShelfListWriter extends CSVWriter
{
	//#CM43202
	// Class fields --------------------------------------------------

	//#CM43203
	// Class variables -----------------------------------------------
	//#CM43204
	/**
	 * Batch No
	 */
	protected String[] wBatchNumber = null;

	//#CM43205
	/**
	 * Stock
	 */
	protected String wWareHouseNo;

	//#CM43206
	/**
	 * RM title machine No
	 */
	protected String wRackmasterNo;

	//#CM43207
	// Constructors --------------------------------------------------
	//#CM43208
	/**
	 * Construct the AsEmptyShelfListWriter object. <BR>
	 * Set the connection. <BR>
	 * 
	 * @param conn <CODE>Connection</CODE> Connection object with data base
	 */
	public AsEmptyShelfListWriter(Connection conn)
	{
		super(conn);
	}

	//#CM43209
	// Public methods ------------------------------------------------
	//#CM43210
	/**
	 * Acquire batch No.
	 * @return Batch No.
	 */
	public String[] getBatchNumber()
	{
		return wBatchNumber;
	}

	//#CM43211
	/**
	 * Set batch No.
	 * @param batchNumber Batch No. to be set
	 */
	public void setBatchNumber(String[] batchNumber)
	{
		wBatchNumber = batchNumber;
	}

	//#CM43212
	/**
	 * Set the warehouse.
	 * @param arg Warehouse to be set
	 */
	public void setWareHouseNo(String arg)
	{
		wWareHouseNo = arg;
	}

	//#CM43213
	/**
	 * Acquire the warehouse.
	 * @return Warehouse
	 */
	public String getWareHouseNo()
	{
		return wWareHouseNo;
	}

	//#CM43214
	/**
	 * Set RM title machine No.
	 * @param arg RM title machine No to be set
	 */
	public void setRackmasterNo(String arg)
	{
		wRackmasterNo = arg;
	}

	//#CM43215
	/**
	 * Acquire RM title machine No.
	 * @return RM title machine No
	 */
	public String getRackmasterNo()
	{
		return wRackmasterNo;
	}

	//#CM43216
	/**
	 * Acquire the number of cases of the print data.<BR>
	 * Use it to judge whether to do the print processing by this retrieval result.<BR>
	 * This method is used by the schedule class which processes the screen.<BR>
	 * 
	 * @return Print data number
	 * @throws ReadWriteException It is notified when abnormality occurs by the connection with the data base.
	 */
	public int count() throws ReadWriteException
	{
		ShelfHandler shlfHndlr = new ShelfHandler(wConn);
		ShelfSearchKey shelfSrchKey = new ShelfSearchKey();

		//#CM43217
		// Set the WHERE condition with setsearchKey.
		setSearchKey(shelfSrchKey);
		return shlfHndlr.count(shelfSrchKey);

	}
	
	//#CM43218
	/**
	 * Make the CSV file for ASRS empty cell list, and execute the print.<BR>
	 * Make the slit file if the result is one or more. Return false and end in case of 0.<BR>
	 * Execute the printing job.
	 * @return True when the printing job ends normally and false when failing.
	 */
	public boolean startPrint()
	{

		ShelfReportFinder reportFinder = new ShelfReportFinder(wConn);
		
		WareHouseHandler whandler = new WareHouseHandler(wConn);
		AisleHandler ahandler = new AisleHandler(wConn);

		ShelfSearchKey shelfSKey = new ShelfSearchKey();
		WareHouseSearchKey wrHsSKey = new WareHouseSearchKey();
		AisleSearchKey aisleSKey = new AisleSearchKey();

		String stationNo = "";
		String wrHsSttnNo = "";
		String aisleSttnNo = "";
		String parentSttnNo = "";

		try
		{
			setSearchKey(shelfSKey);
			//#CM43219
			// The OrderBy condition is set.
			shelfSKey.setWHStationNumberOrder(1, true);
			shelfSKey.setParentStationNumberOrder(2, true);
			shelfSKey.setStationNumberOrder(3, true);

			//#CM43220
			// Do not do the print processing when there is no data.
			if(reportFinder.search(shelfSKey) <= 0 )
			{
				//#CM43221
				// 6003010 = There was no print data.
				wMessage = "6003010";
				return false;
			}

			//#CM43222
			// Acquisition of print data file name
			if(!createPrintWriter(FNAME_EMPTYSHELF))
			{
				return false;
			}
            
            // 出力ファイルにヘッダーを作成
            wStrText.append(HEADER_EMPTYSHELF);
			
			Hashtable whnameHash = new Hashtable();
			Hashtable rmHash = new Hashtable();
			Shelf[] shlf = null;
			while (reportFinder.isNext())
			{
				//#CM43223
				// Output 100 retrieval results.
				shlf = (Shelf[])reportFinder.getEntities(100);

				//#CM43224
				// Make the content output to the data file.
				for (int i = 0; i < shlf.length; i++)
				{
					stationNo = "";
					wrHsSttnNo = shlf[i].getWHStationNumber();
					aisleSttnNo = "";
					parentSttnNo = shlf[i].getParentStationNumber();
	
					if (!wrHsSttnNo.equals("") && !parentSttnNo.equals(""))
					{
						if (whnameHash.containsKey(wrHsSttnNo))
						{
							stationNo = (String) whnameHash.get(wrHsSttnNo);
						}
						else
						{
							wrHsSKey.setStationNumber(wrHsSttnNo);
							WareHouse[] wrHouse = (WareHouse[]) whandler.find(wrHsSKey);
							stationNo = wrHouse[0].getWareHouseName();
							whnameHash.put(wrHsSttnNo, stationNo);
						}
						if (rmHash.containsKey(parentSttnNo))
						{
							aisleSttnNo = (String) rmHash.get(parentSttnNo);
						}
						else
						{
							aisleSKey.KeyClear();
							aisleSKey.setStationNumber(parentSttnNo);
							Aisle[] aisle = (Aisle[]) ahandler.find(aisleSKey);
							aisleSttnNo = "RM" + Integer.toString(aisle[0].getAisleNumber());
							rmHash.put(parentSttnNo, aisleSttnNo);
						}
					}
	
					//#CM43225
					//<en>Sets the warehouse and RM to the detail. If the warehouse and RM are changed on Form side,</en>
					//<en> breaks the page.</en>
					wStrText.append(re);
					wStrText.append(format(stationNo) + tb);
					wStrText.append(format(aisleSttnNo) + tb);
					wStrText.append(format(DisplayText.formatLocation(shlf[i].getStationNumber())));

					wPWriter.print(wStrText);
					
					wStrText.setLength(0);
				}
			}
			wPWriter.close();
			
			//#CM43226
			//<en> Executes UCXSingle.</en>
			if (!executeUCX(JOBID_EMPTYSHELF))
			{
				//#CM43227
				//<en> Printing failed. Please refer to the log.</en>
				return false;
			}

			//#CM43228
			//<en> Moves data file to the backup folder.</en>
			ReportOperation.createBackupFile(wFileName);

			//#CM43229
			//<en> Printing completed.</en>
			setMessage("6001010");

		}
		catch (ReadWriteException e)
		{
			//#CM43230
			//<en> Printing failed. Please refer to the log.</en>
			setMessage("6007034");
			return false;
		}
		finally
		{
			try
			{
				reportFinder.close();
			}
			catch (ReadWriteException e)
			{
				//#CM43231
				// The data base error occurred. Refer to the log.
				setMessage("6007002");
				return false;
			}
		}

		return true;
	}

	//#CM43232
	// Package methods -----------------------------------------------

	//#CM43233
	// Protected methods ---------------------------------------------
	//#CM43234
	/**
	 * Set the Where condition.
	 * @param key Search key
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 */
	protected void setSearchKey(ShelfSearchKey key) throws ReadWriteException
	{
		key.setWHStationNumber(wWareHouseNo);

		if (!wRackmasterNo.equals(AsrsParam.ALL_AISLE_NO))
		{
			key.setParentStationNumber(wRackmasterNo);
		}
		//#CM43235
		// The shelf displays all the one of an empty shelf.
		//#CM43236
		// Display the prohibition shelf and the shelf, etc. which cannot be accessed.
		//#CM43237
		// Do not display the empty shelf. 
		key.setPresence(Shelf.PRESENCE_EMPTY);
	}

	//#CM43238
	// Private methods -----------------------------------------------
}
//#CM43239
//end of class
