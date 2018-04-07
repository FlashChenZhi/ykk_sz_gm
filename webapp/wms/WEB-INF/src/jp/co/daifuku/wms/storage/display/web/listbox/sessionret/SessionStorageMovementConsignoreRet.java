package jp.co.daifuku.wms.storage.display.web.listbox.sessionret;
//#CM570235
/*
 * Created on Oct 8, 2004
 *
 * Copyright 2000-2003 DAIFUKU Co.,Ltd. All Rights Reserved.
 *
 * This software is the proprietary information of DAIFUKU Co.,Ltd.
 * Use is subject to license terms.
 */

import java.sql.Connection;

import jp.co.daifuku.common.RmiMsgLogClient;
import jp.co.daifuku.common.TraceHandler;
import jp.co.daifuku.common.text.StringUtil;
import jp.co.daifuku.wms.base.common.Parameter;
import jp.co.daifuku.wms.base.dbhandler.DatabaseFinder;
import jp.co.daifuku.wms.base.dbhandler.MovementFinder;
import jp.co.daifuku.wms.base.dbhandler.MovementSearchKey;
import jp.co.daifuku.wms.base.display.web.listbox.sessionret.SessionRet;
import jp.co.daifuku.wms.base.entity.Movement;
import jp.co.daifuku.wms.base.entity.StoragePlan;
import jp.co.daifuku.wms.storage.schedule.StorageSupportParameter;

//#CM570236
/**
 * Designer : Muneendra Y <BR>
 * Maker : Muneendra Y <BR>
 * <BR>
 * The class to do data retrieval for the Consignor list box (movement).<BR>
 * Receive Search condition as Parameter, and do Retrieval of the Consignor list. <BR>
 * Maintain instance in the session when you use this class. <BR>
 * Delete it from the session after use. <BR>
 * <BR>
 * Process it in this class as follows. <BR>
 * <BR>
 * 1.Retrieval processing(<CODE>SessionStorageMovementConsignoreRet</CODE>Constructor)<BR>
 * <DIR>
 *   It is executed when initial data is displayed to the list box screen.<BR>
 *   Retrieval of movement Work information is done by calling of <CODE>find</CODE> Method.<BR>
 * <BR>
 *   <Search condition> *MandatoryItem<BR>
 *   <DIR>
 *      , Consignor Code<BR>
 *   </DIR>
 *   <Retrieval table> <BR>
 *   <DIR>
 *      , DNMOVEMENT <BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * 2.Display processing(<CODE>getEntities</CODE> Method)<BR>
 * <BR>
 * <DIR>
 *   Acquire the data to display it on the screen. <BR>
 *   1.Acquire display information from the retrieval result of obtaining in Retrieval processing. <BR>
 * <BR>
 *   <Display Item>
 *   <DIR>
 *     Consignor Code<BR>
 *     Consignor Name<BR>
 *   </DIR>
 * </DIR>
 * <BR>
 * <TABLE BORDER="1" CELLPADDING="3" CELLSPACING="0">
 * <TR BGCOLOR="#CCCCFF" CLASS="TableHeadingColor"><TD>Date</TD><TD>Name</TD><TD>Comment</TD></TR>
 * <TR><TD>2004/10/08</TD><TD>Muneendra</TD><TD>created this class</TD></TR>
 * </TABLE>
 * <BR>
 * @version $Revision: 1.2 $, $Date: 2006/12/07 08:57:28 $
 * @author  $Author: suresh $
 */

public class SessionStorageMovementConsignoreRet extends SessionRet
{
	//#CM570237
	// Class fields --------------------------------------------------

	//#CM570238
	// Class variables -----------------------------------------------

	//#CM570239
	// Class method --------------------------------------------------
	//#CM570240
	/**
	 * Return the version of this class. 
	 * @return Version and date
	 */
	public static String getVersion()
	{
		return ("$Revision: 1.2 $,$Date: 2006/12/07 08:57:28 $");
	}

