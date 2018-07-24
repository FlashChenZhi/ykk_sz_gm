//#CM40827
//$Id: SessionStationStatusRet.java,v 1.2 2006/10/26 05:47:20 suresh Exp $

//#CM40828
/*
 * Copyright 2000-2006 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.display.web.listbox.sessionret;

import java.sql.Connection;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.DisplayText;
import jp.co.daifuku.wms.asrs.schedule.AsScheduleParameter;
import jp.co.daifuku.wms.base.common.Entity;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.CarryInformationHandler;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.StationFinder;
import jp.co.daifuku.wms.base.dbhandler.StationSearchKey;
import jp.co.daifuku.wms.base.dbhandler.TerminalAreaHandler;
import jp.co.daifuku.wms.base.dbhandler.TerminalAreaSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Station;
import jp.co.daifuku.wms.base.entity.TerminalArea;

//#CM40829
/**
 * Designer : M.Koyama <BR>
 * Maker : M.Koyama <BR>
 * <BR>
 * This class is used to search data for station status list box<BR>
 * in addition, keep the instance of this class in a session
 * delete from session after use<BR>
 * The following process are called in this class<BR>
 * <BR>
 * <B>1.search process(<CODE>SessionStationStatusRet(Connection, AsScheduleParameter)</CODE>method )<BR></B>
 * <DIR>
 *   executed during listbox screen initialization<BR>
 *   call <CODE>find(SessionStationStatusRet)</CODE> method and search AS/RS station info<BR>
 * <BR>
 *   <search table>
 *   <DIR>
 *     STATION  station control table <BR>
 *     CARRYINFO  carry info table <BR>
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
 *     <tr><td></td><td>station no.</td><td>:</td><td>StationNo</td></tr>
 *     <tr><td></td><td>station name</td><td>:</td><td>DispStationName</td></tr>
 *     <tr><td></td><td>operation mode</td><td>:</td><td>ModeType</td></tr>
 *     <tr><td></td><td>operation mode name</td><td>:</td><td>DispModeTypeName</td></tr>
 *     <tr><td></td><td>work mode name</td><td>:</td><td>CurrentMode</td></tr>
 *     <tr><td></td><td>work mode name</td><td>:</td><td>DispCurrentModeName</td></tr>
 *     <tr><td></td><td>machine status name</td><td>:</td><td>DispControllerStatusName</td></tr>
 *     <tr><td></td><td>work status</td><td>:</td><td>WorkingStatus</td></tr>
 *     <tr><td></td><td>work status name</td><td>:</td><td>DispWorkingStatusName</td></tr>
 *     <tr><td></td><td>work qty</td><td>:</td><td>WorkingCount</td></tr>
 *     <tr><td></td><td>work mode change request</td><td>:</td><td>WorkingModeRequest</td></tr>
 *     <tr><td></td><td>work mode change request name</td><td>:</td><td>DispWorkingModeRequestName</td></tr>
 *   </table>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/28</TD><TD>M.Koyama</TD><TD>new</TD></TR>
 * </TABLE>
 * <BR>
 * @author $Author M.Koyama
 * @version $Revision 1.1 2005/10/28
 */
