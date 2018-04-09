// $Id: AsShelfStatusSCH.java,v 1.2 2006/10/30 00:48:17 suresh Exp $

//#CM45667
/*
 * Copyright 2000-2001 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */
package jp.co.daifuku.wms.asrs.schedule;

import java.sql.Connection;
import java.text.DecimalFormat;
import java.util.ArrayList;

import jp.co.daifuku.common.ReadWriteException;
import jp.co.daifuku.common.ScheduleException;
import jp.co.daifuku.util.CollectionUtils;
import jp.co.daifuku.wms.asrs.dbhandler.ASShelfHandler;
import jp.co.daifuku.wms.asrs.schedule.AsLocationLevelView.AsLocationBayView;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.ShelfHandler;
import jp.co.daifuku.wms.base.dbhandler.ShelfSearchKey;
import jp.co.daifuku.wms.base.entity.Shelf;
import jp.co.daifuku.wms.idm.schedule.AbstractIdmControlSCH;

//#CM45668
/**
 * Designer : C.Kaminishizono <BR>
 * Maker : C.Kaminishizono <BR>
 * <BR>
 * Class to display ASRS Location Status.  <BR>
 * Receive the content input from the screen as parameter, and do the ASRS Location Status display processing. <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Initial display processing(< CODE>initFind() </ CODE > method)<BR>
 * <BR>
 * <DIR>
 * 	Do the edit of the list of ASRSWarehouse. <BR>
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the Warehouse table < warehouse >.  <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * <DIR>
 *   Edit information on the ASRS BankNo list. <BR>  
 *	<BR>
 *	<Search condition> <BR>
 *	<DIR>
 *		From the shelf table < shelf >. <BR>
 *	</DIR>
 * </DIR>
 * <BR>
 * 2.Processing when Display button is pressed (<CODE>check()</CODE>Method)<BR>
 * <BR>
 * <DIR>
 *   Receive the content input from the screen as parameter, and return Location Status.  <BR>
 *   It is possible to refer by using < CODE>getMessage() </ CODE > method for the content of the error. <BR>
 *   <BR>
 *   <Parameter> *Mandatory Input  +Any one is Mandatory Input <BR><DIR>
 *   Warehouse* <BR>
 *   BankNo* <BR></DIR>
 *   <BR>
 *   <return data> <BR><DIR>
 *   -Location No. <BR>
 *   -BankNo <BR>
 *   -BayNo <BR>
 *   -LevelNo <BR>
 *   -Status flag <BR></DIR>
 *   <BR>
 *   [Content of processing] <BR>
 *   <BR>
 *   -Return each Location Status of specified BankNo from Palette table information (PALETTE) and shelf table information (SHELF).  <BR>
 *   <BR>
 *   [The acquisition order] <BR>
 *   1.Ascending order of Location No <BR>
 *   <BR>
 * </DIR>
 * </FONT>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2005/10/04</TD><TD>C.Kaminishizono</TD><TD>New making</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/10/30 00:48:17 $
 * @author  $Author: suresh $
 */
public class AsShelfStatusSCH extends AbstractAsrsControlSCH
{
	//#CM45669
	/**
	 * Class Name(No plan storage)
	 */
	public static String PROCESSNAME = "AsShelfStatusSCH";

