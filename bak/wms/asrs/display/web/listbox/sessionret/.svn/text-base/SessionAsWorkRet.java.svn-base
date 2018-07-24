//#CM40782
//$Id: SessionAsWorkRet.java,v 1.2 2006/10/26 05:50:01 suresh Exp $

//#CM40783
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.sessionret;

import java.sql.Connection;
import java.util.Vector;

import jp.co.daifuku.common.NoPrimaryException;
import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.asrs.dbhandler.ASStationHandler;
import jp.co.daifuku.wms.asrs.display.ASFindUtil;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.common.WmsParam;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationFinder;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StationHandler;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.CarryInformation;
import jp.co.daifuku.wms.base.entity.Station;

//#CM40784
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * The class to retrieve and to display Carry data list information. <BR>
 * in addition, keep the instance of this class in a session
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * <B>1.search process(<CODE>SessionAsWorkRet(Connection, AsScheduleParameter)</CODE>method )<BR></B>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   <CODE>find(AsScheduleParameter)</CODE>the method calls AS/RScarry info is retrieved. <BR>
 * <BR>
 *   <search table>
 *   <DIR>
 *     DMSTOCK<BR>
 *   </DIR>
 * </DIR>
 * 
 * <B>2.display process(<CODE>getEntities()</CODE>method )<BR></B>
 * <BR>
 * <DIR>
 *   fetch data to display in screen<BR>
 *   fetch the display info from the search result<BR>
 *   set and return the search results as <CODE>AsScheduleParameter</CODE> array<BR>
 * <BR>
 *   <return data>
 *   <DIR>
 *   <table>
 *     <tr><td></td><th>screen name</th><td>:</td><th>parameter name</th></tr>
 *     <tr><td></td><td>conveyance key</td><td>:</td><td>CarryKey</td></tr>
 *     <tr><td></td><td>sending station no.</td><td>:</td><td>FromStationNo</td></tr>
 *     <tr><td></td><td>sending station name</td><td>:</td><td>FromStationName</td></tr>
 *     <tr><td></td><td>receiving station no.</td><td>:</td><td>ToStationNo</td></tr>
 *     <tr><td></td><td>receiving station name</td><td>:</td><td>ToStationName</td></tr>
 *     <tr><td></td><td>picking instruction detail</td><td>:</td><td>RetrievalDetail</td></tr>
 *     <tr><td></td><td>work type</td><td>:</td><td>WorkingType</td></tr>
 *     <tr><td></td><td>Retrieval locationNo</td><td>:</td><td>LocationNo</td></tr>
 *     <tr><td></td><td>work status</td><td>:</td><td>WorkingStatus</td></tr>
 *     <tr><td></td><td>conveyance type</td><td>:</td><td>CarryKind</td></tr>
 *     <tr><td></td><td>job no.</td><td>:</td><td>WorkingNo</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/28</TD><TD>M.Koyama</TD><TD>new</TD> </TR>
 * </TABLE>
 * <BR>
 * @author $Author M.Koyama
 * @version $Revision 1.1 2005/10/28
 */
public class SessionAsWorkRet extends SessionRet
{
	//#CM40785
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result<BR>
	 * @param conn instance to store database connection
	 * @param stParam      AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionAsWorkRet(Connection conn, AsScheduleParameter stParam) throws Exception
	{
		wConn = conn;
		find(stParam);
	}