public class SessionStationStatusRet extends SessionRet
{
	//#CM40830
	/**
	 * call <CODE>find(AsScheduleParameter param)</CODE> method to do search<BR>
	 * set any qty retrieved by <CODE>find(AsScheduleParameter param)</CODE> method<BR>
	 * and it becomes mandatory to call <code>getEntities</code> to fetch search result<BR>
	 * 
	 * @param conn instance to store database connection
	 * @param stParam AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public SessionStationStatusRet(Connection conn, AsScheduleParameter stParam)throws Exception
	{
		wConn = conn;
		find(stParam);
	}
	
	//#CM40831
	/**
	 * returns AS/RS station info search result
	 * @return AS/RSstation info search result
	 */
	public Parameter[] getEntities()
	{
		AsScheduleParameter[] resultArray = null;
		Station[] rData = null ;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{			
			try
			{
				rData = (Station[]) wFinder.getEntities(wStartpoint, wEndpoint);
				resultArray = (AsScheduleParameter[]) convertToParams(rData);
			} catch (Exception e)
			{
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}
		wCurrent = wEndpoint;
		return resultArray;
	}

	//#CM40832
	/**
	 * execute SQL based on input parameter<BR>
	 * save <code>StationFinder</code> used for search as a instance variable<BR>
	 * it becomes mandatory to call <code>getEntities</code> to fetch search result<BR>
	 * @param stParam      AsScheduleParameter with result data
	 * @throws Exception report all the exceptions
	 */
	public void find(AsScheduleParameter stParam) throws Exception
	{
		wFinder = new StationFinder(wConn);
		//#CM40833
		//open cursor
		wFinder.open();

		//#CM40834
		// set the fetch condition
		TerminalAreaHandler tahndl   = new TerminalAreaHandler(wConn);
		TerminalAreaSearchKey takey = new TerminalAreaSearchKey();

		takey.setTerminalNumber(stParam.getTerminalNumber());
		TerminalArea[] tmarea = (TerminalArea[])tahndl.find(takey);

		if (tmarea != null)
		{
			StationSearchKey wStSearchKey = new StationSearchKey();

			String[] st = new String[tmarea.length];
			for (int i = 0; i < tmarea.length; i++)
			{
				st[i] = tmarea[i].getStationNumber();
			}
			//#CM40835
			// Mode switch request type
			int[] mdtypes = {Station.AWC_MODE_CHANGE, Station.AUTOMATIC_MODE_CHANGE};
			wStSearchKey.setStationNumber(st);
			wStSearchKey.setModeType(mdtypes);
			wStSearchKey.setStationNumberOrder(1,true);

			//#CM40836
			//do search
			int count = ((StationFinder)wFinder).search(wStSearchKey);
			wLength = count;
			wCurrent = 0;
		}
		else
		{
			wLength = 0;
			wCurrent = 0;
		}
	}
	
	//#CM40837
	/**
	 * This class converts Station entity to AsScheduleParameter<BR>
	 * 
	 * @param ety entity retrieved
	 * @return <CODE>AsScheduleParameter</CODE>class set with AS/RSstation info
	 */
	private Parameter[] convertToParams(Entity[] ety)
	{
	
		Station[] rData = (Station[]) ety;
		if (rData == null || rData.length==0)
		{	
			return null;
		}
		
		AsScheduleParameter[] stParam = new AsScheduleParameter[rData.length];
		for (int lc = 0; lc < rData.length; lc++)
		{
			stParam[lc] = new AsScheduleParameter();				
			//#CM40838
			// station no.
			stParam[lc].setStationNo(rData[lc].getStationNumber());
			//#CM40839
			// station name
			stParam[lc].setDispStationName(rData[lc].getStationName());
			//#CM40840
			// operation mode
			stParam[lc].setModeType(Integer.toString(rData[lc].getModeType()));
			//#CM40841
			// operation mode name
			stParam[lc].setDispModeTypeName(DisplayText.getText("STATION","MODETYPE", Integer.toString(rData[lc].getModeType())));
			//#CM40842
			// work mode name
			int wCurrentMode = 0;
			switch(rData[lc].getCurrentMode())
			{
				case Station.NEUTRAL:
					wCurrentMode = Station.NEUTRAL;
					break ;
				case Station.STORAGE_MODE:
					wCurrentMode = Station.STORAGE_MODE;
					break ;
				case Station.RETRIEVAL_MODE:
					wCurrentMode = Station.RETRIEVAL_MODE;
					break ;
				//#CM40843
				// All others are undefined station status.
				default:
					break;
			}
			
			stParam[lc].setCurrentMode(Integer.toString(wCurrentMode));
			//#CM40844
			// work mode name
			stParam[lc].setDispCurrentModeName(DisplayText.getText("STATION","CURRENTMODE", Integer.toString(wCurrentMode)));
			//#CM40845
			// machine status name
			stParam[lc].setDispControllerStatusName(DisplayText.getText("STATION","STATUS", Integer.toString(rData[lc].getStatus())));
			//#CM40846
			// work status
			stParam[lc].setWorkingStatus(Integer.toString(rData[lc].getSuspend()));
			//#CM40847
			// work status name
			stParam[lc].setDispWorkingStatusName(DisplayText.getText("STATION","SUSPEND", Integer.toString(rData[lc].getSuspend())));
			//#CM40848
			// work qty
			stParam[lc].setWorkingCount(this.getWorkingCount(wConn, rData[lc].getStationNumber()));
			//#CM40849
			// work mode change request
			stParam[lc].setWorkingModeRequest(Integer.toString(rData[lc].getModeRequest()));
			//#CM40850
			// work mode change request name
			stParam[lc].setDispWorkingModeRequestName(DisplayText.getText("STATION","MODEREQUEST", Integer.toString(rData[lc].getModeRequest())));
		}
		
		return stParam;
	}
	//#CM40851
	/**
	 *fetch work qty of target station
	 * @param conn     instance to store database connection
	 * @param pStation station no.
	 * @return work qty
	 */
	public int getWorkingCount(Connection conn, String pStation)
	{
		try
		{
			CarryInformationHandler wCiHandler = new CarryInformationHandler(conn);
			CarryInformationSearchKey wCiSearchKey = new CarryInformationSearchKey();
			//#CM40852
			//do search
			
			//#CM40853
			// set the fetch condition
			//#CM40854
			// fetch workqty of target station based on carry info table
			wCiSearchKey.KeyClear();
			wCiSearchKey.setSourceStationNumber(pStation, "=", "(", "", "OR");
			wCiSearchKey.setDestStationNumber(pStation, "=", "", ")", "OR");
			
			//#CM40855
			// collect condition
			//#CM40856
			// conveyance key
			wCiSearchKey.setCarryKeyCollect("DISTINCT");
			
			int rCount = wCiHandler.count(wCiSearchKey);
			
			return rCount;
		}
		catch (ReadWriteException ex)
		{
			RmiMsgLogClient.write(new TraceHandler(6006020, ex), this.getClass().getName());
			return 0;
		}
	}
}
//#CM40857
//end of class