	//#CM45670
	// Class method --------------------------------------------------
	//#CM45671
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/10/30 00:48:17 $");
	}
	//#CM45672
	// Constructors --------------------------------------------------
	//#CM45673
	/**
	 * Initialize this Class. 
	 */
	public AsShelfStatusSCH()
	{
		wMessage = null;
	}
	
	//#CM45674
	/**
	 * Method corresponding to the operation to acquire necessary data when initial is displayed the screen. <BR>
	 * Return null when pertinent data does not exist or it exists by two or more.  <BR>
	 * Set null in < CODE>searchParam</CODE > because it does not need the search condition. 
	 * @param conn        Connection object with database.
	 * @param searchParam It is not used and set null. 
	 * @return <CODE>AsScheduleParameter</CODE>Class
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated is generated. 
	 */
	public Parameter initFind(Connection conn, Parameter searchParam)throws ReadWriteException, ScheduleException
	{
		ShelfHandler wShHandler = new ShelfHandler(conn);
		ShelfSearchKey wShSearchKey = new ShelfSearchKey();
		
		wShSearchKey.KeyClear();

		wShSearchKey.setWHStationNumberCollect("");
		wShSearchKey.setNBankCollect("");
		wShSearchKey.setWHStationNumberGroup(1);
		wShSearchKey.setNBankGroup(2);
		wShSearchKey.setWHStationNumberOrder(1, true);
		wShSearchKey.setNBankOrder(2, true);
				
		Shelf[] rData = (Shelf[])wShHandler.find(wShSearchKey);
		
		if(rData.length==0)
		{
			super.doScheduleExceptionHandling(PROCESSNAME);
		}
		String[] bank = new String[rData.length];
		for(int i = 0; i < rData.length; i++)
		{
			ArrayList hiddenList = new ArrayList();
			hiddenList.add(0, rData[i].getWHStationNumber());
			hiddenList.add(1, Integer.toString(rData[i].getNBank()));

			bank[i] = CollectionUtils.getConnectedString(hiddenList);
		}
		
		AsScheduleParameter parameter = new AsScheduleParameter();
		parameter.setBankNoArray(bank);
		return parameter ;
	}
	
	//#CM45675
	/**
	 * Display the Data retrieval.
	 * @param conn        Connection object with database.
	 * @param searchParam Instance of < CODE>AsScheduleParameter</CODE>Class with set content. 
	 * @return Location Array of master information
	 * @throws ReadWriteException Notified when abnormality occurs by the connection with the data base.
	 * @throws ScheduleException It is notified when the exception not anticipated is generated. 
	 */
	public AsLocationLevelView[] getLevelViewData(Connection conn, Parameter searchParam) throws ReadWriteException, ScheduleException 
	{
		AsScheduleParameter param = (AsScheduleParameter)searchParam;
		
		ShelfHandler slfHandler = new ShelfHandler(conn);
		ASShelfHandler asSlfHandler = new ASShelfHandler(conn);
		ShelfSearchKey slfKey = new ShelfSearchKey();
		Shelf[] slf = null;
		
		//#CM45676
		// Location information acquisition
		Shelf[] dispLocate = (Shelf[])asSlfHandler.findLocationStatus(param.getWareHouseNo(), Integer.parseInt(param.getBankNo()));
		if (dispLocate == null || dispLocate.length <= 0)
		{
			super.doScheduleExceptionHandling(AbstractIdmControlSCH.CLASS_NAME);
		}

		//#CM45677
		// Request the maximum number of Bay. 
		slfKey.KeyClear();
		//#CM45678
		// Only specified Warehouse
		slfKey.setWHStationNumber(param.getWareHouseNo());
		//#CM45679
		// Only specified Bank
		slfKey.setNBank(Integer.parseInt(param.getBankNo()));
		slfKey.setNBayCollect("max");
		slf = (Shelf[]) slfHandler.find(slfKey);
		int maxBay = slf[0].getNBay();

		//#CM45680
		// Request the maximum number of Level. 
		slfKey.KeyClear();
		//#CM45681
		// Only specified Warehouse
		slfKey.setWHStationNumber(param.getWareHouseNo());
		//#CM45682
		// Only specified Bank
		slfKey.setNBank(Integer.parseInt(param.getBankNo()));
		slfKey.setNLevelCollect("max");
		slf = (Shelf[]) slfHandler.find(slfKey);
		int maxLevel = slf[0].getNLevel();

		AsLocationLevelView[] levelViews = new AsLocationLevelView[maxLevel];
		DecimalFormat df = new DecimalFormat("000") ;

		int dispLevel = maxLevel;
		int locCnt = 0;
		
		for (int i = 0; i < maxLevel; i++)
		{
			levelViews[i] = new AsLocationLevelView();
			levelViews[i].setLevel(df.format(dispLevel));
			
			//#CM45683
			// Initialize Bay. 
			AsLocationBayView[] bayViews = new AsLocationBayView[maxBay];
			for (int j = 0; j < maxBay; j++)
			{
				bayViews[j] = levelViews[i].new AsLocationBayView();
				
				if (dispLevel == dispLocate[locCnt].getNLevel() && (j + 1) == dispLocate[locCnt].getNBay())
				{
					bayViews[j].setBankNo(df.format(dispLocate[locCnt].getNBank()));
					bayViews[j].setLevelNo(df.format(dispLocate[locCnt].getNLevel()));
					bayViews[j].setBayNo(df.format(dispLocate[locCnt].getNBay()));
					bayViews[j].setLocation(dispLocate[locCnt].getStationNumber());
					bayViews[j].setStatus(dispLocate[locCnt].getStatus());
					bayViews[j].setAccessFlg(dispLocate[locCnt].getAccessNgFlag());
					bayViews[j].setPresence(dispLocate[locCnt].getPresence());
					bayViews[j].setPStatus(dispLocate[locCnt].getPriority());
					bayViews[j].setEmpty(dispLocate[locCnt].getSide());
					bayViews[j].setWhNumber(dispLocate[locCnt].getWHStationNumber());
					locCnt++;
				}
				else
				{
					bayViews[j].setBankNo(null);
					bayViews[j].setLevelNo(null);
					bayViews[j].setBayNo(df.format(j + 1));
					bayViews[j].setLocation(null);
					bayViews[j].setStatus(-1);
					bayViews[j].setAccessFlg(-1);
					bayViews[j].setPresence(-1);
					bayViews[j].setPStatus(-1);
					bayViews[j].setEmpty(-1);
					bayViews[j].setWhNumber(null);
				}
			}
			levelViews[i].setBayView(bayViews);
			dispLevel--;
		}
		
		return levelViews;
	}
}
//#CM45684
//end of class
