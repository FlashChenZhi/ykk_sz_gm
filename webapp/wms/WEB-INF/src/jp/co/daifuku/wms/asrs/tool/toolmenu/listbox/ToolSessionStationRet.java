// $Id: ToolSessionStationRet.java,v 1.2 2006/10/30 04:58:33 suresh Exp $
package jp.co.daifuku.wms.asrs.tool.toolmenu.listbox ;

//#CM53402
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
import java.sql.Connection;

import jp.co.daifuku.wms.asrs.tool.common.ToolFindUtil;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationFinder;
import jp.co.daifuku.wms.asrs.tool.dbhandler.ToolStationSearchKey;
import jp.co.daifuku.wms.asrs.tool.location.Station;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;

//#CM53403
/**<en>
 * This class searchs Station and retrieves results.
 * ToolStationFinder class will be used for the search.
 * Station no. will be set as a search condition.
 * Required information such as search conditions will be set at the instantiation of 
 * ToolSessionStationRet of the screen(JSP).
 * Please have the instance preserved in session when using this class.
 * Also please remove session after use of this class.
 * 
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2001/11/01</TD><TD>sawa</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 04:58:33 $
 * @author  $Author: suresh $
 </en>*/
public class ToolSessionStationRet extends ToolSessionRet
{
    
	//#CM53404
	// Class fields --------------------------------------------------

	//#CM53405
	// Class variables -----------------------------------------------
	//#CM53406
	/**<en>
	 * Search key <code>StationNumber</code>
	 </en>*/
	private String wStationNumber;

	//#CM53407
	/**<en>
	 * Search key <code>WHStationNumber</code>
	 </en>*/
	private String wWHStationNumber;

	//#CM53408
	/**<en>
	 * Search key <code>PRStationNumber</code>
	 </en>*/
	private String wPRStationNumber;

	//#CM53409
	/**<en>
	 * Search key <code>WorkPlaceType</code>
	 </en>*/
	private String wWorkPlaceType;

	//#CM53410
	/**<en>
	 * Search key <code>WorkKbn</code>
	 </en>*/
	private int wWorkKbn;

	private Station[] stationarray;

	//#CM53411
	/**<en> Table name  </en>*/

	private String wTableName;

	//#CM53412
	/**<en>
	 * This will be used to determine the message to dispaly.
	 * It will be used in work maintenance.
	 </en>*/
	private String wDispMessage;