	//#CM570241
	// Constructors --------------------------------------------------
	//#CM570242
	/**
	 * Call <CODE>find</CODE> Method to Retrieve it.<BR>
	 * The acquisition qty in <CODE>find</CODE> Method sets how many are there. <BR>
	 * Moreover, it is necessary to call <code>getEntities</code> Method to acquire the retrieval result.<BR>
	 * <BR>
	 * @param conn Connection Connection object with data base. 
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	public SessionStorageMovementConsignoreRet(Connection conn, StorageSupportParameter param) throws Exception
	{
		this.wConn = conn;
		find(param);
	}

	//#CM570243
	// Public methods ------------------------------------------------
	//#CM570244
	/**
	 * Return partial qty of Retrieval result of <CODE>DnMovement</CODE><BR>
	 * <BR>
	 * <Retrieval result><BR>
	 * <DIR>
	 *  , Consignor Code<BR>
	 *  , Consignor Name<BR>
	 * </DIR>
	 * <BR>
	 * @return Retrieval result of DnMovement
	 */
	public Parameter[] getEntities()
	{
		StorageSupportParameter[] resultArray = null;
		Movement temp[] = null;
		if (wLength > 0 && wLength <= DatabaseFinder.MAXDISP)
		{	
			try
			{	
				temp = (Movement[])((MovementFinder)wFinder).getEntities(wStartpoint, wEndpoint);
				resultArray = convertToStorageSupportParams(temp);
			}
			catch (Exception e)
			{
				//#CM570245
				//Drop the error to the log file. 
				RmiMsgLogClient.write(new TraceHandler(6006020, e), this.getClass().getName());
			}
		}

		wCurrent = wEndpoint;
		return resultArray;
	}
	
	//#CM570246
	// Package methods -----------------------------------------------

	//#CM570247
	// Protected methods ---------------------------------------------

	//#CM570248
	// Private methods -----------------------------------------------
	//#CM570249
	/**
	 * Issue SQL sentence based on input Parameter. <BR>
	 * Maintain <code>MovementFinder</code> as instance variable for retrieval. <BR>
	 * It is necessary to call < code>getEntities</code> Method to acquire The retrieval result.<BR>
	 * <BR>
	 * @param param StorageSupportParameter Parameter including Search condition. 
	 * @throws Exception It is notified when some exceptions are generated. 
	 */
	private void find(StorageSupportParameter param) throws Exception
	{	
		MovementSearchKey skey = new MovementSearchKey();
		//#CM570250
		// Retrieval execution
		if (!StringUtil.isBlank(param.getConsignorCode()))
		{
			skey.setConsignorCode(param.getConsignorCode());
		}
		//#CM570251
		// Set the order of the group. 
		skey.setConsignorCodeGroup(1);
		skey.setConsignorNameGroup(2);
		//#CM570252
		// Set the order of acquisition. 
		skey.setConsignorCodeCollect("");
		skey.setConsignorNameCollect("");
		
		//#CM570253
		// Set the order of sorting. 
		skey.setConsignorCodeOrder(1, true);
		skey.setConsignorNameOrder(2, true);
		
		if(param.getSearchStatus() != null && param.getSearchStatus().length > 0)
		{
			String[] search = new String[param.getSearchStatus().length];
			for(int i = 0; i < param.getSearchStatus().length; ++i)
			{
				if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_UNSTARTED))
				{
					search[i] = StoragePlan.STATUS_FLAG_UNSTART;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_STARTED))
				{
					search[i] = StoragePlan.STATUS_FLAG_START;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_WORKING))
				{
					search[i] = StoragePlan.STATUS_FLAG_NOWWORKING;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_PARTIAL_COMPLETION))
				{
					search[i] = StoragePlan.STATUS_FLAG_COMPLETE_IN_PART;
				}
				else if(param.getSearchStatus()[i].equals(StorageSupportParameter.STATUS_FLAG_COMPLETION))
				{
					search[i] = StoragePlan.STATUS_FLAG_COMPLETION;
				}
				else
				{
					search[i] = "*";
				}
			}
			skey.setStatusFlag(search);
		}
		else
		{
			skey.setStatusFlag(StoragePlan.STATUS_FLAG_DELETE, "!=");
		}
		wFinder = new MovementFinder(wConn);
		//#CM570254
		// Cursor open
		wFinder.open();
		int count = ((MovementFinder)wFinder).search(skey);
		//#CM570255
		// Initialization
		wLength = count;
		wCurrent = 0;
	}
	
	//#CM570256
	/**
	 * Convert the Movement entity into <CODE>StorageSupportParameter</CODE>.  <BR>
	 * <BR>
	 * @param movement Movement[] Retrieval result.
	 * @return Retrieval <CODE>StorageSupportParameter</CODE> array which sets result. 
	 */
	private StorageSupportParameter[] convertToStorageSupportParams(Movement[] movement)
	{
		StorageSupportParameter[] stParam = null;
		
		if (movement == null || movement.length==0)
		{	
		 	return null;
		}
			stParam = new StorageSupportParameter[movement.length];
			for (int i = 0; i < movement.length; i++)
			{
					stParam[i] = new StorageSupportParameter();
					stParam[i].setConsignorCode(movement[i].getConsignorCode());
					stParam[i].setConsignorName(movement[i].getConsignorName());
				
			}
			
		return stParam;
	}
	
}
//#CM570257
//end of class