	//#CM40786
	/**
	 * Return AS/RS transportation info search result. 
	 * @return AS/RS conveyance info search result
	 */
	public Parameter[] getEntities()
	{
		AsScheduleParameter[] resultArray = null;
		CarryInformation[] rData = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{
			try
			{
				rData = (CarryInformation[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (AsScheduleParameter[]) convertToParams(rData);
			}
			catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM40787
	/**
	 * execute SQL based on input parameter<BR>
	 * Maintain retrieved <code>CarryInformationFinder</code> as an instance variable. <BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result<BR>
	 * @param stParam      AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public void find(AsScheduleParameter stParam) throws Exception
	{
		CarryInformationSearchKey cryKey = new CarryInformationSearchKey();

		//#CM40788
		// set the fetch condition
		if (!StringUtil.isBlank(stParam.getStationNo()))
		{
			Vector vec = new Vector();

			//#CM40789
			// fetch the whole station
			ASStationHandler stnHandler = new ASStationHandler(wConn);
			Station[] stations = stnHandler.getStationMntDisplay(stParam.getTerminalNumber(), ASStationHandler.SENDABLE_BOTH);

			int j = 0;
			
			for (int i = 0; i < stations.length; i++)
			{
				if (stParam.getSagyoba().equals(stations[i].getParentStationNumber()))
				{
					//#CM40790
					// When all station are specified, only workplace have to be corresponding.
					if (stParam.getStationNo().equals(WmsParam.ALL_STATION))
					{
						vec.add( j++, stations[i].getStationNumber() );
					}
					else
					{
						//#CM40791
						// Compare stationNo when all station are not specified.
						if (stParam.getStationNo().equals(stations[i].getStationNumber()))
						{
							vec.add( j++, stations[i].getStationNumber() );
						}
					}
				}
			}

			//#CM40792
			//set the search condition
			for (int i = 0; i < vec.size(); i++)
			{
				cryKey.setSourceStationNumber(vec.get(i).toString(), "=", "", "", "OR");
				cryKey.setDestStationNumber(vec.get(i).toString() , "=", "", "", "OR");
			}
			//#CM40793
			// set group by clause
			//#CM40794
			// set sorting order
			cryKey.setPriorityOrder(1, true);
			cryKey.setCarryKeyOrder(2, true);
			cryKey.setCreateDateOrder(3, true);
		}
		else
		{
			cryKey.setPriorityOrder(1, true);
			cryKey.setCarryKeyOrder(2, true);
		}

		wFinder = new CarryInformationFinder(wConn);
		//#CM40795
		//open cursor
		wFinder.open();
		int count = wFinder.search(cryKey);
		wLength = count;
		wCurrent = 0;
	}

	//#CM40796
	/**
	 * This class converts the Carry Information entity into the As Schedule Parameter.<BR>
	 * 
	 * @param ety entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE>class which set the AS/RS carry info
	 * @throws ReadWriteException throw any abnormal database connection error
	 */
	private Parameter[] convertToParams(Entity[] ety) throws ReadWriteException
	{
		CarryInformation[] rData = (CarryInformation[]) ety;
		if (rData == null || rData.length == 0)
		{
			return null;
		}
		//#CM40797
		// Use it to acquire the station name. 
		ASFindUtil util = new ASFindUtil(wConn);
		AsScheduleParameter[] stParam = new AsScheduleParameter[rData.length];
		for (int i = 0; i < rData.length; i++)
		{
			stParam[i] = new AsScheduleParameter();
			//#CM40798
			// Location no., sending station no., receiving station no., Station No. are decided from the transportation division. 
			switch (rData[i].getCarryKind())
			{
				//#CM40799
				// Transportation at Storage from station to location
				//#CM40800
				// Set the aisle at the transportation destination. 
				case CarryInformation.CARRYKIND_STORAGE :
					stParam[i].setLocationNo(rData[i].getDestStationNumber());
					stParam[i].setFromStationNo(rData[i].getSourceStationNumber());
					stParam[i].setToStationNo(rData[i].getAisleStationNumber());
					stParam[i].setStationNo(rData[i].getSourceStationNumber());
					break;
					
				//#CM40801
				// Transportation from location to station when retrieving it
				//#CM40802
				// Set the aisle in the transportation origin. 
				case CarryInformation.CARRYKIND_RETRIEVAL :
					String locationno = null;
					//#CM40803
					// Set Delivery location no in case of stock confirmation.
					if(rData[i].getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
					{
						locationno = rData[i].getRetrievalStationNumber();
					}
					//#CM40804
					// Transportation former station is set, except for the stock confirmation. 
					else
					{
						locationno = rData[i].getSourceStationNumber();
					}
					
					if (!StringUtil.isBlank(locationno))
					{
						stParam[i].setLocationNo(locationno);
					}
					else
					{
						stParam[i].setLocationNo("");
					}
					stParam[i].setFromStationNo(rData[i].getAisleStationNumber());;
					stParam[i].setToStationNo(rData[i].getDestStationNumber());
					stParam[i].setStationNo(rData[i].getDestStationNumber());
					break;
					
				//#CM40805
				// Movement from location to location when it moves between locations
				//#CM40806
				// Display nothing for location no in screen. 
				//#CM40807
				// Set the aisle at the transportation former transportation destination. 
				//#CM40808
				// Do not set station at all. 
				case CarryInformation.CARRYKIND_RACK_TO_RACK :
					if (!StringUtil.isBlank(rData[i].getRetrievalStationNumber()))
					{
						stParam[i].setLocationNo(rData[i].getRetrievalStationNumber());
					}
					else
					{
						stParam[i].setLocationNo("");
					}
					stParam[i].setFromStationNo(rData[i].getSourceStationNumber());;
					stParam[i].setToStationNo(rData[i].getDestStationNumber());
					stParam[i].setStationNo("");
					break;
					
				//#CM40809
				// Movement from station to station when going directly
				//#CM40810
				// display nothing for location no.
				//#CM40811
				//set station no. for origin and destination
				//#CM40812
				// display nothing for station no.
				case CarryInformation.CARRYKIND_DIRECT_TRAVEL :
					stParam[i].setLocationNo("");
					stParam[i].setFromStationNo(rData[i].getSourceStationNumber());
					stParam[i].setToStationNo(rData[i].getDestStationNumber());
					stParam[i].setStationNo("");
					break;
					
				default :
					stParam[i].setLocationNo("");
					stParam[i].setFromStationNo("");
					stParam[i].setToStationNo("");
					stParam[i].setStationNo("");
					break;
			}
			//#CM40813
			// sending station name
			stParam[i].setFromStationName(util.getAsStationName(stParam[i].getFromStationNo()));
			//#CM40814
			// receiving station name
			stParam[i].setToStationName(util.getAsStationName(stParam[i].getToStationNo()));
			//#CM40815
			// station name
			stParam[i].setDispStationName(util.getAsStationName(stParam[i].getStationNo()));
			
			//#CM40816
			// priority type
			stParam[i].setDispPriority(DisplayText.getText("CARRYINFO", "PRIORITY", Integer.toString(rData[i].getPriority())));
			//#CM40817
			// conveyance key
			stParam[i].setCarryKey(rData[i].getCarryKey());
			//#CM40818
			// conveyance type
			if (rData[i].getCarryKind() == CarryInformation.CARRYKIND_STORAGE)
			{
				stParam[i].setCarryKind(AsScheduleParameter.CARRY_KIND_STORAGE);
			}
			else if (rData[i].getCarryKind() == CarryInformation.CARRYKIND_RETRIEVAL)
			{
				stParam[i].setCarryKind(AsScheduleParameter.CARRY_KIND_RETRIEVAL);
			}
			else if (rData[i].getCarryKind() == CarryInformation.CARRYKIND_DIRECT_TRAVEL)
			{
				stParam[i].setCarryKind(AsScheduleParameter.CARRY_KIND_DIRECT_TRAVEL);
			}
			else
			{
				stParam[i].setCarryKind(AsScheduleParameter.CARRY_KIND_RACK_TO_RACK);
			}
			stParam[i].setDispCarryKind(DisplayText.getText("CARRYINFO","CARRYKIND", Integer.toString(rData[i].getCarryKind())));
			//#CM40819
			// conveyance status
			stParam[i].setDispCarringStatusName(DisplayText.getText("CARRYINFO","CMDSTATUS", Integer.toString(rData[i].getCmdStatus())));
			//#CM40820
			// work type
			stParam[i].setDispWorkType(DisplayText.getText("CARRYINFO","WORKTYPE", rData[i].getWorkType()));
			//#CM40821
			// picking instruction detail
			if (rData[i].getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_INVENTORY_CHECK)
			{
				stParam[i].setRetrievalDetail(AsScheduleParameter.RETRIEVALDETAIL_INVENTORY_CHECK);
			}
			else if (rData[i].getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_UNIT)
			{
				stParam[i].setRetrievalDetail(AsScheduleParameter.RETRIEVALDETAIL_UNIT);
			}
			else if (rData[i].getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_PICKING)
			{
				stParam[i].setRetrievalDetail(AsScheduleParameter.RETRIEVALDETAIL_PICKING);
			}
			else if (rData[i].getRetrievalDetail() == CarryInformation.RETRIEVALDETAIL_ADD_STORING)
			{
				stParam[i].setRetrievalDetail(AsScheduleParameter.RETRIEVALDETAIL_ADD_STORING);
			}
			else
			{
				stParam[i].setRetrievalDetail(AsScheduleParameter.RETRIEVALDETAIL_UNKNOWN);
			}
			stParam[i].setDispRetrievalDetail(DisplayText.getText("CARRYINFO","RETRIEVALDETAIL", Integer.toString(rData[i].getRetrievalDetail())));
			//#CM40822
			// job no.
			stParam[i].setWorkingNo(rData[i].getWorkNumber());
		}

		return stParam;
	}

	//#CM40823
	/**
	 * fetch station name from station no.
	 * @param pStation   station no.
	 * @return station name
	 */
	public String getStName(String pStation)
	{
		try
		{
			StationHandler wStHandler = new StationHandler(wConn);
			StationSearchKey wStSearckKey = new StationSearchKey();
			//#CM40824
			//do search

			//#CM40825
			// set the fetch condition
			wStSearckKey.setStationNumber(pStation);

			Station rStation = (Station) wStHandler.findPrimary(wStSearckKey);

			if (rStation == null)
				return "";

			return rStation.getStationName();
		}
		catch (ReadWriteException ex)
		{
			RmiMsgLogClient.write(new TraceHandler(6006020, ex), this.getClass().getName());
			return null;
		}
		catch (NoPrimaryException ex)
		{
			return "";
		}
	}
}
//#CM40826
//end of class