	//#CM53413
	// Class method --------------------------------------------------
	//#CM53414
	/**<en>
	 * Returns the version of this class.
	 * @return Version and the date
	 </en>*/
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 04:58:33 $") ;
	}

	//#CM53415
	// Constructors --------------------------------------------------
	//#CM53416
	/**<en>
	 * This constructor is used to set the station no. in search condition for Station search.
	 * @param conn       <code>Connection</code> :used when connecting with database
	 * @param stnumber   :search key station no.
	 * @param whstnumber :search key warehouse station no.
	 * @param condition  :search condition (number of data to show at one display on the screen)
	 * @throws Exception :Notifies if the search has failed.
	 </en>*/
	public ToolSessionStationRet(Connection conn, String stnumber, String whstnumber, String condition) throws Exception
	{
		ToolFindUtil fu = new ToolFindUtil(conn);
 		//#CM53417
 		//<en>Check the invalid characters.</en>
 		if( fu.isUndefinedCharForListBox(stnumber) )
 		{
     		wLength = 0;
 		}
 		else
 		{
			this.wConn            = conn;
			this.wStationNumber   = stnumber;
			this.wWHStationNumber = whstnumber;
//#CM53418
//			try
//#CM53419
//			{
//#CM53420
//				this.wCondition = Integer.parseInt( condition );
//#CM53421
//			}
//#CM53422
//			catch (Exception e)
//#CM53423
//			{
//#CM53424
//				this.wCondition = 10;
//#CM53425
//			}
			//#CM53426
			//<en> Search</en>
			find();
		}
	}

	//#CM53427
	/**<en>
	 * This constructor is used to set the station no. in search condition for Station search.
	 * @param conn       <code>Connection</code> :used when connecting with database
	 * @param stnumber   :search key station no.
	 * @param whstnumber :search key warehouse station no.
	 * @param prstnumber :search key parent station no.
	 * @param wptype     :search key workshop type
	 * @param kbn        :search key display work type (0:workshop, 1:main station)
	 * @param condition  :search condition (number of data to show at one display on the screen)
	 * @throws Exception :Notifies if the search has failed.
	 </en>*/
	public ToolSessionStationRet(Connection conn, String stnumber, String whstnumber, String prstnumber, String wptype, int kbn, String condition) throws Exception
	{
		ToolFindUtil fu = new ToolFindUtil(conn);
 		//#CM53428
 		//<en>Check the invalid characters.</en>
 		if( fu.isUndefinedCharForListBox(stnumber) )
 		{
     		wLength = 0;
 		}
 		else
 		{
			this.wConn            = conn;
			this.wStationNumber   = stnumber;
			this.wWHStationNumber = whstnumber;
			this.wWorkPlaceType  = wptype;
			this.wWorkKbn         = kbn;
			this.wPRStationNumber = prstnumber;
//#CM53429
//			try
//#CM53430
//			{
//#CM53431
//				this.wCondition = Integer.parseInt( condition );
//#CM53432
//			}
//#CM53433
//			catch (Exception e)
//#CM53434
//			{
//#CM53435
//				this.wCondition = 10;
//#CM53436
//			}
			//#CM53437
			//<en> Search</en>
			find_st();
		}
	}
	//#CM53438
	// Public methods ------------------------------------------------
	//#CM53439
	/**<en>
	 * Search the station based on the station no. as a key.
	 * @throws Exception :Notifies if the search has failed.
	 </en>*/
	public void find() throws Exception
	{
		int count = 0;
		int[] temp_workplace = {Station.STAND_ALONE_STATIONS, Station.AISLE_CONMECT_STATIONS,Station.MAIN_STATIONS};

		//#CM53440
		//<en> Conduct search.</en>
		ToolStationSearchKey srhkey = new ToolStationSearchKey();

		wFinder = new ToolStationFinder(wConn);
		//#CM53441
		//<en> Open a cursor.</en>
		wFinder.open();

		if(!wStationNumber.equals("") && wStationNumber != null)
		{
			srhkey.setNumber(wStationNumber);
		}
		if(wWHStationNumber != null )
		{
			srhkey.setWareHouseStationNumber(wWHStationNumber);

			//#CM53442
			//<en> Workshop type [1: Stand alone workshop, 2: Connected aisle workshop or 3: Main station]</en>
			srhkey.setWorkPlaceType(temp_workplace);
			//#CM53443
			//<en> Order of data dispaly-station no.</en>
			srhkey.setNumberOrder(1,true);

			count = ((ToolStationFinder)wFinder).search(srhkey);
		}
		//#CM53444
		//<en> Initialization</en>
		wLength  = count;
		wCurrent = 0;
	}

	//#CM53445
	/**<en>
	 * Search the station based on the station no. as a key.
	 * @throws Exception :Notifies if the search has failed.
	 </en>*/
	public void find_st() throws Exception
	{
		int count = 0;
		int flg = 0;
		int[] temp_sttype = {	1,	2,	3};
		int[] main_temp_sttype = {	2,	3};

		//#CM53446
		//<en> Conduct search.</en>
		ToolStationSearchKey srhkey = new ToolStationSearchKey();

		wFinder = new ToolStationFinder(wConn);
		//#CM53447
		//<en> open a cursor.</en>
		wFinder.open();

		if(!wStationNumber.equals("") && wStationNumber != null)
		{
			srhkey.setNumber(wStationNumber);
		}
		if(wWHStationNumber != null)
		{
			srhkey.setWareHouseStationNumber(wWHStationNumber);

			//#CM53448
			//<en> Display work type (conditions differs depending on the object; </en>
			//<en> either 'workshop' or 'main station' which is being set'.)</en>

			//#CM53449
			//<en> Setting the workshops.</en>
			if(wWorkKbn == 0)
			{
				//#CM53450
				//<en> Station type</en>
				srhkey.setStationType(temp_sttype);
				//#CM53451
				//<en> Order of data dispaly-aisle station no., station no.</en>
	//#CM53452
	//			srhkey.setAisleStationNumberOrder(1,true);
				srhkey.setNumberOrder(1,true);

				if(wWorkPlaceType.equals("1"))
				{
					flg = 0;
				}
				else
				{
					flg = 1;
				}

				count = ((ToolStationFinder)wFinder).search(srhkey,wPRStationNumber,flg);
			}
			//#CM53453
			//<en> Setting the main stations.</en>
			else
			{

				//#CM53454
				//<en> Station type</en>
				srhkey.setStationType(main_temp_sttype);
				srhkey.setSendable(Station.SENDABLE);
				srhkey.setWorkPlaceType(Station.NOT_WORKPLACE);

				//#CM53455
				//<en> Order of data dispaly-aisle station no., station no.</en>
//#CM53456
//				srhkey.setAisleStationNumberOrder(1,true);
				srhkey.setNumberOrder(1,true);

				count = ((ToolStationFinder)wFinder).search(srhkey);
			}
		}
		//#CM53457
		//<en> Initialization</en>
		wLength  = count;
		wCurrent = 0;
	}
	//#CM53458
	/**<en>
	 * Return the search results for the Station.
	 * @return :the search results for Station
	 </en>*/
	public Station[] getStation()
	{
		//#CM53459
		//<en> In case the search resulted in fractional number, have the fractional number of data in last page.</en>
		//#CM53460
		//<en> Therefore if looped the process as much as wEndpoint counts with for-text, exception will occur.</en>
		if ( wEndpoint >= getLength() )
		{
			//#CM53461
			//<en> calculation of the fraction</en>
			wFraction  = getLength() - (wEndpoint - wCondition);
			wEndpoint  = getLength();
		}

		Station[] resultArray = null;

		if(wLength>0)
		{
			try
			{
				resultArray = (Station[])((ToolStationFinder)wFinder).getEntitys(wStartpoint,wEndpoint);
			}
			catch(Exception e)
			{
				//#CM53462
				//<en>Record the error in log.</en>
				RmiMsgLogClient.write( new TraceHandler(6126013, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM53463
	/**<en>
	 * Determines whether/not the redisplay button was pressed on search result screen.
	 * Conduct search once again if Redisplay button was pressed.<BR>
	 * CLoas the Statement first then conduct the search once again.
	 * @param reload :value of the flag when Redisplay button was pressed.
	 </en>*/
	public void reload(String reload)
	{
		try
		{
			if (reload != null)
			{
				//#CM53464
				//<en> Close the Statement.</en>
				wFinder.close();
				//#CM53465
				//<en> Clear the data of previous search.</en>
				wFinder = null;
				//#CM53466
				//<en> Conduct search again.</en>
				find();
			}
		}
		catch (Exception e)
		{
		}
	}

	//#CM53467
	/**<en>
	 * This is used to determine the message to display.
	 * @return wDispMessage
	 </en>*/
	public String getDispMessage()
	{
		return wDispMessage;
	}

	//#CM53468
	// Package methods -----------------------------------------------

	//#CM53469
	// Protected methods ---------------------------------------------

	//#CM53470
	// Private methods -----------------------------------------------

}
//#CM53471
//end of class
